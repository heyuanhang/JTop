package cn.com.mjsoft.cms.templet.html;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.templet.bean.TemplateContentBean;
import cn.com.mjsoft.cms.templet.service.TempletService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

public class SystemSingleTempletContentTag extends TagSupport
{

    private static final long serialVersionUID = -2318752068153484281L;

    private static final String FILE_SEP = File.separator;

    private static Logger log = Logger
        .getLogger( SystemSingleTempletContentTag.class );

    private String entryPath = "";

    // TODO 暂只有一个站点
    private String sitePath = "site" + FILE_SEP + "default" + FILE_SEP;

    private TempletService templetService = TempletService.getInstance();

    // 约定为aa|bb|cc,分隔符由服务器决定
    private String entry;

    // 当show为true时,将在页面直接显示这些内容,否则作为变量传递
    private String show;

    // // 文本行数
    // private Integer contentLine;

    public int doStartTag() throws JspException
    {
        String rootPath = ServletUtil.getSiteFilePath( pageContext
            .getServletConfig() )
            + sitePath;

        String endStr = "";
        try
        {
            endStr = new String( entry.getBytes( "iso-8859-1" ), "GBK" );

        }
        catch ( UnsupportedEncodingException e )
        {
            log.error( "参数中包含不支持的字符" );
            e.printStackTrace();
            throw new JspException( "参数中包含不支持的字符" );

        }

        TemplateContentBean contentBean = templetService
            .retrieveSingleTempletFileContent( "总站", endStr, rootPath );

        if( StringUtil.isStringNotNull( show ) && "true".equals( show.trim() ) )
        {

            try
            {
                this.pageContext.getOut().write( contentBean.getContent() );
            }
            catch ( IOException e )
            {

                throw new JspException( e );
            }
        }
        else
        {
            this.pageContext.setAttribute( "template", contentBean );
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "template", PageContext.PAGE_SCOPE );
        entry = "";

        return EVAL_PAGE;
    }

    public String getEntry()
    {
        return entry;
    }

    public void setEntry( String entry )
    {
        this.entry = entry;
    }

    public String getShow()
    {
        return show;
    }

    public void setShow( String show )
    {
        this.show = show;
    }

}
