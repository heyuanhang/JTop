package cn.com.mjsoft.cms.site.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "site_publish_gateway", id = "gatewayId", idMode = EntitySqlBridge.DB_IDENTITY )
public class SitePublishGateway
{

    private Long gatewayId = Long.valueOf( -1 );
    private Long collectJobId = Long.valueOf( -1 );
    private Long transferJobId = Long.valueOf( -1 );
    private String name;
    private Integer transfeType;
    private String sourcePath;
    private Long targetServerId = Long.valueOf( -1 );
    private Long targetCloudId = Long.valueOf( -1 );
    private String targetServerRoot;
    private String siteRoot;
    private Long siteId = Long.valueOf( -1 );
    private Integer useState;

    public Long getGatewayId()
    {
        return this.gatewayId;
    }

    public void setGatewayId( Long gatewayId )
    {
        this.gatewayId = gatewayId;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Integer getTransfeType()
    {
        return this.transfeType;
    }

    public void setTransfeType( Integer transfeType )
    {
        this.transfeType = transfeType;
    }

    public String getSourcePath()
    {
        return this.sourcePath;
    }

    public void setSourcePath( String sourcePath )
    {
        this.sourcePath = sourcePath;
    }

    public String getTargetServerRoot()
    {
        return this.targetServerRoot;
    }

    public void setTargetServerRoot( String targetServerRoot )
    {
        this.targetServerRoot = targetServerRoot;
    }

    public Integer getUseState()
    {
        return useState;
    }

    public void setUseState( Integer useState )
    {
        this.useState = useState;
    }

    public Long getTargetServerId()
    {
        return targetServerId;
    }

    public void setTargetServerId( Long targetServerId )
    {
        this.targetServerId = targetServerId;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public String getSiteRoot()
    {
        return siteRoot;
    }

    public void setSiteRoot( String siteRoot )
    {
        this.siteRoot = siteRoot;
    }

    public Long getCollectJobId()
    {
        return collectJobId;
    }

    public void setCollectJobId( Long collectJobId )
    {
        this.collectJobId = collectJobId;
    }

    public Long getTransferJobId()
    {
        return transferJobId;
    }

    public void setTransferJobId( Long transferJobId )
    {
        this.transferJobId = transferJobId;
    }

    public Long getTargetCloudId()
    {
        return targetCloudId;
    }

    public void setTargetCloudId( Long targetCloudId )
    {
        this.targetCloudId = targetCloudId;
    }

}
