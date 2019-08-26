package cn.com.mjsoft.cms.member.bean;

import java.util.Collections;
import java.util.List;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.security.Role;
import cn.com.mjsoft.framework.security.headstream.IUser;

public class MemberLoginUser implements IUser
{
 
    private static final long serialVersionUID = 3696971492370505790L;

    private Long userId = Long.valueOf( -1 );

    private String orgCode;

    private Long orgId;

    private String name;

    private String password;

    private Integer status;

    private List roleIdList = Collections.EMPTY_LIST;

    private Role[] ra = null;

    public Object getIdentity()
    {
        return userId;
    }

    public String getUserName()
    {
        return name;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getPassword()
    {
        return this.password;
    }

    public Role[] getRoleArray()
    {
        if( this.roleIdList != null && ra == null )
        {
            ra = ( Role[] ) this.roleIdList.toArray( new Role[] {} );
        }

        return ra;
    }

    public List getRoleList()
    {
        return roleIdList;
    }

    public boolean isEnabled()
    {
        return Constant.COMMON.ON.equals( status );
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

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus( Integer status )
    {
        this.status = status;
    }

    public Role[] getMemberRoleArray()
    {

        return null;
    }

}
