package cn.com.mjsoft.cms.appbiz.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.com.mjsoft.cms.appbiz.service.AppbizService;
import cn.com.mjsoft.cms.behavior.InitRSABehavior;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.ApplicationContext;
import cn.com.mjsoft.framework.web.IFlow;
import cn.com.mjsoft.framework.web.interceptor.BeforeInterceptor;

public class MVCFlowDecryptAndDisposeParamInterceptor implements
    BeforeInterceptor
{
    private static AppbizService abService = AppbizService.getInstance();

    public Object beforeHandle( ApplicationContext applicationContext,
        IFlow action ) throws Exception
    {
        String command = applicationContext.getFlowContext().getCommand();
        if( StringUtil.isStringNull( command ) )
        {
            return BeforeInterceptor.CONTINUE;
        }
        Map params = applicationContext.getFlowContext()
            .getHttpRequestDecodeSnapshot();

        String sysAESKeyEnc = ( String ) params.get( "sys_app_pak" );

        String sysAppToken = ( String ) params.get( "sys_app_token" );

        String aesKey = AppbizService.getAESKey( sysAESKeyEnc );
        if( StringUtil.isStringNull( aesKey ) )
        {
            return BeforeInterceptor.CONTINUE;
        }
        boolean tokenOk = abService.checkAppFlowToken( sysAppToken );
        if( !tokenOk )
        {
            return "tokenError";
        }
        applicationContext.getFlowContext().resetRequestParamInner(
            disposeEncodeParam( params, aesKey ) );

        params = applicationContext.getFlowContext()
            .getHttpRequestDecodeSnapshot();

        return BeforeInterceptor.CONTINUE;
    }

    private static Map disposeEncodeParam( Map params, String aesKey )
    {
        Map decodeParams = new HashMap();

        Iterator iter = params.entrySet().iterator();

        String key = null;

        String val = null;
        while ( iter.hasNext() )
        {
            Map.Entry entry = ( Map.Entry ) iter.next();

            key = ( String ) entry.getKey();

            val = ( String ) entry.getValue();
            if( key.startsWith( "sys" ) )
            {
                decodeParams.put( key, val );
            }
            else
            {
                decodeParams
                    .put( key, InitRSABehavior.decodeB64AES( val, aesKey ) );
            }
        }
        return decodeParams;
    }
}
