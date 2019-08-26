package cn.com.mjsoft.cms.workflow.bean;

public class WorkflowActorBean
{
    private Long flowId = Long.valueOf( -1 );

    private Long auditManId;

    private Long censorStep;

    private Integer type;

    public Long getCensorStep()
    {
        return censorStep;
    }

    public void setCensorStep( Long censorStep )
    {
        this.censorStep = censorStep;
    }

    public Long getFlowId()
    {
        return flowId;
    }

    public void setFlowId( Long flowId )
    {
        this.flowId = flowId;
    }

    public Long getAuditManId()
    {
        return auditManId;
    }

    public void setAuditManId( Long auditManId )
    {
        this.auditManId = auditManId;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType( Integer type )
    {
        this.type = type;
    }

}
