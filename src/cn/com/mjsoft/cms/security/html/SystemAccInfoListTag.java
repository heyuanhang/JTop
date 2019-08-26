package cn.com.mjsoft.cms.security.html;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.organization.bean.SystemOrganizationBean;
import cn.com.mjsoft.cms.organization.service.OrgService;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemAccInfoListTag extends TagSupport
{
    private static final long serialVersionUID = -6338058548661786492L;

    private static Logger log = Logger.getLogger( SystemAccInfoListTag.class );

    private static SecurityService securityService = SecurityService
        .getInstance();

    private static OrgService orgService = OrgService.getInstance();

    private String roleId;

    private String resId;

    private String sysFlag;

    private String accType;

    private String siteId = "";

    private String orgId = "";

    private String secType = "";

    private String classMode = "channel";

    public int doStartTag() throws JspException
    {
        Long rId = Long.valueOf( StringUtil.getLongValue( roleId, -9999 ) );
        Long rsId = Long.valueOf( StringUtil.getLongValue( resId, -9999 ) );

        List accInfoList = null;
        if( "audit".equals( accType ) )
        {
            accInfoList = securityService.retrieveAuditAccIdList( rId );

        }
        else if( !"".equals( orgId ) && !"".equals( siteId )
            && !"".equals( secType ) )
        {
            String[] idInfo = StringUtil.split( orgId, ":" );

            Long orgIdVar = null;
            Long originalOrgIdVar = null;
            boolean isParRootOrg = false;
            boolean isParentMode = false;

            if( idInfo.length == 1 )
            {
                orgIdVar = Long.valueOf( StringUtil
                    .getLongValue( idInfo[0], -1 ) );

                if( orgIdVar.longValue() == 1 )
                {
                    originalOrgIdVar = orgIdVar;
                    isParRootOrg = true;
                }
            }
            else if( idInfo.length == 2 && "parent".equals( idInfo[0] ) )
            {
                // 上级机构ID
                orgIdVar = Long.valueOf( StringUtil
                    .getLongValue( idInfo[1], -1 ) );
                originalOrgIdVar = orgIdVar;

                SystemOrganizationBean orgBean = orgService.retrieveSingleOrganizationBeanById(
                    orgIdVar );
                
                if(orgBean != null)
                {
                    orgIdVar = orgBean.getParentId();                       
                }
                
             

                if( orgIdVar.longValue() == 1 || orgIdVar == -99999)
                {
                    isParRootOrg = true;
                }

                isParentMode = true;
            }

            Long siteIdVar = Long
                .valueOf( StringUtil.getIntValue( siteId, -1 ) );
            Integer dstVar = Integer.valueOf( StringUtil.getIntValue( secType,
                -1 ) );

            accInfoList = securityService
                .retrieveRoleRangeOrgRelateResourceAcc( originalOrgIdVar,
                    orgIdVar, siteIdVar, dstVar, isParRootOrg, isParentMode,
                     classMode  );
        }
        else
        {
            Long siteIdVar = Long
                .valueOf( StringUtil.getIntValue( siteId, -1 ) );
            accInfoList = securityService.retrieveAccIdList( siteIdVar, rId,
                rsId, accType, StringUtil.getIntValue( secType,
                    -1 ) );

            pageContext.setAttribute( "accInfoList", accInfoList );
        }

        pageContext.setAttribute( "accInfoList", accInfoList );
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "accInfoList" );
        return EVAL_PAGE;
    }

    public String getResId()
    {
        return resId;
    }

    public void setResId( String resId )
    {
        this.resId = resId;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId( String roleId )
    {
        this.roleId = roleId;
    }

    public void setAccType( String accType )
    {
        this.accType = accType;
    }

    public void setSysFlag( String sysFlag )
    {
        this.sysFlag = sysFlag;
    }

    public void setOrgId( String orgId )
    {
        this.orgId = orgId;
    }

    public void setSecType( String secType )
    {
        this.secType = secType;
    }

    public void setSiteId( String siteId )
    {
        this.siteId = siteId;
    }

    public void setClassMode( String classMode )
    {
        this.classMode = classMode;
    }
}
