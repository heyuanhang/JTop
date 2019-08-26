package cn.com.mjsoft.cms.interflow.html;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.interflow.service.InterflowService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientSiteFriendLinkTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -2712293914346843678L;

    private static InterflowService inService = InterflowService.getInstance();

    private String type = "";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
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

        List result = inService.retrieveSiteFriendLinkByType( site.getSiteId(),
            type );

        return result;
    }

    protected String returnPutValueName()
    {
        return "Friend";
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

    public void setType( String type )
    {
        this.type = type;
    }

}
