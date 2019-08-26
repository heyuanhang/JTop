package cn.com.mjsoft.cms.content.html;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.framework.util.StringUtil;

public class ClientContentStatusTag extends TagSupport
{
    private static final long serialVersionUID = 1L;

    private Logger log = Logger.getLogger( ClientContentStatusTag.class );

    private static ContentService contentService = ContentService.getInstance();

    private String contentId = "-1";

    public int doStartTag() throws JspException
    {
        Long cId = Long.valueOf( StringUtil.getLongValue( contentId, -1 ) );

        Map csMap = contentService.retrieveSingleContentStatus( cId );

        pageContext.setAttribute( "InfoStatus", csMap );

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "InfoStatus" );
        return EVAL_PAGE;
    }

    public void setContentId( String contentId )
    {
        this.contentId = contentId;
    }

}
