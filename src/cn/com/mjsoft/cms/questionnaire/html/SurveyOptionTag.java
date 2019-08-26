package cn.com.mjsoft.cms.questionnaire.html;

import java.util.Collections;
import java.util.List;

import cn.com.mjsoft.cms.questionnaire.bean.SurveyBaseInfoBean;
import cn.com.mjsoft.cms.questionnaire.service.SurveyService;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SurveyOptionTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 8280373238002041174L;

    private static SurveyService surveyService = SurveyService.getInstance();

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        List result = Collections.EMPTY_LIST;

        SurveyBaseInfoBean surveyBean = ( SurveyBaseInfoBean ) this.pageContext
            .getAttribute( "Survey" );

        if( surveyBean != null )
        {
            result = surveyService
                .retrieveSurveyOptionBeanListBySurveyId( surveyBean
                    .getSurveyId() );
        }

        return result;

    }

    protected String returnPutValueName()
    {
        return "Option";
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
}
