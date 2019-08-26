package cn.com.mjsoft.cms.stat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.stat.service.StatService;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/stat" )
public class InitSiteContentTraceController
{
    private static StatService statService = StatService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/initCache.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "清空工作量统计缓存", token = true )
    public String initCache( HttpServletRequest request, HttpServletResponse response )
    {
        StatService.cache.clearAllEntry();

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/initSCT.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "初始化工作量统计", token = true )
    public String initSCT( HttpServletRequest request, HttpServletResponse response )
    {
        statService.initSiteContentStatTrace();

        return "success";
    }

}
