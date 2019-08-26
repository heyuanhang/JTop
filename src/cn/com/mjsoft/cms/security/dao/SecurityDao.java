package cn.com.mjsoft.cms.security.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.member.bean.MemberAccRuleBean;
import cn.com.mjsoft.cms.member.dao.MemberBeanTransform;
import cn.com.mjsoft.cms.member.dao.vo.MemberAccRule;
import cn.com.mjsoft.cms.organization.bean.SystemOrganizationBean;
import cn.com.mjsoft.cms.security.bean.MemberRoleBean;
import cn.com.mjsoft.cms.security.bean.MemberSecurityResourceBean;
import cn.com.mjsoft.cms.security.bean.SecurityResourceBean;
import cn.com.mjsoft.cms.security.bean.SystemLoginUser;
import cn.com.mjsoft.cms.security.bean.SystemRoleBean;
import cn.com.mjsoft.cms.security.bean.SystemUserBean;
import cn.com.mjsoft.cms.security.dao.vo.MemberRole;
import cn.com.mjsoft.cms.security.dao.vo.MemberSecurityResource;
import cn.com.mjsoft.cms.security.dao.vo.SecurityResource;
import cn.com.mjsoft.cms.security.dao.vo.SystemRole;
import cn.com.mjsoft.cms.security.dao.vo.SystemUser;
import cn.com.mjsoft.cms.site.dao.SiteGroupBeanTransform;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;

public class SecurityDao
{
    private static Logger log = Logger.getLogger( SecurityDao.class );

    public static Map cacheManager = new HashMap();

    static
    {

    }

    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public SecurityDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public UpdateState save( SecurityResource securityResourceVO )
    {
        return pe.save( securityResourceVO );
    }

    public UpdateState save( MemberSecurityResource securityResourceVO )
    {
        return pe.save( securityResourceVO );
    }

    /**
     * 根据条件取安全资源,无关状态
     * 
     * @param dataProtectType
     * @return
     */
    public List querySecurityResourceBeanByProtectType( Integer dataProtectType )
    {
        String sql = "select * from securityresource where dataProtectType=? order by linearOrderFlag asc";

        List resultList = pe.query( sql, new Object[] { dataProtectType },
            new SecurityResourceBeanTransform() );

        return resultList;
    }

    /**
     * 根据条件取安全资源,无关状态
     * 
     * @param dataProtectType
     * @return
     */
    public List queryMemberSecurityResourceBeanByProtectType(
        Integer dataProtectType, Long siteId )
    {
        String sql = "select * from member_securityresource where dataProtectType=? and siteId=? order by linearOrderFlag asc";

        List resultList = pe.query( sql,
            new Object[] { dataProtectType, siteId },
            new MemberSecurityResourceBeanTransform() );

        return resultList;
    }

    /**
     * 根据条件取所有安全资源,无关状态
     * 
     * @param dataProtectType
     * @return
     */
    public List queryAllSecurityResourceBean()
    {
        String sql = "select * from securityresource order by linearOrderFlag asc";

        List resultList = pe.query( sql, new SecurityResourceBeanTransform() );

        return resultList;
    }

    /**
     * 根据条件取所有安全资源,无关状态
     * 
     * @param dataProtectType
     * @return
     */
    public List queryAllMemberSecurityResourceBean( Long siteId )
    {
        String sql = "select * from member_securityresource where siteId=? order by linearOrderFlag asc";

        List resultList = pe.query( sql, new Object[] { siteId },
            new MemberSecurityResourceBeanTransform() );

        return resultList;
    }

    /**
     * 根据条件取所有安全资源,无关状态，返回Map形式的受保护资源uri:资源描叙信息对
     * 
     * @param dataProtectType
     * @return
     */
    public Map queryAllSecurityResourceBeanMap()
    {
        String sql = "select sr.secResId, sr.target, sr.dataProtectType, sdt.accSymbol, sdt.dataTypeId, sdt.accBehaviorClass from securityresource sr left join security_data_type sdt on sr.dataSecTypeId=sdt.dataTypeId";

        Map result = new HashMap();
        pe.query( sql, new SecurityResourceBeanMapTransform( result ) );

        return result;
    }

    /**
     * (会员)根据条件取所有安全资源,无关状态，返回Map形式的受保护资源uri:资源描叙信息对
     * 
     * @param dataProtectType
     * @return
     */
    public Map queryAllMemberSecurityResourceBeanMap()
    {
        String sql = "select * from member_securityresource";

        Map result = new HashMap();
        pe.query( sql, new MemberSecurityResourceBeanMapTransform( result ) );

        return result;
    }

    public List queryAllSecurityResourceIds()
    {
        String sql = "select secResId from securityresource";

        List resultList = pe.querySingleCloumn( sql, Long.class );

        return resultList;
    }

    public SecurityResourceBean querySingleSecurityResourceInfoBean( Long resId )
    {
        String sql = "select sr.*,pr.resourceName as parentName from securityresource sr left join securityresource pr on sr.parentResId=pr.secResId where sr.secResId=?";

        SecurityResourceBean bean = ( SecurityResourceBean ) pe.querySingleRow(
            sql, new Object[] { resId },
            new SecurityResourceBeanWithParentInfoTransform() );

        return bean;
    }

    public MemberSecurityResourceBean querySingleMemberSecurityResourceInfoBean(
        Long resId )
    {
        String sql = "select sr.*,pr.resourceName as parentName from member_securityresource sr left join member_securityresource pr on sr.parentResId=pr.secResId where sr.secResId=?";

        MemberSecurityResourceBean bean = ( MemberSecurityResourceBean ) pe
            .querySingleRow( sql, new Object[] { resId },
                new MemberSecurityResourceBeanWithParentInfoTransform() );

        return bean;
    }

    public SecurityResourceBean querySingleSecurityResourceInfoBean(
        String linear )
    {
        String sql = "select sr.*,pr.resourceName as parentName from securityresource sr left join securityresource pr on sr.parentResId=pr.secResId where sr.linearOrderFlag=?";

        SecurityResourceBean bean = ( SecurityResourceBean ) pe.querySingleRow(
            sql, new Object[] { linear },
            new SecurityResourceBeanWithParentInfoTransform() );

        return bean;
    }

    public MemberSecurityResourceBean querySingleMemberSecurityResourceInfoBean(
        String linear )
    {
        String sql = "select sr.*,pr.resourceName as parentName from member_securityresource sr left join member_securityresource pr on sr.parentResId=pr.secResId where sr.linearOrderFlag=?";

        MemberSecurityResourceBean bean = ( MemberSecurityResourceBean ) pe
            .querySingleRow( sql, new Object[] { linear },
                new MemberSecurityResourceBeanWithParentInfoTransform() );

        return bean;
    }

    public SecurityResourceBean querySingleSecurityResourceInfoBeanByFlag(
        String flag )
    {
        String sql = "select sr.* from securityresource sr where sr.sysFlag=?";

        SecurityResourceBean bean = ( SecurityResourceBean ) pe.querySingleRow(
            sql, new Object[] { flag }, new SecurityResourceBeanTransform() );

        return bean;
    }

    public List querySecurityResourceBeanByLinear( String linear )
    {
        String sql = "select * from securityresource where linearOrderFlag like '"
            + linear + "%' order by linearOrderFlag asc";

        return pe.query( sql, new SecurityResourceBeanTransform() );
    }

    public List queryMemberSecurityResourceBeanByLinear( String linear,
        Long siteId )
    {
        String sql = "select * from member_securityresource where siteId=? and linearOrderFlag like '"
            + linear + "%' order by linearOrderFlag asc";

        return pe.query( sql, new Object[] { siteId },
            new MemberSecurityResourceBeanTransform() );
    }

    public void updateSecurityResourcelinearOrderFlagByResId( Long secResId,
        String linearFlag )
    {
        String sql = "update securityresource set linearOrderFlag=? where secResId=?";

        pe.update( sql, new Object[] { linearFlag, secResId } );
    }

    public void updateMemberSecurityResourcelinearOrderFlagByResId(
        Long secResId, String linearFlag )
    {
        String sql = "update member_securityresource set linearOrderFlag=? where secResId=?";

        pe.update( sql, new Object[] { linearFlag, secResId } );
    }

    public List querySecurityResourceBeanByParentLinear( String linear )
    {
        String sql = "select * from securityresource where parentResId=(select secResId from securityresource where linearOrderFlag=?) order by linearOrderFlag asc";

        return pe.query( sql, new Object[] { linear },
            new SecurityResourceBeanTransform() );
    }

