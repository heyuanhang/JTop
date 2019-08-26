package cn.com.mjsoft.cms.channel.html;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientTagWordTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -375988156691786351L;

    private static Logger log = Logger.getLogger( ClientTagWordTag.class );

    private ChannelService channelService = ChannelService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    private String typeId = "-1";

    private String typeFlag = "";

    private String order = "";

    private String fc = "";

    private String page = "false";

    private String size = "5000";

    protected String returnPutValueName()
    {
        return "Tag";
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
        Long targetId = Long.valueOf( StringUtil.getLongValue( this.getId(), -1 ) );

        if( targetId.longValue() < 1 )
        {
            return null;
        }

        return channelService.retrieveSingleTagWordBeanByTagId( targetId );
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

        List result = null;

        Long tId = Long.valueOf( StringUtil.getLongValue( typeId, -1 ) );

        int pageNum = StringUtil
            .getIntValue( this.pageContext.getRequest().getParameter( "pn" ), 1 );

        if( !"true".equals( page ) )
        {
            pageNum = 1;
        }

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        Long count = null;

        if( tId.longValue() < 1 && "".equals( typeFlag ) )// 取所有
        {
            if( StringUtil.isStringNotNull( fc ) )
            {
                count = channelService.retrieveTagWordCountBySiteId( siteBean.getSiteId(), fc
                    .toLowerCase() );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = channelService.retrieveTagWordBeanBySiteId( siteBean.getSiteId(), fc, Long
                    .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ), order );
            }
            else
            {
                count = channelService.retrieveTagWordCountBySiteId( siteBean.getSiteId() );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = channelService.retrieveTagWordBeanBySiteId( siteBean.getSiteId(), Long
                    .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ), order );
            }
        }
        else
        {
            if( tId.longValue() < 1 )
            {
                Map tagType = channelService.retrieveTagTypeByFlag( typeFlag );

                tId = ( Long ) tagType.get( "tagTypeId" );
            }

            if( StringUtil.isStringNotNull( fc ) )
            {
                count = channelService.retrieveTagWordCountBySiteId( siteBean.getSiteId(), fc, tId );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = channelService.retrieveTagWordBeanBySiteId( siteBean.getSiteId(), fc, tId,
                    Long.valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ), order );
            }
            else
            {
                count = channelService.retrieveTagWordCountBySiteId( siteBean.getSiteId(), tId );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = channelService.retrieveTagWordBeanBySiteId( siteBean.getSiteId(), tId,
                    Long.valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ), order );
            }

        }

        //String queryCod = "typeId=" + typeId + "&typeFlag=" + typeFlag + "&order=" + order + "&fc="
            //+ fc + "&page=" + page + "&size=" + size;

        ContentClassBean classBean = ( ContentClassBean ) this.pageContext.getAttribute( "Class" );

        if( classBean != null )
        {
            // 静态分页
            publishService.htmlTagPage( this.pageContext, null, classBean.getClassId(), classBean,
                classBean.getClassId(), pageInfo, page, "" );
        }

        this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );

        return result;
    }

    protected void initTag()
    {

    }

    public void setFc( String fc )
    {
        this.fc = fc;
    }

    public void setSize( String size )
    {
        this.size = size;
    }

    public void setTypeId( String typeId )
    {
        this.typeId = typeId;
    }

    public void setTypeFlag( String typeFlag )
    {
        this.typeFlag = typeFlag;
    }

    public void setOrder( String order )
    {
        this.order = order;
    }

    public void setPage( String page )
    {
        this.page = page;
    }

}
