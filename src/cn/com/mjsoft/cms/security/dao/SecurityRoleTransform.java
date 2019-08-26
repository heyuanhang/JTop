package cn.com.mjsoft.cms.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.framework.persistence.core.RowTransform;
import cn.com.mjsoft.framework.security.GenericRole;

public class SecurityRoleTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        GenericRole bean = new GenericRole( rs.getString( "roleName" ), Long
            .valueOf( rs.getLong( "roleId" ) ) );
        return bean;
    }
}
