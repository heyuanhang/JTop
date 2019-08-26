package cn.com.mjsoft.cms.cluster.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@Controller
@RequestMapping( "/cluster" )
public class ClusterServerNodeDisposeileController
{
    @RequestMapping( value = "/recFile.do", method = { RequestMethod.POST } )
    public void recFile( HttpServletRequest request, HttpServletResponse response )
    {
        String root = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        FileUtil.receiveData( request, root );
    }

    @RequestMapping( value = "/unzipSiteFile.do", method = { RequestMethod.POST } )
    public void unzip( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String zipPath = ( String ) params.get( "zipRelatePath" );

        String root = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String osName = System.getProperty( "os.name" ).toLowerCase();
        if( osName.indexOf( "win" ) == -1 )
        {
            zipPath = StringUtil.replaceString( zipPath, "\\", File.separator );
        }

        FileUtil.unZipFile( root + zipPath, root, false );

    }
}
