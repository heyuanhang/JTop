package cn.com.mjsoft.cms.advert.bean;

public class AdvertConfigBean
{
    private Long configId = Long.valueOf( -1 );
    private String configName;
    private String configDesc;
    private String advertCode;
    private Long posModelId;
    private Long contentModelId;
    private Integer userState;
    private String creator;
    private Long siteId;

    public Long getConfigId()
    {
        return this.configId;
    }

    public void setConfigId( Long configId )
    {
        this.configId = configId;
    }

    public String getConfigName()
    {
        return configName;
    }

    public void setConfigName( String configName )
    {
        this.configName = configName;
    }

    public String getConfigDesc()
    {
        return this.configDesc;
    }

    public void setConfigDesc( String configDesc )
    {
        this.configDesc = configDesc;
    }

    public String getAdvertCode()
    {
        return this.advertCode;
    }

    public void setAdvertCode( String advertCode )
    {
        this.advertCode = advertCode;
    }

    public Integer getUserState()
    {
        return userState;
    }

    public void setUserState( Integer userState )
    {
        this.userState = userState;
    }

    public String getCreator()
    {
        return this.creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public Long getContentModelId()
    {
        return contentModelId;
    }

    public void setContentModelId( Long contentModelId )
    {
        this.contentModelId = contentModelId;
    }

    public Long getPosModelId()
    {
        return posModelId;
    }

    public void setPosModelId( Long posModelId )
    {
        this.posModelId = posModelId;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

}
