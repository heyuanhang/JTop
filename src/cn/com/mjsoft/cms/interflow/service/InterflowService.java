package cn.com.mjsoft.cms.interflow.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.interflow.bean.FriendSiteLinkBean;
import cn.com.mjsoft.cms.interflow.bean.SiteAnnounceBean;
import cn.com.mjsoft.cms.interflow.dao.InterflowDao;
import cn.com.mjsoft.cms.interflow.dao.vo.FriendSiteLink;
import cn.com.mjsoft.cms.interflow.dao.vo.SiteAnnounce;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.cache.Cache;

import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

@SuppressWarnings( "unchecked" )
public class InterflowService
{
    private static Logger log = Logger.getLogger( InterflowService.class );

    public static Cache interflowFlCache = new ClusterCacheAdapter( 300,
        "interflowService.interflowFlCache" );

    public static Cache interflowAnCache = new ClusterCacheAdapter( 1000,
        "interflowService.interflowAnCache" );

    private static InterflowService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private InterflowDao inDao;

    private InterflowService()
    {
        inDao = new InterflowDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new InterflowService();
        }
    }

    public static InterflowService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public void addNewFriendSite( FriendSiteLink fl )
    {
        String siteLogo = fl.getSiteLogo();

        Long slResId = Long.valueOf( StringUtil.getLongValue( siteLogo, -1 ) );

        fl.setSiteLogo( ServiceUtil.disposeSingleImageInfo( slResId ) );

        fl.setOrderFlag( Integer.valueOf( 0 ) );

        fl.setSiteId( ( ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo() ).getSiteId() );

        inDao.saveFriendSiteLinkBean( fl );

        clearFlCache();
    }

    public void editFriendSite( FriendSiteLink fl, Map params )
    {
        String siteLogo = fl.getSiteLogo();

        Long slResId = Long.valueOf( StringUtil.getLongValue( siteLogo, -1 ) );

        ServiceUtil.disposeOldImageInfo( slResId, "siteLogo", params );

        fl.setSiteLogo( ServiceUtil.disposeSingleImageInfo( slResId ) );

        inDao.updateFriendSiteLink( fl );

        clearFlCache();
    }

    public void deleteFriendSite( List idList )
    {
        if( idList == null )
        {
            return;
        }

        long id = -1;

        try
        {
            mysqlEngine.beginTransaction();

            for ( int i = 0; i < idList.size(); i++ )
            {
                id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );

                if( id < 1 )
                {
                    continue;
                }

                inDao.deleteFriendSiteLink( Long.valueOf( id ) );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearFlCache();

        }
    }

    public void addNewFriendSiteType( Map paramsMap )
    {
        paramsMap.put( "siteId", ( ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo() ).getSiteId() );

        inDao.saveFriendSiteLinkType( paramsMap );

        clearFlCache();
    }

    public void editFriendSiteType( Map paramsMap )
    {
        inDao.updateFriendSiteLinkType( paramsMap );

        clearFlCache();
    }

    public void deleteFriendSiteType( List idList )
    {
        if( idList == null )
        {
            return;
        }

        long id = -1;

        try
        {
            mysqlEngine.beginTransaction();

            for ( int i = 0; i < idList.size(); i++ )
            {
                if( idList.get( i ) instanceof String )
                {
                    id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );
                }
                else
                {
                    id = ( ( Long ) idList.get( i ) ).longValue();
                }

                if( id < 1 )
                {
                    continue;
                }

                inDao.deleteFriendSiteLinkType( Long.valueOf( id ) );

                inDao.deleteFriendSiteLinkByType( Long.valueOf( id ) );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearFlCache();
        }
    }

    public void sortFriendSite( Map params )
    {
        try
        {
            mysqlEngine.beginTransaction();

            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            List flBeanList = inDao.queryFriendSiteLinkBeanList( site.getSiteId(), Long
                .valueOf( ( String ) params.get( "typeId" ) ) );

            FriendSiteLinkBean flBean = null;

            String order = null;

            for ( int i = 0; i < flBeanList.size(); i++ )
            {
                flBean = ( FriendSiteLinkBean ) flBeanList.get( i );

                order = ( String ) params.get( "orderFlag-" + flBean.getFlId() );

                if( StringUtil.getIntValue( order, -1 ) > 0 )
                {
                    inDao.updateFriendSiteLinkOrder( Integer.valueOf( order ), flBean.getFlId() );
                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearFlCache();

        }

    }

    public Object getFriendSiteInfoTag( String flId, String typeId )
    {
        if( StringUtil.isStringNotNull( flId ) )
        {
            return inDao.querySingleFriendSiteLinkBean( Long.valueOf( StringUtil.getLongValue(
                flId, -1 ) ) );
        }
        else
        {
            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            Long typeIdVar = null;

            if( StringUtil.isStringNull( typeId ) )
            {
                List allType = inDao.queryFriendSiteLinkTypeList( site.getSiteId() );

                if( !allType.isEmpty() )
                {
                    typeIdVar = ( Long ) ( ( Map ) allType.get( 0 ) ).get( "ltId" );
                }
            }
            else
            {
                typeIdVar = Long.valueOf( StringUtil.getLongValue( typeId, -1 ) );
            }

            return inDao.queryFriendSiteLinkBeanList( site.getSiteId(), typeIdVar );
        }

    }

    public Object getFriendSiteTypeTag( String ltId )
    {
        if( StringUtil.isStringNotNull( ltId ) )
        {
            return inDao.querySingleFriendSiteLinkType( Long.valueOf( StringUtil.getLongValue(
                ltId, -1 ) ) );
        }
        else
        {
            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            return inDao.queryFriendSiteLinkTypeList( site.getSiteId() );
        }
    }

    public List retrieveSiteFriendLinkByType( Long siteId, String tFlag )
    {
        String key = "retrieveSiteFriendLinkByType:" + siteId + "|" + tFlag;

        List result = ( List ) interflowFlCache.getEntry( key );

        if( result == null )
        {
            result = inDao.queryFriendSiteLinkBeanList( siteId, tFlag );

            interflowFlCache.putEntry( key, result );
        }

        return result;
    }

    public void addNewSiteAnnounce( SiteAnnounce sa )
    {
        if( sa == null )
        {
            return;
        }

        String content = SystemSafeCharUtil.resumeHTML( sa.getContent() );

        // html白名单
        sa.setContent( ServiceUtil.cleanEditorHtmlByWhiteRule( content ) );

        sa.setSiteId( ( ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo() ).getSiteId() );

        if( sa.getShowStartDate() == null )
        {
            // 注意：只精确到日期的非精确时间要求，无需集群时间同步
            Calendar cal = Calendar.getInstance();

            cal.set( Calendar.HOUR_OF_DAY, 0 );
            cal.set( Calendar.SECOND, 0 );
            cal.set( Calendar.MINUTE, 0 );

            sa.setShowStartDate( cal.getTime() );
        }

        if( sa.getShowEndDate() == null )
        {
            sa.setShowEndDate( Constant.CONTENT.MAX_DATE );
        }

        UpdateState us = inDao.saveSiteAnnounce( sa );

        if( us.haveKey() )
        {
            inDao
                .updateSiteAnnounceOrder( Long.valueOf( us.getKey() ), Long.valueOf( us.getKey() ) );
        }

        clearAnCache();
    }

    public void editSiteAnnounce( SiteAnnounce sa )
    {
        if( sa == null )
        {
            return;
        }

        String content = SystemSafeCharUtil.resumeHTML( sa.getContent() );

        // html白名单
        sa.setContent( ServiceUtil.cleanEditorHtmlByWhiteRule( content ) );

        if( sa.getShowStartDate() == null )
        {
            // 注意：只精确到日期的非精确时间要求，无需集群时间同步
            Calendar cal = Calendar.getInstance();

            cal.set( Calendar.HOUR_OF_DAY, 0 );
            cal.set( Calendar.SECOND, 0 );
            cal.set( Calendar.MINUTE, 0 );

            sa.setShowStartDate( cal.getTime() );
        }

        if( sa.getShowEndDate() == null )
        {
            sa.setShowEndDate( Constant.CONTENT.MAX_DATE );
        }

        inDao.updateSiteAnnounce( sa );

        clearAnCache();
    }

    public void sortAnnounce( Map params )
    {
        try
        {
            mysqlEngine.beginTransaction();

            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            List anBeanList = inDao.querySiteAnnounceBeanList( site.getSiteId(), Long.valueOf( 0 ),
                Integer.valueOf( 100000 ) );

            SiteAnnounceBean anBean = null;

            String order = null;

            for ( int i = 0; i < anBeanList.size(); i++ )
            {
                anBean = ( SiteAnnounceBean ) anBeanList.get( i );

                order = ( String ) params.get( "orderFlag-" + anBean.getAnId() );

                if( StringUtil.getIntValue( order, -1 ) > 0 )
                {
                    inDao.updateSiteAnnounceOrder( anBean.getAnId(), Long.valueOf( order ) );
                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearAnCache();

        }

    }

    public void setSiteAnnounceUseStatus( List idList, Integer status )
    {
        if( idList == null )
        {
            return;
        }

        long id = -1;

        try
        {
            mysqlEngine.beginTransaction();

            for ( int i = 0; i < idList.size(); i++ )
            {
                id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );

                if( id < 1 )
                {
                    continue;
                }

                inDao.updateSiteAnnounceUseStatus( id, status );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearAnCache();
        }

    }

    public void deleteSiteAnnounce( List idList )
    {
        if( idList == null )
        {
            return;
        }

        long id = -1;

        try
        {
            mysqlEngine.beginTransaction();

            for ( int i = 0; i < idList.size(); i++ )
            {
                id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );

                if( id < 1 )
                {
                    continue;
                }

                inDao.deleteSiteAnnounce( id );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearAnCache();
        }
    }

    public Long retrieveSiteAnnounceBeanCountByCurrDate( Long siteId, Date cd )
    {
        String key = "retrieveSiteAnnounceBeanCountByCurrDate:" + siteId + "|" + cd + "|";

        Long result = ( Long ) interflowAnCache.getEntry( key );

        if( result == null )
        {
            result = inDao.querySiteAnnounceBeanCount( siteId, cd );

            interflowAnCache.putEntry( key, result );
        }

        return result;

    }

    public List retrieveSiteAnnounceBeanListByCurrDate( Long siteId, Date cd, Long start,
        Integer size )
    {
        String key = "retrieveSiteAnnounceBeanListByCurrDate:" + siteId + "|" + cd + "|" + start
            + "|" + size;

        List result = ( List ) interflowAnCache.getEntry( key );

        if( result == null )
        {
            result = inDao.querySiteAnnounceBeanList( siteId, cd, start, size );

            interflowAnCache.putEntry( key, result );
        }

        return result;
    }

    public Object getSiteAnnounceTag( String flId, String pn, String size )
    {
        if( StringUtil.isStringNotNull( flId ) )
        {
            return inDao.querySingleSiteAnnounceBean( Long.valueOf( StringUtil.getLongValue( flId,
                -1 ) ) );
        }
        else
        {
            int pageNum = StringUtil.getIntValue( pn, 1 );

            int pageSize = StringUtil.getIntValue( size, 15 );

            Page pageInfo = null;

            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            Long count = inDao.querySiteAnnounceBeanCount( site.getSiteId() );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            List result = inDao.querySiteAnnounceBeanList( site.getSiteId(), Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageSize ) );

            return new Object[] { result, pageInfo };
        }
    }

    public static void clearFlCache()
    {
        interflowFlCache.clearAllEntry();

    }
 
    public static void clearAnCache()
    {
        interflowAnCache.clearAllEntry();
    }

   
}
