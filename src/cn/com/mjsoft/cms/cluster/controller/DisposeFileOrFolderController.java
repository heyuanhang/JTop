package cn.com.mjsoft.cms.cluster.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.mjsoft.cms.common.service.CMSRedisDB;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@Controller
@RequestMapping( "/cluster" )
public class DisposeFileOrFolderController
{
    private static Logger log = Logger.getLogger( DisposeFileOrFolderController.class );

    @RequestMapping( value = "/deleteFile.do", method = { RequestMethod.POST } )
    public void deleteFile( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String innerAccessJtopSysFlag = ( String ) params.get( "innerAccessJtopSysFlag" );

        if( CMSRedisDB.existMapKey( "AuthorizationHandler.innerAccessCheckMap",
            innerAccessJtopSysFlag ) )
        {
            if( ( String ) params.get( "fp" ) != null )
            {
                String[] fullPaths = StringUtil.split( ( String ) params.get( "fp" ), "," );

                String root = SystemConfiguration.getInstance().getSystemConfig()
                    .getSystemRealPath();

                for ( String fp : fullPaths )
                {
                    if( StringUtil.isStringNotNull( fp ) )
                    {
                        FileUtil
                            .delFile( root + StringUtil.replaceString( fp, "/", File.separator ) );
                    }
                }
            }
        }

        // 无论是否正常的accFlag，都要执行remove
        CMSRedisDB.delMapVal( "AuthorizationHandler.innerAccessCheckMap", innerAccessJtopSysFlag );

    }

    @RequestMapping( value = "/deleteFolder.do", method = { RequestMethod.POST } )
    public void deleteFolder( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String innerAccessJtopSysFlag = ( String ) params.get( "innerAccessJtopSysFlag" );

        if( CMSRedisDB.existMapKey( "AuthorizationHandler.innerAccessCheckMap",
            innerAccessJtopSysFlag ) )
        {
            String fullPath = ( String ) params.get( "fp" );

            String root = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

            // 强制只允许删除站点下的目录
            FileUtil.delFolder( root + StringUtil.replaceString( fullPath, "/", File.separator ) );

        }

        // 无论是否正常的accFlag，都要执行remove
        CMSRedisDB.delMapVal( "AuthorizationHandler.innerAccessCheckMap", innerAccessJtopSysFlag );

    }

    @RequestMapping( value = "/moveFolder.do", method = { RequestMethod.POST } )
    public void moveFolder( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String innerAccessJtopSysFlag = ( String ) params.get( "innerAccessJtopSysFlag" );

        if( CMSRedisDB.existMapKey( "AuthorizationHandler.innerAccessCheckMap",
            innerAccessJtopSysFlag ) )
        {
            String oldPath = ( String ) params.get( "ofp" );

            String newPath = ( String ) params.get( "nfp" );

            String root = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

            // 强制只允许删除站点下的目录
            FileUtil.moveFolder( root + StringUtil.replaceString( oldPath, "/", File.separator ),
                root + StringUtil.replaceString( newPath, "/", File.separator ) );

        }

        // 无论是否正常的accFlag，都要执行remove
        CMSRedisDB.delMapVal( "AuthorizationHandler.innerAccessCheckMap", innerAccessJtopSysFlag );

    }

    @RequestMapping( value = "/renameFile.do", method = { RequestMethod.POST } )
    public void renameFile( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String innerAccessJtopSysFlag = ( String ) params.get( "innerAccessJtopSysFlag" );

        if( CMSRedisDB.existMapKey( "AuthorizationHandler.innerAccessCheckMap",
            innerAccessJtopSysFlag ) )
        {
            String oldPath = ( String ) params.get( "ofp" );

            String newPath = ( String ) params.get( "nfp" );

            String root = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

            // 强制只允许删除站点下的目录
            File oldFile = new File( root + StringUtil.replaceString( oldPath, "/", File.separator ) );

            oldFile.renameTo( new File( root
                + StringUtil.replaceString( newPath, "/", File.separator ) ) );

        }

        // 无论是否正常的accFlag，都要执行remove
        CMSRedisDB.delMapVal( "AuthorizationHandler.innerAccessCheckMap", innerAccessJtopSysFlag );

    }

    @RequestMapping( value = "/newFolder.do", method = { RequestMethod.POST } )
    public void newFolder( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String innerAccessJtopSysFlag = ( String ) params.get( "innerAccessJtopSysFlag" );

        if( CMSRedisDB.existMapKey( "AuthorizationHandler.innerAccessCheckMap",
            innerAccessJtopSysFlag ) )
        {
            String path = ( String ) params.get( "fp" );

            String root = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

            FileUtil.newFolder( root + StringUtil.replaceString( path, "/", File.separator ) );

        }

        // 无论是否正常的accFlag，都要执行remove
        CMSRedisDB.delMapVal( "AuthorizationHandler.innerAccessCheckMap", innerAccessJtopSysFlag );

    }
}
