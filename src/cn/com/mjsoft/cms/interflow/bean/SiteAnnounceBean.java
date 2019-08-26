package cn.com.mjsoft.cms.interflow.bean;

import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import cn.com.mjsoft.framework.util.StringUtil;

public class SiteAnnounceBean
{
    private Long anId;
    private String anTitle;
    private String content;
    private Date showStartDate;
    private Date showEndDate;
    private Long anOrder;
    private Long siteId;

    public Long getAnId()
    {
        return anId;
    }

    public void setAnId( Long anId )
    {
        this.anId = anId;
    }

    public String getAnTitle()
    {
        return this.anTitle;
    }

    public void setAnTitle( String anTitle )
    {
        this.anTitle = anTitle;
    }

    public String getContent()
    {
        return this.content;
    }

    public void setContent( String content )
    {
        this.content = content;
    }

    public Date getShowStartDate()
    {
        return this.showStartDate;
    }

    public void setShowStartDate( Date showStartDate )
    {
        this.showStartDate = showStartDate;
    }

    public Date getShowEndDate()
    {
        return this.showEndDate;
    }

    public void setShowEndDate( Date showEndDate )
    {
        this.showEndDate = showEndDate;
    }

    public Long getAnOrder()
    {
        return anOrder;
    }

    public void setAnOrder( Long anOrder )
    {
        this.anOrder = anOrder;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    // ///////////////////////////////////业务方法/////////////////////////////////////

    public String getContentText()
    {
        String text = Jsoup.clean( this.content, Whitelist.none() );

        text = StringUtil.replaceString( text, " ", "", false, false );

        text = StringUtil.replaceString( text, "&nbsp;", "", false, false );

        return text;
    }

}
