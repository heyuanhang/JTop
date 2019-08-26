package cn.com.mjsoft.cms.workflow.bean;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;

public class WorkflowStepActionBean
{
    private static WorkflowService workflowService = WorkflowService.getInstance();

    private Long actionId = Long.valueOf( -1 );

    private Long flowId;

    private Long fromStepId;

    private String actDesc;

    private Long toStepId;

    private String passActionName;

    private Integer orgBossMode;

    private Integer recallMode;

    private Integer conjunctOrgFlag;

    private Integer conjunctRoleFlag;

    private Integer conjunctManFlag;

    private Integer needRequest;
    
    private Integer directMode;

    public Long getActionId()
    {
        return actionId;
    }

    public void setActionId( Long actionId )
    {
        this.actionId = actionId;
    }

    public Long getFlowId()
    {
        return flowId;
    }

    public void setFlowId( Long flowId )
    {
        this.flowId = flowId;
    }

    public Long getFromStepId()
    {
        return fromStepId;
    }

    public void setFromStepId( Long fromStepId )
    {
        this.fromStepId = fromStepId;
    }

    public String getPassActionName()
    {
        return passActionName;
    }

    public void setPassActionName( String passActionName )
    {
        this.passActionName = passActionName;
    }

    public Long getToStepId()
    {
        return toStepId;
    }

    public void setToStepId( Long toStepId )
    {
        this.toStepId = toStepId;
    }

    public String getFromStepNodeName()
    {
        if( Constant.WORKFLOW.START_ACTION_ID_VALUE.equals( this.fromStepId ) )
        {
            return "开始";
        }

        WorkflowStepInfoBean stepBean = workflowService
            .retrieveWorkflowStepBeanByFlowIdAndStep( this.fromStepId );

        if( stepBean != null )
        {
            return stepBean.getStepNodeName();
        }

        return "";
    }

    public String getToStepNodeName()
    {
        if( Constant.WORKFLOW.END_ACTION_ID_VALUE.equals( this.toStepId ) )
        {
            return "通过";
        }

        if( Constant.WORKFLOW.REJECT_ACTION_ID_VALUE.equals( this.toStepId ) )
        {
            return "否决";
        }

        if( Constant.WORKFLOW.DRAFT_ACTION_ID_VALUE.equals( this.toStepId ) )
        {
            return "退稿";
        }

        if( Constant.WORKFLOW.START_ACTION_ID_VALUE.equals( this.toStepId ) )
        {
            return "开始";
        }

        WorkflowStepInfoBean stepBean = workflowService
            .retrieveWorkflowStepBeanByFlowIdAndStep( this.toStepId );

        if( stepBean != null )
        {
            return stepBean.getStepNodeName();
        }

        return "";
    }

    public String getActDesc()
    {
        return actDesc;
    }

    public void setActDesc( String actDesc )
    {
        this.actDesc = actDesc;
    }

    public Integer getNeedRequest()
    {
        return needRequest;
    }

    public void setNeedRequest( Integer needRequest )
    {
        this.needRequest = needRequest;
    }

    public Integer getConjunctManFlag()
    {
        return conjunctManFlag;
    }

    public void setConjunctManFlag( Integer conjunctManFlag )
    {
        this.conjunctManFlag = conjunctManFlag;
    }

    public Integer getConjunctOrgFlag()
    {
        return conjunctOrgFlag;
    }

    public void setConjunctOrgFlag( Integer conjunctOrgFlag )
    {
        this.conjunctOrgFlag = conjunctOrgFlag;
    }

    public Integer getConjunctRoleFlag()
    {
        return conjunctRoleFlag;
    }

    public void setConjunctRoleFlag( Integer conjunctRoleFlag )
    {
        this.conjunctRoleFlag = conjunctRoleFlag;
    }

    public Integer getOrgBossMode()
    {
        return orgBossMode;
    }

    public void setOrgBossMode( Integer orgBossMode )
    {
        this.orgBossMode = orgBossMode;
    }

    public Integer getRecallMode()
    {
        return recallMode;
    }

    public void setRecallMode( Integer recallMode )
    {
        this.recallMode = recallMode;
    }

    public Integer getDirectMode()
    {
        return directMode;
    }

    public void setDirectMode( Integer directMode )
    {
        this.directMode = directMode;
    }
    
    

}
