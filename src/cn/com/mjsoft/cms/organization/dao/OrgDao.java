package cn.com.mjsoft.cms.organization.dao;

import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.cms.channel.dao.ContentClassBeanTransform;
import cn.com.mjsoft.cms.channel.dao.ContentCommendTypeBeanTransform;
import cn.com.mjsoft.cms.guestbook.dao.GuestbookConfigBeanTransform;
import cn.com.mjsoft.cms.organization.bean.SystemOrganizationBean;
import cn.com.mjsoft.cms.organization.dao.vo.SystemOrganization;
import cn.com.mjsoft.cms.site.dao.SiteGroupBeanTransform;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.util.StringUtil;

public class OrgDao
{
    private PersistenceEngine pe = null;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public OrgDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public List queryAllSystemOrganizationBean()
    {
        String sql = "select * from system_organization order by linearOrderFlag asc";
        return pe.query( sql, new SystemOrganizationBeanTransform() );
    }

    public List queryOrganizationBeanByParentId( Long parentId )
    {
        String sql = "select * from system_organization where parentId=? order by linearOrderFlag asc";
        return pe.query( sql, new SystemOrganizationBeanTransform() );
    }

    public SystemOrganizationBean querySingleSystemOrganizationBeanById( Long orgId )
    {
        String sql = "select * from system_organization where orgId=?";
        return ( SystemOrganizationBean ) pe.querySingleBean( sql, new Object[] { orgId },
            SystemOrganizationBean.class );
    }

    public SystemOrganizationBean querySingleSystemOrganizationBeanByLinear( String code )
    {
        String sql = "select * from system_organization where linearOrderFlag=?";
        return ( SystemOrganizationBean ) pe.querySingleRow( sql, new Object[] { code },
            new SystemOrganizationBeanTransform() );
    }

    public List querySystemOrganizationBeanByLinearFlag( String linearFlag )
    {
        String sql = "select * from system_organization where linearOrderFlag like'" + linearFlag
            + "%' order by linearOrderFlag asc";
        return pe.query( sql, new SystemOrganizationBeanTransform() );
    }

    public SystemOrganizationBean querySingleChildLastSystemOrganizationBeanById( Long parentId )
    {
        String sql = "select * from system_organization where parentId=? order by linearOrderFlag desc limit 1";
        return ( SystemOrganizationBean ) pe.querySingleRow( sql, new Object[] { parentId },
            new SystemOrganizationBeanTransform() );
    }

    public Integer queryChildCountById( Long orgId )
    {
        String sql = "select count(*) from system_organization where parentId=?";
        return ( Integer ) pe.querySingleObject( sql, new Object[] { orgId }, Integer.class );
    }

    public void updateNodeLeafFlag( Long orgId, Integer flag )
    {
        String sql = "update system_organization set isLeaf=? where orgId=?";
        pe.update( sql, new Object[] { flag, orgId } );
    }

    public void save( SystemOrganization org )
    {
        pe.save( org );
    }

    public void updateOrganizationBaseInfo( SystemOrganization org )
    {
        String sql = "update system_organization set orgName=?, orgDesc=? where orgId=?";
        pe.update( sql, org );
    }

    public void deleteOrganizationById( Long orgId )
    {
        String sql = "delete from system_organization where orgId=?";
        pe.update( sql, new Object[] { orgId } );

    }

    public void deleteOrganizationByParentId( Long orgId )
    {
        String sql = "delete from system_organization where parentId=?";
        pe.update( sql, new Object[] { orgId } );
    }

    public void deleteOrganizationByLinearFlag( String linearFlag )
    {
        String sql = "delete from system_organization where linearOrderFlag like '" + linearFlag
            + "%'";
        pe.update( sql );
    }

    public void deleteRoleRangeFirstLayerOrgRelateResource( Long orgId )
    {
        String sql = "delete from role_range_fl_org_relate_res where orgId=?";
        pe.update( sql, new Object[] { orgId } );
    }

    public void saveRoleRangeOrgRelateResource( Long orgId, Long targetResId )
    {
        String sql = "insert into role_range_org_relate_resource (orgId,resId) values (?,?)";
        pe.update( sql, new Object[] { orgId, targetResId } );
    }

    public void saveRoleRangeFirstLayerOrgRelateResource( Long orgId, Long targetResId )
    {
        String sql = "insert into role_range_fl_org_relate_res (orgId,resId) values (?,?)";
        pe.update( sql, new Object[] { orgId, targetResId } );
    }

