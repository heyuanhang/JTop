package cn.com.mjsoft.cms.security.html;

import java.util.List;

import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class NotUse__SecurityResourceTag extends AbstractIteratorTag
{

    private static final long serialVersionUID = -5429041399063655250L;

    private SecurityService resourceService = SecurityService
        .getInstance();

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
        return "Resource";
    }

    protected String returnRequestAndPageListAttName()
    {
        return "securityResourceList";
    }

    protected Object returnSingleObject()
    {
        Long id = Long.valueOf( StringUtil.getLongValue( this.getId(), -1 ) );

        return resourceService.retrieveSingleSecurityResourceBean( id );
    }

    protected String returnValueRange()
    {
        return "pageRange";
    }

}
