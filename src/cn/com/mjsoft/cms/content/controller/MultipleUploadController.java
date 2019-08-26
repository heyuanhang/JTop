package cn.com.mjsoft.cms.content.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.cluster.adapter.ClusterMapAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.bean.UploadImageJsonBean;
import cn.com.mjsoft.cms.content.bean.VideoMetadataBean;
import cn.com.mjsoft.cms.member.bean.MemberBean;
import cn.com.mjsoft.cms.resources.dao.vo.SiteResource;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.ImageUtil;
import cn.com.mjsoft.framework.util.MediaUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.FlowConstants;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

@SuppressWarnings( { "unchecked" } )
@Controller
@RequestMapping( "/content" )
public class MultipleUploadController
{
    private static Logger log = Logger.getLogger( MultipleUploadController.class );

    private static final String UPLOAD_ENCODEING = "UTF-8";// swfUpload默认是UTF-8传输

    private static final ClusterMapAdapter IP_UPLOAD_FILE_LENGTH = new ClusterMapAdapter(
        "IP_UPLOAD_FILE_LENGTH", Long.class, Integer.class );

    public static final int MAX_UPLOAD_LENGTH = 50 * 1024;// 指定时间内会员上传不能过50

    public static final int BLACK_MEMBER_TIME = 30;// 禁止上传过期分钟

    private static final ClusterMapAdapter BLACK_IP_UPLOAD = new ClusterMapAdapter(
        "BLACK_IP_UPLOAD", Long.class, Date.class );

    private static ResourcesService resService = ResourcesService.getInstance();

