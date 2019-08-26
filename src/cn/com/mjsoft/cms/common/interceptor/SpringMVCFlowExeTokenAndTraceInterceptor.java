package cn.com.mjsoft.cms.common.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.appbiz.service.AppbizService;
import cn.com.mjsoft.cms.behavior.InitRSABehavior;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.schedule.service.ScheduleService;
import cn.com.mjsoft.cms.stat.bean.FlowTraceBean;
import cn.com.mjsoft.cms.stat.behavior.DangerAcceccTraceBehavior;
import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.IPSeeker;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

/**
 * Spring MVC 辅助业务拦截器
 * 
 * @author MJSoft
 * 
 */
public class SpringMVCFlowExeTokenAndTraceInterceptor implements HandlerInterceptor
{
    private static Logger log = Logger.getLogger( SpringMVCFlowExeTokenAndTraceInterceptor.class );

    private static Behavior accessBehavior = new DangerAcceccTraceBehavior();

    private static AppbizService abService = AppbizService.getInstance();

    private static final String AES_KEY = "___sys_api_aes_key___";

    private static final String MUST_ENC = "___sys_api_must_enc___";

    public void postHandle( HttpServletRequest request, HttpServletResponse response,
        Object handler, ModelAndView modelAndView ) throws Exception
    {

        String reqServletPath = request.getServletPath();

        HandlerMethod hm = ( HandlerMethod ) handler;

        ActionInfo ai = hm.getMethodAnnotation( ActionInfo.class );

        if( ai != null )
        {
            String flowCom = reqServletPath.substring( reqServletPath.lastIndexOf( "/" ) + 1,
                reqServletPath.lastIndexOf( "." ) );

            String flowName = ai.traceName();

            Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

            String flowActUserName = null;

            if( auth != null )
            {
                flowActUserName = ( String ) auth.getApellation();
            }
            else
            {
                // 2017：对于未登录管理员的请求，不再记录访问
                return;
            }

            if( StringUtil.isStringNull( flowActUserName ) )
            {
                flowActUserName = "未知";
            }

            Map params = ServletUtil.getRequestInfo( request );

            Iterator iter = params.entrySet().iterator();

            Entry en = null;

            StringBuilder buf = new StringBuilder();

            while ( iter.hasNext() )
            {
                en = ( Entry ) iter.next();

                if( en.getValue() != null && en.getValue().toString().length() < 300 )
                {
                    buf.append( en.getKey() + "=" + en.getValue() + ",  " );
                }

            }

            String ip = IPSeeker.getIp( request );

            FlowTraceBean ftb = new FlowTraceBean( flowActUserName, flowCom, flowName, buf
                .toString(), ip );

            StatService.traceTemp.add( ftb );

            if( StatService.traceTemp.size() >= 25 )
            {
                ScheduleService.getInstance().startCollectFlowTraceJob();

            }

        }
    }

    public boolean preHandle( HttpServletRequest request, HttpServletResponse response,
        Object handler ) throws Exception
    {

        HandlerMethod hm = ( HandlerMethod ) handler;

        ActionInfo ai = hm.getMethodAnnotation( ActionInfo.class );

        Set tokenSet = null;

        if( ai != null )
        {
            request.setCharacterEncoding( ai.encoding() );

            if( ai.token() )
            {
                HttpSession httpSession = request.getSession();

                if( httpSession == null )
                {
                    responseCliect( request, "token丢失，无法确认访问来源！" );

                    response.setStatus( HttpServletResponse.SC_BAD_REQUEST );

                    return false;
                }

                tokenSet = ( Set ) httpSession.getAttribute( TagConstants.TOKEN );

                if( tokenSet == null )
                {
                    responseCliect( request, "token丢失，无法确认访问来源！" );

                    response.setStatus( HttpServletResponse.SC_BAD_REQUEST );

                    return false;
                }

                String tokenVal = request.getParameter( TagConstants.TOKEN_KEY );

                if( StringUtil.isStringNull( tokenVal ) || !tokenSet.contains( tokenVal ) )
                {
                    // 删除token
                    tokenSet.remove( tokenVal );
                    // tokenSet.clear();

                    responseCliect( request, "token丢失，无法确认访问来源！" );

                    response.setStatus( HttpServletResponse.SC_BAD_REQUEST );

                    return false;
                }

                // 删除token
                tokenSet.remove( tokenVal );

                httpSession.setAttribute( TagConstants.TOKEN, tokenSet );

            }

            /**
             * 以下为api接口业务
             */

            Map params = ServletUtil.getRequestDecodeInfo( request );

            String aesKey = null;

            String code = null;

            String[] flag = ( String[] ) null;

            // api接口token
            if( ai.appToken() )
            {
                String sysAppToken = ( String ) params.get( "sys_app_token" );

                boolean tokenOk = abService.checkAppFlowToken( sysAppToken );

                if( !tokenOk )
                {
                    code = "{error:token-lost}";
                }

            }

            // api接口加密
            if( ai.appEnc() )
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

            // api接口权限token扩展
            if( ai.appToken() )
            {
                // String vbClassName = sac.getExtBehaviorClass();
                //
                // if( StringUtil.isStringNotNull( vbClassName ) )
                // {
                // Class classObj = ObjectUtility.getClassInstance( vbClassName
                // );
                //
                // if( classObj != null )
                // {
                // Object valiedateBehavior = classObj.newInstance();
                // if( ( valiedateBehavior instanceof Behavior ) )
                // {
                //
                // flag = ( String[] ) ( ( Behavior ) valiedateBehavior
                // ).operation(
                // params, new Object[] { request, sac } );
                //
                //                            code = ( String ) ( ( flag != null ) ? flag : "{error:no-flag}" );
                //
                //                        }
                //                    }
                //                }
            }

        }

        return true;
    }

    public void afterCompletion( HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex ) throws Exception
    {

    }

    private static void responseCliect( HttpServletRequest request, String target )
    {
        // 打印出错信息
        log.fatal( "IP->" + IPSeeker.getIp( ( HttpServletRequest ) request ) + ",非法动作->" + target
            + ",URL->" + ( ( HttpServletRequest ) request ).getRequestURL().toString() );

        // 记录行为
        Object[] param = new Object[] { IPSeeker.getIp( ( HttpServletRequest ) request ),
            ( ( HttpServletRequest ) request ).getRequestURL().toString(), target,
            request.getQueryString() };

        accessBehavior.operation( null, param );

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
