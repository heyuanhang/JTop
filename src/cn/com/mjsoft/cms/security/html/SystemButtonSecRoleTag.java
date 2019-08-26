package cn.com.mjsoft.cms.security.html;

import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.security.bean.SecurityResourceBean;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.Role;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemButtonSecRoleTag extends TagSupport
{
    private static final long serialVersionUID = 97073478794800878L;

    private static SecurityService securityService = SecurityService
        .getInstance();

    private String flag = "";

    private String accId = "";

    public int doStartTag() throws JspException
    {
        boolean accOk = false;

        if( StringUtil.isStringNotNull( flag ) )
        {
            if( StringUtil.isStringNull( accId ) )
            {
                // 粗粒度资源
                Set roleSet = securityService
                    .retrieveSecurityResRalateRoleIdByFlag( flag );

                Role[] rs = SecuritySessionKeeper.getSecuritySession()
                    .getAuth().getUserRoleCopy();

                if( rs != null )
                {
                    for ( int i = 0; i < rs.length; i++ )
                    {
                        if( roleSet.contains( ( ( Role ) rs[i] ).getRoleID() ) )
                        {
                            accOk = true;
                            break;
                        }
                    }
                }
            }
            else
            {
                // 细粒度资源
                SecurityResourceBean secResBean = securityService
                    .retrieveSingleSecurityResourceBeanByFlag( flag );

                if( secResBean != null )
                {
                    SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
                        .getSecuritySession().getCurrentLoginSiteInfo();

                    Set roleSet = securityService
                        .retrieveAccSecurityResRalateRoleIdByFlag( secResBean
                            .getSecResId(), Long.valueOf( StringUtil
                            .getLongValue( accId, -1 ) ), site.getSiteId() );

                    Role[] rs = SecuritySessionKeeper.getSecuritySession()
                        .getAuth().getUserRoleCopy();

                    if( rs != null )
                    {
                        for ( int i = 0; i < rs.length; i++ )
                        {
                            if( roleSet.contains( ( ( Role ) rs[i] )
                                .getRoleID() ) )
                            {
                                accOk = true;
                                break;
                            }
                        }
                    }
                }

            }
        }

        if( accOk )
        {
            return EVAL_BODY_INCLUDE;
        }
        else
        {
            return SKIP_BODY;
        }

    }

    public int doEndTag() throws JspException
    {
        return super.doEndTag();
    }

    public void setFlag( String flag )
    {
        this.flag = flag;
    }

    public void setAccId( String accId )
    {
        this.accId = accId;
    }

}
