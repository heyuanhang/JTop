package cn.com.mjsoft.cms.guestbook.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.guestbook.service.GuestbookService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientGuestbookConfigTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 4660224713101585299L;

    private static GuestbookService gbService = GuestbookService.getInstance();

    private String configId = "";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        List result = Collections.EMPTY_LIST;

        SiteGroupBean site = ( SiteGroupBean ) pageContext.getRequest()
            .getAttribute( Constant.CONTENT.HTML_PUB_CURRENT_SITE );

        if( site == null )
        {
            site = ( SiteGroupBean ) this.pageContext.getRequest()
                .getAttribute( "SiteObj" );

            if( site == null )
            {
                site = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                        .getRequest() );
            }
        }

        if( "all".equals( configId ) )
        {
            result = gbService.retrieveAllGuestbookConfigBeanListBySite( site
                .getSiteId() );
        }
        else
        {

            Long configIdVar = Long.valueOf( StringUtil.getLongValue( configId,
                -1 ) );

            if( configIdVar.longValue() > 0 )
            {
                result = new ArrayList( 1 );
                result
                    .add( gbService
                        .retrieveSingleGuestbookConfigBeanByConfigId( configIdVar ) );
            }
        }

        return result;
    }

    protected String returnPutValueName()
    {
        return "GbCfg";
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

    public void setConfigId( String configId )
    {
        this.configId = configId;
    }
}
