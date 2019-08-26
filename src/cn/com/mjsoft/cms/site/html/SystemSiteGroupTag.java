package cn.com.mjsoft.cms.site.html;

import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemSiteGroupTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 3839080756919090842L;

    private static SiteGroupService siteService = SiteGroupService
        .getInstance();

    private String siteId = "";

    private String siteFlag = "";

    public void setSiteId( String siteId )
    {
        this.siteId = siteId;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    protected List returnObjectList()
    {
        if( StringUtil.isStringNotNull( siteId ) )
        {
            SiteGroupBean siteGroupBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( Long.valueOf( StringUtil.getLongValue( siteId, -1 ) ) );

            if( siteGroupBean == null )
            {
                siteGroupBean = new SiteGroupBean();
            }

            List result = new ArrayList( 1 );
            result.add( siteGroupBean );

            return result;
        }
        else if( StringUtil.isStringNotNull( siteFlag ) )
        {
            SiteGroupBean siteGroupBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                .getEntry( siteFlag );

            if( siteGroupBean == null )
            {
                siteGroupBean = new SiteGroupBean();
            }

            List result = new ArrayList( 1 );
            result.add( siteGroupBean );

            return result;
        }

        return siteService.retrieveAllSiteBean();
    }

    protected String returnPutValueName()
    {

        return "Site";
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
    }

    protected Object returnSingleObject()
    {
        return null;
    }

    protected String returnRequestAndPageListAttName()
    {

        return null;
    }

    protected void initTag()
    {

    }

}
