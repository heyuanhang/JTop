package cn.com.mjsoft.cms.site.html;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;

public class ClientSiteBaseTag extends TagSupport
{
    private static final long serialVersionUID = -9154247983237567845L;

    public int doStartTag() throws JspException
    {
        // SiteGroupBean siteBean = ( SiteGroupBean )
        // InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
        // .getEntry( siteFlag );
        SiteGroupBean currentSiteBean = null;

        if( Boolean.TRUE.equals( pageContext.getRequest().getAttribute(
            Constant.CONTENT.HTML_PUB_ACTION_FLAG ) ) )
        {
            currentSiteBean = ( SiteGroupBean ) pageContext.getRequest()
                .getAttribute( Constant.CONTENT.HTML_PUB_CURRENT_SITE );
        }
        else
        {
            currentSiteBean = ( SiteGroupBean ) this.pageContext.getRequest()
                .getAttribute( "SiteObj" );

            if( currentSiteBean == null )
            {
                currentSiteBean = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                        .getRequest() );
            }
        }

        try
        {
            if( currentSiteBean != null )
            {
                this.pageContext.getOut().write( currentSiteBean.getSiteUrl() );
                this.pageContext.setAttribute( "SiteBase", currentSiteBean
                    .getSiteUrl() );
                //移动站点
                this.pageContext.setAttribute( "SiteBaseMob", currentSiteBean.getMobSiteUrl() );
                this.pageContext.setAttribute( "SiteBasePad", currentSiteBean.getPadSiteUrl() );
            }
        }
        catch ( IOException e )
        {
            throw new JspException( e );
        }

        return EVAL_BODY_INCLUDE;
    }
}
