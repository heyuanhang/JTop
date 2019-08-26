package cn.com.mjsoft.cms.member.json;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.member.bean.MemberBean;
import cn.com.mjsoft.cms.member.html.MemberTag;
import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientQueryMemberInfoJsonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger.getLogger( ClientQueryMemberInfoJsonFlow.class );

    private static ClusterCacheAdapter queryParam = new ClusterCacheAdapter( 21000,
        "clientQueryMemberInfoJsonFlow.queryParam" );

    private static MemberService memberService = MemberService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        HttpServletRequest request = this.getServletFlowContext().getRequest();

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        String loginMode = ( String ) params.get( "loginMode" );

        if( "true".equals( loginMode ) )
        {
            Long sysTokenUserId = ( Long ) SystemConfiguration.getInstance().getSystemConfig()
                .getTokenSecurityCheckBehavior().operation( request, null );

            SecuritySession session = null;

            if( sysTokenUserId != null )
            {
                String innerEToken = SecuritySessionKeeper.getETokenByUserId( sysTokenUserId );

                session = SecuritySessionKeeper.getSecSessionByUserId( SecuritySessionKeeper
                    .getUserIdBySecEToken( innerEToken ) );
            }
            else
            {
                // 接口模式不再使用session模式确认权限，统一为secToken
                // session = SecuritySessionKeeper.getSecuritySession();
            }

            if( session == null || session.getAuth() == null || session.isManager()
                || session.getMember() == null )
            {
                return ( JSON.toJSONString( "{not_login:true}" ) );
            }
            else
            {
                MemberBean member = ( MemberBean ) session.getMember();

                SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                    .getEntry( member.getSiteId() );

                // 检查是否session已过期
                if( MemberTag.checkMemberSessionExpired( request, session, site ) )
                {
                    return ( JSON.toJSONString( "{empty:expired}" ) );
                }

                Map memMap = memberService.retrieveSingleMemberAndExtInfo( member.getMemberId(),
                    site.getExtMemberModelId() );

                // 站点信息
                memMap.put( "loginSiteName", site.getSiteName() );
                memMap.put( "loginSiteId", site.getSiteId() );
                memMap.put( "loginSiteFlag", site.getSiteFlag() );

                // 附加会员信息（如会员参与交互统计等信息获取）

                String activeInfo = request.getParameter( "actInfo" );

                Long count = null;

                if( "true".equals( activeInfo ) )
                {

                    // 评论总数

                    count = memberService.retrieveMemberActInfoCount( memMap, 1 );

                    memMap.put( "commCount", count );

                    // 留言总数
                    count = memberService.retrieveMemberActInfoCount( memMap, 3 );

                    memMap.put( "gbCount", count );

                    // 消息总数
                    count = memberService.retrieveMemberActInfoCount( memMap, 2 );

                    memMap.put( "msgCount", count );

                    // 投稿总数
                    count = memberService.retrieveMemberActInfoCount( memMap, 4 );

                    memMap.put( "infoCount", count );
                }

                return ( JSON.toJSONString( memMap ) );

            }
        }
        else
        {
            // 普通按照ID获取会员基础信息

            String siteId = ( String ) params.get( "siteId" );// 指定站点ID,表单模式使用

            if( StringUtil.isStringNull( siteId ) )
            {
                siteId = "-1";
            }

            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( StringUtil.getLongValue( siteId, -1 ) );

            if( site == null )
            {
                site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );
            }

            if( site == null )
            {
                return ( JSON.toJSONString( "{site-empty:true}" ) );
            }

            Long memberId = StringUtil.getLongValue( ( String ) params.get( "id" ), -1 );

            Map memInfo = memberService.retrieveSingleMemberAndExtInfo( memberId, site
                .getExtMemberModelId() );
            
              return ( JSON.toJSONString( memInfo ) );
        }
        // else
        // {
        //
        // // 是否有第一页标签的最后的分页数据
        // String pull = StringUtil.notNull( ( String ) params.get( "pull" ) );
        //
        // String ep = StringUtil.notNull( ( String ) params.get( "ep" ) );
        //
        // if( StringUtil.isStringNull( ep ) )
        // {
        // ep = "1";
        // }
        //
        // // 下拉大小
        // String nz = StringUtil.notNull( ( String ) params.get( "nz" ) );
        //
        // if( StringUtil.isStringNull( nz ) )
        // {
        // nz = "0";
        // }
        //
        // String page = ( String ) params.get( "page" );
        //
        // if( StringUtil.isStringNull( page ) )
        // {
        // page = "false";
        // }
        //
        // String pageSize = ( String ) params.get( "pageSize" );
        //
        // if( StringUtil.isStringNull( pageSize ) )
        // {
        // pageSize = "12";
        // }
        //
        // String roleId = StringUtil.notNull( ( String ) params.get( "roleId" )
        // );
        //
        // int pn = StringUtil.getIntValue( request.getParameter( "pn" ), 1 );
        //
        // if( !"true".equals( page ) )
        // {
        // pn = 1;
        // }
        //
        // String siteId = ( String ) params.get( "siteId" );// 指定站点ID,表单模式使用
        //
        // if( StringUtil.isStringNull( siteId ) )
        // {
        // siteId = "-1";
        // }
        //
        // SiteGroupBean site = ( SiteGroupBean )
        // InitSiteGroupInfoBehavior.siteGroupIdInfoCache
        // .getEntry( StringUtil.getLongValue( siteId, -1 ) );
        //
        // if( site == null )
        // {
        // site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );
        // }
        //
        // if( site == null && "".equals( roleId ) )
        // {
        // return ( JSON.toJSONString( "{empty:true}" ) );
        // }
        //
        // Page pageInfo = null;
        //
        // /**
        // * 下拉分页
        // */
        // int preEndPos = 0;
        //
        // int end = 0;
        //
        // if( "true".equals( pull ) )
        // {
        // // 下拉必定为分页
        // pn = 1;
        //
        // String key = roleId + ":" + siteId;
        //
        // if( queryParam.cacheCurrentSize() > 20000 )
        // {
        // queryParam.clearAllEntry();
        // }
        //
        // Integer currPageSize = ( Integer ) queryParam.getEntry( key );
        //
        // if( currPageSize == null )
        // {
        // currPageSize = Integer.valueOf( 500 );
        //
        // queryParam.putEntry( key, currPageSize );
        // }
        //
        // preEndPos = StringUtil.getIntValue( ep, 1 ) - 1; // 如7
        // // 则为0~6
        //
        // int nextSize = StringUtil.getIntValue( nz, 3 );
        //
        // end = preEndPos + nextSize;// 位6的接下来3笔数据,为7~9位
        //
        // int limitFlag = end + 1;
        //
        // String pageSizeV = currPageSize.toString();//
        // 2000作为首次缓存筏值,下拉操作只允许最大1W数据
        //
        // if( limitFlag >= currPageSize.intValue() )
        // {
        // currPageSize = currPageSize.intValue() + 500;
        //
        // queryParam.putEntry( key, currPageSize );
        //
        // pageSizeV = currPageSize.toString();
        //
        // }
        //
        // pageSize = StringUtil.getIntValue( pageSizeV, 15 ) + "";
        //
        // }
        //
        // List result = null;
        //
        // if( !"".equals( roleId ) )
        // {
        // Long rId = Long.valueOf( StringUtil.getLongValue( roleId, -1 ) );
        //
        // Long count = memberService.retrieveMemeberCountByRoleId( rId );
        //
        // pageInfo = new Page( StringUtil.getIntValue( pageSize, 10 ),
        // count.intValue(), pn );
        //
        // result = memberService.retrieveMemeberByRoleId( rId, Long.valueOf(
        // pageInfo
        // .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );
        // }
        // else
        // {
        // Long count = memberService.retrieveMemeberCount( site.getSiteId() );
        //
        // pageInfo = new Page( StringUtil.getIntValue( pageSize, 10 ),
        // count.intValue(), pn );
        //
        // result = memberService
        // .retrieveMemeberList( site.getSiteId(), Long
        // .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageInfo
        // .getPageSize() ) );
        // }
        //
        // if( "true".equals( pull ) )
        // {
        // // 处理下拉请求
        //
        // List resultEnd = new ArrayList();
        //
        // if( result.isEmpty() )
        // {
        // Map resMap = new HashMap();
        //
        // resMap.put( "isEnd", true );
        //
        // resMap.put( "Member", "" );
        //
        // resMap.put( "size", Integer.valueOf( 0 ) );
        //
        // return ( JSON.toJSONString( resMap ) );
        // }
        //
        // boolean isEnd = false;
        //
        // for ( int i = preEndPos + 1; i <= end; i++ )
        // {
        // if( i >= result.size() )
        // {
        // isEnd = true;
        // break;
        // }
        //
        // resultEnd.add( result.get( i ) );
        // }
        //
        // Map resMap = new HashMap();
        //
        // resMap.put( "isEnd", isEnd );
        //
        // resMap.put( "endPos", end + 1 );
        //
        // resMap.put( "Member", resultEnd );
        //
        // resMap.put( "size", resultEnd.size() );
        //
        // return ( JSON.toJSONString( resMap ) );
        // }
        // else
        // {
        //
        // Map jsonRes = new HashMap( 2 );
        //
        // if( pageInfo != null )
        // {
        // jsonRes.put( "pageInfo", pageInfo );
        // }
        //
        // jsonRes.put( "Member", result );
        //
        // return ( JSON.toJSONString( jsonRes ) );
        // }
        // }

    }
}
