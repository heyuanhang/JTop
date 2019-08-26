package cn.com.mjsoft.cms.resources.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.resources.dao.vo.SiteResource;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.ImageUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/resources" )
public class ManageSiteFileAndCheckController
{
    private static Logger log = Logger.getLogger( ManageSiteFileAndCheckController.class );

    private static final String SPLIT_CHAR = "*";

    private static final String UPLOAD_ENCODEING = "UTF-8";

    private static final List JSP_RULE;

    private static ResourcesService resService = ResourcesService.getInstance();

    private static SiteGroupService siteService = SiteGroupService.getInstance();

    static
    {
        JSP_RULE = new ArrayList( 2 );

        // 系统支持两种模板
        JSP_RULE.add( "jsp" );
        JSP_RULE.add( "thtml" );
    }

    @ResponseBody
    @RequestMapping( value = "/multiUploadSiteFile.do", method = { RequestMethod.POST } )
    public Object multiUploadSiteFile( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        // 前置检查
        boolean fileOk = checkFile( params );

        Map paramPassMap = new HashMap();

        if( fileOk )
        {
            paramPassMap.put( "errorMessage", "" );

            List templateResList = uploadFileToSite( request, 1000 * 1024 * 1024, UPLOAD_ENCODEING,
                null, paramPassMap, ( String ) params.get( "entry" ) );

            // 分发模板资源到服务器
            if( templateResList != null )
            {
                siteService.transferSiteTemplateDataToServer( templateResList,
                    ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                        .getCurrentLoginSiteInfo() );
            }

        }

        List jsonList = new ArrayList();
        jsonList.add( paramPassMap );

        return StringUtil.changeJSON( jsonList, "obj_" );

    }

