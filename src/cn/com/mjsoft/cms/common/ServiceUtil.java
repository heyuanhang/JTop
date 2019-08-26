package cn.com.mjsoft.cms.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.content.dao.vo.PhotoGroupInfo;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.resources.bean.SiteResourceBean;
import cn.com.mjsoft.cms.resources.dao.vo.SiteResource;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.ImageUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

public class ServiceUtil
{
    private static Logger log = Logger.getLogger( ServiceUtil.class );

    private final static Whitelist user_content_filter = Whitelist.relaxed();

    static
    {
        user_content_filter.addTags( "embed", "object", "param", "span", "div" );
        user_content_filter.addAttributes( ":all", "style", "class", "id", "name" );
        user_content_filter.addAttributes( "object", "width", "height", "classid", "codebase" );
        user_content_filter.addAttributes( "param", "name", "value" );
        user_content_filter.addAttributes( "embed", "src", "quality", "width", "height",
            "allowFullScreen", "allowScriptAccess", "flashvars", "name", "type", "pluginspage" );
    }

    private static ResourcesService resService = ResourcesService.getInstance();

    private static SiteGroupService siteService = SiteGroupService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    private static ContentService contentService = ContentService.getInstance();

    /**
     * 将传入的layerLinear增加一个节点单位
     * 
     * @param layerLinear
     * @param parent 是否传入的是父节点的layer flag
     * @return
     */
    public static String increaseLayerLinear( String layerLinear, boolean parent )
    {
        if( StringUtil.isStringNull( layerLinear ) )
        {
            throw new FrameworkException( "layer Linear信息丢失" );
        }

        if( parent )
        {
            return layerLinear + "001";
        }

        String prefix = StringUtil.subString( layerLinear, 0, layerLinear.trim().length() - 3 );

        String targetLayerFlag = StringUtil
            .subString( layerLinear, layerLinear.trim().length() - 3 );

        int flagIntVar = StringUtil.getIntValue( targetLayerFlag, -99999 );

        if( flagIntVar == -99999 )
        {
            log.error( "layer Linear信息错误, targetLayerFlag:" + targetLayerFlag );
            throw new FrameworkException( "layer Linear信息错误,end flag:" + flagIntVar );
        }

        flagIntVar++;

        if( flagIntVar >= Constant.SITE_CHANNEL.LIMIT_CLASS_SIZE )
        {
            throw new FrameworkException( "当前节点数量已达到最大, next:" + flagIntVar );
        }

        return prefix + getEndLayerFlag( flagIntVar );
    }

    /**
     * 处理最后3位标志
     * 
     * @param nextFlag
     * @param linearFlagStringPrefix
     * @return
     */
    public static String getEndLayerFlag( int nextFlag )
    {
        String tmp = nextFlag + "";
        int zear = 3 - tmp.length();

        if( zear == 2 )
        {
            return "00" + nextFlag;
        }
        else if( zear == 1 )
        {
            return "0" + nextFlag;
        }
        else
        // 满3位
        {
            return "" + nextFlag;
        }

    }

    /**
     * 找寻自定义字段中的富文本字段
     * 
     * @param filedBeanList
     * @return
     */
    public static Set checkEditorField( List filedBeanList )
    {
        if( filedBeanList == null )
        {
            return new HashSet();
        }

        Set htmlParamSet = new HashSet();

        ModelFiledInfoBean bean = null;
        for ( int i = 0; i < filedBeanList.size(); i++ )
        {
            bean = ( ModelFiledInfoBean ) filedBeanList.get( i );
            // 富文本框允许部分一些html
            if( Constant.METADATA.EDITER == bean.getHtmlElementId().intValue() )
            {
                htmlParamSet.add( bean.getFieldSign() );
                htmlParamSet.add( bean.getFieldSign() + ServletUtil.HIDDEN_TEMP );
            }
        }

        return htmlParamSet;
    }

    /**
     * 根据模型元数据信息，处理来自字段的输入信息
     * 
     * @param filedInfoBean
     * @param params
     * @return
     */
    @SuppressWarnings( "unchecked" )
    public static Object disposeDataModelFiledFromWeb( ModelFiledInfoBean filedInfoBean,
        Map params, List needUploadImageInfoList, boolean utf8Mode )
    {
        if( params.get( "_sys_content_image_res_count_" ) == null )
        {
            params.put( "_sys_content_image_res_count_", Integer.valueOf( 0 ) );
        }

        if( params.get( "_sys_content_video_res_count_" ) == null )
        {
            params.put( "_sys_content_video_res_count_", Integer.valueOf( 0 ) );
        }

        int imageCount = ( ( Integer ) params.get( "_sys_content_image_res_count_" ) ).intValue();

        int videoCount = ( ( Integer ) params.get( "_sys_content_video_res_count_" ) ).intValue();

        // 需删除资源
        if( params.get( "_sys_content_image_res_del_count_" ) == null )
        {
            params.put( "_sys_content_image_res_del_count_", Integer.valueOf( 0 ) );
        }

        if( params.get( "_sys_content_video_res_del_count_" ) == null )
        {
            params.put( "_sys_content_video_res_del_count_", Integer.valueOf( 0 ) );
        }

        int imageDelCount = ( ( Integer ) params.get( "_sys_content_image_res_del_count_" ) )
            .intValue();

        int videoDelCount = ( ( Integer ) params.get( "_sys_content_video_res_del_count_" ) )
            .intValue();

        Object targetParam = null;
        Long oldRes = null;
        String oldCover = null;

        // 先处特殊的
        // 时间类型
        if( Constant.METADATA.DATETIME == filedInfoBean.getHtmlElementId().intValue()
            || Constant.METADATA.MYSQL_DATETIME.equals( filedInfoBean.getPerdureType() ) )
        {
            targetParam = params.get( filedInfoBean.getFieldSign() );

            if( "null".equals( targetParam ) )
            {
                return null;
            }

            Timestamp inputDateTime = DateAndTimeUtil.getNotNullTimestamp( ( String ) targetParam,
                DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );
            return inputDateTime;
        }
        else if( Constant.METADATA.DATE == filedInfoBean.getHtmlElementId().intValue()
            || Constant.METADATA.MYSQL_DATE.equals( filedInfoBean.getPerdureType() ) )
        {
            targetParam = params.get( filedInfoBean.getFieldSign() );
            if( "null".equals( targetParam ) )
            {
                return null;
            }

            Timestamp inputDateTime = DateAndTimeUtil.getNotNullTimestamp( ( String ) targetParam,
                DateAndTimeUtil.DEAULT_FORMAT_YMD );
            return inputDateTime;
        }
        else if( Constant.METADATA.TIME == filedInfoBean.getHtmlElementId().intValue()
            || Constant.METADATA.MYSQL_TIME.equals( filedInfoBean.getPerdureType() ) )
        {
            targetParam = params.get( filedInfoBean.getFieldSign() );

            if( "null".equals( targetParam ) )
            {
                return null;
            }

            Timestamp inputDateTime = DateAndTimeUtil.getNotNullTimestamp( ( String ) targetParam,
                DateAndTimeUtil.DEAULT_FORMAT_HMS );
            return inputDateTime;
        }
        else if( Constant.METADATA.MYSQL_INT.equals( filedInfoBean.getPerdureType() ) )
        {
            targetParam = params.get( filedInfoBean.getFieldSign() );

            if( targetParam instanceof Integer )
            {
                return targetParam;
            }
            else
            {
                return targetParam == null ? Integer.valueOf( 0 ) : StringUtil.getIntValue(
                    targetParam.toString(), 0 );
            }
        }
        else if( Constant.METADATA.MYSQL_BIGINT.equals( filedInfoBean.getPerdureType() ) )
        {
            targetParam = params.get( filedInfoBean.getFieldSign() );

            if( targetParam instanceof Long )
            {
                return targetParam;
            }
            else
            {
                return targetParam == null ? Long.valueOf( 0 ) : StringUtil.getLongValue(
                    targetParam.toString(), 0 );
            }
        }
        else if( Constant.METADATA.MYSQL_DOUBLE.equals( filedInfoBean.getPerdureType() ) )
        {
            targetParam = params.get( filedInfoBean.getFieldSign() );

            if( targetParam instanceof Float || targetParam instanceof Double )
            {
                return targetParam;
            }
            else
            {
                return targetParam == null ? Double.valueOf( 0.0 ) : StringUtil.getDoubleValue(
                    targetParam.toString(), 0 );
            }
        }
        else if( Constant.METADATA.UPLOAD_MEDIA == filedInfoBean.getHtmlElementId().intValue() )
        {
            targetParam = params.get( filedInfoBean.getFieldSign() );

            Long resId = Long.valueOf( StringUtil.getLongValue( ( String ) targetParam, -1 ) );

            oldRes = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( filedInfoBean
                .getFieldSign()
                + "_sys_jtopcms_old" ), -1 ) );

