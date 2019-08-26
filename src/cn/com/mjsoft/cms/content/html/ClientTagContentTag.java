package cn.com.mjsoft.cms.content.html;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.bean.TagWordBean;
import cn.com.mjsoft.cms.channel.html.ClientTagWordTag;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientTagContentTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 560098858166080681L;

    private static Logger log = Logger.getLogger( ClientTagWordTag.class );

    private ContentService contentService = ContentService.getInstance();

    private ChannelService channelService = ChannelService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    private String tagId = "-1";

    private String tagName = "";

    private String order = "down";

    private String page = "false";

    private String size = "5000";

    protected String returnPutValueName()
    {
        return "TInfo";
    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected String returnValueRange()
    {
        return "selfRange";
    }

    protected Object returnSingleObject()
    {
        return null;
    }

    protected List returnObjectList()
    {
        // 来自发布逻辑的访问,根据管理站点确定当前站点
        SiteGroupBean siteBean = ( SiteGroupBean ) pageContext.getRequest().getAttribute(
            Constant.CONTENT.HTML_PUB_CURRENT_SITE );

        if( siteBean == null )
        {
            siteBean = ( SiteGroupBean ) this.pageContext.getRequest().getAttribute( "SiteObj" );

            if( siteBean == null )
            {
                // 根据URL来判断站点
                siteBean = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                        .getRequest() );
            }
        }

        String orderFlag = "desc";

        if( "up".equals( order ) )
        {
            orderFlag = "asc";
        }

        List result = null;

        Long tagIdVar = Long.valueOf( -1 );

        if( "-1".equals( tagId ) && !"".equals( tagName ) )
        {
            TagWordBean twb = channelService.retrieveSingleTagWordBeanByTagName( tagName );

            if( twb != null )
            {
                tagIdVar = twb.getTagId();
            }
        }
        else
        {
            tagIdVar = Long.valueOf( StringUtil.getLongValue( tagId, -1 ) );
        }

        int pageNum = StringUtil
            .getIntValue( this.pageContext.getRequest().getParameter( "pn" ), 1 );

        if( !"true".equals( page ) )
        {
            pageNum = 1;
        }

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        Long count = null;

        count = contentService.retrieveTagRaleteContentByTagId( tagIdVar );

        pageInfo = new Page( pageSize, count.intValue(), pageNum );

        result = contentService.retrieveTagRaleteContentByTagId( tagIdVar, orderFlag, Long
            .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );

        this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );

        //String queryCod = "tagId=" + tagId + "&tagName=" + tagName + "&order=" + order + "&page="
            //+ page + "&size=" + size;

        ContentClassBean classBean = ( ContentClassBean ) this.pageContext.getAttribute( "Class" );

        if( classBean != null )
        {
            // 静态分页
            publishService.htmlTagPage( this.pageContext, null, classBean.getClassId(), classBean,
                classBean.getClassId(), pageInfo, page, "" );
        }

        return result;
    }

    protected void initTag()
    {

    }

    public void setSize( String size )
    {
        this.size = size;
    }

    public void setOrder( String order )
    {
        this.order = order;
    }

    public void setTagId( String tagId )
    {
        this.tagId = tagId;
    }

    public void setTagName( String tagName )
    {
        this.tagName = tagName;
    }

    public void setPage( String page )
    {
        this.page = page;
    }

}
