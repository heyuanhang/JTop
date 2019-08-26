package cn.com.mjsoft.cms.publish.bean;

public class PublishPageAssistantBean
{
    private String queryKey;
    private String classTemplateUrl;
    private Long classId;
    private String lastPageStaticUrl;
    private Integer lastPn;

    public String getQueryKey()
    {
        return queryKey;
    }

    public void setQueryKey( String queryKey )
    {
        this.queryKey = queryKey;
    }

    public String getClassTemplateUrl()
    {
        return this.classTemplateUrl;
    }

    public void setClassTemplateUrl( String classTemplateUrl )
    {
        this.classTemplateUrl = classTemplateUrl;
    }

    public Long getClassId()
    {
        return this.classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public String getLastPageStaticUrl()
    {
        return this.lastPageStaticUrl;
    }

    public void setLastPageStaticUrl( String lastPageStaticUrl )
    {
        this.lastPageStaticUrl = lastPageStaticUrl;
    }

    public Integer getLastPn()
    {
        return lastPn;
    }

    public void setLastPn( Integer lastPn )
    {
        this.lastPn = lastPn;
    }
}