    public List queryMemberSecurityResourceBeanByParentLinear( String linear )
    {
        String sql = "select * from member_securityresource where parentResId=(select secResId from member_securityresource where linearOrderFlag=?) order by linearOrderFlag asc";

        return pe.query( sql, new Object[] { linear },
            new MemberSecurityResourceBeanTransform() );
    }

    public List querySecurityResourceBeanByLayer( Integer layer )
    {
        String sql = "select * from securityresource where layer=? order by linearOrderFlag asc";

        return pe.query( sql, new Object[] { layer },
            new SecurityResourceBeanTransform() );
    }

    public List queryMemberSecurityResourceBeanByLayer( Integer layer,
        Long siteId )
    {
        String sql = "select * from member_securityresource where layer=? and siteId=? order by linearOrderFlag asc";

        return pe.query( sql, new Object[] { layer, siteId },
            new MemberSecurityResourceBeanTransform() );
    }

    public List querySecurityResourceBeanMainMode()
    {
        String sql = "select * from securityresource where (resourceType=1 or resourceType=2) order by linearOrderFlag asc";

        return pe.query( sql, new SecurityResourceBeanTransform() );
    }

    public List queryMemberSecurityResourceBeanMainMode( Long siteId )
    {
        String sql = "select * from member_securityresource where (resourceType=1 or resourceType=2) and siteId=? order by linearOrderFlag asc";

        return pe.query( sql, new Object[] { siteId },
            new MemberSecurityResourceBeanTransform() );
    }

    public List querySecurityResRalateRoleIdByFlag( String flag )
    {
        String sql = "select distinct roleId from role_relate_resource rrr where rrr.secResId=(select secResId from securityresource sr where sr.sysFlag=?)";

        return pe.querySingleCloumn( sql, new Object[] { flag }, Long.class );
    }

    public List queryAccSecurityResRalateRoleIdByFlag( Long resId, Long accId,
        Long siteId )
    {
        String sql = "select distinct roleId from role_relate_resource_acc rrr where rrr.secResId=? and rrr.accId=? and rrr.siteId=?";

        return pe.querySingleCloumn( sql,
            new Object[] { resId, accId, siteId }, Long.class );
    }

    /**
     * 获取资源的孩子个数
     * 
     * @param resId
     * @return
     */
    public Integer queryResourceChildrenCount( Long resId )
    {
        String sql = "select count(*) from securityresource where parentResId=?";
        return ( Integer ) pe.querySingleObject( sql, new Object[] { resId },
            Integer.class );
    }

    /**
     * 获取会员资源的孩子个数
     * 
     * @param resId
     * @return
     */
    public Integer queryMemberResourceChildrenCount( Long resId )
    {
        String sql = "select count(*) from member_securityresource where parentResId=?";
        return ( Integer ) pe.querySingleObject( sql, new Object[] { resId },
            Integer.class );
    }

    /**
     * 获取指定父节点的最后一个兄弟
     * 
     * @param layer
     * @param parentResId
     * @return
     */
    public SecurityResourceBean queryLastResInfoBeanByLayer( Integer layer,
        Long parentResId )
    {
        String sql = "select * from securityresource where layer=? and parentResId=? order by linearOrderFlag desc limit 1";

        return ( SecurityResourceBean ) pe.querySingleRow( sql, new Object[] {
            layer, parentResId }, new SecurityResourceBeanTransform() );
    }

    /**
     * 获取指定父节点的最后一个兄弟（会员）
     * 
     * @param layer
     * @param parentResId
     * @return
     */
    public MemberSecurityResourceBean queryLastMemberResInfoBeanByLayer(
        Integer layer, Long parentResId )
    {
        String sql = "select * from member_securityresource where layer=? and parentResId=? order by linearOrderFlag desc limit 1";

        return ( MemberSecurityResourceBean ) pe.querySingleRow( sql,
            new Object[] { layer, parentResId },
            new MemberSecurityResourceBeanTransform() );
    }

    /**
     * 设定最后一个孩子节点状态,isLastChild(true):为最后一个孩子 ,isLastChild(false):最后一个孩子
     * 
     * @param classId
     * @param lastStatus
     */
    public void updateResLastChildFlag( Long resId, Boolean lastChildFlag )
    {
        String sql = "update securityresource set isLastChild=? where secResId=?";

        pe.update( sql, new Object[] { lastChildFlag, resId } );
    }

    /**
     * 设定最后一个孩子节点状态,isLastChild(true):为最后一个孩子 ,isLastChild(false):最后一个孩子(会员)
     * 
     * @param classId
     * @param lastStatus
     */
    public void updateMemberResLastChildFlag( Long resId, Boolean lastChildFlag )
    {
        String sql = "update member_securityresource set isLastChild=? where secResId=?";

        pe.update( sql, new Object[] { lastChildFlag, resId } );
    }

    public void updateResNodeLastChildState( Long secResId, Short flag )
    {
        String sql = "update securityresource set isLastChild=? where secResId=?";

        pe.update( sql, new Object[] { flag, secResId } );

    }

    public void updateMemberResNodeLastChildState( Long secResId, Short flag )
    {
        String sql = "update member_securityresource set isLastChild=? where secResId=?";

        pe.update( sql, new Object[] { flag, secResId } );

    }

    public List querySecurityResourceBeanByParentResIdAndType(
        Long parentResId, Short useState, Short type )
    {
        String sql = "select * from securityresource where parentResId=? and userState=? and type=? order by linearOrderFlag asc";

        List resultList = pe.query( sql, new Object[] { parentResId, useState,
            type }, new SecurityResourceBeanTransform() );

        return resultList;
    }

    public List querySecurityResourceBeanByResId( Long resId, Integer useState,
        Integer protectType )
    {
        String sql = "select * from securityresource where resId=? and useState=? and dataProtectType=? order by linearOrderFlag asc";

        List resultList = pe.query( sql, new Object[] { resId, useState,
            protectType }, new SecurityResourceBeanTransform() );

        return resultList;
    }

    public List querySecurityResourceBeanByParentResId( Long parentResId,
        Integer useState, Integer protectType )
    {
        String sql = "select * from securityresource where parentResId=? and useState=? and dataProtectType=? order by linearOrderFlag asc";

        List resultList = pe.query( sql, new Object[] { parentResId, useState,
            protectType }, new SecurityResourceBeanTransform() );

        return resultList;
    }

    public List queryMemberSecurityResourceBeanByParentResId( Long parentResId,
        Integer useState, Integer protectType )
    {
        String sql = "select * from member_securityresource where parentResId=? and useState=? and dataProtectType=? order by linearOrderFlag asc";

        List resultList = pe.query( sql, new Object[] { parentResId, useState,
            protectType }, new MemberSecurityResourceBeanTransform() );

        return resultList;
    }

    public List querySecurityResourceBeanByParentSysFlag( String sysFlag,
        Integer useState, Integer protectType, Long dataSecType )
    {
        String sql = "select * from securityresource where parentResId=(select secResId from securityresource where sysFlag=?) and useState=? and dataProtectType=? and dataSecTypeId=? order by linearOrderFlag asc";

        List resultList = pe.query( sql, new Object[] { sysFlag, useState,
            protectType, dataSecType }, new SecurityResourceBeanTransform() );

        return resultList;
    }

    public List queryAccGroupSecurityResourceBeanByParentSysFlag(
        Integer useState )
    {
        String sql = "select * from securityresource where useState=? and dataProtectType=2 and (resourceType=4 or resourceType=3) order by linearOrderFlag asc";

        List resultList = pe.query( sql, new Object[] { useState },
            new SecurityResourceBeanTransform() );

        return resultList;
    }

    public SecurityResourceBean querySingleSecurityResourceBean( Long resId,
        Integer useState, Integer protectType )
    {
        String sql = "select * from securityresource where secResId=? and useState=? and dataProtectType=?";

        SecurityResourceBean bean = ( SecurityResourceBean ) pe.querySingleRow(
            sql, new Object[] { resId, useState, protectType },
            new SecurityResourceBeanTransform() );

        return bean;
    }

