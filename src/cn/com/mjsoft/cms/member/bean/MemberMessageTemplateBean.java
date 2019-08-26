package cn.com.mjsoft.cms.member.bean;

public class MemberMessageTemplateBean
{
    private Long mtId;
    private String mtFlag;
    private String templateName;
    private String templateTitle;
    private String templateContent;
    private String creator;
    private Long siteId;

    public Long getMtId()
    {
        return this.mtId;
    }

    public void setMtId( Long mtId )
    {
        this.mtId = mtId;
    }

    public String getTemplateName()
    {
        return this.templateName;
    }

    public void setTemplateName( String templateName )
    {
        this.templateName = templateName;
    }

    public String getTemplateContent()
    {
        return this.templateContent;
    }

    public void setTemplateContent( String templateContent )
    {
        this.templateContent = templateContent;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public String getTemplateTitle()
    {
        return templateTitle;
    }

    public void setTemplateTitle( String templateTitle )
    {
        this.templateTitle = templateTitle;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public String getMtFlag()
    {
        return mtFlag;
    }

    public void setMtFlag( String mtFlag )
    {
        this.mtFlag = mtFlag;
    }
}
