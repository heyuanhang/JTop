package cn.com.mjsoft.cms.weixin.controller;

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
import cn.com.mjsoft.cms.weixin.bean.WxAccount;
import cn.com.mjsoft.cms.weixin.dao.vo.WxMenu;
import cn.com.mjsoft.cms.weixin.service.WeixinService;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/wx" )
public class WeixinManageMenuController
{
    private static WeixinService wxService = WeixinService.getInstance();

    @RequestMapping( value = "/createMenu.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加菜单", token = true )
    public ModelAndView createMenu( HttpServletRequest request, HttpServletResponse response )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        WxMenu menu = ( WxMenu ) ServletUtil.getValueObject( request, WxMenu.class );

        String code = wxService.createNewWxMenu( menu, wxCode );

        Map paramMap = new HashMap();
        paramMap.put( "code", code );

        return ServletUtil.redirect( "/core/weixin/CreateWeixinMenu.jsp", paramMap );

    }

    @RequestMapping( value = "/editMenu.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑菜单", token = true )
    public ModelAndView editMenu( HttpServletRequest request, HttpServletResponse response )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        WxMenu menu = ( WxMenu ) ServletUtil.getValueObject( request, WxMenu.class );

        wxService.editNewWxMenu( menu, wxCode );

        Map paramMap = new HashMap();
        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/weixin/EditWeixinMenu.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/sortMenu.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "菜单排序", token = true )
    public String sortMenu( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        wxService.sortNewWxMenu( params, wxCode );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/deleteMenu.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除菜单", token = true )
    public String deleteMenu( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        Long buttonId = StringUtil.getLongValue( ( String ) params.get( "btId" ), -1 );

        wxService.deleteNewWxMenu( wxCode, buttonId );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/transferMenu.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除菜单", token = true )
    public String transferMenu( HttpServletRequest request, HttpServletResponse response )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        String resCode = wxService.transferWeixinMenu( wxCode );

        return ( resCode == null ? "" : resCode );

    }

}
