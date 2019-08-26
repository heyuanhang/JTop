package cn.com.mjsoft.cms.weixin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.weixin.service.WeixinService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@Controller
@RequestMapping( "/wx" )
@SuppressWarnings( "unchecked" )
public class WeixinManageUserController
{
    private static WeixinService wxService = WeixinService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/getWxUserFromWxServer.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "从微信拉取用户", token = true )
    public String getWxUserFromWxServer( HttpServletRequest request, HttpServletResponse response )
    {

        String resCode = wxService.transferFromWeixinAllUser( wxService.getSiteWxCode() );

        return resCode;
    }

    @ResponseBody
    @RequestMapping( value = "/getWxUserGroupFromWxServer.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "从微信拉取用户组", token = true )
    public String getWxUserGroupFromWxServer( HttpServletRequest request,
        HttpServletResponse response )
    {
        String resCode = wxService.transferFromWeixinAllUserGroup( wxService.getSiteWxCode() );
        return resCode;
    }

    @ResponseBody
    @RequestMapping( value = "/createWxUserGroupFromWxServer.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "创建用户组", token = true )
    public String createWxUserGroupFromWxServer( HttpServletRequest request,
        HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String newName = ( String ) params.get( "gname" );

        String resCode = wxService
            .transferToWeixinNewUserGroup( wxService.getSiteWxCode(), newName );

        return resCode;
    }

    @ResponseBody
    @RequestMapping( value = "/editWxUserGroupFromWxServer.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑用户组", token = true )
    public String editWxUserGroupFromWxServer( HttpServletRequest request,
        HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String gid = ( String ) params.get( "gid" );

        String newName = ( String ) params.get( "gname" );

        String resCode = wxService.transferToWeixinEditUserGroupName( wxService.getSiteWxCode(),
            gid, newName );

        return resCode;
    }

    @ResponseBody
    @RequestMapping( value = "/deleteWxUserGroupFromWxServer.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除用户组", token = true )
    public String deleteWxUserGroupFromWxServer( HttpServletRequest request,
        HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String gid = ( String ) params.get( "gid" );

        String resCode = wxService.transferToWeixinDeleteUserGroup( wxService.getSiteWxCode(), gid );

        return resCode;
    }

    @ResponseBody
    @RequestMapping( value = "/changeWxUserGroupFromWxServer.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "变更用户的组", token = true )
    public String changeWxUserGroupFromWxServer( HttpServletRequest request,
        HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String gid = ( String ) params.get( "gid" );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        String resCode = wxService.transferToWeixinChangeUserGroup( wxService.getSiteWxCode(),
            idList, gid );

        return resCode;
    }

    @ResponseBody
    @RequestMapping( value = "/remarkUser.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "标记微信用户", token = true )
    public String remarkUser( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String opid = ( String ) params.get( "openId" );

        String remark = ( String ) params.get( "userRemark" );

        wxService.addWxUserRemark( opid, remark );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteNotSubWxUser.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除未订阅用户", token = true )
    public String deleteNotSubWxUser( HttpServletRequest request, HttpServletResponse response )
    {
        wxService.deleteWxUserBySubStatus( wxService.getSiteWxCode(), Constant.COMMON.OFF );

        return "success";
    }

}
