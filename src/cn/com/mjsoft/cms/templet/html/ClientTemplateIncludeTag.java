package cn.com.mjsoft.cms.templet.html;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.framework.cache.http.BufferedResponseWrapper;
import cn.com.mjsoft.framework.cache.http.BufferedResponseWrapper.WrappedOutputStream;

public class ClientTemplateIncludeTag extends TagSupport
{
    private static final long serialVersionUID = 4848790348666774767L;

    private static Logger log = Logger
        .getLogger( ClientTemplateIncludeTag.class );

    private String page = "";

    public int doStartTag() throws JspException
    {
        HttpServletRequest request = ( HttpServletRequest ) this.pageContext
            .getRequest();

        HttpServletResponse response = ( HttpServletResponse ) this.pageContext
            .getResponse();

        HttpServletResponse responseWrapper = null;
        WrappedOutputStream contentOutputStream = null;
        try
        {
            responseWrapper = new BufferedResponseWrapper( response );

            request.getRequestDispatcher( page ).include( request,
                responseWrapper );
            
            // 切记,一定要在获得output之前flushBuffer
            responseWrapper.flushBuffer();
            
            contentOutputStream = ( WrappedOutputStream ) responseWrapper
                .getOutputStream();

            this.pageContext.getOut().write(
                contentOutputStream.getBuffer().toString(
                    ( String ) request
                        .getAttribute( "___jtopcms_tempplate_charset___" ) ) );

        }
        catch ( Exception e )
        {
            log.error( "[ClientTemplateIncludeTag] 模板文件地址失效:" + page );
        }
        finally
        {
            try
            {
                if( contentOutputStream != null )
                {
                    contentOutputStream.close();
                }
            }
            catch ( IOException e )
            {

            }

        }

        return SKIP_BODY;
    }

    public void setPage( String page )
    {
        this.page = page;
    }
}
