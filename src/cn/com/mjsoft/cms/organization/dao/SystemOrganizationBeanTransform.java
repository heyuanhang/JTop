package cn.com.mjsoft.cms.organization.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.organization.bean.SystemOrganizationBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SystemOrganizationBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SystemOrganizationBean bean = new SystemOrganizationBean();

        bean.setOrgId( Long.valueOf( rs.getLong( "orgId" ) ) );
        bean.setParentId( Long.valueOf( rs.getLong( "parentId" ) ) );
        bean.setOrgFlag( rs.getString( "orgFlag" ) );
        bean.setOrgName( rs.getString( "orgName" ) );
        bean.setOrgDesc( rs.getString( "orgDesc" ) );
        bean.setIsLeaf( Integer.valueOf( rs.getInt( "isLeaf" ) ) );
        bean.setLayer( Integer.valueOf( rs.getInt( "layer" ) ) );
        bean.setLinearOrderFlag( rs.getString( "linearOrderFlag" ) );
        bean.setOrgBossId( Long.valueOf( rs.getLong( "orgBossId" ) ) );
        bean.setDirectorIds( rs.getString( "directorIds" ) );

        return bean;

    }

}
