package cn.com.mjsoft.cms.security.html;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.organization.bean.SystemOrganizationBean;
import cn.com.mjsoft.cms.organization.service.OrgService;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemRoleListTag extends TagSupport
{
    private static final long serialVersionUID = 3893742716365182026L;

    private SecurityService securityService = SecurityService.getInstance();

    private static OrgService orgService = OrgService.getInstance();

    private String userId = "-1";

    private String orgId = "-1";

    private String manage = "false";

    private String pn = "";

    private String size = "";

    public int doStartTag() throws JspException
    {
        Long userIdVar = Long.valueOf( StringUtil.getLongValue( userId, -1 ) );
        List resourceServicList = null;

        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        if( session.isManager() )
        {
            if( userIdVar.longValue() > 0 )
            {
                resourceServicList = securityService
                    .retrieveSystemRoleBeanByUserId( userIdVar );
            }
            else
            {
                // 根据登陆管理员所属的可管理orgId获取对应的授权角色

                String code = null;

                if( !"-1".equals( orgId ) )
                {
                    SystemOrganizationBean org = orgService
                        .retrieveSingleOrganizationBeanById( Long
                            .valueOf( StringUtil.getLongValue( orgId, -1 ) ) );

                    if( org != null )
                    {
                        code = org.getLinearOrderFlag();
                    }
                }
                else
                {
                    code = ( String ) session.getAuth().getOrgCode();
                }

                if( "true".equals( manage ) )
                {

                    int pageNum = StringUtil.getIntValue( pn, 1 );

                    int pageSize = StringUtil.getIntValue( size, 10 );

                    Page pageInfo = null;

                    List temp = null;

                    if( "-1".equals( orgId ) || "".equals( orgId ) )
                    {
                        // 取所有当前用户可见机构的角色

                        temp = securityService.retrieveAllSystemRoleBean(
                            Constant.SECURITY.RES_IS_ALL_USE_STATE,
                            ( String ) session.getAuth().getOrgCode() );

                        pageInfo = new Page( pageSize, temp.size(), pageNum );

                        resourceServicList = securityService
                            .retrieveAllSystemRoleBean( ( String ) session
                                .getAuth().getOrgCode(), ( Long
                                .valueOf( pageInfo.getFirstResult() ) ),
                                Integer.valueOf( pageSize ) );
                    }
                    else
                    {
                        // 只获取org直接的角色
                        temp = securityService
                            .retrieveSystemRoleBeanByOrgId( Long
                                .valueOf( StringUtil.getLongValue( orgId, -1 ) ) );

                        pageInfo = new Page( pageSize, temp.size(), pageNum );

                        resourceServicList = securityService
                            .retrieveSystemRoleBeanByOrgId(
                                Long.valueOf( StringUtil.getLongValue( orgId,
                                    -1 ) ), Long.valueOf( pageInfo
                                    .getFirstResult() ), Integer
                                    .valueOf( pageSize ) );
                    }

                    this.pageContext.setAttribute(
                        "___system_dispose_page_object___", pageInfo );

                }
                else
                {
                    resourceServicList = securityService
                        .retrieveAllSystemRoleBean(
                            Constant.SECURITY.RES_IS_ALL_USE_STATE, code );
                }

            }

        }

        pageContext.setAttribute( "systemRoleList", resourceServicList );

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "systemRoleList" );
        return EVAL_PAGE;
    }

    public void setUserId( String userId )
    {
        this.userId = userId;
    }

    public void setOrgId( String orgId )
    {
        this.orgId = orgId;
    }

    public void setManage( String manage )
    {
        this.manage = manage;
    }

    public void setPn( String pn )
    {
        this.pn = pn;
    }

    public void setSize( String size )
    {
        this.size = size;
    }

}
