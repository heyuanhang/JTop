package cn.com.mjsoft.cms.member.html;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.framework.security.crypto.util.EncodeOne;

public class MemberUtilTag extends TagSupport
{
    private static final long serialVersionUID = 2743300770165412187L;

    private static final Map sMap = new HashMap();

    static
    {
        sMap.put( "A", "sdfaf!Q^Y5tHji" );
        sMap.put( "B", "a2345$(ity67%^" );
        sMap.put( "C", "khj)#^sadaA612" );
        sMap.put( "D", "t5&^&%dsSEW2f!" );
        sMap.put( "E", "hgsa7ayR$%S&7w" );
        sMap.put( "F", ")*ef$%dfw7s&ef" );
    }

    private String target = "";

    private String mode = "push";

    private String use = "C";

    public int doStartTag() throws JspException
    {
        String end = null;

        try
        {
            if( "p".equals( mode ) )
            {
                byte[] encryptResult = EncodeOne.encryptAES( target,
                    ( String ) sMap.get( use ) );

                end = EncodeOne.encode16( encryptResult ).toLowerCase();

            }
            else if( "d".equals( mode ) )
            {
                byte[] decryptResult = EncodeOne.decryptAES( EncodeOne
                    .parseHexStr2Byte( target ), ( String ) sMap.get( use ) );

                end = new String( decryptResult );
            }
        }
        catch ( Exception e )
        {

        }

        this.pageContext.setAttribute( "SysDecVal", end );

        return EVAL_BODY_INCLUDE;
    }

    public void setTarget( String target )
    {
        this.target = target;
    }

    public void setMode( String mode )
    {
        this.mode = mode;
    }

    public void setUse( String use )
    {
        this.use = use;
    }

    public static String getSalt( String key )
    {
        return ( String ) sMap.get( key );
    }

}
