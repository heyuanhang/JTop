package cn.com.mjsoft.cms.security.html;

import java.util.List;

import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientMemberSecurityResourceTag extends AbstractIteratorTag
{

    private static final long serialVersionUID = -3812120016265523062L;
    
    private SecurityService resourceService = SecurityService
        .getInstance();

    protected void initTag()
    {
         

    }

    protected List returnObjectList()
    {

        return null;
    }

    protected String returnPutValueName()
    {
        return "MemResource";
    }

    protected String returnRequestAndPageListAttName()
    {
        return "MemSecurityResourceList";
    }

    protected Object returnSingleObject()
    {
        Long id = Long.valueOf( StringUtil.getLongValue( this.getId(), -1 ) );

        return resourceService.retrieveSingleMemberSecurityResourceBean( id );
    }

    protected String returnValueRange()
    {
        return "pageRange";
    }

}
