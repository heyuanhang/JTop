package cn.com.mjsoft.cms.questionnaire.bean;

public class SurveyBaseInfoBean
{
    private Long surveyId;
    private Long groupId;
    private String groupFlag;
    private Integer optionType;
    private String surveyTitle;
    private String siteFlag;
    private Integer haveText;
    private String addiTitle;
    private Integer orderFlag;

    public Long getSurveyId()
    {
        return this.surveyId;
    }

    public void setSurveyId( Long surveyId )
    {
        this.surveyId = surveyId;
    }

    public Long getGroupId()
    {
        return this.groupId;
    }

    public void setGroupId( Long groupId )
    {
        this.groupId = groupId;
    }

    public Integer getOptionType()
    {
        return optionType;
    }

    public void setOptionType( Integer optionType )
    {
        this.optionType = optionType;
    }

    public String getSurveyTitle()
    {
        return surveyTitle;
    }

    public void setSurveyTitle( String surveyTitle )
    {
        this.surveyTitle = surveyTitle;
    }

    public String getSiteFlag()
    {
        return siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    public Integer getOrderFlag()
    {
        return orderFlag;
    }

    public void setOrderFlag( Integer orderFlag )
    {
        this.orderFlag = orderFlag;
    }

    public String getGroupFlag()
    {
        return groupFlag;
    }

    public void setGroupFlag( String groupFlag )
    {
        this.groupFlag = groupFlag;
    }

    public Integer getHaveText()
    {
        return haveText;
    }

    public void setHaveText( Integer haveText )
    {
        this.haveText = haveText;
    }

    public String getAddiTitle()
    {
        return addiTitle;
    }

    public void setAddiTitle( String addiTitle )
    {
        this.addiTitle = addiTitle;
    }

  
}
