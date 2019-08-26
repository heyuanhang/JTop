package cn.com.mjsoft.cms.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class MemberSecurityResourceBeanMapTransform implements RowTransform
{

    private Map mapper;

    public MemberSecurityResourceBeanMapTransform( Map mapper )
    {
        this.mapper = mapper;
    }

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        Map info = new HashMap( 5 );
        info.put( "dataProtectType", Integer.valueOf( rs
            .getInt( "dataProtectType" ) ) );
        info.put( "secResId", Long.valueOf( rs.getLong( "secResId" ) ) );
        info.put( "target", rs.getString( "target" ) );
        //info.put( "accSymbol", rs.getString( "accSymbol" ) );
       // info.put( "dataTypeId", Long.valueOf( rs.getLong( "dataTypeId" ) ) );
       // info.put( "accBehaviorClass", rs.getString( "accBehaviorClass" ) );

        mapper.put( info.get( "target" ), info );

        return null;
    }
}
