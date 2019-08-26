package cn.com.mjsoft.cms.member.json;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
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

import com.alibaba.fastjson.JSON;

public class MemberAppLoginFlow extends ApiFlowDisposBaseFlow
{

    private static Logger log = Logger.getLogger( MemberAppLoginFlow.class );

    private static MemberService memberService = MemberService.getInstance();

    public String executeBiz() throws Exception
    {
        HttpServletRequest request = this.getServletFlowContext().getRequest();

        HttpServletResponse response = this.getServletFlowContext().getResponse();

        Map params = this.getFlowContext().getHttpRequestDecodeSnapshot();

        SiteGroupBean siteBean = SiteGroupService
            .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.getServletFlowContext()
                .getRequest() );

        if( siteBean == null )
        {

            String siteId = ( String ) params.get( "siteId" );// 指定站点ID,表单模式使用

            if( StringUtil.isStringNull( siteId ) )
            {
                siteId = "-1";
            }

            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( StringUtil.getLongValue( siteId, -1 ) );

            if( siteBean == null )
            {
                siteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.getEmptySiteGroupInfo();
            }
        }

        HttpServletRequest req = this.getServletFlowContext().getRequest();

        HttpSession ssn = req.getSession();

        String command = this.getFlowContext().getCommand();

        if( "appMemberLoginOut".equals( command ) )
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

            String phone = ( String ) params.get( "phone" );

            Boolean fromReg = ( Boolean ) request.getAttribute( "fromReg" );

            Boolean fromThird = ( Boolean ) request.getAttribute( "fromThird" );

            boolean fromLogin = false;

            boolean thirdLoginSuccess = false;

            if( fromThird != null && fromThird.booleanValue() )
            {
                String openId = ( String ) request.getSession().getAttribute( "qq_openid" );

                String weiboId = ( String ) request.getSession().getAttribute( "weibo_user_id" );

                Long memberId = null;

                if( StringUtil.isStringNotNull( openId ) )
                {

                    // 再次查询openId
                    Map qqLoginInfo = memberService.retrieveThirdLoginInfo(
                        Constant.MEMBER.QQ_LOGIN, openId );

                    memberId = ( Long ) qqLoginInfo.get( "selfMemberId" );

                }
                else if( StringUtil.isStringNotNull( weiboId ) )
                {

                    // 再次查询weiboId
                    Map qqLoginInfo = memberService.retrieveThirdLoginInfo(
                        Constant.MEMBER.WEIBO_LOGIN, weiboId );

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

                        this.getFlowContext().registerURLParameter( paramMap );

                        // 跳转到第三方登陆失败页
                        this.directUrl( siteBean.getSiteUrl() + siteBean.getThirdLoginErrorUri(),
                            true );
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
                userName = ( String ) request.getAttribute( "memberName" );
                password = ( String ) request.getAttribute( "password" );
            }
            else
            {
                // 验证码app登录模式由拦截器统一处理，需要再后台确认是否使用token

                /*
                 * Integer loginCount = ( Integer ) ssn .getAttribute(
                 * "sysloginErrorCount" );
                 * 
                 * if( loginCount != null && loginCount.intValue() > 3 ) {
                 * ssn.setAttribute( "loginErrorMore", Boolean.TRUE );
                 * 
                 * String checkCode = ( String ) ssn .getAttribute(
                 * Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );
                 * 
                 * String checkCodeTest = ( String ) params .get( "sysCheckCode" );
                 * 
                 * if( StringUtil.isStringNull( checkCode ) ||
                 * !checkCode.equalsIgnoreCase( checkCodeTest ) ) { // 验证码错误
                 * return this.( "-1" ); } }
                 */

                fromLogin = true;
            }

            userName = SystemSafeCharUtil.decodeFromWeb( userName );

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

            if( thirdLoginSuccess
                || ( memberUser != null && StringUtil.isStringNotNull( userName ) && StringUtil
                    .isStringNotNull( password ) ) )
            {
                // 记录登录
                String ip = IPSeeker.getIp( request );

                if( thirdLoginSuccess
                    || ( PasswordUtility.match( password, memberUser.getPassword() ) ) )
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

                    // 当前会员站点信息,当切换站点时,需要改变持有的站点bean

                    securitySession.setCurrentLoginSiteInfo( siteBean.getSiteId() );

                    Map tokenInfo = SecuritySessionKeeper
                        .setCurrentUserSessionContextToHttpSession( request, response,
                            securitySession, ( Long ) memberUser.getIdentity(), siteBean
                                .getMemberExpire().intValue() * 60 );

                    tokenInfo.remove( "token" );

                    log.info( "[登陆活动]会员用户：" + memberUser.getUserName() + "登陆成功!" );

                    // 设置成功标志
                    Map context = this.getFlowContext().getFlowContextMap();

                    context.put( "successFlag", Boolean.TRUE );

                    context.put( "memberId", member.getMemberId() );

                    if( fromLogin )
                    {
                        return JSON.toJSONString( tokenInfo );
                    }

                    context.put( "memberId", ( Long ) memberUser.getIdentity() );

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
                            this.directUrl( siteBean.getSiteUrl()
                                + siteBean.getThirdLoginSuccessUri(), true );
                        }
                    }

                    memberService.addMemberLoginTrace( siteBean.getSiteId(), userName, ip,
                        Constant.COMMON.LOGIN_SUCCESS );

                    return JSON.toJSONString( tokenInfo );
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

                            return "-4";
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

                return ( "-4" );
            }
            else
            {
                ssn
                    .setAttribute( "sysloginErrorCount", Integer
                        .valueOf( loginCount.intValue() + 1 ) );

                ssn.setAttribute( "loginErrorMore", Boolean.FALSE );
            }
        }

        return ( "-2" );
    }
}
