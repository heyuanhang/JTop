package cn.com.mjsoft.cms.interflow.controller;

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
import cn.com.mjsoft.cms.interflow.dao.vo.FriendSiteLink;
import cn.com.mjsoft.cms.interflow.service.InterflowService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/interflow" )
public class ManageFriendSiteController
{
    private static InterflowService inService = InterflowService.getInstance();

    @RequestMapping( value = "/addFSite.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加友链", token = true )
    public ModelAndView addFSite( HttpServletRequest request, HttpServletResponse response )
    {

        FriendSiteLink fl = ( FriendSiteLink ) ServletUtil.getValueObject( request,
            FriendSiteLink.class );

        inService.addNewFriendSite( fl );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/interflow/CreateSiteLink.jsp", paramMap );
    }

    @RequestMapping( value = "/editFSite.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑友链", token = true )
    public ModelAndView editFSite( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        FriendSiteLink fl = ( FriendSiteLink ) ServletUtil.getValueObject( request,
            FriendSiteLink.class );

        inService.editFriendSite( fl, params );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/interflow/EditSiteLink.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/deleteFSite.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑友链", token = true )
    public String deleteFSite( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        inService.deleteFriendSite( idList );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/sortFSite.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "排序友链", token = true )
    public String sortFSite( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        inService.sortFriendSite( params );

        return "success";
    }

    @RequestMapping( value = "/addFSiteType.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加友链类型", token = true )
    public ModelAndView addFSiteType( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        inService.addNewFriendSiteType( params );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/interflow/CreateSiteLinkType.jsp", paramMap );

    }

    @RequestMapping( value = "/editFSiteType.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑友链类型", token = true )
    public ModelAndView editFSiteType( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        inService.editFriendSiteType( params );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        paramMap.put( "ltId", params.get( "ltId" ) );

        return ServletUtil.redirect( "/core/interflow/EditSiteLinkType.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteFSiteType.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除友链类型", token = true )
    public String deleteFSiteType( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        inService.deleteFriendSiteType( idList );

        return "success";
    }
}
