package cn.com.mjsoft.cms.content.bean;

public class ContentStatusBean
{
    private Long selfContentId;
    private Long clickMonthCount = Long.valueOf( 1 );
    private Long clickWeekCount = Long.valueOf( 1 );
    private Long clickDayCount = Long.valueOf( 1 );
    private Long commMonthCount = Long.valueOf( 1 );
    private Long commWeekCount = Long.valueOf( 1 );
    private Long commDayCount = Long.valueOf( 1 );
    private Long commCount = Long.valueOf( 1 );
    private Long clickCount = Long.valueOf( 1 );
    private Long supportCount = Long.valueOf( 1 );
    private Long againstCount = Long.valueOf( 1 );

    public void addOneClick()
    {
        synchronized ( this )
        {
            this.clickMonthCount = Long.valueOf( this.clickMonthCount.longValue() + 1 );
            this.clickWeekCount = Long.valueOf( this.clickWeekCount.longValue() + 1 );
            this.clickDayCount = Long.valueOf( this.clickDayCount.longValue() + 1 );
            this.clickCount = Long.valueOf( this.clickCount.longValue() + 1 );
            
        }
    }
    
    
    public void addOneComm()
    {
        synchronized ( this )
        {
            this.commMonthCount = Long.valueOf( this.commMonthCount.longValue() + 1 );
            this.commWeekCount = Long.valueOf( this.commWeekCount.longValue() + 1 );
            this.commDayCount = Long.valueOf( this.commDayCount.longValue() + 1 );
            this.commCount = Long.valueOf( this.commCount.longValue() + 1 );
            
        }
    }
    
    
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
