package cn.com.mjsoft.cms.member.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.member.bean.MemberBean;
import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.GenericAuth;
import cn.com.mjsoft.framework.security.crypto.PasswordUtility;
import cn.com.mjsoft.framework.security.headstream.IUser;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionImpl;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.IPSeeker;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/member" )
public class WXMemberLoginController
{
    private static Logger log = Logger.getLogger( WXMemberLoginController.class );

    private static MemberService memberService = MemberService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/wxMemberLogin.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public Object memberLogin( HttpServletRequest request, HttpServletResponse response )
    {

        SiteGroupBean siteBean = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );

        if( siteBean == null )
        {
            siteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.getEmptySiteGroupInfo();
        }

        Map params = ServletUtil.getRequestInfo( request );

        String loginOutFlag = ( String ) params.get( "action" );

        HttpServletRequest req = request;

        HttpSession ssn = req.getSession();

        if( "LoginOut".equals( loginOutFlag ) )
        {
            SecuritySessionKeeper.invalidCurrentUserSessionContext( request );

            SecuritySessionKeeper.romoveToken( StringUtil.getLongValue( request
                .getParameter( "userId" ), 0 ) );

            return "1";
        }
        else
        {

            String userName = ( String ) params.get( "memberName" );

            String password = SystemSafeCharUtil.resumeHTML( ( String ) params.get( "parampw" ) );

            Boolean fromReg = ( Boolean ) request.getAttribute( "fromReg" );

            Boolean fromThird = ( Boolean ) request.getAttribute( "fromThird" );

            if( fromReg == null )
            {
                fromReg = false;
            }

            boolean fromLogin = false;

            boolean thirdLoginSuccess = false;

            if( fromThird != null && fromThird.booleanValue() )
            {
                String openId = ( String ) request.getAttribute( "openId" );

                String type = ( String ) request.getAttribute( "type" );

                Long memberId = null;

                if( StringUtil.isStringNotNull( openId ) )
                {

                    // 再次查询openId
                    Map qqLoginInfo = memberService.retrieveThirdLoginInfo( type, openId );

                    memberId = ( Long ) qqLoginInfo.get( "selfMemberId" );

                }

                MemberBean member = memberService.retrieveSingleMemberBean( memberId );

                if( member != null )
                {
                    if( Constant.COMMON.OFF.equals( member.getUseStatus() ) )
                    {
                        Map paramMap = new HashMap();

                        paramMap.put( "error", "-3" );

                        paramMap.put( "memberName", member.getMemberName() );

                        // 跳转到第三方登陆失败页
                        return ServletUtil.redirect( siteBean.getSiteUrl()
                            + siteBean.getThirdLoginErrorUri(), paramMap );

                    }
                    else
                    {
                        thirdLoginSuccess = true;

                    }

                    userName = member.getMemberName();
                }

            }
            else if( fromReg != null && fromReg.booleanValue() )
            {
                userName = ( String ) request.getAttribute( "openId" );

            }
            else
            {
                // 验证码

                Integer loginCount = ( Integer ) ssn.getAttribute( "sysloginErrorCount" );

                if( loginCount != null && loginCount.intValue() > 3 )
                {
                    ssn.setAttribute( "loginErrorMore", Boolean.TRUE );

                    String checkCode = ( String ) ssn
                        .getAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );

                    String checkCodeTest = ( String ) params.get( "sysCheckCode" );

                    if( StringUtil.isStringNull( checkCode )
                        || !checkCode.equalsIgnoreCase( checkCodeTest ) )
                    {
                        // 验证码错误
                        // DCWY
                        // return "-1";
                    }
                }

                fromLogin = true;
            }

            userName = SystemSafeCharUtil.decodeFromWeb( userName );

            if( StringUtil.isStringNull( userName ) )
            {
                userName = "_sys_no_";
            }

            MemberBean mb = null;

            if( fromReg )
            {
                Map tmem = memberService.retrieveThirdLoginInfo( "weixin", userName );

                mb = memberService.retrieveSingleMemberBean( ( Long ) tmem.get( "selfMemberId" ) );

                userName = mb.getMemberName();
                
                thirdLoginSuccess = true;
            }

            IUser memberUser = memberService.obtainUser( userName );

            if( memberUser == null )
            {
                memberUser = memberService.obtainUserByPhone( userName );
                // 增加邮箱登录
                if( memberUser == null )
                {
                    memberUser = memberService.obtainUserByEmail( userName );
                }
            }

            // 注意: 实际上还有很多状态可以决定是否登陆成功，比如是否锁定
            if( memberUser != null && !memberUser.isEnabled() )
            {
                // 会员被停用，不可登陆
                return "-3";
            }

            if( memberUser != null )
            {
                mb = memberService.retrieveSingleMemberBean( ( Long ) memberUser.getIdentity() );

                if( !mb.getSiteId().equals( siteBean.getSiteId() ) )
                {
                    return "-2";
                }
            }

