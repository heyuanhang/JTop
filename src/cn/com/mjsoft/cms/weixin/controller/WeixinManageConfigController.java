package cn.com.mjsoft.cms.weixin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.weixin.bean.WxAccount;
import cn.com.mjsoft.cms.weixin.service.WeixinService;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@Controller
@RequestMapping( "/wx" )
public class WeixinManageConfigController
{

    private static WeixinService wxService = WeixinService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/configWx.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "微信号配置", token = true )
    public String configWx( HttpServletRequest request, HttpServletResponse response )
    {
        WxAccount wa = ( WxAccount ) ServletUtil.getValueObjectDecode( request, WxAccount.class );

        return wxService.configWeixin( wa );
    }

}
