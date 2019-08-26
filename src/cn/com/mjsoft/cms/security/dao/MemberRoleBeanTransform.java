package cn.com.mjsoft.cms.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.security.bean.MemberRoleBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class MemberRoleBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        MemberRoleBean bean = new MemberRoleBean();

        bean.setRoleId( Long.valueOf( rs.getLong( "roleId" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );
        bean.setRoleName( rs.getString( "roleName" ) );
        bean.setUseState( Integer.valueOf( rs.getInt( "useState" ) ) );
        bean.setRoleDesc( rs.getString( "roleDesc" ) );

        return bean;
    }
}
