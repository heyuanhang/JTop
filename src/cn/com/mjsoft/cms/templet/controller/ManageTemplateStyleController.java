package cn.com.mjsoft.cms.templet.controller;

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
import cn.com.mjsoft.cms.templet.service.TempletService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/templet" )
public class ManageTemplateStyleController
{
    private TempletService templetService = TempletService.getInstance();

    @RequestMapping( value = "/addTs.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加模板风格", token = true )
    public ModelAndView addTs( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        templetService.addTemplateStyle( params );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/templet/CreateTemplateStyle.jsp", paramMap );

    }

    @RequestMapping( value = "/editTs.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑模板风格", token = true )
    public ModelAndView editTs( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        templetService.editTemplateStyle( params );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );
        paramMap.put( "anId", params.get( "anId" ) );

        return ServletUtil.redirect( "/core/templet/EditTemplateStyle.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/changeTs.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "切换模板风格状态", token = true )
    public String changeTs( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long id = StringUtil.getLongValue( ( String ) params.get( "tsId" ), -1 );

        String flag = ( String ) params.get( "flag" );

        templetService.changeTemplateStyle( id, flag );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/deleteTs.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除模板风格状态", token = true )
    public String deleteTs( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long id = StringUtil.getLongValue( ( String ) params.get( "ids" ), -1 );

        templetService.deleteTemplateStyle( id );

        return "success";

    }

}
