package cn.com.mjsoft.cms.security.html;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.organization.service.OrgService;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.security.SecuritrConstant;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemSecurityResourceListTag extends TagSupport
{
    private static Logger log = Logger
        .getLogger( SystemSecurityResourceListTag.class );

    // private static final String ALL_PROTECT_TYPE = "all";

    private static final long serialVersionUID = 7369308444677755340L;

    private static SecurityService resourceService = SecurityService
        .getInstance();

    private static OrgService orgService = OrgService.getInstance();

    private String userId = "";

    private String roleId = "";

    private String orgId = "";

    private String mainMode = "false";

    private String linear = "";

    private String parentLinearMode = "false";// 获取父资源的直系孩子

    // 默认只取粗粒度资源
    private String protectType = SecuritrConstant.GEN_PROTECT_TYPE.toString();

    public int doStartTag() throws JspException
    {
        List resourceServicList = null;

        // role优先
        if( StringUtil.isStringNotNull( roleId ) )
        {
            if( "all".equals( roleId ) )
            {
                Integer pType = SecuritrConstant.GEN_PROTECT_TYPE;

                if( SecuritrConstant.SPEC_PROTECT_TYPE.toString().equals(
                    protectType.trim() ) )
                {
                    pType = SecuritrConstant.SPEC_PROTECT_TYPE;
                }
                else if( SecuritrConstant.ALL_PROTECT_TYPE.toString().equals(
                    protectType.trim() ) )
                {
                    pType = SecuritrConstant.ALL_PROTECT_TYPE;
                }

                log.info( "[SystemSecurityResourceListTag] 取所有资源,protectType:"
                    + pType );

                if( !"".equals( linear ) )
                {

                    if( "true".equals( parentLinearMode ) )
                    {
                        resourceServicList = resourceService
                            .retrieveSecurityResourceBeanByParentLinear( linear );
                    }
                    else
                    {

                        if( "000".equals( linear ) )
                        {
                            linear = "";
                        }

                        resourceServicList = resourceService
                            .retrieveSecurityResourceBeanByLinear( linear );
                    }
                }
                else if( "true".equals( mainMode ) )
                {
                    resourceServicList = resourceService
                        .retrieveSecurityResourceBeanMainMode();
                }
                else
                {
                    resourceServicList = resourceService
                        .retrieveSecurityResourceBeanByProtectType( pType );
                }
            }
            else
            {
                log.info( "[SystemSecurityResourceListTag] 根据角色取资源,roleId:"
                    + roleId + ", protectType:" + protectType );
                resourceServicList = resourceService
                    .retrieveRoleHaveHisResourceBeanByRoleId( Long
                        .valueOf( StringUtil.getLongValue( roleId, -1 ) ) );
            }
        }
        else if( StringUtil.isStringNotNull( userId ) )
        {
            log.info( "[SystemSecurityResourceListTag] 根据用户取资源,userId:"
                + userId + ", protectType:" + protectType );
            resourceServicList = resourceService
                .retrieveUserHaveResourceBeanByUserId( Long.valueOf( StringUtil
                    .getLongValue( userId, -1 ) ) );
        }
        else if( StringUtil.isStringNotNull( orgId ) )
        {
            Integer pType = SecuritrConstant.GEN_PROTECT_TYPE;

            if( SecuritrConstant.SPEC_PROTECT_TYPE.toString().equals(
                protectType.trim() ) )
            {
                pType = SecuritrConstant.SPEC_PROTECT_TYPE;
            }
            else if( SecuritrConstant.ALL_PROTECT_TYPE.toString().equals(
                protectType.trim() ) )
            {
                pType = SecuritrConstant.ALL_PROTECT_TYPE;
            }

            log.info( "[SystemSecurityResourceListTag] 取所有机构资源,protectType:"
                + pType );

            String[] idInfo = StringUtil.split( orgId, ":" );

            Long orgIdVar = null;

            boolean flMode = false;

            if( idInfo.length == 1 )
            {
                orgIdVar = Long.valueOf( StringUtil
                    .getLongValue( idInfo[0], -1 ) );
            }
            else if( idInfo.length == 2 && "parent".equals( idInfo[0] ) )
            {

                orgIdVar = Long.valueOf( StringUtil
                    .getLongValue( idInfo[1], -1 ) );

                // 父机构ID
                orgIdVar = orgService.retrieveSingleOrganizationBeanById(
                    orgIdVar ).getParentId();

                Long loginOrgId = ( Long ) SecuritySessionKeeper
                    .getSecuritySession().getAuth().getOrgIdentity();

                if( loginOrgId.longValue() == 1 && orgIdVar.longValue() == 1 )
                {
                    // 若当前为系统管理员，则拥有所有可见度
                    orgIdVar = loginOrgId;
                }
                else if( orgIdVar.longValue() == 1 )
                {
                    // 父栏目是顶级，只取自己所拥有的
                    orgIdVar = Long.valueOf( StringUtil.getLongValue(
                        idInfo[1], -1 ) );

                    flMode = true;
                }

            }

            resourceServicList = resourceService
                .retrieveAuthRangeRelateResInfo( orgIdVar, pType, flMode );
        }

        pageContext.setAttribute( "resourceList", resourceServicList );

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "resourceList" );
        return EVAL_PAGE;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId( String userId )
    {
        this.userId = userId;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId( String roleId )
    {
        this.roleId = roleId;
    }

    public String getProtectType()
    {
        return protectType;
    }

    public void setProtectType( String protectType )
    {
        this.protectType = protectType;
    }

    public void setOrgId( String orgId )
    {
        this.orgId = orgId;
    }

    public void setMainMode( String mainMode )
    {
        this.mainMode = mainMode;
    }

    public void setParentLinearMode( String parentLinearMode )
    {
        this.parentLinearMode = parentLinearMode;
    }

    public void setLinear( String linear )
    {
        this.linear = linear;
    }
}
