package cn.com.mjsoft.cms.security.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.framework.util.InitClassPathHelper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

/**
 * 提供后台登陆入口，注意! 因安全考虑，只允许使用后台地址而非站群地址跳转
 * 
 * @author mj-soft
 * 
 */
public class SysCoreLoginGateServlet extends HttpServlet
{
    private static final long serialVersionUID = -8815748163023539398L;

    private static final String CS_FILE_NAME = "config" + File.separator + "cs.properties";

    private static final String[] mobileAgents = { "android", "iphone", "phone", "mobile", "wap",
        "netfront", "java", "opera mobi", "opera mini", "ucweb", "windows ce", "symbian", "series",
        "webos", "sony", "blackberry", "dopod", "nokia", "samsung", "palmsource", "xda", "pieplus",
        "meizu", "midp", "cldc", "motorola", "foma", "docomo", "up.browser", "up.link", "blazer",
        "helio", "hosin", "huawei", "novarra", "coolpad", "webos", "techfaith", "palmsource",
        "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem", "wellcom",
        "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
        "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct",
        "320x320", "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan",
        "benq", "bird", "blac", "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric",
        "hipt", "inno", "ipaq", "java", "jigs", "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g",
        "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi", "mot-", "moto", "mwbp", "nec-",
        "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port", "prox", "qwap",
        "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
        "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1",
        "upsi", "vk-v", "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw",
        "xda", "xda-", "Googlebot-Mobile" };

    protected void doPost( HttpServletRequest req, HttpServletResponse res )
        throws ServletException, IOException
    {

    }

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse res )
        throws ServletException, IOException
    {
        String classesPath = null;
        try
        {
            classesPath = InitClassPathHelper.class.getClassLoader().getResource( "/" ).toURI()
                .getPath();
        }
        catch ( URISyntaxException e )
        {
            e.printStackTrace();
        }

        String osName = System.getProperty( "os.name" ).toLowerCase();

        if( osName.indexOf( "win" ) != -1 )
        {
            if( classesPath.startsWith( "/" ) )
            {
                classesPath = StringUtil.subString( classesPath, 1, classesPath.length() );
            }
        }

        classesPath = StringUtil.replaceString( classesPath, "/", File.separator, false, false );

        String root = ServletUtil.getClassPath( InitClassPathHelper.class );

        String rtFile = StringUtil.replaceString( root, "classes", CS_FILE_NAME, false, false );

        /**
         * 读取cs.pro核心配置
         */

        Properties cmsPro = new Properties();

        if( null != rtFile )
        {
            InputStream in = null;
            try
            {

                in = new FileInputStream( rtFile );

                cmsPro.load( in );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
            finally
            {
                if( null != in )
                {
                    try
                    {
                        in.close();
                    }
                    catch ( IOException e )
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        // LOGIN_URL

        String pcLogin = "";

        // String mobLogin = "";

        String lps = ( String ) cmsPro.get( "login_page" );

        String[] lp = lps.split( "," );

        pcLogin = StringUtil.subString( lp[0], 1, lp[0].length() );

        // mobLogin = StringUtil.subString( lp[1], 1, lp[1].length() );

        String cmsGateBase = JtRuntime.cmsServer.getDomainFullPath();

        String url = cmsGateBase + pcLogin;

        // boolean isMobAccess = checkMoblieAccess( req );
        //
        // if( isMobAccess )
        // {
        // url = cmsGateBase + mobLogin;
        // }

        if( JtRuntime.cmsServer.getClientServer() )
        {
            res.sendError( HttpServletResponse.SC_FORBIDDEN );

            return;
        }

        res.sendRedirect( url );

    }

    public boolean checkMoblieAccess( HttpServletRequest request )
    {
        boolean isMoblie = false;

        String ua = request.getHeader( "User-Agent" );

        if( ua != null )
        {
            String mobileAgent = null;

            for ( int i = 0; i < mobileAgents.length; i++ )
            {
                mobileAgent = mobileAgents[i];

                if( ua.toLowerCase().indexOf( mobileAgent ) >= 0 )
                {
                    isMoblie = true;
                    break;
                }
            }
        }
        return isMoblie;
    }

}
