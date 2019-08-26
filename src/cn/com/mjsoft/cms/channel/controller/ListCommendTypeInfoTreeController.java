package cn.com.mjsoft.cms.channel.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.channel.bean.CommendTypeTreeItemBean;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.security.Role;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/channel" )
public class ListCommendTypeInfoTreeController
{
    public static DataSource ds = new MySqlDataSource();

    private static PersistenceEngine pe = new PersistenceEngine( ds );

    private static final boolean showMode = false;

    private ChannelService channelService = ChannelService.getInstance();

    public static Map cacheManager = new HashMap();
    static
    {
        cacheManager.put( "treeItemInfo", new ClusterCacheAdapter( 1200,
            "listCommendTypeInfoTreeFlow.treeItemInfo" ) );
    }

    @ResponseBody
    @RequestMapping( value = "/listCommendTypeInfoTree.do", method = { RequestMethod.POST } )
    public Object listCommendTypeInfoTree( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        List commendTypeBeanList = channelService.retrieveContentCommendTypeBean( siteBean
            .getSiteFlag(), Long.valueOf( -1 ), false, false, true );

        Cache itemCache = ( Cache ) cacheManager.get( "treeItemInfo" );

        String itemKey = "treeiItem:" + siteBean.getSiteId();

        Map hMap = ( Map ) itemCache.getEntry( itemKey );

        if( hMap == null )
        // if( true )
        {
            hMap = new TreeMap();

            CommendTypeTreeItemBean item = null;

            // String base = config.getSystemBasePath();

            // 取站点所有根栏目
            List allRootClassBeanList = channelService
                .retrieveAllRootClassBeanInfoBySiteFlag( siteBean.getSiteFlag() );

            // 获取所有推荐位入口权限信息

            Role[] rs = SecuritySessionKeeper.getSecuritySession().getAuth().getUserRoleCopy();

            StringBuffer buf = new StringBuffer( "and roleId in (" );

            boolean haveRole = false;

            for ( int i = 0; i < rs.length; i++ )
            {
                if( i + 1 != rs.length )
                {
                    buf.append( ( ( Role ) rs[i] ).getRoleID() + ", " );
                }
                else
                {
                    buf.append( ( ( Role ) rs[i] ).getRoleID() );
                }

                haveRole = true;
            }

            buf.append( ")" );

            String sqlHaveRole = "select DISTINCT accId from role_relate_resource_acc rra where rra.siteId=? and secResId=(select secResId from securityresource where sysFlag='manage_commend_jsp') "
                + ( haveRole ? buf.toString() : "" );

            Set roleCtIdSet = new HashSet( pe.querySingleCloumn( sqlHaveRole,
                new Object[] { siteBean.getSiteId() }, Long.class ) );

            ContentClassBean classBean = null;

            ContentClassBean commClassBean = null;

            ContentCommendTypeBean commBean = null;

            int index = 0;

            // 通用

            item = new CommendTypeTreeItemBean( "全站通用", Long.valueOf( -1 ) );

            hMap.put( Integer.valueOf( index++ ), item );

            for ( int j = 0; j < commendTypeBeanList.size(); j++ )
            {

                commBean = ( ContentCommendTypeBean ) commendTypeBeanList.get( j );

                if( !roleCtIdSet.contains( commBean.getCommendTypeId() ) )
                {
                    if( !showMode )
                    {
                        continue;
                    }
                }

                if( commBean.getClassId().longValue() == -9999 )
                {

                    index++;

                    item = new CommendTypeTreeItemBean( commBean.getCommendTypeId(), commBean
                        .getCommendName(), commBean.getClassId(), commBean.getChildClassMode(),
                        commBean.getCommFlag(), commBean.getMustCensor(), commBean.getSiteFlag(),
                        Long.valueOf( -9999 ) );

                    item.setFirstClassId( Long.valueOf( -9999 ) );

                    // item.setSiteId( ( Long ) m.get( "siteId" ) );

                    hMap.put( Integer.valueOf( index ), item );

                }
            }

            // 一般栏目
            for ( int i = 0; i < allRootClassBeanList.size(); i++ )
            {
                index++;

                classBean = ( ContentClassBean ) allRootClassBeanList.get( i );

                item = new CommendTypeTreeItemBean( classBean.getClassName(), Long.valueOf( -1 ) );

                hMap.put( Integer.valueOf( index ), item );

                for ( int j = 0; j < commendTypeBeanList.size(); j++ )
                {

                    commBean = ( ContentCommendTypeBean ) commendTypeBeanList.get( j );

                    if( !roleCtIdSet.contains( commBean.getCommendTypeId() ) )
                    {
                        if( !showMode )
                        {
                            continue;
                        }
                    }

                    if( commBean.getClassId().longValue() > 0 )
                    {

                        commClassBean = channelService
                            .retrieveSingleClassBeanInfoByClassId( commBean.getClassId() );

                        // 若comm的所属根class是当前,则通过
                        if( commClassBean.getLinearOrderFlag().startsWith(
                            classBean.getLinearOrderFlag() )
                            || commClassBean.getLinearOrderFlag().equals(
                                classBean.getLinearOrderFlag() ) )

                        {

                            index++;

                            item = new CommendTypeTreeItemBean( commBean.getCommendTypeId(),
                                commBean.getCommendName(), commBean.getClassId(), commBean
                                    .getChildClassMode(), commBean.getCommFlag(), commBean
                                    .getMustCensor(), commBean.getSiteFlag(), classBean
                                    .getClassId() );

                            item.setFirstClassId( classBean.getClassId() );

                            // item.setSiteId( ( Long ) m.get( "siteId" ) );

                            hMap.put( Integer.valueOf( index ), item );
                        }

                    }
                }

            }

            itemCache.putEntry( itemKey, hMap );
        }

        return hMap;
    }

    public static void resizeSiteCommendTypeCache()
    {
        ( ( Cache ) cacheManager.get( "treeItemInfo" ) ).clearAllEntry();

    }

}