    public MemberSecurityResourceBean querySingleMemberSecurityResourceBean(
        Long resId, Integer useState, Integer protectType )
    {
        String sql = "select * from member_securityresource where secResId=? and useState=? and dataProtectType=?";

        MemberSecurityResourceBean bean = ( MemberSecurityResourceBean ) pe
            .querySingleRow( sql,
                new Object[] { resId, useState, protectType },
                new MemberSecurityResourceBeanTransform() );

        return bean;
    }

    public SecurityResourceBean querySingleSecurityResourceBean(
        String sysFlag, Integer useState, Integer protectType, Long dataSecType )
    {
        String sql = "select * from securityresource where sysFlag=? and useState=? and dataProtectType=? and dataSecTypeId=? order by linearOrderFlag asc";

        SecurityResourceBean bean = ( SecurityResourceBean ) pe.querySingleRow(
            sql, new Object[] { sysFlag, useState, protectType, dataSecType },
            new SecurityResourceBeanTransform() );

        return bean;
    }

    public SecurityResourceBean querySingleSecurityResourceBean( Long resId )
    {
        String sql = "select * from securityresource where secResId=?";

        SecurityResourceBean bean = ( SecurityResourceBean ) pe
            .querySingleBean( sql, new Object[] { resId },
                SecurityResourceBean.class );

        return bean;
    }

    public void deleteSecurityResourceByLinearFlag( String linearOrderFlag )
    {
        String sql = "delete from securityresource where linearOrderFlag like ?";

        pe.update( sql, new Object[] { linearOrderFlag + "%" } );
    }

    public void deleteMemberSecurityResourceByLinearFlag( String linearOrderFlag )
    {
        String sql = "delete from member_securityresource where linearOrderFlag like ?";

        pe.update( sql, new Object[] { linearOrderFlag + "%" } );
    }

    public void updateSecurityResourceSimple( SecurityResource resource )
    {
        String sql = "update securityresource set resourceName=?, resourceDesc=?, resourceType=?, dataProtectType=?, sysFlag=?, dataSecTypeId=?, useState=?, target=?, creator=?, icon=? where secResId=?";
        pe.update( sql, new Object[] { resource.getResourceName(),
            resource.getResourceDesc(), resource.getResourceType(),
            resource.getDataProtectType(), resource.getSysFlag(),
            resource.getDataSecTypeId(), resource.getUseState(),
            resource.getTarget(), resource.getCreator(), resource.getIcon(),
            resource.getSecResId() } );
    }

    public void updateMemberSecurityResourceSimple(
        MemberSecurityResource resource )
    {
        String sql = "update member_securityresource set resourceName=?, resourceDesc=?, resourceType=?, dataProtectType=?, sysFlag=?, dataSecTypeId=?, useState=?, target=?, creator=?, icon=? where secResId=?";
        pe.update( sql, new Object[] { resource.getResourceName(),
            resource.getResourceDesc(), resource.getResourceType(),
            resource.getDataProtectType(), resource.getSysFlag(),
            resource.getDataSecTypeId(), resource.getUseState(),
            resource.getTarget(), resource.getCreator(), resource.getIcon(),
            resource.getSecResId() } );
    }

    public void updateSecurityResourceLeafStatus( Long resId, Integer isLeaf )
    {
        String sql = "update securityresource set isLeaf=? where secResId=?";
        pe.update( sql, new Object[] { isLeaf, resId } );
    }

    public void updateMemberSecurityResourceLeafStatus( Long resId,
        Integer isLeaf )
    {
        String sql = "update member_securityresource set isLeaf=? where secResId=?";
        pe.update( sql, new Object[] { isLeaf, resId } );
    }

    /**
     * 获取同级兄弟中比自己位置靠前的第一个兄弟资源
     * 
     * @param classId
     * @param parentId
     * @return
     */
    public Long queryUpBrotherNodeByClassIdAndTheirParentId( Long resId,
        Long parentResId )
    {
        log
            .info( "[DAO方法] queryUpBrotherResNodeByResIdAndTheirParentId() [参数] classId:"
                + resId + " ,parentId:" + parentResId );

        String sql = "select secResId from securityresource"
            + " where linearOrderFlag <(select linearOrderFlag from securityresource where secResId=?) and linearOrderFlag>(select linearOrderFlag from securityresource where secResId=?)  and  layer=(select layer from securityresource where secResId=?)"
            + " order by linearOrderFlag desc limit 1";

        String sqlForTopLayerNode = "select secResId from securityresource"
            + " where linearOrderFlag <(select linearOrderFlag from securityresource where secResId=?) and layer=(select layer from securityresource where secResId=?)"
            + " order by linearOrderFlag desc limit 1";

        Long result = null;
        if( parentResId.longValue() == -1 )
        {
            result = ( Long ) pe.querySingleObject( sqlForTopLayerNode,
                new Object[] { resId, resId }, Long.class );
        }
        else
        {
            result = ( Long ) pe.querySingleObject( sql, new Object[] { resId,
                parentResId, resId }, Long.class );
        }

        //
        // result = ( Long ) pe.querySingleObject( buf.toString(),
        // new Object[] { classId }, Long.class );

        return result;
    }

    /**
     * 获取同级兄弟中比自己位置靠前的第一个兄弟资源(会员)
     * 
     * @param classId
     * @param parentId
     * @return
     */
    public Long queryMemberUpBrotherNodeByClassIdAndTheirParentId( Long resId,
        Long parentResId )
    {
        log
            .info( "[DAO方法] queryMemberUpBrotherNodeByClassIdAndTheirParentId() [参数] classId:"
                + resId + " ,parentId:" + parentResId );

        String sql = "select secResId from member_securityresource"
            + " where linearOrderFlag <(select linearOrderFlag from member_securityresource where secResId=?) and linearOrderFlag>(select linearOrderFlag from member_securityresource where secResId=?)  and  layer=(select layer from member_securityresource where secResId=?)"
            + " order by linearOrderFlag desc limit 1";

        String sqlForTopLayerNode = "select secResId from member_securityresource"
            + " where linearOrderFlag <(select linearOrderFlag from member_securityresource where secResId=?) and layer=(select layer from member_securityresource where secResId=?)"
            + " order by linearOrderFlag desc limit 1";

        Long result = null;
        if( parentResId.longValue() == -1 )
        {
            result = ( Long ) pe.querySingleObject( sqlForTopLayerNode,
                new Object[] { resId, resId }, Long.class );
        }
        else
        {
            result = ( Long ) pe.querySingleObject( sql, new Object[] { resId,
                parentResId, resId }, Long.class );
        }

        //
        // result = ( Long ) pe.querySingleObject( buf.toString(),
        // new Object[] { classId }, Long.class );

        return result;
    }

    public UpdateState saveRole( SystemRole role )
    {
        return pe.save( role );
    }

    public UpdateState saveMemberRole( MemberRole role )
    {
        return pe.save( role );
    }

    public void saveRoleAssociatedResource( Long roleId, Long resId )
    {
        String sql = "insert into role_relate_resource (roleId, secResId) values (?,?)";

        pe.update( sql, new Object[] { roleId, resId } );
    }

    public void saveMemberRoleAssociatedResource( Long roleId, Long resId )
    {
        String sql = "insert into member_role_relate_resource (roleId, secResId) values (?,?)";

        pe.update( sql, new Object[] { roleId, resId } );
    }

    public void saveMemberRelateRole( Long memberId, Long roleId )
    {
        String sql = "insert into member_relate_role (userId, roleId) values (?,?)";

        pe.update( sql, new Object[] { memberId, roleId } );
    }

    public List queryAllSystemRoleBeanByUseMode( Integer useMode )
    {
        String sql = "select * from systemrole where useState=?";

        return pe.query( sql, new Object[] { useMode },
            new SystemRoleBeanTransform() );
    }

    public List queryAllSystemRoleBean()
    {
        String sql = "select * from systemrole order by roleId desc";

        return pe.queryBeanList( sql, SystemRoleBean.class );
    }

    public List querySystemRoleBeanByOrgId( Long orgId )
    {
        String sql = "select * from systemrole where orgId=? order by roleId desc";

        return pe.queryBeanList( sql, new Object[] { orgId },
            SystemRoleBean.class );
    }

    public List querySystemRoleBeanRelateOrgCode( String orgCode )
    {
        String sql = "select * from systemrole where orgId in (select orgId from system_organization where linearOrderFlag like ?) order by roleId desc";

        return pe.queryBeanList( sql, new Object[] { orgCode + "%" },
            SystemRoleBean.class );
    }

