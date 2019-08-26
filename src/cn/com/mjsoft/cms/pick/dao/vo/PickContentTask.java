package cn.com.mjsoft.cms.pick.dao.vo;

import java.sql.Timestamp;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "pick_content_task", id = "pickTaskId", idMode = EntitySqlBridge.DB_IDENTITY )
public class PickContentTask
{

    private Long pickTaskId;
    private String taskName;
    private Long ruleId;
    private Long classId;
    private Integer downOutImg;
    private Integer guideImgPos;
    private Integer deleteHref;
    private String taskDesc;
    private Integer pickThreadCount;
    private Integer pickInterval;
    private Integer pickMaxListPage;
    private Integer pickMaxContent;
    private Integer censorMode;
    private Long siteId;
    private Timestamp excuteDT;
    private Integer periodType;
    private Integer period;
    private Long selfJobId;

    private Integer pickType = Integer.valueOf( 1 );// 1=html 2=out 3=db

    private String rssUrl;

    private String rssRuleBeClass;

    private String dbRuleBeClass;

    public Long getPickTaskId()
    {
        return this.pickTaskId;
    }

    public void setPickTaskId( Long pickTaskId )
    {
        this.pickTaskId = pickTaskId;
    }

    public String getTaskName()
    {
        return this.taskName;
    }

    public void setTaskName( String taskName )
    {
        this.taskName = taskName;
    }

    public Long getRuleId()
    {
        return this.ruleId;
    }

    public void setRuleId( Long ruleId )
    {
        this.ruleId = ruleId;
    }

    public Long getClassId()
    {
        return this.classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Integer getPickThreadCount()
    {
        return this.pickThreadCount;
    }

    public void setPickThreadCount( Integer pickThreadCount )
    {
        this.pickThreadCount = pickThreadCount;
    }

    public Integer getPickInterval()
    {
        return pickInterval;
    }

    public void setPickInterval( Integer pickInterval )
    {
        this.pickInterval = pickInterval;
    }

    public Integer getPickMaxListPage()
    {
        return this.pickMaxListPage;
    }

    public void setPickMaxListPage( Integer pickMaxListPage )
    {
        this.pickMaxListPage = pickMaxListPage;
    }

    public Integer getPickMaxContent()
    {
        return this.pickMaxContent;
    }

    public void setPickMaxContent( Integer pickMaxContent )
    {
        this.pickMaxContent = pickMaxContent;
    }

    public Integer getCensorMode()
    {
        return censorMode;
    }

    public void setCensorMode( Integer censorMode )
    {
        this.censorMode = censorMode;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public String getTaskDesc()
    {
        return taskDesc;
    }

    public void setTaskDesc( String taskDesc )
    {
        this.taskDesc = taskDesc;
    }

    public Timestamp getExcuteDT()
    {
        return excuteDT;
    }

    public void setExcuteDT( Timestamp excuteDT )
    {
        this.excuteDT = excuteDT;
    }

    public Integer getPeriod()
    {
        return period;
    }

    public void setPeriod( Integer period )
    {
        this.period = period;
    }

    public Integer getPeriodType()
    {
        return periodType;
    }

    public void setPeriodType( Integer periodType )
    {
        this.periodType = periodType;
    }

    public Long getSelfJobId()
    {
        return selfJobId;
    }

    public void setSelfJobId( Long selfJobId )
    {
        this.selfJobId = selfJobId;
    }

    public Integer getDeleteHref()
    {
        return deleteHref;
    }

    public void setDeleteHref( Integer deleteHref )
    {
        this.deleteHref = deleteHref;
    }

    public Integer getDownOutImg()
    {
        return downOutImg;
    }

    public void setDownOutImg( Integer downOutImg )
    {
        this.downOutImg = downOutImg;
    }

    public Integer getGuideImgPos()
    {
        return guideImgPos;
    }

    public void setGuideImgPos( Integer guideImgPos )
    {
        this.guideImgPos = guideImgPos;
    }

    public Integer getPickType()
    {
        return pickType;
    }

    public void setPickType( Integer pickType )
    {
        this.pickType = pickType;
    }

    public String getDbRuleBeClass()
    {
        return dbRuleBeClass;
    }

    public void setDbRuleBeClass( String dbRuleBeClass )
    {
        this.dbRuleBeClass = dbRuleBeClass;
    }

    public String getRssRuleBeClass()
    {
        return rssRuleBeClass;
    }

    public void setRssRuleBeClass( String rssRuleBeClass )
    {
        this.rssRuleBeClass = rssRuleBeClass;
    }

    public String getRssUrl()
    {
        return rssUrl;
    }

    public void setRssUrl( String rssUrl )
    {
        this.rssUrl = rssUrl;
    }

}
