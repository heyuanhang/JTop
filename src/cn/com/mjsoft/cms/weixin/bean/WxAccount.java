package cn.com.mjsoft.cms.weixin.bean;

public class WxAccount
{
    private Long acId = Long.valueOf( -1 );
    private String wxName;
    private String appId;
    private String appsSecret;
    private String apiToken;
    private String mainId;
    private Long subWelInfoId;
    private Long subWelResId;
    private Long siteId = Long.valueOf( -1 );

    public Long getAcId()
    {
        return this.acId;
    }

    public void setAcId( Long acId )
    {
        this.acId = acId;
    }

    public String getWxName()
    {
        return wxName;
    }

    public void setWxName( String wxName )
    {
        this.wxName = wxName;
    }

    public String getAppId()
    {
        return this.appId;
    }

    public void setAppId( String appId )
    {
        this.appId = appId;
    }

    public String getAppsSecret()
    {
        return this.appsSecret;
    }

    public void setAppsSecret( String appsSecret )
    {
        this.appsSecret = appsSecret;
    }

    public String getApiToken()
    {
        return this.apiToken;
    }

    public void setApiToken( String apiToken )
    {
        this.apiToken = apiToken;
    }

    public String getMainId()
    {
        return this.mainId;
    }

    public void setMainId( String mainId )
    {
        this.mainId = mainId;
    }

    public Long getSubWelInfoId()
    {
        return this.subWelInfoId;
    }

    public void setSubWelInfoId( Long subWelInfoId )
    {
        this.subWelInfoId = subWelInfoId;
    }

    public Long getSiteId()
    {
        return this.siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public Long getSubWelResId()
    {
        return subWelResId;
    }

    public void setSubWelResId( Long subWelResId )
    {
        this.subWelResId = subWelResId;
    }

}
