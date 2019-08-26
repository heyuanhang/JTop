package cn.com.mjsoft.cms.content.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;

import com.alibaba.fastjson.JSON;

public class ClientContentMutiQueryJsonFlow extends ApiFlowDisposBaseFlow
{
    private Logger log = Logger.getLogger( ClientContentJsonFlow.class );

    private static ClusterCacheAdapter queryParam = new ClusterCacheAdapter( 21000,
    "clientContentMutiQueryJsonFlow.queryParam" );

    private static MetaDataService mdService = MetaDataService.getInstance();

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

        String classId = ( String ) params.get( "classId" );// 栏目ID

        if( StringUtil.isStringNull( classId ) )
        {
            classId = "-1";
        }

        String formMode = ( String ) params.get( "formMode" );// 是否表单模式

        if( StringUtil.isStringNull( formMode ) )
        {
            formMode = "false";
        }

        String modelId = ( String ) params.get( "modelId" );// 表单模型ID

        if( StringUtil.isStringNull( modelId ) )
        {
            modelId = "-1";
        }

        String siteId = ( String ) params.get( "siteId" );// 指定站点ID,表单模式使用

        if( StringUtil.isStringNull( siteId ) )
        {
            siteId = "-1";
        }

        String query = ( String ) params.get( "query" );// 查询条件,如:0=<a1(对应整数字段)<=9999,1971-1-1=<b1(对应日期字段)<=2018-18-18

        if( StringUtil.isStringNull( query ) )
        {
            query = "";
        }

        query = SystemSafeCharUtil.decodeFromWeb( SystemSafeCharUtil.decodeFromWeb( query ) );

        log.info( "ClientContentMutiQueryAPI: query = " + query );

        String order = ( String ) params.get( "order" );// 排序字段

        if( StringUtil.isStringNull( order ) )
        {
            order = "";
        }

        String way = ( String ) params.get( "way" );// 排序顺序

        if( StringUtil.isStringNull( way ) )
        {
            way = "";
        }

        String page = ( String ) params.get( "page" );// 分页模式

        if( StringUtil.isStringNull( page ) )
        {
            page = "false";
        }

        // API分页强制为true
        page = "true";

        String size = ( String ) params.get( "size" );// 每页大小

        if( StringUtil.isStringNull( size ) )
        {
            size = "";
        }

        // 处理参数
        String[] cIdType = null;

        if( classId.endsWith( ":" ) )
        {
            cIdType = new String[] { classId, "-9999" };
        }
        else if( classId.indexOf( ":" ) != -1 )
        {
            cIdType = StringUtil.split( classId, ":" );
        }
        else
        {
            cIdType = new String[] { classId, "-1" };
        }

        if( cIdType.length < 2 )
        {
            Map error = new HashMap();

            error.put( "empty", "true" );

            return ( JSON.toJSONString( error ) );
        }

        String mode = cIdType[0];

        boolean defFormMode = false;

        if( "form".equals( mode ) )
        {
            defFormMode = true;
        }

        /**
         * 下拉分页
         */
        int preEndPos = 0;

        int end = 0;

        if( "true".equals( pull ) )
        {
            // 下拉必定为分页
            page = "true";

            String key = siteId + ":" + classId + ":" + formMode + ":" + query + ":" + page + ":"
            + order + ":" + way + ":" + modelId;

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

            String pageSize = currPageSize.toString();// 2000作为首次缓存筏值,下拉操作只允许最大1W数据

            if( limitFlag >= currPageSize.intValue() )
            {
                currPageSize = currPageSize.intValue() + 500;

                queryParam.putEntry( key, currPageSize );

                pageSize = currPageSize.toString();

            }

            size = pageSize;
        }

        query = SystemSafeCharUtil.decodeFromWeb( SystemSafeCharUtil.decodeFromWeb( query ) );

        log.info( "ClientContentMutiQueryTag: query = " + query );

