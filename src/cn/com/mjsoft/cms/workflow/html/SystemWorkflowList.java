package cn.com.mjsoft.cms.workflow.html;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;

public class SystemWorkflowList extends TagSupport
{
    private static final long serialVersionUID = 1399994929773995434L;

    private static WorkflowService workflowService = WorkflowService
        .getInstance();

    public int doStartTag() throws JspException
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
            .getSecuritySession().getCurrentLoginSiteInfo();

        List workflowList = workflowService
            .retrieveAllWorkflowBeanBySiteId( site.getSiteId() );

        pageContext.setAttribute( "workflowList", workflowList );

        return EVAL_PAGE;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "workflowList" );
        return EVAL_PAGE;
    }
}