    public List querySystemRoleBeanByOrgId( Long orgId, Long start, Integer size )
    {
        String sql = "select * from systemrole where orgId=? order by roleId desc limit ?,?";

        return pe.queryBeanList( sql, new Object[] { orgId, start, size },
            SystemRoleBean.class );
    }

    public List queryAllSystemRoleBean( Long start, Integer size )
    {
        String sql = "select * from systemrole order by roleId desc limit ?,?";

        return pe.queryBeanList( sql, new Object[] { start, size },
            SystemRoleBean.class );
    }

    public List queryAllSystemRoleBean( List childOrgList )
    {
        if( childOrgList == null || childOrgList.isEmpty() )
        {
            return new ArrayList();
        }

        StringBuffer buf = new StringBuffer();

        SystemOrganizationBean orgBean = null;
        for ( int i = 0; i < childOrgList.size(); i++ )
        {
            orgBean = ( SystemOrganizationBean ) childOrgList.get( i );

            if( i + 1 != childOrgList.size() )
            {
                buf.append( orgBean.getOrgId() + ", " );
            }
            else
            {
                buf.append( orgBean.getOrgId() );
            }
        }

        String sql = "select * from systemrole where orgId in ("
            + buf.toString() + ")";

        return pe.query( sql, new SystemRoleBeanTransform() );
    }

    public List queryAllSystemRoleBean( List childOrgList, Long start,
        Integer size )
    {
        if( childOrgList == null || childOrgList.isEmpty() )
        {
            return new ArrayList();
        }

        StringBuffer buf = new StringBuffer();

        SystemOrganizationBean orgBean = null;
        for ( int i = 0; i < childOrgList.size(); i++ )
        {
            orgBean = ( SystemOrganizationBean ) childOrgList.get( i );

            if( i + 1 != childOrgList.size() )
            {
                buf.append( orgBean.getOrgId() + ", " );
            }
            else
            {
                buf.append( orgBean.getOrgId() );
            }
        }

        String sql = "select * from systemrole where orgId in ("
            + buf.toString() + ") order by roleId desc limit ?,?";

        return pe.query( sql, new Object[] { start, size },
            new SystemRoleBeanTransform() );
    }

    public List queryAllMemberRoleBean( Long siteId )
    {
        String sql = "select * from member_role where siteId=? order by roleId desc";

        return pe.query( sql, new Object[] { siteId },
            new MemberRoleBeanTransform() );

    }

    public SystemRoleBean querySingleSystemRoleBean( Long roleId )
    {
        String sql = "select * from systemrole where roleId=?";
        return ( SystemRoleBean ) pe.querySingleBean( sql,
            new Object[] { roleId }, SystemRoleBean.class );
    }

    public MemberRoleBean querySingleMemberRoleBean( Long roleId )
    {
        String sql = "select * from member_role where roleId=?";

        return ( MemberRoleBean ) pe.querySingleBean( sql,
            new Object[] { roleId }, MemberRoleBean.class );
    }

    public List queryRoleHaveHisResourceBeanByRoleId( Long roleId,
        Long parentResId )
    {
        String sql = "select sr.* from securityresource sr inner join (select secResId from role_relate_resource where roleId=?) reIds on reIds.secResId=sr.secResId where sr.parentResId=? order by linearOrderFlag";

        return pe.query( sql, new Object[] { roleId, parentResId },
            new SecurityResourceBeanTransform() );
    }

    public List queryRoleHaveHisResourceBeanByRoleId( Long roleId )
    {
        String sql = "select sr.* from securityresource sr inner join (select secResId from role_relate_resource where roleId=?) reIds on reIds.secResId=sr.secResId order by linearOrderFlag";

        return pe.query( sql, new Object[] { roleId },
            new SecurityResourceBeanTransform() );
    }

    public List queryMemberRoleHaveHisResourceBeanByRoleId( Long roleId )
    {
        String sql = "select sr.* from member_securityresource sr inner join (select secResId from member_role_relate_resource where roleId=?) reIds on reIds.secResId=sr.secResId order by linearOrderFlag asc";

        return pe.query( sql, new Object[] { roleId },
            new MemberSecurityResourceBeanTransform() );
    }

    public List queryRoleHaveHisResourceBeanByRoleArray( String roleSqlHelper,
        Long parentResId, Integer resType )
    {
        String sqlPartOne = "select distinct sr.* from securityresource sr inner join (select secResId from role_relate_resource where ";
        String sqlPartTwo = ") reIds on reIds.secResId=sr.secResId where sr.parentResId=? and sr.resourceType =? order by linearOrderFlag";

        return pe.query( sqlPartOne + roleSqlHelper + sqlPartTwo, new Object[] {
            parentResId, resType }, new SecurityResourceBeanTransform() );
    }
    
    public List queryMemberRoleHaveHisResourceBeanByRoleArray( String roleSqlHelper,
        Long parentResId, Integer resType )
    {
        String sqlPartOne = "select distinct sr.* from member_securityresource sr inner join (select secResId from member_role_relate_resource where ";
        String sqlPartTwo = ") reIds on reIds.secResId=sr.secResId where sr.parentResId=? and sr.resourceType =? order by linearOrderFlag";

        return pe.query( sqlPartOne + roleSqlHelper + sqlPartTwo, new Object[] {
            parentResId, resType }, new MemberSecurityResourceBeanTransform() );
    }

    public List queryRoleHaveHisResourceBeanByRoleArray( String roleSqlHelper,
        Integer resType )
    {
        String sqlPartOne = "select distinct sr.* from securityresource sr inner join (select secResId from role_relate_resource where ";
        String sqlPartTwo = ") reIds on reIds.secResId=sr.secResId and sr.resourceType =? order by linearOrderFlag";

        return pe.query( sqlPartOne + roleSqlHelper + sqlPartTwo,
            new Object[] { resType }, new SecurityResourceBeanTransform() );
    }
    
    public List queryMemberRoleHaveHisResourceBeanByRoleArray( String roleSqlHelper,
        Integer resType )
    {
        String sqlPartOne = "select distinct sr.* from member_securityresource sr inner join (select secResId from member_role_relate_resource where ";
        String sqlPartTwo = ") reIds on reIds.secResId=sr.secResId and sr.resourceType =? order by linearOrderFlag";

        return pe.query( sqlPartOne + roleSqlHelper + sqlPartTwo,
            new Object[] { resType }, new MemberSecurityResourceBeanTransform() );
    }

    public void queryAllAuthorizationInfo()
    {
        // TODO Auto-generated method stub

    }

    public List queryAllResourceIDs()
    {
        String sql = "select secResId from securityresource";

        return pe.querySingleCloumn( sql, Long.class );
    }

    public List queryAllResourceRelateRoleResId()
    {
        String sql = "select ids.secResId, sr.resourceName from securityresource sr inner join (select distinct secResId from role_relate_resource) ids on ids.secResId=sr.secResId";

        return pe.queryResultMap( sql );
    }

    public Map querySecurityResourceInfoByUriKey( String key )
    {
        String sql = "select sr.secResId, sr.dataProtectType, sdt.accSymbol, sdt.dataTypeId from securityresource sr left join security_data_type sdt on sr.dataSecTypeId=sdt.dataTypeId where sr.target=?";

        return pe.querySingleResultMap( sql, new Object[] { key } );
    }

    public List queryAllProtectTarget()
    {
        String sql = "select target from securityresource";

        return pe.querySingleCloumn( sql, String.class );
    }

    public List queryResourceRelateRoleByResource( String key )
    {
        String sql = "select distinct roleId from role_relate_resource rr, securityresource sr where rr.secResId=sr.secResId and sr.target=?";

        return pe.querySingleCloumn( sql, new Object[] { key }, Long.class );

    }

    public List queryMemberResourceRelateRoleByResource( String key )
    {
        String sql = "select distinct roleId from member_role_relate_resource rr, member_securityresource sr where rr.secResId=sr.secResId and sr.target=?";

        return pe.querySingleCloumn( sql, new Object[] { key }, Long.class );

    }

    public List queryResourceRelateRoleByResourceAndAccInfo( Long resId,
        Long accId, Long accSymbolId )
    {
        String sql = "select distinct roleId from role_relate_resource_acc rra where rra.secResId=? and rra.accId=? and dataSecTypeId=?";

        return pe.querySingleCloumn( sql, new Object[] { resId, accId,
            accSymbolId }, Long.class );

    }

