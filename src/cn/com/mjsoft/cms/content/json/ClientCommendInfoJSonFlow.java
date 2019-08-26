package cn.com.mjsoft.cms.content.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.bean.ContentCommendPushInfoBean;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientCommendInfoJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger.getLogger( ClientCommendInfoJSonFlow.class );

    private static ClusterCacheAdapter queryParam = new ClusterCacheAdapter( 21000,
        "clientCommendInfoJSonFlow.queryParam" );

    private static ContentService contentService = ContentService.getInstance();

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

        String flag = ( String ) params.get( "flag" );

        if( StringUtil.isStringNull( flag ) )
        {
            flag = "";
        }

        String typeId = ( String ) params.get( "typeId" );

        if( StringUtil.isStringNull( typeId ) )
        {
            typeId = "-1";
        }

        String infoId = ( String ) params.get( "infoId" );

        if( StringUtil.isStringNull( infoId ) )
        {
            infoId = "-1";
        }

        // 分页

        String page = ( String ) params.get( "page" );

        if( StringUtil.isStringNull( page ) )
        {
            page = "false";
        }

        String size = ( String ) params.get( "size" );// 每页大小

        if( StringUtil.isStringNull( size ) )
        {
            size = "100";
        }

        List commendContent = null;

        if( !"-1".equals( infoId ) )
        {

            ContentCommendPushInfoBean cpi = contentService
                .retrieveSingleCommendPushInfoByInfoId( Long.valueOf( StringUtil.getLongValue(
                    infoId, -1 ) ) );

            if( cpi == null )
            {
                return ( JSON.toJSONString( "{empty:true}" ) );
            }

            return ( JSON.toJSONString( cpi ) );
        }
        else
        {
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
                return ( JSON.toJSONString( "{empty:true}" ) );
            }

            boolean pageMode = false;

            if( "true".equals( page ) )
            {
                // 注意:当不处于pageMode时,发布行为的任何参数都不需要传递
                pageMode = true;
            }

            int nextPage = StringUtil.getIntValue( request.getParameter( "pn" ), 1 );

            if( !pageMode )
            {
                nextPage = 1;
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
                nextPage = 1;

                String key = siteId + ":" + typeId + ":" + flag;

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

            Integer count = null;

            if( !"-1".equals( typeId ) )
            {
                count = contentService.retrieveAllCommendContentByCommendCountByTypeId( Long
                    .valueOf( StringUtil.getLongValue( typeId, -1 ) ) );
            }
            else
            {
                count = contentService.retrieveAllCommendContentByCommendCountByFlag( flag );
            }

            pageInfo = new Page( pageSize, count.intValue(), nextPage );

            if( !"-1".equals( typeId ) )
            {
                commendContent = contentService
                    .retrieveAllCommendContentByCommendByTypeId( Long.valueOf( StringUtil
                        .getLongValue( typeId, -1 ) ), site.getSiteFlag(), Long.valueOf( pageInfo
                        .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );
            }
            else
            {
                commendContent = contentService.retrieveAllCommendContentByCommendByFlag( flag,
                    site.getSiteFlag(), Integer.valueOf( pageSize ) );
            }

            // 最后位置,目前推荐列表全部发布
            pageInfo.setEndPos( Integer.toString( pageInfo.getPageCount() ) );

            List result = commendContent;

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

                resMap.put( "CommInfo", resultEnd );

                resMap.put( "size", resultEnd.size() );

                return ( JSON.toJSONString( resMap ) );
            }
            else
            {

                Map resMap = new HashMap();

                // resMap.put( "isEnd", isEnd );

                // resMap.put( "endPos", end + 1 );

                resMap.put( "CommInfo", result );

                resMap.put( "pageInfo", pageInfo );

                return ( JSON.toJSONString( resMap ) );
            }

        }

    }

    public String notNull( String taregt )
    {
        String end = taregt;

        if( taregt == null )
        {
            end = "";
        }

        return end;

    }
}
