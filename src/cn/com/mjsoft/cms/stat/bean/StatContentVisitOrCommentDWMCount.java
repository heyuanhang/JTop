package cn.com.mjsoft.cms.stat.bean;

public class StatContentVisitOrCommentDWMCount
{
    private Long dayCount = Long.valueOf( 1 );

    private Long weekCount = Long.valueOf( 1 );

    private Long monthCount = Long.valueOf( 1 );

    private Long noLimitCount = Long.valueOf( 1 );

    public Long getDayCount()
    {
        return dayCount;
    }

    public void setDayCount( Long dayCount )
    {
        this.dayCount = dayCount;
    }

    public Long getMonthCount()
    {
        return monthCount;
    }

    public void setMonthCount( Long monthCount )
    {
        this.monthCount = monthCount;
    }

    public Long getWeekCount()
    {
        return weekCount;
    }

    public void setWeekCount( Long weekCount )
    {
        this.weekCount = weekCount;
    }

    public Long getNoLimitCount()
    {
        return noLimitCount;
    }

    public void setNoLimitCount( Long noLimitCount )
    {
        this.noLimitCount = noLimitCount;
    }

    public void addOneClick()
    {
        synchronized ( this )
        {
            this.dayCount = Long.valueOf( this.dayCount.longValue() + 1 );
            this.weekCount = Long.valueOf( this.weekCount.longValue() + 1 );
            this.monthCount = Long.valueOf( this.monthCount.longValue() + 1 );
            this.noLimitCount = Long
                .valueOf( this.noLimitCount.longValue() + 1 );
        }
    }

    public void setDayZero()
    {
        this.dayCount = Long.valueOf( 0 );
    }

    public void setWeekZero()
    {
        this.weekCount = Long.valueOf( 0 );
    }

    public void setMonthZero()
    {
        this.monthCount = Long.valueOf( 0 );
    }

}
