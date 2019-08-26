package cn.com.mjsoft.cms.questionnaire.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.questionnaire.service.SurveyService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientSurveyGroupTag extends AbstractIteratorTag
{

    private static final long serialVersionUID = 1L;

    private static SurveyService surveyService = SurveyService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    private String groupId = "-1";

    private String classId = "";

    private String page = "false";

    private String size = "12";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        List result = Collections.EMPTY_LIST;

        SiteGroupBean site = ( SiteGroupBean ) pageContext.getRequest().getAttribute(
            Constant.CONTENT.HTML_PUB_CURRENT_SITE );

        if( site == null )
        {
            site = ( SiteGroupBean ) this.pageContext.getRequest().getAttribute( "SiteObj" );

            if( site == null )
            {
                site = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                        .getRequest() );
            }
        }

        Page pageInfo = null;

        Long count = null;

        int nextPage = StringUtil.getIntValue( this.pageContext.getRequest().getParameter( "pn" ),
            1 );

        if( !"true".equals( page ) )
        {
            nextPage = 1;
        }

        int pageSize = StringUtil.getIntValue( size, 12 );

        if( !groupId.equals( "-1" ) )
        {
            Long questIdVar = Long.valueOf( StringUtil.getLongValue( groupId, -1 ) );

            if( questIdVar.longValue() > 0 )
            {
                result = new ArrayList( 1 );

                result.add( surveyService.retrieveSingleSurveyGroupBeanByQuestId( questIdVar ) );
            }
        }
        else if( "".equals( classId ) )
        {

            count = surveyService.retrieveAllSurveyGroupBeanCount( site.getSiteId() );

            pageInfo = new Page( pageSize, count.intValue(), nextPage );

            result = surveyService.retrieveAllSurveyGroupBeanList( site.getSiteId(), Long
                .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
        }
        else if( StringUtil.isStringNotNull( classId ) )
        {
            count = surveyService.retrieveSurveyGroupBeanCountByClassId( classId, site.getSiteId() );

            pageInfo = new Page( pageSize, count.intValue(), nextPage );

            result = surveyService.retrieveSurveyGroupBeanListByClassId( site.getSiteId(), classId,
                Long.valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
        }

        this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );

        //String queryCod = "groupId=" + groupId + "&classId=" + classId + "&page=" + page + "&size="
            //+ size;

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
        return "Group";
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

    public void setClassId( String classId )
    {
        this.classId = classId;
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
