package cn.com.mjsoft.cms.security.dao.vo;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "member_role", id = "roleId", idMode = EntitySqlBridge.DB_IDENTITY )
public class MemberRole
{

    private Long roleId = Long.valueOf( -1 );

    private Long siteId = Long.valueOf( -1 );

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

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

}
