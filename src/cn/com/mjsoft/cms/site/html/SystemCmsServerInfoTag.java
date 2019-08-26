package cn.com.mjsoft.cms.site.html;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.site.bean.CmsServerBean;

public class SystemCmsServerInfoTag extends TagSupport
{
    private static final long serialVersionUID = -2683378596359224012L;

    private Logger log = Logger.getLogger( this.getClass() );

    public String domain;

    public int doStartTag() throws JspException
    {
        CmsServerBean cmsServerBean = JtRuntime.cmsServer;

        this.pageContext.setAttribute( "Cms", cmsServerBean );

        return EVAL_BODY_INCLUDE;
    }

}
