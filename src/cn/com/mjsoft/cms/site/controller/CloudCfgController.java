package cn.com.mjsoft.cms.site.controller;

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
import cn.com.mjsoft.cms.site.bean.SiteCloudCfgBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/site" )
public class CloudCfgController
{
    private static SiteGroupService siteService = SiteGroupService.getInstance();

    @RequestMapping( value = "/addCloudCfg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加云存储配置", token = true )
    public ModelAndView addCloudCfg( HttpServletRequest request, HttpServletResponse response )
    {

        SiteCloudCfgBean cfg = ServletUtil.getValueObject( request, SiteCloudCfgBean.class );

        siteService.addNewCloudConfig( cfg );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/deploy/AddCloudServer.jsp", returnParams );

    }

    @RequestMapping( value = "/editCloudCfg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑云存储配置", token = true )
    public ModelAndView editCloudCfg( HttpServletRequest request, HttpServletResponse response )
    {

        SiteCloudCfgBean cfg = ServletUtil.getValueObjectDecode( request, SiteCloudCfgBean.class );

        siteService.editCloudConfig( cfg );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );
        returnParams.put( "id", cfg.getCloId() );

        return ServletUtil.redirect( "/core/deploy/EditCloudServer.jsp", returnParams );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteCloudCfg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除云存储配置", token = true )
    public String deleteCloudCfg( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long cloId = StringUtil.getLongValue( ( String ) params.get( "id" ), -1 );

        siteService.deleteCloudConfig( cloId );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/transferSiteFile.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "同步站点文件到集群节点", token = true )
    public String transferSiteFile( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long csId = StringUtil.getLongValue( ( String ) params.get( "id" ), -1 );

        siteService.transferSiteAllFileToClusterNode( csId );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/checkCloudCfg.do", method = { RequestMethod.POST } )
    public String checkCloudCfg( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long cloId = StringUtil.getLongValue( ( String ) params.get( "id" ), -1 );

        String status = siteService.checkCloudConfig( cloId );

        return status;
    }

}
