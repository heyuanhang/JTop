package cn.com.mjsoft.cms.site.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@Controller
@RequestMapping( "/site" )
public class ServerAndCloudResUrlController
{

    private static SiteGroupService siteService = SiteGroupService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/changeEditorRes.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "转换编辑器资源路径", token = true )
    public String changeEditorRes( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String mode = ( String ) params.get( "mode" );

        siteService.changeAllResUrlOrUri( mode );

        return "success";

    }

}