            if( thirdLoginSuccess
                || ( memberUser != null && StringUtil.isStringNotNull( userName ) && StringUtil
                    .isStringNotNull( password ) ) )
            {
                // 记录登录
                String ip = IPSeeker.getIp( request );

                if( thirdLoginSuccess || "__sys_random__".equals( password ) )
                {
                    Auth auth = new GenericAuth( memberUser.getRoleArray(), memberUser
                        .getUserName(), password, ( Long ) memberUser.getIdentity(), memberUser
                        .getOrgIdentity(), memberUser.getOrgCode() );

                    SecuritySession securitySession = new SecuritySessionImpl();

                    memberService.updateMemberLoginTrace( ip, new Timestamp( DateAndTimeUtil
                        .clusterTimeMillis() ), ( Long ) auth.getIdentity() );

                    // 记录成功次数
                    memberService.addOneCountForMemberLoginSuccess( ( Long ) auth.getIdentity() );

                    // 登陆者信息
                    securitySession.setAuth( auth );

                    MemberBean member = memberService.retrieveSingleMemberBean( ( Long ) memberUser
                        .getIdentity() );

                    member.setPassword( "" );

                    securitySession.setMember( member );

                    if( Constant.COMMON.ON.equals( siteBean.getMemberLoginOnce() ) )
                    {
                        // 清除之前登录的session
                        SecuritySession oldSe = SecuritySessionKeeper
                            .getSecSessionByUserId( ( Long ) memberUser.getIdentity() );

                        if( oldSe != null )
                        {
                            oldSe.setAuth( null );
                            oldSe.setCurrentLoginSiteInfo( -1l );
                            oldSe.setMember( null );
                            oldSe.setUUID( null );

                            SecuritySessionKeeper.romoveToken( ( Long ) memberUser.getIdentity() );
                        }
                    }

                    // 当前会员站点信息,当切换站点时,需要改变持有的站点bean

                    securitySession.setCurrentLoginSiteInfo( siteBean.getSiteId() );

                    Map tokenInfo = SecuritySessionKeeper
                        .setCurrentUserSessionContextToHttpSession( request, response,
                            securitySession, ( Long ) memberUser.getIdentity(), siteBean
                                .getMemberExpire().intValue() * 60 );

                    tokenInfo.put( "userName", member.getMemberName() );

                    tokenInfo.remove( "token" );

                    log.info( "[登陆活动]会员用户：" + memberUser.getUserName() + "登陆成功!" );

                    // 设置成功标志

                    request.setAttribute( "successFlag", Boolean.TRUE );

                    request.setAttribute( "memberId", member.getMemberId() );

                    if( fromLogin )
                    {
                        return tokenInfo;
                    }

                    request.setAttribute( "memberId", ( Long ) memberUser.getIdentity() );

                    if( thirdLoginSuccess )
                    {
                        // 清除sesion相关信息
                        request.getSession().removeAttribute( "qq_access_token" );

                        request.getSession().removeAttribute( "qq_token_expirein" );

                        request.getSession().removeAttribute( "qq_openid" );

                        request.getSession().removeAttribute( "qq_userinfo" );

                        if( "true".equals( request.getAttribute( "direct" ) ) )
                        {
                            // 跳转到登录成功入口页

                            return ServletUtil.redirect( siteBean.getSiteUrl()
                                + siteBean.getThirdLoginSuccessUri() );

                        }
                    }

                    memberService.addMemberLoginTrace( siteBean.getSiteId(), userName, ip,
                        Constant.COMMON.LOGIN_SUCCESS );

                    return tokenInfo;
                }
                else
                {
                    memberService.addMemberLoginTrace( siteBean.getSiteId(), userName, ip,
                        Constant.COMMON.LOGIN_FAIL );

                    Integer loginCount = ( Integer ) ssn.getAttribute( "sysloginErrorCount" );

                    if( loginCount == null )
                    {
                        ssn.setAttribute( "sysloginErrorCount", Integer.valueOf( 1 ) );

                        ssn.setAttribute( "loginErrorMore", Boolean.FALSE );
                    }
                    else
                    {
                        if( loginCount.intValue() > 2 )
                        {
                            ssn.setAttribute( "loginErrorMore", Boolean.TRUE );

                            ssn.setAttribute( "sysloginErrorCount", Integer.valueOf( loginCount
                                .intValue() + 1 ) );
                            // DCWY
                            // return "-4";
                        }
                        else
                        {
                            ssn.setAttribute( "sysloginErrorCount", Integer.valueOf( loginCount
                                .intValue() + 1 ) );

                            ssn.setAttribute( "loginErrorMore", Boolean.FALSE );
                        }
                    }

                    return "-2";
                }
            }
        }

        Integer loginCount = ( Integer ) ssn.getAttribute( "sysloginErrorCount" );

        if( loginCount == null )
        {
            ssn.setAttribute( "sysloginErrorCount", Integer.valueOf( 1 ) );

            ssn.setAttribute( "loginErrorMore", Boolean.FALSE );
        }
        else
        {
            if( loginCount.intValue() > 2 )
            {
                ssn.setAttribute( "loginErrorMore", Boolean.TRUE );

                ssn
                    .setAttribute( "sysloginErrorCount", Integer
                        .valueOf( loginCount.intValue() + 1 ) );
                // DCWY
                // return "-4";
            }
            else
            {
                ssn
                    .setAttribute( "sysloginErrorCount", Integer
                        .valueOf( loginCount.intValue() + 1 ) );

                ssn.setAttribute( "loginErrorMore", Boolean.FALSE );
            }
        }

        return "-2";
    }
}
