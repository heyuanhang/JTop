package cn.com.mjsoft.cms.metadata.html;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientModelTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 1L;

    private static MetaDataService metaDataService = MetaDataService
        .getInstance();

    private String siteId = "-1";

    private String siteMode = "false";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        SiteGroupBean site = null;

        if( !"-1".equals( siteId ) )
        {
            site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( siteId );
        }
        else
        {
            site = ( SiteGroupBean ) pageContext.getRequest().getAttribute(
                Constant.CONTENT.HTML_PUB_CURRENT_SITE );

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
        }

        return metaDataService
            .retrieveAllDataModelBeanListByModelTypeAndModeAndSiteId(
                Constant.METADATA.MODEL_TYPE_CONTENT, siteMode, site
                    .getSiteId() );

    }

    protected String returnPutValueName()
    {
        return "Model";
    }

    protected String returnRequestAndPageListAttName()
    {

        return null;
    }

    protected Object returnSingleObject()
    {

        Long modelId = Long.valueOf( StringUtil.getLongValue( this.id, -1 ) );

        return metaDataService.retrieveSingleDataModelBeanById( modelId );
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
    }

    public void setSiteId( String siteId )
    {
        this.siteId = siteId;
    }

    public void setSiteMode( String siteMode )
    {
        this.siteMode = siteMode;
    }

}
