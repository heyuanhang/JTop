package cn.com.mjsoft.cms.content.html;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
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

public class ClientContentListTagNot_Use extends TagSupport
{
    private static final long serialVersionUID = 5645397079535991773L;

    private static Logger log = Logger.getLogger( ClientContentListTagNot_Use.class );
    private static final long NO_ID_FLAG = -1;

    private static DecimalFormat decimalFormat = new DecimalFormat( Constant.CONTENT.ORDER_FORMAT );

    private static ContentService contentService = ContentService.getInstance();

    private static ChannelService channelService = ChannelService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    // 模型名称
    private String modelName = "";

    // 单个内容表
    private String classId = "";

    // 排除的classId
    private String exClassId = "";

    // 当classID为多id混合取值模式时，需要指定分页模板主栏目
    private String pageClassId = "-1";

    // 获取指定数量的内容,目前不支持x,y这样的选取,使用此属性后,分页将无效
    private String limit;

    // 是否在列表时取所有数据(主表加副表),只在单class下有效
    private String showAll = "true";

    // 按照机构代码获取内容，如001
    private String orgCode = "";

    // 是否取子机构内容
    private String orgChild = "false";

    // 分页
    private String page;

    // 大小默认20
    private String pageSize = "16";

    // 自定义模型ID
    private String modelId = "-1";

    // 筛选分类属性
    private String filter = "";

    // 内容分类属性
    private String typeId = "";

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

