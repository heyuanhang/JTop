package cn.com.mjsoft.cms.resources.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/resources" )
public class BackupSystemController
{

    private static ResourcesService resService = ResourcesService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/backupSystem.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "系统备份", token = true )
    public String backupSystem( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        String currPw = ( String ) auth.getCredence();

        String pwVar = ( String ) params.get( "pw" );

        if( ( !auth.getOrgCode().equals( "001" ) ) || ( !currPw.equals( pwVar.trim() ) ) )
        {
            return "pwerror";
        }

        resService.zipAllSiteResource( true );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/restore.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "恢复备份", token = true )
    public String restore( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        String currPw = ( String ) auth.getCredence();

        String pwVar = ( String ) params.get( "pw" );

        if( ( !auth.getOrgCode().equals( "001" ) ) || ( !currPw.equals( pwVar.trim() ) ) )
        {
            return "pwerror";
        }
        String targetBak = ( String ) params.get( "target" );

        return resService.restoreSysBackup( targetBak );
    }

    @RequestMapping( value = "/downloadBak.do", method = { RequestMethod.POST } )
    public String downloadBak( HttpServletRequest request, HttpServletResponse response )
        throws UnsupportedEncodingException
    {
        Map params = ServletUtil.getRequestInfo( request );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( !auth.getOrgCode().equals( "001" ) )
        {
            return "orgerror";
        }

        String targetBak = ( String ) params.get( "target" );

        String base = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String testBakRoot = base + "__sys_bak__" + File.separator + targetBak;

        File bak = new File( testBakRoot );
        if( ( !bak.exists() ) || ( bak.isDirectory() ) )
        {
            return null;
        }

        response.reset();
        response.setContentType( "application/octet-stream" );
        response.setHeader( "Content-Disposition", "attachment; filename="
            + new String( bak.getName().getBytes( "GBK" ), "iso-8859-1" ) );
        response.addHeader( "Content-Length", bak.length() + "" );

        OutputStream os = null;
        FileInputStream fis = null;
        try
        {
            os = response.getOutputStream();

            fis = new FileInputStream( bak );
            byte[] b = new byte[1024];
            while ( fis.read( b ) != -1 )
            {
                os.write( b );
            }
            os.flush();
        }
        catch ( Exception localException )
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
            catch ( Exception localException1 )
            {
            }
        }

        return null;
    }

}
