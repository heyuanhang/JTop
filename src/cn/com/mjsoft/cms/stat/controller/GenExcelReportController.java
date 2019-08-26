package cn.com.mjsoft.cms.stat.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/stat" )
public class GenExcelReportController
{
    private static StatService statService = StatService.getInstance();

    @RequestMapping( value = "/report.do", method = { RequestMethod.POST, RequestMethod.GET} )
    public void report( HttpServletRequest request, HttpServletResponse response )
        throws UnsupportedEncodingException
    {
        Map params = ServletUtil.getRequestInfo( request );

        String st = ( String ) params.get( "sd" );

        String et = ( String ) params.get( "ed" );

        String flag = ( String ) params.get( "flag" );

        String ids = ( String ) params.get( "ids" );

        String eFlag = ( String ) params.get( "eFlag" );

        String fileFullPath = statService.report( flag, st, et, ids, eFlag );

        File file = new File( fileFullPath );

        // 文件类型响应报文头
        response.reset();// 强制清除response内部信息
        response.setContentType( "application/octet-stream" );
        response.setHeader( "Content-Disposition", "attachment; filename="
            + new String( file.getName().getBytes( JtRuntime.cmsServer.getEncoding() ),
                "iso-8859-1" ) );
        response.addHeader( "Content-Length", "" + file.length() );

        // 获取输出流，将文件写出到客户端
        OutputStream os = null;
        FileInputStream fis = null;
        try
        {
            os = response.getOutputStream();
            // 将下载文件读入
            fis = new FileInputStream( file );
            byte[] b = new byte[1024];
            while ( fis.read( b ) != -1 )
            {
                os.write( b );
            }
            os.flush();
        }
        catch ( Exception e )
        {
        }
        finally
        {
            try
            {
                if( fis != null )
                {
                    fis.close();
                }

                if( os != null )
                {
                    os.close();
                }
            }
            catch ( Exception e )
            {
            }
        }

    }

}
