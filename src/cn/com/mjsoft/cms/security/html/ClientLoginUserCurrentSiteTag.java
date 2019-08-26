package cn.com.mjsoft.cms.security.html;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;

public class ClientLoginUserCurrentSiteTag extends TagSupport
{
    private static final long serialVersionUID = -1264390271876231442L;

    public int doStartTag() throws JspException
    {
        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        if( session == null || session.getAuth() == null )
        {
            return EVAL_BODY_INCLUDE;
        }
        else
        {
            SiteGroupBean site = ( SiteGroupBean ) session
                .getCurrentLoginSiteInfo();
            pageContext.setAttribute( "CurrSite", site );
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "CurrSite" );
        return EVAL_PAGE;
    }

}
