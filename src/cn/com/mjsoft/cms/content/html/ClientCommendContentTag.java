package cn.com.mjsoft.cms.content.html;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.publish.bean.PublishRuleBean;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientCommendContentTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -8128825765961407881L;

    private static ContentService contentService = ContentService.getInstance();

    private static ChannelService channelService = ChannelService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    private String flag = "";

    private String typeId = "-1";

    private String infoId = "-1";

    private String size = "500";

    private String page = "false";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        List commendContent = null;

        if( !"-1".equals( infoId ) )
        {
            commendContent = new ArrayList( 1 );
            commendContent.add( contentService.retrieveSingleCommendPushInfoByInfoId( Long
                .valueOf( StringUtil.getLongValue( infoId, -1 ) ) ) );
        }
        else
        {
            boolean pageMode = false;

            if( "true".equals( page ) )
            {
                // 注意:当不处于pageMode时,发布行为的任何参数都不需要传递
                pageMode = true;
            }

            int nextPage = StringUtil.getIntValue( this.pageContext.getRequest()
                .getParameter( "pn" ), 1 );

            if( !pageMode )
            {
                nextPage = 1;
            }

            int pageSize = StringUtil.getIntValue( size, 500 );

            Page pageInfo = null;

            Integer count = null;

            if( !"-1".equals( typeId ) )
            {
                count = contentService.retrieveAllCommendContentByCommendCountByTypeId( Long
                    .valueOf( StringUtil.getLongValue( typeId, -1 ) ) );
            }
            else
            {
                count = contentService.retrieveAllCommendContentByCommendCountByFlag( flag );
            }

            pageInfo = new Page( pageSize, count.intValue(), nextPage );

            int nextPn = ( ( pageInfo.getCurrentPage() + 1 ) > pageInfo.getPageCount() ) ? pageInfo
                .getPageCount() : ( pageInfo.getCurrentPage() + 1 );

            int prevPn = ( ( pageInfo.getCurrentPage() - 1 ) < 1 ) ? 1 : ( pageInfo
                .getCurrentPage() - 1 );

            // 页面所用分页对象

            if( pageMode )
            {
                this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );
            }

            HttpServletRequest request = ( HttpServletRequest ) this.pageContext.getRequest();

            boolean mob = request.getAttribute( "_pub_mob_" ) != null ? ( Boolean ) request
                .getAttribute( "_pub_mob_" ) : false;

            boolean pad = request.getAttribute( "_pub_pad_" ) != null ? ( Boolean ) request
                .getAttribute( "_pub_pad_" ) : false;

            boolean fromSystemAction = false;

            if( Boolean.TRUE.equals( request.getAttribute( Constant.CONTENT.HTML_PUB_ACTION_FLAG ) ) )
            {
                fromSystemAction = true;

                // 发布所用分页对象
                if( pageMode )
                {
                    request.setAttribute( "___system_dispose_page_object_for_pub___", pageInfo );
                }
            }

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

            if( !"-1".equals( typeId ) )
            {
                commendContent = contentService
                    .retrieveAllCommendContentByCommendByTypeId( Long.valueOf( StringUtil
                        .getLongValue( typeId, -1 ) ), site.getSiteFlag(), Long.valueOf( pageInfo
                        .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );
            }
            else
            {
                commendContent = contentService.retrieveAllCommendContentByCommendByFlag( flag,
                    site.getSiteFlag(), Integer.valueOf( pageSize ) );
            }

            // 发布逻辑

            if( pageMode )
            {
                // 获取type信息
                ContentCommendTypeBean typeBean = channelService
                    .retrieveSingleContentCommendTypeBeanByTypeId( Long.valueOf( StringUtil
                        .getLongValue( typeId, -1 ) ) );

                if( typeBean == null )
                {
                    return new ArrayList( 1 );
                }

                // 最后位置,目前推荐列表全部发布
                pageInfo.setEndPos( Integer.toString( pageInfo.getPageCount() ) );

                String pubListUrl = typeBean.getListTemplateUrl();

                if( mob )
                {
                    pubListUrl = typeBean.getMobListTemplateUrl();
                }
                else if( pad )
                {
                    pubListUrl = typeBean.getPadListTemplateUrl();
                }

                // 和管理不同,url的组装需要走模板url规则
                String url = site.getSiteUrl()
                // + siteTemplate 隐藏template
                    + StringUtil.replaceString( StringUtil.replaceString( pubListUrl, "{type-id}",
                        typeId, false, false ), "{class-id}", typeBean.getClassId().toString(),
                        false, false );

                String prefixQuery = "?";
                if( StringUtil.isStringNotNull( typeBean.getListTemplateUrl() )
                    && typeBean.getListTemplateUrl().indexOf( "?" ) != -1 )
                {
                    prefixQuery = "&";
                }

                String nextQuery = "";
                String prevQuery = "";

                pageInfo.setHeadQuery( new StringBuffer( url ).append( prefixQuery )
                    .append( "pn=1" ).toString() );

                pageInfo.setEndQuery( new StringBuffer( url ).append( prefixQuery ).append(
                    "pn=" + pageInfo.getPageCount() ).toString() );

                pageInfo.setJumpQuery( new StringBuffer( url ).toString() );

                if( pageInfo.getCurrentPage() == pageInfo.getPageCount()
                    || pageInfo.getPageCount() == 0 )
                {
                    // 静态化最后一页,nextQueryActionUrl将为空

                    if( pageMode )
                    {
                        request.setAttribute( "nextQueryActionUrl", null );
                    }

                    // 下一页将为动态,注意不合法url
                    if( url.indexOf( ".jsp" ) != -1 || url.indexOf( ".thtml" ) != -1 )
                    {
                        nextQuery = new StringBuffer( url ).append( prefixQuery ).append(
                            "pn=" + nextPn ).toString();

                        prevQuery = new StringBuffer( url ).append( prefixQuery ).append(
                            "pn=" + prevPn ).toString();

                    }

                    pageInfo.setNextQuery( nextQuery );
                    pageInfo.setPrevQuery( prevQuery );

                }
                else
                {
                    // 下一页仍然是静态化系统动作

                    // 必须变化为内部访问地址

                    String publishNextQueryChain = null;

                    String siteTemplate = Constant.CONTENT.TEMPLATE_BASE + Constant.CONTENT.URL_SEP;

                    // 页面分页URL
                    // 下一页将为动态,注意不合法url
                    if( url.indexOf( ".jsp" ) != -1 || url.indexOf( ".thtml" ) != -1 )
                    {
                        nextQuery = new StringBuffer( url ).append( prefixQuery ).append(
                            "pn=" + nextPn ).toString();

                        prevQuery = new StringBuffer( url ).append( prefixQuery ).append(
                            "pn=" + prevPn ).toString();
                    }

                    pageInfo.setNextQuery( nextQuery );
                    pageInfo.setPrevQuery( prevQuery );

                    // 切换系统内部访问URL,过滤参数全
                    url = site.getSiteRoot()
                        + Constant.CONTENT.URL_SEP
                        + siteTemplate
                        + StringUtil.replaceString( StringUtil.replaceString( pubListUrl,
                            "{type-id}", typeId, false, false ), "{class-id}", typeBean
                            .getClassId().toString(), false, false );

                    if( url.indexOf( ".jsp?" ) != -1 )
                    {
                        publishNextQueryChain = new StringBuffer( Constant.CONTENT.URL_SEP + url )
                            .append( prefixQuery ).append( "pn=" + nextPn ).toString();
                    }
                    else
                    {
                        publishNextQueryChain = new StringBuffer( Constant.CONTENT.URL_SEP + url )
                            .append( prefixQuery ).append( "pn=" + nextPn ).toString();
                    }

                    // 将nextQueryActionUrl传递给发布逻辑,继续处理分页逻辑
                    if( pageMode )
                    {
                        request.setAttribute( "nextQueryActionUrl", publishNextQueryChain );
                    }

                }

                if( fromSystemAction )
                {

                    // 发布逻辑需要静态的next 和 prev 查询

                    PublishRuleBean ruleBean = publishService
                        .retrieveSinglePublishRuleBean( typeBean.getListPublishRuleId() );

                    if( ruleBean != null )
                    {

                        pageInfo.setJumpStatic( site.getSiteUrl()
                            + ( ( ruleBean == null ) ? site.getSiteUrl() : ruleBean
                                .getFullCommendInfoPublishPath( site, typeBean, null, null, Integer
                                    .valueOf( Integer.valueOf( -99999 ) ) )[1] ) );

                        String[] nextPathInfo = ruleBean.getFullCommendInfoPublishPath( site,
                            typeBean, null, null, Integer.valueOf( nextPn ) );

                        String[] prevPathInfo = ruleBean.getFullCommendInfoPublishPath( site,
                            typeBean, null, null, Integer.valueOf( prevPn ) );

                        String[] headPathInfo = ruleBean.getFullCommendInfoPublishPath( site,
                            typeBean, null, null, Integer.valueOf( 1 ) );

                        String[] endPathInfo = ruleBean.getFullCommendInfoPublishPath( site,
                            typeBean, null, null, Integer.valueOf( pageInfo.getPageCount() ) );

                        pageInfo.setNextQuery( site.getSiteUrl() + nextPathInfo[1] );

                        if( mob )
                        {
                            pageInfo.setNextQuery( site.getSiteUrl() + nextPathInfo[3] );
                        }
                        else if( pad )
                        {
                            pageInfo.setNextQuery( site.getSiteUrl() + nextPathInfo[5] );
                        }

                        pageInfo.setPrevQuery( site.getSiteUrl() + prevPathInfo[1] );

                        if( mob )
                        {
                            pageInfo.setPrevQuery( site.getSiteUrl() + prevPathInfo[2] );
                        }
                        else if( pad )
                        {
                            pageInfo.setPrevQuery( site.getSiteUrl() + prevPathInfo[5] );
                        }

                        pageInfo.setHeadQuery( site.getSiteUrl() + headPathInfo[1] );

                        if( mob )
                        {
                            pageInfo.setHeadQuery( site.getSiteUrl() + headPathInfo[2] );
                        }
                        else if( pad )
                        {
                            pageInfo.setHeadQuery( site.getSiteUrl() + headPathInfo[5] );
                        }

                        pageInfo.setEndQuery( site.getSiteUrl() + endPathInfo[1] );

                        if( mob )
                        {
                            pageInfo.setEndQuery( site.getSiteUrl() + endPathInfo[2] );
                        }
                        else if( pad )
                        {
                            pageInfo.setEndQuery( site.getSiteUrl() + endPathInfo[5] );
                        }
                    }
                }

            }
        }

        return commendContent;
    }

    protected String returnPutValueName()
    {
        return "CommInfo";
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

    public void setFlag( String flag )
    {
        this.flag = flag;
    }

    public void setInfoId( String infoId )
    {
        this.infoId = infoId;
    }

    public void setTypeId( String typeId )
    {
        this.typeId = typeId;
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
