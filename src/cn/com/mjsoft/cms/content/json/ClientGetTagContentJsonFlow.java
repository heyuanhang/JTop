package cn.com.mjsoft.cms.content.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.channel.bean.TagWordBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;

import com.alibaba.fastjson.JSON;

public class ClientGetTagContentJsonFlow extends ApiFlowDisposBaseFlow
{
    private Logger log = Logger.getLogger( ClientContentJsonFlow.class );

    private static ClusterCacheAdapter queryParam = new ClusterCacheAdapter( 21000,
        "clientGetTagContentJsonFlow.queryParam" );

    private ContentService contentService = ContentService.getInstance();

    private ChannelService channelService = ChannelService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
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

        String tagId = ( String ) params.get( "tagId" );

        if( StringUtil.isStringNull( tagId ) )
        {
            tagId = "-1";
        }

        String tagName = ( String ) params.get( "tagName" );

        if( StringUtil.isStringNull( tagName ) )
        {
            tagName = "";
        }

        tagName = SystemSafeCharUtil.decodeFromWeb( tagName );

        String order = ( String ) params.get( "order" );// 排序字段

        if( StringUtil.isStringNull( order ) )
        {
            order = "down";
        }

        String page = ( String ) params.get( "page" );// 分页模式

        if( StringUtil.isStringNull( page ) )
        {
            page = "false";
        }

        String size = ( String ) params.get( "size" );// 每页大小

        if( StringUtil.isStringNull( size ) )
        {
            size = "100";
        }

        String siteId = ( String ) params.get( "siteId" );// 指定站点ID,表单模式使用

        if( StringUtil.isStringNull( siteId ) )
        {
            siteId = "-1";
        }

        String orderFlag = "desc";

        if( "up".equals( order ) )
        {
            orderFlag = "asc";
        }

        List result = null;

        Long tagIdVar = Long.valueOf( -1 );

        if( "-1".equals( tagId ) && !"".equals( tagName ) )
        {
            TagWordBean twb = channelService.retrieveSingleTagWordBeanByTagName( tagName );

            if( twb != null )
            {
                tagIdVar = twb.getTagId();
            }
        }
        else
        {
            tagIdVar = Long.valueOf( StringUtil.getLongValue( tagId, -1 ) );
        }

        int pageNum = StringUtil.getIntValue( request.getParameter( "pn" ), 1 );

        if( !"true".equals( page ) )
        {
            pageNum = 1;
        }

        int pageSize = StringUtil.getIntValue( size, 15 );

        /**
         * 下拉分页
         */
        int preEndPos = 0;

        int end = 0;

        if( "true".equals( pull ) )
        {
            // 下拉必定为分页
            pageNum = 1;

            String key = siteId + ":" + tagId + ":" + tagName + ":" + order;

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

        Page pageInfo = null;

        Long count = null;

        count = contentService.retrieveTagRaleteContentByTagId( tagIdVar );

        pageInfo = new Page( pageSize, count.intValue(), pageNum );

        result = contentService.retrieveTagRaleteContentByTagId( tagIdVar, orderFlag, Long
            .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );

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

            resMap.put( "content", resultEnd );

            resMap.put( "size", resultEnd.size() );

            return ( JSON.toJSONString( resMap ) );
        }
        else
        {

            Map resMap = new HashMap();

            // resMap.put( "isEnd", isEnd );

            // resMap.put( "endPos", end + 1 );

            resMap.put( "content", result );

            resMap.put( "pageInfo", pageInfo );

            return ( JSON.toJSONString( resMap ) );
        }

    }

}
