package cn.com.mjsoft.cms.security.html;

import java.util.List;

import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemRoleTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -3855521180902505378L;

    private SecurityService securityService = SecurityService.getInstance();

    protected void initTag()
    {
        // TODO Auto-generated method stub
    }

    protected List returnObjectList()
    {
        return null;
    }

    protected String returnPutValueName()
    {
        return "Role";
    }

    protected String returnRequestAndPageListAttName()
    {
        return "systemRoleList";
    }

    protected Object returnSingleObject()
    {
        Long id = Long.valueOf( StringUtil.getLongValue( this.getId(), -1 ) );

        return securityService.retrieveSingleSystemRoleBean( id );
    }

    protected String returnValueRange()
    {
        return "pageRange";
    }

}
