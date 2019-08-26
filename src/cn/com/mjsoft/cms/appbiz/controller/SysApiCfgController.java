package cn.com.mjsoft.cms.appbiz.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.appbiz.dao.vo.SystemApiConfig;
import cn.com.mjsoft.cms.appbiz.service.AppbizService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/appbiz" )
public class SysApiCfgController
{
    private static AppbizService abService = AppbizService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/searchAndInitApi.do", method = { RequestMethod.POST } )
    public Object searchAndInitApi( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        abService.initSysApiCfgForFramework( "japi" );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/addApiCfg.do", method = { RequestMethod.POST } )
    public Object addApiCfg( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        SystemApiConfig ac = ( SystemApiConfig ) ServletUtil.getValueObjectDecode( request,
            SystemApiConfig.class );

        abService.addSysApiCfg( ac );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/editApiCfg.do", method = { RequestMethod.POST } )
    public Object editApiCfg( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        SystemApiConfig ac = ( SystemApiConfig ) ServletUtil.getValueObjectDecode( request,
            SystemApiConfig.class );

        abService.editSysApiCfg( ac );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/editApiCfgParam.do", method = { RequestMethod.POST } )
    public Object editApiCfgParam( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String apiParams = ( String ) params.get( "apiParams" );

        Long apiId = Long
            .valueOf( StringUtil.getLongValue( ( String ) params.get( "apiId" ), -1L ) );

        abService.editSysApiCfgParam( apiParams, apiId );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteApiCfg.do", method = { RequestMethod.POST } )
    public Object deleteApiCfg( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        abService.deleteSysApiCfg( idList );

        return "success";
    }

}
