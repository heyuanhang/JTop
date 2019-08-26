package cn.com.mjsoft.cms.workflow.dao.vo;

import java.sql.Timestamp;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "workflow", id = "flowId", idMode = EntitySqlBridge.DB_IDENTITY )
public class Workflow
{
    private Long flowId = Long.valueOf( -1 );
    private String flowName;
    private String flowDesc;
    private Integer step = Integer.valueOf( 0 );
    private String creator;
    private Integer bypassFlag;
    private Integer conjunctFlag;
    private Timestamp systemHandleTime;
    private Long siteId;
    private Long updateDT;

    // 字段

    public Workflow()
    {
    }

    // Property accessors
    public Long getFlowId()
    {
        return this.flowId;
    }

    public void setFlowId( Long flowId )
    {
        this.flowId = flowId;
    }

    public String getFlowName()
    {
        return this.flowName;
    }

    public void setFlowName( String flowName )
    {
        this.flowName = flowName;
    }

    public String getFlowDesc()
    {
        return this.flowDesc;
    }

    public void setFlowDesc( String flowDesc )
    {
        this.flowDesc = flowDesc;
    }

    public Integer getStep()
    {
        return this.step;
    }

    public void setStep( Integer step )
    {
        this.step = step;
    }

    public String getCreator()
    {
        return this.creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public Integer getBypassFlag()
    {
        return this.bypassFlag;
    }

    public void setBypassFlag( Integer bypassFlag )
    {
        this.bypassFlag = bypassFlag;
    }

    public Integer getConjunctFlag()
    {
        return this.conjunctFlag;
    }

    public void setConjunctFlag( Integer conjunctFlag )
    {
        this.conjunctFlag = conjunctFlag;
    }

    public Timestamp getSystemHandleTime()
    {
        return systemHandleTime;
    }

    public void setSystemHandleTime( Timestamp systemHandleTime )
    {
        this.systemHandleTime = systemHandleTime;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public Long getUpdateDT()
    {
        return updateDT;
    }

    public void setUpdateDT( Long updateDT )
    {
        this.updateDT = updateDT;
    }

}
