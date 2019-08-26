package cn.com.mjsoft.cms.workflow.dao.vo;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "workflow_step_action", id = "actionId", idMode = EntitySqlBridge.DB_IDENTITY )
public class WorkflowStepAction
{
    private Long actionId = Long.valueOf( -1 );

    private Long flowId;

    private String actDesc;

    private Long fromStepId;

    private String fromStepNodeName;

    private Long toStepId;

    private String toStepNodeName;

    private String passActionName;

    private Integer orgBossMode = Constant.COMMON.OFF;

    private Integer conjunctOrgFlag = Constant.COMMON.OFF;

    private Integer conjunctRoleFlag = Constant.COMMON.OFF;

    private Integer conjunctManFlag = Constant.COMMON.OFF;

    private Integer needRequest = Constant.COMMON.OFF;

    private Integer directMode = Constant.COMMON.OFF;

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

    public String getFromStepNodeName()
    {
        return fromStepNodeName;
    }

    public void setFromStepNodeName( String fromStepNodeName )
    {
        this.fromStepNodeName = fromStepNodeName;
    }

    public String getToStepNodeName()
    {
        return toStepNodeName;
    }

    public void setToStepNodeName( String toStepNodeName )
    {
        this.toStepNodeName = toStepNodeName;
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

    public Integer getDirectMode()
    {
        return directMode;
    }

    public void setDirectMode( Integer directMode )
    {
        this.directMode = directMode;
    }

}
