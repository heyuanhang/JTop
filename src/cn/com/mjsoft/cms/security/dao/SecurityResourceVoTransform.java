package cn.com.mjsoft.cms.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.security.dao.vo.SecurityResource;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SecurityResourceVoTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {

        SecurityResource vo = new SecurityResource();

        vo.setCreator( rs.getString( "creator" ) );
        vo
            .setDataProtectType( Integer
                .valueOf( rs.getInt( "dataProtectType" ) ) );
        vo.setIsLastChild( Integer.valueOf( rs.getInt( "isLastChild" ) ) );
        vo.setLayer( Integer.valueOf( rs.getInt( "layer" ) ) );
        vo.setLinearOrderFlag( rs.getString( "linearOrderFlag" ) );
        vo.setParentResId( Long.valueOf( rs.getLong( "parentResId" ) ) );
        vo.setResourceDesc( rs.getString( "resourceDesc" ) );
        vo.setResourceName( rs.getString( "resourceName" ) );
        vo.setResourceType( Integer.valueOf( rs.getInt( "resourceType" ) ) );
        vo.setSecResId( Long.valueOf( rs.getLong( "secResId" ) ) );
        vo.setTarget( rs.getString( "target" ) );
        vo.setUseState( Integer.valueOf( rs.getInt( "useState" ) ) );
        vo.setIsLeaf( Integer.valueOf( rs.getInt( "isLeaf" ) ) );
        return vo;

    }
}
