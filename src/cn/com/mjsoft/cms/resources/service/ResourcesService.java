package cn.com.mjsoft.cms.resources.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.dao.CommonSystemDao;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.common.service.CommonSystemService;
import cn.com.mjsoft.cms.common.third.cloud.cos.COSConfig;
import cn.com.mjsoft.cms.common.third.cloud.cos.COSUtil;
import cn.com.mjsoft.cms.common.third.cloud.oss.OSSConfig;
import cn.com.mjsoft.cms.common.third.cloud.oss.OSSUtil;
import cn.com.mjsoft.cms.common.third.cloud.qiniu.QNConfig;
import cn.com.mjsoft.cms.common.third.cloud.qiniu.QNUtil;
import cn.com.mjsoft.cms.content.bean.VideoMetadataBean;
import cn.com.mjsoft.cms.resources.bean.FileInfoBean;
import cn.com.mjsoft.cms.resources.bean.ImgClassTreeItemBean;
import cn.com.mjsoft.cms.resources.bean.SiteResourceBean;
import cn.com.mjsoft.cms.resources.bean.TextBean;
import cn.com.mjsoft.cms.resources.dao.ResourcesDao;
import cn.com.mjsoft.cms.resources.dao.vo.SiteResource;
import cn.com.mjsoft.cms.resources.dao.vo.SiteResourceTrace;

import cn.com.mjsoft.cms.site.bean.SiteCloudCfgBean;
import cn.com.mjsoft.cms.site.bean.SiteDispenseServerBean;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.bean.SitePublishGatewayBean;
import cn.com.mjsoft.cms.site.dao.SiteGroupDao;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.cms.templet.dao.TemplateDao;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.FtpUtil;
import cn.com.mjsoft.framework.util.ImageUtil;
import cn.com.mjsoft.framework.util.InitClassPathHelper;
import cn.com.mjsoft.framework.util.MathUtil;
import cn.com.mjsoft.framework.util.MediaUtil;
import cn.com.mjsoft.framework.util.RegexUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings({ "unchecked" })
public class ResourcesService
{
    public static final String CS_FILE_NAME = "config" + File.separator + "cs.properties";
    private static Logger log = Logger.getLogger( ResourcesService.class );

    // private static final Map DISPOSE_STATUS = new HashMap();

    private static final String SPLIT_CHAR = "*";

    private static final Integer QUERY_COUNT = Integer.valueOf( 1000 );// 每次处理1000个

    private static final int CLEAR_RES_BEFORE_MIN = 120;// 临时资源刪除时间

    private static final FileFilter FOLDER_TYPE = new FolderTypeFilter();

    private static final FileFilter FILE_TYPE = new FileTypeFilter();

    private static final FileFilter TEMPLATE_FILE_TYPE = new TemplateFileTypeFilter();

    private static final List RULE;


    private static final String CREATE_RULE = "*.jsp;*.xml;*.shtml;*.htm;*.html;*.js;*.css;*.txt;";

    private static SiteGroupService siteService = SiteGroupService.getInstance();

    private static CommonSystemService csService = CommonSystemService.getInstance();

    public static Map cacheManager = new HashMap();

    static
    {
        cacheManager.put( "treeItemInfo", new ClusterCacheAdapter( 500,
            "resourcesService.treeItemInfo" ) );

        RULE = new ArrayList();

        RULE.add( "jsp" );
        RULE.add( "html" );
        RULE.add( "htm" );
        RULE.add( "js" );
        RULE.add( "css" );
        RULE.add( "txt" );
        RULE.add( "xml" );
    }

