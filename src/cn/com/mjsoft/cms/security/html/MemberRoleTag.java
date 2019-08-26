package cn.com.mjsoft.cms.security.html;

import java.util.List;

import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class MemberRoleTag extends AbstractIteratorTag
{

    private static final long serialVersionUID = -5307136640030541989L;

    private static SecurityService securityService = SecurityService
        .getInstance();

    private String userId = "-1";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        Long userIdVar = Long.valueOf( StringUtil.getLongValue( userId, -1 ) );

        if( userIdVar.longValue() > 0 )
        {
            return securityService.retrieveMemberRoleBeanByUserId( userIdVar );
        }
        else
        {
            return securityService.retrieveAllMemberRoleBean();
        }
    }

    protected String returnPutValueName()
    {
        return "Role";
    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected Object returnSingleObject()
    {
        Long id = Long.valueOf( StringUtil.getLongValue( this.getId(), -1 ) );

        return securityService.retrieveSingleMemberRoleBean( id );
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
    }

    public void setUserId( String userId )
    {
        this.userId = userId;
    }

}
