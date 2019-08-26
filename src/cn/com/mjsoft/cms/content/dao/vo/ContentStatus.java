package cn.com.mjsoft.cms.content.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "content_status", id = "selfContentId", idMode = EntitySqlBridge.ASSIGNED )
public class ContentStatus
{
    private Long selfContentId;
    private Long clickMonthCount = Long.valueOf( 0 );
    private Long clickWeekCount = Long.valueOf( 0 );
    private Long clickDayCount = Long.valueOf( 0 );
    private Long commMonthCount = Long.valueOf( 0 );
    private Long commWeekCount = Long.valueOf( 0 );
    private Long commDayCount = Long.valueOf( 0 );
    private Long commCount = Long.valueOf( 0 );
    private Long clickCount = Long.valueOf( 0 );
    private Long supportCount = Long.valueOf( 0 );
    private Long againstCount = Long.valueOf( 0 );

    public Long getSelfContentId()
    {
        return this.selfContentId;
    }

    public void setSelfContentId( Long selfContentId )
    {
        this.selfContentId = selfContentId;
    }

    public Long getClickMonthCount()
    {
        return this.clickMonthCount;
    }

    public void setClickMonthCount( Long clickMonthCount )
    {
        this.clickMonthCount = clickMonthCount;
    }

    public Long getClickWeekCount()
    {
        return this.clickWeekCount;
    }

    public void setClickWeekCount( Long clickWeekCount )
    {
        this.clickWeekCount = clickWeekCount;
    }

    public Long getClickDayCount()
    {
        return this.clickDayCount;
    }

    public void setClickDayCount( Long clickDayCount )
    {
        this.clickDayCount = clickDayCount;
    }

    public Long getCommMonthCount()
    {
        return this.commMonthCount;
    }

    public void setCommMonthCount( Long commMonthCount )
    {
        this.commMonthCount = commMonthCount;
    }

    public Long getCommWeekCount()
    {
        return this.commWeekCount;
    }

    public void setCommWeekCount( Long commWeekCount )
    {
        this.commWeekCount = commWeekCount;
    }

    public Long getCommDayCount()
    {
        return this.commDayCount;
    }

    public void setCommDayCount( Long commDayCount )
    {
        this.commDayCount = commDayCount;
    }

    public Long getCommCount()
    {
        return this.commCount;
    }

    public void setCommCount( Long commCount )
    {
        this.commCount = commCount;
    }

    public Long getClickCount()
    {
        return this.clickCount;
    }

    public void setClickCount( Long clickCount )
    {
        this.clickCount = clickCount;
    }

    public Long getSupportCount()
    {
        return this.supportCount;
    }

    public void setSupportCount( Long supportCount )
    {
        this.supportCount = supportCount;
    }

    public Long getAgainstCount()
    {
        return this.againstCount;
    }

    public void setAgainstCount( Long againstCount )
    {
        this.againstCount = againstCount;
    }

}