    public List queryUserHaveResourceBeanByUserId( Long userId )
    {
        String sql = "select * from securityresource sr inner join (select rr.secResId from role_relate_resource rr inner join (select roleId from user_relate_role where userId=?) rids on rr.roleId=rids.roleId) rres on sr.secResId=rres.secResId";
        return pe.query( sql, new Object[] { userId },
            new SecurityResourceBeanTransform() );
    }

    public List queryRoleHaveHisResourceIdByRoleId( Long roleId )
    {
        String sql = "select secResId from role_relate_resource where roleId=?";
        return pe.querySingleCloumn( sql, new Object[] { roleId }, Long.class );
    }

    public SystemLoginUser queryLoginUserInfoBeanByName( String name )
    {
        String sql = "select su.*, so.orgId from systemuser su left join system_organization so on su.relateOrgCode=so.linearOrderFlag where su.userName=?";

        return ( SystemLoginUser ) pe.querySingleRow( sql,
            new Object[] { name }, new IUserBeanTransform() );
    }

    public List querySystemRoleBeanByUserId( Long userId )
    {
        String sql = "select sr.* from user_relate_role ur left join systemrole sr on ur.roleId=sr.roleId where ur.userId=?";

        return pe.query( sql, new Object[] { userId },
            new SystemRoleBeanTransform() );
    }

    public List queryMemberRoleBeanByUserId( Long userId )
    {
        String sql = "select sr.* from member_relate_role ur left join member_role sr on ur.roleId=sr.roleId where ur.userId=?";

        return pe.query( sql, new Object[] { userId },
            new MemberRoleBeanTransform() );
    }

    /**
     * 查询属于指定组织的所有管理员
     * 
     * @param childOrgList
     * @return
     */
    public List queryAllSystemUserBean( List childOrgList )
    {
        if( childOrgList == null || childOrgList.isEmpty() )
        {
            return new ArrayList();
        }

        StringBuffer buf = new StringBuffer();

        SystemOrganizationBean orgBean = null;
        for ( int i = 0; i < childOrgList.size(); i++ )
        {
            orgBean = ( SystemOrganizationBean ) childOrgList.get( i );

            if( i + 1 != childOrgList.size() )
            {
                buf.append( orgBean.getOrgId() + ", " );
            }
            else
            {
                buf.append( orgBean.getOrgId() );
            }
        }

        String sql = "select su.*, uru.roleId as roleId, sr.roleName as roleName from systemuser su left join user_relate_role uru on su.userId=uru.userId left join systemrole sr on uru.roleId=sr.roleId where su.orgId in ("
            + buf.toString() + ")";

        Map infoMap = new LinkedHashMap();

        pe.query( sql, new SystemUserBeanTransform( infoMap ) );

        return new ArrayList( infoMap.values() );
    }

    public List queryAllSystemUserBean()
    {
        String sql = "select * from systemuser";

        return pe.query( sql, new SystemUserBeanTransform() );
    }

    public List queryAllSystemOrgBossUserBean()
    {
        String sql = "select * from systemuser where userId in (select orgBossId from system_organization where orgBossId!=-1)";

        return pe.query( sql, new SystemUserBeanTransform() );
    }

    /**
     * 查询属于指定组织的所有管理员
     * 
     * @param childOrgList
     * @return
     */
    public List queryAllSystemUserBeanByRelateOrg( String orgCode )
    {
        String sql = "select distinct su.* from systemuser su left join user_relate_role uru on su.userId=uru.userId left join systemrole sr on uru.roleId=sr.roleId where su.relateOrgCode like'"
            + orgCode + "%' order by su.userId desc";

        Map infoMap = new LinkedHashMap();

        pe.query( sql, new SystemUserBeanTransform( infoMap ) );

        return new ArrayList( infoMap.values() );
    }

    /**
     * 查询属于指定组织的所有管理员
     * 
     * @param childOrgList
     * @return
     */
    public List queryAllSystemUserBeanByRelateOrg( String orgCode, Long start,
        Integer size )
    {
        String sql = "select distinct su.* from systemuser su left join user_relate_role uru on su.userId=uru.userId left join systemrole sr on uru.roleId=sr.roleId where su.relateOrgCode like'"
            + orgCode + "%' order by su.userId desc limit ?,?";

        Map infoMap = new LinkedHashMap();

        pe.query( sql, new Object[] { start, size },
            new SystemUserBeanTransform( infoMap ) );

        return new ArrayList( infoMap.values() );
    }

    public List queryAllSystemUserBeanByOrgCode( String orgCode )
    {
        String sql = "select distinct su.* from systemuser su left join user_relate_role uru on su.userId=uru.userId left join systemrole sr on uru.roleId=sr.roleId where su.relateOrgCode=? order by su.userId desc";

        Map infoMap = new LinkedHashMap();

        pe.query( sql, new Object[] { orgCode }, new SystemUserBeanTransform(
            infoMap ) );

        return new ArrayList( infoMap.values() );
    }

    public List queryAllSystemUserBeanByOrgCode( String orgCode, Long start,
        Integer size )
    {
        String sql = "select distinct su.* from systemuser su left join user_relate_role uru on su.userId=uru.userId left join systemrole sr on uru.roleId=sr.roleId where su.relateOrgCode=? order by su.userId desc limit ?,?";

        Map infoMap = new LinkedHashMap();

        pe.query( sql, new Object[] { orgCode, start, size },
            new SystemUserBeanTransform( infoMap ) );

        return new ArrayList( infoMap.values() );
    }

    public List querySystemUserBeanButExcludeRoleHaveUser( List orgIdList,
        Long roleId )
    {
        StringBuffer buf = new StringBuffer();

        String orgId = null;
        for ( int i = 0; i < orgIdList.size(); i++ )
        {
            orgId = ( String ) orgIdList.get( i );

            if( i + 1 != orgIdList.size() )
            {
                buf.append( orgId + ", " );
            }
            else
            {
                buf.append( orgId );
            }
        }

        String sql = "select su.* from systemuser su where su.userId not in (select uru.userId from user_relate_role uru where uru.roleId=?) and su.relateOrgCode in ("
            + buf.toString() + ") order by su.userId desc";

        return pe.query( sql, new Object[] { roleId },
            new SystemUserBeanTransform() );
    }

    public Long querySystemMemberBeanButExcludeRoleHaveUserCount( Long roleId )
    {
        String sql = "select count(*) from member su where su.memberId not in (select uru.userId from member_relate_role uru where uru.roleId=?) order by su.memberId desc";

        return ( Long ) pe.querySingleObject( sql, new Object[] { roleId },
            Long.class );
    }

    public List querySystemMemberBeanButExcludeRoleHaveUser( Long roleId,
        Long start, Integer size )
    {

        String sql = "select su.* from member su where su.memberId not in (select uru.userId from member_relate_role uru where uru.roleId=?) order by su.memberId desc limit ?,?";

        return pe.query( sql, new Object[] { roleId, start, size },
            new MemberBeanTransform() );
    }

    public SystemUserBean querySystemUserBeanByName( String name )
    {
        //String sql = "select esu.*, sr.roleId, sr.roleName from systemuser esu left join user_relate_role urr on esu.userId=urr.userId left join systemrole sr on urr.roleId=sr.roleId where esu.userName=?";
        String sql = "select distinct esu.*  from systemuser esu left join user_relate_role urr on esu.userId=urr.userId left join systemrole sr on urr.roleId=sr.roleId where esu.userName=?";
    
        return ( SystemUserBean ) pe.querySingleRow( sql,
            new Object[] { name }, new SystemUserBeanTransform() );
    }

    public SystemUserBean querySingleSystemUserBeanById( Long userId )
    {
        String sql = "select esu.* from systemuser esu where esu.userId=?";

        return ( SystemUserBean ) pe.querySingleRow( sql,
            new Object[] { userId }, new SystemUserBeanTransform() );
    }

    public void updateSystemUserBeanRelateOrgId( Long userId,
        String realteOrgIds )
    {
        String sql = "update systemuser set relateOrg=? where userId=?";

        pe.update( sql, new Object[] { realteOrgIds, userId } );
    }

    public List querySystemUserBeanByRelateRoleId( Long roleId )
    {
        String sql = "select esu.*,null as roleName, null as roleId from systemuser esu inner join (select userId from user_relate_role where roleId=?) ids on ids.userId=esu.userId";

        return pe.query( sql, new Object[] { roleId },
            new SystemUserBeanTransform() );
    }

