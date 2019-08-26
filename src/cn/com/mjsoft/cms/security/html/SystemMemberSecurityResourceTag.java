package cn.com.mjsoft.cms.security.html;

import java.util.List;

import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemMemberSecurityResourceTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 7575660921000255593L;

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
            return resourceService
                .retrieveSingleMemberSecurityResourceBean( linear );
        }

        return resourceService.retrieveSingleMemberSecurityResourceBean( id );
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
