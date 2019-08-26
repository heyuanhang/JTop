package cn.com.mjsoft.cms.workflow.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/workflow" )
public class AuditContentController
{
    private static Logger log = Logger.getLogger( AuditContentController.class );

    private static WorkflowService workflowService = WorkflowService.getInstance();

    @RequestMapping( value = "/applyContent.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "工作流审查", token = true )
    public ModelAndView applyAudit( HttpServletRequest request, HttpServletResponse response )
    {
        
        Integer infoType = Constant.WORKFLOW.INFO_TYPE_CONTENT;

        Map params = ServletUtil.getRequestInfo( request );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();
        if( auth == null || !auth.isAuthenticated() )
        {
            // 没有权限,不是系统用户什么都不做,需要跳转

            Map returnParams = new HashMap();

            returnParams.put( "fromFlow", Boolean.TRUE );

            return ServletUtil.redirect( "/core/content/AuditContentList.jsp", returnParams );
        }

        String auditActionName = ( String ) params.get( "actionFlag" );

        Long contentId = Long.valueOf( StringUtil.getLongValue(
            ( String ) params.get( "contentId" ), -1 ) );

        // 非正常请求
        if( !Constant.WORKFLOW.PASS_ACT.equals( auditActionName )
            && !Constant.WORKFLOW.REJECT_ACT.equals( auditActionName ) )
        {
            log.info( "[AuditContentFlow] 审核命令异常,auditActionName:" + auditActionName );

            Map returnParams = new HashMap();

            returnParams.put( "fromFlow", Boolean.TRUE );

            return ServletUtil.redirect( "/core/content/AuditContentList.jsp", returnParams );
        }

        // pass action 的情况
        int status = workflowService.disposeAuditActionRequest( contentId, infoType,( Long ) auth
            .getIdentity(), auditActionName );

        if( status == -1 )
        {
            // 无法取得审核操作数据的条件则意味着已经被人处理
            throw new FrameworkException( "当前内容已经被处理过!请查看对应的审核处理记录" );
        }

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/content/AuditContentList.jsp", returnParams );
    }

}
