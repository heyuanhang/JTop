package cn.com.mjsoft.cms.workflow.dao.vo;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "workflow_step_info", id = "stepId", idMode = EntitySqlBridge.DB_IDENTITY )
public class WorkflowStepInfo
{
    private Long stepId = Long.valueOf( -1 );

    private Long flowId;

    private String stepNodeName;

    private Integer conjunctOrgFlag = Constant.COMMON.OFF;

    private Integer conjunctRoleFlag = Constant.COMMON.OFF;

    private Integer conjunctManFlag = Constant.COMMON.OFF;

    private Integer avoidFlag = Constant.COMMON.OFF;

    private Integer mustReq = Constant.COMMON.OFF;

    private String stepDesc;

    private Integer isStart = Constant.COMMON.OFF;

    private Long pubDefActId;

    private Long deleteDefActId;

    private Integer orgMode = Constant.COMMON.OFF;

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

    public String getStepDesc()
    {
        return stepDesc;
    }

    public void setStepDesc( String stepDesc )
    {
        this.stepDesc = stepDesc;
    }

    public Long getFlowId()
    {
        return flowId;
    }

    public void setFlowId( Long flowId )
    {
        this.flowId = flowId;
    }

    public Long getStepId()
    {
        return stepId;
    }

    public void setStepId( Long stepId )
    {
        this.stepId = stepId;
    }

    public String getStepNodeName()
    {
        return stepNodeName;
    }

    public void setStepNodeName( String stepNodeName )
    {
        this.stepNodeName = stepNodeName;
    }

    public Integer getAvoidFlag()
    {
        return avoidFlag;
    }

    public void setAvoidFlag( Integer avoidFlag )
    {
        this.avoidFlag = avoidFlag;
    }

    public Integer getIsStart()
    {
        return isStart;
    }

    public void setIsStart( Integer isStart )
    {
        this.isStart = isStart;
    }

    public Integer getMustReq()
    {
        return mustReq;
    }

    public void setMustReq( Integer mustReq )
    {
        this.mustReq = mustReq;
    }

    public Long getDeleteDefActId()
    {
        return deleteDefActId;
    }

    public void setDeleteDefActId( Long deleteDefActId )
    {
        this.deleteDefActId = deleteDefActId;
    }

    public Long getPubDefActId()
    {
        return pubDefActId;
    }

    public void setPubDefActId( Long pubDefActId )
    {
        this.pubDefActId = pubDefActId;
    }

    public Integer getOrgMode()
    {
        return orgMode;
    }

    public void setOrgMode( Integer orgMode )
    {
        this.orgMode = orgMode;
    }

}
