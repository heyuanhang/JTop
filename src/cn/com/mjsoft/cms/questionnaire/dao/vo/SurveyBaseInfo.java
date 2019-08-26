package cn.com.mjsoft.cms.questionnaire.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "survey_base_info", id = "surveyId", idMode = EntitySqlBridge.DB_IDENTITY )
public class SurveyBaseInfo
{

    private Long surveyId = Long.valueOf( -1 );
    private Long groupId;
    private String groupFlag;
    private Integer optionType;
    private String surveyTitle;
    private Integer haveText = Integer.valueOf( 0 );
    private String addiTitle;
    private String siteFlag;
    private Integer orderFlag = Integer.valueOf( 1 );

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
