package cn.com.mjsoft.cms.content.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientGetCommendInfoListPageJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger.getLogger( ClientGetCommendInfoListPageJSonFlow.class );

    private static ContentService contentService = ContentService.getInstance();

    private static ChannelService channelService = ChannelService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        HttpServletRequest request = this.getServletFlowContext().getRequest();

        Thread.sleep( 200 );

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        // 是否有第一页标签的最后的分页数据
        String ep = notNull( ( String ) params.get( "ep" ) );

        if( StringUtil.isStringNull( ep ) )
        {
            ep = "1";
        }

        // 下拉大小
        String nz = notNull( ( String ) params.get( "nz" ) );

        if( StringUtil.isStringNull( nz ) )
        {
            nz = "0";
        }

        String flag = ( String ) params.get( "flag" );

        if( StringUtil.isStringNull( flag ) )
        {
            flag = "";
        }

        String typeId = ( String ) params.get( "typeId" );

        if( StringUtil.isStringNull( typeId ) )
        {
            typeId = "-1";
        }

        String infoId = ( String ) params.get( "infoId" );

        if( StringUtil.isStringNull( infoId ) )
        {
            infoId = "-1";
        }

        // String size = ( String ) params.get( "size" );
        //
        // if( StringUtil.isStringNull( size ) )
        // {
        // size = "500";
        // }

        String size = "2000";// 2000作为缓存筏值

        // 分页

        String page = ( String ) params.get( "page" );

        if( StringUtil.isStringNull( page ) )
        {
            page = "false";
        }

        List commendContent = null;

        Page pageInfo = null;

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

            int nextPage = StringUtil.getIntValue( ( String ) params.get( "pn" ), 1 );

            if( !pageMode )
            {
                nextPage = 1;
            }

            int pageSize = StringUtil.getIntValue( size, 500 );

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
                // this.pageContext.setAttribute(
                // "___system_dispose_page_object___", pageInfo );
            }

            SiteGroupBean site = ( SiteGroupBean ) request
                .getAttribute( Constant.CONTENT.HTML_PUB_CURRENT_SITE );

            if( site == null )
            {
                site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );
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
                    return "";
                }

                // 最后位置,目前推荐列表全部发布
                pageInfo.setEndPos( Integer.toString( pageInfo.getPageCount() ) );

                // 和管理不同,url的组装需要走模板url规则
                String url = site.getSiteUrl()
                // + siteTemplate 隐藏template
                    + StringUtil.replaceString( StringUtil.replaceString( typeBean
                        .getListTemplateUrl(), "{type-id}", typeId, false, false ), "{class-id}",
                        typeBean.getClassId().toString(), false, false );

                String prefixQuery = "?";
                if( StringUtil.isStringNotNull( typeBean.getListTemplateUrl() )
                    && typeBean.getListTemplateUrl().indexOf( "?" ) != -1 )
                {
                    prefixQuery = "&";
                }

                String nextQuery = "";
                String prevQuery = "";

                String nextQueryCd = null;

                nextQueryCd = new StringBuffer().append( "pn=" + ( pageInfo.getCurrentPage() + 1 ) )
                    .toString();

                pageInfo.setHeadQuery( new StringBuffer( url ).append( prefixQuery )
                    .append( "pn=1" ).toString() );

                pageInfo.setEndQuery( new StringBuffer( url ).append( prefixQuery ).append(
                    "pn=" + pageInfo.getPageCount() ).toString() );

                pageInfo.setJumpQuery( new StringBuffer( url ).toString() );

                pageInfo.setNextQueryCd( nextQueryCd );

                if( pageInfo.getCurrentPage() == pageInfo.getPageCount()
                    || pageInfo.getPageCount() == 0 )
                {
                    // 静态化最后一页,nextQueryActionUrl将为空

                    if( pageMode )
                    {
                        // request.setAttribute( "nextQueryActionUrl", null );
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
                        + StringUtil.replaceString( StringUtil.replaceString( typeBean
                            .getListTemplateUrl(), "{type-id}", typeId, false, false ),
                            "{class-id}", typeBean.getClassId().toString(), false, false );

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

            }
        }

        List result = new ArrayList();

        if( commendContent.isEmpty() )
        {
            Map resMap = new HashMap();

            resMap.put( "isEnd", true );

            resMap.put( "content", "" );

            return ( JSON.toJSONString( resMap ) );
        }

        int preEndPos = StringUtil.getIntValue( ep, 1 ) - 1; // 如7
        // 则为0~6

        int nextSize = StringUtil.getIntValue( nz, 3 );

        int end = preEndPos + nextSize;// 位6的接下来3笔数据,为7~9位

        boolean isEnd = false;

        for ( int i = preEndPos + 1; i <= end; i++ )
        {
            if( i >= commendContent.size() )
            {
                isEnd = true;
                break;
            }

            result.add( commendContent.get( i ) );
        }

        Map resMap = new HashMap();

        resMap.put( "isEnd", isEnd );

        resMap.put( "endPos", end + 1 );

        resMap.put( "content", result );

        return ( JSON.toJSONString( resMap ) );

    }

    public String notNull( String taregt )
    {
        String end = taregt;

        if( taregt == null )
        {
            end = "";
        }

        return end;

    }
}
