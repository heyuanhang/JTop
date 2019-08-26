package cn.com.mjsoft.cms.guestbook.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.guestbook.bean.GuestbookConfigBean;
import cn.com.mjsoft.cms.guestbook.service.GuestbookService;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.workflow.bean.WorkflowBean;
import cn.com.mjsoft.cms.workflow.bean.WorkflowOperationBean;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/guestbook" )
public class ReplyAndManageGuestbookInfoController
{
    private static GuestbookService gbService = GuestbookService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/replayGbInfo.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "回复留言", token = true )
    public String replayGbInfo( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        Auth auth = session.getAuth();

        Long gbId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "gbId" ), -1 ) );

        String replyText = SystemSafeCharUtil.filterHTMLNotApos( ( String ) params
            .get( "replyText" ) );

        List wfActionList = new ArrayList();

        gbService.replyGuestbook( params, gbId, replyText, ( String ) auth.getApellation(),
            wfActionList );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/changeStatus.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "切换留言状态", token = true )
    public String changeStatus( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "id" );

        Integer flag = Integer
            .valueOf( StringUtil.getIntValue( ( String ) params.get( "flag" ), 0 ) );

        String action = ( String ) params.get( "action" );

        gbService.editGuestbookStatus( StringUtil.changeStringToList( ids, "," ), action, flag );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/sendReplyMsg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "发送邀请留言信件" )
    public String sendReplyMsg( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        Auth auth = session.getAuth();

        Long userId = StringUtil.getLongValue( ( String ) params.get( "userId" ), 0 );

        Long gbId = StringUtil.getLongValue( ( String ) params.get( "gbId" ), -1 );

        gbService.sendNeedReplyMessage( ( Long ) auth.getIdentity(), userId, gbId );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/deleteGb.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除留言", token = true )
    public String deleteGb( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "id" );

        String cfgFlag = ( String ) params.get( "cfgFlag" );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        gbService.deleteGuestbookAllInfoByIds( StringUtil.changeStringToList( ids, "," ), gbService
            .retrieveSingleGuestbookConfigBeanByConfigFlag( cfgFlag ), site );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/checkWfStatus.do", method = { RequestMethod.POST } )
    public Object checkWfStatus( HttpServletRequest request, HttpServletResponse response )
        throws InstantiationException, IllegalAccessException
    {
        Integer infoType = 2;

        /**
         * 获取栏目信息
         */
        // 由于存在一些字段允许html,所以不能在这里先使用getHttpRequestSnapshot(),故使用普通方式获取classId
        Long cfgId = StringUtil.getLongValue( request.getParameter( "classId" ), -1 );

        GuestbookConfigBean cfgBean = gbService.retrieveSingleGuestbookConfigBeanByConfigId( cfgId );

        if( cfgBean == null || cfgBean.getConfigId() < 0 )
        {
            throw new FrameworkException( "留言版配置信息丢失  cfgId:" + cfgId );
        }

        /**
         * 获取对应数据模型元数据
         */

        List filedBeanList = MetaDataService.getInstance().retrieveModelFiledInfoBeanList(
            cfgBean.getInfoModelId() );

        // 处理复制内容,检查复制栏目是否模型一致

        // 处理站点共享内容

        // 处理相关内容

        // 处理推荐内容 ,推荐内容ID不在main-info里

        /**
         * 根据不同模型进入处理,现系统模型和自定义模型分开
         */

        Map returnParams = new HashMap();

        returnParams.put( "modelId", cfgBean.getInfoModelId() );
        returnParams.put( "classId", cfgId );

        Map params = ServletUtil.getRequestInfo( request, ServiceUtil
            .checkEditorField( filedBeanList ) );

        Long fromStepId = StringUtil.getLongValue( ( String ) params.get( "fromStepId" ), -1 );

        Long toStepId = StringUtil.getLongValue( ( String ) params.get( "toStepId" ), -1 );

        Long contentId = StringUtil.getLongValue( ( String ) params
            .get( Constant.METADATA.CONTENT_ID_NAME ), -1 );

        // 存在工作流要检查工作流状态
        if( cfgBean.getWorkflowId().longValue() > 0 )
        {
            // 工作流信息丢失检查
            WorkflowBean wkBean = WorkflowService.getInstance().retrieveSingleWorkflowBean(
                cfgBean.getWorkflowId() );

            WorkflowOperationBean wopBean = WorkflowService.getInstance()
                .retrieveSingleWorkflowOperationBean( contentId, infoType );

            if( wopBean != null )
            {
                if( !wkBean.getUpdateDT().equals( wopBean.getWkUpdateDT() ) )
                {
                    // 丢失或更动了工作流信息

                    WorkflowService.getInstance().resetWorkflowOperationToStartStepByFlowId(
                        contentId, infoType, wkBean.getFlowId() );

                    return "wf-update";
                }
            }

            // 工作流状态异常检查
            boolean wfOk = WorkflowService.getInstance().checkWorkflowStatus( fromStepId, toStepId,
                contentId, infoType );

            if( !wfOk )
            {

                Map gbInfo = gbService.retrieveSingleGuestbookInfoMapByGbId( contentId );

                if( Constant.WORKFLOW.CENSOR_STATUS_DRAFT.equals( gbInfo.get( "isCensor" ) )
                    && Constant.WORKFLOW.END_ACTION_ID_VALUE.equals( fromStepId )
                    && Constant.WORKFLOW.END_ACTION_ID_VALUE.equals( toStepId ) )
                {
                    return "wf-draft";
                }

                return "wf-empty";
            }

            // 工作流个人回避检查

            wfOk = WorkflowService.getInstance().checkWorkflowAvoidStatus( fromStepId, toStepId,
                contentId );

            if( !wfOk )
            {
                return "wf-avoid";
            }
        }

        return "wf-ok";

    }

}
