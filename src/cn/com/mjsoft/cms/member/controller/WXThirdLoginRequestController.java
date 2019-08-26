package cn.com.mjsoft.cms.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.IPSeeker;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/member" )
public class WXThirdLoginRequestController
{
    @ResponseBody
    @RequestMapping( value = "/wxThirdLoginReq.do", method = { RequestMethod.POST,
        RequestMethod.GET } )
    public Object thirdLoginReq( HttpServletRequest request, HttpServletResponse response )
    {
        Map<String, String> params = ServletUtil.getRequestInfo( request );

        String type = params.get( "type" );

        String openId = params.get( "openId" );

        String hpimg = params.get( "hpimg" );

        String backStr = SystemSafeCharUtil.decodeFromWeb( params.get( "userinfo" ) );

        if( StringUtil.isStringNotNull( backStr ) )
        {
            JSONObject jn = JSON.parseObject( backStr );
            
            openId = jn.getString( "openid" );

            hpimg = jn.getString( "headimgurl" );
        }

        boolean relateReg = StringUtil
            .getBooleanValue( ( String ) params.get( "relateReg" ), false );

        String phoneNumber = ( String ) params.get( "memberName" );

        String password = ( String ) params.get( "password" );

        String checkpassword = ( String ) params.get( "checkPassword" );

        String direct = ( String ) params.get( "direct" );

        SiteGroupBean site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );

        String status = MemberService.getInstance().appThirdLoginAndReg( type, openId, hpimg,
            relateReg, phoneNumber, password, checkpassword, IPSeeker.getIp( request ), site,
            params );

        if( "openid_login".equals( status ) )
        {
            request.setAttribute( "fromThird", Boolean.TRUE );

            if( !"false".equals( direct ) )
            {
                request.setAttribute( "direct", "true" );
            }

            request.setAttribute( "type", type );

            request.setAttribute( "openId", openId );

            request.setAttribute( "userinfo", backStr );

            request.setAttribute( "memberName", phoneNumber );

            return ServletUtil.forward( "/member/wxMemberLogin.do" );
        }
        else if( "third_direct".equals( status ) )
        {
            Map returnParam = new HashMap();

            returnParam.put( "type", type );

            returnParam.put( "openId", openId );

            returnParam.put( "memberName", phoneNumber );

            return ServletUtil.redirect( site.getSiteUrl() + "m_third_reg_direct.jsp", returnParam );
        }
        else if( "relate_reg_success".equals( status ) )
        {
            request.setAttribute( "fromReg", Boolean.TRUE );

            request.setAttribute( "direct", "true" );

            request.setAttribute( "memberName", phoneNumber );
            request.setAttribute( "password", password );

            request.setAttribute( "userinfo", backStr );

            request.setAttribute( "openId", openId );

            return ServletUtil.forward( "/member/wxMemberLogin.do" );
        }
        else if( "relate_success".equals( status ) )
        {
            request.setAttribute( "fromThird", Boolean.TRUE );

            request.setAttribute( "direct", "true" );

            request.setAttribute( "userinfo", backStr );

            request.setAttribute( "type", type );

            request.setAttribute( "openId", openId );

            request.setAttribute( "userinfo", backStr );

            request.setAttribute( "memberName", phoneNumber );

            return ServletUtil.forward( "/memberwx/wxMemberLogin.do" );
        }

        return status;
    }

    @ResponseBody
    @RequestMapping( value = "/wxOauth2.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public Object wxOauth2( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String direct = ( String ) params.get( "direct" );

        if( "true".equals( direct ) )
        {
            String wxcall = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx43a999e7222f694b&redirect_uri=http%3A%2F%2Fgd.wisindata.cn%2Fmember%2FwxOauth2.do&response_type=code&scope=snsapi_userinfo&state=abc12sd#wechat_redirect";

            return new ModelAndView( new RedirectView( wxcall ) );

        }

        String clientId = "wx43a999e7222f694b";

        String clientKey = "9f8641f221bc3fcadfe23a971e7fef3f";

        String target = StringUtil.replaceString( request.getRequestURL().toString(),
            "wx/wxOauth2.do", "", false, false );

        String state = ( String ) params.get( "state" );

        String co = ( String ) params.get( "code" );

        // token
        // String token = ( String ) WeixinService.SNS_API_TOKEN.get( target );

        // if(StringUtil.isStringNull( token ))
        // {

        String opurl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + clientId
            + "&secret=" + clientKey + "&code=" + co + "&grant_type=authorization_code";

        ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

        String backStr = ServiceUtil.readStream( ServiceUtil.doGETMethodRequest( opurl ), "UTF-8" );

        if( StringUtil.isStringNull( backStr ) )
        {
            return null;
        }

        JSONObject jn = JSON.parseObject( backStr );

        String token = jn.getString( "access_token" );

        // }

        String openId = jn.getString( "openid" );

        String getUserUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token
            + "&openid=" + openId + "&lang=zh_CN";

        ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

        backStr = ServiceUtil.readStream( ServiceUtil.doGETMethodRequest( getUserUrl ), "UTF-8" );

        jn = JSON.parseObject( backStr );

        String imgUrl = jn.getString( "headimgurl" );

        openId = jn.getString( "openid" );

        String nickName = jn.getString( "nickname" );

        String wxlogin = "http://gd.wisindata.cn/member/wxThirdLoginReq.do?type=weixin&password=__sys_random__&userinfo="
            + SystemSafeCharUtil.encode( SystemSafeCharUtil.encode( backStr ) );

        return new ModelAndView( new RedirectView( wxlogin ) );

        // return ServletUtil.forward( "/member/wxMemberLogin.do" );

        // return backStr;

    }
}
