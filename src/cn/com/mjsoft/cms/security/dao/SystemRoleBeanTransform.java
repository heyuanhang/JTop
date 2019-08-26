package cn.com.mjsoft.cms.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.security.bean.SystemRoleBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SystemRoleBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SystemRoleBean bean = new SystemRoleBean();

        bean.setRoleId( Long.valueOf( rs.getLong( "roleId" ) ) );
        bean.setOrgId( Long.valueOf( rs.getLong( "orgId" ) ) );
        bean.setRoleName( rs.getString( "roleName" ) );
        bean.setUseState( Integer.valueOf( rs.getInt( "useState" ) ) );
        bean.setRoleDesc( rs.getString( "roleDesc" ) );

        return bean;
    }
}
