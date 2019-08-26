package cn.com.mjsoft.cms.member.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/member" )
public class AfterThirdLoginCallbakController
{
    private static Logger log = Logger.getLogger( AfterThirdLoginCallbakController.class );

    private static MemberService memberService = MemberService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/qqLoginAfter.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public ModelAndView qqLoginAfter( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        try
        {
            String state = ( String ) params.get( "state" );

            String serverState = ( String ) request.getSession().getAttribute(
                "__sys_third_login_uid__" );

            if( StringUtil.isStringNull( state ) || !state.equals( serverState ) )
            {
                log.error( "第三方 state 凭证丢失!" );
                // CSRF
                return null;
            }
            else
            {
                SiteGroupBean site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );

                // 1.立即清除state
                request.getSession().removeAttribute( "__sys_third_login_uid__" );

                String authCode = ( String ) params.get( "code" );

                // 2. 取token
                String clientId = site.getQqAppId();

                String clientKey = site.getQqAppKey();

                String redirect_uri = URLEncoder.encode( site.getSiteUrl()
                    + "member/qqLoginAfter.do", "UTF-8" );

                String tokenUrl = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id="
                    + clientId
                    + "&client_secret="
                    + clientKey
                    + "&code="
                    + authCode
                    + "&redirect_uri=" + redirect_uri;

                ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

                String backStr = ServiceUtil.readStream(
                    ServiceUtil.doGETMethodRequest( tokenUrl ), "UTF-8" );

                if( StringUtil.isStringNull( backStr ) )
                {
                    return null;
                }

                String access_token = ServiceUtil.getValFormUrlParam( backStr, "access_token" );

                String expires_in = ServiceUtil.getValFormUrlParam( backStr, "expires_in" );

                if( StringUtil.isStringNull( access_token ) )
                {
                    return null;
                }

                request.getSession().setAttribute( "qq_access_token", access_token );
                request.getSession().setAttribute( "qq_token_expirein", expires_in );

                // 3.OPenId

                String openIdUrl = "https://graph.qq.com/oauth2.0/me?access_token=" + access_token;

                ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

                String openIdback = ServiceUtil.readStream( ServiceUtil
                    .doGETMethodRequest( openIdUrl ), "UTF-8" );

                if( StringUtil.isStringNull( openIdback ) )
                {
                    return null;
                }

                // 去掉无用干扰字

                openIdback = StringUtil.replaceString( openIdback, "callback(", "", false, false );

                openIdback = StringUtil.replaceString( openIdback, ");", "", false, false );

                JSONObject jn = JSON.parseObject( openIdback );

                String openId = jn.getString( "openid" );

                if( StringUtil.isStringNull( openId ) )
                {
                    return null;
                }

                request.getSession().setAttribute( "qq_openid", openId );

                // 强制去掉可能的weiboId
                request.getSession().removeAttribute( "weibo_user_id" );

                // 4.获取用户信息
                String userUrl = "https://graph.qq.com/user/get_user_info?access_token="
                    + access_token + "&oauth_consumer_key=" + clientId + "&openid=" + openId;

                ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

                String userback = ServiceUtil.readStream(
                    ServiceUtil.doGETMethodRequest( userUrl ), "UTF-8" );

                request.getSession().setAttribute( "qq_user_info", userback );

                // 根据openId查询当前会员第三方信息，若查到，说明已经关联过，若没有，给出选择是新建立会员，还是关联已注册会员

                SiteGroupBean siteBean = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( request );

                Map qqLoginInfo = memberService.retrieveThirdLoginInfo( Constant.MEMBER.QQ_LOGIN,
                    openId );

                if( qqLoginInfo.isEmpty() )
                {
                    // 进入关联选择页
                    return ServletUtil.redirect( siteBean.getSiteUrl() + site.getRelateMemberUri() );
                }
                else
                {
                    // 直接生效

                    request.setAttribute( "fromThird", Boolean.TRUE );

                    request.setAttribute( "direct", "true" );

                    return ServletUtil.forward( "/member/memberLogin.do" );
                }
            }
        }
        catch ( Exception e )
        {
            log.error( e );
        }

        // 进入错误页

