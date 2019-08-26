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
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.dao.SiteGroupDao;
import cn.com.mjsoft.cms.site.dao.vo.SiteDispenseServer;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/site" )
public class ManageServerConfigController
{
    private static SiteGroupService siteService = SiteGroupService.getInstance();

    @RequestMapping( value = "/addServerConfig.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加发布服务器配置", token = true )
    public ModelAndView addServerConfig( HttpServletRequest request, HttpServletResponse response )
    {
        SiteDispenseServer server = ( SiteDispenseServer ) ServletUtil.getValueObject( request,
            SiteDispenseServer.class );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        server.setSiteId( site.getSiteId() );

        siteService.addServerConfig( server );

        SiteGroupDao.clearPSCache();

        Map returnParams = new HashMap();
        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/deploy/AddServerConfig.jsp", returnParams );

    }

    @RequestMapping( value = "/editServerConfig.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑发布服务器配置", token = true )
    public ModelAndView editServerConfig( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteDispenseServer server = ( SiteDispenseServer ) ServletUtil.getValueObject( request,
            SiteDispenseServer.class );

        siteService.editServerConfig( server );

        SiteGroupDao.clearPSCache();

        Map returnParams = new HashMap();
        returnParams.put( "fromFlow", Boolean.TRUE );
        returnParams.put( "id", params.get( "serverId" ) );

        return ServletUtil.redirect( "/core/deploy/EditServerConfig.jsp", returnParams );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteServerConfig.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除发布服务器配置", token = true )
    public String deleteServerConfig( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long id = StringUtil.getLongValue( ( String ) params.get( "id" ), -1 );

        siteService.deleteSiteServerConfig( id );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/checkServer.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "检查存储服务器连通" )
    public String checkServer( HttpServletRequest request, HttpServletResponse response )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        siteService.checkSiteFTPServerStatus( site.getSiteId() );

        SiteGroupDao.clearPSCache();

        return "success";
    }
}
