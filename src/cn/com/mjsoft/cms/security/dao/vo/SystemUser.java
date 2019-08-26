package cn.com.mjsoft.cms.security.dao.vo;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ToStringBuilder;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "systemuser", id = "userId", idMode = EntitySqlBridge.DB_IDENTITY )
public class SystemUser
{
    private Long userId = Long.valueOf( -1 );

    private Long manageOrgId = Long.valueOf( -1 );

    private String relateOrgCode;

    private String userName;

    private String userTrueName;

    private String password;

    private String weixinName;

    private String phone;

    private String email;

    private String remark;

    private Integer useState;

    private String creator;

    private Timestamp addTime;

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

    public String toString()
    {
        return new ToStringBuilder( this ).append( "remark", this.remark ).append( "userId",
            this.userId ).append( "userTrueName", this.userTrueName ).append( "password",
            this.password ).append( "addTime", this.addTime ).append( "useState", this.useState )
            .append( "userName", this.userName ).append( "email", this.email ).toString();
    }

}
