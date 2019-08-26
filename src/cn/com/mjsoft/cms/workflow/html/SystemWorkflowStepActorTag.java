package cn.com.mjsoft.cms.workflow.html;

import java.util.List;

import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemWorkflowStepActorTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -4784775813625322622L;

    private static WorkflowService workflowService = WorkflowService
        .getInstance();

    private String flowId;

    private String stepId;

    private String type;

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        Long stepIdVar = Long.valueOf( StringUtil.getLongValue( stepId, -1 ) );

        Long flowIdVar = Long.valueOf( StringUtil.getLongValue( flowId, -1 ) );

        Integer typeVar = Integer.valueOf( StringUtil.getIntValue( type, -1 ) );

        return workflowService.retrieveStepActorBeanList( flowIdVar, stepIdVar,
            typeVar );
    }

    protected String returnPutValueName()
    {
        return "Actor";
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

    public void setStepId( String stepId )
    {
        this.stepId = stepId;
    }

    public void setFlowId( String flowId )
    {
        this.flowId = flowId;
    }

    public void setType( String type )
    {
        this.type = type;
    }

}