        SiteGroupBean site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );

        Map paramMap = new HashMap();

        paramMap.put( "error", "-4" );

        return ServletUtil.redirect( site.getSiteUrl() + site.getThirdLoginErrorUri(), paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/weiboLoginAfter.do", method = { RequestMethod.POST,
        RequestMethod.GET } )
    public ModelAndView weiboLoginAfter( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        try
        {
            String state = ( String ) params.get( "state" );

            String serverState = ( String ) request.getSession().getAttribute(
                "__sys_third_login_uid__" );

            if( StringUtil.isStringNull( state ) || !state.equals( serverState ) )
            {
                log.error( "第三方 state 凭证丢失!" );
                // CSRF
                return null;
            }
            else
            {
                SiteGroupBean site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );

                // 1.立即清除state
                request.getSession().removeAttribute( "__sys_third_login_uid__" );

                String authCode = ( String ) params.get( "code" );

                // 2. 取token
                String clientId = site.getWbAppId();

                String clientKey = site.getWbAppKey();

                String redirect_uri = URLEncoder.encode( site.getSiteUrl()
                    + "member/weiboLoginAfter.do", "UTF-8" );

                String tokenUrl = "https://api.weibo.com/oauth2/access_token?grant_type=authorization_code&client_id="
                    + clientId
                    + "&client_secret="
                    + clientKey
                    + "&code="
                    + authCode
                    + "&redirect_uri=" + redirect_uri;

                ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

                String backStr = ServiceUtil.readStream(
                    ServiceUtil.doPOSTMethodRequest( tokenUrl ), "UTF-8" );

                log.info( "取token backStr:" + backStr );

                if( StringUtil.isStringNull( backStr ) )
                {
                    return null;
                }

                JSONObject jn = JSON.parseObject( backStr );

                String access_token = jn.getString( "access_token" );

                String expires_in = jn.getString( "expires_in" );

                String weibo_user_id = jn.getString( "uid" );

                if( StringUtil.isStringNull( access_token ) )
                {
                    return null;
                }

                request.getSession().setAttribute( "weibo_access_token", access_token );
                request.getSession().setAttribute( "weibo_token_expirein", expires_in );
                request.getSession().setAttribute( "weibo_user_id", weibo_user_id );

                // 强制去掉可能的qqId
                request.getSession().removeAttribute( "qq_openid" );

                // 4.获取weibo用户信息

                String userInfoUrl = "https://api.weibo.com/2/users/show.json?uid=" + weibo_user_id
                    + "&source=" + clientKey + "&access_token=" + access_token;

                ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

                String userStr = ServiceUtil.readStream( ServiceUtil
                    .doGETMethodRequest( userInfoUrl ), "UTF-8" );

                log.info( "获取weibo用户信息 userStr:" + userStr );

                request.getSession().setAttribute( "weibo_user_info", userStr );

                if( StringUtil.isStringNotNull( weibo_user_id ) )
                {
                    // 根据openId查询当前会员第三方信息，若查到，说明已经关联过，若没有，给出选择是新建立会员，还是关联已注册会员

                    SiteGroupBean siteBean = SiteGroupService
                        .getCurrentSiteInfoFromWebRequest( request );

                    Map weiboLoginInfo = memberService.retrieveThirdLoginInfo(
                        Constant.MEMBER.WEIBO_LOGIN, weibo_user_id );

                    if( weiboLoginInfo.isEmpty() )
                    {
                        // 进入关联选择页
                        return ServletUtil.redirect( siteBean.getSiteUrl()
                            + site.getRelateMemberUri() );

                    }
                    else
                    {
                        // 直接生效

                        request.setAttribute( "fromThird", Boolean.TRUE );

                        request.setAttribute( "direct", "true" );

                        return ServletUtil.forward( "/member/memberLogin.do" );

                    }

                }

            }
        }
        catch ( Exception e )
        {
            log.error( e );
        }

        // 进入错误页

        SiteGroupBean site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );

        Map paramMap = new HashMap();

        paramMap.put( "error", "-4" );

        return ServletUtil.redirect( site.getSiteUrl() + site.getThirdLoginErrorUri(), paramMap );

    }

}
