package cn.com.mjsoft.cms.templet.html;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.templet.bean.TempletFileBean;
import cn.com.mjsoft.cms.templet.service.TempletService;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemDisplayClassTempletTag extends TagSupport
{

    private static final long serialVersionUID = 3097904147537487236L;

    private String type;

    private String site;

    private TempletService templetService = TempletService.getInstance();

    public int doStartTag() throws JspException
    {

        List beanList = templetService.retrieveTempletBeanByTypeAndSite(
            Integer.valueOf( type ), site );

        StringBuffer buf = new StringBuffer();
        for ( int i = 0; i < beanList.size(); i++ )
        {
            TempletFileBean bean = ( TempletFileBean ) beanList.get( i );
            String name = bean.getTempletDisplayName();
            if( StringUtil.isStringNull( name ) )
            {
                name = bean.getTempletFileName();
            }
            String fullPath = bean.getTempletFullPath();

            buf.append( "<option value='" + fullPath );
            buf.append( "'>" );
            buf.append( name );
            buf.append( "</option>" );
        }

        JspWriter writer = this.pageContext.getOut();

        try
        {
            writer.write( buf.toString() );
        }
        catch ( IOException e )
        {
            throw new JspException( e );
        }

        return EVAL_BODY_INCLUDE;
    }

    public String getSite()
    {
        return site;
    }

    public void setSite( String site )
    {
        this.site = site;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

}
