package cn.com.mjsoft.cms.member.html;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.framework.security.GenericAuth;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;

public class MemberLoginTag extends TagSupport
{

    private static final long serialVersionUID = -719386054017354714L;

    public int doStartTag() throws JspException
    {
        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        if( session == null || session.getAuth() == null || session.isManager()
            || session.getMember() == null )
        {
            pageContext.setAttribute( "Auth", new GenericAuth() );
        }
        else
        {
            pageContext.setAttribute( "Auth", session.getAuth() );
        }

        return EVAL_BODY_INCLUDE;

    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "Auth" );
        return EVAL_PAGE;
    }
}