    public void saveRoleRangeOrgRelateResourceAcc( Long orgId, Long targetResId, Long siteId,
        Long secType, Long accId )
    {
        String sql = "insert into role_range_org_relate_res_acc (orgId,resId,siteId,dataSecTypeId,accId) values (?,?,?,?,?)";
        pe.update( sql, new Object[] { orgId, targetResId, siteId, secType, accId } );
    }

    public void deleteRoleRangeOrgRelateResource( Long orgId )
    {
        String sql = "delete from role_range_org_relate_resource where orgId=?";
        pe.update( sql, new Object[] { orgId } );
    }

    public List queryClassBeanByOrgId( Long orgId, String[] dataSecTypeIds, Long siteId )
    {
        StringBuffer buf = new StringBuffer( "(" );

        long id = -1;
        boolean havaParam = false;

        for ( int i = 0; i < dataSecTypeIds.length; i++ )
        {
            // check非法参数
            id = StringUtil.getLongValue( new String( dataSecTypeIds[i] ), -1 );

            if( id < 0 )
            {
                continue;
            }

            havaParam = true;

            buf.append( new String( dataSecTypeIds[i] ) );
            if( ( i + 1 ) != dataSecTypeIds.length )
            {
                buf.append( "," );
            }
        }

        buf.append( ")" );

        if( !havaParam )
        {
            return new ArrayList();
        }

        String sql = "select * from contentclass where classId in (select rrac.accId from role_range_org_relate_res_acc rrac where rrac.orgId=? and rrac.dataSecTypeId in "
            + buf.toString() + " and rrac.siteId=?) order by linearOrderFlag";

        List contentClassList = pe.query( sql, new Object[] { orgId, siteId },
            new ContentClassBeanTransform() );

        return contentClassList;
    }

    public List queryGuestbookConfigBeanByOrgId( Long orgId, String[] dataSecTypeIds, Long siteId )
    {
        StringBuffer buf = new StringBuffer( "(" );

        long id = -1;
        boolean havaParam = false;

        for ( int i = 0; i < dataSecTypeIds.length; i++ )
        {
            // check非法参数
            id = StringUtil.getLongValue( new String( dataSecTypeIds[i] ), -1 );

            if( id < 0 )
            {
                continue;
            }

            havaParam = true;

            buf.append( new String( dataSecTypeIds[i] ) );
            if( ( i + 1 ) != dataSecTypeIds.length )
            {
                buf.append( "," );
            }
        }

        buf.append( ")" );

        if( !havaParam )
        {
            return new ArrayList();
        }

        String sql = "select * from guestbook_config where configId in (select rrac.accId from role_range_org_relate_res_acc rrac where rrac.orgId=? and rrac.dataSecTypeId in "
            + buf.toString() + " and rrac.siteId=?) order by configId asc";

        List gbcList = pe.query( sql, new Object[] { orgId, siteId },
            new GuestbookConfigBeanTransform() );

        return gbcList;
    }

    public List queryCommendBeanByOrgId( Long orgId, String[] dataSecTypeIds, Long siteId )
    {
        StringBuffer buf = new StringBuffer( "(" );

        long id = -1;
        boolean havaParam = false;

        for ( int i = 0; i < dataSecTypeIds.length; i++ )
        {
            // check非法参数
            id = StringUtil.getLongValue( new String( dataSecTypeIds[i] ), -1 );

            if( id < 0 )
            {
                continue;
            }

            havaParam = true;

            buf.append( new String( dataSecTypeIds[i] ) );
            if( ( i + 1 ) != dataSecTypeIds.length )
            {
                buf.append( "," );
            }
        }

        buf.append( ")" );

        if( !havaParam )
        {
            return new ArrayList();
        }

        String sql = "select * from content_commend_type where isSpec=0 and commendTypeId in (select rrac.accId from role_range_org_relate_res_acc rrac where rrac.orgId=? and rrac.dataSecTypeId in "
            + buf.toString() + " and rrac.siteId=?) order by commendTypeId asc";

        List commendTypeList = pe.query( sql, new Object[] { orgId, siteId },
            new ContentCommendTypeBeanTransform() );

        return commendTypeList;
    }

    public List querySpecClassBeanByOrgId( Long orgId, Long dataSecTypeId, Long siteId )
    {
        String sql = "select * from contentclass where classId in (select rrac.accId from role_range_org_relate_res_acc rrac where rrac.orgId=? and rrac.dataSecTypeId=? and rrac.siteId=?) order by linearOrderFlag";

        List contentClassList = pe.query( sql, new Object[] { orgId, dataSecTypeId, siteId },
            new ContentClassBeanTransform() );

        return contentClassList;
    }

