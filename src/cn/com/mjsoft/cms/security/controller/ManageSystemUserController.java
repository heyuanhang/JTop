package cn.com.mjsoft.cms.security.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.security.dao.vo.SystemUser;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.crypto.PasswordUtility;
import cn.com.mjsoft.framework.security.filter.SecuritySessionDisposeFiletr;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/security" )
public class ManageSystemUserController
{

    private static SecurityService securityService = SecurityService.getInstance();

    @RequestMapping( value = "/addSystemUser.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加管理员", token = true )
    public ModelAndView addSystemUser( HttpServletRequest request, HttpServletResponse response )
    {

        if( !systemManagerLogin() )
        {
            return null;
        }

        Map params = ServletUtil.getRequestInfo( request );

        SystemUser user = ( SystemUser ) ServletUtil.getValueObject( request, SystemUser.class );

        String[] selectRoleIds = StringUtil.getCheckBoxValue( params.get( "checkedRoleId" ) );

        List roleIdList = new ArrayList();
        long roleId;
        for ( int i = 0; i < selectRoleIds.length; i++ )
        {
            roleId = StringUtil.getLongValue( selectRoleIds[i], -1 );
            if( roleId > 0 )
            {
                roleIdList.add( Long.valueOf( roleId ) );
            }
        }

        // 无论增加还是编辑,都要去掉非当前所属机构的角色

        if( StringUtil.isStringNull( user.getUserName() ) )
        {
            throw new FrameworkException( "用户名称不可为空!" );
        }

        if( StringUtil.isStringNull( user.getUserTrueName() ) )
        {
            throw new FrameworkException( "真实名称不可为空!" );
        }

        user.setAddTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

        // 两次输入密码判断
        String affirmPassword = ( String ) params.get( "affirmPassword" );

        if( !affirmPassword.equals( user.getPassword() ) )
        {

            throw new FrameworkException( "两次输入的密码不一致！" );

        }

        user.setPassword( PasswordUtility.encrypt( user.getPassword() ) );

        securityService.addSystemUser( user, roleIdList );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/security/AddSystemUser.jsp", paramMap );

    }

    @RequestMapping( value = "/editSystemUser.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑管理员", token = true )
    public ModelAndView editSystemUser( HttpServletRequest request, HttpServletResponse response )
    {

        if( !systemManagerLogin() )
        {
            return null;
        }

        SystemUser user = ( SystemUser ) ServletUtil.getValueObject( request, SystemUser.class );

        if( StringUtil.isStringNull( user.getUserName() ) )
        {
            throw new FrameworkException( "用户名称不可为空!" );
        }

        if( StringUtil.isStringNull( user.getUserTrueName() ) )
        {
            throw new FrameworkException( "真实名称不可为空!" );
        }

        securityService.updateSystemUserBaseInfo( user );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/security/AddSystemUser.jsp", paramMap );

    }

    @RequestMapping( value = "/editUserRole.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑管理员", token = true )
    public ModelAndView editUserRole( HttpServletRequest request, HttpServletResponse response )
    {

        if( !systemManagerLogin() )
        {
            return null;
        }

        Map params = ServletUtil.getRequestInfo( request );

        String[] selectRoleIds = StringUtil.getCheckBoxValue( params.get( "checkedRoleId" ) );

        List roleIdList = new ArrayList();
        long roleId;
        for ( int i = 0; i < selectRoleIds.length; i++ )
        {
            roleId = StringUtil.getLongValue( selectRoleIds[i], -1 );
            if( roleId > 0 )
            {
                roleIdList.add( Long.valueOf( roleId ) );
            }
        }

        Long userId = Long
            .valueOf( StringUtil.getLongValue( ( String ) params.get( "userId" ), -1 ) );

        securityService.updateSystemUserHaveRole( userId, roleIdList );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/security/EditUserHaveRole.jsp", paramMap );
    }

    @RequestMapping( value = "/changePassword.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "改密码", token = true )
    public ModelAndView changePassword( HttpServletRequest request, HttpServletResponse response )
    {

        if( !systemManagerLogin() )
        {
            return null;
        }

        Map params = ServletUtil.getRequestInfo( request );

        SystemUser user = ( SystemUser ) ServletUtil.getValueObject( request, SystemUser.class );

        // 两次输入密码判断
        String password = ( String ) params.get( "password" );

        String affirmPassword = ( String ) params.get( "affirmPassword" );

        if( !affirmPassword.equals( password ) )
        {
            throw new FrameworkException( "两次输入的密码不一致！" );
        }

        Long userId = Long
            .valueOf( StringUtil.getLongValue( ( String ) params.get( "userId" ), -1 ) );

        securityService.updateSystemUserPassword( userId, PasswordUtility.encrypt( user
            .getPassword() ) );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/security/ChangePassword.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/changeStatus.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "改动管理员状态", token = true )
    public String changeStatus( HttpServletRequest request, HttpServletResponse response )
    {
        if( !systemManagerLogin() )
        {
            return null;
        }

        Map params = ServletUtil.getRequestInfo( request );

        String flag = ( String ) params.get( "flag" );

        List idsList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        securityService.updateSystemUserUseStatus( idsList, flag );

        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        Auth auth = session.getAuth();

        // 若停用,则立即删除所有权限
        if( "close".equals( flag ) )
        {
            Set idSet = new HashSet( idsList );

            Map secMap = ( Map ) request.getServletContext().getAttribute(
                SecuritySession.SEC_APP_FLAG );

            Iterator allManagerSecIter = secMap.entrySet().iterator();

            Entry entry = null;
            SecuritySession secSession = null;
            auth = null;
            Long identity = null;

            while ( allManagerSecIter.hasNext() )
            {
                entry = ( Entry ) allManagerSecIter.next();
                secSession = ( SecuritySession ) entry.getValue();

                auth = secSession.getAuth();

                if( auth != null )
                {
                    identity = ( Long ) auth.getIdentity();

                    if( idSet.contains( identity.toString() ) )
                    {
                        auth.setAuthenticated( false );
                    }
                }
            }
        }

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/deleteSystemUser.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除管理员", token = true )
    public String deleteSystemUser( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        securityService.deleteSystemUserAllInfo( StringUtil.changeStringToList( ( String ) params
            .get( "ids" ), "," ) );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/changeLoginUserSite.do", method = { RequestMethod.POST } )
    public String changeLoginUserSite( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long siteId = Long
            .valueOf( StringUtil.getLongValue( ( String ) params.get( "siteId" ), -1 ) );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth != null && auth.isAuthenticated() )
        {
            SiteGroupBean siteGroupBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( siteId );

            if( siteGroupBean != null )
            {
                SecuritySessionKeeper.getSecuritySession().setCurrentLoginSiteInfo(
                    siteGroupBean.getSiteId() );
            }

            SecuritySession snewSec = ( SecuritySession ) request.getSession().getAttribute(
                SecuritySessionDisposeFiletr.SECURITY_SESSION_FLAG );

            snewSec.setCurrentLoginSiteInfo( siteGroupBean.getSiteId() );

            request.getSession().setAttribute( SecuritySessionDisposeFiletr.SECURITY_SESSION_FLAG,
                snewSec );

        }

        return "success";

    }

    private boolean systemManagerLogin()
    {
        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        Auth auth = session.getAuth();

        boolean isOk = true;

        if( !( session.isManager() && auth.isAuthenticated() && auth.getUserRoleCopy() != null && auth
            .getUserRoleCopy().length != 0 ) )
        {
            isOk = false;
        }

        return isOk;
    }

}
