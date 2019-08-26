package cn.com.mjsoft.cms.workflow.html;

import java.util.List;

import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemWorkflow extends AbstractIteratorTag
{
    private static final long serialVersionUID = 1486082938873751028L;

    private static WorkflowService workflowService = WorkflowService
        .getInstance();

    protected void initTag()
    {
        // TODO Auto-generated method stub
    }

    protected List returnObjectList()
    {
        return null;
    }

    protected String returnPutValueName()
    {
        return "Workflow";
    }

    protected String returnRequestAndPageListAttName()
    {
        return "workflowList";
    }

    protected Object returnSingleObject()
    {
        Long id = Long.valueOf( StringUtil.getLongValue( this.getId(), -1 ) );

        return workflowService.retrieveSingleWorkflowBean( id );
    }

    protected String returnValueRange()
    {
        return "pageRange";
    }
}