    private static SiteGroupService siteService = SiteGroupService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/multiUpload.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public Object multiUpload( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        Map paramPassMap = new HashMap();

        Map params = ServletUtil.getRequestInfo( request );

        /**
         * 上传的类型,注意:无论任何文件类型,都需要根据后缀检查文件合法性!!!!
         */
        Integer resType = Integer.valueOf( StringUtil.getIntValue( ( String ) params.get( "type" ),
            5 ) );

        Integer nm = Integer.valueOf( StringUtil.getIntValue( ( String ) params.get( "needMark" ),
            1 ) );

        SecuritySession sec = SecuritySessionKeeper.getSecuritySession();

        SiteGroupBean siteBean = null;

        if( sec != null && sec.isManager() )
        {
            siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();
        }
        else if( sec != null && sec.getMember() != null )
        {
            MemberBean member = ( MemberBean ) sec.getMember();

            if( BLACK_IP_UPLOAD.containsKey( member.getMemberId() ) )
            {
                return "fail:uploadOverflow";
            }

            siteBean = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );
        }
        else
        {
            siteBean = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );
        }

        if( siteBean == null || siteBean.getSiteId().longValue() < 0 )
        {
            return "fail:site_null";
        }

        // 从配置读取限制
        int fileSize = 0;
        // 
        if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( resType ) )
        {
            fileSize = siteBean.getImageMaxC().intValue();
        }
        else if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( resType )
            || Constant.RESOURCE.MUSIC_RES_TYPE.equals( resType ) )
        {
            fileSize = siteBean.getMediaMaxC().intValue();
        }
        else if( Constant.RESOURCE.DOC_RES_TYPE.equals( resType )
            || Constant.RESOURCE.ANY_RES_TYPE.equals( resType ) )
        {
            fileSize = siteBean.getFileMaxC().intValue();
        }
        else
        {
            fileSize = 5000;// 最大配置
        }

        if( fileSize == 0 )
        {
            fileSize = 5000;// 最大配置
        }

        List resList = uploadFileToSite( request, fileSize * 1024 * 1024, UPLOAD_ENCODEING,
            new RandomFileRenamePolicy(), paramPassMap, resType, params, siteBean );

        if( resList == null || resList.isEmpty() )
        {
            return "{null}";
        }

        paramPassMap.put( "fromFlow", "true" );
        paramPassMap.put( "successMsg", "OK" );
        log.info( FlowConstants.REDIRECT_PARAMS + "::" + paramPassMap );

        if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( resType ) )
        {
            resService.addNewResourceList( resList );

            // 进行数据分发
            siteService.transferUpdateDataToServer( resList );

            UploadImageJsonBean jsonBean = new UploadImageJsonBean();

            jsonBean.setResId( ( ( SiteResource ) resList.get( 0 ) ).getResId() );
            jsonBean.setRelatePath( ( String ) paramPassMap.get( "relatePath" ) );
            jsonBean.setImageUrl( ( String ) paramPassMap.get( "imageUrl" ) );
            jsonBean.setResizeImageUrl( ( String ) paramPassMap.get( "resize" ) );
            jsonBean.setImageName( ( String ) paramPassMap.get( "Filename" ) );
            jsonBean.setGenName( ( String ) paramPassMap.get( "genName" ) );
            jsonBean.setPefixDir( ( String ) paramPassMap.get( "pefixDir" ) );
            jsonBean.setWidth( ( Integer ) paramPassMap.get( "width" ) );
            jsonBean.setHeight( ( Integer ) paramPassMap.get( "height" ) );

            List beanList = new ArrayList();
            beanList.add( jsonBean );

            return StringUtil.changeJSON( beanList, "obj_" );
        }

        if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( resType )
            || Constant.RESOURCE.ANY_RES_TYPE.equals( resType ) )
        {
            UpdateState dbState = resService
                .addSiteResourceAndUploadTrace( ( SiteResource ) resList.get( 0 ) );

            if( dbState != null && dbState.getKey() > 0 )
            {
                paramPassMap.put( "resId", Long.valueOf( dbState.getKey() ) );

                // 进行数据分发
                siteService.transferUpdateDataToServer( resList );

                if( "true".equals( params.get( "mediaResCov" ) ) )
                {
                    SiteResource mediaRes = ( SiteResource ) resList.get( 0 );

                    resService.addMediaResCovInfo( mediaRes.getSiteId(), mediaRes.getClassId(),
                        Long.valueOf( dbState.getKey() ) );

                    resService.updateSiteResourceTraceUseStatus( Long.valueOf( dbState.getKey() ),
                        Constant.COMMON.ON );
                }
            }

            List jsonList = new ArrayList( 1 );
            jsonList.add( paramPassMap );

            // 2016-7-19: 文库支持数据类型执行转换
            SiteResource sr = ( SiteResource ) resList.get( 0 );

            changeDocToWenku( sr, siteBean, ServletUtil.getSiteFilePath( request
                .getServletContext() ) );

            // 如果是Office导入模式,将跳转Office处理Flow
            if( "true".equals( ( String ) params.get( "officeMode" ) ) )
            {
                request.setAttribute( "resId", paramPassMap.get( "resId" ) );
                return ServletUtil.forward( "/content/convertOffice.do" );
            }

            return StringUtil.changeJSON( jsonList, "obj_" );
        }

        return ServletUtil.redirect( "/core/content/EditorUploadImage.jsp", paramPassMap );

    }

    @SuppressWarnings( "unchecked" )
    public static void checkIpUpload()
    {
        ClusterMapAdapter ipc = IP_UPLOAD_FILE_LENGTH;

        Iterator iter = ipc.getMap().entrySet().iterator();

        Entry entry = null;

        Integer length = null;

        List removeIp = new ArrayList();

        while ( iter.hasNext() )
        {
            entry = ( Entry ) iter.next();

            length = ( Integer ) entry.getValue();

            if( length >= MAX_UPLOAD_LENGTH )
            {
                BLACK_IP_UPLOAD
                    .put( entry.getKey(), new Date( DateAndTimeUtil.clusterTimeMillis() ) );

                removeIp.add( entry.getKey() );
            }
        }

        for ( int i = 0; i < removeIp.size(); i++ )
        {
            ipc.remove( removeIp.get( i ) );
        }

    }

    public static void clearBlackUploadIP()
    {
        ClusterMapAdapter bip = BLACK_IP_UPLOAD;

        Iterator iter = bip.getMap().entrySet().iterator();

        Entry entry = null;

        Date eventDate = null;

        Date currentDate = new Date( DateAndTimeUtil.clusterTimeMillis() );

        List removeIp = new ArrayList();

        while ( iter.hasNext() )
        {
            entry = ( Entry ) iter.next();

            eventDate = ( Date ) entry.getValue();

            int d = DateAndTimeUtil.getSecInterval( currentDate, eventDate );

            // 强制指定分钟内不可上传, 30分钟内的无效资源被清除
            if( DateAndTimeUtil.getSecInterval( currentDate, eventDate ) > BLACK_MEMBER_TIME )
            {
                removeIp.add( entry.getKey() );
            }
        }

        for ( int i = 0; i < removeIp.size(); i++ )
        {
            bip.remove( removeIp.get( i ) );
        }
    }

    private List uploadFileToSite( HttpServletRequest request, int maxSize, String encodeing,
        FileRenamePolicy policy, Map paramMap, Integer resType, Map requestParam,
        SiteGroupBean siteBean ) throws Exception
    {

        // URL主路径
        // String basePath = InitSiteGroupInfoBehavior.currentCmsServerInfoBean
        // .getDomainFullPath()
        // + Constant.CONTENT.URL_SEP;

        // 物理主路径
        String sitePath = ServletUtil.getSiteFilePath( request.getServletContext() );

        // 当前时间日期
        String day = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        String uploadRoot = null;

        String siteFileURL = null;

        if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( resType ) )
        {
            uploadRoot = siteBean.getImageRoot();
            siteFileURL = siteBean.getSiteImagePrefixUrl() + day + Constant.CONTENT.URL_SEP;
        }
        else if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( resType )
            || Constant.RESOURCE.MUSIC_RES_TYPE.equals( resType ) )
        {
            uploadRoot = siteBean.getMediaRoot();
            siteFileURL = siteBean.getSiteMediaPrefixUrl() + day + Constant.CONTENT.URL_SEP;
        }
        else if( Constant.RESOURCE.DOC_RES_TYPE.equals( resType )
            || Constant.RESOURCE.ANY_RES_TYPE.equals( resType ) )
        {
            uploadRoot = siteBean.getFileRoot();
            siteFileURL = siteBean.getSiteFilePrefixUrl() + day + Constant.CONTENT.URL_SEP;
        }

        // 物理和网络路径
        String uploadFilePath = sitePath + siteBean.getSiteRoot() + File.separator + uploadRoot
            + File.separator + day;

        log.info( ">>>>>>>>>>>>uploadFilePath>>>>>>>>>>:" + uploadFilePath );

        File tf = new File( uploadFilePath );
        if( !tf.exists() )
        {
            tf.mkdirs();
        }

        MultipartRequest multi = new MultipartRequest( request, uploadFilePath, maxSize, encodeing,
            policy );

        // 获取form的其他参数
        System.out.println( "PARAMS: 组装开始" );
        Enumeration params = multi.getParameterNames();
        while ( params.hasMoreElements() )
        {
            String name = ( String ) params.nextElement();
            String value = multi.getParameter( name );

            paramMap.put( name, value );

            System.out.println( name + "=" + value );
        }

        System.out.println( "PARAMS: 组装结束" );

        if( "w".equals( ( String ) paramMap.get( "imageSourceType" ) ) )
        {
            SiteResource imgInfo = new SiteResource();
            // 
            // imgInfo.setImgClassId( Long.valueOf( -1 ) );
            // imgInfo.setModifyTime( new Timestamp( System.currentTimeMillis()
            // ) );
            // imgInfo.setSource( ( String ) paramMap.get( "imgWebUrl" ) );
            List imgList = new ArrayList();
            imgList.add( imgInfo );
            return imgList;
        }

        System.out.println( "FILES:" );
        Enumeration files = multi.getFileNames();

        while ( files.hasMoreElements() )
        {
            String name = ( String ) files.nextElement();
            String filename = multi.getFilesystemName( name );
            String originalFilename = multi.getOriginalFileName( name );
            String type = multi.getContentType( name );
            File targetFile = multi.getFile( name );
            System.out.println( "上传标签名称: " + name );
            System.out.println( "文件新名称: " + filename );
            System.out.println( "原始名称: " + originalFilename );
            System.out.println( "文件type: " + type );
            if( targetFile != null )
            {
                log.info( "文件.toString(): " + targetFile.toString() );
                log.info( "文件.getName(): " + targetFile.getName() );
                log.info( "文件.exists(): " + targetFile.exists() );
                log.info( "文件.length(): " + targetFile.length() );

                SecuritySession sec = SecuritySessionKeeper.getSecuritySession();

                if( sec != null && sec.getMember() != null )
                {
                    MemberBean member = ( MemberBean ) sec.getMember();

                    Integer uploadCount = ( Integer ) IP_UPLOAD_FILE_LENGTH.get( member
                        .getMemberId() );

                    if( uploadCount == null )
                    {
                        uploadCount = Integer.valueOf( 0 );
                    }

                    IP_UPLOAD_FILE_LENGTH.put( member.getMemberId(), Integer.valueOf( uploadCount
                        .intValue()
                        + ( ( int ) ( targetFile.length() / 1024 ) + 1 ) ) );

                }

                if( targetFile.exists() )
                {
                    String rootPath = ServletUtil.getSiteFilePath( request.getServletContext() );

                    if( "application/x-zip-compressed".equals( type ) )
                    {
                        String imageResPath = rootPath + "UPLOAD" + File.separator;
                        checkFile( siteBean, type, targetFile, resType, true );
                        List imgList = resService.disposeZIPImageFile( targetFile.getPath(),
                            imageResPath, sitePath, rootPath, Long.valueOf( ( String ) paramMap
                                .get( "classId" ) ) );

                        targetFile.delete();
                        if( targetFile.exists() )
                        {
                            targetFile.deleteOnExit();
                        }

                        return imgList;

                    }
                    else
                    {
                        checkFile( siteBean, type, targetFile, resType, false );

                        SiteResource resInfo = new SiteResource();

                        if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( resType ) )
                        {
                            // 组装图片信息bean

                            // 获取图片原始的宽高
                            Object[] imgOffset = ImageUtil.getImageHeightAndWidth( uploadFilePath
                                + File.separator + targetFile.getName() );

                            Integer width = ( ( Integer ) imgOffset[0] );
                            Integer height = ( ( Integer ) imgOffset[1] );

                            log.info( ">>>>>>>>>width + height>>>>>>>>>:" + width + " : " + height );

                            // 缩略图
                            String resize = uploadFilePath + File.separator + "imgResize"
                                + targetFile.getName();

                            int maxResize = StringUtil.getIntValue( ( String ) requestParam
                                .get( "maxResize" ), -1 );

                            if( maxResize < 0 )
                            {
                                maxResize = 140;
                            }

                            if( width.intValue() >= height.intValue() )
                            {
                                ImageUtil.resizeImage( maxResize, -1, targetFile.getPath(), resize,
                                    Constant.RESOURCE.IMAGE_RESIZE_Q_MID );
                            }
                            else
                            {
                                ImageUtil.resizeImage( -1, maxResize, targetFile.getPath(), resize,
                                    Constant.RESOURCE.IMAGE_RESIZE_Q_MID );
                            }

                            if( width.intValue() == 0 || height.intValue() == 0 )
                            {
                                deleteErrorFileAndResponse( targetFile );
                            }

                            // 根据配置重新处理图片
                            Integer disposeMode = Integer.valueOf( StringUtil.getIntValue(
                                ( String ) requestParam.get( "disposeMode" ), -1 ) );

                            if( Constant.RESOURCE.IMAGE_DISPOSE_MODE_W.equals( disposeMode ) )
                            {
                                int maxWidth = StringUtil.getIntValue( ( String ) requestParam
                                    .get( "maxWidth" ), 0 );

                                if( maxWidth > 0 )
                                {
                                    ImageUtil.resizeImage( maxWidth, -1, targetFile.getPath(),
                                        uploadFilePath + File.separator + targetFile.getName(),
                                        Constant.RESOURCE.IMAGE_RESIZE_Q_MID );
                                }
                            }
                            else if( Constant.RESOURCE.IMAGE_DISPOSE_MODE_H.equals( disposeMode ) )
                            {
                                int maxHeight = StringUtil.getIntValue( ( String ) requestParam
                                    .get( "maxHeight" ), 0 );

                                if( maxHeight > 0 )
                                {
                                    ImageUtil.resizeImage( -1, maxHeight, targetFile.getPath(),
                                        uploadFilePath + File.separator + targetFile.getName(),
                                        Constant.RESOURCE.IMAGE_RESIZE_Q_MID );
                                }
                            }
                            else if( Constant.RESOURCE.IMAGE_DISPOSE_MODE_W_AND_H
                                .equals( disposeMode ) )
                            {
                                int maxWidth = StringUtil.getIntValue( ( String ) requestParam
                                    .get( "maxWidth" ), 0 );

                                int maxHeight = StringUtil.getIntValue( ( String ) requestParam
                                    .get( "maxHeight" ), 0 );
                                // 注意 ,不能强制缩放
                                if( maxHeight > 0 && maxHeight > 0 )
                                {
                                    ImageUtil.resizeImage( maxWidth, maxHeight, targetFile
                                        .getPath(), uploadFilePath + File.separator
                                        + targetFile.getName(),
                                        Constant.RESOURCE.IMAGE_RESIZE_Q_MID );
                                }
                            }

                            // 文件显示标题
                            // String titleName = URLDecoder.decode(
                            // ( String ) requestParam.get( "titleName" ),
                            // "utf-8" );

                            // 获取图片处理后的宽高
                            imgOffset = ImageUtil.getImageHeightAndWidth( uploadFilePath
                                + File.separator + targetFile.getName() );

                            width = ( ( Integer ) imgOffset[0] );
                            height = ( ( Integer ) imgOffset[1] );

                            paramMap.put( "height", height );

                            paramMap.put( "width", width );

                            paramMap.put( "showName", requestParam.get( "titleName" ) );

                            // 文件信息

                            resInfo.setHeight( height );
                            resInfo.setWidth( width );
                            // 默认为顶级分类下
                            resInfo.setClassId( Long.valueOf( StringUtil.getLongValue(
                                ( String ) paramMap.get( "classId" ), -9999 ) ) );
                            resInfo.setSiteId( siteBean.getSiteId() );

                            resInfo.setResName( StringUtil.subString( originalFilename, 0,
                                originalFilename.lastIndexOf( "." ) ) );

                            String fileType = StringUtil.subString( originalFilename,
                                originalFilename.lastIndexOf( "." ) + 1, originalFilename.length() )
                                .toLowerCase();
                            resInfo.setFileType( fileType );

                            resInfo.setResType( Constant.RESOURCE.IMAGE_RES_TYPE );

                            Timestamp modifyTime;

                            if( ( ( Long ) imgOffset[2] ).longValue() != 0 )
                            {
                                modifyTime = new Timestamp( ( ( Long ) imgOffset[2] ).longValue() );
                            }
                            else
                            {
                                modifyTime = new Timestamp( DateAndTimeUtil.clusterTimeMillis() );
                            }

                            resInfo.setModifyTime( modifyTime );
                            resInfo.setResSize( Long.valueOf( targetFile.length() ) );
                            resInfo.setResSource( day + Constant.CONTENT.URL_SEP
                                + targetFile.getName() );

                            // 回传文件信息
                            paramMap.put( "height", ( Integer ) imgOffset[1] );

                            paramMap.put( "width", ( Integer ) imgOffset[0] );

                            paramMap.put( "pefixDir", day );
                            paramMap.put( "relatePath", day + Constant.CONTENT.URL_SEP
                                + targetFile.getName() );
                            paramMap.put( "imageUrl", siteFileURL + targetFile.getName() );

                            // test simple 模式，现注释imgResize
                            FileUtil.copyFile( targetFile.getPath(), resize );

                            paramMap.put( "resize", siteFileURL + "imgResize"
                                + targetFile.getName() );

                            paramMap.put( "genName", filename );
                        }
                        else
                        {
                            String fileType = StringUtil.subString( originalFilename,
                                originalFilename.lastIndexOf( "." ) + 1, originalFilename.length() )
                                .toLowerCase();

                            if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( resType ) )
                            {
                                // 获取视频媒体信息
                                VideoMetadataBean vmBean = MediaUtil.getMediaMetadata( targetFile
                                    .getPath() );

                                Integer videoSec = Integer.valueOf( 0 );
                                if( vmBean != null )
                                {
                                    int allSec = vmBean.getDurationHour().intValue() * 3600
                                        + vmBean.getDurationMinute().intValue() * 60
                                        + vmBean.getDurationSec().intValue();
                                    videoSec = Integer.valueOf( allSec );
                                }

                                String[] resArray = StringUtil.split( vmBean.getResolution(), "x" );
                                paramMap.put( "videoT", videoSec );
                                if( resArray.length == 2 )
                                {
                                    paramMap.put( "videoW", resArray[0] );
                                    paramMap.put( "videoH", resArray[1] );
                                }

                                resInfo.setDuration( videoSec );
                                resInfo.setResolution( vmBean.getResolution() );

                                resInfo.setResType( Constant.RESOURCE.VIDEO_RES_TYPE );

                            }
                            else
                            {
                                resInfo.setResType( Constant.RESOURCE.ANY_RES_TYPE );
                            }

                            resInfo.setClassId( Long.valueOf( StringUtil.getLongValue(
                                ( String ) paramMap.get( "classId" ), -1 ) ) );

                            resInfo.setSiteId( siteBean.getSiteId() );

                            resInfo.setResName( StringUtil.subString( originalFilename, 0,
                                originalFilename.lastIndexOf( "." ) ) );

                            resInfo.setFileType( fileType );

                            resInfo.setModifyTime( new Timestamp( targetFile.lastModified() ) );
                            resInfo.setResSize( Long.valueOf( targetFile.length() ) );

                            resInfo.setResSource( day + Constant.CONTENT.URL_SEP
                                + targetFile.getName() );

                            // 回传数据
                            paramMap.put( "resName", StringUtil.subString( originalFilename, 0,
                                originalFilename.length() ) );

                            paramMap.put( "pefixDir", day );
                            paramMap.put( "relatePath", day + Constant.CONTENT.URL_SEP
                                + targetFile.getName() );
                            paramMap.put( "fileUrl", siteFileURL + targetFile.getName() );
                            paramMap.put( "genName", filename );
                            paramMap.put( "length", Long.valueOf( targetFile.length() ) );
                        }

                        List imgList = new ArrayList( 1 );
                        imgList.add( resInfo );
                        return imgList;
                    }
                }
            }
        }
        return null;
    }

    private void checkFile( SiteGroupBean site, String fileType, File targetFile, Integer resType,
        boolean zipMode )
    {
        boolean fileEndFlagRight = true;
        String name = targetFile.getName();
        String end = "";

        end = StringUtil.subString( name, name.indexOf( "." ) + 1, name.length() ).toLowerCase();
        if( !zipMode )// 是ZIP模式暂时允许所有文件
        {
            if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( resType ) )
            {
                // if( !( "jpg".equalsIgnoreCase( end ) ||
                // "gif".equalsIgnoreCase( end )
                // || "jpeg".equalsIgnoreCase( end ) || "png".equalsIgnoreCase(
                // end ) || "bmp"
                // .equalsIgnoreCase( end ) ) )
                // {
                // fileEndFlagRight = false;
                // }
                Set at = new HashSet( StringUtil.changeStringToList( site.getImageAllowType(), "," ) );

                if( !at.contains( end ) )
                {
                    fileEndFlagRight = false;
                }
            }
            else if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( resType ) )
            {
                // if( !( Constant.RESOURCE.VIDEO_RULE.indexOf( end ) != -1 ||
                // Constant.RESOURCE.MUSIC_RULE
                // .indexOf( end ) != -1 ) )
                // {
                // fileEndFlagRight = false;
                // }

                Set at = new HashSet( StringUtil.changeStringToList( site.getMediaAllowType(), "," ) );

                if( !at.contains( end ) )
                {
                    fileEndFlagRight = false;
                }

            }
            else if( Constant.RESOURCE.ANY_RES_TYPE.equals( resType ) )
            {
                // if( Constant.RESOURCE.FILE_RULE.indexOf( end ) == -1 )
                // {
                // fileEndFlagRight = false;
                // }
                Set at = new HashSet( StringUtil.changeStringToList( site.getFileAllowType(), "," ) );

                if( !at.contains( end ) )
                {
                    fileEndFlagRight = false;
                }
            }
            else
            {
                // 注意任何未知类型强制为不合法文件
                fileEndFlagRight = false;
            }

        }

        // 检查是否为html类型文本若后缀名已错,立即处理,若不错,进一步检查类型
        if( !fileEndFlagRight )
        {
            deleteErrorFileAndResponse( targetFile );
        }
        else if( end.equals( "png" ) || end.equals( "gif" ) || end.equals( "jpg" )
            || end.equals( "jpeg" ) || end.equals( "bmp" ) )
        {
            // 对于上传文件是文件后缀的要强制检查是否是合格的图片文件,有可能是其他文件改了名称
            Object[] imgOffset = ImageUtil.getImageHeightAndWidth( targetFile.getPath() );

            if( ( ( Integer ) imgOffset[0] ).intValue() == 0
                || ( ( Integer ) imgOffset[1] ).intValue() == 0 )
            {
                deleteErrorFileAndResponse( targetFile );
                fileEndFlagRight = false;
                // passMap.put( "errorMessage", "损坏的图片文件" );
            }
        }
        else if( !( fileEndFlagRight || "text/html".equals( fileType )
            || "text/plain".equals( fileType ) || "application/x-zip-compressed".equals( fileType )
            || "application/octet-stream".equals( fileType ) || ( fileType.indexOf( "image" ) == -1 ) ) )
        {
            deleteErrorFileAndResponse( targetFile );
        }
    }

    private void deleteErrorFileAndResponse( File targetFile )
    {
        targetFile.delete();// 删除非法文件
        if( targetFile.exists() )
        {
            // 尝试虚拟机关闭时删除
            // targetFile.deleteOnExit();
            throw new FrameworkException( "严重错误!所上传的文件非系统允许文件,且无法删除非法文件[" + targetFile.getName()
                + "],这可能造成系统安全隐患，请联系 系统管理员 !" );
        }
        throw new FrameworkException( "所上传的文件非系统允许文件!" );
    }

    public static void changeDocToWenku( SiteResource sr, SiteGroupBean siteBean, String sitePath )
    {
        try
        {
            if( Constant.RESOURCE.DOC_RULE.indexOf( "." + sr.getFileType() ) != -1 )
            {

                String sPath = StringUtil.replaceString( sr.getResSource(), "/", File.separator,
                    false, false );

                if( "pdf".equals( sr.getFileType() ) )
                {
                    // FileUtil.convertPdfToSwf( sitePath +
                    // siteBean.getSiteFlag() + File.separator
                    // + siteBean.getFileRoot() + File.separator + sPath,
                    // sitePath
                    // + siteBean.getSiteFlag() + File.separator +
                    // siteBean.getFileRoot()
                    // + File.separator + sPath + ".swf" );
                }
                else
                {
                    FileUtil.convertMSDocToPdf( sitePath + siteBean.getSiteFlag() + File.separator
                        + siteBean.getFileRoot() + File.separator + sPath, sitePath
                        + siteBean.getSiteFlag() + File.separator + siteBean.getFileRoot()
                        + File.separator + sPath + ".pdf" );

                    // FileUtil.convertPdfToSwf( sitePath +
                    // siteBean.getSiteFlag() + File.separator
                    // + siteBean.getFileRoot() + File.separator + sPath +
                    // ".pdf", sitePath
                    // + siteBean.getSiteFlag() + File.separator +
                    // siteBean.getFileRoot()
                    // + File.separator + sPath + ".swf" );

                }

                List swfList = new ArrayList();

                SiteResource swfSr = new SiteResource();

                // swfSr.setSiteId( siteBean.getSiteId() );
                // swfSr.setResType( Constant.RESOURCE.ANY_RES_TYPE );
                // swfSr.setResSource( sPath + ".swf" );
                //
                // swfList.add( swfSr );
                //
                // // 进行swf数据分发
                // siteService.transferUpdateDataToServer( swfList );

                swfList.clear();

                swfSr = new SiteResource();

                swfSr.setSiteId( siteBean.getSiteId() );
                swfSr.setResType( Constant.RESOURCE.ANY_RES_TYPE );
                swfSr.setResSource( sPath + ".pdf" );

                swfList.add( swfSr );

                // 进行pdf数据分发
                siteService.transferUpdateDataToServer( swfList );
            }
        }
        catch ( Exception e )
        {
            log.error( "无法转换office文档：" );
        }
    }
}
