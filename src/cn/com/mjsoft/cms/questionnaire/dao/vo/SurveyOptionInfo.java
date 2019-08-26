package cn.com.mjsoft.cms.questionnaire.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "survey_option_info", id = "optionId", idMode = EntitySqlBridge.DB_IDENTITY )
public class SurveyOptionInfo
{
    private Long optionId = Long.valueOf( -1 );
    private Long surveyId;
    private String optionText;
    private String optionImage;
    private Integer vote = Integer.valueOf( 0 );
    private Integer votePer = Integer.valueOf( 0 );
    private String target;
    private String inputText;
    private Integer inputTextCount;
    private String siteFlag;

    public Long getOptionId()
    {
        return optionId;
    }

    public void setOptionId( Long optionId )
    {
        this.optionId = optionId;
    }

    public Long getSurveyId()
    {
        return this.surveyId;
    }

    public void setSurveyId( Long surveyId )
    {
        this.surveyId = surveyId;
    }

    public String getOptionText()
    {
        return this.optionText;
    }

    public void setOptionText( String optionText )
    {
        this.optionText = optionText;
    }

    public String getOptionImage()
    {
        return optionImage;
    }

    public void setOptionImage( String optionImage )
    {
        this.optionImage = optionImage;
    }

    public Integer getVote()
    {
        return this.vote;
    }

    public void setVote( Integer vote )
    {
        this.vote = vote;
    }

    public String getTarget()
    {
        return this.target;
    }

    public void setTarget( String target )
    {
        this.target = target;
    }

    public String getInputText()
    {
        return this.inputText;
    }

    public void setInputText( String inputText )
    {
        this.inputText = inputText;
    }

    public Integer getInputTextCount()
    {
        return inputTextCount;
    }

    public void setInputTextCount( Integer inputTextCount )
    {
        this.inputTextCount = inputTextCount;
    }

    public String getSiteFlag()
    {
        return siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    public Integer getVotePer()
    {
        return votePer;
    }

    public void setVotePer( Integer votePer )
    {
        this.votePer = votePer;
    }

}
