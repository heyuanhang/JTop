package cn.com.mjsoft.cms.templet.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.templet.bean.FileInfoBean;
import cn.com.mjsoft.cms.templet.bean.TemplateContentBean;
import cn.com.mjsoft.cms.templet.bean.TempletFileBean;
import cn.com.mjsoft.cms.templet.dao.TemplateDao;
import cn.com.mjsoft.cms.templet.dao.vo.SiteTemplet;
import cn.com.mjsoft.cms.templet.html.FileTypeTemplateFilter;
import cn.com.mjsoft.cms.templet.html.FolderTypeFilter;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.StringUtil;

public class TempletService
{
  

    private static Logger log = Logger.getLogger( TempletService.class );

    private static final FileFilter FOLDER_TYPE = new FolderTypeFilter();

    private static final FileFilter FILE_TYPE = new FileTypeTemplateFilter();

    private static final List RULE;

    private static TempletService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private TemplateDao templetDao = null;

    static
    {
        RULE = new ArrayList();

        RULE.add( "jsp" );
        RULE.add( "ftl" );
        RULE.add( "thtml" );
        RULE.add( "html" );
        RULE.add( "htm" );
        RULE.add( "js" );
        RULE.add( "css" );
        RULE.add( "txt" );
        RULE.add( "xml" );
    }

