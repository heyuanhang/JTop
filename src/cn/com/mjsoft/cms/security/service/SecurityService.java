package cn.com.mjsoft.cms.security.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.controller.ListCommendTypeInfoTreeController;
import cn.com.mjsoft.cms.channel.controller.ListContentClassInfoTreeController;
import cn.com.mjsoft.cms.channel.dao.ChannelDao;
 
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.member.bean.MemberAccRuleBean;
import cn.com.mjsoft.cms.member.dao.MemberDao;
import cn.com.mjsoft.cms.member.dao.vo.MemberAccRule;
import cn.com.mjsoft.cms.organization.bean.SystemOrganizationBean;
import cn.com.mjsoft.cms.organization.dao.OrgDao;
import cn.com.mjsoft.cms.security.bean.MemberSecurityResourceBean;
import cn.com.mjsoft.cms.security.bean.SecurityResourceBean;
import cn.com.mjsoft.cms.security.bean.SystemLoginUser;
import cn.com.mjsoft.cms.security.bean.SystemRoleBean;
import cn.com.mjsoft.cms.security.bean.SystemUserBean;
import cn.com.mjsoft.cms.security.dao.SecurityDao;
import cn.com.mjsoft.cms.security.dao.vo.MemberRole;
import cn.com.mjsoft.cms.security.dao.vo.MemberSecurityResource;
import cn.com.mjsoft.cms.security.dao.vo.SecurityResource;
import cn.com.mjsoft.cms.security.dao.vo.SystemRole;
import cn.com.mjsoft.cms.security.dao.vo.SystemUser;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.dao.SiteGroupDao;
import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.persistence.exception.DataAccessException;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.Role;
import cn.com.mjsoft.framework.security.SecuritrConstant;
import cn.com.mjsoft.framework.security.crypto.PasswordUtility;
import cn.com.mjsoft.framework.security.exception.SecurityException;
import cn.com.mjsoft.framework.security.headstream.IUser;
import cn.com.mjsoft.framework.security.headstream.UserProvider;
import cn.com.mjsoft.framework.security.headstream.bean.ResourceState;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;

public class SecurityService implements UserProvider
{
    private static Logger log = Logger.getLogger( SecurityService.class );

    private static SecurityService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private static Map cacheManager = new HashMap();

    static
    {
        cacheManager.put( "allSecurityResourceMapCache", new ClusterCacheAdapter( 2,
            "securityService.allSecurityResourceMapCache" ) );

        cacheManager.put( "allMemberSecurityResourceMapCache", new ClusterCacheAdapter( 2,
            "securityService.allMemberSecurityResourceMapCache" ) );

        cacheManager.put( "retrieveSingleMemberAccRule", new ClusterCacheAdapter( 8000,
            "securityService.retrieveSingleMemberAccRule" ) );
    }

    private SecurityDao securityDao;

    private ChannelDao channelDao = null;

    private MemberDao memberDao;

    private OrgDao orgDao;

    private SiteGroupDao siteGroupDao;

