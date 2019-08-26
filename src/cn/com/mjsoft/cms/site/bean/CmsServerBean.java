package cn.com.mjsoft.cms.site.bean;

import java.util.HashMap;
import java.util.Map;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.config.JTopServer;
import cn.com.mjsoft.framework.util.StringUtil;

public class CmsServerBean implements JTopServer
{
    private Long serverId = Long.valueOf( 0 );

    private String domain;

    private String context;

    private Integer port;

    private Integer firstInitOver;

    private Boolean clusterMode = Boolean.FALSE;

    private Boolean clientServer = Boolean.FALSE;

    private String redisIP;

    private Boolean actDefense = Boolean.TRUE;

    private Boolean allDefense = Boolean.FALSE;

    private Boolean tplCache = Boolean.TRUE;

    private String encoding = "UTF-8";

    // 业务字段
    private String domainInfo;

    private String contextUri;

    // 扩展配置

    private String managerIp;

    private Integer loginTime;

    private String openOfficePath;

    private String rootOrgName;

    private String baiduMapDefCity;

    private Integer dangerAccessCount;

    private String otherVCUrl;

    private Map extCfg = new HashMap();

    public String getDomain()
    {
        return domain;
    }

    public void setDomain( String domain )
    {
        this.domain = domain;
    }

    public Integer getFirstInitOver()
    {
        return firstInitOver;
    }

    public void setFirstInitOver( Integer firstInitOver )
    {
        this.firstInitOver = firstInitOver;
    }

    public Integer getPort()
    {
        return port;
    }

    public void setPort( Integer port )
    {
        this.port = port;
    }

    public Long getServerId()
    {
        return serverId;
    }

    public void setServerId( Long serverId )
    {
        this.serverId = serverId;
    }

    public String getContext()
    {
        return context;
    }

    public void setContext( String context )
    {
        this.context = context;
    }

    public String getBaiduMapDefCity()
    {
        return baiduMapDefCity;
    }

    public void setBaiduMapDefCity( String baiduMapDefCity )
    {
        this.baiduMapDefCity = baiduMapDefCity;
    }

    public Integer getDangerAccessCount()
    {
        return dangerAccessCount;
    }

    public void setDangerAccessCount( Integer dangerAccessCount )
    {
        this.dangerAccessCount = dangerAccessCount;
    }

    public Integer getLoginTime()
    {
        return loginTime;
    }

    public void setLoginTime( Integer loginTime )
    {
        this.loginTime = loginTime;
    }

    public String getManagerIp()
    {
        return managerIp;
    }

    public void setManagerIp( String managerIp )
    {
        this.managerIp = managerIp;
    }

    public String getOpenOfficePath()
    {
        return openOfficePath;
    }

    public void setOpenOfficePath( String openOfficePath )
    {
        this.openOfficePath = openOfficePath;
    }

    public String getOtherVCUrl()
    {
        return otherVCUrl;
    }

    public void setOtherVCUrl( String otherVCUrl )
    {
        this.otherVCUrl = otherVCUrl;
    }

    public String getRootOrgName()
    {
        return rootOrgName;
    }

    public void setRootOrgName( String rootOrgName )
    {
        this.rootOrgName = rootOrgName;
    }

    public Boolean getClusterMode()
    {
        return clusterMode;
    }

    public void setClusterMode( Boolean clusterMode )
    {
        this.clusterMode = clusterMode;
    }

    public void setClientServer( Boolean client )
    {
        this.clientServer = client;
    }

    public Boolean getClientServer()
    {
        return this.clientServer;
    }

    public String getRedisIP()
    {
        return redisIP;
    }

    public void setRedisIP( String redisIP )
    {
        this.redisIP = redisIP;
    }

    public Boolean getActDefense()
    {
        return actDefense;
    }

    public void setActDefense( Boolean actDefense )
    {
        this.actDefense = actDefense;
    }

    public Boolean getAllDefense()
    {
        return allDefense;
    }

    public void setAllDefense( Boolean allDefense )
    {
        this.allDefense = allDefense;
    }

    public Map getExtCfg()
    {
        return extCfg;
    }

    public Boolean getTplCache()
    {
        return tplCache;
    }

    public void setTplCache( Boolean tplCache )
    {
        this.tplCache = tplCache;
    }

    public String getEncoding()
    {
        return encoding;
    }

    public void setEncoding( String encoding )
    {
        this.encoding = encoding;
    }

    // 业务方法
    public String getDomainFullPath()
    {
        if( this.domainInfo == null )
        {
            this.domainInfo = domain
                + ( this.port.intValue() == 80 ? Constant.CONTENT.URL_SEP : ":" + port
                    + Constant.CONTENT.URL_SEP )
                + ( StringUtil.isStringNull( this.context ) ? "" : this.context );

            this.domainInfo = StringUtil.isStringNull( this.domainInfo )
                ? Constant.SITE_CHANNEL.NO_DOMAIN_INFO : this.domainInfo;

            if( this.domainInfo.lastIndexOf( "/" ) != ( this.domainInfo.length() - 1 ) )
            {
                this.domainInfo = this.domainInfo + "/";
            }

        }

        return this.domainInfo;
    }

    public String getContextUri()
    {
        if( this.contextUri == null )
        {
            if( StringUtil.isStringNull( this.context ) )
            {
                this.contextUri = "/";
            }
            else
            {
                this.contextUri = "/" + this.context + "/";
            }
        }

        return this.contextUri;
    }

    public void clear()
    {
        this.domainInfo = null;

        this.contextUri = null;
    }

}
