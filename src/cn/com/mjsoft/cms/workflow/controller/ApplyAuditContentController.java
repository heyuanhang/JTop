package cn.com.mjsoft.cms.workflow.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@Controller
@RequestMapping( "/workflow" )
public class ApplyAuditContentController
{
    private static WorkflowService workflowService = WorkflowService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/applyAudit.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "申请审核权", token = true )
    public String applyAudit( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();
        if( auth == null )
        {
            return "success";
        }

        Integer infoType = Integer.valueOf( 1 );

        if( StringUtil.isStringNotNull( ( String ) params.get( "infoType" ) ) )
        {
            infoType = StringUtil.getIntValue( ( String ) params.get( "infoType" ), -1 );
        }

        Long contentId = Long.valueOf( StringUtil.getLongValue(
            ( String ) params.get( "contentId" ), -1 ) );

        // 判断当前内容的所属栏目的工作流,以及当前步骤,是否有此用户,若有此用户,则锁定当前数据

        int status = workflowService.disposeOperationLockRequest( contentId, infoType, auth );

        if( status == 0 )
        {
            return ( "fail" );
        }

        return ( "success" );
    }

     
}
