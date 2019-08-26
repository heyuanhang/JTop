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
import cn.com.mjsoft.cms.security.bean.SecurityResourceBean;
import cn.com.mjsoft.cms.security.dao.vo.SystemRole;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/security" )
public class ManageRoleController
{

    private static SecurityService securityService = SecurityService.getInstance();

    @RequestMapping( value = "/addSystemRole.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加管理员角色", token = true )
    public ModelAndView addSystemRole( HttpServletRequest request, HttpServletResponse response )
    {

        SystemRole role = ( SystemRole ) ServletUtil.getValueObject( request, SystemRole.class );

        if( StringUtil.isStringNull( role.getRoleName() ) )
        {
            throw new FrameworkException( "缺少角色名称" );
        }

        securityService.addSystemRole( role );

        Map paramMap = new HashMap();
        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/security/AddSystemRole.jsp", paramMap );

    }

    @RequestMapping( value = "/editSystemRole.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑管理员角色", token = true )
    public ModelAndView editSystemRole( HttpServletRequest request, HttpServletResponse response )
    {

        SystemRole role = ( SystemRole ) ServletUtil.getValueObject( request, SystemRole.class );

        if( StringUtil.isStringNull( role.getRoleName() ) )
        {
            throw new FrameworkException( "缺少角色名称" );
        }

        securityService.editSystemRole( role );

        Map paramMap = new HashMap();
        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/security/EditSystemRole.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteSystemRole.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除管理员角色", token = true )
    public String deleteSystemRole( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        securityService.deleteSystemRoleAllInfo( StringUtil.changeStringToList( ( String ) params
            .get( "ids" ), "," ) );

        return "success";
    }

    @RequestMapping( value = "/deleteUserFromRole.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除管理员拥有角色", token = true )
    public ModelAndView deleteUserFromRole( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long roleId = StringUtil.getLongValue( ( String ) params.get( "roleId" ), -1 );
        String[] checkIds = StringUtil.getCheckBoxValue( params.get( "checkedUserId" ) );

        securityService.deleteUserRealteFromRole( checkIds, roleId, ( Map ) request
            .getServletContext().getAttribute( SecuritySession.SEC_APP_FLAG ) );

        Map paramMap = new HashMap();
        paramMap.put( "roleId", roleId );
        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/security/EditRoleHaveUser.jsp", paramMap );

    }

    @RequestMapping( value = "/addUserFromRole.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加管理员拥有角色", token = true )
    public ModelAndView addUserFromRole( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long roleId = StringUtil.getLongValue( ( String ) params.get( "roleId" ), -1 );
        String[] checkIds = StringUtil.getCheckBoxValue( params.get( "checkedUserId" ) );

        securityService.addUserRealteToRole( checkIds, roleId, ( Map ) request.getServletContext()
            .getAttribute( SecuritySession.SEC_APP_FLAG ) );

        Map paramMap = new HashMap();
        paramMap.put( "roleId", roleId );
        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/security/ShowAllUser.jsp", paramMap );
    }

    @RequestMapping( value = "/matainSystemRoleAuth.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "维护管理员角色", token = true )
    public ModelAndView matainSystemRoleAuth( HttpServletRequest request,
        HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        // 粗粒度资源
        String[] checkedResource = StringUtil.getCheckBoxValue( params.get( "checkResource" ) );

        Long targetSiteId = StringUtil.getLongValue( ( String ) params.get( "targetSiteId" ), -1 );

        Long roleId = StringUtil.getLongValue( ( String ) params.get( "roleId" ), -1 );

        // 站点初级管理授权
        String[] checkRoleSiteManagerArray = StringUtil.getCheckBoxValue( params
            .get( "site-manager" ) );

        // 细粒度内容管理资源
        // 需要保护的所有细粒度资源

        List allProtectContentAccTypeSecRes = securityService
            .retrieverAccGroupSecurityResourceBean();

        // 获取页面中授予的权限
        SecurityResourceBean bean = null;

        String[] checkedAccSecId = null;

        Map accResInfoMap = new HashMap();

        for ( int i = 0; i < allProtectContentAccTypeSecRes.size(); i++ )
        {
            bean = ( SecurityResourceBean ) allProtectContentAccTypeSecRes.get( i );

            checkedAccSecId = getCheckAccClassIdInfo( StringUtil.getCheckBoxValue( params.get( bean
                .getSysFlag() ) ) );

            accResInfoMap.put( bean, checkedAccSecId );
        }

        if( roleId.longValue() < 0 )
        {

            throw new FrameworkException( "角色信息丢失" );
        }

        securityService.updateRoleAndItsResourceByRoleIdandNewrRes( roleId,
            checkRoleSiteManagerArray, checkedResource, accResInfoMap, targetSiteId );

        List allManagerSite = securityService.retrieveSiteBeanByRoleId( roleId );

        SiteGroupBean site = null;
        boolean havaSite = false;
        for ( int i = 0; i < allManagerSite.size(); i++ )
        {
            site = ( SiteGroupBean ) allManagerSite.get( i );
            if( targetSiteId.equals( site.getSiteId() ) )
            {
                havaSite = true;
                break;
            }
        }

        if( !havaSite && !allManagerSite.isEmpty() )
        {
            targetSiteId = ( ( SiteGroupBean ) allManagerSite.get( 0 ) ).getSiteId();
        }

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", "true" );
        paramMap.put( "roleId", roleId );
        paramMap.put( "siteId", targetSiteId );

        return ServletUtil.redirect( "/core/security/MaintainSystemRole.jsp", paramMap );
    }

    private static String[] getCheckAccClassIdInfo( String[] checkValue )
    {
        String[] result = new String[checkValue.length];
        for ( int i = 0; i < checkValue.length; i++ )
        {
            result[i] = StringUtil.split( checkValue[i], "-" )[1];
        }
        return result;

    }

    public static void disposeNewAccResRole( Map params )
    {

        Long targetSiteId = Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "targetSiteId" ), -1 ) );

