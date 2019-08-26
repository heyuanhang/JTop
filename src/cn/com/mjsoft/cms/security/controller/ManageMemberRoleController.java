package cn.com.mjsoft.cms.security.controller;

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
import cn.com.mjsoft.cms.member.dao.vo.MemberAccRule;
import cn.com.mjsoft.cms.security.dao.vo.MemberRole;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/security" )
public class ManageMemberRoleController
{

    private static SecurityService securityService = SecurityService.getInstance();

    @RequestMapping( value = "/addMemberRole.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加会员角色", token = true )
    public ModelAndView addMemberRole( HttpServletRequest request, HttpServletResponse response )
    {

        MemberRole role = ( MemberRole ) ServletUtil.getValueObject( request, MemberRole.class );

        securityService.addMemberRole( role );

        Map paramMap = new HashMap();
        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/member/AddMemberRole.jsp", paramMap );

    }

    @RequestMapping( value = "/editMemberRole.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑会员角色", token = true )
    public ModelAndView editMemberRole( HttpServletRequest request, HttpServletResponse response )
    {
        MemberRole role = ( MemberRole ) ServletUtil.getValueObject( request, MemberRole.class );

        securityService.editMemberRole( role );

        Map paramMap = new HashMap();
        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/member/EditMemberRole.jsp" );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteMemberRole.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除会员角色", token = true )
    public String deleteMemberRole( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        securityService.deleteMemberRoleAllInfo( StringUtil.changeStringToList( ( String ) params
            .get( "ids" ), "," ) );

        return "success";

    }

    @RequestMapping( value = "/matainMemberRoleAuth.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "会员权限维护", token = true )
    public ModelAndView matainMemberRoleAuth( HttpServletRequest request,
        HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        // 粗粒度资源
        String[] checkedResource = StringUtil.getCheckBoxValue( params.get( "checkResource" ) );

        Long targetSiteId = Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "targetSiteId" ), -1 ) );

        Long roleId = Long
            .valueOf( StringUtil.getLongValue( ( String ) params.get( "roleId" ), -1 ) );

        securityService.updateMemberRoleAndItsResourceByRoleIdandNewrRes( roleId, checkedResource );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", "true" );
        paramMap.put( "roleId", roleId );
        paramMap.put( "siteId", targetSiteId );

        return ServletUtil.redirect( "/core/member/MaintainMemberRole.jsp", paramMap );

    }

    @RequestMapping( value = "/deleteMemberFromRole.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除会员拥有角色", token = true )
    public ModelAndView deleteMemberFromRole( HttpServletRequest request,
        HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long roleId = Long
            .valueOf( StringUtil.getLongValue( ( String ) params.get( "roleId" ), -1 ) );

        String[] checkIds = StringUtil.getCheckBoxValue( params.get( "checkedUserId" ) );

        securityService.deleteMemberRealteFromRole( checkIds, roleId );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", "true" );
        paramMap.put( "roleId", roleId );

        return ServletUtil.redirect( "/core/member/EditRoleHaveMember.jsp", paramMap );

    }

    @RequestMapping( value = "/addMemberFromRole.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "给会员增加角色", token = true )
    public ModelAndView addMemberFromRole( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long roleId = Long
            .valueOf( StringUtil.getLongValue( ( String ) params.get( "roleId" ), -1 ) );

        String[] checkIds = StringUtil.getCheckBoxValue( params.get( "checkedUserId" ) );

        securityService.addMemberRealteToRole( checkIds, roleId );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", "true" );
        paramMap.put( "roleId", roleId );

        return ServletUtil.redirect( "/core/member/ShowAllMember.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/addAccRule.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加会员规则权限", token = true )
    public String addAccRule( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        MemberAccRule mar = ( MemberAccRule ) ServletUtil.getValueObject( request,
            MemberAccRule.class );

        if( params.get( "roleIds" ) instanceof String[] )
        {
            mar.setRoleIds( StringUtil.changeStringArrayToStr(
                ( String[] ) params.get( "roleIds" ), "," ) );
        }

        mar.setAccName( SystemSafeCharUtil.decodeFromWeb( mar.getAccName() ) );

        mar.setRuleDesc( SystemSafeCharUtil.decodeFromWeb( mar.getRuleDesc() ) );

        securityService.addMemberAccRule( mar );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/editAccRule.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑会员规则权限", token = true )
    public String editAccRule( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        MemberAccRule mar = ( MemberAccRule ) ServletUtil.getValueObject( request,
            MemberAccRule.class );

        if( params.get( "roleIds" ) instanceof String[] )
        {
            mar.setRoleIds( StringUtil.changeStringArrayToStr(
                ( String[] ) params.get( "roleIds" ), "," ) );
        }

        mar.setAccName( SystemSafeCharUtil.decodeFromWeb( mar.getAccName() ) );

        mar.setRuleDesc( SystemSafeCharUtil.decodeFromWeb( mar.getRuleDesc() ) );

        securityService.editMemberAccRule( mar );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/deleteAccRule.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除会员规则权限", token = true )
    public String deleteAccRule( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        securityService.deleteMemberAccRule( idList );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/applyAcc.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "应用会员分类权限", token = true )
    public String applyAcc( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        securityService.addMemberClassAcc( params );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/applyChildAcc.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "应用会员子分类权限", token = true )
    public String applyChildAcc( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long classId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "classId" ),
            -1 ) );

        Integer typeId = Integer.valueOf( StringUtil.getIntValue(
            ( String ) params.get( "typeId" ), -1 ) );

        securityService.applyChildMemberClassAcc( classId, typeId );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/clearAcc.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "清除会员分类权限", token = true )
    public String clearAcc( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Integer typeId = Integer.valueOf( StringUtil.getIntValue(
            ( String ) params.get( "typeId" ), -1 ) );

        securityService.clearMemberClassAcc( typeId );

        return "success";

    }

}
