package cn.com.mjsoft.cms.content.html;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.framework.util.StringUtil;

public class ClientNextOrPrevContentTag extends TagSupport
{
    private static final long serialVersionUID = -6341654648852789522L;

    private static ContentService contentService = ContentService.getInstance();

    private String cid = "";

    private String flag = "n";

    public int doStartTag() throws JspException
    {
        Map info = contentService
            .retrieveSingleUserDefineContent( Long.valueOf( StringUtil
                .getLongValue( cid, -1 ) ), Integer.valueOf( 1 ) );

        if( info != null )
        {
            Double orderIdFlag = ( Double ) info.get( "orderIdFlag" );

            Long classId = ( Long ) info.get( "classId" );

            Long modelId = ( Long ) info.get( "modelId" );

            Map ncInfo = contentService.retrieveSingleNextOrPrevContentById(
                orderIdFlag, classId, modelId, flag );

            pageContext.setAttribute( "NPInfo", ncInfo );
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "NPInfo" );
        return EVAL_PAGE;
    }

    public void setFlag( String flag )
    {
        this.flag = flag;
    }

    public void setCid( String cid )
    {
        this.cid = cid;
    }

}
