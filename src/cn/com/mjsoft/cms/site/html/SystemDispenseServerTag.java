package cn.com.mjsoft.cms.site.html;

import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemDispenseServerTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 1L;

    private static SiteGroupService siteService = SiteGroupService
        .getInstance();

    private String id = "";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
            .getSecuritySession().getCurrentLoginSiteInfo();

        if( !"".equals( id ) )
        {
            List result = new ArrayList();

            result.add( siteService
                .retrieveSingleDispenseServerBeanById( Long.valueOf( StringUtil
                    .getLongValue( id, -1 ) ), site.getSiteId() ) );

            return result;
        }
        else
        {
            return siteService.retrieveDispenseServerBeanBySiteFlag( site
                .getSiteId() );
        }
    }

    protected String returnPutValueName()
    {
        return "Server";
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

    public void setId( String id )
    {
        this.id = id;
    }

}
