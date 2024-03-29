package cn.com.mjsoft.cms.questionnaire.bean;

import java.util.Date;

public class SurveyGroupBean
{
    private Long groupId = Long.valueOf( -1 );
    private Long classId = Long.valueOf( -1 );
    private String questName;
    private String flagName;
    private Integer restriction;
    private Integer restInterval;
    private String questDesc;
    private Date startDate;
    private Date endDate;
    private Integer needCaptcha;
    private Integer useState;
    private String handlers;
    private Long siteId;

    public Long getGroupId()
    {
        return groupId;
    }

    public void setGroupId( Long groupId )
    {
        this.groupId = groupId;
    }

    public String getQuestName()
    {
        return this.questName;
    }

    public void setQuestName( String questName )
    {
        this.questName = questName;
    }

    public String getFlagName()
    {
        return this.flagName;
    }

    public void setFlagName( String flagName )
    {
        this.flagName = flagName;
    }

    public Integer getRestInterval()
    {
        return restInterval;
    }

    public void setRestInterval( Integer restInterval )
    {
        this.restInterval = restInterval;
    }

    public Integer getNeedCaptcha()
    {
        return needCaptcha;
    }

    public void setNeedCaptcha( Integer needCaptcha )
    {
        this.needCaptcha = needCaptcha;
    }

    public Integer getRestriction()
    {
        return restriction;
    }

    public void setRestriction( Integer restriction )
    {
        this.restriction = restriction;
    }

    public Integer getUseState()
    {
        return useState;
    }

    public void setUseState( Integer useState )
    {
        this.useState = useState;
    }

    public String getHandlers()
    {
        return this.handlers;
    }

    public void setHandlers( String handlers )
    {
        this.handlers = handlers;
    }

    public String getQuestDesc()
    {
        return questDesc;
    }

    public void setQuestDesc( String questDesc )
    {
        this.questDesc = questDesc;
    }

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate( Date endDate )
    {
        this.endDate = endDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate( Date startDate )
    {
        this.startDate = startDate;
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
