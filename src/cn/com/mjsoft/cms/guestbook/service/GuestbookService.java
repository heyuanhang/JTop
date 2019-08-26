package cn.com.mjsoft.cms.guestbook.service;

import static cn.com.mjsoft.cms.common.ServiceUtil.cleanBasicHtmlByWhiteRule;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.content.dao.ContentDao;
import cn.com.mjsoft.cms.content.dao.vo.ContentMainInfo;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.guestbook.bean.GuestbookConfigBean;
import cn.com.mjsoft.cms.guestbook.bean.GuestbookMainInfoBean;
import cn.com.mjsoft.cms.guestbook.dao.GuestbookDao;
import cn.com.mjsoft.cms.guestbook.dao.vo.GuestbookConfig;
import cn.com.mjsoft.cms.guestbook.dao.vo.GuestbookMainInfo;
import cn.com.mjsoft.cms.message.service.MessageService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.workflow.bean.WorkflowStepActionBean;
import cn.com.mjsoft.cms.workflow.bean.WorkflowStepInfoBean;
import cn.com.mjsoft.cms.workflow.dao.WorkFlowDao;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;

public class GuestbookService
{
    private static Logger log = Logger.getLogger( GuestbookService.class );

    private static Cache countCache = new ClusterCacheAdapter( 500,
        "contentService.listContentCache" );

    private static Cache gbCache = new ClusterCacheAdapter( 500, "contentService.listContentCache" );

    private static Cache gbCfgCache = new ClusterCacheAdapter( 120,
        "contentService.listContentCache" ); 

    private static GuestbookService service = null;

    private MetaDataService metaDataService = MetaDataService.getInstance();

    private MessageService messageService = MessageService.getInstance();

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private static ResourcesService resService = ResourcesService.getInstance();

    private GuestbookDao gbDao;

    private ContentDao contentDao;

    private WorkFlowDao workFlowDao;

