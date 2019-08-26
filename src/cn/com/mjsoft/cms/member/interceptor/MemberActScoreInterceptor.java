package cn.com.mjsoft.cms.member.interceptor;

import java.util.ArrayList;
import java.util.List;
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
import cn.com.mjsoft.framework.util.StringUtil;

/**
 * Spring MVC 会员相关拦截器
 * 
 * @author MJSoft
 * 
 */
@SuppressWarnings( "unchecked" )
public class MemberActScoreInterceptor implements HandlerInterceptor
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
            Object memberIdInfo = request.getAttribute( "memberId" );

            // 处理的目标事件个数，如删除了5条记录则为5
            Integer eventCount = Integer.valueOf( StringUtil.getIntValue( ( String ) request
                .getAttribute( "eventCount" ), 1 ) );

            // 获取登陆ID

            MemberBean memberBean = null;

            if( memberIdInfo == null )
            {
                memberBean = ( MemberBean ) SecuritySessionKeeper.getSecuritySession().getMember();

                if( memberBean == null || memberBean.getMemberId().longValue() < 0 )
                {
                    return;
                }

                doMemberScoreChange( memberBean, command, eventCount );
            }
            else
            {
                List memberIdList = null;

                if( memberIdInfo instanceof Long )
                {
                    memberIdList = new ArrayList( 1 );

                    memberIdList.add( memberIdInfo );
                }
                else if( memberIdInfo instanceof List )
                {
                    memberIdList = ( List ) memberIdInfo;
                }

                Long memId = null;

                for ( int i = 0; i < memberIdList.size(); i++ )
                {
                    if( memberIdList.get( i ) instanceof String )
                    {
                        memberBean = memberService
                            .retrieveSingleMemberBean( ( String ) memberIdList.get( i ) );
                    }
                    else
                    {
                        memId = ( Long ) memberIdList.get( i );

                        memberBean = memberService.retrieveSingleMemberBean( memId );
                    }

                    if( memberBean == null || memberBean.getMemberId().longValue() < 0 )
                    {
                        return;
                    }

                    doMemberScoreChange( memberBean, command, eventCount );

                }

            }

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

    public void doMemberScoreChange( MemberBean memberBean, String command, Integer eventCount )
    {
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( memberBean.getSiteId() );

        Map member = memberService.retrieveSingleMemberAndExtInfo( memberBean.getMemberId(), site
            .getExtMemberModelId() );

        if( member.isEmpty() )
        {
            // 会员查询为空,说明会员信息丢失或会员异常登录
            return;
        }

        // 处理积分
        memberService.clientDisposeMemberScore( memberBean.getMemberId(), member, command,
            eventCount );

    }

}
