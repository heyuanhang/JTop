package cn.com.mjsoft.cms.content.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.cms.metadata.bean.PathInjectAssistBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.publish.bean.PublishRuleBean;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.persistence.core.support.MapValueCallback;
import cn.com.mjsoft.framework.util.StringUtil;

public class ContentValueResultCallBack implements MapValueCallback
{
    private static ChannelService channelService = ChannelService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    private Long siteId = null;

    private String siteFlag = null;

    private Long modelId = null;

    private Boolean apiMode = false;

    public ContentValueResultCallBack()
    {
    }

    public ContentValueResultCallBack( Long siteId, String siteFlag, Long modelId )
    {
        this.siteId = siteId;
        this.siteFlag = siteFlag;
        this.modelId = modelId;
    }

    public ContentValueResultCallBack( Long siteId, String siteFlag, Long modelId, Boolean apiMode )
    {
        this.siteId = siteId;
        this.siteFlag = siteFlag;
        this.modelId = modelId;
        this.apiMode = apiMode;
    }

    // 在单独非列表管理模式下，可无这些逻辑,需要独立一个
    @SuppressWarnings( "unchecked" )
    public void transformVlaue( Map result )
    {

        // 上线状态

        Timestamp tsStart = ( Timestamp ) result.get( "appearStartDateTime" );
        Timestamp tsEnd = ( Timestamp ) result.get( "appearEndDateTime" );

        // Long contentId = ( Long ) result.get( "contentId" );

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

        if( Constant.COMMON.OFF.equals( modelBean.getTitleMode() ) )
        {
            result.put( "title", result.get( modelBean.getTitleCol() ) );
        }

        if( tsStart != null )
        {
            if( Constant.CONTENT.MIN_DATE.equals( tsStart ) )
            {
                result.put( "appearStartDateTime", "" );

                result.put( "pubDate", "" );
            }
            else
            {
                result.put( "pubDate", tsStart );
            }

        }

        Timestamp fbsj = ( Timestamp ) result.get( "jt_fbsj" );

        if( fbsj != null )
        {
            result.put( "pubDate", fbsj );
        }

        if( tsEnd != null )
        {
            if( Constant.CONTENT.MAX_DATE.equals( tsEnd ) )
            {
                result.put( "appearEndDateTime", "" );
            }

        }

        // 文章分页处理,必须在url link处理之前

        Integer isPage = ( ( Integer ) result.get( "isPageContent" ) );

        if( modelBean != null
            && Constant.METADATA.MODEL_RES_ARTICLE.equals( modelBean.getModelResType() )
            && Constant.COMMON.ON.equals( isPage ) )
        {
            result.put( "isPage", Boolean.TRUE );

            // pos将作为系统关键字

            result.put( modelBean.getMainEditorFieldSign(), result.get( "pageContent" ) );
        }
        else
        {
            result.put( "isPage", Boolean.FALSE );
        }

        // url link
        ContentClassBean classBean = channelService
            .retrieveSingleClassBeanInfoByClassId( ( Long ) result.get( "classId" ) );

        SiteGroupBean site = null;

        if( siteId != null )
        {
            site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( siteId );
        }
        else if( siteFlag != null )
        {
            site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                .getEntry( siteFlag );
        }
        else
        {
            site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                .getEntry( classBean.getSiteFlag() );
        }

        if( site == null )
        {
            site = new SiteGroupBean();
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

                // 获取内容发布规则

                PublishRuleBean ruleBean = publishService.retrieveSinglePublishRuleBean( classBean
                    .getContentPublishRuleId() );

                Integer pos = ( Integer ) result.get( "pos" );

                if( pos == null )
                {
                    pos = 0;
                }

                if( StringUtil.isStringNotNull( staticUrl ) )
                {
                    result.put( "contentUrl", site.getSiteUrl()
                    // + site.getPublishRoot()
                        + staticUrl );

                    String[] pathInfo = ruleBean.getFullContentClassPagePublishPath( site,
                        classBean, result, null, pos );

                    result.put( "mobContentUrl", site.getSiteUrl()
                    // + site.getPublishRoot()
                        + pathInfo[3] );

                    result.put( "padContentUrl", site.getSiteUrl()
                    // + site.getPublishRoot()
                        + pathInfo[5] );
                }
                else
                {
                    // // 若内容静态URL不存在,则输出动态URL
                    // disposeUrlLink( result, site, classBean );

                    if( ruleBean == null )
                    {
                        result.put( "contentUrl", site.getSiteUrl()
                        // + site.getPublishRoot()
                            + "NO_RULE" );
                    }
                    else
                    {
                        // String[] pathInfo = ruleBean
                        // .getFullContentClassPublishPath( site, classBean,
                        // result, null );

                        String[] pathInfo = ruleBean.getFullContentClassPagePublishPath( site,
                            classBean, result, null, pos );
                        result.put( "contentUrl", site.getSiteUrl()
                        // + site.getPublishRoot()
                            + pathInfo[1] );

                        result.put( "mobContentUrl", site.getSiteUrl()
                        // + site.getPublishRoot()
                            + pathInfo[3] );

                        result.put( "padContentUrl", site.getSiteUrl()
                        // + site.getPublishRoot()
                            + pathInfo[5] );

                    }
                }

            }//
            else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( classBean
                .getContentProduceType() ) )
            {
                disposeUrlLink( result, site, classBean );
            }