    @RequestMapping( value = "/deleteSiteFile.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除站点文件", token = true )
    public ModelAndView deleteSiteFile( HttpServletRequest request, HttpServletResponse response )
        throws UnsupportedEncodingException
    {
        Map params = ServletUtil.getRequestInfo( request );

        String entry = URLDecoder.decode( ( String ) params.get( "entry" ), "UTF-8" );

        String templateMode = ( String ) params.get( "templateMode" );

        String fileInfo = URLDecoder.decode( ( String ) params.get( "fileInfo" ), "UTF-8" );

        String[] targetFileArray = StringUtil.split( fileInfo, "\\*" );

        resService.deleteSiteFileOrFolderInfo( targetFileArray, entry, templateMode );

        Map paramMap = new HashMap();

        paramMap.put( "parentFolder", ( String ) params.get( "entry" ) );
        paramMap.put( "deleteFlag", "true" );

        if( "true".equals( templateMode ) )
        {
            return ServletUtil.redirect( "/core/templet/ManageTemplate.jsp", paramMap );
        }

        return ServletUtil.redirect( "/core/resources/ListFileRes.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/listSiteFolderTree.do", method = { RequestMethod.GET } )
    public Object listSiteFolderTree( HttpServletRequest request, HttpServletResponse response )
        throws UnsupportedEncodingException
    {
        Map params = ServletUtil.getRequestInfo( request );

        String mode = ( String ) params.get( "mode" );

        String parentFolder = URLDecoder.decode( ( String ) params.get( "parentFolder" ), "UTF-8" );

        // 若不是指定命令,强制为folder模式
        if( !Constant.RESOURCE.LIST_FILE_MODE_ALL.equals( mode ) )
        {
            mode = Constant.RESOURCE.LIST_FILE_MODE_FOLDER;
        }

        List fileItemList = resService.retrieveSiteFolderAndFileTreeByParentFolder( parentFolder,
            mode, false );

        Map jsonMap = new TreeMap();

        Object jsonBean = null;

        for ( int i = 0; i < fileItemList.size(); i++ )
        {
            jsonBean = fileItemList.get( i );
            jsonMap.put( "file_" + Integer.valueOf( i ), jsonBean );
        }

        return jsonMap;

    }

    @RequestMapping( value = "/renameFileOrFolder.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "重命名站点文件", token = true )
    public ModelAndView renameFileOrFolder( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String entry = SystemSafeCharUtil.decodeFromWeb( ( String ) params.get( "entry" ) );

        String newName = ( String ) params.get( "newName" );

        String fileTip = ( String ) params.get( "fileTip" );

        String action = ( String ) params.get( "type" );

        resService.renameFileOrFolder( newName, entry, action, fileTip );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/resources/RenameFileOrFolder.jsp", paramMap );
    }

    @RequestMapping( value = "/moveFile.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "移动站点文件", token = true )
    public ModelAndView moveFile( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String entry = SystemSafeCharUtil.decodeFromWeb( ( String ) params.get( "entry" ) );

        String target = ( String ) params.get( "target" );

        String fileInfo = SystemSafeCharUtil.decodeFromWeb( ( String ) params.get( "fileInfo" ) );

        String[] targetFileArray = StringUtil.split( fileInfo, "\\*" );

        resService.moveFile( entry, targetFileArray, target );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/resources/ShowFolder.jsp", paramMap );
    }

    @RequestMapping( value = "/downloadResFile.do", method = { RequestMethod.POST } )
    public ModelAndView downloadResFile( HttpServletRequest request, HttpServletResponse response )
        throws UnsupportedEncodingException
    {
        Map params = ServletUtil.getRequestInfo( request );

        String mode = ( String ) params.get( "mode" );

        String entry = SystemSafeCharUtil.decodeFromWeb( ( String ) params.get( "entry" ) );

        String fileEntry = SystemSafeCharUtil
            .decodeFromWeb( ( String ) params.get( "downFileInfo" ) );

        File file = null;

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( siteBean == null || !SecuritySessionKeeper.getSecuritySession().isManager() )
        {
            return null;
        }

        String tempRoot = siteBean.getSiteRoot() + "_"
            + DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD ) + "_"
            + DateAndTimeUtil.clusterTimeMillis();

        String zipName = tempRoot + ".zip";

        if( "allTemplate".equals( mode ) )
        {
            // template全部打包
            String fullPath = resService.getFullFilePathByManager( "*"
                + Constant.CONTENT.TEMPLATE_BASE );

            if( fullPath == null )
            {
                return null;
            }

            String fullZipPath = resService.getFullFilePathByManager( "*"
                + Constant.CONTENT.TEMPLATE_TEMP_BASE + "*SITE_TEMPLATE_"
                + DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD )
                + ".zip" );

            if( fullZipPath == null )
            {
                return null;
            }

            FileUtil.zip( fullZipPath, fullPath );

            file = new File( fullZipPath );

        }
        else
        {

            String fullZipPath = resService.getFullFilePathByManager( "*"
                + Constant.CONTENT.TEMPLATE_TEMP_BASE + "*" + zipName );

            if( fullZipPath == null )
            {
                return null;
            }

            String[] targetFileArray = StringUtil.split( fileEntry, "\\*" );

            String fileFullPath = null;

            String filePath = null;

            String newTempRoot = resService.getFullFilePathByManager( "*"
                + Constant.CONTENT.TEMPLATE_TEMP_BASE + "*" + tempRoot );

            ( new File( newTempRoot ) ).mkdirs(); // 如果文件夹不存在 则建立新文件夹

            for ( int i = 0; i < targetFileArray.length; i++ )
            {
                filePath = targetFileArray[i];

                if( "sys_template_temp".equals( filePath ) )
                {
                    continue;
                }

                if( StringUtil.isStringNotNull( filePath ) )
                {
                    fileFullPath = entry + "*" + filePath;

                    String fullPath = resService.getFullFilePathByManager( fileFullPath );

                    if( fullPath == null )
                    {
                        return null;
                    }

                    file = new File( fullPath );

                    if( !file.exists() )
                    {
                        return null;
                    }

                    File testFile = new File( fullPath );

                    if( testFile.isFile() )
                    {

                        FileUtil.copyFile( fullPath, newTempRoot + File.separator + filePath );
                    }
                    else
                    {
                        FileUtil.copyFolder( fullPath, newTempRoot + File.separator + filePath );
                    }

                }
            }

            FileUtil.zip( fullZipPath, newTempRoot );

            file = new File( fullZipPath );

            FileUtil.delAllFile( newTempRoot, true );

        }

        // 文件类型响应报文头
        response.reset();// 强制清除response内部信息
        response.setContentType( "application/octet-stream" );
        response.setHeader( "Content-Disposition", "attachment; filename="
            + new String( file.getName().getBytes( JtRuntime.cmsServer.getEncoding() ),
                "iso-8859-1" ) );
        response.addHeader( "Content-Length", "" + file.length() );

        // 获取输出流，将文件写出到客户端
        OutputStream os = null;
        FileInputStream fis = null;
        try
        {
            os = response.getOutputStream();
            // 将下载文件读入
            fis = new FileInputStream( file );
            byte[] b = new byte[1024];
            while ( fis.read( b ) != -1 )
            {
                os.write( b );
            }
            os.flush();
        }
        catch ( Exception e )
        {
        }
        finally
        {
            try
            {
                if( fis != null )
                {
                    fis.close();
                }

                if( os != null )
                {
                    os.close();
                }
            }
            catch ( Exception e )
            {
            }
        }

        return null;
    }

    private boolean checkFile( Map params )
    {

        String fileType = null;
        String fileSize = null;

        fileType = ( String ) params.get( "fileType" );
        fileSize = ( String ) params.get( "fileSize" );

        if( StringUtil.isStringNull( fileType ) )
        {
            return false;
        }

        if( StringUtil.isStringNull( fileSize ) )
        {
            return false;
        }

        if( Constant.RESOURCE.RULE.indexOf( fileType.toLowerCase() ) == -1 )
        {
            return false;
        }

        int size = StringUtil.getIntValue( fileSize, 0 );

        if( size == 0 )
        {
            return false;
        }

        int mb = size / 1024 / 1024;
        if( mb > 100 )
        {
            return false;
        }

        return true;
    }

    private List uploadFileToSite( HttpServletRequest request, int maxSize, String encodeing,
        FileRenamePolicy policy, Map paramMap, String entry )

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

        boolean tmplateUploadMode = false;

        if( StringUtil.isStringNull( entry ) )
        {
            fullPath = rootPath;
        }
        else
        {
            String endEntry = StringUtil.replaceString( entry, SPLIT_CHAR, File.separator, false,
                false );

            if( endEntry.startsWith( File.separator + Constant.CONTENT.TEMPLATE_BASE ) )
            {
                tmplateUploadMode = true;
            }

            log.info( "[UploadFileToSite] 入口:" + entry );

            fullPath = rootPath + endEntry;

        }

        File tf = new File( fullPath );
        if( !tf.exists() )
        {
            tf.mkdirs();
        }

        // 上传大小需要控制
        MultipartRequest multi = null;
        try
        {
            multi = new MultipartRequest( request, fullPath, maxSize, encodeing, policy );
        }
        catch ( IOException e )
        {

            e.printStackTrace();
        }

        // 获取form的其他参数
        System.out.println( "PARAMS: 组装开始" );
        Enumeration params = multi.getParameterNames();
        while ( params.hasMoreElements() )
        {
            String name = ( String ) params.nextElement();
            String value = multi.getParameter( name );

            System.out.println( name + "=" + value );
        }

        System.out.println( "PARAMS: 组装结束" );

        System.out.println( "FILES:" );
        Enumeration files = multi.getFileNames();

        while ( files.hasMoreElements() )
        {
            String name = ( String ) files.nextElement();
            String filename = multi.getFilesystemName( name );
            String originalFilename = multi.getOriginalFileName( name );
            String type = multi.getContentType( name );
            File f = multi.getFile( name );
            System.out.println( "上传标签名称: " + name );
            System.out.println( "文件新名称: " + filename );
            System.out.println( "原始名称: " + originalFilename );
            System.out.println( "文件type: " + type );
            if( f != null )
            {

                System.out.println( "文件.toString(): " + f.toString() );
                System.out.println( "文件.getName(): " + f.getName() );
                System.out.println( "文件.exists(): " + f.exists() );
                System.out.println( "文件.length(): " + f.length() );

                if( f.exists() )
                {
                    // String rootPath = ServletUtility.getSiteFilePath( this
                    // .getServletFlowContext().getConfig() );

                    // if( "application/x-zip-compressed".equals( type ) )
                    if( f.getName().toLowerCase().endsWith( ".zip" ) )
                    {
                        checkFile( type, f.length(), f, paramMap, true );
                        Map fileInfo = resService.disposeZIPFile( f.getPath(), fullPath
                            + File.separator );

                        Iterator fiIter = fileInfo.entrySet().iterator();

                        Entry fileEntry = null;
                        String fn = null;

                        List<SiteResource> resList = new ArrayList<SiteResource>();

                        while ( fiIter.hasNext() )
                        {
                            fileEntry = ( Entry ) fiIter.next();
                            fn = ( String ) fileEntry.getKey();

                            if( fn.toLowerCase().endsWith( ".jsp" )
                                || fn.toLowerCase().endsWith( ".thtml" ) )
                            {
                                File targetFile = new File( ( String ) fileEntry.getValue() );

                                if( !checkTemplate( new File( ( String ) fileEntry.getValue() ) ) )
                                {
                                    log.info( "下列非法文件将被删除:" + fn );
                                    targetFile.delete();
                                    if( targetFile.exists() )
                                    {
                                        targetFile.deleteOnExit();
                                    }

                                    continue;
                                }
                            }

                            SiteResource resInfo = new SiteResource();

                            resInfo.setSiteId( siteBean.getSiteId() );
                            resInfo.setResSource( ( String ) fileEntry.getValue() );

                            resList.add( resInfo );

                        }

                        // 删除ZIP文件
                        f.delete();
                        if( f.exists() )
                        {
                            f.deleteOnExit();
                        }

                        return resList;
                    }
                    else
                    {

                        boolean isOk = checkFile( type, f.length(), f, paramMap, false );

                        if( isOk && tmplateUploadMode )
                        {
                            List<SiteResource> resList = new ArrayList<SiteResource>();

                            SiteResource resInfo = new SiteResource();

                            resInfo.setSiteId( siteBean.getSiteId() );
                            resInfo.setResSource( f.getAbsolutePath() );

                            resList.add( resInfo );

                            return resList;
                        }

                        // 回传文件信息
                        // paramMap.put( "name", originalFilename );

                        // paramMap.put( "path", f.getPath() );

                        // imgList.add( imgInfo );

                    }
                }

            }

            // System.out.println();
        }
        return null;
    }

