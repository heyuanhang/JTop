package cn.com.mjsoft.cms.workflow.html;

import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.workflow.bean.WorkflowStepInfoBean;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemWorkflowStepTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -2193366097953730749L;

    private static WorkflowService workflowService = WorkflowService
        .getInstance();

    private String flowId = "";

    private String step = "";

    private String startMode = "false";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        Long flowIdVar = Long.valueOf( StringUtil.getLongValue( flowId, -1 ) );

        Long stepVar = Long.valueOf( StringUtil.getLongValue( step, -1 ) );

        WorkflowStepInfoBean wsb = null;

        if( stepVar.intValue() != -1 )
        {
            List result = new ArrayList();

            wsb = workflowService
                .retrieveWorkflowStepBeanByFlowIdAndStep( stepVar );

            if( "true".equals( startMode ) )
            {
                wsb.setStepId( Constant.WORKFLOW.START_ACTION_ID_VALUE );
            }

            result.add( wsb );

            return result;
        }
        else
        {
            List resList = workflowService
                .retrieveWorkflowStepBeanListByFlowId( flowIdVar );

            if( "true".equals( startMode ) )
            {
                for ( int i = 0; i < resList.size(); i++ )
                {
                    wsb = ( WorkflowStepInfoBean ) resList.get( i );

                    if( Constant.COMMON.ON.equals( wsb.getIsStart() ) )
                    {
                        wsb.setStepId( Constant.WORKFLOW.START_ACTION_ID_VALUE );
                        break;
                    }
                }
            }

            return resList;
        }
    }

    protected String returnPutValueName()
    {
        return "Step";
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

    public void setStep( String step )
    {
        this.step = step;
    }

    public void setStartMode( String startMode )
    {
        this.startMode = startMode;
    }

}
