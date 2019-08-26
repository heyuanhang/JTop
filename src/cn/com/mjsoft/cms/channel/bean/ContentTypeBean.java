package cn.com.mjsoft.cms.channel.bean;

public class ContentTypeBean
{
    private Long typeId = Long.valueOf( -1 );

    private String typeName;

    private String typeFlag;

    private Integer groupId;

    private Long siteId;

    public Integer getGroupId()
    {
        return groupId;
    }

    public void setGroupId( Integer groupId )
    {
        this.groupId = groupId;
    }

    public String getTypeFlag()
    {
        return typeFlag;
    }

    public void setTypeFlag( String typeFlag )
    {
        this.typeFlag = typeFlag;
    }

    public Long getTypeId()
    {
        return typeId;
    }

    public void setTypeId( Long typeId )
    {
        this.typeId = typeId;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName( String typeName )
    {
        this.typeName = typeName;
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