    public List queryUserHaveSecurityRoleByUserId( Long userId )
    {
        String sql = "select sr.* from systemrole sr inner join (select roleId from user_relate_role where userId=?) ids on sr.roleId=ids.roleId";
        return pe.query( sql, new Object[] { userId },
            new SecurityRoleTransform() );
    }

    public List queryMemberHaveSecurityRoleByUserId( Long userId )
    {
        String sql = "select sr.* from member_role sr inner join (select roleId from member_relate_role where userId=?) ids on sr.roleId=ids.roleId";
        return pe.query( sql, new Object[] { userId },
            new SecurityRoleTransform() );
    }

    public void deleteRoleHaveHisResourceByRoleId( Long roleId )
    {
        String sql = "delete from role_relate_resource where roleId=?";
        pe.update( sql, new Object[] { roleId } );
    }

    public void deleteRoleHaveHisResourceByOrgLinearFlag( String orgLinearFlag )
    {

        String sql = "delete from role_relate_resource where roleId in (select roleId from systemrole where orgId in (select orgId from system_organization where linearOrderFlag like '"
            + orgLinearFlag + "%'))";
        pe.update( sql );

    }

    public void deleteRoleHaveHisAccResourceByOrgLinearFlag(
        String orgLinearFlag )
    {

        String sql = "delete from role_relate_resource_acc where roleId in (select roleId from systemrole where orgId in (select orgId from system_organization where linearOrderFlag like '"
            + orgLinearFlag + "%'))";
        pe.update( sql );

    }

    public void deleteMemberRoleHaveHisResourceByRoleId( Long roleId )
    {
        String sql = "delete from member_role_relate_resource where roleId=?";
        pe.update( sql, new Object[] { roleId } );
    }

    public void deleteRoleHaveHisResourceByRoleIdAndResId( Long roleId,
        Long resId )
    {
        String sql = "delete from role_relate_resource where roleId=? and secResId=?";
        pe.update( sql, new Object[] { roleId, resId } );
    }

    public void deleteRoleHaveHisAccResourceByRoleId( Long roleId )
    {
        String sql = "delete from role_relate_resource_acc where roleId=?";
        pe.update( sql, new Object[] { roleId } );
    }

    public void deleteRoleHaveHisAccResourceByRoleId( Long roleId, Long siteId )
    {
        String sql = "delete from role_relate_resource_acc where roleId=? and siteId=?";
        pe.update( sql, new Object[] { roleId, siteId } );
    }

    public void deleteRoleHaveHisAuditAccByRoleId( Long roleId )
    {
        String sql = "delete from role_relate_workflow_acc where roleId=?";
        pe.update( sql, new Object[] { roleId } );

    }

    public void deleteMemberRoleRealte( Long memberId )
    {
        String sql = "delete from member_relate_role where userId=?";
        pe.update( sql, new Object[] { memberId } );
    }

    public void deleteMemberRoleRealte( Long memberId, Long roleId )
    {
        String sql = "delete from member_relate_role where userId=? and roleId=?";
        pe.update( sql, new Object[] { memberId, roleId } );
    }

    public UpdateState saveUser( SystemUser user )
    {
        return pe.save( user );

    }

    public List querySiteHaveHisOrgAllUser( Long siteId )
    {
        String sql = "select * from systemuser where relateOrgCode in (select linearOrderFlag from system_organization where orgId in (select orgId from role_range_org_relate_site where siteId=?))";

        return pe.query( sql, new Object[] { siteId },
            new SystemUserBeanTransform() );
    }

    public List querySiteHaveHisOrgAllUser( Long siteId, Long excludeUserId )
    {
        String sql = "select * from systemuser where userId!=? and relateOrgCode in (select linearOrderFlag from system_organization where orgId in (select orgId from role_range_org_relate_site where siteId=?))";

        return pe.query( sql, new Object[] { excludeUserId, siteId },
            new SystemUserBeanTransform() );
    }

    public void saveUserAssociatedRole( Long userId, Long roleId )
    {
        String sql = "insert into user_relate_role (userId, roleId) values (?,?)";

        pe.update( sql, new Object[] { userId, roleId } );
    }

    public void saveRoleAssociatedAudit( Long roleId, Long auditResId )
    {
        String sql = "insert into role_relate_workflow_acc (roleId, accId) values (?,?)";

        pe.update( sql, new Object[] { roleId, auditResId } );

    }

    public void deleteAllUserHaveRole( Long userId )
    {
        String sql = "delete from user_relate_role where userId=?";

        pe.update( sql, new Object[] { userId } );
    }

    public void deleteUserForRole( Long userId, Long roleId )
    {
        String sql = "delete from user_relate_role where userId=? and roleId=?";

        pe.update( sql, new Object[] { userId, roleId } );
    }

    public void deleteRoleAssociatedUser( Long roleId )
    {
        String sql = "delete from user_relate_role where roleId=?";

        pe.update( sql, new Object[] { roleId } );
    }

    public void deleteUserAssociatedRole( Long userId )
    {
        String sql = "delete from user_relate_role where userId=?";

        pe.update( sql, new Object[] { userId } );
    }

    public void deleteMemberRoleAssociatedUser( Long roleId )
    {
        String sql = "delete from member_relate_role where roleId=?";

        pe.update( sql, new Object[] { roleId } );
    }

    public void updateSystemUser( SystemUser editUser )
    {
        String sql = "update systemuser set weixinName=?, relateOrgCode=?, userTrueName=?, phone=?, email=?, remark=?, useState=? where userId=?";
        pe.update( sql, new Object[] { editUser.getWeixinName(),
            editUser.getRelateOrgCode(), editUser.getUserTrueName(),
            editUser.getPhone(), editUser.getEmail(), editUser.getRemark(),
            editUser.getUseState(), editUser.getUserId() } );
    }

    public void updateSystemUserUseStatus( Long userId, Integer flag )
    {
        String sql = "update systemuser set useState=? where userId=?";
        pe.update( sql, new Object[] { flag, userId } );
    }

    public void updateSystemRole( SystemRole role )
    {
        String sql = "update systemrole set roleName=?, roleDesc=?, orgId=?, useState=? where roleId=?";

        pe.update( sql, new Object[] { role.getRoleName(), role.getRoleDesc(),
            role.getOrgId(), role.getUseState(), role.getRoleId() } );
    }

    public void updateMemberRole( MemberRole role )
    {
        String sql = "update member_role set roleName=?, roleDesc=? where roleId=?";

        pe.update( sql, role );
    }

    public void saveRoleAssociatedAccResource( Long roleId, Long secResId,
        String accId, Long dataSecTypeId, Long siteId )
    {
        String sql = "insert into role_relate_resource_acc (roleId, secResId, accId, dataSecTypeId, siteId) values (?,?,?,?,?)";

        pe.update( sql, new Object[] { roleId, secResId, accId, dataSecTypeId,
            siteId } );
    }

    public UpdateState deleteSingleSystemRole( Long roleId )
    {
        String sql = "delete from systemrole where roleId=?";

        return pe.update( sql, new Object[] { roleId } );
    }

    public UpdateState deleteSingleSystemUser( Long userId )
    {
        String sql = "delete from systemuser where userId=?";

        return pe.update( sql, new Object[] { userId } );
    }

    public UpdateState deleteSingleMemberRole( Long roleId )
    {
        String sql = "delete from member_role where roleId=?";

        return pe.update( sql, new Object[] { roleId } );
    }

    public UpdateState deleteAdminUser()
    {
        String sql = "delete from systemuser where userName='admin' and relateOrgCode='001'";

        return pe.update( sql );
    }

    public SystemUserBean queryAdminUser()
    {
        String sql = "select * from systemuser where userName='admin' and relateOrgCode='001'";

        return ( SystemUserBean ) pe.querySingleRow( sql,
            new SystemUserBeanTransform() );
    }

    public List queryResourceHaveAccId( Long siteId, Long roleId, Long secResId )
    {
        String sql = "select rra.*, ss.sysFlag from role_relate_resource_acc rra left join securityresource ss on rra.secResId=ss.secResId where rra.siteId=? and rra.roleId=? and rra.secResId=?";
        return pe.queryResultMap( sql,
            new Object[] { siteId, roleId, secResId } );
    }

