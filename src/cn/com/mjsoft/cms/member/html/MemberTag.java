package cn.com.mjsoft.cms.member.html;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.cluster.adapter.ClusterSeriMapAdapter;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.filter.SecuritySessionDisposeFiletr;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.handler.view.DefaultResultHandler;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class MemberTag extends AbstractIteratorTag
{

    private static final long serialVersionUID = 1L;

    private static MemberService memberService = MemberService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    private static DefaultResultHandler resultHandler = new DefaultResultHandler();

    private String page = "false";

    private String pageSize = "11";

    private String roleId = "";

    private String loginMode = "false";

    private String jumpPage = "";// 当loginMode为true时，若不是会员登录，当前页将跳转

    private String actInfo = "";// cmt:msg:gb:info

    protected void initTag()
    {
        if( "true".equals( loginMode ) )
        {
            setToSingleMode();
        }
    }

    protected List returnObjectList()
    {
        if( !"".equals( roleId ) )
        {
            int pn = StringUtil.getIntValue( this.pageContext.getRequest().getParameter( "pn" ), 1 );

            if( !"true".equals( page ) )
            {
                pn = 1;
            }

            Long rId = Long.valueOf( StringUtil.getLongValue( roleId, -1 ) );

            Long count = memberService.retrieveMemeberCountByRoleId( rId );

            Page pageInfo = new Page( StringUtil.getIntValue( pageSize, 10 ), count.intValue(), pn );

            this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );

            // String queryCod = "page=" + page + "&pageSize=" + pageSize;

            ContentClassBean classBean = ( ContentClassBean ) this.pageContext
                .getAttribute( "Class" );

            if( classBean != null )
            {
                // 静态分页
                publishService.htmlTagPage( this.pageContext, null, classBean.getClassId(),
                    classBean, classBean.getClassId(), pageInfo, page, "" );
            }

            return memberService.retrieveMemeberByRoleId( rId, Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );
        }
        else
        {
            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            int pn = StringUtil.getIntValue( this.pageContext.getRequest().getParameter( "pn" ), 1 );

            if( !"true".equals( page ) )
            {
                pn = 1;
            }

            List result = null;

            Long count = memberService.retrieveMemeberCount( site.getSiteId() );

            Page pageInfo = new Page( StringUtil.getIntValue( pageSize, 10 ), count.intValue(), pn );

            result = memberService.retrieveMemeberList( site.getSiteId(), Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );

            this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );

            return result;
        }
    }

    protected String returnPutValueName()
    {
        return "Member";
    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected Object returnSingleObject()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( site == null || site.getSiteId().longValue() < 0 )
        {
            site = ( SiteGroupBean ) this.pageContext.getRequest().getAttribute( "SiteObj" );

            if( site == null )
            {
                site = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                        .getRequest() );
            }
        }

        Map result = null;

        if( StringUtil.isStringNotNull( this.getId() ) )
        {

            Long id = Long.valueOf( StringUtil.getLongValue( this.getId(), -1 ) );

            result = memberService.retrieveSingleMemberAndExtInfo( id, site.getExtMemberModelId() );
        }
        else if( "true".equals( loginMode ) )
        {
            HttpServletRequest request = ( ( HttpServletRequest ) this.pageContext.getRequest() );

            Long sysTokenUserId = ( Long ) SystemConfiguration.getInstance().getSystemConfig()
                .getTokenSecurityCheckBehavior().operation( request, null );

            // 获取安全对象从 token容器中
            SecuritySession securitySession = null;

            if( sysTokenUserId != null )
            {
                String innerEToken = SecuritySessionKeeper.getETokenByUserId( sysTokenUserId );

                Object sessionObj = SecuritySessionKeeper
                    .getSecSessionByUserId( SecuritySessionKeeper
                        .getUserIdBySecEToken( innerEToken ) );

                if( sessionObj != null && !( ( SecuritySession ) sessionObj ).isManager() )
                {

                    Auth auth = ( ( SecuritySession ) sessionObj ).getAuth();

                    if( auth != null )
                    {
                        ClusterSeriMapAdapter coreSysMangerActMap = SecuritySessionKeeper
                            .getMemberLoginActTimeMap();

                        int actSec = DateAndTimeUtil.getSecInterval( new Date( DateAndTimeUtil
                            .clusterTimeMillis() ), ( Date ) coreSysMangerActMap.get( auth
                            .getIdentity() ) );

                        if( actSec > site.getMemberExpire().intValue() )
                        {
                            // 超过限定过期时间,注销
                            SecuritySessionKeeper.invalidCurrentUserSessionContext( request );

                            coreSysMangerActMap.remove( auth.getIdentity() );

                            if( StringUtil.isStringNotNull( jumpPage ) )
                            {
                                // 未登录跳转
                                this.setSkipPage();

                                HttpServletResponse response = ( HttpServletResponse ) pageContext
                                    .getResponse();

                                resultHandler.resolveCustomDirectResult( site.getSiteUrl()
                                    + jumpPage, request, response, true, null );

                                return null;
                            }

                            SecuritySessionKeeper.romoveToken( ( Long ) auth.getIdentity() );
                        }
                        else
                        {
                            securitySession = ( SecuritySession ) sessionObj;

                            coreSysMangerActMap.put( auth.getIdentity(), new Date( DateAndTimeUtil
                                .clusterTimeMillis() ) );

                            pageContext.setAttribute( "SecSign", SecuritySessionKeeper
                                .getETokenByUserId( ( Long ) auth.getIdentity() ) );
                        }
                    }

                }

            }
            else
            {

                HttpSession httpSession = request.getSession( false );

                if( httpSession != null )
                {
                    Object sessionObj = httpSession
                        .getAttribute( SecuritySessionDisposeFiletr.SECURITY_SESSION_FLAG );

                    if( sessionObj != null && !( ( SecuritySession ) sessionObj ).isManager() )
                    {
                        Auth auth = ( ( SecuritySession ) sessionObj ).getAuth();

                        if( auth != null )
                        {
                            ClusterSeriMapAdapter coreSysMangerActMap = SecuritySessionKeeper
                                .getMemberLoginActTimeMap();

                            int actSec = DateAndTimeUtil.getSecInterval( new Date( DateAndTimeUtil
                                .clusterTimeMillis() ), ( Date ) coreSysMangerActMap.get( auth
                                .getIdentity() ) );

                            if( actSec > site.getMemberExpire().intValue() )
                            {
                                // 超过限定过期时间,注销
                                SecuritySessionKeeper.invalidCurrentUserSessionContext( request );

                                coreSysMangerActMap.remove( auth.getIdentity() );

                                if( StringUtil.isStringNotNull( jumpPage ) )
                                {
                                    // 未登录跳转
                                    this.setSkipPage();

                                    HttpServletResponse response = ( HttpServletResponse ) pageContext
                                        .getResponse();

                                    resultHandler.resolveCustomDirectResult( site.getSiteUrl()
                                        + jumpPage, request, response, true, null );

                                    return null;
                                }

                                SecuritySessionKeeper.romoveToken( ( Long ) auth.getIdentity() );
                            }
                            else
                            {
                                coreSysMangerActMap.put( auth.getIdentity(), new Date(
                                    DateAndTimeUtil.clusterTimeMillis() ) );

                                pageContext.setAttribute( "SecSign", SecuritySessionKeeper
                                    .getETokenByUserId( ( Long ) auth.getIdentity() ) );
                            }
                        }

                    }
                }
            }

            SecuritySession session = SecuritySessionKeeper.getSecuritySession();

            if( sysTokenUserId != null )
            {
                session = securitySession;
            }

            if( session != null && session.getAuth() != null && !session.isManager()
                && session.getMember() != null )
            {
                result = memberService.retrieveSingleMemberAndExtInfo( ( Long ) session.getAuth()
                    .getIdentity(), site.getExtMemberModelId() );
            }
            else
            {
                if( StringUtil.isStringNotNull( jumpPage ) )
                {
                    // 未登录跳转
                    this.setSkipPage();

                    HttpServletResponse response = ( HttpServletResponse ) pageContext
                        .getResponse();

                    resultHandler.resolveCustomDirectResult( site.getSiteUrl() + jumpPage, request,
                        response, true, null );

                    return null;
                }
            }
        }

        if( result == null )
        {
            return null;
        }

        // 附加会员信息（如会员参与交互统计等信息获取）

        Long count = null;

        if( actInfo.indexOf( "cmt" ) != -1 )
        {
            // 评论总数

            count = memberService.retrieveMemberActInfoCount( result, 1 );

            result.put( "commCount", count );
        }

        if( actInfo.indexOf( "" ) != -1 )
        {
            // 留言总数
            count = memberService.retrieveMemberActInfoCount( result, 3 );

            result.put( "gbCount", count );
        }

        if( actInfo.indexOf( "msg" ) != -1 )
        {
            // 消息总数
            count = memberService.retrieveMemberActInfoCount( result, 2 );

            result.put( "msgCount", count );
        }

        if( actInfo.indexOf( "info" ) != -1 )
        {
            // 投稿总数
            count = memberService.retrieveMemberActInfoCount( result, 4 );

            result.put( "infoCount", count );
        }

        return result;
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
    }

    public void setRoleId( String roleId )
    {
        this.roleId = roleId;
    }

    public void setPage( String page )
    {
        this.page = page;
    }

    public void setPageSize( String pageSize )
    {
        this.pageSize = pageSize;
    }

    public void setLoginMode( String login )
    {
        this.loginMode = login;
    }

    public void setJumpPage( String jumpPage )
    {
        this.jumpPage = jumpPage;
    }

    public void setActInfo( String actInfo )
    {
        this.actInfo = actInfo;
    }

    public static boolean checkMemberSessionExpired( HttpServletRequest request, Object sessionObj,
        SiteGroupBean site )
    {
        boolean expired = false;

        if( sessionObj != null && !( ( SecuritySession ) sessionObj ).isManager() )
        {

            Auth auth = ( ( SecuritySession ) sessionObj ).getAuth();

            if( auth != null )
            {
                ClusterSeriMapAdapter coreSysMangerActMap = SecuritySessionKeeper
                    .getMemberLoginActTimeMap();

                int actSec = DateAndTimeUtil.getSecInterval( new Date( DateAndTimeUtil
                    .clusterTimeMillis() ), ( Date ) coreSysMangerActMap.get( auth.getIdentity() ) );

                if( actSec > site.getMemberExpire().intValue() )
                {
                    // 超过限定过期时间,注销
                    SecuritySessionKeeper.invalidCurrentUserSessionContext( request );

                    coreSysMangerActMap.remove( auth.getIdentity() );

                    // 未登录跳转
                    expired = true;

                    SecuritySessionKeeper.romoveToken( ( Long ) auth.getIdentity() );
                }
                else
                {

                    coreSysMangerActMap.put( auth.getIdentity(), new Date( DateAndTimeUtil
                        .clusterTimeMillis() ) );

                    // pageContext.setAttribute( "SecSign",
                    // SecuritySessionKeeper
                    // .getETokenByUserId( ( Long ) auth.getIdentity() ) );
                }

                return expired;

            }

        }

        return expired;
    }

}
