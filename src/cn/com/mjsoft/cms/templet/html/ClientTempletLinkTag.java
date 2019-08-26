package cn.com.mjsoft.cms.templet.html;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.templet.bean.TempletFileBean;
import cn.com.mjsoft.cms.templet.service.TempletService;
import cn.com.mjsoft.framework.util.StringUtil;

public class ClientTempletLinkTag extends TagSupport
{
    private static final long serialVersionUID = 7786669078739969528L;

    private static Logger log = Logger.getLogger( SystemSingleTempletContentTag.class );

    private String id;

    private String siteName;

    private TempletService templetService = TempletService.getInstance();

    public int doStartTag() throws JspException
    {
        long templetId = StringUtil.getLongValue( id, -1 );

        TempletFileBean bean = templetService.retrieveSingleTemplet( Long.valueOf( templetId ) );

        String base = JtRuntime.cmsServer.getDomainFullPath();

        JspWriter writer = this.pageContext.getOut();
        try
        {
            writer.write( base + bean.getTempletLink() );
        }
        catch ( IOException e )
        {
            throw new JspException( e );
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        return super.doEndTag();
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getSiteName()
    {
        return siteName;
    }

    public void setSiteName( String siteName )
    {
        this.siteName = siteName;
    }

}
