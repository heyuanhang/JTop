package cn.com.mjsoft.cms.appbiz.flow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.com.mjsoft.cms.appbiz.bean.SystemApiConfigBean;
import cn.com.mjsoft.cms.appbiz.service.AppbizService;
import cn.com.mjsoft.cms.behavior.InitRSABehavior;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.util.ObjectUtility;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.support.WebFlow;

import com.alibaba.fastjson.JSON;

/**
 * APP核心API处理流程
 * @author mjsoft
 *
 */
public abstract class ApiFlowDisposBaseFlow extends WebFlow
{
    private static AppbizService abService = AppbizService.getInstance();

    public String execute() throws Exception
    {
        String command = getFlowContext().getCommand();

        HttpServletRequest request = getServletFlowContext().getRequest();

        String sPath = request.getServletPath();

        if( StringUtil.isStringNull( command ) )
        {
            return "{error:flow-lost}";
        }

        //处理POST和GET请求
        SystemApiConfigBean sac = abService
            .retrieveSingleAppCfgBeanByPath( sPath );

        if( ( "post".equals( sac.getReqMethod() ) )
            && ( "GET".equals( request.getMethod() ) ) )
        {
            return responseAjaxTextMessage( "{error:post-mode}" );
        }

        Map params = getFlowContext().getHttpRequestDecodeSnapshot();

        String aesKey = null;

        boolean mustEnc = false;

        boolean exeuBiz = true;

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
                    return responseAjaxTextMessage( "{error:token-lost}" );
                }
            }

            if( Constant.COMMON.ON.equals( sac.getMustEnc() ) )
            {
                String sysAESKeyEnc = ( String ) params.get( "sys_app_pak" );

                if( StringUtil.isStringNull( sysAESKeyEnc ) )
                {
                    return responseAjaxTextMessage( JSON
                        .toJSONString( "{error:akey-lost}" ) );
                }

                aesKey = AppbizService.getAESKey( sysAESKeyEnc );

                if( StringUtil.isStringNull( aesKey ) )
                {
                    return responseAjaxTextMessage( JSON
                        .toJSONString( "{error:akey-lost}" ) );
                }

                mustEnc = true;

                getFlowContext().resetRequestParamInner(
                    disposeEncodeParam( params, aesKey ) );
            }
            
            if( Constant.COMMON.ON.equals( sac.getMustSecTok() ) )
            {
                String vbClassName = sac.getExtBehaviorClass();

                if( StringUtil.isStringNotNull( vbClassName ) )
                {
                    Class classObj = ObjectUtility
                        .getClassInstance( vbClassName );

                    if( classObj != null )
                    {
                        Object valiedateBehavior = classObj.newInstance();
                        if( ( valiedateBehavior instanceof Behavior ) )
                        {
                            params = getFlowContext()
                                .getHttpRequestDecodeSnapshot();

                            flag = ( String[] ) ( ( Behavior ) valiedateBehavior )
                                .operation( params, new Object[] {
                                    getFlowContext(), getServletFlowContext() } );

                            if( ( flag == null )
                                || ( !"true".equals( flag[0] ) ) )
                            {
                                exeuBiz = false;

                                if( ( flag == null ) || ( flag.length != 2 ) )
                                {
                                    flag = new String[] { "false",
                                        "{error:no-flag}" };
                                }
                            }
                        }
                    }
                }
            }
        }

        String jsonResult = "";

        if( exeuBiz )
        {
            jsonResult = executeBiz();
        }
        else
        {
            jsonResult = flag[1];
        }

        if( mustEnc )
        {
            jsonResult = InitRSABehavior.encodeB64AES( jsonResult, aesKey );
        }

        return responseAjaxTextMessage( jsonResult );
    }

    /**
     * 实际执行的API Flow业务方法
     * @return
     * @throws Exception
     */
    protected abstract String executeBiz() throws Exception;

    /**
     * 处理加密参数，sys开头的系统参数不进行处理
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
                decodeParams
                    .put( key, InitRSABehavior.decodeB64AES( val, aesKey ) );
            }
        }
        return decodeParams;
    }

}
