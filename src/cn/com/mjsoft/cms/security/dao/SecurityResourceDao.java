package cn.com.mjsoft.cms.security.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.security.bean.SecurityResourceBean;
import cn.com.mjsoft.cms.security.dao.vo.SecurityResource;
import cn.com.mjsoft.cms.security.dao.vo.SystemRole;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;

public class SecurityResourceDao
{
    private static Logger log = Logger.getLogger( SecurityResourceDao.class );

    public static Map cacheManager = new HashMap();

    static
    {

    }

    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public SecurityResourceDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public UpdateState save( SecurityResource securityResourceVO )
    {
        return pe.save( securityResourceVO );
    }

    public List queryAllSecurityResourceBeanOrder()
    {
        String sql = "select * from securityresource where useState=1 order by linearOrderFlag";

        List resultList = pe.query( sql, new SecurityResourceBeanTransform() );

        return resultList;
    }

    public List queryAllSecurityResourceIds()
    {
        String sql = "select secResId from securityresource";

        List resultList = pe.querySingleCloumn( sql, Long.class );

        return resultList;
    }

    public SecurityResourceBean querySingleSecurityResourceBean( Long resId )
    {
        String sql = "select sr.*,pr.resourceName as parentName from securityresource sr left join securityresource pr on sr.parentResId=pr.secResId where sr.secResId=?";

        SecurityResourceBean bean = ( SecurityResourceBean ) pe.querySingleRow(
            sql, new Object[] { resId },
            new SecurityResourceBeanWithParentInfoTransform() );

        return bean;
    }

    /**
     * 获取指定父节点的最后一个兄弟
     * 
     * @param layer
     * @param parentResId
     * @return
     */
    public SecurityResourceBean queryLastResInfoBeanByLayer( Short layer,
        Long parentResId )
    {
        String sql = "select * from securityresource where layer=? and parentResId=? order by linearOrderFlag desc limit 1";
        return ( SecurityResourceBean ) pe.querySingleRow( sql, new Object[] {
            layer, parentResId }, new SecurityResourceBeanTransform() );
    }

    public void updateResNodeLastChildState( Long secResId, Short flag )
    {
        String sql = "update securityresource set isLastChild=? where secResId=?";

        pe.update( sql, new Object[] { flag, secResId } );

    }

    public List querySecurityResourceBeanByParentResIdAndType(
        Long parentResId, Short useState, Short type )
    {
        String sql = "select * from securityresource where parentResId=? and userState=? and type=? order by linearOrderFlag";

        List resultList = pe.query( sql, new Object[] { parentResId, useState,
            type }, new SecurityResourceBeanTransform() );

        return resultList;
    }

    public List querySecurityResourceBeanByParentResId( Long parentResId,
        Short useState )
    {
        String sql = "select * from securityresource where parentResId=? and userState=? order by linearOrderFlag";

        List resultList = pe.query( sql,
            new Object[] { parentResId, useState },
            new SecurityResourceBeanTransform() );

        return resultList;
    }

    public void deleteSecurityResourceByLinearFlag( String linearOrderFlag )
    {
        String sql = "delete from securityresource where linearOrderFlag like ?";

        pe.update( sql, new Object[] { linearOrderFlag + "%" } );
    }

    public void updateSecurityResourceSimple( SecurityResource resource )
    {
        String sql = "update securityresource set resourceName=?, resourceDesc=?, resourceType=?, dataProtectType=?, useState=?, target=?, creator=? where secResId=?";
        pe.update( sql,
            new Object[] { resource.getResourceName(),
                resource.getResourceDesc(), resource.getResourceType(),
                resource.getDataProtectType(), resource.getUseState(),
                resource.getTarget(), resource.getCreator(),
                resource.getSecResId() } );
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

    public UpdateState saveRole( SystemRole role )
    {
        return pe.save( role );
    }

    public void saveRoleAssociatedResource( Long roleId, Long resId )
    {
        String sql = "insert into role_relate_resource (roleId, secResId) values (?,?)";

        pe.update( sql, new Object[] { roleId, resId } );
    }

    public List queryAllSystemRoleBean()
    {
        String sql = "select * from systemrole";

        return pe.query( sql, new SystemRoleBeanTransform() );
    }

    public Object querySingleSystemRoleBean( Long roleId )
    {
        String sql = "select * from systemrole where roleId=?";
        return pe.querySingleRow( sql, new Object[] { roleId },
            new SystemRoleBeanTransform() );
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

    public List queryRoleHaveHisResourceBeanByRoleArray( String roleSqlHelper,
        Long parentResId )
    {
        String sqlPartOne = "select sr.* from securityresource sr inner join (select secResId from role_relate_resource where ";
        String sqlPartTwo = ") reIds on reIds.secResId=sr.secResId where sr.parentResId=? order by linearOrderFlag";

        return pe.query( sqlPartOne + roleSqlHelper + sqlPartTwo,
            new Object[] { parentResId }, new SecurityResourceBeanTransform() );
    }

    public List queryRoleHaveHisResourceBeanByRoleArray( String roleSqlHelper )
    {
        String sqlPartOne = "select sr.* from securityresource sr inner join (select secResId from role_relate_resource where ";
        String sqlPartTwo = ") reIds on reIds.secResId=sr.secResId order by linearOrderFlag";

        return pe.query( sqlPartOne + roleSqlHelper + sqlPartTwo,
            new SecurityResourceBeanTransform() );
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

    public Long querySecurityResourceIdByName( String key )
    {
        String sql = "select secResId from securityresource where target=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { key },
            Long.class );
    }

    public List queryResourceRelateRoleByResource( String key )
    {
        String sql = "select distinct roleId from role_relate_resource rr, securityresource sr where rr.secResId=sr.secResId and sr.target=?";

        return pe.querySingleCloumn( sql, new Object[] { key }, Long.class );

    }

    public List queryUserHaveResourceBeanByUserId( Long userId )
    {
        String sql = "select * from securityresource sr inner join (select rr.secResId from role_relate_resource rr inner join (select roleId from user_relate_role where userId=?) rids on rr.roleId=rids.roleId) rres on sr.secResId=rres.secResId";
        return pe.query( sql, new Object[] { userId },
            new SecurityResourceBeanTransform() );
    }

    

}
