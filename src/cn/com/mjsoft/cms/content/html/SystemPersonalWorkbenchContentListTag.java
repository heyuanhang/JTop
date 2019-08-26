package cn.com.mjsoft.cms.content.html;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.guestbook.service.GuestbookService;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemPersonalWorkbenchContentListTag extends TagSupport
{
    private static final long serialVersionUID = 7437245711008571325L;

    private static ContentService contentService = ContentService.getInstance();

    private String personalReject;

    private String mode = "content";

    private String pn = "1";

    private String size = "5000";

    public int doStartTag() throws JspException
    {
        // 获取系统登陆用户信息
        Auth sysAuth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( sysAuth == null || !sysAuth.isAuthenticated() )
        {
            return EVAL_BODY_INCLUDE;
        }
        else
        {

            // String userName = ( String ) sysAuth.getApellation();

            // workflowService.retrieveWorkflowActorBeanList( flowId );

            int pageNum = StringUtil.getIntValue( pn, 1 );

            int pageSize = StringUtil.getIntValue( size, 12 );

            Page pageInfo = null;

            Integer count = null;

            if( "true".equals( personalReject ) )
            {
                List SPInfoList = contentService
                    .retrieveInWorkflowUserDefinePersonalRejectContent( sysAuth );

                pageContext.setAttribute( "SPInfoList", SPInfoList );

                return EVAL_BODY_INCLUDE;
            }
            else
            {
                List SPInfoList = null;

                if( "content".equals( mode ) )
                {

                    count = contentService.retrieveAllInWorkflowUserDefineContent( sysAuth,
                        Long.valueOf( 0 ), Integer.valueOf( 10000 ) ).size();

                    pageInfo = new Page( pageSize, count, pageNum );

                    // 取所有在工作流中的内容
                    SPInfoList = contentService.retrieveAllInWorkflowUserDefineContent( sysAuth,
                        Long.valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
                }
                else if( "gb".equals( mode ) )
                {

                    pageInfo = new Page( pageSize, GuestbookService.getInstance()
                        .retrieveAllInWorkflowUserGuestbookContent( sysAuth, 0l, 100000 ).size(),
                        pageNum );

                    // 取所有在工作流中的内容
                    SPInfoList = GuestbookService.getInstance()
                        .retrieveAllInWorkflowUserGuestbookContent( sysAuth,
                            Long.valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
                }

                pageContext.setAttribute( "SPInfoList", SPInfoList );

                this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );

                return EVAL_BODY_INCLUDE;
            }
        }
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "SPInfoList" );
        return EVAL_PAGE;
    }

    public void setPersonalReject( String personalReject )
    {
        this.personalReject = personalReject;
    }

    public void setPn( String pn )
    {
        this.pn = pn;
    }

    public void setSize( String size )
    {
        this.size = size;
    }

    public void setMode( String mode )
    {
        this.mode = mode;
    }

}
