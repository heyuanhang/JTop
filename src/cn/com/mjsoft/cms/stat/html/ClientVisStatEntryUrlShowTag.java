package cn.com.mjsoft.cms.stat.html;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;

public class ClientVisStatEntryUrlShowTag extends TagSupport
{
    private static final long serialVersionUID = -2904432354002568309L;

    private static Logger log = Logger
        .getLogger( ClientVisStatEntryUrlShowTag.class );

    private String contentId = "-1";

    private String classId = "-1";

    public int doStartTag() throws JspException
    {
        String statUrl = "";

        // 来自发布逻辑的访问,根据管理站点确定当前站点
        SiteGroupBean siteBean = ( SiteGroupBean ) pageContext.getRequest()
            .getAttribute( Constant.CONTENT.HTML_PUB_CURRENT_SITE );

        if( siteBean == null )
        {
            siteBean = ( SiteGroupBean ) this.pageContext.getRequest()
                .getAttribute( "SiteObj" );

            if( siteBean == null )
            {
                // 根据URL来判断站点
                siteBean = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                        .getRequest() );
            }
        }

        statUrl = "<script type=\"text/javascript\" src=\""
            + siteBean.getHostMainUrl() + "core/javascript/stat.js?siteId="
            + siteBean.getSiteId() + "&siteUrl=" + siteBean.getSiteUrl()
            + "&notHost=" + siteBean.isNotHost() + "&contentId="
            + StringUtil.getLongValue( contentId, -1 ) + "&classId="
            + StringUtil.getLongValue( classId, -1 ) + "\" ></script>";

        try
        {
            this.pageContext.getOut().write( statUrl );
        }
        catch ( IOException e )
        {
            throw new JspException( e );
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {

        return EVAL_PAGE;
    }

    public void setClassId( String classId )
    {
        this.classId = classId;
    }

    public void setContentId( String contentId )
    {
        this.contentId = contentId;
    }
}
