package cn.com.mjsoft.cms.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.security.bean.MemberSecurityResourceBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class MemberSecurityResourceBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        MemberSecurityResourceBean bean = new MemberSecurityResourceBean();

        bean.setCreator( rs.getString( "creator" ) );
        bean.setDataProtectType( Integer
            .valueOf( rs.getInt( "dataProtectType" ) ) );
        bean.setDataTypeSecId( Long.valueOf( rs.getLong( "dataSecTypeId" ) ) );
        bean.setDataSecTypeId( Long.valueOf( rs.getLong( "dataSecTypeId" ) ) );
        bean.setIsLastChild( Integer.valueOf( rs.getInt( "isLastChild" ) ) );
        bean.setLayer( Integer.valueOf( rs.getInt( "layer" ) ) );
        bean.setLinearOrderFlag( rs.getString( "linearOrderFlag" ) );
        bean.setParentResId( Long.valueOf( rs.getLong( "parentResId" ) ) );
        bean.setResourceDesc( rs.getString( "resourceDesc" ) );
        bean.setResourceName( rs.getString( "resourceName" ) );
        bean.setResourceType( Integer.valueOf( rs.getInt( "resourceType" ) ) );
        bean.setSecResId( Long.valueOf( rs.getLong( "secResId" ) ) );
        bean.setTarget( rs.getString( "target" ) );
        bean.setUseState( Integer.valueOf( rs.getInt( "useState" ) ) );
        bean.setIsLeaf( Integer.valueOf( rs.getInt( "isLeaf" ) ) );

        bean.setIcon( rs.getString( "icon" ) );
        bean.setSysFlag( rs.getString( "sysFlag" ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );

        return bean;
    }
}