            oldCover = ( String ) params.get( filedInfoBean.getFieldSign()
                + "_sys_jtopcms_old_cover" );

            // 需要删除
            SiteResourceBean oldResBean = resService.retrieveSingleResourceBeanByResId( oldRes );

            if( oldRes.longValue() > 0 && !oldRes.equals( resId ) )
            {
                if( oldResBean != null )// 此时已经产生了新的res对象
                {
                    resService.updateSiteResourceTraceUseStatus( oldResBean.getResId(),
                        Constant.COMMON.OFF );

                    Integer dc = ( Integer ) params.get( "_sys_content_video_res_del_count_" );

                    dc++;

                    params.put( "_sys_content_video_res_del_count_", Integer.valueOf( dc ) );

                    oldCover = StringUtil.isStringNull( oldResBean.getCover() ) ? "" : oldResBean
                        .getCover();

                    if( StringUtil.isStringNotNull( oldResBean.getCover() ) )
                    {
                        SiteResourceBean coverResBean = resService
                            .retrieveSingleResourceBeanBySource( oldCover );

                        if( coverResBean != null )
                        {
                            resService.updateSiteResourceTraceUseStatus( coverResBean.getResId(),
                                Constant.COMMON.OFF );
                        }
                    }
                }
            }

            SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( resId );

