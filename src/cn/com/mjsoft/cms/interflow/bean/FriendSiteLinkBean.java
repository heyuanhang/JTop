package cn.com.mjsoft.cms.interflow.bean;

public class FriendSiteLinkBean
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
