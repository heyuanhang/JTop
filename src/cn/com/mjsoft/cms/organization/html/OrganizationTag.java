package cn.com.mjsoft.cms.organization.html;

import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.cms.organization.bean.SystemOrganizationBean;
import cn.com.mjsoft.cms.organization.service.OrgService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class OrganizationTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 3379003959617836610L;

    private static OrgService orgService = OrgService.getInstance();

    private String orgId = "-1";

    private String orgCode = "";

    private String parentId = "-1";

    private String childMode = "false";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        if( "all".equals( orgId ) )
        {
            return orgService.retrieveAllOrganizationBean();
        }
        else if( "-1" != parentId )
        {
            Long parentIdVar = Long.valueOf( StringUtil.getLongValue( parentId,
                -1 ) );

            return orgService.retrieveOrganizationBeanByParentId( parentIdVar );
        }
        else if( StringUtil.isStringNotNull( orgCode ) )
        {
            SystemOrganizationBean orgBean = orgService
                .retrieveSingleOrganizationBeanByOrgIdLinearFlag( orgCode );

            List result = new ArrayList( 1 );

            result.add( orgBean );

            return result;
        }
        else
        {
            Long orgIdVar = Long.valueOf( StringUtil.getLongValue( orgId, -1 ) );

            if( "true".equals( childMode ) )
            {
                return orgService
                    .retrieveOrganizationBeanByOrgIdLinearFlag( orgIdVar );
            }
            else
            {
                List result = new ArrayList( 1 );

                SystemOrganizationBean orgBean = orgService
                    .retrieveSingleOrganizationBeanById( orgIdVar );

                result.add( orgBean );
                return result;
            }
        }
    }

    protected String returnPutValueName()
    {
        return "Org";
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

    public void setParentId( String parentId )
    {
        this.parentId = parentId;
    }

    public void setChildMode( String childMode )
    {
        this.childMode = childMode;
    }

    public void setOrgCode( String orgCode )
    {
        this.orgCode = orgCode;
    }
}