        Long roleId = Long
            .valueOf( StringUtil.getLongValue( ( String ) params.get( "roleId" ), -1 ) );

        // 细粒度内容管理资源
        // 需要保护的所有细粒度资源

        List allProtectContentAccTypeSecRes = securityService
            .retrieverAccGroupSecurityResourceBean();

        // 获取页面中授予的权限
        SecurityResourceBean bean = null;

        String[] checkedAccSecId = null;

        Map accResInfoMap = new HashMap();

        for ( int i = 0; i < allProtectContentAccTypeSecRes.size(); i++ )
        {
            bean = ( SecurityResourceBean ) allProtectContentAccTypeSecRes.get( i );

            checkedAccSecId = getCheckAccClassIdInfo( StringUtil.getCheckBoxValue( params.get( bean
                .getSysFlag() ) ) );

            accResInfoMap.put( bean, checkedAccSecId );
        }

        if( roleId.longValue() < 0 )
        {

            throw new FrameworkException( "角色信息丢失" );
        }

        securityService.updateRoleAndItsResourceByRoleIdandNewrResForNewClassMode( roleId,
            accResInfoMap, targetSiteId );

        List allManagerSite = securityService.retrieveSiteBeanByRoleId( roleId );

        SiteGroupBean site = null;
        boolean havaSite = false;
        for ( int i = 0; i < allManagerSite.size(); i++ )
        {
            site = ( SiteGroupBean ) allManagerSite.get( i );
            if( targetSiteId.equals( site.getSiteId() ) )
            {
                havaSite = true;
                break;
            }
        }

        if( !havaSite && !allManagerSite.isEmpty() )
        {
            targetSiteId = ( ( SiteGroupBean ) allManagerSite.get( 0 ) ).getSiteId();
        }

    }

}
