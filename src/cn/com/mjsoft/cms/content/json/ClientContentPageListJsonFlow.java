package cn.com.mjsoft.cms.content.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.bean.ContentAssistantPageInfoBean;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientContentPageListJsonFlow extends ApiFlowDisposBaseFlow
{
    private Logger log = Logger.getLogger( ClientContentPageListJsonFlow.class );

    private static final long serialVersionUID = -7423672300015623712L;

    private static ContentService contentService = ContentService.getInstance();

    private static ChannelService channelService = ChannelService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        // 内容ID
        String idV = StringUtil.notNull( ( String ) params.get( "id" ) );

        Long id = Long.valueOf( StringUtil.getLongValue( idV, -1 ) );

        if( id.longValue() < 0 )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        Map info = contentService.retrieveSingleUserDefineContent( id, Integer
            .valueOf( 1 ) );

        if( info == null || info.isEmpty() )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        Long contentId = ( Long ) info.get( "contentId" );

        Integer isPage = ( ( Integer ) info.get( "isPageContent" ) );

        if( !Constant.COMMON.ON.equals( isPage ) )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        Map jsonResult = new HashMap();

        ContentClassBean classBean = channelService
            .retrieveSingleClassBeanInfoByClassId( ( Long ) info
                .get( "classId" ) );

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( classBean.getSiteFlag() );

        // 取内容
        List result = contentService
            .retrieveAllContentAssistantPageInfoByContentId( contentId, info,
                classBean, site );

        jsonResult.put( "pageInfoList", result );

        jsonResult.put( "info", info );

        int currentPos = 0;

        int pageCount = 0;

        if( !result.isEmpty() )
        {

            pageCount = ( ( ContentAssistantPageInfoBean ) result.get( result
                .size() - 1 ) ).getPos().intValue();

            // 最后一条记录的pos为分页内容总数量
            info.put( "pageCount", Integer.valueOf( pageCount ) );

            currentPos = info.get( "pos" ) != null ? ( ( Integer ) info
                .get( "pos" ) ).intValue() : Integer.valueOf( 1 ).intValue();

        }

        if( !result.isEmpty()
            && ( classBean != null || classBean.getClassId().longValue() > 0 ) )
        {
            if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean
                .getContentProduceType() ) )
            {
                if( currentPos == pageCount )
                {
                    // 最后一页
                    info.put( "nextPageUrl", site.getSiteUrl()
                        + ( ( ContentAssistantPageInfoBean ) result
                            .get( currentPos - 1 ) ).getPageStaticUrl() );
                    if( currentPos != 1 )
                    {
                        info.put( "prevPageUrl", site.getSiteUrl()
                            + ( ( ContentAssistantPageInfoBean ) result
                                .get( currentPos - 2 ) ).getPageStaticUrl() );
                    }
                    else
                    {
                        info.put( "prevPageUrl", site.getSiteUrl()
                            + ( ( ContentAssistantPageInfoBean ) result
                                .get( currentPos - 1 ) ).getPageStaticUrl() );
                    }
                }
                else if( currentPos == 1 )
                {
                    info.put( "nextPageUrl", site.getSiteUrl()
                        + ( ( ContentAssistantPageInfoBean ) result
                            .get( currentPos ) ).getPageStaticUrl() );
                    info.put( "prevPageUrl", site.getSiteUrl()
                        + ( ( ContentAssistantPageInfoBean ) result.get( 0 ) )
                            .getPageStaticUrl() );

                }
                else
                {
                    info.put( "nextPageUrl", site.getSiteUrl()
                        + ( ( ContentAssistantPageInfoBean ) result
                            .get( currentPos ) ).getPageStaticUrl() );
                    info.put( "prevPageUrl", site.getSiteUrl()
                        + ( ( ContentAssistantPageInfoBean ) result
                            .get( currentPos - 2 ) ).getPageStaticUrl() );
                }
            }
            else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE
                .equals( classBean.getContentProduceType() ) )
            {

                String contentTemplateUrl = ( String ) info
                    .get( "especialTemplateUrl" );

                if( StringUtil.isStringNull( contentTemplateUrl ) )
                {
                    // 如果当前的单个内容没有特殊模斑则将取栏目共用模版
                    contentTemplateUrl = classBean.getContentTemplateUrl();
                    // if( StringUtil.isStringNull( contentTemplateUrl )
                    // )
                    // {
                    // log.error( "[ContentLinkTag] 内容模型名称丢失,Info:"
                    // + contentInfo );
                    // return EVAL_BODY_INCLUDE;
                    // }
                }
                String endUrl = site.getSiteUrl()

                    + StringUtil.replaceString( contentTemplateUrl,
                        "{content-id}", ( ( Long ) info.get( "contentId" ) )
                            .toString(), false, false );

                String next = null;
                String prev = null;

                if( endUrl.indexOf( ".jsp?" ) != -1 )
                {
                    // 有参数

                    if( currentPos == pageCount )
                    {
                        next = endUrl + "&pn=" + currentPos;
                        if( currentPos != 1 )
                        {
                            prev = endUrl + "&pn="
                                + Integer.valueOf( currentPos - 1 );
                        }
                        else
                        {
                            prev = endUrl + "&pn=" + Integer.valueOf( 1 );
                        }
                    }
                    else if( currentPos == 1 )
                    {
                        next = endUrl + "&pn="
                            + Integer.valueOf( currentPos + 1 );
                        prev = endUrl + "&pn=" + currentPos;

                    }
                    else
                    {
                        next = endUrl + "&pn="
                            + Integer.valueOf( currentPos + 1 );
                        prev = endUrl + "&pn="
                            + Integer.valueOf( currentPos - 1 );
                    }
                }
                else
                {
                    if( currentPos == pageCount )
                    {
                        next = endUrl + "?pn=" + currentPos;
                        prev = endUrl + "?pn="
                            + Integer.valueOf( currentPos - 1 );
                    }
                    else if( currentPos == 1 )
                    {
                        next = endUrl + "?pn="
                            + Integer.valueOf( currentPos + 1 );
                        prev = endUrl + "?pn=" + currentPos;

                    }
                    else
                    {
                        next = endUrl + "?pn="
                            + Integer.valueOf( currentPos + 1 );
                        prev = endUrl + "?pn="
                            + Integer.valueOf( currentPos - 1 );
                    }
                }

                info.put( "nextPageUrl", next );
                info.put( "prevPageUrl", prev );
            }

        }

        return ( JSON.toJSONString( jsonResult ) );

    }

}
