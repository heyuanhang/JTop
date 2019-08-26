package cn.com.mjsoft.cms.block.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "block_type", id = "blockTypeId", idMode = EntitySqlBridge.DB_IDENTITY )
public class BlockType
{
    private Long blockTypeId;
    private String blockTypeName;
    private Long jobId;
    private String siteFlag;
    private String creator;

    public Long getBlockTypeId()
    {
        return this.blockTypeId;
    }

    public void setBlockTypeId( Long blockTypeId )
    {
        this.blockTypeId = blockTypeId;
    }

    public String getBlockTypeName()
    {
        return this.blockTypeName;
    }

    public void setBlockTypeName( String blockTypeName )
    {
        this.blockTypeName = blockTypeName;
    }

    public Long getJobId()
    {
        return this.jobId;
    }

    public void setJobId( Long jobId )
    {
        this.jobId = jobId;
    }

    public String getSiteFlag()
    {
        return this.siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

}
