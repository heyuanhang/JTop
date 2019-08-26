package cn.com.mjsoft.cms.interflow.dao.vo;

import java.util.Date;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "site_announce", id = "anId", idMode = EntitySqlBridge.DB_IDENTITY )
public class SiteAnnounce
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

}
