package cn.com.mjsoft.cms.security.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "member_securityresource", id = "secResId", idMode = EntitySqlBridge.DB_IDENTITY )
public class MemberSecurityResource
{
    private Long secResId = Long.valueOf( -1 );

    private Long parentResId = Long.valueOf( -1 );

    private String icon;

    private String resourceName;

    private String resourceDesc;

    private Integer resourceType;

    private Integer useState;

    private Integer dataProtectType;

    private String creator;

    private String target;

    private String linearOrderFlag;

    private Integer layer;

    private Integer isLeaf;

    private Integer isLastChild;

    private Long dataSecTypeId = Long.valueOf( 1 );

    private String sysFlag;

    private Long siteId;

    public Long getSecResId()
    {
        return this.secResId;
    }

    public void setSecResId( Long secResId )
    {
        this.secResId = secResId;
    }

    public String getResourceName()
    {
        return this.resourceName;
    }

    public void setResourceName( String resourceName )
    {
        this.resourceName = resourceName;
    }

    public String getCreator()
    {
        return this.creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public String getTarget()
    {
        return this.target;
    }

    public void setTarget( String target )
    {
        this.target = target;
    }

    public Long getParentResId()
    {
        return this.parentResId;
    }

    public void setParentResId( Long parentResId )
    {
        this.parentResId = parentResId;
    }

    public String getResourceDesc()
    {
        return resourceDesc;
    }

    public void setResourceDesc( String resourceDesc )
    {
        this.resourceDesc = resourceDesc;
    }

    public String getLinearOrderFlag()
    {
        return linearOrderFlag;
    }

    public void setLinearOrderFlag( String linearOrderFlag )
    {
        this.linearOrderFlag = linearOrderFlag;
    }

    public Long getDataSecTypeId()
    {
        return dataSecTypeId;
    }

    public void setDataSecTypeId( Long dataSecTypeId )
    {
        this.dataSecTypeId = dataSecTypeId;
    }

    public Integer getDataProtectType()
    {
        return dataProtectType;
    }

    public void setDataProtectType( Integer dataProtectType )
    {
        this.dataProtectType = dataProtectType;
    }

    public Integer getIsLastChild()
    {
        return isLastChild;
    }

    public void setIsLastChild( Integer isLastChild )
    {
        this.isLastChild = isLastChild;
    }

    public Integer getLayer()
    {
        return layer;
    }

    public void setLayer( Integer layer )
    {
        this.layer = layer;
    }

    public Integer getResourceType()
    {
        return resourceType;
    }

    public void setResourceType( Integer resourceType )
    {
        this.resourceType = resourceType;
    }

    public Integer getUseState()
    {
        return useState;
    }

    public void setUseState( Integer useState )
    {
        this.useState = useState;
    }

    public Integer getIsLeaf()
    {
        return isLeaf;
    }

    public void setIsLeaf( Integer isLeaf )
    {
        this.isLeaf = isLeaf;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon( String icon )
    {
        this.icon = icon;
    }

    public String getSysFlag()
    {
        return sysFlag;
    }

    public void setSysFlag( String sysFlag )
    {
        this.sysFlag = sysFlag;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

}
