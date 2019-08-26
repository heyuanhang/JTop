package cn.com.mjsoft.cms.comment.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.comment.bean.CommentInfoBean;
import cn.com.mjsoft.cms.comment.service.CommentService;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;

import com.alibaba.fastjson.JSON;

public class ClientContentCommentJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger.getLogger( ClientContentCommentJSonFlow.class );

    private static ClusterCacheAdapter queryParam = new ClusterCacheAdapter( 21000,
        "clientContentCommentJSonFlow.queryParam" );

    private static CommentService commentService = CommentService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        HttpServletRequest request = this.getServletFlowContext().getRequest();

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        String pull = StringUtil.notNull( ( String ) params.get( "pull" ) );

        String ep = StringUtil.notNull( ( String ) params.get( "ep" ) );

        if( StringUtil.isStringNull( ep ) )
        {
            ep = "1";
        }

        // 下拉大小
        String nz = StringUtil.notNull( ( String ) params.get( "nz" ) );

        if( StringUtil.isStringNull( nz ) )
        {
            nz = "0";
        }

        String contentId = ( String ) params.get( "contentId" );

        String commentId = ( String ) params.get( "commentId" );

        String userName = StringUtil.notNull( ( String ) params.get( "userName" ) );

        String page = ( String ) params.get( "page" );

        String size = ( String ) params.get( "size" );

        String siteId = ( String ) params.get( "siteId" );// 指定站点ID,表单模式使用

        if( StringUtil.isStringNull( siteId ) )
        {
            siteId = "-1";
        }

        Long contentIdVar = Long.valueOf( StringUtil.getLongValue( contentId, -1 ) );

        Long commentIdVar = Long.valueOf( StringUtil.getLongValue( commentId, -1 ) );

        int pageNum = StringUtil.getIntValue( ( String ) request.getParameter( "pn" ), 1 );

        if( !"true".equals( page ) )
        {
            pageNum = 1;
        }

        int sizeVar = StringUtil.getIntValue( size, 15 );

        List result = null;

        Long count = null;

        Page pageInfo = null;

        if( contentIdVar.longValue() > 0 )
        {
            count = commentService.retrieveCommentCount( null, CommentService.CONTENT_MODE,
                contentIdVar, Integer.valueOf( 1 ) );

            /**
             * 1.组装page对象
             */
            pageInfo = new Page( sizeVar, count.intValue(), pageNum );

            result = commentService.retrieveCommentBeanListByContentId( contentIdVar, Long
                .valueOf( pageInfo.getFirstResult() ), sizeVar, Integer.valueOf( 1 ) );
        }
        else if( commentIdVar.longValue() > 0 )
        {
            CommentInfoBean cib = commentService.retrieveSingeleCommentBean( commentIdVar );

            if( cib == null )
            {
                return ( JSON.toJSONString( "{empty:true}" ) );
            }

            return ( JSON.toJSONString( cib ) );

        }
        else if( !"".equals( userName ) )
        {
            SiteGroupBean siteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( StringUtil.getLongValue( siteId, -1 ) );

            if( siteBean == null )
            {
                siteBean = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );
            }

            if( siteBean == null )
            {
                return ( JSON.toJSONString( "{empty:true}" ) );
            }

            userName = SystemSafeCharUtil.decodeFromWeb( userName );

            /**
             * 下拉分页
             */
            int preEndPos = 0;

            int end = 0;

            if( "true".equals( pull ) )
            {
                // 下拉必定为分页
                pageNum = 1;

                String key = siteId + ":" + userName;

                if( queryParam.cacheCurrentSize() > 20000 )
                {
                    queryParam.clearAllEntry();
                }

                Integer currPageSize = ( Integer ) queryParam.getEntry( key );

                if( currPageSize == null )
                {
                    currPageSize = Integer.valueOf( 500 );

                    queryParam.putEntry( key, currPageSize );
                }

                preEndPos = StringUtil.getIntValue( ep, 1 ) - 1; // 如7
                // 则为0~6

                int nextSize = StringUtil.getIntValue( nz, 3 );

                end = preEndPos + nextSize;// 位6的接下来3笔数据,为7~9位

                int limitFlag = end + 1;

                String pageSizeV = currPageSize.toString();// 2000作为首次缓存筏值,下拉操作只允许最大1W数据

                if( limitFlag >= currPageSize.intValue() )
                {
                    currPageSize = currPageSize.intValue() + 500;

                    queryParam.putEntry( key, currPageSize );

                    pageSizeV = currPageSize.toString();

                }

                sizeVar = StringUtil.getIntValue( pageSizeV, 15 );

            }

            count = commentService.retrieveCommentCountByUserName( siteBean.getSiteId(), userName );

            /**
             * 1.组装page对象
             */
            pageInfo = new Page( sizeVar, count.intValue(), pageNum );

            result = commentService.retrieveAllCommentBeanListByUserName( siteBean.getSiteId(),
                userName, Long.valueOf( pageInfo.getFirstResult() ), sizeVar );

            if( result.isEmpty() )
            {
                return ( JSON.toJSONString( "{empty:true}" ) );
            }

            if( "true".equals( pull ) )
            {
                // 处理下拉请求

                List resultEnd = new ArrayList();

                if( result.isEmpty() )
                {
                    Map resMap = new HashMap();

                    resMap.put( "isEnd", true );

                    resMap.put( "content", "" );

                    resMap.put( "size", Integer.valueOf( 0 ) );

                    return ( JSON.toJSONString( resMap ) );
                }

                boolean isEnd = false;

                for ( int i = preEndPos + 1; i <= end; i++ )
                {
                    if( i >= result.size() )
                    {
                        isEnd = true;
                        break;
                    }

                    resultEnd.add( result.get( i ) );
                }

                Map resMap = new HashMap();

                resMap.put( "isEnd", isEnd );

                resMap.put( "endPos", end + 1 );

                resMap.put( "comment", resultEnd );

                resMap.put( "size", resultEnd.size() );

                return ( JSON.toJSONString( resMap ) );
            }
            else
            {

                Map jsonRes = new HashMap( 2 );

                jsonRes.put( "pageInfo", pageInfo );

                jsonRes.put( "comment", result );

                return ( JSON.toJSONString( jsonRes ) );
            }
        }

        return ( JSON.toJSONString( "{empty:true}" ) );
    }
}
