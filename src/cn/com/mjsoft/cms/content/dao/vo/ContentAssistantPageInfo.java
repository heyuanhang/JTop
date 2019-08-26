package cn.com.mjsoft.cms.content.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "content_assistant_page_info", id = "", idMode = EntitySqlBridge.NO_KEY_ID )
public class ContentAssistantPageInfo
{

    private Long contentId;
    private Integer pos;
    private String pageTitle;
    private String pageContent;
    private String pageStaticUrl;
    private Integer startPos;
    private Integer endPos;

    public ContentAssistantPageInfo()
    {
    }

    public Long getContentId()
    {
        return this.contentId;
    }

    public void setContentId( Long contentId )
    {
        this.contentId = contentId;
    }

    public Integer getPos()
    {
        return this.pos;
    }

    public void setPos( Integer pos )
    {
        this.pos = pos;
    }

    public String getPageTitle()
    {
        return this.pageTitle;
    }

    public void setPageTitle( String pageTitle )
    {
        this.pageTitle = pageTitle;
    }

    public String getPageContent()
    {
        return this.pageContent;
    }

    public void setPageContent( String pageContent )
    {
        this.pageContent = pageContent;
    }

    public String getPageStaticUrl()
    {
        return pageStaticUrl;
    }

    public void setPageStaticUrl( String pageStaticUrl )
    {
        this.pageStaticUrl = pageStaticUrl;
    }

    public Integer getEndPos()
    {
        return endPos;
    }

    public void setEndPos( Integer endPos )
    {
        this.endPos = endPos;
    }

    public Integer getStartPos()
    {
        return startPos;
    }

    public void setStartPos( Integer startPos )
    {
        this.startPos = startPos;
    }

}
