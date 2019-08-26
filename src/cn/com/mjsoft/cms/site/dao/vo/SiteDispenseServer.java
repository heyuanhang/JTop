package cn.com.mjsoft.cms.site.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "site_dispense_server", id = "serverId", idMode = EntitySqlBridge.DB_IDENTITY )
public class SiteDispenseServer
{
    private Long serverId = Long.valueOf( -1 );
    private Long siteId = Long.valueOf( -1 );
    private Integer protocol;
    private String serverName;
    private String serverIP;
    private String serverPort;
    private String serverUrl;
    private String fileRoot;
    private Integer connectStatus;
    private Integer status;
    private String loginName;
    private String loginPassword;
    private String filterFlag;

    public Long getServerId()
    {
        return this.serverId;
    }

    public void setServerId( Long serverId )
    {
        this.serverId = serverId;
    }

    public Integer getProtocol()
    {
        return this.protocol;
    }

    public void setProtocol( Integer protocol )
    {
        this.protocol = protocol;
    }

    public String getServerName()
    {
        return this.serverName;
    }

    public void setServerName( String serverName )
    {
        this.serverName = serverName;
    }

    public String getServerIP()
    {
        return this.serverIP;
    }

    public void setServerIP( String serverIp )
    {
        this.serverIP = serverIp;
    }

    public String getServerPort()
    {
        return this.serverPort;
    }

    public void setServerPort( String serverPort )
    {
        this.serverPort = serverPort;
    }

    public String getServerUrl()
    {
        return this.serverUrl;
    }

    public void setServerUrl( String serverUrl )
    {
        this.serverUrl = serverUrl;
    }

    public Integer getStatus()
    {
        return this.status;
    }

    public void setStatus( Integer status )
    {
        this.status = status;
    }

    public String getLoginName()
    {
        return this.loginName;
    }

    public void setLoginName( String loginName )
    {
        this.loginName = loginName;
    }

    public String getLoginPassword()
    {
        return this.loginPassword;
    }

    public void setLoginPassword( String loginPassword )
    {
        this.loginPassword = loginPassword;
    }

    public String getFilterFlag()
    {
        return filterFlag;
    }

    public void setFilterFlag( String filterFlag )
    {
        this.filterFlag = filterFlag;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public Integer getConnectStatus()
    {
        return connectStatus;
    }

    public void setConnectStatus( Integer connectStatus )
    {
        this.connectStatus = connectStatus;
    }

    public String getFileRoot()
    {
        return fileRoot;
    }

    public void setFileRoot( String fileRoot )
    {
        this.fileRoot = fileRoot;
    }
}
