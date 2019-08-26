package cn.com.mjsoft.cms.channel.bean;

public class TagWordBean
{
    private Long tagId;
    private String tagName;
    private String tagFlag;
    private Long tagTypeId;
    private String firstChar;
    private Long clickCount;
    private Long contentCount;
    private String creator;
    private Long siteId;

    public Long getTagId()
    {
        return this.tagId;
    }

    public void setTagId( Long tagId )
    {
        this.tagId = tagId;
    }

    public String getTagName()
    {
        return this.tagName;
    }

    public void setTagName( String tagName )
    {
        this.tagName = tagName;
    }

    public String getTagFlag()
    {
        return this.tagFlag;
    }

    public void setTagFlag( String tagFlag )
    {
        this.tagFlag = tagFlag;
    }

    public Long getTagTypeId()
    {
        return this.tagTypeId;
    }

    public void setTagTypeId( Long tagTypeId )
    {
        this.tagTypeId = tagTypeId;
    }

    public Long getContentCount()
    {
        return this.contentCount;
    }

    public void setContentCount( Long contentCount )
    {
        this.contentCount = contentCount;
    }

    public String getCreator()
    {
        return this.creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public Long getSiteId()
    {
        return this.siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public String getFirstChar()
    {
        return firstChar;
    }

    public void setFirstChar( String firstChar )
    {
        this.firstChar = firstChar;
    }

    public Long getClickCount()
    {
        return clickCount;
    }

    public void setClickCount( Long clickCount )
    {
        this.clickCount = clickCount;
    }

}
