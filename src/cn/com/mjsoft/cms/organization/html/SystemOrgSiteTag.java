package cn.com.mjsoft.cms.organization.html;

import java.util.List;

import cn.com.mjsoft.cms.organization.service.OrgService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemOrgSiteTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 2556074002978311968L;

    private static OrgService orgService = OrgService.getInstance();

    private String orgId = "";

    private String roleId = "";

    private String roleMode = "false";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        if( "true".equals( roleMode ) )
        {
            Long roleIdVar = Long
                .valueOf( StringUtil.getLongValue( roleId, -1 ) );
            return orgService.retrieveSiteBeanByRoleHaveSite( roleIdVar );
        }
        else
        {
            Long childOrgIdVar = null;

            String[] idInfo = StringUtil.split( orgId, ":" );

            Long orgIdVar = null;
            if( idInfo.length == 1 )
            {
                orgIdVar = Long.valueOf( StringUtil
                    .getLongValue( idInfo[0], -1 ) );
            }
            else if( idInfo.length == 2 && "parent".equals( idInfo[0] ) )
            {
                // 上级机构ID模式
                orgIdVar = Long.valueOf( StringUtil
                    .getLongValue( idInfo[1], -1 ) );

                childOrgIdVar = orgIdVar;

                // 上级ID
                orgIdVar = orgService.retrieveSingleOrganizationBeanById(
                    orgIdVar ).getParentId();
            }

            return orgService.retrieveSiteBeanByOrgId( orgIdVar, childOrgIdVar );
        }
    }

    protected String returnPutValueName()
    {
        return "OrgSite";
    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected Object returnSingleObject()
    {
        return null;
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
    }

    public void setOrgId( String orgId )
    {
        this.orgId = orgId;
    }

    public void setRoleId( String roleId )
    {
        this.roleId = roleId;
    }

    public void setRoleMode( String roleMode )
    {
        this.roleMode = roleMode;
    }
}
