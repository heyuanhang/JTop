package cn.com.mjsoft.cms.content.html;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.html.ParamUtilTag;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.handler.view.DefaultResultHandler;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientContentTag extends AbstractIteratorTag
{
    private Logger log = Logger.getLogger( ClientContentTag.class );

    private static final long serialVersionUID = -2886317389988178099L;

    private static ContentService contentService = ContentService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    private static ChannelService channelService = ChannelService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    private static DefaultResultHandler resultHandler = new DefaultResultHandler();

    private String objName = "Info";

    private String use404 = "false";

    private String formMode = "false";

    // //////////////////////////////
    // 模型名称
    private String modelName = "";

    // 单个内容表
    private String classId = "";

    // 排除的classId
    private String exClassId = "";

    // 当classID为多id混合取值模式时，需要指定分页模板主栏目
    private String pageClassId = "-1";

    // 是否在列表时取所有数据(主表加副表),只在单class下有效
    private String showAll = "true";

    // 按照机构代码获取内容，如001
    private String orgCode = "";

    // 是否取子机构内容
    private String orgChild = "false";

    // 分页
    private String page;

    // 大小默认20
    private String pageSize = "20";

    // 自定义模型ID
    private String modelId = "-1";

    // 筛选分类属性
    private String filter = "";

    // 内容分类标识
    private String type = "";

    // 排序属性
    private String order = "";

    // 排序属性
    // private String orderBy = "";

    // 排序标准，默认降序
    // private String orderWay = "desc";

    private String startDate = "";

    private String endDate = "";

    protected String returnPutValueName()
    {
        return objName;
    }

    protected String returnRequestAndPageListAttName()
    {
        return "allContent";
    }

    protected String returnValueRange()
    {
        return "selfRange";
    }

    protected Object returnSingleObject()
    {
        Map info = ( Map ) pageContext.getRequest().getAttribute( "Info" );

        // 静态化发布逻辑会根据情况传递已存在的info对象
        if( info != null )
        {
            // 去掉缓存
            pageContext.getRequest().removeAttribute( "Info" );
            return info;
        }

        Long id = Long.valueOf( StringUtil.getLongValue( this.getId(), -1 ) );

        // 从URL获取内容分页参数,如果模型是文章资源类型,会特别处理
        int posVal = StringUtil.getIntValue( pageContext.getRequest().getParameter( "pn" ), 1 );

        // 根据模型ID获取对应contentId的全部信息

        if( "true".equals( formMode ) )
        {
            info = metaDataService.retrieveSingleFormDataById( id );
        }
        else
        {
            info = contentService.retrieveSingleUserDefineContent( id, Integer.valueOf( posVal ) );
        }

        if( "true".equals( use404 ) && info.isEmpty() )
        {

            HttpServletRequest request = ( HttpServletRequest ) pageContext.getRequest();

            HttpServletResponse response = ( HttpServletResponse ) pageContext.getResponse();

            String requestPath = request.getServletPath();

            int pathDepth = StringUtil.getRepeatCharLength( requestPath, Constant.CONTENT.URL_SEP );

            StringBuffer buf = new StringBuffer();

            for ( int i = 0; i < ( pathDepth - 1 ); i++ )
            {
                buf.append( "../" );
            }

            // 应该可以自定义404页
            buf.append( "common/404/404.jsp" );

            response.setStatus( HttpServletResponse.SC_NOT_FOUND );

            resultHandler
                .resolveCustomDirectResult( buf.toString(), request, response, false, null );

            this.setSkipPage();
        }

        // 不允许看到没有审核通过的数据
        if( !Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( ( Integer ) info.get( "censorState" ) ) )
        {
            // view mode

            String pcode = this.pageContext.getRequest().getParameter( "___sys_cms_preview___" );

            if( StringUtil.isStringNotNull( pcode ) )
            {
                String flag = "";

                try
                {
                    flag = ParamUtilTag.decodePW( pcode, "A" );

                }
                catch ( Exception e )
                {
                    flag = "";
                }

                if( flag.startsWith( "true" ) )
                {
                    return info;
                }
            }

            return null;
        }

        //

        return info;
    }

    public void setUse404( String use404 )
    {
        this.use404 = use404;
    }

    protected List returnObjectList()
    {

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
                targetClassId = StringUtil.getLongValue( pageContext.getRequest().getParameter(
                    "classId" ), -1 );
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
        pageContext.setAttribute( "currentModelBean", modelBean );

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
        // Object headOrderIdFlag = null;
        // Object lastOrderIdFlag = null;

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

            }
            else if( Constant.CONTENT.ID_ORDER.equals( orderByFlag )
            // || Constant.CONTENT.ADD_DATE_ORDER.equals( orderByFlag )
            )
            {
                // 时间合并ID排序线索标志,为双精度类型线索排序ID
                orderBy = "contentId";

            }
            else if( Constant.CONTENT.ADD_DATE_ORDER.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.ADD_DATE_ORDER_VAR;
                // orderFilterFlag = Constant.CONTENT.ADD_DATE_ORDER_VAR;

                //noTopMode = true;
            }
            else if( Constant.CONTENT.PUB_DATE_ORDER.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.PUB_DATE_ORDER_VAR;

            }
            // 以下为快速变动条件排序,点击,评论,digg,全部为普通分页,以及无top查询
            else if( Constant.CONTENT.CLICK_COUNT_ORDER.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.CLICK_COUNT_ORDER_VAR;
                orderFilterFlag = Constant.CONTENT.CLICK_COUNT_ORDER_VAR;

                noTopMode = true;
            }
            else if( Constant.CONTENT.CLICK_COUNT_ORDER_DAY.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.CLICK_COUNT_ORDER_DAY_VAR;
                orderFilterFlag = Constant.CONTENT.CLICK_COUNT_ORDER_DAY_VAR;

                noTopMode = true;
            }
            else if( Constant.CONTENT.CLICK_COUNT_ORDER_WEEK.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.CLICK_COUNT_ORDER_WEEK_VAR;
                orderFilterFlag = Constant.CONTENT.CLICK_COUNT_ORDER_WEEK_VAR;

                noTopMode = true;
            }
            else if( Constant.CONTENT.CLICK_COUNT_ORDER_MONTH.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.CLICK_COUNT_ORDER_MONTH_VAR;
                orderFilterFlag = Constant.CONTENT.CLICK_COUNT_ORDER_MONTH_VAR;

                noTopMode = true;
            }
            else if( Constant.CONTENT.COMM_COUNT_ORDER.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.COMM_COUNT_ORDER_VAR;
                orderFilterFlag = Constant.CONTENT.COMM_COUNT_ORDER_VAR;

                noTopMode = true;
            }
            else if( Constant.CONTENT.COMM_COUNT_ORDER_DAY.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.COMM_COUNT_ORDER_DAY_VAR;
                orderFilterFlag = Constant.CONTENT.COMM_COUNT_ORDER_DAY_VAR;

                noTopMode = true;
            }
            else if( Constant.CONTENT.COMM_COUNT_ORDER_WEEK.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.COMM_COUNT_ORDER_WEEK_VAR;
                orderFilterFlag = Constant.CONTENT.COMM_COUNT_ORDER_WEEK_VAR;

                noTopMode = true;
            }
            else if( Constant.CONTENT.COMM_COUNT_ORDER_MONTH.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.COMM_COUNT_ORDER_MONTH_VAR;
                orderFilterFlag = Constant.CONTENT.COMM_COUNT_ORDER_MONTH_VAR;

                noTopMode = true;
            }
            else if( Constant.CONTENT.SUPPORT_ORDER.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.SUPPORT_ORDER_VAR;
                orderFilterFlag = Constant.CONTENT.SUPPORT_ORDER_VAR;

                noTopMode = true;
            }
            else if( Constant.CONTENT.AGAINST_ORDER.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.AGAINST_ORDER_VAR;
                orderFilterFlag = Constant.CONTENT.AGAINST_ORDER_VAR;

                noTopMode = true;
            }

            /**
             * 内容过滤,目前为各引导图类型,且都为传统分页
             */
            if( Constant.CONTENT.HAME_IMG_FILTER.equals( filterBy ) )
            {
                // orderBy = Constant.CONTENT.DEFAULT_ORDER_VAR;
                // orderFilterFlag = Constant.CONTENT.HAME_IMG_FILTER_VAR;

                // noTopMode = true;
            }
            else if( Constant.CONTENT.CHANNEL_IMG_FILTER.equals( filterBy ) )
            {
                // orderBy = Constant.CONTENT.DEFAULT_ORDER_VAR;
                // orderFilterFlag =
                // Constant.CONTENT.CHANNEL_IMG_FILTER_VAR;

                // noTopMode = true;
            }
            else if( Constant.CONTENT.CLASS_IMG_FILTER.equals( filterBy ) )
            {
                // orderBy = Constant.CONTENT.DEFAULT_ORDER_VAR;
                // orderFilterFlag = Constant.CONTENT.CLASS_IMG_FILTER_VAR;

                // noTopMode = true;
            }
            else if( Constant.CONTENT.CONTENT_IMG_FILTER.equals( filterBy ) )
            {
                // orderBy = Constant.CONTENT.DEFAULT_ORDER_VAR;
                // orderFilterFlag =
                // Constant.CONTENT.CONTENT_IMG_FILTER_VAR;

                // noTopMode = true;
            }
            // if( Constant.CONTENT.CREATOR_FILTER.equals( orderByFlag
            // ) )
            // {
            // orderBy = Constant.CONTENT.DEFAULT_ORDER_VAR;
            // orderFilterFlag = Constant.CONTENT.CREATOR_FILTER_VAR;
            //                
            // 
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

            SiteGroupBean site = ( SiteGroupBean ) pageContext.getRequest().getAttribute(
                Constant.CONTENT.HTML_PUB_CURRENT_SITE );

            if( site == null )
            {
                site = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                        .getRequest() );
            }

            if( !singeClassIdMode )
            {
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
                    
                    if(classIds.startsWith( "," ))
                    {
                        classIds = classIds.substring( 1, classIds.length() );
                    }

                }
                else if( classId.startsWith( "allChild:" ) )
                {
                    // 所有孩子及孙子和自己

                    if( classId.endsWith( ":root" ) )
                    {
                        // 全站模式

                        siteMode = true;

                        // contentList =
                        // contentService.retrieveLimitModeContentMainInfo(
                        // site.getSiteId(), type, size,
                        // orderFilter, orderBy, orderWay );
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
 

                // 查询排序条件

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
                int possibleCurrentPage = StringUtil.getIntValue( pageContext.getRequest()
                    .getParameter( "pn" ), 1 );

                log.info( "[Page] possibleCurrentPage:" + possibleCurrentPage );
                // log.info( "[Page] pageAction:" + pageAction );

                // 移动
                Page pageInfo = new Page( ( size == 0 ) ? Page.DEFAULT_PAGE_SIZE : size, count
                    .intValue(), possibleCurrentPage, channelService
                    .retrieveSingleClassBeanInfoByClassId( targetClassId ) );

                // 以下的Page对象的currentPage为正确的下一页
                /**
                 * 3.分页逻辑
                 */

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
                        contentList = contentService.retrieveLimitModeContentTopMode( StringUtil
                            .getBooleanValue( showAll, false ), modelBean, targetClassId,
                            censorByVar, type, startAddTS, endAddTS, pageInfo.getFirstResult(),
                            pageInfo.getPageSize(), orderFilter, orderBy, orderWay );
                    }
                }
                else
                {
                    contentList = contentService.retrieveLimitModeContentMainInfoByClassIds(
                        classIds, type, pageInfo.getFirstResult(), size, orderFilter, orderBy,
                        orderWay );
                }

                log.info( "最终结果:" + contentList.size() );

                // String queryCod = "tb=" + type + "&ob=" + order + "&fb=" +
                // filterBy;

                // 静态分页
                publishService.htmlTagPage( this.pageContext, site, StringUtil.getLongValue(
                    classId, -1 ), classBean, targetClassId, pageInfo, page, "" );

                pageContext.setAttribute( "allContent", contentList );

                // 模型名
                pageContext.setAttribute( "contentType", modelName );

            }
        }
        else
        {

            pageContext.setAttribute( "allContent", contentList );

            // 模型名
            pageContext.setAttribute( "contentType", modelName );

        }

        return contentList;

    }

    protected void initTag()
    {

    }

    public void setObjName( String objName )
    {
        this.objName = objName;
    }

    public void setFormMode( String formMode )
    {
        this.formMode = formMode;
    }

    public void setModelName( String modelName )
    {
        this.modelName = modelName;
    }

    public String getPage()
    {
        return page;
    }

    public void setPage( String page )
    {
        this.page = page;
    }

    public String getPageSize()
    {
        return pageSize;
    }

    public void setPageSize( String pageSize )
    {
        this.pageSize = pageSize;
    }

    public String getClassId()
    {
        return classId;
    }

    public void setClassId( String classId )
    {
        this.classId = classId;
    }

    public void setModelId( String modelId )
    {
        this.modelId = modelId;
    }

    public void setFilter( String filter )
    {
        this.filter = filter;
    }

    public void setStartDate( String startDate )
    {
        this.startDate = startDate;
    }

    public void setEndDate( String endDate )
    {
        this.endDate = endDate;
    }

    public void setOrder( String order )
    {
        this.order = order;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public void setShowAll( String showAll )
    {
        this.showAll = showAll;
    }

    public void setPageClassId( String pageClassId )
    {
        this.pageClassId = pageClassId;
    }

    public void setExClassId( String exClassId )
    {
        this.exClassId = exClassId;
    }

    public void setOrgChild( String orgChild )
    {
        this.orgChild = orgChild;
    }

    public void setOrgCode( String orgCode )
    {
        this.orgCode = orgCode;
    }

}
