package cn.com.mjsoft.cms.workflow.html;

import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.bean.ContentMainInfoBean;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.guestbook.bean.GuestbookConfigBean;
import cn.com.mjsoft.cms.guestbook.service.GuestbookService;
import cn.com.mjsoft.cms.workflow.bean.WorkflowOperationBean;
import cn.com.mjsoft.cms.workflow.bean.WorkflowStepActionBean;
import cn.com.mjsoft.cms.workflow.dao.vo.WorkflowOperation;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientWorkflowActionUiHelpTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -2461173484950890882L;

    private static WorkflowService workflowService = WorkflowService.getInstance();

    private static ChannelService channelService = ChannelService.getInstance();

    private static ContentService contentService = ContentService.getInstance();

    private String classId;

    private String contentId;

    private String edit;

    @SuppressWarnings( "unchecked" )
    protected List returnObjectList()
    {
        Integer infoType = Constant.WORKFLOW.INFO_TYPE_CONTENT;// 栏目内容

        if( contentId != null && contentId.startsWith( "gb:" ) )
        {
            infoType = Constant.WORKFLOW.INFO_TYPE_GB;// 留言内容

            contentId = StringUtil.replaceString( contentId, "gb:", "" );
        }

        List<WorkflowStepActionBean> result = null;

        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        if( session != null && session.getAuth() != null )
        {
            Long userId = ( Long ) session.getAuth().getIdentity();

            Long contentIdVar = Long.valueOf( StringUtil.getLongValue( contentId, -1 ) );

            boolean needFlowStartAction = false;
            boolean needEditAction = false;

            // 存在cotentId意味着为编辑审核模式
            if( contentIdVar.longValue() > 0 )
            {
                // 有ID,则看是否在工作流中,且当前登陆者是否可有审核权
                if( workflowService.checkCurrentLoginUserHaveStepActionForSingleAuditContent(
                    userId, contentIdVar, infoType ) )
                {
                    // 在工作流中,取当前工作流动作所有步骤
                    // TODO 需要放入session,防止不合法的工作流
                    result = workflowService.retrieveWorkflowStepBeanListByClassId( contentIdVar,
                        infoType );

                    String flowTarget = workflowService.retrieveSingleWorkflowOperationBean(
                        contentIdVar, infoType ).getFlowTarget();

                    List<WorkflowStepActionBean> flowStepAction = new ArrayList<WorkflowStepActionBean>();

                    for ( WorkflowStepActionBean sab : result )
                    {
                        if( infoType == 1 )
                        {
                            if( "publish".equals( flowTarget ) )
                            {
                                if( !Constant.WORKFLOW.REJECT_ACTION_ID_VALUE.equals( sab
                                    .getToStepId() ) )
                                {
                                    flowStepAction.add( sab );
                                }
                            }
                            else if( "offline".equals( flowTarget ) )
                            {
                                if( !Constant.WORKFLOW.DRAFT_ACTION_ID_VALUE.equals( sab
                                    .getToStepId() ) )
                                {
                                    flowStepAction.add( sab );
                                }
                            }
                            else if( "delete".equals( flowTarget ) )
                            {
                                if( !Constant.WORKFLOW.DRAFT_ACTION_ID_VALUE.equals( sab
                                    .getToStepId() ) )
                                {
                                    flowStepAction.add( sab );
                                }
                            }
                        }
                        else if( infoType == 2 )
                        {
                            if( "reply".equals( flowTarget ) )
                            {
                                
                                
                                if( !Constant.WORKFLOW.REJECT_ACTION_ID_VALUE.equals( sab
                                   .getToStepId() ) )
                                {
                                    flowStepAction.add( sab );
                                }
                            }
                            /*
                             * else if( "open".equals( flowTarget ) ) { if(
                             * !Constant.WORKFLOW.DRAFT_ACTION_ID_VALUE.equals(
                             * sab .getToStepId() ) ) { flowStepAction.add( sab ); } }
                             */
                            else if( "delete".equals( flowTarget ) )
                            {
                                if( !Constant.WORKFLOW.DRAFT_ACTION_ID_VALUE.equals( sab
                                    .getToStepId() ) )
                                {
                                    flowStepAction.add( sab );
                                }
                            }
                        }
                    }

                    result = flowStepAction;

                }
                else
                {
                    Integer censorStatus = null;

                    if( infoType == 1 )
                    {
                        censorStatus = contentService.retrieveCensorStateByContentId( contentIdVar );

                    }
                    else if( infoType == 2 )
                    {
                        censorStatus = ( Integer ) GuestbookService.getInstance()
                            .retrieveSingleGuestbookInfoMapByGbId( contentIdVar ).get( "isCensor" );
                    }

                    // 不在工作流审核状态中,当前内容且不在审核状态,重新从开始进入

                    if( Constant.WORKFLOW.CENSOR_STATUS_IN_EDIT.equals( censorStatus ) )
                    {
                        // 已经是重新编辑状态,准备进入工作流第一步
                        needFlowStartAction = true;
                    }

                    else if( !Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW.equals( censorStatus ) )
                    {
                        // 当前内容不在工作流处理中,为待发或已发布状态,说明已经通过审核过的内容,需要进入重新编辑状态
                        needEditAction = true;
                    }

                    else if( infoType == 2 )
                    {
                        WorkflowOperationBean wfOperBean = workflowService
                            .retrieveSingleWorkflowOperationBean( contentIdVar, 2 );

                        // 留言的状态特殊，0为初始状态
                        if( wfOperBean == null
                            && Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW.equals( censorStatus ) )
                        {
                            needEditAction = true;
                        }

                    }
                }
            }

            Long classIdVar = Long.valueOf( StringUtil.getLongValue( classId, -1 ) );

            long workflowId = -1l;

            if( infoType == 1 )
            {
                ContentClassBean classBean = channelService
                    .retrieveSingleClassBeanInfoByClassId( classIdVar );

                workflowId = classBean.getWorkflowId();
            }
            else if( infoType == 2 )
            {
                GuestbookConfigBean gbCfgBean = GuestbookService.getInstance()
                    .retrieveSingleGuestbookConfigBeanByConfigId( classIdVar );

                workflowId = ( gbCfgBean != null ) ? gbCfgBean.getWorkflowId() : -1l;
            }

            // 以上逻辑没有达成,进入以下处理逻辑
            if( contentIdVar.longValue() < 0 || needFlowStartAction || needEditAction )
            {
                // 不在工作流中的情况处理

                // TODO 需要放入session,防止不合法的工作流
                if( workflowId > 0 )
                {
                    // 说明当前栏目有工作流,取起始动作的所有下一步骤
                    if( needEditAction )
                    {
                        if( "true".equals( edit ) )
                        {
                            result = new ArrayList();

                            if( infoType == 1 )
                            {
                                // 重新编辑
                                WorkflowStepActionBean pubActionBean = new WorkflowStepActionBean();

                                pubActionBean.setPassActionName( "进入发布审批流程" );
                                pubActionBean
                                    .setFromStepId( Constant.WORKFLOW.RE_EDIT_ACTION_ID_VALUE );
                                pubActionBean.setToStepId( Constant.WORKFLOW.START_ACTION_ID_VALUE );

                                result.add( pubActionBean );

                                pubActionBean = new WorkflowStepActionBean();

                                pubActionBean.setPassActionName( "进入删除审批流程" );
                                pubActionBean
                                    .setFromStepId( Constant.WORKFLOW.RE_EDIT_ACTION_ID_VALUE );
                                pubActionBean.setToStepId( Constant.WORKFLOW.START_ACTION_ID_VALUE );

                                result.add( pubActionBean );

//                                pubActionBean = new WorkflowStepActionBean();
//
//                                pubActionBean.setPassActionName( "进入下线审批流程" );
//                                pubActionBean
//                                    .setFromStepId( Constant.WORKFLOW.RE_EDIT_ACTION_ID_VALUE );
//                                pubActionBean.setToStepId( Constant.WORKFLOW.START_ACTION_ID_VALUE );
//
//                                result.add( pubActionBean );

                                Integer inStep = workflowService.checkExistStepMan( userId,
                                    workflowId );

                                if( inStep > 0 )
                                {
                                    ContentMainInfoBean mainInfo = contentService.retrieveSingleContentMainInfoBean( contentIdVar );
                                    
                                    String userName =  ( String ) session.getAuth().getApellation();
                                   
                                    if( !userName.equals( mainInfo.getCreator() ) )
                                    {
                                        pubActionBean = new WorkflowStepActionBean();

                                        pubActionBean.setPassActionName( "直接发布" );
                                        pubActionBean
                                            .setFromStepId( Constant.WORKFLOW.RE_EDIT_ACTION_ID_VALUE );
                                        pubActionBean.setToStepId( Long.valueOf( 99999 ) );

                                        result.add( pubActionBean );
                                    }
                                    
                                   
                                }
                            }
                            else if( infoType == 2 )
                            {
                                // 重新编辑
                                WorkflowStepActionBean pubActionBean = new WorkflowStepActionBean();

                                pubActionBean.setPassActionName( "进入回复审批流程" );
                                pubActionBean
                                    .setFromStepId( Constant.WORKFLOW.RE_EDIT_ACTION_ID_VALUE );
                                pubActionBean.setToStepId( Constant.WORKFLOW.START_ACTION_ID_VALUE );

                                result.add( pubActionBean );

                                /*
                                 * pubActionBean = new WorkflowStepActionBean();
                                 * 
                                 * pubActionBean.setPassActionName( "进入公开流程" );
                                 * pubActionBean .setFromStepId(
                                 * Constant.WORKFLOW.RE_EDIT_ACTION_ID_VALUE );
                                 * pubActionBean.setToStepId(
                                 * Constant.WORKFLOW.START_ACTION_ID_VALUE );
                                 * 
                                 * result.add( pubActionBean );
                                 */

                                pubActionBean = new WorkflowStepActionBean();

                                pubActionBean.setPassActionName( "进入删除审批流程" );
                                pubActionBean
                                    .setFromStepId( Constant.WORKFLOW.RE_EDIT_ACTION_ID_VALUE );
                                pubActionBean.setToStepId( Constant.WORKFLOW.START_ACTION_ID_VALUE );

                                result.add( pubActionBean );

                            }
                        }
                    }
                    else
                    {
                        // 添加内容时,开始步骤动作
                        result = workflowService
                            .retrieveWorkflowStartActionBeanByFlowId( workflowId );
                    }
                }
                else
                {
                    result = new ArrayList<WorkflowStepActionBean>();

                    if( infoType == 1 )
                    {
                        // 没有工作流,默认发布
                        WorkflowStepActionBean pubActionBean = new WorkflowStepActionBean();
                        pubActionBean.setPassActionName( "发布内容" );
                        pubActionBean.setToStepId( Constant.WORKFLOW.END_ACTION_ID_VALUE );

                        result.add( pubActionBean );

//                        pubActionBean = new WorkflowStepActionBean();
//                        pubActionBean.setPassActionName( "下线内容" );
//                        pubActionBean.setToStepId( Constant.WORKFLOW.END_ACTION_ID_VALUE );
//
//                        result.add( pubActionBean );

                    }
                    else if( infoType == 2 )
                    {
                        // 没有工作流,默认发布
                        WorkflowStepActionBean pubActionBean = new WorkflowStepActionBean();
                        pubActionBean.setPassActionName( "回复内容" );
                        pubActionBean.setToStepId( Constant.WORKFLOW.END_ACTION_ID_VALUE );

                        result.add( pubActionBean );
                    }

                }

            }
            else
            {
                // result = workflowService
                // .retrieveWorkflowActionBeanByFlowIdAndFromStepId( classBean
                // .getWorkflowId(),
                // Constant.WORKFLOW.START_ACTION_ID_VALUE );
            }
        }

        return result;
    }

    protected String returnPutValueName()
    {
        return "Action";
    }

    protected void initTag()
    {

    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected Object returnSingleObject()
    {
        return null;
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
    }

    public void setClassId( String classId )
    {
        this.classId = classId;
    }

    public void setContentId( String contentId )
    {
        this.contentId = contentId;
    }

    public void setEdit( String edit )
    {
        this.edit = edit;
    }
}
