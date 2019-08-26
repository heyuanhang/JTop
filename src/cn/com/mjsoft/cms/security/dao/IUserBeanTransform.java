package cn.com.mjsoft.cms.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.security.bean.SystemLoginUser;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class IUserBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SystemLoginUser user = new SystemLoginUser();

        user.setName( rs.getString( "userName" ) );
        user.setPassword( rs.getString( "password" ) );
        user.setUserId( Long.valueOf( rs.getLong( "userId" ) ) );
        user.setOrgCode( rs.getString( "relateOrgCode" ) );
        user.setOrgId( Long.valueOf( rs.getLong( "orgId" ) ) );
        user.setUseStatus( Integer.valueOf( rs.getInt( "useState" ) ) );

        return user;
    }
}
