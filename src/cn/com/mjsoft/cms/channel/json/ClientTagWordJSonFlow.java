package cn.com.mjsoft.cms.channel.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.cluster.adapter.ClusterMapAdapter;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientTagWordJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger.getLogger( ClientTagWordJSonFlow.class );

    private static ClusterCacheAdapter queryParam = new ClusterCacheAdapter( 21000,
        "clientTagWordJSonFlow.queryParam" );

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

        String tagId = ( String ) params.get( "id" );

        if( StringUtil.isStringNotNull( tagId ) )
        {
            long id = StringUtil.getLongValue( tagId, -1 );

            if( id < 0 )
            {
                return ( JSON.toJSONString( "{empty:true}" ) );
            }

            return ( JSON.toJSONString( channelService.retrieveSingleTagWordBeanByTagId( id ) ) );
        }
        else
        {
            String siteId = ( String ) params.get( "siteId" );

            if( StringUtil.isStringNull( siteId ) )
            {
                siteId = "-1";
            }

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

            List result = null;

            Long tId = Long.valueOf( StringUtil
                .getLongValue( ( String ) params.get( "typeId" ), -1 ) );

            int pageNum = StringUtil.getIntValue( ( String ) params.get( "pn" ), 1 );

            if( !"true".equals( ( String ) params.get( "page" ) ) )
            {
                pageNum = 1;
            }

            int pageSize = StringUtil.getIntValue( ( String ) params.get( "size" ), 15 );

            Page pageInfo = null;

            Long count = null;

            String fc = ( String ) params.get( "fc" );

            if( StringUtil.isStringNull( fc ) )
            {
                fc = "";
            }

            String order = ( String ) params.get( "order" );

            if( StringUtil.isStringNull( order ) )
            {
                order = "";
            }

            String typeFlag = ( String ) params.get( "typeFlag" );

            if( StringUtil.isStringNull( typeFlag ) )
            {
                typeFlag = "";
            }

            /**
             * 下拉分页
             */
            int preEndPos = 0;

            int end = 0;

            if( "true".equals( pull ) )
            {
                // 下拉必定为分页
                pageNum = 1;

                String key = siteId + ":" + tId + ":" + fc + ":" + order + ":" + typeFlag;

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

            if( tId.longValue() < 1 && "".equals( ( String ) params.get( "typeFlag" ) ) )// 取所有
            {

                if( StringUtil.isStringNotNull( fc ) )
                {
                    count = channelService.retrieveTagWordCountBySiteId( siteBean.getSiteId(), fc
                        .toLowerCase() );

                    pageInfo = new Page( pageSize, count.intValue(), pageNum );

                    result = channelService.retrieveTagWordBeanBySiteId( siteBean.getSiteId(), fc,
                        Long.valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ),
                        order );
                }
                else
                {
                    count = channelService.retrieveTagWordCountBySiteId( siteBean.getSiteId() );

                    pageInfo = new Page( pageSize, count.intValue(), pageNum );

                    result = channelService.retrieveTagWordBeanBySiteId( siteBean.getSiteId(), Long
                        .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ), order );
                }
            }
            else
            {
                if( tId.longValue() < 1 && tId.longValue() != -9999 )
                {
                    Map tagType = channelService.retrieveTagTypeByFlag( typeFlag );

                    tId = ( Long ) tagType.get( "tagTypeId" );
                }

                if( StringUtil.isStringNotNull( fc ) )
                {
                    count = channelService.retrieveTagWordCountBySiteId( siteBean.getSiteId(), fc,
                        tId );

                    pageInfo = new Page( pageSize, count.intValue(), pageNum );

                    result = channelService.retrieveTagWordBeanBySiteId( siteBean.getSiteId(), fc,
                        tId, Long.valueOf( pageInfo.getFirstResult() ),
                        Integer.valueOf( pageSize ), order );
                }
                else
                {
                    count = channelService.retrieveTagWordCountBySiteId( siteBean.getSiteId(), tId );

                    pageInfo = new Page( pageSize, count.intValue(), pageNum );

                    result = channelService.retrieveTagWordBeanBySiteId( siteBean.getSiteId(), tId,
                        Long.valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ),
                        order );
                }

            }

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

                resMap.put( "TagWord", resultEnd );

                resMap.put( "size", resultEnd.size() );

                return ( JSON.toJSONString( resMap ) );
            }
            else
            {

                Map json = new HashMap( 2 );

                json.put( "TagWord", result );
                json.put( "PageInfo", pageInfo );

                return ( JSON.toJSONString( json ) );
            }

        }

    }
}
