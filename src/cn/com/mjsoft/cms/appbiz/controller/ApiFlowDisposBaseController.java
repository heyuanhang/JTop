package cn.com.mjsoft.cms.appbiz.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import cn.com.mjsoft.cms.appbiz.bean.ApiReq;
import cn.com.mjsoft.cms.appbiz.bean.SystemApiConfigBean;
import cn.com.mjsoft.cms.appbiz.service.AppbizService;
import cn.com.mjsoft.cms.behavior.InitRSABehavior;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.util.ObjectUtility;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

/**
 * APP核心API处理流程
 * 
 * @author mjsoft
 * 
 */
@SuppressWarnings( "unchecked" )
public abstract class ApiFlowDisposBaseController
{
    private static AppbizService abService = AppbizService.getInstance();

    private static final String AES_KEY = "___sys_api_aes_key___";

    private static final String MUST_ENC = "___sys_api_must_enc___";

    /**
     * 根据配置对API请求的数据进行解密等处理
     * 
     * @param request
     * @return ApiReq
     * @throws Exception
     */

    protected ApiReq apiReqCheck( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        String sPath = request.getServletPath();

        String code = null;

        /*
         * if( StringUtil.isStringNull( command ) ) { return
         * "{error:flow-lost}"; }
         */

        // 处理POST和GET请求
        SystemApiConfigBean sac = abService.retrieveSingleAppCfgBeanByPath( sPath );

        if( ( "post".equals( sac.getReqMethod() ) ) && ( "GET".equals( request.getMethod() ) ) )
        {
            code = "{error:post-mode}";
        }

        Map params = ServletUtil.getRequestDecodeInfo( request );

        String aesKey = null;

        String[] flag = ( String[] ) null;

        /**
         * 对于需要token 加密传输 扩展业务的处理
         */
        if( sac.getApiId().longValue() > 0L )
        {
            if( Constant.COMMON.ON.equals( sac.getMustTok() ) )
            {
                String sysAppToken = ( String ) params.get( "sys_app_token" );

                boolean tokenOk = abService.checkAppFlowToken( sysAppToken );

                if( !tokenOk )
                {
                    code = "{error:token-lost}";
                }
            }

            if( Constant.COMMON.ON.equals( sac.getMustEnc() ) )
            {
                String sysAESKeyEnc = ( String ) params.get( "sys_app_pak" );

                if( StringUtil.isStringNull( sysAESKeyEnc ) )
                {
                    code = "{error:akey-lost}";
                }

                aesKey = AppbizService.getAESKey( sysAESKeyEnc );

                if( StringUtil.isStringNull( aesKey ) )
                {
                    code = "{error:akey-lost}";
                }

                params = disposeEncodeParam( params, aesKey );

                params.put( MUST_ENC, Boolean.TRUE );

                params.put( AES_KEY, aesKey );

            }

            if( Constant.COMMON.ON.equals( sac.getMustSecTok() ) )
            {
                String vbClassName = sac.getExtBehaviorClass();

                if( StringUtil.isStringNotNull( vbClassName ) )
                {
                    Class classObj = ObjectUtility.getClassInstance( vbClassName );

                    if( classObj != null )
                    {
                        Object valiedateBehavior = classObj.newInstance();
                        if( ( valiedateBehavior instanceof Behavior ) )
                        {

                            flag = ( String[] ) ( ( Behavior ) valiedateBehavior ).operation(
                                params, new Object[] { request, sac } );

                            code = ( String ) ( ( flag != null ) ? flag : "{error:no-flag}" );

                        }
                    }
                }
            }
        }
        return new ApiReq( request, response, code, params );

    }

    /**
     * 根据配置决定结果是否需要加密
     * 
     * @param params 已处理过的接口参数
     * @param jsonResult 返回结果
     * @return
     * @throws Exception
     */
    protected Object apiResult( ApiReq ar, Object result ) throws Exception
    {
        Map params = ar.getParam();

        String aesKey = ( String ) params.get( AES_KEY );

        Boolean mustEnc = ( Boolean ) params.get( MUST_ENC );

        if( mustEnc != null && mustEnc )
        {
            return InitRSABehavior.encodeB64AES( JSON.toJSONString( result ), aesKey );
        }

        return ServletUtil.responseJSON( ar.getResponse(), JSON.toJSONString( result ) );
    }

    /**
     * 处理加密参数，sys开头的系统参数不进行处理
     * 
     * @param params
     * @param aesKey
     * @return
     */
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
                decodeParams.put( key, InitRSABehavior.decodeB64AES( val, aesKey ) );
            }
        }
        return decodeParams;
    }

}
