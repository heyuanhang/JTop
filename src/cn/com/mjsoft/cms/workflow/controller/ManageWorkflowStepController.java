package cn.com.mjsoft.cms.workflow.controller;

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
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.workflow.dao.vo.WorkflowActor;
import cn.com.mjsoft.cms.workflow.dao.vo.WorkflowStepInfo;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/workflow" )
public class ManageWorkflowStepController
{
    private static WorkflowService workflowService = WorkflowService.getInstance();

    @RequestMapping( value = "/createWorkflowStep.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加工作流步骤", token = true )
    public ModelAndView createWorkflowStep( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        WorkflowStepInfo stepInfo = ( WorkflowStepInfo ) ServletUtil.getValueObject( request,
            WorkflowStepInfo.class );

        workflowService.addWorkflowStepInfoAndAuditActor( stepInfo, disposeStepParam( params,
            stepInfo.getFlowId() ) );

        // 更新工作流步骤记录
        workflowService.updateWorkflowStepCount( stepInfo.getFlowId() );

        // 更新工作流时间戳
        workflowService.setWorkflowUpdateDTInfo( stepInfo.getFlowId() );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );
        returnParams.put( "flowId", params.get( "flowId" ) );
        returnParams.put( "stepId", params.get( "stepId" ) );

        return ServletUtil.redirect( "/core/workflow/CreateWorkflowStep.jsp", returnParams );
    }

    @RequestMapping( value = "/editWorkflowStep.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑工作流步骤", token = true )
    public ModelAndView editWorkflowStep( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        WorkflowStepInfo stepInfo = ( WorkflowStepInfo ) ServletUtil.getValueObject( request,
            WorkflowStepInfo.class );

        workflowService.editWorkflowStepInfoAndAuditActor( stepInfo, disposeStepParam( params,
            stepInfo.getFlowId() ) );

        // 更新工作流步骤记录
        workflowService.updateWorkflowStepCount( stepInfo.getFlowId() );

        // 更新工作流时间戳
        workflowService.setWorkflowUpdateDTInfo( stepInfo.getFlowId() );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );
        returnParams.put( "flowId", params.get( "flowId" ) );
        returnParams.put( "stepId", params.get( "stepId" ) );

        return ServletUtil.redirect( "/core/workflow/EditWorkflowStep.jsp", returnParams );
    }

    @ResponseBody
    @RequestMapping( value = "/deleteWorkflowStep.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除工作流步骤", token = true )
    public String deleteWorkflowStep( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String flowId = ( String ) params.get( "flowId" );

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        workflowService.deleteWorkflowStep( idList );

        workflowService.setWorkflowUpdateDTInfo( Long
            .valueOf( StringUtil.getLongValue( flowId, -1 ) ) );

        return "success";

    }

    private List<WorkflowActor> disposeStepParam( Map params, Long flowId )
    {
        String[] checkOrgIds = StringUtil.split( ( ( String ) params.get( "checkOrgIds" ) ), "\\*" );

        String[] checkRoleIds = StringUtil.split( ( ( String ) params.get( "checkRoleIds" ) ),
            "\\*" );

        String[] checkUserIds = StringUtil.split( ( ( String ) params.get( "checkUserIds" ) ),
            "\\*" );

        List<WorkflowActor> actorBeanList = new ArrayList<WorkflowActor>();

        WorkflowActor workflowActor = null;

        // 选取的参与机构

        String orgId = null;

        for ( int i = 0; i < checkOrgIds.length; i++ )
        {
            orgId = checkOrgIds[i];

            workflowActor = new WorkflowActor();

            workflowActor.setFlowId( flowId );
            workflowActor.setAuditManId( Long.valueOf( StringUtil.getLongValue( orgId, -1 ) ) );
            workflowActor.setType( Constant.WORKFLOW.ORG_TYPE );

            actorBeanList.add( workflowActor );
        }

        // 选取的参与角色
        String checkRole = null;

        for ( int i = 0; i < checkRoleIds.length; i++ )
        {
            checkRole = checkRoleIds[i];
            workflowActor = new WorkflowActor();
            workflowActor.setFlowId( flowId );
            workflowActor.setAuditManId( Long.valueOf( StringUtil.getLongValue( checkRole, -1 ) ) );
            workflowActor.setType( Constant.WORKFLOW.ROLE_TYPE );

            actorBeanList.add( workflowActor );
        }

        String checkUser = null;
        // 选取的参与用户
        for ( int i = 0; i < checkUserIds.length; i++ )
        {
            checkUser = checkUserIds[i];
            workflowActor = new WorkflowActor();
            workflowActor.setFlowId( flowId );
            workflowActor.setAuditManId( Long.valueOf( StringUtil.getLongValue( checkUser, -1 ) ) );
            workflowActor.setType( Constant.WORKFLOW.USER_TYPE );

            actorBeanList.add( workflowActor );
        }

        return actorBeanList;
    }

}
