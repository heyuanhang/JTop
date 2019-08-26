package cn.com.mjsoft.cms.site.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.site.bean.CmsServerBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class CmsServerBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        CmsServerBean bean = new CmsServerBean();

        bean.setDomain( rs.getString( "domain" ) );
        bean.setFirstInitOver( Integer.valueOf( rs.getInt( "firstInitOver" ) ) );
        bean.setPort( Integer.valueOf( rs.getInt( "port" ) ) );
        bean.setServerId( Long.valueOf( rs.getLong( "serverId" ) ) );
        bean.setContext( rs.getString( "context" ) );

        return bean;
    }

}
