package cn.com.mjsoft.cms.interflow.controller;

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

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.interflow.dao.vo.SiteAnnounce;
import cn.com.mjsoft.cms.interflow.service.InterflowService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/interflow" )
public class ManageAnnounceController
{
    private static InterflowService inService = InterflowService.getInstance();

    @RequestMapping( value = "/addAn.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加公告", token = true )
    public ModelAndView addAn( HttpServletRequest request, HttpServletResponse response )
    {
        Set htmlFieldSet = new HashSet();

        htmlFieldSet.add( "content" );

        SiteAnnounce sa = ( SiteAnnounce ) ServletUtil.getValueObject( request, htmlFieldSet,
            SiteAnnounce.class );

        inService.addNewSiteAnnounce( sa );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/interflow/CreateAnnounce.jsp", paramMap );
    }

    @RequestMapping( value = "/editAn.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑公告", token = true )
    public ModelAndView editAn( HttpServletRequest request, HttpServletResponse response )
    {
        Set htmlFieldSet = new HashSet();

        htmlFieldSet.add( "content" );

        Map params = ServletUtil.getRequestInfo( request, htmlFieldSet );

        SiteAnnounce sa = ( SiteAnnounce ) ServletUtil.getValueObject( request, htmlFieldSet,
            SiteAnnounce.class );

        inService.editSiteAnnounce( sa );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        paramMap.put( "anId", params.get( "anId" ) );

        return ServletUtil.redirect( "/core/interflow/EditAnnounce.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/deleteAn.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除公告", token = true )
    public String deleteAn( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        inService.deleteSiteAnnounce( idList );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/sortAn.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "公告排序", token = true )
    public String sortAn( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        inService.sortAnnounce( params );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/changeAnUs.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "切换公告状态", token = true )
    public String changeAnUs( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        String status = ( String ) params.get( "status" );

        Integer us = null;

        if( "on".equals( status ) )
        {
            us = Constant.COMMON.ON;
        }
        else
        {
            us = Constant.COMMON.OFF;
        }

        inService.setSiteAnnounceUseStatus( idList, us );

        return "success";

    }

}
