package cn.com.mjsoft.cms.security.html;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class SystemAccSelectTag extends TagSupport
{
    private static final long serialVersionUID = 6322615108923358672L;

    public int doStartTag() throws JspException
    {
        List accInfoList = ( List ) pageContext.getAttribute( "accInfoList" );

        if( accInfoList != null )
        {

        }

        return EVAL_BODY_INCLUDE;
    }

    public int doAfterBody() throws JspException
    {
        return super.doAfterBody();
    }

    public int doEndTag() throws JspException
    {
        // TODO Auto-generated method stub
        return super.doEndTag();
    }

}
