package cn.com.mjsoft.cms.weixin.controller;

import java.util.HashMap;
import java.util.List;
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
import cn.com.mjsoft.cms.weixin.service.WeixinService;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@Controller
@RequestMapping( "/wx" )
@SuppressWarnings( "unchecked" )
public class WeixinManageResourceController
{
    private static WeixinService wxService = WeixinService.getInstance();

    @RequestMapping( value = "/addWxImage.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加微信图片", token = true )
    public ModelAndView addWxImage( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        params.put( "siteId", site.getSiteId() );

        params.put( "wxCode", wxService.getSiteWxCode() );

        wxService.addImageRes( params );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/weixin/AddWxImageRes.jsp", returnParams );

    }

    @RequestMapping( value = "/editWxImage.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑微信图片", token = true )
    public ModelAndView editWxImage( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        params.put( "wxCode", wxService.getSiteWxCode() );

        wxService.editImageRes( params );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/weixin/EditWxImageRes.jsp", returnParams );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteWxImage.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除微信图片", token = true )
    public String deleteWxImage( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        wxService.deleteImageRes( idList, wxService.getSiteWxCode() );

        return "success";
    }

    @RequestMapping( value = "/addWxVideo.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加微信视频", token = true )
    public ModelAndView addWxVideo( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        params.put( "siteId", site.getSiteId() );

        params.put( "wxCode", wxService.getSiteWxCode() );

        wxService.addVideoRes( params );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/weixin/AddWxVideoRes.jsp", returnParams );

    }

    @RequestMapping( value = "/editWxVideo.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑微信视频", token = true )
    public ModelAndView editWxVideo( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        params.put( "siteId", site.getSiteId() );

        params.put( "wxCode", wxService.getSiteWxCode() );

        wxService.editVideoRes( params );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/weixin/EditWxVideoRes.jsp", returnParams );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteWxVideo.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除微信视频", token = true )
    public String deleteWxVideo( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        wxService.deleteVideoAndVoiceAndMusicAndTextRes( idList, wxService.getSiteWxCode() );

        return "success";
    }

    @RequestMapping( value = "/addWxVoice.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加微信音频", token = true )
    public ModelAndView addWxVoice( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        params.put( "siteId", site.getSiteId() );

        params.put( "wxCode", wxService.getSiteWxCode() );

        wxService.addVoiceRes( params );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/weixin/AddWxVoiceRes.jsp", returnParams );

    }

    @RequestMapping( value = "/editWxVoice.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑微信音频", token = true )
    public ModelAndView editWxVoice( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        params.put( "siteId", site.getSiteId() );

        params.put( "wxCode", wxService.getSiteWxCode() );

        wxService.editVoiceRes( params );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/weixin/EditWxVoiceRes.jsp", returnParams );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteWxVoice.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除微信音频", token = true )
    public String deleteWxVoice( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        wxService.deleteVideoAndVoiceAndMusicAndTextRes( idList, wxService.getSiteWxCode() );

        return "success";
    }

    @RequestMapping( value = "/addWxMusic.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加微信歌曲", token = true )
    public ModelAndView addWxMusic( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        params.put( "siteId", site.getSiteId() );

        params.put( "wxCode", wxService.getSiteWxCode() );

        wxService.addMusicRes( params );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/weixin/AddWxMusicRes.jsp", returnParams );

    }

    @RequestMapping( value = "/editWxMusic.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑微信歌曲", token = true )
    public ModelAndView editWxMusic( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        params.put( "siteId", site.getSiteId() );

        params.put( "wxCode", wxService.getSiteWxCode() );

        wxService.editMusicRes( params );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/weixin/EditWxMusicRes.jsp", returnParams );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteWxMusic.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除微信歌曲", token = true )
    public String deleteWxMusic( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        wxService.deleteVideoAndVoiceAndMusicAndTextRes( idList, wxService.getSiteWxCode() );

        return "success";
    }

    @RequestMapping( value = "/addWxText.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加微信文本", token = true )
    public ModelAndView addWxText( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        params.put( "siteId", site.getSiteId() );

        params.put( "wxCode", wxService.getSiteWxCode() );

        wxService.addTextRes( params );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/weixin/AddWxTextRes.jsp", returnParams );

    }

    @RequestMapping( value = "/editWxText.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑微信文本", token = true )
    public ModelAndView editWxText( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        params.put( "siteId", site.getSiteId() );

        params.put( "wxCode", wxService.getSiteWxCode() );

        wxService.editTextRes( params );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/weixin/EditWxTextRes.jsp", returnParams );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteWxText.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除微信文本", token = true )
    public String deleteWxText( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        wxService.deleteVideoAndVoiceAndMusicAndTextRes( idList, wxService.getSiteWxCode() );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/addResTag.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加微信资源标签", token = true )
    public String addResTag( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        params.put( "wxCode", wxService.getSiteWxCode() );

        String tagName = ( String ) params.get( "tagName" );

        wxService.addResTag( tagName, wxService.getSiteWxCode(), ( String ) params.get( "type" ) );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/editResTag.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑微信资源标签", token = true )
    public String editResTag( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        params.put( "wxCode", wxService.getSiteWxCode() );

        String tagName = ( String ) params.get( "tagName" );

        Long rtId = StringUtil.getLongValue( ( String ) params.get( " rtId" ), -1 );

        wxService.editResTag( rtId, tagName, wxService.getSiteWxCode() );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteResTag.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除微信资源标签", token = true )
    public String deleteResTag( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long rtId = StringUtil.getLongValue( ( String ) params.get( "id" ), -1 );

        wxService.deleteResTag( rtId, wxService.getSiteWxCode() );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/transferRes.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "同步资源到微信", token = true )
    public String transferRes( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        String reType = ( String ) params.get( "type" );

        String flag = wxService.transferWxResource( idList, reType, wxService.getSiteWxCode() );

        return flag;
    }

}
