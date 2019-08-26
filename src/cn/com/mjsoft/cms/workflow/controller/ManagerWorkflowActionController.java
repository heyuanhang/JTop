package cn.com.mjsoft.cms.workflow.controller;

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

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.workflow.dao.vo.WorkflowStepAction;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/workflow" )
public class ManagerWorkflowActionController
{
    private static WorkflowService workflowService = WorkflowService.getInstance();

    @RequestMapping( value = "/createWorkflowAction.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加工作流动作", token = true )
    public ModelAndView createWorkflowAction( HttpServletRequest request,
        HttpServletResponse response )
    {

        WorkflowStepAction action = ( WorkflowStepAction ) ServletUtil.getValueObject( request,
            WorkflowStepAction.class );

        workflowService.addNewWorkflowActionInfo( action );

        // 更新工作流时间戳
        workflowService.setWorkflowUpdateDTInfo( action.getFlowId() );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/workflow/CreateWorkflowAction.jsp", returnParams );

    }

    @RequestMapping( value = "/editWorkflowAction.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑工作流动作", token = true )
    public ModelAndView editWorkflowAction( HttpServletRequest request, HttpServletResponse response )
    {

        WorkflowStepAction action = ( WorkflowStepAction ) ServletUtil.getValueObject( request,
            WorkflowStepAction.class );

        workflowService.editWorkflowActionInfo( action );

        // 更新工作流时间戳
        workflowService.setWorkflowUpdateDTInfo( action.getFlowId() );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/workflow/EditWorkflowAction.jsp", returnParams );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteWorkflowAction.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除工作流动作", token = true )
    public String deleteWorkflow( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String flowId = ( String ) params.get( "flowId" );

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        workflowService.deleteWorkflowStepAction( idList );

        // 更新工作流时间戳
        workflowService.setWorkflowUpdateDTInfo( Long
            .valueOf( StringUtil.getLongValue( flowId, -1 ) ) );

        return "success";

    }
}
