package cn.com.mjsoft.cms.workflow.html;

import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemWorkflowStepActionTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 2104416248799416926L;

    private static WorkflowService workflowService = WorkflowService
        .getInstance();

    private String flowId;

    private String actId;

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {

        Long flowIdVar = Long.valueOf( StringUtil.getLongValue( flowId, -1 ) );

        Long actIdVar = Long.valueOf( StringUtil.getLongValue( actId, -1 ) );

        if( actIdVar.longValue() > 0 )
        {
            List result = new ArrayList( 1 );

            result.add( workflowService
                .retrieveSingleWorkflowStepActionBean( actIdVar ) );
            return result;
        }
        else
        {
            return workflowService.retrieveStepActionBeanList( flowIdVar );
        }
    }

    protected String returnPutValueName()
    {
        return "Action";
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

    public void setFlowId( String flowId )
    {
        this.flowId = flowId;
    }

    public void setActId( String actId )
    {
        this.actId = actId;
    }

}
