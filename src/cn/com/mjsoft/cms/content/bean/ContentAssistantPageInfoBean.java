package cn.com.mjsoft.cms.content.bean;

public class ContentAssistantPageInfoBean
{
    private Long contentId = Long.valueOf( -1 );
    private Integer pos;
    private String pageTitle;
    private String pageContent;
    private String pageStaticUrl;

    // 扩展bean数据
    private String pageUrl;

    public Long getContentId()
    {
        return contentId;
    }

    public void setContentId( Long contentId )
    {
        this.contentId = contentId;
    }

    public String getPageContent()
    {
        return pageContent;
    }

    public void setPageContent( String pageContent )
    {
        this.pageContent = pageContent;
    }

    public String getPageTitle()
    {
        return pageTitle;
    }

    public void setPageTitle( String pageTitle )
    {
        this.pageTitle = pageTitle;
    }

    public Integer getPos()
    {
        return pos;
    }

    public void setPos( Integer pos )
    {
        this.pos = pos;
    }

    public String getPageStaticUrl()
    {
        return pageStaticUrl;
    }

    public void setPageStaticUrl( String pageStaticUrl )
    {
        this.pageStaticUrl = pageStaticUrl;
    }

  

    public String getPageUrl()
    {
        return pageUrl;
    }

    public void setPageUrl( String pageUrl )
    {
        this.pageUrl = pageUrl;
    }

}
