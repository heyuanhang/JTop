package cn.com.mjsoft.cms.content.json;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.publish.bean.PublishPageAssistantBean;
import cn.com.mjsoft.cms.publish.bean.PublishRuleBean;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientGetContentListJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger.getLogger( ClientGetContentListJSonFlow.class );

    private static ClusterCacheAdapter queryParam = new ClusterCacheAdapter( 21000,
        "clientGetContentListJSonFlow.queryParam" );

    private static final long NO_ID_FLAG = -1;

    private static ContentService contentService = ContentService.getInstance();

    private static ChannelService channelService = ChannelService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        HttpServletRequest request = this.getServletFlowContext().getRequest();

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        String pull = StringUtil.notNull( ( String ) params.get( "pull" ) );

        // 是否有第一页标签的最后的分页数据
        String ep = notNull( ( String ) params.get( "ep" ) );

        if( StringUtil.isStringNull( ep ) )
        {
            ep = "1";
        }

        // 下拉大小
        String nz = notNull( ( String ) params.get( "nz" ) );

        if( StringUtil.isStringNull( nz ) )
        {
            nz = "0";
        }

        // 模型名称
        String modelName = notNull( ( String ) params.get( "modelName" ) );

        // 单个内容表
        String classId = notNull( ( String ) params.get( "classId" ) );

        // 当classID为多id混合取值模式时，需要指定分页模板主栏目
        String pageClassId = notNull( ( String ) params.get( "pageClassId" ) );

        if( StringUtil.isStringNull( pageClassId ) )
        {
            pageClassId = "-1";
        }

        // 排除的ClassId
        String exClassId = notNull( ( String ) params.get( "exClassId" ) );

        // 是否在列表时取所有数据(主表加副表),只在单class下有效
        String showAll = ( String ) params.get( "showAll" );

        if( StringUtil.isStringNull( showAll ) )
        {
            showAll = "true";
        }

        // orgCode
        String orgCode = ( String ) params.get( "orgCode" );

        if( StringUtil.isStringNull( orgCode ) )
        {
            orgCode = "";
        }

        // orgChild
        String orgChild = ( String ) params.get( "orgChild" );

        if( StringUtil.isStringNull( orgChild ) )
        {
            orgChild = "false";
        }

        // 分页
        String page = ( String ) params.get( "page" );

        if( StringUtil.isStringNull( page ) )
        {
            page = "false";
        }

        page = "true";

        // 大小默认10
        String pageSize = ( String ) params.get( "pageSize" );

        if( StringUtil.isStringNull( pageSize ) )
        {
            pageSize = "10";
        }

        // 筛选分类属性
        String filter = notNull( ( String ) params.get( "filter" ) );

        // 内容分类标识
        String type = notNull( ( String ) params.get( "type" ) );

        // 排序属性
        String order = notNull( ( String ) params.get( "order" ) );

        // 排序属性
        // private String orderBy = "";

        // 排序标准，默认降序
        // private String orderWay = "desc";

        // 自定义模型ID
        String modelId = ( String ) params.get( "modelId" );

        if( StringUtil.isStringNull( modelId ) )
        {
            modelId = "-1";
        }

        // 自定义模型ID
        String siteId = ( String ) params.get( "siteId" );

        if( StringUtil.isStringNull( siteId ) )
        {
            siteId = "-1";
        }

        String startDate = notNull( ( String ) params.get( "startDate" ) );

        String endDate = notNull( ( String ) params.get( "endDate" ) );

        /**
         * 下拉分页
         */
        int preEndPos = 0;

        int end = 0;

        if( "true".equals( pull ) )
        {

            page = "true";

            String key = siteId + ":" + classId + ":" + modelName + ":" + pageClassId + ":" + ":"
                + showAll + ":" + page + ":" + filter + ":" + type + ":" + order + ":" + modelId;

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

            pageSize = currPageSize.toString();// 2000作为首次缓存筏值,下拉操作只允许最大1W数据

            if( limitFlag >= currPageSize.intValue() )
            {
                currPageSize = currPageSize.intValue() + 500;

                queryParam.putEntry( key, currPageSize );

                pageSize = currPageSize.toString();

            }
        }

        log.info( "[ContentListTag] {classId}:" + classId + " ,{contentType}:" + modelName );

        String filterBy = filter;
        /**
         * classId参数不为空的话,优先取
         */

        boolean singeClassIdMode = true;

        if( classId.indexOf( "," ) != -1 || classId.indexOf( ":" ) != -1 )
        {
            singeClassIdMode = false;
        }

        // 单一栏目模式对应内容模型
        DataModelBean modelBean = null;

        // 单一栏目模式对应classId
        long targetClassId = -1;

        // 单一栏目模式对应class bean
        ContentClassBean classBean = null;

        if( singeClassIdMode )
        {
            // 注意,标签中使用classId属性会比URL传递参数级别高
            if( StringUtil.isStringNotNull( classId ) )
            {
                targetClassId = StringUtil.getLongValue( classId, -1 );
            }
            else
            {
                targetClassId = StringUtil.getLongValue( request.getParameter( "classId" ), -1 );
            }

            classBean = channelService.retrieveSingleClassBeanInfoByClassId( Long
                .valueOf( targetClassId ) );

            log.info( " targetClassId:" + targetClassId );

            if( classBean.getClassId().longValue() > 0 )
            {
                modelBean = metaDataService.retrieveSingleDataModelBeanById( classBean
                    .getContentType() );
            }
            else if( !"-1".equals( modelId ) )// 传入模型ID参数
            {
                modelBean = metaDataService.retrieveSingleDataModelBeanById( Long
                    .valueOf( StringUtil.getLongValue( modelId, -1 ) ) );
            }
            else
            {
                modelBean = metaDataService.retrieveSingleDataModelBeanByName( modelName );
            }
        }
        else
        {
            classBean = channelService.retrieveSingleClassBeanInfoByClassId( Long
                .valueOf( pageClassId ) );

            targetClassId = StringUtil.getLongValue( pageClassId, -1 );
        }

        List contentList = Collections.EMPTY_LIST;

        // 开始获取数据逻辑
        log.info( "模型名称：" + ( ( modelBean != null ) ? modelBean.getModelName() : "" ) );

        // 当前标签使用时,都可获取到,当前内容模型
        // pageContext.setAttribute( "currentModelBean", modelBean );

        // 发布状态,注意:客户端获取数据模式的发布状态必须为强制为获取发布成功的内容!!!
        Integer censorByVar = Constant.WORKFLOW.CENSOR_STATUS_SUCCESS;

        // 排序,在Client模式下,默认为按发布顺序,且已发布的内容都为审核通过状态,默认匹配发布状态
        if( "".equals( order ) )
        {
            order = "pubDate-down";
        }
        String[] orderFlag = StringUtil.split( order, "-" );

        String orderByFlag = null;
        String orderWayFlag = null;

        String orderBy = null;// 时间,自然序列,点击等
        String orderWay = null;// desc asc
        String orderFilterFlag = null;// 数据过滤条件

        // 分页线索ID,根据实际情况决定线索类型
        Object headOrderIdFlag = null;
        Object lastOrderIdFlag = null;

        // 是否使用limit方法分页
        boolean pageLimitMode = false;
        // 是否需要top数据
        boolean noTopMode = false;

        if( orderFlag != null && orderFlag.length > 1 )
        {
            orderByFlag = orderFlag[0];
            orderWayFlag = orderFlag[1];

            if( Constant.CONTENT.UP_ORDER_WAY.equals( orderWayFlag ) )
            {
                orderWay = "asc";
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY.equals( orderWayFlag ) )
            {
                orderWay = "desc";
            }

            /**
             * 获取上次线索分页的ID信息
             */

            if( Constant.CONTENT.DEFAULT_ORDER.equals( orderByFlag ) )
            {
                // 自然位置排序
                orderBy = Constant.CONTENT.DEFAULT_ORDER_VAR;

                // 自然位置排序线索标志,为双精度类型线索排序ID
                headOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "hof" ), NO_ID_FLAG ) );

                lastOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "lof" ), NO_ID_FLAG ) );
            }
            else if( Constant.CONTENT.ID_ORDER.equals( orderByFlag )
                || Constant.CONTENT.ADD_DATE_ORDER.equals( orderByFlag ) )
            {
                // 时间合并ID排序线索标志,为双精度类型线索排序ID
                orderBy = "contentId";

                headOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "hof" ), NO_ID_FLAG ) );

                lastOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "lof" ), NO_ID_FLAG ) );
            }
            else if( Constant.CONTENT.PUB_DATE_ORDER.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.PUB_DATE_ORDER_VAR;

                headOrderIdFlag = request.getParameter( "hof" );

                lastOrderIdFlag = request.getParameter( "lof" );
            }
            // 以下为快速变动条件排序,点击,评论,digg,全部为普通分页,以及无top查询
            else if( Constant.CONTENT.CLICK_COUNT_ORDER.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.CLICK_COUNT_ORDER_VAR;
                orderFilterFlag = Constant.CONTENT.CLICK_COUNT_ORDER_VAR;

                // 点击数排序线索标志,为双精度类型线索排序ID
                headOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "hof" ), NO_ID_FLAG ) );

                lastOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "lof" ), NO_ID_FLAG ) );

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.CLICK_COUNT_ORDER_DAY.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.CLICK_COUNT_ORDER_DAY_VAR;
                orderFilterFlag = Constant.CONTENT.CLICK_COUNT_ORDER_DAY_VAR;

                // 点击数排序线索标志,为双精度类型线索排序ID
                headOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "hof" ), NO_ID_FLAG ) );

                lastOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "lof" ), NO_ID_FLAG ) );

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.CLICK_COUNT_ORDER_WEEK.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.CLICK_COUNT_ORDER_WEEK_VAR;
                orderFilterFlag = Constant.CONTENT.CLICK_COUNT_ORDER_WEEK_VAR;

                // 点击数排序线索标志,为双精度类型线索排序ID
                headOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "hof" ), NO_ID_FLAG ) );

                lastOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "lof" ), NO_ID_FLAG ) );

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.CLICK_COUNT_ORDER_MONTH.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.CLICK_COUNT_ORDER_MONTH_VAR;
                orderFilterFlag = Constant.CONTENT.CLICK_COUNT_ORDER_MONTH_VAR;

                // 点击数排序线索标志,为双精度类型线索排序ID
                headOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "hof" ), NO_ID_FLAG ) );

                lastOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "lof" ), NO_ID_FLAG ) );

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.COMM_COUNT_ORDER.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.COMM_COUNT_ORDER_VAR;
                orderFilterFlag = Constant.CONTENT.COMM_COUNT_ORDER_VAR;

                // 评论数排序线索标志,为双精度类型线索排序ID
                headOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "hof" ), NO_ID_FLAG ) );

                lastOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "lof" ), NO_ID_FLAG ) );

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.COMM_COUNT_ORDER_DAY.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.COMM_COUNT_ORDER_DAY_VAR;
                orderFilterFlag = Constant.CONTENT.COMM_COUNT_ORDER_DAY_VAR;

                // 评论数排序线索标志,为双精度类型线索排序ID
                headOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "hof" ), NO_ID_FLAG ) );

                lastOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "lof" ), NO_ID_FLAG ) );

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.COMM_COUNT_ORDER_WEEK.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.COMM_COUNT_ORDER_WEEK_VAR;
                orderFilterFlag = Constant.CONTENT.COMM_COUNT_ORDER_WEEK_VAR;

                // 评论数排序线索标志,为双精度类型线索排序ID
                headOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "hof" ), NO_ID_FLAG ) );

                lastOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "lof" ), NO_ID_FLAG ) );

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.COMM_COUNT_ORDER_MONTH.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.COMM_COUNT_ORDER_MONTH_VAR;
                orderFilterFlag = Constant.CONTENT.COMM_COUNT_ORDER_MONTH_VAR;

                // 评论数排序线索标志,为双精度类型线索排序ID
                headOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "hof" ), NO_ID_FLAG ) );

                lastOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "lof" ), NO_ID_FLAG ) );

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.SUPPORT_ORDER.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.SUPPORT_ORDER_VAR;
                orderFilterFlag = Constant.CONTENT.SUPPORT_ORDER_VAR;

                // 支持数排序线索标志,为双精度类型线索排序ID
                headOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "hof" ), NO_ID_FLAG ) );

                lastOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "lof" ), NO_ID_FLAG ) );

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.AGAINST_ORDER.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.AGAINST_ORDER_VAR;
                orderFilterFlag = Constant.CONTENT.AGAINST_ORDER_VAR;

                // 反对数排序线索标志,为双精度类型线索排序ID
                headOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "hof" ), NO_ID_FLAG ) );

                lastOrderIdFlag = Double.valueOf( StringUtil.getDoubleValue( request
                    .getParameter( "lof" ), NO_ID_FLAG ) );

                pageLimitMode = true;
                noTopMode = true;
            }

            /**
             * 内容过滤,目前为各引导图类型,且都为传统分页
             */
            if( Constant.CONTENT.HAME_IMG_FILTER.equals( filterBy ) )
            {
                // orderBy = Constant.CONTENT.DEFAULT_ORDER_VAR;
                // orderFilterFlag = Constant.CONTENT.HAME_IMG_FILTER_VAR;

                pageLimitMode = true;
                // noTopMode = true;
            }
            else if( Constant.CONTENT.CHANNEL_IMG_FILTER.equals( filterBy ) )
            {
                // orderBy = Constant.CONTENT.DEFAULT_ORDER_VAR;
                // orderFilterFlag =
                // Constant.CONTENT.CHANNEL_IMG_FILTER_VAR;

                pageLimitMode = true;
                // noTopMode = true;
            }
            else if( Constant.CONTENT.CLASS_IMG_FILTER.equals( filterBy ) )
            {
                // orderBy = Constant.CONTENT.DEFAULT_ORDER_VAR;
                // orderFilterFlag = Constant.CONTENT.CLASS_IMG_FILTER_VAR;

                pageLimitMode = true;
                // noTopMode = true;
            }
            else if( Constant.CONTENT.CONTENT_IMG_FILTER.equals( filterBy ) )
            {
                // orderBy = Constant.CONTENT.DEFAULT_ORDER_VAR;
                // orderFilterFlag =
                // Constant.CONTENT.CONTENT_IMG_FILTER_VAR;

                pageLimitMode = true;
                // noTopMode = true;
            }
            // if( Constant.CONTENT.CREATOR_FILTER.equals( orderByFlag
            // ) )
            // {
            // orderBy = Constant.CONTENT.DEFAULT_ORDER_VAR;
            // orderFilterFlag = Constant.CONTENT.CREATOR_FILTER_VAR;
            //                
            // pageLimitMode = true;
            // noTopMode = true;
            // }

        }

        // 时间条件处理
        // 内容添加时间筛选条件
        Timestamp startAddTS = null;
        Timestamp endAddTS = null;
        if( "" != startDate || "" != endDate )
        {
            startAddTS = DateAndTimeUtil.getTimestamp( startDate + " 00:00:00",
                DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );

            endAddTS = DateAndTimeUtil.getTimestamp( endDate + " 23:59:59",
                DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );

            if( startAddTS == null )
            {
                startAddTS = Constant.CONTENT.MIN_DATE;
            }

            if( endAddTS == null )
            {
                endAddTS = Constant.CONTENT.MAX_DATE;
            }

            if( endAddTS != null && startAddTS != null )
            {
                // 结束时间小与开始时间的情况
                if( endAddTS.getTime() - startAddTS.getTime() < 0 )
                {
                    // 或提示
                    // return resList;
                }
            }
        }

        log.info( " typeFalg:" + type );

        // if( false )
        // {
        // /**
        // * 废弃：推荐位已经独立处理，此代码记录
        // */
        //
        // // 推荐位模式,需要进一步处理
        // SiteGroupBean site = SiteGroupService
        // .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest )
        // this.pageContext
        // .getRequest() );
        //
        // // 推荐模式
        // contentList = contentService
        // .retrieveAllCommendContentByCommendByFlag( filterBy, site
        // .getSiteFlag() );
        //
        // pageContext.setAttribute( "allContent", contentList );
        //
        // HttpServletRequest request = ( HttpServletRequest ) pageContext
        // .getRequest();
        //
        // }

        if( orderBy != null && orderWay != null )
        {
            int size = StringUtil.getIntValue( pageSize, 25 );

            log.info( "pagesize:" + size );

            String countOrderFilter = contentService.getOrderFilterByFilterBy( orderFilterFlag,
                filterBy, orgCode, StringUtil.getBooleanValue( orgChild, false ) );

            String orderFilter = countOrderFilter;

            String classIds = null;

            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( StringUtil.getLongValue( siteId, -1 ) );

            if( site == null )
            {
                site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );
            }

            if( site == null )
            {
                Map resMap = new HashMap();

                resMap.put( "isEnd", true );

                resMap.put( "content", "" );

                resMap.put( "size", Integer.valueOf( 0 ) );

                return ( JSON.toJSONString( resMap ) );
            }

            if( !singeClassIdMode )
            {
                pageLimitMode = true;

                noTopMode = true;

                // 组合查询,无分页
                log
                    .info( "ContentListTag 进入栏目ID组合查询 orderBy:" + orderBy + ", orderWay:"
                        + orderWay );

                Long paramClassId = null;

                boolean siteMode = false;

                if( classId.indexOf( "," ) != -1 )
                {
                    // 传入的多个ID
                    String[] ids = StringUtil.split( classId, "," );

                    boolean isLong = true;
                    for ( int i = 0; i < ids.length; i++ )
                    {
                        if( StringUtil.getLongValue( ids[i], 0 ) == 0 )
                        {
                            isLong = false;
                            break;
                        }
                    }

                    classIds = isLong ? classId : "0";
                }
                else if( classId.startsWith( "child:" ) )
                {
                    // 直接孩子

                    if( classId.endsWith( "root" ) )
                    {
                        // 根栏目
                        paramClassId = Long.valueOf( -9999 );
                    }
                    else
                    {
                        paramClassId = Long.valueOf( StringUtil.getLongValue( StringUtil
                            .replaceString( classId, "child:", "", false, false ), -1 ) );
                    }

                    classIds = channelService.retrieveConetentClassIdNotSpecByParentClassId(
                        paramClassId, site.getSiteFlag() );
                }
                else if( classId.startsWith( "self:child:" ) )
                {
                    // 直接孩子和自己

                    paramClassId = Long.valueOf( StringUtil.getLongValue( StringUtil.replaceString(
                        classId, "self:child:", "", false, false ), -1 ) );

                    classIds = channelService.retrieveConetentClassIdNotSpecByParentClassId(
                        paramClassId, site.getSiteFlag() );

                    classIds = classIds + "," + paramClassId;

                }
                else if( classId.startsWith( "allChild:" ) )
                {
                    // 所有孩子及孙子和自己

                    if( classId.endsWith( ":root" ) )
                    {
                        // 全站模式

                        siteMode = true;

                        classIds = channelService.retrieveContentClassIdByPreLinear( "000", site
                            .getSiteFlag() );
                    }
                    else
                    {
                        paramClassId = Long.valueOf( StringUtil.getLongValue( StringUtil
                            .replaceString( classId, "allChild:", "", false, false ), -1 ) );

                        classIds = channelService.retrieveContentClassIdByPreLinear( channelService
                            .retrieveSingleClassBeanInfoByClassId( paramClassId )
                            .getLinearOrderFlag(), site.getSiteFlag() );
                    }

                }

                // 处理排除的classId
                if( StringUtil.isStringNotNull( exClassId ) )
                {
                    classIds = StringUtil.replaceString( classIds, " ", "", false, false );

                    String[] eids = StringUtil.split( exClassId, "," );

                    for ( int i = 0; i < eids.length; i++ )
                    {
                        if( classIds.indexOf( eids[i] ) != -1 )
                        {
                            classIds = StringUtil.replaceString( classIds, eids[i] + ",", "",
                                false, false );
                            classIds = StringUtil.replaceString( classIds, "," + eids[i], "",
                                false, false );
                        }
                    }
                }

            }
            // else
            {
                log.info( "ContentListTag进入分页模式 orderBy:" + orderBy + ", orderWay:" + orderWay );

                // 分页模式单classId模式只有指定clasId才可使用,故model必须存在

                boolean pageMode = false;

                if( "true".equals( page ) )
                {
                    // 注意:当不处于pageMode时,发布行为的任何参数都不需要传递
                    pageMode = true;
                }

                // 将传入classId以分别栏目,此参数若不存在视为站点根目录
                // 将传入lastId以表示为next分页标志ID
                // 将传入firstId以表示为prev分页标志ID
                // 将传入pageAction以表示为何种分页动作

                // 查询排序条件

                // log.info( "[Page] topFlag:" + topFlag );
                // log.info( "[Page] orderIdFlag:" + orderIdFlag );

                /**
                 * 1.计算各查询条件总数据大小并缓存
                 */
                Integer count = Integer.valueOf( 0 );

                if( singeClassIdMode )
                {
                    if( StringUtil.isStringNotNull( countOrderFilter ) )
                    {
                        count = contentService.getUserDefineContentAllCountOrderFilterMode( Long
                            .valueOf( targetClassId ), modelBean, type, censorByVar, startAddTS,
                            endAddTS, countOrderFilter );
                    }
                    else
                    {
                        count = contentService.getUserDefineContentAllCount( Long
                            .valueOf( targetClassId ), modelBean, type, censorByVar, startAddTS,
                            endAddTS );
                    }
                }
                else
                {
                    if( StringUtil.isStringNotNull( countOrderFilter ) )
                    {
                        count = contentService.getUserDefineContentAllCountOrderFilterMode(
                            classIds, type, censorByVar, countOrderFilter );
                    }
                    else
                    {
                        count = contentService.getUserDefineContentAllCount( classIds, type,
                            censorByVar );
                    }
                }

                /**
                 * 2.组装page对象
                 */
                // 当前所处第几页
                int possibleCurrentPage = StringUtil.getIntValue( request.getParameter( "pn" ), 1 );

                // 分页动作,前进或后退第几页
                int pageAction = StringUtil.getIntValue( request.getParameter( "pa" ),
                    Constant.PAGE.PAGE_ACTION_NEXT );

                // 取下一页的page值(可能的值,由page对象考虑边界计算正确值)
                // if( pageAction == Constant.PAGE.PAGE_ACTION_NEXT )
                // {
                // possibleCurrentPage += 1;
                // }
                // else if( pageAction == Constant.PAGE.PAGE_ACTION_PREV )
                // {
                // possibleCurrentPage -= 1;
                // }

                if( !pageMode )
                {
                    possibleCurrentPage = 1;

                    pageAction = Constant.PAGE.PAGE_ACTION_HEAD;
                }

                log.info( "[Page] possibleCurrentPage:" + possibleCurrentPage );
                log.info( "[Page] pageAction:" + pageAction );

                Page pageInfo = new Page( ( size == 0 ) ? Page.DEFAULT_PAGE_SIZE : size, count
                    .intValue(), possibleCurrentPage );

                // 以下的Page对象的currentPage为正确的下一页
                /**
                 * 3.分页逻辑
                 */

                pageLimitMode = true;

                // 下一页动作
                if( pageAction == Constant.PAGE.PAGE_ACTION_NEXT )
                {
                    log.info( "[Page] 进入下一页动作,使用Last Flag" );

                    // 已经是最后一页,强制进入尾页,大于最大页强制作为作后一页
                    // 通过获取最后一行数据的定位ID,反向查询
                    if( pageInfo.getCurrentPage() == pageInfo.getPageCount()
                        || pageInfo.getPageCount() == 0 )
                    {
                        log.info( "[Page] 已经是最后一页,强制进入尾页" );

                        // 空页处理
                        if( pageInfo.getPageCount() == 0 )
                        {
                            pageInfo.setCurrentPage( 1 );
                        }

                        int endContentSize = pageInfo.getLastPageSize();

                        log.info( "[Page] 最后页内容的个数:" + endContentSize );

                        long firstFlagId;
                        long firstTopFlag = -1;
                        Object firstOrderIdFlag = null;

                        /*
                         * 获取最后一页分页的最后一个ID信息
                         */
                        if( targetClassId == -1 )
                        {
                            if( Constant.CONTENT.USER_DEFINE.equals( modelName ) )
                            {

                            }

                        }
                        else
                        {
                            firstOrderIdFlag = contentService
                                .getMaxOrMinEndPageModeOrderIdByOrderByAndWay( orderBy, orderWay );
                        }

                        // 取最后一页的数据

                        if( !pageLimitMode )
                        {
                            contentList = contentService
                                .retrieveLimitUserDefineContentByClassIDAndModelIdAndFlag(
                                    StringUtil.getBooleanValue( showAll, false ), modelBean,
                                    targetClassId, startAddTS, endAddTS, firstOrderIdFlag, type,
                                    censorByVar, orderBy, orderWay, pageInfo.getPageSize(),
                                    endContentSize, Constant.PAGE.PAGE_ACTION_END, pageInfo );
                        }
                        else
                        {

                            if( singeClassIdMode )
                            {
                                // 跳到某页
                                if( noTopMode )
                                {
                                    contentList = contentService.retrieveLimitModeContent(
                                        StringUtil.getBooleanValue( showAll, false ), modelBean,
                                        targetClassId, censorByVar, type, startAddTS, endAddTS,
                                        pageInfo.getFirstResult(), pageInfo.getPageSize(),
                                        orderFilter, orderBy, orderWay );
                                }
                                else
                                {
                                    contentList = contentService.retrieveLimitModeContentTopMode(
                                        StringUtil.getBooleanValue( showAll, false ), modelBean,
                                        targetClassId, censorByVar, type, startAddTS, endAddTS,
                                        pageInfo.getFirstResult(), pageInfo.getPageSize(),
                                        orderFilter, orderBy, orderWay );
                                }
                            }
                            else
                            {
                                contentList = contentService
                                    .retrieveLimitModeContentMainInfoByClassIds( classIds, type,
                                        pageInfo.getFirstResult(), size, orderFilter, orderBy,
                                        orderWay );
                            }
                        }
                    }
                    else
                    // 一般情况下的下一页动作
                    {

                        if( !pageLimitMode )
                        {
                            contentList = contentService
                                .retrieveLimitUserDefineContentByClassIDAndModelIdAndFlag(
                                    StringUtil.getBooleanValue( showAll, false ), modelBean,
                                    targetClassId, startAddTS, endAddTS, lastOrderIdFlag, type,
                                    censorByVar, orderBy, orderWay, pageInfo.getPageSize(),
                                    pageInfo.getPageSize(), Constant.PAGE.PAGE_ACTION_NEXT,
                                    pageInfo );
                        }
                        else
                        {
                            if( singeClassIdMode )
                            {
                                // 跳到某页
                                if( noTopMode )
                                {
                                    contentList = contentService.retrieveLimitModeContent(
                                        StringUtil.getBooleanValue( showAll, false ), modelBean,
                                        targetClassId, censorByVar, type, startAddTS, endAddTS,
                                        pageInfo.getFirstResult(), pageInfo.getPageSize(),
                                        orderFilter, orderBy, orderWay );
                                }
                                else
                                {
                                    contentList = contentService.retrieveLimitModeContentTopMode(
                                        StringUtil.getBooleanValue( showAll, false ), modelBean,
                                        targetClassId, censorByVar, type, startAddTS, endAddTS,
                                        pageInfo.getFirstResult(), pageInfo.getPageSize(),
                                        orderFilter, orderBy, orderWay );
                                }
                            }
                            else
                            {
                                contentList = contentService
                                    .retrieveLimitModeContentMainInfoByClassIds( classIds, type,
                                        pageInfo.getFirstResult(), size, orderFilter, orderBy,
                                        orderWay );
                            }
                        }
                    }
                }
                else if( pageAction == Constant.PAGE.PAGE_ACTION_PREV )// 上一页
                {
                    log.info( "[Page] 进入上一页模式,使用Head Flag:" );

                    if( pageInfo.getCurrentPage() == 1 )// 已经是第一页,强制进入首页
                    {
                        // 暂时取10000后的
                        log.info( "[Page] 已经是第一页,强制进入首页" );

                        Object max = contentService.getMaxOrMinHeadPageModeOrderIdByOrderByAndWay(
                            orderBy, orderWay );

                        if( !pageLimitMode )
                        {

                            contentList = contentService
                                .retrieveLimitUserDefineContentByClassIDAndModelIdAndFlag(
                                    StringUtil.getBooleanValue( showAll, false ), modelBean,
                                    targetClassId, startAddTS, endAddTS, max, type, censorByVar,
                                    orderBy, orderWay, pageInfo.getPageSize(), pageInfo
                                        .getPageSize(), Constant.PAGE.PAGE_ACTION_HEAD, pageInfo );
                        }
                        else
                        {
                            if( singeClassIdMode )
                            {
                                // 跳到某页
                                if( noTopMode )
                                {

                                    contentList = contentService.retrieveLimitModeContent(
                                        StringUtil.getBooleanValue( showAll, false ), modelBean,
                                        targetClassId, censorByVar, type, startAddTS, endAddTS,
                                        pageInfo.getFirstResult(), pageInfo.getPageSize(),
                                        orderFilter, orderBy, orderWay );
                                }
                                else
                                {
                                    contentList = contentService.retrieveLimitModeContentTopMode(
                                        StringUtil.getBooleanValue( showAll, false ), modelBean,
                                        targetClassId, censorByVar, type, startAddTS, endAddTS,
                                        pageInfo.getFirstResult(), pageInfo.getPageSize(),
                                        orderFilter, orderBy, orderWay );
                                }
                            }
                            else
                            {
                                contentList = contentService
                                    .retrieveLimitModeContentMainInfoByClassIds( classIds, type,
                                        pageInfo.getFirstResult(), size, orderFilter, orderBy,
                                        orderWay );
                            }
                        }
                    }
                    else
                    {

                        if( !pageLimitMode )
                        {
                            contentList = contentService
                                .retrieveLimitUserDefineContentByClassIDAndModelIdAndFlag(
                                    StringUtil.getBooleanValue( showAll, false ), modelBean,
                                    targetClassId, startAddTS, endAddTS, headOrderIdFlag, type,
                                    censorByVar, orderBy, orderWay, pageInfo.getPageSize(),
                                    pageInfo.getPageSize(), Constant.PAGE.PAGE_ACTION_PREV,
                                    pageInfo );
                        }
                        else
                        {
                            if( singeClassIdMode )
                            {
                                // 跳到某页
                                if( noTopMode )
                                {
                                    contentList = contentService.retrieveLimitModeContent(
                                        StringUtil.getBooleanValue( showAll, false ), modelBean,
                                        targetClassId, censorByVar, type, startAddTS, endAddTS,
                                        pageInfo.getFirstResult(), pageInfo.getPageSize(),
                                        orderFilter, orderBy, orderWay );
                                }
                                else
                                {
                                    contentList = contentService.retrieveLimitModeContentTopMode(
                                        StringUtil.getBooleanValue( showAll, false ), modelBean,
                                        targetClassId, censorByVar, type, startAddTS, endAddTS,
                                        pageInfo.getFirstResult(), pageInfo.getPageSize(),
                                        orderFilter, orderBy, orderWay );
                                }
                            }
                            else
                            {
                                contentList = contentService
                                    .retrieveLimitModeContentMainInfoByClassIds( classIds, type,
                                        pageInfo.getFirstResult(), size, orderFilter, orderBy,
                                        orderWay );
                            }
                        }
                    }

                }
                else if( pageAction == Constant.PAGE.PAGE_ACTION_HEAD )
                // 取首页
                {
                    log.info( "[Page] 进入首页模式 [contentType]:" + modelName );

                    // 强制进入首页
                    pageInfo.setCurrentPage( 1 );

                    Object max = contentService.getMaxOrMinHeadPageModeOrderIdByOrderByAndWay(
                        orderBy, orderWay );

                    if( !pageLimitMode )
                    {
                        contentList = contentService
                            .retrieveLimitUserDefineContentByClassIDAndModelIdAndFlag( StringUtil
                                .getBooleanValue( showAll, false ), modelBean, targetClassId,
                                startAddTS, endAddTS, max, type, censorByVar, orderBy, orderWay,
                                pageInfo.getPageSize(), pageInfo.getPageSize(),
                                Constant.PAGE.PAGE_ACTION_HEAD, pageInfo );
                    }
                    else
                    {
                        if( singeClassIdMode )
                        {
                            // 跳到某页
                            if( noTopMode )
                            {
                                contentList = contentService.retrieveLimitModeContent( StringUtil
                                    .getBooleanValue( showAll, false ), modelBean, targetClassId,
                                    censorByVar, type, startAddTS, endAddTS, pageInfo
                                        .getFirstResult(), pageInfo.getPageSize(), orderFilter,
                                    orderBy, orderWay );
                            }
                            else
                            {
                                contentList = contentService.retrieveLimitModeContentTopMode(
                                    StringUtil.getBooleanValue( showAll, false ), modelBean,
                                    targetClassId, censorByVar, type, startAddTS, endAddTS,
                                    pageInfo.getFirstResult(), pageInfo.getPageSize(), orderFilter,
                                    orderBy, orderWay );
                            }
                        }
                        else
                        {
                            contentList = contentService
                                .retrieveLimitModeContentMainInfoByClassIds( classIds, type,
                                    pageInfo.getFirstResult(), size, orderFilter, orderBy, orderWay );
                        }

                    }

                }
                else if( pageAction == Constant.PAGE.PAGE_ACTION_END )
                // 取最后一页
                {
                    log.info( "[Page] 进入尾页模式 [contentType]:" + modelName );

                    int endContentSize = pageInfo.getLastPageSize();

                    log.info( "[Page] 最后页内容的个数:" + endContentSize );

                    // 强制进入最后一页
                    pageInfo.setCurrentPage( pageInfo.getPageCount() );

                    long firstFlagId;
                    long firstTopFlag = -1;
                    Object firstOrderIdFlag = null;

                    boolean limitMode = false;

                    if( targetClassId == -1 )
                    {
                        if( Constant.CONTENT.USER_DEFINE.equals( modelName ) )
                        {

                        }

                    }
                    else
                    {

                        {
                            firstOrderIdFlag = contentService
                                .getMaxOrMinEndPageModeOrderIdByOrderByAndWay( orderBy, orderWay );
                        }

                    }

                    // 取最后一页的数据

                    if( !pageLimitMode )
                    {
                        contentList = contentService
                            .retrieveLimitUserDefineContentByClassIDAndModelIdAndFlag( StringUtil
                                .getBooleanValue( showAll, false ), modelBean, targetClassId,
                                startAddTS, endAddTS, firstOrderIdFlag, type, censorByVar, orderBy,
                                orderWay, pageInfo.getPageSize(), endContentSize,
                                Constant.PAGE.PAGE_ACTION_END, pageInfo );
                    }
                    else
                    {
                        if( singeClassIdMode )
                        {
                            // 跳到某页
                            if( noTopMode )
                            {
                                contentList = contentService.retrieveLimitModeContent( StringUtil
                                    .getBooleanValue( showAll, false ), modelBean, targetClassId,
                                    censorByVar, type, startAddTS, endAddTS, pageInfo
                                        .getFirstResult(), pageInfo.getPageSize(), orderFilter,
                                    orderBy, orderWay );
                            }
                            else
                            {
                                contentList = contentService.retrieveLimitModeContentTopMode(
                                    StringUtil.getBooleanValue( showAll, false ), modelBean,
                                    targetClassId, censorByVar, type, startAddTS, endAddTS,
                                    pageInfo.getFirstResult(), pageInfo.getPageSize(), orderFilter,
                                    orderBy, orderWay );
                            }
                        }
                        else
                        {
                            contentList = contentService
                                .retrieveLimitModeContentMainInfoByClassIds( classIds, type,
                                    pageInfo.getFirstResult(), size, orderFilter, orderBy, orderWay );
                        }
                    }
                }
                else if( pageAction == Constant.PAGE.PAGE_ACTION_JUMP )
                {
                    if( pageInfo.getPageCount() == 0 )
                    {
                        pageInfo.setCurrentPage( 1 );
                    }

                    if( singeClassIdMode )
                    {
                        // 跳到某页
                        if( noTopMode )
                        {
                            contentList = contentService.retrieveLimitModeContent( StringUtil
                                .getBooleanValue( showAll, false ), modelBean, targetClassId,
                                censorByVar, type, startAddTS, endAddTS, pageInfo.getFirstResult(),
                                pageInfo.getPageSize(), orderFilter, orderBy, orderWay );
                        }
                        else
                        {
                            contentList = contentService.retrieveLimitModeContentTopMode(
                                StringUtil.getBooleanValue( showAll, false ), modelBean,
                                targetClassId, censorByVar, type, startAddTS, endAddTS, pageInfo
                                    .getFirstResult(), pageInfo.getPageSize(), orderFilter,
                                orderBy, orderWay );
                        }
                    }
                    else
                    {
                        contentList = contentService.retrieveLimitModeContentMainInfoByClassIds(
                            classIds, type, pageInfo.getFirstResult(), size, orderFilter, orderBy,
                            orderWay );
                    }

                }
                else
                {
                    throw new JspException( "错误的分页标记" );
                }

                log.info( "最终结果:" + contentList );

                // 和管理不同,url的组装需要走模板url规则
                String url = site.getSiteUrl()
                // + siteTemplate 隐藏template
                    + StringUtil.replaceString( classBean.getClassTemplateUrl(), "{class-id}", Long
                        .valueOf( targetClassId ).toString(), false, false );

                String prefixQuery = "?";
                if( StringUtil.isStringNotNull( classBean.getClassTemplateUrl() )
                    && classBean.getClassTemplateUrl().indexOf( "?" ) != -1 )
                {
                    prefixQuery = "&";
                }

                String nextQuery = null;
                String prevQuery = null;
                String publishNextQueryChain = null;

                String nextQueryCd = null;

                // 最后分页数据组合
                // pageInfo.setCurrentPage( currentPage );

                String headQuery = null;
                String endQuery = null;
                String jumpQuery = null;

                String currentHeadOrderFlag = "";

                String currentLastOrderFlag = "";

                // if( contentList.size() != 0 )
                // {
                // 这个地方要注意下,涉及到多模型的元数据,也就是id name,由于
                // 已经没有了bean,所以无法从bean取得相关信息,这样的话,必须
                // 在其内容模型元数据表中记录足够多的信息
                String idName = Constant.METADATA.CONTENT_ID_NAME;

                // if( Constant.CONTENT.DEFAULT_ORDER.equals( orderByFlag )
                // )
                // {
                // 目前,orderId为long和double的都统一为double
                // if( !contentList.isEmpty() )
                // {
                // currentHeadOrderFlag = decimalFormat.format( ( ( Map )
                // contentList.get( 0 ) )
                // .get( orderBy ) );
                // Object obj = ( ( Map ) contentList.get( contentList.size() -
                // 1 ) );
                // currentLastOrderFlag = decimalFormat.format( ( ( Map )
                // contentList
                // .get( contentList.size() - 1 ) ).get( orderBy ) );
                // }

                // pageInfo.setHeadPosQueryCondition( "&hof=" +
                // currentHeadOrderFlag );
                // pageInfo.setLastPosQueryCondition( "&lof=" +
                // currentLastOrderFlag );

                // 只取URL部分,无参数
                // StringBuffer url = ( ( HttpServletRequest )
                // this.pageContext
                // .getRequest() ).getRequestURL();
                // 客户端模式不需要censorBy参数,因为必须是已发布状态的内容

                /**
                 * 所有的分页句只记录分页线索,不记录排序,过滤条件,这些条件由参数给出,若需要,由用户自行加入
                 */

                nextQueryCd = new StringBuffer()

                .append( "pn=" + pageInfo.getCurrentPage() ).append( "&pa=1" ).append(
                    "&hof=" + currentHeadOrderFlag ).append( "&lof=" + currentLastOrderFlag )
                    .toString();

                nextQuery = new StringBuffer( url )
                    .append( prefixQuery )
                    // .append( "typeBy=" + type ).append( "&orderBy=" +
                    // order )
                    // .append( "&filterBy=" + filterBy )
                    .append( "pn=" + pageInfo.getCurrentPage() ).append( "&pa=1" ).append(
                        "&hof=" + currentHeadOrderFlag ).append( "&lof=" + currentLastOrderFlag )
                    .toString();

                prevQuery = new StringBuffer( url.toString() )
                    .append( prefixQuery )
                    // .append( "typeBy=" + type ).append("&orderBy=" +
                    // order
                    // ).append( "&filterBy=" + filterBy )
                    .append( "pn=" + pageInfo.getCurrentPage() ).append( "&pa=-1" ).append(
                        "&hof=" + currentHeadOrderFlag ).append( "&lof=" + currentLastOrderFlag )
                    .toString();

                headQuery = new StringBuffer( url.toString() )
                    .append( prefixQuery )
                    // .append( "typeBy=" + type ).append("&orderBy=" +
                    // order
                    // ).append( "&filterBy=" + filterBy )
                    .append( "pn=" + pageInfo.getCurrentPage() ).append( "&pa=2" ).append(
                        "&hof=" + currentHeadOrderFlag ).append( "&lof=" + currentLastOrderFlag )
                    .toString();

                endQuery = new StringBuffer( url.toString() )
                    .append( prefixQuery )
                    // .append( "typeBy=" + type ).append("&orderBy=" +
                    // order
                    // ).append( "&filterBy=" + filterBy )
                    .append( "pn=" + pageInfo.getCurrentPage() ).append( "&pa=-2" ).append(
                        "&hof=" + currentHeadOrderFlag ).append( "&lof=" + currentLastOrderFlag )
                    .toString();

                jumpQuery = new StringBuffer( url.toString() ).append( prefixQuery ).append(
                    "tb=" + type ).append( "&ob=" + order ).append( "&fb=" + filterBy )
                // .append("&currentPage=" + pageInfo.getCurrentPage() )
                    .append( "&pa=3" )
                    // .append("&pageCount=" +pageInfo.getPageCount() )
                    // .append("&headTopFlag=" + currentHeadTopFlag+
                    // "&headOrderFlag=" + currentHeadOrderFlag )
                    // .append("&lastTopFlag=" + currentLastTopFlag+
                    // "&lastOrderFlag=" + currentLastOrderFlag )
                    .toString();

                //
                // pageInfo.setLastId( lId );

                // }

                // 获取栏目发布规则
                PublishRuleBean ruleBean = publishService.retrieveSinglePublishRuleBean( classBean
                    .getClassPublishRuleId() );

                pageInfo.setNextQuery( nextQuery );
                pageInfo.setPrevQuery( prevQuery );
                pageInfo.setHeadQuery( headQuery );
                pageInfo.setEndQuery( endQuery );
                pageInfo.setJumpQuery( jumpQuery );
                pageInfo.setEndPos( classBean.getListPageLimit() );

                pageInfo.setNextQueryCd( nextQueryCd );

                if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean
                    .getClassProduceType() ) )
                {
                    pageInfo.setJumpStatic( site.getSiteUrl()
                        + ( ( ruleBean == null ) ? site.getSiteUrl() : ruleBean
                            .getFullContentClassPagePublishPath( site, classBean, null, null,
                                Integer.valueOf( -99999 ) )[1] ) );
                }

                log.info( "[Page] 分页对象为:" + pageInfo );

                if( pageMode )
                {
                    // 页面使用分页对象
                    // this.pageContext.setAttribute(
                    // "___system_dispose_page_object___", pageInfo );

                    // return ( JSON.toJSONString(
                    // pageInfo ) );
                }

                /**
                 * 发布逻辑<br>
                 * 1.nextQuery:页面分页,上一页 <br>
                 * 2.prevQuery:页面分页,下一页 <br>
                 * 3.nextQueryActionUrl:系统内部静态化操作所使用下一页地址 <br>
                 * 4.nextStaticPage prevStaticPage:由系统传入静态分页地址,此地址为生成上下一页的标准
                 */
                boolean fromSystemAction = false;
                if( Boolean.TRUE.equals( request
                    .getAttribute( Constant.CONTENT.HTML_PUB_ACTION_FLAG ) ) )
                {
                    fromSystemAction = true;

                    if( pageMode )
                    {
                        request.setAttribute( "___system_dispose_page_object_for_pub___", pageInfo );
                    }
                }

                // SiteGroupBean site = null;
                // 若来自静态化发布
                if( fromSystemAction )
                {

                    // site = request.getAttribute(
                    // Constant.CONTENT.HTML_PUB_CURRENT_SITE,
                    // );
                    // 传递必须参数

                    // TODO
                    // 首先,若是第一次来自静态action,则需要正常处理第一页的情况,且要将分页结果(下一页上一页)静态化
                    // TODO接下来,将下一次分页参数传到静态action,由action再一次处理,模拟用户进行访问,知道处理结束

                }

                // request.setAttribute( "page", pageInfo
                // );
                if( fromSystemAction && request.getAttribute( "needPage" ) != null )
                {
                    String siteTemplate = Constant.CONTENT.TEMPLATE_BASE + Constant.CONTENT.URL_SEP;

                    // 设定上一页地址,上一页在分页流程中必定为静态地址

                    pageInfo.setPrevQuery( site.getSiteUrl()
                        + request.getAttribute( "prevStaticPage" ) );

                    // 设定下一次访问标志,当达到分页标志位的时候,不再进行静态化操作
                    int genPageSize = ( ( Integer ) request.getAttribute( "genPageSize" ) )
                        .intValue();

                    if( pageInfo.getCurrentPage() == pageInfo.getPageCount()
                        || pageInfo.getPageCount() == 0 || pageInfo.getCurrentPage() == genPageSize )
                    {
                        // 静态化最后一页,nextQueryActionUrl将为空
                        if( pageMode )
                        {
                            request.setAttribute( "nextQueryActionUrl", null );
                        }

                        if( genPageSize >= pageInfo.getPageCount() )
                        {
                            if( pageInfo.getPageCount() == 1 || pageInfo.getPageCount() == 0 )
                            {
                                // 只有一页
                                pageInfo.setNextQuery( site.getSiteUrl()
                                    + request.getAttribute( "nextStaticPageOnlyOne" ) );
                            }

                            else
                            {
                                // 下一页不存在的情况,最后一页即为下一页
                                pageInfo.setNextQuery( site.getSiteUrl()
                                    + request.getAttribute( "nextStaticPage" ) );
                            }
                        }
                        else
                        {
                            // 下一页将为动态,注意不合法url
                            if( url.indexOf( ".jsp" ) != -1 || url.indexOf( ".thtml" ) != -1 )
                            {
                                nextQuery = new StringBuffer( url )
                                    .append( prefixQuery )
                                    // .append( "&typeBy=" + type ).append(
                                    // "&orderBy=" + order
                                    // ).append("&filterBy="
                                    // + filterBy )
                                    .append( "pn=" + pageInfo.getCurrentPage() ).append( "&pa=1" )
                                    .append( "&hof=" + currentHeadOrderFlag ).append(
                                        "&lof=" + currentLastOrderFlag ).toString();
                            }
                            // else
                            // {
                            // new StringBuffer( url )
                            // .append( prefixQuery )
                            // // .append( "?typeBy=" + type ).append(
                            // // "&orderBy=" + order ).append(
                            // // "&filterBy=" + filterBy )
                            // .append(
                            // "currentPage="
                            // + pageInfo.getCurrentPage() )
                            // .append( "&pageAction=1" ).append(
                            // "&headOrderFlag="
                            // + currentHeadOrderFlag )
                            // .append(
                            // "&lastOrderFlag="
                            // + currentLastOrderFlag )
                            // .toString();
                            // }

                            pageInfo.setNextQuery( nextQuery );

                        }

                    }
                    else
                    {
                        // 下一页仍然是静态化系统动作

                        // 必须变化为内部访问地址
                        url = site.getSiteRoot()
                            + Constant.CONTENT.URL_SEP
                            + siteTemplate
                            + StringUtil.replaceString( classBean.getClassTemplateUrl(),
                                "{class-id}", Long.valueOf( targetClassId ).toString(), false,
                                false );

                        // 获取系统内部访问URL,过滤参数全

                        if( url.indexOf( ".jsp?" ) != -1 )
                        {
                            publishNextQueryChain = new StringBuffer( Constant.CONTENT.URL_SEP
                                + url ).append( "&tb=" + type ).append( "&fb=" + filterBy ).append(
                                "&pn=" + pageInfo.getCurrentPage() ).append( "&pa=1" ).append(
                                "&hof=" + currentHeadOrderFlag ).append(
                                "&lof=" + currentLastOrderFlag ).toString();
                        }
                        else
                        {
                            publishNextQueryChain = new StringBuffer( Constant.CONTENT.URL_SEP
                                + url ).append( "&tb=" + type ).append( "&fb=" + filterBy ).append(
                                "&pn=" + pageInfo.getCurrentPage() ).append( "&pa=1" ).append(
                                "&hof=" + currentHeadOrderFlag ).append(
                                "&lof=" + currentLastOrderFlag ).toString();
                        }

                        // 将nextQueryActionUrl传递给发布逻辑,继续处理分页逻辑
                        if( pageMode )
                        {
                            request.setAttribute( "nextQueryActionUrl", publishNextQueryChain );
                        }

                        pageInfo.setNextQuery( site.getSiteUrl()
                            + request.getAttribute( "nextStaticPage" ) );
                    }

                    // 因为存在静态分页,必定首页必定为静态第一页
                    // 获取栏目发布规则

                    if( pageMode )
                    {
                        pageInfo.setHeadQuery( site.getSiteUrl()
                            + ( ( ruleBean == null ) ? site.getSiteUrl() : ruleBean
                                .getFullContentClassPagePublishPath( site, classBean, null, null,
                                    Integer.valueOf( 1 ) )[1] ) );
                    }

                    if( StringUtil.getIntValue( classBean.getListPageLimit(), 0 ) >= pageInfo
                        .getPageCount() )
                    {
                        if( pageInfo.getPageCount() == 0 )
                        {
                            if( pageMode )
                            {
                                pageInfo.setEndQuery( site.getSiteUrl()
                                    + ( ( ruleBean == null ) ? site.getSiteUrl() : ruleBean
                                        .getFullContentClassPagePublishPath( site, classBean, null,
                                            null, Integer.valueOf( 1 ) )[1] ) );
                            }
                        }
                        else
                        {
                            if( pageMode )
                            {
                                pageInfo
                                    .setEndQuery( site.getSiteUrl()
                                        + ( ( ruleBean == null ) ? site.getSiteUrl() : ruleBean
                                            .getFullContentClassPagePublishPath( site, classBean,
                                                null, null, Integer.valueOf( pageInfo
                                                    .getPageCount() ) )[1] ) );
                            }
                        }
                    }

                }
                else
                {
                    // 正常访问,处理首页 尾页 以及 静态最后一页跳转url

                    pageInfo.setNextQuery( nextQuery );

                    Map infoMap = channelService.retrieveClassPublishPageAssistant();

                    // 静态发布方式的上一页链接处理
                    if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean
                        .getClassProduceType() ) )
                    {
                        PublishPageAssistantBean bean = ( PublishPageAssistantBean ) infoMap
                            .get( Long.valueOf( targetClassId ) );

                        if( bean != null )
                        {
                            if( bean.getLastPn().intValue() + 1 == pageInfo.getCurrentPage() )
                            {
                                // 意味着第一个动态页,将使用静态的prevPage地址

                                prevQuery = site.getSiteUrl()
                                    + ( ( PublishPageAssistantBean ) infoMap.get( Long
                                        .valueOf( targetClassId ) ) ).getLastPageStaticUrl();
                            }

                            if( bean.getLastPn().intValue() == pageInfo.getPageCount() )
                            {
                                pageInfo.setEndQuery( site.getSiteUrl()
                                    + ( ( PublishPageAssistantBean ) infoMap.get( Long
                                        .valueOf( targetClassId ) ) ).getLastPageStaticUrl() );
                            }

                            // 因为存在静态分页,必定首页必定为静态第一页
                            if( pageMode )
                            {
                                pageInfo.setHeadQuery( site.getSiteUrl()
                                    + ( ( ruleBean == null ) ? site.getSiteUrl() : ruleBean
                                        .getFullContentClassPagePublishPath( site, classBean, null,
                                            null, Integer.valueOf( 1 ) )[1] ) );
                            }

                        }
                    }

                    pageInfo.setPrevQuery( prevQuery );
                }

                if( "true".equals( pull ) )
                {
                    // 处理下拉请求

                    List result = new ArrayList();

                    if( contentList.isEmpty() )
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
                        if( i >= contentList.size() )
                        {
                            isEnd = true;
                            break;
                        }

                        result.add( contentList.get( i ) );
                    }

                    // 确定站点是否传递错误
                    if( result == null || result.isEmpty() )
                    {
                        Map resMap = new HashMap();

                        resMap.put( "isEnd", true );

                        resMap.put( "content", "" );

                        resMap.put( "size", Integer.valueOf( 0 ) );

                        return ( JSON.toJSONString( resMap ) );
                    }

                    Long cSiteId = ( Long ) ( ( Map ) result.get( 0 ) ).get( "siteId" );

                    if( !site.getSiteId().equals( cSiteId ) )
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

                    resMap.put( "allContent", contentList );

                    resMap.put( "pageInfo", pageInfo );

                    return ( JSON.toJSONString( resMap ) );
                }

            }
        }

        return "";

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
