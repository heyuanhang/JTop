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
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@Controller
@RequestMapping( "/site" )
public class CopyCfgFromSiteNodeController
{
    private static SiteGroupService siteService = SiteGroupService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/copySiteCfg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "同步站点配置", token = true )
    public String changeEditorRes( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String mode = ( String ) params.get( "mode" );

        Long siteId = StringUtil.getLongValue( ( String ) params.get( "siteId" ), -1 );

        siteService.copySiteConfig( mode, siteId );

        return "success";

    }

}
