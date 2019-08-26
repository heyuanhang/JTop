package cn.com.mjsoft.cms.site.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.site.bean.SiteDispenseServerBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SiteDispenseServerBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SiteDispenseServerBean bean = new SiteDispenseServerBean();

        bean.setServerId( Long.valueOf( rs.getLong( "serverId" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );
        bean.setProtocol( Integer.valueOf( rs.getInt( "protocol" ) ) );
        bean.setServerName( rs.getString( "serverName" ) );
        bean.setServerIP( rs.getString( "serverIP" ) );
        bean.setServerPort( rs.getString( "serverPort" ) );
        bean.setServerUrl( rs.getString( "serverUrl" ) );
        bean.setConnectStatus( Integer.valueOf( rs.getInt( "connectStatus" ) ) );
        bean.setStatus( Integer.valueOf( rs.getInt( "status" ) ) );
        bean.setLoginName( rs.getString( "loginName" ) );
        bean.setLoginPassword( rs.getString( "loginPassword" ) );
        bean.setFilterFlag( rs.getString( "filterFlag" ) );
        bean.setFileRoot( rs.getString( "fileRoot" ) );

        return bean;
    }
}
