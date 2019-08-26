package cn.com.mjsoft.cms.content.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

import com.alibaba.fastjson.JSON;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/content" )
public class ClientRequestContentClickAndCommStatusController
{

    private static ContentService contentService = ContentService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/status.do", method = { RequestMethod.POST } )
    public String status( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long cId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "id" ), -1 ) );

        Map csMap = contentService.retrieveSingleContentStatus( cId );

        return JSON.toJSONString( csMap );

    }

}
