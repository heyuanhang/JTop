package cn.com.mjsoft.cms.security.html;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.security.SecuritrConstant;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemMemberResourceListTag extends TagSupport
{

    private static final long serialVersionUID = 1L;

    private static Logger log = Logger
        .getLogger( SystemMemberResourceListTag.class );

    // private static final String ALL_PROTECT_TYPE = "all";

    private static SecurityService resourceService = SecurityService
        .getInstance();

    private String userId = "";

    private String roleId = "";

    private String mainMode = "false";

    private String linear = "";

    private String parentLinearMode = "false";// 获取父资源的直系孩子

    // 默认只取粗粒度资源
    private String protectType = SecuritrConstant.GEN_PROTECT_TYPE.toString();

    public int doStartTag() throws JspException
    {
        List resourceServicList = null;

        // role优先
        if( StringUtil.isStringNotNull( roleId ) )
        {
            if( "all".equals( roleId ) )
            {
                Integer pType = SecuritrConstant.GEN_PROTECT_TYPE;

                if( SecuritrConstant.SPEC_PROTECT_TYPE.toString().equals(
                    protectType.trim() ) )
                {
                    pType = SecuritrConstant.SPEC_PROTECT_TYPE;
                }
                else if( SecuritrConstant.ALL_PROTECT_TYPE.toString().equals(
                    protectType.trim() ) )
                {
                    pType = SecuritrConstant.ALL_PROTECT_TYPE;
                }

                log
                    .info( "[SystemMemberSecurityResourceListTag] 取所有资源,protectType:"
                        + pType );

                if( !"".equals( linear ) )
                {

                    if( "true".equals( parentLinearMode ) )
                    {
                        resourceServicList = resourceService
                            .retrieveMemberSecurityResourceBeanByParentLinear( linear );
                    }
                    else
                    {

                        if( "000".equals( linear ) )
                        {
                            linear = "";
                        }

                        resourceServicList = resourceService
                            .retrieveMemberSecurityResourceBeanByLinear( linear );
                    }
                }
                else if( "true".equals( mainMode ) )
                {
                    resourceServicList = resourceService
                        .retrieveMemberSecurityResourceBeanMainMode();
                }
                else
                {
                    resourceServicList = resourceService
                        .retrieveMemberSecurityResourceBeanByProtectType( pType );
                }
            }
            else
            {
                log
                    .info( "[SystemMemberSecurityResourceListTag] 根据角色取资源,roleId:"
                        + roleId + ", protectType:" + protectType );

                resourceServicList = resourceService
                    .retrieveMemberRoleHaveHisResourceBeanByRoleId( Long
                        .valueOf( StringUtil.getLongValue( roleId, -1 ) ) );
            }
        }

        pageContext.setAttribute( "resourceList", resourceServicList );

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "resourceList" );
        return EVAL_PAGE;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId( String userId )
    {
        this.userId = userId;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId( String roleId )
    {
        this.roleId = roleId;
    }

    public String getProtectType()
    {
        return protectType;
    }

    public void setProtectType( String protectType )
    {
        this.protectType = protectType;
    }

    public void setMainMode( String mainMode )
    {
        this.mainMode = mainMode;
    }

    public void setParentLinearMode( String parentLinearMode )
    {
        this.parentLinearMode = parentLinearMode;
    }

    public void setLinear( String linear )
    {
        this.linear = linear;
    }
}
