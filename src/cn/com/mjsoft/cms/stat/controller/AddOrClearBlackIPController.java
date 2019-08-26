package cn.com.mjsoft.cms.stat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/stat" )
public class AddOrClearBlackIPController
{
    private static StatService statService = StatService.getInstance();

    @RequestMapping( value = "/addBlackIp.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加IP黑名单", token = true )
    public ModelAndView addBlackIp( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List ipsList = StringUtil.changeStringToList( ( String ) params.get( "ips" ), "," );

        Integer effectHour = Integer.valueOf( StringUtil.getIntValue( ( String ) params
            .get( "effectHour" ), -1 ) );

        Integer forever = Integer.valueOf( StringUtil.getIntValue( ( String ) params
            .get( "forever" ), -1 ) );

        statService.addNewBlackIp( ipsList, effectHour, forever );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/stat/AddBlackIp.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/clearBlackIp.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "解除IP黑名单", token = true )
    public String clearBlackIp( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List ipsList = StringUtil.changeStringToList( ( String ) params.get( "ips" ), "," );

        statService.deleteBlackIp( ipsList );

        return "success";

    }

}
