package cn.com.mjsoft.cms.site.html;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;

public class ClientCurrentSiteMediaBaseTag extends TagSupport
{
    private static final long serialVersionUID = -5593924389032484549L;

    public int doStartTag() throws JspException
    {
        String siteFlag = ( String ) ( ( Map ) this.pageContext
            .getAttribute( "Info" ) ).get( "siteFlag" );

        SiteGroupBean siteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( siteFlag );

        try
        {
            this.pageContext.getOut().write( siteBean.getSiteMediaPrefixUrl() );
        }
        catch ( IOException e )
        {
            throw new JspException( e );
        }

        return EVAL_BODY_INCLUDE;

    }
}
