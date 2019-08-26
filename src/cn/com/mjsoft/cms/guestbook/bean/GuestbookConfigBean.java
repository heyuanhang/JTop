package cn.com.mjsoft.cms.guestbook.bean;

public class GuestbookConfigBean
{
    private Long configId = Long.valueOf( -1 );
    private String cfgName;
    private String cfgDesc;
    private Integer mustHaveTitle;
    private Integer mustLogin;
    private Integer mustCensor;
    private Integer needVerifyCode;
    private String cfgFlag;
    private Integer useState;
    private Long infoModelId;
    private Long workflowId;
    private Long siteId;

    public Long getConfigId()
    {
        return this.configId;
    }

    public void setConfigId( Long configId )
    {
        this.configId = configId;
    }

    public Integer getMustCensor()
    {
        return mustCensor;
    }

    public void setMustCensor( Integer mustCensor )
    {
        this.mustCensor = mustCensor;
    }

    public Integer getMustLogin()
    {
        return mustLogin;
    }

    public void setMustLogin( Integer mustLogin )
    {
        this.mustLogin = mustLogin;
    }

    public Integer getNeedVerifyCode()
    {
        return needVerifyCode;
    }

    public void setNeedVerifyCode( Integer needVerifyCode )
    {
        this.needVerifyCode = needVerifyCode;
    }

    public String getCfgFlag()
    {
        return cfgFlag;
    }

    public void setCfgFlag( String cfgFlag )
    {
        this.cfgFlag = cfgFlag;
    }

    public Long getInfoModelId()
    {
        return this.infoModelId;
    }

    public void setInfoModelId( Long infoModelId )
    {
        this.infoModelId = infoModelId;
    }

    public String getCfgName()
    {
        return cfgName;
    }

    public void setCfgName( String cfgName )
    {
        this.cfgName = cfgName;
    }

    public String getCfgDesc()
    {
        return cfgDesc;
    }

    public void setCfgDesc( String cfgDesc )
    {
        this.cfgDesc = cfgDesc;
    }

    public Integer getUseState()
    {
        return useState;
    }

    public void setUseState( Integer useState )
    {
        this.useState = useState;
    }

    public Integer getMustHaveTitle()
    {
        return mustHaveTitle;
    }

    public void setMustHaveTitle( Integer mustHaveTitle )
    {
        this.mustHaveTitle = mustHaveTitle;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public Long getWorkflowId()
    {
        return workflowId;
    }

    public void setWorkflowId( Long workflowId )
    {
        this.workflowId = workflowId;
    }

}
