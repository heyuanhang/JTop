package cn.com.mjsoft.cms.schedule.bean;

import java.sql.Timestamp;
import java.util.Date;

public class ScheduleJobDetailBean
{
    private Long jobId = Long.valueOf( -1 );
    private String jobName;
    private String jobDesc;
    private String jobExecuteClass;
    private Integer triggerType;
    private Integer periodSegment;
    private Integer periodVar;
    private String dayExeTime;
    private String cronExpression;
    private Date lastExcuteTime;
    private Date jobStartDate;
    private Date jobEndDate;
    private Integer systemJob;
    private Integer useState;
    private Long siteId;

    public Long getJobId()
    {
        return this.jobId;
    }

    public void setJobId( Long jobId )
    {
        this.jobId = jobId;
    }

    public String getJobName()
    {
        return this.jobName;
    }

    public void setJobName( String jobName )
    {
        this.jobName = jobName;
    }

    public String getJobDesc()
    {
        return this.jobDesc;
    }

    public void setJobDesc( String jobDesc )
    {
        this.jobDesc = jobDesc;
    }

    public String getJobExecuteClass()
    {
        return this.jobExecuteClass;
    }

    public void setJobExecuteClass( String jobExecuteClass )
    {
        this.jobExecuteClass = jobExecuteClass;
    }

    public Integer getTriggerType()
    {
        return this.triggerType;
    }

    public void setTriggerType( Integer triggerType )
    {
        this.triggerType = triggerType;
    }

    public Integer getPeriodSegment()
    {
        return this.periodSegment;
    }

    public void setPeriodSegment( Integer periodSegment )
    {
        this.periodSegment = periodSegment;
    }

    public Integer getPeriodVar()
    {
        return this.periodVar;
    }

    public void setPeriodVar( Integer periodVar )
    {
        this.periodVar = periodVar;
    }

    public String getCronExpression()
    {
        return this.cronExpression;
    }

    public void setCronExpression( String cronExpression )
    {
        this.cronExpression = cronExpression;
    }

    public Date getJobStartDate()
    {
        return this.jobStartDate;
    }

    public void setJobStartDate( Date jobStartDate )
    {
        this.jobStartDate = jobStartDate;
    }

    public Date getJobEndDate()
    {
        return this.jobEndDate;
    }

    public void setJobEndDate( Date jobEndDate )
    {
        this.jobEndDate = jobEndDate;
    }

    public Integer getUseState()
    {
        return this.useState;
    }

    public void setUseState( Integer useState )
    {
        this.useState = useState;
    }

    public Date getLastExcuteTime()
    {
        return lastExcuteTime;
    }

    public void setLastExcuteTime( Date lastExcuteTime )
    {
        this.lastExcuteTime = lastExcuteTime;
    }

    public Integer getSystemJob()
    {
        return systemJob;
    }

    public void setSystemJob( Integer systemJob )
    {
        this.systemJob = systemJob;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public String getDayExeTime()
    {
        return dayExeTime;
    }

    public void setDayExeTime( String dayExeTime )
    {
        this.dayExeTime = dayExeTime;
    }
}
