package cn.com.mjsoft.cms.workflow.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "workflow_operation", id = "opId", idMode = EntitySqlBridge.DB_IDENTITY )
public class WorkflowOperation
{
    private Long opId = Long.valueOf( -1 );
    private Long contentId = Long.valueOf( -1 );
    private Long flowId;
    private String flowTarget;
    private Long classId;
    private Integer infoType;
    private Integer startCensor;

    private Long currentStep;
    private Integer operStatus;
    private String currentAuditUser;
    private Integer possessStatus;
    private Long wkUpdateDT;

    public Long getContentId()
    {
        return contentId;
    }

    public void setContentId( Long contentId )
    {
        this.contentId = contentId;
    }

    public Long getCurrentStep()
    {
        return currentStep;
    }

    public void setCurrentStep( Long currentStep )
    {
        this.currentStep = currentStep;
    }

    public Long getFlowId()
    {
        return flowId;
    }

    public void setFlowId( Long flowId )
    {
        this.flowId = flowId;
    }

    public Integer getOperStatus()
    {
        return operStatus;
    }

    public void setOperStatus( Integer operStatus )
    {
        this.operStatus = operStatus;
    }

    public String getCurrentAuditUser()
    {
        return currentAuditUser;
    }

    public void setCurrentAuditUser( String currentAuditUser )
    {
        this.currentAuditUser = currentAuditUser;
    }

    public Integer getPossessStatus()
    {
        return possessStatus;
    }

    public void setPossessStatus( Integer possessStatus )
    {
        this.possessStatus = possessStatus;
    }

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Long getOpId()
    {
        return opId;
    }

    public void setOpId( Long opId )
    {
        this.opId = opId;
    }

    public Long getWkUpdateDT()
    {
        return wkUpdateDT;
    }

    public void setWkUpdateDT( Long wkUpdateDT )
    {
        this.wkUpdateDT = wkUpdateDT;
    }

    public Integer getInfoType()
    {
        return infoType;
    }

    public void setInfoType( Integer infoType )
    {
        this.infoType = infoType;
    }

    public String getFlowTarget()
    {
        return flowTarget;
    }

    public void setFlowTarget( String flowTarget )
    {
        this.flowTarget = flowTarget;
    }

    public Integer getStartCensor()
    {
        return startCensor;
    }

    public void setStartCensor( Integer startCensor )
    {
        this.startCensor = startCensor;
    }

}
