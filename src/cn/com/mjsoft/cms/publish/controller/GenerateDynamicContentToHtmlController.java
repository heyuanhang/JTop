package cn.com.mjsoft.cms.publish.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.block.bean.BlockInfoBean;
import cn.com.mjsoft.cms.block.service.BlockService;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.controller.OperationDisposeObserverController;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.publish.bean.GeneratorBean;
import cn.com.mjsoft.cms.publish.bean.PublishRuleBean;
import cn.com.mjsoft.cms.publish.bean.PublishStatusBean;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.cache.http.BufferedResponseWrapper;
import cn.com.mjsoft.framework.cache.http.BufferedResponseWrapper.WrappedOutputStream;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.FlowConstants;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/publish" )
public class GenerateDynamicContentToHtmlController
{
    private static Logger log = Logger.getLogger( GenerateDynamicContentToHtmlController.class );

    private static final Integer pubLimitCount = Integer.valueOf( 500 );

    private String DISPATCH_TEMPLATE_PATH = "";

    private static ChannelService channelService = ChannelService.getInstance();

    private static ContentService contentService = ContentService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    private static BlockService blockService = BlockService.getInstance();

    private static SiteGroupService siteService = SiteGroupService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    // private static MetaDataService metaDataService = MetaDataService
    // .getInstance();

