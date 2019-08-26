package cn.com.mjsoft.cms.questionnaire.html;

import java.util.Collections;
import java.util.List;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.questionnaire.bean.SurveyBaseInfoBean;
import cn.com.mjsoft.cms.questionnaire.service.SurveyService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SurveyTextTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 1L;

    private static SurveyService surveyService = SurveyService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    private String page = "false";

    private String size = "15";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        List result = Collections.EMPTY_LIST;

        int pageNum = StringUtil
            .getIntValue( this.pageContext.getRequest().getParameter( "pn" ), 1 );

        if( !"true".equals( page ) )
        {
            pageNum = 1;
        }

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        Long count = null;

        SurveyBaseInfoBean surveyBean = ( SurveyBaseInfoBean ) this.pageContext
            .getAttribute( "Survey" );

        if( surveyBean != null )
        {
            count = surveyService.retrieveSurveyVoteInfoCountBySurveyId( surveyBean.getSurveyId() );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = surveyService.retrieveSurveyVoteInfoBySurveyId( surveyBean.getSurveyId(), Long
                .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
        }

        this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );

        //String queryCod = "page=" + page + "&size=" + size;

        ContentClassBean classBean = ( ContentClassBean ) this.pageContext.getAttribute( "Class" );

        if( classBean != null )
        {
            // 静态分页
            publishService.htmlTagPage( this.pageContext, null, classBean.getClassId(), classBean,
                classBean.getClassId(), pageInfo, page, "" );
        }

        return result;
    }

    protected String returnPutValueName()
    {
        return "OptText";
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

    public void setSize( String size )
    {
        this.size = size;
    }

    public void setPage( String page )
    {
        this.page = page;
    }
}
