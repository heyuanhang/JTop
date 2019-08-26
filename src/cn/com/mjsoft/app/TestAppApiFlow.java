package cn.com.mjsoft.app;

import java.util.HashMap;
import java.util.Map;

import cn.com.mjsoft.app.apputil.AppStatus;
import cn.com.mjsoft.app.apputil.AppUtil;

@SuppressWarnings( "unchecked" )
public class TestAppApiFlow
{

    private static String CMS_SERVER_URL = "http://192.168.0.101:8080/client";

    // private static String CMS_SERVER_URL = "http://192.168.0.100/jtopcms";
    static
    {
        /**
         * 重要的第一步，向服务器注册
         */
        boolean isOk = AppUtil.initApp( CMS_SERVER_URL );

        if(  isOk )
        {
            System.out.println( "注册成功" );
        }
        else
        {
            System.out.println( "注册失败" );
        }
    }

    public static String testAppAPi()
    {

        Map params = new HashMap();

        String json = null;

        // 站点ID
        String id = "5";

        // id = AppUtil.encodeAES( id );

        /**
         * 获取站点信息
         */
        String apiGetSiteUrl = CMS_SERVER_URL + "/japi/getSite.cmd";

        params.clear();
        params.put( "sys_app_pak", AppUtil.getPAesKey() );
        params.put( "sys_app_token", AppUtil.newToken( CMS_SERVER_URL ) );
        params.put( "siteId", id );

        apiGetSiteUrl = AppUtil.buildAPIUrl( apiGetSiteUrl, params );

        json = AppUtil.doGETMethodRequest( apiGetSiteUrl );
        
        System.out.println( json );

        /**
         * 用户登录演示
         */

        String apiLoginUrl = CMS_SERVER_URL + "/appbiz/appMemberLogin.cmd";

        params.clear();
        params.put( "sys_app_pak", AppUtil.getPAesKey() );
        params.put( "sys_app_token", AppUtil.newToken( CMS_SERVER_URL ) );
        params.put( "siteId", id );

        params.put( "memberName", "2222" );
        params.put( "parampw", "111" );

        apiLoginUrl = AppUtil.buildAPIUrl( apiLoginUrl, params );

        json = AppUtil.doPOSTMethodRequest( apiLoginUrl );

        System.out.println( json );

        /**
         * 获取内容列表
         */
        String classId = "10658";

        String apiGetCList = CMS_SERVER_URL + "/japi/getContentList.cmd";

        // 获取API基本信息
        AppStatus apiStatus = AppUtil.checkApiStatus( CMS_SERVER_URL,
            "/japi/getContentList.cmd" );

        params.clear();

        // 如果需要
        if( apiStatus.getMustTok() )
        {

            params.put( "sys_app_token", AppUtil.newToken( CMS_SERVER_URL ) );
        }

        if( apiStatus.getMustEnc() )
        {
            params.put( "sys_app_pak", AppUtil.getPAesKey() );

            params.put( "siteId", AppUtil.encodeAES( id ) );
            params.put( "classId", AppUtil.encodeAES( classId ) );
        }
        else
        {
            params.put( "siteId", id );
            params.put( "classId", classId );
        }

        params.put( "showAll", "true" );

        if( apiStatus.getPostMode() )
        {
            json = AppUtil.doPOSTMethodRequest( apiGetCList, params );
        }
        else
        {
            json = AppUtil.doGETMethodRequest( AppUtil.buildAPIUrl(
                apiGetCList, params ) );
        }

        if( apiStatus.getMustEnc() )
        {
            json = AppUtil.decodeAES( json );
        }

        return json;
    }

    public static void main( String[] args )
    { }
}
