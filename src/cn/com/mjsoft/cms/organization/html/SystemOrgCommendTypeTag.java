package cn.com.mjsoft.cms.organization.html;

import java.util.List;

import cn.com.mjsoft.cms.organization.service.OrgService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemOrgCommendTypeTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -4148221595409630617L;

    private static OrgService orgService = OrgService.getInstance();

    private String orgId = "";

    private String siteId = "";

    private String secType = "";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        Long orgIdVar = null;

        String[] idInfo = StringUtil.split( orgId, ":" );

        Long originalOrgIdVar = null;
        boolean isParRootOrg = false;
        boolean isParentMode = false;

        if( idInfo.length == 1 )
        {
            orgIdVar = Long.valueOf( StringUtil.getLongValue( idInfo[0], -1 ) );

            // 是否总机构管理角色
            if( orgIdVar.longValue() == 1 )
            {
                originalOrgIdVar = orgIdVar;
                isParRootOrg = true;
            }
        }
        else if( idInfo.length == 2 && "parent".equals( idInfo[0] ) )
        {
            // 上级机构ID
            orgIdVar = Long.valueOf( StringUtil.getLongValue( idInfo[1], -1 ) );
            originalOrgIdVar = orgIdVar;

            orgIdVar = orgService.retrieveSingleOrganizationBeanById( orgIdVar )
                .getParentId();

            // 是否总机构管理角色
            if( orgIdVar.longValue() == 1 )
            {
                isParRootOrg = true;
            }

            isParentMode = true;
        }

        Long siteIdVar = Long.valueOf( StringUtil.getIntValue( siteId, -1 ) );

        return orgService.retrieveCommendTypeBeanByOrgId( originalOrgIdVar,
            orgIdVar, StringUtil.changeStringToList( secType, "," ), siteIdVar,
            isParRootOrg, isParentMode );
    }

    protected String returnPutValueName()
    {
        return "CommendType";
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

    public void setSiteId( String siteId )
    {
        this.siteId = siteId;
    }

    public void setSecType( String secType )
    {
        this.secType = secType;
    }

}