    public List queryClassBeanByOrgId( Long siteId )
    {
        String sql = "select * from contentclass where siteFlag=(select siteFlag from site_group where siteId=?) and classType<4 order by linearOrderFlag";

        List contentClassList = pe.query( sql, new Object[] { siteId },
            new ContentClassBeanTransform() );

        return contentClassList;
    }

    public List querySpecClassBeanByOrgId( Long siteId )
    {
        String sql = "select * from contentclass where siteFlag=(select siteFlag from site_group where siteId=?) and classType=4 order by linearOrderFlag";

        List contentClassList = pe.query( sql, new Object[] { siteId },
            new ContentClassBeanTransform() );

        return contentClassList;
    }

    public Long querySingleParentClassIdByClassId( Long classId )
    {
        String sql = "select pcc.classId from contentclass pcc where pcc.classId = (select cc.parent from contentclass cc where cc.classId=?)";

        return ( Long ) pe.querySingleObject( sql, new Object[] { classId }, Long.class );
    }

    public void deleteRoleRangeOrgRelateResourceAcc( Long orgId, Long siteId, Long secType )
    {
        String sql = "delete from role_range_org_relate_res_acc where orgId=? and siteId=? and dataSecTypeId=?";
        pe.update( sql, new Object[] { orgId, siteId, secType } );
    }

    public List queryOrganizationResIdByOrgLinerFlag( String linearOrderFlag )
    {
        String sql = "select resId, orgId from role_range_org_relate_resource where orgId in (select orgId from system_organization where linearOrderFlag like '"
            + linearOrderFlag + "%')";

        return pe.queryResultMap( sql );
    }

    public List queryOrganizationAccResIdByOrgLinerFlag( String linearOrderFlag, Long dataSecTypeId )
    {
        String sql = "select * from role_range_org_relate_res_acc where orgId in(select orgId from system_organization where dataSecTypeId=? and linearOrderFlag like '"
            + linearOrderFlag + "%')";

        return pe.queryResultMap( sql, new Object[] { dataSecTypeId } );
    }

    public void deleteRoleRangeOrgRelateResourceByOrgAndRes( Long orgId, Long resId )
    {
        String sql = "delete from role_range_org_relate_resource where orgId=? and resId=?";
        pe.update( sql, new Object[] { orgId, resId } );

    }

    public void deleteRoleAccRangeOrgRelateResourceByOrgAndRes( Long siteId, Long orgId,
        Long resId, Long accId, Long dataSecTypeId )
    {
        String sql = "delete from role_range_org_relate_res_acc where siteId=? and orgId=? and resId=? and accId=? and dataSecTypeId=?";
        pe.update( sql, new Object[] { siteId, orgId, resId, accId, dataSecTypeId } );
    }

    public void deleteRoleRangeOrgRelateSite( Long orgId )
    {
        String sql = "delete from role_range_org_relate_site where orgId=?";

        pe.update( sql, new Object[] { orgId } );
    }

    public void deleteRoleRangeOrgRelateSiteBySiteAndOrg( Long orgId, Long siteId )
    {
        String sql = "delete from role_range_org_relate_site where orgId=? and siteId=?";

        pe.update( sql, new Object[] { orgId, siteId } );
    }

    public void saveRoleRangeOrgRelateSite( Long orgId, Long siteId )
    {
        String sql = "insert into role_range_org_relate_site (orgId, siteId) values (?,?)";

        pe.update( sql, new Object[] { orgId, siteId } );
    }

    public void saveRoleRangeFirstLayerOrgRelateSite( Long orgId, Long siteId )
    {
        String sql = "insert into role_range_fl_org_relate_site (orgId, siteId) values (?,?)";
        pe.update( sql, new Object[] { orgId, siteId } );
    }

    public List querySiteBeanByOrgId( Long orgId )
    {
        String sql = "select * from site_group where siteId in (select siteId from role_range_org_relate_site where orgId=?) order by orderFlag asc";
        return pe.query( sql, new Object[] { orgId }, new SiteGroupBeanTransform() );
    }

    public List queryFirstLayerRangeSiteBeanByOrgId( Long orgId )
    {
        String sql = "select * from site_group where siteId in (select siteId from role_range_fl_org_relate_site where orgId=?) order by orderFlag asc";
        return pe.query( sql, new Object[] { orgId }, new SiteGroupBeanTransform() );
    }

    public void deleteFirstLayerSiteRangeByOrgId( Long orgId )
    {
        String sql = "delete from role_range_fl_org_relate_site where orgId=?";
        pe.update( sql, new Object[] { orgId } );
    }

