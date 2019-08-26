package cn.com.mjsoft.cms.search.html;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.search.service.SearchService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientSearchKeyTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -1496553801607411268L;

    private static SearchService searchService = SearchService.getInstance();

    private String size = "15";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        // 根据URL来判断站点
        SiteGroupBean siteBean = ( SiteGroupBean ) pageContext.getRequest()
            .getAttribute( Constant.CONTENT.HTML_PUB_CURRENT_SITE );

        if( siteBean == null )
        {
            siteBean = ( SiteGroupBean ) this.pageContext.getRequest()
                .getAttribute( "SiteObj" );

            if( siteBean == null )
            {
                siteBean = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                        .getRequest() );
            }
        }

        return searchService
            .retrieveSearchKeyCountInfoBySiteId( siteBean.getSiteId(), Integer
                .valueOf( StringUtil.getIntValue( size, 15 ) ) );

    }

    protected String returnPutValueName()
    {
        return "Key";
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

    public void setSize( String size )
    {
        this.size = size;
    }

}
