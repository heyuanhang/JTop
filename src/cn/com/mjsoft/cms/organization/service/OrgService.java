package cn.com.mjsoft.cms.organization.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.dao.ChannelDao;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.guestbook.dao.GuestbookDao;
import cn.com.mjsoft.cms.organization.bean.SystemOrganizationBean;
import cn.com.mjsoft.cms.organization.dao.OrgDao;
import cn.com.mjsoft.cms.organization.dao.vo.SystemOrganization;
import cn.com.mjsoft.cms.security.bean.SecurityResourceBean;
import cn.com.mjsoft.cms.security.bean.SystemRoleBean;
import cn.com.mjsoft.cms.security.bean.SystemUserBean;
import cn.com.mjsoft.cms.security.dao.SecurityDao;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class OrgService
{
    private static Logger log = Logger.getLogger( OrgService.class );

    private static OrgService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private OrgDao orgDao;

    private ChannelDao channelDao;

    private GuestbookDao gbDao;

    private SecurityDao securityDao;

    private OrgService()
    {
        orgDao = new OrgDao( mysqlEngine );
        channelDao = new ChannelDao( mysqlEngine );
        gbDao = new GuestbookDao( mysqlEngine );
        securityDao = new SecurityDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new OrgService();
        }
    }

    public static OrgService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public void addNewOrganization( SystemOrganization org )
    {
        if( org == null )
        {
            throw new FrameworkException( "SystemOrganization对象为空" );
        }

        try
        {
            mysqlEngine.beginTransaction();

            SystemOrganizationBean parentOrgBean = orgDao
                .querySingleSystemOrganizationBeanById( org.getParentId() );

            if( parentOrgBean == null )
            {
                throw new FrameworkException( "SystemOrganizationBean对象为空,orgId:"
                    + org.getParentId() );
            }

            String newLinearFlag = null;
            if( Constant.COMMON.FLAG_IN == parentOrgBean.getIsLeaf().intValue() )
            {
                // 上级部门下没有子机构
                newLinearFlag = ServiceUtil.increaseLayerLinear(
                    parentOrgBean.getLinearOrderFlag(), true );
            }
            else
            {
                // 已经有兄弟部门存在
                SystemOrganizationBean orgBean = orgDao
                    .querySingleChildLastSystemOrganizationBeanById( org.getParentId() );

                newLinearFlag = ServiceUtil.increaseLayerLinear( orgBean.getLinearOrderFlag(),
                    false );
            }

            // 更新上级机构叶子状态
            orgDao.updateNodeLeafFlag( parentOrgBean.getOrgId(), Integer
                .valueOf( Constant.COMMON.FLAG_OUT ) );

            org.setIsLeaf( Integer.valueOf( Constant.COMMON.FLAG_IN ) );
            org.setLayer( Integer.valueOf( parentOrgBean.getLayer().intValue() + 1 ) );
            org.setLinearOrderFlag( newLinearFlag );

            orgDao.save( org );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void editOrganization( SystemOrganization org )
    {
        orgDao.updateOrganizationBaseInfo( org );

    }

    public SystemOrganizationBean retrieveSingleOrganizationBeanById( Long orgId )
    {
        return orgDao.querySingleSystemOrganizationBeanById( orgId );
    }

    public SystemOrganizationBean retrieveSingleOrganizationBeanByOrgIdLinearFlag( String code )
    {
        return orgDao.querySingleSystemOrganizationBeanByLinear( code );
    }

    public List retrieveOrganizationBeanByOrgIdLinearFlag( Long orgId )
    {
        List result = null;

        try
        {
            mysqlEngine.beginTransaction();

            SystemOrganizationBean orgBean = orgDao.querySingleSystemOrganizationBeanById( orgId );

            if( orgBean == null )
            {
                return result;
            }

            result = orgDao.querySystemOrganizationBeanByLinearFlag( orgBean.getLinearOrderFlag() );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        return result;
    }

    public List retrieveAllOrganizationBean()
    {
        return orgDao.queryAllSystemOrganizationBean();
    }

    public List retrieveOrganizationBeanByParentId( Long parentId )
    {
        return orgDao.queryOrganizationBeanByParentId( parentId );
    }

    public void deleteOrganizationAllInfo( Long orgId )
    {
        if( orgId == null || orgId.longValue() == 1 )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            SystemOrganizationBean orgBean = orgDao.querySingleSystemOrganizationBeanById( orgId );

            if( orgBean.getParentId().longValue() == -1 )
            {
                return;
            }

            SystemOrganizationBean parentOrgBean = orgDao
                .querySingleSystemOrganizationBeanById( orgBean.getParentId() );

            Integer childCount = orgDao.queryChildCountById( parentOrgBean.getOrgId() );

            if( childCount.intValue() == 1 )
            {
                // 删除后需要改变叶子节点
                orgDao.updateNodeLeafFlag( parentOrgBean.getOrgId(), Integer
                    .valueOf( Constant.COMMON.FLAG_IN ) );
            }

            // 删除range数据。 只有第一层org有辅助数据，故没有使用linear

            orgDao.deleteRoleRangeFirstLayerOrgRelateResource( orgId );

            orgDao.deleteFirstLayerSiteRangeByOrgId( orgId );

            orgDao.deleteRangeRoleRelateSiteByOrgLinearFlag( orgBean.getLinearOrderFlag() );

            orgDao.deleteRangeRoleHaveHisResourceByOrgLinearFlag( orgBean.getLinearOrderFlag() );

            orgDao.deleteRangeRoleHaveHisAccResourceByOrgLinearFlag( orgBean.getLinearOrderFlag() );

            // 删除所属角色以及授权资源，管理员不用删除

            securityDao.deleteRoleHaveHisResourceByOrgLinearFlag( orgBean.getLinearOrderFlag() );

            securityDao.deleteRoleHaveHisAccResourceByOrgLinearFlag( orgBean.getLinearOrderFlag() );

            securityDao.deleteRoleByOrgId( orgBean.getLinearOrderFlag() );

            // 删除所有孩子以及自己
            orgDao.deleteOrganizationByLinearFlag( orgBean.getLinearOrderFlag() );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }
    }

    public void maintainOrganizationProtectResRangeForRole( Long orgId, Long siteId,
        String[] checkOrgSiteManagerArray, String[] checkResourceArray, Map dataSecTypeMap )
    // Map checkContentAccProtectClassIdResMap )
    {
        log.info( "[maintainOrganizationProtectResRangeForRole]  orgId:" + orgId );

        if( orgId == null || checkOrgSiteManagerArray == null || checkResourceArray == null )
        {
            return;
        }

        String resStr = null;

        Set checkResIdSet = new HashSet();
        Long targetRoleResId = null;
        Integer type = null;
        String[] resInfo = null;

        SystemOrganizationBean orgBean = null;

        try
        {
            mysqlEngine.beginTransaction();

            orgBean = orgDao.querySingleSystemOrganizationBeanById( orgId );

            Long loginOrgId = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth()
                .getOrgIdentity();

            /**
             * 站点管理粗粒度资源
             */

            // 删除当前管理站点原资源信息
            orgDao.deleteRoleRangeOrgRelateSite( orgId );

            // 先删除可能的第一等级机构原数据
            if( orgBean.getParentId() == 1 && loginOrgId.longValue() == 1 )
            {
                orgDao.deleteFirstLayerSiteRangeByOrgId( orgId );
            }

            Set manageSiteIdSet = new HashSet();
            Long managerSiteId = null;

            for ( int i = 0; i < checkOrgSiteManagerArray.length; i++ )
            {
                managerSiteId = Long.valueOf( StringUtil.getLongValue(
                    ( String ) checkOrgSiteManagerArray[i], -1 ) );

                if( managerSiteId.longValue() > 0 )
                {

                    manageSiteIdSet.add( managerSiteId );

                    orgDao.saveRoleRangeOrgRelateSite( orgId, managerSiteId );

                    if( orgBean.getParentId() == 1 && loginOrgId.longValue() == 1 )
                    {
                        // 第一等级站点
                        orgDao.saveRoleRangeFirstLayerOrgRelateSite( orgId, managerSiteId );
                    }

                }
            }

            /**
             * 粗粒度资源
             */
            // 删除原数据
            orgDao.deleteRoleRangeOrgRelateResource( orgId );

            SecurityResourceBean groupRes = null;

            List groupChildRes = null;

            for ( int i = 0; i < checkResourceArray.length; i++ )
            {
                resStr = checkResourceArray[i];
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

                    // 如果是组合要查询下面所有资源
                    groupRes = securityDao.querySingleSecurityResourceBean( targetRoleResId );

                    if( groupRes != null
                        && Constant.SECURITY.RES_TYPE_GROUP.equals( groupRes.getResourceType() ) )
                    {
                        groupChildRes = securityDao.querySecurityResourceBeanByLinear( groupRes
                            .getLinearOrderFlag() );

                        for ( int gi = 0; gi < groupChildRes.size(); gi++ )
                        {
                            checkResIdSet.add( ( ( SecurityResourceBean ) groupChildRes.get( gi ) )
                                .getSecResId() );
                        }
                    }

                    checkResIdSet.add( targetRoleResId );
                }
            }

            // 当前站点管理的粗粒度资源增加
            Iterator resIter = checkResIdSet.iterator();

            // 先删除可能的第一等级机构原数据
            if( orgBean.getParentId() == 1 && loginOrgId.longValue() == 1 )
            {
                orgDao.deleteRoleRangeFirstLayerOrgRelateResource( orgId );
            }

            mysqlEngine.startBatch();

            while ( resIter.hasNext() )
            {
                Long targetResId = ( Long ) resIter.next();

                orgDao.saveRoleRangeOrgRelateResource( orgId, targetResId );

                // 若为第一等级的机构，且为系统级管理员操作，则记录独立的粗粒度范围

                if( orgBean.getParentId() == 1 && loginOrgId.longValue() == 1 )
                {
                    orgDao.saveRoleRangeFirstLayerOrgRelateResource( orgId, targetResId );
                }
            }

            mysqlEngine.executeBatch();

            // 清除所属下属结构所有角色不再拥有的粗粒度权限
            // 获取所有当前的组织的所属org包括自己
            // iter获取这些组织在当前站点的所有res权
            // iter这些res和当前res比较,若不在当前res里,选出,等待删除

            List needDiposeRangeList = orgDao.queryOrganizationResIdByOrgLinerFlag( orgBean
                .getLinearOrderFlag() );

            Long resId = null;

            Long orgDeleteId = null;

            Map value = null;

            for ( int x = 0; x < needDiposeRangeList.size(); x++ )
            {
                value = ( Map ) needDiposeRangeList.get( x );

                resId = ( Long ) value.get( "resId" );

                orgDeleteId = ( Long ) value.get( "orgId" );

                if( !checkResIdSet.contains( resId ) )
                {
                    orgDao.deleteRoleRangeOrgRelateResourceByOrgAndRes( orgDeleteId, resId );
                }
            }

            // 删除机构(包含子结构的)所有角色相关授权
            List roleList = securityDao.querySystemRoleBeanRelateOrgCode( orgBean
                .getLinearOrderFlag() );

            SystemRoleBean roleBean = null;

            List roleHaveResIdList = null;

            SecurityResourceBean secBean = null;

            for ( int ri = 0; ri < roleList.size(); ri++ )
            {
                roleBean = ( SystemRoleBean ) roleList.get( ri );

                roleHaveResIdList = securityDao.queryRoleHaveHisResourceIdByRoleId( roleBean
                    .getRoleId() );

                for ( int rhi = 0; rhi < roleHaveResIdList.size(); rhi++ )
                {
                    if( !checkResIdSet.contains( ( Long ) roleHaveResIdList.get( rhi ) ) )
                    {
                        secBean = securityDao
                            .querySingleSecurityResourceBean( ( Long ) roleHaveResIdList.get( rhi ) );

                        if( secBean == null
                            || !Constant.SECURITY.RES_TYPE_CLASS.equals( secBean.getResourceType() ) )
                        {
                            // 模块分类不需要删除,资源已丢失需要删除
                            securityDao.deleteRoleHaveHisResourceByRoleIdAndResId( roleBean
                                .getRoleId(), ( Long ) roleHaveResIdList.get( rhi ) );
                        }
                    }
                }
            }

            /**
             * 细粒度内容资源
             */
            // 2014-6 ：以下代码请不要删除
            // // 删除内容洗粒度权限原数据
            // orgDao.deleteRoleRangeOrgRelateResourceAcc( orgId, siteId,
            // Constant.SECURITY.DATA_SEC_CONTENT );
            //
            // Iterator accClassIdInfoIter = checkContentAccProtectClassIdResMap
            // .entrySet().iterator();
            // Entry entry = null;
            // SecurityResourceBean resBean = null;
            // String[] checkedClassIds = null;
            // // 包含所选acc以及其父acc的id set
            // Set idSet = new HashSet();
            // Map checkAccResIdMap = new HashMap();
            // while ( accClassIdInfoIter.hasNext() )
            // {
            // entry = ( Entry ) accClassIdInfoIter.next();
            // resBean = ( SecurityResourceBean ) entry.getKey();
            // checkedClassIds = ( String[] ) entry.getValue();
            //
            // // 转换为idSet,判断父ID
            // for ( int j = 0; j < checkedClassIds.length; j++ )
            // {
            // Long classId = Long.valueOf( StringUtil.getLongValue(
            // ( String ) checkedClassIds[j], -1 ) );
            //
            // // 必须是合法的ID类型
            // if( classId.longValue() > 0 )
            // {
            // idSet.add( classId );
            // }
            // }
            //
            // Long targetResId = resBean.getSecResId();
            // checkAccResIdMap.put( targetResId, checkedClassIds );
            // Long classId = null;
            // Long parentClassId = null;
            // // 每一种细粒度操作集合的classId,
            //
            // mysqlEngine.startBatch();
            //
            // // 对于选取的每个栏目都要独立处理
            // for ( int j = 0; j < checkedClassIds.length; j++ )
            // {
            // classId = Long.valueOf( StringUtil.getLongValue(
            // ( String ) checkedClassIds[j], -1 ) );
            //
            // if( classId.longValue() < 1 )
            // {
            // continue;
            // }
            //
            // // 主classId
            // orgDao.saveRoleRangeOrgRelateResourceAcc( orgId,
            // targetResId, siteId,
            // Constant.SECURITY.DATA_SEC_CONTENT, classId );
            //
            // // 各相关父ID,如果没有check的话,强制check
            // parentClassId = orgDao
            // .querySingleParentClassIdByClassId( classId );
            //
            // while ( parentClassId != null
            // && parentClassId.longValue() > 0 )
            // {
            // // 不为根栏目,存入资源表,以便显示栏目树
            //
            // if( !idSet.contains( parentClassId ) )
            // {
            // // 存入resId为-1,表示只是一个没有授权的辅助classId
            // orgDao.saveRoleRangeOrgRelateResourceAcc( orgId,
            // Long.valueOf( -1 ), siteId,
            // Constant.SECURITY.DATA_SEC_CONTENT,
            // parentClassId );
            // // 加入已经存在classId
            // idSet.add( parentClassId );
            // }
            //
            // // 直系父栏目
            // parentClassId = orgDao
            // .querySingleParentClassIdByClassId( parentClassId );
            // }
            //
            // }
            // mysqlEngine.executeBatch();
            // }
            //
            // // 变跟后所需处理内容部分的相关Org acc资源
            // List needDisposeContentAccRangeList = orgDao
            // .queryOrganizationAccResIdByOrgLinerFlag( orgBean
            // .getLinearOrderFlag(), Constant.SECURITY.DATA_SEC_CONTENT );
            //
            // resId = null;
            // orgDeleteId = null;
            // Long accId = null;
            // String[] accIdstrs = null;
            //
            // value = null;
            // for ( int z = 0; z < needDisposeContentAccRangeList.size(); z++ )
            // {
            // value = ( Map ) needDisposeContentAccRangeList.get( z );
            // resId = ( Long ) value.get( "resId" );
            // accId = ( Long ) value.get( "accId" );
            // orgDeleteId = ( Long ) value.get( "orgId" );
            //
            // // parentId的情况
            // if( resId.longValue() == -1 )
            // {
            // if( !idSet.contains( accId ) )
            // {
            // 
            // orgDao.deleteRoleAccRangeOrgRelateResourceByOrgAndRes(
            // siteId, orgDeleteId, Long.valueOf( -1 ), accId,
            // Constant.SECURITY.DATA_SEC_CONTENT );
            // }
            // continue;
            // }
            //
            // // 取某一组细粒度权限的所有已选择accId
            // accIdstrs = ( String[] ) checkAccResIdMap.get( resId );
            //
            // boolean haveAcc = false;
            // if( accIdstrs != null )
            // {
            // for ( int c = 0; c < accIdstrs.length; c++ )
            // {
            // Long accTargetId = Long.valueOf( StringUtil
            // .getLongValue( ( String ) accIdstrs[c], -1 ) );
            //
            // if( accTargetId.longValue() > 0 )
            // {
            // // 若当前DB中存在的对应res的accId存在当前已选accID中,则通过
            // if( accId.equals( accTargetId ) )
            // {
            // haveAcc = true;
            // break;
            // }
            // }
            // }
            // }
            //
            // if( !haveAcc )
            // {
            // orgDao.deleteRoleAccRangeOrgRelateResourceByOrgAndRes(
            // siteId, orgDeleteId, resId, accId,
            // Constant.SECURITY.DATA_SEC_CONTENT );
            // }
            //
            // }
            /**
             * 处理内容细粒度资源
             */
            // disposeOrgClassAccRangeMustInDBTran( orgId, siteId,
            // checkContentAccProtectClassIdResMap, orgBean,
            // Constant.SECURITY. DATA_SEC_CONTENT );
            /**
             * 处理细粒度资源
             */
            Iterator secIt = dataSecTypeMap.entrySet().iterator();

            Long secTypeId = null;// 保护类型
            Map accMap = null;// key :resBean value:ids
            Entry entry = null;

            while ( secIt.hasNext() )
            {
                entry = ( Entry ) secIt.next();

                secTypeId = ( Long ) entry.getKey();
                accMap = ( Map ) entry.getValue();

                if( accMap.isEmpty() )
                {
                    continue;
                }

                disposeOrgClassAccRangeMustInDBTran( orgId, siteId, accMap, orgBean, secTypeId );
            }

            // 处理专题细粒度资源
            // disposeOrgClassAccRangeMustInDBTran( orgId, siteId,
            // checkClassAccProtectClassIdResMap, orgBean,
            // Constant.SECURITY.DATA_SEC_SPEC );

            // 统一删除不可管理站点的权限授权

            orgDao.deleteOrgSiteRangeNotIncludeSite( ( Long[] ) manageSiteIdSet
                .toArray( new Long[] {} ), orgBean.getLinearOrderFlag() );

            orgDao.deleteResourceRangeOrgRelateSiteNotIncludeSite( ( Long[] ) manageSiteIdSet
                .toArray( new Long[] {} ), orgBean.getLinearOrderFlag() );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        // 删除所属机构所有角色的所有不需要目标ID

        if( orgBean != null )
        {

            List roleBeanList = securityDao.queryAllSystemRoleBean( orgDao
                .querySystemOrganizationBeanByLinearFlag( orgBean.getLinearOrderFlag() ) );

            Iterator secIt = dataSecTypeMap.entrySet().iterator();

            Long secTypeId = null;// 保护类型
            Map accMap = null;// key :resBean value:ids
            Entry entry = null;

            Set accIdSet = null;

            while ( secIt.hasNext() )
            {
                entry = ( Entry ) secIt.next();

                secTypeId = ( Long ) entry.getKey();

                accIdSet = new HashSet( orgDao.queryOrgRangeAccIdsList( orgId, secTypeId ) );

                Long roleId = null;

                Long accId = null;

                List roleAccIdList = null;

                for ( int i = 0; i < roleBeanList.size(); i++ )
                {
                    roleId = ( ( SystemRoleBean ) roleBeanList.get( i ) ).getRoleId();

                    roleAccIdList = securityDao.queryAccIdBySecTypeIdAndRoleId( roleId, secTypeId );

                    for ( int j = 0; j < roleAccIdList.size(); j++ )
                    {
                        accId = ( Long ) roleAccIdList.get( j );

                        if( !accIdSet.contains( accId ) )
                        {
                            securityDao.deleteRoleRelateAccResByRoleAndSecType( roleId, secTypeId,
                                accId );

                        }
                    }

                }

            }

        }
    }

    public void maintainOrganizationProtectResRangeForRoleForNewClassMode( Long orgId, Long siteId,
        String[] checkOrgSiteManagerArray, String[] checkResourceArray, Map dataSecTypeMap )
    // Map checkContentAccProtectClassIdResMap )
    {
        log.info( "[maintainOrganizationProtectResRangeForRole]  orgId:" + orgId );

        if( orgId == null || checkOrgSiteManagerArray == null || checkResourceArray == null )
        {
            return;
        }

        SystemOrganizationBean orgBean = null;

        try
        {
            mysqlEngine.beginTransaction();

            orgBean = orgDao.querySingleSystemOrganizationBeanById( orgId );

            /**
             * 细粒度内容资源
             */

            /**
             * 处理内容细粒度资源
             */
            // disposeOrgClassAccRangeMustInDBTran( orgId, siteId,
            // checkContentAccProtectClassIdResMap, orgBean,
            // Constant.SECURITY. DATA_SEC_CONTENT );
            /**
             * 处理细粒度资源
             */
            Iterator secIt = dataSecTypeMap.entrySet().iterator();

            Long secTypeId = null;// 保护类型
            Map accMap = null;// key :resBean value:ids
            Entry entry = null;

            while ( secIt.hasNext() )
            {
                entry = ( Entry ) secIt.next();

                secTypeId = ( Long ) entry.getKey();
                accMap = ( Map ) entry.getValue();

                if( accMap.isEmpty() )
                {
                    continue;
                }

                disposeOrgClassAccRangeMustInDBTranForNewClassMode( orgId, siteId, accMap, orgBean,
                    secTypeId );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public List retrieveClassBeanByOrgId( Long originalOrgId, Long orgId, List dataSecTypeIds,
        Long siteId, boolean isParRootOrg, boolean isParentMode, boolean specMode )
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

                if( specMode )
                {
                    result = orgDao.querySpecClassBeanByOrgId( siteId );
                }
                else
                {
                    result = orgDao.queryClassBeanByOrgId( siteId );
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

                result = orgDao.queryClassBeanByOrgId( orgId, ( String[] ) dataSecTypeIds
                    .toArray( new String[] {} ), siteId );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        return result;
    }

    public List retrieveCommendTypeBeanByOrgId( Long originalOrgId, Long orgId,
        List dataSecTypeIds, Long siteId, boolean isParRootOrg, boolean isParentMode )
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

                result = channelDao
                    .queryAllContentCommendTypeBean( ( ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                        .getEntry( siteId ) ).getSiteFlag() );

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

                result = orgDao.queryCommendBeanByOrgId( orgId, ( String[] ) dataSecTypeIds
                    .toArray( new String[] {} ), siteId );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        return result;
    }

    public List retrieveGuestbookConfigBeanByOrgId( Long originalOrgId, Long orgId,
        List dataSecTypeIds, Long siteId, boolean isParRootOrg, boolean isParentMode )
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

                result = gbDao
                    .queryAllGuestbookConfigBeanList( ( ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                        .getEntry( siteId ) ).getSiteId() );

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

                result = orgDao.queryGuestbookConfigBeanByOrgId( orgId, ( String[] ) dataSecTypeIds
                    .toArray( new String[] {} ), siteId );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        return result;
    }

    /**
     * 获取授权可管理站点
     * 
     * @param orgId
     * @return
     */
    public List retrieveSiteBeanByOrgId( Long orgId, Long childOrgIdVar )
    {
        List result = null;

        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        Auth auth = session.getAuth();

        Long currentLoginOrgId = ( Long ) auth.getOrgIdentity();

        // 注意：以下代码记录需要保留，请不要删除
        // childOrgIdVar为空说明不是parent模式
        if( orgId.longValue() == 1 && currentLoginOrgId.longValue() == 1 && childOrgIdVar != null )
        { // 根节点组织管理员管理第一等级机构获取所有站点
            result = orgDao.queryAllOrgSiteBean();
        }
        else
        {
            // if( orgId.longValue() == 1 )
            // {
            // // 若不是根节点组织管理员登陆,且当前管理的为根节点直属的二级组织,取根节点站点授权特别数据
            // result = orgDao.querySiteBeanByRootOrgId( childOrgIdVar );
            // }
            // else if( currentLoginOrgId.longValue() == 1
            // && childOrgIdVar == null )
            // {
            // result = orgDao.querySiteBeanByRootOrgId( orgId );
            // }
            // else

            if( orgId.longValue() == 1 && childOrgIdVar != null )
            {
                result = orgDao.queryFirstLayerRangeSiteBeanByOrgId( childOrgIdVar );
            }
            else if( childOrgIdVar != null && currentLoginOrgId == childOrgIdVar )
            {
                // 登露管理org可见站点
                result = orgDao.querySiteBeanByOrgId( currentLoginOrgId );
            }
            else
            {
                // 父级org可见站点
                result = orgDao.querySiteBeanByOrgId( orgId );
            }
        }
        return result;
    }

    public List retrieveSiteBeanByOrgId( Long orgId )
    {
        return orgDao.querySiteBeanByOrgId( orgId );
    }

    public List retrieveSiteBeanByRoleHaveSite( Long roleId )
    {
        List result = null;

        result = orgDao.querySiteBeanByRoleId( roleId );

        return result;
    }

    public void setRelateOrgBoss( Long uid, Long orgId )
    {
        try
        {
            mysqlEngine.beginTransaction();

            SystemUserBean user = securityDao.querySingleSystemUserBeanById( uid );

            SystemOrganizationBean org = orgDao.querySingleSystemOrganizationBeanById( orgId );

            if( user == null || org == null
                || !user.getRelateOrgCode().equals( org.getLinearOrderFlag() ) )
            {
                return;
            }

            orgDao.updateOrgBossId( orgId, uid );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    private void disposeOrgClassAccRangeMustInDBTran( Long orgId, Long siteId,
        Map checkAccProtectClassIdResMap, SystemOrganizationBean orgbean, Long secType )
    {
        /**
         * 细粒度内容资源
         */
        // 删除内容洗粒度权限原数据
        orgDao.deleteRoleRangeOrgRelateResourceAcc( orgId, siteId, secType );

        Iterator accClassIdInfoIter = checkAccProtectClassIdResMap.entrySet().iterator();
        Entry entry = null;
        SecurityResourceBean resBean = null;
        String[] checkedClassIds = null;
        // 包含所选acc以及其父acc的id set
        Set idSet = new HashSet();
        Map checkAccResIdMap = new HashMap();
        while ( accClassIdInfoIter.hasNext() )
        {
            entry = ( Entry ) accClassIdInfoIter.next();
            resBean = ( SecurityResourceBean ) entry.getKey();
            checkedClassIds = ( String[] ) entry.getValue();

            // 转换为idSet,判断父ID
            for ( int j = 0; j < checkedClassIds.length; j++ )
            {
                Long classId = Long.valueOf( StringUtil.getLongValue(
                    ( String ) checkedClassIds[j], -1 ) );

                // 必须是合法的ID类型
                if( classId.longValue() > 0 )
                {
                    idSet.add( classId );
                }
            }

            Long targetResId = resBean.getSecResId();
            checkAccResIdMap.put( targetResId, checkedClassIds );
            Long classId = null;
            Long parentClassId = null;
            // 每一种细粒度操作集合的classId,

            mysqlEngine.startBatch();

            // 对于选取的每个栏目都要独立处理
            for ( int j = 0; j < checkedClassIds.length; j++ )
            {
                classId = Long
                    .valueOf( StringUtil.getLongValue( ( String ) checkedClassIds[j], -1 ) );

                if( classId.longValue() < 1 )
                {
                    continue;
                }

                // 主classId
                orgDao.saveRoleRangeOrgRelateResourceAcc( orgId, targetResId, siteId, secType,
                    classId );

                // 各相关父ID,如果没有check的话,强制check
                parentClassId = orgDao.querySingleParentClassIdByClassId( classId );
                while ( parentClassId != null && parentClassId.longValue() > 0 )
                {
                    // 不为根栏目,存入资源表,以便显示栏目树

                    if( !idSet.contains( parentClassId ) )
                    {
                        // 存入resId为-1,表示只是一个没有授权的辅助classId
                        orgDao.saveRoleRangeOrgRelateResourceAcc( orgId, Long.valueOf( -1 ),
                            siteId, secType, parentClassId );
                        // 加入已经存在classId
                        idSet.add( parentClassId );
                    }

                    // 直系父栏目
                    parentClassId = orgDao.querySingleParentClassIdByClassId( parentClassId );
                }
            }

            mysqlEngine.executeBatch();
        }

        // 变跟后所需处理内容部分的相关Org acc资源
        List needDisposeContentAccRangeList = orgDao.queryOrganizationAccResIdByOrgLinerFlag(
            orgbean.getLinearOrderFlag(), secType );

        Long resId = null;
        Long orgDeleteId = null;
        Long accId = null;
        String[] accIdstrs = null;

        Map value = null;
        for ( int z = 0; z < needDisposeContentAccRangeList.size(); z++ )
        {
            value = ( Map ) needDisposeContentAccRangeList.get( z );
            resId = ( Long ) value.get( "resId" );
            accId = ( Long ) value.get( "accId" );
            orgDeleteId = ( Long ) value.get( "orgId" );

            // parentId的情况
            if( resId.longValue() == -1 )
            {
                if( !idSet.contains( accId ) )
                {
                    orgDao.deleteRoleAccRangeOrgRelateResourceByOrgAndRes( siteId, orgDeleteId,
                        Long.valueOf( -1 ), accId, secType );
                }

                continue;
            }

            // 取某一组细粒度权限的所有已选择accId
            accIdstrs = ( String[] ) checkAccResIdMap.get( resId );

            boolean haveAcc = false;
            if( accIdstrs != null )
            {
                for ( int c = 0; c < accIdstrs.length; c++ )
                {
                    Long accTargetId = Long.valueOf( StringUtil.getLongValue(
                        ( String ) accIdstrs[c], -1 ) );

                    if( accTargetId.longValue() > 0 )
                    {
                        // 若当前DB中存在的对应res的accId存在当前已选accID中,则通过
                        if( accId.equals( accTargetId ) )
                        {
                            haveAcc = true;
                            break;
                        }
                    }
                }
            }

            if( !haveAcc )
            {
                orgDao.deleteRoleAccRangeOrgRelateResourceByOrgAndRes( siteId, orgDeleteId, resId,
                    accId, secType );
            }

        }
    }

    private void disposeOrgClassAccRangeMustInDBTranForNewClassMode( Long orgId, Long siteId,
        Map checkAccProtectClassIdResMap, SystemOrganizationBean orgbean, Long secType )
    {
        /**
         * 细粒度内容资源
         */

        Iterator accClassIdInfoIter = checkAccProtectClassIdResMap.entrySet().iterator();
        Entry entry = null;
        SecurityResourceBean resBean = null;
        String[] checkedClassIds = null;
        // 包含所选acc以及其父acc的id set
        Set idSet = new HashSet();
        Map checkAccResIdMap = new HashMap();
        while ( accClassIdInfoIter.hasNext() )
        {
            entry = ( Entry ) accClassIdInfoIter.next();
            resBean = ( SecurityResourceBean ) entry.getKey();
            checkedClassIds = ( String[] ) entry.getValue();

            // 转换为idSet,判断父ID
            for ( int j = 0; j < checkedClassIds.length; j++ )
            {
                Long classId = Long.valueOf( StringUtil.getLongValue(
                    ( String ) checkedClassIds[j], -1 ) );

                // 必须是合法的ID类型
                if( classId.longValue() > 0 )
                {
                    idSet.add( classId );
                }
            }

            Long targetResId = resBean.getSecResId();
            checkAccResIdMap.put( targetResId, checkedClassIds );
            Long classId = null;
            Long parentClassId = null;
            // 每一种细粒度操作集合的classId,

            mysqlEngine.startBatch();

            // 对于选取的每个栏目都要独立处理
            for ( int j = 0; j < checkedClassIds.length; j++ )
            {
                classId = Long
                    .valueOf( StringUtil.getLongValue( ( String ) checkedClassIds[j], -1 ) );

                if( classId.longValue() < 1 )
                {
                    continue;
                }

                // 主classId
                orgDao.saveRoleRangeOrgRelateResourceAcc( orgId, targetResId, siteId, secType,
                    classId );

                // 各相关父ID,如果没有check的话,强制check
                parentClassId = orgDao.querySingleParentClassIdByClassId( classId );
                while ( parentClassId != null && parentClassId.longValue() > 0 )
                {
                    // 不为根栏目,存入资源表,以便显示栏目树

                    if( !idSet.contains( parentClassId ) )
                    {
                        // 存入resId为-1,表示只是一个没有授权的辅助classId
                        orgDao.saveRoleRangeOrgRelateResourceAcc( orgId, Long.valueOf( -1 ),
                            siteId, secType, parentClassId );
                        // 加入已经存在classId
                        idSet.add( parentClassId );
                    }

                    // 直系父栏目
                    parentClassId = orgDao.querySingleParentClassIdByClassId( parentClassId );
                }
            }

            mysqlEngine.executeBatch();
        }

    }
}
