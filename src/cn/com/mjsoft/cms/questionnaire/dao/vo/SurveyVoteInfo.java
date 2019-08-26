package cn.com.mjsoft.cms.questionnaire.dao.vo;

import java.util.Date;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "survey_vote_info", id = "", idMode = EntitySqlBridge.NO_KEY_ID )
public class SurveyVoteInfo
{
    private Long optId;
    private Long surveyId;
    private String voteText;
    private String voteMan;
    private String ip;
    private Date voteDate;

    public Long getSurveyId()
    {
        return this.surveyId;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp( String ip )
    {
        this.ip = ip;
    }

    public Long getOptId()
    {
        return optId;
    }

    public void setOptId( Long optId )
    {
        this.optId = optId;
    }

    public Date getVoteDate()
    {
        return voteDate;
    }

    public void setVoteDate( Date voteDate )
    {
        this.voteDate = voteDate;
    }

    public String getVoteMan()
    {
        return voteMan;
    }

    public void setVoteMan( String voteMan )
    {
        this.voteMan = voteMan;
    }

    public String getVoteText()
    {
        return voteText;
    }

    public void setVoteText( String voteText )
    {
        this.voteText = voteText;
    }

    public void setSurveyId( Long surveyId )
    {
        this.surveyId = surveyId;
    }

}
