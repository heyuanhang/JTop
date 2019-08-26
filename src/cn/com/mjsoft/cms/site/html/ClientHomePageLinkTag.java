package cn.com.mjsoft.cms.site.html;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.handler.view.DefaultResultHandler;

public class ClientHomePageLinkTag extends TagSupport
{
    private static final long serialVersionUID = 586948228414543595L;

    private static Logger log = Logger.getLogger( ClientHomePageLinkTag.class );

    private static final String BLANK = "CMS_SYSTEM_HOME_BLANK";

    private static DefaultResultHandler resultHandler = new DefaultResultHandler();

    private String siteId;

    // private String forwardMode = "false";

    public int doStartTag() throws JspException
    {

 

        SiteGroupBean siteGroupBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( Long.valueOf( StringUtil.getLongValue( siteId, -1 ) ) );

        if( siteGroupBean != null )
        {
            log.info( "当前首页URL:" + siteGroupBean.getSiteUrl() );

            try
            {
                this.pageContext.getOut().write( siteGroupBean.getSiteUrl() );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        siteId = null;
        return EVAL_PAGE;
    }

    public String getSiteId()
    {
        return siteId;
    }

    public void setSiteId( String siteId )
    {
        this.siteId = siteId;
    }

}