    public void deleteRangeRoleHaveHisResourceByOrgLinearFlag( String orgLinearFlag )
    {
        String sql = "delete from role_range_org_relate_resource where orgId in (select orgId from system_organization where linearOrderFlag like '"
            + orgLinearFlag + "%')";

        pe.update( sql );
    }

    public void deleteRangeRoleRelateSiteByOrgLinearFlag( String orgLinearFlag )
    {

        String sql = "delete from role_range_org_relate_site where orgId in (select orgId from system_organization where linearOrderFlag like '"
            + orgLinearFlag + "%')";

        pe.update( sql );
    }

    public void deleteRangeRoleHaveHisAccResourceByOrgLinearFlag( String orgLinearFlag )
    {

        String sql = "delete from role_range_org_relate_res_acc where orgId in (select orgId from system_organization where linearOrderFlag like '"
            + orgLinearFlag + "%')";

        pe.update( sql );
    }

    public List querySiteBeanByRoleId( Long orgId )
    {
        String sql = "select * from site_group where siteId in (select siteId from role_relate_site where roleId=?) order by orderFlag asc";
        return pe.query( sql, new Object[] { orgId }, new SiteGroupBeanTransform() );
    }

    public List queryAllOrgSiteBean()
    {
        String sql = "select * from site_group order by orderFlag asc";
        return pe.query( sql, new SiteGroupBeanTransform() );
    }

    public void deleteOrgSiteRangeNotIncludeSite( Long[] currentManagerSite, String orgLinearFlag )
    {
        StringBuffer buf;
        if( currentManagerSite.length == 0 )
        {
            buf = new StringBuffer( " " );
        }
        else
        {
            buf = new StringBuffer( " siteId not in (" );
            for ( int i = 0; i < currentManagerSite.length; i++ )
            {
                if( i + 1 != currentManagerSite.length )
                {
                    buf.append( currentManagerSite[i] + ", " );
                }
                else
                {
                    buf.append( currentManagerSite[i] );
                }
            }
            buf.append( ") and " );
        }

        String sql = "delete from role_range_org_relate_site where" + buf.toString()
            + "orgId in (select orgId from system_organization where linearOrderFlag like'"
            + orgLinearFlag + "%')";
        pe.update( sql );
    }

    public void deleteResourceRangeOrgRelateSiteNotIncludeSite( Long[] currentManagerSite,
        String orgLinearFlag )
    {
        StringBuffer buf;
        if( currentManagerSite.length == 0 )
        {
            buf = new StringBuffer( " " );
        }
        else
        {
            buf = new StringBuffer( " siteId not in (" );
            for ( int i = 0; i < currentManagerSite.length; i++ )
            {
                if( i + 1 != currentManagerSite.length )
                {
                    buf.append( currentManagerSite[i] + ", " );
                }
                else
                {
                    buf.append( currentManagerSite[i] );
                }
            }
            buf.append( ") and " );
        }

        String sql = "delete from role_range_org_relate_res_acc where" + buf.toString()
            + "orgId in (select orgId from system_organization where linearOrderFlag like'"
            + orgLinearFlag + "%')";
        pe.update( sql );
    }

    public Integer checkOrgRelateSite( Long orgId, Long siteId )
    {
        String sql = "select count(*) from role_range_org_relate_site where orgId=? and siteId=?";
        return ( Integer ) pe
            .querySingleObject( sql, new Object[] { orgId, siteId }, Integer.class );
    }

    public List checkOrgRelateSiteForRootOrgRange( Long orgId )
    {
        String sql = "select siteId from role_range_org_relate_site where orgId=?";
        return pe.querySingleCloumn( sql, new Object[] { orgId }, Long.class );
    }

    public void updateOrgBossId( Long orgId, Long uid )
    {
        String sql = "update system_organization set orgBossId=? where orgId=?";

        pe.update( sql, new Object[] { uid, orgId } );
    }

    public void updateOrgBossId( String orgCode, Long uid )
    {
        String sql = "update system_organization set orgBossId=? where linearOrderFlag=?";

        pe.update( sql, new Object[] { uid, orgCode } );
    }

    public void updateRootOrgName( String orgName )
    {
        String sql = "update system_organization set orgName=? where orgId=1 and orgFlag='group' and parentId=-1";

        pe.update( sql, new Object[] { orgName } );
    }

    public List queryOrgRangeAccIdsList( Long orgId, Long secTypeId )
    {
        String sql = "select accId from role_range_org_relate_res_acc where orgId=? and dataSecTypeId=? and resId != -1";

        return pe.querySingleCloumn( sql, new Object[] { orgId, secTypeId }, Long.class );
    }

}
