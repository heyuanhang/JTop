package cn.com.mjsoft.cms.workflow.html;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.workflow.bean.WorkflowStepInfoBean;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemFlowStepActionTag extends TagSupport
{
    private static final long serialVersionUID = -668579684908615871L;

    private static WorkflowService workflowService = WorkflowService.getInstance();

    private String contentId;

    private String flowId;

    private String step;

    public int doStartTag() throws JspException
    {
        Integer infoType = Constant.WORKFLOW.INFO_TYPE_CONTENT;

        Long flowIdvar = Long.valueOf( StringUtil.getLongValue( flowId, -1 ) );

        Integer stepVar = Integer.valueOf( StringUtil.getIntValue( step, -1 ) );

        Long contentIdvar = Long.valueOf( StringUtil.getLongValue( contentId, -1 ) );

        WorkflowStepInfoBean bean = workflowService.retrieveSingleWorkflowStepInfoBean(
            contentIdvar, infoType );

        pageContext.setAttribute( "Step", bean );

        return EVAL_PAGE;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "Step" );
        return EVAL_PAGE;
    }

    public void setStep( String currentStep )
    {
        this.step = currentStep;
    }

    public void setFlowId( String flowId )
    {
        this.flowId = flowId;
    }

    public void setContentId( String contentId )
    {
        this.contentId = contentId;
    }

}
