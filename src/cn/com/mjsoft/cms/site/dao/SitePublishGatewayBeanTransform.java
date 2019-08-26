package cn.com.mjsoft.cms.site.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.site.bean.SitePublishGatewayBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SitePublishGatewayBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SitePublishGatewayBean bean = new SitePublishGatewayBean();

        bean.setGatewayId( Long.valueOf( rs.getLong( "gatewayId" ) ) );
        bean.setName( rs.getString( "name" ) );
        bean.setSourcePath( rs.getString( "sourcePath" ) );
        bean.setTargetServerId( Long.valueOf( rs.getLong( "targetServerId" ) ) );
        bean.setTargetCloudId( Long.valueOf( rs.getLong( "targetCloudId" ) ) );
        bean.setTargetServerRoot( rs.getString( "targetServerRoot" ) );
        bean.setTransfeType( Integer.valueOf( rs.getInt( "transfeType" ) ) );
        bean.setUseState( Integer.valueOf( rs.getInt( "useState" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );
        bean.setSiteRoot( rs.getString( "siteRoot" ) );
        bean.setCollectJobId( Long.valueOf( rs.getLong( "collectJobId" ) ) );
        bean.setTransferJobId( Long.valueOf( rs.getLong( "transferJobId" ) ) );
        return bean;
    }

}