        List sqlParamList = new ArrayList();

        SiteGroupBean siteBean = getSiteObj( StringUtil.getLongValue( siteId, -1 ), request );

        if( siteBean == null )
        {
            Map error = new HashMap();

            error.put( "error", "site_lost" );

            return ( JSON.toJSONString( error ) );
        }

        Map<String, Long> midMap = new HashMap<String, Long>( 2 );

        Page pageInfo = null;

        String targetQuerySql = mdService.retrieveModelMutiQuery( cIdType, siteBean, StringUtil
            .getLongValue( modelId, -1 ), query, order, way, sqlParamList, false, midMap, siteId );

        if( targetQuerySql == null )
        {

            pageInfo = new Page( 1, 0, 0 );

            sqlParamList.add( Long.valueOf( pageInfo.getFirstResult() ) );// 起始位置
            sqlParamList.add( Integer.valueOf( pageInfo.getPageSize() ) );// 起始位置

            Map error = new HashMap();

            error.put( "empty", "true" );

            return ( JSON.toJSONString( error ) );

        }

        // 替换分页参数
        int nextPage = StringUtil.getIntValue( ( String ) request.getParameter( "pn" ), 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        if( "true".equals( page ) )
        {
            List countSqlParamList = new ArrayList();

            Long count = mdService.retrieveSystemTableByQueryFlagAndPageInfoCount( mdService
                .retrieveModelMutiQuery( cIdType, siteBean, StringUtil.getLongValue( modelId, -1 ),
                    query, order, way, countSqlParamList, true, midMap, siteId ), countSqlParamList
                    .toArray( new Object[] {} ), defFormMode );

            pageInfo = new Page( pageSize, count.intValue(), nextPage );

            sqlParamList.add( Long.valueOf( pageInfo.getFirstResult() ) );// 起始位置
            sqlParamList.add( Integer.valueOf( pageInfo.getPageSize() ) );// 起始位置

        }
        else
        {

            // 非分页只查询部分数据
            sqlParamList.add( Long.valueOf( 0 ) );// 起始位置
            sqlParamList.add( Integer.valueOf( pageSize ) );// 起始位置
        }

        List resultMap = mdService.retrieveMutiQueryContentByQueryFlagAndPageInfo( targetQuerySql,
            sqlParamList.toArray( new Object[] {} ), defFormMode, siteBean.getSiteId(), midMap
            .get( "modelId" ) );

        if( "true".equals( pull ) )
        {
            // 处理下拉请求

            List result = new ArrayList();

            if( resultMap.isEmpty() )
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
                if( i >= resultMap.size() )
                {
                    isEnd = true;
                    break;
                }

                result.add( resultMap.get( i ) );
            }

            // 确定站点是否传递错误
            Long cSiteId = ( Long ) ( ( Map ) result.get( 0 ) ).get( "siteId" );

            if( !siteBean.getSiteId().equals( cSiteId ) )
            {
                Map resMap = new HashMap();

                resMap.put( "isEnd", true );

                resMap.put( "content", "" );

                resMap.put( "size", Integer.valueOf( 0 ) );

                return ( JSON.toJSONString( resMap ) );
            }

            Map resMap = new HashMap();

            resMap.put( "isEnd", isEnd );

            resMap.put( "endPos", end + 1 );

            resMap.put( "content", result );

            resMap.put( "size", result.size() );

            return ( JSON.toJSONString( resMap ) );
        }
        else
        {

            Map resMap = new HashMap();

            // resMap.put( "isEnd", isEnd );

            // resMap.put( "endPos", end + 1 );

            resMap.put( "content", resultMap );

            resMap.put( "pageInfo", pageInfo );

            return ( JSON.toJSONString( resMap ) );
        }

    }

    private SiteGroupBean getSiteObj( Long siteId, HttpServletRequest request )
    {
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
        .getEntry( siteId );

        if( site == null )
        {
            site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );
        }

        return site;
    }

}
