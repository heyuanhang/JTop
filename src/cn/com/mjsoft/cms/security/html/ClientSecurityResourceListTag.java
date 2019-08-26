package cn.com.mjsoft.cms.security.html;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.RoleSqlHelper;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class ClientSecurityResourceListTag extends TagSupport
{
    private static final long serialVersionUID = -6458280693014916797L;

    private SecurityService resourceService = SecurityService.getInstance();

    private String parentId = "";

    private String type = "";

    public int doStartTag() throws JspException
    {
        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth == null || !auth.isAuthenticated() )
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
                    .retrieveRoleHaveHisResourceBeanByRoleArray( roleSqlHelper
                        .getAllRoleOrQuery( "", "roleId" ), typeVal );
            }
            else
            {
                resourceServicList = resourceService
                    .retrieveRoleHaveHisResourceBeanByRoleArray( roleSqlHelper
                        .getAllRoleOrQuery( "", "roleId" ), Long
                        .valueOf( StringUtil.getLongValue( parentId, -9999 ) ),
                        typeVal );
            }

            pageContext.setAttribute( "securityResourceList",
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
        pageContext.removeAttribute( "securityResourceList" );
        parentId = null;
        return EVAL_PAGE;
    }

}
