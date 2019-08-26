package cn.com.mjsoft.cms.security.bean;

import java.sql.Timestamp;

public class SystemUserBean
{
    private Long userId = Long.valueOf( -1 );

    private Long manageOrgId = Long.valueOf( -1 );

    private String relateOrgCode;

    private String userName;

    private String userTrueName;

    private String password;

    private String phone;

    private String email;

    private String remark;

    private Integer useState;

    private String creator;

    private String weixinName;

    private Timestamp addTime;

    private Long roleId = Long.valueOf( -1 );

    // 业务字段
    private String someRoleName = "";

    public Timestamp getAddTime()
    {
        return addTime;
    }

    public void setAddTime( Timestamp addTime )
    {
        this.addTime = addTime;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark( String remark )
    {
        this.remark = remark;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId( Long userId )
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public String getUserTrueName()
    {
        return userTrueName;
    }

    public void setUserTrueName( String userTrueName )
    {
        this.userTrueName = userTrueName;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public String getRelateOrgCode()
    {
        return relateOrgCode;
    }

    public void setRelateOrgCode( String relateOrgCode )
    {
        this.relateOrgCode = relateOrgCode;
    }

    public Integer getUseState()
    {
        return useState;
    }

    public void setUseState( Integer useState )
    {
        this.useState = useState;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId( Long roleId )
    {
        this.roleId = roleId;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone( String phone )
    {
        this.phone = phone;
    }

    public Long getManageOrgId()
    {
        return manageOrgId;
    }

    public void setManageOrgId( Long manageOrgId )
    {
        this.manageOrgId = manageOrgId;
    }

    public String getWeixinName()
    {
        return weixinName;
    }

    public void setWeixinName( String weixinName )
    {
        this.weixinName = weixinName;
    }

    // 以下为业务方法
    public String getUseStateInfo()
    {

        if( this.useState != null )
        {
            if( this.useState.shortValue() == 1 )
            {
                return "启用";
            }

            if( this.useState.shortValue() == 0 )
            {
                return "<font color=red>停用</font>";
            }
        }

        return "";
    }

    public String getSomeRoleName()
    {
        return this.someRoleName;
    }

    public void setSomeRoleName( String rn )
    {
        this.someRoleName = rn;
    }
}
