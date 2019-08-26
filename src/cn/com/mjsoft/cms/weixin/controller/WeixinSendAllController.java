package cn.com.mjsoft.cms.weixin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.weixin.bean.WxSendAllInfoBean;
import cn.com.mjsoft.cms.weixin.service.WeixinService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@Controller
@RequestMapping( "/wx" )
@SuppressWarnings( "unchecked" )
public class WeixinSendAllController
{
    private static WeixinService wxService = WeixinService.getInstance();

    @RequestMapping( value = "/addSendAll.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加群发请求", token = true )
    public ModelAndView addSendAll( HttpServletRequest request, HttpServletResponse response )
    {

        WxSendAllInfoBean wsa = ( WxSendAllInfoBean ) ServletUtil.getValueObject( request,
            WxSendAllInfoBean.class );

        wxService.addSendAllInfo( wsa );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/weixin/SendAllWxInfo.jsp", returnParams );

    }

    @ResponseBody
    @RequestMapping( value = "/censor.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "审核群发", token = true )
    public String censor( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long saId = StringUtil.getLongValue( ( String ) params.get( "saId" ), -1 );

        Integer censor = StringUtil.getIntValue( ( String ) params.get( "censor" ), 0 );

        String status = wxService.censorSendAllInfo( saId, censor );

        return status;
    }

    @ResponseBody
    @RequestMapping( value = "/sendAll.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "群发信息", token = true )
    public String sendAll( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String reType = ( String ) params.get( "type" );

        String previewId = ( String ) params.get( "previewId" );

        Long newsId = StringUtil.getLongValue( ( String ) params.get( "resId" ), -1 );

        String flag = wxService.sendWeixinContent( wxService.getSiteWxCode(), newsId, "true",
            previewId, reType, null );

        return flag;
    }

}
