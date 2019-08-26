package cn.com.mjsoft.cms.channel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.channel.dao.vo.TagWord;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.support.WebFlow;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/channel" )
public class ManageTagsController
{
    private static ChannelService channelService = ChannelService.getInstance();

    @RequestMapping( value = "/addTag.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加Tag", token = true )
    public Object addTag( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        Map params = ServletUtil.getRequestInfo( request );

        Long typeId = StringUtil.getLongValue( ( String ) params.get( "tagTypeId" ), -9999 );

        List tagNameList = StringUtil.changeStringToList( ( String ) params.get( "tagNames" ), "," );

        String click = ( String ) params.get( "click" );

        channelService.addTagWord( typeId, tagNameList, Long.valueOf( StringUtil.getLongValue(
            click, 0 ) ) );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/words/CreateSiteTag.jsp", paramMap );
    }

    @RequestMapping( value = "/editTag.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑Tag", token = true )
    public Object editTag( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        TagWord tw = ( TagWord ) ServletUtil.getValueObject( request, TagWord.class );

        channelService.editTagWord( tw );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/words/EditSiteTag.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/deleteTag.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除Tag", token = true )
    public Object deleteTag( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List typeIdList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        channelService.deleteTagWordAllInfo( typeIdList );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/addTagClass.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加Tag分类", token = true )
    public Object addTagClass( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        Map params = ServletUtil.getRequestInfo( request );

        String typeName = ( String ) params.get( "tagTypeName" );

        String typeFlag = ( String ) params.get( "flag" );

        SecuritySession securitySession = SecuritySessionKeeper.getSecuritySession();

        SiteGroupBean siteBean = ( SiteGroupBean ) securitySession.getCurrentLoginSiteInfo();

        channelService.addTagTypeInfo( SystemSafeCharUtil.decodeFromWeb( typeName ), typeFlag,
            siteBean.getSiteId() );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/editTagClass.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑Tag分类", token = true )
    public Object editTagClass( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        Map params = ServletUtil.getRequestInfo( request );

        String typeName = ( String ) params.get( "tagTypeName" );

        String typeFlag = ( String ) params.get( "flag" );

        Long typeId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "tagTypeId" ),
            -1 ) );

        channelService.editTagTypeInfo( typeId, SystemSafeCharUtil.decodeFromWeb( typeName ),
            typeFlag );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteTagClass.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除Tag分类", token = true )
    public Object deleteTagClass( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        List typeIdList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        channelService.deleteTagTypeAllInfo( typeIdList );

        return "success";
    }

}
