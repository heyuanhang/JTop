package cn.com.mjsoft.cms.site.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.site.bean.SiteFileTransfeStateBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SiteFileTransfeStateBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SiteFileTransfeStateBean bean = new SiteFileTransfeStateBean();

        bean.setEventTime( rs.getTimestamp( "eventTime" ) );
        bean.setFileEventFlag( Integer.valueOf( rs.getInt( "FileEventFlag" ) ) );
        bean.setFilePath( rs.getString( "filePath" ) );
        bean.setFullPath( rs.getString( "fullPath" ) );
        bean.setGatewayId( Long.valueOf( rs.getLong( "gatewayId" ) ) );
        bean.setTransferFileId( Long.valueOf( rs.getLong( "transferFileId" ) ) );
        bean
            .setTransferStatus( Integer.valueOf( rs.getInt( "transferStatus" ) ) );

        return bean;
    }

}
