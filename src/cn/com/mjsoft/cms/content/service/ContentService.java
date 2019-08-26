package cn.com.mjsoft.cms.content.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.cms.channel.dao.ChannelDao;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.dao.ValiDao;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.bean.ContentCommendPushInfoBean;
import cn.com.mjsoft.cms.content.bean.ContentMainInfoBean;
import cn.com.mjsoft.cms.content.dao.ContentDao;
import cn.com.mjsoft.cms.content.dao.vo.ContentAssistantPageInfo;
import cn.com.mjsoft.cms.content.dao.vo.ContentCommendPushInfo;
import cn.com.mjsoft.cms.content.dao.vo.ContentMainInfo;
import cn.com.mjsoft.cms.content.dao.vo.PhotoGroupInfo;
import cn.com.mjsoft.cms.member.bean.MemberBean;
import cn.com.mjsoft.cms.member.dao.MemberDao;
import cn.com.mjsoft.cms.message.service.MessageService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.dao.MetaDataDao;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.pick.dao.PickDao;
import cn.com.mjsoft.cms.resources.bean.SiteResourceBean;
import cn.com.mjsoft.cms.resources.dao.ResourcesDao;
import cn.com.mjsoft.cms.resources.dao.vo.SiteResource;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.search.dao.vo.SearchIndexContentState;
import cn.com.mjsoft.cms.search.service.SearchService;
import cn.com.mjsoft.cms.security.bean.SystemUserBean;
import cn.com.mjsoft.cms.security.dao.SecurityDao;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.cms.stat.bean.StatContentVisitOrCommentDWMCount;
import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.cms.workflow.bean.WorkflowOperationBean;
import cn.com.mjsoft.cms.workflow.bean.WorkflowStepInfoBean;
import cn.com.mjsoft.cms.workflow.dao.WorkFlowDao;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.config.SystemRuntimeConfig;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.authorization.AuthorizationHandler;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.HtmlUtil;
import cn.com.mjsoft.framework.util.ImageUtil;
import cn.com.mjsoft.framework.util.MathUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.util.FileUtil.FileTypeEM;

public class ContentService
{
    private static Logger log = Logger.getLogger( ContentService.class );

    public static final int KEY_SIZE = 5;// 最大5个关键词

    private static final int RELATE_SIZE = 5;// 最大25个关联内容

    private static final int SW_MAX = 1;// 最大匹配一个

    private static final Integer DELETE_QUERY_COUNT = Integer.valueOf( 1000 );// 每次删除1000个

    public static Cache singleContentCache = new ClusterCacheAdapter( 20000,
        "contentService.singleContentCache" );

    public static Cache listContentCache = new ClusterCacheAdapter( 20000,
        "contentService.listContentCache" );

    public static Cache listTagContentCountCache = new ClusterCacheAdapter( 4000,
        "contentService.listTagContentCountCache" );

    public static Cache listTagContentCache = new ClusterCacheAdapter( 6000,
        "contentService.listTagContentCache" );

    public static Cache fastListContentCache = new ClusterCacheAdapter( 6000,
        "contentService.fastListContentCache" );

    public static Cache fastContentStatusCache = new ClusterCacheAdapter( 4000,
        "contentService.fastContentStatusCache" );

    public static Map<String, String> SW_REP = new HashMap<String, String>();// 无需集群化

    public static Map<String, String> SW_HL = new HashMap<String, String>();// 无需集群化

    public static Map<String, List> HL = new HashMap<String, List>();// 无需集群化

    private static ContentService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private ChannelService channelService = ChannelService.getInstance();

    private MetaDataService metaDataService = MetaDataService.getInstance();

    private WorkflowService workflowService = WorkflowService.getInstance();

    private ResourcesService resService = ResourcesService.getInstance();

    private SearchService searchService = SearchService.getInstance();

    private MessageService messageService = MessageService.getInstance();

    private SensitiveWord swFilter = new SensitiveWord();

    private PickDao pickDao = null;

    private MemberDao memberDao;

    private ContentDao contentDao;

    private ChannelDao channelDao;

    private MetaDataDao metaDataDao;

    private WorkFlowDao workFlowDao;

    private ResourcesDao resourcesDao;

    private SecurityDao securityDao;

    private ValiDao valiDao = null;

    private ContentService()
    {
        pickDao = new PickDao( mysqlEngine );
        contentDao = new ContentDao( mysqlEngine );
        channelDao = new ChannelDao( mysqlEngine );
        metaDataDao = new MetaDataDao( mysqlEngine );
        workFlowDao = new WorkFlowDao( mysqlEngine );

        resourcesDao = new ResourcesDao( mysqlEngine );

        securityDao = new SecurityDao( mysqlEngine );
        memberDao = new MemberDao( mysqlEngine );

        valiDao = new ValiDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new ContentService();
        }
    }

    public static ContentService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public Integer retrieveContentCensorStatusById( Long contentId )
    {
        return contentDao.queryContentCensorStatusById( contentId );
    }

    public ContentMainInfoBean retrieveSingleContentMainInfoBean( Long contentId )
    {
        return contentDao.querySingleContentMainInfoBean( contentId );
    }

    public Map retrieveSingleContentMainInfoMap( Long contentId )
    {
        return contentDao.querySingleContentMainInfo( contentId );
    }

    public List retrieveSingleContentMainInfoBeanByIds( List idList )
    {
        return contentDao.querySingleContentMainInfoBeanByIds( idList );
    }

    public List retrieveSingleTrashContentMainInfoBeanByIds( List idList )
    {
        return contentDao.querySingleTrashContentMainInfoBeanByIds( idList );
    }

    public List retrieveContentMainInfoByIds( List cidArrayList )
    {
        List result = new ArrayList();

        long contentId = -1;

        for ( int i = 0; i < cidArrayList.size(); i++ )
        {
            contentId = StringUtil.getLongValue( ( String ) cidArrayList.get( i ), -1 );

            if( contentId < 0 )
            {
                continue;
            }

            result.add( contentDao.querySingleContentMainInfoBean( Long.valueOf( contentId ) ) );
        }

        return result;
    }

    /**
     * 更新指定的内容静态化URL数据
     * 
     * @param endStaticClassFilePath
     * @param videoId
     */
    public void setContentStaticPageURL( String endStaticClassFilePath, Long contentId )
    {
        if( endStaticClassFilePath == null )
        {
            return;
        }

        boolean isUpdate = false;

        try
        {
            mysqlEngine.beginTransaction();

            ContentMainInfoBean mainInfo = contentDao.querySingleContentMainInfoBean( contentId );

            if( mainInfo != null && !endStaticClassFilePath.equals( mainInfo.getStaticPageUrl() ) )
            {
                contentDao.updateContentStaticPageURL( endStaticClassFilePath, contentId );

                isUpdate = true;
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            if( isUpdate )
            {
                ContentDao.releaseAllCountCache();
                releaseContentCache();
            }
        }

    }

    /**
     * 获取指定媒体资源的所有缩略图
     * 
     * @param contentId
     * @return
     */
    public List retrieveMediaSnapshotImageByContentId( Long contentId )
    {
        return contentDao.queryMediaSnapshotImageByContentId( contentId );
    }

    /**
     * 删除指定的图片,包括数据库以及对应所有磁盘文件
     * 
     * @param photoId
     */

    public void deleteSomeImageByPathInfo( String[] pathArray, String cmsRoot,
        SiteGroupBean siteBean )
    {
        if( pathArray == null || cmsRoot == null || siteBean == null )
        {
            return;
        }

        String targetFileBasePath = siteBean.getSiteRoot() + File.separator
            + siteBean.getImageRoot() + File.separator;

        StringBuilder buf = new StringBuilder();

        String path = null;
        for ( int i = 0; i < pathArray.length; i++ )
        {
            path = pathArray[i];

            if( StringUtil.isStringNull( path ) )
            {
                continue;
            }

            if( FileUtil.delFile( cmsRoot
                + targetFileBasePath
                + StringUtil.replaceString( path, Constant.CONTENT.URL_SEP, File.separator, false,
                    false ) ) )
            {
                log.info( "[Service] contentService - deleteSomeImageByPathInfo() 成功删除临时文件:"
                    + targetFileBasePath
                    + StringUtil.replaceString( path, Constant.CONTENT.URL_SEP, File.separator,
                        false, false ) );

                buf.append( targetFileBasePath + path + "," );

            }

            if( FileUtil.delFile( cmsRoot
                + targetFileBasePath
                + StringUtil.replaceString( path, Constant.CONTENT.URL_SEP, File.separator
                    + Constant.CONTENT.RESIZE_IMG_FLAG, false, false ) ) )
            {
                log.info( "[Service] contentService - deleteSomeImageByPathInfo() 成功删除临时文件:"
                    + targetFileBasePath
                    + StringUtil.replaceString( path, Constant.CONTENT.URL_SEP, File.separator
                        + Constant.CONTENT.RESIZE_IMG_FLAG, false, false ) );

                buf.append( targetFileBasePath
                    + StringUtil.replaceString( path, Constant.CONTENT.URL_SEP, "/"
                        + Constant.CONTENT.RESIZE_IMG_FLAG, false, false ) + "," );
            }

            // 删除res信息
            resourcesDao.deleteResInfoByRePath( path );

            ClusterService.exeClusterMasterCMD( "cluster/deleteFile.do", "fp=" + buf.toString(),
                Constant.COMMON.POST );

        }

    }

    /**
     * 添加新的图集附属图片信息
     * 
     * @param pgi
     */
    public Long addSingleGroupPhotoNotUse( PhotoGroupInfo pgi )
    {
        pgi.setPhotoAddTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

        return Long.valueOf( contentDao.saveSingleGroupPhoto( pgi ).getKey() );
    }

    /**
     * 对文章内容分页操作
     * 
     * @param text
     */
    @SuppressWarnings( "unchecked" )
    private List disposeContentPage( Long contentId, String fullContent )
    {
        if( fullContent == null )
        {
            return new ArrayList();
        }

        String targetContent = fullContent;

        List pageFlagList = new ArrayList();

        org.jsoup.nodes.Document flagContentDoc = Jsoup.parse( targetContent );

        Elements esfc = flagContentDoc
            .getElementsByClass( Constant.CONTENT.CONTENT_PAGE_SPLIT_CLASS );

        Iterator itfc = esfc.iterator();

        ContentAssistantPageInfo capInfoBean = null;

        while ( itfc.hasNext() )
        {
            pageFlagList.add( ( ( org.jsoup.nodes.Element ) itfc.next() ).outerHtml() );
        }

        List pageInfoList = new ArrayList();
        int pageCount = pageFlagList.size();

        for ( int i = 0; i < pageCount; i++ )
        {
            org.jsoup.nodes.Document infoDoc = Jsoup.parse( ( String ) pageFlagList.get( i ) );

            Elements esinfo = infoDoc
                .getElementsByClass( Constant.CONTENT.CONTENT_PAGE_SPLIT_CLASS );
            Iterator itinfo = esinfo.iterator();

            String flagInfoTemp = null;
            String[] flagInfoArray = null;

            int start = 0;
            int end = 0;

            if( itinfo.hasNext() )
            {
                start = targetContent.indexOf( ( String ) pageFlagList.get( i ) );

                if( i + 1 != pageCount )
                {
                    end = targetContent.indexOf( ( String ) pageFlagList.get( i + 1 ) );
                }
                else
                {
                    end = targetContent.length();
                }

                capInfoBean = new ContentAssistantPageInfo();
                flagInfoTemp = ( ( org.jsoup.nodes.Element ) itinfo.next() ).id();

                flagInfoArray = StringUtil.split( flagInfoTemp,
                    Constant.CONTENT.CONTENT_PAGE_SPLIT_STR );

                if( flagInfoArray.length < 2 )
                {
                    continue;
                }

                capInfoBean.setContentId( contentId );
                capInfoBean.setPos( Integer.valueOf( i + 1 ) );
                capInfoBean.setPageTitle( flagInfoArray[1] );

                capInfoBean.setStartPos( Integer.valueOf( start ) );
                capInfoBean.setEndPos( Integer.valueOf( end ) );

                capInfoBean.setPageContent( StringUtil.subString( targetContent, start
                    + ( ( String ) pageFlagList.get( i ) ).length(), end ) );

                pageInfoList.add( capInfoBean );

            }

        }

        return pageInfoList;
    }

    /**
     * 对文章内容分页操作
     * 
     * @param text
     */
    public String deleteContentTextOutHref( String fullContent, SiteGroupBean site )
    {
        if( site == null )
        {
            return null;
        }

        String webBase = site.getHostMainUrl();

        String targetContent = fullContent;

        if( StringUtil.isStringNull( targetContent ) )
        {
            return targetContent;
        }

        org.jsoup.nodes.Document flagContentDoc = Jsoup.parse( targetContent );

        Elements esfc = flagContentDoc.getElementsByTag( "a" );

        Iterator itfc = esfc.iterator();

        org.jsoup.nodes.Element te = null;

        String hrefHtml = null;

        String allHrefHtml = null;

        String href = null;
        while ( itfc.hasNext() )
        {
            te = ( ( org.jsoup.nodes.Element ) itfc.next() );

            href = te.attr( "href" );

            if( href != null && href.indexOf( webBase ) != -1 )
            {
                continue;
            }

            allHrefHtml = te.outerHtml();

            hrefHtml = te.text();

            targetContent = StringUtil.replaceString( targetContent, allHrefHtml, hrefHtml, false,
                false );
        }

        return targetContent;
    }

    public void setContentCensorState( Long contentId, Integer censorStatus )
    {
        try
        {
            mysqlEngine.beginTransaction();

            contentDao.updateContentCensorState( contentId, censorStatus );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            ContentDao.releaseAllCountCache();
            releaseContentCache();
        }
    }

    /**
     * 下载文章中出现的所有图片到本地
     * 
     * @param text
     */
    @SuppressWarnings( "unchecked" )
    public String downloadImageFormWeb( String fullUrl, String prefixUrl, String text,
        SiteGroupBean site, Long classId, List dfList )
    {
        if( StringUtil.isStringNull( text ) )
        {
            return null;
        }

        if( site == null )
        {
            return null;
        }

        List resList = new ArrayList();

        SystemRuntimeConfig config = SystemConfiguration.getInstance().getSystemConfig();

        String webBase = site.getSiteImagePrefixUrl();

        // String systemBase = config.getSystemRealPath();

        String uploadImageBasePath = config.getSystemRealPath() + File.separator
            + site.getSiteRoot() + File.separator + site.getImageRoot();

        String date = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        Map imgHandlerMap = new HashMap();

        String targetContent = text;

        int startFlag = targetContent.indexOf( "<img", 0 );

        while ( startFlag != -1 )
        {
            int end = targetContent.indexOf( ">", startFlag ) + 1;

            String targetImg = StringUtil.subString( targetContent, startFlag, end );

            int srcStart = targetImg.indexOf( "\"", targetImg.indexOf( " src" ) );

            int endPos = targetImg.indexOf( "\" ", srcStart );

            String jsosrc = null;

            if( endPos <= srcStart )
            {
                Document doc = Jsoup.parse( targetImg );

                Elements imgTags = doc.getElementsByTag( "img" );

                if( imgTags != null )
                {
                    Element element = imgTags.get( 0 );

                    if( element != null )
                    {
                        jsosrc = element.attr( "abs:src" );
                    }
                }

                if( StringUtil.isStringNull( jsosrc ) )
                {
                    startFlag = targetContent.indexOf( "<img", end );
                    continue;
                }
            }

            String targetSrc = "";

            if( StringUtil.isStringNull( jsosrc ) )
            {
                targetSrc = StringUtil.subString( targetImg, srcStart + 1, endPos );
            }
            else
            {
                targetSrc = jsosrc;
            }

            // 如果图片已经是本地的，则不需要做下载处理，后期若出现在图片服务器中的地址也不需要处理

            if( targetSrc.indexOf( webBase ) != -1 )
            {
                startFlag = targetContent.indexOf( "<img", end );
                continue;
            }

            String downTrueUrl = targetSrc;

            if( StringUtil.isStringNotNull( prefixUrl ) && targetSrc != null
                && !targetSrc.toLowerCase().startsWith( "http:" ) )
            {
                // downTrueUrl = prefixUrl + targetSrc;

                URI base;
                try
                {
                    base = new URI( fullUrl );

                    URI abs = base.resolve( targetSrc );

                    System.out.println( abs.toURL().toString() );

                    downTrueUrl = abs.toURL().toString();
                }
                catch ( Exception e )
                {

                    e.printStackTrace();
                }

            }

            File endImgFile = HtmlUtil.downloadImageByUrl( downTrueUrl, uploadImageBasePath
                + File.separator + date + Constant.CONTENT.URL_SEP );

            if( endImgFile != null && endImgFile.exists() )
            {

                String successDownloadImgName = endImgFile.getName();

                if( StringUtil.isStringNotNull( successDownloadImgName ) )
                {

                    imgHandlerMap.put( targetSrc, successDownloadImgName );

                    // 加入res
                    SiteResource resInfo = new SiteResource();

                    Object[] imgOffset = ImageUtil.getImageHeightAndWidth( endImgFile.getPath() );

                    Integer width = ( ( Integer ) imgOffset[0] );
                    Integer height = ( ( Integer ) imgOffset[1] );

                    // 缩略图
                    String resize = uploadImageBasePath + File.separator + date
                        + Constant.CONTENT.URL_SEP + "imgResize" + endImgFile.getName();

                    // ImageUtil.zoomPicture( targetFile, resize, 177 );
                    int maxResize = 140;

                    try
                    {
                        if( width.intValue() >= height.intValue() )
                        {

                            ImageUtil.resizeImage( maxResize, -1, endImgFile.getPath(), resize,
                                Constant.RESOURCE.IMAGE_RESIZE_Q_MID );

                        }
                        else
                        {
                            ImageUtil.resizeImage( -1, maxResize, endImgFile.getPath(), resize,
                                Constant.RESOURCE.IMAGE_RESIZE_Q_MID );
                        }
                    }
                    catch ( Exception e )
                    {
                        e.printStackTrace();
                    }

                    resInfo.setHeight( height );
                    resInfo.setWidth( width );

                    resInfo.setClassId( classId );
                    resInfo.setSiteId( site.getSiteId() );

                    resInfo.setResName( StringUtil.subString( successDownloadImgName, 0,
                        successDownloadImgName.lastIndexOf( "." ) ) );

                    String fileType = StringUtil.subString( successDownloadImgName,
                        successDownloadImgName.lastIndexOf( "." ) + 1,
                        successDownloadImgName.length() ).toLowerCase();

                    if( fileType.length() <= 5 )
                    {
                        resInfo.setFileType( fileType );
                    }
                    else
                    {
                        FileTypeEM ft = FileUtil.getFileType( endImgFile );

                        String type = ft.getExt();

                        resInfo.setFileType( type );
                    }

                    resInfo.setResType( Constant.RESOURCE.IMAGE_RES_TYPE );

                    resInfo.setModifyTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );
                    resInfo.setResSize( Long.valueOf( endImgFile.length() ) );
                    resInfo.setResSource( date + Constant.CONTENT.URL_SEP + endImgFile.getName() );

                    UpdateState us = resService
                        .addSiteResourceAndUploadTraceSuccessStatus( resInfo );

                    if( dfList != null )
                    {
                        dfList.add( Long.valueOf( us.getKey() ) );
                    }

                    resList.add( resInfo );

                }

            }
            else
            {
                log.warn( "无法下载图片，目标地址：" + targetSrc );
            }

            startFlag = targetContent.indexOf( "<img", end );
        }

        Iterator it = imgHandlerMap.entrySet().iterator();
        // 替换内容所有出现的已经下载成功的图片

        while ( it.hasNext() )
        {
            Entry entry = ( Entry ) it.next();

            targetContent = StringUtil.replaceString( targetContent, ( String ) entry.getKey(),
                webBase + date + Constant.CONTENT.URL_SEP + entry.getValue(), false, false );
        }

        // 进行数据分发
        SiteGroupService.getInstance().transferUpdateDataToServer( resList );

        return targetContent;

    }

    /**
     * 下载文章中出现的所有附件到本地
     * 
     * @param text
     */
    @SuppressWarnings( "unchecked" )
    public String downloadFileFormWeb( String fullUrl, String prefixUrl, String text,
        SiteGroupBean site, Long classId, List dfList )
    {
        if( StringUtil.isStringNull( text ) )
        {
            return null;
        }

        if( site == null )
        {
            return null;
        }

        List resList = new ArrayList();

        SystemRuntimeConfig config = SystemConfiguration.getInstance().getSystemConfig();

        String webBase = site.getSiteFilePrefixUrl();

        // String systemBase = config.getSystemRealPath();

        String uploadImageBasePath = config.getSystemRealPath() + File.separator
            + site.getSiteRoot() + File.separator + site.getFileRoot();

        String date = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        Map imgHandlerMap = new HashMap();

        Map newImgHandlerMap = new HashMap();

        String targetContent = text;

        /*
         * Document doc = Jsoup.parse( targetContent );
         * 
         * Elements es = doc.getElementsByTag( "img" );
         * 
         * Iterator iter = es.iterator();
         * 
         * Element el = null;
         * 
         * while ( iter.hasNext() ) { el = ( Element ) iter.next(); }
         */

        int startFlag = targetContent.indexOf( "<a", 0 );

        while ( startFlag != -1 )
        {
            int end = targetContent.indexOf( ">", startFlag ) + 1;

            String targetImg = StringUtil.subString( targetContent, startFlag, end );

            int srcStart = targetImg.indexOf( "\"", targetImg.indexOf( " href" ) );

            int endPos = targetImg.indexOf( "\" ", srcStart );

            if( endPos <= srcStart )
            {
                startFlag = targetContent.indexOf( "<a", end );
                continue;
            }

            String targetSrc = StringUtil.subString( targetImg, srcStart + 1, endPos );

            // 如果文件已经是本地的，则不需要做下载处理，后期若出现在文件服务器中的地址也不需要处理

            if( targetSrc.indexOf( webBase ) != -1 )
            {
                startFlag = targetContent.indexOf( "<a", end );
                continue;
            }

            String downTrueUrl = targetSrc;

            if( StringUtil.isStringNotNull( prefixUrl ) && targetSrc != null
                && !targetSrc.toLowerCase().startsWith( "http:" ) )
            {
                // if( prefixUrl.endsWith( "/" ) && targetSrc.startsWith( "/" )
                // )
                // {
                // downTrueUrl = StringUtil.subString( prefixUrl, 0,
                // prefixUrl.length() - 1 )
                // + targetSrc;
                // }
                // else
                // {
                // downTrueUrl = prefixUrl + targetSrc;
                // }

                URI base;
                try
                {
                    base = new URI( fullUrl );

                    URI abs = base.resolve( targetSrc );

                    System.out.println( abs.toURL().toString() );

                    downTrueUrl = abs.toURL().toString();
                }
                catch ( Exception e )
                {

                    e.printStackTrace();
                }
            }

            String testName = StringUtil.subString( downTrueUrl,
                downTrueUrl.lastIndexOf( "." ) + 1, downTrueUrl.length() );

            if( site.getFileAllowType().indexOf( testName + "," ) == -1 )
            {
                startFlag = targetContent.indexOf( "<a", end );
                continue;
            }

            Object[] df = HtmlUtil.downloadFileByUrl( downTrueUrl, uploadImageBasePath
                + File.separator + date + Constant.CONTENT.URL_SEP, true );
            
            File endFileFile = ( File ) df[0];

            if( endFileFile != null && endFileFile.exists() )
            {

                String successDownloadImgName = endFileFile.getName();

                if( StringUtil.isStringNotNull( successDownloadImgName ) )
                {

                    imgHandlerMap.put( targetSrc, successDownloadImgName );

                    // 加入res
                    SiteResource resInfo = new SiteResource();

                    resInfo.setClassId( classId );
                    resInfo.setSiteId( site.getSiteId() );

                    resInfo.setResName( StringUtil.subString( successDownloadImgName, 0,
                        successDownloadImgName.lastIndexOf( "." ) ) );

                    String fileType = StringUtil.subString( successDownloadImgName,
                        successDownloadImgName.lastIndexOf( "." ) + 1,
                        successDownloadImgName.length() ).toLowerCase();
                    resInfo.setFileType( fileType );

                    resInfo.setResType( Constant.RESOURCE.ANY_RES_TYPE );

                    resInfo.setModifyTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );
                    resInfo.setResSize( Long.valueOf( endFileFile.length() ) );
                    resInfo.setResSource( date + Constant.CONTENT.URL_SEP
                        + StringUtil.getUUIDString() + endFileFile.getName() );

                    UpdateState us = resService
                        .addSiteResourceAndUploadTraceSuccessStatus( resInfo );

                    if( dfList != null )
                    {
                        dfList.add( Long.valueOf( us.getKey() ) );

                        newImgHandlerMap.put( successDownloadImgName, Long.valueOf( us.getKey() ) );

                    }

                    resList.add( resInfo );

                }

            }
            else
            {
                log.warn( "无法下载文件，目标地址：" + targetSrc );
            }

            startFlag = targetContent.indexOf( "<a", end );
        }

        Iterator it = imgHandlerMap.entrySet().iterator();
        // 替换内容所有出现的已经下载成功的文件

        while ( it.hasNext() )
        {
            Entry entry = ( Entry ) it.next();

            Long resId = ( Long ) newImgHandlerMap.get( entry.getValue() );

            if( targetContent.indexOf( ( String ) entry.getKey() + "\"" ) != -1 )
            {
                targetContent = StringUtil.replaceString( targetContent, ( String ) entry.getKey()
                    + "\"", webBase + date + Constant.CONTENT.URL_SEP + entry.getValue()
                    + "\" id='jtopcms_content_file_" + resId + "' name='jtopcms_content_file' ",
                    false, false );
            }
            else
            {
                targetContent = StringUtil.replaceString( targetContent, ( String ) entry.getKey()
                    + "'", webBase + date + Constant.CONTENT.URL_SEP + entry.getValue()
                    + "' id='jtopcms_content_file_" + resId + "' name='jtopcms_content_file' ",
                    false, false );
            }
        }

        // 进行数据分发
        SiteGroupService.getInstance().transferUpdateDataToServer( resList );

        return targetContent;

    }
    
    /**
     * 更新内容状态
     * 
     * @param contentId
     * @param censorStatus
     */
    public void changeContentStatus( Long contentId, Integer censorStatus )
    {
        contentDao.updateContentMainInfoCensorStatus( contentId, censorStatus );

        ContentDao.releaseAllCountCache();
        releaseContentCache();
    }

    /**
     * VIEW模式下审核内容
     * 
     * @param params
     * @param contentId
     * @param classBean
     * @param info
     * @param wfActionList
     * @return
     */
    public ContentMainInfo censorUserDefineContent( Map params, ContentClassBean classBean,
        List wfActionList )
    {
        // 获取数据中实际info值,注意不能在以下update后获取,因为censor值会有暂时变换
        Map currentInfo = contentDao.querySingleContentMainInfo( Long.valueOf( StringUtil
            .getLongValue( ( String ) params.get( Constant.METADATA.CONTENT_ID_NAME ), -1 ) ) );

        ContentMainInfo info = new ContentMainInfo();

        info.setTitle( ( String ) currentInfo.get( "title" ) );

        info.setContentId( ( Long ) currentInfo.get( "contentId" ) );

        info.setAppearStartDateTime( ( Date ) currentInfo.get( "appearStartDateTime" ) );
        info.setAppearEndDateTime( ( Date ) currentInfo.get( "appearEndDateTime" ) );

        Integer infoType = Constant.WORKFLOW.INFO_TYPE_CONTENT;

        Integer endCensorState = ( Integer ) disposeWorkflowState( params, infoType, info
            .getContentId(), classBean, info, true, wfActionList, currentInfo )[0];

        info.setCensorState( endCensorState );

        ContentDao.releaseAllCountCache();
        releaseContentCache();

        return info;
    }

    /**
     * 检查内容中出现的图片和视频数
     * 
     * @param contentId
     * @return
     */
    public Integer[] checkContentImgAndVideoCount( Long contentId )
    {
        Map main = contentDao.querySingleContentMainInfo( contentId );

        /**
         * 获取对应元数据
         */
        ContentClassBean classBean = channelService
            .retrieveSingleClassBeanInfoByClassId( ( Long ) main.get( "classId" ) );

        DataModelBean modelBean = metaDataService.retrieveSingleDataModelBeanById( classBean
            .getContentType() );

        ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( classBean.getContentType() );

        if( sqlCodeBean == null )
        {
            return new Integer[] { 0, 0 };
        }

        Map info = contentDao.querySingleUserDefineContentManageMode( sqlCodeBean, modelBean
            .getRelateTableName(), contentId );

        List filedBeanList = metaDataService.retrieveModelFiledInfoBeanList( classBean
            .getContentType() );

        // 获取自定义模型数据
        ModelFiledInfoBean filedInfoBean = null;

        Integer ic = 0;

        Integer iv = 0;

        for ( int j = 0; j < filedBeanList.size(); j++ )
        {
            filedInfoBean = ( ModelFiledInfoBean ) filedBeanList.get( j );

            // 内部业务字段不需要处理
            if( Constant.METADATA.INNER_DATA == filedInfoBean.getHtmlElementId().intValue() )
            {
                continue;
            }

            if( Constant.METADATA.UPLOAD_IMG == filedInfoBean.getHtmlElementId().intValue() )
            {
                Long id = StringUtil.getLongValue( ( String ) info.get( filedInfoBean
                    .getFieldSign()
                    + "ResId" ), -1 );

                if( id.longValue() > 0 )
                {
                    ic++;
                }
            }
            if( Constant.METADATA.UPLOAD_MEDIA == filedInfoBean.getHtmlElementId().intValue() )
            {
                Long id = StringUtil.getLongValue( ( String ) info.get( filedInfoBean
                    .getFieldSign()
                    + "ResId" ), -1 );

                if( id.longValue() > 0 )
                {
                    iv++;
                }
            }
            else if( Constant.METADATA.UPLOAD_IMG_GROUP == filedInfoBean.getHtmlElementId()
                .intValue() )
            {
                int count = StringUtil.getIntValue( ( String ) info.get( filedInfoBean
                    .getFieldSign()
                    + "CmsSysCount" ), 0 );

                ic += count;
            }
            else if( Constant.METADATA.EDITER == filedInfoBean.getHtmlElementId().intValue() )
            {

                String text = ( String ) info.get( filedInfoBean.getFieldSign() );

                if( text == null )
                {
                    text = "";
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
                        ic++;
                    }
                    else if( id.startsWith( "jtopcms_content_media_" ) )
                    {
                        iv++;
                    }

                }
            }

        }

        return new Integer[] { ic, iv };
    }

    /**
     * 新增加或改动系统内容模型信息,根据各模型特点进行数据处理
     * 
     * @param classBean
     * @param params
     * @param wfActionList
     * @param filedBeanList
     * @param sqlCodeBean
     * @param editMode
     * @return
     */
    @SuppressWarnings( "unchecked" )
    public ContentMainInfo addOrEditUserDefineContent( ContentClassBean classBean, Map params,
        List wfActionList, List filedBeanList, ModelPersistenceMySqlCodeBean sqlCodeBean,
        boolean editMode )
    {
        if( params == null || filedBeanList == null || sqlCodeBean == null || classBean == null )
        {
            return null;
        }

        /**
         * 处理main数据
         */
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( classBean.getSiteFlag() );

        DataModelBean model = metaDataDao.querySingleDataModelBeanById( classBean.getContentType() );

        Map workValue = new HashMap();

        ContentMainInfo info = getSystemModelValueFromWebParam( editMode, classBean, params,
            workValue, model.getMainEditorFieldSign(), site );

        // 获取自定义模型数据
        ModelFiledInfoBean bean = null;

        List needUploadImageGroupInfoList = new ArrayList();

        List userDefineParamList = new ArrayList();

        Object val = null;

        String reUrl = null;

        Map currentObj = null;

        if( editMode )
        {
            currentObj = contentDao.querySingleUserDefineContent( sqlCodeBean, model
                .getRelateTableName(), info.getContentId() );
        }

        for ( int j = 0; j < filedBeanList.size(); j++ )
        {
            bean = ( ModelFiledInfoBean ) filedBeanList.get( j );

            // 内部业务字段需要强制设定默认值
            if( Constant.METADATA.INNER_DATA == bean.getHtmlElementId().intValue() )
            {
                continue;
            }

            // 需要引入filed元数据来对不同类型字段进行对应处理
            val = ServiceUtil.disposeDataModelFiledFromWeb( bean, params,
                needUploadImageGroupInfoList, false );

            if( val == null )
            {
                val = bean.getDefaultValue();
            }

            if( editMode && !params.containsKey( bean.getFieldSign() ) )
            {
                val = currentObj.get( bean.getFieldSign() );
            }

            userDefineParamList.add( val );

            // 单图水印处理
            if( Constant.METADATA.UPLOAD_IMG == bean.getHtmlElementId().intValue()
                && Constant.COMMON.ON.equals( bean.getNeedMark() ) )
            {
                // 水印处理
                reUrl = ServiceUtil.getImageReUrl( ( String ) val );

                // 已经加过水印的不需要再增加
                if( !Constant.COMMON.ON.equals( resService.getImageMarkStatus( reUrl ) ) )
                {
                    if( ServiceUtil.disposeImageMark( site, reUrl, Integer.valueOf( ServiceUtil
                        .getImageW( ( String ) val ) ), Integer.valueOf( ServiceUtil
                        .getImageH( ( String ) val ) ) ) )
                    {
                        // 成功加水印则更新
                        resService.setImageMarkStatus( reUrl, Constant.COMMON.ON );
                    }
                }

            }

            // 自定义时间排序类型处理,需要独立新加值
            if( Constant.METADATA.MYSQL_DATETIME.equals( bean.getPerdureType() )
                && Constant.COMMON.ON.equals( bean.getOrderFlag() ) )
            {
                if( val instanceof Timestamp )
                {
                    userDefineParamList.add( Long.valueOf( ( ( Timestamp ) val ).getTime() ) );
                }
                else
                {
                    userDefineParamList.add( DateAndTimeUtil.clusterTimeMillis() );
                }
            }
        }

        /**
         * 处理自动关键字
         */
        if( Constant.COMMON.ON.equals( site.getGenKw() )
            && StringUtil.isStringNull( ( String ) params.get( "keywords" ) ) )
        {
            List keyInfoList = null;

            keyInfoList = searchService.disposeTextKeyword( ( String ) params.get( "title" ) );

            String key = null;

            StringBuffer buf = new StringBuffer();

            for ( int i = 0; i < keyInfoList.size(); i++ )
            {
                key = ( String ) keyInfoList.get( i );

                if( i < KEY_SIZE )
                {
                    buf.append( key + " " );
                }

            }

            info.setKeywords( buf.toString() );
        }

        Integer endCensorState = null;

        Integer infoType = Integer.valueOf( 1 );

        try
        {
            mysqlEngine.beginTransaction();

            // 获取数据中实际info值,注意不能在以下update后获取,因为censor值会有暂时变换
            Map currentInfo = contentDao.querySingleContentMainInfo( Long.valueOf( StringUtil
                .getLongValue( ( String ) params.get( Constant.METADATA.CONTENT_ID_NAME ), -1 ) ) );

            if( editMode )
            {

                // 将ID放在最后一个位置
                userDefineParamList.add( Long.valueOf( StringUtil.getLongValue( ( String ) params
                    .get( Constant.METADATA.CONTENT_ID_NAME ), -1 ) ) );

                // 会员提交稿件参数补充
                if( info.getAppearStartDateTime() == null )
                {
                    info.setAppearStartDateTime( ( Date ) currentInfo.get( "appearStartDateTime" ) );
                }

                UpdateState updateState = contentDao.updateContentMainInfo( info );

                if( updateState.getRow() > 0 )
                {
                    contentDao.saveOrUpdateModelContent( sqlCodeBean.getUpdateSql(),
                        userDefineParamList.toArray() );
                }

                // 确定工作流状态
                Object[] wi = disposeWorkflowState( params, infoType, info.getContentId(),
                    classBean, info, editMode, wfActionList, currentInfo );

                endCensorState = ( Integer ) wi[0];

                params.put( "_*sys_ft", wi[1] );

            }
            else
            {
                UpdateState updateState = contentDao.saveContentMainInfo( info );

                if( updateState.haveKey() )
                {
                    Long contentId = Long.valueOf( updateState.getKey() );

                    // 增加内容快速更新状态表 不再使用,保留记录
                    // ContentStatus cs = new ContentStatus();

                    // cs.setSelfContentId( contentId );

                    // contentDao.saveContentStatus( cs );

                    info.setContentId( contentId );

                    // 将ID放在最后一个位置
                    userDefineParamList.add( contentId );

                    contentDao.saveOrUpdateModelContent( sqlCodeBean.getInsertSql(),
                        userDefineParamList.toArray() );

                    // 设定最新的排序ID
                    contentDao.updateSystemContentOrderIdFlag( Double
                        .valueOf( updateState.getKey() ), contentId );

                    // 工作流处理

                    Object[] wi = disposeWorkflowState( params, infoType, info.getContentId(),
                        classBean, info, editMode, wfActionList, currentInfo );

                    endCensorState = ( Integer ) wi[0];

                    params.put( "_*sys_ft", wi[1] );

                    // 内部业务字段初始化值
                    for ( int j = 0; j < filedBeanList.size(); j++ )
                    {
                        bean = ( ModelFiledInfoBean ) filedBeanList.get( j );

                        // 内部业务字段需要强制设定默认值
                        if( Constant.METADATA.INNER_DATA == bean.getHtmlElementId().intValue() )
                        {
                            metaDataService.updateFieldMetadataDefValAndId( model,
                                Constant.METADATA.PREFIX_COLUMN_NAME + bean.getFieldSign(), bean
                                    .getDefaultValue(), contentId );
                        }
                    }

                }

            }

            /**
             * 处理tag信息,只有通过审核发布的内容
             */
            if( Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( endCensorState ) )
            {
                List tagIdList = StringUtil.changeStringToList( ( String ) params.get( "tagKey" ),
                    "\\*" );

                Long tagId = null;

                // 先删除所有相关Tag关联
                channelDao.deleteTagRelateContentByContentId( info.getContentId() );

                for ( int i = 0; i < tagIdList.size(); i++ )
                {
                    if( tagIdList.get( i ) instanceof Long )
                    {
                        tagId = ( Long ) tagIdList.get( i );
                    }
                    else
                    {
                        tagId = Long.valueOf( StringUtil.getLongValue(
                            ( String ) tagIdList.get( i ), -1 ) );
                    }

                    if( tagId.longValue() < 0 )
                    {
                        continue;
                    }

                    channelDao.saveTagWordRelateContent( tagId, info.getContentId() );

                    channelDao.updateTagWordRelateContentCount( tagId );
                }
            }

            /**
             * 处理指定和自动相关内容
             */

            // 清除
            contentDao.deleteRelateContentId( info.getContentId() );

            if( StringUtil.isStringNull( ( String ) params.get( "relateIds" ) )
                && StringUtil.isStringNotNull( ( String ) params.get( "keywords" ) ) )
            {
                // 自动关联模式,使用lucene高速查询
                String keyVal = ( String ) params.get( "keywords" );

                Object[] result = searchService.searchContentByKey( site, null, null, null,
                    channelService.retrieveSiteNotUseRelateFunClassId( site.getSiteFlag() ), Long
                        .valueOf( StringUtil
                            .getLongValue( ( String ) params.get( "contentId" ), -1 ) ),
                    new String[] {}, keyVal, RELATE_SIZE, 1, false, false );

                // 多关联模式业务中作为默认分类的关联内容
                List reIdList = ( List ) result[0];

                String id = null;

                for ( int i = 0; i < reIdList.size(); i++ )
                {
                    id = ( String ) reIdList.get( i );

                    contentDao
                        .saveRelateContentId( info.getContentId(), 0, Long.valueOf( id ), ++i );
                }

            }

            // 指定的关联内容
            if( StringUtil.isStringNotNull( ( String ) params.get( "relateIds" ) ) )
            {
                // 0:123*1234!1:345:345
                List<String> ridslist = StringUtil.changeStringToList( ( String ) params
                    .get( "relateIds" ), "-" );

                Integer tid = null;

                String[] ti = null;

                for ( String rids : ridslist )
                {
                    // 0
                    ti = rids.split( ":" );

                    // 0:123*1234
                    tid = StringUtil.getIntValue( ti[0], -9999 );

                    if( tid == -9999 )
                    {
                        continue;
                    }

                    int order = 1;

                    if( ti.length == 2 )
                    {
                        for ( String rid : StringUtil.changeStringToList( ti[1], "_" ) )
                        {
                            contentDao.saveRelateContentId( info.getContentId(), tid, Long
                                .valueOf( rid ), order );

                            order++;
                        }
                    }

                }

            }

            /**
             * 文章资源类型模型内容分页处理
             */

            List pageInfoList = null;
            if( Constant.METADATA.MODEL_RES_ARTICLE.equals( model.getModelResType() ) )
            {
                // 删除文章类型内容分页信息
                contentDao.deleteContentAssistantPageInfoByContentId( info.getContentId() );

                // 文章类型模型独立内容分页
                pageInfoList = disposeContentPage( info.getContentId(), ( String ) params
                    .get( model.getMainEditorFieldSign() ) );

                if( !pageInfoList.isEmpty() )
                {
                    // 设定对应mainInfo为内容分页状态
                    contentDao.updateContentPageModeInfo( info.getContentId(), Constant.COMMON.ON );
                }
                else
                {
                    // 设定对应mainInfo为非内容分页状态
                    contentDao.updateContentPageModeInfo( info.getContentId(), Constant.COMMON.OFF );
                }
            }

            // 以下批处理改动统一执行
            // mysqlEngine.startBatch();

            /**
             * 所有图集组件出现的图片入库
             */
            // 获取原图集记录
            List oldGroupPhotoList = contentDao.queryGroupPhotoInfoByContentId(
                info.getContentId(), Constant.METADATA.MODEL_TYPE_CONTENT, site, true );

            // 删除所有原相关信息
            contentDao.deletePhotoGroupInfo( info.getContentId(),
                Constant.METADATA.MODEL_TYPE_CONTENT );

            // 增加本次改动中出现的所有的图片信息
            PhotoGroupInfo pgi = null;
            Set urlInfoSet = new HashSet();

            for ( int i = 0; i < needUploadImageGroupInfoList.size(); i++ )
            {
                pgi = ( PhotoGroupInfo ) needUploadImageGroupInfoList.get( i );

                urlInfoSet.add( pgi.getUrl() );

                pgi.setContentId( info.getContentId() );

                pgi.setModelType( Constant.METADATA.MODEL_TYPE_CONTENT );

                contentDao.saveSingleGroupPhoto( pgi );

                // 水印处理
                if( Constant.COMMON.ON.equals( pgi.getNeedMark() ) )
                {
                    reUrl = ServiceUtil.getImageReUrl( pgi.getUrl() );

                    // 已经加过水印的不需要再增加
                    if( !Constant.COMMON.ON.equals( resService.getImageMarkStatus( reUrl ) ) )
                    {
                        if( ServiceUtil.disposeImageMark( site, reUrl, Integer.valueOf( ServiceUtil
                            .getImageW( pgi.getUrl() ) ), Integer.valueOf( ServiceUtil
                            .getImageH( pgi.getUrl() ) ) ) )
                        {
                            // 成功加水印则更新
                            resService.setImageMarkStatus( reUrl, Constant.COMMON.ON );
                        }
                    }
                }
            }

            Map pgiInfo = null;
            for ( int i = 0; i < oldGroupPhotoList.size(); i++ )
            {
                pgiInfo = ( Map ) oldGroupPhotoList.get( i );

                if( !urlInfoSet.contains( pgiInfo.get( "cmsSysUrl" ) ) )
                {
                    resService.updateSiteResourceTraceUseStatus( Long.valueOf( StringUtil
                        .getLongValue( ( String ) pgiInfo.get( "resId" ), -1 ) ),
                        Constant.COMMON.OFF );

                    Integer ic = ( Integer ) params.get( "_sys_content_image_res_del_count_" );

                    ic++;

                    params.put( "_sys_content_image_res_del_count_", Integer.valueOf( ic ) );
                }

            }

            // 增加本次改动中出现的所有的内容分页信息
            // ContentAssistantPageInfo capInfoBean = null;
            if( pageInfoList != null )
            {
                for ( int i = 0; i < pageInfoList.size(); i++ )
                {
                    // capInfoBean = ;
                    contentDao
                        .saveArticleContentPageInfo( ( ContentAssistantPageInfo ) pageInfoList
                            .get( i ) );
                }
            }

            // mysqlEngine.executeBatch();

            /**
             * 将需要索引的信息记录入库，由线程统一解析
             */
            // 自定义模型支持搜索字段,需要将模型信息加入索引准备
            SearchIndexContentState searchIndexState = new SearchIndexContentState();

            searchIndexState.setClassId( classBean.getClassId() );
            searchIndexState.setContentId( info.getContentId() );

            searchIndexState.setCensor( endCensorState );

            searchIndexState.setIndexDate( info.getAddTime() );
            searchIndexState.setBoost( info.getBoost() );
            searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_ADD );

            searchIndexState.setModelId( info.getModelId() );
            searchIndexState
                .setSiteId( ( ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                    .getEntry( classBean.getSiteFlag() ) ).getSiteId() );

            searchService.addIndexContentState( searchIndexState );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            ContentDao.releaseAllCountCache();
            releaseContentCache();
        }

        // 若存在工作流信息，发送必要提示给参数管理员

        info.setCensorState( endCensorState );

        return info;
    }

    // public void setImageGroupCover(Long )

    /**
     * 移动内容到指定栏目
     * 
     * @param contentId
     * @param classIdList
     */
    public boolean moveContentToSiteClass( List contentIdList, Long classId )
    {
        log.info( "[service] moveContentToSiteClass : contentIdList=" + contentIdList
            + ", classId:" + classId );

        if( classId == null || classId.longValue() < 0 )
        {
            return false;
        }

        try
        {
            mysqlEngine.beginTransaction();
            Long contentId = null;
            Long selfClassId = null;
            for ( int x = contentIdList.size() - 1; x >= 0; x-- )
            {
                if( contentIdList.get( x ) instanceof Long )
                {
                    contentId = ( Long ) contentIdList.get( x );
                }
                else
                {
                    contentId = Long.valueOf( StringUtil.getLongValue( ( String ) contentIdList
                        .get( x ), -1 ) );
                }

                if( contentId == null || contentId.longValue() < 1 )
                {
                    continue;
                }

                // 检查对应栏目模型
                Map mainInfo = contentDao.querySingleContentMainInfo( contentId );

                DataModelBean modelBean = metaDataDao
                    .querySingleDataModelBeanById( ( Long ) mainInfo.get( "modelId" ) );

                // 判断classId数据模型是否和目标内容模型一致

                ContentClassBean classBean = channelService
                    .retrieveSingleClassBeanInfoByClassId( classId );

                if( classBean == null )
                {
                    continue;
                }

                if( !classBean.getContentType().equals( modelBean.getDataModelId() ) )
                {
                    continue;
                }

                // 判断是否当前content已经在目标栏目

                selfClassId = ( Long ) mainInfo.get( "classId" );

                if( classId.equals( selfClassId ) )
                {
                    continue;
                }

                // 更新核心数据
                contentDao.updateContentMoveClassInfo( contentId, classId, classBean
                    .getContentProduceType(), classBean.getOpenComment() );

                // 移动内容到其他栏目功能的工作流处理,当目标栏目有工作流时,必须转为稿件状态,等待发布审核流程,若无工作流,更新发布状态

                Integer endCensor = null;

                if( classBean.getWorkflowId().longValue() > 0 )
                {
                    // 进入稿件状态
                    contentDao.updateContentMainInfoCensorStatus( contentId,
                        Constant.WORKFLOW.CENSOR_STATUS_DRAFT );

                    endCensor = Constant.WORKFLOW.CENSOR_STATUS_DRAFT;
                }
                else
                {
                    // 默认发布状态

                    endCensor = WorkflowService.pendingCensorStateByStartAndEndPublishDate(
                        new Timestamp( ( ( Timestamp ) mainInfo.get( "appearStartDateTime" ) )
                            .getTime() ), new Timestamp( ( ( Timestamp ) mainInfo
                            .get( "appearEndDateTime" ) ).getTime() ),
                        Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );

                    contentDao.updateContentMainInfoCensorStatus( contentId, endCensor );
                }

                // 更新索引信息
                // 自定义模型支持搜索字段,需要将模型信息加入索引准备
                SearchIndexContentState searchIndexState = new SearchIndexContentState();

                searchIndexState.setClassId( classId );
                searchIndexState.setContentId( contentId );

                searchIndexState.setCensor( endCensor );
                searchIndexState.setBoost( ( Float ) mainInfo.get( "boost" ) );
                searchIndexState
                    .setIndexDate( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );
                searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_EDIT );

                searchIndexState.setModelId( classBean.getContentType() );
                searchIndexState
                    .setSiteId( ( ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                        .getEntry( classBean.getSiteFlag() ) ).getSiteId() );

                searchService.addIndexContentState( searchIndexState );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            ContentDao.releaseAllCountCache();
            releaseContentCache();
        }

        log.info( "[service] moveContentToSiteClass ...over..." );

        return true;
    }

    public void moveAllContentToSiteClass( Long classId, Long targetMoveToClassId )
    {
        // 不开启事务,以下调用带事务删除

        Long prevCid = Long.valueOf( Constant.CONTENT.MAX_ID_FLAG );

        Long modelId = null;

        ContentClassBean classBean = channelDao.querySingleClassBeanInfoByClassId( classId );

        if( classId != null )
        {
            modelId = classBean.getContentType();
        }

        List needMoveContentList = contentDao.queryMainContentIdByClassIdAndModelId( classId,
            modelId, prevCid, DELETE_QUERY_COUNT );

        while ( !needMoveContentList.isEmpty() )
        {
            prevCid = ( Long ) needMoveContentList.get( needMoveContentList.size() - 1 );

            moveContentToSiteClass( needMoveContentList, targetMoveToClassId );

            needMoveContentList = contentDao.queryMainContentIdByClassIdAndModelId( classId,
                modelId, prevCid, DELETE_QUERY_COUNT );
        }
    }

    /**
     * 复制内容到指定栏目,返回立即发布的id字符
     * 
     * @param contentId
     * @param classIdList
     */
    public String copyContentToSiteClass( List contentIdList, List classIdList, boolean shareMode )
    {
        log.info( "[service] copyContentToSiteClass : contentIdList=" + contentIdList
            + ", classIdList:" + classIdList );

        if( classIdList == null || classIdList.isEmpty() )
        {
            return "";
        }

        SiteGroupBean currSite = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        ContentClassBean testClassBean = channelService
            .retrieveSingleClassBeanInfoByClassId( StringUtil.getLongValue( ( String ) classIdList
                .get( 0 ), -1 ) );

        SiteGroupBean targetSite = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( testClassBean.getSiteFlag() );

        StringBuffer buf = new StringBuffer( "" );
        try
        {
            mysqlEngine.beginTransaction();

            Long contentId = null;
            for ( int x = contentIdList.size() - 1; x >= 0; x-- )
            {
                if( contentIdList.get( x ) instanceof Long )
                {
                    contentId = ( Long ) contentIdList.get( x );
                }
                else
                {
                    contentId = Long.valueOf( StringUtil.getLongValue( ( String ) contentIdList
                        .get( x ), -1 ) );
                }

                if( contentId == null || contentId.longValue() < 1 )
                {
                    continue;
                }

                Map mainInfo = contentDao.querySingleContentMainInfo( contentId );

                if( mainInfo.isEmpty() )
                {
                    continue;
                }

                if( mainInfo.get( "linkCid" ) != null
                    && ( ( Long ) mainInfo.get( "linkCid" ) ).longValue() > 0 )
                {
                    continue;
                }

                DataModelBean modelBean = metaDataDao
                    .querySingleDataModelBeanById( ( Long ) mainInfo.get( "modelId" ) );

                if( modelBean == null )
                {
                    continue;
                }

                long classId = -1;
                ContentClassBean classBean = null;
                List copySuccessIdList = new ArrayList();

                for ( int i = 0; i < classIdList.size(); i++ )
                {
                    classId = StringUtil.getLongValue( ( String ) classIdList.get( i ), -1 );

                    if( classId < 0 )
                    {
                        continue;
                    }

                    classBean = channelService.retrieveSingleClassBeanInfoByClassId( Long
                        .valueOf( classId ) );

                    // 判断classId数据模型是否和目标内容模型一致
                    if( classBean.getContentType() != null
                        && !classBean.getContentType().equals( modelBean.getDataModelId() ) )
                    {
                        continue;
                    }

                    // 将由站点配置决定 判断标题是否重复,若重复不可复制

                    if( Constant.COMMON.OFF.equals( targetSite.getSameTitle() ) )
                    {
                        boolean titleExist = contentDao.checkContentTitle( ( String ) mainInfo
                            .get( "title" ), classBean.getClassId() );

                        if( titleExist )
                        {
                            // continue;
                        }
                    }

                    // 当前时间日期
                    String day = DateAndTimeUtil
                        .getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

                    // 加入合法的classId
                    copySuccessIdList.add( Long.valueOf( classId ) );

                    // 复制主表信息

                    ContentMainInfo copyMainInfo = changeDbMapToNewCopyContentMainVo( mainInfo,
                        classBean );

                    if( shareMode )
                    {
                        // 共享模式需要改变新内容的siteId
                        copyMainInfo.setSiteId( currSite.getSiteId() );
                    }
                    else
                    {
                        copyMainInfo.setSiteId( targetSite.getSiteId() );
                    }

                    // 去掉内容特征标志
                    copyMainInfo.setCommendFlag( Constant.COMMON.OFF );
                    copyMainInfo.setTopFlag( Constant.COMMON.OFF );
                    copyMainInfo.setTitle( copyMainInfo.getTitle() );

                    UpdateState us = contentDao.saveContentMainInfo( copyMainInfo );

                    // 插入主数据成功
                    if( us.haveKey() )
                    {
                        // // 增加内容快速更新状态表,不再使用,保留记录
                        // ContentStatus cs = new ContentStatus();
                        //
                        // cs.setSelfContentId( Long.valueOf( us.getKey() ) );
                        //
                        // contentDao.saveContentStatus( cs );

                        // 更新排序ID
                        contentDao.updateSystemContentOrderIdFlag( Double.valueOf( us.getKey() ),
                            Long.valueOf( us.getKey() ) );

                        // 复制内容业务工作流处理,当目标栏目有工作流时,必须转为稿件状态,等待发布审核流程,若无工作流,更新发布状态
                        Integer endCensor = null;

                        if( classBean.getWorkflowId().longValue() > 0 )
                        {
                            // 进入稿件状态
                            contentDao.updateContentMainInfoCensorStatus( Long
                                .valueOf( us.getKey() ), Constant.WORKFLOW.CENSOR_STATUS_DRAFT );

                            endCensor = Constant.WORKFLOW.CENSOR_STATUS_DRAFT;
                        }
                        else
                        {
                            // 默认发布状态
                            endCensor = WorkflowService.pendingCensorStateByStartAndEndPublishDate(
                                new Timestamp(
                                    ( ( Timestamp ) mainInfo.get( "appearStartDateTime" ) )
                                        .getTime() ), new Timestamp( ( ( Timestamp ) mainInfo
                                    .get( "appearEndDateTime" ) ).getTime() ),
                                Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );

                            contentDao.updateContentMainInfoCensorStatus( Long
                                .valueOf( us.getKey() ), endCensor );

                            // 记录发布成功的ID
                            buf.append( Long.valueOf( us.getKey() ).toString() + "," );

                            // 发布状态,需要更新排序ID
                            contentDao.updateSystemPublishIdFlag( getNextPublishOrderTrace(), Long
                                .valueOf( us.getKey() ) );
                        }

                        ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
                            .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() );

                        // 获取副表信息
                        Map info = contentDao
                            .querySingleUserDefineContentOnlyModelDataResultNotDisposeInfo(
                                sqlCodeBean, modelBean.getRelateTableName(), contentId );

                        // 副表文件字段
                        List allFiledList = metaDataDao
                            .queryUserDefinedModelFiledInfoBeanList( modelBean.getDataModelId() );

                        ModelFiledInfoBean fBean = null;

                        for ( int ai = 0; ai < allFiledList.size(); ai++ )
                        {
                            fBean = ( ModelFiledInfoBean ) allFiledList.get( ai );

                            if( Constant.METADATA.UPLOAD_IMG == fBean.getHtmlElementId().intValue() )
                            {
                                // 附件copy
                                // 复制所有图片物理信息
                                String resInfo = ServiceUtil.copyImageRes( ( String ) info
                                    .get( Constant.METADATA.PREFIX_COLUMN_NAME
                                        + fBean.getFieldSign() ), targetSite, day, Long
                                    .valueOf( classId ) );

                                info.put( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign(), resInfo );

                            }
                            else if( Constant.METADATA.UPLOAD_MEDIA == fBean.getHtmlElementId()
                                .intValue() )
                            {
                                // 附件copy
                                // 复制所有附件物理信息
                                String resInfo = ServiceUtil.copyMediaRes( ( String ) info
                                    .get( Constant.METADATA.PREFIX_COLUMN_NAME
                                        + fBean.getFieldSign() ), targetSite, day, Long
                                    .valueOf( classId ) );

                                info.put( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign(), resInfo );

                            }
                            else if( Constant.METADATA.UPLOAD_FILE == fBean.getHtmlElementId()
                                .intValue() )
                            {
                                // 附件copy
                                // 复制所有附件物理信息
                                String resInfo = ServiceUtil.copyFileRes( ( String ) info
                                    .get( Constant.METADATA.PREFIX_COLUMN_NAME
                                        + fBean.getFieldSign() ), targetSite, day, Long
                                    .valueOf( classId ) );

                                info.put( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign(), resInfo );

                            }
                            else if( Constant.METADATA.UPLOAD_IMG_GROUP == fBean.getHtmlElementId()
                                .intValue() )
                            {
                                // 附件copy
                                // 复制所有图集物理信息
                                List groupPhotoList = contentDao
                                    .queryGroupPhotoInfoByContentIdAndGroupSignModelDataMode(
                                        contentId, fBean.getFieldSign(),
                                        Constant.METADATA.MODEL_TYPE_CONTENT );

                                PhotoGroupInfo newPgi = null;
                                Map pgiInfo = null;
                                Integer isCover = null;
                                String cover = "";

                                for ( int j = 0; j < groupPhotoList.size(); j++ )
                                {
                                    pgiInfo = ( Map ) groupPhotoList.get( j );

                                    // 复制所有图片物理信息
                                    String resInfo = ServiceUtil.copyImageRes( ( String ) pgiInfo
                                        .get( "url" ), targetSite, day, Long.valueOf( classId ) );

                                    newPgi = new PhotoGroupInfo();

                                    newPgi.setContentId( Long.valueOf( us.getKey() ) );

                                    isCover = ( Integer ) pgiInfo.get( "isCover" );

                                    newPgi.setIsCover( isCover );

                                    if( isCover != null && Constant.COMMON.ON.equals( isCover ) )
                                    {

                                        if( resInfo != null )
                                        {
                                            cover = StringUtil.subString( resInfo, resInfo
                                                .indexOf( "reUrl=" ) + 6, resInfo.indexOf( ";",
                                                resInfo.indexOf( "reUrl=" ) + 6 ) );
                                        }
                                    }

                                    newPgi.setGroupSign( ( String ) pgiInfo.get( "groupSign" ) );
                                    newPgi.setOrderFlag( ( Integer ) pgiInfo.get( "orderFlag" ) );
                                    newPgi.setOutLinkUrl( ( String ) pgiInfo.get( "outLinkUrl" ) );
                                    newPgi.setPhotoAddTime( ( Timestamp ) pgiInfo
                                        .get( "photoAddTime" ) );
                                    newPgi.setPhotoDesc( ( String ) pgiInfo.get( "photoDesc" ) );
                                    newPgi.setPhotoName( ( String ) pgiInfo.get( "photoName" ) );
                                    newPgi.setNeedMark( ( Integer ) pgiInfo.get( "needMark" ) );
                                    newPgi.setUrl( resInfo );

                                    newPgi.setModelType( Constant.METADATA.MODEL_TYPE_CONTENT );

                                    contentDao.saveSingleGroupPhoto( newPgi );

                                }

                                info.put( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign(), "count=" + groupPhotoList.size()
                                    + ";gid=" + fBean.getFieldSign() + ";reUrl=" + cover + ";" );

                            }
                            else if( Constant.METADATA.EDITER == fBean.getHtmlElementId()
                                .intValue() )
                            {
                                // 编辑器资源深度copy
                                String content = ( String ) info
                                    .get( Constant.METADATA.PREFIX_COLUMN_NAME
                                        + fBean.getFieldSign() );

                                Document doc = Jsoup.parse( content != null ? content : "" );

                                Element ele = doc.body();

                                Elements eles = ele.getAllElements();

                                Iterator iter = eles.iterator();

                                Element objEle;

                                String targetId = null;

                                String endContent = content;

                                while ( iter.hasNext() )
                                {
                                    objEle = ( Element ) iter.next();

                                    targetId = objEle.attr( "id" );

                                    if( StringUtil.isStringNotNull( targetId ) )
                                    {
                                        endContent = copyEditorRes( endContent, objEle, targetId,
                                            targetSite, day, classId );
                                    }
                                }

                                info.put( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign(), endContent );

                            }
                        }

                        info.put( "contentId", Long.valueOf( us.getKey() ) );

                        // 存储拷贝内容副表信息
                        contentDao.saveOrUpdateModelContent( sqlCodeBean.getInsertSql(), info );

                        // 若是文章和栏目类型,要复制辅助信息
                        if( Constant.METADATA.MODEL_RES_ARTICLE
                            .equals( modelBean.getModelResType() ) )
                        {
                            // 分页信息
                            List articlePageInfo = contentDao
                                .queryContentAssistantPageInfoBeanByContentIdDataMode( contentId,
                                    info, classBean );

                            Map pageInfo = null;
                            ContentAssistantPageInfo capInfoBean = null;
                            for ( int j = 0; j < articlePageInfo.size(); j++ )
                            {
                                pageInfo = ( Map ) articlePageInfo.get( j );

                                capInfoBean = new ContentAssistantPageInfo();

                                // 注意不可设置staticUrl
                                capInfoBean.setContentId( Long.valueOf( us.getKey() ) );
                                capInfoBean.setPageTitle( ( String ) pageInfo.get( "pageTitle" ) );
                                capInfoBean
                                    .setPageContent( ( String ) pageInfo.get( "pageContent" ) );
                                capInfoBean.setStartPos( ( Integer ) pageInfo.get( "startPos" ) );
                                capInfoBean.setEndPos( ( Integer ) pageInfo.get( "endPos" ) );
                                capInfoBean.setPos( ( Integer ) pageInfo.get( "pos" ) );

                                contentDao.saveArticleContentPageInfo( capInfoBean );
                            }

                        }

                        // 更新辅助信息
                        // contentDao
                        // .deleteContentAssistantCopyInfoByContentId( contentId
                        // );

                        if( shareMode )
                        {

                            contentDao.deleteShareContentInfo( currSite.getSiteId(), contentId );
                        }
                        else
                        {
                            Set currentRefClassSet = new HashSet( contentDao
                                .queryContentAssistantCopyResClassIdByContentId( contentId ) );

                            Long copyClassid = null;
                            for ( int k = 0; k < copySuccessIdList.size(); k++ )
                            {
                                copyClassid = ( Long ) copySuccessIdList.get( k );
                                if( !currentRefClassSet.contains( copyClassid ) )
                                {
                                    contentDao
                                        .saveContentAssistantCopyInfo( contentId, copyClassid );
                                }
                            }
                        }

                        // 更新索引信息
                        // 自定义模型支持搜索字段,需要将模型信息加入索引准备
                        SearchIndexContentState searchIndexState = new SearchIndexContentState();

                        searchIndexState.setClassId( classBean.getClassId() );
                        searchIndexState.setContentId( Long.valueOf( us.getKey() ) );

                        searchIndexState.setCensor( endCensor );
                        searchIndexState.setBoost( copyMainInfo.getBoost() );
                        searchIndexState.setIndexDate( new Timestamp( DateAndTimeUtil
                            .clusterTimeMillis() ) );
                        searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_EDIT );

                        searchIndexState.setModelId( classBean.getContentType() );
                        searchIndexState
                            .setSiteId( ( ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                                .getEntry( classBean.getSiteFlag() ) ).getSiteId() );

                        searchService.addIndexContentState( searchIndexState );
                    }

                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            ContentDao.releaseAllCountCache();
            releaseContentCache();
        }

        log.info( "[service] copyContentToSiteClass ...over..." );

        return buf.toString();
    }
    
    /**
     * 创建当前编辑内容的版本镜像
     * 
     * @param contentId
     * @param classIdList
     */
    public Long createVersionContent( Long contentId )
    {
        log.info( "[service] createVersionContent : contentId=" + contentId );

        if( contentId == null || contentId.longValue() < 1 )
        {
            return -1l;
        }

        Map mainInfo = contentDao.querySingleContentMainInfo( contentId );

        if( mainInfo.isEmpty() )
        {
            return -1l;
        }

        SiteGroupBean targetSite = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( mainInfo.get( "siteId" ) );

        if( mainInfo.get( "linkCid" ) != null
            && ( ( Long ) mainInfo.get( "linkCid" ) ).longValue() > 0 )
        {
            return -1l;
        }

        DataModelBean modelBean = metaDataDao.querySingleDataModelBeanById( ( Long ) mainInfo
            .get( "modelId" ) );

        if( modelBean == null )
        {
            return -1l;
        }

        long verClassId = -2;// 代表版本栏目

        ContentClassBean classBean = channelDao
            .querySingleClassBeanInfoByClassId( ( Long ) mainInfo.get( "classId" ) );

        classBean.setClassId( verClassId );

        // 当前时间日期
        String day = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        // 复制主表信息

        ContentMainInfo copyMainInfo = changeDbMapToNewCopyContentMainVo( mainInfo, classBean );

        copyMainInfo.setSiteId( targetSite.getSiteId() );

        // 去掉内容特征标志
        copyMainInfo.setCommendFlag( Constant.COMMON.OFF );
        copyMainInfo.setTopFlag( Constant.COMMON.OFF );
        copyMainInfo.setTitle( copyMainInfo.getTitle() );

        UpdateState us = contentDao.saveContentMainInfo( copyMainInfo );

        // 插入主数据成功
        if( us.haveKey() )
        {

            // 镜像版本内容默认为稿件状态
            contentDao.updateContentMainInfoCensorStatus( Long.valueOf( us.getKey() ),
                Constant.WORKFLOW.CENSOR_STATUS_DRAFT );

            ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
                .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() );

            // 获取副表信息
            Map info = contentDao.querySingleUserDefineContentOnlyModelDataResultNotDisposeInfo(
                sqlCodeBean, modelBean.getRelateTableName(), contentId );

            // 副表文件字段
            List allFiledList = metaDataDao.queryUserDefinedModelFiledInfoBeanList( modelBean
                .getDataModelId() );

            ModelFiledInfoBean fBean = null;

            for ( int ai = 0; ai < allFiledList.size(); ai++ )
            {
                fBean = ( ModelFiledInfoBean ) allFiledList.get( ai );

                if( Constant.METADATA.UPLOAD_IMG == fBean.getHtmlElementId().intValue() )
                {
                    // 附件copy
                    // 复制所有图片物理信息
                    String resInfo = ServiceUtil.copyImageRes( ( String ) info
                        .get( Constant.METADATA.PREFIX_COLUMN_NAME + fBean.getFieldSign() ),
                        targetSite, day, Long.valueOf( verClassId ) );

                    info.put( Constant.METADATA.PREFIX_COLUMN_NAME + fBean.getFieldSign(), resInfo );

                }
                else if( Constant.METADATA.UPLOAD_MEDIA == fBean.getHtmlElementId().intValue() )
                {
                    // 附件copy
                    // 复制所有附件物理信息
                    String resInfo = ServiceUtil.copyMediaRes( ( String ) info
                        .get( Constant.METADATA.PREFIX_COLUMN_NAME + fBean.getFieldSign() ),
                        targetSite, day, Long.valueOf( verClassId ) );

                    info.put( Constant.METADATA.PREFIX_COLUMN_NAME + fBean.getFieldSign(), resInfo );

                }
                else if( Constant.METADATA.UPLOAD_FILE == fBean.getHtmlElementId().intValue() )
                {
                    // 附件copy
                    // 复制所有附件物理信息
                    String resInfo = ServiceUtil.copyFileRes( ( String ) info
                        .get( Constant.METADATA.PREFIX_COLUMN_NAME + fBean.getFieldSign() ),
                        targetSite, day, Long.valueOf( verClassId ) );

                    info.put( Constant.METADATA.PREFIX_COLUMN_NAME + fBean.getFieldSign(), resInfo );

                }
                else if( Constant.METADATA.UPLOAD_IMG_GROUP == fBean.getHtmlElementId().intValue() )
                {
                    // 附件copy
                    // 复制所有图集物理信息
                    List groupPhotoList = contentDao
                        .queryGroupPhotoInfoByContentIdAndGroupSignModelDataMode( contentId, fBean
                            .getFieldSign(), Constant.METADATA.MODEL_TYPE_CONTENT );

                    PhotoGroupInfo newPgi = null;
                    Map pgiInfo = null;
                    Integer isCover = null;
                    String cover = "";

                    for ( int j = 0; j < groupPhotoList.size(); j++ )
                    {
                        pgiInfo = ( Map ) groupPhotoList.get( j );

                        // 复制所有图片物理信息
                        String resInfo = ServiceUtil.copyImageRes( ( String ) pgiInfo.get( "url" ),
                            targetSite, day, Long.valueOf( verClassId ) );

                        newPgi = new PhotoGroupInfo();

                        newPgi.setContentId( Long.valueOf( us.getKey() ) );

                        isCover = ( Integer ) pgiInfo.get( "isCover" );

                        newPgi.setIsCover( isCover );

                        if( isCover != null && Constant.COMMON.ON.equals( isCover ) )
                        {

                            if( resInfo != null )
                            {
                                cover = StringUtil.subString( resInfo,
                                    resInfo.indexOf( "reUrl=" ) + 6, resInfo.indexOf( ";", resInfo
                                        .indexOf( "reUrl=" ) + 6 ) );
                            }
                        }

                        newPgi.setGroupSign( ( String ) pgiInfo.get( "groupSign" ) );
                        newPgi.setOrderFlag( ( Integer ) pgiInfo.get( "orderFlag" ) );
                        newPgi.setOutLinkUrl( ( String ) pgiInfo.get( "outLinkUrl" ) );
                        newPgi.setPhotoAddTime( ( Timestamp ) pgiInfo.get( "photoAddTime" ) );
                        newPgi.setPhotoDesc( ( String ) pgiInfo.get( "photoDesc" ) );
                        newPgi.setPhotoName( ( String ) pgiInfo.get( "photoName" ) );
                        newPgi.setNeedMark( ( Integer ) pgiInfo.get( "needMark" ) );
                        newPgi.setUrl( resInfo );

                        newPgi.setModelType( Constant.METADATA.MODEL_TYPE_CONTENT );

                        contentDao.saveSingleGroupPhoto( newPgi );

                    }

                    info.put( Constant.METADATA.PREFIX_COLUMN_NAME + fBean.getFieldSign(), "count="
                        + groupPhotoList.size() + ";gid=" + fBean.getFieldSign() + ";reUrl="
                        + cover + ";" );

                }
                else if( Constant.METADATA.EDITER == fBean.getHtmlElementId().intValue() )
                {
                    // 编辑器资源深度copy
                    String content = ( String ) info.get( Constant.METADATA.PREFIX_COLUMN_NAME
                        + fBean.getFieldSign() );

                    Document doc = Jsoup.parse( content != null ? content : "" );

                    Element ele = doc.body();

                    Elements eles = ele.getAllElements();

                    Iterator iter = eles.iterator();

                    Element objEle;

                    String targetId = null;

                    String endContent = content;

                    while ( iter.hasNext() )
                    {
                        objEle = ( Element ) iter.next();

                        targetId = objEle.attr( "id" );

                        if( StringUtil.isStringNotNull( targetId ) )
                        {
                            endContent = copyEditorRes( endContent, objEle, targetId, targetSite,
                                day, verClassId );
                        }
                    }

                    info.put( Constant.METADATA.PREFIX_COLUMN_NAME + fBean.getFieldSign(),
                        endContent );

                }
            }

            info.put( "contentId", Long.valueOf( us.getKey() ) );

            // 存储拷贝内容副表信息
            contentDao.saveOrUpdateModelContent( sqlCodeBean.getInsertSql(), info );

            // 若是文章和栏目类型,要复制辅助信息
            if( Constant.METADATA.MODEL_RES_ARTICLE.equals( modelBean.getModelResType() ) )
            {
                // 分页信息
                List articlePageInfo = contentDao
                    .queryContentAssistantPageInfoBeanByContentIdDataMode( contentId, info,
                        classBean );

                Map pageInfo = null;
                ContentAssistantPageInfo capInfoBean = null;
                for ( int j = 0; j < articlePageInfo.size(); j++ )
                {
                    pageInfo = ( Map ) articlePageInfo.get( j );

                    capInfoBean = new ContentAssistantPageInfo();

                    // 注意不可设置staticUrl
                    capInfoBean.setContentId( Long.valueOf( us.getKey() ) );
                    capInfoBean.setPageTitle( ( String ) pageInfo.get( "pageTitle" ) );
                    capInfoBean.setPageContent( ( String ) pageInfo.get( "pageContent" ) );
                    capInfoBean.setStartPos( ( Integer ) pageInfo.get( "startPos" ) );
                    capInfoBean.setEndPos( ( Integer ) pageInfo.get( "endPos" ) );
                    capInfoBean.setPos( ( Integer ) pageInfo.get( "pos" ) );

                    contentDao.saveArticleContentPageInfo( capInfoBean );
                }

            }

        }

        return Long.valueOf( us.getKey() );

    }

    /**
     * 引用内容到指定栏目,返回立即发布的id字符
     * 
     * @param contentId
     * @param classIdList
     */
    public String linkContentToSiteClass( List contentIdList, List classIdList )
    {
        log.info( "[service] linkContentToSiteClass : contentIdList=" + contentIdList
            + ", classIdList:" + classIdList );

        if( classIdList == null || classIdList.isEmpty() )
        {
            return "";
        }

        ContentClassBean testClassBean = channelService
            .retrieveSingleClassBeanInfoByClassId( StringUtil.getLongValue( ( String ) classIdList
                .get( 0 ), -1 ) );

        SiteGroupBean targetSite = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( testClassBean.getSiteFlag() );

        StringBuffer buf = new StringBuffer( "" );
        
        List<ContentMainInfo> succLCOBean = new ArrayList<ContentMainInfo>();

        List<ContentClassBean> succCCBean = new ArrayList<ContentClassBean>();

        try
        {
            mysqlEngine.beginTransaction();

            Long contentId = null;
            for ( int x = contentIdList.size() - 1; x >= 0; x-- )
            {
                if( contentIdList.get( x ) instanceof Long )
                {
                    contentId = ( Long ) contentIdList.get( x );
                }
                else
                {
                    contentId = Long.valueOf( StringUtil.getLongValue( ( String ) contentIdList
                        .get( x ), -1 ) );
                }

                if( contentId == null || contentId.longValue() < 1 )
                {
                    continue;
                }

                Map mainInfo = contentDao.querySingleContentMainInfo( contentId );

                if( mainInfo.isEmpty() )
                {
                    continue;
                }

                if( mainInfo.get( "linkCid" ) != null
                    && ( ( Long ) mainInfo.get( "linkCid" ) ).longValue() > 0 )
                {
                    continue;
                }

                DataModelBean modelBean = metaDataDao
                    .querySingleDataModelBeanById( ( Long ) mainInfo.get( "modelId" ) );

                if( modelBean == null )
                {
                    continue;
                }

                long classId = -1;
                ContentClassBean classBean = null;
                List copySuccessIdList = new ArrayList();

                for ( int i = 0; i < classIdList.size(); i++ )
                {
                    classId = StringUtil.getLongValue( ( String ) classIdList.get( i ), -1 );

                    if( classId < 0 )
                    {
                        continue;
                    }

                    classBean = channelService.retrieveSingleClassBeanInfoByClassId( Long
                        .valueOf( classId ) );

                    // 判断classId数据模型是否和目标内容模型一致
                    if( !classBean.getContentType().equals( modelBean.getDataModelId() ) )
                    {
                        continue;
                    }

                    // 将由站点配置决定 判断标题是否重复,若重复不可引用

                    if( Constant.COMMON.OFF.equals( targetSite.getSameTitle() ) )
                    {
                        boolean titleExist = contentDao.checkContentTitle( ( String ) mainInfo
                            .get( "title" ), classBean.getClassId() );

                        if( titleExist )
                        {
                            continue;
                        }
                    }

                    // 当前时间日期
                    String day = DateAndTimeUtil
                        .getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

                    // 加入合法的classId
                    copySuccessIdList.add( Long.valueOf( classId ) );

                    // 复制主表信息

                    ContentMainInfo copyMainInfo = changeDbMapToNewCopyContentMainVo( mainInfo,
                        classBean );

                    copyMainInfo.setSiteId( targetSite.getSiteId() );

                    // 去掉内容特征标志
                    copyMainInfo.setCommendFlag( Constant.COMMON.OFF );
                    copyMainInfo.setTopFlag( Constant.COMMON.OFF );
                    copyMainInfo.setLinkCid( contentId );

                    UpdateState us = contentDao.saveContentMainInfo( copyMainInfo );

                    // 插入主数据成功
                    if( us.haveKey() )
                    {
                        // // 增加内容快速更新状态表,不再使用,保留记录
                        // ContentStatus cs = new ContentStatus();
                        //
                        // cs.setSelfContentId( Long.valueOf( us.getKey() ) );
                        //
                        // contentDao.saveContentStatus( cs );

                        // trace信息
                        contentDao
                            .saveContentLinkInfoTrace( contentId, Long.valueOf( us.getKey() ) );

                        // 更新排序ID
                        contentDao.updateSystemContentOrderIdFlag( Double.valueOf( us.getKey() ),
                            Long.valueOf( us.getKey() ) );

                        // 复制内容业务工作流处理,当目标栏目有工作流时,必须转为稿件状态,等待发布审核流程,若无工作流,更新发布状态
                        Integer endCensor = null;

                        // if( classBean.getWorkflowId().longValue() > 0 )
                        // {
                        // // 进入稿件状态
                        // contentDao.updateContentMainInfoCensorStatus( Long
                        // .valueOf( us.getKey() ),
                        // Constant.WORKFLOW.CENSOR_STATUS_DRAFT );
                        //
                        // endCensor = Constant.WORKFLOW.CENSOR_STATUS_DRAFT;
                        // }
                        // else
                        {
                            if( Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW.equals( mainInfo
                                .get( "censorState" ) )
                                || Constant.WORKFLOW.CENSOR_STATUS_DRAFT.equals( mainInfo
                                    .get( "censorState" ) ) )
                            {
                                contentDao.updateContentMainInfoCensorStatus( Long.valueOf( us
                                    .getKey() ), ( Integer ) mainInfo.get( "censorState" ) );
                            }
                            else
                            {
                                // 默认发布状态
                                endCensor = WorkflowService
                                    .pendingCensorStateByStartAndEndPublishDate( new Timestamp(
                                        ( ( Timestamp ) mainInfo.get( "appearStartDateTime" ) )
                                            .getTime() ), new Timestamp( ( ( Timestamp ) mainInfo
                                        .get( "appearEndDateTime" ) ).getTime() ),
                                        Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );

                                contentDao.updateContentMainInfoCensorStatus( Long.valueOf( us
                                    .getKey() ), endCensor );

                                // 记录发布成功的ID
                                buf.append( Long.valueOf( us.getKey() ) + "," );

                                // 发布状态,需要更新排序ID
                                contentDao.updateSystemPublishIdFlag( getNextPublishOrderTrace(),
                                    Long.valueOf( us.getKey() ) );
                            }
                        }

                        ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
                            .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() );

                        // 获取副表信息
                        Map info = contentDao
                            .querySingleUserDefineContentOnlyModelDataResultNotDisposeInfo(
                                sqlCodeBean, modelBean.getRelateTableName(), contentId );

                        // 副表文件字段
                        List allFiledList = metaDataDao
                            .queryUserDefinedModelFiledInfoBeanList( modelBean.getDataModelId() );

                        ModelFiledInfoBean fBean = null;

                        for ( int ai = 0; ai < allFiledList.size(); ai++ )
                        {
                            fBean = ( ModelFiledInfoBean ) allFiledList.get( ai );

                            if( Constant.METADATA.UPLOAD_IMG == fBean.getHtmlElementId().intValue() )
                            {
                                // 附件copy
                                // 复制所有图片物理信息
                                String resInfo = ServiceUtil.copyImageRes( ( String ) info
                                    .get( Constant.METADATA.PREFIX_COLUMN_NAME
                                        + fBean.getFieldSign() ), targetSite, day, Long
                                    .valueOf( classId ) );

                                info.put( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign(), resInfo );

                            }
                            else if( Constant.METADATA.UPLOAD_MEDIA == fBean.getHtmlElementId()
                                .intValue() )
                            {
                                // 附件copy
                                // 复制所有附件物理信息
                                String resInfo = ServiceUtil.copyMediaRes( ( String ) info
                                    .get( Constant.METADATA.PREFIX_COLUMN_NAME
                                        + fBean.getFieldSign() ), targetSite, day, Long
                                    .valueOf( classId ) );

                                info.put( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign(), resInfo );

                            }
                            else if( Constant.METADATA.UPLOAD_FILE == fBean.getHtmlElementId()
                                .intValue() )
                            {
                                // 附件copy
                                // 复制所有附件物理信息
                                String resInfo = ServiceUtil.copyFileRes( ( String ) info
                                    .get( Constant.METADATA.PREFIX_COLUMN_NAME
                                        + fBean.getFieldSign() ), targetSite, day, Long
                                    .valueOf( classId ) );

                                info.put( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign(), resInfo );

                            }
                            else if( Constant.METADATA.UPLOAD_IMG_GROUP == fBean.getHtmlElementId()
                                .intValue() )
                            {
                                // 附件copy
                                // 复制所有图集物理信息
                                List groupPhotoList = contentDao
                                    .queryGroupPhotoInfoByContentIdAndGroupSignModelDataMode(
                                        contentId, fBean.getFieldSign(),
                                        Constant.METADATA.MODEL_TYPE_CONTENT );

                                PhotoGroupInfo newPgi = null;
                                Map pgiInfo = null;
                                Integer isCover = null;
                                String cover = "";

                                for ( int j = 0; j < groupPhotoList.size(); j++ )
                                {
                                    pgiInfo = ( Map ) groupPhotoList.get( j );

                                    // 复制所有图片物理信息
                                    String resInfo = ServiceUtil.copyImageRes( ( String ) pgiInfo
                                        .get( "url" ), targetSite, day, Long.valueOf( classId ) );

                                    newPgi = new PhotoGroupInfo();

                                    newPgi.setContentId( Long.valueOf( us.getKey() ) );

                                    isCover = ( Integer ) pgiInfo.get( "isCover" );

                                    newPgi.setIsCover( isCover );

                                    if( isCover != null && Constant.COMMON.ON.equals( isCover ) )
                                    {

                                        if( resInfo != null )
                                        {
                                            cover = StringUtil.subString( resInfo, resInfo
                                                .indexOf( "reUrl=" ) + 6, resInfo.indexOf( ";",
                                                resInfo.indexOf( "reUrl=" ) + 6 ) );
                                        }
                                    }

                                    newPgi.setGroupSign( ( String ) pgiInfo.get( "groupSign" ) );
                                    newPgi.setOrderFlag( ( Integer ) pgiInfo.get( "orderFlag" ) );
                                    newPgi.setOutLinkUrl( ( String ) pgiInfo.get( "outLinkUrl" ) );
                                    newPgi.setPhotoAddTime( ( Timestamp ) pgiInfo
                                        .get( "photoAddTime" ) );
                                    newPgi.setPhotoDesc( ( String ) pgiInfo.get( "photoDesc" ) );
                                    newPgi.setPhotoName( ( String ) pgiInfo.get( "photoName" ) );
                                    newPgi.setNeedMark( ( Integer ) pgiInfo.get( "needMark" ) );
                                    newPgi.setUrl( resInfo );

                                    newPgi.setModelType( Constant.METADATA.MODEL_TYPE_CONTENT );

                                    contentDao.saveSingleGroupPhoto( newPgi );

                                }

                                info.put( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign(), "count=" + groupPhotoList.size()
                                    + ";gid=" + fBean.getFieldSign() + ";reUrl=" + cover + ";" );

                            }
                            else if( Constant.METADATA.EDITER == fBean.getHtmlElementId()
                                .intValue() )
                            {
                                // 编辑器资源深度copy
                                String content = ( String ) info
                                    .get( Constant.METADATA.PREFIX_COLUMN_NAME
                                        + fBean.getFieldSign() );

                                Document doc = Jsoup.parse( content != null ? content : "" );

                                Element ele = doc.body();

                                Elements eles = ele.getAllElements();

                                Iterator iter = eles.iterator();

                                Element objEle;

                                String targetId = null;

                                String endContent = content;

                                while ( iter.hasNext() )
                                {
                                    objEle = ( Element ) iter.next();

                                    targetId = objEle.attr( "id" );

                                    if( StringUtil.isStringNotNull( targetId ) )
                                    {
                                        endContent = copyEditorRes( endContent, objEle, targetId,
                                            targetSite, day, classId );
                                    }
                                }

                                info.put( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign(), endContent );

                            }
                        }

                        info.put( "contentId", Long.valueOf( us.getKey() ) );

                        // 存储拷贝内容副表信息
                        contentDao.saveOrUpdateModelContent( sqlCodeBean.getInsertSql(), info );

                        // 若是文章和栏目类型,要复制辅助信息
                        if( Constant.METADATA.MODEL_RES_ARTICLE
                            .equals( modelBean.getModelResType() ) )
                        {
                            // 分页信息
                            List articlePageInfo = contentDao
                                .queryContentAssistantPageInfoBeanByContentIdDataMode( contentId,
                                    info, classBean );

                            Map pageInfo = null;
                            ContentAssistantPageInfo capInfoBean = null;
                            for ( int j = 0; j < articlePageInfo.size(); j++ )
                            {
                                pageInfo = ( Map ) articlePageInfo.get( j );

                                capInfoBean = new ContentAssistantPageInfo();

                                // 注意不可设置staticUrl
                                capInfoBean.setContentId( Long.valueOf( us.getKey() ) );
                                capInfoBean.setPageTitle( ( String ) pageInfo.get( "pageTitle" ) );
                                capInfoBean
                                    .setPageContent( ( String ) pageInfo.get( "pageContent" ) );
                                capInfoBean.setStartPos( ( Integer ) pageInfo.get( "startPos" ) );
                                capInfoBean.setEndPos( ( Integer ) pageInfo.get( "endPos" ) );
                                capInfoBean.setPos( ( Integer ) pageInfo.get( "pos" ) );

                                contentDao.saveArticleContentPageInfo( capInfoBean );
                            }

                        }

                        // 更新辅助信息
                        // contentDao
                        // .deleteContentAssistantCopyInfoByContentId( contentId
                        // );

                        /*
                         * if( shareMode ) {
                         * 
                         * contentDao.deleteShareContentInfo( currSite
                         * .getSiteId(), contentId ); } else
                         */
                        // {
                        // Set currentRefClassSet = new HashSet(
                        // contentDao
                        // .queryContentAssistantCopyResClassIdByContentId(
                        // contentId ) );
                        //
                        // Long copyClassid = null;
                        // for ( int k = 0; k < copySuccessIdList.size(); k++ )
                        // {
                        // copyClassid = ( Long ) copySuccessIdList
                        // .get( k );
                        // if( !currentRefClassSet.contains( copyClassid ) )
                        // {
                        // contentDao.saveContentAssistantCopyInfo(
                        // contentId, copyClassid );
                        // }
                        // }
                        // }
                        // 更新索引信息
                        // 自定义模型支持搜索字段,需要将模型信息加入索引准备
                        SearchIndexContentState searchIndexState = new SearchIndexContentState();

                        searchIndexState.setClassId( classBean.getClassId() );
                        searchIndexState.setContentId( Long.valueOf( us.getKey() ) );

                        searchIndexState.setCensor( endCensor );
                        searchIndexState.setBoost( ( Float ) mainInfo.get( "boost" ) );
                        searchIndexState.setIndexDate( new Timestamp( DateAndTimeUtil
                            .clusterTimeMillis() ) );
                        searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_EDIT );

                        searchIndexState.setModelId( classBean.getContentType() );
                        searchIndexState
                            .setSiteId( ( ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                                .getEntry( classBean.getSiteFlag() ) ).getSiteId() );

                        searchService.addIndexContentState( searchIndexState );
                    }

                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            ContentDao.releaseAllCountCache();
            releaseContentCache();
        }
        
        // 发布内容
        for ( ContentMainInfo mainInfo : succLCOBean )
        {
            String cmsPath = JtRuntime.cmsServer.getDomainFullPath();

            // 首先要向权限系统注册本次访问，下面的url需要带入本次生成的key

            String uuidKey = StringUtil.getUUIDString();

            AuthorizationHandler.setInnerAccessFlag( uuidKey );

            String url = cmsPath
                + "publish/generateContent.do?staticType=2&thread=true&job=true&innerAccessJtopSysFlag="
                + uuidKey + "&siteId=" + mainInfo.getSiteId() + "&someContentId="
                + mainInfo.getContentId();

            try
            {
                ServiceUtil.doGETMethodRequest( url );
            }
            finally
            {
                // AuthorizationHandler.romoveInnerAccessFlag( uuidKey );
            }
        }
        
        //发布栏目
        for ( ContentClassBean cc : succCCBean )
        {
            String cmsPath = JtRuntime.cmsServer.getDomainFullPath();

            // 首先要向权限系统注册本次访问，下面的url需要带入本次生成的key

            String uuidKey = StringUtil.getUUIDString();

            AuthorizationHandler.setInnerAccessFlag( uuidKey );
            
            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache.getEntry( cc.getSiteFlag() );

            String  url = cmsPath
                + "publish/generateContent.do?staticType=1&thread=true&job=true&innerAccessJtopSysFlag="
                + uuidKey + "&siteId=" +site.getSiteId() + "&tcId=" + cc.getClassId();

            ServiceUtil.doGETMethodRequest( url );
        }

        log.info( "[service] linkContentToSiteClass ...over..." );

        return buf.toString();
    }

    /**
     * 重新同步引用内容
     * 
     * @param contentId
     * @param classIdList
     */
    public String linkContentEditMode( Long contentId )
    {
        log.info( "[service] linkContentToSiteClass : contentId=" + contentId );

        if( contentId == null )
        {
            return "";
        }

        StringBuffer buf = new StringBuffer( "" );
        try
        {
            mysqlEngine.beginTransaction();

            if( contentId == null || contentId.longValue() < 1 )
            {
                return "";
            }

            Map mainInfo = contentDao.querySingleContentMainInfo( contentId );

            if( mainInfo.isEmpty() )
            {
                return "";
            }

            DataModelBean modelBean = metaDataDao.querySingleDataModelBeanById( ( Long ) mainInfo
                .get( "modelId" ) );

            if( modelBean == null )
            {
                return "";
            }

            List linkcIdList = contentDao.queryContentLinkInfoTrace( contentId );

            if( linkcIdList.isEmpty() )
            {
                return "";
            }

            long linkId = -1;
            ContentClassBean classBean = null;

            ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
                .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() );

            Map info = contentDao.querySingleUserDefineContentOnlyModelDataResultNotDisposeInfo(
                sqlCodeBean, modelBean.getRelateTableName(), contentId );

            for ( int i = 0; i < linkcIdList.size(); i++ )
            {
                linkId = ( Long ) linkcIdList.get( i );

                if( linkId < 0 )
                {
                    continue;
                }

                Map linkMainInfo = contentDao.querySingleContentMainInfo( linkId );

                if( linkMainInfo.isEmpty() )
                {
                    continue;
                }

                classBean = channelService
                    .retrieveSingleClassBeanInfoByClassId( ( Long ) linkMainInfo.get( "classId" ) );

                SiteGroupBean targetSite = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                    .getEntry( classBean.getSiteFlag() );

                Long classId = classBean.getClassId();

                // 当前时间日期
                String day = DateAndTimeUtil
                    .getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

                // 复制主表信息

                ContentMainInfo copyMainInfo = changeDbMapToNewCopyContentMainVo( mainInfo,
                    classBean );

                copyMainInfo.setTypeFlag( ( String ) mainInfo.get( "typeFlag" ) );

                // 内容特征标志
                copyMainInfo.setCommendFlag( ( Integer ) linkMainInfo.get( "commendFlag" ) );
                copyMainInfo.setTopFlag( ( Integer ) linkMainInfo.get( "topFlag" ) );

                // 主信息
                copyMainInfo.setContentId( ( Long ) linkMainInfo.get( "contentId" ) );

                contentDao.updateContentMainInfo( copyMainInfo );

                // 插入主数据成功
                // if( us.haveKey() )
                {
                    // // 增加内容快速更新状态表,不再使用,保留记录
                    // ContentStatus cs = new ContentStatus();
                    //
                    // cs.setSelfContentId( Long.valueOf( us.getKey() ) );
                    //
                    // contentDao.saveContentStatus( cs );

                    // 更新排序ID
                    // contentDao
                    // .updateSystemContentOrderIdFlag( Double.valueOf( us
                    // .getKey() ), Long.valueOf( us.getKey() ) );

                    // 复制内容业务工作流处理,当目标栏目有工作流时,必须转为稿件状态,等待发布审核流程,若无工作流,更新发布状态
                    Integer endCensor = null;

                    // if( classBean.getWorkflowId().longValue() > 0 )
                    // {
                    // // 进入稿件状态
                    // contentDao.updateContentMainInfoCensorStatus( Long
                    // .valueOf( linkId ),
                    // Constant.WORKFLOW.CENSOR_STATUS_DRAFT );
                    //
                    // endCensor = Constant.WORKFLOW.CENSOR_STATUS_DRAFT;
                    // }
                    // else
                    {

                        if( Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW.equals( mainInfo
                            .get( "censorState" ) )
                            || Constant.WORKFLOW.CENSOR_STATUS_DRAFT.equals( mainInfo
                                .get( "censorState" ) ) )
                        {
                            contentDao.updateContentMainInfoCensorStatus( linkId,
                                ( Integer ) mainInfo.get( "censorState" ) );
                        }
                        else
                        {
                            // 默认发布状态
                            endCensor = WorkflowService.pendingCensorStateByStartAndEndPublishDate(
                                new Timestamp(
                                    ( ( Timestamp ) mainInfo.get( "appearStartDateTime" ) )
                                        .getTime() ), new Timestamp( ( ( Timestamp ) mainInfo
                                    .get( "appearEndDateTime" ) ).getTime() ),
                                Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );

                            contentDao.updateContentMainInfoCensorStatus( linkId, endCensor );

                            // 记录发布成功的ID
                            buf.append( linkId + "," );

                            // 发布状态,需要更新排序ID
                            contentDao.updateSystemPublishIdFlag( getNextPublishOrderTrace(), Long
                                .valueOf( linkId ) );
                        }
                    }

                    // 获取副表信息

                    Map linkInfo = contentDao
                        .querySingleUserDefineContentOnlyModelDataResultNotDisposeInfo(
                            sqlCodeBean, modelBean.getRelateTableName(), ( Long ) linkMainInfo
                                .get( "contentId" ) );

                    // 副表文件字段
                    List allFiledList = metaDataDao
                        .queryUserDefinedModelFiledInfoBeanList( modelBean.getDataModelId() );

                    ModelFiledInfoBean fBean = null;

                    for ( int ai = 0; ai < allFiledList.size(); ai++ )
                    {
                        fBean = ( ModelFiledInfoBean ) allFiledList.get( ai );

                        if( Constant.METADATA.UPLOAD_IMG == fBean.getHtmlElementId().intValue() )
                        {
                            // 处理原图片
                            resService.updateSiteResourceTraceUseStatus(
                                ( String ) linkInfo.get( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign() ), Constant.COMMON.OFF );

                            // 附件copy
                            // 复制所有图片物理信息
                            String resInfo = ServiceUtil.copyImageRes(
                                ( String ) info.get( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign() ), targetSite, day, Long
                                    .valueOf( classId ) );

                            info.put( Constant.METADATA.PREFIX_COLUMN_NAME + fBean.getFieldSign(),
                                resInfo );

                        }
                        else if( Constant.METADATA.UPLOAD_MEDIA == fBean.getHtmlElementId()
                            .intValue() )
                        {
                            // 处理原视频
                            Long resId = ServiceUtil
                                .getResId( ( String ) linkInfo
                                    .get( Constant.METADATA.PREFIX_COLUMN_NAME
                                        + fBean.getFieldSign() ) );

                            if( resId.longValue() > 0 )
                            {
                                SiteResourceBean oldResBean = resService
                                    .retrieveSingleResourceBeanByResId( resId );

                                if( oldResBean != null )
                                {
                                    resService.updateSiteResourceTraceUseStatus( oldResBean
                                        .getResId(), Constant.COMMON.OFF );

                                    String oldCover = StringUtil.isStringNull( oldResBean
                                        .getCover() ) ? "" : oldResBean.getCover();

                                    if( StringUtil.isStringNotNull( oldResBean.getCover() ) )
                                    {
                                        SiteResourceBean coverResBean = resService
                                            .retrieveSingleResourceBeanBySource( oldCover );

                                        if( coverResBean != null )
                                        {
                                            resService.updateSiteResourceTraceUseStatus(
                                                coverResBean.getResId(), Constant.COMMON.OFF );
                                        }
                                    }
                                }
                            }

                            // 附件copy
                            // 复制所有附件物理信息
                            String resInfo = ServiceUtil.copyMediaRes(
                                ( String ) info.get( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign() ), targetSite, day, Long
                                    .valueOf( classId ) );

                            info.put( Constant.METADATA.PREFIX_COLUMN_NAME + fBean.getFieldSign(),
                                resInfo );

                        }
                        else if( Constant.METADATA.UPLOAD_FILE == fBean.getHtmlElementId()
                            .intValue() )
                        {
                            // 处理原文件
                            resService.updateSiteResourceTraceUseStatus(
                                ( String ) linkInfo.get( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign() ), Constant.COMMON.OFF );

                            // 附件copy
                            // 复制所有附件物理信息
                            String resInfo = ServiceUtil.copyFileRes(
                                ( String ) info.get( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign() ), targetSite, day, Long
                                    .valueOf( classId ) );

                            info.put( Constant.METADATA.PREFIX_COLUMN_NAME + fBean.getFieldSign(),
                                resInfo );

                        }
                        else if( Constant.METADATA.UPLOAD_IMG_GROUP == fBean.getHtmlElementId()
                            .intValue() )
                        {
                            // 处理原始图集信息
                            List imageGroupList = contentDao.queryGroupPhotoInfoByContentId( Long
                                .valueOf( linkId ), Constant.METADATA.MODEL_TYPE_CONTENT, null,
                                true );

                            Map imageInfo = null;

                            for ( int k = 0; k < imageGroupList.size(); k++ )
                            {
                                imageInfo = ( Map ) imageGroupList.get( k );

                                SiteResourceBean resBean = resService
                                    .retrieveSingleResourceBeanByResId( Long.valueOf( StringUtil
                                        .getLongValue( ( String ) imageInfo.get( "resId" ), -1 ) ) );

                                if( resBean != null )
                                {
                                    // 更新文件使用状态
                                    resService.updateSiteResourceTraceUseStatus(
                                        resBean.getResId(), Constant.COMMON.OFF );
                                }

                            }

                            contentDao.deletePhotoGroupInfo( Long.valueOf( linkId ),
                                Constant.METADATA.MODEL_TYPE_CONTENT );

                            // 附件copy
                            // 复制所有图集物理信息
                            List groupPhotoList = contentDao
                                .queryGroupPhotoInfoByContentIdAndGroupSignModelDataMode(
                                    contentId, fBean.getFieldSign(),
                                    Constant.METADATA.MODEL_TYPE_CONTENT );

                            PhotoGroupInfo newPgi = null;
                            Map pgiInfo = null;
                            Integer isCover = null;
                            String cover = "";

                            for ( int j = 0; j < groupPhotoList.size(); j++ )
                            {
                                pgiInfo = ( Map ) groupPhotoList.get( j );

                                // 复制所有图片物理信息
                                String resInfo = ServiceUtil.copyImageRes( ( String ) pgiInfo
                                    .get( "url" ), targetSite, day, Long.valueOf( classId ) );

                                newPgi = new PhotoGroupInfo();

                                newPgi.setContentId( Long.valueOf( linkId ) );

                                isCover = ( Integer ) pgiInfo.get( "isCover" );

                                newPgi.setIsCover( isCover );

                                if( isCover != null && Constant.COMMON.ON.equals( isCover ) )
                                {

                                    if( resInfo != null )
                                    {
                                        cover = StringUtil.subString( resInfo, resInfo
                                            .indexOf( "reUrl=" ) + 6, resInfo.indexOf( ";", resInfo
                                            .indexOf( "reUrl=" ) + 6 ) );
                                    }
                                }

                                newPgi.setGroupSign( ( String ) pgiInfo.get( "groupSign" ) );
                                newPgi.setOrderFlag( ( Integer ) pgiInfo.get( "orderFlag" ) );
                                newPgi.setOutLinkUrl( ( String ) pgiInfo.get( "outLinkUrl" ) );
                                newPgi
                                    .setPhotoAddTime( ( Timestamp ) pgiInfo.get( "photoAddTime" ) );
                                newPgi.setPhotoDesc( ( String ) pgiInfo.get( "photoDesc" ) );
                                newPgi.setPhotoName( ( String ) pgiInfo.get( "photoName" ) );
                                newPgi.setNeedMark( ( Integer ) pgiInfo.get( "needMark" ) );
                                newPgi.setUrl( resInfo );

                                newPgi.setModelType( Constant.METADATA.MODEL_TYPE_CONTENT );

                                contentDao.saveSingleGroupPhoto( newPgi );

                            }

                            info.put( Constant.METADATA.PREFIX_COLUMN_NAME + fBean.getFieldSign(),
                                "count=" + groupPhotoList.size() + ";gid=" + fBean.getFieldSign()
                                    + ";reUrl=" + cover + ";" );

                        }
                        else if( Constant.METADATA.EDITER == fBean.getHtmlElementId().intValue() )
                        {
                            // 处理原有文本资源

                            ServiceUtil.disposeTextHaveSiteResId( null,
                                ( String ) linkInfo.get( Constant.METADATA.PREFIX_COLUMN_NAME
                                    + fBean.getFieldSign() ), new HashSet(), linkId, true );

                            // 编辑器资源深度copy
                            String content = ( String ) info
                                .get( Constant.METADATA.PREFIX_COLUMN_NAME + fBean.getFieldSign() );

                            Document doc = Jsoup.parse( content );

                            Element ele = doc.body();

                            Elements eles = ele.getAllElements();

                            Iterator iter = eles.iterator();

                            Element objEle;

                            String targetId = null;

                            String endContent = content;

                            while ( iter.hasNext() )
                            {
                                objEle = ( Element ) iter.next();

                                targetId = objEle.attr( "id" );

                                if( StringUtil.isStringNotNull( targetId ) )
                                {
                                    endContent = copyEditorRes( endContent, objEle, targetId,
                                        targetSite, day, classId );
                                }
                            }

                            info.put( Constant.METADATA.PREFIX_COLUMN_NAME + fBean.getFieldSign(),
                                endContent );

                        }
                    }

                    info.put( "contentId", Long.valueOf( linkId ) );

                    // 存储拷贝内容副表信息
                    contentDao.saveOrUpdateModelContent( sqlCodeBean.getUpdateSql(), info );

                    // 若是文章和栏目类型,要复制辅助信息
                    if( Constant.METADATA.MODEL_RES_ARTICLE.equals( modelBean.getModelResType() ) )
                    {
                        // 分页信息
                        List articlePageInfo = contentDao
                            .queryContentAssistantPageInfoBeanByContentIdDataMode( contentId, info,
                                classBean );

                        Map pageInfo = null;
                        ContentAssistantPageInfo capInfoBean = null;
                        for ( int j = 0; j < articlePageInfo.size(); j++ )
                        {
                            pageInfo = ( Map ) articlePageInfo.get( j );

                            capInfoBean = new ContentAssistantPageInfo();

                            // 注意不可设置staticUrl
                            capInfoBean.setContentId( Long.valueOf( linkId ) );
                            capInfoBean.setPageTitle( ( String ) pageInfo.get( "pageTitle" ) );
                            capInfoBean.setPageContent( ( String ) pageInfo.get( "pageContent" ) );
                            capInfoBean.setStartPos( ( Integer ) pageInfo.get( "startPos" ) );
                            capInfoBean.setEndPos( ( Integer ) pageInfo.get( "endPos" ) );
                            capInfoBean.setPos( ( Integer ) pageInfo.get( "pos" ) );

                            contentDao.saveArticleContentPageInfo( capInfoBean );
                        }

                    }

                    // 更新索引信息
                    // 自定义模型支持搜索字段,需要将模型信息加入索引准备
                    SearchIndexContentState searchIndexState = new SearchIndexContentState();

                    searchIndexState.setClassId( classBean.getClassId() );
                    searchIndexState.setContentId( Long.valueOf( linkId ) );

                    searchIndexState.setCensor( endCensor );
                    searchIndexState.setBoost( ( Float ) mainInfo.get( "boost" ) );
                    searchIndexState.setIndexDate( new Timestamp( DateAndTimeUtil
                        .clusterTimeMillis() ) );
                    searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_EDIT );

                    searchIndexState.setModelId( classBean.getContentType() );
                    searchIndexState
                        .setSiteId( ( ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                            .getEntry( classBean.getSiteFlag() ) ).getSiteId() );

                    searchService.addIndexContentState( searchIndexState );
                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            ContentDao.releaseAllCountCache();
            releaseContentCache();
        }

        log.info( "[service] linkContentToSiteClass ...over..." );

        return buf.toString();
    }

    private String copyEditorRes( String content, Element fileEle, String targetId,
        SiteGroupBean targetSite, String day, long classId )
    {
        String fullSrc = null;

        String newContent = content;

        SiteResourceBean res = null;

        if( StringUtil.isStringNotNull( targetId )
            && targetId.startsWith( "jtopcms_content_image_" ) )
        {

            String id = StringUtil.replaceString( targetId, "jtopcms_content_image_", "", false,
                false );

            if( StringUtil.getLongValue( id, -1 ) > 0 )
            {

                res = resourcesDao.querySingleResourceBeanByResId( Long.valueOf( StringUtil
                    .getLongValue( id, -1 ) ) );

                fullSrc = res != null ? res.getUrl() : "";

                String resInfo = ServiceUtil.copyImageRes( "id=" + id + ";", targetSite, day, Long
                    .valueOf( classId ) );

                String newUrl = targetSite.getSiteImagePrefixUrl()
                    + StringUtil.subString( resInfo, resInfo.indexOf( "reUrl=" ) + 6, resInfo
                        .indexOf( ";", resInfo.indexOf( "reUrl=" ) + 6 ) );

                Long newId = Long.valueOf( StringUtil.getLongValue( StringUtil.subString( resInfo,
                    resInfo.indexOf( "id=" ) + 3, resInfo.indexOf( ";",
                        resInfo.indexOf( "id=" ) + 3 ) ), -1 ) );

                newContent = StringUtil.replaceString( content, targetId, "jtopcms_content_image_"
                    + newId, false, false );

                newContent = StringUtil.replaceString( newContent, fullSrc, newUrl, false, false );
            }

        }
        else if( StringUtil.isStringNotNull( targetId )
            && targetId.startsWith( "jtopcms_content_media_" ) )
        {

            String id = StringUtil.replaceString( targetId, "jtopcms_content_media_", "", false,
                false );

            if( StringUtil.getLongValue( id, -1 ) > 0 )
            {

                res = resourcesDao.querySingleResourceBeanByResId( Long.valueOf( StringUtil
                    .getLongValue( id, -1 ) ) );

                fullSrc = res != null ? res.getUrl() : "";

                String resInfo = ServiceUtil.copyMediaRes( "id=" + id + ";", targetSite, day, Long
                    .valueOf( classId ) );

                String newUrl = targetSite.getSiteMediaPrefixUrl()
                    + StringUtil.subString( resInfo, resInfo.indexOf( "reUrl=" ) + 6, resInfo
                        .indexOf( ";", resInfo.indexOf( "reUrl=" ) + 6 ) );

                Long newId = Long.valueOf( StringUtil.getLongValue( StringUtil.subString( resInfo,
                    resInfo.indexOf( "id=" ) + 3, resInfo.indexOf( ";",
                        resInfo.indexOf( "id=" ) + 3 ) ), -1 ) );

                newContent = StringUtil.replaceString( content, targetId, "jtopcms_content_media_"
                    + newId, false, false );

                newContent = StringUtil.replaceString( newContent, fullSrc, newUrl, false, false );
            }

        }
        else if( StringUtil.isStringNotNull( targetId )
            && targetId.startsWith( "jtopcms_content_file_" ) )
        {
            fullSrc = fileEle.attr( "src" );

            String id = StringUtil.replaceString( targetId, "jtopcms_content_file_", "", false,
                false );

            if( StringUtil.getLongValue( id, -1 ) > 0 )
            {
                res = resourcesDao.querySingleResourceBeanByResId( Long.valueOf( StringUtil
                    .getLongValue( id, -1 ) ) );

                fullSrc = res != null ? res.getUrl() : "";

                String resInfo = ServiceUtil.copyFileRes( "id=" + id + ";", targetSite, day, Long
                    .valueOf( classId ) );

                String newUrl = targetSite.getSiteFilePrefixUrl()
                    + StringUtil.subString( resInfo, resInfo.indexOf( "reUrl=" ) + 6, resInfo
                        .indexOf( ";", resInfo.indexOf( "reUrl=" ) + 6 ) );

                Long newId = Long.valueOf( StringUtil.getLongValue( StringUtil.subString( resInfo,
                    resInfo.indexOf( "id=" ) + 3, resInfo.indexOf( ";",
                        resInfo.indexOf( "id=" ) + 3 ) ), -1 ) );

                newContent = StringUtil.replaceString( content, targetId, "jtopcms_content_file_"
                    + newId, false, false );

                newContent = StringUtil.replaceString( newContent, "content/clientDf.do?id=" + id,
                    "content/clientDf.do?id=" + newId, false, false );

                // newContent = StringUtil.replaceString( newContent, fullSrc,
                // newUrl,
                // false, false );
            }

        }

        return newContent;
    }

    public void copyAllContentToSiteClass( Long classId, List classIdList, boolean shareMode )
    {
        // 不开启事务,以下调用带事务删除
        for ( int i = 0; i < 10; i++ )
        {
            Long prevCid = Long.valueOf( Constant.CONTENT.MAX_ID_FLAG );

            Long modelId = null;

            ContentClassBean classBean = channelDao.querySingleClassBeanInfoByClassId( classId );

            if( classId != null )
            {
                modelId = classBean.getContentType();
            }

            List needMoveContentList = contentDao.queryMainContentIdByClassIdAndModelId( classId,
                modelId, prevCid, DELETE_QUERY_COUNT );

            while ( !needMoveContentList.isEmpty() )
            {
                prevCid = ( Long ) needMoveContentList.get( needMoveContentList.size() - 1 );

                copyContentToSiteClass( needMoveContentList, classIdList, shareMode );

                needMoveContentList = contentDao.queryMainContentIdByClassIdAndModelId( classId,
                    modelId, prevCid, DELETE_QUERY_COUNT );
            }
        }
    }

    public void linkAllContentToSiteClass( Long classId, List classIdList )
    {
        // 不开启事务,以下调用带事务删除

        Long prevCid = Long.valueOf( Constant.CONTENT.MAX_ID_FLAG );

        Long modelId = null;

        ContentClassBean classBean = channelDao.querySingleClassBeanInfoByClassId( classId );

        if( classId != null )
        {
            modelId = classBean.getContentType();
        }

        List needMoveContentList = contentDao.queryMainContentIdByClassIdAndModelId( classId,
            modelId, prevCid, DELETE_QUERY_COUNT );

        while ( !needMoveContentList.isEmpty() )
        {
            prevCid = ( Long ) needMoveContentList.get( needMoveContentList.size() - 1 );

            linkContentToSiteClass( needMoveContentList, classIdList );

            needMoveContentList = contentDao.queryMainContentIdByClassIdAndModelId( classId,
                modelId, prevCid, DELETE_QUERY_COUNT );
        }
    }

    /***************************************************************************
     * **************************以下为自定义数据模型业务* **************************
     **************************************************************************/

    public Long retrieveContentMainInfoModelIdByCid( Long id )
    {
        return contentDao.queryContentMainInfoModelIdByCid( id );
    }

    /**
     * 获取指定条件下内容总数目
     * 
     * @param targetClassId
     * @param modelBean
     * @param typeBy 内容属性分类
     * @param orderBy
     * @param orderWay
     * @param currentPublishDateTime
     * @param startDate
     * @param endDate
     * @return
     */
    public Integer getUserDefineContentAllCount( Long targetClassId, DataModelBean modelBean,
        String typeBy, Integer censorBy, Timestamp startDate, Timestamp endDate )
    {
        if( modelBean == null )
        {
            return Integer.valueOf( 0 );
        }

        Integer result = Integer.valueOf( 0 );

        Long startDateContentId = null;

        Long endDateContentId = null;

        if( startDate != null || endDate != null )
        {
            startDateContentId = contentDao.queryMinAddDateContentIdByDate( targetClassId,
                modelBean.getDataModelId(), startDate, endDate );

            endDateContentId = contentDao.queryMaxAddDateContentIdByDate( targetClassId, modelBean
                .getDataModelId(), startDate, endDate );

            // 若任何一个无值,说明时间区间内是无匹配值的,直接返回
            if( startDateContentId == null || endDateContentId == null )
            {
                return Integer.valueOf( 0 );
            }
        }

        if( typeBy.length() > 0 )
        {
            // 包含type条件
            if( startDate != null || endDate != null )
            {
                // 有日期限制
                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                {
                    result = contentDao.queryUserDefineContentAllCountDateMode( targetClassId,
                        modelBean, typeBy, startDateContentId, endDateContentId );
                }
                else
                {
                    result = contentDao.queryUserDefineContentAllCountDateMode( targetClassId,
                        modelBean, typeBy, startDateContentId, endDateContentId, censorBy );
                }
            }
            else
            {
                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                {
                    result = contentDao.queryUserDefineContentAllCount( typeBy, targetClassId,
                        modelBean );
                }
                else
                {
                    result = contentDao.queryUserDefineContentAllCount( typeBy, targetClassId,
                        modelBean, censorBy );
                }
            }
        }
        else
        {
            if( startDate != null || endDate != null )
            {
                // 有日期限制
                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                {
                    result = contentDao.queryUserDefineContentAllCountDateMode( targetClassId,
                        modelBean, startDateContentId, endDateContentId );
                }
                else
                {
                    result = contentDao.queryUserDefineContentAllCountDateMode( targetClassId,
                        modelBean, startDateContentId, endDateContentId, censorBy );
                }
            }
            else
            {
                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                {
                    result = contentDao.queryUserDefineContentAllCount( targetClassId, modelBean );
                }
                else
                {
                    result = contentDao.queryUserDefineContentAllCount( targetClassId, modelBean,
                        censorBy );
                }
            }
        }

        return result;
    }

    /**
     * 获取指定条件下内容总数目,classIds模式
     * 
     * @param targetClassId
     * @param modelBean
     * @param typeBy 内容属性分类
     * @param orderBy
     * @param orderWay
     * @param currentPublishDateTime
     * @param startDate
     * @param endDate
     * @return
     */
    public Integer getUserDefineContentAllCount( String classIds, String typeBy, Integer censorBy )
    {

        Integer result = Integer.valueOf( 0 );

        if( typeBy.length() > 0 )
        {

            if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
            {
                result = contentDao.queryUserDefineContentAllCountIdsTb( typeBy, classIds );
            }
            else
            {
                result = contentDao.queryUserDefineContentAllCountIdsTbCen( typeBy, classIds,
                    censorBy );
            }

        }
        else
        {

            if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
            {
                result = contentDao.queryUserDefineContentAllCountIds( classIds );
            }
            else
            {
                result = contentDao.queryUserDefineContentAllCountIdsCen( classIds, censorBy );
            }

        }

        return result;
    }

    /**
     * 获取指定条件下内容总数目
     * 
     * @param targetClassId
     * @param modelBean
     * @param typeBy 内容属性分类
     * @param orderBy
     * @param orderWay
     * @param currentPublishDateTime
     * @param startDate
     * @param endDate
     * @return
     */
    public Integer getUserDefineContentAllCountOrderFilterMode( Long targetClassId,
        DataModelBean modelBean, String typeBy, Integer censorBy, Timestamp startDate,
        Timestamp endDate, String orderFilter )
    {
        if( modelBean == null )
        {
            return Integer.valueOf( 0 );
        }

        Integer result = Integer.valueOf( 0 );

        Long startDateContentId = null;

        Long endDateContentId = null;

        if( startDate != null || endDate != null )
        {
            startDateContentId = contentDao.queryMinAddDateContentIdByDate( targetClassId,
                modelBean.getDataModelId(), startDate, endDate );

            endDateContentId = contentDao.queryMaxAddDateContentIdByDate( targetClassId, modelBean
                .getDataModelId(), startDate, endDate );

            // 若任何一个无值,说明时间区间内是无匹配值的,直接返回
            if( startDateContentId == null || endDateContentId == null )
            {
                return Integer.valueOf( 0 );
            }
        }

        if( typeBy.length() > 0 )
        {
            // 包含type条件
            if( startDate != null || endDate != null )
            {
                // 有日期限制
                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                {
                    result = contentDao.queryUserDefineContentAllCountDateMode( targetClassId,
                        modelBean, typeBy, startDateContentId, endDateContentId, orderFilter );
                }
                else
                {
                    result = contentDao.queryUserDefineContentAllCountDateMode( targetClassId,
                        modelBean, typeBy, startDateContentId, endDateContentId, censorBy,
                        orderFilter );
                }
            }
            else
            {
                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                {
                    result = contentDao.queryUserDefineContentAllCount( typeBy, targetClassId,
                        modelBean, orderFilter );
                }
                else
                {
                    result = contentDao.queryUserDefineContentAllCount( typeBy, targetClassId,
                        modelBean, censorBy, orderFilter );
                }
            }
        }
        else
        {
            if( startDate != null || endDate != null )
            {

                // 有日期限制
                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                {
                    result = contentDao.queryUserDefineContentAllCountDateMode( targetClassId,
                        modelBean, startDateContentId, endDateContentId, orderFilter );
                }
                else
                {
                    result = contentDao.queryUserDefineContentAllCountDateMode( targetClassId,
                        modelBean, startDateContentId, endDateContentId, censorBy, orderFilter );
                }
            }
            else
            {
                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                {
                    result = contentDao.queryUserDefineContentAllCount( targetClassId, modelBean,
                        orderFilter );
                }
                else
                {
                    result = contentDao.queryUserDefineContentAllCount( targetClassId, modelBean,
                        censorBy, orderFilter );
                }
            }
        }

        return result;
    }

    /**
     * 获取指定条件下内容总数目,多classId模式
     * 
     * @param targetClassId
     * @param modelBean
     * @param typeBy 内容属性分类
     * @param orderBy
     * @param orderWay
     * @param currentPublishDateTime
     * @param startDate
     * @param endDate
     * @return
     */
    public Integer getUserDefineContentAllCountOrderFilterMode( String classIds, String typeBy,
        Integer censorBy, String orderFilter )
    {

        Integer result = Integer.valueOf( 0 );

        if( typeBy.length() > 0 )
        {
            // 包含type条件

            if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
            {
                result = contentDao.queryUserDefineContentAllCountIdsTb( typeBy, classIds,
                    orderFilter );
            }
            else
            {
                result = contentDao.queryUserDefineContentAllCountIdsTb( typeBy, classIds,
                    censorBy, orderFilter );
            }

        }
        else
        {

            if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
            {
                result = contentDao.queryUserDefineContentAllCountIds( classIds, orderFilter );
            }
            else
            {
                result = contentDao.queryUserDefineContentAllCountIds( classIds, censorBy,
                    orderFilter );
            }

        }

        return result;
    }

    public Map retrieveContentPublishInfo( Long contentId )
    {
        return contentDao.queryContentPublishInfo( contentId );
    }

    /**
     * 根据相关条件获取内容数据,TOP数据永远排最前,按照传入的各种排序标准对内容进行排序,根据数据模型和栏目以及每页数据数量,发布时间等条件控制获取范围
     * 
     * @param modelBean 当前数据模型bena
     * @param classId 当前栏目
     * @param startDate 起始发布时间
     * @param endDate 结束发布时间
     * @param startDate 起始时间
     * @param endDate 结束时间
     * @param orderIdFlag 排序标志
     * @param typeBy 数据过滤标志
     * @param censorBy 数据发布标志
     * @param orderBy 排序依赖标准
     * @param orderWay 排序规则
     * @param pageSize 查询每页数值大小
     * @param currentLimitSize 本次查询实际数据大小
     * @param pageActionFlag 查询动作标志
     * @param currentPage 当前页
     * @return
     */
    public List retrieveLimitUserDefineContentByClassIDAndModelIdAndFlag( boolean showAll,
        DataModelBean modelBean, long classId, Timestamp startDate, Timestamp endDate,
        Object idFlag, String typeBy, Integer censorBy, String orderBy, String orderWay,
        int pageSize, int currentLimitSize, int pageActionFlag, Page pageInfo )
    {
        if( modelBean == null )
        {
            return new ArrayList();
        }

        String key = "retrieveLimitUserDefineContentByClassIDAndModelIdAndFlag:" + showAll + "|"
            + modelBean.getDataModelId() + "|" + classId + "|" + startDate + "|" + endDate + "|"
            + idFlag + "|" + typeBy + "|" + censorBy + "|" + orderBy + "|" + orderWay + "|"
            + pageSize + "|" + currentLimitSize + "|" + pageActionFlag + "|"
            + pageInfo.getCurrentPage();

        List resList = ( List ) listContentCache.getEntry( key );

        if( resList == null )
        // if( true )
        {
            if( modelBean == null )
            {
                resList = new ArrayList();
                listContentCache.putEntry( key, resList );
                return resList;
            }

            Long startDateContentId = null;

            Long endDateContentId = null;

            // 若选则了添加时间区间
            if( startDate != null || endDate != null )
            {
                startDateContentId = contentDao.queryMinAddDateContentIdByDate( Long
                    .valueOf( classId ), modelBean.getDataModelId(), startDate, endDate );

                endDateContentId = contentDao.queryMaxAddDateContentIdByDate( Long
                    .valueOf( classId ), modelBean.getDataModelId(), startDate, endDate );

                // 若任何一个无值,说明所选时间区间内是无匹配值的,直接返回
                if( startDateContentId == null || endDateContentId == null )
                {
                    return new ArrayList( 1 );
                }
            }

            // 获取SQL信息
            ModelPersistenceMySqlCodeBean perMysqlCodebean = metaDataService
                .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() );

            if( perMysqlCodebean == null )
            {
                resList = new ArrayList();
                listContentCache.putEntry( key, resList );
                return resList;
            }

            boolean inTopArea = false;
            boolean lastTopArea = false;

            // [0]:top内容数量 [1]:top最后一页个数
            Integer[] topPageInfo = null;

            // 热点内容,无需TOP信息 ,注意:目前所有信息都需要Top参与,则保留此代码
            // if( Constant.CONTENT.HOT_FILTER.equals( filterBy )
            // || Constant.CONTENT.HAME_PAGE_IMG_FILTER.equals( filterBy ) )
            // {
            // topPageInfo = new Integer[] { Integer.valueOf( 0 ),
            // Integer.valueOf( 0 ) };
            // }

            // Top内容首先查询

            if( typeBy.length() > 0 )
            {
                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                {
                    topPageInfo = contentDao.queryTopContentPageInfo( typeBy, Long
                        .valueOf( classId ), modelBean.getDataModelId(), startDateContentId,
                        endDateContentId, pageSize );
                }
                else
                {
                    topPageInfo = contentDao.queryTopContentPageInfo( typeBy, Long
                        .valueOf( classId ), modelBean.getDataModelId(), censorBy,
                        startDateContentId, endDateContentId, pageSize );
                }
            }
            else
            {
                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                {
                    topPageInfo = contentDao.queryTopContentPageInfo( Long.valueOf( classId ),
                        modelBean.getDataModelId(), startDateContentId, endDateContentId, pageSize );
                }
                else
                {
                    topPageInfo = contentDao.queryTopContentPageInfo( Long.valueOf( classId ),
                        modelBean.getDataModelId(), censorBy, startDateContentId, endDateContentId,
                        pageSize );
                }
            }

            if( pageInfo.getCurrentPage() > 0 )
            {
                // 如果当前页小于等于总top数,说明在top区域内
                if( pageInfo.getCurrentPage() <= topPageInfo[0].intValue() )// 在top取值范围内先取top
                {
                    inTopArea = true;

                    // 若正好等于top页,说明为top区域最后一页
                    if( pageInfo.getCurrentPage() == topPageInfo[0].intValue() )
                    {
                        lastTopArea = true;
                    }
                }
            }

            // 上一页和最后一页,同样内部sql分页标准,所以作为一组处理
            if( pageActionFlag == Constant.PAGE.PAGE_ACTION_PREV
                || pageActionFlag == Constant.PAGE.PAGE_ACTION_END )
            {
                if( classId == -1 )
                {
                    // resList = contentDao.queryLimitContentByModelAndFlagId(
                    // modelBean, "desc", topFlag, orderIdFlag, currentLimitSize
                    // );
                }
                else
                {// OK
                    if( inTopArea )// 表示绝对在top数据分页范围,如果需要top数据的话
                    {
                        if( lastTopArea )// OK
                        {
                            // 最后一页top数据,追加"第一页"的非top数据,当前所有类型都需要追加数据

                            // 包含内容分类查询
                            if( typeBy.length() > 0 )
                            {
                                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy
                                    .intValue() )
                                {
                                    // 所有审核条件包含内容

                                    // 取最后一页的top数据,,根据orderWay取最后一笔数据信息
                                    Object lastTopContentOrderIdFlag = getMaxOrMinEndPageModeOrderIdByOrderByAndWay(
                                        orderBy, orderWay );

                                    // 因为是查询末页数据,则使用PAGE_ACTION_PREV标志
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, typeBy, Constant.COMMON.FLAG_IN,
                                        lastTopContentOrderIdFlag, Constant.PAGE.PAGE_ACTION_PREV,
                                        startDateContentId, endDateContentId, topPageInfo[1]
                                            .intValue() );

                                    if( resList.size() < currentLimitSize )
                                    {
                                        // 普通第一页追加数据
                                        idFlag = getMaxOrMinHeadPageModeOrderIdByOrderByAndWay(
                                            orderBy, orderWay );

                                        resList.addAll( contentDao
                                            .queryLimitContentByModelAndFlagId( showAll, modelBean,
                                                perMysqlCodebean, orderBy, orderWay, classId,
                                                typeBy, Constant.COMMON.FLAG_OUT, idFlag,
                                                Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                                endDateContentId, currentLimitSize
                                                    - topPageInfo[1].intValue() ) );
                                    }

                                }
                                else
                                {
                                    // 指定审核条件查询内容
                                    // 首先取top数据,top只有一种独立排序规则

                                    // 取最后一页的top数据,,根据orderWay取最后一笔数据信息
                                    Object lastTopContentOrderIdFlag = getMaxOrMinEndPageModeOrderIdByOrderByAndWay(
                                        orderBy, orderWay );

                                    // 因为是查询末页数据,则使用PAGE_ACTION_PREV标志
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, typeBy, Constant.COMMON.FLAG_IN, censorBy,
                                        lastTopContentOrderIdFlag, Constant.PAGE.PAGE_ACTION_PREV,
                                        startDateContentId, endDateContentId, topPageInfo[1]
                                            .intValue() );

                                    if( resList.size() < currentLimitSize )
                                    {
                                        // 普通第一页追加数据
                                        idFlag = getMaxOrMinHeadPageModeOrderIdByOrderByAndWay(
                                            orderBy, orderWay );

                                        resList.addAll( contentDao
                                            .queryLimitContentByModelAndFlagId( showAll, modelBean,
                                                perMysqlCodebean, orderBy, orderWay, classId,
                                                typeBy, Constant.COMMON.FLAG_OUT, censorBy, idFlag,
                                                Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                                endDateContentId, currentLimitSize
                                                    - topPageInfo[1].intValue() ) );
                                    }

                                }
                            }
                            else
                            {
                                // 不含内容分类属性划分,TOP区域最后一页
                                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy
                                    .intValue() )
                                {
                                    // 所有审核条件包含内容

                                    // 取最后一页的top数据,,根据orderWay取最后一笔数据信息
                                    Object lastTopContentOrderIdFlag = getMaxOrMinEndPageModeOrderIdByOrderByAndWay(
                                        orderBy, orderWay );

                                    // 因为是查询末页数据,则使用PAGE_ACTION_PREV标志
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, Constant.COMMON.FLAG_IN,
                                        lastTopContentOrderIdFlag, Constant.PAGE.PAGE_ACTION_PREV,
                                        startDateContentId, endDateContentId, topPageInfo[1]
                                            .intValue() );

                                    if( resList.size() < currentLimitSize )
                                    {
                                        // 普通第一页追加数据
                                        idFlag = getMaxOrMinHeadPageModeOrderIdByOrderByAndWay(
                                            orderBy, orderWay );

                                        resList.addAll( contentDao
                                            .queryLimitContentByModelAndFlagId( showAll, modelBean,
                                                perMysqlCodebean, orderBy, orderWay, classId,
                                                Constant.COMMON.FLAG_OUT, idFlag,
                                                Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                                endDateContentId, currentLimitSize
                                                    - topPageInfo[1].intValue() ) );
                                    }

                                }
                                else
                                {
                                    // 指定审核条件查询内容
                                    // 首先取top数据,top只有一种独立排序规则

                                    // 取最后一页的top数据,,根据orderWay取最后一笔数据信息
                                    Object lastTopContentOrderIdFlag = getMaxOrMinEndPageModeOrderIdByOrderByAndWay(
                                        orderBy, orderWay );

                                    // 因为是查询末页数据,则使用PAGE_ACTION_PREV标志
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, Constant.COMMON.FLAG_IN, censorBy,
                                        lastTopContentOrderIdFlag, Constant.PAGE.PAGE_ACTION_PREV,
                                        startDateContentId, endDateContentId, topPageInfo[1]
                                            .intValue() );

                                    if( resList.size() < currentLimitSize )
                                    {
                                        // 普通第一页追加数据
                                        idFlag = getMaxOrMinHeadPageModeOrderIdByOrderByAndWay(
                                            orderBy, orderWay );

                                        resList.addAll( contentDao
                                            .queryLimitContentByModelAndFlagId( showAll, modelBean,
                                                perMysqlCodebean, orderBy, orderWay, classId,
                                                Constant.COMMON.FLAG_OUT, censorBy, idFlag,
                                                Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                                endDateContentId, currentLimitSize
                                                    - topPageInfo[1].intValue() ) );
                                    }

                                }
                            }
                        }// OK
                        else
                        {
                            // 纯top区域数据,包含内容属性查询 OK
                            if( typeBy.length() > 0 )
                            {
                                // 获取top数据,默认orderIdFlag desc排序

                                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy
                                    .intValue() )
                                {
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, typeBy, Constant.COMMON.FLAG_IN, idFlag,
                                        Constant.PAGE.PAGE_ACTION_PREV, startDateContentId,
                                        endDateContentId, currentLimitSize );
                                }
                                else
                                {
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, typeBy, Constant.COMMON.FLAG_IN, censorBy, idFlag,
                                        Constant.PAGE.PAGE_ACTION_PREV, startDateContentId,
                                        endDateContentId, currentLimitSize );
                                }
                            }
                            else
                            {
                                // 纯TOP区,不包含typeBy查询,获取top数据,默认orderIdFlag
                                // desc排序

                                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy
                                    .intValue() )
                                {
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, Constant.COMMON.FLAG_IN, idFlag,
                                        Constant.PAGE.PAGE_ACTION_PREV, startDateContentId,
                                        endDateContentId, currentLimitSize );
                                }
                                else
                                {
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, Constant.COMMON.FLAG_IN, censorBy, idFlag,
                                        Constant.PAGE.PAGE_ACTION_PREV, startDateContentId,
                                        endDateContentId, currentLimitSize );
                                }
                            }
                            // OK

                        }

                    }
                    else
                    {// OK
                        // 非Top数据区间

                        // if( currentPage == topPageInfo[0].intValue() + 1
                        // && topPageInfo[1].intValue() == pageSize )//
                        // 表示为恰好脱离top数据页的第一页普通数据.
                        // {
                        // idFlag = getMaxOrMinOrderIdByOrderByAndWay( orderBy,
                        // orderWay );
                        // }

                        // 普通模式,内容属性条件
                        if( typeBy.length() > 0 )
                        {
                            if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy
                                .intValue() )
                            {
                                resList = contentDao.queryLimitContentByModelAndFlagId( showAll,
                                    modelBean, perMysqlCodebean, orderBy, orderWay, classId,
                                    typeBy, Constant.COMMON.FLAG_OUT, idFlag,
                                    Constant.PAGE.PAGE_ACTION_PREV, startDateContentId,
                                    endDateContentId, currentLimitSize );
                            }
                            else
                            {
                                resList = contentDao.queryLimitContentByModelAndFlagId( showAll,
                                    modelBean, perMysqlCodebean, orderBy, orderWay, classId,
                                    typeBy, Constant.COMMON.FLAG_OUT, censorBy, idFlag,
                                    Constant.PAGE.PAGE_ACTION_PREV, startDateContentId,
                                    endDateContentId, currentLimitSize );
                            }
                        }
                        else
                        {

                            // 普通数据,所有审核状态查询,不含内容属性
                            if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy
                                .intValue() )
                            {
                                resList = contentDao.queryLimitContentByModelAndFlagId( showAll,
                                    modelBean, perMysqlCodebean, orderBy, orderWay, classId,
                                    Constant.COMMON.FLAG_OUT, idFlag,
                                    Constant.PAGE.PAGE_ACTION_PREV, startDateContentId,
                                    endDateContentId, currentLimitSize );
                            }
                            else
                            {
                                resList = contentDao.queryLimitContentByModelAndFlagId( showAll,
                                    modelBean, perMysqlCodebean, orderBy, orderWay, classId,
                                    Constant.COMMON.FLAG_OUT, censorBy, idFlag,
                                    Constant.PAGE.PAGE_ACTION_PREV, startDateContentId,
                                    endDateContentId, currentLimitSize );
                            }
                        }
                    }// OK
                }
            }
            else if( pageActionFlag == Constant.PAGE.PAGE_ACTION_NEXT
                || pageActionFlag == Constant.PAGE.PAGE_ACTION_HEAD )
            {
                if( classId == -1 )
                {
                    // resList = contentDao.queryLimitContentByModelAndFlagId(
                    // modelBean, "desc", topFlag, orderIdFlag, currentLimitSize
                    // );
                }
                else
                {// OK
                    if( inTopArea )// 表示绝对在top数据分页范围,如果需要top数据的话
                    {
                        if( lastTopArea )// OK
                        {
                            // 最后一页top数据,追加"第一页"的非top数据,当前所有类型都需要追加数据

                            // 包含内容分类查询
                            if( typeBy.length() > 0 )
                            {
                                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy
                                    .intValue() )
                                {
                                    // 所有审核条件包含内容

                                    // 取最后一页的top数据,,根据orderWay取最后一笔数据信息
                                    Object lastTopContentOrderIdFlag = getMaxOrMinEndPageModeOrderIdByOrderByAndWay(
                                        orderBy, orderWay );

                                    // 因为是查询末页数据,则使用PAGE_ACTION_PREV标志
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, typeBy, Constant.COMMON.FLAG_IN,
                                        lastTopContentOrderIdFlag, Constant.PAGE.PAGE_ACTION_PREV,
                                        startDateContentId, endDateContentId, topPageInfo[1]
                                            .intValue() );

                                    if( resList.size() < currentLimitSize )
                                    {
                                        // 普通第一页追加数据
                                        idFlag = getMaxOrMinHeadPageModeOrderIdByOrderByAndWay(
                                            orderBy, orderWay );

                                        resList.addAll( contentDao
                                            .queryLimitContentByModelAndFlagId( showAll, modelBean,
                                                perMysqlCodebean, orderBy, orderWay, classId,
                                                typeBy, Constant.COMMON.FLAG_OUT, idFlag,
                                                Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                                endDateContentId, currentLimitSize
                                                    - topPageInfo[1].intValue() ) );
                                    }

                                }
                                else
                                {
                                    // 指定审核条件查询内容
                                    // 首先取top数据,top只有一种独立排序规则

                                    // 取最后一页的top数据,,根据orderWay取最后一笔数据信息
                                    Object lastTopContentOrderIdFlag = getMaxOrMinEndPageModeOrderIdByOrderByAndWay(
                                        orderBy, orderWay );

                                    // 因为是查询末页数据,则使用PAGE_ACTION_PREV标志
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, typeBy, Constant.COMMON.FLAG_IN, censorBy,
                                        lastTopContentOrderIdFlag, Constant.PAGE.PAGE_ACTION_PREV,
                                        startDateContentId, endDateContentId, topPageInfo[1]
                                            .intValue() );

                                    if( resList.size() < currentLimitSize )
                                    {
                                        // 普通第一页追加数据
                                        idFlag = getMaxOrMinHeadPageModeOrderIdByOrderByAndWay(
                                            orderBy, orderWay );

                                        resList.addAll( contentDao
                                            .queryLimitContentByModelAndFlagId( showAll, modelBean,
                                                perMysqlCodebean, orderBy, orderWay, classId,
                                                typeBy, Constant.COMMON.FLAG_OUT, censorBy, idFlag,
                                                Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                                endDateContentId, currentLimitSize
                                                    - topPageInfo[1].intValue() ) );
                                    }

                                }
                            }
                            else
                            {
                                // 不含内容分类属性划分,TOP区域最后一页
                                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy
                                    .intValue() )
                                {
                                    // 所有审核条件包含内容

                                    // 取最后一页的top数据,,根据orderWay取最后一笔数据信息
                                    Object lastTopContentOrderIdFlag = getMaxOrMinEndPageModeOrderIdByOrderByAndWay(
                                        orderBy, orderWay );

                                    // 因为是查询末页数据,则使用PAGE_ACTION_PREV标志
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, Constant.COMMON.FLAG_IN,
                                        lastTopContentOrderIdFlag, Constant.PAGE.PAGE_ACTION_PREV,
                                        startDateContentId, endDateContentId, topPageInfo[1]
                                            .intValue() );

                                    if( resList.size() < currentLimitSize )
                                    {
                                        // 普通第一页追加数据
                                        idFlag = getMaxOrMinHeadPageModeOrderIdByOrderByAndWay(
                                            orderBy, orderWay );

                                        resList.addAll( contentDao
                                            .queryLimitContentByModelAndFlagId( showAll, modelBean,
                                                perMysqlCodebean, orderBy, orderWay, classId,
                                                Constant.COMMON.FLAG_OUT, idFlag,
                                                Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                                endDateContentId, currentLimitSize
                                                    - topPageInfo[1].intValue() ) );
                                    }

                                }
                                else
                                {
                                    // 指定审核条件查询内容
                                    // 首先取top数据,top只有一种独立排序规则

                                    // 取最后一页的top数据,,根据orderWay取最后一笔数据信息
                                    Object lastTopContentOrderIdFlag = getMaxOrMinEndPageModeOrderIdByOrderByAndWay(
                                        orderBy, orderWay );

                                    // 因为是查询末页数据,则使用PAGE_ACTION_PREV标志
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, Constant.COMMON.FLAG_IN, censorBy,
                                        lastTopContentOrderIdFlag, Constant.PAGE.PAGE_ACTION_PREV,
                                        startDateContentId, endDateContentId, topPageInfo[1]
                                            .intValue() );

                                    if( resList.size() < currentLimitSize )
                                    {
                                        // 普通第一页追加数据
                                        idFlag = getMaxOrMinHeadPageModeOrderIdByOrderByAndWay(
                                            orderBy, orderWay );

                                        resList.addAll( contentDao
                                            .queryLimitContentByModelAndFlagId( showAll, modelBean,
                                                perMysqlCodebean, orderBy, orderWay, classId,
                                                Constant.COMMON.FLAG_OUT, censorBy, idFlag,
                                                Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                                endDateContentId, currentLimitSize
                                                    - topPageInfo[1].intValue() ) );
                                    }

                                }
                            }
                        }// OK
                        else
                        {
                            // 纯top区域数据,包含内容属性查询 OK
                            if( typeBy.length() > 0 )
                            {
                                // 获取top数据,默认orderIdFlag desc排序

                                if( pageInfo.getCurrentPage() == 1 )
                                {
                                    // 第一页需要和最大MAX ID比较
                                    idFlag = Double.valueOf( Constant.CONTENT.MAX_ORDER_ID_FLAG );
                                }

                                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy
                                    .intValue() )
                                {
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, typeBy, Constant.COMMON.FLAG_IN, idFlag,
                                        Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                        endDateContentId, currentLimitSize );
                                }
                                else
                                {
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, typeBy, Constant.COMMON.FLAG_IN, censorBy, idFlag,
                                        Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                        endDateContentId, currentLimitSize );
                                }
                            }
                            else
                            {
                                // 纯TOP区,不包含typeBy查询,获取top数据,默认orderIdFlag
                                // desc排序

                                // if( pageInfo.getCurrentPage() == 1 )
                                // {
                                // // 第一页需要和最大MAX ID比较
                                // idFlag = Double
                                // .valueOf( Constant.CONTENT.MAX_ORDER_ID_FLAG
                                // );
                                // }

                                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy
                                    .intValue() )
                                {
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, Constant.COMMON.FLAG_IN, idFlag,
                                        Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                        endDateContentId, currentLimitSize );
                                }
                                else
                                {
                                    resList = contentDao.queryLimitContentByModelAndFlagId(
                                        showAll, modelBean, perMysqlCodebean, orderBy, orderWay,
                                        classId, Constant.COMMON.FLAG_IN, censorBy, idFlag,
                                        Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                        endDateContentId, currentLimitSize );
                                }
                            }
                            // OK

                        }

                    }
                    else
                    {// OK
                        // 非Top数据区间

                        if( pageInfo.getCurrentPage() == topPageInfo[0].intValue() + 1
                            && topPageInfo[1].intValue() == pageSize )// 表示为恰好脱离top数据页的第一页普通数据.
                        {
                            idFlag = getMaxOrMinHeadPageModeOrderIdByOrderByAndWay( orderBy,
                                orderWay );
                        }

                        // 普通模式,内容属性条件
                        if( typeBy.length() > 0 )
                        {
                            if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy
                                .intValue() )
                            {
                                resList = contentDao.queryLimitContentByModelAndFlagId( showAll,
                                    modelBean, perMysqlCodebean, orderBy, orderWay, classId,
                                    typeBy, Constant.COMMON.FLAG_OUT, idFlag,
                                    Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                    endDateContentId, currentLimitSize );
                            }
                            else
                            {
                                resList = contentDao.queryLimitContentByModelAndFlagId( showAll,
                                    modelBean, perMysqlCodebean, orderBy, orderWay, classId,
                                    typeBy, Constant.COMMON.FLAG_OUT, censorBy, idFlag,
                                    Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                    endDateContentId, currentLimitSize );
                            }
                        }
                        else
                        {

                            // 普通数据,所有审核状态查询,不含内容属性
                            if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy
                                .intValue() )
                            {
                                resList = contentDao.queryLimitContentByModelAndFlagId( showAll,
                                    modelBean, perMysqlCodebean, orderBy, orderWay, classId,
                                    Constant.COMMON.FLAG_OUT, idFlag,
                                    Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                    endDateContentId, currentLimitSize );
                            }
                            else
                            {
                                resList = contentDao.queryLimitContentByModelAndFlagId( showAll,
                                    modelBean, perMysqlCodebean, orderBy, orderWay, classId,
                                    Constant.COMMON.FLAG_OUT, censorBy, idFlag,
                                    Constant.PAGE.PAGE_ACTION_NEXT, startDateContentId,
                                    endDateContentId, currentLimitSize );
                            }
                        }
                    }// OK
                }
            }

            listContentCache.putEntry( key, resList );
        }

        return resList;
    }

    /**
     * limit传统分页取数据,包含top数据模式
     * 
     * @param modelBean
     * @param targetClassId
     * @param typeBy
     * @param censorBy
     * @param startDate
     * @param endDate
     * @param pagePos
     * @param pageSize
     * @param orderBy
     * @param orderWay
     * @return
     */
    public List retrieveLimitModeContentTopMode( boolean showAll, DataModelBean modelBean,
        long targetClassId, Integer censorBy, String typeBy, Timestamp startDate,
        Timestamp endDate, long pagePos, int pageSize, String orderFilter, String orderBy,
        String orderWay )
    {
        if( modelBean == null )
        {
            return new ArrayList();
        }

        String key = "retrieveLimitModeContentTopMode:" + showAll + "|"
            + modelBean.getDataModelId() + "|" + targetClassId + "|" + censorBy + "|" + typeBy
            + "|" + startDate + "|" + endDate + "|" + pagePos + "|" + pageSize + "|" + orderFilter
            + "|" + orderBy + "|" + orderWay;

        List result = ( List ) listContentCache.getEntry( key );

        if( result == null )
        {

            // 获取SQL信息
            ModelPersistenceMySqlCodeBean perMysqlCodebean = metaDataService
                .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() );

            if( perMysqlCodebean == null )
            {
                return new ArrayList();
            }

            Long startDateContentId = null;

            Long endDateContentId = null;

            boolean haveDateFlag = false;
            // 若选则了添加时间区间
            if( startDate != null || endDate != null )
            {
                haveDateFlag = true;

                startDateContentId = contentDao.queryMinAddDateContentIdByDate( Long
                    .valueOf( targetClassId ), modelBean.getDataModelId(), startDate, endDate );

                endDateContentId = contentDao.queryMaxAddDateContentIdByDate( Long
                    .valueOf( targetClassId ), modelBean.getDataModelId(), startDate, endDate );

                // 若任何一个无值,说明所选时间区间内是无匹配值的,直接返回
                if( startDateContentId == null || endDateContentId == null )
                {
                    result = new ArrayList( 1 );
                    listContentCache.putEntry( key, result );
                    return result;
                }
            }

            if( typeBy.length() > 0 )
            {
                // 包含type条件
                if( haveDateFlag )
                {
                    // 有日期限制
                    if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                    {
                        result = contentDao.queryLimitModeContentTopMode( showAll, modelBean,
                            perMysqlCodebean, targetClassId, typeBy, startDateContentId,
                            endDateContentId, pagePos, pageSize, orderFilter, orderBy, orderWay );
                    }
                    else
                    {
                        result = contentDao.queryLimitModeContentTopMode( showAll, modelBean,
                            perMysqlCodebean, targetClassId, censorBy.intValue(), typeBy,
                            startDateContentId, endDateContentId, pagePos, pageSize, orderFilter,
                            orderBy, orderWay );
                    }
                }
                else
                {
                    if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                    {
                        result = contentDao.queryLimitModeContentTopMode( showAll, modelBean,
                            perMysqlCodebean, targetClassId, typeBy, null, null, pagePos, pageSize,
                            orderFilter, orderBy, orderWay );
                    }
                    else
                    {
                        result = contentDao.queryLimitModeContentTopMode( showAll, modelBean,
                            perMysqlCodebean, targetClassId, censorBy.intValue(), typeBy, null,
                            null, pagePos, pageSize, orderFilter, orderBy, orderWay );
                    }
                }
            }
            else
            {
                if( haveDateFlag )
                {
                    // 有日期限制
                    if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                    {
                        result = contentDao.queryLimitModeContentTopMode( showAll, modelBean,
                            perMysqlCodebean, targetClassId, startDateContentId, endDateContentId,
                            pagePos, pageSize, orderFilter, orderBy, orderWay );
                    }
                    else
                    {
                        result = contentDao.queryLimitModeContentTopMode( showAll, modelBean,
                            perMysqlCodebean, targetClassId, censorBy.intValue(),
                            startDateContentId, endDateContentId, pagePos, pageSize, orderFilter,
                            orderBy, orderWay );
                    }
                }
                else
                {
                    if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                    {
                        result = contentDao.queryLimitModeContentTopMode( showAll, modelBean,
                            perMysqlCodebean, targetClassId, null, null, pagePos, pageSize,
                            orderFilter, orderBy, orderWay );
                    }
                    else
                    {
                        result = contentDao.queryLimitModeContentTopMode( showAll, modelBean,
                            perMysqlCodebean, targetClassId, censorBy.intValue(), null, null,
                            pagePos, pageSize, orderFilter, orderBy, orderWay );
                    }
                }
            }

            listContentCache.putEntry( key, result );
        }

        return result;
    }

    /**
     * limit传统分页取数据,普通数据模式
     * 
     * @param modelBean
     * @param targetClassId
     * @param typeBy
     * @param censorBy
     * @param startDate
     * @param endDate
     * @param pagePos
     * @param pageSize
     * @param orderBy
     * @param orderWay
     * @return
     */
    public List retrieveLimitModeContent( boolean showAll, DataModelBean modelBean,
        long targetClassId, Integer censorBy, String typeBy, Timestamp startDate,
        Timestamp endDate, long pagePos, int pageSize, String orderFilter, String orderBy,
        String orderWay )
    {
        if( modelBean == null )
        {
            return new ArrayList();
        }

        String key = "retrieveLimitModeContent:" + showAll + "|" + modelBean.getDataModelId() + "|"
            + targetClassId + "|" + censorBy + "|" + typeBy + "|" + startDate + "|" + endDate + "|"
            + pagePos + "|" + pageSize + "|" + orderFilter + "|" + orderBy + "|" + orderWay;

        List result = ( List ) fastListContentCache.getEntry( key );

        if( result == null )
        {

            // 获取SQL信息
            ModelPersistenceMySqlCodeBean perMysqlCodebean = metaDataService
                .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() );

            Long startDateContentId = null;

            Long endDateContentId = null;

            boolean haveDateFlag = false;
            // 若选则了添加时间区间
            if( startDate != null || endDate != null )
            {
                haveDateFlag = true;

                startDateContentId = contentDao.queryMinAddDateContentIdByDate( Long
                    .valueOf( targetClassId ), modelBean.getDataModelId(), startDate, endDate );

                endDateContentId = contentDao.queryMaxAddDateContentIdByDate( Long
                    .valueOf( targetClassId ), modelBean.getDataModelId(), startDate, endDate );

                // 若任何一个无值,说明所选时间区间内是无匹配值的,直接返回
                if( startDateContentId == null || endDateContentId == null )
                {
                    result = new ArrayList( 1 );
                    listContentCache.putEntry( key, result );
                    return result;
                }
            }

            if( typeBy.length() > 0 )
            {
                // 包含type条件
                if( haveDateFlag )
                {
                    // 有日期限制
                    if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                    {
                        result = contentDao.queryLimitModeContent( showAll, modelBean,
                            perMysqlCodebean, targetClassId, typeBy, startDateContentId,
                            endDateContentId, pagePos, pageSize, orderFilter, orderBy, orderWay );
                    }
                    else
                    {
                        result = contentDao.queryLimitModeContent( showAll, modelBean,
                            perMysqlCodebean, targetClassId, censorBy.intValue(), typeBy,
                            startDateContentId, endDateContentId, pagePos, pageSize, orderFilter,
                            orderBy, orderWay );
                    }
                }
                else
                {
                    if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                    {
                        result = contentDao.queryLimitModeContent( showAll, modelBean,
                            perMysqlCodebean, targetClassId, typeBy, null, null, pagePos, pageSize,
                            orderFilter, orderBy, orderWay );
                    }
                    else
                    {
                        result = contentDao.queryLimitModeContent( showAll, modelBean,
                            perMysqlCodebean, targetClassId, censorBy.intValue(), typeBy, null,
                            null, pagePos, pageSize, orderFilter, orderBy, orderWay );
                    }
                }
            }
            else
            {
                if( haveDateFlag )
                {
                    // 有日期限制
                    if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                    {
                        result = contentDao.queryLimitModeContent( showAll, modelBean,
                            perMysqlCodebean, targetClassId, startDateContentId, endDateContentId,
                            pagePos, pageSize, orderFilter, orderBy, orderWay );
                    }
                    else
                    {
                        result = contentDao.queryLimitModeContent( showAll, modelBean,
                            perMysqlCodebean, targetClassId, censorBy.intValue(),
                            startDateContentId, endDateContentId, pagePos, pageSize, orderFilter,
                            orderBy, orderWay );
                    }
                }
                else
                {
                    if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                    {

                        result = contentDao.queryLimitModeContent( showAll, modelBean,
                            perMysqlCodebean, targetClassId, null, null, pagePos, pageSize,
                            orderFilter, orderBy, orderWay );

                    }
                    else
                    {
                        result = contentDao.queryLimitModeContent( showAll, modelBean,
                            perMysqlCodebean, targetClassId, censorBy.intValue(), null, null,
                            pagePos, pageSize, orderFilter, orderBy, orderWay );
                    }
                }
            }

            fastListContentCache.putEntry( key, result );

        }

        return result;
    }

    /**
     * limit传统分页取数据,普通数据模式,可以传入多个栏目信息,只获取已经通过审核发布的内容
     * 
     * @param modelBean
     * @param targetClassId
     * @param typeBy
     * @param censorBy
     * @param startDate
     * @param endDate
     * @param pagePos
     * @param pageSize
     * @param orderBy
     * @param orderWay
     * @return
     */
    public List retrieveLimitModeContentMainInfoByClassIds( String classIds, String typeBy,
        long startPos, int pageSize, String orderFilter, String orderBy, String orderWay )
    {

        List<Map> result = null;

        String key = "retrieveLimitModeContentMainInfoByClassIds:" + classIds + "|" + typeBy + "|"
            + startPos + "|" + pageSize + "|" + orderFilter + "|" + orderBy + "|" + orderWay;

        String order = orderBy.toLowerCase();

        boolean fastMode = false;

        if( order.indexOf( "comm" ) != -1 || order.indexOf( "click" ) != -1
            || order.indexOf( "su" ) != -1 || order.indexOf( "ag" ) != -1 )
        {
            fastMode = true;
        }

        if( fastMode )
        {
            result = ( List ) fastListContentCache.getEntry( key );
        }
        else
        {
            result = ( List ) listContentCache.getEntry( key );
        }

        if( result == null )
        {
            // 只取通过审核发布的内容
            Integer censorBy = Constant.WORKFLOW.CENSOR_STATUS_SUCCESS;

            if( typeBy.length() > 0 )
            {
                // 包含type条件

                result = contentDao
                    .queryLimitModeContentIdByClassIdsOnlyMainInfo( classIds, typeBy, censorBy
                        .intValue(), startPos, pageSize, orderFilter, orderBy, orderWay );
            }
            else
            {
                result = contentDao.queryLimitModeContentIdByClassIdsOnlyMainInfo( classIds, null,
                    censorBy.intValue(), startPos, pageSize, orderFilter, orderBy, orderWay );

            }
            
            List fullRes = new ArrayList();
            
            for(Map ci : result)
            {
               Long cid = ( Long ) ci.get( "contentId" );
                
               Map fc =  retrieveSingleUserDefineContent(  cid , Integer.valueOf( 1 ) );
               
               fullRes.add( fc );
                
            }
            
            result = fullRes;

            if( fastMode )
            {
                fastListContentCache.putEntry( key, result );
            }
            else
            {
                listContentCache.putEntry( key, result );
            }

        }

        return result;
    }

    /**
     * limit传统分页取数据,普通数据模式,可以传入多个栏目信息,只获取已经通过审核发布的内容
     * 
     * @param modelBean
     * @param targetClassId
     * @param typeBy
     * @param censorBy
     * @param startDate
     * @param endDate
     * @param pagePos
     * @param pageSize
     * @param orderBy
     * @param orderWay
     * @return
     */
    public List retrieveLimitModeContentMainInfo( Long siteId, String typeBy, long startPos,
        int pageSize, String orderFilter, String orderBy, String orderWay )
    {

        List result = null;

        String key = "retrieveLimitModeContentMainInfo:" + siteId + "|" + typeBy + "|" + startPos
            + "|" + pageSize + "|" + orderFilter + "|" + orderBy + "|" + orderWay;

        String order = orderBy.toLowerCase();

        boolean fastMode = false;

        if( order.indexOf( "comm" ) != -1 || order.indexOf( "click" ) != -1
            || order.indexOf( "su" ) != -1 || order.indexOf( "ag" ) != -1 )
        {
            fastMode = true;
        }

        if( fastMode )
        {
            result = ( List ) fastListContentCache.getEntry( key );
        }
        else
        {
            result = ( List ) listContentCache.getEntry( key );

        }

        if( result == null )
        {

            // 只取通过审核发布的内容
            Integer censorBy = Constant.WORKFLOW.CENSOR_STATUS_SUCCESS;

            if( typeBy.length() > 0 )
            {
                // 包含type条件

                result = contentDao.queryLimitModeContentIdOnlyMainInfo( siteId, typeBy, censorBy
                    .intValue(), startPos, pageSize, orderFilter, orderBy, orderWay );

            }
            else
            {
                result = contentDao.queryLimitModeContentIdOnlyMainInfo( siteId, null, censorBy
                    .intValue(), startPos, pageSize, orderFilter, orderBy, orderWay );

            }

            if( fastMode )
            {
                fastListContentCache.putEntry( key, result );
            }
            else
            {
                listContentCache.putEntry( key, result );
            }

        }

        return result;
    }

    /**
     * 
     * @param targetClassId
     * @param modelId
     * @param filterBy
     * @param startDate
     * @param endDate
     * @return
     */
    public Map retrieveContentQueryFlagForEndPageMode( Long targetClassId, Long modelId,
        String typeBy, Integer censorBy, String orderBy, String orderWay, Timestamp startDate,
        Timestamp endDate )
    {
        // TODO 需要改进缓存模式
        if( typeBy.length() > 0 )
        {
            if( startDate != null || endDate != null )
            {
                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                {
                    return contentDao.queryFirstContentQueryFlagDateMode( typeBy, targetClassId,
                        startDate, endDate, modelId );
                }
                else
                {
                    return contentDao.queryFirstContentQueryFlagDateMode( typeBy, targetClassId,
                        startDate, endDate, modelId );
                }
            }
            else
            {
                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                {
                    return contentDao.queryFirstContentQueryFlagDateMode( typeBy, targetClassId,
                        modelId );
                }
                else
                {
                    return contentDao.queryFirstContentQueryFlag( typeBy, targetClassId, modelId,
                        censorBy );
                }
            }

        }
        else
        {
            // 普通模式
            if( startDate != null || endDate != null )
            {
                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                {
                    return contentDao.queryFirstContentQueryFlagDateMode( targetClassId, startDate,
                        endDate, modelId );
                }
                else
                {
                    return contentDao.queryFirstContentQueryFlagDateMode( targetClassId, startDate,
                        endDate, modelId );
                }
            }
            else
            {
                if( Constant.WORKFLOW.CENSOR_ALL_STATUS.intValue() == censorBy.intValue() )
                {
                    return contentDao.queryFirstContentQueryFlagDateMode( targetClassId, modelId );
                }
                else
                {
                    return contentDao.queryFirstContentQueryFlag( targetClassId, modelId, censorBy );
                }
            }

        }

    }

    public void updateWaitPublishContentSuccessStatus( Map contentMap, Timestamp currDT )
    {
        try
        {
            mysqlEngine.beginTransaction();

            Long contentId = ( Long ) contentMap.get( "contentId" );

            // 发布状态,需要更新排序ID以及发布状态
            contentDao.updateSystemPublishIdFlagAndCensorStatusAndPubDate(
                getNextPublishOrderTrace(), Constant.WORKFLOW.CENSOR_STATUS_SUCCESS, currDT,
                contentId );

            // 将索引状态改动为已发布
            SearchIndexContentState searchIndexState = new SearchIndexContentState();

            searchIndexState.setClassId( ( Long ) contentMap.get( "classId" ) );
            searchIndexState.setContentId( contentId );

            searchIndexState.setCensor( Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );
            searchIndexState.setBoost( ( Float ) contentMap.get( "boost" ) );
            searchIndexState.setIndexDate( ( Date ) contentMap.get( "addTime" ) );
            searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_EDIT );

            searchIndexState.setModelId( ( Long ) contentMap.get( "modelId" ) );
            searchIndexState.setSiteId( ( Long ) contentMap.get( "siteId" ) );

            searchService.addIndexContentState( searchIndexState );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            // 缓存更新在更高级别
        }
    }

    public void updateWithdrawContentSuccessStatus( Map contentMap )
    {
        try
        {
            mysqlEngine.beginTransaction();

            Long contentId = ( Long ) contentMap.get( "contentId" );

            // 发布状态,需要更新发布状态

            contentDao.updateContentCensorState( contentId,
                Constant.WORKFLOW.CENSOR_STATUS_WITHDRAW );

            // 将索引状态改动为已发布
            SearchIndexContentState searchIndexState = new SearchIndexContentState();

            searchIndexState.setClassId( ( Long ) contentMap.get( "classId" ) );
            searchIndexState.setContentId( contentId );

            searchIndexState.setCensor( Constant.WORKFLOW.CENSOR_STATUS_WITHDRAW );
            searchIndexState.setBoost( ( Float ) contentMap.get( "boost" ) );
            searchIndexState.setIndexDate( ( Date ) contentMap.get( "addTime" ) );
            searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_EDIT );

            searchIndexState.setModelId( ( Long ) contentMap.get( "modelId" ) );
            searchIndexState.setSiteId( ( Long ) contentMap.get( "siteId" ) );

            searchService.addIndexContentState( searchIndexState );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            // 在更高级处更新cache
        }
    }

    /**
     * 为静态发布工作批量获取模型内容,所有相关值(主数据和定义数据以及辅助数据)必须全部获取
     * 
     * @param classId
     * @param modelId
     * @param orderIdFlag
     * @param limitCount
     * @return
     */
    public List retrieveNeedPublishContentByClassIDAndModelIdAndFlag( Long classId, Long modelId,
        Double orderIdFlag, Integer limitCount, Timestamp startAddDate, Timestamp endAddDate )
    {
        List result = null;

        DataModelBean modelBean = metaDataService.retrieveSingleDataModelBeanById( modelId );

        if( modelBean == null )
        {
            return Collections.EMPTY_LIST;
        }

        ModelPersistenceMySqlCodeBean sqlBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( modelId );

        // 如果为文章类型,需要连带查询内容分页数据的第一页
        if( Constant.METADATA.MODEL_RES_ARTICLE.equals( modelBean.getModelResType() ) )
        {
            if( startAddDate == null && endAddDate == null )
            {
                result = contentDao
                    .queryNeedPublishContentAndPageContentByClassIDAndModelIdAndFlag( classId,
                        modelBean, sqlBean, orderIdFlag, limitCount );
            }
            else
            {
                result = contentDao
                    .queryNeedPublishContentAndPageContentByClassIDAndModelIdAndFlag( classId,
                        modelBean, sqlBean, orderIdFlag, startAddDate, endAddDate, limitCount );
            }
        }
        else
        {
            if( startAddDate == null && endAddDate == null )
            {
                result = contentDao.queryNeedPublishContentByClassIDAndModelIdAndFlag( classId,
                    modelBean, sqlBean, orderIdFlag, limitCount );
            }
            else
            {
                result = contentDao.queryNeedPublishContentByClassIDAndModelIdAndFlag( classId,
                    modelBean, sqlBean, orderIdFlag, startAddDate, endAddDate, limitCount );
            }
        }

        return result;
    }

    public List retrieveWaitPublishContentBySiteId( Long siteId, Timestamp currTime )
    {
        return contentDao.querySiteWaitPublishContentMainInfo( siteId, currTime );
    }

    public List retrieveWithdrawContentBySiteId( Long siteId, Timestamp currTime )
    {
        return contentDao.querySiteWithdrawContentMainInfo( siteId, currTime );
    }

    public void addWaitPublishIdTemp( Long contentId, Double orderIdFlag, Long classId )
    {
        contentDao.saveWaitPublishIdTemp( contentId, orderIdFlag, classId );
    }

    public void deleteWaitPublishIdTemp()
    {
        contentDao.deleteWaitPublishIdTemp();
    }

    public List retrieveWaitPublishContentBySiteIdAndCurrentDate( Long classId, Long modelId,
        Double orderIdFlag, Integer limitCount )
    {
        List result = null;

        DataModelBean modelBean = metaDataService.retrieveSingleDataModelBeanById( modelId );

        if( modelBean == null )
        {
            return Collections.EMPTY_LIST;
        }

        ModelPersistenceMySqlCodeBean sqlBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( modelId );

        // 如果为文章类型,需要连带查询内容分页数据的第一页
        if( Constant.METADATA.MODEL_RES_ARTICLE.equals( modelBean.getModelResType() ) )
        {
            result = contentDao.queryWaitPublishArticlePageContentIdByCurrentDate( classId,
                modelBean, sqlBean, orderIdFlag, limitCount );

        }
        else
        {
            result = contentDao.queryWaitPublishContentIdByCurrentDate( classId, modelBean,
                sqlBean, orderIdFlag, limitCount );
        }

        return result;

    }

    public Long retrieveNeedPublishContentCountByClassIDAndModelIdAndFlag( Long classId,
        Long modelId, Double orderIdFlag, Timestamp startAddDate, Timestamp endAddDate )
    {
        DataModelBean modelBean = metaDataService.retrieveSingleDataModelBeanById( modelId );

        if( modelBean == null )
        {
            return Long.valueOf( 0 );
        }

        if( startAddDate == null && endAddDate == null )
        {
            return contentDao.queryNeedPublishContentCountByClassIDAndModelIdAndFlag( classId,
                modelBean, orderIdFlag );
        }
        else
        {
            return contentDao.queryNeedPublishContentCountByClassIDAndModelIdAndFlagAndAddDate(
                classId, modelBean, orderIdFlag, startAddDate, endAddDate );
        }
    }

    public Map retrieveSingleUserDefineContent( DataModelBean modelBean, Long id )
    {
        if( modelBean == null )
        {
            return Collections.EMPTY_MAP;
        }

        return contentDao.querySingleUserDefineContent( metaDataService
            .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() ), modelBean
            .getRelateTableName(), id, Integer.valueOf( 1 ) );
    }

    /**
     * 获取自定义模型的内容数据,不包含内容模型的maininfo,全通用
     * 
     * @param modelBean
     * @param id
     * @return
     */
    public Map retrieveSingleUserDefineContentOnlyModelData( Long modelId, Long id, String siteFlag )
    {
        DataModelBean modelBean = metaDataService.retrieveSingleDataModelBeanById( modelId );

        if( modelBean == null )
        {
            return Collections.EMPTY_MAP;
        }

        return contentDao.querySingleUserDefineContentOnlyModelData( metaDataService
            .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() ), modelBean
            .getRelateTableName(), id, siteFlag, modelId );
    }

    /**
     * 获取单一内容
     * 
     * @param modelId
     * @param id
     * @param pos
     * @return
     */
    public Map retrieveSingleUserDefineContent( Long id, Integer pos )
    {
        String key = "retrieveSingleUserDefineContent:" + id + "|" + pos;

        Map info = ( Map ) singleContentCache.getEntry( key );

        if( info == null )
        {
            DataModelBean modelBean = metaDataDao
                .querySingleDataModelBeanById( retrieveContentMainInfoModelIdByCid( id ) );

            if( modelBean == null )
            {
                info = Collections.EMPTY_MAP;
            }
            else
            {
                // 文章资源类型处理
                if( Constant.METADATA.MODEL_RES_ARTICLE.equals( modelBean.getModelResType() ) )
                {
                    info = contentDao.querySingleUserDefineContent( metaDataService
                        .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() ),
                        modelBean.getRelateTableName(), id, pos );
                }
                else
                {
                    info = contentDao.querySingleUserDefineContent( metaDataService
                        .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() ),
                        modelBean.getRelateTableName(), id );
                }
            }

            singleContentCache.putEntry( key, info );
        }

        return info;

    }

    public Map retrieveSingleNextOrPrevContentById( Double orderId, Long classId, Long modelId,
        String flag )
    {
        String key = "retrieveSingleNextOrPrevContentById:" + orderId + "|" + classId + "|"
            + modelId + "|" + flag;

        Map info = ( Map ) singleContentCache.getEntry( key );

        if( info == null )
        {

            if( "n".equals( flag ) )
            {
                info = contentDao.querySingleNextContentById( orderId, classId, modelId );
            }
            else if( "p".equals( flag ) )
            {
                info = contentDao.querySinglePrevContentById( orderId, classId, modelId );
            }

            singleContentCache.putEntry( key, info );
        }

        return info;
    }

    public Map retrieveSingleUserDefineContent( Long modelId, Long id )
    {
        DataModelBean modelBean = metaDataDao.querySingleDataModelBeanById( modelId );

        if( modelBean == null )
        {
            return Collections.EMPTY_MAP;
        }

        return contentDao.querySingleUserDefineContent( metaDataService
            .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() ), modelBean
            .getRelateTableName(), id );
    }

    public Map retrieveSingleUserDefineContentManageMode( Long modelId, Long id )
    {
        DataModelBean modelBean = metaDataDao.querySingleDataModelBeanById( modelId );

        if( modelBean == null )
        {
            return Collections.EMPTY_MAP;
        }

        // 引用模式专用cache
        String key = "retrieveSingleUserDefineContentManageMode:" + modelId + "|" + id;

        Map info = ( Map ) singleContentCache.getEntry( key );

        if( info == null )
        {
            info = contentDao.querySingleUserDefineContentManageMode( metaDataService
                .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() ), modelBean
                .getRelateTableName(), id );

            singleContentCache.putEntry( key, info );
        }

        return info;
    }

    public Map retrieveSingleUserDefineContentManageMode( String modelName, Long id )
    {
        DataModelBean modelBean = metaDataDao.querySingleDataModelBeanByName( modelName );

        if( modelBean == null )
        {
            return Collections.EMPTY_MAP;
        }

        return contentDao.querySingleUserDefineContentManageMode( metaDataService
            .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() ), modelBean
            .getRelateTableName(), id );
    }

    public boolean checkContentTitleExist( Long siteId, String title )
    {
        if( title == null )
        {
            return false;
        }

        Long count = contentDao.queryCountForContentTitle( siteId, title.trim() );

        if( count.longValue() > 0 )
        {
            return true;
        }

        return false;
    }

    public String retrieveTextFieldVal( DataModelBean modelBean, ModelFiledInfoBean filedInfoBean,
        Long contentId )
    {
        if( modelBean == null )
        {
            return null;
        }

        return contentDao.queryTextColumnVal( modelBean, filedInfoBean, contentId );
    }

    public Map retrieveSingleUserDefineContent( String modelName, Long id, Integer pos )
    {

        String key = "retrieveSingleUserDefineContent:" + modelName + "|" + id + "|" + pos;

        Map info = ( Map ) singleContentCache.getEntry( key );

        if( info == null )
        {
            DataModelBean modelBean = metaDataDao.querySingleDataModelBeanByName( modelName );

            if( modelBean == null )
            {
                info = Collections.EMPTY_MAP;
            }
            else
            {
                // 文章资源类型处理
                if( Constant.METADATA.MODEL_RES_ARTICLE.equals( modelBean.getModelResType() ) )
                {
                    info = contentDao.querySingleUserDefineContent( metaDataService
                        .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() ),
                        modelBean.getRelateTableName(), id, pos );
                }
                else
                {
                    info = contentDao.querySingleUserDefineContent( metaDataService
                        .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() ),
                        modelBean.getRelateTableName(), id, Integer.valueOf( 1 ) );
                }
            }

            singleContentCache.putEntry( key, info );
        }

        return info;
    }

    /**
     * 将指定ID的自定义模型数据删除到trash中
     * 
     * @param modelId
     * @param contentId
     */
    public void deleteSystemAndUserDefineContentToTrash( SiteGroupBean site, List idList,
        List memberList )
    {
        if( idList == null )
        {
            return;
        }

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        try
        {
            mysqlEngine.beginTransaction();

            Long contentId = null;

            Integer censor = null;

            String creator = null;

            for ( int i = 0; i < idList.size(); i++ )
            {

                if( idList.get( i ) instanceof Long )
                {
                    contentId = ( Long ) idList.get( i );
                }
                else
                {
                    contentId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ),
                        -1 ) );
                }

                if( contentId.longValue() < 0 )
                {
                    continue;
                }

                Map mainInfo = contentDao.querySingleContentMainInfo( contentId );

                if( mainInfo.isEmpty() )
                {
                    contentDao.deleteTrashContentMainInfo( ( Long ) mainInfo.get( "contentId" ) );
                }

                // 若存在不是自己的稿件,不可删除
                creator = ( String ) mainInfo.get( "creator" );

                censor = ( Integer ) mainInfo.get( "censorState" );

                if( Constant.WORKFLOW.CENSOR_STATUS_DRAFT.equals( censor ) )
                {

                    if( auth != null && !creator.equals( auth.getApellation() ) )
                    {
                        // 2015-5:解决用户（含会员）删除后，遗漏信息问题，总机构管理员可删除
                        if( !"001".equals( auth.getOrgCode() ) )
                        {
                            continue;
                        }
                    }
                }

                Map exist = contentDao.querySingleTrashContentByContentId( contentId );

                if( !exist.isEmpty() )
                {
                    continue;
                }

                // 记录资源改动
                Map rt = StatService.getInstance().getContentResTrace( contentId );

                // 只有已经提交过的内容需要改变数据
                if( !rt.isEmpty() )
                {

                    int pc = -1;

                    Integer ic = ( Integer ) rt.get( "imgCount" );

                    Integer iv = ( Integer ) rt.get( "videoCount" );

                    if( Constant.COMMON.OFF.equals( rt.get( "isPub" ) ) )
                    {
                        pc = 0;

                        ic = 0;

                        iv = 0;
                    }

                    /*
                     * StatService.getInstance().collAndAnalysisContentStat(
                     * false, null, contentId, null, ( Long ) mainInfo.get(
                     * "siteId" ), ( Long ) mainInfo.get( "classId" ), -9999,
                     * pc, -ic, -iv );
                     */

                }

                // 将main info复制到trash
                contentDao.transferCotentToTrash( contentId );

                // 删除main info
                contentDao.deleteContentMainInfo( contentId );

                // 增加删除索引任务
                SearchIndexContentState searchIndexState = new SearchIndexContentState();

                searchIndexState.setContentId( contentId );

                searchIndexState.setSiteId( ( Long ) mainInfo.get( "siteId" ) );

                searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_DEL );

                searchService.addIndexContentState( searchIndexState );

                // 会员投稿删除记录
                if( Constant.COMMON.ON.equals( mainInfo.get( "otherFlag" ) ) )
                {
                    memberList.add( mainInfo.get( "creator" ) );

                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            ContentDao.releaseAllCountCache();

            releaseContentCache();
        }

    }

    public void recoverAllSystemAndUserDefineContent( Long classId )
    {
        // 不开启事务,以下调用带事务删除

        Long prevCid = Long.valueOf( Constant.CONTENT.MAX_ID_FLAG );

        Long modelId = null;

        ContentClassBean classBean = channelDao.querySingleClassBeanInfoByClassId( classId );

        if( classId != null )
        {
            modelId = classBean.getContentType();
        }

        List needRecoverContentList = contentDao.queryTrashContentIdByClassIdAndModelId( classId,
            modelId, prevCid, DELETE_QUERY_COUNT );

        while ( !needRecoverContentList.isEmpty() )
        {
            prevCid = ( Long ) needRecoverContentList.get( needRecoverContentList.size() - 1 );

            recoverContentForTrash( needRecoverContentList );

            recoverContentForTrash( retrieveTrashLinkInfo( needRecoverContentList ) );

            needRecoverContentList = contentDao.queryTrashContentIdByClassIdAndModelId( classId,
                modelId, prevCid, DELETE_QUERY_COUNT );
        }
    }

    public void recoverContentForTrash( List idList )
    {
        if( idList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long contentId = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                if( idList.get( i ) instanceof String )
                {
                    contentId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ),
                        -1 ) );
                }
                else
                {
                    contentId = ( Long ) idList.get( i );
                }

                if( contentId.longValue() < 0 )
                {
                    continue;
                }

                // 获取原始信息
                Map info = contentDao.querySingleTrashContentByContentId( contentId );

                if( info.isEmpty() )
                {
                    continue;
                }

                // 将内容恢复
                contentDao.transferTrashToCotent( contentId );

                // 删除回收站信息
                contentDao.deleteTrashContentMainInfo( contentId );

                // 记录资源改动
                Map rt = StatService.getInstance().getContentResTrace( contentId );

                if( !rt.isEmpty() )
                {
                    Integer ic = ( Integer ) rt.get( "imgCount" );

                    Integer iv = ( Integer ) rt.get( "videoCount" );

                    int pc = 1;

                    if( Constant.COMMON.OFF.equals( rt.get( "isPub" ) ) )
                    {
                        pc = 0;

                        ic = 0;

                        iv = 0;
                    }

                    /*
                     * StatService.getInstance().collAndAnalysisContentStat(
                     * false, null, contentId, null, ( Long ) info.get( "siteId" ), (
                     * Long ) info.get( "classId" ), 9999, pc, ic, iv );
                     */
                }

                // 索引重新增加
                // 自定义模型支持搜索字段,需要将模型信息加入索引准备
                SearchIndexContentState searchIndexState = new SearchIndexContentState();

                searchIndexState.setClassId( ( Long ) info.get( "classId" ) );
                searchIndexState.setContentId( contentId );

                searchIndexState.setCensor( ( Integer ) info.get( "censorState" ) );
                searchIndexState.setBoost( ( Float ) info.get( "boost" ) );
                searchIndexState.setIndexDate( ( Date ) info.get( "addTime" ) );
                searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_ADD );

                searchIndexState.setModelId( ( Long ) info.get( "modelId" ) );
                searchIndexState.setSiteId( ( Long ) info.get( "siteId" ) );

                searchService.addIndexContentState( searchIndexState );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            ContentDao.releaseAllCountCache();
            releaseContentCache();
        }

    }

    public List retrieveAllTrashContentByclassId( Long classId )
    {
        return contentDao.queryAllTrashContentByclassId( classId );
    }

    // public void deleteAllSystemAndUserDefineContent( Long modelId, Long
    // classId )
    // {
    // // 不开启事务,以下调用带事务删除
    //
    // Long prevCid = Long.valueOf( Constant.CONTENT.MAX_ID_FLAG );
    //
    // List needDeleteContentList = contentDao
    // .queryContentIdByClassIdAndModelId( classId, modelId, prevCid,
    // DELETE_QUERY_COUNT );
    //
    // while ( !needDeleteContentList.isEmpty() )
    // {
    // prevCid = ( Long ) needDeleteContentList.get( needDeleteContentList
    // .size() );
    //
    // deleteSystemAndUserDefineContent( modelId, needDeleteContentList );
    //
    // needDeleteContentList = contentDao
    // .queryContentIdByClassIdAndModelId( classId, modelId, prevCid,
    // DELETE_QUERY_COUNT );
    //
    // }
    //
    // }

    public void deleteAllSystemAndUserDefineContentToTrash( SiteGroupBean site, Long modelId,
        Long classId, List memberList )
    {
        // 不开启事务,以下调用带事务删除

        Long prevCid = Long.valueOf( Constant.CONTENT.MAX_ID_FLAG );

        if( modelId == null || modelId.longValue() < 0 )
        {
            ContentClassBean classBean = channelDao.querySingleClassBeanInfoByClassId( classId );

            if( classId != null )
            {
                modelId = classBean.getContentType();
            }
        }

        List needDeleteContentList = contentDao.queryMainContentIdByClassIdAndModelId( classId,
            modelId, prevCid, DELETE_QUERY_COUNT );

        while ( !needDeleteContentList.isEmpty() )
        {
            prevCid = ( Long ) needDeleteContentList.get( needDeleteContentList.size() - 1 );

            deleteSystemAndUserDefineContentToTrash( site, needDeleteContentList, memberList );

            deleteSystemAndUserDefineContentToTrash( site,
                retrieveLinkInfo( needDeleteContentList ), memberList );

            needDeleteContentList = contentDao.queryMainContentIdByClassIdAndModelId( classId,
                modelId, prevCid, DELETE_QUERY_COUNT );
        }

    }

    public void deleteAllSystemAndUserDefineContent( Long modelId, Long classId )
    {
        // 不开启事务,以下调用带事务删除

        Long prevCid = Long.valueOf( Constant.CONTENT.MAX_ID_FLAG );

        if( modelId == null || modelId.longValue() < 0 )
        {
            ContentClassBean classBean = channelDao.querySingleClassBeanInfoByClassId( classId );

            if( classId != null )
            {
                modelId = classBean.getContentType();
            }
        }

        List needDeleteContentList = contentDao.queryTrashContentIdByClassIdAndModelId( classId,
            modelId, prevCid, DELETE_QUERY_COUNT );

        while ( !needDeleteContentList.isEmpty() )
        {
            prevCid = ( Long ) needDeleteContentList.get( needDeleteContentList.size() - 1 );

            deleteSystemAndUserDefineContent( needDeleteContentList );

            deleteSystemAndUserDefineContent( retrieveTrashLinkInfo( needDeleteContentList ) );

            needDeleteContentList = contentDao.queryTrashContentIdByClassIdAndModelId( classId,
                modelId, prevCid, DELETE_QUERY_COUNT );
        }
    }

    public void deleteAllDefFormContent( SiteGroupBean site, Long modelId )
    {
        // 不开启事务,以下调用带事务删除

        Long prevCid = Long.valueOf( Constant.CONTENT.MAX_ID_FLAG );

        List needDeleteContentList = metaDataService.retrieveFormDataByIdTrace( modelId, Long
            .valueOf( Constant.CONTENT.MAX_ID_FLAG ), DELETE_QUERY_COUNT );

        while ( !needDeleteContentList.isEmpty() )
        {
            prevCid = ( Long ) ( ( Map ) needDeleteContentList
                .get( needDeleteContentList.size() - 1 ) ).get( "contentId" );

            deleteDefFormContent( site, needDeleteContentList, modelId );

            needDeleteContentList = metaDataService.retrieveFormDataByIdTrace( modelId, prevCid,
                DELETE_QUERY_COUNT );
        }
    }

    /**
     * (会员)删除指定ID的自定义模型数据,包括附带资源,只可删除草稿，发布后的内容由管理员维护，会员无权管理,需要检查内容是否由会员创建
     * 
     * @param site
     * @param modelId
     * @param idList
     */
    public void deleteSystemAndUserDefineContentForMember( List idList )
    {

        // 检查内容是否由会员创建
        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        // 获取会员
        MemberBean memberUser = memberDao.querySingleMemberBeanById( ( Long ) session.getAuth()
            .getIdentity() );

        Long contentId = null;

        List memberContentIdList = new ArrayList();

        Map info = null;

        String creator = null;

        for ( int i = 0; i < idList.size(); i++ )
        {
            if( idList.get( i ) instanceof Long )
            {
                contentId = ( Long ) idList.get( i );
            }
            else
            {
                contentId = Long
                    .valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );
            }

            if( contentId.longValue() < 0 )
            {
                continue;
            }

            info = contentDao.querySingleContentMainInfo( contentId );

            if( Constant.WORKFLOW.CENSOR_STATUS_DRAFT.equals( info.get( "censorState" ) ) )
            {
                creator = ( String ) info.get( "creator" );

                if( creator != null && creator.equals( memberUser.getMemberName() ) )
                {
                    memberContentIdList.add( contentId );
                }
            }

        }

        deleteSystemAndUserDefineContent( memberContentIdList );
    }

    /**
     * 删除指定ID的自定义模型数据,包括附带资源
     * 
     * @param modelId
     * @param contentId
     */
    public void deleteSystemAndUserDefineContent( List idList )
    {
        log.info( "[SERVICE:] deleteSystemAndUserDefineContent : 将删除ID为:" + idList + "的数据" );

        if( idList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            ModelFiledInfoBean bean = null;

            Long contentId = null;

            Integer censor = null;

            String creator = null;

            Map main = null;

            Long modelId = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                if( idList.get( i ) instanceof Long )
                {
                    contentId = ( Long ) idList.get( i );
                }
                else
                {
                    contentId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ),
                        -1 ) );
                }

                if( contentId.longValue() < 0 )
                {
                    continue;
                }

                main = contentDao.querySingleContentMainInfo( contentId );

                modelId = ( Long ) main.get( "modelId" );

                if( modelId == null )
                {
                    // 若main不存在，则需要从trash中取
                    main = contentDao.querySingleTrashContentByContentId( contentId );

                    if( main.isEmpty() )
                    {
                        continue;
                    }

                    modelId = ( Long ) main.get( "modelId" );
                }

                if( main.isEmpty() )
                {
                    continue;
                }

                ModelPersistenceMySqlCodeBean sqlCodeBean = null;

                List modeFieldList = null;

                DataModelBean modelBean = null;

                if( modelId != null )
                {
                    sqlCodeBean = metaDataService.retrieveSingleModelPerMysqlCodeBean( modelId );

                    modeFieldList = metaDataService.retrieveModelFiledInfoBeanList( modelId );

                    modelBean = metaDataService.retrieveSingleDataModelBeanById( modelId );
                }

                Map mainAndDefInfo = null;

                if( modelBean == null )
                {
                    mainAndDefInfo = contentDao.querySingleTrashContentMainInfo( contentId );
                }
                else
                {
                    mainAndDefInfo = contentDao.querySingleTrashUserDefineContent( sqlCodeBean,
                        modelBean.getRelateTableName(), contentId );
                }

                // 若存在不是自己的稿件,不可删除
                creator = ( String ) mainAndDefInfo.get( "creator" );

                censor = ( Integer ) mainAndDefInfo.get( "censorState" );

                // 2015-5:回收删除不再限制
                // if( Constant.WORKFLOW.CENSOR_STATUS_DRAFT.equals( censor ) )
                // {
                // Auth auth = SecuritySessionKeeper.getSecuritySession()
                // .getAuth();
                //
                // if( auth != null && !creator.equals( auth.getApellation() ) )
                // {
                // continue;
                // }
                //
                // }

                // 删除核心数据
                contentDao.deleteTrashContentMainInfo( contentId );

                // 删除main info ,尽管trash过程已经删除，仍他调用方法兼容其他删除行为
                contentDao.deleteContentMainInfo( contentId );

                // 删除资源跟踪信息
                StatService.getInstance().deleteContentResInfo( contentId );

                if( modelBean != null )
                {
                    contentDao.deleteUserDefineInfo( modelBean, contentId );
                }

                // 不再使用,保留记录 contentDao.deleteContentStatus( contentId
                // );

                // 删除工作流审核数据

                workFlowDao.deleteWorkflowOperationByContentId( contentId, Constant.WORKFLOW.INFO_TYPE_CONTENT );

                // 第二次更新数据失败,需要全局回滚
                // 2014:已删除rollback,不影响数据完整,要保证其他数据删除正常,无需回滚
                // if( us.getRow() < 1 )
                // {
                // mysqlEngine.rollback();
                // }

                /**
                 * 删除主信息的引导图片信息
                 */
                resService.updateSiteResourceTraceUseStatus( Long.valueOf( StringUtil.getLongValue(
                    ( String ) mainAndDefInfo.get( "homeImageResId" ), -1 ) ), Constant.COMMON.OFF );

                resService.updateSiteResourceTraceUseStatus( Long.valueOf( StringUtil.getLongValue(
                    ( String ) mainAndDefInfo.get( "channelImageResId" ), -1 ) ),
                    Constant.COMMON.OFF );

                resService
                    .updateSiteResourceTraceUseStatus( Long.valueOf( StringUtil.getLongValue(
                        ( String ) mainAndDefInfo.get( "classImageResId" ), -1 ) ),
                        Constant.COMMON.OFF );

                resService.updateSiteResourceTraceUseStatus( Long.valueOf( StringUtil.getLongValue(
                    ( String ) mainAndDefInfo.get( "contentImageResId" ), -1 ) ),
                    Constant.COMMON.OFF );

                /**
                 * 删除资源文件以及信息 以下为资源类型字段
                 */
                if( modeFieldList != null )
                {
                    for ( int j = 0; j < modeFieldList.size(); j++ )
                    {
                        bean = ( ModelFiledInfoBean ) modeFieldList.get( j );

                        if( Constant.METADATA.UPLOAD_IMG == bean.getHtmlElementId().intValue() )
                        {
                            SiteResourceBean resBean = resService
                                .retrieveSingleResourceBeanByResId( Long.valueOf( StringUtil
                                    .getLongValue( ( String ) mainAndDefInfo.get( bean
                                        .getFieldSign()
                                        + "ResId" ), -1 ) ) );

                            if( resBean != null )
                            {
                                // 更新文件使用状态
                                resService.updateSiteResourceTraceUseStatus( resBean.getResId(),
                                    Constant.COMMON.OFF );
                            }
                        }
                        else if( Constant.METADATA.UPLOAD_MEDIA == bean.getHtmlElementId()
                            .intValue() )
                        {
                            SiteResourceBean resBean = resService
                                .retrieveSingleResourceBeanByResId( Long.valueOf( StringUtil
                                    .getLongValue( ( String ) mainAndDefInfo.get( bean
                                        .getFieldSign()
                                        + "ResId" ), -1 ) ) );

                            if( resBean != null )
                            {
                                // 更新文件使用状态
                                resService.updateSiteResourceTraceUseStatus( resBean.getResId(),
                                    Constant.COMMON.OFF );

                                String cover = StringUtil.isStringNull( resBean.getCover() ) ? ""
                                    : resBean.getCover();

                                if( StringUtil.isStringNotNull( resBean.getCover() ) )
                                {
                                    SiteResourceBean coverResBean = resService
                                        .retrieveSingleResourceBeanBySource( cover );

                                    if( coverResBean != null )
                                    {
                                        resService.updateSiteResourceTraceUseStatus( coverResBean
                                            .getResId(), Constant.COMMON.OFF );
                                    }
                                }
                            }
                        }
                        else if( Constant.METADATA.UPLOAD_FILE == bean.getHtmlElementId()
                            .intValue() )
                        {
                            SiteResourceBean resBean = resService
                                .retrieveSingleResourceBeanByResId( Long.valueOf( StringUtil
                                    .getLongValue( ( String ) mainAndDefInfo.get( bean
                                        .getFieldSign() ), -1 ) ) );

                            if( resBean != null )
                            {
                                // 更新文件使用状态
                                resService.updateSiteResourceTraceUseStatus( resBean.getResId(),
                                    Constant.COMMON.OFF );
                            }
                        }
                        else if( Constant.METADATA.UPLOAD_IMG_GROUP == bean.getHtmlElementId()
                            .intValue() )
                        {
                            List imageGroupList = contentDao.queryGroupPhotoInfoByContentId(
                                contentId, Constant.METADATA.MODEL_TYPE_CONTENT, null, true );

                            Map imageInfo = null;

                            for ( int k = 0; k < imageGroupList.size(); k++ )
                            {
                                imageInfo = ( Map ) imageGroupList.get( k );

                                SiteResourceBean resBean = resService
                                    .retrieveSingleResourceBeanByResId( Long.valueOf( StringUtil
                                        .getLongValue( ( String ) imageInfo.get( "resId" ), -1 ) ) );

                                if( resBean != null )
                                {
                                    // 更新文件使用状态
                                    resService.updateSiteResourceTraceUseStatus(
                                        resBean.getResId(), Constant.COMMON.OFF );
                                }

                            }

                        }
                        else if( Constant.METADATA.EDITER == bean.getHtmlElementId().intValue() )
                        {
                            ServiceUtil.disposeTextHaveSiteResId( null, ( String ) mainAndDefInfo
                                .get( bean.getFieldSign() ), new HashSet(), contentId, true );
                        }
                    }
                }

                // 根据模型删除对应辅助数据和资源
                if( modelBean != null
                    && Constant.METADATA.MODEL_RES_ARTICLE.equals( modelBean.getModelResType() ) )
                {
                    // 删除文章分页信息
                    contentDao.deleteContentAssistantPageInfoByContentId( contentId );
                }

                // 删除图集信息,并改动资源使用状态,如果有的话
                contentDao.deletePhotoGroupInfo( contentId, Constant.METADATA.MODEL_TYPE_CONTENT );

                // 删除复制信息数据,如果有的话
                Long refCid = ( Long ) mainAndDefInfo.get( "refCid" );
                Long classId = ( Long ) mainAndDefInfo.get( "classId" );

                contentDao.deleteContentAssistantCopyInfoByContentIdAndClassId( refCid, classId );

                // 删除共享信息
                contentDao.deleteShareContentInfo( contentId );

                contentDao.deleteShareContentTrace( contentId );

                // 增加删除索引任务
                SearchIndexContentState searchIndexState = new SearchIndexContentState();

                searchIndexState.setContentId( contentId );

                searchIndexState.setSiteId( ( Long ) main.get( "siteId" ) );

                searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_DEL );

                searchService.addIndexContentState( searchIndexState );

            }

            // 删除工作流记录
            workFlowDao.deleteWorkflowOperInfoByContentId( contentId, Constant.WORKFLOW.INFO_TYPE_CONTENT );

            workFlowDao.deleteWorkflowOpTraceByContentId( contentId, Constant.WORKFLOW.INFO_TYPE_CONTENT );

            // 删除关联内容
            contentDao.deleteRelateContentId( contentId );

            // 删除被关联内容
            contentDao.deleteRelateContentIdByInfoId( contentId );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            ContentDao.releaseAllCountCache();
            releaseContentCache();
        }

    }

    /**
     * 删除指定ID的表单数据,包括附带资源
     * 
     * @param modelId
     * @param contentId
     */
    public void deleteDefFormContent( SiteGroupBean site, List idList, Long modelId )
    {
        log.info( "[SERVICE:] deleteDefFormContent : 将删除ID为:" + idList + "的数据" );

        if( idList == null )
        {
            return;
        }

        DataModelBean modelBean = metaDataService.retrieveSingleDataModelBeanById( modelId );

        if( modelBean == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long contentId = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                if( idList.get( i ) instanceof Long )
                {
                    contentId = ( Long ) idList.get( i );
                }
                else if( idList.get( i ) instanceof Map )
                {
                    contentId = ( Long ) ( ( Map ) idList.get( i ) ).get( "contentId" );
                }
                else
                {
                    contentId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ),
                        -1 ) );
                }

                if( contentId.longValue() < 0 )
                {
                    continue;
                }

                /**
                 * 删除扩展数据
                 */
                metaDataService.deleteAndClearDefModelInfo( contentId, modelBean.getDataModelId(),
                    site.getSiteFlag() );

                // 删除核心数据
                metaDataDao.deleteFormDataMainById( contentId );

                contentDao.deleteUserDefineInfo( modelBean, contentId );

                // 删除图集信息,并改动资源使用状态,如果有的话
                contentDao.deletePhotoGroupInfo( contentId, Constant.METADATA.MODEL_TYPE_CONTENT );

                // 增加删除索引任务
                SearchIndexContentState searchIndexState = new SearchIndexContentState();

                searchIndexState.setContentId( contentId );

                searchIndexState.setSiteId( site.getSiteId() );

                searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_DEL );

                searchService.addIndexContentState( searchIndexState );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            MetaDataService.resetFormDataCache();
        }

    }

    public void sortContentAgTwo( Long targetId, Long nextId )
    {
        if( targetId == null || nextId == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Map targetContent = contentDao.querySingleContentMainInfo( targetId );
            Map nextContent = contentDao.querySingleContentMainInfo( nextId );

            // 需要替换的排序ID
            Long tmpOrderId = null;

            Long targetOrderFlag = ( Long ) targetContent.get( "orderIdFlag" );
            Long nextOrderFlag = ( Long ) nextContent.get( "orderIdFlag" );

            String directFlag = null;

            if( targetContent.get( "classId" ).equals( nextContent.get( "classId" ) ) )
            {

                if( targetOrderFlag.longValue() > nextOrderFlag.longValue() )
                {
                    directFlag = "great";
                    Map lastFlagArticle = contentDao.queryBigestOrderArticleByNextId(
                        ( Long ) nextContent.get( "orderIdFlag" ), ( Long ) targetContent
                            .get( "classId" ), directFlag );

                    tmpOrderId = ( Long ) lastFlagArticle.get( "orderIdFlag" );
                }
                else
                {

                    tmpOrderId = nextOrderFlag;
                }

                List sortInfoList = contentDao.queryContentOrderInfo( targetOrderFlag,
                    nextOrderFlag, ( Long ) targetContent.get( "classId" ), directFlag );

                Map info;
                Long id;
                Long nextOrderId;

                mysqlEngine.startBatch();
                for ( int i = 0; i < sortInfoList.size(); i++ )
                {
                    if( i + 1 == sortInfoList.size() )
                    {
                        break;
                    }

                    // 当前信息
                    info = ( Map ) sortInfoList.get( i );
                    id = ( Long ) info.get( "contentId" );

                    // 紧接着的下一条信息
                    info = ( Map ) sortInfoList.get( i + 1 );
                    nextOrderId = ( Long ) info.get( "orderIdFlag" );

                    contentDao.updateContentOrderId( id, nextOrderId );
                }

                // articleDao.updateArticleOrderIdByFlag( targetOrderFlag,
                // nextOrderFlag, ( Long ) targetArticle.get( "classId" ),
                // directFlag );

                // 更新关键信息
                contentDao.updateContentOrderId( ( Long ) targetContent.get( "contentId" ),
                    tmpOrderId );

                mysqlEngine.executeBatch();

            }
            else
            {
                log.warn( "当前参与排序的内容不属于同一栏目!" );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    /**
     * 对指定的内容进行排序，与栏目和内容模型无关
     * 
     * @param targetId
     * @param nextId
     */
    public void sortContent( Long targetId, Long nextId )
    {
        if( targetId == null || nextId == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Map targetContent = contentDao.querySingleContentMainInfo( targetId );
            Map nextContent = contentDao.querySingleContentMainInfo( nextId );

            Double nextOrderFlag = ( Double ) nextContent.get( "orderIdFlag" );

            // TODO
            // 1.判断目标数据位置当前的排序ID是否是系统原生
            // 若是原生,获取他的上一最大标志,若是整数,说明没有任何排序活动
            // 直接target+unit
            // 若不是原的话,取最小的非原生排序ID,将当前目标排序ID置换为这个最小的原生数
            // 存在大于0的小数部分则意味着系统原生排序ID
            // 更新所有比最小非原生排序ID+0.000001(包括最小的)

            Double newTargetOrderFlag = null;

            // 目标内容是原生的ID

            Map bigestContent = contentDao.queryBigestOrderContentByNextId( nextOrderFlag );

            newTargetOrderFlag = Double.valueOf( MathUtil.add( nextOrderFlag.doubleValue(),
                Constant.CONTENT.OREDER_UNIT ) );

            if( !bigestContent.isEmpty()
                && !( ( ( Integer ) bigestContent.get( "isSystemOrder" ) ).intValue() == Constant.CONTENT.IS_SYS_ORDER && ( ( Integer ) nextContent
                    .get( "isSystemOrder" ) ).intValue() == Constant.CONTENT.IS_SYS_ORDER ) )
            {
                // 已经有过排序活动

                // 取所有大于target的非原生记录
                List allBigestContent = contentDao.queryAllNotSysBigestOrderArticleByNextId(
                    nextOrderFlag, Long.valueOf( nextOrderFlag.longValue() ) );

                mysqlEngine.startBatch();

                Map contentMainInfo = null;
                for ( int i = 0; i < allBigestContent.size(); i++ )
                {
                    contentMainInfo = ( Map ) allBigestContent.get( i );

                    contentDao.updateContentOrderIdAndFlag( ( Long ) contentMainInfo
                        .get( "contentId" ), Double.valueOf( MathUtil.add(
                        ( ( Double ) contentMainInfo.get( "orderIdFlag" ) ).doubleValue(),
                        Constant.CONTENT.OREDER_UNIT ) ), Integer
                        .valueOf( Constant.CONTENT.NOT_SYS_ORDER ) );
                }
                mysqlEngine.executeBatch();
            }

            contentDao.updateContentOrderIdAndFlag( ( Long ) targetContent.get( "contentId" ),
                newTargetOrderFlag, Integer.valueOf( Constant.CONTENT.NOT_SYS_ORDER ) );

            // 若next和target的top标志不一致,则按照next的top标志设定target的top状态
            if( !nextContent.get( "topFlag" ).equals( targetContent.get( "topFlag" ) ) )
            {
                contentDao.updateContentTopFlag( ( Long ) targetContent.get( "contentId" ),
                    ( Integer ) nextContent.get( "topFlag" ) );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            ContentDao.releaseAllCountCache();
            releaseContentCache();
        }

    }

    /**
     * 批量更新模型数据静态化后的结果
     * 
     * @param endStaticURLInfoList
     */
    public void setContentStaticPageURL( List endStaticURLInfoList, SiteGroupBean site )
    {
        Object[] info = null;

        Long contentId = null;

        String endStaticClassFilePath = null;

        boolean isUpdate = false;

        try
        {
            mysqlEngine.beginTransaction();

            mysqlEngine.startBatch();

            for ( int endI = 0; endI < endStaticURLInfoList.size(); endI++ )
            {
                info = ( Object[] ) endStaticURLInfoList.get( endI );

                endStaticClassFilePath = ( String ) info[0];

                contentId = ( Long ) info[1];

                if( endStaticClassFilePath == null )
                {
                    continue;
                }

                ContentMainInfoBean mainInfo = contentDao
                    .querySingleContentMainInfoBean( contentId );

                if( mainInfo != null
                    && !endStaticClassFilePath.equals( mainInfo.getStaticPageUrl() ) )
                {
                    contentDao.updateContentStaticPageURL( endStaticClassFilePath, contentId );

                    isUpdate = true;
                }
            }

            mysqlEngine.executeBatch();

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            if( isUpdate )
            {
                ContentDao.releaseAllCountCache();

                releaseContentCache();
            }
        }

        // /**
        // * 分发同步
        // */
        // String filePath = null;
        //
        // SitePublishGatewayBean gwBean = null;
        //
        // SiteDispenseServerBean server = null;
        //
        // gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( site
        // .getSiteId(), Constant.SITE_CHANNEL.TRAN_TYPE_HTML );
        //
        // if( gwBean != null )
        // {
        // server = siteGroupDao
        // .querySingleSiteDispenseServerBeanByserverId( gwBean
        // .getTargetServerId() );
        //
        // if( server != null
        // && StringUtil.isStringNotNull( server.getServerUrl() ) )
        // {
        // // FTP和SFTP
        //
        // FTPClient ftp = FtpUtil.getFtpConnection( server.getServerIP(),
        // Integer.parseInt( server.getServerPort() ), server
        // .getLoginName(), server.getLoginPassword() );
        //
        // boolean connect = false;
        //
        // if( ftp != null )
        // {
        // connect = ftp.isConnected();
        // }
        //
        // try
        // {
        // if( connect )// 没有成功连接则无需进行传输操作
        // {
        // String rootPath = SystemConfiguration.getInstance()
        // .getSystemConfig().getSystemRealPath();
        //
        // String siteResRoot = site.getSiteRoot()
        // + File.separator + Constant.CONTENT.HTML_BASE;
        //
        // String siteRootPath = rootPath + siteResRoot;
        //
        // String file = null;
        //
        // String prefix = "";
        //
        // if( StringUtil
        // .isStringNotNull( InitSiteGroupInfoBehavior.currentCmsServerInfoBean
        // .getContext() ) )
        // {
        // prefix = File.separator
        // + InitSiteGroupInfoBehavior.currentCmsServerInfoBean
        // .getContext() + File.separator;
        // }
        //
        // for ( int endI = 0; endI < endStaticURLInfoList.size(); endI++ )
        // {
        // info = ( Object[] ) endStaticURLInfoList.get( endI );
        //
        // file = ( String ) info[2];
        //
        // filePath = siteRootPath + File.separator
        // + ( String ) info[2];
        //
        // FtpUtil.uploadFileFTP( ftp, prefix
        // + StringUtil.subString( file, 0, file
        // .lastIndexOf( File.separator ) ), null,
        // new File( filePath ) );
        //
        // status.setTranContentCurrent( Long.valueOf( status
        // .getTranContentCurrent().longValue() + 1 ) );
        // }
        // }
        // }
        // finally
        // {
        // FtpUtil.closeFtpConnection( ftp );
        // }
        // }
        // }

    }

    /**
     * 批量更新模型数据分页静态化后的结果
     * 
     * @param endStaticURLInfoList
     */
    public void setPageContentStaticURL( List endStaticURLInfoList )
    {

        Long contentId = null;

        String endStaticClassFilePath = null;

        Integer pagePos = null;

        boolean isUpdate = false;

        try
        {
            mysqlEngine.beginTransaction();

            mysqlEngine.startBatch();

            Object[] info = null;

            for ( int endI = 0; endI < endStaticURLInfoList.size(); endI++ )
            {
                info = ( Object[] ) endStaticURLInfoList.get( endI );

                endStaticClassFilePath = ( String ) info[0];

                contentId = ( Long ) info[1];

                pagePos = ( Integer ) info[2];

                if( endStaticClassFilePath == null )
                {
                    continue;
                }

                Map mainPageInfo = contentDao.queryContentAssistantPageInfoBeanByContentIdDataMode(
                    contentId, pagePos );

                if( mainPageInfo == null )
                {
                    continue;
                }

                if( !endStaticClassFilePath.equals( ( String ) mainPageInfo.get( "pageStaticUrl" ) ) )
                {
                    contentDao.updatePageContentStaticURL( endStaticClassFilePath, contentId,
                        pagePos );

                    isUpdate = true;
                }

            }

            mysqlEngine.executeBatch();

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            if( isUpdate )
            {
                ContentDao.releaseAllCountCache();

                releaseContentCache();
            }
        }

    }

    public Integer retrieveTrashContentCountByClassId( Long classId, Long modelId )
    {
        return contentDao.queryTrashContentCountByClassId( classId, modelId );
    }

    public List retrieveTrashContentByClassId( Long classId, Long modelId, Long startPos,
        Integer size )
    {
        return contentDao.queryTrashContentByClassId( classId, modelId, startPos, size );
    }

    public List retrieveTrashContentByTitleKey( String key, Long classId )
    {
        return contentDao.queryTrashContentByTitleKey( key, classId );
    }

    /**
     * 从web获取内容系统定义参数,普通edit模式不更新系统参数
     * 
     * @param editMode 若为edit模式,ID将放在返回List的最后位置
     * @param modelId
     * @param classBean
     * @param params
     * @return
     */
    private List getSystemModelValueFromWebParamOld( boolean editMode, Long modelId,
        ContentClassBean classBean, Map params, Map workValue )
    {
        List values = new ArrayList();

        /**
         * 必须严格按照系统字段顺序获取和合理处理值!!!
         */

        values.add( params.get( "classId" ) );
        values.add( params.get( "title" ) );
        // 创建者,为系统用户或会员
        Auth sysAuth = SecuritySessionKeeper.getSecuritySession().getAuth();
        if( sysAuth == null )
        {
            values.add( "匿名用户" );
        }
        else
        {
            values.add( ( String ) sysAuth.getApellation() );
        }

        // 作者,版权所有人
        values.add( params.get( "author" ) );

        // addTime
        if( !editMode )
        {
            values.add( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );
        }

        // clickCount
        values.add( Long.valueOf( 0 ) );

        // systemHandleTime
        values.add( DateAndTimeUtil.getNotNullTimestamp( null, DateAndTimeUtil.DEAULT_FORMAT_NANO )
            .toString() );

        // staticPageUrl
        values.add( "" );

        // produceType 根据栏目设定
        values.add( classBean.getContentProduceType() );

        // orderIdFlag
        if( !editMode )
        {
            values.add( Long.valueOf( 1 ) );
        }

        // tagKey
        values.add( params.get( "tagKey" ) );

        // appearStartDateTime
        // 当为空设定最小的时间,表示开始发布时间为立即

        String appearStartDateTime = ( String ) params.get( "appearStartDateTime" );
        Timestamp appearStartDateTS = null;
        if( StringUtil.isStringNull( appearStartDateTime ) )
        {
            values.add( Constant.CONTENT.MIN_DATE );
            appearStartDateTS = Constant.CONTENT.MIN_DATE;
        }
        else
        {
            appearStartDateTS = DateAndTimeUtil.getNotNullTimestamp( appearStartDateTime,
                DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );
            values.add( appearStartDateTS );
        }

        workValue.put( "appearStartDateTS", appearStartDateTS );

        // appearEndDateTime
        // 当为空设定最大的时间,表示结束发布时间为永远
        String appearEndDateTime = ( String ) params.get( "appearEndDateTime" );
        Timestamp appearEndDateTS = null;
        if( StringUtil.isStringNull( appearEndDateTime ) )
        {
            values.add( Constant.CONTENT.MAX_DATE );
            appearEndDateTS = Constant.CONTENT.MAX_DATE;
        }
        else
        {
            appearEndDateTS = DateAndTimeUtil.getNotNullTimestamp( appearEndDateTime,
                DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );
            values.add( appearEndDateTS );
        }

        workValue.put( "appearEndDateTS", appearEndDateTS );

        // censorState
        if( !editMode )
        {
            // 暂时为审核中状态,等待工作流处理逻辑根据工作流配置确定工作流状态
            values.add( Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW );
        }
        else
        {
            // 编辑模式
            values.add( WorkflowService.pendingCensorStateByStartAndEndPublishDate(
                appearStartDateTS, appearEndDateTS, Constant.WORKFLOW.CENSOR_STATUS_SUCCESS ) );
        }

        // topFlag
        String topFlag = ( String ) params.get( "topFlag" );
        if( Constant.COMMON.FLAG_IN_VAL.equals( topFlag ) )
        {
            values.add( Integer.valueOf( 1 ) );
        }
        else
        {
            values.add( Integer.valueOf( 0 ) );
        }

        // commendFlag
        String commendFlag = ( String ) params.get( "commendFlag" );
        if( Constant.COMMON.FLAG_IN_VAL.equals( commendFlag ) )
        {
            values.add( Integer.valueOf( 1 ) );
        }
        else
        {
            values.add( Integer.valueOf( 0 ) );
        }

        // hotFlag
        String hotFlag = ( String ) params.get( "hotFlag" );
        if( Constant.COMMON.FLAG_IN_VAL.equals( hotFlag ) )
        {
            values.add( Integer.valueOf( 1 ) );
        }
        else
        {
            values.add( Integer.valueOf( 0 ) );
        }

        // TODO 以上flag改动为推荐位定制,这样的话,首先要取推荐位元数据
        // 然后,根据元数据获取各位置是否存在，若存在,更新commend表数据

        // photoArticleType
        String photoArticleType = ( String ) params.get( "photoArticleType" );
        if( Constant.COMMON.FLAG_IN_VAL.equals( photoArticleType ) )
        {
            values.add( Integer.valueOf( 1 ) );
        }
        else
        {
            values.add( Integer.valueOf( 0 ) );
        }

        // videoType
        String videoType = ( String ) params.get( "videoType" );
        if( Constant.COMMON.FLAG_IN_VAL.equals( videoType ) )
        {
            values.add( Integer.valueOf( 1 ) );
        }
        else
        {
            values.add( Integer.valueOf( 0 ) );
        }

        // attachType
        String attachType = ( String ) params.get( "attachType" );
        if( Constant.COMMON.FLAG_IN_VAL.equals( attachType ) )
        {
            values.add( Integer.valueOf( 1 ) );
        }
        else
        {
            values.add( Integer.valueOf( 0 ) );
        }

        // isSystemOrder
        if( !editMode )
        {
            values.add( Integer.valueOf( 1 ) );
        }

        // homePageImgFlag
        String homePage = ( String ) params.get( "homeImage" );

        if( StringUtil.isStringNotNull( homePage ) )
        {
            values.add( homePage );

            values.add( Integer.valueOf( 1 ) );
        }
        else
        {
            values.add( homePage );
            values.add( Integer.valueOf( 0 ) );
        }

        // modelID
        values.add( modelId );

        if( editMode )
        {
            values.add( Long.valueOf( StringUtil.getLongValue(
                ( String ) params.get( "contentId" ), -1 ) ) );
        }

        return values;

    }

    /**
     * 从web获取内容系统定义参数,普通edit模式不更新系统参数
     * 
     * @param editMode
     * @param modelId
     * @param classBean
     * @param params
     * @return
     */
    @SuppressWarnings( "unchecked" )
    private ContentMainInfo getSystemModelValueFromWebParam( boolean editMode,
        ContentClassBean classBean, Map params, Map workValue, String mainEditorSign,
        SiteGroupBean site )
    {
        ContentMainInfo mainInfo = new ContentMainInfo();

        // classId
        mainInfo.setClassId( classBean.getClassId() );

        // refCid
        long refCid = StringUtil.getLongValue( ( String ) params.get( "refCid" ), -1 );

        if( refCid > 0 )
        {
            mainInfo.setRefCid( Long.valueOf( refCid ) );
        }

        // title
        mainInfo.setTitle( replcaeContentTextSensitive( ( String ) params.get( "title" ) ) );

        // simpleTitle
        mainInfo
            .setSimpleTitle( replcaeContentTextSensitive( ( String ) params.get( "simpleTitle" ) ) );

        // shortTitle
        mainInfo
            .setShortTitle( replcaeContentTextSensitive( ( String ) params.get( "shortTitle" ) ) );

        // titleStyle
        mainInfo.setTitleStyle( ( String ) params.get( "titleStyle" ) );

        // simpleTitleStyle
        mainInfo.setSimpleTitleStyle( ( String ) params.get( "simpleTitleStyle" ) );

        // addTime
        mainInfo.setAddTime( DateAndTimeUtil.getNotNullTimestamp(
            ( String ) params.get( "addTime" ), DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );

        // boost
        mainInfo.setBoost( StringUtil.getFloatValue( ( String ) params.get( "boost" ), 1.0f ) );
        // summary

        if( !editMode )
        {
            String summaryExist = ( String ) params.get( "summary" );

            if( StringUtil.isStringNotNull( summaryExist ) )
            {
                mainInfo.setSummary( replcaeContentTextSensitive( summaryExist ) );
            }
            else
            {
                if( StringUtil.isStringNotNull( ( String ) params.get( mainEditorSign ) ) )
                {
                    String text = Jsoup.clean( ( String ) params.get( mainEditorSign ), Whitelist
                        .none() );

                    text = StringUtil.replaceString( text, " ", "", false, false );

                    text = StringUtil.replaceString( text, "&nbsp;", "", false, false );

                    if( StringUtil.isStringNotNull( text ) )
                    {
                        if( ( text.length() + 1 <= site.getSummaryLength().intValue() )
                            && text.length() < 200 )
                        {
                            mainInfo.setSummary( replcaeContentTextSensitive( text ) );
                        }
                        else
                        {
                            mainInfo.setSummary( replcaeContentTextSensitive( StringUtil.subString(
                                text, 0, site.getSummaryLength().intValue() <= 200 ? site
                                    .getSummaryLength().intValue() : 200 ) ) );
                        }
                    }
                }
            }

        }
        else
        {
            mainInfo
                .setSummary( ( replcaeContentTextSensitive( ( String ) params.get( "summary" ) ) ) );
        }

        // 外部连接
        mainInfo.setOutLink( ( String ) params.get( "outLink" ) );

        // 创建或修改者,为系统用户或会员或填入的名称
        String creator = ( String ) params.get( "creator" );
        if( StringUtil.isStringNull( creator ) )
        {
            Auth sysAuth = SecuritySessionKeeper.getSecuritySession().getAuth();
            if( sysAuth == null )
            {
                mainInfo.setCreator( "匿名用户" );
            }
            else
            {
                mainInfo.setCreator( ( String ) sysAuth.getApellation() );
                mainInfo.setOrgCode( ( String ) sysAuth.getOrgCode() );
            }
        }
        else
        {
            mainInfo.setCreator( creator );

            if( SecuritySessionKeeper.getSecuritySession() != null
                && SecuritySessionKeeper.getSecuritySession().getAuth() != null )
            {

                mainInfo.setOrgCode( ( String ) SecuritySessionKeeper.getSecuritySession()
                    .getAuth().getOrgCode() );
            }
        }

        // 作者,版权所有人
        mainInfo.setAuthor( ( String ) params.get( "author" ) );

        // systemHandleTime 只要对数据更改,都必须改变这个值
        mainInfo.setSystemHandleTime( DateAndTimeUtil.getNotNullTimestamp( null,
            DateAndTimeUtil.DEAULT_FORMAT_NANO ).toString() );

        if( !editMode )
        {
            // produceType 根据栏目设定
            mainInfo.setProduceType( classBean.getContentProduceType() );

            // orderIdFlag
            mainInfo.setOrderIdFlag( Double.valueOf( 1 ) );

            // isSystemOrder
            mainInfo.setIsSystemOrder( Integer.valueOf( 1 ) );

            // otherFlag
            Integer of = ( Integer ) params.get( "otherFlag" );

            if( Constant.COMMON.ON.equals( of ) )
            {
                mainInfo.setOtherFlag( of );
            }
            else
            {
                mainInfo.setOtherFlag( Constant.COMMON.OFF );
            }
        }

        // tagKey
        mainInfo.setTagKey( ( String ) params.get( "tagKey" ) );

        // keywords
        mainInfo.setKeywords( replcaeContentTextSensitive( ( String ) params.get( "keywords" ) ) );

        // 关联内容
        mainInfo.setRelateIds( ( String ) params.get( "relateIds" ) );

        // 关联调查
        mainInfo.setRelateSurvey( ( String ) params.get( "relateSurvey" ) );

        // 点击数
        if( !editMode && site.getDefClickCount().intValue() > 0 )
        {
            mainInfo.setClickCount( Long.valueOf( new Random().nextInt( site.getDefClickCount()
                .intValue() ) + 1 ) );
        }

        // appearStartDateTime
        // 当为空设定当前时间,表示开始发布时间为立即发布,注意：为空时取时间，永远为服务器最新的时间为发布时间。若某一秒已被其他操作获取
        // 需要保持唯一性，即获取不同的新的一秒时间，要严格保持严格性
        String appearStartDateTime = ( String ) params.get( "appearStartDateTime" );

        Timestamp appearStartDateTS = null;

        String contentAddStatus = ( String ) params.get( "contentAddStatus" );

        if( Constant.WORKFLOW.DRAFT.equals( contentAddStatus ) )
        {
            appearStartDateTS = DateAndTimeUtil.getTodayTimestampDayAndTime();
        }
        else
        {

            if( StringUtil.isStringNull( appearStartDateTime ) )
            {
                if( !editMode )
                {
                    // 增加模式下 若为空,则立即发布,并立即设置置时间记录序列ID,静态化则需要静态更新URL

                    appearStartDateTS = DateAndTimeUtil.getTodayTimestampDayAndTime();

                    if( !Constant.WORKFLOW.DRAFT.equals( contentAddStatus ) )
                    {
                        mainInfo.setPubDateSysDT( getNextPublishOrderTrace() );
                    }
                }
                // 编辑模式下,不可能值为空,所以不会出现在空发布时间值处理逻辑中
            }
            else
            {
                // 填入时间则为将来发步,获取填入的时间并检查,若比当前时间小，则为当前时间,且立即发布,若为合法将来发布时间,则只记录时间,不设置时间记录序列ID,等待发布任务改动序列
                appearStartDateTS = DateAndTimeUtil.getNotNullTimestamp( appearStartDateTime,
                    DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );

                // 添加时,若所给时间小于等于现在时间,立即发布,并设定发布排序ID
                // 编辑时,若小于等于现在时间,时间为原来时间,不做改动,但若为工作流中,则改为当前时间
                Timestamp currentTime = DateAndTimeUtil.getTodayTimestampDayAndTime();

                if( appearStartDateTS.compareTo( currentTime ) < 1 )
                {
                    if( !editMode )
                    {
                        // 增加时,立即设置为现在时间,并设置发布排序ID,静态化则需要静态更新URL
                        appearStartDateTS = currentTime;

                        if( !Constant.WORKFLOW.DRAFT.equals( contentAddStatus ) )
                        {
                            mainInfo.setPubDateSysDT( getNextPublishOrderTrace() );
                        }
                    }
                    else
                    {
                        Integer prevCensorState = Integer.valueOf( ( String ) params
                            .get( "censorState" ) );
                        // TODO 下线后的状态要处理
                        if( Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( prevCensorState )
                            || Constant.WORKFLOW.CENSOR_STATUS_IN_EDIT.equals( prevCensorState )
                            || Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW.equals( prevCensorState ) )
                        {
                            // 若已经发布或由发布进入工作流,设定为原时间,原排序ID,静态化则需要静态更新URL
                            appearStartDateTS = DateAndTimeUtil.getNotNullTimestamp(
                                ( String ) params.get( "cmsSysOldPublishDateTime" ),
                                DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );

                            if( StringUtil.isStringNull( ( String ) params
                                .get( "cmsSysOldPublishDT" ) ) )
                            {
                                // 存在PublishDT不存在的情况,需要重新给予pubId
                                mainInfo.setPubDateSysDT( getNextPublishOrderTrace() );

                                if( Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW
                                    .equals( prevCensorState ) )
                                {
                                    // 之前在工作流中,但过期发布,需要更新发布日期
                                    mainInfo.setAppearStartDateTime( currentTime );
                                }

                            }
                            else
                            {
                                mainInfo.setPubDateSysDT( Long.valueOf( ( String ) params
                                    .get( "cmsSysOldPublishDT" ) ) );
                            }

                        }
                        else if( Constant.WORKFLOW.CENSOR_STATUS_WAIT_PUBLISH
                            .equals( prevCensorState )
                            || Constant.WORKFLOW.CENSOR_STATUS_DRAFT.equals( prevCensorState ) )
                        {
                            // 若为等待发布状态的将来时间变为现在时间,则需要立即发布,并更新发布排序ID,静态化则需要静态更新URL。
                            // 若为稿件状态进入直接发布，更新为现在时间。
                            appearStartDateTS = currentTime;

                            mainInfo.setPubDateSysDT( getNextPublishOrderTrace() );
                        }
                    }
                }
                else
                {
                    // 大于现在时间需要清除发布排序ID
                    mainInfo.setPubDateSysDT( null );
                }
                // 大于现在时间的待发布状态无论是增加还是更新操作直接设定发布时间,并清除排序ID,等待发布任务改动发布排序ID
            }

        }

        mainInfo.setAppearStartDateTime( appearStartDateTS );

        workValue.put( "appearStartDateTS", appearStartDateTS );

        // appearEndDateTime
        // 当为空设定最大的时间,表示结束发布时间为永远
        String appearEndDateTime = ( String ) params.get( "appearEndDateTime" );
        Timestamp appearEndDateTS = null;
        if( StringUtil.isStringNull( appearEndDateTime ) )
        {
            appearEndDateTS = Constant.CONTENT.MAX_DATE;
        }
        else
        {
            appearEndDateTS = DateAndTimeUtil.getNotNullTimestamp( appearEndDateTime,
                DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );
        }

        mainInfo.setAppearEndDateTime( appearEndDateTS );

        workValue.put( "appearEndDateTS", appearEndDateTS );

        // censorState

        if( Constant.WORKFLOW.DRAFT.equals( contentAddStatus ) )
        {
            mainInfo.setCensorState( Constant.WORKFLOW.CENSOR_STATUS_DRAFT );
        }
        else
        {
            if( !editMode )
            {
                // 第一次添加信息时,工作流处理逻辑根据工作流配置确定工作流状态
                // 若无工作流,根据发布开始结束时间确定发布状态

                if( Constant.WORKFLOW.DRAFT.equals( contentAddStatus ) )
                {
                    mainInfo.setCensorState( Constant.WORKFLOW.CENSOR_STATUS_DRAFT );
                }
                else
                {
                    mainInfo.setCensorState( WorkflowService
                        .pendingCensorStateByStartAndEndPublishDate( appearStartDateTS,
                            appearEndDateTS, Constant.WORKFLOW.CENSOR_STATUS_SUCCESS ) );
                }
            }
            else
            {

                Map currentInfo = contentDao.querySingleContentMainInfo( Long
                    .valueOf( StringUtil.getLongValue( ( String ) params
                        .get( Constant.METADATA.CONTENT_ID_NAME ), -1 ) ) );

                Integer censorState = null;

                if( params.get( "censorState" ) == null )
                {
                    censorState = ( Integer ) currentInfo.get( "censorState" );
                }
                else
                {
                    censorState = Integer.valueOf( ( String ) params.get( "censorState" ) );
                }

                if( Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( censorState )
                    || Constant.WORKFLOW.CENSOR_STATUS_WAIT_PUBLISH.equals( censorState )
                    || Constant.WORKFLOW.CENSOR_STATUS_WITHDRAW.equals( censorState ) )
                {
                    // 编辑模式,当为发布成功时,或为等待发布,或为已下线,则根据发布时间的调整更新审核状态
                    mainInfo.setCensorState( WorkflowService
                        .pendingCensorStateByStartAndEndPublishDate( appearStartDateTS,
                            appearEndDateTS, Constant.WORKFLOW.CENSOR_STATUS_SUCCESS ) );
                }
                else
                {
                    mainInfo.setCensorState( censorState );
                }
            }
        }

        // topFlag
        String topFlag = ( String ) params.get( "topFlag" );
        if( Constant.COMMON.FLAG_IN_VAL.equals( topFlag ) )
        {
            mainInfo.setTopFlag( Integer.valueOf( 1 ) );
        }
        else
        {
            mainInfo.setTopFlag( Integer.valueOf( 0 ) );
        }

        // typeFlag
        mainInfo.setTypeFlag( ( String ) params.get( "typeFlag" ) );

        // allowCommend
        String allowCommend = ( String ) params.get( "allowCommend" );

        // if( Constant.COMMON.FLAG_IN_VAL.equals( allowCommend ) )
        // 使用禁评论选项，则不选为允许评论
        if( !Constant.COMMON.FLAG_OUT_VAL.equals( allowCommend ) )
        {
            mainInfo.setAllowCommend( Integer.valueOf( 1 ) );
        }
        else
        {
            mainInfo.setAllowCommend( Integer.valueOf( 0 ) );
        }

        // especialTemplateUrl
        mainInfo.setEspecialTemplateUrl( ( String ) params.get( "especialTemplateUrl" ) );

        // homeImage and flag
        String homeImage = ( String ) params.get( "homeImage" );

        Long homeResId = Long.valueOf( StringUtil.getLongValue( homeImage, -1 ) );

        ServiceUtil.disposeOldImageInfo( homeResId, "homeImage", params );

        mainInfo.setHomeImage( ServiceUtil.disposeSingleImageInfo( homeResId ) );
        if( StringUtil.isStringNotNull( homeImage ) && !"-1".equals( homeImage ) )
        {
            mainInfo.setHomeImgFlag( Integer.valueOf( 1 ) );
        }
        else
        {
            mainInfo.setHomeImgFlag( Integer.valueOf( 0 ) );
        }

        // channelImage and flag
        String channelImage = ( String ) params.get( "channelImage" );

        Long chResId = Long.valueOf( StringUtil.getLongValue( channelImage, -1 ) );

        ServiceUtil.disposeOldImageInfo( chResId, "channelImage", params );

        mainInfo.setChannelImage( ServiceUtil.disposeSingleImageInfo( chResId ) );
        if( StringUtil.isStringNotNull( channelImage ) && !"-1".equals( channelImage ) )
        {
            mainInfo.setChannelImgFlag( Integer.valueOf( 1 ) );
        }
        else
        {
            mainInfo.setChannelImgFlag( Integer.valueOf( 0 ) );
        }

        // classImage and flag
        String classImage = ( String ) params.get( "classImage" );

        Long csResId = Long.valueOf( StringUtil.getLongValue( classImage, -1 ) );

        ServiceUtil.disposeOldImageInfo( csResId, "classImage", params );

        mainInfo.setClassImage( ServiceUtil.disposeSingleImageInfo( csResId ) );
        if( StringUtil.isStringNotNull( classImage ) && !"-1".equals( classImage ) )
        {
            mainInfo.setClassImgFlag( Integer.valueOf( 1 ) );
        }
        else
        {
            mainInfo.setClassImgFlag( Integer.valueOf( 0 ) );
        }

        // contentImage and flag
        String contentImage = ( String ) params.get( "contentImage" );

        Long cnResId = Long.valueOf( StringUtil.getLongValue( contentImage, -1 ) );

        ServiceUtil.disposeOldImageInfo( cnResId, "contentImage", params );

        mainInfo.setContentImage( ServiceUtil.disposeSingleImageInfo( cnResId ) );

        if( StringUtil.isStringNotNull( contentImage ) && !"-1".equals( contentImage ) )
        {
            mainInfo.setContentImgFlag( Integer.valueOf( 1 ) );
        }
        else
        {
            mainInfo.setContentImgFlag( Integer.valueOf( 0 ) );
        }

        // 是否为推荐类型
        mainInfo.setCommendFlag( Integer.valueOf( StringUtil.getIntValue( ( String ) params
            .get( "commendFlag" ), 0 ) ) );

        // modelID
        mainInfo.setModelId( classBean.getContentType() );

        if( editMode )
        {
            mainInfo.setContentId( StringUtil.getLongValue( ( String ) params.get( "contentId" ),
                -1 ) );
        }
        else
        {
            // siteId

            mainInfo.setSiteId( site.getSiteId() );
        }

        return mainInfo;

    }

    private ContentMainInfo changeDbMapToNewCopyContentMainVo( Map infpMap,
        ContentClassBean classBean )
    {
        // 注意:所有计数清空清空,重新根据目标栏目获取配置

        ContentMainInfo info = new ContentMainInfo();
        info.setContentId( ( Long ) infpMap.get( "contentId" ) );
        info.setModelId( ( Long ) infpMap.get( "modelId" ) );
        // 新的classId
        info.setClassId( classBean.getClassId() );
        info.setRefCid( ( Long ) infpMap.get( "contentId" ) );
        info.setTitle( ( String ) infpMap.get( "title" ) );
        info.setSimpleTitle( ( String ) infpMap.get( "simpleTitle" ) );
        info.setShortTitle( ( String ) infpMap.get( "shortTitle" ) );
        info.setTitleStyle( ( String ) infpMap.get( "titleStyle" ) );
        info.setSimpleTitleStyle( ( String ) infpMap.get( "simpleTitleStyle" ) );
        info.setAuthor( ( String ) infpMap.get( "author" ) );
        info.setCreator( ( String ) infpMap.get( "creator" ) );
        info.setOrgCode( ( String ) SecuritySessionKeeper.getSecuritySession().getAuth()
            .getOrgCode() );
        info.setSummary( ( String ) infpMap.get( "summary" ) );
        info.setOrderIdFlag( Double.valueOf( -1 ) );
        info.setBoost( ( Float ) infpMap.get( "boost" ) );
        info.setAddTime( ( Timestamp ) infpMap.get( "addTime" ) );

        // info.setClickMonthCount( ( Long ) infpMap.get( "clickMonthCount" ) );
        // info.setClickWeekCount( ( Long ) infpMap.get( "clickWeekCount" ) );
        // info.setClickDayCount( ( Long ) infpMap.get( "clickDayCount" ) );
        // info.setClickCount( ( Long ) infpMap.get( "clickCount" ) );
        // info.setCommMonthCount( ( Long ) infpMap.get( "commMonthCount" ) );
        // info.setCommWeekCount( ( Long ) infpMap.get( "commWeekCount" ) );
        // info.setCommDayCount( ( Long ) infpMap.get( "commDayCount" ) );
        // info.setCommCount( ( Long ) infpMap.get( "commCount" ) );
        // info.setSupportCount( ( Long ) infpMap.get( "supportCount" ) );
        // info.setAgainstCount( ( Long ) infpMap.get( "againstCount" ) );

        info.setHomeImgFlag( ( Integer ) infpMap.get( "homeImgFlag" ) );
        info.setClassImgFlag( ( Integer ) infpMap.get( "classImgFlag" ) );
        info.setChannelImgFlag( ( Integer ) infpMap.get( "channelImgFlag" ) );
        info.setContentImgFlag( ( Integer ) infpMap.get( "contentImgFlag" ) );

        String day = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        SiteGroupBean targetSite = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( classBean.getSiteFlag() );

        if( StringUtil.isStringNotNull( ( String ) infpMap.get( "homeImage" ) ) )
        {
            // 复制所有图片物理信息
            String resInfo = ServiceUtil.copyImageRes( ( String ) infpMap.get( "homeImage" ),
                targetSite, day, classBean.getClassId() );

            info.setHomeImage( resInfo );
        }

        if( StringUtil.isStringNotNull( ( String ) infpMap.get( "classImage" ) ) )
        {
            // 复制所有图片物理信息
            String resInfo = ServiceUtil.copyImageRes( ( String ) infpMap.get( "classImage" ),
                targetSite, day, classBean.getClassId() );

            info.setClassImage( resInfo );
        }

        if( StringUtil.isStringNotNull( ( String ) infpMap.get( "channelImage" ) ) )
        {
            // 复制所有图片物理信息
            String resInfo = ServiceUtil.copyImageRes( ( String ) infpMap.get( "channelImage" ),
                targetSite, day, classBean.getClassId() );

            info.setChannelImage( resInfo );
        }

        if( StringUtil.isStringNotNull( ( String ) infpMap.get( "contentImage" ) ) )
        {
            // 复制所有图片物理信息
            String resInfo = ServiceUtil.copyImageRes( ( String ) infpMap.get( "contentImage" ),
                targetSite, day, classBean.getClassId() );

            info.setContentImage( resInfo );
        }

        info.setSystemHandleTime( DateAndTimeUtil.getNotNullTimestamp( null,
            DateAndTimeUtil.DEAULT_FORMAT_NANO ).toString() );
        info.setEspecialTemplateUrl( ( String ) infpMap.get( "especialTemplateUrl" ) );
        // 静态页地址不可复制 info.setStaticPageUrl( ( String )
        // infpMap.get("staticPageUrl" ) );

        // 静态化方式根据新的栏目获取配置
        info.setProduceType( classBean.getContentProduceType() );
        // 暂时为在工作流中,等待后面的工作流处理
        info.setCensorState( Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW );
        info.setIsPageContent( ( Integer ) infpMap.get( "isPageContent" ) );
        info.setIsSystemOrder( ( Integer ) infpMap.get( "isSystemOrder" ) );
        info.setKeywords( ( String ) infpMap.get( "keywords" ) );
        info.setTagKey( ( String ) infpMap.get( "tagKey" ) );
        // info.setRelateIds( ( String ) infpMap.get( "relateIds" ) );
        // 以上代码已注释，业务改变不再存储关联id字段
        info.setRelateIds( "" );
        info.setRelateSurvey( ( String ) infpMap.get( "relateSurvey" ) );
        info.setAppearStartDateTime( DateAndTimeUtil.getTodayTimestampDayAndTime() );// 当前时间
        info.setAppearEndDateTime( Constant.CONTENT.MAX_DATE );// 最大

        // 推荐位为空
        info.setCommendFlag( ( Integer ) infpMap.get( "commendFlag" ) );

        // 分类属性根据新栏目,现在清空 info.setTypeFlag( "" );

        // 设定为非 top info.setTopFlag( ( Integer ) infpMap.get( "topFlag" ) );

        // 评论配置根据新栏目
        info.setAllowCommend( classBean.getOpenComment() );
        info.setSiteId( ( Long ) infpMap.get( "siteId" ) );

        return info;
    }

    public void setContentTopFlag( List idList, String topFlag )
    {
        try
        {
            Long contentId = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                if( idList.get( i ) instanceof Long )
                {
                    contentId = ( Long ) idList.get( i );
                }
                else
                {
                    contentId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ),
                        -1 ) );
                }

                if( contentId.longValue() < 0 )
                {
                    continue;
                }

                if( "up".equals( topFlag ) )
                {
                    contentDao.updateContentTopFlag( contentId, Constant.COMMON.ON );
                }
                else if( "down".equals( topFlag ) )
                {
                    contentDao.updateContentTopFlag( contentId, Constant.COMMON.OFF );
                }
            }
        }
        finally
        {
            ContentDao.releaseAllCountCache();

            releaseContentCache();
        }

    }

    public Long retrieveAllInWorkflowUserDefineContentCount( Auth auth )
    {
        if( auth == null || !auth.isAuthenticated() )
        {
            return Long.valueOf( 0 );
        }

        String sqlOrCond = StringUtil.replaceString( auth.getRoleSqlHelper().getAllRoleOrQuery( "",
            "auditManId" ), "auditManId", "wa.auditManId" );

        Long result = null;

        try
        {
            mysqlEngine.beginTransaction();

            result = contentDao.queryAllInWorkflowUserDefineContentCount( sqlOrCond, ( Long ) auth
                .getIdentity(), ( Long ) auth.getOrgIdentity() );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        return result;
    }

    /**
     * 根据当前用户获取对应的审核文章
     * 
     * @param sysAuth
     * @return
     */
    public List<WorkflowOperationBean> retrieveAllInWorkflowUserDefineContent( Auth auth, Long start, Integer size )
    {
        if( auth == null || !auth.isAuthenticated() )
        {
            return null;
        }

        String sqlOrCond = StringUtil.replaceString( auth.getRoleSqlHelper().getAllRoleOrQuery( "",
            "auditManId" ), "auditManId", "wa.auditManId" );

        List<WorkflowOperationBean> result = new ArrayList<WorkflowOperationBean>();

        try
        {
            mysqlEngine.beginTransaction();

            List<WorkflowOperationBean> tmpResult = contentDao.queryAllInWorkflowUserDefineContent( sqlOrCond, ( Long ) auth
                .getIdentity(), ( Long ) auth.getOrgIdentity(), start, size );
            
            for(WorkflowOperationBean wo : tmpResult)
            {
                WorkflowStepInfoBean ws =  workFlowDao.querySingleWorkflowStepBeanByFlowIdAndStep( wo.getCurrentStep() );
                
                if(ws != null && Constant.COMMON.ON.equals( ws.getOrgMode() ) )
                {
                    ContentMainInfoBean info = contentDao.querySingleContentMainInfoBean( wo.getContentId() );
                    
                    if(!info.getOrgCode().equals( auth.getOrgCode().toString() ))
                    {
                        continue;
                    }
                }
                
                result.add( wo );
            }
                        
            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        return result;
    }

    /**
     * 获取被退稿的文章
     * 
     * @param sysAuth
     * @return
     */
    public List retrieveInWorkflowUserDefinePersonalRejectContent( Auth auth )
    {
        if( auth == null || !auth.isAuthenticated() )
        {
            return null;
        }

        return contentDao.queryInWorkflowUserDefinePersonalRejectContent( ( String ) auth
            .getApellation() );
    }

    /**
     * 获取工作流中内容当前处理信息
     * 
     * @param contentId
     * @return
     */
    public WorkflowOperationBean retrieveSingleWorkflowContentProcessInfo( Long contentId )
    {
        return contentDao.querySingleWorkflowContentProcessBean( contentId );
    }

    /**
     * 获取指定内容的附属图集
     * 
     * @param contentId
     * @param siteBean
     * @return
     */
    public List retrieveGroupPhotoInfoByContentId( Long contentId, String group, Integer modelType,
        SiteGroupBean siteBean, boolean serverMode )
    {

        if( siteBean == null )
        {
            return new ArrayList( 1 );
        }

        String key = "retrieveGroupPhotoInfoByContentId:" + contentId + "|" + group + "|"
            + modelType + "|" + siteBean.getSiteId() + "|" + serverMode;

        List result = ( List ) singleContentCache.getEntry( key );

        if( result == null )
        {
            if( StringUtil.isStringNull( group ) )
            {
                result = contentDao.queryGroupPhotoInfoByContentId( contentId, modelType, siteBean,
                    serverMode );
            }
            else
            {
                result = contentDao.queryGroupPhotoInfoByContentId( contentId, group, modelType,
                    siteBean, serverMode );
            }

            singleContentCache.putEntry( key, result );
        }

        return result;
    }

    public List retrieveAllContentPageByIds( String query, Long modelId )
    {
        DataModelBean modelBean = metaDataService.retrieveSingleDataModelBeanById( modelId );

        if( modelBean == null || StringUtil.isStringNull( query ) )
        {
            return Collections.EMPTY_LIST;
        }

        ModelPersistenceMySqlCodeBean sqlBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( modelId );

        return contentDao.queryAllContentPageByIds( query, modelBean, sqlBean );
    }

    public List retrieveAllContentAssistantPageInfoByContentId( Long contentId, Map info,
        ContentClassBean classBean, SiteGroupBean site )
    {
        String key = "retrieveAllContentAssistantPageInfoByContentId:" + contentId;

        List resList = ( List ) singleContentCache.getEntry( key );

        if( resList == null )
        {
            resList = contentDao.queryContentAssistantPageInfoBeanNotIncludeTextByContentId(
                contentId, info, classBean, site );

            singleContentCache.putEntry( key, resList );
        }

        return resList;
    }

    /**
     * 推荐内容到指定推荐位
     * 
     * @param contentIdArrayList
     * @param idArrayList
     */
    public void commendContentInfo( List contentIdArrayList, List idArrayList, boolean isSpec )
    {
        if( contentIdArrayList == null || idArrayList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            // mysqlEngine.startBatch();

            Long contentId = null;

            Long commendTypeId = null;

            ContentClassBean classBean = null;

            Map mainInfo = null;

            ContentCommendTypeBean commTypeBean = null;

            ContentCommendPushInfo pushInfo = null;

            String siteFlag = ( ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo() ).getSiteFlag();

            // List commendTypeIdList = new ArrayList();
            //
            // if( isSpec )
            // {
            // // 取专题下所有分类
            // Long specId = null;
            //
            // for ( int i = 0; i < idArrayList.size(); i++ )
            // {
            // specId = Long.valueOf( StringUtil.getLongValue(
            // ( String ) idArrayList.get( i ), -1 ) );
            //
            // if( specId.longValue() < 0 )
            // {
            // continue;
            // }
            //
            // commendTypeIdList.addAll( channelService
            // .retrieveContentCommendTypeBean( siteFlag, specId,
            // false, true, true ) );
            // }
            //
            // idArrayList = commendTypeIdList;
            // }

            for ( int j = 0; j < idArrayList.size(); j++ )
            {
                if( idArrayList.get( j ) instanceof String )
                {
                    commendTypeId = Long.valueOf( StringUtil.getLongValue( ( String ) idArrayList
                        .get( j ), -1 ) );
                }
                else
                {
                    commTypeBean = ( ( ContentCommendTypeBean ) idArrayList.get( j ) );

                    commendTypeId = commTypeBean.getCommendTypeId();

                }

                if( commendTypeId.longValue() < 0 )
                {
                    continue;
                }

                commTypeBean = channelDao.querySingleContentCommendTypeBeanByTypeId( commendTypeId );

                if( commTypeBean == null )
                {
                    continue;
                }

                int num = 0;

                // 原存在的推荐数据
                List existCommendInfoList = contentDao.queryAllCommendContentByCommendTypeByFlag(
                    commTypeBean.getCommFlag(), siteFlag, false );

                for ( int i = 0; i < contentIdArrayList.size(); i++ )
                {
                    contentId = Long.valueOf( StringUtil.getLongValue(
                        ( String ) contentIdArrayList.get( i ), -1 ) );

                    if( contentId.longValue() < 0 )
                    {
                        continue;
                    }

                    mainInfo = contentDao.querySingleContentMainInfo( contentId );

                    if( mainInfo == null )
                    {
                        continue;
                    }

                    classBean = channelService
                        .retrieveSingleClassBeanInfoByClassId( ( Long ) mainInfo.get( "classId" ) );

                    if( classBean == null || classBean.getClassId().longValue() < 0 )
                    {
                        continue;
                    }

                    if( Constant.COMMON.ON.equals( commTypeBean.getMustCensor() ) )
                    {
                        // 进入编辑推荐

                        Long count = contentDao.queryCommendPushTempCount( contentId, commTypeBean
                            .getCommendTypeId() );

                        if( count.longValue() < 1 )
                        {
                            contentDao.saveCommendPushTemp( contentId, commTypeBean
                                .getCommendTypeId(), commTypeBean.getCommFlag() );

                            // contentDao.updateCommendFlagByContentId(
                            // contentId,
                            // Constant.COMMON.ON );
                        }
                    }
                    else
                    {
                        // 直接出现在推荐位

                        num++;

                        // int maxNum = contentDao
                        // .queryCommendContentMaxRowFlagByCommFlag(
                        // commTypeBean.getCommFlag() ).intValue();

                        // maxNum++;

                        pushInfo = new ContentCommendPushInfo();

                        pushInfo.setAddTime( DateAndTimeUtil.getTodayTimestampDayAndTime() );
                        pushInfo.setClassId( classBean.getClassId() );
                        pushInfo.setCommendFlag( commTypeBean.getCommFlag() );
                        pushInfo.setCommendTypeId( commTypeBean.getCommendTypeId() );
                        pushInfo.setContentId( contentId );

                        pushInfo.setImg( ServiceUtil.disposeSingleImageInfo( Long
                            .valueOf( StringUtil.getLongValue( ( String ) mainInfo
                                .get( "contentImageSysReUrl" ), -1 ) ) ) );

                        pushInfo.setModelId( ( Long ) mainInfo.get( "modelId" ) );
                        pushInfo.setSummary( ( String ) mainInfo.get( "summary" ) );
                        pushInfo.setTitle( ( String ) mainInfo.get( "title" ) );

                        pushInfo.setUrl( Constant.CONTENT.GEN_CONTENT_URL_PREFIX
                            + mainInfo.get( "contentId" ) );

                        // 设定推荐人
                        Auth sysAuth = SecuritySessionKeeper.getSecuritySession().getAuth();

                        pushInfo.setCommendMan( ( String ) sysAuth.getApellation() );

                        // 设定站点
                        pushInfo.setSiteFlag( siteFlag );

                        pushInfo.setRowFlag( Long.valueOf( 0 ) );// 暂时替代,因不可为空值

                        pushInfo.setRowOrder( Integer.valueOf( 1 ) );

                        UpdateState us = contentDao.saveCommendContent( pushInfo );

                        if( us.haveKey() )
                        {
                            contentDao.updateCommendPushContentOrderInfoByInfoId( Long
                                .valueOf( num ), Integer.valueOf( 1 ), Long.valueOf( us.getKey() ) );

                            contentDao.updateCommendFlagByContentId( contentId, Constant.COMMON.ON );
                        }

                    }
                }

                // 更新已有的数据的排位

                if( num > 0 )
                {
                    // 没有新数据加入,以下逻辑不需要执行
                    ContentCommendPushInfoBean commInfoBean = null;

                    List rowBeanInnerList = null;

                    for ( int y = 0; y < existCommendInfoList.size(); y++ )
                    {
                        num++;

                        commInfoBean = ( ContentCommendPushInfoBean ) existCommendInfoList.get( y );

                        contentDao.updateCommendPushContentRowFlagByInfoId( Long.valueOf( num ),
                            commInfoBean.getInfoId() );

                        rowBeanInnerList = commInfoBean.getRowInfoList();

                        for ( int x = 0; x < rowBeanInnerList.size(); x++ )
                        {
                            commInfoBean = ( ContentCommendPushInfoBean ) rowBeanInnerList.get( x );
                            contentDao.updateCommendPushContentRowFlagByInfoId(
                                Long.valueOf( num ), commInfoBean.getInfoId() );
                        }
                    }
                }

            }

            // mysqlEngine.executeBatch();
            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            releaseContentCache();
            ContentDao.releaseAllCountCache();
        }
    }

    public void addSingleCommendInfo( Map params, ContentCommendPushInfo commInfo, Long rowFlag,
        Integer rowIndex, boolean inCol, String siteFlag, String commendFlag )
    {
        if( commInfo == null || rowFlag == null || rowFlag == null || rowIndex == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            ContentCommendTypeBean commTypeBean = channelDao
                .querySingleContentCommendTypeBeanByTypeId( commInfo.getCommendTypeId() );

            if( commTypeBean == null )
            {
                return;
            }

            if( inCol )// 在某一行中增加
            {
                // 获取增加新内容之前行最大order
                Integer maxOrder = contentDao.queryCommendContentMaxRowOrderByRowFlag( rowFlag,
                    commInfo.getCommendFlag() );

                if( maxOrder == null )
                {
                    // 表示加入了新的最后一行
                    maxOrder = Integer.valueOf( 0 );
                }

                if( rowIndex.intValue() == -1 )
                {
                    rowIndex = Integer.valueOf( maxOrder.intValue() + 1 );
                }

                Integer pos = Integer.valueOf( ( maxOrder.intValue() + 2 ) - rowIndex.intValue() );

                // 增加新的info
                commInfo.setRowFlag( rowFlag );
                commInfo.setRowOrder( pos );
                commInfo.setAddTime( DateAndTimeUtil.getTodayTimestampDayAndTime() );

                // 图片信息处理
                commInfo.setImg( ServiceUtil.disposeSingleImageInfo( Long.valueOf( StringUtil
                    .getLongValue( commInfo.getImg(), -1 ) ) ) );

                commInfo.setCommendFlag( commTypeBean.getCommFlag() );
                commInfo.setCommendTypeId( commTypeBean.getCommendTypeId() );

                UpdateState us = contentDao.saveCommendContent( commInfo );

                if( us.haveKey() && maxOrder.intValue() > 0 )
                {
                    commInfo.setInfoId( us.getKey() );

                    contentDao.updateCommendPushContentRowOrder( commInfo.getCommendFlag(),
                        rowFlag, pos, Long.valueOf( us.getKey() ) );

                    if( commInfo.getContentId() != null )
                    {
                        contentDao.updateCommendFlagByContentId( commInfo.getContentId(),
                            Constant.COMMON.ON );
                    }
                }
            }
            else
            {
                List rowBeanAllList = contentDao.queryAllCommendContentByCommendTypeByFlag(
                    commInfo.getCommendFlag(), siteFlag, false );

                // 增加新的行,新的info
                commInfo.setRowFlag( rowFlag );
                commInfo.setRowOrder( Integer.valueOf( 1 ) );
                commInfo.setAddTime( DateAndTimeUtil.getTodayTimestampDayAndTime() );

                // 图片信息处理
                commInfo.setImg( ServiceUtil.disposeSingleImageInfo( Long.valueOf( StringUtil
                    .getLongValue( commInfo.getImg(), -1 ) ) ) );

                commInfo.setCommendFlag( commTypeBean.getCommFlag() );
                commInfo.setCommendTypeId( commTypeBean.getCommendTypeId() );

                UpdateState us = contentDao.saveCommendContent( commInfo );

                if( us.haveKey() )
                {
                    commInfo.setInfoId( us.getKey() );

                    if( commInfo.getContentId() != null )
                    {
                        contentDao.updateCommendFlagByContentId( commInfo.getContentId(),
                            Constant.COMMON.ON );
                    }

                    ContentCommendPushInfoBean rowInfoBean = null;
                    List rowBeanInnerList = null;
                    int num = 0;

                    for ( int i = 0; i < rowBeanAllList.size(); i++ )
                    {
                        num = i + 1;

                        rowInfoBean = ( ContentCommendPushInfoBean ) rowBeanAllList.get( i );

                        if( rowInfoBean.getRowFlag().intValue() < rowFlag.intValue() )// 小于新行的无需更新,大于等于新行的需要重新编号
                        {
                            continue;
                        }

                        contentDao.updateCommendPushContentRowFlagByInfoId(
                            Long.valueOf( num + 1 ), rowInfoBean.getInfoId() );

                        rowBeanInnerList = rowInfoBean.getRowInfoList();
                        for ( int j = 0; j < rowBeanInnerList.size(); j++ )
                        {
                            rowInfoBean = ( ContentCommendPushInfoBean ) rowBeanInnerList.get( j );
                            contentDao.updateCommendPushContentRowFlagByInfoId( Long
                                .valueOf( num + 1 ), rowInfoBean.getInfoId() );
                        }
                    }
                }
            }

            if( "pushMode".equals( commendFlag ) )
            {
                contentDao.deleteCommendPushTemp( commInfo.getContentId(), commTypeBean
                    .getCommendTypeId() );
            }

            /**
             * 扩展模型数据
             */

            metaDataService.addOrEditDefModelInfo( params, commTypeBean.getModelId(), commInfo
                .getInfoId(), Constant.METADATA.MODEL_TYPE_COMMEND );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            releaseContentCache();
            ContentDao.releaseAllCountCache();

        }
    }

    public void addMutiCommendInfoForContent( String commFlag, List contentIdList,
        String commendMan, String siteFlag, String commendFlag, String action )
    {
        try
        {
            mysqlEngine.beginTransaction();

            ContentCommendTypeBean commTypeBean = channelDao
                .querySingleContentCommendTypeBeanByCommFlag( commFlag );

            if( commTypeBean == null )
            {
                return;
            }

            List contentMainInfoList = new ArrayList();

            Long contentId = null;
            for ( int i = 0; i < contentIdList.size(); i++ )
            {
                contentId = Long.valueOf( StringUtil.getLongValue(
                    ( String ) contentIdList.get( i ), -1 ) );

                if( contentId.longValue() < 0 )
                {
                    continue;
                }

                contentMainInfoList.add( contentDao.querySingleContentMainInfoBean( contentId ) );

                contentDao.updateCommendFlagByContentId( contentId, Constant.COMMON.ON );
            }

            List existCommendInfoList = contentDao.queryAllCommendContentByCommendTypeByFlag(
                commFlag, siteFlag, false );

            int num = 0;

            ContentCommendPushInfo commInfo = null;

            ContentMainInfoBean contentInfoBean = null;

            int firstAddNum = 0;

            // 增加新的推荐数据
            for ( int i = 0; i < contentMainInfoList.size(); i++ )
            {
                num++;

                if( "line".equals( action ) && i == 0 )
                {
                    // 记录第一个num
                    firstAddNum = num;
                }

                contentInfoBean = ( ContentMainInfoBean ) contentMainInfoList.get( i );

                // 增加新的行,新的info

                commInfo = new ContentCommendPushInfo();

                if( "line".equals( action ) )
                {
                    commInfo.setRowFlag( Long.valueOf( firstAddNum ) );

                    commInfo.setRowOrder( Integer.valueOf( num ) );
                }
                else
                {
                    commInfo.setRowFlag( Long.valueOf( num ) );
                    commInfo.setRowOrder( Integer.valueOf( 1 ) );
                }

                commInfo.setAddTime( DateAndTimeUtil.getTodayTimestampDayAndTime() );

                commInfo.setContentId( contentInfoBean.getContentId() );
                commInfo.setClassId( contentInfoBean.getClassId() );
                commInfo.setModelId( contentInfoBean.getModelId() );
                commInfo.setTitle( contentInfoBean.getTitle() );
                commInfo.setSummary( contentInfoBean.getSummary() );
                commInfo.setUrl( Constant.CONTENT.GEN_CONTENT_URL_PREFIX
                    + contentInfoBean.getContentId() );

                // 图片信息处理
                commInfo.setImg( ServiceUtil.disposeSingleImageInfo( Long.valueOf( StringUtil
                    .getLongValue( contentInfoBean.getContentImage(), -1 ) ) ) );

                commInfo.setCommendFlag( commFlag );
                commInfo.setCommendTypeId( commTypeBean.getCommendTypeId() );
                commInfo.setCommendMan( commendMan );
                commInfo.setSiteFlag( siteFlag );

                contentDao.saveCommendContent( commInfo );

            }

            // 更新已有的数据的排位

            if( num == 0 )
            {
                // 没有新数据加入,以下逻辑不需要执行
                return;
            }

            ContentCommendPushInfoBean commInfoBean = null;

            List rowBeanInnerList = null;

            if( "line".equals( action ) )
            {
                num = firstAddNum;
            }

            for ( int j = 0; j < existCommendInfoList.size(); j++ )
            {
                num++;

                commInfoBean = ( ContentCommendPushInfoBean ) existCommendInfoList.get( j );

                contentDao.updateCommendPushContentRowFlagByInfoId( Long.valueOf( num ),
                    commInfoBean.getInfoId() );

                rowBeanInnerList = commInfoBean.getRowInfoList();

                for ( int x = 0; x < rowBeanInnerList.size(); x++ )
                {
                    commInfoBean = ( ContentCommendPushInfoBean ) rowBeanInnerList.get( x );
                    contentDao.updateCommendPushContentRowFlagByInfoId( Long.valueOf( num ),
                        commInfoBean.getInfoId() );
                }
            }

            // 删除temp表
            if( "pushMode".equals( commendFlag ) )
            {
                for ( int i = 0; i < contentIdList.size(); i++ )
                {
                    contentId = Long.valueOf( StringUtil.getLongValue( ( String ) contentIdList
                        .get( i ), -1 ) );

                    if( contentId.longValue() < 0 )
                    {
                        continue;
                    }

                    contentDao.deleteCommendPushTemp( contentId, commTypeBean.getCommendTypeId() );
                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            releaseContentCache();
            ContentDao.releaseAllCountCache();

        }
    }

    /**
     * tag查询编辑推荐信息
     * 
     * @param commTypeId
     * @return
     */
    public List getCommendContentTempQueryTag( String commTypeId )
    {
        List result = null;

        Long ctId = Long.valueOf( StringUtil.getLongValue( commTypeId, -1 ) );

        if( ctId.longValue() < 1 )
        {
            return result;
        }

        result = contentDao.queryCommendPushTemp( ctId );

        return result;
    }

    public void editSingleCommendInfo( Map params, ContentCommendPushInfo commInfo )
    {
        try
        {
            // 图片信息处理
            commInfo.setImg( ServiceUtil.disposeSingleImageInfo( Long.valueOf( StringUtil
                .getLongValue( commInfo.getImg(), -1 ) ) ) );

            ContentCommendTypeBean commTypeBean = channelDao
                .querySingleContentCommendTypeBeanByTypeId( commInfo.getCommendTypeId() );

            if( commTypeBean == null )
            {
                return;
            }

            contentDao.updateCommendPushInfoByInfoId( commInfo );

            /**
             * 扩展模型数据
             */

            metaDataService.addOrEditDefModelInfo( params, commTypeBean.getModelId(), commInfo
                .getInfoId(), Constant.METADATA.MODEL_TYPE_COMMEND );

        }
        finally
        {
            releaseContentCache();
            ContentDao.releaseAllCountCache();

        }

    }

    public void deleteCommendContentColumnInfo( String commFlag, List rowFlagArrayList,
        String siteFlag )
    {
        try
        {
            mysqlEngine.beginTransaction();

            List commList = null;

            if( rowFlagArrayList == null )
            {
                // 为空 ,取commFlag全部,删除所有图片
                commList = contentDao.queryCommendRowInfoByCommFlag( commFlag, siteFlag );
            }
            else
            {
                commList = contentDao.queryCommendRowInfoByCommFlagAndRowFlag( rowFlagArrayList,
                    commFlag, siteFlag );
            }

            long rowFlag = -1;

            Set excludeRowFlagSet = new HashSet();

            if( rowFlagArrayList != null )
            {
                for ( int i = 0; i < rowFlagArrayList.size(); i++ )
                {
                    rowFlag = StringUtil.getLongValue( ( String ) rowFlagArrayList.get( i ), -1 );

                    if( rowFlag < 0 )
                    {
                        continue;
                    }

                    excludeRowFlagSet.add( Long.valueOf( rowFlag ) );
                }
            }

            // 删除图片
            ContentCommendPushInfoBean bean = null;

            for ( int i = 0; i < commList.size(); i++ )
            {
                bean = ( ContentCommendPushInfoBean ) commList.get( i );

                ServiceUtil.deleteSiteResTraceMode( Long.valueOf( StringUtil.getLongValue( bean
                    .getImgResId(), -1 ) ) );

                ContentCommendTypeBean ctBean = channelService
                    .retrieveSingleContentCommendTypeBeanByTypeId( bean.getCommendTypeId() );

                metaDataService.deleteAndClearDefModelInfo( bean.getInfoId(), ctBean.getModelId(),
                    siteFlag, Constant.METADATA.MODEL_TYPE_COMMEND );

            }

            List excludeRowList = new ArrayList( excludeRowFlagSet );

            // 推荐位标志失效
            contentDao.updateCommendRowInfoContentStatusByCommFlag( commFlag, excludeRowList,
                Constant.COMMON.OFF, siteFlag );

            contentDao.deleteCommendRowInfoByCommFlag( commFlag, excludeRowList, siteFlag );

            // 处理删除后的row信息
            List rowBeanAllList = contentDao.queryAllCommendContentByCommendTypeByFlag( commFlag,
                siteFlag, false );

            ContentCommendPushInfoBean rowInfoBean = null;
            List rowBeanInnerList = null;
            int num = 0;

            for ( int i = 0; i < rowBeanAllList.size(); i++ )
            {
                rowInfoBean = ( ContentCommendPushInfoBean ) rowBeanAllList.get( i );

                // 已经删除的row不处理
                if( excludeRowFlagSet.contains( rowInfoBean.getRowFlag() ) )
                {
                    continue;
                }

                num++;

                contentDao.updateCommendPushContentRowFlagByInfoId( Long.valueOf( num ),
                    rowInfoBean.getInfoId() );

                rowBeanInnerList = rowInfoBean.getRowInfoList();
                for ( int j = 0; j < rowBeanInnerList.size(); j++ )
                {
                    rowInfoBean = ( ContentCommendPushInfoBean ) rowBeanInnerList.get( j );
                    contentDao.updateCommendPushContentRowFlagByInfoId( Long.valueOf( num ),
                        rowInfoBean.getInfoId() );
                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            releaseContentCache();
            ContentDao.releaseAllCountCache();

        }

    }

    public void deleteCommendContentRowInfo( String commFlag, Long rowFlag,
        List deleteInfoIdArrayList, String siteFlag )
    {
        try
        {
            mysqlEngine.beginTransaction();

            List rowInfoExcludeDeleteIdList = contentDao
                .queryCommendPushContentRowInfoByRowFlagAndExcludeId( commFlag, rowFlag,
                    deleteInfoIdArrayList );

            // 删除info
            Long infoId = null;

            ContentCommendPushInfoBean bean = null;

            for ( int i = 0; i < deleteInfoIdArrayList.size(); i++ )
            {
                infoId = Long.valueOf( StringUtil.getLongValue( ( String ) deleteInfoIdArrayList
                    .get( i ), -1 ) );

                if( infoId.longValue() > 0 )
                {
                    bean = contentDao.querySingleCommendPushInfoByInfoId( infoId );

                    ServiceUtil.deleteSiteResTraceMode( Long.valueOf( StringUtil.getLongValue( bean
                        .getImgResId(), -1 ) ) );

                    contentDao.deleteCommendInfoByInfoId( infoId );

                    ContentCommendTypeBean ctBean = channelService
                        .retrieveSingleContentCommendTypeBeanByTypeId( bean.getCommendTypeId() );

                    metaDataService.deleteAndClearDefModelInfo( infoId, ctBean.getModelId(),
                        siteFlag, Constant.METADATA.MODEL_TYPE_COMMEND );
                }

            }

            // 更新删除后的order
            ContentCommendPushInfoBean infoBean = null;
            int num = 0;
            for ( int i = 0; i < rowInfoExcludeDeleteIdList.size(); i++ )
            {
                num = i + 1;

                infoBean = ( ContentCommendPushInfoBean ) rowInfoExcludeDeleteIdList.get( i );

                contentDao.updateCommendPushContentOrderInfoByInfoId( infoBean.getRowFlag(),
                    Integer.valueOf( num ), infoBean.getInfoId() );
            }

            // 若整个行数据被删除,需要调整行排序
            if( rowInfoExcludeDeleteIdList.size() == 0 )
            {
                List allRowInfo = contentDao.queryAllCommendContentByCommendTypeByFlag( commFlag,
                    siteFlag, false );

                List rowList = null;
                num = 0;
                for ( int i = 0; i < allRowInfo.size(); i++ )
                {

                    infoBean = ( ContentCommendPushInfoBean ) allRowInfo.get( i );

                    if( infoBean.getRowFlag().longValue() != rowFlag.longValue() )
                    {
                        num++;

                        contentDao.updateCommendPushContentOrderInfoByInfoId( Long.valueOf( num ),
                            infoBean.getRowOrder(), infoBean.getInfoId() );

                        rowList = infoBean.getRowInfoList();

                        for ( int j = 0; j < rowList.size(); j++ )
                        {
                            infoBean = ( ContentCommendPushInfoBean ) rowList.get( j );

                            contentDao.updateCommendPushContentOrderInfoByInfoId( Long
                                .valueOf( num ), infoBean.getRowOrder(), infoBean.getInfoId() );
                        }
                    }
                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            releaseContentCache();
            ContentDao.releaseAllCountCache();

        }
    }

    public Integer retrieveAllCommendContentByCommendCountByFlag( String commendFlag )
    {
        return contentDao.queryAllCommendContentByCommendCountByFlag( commendFlag );
    }

    public Integer retrieveAllCommendContentByCommendCountByTypeId( Long typeId )
    {
        return contentDao.queryAllCommendContentByCommendCountByTypeId( typeId );
    }

    public List retrieveAllCommendContentByCommendByFlag( String commendFlag, String siteFlag )
    {
        String key = "retrieveAllCommendContentByCommendByFlag:" + commendFlag + "|" + siteFlag;

        List resList = ( List ) listContentCache.getEntry( key );

        if( resList == null )
        {
            resList = contentDao.queryAllCommendContentByCommendTypeByFlag( commendFlag, siteFlag,
                false );

            listContentCache.putEntry( key, resList );
        }

        return resList;
    }

    public List retrieveAllCommendContentByCommendByFlag( String commendFlag, String siteFlag,
        Integer size )
    {
        String key = "retrieveAllCommendContentByCommendByFlag:" + commendFlag + "|" + siteFlag
            + "|" + size;

        List resList = ( List ) listContentCache.getEntry( key );

        if( resList == null )
        {
            resList = contentDao.queryAllCommendContentByCommendTypeByFlag( commendFlag, siteFlag,
                size, false );

            listContentCache.putEntry( key, resList );
        }

        return resList;
    }

    public List retrieveAllCommendContentByCommendByTypeId( Long typeId, String siteFlag,
        Long startPos, Integer size )
    {
        String key = "retrieveAllCommendContentByCommendByTypeId:" + typeId + "|" + siteFlag + "|"
            + startPos + "|" + size;

        List resList = ( List ) listContentCache.getEntry( key );

        if( resList == null )
        {
            resList = contentDao.queryAllCommendContentByCommendTypeByTypeId( typeId, siteFlag,
                startPos, size, false );

            listContentCache.putEntry( key, resList );
        }

        return resList;
    }

    public ContentCommendPushInfoBean retrieveSingleCommendPushInfoByInfoId( Long infoId )
    {

        String key = "retrieveSingleCommendPushInfoByInfoId:" + infoId;

        ContentCommendPushInfoBean result = ( ContentCommendPushInfoBean ) singleContentCache
            .getEntry( key );

        if( result == null )
        {
            result = contentDao.querySingleCommendPushInfoByInfoId( infoId );

            singleContentCache.putEntry( key, result );
        }

        return result;
    }

    public Integer retrieveCensorStateByContentId( Long contentId )
    {
        return contentDao.queryCensorStateByContentId( contentId );
    }

    public List retrieveCommendContentRowInfoByRowFlag( Long rowFlag, String commFlag,
        String siteFlag )
    {
        String key = "retrieveCommendContentRowInfoByRowFlag:" + rowFlag + "|" + commFlag + "|"
            + siteFlag;

        List resList = ( List ) listContentCache.getEntry( key );

        if( resList == null )
        {
            resList = contentDao.queryCommendPushContentRowInfoByRowFlag( rowFlag, commFlag,
                siteFlag );

            listContentCache.putEntry( key, resList );
        }

        return resList;
    }

    public void sortCommendContentColumnInfo( String direct, Integer count, String commFlag,
        Long rowFlag, String siteFlag )
    {
        try
        {
            mysqlEngine.beginTransaction();

            List allCommInfoList = contentDao.queryAllCommendContentByCommendTypeByFlag( commFlag,
                siteFlag, false );

            ContentCommendPushInfoBean infoBean = null;
            ContentCommendPushInfoBean directBean = null;
            int directBeanPos = 0;

            for ( int i = 0; i < allCommInfoList.size(); i++ )
            {
                infoBean = ( ContentCommendPushInfoBean ) allCommInfoList.get( i );

                if( infoBean.getRowFlag().equals( rowFlag ) )
                {
                    directBean = infoBean;
                    directBeanPos = i + 1;
                    break;
                }
            }

            allCommInfoList.remove( directBean );

            int allRowCount = allCommInfoList.size();

            int targetPos = 0;

            if( "up".equals( direct ) )
            {
                targetPos = directBeanPos - count.intValue();
            }
            else if( "down".equals( direct ) )
            {
                targetPos = directBeanPos + count.intValue();
            }

            int newPos = targetPos - 1;
            if( targetPos - 1 < 0 )
            {
                newPos = 0;
            }
            else if( ( targetPos - 1 ) > allRowCount )
            {
                newPos = allRowCount;
            }

            allCommInfoList.add( newPos, directBean );

            // 更新所有排序后bean信息
            List innerRowBeanList = null;
            for ( int i = 0; i < allCommInfoList.size(); i++ )
            {
                targetPos = i + 1;

                infoBean = ( ContentCommendPushInfoBean ) allCommInfoList.get( i );

                contentDao.updateCommendPushContentRowFlagByInfoId( Long.valueOf( targetPos ),
                    infoBean.getInfoId() );

                innerRowBeanList = infoBean.getRowInfoList();
                for ( int j = 0; j < innerRowBeanList.size(); j++ )
                {
                    infoBean = ( ContentCommendPushInfoBean ) innerRowBeanList.get( j );

                    contentDao.updateCommendPushContentRowFlagByInfoId( Long.valueOf( targetPos ),
                        infoBean.getInfoId() );
                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            releaseContentCache();
            // TODO RELEASE CACHE
        }

    }

    public void sortCommendContentRowInfo( Long infoId, String sortFlag )
    {
        try
        {
            mysqlEngine.beginTransaction();

            // 根据flag，交换最进元素的rowOrder
            ContentCommendPushInfoBean currentInfoBean = contentDao
                .querySingleCommendPushInfoByInfoId( infoId );

            if( currentInfoBean == null )
            {
                return;
            }

            ContentCommendPushInfoBean targetInfoBean = null;

            Integer currentOrder = currentInfoBean.getRowOrder();

            if( Constant.CONTENT.SORT_LEFT.equals( sortFlag ) )
            {
                targetInfoBean = contentDao.querySingleCommendPushInfoByOrderInfo( currentInfoBean
                    .getCommendFlag(), currentInfoBean.getRowFlag(), Integer
                    .valueOf( currentInfoBean.getRowOrder().intValue() + 1 ) );

            }
            else if( Constant.CONTENT.SORT_RIGHT.equals( sortFlag ) )
            {
                targetInfoBean = contentDao.querySingleCommendPushInfoByOrderInfo( currentInfoBean
                    .getCommendFlag(), currentInfoBean.getRowFlag(), Integer
                    .valueOf( currentInfoBean.getRowOrder().intValue() - 1 ) );
            }

            if( targetInfoBean == null )
            {
                return;
            }

            // 更新目标对象的order
            contentDao.updateCommendPushContentRowOrder( infoId, targetInfoBean.getRowOrder() );

            // 更新当前的order
            contentDao.updateCommendPushContentRowOrder( targetInfoBean.getInfoId(), currentOrder );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            releaseContentCache();

        }

    }

    public void addDownloadImgRes( Long contentId, Long resId )
    {
        contentDao.saveDlImg( contentId, resId );
    }

    public List retrieveDownloadImgRes( Long contentId )
    {
        return contentDao.queryDlImgList( contentId );
    }

    public void deleteDownloadImgRes( Long contentId )
    {
        contentDao.deleteDlImgList( contentId );
    }

    public List retrieveContentAssistantCopyInfoByContentId( Long contentId )
    {
        return contentDao.queryContentAssistantCopyInfoByContentId( contentId );
    }

    public void shareContentToSiteGroup( List idList, List siteList )
    {
        if( idList == null || siteList == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long siteId = null;

            Long contentId = null;

            SiteGroupBean currSite = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            ContentMainInfoBean mainInfo = null;

            Long count = null;

            for ( int i = 0; i < siteList.size(); i++ )
            {
                siteId = Long.valueOf( StringUtil.getLongValue( ( String ) siteList.get( i ), -1 ) );

                if( siteId.longValue() < 1 )
                {
                    continue;
                }

                Map paramMap = null;

                for ( int j = idList.size() - 1; j >= 0; j-- )
                {
                    if( idList.get( j ) instanceof String )
                    {
                        contentId = Long.valueOf( StringUtil.getLongValue( ( String ) idList
                            .get( j ), -1 ) );
                    }
                    else
                    {
                        contentId = ( Long ) idList.get( j );
                    }

                    mainInfo = contentDao.querySingleContentMainInfoBean( contentId );

                    if( contentId.longValue() < 1 || mainInfo == null )
                    {
                        continue;
                    }

                    count = contentDao.queryShareContentCountBySite( siteId, contentId );

                    if( count.longValue() < 1 )
                    {
                        paramMap = new HashMap();

                        paramMap.put( "contentId", contentId );
                        paramMap.put( "fromSiteId", currSite.getSiteId() );
                        paramMap.put( "toSiteId", siteId );
                        paramMap.put( "modelId", mainInfo.getModelId() );
                        paramMap.put( "fromClassId", mainInfo.getClassId() );
                        paramMap.put( "isPub", Constant.COMMON.OFF );

                        contentDao.saveSiteShareContent( paramMap );

                        // trace
                        count = contentDao.queryShareTraceCountBySite( siteId, contentId );

                        if( count.longValue() < 1 )
                        {
                            contentDao.saveSiteShareContentTrace( siteId, contentId );
                        }
                    }
                }

                SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                    .getEntry( siteId );

                Long currUserId = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth()
                    .getIdentity();

                messageService.sendManagerMessageNoTran( Long.valueOf( -9999 ), "系统事件消息", site
                    .getSiteName()
                    + "有新的共享内容", site.getSiteName() + "有来自" + currSite.getSiteName()
                    + "的共享内容，请相关人员进行处理。", securityDao.querySiteHaveHisOrgAllUser( site.getSiteId(),
                    currUserId ) );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }
    }

    public void deleteShareContentToSiteGroupInfo( List idList, Long siteId )
    {
        if( idList == null || siteId == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long contentId = null;

            for ( int j = idList.size() - 1; j >= 0; j-- )
            {
                if( idList.get( j ) instanceof String )
                {
                    contentId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( j ),
                        -1 ) );
                }
                else
                {
                    contentId = ( Long ) idList.get( j );
                }

                if( contentId.longValue() < 1 )
                {
                    continue;
                }

                // 删除主信息
                contentDao.deleteShareContentInfo( siteId, contentId );

                // 删除记录信息,因为主信息存在表示还没有被投送
                contentDao.deleteShareContentTrace( siteId, contentId );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }
    }

    public List retrieveShareContentSiteName( Long contentId )
    {
        return contentDao.queryShareContentSiteName( contentId );
    }

    public Object[] getShareContentForSiteQueryTag( String modelId, String pn, String size )
    {
        List result = null;

        Long mId = Long.valueOf( StringUtil.getLongValue( modelId, -1 ) );

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Page pageInfo = null;

        Long count = null;

        if( mId.longValue() < 1 )
        {
            count = contentDao.queryShareContentCountBySiteId( site.getSiteId() );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = contentDao.queryShareContentBySiteId( site.getSiteId(), Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageSize ) );
        }
        else
        {
            count = contentDao.queryShareContentCountBySiteIdAndModelId( site.getSiteId(), mId );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = contentDao.queryShareContentBySiteIdAndModelId( site.getSiteId(), mId, Long
                .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
        }

        return new Object[] { result, pageInfo };
    }

    /**
     * 获取当前用户的稿件
     * 
     * @param classId
     * @param pn
     * @param size
     * @return
     */
    public Object[] getDraftContentForSiteQueryTag( String censorState, String classId, String pn,
        String size )
    {
        List result = null;

        Long classIdVar = Long.valueOf( StringUtil.getLongValue( classId, -1 ) );

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 12 );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String managerName = ( String ) SecuritySessionKeeper.getSecuritySession().getAuth()
            .getApellation();

        Page pageInfo = null;

        Long count = null;

        Long censor = Long.valueOf( StringUtil.getLongValue( censorState, -1 ) );

        if( classIdVar.longValue() < 1 )
        {
            count = contentDao.queryDraftContentCountBySiteId( site.getSiteId(), censor,
                managerName );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = contentDao
                .queryDraftContentInfoBySiteId( site.getSiteId(), censor, managerName, Long
                    .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
        }
        else
        {
            count = contentDao.queryDraftContentCountBySiteId( site.getSiteId(), classIdVar,
                censor, managerName );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = contentDao.queryDraftContentInfoBySiteId( site.getSiteId(), classIdVar,
                censor, managerName, Long.valueOf( pageInfo.getFirstResult() ), Integer
                    .valueOf( pageSize ) );
        }

        return new Object[] { result, pageInfo };
    }

    public void deleteRelateContent( Long contentId, List deleteRIdList )
    {
        if( contentId == null || contentId.longValue() < 1 || deleteRIdList == null )
        {
            return;
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        try
        {
            mysqlEngine.beginTransaction();

            ContentMainInfoBean mainInfo = contentDao.querySingleContentMainInfoBean( contentId );

            if( !site.getSiteId().equals( mainInfo.getSiteId() ) )
            {
                // 只能操作管理站点的内容
                return;
            }

            String currentRelateStr = mainInfo.getRelateIds();

            if( StringUtil.isStringNull( currentRelateStr ) )
            {
                return;
            }

            String idStr = null;

            for ( int i = 0; i < deleteRIdList.size(); i++ )
            {
                idStr = ( String ) deleteRIdList.get( i );

                currentRelateStr = StringUtil.replaceString( currentRelateStr, idStr + "|", "",
                    false, false );
            }

            contentDao.updateContentRelateIdInfo( contentId, currentRelateStr );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            ContentDao.releaseAllCountCache();

            releaseContentCache();
        }

    }

    /**
     * 获取关联内容
     * 
     * @param tagId
     * @return
     */
    public Object getRelateContentQueryTag( String contentId, String clId, String classId )
    {

        ContentMainInfoBean main = contentDao.querySingleContentMainInfoBean( StringUtil
            .getLongValue( contentId, -1 ) );

        ContentClassBean classBean = null;

        if( main == null )
        {
            classBean = channelService.retrieveSingleClassBeanInfoByClassId( StringUtil
                .getLongValue( classId, -1 ) );
        }
        else
        {
            classBean = channelService.retrieveSingleClassBeanInfoByClassId( main.getClassId() );
        }

        StringBuilder buf = new StringBuilder();

        List<Long> result = null;

        if( "all".equals( clId ) )
        {

            result = contentDao.queryRelateContentIdByCId(
                StringUtil.getLongValue( contentId, -1 ), 0 );

            buf.append( "0:" );

            for ( Long infoId : result )
            {
                buf.append( infoId + "_" );
            }

            buf.append( "-" );

            List<String> infoClassList = StringUtil.changeStringToList( classBean
                .getRelateClassIds(), "," );

            int i = 1;

            for ( @SuppressWarnings( "unused" )
            String ifc : infoClassList )
            {
                result = contentDao.queryRelateContentIdByCId( StringUtil.getLongValue( contentId,
                    -1 ), i );

                buf.append( i + ":" );

                for ( Long infoId : result )
                {
                    buf.append( infoId + "_" );
                }

                buf.append( "-" );

                i++;
            }

            return buf.toString();
        }

        List<Long> infoIdList = contentDao.queryRelateContentIdByCId( StringUtil.getLongValue(
            contentId, -1 ), StringUtil.getIntValue( clId, 0 ) );

        for ( Long infoId : infoIdList )
        {
            buf.append( infoId + "*" );
        }

        return buf.toString();
    }

    /**
     * 获取关联内容
     * 
     * @param tagId
     * @return
     */
    public List getRelateInfoTag( String idList, String clId )
    {
        List<String> infoList = StringUtil.changeStringToList( idList, "-" );

        for ( String infos : infoList )
        {
            if( infos.startsWith( clId ) )
            {
                return StringUtil.changeStringToList( StringUtil.replaceString( infos, clId + ":",
                    "" ), "_" );
            }
        }

        return null;
    }

    /**
     * 获取关联调查
     * 
     * @param tagId
     * @return
     */
    public List getRelateSurveyQueryTag( String idList )
    {
        return StringUtil.changeStringToList( idList, "\\*" );
    }

    public Long retrieveTagRaleteContentByTagId( Long tagId )
    {

        Long result = ( Long ) listTagContentCountCache.getEntry( tagId );

        if( result == null )
        {
            result = contentDao.queryTagRaleteContentCountByTagId( tagId );

            listTagContentCountCache.putEntry( tagId, result );
        }

        return result;
    }

    public List retrieveTagRaleteContentByTagId( Long tagId, String order, Long start, Integer size )
    {

        String key = "retrieveTagRaleteContentByTagId:" + tagId + "|" + order + "|" + start + "|"
            + size;

        List result = ( List ) listTagContentCache.getEntry( key );

        if( result == null )
        {
            result = contentDao.queryTagRaleteContentByTagId( tagId, order, start, size );

            listTagContentCache.putEntry( tagId, result );
        }

        return result;

    }

    public void addContentOperInfo( Long contentId, String puserName, String actionId,
        String msgContent, String cMode, Integer infoType )
    {
        try
        {
            mysqlEngine.beginTransaction();

            Long verId = -1l;
            
            if(Constant.WORKFLOW.INFO_TYPE_CONTENT.equals( infoType ))
            {
                verId = createVersionContent( contentId );
            }
            
            //  
            // 1。content_oper_info 追加verId 2.删除时要删除 3.取内容时取真实classId
            // 2.工作流所有涉及contentId和classId字段增加,对应bean也同步修改
            // 3. 需这对每次审核记录设置ID,区分每次审核流程,注意:进入最后步骤时,工作流OP已删除,需反向查询opId

            WorkflowOperationBean op = workFlowDao.querySingleWorkflowOperation( contentId,
                infoType );

            Long opId = null;

            if( op == null )
            {
                opId = contentDao.queryLastContentOperInfoInfo( contentId );
            }
            else
            {
                opId = op.getOpId();
            }

            contentDao.saveContentOperInfo( contentId, opId, verId, puserName, actionId,
                msgContent, new Timestamp( DateAndTimeUtil.clusterTimeMillis() ), cMode, infoType );

            mysqlEngine.commit();

        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void checkAndDisposePickTraceSourceInfo()
    {
        try
        {
            mysqlEngine.beginTransaction();

            List pickSourceList = pickDao.queryAllPickWebTraceSource();

            String source = null;

            Map csMap = null;
            for ( int i = 0; i < pickSourceList.size(); i++ )
            {
                source = ( String ) pickSourceList.get( i );

                if( StringUtil.isStringNotNull( source ) )
                {
                    csMap = contentDao.querySingleContentSource( source );

                    if( csMap.isEmpty() )
                    {
                        contentDao.saveSource( source, StringUtil.getFirstPY(
                            source.toCharArray()[0] ).toString() );
                    }
                }
            }

            mysqlEngine.commit();
        }
        catch ( Exception e )
        {
            // 若出现异常不提示
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void addNewContentSource( String sourceName )
    {
        contentDao.saveSource( sourceName, StringUtil.getFirstPY( sourceName.toCharArray()[0] )
            .toString() );
    }

    public void editContentSource( String sourceName, Long sId )
    {
        contentDao.updateSource( sourceName, StringUtil.getFirstPY( sourceName.toCharArray()[0] )
            .toString(), sId );
    }

    public void deleteContentSource( List idList )
    {
        Long id = null;

        for ( int i = 0; i < idList.size(); i++ )
        {
            id = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

            if( id.longValue() < 1 )
            {
                continue;
            }

            contentDao.deleteSource( id );
        }
    }

    public Object getContentSourceTag( String id, String fc, String pn, String size )
    {
        List result = null;

        Long tId = Long.valueOf( StringUtil.getLongValue( id, -1 ) );

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        if( tId.longValue() > 0 )
        {
            return contentDao.querySingleContentSource( tId );
        }
        else
        {
            if( StringUtil.isStringNull( fc ) )
            {
                Long count = contentDao.queryContentSourceCount();

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = contentDao.queryContentSource( Long.valueOf( pageInfo.getFirstResult() ),
                    Integer.valueOf( pageSize ) );
            }
            else
            {
                Long count = contentDao.queryContentSourceCount( fc );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = contentDao.queryContentSource( fc, Long
                    .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
            }

            return new Object[] { result, pageInfo };
        }

    }

    public void addNewSensitiveWord( String sensitive, String replace )
    {

        if( StringUtil.isStringNotNull( sensitive ) && !haveSameFlag( sensitive.trim() ) )
        {
            contentDao.saveSensitiveWord( sensitive, replace, Constant.COMMON.ON );

            swFilter.load();
        }

    }

    public void editSensitiveWord( String sensitive, String replace, Long swId )
    {
        contentDao.updateSensitiveWord( sensitive.trim(), replace, swId );

        swFilter.load();
    }

    public void importSensitiveWord( String sword )
    {
        if( sword == null )
        {
            return;
        }

        String[] sws = StringUtil.split( sword, "\n" );

        for ( String sw : sws )
        {
            if( StringUtil.isStringNotNull( sw ) && !haveSameFlag( sw.trim() ) )
            {
                String[] swsa = sw.trim().split( "\\|" );

                if( swsa.length == 2 && StringUtil.isStringNotNull( swsa[1] ))
                {
                    contentDao.saveSensitiveWord( swsa[0], swsa[1], Constant.COMMON.ON );
                }
                else
                {
                    if(sw.trim().endsWith( "|" ))
                    {
                        sw = sw.trim().substring( 0, sw.length()-1 );
                    }
                    
                    contentDao.saveSensitiveWord( sw.trim(), "", Constant.COMMON.ON );
                }
            }
        }

        swFilter.load();
    }

    public boolean haveSameFlag( String flag )
    {
        Integer count = valiDao.querySystemTableFlagExist( "site_sensitive_word", "sensitiveStr",
            flag );

        if( count.intValue() > 0 )
        {
            return true;
        }

        return false;
    }

    public void deleteSensitiveWord( List idList )
    {
        Long id = null;

        for ( int i = 0; i < idList.size(); i++ )
        {
            id = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

            if( id.longValue() < 1 )
            {
                continue;
            }

            contentDao.deleteSensitiveWord( id );
        }

        swFilter.load();
    }

    public Object getSensitiveWordTag( String id, String pn, String size )
    {
        List result = null;

        Long tId = Long.valueOf( StringUtil.getLongValue( id, -1 ) );

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        if( tId.longValue() > 0 )
        {
            return contentDao.querySingleSensitiveWord( tId );
        }
        else
        {
            Long count = contentDao.querySensitiveWordCount();

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = contentDao.querySensitiveWord( Long.valueOf( pageInfo.getFirstResult() ),
                Integer.valueOf( pageSize ) );

            return new Object[] { result, pageInfo };
        }

    }

    public Object getSensitiveWordTag( String id, String fc, String pn, String size )
    {
        List result = null;

        Long tId = Long.valueOf( StringUtil.getLongValue( id, -1 ) );

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        if( tId.longValue() > 0 )
        {
            return contentDao.querySingleContentSource( tId );
        }
        else
        {
            if( StringUtil.isStringNull( fc ) )
            {
                Long count = contentDao.queryContentSourceCount();

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = contentDao.queryContentSource( Long.valueOf( pageInfo.getFirstResult() ),
                    Integer.valueOf( pageSize ) );
            }
            else
            {
                Long count = contentDao.queryContentSourceCount( fc );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = contentDao.queryContentSource( fc, Long
                    .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
            }

            return new Object[] { result, pageInfo };
        }

    }

    public void changeSensitiveWorduserStatus( List idList, Integer us )
    {
        Long id = null;

        for ( int i = 0; i < idList.size(); i++ )
        {
            id = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

            if( id.longValue() < 1 )
            {
                continue;
            }

            contentDao.updateSensitiveWordUseStatus( us, id );
        }
    }

    /**
     * 处理所有提交的模型内容字符参数，对于出现铭感词的字段高亮处理
     * 
     * @param params
     * @param filedBeanList
     * @return
     */
    public String disposeModelDataSensitiveWordHighlight( Map params,
        List<ModelFiledInfoBean> filedBeanList )
    {

        Map<String, String> hlsvMap = null;

        List<Map> hlsvList = new ArrayList<Map>();

        String swc = null;

        String title = ( String ) params.get( "title" );

        String value = Jsoup.parse( title != null ? title : "" ).text();

        swc = highlightContentTextSensitive( value );

        if( !value.equals( swc ) )
        {
            hlsvMap = new HashMap<String, String>();

            hlsvMap.put( "sn", "标题" );

            hlsvMap.put( "swh", highlightContentTextSensitive( value ) );

            hlsvList.add( hlsvMap );
        }

        for ( ModelFiledInfoBean fb : filedBeanList )
        {
            // 所有的字符类型
            Integer dataType = fb.getDataType();

            if( !Constant.METADATA.TEXT_TYPE.equals( dataType )
                && !Constant.METADATA.LONGTEXT_TYPE.equals( dataType ) )
            {
                continue;
            }

            value = Jsoup.parse(
                ( String ) params.get( fb.getFieldSign() ) != null ? ( String ) params.get( fb
                    .getFieldSign() ) : "" ).text();

            swc = highlightContentTextSensitive( value );

            if( !value.equals( swc ) )
            {
                hlsvMap = new HashMap<String, String>();

                hlsvMap.put( "sn", fb.getShowName() );

                hlsvMap.put( "swh", highlightContentTextSensitive( value ) );

                hlsvList.add( hlsvMap );
            }

        }

        if( hlsvList.isEmpty() )
        {
            return null;
        }

        String code = StringUtil.getUUIDString();

        HL.put( code, hlsvList );

        return code;

    }

    public Object getHighlightSW( String code )
    {
        List res = HL.get( code );

        HL.remove( code );

        return res;
    }
    
    public Map retrieveSingleContentStatus( Long contentId )
    {
        String key = "retrieveSingleContentStatus:" + contentId;

        Map result = ( Map ) fastContentStatusCache.getEntry( key );

        if( result == null )
        {
            result = contentDao.querySingleContentStatus( contentId );

            fastContentStatusCache.putEntry( key, result );
        }
        else
        {
            StatContentVisitOrCommentDWMCount scv = ( StatContentVisitOrCommentDWMCount ) StatService.statCacheContentClickCountMap
                .get( contentId );

            if( scv != null )
            {
                long clickMonthCount = ( Long ) result.get( "clickMonthCount" );

                long clickWeekCount = ( Long ) result.get( "clickWeekCount" );

                long clickDayCount = ( Long ) result.get( "clickDayCount" );

                long clickCount = ( Long ) result.get( "clickCount" );

                Map res = new LinkedHashMap();

                res.putAll( result );

                res.put( "clickMonthCount", clickMonthCount + scv.getMonthCount() );
                res.put( "clickWeekCount", clickWeekCount + scv.getWeekCount() );
                res.put( "clickDayCount", clickDayCount + scv.getDayCount() );
                res.put( "clickCount", clickCount + scv.getNoLimitCount() );

                return res;
            }

        }

        return result;
    }

    public void addTagRelateContent( List cidList, List tagIdList )
    {
        if( cidList == null || tagIdList == null )
        {
            return;
        }

        Long tagId = null;

        Long contentId = null;

        Long existCount = null;

        Map mainInfo = null;

        String tagkey = null;

        try
        {
            mysqlEngine.beginTransaction();

            for ( int i = 0; i < tagIdList.size(); i++ )
            {
                if( tagIdList.get( i ) instanceof Long )
                {
                    tagId = ( Long ) tagIdList.get( i );
                }
                else
                {
                    tagId = Long.valueOf( StringUtil.getLongValue( ( String ) tagIdList.get( i ),
                        -1 ) );
                }

                if( tagId.longValue() < 0 )
                {
                    continue;
                }

                for ( int j = 0; j < cidList.size(); j++ )
                {
                    if( cidList.get( j ) instanceof Long )
                    {
                        contentId = ( Long ) cidList.get( j );
                    }
                    else
                    {
                        contentId = Long.valueOf( StringUtil.getLongValue( ( String ) cidList
                            .get( j ), -1 ) );
                    }

                    if( contentId.longValue() < 0 )
                    {
                        continue;
                    }

                    existCount = channelDao.queryTagWordRelateContentCount( tagId, contentId );

                    if( existCount.longValue() < 1 )
                    {
                        channelDao.saveTagWordRelateContent( tagId, contentId );

                        mainInfo = contentDao.querySingleContentMainInfo( contentId );

                        tagkey = ( String ) mainInfo.get( "tagKey" );

                        contentDao.updateContentTagKeyIdStr( contentId, tagkey + "#" + tagId );
                    }
                }

                channelDao.updateTagWordRelateContentCount( tagId );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            ContentDao.releaseAllCountCache();

            releaseContentCache();
        }

    }

    public int addOrEditDefineFormData( SiteGroupBean site, Map params, boolean editMode )
    {
        Long modelId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "modelId" ),
            -1 ) );

        /**
         * 获取对应数据模型元数据
         */
        DataModelBean modelBean = metaDataService.retrieveSingleDataModelBeanById( modelId );
        List filedBeanList = metaDataService.retrieveModelFiledInfoBeanList( modelId );
        ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( modelId );

        if( modelBean == null )
        {
            return -1;// 表单模型不存在
        }

        // 获取自定义模型数据
        ModelFiledInfoBean bean = null;

        List needUploadImageGroupInfoList = new ArrayList();

        List userDefineParamList = new ArrayList();

        Object val = null;

        Map currentObj = null;

        if( editMode )
        {
            currentObj = contentDao.querySingleUserDefineContentOnlyModelDataResultNotDisposeInfo(
                sqlCodeBean, modelBean.getRelateTableName(), Long.valueOf( StringUtil.getLongValue(
                    ( String ) params.get( Constant.METADATA.CONTENT_ID_NAME ), -1 ) ) );
        }

        String reUrl = null;

        for ( int j = 0; j < filedBeanList.size(); j++ )
        {
            bean = ( ModelFiledInfoBean ) filedBeanList.get( j );

            // 内部业务字段需要强制设定默认值
            if( Constant.METADATA.INNER_DATA == bean.getHtmlElementId().intValue() )
            {
                continue;
            }

            // 需要引入filed元数据来对不同类型字段进行对应处理
            val = ServiceUtil.disposeDataModelFiledFromWeb( bean, params,
                needUploadImageGroupInfoList, false );

            if( val == null )
            {
                val = bean.getDefaultValue();
            }

            if( editMode && !params.containsKey( bean.getFieldSign() ) )
            {
                val = currentObj.get( bean.getFieldSign() );
            }

            userDefineParamList.add( val );

            // 单图水印处理
            if( Constant.METADATA.UPLOAD_IMG == bean.getHtmlElementId().intValue()
                && Constant.COMMON.ON.equals( bean.getNeedMark() ) )
            {
                // 水印处理
                reUrl = ServiceUtil.getImageReUrl( ( String ) val );

                // 已经加过水印的不需要再增加
                if( !Constant.COMMON.ON.equals( resService.getImageMarkStatus( reUrl ) ) )
                {
                    if( ServiceUtil.disposeImageMark( site, reUrl, Integer.valueOf( ServiceUtil
                        .getImageW( ( String ) val ) ), Integer.valueOf( ServiceUtil
                        .getImageH( ( String ) val ) ) ) )
                    {
                        // 成功加水印则更新
                        resService.setImageMarkStatus( reUrl, Constant.COMMON.ON );
                    }
                }

            }

            // 自定义时间排序类型处理,需要独立新加值
            if( Constant.METADATA.MYSQL_DATETIME.equals( bean.getPerdureType() )
                && Constant.COMMON.ON.equals( bean.getOrderFlag() ) )
            {
                if( val instanceof Timestamp )
                {
                    userDefineParamList.add( Long.valueOf( ( ( Timestamp ) val ).getTime() ) );
                }
                else
                {
                    userDefineParamList.add( DateAndTimeUtil.clusterTimeMillis() );
                }
            }
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long contentId = Long.valueOf( -1 );

            if( editMode )
            {
                // 将ID放在最后一个位置
                contentId = Long.valueOf( StringUtil.getLongValue( ( String ) params
                    .get( Constant.METADATA.CONTENT_ID_NAME ), -1 ) );

                userDefineParamList.add( contentId );

                contentDao.saveOrUpdateModelContent( sqlCodeBean.getUpdateSql(),
                    userDefineParamList.toArray() );
            }
            else
            {

                UpdateState updateState = metaDataDao.saveDefFormMainInfo( modelBean
                    .getDataModelId(), site.getSiteId(), modelBean.getMustCensor().intValue() == 1
                    ? Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW
                    : Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );

                if( updateState.haveKey() )
                {
                    contentId = Long.valueOf( updateState.getKey() );

                    params.put( Constant.CONTENT.ID_ORDER_VAR, contentId );

                    // 将ID放在最后一个位置
                    userDefineParamList.add( contentId );

                    contentDao.saveOrUpdateModelContent( sqlCodeBean.getInsertSql(),
                        userDefineParamList.toArray() );

                    // 内部业务字段初始化值
                    for ( int j = 0; j < filedBeanList.size(); j++ )
                    {
                        bean = ( ModelFiledInfoBean ) filedBeanList.get( j );

                        // 内部业务字段需要强制设定默认值
                        if( Constant.METADATA.INNER_DATA == bean.getHtmlElementId().intValue() )
                        {
                            metaDataService.updateFieldMetadataDefValAndId( modelBean,
                                Constant.METADATA.PREFIX_COLUMN_NAME + bean.getFieldSign(), bean
                                    .getDefaultValue(), contentId );
                        }
                    }

                }
                else
                {
                    return -3;// 无法存储主数据
                }

            }

            /**
             * 将需要索引的信息记录入库，由线程统一解析
             */
            // 自定义模型支持搜索字段,需要将模型信息加入索引准备
            SearchIndexContentState searchIndexState = new SearchIndexContentState();

            searchIndexState.setClassId( Long.valueOf( -999999999 ) );
            searchIndexState.setContentId( contentId );

            searchIndexState
                .setCensor( modelBean.getMustCensor().intValue() == 1
                    ? Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW
                    : Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );

            searchIndexState.setIndexDate( new Date( DateAndTimeUtil.clusterTimeMillis() ) );
            searchIndexState.setEventFlag( editMode ? Constant.JOB.SEARCH_INDEX_EDIT
                : Constant.JOB.SEARCH_INDEX_ADD );

            searchIndexState.setModelId( modelBean.getDataModelId() );
            searchIndexState.setSiteId( site.getSiteId() );

            searchService.addIndexContentState( searchIndexState );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            MetaDataService.resetFormDataCache();
        }

        return 1;
    }

    public void addContentLinkInfoTrace( Long contentId, Long linkId )
    {
        contentDao.saveContentLinkInfoTrace( contentId, linkId );
    }

    /**
     * 获取内容审核日志
     * 
     * @param contentId
     * @return
     */
    public Object getContentOperInfoInfoList( String contentId, String pn, String size )
    {
        List result = null;

        Long cid = Long.valueOf( StringUtil.getLongValue( contentId, -1 ) );

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 12 );

        Page pageInfo = null;

        Long count = null;

        count = contentDao.queryContentOperInfoInfoCount( cid );

        pageInfo = new Page( pageSize, count.intValue(), pageNum );

        result = contentDao.queryContentOperInfoInfoList( cid, Long.valueOf( pageInfo
            .getFirstResult() ), Integer.valueOf( pageSize ) );

        return new Object[] { result, pageInfo };
    }

    public void changeWKOPerCurrentCensorMan( Long contentId, Long userId, Integer infoType )
    {
        WorkflowOperationBean op = workFlowDao.querySingleWorkflowOperation( contentId, infoType );

        if( op == null )
        {
            return;
        }

        SystemUserBean user = securityDao.querySingleSystemUserBeanById( userId );

        if( user == null )
        {
            return;
        }

        // 占有被审核数据
        workFlowDao.updateWorkflowOperationPossessInfo( contentId, infoType, user.getUserId(), user
            .getUserName(), Constant.WORKFLOW.OPER_IS_POSSESS, Constant.WORKFLOW.OPER_IN_FLOW );
    }
    
    public Object getStepActUserForTag( String cId, String toStep, String it, String orgCode,
        String key )
    {
        Long contentId = StringUtil.getLongValue( cId, -1 );

        Long toStepId = StringUtil.getLongValue( toStep, -1 );

        String skey = SystemSafeCharUtil.decodeFromWeb( key );

        Integer infoType = StringUtil.getIntValue( it, 1 );

        WorkflowOperationBean op = workFlowDao.querySingleWorkflowOperation( contentId, infoType );

        WorkflowStepInfoBean st = workFlowDao.querySingleWorkflowStepBeanByStepId( toStepId );

        if( op == null && st == null )
        {
            return null;
        }

        Set<Long> uidSet = workFlowDao.queryActorIdForStepOrg( st.getFlowId(), toStepId );

        Iterator<Long> iter = uidSet.iterator();

        Long actMan = null;

        List<SystemUserBean> userIdList = new ArrayList<SystemUserBean>();

        while ( iter.hasNext() )
        {
            actMan = iter.next();

            SystemUserBean user = securityDao.querySingleSystemUserBeanById( actMan );

            if( user != null )
            {
                if( StringUtil.isStringNotNull( orgCode )
                    && !orgCode.equals( user.getRelateOrgCode() ) )
                {
                    continue;
                }

                if( StringUtil.isStringNotNull( skey ) )
                {
                    if( user.getUserTrueName().indexOf( skey ) != -1 )
                    {
                        userIdList.add( user );
                    }
                }
                else
                {
                    userIdList.add( user );
                }
            }
        }

        return userIdList;

    }
    
    /**
     * 工作流获取内容审核日志
     * 
     * @param contentId
     * @param action
     * @param size
     * @return
     */
    public Object getContentOperInfoInfoForWK( String contentId, String infoType, String action,
        String size )
    {
        List result = null;

        Long cid = Long.valueOf( StringUtil.getLongValue( contentId, -1 ) );

        int pageSize = StringUtil.getIntValue( size, 5 );

        if( "1".equals( infoType ) )
        {

            if( StringUtil.isStringNotNull( action ) )
            {
                result = contentDao.queryContentOperInfoByAction( cid, action, Integer
                    .valueOf( pageSize ) );
            }
            else
            {
                result = contentDao.queryContentOperInfo( cid, Integer.valueOf( pageSize ) );
            }
        }
        else if( "2".equals( infoType ) )
        {

            if( StringUtil.isStringNotNull( action ) )
            {
                result = contentDao.queryGbOperInfoByAction( cid, action, Integer
                    .valueOf( pageSize ) );
            }
            else
            {
                result = contentDao.queryGbOperInfo( cid, Integer.valueOf( pageSize ) );
            }
        }

        return result;
    }
    
    public List retrieveLinkInfo( List ids )
    {
        List allId = new ArrayList();

        for ( Object id : ids )
        {
            Long cId = StringUtil.getLongValue( id.toString(), -1 );

            allId.addAll( contentDao.queryLinkContentMainInfo( cId ) );

        }

        return allId;
    }

    public List retrieveTrashLinkInfo( List ids )
    {
        List allId = new ArrayList();

        for ( Object id : ids )
        {
            Long cId = StringUtil.getLongValue( id.toString(), -1 );

            allId.addAll( contentDao.queryTrashLinkContentMainInfo( cId ) );

        }

        return allId;
    }
    
    public String genFromDataReport( List<String> idList, Long modelId, String head )
    {

        if( StringUtil.isStringNull( head ) )
        {
            head = System.currentTimeMillis() + "";
        }

        if( idList.isEmpty() )
        {
            return null;
        }

        DataModelBean model = metaDataDao.querySingleDataModelBeanById( modelId );

        if( model == null )
        {
            return null;
        }

        List<ModelFiledInfoBean> fbList = metaDataDao
            .queryUserDefinedModelFiledInfoBeanList( modelId );

        List<String> headList = new ArrayList<String>();

        Map taHead = new LinkedHashMap();

        for ( ModelFiledInfoBean fb : fbList )
        {
            if( fb.getHtmlElementId() != 3 && fb.getHtmlElementId() < 10 )
            {
                headList.add( fb.getFieldSign() );

                taHead.put( fb.getShowName(), fb.getFieldSign() );
            }
        }

        List<Map> dataListTemp = metaDataService.retrieveFormDataByIds( model.getDataModelId(),
            idList );

        List<Map> dataList = new ArrayList<Map>();

        Map nd = null;

        String fn = null;

        for ( Map data : dataListTemp )
        {
            nd = new HashMap();

            for ( String hn : headList )
            {
                nd.put( hn, data.get( hn ) );
            }

            dataList.add( nd );
        }

        return genExcel( taHead, dataList, head );
    }

    private String genExcel( Map taHead, List bodyResult, String flagName )
    {
        Workbook wb = new SXSSFWorkbook(); // 创建一个文档

        Sheet sh = wb.createSheet();

        List colNameList = createTableHead( taHead, wb, sh );

        createTableBody( colNameList, bodyResult, wb, sh );

        String base = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        // 当前时间日期
        String day = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        String fileBase = base + "sys_temp";

        File test = new File( fileBase );

        if( !test.exists() )
        {
            test.mkdirs();
        }

        String fullName = fileBase + File.separator + flagName + "_" + day + ".xlsx";

        FileOutputStream fileOut = null;

        try
        {
            fileOut = new FileOutputStream( fullName );
            wb.write( fileOut );
        }
        catch ( Exception e )
        {

            e.printStackTrace();

            try
            {

                fileOut.close();
            }
            catch ( IOException e1 )
            {

            }

        }

        return fullName;
    }

    @SuppressWarnings( "deprecation" )
    private List createTableHead( Map head, Workbook wb, Sheet sh )
    {

        int cellIndex = 0;
        Row title = sh.createRow( 0 ); // 创建一行

        Iterator iter = null;

        Entry entry = null;

        String key = null;

        String val = null;

        List colNameList = new ArrayList();

        iter = head.entrySet().iterator();

        Set bigc = new HashSet();

        while ( iter.hasNext() )
        {

            entry = ( Entry ) iter.next();

            key = ( String ) entry.getKey();

            val = ( String ) entry.getValue();

            //if( key.indexOf( "个人学习简历" ) != -1 || key.indexOf( "获得证书" ) != -1
            //    || key.indexOf( "奖励情况" ) != -1 )
            {
                //bigc.add( cellIndex );
            }

            colNameList.add( val );

            title.createCell( cellIndex++ ).setCellValue( key ); // 创建单元格

        }

        CellStyle style = wb.createCellStyle(); // 单元格样式
        style.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND ); // 设置背景颜色模式
        style.setFillForegroundColor( HSSFColor.GREY_25_PERCENT.index );
        // 设置背景颜色
        style.setAlignment( HSSFCellStyle.ALIGN_CENTER );
        Font font = wb.createFont();
        font.setFontName( "黑体" );
        font.setFontHeightInPoints( ( short ) 13 );// 设置字体大小
        style.setFont( font ); // 设置字体
        for ( int i = 0; i < cellIndex; i++ )
        {

            title.getCell( i ).setCellStyle( style );

            if( i == 0 )
            {
                sh.setColumnWidth( i, 2000 ); // 设置列宽
            }
            else if( bigc.contains( i ) )
            {
                sh.setColumnWidth( i, 16000 ); // 设置列宽
            }
            else
            {
                sh.setColumnWidth( i, 4000 ); // 设置列宽
            }
        }

        return colNameList;
    }

    private void createTableBody( List colNameList, List body, Workbook wb, Sheet sh )
    {
        Map valMap = null;

        String val = null;

        String col = null;

        for ( int i = 0; i < body.size(); i++ )
        {
            valMap = ( Map ) body.get( i );

            Row row = sh.createRow( i + 1 );

            int cellIndex = 0;

            for ( int j = 0; j < colNameList.size(); j++ )
            {
                col = ( String ) colNameList.get( j );

                val = valMap.get( col ) != null ? valMap.get( col ).toString() : "0";

                if( "rank".equals( col ) )
                {
                    val = ( i + 1 ) + "";
                }

                double test = StringUtil.getDoubleValue( val, -1 );

                // 暂无小数转换需求
                // if( test != -1 )
                // {
                // row.createCell( cellIndex++ ).setCellValue( test );
                // }
                // else
                {
                    row.createCell( cellIndex++ ).setCellValue( val );
                }
            }

        }
    }

    public Object getMaxOrMinHeadPageModeOrderIdByOrderByAndWay( String orderBy, String orderWay )
    {
        Object max = null;
        if( Constant.CONTENT.DEFAULT_ORDER_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ORDER_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ORDER_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.ID_ORDER_VAR.equals( orderBy )
            || Constant.CONTENT.ADD_DATE_ORDER_VAR.equals( orderBy ) )
        {

            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Long.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Long.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.PUB_DATE_ORDER_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Long.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Long.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.CLICK_COUNT_ORDER_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.CLICK_COUNT_ORDER_DAY_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.CLICK_COUNT_ORDER_WEEK_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.CLICK_COUNT_ORDER_MONTH_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.COMM_COUNT_ORDER_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.COMM_COUNT_ORDER_DAY_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.COMM_COUNT_ORDER_WEEK_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.COMM_COUNT_ORDER_MONTH_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.SUPPORT_ORDER_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.AGAINST_ORDER_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
        }

        return max;
    }

    public Object getMaxOrMinEndPageModeOrderIdByOrderByAndWay( String orderBy, String orderWay )
    {
        Object max = null;
        if( Constant.CONTENT.DEFAULT_ORDER_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ORDER_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ORDER_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.ID_ORDER_VAR.equals( orderBy )
            || Constant.CONTENT.ADD_DATE_ORDER_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.PUB_DATE_ORDER_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.CLICK_COUNT_ORDER_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.CLICK_COUNT_ORDER_DAY_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.CLICK_COUNT_ORDER_WEEK_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.CLICK_COUNT_ORDER_MONTH_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.COMM_COUNT_ORDER_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.COMM_COUNT_ORDER_DAY_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.COMM_COUNT_ORDER_WEEK_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.COMM_COUNT_ORDER_MONTH_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.SUPPORT_ORDER_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
        }
        else if( Constant.CONTENT.AGAINST_ORDER_VAR.equals( orderBy ) )
        {
            if( Constant.CONTENT.UP_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MAX_ID_FLAG );
            }
            else if( Constant.CONTENT.DOWN_ORDER_WAY_VAR.equals( orderWay ) )
            {
                max = Double.valueOf( Constant.CONTENT.MIN_ID_FLAG );
            }
        }

        return max;
    }

    public String getOrderFilterByCreateBy( String orderFilterBy, String filterBy,
        String creatorBy, String orgCode, boolean childMode )
    {
        String filter = getOrderFilterByFilterBy( orderFilterBy, filterBy, orgCode, childMode );

        if( Constant.CONTENT.CREATOR_MY_FILTER.equals( creatorBy ) )
        {
            filter = filter + " and creator='"
                + SecuritySessionKeeper.getSecuritySession().getAuth().getApellation() + "'";
        }
        else if( Constant.CONTENT.CREATOR_OTHER_FILTER.equals( creatorBy ) )
        {
            filter = filter + " and otherFlag=1";
        }

        return filter;
    }

    public String getOrderFilterByFilterBy( String orderFilterBy, String filterBy, String orgCode,
        boolean childMode )
    {
        String filter = getOrderFilterByOrderFilterBy( orderFilterBy );

        filter = getOrderFilterByOrgCodeFilterBy( orgCode, childMode );

        if( Constant.CONTENT.HAME_IMG_FILTER.equals( filterBy ) )
        {
            filter = filter + " and homeImgFlag=1 ";
        }
        else if( Constant.CONTENT.CHANNEL_IMG_FILTER.equals( filterBy ) )
        {
            filter = filter + " and channelImgFlag=1 ";
        }
        else if( Constant.CONTENT.CLASS_IMG_FILTER.equals( filterBy ) )
        {
            filter = filter + " and classImgFlag=1 ";
        }
        else if( Constant.CONTENT.CONTENT_IMG_FILTER.equals( filterBy ) )
        {
            filter = filter + " and contentImgFlag=1 ";
        }

        return filter;
    }

    public String getOrderFilterByOrgCodeFilterBy( String orgCode, boolean childMode )
    {
        String filter = "";

        // TODO 需要检查合法性
        if( StringUtil.isStringNotNull( orgCode ) )
        {
            if( childMode )
            {
                filter = " and orgCode like '" + orgCode + "%' ";
            }
            else
            {
                filter = " and orgCode='" + orgCode + "' ";
            }
        }

        return filter;
    }

    public String getOrderFilterByOrderFilterBy( String orderFilterBy )
    {
        String filter = "";

        if( Constant.CONTENT.CLICK_COUNT_ORDER_VAR.equals( orderFilterBy ) )
        {
            filter = " and clickCount>=0 ";
        }
        else if( Constant.CONTENT.CLICK_COUNT_ORDER_DAY_VAR.equals( orderFilterBy ) )
        {
            filter = " and clickDayCount>=0 ";
        }
        else if( Constant.CONTENT.CLICK_COUNT_ORDER_WEEK_VAR.equals( orderFilterBy ) )
        {
            filter = " and clickWeekCount>=0 ";
        }
        else if( Constant.CONTENT.CLICK_COUNT_ORDER_MONTH_VAR.equals( orderFilterBy ) )
        {
            filter = " and clickMonthCount>=0 ";
        }
        else if( Constant.CONTENT.COMM_COUNT_ORDER_VAR.equals( orderFilterBy ) )
        {
            filter = " and commCount>=0 ";
        }
        else if( Constant.CONTENT.COMM_COUNT_ORDER_DAY_VAR.equals( orderFilterBy ) )
        {
            filter = " and commDayCount>=0 ";
        }
        else if( Constant.CONTENT.COMM_COUNT_ORDER_WEEK_VAR.equals( orderFilterBy ) )
        {
            filter = " and commWeekCount>=0 ";
        }
        else if( Constant.CONTENT.COMM_COUNT_ORDER_MONTH_VAR.equals( orderFilterBy ) )
        {
            filter = " and commMonthCount>=0 ";
        }
        else if( Constant.CONTENT.SUPPORT_ORDER_VAR.equals( orderFilterBy ) )
        {
            filter = " and supportCount>=0 ";
        }
        else if( Constant.CONTENT.AGAINST_ORDER_VAR.equals( orderFilterBy ) )
        {
            filter = " and againstCount>=0 ";
        }

        return filter;
    }

    public Object[] disposeWorkflowState( Map requestParams, Integer infoType, Long currentKey,
        ContentClassBean classBean, ContentMainInfo info, boolean editMode, List wfActionList,
        Map currentInfo )
    {
        // 工作流处理
        String contentAddStatus = ( String ) requestParams.get( "contentAddStatus" );

        Long actionId = Long.valueOf( StringUtil.getLongValue( ( String ) requestParams
            .get( "actionId" ), -1 ) );

        Long toStepId = StringUtil.getLongValue( ( String ) requestParams.get( "toStepId" ), -1 );

        Integer endState = null;

        String flowTarget = "";

        if( toStepId == 99999 )
        {
            // 默认发布状态
            // 删除可能存在遗留的op
            workFlowDao.deleteWorkflowOperationByContentId( currentKey, infoType );

            endState = WorkflowService.pendingCensorStateByStartAndEndPublishDate( new Timestamp(
                info.getAppearStartDateTime().getTime() ), new Timestamp( info
                .getAppearEndDateTime().getTime() ), Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );

            contentDao.updateContentMainInfoCensorStatus( currentKey, endState );
        }
        else if( Constant.WORKFLOW.DRAFT.equals( contentAddStatus ) )
        {
            // 稿件状态
            contentDao.updateContentMainInfoCensorStatus( currentKey,
                Constant.WORKFLOW.CENSOR_STATUS_DRAFT );

            endState = Constant.WORKFLOW.CENSOR_STATUS_DRAFT;
        }
        else if( classBean.getWorkflowId().longValue() > 0 )
        {
            // 非稿件,进入工作流流程

            Object[] wi = workflowService.disposeContentWorkflowStatus( infoType, classBean
                .getClassId(), classBean.getWorkflowId(), currentKey, actionId,
                ( String ) requestParams.get( "flowTarget" ), Long
                    .valueOf( ( String ) requestParams.get( "fromStepId" ) ), Long
                    .valueOf( ( String ) requestParams.get( "toStepId" ) ), new Timestamp( info
                    .getAppearStartDateTime().getTime() ), new Timestamp( info
                    .getAppearEndDateTime().getTime() ), editMode, wfActionList,
                ( Integer ) currentInfo.get( "censorState" ) );

            flowTarget = ( String ) wi[1];

            endState = ( Integer ) wi[0];

        }
        else
        {
            // 默认发布状态
            // 删除可能存在遗留的op
            workFlowDao.deleteWorkflowOperationByContentId( currentKey, infoType );

            endState = WorkflowService.pendingCensorStateByStartAndEndPublishDate( new Timestamp(
                info.getAppearStartDateTime().getTime() ), new Timestamp( info
                .getAppearEndDateTime().getTime() ), Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );

            contentDao.updateContentMainInfoCensorStatus( currentKey, endState );
        }

        // 将变发布状态,删除所有操作记录
        if( Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( endState ) )
        {
            workFlowDao.deleteWorkflowOperInfoByContentId( currentKey, infoType );
        }

        return new Object[] { endState, flowTarget };
    }

    /**
     * 替换敏感词
     * 
     * @param fullContent
     * @return
     */
    public String replcaeContentTextSensitive( String fullContent )
    {
        List<String> swList = new ArrayList<String>( swFilter
            .getSensitiveWord( fullContent, SW_MAX ) );

        String sw = null;
        String replace = null;

        for ( int i = 0; i < swList.size(); i++ )
        {
            sw = swList.get( i );

            if( SW_REP.containsKey( sw ) )
            {
                replace = SW_REP.get( sw );
            }
            else
            {
                replace = contentDao.querySingleSensitiveWordRep( sw );

                SW_REP.put( sw, replace );
            }

            fullContent = StringUtil.replaceString( fullContent, sw, replace, false, false );
        }

        return fullContent;
    }
    
    /**
     * 敏感词高亮
     * 
     * @param fullContent
     * @return
     */
    public String highlightContentTextSensitive( String fullContent )
    {
        List<String> swList = new ArrayList<String>( swFilter
            .getSensitiveWord( fullContent, SW_MAX ) );

        String sw = null;
        String replace = null;

        String h1html1 = "<strong><font color='red'>";

        String h1html2 = "</font></strong>";

        for ( int i = 0; i < swList.size(); i++ )
        {
            sw = swList.get( i );

            if( SW_HL.containsKey( sw ) )
            {
                replace = SW_HL.get( sw );
            }
            else
            {
                SW_HL.put( sw, h1html1 + sw + h1html2 );
            }

            fullContent = StringUtil.replaceString( fullContent, sw, replace, false, false );
        }

        return fullContent;
    }

    public void reloadSensitiveWord()
    {
        this.swFilter.load();
    }

    /**
     * 获取新的发布时间排序ID,此方法必须为完全方法级同步,保证ID生成正确性
     * 
     * @return
     */
    public synchronized Long getNextPublishOrderTrace()
    {
        UpdateState us = contentDao.updateAndAddValPublishOrderId();

        if( us.getRow() < 0 )
        {
            // return Long.valueOf( -1 );
            throw new FrameworkException( "更新发布排序ID失败." );
        }

        return contentDao.queryPublishOrderId();
    }

    public static void releaseContentCache()
    {
        singleContentCache.clearAllEntry();
        listContentCache.clearAllEntry();

        fastListContentCache.clearAllEntry();
        fastContentStatusCache.clearAllEntry();

        listTagContentCountCache.clearAllEntry();
        listTagContentCache.clearAllEntry();

    }

    public static void releaseTagContentCache()
    {
        listTagContentCountCache.clearAllEntry();
        listTagContentCache.clearAllEntry();
    }

    public static void releaseFastListContentCache()
    {
        fastListContentCache.clearAllEntry();
        fastContentStatusCache.clearAllEntry();

    }

}