    private boolean checkFile( String fileType, long length, File targetFile, Map passMap,
        boolean zipMode )
    {
        boolean fileEndFlagRight = true;
        String name = targetFile.getName();
        String end = "";

        end = StringUtil.subString( name, name.lastIndexOf( "." ) + 1, name.length() )
            .toLowerCase();
        if( !zipMode )// 
        {
            if( Constant.RESOURCE.RULE.indexOf( "." + end.toLowerCase() ) == -1 )
            {
                fileEndFlagRight = false;
                passMap.put( "errorMessage", "不允许的文件类型" );
            }

            if( length == 0 )
            {
                fileEndFlagRight = false;
                passMap.put( "errorMessage", "文件大小超过范围" );
            }

            long mb = length / 1024 / 1024;
            if( mb > 100 )
            {
                fileEndFlagRight = false;
                passMap.put( "errorMessage", "文件大小超过范围" );
            }
        }

        // 检查是否为html类型文本若后缀名已错,立即处理,若不错,进一步检查类型
        if( !fileEndFlagRight )
        {
            deleteErrorFileAndResponse( targetFile );
            fileEndFlagRight = false;
        }
        else if( end.equals( "jsp" ) && !checkTemplate( targetFile ) )
        {
            deleteErrorFileAndResponse( targetFile );
            fileEndFlagRight = false;
            passMap.put( "errorMessage", "非模板文件" );
        }
        else if( end.equals( "png" ) || end.equals( "gif" ) || end.equals( "jpg" )
            || end.equals( "jpeg" ) || end.equals( "bmp" ) )
        {
            // 检查是否是合格的图片文件
            Object[] imgOffset = ImageUtil.getImageHeightAndWidth( targetFile.getPath() );

            if( ( ( Integer ) imgOffset[0] ).intValue() == 0
                || ( ( Integer ) imgOffset[1] ).intValue() == 0 )
            {
                deleteErrorFileAndResponse( targetFile );
                fileEndFlagRight = false;
                passMap.put( "errorMessage", "损坏的图片文件" );
            }
        }
        else if( !( fileEndFlagRight || "text/html".equals( fileType )
            || "text/plain".equals( fileType ) || "application/x-zip-compressed".equals( fileType )
            || "application/octet-stream".equals( fileType ) || ( fileType.indexOf( "image" ) == -1 ) ) )
        {
            deleteErrorFileAndResponse( targetFile );
            fileEndFlagRight = false;
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
            throw new FrameworkException( "严重错误!所上传的文件非图片文件,且无法删除非法文件[" + targetFile.getName()
                + "],这可能造成系统安全隐患，请联系 系统管理员 !" );
        }
        log.info( "删除非法文件! file:" + targetFile );
        // throw new FrameworkException( "所上传的文件非图片文件!" );
    }

    private boolean checkTemplate( File targetFile )
    {
        Object[] obj = FileUtil.getTXTFileContent( targetFile.getPath(), JSP_RULE, null );

        String content = ( String ) obj[0];
        return ResourcesService.checkFile( content );
    }

}
