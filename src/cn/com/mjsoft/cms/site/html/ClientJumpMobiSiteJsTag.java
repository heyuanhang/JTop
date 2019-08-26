package cn.com.mjsoft.cms.site.html;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;

public class ClientJumpMobiSiteJsTag extends TagSupport
{
    private static final long serialVersionUID = 5465382308402547424L;

    public int doStartTag() throws JspException
    {
        SiteGroupBean site = ( SiteGroupBean ) pageContext.getRequest()
            .getAttribute( Constant.CONTENT.HTML_PUB_CURRENT_SITE );

        if( site == null )
        {

            site = ( SiteGroupBean ) this.pageContext.getRequest()
                .getAttribute( "SiteObj" );

            if( site == null )
            {
                site = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                        .getRequest() );
            }
        }

        String JS_GATE = "";

        if( Constant.COMMON.ON.equals( site.getMobJump() ) )
        {

            JS_GATE = "<script type=\"text/javascript\" src=\""
                + site.getHostMainUrl()
                + "core/javascript/mobic.js?domain="
                + ( ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                    .getEntry( site.getMobSiteId() ) ).getSiteUrl()
                + "\"></script>";

        }
        else
        {
            JS_GATE = "<script type=\"text/javascript\" src=\""
                + site.getHostMainUrl() + "core/javascript/mobic_curr_site.js?domain="
                + site.getSiteUrl() + "&domainMob=" + site.getMobSiteUrl()
                + "&domainPad=" + site.getPadSiteUrl() + "\"></script>";
        }

        try
        {
            this.pageContext.getOut().write( JS_GATE );
        }
        catch ( IOException e )
        {
            throw new JspException( e );
        }

        return EVAL_BODY_INCLUDE;

    }
}
