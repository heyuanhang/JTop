package cn.com.mjsoft.cms.weixin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.weixin.service.WeixinService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping( "/wx" )
public class GetContentMainInfoAJAXRequestController
{
    private static WeixinService wxService = WeixinService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/getContent.do", method = { RequestMethod.POST, RequestMethod.GET  } )
    @ActionInfo( traceName = "微信获取内容" )
    public Object getContent( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List cidArrayList = StringUtil.changeStringToList( ( String ) params.get( "contentIds" ),
            "," );

        List contentInfoList = wxService.retrieveContentInfoByIds( cidArrayList );

        return contentInfoList;
    }

}
