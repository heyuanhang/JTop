package cn.com.mjsoft.cms.content.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.PathInjectAssistBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.publish.bean.PublishRuleBean;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.persistence.core.support.MapValueCallback;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;

public class ContentValueResultManageModeCallBack implements MapValueCallback
{
    private static ChannelService channelService = ChannelService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    private String siteFlag = null;

    private Long modelId = null;

    public ContentValueResultManageModeCallBack()
    {
    }

    public ContentValueResultManageModeCallBack( String siteFlag, Long modelId )
    {
        this.siteFlag = siteFlag;
        this.modelId = modelId;
    }

    //  在单独非列表管理模式下，可无这些逻辑,需要独立一个
    @SuppressWarnings( "unchecked" )
    public void transformVlaue( Map result )
    {
        Timestamp tsStart = ( Timestamp ) result.get( "appearStartDateTime" );
        Timestamp tsEnd = ( Timestamp ) result.get( "appearEndDateTime" );
        Timestamp addTime = ( Timestamp ) result.get( "addTime" );

        
        Long modelId = null;
        if( this.modelId != null )
        {
            modelId = this.modelId;
        }
        else
        {
            modelId = ( Long ) result.get( "modelId" );
        }

        DataModelBean modelBean = metaDataService.retrieveSingleDataModelBeanById( modelId );

        // 点击数
        // StatContentVisitDWMCount clickDWMCount = ( StatContentVisitDWMCount )
        // StatService.statCacheContentClickCountMap
        // .get( contentId );
        //
        // if( clickDWMCount != null )
        // {
        // result.put( "clickCount", Long.valueOf( ( ( Long ) result
        // .get( "clickCount" ) ).longValue()
        // + clickDWMCount.getNoLimitCount().longValue() ) );
        //
        // result.put( "clickDayCount", Long.valueOf( ( ( Long ) result
        // .get( "clickDayCount" ) ).longValue()
        // + clickDWMCount.getDayCount().longValue() ) );
        //
        // result.put( "clickWeekCount", Long.valueOf( ( ( Long ) result
        // .get( "clickWeekCount" ) ).longValue()
        // + clickDWMCount.getWeekCount().longValue() ) );
        //
        // result.put( "clickMonthCount", Long.valueOf( ( ( Long ) result
        // .get( "clickMonthCount" ) ).longValue()
        // + clickDWMCount.getMonthCount().longValue() ) );
        // }
        
        if(Constant.COMMON.OFF.equals( modelBean.getTitleMode() ))
        {
            result.put( "title", result.get( modelBean.getTitleCol() ) );
        }
        
        
        

        if( tsStart != null )
        {
            if( Constant.CONTENT.MIN_DATE.equals( tsStart ) )
            {
                result.put( "appearStartDateTime", "" );
            }
            else
            {
                result.put( "appearStartDateTime", DateAndTimeUtil.getFormatDate(
                    tsStart.getTime(), DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );
            }

        }

        if( tsEnd != null )
        {
            if( Constant.CONTENT.MAX_DATE.equals( tsEnd ) )
            {
                result.put( "appearEndDateTime", "" );
            }
            else
            {
                result.put( "appearEndDateTime", DateAndTimeUtil.getFormatDate( tsEnd.getTime(),
                    DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );
            }
        }

        if( addTime != null )
        {
            result.put( "addTime", DateAndTimeUtil.getFormatDate( addTime.getTime(),
                DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );

            result.put( "addDate", DateAndTimeUtil.getFormatDate( addTime.getTime(),
                DateAndTimeUtil.DEAULT_FORMAT_MD ) );
        }

        // 文章分页处理,必须在url link处理之前

        Integer isPage = ( ( Integer ) result.get( "isPageContent" ) );

        if( modelBean != null
            && Constant.METADATA.MODEL_RES_ARTICLE.equals( modelBean.getModelResType() )
            && Constant.COMMON.ON.equals( isPage ) )
        {
            result.put( "isPage", Boolean.TRUE );
        }
        else
        {
            result.put( "isPage", Boolean.FALSE );
        }
        
        long cId = ( Long ) result.get( "classId" );
        
        if( cId  == -2)
        {
            cId = ContentService.getInstance().retrieveSingleContentMainInfoBean(  (Long ) result.get( "refCid" ) ).getClassId();
        }

        // url link
        ContentClassBean classBean = channelService
            .retrieveSingleClassBeanInfoByClassId( cId );

        SiteGroupBean site = null;
        if( siteFlag != null )
        {
            site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                .getEntry( siteFlag );
        }
        else
        {
            site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                .getEntry( classBean.getSiteFlag() );
        }

        // 每个内容必须对应有其所属的栏目
        if( classBean != null || classBean.getClassId().longValue() > 0 )
        {
            // SystemRuntimeConfig config = SystemConfiguration.getInstance()
            // .getSystemConfig();
            // String base = config.getSystemBasePath();

            // 外部地址
            if( StringUtil.isStringNotNull( ( String ) result.get( "outLink" ) ) )
            {
                result.put( "contentUrl", result.get( "outLink" ) );
            }
            else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean
                .getContentProduceType() ) ) // HTML
            {
                String staticUrl = ( String ) result.get( "staticPageUrl" );

                if( StringUtil.isStringNotNull( staticUrl ) )
                {
                    result.put( "contentUrl", site.getSiteUrl()
                    // + site.getPublishRoot()
                        + staticUrl );
                }
                else
                {
                    // // 若内容静态URL不存在,则输出动态URL
                    // disposeUrlLink( result, site, classBean );

                    // 获取内容发布规则

                    PublishRuleBean ruleBean = publishService
                        .retrieveSinglePublishRuleBean( classBean.getContentPublishRuleId() );

                    if( ruleBean == null )
                    {
                        result.put( "contentUrl", site.getSiteUrl()
                        // + site.getPublishRoot()
                            + "NO_RULE" );
                    }
                    else
                    {
                        String[] pathInfo = ruleBean.getFullContentClassPublishPath( site,
                            classBean, result, null );
                        result.put( "contentUrl", site.getSiteUrl()
                        // + site.getPublishRoot()
                            + pathInfo[1] );
                    }
                }

            }//
            else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( classBean
                .getContentProduceType() ) )
            {
                disposeUrlLink( result, site, classBean );
            }
        }

        // 资源字段url处理,以及对应参数处理

        List pathInjectAssistList = metaDataService.retrieveContentPathInjectAssistInfo( modelId );

        PathInjectAssistBean pathAssistBean = null;
        for ( int i = 0; i < pathInjectAssistList.size(); i++ )
        {
            pathAssistBean = ( PathInjectAssistBean ) pathInjectAssistList.get( i );

            Object temp = result.get( pathAssistBean.getFieldName() );

            String fieldVal = temp == null ? "" : temp.toString();

            if( StringUtil.isStringNotNull( fieldVal ) && fieldVal.length() > 9 && fieldVal.startsWith( "id=" ))
            {
                // 原始数据,包含子数据
                result.put( pathAssistBean.getFieldName() + "CmsSysValue", fieldVal );

                if( Constant.SITE_CHANNEL.RES_IMAGE.equals( pathAssistBean.getResType() ) )
                {
                    String reUrl = StringUtil.subString( fieldVal,
                        fieldVal.indexOf( "reUrl=" ) + 6, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "reUrl=" ) + 6 ) );

                    result.put( pathAssistBean.getFieldName() + "CmsSysReUrl", reUrl );

                    result.put( pathAssistBean.getFieldName() + "Resize", site
                        .getSiteImagePrefixUrl()
                        + StringUtil.replaceString( reUrl, "/", "/imgResize", false, false ) );

                    result
                        .put( pathAssistBean.getFieldName(), site.getSiteImagePrefixUrl() + reUrl );

                    result.put( pathAssistBean.getFieldName() + "ResId", StringUtil.subString(
                        fieldVal, fieldVal.indexOf( "id=" ) + 3, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "id=" ) + 3 ) ) );

