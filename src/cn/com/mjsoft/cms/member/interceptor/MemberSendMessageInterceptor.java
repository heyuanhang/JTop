package cn.com.mjsoft.cms.member.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.member.bean.MemberBean;
import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;

public class MemberSendMessageInterceptor implements HandlerInterceptor
{
    private static MemberService memberService = MemberService.getInstance();

    public void postHandle( HttpServletRequest request, HttpServletResponse response,
        Object handler, ModelAndView modelAndView ) throws Exception
    {

        HandlerMethod handlerMethod = ( HandlerMethod ) handler;

        String command = handlerMethod.getMethod().getName();

        Boolean flowSuccessFlag = ( Boolean ) request.getAttribute( "successFlag" );

        if( Boolean.TRUE.equals( flowSuccessFlag ) )
        {
            // 获取登陆ID

            if( !( request.getAttribute( "memberId" ) instanceof String ) )
            {
                return;
            }

            Long memberId = ( Long ) request.getAttribute( "memberId" );

            MemberBean memberBean = null;

            if( memberId == null )
            {
                memberBean = ( MemberBean ) SecuritySessionKeeper.getSecuritySession().getMember();
            }
            else
            {
                memberBean = memberService.retrieveSingleMemberBean( memberId );
            }

            if( memberBean == null || memberBean.getMemberId().longValue() < 0 )
            {
                return;
            }

            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( memberBean.getSiteId() );

            Map member = memberService.retrieveSingleMemberAndExtInfo( memberId, site
                .getExtMemberModelId() );

            if( member.isEmpty() )
            {
                // 会员查询为空,说明会员信息丢失或会员异常登录
                return;
            }

            // 事件发生时间
            member.put( "sysEventDT", DateAndTimeUtil.getFormatDate( DateAndTimeUtil
                .clusterTimeMillis(), DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );

            // 生成会员消息
            memberService.clientAddNewMemberMessage( memberId, member, command );

        }

    }

    public boolean preHandle( HttpServletRequest request, HttpServletResponse response,
        Object handler ) throws Exception
    {
        return true;
    }

    public void afterCompletion( HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex ) throws Exception
    {

    }
}
