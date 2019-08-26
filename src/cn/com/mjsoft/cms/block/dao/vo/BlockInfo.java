package cn.com.mjsoft.cms.block.dao.vo;

import java.util.Date;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "block_info", id = "blockId", idMode = EntitySqlBridge.DB_IDENTITY )
public class BlockInfo
{

    private Long blockId = Long.valueOf( -1 );
    private Long parentId = Long.valueOf( -1 );
    private Long siteId = Long.valueOf( -1 );
    private String blockName;
    private String flag;
    private Integer type;
    private String templateUrl;
    private String creator;
    private String blockDesc;
    private String staticUrl;
    private Long jobId;
    private Integer periodType;
    private Integer period;
    private Date lastPubTime;

    public Long getBlockId()
    {
        return this.blockId;
    }

    public void setBlockId( Long blockId )
    {
        this.blockId = blockId;
    }

    public String getBlockName()
    {
        return this.blockName;
    }

    public void setBlockName( String blockName )
    {
        this.blockName = blockName;
    }

    public String getFlag()
    {
        return this.flag;
    }

    public void setFlag( String flag )
    {
        this.flag = flag;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType( Integer type )
    {
        this.type = type;
    }

    public String getTemplateUrl()
    {
        return this.templateUrl;
    }

    public void setTemplateUrl( String templateUrl )
    {
        this.templateUrl = templateUrl;
    }

    public String getBlockDesc()
    {
        return blockDesc;
    }

    public void setBlockDesc( String blockDesc )
    {
        this.blockDesc = blockDesc;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public String getStaticUrl()
    {
        return staticUrl;
    }

    public void setStaticUrl( String staticUrl )
    {
        this.staticUrl = staticUrl;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId( Long parentId )
    {
        this.parentId = parentId;
    }

    public Date getLastPubTime()
    {
        return lastPubTime;
    }

    public void setLastPubTime( Date lastPubTime )
    {
        this.lastPubTime = lastPubTime;
    }

    public Integer getPeriod()
    {
        return period;
    }

    public void setPeriod( Integer period )
    {
        this.period = period;
    }

    public Integer getPeriodType()
    {
        return periodType;
    }

    public void setPeriodType( Integer periodType )
    {
        this.periodType = periodType;
    }

    public Long getJobId()
    {
        return jobId;
    }

    public void setJobId( Long jobId )
    {
        this.jobId = jobId;
    }
}
