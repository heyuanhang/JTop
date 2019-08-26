package cn.com.mjsoft.cms.security.html;

import java.util.List;

import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemSecurityResourceTag extends AbstractIteratorTag
{

    private static final long serialVersionUID = -5429041399063655250L;

    private SecurityService resourceService = SecurityService.getInstance();

    private String linear = "";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        return null;
    }

    protected String returnPutValueName()
    {
        return "Res";
    }

    protected String returnRequestAndPageListAttName()
    {
        return "resourceList";
    }

    protected Object returnSingleObject()
    {
        Long id = Long.valueOf( StringUtil.getLongValue( this.getId(), -1 ) );

        if( id.longValue() < 1 )
        {
            return resourceService.retrieveSingleSecurityResourceBean( linear );
        }

        return resourceService.retrieveSingleSecurityResourceBean( id );
    }

    protected String returnValueRange()
    {
        return "pageRange";
    }

    public void setLinear( String linear )
    {
        this.linear = linear;
    }
    
    

}
