package cn.com.mjsoft.cms.templet.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/templet" )
public class CreateFileOrFolderController
{

    private static ResourcesService resService = ResourcesService.getInstance();

    @RequestMapping( value = "/createSiteFile.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "创建站点文件", token = true )
    public ModelAndView createSiteFile( HttpServletRequest request, HttpServletResponse response )
    {

        Map params = ServletUtil.getRequestInfo( request );

        String entry = SystemSafeCharUtil.decodeFromWeb( ( String ) params.get( "entry" ) );

        String name = ( String ) params.get( "name" );

        String action = ( String ) params.get( "type" );

        resService.createFileOrFolder( name, entry, action );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );
        paramMap.put( "parentFolder", ( String ) params.get( "entry" ) );

        return ServletUtil.redirect( "/core/resources/CreateFileOrFolder.jsp", paramMap );

    }

}
