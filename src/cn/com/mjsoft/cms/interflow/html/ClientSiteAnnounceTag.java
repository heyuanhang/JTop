package cn.com.mjsoft.cms.interflow.html;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.interflow.service.InterflowService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientSiteAnnounceTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -7048826891412426159L;

    private static InterflowService inService = InterflowService.getInstance();

    private String size = "200";

    private String page = "false";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        SiteGroupBean site = ( SiteGroupBean ) pageContext.getRequest().getAttribute(
            Constant.CONTENT.HTML_PUB_CURRENT_SITE );

        if( site == null )
        {
            site = ( SiteGroupBean ) this.pageContext.getRequest().getAttribute( "SiteObj" );

            if( site == null )
            {
                site = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                        .getRequest() );
            }
        }

        // 注意：只精确到日期的非精确时间要求，无需集群时间同步
        Calendar cal = Calendar.getInstance();

        cal.set( Calendar.HOUR_OF_DAY, 0 );
        cal.set( Calendar.SECOND, 0 );
        cal.set( Calendar.MINUTE, 0 );

        Date cd = cal.getTime();

        int pn = StringUtil.getIntValue( this.pageContext.getRequest().getParameter( "pn" ), 1 );

        if( !"true".equals( page ) )
        {
            pn = 1;
        }

        Long count = inService.retrieveSiteAnnounceBeanCountByCurrDate( site.getSiteId(), cd );

        Page pageInfo = new Page( StringUtil.getIntValue( size, 15 ), count.intValue(), pn );

        int pageSize = StringUtil.getIntValue( size, 200 );

        List result = inService.retrieveSiteAnnounceBeanListByCurrDate( site.getSiteId(), cd, Long
            .valueOf( pageInfo.getFirstResult() ), pageSize );

        return result;
    }

    protected String returnPutValueName()
    {
        return "Ann";
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

    public void setPage( String page )
    {
        this.page = page;
    }

}