    private static ResourcesService service = null;
    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );
    private ResourcesDao resourcesDao;
    private TemplateDao templateDao = null;
    private CommonSystemDao csDao = null;
    private SiteGroupDao siteGroupDao;

    private ResourcesService()
    {
        this.resourcesDao = new ResourcesDao( this.mysqlEngine );

        this.templateDao = new TemplateDao( this.mysqlEngine );

        this.csDao = new CommonSystemDao( this.mysqlEngine );

        this.siteGroupDao = new SiteGroupDao( this.mysqlEngine );
    }

    private static synchronized void init()
    {
        if( service == null )
        {
            service = new ResourcesService();
        }
    }

    public static ResourcesService getInstance()
    {
        if( service == null )
        {
            init();
        }
        return service;
    }

    public Map retrieveSingleResUseStatus( Long resId )
    {
        return resourcesDao.queryResUseStatus( resId );
    }

    public List retrieveImgClassInfoBeanByParentId( Long parentIdValue )
    {
        return this.resourcesDao.queryImgClassInfoBeanByParentId( parentIdValue );
    }

    public Map retrieveImgClassTreeItemBeanByParentId( Long parentIdValue )
    {
        if( parentIdValue == null )
        {
            return Collections.EMPTY_MAP;
        }
        Cache itemCache = ( Cache ) cacheManager.get( "treeItemInfo" );

        Map itemValueMap = ( Map ) itemCache.getEntry( "treeiItem:" + parentIdValue );
        if( itemValueMap == null )
        {
            List imgClassInfoBeanList = this.resourcesDao
                .queryImgClassInfoBeanByParentId( parentIdValue );

            itemValueMap = new TreeMap();
            ImgClassTreeItemBean item = null;
            for ( int i = 0; i < imgClassInfoBeanList.size(); i++ )
            {
                item = ( ImgClassTreeItemBean ) imgClassInfoBeanList.get( i );

                itemValueMap.put( Integer.valueOf( i + 1 ), item );
            }
            itemCache.putEntry( "treeiItem:" + parentIdValue, itemValueMap );
        }
        return itemValueMap;
    }

    public UpdateState addSiteResourceAndUploadTrace( SiteResource resInfo )
    {
        UpdateState us = this.resourcesDao.saveResourceInfo( resInfo );
        if( us.haveKey() )
        {
            SiteResourceTrace srt = new SiteResourceTrace();

            srt.setResId( Long.valueOf( us.getKey() ) );
            srt.setIsUse( Constant.COMMON.OFF );
            srt.setUploadDate( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

            this.resourcesDao.save( srt );
        }
        return us;
    }

    public UpdateState addSiteResourceAndUploadTraceSuccessStatus( SiteResource resInfo )
    {
        UpdateState us = this.resourcesDao.saveResourceInfo( resInfo );
        if( us.haveKey() )
        {
            SiteResourceTrace srt = new SiteResourceTrace();

            srt.setResId( Long.valueOf( us.getKey() ) );
            srt.setIsUse( Constant.COMMON.ON );
            srt.setUploadDate( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

            this.resourcesDao.save( srt );
        }
        return us;
    }

    public List retrieveImageInfoBeanByClassId( Long imgClassId )
    {
        return this.resourcesDao.queryImageInfoBeanByClassId( imgClassId );
    }

    public List disposeZIPImageFile( String targerFilePath, String imageResPath, String sitePath,
        String rootPath, Long imgClassId )
    {
        log.info( "disposeZIPImageFile()  targerFilePath:" + targerFilePath + " ,imageResPath"
            + imageResPath + " ,sitePath" + sitePath );

        if( imgClassId == null )
        {
            log.info( "处理的目标文件对应ID丢失,targetFile:" + targerFilePath );
            return null;

        }

        Map resultZip = FileUtil.unZipFile( targerFilePath, imageResPath, true );

        log.info( "开始生成实际访问文件" );

        Iterator iter = resultZip.entrySet().iterator();
        String fileName = "";
        Entry entry = null;

        List imgBeanList = new ArrayList();
        while ( iter.hasNext() )
        {
            entry = ( Entry ) iter.next();
            fileName = ( String ) entry.getKey();
            File targetImageFile = new File( ( String ) entry.getValue() );

            boolean isOk = checkImageFile( targetImageFile );

            if( isOk )
            {

                // 缩略图
                String resize = imageResPath + "imgResize" + targetImageFile.getName();

                try
                {
                    ImageUtil.zoomPicture( targetImageFile, resize, 294 );
                }
                catch ( IOException e )
                {
                    log.error( e );
                    e.printStackTrace();
                }

                // 组装图片信息bean

                Object[] imgOffset = ImageUtil.getImageHeightAndWidth( imageResPath
                    + targetImageFile.getName() );

                SiteResource imgInfo = new SiteResource();
                imgInfo.setHeight( ( Integer ) imgOffset[1] );
                imgInfo.setWidth( ( Integer ) imgOffset[0] );

                imgInfo.setClassId( imgClassId );
                imgInfo
                    .setResName( StringUtil.subString( fileName, 0, fileName.lastIndexOf( "." ) ) );
                // TODO imgInfo.setFileType( fileName
                // .substring( fileName.indexOf( "." ) + 1 ) );

                Timestamp modifyTime;
                if( ( ( Long ) imgOffset[2] ).longValue() != 0 )
                {
                    modifyTime = new Timestamp( ( ( Long ) imgOffset[2] ).longValue() );
                }
                else
                {
                    modifyTime = new Timestamp( DateAndTimeUtil.clusterTimeMillis() );
                }
                imgInfo.setModifyTime( modifyTime );
                imgInfo.setResSize( Long.valueOf( targetImageFile.length() ) );
                imgInfo.setResSource( targetImageFile.getName() );

                imgBeanList.add( imgInfo );

            }
        }
        return imgBeanList;
        // System.gc();
    }

    public Map disposeZIPFile( String targerZIPFilePath, String resFileRootPath )
    {
        log.info( "disposeZIPFile()  targerFilePath:" + targerZIPFilePath + " ,resFileRootPath"
            + resFileRootPath );
        if( targerZIPFilePath == null )
        {
            return null;
        }
        if( !new File( targerZIPFilePath ).exists() )
        {
            return null;
        }
        Map resultZipInfo = FileUtil.unZipFile( targerZIPFilePath, resFileRootPath, false );

        return resultZipInfo;
    }

    public void addNewResourceList( List imgBeanList )
    {
        if( imgBeanList != null )
        {
            try
            {
                this.mysqlEngine.beginTransaction();

                SiteResource resBean = null;
                for ( int i = 0; i < imgBeanList.size(); i++ )
                {
                    resBean = ( SiteResource ) imgBeanList.get( i );
                    UpdateState us = addSiteResourceAndUploadTrace( resBean );
                    if( us.haveKey() )
                    {
                        resBean.setResId( Long.valueOf( us.getKey() ) );
                    }
                }
                this.mysqlEngine.commit();
            }
            finally
            {
                this.mysqlEngine.endTransaction();
            }
        }
    }

    public void deleteResourceByPath( String path )
    {
        this.resourcesDao.deleteResInfoByRePath( path );
    }

    public Integer getImageAllCount( Long targetClassId )
    {
        Integer result = this.resourcesDao.queryImageAllCount( targetClassId );

        return result;
    }

    private boolean checkImageFile( File targetFile )
    {

        boolean fileEndFlagRight = true;
        String name = targetFile.getName();
        String end = "";

        end = StringUtil.subString( name, name.indexOf( "." ) + 1, name.length() );

        if( !( "jpg".equalsIgnoreCase( end ) || "gif".equalsIgnoreCase( end )
            || "tif".equalsIgnoreCase( end ) || "jpeg".equalsIgnoreCase( end )
            || "png".equalsIgnoreCase( end ) || "bmp".equalsIgnoreCase( end ) ) )
        {
            fileEndFlagRight = false;
        }

        // 检查是否为html类型文本若后缀名已错,立即处理,若不错,进一步检查类型
        if( !fileEndFlagRight )
        {
            deleteErrorFileAndResponse( targetFile );
        }

        return fileEndFlagRight;

    }

    private void deleteErrorFileAndResponse( File targetFile )
    {
        targetFile.delete();// 删除非法文件
        if( targetFile.exists() )
        {
            // 尝试虚拟机关闭时删除
            // targetFile.deleteOnExit();
            log.error( "严重错误!所上传的文件非图片文件,且无法删除非法文件[" + targetFile.getName()
                + "],这可能造成系统安全隐患，请联系 系统管理员 !" );
            // throw new FrameworkException( "严重错误!所上传的文件非图片文件,且无法删除非法文件["
            // + targetFile.getName() + "],这可能造成系统安全隐患，请联系 系统管理员 !" );
        }

        // throw new FrameworkException( "所上传的文件非图片文件!" );
    }

    public List retrieveLimitImageByImgClassID( Long classId, Long flagImageId, int limitSize,
        int pageActionFlag )
    {
        List resList = Collections.EMPTY_LIST;
        if( ( classId == null ) || ( flagImageId == null ) )
        {
            return resList;
        }
        if( ( pageActionFlag == -1 ) || ( pageActionFlag == -2 ) )
        {
            resList = this.resourcesDao.queryLimitImageByClassIDAndFlagImageId( "asc", classId,
                flagImageId, limitSize );
        }
        else if( ( pageActionFlag == 1 ) || ( pageActionFlag == 2 ) )
        {
            resList = this.resourcesDao.queryLimitImageByClassIDAndFlagImageId( "desc", classId,
                flagImageId, limitSize );
        }
        return resList;
    }

    public Long retrieveImgClassIdForEndPageMode( Long targetClassId )
    {
        return this.resourcesDao.queryImgClassIdForEndPageMode( targetClassId );
    }

    public void setImageMarkStatus( Long resId, Integer status )
    {
        this.resourcesDao.updateImageMarkStatus( resId, status );
    }

    public void setImageMarkStatus( String reUrl, Integer status )
    {
        this.resourcesDao.updateImageMarkStatus( reUrl, status );
    }

    public Integer getImageMarkStatus( String reUrl )
    {
        return this.resourcesDao.queryImageMarkStatus( reUrl );
    }

    public Integer getImageMarkStatus( Long resId )
    {
        return this.resourcesDao.queryImageMarkStatus( resId );
    }

    public List retrieveSiteFolderAndFileTreeByParentFolder( String entry, String mode,
        boolean templateMode )
    {
        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();
        if( siteBean == null || StringUtil.isStringNull( siteBean.getSiteRoot() ) )
        {
            return Collections.EMPTY_LIST;
        }

        String baseRealPath = SystemConfiguration.getInstance().getSystemConfig()
            .getSystemRealPath();

        String rootPath = baseRealPath + siteBean.getSiteRoot();

        String fullPath = "";
        if( StringUtil.isStringNull( entry ) )
        {
            fullPath = rootPath;
        }
        else
        {
            entry = SystemSafeCharUtil.decodeFromWeb( entry );

            if( ( entry.indexOf( "../" ) != -1 ) || ( entry.indexOf( "..%2F" ) != -1 )
                || ( entry.indexOf( "..%2f" ) != -1 ) || ( entry.indexOf( "WEB-INF" ) != -1 ) )
            {
                return Collections.EMPTY_LIST;
            }
            String endEntry = StringUtil.replaceString( entry, SPLIT_CHAR, File.separator, false,
                false );

            log.info( "最终入口:" + endEntry );

            fullPath = rootPath + endEntry;
        }
        List fileResultList = new ArrayList();

        File target = null;

        File[] folders = FileUtil.getAllFile( fullPath, FOLDER_TYPE );

        File[] files = ( File[] ) null;
        if( "all".equals( mode ) )
        {
            if( templateMode )
            {
                files = FileUtil.getAllFile( fullPath, TEMPLATE_FILE_TYPE );
            }
            else
            {
                files = FileUtil.getAllFile( fullPath, FILE_TYPE );
            }
        }
        String targetPath = "";
        if( folders != null )
        {
            for ( int i = 0; i < folders.length; i++ )
            {
                FileInfoBean bean = new FileInfoBean();
                target = folders[i];

                File[] testHaveFolder = target.listFiles( FOLDER_TYPE );
                if( testHaveFolder.length == 0 )
                {
                    bean.setHaveDir( Boolean.FALSE );
                }
                else
                {
                    bean.setHaveDir( Boolean.TRUE );
                }
                bean.setFileName( target.getName() );
                bean.setLastModifyTime( DateAndTimeUtil.getFormatDate( target.lastModified(),
                    "yyyy-MM-dd HH:mm:ss" ) );

                targetPath = StringUtil.replaceString( target.getPath(), rootPath, "", false, true );

                bean.setEntry( StringUtil.replaceString( targetPath, File.separator, SPLIT_CHAR,
                    false, false ) );

                bean.setSize( Long.valueOf( 0L ) );
                bean.setIsDir( Boolean.TRUE );
                fileResultList.add( bean );
            }
        }
        if( files != null )
        {
            for ( int j = 0; j < files.length; j++ )
            {
                FileInfoBean bean = new FileInfoBean();
                target = files[j];
                bean.setFileName( target.getName() );
                bean.setLastModifyTime( DateAndTimeUtil.getFormatDate( target.lastModified(),
                    "yyyy-MM-dd HH:mm:ss" ) );
                targetPath = StringUtil.replaceString( target.getPath(), rootPath, "", false, true );

                bean.setEntry( StringUtil.replaceString( targetPath, File.separator, SPLIT_CHAR,
                    false, false ) );

                String fileName = target.getName();
                String fileType = StringUtil.subString( fileName, fileName.lastIndexOf( "." ) + 1,
                    fileName.length() );

                bean.setCreator( "Admin" );

                bean.setType( fileType.toLowerCase() );
                bean.setSize( Long.valueOf( target.length() ) );
                bean.setIsDir( Boolean.FALSE );
                fileResultList.add( bean );
            }
        }
        return fileResultList;
    }

    public TextBean retrieveSingleSiteTextFileContentInfo( String entry, boolean fullInfoMode )
    {
        String fullPath = "";
        if( StringUtil.isStringNull( entry ) )
        {
            return new TextBean();
        }
        if( ( entry.indexOf( "../" ) != -1 ) || ( entry.indexOf( "..%2F" ) != -1 )
            || ( entry.indexOf( "..%2f" ) != -1 ) || ( entry.indexOf( "WEB-INF" ) != -1 ) )
        {
            return new TextBean();
        }
        String endEntry = StringUtil
            .replaceString( entry, SPLIT_CHAR, File.separator, false, false );

        log.info( "最终入口:" + endEntry );

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String baseRealPath = SystemConfiguration.getInstance().getSystemConfig()
            .getSystemRealPath();

        String rootPath = baseRealPath + siteBean.getSiteRoot();

        fullPath = rootPath + endEntry;
        if( fullInfoMode )
        {
            Object[] value = FileUtil.getTXTFileContent( fullPath, RULE, null );

            TextBean bean = new TextBean();
            bean.setContent( ( String ) value[0] );
            bean.setLineCount( ( Integer ) value[1] );
            bean.setCharset( ( String ) value[2] );

            return bean;
        }
        TextBean bean = new TextBean();

        File file = new File( fullPath.trim() );
        if( !file.exists() )
        {
            log.info( "指定的文件不存在" );
            bean.setCharset( "" );
        }
        if( ( !file.isFile() ) || ( file.isHidden() ) )
        {
            log.info( "指定的文件不是一个文件或被系统隐藏" );
            bean.setCharset( "" );
        }
        String currentCode = FileUtil.getTXTFileCode( file );

        currentCode = "UTF-8".equalsIgnoreCase( currentCode ) ? "UTF-8" : "GBK";
        if( StringUtil.isStringNotNull( currentCode ) )
        {
            bean.setCharset( currentCode );
        }
        return bean;
    }

    public void recoverSingleTemplateFileContent( String fileEntry, Long etId, String content,
        String charset )
    {
        Map edTempInfo = this.templateDao.querySingleTemplateEditionInfo( etId );

        String templateFullPath = getFullFilePathByManager( fileEntry );
        if( templateFullPath == null )
        {
            return;
        }
        try
        {
            FileUtil.writeTXTFileContent( content, templateFullPath, charset );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String entry = fileEntry;
        if( ( entry.toLowerCase().lastIndexOf( ".jsp" ) != -1 || entry.toLowerCase().lastIndexOf(
            ".thtml" ) != -1 )
            || ( entry.toLowerCase().lastIndexOf( ".css" ) != -1 )
            || ( entry.toLowerCase().lastIndexOf( ".js" ) != -1 )
            || ( entry.toLowerCase().lastIndexOf( ".html" ) != -1 ) )
        {
            String ftName = ".jsp";
            if( entry.toLowerCase().lastIndexOf( ".css" ) != -1 )
            {
                ftName = ".css";
            }
            else if( entry.toLowerCase().lastIndexOf( ".js" ) != -1 )
            {
                ftName = ".js";
            }
            else if( entry.toLowerCase().lastIndexOf( ".html" ) != -1 )
            {
                ftName = ".html";
            }
            else if( entry.toLowerCase().lastIndexOf( ".thtml" ) != -1 )
            {
                ftName = ".thtml";
            }
            String tempalteName = ( String ) edTempInfo.get( "templateName" );

            String temp = "*sys_template_temp*" + tempalteName;

            temp = StringUtil.replaceString( temp, ftName, "_" + StringUtil.getUUIDString()
                + ftName, false, false );

            String fullTempPath = getFullFilePathByManager( temp );
            if( fullTempPath == null )
            {
                return;
            }
            try
            {
                FileUtil.writeTXTFileContent( content, fullTempPath, charset );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
            Long userId = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth()
                .getIdentity();

            Map style = this.templateDao.querySingleTemplateStyleByUse( site.getSiteId(),
                Constant.COMMON.ON );
            if( style.isEmpty() )
            {
                style.put( "styleFlag", "_sys_def_" );
            }
            this.templateDao.addTemplateEditionInfo( tempalteName, temp, "还原到版本" + etId, userId,
                new Date( DateAndTimeUtil.clusterTimeMillis() ), site.getSiteId(), ( String ) style
                    .get( "styleFlag" ) );

            String systemRoot = SystemConfiguration.getInstance().getSystemConfig()
                .getSystemRealPath();

            // 集群模式发送文件到节点
            if( JtRuntime.cmsServer.getClusterMode() )
            {

                ClusterService.getInstance().broadcastFileToCluster(
                    StringUtil.replaceString( templateFullPath, systemRoot, "", false, false ),
                    templateFullPath );

            }
        }
    }

    public void writeSingleTemplateFileContent( String editDesc, String entry, String content,
        String charset )
    {
        String fullPath = getFullFilePathByManager( entry );
        if( ( fullPath == null ) || ( fullPath.indexOf( "sys_template_temp" ) != -1 ) )
        {
            return;
        }
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String oldContent = ( String ) FileUtil.readTXTFileContent( new File( fullPath ), charset )[0];

        try
        {
            this.mysqlEngine.beginTransaction();

            FileUtil.writeTXTFileContent( content, fullPath, charset );
            if( ( entry.toLowerCase().lastIndexOf( ".jsp" ) != -1 || entry.toLowerCase()
                .lastIndexOf( ".thtml" ) != -1 )
                || ( entry.toLowerCase().lastIndexOf( ".css" ) != -1 )
                || ( entry.toLowerCase().lastIndexOf( ".js" ) != -1 )
                || ( entry.toLowerCase().lastIndexOf( ".html" ) != -1 ) )
            {
                String ftName = ".jsp";
                if( entry.toLowerCase().lastIndexOf( ".css" ) != -1 )
                {
                    ftName = ".css";
                }
                else if( entry.toLowerCase().lastIndexOf( ".js" ) != -1 )
                {
                    ftName = ".js";
                }
                else if( entry.toLowerCase().lastIndexOf( ".html" ) != -1 )
                {
                    ftName = ".html";
                }
                else if( entry.toLowerCase().lastIndexOf( ".thtml" ) != -1 )
                {
                    ftName = ".thtml";
                }
                String tempalteName = StringUtil.subString( entry,
                    entry.lastIndexOf( SPLIT_CHAR ) + 1 );

                Map style = this.templateDao.querySingleTemplateStyleByUse( site.getSiteId(),
                    Constant.COMMON.ON );
                if( style.isEmpty() )
                {
                    style.put( "styleFlag", "_sys_def_" );
                }
                Long tCount = this.templateDao.querySingleTemplateEditionCountByTName(
                    tempalteName, site.getSiteId(), ( String ) style.get( "styleFlag" ) );

                boolean needFirstEd = false;
                if( tCount.longValue() < 1L )
                {
                    needFirstEd = true;
                }
                String realPath = SystemConfiguration.getInstance().getSystemConfig()
                    .getSystemRealPath();

                File testFile = new File( realPath + site.getSiteRoot() + File.separator
                    + "sys_template_temp" );
                if( !testFile.exists() )
                {
                    testFile.mkdirs();
                }
                String temp = "*sys_template_temp*" + tempalteName;

                StringUtil.replaceString( entry, "template", "sys_template_temp", false, false );

                String tempNewEd = StringUtil.replaceString( temp, ftName, "_"
                    + StringUtil.getUUIDString() + ftName, false, false );

                String firstEdTemp = StringUtil.replaceString( temp, ftName, "_"
                    + StringUtil.getUUIDString() + ftName, false, false );

                String fullTempPath = getFullFilePathByManager( tempNewEd );

                String firstEdFullTempPath = getFullFilePathByManager( firstEdTemp );

                FileUtil.writeTXTFileContent( content, fullTempPath, charset );

                if( needFirstEd )
                {
                    FileUtil.writeTXTFileContent( oldContent, firstEdFullTempPath, charset );
                }
                Long userId = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth()
                    .getIdentity();
                if( needFirstEd )
                {
                    this.templateDao.addTemplateEditionInfo( tempalteName, firstEdTemp, "初始版本模板",
                        userId, new Date( DateAndTimeUtil.clusterTimeMillis() ), site.getSiteId(),
                        ( String ) style.get( "styleFlag" ) );
                }
                this.templateDao.addTemplateEditionInfo( tempalteName, tempNewEd, editDesc, userId,
                    new Date( DateAndTimeUtil.clusterTimeMillis() ), site.getSiteId(),
                    ( String ) style.get( "styleFlag" ) );
            }
            this.mysqlEngine.commit();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            this.mysqlEngine.endTransaction();
        }

        String systemRoot = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        // 集群模式发送文件到节点
        if( JtRuntime.cmsServer.getClusterMode() )
        {

            ClusterService.getInstance().broadcastFileToCluster(
                StringUtil.replaceString( fullPath, systemRoot, "", false, false ), fullPath );

        }
    }

    public void deleteSiteFileOrFolderInfo( String[] targetFileArray, String entry,
        String templateMode )
    {
        if( ( targetFileArray == null ) || ( entry == null ) )
        {
            return;
        }
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String fullRootPath = getFullFilePathByManager( entry );
        if( fullRootPath == null )
        {
            return;
        }
        Map style = this.templateDao.querySingleTemplateStyleByUse( site.getSiteId(),
            Constant.COMMON.ON );
        if( style.isEmpty() )
        {
            style.put( "styleFlag", "_sys_def_" );
        }
        String filePath = null;
        File target = null;
        String fileFullPath = null;

        String cmsBasePath = SystemConfiguration.getInstance().getSystemConfig()
            .getSystemRealPath();

        for ( int i = 0; i < targetFileArray.length; i++ )
        {
            filePath = targetFileArray[i];
            if( StringUtil.isStringNotNull( filePath ) )
            {
                fileFullPath = fullRootPath + File.separator + filePath;
                target = new File( fileFullPath );
                if( target.isFile() )
                {
                    FileUtil.delFile( fileFullPath );

                    ClusterService.exeClusterMasterCMD( "cluster/deleteFile.do", "fp="
                        + StringUtil.replaceString( StringUtil.replaceString( fileFullPath,
                            cmsBasePath, "" ), File.separator, "/" ), Constant.COMMON.POST );

                    if( "true".equals( templateMode ) )
                    {
                        List edList = this.templateDao.queryTemplateEditionInfoList( target
                            .getName(), site.getSiteId(), ( String ) style.get( "styleFlag" ) );

                        Map edInfo = null;

                        String tempFullRootPath = null;
                        for ( int j = 0; j < edList.size(); j++ )
                        {
                            edInfo = ( Map ) edList.get( j );

                            tempFullRootPath = getFullFilePathByManager( ( String ) edInfo
                                .get( "filePath" ) );
                            if( tempFullRootPath != null )
                            {
                                FileUtil.delFile( tempFullRootPath );

                                ClusterService.exeClusterMasterCMD( "cluster/deleteFile.do", "fp="
                                    + StringUtil.replaceString( StringUtil.replaceString(
                                        tempFullRootPath, cmsBasePath, "" ), File.separator, "/" ),
                                    Constant.COMMON.POST );
                            }
                        }
                        this.templateDao.deleteTemplateEditionInfoByTName( target.getName(), site
                            .getSiteId() );
                    }
                }
                else if( target.isDirectory() )
                {
                    FileUtil.delFolder( fileFullPath );

                    ClusterService.exeClusterMasterCMD( "cluster/deleteFolder.do", "fp="
                        + StringUtil.replaceString( StringUtil.replaceString( fileFullPath,
                            cmsBasePath, "" ), File.separator, "/" ), Constant.COMMON.POST );
                }
            }
        }
    }

    public void moveFile( String entry, String[] targetFileArray, String targetFolder )
    {
        if( ( targetFileArray == null ) || ( targetFolder == null ) )
        {
            return;
        }

        String fullRootPath = getFullFilePathByManager( entry );
        if( fullRootPath == null )
        {
            return;
        }
        String fullTargetRootPath = getFullFilePathByManager( targetFolder );
        if( fullRootPath == null )
        {
            return;
        }
        String filePath = null;

        File target = null;

        File newFileTarget = null;

        String fileFullPath = null;

        String newFileFullPath = null;

        String cmsBasePath = SystemConfiguration.getInstance().getSystemConfig()
            .getSystemRealPath();

        for ( int i = 0; i < targetFileArray.length; i++ )
        {
            filePath = targetFileArray[i];
            if( StringUtil.isStringNotNull( filePath ) )
            {
                fileFullPath = SystemSafeCharUtil.decodeFromWeb( fullRootPath + File.separator
                    + filePath );

                newFileFullPath = SystemSafeCharUtil.decodeFromWeb( fullTargetRootPath
                    + File.separator + filePath );

                target = new File( fileFullPath );
                if( target.isDirectory() )
                {

                    FileUtil.moveFolder( fileFullPath, newFileFullPath );

                    ClusterService.exeClusterMasterCMD( "cluster/moveFolder.do", "ofp="
                        + StringUtil.replaceString( StringUtil.replaceString( fileFullPath,
                            cmsBasePath, "" ), File.separator, "/" )
                        + "&nfp="
                        + StringUtil.replaceString( StringUtil.replaceString( newFileFullPath,
                            cmsBasePath, "" ), File.separator, "/" ), Constant.COMMON.POST );
                }
                else
                {
                    newFileTarget = new File( newFileFullPath );
                    if( target.isFile() )
                    {
                        target.renameTo( newFileTarget );

                        ClusterService.exeClusterMasterCMD( "cluster/renameFile.do", "ofp="
                            + StringUtil.replaceString( StringUtil.replaceString( fileFullPath,
                                cmsBasePath, "" ), File.separator, "/" )
                            + "&nfp="
                            + StringUtil.replaceString( StringUtil.replaceString( newFileFullPath,
                                cmsBasePath, "" ), File.separator, "/" ), Constant.COMMON.POST );
                    }
                }
            }
        }
    }

    public void renameFileOrFolder( String newName, String entry, String action, String tip )
    {
        if( entry == null )
        {
            return;
        }
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String fullRootPath = getFullFilePathByManager( entry );
        if( fullRootPath == null )
        {
            return;
        }

        String cmsBasePath = SystemConfiguration.getInstance().getSystemConfig()
            .getSystemRealPath();

        if( "file".equals( action ) )
        {
            File file = new File( fullRootPath );

            String fileName = file.getName();

            String fileType = StringUtil.subString( entry, entry.lastIndexOf( "." ) );
            if( StringUtil.isStringNull( newName ) )
            {
                newName = fileName;
            }
            String newFileName = newName + fileType;

            String newFullPath = StringUtil.replaceString( fullRootPath, fileName, newFileName,
                false, false );

            boolean renameSucess = file.renameTo( new File( newFullPath ) );
            if( renameSucess )
            {
                ClusterService.exeClusterMasterCMD( "cluster/renameFile.do", "ofp="
                    + StringUtil.replaceString( StringUtil.replaceString( fullRootPath,
                        cmsBasePath, "" ), File.separator, "/" )
                    + "&nfp="
                    + StringUtil.replaceString( StringUtil.replaceString( newFullPath, cmsBasePath,
                        "" ), File.separator, "/" ), Constant.COMMON.POST );

                boolean needChange = false;
                if( fileName.toLowerCase().lastIndexOf( ".jsp" ) != -1
                    || fileName.toLowerCase().lastIndexOf( ".thtml" ) != -1 )
                {
                    needChange = true;
                }
                else if( fileName.toLowerCase().lastIndexOf( ".css" ) != -1 )
                {
                    needChange = true;
                }
                else if( fileName.toLowerCase().lastIndexOf( ".js" ) != -1 )
                {
                    needChange = true;
                }
                else if( fileName.toLowerCase().lastIndexOf( ".html" ) != -1 )
                {
                    needChange = true;
                }
                if( needChange )
                {
                    Map style = this.templateDao.querySingleTemplateStyleByUse( site.getSiteId(),
                        Constant.COMMON.ON );
                    if( style.isEmpty() )
                    {
                        style.put( "styleFlag", "_sys_def_" );
                    }
                    this.templateDao.updateTemplateName( site.getSiteId(), fileName, newFileName,
                        ( String ) style.get( "styleFlag" ) );
                }
                Map style = this.templateDao.querySingleTemplateStyleByUse( site.getSiteId(),
                    Constant.COMMON.ON );
                if( style.isEmpty() )
                {
                    style.put( "styleFlag", "_sys_def_" );
                }
                if( needChange )
                {
                    this.templateDao.updateTemplateNameForTip( site.getSiteId(), fileName,
                        newFileName, ( String ) style.get( "styleFlag" ) );
                }
                if( StringUtil.isStringNotNull( tip ) )
                {
                    Map tipRes = this.templateDao.querySingleTemplateTipHelper( site.getSiteId(),
                        newFileName, ( String ) style.get( "styleFlag" ) );
                    if( tipRes.isEmpty() )
                    {
                        this.templateDao.saveTemplateTipHelper( site.getSiteId(), newFileName, tip,
                            ( String ) style.get( "styleFlag" ) );
                    }
                    else
                    {
                        this.templateDao.updateTemplateTipHelper( site.getSiteId(), newFileName,
                            tip, ( String ) style.get( "styleFlag" ) );
                    }
                }
            }
        }
        else if( "folder".equals( action ) )
        {
            File file = new File( fullRootPath );

            String fileName = file.getName();

            String newFileName = newName;

            String newFullPath = StringUtil.replaceString( fullRootPath, fileName, newFileName,
                false, false );

            file.renameTo( new File( newFullPath ) );

            ClusterService.exeClusterMasterCMD( "cluster/renameFile.do",
                "ofp="
                    + StringUtil.replaceString( StringUtil.replaceString( fullRootPath,
                        cmsBasePath, "" ), File.separator, "/" )
                    + "&nfp="
                    + StringUtil.replaceString( StringUtil.replaceString( newFullPath, cmsBasePath,
                        "" ), File.separator, "/" ), Constant.COMMON.POST );

        }
    }

    public void createFileOrFolder( String name, String entry, String action )
    {
        if( ( name == null ) || ( entry == null ) )
        {
            return;
        }
        String fullRootPath = getFullFilePathByManager( entry );
        if( fullRootPath == null )
        {
            return;
        }
        if( "file".equals( action ) )
        {
            /**
             * 业务已更新为禁止创建文件
             */
            // String end = "";
            //
            // end = StringUtil.subString( name, name.lastIndexOf( "." ) + 1,
            // name.length() )
            // .toLowerCase();
            // if( RULE.indexOf( end.toLowerCase() ) == -1 )
            // {
            // return;
            // }
            // FileUtil.newFile( fullRootPath + File.separator + name, "" );
        }
        else if( "folder".equals( action ) )
        {
            FileUtil.newFolder( fullRootPath + File.separator + name );

            String cmsBasePath = SystemConfiguration.getInstance().getSystemConfig()
                .getSystemRealPath();

            ClusterService.exeClusterMasterCMD( "cluster/newFolder.do", "fp="
                + StringUtil.replaceString( StringUtil.replaceString( fullRootPath + File.separator
                    + name, cmsBasePath, "" ), File.separator, "/" ), Constant.COMMON.POST );

        }
    }

    public Integer retrieveResCountByClassIdAndResType( String targetClassIds, Integer resTypeVal )
    {
        return this.resourcesDao.queryResCountByClassIdAndResType( targetClassIds, resTypeVal );
    }

    public List retrieveResourceBeanByClassIdAndResType( String targetClassIds, Integer resTypeVal,
        Long startPos, Integer limit )
    {
        return this.resourcesDao.queryResourceBeanByClassIdAndResType( targetClassIds, resTypeVal,
            startPos, limit );
    }

    public SiteResourceBean retrieveSingleResourceBeanByResId( Long resId )
    {
        return this.resourcesDao.querySingleResourceBeanByResId( resId );
    }

    public SiteResourceBean retrieveSingleResourceBeanBySource( String source )
    {
        return this.resourcesDao.querySingleResourceBeanBySource( source );
    }

    public void updateSingleVideoResourceCoverByResId( String relateImageUrl, Long resId )
    {
        this.resourcesDao.updateSingleVideoResourceCover( relateImageUrl, resId );
    }

    public void updateSiteResourceTraceUseStatus( Long resId, Integer isUse )
    {
        this.resourcesDao.updateResUseStatus( resId, isUse );
    }

    public void updateSiteResourceTraceUseStatus( String resInfo, Integer isUse )
    {
        Long resId = ServiceUtil.getResId( resInfo );
        if( resId.longValue() < 1 )
        {
            return;
        }
        SiteResourceBean resBean = retrieveSingleResourceBeanByResId( resId );
        if( resBean == null )
        {
            return;
        }
        this.resourcesDao.updateResUseStatus( resId, isUse );
    }

    public void updateImageResourceWHL( Long resId, Integer w, Integer h, Long size )
    {
        this.resourcesDao.updateImageWHL( resId, w, h, size );
    }

    public SiteResource copyNewResourceAndFile( Long resId, Long classId )
    {
        UpdateState us = null;
        SiteResource newRes = new SiteResource();

        String oldFilePath = null;

        String oldImgResizeFilePath = null;

        String newImgResizeFilePath = null;

        String newFilePath = null;

        try
        {
            mysqlEngine.beginTransaction();

            SiteResourceBean res = resourcesDao.querySingleResourceBeanByResId( resId );

            if( res == null )
            {
                return null;
            }

            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( res.getSiteId() );

            SiteGroupBean currSite = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            String cmsBasePath = SystemConfiguration.getInstance().getSystemConfig()
                .getSystemRealPath();

            // 当前时间日期
            String day = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

            Integer resType = res.getResType();

            String source = res.getResSource();

            String newName = DateAndTimeUtil.clusterTimeMillis() + StringUtil.getUUIDString() + "."
                + res.getFileType();

            String newSource = day + File.separator + newName;

            String resRootDir = null;

            if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( resType ) )
            {
                resRootDir = site.getImageRoot();
            }
            else if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( resType ) )
            {
                resRootDir = site.getMediaRoot();
            }
            else if( Constant.RESOURCE.ANY_RES_TYPE.equals( resType ) )
            {
                resRootDir = site.getFileRoot();
            }

            // 确定新文件夹是否存在,因为日期在0点将变化
            File dir = new File( cmsBasePath + currSite.getSiteRoot() + File.separator + resRootDir
                + File.separator + day );

            if( !dir.exists() )
            {
                dir.mkdirs();
            }

            newFilePath = cmsBasePath + currSite.getSiteRoot() + File.separator + resRootDir
                + File.separator + newSource;

            oldFilePath = cmsBasePath + site.getSiteRoot() + File.separator + resRootDir
                + File.separator
                + StringUtil.replaceString( source, "/", File.separator, false, false );

            if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( resType ) )
            {
                oldImgResizeFilePath = cmsBasePath
                    + site.getSiteRoot()
                    + File.separator
                    + resRootDir
                    + File.separator
                    + StringUtil.replaceString( StringUtil.replaceString( source, "/",
                        "/imgResize", false, false ), "/", File.separator, false, false );

                newImgResizeFilePath = cmsBasePath + currSite.getSiteRoot() + File.separator
                    + resRootDir + File.separator + day + File.separator + "imgResize" + newName;
            }

            File checkFile = new File( oldFilePath );

            if( !checkFile.exists() )
            {
                return null;
            }

            newRes.setClassId( classId );
            newRes.setSiteId( currSite.getSiteId() );
            newRes.setFileType( res.getFileType() );
            newRes.setResType( res.getResType() );
            newRes.setResName( res.getResName() );
            newRes.setResSource( StringUtil.replaceString( newSource, File.separator, "/", false,
                false ) );
            newRes.setResSize( res.getResSize() );
            newRes.setWidth( res.getWidth() );
            newRes.setHeight( res.getHeight() );
            newRes.setDuration( res.getDuration() );
            newRes.setResolution( res.getResolution() );
            // 注意!不需要cover
            newRes.setModifyTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

            us = addSiteResourceAndUploadTrace( newRes );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        if( us != null && us.haveKey() )
        {
            // 考虑拷贝文件的时间较长,文件拷贝需要放在数据库事务外
            FileUtil.copyFile( oldFilePath, newFilePath );

            if( oldImgResizeFilePath != null )
            {
                FileUtil.copyFile( oldImgResizeFilePath, newImgResizeFilePath );
            }

            newRes.setResId( Long.valueOf( us.getKey() ) );

            List resList = new ArrayList( 2 );

            resList.add( newRes );

            // 进行数据分发
            siteService.transferUpdateDataToServer( resList );

            return newRes;
        }
        else
        {
            return null;
        }

    }

    /**
     * 清除不再使用的资源,集群节点资源同步删除
     * 
     */
    public void clearUselessResource()
    {
        Calendar cal = Calendar.getInstance();

        cal.set( 12, cal.get( 12 ) - CLEAR_RES_BEFORE_MIN );

        List resInfoList = this.resourcesDao.queryResUploadTraceInfoByEndUploadDateAndUseStatus(
            cal.getTime(), Constant.COMMON.OFF );

        String cmsBasePath = SystemConfiguration.getInstance().getSystemConfig()
            .getSystemRealPath();

        SiteResourceBean bean = null;
        String urlPath = null;
        String resizePath = null;
        SiteGroupBean site = null;

        StringBuilder buf = new StringBuilder();

        for ( int i = 0; i < resInfoList.size(); i++ )
        {
            bean = ( SiteResourceBean ) resInfoList.get( i );

            site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache.getEntry( bean
                .getSiteId() );
            if( site != null )
            {
                urlPath = bean.getResSource();
                if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( bean.getResType() ) )
                {
                    String fp = cmsBasePath + site.getSiteRoot() + File.separator
                        + site.getImageRoot() + File.separator
                        + StringUtil.replaceString( urlPath, "/", File.separator, false, false );

                    FileUtil.delFile( fp );

                    buf.append( site.getSiteRoot() + "/" + site.getImageRoot() + "/" + urlPath
                        + "," );

                    resizePath = StringUtil.replaceString( urlPath, "/", File.separator
                        + "imgResize", false, false );

                    fp = cmsBasePath + site.getSiteRoot() + File.separator + site.getImageRoot()
                        + File.separator + resizePath;

                    FileUtil.delFile( fp );

                    buf.append( site.getSiteRoot() + "/" + site.getImageRoot() + "/"
                        + StringUtil.replaceString( urlPath, "/", "/imgResize", false, false )
                        + "," );

                }
                else if( ( Constant.RESOURCE.VIDEO_RES_TYPE.equals( bean.getResType() ) )
                    || ( Constant.RESOURCE.MUSIC_RES_TYPE.equals( bean.getResType() ) ) )
                {
                    String fp = cmsBasePath + site.getSiteRoot() + File.separator
                        + site.getMediaRoot() + File.separator
                        + StringUtil.replaceString( urlPath, "/", File.separator, false, false );

                    buf.append( site.getSiteRoot() + "/" + site.getMediaRoot() + "/" + urlPath
                        + "," );

                    FileUtil.delFile( fp );
                }
                else
                {
                    FileUtil.delFile( cmsBasePath + site.getSiteRoot() + File.separator
                        + site.getFileRoot() + File.separator
                        + StringUtil.replaceString( urlPath, "/", File.separator, false, false ) );

                    buf
                        .append( site.getSiteRoot() + "/" + site.getFileRoot() + "/" + urlPath
                            + "," );

                    if( "*.doc;*.docx;*.pptx;*.ppt;*.xlsx;*.xls;*.vsd;*.pdf;*.txt".indexOf( "."
                        + bean.getFileType() ) != -1 )
                    {
                        String fp = cmsBasePath + site.getSiteRoot() + File.separator
                            + site.getFileRoot() + File.separator
                            + StringUtil.replaceString( urlPath, "/", File.separator, false, false )
                            + ".swf";

                        buf.append( site.getSiteRoot() + "/" + site.getFileRoot() + "/" + urlPath
                            + ".swf" + "," );

                        FileUtil.delFile( fp );

                        fp = cmsBasePath + site.getSiteRoot() + File.separator + site.getFileRoot()
                            + File.separator
                            + StringUtil.replaceString( urlPath, "/", File.separator, false, false )
                            + ".pdf";

                        buf.append( site.getSiteRoot() + "/" + site.getFileRoot() + "/" + urlPath
                            + ".pdf" + "," );

                        FileUtil.delFile( fp );
                    }
                }
            }

            /**
             * 云存储删除文件
             */
            deleteFileFromCloud( bean );

        }

        /**
         * 集群删除文件
         */
        ClusterService.exeClusterMasterCMD( "cluster/deleteFile.do", "fp=" + buf.toString(),
            Constant.COMMON.POST );

        this.resourcesDao.deleteResInfoByEndUploadDateAndUseStatus( cal.getTime(),
            Constant.COMMON.OFF );

        this.resourcesDao.deleteResUploadTraceInfoByEndUploadDateAndUseStatus( cal.getTime(),
            Constant.COMMON.OFF );
    }

    public void addMediaResCovInfo( Long siteId, Long classId, Long resId )
    {
        this.resourcesDao.saveMediaResCov( siteId, classId, resId );
    }

    public void covertMediaResource( Long resId, String targetFileType, int qual, String eventKey,
        Map statusMap )
    {
        SiteResourceBean res = this.resourcesDao.querySingleResourceBeanByResId( resId );
        if( res == null )
        {
            return;
        }
        if( res.getFileType().equals( targetFileType.toLowerCase() ) )
        {
            return;
        }
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( res.getSiteId() );

        String sitePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String day = DateAndTimeUtil.getCunrrentDayAndTime( "yyyy-MM-dd" );

        String newFileName = DateAndTimeUtil.clusterTimeMillis() + StringUtil.getUUIDString() + "."
            + targetFileType;

        String oldPath = sitePath + site.getSiteRoot() + File.separator + site.getMediaRoot()
            + File.separator
            + StringUtil.replaceString( res.getResSource(), "/", File.separator, false, false );

        String endPath = sitePath + site.getSiteRoot() + File.separator + site.getMediaRoot()
            + File.separator + day + File.separator + newFileName;

        new File( endPath ).getParentFile().mkdirs();

        int status = MediaUtil.convertVideo( oldPath, endPath, qual, true, null, eventKey,
            statusMap );
        if( status == 1 )
        {
            File newFile = new File( endPath );
            if( newFile.length() < 0L )
            {
                return;
            }
            SiteResource resInfo = new SiteResource();

            VideoMetadataBean vmBean = MediaUtil.getMediaMetadata( newFile.getPath() );

            Integer videoSec = Integer.valueOf( 0 );
            if( vmBean != null )
            {
                int allSec = vmBean.getDurationHour().intValue() * 3600
                    + vmBean.getDurationMinute().intValue() * 60
                    + vmBean.getDurationSec().intValue();
                videoSec = Integer.valueOf( allSec );
            }
            resInfo.setDuration( videoSec );
            resInfo.setResolution( vmBean.getResolution() );

            resInfo.setResType( Constant.RESOURCE.VIDEO_RES_TYPE );

            resInfo.setClassId( res.getClassId() );

            resInfo.setSiteId( res.getSiteId() );

            resInfo.setResName( res.getResName() + "(" + targetFileType + ")" );

            resInfo.setFileType( targetFileType );

            resInfo.setModifyTime( new Timestamp( newFile.lastModified() ) );
            resInfo.setResSize( Long.valueOf( newFile.length() ) );

            resInfo.setResSource( day + "/" + newFile.getName() );

            List resList = new ArrayList();

            resList.add( resInfo );

            UpdateState dbState = addSiteResourceAndUploadTrace( ( SiteResource ) resList.get( 0 ) );
            if( ( dbState != null ) && ( dbState.getKey() > 0L ) )
            {
                siteService.transferUpdateDataToServer( resList );

                SiteResource mediaRes = ( SiteResource ) resList.get( 0 );

                addMediaResCovInfo( mediaRes.getSiteId(), mediaRes.getClassId(), Long
                    .valueOf( dbState.getKey() ) );

                updateSiteResourceTraceUseStatus( Long.valueOf( dbState.getKey() ),
                    Constant.COMMON.ON );
            }
        }
    }

    public void divideMediaResource( Long resId, String startSec, String endSec, String eventKey,
        Map statusMap )
    {
        SiteResourceBean res = this.resourcesDao.querySingleResourceBeanByResId( resId );
        if( res == null )
        {
            return;
        }
        int st = StringUtil.getIntValue( startSec, -1 );

        int et = StringUtil.getIntValue( endSec, -1 );
        if( st < 0 )
        {
            return;
        }
        if( et <= st )
        {
            et = 0;
        }
        else
        {
            et -= st;
        }
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( res.getSiteId() );

        String sitePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String day = DateAndTimeUtil.getCunrrentDayAndTime( "yyyy-MM-dd" );

        String newFileName = DateAndTimeUtil.clusterTimeMillis() + StringUtil.getUUIDString() + "."
            + res.getFileType();

        String oldPath = sitePath + site.getSiteRoot() + File.separator + site.getMediaRoot()
            + File.separator
            + StringUtil.replaceString( res.getResSource(), "/", File.separator, false, false );

        String endPath = sitePath + site.getSiteRoot() + File.separator + site.getMediaRoot()
            + File.separator + day + File.separator + newFileName;

        new File( endPath ).getParentFile().mkdirs();

        boolean succ = MediaUtil.divideUpVideoMedia( oldPath, endPath, st + "", et + "", eventKey,
            statusMap );
        if( succ )
        {
            File newFile = new File( endPath );
            if( newFile.length() < 0L )
            {
                return;
            }
            SiteResource resInfo = new SiteResource();

            VideoMetadataBean vmBean = MediaUtil.getMediaMetadata( newFile.getPath() );

            Integer videoSec = Integer.valueOf( 0 );
            if( vmBean != null )
            {
                int allSec = vmBean.getDurationHour().intValue() * 3600
                    + vmBean.getDurationMinute().intValue() * 60
                    + vmBean.getDurationSec().intValue();
                videoSec = Integer.valueOf( allSec );
            }
            resInfo.setDuration( videoSec );
            resInfo.setResolution( vmBean.getResolution() );

            resInfo.setResType( Constant.RESOURCE.VIDEO_RES_TYPE );

            resInfo.setClassId( res.getClassId() );

            resInfo.setSiteId( res.getSiteId() );

            resInfo.setResName( res.getResName() + "(C:" + st + "~" + ( et == 0 ? "end" : endSec )
                + ")" );

            resInfo.setFileType( res.getFileType() );

            resInfo.setModifyTime( new Timestamp( newFile.lastModified() ) );
            resInfo.setResSize( Long.valueOf( newFile.length() ) );

            resInfo.setResSource( day + "/" + newFile.getName() );

            List resList = new ArrayList();

            resList.add( resInfo );

            UpdateState dbState = addSiteResourceAndUploadTrace( ( SiteResource ) resList.get( 0 ) );
            if( ( dbState != null ) && ( dbState.getKey() > 0L ) )
            {
                siteService.transferUpdateDataToServer( resList );

                SiteResource mediaRes = ( SiteResource ) resList.get( 0 );

                addMediaResCovInfo( mediaRes.getSiteId(), mediaRes.getClassId(), Long
                    .valueOf( dbState.getKey() ) );

                updateSiteResourceTraceUseStatus( Long.valueOf( dbState.getKey() ),
                    Constant.COMMON.ON );
            }
        }
    }

    public void changeMediaResName( Long resId, String newName )
    {
        this.resourcesDao.updateResName( resId, newName );
    }

    public void setMediaResClass( List idList, Long classId )
    {
        if( idList == null )
        {
            return;
        }
        try
        {
            this.mysqlEngine.beginTransaction();

            Long resId = null;
            for ( int i = 0; i < idList.size(); i++ )
            {
                resId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1L ) );

                this.resourcesDao.updateMediaCovClass( resId, classId );

                this.resourcesDao.updateResClass( resId, classId );
            }
            this.mysqlEngine.commit();
        }
        finally
        {
            this.mysqlEngine.endTransaction();
        }
    }

    public void snapMediaResourceCover( Long resId, String sec )
    {
        SiteResourceBean res = this.resourcesDao.querySingleResourceBeanByResId( resId );
        if( res == null )
        {
            return;
        }
        int st = StringUtil.getIntValue( sec, -1 );
        if( st < 0 )
        {
            return;
        }
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( res.getSiteId() );

        String sitePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String day = DateAndTimeUtil.getCunrrentDayAndTime( "yyyy-MM-dd" );

        String newFileName = DateAndTimeUtil.clusterTimeMillis() + StringUtil.getUUIDString()
            + ".jpg";

        String oldPath = sitePath + site.getSiteRoot() + File.separator + site.getMediaRoot()
            + File.separator
            + StringUtil.replaceString( res.getResSource(), "/", File.separator, false, false );

        String endPath = sitePath + site.getSiteRoot() + File.separator + site.getImageRoot()
            + File.separator + day + File.separator + newFileName;

        new File( endPath ).getParentFile().mkdirs();

        String[] resolutionInfo = StringUtil.split( res.getResolution(), "x" );

        int width = StringUtil.getIntValue( resolutionInfo[0], 800 );

        int height = StringUtil.getIntValue( resolutionInfo[1], 600 );

        boolean succ = MediaUtil.processVideoImg( oldPath, endPath, Double.valueOf(
            MathUtil.round( Double.parseDouble( sec ), 0 ) ).intValue(), width + SPLIT_CHAR
            + height );
        if( succ )
        {
            File newFile = new File( endPath );
            if( newFile.length() < 0L )
            {
                return;
            }
            updateSingleVideoResourceCoverByResId( day + "/" + newFileName, res.getResId() );

            SiteResource imgRes = new SiteResource();

            imgRes.setClassId( res.getClassId() );
            imgRes.setSiteId( res.getSiteId() );
            imgRes.setFileType( "jpg" );
            imgRes.setResType( Constant.RESOURCE.IMAGE_RES_TYPE );
            imgRes.setWidth( Integer.valueOf( width ) );
            imgRes.setHeight( Integer.valueOf( height ) );
            imgRes.setModifyTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );
            imgRes.setResName( res.getResName() + "_" + sec + "s" );
            imgRes.setResSource( day + "/" + newFileName );

            UpdateState us = addSiteResourceAndUploadTrace( imgRes );

            Long newCoverResId = Long.valueOf( us.getKey() );

            updateSiteResourceTraceUseStatus( newCoverResId, Constant.COMMON.ON );

            String resize = sitePath + site.getSiteRoot() + File.separator + site.getImageRoot()
                + File.separator + day + File.separator + "imgResize" + newFileName;
            if( width >= height )
            {
                ImageUtil.resizeImage( 140, -1, endPath, resize, 90 );
            }
            else
            {
                ImageUtil.resizeImage( -1, 140, endPath, resize, 90 );
            }
            List resList = new ArrayList( 2 );

            resList.add( imgRes );

            siteService.transferUpdateDataToServer( resList );
        }
    }

    public void deleteCovertMediaResource( List resIdList )
    {
        if( resIdList == null )
        {
            return;
        }
        try
        {
            this.mysqlEngine.beginTransaction();

            Long resId = null;
            for ( int i = 0; i < resIdList.size(); i++ )
            {
                resId = Long
                    .valueOf( StringUtil.getLongValue( ( String ) resIdList.get( i ), -1L ) );

                this.resourcesDao.deleteMediaResCovById( resId );

                updateSiteResourceTraceUseStatus( resId, Constant.COMMON.OFF );
            }
            this.mysqlEngine.commit();
        }
        finally
        {
            this.mysqlEngine.endTransaction();
        }
    }

    public Object getMediaResCovForTag( String covId, String classId, String pn, String size )
    {
        if( StringUtil.isStringNotNull( covId ) )
        {
            return this.resourcesDao.querySingleMediaCovById( Long.valueOf( StringUtil
                .getLongValue( covId, -1L ) ) );
        }
        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 10 );

        List result = null;

        Page pageInfo = null;

        Long cId = Long.valueOf( StringUtil.getLongValue( classId, -1L ) );
        if( cId.longValue() != -1L )
        {
            Long count = this.resourcesDao.queryMediaCovCount( cId );

            pageInfo = new Page( pageSize, Integer.valueOf( count.intValue() ).intValue(), pageNum );

            result = this.resourcesDao.queryMediaCov( cId,
                Long.valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
        }
        else
        {
            Long count = this.resourcesDao.queryMediaCovCountBySiteId( siteBean.getSiteId() );

            pageInfo = new Page( pageSize, Integer.valueOf( count.intValue() ).intValue(), pageNum );

            result = this.resourcesDao.queryMediaCovBySiteId( siteBean.getSiteId(), Long
                .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
        }
        return new Object[] { result, pageInfo };
    }

    public Object getMediaResCovForTag( String classId, String pn, String size )
    {
        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 10 );

        List result = null;

        Page pageInfo = null;

        Long cId = Long.valueOf( StringUtil.getLongValue( classId, -1L ) );
        if( cId.longValue() != -1L )
        {
            Long count = this.resourcesDao.queryMediaCovCountBySiteAndClassId(
                siteBean.getSiteId(), cId );

            pageInfo = new Page( pageSize, Integer.valueOf( count.intValue() ).intValue(), pageNum );

            result = this.resourcesDao.queryMediaCovBySiteAndClassId( siteBean.getSiteId(), cId,
                Long.valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
        }
        return new Object[] { result, pageInfo };
    }

    public Object getIconResForTag( String iconName, String pn, String size )
    {
        if( StringUtil.isStringNotNull( iconName ) )
        {
            return this.resourcesDao.queryIconResByName( iconName );
        }
        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        pageInfo = new Page( pageSize, Integer.valueOf( 3000 ).intValue(), pageNum );

        List result = this.resourcesDao.queryIconRes( Long.valueOf( pageInfo.getFirstResult() ),
            Integer.valueOf( pageSize ) );

        return new Object[] { result, pageInfo };
    }

    public void zipAllSiteResource( boolean isFlowAct )
    {
        Map rtCfg = this.csDao.queryCmsSystemRuntimeConfig();

        Integer bd = ( Integer ) rtCfg.get( "backupDay" );

        Integer bhe = ( Integer ) rtCfg.get( "backupHourExe" );

        Calendar currentTime = Calendar.getInstance();

        Integer hour = Integer.valueOf( currentTime.get( 11 ) );
        if( !isFlowAct )
        {
            if( hour != bhe )
            {
                return;
            }
        }
        List siteBeanList = InitSiteGroupInfoBehavior.siteGroupListCache;

        SiteGroupBean site = null;

        String base = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String day = DateAndTimeUtil.getCunrrentDayAndTime( "yyyy-MM-dd_[HH_mm]" );

        String testBakRoot = base + "__sys_bak__" + File.separator + day;

        File test = new File( testBakRoot );
        if( !test.exists() )
        {
            test.mkdirs();
        }
        for ( int i = 0; i < siteBeanList.size(); i++ )
        {
            site = ( SiteGroupBean ) siteBeanList.get( i );

            FileUtil.copyFolder( base + site.getSiteRoot(), testBakRoot + File.separator
                + site.getSiteRoot() );
        }
        String classesPath = null;
        try
        {
            classesPath = InitSiteGroupInfoBehavior.class.getClassLoader().getResource( "/" )
                .toURI().getPath();
        }
        catch ( URISyntaxException e1 )
        {
            e1.printStackTrace();
        }
        String osName = System.getProperty( "os.name" ).toLowerCase();
        if( osName.indexOf( "win" ) != -1 )
        {
            if( classesPath.startsWith( "/" ) )
            {
                classesPath = StringUtil.subString( classesPath, 1, classesPath.length() );
            }
        }
        classesPath = StringUtil.replaceString( classesPath, "/", File.separator, false, false );

        String root = ServletUtil.getClassPath( InitClassPathHelper.class );

        String rtFile = StringUtil.replaceString( root, "classes", CS_FILE_NAME, false, false );

        Properties mysql = new Properties();
        try
        {
            mysql.load( new FileInputStream( new File( rtFile ) ) );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        csService.mysqlBack( ( String ) mysql.get( "db_user" ), ( String ) mysql.get( "db_pw" ),
            ( String ) mysql.get( "db_name" ), testBakRoot + File.separator + "db_bak_" + day
                + ".sql" );

        FileUtil.zip( base + "__sys_bak__" + File.separator + "bak_" + day + ".zip", testBakRoot );

        FileUtil.delFolder( testBakRoot + File.separator );

        Long bftp = ( Long ) rtCfg.get( "backupFtpId" );

        transferBackupToServer( base + "__sys_bak__" + File.separator + "bak_" + day + ".zip", bftp );

        FileFilter filter = new FileFilter()
        {
            public boolean accept( File pathname )
            {
                if( ( pathname.getName().startsWith( "bak_" ) ) && ( pathname.isFile() ) )
                {
                    return true;
                }
                return false;
            }
        };
        File bf = new File( base + "__sys_bak__" );

        File[] fs = bf.listFiles( filter );

        File bak = null;

        String curr = DateAndTimeUtil.getFormatDate( DateAndTimeUtil.clusterTimeMillis(),
            "yyyy-MM-dd" );
        for ( int i = 0; i < fs.length; i++ )
        {
            bak = fs[i];

            String bDay = DateAndTimeUtil.getFormatDate( bak.lastModified(), "yyyy-MM-dd" );

            int dayCount = DateAndTimeUtil.getDayInterval( curr, bDay, "yyyy-MM-dd" );
            if( dayCount > bd.intValue() )
            {
                FileUtil.delFile( bak.getPath() );
            }
        }
    }

    public void transferBackupToServer( String bakFilePath, Long ftpServerId )
    {
        String context = JtRuntime.cmsServer.getContext();

        String newPath = null;

        String fullPath = bakFilePath;

        SiteDispenseServerBean server = this.siteGroupDao
            .querySingleSiteDispenseServerBeanByserverId( ftpServerId );
        if( server == null )
        {
            return;
        }
        FTPClient ftp = FtpUtil.getFtpConnection( server.getServerIP(), Integer.parseInt( server
            .getServerPort() ), server.getLoginName(), server.getLoginPassword(), 30000 );

        boolean connect = false;
        if( ftp != null )
        {
            connect = ftp.isConnected();
        }
        try
        {
            if( connect )
            {
                if( StringUtil.isStringNotNull( context ) )
                {
                    newPath = context + File.separator + "SYS_BACKUP";
                }
                else
                {
                    newPath = "SYS_BACKUP";
                }
                FtpUtil.uploadFileFTP( ftp, newPath, null, new File( fullPath ) );

                log.info( "[传输成功 ：]" + fullPath );
            }
        }
        finally
        {
            FtpUtil.closeFtpConnection( ftp );
        }
    }

    public String restoreSysBackup( String targetBak )
    {
        String base = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String testBakRoot = base + "__sys_bak__" + File.separator + targetBak;

        File bak = new File( testBakRoot );
        if( ( !bak.exists() ) || ( bak.isDirectory() ) )
        {
            return "-1";
        }
        FileUtil.unZipFile( testBakRoot, base + "__sys_bak__" + File.separator + "RESTORE_TEMP"
            + File.separator, false );

        String bakName = StringUtil.subString( targetBak, 0, targetBak.length() - 4 );

        String bakDayName = StringUtil.subString( targetBak, 4, targetBak.length() - 4 );

        String sqlFile = base + "__sys_bak__" + File.separator + "RESTORE_TEMP" + File.separator
            + bakDayName + File.separator + "db_" + bakName + ".sql";

        File sqlFs = new File( sqlFile );
        if( sqlFs.exists() )
        {
            String classesPath = null;
            try
            {
                classesPath = InitSiteGroupInfoBehavior.class.getClassLoader().getResource( "/" )
                    .toURI().getPath();
            }
            catch ( URISyntaxException e1 )
            {
                e1.printStackTrace();
            }
            String osName = System.getProperty( "os.name" ).toLowerCase();
            if( osName.indexOf( "win" ) != -1 )
            {
                if( classesPath.startsWith( "/" ) )
                {
                    classesPath = StringUtil.subString( classesPath, 1, classesPath.length() );
                }
            }
            classesPath = StringUtil.replaceString( classesPath, "/", File.separator, false, false );

            String root = ServletUtil.getClassPath( InitClassPathHelper.class );

            String rtFile = StringUtil.replaceString( root, "classes", CS_FILE_NAME, false, false );

            Properties mysql = new Properties();
            try
            {
                mysql.load( new FileInputStream( new File( rtFile ) ) );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
            boolean succ = csService.mysqlRecovery( ( String ) mysql.get( "db_user" ),
                ( String ) mysql.get( "db_pw" ), ( String ) mysql.get( "db_name" ), sqlFile );
            if( !succ )
            {
                return "-3";
            }
            FileUtil.delFile( sqlFile );
        }
        else
        {
            return "-2";
        }
        FileUtil.copyFolder( base + "__sys_bak__" + File.separator + "RESTORE_TEMP"
            + File.separator + bakDayName, base );

        FileUtil
            .delFolder( base + "__sys_bak__" + File.separator + "RESTORE_TEMP" + File.separator );

        return "success";
    }

    public List showBackupFileForTag()
    {
        String base = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String bakFullPath = base + "__sys_bak__";

        File bakDir = new File( bakFullPath );
        if( !bakDir.exists() )
        {
            bakDir.mkdirs();
        }
        FileFilter filter = new FileFilter()
        {
            public boolean accept( File pathname )
            {
                return !pathname.isDirectory();
            }
        };
        File[] fs = bakDir.listFiles( filter );
        Arrays.sort( fs, new Comparator()
        {
            public int compare( Object f1, Object f2 )
            {
                long diff = ( ( File ) f1 ).lastModified() - ( ( File ) f2 ).lastModified();
                if( diff > 0L )
                {
                    return -1;
                }
                if( diff == 0L )
                {
                    return 0;
                }
                return 1;
            }
        } );
        List fa = new ArrayList();
        for ( int i = 0; i < fs.length; i++ )
        {
            Map v = new HashMap();

            v.put( "name", fs[i].getName() );

            v.put( "size", convertFileSize( fs[i].length() ) );

            v.put( "time", DateAndTimeUtil.getFormatDate( fs[i].lastModified(),
                "yyyy-MM-dd HH:mm:ss" ) );

            fa.add( v );
        }
        return fa;
    }

    public String getCloudEndPoint( Long siteId, Integer resType )
    {

        SitePublishGatewayBean gwBean = null;

        if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( resType ) )
        {
            gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( siteId,
                Constant.SITE_CHANNEL.TRAN_TYPE_IMAGE );
        }
        else if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( resType )
            || Constant.RESOURCE.MUSIC_RES_TYPE.equals( resType ) )
        {
            gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( siteId,
                Constant.SITE_CHANNEL.TRAN_TYPE_MEDIA );
        }
        else if( Constant.RESOURCE.DOC_RES_TYPE.equals( resType )
            || Constant.RESOURCE.ANY_RES_TYPE.equals( resType ) )
        {
            gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( siteId,
                Constant.SITE_CHANNEL.TRAN_TYPE_FILE );
        }

        if( gwBean == null )
        {
            return "no_pub";
        }

        SiteCloudCfgBean ccBean = siteGroupDao.querysSingleCloudCfgBean( gwBean.getTargetCloudId() );

        if( ccBean == null )
        {
            return "no_cloud";
        }

        return ccBean.getAccessUrl();

    }

    public void uploadFileToCloud( SiteResourceBean res )
    {
        SiteGroupBean siteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( res.getSiteId() );

        SitePublishGatewayBean gwBean = null;

        String resRoot = null;

        if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( res.getResType() ) )
        {
            gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( res.getSiteId(),
                Constant.SITE_CHANNEL.TRAN_TYPE_IMAGE );

            resRoot = Constant.CONTENT.IMG_BASE;
        }
        else if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( res.getResType() )
            || Constant.RESOURCE.MUSIC_RES_TYPE.equals( res.getResType() ) )
        {
            gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( res.getSiteId(),
                Constant.SITE_CHANNEL.TRAN_TYPE_MEDIA );
            resRoot = Constant.CONTENT.MEDIA_BASE;
        }
        else if( Constant.RESOURCE.DOC_RES_TYPE.equals( res.getResType() )
            || Constant.RESOURCE.ANY_RES_TYPE.equals( res.getResType() ) )
        {
            gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( res.getSiteId(),
                Constant.SITE_CHANNEL.TRAN_TYPE_FILE );
            resRoot = Constant.CONTENT.FILE_BASE;
        }

        if( gwBean == null )
        {
            return;
        }

        SiteCloudCfgBean cfgBean = siteGroupDao
            .querysSingleCloudCfgBean( gwBean.getTargetCloudId() );

        if( cfgBean == null )
        {
            return;
        }

        if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( res.getResType() ) )
        {
            if( Constant.RESOURCE.CLOUD_COS.equals( cfgBean.getCloudType() ) )
            {
                COSConfig cfg = cfgBean.toCOSCfg();

                String[] rep = res.getFullResFilePath();

                COSUtil.simpleUploadFileFromLocal( cfg, rep[0], siteBean.getSiteRoot() + "/"
                    + resRoot + "/" + res.getResSource() );

                String resizefileResSource = StringUtil.replaceString( res.getResSource(), "/",
                    "/imgResize", false, false );

                COSUtil.simpleUploadFileFromLocal( cfg, rep[1], siteBean.getSiteRoot() + "/"
                    + resRoot + "/" + resizefileResSource );

            }
            else if( Constant.RESOURCE.CLOUD_OSS.equals( cfgBean.getCloudType() ) )
            {
                OSSConfig cfg = cfgBean.toOSSCfg();

                String[] rep = res.getFullResFilePath();

                OSSUtil.uploadFile( cfg, rep[0], siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getResSource() );

                String resizefileResSource = StringUtil.replaceString( res.getResSource(), "/",
                    "/imgResize", false, false );

                OSSUtil.uploadFile( cfg, rep[1], siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + resizefileResSource );
            }
            else if( Constant.RESOURCE.CLOUD_QN.equals( cfgBean.getCloudType() ) )
            {
                QNConfig cfg = cfgBean.toQNCfg();

                String[] rep = res.getFullResFilePath();

                QNUtil.uploadFile( cfg, rep[0], siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getResSource() );

                String resizefileResSource = StringUtil.replaceString( res.getResSource(), "/",
                    "/imgResize", false, false );

                QNUtil.uploadFile( cfg, rep[1], siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + resizefileResSource );
            }

        }
        else if( ( Constant.RESOURCE.VIDEO_RES_TYPE.equals( res.getResType() ) )
            || ( Constant.RESOURCE.MUSIC_RES_TYPE.equals( res.getResType() ) ) )
        {
            if( Constant.RESOURCE.CLOUD_COS.equals( cfgBean.getCloudType() ) )
            {
                COSConfig cfg = cfgBean.toCOSCfg();

                String[] rep = res.getFullResFilePath();

                COSUtil.simpleUploadFileFromLocal( cfg, rep[0], siteBean.getSiteRoot() + "/"
                    + resRoot + "/" + res.getResSource() );

            }
            else if( Constant.RESOURCE.CLOUD_OSS.equals( cfgBean.getCloudType() ) )
            {
                OSSConfig cfg = cfgBean.toOSSCfg();

                String[] rep = res.getFullResFilePath();

                OSSUtil.uploadFile( cfg, rep[0], siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getResSource() );
            }
            else if( Constant.RESOURCE.CLOUD_QN.equals( cfgBean.getCloudType() ) )
            {
                QNConfig cfg = cfgBean.toQNCfg();

                String[] rep = res.getFullResFilePath();

                QNUtil.uploadFile( cfg, rep[0], siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getResSource() );
            }
        }
        else
        {

            if( Constant.RESOURCE.CLOUD_COS.equals( cfgBean.getCloudType() ) )
            {
                COSConfig cfg = cfgBean.toCOSCfg();

                String[] rep = res.getFullResFilePath();

                COSUtil.simpleUploadFileFromLocal( cfg, rep[0], siteBean.getSiteRoot() + "/"
                    + resRoot + "/" + res.getResSource() );

                // if(
                // "*.doc;*.docx;*.pptx;*.ppt;*.xlsx;*.xls;*.vsd;*.pdf;*.txt".indexOf(
                // "."
                // + res.getFileType() ) != -1 )
                // {
                // COSUtil.simpleUploadFileFromLocal( cfg, rep[0]+".swf", "/" +
                // res.getResSource()+".swf" );
                // COSUtil.simpleUploadFileFromLocal( cfg, rep[0]+".pdf", "/" +
                // res.getResSource()+".pdf" );
                // }

            }
            else if( Constant.RESOURCE.CLOUD_OSS.equals( cfgBean.getCloudType() ) )
            {
                OSSConfig cfg = cfgBean.toOSSCfg();

                String[] rep = res.getFullResFilePath();

                OSSUtil.uploadFile( cfg, rep[0], siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getResSource() );
            }
            else if( Constant.RESOURCE.CLOUD_QN.equals( cfgBean.getCloudType() ) )
            {
                QNConfig cfg = cfgBean.toQNCfg();

                String[] rep = res.getFullResFilePath();

                QNUtil.uploadFile( cfg, rep[0], siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getResSource() );
            }

        }

    }

    public void deleteFileFromCloud( SiteResourceBean res )
    {
        SiteGroupBean siteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( res.getSiteId() );

        String resRoot = null;

        SitePublishGatewayBean gwBean = null;

        if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( res.getResType() ) )
        {
            gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( res.getSiteId(),
                Constant.SITE_CHANNEL.TRAN_TYPE_IMAGE );
            resRoot = Constant.CONTENT.IMG_BASE;
        }
        else if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( res.getResType() )
            || Constant.RESOURCE.MUSIC_RES_TYPE.equals( res.getResType() ) )
        {
            gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( res.getSiteId(),
                Constant.SITE_CHANNEL.TRAN_TYPE_MEDIA );
            resRoot = Constant.CONTENT.MEDIA_BASE;
        }
        else if( Constant.RESOURCE.DOC_RES_TYPE.equals( res.getResType() )
            || Constant.RESOURCE.ANY_RES_TYPE.equals( res.getResType() ) )
        {
            gwBean = siteGroupDao.querysSingleEffiPublishGatewayBeanBySite( res.getSiteId(),
                Constant.SITE_CHANNEL.TRAN_TYPE_FILE );
            resRoot = Constant.CONTENT.FILE_BASE;
        }

        if( gwBean == null )
        {
            return;
        }

        SiteCloudCfgBean ccBean = siteGroupDao.querysSingleCloudCfgBean( gwBean.getTargetCloudId() );

        if( ccBean == null )
        {
            return;
        }

        if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( res.getResType() ) )
        {
            if( Constant.RESOURCE.CLOUD_COS.equals( ccBean.getCloudType() ) )
            {
                COSConfig cfg = ccBean.toCOSCfg();

                COSUtil.delSingleFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getResSource() );

                String resizefileResSource = StringUtil.replaceString( res.getResSource(), "/",
                    "/imgResize", false, false );

                COSUtil.delSingleFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + resizefileResSource );

            }
            else if( Constant.RESOURCE.CLOUD_OSS.equals( ccBean.getCloudType() ) )
            {
                OSSConfig cfg = ccBean.toOSSCfg();

                OSSUtil.deleteFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getResSource() );

                String resizefileResSource = StringUtil.replaceString( res.getResSource(), "/",
                    "/imgResize", false, false );

                OSSUtil.deleteFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + resizefileResSource );
            }
            else if( Constant.RESOURCE.CLOUD_QN.equals( ccBean.getCloudType() ) )
            {
                QNConfig cfg = ccBean.toQNCfg();

                QNUtil.deleteFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getResSource() );

                String resizefileResSource = StringUtil.replaceString( res.getResSource(), "/",
                    "/imgResize", false, false );

                QNUtil.deleteFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + resizefileResSource );
            }

        }
        else if( ( Constant.RESOURCE.VIDEO_RES_TYPE.equals( res.getResType() ) )
            || ( Constant.RESOURCE.MUSIC_RES_TYPE.equals( res.getResType() ) ) )
        {
            if( Constant.RESOURCE.CLOUD_COS.equals( ccBean.getCloudType() ) )
            {
                COSConfig cfg = ccBean.toCOSCfg();

                COSUtil.delSingleFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getResSource() );

                COSUtil.delSingleFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getCover() );

            }
            else if( Constant.RESOURCE.CLOUD_OSS.equals( ccBean.getCloudType() ) )
            {
                OSSConfig cfg = ccBean.toOSSCfg();

                OSSUtil.deleteFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getResSource() );

                OSSUtil.deleteFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getCover() );
            }
            else if( Constant.RESOURCE.CLOUD_QN.equals( ccBean.getCloudType() ) )
            {
                QNConfig cfg = ccBean.toQNCfg();

                QNUtil.deleteFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getResSource() );

                QNUtil.deleteFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getCover() );
            }
        }
        else
        {

            if( Constant.RESOURCE.CLOUD_COS.equals( ccBean.getCloudType() ) )
            {
                COSConfig cfg = ccBean.toCOSCfg();

                COSUtil.delSingleFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getResSource() );

                if( "*.doc;*.docx;*.pptx;*.ppt;*.xlsx;*.xls;*.vsd;*.pdf;*.txt".indexOf( "."
                    + res.getFileType() ) != -1 )
                {
                    COSUtil.delSingleFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                        + res.getResSource() + ".swf" );
                    COSUtil.delSingleFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                        + res.getResSource() + ".pdf" );
                }

            }
            else if( Constant.RESOURCE.CLOUD_OSS.equals( ccBean.getCloudType() ) )
            {
                OSSConfig cfg = ccBean.toOSSCfg();

                OSSUtil.deleteFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getResSource() );

                if( "*.doc;*.docx;*.pptx;*.ppt;*.xlsx;*.xls;*.vsd;*.pdf;*.txt".indexOf( "."
                    + res.getFileType() ) != -1 )
                {
                    OSSUtil.deleteFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                        + res.getResSource() + ".swf" );
                    OSSUtil.deleteFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                        + res.getResSource() + ".pdf" );
                }
            }
            else if( Constant.RESOURCE.CLOUD_QN.equals( ccBean.getCloudType() ) )
            {
                QNConfig cfg = ccBean.toQNCfg();

                QNUtil.deleteFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                    + res.getResSource() );

                if( "*.doc;*.docx;*.pptx;*.ppt;*.xlsx;*.xls;*.vsd;*.pdf;*.txt".indexOf( "."
                    + res.getFileType() ) != -1 )
                {
                    QNUtil.deleteFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                        + siteBean.getSiteRoot() + "/" + resRoot + "/" + res.getResSource()
                        + ".swf" );
                    QNUtil.deleteFile( cfg, siteBean.getSiteRoot() + "/" + resRoot + "/"
                        + res.getResSource() + ".pdf" );
                }
            }

        }

    }

    public void uploadAllSiteFileToCloud()
    {
        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( siteBean == null )
        {
            return;
        }

        Long prevCid = Long.valueOf( Constant.CONTENT.MAX_ID_FLAG );

        Long count = resourcesDao.queryResourceBeanCountBySiteId( siteBean.getSiteId() );

        List<SiteResourceBean> restList = resourcesDao.queryResourceBeanBySiteId( siteBean
            .getSiteId(), prevCid, QUERY_COUNT );

        while ( !restList.isEmpty() )
        {
            prevCid = ( ( SiteResourceBean ) restList.get( restList.size() - 1 ) ).getResId();

            uploadFileToCloud( restList );

            restList = resourcesDao.queryResourceBeanBySiteId( siteBean.getSiteId(), prevCid,
                QUERY_COUNT );
        }

    }

    public void uploadFileToCloud( List<SiteResourceBean> resList )
    {
        for ( SiteResourceBean bean : resList )
        {
            uploadFileToCloud( bean );
        }
    }

    public static String convertFileSize( long size )
    {
        long kb = 1024L;
        long mb = kb * 1024L;
        long gb = mb * 1024L;
        if( size >= gb )
        {
            return String.format( "%.1f GB", new Object[] { Float.valueOf( ( float ) size
                / ( float ) gb ) } );
        }
        if( size >= mb )
        {
            float f = ( float ) size / ( float ) mb;
            return String.format( f > 100.0F ? "%.0f MB" : "%.1f MB", new Object[] { Float
                .valueOf( f ) } );
        }
        if( size >= kb )
        {
            float f = ( float ) size / ( float ) kb;
            return String.format( f > 100.0F ? "%.0f KB" : "%.1f KB", new Object[] { Float
                .valueOf( f ) } );
        }
        return String.format( "%d B", new Object[] { Long.valueOf( size ) } );
    }

    public String getFullFilePathByManager( String entry )
    {
        String fullPath = "";

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();
        if( siteBean == null )
        {
            return null;
        }
        String baseRealPath = SystemConfiguration.getInstance().getSystemConfig()
            .getSystemRealPath();

        String rootPath = baseRealPath + siteBean.getSiteRoot();
        if( StringUtil.isStringNull( entry ) )
        {
            fullPath = rootPath;
        }
        else
        {
            entry = SystemSafeCharUtil.decodeFromWeb( entry );

            String endEntry = StringUtil.replaceString( entry, SPLIT_CHAR, File.separator, false,
                false );
            if( ( endEntry.indexOf( "../" ) != -1 ) || ( endEntry.indexOf( "..%2F" ) != -1 )
                || ( endEntry.indexOf( "..%2f" ) != -1 ) || ( endEntry.indexOf( "WEB-INF" ) != -1 ) )
            {
                return "";
            }
            log.info( "[getFullFilePathByManager] 入口:" + entry );

            fullPath = rootPath + endEntry;
        }
        if( ( fullPath.indexOf( "../" ) != -1 ) || ( fullPath.indexOf( "..%2F" ) != -1 )
            || ( fullPath.indexOf( "..%2f" ) != -1 ) || ( fullPath.indexOf( "WEB-INF" ) != -1 ) )
        {
            return "";
        }
        return fullPath;
    }

    public static Integer chooseResType( String fileType )
    {
        if( "*.png;*.jpeg;*.gif;*.bmp;*.jpg".indexOf( fileType ) != -1 )
        {
            return Constant.RESOURCE.IMAGE_RES_TYPE;
        }
        if( "*.swf;*.flv;*.f4v;*.avi;*.mpg;*.mpeg;*.mp4;*.rm;*.rmvb;*.wmv;*.wma".indexOf( fileType ) != -1 )
        {
            return Constant.RESOURCE.VIDEO_RES_TYPE;
        }
        if( "*.mp3;*.wav".indexOf( fileType ) != -1 )
        {
            return Constant.RESOURCE.MUSIC_RES_TYPE;
        }
        if( "*.doc;*.docx;*.pptx;*.ppt;*.xlsx;*.xls;*.vsd;*.pdf;*.txt".indexOf( fileType ) != -1 )
        {
            return Constant.RESOURCE.DOC_RES_TYPE;
        }
        return Constant.RESOURCE.ANY_RES_TYPE;
    }

    public static boolean checkFile( String content )
    {
        if( StringUtil.isStringNull( content ) )
        {
            return false;
        }
        if( content.indexOf( "<%!" ) != -1 )
        {
            return false;
        }
        if( RegexUtil.match( content, "<%@.+import.+%>" ) )
        {
            return false;
        }
        int start = content.indexOf( "<%" );
        int end = content.indexOf( "%>", start );
        while ( ( start != -1 ) && ( end != -1 ) )
        {
            String testStr = StringUtil.subString( content, start + 2, end );
            if( testStr.length() > 65 )
            {
                if( ( !testStr.startsWith( "@" ) ) && ( !testStr.startsWith( "--" ) ) )
                {
                    return false;
                }
            }
            start = content.indexOf( "<%", end );
            end = content.indexOf( "%>", start );
        }
        return true;
    }

    

}