    private TempletService()
    {
        templetDao = new TemplateDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new TempletService();
        }
    }

    public static TempletService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public List retrieveTempletBeanByTypeAndSite( Integer type, String site )
    {
        List beanList = null;
        beanList = this.templetDao.queryTempletBeanByTypeAndSite( type, site );
        return beanList;
    }

    public List retrieveAllTempletFileBySite( String entry, String rootPath, String folderMode )
    {
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
            String endEntry = StringUtil.replaceString( entry, "*", File.separator, false, false );

            log.info( "最终入口:" + endEntry );

            fullPath = rootPath + endEntry;
        }
        List fileResultList = new ArrayList();

        File[] folders = FileUtil.getAllFile( fullPath, FOLDER_TYPE );

        File[] files = FileUtil.getAllFile( fullPath, FILE_TYPE );

        String targetPath = "";
        if( folders != null )
        {
            for ( int i = 0; i < folders.length; i++ )
            {
                FileInfoBean bean = new FileInfoBean();
                bean.setFileName( folders[i].getName() );
                bean.setLastModifyTime( DateAndTimeUtil.getFormatDate( folders[i].lastModified(),
                    "yyyy-MM-dd HH:mm:ss" ) );

                bean.setCreator( "Admin" );

                targetPath = StringUtil.replaceString( folders[i].getPath(), rootPath, "", false,
                    true );

                bean.setEntry( StringUtil.replaceString( targetPath, File.separator, "*", false,
                    false ) );

                bean.setDir( true );
                fileResultList.add( bean );
            }
        }
        if( ( files != null ) && ( "false".equals( folderMode ) ) )
        {
            for ( int j = 0; j < files.length; j++ )
            {
                FileInfoBean bean = new FileInfoBean();
                bean.setFileName( files[j].getName() );
                bean.setLastModifyTime( DateAndTimeUtil.getFormatDate( files[j].lastModified(),
                    "yyyy-MM-dd HH:mm:ss" ) );
                targetPath = StringUtil.replaceString( files[j].getPath(), rootPath, "", false,
                    true );

                bean.setEntry( StringUtil.replaceString( targetPath, File.separator, "*", false,
                    false ) );

                String fileName = files[j].getName();
                String fileType = StringUtil.subString( fileName, fileName.lastIndexOf( "." ) + 1,
                    fileName.length() );

                bean.setCreator( "Admin" );

                bean.setType( fileType );
                bean.setSize( files[j].length() );
                bean.setDir( false );
                fileResultList.add( bean );
            }
        }
        return fileResultList;
    }

    public TemplateContentBean retrieveSingleTempletFileContent( String site, String entry,
        String rootPath )
    {
        String fullPath = "";
        if( StringUtil.isStringNull( entry ) )
        {
            return new TemplateContentBean();
        }
        if( ( entry.indexOf( "../" ) != -1 ) || ( entry.indexOf( "..%2F" ) != -1 )
            || ( entry.indexOf( "..%2f" ) != -1 ) || ( entry.indexOf( "WEB-INF" ) != -1 ) )
        {
            return new TemplateContentBean();
        }
        String endEntry = StringUtil.replaceString( entry, "*", File.separator, false, false );

        log.info( "最终入口:" + endEntry );

        fullPath = rootPath + endEntry;

        Object[] value = FileUtil.getTXTFileContent( fullPath, RULE, null );

        TemplateContentBean bean = new TemplateContentBean();
        bean.setContent( ( String ) value[0] );
        bean.setLineCount( ( Integer ) value[1] );

        return bean;
    }

    public void writeSingleTempletFileContent( String site, String entry, String rootPath,
        String content )
    {
        String fullPath = "";
        if( StringUtil.isStringNull( entry ) )
        {
            fullPath = rootPath;
        }
        else
        {
            String endEntry = StringUtil.replaceString( entry, "*", File.separator, false, false );

            log.info( "最终入口:" + endEntry );

            fullPath = rootPath + endEntry;
        }
        try
        {
            FileUtil.writeTXTFileContent( content, fullPath, "GBK" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public void disposeZIPTempletFile( String path, String templatPath, String sitePath,
        String root, String site )
    {
        log.info( "disposeZIPTempletFile()  path:" + path + " ,templatPath" + templatPath
            + " ,sitePath" + sitePath );

        FileUtil.unZipFile( path, templatPath, false );

        log.info( "开始生成实际访问文件" );
        try
        {
            this.mysqlEngine.beginTransaction();
            this.mysqlEngine.startBatch();
            generateSiteTemplate( templatPath, sitePath, root, site );
            this.mysqlEngine.executeBatch();
            this.mysqlEngine.commit();
        }
        finally
        {
            this.mysqlEngine.endTransaction();
        }
    }

    public void disposeSingleTempletFile( String oldFileFullPath, String templetRootPath,
        String siteRootPath, String entry, String root, String site )
    {
        log.info( "disposeSingleTempletFile()  oldFileFullPath:" + oldFileFullPath
            + " ,templetRootPath" + templetRootPath + " ,sitePath" + siteRootPath + ", entry:"
            + entry );

        File oldFile = new File( oldFileFullPath );
        if( !oldFile.exists() )
        {
            return;
        }
        if( oldFile.isDirectory() )
        {
            return;
        }
        String targetFileName = StringUtil.subString( oldFileFullPath, oldFileFullPath
            .lastIndexOf( File.separator ) + 1, oldFileFullPath.length() );

        log.info( "targetFileName:" + targetFileName );

        String newFilePath = "";
        if( StringUtil.isStringNull( entry ) )
        {
            newFilePath = templetRootPath + entry + targetFileName;
        }
        else
        {
            newFilePath = templetRootPath + entry + File.separator + targetFileName;
        }
        FileUtil.copyFile( oldFileFullPath, newFilePath );

        String type = StringUtil.subString( targetFileName, targetFileName.lastIndexOf( "." ) + 1,
            targetFileName.length() );
        if( ( "html".equalsIgnoreCase( type ) ) || ( "htm".equalsIgnoreCase( type ) ) )
        {
            disposeSingleHtmlTemplet( new File( newFilePath ), templetRootPath, siteRootPath, root,
                site );
            oldFile.delete();
            return;
        }
        String entryFilePath = entry + File.separator + targetFileName;

        String targetFileFullPath = siteRootPath + entryFilePath;

        File targetFile = new File( targetFileFullPath );
        if( targetFile.exists() )
        {
            targetFile.delete();
        }
        FileUtil.copyFile( oldFileFullPath, targetFileFullPath );
        oldFile.delete();
        if( "jsp".equalsIgnoreCase( type ) )
        {
            SiteTemplet templet = new SiteTemplet();

            templet.setTempletFileName( targetFile.getName() );
            templet.setRelatedTempletFilePath( "" );
            templet.setTempletFullPath( StringUtil.replaceString( entryFilePath, File.separator,
                "/", false, false ) );

            templet.setType( Integer.valueOf( "-1" ) );
            templet.setTempletDisplayName( "" );
            templet.setTempletDesc( "" );
            templet.setSiteName( site );

            this.templetDao.saveSiteTemplet( templet );
        }
    }

    private void generateSiteTemplate( String templetPath, String sitePath, String root, String site )
    {
        log.info( "generateSiteTemplate()  templetPath:" + templetPath + " ,sitePath" + sitePath );
        try
        {
            new File( sitePath ).mkdirs();
            File a = new File( templetPath );
            String[] file = a.list();
            File targetFile = null;
            for ( int i = 0; i < file.length; i++ )
            {
                if( templetPath.endsWith( File.separator ) )
                {
                    targetFile = new File( templetPath + file[i] );
                }
                else
                {
                    targetFile = new File( templetPath + File.separator + file[i] );
                }
                if( targetFile.isFile() )
                {
                    String fileName = targetFile.getName();

                    String type = StringUtil.subString( fileName, fileName.lastIndexOf( "." ) + 1,
                        fileName.length() );
                    if( ( "html".equalsIgnoreCase( type ) ) || ( "htm".equalsIgnoreCase( type ) ) )
                    {
                        disposeSingleHtmlTemplet( targetFile, templetPath, sitePath, root, site );
                    }
                    else
                    {
                        copyTempletFile( targetFile, sitePath );
                        if( "jsp".equalsIgnoreCase( type ) )
                        {
                            SiteTemplet templet = new SiteTemplet();

                            templet.setTempletFileName( targetFile.getName() );
                            templet.setRelatedTempletFilePath( "" );
                            templet.setTempletFullPath( StringUtil.replaceString( targetFile
                                .getPath(), root + "templet" + File.separator + site
                                + File.separator, "", false, false ) );

                            templet.setType( Integer.valueOf( "-1" ) );
                            templet.setTempletDisplayName( "" );
                            templet.setTempletDesc( "" );
                            templet.setSiteName( site );

                            this.templetDao.saveSiteTemplet( templet );
                        }
                    }
                }
                if( targetFile.isDirectory() )
                {
                    generateSiteTemplate( templetPath + file[i] + File.separator, sitePath
                        + file[i] + File.separator, root, site );
                }
            }
        }
        catch ( Exception e )
        {
            log.error( "生成实际访问模板出错" );
            e.printStackTrace();
        }
    }

    private void copyTempletFile( File targetFile, String sitePath ) throws Exception
    {
        FileInputStream input = new FileInputStream( targetFile );
        FileOutputStream output = new FileOutputStream( sitePath + File.separator
            + targetFile.getName().toString() );

        BufferedInputStream bufIn = new BufferedInputStream( input );
        BufferedOutputStream bufOut = new BufferedOutputStream( output );

        FileUtil.writeFile( bufIn, bufOut );

        output.close();
        input.close();
        bufIn.close();
        bufOut.close();
    }

    private void disposeSingleHtmlTemplet( File targetFile, String templetPath, String sitePath,
        String root, String site )
    {
        String currentCode = FileUtil.getTXTFileCode( targetFile );
        try
        {
            log.info( "当前处理文本编码为:" + currentCode + ", 文件名:" + targetFile.getName() );
            String charset = "UTF-8".equalsIgnoreCase( currentCode ) ? "UTF-8" : "GBK";

            Behavior be = new CheckIsTempletBehavior();

            Object[] isTempletflag = { Boolean.FALSE };

            String htmTempletContent = ( String ) FileUtil.readTXTFileContent( targetFile, charset,
                be, isTempletflag )[0];

            log.info( targetFile.getName() + "是模板文件吗?:" + isTempletflag[0] );
            if( Boolean.TRUE.equals( isTempletflag[0] ) )
            {
                String targetJSP = StringUtil.replaceString( targetFile.getPath(), templetPath,
                    sitePath, false, false );

                File jspFile = new File( StringUtil.subString( targetJSP, 0, targetJSP
                    .lastIndexOf( "." ) )
                    + ".jsp" );

                jspFile.createNewFile();
                FileUtil
                    .writeTXTFileContent(
                        "<%@ page contentType=\"text/html; charset=GBK\" session=\"false\"%>\n<%@ taglib uri=\"/cmsTag\" prefix=\"cms\"%>\n<%@ page contentType=\"text/html; charset=GBK\"%>\n"
                            + htmTempletContent, jspFile.getPath(), "GBK" );

                SiteTemplet templet = new SiteTemplet();

                templet.setTempletFileName( targetFile.getName() );
                templet.setRelatedTempletFilePath( StringUtil.replaceString( targetFile.getPath(),
                    root + "templet" + File.separator + site + File.separator, "", false, false ) );
                templet.setTempletFullPath( StringUtil.replaceString( jspFile.getPath(), root
                    + "site" + File.separator + site + File.separator, "", false, false ) );

                templet.setType( Integer.valueOf( "-1" ) );
                templet.setTempletDisplayName( "" );
                templet.setTempletDesc( "" );
                templet.setSiteName( site );

                this.templetDao.saveSiteTemplet( templet );
            }
            else
            {
                copyTempletFile( targetFile, sitePath );
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public TempletFileBean retrieveSingleTemplet( Long id )
    {
        return this.templetDao.queryTempletBeanById( id );
    }

    public Object getTemplateEditorTag( String templateName, String tempId )
    {
        if( StringUtil.isStringNotNull( tempId ) )
        {
            return this.templetDao.querySingleTemplateEditionInfo( Long.valueOf( StringUtil
                .getLongValue( tempId, -1L ) ) );
        }
        templateName = SystemSafeCharUtil.decodeFromWeb( templateName );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Map style = this.templetDao.querySingleTemplateStyleByUse( site.getSiteId(),
            Constant.COMMON.ON );
        if( style.isEmpty() )
        {
            style.put( "styleFlag", "_sys_def_" );
        }
        return this.templetDao.queryTemplateEditionInfoList( templateName, site.getSiteId(),
            ( String ) style.get( "styleFlag" ) );
    }

    public Object getTemplateHelperForTag( String templateName )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Map style = this.templetDao.querySingleTemplateStyleByUse( site.getSiteId(),
            Constant.COMMON.ON );
        if( style.isEmpty() )
        {
            style.put( "styleFlag", "_sys_def_" );
        }
        return this.templetDao.querySingleTemplateTipHelper( site.getSiteId(), templateName,
            ( String ) style.get( "styleFlag" ) );
    }

    public void addTemplateStyle( Map params )
    {
        String stylePic = ( String ) params.get( "stylePic" );

        Long slResId = Long.valueOf( StringUtil.getLongValue( stylePic, -1L ) );

        params.put( "stylePic", ServiceUtil.disposeSingleImageInfo( slResId ) );

        params.put( "isUse", Constant.COMMON.OFF );

        params.put( "siteId", ( ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo() ).getSiteId() );

        this.templetDao.saveTemplateStyle( params );

        String basePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String fullPath = basePath + site.getSiteFlag() + File.separator + "sys_template_style"
            + File.separator + params.get( "styleFlag" ) + File.separator + "template";

        File file = new File( fullPath );

        file.mkdirs();
 
        ClusterService.exeClusterMasterCMD( "cluster/newFolder.do", "fp="
            + StringUtil.replaceString( StringUtil.replaceString( fullPath, basePath, "" ), 
                File.separator, "/" ), Constant.COMMON.POST );

    }

    public void editTemplateStyle( Map params )
    {
        String stylePic = ( String ) params.get( "stylePic" );

        Long slResId = Long.valueOf( StringUtil.getLongValue( stylePic, -1L ) );

        params.put( "stylePic", ServiceUtil.disposeSingleImageInfo( slResId ) );

        this.templetDao.updateTemplateStyle( params );

        String basePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Map ts = this.templetDao.querySingleTemplateStyle( Long.valueOf( StringUtil.getLongValue(
            ( String ) params.get( "tsId" ), -1L ) ) );

        String fullPath = basePath + site.getSiteFlag() + File.separator + "sys_template_style"
            + File.separator + ts.get( "styleFlag" ) + File.separator + "template";

        File file = new File( fullPath );
        if( !file.exists() )
        {
            file.mkdirs();
        }
        file.mkdirs();
    }

    public String deleteTemplateStyle( Long id )
    {
        Map tmpStyle = null;
        try
        {
            mysqlEngine.beginTransaction();

            tmpStyle =  templetDao.querySingleTemplateStyle( id );
            
            if( Constant.COMMON.ON.equals( tmpStyle.get( "isUse" ) ) )
            {
                return "onuse";
            }
            
             
            templetDao.deleteTemplateStyle( id );
            
            mysqlEngine.commit();
        }
        finally
        {
            this.mysqlEngine.endTransaction();
        }
      

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String basePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        if( StringUtil.isStringNotNull( ( String ) tmpStyle.get( "styleFlag" ) ) )
        {
            String styleFullPath = basePath + site.getSiteFlag() + File.separator
                + "sys_template_style" + File.separator + tmpStyle.get( "styleFlag" );

            FileUtil.delFolder( styleFullPath );

            ClusterService.exeClusterMasterCMD( "cluster/deleteFolder.do", "fp="
                + site.getSiteFlag() + "/" + "sys_template_style" + "/"
                + tmpStyle.get( "styleFlag" ), Constant.COMMON.POST );

        }
        return "success";
    }

    public void changeTemplateStyle( Long tsId, String changeFlag )
    {
        if( ( tsId == null ) || ( tsId.longValue() < 0L ) )
        {
            return;
        }
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Map prevStyle = null;
        try
        {
            this.mysqlEngine.beginTransaction();

            prevStyle = this.templetDao.querySingleTemplateStyleByUse( site.getSiteId(),
                Constant.COMMON.ON );
            if( "1".equals( changeFlag ) )
            {
                this.templetDao.updateTemplateStyleAllUseFlag( site.getSiteId(),
                    Constant.COMMON.OFF, tsId );
            }
            this.templetDao.updateTemplateStyleUseFlag( site.getSiteId(), tsId, Integer
                .valueOf( changeFlag ) );

            this.mysqlEngine.commit();
        }
        finally
        {
            this.mysqlEngine.endTransaction();
        }
        Map useTs = this.templetDao.querySingleTemplateStyleByUse( site.getSiteId(),
            Constant.COMMON.ON );

        String basePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String templateFullPath = basePath + site.getSiteFlag() + File.separator + "template";
        if( !prevStyle.isEmpty() )
        {
            if( useTs.isEmpty() )
            {
                String prevFullPath = basePath + site.getSiteFlag() + File.separator
                    + "sys_template_style" + File.separator + prevStyle.get( "styleFlag" )
                    + File.separator + "template";

                FileUtil.moveFolder( templateFullPath, prevFullPath );
                
               
                ClusterService.exeClusterMasterCMD( "cluster/moveFolder.do", "ofp="
                    + StringUtil.replaceString( StringUtil.replaceString( templateFullPath,
                        basePath, "" ), File.separator, "/" )
                    + "&nfp="
                    + StringUtil.replaceString( StringUtil.replaceString( prevFullPath,
                        basePath, "" ), File.separator, "/" ), Constant.COMMON.POST );

                String defaultFullPath = basePath + site.getSiteFlag() + File.separator
                    + "sys_template_style" + File.separator + "__sys_default_site_template__"
                    + File.separator + "template";

                FileUtil.moveFolder( defaultFullPath, templateFullPath );
                
                
                ClusterService.exeClusterMasterCMD( "cluster/moveFolder.do", "ofp="
                    + StringUtil.replaceString( StringUtil.replaceString( defaultFullPath,
                        basePath, "" ), File.separator, "/" )
                    + "&nfp="
                    + StringUtil.replaceString( StringUtil.replaceString( templateFullPath,
                        basePath, "" ), File.separator, "/" ), Constant.COMMON.POST );

            }
            else if( !useTs.get( "tsId" ).equals( prevStyle.get( "tsId" ) ) )
            {
                String prevFullPath = basePath + site.getSiteFlag() + File.separator
                    + "sys_template_style" + File.separator + prevStyle.get( "styleFlag" )
                    + File.separator + "template";

                FileUtil.moveFolder( templateFullPath, prevFullPath );
                
                ClusterService.exeClusterMasterCMD( "cluster/moveFolder.do", "ofp="
                    + StringUtil.replaceString( StringUtil.replaceString( templateFullPath,
                        basePath, "" ), File.separator, "/" )
                    + "&nfp="
                    + StringUtil.replaceString( StringUtil.replaceString( prevFullPath,
                        basePath, "" ), File.separator, "/" ), Constant.COMMON.POST );

                String newFullPath = basePath + site.getSiteFlag() + File.separator
                    + "sys_template_style" + File.separator + useTs.get( "styleFlag" )
                    + File.separator + "template";

                FileUtil.moveFolder( newFullPath, templateFullPath );
                
                ClusterService.exeClusterMasterCMD( "cluster/moveFolder.do", "ofp="
                    + StringUtil.replaceString( StringUtil.replaceString( newFullPath,
                        basePath, "" ), File.separator, "/" )
                    + "&nfp="
                    + StringUtil.replaceString( StringUtil.replaceString( templateFullPath,
                        basePath, "" ), File.separator, "/" ), Constant.COMMON.POST );
            }
        }
        else if( !useTs.isEmpty() )
        {
            String defaultFullPath = basePath + site.getSiteFlag() + File.separator
                + "sys_template_style" + File.separator + "__sys_default_site_template__"
                + File.separator + "template";

            FileUtil.moveFolder( templateFullPath, defaultFullPath );
            
            ClusterService.exeClusterMasterCMD( "cluster/moveFolder.do", "ofp="
                + StringUtil.replaceString( StringUtil.replaceString( templateFullPath,
                    basePath, "" ), File.separator, "/" )
                + "&nfp="
                + StringUtil.replaceString( StringUtil.replaceString( defaultFullPath,
                    basePath, "" ), File.separator, "/" ), Constant.COMMON.POST );

            String newFullPath = basePath + site.getSiteFlag() + File.separator
                + "sys_template_style" + File.separator + useTs.get( "styleFlag" ) + File.separator
                + "template";

            FileUtil.moveFolder( newFullPath, templateFullPath );
            
            ClusterService.exeClusterMasterCMD( "cluster/moveFolder.do", "ofp="
                + StringUtil.replaceString( StringUtil.replaceString( newFullPath,
                    basePath, "" ), File.separator, "/" )
                + "&nfp="
                + StringUtil.replaceString( StringUtil.replaceString( templateFullPath,
                    basePath, "" ), File.separator, "/" ), Constant.COMMON.POST );
        }
    }

    public Object getTemplateStyleForTag( String tsId )
    {
        if( StringUtil.isStringNotNull( tsId ) )
        {
            return this.templetDao.querySingleTemplateStyle( Long.valueOf( StringUtil.getLongValue(
                tsId, -1L ) ) );
        }
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        return this.templetDao.queryTemplateStyle( site.getSiteId() );
    }
}
