package cn.com.mjsoft.cms.security.html;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.web.handler.view.DefaultResultHandler;

public class SystemManagerLoginCheckTag extends TagSupport
{
    private static final long serialVersionUID = 1242964184007636406L;

    private static final String LOGIN_PAGE = "/core/SystemManager/login/page.jsp";

    private static DefaultResultHandler resultHandler = new DefaultResultHandler();

    String jump = "false";

    public int doStartTag() throws JspException
    {
        SecuritySession sec = SecuritySessionKeeper.getSecuritySession();

        Auth auth = sec.getAuth();

        boolean notLoginManager = false;

        if( auth == null || !auth.isAuthenticated() || sec == null || !sec.isManager() )
        {
            notLoginManager = true;
        }

        if( notLoginManager )
        {
            this.pageContext.setAttribute( "_____jtopcms__not__login__manager_____", Boolean.TRUE );

            if( "true".equals( jump ) )
            {
                HttpServletRequest request = ( HttpServletRequest ) this.pageContext.getRequest();

                HttpServletResponse response = ( HttpServletResponse ) this.pageContext
                    .getResponse();

                resultHandler.resolveCustomDirectResult( "/" + JtRuntime.cmsServer.getContext()
                    + LOGIN_PAGE, request, response, true, null );

                return SKIP_BODY;
            }
            else
            {
                return SKIP_BODY;
            }
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        Boolean notLogin = ( Boolean ) this.pageContext
            .getAttribute( "_____jtopcms__not__login__manager_____" );

        if( notLogin != null && notLogin.booleanValue() && "true".equals( jump ) )
        {
            return SKIP_PAGE;
        }
        else
        {
            return EVAL_PAGE;
        }
    }

    public void setJump( String jump )
    {
        this.jump = jump;
    }

}
