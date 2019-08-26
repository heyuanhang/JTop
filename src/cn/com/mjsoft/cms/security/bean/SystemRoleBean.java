package cn.com.mjsoft.cms.security.bean;

import cn.com.mjsoft.cms.organization.bean.SystemOrganizationBean;
import cn.com.mjsoft.cms.organization.service.OrgService;

public class SystemRoleBean
{
    private static OrgService orgService = OrgService.getInstance();

    private Long roleId = Long.valueOf( -1 );

    private Long orgId = Long.valueOf( -1 );

    private String roleName;

    private Integer useState;

    private String roleDesc;

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

    public static OrgService getOrgService()
    {
        return orgService;
    }

    public static void setOrgService( OrgService orgService )
    {
        SystemRoleBean.orgService = orgService;
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

    // 以下为业务方法
    public String getUseStateInfo()
    {

        if( this.useState != null )
        {
            if( this.useState.shortValue() == 0 )
            {
                return "停用";
            }

            if( this.useState.shortValue() == 1 )
            {
                return "启用";
            }
        }

        return "";
    }

    public String getOrgName()
    {
        SystemOrganizationBean orgBean = orgService
            .retrieveSingleOrganizationBeanById( this.orgId );

        if( orgBean != null )
        {
            return orgBean.getOrgName();
        }

        return null;
    }

}