    private GuestbookService()
    {
        gbDao = new GuestbookDao( mysqlEngine );
        contentDao = new ContentDao( mysqlEngine );
        workFlowDao = new WorkFlowDao( mysqlEngine );

    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new GuestbookService();
        }
    }

    public static GuestbookService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public List retrieveAllGuestbookConfigBeanListBySite( Long siteId )
    {
        String key = "retrieveAllGuestbookConfigBeanListBySite" + siteId;

        List result = ( List ) gbCfgCache.getEntry( key );

        if( result == null )
        {
            result = gbDao.queryAllGuestbookConfigBeanList( siteId );

            gbCfgCache.putEntry( key, result );
        }

        return result;
    }

    public Long retrieveGuestbookMainInfoCount( String configFlag, String isReply, String isCensor,
        String isOpen )
    {
        GuestbookConfigBean configBean = retrieveSingleGuestbookConfigBeanByConfigFlag( configFlag );

        if( configBean == null )
        {
            // 缺少配置
            return Long.valueOf( 0 );
        }

        Long cfgId = configBean.getConfigId();

        Integer isOp = Integer.valueOf( StringUtil.getIntValue( isOpen, -1 ) );

        Integer isCe = Integer.valueOf( StringUtil.getIntValue( isCensor, -1 ) );

        Integer isRe = Integer.valueOf( StringUtil.getIntValue( isReply, -1 ) );

        String key = "retrieveGuestbookMainInfoCount:" + cfgId + "|" + isReply + "|" + isCensor
            + "|" + isOpen;

        Long result = ( Long ) countCache.getEntry( key );

        if( result == null )
        {
            if( "".equals( isReply ) )
            {
                if( "".equals( isCensor ) )
                {
                    if( "".equals( isOpen ) )
                    {
                        result = gbDao.queryGuestbookMainInfoCount( cfgId );
                    }
                    else
                    {
                        result = gbDao.queryGuestbookMainInfoCountIsOpen( isOp, cfgId );
                    }
                }
                else
                {
                    if( "".equals( isOpen ) )
                    {
                        result = gbDao.queryGuestbookMainInfoCountIsCensor( isCe, cfgId );
                    }
                    else
                    {
                        result = gbDao.queryGuestbookMainInfoCountIsOpenAndIsCensor( isCe, isOp,
                            cfgId );
                    }
                }
            }
            else
            {
                if( "".equals( isCensor ) )
                {
                    if( "".equals( isOpen ) )
                    {
                        result = gbDao.queryGuestbookMainInfoCountIsReply( isRe, cfgId );
                    }
                    else
                    {
                        result = gbDao.queryGuestbookMainInfoCountIsOpenAndIsReply( isOp, isRe,
                            cfgId );
                    }
                }
                else
                {
                    if( "".equals( isOpen ) )
                    {
                        result = gbDao.queryGuestbookMainInfoCountIsCensorAndIsReply( isCe, isRe,
                            cfgId );
                    }
                    else
                    {
                        result = gbDao.queryGuestbookMainInfoCountIsOpenAndIsCensorAndIsReply(
                            isCe, isOp, isRe, cfgId );
                    }
                }
            }

            countCache.putEntry( key, result );
        }

        return result;
    }

    public List retrieveGuestbookMainInfoMapList( String configFlag, String isReply,
        String isCensor, String isOpen, Long startPos, Integer pageSize )
    {
        // Long cfgId = Long.valueOf( StringUtil.getLongValue( configId, -1 ) );

        Integer isOp = Integer.valueOf( StringUtil.getIntValue( isOpen, -1 ) );

        Integer isCe = Integer.valueOf( StringUtil.getIntValue( isCensor, -1 ) );

        Integer isRe = Integer.valueOf( StringUtil.getIntValue( isReply, -1 ) );

        String key = "retrieveGuestbookMainInfoMapList:" + configFlag + "|" + isReply + "|"
            + isCensor + "|" + isOpen + "|" + startPos + "|" + pageSize;

        List result = ( List ) gbCache.getEntry( key );

        if( result == null )
        {

            GuestbookConfigBean configBean = retrieveSingleGuestbookConfigBeanByConfigFlag( configFlag );

            if( configBean == null )
            {
                // 缺少配置
                return new ArrayList( 1 );
            }

            // 扩展模型信息

            ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
                .retrieveSingleModelPerMysqlCodeBean( configBean.getInfoModelId() );

            DataModelBean model = metaDataService.retrieveSingleDataModelBeanById( configBean
                .getInfoModelId() );

            if( "".equals( isReply ) )
            {
                if( "".equals( isCensor ) )
                {
                    if( "".equals( isOpen ) )
                    {
                        result = gbDao.queryGuestbookMainInfoMapList( configBean.getConfigId(),
                            model, sqlCodeBean, pageSize, startPos );
                    }
                    else
                    {
                        result = gbDao.queryGuestbookMainInfoMapIsOpenList( configBean
                            .getConfigId(), isOp, model, sqlCodeBean, pageSize, startPos );
                    }
                }
                else
                {
                    if( "".equals( isOpen ) )
                    {
                        result = gbDao.queryGuestbookMainInfoMapIsCensorList( configBean
                            .getConfigId(), isCe, model, sqlCodeBean, pageSize, startPos );
                    }
                    else
                    {
                        result = gbDao.queryGuestbookMainInfoMapIsOpenAndIsCensorList( configBean
                            .getConfigId(), isOp, isCe, model, sqlCodeBean, pageSize, startPos );
                    }
                }
            }
            else
            {
                if( "".equals( isCensor ) )
                {
                    if( "".equals( isOpen ) )
                    {
                        result = gbDao.queryGuestbookMainInfoMapIsReplyList( configBean
                            .getConfigId(), isRe, model, sqlCodeBean, pageSize, startPos );

                    }
                    else
                    {
                        result = gbDao.queryGuestbookMainInfoMapIsReplyAndIsOpenList( configBean
                            .getConfigId(), isRe, isOp, model, sqlCodeBean, pageSize, startPos );
                    }
                }
                else
                {
                    if( "".equals( isOpen ) )
                    {

                        result = gbDao.queryGuestbookMainInfoMapIsReplyAndIsCensorList( configBean
                            .getConfigId(), isCe, isRe, model, sqlCodeBean, pageSize, startPos );
                    }
                    else
                    {
                        result = gbDao.queryGuestbookMainInfoMapIsOpenAndIsCensorAndIsReplyList(
                            configBean.getConfigId(), isOp, isRe, isCe, model, sqlCodeBean,
                            pageSize, startPos );
                    }
                }
            }

            gbCache.putEntry( key, result );
        }

        return result;
    }

    public GuestbookConfigBean retrieveSingleGuestbookConfigBeanByConfigId( Long configId )
    {
        String key = "retrieveSingleGuestbookConfigBeanByConfigId" + configId;

        GuestbookConfigBean result = ( GuestbookConfigBean ) gbCfgCache.getEntry( key );

        if( result == null )
        {
            result = gbDao.querySingleGuestbookConfigBeanByConfigId( configId );

            gbCfgCache.putEntry( key, result );
        }
        return result;
    }

    public GuestbookConfigBean retrieveSingleGuestbookConfigBeanByConfigFlag( String configFlag )
    {
        return gbDao.querySingleGuestbookConfigBeanByConfigFlag( configFlag );
    }

    public Map retrieveSingleGuestbookInfoMapByGbId( Long gbId )
    {

        GuestbookMainInfoBean gbMain = gbDao.querySingleGuestbookMainInfoByGbid( gbId );

        if( gbMain == null )
        {
            return new HashMap();
        }

        GuestbookConfigBean configBean = gbDao.querySingleGuestbookConfigBeanByConfigId( gbMain
            .getConfigId() );

        if( configBean == null )
        {
            // 缺少配置
            return new HashMap( 1 );
        }

        // 扩展模型信息

        ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( configBean.getInfoModelId() );

        DataModelBean model = metaDataService.retrieveSingleDataModelBeanById( configBean
            .getInfoModelId() );

        return gbDao.querySingleGuestbookInfoMapByGbId( gbId, model, sqlCodeBean );
    }

    public void addNewGuestbookConfig( GuestbookConfig gbCfg )
    {
        try
        {
            gbDao.save( gbCfg );
        }
        finally
        {
            GuestbookDao.clearConfigBeanCache();

            clear();

            clearCfg();
        }
    }

    public void editGuestbookConfig( GuestbookConfig gbCfg )
    {
        try
        {
            gbDao.update( gbCfg );
        }
        finally
        {
            GuestbookDao.clearConfigBeanCache();

            clear();

            clearCfg();
        }
    }

    public void addNewGuestbookInfo( GuestbookMainInfo gbInfo, DataModelBean model,
        List filedBeanList, ModelPersistenceMySqlCodeBean sqlCodeBean, Map requestParams )
    {

        if( gbInfo == null )
        {
            return;
        }

        // html白名单
        if( StringUtil.isStringNotNull( gbInfo.getGbTitle() ) )
        {
            gbInfo.setGbTitle( ContentService.getInstance().replcaeContentTextSensitive(
                gbInfo.getGbTitle() ) );
        }

        gbInfo.setGbText( ContentService.getInstance().replcaeContentTextSensitive(
            cleanBasicHtmlByWhiteRule( gbInfo.getGbText() ) ) );

        try
        {
            mysqlEngine.beginTransaction();

            UpdateState us = gbDao.save( gbInfo );

            if( us.haveKey() && model != null && filedBeanList != null && sqlCodeBean != null )
            {
                ModelFiledInfoBean bean = null;

                List userDefineParamList = new ArrayList();

                String reUrl = null;

                Object val = null;

                for ( int j = 0; j < filedBeanList.size(); j++ )
                {
                    bean = ( ModelFiledInfoBean ) filedBeanList.get( j );
                    // 需要引入filed元数据来对不同类型字段进行对应处理

                    val = ServiceUtil
                        .disposeDataModelFiledFromWeb( bean, requestParams, null, true );

                    // 编辑器类型html白名单
                    if( Constant.METADATA.EDITER == bean.getHtmlElementId().intValue() )
                    {
                        val = ServiceUtil.cleanEditorHtmlByWhiteRule( ( String ) val );
                    }

                    userDefineParamList.add( val );

                    // 单图水印处理
                    if( Constant.METADATA.UPLOAD_IMG == bean.getHtmlElementId().intValue()
                        && Constant.COMMON.ON.equals( bean.getNeedMark() ) )
                    {
                        // 水印处理
                        reUrl = ServiceUtil.getImageReUrl( ( String ) val );

                        // 已经加过水印的不需要再增加
                        if( !Constant.COMMON.ON.equals( resService.getImageMarkStatus( reUrl ) ) )
                        {
                            if( ServiceUtil.disposeImageMark(
                                ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                                    .getCurrentLoginSiteInfo(), reUrl, Integer.valueOf( ServiceUtil
                                    .getImageW( ( String ) val ) ), Integer.valueOf( ServiceUtil
                                    .getImageH( ( String ) val ) ) ) )
                            {
                                // 成功加水印则更新
                                resService.setImageMarkStatus( reUrl, Constant.COMMON.ON );
                            }
                        }
                    }

                }

                // 添加ID到最后位置
                userDefineParamList.add( Long.valueOf( us.getKey() ) );

                contentDao.saveOrUpdateModelContent( sqlCodeBean.getInsertSql(),
                    userDefineParamList.toArray() );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clear();
        }

    }

    public void replyGuestbook( Map params, Long gbId, String replyText, String replyMan,
        List wfActionList )
    {
        Integer infoType = 2;

        try
        {
            // 须提前获取当前内容
            GuestbookMainInfoBean gbMain = gbDao.querySingleGuestbookMainInfoByGbid( gbId );

            GuestbookConfigBean configBean = gbDao
                .querySingleGuestbookConfigBeanByConfigId( StringUtil.getLongValue(
                    ( String ) params.get( "configId" ), -1 ) );

            // 确定工作流状态
            Object[] wi = disposeWorkflowState( params, infoType, gbId, configBean, true,
                wfActionList, gbMain.getIsCensor() );

            Integer endCensorState = ( Integer ) wi[0];

            String flowTarget = ( String ) wi[1];

            params.put( "_*sys_ft", wi[1] );

            gbDao.updateGuestbookReplyInfo( gbId, replyText, replyMan, new Date( DateAndTimeUtil
                .clusterTimeMillis() ), StringUtil.getIntValue( ( String ) params.get( "isOpen" ), Constant.COMMON.OFF ) );

            if( Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( endCensorState ) )
            {
                if( "reply".equals( flowTarget ) )
                {
                    gbDao.updateGuestbookCensorStatus( gbId,
                        Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );

                    gbDao
                        .updateGuestbookReplyStatus( gbId, Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );
                }
                else if( "delete".equals( flowTarget ) 
                        && !Constant.WORKFLOW.REJECT_ACTION_ID_VALUE.toString().equals( ( String ) params.get( "toStepId" ) ) )
                {
                    String ids = gbId.toString() + ",";

                    SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
                        .getSecuritySession().getCurrentLoginSiteInfo();

                    deleteGuestbookAllInfoByIds( StringUtil.changeStringToList( ids, "," ),
                        configBean, site );
                }
            }
            else if( Constant.WORKFLOW.CENSOR_STATUS_DRAFT.equals( endCensorState ) )
            {
                if( "reply".equals( flowTarget ) )
                {
                    gbDao.updateGuestbookCensorStatus( gbId,
                        Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW );

                    gbDao
                        .updateGuestbookReplyStatus( gbId, Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW );
                }
                else if( "delete".equals( flowTarget ) )
                {

                }
            }

            // 记录日志

            // Long fromStepId = StringUtil.getLongValue( ( String ) params.get(
            // "fromStepId" ), -1 );

            Long toStepId = StringUtil.getLongValue( ( String ) params.get( "toStepId" ), -1 );

            String actionName = "回复通过";

            if( Constant.WORKFLOW.REJECT_ACTION_ID_VALUE.equals( toStepId ) )
            {
                actionName = "否决";
            }

            if( Constant.WORKFLOW.CENSOR_STATUS_DRAFT.equals( endCensorState ) )
            {
                actionName = "回复无效";
            }

            String sysFlowSuggest = ( String ) params.get( "jtopcms_sys_flow_suguest" );

            String sysFlowEditSuggest = ( String ) params.get( "jtopcms_sys_flow_edit_suguest" );

            if( wfActionList.isEmpty() )
            {
                String fm =   "\n\n修改建议： " + sysFlowEditSuggest;

                ContentService.getInstance()
                    .addContentOperInfo(
                        gbId,
                        ( String ) SecuritySessionKeeper.getSecuritySession().getAuth()
                            .getApellation(), actionName, fm, "电脑端", 2 );
            }

            String sysNextStep = ( String ) params.get( "jtopcms_sys_flow_next_step" );

            String sysPrevStep = ( String ) params.get( "jtopcms_sys_flow_prev_step" );

            sendWorkflowMsg( wfActionList, gbMain, sysFlowSuggest, sysFlowEditSuggest, sysNextStep,
                sysPrevStep, "电脑端" );
        }
        finally
        {
            clear();
        }
    }

    private Object[] disposeWorkflowState( Map requestParams, Integer infoType, Long currentKey,
        GuestbookConfigBean cfgBean, boolean editMode, List wfActionList, Integer censorState )
    {
        // 工作流处理

        Long actionId = Long.valueOf( StringUtil.getLongValue( ( String ) requestParams
            .get( "actionId" ), -1 ) );

        Integer endState = null;

        String flowTarget = "";

        if( cfgBean.getWorkflowId().longValue() > 0 )
        {
            // 进入工作流流程

            Object[] wi = WorkflowService.getInstance().disposeContentWorkflowStatus( infoType,
                cfgBean.getConfigId(), cfgBean.getWorkflowId(), currentKey, actionId,
                ( String ) requestParams.get( "flowTarget" ),
                Long.valueOf( ( String ) requestParams.get( "fromStepId" ) ),
                Long.valueOf( ( String ) requestParams.get( "toStepId" ) ), null, null, editMode,
                wfActionList, censorState );

            flowTarget = ( String ) wi[1];

            endState = ( Integer ) wi[0];

        }
        else
        {
            // 默认发布状态
            // 删除可能存在遗留的op
            workFlowDao.deleteWorkflowOperationByContentId( currentKey, infoType );

            endState = Constant.WORKFLOW.CENSOR_STATUS_SUCCESS;

            gbDao.updateGuestbookCensorStatus( currentKey, endState );
        }

        // 将变发布状态,删除所有操作记录
        if( Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( endState ) )
        {
            workFlowDao.deleteWorkflowOperInfoByContentId( currentKey, infoType );
        }

        return new Object[] { endState, flowTarget };
    }

    @SuppressWarnings( "unchecked" )
    private void sendWorkflowMsg( List execWorkflowActionList, GuestbookMainInfoBean mainInfo,
        String suggest, String editSuggest, String nextStepMan, String prevStepMan, String cMode )
    {
        suggest = ( suggest == null ) ? "" : suggest;

        editSuggest = ( editSuggest == null ) ? "" : editSuggest;

        if( execWorkflowActionList.isEmpty() )
        {
            return;
        }

        String actSucc = ( ( String[] ) execWorkflowActionList.get( 0 ) )[1];

        WorkflowStepActionBean actBean = WorkflowService.getInstance()
            .retrieveSingleWorkflowStepActionBean(
                StringUtil.getLongValue( ( ( String[] ) execWorkflowActionList.get( 0 ) )[0], -1 ) );

        String title = null;

        String info = null;

        List revUserList = null;

        String formStart = ( ( String[] ) execWorkflowActionList.get( 0 ) )[0];

        if( formStart != null && formStart.startsWith( "start:" ) )
        {
            String[] si = formStart.split( ":" );

            title = "留言工作流 ID : " + mainInfo.getGbId() + ", 进入 [开始] " + "审核";

            info = "标题为 \"" + mainInfo.getGbTitle() + "\" 的留言需要您参与审核。(请勿回复)"  
                + "\n\n修改建议： " + editSuggest;

            revUserList = new ArrayList( WorkflowService.getInstance().retrieveActorIdForStepOrg(
                StringUtil.getLongValue( si[1], -1 ), StringUtil.getLongValue( si[2], -1 ) ) );

            messageService.sendManagerMessage( Long.valueOf( -9999 ), "系统事件消息", title, info,
                revUserList );
        }
        else if( actBean != null )
        {
            WorkflowStepInfoBean stepBean = WorkflowService.getInstance()
                .retrieveWorkflowStepBeanByFlowIdAndStep( actBean.getToStepId() );

            String stepOrActName = null;

            boolean allMode = false;

            if( Constant.COMMON.ON.equals( actBean.getConjunctManFlag() ) )
            {
                if( "1".equals( actSucc ) )
                {
                    stepOrActName = ( ( stepBean != null ) ? stepBean.getStepNodeName() : "" );

                    info = "标题为 \"" + mainInfo.getGbTitle() + "\" 的留言需要您参与审核,请处理。(请勿回复)"
                          + "\n\n修改建议： " + editSuggest;

                    revUserList = new ArrayList( WorkflowService.getInstance()
                        .retrieveActorIdForStepOrg( actBean.getFlowId(), actBean.getToStepId() ) );
                }
                else
                {
                    allMode = true;

                    stepOrActName = actBean.getPassActionName();

                    info = "标题为 \"" + mainInfo.getGbTitle() + "\" 的留言需要您参与会签。(请勿回复)"
                         + "\n\n修改建议： " + editSuggest;

                    revUserList = new ArrayList( WorkflowService.getInstance()
                        .retrieveActorIdForStepOrg( actBean.getFlowId(), actBean.getFromStepId() ) );
                }

            }
            else
            {

                stepOrActName = ( ( stepBean != null ) ? stepBean.getStepNodeName() : "" );

                info = "标题为 \"" + mainInfo.getGbTitle() + "\" 的留言需要您参与审核。(请勿回复)"
                    + "\n\n修改建议： " + editSuggest;

                revUserList = new ArrayList( WorkflowService.getInstance()
                    .retrieveActorIdForStepOrg( actBean.getFlowId(), actBean.getToStepId() ) );
            }

            // 发送系统消息

            // 2015-3:退稿仍然需要发送消息
            if( Constant.WORKFLOW.DRAFT_ACTION_ID_VALUE.equals( actBean.getToStepId() ) )
            {
                stepOrActName = "退稿";

                info = "标题为 \"" + mainInfo.getGbTitle() + "\" 的留言被退稿。(请勿回复)"  
                    + "\n\n修改建议： " + editSuggest;

                revUserList = new ArrayList( 1 );

                // revUserList.add(
                // SecurityService.getInstance().retrieveSingleSystemUserBean(
                // mainInfo.getCreator(), null ) );

                title = "您的留言 ID :" + mainInfo.getGbId() + ", 已进入 [" + stepOrActName + "] 状态";

                messageService.sendManagerMessage( Long.valueOf( -9999 ), "系统事件消息", title, info,
                    revUserList );

            }
            else
            {
                if( StringUtil.isStringNull( stepOrActName ) )
                {
                    title = "留言工作流 ID : " + mainInfo.getGbId() + " 已流转到终点";

                }
                else
                {
                    title = "留言工作流 ID : " + mainInfo.getGbId() + ", 进入 [" + stepOrActName + "] "
                        + ( allMode ? "会签" : "审核" );
                }

                messageService.sendManagerMessage( Long.valueOf( -9999 ), "系统事件消息", title, info,
                    revUserList );
            }

            // 发送回溯站内信,任何参与过审核的人必须受到站内信,注意!必须在记录操作日志之前发送站内信,保证不重复收信

            if( !"【该留言无改动】".equals( editSuggest ) )
            {
                // 默认发全部
                if( StringUtil.isStringNull( prevStepMan ) )
                {
                        // ContentService.getInstance()
                        //  .backActManSendMsg( mainInfo.getGbId(), title, info );
                }
                else
                {
                    List<String> pids = StringUtil.changeStringToList( prevStepMan, "," );

                    info = StringUtil.replaceString( info, "需要您参与审核", "" );

                    messageService.sendManagerMessage( Long.valueOf( -9999 ), "系统事件消息", "[ 提示消息 ] "
                        + title, " (此为其他管理员留言修改提示, 无需您参与操作)\n\n" + info, pids );
                }

            }

            // 指定下一步骤审批人
            if( StringUtil.isStringNotNull( nextStepMan ) )
            {
                Long userId = StringUtil
                    .getLongValue( nextStepMan.replaceAll( ",", "" ).trim(), -1 );

                ContentService.getInstance().changeWKOPerCurrentCensorMan( mainInfo.getGbId(),
                    userId, 2 );
            }

            // 记录每一步操作日志

            String fm =   "\n\n修改建议： " + editSuggest;

            ContentService.getInstance().addContentOperInfo( mainInfo.getGbId(),
                ( String ) SecuritySessionKeeper.getSecuritySession().getAuth().getApellation(),
                actBean.getPassActionName(), fm, cMode, 2 );

        }
    }

    public void editGuestbookStatus( List idList, String action, Integer flag )
    {
        try
        {
            mysqlEngine.beginTransaction();

            long id = -1;
            for ( int i = 0; i < idList.size(); i++ )
            {
                id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );

                if( id > 0 )
                {
                    if( "censor".equals( action ) )
                    {
                        gbDao.updateGuestbookCensorStatus( Long.valueOf( id ), flag );
                    }
                    else if( "open".equals( action ) )
                    {
                        gbDao.updateGuestbookOpenStatus( Long.valueOf( id ), flag );
                    }
                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            clear();
        }
    }

    /**
     * 删除给定ID的留言信息
     * 
     * @param idList
     */
    public void deleteGuestbookAllInfoByIds( List idList, GuestbookConfigBean cfgBean,
        SiteGroupBean site )
    {
        if( cfgBean == null || idList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            deleteGuestbookInfoByIdsNoTran( idList, cfgBean, site );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clear();
        }
    }

    /**
     * 根据configId删除留言配置和其所属留言信息
     * 
     * @param idList
     */
    public void deleteGuestbookConfigAllInfoByIds( List idList, SiteGroupBean site )
    {
        try
        {
            mysqlEngine.beginTransaction();

            long configId = -1;

            GuestbookConfigBean cfgBean = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                if( idList.get( i ) instanceof String )
                {
                    configId = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );
                }
                else
                {
                    configId = ( ( Long ) idList.get( i ) ).longValue();
                }

                cfgBean = gbDao.querySingleGuestbookConfigBeanByConfigId( Long.valueOf( configId ) );

                if( configId > 0 )
                {
                    // 删除留言所有信息
                    deleteGuestbookInfoByIdsNoTran( gbDao.queryAllGuestbookIdList( Long
                        .valueOf( configId ) ), cfgBean, site );

                    // 删除配置信息
                    gbDao.deleteGuestbookConfigById( Long.valueOf( configId ) );

                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clear();

            clearCfg();
        }
    }

    public void sendNeedReplyMessage( Long senderId, Long userId, Long gbId )
    {
        // 发送系统消息

        List uidList = new ArrayList();

        uidList.add( userId );

        GuestbookMainInfoBean gb = gbDao.querySingleGuestbookMainInfoByGbid( gbId );

        if( gb != null )
        {
            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            String info = "来自站点 '" + site.getSiteName() + "' 标题为 '" + gb.getGbTitle()
                + "' 的留言需要您来处理。";

            messageService.sendManagerMessage( senderId, "指定回复留言事件消息", site.getSiteName()
                + "的留言回复请求 留言ID : " + gb.getGbId(), info, uidList );
        }

    }

    private void deleteGuestbookInfoByIdsNoTran( List idList, GuestbookConfigBean cfgBean,
        SiteGroupBean site )
    {
        long id = -1;

        for ( int i = 0; i < idList.size(); i++ )
        {
            if( idList.get( i ) instanceof String )
            {
                id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );
            }
            else
            {
                id = ( ( Long ) idList.get( i ) ).longValue();
            }

            if( id > 0 )
            {
                metaDataService.deleteAndClearDefModelInfo( id, cfgBean.getInfoModelId(), site
                    .getSiteFlag() );

                gbDao.deleteGuestbookInfoById( Long.valueOf( id ) );

            }
        }
    }

    /**
     * 根据当前用户获取对应的审核留言
     * 
     * @param sysAuth
     * @return
     */
    public List retrieveAllInWorkflowUserGuestbookContent( Auth auth, Long start, Integer size )
    {
        if( auth == null || !auth.isAuthenticated() )
        {
            return null;
        }

        String sqlOrCond = StringUtil.replaceString( auth.getRoleSqlHelper().getAllRoleOrQuery( "",
            "auditManId" ), "auditManId", "wa.auditManId" );

        List result = null;

        try
        {
            mysqlEngine.beginTransaction();

            result = gbDao.queryAllInWorkflowGuestbookContent( sqlOrCond, ( Long ) auth
                .getIdentity(), ( Long ) auth.getOrgIdentity(), start, size );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        return result;
    }

    public static void clear()
    {
        countCache.clearAllEntry();
        gbCache.clearAllEntry();

    }

    public static void clearCfg()
    {
        gbCfgCache.clearAllEntry();
    }

}