    public List queryResourceHaveAccId( Long siteId, String roleIds, Long secResId  ,Integer secType)
    {
        String sql = "select rra.*, ss.sysFlag from role_relate_resource_acc rra left join securityresource ss on rra.secResId=ss.secResId where rra.siteId=? and rra.dataSecTypeId=? and "+roleIds+" and rra.secResId=?";
        return pe.queryResultMap( sql,
            new Object[] { siteId, secType,  secResId } );
    }
    
    public List queryResourceHaveAccIdByType( Long roleId, Long secResId,
        Integer resType )
    {
        String sql = "select rra.*, ss.sysFlag from role_relate_resource_acc rra left join securityresource ss on rra.secResId=ss.secResId where rra.roleId=? and rra.secResId=? and ss.resourceType=?";
        return pe.queryResultMap( sql,
            new Object[] { roleId, secResId, resType } );
    }
    
    public List queryResourceHaveAccIdByType( String roleIds, Long secResId,
        Integer resType ,Integer secType)
    {
        String sql = "select rra.*, ss.sysFlag from role_relate_resource_acc rra left join securityresource ss on rra.secResId=ss.secResId where "+roleIds+" and rra.secResId=? and rra.dataSecTypeId=? and ss.resourceType=?";
        return pe.queryResultMap( sql,
            new Object[] { secResId, secType,  resType } );
    }

    public List queryResourceHaveAccId( Long siteId, Long roleId )
    {
        String sql = "select rra.*, ss.sysFlag from role_relate_resource_acc rra left join securityresource ss on rra.secResId=ss.secResId where rra.siteId=? and rra.roleId=?";
        return pe.queryResultMap( sql, new Object[] { siteId, roleId } );
    }
    
    public List queryResourceHaveAccId( Long siteId, String roleIds, Integer secType )
    {
        String sql = "select rra.*, ss.sysFlag from role_relate_resource_acc rra left join securityresource ss on rra.secResId=ss.secResId where rra.siteId=? and rra.dataSecTypeId=? and "+roleIds;
        return pe.queryResultMap( sql, new Object[] { siteId, secType  } );
    }

    public List queryResourceHaveAccIdByType( Long roleId, Integer resType )
    {
        String sql = "select rra.*, ss.sysFlag from role_relate_resource_acc rra left join securityresource ss on rra.secResId=ss.secResId where rra.roleId=? and ss.resourceType=?";
        return pe.queryResultMap( sql, new Object[] { roleId, resType } );
    }
    
    public List queryResourceHaveAccIdByType( String roleIds, Integer resType, Integer secType)
    {
        String sql = "select rra.*, ss.sysFlag from role_relate_resource_acc rra left join securityresource ss on rra.secResId=ss.secResId where "+roleIds+" and ss.resourceType=? and rra.dataSecTypeId=? ";
        return pe.queryResultMap( sql, new Object[] { resType, secType } );
    }

    public List queryResourceHaveAccIdBySysFlag( Long roleId, String sysFlag )
    {
        String sql = "select * from role_relate_resource_acc where roleId=? and secResId=(select secResId from securityresource where sysFlag=?)";
        return pe.queryResultMap( sql, new Object[] { roleId, sysFlag } );
    }

    public List queryAuditAccIdList( Long roleId )
    {
        String sql = "select * from role_relate_workflow_acc where roleId=?";
        return pe.queryResultMap( sql, new Object[] { roleId } );
    }

    public List queryAuthRangeRelateResInfo( Long orgId )
    {
        String sql = "select * from securityresource sr inner join (select resId from role_range_org_relate_resource where orgId=?) ids on ids.resId=sr.secResId order by linearOrderFlag";
        return pe.query( sql, new Object[] { orgId },
            new SecurityResourceBeanTransform() );
    }

    public List queryAuthRangeRelateResInfoforFirstLayer( Long orgId )
    {
        String sql = "select * from securityresource sr inner join (select resId from role_range_fl_org_relate_res where orgId=?) ids on ids.resId=sr.secResId order by linearOrderFlag";
        return pe.query( sql, new Object[] { orgId },
            new SecurityResourceBeanTransform() );
    }

    public List queryAuthRangeRelateResInfoByParentOrgId( Long orgId )
    {
        String sql = "select * from securityresource sr inner join (select resId from role_range_org_relate_resource where orgId=(select parentId from system_organization where orgId=?)) ids on ids.resId=sr.secResId order by linearOrderFlag";
        return pe.query( sql, new Object[] { orgId },
            new SecurityResourceBeanTransform() );
    }

    public List queryRoleRangeOrgRelateResourceAcc( Long orgId, Long siteId,
        Integer dst )
    {
        String sql = "select * from role_range_org_relate_res_acc roaac left join securityresource ss on roaac.resId=ss.secResId where roaac.orgId=? and roaac.siteId=? and roaac.dataSecTypeId=?";
        return pe.queryResultMap( sql, new Object[] { orgId, siteId, dst } );
    }

    public List queryAllRoleRangeOrgRelateResourceAcc( Long siteId, Integer dst )
    {
        String sql = "select resIds.sysFlag, cc.classId as accId from contentclass cc , (select sysFlag from securityresource where dataSecTypeId=? and resourceType=4) resIds where cc.siteFlag = (select siteFlag from site_group where siteId=?)";
        return pe.queryResultMap( sql, new Object[] { dst, siteId } );
    }

    public List queryAllCommendRoleRangeOrgRelateResourceAcc( Long siteId,
        Integer dst )
    {
        String sql = "select resIds.sysFlag, cc.commendTypeId as accId from content_commend_type cc , (select sysFlag from securityresource where dataSecTypeId=? and resourceType=4) resIds where cc.siteFlag = (select siteFlag from site_group where siteId=?)";
        return pe.queryResultMap( sql, new Object[] { dst, siteId } );
    }
    
    public List queryAllGuestbookRoleRangeOrgRelateResourceAcc( Long siteId,
        Integer dst )
    {
        String sql = "select resIds.sysFlag, cc.configId as accId from guestbook_config cc , (select sysFlag from securityresource where dataSecTypeId=? and resourceType=4) resIds where cc.siteId = ?";
        return pe.queryResultMap( sql, new Object[] { dst, siteId } );
    }

    public Integer queryDataCountForDataSecTypeId( Long dataSecTypeId )
    {
        String sql = "select count(*) from security_data_type where dataTypeId=?";
        return ( Integer ) pe.querySingleObject( sql,
            new Object[] { dataSecTypeId }, Integer.class );
    }

    public void updateUserPassword( Long userId, String password )
    {
        String sql = "update systemuser set password=? where userId=?";
        pe.update( sql, new Object[] { password, userId } );
    }

    public List querySiteBeanByRoleId( Long orgId )
    {
        String sql = "select * from site_group where siteId in (select siteId from role_relate_site where roleId=?)";
        return pe.query( sql, new Object[] { orgId },
            new SiteGroupBeanTransform() );
    }

    public void deleteRoleRelateSite( Long roleId )
    {
        String sql = "delete from role_relate_site where roleId=?";
        pe.update( sql, new Object[] { roleId } );
    }