            List filedBeanList = metaDataService.retrieveModelFiledInfoBeanList( classBean
                .getContentType() );

            // 获取自定义模型数据
            ModelFiledInfoBean filedInfoBean = null;

            for ( int j = 0; j < filedBeanList.size(); j++ )
            {
                filedInfoBean = ( ModelFiledInfoBean ) filedBeanList.get( j );

                // 内部业务字段不需要处理
                if( Constant.METADATA.INNER_DATA == filedInfoBean.getHtmlElementId().intValue() )
                {
                    continue;
                }

                if( StringUtil.isStringNotNull( filedInfoBean.getChoiceValue() ) )
                {
                    String[] strs = filedInfoBean.getChoiceValue().split( "," );

                    String[] kv = null;

                    Object obj = result.get( filedInfoBean.getFieldSign() );

                    String val = obj == null ? "" : obj.toString();

                    for ( int x = 0; x < strs.length; x++ )
                    {
                        kv = strs[x].split( "=" );

                        if( kv.length == 2 )
                        {

                            if( kv[1].toString().equals( val ) )
                            {
                                result.put( filedInfoBean.getFieldSign() + "SysKey", kv[0] );

                            }

                        }

                    }
                }

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

            if( StringUtil.isStringNotNull( fieldVal ) && fieldVal.length() > 9 )
            {
                // 原始数据,包含子数据
                result.put( pathAssistBean.getFieldName() + "CmsSysValue", fieldVal );

                if( Constant.SITE_CHANNEL.RES_IMAGE.equals( pathAssistBean.getResType() ) )
                {
                    String reUrl = StringUtil.subString( fieldVal,
                        fieldVal.indexOf( "reUrl=" ) + 6, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "reUrl=" ) + 6 ) );

                    result.put( pathAssistBean.getFieldName() + "CmsSysReUrl", reUrl );

                    String preUrl = site.getSiteImagePrefixUrl();

                    if( apiMode )
                    {
                        preUrl = site.getSiteImagePrefixUrlDMode();
                    }

                    result.put( pathAssistBean.getFieldName() + "Resize", preUrl
                        + StringUtil.replaceString( reUrl, "/", "/imgResize", false, false ) );

                    result.put( pathAssistBean.getFieldName(), preUrl + reUrl );

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

                    if( !"null".equals( reUrl ) && StringUtil.isStringNotNull( reUrl ) )
                    {
                        result.put( pathAssistBean.getFieldName() + "CmsSysCover", site
                            .getSiteImagePrefixUrl()
                            + reUrl );
                        
                        String preUrl = site.getSiteImagePrefixUrl();
                        
                        if( apiMode )
                        {
                            preUrl = site.getSiteImagePrefixUrlDMode();
                        }

                        result.put( pathAssistBean.getFieldName() + "CmsSysCoverResize", preUrl
                            + StringUtil.replaceString( reUrl, "/", "/imgResize", false, false ) );
                    }

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

                    result.put( pathAssistBean.getFieldName() + "MediaC", StringUtil
                        .isStringNotNull( coverReUrl ) ? site.getSiteImagePrefixUrl() + coverReUrl
                        : "" );

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

                    // 2016-7-19:文库
                    result.put( pathAssistBean.getFieldName() + "FileWkSwf", site
                        .getSiteFilePrefixUrl()
                        + reUrl + ".swf" );

                    if( reUrl.endsWith( ".pdf" ) )
                    {
                        result.put( pathAssistBean.getFieldName() + "FileWkPdf", site
                            .getSiteFilePrefixUrl()
                            + reUrl );
                    }
                    else
                    {
                        result.put( pathAssistBean.getFieldName() + "FileWkPdf", site
                            .getSiteFilePrefixUrl()
                            + reUrl + ".pdf" );
                    }

                    result.put( pathAssistBean.getFieldName() + "FileN", StringUtil.subString(
                        fieldVal, fieldVal.indexOf( "fn=" ) + 3, fieldVal.indexOf( ";", fieldVal
                            .indexOf( "fn=" ) + 3 ) ) );

                    String siteUrl = site.getSiteUrl();

                    if( siteUrl.endsWith( "/" + site.getSiteRoot() ) )
                    {
                        siteUrl = StringUtil.subString( siteUrl, 0, siteUrl.length()
                            - site.getSiteRoot().length() );
                    }

                    result.put( pathAssistBean.getFieldName() + "FileDown", siteUrl
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
        String contentImage = ( String ) result.get( "contentImage" );

        if( StringUtil.isStringNotNull( contentImage ) )
        {
            disposeSingleImageInfo( result, "contentImage", contentImage, site );
        }

        String classImage = ( String ) result.get( "classImage" );

        if( StringUtil.isStringNotNull( classImage ) )
        {
            disposeSingleImageInfo( result, "classImage", classImage, site );
        }

        String channelImage = ( String ) result.get( "channelImage" );

        if( StringUtil.isStringNotNull( channelImage ) )
        {
            disposeSingleImageInfo( result, "channelImage", channelImage, site );
        }

        String homeImage = ( String ) result.get( "homeImage" );

        if( StringUtil.isStringNotNull( homeImage ) )
        {
            disposeSingleImageInfo( result, "homeImage", homeImage, site );
        }

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
    private void disposeSingleImageInfo( Map result, String fieldName, String fieldVal,
        SiteGroupBean site )
    {
        String reUrl = StringUtil.subString( fieldVal, fieldVal.indexOf( "reUrl=" ) + 6, fieldVal
            .indexOf( ";", fieldVal.indexOf( "reUrl=" ) + 6 ) );

        if( StringUtil.isStringNull( reUrl ) )
        {
            reUrl = "core/style/blue/images/no-image.gif";
        }

        result.put( fieldName + "CmsSysReUrl", reUrl );

        result.put( fieldName + "CmsSysResize", site.getSiteImagePrefixUrl()
            + StringUtil.replaceString( reUrl, "/", "/imgResize", false, false ) );

        result.put( fieldName, site.getSiteImagePrefixUrl() + reUrl );

        result
            .put( fieldName + "ResId", StringUtil.subString( fieldVal,
                fieldVal.indexOf( "id=" ) + 3, fieldVal
                    .indexOf( ";", fieldVal.indexOf( "id=" ) + 3 ) ) );

        result
            .put( fieldName + "ImageW", StringUtil.subString( fieldVal,
                fieldVal.indexOf( "iw=" ) + 3, fieldVal
                    .indexOf( ";", fieldVal.indexOf( "iw=" ) + 3 ) ) );

        result
            .put( fieldName + "ImageH", StringUtil.subString( fieldVal,
                fieldVal.indexOf( "ih=" ) + 3, fieldVal
                    .indexOf( ";", fieldVal.indexOf( "ih=" ) + 3 ) ) );
    }

}
