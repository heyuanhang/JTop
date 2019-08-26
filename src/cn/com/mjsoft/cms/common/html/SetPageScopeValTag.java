package cn.com.mjsoft.cms.common.html;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class SetPageScopeValTag extends TagSupport
{

    private static final long serialVersionUID = 4944313583508153141L;

    String id = "";

    String val = "";

    public int doStartTag() throws JspException
    {
        this.pageContext.setAttribute( id, val );

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {

        return EVAL_PAGE;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public void setVal( String val )
    {
        this.val = val;
    }

}
