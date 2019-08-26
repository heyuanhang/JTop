package cn.com.mjsoft.cms.workflow.bean;

import java.sql.Timestamp;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.util.StringUtil;

public class WorkflowOperationBean
{
    private Long opId = Long.valueOf( -1 );
    private Long contentId = Long.valueOf( -1 );
    private Long classId;
    private Integer infoType;
    private Long flowId;
    private String flowTarget;
    private Integer startCensor;

    private Long currentStep;
    private Integer operStatus;
    private String currentAuditUser;
    private Integer possessStatus;
    private Long possessUserId = Long.valueOf( -1 );

    // 扩展字段
    private Long modelId = Long.valueOf( -1 );
    private String title;
    private Timestamp addTime;
    private String creator;
    private String stepNodeName;
    private Long wkUpdateDT;

    // 字段

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

    public Timestamp getAddTime()
    {
        return addTime;
    }

    public void setAddTime( Timestamp addTime )
    {
        this.addTime = addTime;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
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

    public Long getModelId()
    {
        return modelId;
    }

    public void setModelId( Long modelId )
    {
        this.modelId = modelId;
    }

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Long getPossessUserId()
    {
        return possessUserId;
    }

    public void setPossessUserId( Long possessUserId )
    {
        this.possessUserId = possessUserId;
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

    public String getStepNodeName()
    {
        if( this.currentStep.intValue() == 0 && StringUtil.isStringNull( stepNodeName ) )
        {
            return "审核起点";
        }

        return stepNodeName;
    }

    public void setStepNodeName( String stepNodeName )
    {
        this.stepNodeName = stepNodeName;
    }

    // 扩展属性
    public String getOperStatusStr()
    {
        String statusStr = "";
        switch ( this.operStatus.intValue() )
        {
            case Constant.WORKFLOW.OPER_IN_FLOW:
                statusStr = "审核中";
                break;
            case Constant.WORKFLOW.OPER_NOT_READ:
                statusStr = "未读";
                break;
            case Constant.WORKFLOW.OPER_EDIT_AGAIN:
                statusStr = "重新编辑";
                break;
            case Constant.WORKFLOW.OPER_REJECT_START:
                statusStr = "退稿";
                break;

            default:
                statusStr = "未知状态";
        }
        return statusStr;
    }
}
