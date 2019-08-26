package cn.com.mjsoft.cms.workflow.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.dao.ChannelDao;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.dao.ContentDao;
import cn.com.mjsoft.cms.content.dao.vo.ContentMainInfo;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.guestbook.dao.GuestbookDao;
import cn.com.mjsoft.cms.message.service.MessageService;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.workflow.bean.WorkflowBean;
import cn.com.mjsoft.cms.workflow.bean.WorkflowOperationBean;
import cn.com.mjsoft.cms.workflow.bean.WorkflowStepActionBean;
import cn.com.mjsoft.cms.workflow.bean.WorkflowStepInfoBean;
import cn.com.mjsoft.cms.workflow.dao.WorkFlowDao;
import cn.com.mjsoft.cms.workflow.dao.vo.Workflow;
import cn.com.mjsoft.cms.workflow.dao.vo.WorkflowActor;
import cn.com.mjsoft.cms.workflow.dao.vo.WorkflowOperation;
import cn.com.mjsoft.cms.workflow.dao.vo.WorkflowStepAction;
import cn.com.mjsoft.cms.workflow.dao.vo.WorkflowStepInfo;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
public class WorkflowService
{
    private static Logger log = Logger.getLogger( WorkflowService.class );

    private static Map cacheManager = new HashMap();

    static
    {
        cacheManager.put( "retrieveWorkflowActorBeanList", new ClusterCacheAdapter( 100,
            "workflowService.retrieveWorkflowActorBeanList" ) );
        cacheManager.put( "retrieveWorkflowSingleBean", new ClusterCacheAdapter( 100,
            "workflowService.retrieveWorkflowSingleBean" ) );
    }

    private static WorkflowService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private WorkFlowDao workflowDao = null;

    private ChannelDao channelDao = null;

    private ContentDao contentDao = null;

    private GuestbookDao gbDao = null;

