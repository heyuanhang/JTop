package cn.com.mjsoft.cms.stat.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.util.StringUtil;

public class ReadSystemLogTextAjaxServelt extends HttpServlet
{
    private static final long serialVersionUID = 1908076789012358989L;

    @SuppressWarnings( "unchecked" )
    protected void doPost( HttpServletRequest req, HttpServletResponse res )
        throws ServletException, IOException
    {

        HttpSession httpSession = req.getSession( false );

        SecuritySession sessionObj = ( SecuritySession ) httpSession
            .getAttribute( "___JTOP__SYSTEM__FRAMEWORK__SECURITY__SESSION___" );

        if( !sessionObj.isManager() )
        {
            // 非登录管理员任何人不得查看日志
            return;
        }

        String basePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        RandomAccessFile raf = null;

        int prevPos = Integer.valueOf( ( String ) req.getParameter( "prevPos" ) ).intValue();

        try
        {
            raf = new RandomAccessFile( basePath + "WEB-INF" + File.separator + "log"
                + File.separator + "cms_core.log", "r" );

            long len = raf.length();

            String lastLine = "";

            long pos = 0;

            long startPos = 0;

            if( len != 0L )
            {
                pos = len - 1;

                startPos = pos;

                while ( pos > 0 && pos > prevPos && prevPos > 0 )
                {
                    pos--;
                    raf.seek( pos );
                    if( raf.readByte() == '\n' )
                    {
                        lastLine = raf.readLine() + "\r\n" + lastLine;
                    }
                }
            }

            Map m = new HashMap();

            m.put( "pos", startPos + "" );
            m.put( "content", new String( lastLine.getBytes( "iso8859_1" ), "UTF-8" ) );

            res.setContentType( "text/plain;charset=UTF-8" );
            PrintWriter out = null;
            try
            {
                out = res.getWriter();
                out.write( StringUtil.changeMapToJSON( m ) );

            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
            finally
            {
                if( out != null )
                {
                    out.close();
                }
            }

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            if( raf != null )
            {
                raf.close();
            }
        }

    }
}
