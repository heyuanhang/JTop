package cn.com.mjsoft.cms.weixin.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.weixin.bean.WxAccount;
import cn.com.mjsoft.cms.weixin.bean.item.WxNewsItemInfo;
import cn.com.mjsoft.cms.weixin.service.WeixinService;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/wx" )
public class WeixinManageInfoItemController
{

    private static ContentService contentService = ContentService.getInstance();

    private static WeixinService wxService = WeixinService.getInstance();

    @RequestMapping( value = "/createNewsInfoItem.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加图文信息", token = true )
    public ModelAndView createNewsInfoItem( HttpServletRequest request, HttpServletResponse response )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        // 编辑器类型字段
        Set editorFieldSet = new HashSet();

        editorFieldSet.add( "articleText" );

        editorFieldSet.add( "articleText_jtop_sys_hidden_temp_html" );

        Map params = ServletUtil.getRequestInfo( request, editorFieldSet );

        disposeNewsInfoParam( params, editorFieldSet, site );

        WxNewsItemInfo news = ( WxNewsItemInfo ) ServletUtil.getValueObject( request,
            WxNewsItemInfo.class );

        Long rowFlag = Long.valueOf( StringUtil
            .getLongValue( ( String ) params.get( "rowFlag" ), 1 ) );

        Integer infoRow = Integer.valueOf( StringUtil.getIntValue( ( String ) params
            .get( "infoRow" ), 1 ) );

        boolean inCol = StringUtil.getBooleanValue( ( String ) params.get( "inCol" ), false );

        news.setWxId( Long.valueOf( 1 ) );

        // 设定站点

        news.setSiteFlag( site.getSiteFlag() );

        wxService.addSingleWxNewsInfo( news, rowFlag, infoRow, inCol );
 
        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/weixin/AddWxNewsInfo.jsp", returnParams );
    }

    @RequestMapping( value = "/editNewsInfoItem.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑图文信息", token = true )
    public ModelAndView editNewsInfoItem( HttpServletRequest request, HttpServletResponse response )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        // 编辑器类型字段
        Set editorFieldSet = new HashSet();

        editorFieldSet.add( "articleText" );

        editorFieldSet.add( "articleText_jtop_sys_hidden_temp_html" );

        Map params = ServletUtil.getRequestInfo( request, editorFieldSet );

        disposeNewsInfoParam( params, editorFieldSet, site );

        WxNewsItemInfo news = ( WxNewsItemInfo ) ServletUtil.getValueObject( request,
            WxNewsItemInfo.class );

        news.setWxId( Long.valueOf( 1 ) );

        // 设定站点

        news.setSiteFlag( site.getSiteFlag() );

        wxService.editSingleNewsItemInfo( news );

        
        
        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/weixin/EditWxNewsInfo.jsp", returnParams );
    }

    @ResponseBody
    @RequestMapping( value = "/deleteNewsInfoItem.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除微信图文", token = true )
    public String deleteNewsInfoItem( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List infoIdArrayList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        Long rowFlag = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "rowFlag" ),
            -1 ) );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        wxService.deleteNewsItemInfo( rowFlag, infoIdArrayList, site.getSiteFlag() );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/deleteAllNews.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "批量删除微信图文", token = true )
    public String deleteAllNews( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List rowFlagList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        wxService.deleteAllNewsItemInfo( rowFlagList );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/transferNews.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "图文同步到微信", token = true )
    public String transferNews( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = wxService.retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        String flag = wxService.transferWxNews( idList, wxCode );

        return flag;

    }

    private void disposeNewsInfoParam( Map params, Set editorFieldSet, SiteGroupBean site )
    {
        /**
         * 文本特殊处理
         */
        Iterator editorFieldIter = editorFieldSet.iterator();

        String fieldName = null;

        String text = null;

        while ( editorFieldIter.hasNext() )
        {
            fieldName = ( String ) editorFieldIter.next();

            text = ( String ) params.get( fieldName );

            if( Constant.COMMON.OFF.equals( site.getSiteCollType() ) )
            {
                // html白名单
                text = ServiceUtil.cleanEditorHtmlByWhiteRule( text );
            }

            // 站外链接
            if( Constant.COMMON.ON.equals( site.getDeleteOutLink() ) )
            {
                text = contentService.deleteContentTextOutHref( text, site );
            }

            params.put( fieldName, text );
        }
    }
}
