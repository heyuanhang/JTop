package cn.com.mjsoft.cms.advert.html;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.advert.bean.AdvertContentBean;
import cn.com.mjsoft.cms.advert.service.AdvertService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;

public class ClientAdvertTag extends TagSupport
{
    private static final long serialVersionUID = 8846376440720411955L;

    private static Logger log = Logger.getLogger( ClientAdvertTag.class );

    private static final String JS_GATE = "<span id=\"_____jtop__cms__trevda_pos_${param.posFlag}_____\"></span>\n<script type=\"text/javascript\" src=\"<cms:BasePath/>core/javascript/loadTrevda.js?posFlag=${param.posFlag}&domain=<cms:BasePath/>&siteUrl=@SiteUrl&notHost=@NotHost\"></script>";

    private static AdvertService advertService = AdvertService.getInstance();

    private String posFlag = "";

    private String adFlag = "";

    public int doStartTag() throws JspException
    {

        if( adFlag.length() > 0 )
        {

            AdvertContentBean adBean = advertService
                .retrieveSingleAdvertContentBeanByAdvertFlag( adFlag );

            try
            {
                this.pageContext.getOut().write(
                    adBean != null ? adBean.getAdvertCode() : "" );

            }
            catch ( Exception e )
            {
                log.error( e );

                e.printStackTrace();
            }
        }
        else
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

            String jsGate = StringUtil.replaceString( JS_GATE,
                "<cms:BasePath/>", site.getHostMainUrl(), false, false );
            
            jsGate = StringUtil.replaceString( jsGate, "${param.posFlag}",
                posFlag, false, false );

            jsGate = StringUtil.replaceString( jsGate, "<cms:BasePath/>", site
                .getHostMainUrl(), false, false );

            jsGate = StringUtil.replaceString( jsGate, "@SiteUrl", site
                .getSiteUrl(), false, false );

            jsGate = StringUtil.replaceString( jsGate, "@NotHost", site
                .isNotHost()
                + "", false, false );

            try
            {
                this.pageContext.getOut().write( jsGate );

            }
            catch ( Exception e )
            {
                log.error( e );

                e.printStackTrace();
            }

        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        return EVAL_PAGE;
    }

    public void setAdFlag( String adFlag )
    {
        this.adFlag = adFlag;
    }

    public void setPosFlag( String posFlag )
    {
        this.posFlag = posFlag;
    }

}