    private SecurityService()
    {
        securityDao = new SecurityDao( mysqlEngine );

        orgDao = new OrgDao( mysqlEngine );

        channelDao = new ChannelDao( mysqlEngine );

        memberDao = new MemberDao( mysqlEngine );

        siteGroupDao = new SiteGroupDao( mysqlEngine );

    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new SecurityService();
        }
    }

    public static SecurityService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public SecurityResourceBean retrieveSingleSecurityResourceBean( Long resId )
    {
        return securityDao.querySingleSecurityResourceInfoBean( resId );
    }

    public MemberSecurityResourceBean retrieveSingleMemberSecurityResourceBean( Long resId )
    {
        return securityDao.querySingleMemberSecurityResourceInfoBean( resId );
    }

    public SecurityResourceBean retrieveSingleSecurityResourceBean( String linear )
    {
        return securityDao.querySingleSecurityResourceInfoBean( linear );
    }

    public MemberSecurityResourceBean retrieveSingleMemberSecurityResourceBean( String linear )
    {
        return securityDao.querySingleMemberSecurityResourceInfoBean( linear );
    }

    public SecurityResourceBean retrieveSingleSecurityResourceBeanByFlag( String flag )
    {
        return securityDao.querySingleSecurityResourceInfoBeanByFlag( flag );
    }

    public Set retrieveSecurityResRalateRoleIdByFlag( String flag )
    {
        return new HashSet( securityDao.querySecurityResRalateRoleIdByFlag( flag ) );
    }

    public Set retrieveAccSecurityResRalateRoleIdByFlag( Long resId, Long accId, Long siteId )
    {
        return new HashSet( securityDao
            .queryAccSecurityResRalateRoleIdByFlag( resId, accId, siteId ) );
    }

    public String checkSecurityResType( Long parentResId, Integer currResType, boolean isMember )
    {
        Integer parentResType = null;

        if( isMember )
        {
            parentResType = securityDao.queryMemberSecurityResType( parentResId );
        }
        else
        {
            parentResType = securityDao.querySecurityResType( parentResId );
        }

        if( parentResId.longValue() == -1 )// 顶级入口类型
        {
            parentResType = Constant.SECURITY.RES_TYPE_ENTRY;

            if( !Constant.SECURITY.RES_TYPE_ENTRY.equals( currResType ) )
            {
                return "6";// 顶级入口只能添加入口
            }
            else
            {
                return "-1";
            }

        }

        if( Constant.SECURITY.RES_TYPE_ENTRY.equals( parentResType ) )
        {

            if( !Constant.SECURITY.RES_TYPE_CLASS.equals( currResType ) )
            {
                return "1";// 入口下必须模块
            }
        }
        else if( Constant.SECURITY.RES_TYPE_CLASS.equals( parentResType ) )
        {
            if( !Constant.SECURITY.RES_TYPE_ACT_LINK.equals( currResType ) )
            {
                return "2";// 模块下必须菜单
            }
        }
        else if( Constant.SECURITY.RES_TYPE_ACT_LINK.equals( parentResType ) )
        {
            if( !Constant.SECURITY.RES_TYPE_GROUP.equals( currResType ) )
            {
                return "3";// 菜单下必须操作组
            }
        }
        else if( Constant.SECURITY.RES_TYPE_GROUP.equals( parentResType ) )
        {
            if( !Constant.SECURITY.RES_TYPE_URL.equals( currResType ) )
            {
                return "4";// 操作组下必须操作URL
            }
        }
        else if( Constant.SECURITY.RES_TYPE_URL.equals( parentResType ) )
        {

            return "5";// 操作URL不可添加任何下级

        }

        return "-1";
    }

    public void swapSecurityResNote( Long currentResId, Long targetResId )
    {

        // 只有顶级机构管理员才可执行
        Long loginOrgId = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth()
            .getOrgIdentity();

        SystemOrganizationBean orgBean = orgDao.querySingleSystemOrganizationBeanById( loginOrgId );

        if( "001".equals( orgBean.getLinearOrderFlag() )
            && !SecuritySessionKeeper.getSecuritySession().getAuth().getIdentity().equals(
                orgBean.getOrgBossId() ) )
        {
            return;
        }

        SecurityResourceBean currentNodeInfoBean = null;
        SecurityResourceBean targetNodeInfoBean = null;

        String currentNodeLinearFlag = "";
        String tragetNodeLinearFlag = "";

        boolean currentNodeLastChildFlag = false;
        boolean targetNodeLastChildFlag = false;

        try
        {
            mysqlEngine.beginTransaction();

            currentNodeInfoBean = securityDao.querySingleSecurityResourceInfoBean( currentResId );

            targetNodeInfoBean = securityDao.querySingleSecurityResourceInfoBean( targetResId );

            if( currentNodeInfoBean == null )
            {
                return;
            }

            if( targetNodeInfoBean == null )
            {
                return;
            }

            currentNodeLinearFlag = currentNodeInfoBean.getLinearOrderFlag();

            tragetNodeLinearFlag = targetNodeInfoBean.getLinearOrderFlag();

            currentNodeLastChildFlag = currentNodeInfoBean.getIsLastChild().intValue() == 1 ? true
                : false;

            targetNodeLastChildFlag = targetNodeInfoBean.getIsLastChild().intValue() == 1 ? true
                : false;

            // 对于交换节点双方的孩子节点属性处理
            if( currentNodeLastChildFlag )
            {
                securityDao.updateResLastChildFlag( currentResId, Boolean.FALSE );

                securityDao.updateResLastChildFlag( targetResId, Boolean.TRUE );

            }
            else if( targetNodeLastChildFlag )
            {
                securityDao.updateResLastChildFlag( targetResId, Boolean.FALSE );
                securityDao.updateResLastChildFlag( currentResId, Boolean.TRUE );
            }

            // 交换彼此的排序信息,包括所有子孩子

            log.info( "tragetNodeLinearFlag : " + tragetNodeLinearFlag );
            log.info( "currentNodeLinearFlag : " + currentNodeLinearFlag );

            List allTargetNodeInfoList = securityDao
                .querySecurityResourceBeanByLinear( tragetNodeLinearFlag );

            List allCurrentNodeInfoList = securityDao
                .querySecurityResourceBeanByLinear( currentNodeLinearFlag );

            String linearFlag = "";
            Long resId;
            SecurityResourceBean nodeInfoBean = null;

            mysqlEngine.startBatch();
            for ( int i = 0; i < allTargetNodeInfoList.size(); i++ )
            {
                nodeInfoBean = ( SecurityResourceBean ) allTargetNodeInfoList.get( i );

                linearFlag = nodeInfoBean.getLinearOrderFlag();
                resId = nodeInfoBean.getSecResId();

                log.info( "LinearFlag replace - linearFlag:" + linearFlag
                    + ", tragetNodeLinearFlag:" + tragetNodeLinearFlag + ", currentNodeLinearFlag:"
                    + currentNodeLinearFlag );

                linearFlag = StringUtil.replaceString( linearFlag, tragetNodeLinearFlag,
                    currentNodeLinearFlag, false, true );

                log.info( "linearFlag result:" + linearFlag );

                securityDao.updateSecurityResourcelinearOrderFlagByResId( resId, linearFlag );
            }

            for ( int j = 0; j < allCurrentNodeInfoList.size(); j++ )
            {
                nodeInfoBean = ( SecurityResourceBean ) allCurrentNodeInfoList.get( j );

                linearFlag = nodeInfoBean.getLinearOrderFlag();
                resId = nodeInfoBean.getSecResId();

                log.info( "LinearFlag replace - linearFlag:" + linearFlag
                    + ", tragetNodeLinearFlag:" + tragetNodeLinearFlag + ", currentNodeLinearFlag:"
                    + currentNodeLinearFlag );

                linearFlag = StringUtil.replaceString( linearFlag, currentNodeLinearFlag,
                    tragetNodeLinearFlag, false, true );

                log.info( "linearFlag result:" + linearFlag );

                securityDao.updateSecurityResourcelinearOrderFlagByResId( resId, linearFlag );

            }
            mysqlEngine.executeBatch();

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearCache();
        }

    }

    public void swapMemberSecurityResNote( Long currentResId, Long targetResId )
    {

        // 只有顶级机构管理员才可执行
        Long loginOrgId = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth()
            .getOrgIdentity();

        SystemOrganizationBean orgBean = orgDao.querySingleSystemOrganizationBeanById( loginOrgId );

        if( "001".equals( orgBean.getLinearOrderFlag() )
            && !SecuritySessionKeeper.getSecuritySession().getAuth().getIdentity().equals(
                orgBean.getOrgBossId() ) )
        {
            return;
        }

        MemberSecurityResourceBean currentNodeInfoBean = null;
        MemberSecurityResourceBean targetNodeInfoBean = null;

        String currentNodeLinearFlag = "";
        String tragetNodeLinearFlag = "";

        boolean currentNodeLastChildFlag = false;
        boolean targetNodeLastChildFlag = false;

        try
        {
            mysqlEngine.beginTransaction();

            currentNodeInfoBean = securityDao
                .querySingleMemberSecurityResourceInfoBean( currentResId );

            targetNodeInfoBean = securityDao
                .querySingleMemberSecurityResourceInfoBean( targetResId );

            if( currentNodeInfoBean == null )
            {
                return;
            }

            if( targetNodeInfoBean == null )
            {
                return;
            }

            currentNodeLinearFlag = currentNodeInfoBean.getLinearOrderFlag();

            tragetNodeLinearFlag = targetNodeInfoBean.getLinearOrderFlag();

            currentNodeLastChildFlag = currentNodeInfoBean.getIsLastChild().intValue() == 1 ? true
                : false;

            targetNodeLastChildFlag = targetNodeInfoBean.getIsLastChild().intValue() == 1 ? true
                : false;

            // 对于交换节点双方的孩子节点属性处理
            if( currentNodeLastChildFlag )
            {
                securityDao.updateMemberResLastChildFlag( currentResId, Boolean.FALSE );

                securityDao.updateMemberResLastChildFlag( targetResId, Boolean.TRUE );

            }
            else if( targetNodeLastChildFlag )
            {
                securityDao.updateMemberResLastChildFlag( targetResId, Boolean.FALSE );
                securityDao.updateMemberResLastChildFlag( currentResId, Boolean.TRUE );
            }

            // 交换彼此的排序信息,包括所有子孩子

            log.info( "tragetNodeLinearFlag : " + tragetNodeLinearFlag );
            log.info( "currentNodeLinearFlag : " + currentNodeLinearFlag );

            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            List allTargetNodeInfoList = securityDao.queryMemberSecurityResourceBeanByLinear(
                tragetNodeLinearFlag, site.getSiteId() );

            List allCurrentNodeInfoList = securityDao.queryMemberSecurityResourceBeanByLinear(
                currentNodeLinearFlag, site.getSiteId() );

            String linearFlag = "";
            Long resId;
            MemberSecurityResourceBean nodeInfoBean = null;

            mysqlEngine.startBatch();
            for ( int i = 0; i < allTargetNodeInfoList.size(); i++ )
            {
                nodeInfoBean = ( MemberSecurityResourceBean ) allTargetNodeInfoList.get( i );

                linearFlag = nodeInfoBean.getLinearOrderFlag();
                resId = nodeInfoBean.getSecResId();

                log.info( "LinearFlag replace - linearFlag:" + linearFlag
                    + ", tragetNodeLinearFlag:" + tragetNodeLinearFlag + ", currentNodeLinearFlag:"
                    + currentNodeLinearFlag );

                linearFlag = StringUtil.replaceString( linearFlag, tragetNodeLinearFlag,
                    currentNodeLinearFlag, false, true );

                log.info( "linearFlag result:" + linearFlag );

                securityDao.updateMemberSecurityResourcelinearOrderFlagByResId( resId, linearFlag );
            }

            for ( int j = 0; j < allCurrentNodeInfoList.size(); j++ )
            {
                nodeInfoBean = ( MemberSecurityResourceBean ) allCurrentNodeInfoList.get( j );

                linearFlag = nodeInfoBean.getLinearOrderFlag();
                resId = nodeInfoBean.getSecResId();

                log.info( "LinearFlag replace - linearFlag:" + linearFlag
                    + ", tragetNodeLinearFlag:" + tragetNodeLinearFlag + ", currentNodeLinearFlag:"
                    + currentNodeLinearFlag );

                linearFlag = StringUtil.replaceString( linearFlag, currentNodeLinearFlag,
                    tragetNodeLinearFlag, false, true );

                log.info( "linearFlag result:" + linearFlag );

                securityDao.updateMemberSecurityResourcelinearOrderFlagByResId( resId, linearFlag );

            }
            mysqlEngine.executeBatch();

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearMemberCache();
        }

    }

    /**
     * 
     * @param securityResourceVO
     */
    public void addSecurityResource( SecurityResource securityResourceVO )
    {

        if( securityResourceVO == null )
        {
            return;
        }

        // 只有顶级机构管理员才可执行
        Long loginOrgId = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth()
            .getOrgIdentity();

        SystemOrganizationBean orgBean = orgDao.querySingleSystemOrganizationBeanById( loginOrgId );

        if( "001".equals( orgBean.getLinearOrderFlag() )
            && !SecuritySessionKeeper.getSecuritySession().getAuth().getIdentity().equals(
                orgBean.getOrgBossId() ) )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            securityResourceVO.setIsLeaf( Integer.valueOf( "1" ) );// 一定是叶子节点

            // 顶级菜单
            if( securityResourceVO.getParentResId().longValue() == -1 )
            {
                securityResourceVO.setIsLastChild( Integer.valueOf( "1" ) );// 一定是最后一个孩子
                securityResourceVO.setLayer( Integer.valueOf( "1" ) );// 一定是第一层

                SecurityResourceBean nearlyResBean = securityDao.queryLastResInfoBeanByLayer(
                    Integer.valueOf( "1" ), Long.valueOf( -1 ) );

                String linearFlag = null;

                if( nearlyResBean != null )
                {
                    // 若有兄弟，兄弟状态改变,且取兄弟的linearFlag
                    securityDao.updateResNodeLastChildState( nearlyResBean.getSecResId(), Short
                        .valueOf( "0" ) );
                    linearFlag = nearlyResBean.getLinearOrderFlag();
                }

                securityResourceVO.setLinearOrderFlag( StringUtil.isStringNull( linearFlag )
                    ? "001" : ServiceUtil.increaseLayerLinear( linearFlag, false ) );

            }
            else
            {// 子菜单
                Long parentResId = securityResourceVO.getParentResId();

                SecurityResourceBean parentResbean = securityDao
                    .querySingleSecurityResourceInfoBean( parentResId );

                if( parentResbean == null )
                {
                    throw new FrameworkException( "无法获取父资源,parentResId:" + parentResId );
                }

                Integer currentLayer = Integer.valueOf( parentResbean.getLayer().intValue() + 1
                    + "" );

                SecurityResourceBean nearlyResBean = securityDao.queryLastResInfoBeanByLayer(
                    currentLayer, parentResId );

                String nearlyLinearFlag = null;
                if( nearlyResBean != null )
                {
                    // 若有兄弟，兄弟状态改变,且取兄弟的linearFlag
                    securityDao.updateResNodeLastChildState( nearlyResBean.getSecResId(), Short
                        .valueOf( "0" ) );

                    nearlyLinearFlag = nearlyResBean.getLinearOrderFlag();
                }

                // 一定是最后一个孩子,
                securityResourceVO.setIsLastChild( Integer.valueOf( "1" ) );

                // layer就 为当前层
                securityResourceVO.setLayer( currentLayer );

                // 更新父节点为非叶子节点
                securityDao.updateSecurityResourceLeafStatus( parentResbean.getSecResId(), Integer
                    .valueOf( 0 ) );

                // 为空说明子没有子孩子节点,不为空则使用兄弟节点

                if( StringUtil.isStringNotNull( nearlyLinearFlag ) )
                {
                    securityResourceVO.setLinearOrderFlag( ServiceUtil.increaseLayerLinear(
                        nearlyLinearFlag, false ) );
                }
                else
                {
                    securityResourceVO.setLinearOrderFlag( ServiceUtil.increaseLayerLinear(
                        parentResbean.getLinearOrderFlag(), true ) );
                }

            }

            securityDao.save( securityResourceVO );

            mysqlEngine.commit();

        }
        finally
        {
            mysqlEngine.endTransaction();

            clearCache();
        }

    }

    /**
     * 
     * @param securityResourceVO
     */
    public void addMemberSecurityResource( MemberSecurityResource securityResourceVO )
    {

        if( securityResourceVO == null )
        {
            return;
        }

        // 只有顶级机构管理员才可执行
        Long loginOrgId = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth()
            .getOrgIdentity();

        SystemOrganizationBean orgBean = orgDao.querySingleSystemOrganizationBeanById( loginOrgId );

        if( "001".equals( orgBean.getLinearOrderFlag() )
            && !SecuritySessionKeeper.getSecuritySession().getAuth().getIdentity().equals(
                orgBean.getOrgBossId() ) )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            securityResourceVO.setIsLeaf( Integer.valueOf( "1" ) );// 一定是叶子节点

            // 顶级菜单
            if( securityResourceVO.getParentResId().longValue() == -1 )
            {
                securityResourceVO.setIsLastChild( Integer.valueOf( "1" ) );// 一定是最后一个孩子
                securityResourceVO.setLayer( Integer.valueOf( "1" ) );// 一定是第一层

                MemberSecurityResourceBean nearlyResBean = securityDao
                    .queryLastMemberResInfoBeanByLayer( Integer.valueOf( "1" ), Long.valueOf( -1 ) );

                String linearFlag = null;

                if( nearlyResBean != null )
                {
                    // 若有兄弟，兄弟状态改变,且取兄弟的linearFlag
                    securityDao.updateResNodeLastChildState( nearlyResBean.getSecResId(), Short
                        .valueOf( "0" ) );
                    linearFlag = nearlyResBean.getLinearOrderFlag();
                }

                securityResourceVO.setLinearOrderFlag( StringUtil.isStringNull( linearFlag )
                    ? "001" : ServiceUtil.increaseLayerLinear( linearFlag, false ) );

            }
            else
            {// 子菜单
                Long parentResId = securityResourceVO.getParentResId();

                MemberSecurityResourceBean parentResbean = securityDao
                    .querySingleMemberSecurityResourceInfoBean( parentResId );

                if( parentResbean == null )
                {
                    throw new FrameworkException( "无法获取父资源,parentResId:" + parentResId );
                }

                Integer currentLayer = Integer.valueOf( parentResbean.getLayer().intValue() + 1
                    + "" );

                MemberSecurityResourceBean nearlyResBean = securityDao
                    .queryLastMemberResInfoBeanByLayer( currentLayer, parentResId );

                String nearlyLinearFlag = null;
                if( nearlyResBean != null )
                {
                    // 若有兄弟，兄弟状态改变,且取兄弟的linearFlag
                    securityDao.updateMemberResNodeLastChildState( nearlyResBean.getSecResId(),
                        Short.valueOf( "0" ) );

                    nearlyLinearFlag = nearlyResBean.getLinearOrderFlag();
                }

                // 一定是最后一个孩子,
                securityResourceVO.setIsLastChild( Integer.valueOf( "1" ) );

                // layer就 为当前层
                securityResourceVO.setLayer( currentLayer );

                // 更新父节点为非叶子节点
                securityDao.updateMemberSecurityResourceLeafStatus( parentResbean.getSecResId(),
                    Integer.valueOf( 0 ) );

                // 为空说明子没有子孩子节点,不为空则使用兄弟节点

                if( StringUtil.isStringNotNull( nearlyLinearFlag ) )
                {
                    securityResourceVO.setLinearOrderFlag( ServiceUtil.increaseLayerLinear(
                        nearlyLinearFlag, false ) );
                }
                else
                {
                    securityResourceVO.setLinearOrderFlag( ServiceUtil.increaseLayerLinear(
                        parentResbean.getLinearOrderFlag(), true ) );
                }

            }

            securityDao.save( securityResourceVO );

            mysqlEngine.commit();

        }
        finally
        {
            mysqlEngine.endTransaction();

            clearMemberCache();
        }

    }

    public List retrieverSecurityResourceBeanByLayer( Long parentResId, Short useState, Short type )
    {

        return securityDao.querySecurityResourceBeanByParentResIdAndType( parentResId, useState,
            type );

    }

    public void deleteSecurityResourceByResId( Long resId )
    {
        if( resId == null )
        {
            return;
        }

        // 只有顶级机构管理员才可执行
        Long loginOrgId = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth()
            .getOrgIdentity();

        SystemOrganizationBean orgBean = orgDao.querySingleSystemOrganizationBeanById( loginOrgId );

        if( "001".equals( orgBean.getLinearOrderFlag() )
            && !SecuritySessionKeeper.getSecuritySession().getAuth().getIdentity().equals(
                orgBean.getOrgBossId() ) )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            SecurityResourceBean bean = securityDao.querySingleSecurityResourceInfoBean( resId );

            if( bean == null )
            {
                return;
            }

            // 父节点叶子属性处理，放在第一位置
            SecurityResourceBean parentResbean = securityDao
                .querySingleSecurityResourceInfoBean( resId );

            if( parentResbean != null )
            {
                Integer count = securityDao
                    .queryResourceChildrenCount( parentResbean.getSecResId() );
                if( count.intValue() == 1 )
                {
                    // 若父节点只有一个孩子，则需要将他变为叶子节点
                    securityDao.updateSecurityResourceLeafStatus( parentResbean.getSecResId(),
                        Integer.valueOf( 1 ) );
                }
            }

            // 若所删节点是最后一个孩子
            if( bean.getIsLastChild().shortValue() == 1 )
            {
                Long brotherResId = securityDao.queryUpBrotherNodeByClassIdAndTheirParentId( bean
                    .getSecResId(), bean.getParentResId() );

                if( brotherResId != null )
                {
                    securityDao.updateResNodeLastChildState( brotherResId, Short.valueOf( "1" ) );
                }
            }

            securityDao.deleteSecurityResourceByLinearFlag( bean.getLinearOrderFlag() );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            clearCache();
        }

    }

    public void deleteMemberSecurityResourceByResId( Long resId )
    {
        if( resId == null )
        {
            return;
        }

        // 只有顶级机构管理员才可执行
        Long loginOrgId = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth()
            .getOrgIdentity();

        SystemOrganizationBean orgBean = orgDao.querySingleSystemOrganizationBeanById( loginOrgId );

        if( "001".equals( orgBean.getLinearOrderFlag() )
            && !SecuritySessionKeeper.getSecuritySession().getAuth().getIdentity().equals(
                orgBean.getOrgBossId() ) )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            MemberSecurityResourceBean bean = securityDao
                .querySingleMemberSecurityResourceInfoBean( resId );

            if( bean == null )
            {
                return;
            }

            // 父节点叶子属性处理，放在第一位置
            MemberSecurityResourceBean parentResbean = securityDao
                .querySingleMemberSecurityResourceInfoBean( resId );

            if( parentResbean != null )
            {
                Integer count = securityDao.queryMemberResourceChildrenCount( parentResbean
                    .getSecResId() );
                if( count.intValue() == 1 )
                {
                    // 若父节点只有一个孩子，则需要将他变为叶子节点
                    securityDao.updateMemberSecurityResourceLeafStatus(
                        parentResbean.getSecResId(), Integer.valueOf( 1 ) );
                }
            }

            // 若所删节点是最后一个孩子
            if( bean.getIsLastChild().shortValue() == 1 )
            {
                Long brotherResId = securityDao.queryMemberUpBrotherNodeByClassIdAndTheirParentId(
                    bean.getSecResId(), bean.getParentResId() );

                if( brotherResId != null )
                {
                    securityDao.updateMemberResNodeLastChildState( brotherResId, Short
                        .valueOf( "1" ) );
                }
            }

            securityDao.deleteMemberSecurityResourceByLinearFlag( bean.getLinearOrderFlag() );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearMemberCache();
        }

    }

    public List retrieveSecurityResourceBeanByLinear( String linear )
    {
        return securityDao.querySecurityResourceBeanByLinear( linear );
    }

    public List retrieveMemberSecurityResourceBeanByLinear( String linear )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        return securityDao.queryMemberSecurityResourceBeanByLinear( linear, site.getSiteId() );
    }

    public List retrieveSecurityResourceBeanByParentLinear( String linear )
    {
        if( "000".equals( linear ) )
        {
            return securityDao.querySecurityResourceBeanByLayer( Integer.valueOf( 1 ) );
        }

        return securityDao.querySecurityResourceBeanByParentLinear( linear );
    }

    public List retrieveMemberSecurityResourceBeanByParentLinear( String linear )
    {

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( "000".equals( linear ) )
        {
            return securityDao.queryMemberSecurityResourceBeanByLayer( Integer.valueOf( 1 ), site
                .getSiteId() );
        }

        return securityDao.queryMemberSecurityResourceBeanByParentLinear( linear );
    }

    public List retrieveSecurityResourceBeanByLayer( Integer layer )
    {
        return securityDao.querySecurityResourceBeanByLayer( layer );
    }

    /**
     * 获取所有顶级，模块，菜单资源
     * 
     * @return
     */
    public List retrieveSecurityResourceBeanMainMode()
    {
        return securityDao.querySecurityResourceBeanMainMode();
    }

    /**
     * 获取会员所有顶级，模块，菜单资源
     * 
     * @return
     */
    public List retrieveMemberSecurityResourceBeanMainMode()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        return securityDao.queryMemberSecurityResourceBeanMainMode( site.getSiteId() );
    }

    /**
     * 取资源bean,按照资源保护类型,此方法不按照资源使用状态条件
     * 
     * @param dataProtectType
     * @return
     */
    public List retrieveSecurityResourceBeanByProtectType( Integer dataProtectType )
    {
        if( SecuritrConstant.ALL_PROTECT_TYPE.equals( dataProtectType ) )
        {
            return securityDao.queryAllSecurityResourceBean();
        }

        return securityDao.querySecurityResourceBeanByProtectType( dataProtectType );
    }

    /**
     * 取资源bean,按照资源保护类型,此方法不按照资源使用状态条件
     * 
     * @param dataProtectType
     * @return
     */
    public List retrieveMemberSecurityResourceBeanByProtectType( Integer dataProtectType )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( SecuritrConstant.ALL_PROTECT_TYPE.equals( dataProtectType ) )
        {
            return securityDao.queryAllMemberSecurityResourceBean( site.getSiteId() );
        }

        return securityDao.queryMemberSecurityResourceBeanByProtectType( dataProtectType, site
            .getSiteId() );
    }

    public List retrieveAllSecurityResourceBean()
    {
        return securityDao.queryAllSecurityResourceBean();
    }

    public List retrieveAllMemberSecurityResourceBean()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        return securityDao.queryAllMemberSecurityResourceBean( site.getSiteId() );
    }

    public void editSecurityResource( SecurityResource resource )
    {
        if( resource == null || !SecuritySessionKeeper.getSecuritySession().isManager() )
        {
            return;
        }

        // 只有顶级机构管理员才可执行
        Long loginOrgId = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth()
            .getOrgIdentity();

        SystemOrganizationBean orgBean = orgDao.querySingleSystemOrganizationBeanById( loginOrgId );

        if( "001".equals( orgBean.getLinearOrderFlag() )
            && !SecuritySessionKeeper.getSecuritySession().getAuth().getIdentity().equals(
                orgBean.getOrgBossId() ) )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            securityDao.updateSecurityResourceSimple( resource );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            clearCache();
        }
    }

    public void editMemberSecurityResource( MemberSecurityResource resource )
    {
        if( resource == null || !SecuritySessionKeeper.getSecuritySession().isManager() )
        {
            return;
        }

        // 只有顶级机构管理员才可执行
        Long loginOrgId = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth()
            .getOrgIdentity();

        SystemOrganizationBean orgBean = orgDao.querySingleSystemOrganizationBeanById( loginOrgId );

        if( "001".equals( orgBean.getLinearOrderFlag() )
            && !SecuritySessionKeeper.getSecuritySession().getAuth().getIdentity().equals(
                orgBean.getOrgBossId() ) )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            securityDao.updateMemberSecurityResourceSimple( resource );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearMemberCache();
        }
    }

    public void addSystemRole( SystemRole role )
    {
        if( role == null )
        {
            return;
        }

        if( "系统管理员".equals( role.getRoleName() ) )
        {
            role.setRoleName( "系统管理员(2)" );
        }

        securityDao.saveRole( role );

        ListContentClassInfoTreeController.resizeSiteContentClassCache();
        ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
    }

    public void addMemberRole( MemberRole role )
    {
        if( role == null )
        {
            return;
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        role.setSiteId( site.getSiteId() );

        securityDao.saveMemberRole( role );
    }

    public void editSystemRole( SystemRole role )
    {
        if( role == null )
        {
            return;
        }

        // 检查root role,不可删除
        SystemRoleBean rootRole = securityDao.querySingleAdminRole();

        if( rootRole != null && !rootRole.getRoleId().equals( role.getRoleId() ) )
        {
            if( "系统管理员".equals( role.getRoleName() ) )
            {
                role.setRoleName( "系统管理员(2)" );
            }
        }
        else
        {
            role.setRoleName( "系统管理员" );
            role.setRoleDesc( "默认管理员，不可删除" );
        }

        securityDao.updateSystemRole( role );

        ListContentClassInfoTreeController.resizeSiteContentClassCache();
        ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
    }

    public void editMemberRole( MemberRole role )
    {
        if( role == null )
        {
            return;
        }

        securityDao.updateMemberRole( role );

    }

    public List retrieveSiteBeanByRoleId( Long roleId )
    {
        return securityDao.querySiteBeanByRoleId( roleId );
    }

    /**
     * 维护角色授权
     * 
     * @param role
     * @param resIdSet
     */
    public void maintainSystemRoleAuthorization( SystemRole role, String[] resIdArray,
        String[] checkAuditClassSec, List accProtectBeanList, Long siteId )
    {
        if( role == null || resIdArray == null || accProtectBeanList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            UpdateState updateState = securityDao.saveRole( role );

            if( !updateState.haveKey() )
            {
                log.error( "持久层增加基础角色信息失败,无法添加数据，Role:" + role );
                throw new FrameworkException( "增加基础角色信息失败" );
            }

            Long roleId = Long.valueOf( updateState.getKey() );

            String resStr = null;

            Set resIdSet = new HashSet();

            for ( int i = 0; i < resIdArray.length; i++ )
            {
                resStr = resIdArray[i];
                if( StringUtil.isStringNotNull( resStr ) )
                {
                    resIdSet.add( Long.valueOf( StringUtil.getLongValue( resStr, -1 ) ) );
                }
            }

            // 以下涉及批处理持久层操作
            mysqlEngine.startBatch();

            List resList;
            String[] taregetAccIdList;
            SecurityResourceBean resBean;
            // 每一种细粒度控制类型
            // for ( int j = 0; j < accProtectBeanList.size(); j++ )
            // {
            // ProtectResourceInfoBean bean = ( ProtectResourceInfoBean )
            // accProtectBeanList
            // .get( j );
            //
            // // resList = bean.getProtectResList();
            // //xtaregetAccIdList = bean.getSelectedTragetDataAccId();
            // // 每一个受控制资源
            // for ( int k = 0; k < resList.size(); k++ )
            // {
            // resBean = ( SecurityResourceBean ) resList.get( k );
            // // 每一个目标ACC
            // // acc值来自页面,要做重复值处理
            // for ( int h = 0; h < taregetAccIdList.length; h++ )
            // {
            // securityDao.saveRoleAssociatedAccResource( roleId,
            // resBean.getSecResId(), taregetAccIdList[h] );
            // }
            // }
            //
            // }

            Iterator resIt = resIdSet.iterator();

            while ( resIt.hasNext() )
            {
                Long resId = ( Long ) resIt.next();

                securityDao.saveRoleAssociatedResource( roleId, resId );
            }

            // 审核权目前单独记录

            String resAuditStr = null;

            Set resAuditSet = new HashSet();

            for ( int i = 0; i < checkAuditClassSec.length; i++ )
            {
                resAuditStr = checkAuditClassSec[i];
                if( StringUtil.isStringNotNull( resAuditStr ) )
                {
                    resAuditSet.add( Long.valueOf( StringUtil.getLongValue( resAuditStr, -1 ) ) );
                }
            }

            Iterator auditResIt = resAuditSet.iterator();
            Long auditResId = null;
            while ( auditResIt.hasNext() )
            {
                auditResId = ( Long ) auditResIt.next();

                securityDao.saveRoleAssociatedAudit( roleId, auditResId );
            }

            mysqlEngine.executeBatch();

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public List retrieveAllSystemRoleBean( Integer useState, String orgCode )
    {
        if( Constant.SECURITY.RES_IS_ALL_USE_STATE.equals( useState ) )
        {
            SystemOrganizationBean orgBean = orgDao
                .querySingleSystemOrganizationBeanByLinear( orgCode );

            if( orgBean == null )
            {
                return null;
            }

            return securityDao.queryAllSystemRoleBean( orgDao
                .querySystemOrganizationBeanByLinearFlag( orgBean.getLinearOrderFlag() ) );
        }

        return securityDao.queryAllSystemRoleBeanByUseMode( useState );
    }

    public List retrieveAllSystemRoleBean( String orgCode, Long start, Integer size )
    {
        SystemOrganizationBean orgBean = orgDao.querySingleSystemOrganizationBeanByLinear( orgCode );

        if( orgBean == null )
        {
            return null;
        }

        return securityDao.queryAllSystemRoleBean( orgDao
            .querySystemOrganizationBeanByLinearFlag( orgBean.getLinearOrderFlag() ), start, size );
    }

    public List retrieveAllMemberRoleBean()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( site == null )
        {
            return null;
        }

        return securityDao.queryAllMemberRoleBean( site.getSiteId() );

    }

    public List retrieveSystemRoleBeanByOrgId( Long orgId )
    {
        return securityDao.querySystemRoleBeanByOrgId( orgId );
    }

    public List retrieveSystemRoleBeanByOrgId( Long orgId, Long start, Integer size )
    {
        return securityDao.querySystemRoleBeanByOrgId( orgId, start, size );
    }

    public List retrieveSystemRoleBeanByUserId( Long userId )
    {
        return securityDao.querySystemRoleBeanByUserId( userId );
    }

    public List retrieveMemberRoleBeanByUserId( Long userId )
    {
        return securityDao.queryMemberRoleBeanByUserId( userId );
    }

    public Object retrieveSingleSystemRoleBean( Long roleId )
    {
        return securityDao.querySingleSystemRoleBean( roleId );
    }

    public Object retrieveSingleMemberRoleBean( Long roleId )
    {
        return securityDao.querySingleMemberRoleBean( roleId );
    }

    /**
     * 获取指定角色对应的所有系统受保护资源信息
     * 
     * @param roleId
     * @return
     */
    public List retrieveRoleHaveHisResourceBeanByRoleId( Long roleId )
    {
        List resourceBeanList = securityDao.queryRoleHaveHisResourceBeanByRoleId( roleId );

        return resourceBeanList;
    }

    /**
     * 获取指定会员组对应的所有系统受保护资源信息
     * 
     * @param roleId
     * @return
     */
    public List retrieveMemberRoleHaveHisResourceBeanByRoleId( Long roleId )
    {
        List resourceBeanList = securityDao.queryMemberRoleHaveHisResourceBeanByRoleId( roleId );

        return resourceBeanList;
    }

    /**
     * 获取指定角色对应的所有系统受保护资源ID
     * 
     * @param roleId
     * @return
     */
    public List retrieveRoleHaveHisResourceIdByRoleId( Long roleId )
    {
        List resourceBeanList = securityDao.queryRoleHaveHisResourceIdByRoleId( roleId );

        return resourceBeanList;
    }

    /**
     * 获取指定角色对应的资源,根据指定的父资源标志
     * 
     * @param roleId
     * @return
     */
    public List retrieveRoleHaveHisResourceBeanByRoleId( Long roleId, Long parentResId )
    {
        List resourceBeanList = securityDao.queryRoleHaveHisResourceBeanByRoleId( roleId,
            parentResId );

        return resourceBeanList;
    }

    /**
     * 获取指定角色对应的资源,根据指定的父资源标志集合
     * 
     * @param roleId
     * @return
     */
    public List retrieveRoleHaveHisResourceBeanByRoleArray( String roleSqlHelper, Long parentResId,
        Integer resType )
    {
        List resourceBeanList = securityDao.queryRoleHaveHisResourceBeanByRoleArray( roleSqlHelper,
            parentResId, resType );

        return resourceBeanList;
    }

    /**
     * 获取指定角色对应的资源,根据指定的父资源标志集合
     * 
     * @param roleId
     * @return
     */
    public List retrieveMemberRoleHaveHisResourceBeanByRoleArray( String roleSqlHelper,
        Long parentResId, Integer resType )
    {
        List resourceBeanList = securityDao.queryMemberRoleHaveHisResourceBeanByRoleArray(
            roleSqlHelper, parentResId, resType );

        return resourceBeanList;
    }

    /**
     * 获取指定角色对应的资源
     * 
     * @param roleId
     * @return
     */
    public List retrieveRoleHaveHisResourceBeanByRoleArray( String roleSqlHelper, Integer resType )
    {
        List resourceBeanList = securityDao.queryRoleHaveHisResourceBeanByRoleArray( roleSqlHelper,
            resType );

        return resourceBeanList;
    }

    /**
     * 获取指定角色对应的资源
     * 
     * @param roleId
     * @return
     */
    public List retrieveMemberRoleHaveHisResourceBeanByRoleArray( String roleSqlHelper,
        Integer resType )
    {
        List resourceBeanList = securityDao.queryMemberRoleHaveHisResourceBeanByRoleArray(
            roleSqlHelper, resType );

        return resourceBeanList;
    }

    public Set retrieveAllSecurityResourceInfo()
    {

        // Set resultSet = new HashSet();
        // try
        // {
        // mysqlEngine.beginTransaction();
        //
        // // 所有被保护的资源信息
        //
        // Map resInfo = null;
        // String resName = null;
        // Long resId = null;
        // // for ( int i = 0; i < securityResInfoList.size(); i++ )
        // // {
        // // resInfo = ( Map ) securityResInfoList.get( i );
        // // resId = ( Long ) resInfo.get( "secResId" );
        // // resName = ( String ) resInfo.get( "resourceName" );
        // // resultSet.add( resName.trim() );
        // // }
        //
        // mysqlEngine.commit();
        // }
        // finally
        // {
        // mysqlEngine.endTransaction();
        // }
        List securityResInfoList = securityDao.queryAllSecurityResourceIds();
        return new HashSet( securityResInfoList );
    }

    public Set retrieveTargetResourceRelateRoleIdSetByKey( String key )
    {
        return new HashSet( securityDao.queryResourceRelateRoleByResource( key ) );
    }

    public Set retrieveMemberTargetResourceRelateRoleIdSetByKey( String key )
    {
        return new HashSet( securityDao.queryMemberResourceRelateRoleByResource( key ) );
    }

    public Set retrieveTargetResourceRelateRoleIdSetByKeyAndAccInfo( Long resId, Long accId,
        Long accSymbolId )
    {
        if( resId.longValue() < 0 )
        {
            return new HashSet( 1 );
        }

        return new HashSet( securityDao.queryResourceRelateRoleByResourceAndAccInfo( resId, accId,
            accSymbolId ) );
    }

    public ResourceState getSecurityResource( String uriKey )
    {
        ResourceState resState = new ResourceState();

        String allResMapkey = "allSecurityResourceMapCache";

        String allMemberResMapkey = "allMemberSecurityResourceMapCache";

        Cache resMapCache = ( Cache ) cacheManager.get( allResMapkey );

        Cache memberResMapCache = ( Cache ) cacheManager.get( allMemberResMapkey );

        Map resInfoMap = ( Map ) resMapCache.getEntry( allResMapkey );

        Map memberResInfoMap = ( Map ) memberResMapCache.getEntry( allMemberResMapkey );

        if( resInfoMap == null )
        {
            resInfoMap = securityDao.queryAllSecurityResourceBeanMap();

            resMapCache.putEntry( allResMapkey, resInfoMap );
        }

        if( memberResInfoMap == null )
        {
            memberResInfoMap = securityDao.queryAllMemberSecurityResourceBeanMap();

            memberResMapCache.putEntry( allMemberResMapkey, memberResInfoMap );
        }

        // resInfoMap = securityDao.querySecurityResourceInfoByUriKey( uriKey );

        if( resInfoMap.containsKey( uriKey ) )// 当前访问目标在后端受保护资源中
        {
            Map resInfo = ( Map ) resInfoMap.get( uriKey );
            resState.setTargetResId( ( Long ) resInfo.get( "secResId" ) );
            resState.setAccSymbol( ( String ) resInfo.get( "accSymbol" ) );
            resState.setDateProtectType( ( Integer ) resInfo.get( "dataProtectType" ) );
            resState.setAccSymbolId( ( Long ) resInfo.get( "dataTypeId" ) );
            resState.setAccBehaviorClass( ( String ) resInfo.get( "accBehaviorClass" ) );
        }
        else if( memberResInfoMap.containsKey( uriKey ) )// 当前访问目标在前端受保护资源中
        {
            Map resInfo = ( Map ) memberResInfoMap.get( uriKey );
            resState.setTargetResId( ( Long ) resInfo.get( "secResId" ) );
            resState.setAccSymbol( ( String ) resInfo.get( "accSymbol" ) );
            resState.setDateProtectType( ( Integer ) resInfo.get( "dataProtectType" ) );
            resState.setAccSymbolId( ( Long ) resInfo.get( "dataTypeId" ) );
            resState.setAccBehaviorClass( ( String ) resInfo.get( "accBehaviorClass" ) );
        }

        else
        {
            resState.setIsProtectResource( Boolean.FALSE );
        }

        log.info( "[Service] getSecurityResource()  ResourceState:" + resState );

        return resState;
    }

    public List retrieveUserHaveResourceBeanByUserId( Long userId )
    {
        return securityDao.queryUserHaveResourceBeanByUserId( userId );
    }

    public IUser obtainUser( String name ) throws SecurityException, DataAccessException
    {
        if( StringUtil.isStringNull( name ) )
        {
            return null;
        }

        SystemLoginUser systemUser = null;

        try
        {
            mysqlEngine.beginTransaction();

            systemUser = securityDao.queryLoginUserInfoBeanByName( name );

            if( systemUser == null )
            {
                return null;
            }

            List roleList = securityDao.queryUserHaveSecurityRoleByUserId( ( Long ) systemUser
                .getIdentity() );

            systemUser.setRoleIdList( roleList );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        return systemUser;
    }

    public List retrieveUserHaveSecurityRoleByUserId( Long userId )
    {
        List roleList = securityDao.queryUserHaveSecurityRoleByUserId( userId );

        return roleList;
    }

    /**
     * 更新指定的角色所拥有的受保护资源，以及角色信息。
     * 
     * @param targetRole
     * @param currentCheckResIdSet
     */
    public void updateRoleAndItsResourceByRoleIdandNewrRes( Long roleId,
        String[] checkRoleSiteManagerArray, String[] currentCheckResIdArray, Map accResInfoMap,
        Long siteId )
    {
        log.info( "[updateRoleAndItsResourceByRoleIdandNewrRes] role:" + roleId );

        if( roleId == null || roleId.longValue() < 0 || currentCheckResIdArray == null
            || accResInfoMap == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            // 更新角色相关的保护资源信息
            securityDao.deleteRoleHaveHisResourceByRoleId( roleId );

            // 更新细粒度资源保护信息
            securityDao.deleteRoleHaveHisAccResourceByRoleId( roleId, siteId );

            // 改动：现设计变动为不需要在角色层进行站点整体授权 2013-8-2 by wu
            // 更新细粒度审核资源
            // securityDao.deleteRoleHaveHisAuditAccByRoleId( roleId );
            //
            // /**
            // * 站点管理粗粒度资源
            // */
            // // 删除原资源
            // securityDao.deleteRoleRelateSite( roleId );
            //
            // Set manageSiteIdSet = new HashSet();
            // Long managerSiteId = null;
            //
            // for ( int i = 0; i < checkRoleSiteManagerArray.length; i++ )
            // {
            // managerSiteId = Long.valueOf( StringUtil.getLongValue(
            // ( String ) checkRoleSiteManagerArray[i], -1 ) );
            //
            // if( managerSiteId.longValue() > 0 )
            // {
            // manageSiteIdSet.add( managerSiteId );
            // securityDao.saveRoleRelateSite( roleId, managerSiteId );
            // }
            // }

            /**
             * 站点操作粗粒度资源
             */
            String resStr = null;

            Set checkResIdSet = new HashSet();
            Long targetRoleResId = null;
            Integer type = null;
            String[] resInfo = null;
            List childRes = null;
            for ( int i = 0; i < currentCheckResIdArray.length; i++ )
            {
                resStr = currentCheckResIdArray[i];
                if( StringUtil.isStringNotNull( resStr ) )
                {
                    resInfo = StringUtil.split( resStr, "-" );

                    targetRoleResId = Long.valueOf( StringUtil.getLongValue( resInfo[0], -1 ) );
                    type = Integer.valueOf( StringUtil.getIntValue( resInfo[1], -1 ) );

                    if( targetRoleResId.longValue() < 0 || type.intValue() < 0 )
                    {
                        log.error( "当前资源ID非法,resId:" + resStr );
                        throw new FrameworkException( "资源ID信息非法!" );
                    }

                    // 组合类型,所有子资源需要被授权
                    if( Constant.SECURITY.RES_TYPE_GROUP.equals( type ) )
                    {
                        childRes = securityDao.querySecurityResourceBeanByParentResId(
                            targetRoleResId, Constant.SECURITY.RES_IS_USE_STATE,
                            Constant.SECURITY.DATA_P_TYPE_SIMPLE );
                        for ( int j = 0; j < childRes.size(); j++ )
                        {
                            checkResIdSet.add( ( ( SecurityResourceBean ) childRes.get( j ) )
                                .getSecResId() );
                        }
                    }

                    if( Constant.SECURITY.RES_TYPE_ACT_LINK.equals( type ) )
                    {
                        // 菜单所属上级分类模块授权
                        checkResIdSet.add( ( ( SecurityResourceBean ) securityDao
                            .querySingleSecurityResourceBean( targetRoleResId,
                                Constant.SECURITY.RES_IS_USE_STATE,
                                Constant.SECURITY.DATA_P_TYPE_SIMPLE ) ).getParentResId() );
                    }

                    checkResIdSet.add( targetRoleResId );
                }
            }

            Iterator resIter = checkResIdSet.iterator();

            mysqlEngine.startBatch();
            while ( resIter.hasNext() )
            {
                Long targetResId = ( Long ) resIter.next();

                securityDao.saveRoleAssociatedResource( roleId, targetResId );
            }

            /**
             * 细粒度资源
             */
            List resList;
            String[] taregetAccIdList;
            Map resINfoMap = null;
            Iterator infoIter = null;
            SecurityResourceBean resBean;
            SecurityResourceBean childResBean;

            // 每一种细粒度控制类型

            resINfoMap = accResInfoMap;

            infoIter = resINfoMap.keySet().iterator();

            // 每一组受控资源
            while ( infoIter.hasNext() )
            {
                // 每一个真实资源
                resBean = ( SecurityResourceBean ) infoIter.next();
                taregetAccIdList = ( String[] ) resINfoMap.get( resBean );

                // 若为组,则查找其所有子资源
                if( Constant.SECURITY.RES_TYPE_GROUP.equals( resBean.getResourceType() ) )
                {
                    resList = securityDao.querySecurityResourceBeanByParentResId( resBean
                        .getSecResId(), Constant.SECURITY.RES_IS_USE_STATE,
                        Constant.SECURITY.DATA_P_TYPE_ACC );

                    for ( int k = 0; k < resList.size(); k++ )
                    {
                        childResBean = ( SecurityResourceBean ) resList.get( k );
                        for ( int m = 0; m < taregetAccIdList.length; m++ )
                        {
                            securityDao.saveRoleAssociatedAccResource( roleId, childResBean
                                .getSecResId(), taregetAccIdList[m], resBean.getDataTypeSecId(),
                                siteId );
                        }
                    }
                }

                for ( int m = 0; m < taregetAccIdList.length; m++ )
                {
                    securityDao.saveRoleAssociatedAccResource( roleId, resBean.getSecResId(),
                        taregetAccIdList[m], resBean.getDataTypeSecId(), siteId );
                }

            }

            mysqlEngine.executeBatch();

            // 改动：现设计变动为不需要在角色层进行站点整体授权 2013-8-2 by wu
            // 在最后删除不在当前site授权的所有各种权限
            // securityDao.deleteRoleRelateAccResByNotInSite(
            // ( Long[] ) manageSiteIdSet.toArray( new Long[] {} ), roleId );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
        }
    }

    /**
     * 更新指定的角色所拥有的受保护资源，以及角色信息,此为添加的新栏目或专题使用。
     * 
     * @param targetRole
     * @param currentCheckResIdSet
     */
    public void updateRoleAndItsResourceByRoleIdandNewrResForNewClassMode( Long roleId,
        Map accResInfoMap, Long siteId )
    {
        log.info( "[updateRoleAndItsResourceByRoleIdandNewrResForNewClassMode] role:" + roleId );

        if( roleId == null || roleId.longValue() < 0 || accResInfoMap == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            /**
             * 细粒度资源
             */
            List resList;
            String[] taregetAccIdList;
            Map resINfoMap = null;
            Iterator infoIter = null;
            SecurityResourceBean resBean;
            SecurityResourceBean childResBean;

            // 每一种细粒度控制类型

            resINfoMap = accResInfoMap;

            infoIter = resINfoMap.keySet().iterator();

            // 每一组受控资源
            while ( infoIter.hasNext() )
            {
                // 每一个真实资源
                resBean = ( SecurityResourceBean ) infoIter.next();
                taregetAccIdList = ( String[] ) resINfoMap.get( resBean );

                // 若为组,则查找其所有子资源
                if( Constant.SECURITY.RES_TYPE_GROUP.equals( resBean.getResourceType() ) )
                {
                    resList = securityDao.querySecurityResourceBeanByParentResId( resBean
                        .getSecResId(), Constant.SECURITY.RES_IS_USE_STATE,
                        Constant.SECURITY.DATA_P_TYPE_ACC );

                    for ( int k = 0; k < resList.size(); k++ )
                    {
                        childResBean = ( SecurityResourceBean ) resList.get( k );
                        for ( int m = 0; m < taregetAccIdList.length; m++ )
                        {
                            securityDao.saveRoleAssociatedAccResource( roleId, childResBean
                                .getSecResId(), taregetAccIdList[m], resBean.getDataTypeSecId(),
                                siteId );
                        }
                    }
                }

                for ( int m = 0; m < taregetAccIdList.length; m++ )
                {
                    securityDao.saveRoleAssociatedAccResource( roleId, resBean.getSecResId(),
                        taregetAccIdList[m], resBean.getDataTypeSecId(), siteId );
                }

            }

            mysqlEngine.executeBatch();

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
        }
    }

    /**
     * 更新指定的会员角色所拥有的受保护资源，以及角色信息。
     * 
     * @param targetRole
     * @param currentCheckResIdSet
     */
    public void updateMemberRoleAndItsResourceByRoleIdandNewrRes( Long roleId,
        String[] currentCheckResIdArray )
    {
        log.info( "[updateMemberRoleAndItsResourceByRoleIdandNewrRes] role:" + roleId );

        if( roleId == null || roleId.longValue() < 0 || currentCheckResIdArray == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            // 更新会员角色相关的保护资源信息
            securityDao.deleteMemberRoleHaveHisResourceByRoleId( roleId );

            /**
             * 站点操作粗粒度资源
             */
            String resStr = null;

            Set checkResIdSet = new HashSet();
            Long targetRoleResId = null;
            Integer type = null;
            String[] resInfo = null;
            List childRes = null;
            for ( int i = 0; i < currentCheckResIdArray.length; i++ )
            {
                resStr = currentCheckResIdArray[i];
                if( StringUtil.isStringNotNull( resStr ) )
                {
                    resInfo = StringUtil.split( resStr, "-" );

                    targetRoleResId = Long.valueOf( StringUtil.getLongValue( resInfo[0], -1 ) );
                    type = Integer.valueOf( StringUtil.getIntValue( resInfo[1], -1 ) );

                    if( targetRoleResId.longValue() < 0 || type.intValue() < 0 )
                    {
                        log.error( "当前资源ID非法,resId:" + resStr );
                        throw new FrameworkException( "资源ID信息非法!" );
                    }

                    // 组合类型,所有子资源需要被授权
                    if( Constant.SECURITY.RES_TYPE_GROUP.equals( type ) )
                    {
                        childRes = securityDao.queryMemberSecurityResourceBeanByParentResId(
                            targetRoleResId, Constant.SECURITY.RES_IS_USE_STATE,
                            Constant.SECURITY.DATA_P_TYPE_SIMPLE );
                        for ( int j = 0; j < childRes.size(); j++ )
                        {
                            checkResIdSet.add( ( ( MemberSecurityResourceBean ) childRes.get( j ) )
                                .getSecResId() );
                        }
                    }

                    if( Constant.SECURITY.RES_TYPE_ACT_LINK.equals( type ) )
                    {
                        // 菜单所属上级分类模块授权
                        checkResIdSet.add( ( ( MemberSecurityResourceBean ) securityDao
                            .querySingleMemberSecurityResourceBean( targetRoleResId,
                                Constant.SECURITY.RES_IS_USE_STATE,
                                Constant.SECURITY.DATA_P_TYPE_SIMPLE ) ).getParentResId() );
                    }

                    checkResIdSet.add( targetRoleResId );
                }
            }

            Iterator resIter = checkResIdSet.iterator();

            mysqlEngine.startBatch();
            while ( resIter.hasNext() )
            {
                Long targetResId = ( Long ) resIter.next();

                securityDao.saveMemberRoleAssociatedResource( roleId, targetResId );
            }

            mysqlEngine.executeBatch();

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

        }
    }

    public void addSystemUser( SystemUser user, List roleIdList )
    {
        if( roleIdList == null )
        {
            log.error( "[addSystemUser] roleIdArray为null!" );
            return;
        }

        if( user == null )
        {
            log.error( "[addSystemUser] 用户信息对象为null!" );
            return;
        }

        // 创建者
        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        Long id = ( Long ) session.getAuth().getIdentity();

        try
        {
            mysqlEngine.beginTransaction();

            SystemUserBean creator = securityDao.querySingleSystemUserBeanById( id );

            // 若管理员不存在,立即中止
            if( creator == null )
            {
                return;
            }

            UpdateState updateState = securityDao.saveUser( user );

            if( !updateState.haveKey() )
            {
                log.error( "持久层无法增加新用户信息,user:" + user );
                throw new FrameworkException( "添加用户失败!" );
            }

            Set orgIdSet = new HashSet();

            Long roleId = null;
            for ( int i = 0; i < roleIdList.size(); i++ )
            {
                roleId = ( Long ) roleIdList.get( i );

                orgIdSet.add( securityDao.querySingleSystemRoleBean( roleId ).getOrgId() );

                securityDao.saveUserAssociatedRole( Long.valueOf( updateState.getKey() ), roleId );
            }

            // String relateOrg = StringUtil.changeStringArrayToStr(
            // ( String[] ) orgIdSet.toArray( new String[] {} ), "," );
            //
            // securityDao.updateSystemUserBeanRelateOrgId( Long
            // .valueOf( updateState.getKey() ), relateOrg );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    /**
     * 根据条件获取系统用户信息,ID条件为优先选取条件
     * 
     * @param userName
     * @param userId
     * @return
     */
    public SystemUserBean retrieveSingleSystemUserBean( String userName, Long userId )
    {
        if( userId != null )
        {
            return securityDao.querySingleSystemUserBeanById( userId );
        }

        return securityDao.querySystemUserBeanByName( userName );
    }

    public List retrieveAllSystemUserBean( Long roleId, Long excludeRoleId, String orgCode )
    {
        if( roleId != null )
        {
            return securityDao.querySystemUserBeanByRelateRoleId( roleId );
        }

        if( excludeRoleId != null && excludeRoleId.longValue() > 0 )
        {
            SystemRoleBean roleBean = securityDao.querySingleSystemRoleBean( excludeRoleId );

            SystemOrganizationBean orgBean = orgDao.querySingleSystemOrganizationBeanById( roleBean
                .getOrgId() );

            List orgIdList = new ArrayList();

            while ( orgBean != null )
            {
                orgIdList.add( orgBean.getLinearOrderFlag() );

                orgBean = orgDao.querySingleSystemOrganizationBeanById( orgBean.getParentId() );
            }

            return securityDao.querySystemUserBeanButExcludeRoleHaveUser( orgIdList, excludeRoleId );
        }

        return securityDao.queryAllSystemUserBeanByRelateOrg( orgCode );
    }

    public Long retrieveAllSystemMemberBeanCount( Long excludeRoleId )
    {

        if( excludeRoleId == null || excludeRoleId.longValue() < 1 )
        {
            return Long.valueOf( 0 );
        }

        return securityDao.querySystemMemberBeanButExcludeRoleHaveUserCount( excludeRoleId );

    }

    public Object getAllSystemMemberBeanForTag( String excludeRoleId, String pageNumber,
        String pageSize, String flag, String key )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long exRoleId = Long.valueOf( StringUtil.getLongValue( excludeRoleId, -1 ) );

        int pn = StringUtil.getIntValue( pageNumber, 1 );

        int size = StringUtil.getIntValue( pageSize, 11 );

        Map res = null;

        List result = new ArrayList();

        if( "name".equals( flag ) )
        {
            if( StringUtil.isStringNotNull( key ) )
            {
                res = memberDao.queryMemeberByName( site.getSiteId(), key );

                if( !res.isEmpty() )
                {
                    result.add( res );
                }
            }

        }
        else if( "trueName".equals( flag ) )
        {
            if( StringUtil.isStringNotNull( key ) )
            {
                result = memberDao.queryMemeberByTrueName( site.getSiteId(), key );
            }

        }
        else if( "email".equals( flag ) )
        {
            if( StringUtil.isStringNotNull( key ) )
            {
                result = memberDao.queryMemeberByEmail( site.getSiteId(), key );
            }

        }
        else
        {

            Page pageInfo = null;

            Long count = retrieveAllSystemMemberBeanCount( exRoleId );

            pageInfo = new Page( size, count.intValue(), pn );

            result = retrieveAllSystemMemberBean( exRoleId, Long
                .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );

            return new Object[] { result, pageInfo };
        }

        return result;
    }

    public List retrieveAllSystemMemberBean( Long excludeRoleId, Long start, Integer size )
    {

        if( excludeRoleId == null || excludeRoleId.longValue() < 1 )
        {
            return null;
        }

        return securityDao.querySystemMemberBeanButExcludeRoleHaveUser( excludeRoleId, start, size );
    }

    public List retrieveAllSystemUserBeanByRelateOrg( String orgCode )
    {
        return securityDao.queryAllSystemUserBeanByRelateOrg( orgCode );
    }

    public List retrieveAllSystemUserBeanByRelateOrg( String orgCode, Long start, Integer size )
    {
        return securityDao.queryAllSystemUserBeanByRelateOrg( orgCode, start, size );
    }

    public List retrieveAllSystemUserBeanByOrgCode( String orgCode )
    {
        return securityDao.queryAllSystemUserBeanByOrgCode( orgCode );
    }

    public List retrieveAllSystemUserBeanByOrgCode( String orgCode, Long start, Integer size )
    {
        return securityDao.queryAllSystemUserBeanByOrgCode( orgCode, start, size );
    }

    public List retrieveAllSystemUserBean()
    {
        return securityDao.queryAllSystemUserBean();
    }

    public List retrieveAllSystemOrgBossUserBean()
    {
        return securityDao.queryAllSystemOrgBossUserBean();
    }

    /**
     * 更新系统用户信息和其相关资源
     * 
     * @param editUser
     * @param roleIdList
     */
    public void updateSystemUserBaseInfo( SystemUser editUser )
    {

        if( editUser == null || editUser.getUserId().longValue() < 0 )
        {
            log.error( "[updateSystemUserAndRole] 错误的editUser：" + editUser );
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            // 若为所属机构主管,并且机构发生了变化
            SystemUserBean oldBean = securityDao.querySingleSystemUserBeanById( editUser
                .getUserId() );

            SystemOrganizationBean orgBean = orgDao
                .querySingleSystemOrganizationBeanByLinear( oldBean.getRelateOrgCode() );

            // 机构改变,删除原角色,若为主管,检查目前所属机构是否发生变化,将机构管理员删除
            if( !oldBean.getRelateOrgCode().equals( editUser.getRelateOrgCode() ) )
            {
                securityDao.deleteAllUserHaveRole( editUser.getUserId() );

                if( orgBean != null && orgBean.getOrgBossId().equals( oldBean.getUserId() ) )
                {
                    orgDao.updateOrgBossId( orgBean.getOrgId(), Long.valueOf( -1 ) );
                }
            }

            securityDao.updateSystemUser( editUser );

            mysqlEngine.commit();

        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    /**
     * 更新系统用户角色
     * 
     * @param editUser
     * @param roleIdList
     */
    public void updateSystemUserHaveRole( Long userId, List roleIdList )
    {
        if( roleIdList == null )
        {
            log.error( "[updateSystemUserAndRole] roleIdArray为null!" );
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            // 若为所属机构主管,并且机构发生了变化
            SystemUserBean oldBean = securityDao.querySingleSystemUserBeanById( userId );

            // 更新角色相关信息

            securityDao.deleteAllUserHaveRole( userId );

            mysqlEngine.startBatch();

            for ( int i = 0; i < roleIdList.size(); i++ )
            {
                securityDao.saveUserAssociatedRole( userId, ( Long ) roleIdList.get( i ) );
            }

            mysqlEngine.executeBatch();

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    /**
     * 根据父资源ID取得直系资源信息,包括父资源,当目标res被停用,不选取.
     * 
     * @param parentResId
     * @param protectType
     * @return
     */
    public List retrieverSecurityResourceBeanByParentId( Long parentResId, Integer protectType )
    {
        List result = securityDao.querySecurityResourceBeanByParentResId( parentResId,
            Constant.SECURITY.RES_IS_USE_STATE, protectType );
        // 取父资源
        result.add( securityDao.querySingleSecurityResourceBean( parentResId,
            Constant.SECURITY.RES_IS_USE_STATE, protectType ) );

        return result;
    }

    /**
     * 根据父资源标识取得直系资源信息,包括父资源,当目标res被停用,不选取.
     * 
     * @param parentSysFlag
     * @param protectType
     * @return
     */
    public List retrieverSecurityResourceBeanByParentSysFlag( String parentSysFlag,
        Integer protectType, Long dataSecType )
    {
        List result = securityDao.querySecurityResourceBeanByParentSysFlag( parentSysFlag,
            Constant.SECURITY.RES_IS_USE_STATE, protectType, dataSecType );
        // 取父资源
        result.add( securityDao.querySingleSecurityResourceBean( parentSysFlag,
            Constant.SECURITY.RES_IS_USE_STATE, protectType, dataSecType ) );

        return result;
    }

    /**
     * 根据父资源标识取得直系组合资源信息,包括父资源,当目标res被停用,不选取.
     * 此方法动态获取所有的细粒度(type=2)类型资源组合(restype=3 or 4)
     * 
     * @param parentSysFlag
     * @param protectType
     * @return
     */
    public List retrieverAccGroupSecurityResourceBean()
    {
        List result = securityDao
            .queryAccGroupSecurityResourceBeanByParentSysFlag( Constant.SECURITY.RES_IS_USE_STATE );

        return result;
    }

    /**
     * 删除指定角色ID的系统角色所有相关信息
     * 
     * @param roleId
     */
    public void deleteSystemRoleAllInfo( List roleIdList )
    {
        if( roleIdList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long roleId = null;

            for ( int i = 0; i < roleIdList.size(); i++ )
            {
                roleId = Long
                    .valueOf( StringUtil.getLongValue( ( String ) roleIdList.get( i ), -1 ) );

                if( roleId.longValue() < 1 )
                {
                    continue;
                }

                // 检查root role,不可删除
                SystemRoleBean rootRole = securityDao.querySingleAdminRole();

                if( rootRole != null && rootRole.getRoleId().equals( roleId ) )
                {

                    continue;
                }

                // 先删除role本身
                securityDao.deleteSingleSystemRole( roleId );

                // 删除role拥有的一般资源信息
                securityDao.deleteRoleHaveHisResourceByRoleId( roleId );
                // 删除role拥有的细粒度Acc资源信息
                securityDao.deleteRoleHaveHisAccResourceByRoleId( roleId );
                // 删除所有与此role相关的用户信息
                securityDao.deleteRoleAssociatedUser( roleId );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            ListContentClassInfoTreeController.resizeSiteContentClassCache();

            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
        }

    }

    /**
     * 删除指定会员ID的系统角色所有相关信息
     * 
     * @param roleId
     */
    public void deleteMemberRoleAllInfo( List roleIdList )
    {
        if( roleIdList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long roleId = null;

            for ( int i = 0; i < roleIdList.size(); i++ )
            {
                roleId = Long
                    .valueOf( StringUtil.getLongValue( ( String ) roleIdList.get( i ), -1 ) );

                if( roleId.longValue() < 1 )
                {
                    continue;
                }

                // 先删除role本身
                securityDao.deleteSingleMemberRole( roleId );

                // 删除role拥有的一般资源信息
                securityDao.deleteMemberRoleHaveHisResourceByRoleId( roleId );

                // 删除所有与此role相关的会员信息
                securityDao.deleteMemberRoleAssociatedUser( roleId );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

        }

    }

    /**
     * 删除指定用户ID的系统用户所有相关信息
     * 
     * @param roleId
     */
    public void deleteSystemUserAllInfo( List idList )
    {
        if( idList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long userId = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                userId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

                if( userId.longValue() < 1 )
                {
                    continue;
                }

                // 先删除user本身

                securityDao.deleteSingleSystemUser( userId );

                // 删除所有与此user相关的角色信息
                securityDao.deleteUserAssociatedRole( userId );

                StatService.getInstance().deleteUserTrace( userId );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public List retrieveAccIdList( Long siteId, Long roleId, Long secResId, String accType, Integer secType )
    {

        if( roleId == -99999 )// 登录人员role
        {              
            String rs = StringUtil.replaceString( SecuritySessionKeeper.getSecuritySession().getAuth().getRoleSqlHelper()
                .getAllRoleOrQuery( "", "roleId" ), "roleId", "rra.roleId" );
            
            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            siteId = site.getSiteId();

            List<Map> result = null;

            // 是否需要分类获取资源
            if( StringUtil.isStringNull( accType ) )
            {
                if( secResId == null || secResId.longValue() < 0 )
                {
                    // 取所有
                    result = securityDao.queryResourceHaveAccId( siteId, rs, secType );
                }
                else
                {
                    result = securityDao.queryResourceHaveAccId( siteId, rs, secResId, secType );
                }
            }
            else
            {
                if( secResId == null || secResId.longValue() < 0 )
                {
                    // 取所有
                    result = securityDao.queryResourceHaveAccIdByType( rs,
                        changeResTypeToDbVal( accType ) , secType);
                }
                else
                {
                    result = securityDao.queryResourceHaveAccIdByType( rs, secResId,
                        changeResTypeToDbVal( accType ), secType );
                }
            }

            Set rset = new HashSet();

            if( result != null )
            {
                for ( Map res : result )
                {
                    rset.add( ( Long ) res.get( "accId" ) );
                }
            }

            List<Long> tmp = new ArrayList( rset );

            result = new ArrayList<Map>();

            for ( Long id : tmp )
            {
                Map m = new HashMap();

                m.put( "accId", id );

                result.add( m );
            }

            return result;

        }
        else
        {

            // 是否需要分类获取资源
            if( StringUtil.isStringNull( accType ) )
            {
                if( secResId == null || secResId.longValue() < 0 )
                {
                    // 取所有
                    return securityDao.queryResourceHaveAccId( siteId, roleId );
                }
                return securityDao.queryResourceHaveAccId( siteId, roleId, secResId );
            }
            else
            {
                if( secResId == null || secResId.longValue() < 0 )
                {
                    // 取所有
                    return securityDao.queryResourceHaveAccIdByType( roleId,
                        changeResTypeToDbVal( accType ) );
                }
                return securityDao.queryResourceHaveAccIdByType( roleId, secResId,
                    changeResTypeToDbVal( accType ) );
            }
        }
    }

    public List retrieveAuditAccIdList( Long roleId )
    {
        return securityDao.queryAuditAccIdList( roleId );
    }

    public List retrieveAuthRangeRelateResInfo( Long orgId, Integer pType, boolean flMode )
    {
        if( orgId.longValue() == 1 )
        {
            // 总公司级别
            return retrieveSecurityResourceBeanByProtectType( pType );
        }

        if( flMode )
        {
            return securityDao.queryAuthRangeRelateResInfoforFirstLayer( orgId );
        }

        return securityDao.queryAuthRangeRelateResInfo( orgId );
    }

    public List retrieveAuthRangeRelateResInfoByParentOrgId( Long orgId )
    {
        return securityDao.queryAuthRangeRelateResInfoByParentOrgId( orgId );
    }

    public List retrieveRoleRangeOrgRelateResourceAcc( Long originalOrgId, Long orgId, Long siteId,
        Integer dst, boolean isParRootOrg, boolean isParentMode, String classMode )
    {
        List result = null;
        try
        {
            mysqlEngine.beginTransaction();

            if( isParRootOrg )
            {
                // 首先判断是否拥有站点管理权
                Integer count = orgDao.checkOrgRelateSite( originalOrgId, siteId );

                if( count.intValue() != 1 )
                {
                    return new ArrayList( 1 );
                }

                if( "channel".equals( classMode ) )
                {
                    result = securityDao.queryAllRoleRangeOrgRelateResourceAcc( siteId, dst );
                }
                else if( "commend".equals( classMode ) )
                {
                    result = securityDao.queryAllCommendRoleRangeOrgRelateResourceAcc( siteId, dst );
                }
                else if( "guestbook".equals( classMode ) )
                {
                    result = securityDao.queryAllGuestbookRoleRangeOrgRelateResourceAcc( siteId,
                        dst );
                }
            }
            else
            {
                // 首先判断是否拥有站点管理权
                Long checkOrgId = orgId;
                if( isParentMode )
                {
                    checkOrgId = originalOrgId;
                }

                Integer count = orgDao.checkOrgRelateSite( checkOrgId, siteId );

                if( count.intValue() != 1 )
                {
                    return new ArrayList( 1 );
                }

                result = securityDao.queryRoleRangeOrgRelateResourceAcc( orgId, siteId, dst );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        return result;
    }

    public void deleteUserRealteFromRole( String[] checkIds, Long roleId, Map secMap  )
    {

        if( checkIds == null || roleId == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();
            for ( int i = 0; i < checkIds.length; i++ )
            {
                Long userId = Long.valueOf( StringUtil.getLongValue( checkIds[i], -1 ) );

                if( userId.longValue() > 0 )
                {
                    securityDao.deleteUserForRole( userId, roleId );
                }
            }
            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }
        
        // 更新登陆管理员的权限
        //TODO SESSION ERROR

        Iterator allManagerSecIter = secMap.entrySet().iterator();

        Entry entry = null;
        SecuritySession secSession = null;
        Auth auth = null;
        Long identity = null;
        while ( allManagerSecIter.hasNext() )
        {
            entry = ( Entry ) allManagerSecIter.next();
            secSession = ( SecuritySession ) entry.getValue();

            auth = secSession.getAuth();

            if( auth != null )
            {
                identity = ( Long ) auth.getIdentity();

                List roleList = retrieveUserHaveSecurityRoleByUserId( identity );

                Role[] ra = new Role[roleList.size()];
                for ( int i = 0; i < roleList.size(); i++ )
                {
                    ra[i] = ( Role ) roleList.get( i );
                }

                auth.setAuthorities( ra );

            }
        }
    }

    public void deleteMemberRealteFromRole( String[] checkIds, Long roleId )
    {

        if( checkIds == null || roleId == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();
            for ( int i = 0; i < checkIds.length; i++ )
            {
                Long userId = Long.valueOf( StringUtil.getLongValue( checkIds[i], -1 ) );

                if( userId.longValue() > 0 )
                {
                    securityDao.deleteMemberRoleRealte( userId, roleId );
                }
            }
            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }
    }

    public void addUserRealteToRole( String[] checkIds, Long roleId, Map secMap )
    {
        if( checkIds == null || roleId == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();
            mysqlEngine.startBatch();

            for ( int i = 0; i < checkIds.length; i++ )
            {
                Long userId = Long.valueOf( StringUtil.getLongValue( checkIds[i], -1 ) );

                if( userId.longValue() > 0 )
                {
                    securityDao.saveUserAssociatedRole( userId, roleId );
                }
            }

            mysqlEngine.executeBatch();
            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }
//      TODO SESSION ERROR
        Iterator allManagerSecIter = secMap.entrySet().iterator();

        Entry entry = null;
        SecuritySession secSession = null;
        Auth auth = null;
        Long identity = null;
        while ( allManagerSecIter.hasNext() )
        {
            entry = ( Entry ) allManagerSecIter.next();
            secSession = ( SecuritySession ) entry.getValue();

            auth = secSession.getAuth();

            if( auth != null )
            {
                identity = ( Long ) auth.getIdentity();

                List roleList = retrieveUserHaveSecurityRoleByUserId( identity );

                Role[] ra = new Role[roleList.size()];
                for ( int i = 0; i < roleList.size(); i++ )
                {
                    ra[i] = ( Role ) roleList.get( i );
                }

                auth.setAuthorities( ra );
                
                
            }
        }


    }

    public void addMemberRealteToRole( String[] checkIds, Long roleId )
    {
        if( checkIds == null || roleId == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();
            mysqlEngine.startBatch();

            for ( int i = 0; i < checkIds.length; i++ )
            {
                Long userId = Long.valueOf( StringUtil.getLongValue( checkIds[i], -1 ) );

                if( userId.longValue() > 0 )
                {
                    securityDao.saveMemberRelateRole( userId, roleId );
                }
            }

            mysqlEngine.executeBatch();
            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void updateSystemUserPassword( Long userId, String password )
    {
        securityDao.updateUserPassword( userId, password );
    }

    public void updateSystemUserUseStatus( List idList, String flag )
    {
        if( idList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long id = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                id = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

                if( id.longValue() < 1 )
                {
                    continue;
                }

                if( "open".equals( flag ) )
                {
                    securityDao.updateSystemUserUseStatus( id, Constant.COMMON.ON );
                }
                else if( "close".equals( flag ) )
                {
                    securityDao.updateSystemUserUseStatus( id, Constant.COMMON.OFF );
                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public boolean checkDataSecTypeId( Long dataSecTypeId )
    {
        Integer count = securityDao.queryDataCountForDataSecTypeId( dataSecTypeId );

        if( count.intValue() != 1 )
        {
            return false;
        }

        return true;
    }

    public Object getAccSecDataTypeTag( String secTypeId )
    {

        Long secTidVar = Long.valueOf( StringUtil.getLongValue( secTypeId, -1 ) );

        if( secTidVar.longValue() > 0 )
        {
            return securityDao.querySingleSysteAccSecDataType( secTidVar );
        }
        else
        {
            return securityDao.querySystemAllAccSecDataType();
        }

    }

    public List retrieveAllSecTypeId()
    {
        return securityDao.querySystemAllAccSecDataTypeId();
    }

    public List retrieveSiteHaveHisOrgAllUserForTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        return securityDao.querySiteHaveHisOrgAllUser( site.getSiteId() );
    }

    public void addSecType( Map valMap )
    {
        securityDao.saveAccSecDataType( valMap );

        clearCache();

        ListContentClassInfoTreeController.resizeSiteContentClassCache();
        ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
    }

    public void editSecType( Map valMap )
    {
        securityDao.updateAccSecDataType( valMap );

        clearCache();

        ListContentClassInfoTreeController.resizeSiteContentClassCache();
        ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
    }

    public void deleteSecType( Long id )
    {
        securityDao.deleteAccSecDataType( id );

        clearCache();

        ListContentClassInfoTreeController.resizeSiteContentClassCache();
        ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
    }

    public void checkSysRootRoleAndUser( String initAdminPW )
    {
        // 检查root role
        SystemRoleBean rootRole = securityDao.querySingleAdminRole();

        Long roleId = null;

        if( rootRole == null )
        {
            SystemRole role = new SystemRole();

            role.setOrgId( Long.valueOf( 1 ) );
            role.setRoleName( "系统管理员" );
            role.setRoleDesc( "全权限" );

            role.setUseState( Constant.COMMON.ON );

            UpdateState us = securityDao.saveRole( role );

            roleId = Long.valueOf( us.getKey() );
        }
        else
        {
            roleId = rootRole.getRoleId();
        }

        // 核心角色可管站点
        orgDao.deleteRoleRangeOrgRelateSite( Long.valueOf( 1 ) );

        List siteList = siteGroupDao.queryAllSiteBean();

        SiteGroupBean site = null;

        for ( int i = 0; i < siteList.size(); i++ )
        {
            site = ( SiteGroupBean ) siteList.get( i );

            orgDao.saveRoleRangeOrgRelateSite( Long.valueOf( 1 ), site.getSiteId() );
        }

        // 核心角色权限

        List resIdList = securityDao.queryUsefullRoleAuthSecId();

        Long resId = null;

        for ( int i = 0; i < resIdList.size(); i++ )
        {

            resId = ( Long ) resIdList.get( i );

            if( resId != null )
            {
                if( securityDao.querySecurityResRalateRoleCount( roleId, resId ).longValue() < 1 )
                {
                    securityDao.saveRoleAssociatedResource( roleId, resId );
                }

            }
        }

        // init基础role

        if( StringUtil.isStringNotNull( initAdminPW ) )
        {
            SystemUserBean adminUser = securityDao.queryAdminUser();

            if( adminUser != null )
            {

                // 删除原admin
                securityDao.deleteAdminUser();

                // 删除所有与此user相关的角色信息
                securityDao.deleteUserAssociatedRole( adminUser.getUserId() );
            }

            SystemUser user = new SystemUser();

            user.setAddTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );
            user.setCreator( "sysAdmin" );
            user.setEmail( "admin@jtopcms.com" );
            user.setManageOrgId( Long.valueOf( -1 ) );
            user.setPassword( PasswordUtility.encrypt( initAdminPW ) );
            user.setPhone( "13988888888" );
            user.setRelateOrgCode( "001" );
            user.setRemark( "系统管理员,拥有所有基础权限" );
            user.setUserName( "admin" );
            user.setUserTrueName( "超级管理员" );
            user.setUseState( Constant.COMMON.ON );

            UpdateState us = securityDao.saveUser( user );

            securityDao.saveUserAssociatedRole( Long.valueOf( us.getKey() ), roleId );

            orgDao.updateOrgBossId( "001", Long.valueOf( us.getKey() ) );

        }

    }

    public void addMemberAccRule( MemberAccRule mar )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        mar.setSiteId( site.getSiteId() );

        securityDao.save( mar );

        clearMemberAccCache();

    }

    public void editMemberAccRule( MemberAccRule mar )
    {
        securityDao.updateMemberAccRule( mar );

        clearMemberAccCache();
    }

    public void deleteMemberAccRule( List idList )
    {
        if( idList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long id = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                id = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

                if( id.longValue() < 1 )
                {
                    continue;
                }

                securityDao.deleteMemberAccRuleById( id );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearMemberAccCache();
        }

    }

    public List getMemberAccRuleListForTag( String type )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( "".equals( type ) )
        {
            return securityDao.queryMemberAccRuleList( site.getSiteId() );
        }

        return securityDao.queryMemberAccRuleList( site.getSiteId(), Integer.valueOf( type ) );
    }

    public MemberAccRuleBean getSingleMemberAccRuleForTag( String accId )
    {
        Long accIdVar = Long.valueOf( StringUtil.getLongValue( accId, -1 ) );

        return securityDao.querySingleMemberAccRule( accIdVar );

    }

    public MemberAccRuleBean retrieveSingleMemberAccRule( Long classId )
    {
        String key = "retrieveSingleMemberAccRule:" + classId + "|1";

        Cache cache = ( Cache ) cacheManager.get( "retrieveSingleMemberAccRule" );

        MemberAccRuleBean arBean = ( MemberAccRuleBean ) cache.getEntry( key );

        if( arBean == null )
        {
            arBean = securityDao.querySingleMemberAccRuleByClassId( classId );

            if( arBean == null )
            {
                arBean = new MemberAccRuleBean();
            }

            cache.putEntry( key, arBean );
        }

        return arBean;

    }

    public MemberAccRuleBean retrieveSingleMemberSubmitAccRule( Long classId )
    {
        String key = "retrieveSingleMemberAccRule:" + classId + "|2";

        Cache cache = ( Cache ) cacheManager.get( "retrieveSingleMemberAccRule" );

        MemberAccRuleBean arBean = ( MemberAccRuleBean ) cache.getEntry( key );

        if( arBean == null )
        {
            arBean = securityDao.querySingleMemberSubmitAccRuleByClassId( classId );

            if( arBean == null )
            {
                arBean = new MemberAccRuleBean();
            }

            cache.putEntry( key, arBean );
        }

        return arBean;

    }

    // public Set retrieveMemberClassAccRelateRoleByClassId( Long classId )
    // {
    // return securityDao.queryMemberClassAccRelateRoleByClassId( classId );
    // }

    public void addMemberClassAcc( Map params )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Integer typeId = Integer.valueOf( StringUtil.getIntValue(
            ( String ) params.get( "typeId" ), -1 ) );

        try
        {
            mysqlEngine.beginTransaction();

            Iterator iter = params.entrySet().iterator();

            Entry entry = null;

            String key = null;

            Long classId = null;

            Long ruleId = null;

            while ( iter.hasNext() )
            {
                entry = ( Entry ) iter.next();

                key = ( String ) entry.getKey();

                ruleId = Long.valueOf( StringUtil.getLongValue( ( String ) entry.getValue(), -1 ) );

                if( key.startsWith( "classAcc-" ) )
                {
                    classId = Long.valueOf( StringUtil.getLongValue( StringUtil.replaceString( key,
                        "classAcc-", "", false, false ), -1 ) );

                    if( classId.longValue() > 0 )
                    {
                        if( Constant.MEMBER.ACC_CLASS_VIEW.equals( typeId ) )
                        {
                            securityDao.deleteMemberClassAccByClassId( classId );
                        }
                        else if( Constant.MEMBER.ACC_CLASS_SUBMIT.equals( typeId ) )
                        {
                            securityDao.deleteMemberClassSubmitAccByClassId( classId );
                        }

                        // securityDao
                        // .deleteMemberClassAccRelateRoleByClassId( classId );

                        if( ruleId.longValue() > 0 )
                        {
                            if( Constant.MEMBER.ACC_CLASS_VIEW.equals( typeId ) )
                            {
                                securityDao.saveMemberClassAcc( classId, ruleId, site.getSiteId() );
                            }
                            else if( Constant.MEMBER.ACC_CLASS_SUBMIT.equals( typeId ) )
                            {
                                securityDao.saveMemberClassSubmitAcc( classId, ruleId, site
                                    .getSiteId() );
                            }

                            // arBean = securityDao
                            // .querySingleMemberAccRule( ruleId );
                            //
                            // if( arBean != null )
                            // {
                            // Long[] roleIds = arBean.getRoleIdArray();
                            //
                            // for ( int i = 0; i < roleIds.length; i++ )
                            // {
                            // roleId = roleIds[i];
                            //
                            // securityDao.saveMemberClassAccRelateRole(
                            // classId, roleId, site.getSiteId() );
                            // }
                            // }
                        }
                    }
                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearMemberAccCache();

        }

    }

    public void clearMemberClassAcc( Integer typeId )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        try
        {
            mysqlEngine.beginTransaction();

            if( Constant.MEMBER.ACC_CLASS_VIEW.equals( typeId ) )
            {
                securityDao.deleteMemberClassAccBySiteId( site.getSiteId() );
            }
            else if( Constant.MEMBER.ACC_CLASS_SUBMIT.equals( typeId ) )
            {
                securityDao.deleteMemberClassSubmitAccBySiteId( site.getSiteId() );
            }

            // securityDao.deleteMemberClassAccRelateRoleBySiteId( site
            // .getSiteId() );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearMemberAccCache();
        }

    }

    public void applyChildMemberClassAcc( Long classId, Integer typeId )
    {

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        try
        {
            mysqlEngine.beginTransaction();

            ContentClassBean classBean = channelDao.querySingleClassBeanInfoByClassId( classId );

            if( classBean == null )
            {
                return;
            }

            Long accRuleId = Long.valueOf( -1 );

            Map classAcc = null;

            if( Constant.MEMBER.ACC_CLASS_VIEW.equals( typeId ) )
            {
                classAcc = securityDao.querySingleMemberClassAcc( classId );
            }
            else if( Constant.MEMBER.ACC_CLASS_SUBMIT.equals( typeId ) )
            {
                classAcc = securityDao.querySingleMemberSubmitClassAcc( classId );
            }

            if( !classAcc.isEmpty() )
            {
                accRuleId = ( Long ) classAcc.get( "accRuleId" );
            }

            List allChildClassId = StringUtil.changeStringToList(
                channelDao.queryContentClassIdByPreLinear( classBean.getLinearOrderFlag(), site
                    .getSiteFlag() ), "," );

            Long childId = null;

            Long roleId = null;

            MemberAccRuleBean arBean = null;

            for ( int i = 0; i < allChildClassId.size(); i++ )
            {
                childId = StringUtil.getLongValue( ( String ) allChildClassId.get( i ), -1 );

                if( Constant.MEMBER.ACC_CLASS_VIEW.equals( typeId ) )
                {
                    securityDao.deleteMemberClassAccByClassId( childId );
                }
                else if( Constant.MEMBER.ACC_CLASS_SUBMIT.equals( typeId ) )
                {
                    securityDao.deleteMemberClassSubmitAccByClassId( childId );

                }

                // securityDao.deleteMemberClassAccRelateRoleByClassId( childId
                // );

                if( accRuleId.longValue() > 0 )
                {
                    if( Constant.MEMBER.ACC_CLASS_VIEW.equals( typeId ) )
                    {
                        securityDao.saveMemberClassAcc( childId, accRuleId, site.getSiteId() );
                    }
                    else if( Constant.MEMBER.ACC_CLASS_SUBMIT.equals( typeId ) )
                    {

                        securityDao.saveMemberClassSubmitAcc( childId, accRuleId, site.getSiteId() );
                    }

                    // arBean = securityDao.querySingleMemberAccRule( accRuleId
                    // );
                    //
                    // if( arBean != null )
                    // {
                    // Long[] roleIds = arBean.getRoleIdArray();
                    //
                    // for ( int j = 0; j < roleIds.length; j++ )
                    // {
                    // roleId = roleIds[j];
                    //
                    // securityDao.saveMemberClassAccRelateRole( childId,
                    // roleId, site.getSiteId() );
                    // }
                    // }
                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearMemberAccCache();
        }

    }

    public List getMemberAccForTag( String typeId )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Integer tId = Integer.valueOf( StringUtil.getIntValue( typeId, -1 ) );

        if( Constant.MEMBER.ACC_CLASS_VIEW.equals( tId ) )
        {
            return securityDao.queryMemberClassAccList( site.getSiteId() );
        }
        else if( Constant.MEMBER.ACC_CLASS_SUBMIT.equals( tId ) )
        {
            return securityDao.queryMemberClassSubmitAccList( site.getSiteId() );
        }

        return null;

    }

    // public List getAdminIsOrgBossForTag( )
    // {
    // SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
    // .getSecuritySession().getCurrentLoginSiteInfo();
    //
    // Integer tId = Integer.valueOf( StringUtil.getIntValue( typeId, -1 ) );
    //
    // if( Constant.MEMBER.ACC_CLASS_VIEW.equals( tId ) )
    // {
    // return securityDao.queryMemberClassAccList( site.getSiteId() );
    // }
    // else if( Constant.MEMBER.ACC_CLASS_SUBMIT.equals( tId ) )
    // {
    // return securityDao.queryMemberClassSubmitAccList( site.getSiteId() );
    // }
    //
    // return null;
    //
    // }

    private Integer changeResTypeToDbVal( String input )
    {
        if( "group".equals( input ) )
        {
            return Constant.SECURITY.RES_TYPE_GROUP;
        }
        else if( "link".equals( input ) )
        {
            return Constant.SECURITY.RES_TYPE_ACT_LINK;
        }
        else if( "class".equals( input ) )
        {
            return Constant.SECURITY.RES_TYPE_CLASS;
        }
        else if( "entry".equals( input ) )
        {
            return Constant.SECURITY.RES_TYPE_ENTRY;
        }

        return Integer.valueOf( -1 );
    }

    public static void clearCache()
    {
        Cache cache = ( Cache ) cacheManager.get( "allSecurityResourceMapCache" );

        cache.clearAllEntry();
    }

   

    public static void clearMemberCache()
    {
        Cache cache = ( Cache ) cacheManager.get( "allMemberSecurityResourceMapCache" );

        cache.clearAllEntry();
    }
 

    public static void clearMemberAccCache()
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveSingleMemberAccRule" );

        cache.clearAllEntry();
    }

 
}
