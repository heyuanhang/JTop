package cn.com.mjsoft.cms.security.html;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.security.bean.SystemUserBean;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemUserTag extends AbstractIteratorTag
{
    private static Logger log = Logger.getLogger( SystemUserTag.class );

    private static final long serialVersionUID = -527922068083283343L;

    private SecurityService securityService = SecurityService.getInstance();

    private String name;

    protected void initTag()
    {
        if( StringUtil.isStringNotNull( name ) )
        {
            setToSingleMode();
        }
    }

    protected List returnObjectList()
    {
        return null;
    }

    protected String returnPutValueName()
    {
        return "SysUser";
    }

    protected String returnRequestAndPageListAttName()
    {
        return "systemUserList";
    }

    protected Object returnSingleObject()
    {
        SystemUserBean sysUser = null;

        if( StringUtil.isStringNotNull( this.getId() ) )
        {
            log.info( "[SystemUserTag] userId:" + this.getId() );

            Long id = Long
                .valueOf( StringUtil.getLongValue( this.getId(), -1 ) );

            sysUser = securityService.retrieveSingleSystemUserBean( null, id );
        }
        else if( StringUtil.isStringNotNull( name ) )
        {
            log.info( "[SystemUserTag] userName:" + name );
            sysUser = securityService.retrieveSingleSystemUserBean( name, null );
        }

        return sysUser;
    }

    protected String returnValueRange()
    {
        return "pageRange";
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }
}
