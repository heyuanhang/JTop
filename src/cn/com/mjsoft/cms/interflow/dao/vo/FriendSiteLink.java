package cn.com.mjsoft.cms.interflow.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "friend_site_link", id = "flId", idMode = EntitySqlBridge.DB_IDENTITY )
public class FriendSiteLink
{

    private Long flId;
    private String siteName;
    private String siteLink;
    private String siteLogo;
    private Integer orderFlag;
    private Long typeId;
    private Long siteId;

    public Long getFlId()
    {
        return this.flId;
    }

    public void setFlId( Long flId )
    {
        this.flId = flId;
    }

    public String getSiteName()
    {
        return this.siteName;
    }

    public void setSiteName( String siteName )
    {
        this.siteName = siteName;
    }

    public String getSiteLink()
    {
        return this.siteLink;
    }

    public void setSiteLink( String siteLink )
    {
        this.siteLink = siteLink;
    }

    public String getSiteLogo()
    {
        return this.siteLogo;
    }

    public void setSiteLogo( String siteLogo )
    {
        this.siteLogo = siteLogo;
    }

    public Integer getOrderFlag()
    {
        return this.orderFlag;
    }

    public void setOrderFlag( Integer orderFlag )
    {
        this.orderFlag = orderFlag;
    }

    public Long getSiteId()
    {
        return this.siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public Long getTypeId()
    {
        return typeId;
    }

    public void setTypeId( Long typeId )
    {
        this.typeId = typeId;
    }

}
