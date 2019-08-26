package cn.com.mjsoft.cms.security.html;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.RoleSqlHelper;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class ClientMemberSecurityResourceListTag extends TagSupport
{
    private static final long serialVersionUID = -6673933711587834303L;

    private SecurityService resourceService = SecurityService.getInstance();

    private String parentId = "";

    private String type = "";

    public int doStartTag() throws JspException
    {
        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth == null || !auth.isAuthenticated() || SecuritySessionKeeper.getSecuritySession().isManager())
        {
            return EVAL_BODY_INCLUDE;
        }
        else
        {
            RoleSqlHelper roleSqlHelper = auth.getRoleSqlHelper();

            List resourceServicList = null;

            Integer typeVal = Integer.valueOf( StringUtil
                .getIntValue( type, -1 ) );

            if( StringUtil.isStringNull( parentId ) )// 只根据当前角色取对应资源
            {
                resourceServicList = resourceService
                    .retrieveMemberRoleHaveHisResourceBeanByRoleArray( roleSqlHelper
                        .getAllRoleOrQuery( "", "roleId" ), typeVal );
            }
            else
            {
                resourceServicList = resourceService
                    .retrieveMemberRoleHaveHisResourceBeanByRoleArray( roleSqlHelper
                        .getAllRoleOrQuery( "", "roleId" ), Long
                        .valueOf( StringUtil.getLongValue( parentId, -9999 ) ),
                        typeVal );
            }

            pageContext.setAttribute( "MemSecurityResourceList",
                resourceServicList );
        }

        return EVAL_BODY_INCLUDE;
    }

    public void setParentId( String parentId )
    {
        this.parentId = parentId;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "MemSecurityResourceList" );
        parentId = null;
        return EVAL_PAGE;
    }

}
