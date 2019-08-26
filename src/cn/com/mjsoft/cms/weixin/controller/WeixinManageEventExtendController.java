package cn.com.mjsoft.cms.weixin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.weixin.bean.WxAccount;
import cn.com.mjsoft.cms.weixin.service.WeixinService;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/wx" )
public class WeixinManageEventExtendController
{
    private static WeixinService wxService = WeixinService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/addWxExtend.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加微信扩展业务", token = true )
    public String addWxExtend( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        params.put( "wxCode", wxCode );

        String returnCode = wxService.createWxExtendBehavior( params );

        return returnCode;

    }

    @ResponseBody
    @RequestMapping( value = "/checkWxExtend.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "检查微信扩展" )
    public String checkWxExtend( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        return wxService.checkWxExtendBehavior( ( String ) params.get( "eventType" ), wxCode );

    }

    @ResponseBody
    @RequestMapping( value = "/editWxExtend.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑微信扩展业务", token = true )
    public String editWxExtend( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        params.put( "wxCode", wxCode );

        wxService.editWxExtendBehavior( params );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/deleteWxExtend.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除微信扩展业务", token = true )
    public String deleteWxExtend( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long ebId = StringUtil.getLongValue( ( String ) params.get( "ebId" ), -1 );

        wxService.deleteWxExtendBehavior( ebId );

        return "success";

    }

}
