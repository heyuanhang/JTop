package cn.com.mjsoft.cms.security.bean;

import java.util.Collections;
import java.util.List;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.security.Role;
import cn.com.mjsoft.framework.security.headstream.IUser;

public class SystemLoginUser implements IUser
{
    private Long userId = Long.valueOf( -1 );

    private String orgCode;

    private Long orgId;

    private String name;

    private String password;

    private Integer useStatus = Constant.COMMON.OFF;

    private List roleIdList = Collections.EMPTY_LIST;

    public Object getIdentity()
    {
        return userId;
    }

    public String getUserName()
    {
        return name;
    }

    public String getPassword()
    {
        return password;
    }

    public Role[] getRoleArray()
    {
        if( this.roleIdList != null )
        {
            return ( Role[] ) this.roleIdList.toArray( new Role[] {} );
        }

        return new Role[] {};
    }

    public List getRoleList()
    {
        return roleIdList;
    }

    public boolean isEnabled()
    {
        return ( useStatus.intValue() == 1 ) ? true : false;
    }

    public boolean isExpired()
    {
        return false;
    }

    public boolean isLocked()
    {

        return false;
    }

    public boolean isPasswordExpired()
    {

        return false;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public void setUserId( Long userId )
    {
        this.userId = userId;
    }

    public void setRoleIdList( List roleIdList )
    {
        this.roleIdList = roleIdList;
    }

    public Object getOrgIdentity()
    {
        return this.orgId;
    }

    public Object getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode( String orgCode )
    {
        this.orgCode = orgCode;
    }

    public Object getOrgId()
    {
        return orgId;
    }

    public void setOrgId( Long orgId )
    {
        this.orgId = orgId;
    }

    public Integer getUseStatus()
    {
        return useStatus;
    }

    public void setUseStatus( Integer useStatus )
    {
        this.useStatus = useStatus;
    }

    public Role[] getMemberRoleArray()
    {

        return null;
    }

}