    public int doStartTag() throws JspException
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
        //Object headOrderIdFlag = null;
        //Object lastOrderIdFlag = null;

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
              
                
                pageLimitMode = true;
            }
            else if( Constant.CONTENT.ID_ORDER.equals( orderByFlag )
            // || Constant.CONTENT.ADD_DATE_ORDER.equals( orderByFlag )
            )
            {
                // 时间合并ID排序线索标志,为双精度类型线索排序ID
                orderBy = "contentId";

 
                
                pageLimitMode = true;
            }
            else if( Constant.CONTENT.ADD_DATE_ORDER.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.ADD_DATE_ORDER_VAR;
                // orderFilterFlag = Constant.CONTENT.ADD_DATE_ORDER_VAR;

                 

                pageLimitMode = true;
                noTopMode = true;
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

                

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.CLICK_COUNT_ORDER_DAY.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.CLICK_COUNT_ORDER_DAY_VAR;
                orderFilterFlag = Constant.CONTENT.CLICK_COUNT_ORDER_DAY_VAR;

                

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.CLICK_COUNT_ORDER_WEEK.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.CLICK_COUNT_ORDER_WEEK_VAR;
                orderFilterFlag = Constant.CONTENT.CLICK_COUNT_ORDER_WEEK_VAR;

                
                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.CLICK_COUNT_ORDER_MONTH.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.CLICK_COUNT_ORDER_MONTH_VAR;
                orderFilterFlag = Constant.CONTENT.CLICK_COUNT_ORDER_MONTH_VAR;

                 

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.COMM_COUNT_ORDER.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.COMM_COUNT_ORDER_VAR;
                orderFilterFlag = Constant.CONTENT.COMM_COUNT_ORDER_VAR;

                 

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.COMM_COUNT_ORDER_DAY.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.COMM_COUNT_ORDER_DAY_VAR;
                orderFilterFlag = Constant.CONTENT.COMM_COUNT_ORDER_DAY_VAR;

                 

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.COMM_COUNT_ORDER_WEEK.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.COMM_COUNT_ORDER_WEEK_VAR;
                orderFilterFlag = Constant.CONTENT.COMM_COUNT_ORDER_WEEK_VAR;

                

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.COMM_COUNT_ORDER_MONTH.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.COMM_COUNT_ORDER_MONTH_VAR;
                orderFilterFlag = Constant.CONTENT.COMM_COUNT_ORDER_MONTH_VAR;

                 

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.SUPPORT_ORDER.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.SUPPORT_ORDER_VAR;
                orderFilterFlag = Constant.CONTENT.SUPPORT_ORDER_VAR;

                 

                pageLimitMode = true;
                noTopMode = true;
            }
            else if( Constant.CONTENT.AGAINST_ORDER.equals( orderByFlag ) )
            {
                orderBy = Constant.CONTENT.AGAINST_ORDER_VAR;
                orderFilterFlag = Constant.CONTENT.AGAINST_ORDER_VAR;

                 

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

            /**
             * 机构部门筛选,为传统分页
             */
            if( StringUtil.isStringNotNull( orgCode ) )
            {
                pageLimitMode = true;
            }

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
                    //  或提示
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

                // if( !siteMode )
                // {
                // contentList =
                // contentService.retrieveLimitModeContentMainInfoByClassIds(
                // classIds, type, size,
                // orderFilter, orderBy, orderWay );
                // }

                // pageContext.setAttribute( "allContent", contentList );
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
                int possibleCurrentPage = StringUtil.getIntValue( pageContext.getRequest()
                    .getParameter( "pn" ), 1 );

                

                log.info( "[Page] possibleCurrentPage:" + possibleCurrentPage );
                //log.info( "[Page] pageAction:" + pageAction );

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

               

                log.info( "最终结果:" + contentList.size() );

                // 和管理不同,url的组装需要走模板url规则
                // 移动
                String tpl = classBean.getClassTemplateUrl();

                if( StringUtil.isStringNull( tpl ) )
                {
                    tpl = classBean.getMobClassTemplateUrl();
                }

                if( StringUtil.isStringNull( tpl ) )
                {
                    tpl = classBean.getPadClassTemplateUrl();
                }

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

                // 最后分页数据组合
                // pageInfo.setCurrentPage( currentPage );

                String headQuery = null;
                String endQuery = null;
                String jumpQuery = null;

           
             
              
              

                // 只取URL部分,无参数
                // StringBuffer url = ( ( HttpServletRequest )
                // this.pageContext
                // .getRequest() ).getRequestURL();
                // 客户端模式不需要censorBy参数,因为必须是已发布状态的内容

                /**
                 * 所有的分页句只记录分页线索,不记录排序,过滤条件,这些条件由参数给出,若需要,由用户自行加入
                 * 201828:现URL参数带所有排序过滤条件
                 */
                nextQuery = new StringBuffer( url )
                    .append( prefixQuery )
                     
                    .append( "tb=" + type ).append( "&ob=" + order ).append( "&fb=" + filterBy ).append( "&pn=" + (pageInfo.getCurrentPage()+1) )
                    .toString();

                prevQuery = new StringBuffer( url.toString() )
                    .append( prefixQuery )
                     
                    .append( "tb=" + type ).append( "&ob=" + order ).append( "&fb=" + filterBy ).append( "&pn=" + (pageInfo.getCurrentPage()-1) )
                    .toString();

                headQuery = new StringBuffer( url.toString() )
                    .append( prefixQuery )
                   
                    .append( "tb=" + type ).append( "&ob=" + order ).append( "&fb=" + filterBy ).append( "&pn=1" )
                    .toString();

                endQuery = new StringBuffer( url.toString() )
                    .append( prefixQuery )
                    
                    .append( "tb=" + type ).append( "&ob=" + order ).append( "&fb=" + filterBy ).append( "&pn=" + pageInfo.getPageCount() )
                    .toString();

                jumpQuery = new StringBuffer( url.toString() ).append( prefixQuery ).append(
                    "tb=" + type ).append( "&ob=" + order ).append( "&fb=" + filterBy )
                
                    .toString();

                //201828:改动分页规则,增加分页参数入标签
                 pageInfo.setQueryCod( "tb=" + type+"&ob=" + order + "&fb=" + filterBy   );

                // 获取栏目发布规则
                PublishRuleBean ruleBean = publishService.retrieveSinglePublishRuleBean( classBean
                    .getClassPublishRuleId() );

                pageInfo.setNextQuery( nextQuery );
                pageInfo.setPrevQuery( prevQuery );
                pageInfo.setHeadQuery( headQuery );
                pageInfo.setEndQuery( endQuery );
                pageInfo.setJumpQuery( jumpQuery );
                pageInfo.setEndPos( classBean.getListPageLimit() );

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
                    this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );
                }

                /**
                 * 发布逻辑<br>
                 * 1.nextQuery:页面分页,上一页 <br>
                 * 2.prevQuery:页面分页,下一页 <br>
                 * 3.nextQueryActionUrl:系统内部静态化操作所使用下一页地址 <br>
                 * 4.nextStaticPage prevStaticPage:由系统传入静态分页地址,此地址为生成上下一页的标准
                 */
                HttpServletRequest request = ( HttpServletRequest ) this.pageContext.getRequest();

                boolean mob = request.getAttribute( "_pub_mob_" ) != null ? ( Boolean ) request
                    .getAttribute( "_pub_mob_" ) : false;

                boolean pad = request.getAttribute( "_pub_pad_" ) != null ? ( Boolean ) request
                    .getAttribute( "_pub_pad_" ) : false;

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

                    //  
                    // 首先,若是第一次来自静态action,则需要正常处理第一页的情况,且要将分页结果(下一页上一页)静态化
                    // TODO接下来,将下一次分页参数传到静态action,由action再一次处理,模拟用户进行访问,知道处理结束

                }

                // pageContext.getRequest().setAttribute( "page", pageInfo
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
                                    .append( "tb=" + type ).append( "&ob=" + order ).append( "&fb=" + filterBy )
                                    .append( "&pn=" + (pageInfo.getCurrentPage()) )
                                   .toString();
                            }
                             

                            pageInfo.setNextQuery( nextQuery );

                        }

                    }
                    else
                    {
                        // 下一页仍然是静态化系统动作

                        // 必须变化为内部访问地址

                        String pubListUrl = classBean.getClassTemplateUrl();

                        if( mob )
                        {
                            pubListUrl = classBean.getMobClassTemplateUrl();
                        }
                        else if( pad )
                        {
                            pubListUrl = classBean.getPadClassTemplateUrl();
                        }

                        url = site.getSiteRoot()
                            + Constant.CONTENT.URL_SEP
                            + siteTemplate
                            + StringUtil.replaceString( pubListUrl, "{class-id}", Long.valueOf(
                                targetClassId ).toString(), false, false );

                        // 获取系统内部访问URL,过滤参数全

                        if( url.indexOf( ".jsp?" ) != -1 )
                        {
                            publishNextQueryChain = new StringBuffer( Constant.CONTENT.URL_SEP
                                + url ).append( "&tb=" + type ).append( "&fb=" + filterBy ).append(
                                "&pn=" + (pageInfo.getCurrentPage()+1) )  .toString();
                        }
                        else
                        {
                            publishNextQueryChain = new StringBuffer( Constant.CONTENT.URL_SEP
                                + url ).append( "?tb=" + type ).append( "&fb=" + filterBy ).append(
                                "&pn=" + (pageInfo.getCurrentPage()+1) ) .toString();
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
                        String[] sps = ruleBean.getFullContentClassPagePublishPath( site,
                            classBean, null, null, Integer.valueOf( 1 ) );

                        String sp = sps[1];

                        if( mob )
                        {
                            sp = sps[3];
                        }

                        if( pad )
                        {
                            sp = sps[5];
                        }

                        pageInfo.setHeadQuery( site.getSiteUrl() + sp );
                    }

                    if( StringUtil.getIntValue( classBean.getListPageLimit(), 0 ) >= pageInfo
                        .getPageCount() )
                    {
                        if( pageInfo.getPageCount() == 0 )
                        {
                            if( pageMode )
                            {
                                String[] sps = ruleBean.getFullContentClassPagePublishPath( site,
                                    classBean, null, null, Integer.valueOf( 1 ) );

                                String sp = sps[1];

                                if( mob )
                                {
                                    sp = sps[3];
                                }

                                if( pad )
                                {
                                    sp = sps[5];
                                }

                                pageInfo.setEndQuery( site.getSiteUrl() + sp );
                            }
                        }
                        else
                        {
                            if( pageMode )
                            {
                                String[] sps = ruleBean.getFullContentClassPagePublishPath( site,
                                    classBean, null, null, Integer
                                        .valueOf( pageInfo.getPageCount() ) );

                                String sp = sps[1];

                                if( mob )
                                {
                                    sp = sps[3];
                                }

                                if( pad )
                                {
                                    sp = sps[5];
                                }

                                pageInfo.setEndQuery( site.getSiteUrl() + sp );
                            }
                        }
                    }

                }
                else
                {
                    // 正常访问,处理首页 尾页 以及 静态最后一页跳转url

                    pageInfo.setNextQuery( nextQuery );

                    Map infoMap = channelService.retrieveClassPublishPageAssistant();

                    String target = targetClassId + "";

                    if( mob )
                    {
                        target = "mob" + target;
                    }
                    else if( pad )
                    {
                        target = "pad" + target;
                    }

                    // 静态发布方式的上一页链接处理
                    if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean
                        .getClassProduceType() ) )
                    {
                        PublishPageAssistantBean bean = ( PublishPageAssistantBean ) infoMap
                            .get( target );

                        if( bean != null )
                        {
                            if( bean.getLastPn().intValue() + 1 == pageInfo.getCurrentPage() )
                            {
                                // 意味着第一个动态页,将使用静态的prevPage地址

                                prevQuery = site.getSiteUrl()
                                    + ( ( PublishPageAssistantBean ) infoMap.get( target ) )
                                        .getLastPageStaticUrl();
                            }

                            if( bean.getLastPn().intValue() == pageInfo.getPageCount() )
                            {
                                pageInfo.setEndQuery( site.getSiteUrl()
                                    + ( ( PublishPageAssistantBean ) infoMap.get( target ) )
                                        .getLastPageStaticUrl() );
                            }

                            // 因为存在静态分页,必定首页必定为静态第一页
                            if( pageMode )
                            {
                                String[] sps = ruleBean.getFullContentClassPagePublishPath( site,
                                    classBean, null, null, Integer.valueOf( 1 ) );

                                String sp = sps[1];

                                if( mob )
                                {
                                    sp = sps[3];
                                }

                                if( pad )
                                {
                                    sp = sps[5];
                                }

                                pageInfo.setHeadQuery( site.getSiteUrl() + sp );
                            }

                        }
                    }

                    pageInfo.setPrevQuery( prevQuery );
                }

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
 

        return EVAL_BODY_INCLUDE;

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

    public String getLimit()
    {
        return limit;
    }

    public void setLimit( String limit )
    {
        this.limit = limit;
    }

    public void setModelId( String modelId )
    {
        this.modelId = modelId;
    }

    public void setFilter( String filter )
    {
        this.filter = filter;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "allContent" );
        pageContext.removeAttribute( "contentType" );
        return EVAL_PAGE;
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

    public void setTypeId( String typeId )
    {
        this.typeId = typeId;
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
