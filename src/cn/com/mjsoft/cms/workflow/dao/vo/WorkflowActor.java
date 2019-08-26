package cn.com.mjsoft.cms.workflow.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "workflow_actor", id = "", idMode = EntitySqlBridge.NO_KEY_ID )
public class WorkflowActor
{

    private Long flowId = Long.valueOf( -1 );

    private Long auditManId;

    private Long censorStep;

    private Integer type;

    public WorkflowActor()
    {
    }

    public Long getFlowId()
    {
        return this.flowId;
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

    public Long getCensorStep()
    {
        return censorStep;
    }

    public void setCensorStep( Long censorStep )
    {
        this.censorStep = censorStep;
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
