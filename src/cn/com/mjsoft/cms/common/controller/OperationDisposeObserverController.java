package cn.com.mjsoft.cms.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.JSONString;
import cn.com.mjsoft.framework.cache.jsr14.ReadWriteLockHashMap;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

/**
 * 业务处理状态观察者,此flow针对AJAX异步请求和服务器内部处理状态查询请求
 * 
 * @author mjsoft
 * 
 */
@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/common" )
public class OperationDisposeObserverController
{
    public static Map statusMap = new ReadWriteLockHashMap();

    /**
     * 这里保留web request入口是因为主要针对ajax入口,而其他service等内部调用可直接使用方法查询处理状态
     */
    @ResponseBody
    @RequestMapping( value = "/queryOperationRTStatus.do", method = { RequestMethod.POST,
        RequestMethod.GET } )
    public Object queryOperationRTStatus( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        String eventKay = ( String ) params.get( "eventKey" );

        String mode = ( String ) params.get( "mode" );

        if( "json".equals( mode ) )
        {
            return getOperationRTStatusJSON( eventKay );
        }
        else if( "remove".equals( mode ) )
        {
            destroyOperation( eventKay );
        }

        Object status = getOperationRTStatus( eventKay );

        return status != null ? status.toString() : "";
    }

    public static void registerOperationRTStatus( String eventKey, Object status )
    {
        statusMap.put( eventKey, status );
    }

    public static Object getOperationRTStatus( String eventKey )
    {
        // log.info( "[OperationDisposeObserverFlow] 请求中......" );
        return statusMap.get( eventKey );
    }

    public static String getOperationRTStatusJSON( String eventKey )
    {
        // log.info( "[OperationDisposeObserverFlow] 请求中......" );
        JSONString jsonStatus = ( JSONString ) statusMap.get( eventKey );

        return jsonStatus == null ? "" : jsonStatus.toJSON();
    }

    public static void destroyOperation( String eventKey )
    {
        statusMap.remove( eventKey );
    }

}
