package cn.com.mjsoft.cms.guestbook.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.guestbook.service.GuestbookService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientGuestbookInfoJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger.getLogger( ClientGuestbookInfoJSonFlow.class );

    private static ClusterCacheAdapter queryParam = new ClusterCacheAdapter( 21000,
        "clientGuestbookInfoJSonFlow.queryParam" );

    private static GuestbookService gbService = GuestbookService.getInstance();

    @Override
    protected String executeBiz() throws Exception
    {
        HttpServletRequest request = this.getServletFlowContext().getRequest();

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        // 是否有第一页标签的最后的分页数据
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

        String gbId = StringUtil.notNull( ( String ) params.get( "gbId" ) );

        String configFlag = StringUtil.notNull( ( String ) params.get( "configFlag" ) );

        String isReply = StringUtil.notNull( ( String ) params.get( "isReply" ) );

        String isCensor = StringUtil.notNull( ( String ) params.get( "isCensor" ) );

        String isOpen = StringUtil.notNull( ( String ) params.get( "isOpen" ) );

        String page = StringUtil.notNull( ( String ) params.get( "page" ) );

        if( "".equals( page ) )
        {
            page = "false";
        }

        int pageSize = StringUtil.getIntValue( StringUtil.notNull( ( String ) params
            .get( "pageSize" ) ), 15 );

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
            return responseAjaxTextMessage( JSON.toJSONString( "{site-empty:true}" ) );
        }

        List result = Collections.EMPTY_LIST;

        if( !"".equals( gbId ) )
        {

            return responseAjaxTextMessage( JSON.toJSONString( gbService
                .retrieveSingleGuestbookInfoMapByGbId( Long.valueOf( StringUtil.getLongValue( gbId,
                    -1 ) ) ) ) );
        }
        else
        {
            int pn = StringUtil.getIntValue( request.getParameter( "pn" ), 1 );

            if( !"true".equals( page ) )
            {
                pn = 1;
            }

            /**
             * 下拉分页
             */
            int preEndPos = 0;

            int end = 0;

            if( "true".equals( pull ) )
            {
                // 下拉必定为分页
                pn = 1;

                String key = siteId + ":" + configFlag + ":" + isReply + ":" + isCensor + ":"
                    + isOpen;

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

                pageSize = StringUtil.getIntValue( pageSizeV, 15 );

            }

            Long count = gbService.retrieveGuestbookMainInfoCount( configFlag, isReply, isCensor,
                isOpen );

            Page pageInfo = new Page( pageSize, count.intValue(), pn );

            result = gbService.retrieveGuestbookMainInfoMapList( configFlag, isReply, isCensor,
                isOpen, Long.valueOf( pageInfo.getFirstResult() ), pageInfo.getPageSize() );

            if( result.isEmpty() )
            {
                return responseAjaxTextMessage( JSON.toJSONString( "{empty:true}" ) );
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

                    return responseAjaxTextMessage( JSON.toJSONString( resMap ) );
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

                resMap.put( "GbInfo", resultEnd );

                resMap.put( "size", resultEnd.size() );

                return responseAjaxTextMessage( JSON.toJSONString( resMap ) );
            }
            else
            {

                Map jsonRes = new HashMap( 2 );

                jsonRes.put( "pageInfo", pageInfo );

                jsonRes.put( "GbInfo", result );

                return responseAjaxTextMessage( JSON.toJSONString( jsonRes ) );
            }

        }

    }
}
