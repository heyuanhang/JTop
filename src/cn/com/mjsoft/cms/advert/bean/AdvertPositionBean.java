package cn.com.mjsoft.cms.advert.bean;

public class AdvertPositionBean
{
    private Long posId = Long.valueOf( -1 );
    private String posName;
    private String posFlag;
    private String configName;
    private Long configId;
    private Integer width;
    private Integer height;
    private String posDesc;
    private Integer useState;
    private String creator;
    private String target;
    private Integer showMode;
    private Long siteId;

    public Long getPosId()
    {
        return this.posId;
    }

    public void setPosId( Long posId )
    {
        this.posId = posId;
    }

    public String getPosName()
    {
        return this.posName;
    }

    public void setPosName( String posName )
    {
        this.posName = posName;
    }

    public String getPosFlag()
    {
        return this.posFlag;
    }

    public void setPosFlag( String posFlag )
    {
        this.posFlag = posFlag;
    }

    public String getConfigName()
    {
        return this.configName;
    }

    public void setConfigName( String configName )
    {
        this.configName = configName;
    }

    public Long getConfigId()
    {
        return this.configId;
    }

    public void setConfigId( Long configId )
    {
        this.configId = configId;
    }

    public String getPosDesc()
    {
        return this.posDesc;
    }

    public void setPosDesc( String posDesc )
    {
        this.posDesc = posDesc;
    }

    public String getCreator()
    {
        return this.creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public Integer getShowMode()
    {
        return showMode;
    }

    public void setShowMode( Integer showMode )
    {
        this.showMode = showMode;
    }

    public Integer getUseState()
    {
        return useState;
    }

    public void setUseState( Integer useState )
    {
        this.useState = useState;
    }

    public Integer getWidth()
    {
        return width;
    }

    public String getTarget()
    {
        return target;
    }

    public void setTarget( String target )
    {
        this.target = target;
    }

    public void setWidth( Integer width )
    {
        this.width = width;
    }

    public Integer getHeight()
    {
        return height;
    }

    public void setHeight( Integer height )
    {
        this.height = height;
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
