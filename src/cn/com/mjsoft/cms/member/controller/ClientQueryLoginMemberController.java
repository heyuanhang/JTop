package cn.com.mjsoft.cms.member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.member.bean.MemberBean;
import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/member" )
public class ClientQueryLoginMemberController
{
    private static MemberService memberService = MemberService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/memberStatus.do", method = { RequestMethod.POST } )
    public Object memberStatus( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        if( "true".equals( params.get( "thirdUserInfo" ) ) )
        {

            String weiboid = ( String ) request.getSession().getAttribute( "weibo_user_id" );

            String qqId = ( String ) request.getSession().getAttribute( "qq_openid" );

            if( StringUtil.isStringNotNull( qqId ) )
            {
                String qq_user_info = ( String ) request.getSession().getAttribute( "qq_user_info" );

                return qq_user_info;

            }
            else if( StringUtil.isStringNotNull( weiboid ) )
            {
                String weibo_user_info = ( String ) request.getSession().getAttribute(
                    "weibo_user_info" );

                return weibo_user_info;
            }

        }
        else
        {

            SecuritySession session = SecuritySessionKeeper.getSecuritySession();

            if( session == null || session.getAuth() == null || session.isManager()
                || session.getMember() == null )
            {
                return "";
            }
            else
            {
                MemberBean member = ( MemberBean ) session.getMember();

                SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                    .getEntry( member.getSiteId() );

                Map memMap = memberService.retrieveSingleMemberAndExtInfo( member.getMemberId(),
                    site.getExtMemberModelId() );

                // 站点信息
                memMap.put( "loginSiteName", site.getSiteName() );
                memMap.put( "loginSiteId", site.getSiteId() );
                memMap.put( "loginSiteFlag", site.getSiteFlag() );

                return memMap;

            }
        }

        return "";
    }
}
