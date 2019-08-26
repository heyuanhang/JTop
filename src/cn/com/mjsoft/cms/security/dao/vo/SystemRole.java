package cn.com.mjsoft.cms.security.dao.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "systemrole", id = "roleId", idMode = EntitySqlBridge.DB_IDENTITY )
public class SystemRole
{

    private Long roleId = Long.valueOf( -1 );

    private Long orgId = Long.valueOf( -1 );

    private String roleName;

    private String roleDesc;

    private Integer useState = Constant.COMMON.ON;

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId( Long roleId )
    {
        this.roleId = roleId;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName( String roleName )
    {
        this.roleName = roleName;
    }

    public Integer getUseState()
    {
        return useState;
    }

    public void setUseState( Integer useState )
    {
        this.useState = useState;
    }

    public String getRoleDesc()
    {
        return roleDesc;
    }

    public void setRoleDesc( String roleDesc )
    {
        this.roleDesc = roleDesc;
    }

    public Long getOrgId()
    {
        return orgId;
    }

    public void setOrgId( Long orgId )
    {
        this.orgId = orgId;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder( this ).append( "roleName", this.roleName ).append( "roleDesc",
            this.roleDesc ).append( "roleId", this.roleId ).append( "useState", this.useState )
            .toString();
    }

}