    private WorkflowService()
    {
        workflowDao = new WorkFlowDao( mysqlEngine );
        channelDao = new ChannelDao( mysqlEngine );
        contentDao = new ContentDao( mysqlEngine );

        gbDao = new GuestbookDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new WorkflowService();
        }
    }

    public static WorkflowService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public void createWorkflowStepInfo( WorkflowStepInfo stepInfo )
    {
        workflowDao.saveWorkflowStepInfo( stepInfo );
    }

    /**
     * 新增加一个工作流,以及其相关信息.
     * 
     * @param workflow
     * @param workflowActorList
     */
    public Long addWorkflowInfo( Workflow workflow )
    {
        Long newId = -1l;

        if( workflow == null )
        {
            return newId;
        }

        try
        {
            mysqlEngine.beginTransaction();

            workflow.setSystemHandleTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

            workflow.setUpdateDT( DateAndTimeUtil.clusterTimeMillis() );

            workflow.setStep( Integer.valueOf( 1 ) );

            UpdateState us = workflowDao.saveWorkflowMainInfo( workflow );

            if( us.haveKey() )
            {

                // 增加默认的开始步骤
                WorkflowStepInfo defStartStepInfo = new WorkflowStepInfo();

                defStartStepInfo.setFlowId( Long.valueOf( us.getKey() ) );
                defStartStepInfo.setIsStart( Constant.COMMON.ON );
                defStartStepInfo.setStepNodeName( "开始" );
                defStartStepInfo.setStepDesc( "系统默认开始步骤,不可删除。" );

                workflowDao.saveWorkflowStepInfo( defStartStepInfo );

                newId = us.getKey();
            }

            //
            // if( us.getRow() > 0 )
            // {
            // WorkflowActor workflowActor = null;
            // WorkflowStepInfo workflowStepInfo = null;
            //
            // mysqlEngine.startBatch();
            //
            // // 工作流参与者
            // for ( int i = 0; i < workflowActorList.size(); i++ )
            // {
            // workflowActor = ( WorkflowActor ) workflowActorList.get( i );
            // workflowActor.setFlowId( Long.valueOf( us.getKey() ) );
            // workFlowDao.saveWorkflowActor( workflowActor );
            // }
            //
            // // 工作流步骤信息
            // for ( int i = 0; i < workflowStepList.size(); i++ )
            // {
            // workflowStepInfo = ( WorkflowStepInfo ) workflowStepList
            // .get( i );
            // workflowStepInfo.setFlowId( Long.valueOf( us.getKey() ) );
            // workFlowDao.saveWorkflowStepInfo( workflowStepInfo );
            // }

            // mysqlEngine.executeBatch();

            // }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            resetWorkflowActorBeanListCache();
        }

        return newId;
    }

    public void editWorkflowInfo( Workflow workflow )
    {
        if( workflow == null )
        {
            return;
        }

        try
        {
            workflowDao.updateWorkflowMainInfo( workflow );
        }
        finally
        {
            mysqlEngine.endTransaction();
            resetWorkflowActorBeanListCache();
        }
    }

    public List retrieveAllWorkflowBeanBySiteId( Long siteId )
    {
        List result = workflowDao.queryAllWorkflowBeanBySiteId( siteId );
        return result;
    }

    /**
     * 获取指定工作流的所有步骤基本信息
     * 
     * @param flowId
     * @return
     */
    public List retrieveWorkflowStepBeanListByFlowId( Long flowId )
    {
        List result = workflowDao.queryWorkflowStepBeanListByFlowId( flowId );

        return result;
    }

    /**
     * 获取指定工作流的指定步骤基本信息
     * 
     * @param flowId
     * @return
     */
    public WorkflowStepInfoBean retrieveWorkflowStepBeanByFlowIdAndStep( Long step )
    {
        WorkflowStepInfoBean result = workflowDao.querySingleWorkflowStepBeanByFlowIdAndStep( step );

        return result;
    }

    // /**
    // * 获取工作流参与审核人,按照审核顺序排列的列表.
    // *
    // * @param flowId
    // * @return
    // */
    // // TODO
    // public List retrieveWorkflowActorBeanListTODO( Long flowId )
    // {
    // Cache cache = ( Cache ) cacheManager.get( "retrieveWorkflowActorBeanList"
    // );
    // String key = "retrieveWorkflowActorBeanList:" + flowId;
    //
    // List result = ( List ) cache.getEntry( key );
    //
    // // if( result == null )
    // // {
    // // result = workFlowDao.queryWorkflowActorBeanList( flowId );
    // // cache.putEntry( key, result );
    // // }
    //
    // return result;
    // }

    /**
     * 获取工作流主信息.
     * 
     * @param flowId
     * @return
     */
    public WorkflowBean retrieveSingleWorkflowBean( Long flowId )
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveWorkflowSingleBean" );
        String key = "retrieveWorkflowSingleBean:" + flowId;

        WorkflowBean result = ( WorkflowBean ) cache.getEntry( key );

        if( result == null )
        {
            result = workflowDao.querySingleWorkflowBean( flowId );
            cache.putEntry( key, result );
        }

        return result;
    }

    public WorkflowOperationBean retrieveSingleWorkflowOperationBean( Long contentId,
        Integer infoType )
    {
        return workflowDao.querySingleWorkflowOperationBean( contentId, infoType );
    }

    public WorkflowOperationBean getWorkflowOperationBean( String contentId, String infoType )
    {
        return retrieveSingleWorkflowOperationBean( StringUtil.getLongValue( contentId, -1 ),
            StringUtil.getIntValue( infoType, -1 ) );
    }

    /**
     * 根据栏目挂靠的工作流,确定当前内容的发布状态.
     * 
     * @param contentClass
     * @param contentId
     * @param appearStartDateTS
     * @param appearEndDateTS
     * @return
     */
    @SuppressWarnings( "unchecked" )
    public Object[] disposeContentWorkflowStatus( Integer infoType, Long classId, Long wkId,
        Long contentId, Long actionId, String flowTarget, Long fromStepId, Long toStepId,
        Timestamp appearStartDateTS, Timestamp appearEndDateTS, boolean update, List wfActionList,
        Integer censorState )
    {

        WorkflowBean workflowBean = retrieveSingleWorkflowBean( wkId );

        WorkflowOperationBean opBean = workflowDao.querySingleWorkflowOperationBean( contentId,
            infoType );

        String managerName = ( String ) SecuritySessionKeeper.getSecuritySession().getAuth()
            .getApellation();

        // 默认为发布状态
        Integer censorStatus = Constant.WORKFLOW.CENSOR_STATUS_SUCCESS;

        Date currentDate = new Date( DateAndTimeUtil.clusterTimeMillis() );

        if( workflowBean != null )
        {

            // 有工作流但是暂无审核步骤
            if( workflowBean.getStep().intValue() == 0 )
            {
                // 若当前时间在下线时间之后,则为过期状态
                if( currentDate.compareTo( appearEndDateTS ) > 0 )
                {
                    censorStatus = Constant.WORKFLOW.CENSOR_STATUS_WITHDRAW;
                }
                else if( currentDate.compareTo( appearStartDateTS ) < 0 )
                {
                    // 若当前时间在发布时间之前,还没到发布时间,则为等待发布状态
                    censorStatus = Constant.WORKFLOW.CENSOR_STATUS_WAIT_PUBLISH;
                }
            }
            else
            {
                // 增加模式下,先要生成op对象
                if( !update )
                {
                    WorkflowOperation wfOperBean = new WorkflowOperation();

                    wfOperBean.setContentId( contentId );
                    wfOperBean.setClassId( classId );
                    wfOperBean.setCurrentStep( toStepId );
                    wfOperBean.setFlowId( workflowBean.getFlowId() );
                    wfOperBean.setOperStatus( Integer.valueOf( Constant.WORKFLOW.OPER_NOT_READ ) );
                    wfOperBean.setPossessStatus( Integer
                        .valueOf( Constant.WORKFLOW.OPER_NOT_POSSESS ) );
                    wfOperBean.setWkUpdateDT( workflowBean.getUpdateDT() );

                    wfOperBean.setInfoType( infoType );
                    wfOperBean.setFlowTarget( flowTarget );
                    wfOperBean.setStartCensor( censorState );

                    workflowDao.saveWorkflowOperation( wfOperBean );

                    opBean = workflowDao.querySingleWorkflowOperationBean( contentId, infoType );

                    Map infoMap = new HashMap();

                    infoMap.put( "infoType", infoType );

                    infoMap.put( "contentId", contentId );
                    infoMap.put( "pUserName", managerName );
                    infoMap.put( "fromStepId", fromStepId );
                    infoMap.put( "actionId", actionId );
                    infoMap.put( "toStepId", toStepId );
                    infoMap.put( "eventDT", new Date( DateAndTimeUtil.clusterTimeMillis() ) );

                    workflowDao.saveWorkflowOperInfo( infoMap );
                }

                // 返回执行的actionID

                String[] actExecInfo = new String[2];

                // 在update模式工作流中只有存在op对象的动作请求才是合法的

                if( update
                    && !Constant.WORKFLOW.START_ACTION_ID_VALUE.equals( toStepId )
                    && workflowDao.queryWorkflowOperationCountByContentId( contentId, infoType )
                        .intValue() < 1 )
                {
                    actExecInfo[0] = "-1";

                    actExecInfo[1] = Constant.COMMON.OFF.toString();

                    // 更新实际当前内容审核状态
                    if( Constant.WORKFLOW.INFO_TYPE_CONTENT.equals( infoType ) )
                    {
                        contentDao.updateContentMainInfoCensorStatus( contentId, censorState );
                    }
                    else if( Constant.WORKFLOW.INFO_TYPE_GB.equals( infoType ) )
                    {
                        gbDao.updateGuestbookCensorStatus( contentId, censorState );

                        gbDao.updateGuestbookReplyStatus( contentId, censorState );
                    }

                }
                else
                {
                    WorkflowStepActionBean actionBean = workflowDao
                        .queryWorkflowStepActionBeanByFlowIdAndStep( workflowBean.getFlowId(),
                            fromStepId, toStepId );

                    actExecInfo[0] = ( actionBean != null ) ? actionBean.getActionId().toString()
                        : "";

                    // 有审核步骤存在,立即进入审核模式
                    // TODO 需要检查是否当前角色有资格进入当前步骤

                    if( Constant.WORKFLOW.END_ACTION_ID_VALUE.equals( toStepId ) )
                    {
                        // 进入结束节点，删除工作流处理信息
                        if( Constant.COMMON.ON.equals( actionBean.getConjunctManFlag() ) )
                        {
                            // 当前动作为会签模式,只有所有机构 角色 人员都通过,才更新

                            if( disposeConjunctAction( infoType, workflowBean, actionBean,
                                contentId, fromStepId ) )
                            {
                                censorStatus = pendingCensorStateByStartAndEndPublishDate(
                                    appearStartDateTS, appearEndDateTS,
                                    Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );

                                actExecInfo[1] = Constant.COMMON.ON.toString();

                                workflowDao
                                    .deleteWorkflowOperationByContentId( contentId, infoType );

                                // 删除trace
                                workflowDao.deleteActorCensorTraceInfo( contentId, actionBean
                                    .getActionId(), infoType );

                                // 删除msg
                                workflowDao.deleteWorkflowMsg( contentId );

                                Map infoMap = new HashMap();

                                infoMap.put( "infoType", infoType );

                                infoMap.put( "contentId", contentId );
                                infoMap.put( "pUserName", managerName );
                                infoMap.put( "fromStepId", fromStepId );
                                infoMap.put( "actionId", actionId );
                                infoMap.put( "toStepId", toStepId );
                                infoMap.put( "eventDT", new Date( DateAndTimeUtil
                                    .clusterTimeMillis() ) );

                                workflowDao.saveWorkflowOperInfo( infoMap );
                            }
                            else
                            {
                                censorStatus = censorState;

                                actExecInfo[1] = Constant.COMMON.OFF.toString();

                                workflowDao.updateWorkflowOperationCurrentAuditUser( contentId,
                                    infoType, null );

                                censorStatus = Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW;

                            }
                        }
                        else
                        {
                            if( "delete".equals( opBean.getFlowTarget() ) )
                            {
                                if( infoType == 1 )
                                {
                                    // 工作流状态保持原样不改动
                                    censorStatus = opBean.getStartCensor();
                                }
                            }
                            else if( "offline".equals( opBean.getFlowTarget() ) )
                            {
                                // 工作流状态保持原样不改动
                                censorStatus = opBean.getStartCensor();
                            }
                            else
                            {
                                censorStatus = pendingCensorStateByStartAndEndPublishDate(
                                    appearStartDateTS, appearEndDateTS,
                                    Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );
                            }

                            actExecInfo[1] = Constant.COMMON.ON.toString();

                            workflowDao.deleteWorkflowOperationByContentId( contentId, infoType );

                            // 删除msg
                            workflowDao.deleteWorkflowMsg( contentId );
                        }
                    }
                    else if( Constant.WORKFLOW.REJECT_ACTION_ID_VALUE.equals( toStepId ) )
                    {
                        // 进入结束节点，删除工作流处理信息
                        if( Constant.COMMON.ON.equals( actionBean.getConjunctManFlag() ) )
                        {
                            // 当前动作为会签模式,只有所有机构 角色 人员都通过,才更新

                            if( disposeConjunctAction( infoType, workflowBean, actionBean,
                                contentId, fromStepId ) )
                            {
                                censorStatus = pendingCensorStateByStartAndEndPublishDate(
                                    appearStartDateTS, appearEndDateTS,
                                    Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );

                                actExecInfo[1] = Constant.COMMON.ON.toString();

                                workflowDao
                                    .deleteWorkflowOperationByContentId( contentId, infoType );

                                // 删除trace
                                workflowDao.deleteActorCensorTraceInfo( contentId, actionBean
                                    .getActionId(), infoType );

                                // 删除msg
                                workflowDao.deleteWorkflowMsg( contentId );

                                Map infoMap = new HashMap();

                                infoMap.put( "infoType", infoType );

                                infoMap.put( "contentId", contentId );
                                infoMap.put( "pUserName", managerName );
                                infoMap.put( "fromStepId", fromStepId );
                                infoMap.put( "actionId", actionId );
                                infoMap.put( "toStepId", toStepId );
                                infoMap.put( "eventDT", new Date( DateAndTimeUtil
                                    .clusterTimeMillis() ) );

                                workflowDao.saveWorkflowOperInfo( infoMap );
                            }
                            else
                            {
                                actExecInfo[1] = Constant.COMMON.OFF.toString();

                                workflowDao.updateWorkflowOperationCurrentAuditUser( contentId,
                                    infoType, null );

                                censorStatus = Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW;

                            }

                        }
                        else
                        {
                            // censorStatus =
                            // pendingCensorStateByStartAndEndPublishDate(
                            // appearStartDateTS, appearEndDateTS,
                            // Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );

                            actExecInfo[1] = Constant.COMMON.ON.toString();

                            workflowDao.deleteWorkflowOperationByContentId( contentId, infoType );

                            // 删除msg
                            workflowDao.deleteWorkflowMsg( contentId );
                        }
                        // 拒绝模式工作流状态保持原样不改动
                        censorStatus = opBean.getStartCensor();
                    }
                    else if( Constant.WORKFLOW.DRAFT_ACTION_ID_VALUE.equals( toStepId ) )
                    {
                        // 进入退稿节点，删除工作流处理信息
                        if( Constant.COMMON.ON.equals( actionBean.getConjunctManFlag() ) )
                        {
                            // 当前动作为会签模式,只有所有机构 角色 人员都通过,才更新

                            if( disposeConjunctAction( infoType, workflowBean, actionBean,
                                contentId, fromStepId ) )
                            {
                                censorStatus = Constant.WORKFLOW.CENSOR_STATUS_DRAFT;

                                actExecInfo[1] = Constant.COMMON.ON.toString();

                                workflowDao
                                    .deleteWorkflowOperationByContentId( contentId, infoType );

                                // 删除trace
                                workflowDao.deleteActorCensorTraceInfo( contentId, actionBean
                                    .getActionId(), infoType );

                                // 删除msg
                                workflowDao.deleteWorkflowMsg( contentId );

                                Map infoMap = new HashMap();

                                infoMap.put( "infoType", infoType );

                                infoMap.put( "contentId", contentId );
                                infoMap.put( "pUserName", managerName );
                                infoMap.put( "fromStepId", fromStepId );
                                infoMap.put( "actionId", actionId );
                                infoMap.put( "toStepId", toStepId );
                                infoMap.put( "eventDT", new Date( DateAndTimeUtil
                                    .clusterTimeMillis() ) );

                                workflowDao.saveWorkflowOperInfo( infoMap );
                            }
                            else
                            {
                                // 稿件模式工作流状态保持原样不改动
                                censorStatus = opBean.getStartCensor();

                                actExecInfo[1] = Constant.COMMON.OFF.toString();

                                workflowDao.updateWorkflowOperationCurrentAuditUser( contentId,
                                    infoType, null );

                            }
                        }
                        else
                        {
                            censorStatus = Constant.WORKFLOW.CENSOR_STATUS_DRAFT;

                            actExecInfo[1] = Constant.COMMON.ON.toString();

                            workflowDao.deleteWorkflowOperationByContentId( contentId, infoType );

                            // 删除msg
                            workflowDao.deleteWorkflowMsg( contentId );
                        }
                    }
                    else if( Constant.WORKFLOW.START_ACTION_ID_VALUE.equals( toStepId ) )
                    {
                        // 进入工作流开始状态,需要分别对待是否是发布态进入工作流

                        if( Constant.WORKFLOW.RE_EDIT_ACTION_ID_VALUE.equals( fromStepId ) )
                        {
                            // 正式确认进入工作流,并处于默认开始模式,此步无需人员参与,需要生成新的工作流op

                            WorkflowOperation wfOperBean = new WorkflowOperation();

                            wfOperBean.setInfoType( infoType );
                            wfOperBean.setFlowTarget( flowTarget );
                            wfOperBean.setStartCensor( censorState );

                            wfOperBean.setContentId( contentId );
                            wfOperBean.setClassId( classId );

                            Long stepId = workflowDao.queryStartStepTrueIdByFlowId( workflowBean
                                .getFlowId() );

                            wfOperBean.setCurrentStep( workflowDao
                                .queryStartStepTrueIdByFlowId( workflowBean.getFlowId() ) ); // 开始默认动作需要获取实际的步骤id，以获取参与者
                            wfOperBean.setFlowId( workflowBean.getFlowId() );
                            wfOperBean.setOperStatus( Integer
                                .valueOf( Constant.WORKFLOW.OPER_NOT_READ ) );
                            wfOperBean.setPossessStatus( Integer
                                .valueOf( Constant.WORKFLOW.OPER_NOT_POSSESS ) );
                            wfOperBean.setWkUpdateDT( workflowBean.getUpdateDT() );

                            workflowDao.saveWorkflowOperation( wfOperBean );

                            opBean = workflowDao.querySingleWorkflowOperationBean( contentId,
                                infoType );

                            Map infoMap = new HashMap();

                            infoMap.put( "infoType", infoType );

                            infoMap.put( "contentId", contentId );
                            infoMap.put( "eActionName", fromStepId );

                            infoMap.put( "pUserName", managerName );
                            infoMap.put( "fromStepId", fromStepId );
                            infoMap.put( "actionId", actionId );
                            infoMap.put( "toStepId", toStepId );
                            infoMap
                                .put( "eventDT", new Date( DateAndTimeUtil.clusterTimeMillis() ) );

                            workflowDao.saveWorkflowOperInfo( infoMap );

                            actExecInfo[0] = "start:" + workflowBean.getFlowId() + ":" + stepId;// 默认开始节点标识

                            actExecInfo[1] = Constant.COMMON.ON.toString();

                            censorStatus = Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW;
                        }
                        else if( Constant.COMMON.ON.equals( actionBean.getConjunctManFlag() ) )
                        {
                            // 预发布模式特殊,当fromStepId不是定义步骤时,说明是非定义动作
                            // 当前动作为会签模式,只有所有机构 角色 人员都通过,才更新

                            if( disposeConjunctAction( infoType, workflowBean, actionBean,
                                contentId, fromStepId ) )
                            {
                                // censorStatus =
                                // Constant.WORKFLOW.CENSOR_STATUS_IN_EDIT;

                                // workFlowDao
                                // .deleteWorkflowOperationByContentId(
                                // contentId );

                                // 删除trace
                                workflowDao.deleteActorCensorTraceInfo( contentId, actionBean
                                    .getActionId(), infoType );

                                Map infoMap = new HashMap();

                                infoMap.put( "infoType", infoType );

                                infoMap.put( "contentId", contentId );
                                infoMap.put( "pUserName", managerName );
                                infoMap.put( "fromStepId", fromStepId );
                                infoMap.put( "actionId", actionId );
                                infoMap.put( "toStepId", toStepId );
                                infoMap.put( "eventDT", new Date( DateAndTimeUtil
                                    .clusterTimeMillis() ) );

                                workflowDao.saveWorkflowOperInfo( infoMap );
                            }
                            else
                            {
                                censorStatus = censorState;
                            }

                            workflowDao.updateWorkflowOperationCurrentAuditUser( contentId,
                                infoType, null );
                        }

                    }
                    else if( Constant.WORKFLOW.START_ACTION_ID_VALUE.equals( fromStepId ) )
                    {
                        /**
                         * 注意:以下流程已经不再使用
                         */
                        // 开始一个新的工作流op
                        /*
                         * if( Constant.COMMON.ON.equals( actionBean
                         * .getConjunctManFlag() ) ) { // 当前动作为会签模式,只有所有机构 角色
                         * 人员都通过,才更新 // 开始默认动作需要获取实际的步骤id，以获取参与者 fromStepId =
                         * workFlowDao .queryStartStepTrueIdByFlowId(
                         * workflowBean .getFlowId() );
                         * 
                         * if( disposeConjunctAction( workflowBean, actionBean,
                         * contentId, fromStepId ) ) { WorkflowOperation
                         * wfOperBean = new WorkflowOperation();
                         * wfOperBean.setContentId( contentId );
                         * wfOperBean.setClassId( contentClass .getClassId() );
                         * wfOperBean.setCurrentStep( toStepId );
                         * wfOperBean.setFlowId( workflowBean.getFlowId() );
                         * wfOperBean .setOperStatus( Integer .valueOf(
                         * Constant.WORKFLOW.OPER_NOT_READ ) ); wfOperBean
                         * .setPossessStatus( Integer .valueOf(
                         * Constant.WORKFLOW.OPER_NOT_POSSESS ) );
                         * 
                         * workFlowDao.saveWorkflowOperation( wfOperBean );
                         * 
                         * censorStatus =
                         * Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW; // 删除trace
                         * workFlowDao.deleteActorCensorTraceInfo( contentId,
                         * actionBean.getActionId() ); } } else {
                         * 
                         * WorkflowOperation wfOperBean = new
                         * WorkflowOperation(); wfOperBean.setContentId(
                         * contentId ); wfOperBean.setClassId(
                         * contentClass.getClassId() );
                         * wfOperBean.setCurrentStep( toStepId );
                         * wfOperBean.setFlowId( workflowBean.getFlowId() );
                         * wfOperBean.setOperStatus( Integer .valueOf(
                         * Constant.WORKFLOW.OPER_NOT_READ ) );
                         * wfOperBean.setPossessStatus( Integer .valueOf(
                         * Constant.WORKFLOW.OPER_NOT_POSSESS ) );
                         * 
                         * workFlowDao.saveWorkflowOperation( wfOperBean );
                         * 
                         * censorStatus =
                         * Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW; }
                         */

                    }
                    else
                    {
                        // 进入其他工作流中间步骤

                        if( update )
                        {
                            if( Constant.COMMON.ON.equals( actionBean.getConjunctManFlag() ) )
                            {
                                // 当前动作为会签模式,只有所有机构 角色 人员都通过,才更新

                                if( disposeConjunctAction( infoType, workflowBean, actionBean,
                                    contentId, fromStepId ) )
                                {
                                    workflowDao.updateWorkflowOperationPossessInfo( contentId,
                                        infoType, null, null, Integer
                                            .valueOf( Constant.WORKFLOW.OPER_NOT_POSSESS ), Integer
                                            .valueOf( Constant.WORKFLOW.OPER_NOT_READ ), toStepId );

                                    actExecInfo[1] = Constant.COMMON.ON.toString();

                                    // 删除trace
                                    workflowDao.deleteActorCensorTraceInfo( contentId, actionBean
                                        .getActionId(), infoType );

                                    Map infoMap = new HashMap();

                                    infoMap.put( "infoType", infoType );

                                    infoMap.put( "contentId", contentId );
                                    infoMap.put( "pUserName", managerName );
                                    infoMap.put( "fromStepId", fromStepId );
                                    infoMap.put( "actionId", actionId );
                                    infoMap.put( "toStepId", toStepId );
                                    infoMap.put( "eventDT", new Date( DateAndTimeUtil
                                        .clusterTimeMillis() ) );

                                    workflowDao.saveWorkflowOperInfo( infoMap );
                                }
                                else
                                {
                                    censorStatus = censorState;

                                    actExecInfo[1] = Constant.COMMON.OFF.toString();

                                    Map infoMap = new HashMap();

                                    infoMap.put( "infoType", infoType );

                                    infoMap.put( "contentId", contentId );
                                    infoMap.put( "pUserName", managerName );
                                    infoMap.put( "fromStepId", fromStepId );
                                    infoMap.put( "actionId", actionId );
                                    infoMap.put( "toStepId", toStepId );
                                    infoMap.put( "eventDT", new Date( DateAndTimeUtil
                                        .clusterTimeMillis() ) );

                                    workflowDao.saveWorkflowOperInfo( infoMap );
                                }
                            }
                            else
                            {
                                actExecInfo[1] = Constant.COMMON.ON.toString();

                                workflowDao.updateWorkflowOperationPossessInfo( contentId,
                                    infoType, null, null, Integer
                                        .valueOf( Constant.WORKFLOW.OPER_NOT_POSSESS ), Integer
                                        .valueOf( Constant.WORKFLOW.OPER_NOT_READ ), toStepId );

                                Map infoMap = new HashMap();

                                infoMap.put( "infoType", infoType );

                                infoMap.put( "contentId", contentId );
                                infoMap.put( "pUserName", managerName );
                                infoMap.put( "fromStepId", fromStepId );
                                infoMap.put( "actionId", actionId );
                                infoMap.put( "toStepId", toStepId );
                                infoMap.put( "eventDT", new Date( DateAndTimeUtil
                                    .clusterTimeMillis() ) );

                                workflowDao.saveWorkflowOperInfo( infoMap );
                            }

                            workflowDao.updateWorkflowOperationCurrentAuditUser( contentId,
                                infoType, null );
                        }
                        else
                        {
                            // 以下不再使用,
                            /*
                             * WorkflowOperation wfOperBean = new
                             * WorkflowOperation(); wfOperBean.setContentId(
                             * contentId ); wfOperBean.setClassId(
                             * contentClass.getClassId() );
                             * wfOperBean.setCurrentStep( toStepId );
                             * wfOperBean.setFlowId( workflowBean.getFlowId() );
                             * wfOperBean.setOperStatus( Integer .valueOf(
                             * Constant.WORKFLOW.OPER_NOT_READ ) );
                             * wfOperBean.setPossessStatus( Integer .valueOf(
                             * Constant.WORKFLOW.OPER_NOT_POSSESS ) );
                             * 
                             * workFlowDao.saveWorkflowOperation( wfOperBean );
                             */
                        }

                        censorStatus = Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW;
                    }

                    // 更新内容状态
                    if( Constant.WORKFLOW.INFO_TYPE_CONTENT.equals( infoType ) )
                    {
                        contentDao.updateContentMainInfoCensorStatus( contentId, censorStatus );
                    }
                    else if( Constant.WORKFLOW.INFO_TYPE_GB.equals( infoType ) )
                    {
                        gbDao.updateGuestbookCensorStatus( contentId, censorStatus );

                        gbDao.updateGuestbookReplyStatus( contentId, censorStatus );
                    }

                }

                wfActionList.add( actExecInfo );
            }
        }

        return new Object[] { censorStatus, ( opBean != null ? opBean.getFlowTarget() : "" ) };

    }

    /**
     * 根据挂靠的工作流,确定当前内容的流转状态.
     * 
     * @param contentClass
     * @param contentId
     * @param actionId
     * @param fromStepId
     * @param toStepId
     * @param appearStartDateTS
     * @param appearEndDateTS
     * @param update
     * @param wfActionList
     * @param currentDbInfo
     * @return
     */
    @SuppressWarnings( "unchecked" )
    // public Integer disposeCommonWorkflowStatus( Long flowId, Long contentId,
    // Integer infoType,
    // Long classId, Long actionId, String flowTarget, Long fromStepId, Long
    // toStepId,
    // Timestamp appearStartDateTS, Timestamp appearEndDateTS, boolean update,
    // List wfActionList,
    // Map currentDbInfo )
    // {
    // WorkflowBean workflowBean = retrieveSingleWorkflowBean( flowId );
    //
    // String managerName = ( String )
    // SecuritySessionKeeper.getSecuritySession().getAuth()
    // .getApellation();
    //
    // // 默认为发布状态
    // Integer censorStatus = Constant.WORKFLOW.CENSOR_STATUS_SUCCESS;
    //
    // Date currentDate = new Date( DateAndTimeUtil.clusterTimeMillis() );
    //
    // if( workflowBean != null )
    // {
    //
    // // 有工作流但是暂无审核步骤
    // if( workflowBean.getStep().intValue() == 0 )
    // {
    // // 若当前时间在下线时间之后,则为过期状态
    // if( currentDate.compareTo( appearEndDateTS ) > 0 )
    // {
    // censorStatus = Constant.WORKFLOW.CENSOR_STATUS_WITHDRAW;
    // }
    // else if( currentDate.compareTo( appearStartDateTS ) < 0 )
    // {
    // // 若当前时间在发布时间之前,还没到发布时间,则为等待发布状态
    // censorStatus = Constant.WORKFLOW.CENSOR_STATUS_WAIT_PUBLISH;
    // }
    // }
    // else
    // {
    // // 增加模式下,先要生成op对象
    // if( !update )
    // {
    // WorkflowOperation wfOperBean = new WorkflowOperation();
    //
    // wfOperBean.setInfoType( infoType );
    // wfOperBean.setFlowTarget( flowTarget );
    //
    // wfOperBean.setContentId( contentId );
    // wfOperBean.setClassId( classId );
    // wfOperBean.setCurrentStep( toStepId );
    // wfOperBean.setFlowId( workflowBean.getFlowId() );
    // wfOperBean.setOperStatus( Integer.valueOf(
    // Constant.WORKFLOW.OPER_NOT_READ ) );
    // wfOperBean.setPossessStatus( Integer
    // .valueOf( Constant.WORKFLOW.OPER_NOT_POSSESS ) );
    // wfOperBean.setWkUpdateDT( workflowBean.getUpdateDT() );
    //
    // workflowDao.saveWorkflowOperation( wfOperBean );
    //
    // Map infoMap = new HashMap();
    //
    // infoMap.put( "infoType", infoType );
    //
    // infoMap.put( "contentId", contentId );
    // infoMap.put( "pUserName", managerName );
    // infoMap.put( "fromStepId", fromStepId );
    // infoMap.put( "actionId", actionId );
    // infoMap.put( "toStepId", toStepId );
    // infoMap.put( "eventDT", new Date( DateAndTimeUtil.clusterTimeMillis() )
    // );
    //
    // workflowDao.saveWorkflowOperInfo( infoMap );
    // }
    //
    // // 返回执行的actionID
    //
    // String[] actExecInfo = new String[2];
    //
    // // 在update模式工作流中只有存在op对象的动作请求才是合法的
    //
    // if( update
    // && !Constant.WORKFLOW.START_ACTION_ID_VALUE.equals( toStepId )
    // && workflowDao.queryWorkflowOperationCountByContentId( contentId,
    // infoType )
    // .intValue() < 1 )
    // {
    // actExecInfo[0] = "-1";
    //
    // actExecInfo[1] = Constant.COMMON.OFF.toString();
    //
    // // 更新实际当前内容审核状态
    // contentDao.updateContentMainInfoCensorStatus( contentId,
    // ( Integer ) currentDbInfo.get( "censorState" ) );
    // }
    // else
    // {
    // WorkflowStepActionBean actionBean = workflowDao
    // .queryWorkflowStepActionBeanByFlowIdAndStep( workflowBean.getFlowId(),
    // fromStepId, toStepId );
    //
    // actExecInfo[0] = ( actionBean != null ) ?
    // actionBean.getActionId().toString()
    // : "";
    //
    // // 有审核步骤存在,立即进入审核模式
    // // TODO 需要检查是否当前角色有资格进入当前步骤
    //
    // if( Constant.WORKFLOW.END_ACTION_ID_VALUE.equals( toStepId ) )
    // {
    // // 进入结束节点，删除工作流处理信息
    // if( Constant.COMMON.ON.equals( actionBean.getConjunctManFlag() ) )
    // {
    // // 当前动作为会签模式,只有所有机构 角色 人员都通过,才更新
    //
    // if( disposeConjunctAction( infoType, workflowBean, actionBean,
    // contentId, fromStepId ) )
    // {
    // censorStatus = pendingCensorStateByStartAndEndPublishDate(
    // appearStartDateTS, appearEndDateTS,
    // Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );
    //
    // actExecInfo[1] = Constant.COMMON.ON.toString();
    //
    // workflowDao
    // .deleteWorkflowOperationByContentId( contentId, infoType );
    //
    // // 删除trace
    // workflowDao.deleteActorCensorTraceInfo( contentId, actionBean
    // .getActionId(), infoType );
    //
    // // 删除msg
    // workflowDao.deleteWorkflowMsg( contentId );
    //
    // Map infoMap = new HashMap();
    //
    // infoMap.put( "infoType", infoType );
    //
    // infoMap.put( "contentId", contentId );
    // infoMap.put( "pUserName", managerName );
    // infoMap.put( "fromStepId", fromStepId );
    // infoMap.put( "actionId", actionId );
    // infoMap.put( "toStepId", toStepId );
    // infoMap.put( "eventDT", new Date( DateAndTimeUtil
    // .clusterTimeMillis() ) );
    //
    // workflowDao.saveWorkflowOperInfo( infoMap );
    // }
    // else
    // {
    // censorStatus = ( Integer ) currentDbInfo.get( "censorState" );
    //
    // actExecInfo[1] = Constant.COMMON.OFF.toString();
    //
    // workflowDao.updateWorkflowOperationCurrentAuditUser( contentId,
    // infoType, null );
    //
    // censorStatus = Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW;
    //
    // }
    // }
    // else
    // {
    // censorStatus = pendingCensorStateByStartAndEndPublishDate(
    // appearStartDateTS, appearEndDateTS,
    // Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );
    //
    // actExecInfo[1] = Constant.COMMON.ON.toString();
    //
    // workflowDao.deleteWorkflowOperationByContentId( contentId, infoType );
    //
    // // 删除msg
    // workflowDao.deleteWorkflowMsg( contentId );
    // }
    // }
    // else if( Constant.WORKFLOW.DRAFT_ACTION_ID_VALUE.equals( toStepId ) )
    // {
    // // 进入退稿节点，删除工作流处理信息
    // if( Constant.COMMON.ON.equals( actionBean.getConjunctManFlag() ) )
    // {
    // // 当前动作为会签模式,只有所有机构 角色 人员都通过,才更新
    //
    // if( disposeConjunctAction( infoType, workflowBean, actionBean,
    // contentId, fromStepId ) )
    // {
    // censorStatus = Constant.WORKFLOW.CENSOR_STATUS_DRAFT;
    //
    // actExecInfo[1] = Constant.COMMON.ON.toString();
    //
    // workflowDao
    // .deleteWorkflowOperationByContentId( contentId, infoType );
    //
    // // 删除trace
    // workflowDao.deleteActorCensorTraceInfo( contentId, actionBean
    // .getActionId(), infoType );
    //
    // // 删除msg
    // workflowDao.deleteWorkflowMsg( contentId );
    //
    // Map infoMap = new HashMap();
    //
    // infoMap.put( "infoType", infoType );
    //
    // infoMap.put( "contentId", contentId );
    // infoMap.put( "pUserName", managerName );
    // infoMap.put( "fromStepId", fromStepId );
    // infoMap.put( "actionId", actionId );
    // infoMap.put( "toStepId", toStepId );
    // infoMap.put( "eventDT", new Date( DateAndTimeUtil
    // .clusterTimeMillis() ) );
    //
    // workflowDao.saveWorkflowOperInfo( infoMap );
    // }
    // else
    // {
    // censorStatus = ( Integer ) currentDbInfo.get( "censorState" );
    //
    // actExecInfo[1] = Constant.COMMON.OFF.toString();
    //
    // workflowDao.updateWorkflowOperationCurrentAuditUser( contentId,
    // infoType, null );
    //
    // }
    // }
    // else
    // {
    // censorStatus = Constant.WORKFLOW.CENSOR_STATUS_DRAFT;
    //
    // actExecInfo[1] = Constant.COMMON.ON.toString();
    //
    // workflowDao.deleteWorkflowOperationByContentId( contentId, infoType );
    //
    // // 删除msg
    // workflowDao.deleteWorkflowMsg( contentId );
    // }
    // }
    // else if( Constant.WORKFLOW.START_ACTION_ID_VALUE.equals( toStepId ) )
    // {
    // // 进入工作流开始状态,需要分别对待是否是发布态进入工作流
    //
    // if( Constant.WORKFLOW.RE_EDIT_ACTION_ID_VALUE.equals( fromStepId ) )
    // {
    // // 正式确认进入工作流,并处于默认开始模式,此步无需人员参与,需要生成新的工作流op
    //
    // WorkflowOperation wfOperBean = new WorkflowOperation();
    //
    // wfOperBean.setInfoType( infoType );
    // wfOperBean.setFlowTarget( flowTarget );
    //
    // wfOperBean.setContentId( contentId );
    // wfOperBean.setClassId( classId );
    // wfOperBean.setCurrentStep( workflowDao
    // .queryStartStepTrueIdByFlowId( workflowBean.getFlowId() ) ); //
    // 开始默认动作需要获取实际的步骤id，以获取参与者
    // wfOperBean.setFlowId( workflowBean.getFlowId() );
    // wfOperBean.setOperStatus( Integer
    // .valueOf( Constant.WORKFLOW.OPER_NOT_READ ) );
    // wfOperBean.setPossessStatus( Integer
    // .valueOf( Constant.WORKFLOW.OPER_NOT_POSSESS ) );
    // wfOperBean.setWkUpdateDT( workflowBean.getUpdateDT() );
    //
    // workflowDao.saveWorkflowOperation( wfOperBean );
    //
    // Map infoMap = new HashMap();
    //
    // infoMap.put( "infoType", infoType );
    //
    // infoMap.put( "contentId", contentId );
    // infoMap.put( "eActionName", fromStepId );
    //
    // infoMap.put( "pUserName", managerName );
    // infoMap.put( "fromStepId", fromStepId );
    // infoMap.put( "actionId", actionId );
    // infoMap.put( "toStepId", toStepId );
    // infoMap
    // .put( "eventDT", new Date( DateAndTimeUtil.clusterTimeMillis() ) );
    //
    // workflowDao.saveWorkflowOperInfo( infoMap );
    //
    // censorStatus = Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW;
    // }
    // else if( Constant.COMMON.ON.equals( actionBean.getConjunctManFlag() ) )
    // {
    // // 预发布模式特殊,当fromStepId不是定义步骤时,说明是非定义动作
    // // 当前动作为会签模式,只有所有机构 角色 人员都通过,才更新
    //
    // if( disposeConjunctAction( infoType, workflowBean, actionBean,
    // contentId, fromStepId ) )
    // {
    // // censorStatus =
    // // Constant.WORKFLOW.CENSOR_STATUS_IN_EDIT;
    //
    // // workFlowDao
    // // .deleteWorkflowOperationByContentId(
    // // contentId );
    //
    // // 删除trace
    // workflowDao.deleteActorCensorTraceInfo( contentId, actionBean
    // .getActionId(), infoType );
    //
    // Map infoMap = new HashMap();
    //
    // infoMap.put( "infoType", infoType );
    //
    // infoMap.put( "contentId", contentId );
    // infoMap.put( "pUserName", managerName );
    // infoMap.put( "fromStepId", fromStepId );
    // infoMap.put( "actionId", actionId );
    // infoMap.put( "toStepId", toStepId );
    // infoMap.put( "eventDT", new Date( DateAndTimeUtil
    // .clusterTimeMillis() ) );
    //
    // workflowDao.saveWorkflowOperInfo( infoMap );
    // }
    // else
    // {
    // censorStatus = ( Integer ) currentDbInfo.get( "censorState" );
    // }
    //
    // workflowDao.updateWorkflowOperationCurrentAuditUser( contentId,
    // infoType, null );
    // }
    //
    // }
    // else if( Constant.WORKFLOW.START_ACTION_ID_VALUE.equals( fromStepId ) )
    // {
    // /**
    // * 注意:以下流程已经不再使用
    // */
    // // 开始一个新的工作流op
    // /*
    // * if( Constant.COMMON.ON.equals( actionBean
    // * .getConjunctManFlag() ) ) { // 当前动作为会签模式,只有所有机构 角色
    // * 人员都通过,才更新 // 开始默认动作需要获取实际的步骤id，以获取参与者 fromStepId =
    // * workFlowDao .queryStartStepTrueIdByFlowId(
    // * workflowBean .getFlowId() );
    // *
    // * if( disposeConjunctAction( workflowBean, actionBean,
    // * contentId, fromStepId ) ) { WorkflowOperation
    // * wfOperBean = new WorkflowOperation();
    // * wfOperBean.setContentId( contentId );
    // * wfOperBean.setClassId( contentClass .getClassId() );
    // * wfOperBean.setCurrentStep( toStepId );
    // * wfOperBean.setFlowId( workflowBean.getFlowId() );
    // * wfOperBean .setOperStatus( Integer .valueOf(
    // * Constant.WORKFLOW.OPER_NOT_READ ) ); wfOperBean
    // * .setPossessStatus( Integer .valueOf(
    // * Constant.WORKFLOW.OPER_NOT_POSSESS ) );
    // *
    // * workFlowDao.saveWorkflowOperation( wfOperBean );
    // *
    // * censorStatus =
    // * Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW; // 删除trace
    // * workFlowDao.deleteActorCensorTraceInfo( contentId,
    // * actionBean.getActionId() ); } } else {
    // *
    // * WorkflowOperation wfOperBean = new
    // * WorkflowOperation(); wfOperBean.setContentId(
    // * contentId ); wfOperBean.setClassId(
    // * contentClass.getClassId() );
    // * wfOperBean.setCurrentStep( toStepId );
    // * wfOperBean.setFlowId( workflowBean.getFlowId() );
    // * wfOperBean.setOperStatus( Integer .valueOf(
    // * Constant.WORKFLOW.OPER_NOT_READ ) );
    // * wfOperBean.setPossessStatus( Integer .valueOf(
    // * Constant.WORKFLOW.OPER_NOT_POSSESS ) );
    // *
    // * workFlowDao.saveWorkflowOperation( wfOperBean );
    // *
    // * censorStatus =
    // * Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW; }
    // */
    // }
    // else
    // {
    // // 进入其他工作流中间步骤
    //
    // if( update )
    // {
    // if( Constant.COMMON.ON.equals( actionBean.getConjunctManFlag() ) )
    // {
    // // 当前动作为会签模式,只有所有机构 角色 人员都通过,才更新
    //
    // if( disposeConjunctAction( infoType, workflowBean, actionBean,
    // contentId, fromStepId ) )
    // {
    // workflowDao.updateWorkflowOperationPossessInfo( contentId,
    // infoType, null, null, Integer
    // .valueOf( Constant.WORKFLOW.OPER_NOT_POSSESS ), Integer
    // .valueOf( Constant.WORKFLOW.OPER_NOT_READ ), toStepId );
    //
    // actExecInfo[1] = Constant.COMMON.ON.toString();
    //
    // // 删除trace
    // workflowDao.deleteActorCensorTraceInfo( contentId, actionBean
    // .getActionId(), infoType );
    //
    // Map infoMap = new HashMap();
    //
    // infoMap.put( "infoType", infoType );
    //
    // infoMap.put( "contentId", contentId );
    // infoMap.put( "pUserName", managerName );
    // infoMap.put( "fromStepId", fromStepId );
    // infoMap.put( "actionId", actionId );
    // infoMap.put( "toStepId", toStepId );
    // infoMap.put( "eventDT", new Date( DateAndTimeUtil
    // .clusterTimeMillis() ) );
    //
    // workflowDao.saveWorkflowOperInfo( infoMap );
    // }
    // else
    // {
    // censorStatus = ( Integer ) currentDbInfo.get( "censorState" );
    //
    // actExecInfo[1] = Constant.COMMON.OFF.toString();
    //
    // Map infoMap = new HashMap();
    //
    // infoMap.put( "infoType", infoType );
    //
    // infoMap.put( "contentId", contentId );
    // infoMap.put( "pUserName", managerName );
    // infoMap.put( "fromStepId", fromStepId );
    // infoMap.put( "actionId", actionId );
    // infoMap.put( "toStepId", toStepId );
    // infoMap.put( "eventDT", new Date( DateAndTimeUtil
    // .clusterTimeMillis() ) );
    //
    // workflowDao.saveWorkflowOperInfo( infoMap );
    // }
    // }
    // else
    // {
    // actExecInfo[1] = Constant.COMMON.ON.toString();
    //
    // workflowDao.updateWorkflowOperationPossessInfo( contentId,
    // infoType, null, null, Integer
    // .valueOf( Constant.WORKFLOW.OPER_NOT_POSSESS ), Integer
    // .valueOf( Constant.WORKFLOW.OPER_NOT_READ ), toStepId );
    //
    // Map infoMap = new HashMap();
    //
    // infoMap.put( "infoType", infoType );
    //
    // infoMap.put( "contentId", contentId );
    // infoMap.put( "pUserName", managerName );
    // infoMap.put( "fromStepId", fromStepId );
    // infoMap.put( "actionId", actionId );
    // infoMap.put( "toStepId", toStepId );
    // infoMap.put( "eventDT", new Date( DateAndTimeUtil
    // .clusterTimeMillis() ) );
    //
    // workflowDao.saveWorkflowOperInfo( infoMap );
    // }
    //
    // workflowDao.updateWorkflowOperationCurrentAuditUser( contentId,
    // infoType, null );
    // }
    // else
    // {
    // // 以下不再使用,
    // /*
    // * WorkflowOperation wfOperBean = new
    // * WorkflowOperation(); wfOperBean.setContentId(
    // * contentId ); wfOperBean.setClassId(
    // * contentClass.getClassId() );
    // * wfOperBean.setCurrentStep( toStepId );
    // * wfOperBean.setFlowId( workflowBean.getFlowId() );
    // * wfOperBean.setOperStatus( Integer .valueOf(
    // * Constant.WORKFLOW.OPER_NOT_READ ) );
    // * wfOperBean.setPossessStatus( Integer .valueOf(
    // * Constant.WORKFLOW.OPER_NOT_POSSESS ) );
    // *
    // * workFlowDao.saveWorkflowOperation( wfOperBean );
    // */
    // }
    //
    // censorStatus = Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW;
    // }
    //
    // // 更新内容状态
    // contentDao.updateContentMainInfoCensorStatus( contentId, censorStatus );
    // }
    //
    // wfActionList.add( actExecInfo );
    // }
    // }
    //
    // return censorStatus;
    //
    // }
    public boolean checkWorkflowStatus( Long fromStepId, Long toStepId, Long contentId,
        Integer infoType )
    {
        WorkflowOperationBean op = workflowDao.queryWorkflowOperationBeanByContentId( contentId,
            infoType );

        if( ( !Constant.WORKFLOW.START_ACTION_ID_VALUE.equals( toStepId ) && op == null )
            || ( op != null && !op.getCurrentStep().equals( fromStepId ) ) )
        {
            return false;
        }

        return true;
    }

    public boolean checkWorkflowAvoidStatus( Long fromStepId, Long toStepId, Long contentId )
    {
        WorkflowStepInfoBean step = workflowDao.querySingleWorkflowStepBeanByStepId( fromStepId );

        if( step != null && Constant.COMMON.ON.equals( step.getAvoidFlag() ) )
        {
            // 回避模式下不允许审核自己添加的内容
            Map info = contentDao.querySingleContentMainInfo( contentId );

            if( !info.isEmpty() )
            {
                if( ( ( String ) SecuritySessionKeeper.getSecuritySession().getAuth()
                    .getApellation() ).equals( ( String ) info.get( "creator" ) ) )
                {
                    return false;
                }
            }

        }

        return true;
    }

    public Set retrieveActorIdForStepOrg( Long flowId, Long stepId )
    {
        return workflowDao.queryActorIdForStepOrg( flowId, stepId );
    }

    @SuppressWarnings( "unchecked" )
    private boolean disposeConjunctAction( Integer infoType, WorkflowBean workflowBean,
        WorkflowStepActionBean actionBean, Long contentId, Long fromStepId )
    {
        boolean goNext = true;

        Long loginUid = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth().getIdentity();

        Set traceUserIdSet = null;

        Set allUserIdSet = null;

        Set orgUserIdSet = null;

        Long[] allUserIdArray = null;

        Long userId = null;

        // 若动作为主管模式,当前管理员为某机构主管,直接通过
        if( Constant.COMMON.ON.equals( actionBean.getOrgBossMode() )
            && Constant.COMMON.ON.equals( actionBean.getConjunctManFlag() ) )
        {
            // 获取当前参与的各机构主管人员
            orgUserIdSet = workflowDao.queryActorOrgBossId( workflowBean.getFlowId(), fromStepId );

            if( !orgUserIdSet.isEmpty() )
            {
                // 当前管理员在参与机构管理员列表中,说明当前审核者为参与机构主管,直接通过
                if( orgUserIdSet.contains( loginUid ) )
                {
                    return true;
                }
            }
        }

        // 获取当前动作已经审核的人员
        traceUserIdSet = workflowDao.queryActionOpTraceUserId( actionBean.getActionId(), contentId,
            infoType );

        // 加上当前用户Id
        traceUserIdSet.add( loginUid );

        // 获取步骤所有可能参与人员
        allUserIdSet = workflowDao.queryActorIdForStepOrg( workflowBean.getFlowId(), fromStepId );

        // 若当前步骤回避模式,并且内容所有者在参与审核者中,还需加上内容所有者
        WorkflowStepInfoBean step = workflowDao.querySingleWorkflowStepBeanByStepId( fromStepId );

        if( step != null && Constant.COMMON.ON.equals( step.getAvoidFlag() ) )
        {
            Long cuId = contentDao.querySingleContentMainInfoRelateCreatorId( contentId );

            if( allUserIdSet.contains( cuId ) )
            {
                traceUserIdSet.add( cuId );
            }

        }

        allUserIdArray = ( Long[] ) allUserIdSet.toArray( new Long[] {} );

        boolean notAllCensor = false;

        for ( int i = 0; i < allUserIdArray.length; i++ )
        {
            userId = allUserIdArray[i];

            if( !traceUserIdSet.contains( userId ) )
            {
                // 只要有一个不存在,意味着还有人没有审核
                notAllCensor = true;

                goNext = false;

                break;
            }
        }

        if( notAllCensor )
        {
            // 若当前动作没有完结,则将当前用户的审核信息加入trace,等待其他人审核
            if( workflowDao.queryActorIdCountForStepOrg( contentId, actionBean.getActionId(),
                loginUid, infoType ).longValue() < 1 )
            {
                workflowDao.saveActorIdForStepOrg( contentId, actionBean.getActionId(), loginUid,
                    infoType );
            }
        }

        return goNext;
    }

    /**
     * 判断当前内容的所属栏目的工作流,以及当前步骤,是否有此用户,若有此用户,则锁定当前数据
     * 
     * @param contentId
     * @param userName
     */
    public int disposeOperationLockRequest( Long contentId, Integer infoType, Auth auth )
    {
        if( contentId == null || contentId.longValue() == -1 || auth == null
            || !auth.isAuthenticated() )
        {
            return -1;
        }

        try
        {
            mysqlEngine.beginTransaction();

            WorkflowOperationBean operBean = workflowDao.querySingleWorkflowOperation( contentId,
                infoType );

            // 非占有状态
            if( Constant.WORKFLOW.OPER_NOT_POSSESS == operBean.getPossessStatus().intValue() )
            {
                // 占有被审核数据
                workflowDao.updateWorkflowOperationPossessInfo( contentId, infoType, ( Long ) auth
                    .getIdentity(), ( String ) auth.getApellation(),
                    Constant.WORKFLOW.OPER_IS_POSSESS, Constant.WORKFLOW.OPER_IN_FLOW );

                // Map infoMap = new HashMap();

                // // 只记录user的情况,那意味着被申请
                // infoMap.put( "contentId", contentId );
                // infoMap.put( "pUserName", ( String ) auth.getApellation() );
                // infoMap.put( "fromStepId", Long.valueOf( -9999 ) );
                // infoMap.put( "actionId", Long.valueOf( -9999 ) );
                // infoMap.put( "toStepId", Long.valueOf( -9999 ) );
                // infoMap.put( "eventDT", new Date() );
                //
                // workFlowDao.saveWorkflowOperInfo( infoMap );
            }
            else
            {
                return 0;
            }

            // Integer countFlag = workFlowDao.checkOperationStep( contentId );
            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        return 1;

    }

    /**
     * 获取内容审核日志
     * 
     * @param contentId
     * @return
     */
    public Object getWorkflowOperInfoInfoList( String contentId, String infoType, String pn,
        String size )
    {
        List result = null;

        Long cid = Long.valueOf( StringUtil.getLongValue( contentId, -1 ) );

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 12 );

        Page pageInfo = null;

        Long count = null;

        count = workflowDao.queryWorkflowOperInfoInfoCount( cid, Integer.valueOf( infoType ) );

        pageInfo = new Page( pageSize, count.intValue(), pageNum );

        result = workflowDao.queryWorkflowOperInfoInfoList( cid, Integer.valueOf( infoType ), Long
            .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );

        return new Object[] { result, pageInfo };
    }

    public void deleteWorkflowAllInfo( List idList, Integer infoType )
    {
        if( idList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();
            Long flowId = null;
            for ( int i = 0; i < idList.size(); i++ )
            {
                flowId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

                Integer count = workflowDao.queryFlowIdForOperation( flowId, infoType );

                if( count.intValue() > 0 )
                {
                    // 说明存在没有完成的工作流,不可删除
                    continue;
                }

                workflowDao.deleteWorkflowStepInfoByFlowId( flowId );
                workflowDao.deleteWorkflowActorByFlowId( flowId );
                workflowDao.deleteWorkflowActionByFlowId( flowId );
                workflowDao.deleteWorkflowByFlowId( flowId );

                channelDao.updateWorkflowInfoToDefaultStatus( flowId );
            }
            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            resetWorkflowActorBeanListCache();
            resetWorkflowSingleBeanCache();
            ChannelDao.clearAllCache();
        }
    }

    public boolean checkFlowRestrictedUser( Auth auth, Long contentId, Integer infoType )
    {

        if( auth == null || !auth.isAuthenticated() || contentId == null )
        {
            return false;
        }

        WorkflowOperationBean bean = workflowDao.querySingleWorkflowOperation( contentId, infoType );

        // 当此文档已经被申请的情况下,用户必须一致
        if( Constant.WORKFLOW.OPER_IS_POSSESS == bean.getPossessStatus().intValue() )
        {
            if( bean.getPossessUserId().equals( auth.getIdentity() ) )
            {
                return true;
            }
        }

        String sqlOrCond = StringUtil.replaceString( auth.getRoleSqlHelper().getAllRoleOrQuery( "",
            "" ), "auditManId", "wa.auditManId" );

        // 没有被申请的内容审核检查
        Integer count = workflowDao.queryWorkflowOperationInAuditCountByUserIdWithNoPossess(
            sqlOrCond, contentId, infoType );

        return count.intValue() > 0 ? true : false;
    }

    public WorkflowStepInfoBean retrieveSingleWorkflowStepInfoBean( Long contentId, Integer infoType )
    {
        // TODO 需要缓存
        return workflowDao.querySingleWorkflowStepInfoBean( contentId, infoType );
    }

    /**
     * 处理PASS action请求
     * 
     * @param contentId
     * @param userId
     * @return -1:已被处理过的数据
     */
    public int disposeAuditActionRequest( Long contentId, Integer infoType, Long userId,
        String actionFlag )
    {
        if( contentId == null || userId == null )
        {
            return 0;
        }

        try
        {
            mysqlEngine.beginTransaction();

            WorkflowOperationBean operBean = workflowDao
                .querySingleWorkflowOperationBeanByContentIdAndUserId( contentId, infoType );

            if( operBean == null )
            {
                // 表示已经被处理过
                return -1;
            }

            // 如果在处理中
            if( Constant.WORKFLOW.OPER_IS_POSSESS == operBean.getPossessStatus().intValue() )
            {
                // 占有状态的处理
                if( !userId.equals( operBean.getPossessUserId() ) )
                {
                    // 表示已经被处理过,因为申请人和当前处理用户ID不一致
                    return -1;
                }
            }

            WorkflowBean workFlowBean = workflowDao.querySingleWorkflowBean( operBean.getFlowId() );

            // 处理审核步骤,下一步或上一步
            Integer flowStep = workFlowBean.getStep();

            Integer nextStep = null;
            if( Constant.WORKFLOW.PASS_ACT.equals( actionFlag ) )
            {
                nextStep = Integer.valueOf( operBean.getCurrentStep().intValue() + 1 );

                // TODO记录操作日志
                if( nextStep.intValue() > flowStep.intValue()
                    && operBean.getCurrentStep().intValue() == flowStep.intValue() )
                {
                    // 流程结束

                    // 根据内容发布时间决定流程状态

                    // 若当前时间在下线时间之后,则为过期状态
                    Map contentMap = contentDao.querySingleContentMainInfo( contentId );

                    Integer censorStatus = pendingCensorStateByStartAndEndPublishDate(
                        ( Timestamp ) contentMap.get( "appearStartDateTime" ),
                        ( Timestamp ) contentMap.get( "appearEndDateTime" ),
                        Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );

                    // 更新内容主信息审核发布状态
                    if( infoType == 1 )
                    {
                        contentDao.updateContentMainInfoCensorStatus( contentId, censorStatus );
                    }
                    else if( infoType == 2 )
                    {
                        gbDao.updateGuestbookCensorStatus( contentId, censorStatus );

                        gbDao.updateGuestbookReplyStatus( contentId, censorStatus );

                    }

                    // 删除审核信息
                    workflowDao.deleteWorkflowOperationByContentId( contentId, infoType );

                }
                else
                {
                    // 进入下一步,更新为下一步未读和未处理
                    // workFlowDao.updateWorkflowOperationPossessInfo(
                    // contentId,
                    // null, null, Constant.WORKFLOW.OPER_NOT_POSSESS,
                    // Constant.WORKFLOW.OPER_NOT_READ, nextStep );
                }

            }
            else if( Constant.WORKFLOW.REJECT_ACT.equals( actionFlag ) )
            {
                nextStep = Integer.valueOf( operBean.getCurrentStep().intValue() - 1 );

                if( nextStep.intValue() < 1 && operBean.getCurrentStep().intValue() == 1 )
                {
                    // 稿件被退回
                    // workFlowDao.updateWorkflowOperationPossessInfo(
                    // contentId,
                    // null, null, Constant.WORKFLOW.OPER_NOT_POSSESS,
                    // Constant.WORKFLOW.OPER_REJECT_START, nextStep );
                }
                else
                {
                    // workFlowDao.updateWorkflowOperationPossessInfo(
                    // contentId,
                    // null, null, Constant.WORKFLOW.OPER_NOT_POSSESS,
                    // Constant.WORKFLOW.OPER_NOT_READ, nextStep );
                }
            }

            mysqlEngine.commit();
            return 1;

        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        // return 0;
    }

    public void deleteWorkflowOperationByContentId( Long contentId, Integer infoType )
    {
        workflowDao.deleteWorkflowOperationByContentId( contentId, infoType );
    }

    public void resetWorkflowOperationToStartStepByFlowId( Long contentId, Integer infoType,
        Long flowId )
    {
        try
        {
            mysqlEngine.beginTransaction();

            WorkflowBean wkBean = workflowDao.querySingleWorkflowBean( flowId );

            if( wkBean != null )
            {
                workflowDao.updateWorkflowOperationStepAndWkUpdateDtInfo( contentId, infoType,
                    workflowDao.queryStartStepTrueIdByFlowId( flowId ), wkBean.getUpdateDT() );
            }

            mysqlEngine.commit();

        }
        finally
        {
            mysqlEngine.endTransaction();
        }
    }

    public static Integer pendingCensorStateByStartAndEndPublishDate( Timestamp start,
        Timestamp end, Integer defaultCensor )
    {
        if( start == null && end == null )
        {
            return defaultCensor;
        }

        Date current = new Date( DateAndTimeUtil.clusterTimeMillis() );

        if( current.compareTo( end ) > 0 )
        {
            // 到达下线时间
            return Constant.WORKFLOW.CENSOR_STATUS_WITHDRAW;
        }
        else if( current.compareTo( start ) < 0 )
        {
            // 若当前时间在发布时间之前,还没到发布时间,则为等待发布状态
            return Constant.WORKFLOW.CENSOR_STATUS_WAIT_PUBLISH;
        }
        else
        {
            return defaultCensor;
        }
    }

    /**
     * 添加步骤信息以及参与审核者信息
     * 
     * @param stepInfo
     * @param actorBeanList
     */
    public void addWorkflowStepInfoAndAuditActor( WorkflowStepInfo stepInfo, List actorBeanList )
    {
        if( stepInfo == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            UpdateState updateState = workflowDao.saveWorkflowStepInfo( stepInfo );

            if( updateState.haveKey() )
            {
                WorkflowActor actor = null;
                for ( int i = 0; i < actorBeanList.size(); i++ )
                {
                    actor = ( WorkflowActor ) actorBeanList.get( i );
                    actor.setFlowId( stepInfo.getFlowId() );
                    actor.setCensorStep( Long.valueOf( updateState.getKey() ) );
                    workflowDao.saveWorkflowActor( actor );
                }

            }
            else
            {
                throw new FrameworkException( "添加步骤主信息失败!" );
            }

            mysqlEngine.commit();

        }
        finally
        {
            mysqlEngine.endTransaction();
            resetWorkflowActorBeanListCache();
            resetWorkflowSingleBeanCache();
            ChannelDao.clearAllCache();
        }

    }

    /**
     * 更新步骤信息以及参与审核者信息
     * 
     * @param stepInfo
     * @param actorBeanList
     */
    public void editWorkflowStepInfoAndAuditActor( WorkflowStepInfo stepInfo, List actorBeanList )
    {
        if( stepInfo == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            workflowDao.updateWorkflowStepInfo( stepInfo );

            // 删除原有的actor
            workflowDao.deleteWorkflowActorByFlowIdAndStepId( stepInfo.getFlowId(), stepInfo
                .getStepId() );

            WorkflowActor actor = null;

            for ( int i = 0; i < actorBeanList.size(); i++ )
            {
                actor = ( WorkflowActor ) actorBeanList.get( i );

                if( actor.getAuditManId().longValue() > 0 )
                {
                    actor.setFlowId( stepInfo.getFlowId() );
                    actor.setCensorStep( stepInfo.getStepId() );
                    workflowDao.saveWorkflowActor( actor );
                }
            }

            mysqlEngine.commit();

        }
        finally
        {
            mysqlEngine.endTransaction();
            resetWorkflowActorBeanListCache();
            resetWorkflowSingleBeanCache();
            ChannelDao.clearAllCache();
        }

    }

    public List retrieveStepActorBeanList( Long flowIdVar, Long stepIdVar, Integer typeVar )
    {
        return workflowDao.queryWorkflowActorBeanList( flowIdVar, stepIdVar, typeVar );
    }

    public List retrieveStepActionBeanList( Long flowIdVar )
    {
        return workflowDao.queryWorkflowActionBeanByFlowId( flowIdVar );
    }

    public List retrieveWorkflowStartActionBeanByFlowId( Long flowId )
    {
        return workflowDao.queryWorkflowStartActionBeanByFlowId( flowId );
    }

    public WorkflowStepActionBean retrieveSingleWorkflowStepActionBean( Long actId )
    {
        return workflowDao.querySingleWorkflowStepActionBean( actId );
    }

    /**
     * 根据当前处于工作流中的内容状态获取当前步骤动作信息
     * 
     * @param contentId
     * @return
     */
    public List retrieveWorkflowStepBeanListByClassId( Long contentId, Integer infoType )
    {
        return workflowDao.queryWorkflowStepBeanListByClassId( contentId, infoType );
    }

    /**
     * 检查当前内容是否处于工作流处理中
     * 
     * @param userId
     * @param flowId
     * @param stepId
     * @return
     */
    public boolean checkCurrentLoginUserHaveStepActionForSingleAuditContent( Long userId,
        Long contentId, Integer infoType )
    {
        Integer count = workflowDao.queryActionCountByUserId( userId, contentId, infoType );

        return count.intValue() > 0 ? true : false;
    }

    /**
     * 添加一个新的工作流动作
     * 
     * @param action
     */
    public void addNewWorkflowActionInfo( WorkflowStepAction action )
    {
        try
        {
            workflowDao.saveWorkflowAction( action );
        }
        finally
        {
            resetWorkflowActorBeanListCache();
            resetWorkflowSingleBeanCache();
            ChannelDao.clearAllCache();
        }

    }

    public void editWorkflowActionInfo( WorkflowStepAction action )
    {
        try
        {
            workflowDao.updateWorkflowAction( action );
        }
        finally
        {
            resetWorkflowActorBeanListCache();
            resetWorkflowSingleBeanCache();
            ChannelDao.clearAllCache();
        }
    }

    public void updateWorkflowStepCount( Long flowId )
    {
        try
        {
            mysqlEngine.beginTransaction();

            List stepList = workflowDao.queryWorkflowStepBeanListByFlowId( flowId );

            workflowDao.updateWorkflowStepCount( flowId, Integer.valueOf( stepList.size() ) );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            resetWorkflowActorBeanListCache();
            resetWorkflowSingleBeanCache();
            ChannelDao.clearAllCache();
        }

    }

    public void deleteWorkflowStep( List idList )
    {

        try
        {
            mysqlEngine.beginTransaction();

            long id = -1;

            for ( int i = 0; i < idList.size(); i++ )
            {
                id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );

                if( id < 0 )
                {
                    continue;
                }

                workflowDao.deleteWorkflowStepNotStartStep( Long.valueOf( id ) );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            resetWorkflowActorBeanListCache();
            resetWorkflowSingleBeanCache();
            ChannelDao.clearAllCache();
        }

    }

    public void deleteWorkflowStepAction( List idList )
    {

        try
        {
            mysqlEngine.beginTransaction();

            long id = -1;

            for ( int i = 0; i < idList.size(); i++ )
            {
                id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );

                if( id < 0 )
                {
                    continue;
                }

                workflowDao.deleteWorkflowStepAction( Long.valueOf( id ) );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            resetWorkflowActorBeanListCache();
            resetWorkflowSingleBeanCache();
            ChannelDao.clearAllCache();
        }

    }

    

    public List<WorkflowStepAction> getWorkflowActionForTag( String fId )
    {
        long flowId = StringUtil.getLongValue( fId, -1 );

        return workflowDao.queryWorkflowActionByFlowId( flowId );
    }

    public void setWorkflowUpdateDTInfo( Long flowId )
    {
        workflowDao.updateWorkflowUpdateDT( DateAndTimeUtil.clusterTimeMillis(), flowId );
    }

    public Object getWorkflowActionByfIdAndfsIdForTag( String fId, String fsId )
    {
        return workflowDao.queryWorkflowActionBeanByFlowIdAndStartStepId( StringUtil.getLongValue(
            fId, -1 ), StringUtil.getLongValue( fsId, -1 ) );
    }

    public void censorModelContentBatchMode( List<String> idList )
    {
        if( idList == null || idList.isEmpty() )
        {
            return;
        }

        WorkflowOperationBean opBean = null;

        WorkflowStepInfoBean stepBean = null;

        WorkflowStepActionBean actBean = null;

        Integer infoType = 1;

        String opIdStr = null;

        for ( String idv : idList )
        {
            opIdStr = idv.split( "-" )[1];

            opBean = workflowDao.querySingleWorkflowOperationBeanByOpId( StringUtil.getLongValue(
                opIdStr, -1 ) );

            if( opBean == null )
            {
                continue;
            }

            // 获取数据中实际info值,注意不能在以下update后获取,因为censor值会有暂时变换
            Map currentInfo = contentDao.querySingleContentMainInfo( opBean.getContentId() );

            if( currentInfo.isEmpty() )
            {
                continue;
            }

            ContentMainInfo info = new ContentMainInfo();

            info.setTitle( ( String ) currentInfo.get( "title" ) );

            info.setContentId( ( Long ) currentInfo.get( "contentId" ) );

            info.setAppearStartDateTime( ( Date ) currentInfo.get( "appearStartDateTime" ) );
            info.setAppearEndDateTime( ( Date ) currentInfo.get( "appearEndDateTime" ) );

            ContentClassBean classBean = channelDao
                .querySingleClassBeanInfoByClassId( ( Long ) currentInfo.get( "classId" ) );

            Map params = new HashMap();

            // contentAddStatus actionId flowTarget fromStepId toStepId

            stepBean = workflowDao.querySingleWorkflowStepBeanByStepId( opBean.getCurrentStep() );

            if( "publish".equals( opBean.getFlowTarget() ) )
            {
                actBean = workflowDao.querySingleWorkflowStepActionBean( stepBean.getPubDefActId() );

            }
            else if( "delete".equals( opBean.getFlowTarget() )
                || "offline".equals( opBean.getFlowTarget() ) )
            {
                actBean = workflowDao.querySingleWorkflowStepActionBean( stepBean
                    .getDeleteDefActId() );
            }

            if( actBean == null )
            {
                continue;
            }

            params.put( "actionId", actBean.getActionId().toString() );
            params.put( "flowTarget", opBean.getFlowTarget() );

            params.put( "fromStepId", actBean.getFromStepId().toString() );
            params.put( "toStepId", actBean.getToStepId().toString() );

            List execWorkflowActionList = new ArrayList( 5 );

            Integer endCensorState = ( Integer ) ContentService.getInstance().disposeWorkflowState(
                params, infoType, info.getContentId(), classBean, info, true,
                execWorkflowActionList, currentInfo )[0];

            if( execWorkflowActionList.size() == 1 )
            {
                String sysFlowSuggest = ( String ) params.get( "jtopcms_sys_flow_suguest" );

                String sysFlowEditSuggest = ( String ) params.get( "jtopcms_sys_flow_edit_suguest" );

                String sysNextStep = ( String ) params.get( "jtopcms_sys_flow_next_step" );

                String sysPrevStep = ( String ) params.get( "jtopcms_sys_flow_prev_step" );

                sendWorkflowMsg( execWorkflowActionList, info, sysFlowSuggest, sysFlowEditSuggest,
                    sysNextStep, sysPrevStep, "PC端", 1 );

            }

            info.setCensorState( endCensorState );

            if( "delete".equals( opBean.getFlowTarget() )
                && Constant.WORKFLOW.END_ACTION_ID_VALUE.equals( actBean.getToStepId() ) )
            {

                List cidList = StringUtil
                    .changeStringToList( opBean.getContentId().toString(), "," );

                SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                    .getEntry( ( Long ) currentInfo.get( "siteId" ) );

                ContentService.getInstance().deleteSystemAndUserDefineContentToTrash( site,
                    cidList, new ArrayList() );

                ContentService.getInstance().deleteSystemAndUserDefineContentToTrash( site,
                    ContentService.getInstance().retrieveLinkInfo( cidList ), new ArrayList() );
            }
            else if( "offline".equals( opBean.getFlowTarget() )
                && Constant.WORKFLOW.END_ACTION_ID_VALUE.equals( actBean.getToStepId() ) )
            {

                ContentService.getInstance().changeContentStatus( opBean.getContentId(),
                    Constant.WORKFLOW.CENSOR_STATUS_WITHDRAW );
            }

        }

        ContentDao.releaseAllCountCache();
        ContentService.releaseContentCache();

    }

    @SuppressWarnings( "unchecked" )
    private void sendWorkflowMsg( List execWorkflowActionList, ContentMainInfo mainInfo,
        String suggest, String editSuggest, String nextStepMan, String prevStepMan, String cMode,
        Integer infoType )
    {
        suggest = ( suggest == null ) ? "" : suggest;

        editSuggest = ( editSuggest == null ) ? "" : editSuggest;

        if( execWorkflowActionList.isEmpty() )
        {
            return;
        }

        String actSucc = ( ( String[] ) execWorkflowActionList.get( 0 ) )[1];

        WorkflowStepActionBean actBean = retrieveSingleWorkflowStepActionBean( StringUtil
            .getLongValue( ( ( String[] ) execWorkflowActionList.get( 0 ) )[0], -1 ) );

        String title = null;

        String info = null;

        List revUserList = null;

        String formStart = ( ( String[] ) execWorkflowActionList.get( 0 ) )[0];

        if( formStart != null && formStart.startsWith( "start:" ) )
        {
            String[] si = formStart.split( ":" );

            title = "内容工作流 ID : " + mainInfo.getContentId() + ", 进入 [开始] " + "审核";

            info = "标题为 \"" + mainInfo.getTitle() + "\" 的内容需要您参与审核。(请勿回复)"  
                + "\n\n修改建议： " + editSuggest;

            revUserList = new ArrayList( retrieveActorIdForStepOrg( StringUtil.getLongValue( si[1],
                -1 ), StringUtil.getLongValue( si[2], -1 ) ) );

            MessageService.getInstance().sendManagerMessage( Long.valueOf( -9999 ), "系统事件消息",
                title, info, revUserList );
        }
        else if( actBean != null )
        {
            WorkflowStepInfoBean stepBean = retrieveWorkflowStepBeanByFlowIdAndStep( actBean
                .getToStepId() );

            String stepOrActName = null;

            boolean allMode = false;

            if( Constant.COMMON.ON.equals( actBean.getConjunctManFlag() ) )
            {
                if( "1".equals( actSucc ) )
                {
                    stepOrActName = ( ( stepBean != null ) ? stepBean.getStepNodeName() : "" );

                    info = "标题为 \"" + mainInfo.getTitle() + "\" 的内容需要您参与审核,请处理。(请勿回复)"
                        + "\n\n修改建议： " + editSuggest;

                    revUserList = new ArrayList( retrieveActorIdForStepOrg( actBean.getFlowId(),
                        actBean.getToStepId() ) );
                }
                else
                {
                    allMode = true;

                    stepOrActName = actBean.getPassActionName();

                    info = "标题为 \"" + mainInfo.getTitle() + "\" 的内容需要您参与会签。(请勿回复)"
                        + "\n\n修改建议： " + editSuggest;

                    revUserList = new ArrayList( retrieveActorIdForStepOrg( actBean.getFlowId(),
                        actBean.getFromStepId() ) );
                }

            }
            else
            {

                stepOrActName = ( ( stepBean != null ) ? stepBean.getStepNodeName() : "" );

                info = "标题为 \"" + mainInfo.getTitle() + "\" 的内容需要您参与审核。(请勿回复)"  
                    + "\n\n修改建议： " + editSuggest;

                revUserList = new ArrayList( retrieveActorIdForStepOrg( actBean.getFlowId(),
                    actBean.getToStepId() ) );
            }

            // 发送系统消息

            // 2015-3:退稿仍然需要发送消息
            if( Constant.WORKFLOW.DRAFT_ACTION_ID_VALUE.equals( actBean.getToStepId() ) )
            {
                stepOrActName = "退稿";

                info = "标题为 \"" + mainInfo.getTitle() + "\" 的内容被退稿。(请勿回复)"  
                    + "\n\n修改建议： " + editSuggest;

                revUserList = new ArrayList( 1 );

                revUserList.add( SecurityService.getInstance().retrieveSingleSystemUserBean(
                    mainInfo.getCreator(), null ) );

                title = "您的内容 ID :" + mainInfo.getContentId() + ", 已进入 [" + stepOrActName + "] 状态";

                MessageService.getInstance().sendManagerMessage( Long.valueOf( -9999 ), "系统事件消息",
                    title, info, revUserList );

            }
            else
            {
                if( StringUtil.isStringNull( stepOrActName ) )
                {
                    title = "内容工作流 ID : " + mainInfo.getContentId() + " 已流转到终点";

                }
                else
                {
                    title = "内容工作流 ID : " + mainInfo.getContentId() + ", 进入 [" + stepOrActName
                        + "] " + ( allMode ? "会签" : "审核" );
                }

                MessageService.getInstance().sendManagerMessage( Long.valueOf( -9999 ), "系统事件消息",
                    title, info, revUserList );
            }

             

            // 指定下一步骤审批人
            if( StringUtil.isStringNotNull( nextStepMan ) )
            {
                Long userId = StringUtil
                    .getLongValue( nextStepMan.replaceAll( ",", "" ).trim(), -1 );

                ContentService.getInstance().changeWKOPerCurrentCensorMan( mainInfo.getContentId(),
                    userId, infoType );
            }

            // 记录每一步操作日志

            String fm =   "\n\n修改建议： " + editSuggest;

            ContentService.getInstance().addContentOperInfo( mainInfo.getContentId(),
                ( String ) SecuritySessionKeeper.getSecuritySession().getAuth().getApellation(),
                "(批量)" + actBean.getPassActionName(), fm, cMode, infoType );

        }
    }

    public Integer checkExistStepMan( Long userId, Long flowId )
    {
        List<Map> res = workflowDao.queryExistInStepCountByUserId( userId, flowId );

        Long stepId = null;

        List<Map> newRes = new ArrayList<Map>();

        WorkflowStepInfoBean step = null;

        for ( Map si : res )
        {
            stepId = ( Long ) si.get( "censorStep" );

            step = workflowDao.querySingleWorkflowStepBeanByStepId( stepId );

            if( "特权发布".equals( step.getStepDesc() ) )
            {
                newRes.add( si );
            }
        }

        return newRes.size();
    }

    public static void resetWorkflowActorBeanListCache()
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveWorkflowSingleBean" );
        cache.clearAllEntry();
    }

    public static void resetWorkflowSingleBeanCache()
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveWorkflowActorBeanList" );
        cache.clearAllEntry();
    }

}
