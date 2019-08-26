package cn.com.mjsoft.cms.weixin.controller;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.mjsoft.cms.weixin.service.WeixinService;
import cn.com.mjsoft.cms.weixin.util.SignUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@Controller
@RequestMapping( "/wx" )
public class WeixinCallbackController
{
    private static Logger log = Logger.getLogger( WeixinCallbackController.class );

    private static WeixinService wxService = WeixinService.getInstance();

    @RequestMapping( value = "/wxcallback.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public void wxcallback( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        request.setCharacterEncoding( "UTF-8" );

        String target = StringUtil.replaceString( request.getRequestURL().toString(),
            "wx/wxcallback.do", "", false, false );

        String apiToken = ( String ) WeixinService.API_TOKEN.get( target );

        try
        {

            // 微信加密签名
            String signature = ( String ) params.get( "signature" );
            // 时间戳
            String timestamp = ( String ) params.get( "timestamp" );
            // 随机数
            String nonce = ( String ) params.get( "nonce" );
            // 随机字符串
            String echostr = ( String ) params.get( "echostr" );

            log.info( "wxp:" + params.toString() );

            if( SignUtil.checkSignature( apiToken, signature, timestamp, nonce ) )
            {

                response.setCharacterEncoding( "UTF-8" );

                PrintWriter out = response.getWriter();

                if( "POST".equals( request.getMethod() ) )
                {
                    // String agent = request.getHeader( "User-Agent" );

                    wxService.processClientRequest( out, request );
                }
                else if( "GET".equals( request.getMethod() ) )
                {
                    out.print( echostr );
                }

                out.close();
                out = null;

            }
            else
            {
                log.error( "无效访问:" + target + " : " + apiToken );

                WeixinService.clearWaCache();

                wxService.initSitGroupWXApiToken();
            }

        }
        catch ( Exception e )
        {
            log.error( e );
        }

    }
}
