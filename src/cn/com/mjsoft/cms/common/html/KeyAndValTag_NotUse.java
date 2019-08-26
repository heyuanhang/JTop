package cn.com.mjsoft.cms.common.html;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.framework.util.StringUtil;

public class KeyAndValTag_NotUse extends TagSupport
{
    private static final long serialVersionUID = 323953285147587252L;

    private String choiceData = "";// 格式为a=1,b=2,c=3

    String key = "";

    String val = "";

    public int doStartTag() throws JspException
    {
        if( StringUtil.isStringNotNull( choiceData ) )
        {
            String[] choice = StringUtil.split( choiceData, "," );

            String end = "";

            for ( int i = 0; i < choice.length; i++ )
            {
                String[] choiceKV = StringUtil.split( choice[i], "=" );

                if(choiceKV[1] != null && choiceKV[1].equals( val ) )
                {
                    end = choiceKV[0];
                    
                    break;
                }
                else if( choiceKV[0] != null && choiceKV[0].equals( key ) )
                {
                    end = choiceKV[1];
                    
                    break;
                }
            }

            try
            {
                this.pageContext.getOut().write( end );
            }
            catch ( IOException e )
            {

                e.printStackTrace();
            }
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {

        return EVAL_PAGE;
    }

    public void setKey( String key )
    {
        this.key = key;
    }

    public void setVal( String val )
    {
        this.val = val;
    }

}
