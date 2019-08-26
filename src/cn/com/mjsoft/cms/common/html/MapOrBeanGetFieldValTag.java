package cn.com.mjsoft.cms.common.html;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.framework.util.ObjectUtility;
import cn.com.mjsoft.framework.util.StringUtil;

public class MapOrBeanGetFieldValTag extends TagSupport
{
    private static final long serialVersionUID = 323953285147587252L;

    private String objName = "";

    String fieldName = "";

    String objMode = "false";

    private String choice = "";// 格式为a=1,b=2,c=3

    public int doStartTag() throws JspException
    {
        Object obj = pageContext.getAttribute( objName );

        String val = "";

        Object target = null;

        if( obj != null )
        {
            if( obj instanceof Map )
            {
                target = ( ( Map ) obj ).get( fieldName );

                if( target != null )
                {
                    val = target.toString();
                }
            }
            else
            {
                target = ObjectUtility.getPrivateFieldValue( fieldName, obj );

                if( target != null )
                {
                    val = target.toString();
                }
            }

        }

        if( StringUtil.isStringNotNull( choice ) )
        {
            String[] choiceArray = StringUtil.split( choice, "," );

            for ( int i = 0; i < choiceArray.length; i++ )
            {
                String[] choiceKV = StringUtil.split( choiceArray[i], "=" );

                if( choiceKV[1] != null && choiceKV[1].equals( val ) )
                {
                    val = choiceKV[0];

                    break;
                }
            }
        }

        if( "true".equals( objMode ) )
        {
            this.pageContext.setAttribute( "FV", val );
        }
        else
        {
            try
            {
                this.pageContext.getOut().write( val );
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
        objName = "";

        fieldName = "";

        return EVAL_PAGE;
    }

    public void setFieldName( String fieldName )
    {
        this.fieldName = fieldName;
    }

    public void setObjName( String objName )
    {
        this.objName = objName;
    }

    public void setChoice( String choice )
    {
        this.choice = choice;
    }

    public void setObjMode( String objMode )
    {
        this.objMode = objMode;
    }

}
