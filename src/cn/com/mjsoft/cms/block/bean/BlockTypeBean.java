package cn.com.mjsoft.cms.block.bean;

public class BlockTypeBean
{
    private Long blockTypeId;
    private String blockTypeName;
    private Long jobId;
    private String siteFlag;
    private String creator;
    private String templateUrl;

    public Long getBlockTypeId()
    {
        return this.blockTypeId;
    }

    public void setBlockTypeId( Long blockTypeId )
    {
        this.blockTypeId = blockTypeId;
    }

    public String getBlockTypeName()
    {
        return this.blockTypeName;
    }

    public void setBlockTypeName( String blockTypeName )
    {
        this.blockTypeName = blockTypeName;
    }

    public Long getJobId()
    {
        return this.jobId;
    }

    public void setJobId( Long jobId )
    {
        this.jobId = jobId;
    }

    public String getSiteFlag()
    {
        return this.siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public String getTemplateUrl()
    {
        return templateUrl;
    }

    public void setTemplateUrl( String templateUrl )
    {
        this.templateUrl = templateUrl;
    }

}
