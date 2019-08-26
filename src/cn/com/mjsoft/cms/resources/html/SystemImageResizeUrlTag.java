package cn.com.mjsoft.cms.resources.html;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemImageResizeUrlTag extends TagSupport
{
    private static final long serialVersionUID = -8967088520013106185L;

    private String reUrl = "";

    public int doStartTag() throws JspException
    {
        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        SiteGroupBean site = ( SiteGroupBean ) session.getCurrentLoginSiteInfo();

        String cmsResizeImageUrl = null;
        if( StringUtil.isStringNull( reUrl ) || "no_url".equals( reUrl ) )
        {
            cmsResizeImageUrl = JtRuntime.cmsServer.getDomainFullPath()
                + "/core/style/blue/images/no-image.gif";
        }
        else
        {
            cmsResizeImageUrl = site.getSiteImagePrefixUrl()
                + StringUtil.replaceString( reUrl, "/", "/imgResize", false, false );
        }

        try
        {
            this.pageContext.getOut().write( cmsResizeImageUrl );
        }
        catch ( IOException e )
        {
            throw new JspException( e );
        }

        return EVAL_BODY_INCLUDE;
    }

    public void setReUrl( String reUrl )
    {
        this.reUrl = reUrl;
    }
}
