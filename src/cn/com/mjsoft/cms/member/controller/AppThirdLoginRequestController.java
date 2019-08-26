package cn.com.mjsoft.cms.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.IPSeeker;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/member" )
public class AppThirdLoginRequestController
{
    @ResponseBody
    @RequestMapping( value = "/appThirdLoginReq.do", method = { RequestMethod.POST,
        RequestMethod.GET } )
    public Object thirdLoginReq( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String type = ( String ) params.get( "type" );

        String openId = ( String ) params.get( "openId" );

        String hpimg = ( String ) params.get( "hpimg" );

        boolean relateReg = StringUtil
            .getBooleanValue( ( String ) params.get( "relateReg" ), false );

        String phoneNumber = ( String ) params.get( "memberName" );

        String password = ( String ) params.get( "password" );

        String checkpassword = ( String ) params.get( "checkPassword" );

        SiteGroupBean site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );

        String status = MemberService.getInstance().appThirdLoginAndReg( type, openId, hpimg,
            relateReg, phoneNumber, password, checkpassword, IPSeeker.getIp( request ), site,
            params );

        if( "openid_login".equals( status ) )
        {
            request.setAttribute( "fromThird", Boolean.TRUE );

            request.setAttribute( "type", type );

            request.setAttribute( "openId", openId );

            request.setAttribute( "memberName", phoneNumber );

            //request.setAttribute( "direct", "true" );

            return ServletUtil.forward( "/member/wxMemberLogin.do" );
        }
        else if( "third_direct".equals( status ) )
        {
            Map returnParam = new HashMap();

            returnParam.put( "type", type );

            returnParam.put( "openId", openId );

            returnParam.put( "memberName", phoneNumber );

            //return ServletUtil.redirect( site.getSiteUrl() + "m_third_reg_direct.jsp", returnParam );
        }
        else if( "relate_reg_success".equals( status ) )
        {
            
            request.setAttribute( "fromReg", Boolean.TRUE );

            request.setAttribute( "memberName", phoneNumber );
            request.setAttribute( "password", password );
            
            request.setAttribute( "openId", openId );

            return ServletUtil.forward( "/member/wxMemberLogin.do" );
        }
        else if( "relate_success".equals( status ) )
        {
            request.setAttribute( "fromThird", Boolean.TRUE );

            request.setAttribute( "type", type );

            request.setAttribute( "openId", openId );

            request.setAttribute( "memberName", phoneNumber );

            return ServletUtil.forward( "/member/wxMemberLogin.do" );
        }

        return status;
    }
}
