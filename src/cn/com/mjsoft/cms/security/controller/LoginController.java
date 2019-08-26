package cn.com.mjsoft.cms.security.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.behavior.InitRSABehavior;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.organization.service.OrgService;
import cn.com.mjsoft.cms.security.bean.SystemUserBean;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.GenericAuth;
import cn.com.mjsoft.framework.security.crypto.PasswordUtility;
import cn.com.mjsoft.framework.security.headstream.IUser;
import cn.com.mjsoft.framework.security.headstream.UserProvider;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionImpl;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.IPSeeker;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/login" )
public class LoginController
{

    private static Logger log = Logger.getLogger( LoginController.class );

    private static UserProvider securityService = SecurityService.getInstance();

    private static OrgService orgService = OrgService.getInstance();

    private static StatService statService = StatService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/postLogin.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "管理员登录"  )
    public Object postLogin( HttpServletRequest request, HttpServletResponse response )
    {
      
        Map params = ServletUtil.getRequestDecodeInfo( request );
        
        if( params.get( "userName" ) != null )
        {
            params.put( "userName", InitRSABehavior.decryptPrivate(
                InitRSABehavior.getPrivateKey(), params.get( "userName" )
                    .toString() ) );
        }

        if( params.get( "parampw" ) != null )
        {
            params.put( "parampw", InitRSABehavior.decryptPrivate(
                InitRSABehavior.getPrivateKey(), params.get( "parampw" )
                    .toString() ) );
        }
        
        String loginOutFlag = ( String ) params.get( "action" );

        if( "LoginOut".equals( loginOutFlag ) )
        {
            SecuritySessionKeeper.invalidCurrentUserSessionContext( request );

            return "success";
        }
        else
        {
            HttpSession ssn = request.getSession();

            String checkCode = ( String ) ssn
                .getAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );

            String checkCodeTest = ( String ) params.get( "sysCheckCode" );

            if( StringUtil.isStringNull( checkCode ) || !checkCode.equalsIgnoreCase( checkCodeTest ) )
            {
                // 验证码错误

                 return "0"  ;
            }

            // 服务节点模式不允许登录后台
            if( JtRuntime.cmsServer.getClientServer() )
            {
                return   "-9999"  ;
            }

            // 登录时间限制
            if( checkCurrentDayNotLogin() )
            {
                return  "-9998" ;
            }

            String loginIp = IPSeeker.getIp( request );

            String userName = ( String ) params.get( "userName" );

            String password = SystemSafeCharUtil.resumeHTML( ( String ) params.get( "parampw" ) );

            // 检查是否存在白名单
            String managerIp = ( String ) JtRuntime.cmsServer.getManagerIp();

            if( StringUtil.isStringNotNull( managerIp ) )
            {
                if( !ServiceUtil.checkWhiteIP( managerIp, loginIp ) )
                {
                    statService.addManagerLoginTrace( userName, loginIp,
                        Constant.COMMON.LOGIN_BLACK_IP );

                    return   "-1"  ;
                }
            }

            IUser systemUser = securityService.obtainUser( userName );
            
            if( systemUser != null && !systemUser.getUserName().equals( userName ))
            {
                return   "-2"  ;
            }

            // 注意: 很多状态可以决定是否登陆成功，比如是否停用,锁定,密码过期
            if( systemUser != null && systemUser.isEnabled()
                && StringUtil.isStringNotNull( userName ) && StringUtil.isStringNotNull( password ) )
            {
                if( PasswordUtility.match( password, systemUser.getPassword() ) )
                {
                    Auth auth = new GenericAuth( systemUser.getRoleArray(), userName, password,
                        ( Long ) systemUser.getIdentity(), systemUser.getOrgIdentity(), systemUser
                            .getOrgCode() );

                    SecuritySession securitySession = new SecuritySessionImpl();

                    SystemUserBean su = SecurityService.getInstance().retrieveSingleSystemUserBean(
                        null, ( Long ) systemUser.getIdentity() );

                    securitySession.getWorkContextMap().put( "weixinName", su.getWeixinName() );

                    // 登陆者信息
                    securitySession.setAuth( auth );

                    // 当前管理站点信息,当切换站点时,需要改变持有的站点bean

                    List orgSiteGroup = orgService.retrieveSiteBeanByOrgId( ( Long ) systemUser
                        .getOrgIdentity() );

                    SiteGroupBean siteBean = null;

                    if( !orgSiteGroup.isEmpty() )
                    {
                        siteBean = ( SiteGroupBean ) orgSiteGroup.get( 0 );
                    }
                    else
                    {
                        siteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior
                            .getEmptySiteGroupInfo();
                    }

                    securitySession.setCurrentLoginSiteInfo( siteBean.getSiteId() );

                    securitySession.setManagerFlag();

                    SecuritySessionKeeper.setCurrentUserSessionContextToHttpSession( request,
                        response, securitySession, ( Long ) systemUser.getIdentity(),
                        JtRuntime.cmsServer.getLoginTime().intValue() * 60 );

                    log.info( "[登陆活动]系统用户：" + systemUser.getUserName() + "登陆成功!" );

                    statService.addManagerLoginTrace( userName, loginIp,
                        Constant.COMMON.LOGIN_SUCCESS );

                    // InterceptFilter.regActManager( ( Long )
                    // systemUser.getIdentity()
                    // );

                    return   "1"  ;
                }

            }

            statService.addManagerLoginTrace( userName, loginIp, Constant.COMMON.LOGIN_FAIL );
        }

        return   "-2"  ;
    }

    private boolean checkCurrentDayNotLogin()
    {
        Integer aldOpt = ( Integer ) JtRuntime.cmsServer.getExtCfg().get( "aldOpt" );

        Integer alhOpt = ( Integer ) JtRuntime.cmsServer.getExtCfg().get( "alhOpt" );

        Integer alStartHour = ( Integer ) JtRuntime.cmsServer.getExtCfg().get( "alStartHour" );

        Integer alEndHour = ( Integer ) JtRuntime.cmsServer.getExtCfg().get( "alEndHour" );

        Calendar cal = Calendar.getInstance();

        int w = cal.get( Calendar.DAY_OF_WEEK ) - 1;

        int h = cal.get( Calendar.HOUR_OF_DAY );

        // 周一 周五
        if( Constant.SECURITY.ADMIN_LG_M2.equals( aldOpt ) )
        {
            if( w == 0 || w == 6 )
            {
                return true;
            }
        }
        else if( Constant.SECURITY.ADMIN_LG_M3.equals( aldOpt ) ) // 周一 周六
        {
            if( w == 6 )
            {
                return true;
            }
        }

        // 每日都可登录

        // 小时限制
        if( Constant.SECURITY.ADMIN_LG_H_M.equals( alhOpt ) )
        {
            if( h < alStartHour || h >= alEndHour )
            {
                return true;
            }

        }

        return false;
    }
}
