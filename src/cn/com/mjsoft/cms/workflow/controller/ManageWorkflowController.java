package cn.com.mjsoft.cms.workflow.controller;

import java.util.HashMap;
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
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.workflow.dao.vo.Workflow;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/workflow" )
public class ManageWorkflowController
{
    private static WorkflowService workflowService = WorkflowService.getInstance();

    @RequestMapping( value = "/createWorkflow.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加工作流", token = true )
    public ModelAndView createWorkflow( HttpServletRequest request, HttpServletResponse response )
    {
        Workflow workflow = ( Workflow ) ServletUtil.getValueObject( request, Workflow.class );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();
        if( auth != null )
        {
            String currentSystemUserName = ( String ) auth.getApellation();

            if( StringUtil.isStringNotNull( currentSystemUserName ) )
            {
                workflow.setCreator( ( String ) auth.getApellation() );
            }
            else
            {
                workflow.setCreator( "" );
            }
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        workflow.setSiteId( site.getSiteId() );

        workflowService.addWorkflowInfo( workflow );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/workflow/CreateWorkflow.jsp", returnParams );
    }

    @RequestMapping( value = "/editWorkflow.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑工作流", token = true )
    public ModelAndView editWorkflow( HttpServletRequest request, HttpServletResponse response )
    {
        Workflow workflow = ( Workflow ) ServletUtil.getValueObject( request, Workflow.class );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();
        if( auth != null )
        {
            String currentSystemUserName = ( String ) auth.getApellation();

            if( StringUtil.isStringNotNull( currentSystemUserName ) )
            {
                workflow.setCreator( ( String ) auth.getApellation() );
            }
            else
            {
                workflow.setCreator( "" );
            }
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        workflow.setSiteId( site.getSiteId() );

        workflowService.editWorkflowInfo( workflow );

        Map returnParam = new HashMap();

        returnParam.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/workflow/EditWorkflow.jsp", returnParam );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteWorkflow.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除工作流", token = true )
    public String deleteWorkflow( HttpServletRequest request, HttpServletResponse response )
    {
        Integer infoType = Constant.WORKFLOW.INFO_TYPE_CONTENT;

        Map params = ServletUtil.getRequestInfo( request );

        String allSelectedIds = ( String ) params.get( "allSelectedIds" );

        workflowService
            .deleteWorkflowAllInfo( StringUtil.changeStringToList( allSelectedIds, "," ) ,infoType);

        return "success";

    }

}
