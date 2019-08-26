package cn.com.mjsoft.cms.site.html;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.site.bean.CmsServerBean;

public class SystemCmsServerDomainTag extends TagSupport
{
    private static final long serialVersionUID = -8894982059417881019L;

    private Logger log = Logger.getLogger( this.getClass() );

    public String domain;

    public int doStartTag() throws JspException
    {
        CmsServerBean cmsServerBean = JtRuntime.cmsServer;

        try
        {
            this.pageContext.getOut().write( cmsServerBean.getDomainFullPath() );
        }
        catch ( IOException e )
        {
            throw new JspException( e );
        }
        return EVAL_BODY_INCLUDE;
    }
}
