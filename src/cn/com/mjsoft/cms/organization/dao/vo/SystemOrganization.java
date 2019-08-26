package cn.com.mjsoft.cms.organization.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "system_organization", id = "orgId", idMode = EntitySqlBridge.DB_IDENTITY )
public class SystemOrganization
{

    private Long orgId;

    private Long parentId;
    private String orgFlag;
    private String orgName;
    private String orgDesc;
    private Integer isLeaf;
    private Integer layer;
    private String linearOrderFlag;
    private String directorIds;
    private Long orgBossId = Long.valueOf( -1 );
    private String creator;

    public Long getOrgId()
    {
        return this.orgId;
    }

    public void setOrgId( Long orgId )
    {
        this.orgId = orgId;
    }

    public Long getParentId()
    {
        return this.parentId;
    }

    public void setParentId( Long parentId )
    {
        this.parentId = parentId;
    }

    public String getOrgFlag()
    {
        return this.orgFlag;
    }

    public void setOrgFlag( String orgFlag )
    {
        this.orgFlag = orgFlag;
    }

    public String getOrgName()
    {
        return this.orgName;
    }

    public void setOrgName( String orgName )
    {
        this.orgName = orgName;
    }

    public String getOrgDesc()
    {
        return this.orgDesc;
    }

    public void setOrgDesc( String orgDesc )
    {
        this.orgDesc = orgDesc;
    }

    public Integer getLayer()
    {
        return this.layer;
    }

    public void setLayer( Integer layer )
    {
        this.layer = layer;
    }

    public String getLinearOrderFlag()
    {
        return this.linearOrderFlag;
    }

    public void setLinearOrderFlag( String linearOrderFlag )
    {
        this.linearOrderFlag = linearOrderFlag;
    }

    public String getDirectorIds()
    {
        return this.directorIds;
    }

    public void setDirectorIds( String directorIds )
    {
        this.directorIds = directorIds;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public Integer getIsLeaf()
    {
        return isLeaf;
    }

    public void setIsLeaf( Integer isLeaf )
    {
        this.isLeaf = isLeaf;
    }

    public Long getOrgBossId()
    {
        return orgBossId;
    }

    public void setOrgBossId( Long orgBossId )
    {
        this.orgBossId = orgBossId;
    }
}
