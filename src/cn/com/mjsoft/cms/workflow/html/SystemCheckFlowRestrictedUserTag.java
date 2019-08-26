package cn.com.mjsoft.cms.workflow.html;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemCheckFlowRestrictedUserTag extends TagSupport
{
    private static final long serialVersionUID = 8104750388286587258L;
    
    private static WorkflowService workflowService = WorkflowService
        .getInstance();

    private String contentId;

    public int doStartTag() throws JspException
    {
        Integer infoType = Constant.WORKFLOW.INFO_TYPE_CONTENT;
        
        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();
        if( auth == null )
        {
            // TOTO 没有权限,不是系统用户什么都不做,以后需要做跳转到 无权限审核页面
            try
            {
                this.pageContext.getOut().write( "当前内容已由其他编辑获得审核权" );
            }
            catch ( IOException e )
            {
               
                e.printStackTrace();
            }
            return SKIP_BODY;
        }
        else
        {
            boolean checkOk = workflowService.checkFlowRestrictedUser( auth,
                Long.valueOf( StringUtil.getLongValue( contentId, -1 ) ),infoType );

            if( checkOk )
            {
                return EVAL_PAGE;
            }
            else
            {
                try
                {
                    this.pageContext.getOut().write( "当前内容已由其他编辑获得审核权" );
                }
                catch ( IOException e )
                {
                   
                    e.printStackTrace();
                }
                return SKIP_BODY;
            }
        }

    }

    public void setContentId( String contentId )
    {
        this.contentId = contentId;
    }

}