                    result.put( pathAssistBean.getFieldName() + "ImageW", StringUtil.subString(
                        fieldVal, fieldVal.indexOf( "iw=" ) + 3, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "iw=" ) + 3 ) ) );

                    result.put( pathAssistBean.getFieldName() + "ImageH", StringUtil.subString(
                        fieldVal, fieldVal.indexOf( "ih=" ) + 3, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "ih=" ) + 3 ) ) );

                }
                else if( Constant.SITE_CHANNEL.RES_IMG_GROUP.equals( pathAssistBean.getResType() ) )
                {
                    String reUrl = StringUtil.subString( fieldVal,
                        fieldVal.indexOf( "reUrl=" ) + 6, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "reUrl=" ) + 6 ) );

                    result.put( pathAssistBean.getFieldName() + "CmsSysCover", site
                        .getSiteImagePrefixUrl()
                        + reUrl );

                    result.put( pathAssistBean.getFieldName() + "CmsSysCount", StringUtil
                        .subString( fieldVal, fieldVal.indexOf( "count=" ) + 6, fieldVal.indexOf(
                            ";", fieldVal.indexOf( "count=" ) + 6 ) ) );

                }
                else if( Constant.SITE_CHANNEL.RES_MEDIA.equals( pathAssistBean.getResType() ) )
                {
                    String reUrl = StringUtil.subString( fieldVal,
                        fieldVal.indexOf( "reUrl=" ) + 6, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "reUrl=" ) + 6 ) );

                    String coverReUrl = StringUtil.subString( fieldVal,
                        fieldVal.indexOf( "vc=" ) + 3, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "vc=" ) + 3 ) );

                    result.put( pathAssistBean.getFieldName() + "CmsSysReUrl", reUrl );

                    result.put( pathAssistBean.getFieldName() + "CmsSysCoverReUrl", coverReUrl );

                    result
                        .put( pathAssistBean.getFieldName(), site.getSiteMediaPrefixUrl() + reUrl );

                    result.put( pathAssistBean.getFieldName() + "ResId", StringUtil.subString(
                        fieldVal, fieldVal.indexOf( "id=" ) + 3, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "id=" ) + 3 ) ) );

                    result.put( pathAssistBean.getFieldName() + "MediaT", StringUtil.subString(
                        fieldVal, fieldVal.indexOf( "vt=" ) + 3, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "vt=" ) + 3 ) ) );

                    result.put( pathAssistBean.getFieldName() + "MediaW", StringUtil.subString(
                        fieldVal, fieldVal.indexOf( "vw=" ) + 3, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "vw=" ) + 3 ) ) );

                    result.put( pathAssistBean.getFieldName() + "MediaH", StringUtil.subString(
                        fieldVal, fieldVal.indexOf( "vh=" ) + 3, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "vh=" ) + 3 ) ) );

                    result.put( pathAssistBean.getFieldName() + "MediaC", site
                        .getSiteImagePrefixUrl()
                        + coverReUrl );

                }
                else if( Constant.SITE_CHANNEL.RES_FILE.equals( pathAssistBean.getResType() ) )
                {
                    String reUrl = StringUtil.subString( fieldVal,
                        fieldVal.indexOf( "reUrl=" ) + 6, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "reUrl=" ) + 6 ) );

                    result.put( pathAssistBean.getFieldName() + "CmsSysReUrl", reUrl );

                    result.put( pathAssistBean.getFieldName(), StringUtil.subString( fieldVal,
                        fieldVal.indexOf( "id=" ) + 3, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "id=" ) + 3 ) ) );

                    result.put( pathAssistBean.getFieldName() + "FileUrl", site
                        .getSiteFilePrefixUrl()
                        + reUrl );

                    result.put( pathAssistBean.getFieldName() + "FileN", StringUtil.subString(
                        fieldVal, fieldVal.indexOf( "fn=" ) + 3, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "fn=" ) + 3 ) ) );

                    result.put( pathAssistBean.getFieldName() + "FileDown", site.getSiteUrl()
                        + "content/clientDf.do?id="
                        + StringUtil.subString( fieldVal, fieldVal.indexOf( "id=" ) + 3, fieldVal
                            .indexOf( ";", fieldVal.indexOf( "id=" ) + 3 ) ) );
                }
                else if( Constant.SITE_CHANNEL.RES_DATETIME.equals( pathAssistBean.getResType() ) )
                {
                    // // 处理时间类型,去掉毫秒
                    // int lastPos = fieldVal.lastIndexOf( "." );
                    //
                    // if( lastPos > 0 )
                    // {
                    // result.put( pathAssistBean.getFieldName(), StringUtil
                    // .subString( fieldVal, 0, lastPos ) );
                    // }
                }
            }
        }

        /**
         * 各引导图片信息处理
         */

        disposeSingleImageInfo( result, "contentImage", site );

        disposeSingleImageInfo( result, "classImage", site );

        disposeSingleImageInfo( result, "channelImage", site );

        disposeSingleImageInfo( result, "homeImage", site );

    }

    private void disposeUrlLink( Map result, SiteGroupBean site, ContentClassBean classBean )
    {
        String contentTemplateUrl = ( String ) result.get( "especialTemplateUrl" );

        String mobContentTemplateUrl = classBean.getMobContentTemplateUrl();

        String padContentTemplateUrl = classBean.getPadContentTemplateUrl();

        if( StringUtil.isStringNull( contentTemplateUrl ) )
        {
            // 如果当前的单个内容没有特殊模斑则将取栏目共用模版
            contentTemplateUrl = classBean.getContentTemplateUrl();
            // if( StringUtil.isStringNull( contentTemplateUrl ) )
            // {
            // log.error( "[ContentLinkTag] 内容模型名称丢失,Info:"
            // + contentInfo );
            // return EVAL_BODY_INCLUDE;
            // }
        }

        if( site != null )
        {
            // if( site.isNotHost() )
            {
                result.put( "contentUrl", site.getSiteUrl()
                // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                    // + Constant.CONTENT.URL_SEP
                    + StringUtil.replaceString( contentTemplateUrl, "{content-id}",
                        ( ( Long ) result.get( "contentId" ) ).toString(), false, false ) );

                // 移动

                result.put( "mobContentUrl", site.getSiteUrl()
                // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                    // + Constant.CONTENT.URL_SEP
                    + StringUtil.replaceString( mobContentTemplateUrl, "{content-id}",
                        ( ( Long ) result.get( "contentId" ) ).toString(), false, false ) );

                result.put( "padContentUrl", site.getSiteUrl()
                // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                    // + Constant.CONTENT.URL_SEP
                    + StringUtil.replaceString( padContentTemplateUrl, "{content-id}",
                        ( ( Long ) result.get( "contentId" ) ).toString(), false, false ) );
            }
            // else
            // {
            // result.put( "contentUrl", site.getSiteUrl()
            // // + site.getSiteRoot()
            // // + Constant.CONTENT.URL_SEP
            // + Constant.CONTENT.TEMPLATE_BASE
            // + Constant.CONTENT.URL_SEP
            // + StringUtil.replaceString( contentTemplateUrl,
            // "{content-id}", ( ( Long ) result
            // .get( "contentId" ) ).toString(), false,
            // false ) );
            // }

        }

    }

    @SuppressWarnings( "unchecked" )
    private void disposeSingleImageInfo( Map result, String fieldName, SiteGroupBean site )
    {
        String fieldVal = ( String ) result.get( fieldName );

        if( StringUtil.isStringNull( fieldVal ) )
        {
            String reUrl = "core/style/blue/images/no-image.gif";

            result.put( fieldName + "CmsSysReUrl", reUrl );

            result
                .put( fieldName + "CmsSysResize", JtRuntime.cmsServer.getDomainFullPath() + reUrl );

            result.put( fieldName, site.getSiteUrl() + reUrl );

            result.put( fieldName + "ResId", "-1" );
        }
        else
        {
            String reUrl = StringUtil.subString( fieldVal, fieldVal.indexOf( "reUrl=" ) + 6,
                fieldVal.indexOf( ";", fieldVal.indexOf( "reUrl=" ) + 6 ) );

            if( StringUtil.isStringNull( reUrl ) )
            {
                reUrl = "core/style/blue/images/no-image.gif";
            }

            result.put( fieldName + "CmsSysReUrl", reUrl );

            result.put( fieldName + "CmsSysResize", site.getSiteImagePrefixUrl()
                + StringUtil.replaceString( reUrl, "/", "/imgResize", false, false ) );

            result.put( fieldName, site.getSiteImagePrefixUrl() + reUrl );

            result.put( fieldName + "ResId", StringUtil.subString( fieldVal, fieldVal
                .indexOf( "id=" ) + 3, fieldVal.indexOf( ";", fieldVal.indexOf( "id=" ) + 3 ) ) );

            result.put( fieldName + "ImageW", StringUtil.subString( fieldVal, fieldVal
                .indexOf( "iw=" ) + 3, fieldVal.indexOf( ";", fieldVal.indexOf( "iw=" ) + 3 ) ) );

            result.put( fieldName + "ImageH", StringUtil.subString( fieldVal, fieldVal
                .indexOf( "ih=" ) + 3, fieldVal.indexOf( ";", fieldVal.indexOf( "ih=" ) + 3 ) ) );
        }

    }

}
