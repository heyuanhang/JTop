package cn.com.mjsoft.cms.questionnaire.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.questionnaire.service.SurveyService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemSurveyGroupTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 1868737202685846332L;

    private static SurveyService surveyService = SurveyService.getInstance();

    private String groupId = "-1";

    private String classId = "";

    private String size = "12";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        // TODO 系统标签必须是管理员
        // TODO 必须是审核通过的
        List result = Collections.EMPTY_LIST;

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
            .getSecuritySession().getCurrentLoginSiteInfo();

        Page pageInfo = null;

        Long count = null;

        int nextPage = StringUtil.getIntValue( ( String ) this.pageContext
            .getRequest().getParameter( "pn" ), 1 );

        int pageSize = StringUtil.getIntValue( size, 12 );

        if( !groupId.equals( "-1" ) )
        {
            Long questIdVar = Long.valueOf( StringUtil.getLongValue( groupId,
                -1 ) );

            if( questIdVar.longValue() > 0 )
            {
                result = new ArrayList( 1 );

                result.add( surveyService
                    .retrieveSingleSurveyGroupBeanByQuestId( questIdVar ) );
            }
        }
        else if( "".equals( classId ) )
        {

            count = surveyService.retrieveAllSurveyGroupBeanCount( site
                .getSiteId() );

            pageInfo = new Page( pageSize, count.intValue(), nextPage );

            result = surveyService.retrieveAllSurveyGroupBeanList( site
                .getSiteId(), Long.valueOf( pageInfo.getFirstResult() ),
                Integer.valueOf( pageSize ) );
        }
        else if( StringUtil.isStringNotNull( classId ) )
        {
            count = surveyService.retrieveSurveyGroupBeanCountByClassId(
                classId, site.getSiteId() );

            pageInfo = new Page( pageSize, count.intValue(), nextPage );

            result = surveyService.retrieveSurveyGroupBeanListByClassId( site
                .getSiteId(), classId,
                Long.valueOf( pageInfo.getFirstResult() ), Integer
                    .valueOf( pageSize ) );
        }

        this.pageContext.setAttribute( "___system_dispose_page_object___",
            pageInfo );

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

}