    @ResponseBody
    @RequestMapping( value = "/generateContent.do", method = { RequestMethod.POST,
        RequestMethod.GET } )
    @ActionInfo( traceName = "静态发布" )
    public Object generateContent( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        Map requestParam = ServletUtil.getRequestInfo( request );

        boolean mob = StringUtil.getBooleanValue( ( String ) requestParam.get( "mob" ), false );

        boolean pad = StringUtil.getBooleanValue( ( String ) requestParam.get( "pad" ), false );

        request.setAttribute( "_pub_mob_", false );

        request.setAttribute( "_pub_pad_", false );

        GeneratorBean generatorBean = ( GeneratorBean ) ServletUtil.getValueObject( request,
            GeneratorBean.class );

        if( generatorBean.getStaticType() == -1 )
        {
            Integer staticType = ( Integer ) request.getAttribute( "staticType" );

            String classIdInfo = ( String ) request.getAttribute( "classIdInfo" );

            // 某些flow通过request传递数据
            if( staticType != null )
            {
                generatorBean.setStaticType( staticType.intValue() );

                generatorBean.setClassIdInfo( classIdInfo );
            }
        }

        // 是否来自内部action
        boolean fromAction = ( request.getAttribute( "fromAction" ) == null ) ? false
            : ( ( Boolean ) request.getAttribute( "fromAction" ) ).booleanValue();

        // 内部访问
        boolean innerWaitCensorPublish = false;

        boolean innerThreadPublish = false;

        boolean innerSpecPublish = false;

        Timestamp currTime = null;

        if( "wait".equals( ( String ) requestParam.get( "censor" ) ) )
        {
            innerWaitCensorPublish = true;

            currTime = new Timestamp( StringUtil.getLongValue( ( String ) requestParam
                .get( "currTime" ), -1 ) );
        }

        if( "true".equals( ( String ) requestParam.get( "thread" ) )
            && generatorBean.getJob().booleanValue() )
        {
            innerThreadPublish = true;

            if( "true".equals( ( String ) requestParam.get( "specClass" ) ) )
            {
                innerSpecPublish = true;
            }
        }

        // 设定发布标志位
        request.setAttribute( Constant.CONTENT.HTML_PUB_ACTION_FLAG, Boolean.TRUE );

        // 区块发布回调信息
        List needPubBlockList = new ArrayList();

        // 区块页信息
        request.setAttribute( Constant.CONTENT.HTML_PUB_ACTION_STATIC_BLOCKLIST_FLAG,
            needPubBlockList );

        SiteGroupBean currentSiteBean = null;
        // block类型
        if( generatorBean.getStaticType() == 5 )
        {
            currentSiteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( generatorBean.getSiteId() );

            // 区块静态发布处理逻辑
            needPubBlockList.addAll( blockService.retrieveBlockBeanBySomeFlag( StringUtil
                .changeStringToList( generatorBean.getBlockFlag(), "," ) ) );
        }
        else
        {
            if( innerWaitCensorPublish || innerThreadPublish )
            {
                Long siteId = Long.valueOf( StringUtil.getLongValue( ( String ) requestParam
                    .get( "siteId" ), -1 ) );

                currentSiteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                    .getEntry( siteId );
            }
            else
            {
                SecuritySession securitySession = SecuritySessionKeeper.getSecuritySession();

                if( securitySession != null )
                {
                    currentSiteBean = ( SiteGroupBean ) securitySession.getCurrentLoginSiteInfo();
                }
            }
        }

        if( currentSiteBean.getSiteId() < 0 )
        {
            Long siteId = ( Long ) request.getAttribute( "siteId" );

            currentSiteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( siteId );
        }

        log.info( "[GenerateDynamicContentToHtmlFlow] 当前发布目标站点：" + currentSiteBean );

        // 必须存在当前管理的站点信息
        if( currentSiteBean == null )
        {
            log.error( "站点信息丢失,发布活动终止!" );

            if( generatorBean.getStaticType() == 5 )
            {
                return ServletUtil.redirect( "/core/deploy/ManageStaticPublishBlock.jsp" );
            }

            return ServletUtil.redirect( "/core/deploy/ManageStaicPublishContent.jsp" );
        }

        // 发布文件信息
        List genHtmlFileList = new ArrayList();

        // 将站点资源和URL信息推送到目标页
        request.setAttribute( "ResBase", currentSiteBean.getSiteTemplateUrl() );
        request.setAttribute( "SiteBase", currentSiteBean.getSiteUrl() );
        // 移动站点

        request.setAttribute( "SiteBaseMob", currentSiteBean.getMobSiteUrl() );
        request.setAttribute( "SiteBasePad", currentSiteBean.getPadSiteUrl() );
        request.setAttribute( "___jtopcms_tempplate_charset___", currentSiteBean
            .getTemplateCharset() );

        request.setAttribute( "SiteObj", currentSiteBean );

        // 本次发布唯一识别代码(UUID)
        String pubEventKey = ( String ) requestParam.get( "pubEventKey" );

        // 当前管理站点信息
        request.setAttribute( Constant.CONTENT.HTML_PUB_CURRENT_SITE, currentSiteBean );

        DISPATCH_TEMPLATE_PATH = currentSiteBean.getSiteRoot() + Constant.CONTENT.URL_SEP
            + "template" + Constant.CONTENT.URL_SEP;

        long lx = System.currentTimeMillis();
        // 进入单个内容静态度处理

        // 除文章外,单内容全部合并模型发布,文章模型单独处理,需要内容分页
        Boolean singleContentPublish = ( Boolean ) request.getAttribute( "singleContentPublish" ) == null
            ? Boolean.valueOf( ( String ) requestParam.get( "singleContentPublish" ) )
            : ( Boolean ) request.getAttribute( "singleContentPublish" );

        PublishStatusBean status = new PublishStatusBean();

        /**
         * 单内容发布处理逻辑
         */
        boolean needSyncPubClass = false;

        ContentClassBean syncClassBean = null;

        Long singlePubContentModelId = null;

        Boolean editMode = null;

        // 注册跳转后需要的参数
        Map params = new HashMap();

        if( Boolean.TRUE.equals( singleContentPublish ) )
        {
            singlePubContentModelId = ( Long ) request.getAttribute( "modelId" ) == null ? Long
                .valueOf( ( String ) requestParam.get( "modelId" ) ) : ( Long ) request
                .getAttribute( "modelId" );

            editMode = ( Boolean ) request.getAttribute( "editMode" ) == null ? Boolean
                .valueOf( StringUtil.getBooleanValue( ( String ) requestParam.get( "editMode" ),
                    false ) ) : ( Boolean ) request.getAttribute( "editMode" );

            Long contentId = ( Long ) request.getAttribute( "contentId" ) == null ? Long
                .valueOf( ( String ) requestParam.get( "contentId" ) ) : ( Long ) request
                .getAttribute( "contentId" );

            Long classId = ( Long ) request.getAttribute( "classId" ) == null ? Long
                .valueOf( ( String ) requestParam.get( "classId" ) ) : ( Long ) request
                .getAttribute( "classId" );

            params.put( "fromFlow", "true" );
            params.put( "contentId", contentId );
            // 注意，由于栏目的操作为细粒度权限控制，所以，要放入访问栏目标志ID
            params.put( "classId", classId );
            params.put( "modelId", singlePubContentModelId );
            params.put( "manageParam", request.getAttribute( "manageParam" ) );

            DataModelBean modelBean = metaDataService
                .retrieveSingleDataModelBeanById( singlePubContentModelId );

            if( modelBean == null )
            {
                throw new FrameworkException( "内容模型已经失效！" );
            }

            ContentClassBean classBean = channelService
                .retrieveSingleClassBeanInfoByClassId( classId );

            if( classId == null || classId.longValue() < 1 )
            {
                throw new FrameworkException( "没有选择所要操作的栏目！" );
            }

            log.info( "[发布] 文章类型内容ID为：" + contentId + "的静态化操作开始" );

            // 引入模板进行初始化
            Map mainInfo = contentService.retrieveSingleContentMainInfoMap( contentId );

            // 静态化
            String endStaticClassFilePath = doContentStaticAction( request, response,
                currentSiteBean, classBean, mainInfo, genHtmlFileList, mob, pad );

            // 移动
            if( Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
            {
                // doMobContentStaticAction( request,
                // response, currentSiteBean, classBean, mainInfo,
                // genHtmlFileList, "mob" );
            }

            if( Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
            {
                // doMobContentStaticAction( request,
                // response, currentSiteBean, classBean, mainInfo,
                // genHtmlFileList, "pad" );
            }

            boolean singlePubTemplateError = false;
            if( endStaticClassFilePath == null || endStaticClassFilePath.startsWith( "error:" ) )
            {
                singlePubTemplateError = true;

                if( fromAction )
                {
                    if( editMode.booleanValue() )
                    {
                        return ServletUtil.redirect(
                            "/core/content/EditUserDefineModelContent.jsp", params );
                    }

                    return ServletUtil.redirect( "/core/content/EditUserDefineModelContent.jsp",
                        params );
                }

                return "本次发布失败,请检查栏目 <font color='red'>" + classBean.getClassName()
                    + "</font> 的内容页发布规则配置是否正确";
            }

            // 更新持久数据
            if( !singlePubTemplateError )
            {
                contentService.setContentStaticPageURL( endStaticClassFilePath, contentId );
            }

            log.info( "[发布] " + modelBean.getModelName() + "内容ID为：" + contentId + "的静态化成功" );

            if( classBean.getSyncPubClass().equals( Constant.COMMON.ON ) )
            {
                needSyncPubClass = true;
                syncClassBean = classBean;
            }

            // 须在此同步数据
            status.setTranContentCount( Long.valueOf( genHtmlFileList.size() ) );

            siteService.transferGenHtmlDataToServer( genHtmlFileList, currentSiteBean, status );

            if( !needSyncPubClass )
            {
                if( editMode.booleanValue() )
                {
                    return ServletUtil.redirect( "/core/content/EditUserDefineModelContent.jsp",
                        params );
                }

                return ServletUtil
                    .redirect( "/core/content/EditUserDefineModelContent.jsp", params );
            }

            log.info( "[发布] 没有任何单个内容静态要求" );

        }

        /**
         * 批量内容栏目等发布处理逻辑
         */

        // 记录发布对象状态
        boolean homePubSucc = false;

        boolean channelPubSucc = false;

        boolean listPubSucc = false;

        boolean contentPubSucc = false;

        if( !innerWaitCensorPublish && !innerThreadPublish )
        {
            OperationDisposeObserverController.statusMap.put( pubEventKey, status );
        }

        // 目标发布栏目或专题
        List classBeanList = null;

        // 若不是单独的首页静态化操作，进入栏目和内容处理
        // 0:频道静态 1:栏目静态 2:内容静态 3:全部静态 4:首页静态 5：区块静态 6:needSyncPubClass 同步发布栏目
        // 7:专题类型推荐列表
        if( generatorBean.getStaticType() == 0 || generatorBean.getStaticType() == 1
            || generatorBean.getStaticType() == 2 || generatorBean.getStaticType() == 3
            || generatorBean.getStaticType() == 7 || needSyncPubClass )
        {

            if( innerWaitCensorPublish )
            {
                // 待发布状态获取所有内容静态发布的栏目
                classBeanList = channelService
                    .retrieveAllClassBeanInfoBySiteFlagAndContentPubStatus( currentSiteBean
                        .getSiteFlag(), Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE );
            }
            else if( innerThreadPublish )
            {
                // 发布任务获取所有栏目或专题静态发布的栏目
                if( innerSpecPublish )
                {
                    classBeanList = channelService
                        .retrieveAllClassBeanInfoBySiteFlagAndSpecClassPubStatus( currentSiteBean
                            .getSiteFlag(), Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE );
                }
                else
                {

                    if( requestParam.get( "tcId" ) != null )
                    {
                        classBeanList = new ArrayList();

                        String[] tca = requestParam.get( "tcId" ).toString().split( "," );

                        for ( String tc : tca )
                        {
                            classBeanList.add( channelService
                                .retrieveSingleClassBeanInfoByClassId( StringUtil.getLongValue( tc,
                                    -1 ) ) );
                        }

                    }
                    else
                    {
                        classBeanList = channelService
                            .retrieveAllClassBeanInfoBySiteFlagAndChannelPubStatus( currentSiteBean
                                .getSiteFlag(), Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE );
                    }
                }
            }
            else
            {
                classBeanList = generatorBean.getClassBeanList();
            }

            boolean needContentStaticAction = ( generatorBean.getStaticType() != 1
                && generatorBean.getStaticType() != 0 && generatorBean.getStaticType() != 7 && generatorBean
                .getStaticType() >= 0 );

            boolean needClassStaticAction = ( generatorBean.getStaticType() != 2 && generatorBean
                .getStaticType() >= 0 );

            boolean needSecListStaticAction = ( generatorBean.getStaticType() == 7 );

            // 同步发布栏目的情况
            if( needSyncPubClass )
            {
                classBeanList = new ArrayList( 1 );
                classBeanList.add( syncClassBean );
            }

            // 当发布选项为栏目全部,则要先便历所有栏目,更新全部栏目和频道静态地址,以便发布内容时使用最新的栏目信息
            if( generatorBean.getStaticType() == 3 )
            {
                // 更新所有需要静态化的栏目的静态URL
                List currentSiteAllClassBeanList = channelService
                    .retrieveAllClassBeanInfoBySiteFlag( currentSiteBean.getSiteFlag() );

                ContentClassBean siteClassBean = null;

                for ( int i = 0; i < currentSiteAllClassBeanList.size(); i++ )
                {
                    siteClassBean = ( ContentClassBean ) currentSiteAllClassBeanList.get( i );

                    // 列表静态URL
                    if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( siteClassBean
                        .getClassProduceType() ) )
                    {
                        PublishRuleBean ruleBean = publishService
                            .retrieveSinglePublishRuleBean( siteClassBean.getClassPublishRuleId() );

                        // 不再提示异常,无URL规则的栏目,而直接给予站点URL
                        if( ruleBean == null )
                        {
                            return "本次发布失败,请检查栏目 <fontcolor='red'>" + siteClassBean.getClassName()
                                + "</font> 的列表页发布规则配置是否正确";
                        }

                        channelService.setChannelClassStaticPageURL( ruleBean == null
                            ? currentSiteBean.getSiteUrl() : ruleBean
                                .getFullContentClassPagePublishPath( currentSiteBean,
                                    siteClassBean, null, null, Integer.valueOf( 1 ) )[1],
                            siteClassBean.getClassId() );
                    }

                    // 频道静态URL
                    if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( siteClassBean
                        .getClassHomeProduceType() ) )
                    {
                        PublishRuleBean ruleBean = publishService
                            .retrieveSinglePublishRuleBean( siteClassBean
                                .getClassHomePublishRuleId() );

                        // 不再提示异常,无URL规则的栏目,而直接给予站点URL
                        if( ruleBean == null )
                        {
                            return "本次发布失败,请检查栏目 <fontcolor='red'>" + siteClassBean.getClassName()
                                + "</font> 的频道页发布规则配置是否正确";
                        }

                        channelService.setChannelClassHomeStaticPageURL( ruleBean == null
                            ? currentSiteBean.getSiteUrl() : ruleBean
                                .getFullContentClassPagePublishPath( currentSiteBean,
                                    siteClassBean, null, null, Integer.valueOf( 1 ) )[1],
                            siteClassBean.getClassId() );
                    }
                }
            }

            /**
             * 处理多个内容ID发布的情况
             */
            String someContentId = ( String ) requestParam.get( "someContentId" );

            if( StringUtil.isStringNull( someContentId ) )
            {
                someContentId = ( String ) request.getAttribute( "someContentId" );
            }

            if( needContentStaticAction && StringUtil.isStringNotNull( someContentId ) )
            {
                String[] targetIdArray = StringUtil.split( someContentId, "," );
                Integer censor = null;
                Long targetContentId = null;
                Long targetClassId = null;
                Map infoMap = null;
                Map contentInfo = null;
                ContentClassBean classBean = null;

                for ( int i = 0; i < targetIdArray.length; i++ )
                {
                    targetContentId = Long
                        .valueOf( StringUtil.getLongValue( targetIdArray[i], -1 ) );
                    if( targetContentId.longValue() > 0 )
                    {
                        contentInfo = contentService.retrieveContentPublishInfo( targetContentId );

                        censor = ( Integer ) contentInfo.get( "censorState" );

                        // 审核状态是否正常
                        if( censor == null
                            || !Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( censor ) )
                        {
                            continue;
                        }

                        targetClassId = ( Long ) contentInfo.get( "classId" );

                        classBean = channelService
                            .retrieveSingleClassBeanInfoByClassId( targetClassId );

                        // String template = Constant.CONTENT.URL_SEP
                        // + DISPATCH_TEMPLATE_PATH
                        // + classBean.getContentTemplateUrl();
                        //
                        // String esTemplate = ( String ) contentInfo
                        // .get( "especialTemplateUrl" );
                        //
                        // if( StringUtil.isStringNull( esTemplate ) )
                        // {
                        // template = StringUtil.replaceString( template,
                        // "{content-id}", targetContentId.toString(),
                        // false, false );
                        // }
                        // else
                        // {
                        // template = StringUtil.replaceString( esTemplate,
                        // "{content-id}", targetContentId.toString(),
                        // false, false );
                        // }

                        /*
                         * infoMap = new HashMap( 5 ); infoMap.put(
                         * Constant.METADATA.CONTENT_ID_NAME, targetContentId );
                         */

                        infoMap = contentService.retrieveSingleContentMainInfoMap( targetContentId );

                        String endStaticClassFilePath = doContentStaticAction( request, response,
                            currentSiteBean, classBean, infoMap, genHtmlFileList, mob, pad );

                        if( Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                        {
                            // doMobContentStaticAction( request,
                            // response, currentSiteBean, classBean, infoMap,
                            // genHtmlFileList, "mob" );
                        }

                        if( Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                        {
                            // doMobContentStaticAction( request,
                            // response, currentSiteBean, classBean, infoMap,
                            // genHtmlFileList, "pad" );
                        }

                        if( StringUtil.isStringNull( endStaticClassFilePath )
                            || "error:template".equals( endStaticClassFilePath ) )
                        {
                            if( fromAction )
                            {
                                if( "true".equals( request.getAttribute( "addOrEditFlag" ) ) )
                                {
                                    // 添加编辑内容逻辑进入需要返回管理页
                                    Map returnParams = new HashMap();
                                    returnParams.put( "classId", request.getAttribute( "classId" ) );
                                    returnParams.put( "modelId", request.getAttribute( "modelId" ) );

                                    return ServletUtil.redirect(
                                        "/core/content/EditUserDefineModelContent.jsp",
                                        returnParams );
                                }

                                // 管理页AJAX进入
                                return "success";
                            }

                            return "本次发布失败,请检查栏目 <font color='red'>" + classBean.getClassName()
                                + "</font> 的内容页发布规则配置以及模板语法是否正确";
                        }

                        // 更新持久数据
                        contentService.setContentStaticPageURL( endStaticClassFilePath,
                            targetContentId );

                    }
                }

                // 须在此同步数据
                status.setTranContentCount( Long.valueOf( genHtmlFileList.size() ) );

                siteService.transferGenHtmlDataToServer( genHtmlFileList, currentSiteBean, status );

                if( "true".equals( request.getAttribute( "addOrEditFlag" ) ) )
                {
                    // 添加编辑内容逻辑进入需要返回管理页
                    Map returnParams = new HashMap();
                    returnParams.put( "classId", request.getAttribute( "classId" ) );
                    returnParams.put( "modelId", request.getAttribute( "modelId" ) );

                    if( Boolean.TRUE.equals( request.getAttribute( "fromFlow" ) ) )
                    {
                        // 注册跳转后需要的参数
                        params = new HashMap();

                        params.put( "fromFlow", "true" );
                        params.put( "contentId", request.getAttribute( "contentId" ) );
                        // 注意，由于栏目的操作为细粒度权限控制，所以，要放入访问栏目标志ID
                        params.put( "classId", request.getAttribute( "classId" ) );
                        params.put( "modelId", request.getAttribute( "modelId" ) );
                        params.put( "manageParam", request.getAttribute( "manageParam" ) );

                        return ServletUtil.redirect(
                            "/core/content/EditUserDefineModelContent.jsp", params );
                    }

                    return ServletUtil.redirect( "/core/content/EditUserDefineModelContent.jsp",
                        returnParams );
                }

                // 管理页AJAX进入
                return "success";
            }

            // 对于idArray中的每一个ID代表的栏目，将根据静态类型进行操作

            // 将优化效率，考虑多线程等
            /**
             * 按照栏目发布各内容
             */
            // 获取添加时间筛选条件
            Timestamp startAddTS = null;
            Timestamp endAddTS = null;
            if( StringUtil.isStringNotNull( generatorBean.getStartAddDate() )
                || StringUtil.isStringNotNull( generatorBean.getEndAddDate() ) )
            {
                startAddTS = DateAndTimeUtil.getTimestamp( generatorBean.getStartAddDate()
                    + " 00:00:00", DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );

                endAddTS = DateAndTimeUtil.getTimestamp( generatorBean.getEndAddDate()
                    + " 23:59:59", DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );

                if( startAddTS == null )
                {
                    startAddTS = Constant.CONTENT.MIN_DATE;
                }

                if( endAddTS == null )
                {
                    endAddTS = Constant.CONTENT.MAX_DATE;
                }
            }

            // 如果是全静态,要优先处理栏目静态规则,即先使用最新的静态规则,以便内容发布知道栏目的URL变化
            ContentClassBean classBean = null;

            /**
             * 首先确定获取总页数,包含所有栏目和内容
             */
            long allNeedStaticPageCount = 0;

            if( !innerWaitCensorPublish )
            {
                for ( int i = 0; i < classBeanList.size(); i++ )
                {
                    classBean = ( ContentClassBean ) classBeanList.get( i );

                    // 检查当前管理的栏目所在站点是否是权限站点一致
                    if( !classBean.getSiteFlag().equals( currentSiteBean.getSiteFlag() ) )
                    {
                        log.info( "[发布] 将要处理的栏目所属站点没有被授权! classId:" + classBean.getClassId() );
                        continue;
                    }

                    long classId = classBean.getClassId().longValue();

                    log.info( "[发布] 将要处理的栏目ID为：" + classId + " ,处理模式为："
                        + generatorBean.getStaticType() );

                    if( classId < 1 )
                    {
                        // 异常处理
                        throw new FrameworkException( "没有选择所要操作的栏目！" );

                    }

                    classBean = channelService.retrieveSingleClassBeanInfoByClassId( classBean
                        .getClassId() );

                    // 栏目总内容数
                    if( needContentStaticAction
                        && ( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean
                            .getContentProduceType() ) ) )
                    {
                        // 获取当前栏目需要静态发布的内容数

                        if( !Constant.SITE_CHANNEL.CLASS_TYPE_SINGLE_PAGE.equals( classBean
                            .getClassType() ) )
                        {
                            Long pubCount = contentService
                                .retrieveNeedPublishContentCountByClassIDAndModelIdAndFlag( Long
                                    .valueOf( classId ), Long.valueOf( classBean.getContentType()
                                    .longValue() ), Double
                                    .valueOf( Constant.CONTENT.MAX_ORDER_ID_FLAG ), startAddTS,
                                    endAddTS );

                            allNeedStaticPageCount += pubCount.longValue();
                        }
                        else
                        {
                            allNeedStaticPageCount += 0;
                        }
                    }

                    // 栏目总列表数
                    // TODO 处理栏目首页 ||
                    // Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals(
                    // classBean.getClassHomeProduceType() )

                    if( ( needClassStaticAction || needSyncPubClass )
                        && ( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean
                            .getClassProduceType() ) || Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE
                            .equals( classBean.getClassHomeProduceType() ) ) )
                    {
                        if( !Constant.SITE_CHANNEL.CLASS_TYPE_SINGLE_PAGE.equals( classBean
                            .getClassType() ) )
                        {
                            int allClassPageCount = 0;

                            if( needSecListStaticAction )
                            {
                                // 获取专题下所有栏目(推荐列表)列表数
                                List commTypeList = channelService.retrieveContentCommendTypeBean(
                                    currentSiteBean.getSiteFlag(), classBean.getClassId(), false,
                                    true, false );

                                ContentCommendTypeBean commTypeBean = null;

                                for ( int icl = 0; icl < commTypeList.size(); icl++ )
                                {
                                    commTypeBean = ( ContentCommendTypeBean ) commTypeList
                                        .get( icl );

                                    if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE
                                        .equals( commTypeBean.getListProduceType() ) )
                                    {
                                        continue;
                                    }

                                    HttpServletResponse responseWrapper = new BufferedResponseWrapper(
                                        response );

                                    responseWrapper.setCharacterEncoding( currentSiteBean
                                        .getTemplateCharset() );

                                    // 此出的静态化独立为模版的编码，是否和系统的编码一致，后期要解决
                                    request.setCharacterEncoding( currentSiteBean
                                        .getTemplateCharset() );
                                    response.setCharacterEncoding( currentSiteBean
                                        .getTemplateCharset() );

                                    // 获取栏目发布规则
                                    PublishRuleBean ruleBean = publishService
                                        .retrieveSinglePublishRuleBean( commTypeBean
                                            .getListPublishRuleId() );

                                    if( ruleBean == null )
                                    {
                                        return "本次发布失败,请检查专题子分类 <font color='red'>"
                                            + commTypeBean.getCommendName()
                                            + "</font> 的列表页发布规则配置是否正确";
                                    }

                                    // 访问目标页获取分页对象

                                    // 处理栏目参数
                                    String secListTemplateUrl = commTypeBean.getListTemplateUrl();

                                    if( mob
                                        && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                                    {
                                        secListTemplateUrl = commTypeBean.getMobListTemplateUrl();

                                        request.setAttribute( "_pub_mob_", mob );
                                    }

                                    if( pad
                                        && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                                    {
                                        secListTemplateUrl = commTypeBean.getPadListTemplateUrl();

                                        request.setAttribute( "_pub_pad_", pad );
                                    }

                                    secListTemplateUrl = StringUtil.replaceString(
                                        secListTemplateUrl, "{type-id}", commTypeBean
                                            .getCommendTypeId().toString(), false, false );

                                    try
                                    {
                                        // freemarker
                                        if( secListTemplateUrl.indexOf( ".thtml?" ) != -1
                                            || secListTemplateUrl.indexOf( ".thtml" ) != -1 )
                                        {
                                            request.setAttribute( "param",
                                                paramMap( secListTemplateUrl ) );

                                            request.setAttribute( "request", request );
                                        }

                                        request.getRequestDispatcher(
                                            Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
                                                + secListTemplateUrl ).include( request,
                                            responseWrapper );
                                    }
                                    catch ( Exception e )
                                    {
                                        return "本次发布失败,请检查专题子分类 <font color='red'>"
                                            + classBean.getClassName() + "</font> 的列表页模板语法是否符合规则!";
                                    }

                                    // 本次分页信息
                                    Page pageInfo = ( Page ) request
                                        .getAttribute( "___system_dispose_page_object_for_pub___" );

                                    // 若最大分页数不存在或分页数小于最大分页数

                                    if( pageInfo == null )
                                    {
                                        allClassPageCount += 1;
                                    }
                                    else
                                    {
                                        allClassPageCount += pageInfo.getPageCount() == 0 ? 1
                                            : pageInfo.getPageCount();
                                    }

                                    request
                                        .removeAttribute( "___system_dispose_page_object_for_pub___" );

                                    request.removeAttribute( "nextQueryActionUrl" );

                                }
                            }
                            else
                            {
                                if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean
                                    .getClassProduceType() )
                                    && Constant.COMMON.OFF.equals( classBean.getIsSpecial() ) )
                                {
                                    HttpServletResponse responseWrapper = new BufferedResponseWrapper(
                                        response );

                                    responseWrapper.setCharacterEncoding( currentSiteBean
                                        .getTemplateCharset() );

                                    // 此出的静态化独立为模版的编码
                                    request.setCharacterEncoding( currentSiteBean
                                        .getTemplateCharset() );
                                    response.setCharacterEncoding( currentSiteBean
                                        .getTemplateCharset() );

                                    // 获取栏目发布规则
                                    PublishRuleBean ruleBean = publishService
                                        .retrieveSinglePublishRuleBean( classBean
                                            .getClassPublishRuleId() );

                                    if( ruleBean == null )
                                    {
                                        if( singleContentPublish.booleanValue() )
                                        {
                                            // 编辑或增模式下,不提示,直接返回
                                            if( editMode.booleanValue() )
                                            {
                                                return ServletUtil.redirect(
                                                    "/core/content/EditUserDefineModelContent.jsp",
                                                    params );
                                            }

                                            return ServletUtil.redirect(
                                                "/core/content/EditUserDefineModelContent.jsp",
                                                params );
                                        }
                                        else
                                        {
                                            return "本次发布失败,请检查栏目 <font color='red'>"
                                                + classBean.getClassName()
                                                + "</font> 的列表页发布规则配置是否正确";
                                        }
                                    }

                                    String siteFilePath = ServletUtil.getSiteFilePath( request
                                        .getServletContext() );

                                    // 设定参数,当前栏目的列表页分页数

                                    // 计数,pn
                                    Integer disposePageCurrentCount = Integer.valueOf( 1 );

                                    request.setAttribute( "genPageSize", Integer
                                        .valueOf( StringUtil.getIntValue( classBean
                                            .getListPageLimit(), 0 ) ) );

                                    String[] pathInfo = null;
                                    String htmlRootPath = siteFilePath
                                        + currentSiteBean.getSiteRoot() + File.separator
                                        + currentSiteBean.getPublishRoot() + File.separator;
                                    // 不使用html目录,按照rule划分目录

                                    // if( ruleBean.getNeedPage().intValue() ==
                                    // 1 )
                                    if( StringUtil.getIntValue( classBean.getListPageLimit(), 0 ) > 0 )
                                    {
                                        request.setAttribute( "needPage", Boolean.TRUE );

                                        // prevPage传递给下一次访问,表示当前静态化后的当前页是它的上一页

                                        String[] sps = ruleBean.getFullContentClassPagePublishPath(
                                            currentSiteBean, classBean, null, siteFilePath,
                                            disposePageCurrentCount );

                                        String sp = sps[1];

                                        if( mob
                                            && Constant.COMMON.ON.equals( currentSiteBean
                                                .getMobVm() ) )
                                        {
                                            sp = sps[3];
                                        }

                                        if( pad
                                            && Constant.COMMON.ON.equals( currentSiteBean
                                                .getPadVm() ) )
                                        {
                                            sp = sps[5];
                                        }

                                        request.setAttribute( "prevStaticPage", sp );

                                        sps = ruleBean.getFullContentClassPagePublishPath(
                                            currentSiteBean, classBean, null, siteFilePath, Integer
                                                .valueOf( disposePageCurrentCount.intValue() + 1 ) );

                                        sp = sps[1];

                                        if( mob
                                            && Constant.COMMON.ON.equals( currentSiteBean
                                                .getMobVm() ) )
                                        {
                                            sp = sps[3];
                                        }

                                        if( pad
                                            && Constant.COMMON.ON.equals( currentSiteBean
                                                .getPadVm() ) )
                                        {
                                            sp = sps[5];
                                        }

                                        request.setAttribute( "nextStaticPage", sp );

                                        // 只分一页的情况,所以页码不会增加

                                        sps = ruleBean.getFullContentClassPagePublishPath(
                                            currentSiteBean, classBean, null, siteFilePath, Integer
                                                .valueOf( disposePageCurrentCount.intValue() ) );

                                        sp = sps[1];

                                        if( mob
                                            && Constant.COMMON.ON.equals( currentSiteBean
                                                .getMobVm() ) )
                                        {
                                            sp = sps[3];
                                        }

                                        if( pad
                                            && Constant.COMMON.ON.equals( currentSiteBean
                                                .getPadVm() ) )
                                        {
                                            sp = sps[5];
                                        }

                                        request.setAttribute( "nextStaticPageOnlyOne", sp );

                                        pathInfo = ruleBean.getFullContentClassPagePublishPath(
                                            currentSiteBean, classBean, null, siteFilePath,
                                            disposePageCurrentCount );

                                    }
                                    else
                                    {
                                        // 不需分页
                                        pathInfo = ruleBean.getFullContentClassPublishPath(
                                            currentSiteBean, classBean, null, siteFilePath );
                                    }

                                    // 栏目非分页第一次访问
                                    // 1.存储静态动作
                                    // channelService
                                    // .setChannelClassStaticPageURL(
                                    // pathInfo[1], Long.valueOf( classId ) );

                                    // 处理栏目参数
                                    String classTemplateUrl = classBean.getClassTemplateUrl();

                                    if( mob
                                        && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                                    {
                                        classTemplateUrl = classBean.getMobClassTemplateUrl();

                                        request.setAttribute( "_pub_mob_", mob );
                                    }

                                    if( pad
                                        && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                                    {
                                        classTemplateUrl = classBean.getPadClassTemplateUrl();

                                        request.setAttribute( "_pub_pad_", pad );
                                    }

                                    classTemplateUrl = StringUtil.replaceString( classTemplateUrl,
                                        "{class-id}", classBean.getClassId().toString(), false,
                                        false );

                                    // 所有模板访问出现异常需要提示

                                    try
                                    {
                                        // freemarker
                                        if( classTemplateUrl.indexOf( ".thtml?" ) != -1
                                            || classTemplateUrl.indexOf( ".thtml" ) != -1 )
                                        {
                                            request.setAttribute( "param",
                                                paramMap( classTemplateUrl ) );

                                            request.setAttribute( "request", request );
                                        }

                                        request.getRequestDispatcher(
                                            Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
                                                + classTemplateUrl ).include( request,
                                            responseWrapper );
                                    }
                                    catch ( Exception e )
                                    {
                                        e.printStackTrace();
                                        if( singleContentPublish.booleanValue() )
                                        {
                                            // 编辑或增模式下,不提示,直接返回
                                            if( editMode.booleanValue() )
                                            {
                                                return ServletUtil.redirect(
                                                    "/core/content/EditUserDefineModelContent.jsp",
                                                    params );
                                            }

                                            return ServletUtil.redirect(
                                                "/core/content/EditUserDefineModelContent.jsp",
                                                params );
                                        }
                                        else
                                        {
                                            return "本次发布失败,请检查栏目 <font color='red'>"
                                                + classBean.getClassName()
                                                + "</font> 的列表页模板语法是否符合规则!";
                                        }
                                    }

                                    // remove不需要的数据
                                    request.removeAttribute( "headPageFlag" );
                                    request.removeAttribute( "nextStaticPageOnlyOne" );

                                    responseWrapper.flushBuffer();

                                    String filePath = pathInfo[0];

                                    if( mob
                                        && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                                    {
                                        filePath = pathInfo[2];
                                    }

                                    if( pad
                                        && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                                    {
                                        filePath = pathInfo[4];
                                    }

                                    BufferedResponseWrapper.writeHtml(
                                        ( WrappedOutputStream ) responseWrapper.getOutputStream(),
                                        htmlRootPath + filePath );

                                    // 本次分页信息
                                    Page pageInfo = ( Page ) request
                                        .getAttribute( "___system_dispose_page_object_for_pub___" );

                                    int listPageLimit = StringUtil.getIntValue( classBean
                                        .getListPageLimit(), 0 );

                                    if( listPageLimit == 0 || pageInfo == null
                                        || pageInfo.getPageCount() < listPageLimit )
                                    {
                                        // 若最大分页数不存在或分页数小于最大分页数

                                        if( pageInfo == null )
                                        {
                                            allClassPageCount = 1;
                                        }
                                        else
                                        {
                                            allClassPageCount = pageInfo.getPageCount() == 0 ? 1
                                                : pageInfo.getPageCount();
                                        }
                                    }
                                    else
                                    {
                                        // 最大分页数存在且有效
                                        allClassPageCount = listPageLimit;
                                    }

                                    request
                                        .removeAttribute( "___system_dispose_page_object_for_pub___" );

                                    // 移动
                                    if( Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                                    {
                                        // doMobContentClassStaticAction(
                                        // request,
                                        // response, classBean, currentSiteBean,
                                        // genHtmlFileList, "list" ,"channel");
                                    }

                                    if( Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                                    {
                                        // doMobContentClassStaticAction(
                                        // request,
                                        // response, classBean, currentSiteBean,
                                        // genHtmlFileList, "pad", "list" );
                                    }
                                }

                                // 若存在频道静态,则增加发布数
                                if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean
                                    .getClassHomeProduceType() ) )
                                {
                                    allClassPageCount++;
                                }

                            }

                            allNeedStaticPageCount += allClassPageCount;

                        }
                        else
                        {
                            allNeedStaticPageCount += 1;
                        }

                    }
                }
            }

            // 设定总页

            if( OperationDisposeObserverController.statusMap.get( pubEventKey ) == null
                && !innerWaitCensorPublish && !innerThreadPublish )
            {
                // 是否状态已丢失,一般认为Client已经关闭了此操作
                return FlowConstants.NULL_RESULT;
            }

            status.setPubCount( Long.valueOf( allNeedStaticPageCount ) );

            /**
             * 静态发布开始,注意要区分内部访问和一般访问的返回值处理
             */
            boolean notHaveWaitPublishContent = false;

            for ( int i = 0; i < classBeanList.size(); i++ )
            {
                classBean = ( ContentClassBean ) classBeanList.get( i );

                // 检查当前管理的栏目所在站点是否是权限站点一致
                if( !classBean.getSiteFlag().equals( currentSiteBean.getSiteFlag() ) )
                {
                    log.info( "[发布] 将要处理的栏目所属站点没有被授权! classId:" + classBean.getClassId() );
                    continue;
                }

                long classId = classBean.getClassId().longValue();

                log
                    .info( "[发布] 将要处理的栏目ID为：" + classId + " ,处理模式为："
                        + generatorBean.getStaticType() );

                if( classId < 1 )
                {
                    // 异常处理
                    throw new FrameworkException( "没有选择所要操作的栏目！" );

                }

                classBean = channelService.retrieveSingleClassBeanInfoByClassId( classBean
                    .getClassId() );

                // 1:栏目静态要求 - 栏目静态后，将更新对应的栏目页
                // 2:内容静态要求 - 内容静态后，将不更新对应的栏目页
                // 3:全部静态要求 - 全部静态后，同时将更新所有
                // 若内容静态化指令存在且静态配置存在,则进行栏目下内容静态化操作
                if( needContentStaticAction
                    && ( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean
                        .getContentProduceType() ) )
                    && !Constant.SITE_CHANNEL.CLASS_TYPE_SINGLE_PAGE.equals( classBean
                        .getClassType() ) )
                {
                    // 1.取内容
                    List contentList = null;

                    long contentModelTypeId = classBean.getContentType().longValue();
                    String idFlag = Constant.METADATA.CONTENT_ID_NAME;

                    // 获取指定批量的内容数据

                    if( innerWaitCensorPublish )
                    {
                        contentList = contentService
                            .retrieveWaitPublishContentBySiteIdAndCurrentDate( Long
                                .valueOf( classId ), Long.valueOf( contentModelTypeId ), Double
                                .valueOf( Constant.CONTENT.MAX_ORDER_ID_FLAG ), pubLimitCount );

                        if( contentList.isEmpty() )
                        {
                            notHaveWaitPublishContent = true;
                        }
                    }
                    else
                    {
                        contentList = contentService
                            .retrieveNeedPublishContentByClassIDAndModelIdAndFlag( Long
                                .valueOf( classId ), Long.valueOf( contentModelTypeId ), Double
                                .valueOf( Constant.CONTENT.MAX_ORDER_ID_FLAG ), pubLimitCount,
                                startAddTS, endAddTS );
                    }

                    long l1 = System.currentTimeMillis();

                    List endStaticURLInfoList = new ArrayList();

                    String siteFilePath = ServletUtil.getSiteFilePath( request.getServletContext() );

                    String htmlRootPath = siteFilePath + currentSiteBean.getSiteRoot()
                        + File.separator + currentSiteBean.getPublishRoot() + File.separator;
                    // Iterator it = contentMap.keySet().iterator();

                    /**
                     * 发布规则获取
                     */
                    PublishRuleBean ruleBean = publishService
                        .retrieveSinglePublishRuleBean( classBean.getContentPublishRuleId() );

                    if( ruleBean == null )
                    {
                        if( singleContentPublish.booleanValue() )
                        {
                            // 编辑或增模式下,不提示,直接返回
                            if( editMode.booleanValue() )
                            {
                                return ServletUtil.redirect(
                                    "/core/content/EditUserDefineModelContent.jsp", params );
                            }

                            return ServletUtil.redirect(
                                "/core/content/EditUserDefineModelContent.jsp", params );
                        }
                        else
                        {
                            if( innerWaitCensorPublish || innerThreadPublish )
                            {
                                // 内部访问不需要提示,但也不可返回,而是尝试处理下一笔数据
                                continue;
                            }

                            return "本次发布失败,请检查栏目 <font color='red'>" + classBean.getClassName()
                                + "</font> 的内容页发布规则配置是否正确";

                        }
                    }

                    /**
                     * 发布进度状态
                     */
                    // 获取当前栏目需要静态发布的内容数
                    Long pubCount = contentService
                        .retrieveNeedPublishContentCountByClassIDAndModelIdAndFlag( Long
                            .valueOf( classId ), Long.valueOf( contentModelTypeId ), Double
                            .valueOf( Constant.CONTENT.MAX_ORDER_ID_FLAG ), startAddTS, endAddTS );

                    if( OperationDisposeObserverController.statusMap.get( pubEventKey ) == null
                        && !innerWaitCensorPublish && !innerThreadPublish )
                    {
                        // 是否状态已丢失,一般认为Client已经关闭了此操作
                        return FlowConstants.NULL_RESULT;
                    }

                    status.setCurrPubObj( classBean.getClassName() );

                    Double prevContentOrderId = null;

                    while ( contentList.size() > 0 )
                    {
                        int j = 0;
                        Map contentInfo = null;
                        List contentPageInfoList = new ArrayList();
                        /*
                         * 针对本次查询的已发布文件进行统一静态发布
                         */
                        // 1.查询所有需要内容分页的内容
                        for ( j = 0; j < contentList.size(); j++ )
                        {
                            // Map contentInfo = ( Map ) it.next();
                            contentInfo = ( Map ) contentList.get( j );

                            // 分页类型只有文章大类型才独有
                            if( Constant.COMMON.ON.equals( ( Integer ) contentInfo
                                .get( "isPageContent" ) ) )
                            {
                                // 当前内容为分页内容
                                contentPageInfoList.add( contentInfo.get( "contentId" ) );
                            }
                        }

                        // 2. 内容分页静态化
                        StringBuffer inSQlqueryBuf = new StringBuffer();
                        int cpSize = contentPageInfoList.size();
                        List pageInfoList = null;
                        List endPageStaticUrlList = new ArrayList();

                        for ( j = 0; j < cpSize; j++ )
                        {
                            inSQlqueryBuf.append( contentPageInfoList.get( j ) );
                            if( j + 1 != cpSize )
                            {
                                inSQlqueryBuf.append( "," );
                            }
                        }

                        pageInfoList = contentService.retrieveAllContentPageByIds( inSQlqueryBuf
                            .toString(), Long.valueOf( contentModelTypeId ) );

                        int k = 0;
                        Map pageInfo = null;
                        // 记录静态url
                        for ( k = 0; k < pageInfoList.size(); k++ )
                        {
                            pageInfo = ( Map ) pageInfoList.get( k );

                            endPageStaticUrlList.add( new Object[] {
                                ruleBean.getFullContentClassPagePublishPath( currentSiteBean,
                                    classBean, pageInfo, siteFilePath, ( Integer ) pageInfo
                                        .get( "pos" ) )[1], ( Long ) pageInfo.get( "contentId" ),
                                ( Integer ) pageInfo.get( "pos" ) } );
                            // 分页url更新
                            contentService.setPageContentStaticURL( endPageStaticUrlList );
                        }

                        String template = Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
                            + classBean.getContentTemplateUrl();

                        if( mob && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                        {
                            template = Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
                                + classBean.getMobContentTemplateUrl();
                        }

                        if( pad && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                        {
                            template = Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
                                + classBean.getPadContentTemplateUrl();
                        }

                        // 模版处理
                        for ( k = 0; k < pageInfoList.size(); k++ )
                        {
                            pageInfo = ( Map ) pageInfoList.get( k );
                            if( StringUtil.isStringNull( ( String ) pageInfo
                                .get( "especialTemplateUrl" ) ) )
                            {
                                template = StringUtil.replaceString( template, "{content-id}",
                                    classBean.getClassId().toString(), false, false );
                            }
                            else
                            {
                                template = StringUtil.replaceString( ( String ) pageInfo
                                    .get( "especialTemplateUrl" ), "{content-id}", classBean
                                    .getClassId().toString(), false, false );
                            }
                            // 静态发布内容页分页
                            doContentPageStaticAction( request, response, currentSiteBean,
                                classBean, pageInfo, template, genHtmlFileList, mob, pad );

                            // 移动

                            if( Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                            {
                                // doMobContentPageStaticAction( request,
                                // response,
                                // currentSiteBean, classBean, pageInfo,
                                // genHtmlFileList, "mob" );
                            }

                            if( Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                            {
                                // doMobContentPageStaticAction( request,
                                // response,
                                // currentSiteBean, classBean, pageInfo,
                                // genHtmlFileList, "pad" );
                            }
                        }

                        // 3.单页内容静态化
                        for ( j = 0; j < contentList.size(); j++ )
                        {
                            // Map contentInfo = ( Map ) it.next();
                            contentInfo = ( Map ) contentList.get( j );

                            // 2.生成静态文件
                            HttpServletResponse responseWrapper = new BufferedResponseWrapper(
                                response );

                            responseWrapper.setCharacterEncoding( currentSiteBean
                                .getTemplateCharset() );

                            // 关于传入的参数,以后要做处理,不可能仅仅是ID

                            request.setCharacterEncoding( currentSiteBean.getTemplateCharset() );
                            response.setCharacterEncoding( currentSiteBean.getTemplateCharset() );

                            /**
                             * 在进行静态发布之间,将info传给标签,使得不需要重复查询数据库
                             */
                            request.setAttribute( Constant.CONTENT.HTML_PUB_ACTION_FLAG,
                                Boolean.TRUE );
                            request.setAttribute( "Info", contentInfo );

                            template = Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
                                + classBean.getContentTemplateUrl();

                            if( mob && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                            {
                                template = Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
                                    + classBean.getMobContentTemplateUrl();
                            }

                            if( pad && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                            {
                                template = Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
                                    + classBean.getPadContentTemplateUrl();
                            }

                            if( StringUtil.isStringNull( ( String ) contentInfo
                                .get( "especialTemplateUrl" ) ) )
                            {
                                template = StringUtil
                                    .replaceString( template, "{content-id}",
                                        ( ( Long ) contentInfo.get( idFlag ) ).toString(), false,
                                        false );
                            }
                            else
                            {
                                template = StringUtil
                                    .replaceString( ( String ) contentInfo
                                        .get( "especialTemplateUrl" ), "{content-id}",
                                        ( ( Long ) contentInfo.get( idFlag ) ).toString(), false,
                                        false );
                            }

                            try
                            {
                                // freemarker
                                if( template.indexOf( ".thtml?" ) != -1
                                    || template.indexOf( ".thtml" ) != -1 )
                                {
                                    request.setAttribute( "param", paramMap( template ) );

                                    request.setAttribute( "request", request );
                                }

                                request.getRequestDispatcher( template ).include( request,
                                    responseWrapper );
                            }
                            catch ( Exception e )
                            {
                                if( singleContentPublish.booleanValue() )
                                {
                                    // 编辑或增模式下,不提示,直接返回
                                    if( editMode.booleanValue() )
                                    {
                                        return ServletUtil.redirect(
                                            "/core/content/EditUserDefineModelContent.jsp", params );
                                    }

                                    return ServletUtil.redirect(
                                        "/core/content/EditUserDefineModelContent.jsp", params );
                                }
                                else
                                {
                                    if( innerWaitCensorPublish || innerThreadPublish )
                                    {
                                        // 内部访问不需要提示,但也不可返回,而是尝试处理下一笔数据
                                        continue;
                                    }

                                    return "本次发布失败,请检查栏目 <font color='red'>"
                                        + classBean.getClassName() + "</font> 的内容页模板是否符合规则!";
                                }
                            }

                            responseWrapper.flushBuffer();

                            WrappedOutputStream contentOutputStream = ( WrappedOutputStream ) responseWrapper
                                .getOutputStream();

                            String[] pathInfo = ruleBean.getFullContentClassPublishPath(
                                currentSiteBean, classBean, contentInfo, siteFilePath );

                            String filePath = pathInfo[0];

                            if( mob && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                            {
                                filePath = pathInfo[2];
                            }

                            if( pad && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                            {
                                filePath = pathInfo[4];
                            }

                            BufferedResponseWrapper.writeHtml( contentOutputStream, htmlRootPath
                                + filePath );

                            endStaticURLInfoList.add( new Object[] { pathInfo[1],
                                contentInfo.get( idFlag ), pathInfo[0] } );

                            // 移动
                            if( Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                            {
                                // doMobContentPageStaticAction( request,
                                // response,
                                // currentSiteBean, classBean, contentInfo,
                                // genHtmlFileList, "mob" );
                            }

                            if( Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                            {
                                // doMobContentPageStaticAction( request,
                                // response,
                                // currentSiteBean, classBean, contentInfo,
                                // genHtmlFileList, "pad" );
                            }

                            // 记录文件信息
                            genHtmlFileList
                                .add( new String[] { filePath, htmlRootPath + filePath } );

                            // 增加发布数状态
                            if( OperationDisposeObserverController.statusMap.get( pubEventKey ) == null
                                && !innerWaitCensorPublish && !innerThreadPublish )
                            {
                                // 是否状态已丢失,一般认为Client已经关闭了此操作
                                return FlowConstants.NULL_RESULT;
                            }

                            status.setCurrent( Long.valueOf( status.getCurrent().longValue() + 1 ) );

                            log.info( "[发布] 内容ID为：" + contentInfo.get( idFlag ) + "的[静态化操作]结束" );
                        }

                        System.out.println( "生成html时间：" + ( System.currentTimeMillis() - l1 ) );

                        Map cMap = ( Map ) contentList.get( contentList.size() - 1 );
                        if( contentList.size() == pubLimitCount.intValue() )
                        {
                            prevContentOrderId = ( Double ) cMap.get( "orderIdFlag" );
                        }
                        else
                        {
                            // 不再获取
                            prevContentOrderId = Double.valueOf( 0 );
                        }

                        // 此批数据已经发布完成,取下批数据

                        if( innerWaitCensorPublish )
                        {
                            contentList = contentService
                                .retrieveWaitPublishContentBySiteIdAndCurrentDate( Long
                                    .valueOf( classId ), Long.valueOf( contentModelTypeId ),
                                    prevContentOrderId, pubLimitCount );
                        }
                        else
                        {
                            contentList = contentService
                                .retrieveNeedPublishContentByClassIDAndModelIdAndFlag( Long
                                    .valueOf( classId ), Long.valueOf( contentModelTypeId ),
                                    prevContentOrderId, pubLimitCount, startAddTS, endAddTS );
                        }
                    }

                    // 改动内容页发布成功状态
                    contentPubSucc = true;

                    if( OperationDisposeObserverController.statusMap.get( pubEventKey ) == null
                        && !innerWaitCensorPublish )
                    {
                        // 是否状态已丢失,一般认为Client已经关闭了此操作
                        return FlowConstants.NULL_RESULT;
                    }

                    status.setContentAllCount( Long.valueOf( status.getContentAllCount()
                        .longValue()
                        + pubCount.intValue() ) );

                    log.info( "[发布] 开始更新到持久层" );
                    // 3.记录更新到持久层
                    log.debug( "[发布]  endStaticURLInfoList: " + endStaticURLInfoList );

                    contentService.setContentStaticPageURL( endStaticURLInfoList, currentSiteBean );

                    // setPublishStatus( homePubSucc, channelPubSucc,
                    // listPubSucc,
                    // contentPubSucc, status );

                    log.info( "[发布] 更新到持久层结束" );

                    log.info( "[发布] 栏目ID为：" + classId + "的[内容静态化]操作完成" );

                }

                /**
                 * 栏目静态化
                 */

                if( innerWaitCensorPublish )
                {
                    // 如果为发布待发布内容逻辑,没有待发布内容的栏目不需要进行栏目页发布
                    if( notHaveWaitPublishContent )
                    {
                        continue;
                    }
                }

                // TODO || Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE
                // .equals( classBean.getClassHomeProduceType() )
                // 单页类型栏目发布独立处理,以下逻辑只发布列表类型栏目
                if( ( needClassStaticAction || needSyncPubClass )
                    && ( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean
                        .getClassProduceType() ) || Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE
                        .equals( classBean.getClassHomeProduceType() ) ) )
                {
                    String siteFilePath = ServletUtil.getSiteFilePath( request.getServletContext() );

                    String[] pathInfo = null;

                    String htmlRootPath = siteFilePath + currentSiteBean.getSiteRoot()
                        + File.separator + currentSiteBean.getPublishRoot() + File.separator;

                    if( needSecListStaticAction )
                    {
                        // 获取专题下所有栏目列表数
                        List commTypeList = channelService.retrieveContentCommendTypeBean(
                            currentSiteBean.getSiteFlag(), classBean.getClassId(), false, true,
                            false );

                        ContentCommendTypeBean commTypeBean = null;

                        for ( int icl = 0; icl < commTypeList.size(); icl++ )
                        {
                            commTypeBean = ( ContentCommendTypeBean ) commTypeList.get( icl );

                            if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( commTypeBean
                                .getListProduceType() ) )
                            {
                                continue;
                            }

                            HttpServletResponse responseWrapper = new BufferedResponseWrapper(
                                response );

                            responseWrapper.setCharacterEncoding( currentSiteBean
                                .getTemplateCharset() );

                            // 此出的静态化独立为模版的编码，是否和系统的编码一致，后期要解决
                            request.setCharacterEncoding( currentSiteBean.getTemplateCharset() );
                            response.setCharacterEncoding( currentSiteBean.getTemplateCharset() );

                            // 获取栏目发布规则
                            PublishRuleBean ruleBean = publishService
                                .retrieveSinglePublishRuleBean( commTypeBean.getListPublishRuleId() );

                            if( ruleBean == null )
                            {
                                if( innerWaitCensorPublish || innerThreadPublish )
                                {
                                    // 内部访问不需要提示,但也不可返回,而是尝试处理下一笔数据
                                    continue;
                                }

                                return "本次发布失败,请检查专题子分类 <font color='red'>"
                                    + commTypeBean.getCommendName() + "</font> 的列表页发布规则配置是否正确";
                            }

                            // 访问目标页获取分页对象

                            // 处理栏目参数
                            String secListTemplateUrl = commTypeBean.getListTemplateUrl();

                            if( mob && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                            {
                                secListTemplateUrl = commTypeBean.getMobListTemplateUrl();

                                request.setAttribute( "_pub_mob_", mob );
                            }

                            if( pad && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                            {
                                secListTemplateUrl = commTypeBean.getPadListTemplateUrl();

                                request.setAttribute( "_pub_pad_", pad );
                            }

                            secListTemplateUrl = StringUtil.replaceString( secListTemplateUrl,
                                "{type-id}", commTypeBean.getCommendTypeId().toString(), false,
                                false );

                            try
                            {
                                // freemarker
                                if( secListTemplateUrl.indexOf( ".thtml?" ) != -1
                                    || secListTemplateUrl.indexOf( ".thtml" ) != -1 )
                                {
                                    request.setAttribute( "param", paramMap( secListTemplateUrl ) );

                                    request.setAttribute( "request", request );
                                }

                                request.getRequestDispatcher(
                                    Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
                                        + secListTemplateUrl ).include( request, responseWrapper );
                            }
                            catch ( Exception e )
                            {
                                if( innerWaitCensorPublish || innerThreadPublish )
                                {
                                    // 内部访问不需要提示,但也不可返回,而是尝试处理下一笔数据
                                    continue;
                                }

                                return "本次发布失败,请检查专题子分类 <font color='red'>"
                                    + classBean.getClassName() + "</font> 的列表页模板语法是否符合规则!";
                            }

                            responseWrapper.flushBuffer();

                            // 第一页静态地址
                            pathInfo = ruleBean.getFullCommendInfoPublishPath( currentSiteBean,
                                commTypeBean, null, siteFilePath, Integer.valueOf( 1 ) );

                            String filePath = pathInfo[0];

                            if( mob && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                            {
                                filePath = pathInfo[2];
                            }

                            if( pad && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                            {
                                filePath = pathInfo[4];
                            }

                            BufferedResponseWrapper.writeHtml(
                                ( WrappedOutputStream ) responseWrapper.getOutputStream(),
                                htmlRootPath + filePath );

                            // 记录发布的静态文件
                            genHtmlFileList
                                .add( new String[] { filePath, htmlRootPath + filePath } );

                            // 注意: 已经进行了一次页面静态
                            if( OperationDisposeObserverController.statusMap.get( pubEventKey ) == null
                                && !innerWaitCensorPublish && !innerThreadPublish )
                            {
                                // 是否状态已丢失,一般认为Client已经关闭了此操作
                                return FlowConstants.NULL_RESULT;
                            }

                            status.setCurrent( Long.valueOf( status.getCurrent().longValue() + 1 ) );

                            // 若需要分页且在分页范围内,则不会为空
                            String nextQueryUrl = ( String ) request
                                .getAttribute( "nextQueryActionUrl" );

                            // 已经进行了一次静态发布
                            int disposeCount = 1;

                            while ( nextQueryUrl != null )// 为空则表示结束
                            {
                                disposeCount++;

                                request.removeAttribute( "nextQueryActionUrl" );

                                responseWrapper = new BufferedResponseWrapper( response );

                                responseWrapper.setCharacterEncoding( currentSiteBean
                                    .getTemplateCharset() );

                                // 此出的静态化独立为模版的编码，是否和系统的编码一致，后期要解决
                                request.setCharacterEncoding( currentSiteBean.getTemplateCharset() );
                                response
                                    .setCharacterEncoding( currentSiteBean.getTemplateCharset() );

                                try
                                {
                                    // freemarker
                                    if( nextQueryUrl.indexOf( ".thtml?" ) != -1
                                        || nextQueryUrl.indexOf( ".thtml" ) != -1 )
                                    {
                                        request.setAttribute( "param", paramMap( nextQueryUrl ) );

                                        request.setAttribute( "request", request );
                                    }

                                    request.getRequestDispatcher( nextQueryUrl ).include( request,
                                        responseWrapper );
                                }
                                catch ( Exception e )
                                {
                                    if( innerWaitCensorPublish || innerThreadPublish )
                                    {
                                        // 内部访问不需要提示,但也不可返回,而是尝试处理下一笔数据
                                        continue;
                                    }

                                    return "本次发布失败,请检查专题子分类 <font color='red'>"
                                        + classBean.getClassName() + "</font> 的列表页模板语法是否符合规则!";
                                }

                                responseWrapper.flushBuffer();

                                pathInfo = ruleBean.getFullCommendInfoPublishPath( currentSiteBean,
                                    commTypeBean, null, siteFilePath, Integer
                                        .valueOf( disposeCount ) );

                                filePath = pathInfo[0];

                                if( mob && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                                {
                                    filePath = pathInfo[2];
                                }

                                if( pad && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                                {
                                    filePath = pathInfo[4];
                                }

                                BufferedResponseWrapper.writeHtml(
                                    ( WrappedOutputStream ) responseWrapper.getOutputStream(),
                                    htmlRootPath + filePath );

                                genHtmlFileList.add( new String[] { filePath,
                                    htmlRootPath + filePath } );

                                status.setCurrent( Long
                                    .valueOf( status.getCurrent().longValue() + 1 ) );

                                // 获取新的访问标识
                                nextQueryUrl = ( String ) request
                                    .getAttribute( "nextQueryActionUrl" );

                            }

                            request.removeAttribute( "___system_dispose_page_object_for_pub___" );

                            // 设定列表发布状态
                            listPubSucc = true;

                        }

                        // 设定发布完成的
                        status.setListAllCount( Long.valueOf( allNeedStaticPageCount ) );
                    }
                    else
                    {
                        HttpServletResponse responseWrapper = new BufferedResponseWrapper( response );

                        responseWrapper.setCharacterEncoding( currentSiteBean.getTemplateCharset() );

                        // 此出的静态化独立为模版的编码，是否和系统的编码一致，后期要解决
                        request.setCharacterEncoding( currentSiteBean.getTemplateCharset() );

                        response.setCharacterEncoding( currentSiteBean.getTemplateCharset() );

                        if( Constant.SITE_CHANNEL.CLASS_TYPE_SINGLE_PAGE.equals( classBean
                            .getClassType() ) )
                        {
                            /**
                             * 单页类型发布
                             */
                            Map contentInfo = contentService.retrieveContentPublishInfo( classBean
                                .getSingleContentId() );

                            if( contentInfo.isEmpty() )
                            {
                                if( innerWaitCensorPublish || innerThreadPublish )
                                {
                                    // 内部访问不需要提示,但也不可返回,而是尝试处理下一笔数据
                                    continue;
                                }

                                return "本次发布失败,单页栏目 <font color='red'>" + classBean.getClassName()
                                    + "</font> 的内容没有编辑";
                            }

                            Integer censor = ( Integer ) contentInfo.get( "censorState" );

                            // 审核状态是否正常
                            if( censor == null
                                || !Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( censor ) )
                            {
                                return FlowConstants.NULL_RESULT;
                            }

                            Map infoMap = contentService
                                .retrieveSingleContentMainInfoMap( classBean.getSingleContentId() );

                            String endStaticClassFilePath = doContentStaticAction( request,
                                response, currentSiteBean, classBean, infoMap, genHtmlFileList,
                                mob, pad );

                            if( Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                            {
                                // doMobContentStaticAction( request,
                                // response, currentSiteBean, classBean,
                                // infoMap, genHtmlFileList, "mob" );
                            }

                            if( Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                            {
                                // doMobContentStaticAction( request,
                                // response, currentSiteBean, classBean,
                                // infoMap, genHtmlFileList, "pad" );
                            }

                            if( StringUtil.isStringNull( endStaticClassFilePath )
                                || "error:template".equals( endStaticClassFilePath ) )
                            {
                                if( singleContentPublish.booleanValue() )
                                {
                                    // 编辑或增模式下,不提示,直接返回
                                    if( editMode.booleanValue() )
                                    {
                                        return ServletUtil.redirect(
                                            "/core/content/EditUserDefineModelContent.jsp", params );
                                    }

                                    return ServletUtil.redirect(
                                        "/core/content/EditUserDefineModelContent.jsp", params );
                                }
                                else
                                {
                                    // 内部访问不需要提示,但也不可返回,而是尝试处理下一笔数据
                                    if( fromAction )
                                    {
                                        // 编辑或增模式下,不提示,直接返回
                                        if( editMode.booleanValue() )
                                        {
                                            return ServletUtil.redirect(
                                                "/core/content/EditUserDefineModelContent.jsp",
                                                params );
                                        }

                                        return ServletUtil.redirect(
                                            "/core/content/EditUserDefineModelContent.jsp", params );
                                    }

                                    if( innerWaitCensorPublish || innerThreadPublish )
                                    {

                                        continue;
                                    }

                                    return "本次发布失败,请检查栏目 <font color='red'>"
                                        + classBean.getClassName() + "</font> 的内容页发布规则配置以及模板语法是否正确";
                                }
                            }

                            // 更新持久数据,单页栏目实际静态所属内容

                            contentService.setContentStaticPageURL( endStaticClassFilePath,
                                classBean.getSingleContentId() );

                            // 设定栏目单发布状态

                            status.setCurrent( Long.valueOf( status.getCurrent().longValue() + 1 ) );

                            listPubSucc = true;

                            status.setListAllCount( Long.valueOf( status.getListAllCount()
                                .longValue() + 1 ) );
                        }
                        else
                        {
                            /**
                             * 列表类型,包含频道发布
                             */

                            if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean
                                .getClassProduceType() )
                                && Constant.COMMON.OFF.equals( classBean.getIsSpecial() ) )// 专题类型不需要栏目类型列表
                            {
                                // 1.发布列表
                                // 获取栏目列表发布规则
                                PublishRuleBean ruleBean = publishService
                                    .retrieveSinglePublishRuleBean( classBean
                                        .getClassPublishRuleId() );

                                if( ruleBean == null )
                                {
                                    if( singleContentPublish.booleanValue() )
                                    {
                                        // 编辑或增模式下,不提示,直接返回
                                        if( editMode.booleanValue() )
                                        {
                                            return ServletUtil.redirect(
                                                "/core/content/EditUserDefineModelContent.jsp",
                                                params );
                                        }

                                        return ServletUtil.redirect(
                                            "/core/content/EditUserDefineModelContent.jsp", params );
                                    }
                                    else
                                    {
                                        if( innerWaitCensorPublish || innerThreadPublish )
                                        {
                                            // 内部访问不需要提示,但也不可返回,而是尝试处理下一笔数据
                                            continue;
                                        }

                                        return "本次发布失败,请检查栏目 <font color='red'>"
                                            + classBean.getClassName() + "</font> 的列表页发布规则配置是否正确";
                                    }
                                }

                                if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean
                                    .getClassProduceType() ) )
                                {
                                    Integer disposePageCurrentCount = Integer.valueOf( 1 );

                                    request.setAttribute( "genPageSize", Integer
                                        .valueOf( StringUtil.getIntValue( classBean
                                            .getListPageLimit(), 0 ) ) );

                                    // request.setAttribute( "ruleBean",
                                    // ruleBean );

                                    // 不使用html目录,按照rule划分目录

                                    // if( ruleBean.getNeedPage().intValue() ==
                                    // 1 )
                                    if( StringUtil.getIntValue( classBean.getListPageLimit(), 0 ) > 0 )
                                    {
                                        request.setAttribute( "needPage", Boolean.TRUE );

                                        // prevPage传递给下一次访问,表示当前静态化后的当前页是它的上一页
                                        String[] sps = ruleBean.getFullContentClassPagePublishPath(
                                            currentSiteBean, classBean, null, siteFilePath,
                                            disposePageCurrentCount );

                                        String sp = sps[1];

                                        if( mob
                                            && Constant.COMMON.ON.equals( currentSiteBean
                                                .getMobVm() ) )
                                        {
                                            sp = sps[3];
                                        }

                                        if( pad
                                            && Constant.COMMON.ON.equals( currentSiteBean
                                                .getPadVm() ) )
                                        {
                                            sp = sps[5];
                                        }

                                        request.setAttribute( "prevStaticPage", sp );

                                        sps = ruleBean.getFullContentClassPagePublishPath(
                                            currentSiteBean, classBean, null, siteFilePath, Integer
                                                .valueOf( disposePageCurrentCount.intValue() + 1 ) );

                                        sp = sps[1];

                                        if( mob
                                            && Constant.COMMON.ON.equals( currentSiteBean
                                                .getMobVm() ) )
                                        {
                                            sp = sps[3];
                                        }

                                        if( pad
                                            && Constant.COMMON.ON.equals( currentSiteBean
                                                .getPadVm() ) )
                                        {
                                            sp = sps[5];
                                        }

                                        request.setAttribute( "nextStaticPage", sp );

                                        // 只分一页的情况,所以页码不会增加
                                        sps = ruleBean.getFullContentClassPagePublishPath(
                                            currentSiteBean, classBean, null, siteFilePath, Integer
                                                .valueOf( disposePageCurrentCount.intValue() ) );

                                        sp = sps[1];

                                        if( mob
                                            && Constant.COMMON.ON.equals( currentSiteBean
                                                .getMobVm() ) )
                                        {
                                            sp = sps[3];
                                        }

                                        if( pad
                                            && Constant.COMMON.ON.equals( currentSiteBean
                                                .getPadVm() ) )
                                        {
                                            sp = sps[5];
                                        }

                                        request.setAttribute( "nextStaticPageOnlyOne", sp );

                                        pathInfo = ruleBean.getFullContentClassPagePublishPath(
                                            currentSiteBean, classBean, null, siteFilePath,
                                            disposePageCurrentCount );

                                        // disposePageCurrentCount = Integer
                                        // .valueOf(
                                        // disposePageCurrentCount.intValue()
                                        // + 1
                                        // );
                                    }
                                    else
                                    {
                                        // 不需分页
                                        pathInfo = ruleBean.getFullContentClassPublishPath(
                                            currentSiteBean, classBean, null, siteFilePath );
                                    }

                                    // 栏目非分页第一次访问
                                    // 1.存储静态动作
                                    // channelService
                                    // .setChannelClassStaticPageURL(
                                    // pathInfo[1], Long.valueOf( classId ) );

                                    // 处理栏目参数
                                    String classTemplateUrl = classBean.getClassTemplateUrl();

                                    if( mob
                                        && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                                    {
                                        classTemplateUrl = classBean.getMobClassTemplateUrl();

                                        request.setAttribute( "_pub_mob_", mob );
                                    }

                                    if( pad
                                        && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                                    {
                                        classTemplateUrl = classBean.getPadClassTemplateUrl();

                                        request.setAttribute( "_pub_pad_", pad );
                                    }

                                    classTemplateUrl = StringUtil.replaceString( classTemplateUrl,
                                        "{class-id}", classBean.getClassId().toString(), false,
                                        false );

                                    try
                                    {
                                        // freemarker
                                        if( classTemplateUrl.indexOf( ".thtml?" ) != -1
                                            || classTemplateUrl.indexOf( ".thtml" ) != -1 )
                                        {
                                            request.setAttribute( "param",
                                                paramMap( classTemplateUrl ) );

                                            request.setAttribute( "request", request );
                                        }

                                        request.getRequestDispatcher(
                                            Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
                                                + classTemplateUrl ).include( request,
                                            responseWrapper );
                                    }
                                    catch ( Exception e )
                                    {
                                        if( singleContentPublish.booleanValue() )
                                        {
                                            // 编辑或增模式下,不提示,直接返回
                                            if( editMode.booleanValue() )
                                            {
                                                return ServletUtil.redirect(
                                                    "/core/content/EditUserDefineModelContent.jsp",
                                                    params );
                                            }

                                            return ServletUtil.redirect(
                                                "/core/content/EditUserDefineModelContent.jsp",
                                                params );
                                        }
                                        else
                                        {
                                            if( innerWaitCensorPublish || innerThreadPublish )
                                            {
                                                // 内部访问不需要提示,但也不可返回,而是尝试处理下一笔数据
                                                continue;
                                            }

                                            return "本次发布失败,请检查栏目 <font color='red'>"
                                                + classBean.getClassName()
                                                + "</font> 的列表页模板语法是否符合规则!";
                                        }
                                    }

                                    // remove不需要的数据
                                    request.removeAttribute( "headPageFlag" );
                                    request.removeAttribute( "nextStaticPageOnlyOne" );

                                    responseWrapper.flushBuffer();

                                    String filePath = pathInfo[0];

                                    if( mob
                                        && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                                    {
                                        filePath = pathInfo[2];
                                    }

                                    if( pad
                                        && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                                    {
                                        filePath = pathInfo[4];
                                    }

                                    BufferedResponseWrapper.writeHtml(
                                        ( WrappedOutputStream ) responseWrapper.getOutputStream(),
                                        htmlRootPath + filePath );

                                    // 记录发布的静态文件
                                    genHtmlFileList.add( new String[] { filePath,
                                        htmlRootPath + filePath } );

                                    // 本次分页信息
                                    Page pageInfo = ( Page ) request
                                        .getAttribute( "___system_dispose_page_object_for_pub___" );

                                    int allClassPageCount = 1;

                                    int listPageLimit = StringUtil.getIntValue( classBean
                                        .getListPageLimit(), 0 );

                                    if( listPageLimit == 0 || pageInfo == null
                                        || pageInfo.getPageCount() < listPageLimit )
                                    {

                                        if( pageInfo == null )
                                        {
                                            allClassPageCount = 1;
                                        }
                                        else
                                        {
                                            allClassPageCount = pageInfo.getPageCount() == 0 ? 1
                                                : pageInfo.getPageCount();
                                        }

                                    }
                                    else
                                    {
                                        // 最大分页数存在且有效
                                        allClassPageCount = listPageLimit;
                                    }

                                    // 移动
                                    if( Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                                    {
                                        // doMobContentClassStaticAction(
                                        // request,
                                        // response, classBean, currentSiteBean,
                                        // genHtmlFileList, "list" ,"channel");
                                    }

                                    if( Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                                    {
                                        // doMobContentClassStaticAction(
                                        // request,
                                        // response, classBean, currentSiteBean,
                                        // genHtmlFileList, "pad", "list" );
                                    }

                                    /**
                                     * 记录进度
                                     */
                                    // 注意: 已经进行了一次页面静态
                                    if( OperationDisposeObserverController.statusMap
                                        .get( pubEventKey ) == null
                                        && !innerWaitCensorPublish && !innerThreadPublish )
                                    {
                                        // 是否状态已丢失,一般认为Client已经关闭了此操作
                                        return FlowConstants.NULL_RESULT;
                                    }

                                    status.setCurrent( Long.valueOf( status.getCurrent()
                                        .longValue() + 1 ) );

                                    // 若需要分页且在分页范围内,则不会为空
                                    String nextQueryUrl = ( String ) request
                                        .getAttribute( "nextQueryActionUrl" );

                                    while ( nextQueryUrl != null )// 为空则表示结束
                                    {
                                        // 增加pn
                                        disposePageCurrentCount = Integer
                                            .valueOf( disposePageCurrentCount.intValue() + 1 );

                                        request.removeAttribute( "nextQueryActionUrl" );

                                        String[] sps = ruleBean.getFullContentClassPagePublishPath(
                                            currentSiteBean, classBean, null, siteFilePath, Integer
                                                .valueOf( disposePageCurrentCount.intValue() - 1 ) );

                                        String sp = sps[1];

                                        if( mob
                                            && Constant.COMMON.ON.equals( currentSiteBean
                                                .getMobVm() ) )
                                        {
                                            sp = sps[3];
                                        }

                                        if( pad
                                            && Constant.COMMON.ON.equals( currentSiteBean
                                                .getPadVm() ) )
                                        {
                                            sp = sps[5];
                                        }

                                        request.setAttribute( "prevStaticPage", sp );

                                        // 增加发布数状态
                                        if( OperationDisposeObserverController.statusMap
                                            .get( pubEventKey ) == null
                                            && !innerWaitCensorPublish && !innerThreadPublish )
                                        {
                                            // 是否状态已丢失,一般认为Client已经关闭了此操作
                                            return FlowConstants.NULL_RESULT;
                                        }

                                        status.setCurrent( Long.valueOf( status.getCurrent()
                                            .longValue() + 1 ) );

                                        if( disposePageCurrentCount.intValue() == pageInfo
                                            .getPageCount() )
                                        {
                                            sps = ruleBean.getFullContentClassPagePublishPath(
                                                currentSiteBean, classBean, null, siteFilePath,
                                                Integer
                                                    .valueOf( disposePageCurrentCount.intValue() ) );

                                            sp = sps[1];

                                            if( mob
                                                && Constant.COMMON.ON.equals( currentSiteBean
                                                    .getMobVm() ) )
                                            {
                                                sp = sps[3];
                                            }

                                            if( pad
                                                && Constant.COMMON.ON.equals( currentSiteBean
                                                    .getPadVm() ) )
                                            {
                                                sp = sps[5];
                                            }

                                            request.setAttribute( "nextStaticPage", sp );
                                        }
                                        else
                                        {
                                            sps = ruleBean
                                                .getFullContentClassPagePublishPath(
                                                    currentSiteBean, classBean, null, siteFilePath,
                                                    Integer.valueOf( disposePageCurrentCount
                                                        .intValue() + 1 ) );

                                            sp = sps[1];

                                            if( mob
                                                && Constant.COMMON.ON.equals( currentSiteBean
                                                    .getMobVm() ) )
                                            {
                                                sp = sps[3];
                                            }

                                            if( pad
                                                && Constant.COMMON.ON.equals( currentSiteBean
                                                    .getPadVm() ) )
                                            {
                                                sp = sps[5];
                                            }

                                            request.setAttribute( "nextStaticPage", sp );
                                        }

                                        // 进行下一次分页动作,将访问模板并确定下次访问
                                        String error = doContentClassPageStaticAction( request,
                                            responseWrapper, classBean, currentSiteBean,
                                            nextQueryUrl, disposePageCurrentCount, genHtmlFileList,
                                            mob, pad );

                                        if( "error:content".equals( error ) )
                                        {
                                            // 内部访问不需要提示,但也不可返回,而是尝试处理下一笔数据

                                            if( innerWaitCensorPublish || innerThreadPublish
                                                || fromAction )
                                            {
                                                continue;
                                            }

                                            return "本次发布失败,请检查栏目 <font color='red'>"
                                                + classBean.getClassName() + "</font> 的模板是否符合规则!";
                                        }
                                        else if( "error:rule".equals( error ) )
                                        {
                                            if( innerWaitCensorPublish || innerThreadPublish
                                                || fromAction )
                                            {
                                                // 内部访问不需要提示,但也不可返回,而是尝试处理下一笔数据
                                                continue;
                                            }

                                            return "本次发布失败,请检查栏目 <font color='red'>"
                                                + classBean.getClassName()
                                                + "</font> 的内容页发布规则是否存在!";
                                        }

                                        // 获取新的访问标识
                                        nextQueryUrl = ( String ) request
                                            .getAttribute( "nextQueryActionUrl" );

                                    }

                                    // 到此结束分页静态化动作,将最后一页作为第一个动态页上一页地址
                                    // 辅助信息,不属于主缓存
                                    String assistantUrl = classBean.getClassTemplateUrl();

                                    if( mob
                                        && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                                    {
                                        assistantUrl = classBean.getMobClassTemplateUrl();

                                        request.setAttribute( "_pub_mob_", mob );
                                    }

                                    if( pad
                                        && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                                    {
                                        assistantUrl = classBean.getPadClassTemplateUrl();

                                        request.setAttribute( "_pub_pad_", pad );
                                    }

                                    String[] sps = ruleBean.getFullContentClassPagePublishPath(
                                        currentSiteBean, classBean, null, siteFilePath, Integer
                                            .valueOf( disposePageCurrentCount.intValue() ) );

                                    String sp = sps[1];

                                    if( mob
                                        && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                                    {
                                        sp = sps[3];
                                    }

                                    if( pad
                                        && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                                    {
                                        sp = sps[5];
                                    }

                                    channelService.setClassPublishPageAssistant( classBean
                                        .getClassId(), assistantUrl, disposePageCurrentCount, sp,
                                        ruleBean.getRuleId() );

                                    // channelService
                                    // .setChannelClassEndStaticPageInfo(
                                    // classBean
                                    // .getClassId(), disposePageCurrentCount,
                                    // ruleBean
                                    // .getFullContentClassPagePublishPath(
                                    // classBean,
                                    // null, siteFilePath, Integer
                                    // .valueOf( disposePageCurrentCount
                                    // .intValue() ) )[0] );

                                    // 设定栏目列表发布状态
                                    listPubSucc = true;

                                    status.setListAllCount( Long.valueOf( status.getListAllCount()
                                        .longValue()
                                        + allClassPageCount ) );

                                    // 清除每次栏目发布的分页对象
                                    request
                                        .removeAttribute( "___system_dispose_page_object_for_pub___" );

                                }
                            }

                            // 2.发布频道
                            if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean
                                .getClassHomeProduceType() ) )
                            {
                                // 获取栏目发布规则
                                PublishRuleBean ruleBean = publishService
                                    .retrieveSinglePublishRuleBean( classBean
                                        .getClassHomePublishRuleId() );

                                if( ruleBean == null )
                                {
                                    if( singleContentPublish.booleanValue() )
                                    {
                                        // 编辑或增模式下,不提示,直接返回
                                        if( editMode.booleanValue() )
                                        {
                                            return ServletUtil.redirect(
                                                "/core/content/EditUserDefineModelContent.jsp",
                                                params );
                                        }

                                        return ServletUtil.redirect(
                                            "/core/content/EditUserDefineModelContent.jsp", params );
                                    }
                                    else
                                    {
                                        if( innerWaitCensorPublish || innerThreadPublish )
                                        {
                                            // 内部访问不需要提示,但也不可返回,而是尝试处理下一笔数据
                                            continue;
                                        }

                                        return "本次发布失败,请检查栏目 <font color='red'>"
                                            + classBean.getClassName() + "</font> 的频道页发布规则配置是否正确";
                                    }
                                }

                                pathInfo = null;

                                htmlRootPath = siteFilePath + currentSiteBean.getSiteRoot()
                                    + File.separator + currentSiteBean.getPublishRoot()
                                    + File.separator;
                                // 不使用html目录,按照rule划分目录

                                // 不需分页
                                pathInfo = ruleBean.getFullContentClassPublishPath(
                                    currentSiteBean, classBean, null, siteFilePath );

                                // 栏目非分页第一次访问
                                // 1.存储静态动作
                                channelService.setChannelClassHomeStaticPageURL( pathInfo[1], Long
                                    .valueOf( classId ) );

                                // 处理栏目参数
                                String classHomeTemplateUrl = classBean.getClassHomeTemplateUrl();

                                if( mob && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                                {
                                    classHomeTemplateUrl = classBean.getMobClassHomeTemplateUrl();

                                    request.setAttribute( "_pub_mob_", mob );
                                }

                                if( pad && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                                {
                                    classHomeTemplateUrl = classBean.getPadClassHomeTemplateUrl();

                                    request.setAttribute( "_pub_pad_", pad );
                                }

                                classHomeTemplateUrl = StringUtil.replaceString(
                                    classHomeTemplateUrl, "{class-id}", classBean.getClassId()
                                        .toString(), false, false );

                                try
                                {
                                    responseWrapper = new BufferedResponseWrapper( response );

                                    responseWrapper.setCharacterEncoding( currentSiteBean
                                        .getTemplateCharset() );

                                    // 此出的静态化独立为模版的编码，是否和系统的编码一致，后期要解决
                                    request.setCharacterEncoding( currentSiteBean
                                        .getTemplateCharset() );
                                    response.setCharacterEncoding( currentSiteBean
                                        .getTemplateCharset() );

                                    // freemarker
                                    if( classHomeTemplateUrl.indexOf( ".thtml?" ) != -1
                                        || classHomeTemplateUrl.indexOf( ".thtml" ) != -1 )
                                    {
                                        request.setAttribute( "param",
                                            paramMap( classHomeTemplateUrl ) );

                                        request.setAttribute( "request", request );
                                    }

                                    request.getRequestDispatcher(
                                        Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
                                            + classHomeTemplateUrl ).include( request,
                                        responseWrapper );
                                }
                                catch ( Exception e )
                                {
                                    // e.printStackTrace();

                                    if( singleContentPublish.booleanValue() )
                                    {
                                        // 编辑或增模式下,不提示,直接返回
                                        if( editMode.booleanValue() )
                                        {
                                            return ServletUtil.redirect(
                                                "/core/content/EditUserDefineModelContent.jsp",
                                                params );
                                        }

                                        return ServletUtil.redirect(
                                            "/core/content/EditUserDefineModelContent.jsp", params );
                                    }
                                    else
                                    {
                                        if( innerWaitCensorPublish || innerThreadPublish )
                                        {
                                            // 内部访问不需要提示,但也不可返回,而是尝试处理下一笔数据
                                            continue;
                                        }

                                        return "本次发布失败,请检查栏目 <font color='red'>"
                                            + classBean.getClassName() + "</font> 的频道页模板是否正常访问!";
                                    }
                                }

                                responseWrapper.flushBuffer();

                                String filePath = pathInfo[0];

                                if( mob && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
                                {
                                    filePath = pathInfo[2];
                                }

                                if( pad && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
                                {
                                    filePath = pathInfo[4];
                                }

                                BufferedResponseWrapper.writeHtml(
                                    ( WrappedOutputStream ) responseWrapper.getOutputStream(),
                                    htmlRootPath + filePath );

                                // 记录发布的静态文件
                                genHtmlFileList.add( new String[] { filePath,
                                    htmlRootPath + filePath } );

                                /**
                                 * 记录进度
                                 */
                                // 注意: 已经进行了一次页面静态
                                if( OperationDisposeObserverController.statusMap.get( pubEventKey ) == null
                                    && !innerWaitCensorPublish && !innerThreadPublish )
                                {
                                    // 是否状态已丢失,一般认为Client已经关闭了此操作
                                    return FlowConstants.NULL_RESULT;
                                }

                                channelPubSucc = true;

                                status.setCurrent( Long
                                    .valueOf( status.getCurrent().longValue() + 1 ) );

                                status.setChannelAllCount( Long.valueOf( status
                                    .getChannelAllCount().longValue() + 1 ) );

                            }
                        }
                    }

                    log.info( "[发布] 栏目ID为：" + classId + "的[栏目静态化]操作完成" );
                }

            }

            if( OperationDisposeObserverController.statusMap.get( pubEventKey ) == null
                && !innerWaitCensorPublish && !innerThreadPublish )
            {
                // 是否状态已丢失,一般认为Client已经关闭了此操作
                return FlowConstants.NULL_RESULT;
            }

            // setPublishStatus( homePubSucc, channelPubSucc, listPubSucc,
            // contentPubSucc, status );

            // 注意:关于此处的GC动作,经过测试,大数据量下,高频率,是需要的
            System.gc();
        }

        // 首页静态发布

        int pubC = 0;

        if( generatorBean.getStaticType() == 4 )
        {
            String error = null;

            if( mob && Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ) )
            {
                error = doMobHomePageStaticAction( request, response, generatorBean,
                    currentSiteBean, genHtmlFileList, "mob" );
            }
            else if( pad && Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ) )
            {
                error = doMobHomePageStaticAction( request, response, generatorBean,
                    currentSiteBean, genHtmlFileList, "pad" );
            }
            else
            {
                error = doHomePageStaticAction( request, response, generatorBean, currentSiteBean,
                    genHtmlFileList );
            }

            if( "error:home".equals( error ) )
            {

                if( singleContentPublish.booleanValue() || fromAction )
                {
                    // 编辑或增模式下,不提示,直接返回
                    if( editMode.booleanValue() )
                    {
                        return ServletUtil.redirect(
                            "/core/content/EditUserDefineModelContent.jsp", params );
                    }

                    return ServletUtil.redirect( "/core/content/EditUserDefineModelContent.jsp",
                        params );
                }
                else
                {
                    return "本次发布失败,请检查首页模板是否正常访问!";
                }
            }

            pubC++;

            // 移动
            // if(Constant.COMMON.ON.equals( currentSiteBean.getMobVm() ))
            // {
            // //error = doMobHomePageStaticAction( request, response,
            // //generatorBean, currentSiteBean, genHtmlFileList, "mob" );
            //
            // if( "error:home".equals( error ) )
            // {
            //
            // if( singleContentPublish.booleanValue() || fromAction )
            // {
            // // 编辑或增模式下,不提示,直接返回
            // if( editMode.booleanValue() )
            // {
            // return "toContentEditPage";
            // }
            //
            // return "toContentEditPage";
            // }
            // else
            // {
            // return this
            // .responseAjaxTextMessage( "本次发布失败,请检查首页模板是否正常访问!" );
            // }
            // }
            // }
            //            
            // pubC++;
            //            
            // if(Constant.COMMON.ON.equals( currentSiteBean.getPadVm() ))
            // {
            // //error = doMobHomePageStaticAction( request, response,
            // //generatorBean, currentSiteBean, genHtmlFileList, "pad" );
            //
            // if( "error:home".equals( error ) )
            // {
            //
            // if( singleContentPublish.booleanValue() || fromAction )
            // {
            // // 编辑或增模式下,不提示,直接返回
            // if( editMode.booleanValue() )
            // {
            // return "toContentEditPage";
            // }
            //
            // return "toContentEditPage";
            // }
            // else
            // {
            // return this
            // .responseAjaxTextMessage( "本次发布失败,请检查首页模板是否正常访问!" );
            // }
            // }
            // }
            //            
            // pubC++;

            // 更新相关site的cache
            InitSiteGroupInfoBehavior.bulidSiteGroupInfo();

            homePubSucc = true;

            // OperationDisposeObserverController.destroyOperation( pubEventKey
            // );

            status.setPubCount( Long.valueOf( pubC ) );
            status.setCurrent( Long.valueOf( pubC ) );
            // setPublishStatus( homePubSucc, channelPubSucc, listPubSucc,
            // contentPubSucc, status );

        }

        // 发布所有页面中包含的区块页
        String errorBlockName = doBlockPageStaticAction( request, response, needPubBlockList,
            currentSiteBean, genHtmlFileList );

        if( errorBlockName != null )
        {
            if( singleContentPublish.booleanValue() )
            {
                // 编辑或增模式下,不提示,直接返回
                if( editMode.booleanValue() )
                {
                    return ServletUtil.redirect( "/core/content/EditUserDefineModelContent.jsp",
                        params );
                }

                return ServletUtil
                    .redirect( "/core/content/EditUserDefineModelContent.jsp", params );
            }
            else
            {
                return "发布失败,请检查区块 <font color='red'>" + errorBlockName + "</font> 的模板是否符合规则!";
            }
        }

        /**
         * 分发到资源服务器
         */
        status.setTranContentCount( Long.valueOf( genHtmlFileList.size() ) );

        siteService.transferGenHtmlDataToServer( genHtmlFileList, currentSiteBean, status );

        setPublishStatus( homePubSucc, channelPubSucc, listPubSucc, contentPubSucc, status );

        System.out.println( "总静态化html时间：" + ( System.currentTimeMillis() - lx ) );

        // 记录栏目发布时间
        // ContentClassBean classBean = null;
        //
        // for ( int i = 0; i < classBeanList.size(); i++ )
        // {
        // // channelService.updateContentClassPubDate( classBean.getClassId(),
        // // );
        // }

        // 由job执行的静态化无需返回页面
        if( generatorBean.getJob().booleanValue() )
        {
            return FlowConstants.NULL_RESULT;
        }

        // 区块发布成功
        if( generatorBean.getStaticType() == 5 )
        {
            return "success_block";
        }

        // 返回单页发布结果有页
        if( needSyncPubClass )
        {
            if( editMode.booleanValue() )
            {
                return ServletUtil
                    .redirect( "/core/content/EditUserDefineModelContent.jsp", params );
            }

            if( classBeanList != null && classBeanList.size() == 1 )
            {
                ContentClassBean singleClassBean = ( ContentClassBean ) classBeanList.get( 0 );

                if( Constant.SITE_CHANNEL.CLASS_TYPE_SINGLE_PAGE.equals( singleClassBean
                    .getClassType() ) )
                {
                    return ServletUtil.redirect( "/core/content/EditUserDefineModelContent.jsp",
                        params );
                }

            }

            return ServletUtil.redirect( "/core/content/EditUserDefineModelContent.jsp", params );
        }

        // 更新缓存
        //
        // ListContentClassInfoTreeFlow.resizeSiteContentClassCache();
        // ListCommendTypeInfoTreeFlow.resizeSiteCommendTypeCache();
        // ChannelDao.clearAllCache();
        // ChannelService.clearContentClassCache();
        //
        // ContentDao.releaseAllCountCache();
        // ContentService.releaseContentCache();

        String wf = ( String ) request.getAttribute( "workflow" );

        if( "delete".equals( wf ) )
        {
            params.put( "fromFlow", "true" );
            params.put( "contentId", request.getAttribute( "contentId" ) );
            // 注意，由于栏目的操作为细粒度权限控制，所以，要放入访问栏目标志ID
            params.put( "classId", request.getAttribute( "classId" ) );
            params.put( "modelId", request.getAttribute( "modelId" ) );
            params.put( "workflow", "delete" );

            return ServletUtil.redirect( "/core/content/ContentForWorkflowShow.jsp", params );
        }

        return "";
    }

    private String doBlockPageStaticAction( HttpServletRequest request,
        HttpServletResponse response, List needPubBlockList, SiteGroupBean siteBean,
        List genHtmlFileList ) throws Exception
    {
        BlockInfoBean blockBean = null;

        for ( int i = 0; i < needPubBlockList.size(); i++ )
        {
            blockBean = ( BlockInfoBean ) needPubBlockList.get( i );

            HttpServletResponse responseWrapper = new BufferedResponseWrapper( response );

            // 此出的静态化独立为模版的编码，是否和系统的编码一致，后期要解决
            request.setCharacterEncoding( siteBean.getTemplateCharset() );
            response.setCharacterEncoding( siteBean.getTemplateCharset() );

            if( StringUtil.isStringNull( blockBean.getTemplateUrl() )
                || blockBean.getTemplateUrl().length() < 1 )
            {
                return blockBean.getBlockName();
            }

            try
            {
                // freemarker
                if( blockBean.getTemplateUrl().indexOf( ".thtml?" ) != -1
                    || blockBean.getTemplateUrl().indexOf( ".thtml" ) != -1 )
                {
                    request.setAttribute( "param", paramMap( blockBean.getTemplateUrl() ) );

                    request.setAttribute( "request", request );
                }

                request.getRequestDispatcher(
                    Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH + blockBean.getTemplateUrl() )
                    .include( request, responseWrapper );
            }
            catch ( Exception e )
            {
                return blockBean.getBlockName();
            }

            // 切记,一定要在获得output之前flushBuffer
            responseWrapper.flushBuffer();

            WrappedOutputStream contentOutputStream = ( WrappedOutputStream ) responseWrapper
                .getOutputStream();

            String siteFilePath = ServletUtil.getSiteFilePath( request.getServletContext() );

            String htmlCachePath = siteFilePath + File.separator + siteBean.getSiteRoot()
                + File.separator + siteBean.getPublishRoot() + File.separator
                + Constant.CONTENT.BLOCK_BASE + File.separator;

            File file = new File( htmlCachePath );
            if( !file.exists() )
            {
                file.mkdirs();
            }

            String endStaticClassFilePath = htmlCachePath + blockBean.getFlag()
                + Constant.CONTENT.HTML_PUB_HTML;

            BufferedResponseWrapper.writeHtml( contentOutputStream, endStaticClassFilePath );

            // String tempFile = htmlCachePath + "sys_temp_"
            // + StringUtil.getUUIDString() + Constant.CONTENT.HTML_PUB_HTML;

            genHtmlFileList.add( new String[] {
                File.separator + siteBean.getSiteRoot() + File.separator
                    + siteBean.getPublishRoot() + File.separator + Constant.CONTENT.BLOCK_BASE
                    + File.separator + blockBean.getFlag() + Constant.CONTENT.HTML_PUB_HTML,
                endStaticClassFilePath } );

            blockService.updateBlockPublishDateTrace( blockBean.getBlockId(),

            new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

        }

        return null;
    }

    /**
     * 对单一content进行静态化
     * 
     * @param request
     * @param response
     * @param contentId
     * @param classId
     * @param contentTemplet
     * @return
     * @throws Exception
     */
    private String doContentStaticAction( HttpServletRequest request, HttpServletResponse response,
        SiteGroupBean site, ContentClassBean classBean, Map info, List genHtmlFileList,
        boolean mob, boolean pad ) throws Exception
    {
        Long contentId = ( Long ) info.get( "contentId" );

        site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( classBean.getSiteFlag() );

        DISPATCH_TEMPLATE_PATH = site.getSiteRoot() + Constant.CONTENT.URL_SEP + "template"
            + Constant.CONTENT.URL_SEP;

        String targetEndRequestUrl = Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
            + classBean.getContentTemplateUrl();

        if( mob && Constant.COMMON.ON.equals( site.getMobVm() ) )
        {
            targetEndRequestUrl = Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
                + classBean.getMobContentTemplateUrl();
        }

        if( pad && Constant.COMMON.ON.equals( site.getPadVm() ) )
        {
            targetEndRequestUrl = Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
                + classBean.getPadContentTemplateUrl();
        }

        Map infoState = ( Map ) contentService.retrieveContentPublishInfo( contentId );

        Integer censorState = ( Integer ) infoState.get( "censorState" );
        // 只有通过审核的内容才可发布
        if( censorState != null && Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( censorState ) )
        {
            String esTemplate = ( String ) infoState.get( "especialTemplateUrl" );

            if( StringUtil.isStringNull( esTemplate ) )
            {
                targetEndRequestUrl = StringUtil.replaceString( targetEndRequestUrl,
                    "{content-id}", contentId.toString(), false, false );
            }
            else
            {
                targetEndRequestUrl = StringUtil.replaceString( esTemplate, "{content-id}",
                    contentId.toString(), false, false );
            }

            targetEndRequestUrl = StringUtil.replaceString( targetEndRequestUrl, "{class-id}",
                classBean.getClassId().toString(), false, false );

            HttpServletResponse responseWrapper = new BufferedResponseWrapper( response );

            responseWrapper.setCharacterEncoding( site.getTemplateCharset() );

            String siteFilePath = ServletUtil.getSiteFilePath( request.getServletContext() );

            request.setCharacterEncoding( site.getTemplateCharset() );
            response.setCharacterEncoding( site.getTemplateCharset() );

            // 获取栏目发布规则
            PublishRuleBean ruleBean = publishService.retrieveSinglePublishRuleBean( classBean
                .getContentPublishRuleId() );

            if( ruleBean == null )
            {
                return "error:rule";
            }

            /**
             * 1.内容分页静态化
             */
            // 只有文章资源类型(包括扩展的文章类型)才可内容分页,没有查到分页数据,就说明这不是主文章类型
            StringBuffer inSQlqueryBuf = new StringBuffer();

            List pageInfoList = null;
            List endPageStaticUrlList = new ArrayList();

            inSQlqueryBuf.append( StringUtil.getLongValue( ( ( Long ) info.get( "contentId" ) )
                .toString(), -1 ) );

            pageInfoList = contentService.retrieveAllContentPageByIds( inSQlqueryBuf.toString(),
                classBean.getContentType() );

            if( !pageInfoList.isEmpty() )
            {
                log.info( "[发布] 当前为文章资源模型内容,将发布内容分页数为" + pageInfoList.size() );
            }

            int k = 0;
            Map pageInfo = null;
            Map firstPageInfo = null;

            for ( k = 0; k < pageInfoList.size(); k++ )
            {
                pageInfo = ( Map ) pageInfoList.get( k );
                endPageStaticUrlList.add( new Object[] {
                    ruleBean.getFullContentClassPagePublishPath( site, classBean, pageInfo,
                        siteFilePath, ( Integer ) pageInfo.get( "pos" ) )[1],
                    ( Long ) pageInfo.get( "contentId" ), ( Integer ) pageInfo.get( "pos" ) } );
            }

            // 分页url首先更新
            contentService.setPageContentStaticURL( endPageStaticUrlList );

            // 静态发布内容分页

            for ( k = 0; k < pageInfoList.size(); k++ )
            {
                if( k == 0 )
                {
                    // 记录文章类型第一页内容分页
                    firstPageInfo = ( Map ) pageInfoList.get( k );
                }

                pageInfo = ( Map ) pageInfoList.get( k );

                doContentPageStaticAction( request, response, site, classBean, pageInfo,
                    targetEndRequestUrl, genHtmlFileList, mob, pad );

                // 移动
                if( Constant.COMMON.ON.equals( site.getMobVm() ) )
                {
                    // doMobContentPageStaticAction( request, response,
                    // site, classBean, pageInfo,
                    // genHtmlFileList, "mob" );
                }

                if( Constant.COMMON.ON.equals( site.getPadVm() ) )
                {
                    // doMobContentPageStaticAction( request, response,
                    // site, classBean, pageInfo,
                    // genHtmlFileList, "pad" );
                }

            }

            /**
             * 2.单页静态化
             */

            if( firstPageInfo != null )
            {
                /**
                 * 重新传递info,使得不需要重复查询数据库,注意若存在分页,需要传递第一页
                 */
                request.setAttribute( Constant.CONTENT.HTML_PUB_ACTION_FLAG, Boolean.TRUE );
                request.setAttribute( "Info", firstPageInfo );
            }

            // 对于不存在的地址,需要友好处理
            try
            {
                // freemarker
                if( targetEndRequestUrl.indexOf( ".thtml?" ) != -1
                    || targetEndRequestUrl.indexOf( ".thtml" ) != -1 )
                {
                    request.setAttribute( "param", paramMap( targetEndRequestUrl ) );

                    request.setAttribute( "request", request );
                }

                request.getRequestDispatcher( targetEndRequestUrl ).include( request,
                    responseWrapper );
            }
            catch ( Exception e )
            {
                return "error:template";
            }

            responseWrapper.flushBuffer();

            WrappedOutputStream contentOutputStream = ( WrappedOutputStream ) responseWrapper
                .getOutputStream();

            String[] pathInfo = ruleBean.getFullContentClassPublishPath( site, classBean, info,
                siteFilePath );

            String filePath = pathInfo[0];

            if( mob && Constant.COMMON.ON.equals( site.getMobVm() ) )
            {
                filePath = pathInfo[2];
            }

            if( pad && Constant.COMMON.ON.equals( site.getPadVm() ) )
            {
                filePath = pathInfo[4];
            }

            BufferedResponseWrapper.writeHtml( contentOutputStream, siteFilePath
                + site.getSiteRoot() + File.separator + site.getPublishRoot() + File.separator
                + filePath );

            genHtmlFileList.add( new String[] {
                filePath,
                siteFilePath + site.getSiteRoot() + File.separator + site.getPublishRoot()
                    + File.separator + filePath } );

            // 返回静态页文件路径
            return pathInfo[1];
        }

        return "";
    }

    /**
     * 对单一content进行静态化
     * 
     * @param request
     * @param response
     * @param contentId
     * @param classId
     * @param contentTemplet
     * @return
     * @throws Exception
     */
    private String doMobContentStaticActionNotUse( HttpServletRequest request,
        HttpServletResponse response, SiteGroupBean site, ContentClassBean classBean, Map info,
        List genHtmlFileList, String flag ) throws Exception
    {
        Long contentId = ( Long ) info.get( "contentId" );

        site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( classBean.getSiteFlag() );

        DISPATCH_TEMPLATE_PATH = site.getSiteRoot() + Constant.CONTENT.URL_SEP + "template"
            + Constant.CONTENT.URL_SEP;

        String targetEndRequestUrl = Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
            + classBean.getMobContentTemplateUrl();

        if( "pad".equals( flag ) )
        {
            targetEndRequestUrl = Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
                + classBean.getPadContentTemplateUrl();
        }

        Map infoState = ( Map ) contentService.retrieveContentPublishInfo( contentId );

        Integer censorState = ( Integer ) infoState.get( "censorState" );
        // 只有通过审核的内容才可发布
        if( censorState != null && Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( censorState ) )
        {
            // String esTemplate = ( String ) infoState
            // .get( "especialTemplateUrl" );

            // if( StringUtil.isStringNull( esTemplate ) )
            {
                targetEndRequestUrl = StringUtil.replaceString( targetEndRequestUrl,
                    "{content-id}", contentId.toString(), false, false );
            }
            // else
            // {
            // targetEndRequestUrl = StringUtil.replaceString( esTemplate,
            // "{content-id}", contentId.toString(), false, false );
            // }

            targetEndRequestUrl = StringUtil.replaceString( targetEndRequestUrl, "{class-id}",
                classBean.getClassId().toString(), false, false );

            HttpServletResponse responseWrapper = new BufferedResponseWrapper( response );

            responseWrapper.setCharacterEncoding( site.getTemplateCharset() );

            String siteFilePath = ServletUtil.getSiteFilePath( request.getServletContext() );

            request.setCharacterEncoding( site.getTemplateCharset() );
            response.setCharacterEncoding( site.getTemplateCharset() );

            // 获取栏目发布规则
            PublishRuleBean ruleBean = publishService.retrieveSinglePublishRuleBean( classBean
                .getContentPublishRuleId() );

            if( ruleBean == null )
            {
                return "error:rule";
            }

            /**
             * 1.内容分页静态化
             */
            // 只有文章资源类型(包括扩展的文章类型)才可内容分页,没有查到分页数据,就说明这不是主文章类型
            StringBuffer inSQlqueryBuf = new StringBuffer();

            List pageInfoList = null;
            List endPageStaticUrlList = new ArrayList();

            inSQlqueryBuf.append( StringUtil.getLongValue( ( ( Long ) info.get( "contentId" ) )
                .toString(), -1 ) );

            pageInfoList = contentService.retrieveAllContentPageByIds( inSQlqueryBuf.toString(),
                classBean.getContentType() );

            if( !pageInfoList.isEmpty() )
            {
                log.info( "[发布] 当前为文章资源模型内容,将发布内容分页数为" + pageInfoList.size() );
            }

            int k = 0;
            Map pageInfo = null;
            Map firstPageInfo = null;

            for ( k = 0; k < pageInfoList.size(); k++ )
            {
                pageInfo = ( Map ) pageInfoList.get( k );
                // endPageStaticUrlList.add( new Object[] {
                // ruleBean.getFullContentClassPagePublishPath( site,
                // classBean, pageInfo, siteFilePath, ( Integer ) pageInfo
                // .get( "pos" ) )[1],
                // ( Long ) pageInfo.get( "contentId" ),
                // ( Integer ) pageInfo.get( "pos" ) } );
            }

            // 分页url首先更新
            // contentService.setPageContentStaticURL( endPageStaticUrlList );

            // 静态发布内容分页

            for ( k = 0; k < pageInfoList.size(); k++ )
            {
                if( k == 0 )
                {
                    // 记录文章类型第一页内容分页
                    firstPageInfo = ( Map ) pageInfoList.get( k );
                }

                pageInfo = ( Map ) pageInfoList.get( k );

                // doContentPageStaticAction( request, response, site,
                // classBean,
                // pageInfo, targetEndRequestUrl, genHtmlFileList );

                // 移动
                if( Constant.COMMON.ON.equals( site.getMobVm() ) )
                {
                    // doMobContentPageStaticAction( request, response,
                    // site, classBean, pageInfo,
                    // genHtmlFileList, "mob" );
                }

                if( Constant.COMMON.ON.equals( site.getPadVm() ) )
                {
                    // doMobContentPageStaticAction( request, response,
                    // site, classBean, pageInfo,
                    // genHtmlFileList, "pad" );
                }

            }

            /**
             * 2.单页静态化
             */

            if( firstPageInfo != null )
            {
                /**
                 * 重新传递info,使得不需要重复查询数据库,注意若存在分页,需要传递第一页
                 */
                request.setAttribute( Constant.CONTENT.HTML_PUB_ACTION_FLAG, Boolean.TRUE );
                request.setAttribute( "Info", firstPageInfo );
            }

            // 对于不存在的地址,需要友好处理
            try
            {
                // freemarker
                if( targetEndRequestUrl.indexOf( ".thtml?" ) != -1
                    || targetEndRequestUrl.indexOf( ".thtml" ) != -1 )
                {
                    request.setAttribute( "param", paramMap( targetEndRequestUrl ) );

                    request.setAttribute( "request", request );
                }

                request.getRequestDispatcher( targetEndRequestUrl ).include( request,
                    responseWrapper );
            }
            catch ( Exception e )
            {
                return "error:template";
            }

            responseWrapper.flushBuffer();

            WrappedOutputStream contentOutputStream = ( WrappedOutputStream ) responseWrapper
                .getOutputStream();

            String[] pathInfo = ruleBean.getFullContentClassPublishPath( site, classBean, info,
                siteFilePath );

            String filePath = pathInfo[2];

            if( "pad".equals( flag ) )
            {
                filePath = pathInfo[4];
            }

            BufferedResponseWrapper.writeHtml( contentOutputStream, siteFilePath
                + site.getSiteRoot() + File.separator + site.getPublishRoot() + File.separator
                + filePath );

            genHtmlFileList.add( new String[] {
                pathInfo[0],
                siteFilePath + site.getSiteRoot() + File.separator + site.getPublishRoot()
                    + File.separator + filePath } );

            // 返回静态页文件路径
            return pathInfo[1];
        }

        return "";
    }

    /**
     * 对内容页进行静态化的操作
     * 
     * @param request
     * @param response
     * @param classBean
     * @param generatorBean
     * @throws Exception
     */
    private void doContentPageStaticAction( HttpServletRequest request,
        HttpServletResponse response, SiteGroupBean site, ContentClassBean classBean, Map info,
        String targetEndRequestUrl, List genHtmlFileList, boolean mob, boolean pad )
        throws Exception
    {
        HttpServletResponse responseWrapper = new BufferedResponseWrapper( response );

        responseWrapper.setCharacterEncoding( site.getTemplateCharset() );

        // 此出的静态化独立为模版的编码，是否和系统的编码一致，后期要解决
        request.setCharacterEncoding( site.getTemplateCharset() );
        response.setCharacterEncoding( site.getTemplateCharset() );

        // 获取栏目发布规则
        PublishRuleBean ruleBean = publishService.retrieveSinglePublishRuleBean( classBean
            .getContentPublishRuleId() );

        if( ruleBean == null )
        {
            log.error( "本次发布失败,请检查栏目 " + classBean.getClassName() + " 的内容页发布规则配置是否正确" );
            return;
        }

        /**
         * 在进行静态之间,将info传给标签,使得不需要重复查询数据库
         */
        request.setAttribute( Constant.CONTENT.HTML_PUB_ACTION_FLAG, Boolean.TRUE );
        request.setAttribute( "Info", info );

        // freemarker
        if( targetEndRequestUrl.indexOf( ".thtml?" ) != -1
            || targetEndRequestUrl.indexOf( ".thtml" ) != -1 )
        {
            request.setAttribute( "param", paramMap( targetEndRequestUrl ) );

            request.setAttribute( "request", request );
        }

        request.getRequestDispatcher( targetEndRequestUrl ).include( request, responseWrapper );

        // 切记,一定要在获得output之前flushBuffer
        responseWrapper.flushBuffer();

        WrappedOutputStream contentOutputStream = ( WrappedOutputStream ) responseWrapper
            .getOutputStream();

        String siteFilePath = ServletUtil.getSiteFilePath( request.getServletContext() );

        String[] pathInfo = ruleBean.getFullContentClassPagePublishPath( site, classBean, info,
            null, ( Integer ) info.get( "pos" ) );

        String filePath = pathInfo[0];

        if( mob && Constant.COMMON.ON.equals( site.getMobVm() ) )
        {
            filePath = pathInfo[2];
        }

        if( pad && Constant.COMMON.ON.equals( site.getPadVm() ) )
        {
            filePath = pathInfo[4];
        }

        BufferedResponseWrapper.writeHtml( contentOutputStream, siteFilePath + site.getSiteRoot()
            + File.separator + site.getPublishRoot() + File.separator + filePath );

        genHtmlFileList.add( new String[] {
            filePath,
            siteFilePath + site.getSiteRoot() + File.separator + site.getPublishRoot()
                + File.separator + filePath } );
    }

    /**
     * 对内容页进行静态化的操作
     * 
     * @param request
     * @param response
     * @param classBean
     * @param generatorBean
     * @throws Exception
     */
    private void doMobContentPageStaticActionNotUse( HttpServletRequest request,
        HttpServletResponse response, SiteGroupBean site, ContentClassBean classBean, Map info,
        List genHtmlFileList, String flag ) throws Exception
    {
        HttpServletResponse responseWrapper = new BufferedResponseWrapper( response );

        responseWrapper.setCharacterEncoding( site.getTemplateCharset() );

        // 此出的静态化独立为模版的编码，是否和系统的编码一致，后期要解决
        request.setCharacterEncoding( site.getTemplateCharset() );
        response.setCharacterEncoding( site.getTemplateCharset() );

        // 获取栏目发布规则
        PublishRuleBean ruleBean = publishService.retrieveSinglePublishRuleBean( classBean
            .getContentPublishRuleId() );

        if( ruleBean == null )
        {
            log.error( "本次发布失败,请检查栏目 " + classBean.getClassName() + " 的内容页发布规则配置是否正确" );
            return;
        }

        /**
         * 在进行静态之间,将info传给标签,使得不需要重复查询数据库
         */
        request.setAttribute( Constant.CONTENT.HTML_PUB_ACTION_FLAG, Boolean.TRUE );
        request.setAttribute( "Info", info );

        String targetEndRequestUrl = Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
            + classBean.getMobContentTemplateUrl();

        if( "pad".equals( flag ) )
        {
            targetEndRequestUrl = Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH
                + classBean.getPadContentTemplateUrl();
        }

        // 模版处理

        targetEndRequestUrl = StringUtil.replaceString( targetEndRequestUrl, "{content-id}",
            classBean.getClassId().toString(), false, false );

        // freemarker
        if( targetEndRequestUrl.indexOf( ".thtml?" ) != -1
            || targetEndRequestUrl.indexOf( ".thtml" ) != -1 )
        {
            request.setAttribute( "param", paramMap( targetEndRequestUrl ) );

            request.setAttribute( "request", request );
        }

        request.getRequestDispatcher( targetEndRequestUrl ).include( request, responseWrapper );

        // 切记,一定要在获得output之前flushBuffer
        responseWrapper.flushBuffer();

        WrappedOutputStream contentOutputStream = ( WrappedOutputStream ) responseWrapper
            .getOutputStream();

        String siteFilePath = ServletUtil.getSiteFilePath( request.getServletContext() );

        Integer pos = ( Integer ) info.get( "pos" );

        if( pos == null )
        {
            pos = 0;
        }

        String[] pathInfo = ruleBean.getFullContentClassPagePublishPath( site, classBean, info,
            null, pos );

        String filePath = pathInfo[2];

        if( "pad".equals( flag ) )
        {
            filePath = pathInfo[4];
        }

        BufferedResponseWrapper.writeHtml( contentOutputStream, siteFilePath + site.getSiteRoot()
            + File.separator + site.getPublishRoot() + File.separator + filePath );

        genHtmlFileList.add( new String[] {
            pathInfo[0],
            siteFilePath + site.getSiteRoot() + File.separator + site.getPublishRoot()
                + File.separator + filePath } );
    }

    /**
     * 执行首页的静态化动作
     * 
     * @param request
     * @param response
     * @param generatorBean
     * @throws Exception
     */
    private String doHomePageStaticAction( HttpServletRequest request,
        HttpServletResponse response, GeneratorBean generatorBean, SiteGroupBean siteBean,
        List genListHtmlList ) throws Exception
    {

        // 首页静态发布

        HttpServletResponse responseWrapper = new BufferedResponseWrapper( response );

        // 此出的静态化独立为模版的编码，若为SSI模式,则web容器的SSI编码必须要和模板一致

        request.setCharacterEncoding( siteBean.getTemplateCharset() );
        response.setCharacterEncoding( siteBean.getTemplateCharset() );

        try
        {
            // freemarker
            if( siteBean.getHomePageTemplate().indexOf( ".thtml?" ) != -1
                || siteBean.getHomePageTemplate().indexOf( ".thtml" ) != -1 )
            {
                request.setAttribute( "param", paramMap( siteBean.getHomePageTemplate() ) );

                request.setAttribute( "request", request );
            }

            request.getRequestDispatcher(
                Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH + siteBean.getHomePageTemplate() )
                .include( request, responseWrapper );
        }
        catch ( Exception e )
        {

            return "error:home";
        }

        // 切记,一定要在获得output之前flushBuffer
        responseWrapper.flushBuffer();

        WrappedOutputStream contentOutputStream = ( WrappedOutputStream ) responseWrapper
            .getOutputStream();

        String siteFilePath = ServletUtil.getSiteFilePath( request.getServletContext() );

        String htmlCachePath = siteFilePath + siteBean.getSiteRoot() + File.separator
            + siteBean.getPublishRoot() + File.separator;

        File file = new File( htmlCachePath );
        if( !file.exists() )
        {
            file.mkdirs();
        }

        // 每个子站的首页需要放在不同的目录

        String endFileName = null;

        if( Constant.CONTENT.HTML_PUB_S.equals( siteBean.getStaticFileType().toString() ) )
        {
            endFileName = "index.shtml";
        }
        else
        {
            endFileName = "index.html";
        }

        BufferedResponseWrapper.writeHtml( contentOutputStream, htmlCachePath + endFileName );

        siteService.updateSiteStaticUrl( endFileName, siteBean.getSiteId(), "pc" );

        genListHtmlList.add( new String[] { endFileName, htmlCachePath + endFileName } );

        return null;

    }

    /**
     * 执行首页的静态化动作
     * 
     * @param request
     * @param response
     * @param generatorBean
     * @throws Exception
     */
    private String doMobHomePageStaticAction( HttpServletRequest request,
        HttpServletResponse response, GeneratorBean generatorBean, SiteGroupBean siteBean,
        List genListHtmlList, String flag ) throws Exception
    {

        // 首页静态发布

        HttpServletResponse responseWrapper = new BufferedResponseWrapper( response );

        // 此出的静态化独立为模版的编码，若为SSI模式,则web容器的SSI编码必须要和模板一致

        request.setCharacterEncoding( siteBean.getTemplateCharset() );
        response.setCharacterEncoding( siteBean.getTemplateCharset() );

        String tpl = siteBean.getMobHomePageTemplate();

        if( "pad".equals( flag ) )
        {
            tpl = siteBean.getPadHomePageTemplate();
        }

        try
        {
            // freemarker
            if( tpl.indexOf( ".thtml?" ) != -1 || tpl.indexOf( ".thtml" ) != -1 )
            {
                request.setAttribute( "param", paramMap( tpl ) );

                request.setAttribute( "request", request );
            }

            request.getRequestDispatcher( Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH + tpl )
                .include( request, responseWrapper );
        }
        catch ( Exception e )
        {

            return "error:home";
        }

        // 切记,一定要在获得output之前flushBuffer
        responseWrapper.flushBuffer();

        WrappedOutputStream contentOutputStream = ( WrappedOutputStream ) responseWrapper
            .getOutputStream();

        String siteFilePath = ServletUtil.getSiteFilePath( request.getServletContext() );

        String htmlCachePath = siteFilePath + siteBean.getSiteRoot() + File.separator
            + siteBean.getPublishRoot() + File.separator;

        File file = new File( htmlCachePath );
        if( !file.exists() )
        {
            file.mkdirs();
        }

        // 每个子站的首页需要放在不同的目录

        String endFileName = null;

        if( Constant.CONTENT.HTML_PUB_S.equals( siteBean.getStaticFileType().toString() ) )
        {
            endFileName = "mob_index.shtml";

            if( "pad".equals( flag ) )
            {
                endFileName = "pad_index.shtml";

            }
        }
        else
        {
            endFileName = "mob_index.html";

            if( "pad".equals( flag ) )
            {
                endFileName = "pad_index.html";

            }
        }

        BufferedResponseWrapper.writeHtml( contentOutputStream, htmlCachePath + endFileName );

        if( "pad".equals( flag ) )
        {
            siteService.updateSiteStaticUrl( endFileName, siteBean.getSiteId(), "pad" );
        }
        else
        {
            siteService.updateSiteStaticUrl( endFileName, siteBean.getSiteId(), "mob" );
        }

        genListHtmlList.add( new String[] { endFileName, htmlCachePath + endFileName } );

        return null;

    }

    /**
     * 对栏目页进行静态化的操作
     * 
     * @param request
     * @param response
     * @param classBean
     * @param generatorBean
     * @throws Exception
     */
    private String doContentClassPageStaticAction( HttpServletRequest request,
        HttpServletResponse response, ContentClassBean classBean, SiteGroupBean site,
        String targetEndRequestUrl, Integer currentCount, List genListHtmlList, boolean mob,
        boolean pad ) throws Exception
    {
        HttpServletResponse responseWrapper = new BufferedResponseWrapper( response );

        responseWrapper.setCharacterEncoding( site.getTemplateCharset() );

        // 此出的静态化独立为模版的编码，是否和系统的编码一致，后期要解决
        request.setCharacterEncoding( site.getTemplateCharset() );
        response.setCharacterEncoding( site.getTemplateCharset() );

        // 获取栏目发布规则
        PublishRuleBean ruleBean = publishService.retrieveSinglePublishRuleBean( classBean
            .getClassPublishRuleId() );

        if( ruleBean == null )
        {
            log.error( "本次发布失败,请检查栏目 " + classBean.getClassName() + " 的列表页发布规则配置是否正确" );
            return "error:rule";
        }

        // 计数,pn
        Integer disposePageCurrentCount = Integer.valueOf( 1 );

        request.setAttribute( "currentPageCount", disposePageCurrentCount );

        try
        {
            // freemarker
            if( targetEndRequestUrl.indexOf( ".thtml?" ) != -1
                || targetEndRequestUrl.indexOf( ".thtml" ) != -1 )
            {
                request.setAttribute( "param", paramMap( targetEndRequestUrl ) );

                request.setAttribute( "request", request );
            }

            request.getRequestDispatcher( targetEndRequestUrl ).include( request, responseWrapper );
        }
        catch ( Exception e )
        {
            return "error:content";
        }

        // 切记,一定要在获得output之前flushBuffer
        responseWrapper.flushBuffer();

        WrappedOutputStream contentOutputStream = ( WrappedOutputStream ) responseWrapper
            .getOutputStream();

        String siteFilePath = ServletUtil.getSiteFilePath( request.getServletContext() );

        String[] pathInfo = ruleBean.getFullContentClassPagePublishPath( site, classBean, null,
            siteFilePath, currentCount );

        String filePath = pathInfo[0];

        if( mob && Constant.COMMON.ON.equals( site.getMobVm() ) )
        {
            filePath = pathInfo[2];
        }

        if( pad && Constant.COMMON.ON.equals( site.getPadVm() ) )
        {
            filePath = pathInfo[4];
        }

        BufferedResponseWrapper.writeHtml( contentOutputStream, siteFilePath + site.getSiteRoot()
            + File.separator + site.getPublishRoot() + File.separator + filePath );

        // 记录发布的静态文件
        genListHtmlList.add( new String[] {
            pathInfo[0],
            siteFilePath + site.getSiteRoot() + File.separator + site.getPublishRoot()
                + File.separator + filePath } );

        return null;
    }

    /**
     * 对移动栏目页进行静态化的操作
     * 
     * @param request
     * @param response
     * @param generatorBean
     * @throws Exception
     */
    private String doMobContentClassStaticActionNotUse( HttpServletRequest request,
        HttpServletResponse response, ContentClassBean classBean, SiteGroupBean siteBean,
        List genListHtmlList, String flag, String tplType ) throws Exception
    {

        HttpServletResponse responseWrapper = new BufferedResponseWrapper( response );

        // 此出的静态化独立为模版的编码，若为SSI模式,则web容器的SSI编码必须要和模板一致

        request.setCharacterEncoding( siteBean.getTemplateCharset() );
        response.setCharacterEncoding( siteBean.getTemplateCharset() );

        String tpl = classBean.getMobClassTemplateUrl();

        if( "pad".equals( flag ) )
        {
            tpl = classBean.getPadClassTemplateUrl();
        }

        if( "channel".equals( tplType ) )
        {
            tpl = classBean.getMobClassHomeTemplateUrl();

            if( "pad".equals( flag ) )
            {
                tpl = classBean.getPadClassHomeTemplateUrl();
            }
        }

        tpl = StringUtil.replaceString( tpl, "{class-id}", classBean.getClassId().toString(),
            false, false );
        try
        {
            // freemarker
            if( tpl.indexOf( ".thtml?" ) != -1 || tpl.indexOf( ".thtml" ) != -1 )
            {
                request.setAttribute( "param", paramMap( tpl ) );

                request.setAttribute( "request", request );
            }

            request.getRequestDispatcher( Constant.CONTENT.URL_SEP + DISPATCH_TEMPLATE_PATH + tpl )
                .include( request, responseWrapper );
        }
        catch ( Exception e )
        {

            return "error:home";
        }

        // 切记,一定要在获得output之前flushBuffer
        responseWrapper.flushBuffer();

        String siteFilePath = ServletUtil.getSiteFilePath( request.getServletContext() );

        String[] pathInfo = null;

        String htmlRootPath = siteFilePath + siteBean.getSiteRoot() + File.separator
            + siteBean.getPublishRoot() + File.separator;

        PublishRuleBean ruleBean = publishService.retrieveSinglePublishRuleBean( classBean
            .getClassPublishRuleId() );

        if( ruleBean == null )
        {
            return "NULL_RULE";
        }

        if( StringUtil.getIntValue( classBean.getListPageLimit(), 0 ) > 0 )
        {
            pathInfo = ruleBean.getFullContentClassPagePublishPath( siteBean, classBean, null,
                siteFilePath, 1 );

        }
        else
        {
            // 不需分页
            pathInfo = ruleBean.getFullContentClassPublishPath( siteBean, classBean, null,
                siteFilePath );
        }

        String filePath = pathInfo[2];

        if( "pad".equals( flag ) )
        {
            filePath = pathInfo[4];
        }

        BufferedResponseWrapper.writeHtml( ( WrappedOutputStream ) responseWrapper
            .getOutputStream(), htmlRootPath + filePath );

        genListHtmlList.add( new String[] {
            filePath,
            siteFilePath + siteBean.getSiteRoot() + File.separator + siteBean.getPublishRoot()
                + File.separator + filePath } );

        return null;

    }

    public void setPublishStatus( boolean homeSucc, boolean channelSucc, boolean listSucc,
        boolean contentSucc, PublishStatusBean status )
    {
        if( homeSucc )
        {
            status.setHomeOperStatus( Constant.COMMON.ON );
        }

        if( channelSucc )
        {
            status.setChannelOperStatus( Constant.COMMON.ON );
        }

        if( listSucc )
        {
            status.setListOperStatus( Constant.COMMON.ON );
        }

        if( contentSucc )
        {
            status.setContentOperStatus( Constant.COMMON.ON );
        }

    }

    public void setTransferStatus( boolean homeSucc, boolean channelSucc, boolean listSucc,
        boolean contentSucc, PublishStatusBean status )
    {
        if( homeSucc )
        {
            status.setHomeTranStatus( Constant.COMMON.ON );
        }

        if( channelSucc )
        {
            status.setChannelTranStatus( Constant.COMMON.ON );
        }

        if( listSucc )
        {
            status.setListTranStatus( Constant.COMMON.ON );
        }

        if( contentSucc )
        {
            status.setContentTranStatus( Constant.COMMON.ON );
        }

    }

    private Map paramMap( String fullUrl )
    {
        Map requestParamContext = new HashMap();

        String param = StringUtil.subString( fullUrl, fullUrl.indexOf( "?" ) + 1, fullUrl.length() );

        if( StringUtil.isStringNotNull( param ) )
        {
            String[] params = StringUtil.split( param, "&" );

            for ( String ps : params )
            {
                String[] kv = ps.split( "=" );

                String val = "";

                if( kv.length == 2 )
                {
                    val = kv[1];
                }

                requestParamContext.put( kv[0], val );
            }
        }

        return requestParamContext;
    }
}
