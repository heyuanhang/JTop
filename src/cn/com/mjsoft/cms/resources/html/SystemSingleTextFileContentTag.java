package cn.com.mjsoft.cms.resources.html;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.resources.bean.TextBean;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;

public class SystemSingleTextFileContentTag extends TagSupport
{
    private static final long serialVersionUID = 4490779403861806989L;

    private static Logger log = Logger.getLogger( SystemSingleTextFileContentTag.class );

    private static ResourcesService resService = ResourcesService.getInstance();

    // 约定为aa*bb*cc,分隔符由服务器决定
    private String entry;

    // 当show为true时,将在页面直接显示这些内容,否则作为变量传递
    // private String show;

    // // 文本行数
    // private Integer contentLine;

    public int doStartTag() throws JspException
    {
        String endStr = "";

        endStr = SystemSafeCharUtil.decodeFromWeb( entry );

        endStr = StringUtil.replaceString( endStr, "%20", " ", false, false );

        TextBean textBean = resService.retrieveSingleSiteTextFileContentInfo( endStr, true );

        textBean.setContent( StringUtil.replaceString( textBean.getContent(), "</textarea>",
            "&lt;/textarea&gt;", true, false ) );

        this.pageContext.setAttribute( "Text", textBean );

        // if( StringUtil.isStringNotNull( show ) && "true".equals( show.trim()
        // ) )
        // {
        //
        // try
        // {
        // this.pageContext.getOut().write( contentBean.getContent() );
        // }
        // catch ( IOException e )
        // {
        //
        // throw new JspException( e );
        // }
        // }
        // else
        // {
        // this.pageContext.setAttribute( "template", contentBean );
        // }

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

    // public String getShow()
    // {
    // return show;
    // }
    //
    // public void setShow( String show )
    // {
    // this.show = show;
    // }

}
