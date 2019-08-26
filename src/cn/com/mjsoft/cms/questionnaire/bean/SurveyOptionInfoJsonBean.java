package cn.com.mjsoft.cms.questionnaire.bean;

public class SurveyOptionInfoJsonBean
{
    private Long optionId = Long.valueOf( -1L );
    private Long surveyId;
    private String optionText;
    private String optionImage;
    private Integer vote;
    private Integer votePer;
    private String target;
    private String inputText;
    private Integer inputTextCount;
    private String optionImageResId;
    private String siteFlag;

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
        return this.inputTextCount;
    }

    public void setInputTextCount( Integer inputTextCount )
    {
        this.inputTextCount = inputTextCount;
    }

    public Long getOptionId()
    {
        return this.optionId;
    }

    public void setOptionId( Long optionId )
    {
        this.optionId = optionId;
    }

    public String getOptionImage()
    {
        return this.optionImage;
    }

    public void setOptionImage( String optionImage )
    {
        this.optionImage = optionImage;
    }

    public String getOptionImageResId()
    {
        return this.optionImageResId;
    }

    public void setOptionImageResId( String optionImageResId )
    {
        this.optionImageResId = optionImageResId;
    }

    public String getOptionText()
    {
        return this.optionText;
    }

    public void setOptionText( String optionText )
    {
        this.optionText = optionText;
    }

    public String getSiteFlag()
    {
        return this.siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    public Long getSurveyId()
    {
        return this.surveyId;
    }

    public void setSurveyId( Long surveyId )
    {
        this.surveyId = surveyId;
    }

    public String getTarget()
    {
        return this.target;
    }

    public void setTarget( String target )
    {
        this.target = target;
    }

    public Integer getVote()
    {
        return this.vote;
    }

    public void setVote( Integer vote )
    {
        this.vote = vote;
    }

    public Integer getVotePer()
    {
        return this.votePer;
    }

    public void setVotePer( Integer votePer )
    {
        this.votePer = votePer;
    }
}
