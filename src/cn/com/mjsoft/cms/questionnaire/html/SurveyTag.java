package cn.com.mjsoft.cms.questionnaire.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.com.mjsoft.cms.questionnaire.service.SurveyService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SurveyTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 3949370371472266015L;

    private static SurveyService surveyService = SurveyService.getInstance();

    private String surveyId = "-1";

    private String groupId = "-1";

    private String groupFlag = "";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {

        List result = Collections.EMPTY_LIST;

        Long groupIdVar = Long.valueOf( StringUtil.getLongValue( groupId, -1 ) );
        if( groupIdVar.longValue() > 0 )
        {
            result = surveyService
                .retrieveSurveyBaseInfoBeanListByGroupId( groupIdVar );
        }
        else if( groupFlag.length() > 0 )
        {
            result = surveyService
                .retrieveSurveyBaseInfoBeanListByGroupFlag( groupFlag );
        }
        else
        {
            Long surveyIdVar = Long.valueOf( StringUtil.getLongValue( surveyId,
                -1 ) );

            if( surveyIdVar.longValue() > 0 )
            {
                result = new ArrayList( 1 );
                result.add( surveyService
                    .retrieveSingleSurveyBaseInfoBeanBySurveyId( surveyIdVar ) );
            }
        }

        return result;
    }

    protected String returnPutValueName()
    {
        return "Survey";
    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected Object returnSingleObject()
    {
        return null;
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
    }

    public void setGroupId( String groupId )
    {
        this.groupId = groupId;
    }

    public void setSurveyId( String surveyId )
    {
        this.surveyId = surveyId;
    }

    public void setGroupFlag( String groupFlag )
    {
        this.groupFlag = groupFlag;
    }

}
