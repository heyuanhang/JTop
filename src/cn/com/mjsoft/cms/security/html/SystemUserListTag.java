package cn.com.mjsoft.cms.security.html;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.security.bean.SystemUserBean;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemUserListTag extends TagSupport
{
    private static Logger log = Logger.getLogger( SystemUserListTag.class );

    private static final long serialVersionUID = 3673126269216097853L;

    private static SecurityService securityService = SecurityService
        .getInstance();

    private String roleId = "";

    private String allMode = "false";

    private String orgCode = "";

    private String manage = "false";

    private String pn = "";

    private String size = "";

    private String queryName = "";

    // 此role中包含的用户不再选出
    private String excludeUserRoleId = "";

    @SuppressWarnings( "unchecked" )
    public int doStartTag() throws JspException
    {
        // 需要权限,只有登陆管理员且有权限才可查看
        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        List systeUserList = null;

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 10 );

        Page pageInfo = null;

        if( !"".equals( queryName ) )
        {

            systeUserList = securityService.retrieveAllSystemUserBean( null,
                null, ( String ) session.getAuth().getOrgCode() );

            SystemUserBean userBean = null;

            List queryList = new ArrayList();

            for ( int i = 0; i < systeUserList.size(); i++ )
            {
                userBean = ( SystemUserBean ) systeUserList.get( i );

                if( userBean.getUserName().equals( queryName ) )
                {
                    queryList.add( userBean );

                    break;
                }
            }

            systeUserList = queryList;

        }
        else if( StringUtil.isStringNotNull( roleId ) )
        {
            Long roleIdVar = Long
                .valueOf( StringUtil.getLongValue( roleId, -1 ) );

            systeUserList = securityService.retrieveAllSystemUserBean(
                roleIdVar, null, ( String ) session.getAuth().getOrgCode() );
        }
        else if( "true".equals( allMode ) )
        {
            systeUserList = securityService.retrieveAllSystemUserBean();
        }
        else if( "true".equals( manage ) )
        {
            List temp = null;

            if( StringUtil.isStringNull( orgCode ) )
            {
                orgCode = ( String ) session.getAuth().getOrgCode();

                temp = securityService
                    .retrieveAllSystemUserBeanByRelateOrg( orgCode );

                pageInfo = new Page( pageSize, temp.size(), pageNum );

                systeUserList = securityService
                    .retrieveAllSystemUserBeanByRelateOrg( orgCode, Long
                        .valueOf( pageInfo.getFirstResult() ), Integer
                        .valueOf( pageSize ) );
            }
            else
            {
                temp = securityService
                    .retrieveAllSystemUserBeanByOrgCode( orgCode );

                pageInfo = new Page( pageSize, temp.size(), pageNum );

                systeUserList = securityService
                    .retrieveAllSystemUserBeanByOrgCode( orgCode, Long
                        .valueOf( pageInfo.getFirstResult() ), Integer
                        .valueOf( pageSize ) );
            }

            this.pageContext.setAttribute( "___system_dispose_page_object___",
                pageInfo );
        }
        else if( StringUtil.isStringNotNull( orgCode ) )
        {

            systeUserList = securityService
                .retrieveAllSystemUserBeanByOrgCode( orgCode );

        }
        else
        {
            Long excludeVar = Long.valueOf( StringUtil.getLongValue(
                excludeUserRoleId, -1 ) );

            systeUserList = securityService.retrieveAllSystemUserBean( null,
                excludeVar, ( String ) session.getAuth().getOrgCode() );

        }

        pageContext.setAttribute( "systemUserList", systeUserList );

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "systemUserList" );
        return EVAL_PAGE;
    }

    public void setRoleId( String roleId )
    {
        this.roleId = roleId;
    }

    public void setExcludeUserRoleId( String excludeUserRoleId )
    {
        this.excludeUserRoleId = excludeUserRoleId;
    }

    public void setAllMode( String allMode )
    {
        this.allMode = allMode;
    }

    public void setOrgCode( String orgCode )
    {
        this.orgCode = orgCode;
    }

    public void setManage( String manage )
    {
        this.manage = manage;
    }

    public void setPn( String pn )
    {
        this.pn = pn;
    }

    public void setSize( String size )
    {
        this.size = size;
    }

    public void setQueryName( String queryName )
    {
        this.queryName = queryName;
    }

}
