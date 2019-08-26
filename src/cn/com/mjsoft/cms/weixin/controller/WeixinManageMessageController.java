package cn.com.mjsoft.cms.weixin.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import cn.com.mjsoft.cms.weixin.service.WeixinService;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@Controller
@RequestMapping( "/wx" )
@SuppressWarnings( "unchecked" )
public class WeixinManageMessageController
{
    private static WeixinService wxService = WeixinService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/addWxMsg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加微信消息", token = true )
    public String addWxMsg( HttpServletRequest request, HttpServletResponse response )
    {

        Set textSet = new HashSet();

        textSet.add( "textMsg" );

        Map params = ServletUtil.getRequestDecodeInfo( request, textSet );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        params.put( "wxCode", wxCode );

        wxService.createWxMessage( params );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/editWxMsg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑微信消息", token = true )
    public String editWxMsg( HttpServletRequest request, HttpServletResponse response )
    {
        Set textSet = new HashSet();

        textSet.add( "textMsg" );

        Map params = ServletUtil.getRequestDecodeInfo( request, textSet );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        params.put( "wxCode", wxCode );

        wxService.editWxMessage( params );

        return "success";

    }

    @RequestMapping( value = "/editText.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑消息文本", token = true )
    public ModelAndView editText( HttpServletRequest request, HttpServletResponse response )
    {
        Set textSet = new HashSet();

        textSet.add( "textMsg" );

        Map params = ServletUtil.getRequestDecodeInfo( request, textSet );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        params.put( "wxCode", wxCode );

        Long msgId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "msgId" ), 1 ) );

        wxService.editWxTextMessage( msgId, wxCode, ( String ) params.get( "textMsg" ) );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );
        returnParams.put( "msgId", msgId );

        return ServletUtil.redirect( "/core/weixin/EditWxText.jsp", returnParams );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteWxMsg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除微信消息", token = true )
    public String deleteWxMsg( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        params.put( "wxCode", wxCode );

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        wxService.deleteWxMessage( idList, wxCode );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/addWxUnkMsg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加无法识别回复消息", token = true )
    public String addWxUnkMsg( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        params.put( "wxCode", wxCode );

        boolean notExist = wxService.createWxUnkMessage( params );

        if( !notExist )
        {
            return "exist";
        }

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/editWxUnkMsg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑无法识别回复消息", token = true )
    public String editWxUnkMsg( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        params.put( "wxCode", wxCode );

        wxService.editWxUnkMessage( params );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/deleteWxUnkMsg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除无法识别回复消息", token = true )
    public String deleteWxUnkMsg( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        params.put( "wxCode", wxCode );

        String msgType = ( String ) params.get( "msgType" );

        wxService.deleteWxUnkMessage( msgType, wxCode );

        return "success";

    }
}
