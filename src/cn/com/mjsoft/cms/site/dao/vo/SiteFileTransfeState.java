package cn.com.mjsoft.cms.site.dao.vo;

import java.util.Date;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "site_file_transfe_state", id = "transferFileId", idMode = EntitySqlBridge.DB_IDENTITY )
public class SiteFileTransfeState
{
    private Long transferFileId;
    private Long gatewayId;
    private String fullPath;
    private String filePath;
    private Integer transferStatus;
    private Integer fileEventFlag;
    private Date eventTime;

    public Long getTransferFileId()
    {
        return this.transferFileId;
    }

    public void setTransferFileId( Long transferFileId )
    {
        this.transferFileId = transferFileId;
    }

    public String getFullPath()
    {
        return this.fullPath;
    }

    public void setFullPath( String fullPath )
    {
        this.fullPath = fullPath;
    }

    public Integer getTransferStatus()
    {
        return this.transferStatus;
    }

    public void setTransferStatus( Integer transferStatus )
    {
        this.transferStatus = transferStatus;
    }

    public Integer getFileEventFlag()
    {
        return fileEventFlag;
    }

    public void setFileEventFlag( Integer fileEventFlag )
    {
        this.fileEventFlag = fileEventFlag;
    }

    public Date getEventTime()
    {
        return this.eventTime;
    }

    public void setEventTime( Date eventTime )
    {
        this.eventTime = eventTime;
    }

    public Long getGatewayId()
    {
        return gatewayId;
    }

    public void setGatewayId( Long gatewayId )
    {
        this.gatewayId = gatewayId;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath( String filePath )
    {
        this.filePath = filePath;
    }

}