    public void deleteRangeOrgRelateResAccBySiteId( Long siteId )
    {
        String sql = "delete from role_range_org_relate_res_acc where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteRangeOrgRelateSiteAccBySiteId( Long siteId )
    {
        String sql = "delete from role_range_org_relate_site where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteOrgRelateResAccBySiteId( Long siteId )
    {
        String sql = "delete from role_relate_resource_acc where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteRoleByOrgId( String orgLinearFlag )
    {
        String sql = "delete from systemrole where orgId in (select orgId from system_organization where linearOrderFlag like '"
            + orgLinearFlag + "%')";
        pe.update( sql );
    }

    public void saveRoleRelateSite( Long roleId, Long managerSiteId )
    {
        String sql = "insert into role_relate_site (roleId, siteId) values (?,?)";
        pe.update( sql, new Object[] { roleId, managerSiteId } );
    }

    public void deleteRoleRelateAccResByNotInSite(
        Long[] currentCheckResIdArray, Long roleId )
    {
        StringBuffer buf;
        if( currentCheckResIdArray.length == 0 )
        {
            buf = new StringBuffer( " " );
        }
        else
        {
            buf = new StringBuffer( " and siteId not in (" );
            for ( int i = 0; i < currentCheckResIdArray.length; i++ )
            {
                if( i + 1 != currentCheckResIdArray.length )
                {
                    buf.append( currentCheckResIdArray[i] + ", " );
                }
                else
                {
                    buf.append( currentCheckResIdArray[i] );
                }
            }
            buf.append( ")" );
        }

        String sql = "delete from role_relate_resource_acc where roleId=?"
            + buf.toString();

        pe.update( sql, new Object[] { roleId } );

    }

    public List querySystemAllAccSecDataType()
    {
        String sql = "select * from security_data_type order by dataTypeId asc";

        return pe.queryResultMap( sql );
    }

    public List querySystemAllAccSecDataTypeId()
    {
        String sql = "select dataTypeId from security_data_type order by dataTypeId asc";

        return pe.querySingleCloumn( sql, Long.class );
    }

    public Map querySingleSysteAccSecDataType( Long secTid )
    {
        String sql = "select * from security_data_type where dataTypeId=?";

        return pe.querySingleResultMap( sql, new Object[] { secTid } );
    }

    public void saveAccSecDataType( Map valMap )
    {
        String sql = "insert into security_data_type (typeName, dtDesc, accSymbol, isSys, accBehaviorClass ) values (?, ?, ?, ?, ?)";

        pe.update( sql, valMap );
    }

    public void updateAccSecDataType( Map valMap )
    {
        String sql = "update security_data_type set typeName=?, dtDesc=?, accSymbol=?, accBehaviorClass=? where dataTypeId=?";

        pe.update( sql, valMap );
    }

    public void deleteAccSecDataType( Long id )
    {
        String sql = "delete from security_data_type where dataTypeId=? and isSys=0";

        pe.update( sql, new Object[] { id } );
    }

    public Integer querySecurityResType( Long parentResId )
    {
        String sql = "select resourceType from securityresource where secResId=?";

        return ( Integer ) pe.querySingleObject( sql,
            new Object[] { parentResId }, Integer.class );
    }

    public Integer queryMemberSecurityResType( Long parentResId )
    {
        String sql = "select resourceType from member_securityresource where secResId=?";

        return ( Integer ) pe.querySingleObject( sql,
            new Object[] { parentResId }, Integer.class );
    }

    public SystemRoleBean querySingleAdminRole()
    {
        String sql = "select * from systemrole where orgId=1 and roleName='系统管理员'";

        return ( SystemRoleBean ) pe.querySingleRow( sql,
            new SystemRoleBeanTransform() );
    }

    public Long querySecurityResRalateRoleCount( Long roleId, Long resId )
    {
        String sql = "select count(*) from role_relate_resource where roleId=? and secResId=?";
        return ( Long ) pe.querySingleObject( sql,
            new Object[] { roleId, resId } );
    }

    public List queryUsefullRoleAuthSecId()
    {
        String sql = "select secResId from securityresource where (dataProtectType=1)";
        return pe.querySingleCloumn( sql, Long.class );
    }

    public List queryAccIdBySecTypeIdAndRoleId( Long roleId, Long secTypeId )
    {
        String sql = "select distinct accId from role_relate_resource_acc where roleId=? and dataSecTypeId=?";

        return pe.querySingleCloumn( sql, new Object[] { roleId, secTypeId },
            Long.class );
    }

    public void save( MemberAccRule mcr )
    {
        pe.save( mcr );
    }

    public void updateMemberAccRule( MemberAccRule mcr )
    {
        String sql = "update member_acc_rule set accName=?, ruleDesc=?, minScore=?, minLever=?, roleIds=?, eft=? where accRuleId=?";

        pe.update( sql, mcr );
    }

    public void deleteMemberAccRuleById( Long id )
    {
        String sql = "delete from member_acc_rule where accRuleId=?";

        pe.update( sql, new Object[] { id } );
    }

    public MemberAccRuleBean querySingleMemberAccRule( Long accId )
    {
        String sql = "select * from member_acc_rule where accRuleId=?";

        return ( MemberAccRuleBean ) pe.querySingleBean( sql,
            new Object[] { accId }, MemberAccRuleBean.class );
    }

    public MemberAccRuleBean querySingleMemberAccRuleByClassId( Long classId )
    {
        String sql = "select * from member_acc_rule where accRuleId=(select accRuleId from member_class_acc where classId=?)";

        return ( MemberAccRuleBean ) pe.querySingleBean( sql,
            new Object[] { classId }, MemberAccRuleBean.class );
    }

    public MemberAccRuleBean querySingleMemberSubmitAccRuleByClassId(
        Long classId )
    {
        String sql = "select * from member_acc_rule where accRuleId=(select accRuleId from member_class_submit_acc where classId=?)";

        return ( MemberAccRuleBean ) pe.querySingleBean( sql,
            new Object[] { classId }, MemberAccRuleBean.class );
    }

    public List queryMemberAccRuleList( Long accId, Integer typeId )
    {
        String sql = "select * from member_acc_rule where siteId=? and typeId=? order by accRuleId desc";

        return pe.queryBeanList( sql, new Object[] { accId, typeId },
            MemberAccRuleBean.class );
    }

    public List queryMemberAccRuleList( Long siteId )
    {
        String sql = "select * from member_acc_rule where siteId=? order by accRuleId desc";

        return pe.queryBeanList( sql, new Object[] { siteId },
            MemberAccRuleBean.class );
    }

    public void saveMemberClassAcc( Long classId, Long accId, Long siteId )
    {
        String sql = "insert into member_class_acc (classId, accRuleId, siteId) values (?,?,?)";

        pe.update( sql, new Object[] { classId, accId, siteId } );
    }

    public void saveMemberClassSubmitAcc( Long classId, Long accId, Long siteId )
    {
        String sql = "insert into member_class_submit_acc (classId, accRuleId, siteId) values (?,?,?)";

        pe.update( sql, new Object[] { classId, accId, siteId } );
    }

    public void deleteMemberClassAccByClassId( Long classId )
    {
        String sql = "delete from member_class_acc where classId=?";

        pe.update( sql, new Object[] { classId } );
    }

    public void deleteMemberClassSubmitAccByClassId( Long classId )
    {
        String sql = "delete from member_class_submit_acc where classId=?";

        pe.update( sql, new Object[] { classId } );
    }

    public void deleteMemberClassAccBySiteId( Long siteId )
    {
        String sql = "delete from member_class_acc where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteMemberClassSubmitAccBySiteId( Long siteId )
    {
        String sql = "delete from member_class_submit_acc where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public Map querySingleMemberClassAcc( Long classId )
    {
        String sql = "select * from member_class_acc where classId=?";

        return pe.querySingleResultMap( sql, new Object[] { classId } );
    }

    public Map querySingleMemberSubmitClassAcc( Long classId )
    {
        String sql = "select * from member_class_submit_acc where classId=?";

        return pe.querySingleResultMap( sql, new Object[] { classId } );
    }

    public List queryMemberClassAccList( Long siteId )
    {
        String sql = "select * from member_class_acc where siteId=?";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public List queryMemberClassSubmitAccList( Long siteId )
    {
        String sql = "select * from member_class_submit_acc where siteId=?";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public void deleteRoleRelateAccResByRoleAndSecType( Long roleId,
        Long secTypeId, Long accId )
    {
        String sql = "delete from role_relate_resource_acc where roleId=? and dataSecTypeId=? and accId=?";

        pe.update( sql, new Object[] { roleId, secTypeId, accId } );
    }

    // public void saveMemberClassAccRelateRole( Long classId, Long roleId,
    // Long siteId )
    // {
    // String sql = "insert into member_acc_class_relate_role (classId, roleId,
    // siteId) values (?,?,?)";
    //
    // pe.update( sql, new Object[] { classId, roleId, siteId } );
    // }

    // public Set queryMemberClassAccRelateRoleByClassId( Long classId )
    // {
    // String sql = "select distinct(roleId) from member_acc_class_relate_role
    // where classId=?";
    //
    // return new HashSet( pe.querySingleCloumn( sql,
    // new Object[] { classId }, Long.class ) );
    // }

    // public void deleteMemberClassAccRelateRoleByClassId( Long classId )
    // {
    // String sql = "delete from member_acc_class_relate_role where classId=?";
    //
    // pe.update( sql, new Object[] { classId } );
    // }

    // public void deleteMemberClassAccRelateRoleBySiteId( Long siteId )
    // {
    // String sql = "delete from member_acc_class_relate_role where siteId=?";
    //
    // pe.update( sql, new Object[] { siteId } );
    // }

    

}
