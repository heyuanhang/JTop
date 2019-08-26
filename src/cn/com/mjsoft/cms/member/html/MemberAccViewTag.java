package cn.com.mjsoft.cms.member.html;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.member.bean.MemberAccRuleBean;
import cn.com.mjsoft.cms.member.bean.MemberBean;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.security.Role;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.handler.view.DefaultResultHandler;

public class MemberAccViewTag extends TagSupport
{

    private static final long serialVersionUID = -8157496028599513805L;

    private static DefaultResultHandler resultHandler = new DefaultResultHandler();

    private static SecurityService securityService = SecurityService
        .getInstance();

    private String classId = "-1";

    private String jump = "";
    
    private boolean mustSkip = false;

    public int doStartTag() throws JspException
    {

        // 发布时跳过
        HttpServletRequest request = ( HttpServletRequest ) pageContext
            .getRequest();

        if( !Boolean.TRUE.equals( request
            .getAttribute( Constant.CONTENT.HTML_PUB_ACTION_FLAG ) ) )
        {

            Long cid = Long.valueOf( StringUtil.getLongValue( classId, -1 ) );

            MemberAccRuleBean acBean = null;

            if( cid.longValue() > 0 )
            {
                acBean = securityService.retrieveSingleMemberAccRule( cid );
            }

            if( acBean != null && acBean.getAccRuleId().longValue() > 0 )
            {
                SecuritySession session = SecuritySessionKeeper
                    .getSecuritySession();

                if( session == null || session.getAuth() == null
                    || session.isManager() || session.getMember() == null )
                {
                    mustSkip = true;
                    
                    jumpAccFailPage();

                }
                else
                {

                    Set authorizationRoleIdSet = acBean.getRoleIdSet();

                    Role[] rs = session.getAuth().getUserRole();

                    boolean accRoleOk = false;

                    for ( int i = 0; i < rs.length; i++ )
                    {
                        if( authorizationRoleIdSet.contains( ( ( Role ) rs[i] )
                            .getRoleID() ) )
                        {
                            accRoleOk = true;
                            break;
                        }
                    }

                    MemberBean member = ( MemberBean ) session.getMember();

                    int lever = member.getMemLevel().intValue();

                    long score = member.getScore().longValue();

                    // 确定是否可访问
                    boolean accLevelOk = ( lever >= acBean.getMinLever()
                        .intValue() ) ? true : false;

                    boolean accScoreOk = ( score >= acBean.getMinScore()
                        .longValue() ) ? true : false;

                    if( acBean.getEft().intValue() == 1 )
                    {
                        if( !( accRoleOk || accLevelOk || accScoreOk ) )
                        {
                            mustSkip = true;
                            
                            jumpAccFailPage();
                        }
                    }
                    else
                    {
                        if( !( accRoleOk && accLevelOk && accScoreOk ) )
                        {
                            mustSkip = true;
                            
                            jumpAccFailPage();
                        }
                    }
                }
            }

        }

        return EVAL_BODY_INCLUDE;

    }

    private void jumpAccFailPage()
    {
        HttpServletRequest request = ( HttpServletRequest ) pageContext
            .getRequest();

        HttpServletResponse response = ( HttpServletResponse ) pageContext
            .getResponse();

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
            .getSecuritySession().getCurrentLoginSiteInfo();

        if( site == null || site.getSiteId().longValue() < 0 )
        {
            site = ( SiteGroupBean ) this.pageContext.getRequest()
                .getAttribute( "SiteObj" );

            if( site == null )
            {
                site = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                        .getRequest() );
            }
        }

        resultHandler.resolveCustomDirectResult( site.getSiteUrl() + jump,
            request, response, true, null );
    }

    public int doEndTag() throws JspException
    {
        if( mustSkip )
        {
            mustSkip  =false;   
            
            classId = "";
            
            jump = "";
           
            return SKIP_PAGE;
        }
        else
        {
            mustSkip  =false;   
            
            classId = "";
            
            jump = "";  
            
            return EVAL_PAGE;
        }
    }

    public void setClassId( String classId )
    {
        this.classId = classId;
    }

    public void setJump( String jump )
    {
        this.jump = jump;
    }

}