            if( resBean == null )
            {
                return "";
            }
            else
            {
                Map existResTrace = resService.retrieveSingleResUseStatus( resId );

                if( Constant.COMMON.OFF.equals( existResTrace.get( "isUse" ) ) )
                {
                    videoCount++;

                    params.put( "_sys_content_video_res_count_", Integer.valueOf( videoCount ) );
                }

                // 更新视频文件使用状态
                resService
                    .updateSiteResourceTraceUseStatus( resBean.getResId(), Constant.COMMON.ON );

                if( oldRes.equals( resId ) ) // 文件没有变化的更新操作,需要进一步检查cover是否变化
                {
                    if( oldResBean != null )// 此时的old res对象并没有改变,但cover有可能改变
                    {
                        if( oldResBean.getCover() != null
                            && !oldResBean.getCover().equals( oldCover ) )// 如果cover文件发生变化
                        {
                            if( StringUtil.isStringNotNull( oldCover ) )// 如果旧的cover存在
                            {
                                SiteResourceBean coverResBean = resService
                                    .retrieveSingleResourceBeanBySource( oldCover );

                                if( coverResBean != null )
                                {
                                    resService.updateSiteResourceTraceUseStatus( coverResBean
                                        .getResId(), Constant.COMMON.OFF );
                                }
                            }
                        }
                    }
                }

                String[] hw = StringUtil.split( resBean.getResolution(), "x" );

                String vw = "";
                String vh = "";

                if( hw.length == 2 )
                {
                    vw = hw[0];
                    vh = hw[1];
                }

                String cover = StringUtil.isStringNull( resBean.getCover() ) ? "" : resBean
                    .getCover();

                if( StringUtil.isStringNotNull( resBean.getCover() ) )
                {
                    SiteResourceBean coverResBean = resService
                        .retrieveSingleResourceBeanBySource( cover );

                    if( coverResBean != null )
                    {
                        resService.updateSiteResourceTraceUseStatus( coverResBean.getResId(),
                            Constant.COMMON.ON );
                    }
                }

                return "id=" + resBean.getResId() + ";t=m" + ";sid=" + resBean.getSiteId()
                    + ";reUrl=" + resBean.getResSource() + ";vc=" + cover + ";vt="
                    + resBean.getDuration() + ";vw=" + vw + ";vh=" + vh + ";";

            }
        }
        else if( Constant.METADATA.UPLOAD_IMG == filedInfoBean.getHtmlElementId().intValue() )
        {
            targetParam = params.get( filedInfoBean.getFieldSign() );

            Long resId = Long.valueOf( StringUtil.getLongValue( ( String ) targetParam, -1 ) );

            disposeOldImageInfo( resId, filedInfoBean.getFieldSign(), params );

            Map existResTrace = resService.retrieveSingleResUseStatus( resId );

            if( Constant.COMMON.OFF.equals( existResTrace.get( "isUse" ) ) )
            {
                imageCount++;

                params.put( "_sys_content_image_res_count_", Integer.valueOf( imageCount ) );
            }

            String resInfo = disposeSingleImageInfo( resId );

            return resInfo;
        }
        else if( Constant.METADATA.UPLOAD_IMG_GROUP == filedInfoBean.getHtmlElementId().intValue() )
        {
            // 获取提交的图集信息,并放入待处理列表

            Object[] res = disposePhotoGroupInfoParam( filedInfoBean.getFieldSign(), params,
                filedInfoBean.getNeedMark() );

            List fieldImageGroupList = ( List ) res[0];

            imageCount = imageCount + ( ( Integer ) res[1] ).intValue();

            params.put( "_sys_content_image_res_count_", Integer.valueOf( imageCount ) );

            needUploadImageInfoList.addAll( fieldImageGroupList );

            String coverResPath = ( String ) params.get( filedInfoBean.getFieldSign()
                + "CmsSysImageCover" );

            // 若没有设定封面图,则将第一张图片作为封面

            if( StringUtil.isStringNull( coverResPath ) )
            {
                if( !needUploadImageInfoList.isEmpty() )
                {
                    PhotoGroupInfo firstImage = ( ( PhotoGroupInfo ) needUploadImageInfoList
                        .get( 0 ) );

                    String imageInfo = firstImage.getUrl();

                    String reUrl = StringUtil.subString( imageInfo,
                        imageInfo.indexOf( "reUrl=" ) + 6, imageInfo.indexOf( ";", imageInfo
                            .indexOf( "reUrl=" ) + 6 ) );
                    coverResPath = reUrl;

                    firstImage.setIsCover( Constant.COMMON.ON );
                }
            }

            return "count="
                + ( StringUtil.getIntValue( ( String ) params.get( filedInfoBean.getFieldSign()
                    + "CmsSysImageCurrentCount" ), 0 ) ) + ";gid=" + filedInfoBean.getFieldSign()
                + ";reUrl=" + coverResPath + ";";

        }
        else if( Constant.METADATA.UPLOAD_FILE == filedInfoBean.getHtmlElementId().intValue() )
        {
            targetParam = params.get( filedInfoBean.getFieldSign() );

            Long resId = Long.valueOf( StringUtil.getLongValue( ( String ) targetParam, -1 ) );

            SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( resId );

            oldRes = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( filedInfoBean
                .getFieldSign()
                + "_sys_jtopcms_old" ), -1 ) );

            if( oldRes.longValue() > 0 && !oldRes.equals( resId ) )
            {
                SiteResourceBean oldResBean = resService.retrieveSingleResourceBeanByResId( oldRes );

                if( oldResBean != null )
                {
                    resService.updateSiteResourceTraceUseStatus( oldResBean.getResId(),
                        Constant.COMMON.OFF );
                }

            }

            if( resBean == null )
            {
                return "";
            }
            else
            {
                // 更新文件使用状态
                resService
                    .updateSiteResourceTraceUseStatus( resBean.getResId(), Constant.COMMON.ON );

                return "id=" + resBean.getResId() + ";t=f" + ";sid=" + resBean.getSiteId()
                    + ";reUrl=" + resBean.getResSource() + ";fn=" + resBean.getResName() + "."
                    + resBean.getFileType() + ";";
            }
        }
        else if( Constant.METADATA.EDITER == filedInfoBean.getHtmlElementId().intValue() )
        {
            /**
             * 处理所有编辑器的资源文件，更新为已使用
             */
            // 编辑器类型字段
            // String temp = StringUtil.resumeHTML( ( String ) params.get(
            // filedInfoBean
            // .getFieldSign()
            // + ServletUtility.HIDDEN_TEMP ) );
            //            
            // if(StringUtil.isStringNotNull( temp ))
            // {
            // params.put( filedInfoBean.getFieldSign(), temp );
            // }
            String text = ( String ) params.get( filedInfoBean.getFieldSign() );

            if( StringUtil.isStringNotNull( text ) )
            {
                Document doc = Jsoup.parse( text );

                Iterator eles = doc.getAllElements().iterator();

                Element ele = null;

                String id = null;

                Set currentIdSet = new HashSet();

                DataModelBean modelBean = null;

                String oldText = null;

                SiteResourceBean imgResBean = null;

                SiteGroupBean site = null;

                while ( eles.hasNext() )
                {
                    ele = ( Element ) eles.next();

                    id = ele.id();

                    if( id.startsWith( "jtopcms_content_image_" ) )
                    {
                        id = StringUtil.replaceString( id, "jtopcms_content_image_", "", false,
                            false );

                        // 水印添加
                        imgResBean = resService.retrieveSingleResourceBeanByResId( Long
                            .valueOf( StringUtil.getLongValue( id, -1 ) ) );

                        // 站点开启水印且图片信息存在才会添加

                        if( imgResBean != null )
                        {
                            String reUrl = imgResBean.getResSource();

                            Map existResTrace = resService.retrieveSingleResUseStatus( Long
                                .valueOf( StringUtil.getLongValue( id, -1 ) ) );

                            if( Constant.COMMON.OFF.equals( existResTrace.get( "isUse" ) ) )
                            {
                                imageCount++;

                                params.put( "_sys_content_image_res_count_", Integer
                                    .valueOf( imageCount ) );
                            }

                            // 已经加过水印的不需要再增加,此处代码和其他地方不一样，不需要再次读取resbean
                            if( !Constant.COMMON.ON.equals( imgResBean.getHaveMark() ) )
                            {
                                if( ServiceUtil
                                    .disposeImageMark(
                                        ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                            .getEntry( imgResBean.getSiteId() ), reUrl, imgResBean
                                            .getWidth(), imgResBean.getHeight() ) )
                                {
                                    // 成功加水印则更新
                                    resService.setImageMarkStatus( reUrl, Constant.COMMON.ON );
                                }
                            }
                        }
                    }
                    else if( id.startsWith( "jtopcms_content_media_" ) )
                    {
                        id = StringUtil.replaceString( id, "jtopcms_content_media_", "", false,
                            false );

                        Map existResTrace = resService.retrieveSingleResUseStatus( Long
                            .valueOf( StringUtil.getLongValue( id, -1 ) ) );

                        if( Constant.COMMON.OFF.equals( existResTrace.get( "isUse" ) ) )
                        {
                            videoCount++;

                            params.put( "_sys_content_video_res_count_", Integer
                                .valueOf( videoCount ) );
                        }
                    }
                    else if( id.startsWith( "jtopcms_content_file_" ) )
                    {
                        id = StringUtil.replaceString( id, "jtopcms_content_file_", "", false,
                            false );
                    }

                    resService.updateSiteResourceTraceUseStatus( Long.valueOf( StringUtil
                        .getLongValue( id, -1 ) ), Constant.COMMON.ON );

                    currentIdSet.add( id );

                }

                // 处理原内容资源
                modelBean = metaDataService.retrieveSingleDataModelBeanById( filedInfoBean
                    .getDataModelId() );

                Long cid = Long.valueOf( StringUtil.getLongValue( ( String ) params
                    .get( Constant.METADATA.CONTENT_ID_NAME ), -1 ) );

                // 存在原始记录
                if( cid.longValue() > 0 )
                {
                    oldText = contentService.retrieveTextFieldVal( modelBean, filedInfoBean, cid );

                    if( StringUtil.isStringNotNull( oldText ) )
                    {
                        disposeTextHaveSiteResId( params, oldText, currentIdSet, cid, false );
                    }

                }

            }
            else
            {
                DataModelBean modelBean = null;

                String oldText = null;

                // 处理原内容资源
                modelBean = metaDataService.retrieveSingleDataModelBeanById( filedInfoBean
                    .getDataModelId() );

                Long cid = Long.valueOf( StringUtil.getLongValue( ( String ) params
                    .get( Constant.METADATA.CONTENT_ID_NAME ), -1 ) );

                // 存在原始记录
                if( cid.longValue() > 0 )
                {
                    oldText = contentService.retrieveTextFieldVal( modelBean, filedInfoBean, cid );

                    if( StringUtil.isStringNotNull( oldText ) )
                    {
                        disposeTextHaveSiteResId( params, oldText, new HashSet(), cid, false );
                    }

                }
            }

            text = contentService.replcaeContentTextSensitive( text );

            return text;

        }
        else
        {

            targetParam = params.get( filedInfoBean.getFieldSign() );

            if( targetParam instanceof String || targetParam == null )
            {
                if( StringUtil.isStringNull( ( String ) targetParam ) )
                {
                    if( Constant.METADATA.MYSQL_INT.equals( filedInfoBean.getPerdureType() )
                        || Constant.METADATA.MYSQL_BIGINT.equals( filedInfoBean.getPerdureType() )
                        || Constant.METADATA.MYSQL_DOUBLE.equals( filedInfoBean.getPerdureType() ) )
                    {
                        return "0";
                    }
                }
            }

            // 处理多选值元素
            String text = StringUtil.getStringFromStringOrArray( targetParam, ",", utf8Mode );

            text = contentService.replcaeContentTextSensitive( text );

            int max = filedInfoBean.getMaxLength();

            if( text.length() > max )
            {
                text = text.substring( 0, max - 1 );
            }

            return text;
        }

        // return targetParam;
    }

    @SuppressWarnings( "unchecked" )
    public static void disposeTextHaveSiteResId( Map params, String text, Set currentResIdSet,
        Long contentId, boolean deleteMode )
    {
        if( text == null )
        {
            return;
        }

        Document doc = Jsoup.parse( text );

        Iterator eles = doc.getAllElements().iterator();

        Element ele = null;

        String id = null;

        while ( eles.hasNext() )
        {
            ele = ( Element ) eles.next();

            id = ele.id();

            if( id.startsWith( "jtopcms_content_image_" ) )
            {
                id = StringUtil.replaceString( id, "jtopcms_content_image_", "", false, false );

                if( params != null && !currentResIdSet.contains( id ) )
                {
                    Integer dc = ( Integer ) params.get( "_sys_content_image_res_del_count_" );

                    dc++;

                    params.put( "_sys_content_image_res_del_count_", Integer.valueOf( dc ) );
                }
            }
            else if( id.startsWith( "jtopcms_content_media_" ) )
            {
                id = StringUtil.replaceString( id, "jtopcms_content_media_", "", false, false );

                if( params != null && !currentResIdSet.contains( id ) )
                {
                    Integer dc = ( Integer ) params.get( "_sys_content_video_res_del_count_" );

                    dc++;

                    params.put( "_sys_content_video_res_del_count_", Integer.valueOf( dc ) );
                }
            }
            else if( id.startsWith( "jtopcms_content_file_" ) )
            {
                id = StringUtil.replaceString( id, "jtopcms_content_file_", "", false, false );
            }

            if( !currentResIdSet.contains( id ) )
            {
                resService.updateSiteResourceTraceUseStatus( Long.valueOf( StringUtil.getLongValue(
                    id, -1 ) ), Constant.COMMON.OFF );

            }

        }

        if( deleteMode )
        {
            // 删除下载的图片
            List resIdList = contentService.retrieveDownloadImgRes( contentId );

            Long resId = null;
            for ( int i = 0; i < resIdList.size(); i++ )
            {
                resId = ( Long ) resIdList.get( i );

                resService.updateSiteResourceTraceUseStatus( resId, Constant.COMMON.OFF );
            }

            contentService.deleteDownloadImgRes( contentId );
        }
    }

    public static String disposeSingleImageInfo( Long resId )
    {
        SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( resId );

        if( resBean == null )
        {
            return "";
        }
        else
        {
            // 更新文件使用状态
            resService.updateSiteResourceTraceUseStatus( resBean.getResId(), Constant.COMMON.ON );

            return "id=" + resBean.getResId() + ";t=i" + ";sid=" + resBean.getSiteId() + ";reUrl="
                + resBean.getResSource() + ";iw=" + resBean.getWidth() + ";ih="
                + resBean.getHeight() + ";";
        }
    }

    public static void disposeOldImageInfo( Long resId, String fieldSign, Map params )
    {
        Long oldRes = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( fieldSign
            + "_sys_jtopcms_old" ), -1 ) );

        if( oldRes.longValue() > 0 && !oldRes.equals( resId ) )
        {
            // 图片被替换或被删除
            SiteResourceBean oldResBean = resService.retrieveSingleResourceBeanByResId( oldRes );

            if( oldResBean != null )
            {
                resService.updateSiteResourceTraceUseStatus( oldResBean.getResId(),
                    Constant.COMMON.OFF );

                Integer dc = ( Integer ) params.get( "_sys_content_image_res_del_count_" );

                if( dc != null )
                {
                    dc++;

                    params.put( "_sys_content_image_res_del_count_", Integer.valueOf( dc ) );
                }
            }

        }
    }

    public static String disposeSingleFileInfo( Long resId )
    {
        SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( resId );

        if( resBean == null )
        {
            return "";
        }
        else
        {
            return "id=" + resBean.getResId() + ";t=f" + ";sid=" + resBean.getSiteId() + ";reUrl="
                + resBean.getResSource() + ";fn=" + resBean.getResName() + "."
                + resBean.getFileType() + ";";
        }
    }

    public static String disposeSingleVideoInfo( Map params, String fieldSign )
    {
        Object targetParam = null;
        Long oldRes = null;
        String oldCover = null;

        targetParam = params.get( fieldSign );

        Long resId = Long.valueOf( StringUtil.getLongValue( ( String ) targetParam, -1 ) );

        oldRes = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( fieldSign
            + "_sys_jtopcms_old" ), -1 ) );

        oldCover = ( String ) params.get( fieldSign + "_sys_jtopcms_old_cover" );

        // 需要删除
        SiteResourceBean oldResBean = resService.retrieveSingleResourceBeanByResId( oldRes );

        if( oldRes.longValue() > 0 && !oldRes.equals( resId ) )
        {
            if( oldResBean != null )// 此时已经产生了新的res对象
            {
                resService.updateSiteResourceTraceUseStatus( oldResBean.getResId(),
                    Constant.COMMON.OFF );

                Integer dc = ( Integer ) params.get( "_sys_content_video_res_del_count_" );

                dc++;

                params.put( "_sys_content_video_res_del_count_", Integer.valueOf( dc ) );

                oldCover = StringUtil.isStringNull( oldResBean.getCover() ) ? "" : oldResBean
                    .getCover();

                if( StringUtil.isStringNotNull( oldResBean.getCover() ) )
                {
                    SiteResourceBean coverResBean = resService
                        .retrieveSingleResourceBeanBySource( oldCover );

                    if( coverResBean != null )
                    {
                        resService.updateSiteResourceTraceUseStatus( coverResBean.getResId(),
                            Constant.COMMON.OFF );
                    }
                }
            }
        }

        SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( resId );

        if( resBean == null )
        {
            return "";
        }
        else
        {
            // 更新视频文件使用状态
            resService.updateSiteResourceTraceUseStatus( resBean.getResId(), Constant.COMMON.ON );

            if( oldRes.equals( resId ) ) // 文件没有变化的更新操作,需要进一步检查cover是否变化
            {
                if( oldResBean != null )// 此时的old res对象并没有改变,但cover有可能改变
                {
                    if( oldResBean.getCover() != null && !oldResBean.getCover().equals( oldCover ) )// 如果cover文件发生变化
                    {
                        if( StringUtil.isStringNotNull( oldCover ) )// 如果旧的cover存在
                        {
                            SiteResourceBean coverResBean = resService
                                .retrieveSingleResourceBeanBySource( oldCover );

                            if( coverResBean != null )
                            {
                                resService.updateSiteResourceTraceUseStatus( coverResBean
                                    .getResId(), Constant.COMMON.OFF );
                            }
                        }
                    }
                }
            }

            String[] hw = StringUtil.split( resBean.getResolution(), "x" );

            String vw = "";
            String vh = "";

            if( hw.length == 2 )
            {
                vw = hw[0];
                vh = hw[1];
            }

            String cover = StringUtil.isStringNull( resBean.getCover() ) ? "" : resBean.getCover();

            if( StringUtil.isStringNotNull( resBean.getCover() ) )
            {
                SiteResourceBean coverResBean = resService
                    .retrieveSingleResourceBeanBySource( cover );

                if( coverResBean != null )
                {
                    resService.updateSiteResourceTraceUseStatus( coverResBean.getResId(),
                        Constant.COMMON.ON );
                }
            }

            return "id=" + resBean.getResId() + ";t=m" + ";sid=" + resBean.getSiteId() + ";reUrl="
                + resBean.getResSource() + ";vc=" + cover + ";vt=" + resBean.getDuration() + ";vw="
                + vw + ";vh=" + vh + ";";

        }

    }

    public static Object[] disposePhotoGroupInfoParam( String fieldSign, Map params,
        Integer needMark )
    {
        List photoGroupInfoList = new ArrayList();

        int needAddImageCount = StringUtil.getIntValue( ( String ) params.get( fieldSign
            + "CmsSysImageArrayLength" ), -1 ) + 1;

        String coverResPath = ( String ) params.get( fieldSign + "CmsSysImageCover" );

        PhotoGroupInfo pgi = null;

        String photoName = null;
        String relatePath = null;
        String resId = null;

        int order = 1;

        int imageCount = 0;

        for ( int i = 0; i < needAddImageCount; i++ )
        {
            pgi = new PhotoGroupInfo();

            pgi.setNeedMark( needMark );

            resId = ( String ) params.get( fieldSign + "-resId-" + i );
            photoName = ( String ) params.get( fieldSign + "-name-show-" + i );
            relatePath = ( String ) params.get( fieldSign + "-relatePath-" + i );

            if( StringUtil.isStringNull( relatePath ) || StringUtil.isStringNull( resId ) )
            {
                continue;
            }

            pgi.setGroupSign( fieldSign );

            pgi.setPhotoName( photoName );

            Map existResTrace = resService.retrieveSingleResUseStatus( Long.valueOf( resId ) );

            if( Constant.COMMON.OFF.equals( existResTrace.get( "isUse" ) ) )
            {
                imageCount++;
            }

            pgi.setUrl( disposeSingleImageInfo( Long.valueOf( resId ) ) );

            if( relatePath.equals( coverResPath ) )
            {
                pgi.setIsCover( Constant.COMMON.ON );
            }

            pgi.setPhotoDesc( ( String ) params.get( fieldSign + "-photoDesc-" + i ) );

            pgi.setOrderFlag( Integer.valueOf( order++ ) );
            pgi.setPhotoAddTime( DateAndTimeUtil.getTodayTimestampDayAndTime() );

            photoGroupInfoList.add( pgi );
        }

        return new Object[] { photoGroupInfoList, imageCount };
    }

    public static String copyImageRes( String resInfo, SiteGroupBean targetSite, String dayStr,
        Long targetClassId )
    {
        Long resId = getResId( resInfo );

        if( resId.longValue() < 1 )
        {
            return null;
        }

        SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( resId );

        if( resBean == null )
        {
            return null;
        }

        SiteGroupBean currSiteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( resBean.getSiteId() );

        String sitePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String uploadFilePath = sitePath + currSiteBean.getSiteRoot() + File.separator
            + currSiteBean.getImageRoot() + File.separator;

        String uploadNewFilePathWithDay = sitePath + targetSite.getSiteRoot() + File.separator
            + targetSite.getImageRoot() + File.separator + dayStr + File.separator;

        File testDir = new File( uploadNewFilePathWithDay );

        if( !testDir.exists() )
        {
            testDir.mkdirs();
        }

        String fileSource = StringUtil.replaceString( resBean.getResSource(), "/", File.separator,
            false, false );

        String oldPath = uploadFilePath + fileSource;

        String oldResizePath = uploadFilePath
            + StringUtil.replaceString( fileSource, File.separator, File.separator + "imgResize",
                false, false );

        String uuid = DateAndTimeUtil.clusterTimeMillis() + StringUtil.getUUIDString();

        String newPath = uploadNewFilePathWithDay + uuid + "." + resBean.getFileType();

        String newResizePath = uploadNewFilePathWithDay + "imgResize" + uuid + "."
            + resBean.getFileType();

        FileUtil.copyFile( oldPath, newPath );

        FileUtil.copyFile( oldResizePath, newResizePath );

        // 记录res

        SiteResource newResBean = new SiteResource();

        newResBean.setClassId( targetClassId );

        newResBean.setCover( resBean.getCover() );

        newResBean.setDuration( resBean.getDuration() );

        newResBean.setFileType( resBean.getFileType() );

        newResBean.setHaveMark( resBean.getHaveMark() );

        newResBean.setHeight( resBean.getHeight() );

        newResBean.setModifyTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

        newResBean.setResName( resBean.getResName() );

        newResBean.setResolution( resBean.getResolution() );

        newResBean.setResType( resBean.getResType() );

        newResBean.setSiteId( targetSite.getSiteId() );

        newResBean.setResSize( resBean.getResSize() );

        newResBean.setResSource( dayStr + "/" + uuid + "." + resBean.getFileType() );

        newResBean.setWidth( resBean.getWidth() );

        UpdateState us = resService.addSiteResourceAndUploadTraceSuccessStatus( newResBean );

        // 进行数据分发
        List resList = new ArrayList();
        resList.add( newResBean );
        siteService.transferUpdateDataToServer( resList );

        if( us.haveKey() )
        {
            return "id=" + us.getKey() + ";t=i" + ";sid=" + newResBean.getSiteId() + ";reUrl="
                + newResBean.getResSource() + ";iw=" + newResBean.getWidth() + ";ih="
                + newResBean.getHeight() + ";";
        }
        else
        {
            return null;
        }

    }

    public static String copyFileRes( String resInfo, SiteGroupBean targetSite, String dayStr,
        Long targetClassId )
    {
        Long resId = getResId( resInfo );

        if( resId.longValue() < 1 )
        {
            return null;
        }

        SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( resId );

        if( resBean == null )
        {
            return null;
        }

        SiteGroupBean currSiteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( resBean.getSiteId() );

        String sitePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String uploadFilePath = sitePath + currSiteBean.getSiteRoot() + File.separator
            + currSiteBean.getFileRoot() + File.separator;

        String uploadNewFilePathWithDay = sitePath + targetSite.getSiteRoot() + File.separator
            + targetSite.getFileRoot() + File.separator + dayStr + File.separator;

        File testDir = new File( uploadNewFilePathWithDay );

        if( !testDir.exists() )
        {
            testDir.mkdirs();
        }

        String fileSource = StringUtil.replaceString( resBean.getResSource(), "/", File.separator,
            false, false );

        String oldPath = uploadFilePath + fileSource;

        String uuid = DateAndTimeUtil.clusterTimeMillis() + StringUtil.getUUIDString();

        String newPath = uploadNewFilePathWithDay + uuid + "." + resBean.getFileType();

        FileUtil.copyFile( oldPath, newPath );

        // 记录res

        SiteResource newResBean = new SiteResource();

        newResBean.setClassId( targetClassId );

        newResBean.setCover( resBean.getCover() );

        newResBean.setDuration( resBean.getDuration() );

        newResBean.setFileType( resBean.getFileType() );

        newResBean.setHaveMark( resBean.getHaveMark() );

        newResBean.setHeight( resBean.getHeight() );

        newResBean.setModifyTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

        newResBean.setResName( resBean.getResName() );

        newResBean.setResolution( resBean.getResolution() );

        newResBean.setResType( resBean.getResType() );

        newResBean.setSiteId( targetSite.getSiteId() );

        newResBean.setResSize( resBean.getResSize() );

        newResBean.setResSource( dayStr + "/" + uuid + "." + resBean.getFileType() );

        newResBean.setWidth( resBean.getWidth() );

        UpdateState us = resService.addSiteResourceAndUploadTraceSuccessStatus( newResBean );

        // 进行数据分发
        List resList = new ArrayList();
        resList.add( newResBean );
        siteService.transferUpdateDataToServer( resList );

        if( us.haveKey() )
        {
            return "id=" + us.getKey() + ";t=f" + ";sid=" + newResBean.getSiteId() + ";reUrl="
                + newResBean.getResSource() + ";fn=" + newResBean.getResName() + "."
                + newResBean.getFileType() + ";";
        }
        else
        {
            return null;
        }

    }

    public static String copyMediaRes( String resInfo, SiteGroupBean targetSite, String dayStr,
        Long targetClassId )
    {
        Long resId = getResId( resInfo );

        if( resId.longValue() < 1 )
        {
            return null;
        }

        SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( resId );

        if( resBean == null )
        {
            return null;
        }

        SiteGroupBean currSiteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( resBean.getSiteId() );

        String sitePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String uploadFilePath = sitePath + currSiteBean.getSiteRoot() + File.separator
            + currSiteBean.getMediaRoot() + File.separator;

        String uploadNewFilePathWithDay = sitePath + targetSite.getSiteRoot() + File.separator
            + targetSite.getMediaRoot() + File.separator + dayStr + File.separator;

        File testDir = new File( uploadNewFilePathWithDay );

        if( !testDir.exists() )
        {
            testDir.mkdirs();
        }

        String fileSource = StringUtil.replaceString( resBean.getResSource(), "/", File.separator,
            false, false );

        String oldPath = uploadFilePath + fileSource;

        String uuid = DateAndTimeUtil.clusterTimeMillis() + StringUtil.getUUIDString();

        String newPath = uploadNewFilePathWithDay + uuid + "." + resBean.getFileType();

        FileUtil.copyFile( oldPath, newPath );

        // cover
        String cover = "";

        Integer coverH = Integer.valueOf( 0 );

        Integer coverW = Integer.valueOf( 0 );

        if( StringUtil.isStringNotNull( resBean.getCover() ) )
        {
            SiteResourceBean coverBean = resService.retrieveSingleResourceBeanBySource( resBean
                .getCover() );

            if( coverBean != null )
            {
                String newCoverInfo = copyImageRes( "id=" + coverBean.getResId() + ";t=i" + ";sid="
                    + coverBean.getSiteId() + ";reUrl=" + coverBean.getResSource() + ";iw="
                    + coverBean.getWidth() + ";ih=" + coverBean.getHeight() + ";", targetSite,
                    dayStr, targetClassId );

                coverH = coverBean.getHeight();

                coverW = coverBean.getWidth();

                cover = StringUtil.subString( newCoverInfo, newCoverInfo.indexOf( "reUrl=" ) + 6,
                    newCoverInfo.indexOf( ";", newCoverInfo.indexOf( "reUrl=" ) + 6 ) );
            }
        }

        // 记录res

        SiteResource newResBean = new SiteResource();

        newResBean.setClassId( targetClassId );

        newResBean.setCover( cover );

        newResBean.setDuration( resBean.getDuration() );

        newResBean.setFileType( resBean.getFileType() );

        newResBean.setHaveMark( resBean.getHaveMark() );

        newResBean.setHeight( resBean.getHeight() );

        newResBean.setModifyTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

        newResBean.setResName( resBean.getResName() );

        newResBean.setResolution( resBean.getResolution() );

        newResBean.setResType( resBean.getResType() );

        newResBean.setSiteId( targetSite.getSiteId() );

        newResBean.setResSize( resBean.getResSize() );

        newResBean.setResSource( dayStr + "/" + uuid + "." + resBean.getFileType() );

        newResBean.setWidth( resBean.getWidth() );

        UpdateState us = resService.addSiteResourceAndUploadTraceSuccessStatus( newResBean );

        // 进行数据分发
        List resList = new ArrayList();
        resList.add( newResBean );
        siteService.transferUpdateDataToServer( resList );

        if( us.haveKey() )
        {
            return "id=" + us.getKey() + ";t=m" + ";sid=" + newResBean.getSiteId() + ";reUrl="
                + newResBean.getResSource() + ";vc=" + cover + ";vt=" + newResBean.getDuration()
                + ";vw=" + coverW + ";vh=" + coverH + ";";
        }
        else
        {
            return null;
        }

    }

    public static void disposeDeleteResFromDefContent( List modeFieldList, Map mainAndDefInfo )
    {

        if( modeFieldList == null )
        {
            return;
        }

        // 删除资源文件以及信息
        // 以下为资源类型字段
        ModelFiledInfoBean bean = null;

        for ( int j = 0; j < modeFieldList.size(); j++ )
        {
            bean = ( ModelFiledInfoBean ) modeFieldList.get( j );

            if( Constant.METADATA.UPLOAD_IMG == bean.getHtmlElementId().intValue() )
            {
                SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( Long
                    .valueOf( StringUtil.getLongValue( ( String ) mainAndDefInfo.get( bean
                        .getFieldSign()
                        + "ResId" ), -1 ) ) );

                if( resBean != null )
                {
                    // 更新文件使用状态
                    resService.updateSiteResourceTraceUseStatus( resBean.getResId(),
                        Constant.COMMON.OFF );
                }
            }
            else if( Constant.METADATA.UPLOAD_MEDIA == bean.getHtmlElementId().intValue() )
            {
                SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( Long
                    .valueOf( StringUtil.getLongValue( ( String ) mainAndDefInfo.get( bean
                        .getFieldSign()
                        + "ResId" ), -1 ) ) );

                if( resBean != null )
                {
                    // 更新文件使用状态
                    resService.updateSiteResourceTraceUseStatus( resBean.getResId(),
                        Constant.COMMON.OFF );

                    String cover = StringUtil.isStringNull( resBean.getCover() ) ? "" : resBean
                        .getCover();

                    if( StringUtil.isStringNotNull( resBean.getCover() ) )
                    {
                        SiteResourceBean coverResBean = resService
                            .retrieveSingleResourceBeanBySource( cover );

                        if( coverResBean != null )
                        {
                            resService.updateSiteResourceTraceUseStatus( coverResBean.getResId(),
                                Constant.COMMON.OFF );
                        }
                    }
                }
            }
            else if( Constant.METADATA.UPLOAD_FILE == bean.getHtmlElementId().intValue() )
            {
                SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( Long
                    .valueOf( StringUtil.getLongValue( ( String ) mainAndDefInfo.get( bean
                        .getFieldSign() ), -1 ) ) );

                if( resBean != null )
                {
                    // 更新文件使用状态
                    resService.updateSiteResourceTraceUseStatus( resBean.getResId(),
                        Constant.COMMON.OFF );
                }
            }
            /*
             * else if( Constant.METADATA.UPLOAD_IMG_GROUP == bean
             * .getHtmlElementId().intValue() ) { List imageGroupList =
             * contentDao .retrieveGroupPhotoInfoByContentId( contentId,
             * Constant.METADATA.MODEL_TYPE_CONTENT, site, true );
             * 
             * Map imageInfo = null;
             * 
             * for ( int k = 0; k < imageGroupList.size(); k++ ) { imageInfo = (
             * Map ) imageGroupList.get( k );
             * 
             * SiteResourceBean resBean = resService
             * .retrieveSingleResourceBeanByResId( Long .valueOf( ( String )
             * imageInfo.get( "resId" ) ) );
             * 
             * if( resBean != null ) { // 更新文件使用状态
             * resService.updateSiteResourceTraceUseStatus( resBean .getResId(),
             * Constant.COMMON.OFF ); } } }
             */
            else if( Constant.METADATA.EDITER == bean.getHtmlElementId().intValue() )
            {
                ServiceUtil.disposeTextHaveSiteResId( null, ( String ) mainAndDefInfo.get( bean
                    .getFieldSign() ), new HashSet(), Long.valueOf( -1 ), true );
            }
        }
    }

    /**
     * 单独删除上传资源(trace)
     * 
     * @param resId
     */
    public static void deleteSiteResTraceMode( Long resId )
    {
        SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( resId );

        if( resBean != null )
        {
            // 更新文件使用状态
            resService.updateSiteResourceTraceUseStatus( resBean.getResId(), Constant.COMMON.OFF );

            // 视频模式
            String cover = StringUtil.isStringNull( resBean.getCover() ) ? "" : resBean.getCover();

            if( StringUtil.isStringNotNull( resBean.getCover() ) )
            {
                SiteResourceBean coverResBean = resService
                    .retrieveSingleResourceBeanBySource( cover );

                if( coverResBean != null )
                {
                    resService.updateSiteResourceTraceUseStatus( coverResBean.getResId(),
                        Constant.COMMON.OFF );
                }
            }
        }

    }

    /**
     * 返回GET请求的数据，UTF-8模式
     * 
     * @param url
     * @return
     */
    public static String doGETInfo( String url )
    {
        return readStream( doGETMethodRequest( url ), "UTF-8" );
    }

    public static InputStream doGETMethodRequest( String url )
    {
        InputStream stream = null;

        if( StringUtil.isStringNotNull( url ) )
        {
            try
            {
                URL targetUrl = new URL( url );

                URLConnection URLconnection = targetUrl.openConnection();
                HttpURLConnection httpConnection = ( HttpURLConnection ) URLconnection;

                httpConnection.setRequestProperty( "contentType", "UTF-8" );
                httpConnection.setConnectTimeout( 5 * 1000 );
                httpConnection.setRequestMethod( "GET" );

                // httpConnection.connect();
                stream = httpConnection.getInputStream();

            }
            catch ( Exception e )
            {
                log.error( "GET请求连接错误:" + url + " [error]: " + e );

            }
        }

        return stream;
    }

    public static boolean GETMethodRequest( String url )
    {
        boolean error = false;

        if( StringUtil.isStringNotNull( url ) )
        {
            try
            {
                URL targetUrl = new URL( url );

                URLConnection URLconnection = targetUrl.openConnection();
                HttpURLConnection httpConnection = ( HttpURLConnection ) URLconnection;

                httpConnection.setRequestProperty( "contentType", "UTF-8" );
                httpConnection.setConnectTimeout( 5 * 1000 );
                httpConnection.setRequestMethod( "GET" );

                // httpConnection.connect();
                httpConnection.getInputStream();

            }
            catch ( Exception e )
            {
                error = true;
                log.error( "GET请求连接错误:" + url + " [error]: " + e );

            }
        }

        return error;
    }

    public static InputStream doPOSTMethodRequest( String url )
    {
        InputStream stream = null;

        if( StringUtil.isStringNotNull( url ) )
        {
            try
            {
                URL targetUrl = new URL( url );

                URLConnection URLconnection = targetUrl.openConnection();
                HttpURLConnection httpConnection = ( HttpURLConnection ) URLconnection;

                httpConnection.setRequestProperty( "contentType", "UTF-8" );
                httpConnection.setConnectTimeout( 5 * 1000 );
                httpConnection.setRequestMethod( "POST" );

                // httpConnection.connect();
                stream = httpConnection.getInputStream();

            }
            catch ( Exception e )
            {
                log.error( "POST请求连接超时:" + url );

            }
        }

        return stream;
    }

    public static boolean POSTMethodRequest( String url )
    {
        boolean error = false;

        if( StringUtil.isStringNotNull( url ) )
        {
            try
            {
                URL targetUrl = new URL( url );

                URLConnection URLconnection = targetUrl.openConnection();
                HttpURLConnection httpConnection = ( HttpURLConnection ) URLconnection;

                httpConnection.setRequestProperty( "contentType", "UTF-8" );
                httpConnection.setConnectTimeout( 5 * 1000 );
                httpConnection.setRequestMethod( "POST" );

                // httpConnection.connect();
                httpConnection.getInputStream();

            }
            catch ( Exception e )
            {
                error = true;
                log.error( "POST请求连接错误:" + url + " [error]: " + e );

            }
        }

        return error;
    }

    public static String doPOSTMethodRequestForJson( String strURL, String json )
    {

        try
        {
            URL url = new URL( strURL );
            HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
            connection.setDoOutput( true );
            connection.setDoInput( true );
            connection.setUseCaches( false );
            connection.setInstanceFollowRedirects( true );
            connection.setRequestMethod( "POST" );
            connection.setRequestProperty( "Accept", "application/json" );
            connection.setRequestProperty( "Content-Type", "application/json" );
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter( connection.getOutputStream(), "UTF-8" ); // utf-8编码
            out.append( json );
            out.flush();
            out.close();
            // 读取响应
            InputStream is = connection.getInputStream();
            return readStream( is, "UTF-8" );

        }
        catch ( IOException e )
        {
            log.error( "POST请求连接超时:" + strURL );
        }

        return "";
    }

    public static String doPOSTMethodRequest( String strURL, String str )
    {

        try
        {
            URL url = new URL( strURL );
            HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
            connection.setDoOutput( true );
            connection.setDoInput( true );
            connection.setUseCaches( false );
            connection.setInstanceFollowRedirects( true );
            connection.setRequestMethod( "POST" ); // 设置请求方式

            connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" ); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter( connection.getOutputStream(), "UTF-8" ); // utf-8编码
            out.write( str );
            out.flush();
            out.close();

            // 读取响应
            InputStream is = connection.getInputStream();
            return readStream( is, "UTF-8" );

        }
        catch ( IOException e )
        {
            log.error( "POST请求连接超时:" + strURL );
        }

        return "";
    }

    /**
     * 设置信任所有的http证书（正常情况下访问https打头的网站会出现证书不信任相关错误，所以必须在访问前调用此方法）
     * 
     * @throws Exception
     */
    public static void trustAllHttpsCertificates()
    {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        trustAllCerts[0] = new X509TrustManager()
        {

            public X509Certificate[] getAcceptedIssuers()
            {
                return null;
            }

            public void checkServerTrusted( X509Certificate[] arg0, String arg1 )
                throws CertificateException
            {
            }

            public void checkClientTrusted( X509Certificate[] arg0, String arg1 )
                throws CertificateException
            {
            }
        };
        javax.net.ssl.SSLContext sc = null;
        try
        {
            sc = javax.net.ssl.SSLContext.getInstance( "SSL" );

            sc.init( null, trustAllCerts, null );
        }
        catch ( Exception e )
        {

            e.printStackTrace();
        }

        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory( sc.getSocketFactory() );
    }

    public static String readStream( InputStream stream, String charSet )
    {
        if( stream == null )
        {
            return "";
        }

        String line = null;

        StringBuffer buf = new StringBuffer();

        BufferedReader bufReader = null;

        try
        {
            bufReader = new BufferedReader( new InputStreamReader( stream, charSet ) );

            while ( ( line = bufReader.readLine() ) != null )
            {
                buf.append( line );
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if( stream != null )
            {
                try
                {
                    stream.close();
                }
                catch ( IOException e )
                {

                }
            }

            if( bufReader != null )
            {
                try
                {
                    bufReader.close();
                }
                catch ( IOException e )
                {

                }
            }
        }

        return buf.toString();
    }

    public static String getValFormUrlParam( String target, String key )
    {
        if( StringUtil.isStringNull( target ) )
        {
            return "";
        }

        String[] spStr = StringUtil.split( target, "&" );

        String str = null;

        for ( int i = 0; i < spStr.length; i++ )
        {
            str = spStr[i];

            if( str.startsWith( key + "=" ) )
            {
                return str.replaceAll( key + "=", "" );
            }

        }

        return "";
    }

    public static String encodeValFormUrlParam( String target )
    {
        if( StringUtil.isStringNull( target ) )
        {
            return "";
        }

        String[] spStr = StringUtil.split( target, "&" );

        String str = null;

        StringBuilder buf = new StringBuilder();

        String[] tmp = null;

        for ( int i = 0; i < spStr.length; i++ )
        {
            str = spStr[i];

            tmp = StringUtil.split( str, "=" );

            if( tmp.length == 2 )
            {
                buf.append( tmp[0]
                    + "="
                    + SystemSafeCharUtil.encode( SystemSafeCharUtil.encode( SystemSafeCharUtil
                        .encodeDangerChar( tmp[1] ) ) ) );

                if( i + 1 != spStr.length )
                {
                    buf.append( "&" );
                }
            }

        }

        return buf.toString();
    }

    /**
     * 将encode过的参数进行解码,此方法不主动过滤特殊符号
     * 
     * @param params
     */
    public static void decodeMapParam( Map params )
    {
        Iterator iter = params.entrySet().iterator();

        Entry entry = null;

        String value = null;
        try
        {
            while ( iter.hasNext() )
            {
                entry = ( Entry ) iter.next();

                if( entry.getValue() instanceof String[] )
                {
                    String[] vals = ( String[] ) entry.getValue();

                    String[] newVals = new String[vals.length];

                    for ( int i = 0; i < vals.length; i++ )
                    {
                        newVals[i] = URLDecoder.decode( vals[i], "UTF-8" );
                    }

                    entry.setValue( newVals );
                }
                else
                {
                    value = ( String ) entry.getValue();
                    entry.setValue( URLDecoder.decode( value, "UTF-8" ) );
                }

            }
        }
        catch ( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }

    }

    /**
     * 将Encode过的param Map所有参数进行解码,强制过滤特殊符号
     * 
     * @param target
     * @param enc
     * @return
     */
    public static void decodeAndDisposeMapParam( Map params )
    {
        Iterator iter = params.entrySet().iterator();

        Entry entry = null;

        String value = null;
        try
        {
            while ( iter.hasNext() )
            {
                entry = ( Entry ) iter.next();

                if( entry.getValue() instanceof String[] )
                {
                    String[] vals = ( String[] ) entry.getValue();

                    String[] newVals = new String[vals.length];

                    for ( int i = 0; i < vals.length; i++ )
                    {
                        newVals[i] = SystemSafeCharUtil.filterHTMLNotApos( URLDecoder.decode(
                            vals[i], "UTF-8" ) );
                    }

                    entry.setValue( newVals );
                }
                else
                {
                    value = SystemSafeCharUtil.filterHTMLNotApos( ( String ) entry.getValue() );

                    entry.setValue( URLDecoder.decode( value, "UTF-8" ) );
                }

            }
        }
        catch ( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }

    }

    public static int checkCode( HttpServletRequest req, String checkCodeTest )
    {

        HttpSession ssn = req.getSession();

        String checkCode = ( String ) ssn
            .getAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );

        if( StringUtil.isStringNull( checkCode ) || !checkCode.equalsIgnoreCase( checkCodeTest ) )
        {
            // 验证码错误
            return -1;
        }

        return 1;
    }

    public static int addSiteEmailSendInfo( Long siteId, String sendTo, String subject,
        String mailContent, Timestamp createDT )
    {

        if( mailContent != null )
        {
            mailContent = SystemSafeCharUtil.resumeHTML( mailContent );
        }

        UpdateState us = siteService.addSiteEmailSendInfo( siteId, sendTo, subject, mailContent,
            createDT );

        if( us.getRow() > 0 )
        {
            return 1;
        }

        return 0;
    }

    public static String getImageReUrl( String res )
    {
        if( res == null || res.indexOf( "id=" ) == -1 )
        {
            return "";
        }

        return StringUtil.subString( res, res.indexOf( "reUrl=" ) + 6, res.indexOf( ";", res
            .indexOf( "reUrl=" ) + 6 ) );
    }

    public static String getImageW( String res )
    {
        if( res == null || res.indexOf( "id=" ) == -1 )
        {
            return "0";
        }

        return StringUtil.subString( res, res.indexOf( "iw=" ) + 3, res.indexOf( ";", res
            .indexOf( "iw=" ) + 3 ) );
    }

    public static String getImageH( String res )
    {
        if( res == null || res.indexOf( "id=" ) == -1 )
        {
            return "0";
        }

        return StringUtil.subString( res, res.indexOf( "ih=" ) + 3, res.indexOf( ";", res
            .indexOf( "ih=" ) + 3 ) );
    }

    public static Long getResId( String res )
    {
        if( res == null || res.indexOf( "id=" ) == -1 )
        {
            return Long.valueOf( "-1" );
        }

        return Long.valueOf( StringUtil.getLongValue( StringUtil.subString( res, res
            .indexOf( "id=" ) + 3, res.indexOf( ";", res.indexOf( "id=" ) + 3 ) ), -1 ) );
    }

    /**
     * 增加图片水印
     * 
     * @param siteBean
     * @param reUrl
     * @param width
     * @param height
     */
    public static boolean disposeImageMark( SiteGroupBean siteBean, String reUrl, Integer width,
        Integer height )
    {
        boolean success = false;

        if( siteBean == null || StringUtil.isStringNull( reUrl ) || width == null || height == null )
        {
            return false;
        }

        // 站点关闭水印功能则不操作
        if( !Constant.COMMON.ON.equals( siteBean.getUseImageMark() ) )
        {
            return false;
        }

        String realPath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        // 物理和网络路径
        String uploadFilePath = realPath + File.separator + siteBean.getSiteRoot() + File.separator
            + siteBean.getImageRoot();

        // 水印处理
        int markW = StringUtil.getIntValue( siteBean.getImageMarkImageW(), 9999 );

        int markH = StringUtil.getIntValue( siteBean.getImageMarkImageH(), 9999 );

        String markImageUrl = realPath
            + File.separator
            + siteBean.getSiteRoot()
            + File.separator
            + siteBean.getImageRoot()
            + File.separator
            + StringUtil.replaceString( siteBean.getImageMarkReUrl(), "/", File.separator, false,
                false );

        // 小于水印的图片不允许被加水印
        String rePath = StringUtil.replaceString( reUrl, "/", File.separator, false, false );

        if( markW < width.intValue() && markH < height.intValue() )
        {
            success = ImageUtil.addImageMark( uploadFilePath + File.separator + rePath,
                uploadFilePath + File.separator + rePath, markImageUrl, siteBean.getImageMarkPos(),
                markW, markH, siteBean.getOffSetX().intValue(), siteBean.getOffSetY().intValue(),
                siteBean.getImageMarkDis() );

            List<SiteResource> resList = new ArrayList<SiteResource>();

            SiteResource res = new SiteResource();

            SiteResourceBean resBean = resService.retrieveSingleResourceBeanBySource( reUrl );

            res.setResSource( resBean.getResSource() );
            res.setSiteId( resBean.getSiteId() );
            res.setResType( resBean.getResType() );

            resList.add( res );

            // 进行数据分发
            siteService.transferUpdateDataToServer( resList );

        }

        return success;
    }

    public static void deleteClassAllContentInfo( SiteGroupBean site, ContentClassBean classBean )
    {

        List allSiteModelList = metaDataService.retrieveAllDataModelBeanListByModelTypeAndSiteId(
            Constant.METADATA.MODEL_TYPE_CONTENT, site.getSiteId() );

        DataModelBean modelBean = null;

        // 每一种可能的内容模型
        for ( int mb = 0; mb < allSiteModelList.size(); mb++ )
        {
            modelBean = ( DataModelBean ) allSiteModelList.get( mb );

            contentService.deleteAllSystemAndUserDefineContentToTrash( site, modelBean
                .getDataModelId(), classBean.getClassId(), new ArrayList() );

            contentService.deleteAllSystemAndUserDefineContent( modelBean.getDataModelId(),
                classBean.getClassId() );
        }
    }

    public static boolean checkWhiteIP( String managerIP, String ip )
    {
        List mipList = StringUtil.changeStringToList( managerIP, "," );

        String ipInfo = null;

        for ( int i = 0; i < mipList.size(); i++ )
        {
            ipInfo = ( String ) mipList.get( i );

            if( ipInfo.endsWith( ".*" ) )
            {
                if( ip.startsWith( StringUtil.replaceString( ipInfo, ".*", "", false, false ) ) )
                {
                    return true;
                }
            }
            else
            {
                if( ip.equals( ipInfo ) )
                {
                    return true;
                }
            }
        }

        return false;
    }

    public static String cleanEditorHtmlByWhiteRule( String html )
    {
        if( html == null )
        {
            return "";
        }

        return Jsoup.clean( html, user_content_filter );
    }

    public static String cleanBasicHtmlByWhiteRule( String html )
    {
        return Jsoup.clean( html, Whitelist.basic() );
    }

}
