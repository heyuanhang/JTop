package cn.com.mjsoft.cms.questionnaire.bean;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.util.StringUtil;

public class SurveyOptionInfoBean
{
    private Long optionId = Long.valueOf( -1 );
    private Long surveyId;
    private String optionText;
    private String optionImage;
    private Integer vote;
    private Integer votePer;
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

        if( StringUtil.isStringNotNull( this.optionImage ) )
        {
            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                .getEntry( this.siteFlag );

            String reUrl = StringUtil.subString( this.optionImage,
                this.optionImage.indexOf( "reUrl=" ) + 6, this.optionImage
                    .indexOf( ";", this.optionImage.indexOf( "reUrl=" ) + 6 ) );

            return site.getSiteImagePrefixUrl() + reUrl;
        }

        return "no_url";
    }

    public String getOptionImageResId()
    {
        if( StringUtil.isStringNotNull( this.optionImage ) )
        {
            return StringUtil.subString( this.optionImage, this.optionImage
                .indexOf( "id=" ) + 3, this.optionImage.indexOf( ";",
                this.optionImage.indexOf( "id=" ) + 3 ) );
        }

        return "";
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
