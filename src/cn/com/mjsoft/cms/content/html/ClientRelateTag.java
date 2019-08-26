package cn.com.mjsoft.cms.content.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModelException;

/**
 * 相关内容获取
 * 
 * @author mjsoft
 * 
 */
public class ClientRelateTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -2886317389988178099L;

    private String type = "";

    private String size = "8";

    protected String returnPutValueName()
    {
        return "ReId";
    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected String returnValueRange()
    {
        return "selfRange";
    }

    protected Object returnSingleObject()
    {
        return null;
    }

    protected List returnObjectList()
    {
        // 只有存在内容的情况下才获取相关内容
        Map info = null;
        
        // freemarker
        Object obj = pageContext
            .getAttribute( "Info" );

        if( obj instanceof SimpleHash )
        {
            try
            {
                info = ( ( SimpleHash ) obj ).toMap();
            }
            catch ( TemplateModelException e )
            {
                info = new HashMap();
                e.printStackTrace();
            }
        }
        else
        {
            info = ( Map ) pageContext.getAttribute( "Info" );
        }

        if( info != null )
        {
            List strList = new ArrayList();

            String rids = "";

            if( type.startsWith( "c:" ))
            {                  
                String clId = StringUtil.replaceString( type, "c:", "" );
                
                rids = ( String ) ContentService.getInstance().getRelateContentQueryTag( info.get( "contentId" ).toString(), clId, null );
                
            }
            else if( "s".equals( type ) )
            {
                rids = ( String ) info.get( "relateSurvey" );
            }

            int sizeVar = StringUtil.getIntValue( size, 8 );

            if( StringUtil.isStringNotNull( rids ) )
            {
                String[] temp = StringUtil.split( rids, "\\*" );

                if( temp != null )
                {
                    for ( int i = 0; i < temp.length; i++ )
                    {
                        if( i == sizeVar )
                        {
                            // 达到size
                            break;
                        }

                        strList.add( temp[i] );
                    }
                }
            }

            return strList;

        }

        return new ArrayList();
    }

    protected void initTag()
    {

    }

    public void setSize( String size )
    {
        this.size = size;
    }

    public void setType( String type )
    {
        this.type = type;
    }
}
