package cn.com.mjsoft.cms.common.html;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.behavior.JtRuntime;

public class BasePathTag extends TagSupport
{

    public String basePath;

    private static final long serialVersionUID = 1L;

    public int doStartTag() throws JspException
    {
        String base = JtRuntime.cmsServer.getDomainFullPath();

        try
        {
            this.pageContext.getOut().write( base );
        }
        catch ( IOException e )
        {
            throw new JspException( e );
        }

        return EVAL_BODY_INCLUDE;
    }

    public String getBasePath()
    {
        return basePath;
    }

    public void setBasePath( String basePath )
    {
        this.basePath = basePath;
    }

}
