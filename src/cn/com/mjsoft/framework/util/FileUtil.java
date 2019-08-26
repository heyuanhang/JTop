package cn.com.mjsoft.framework.util;

import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.util.jdk14zip.ZipInputStream;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

@SuppressWarnings( "unchecked" )
public class FileUtil
{
    public static final String DEFAULT_CHARSET = "GBK";

    static final int BUFFER = 8192;

    private static UUIDHexGenerator gen = new UUIDHexGenerator();

    private static Logger log = Logger.getLogger( FileUtil.class );

    // Openoffice连接,目前保持一个唯一连接
    private static OpenOfficeConnection con = null;

    public static final String PDFTOSWF_PATH = "core" + File.separator + "extools" + File.separator
        + "flash"
        // + File.separator + "ffmpeg"
        + File.separator + "pdf2swf_new.exe"; // pdf2swf_new.exe的目录

    public static void newFolder( String folderPath )
    {
        try
        {
            File myFilePath = new File( folderPath );
            if( !myFilePath.exists() )
            {
                myFilePath.mkdirs();
            }
        }
        catch ( Exception e )
        {
            System.out.println( "新建目录操作出错" );
            e.printStackTrace();
        }
    }

    public static void newFile( String filePathAndName, String fileContent )
    {
        try
        {
            File myFilePath = new File( filePathAndName );
            if( !myFilePath.exists() )
            {
                myFilePath.createNewFile();
            }
            FileWriter resultFile = new FileWriter( myFilePath );
            PrintWriter myFile = new PrintWriter( resultFile );
            String strContent = fileContent;
            myFile.println( strContent );
            resultFile.close();
        }
        catch ( Exception e )
        {
            System.err.println( "新建目录操作出错" );
            e.printStackTrace();
        }
    }

    public static boolean delFile( String filePathAndName )
    {
        log.info( "将删除文件：" + filePathAndName );
        try
        {
            String filePath = filePathAndName;
            File myDelFile = new File( filePath );
            if( myDelFile.isDirectory() )
            {
                log.error( "试图删除一个目录! target:" + filePathAndName );
                return false;
            }
            myDelFile.delete();
            if( myDelFile.exists() )
            {
                myDelFile.deleteOnExit();
            }
            return !myDelFile.exists();
        }
        catch ( Exception e )
        {
            log.error( e );
            e.printStackTrace();
        }
        return false;
    }

    public static void delFolder( String folderPath )
    {
        try
        {
            delAllFile( folderPath, true );
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File( filePath );
            myFilePath.delete();
        }
        catch ( Exception e )
        {
            log.error( "删除文件夹操作出错" );
            e.printStackTrace();
        }
    }

    public static void delAllFile( String path, boolean deleteRoot )
    {
        log.info( "将删除的文件系统path:" + path );

        File file = new File( path );
        if( !file.exists() )
        {
            return;
        }
        if( !file.isDirectory() )
        {
            file.delete();
            log.info( "作为单文件删除" );
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for ( int i = 0; i < tempList.length; i++ )
        {
            if( path.endsWith( File.separator ) )
            {
                temp = new File( path + tempList[i] );
            }
            else
            {
                temp = new File( path + File.separator + tempList[i] );
            }
            if( temp.isFile() )
            {
                temp.delete();
            }
            if( temp.isDirectory() )
            {
                delAllFile( path + File.separator + tempList[i], true );
                delFolder( path + File.separator + tempList[i] );
            }
        }
        if( deleteRoot )
        {
            file.delete();
        }
    }

    public static void copyFile( String oldPath, String newPath )
    {
        log.info( "copyFile() oldFile:" + oldPath + " ,newPath:" + newPath );

        File newFilePath = null;

        FileInputStream fileIn = null;
        FileOutputStream fileOut = null;
        BufferedInputStream bufIn = null;
        BufferedOutputStream bufOut = null;
        try
        {
            File oldfile = new File( oldPath );
            if( oldfile.exists() )
            {
                newFilePath = new File( newPath );
                newFilePath.createNewFile();

                fileIn = new FileInputStream( oldPath );
                fileOut = new FileOutputStream( newPath );

                bufIn = new BufferedInputStream( fileIn );
                bufOut = new BufferedOutputStream( fileOut );

                writeFile( bufIn, bufOut );
            }
        }
        catch ( Exception e )
        {
            log.error( "复制单个文件操作出错" );
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if( fileIn != null )
                {
                    fileIn.close();
                }
                if( fileOut != null )
                {
                    fileOut.close();
                }
                if( bufIn != null )
                {
                    bufIn.close();
                }
                if( bufOut != null )
                {
                    bufOut.close();
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    public static boolean move( String srcFile, String destPath )
    {
        File file = new File( srcFile );

        File dir = new File( destPath );

        boolean success = file.renameTo( new File( dir, file.getName() ) );

        return success;
    }

    public static void copyFolder( String oldPath, String newPath )
    {
        try
        {
            new File( newPath ).mkdirs();

            File a = new File( oldPath );
            String[] file = a.list();
            if( file != null )
            {
                File temp = null;
                for ( int i = 0; i < file.length; i++ )
                {
                    if( oldPath.endsWith( File.separator ) )
                    {
                        temp = new File( oldPath + file[i] );
                    }
                    else
                    {
                        temp = new File( oldPath + File.separator + file[i] );
                    }
                    if( temp.isFile() )
                    {
                        FileInputStream input = new FileInputStream( temp );
                        FileOutputStream output = new FileOutputStream( newPath + File.separator
                            + temp.getName().toString() );

                        BufferedInputStream bufIn = new BufferedInputStream( input );
                        BufferedOutputStream bufOut = new BufferedOutputStream( output );

                        writeFile( bufIn, bufOut );

                        output.flush();
                        output.close();
                        input.close();
                        bufIn.close();
                        bufOut.close();
                    }
                    if( temp.isDirectory() )
                    {
                        copyFolder( oldPath + File.separator + file[i], newPath + File.separator
                            + file[i] );
                    }
                }
            }
        }
        catch ( Exception e )
        {
            System.out.println( "复制整个文件夹内容操作出错" );
            e.printStackTrace();
        }
    }

    public static void copyOnlyFolder( String oldPath, String newPath, String excludedStr )
    {
        try
        {
            if( StringUtil.isStringNotNull( excludedStr ) )
            {
                if( newPath.indexOf( excludedStr ) == -1 )
                {
                    new File( newPath ).mkdirs();
                }
            }
            else
            {
                new File( newPath ).mkdirs();
            }
            File a = new File( oldPath );
            String[] file = a.list();
            if( file != null )
            {
                File temp = null;
                for ( int i = 0; i < file.length; i++ )
                {
                    if( oldPath.endsWith( File.separator ) )
                    {
                        temp = new File( oldPath + file[i] );
                    }
                    else
                    {
                        temp = new File( oldPath + File.separator + file[i] );
                    }
                    if( temp.isDirectory() )
                    {
                        copyOnlyFolder( oldPath + File.separator + file[i], newPath
                            + File.separator + file[i], excludedStr );
                    }
                }
            }
        }
        catch ( Exception e )
        {
            System.out.println( "复制文件夹操作出错" );
            e.printStackTrace();
        }
    }

    public static void moveCopyFile( String oldPath, String newPath )
    {
        copyFile( oldPath, newPath );
        delFile( oldPath );
    }

    public static void moveFolder( String oldPath, String newPath )
    {
        if( ( !oldPath.equalsIgnoreCase( newPath ) ) && ( !newPath.startsWith( oldPath ) ) )
        {
            copyFolder( oldPath, newPath );
            delFolder( oldPath );
        }
    }

    public static File[] getAllFile( String fullPath, FileFilter filter )
    {
        File[] fileList = ( File[] ) null;
        if( StringUtil.isStringNull( fullPath ) )
        {
            return null;
        }
        try
        {
            File file = new File( fullPath.trim() );
            if( filter != null )
            {
                fileList = file.listFiles( filter );
            }
            else
            {
                fileList = file.listFiles();
            }
        }
        catch ( Exception e )
        {
            System.err.println( "取当前路径下所有文件出错,fullPath:" + fullPath.trim() );
            e.printStackTrace();
        }
        return fileList;
    }

    public static File[] getAllFile( String fullPath )
    {
        return getAllFile( fullPath, null );
    }

    public static Object[] getTXTFileContent( String fullPath, List rule, String charset )
    {
        log.info( "[getTXTFileContent]{fullPath}:" + fullPath + ", {rule}:" + rule );

        Object[] value = { "", Integer.valueOf( "0" ), charset };
        if( StringUtil.isStringNull( fullPath ) )
        {
            log.info( "路径为空,将返回空字符" );
            return value;
        }
        try
        {
            File file = new File( fullPath.trim() );
            if( !file.exists() )
            {
                log.info( "指定的文件不存在" );
                return value;
            }
            if( ( !file.isFile() ) || ( file.isHidden() ) )
            {
                log.info( "指定的文件不是一个文件或被系统隐藏" );
                return value;
            }
            boolean ruleError = true;
            String fileName = file.getName();
            String ruleFlag = "";
            if( ( rule == null ) || ( rule.size() == 0 ) )
            {
                ruleError = false;
            }
            else
            {
                for ( int i = 0; i < rule.size(); i++ )
                {
                    ruleFlag = ( String ) rule.get( i );
                    if( fileName.lastIndexOf( ruleFlag ) + ruleFlag.length() == fileName.length() )
                    {
                        ruleError = false;
                        break;
                    }
                }
            }
            if( ruleError )
            {
                log.info( "指定的文件不是一个合法文件,fileName:" + fileName );
                return value;
            }
            if( StringUtil.isStringNull( charset ) )
            {
                String currentCode = getTXTFileCode( file );

                log.info( "当前处理文本编码为:" + currentCode + ", 文件名:" + file.getName() );
                charset = "UTF-8".equalsIgnoreCase( currentCode ) ? "UTF-8" : "GBK";
            }
            Object[] tempInfo = readTXTFileContent( file, charset );

            return new Object[] { tempInfo[0], tempInfo[1], charset };
        }
        catch ( Exception e )
        {
            System.out.println( "取当前路径下所有文件出错,fullPath:" + fullPath.trim() );
            e.printStackTrace();
        }
        return value;
    }

    public static void writeTXTFileContent( String content, String targetFullPath, String charset )
    {
        BufferedWriter bw = null;
        FileOutputStream fs = null;
        OutputStreamWriter ow = null;
        try
        {
            fs = new FileOutputStream( new File( targetFullPath ) );
            ow = new OutputStreamWriter( fs, charset );
            bw = new BufferedWriter( ow );
            bw.write( content );
            bw.flush();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if( fs != null )
                {
                    fs.close();
                }
                if( ow != null )
                {
                    ow.close();
                }
                if( bw != null )
                {
                    bw.close();
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    public static Object[] readTXTFileContent( File file, String charset )
    {
        return readTXTFileContent( file, charset, null, null );
    }

    public static Object[] readTXTFileContent( File file, String charset, Behavior behavior,
        Object[] bParam )
    {
        Object[] value = { "", Integer.valueOf( "0" ) };

        BufferedReader reader = null;
        InputStreamReader streamReader = null;

        boolean behaviorMode = false;
        if( behavior != null )
        {
            behaviorMode = true;
        }
        int lineCount = 0;
        try
        {
            streamReader = new InputStreamReader( new FileInputStream( file ), charset );
            reader = new BufferedReader( streamReader );

            String line = reader.readLine();
            StringBuffer buf = new StringBuffer();

            String tempStr = "";
            while ( line != null )
            {
                lineCount++;

                tempStr = line + "\n";
                if( behaviorMode )
                {
                    if( ( ( Boolean ) behavior.operation( tempStr, bParam ) ).booleanValue() )
                    {
                        buf = new StringBuffer( "" );
                        break;
                    }
                }
                buf.append( tempStr );
                line = reader.readLine();
            }
            value[0] = buf.toString();
            value[1] = Integer.valueOf( lineCount );

            return value;
        }
        catch ( Exception e )
        {
            log.error( "读取字符出现错误,指定文件为:" + file.getPath() );
            e.printStackTrace();
        }
        finally
        {
            if( reader != null )
            {
                try
                {
                    reader.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
            if( streamReader != null )
            {
                try
                {
                    streamReader.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
        }
        if( reader != null )
        {
            try
            {
                reader.close();
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
        if( streamReader != null )
        {
            try
            {
                streamReader.close();
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
        return value;
    }

    public static String getTXTFileCode( File txtFile )
    {
        String code = "";
        if( ( txtFile == null ) || ( !txtFile.exists() ) )
        {
            return code;
        }
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();

        detector.add( new ParsingDetector( false ) );
        detector.add( JChardetFacade.getInstance() );

        Charset charset = null;
        try
        {
            charset = detector.detectCodepage( txtFile.toURL() );
        }
        catch ( Exception e )
        {
            log.error( "获取文本文件编码出现错误,File:" + txtFile.getPath() );
            e.printStackTrace();
        }
        if( charset != null )
        {
            code = charset.name();
        }
        return code;
    }

    public static void writeFile( InputStream input, OutputStream out )
    {
        try
        {
            byte[] buf = new byte[1024];

            int length = input.read( buf );
            while ( length != -1 )
            {
                out.write( buf, 0, length );

                out.flush();
                length = input.read( buf );
            }
        }
        catch ( Exception e )
        {
            log.error( "写入文件出错" );
            log.error( e );
        }
    }

    public static Map unZipFile( String zipFile, String targetRootPath, boolean randomName )
    {
        Map resultMap = new TreeMap();
        FileInputStream zipfileInput = null;
        ZipInputStream zipInput = null;

        BufferedInputStream bufIn = null;
        BufferedOutputStream bufOut = null;
        FileOutputStream fileOut = null;
        try
        {
            zipfileInput = new FileInputStream( zipFile );

            zipInput = new ZipInputStream( zipfileInput );

            cn.com.mjsoft.framework.util.jdk14zip.ZipEntry entry = zipInput.getNextEntry( "GBK" );

            String targetName = "";

            bufIn = new BufferedInputStream( zipInput );
            while ( entry != null )
            {
                targetName = entry.getName();
                File targetFile = null;
                log.info( "[ZIP] name:" + targetName );
                if( entry.isDirectory() )
                {
                    targetFile = new File( targetRootPath + targetName );
                    targetFile.mkdirs();
                }
                else
                {
                    if( targetName.indexOf( "Thumbs.db" ) == -1 )
                    {
                        if( randomName )
                        {
                            String tail = StringUtil.subString( targetName, targetName
                                .lastIndexOf( "." ), targetName.length() );

                            targetFile = new File( targetRootPath + gen.generate() + tail );
                        }
                        else
                        {
                            targetFile = new File( targetRootPath + targetName );
                        }
                        targetFile.getParentFile().mkdirs();

                        targetFile.createNewFile();

                        fileOut = new FileOutputStream( targetFile );

                        bufOut = new BufferedOutputStream( fileOut );

                        writeFile( bufIn, bufOut );

                        resultMap.put(

                        StringUtil.subString( targetName, targetName.lastIndexOf( "/" ) + 1,
                            targetName.length() ), targetFile.getPath() );
                    }
                    bufOut.flush();
                    fileOut.close();
                    bufOut.close();
                }
                zipInput.closeEntry();

                entry = zipInput.getNextEntry( "GBK" );
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            log.error( "解压ZIP文件出错,zip file:" + zipFile + e );
            throw new FrameworkException( "解压ZIP文件出错,zip file:" + zipFile );
        }
        finally
        {
            try
            {
                if( zipfileInput != null )
                {
                    zipfileInput.close();
                }
                if( zipInput != null )
                {
                    zipInput.close();
                }
                if( bufIn != null )
                {
                    bufIn.close();
                }
            }
            catch ( IOException e )
            {
                log.error( "关闭流出错" );
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    public static void unZip( String unZipfileName )
    {
        byte[] buf = new byte[512];

        int readedBytes = -1;
        try
        {
            ZipFile zipFile = new ZipFile( unZipfileName );
            for ( Enumeration entries = zipFile.getEntries(); entries.hasMoreElements(); )
            {
                org.apache.tools.zip.ZipEntry entry = ( org.apache.tools.zip.ZipEntry ) entries
                    .nextElement();
                File file = new File( entry.getName() );
                if( entry.isDirectory() )
                {
                    file.mkdirs();
                }
                else
                {
                    File parent = file.getParentFile();
                    if( !parent.exists() )
                    {
                        parent.mkdirs();
                    }
                    InputStream inputStream = zipFile.getInputStream( entry );

                    FileOutputStream fileOut = new FileOutputStream( file );

                    readedBytes = inputStream.read( buf );
                    while ( readedBytes > 0 )
                    {
                        fileOut.write( buf, 0, readedBytes );
                        readedBytes = inputStream.read( buf );
                    }
                    fileOut.close();

                    inputStream.close();
                }
            }
            zipFile.close();
        }
        catch ( IOException ioe )
        {
            ioe.printStackTrace();
        }
    }

    public static void zip( String zipFilePath, String srcPathName )
    {
        File zipFile = new File( zipFilePath );

        File file = new File( srcPathName );
        if( !file.exists() )
        {
            throw new RuntimeException( srcPathName + "不存在！" );
        }
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream( zipFile );
            CheckedOutputStream cos = new CheckedOutputStream( fileOutputStream, new CRC32() );
            ZipOutputStream out = new ZipOutputStream( cos );
            String basedir = "";
            zip( file, out, basedir );
            out.close();
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }

    public static void zip( String zipFilePath, List fileList )
    {
        File zipFile = new File( zipFilePath );
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream( zipFile );
            CheckedOutputStream cos = new CheckedOutputStream( fileOutputStream, new CRC32() );
            ZipOutputStream out = new ZipOutputStream( cos );
            String basedir = "";
            zip( fileList, out, basedir );
            out.close();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public static void zip( File file, ZipOutputStream out, String basedir )
    {
        if( file.isDirectory() )
        {
            zipDirectory( file, out, basedir );
        }
        else
        {
            zipFile( file, out, basedir );
        }
    }

    public static void zip( List fileList, ZipOutputStream out, String basedir )
    {
        zipSomeFile( fileList, out, basedir );
    }

    public static void zipDirectory( File dir, ZipOutputStream out, String basedir )
    {
        if( !dir.exists() )
        {
            return;
        }
        File[] files = dir.listFiles();
        for ( int i = 0; i < files.length; i++ )
        {
            zip( files[i], out, basedir + dir.getName() + "/" );
        }
    }

    public static void zipSomeFile( List fileList, ZipOutputStream out, String basedir )
    {
        File file = null;
        for ( int i = 0; i < fileList.size(); i++ )
        {
            try
            {
                file = ( File ) fileList.get( i );

                BufferedInputStream bis = new BufferedInputStream( new FileInputStream( file ) );
                org.apache.tools.zip.ZipEntry entry = new org.apache.tools.zip.ZipEntry( basedir
                    + file.getName() );
                out.putNextEntry( entry );

                byte[] data = new byte[8192];
                int count;
                while ( ( count = bis.read( data, 0, 8192 ) ) != -1 )
                {

                    out.write( data, 0, count );
                }
                bis.close();
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
        }
    }

    public static void zipFile( File file, ZipOutputStream out, String basedir )
    {
        if( !file.exists() )
        {
            return;
        }
        try
        {
            BufferedInputStream bis = new BufferedInputStream( new FileInputStream( file ) );
            org.apache.tools.zip.ZipEntry entry = new org.apache.tools.zip.ZipEntry( basedir
                + file.getName() );
            out.putNextEntry( entry );

            byte[] data = new byte[8192];
            int count;
            while ( ( count = bis.read( data, 0, 8192 ) ) != -1 )
            {

                out.write( data, 0, count );
            }
            bis.close();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public static void refreshFileList( String strPath, List resultFileList )
    {
        File dir = new File( strPath );
        File[] files = dir.listFiles();
        if( ( files == null ) || ( resultFileList == null ) )
        {
            return;
        }
        for ( int i = 0; i < files.length; i++ )
        {
            if( files[i].isDirectory() )
            {
                refreshFileList( files[i].getAbsolutePath(), resultFileList );
            }
            else
            {
                resultFileList.add( files[i] );
            }
        }
    }

    public static void createXML( String fileName )
    {
        Document doc = DocumentHelper.createDocument();

        Element root = doc.addElement( "book" );

        root.addAttribute( "name", "我的图书" );

        Element childTmp = root.addElement( "price" );

        childTmp.setText( "21.22" );

        Element writer = root.addElement( "author" );

        writer.setText( "李四" );

        writer.addAttribute( "ID", "001" );

        FileOutputStream out = null;
        try
        {
            out = new FileOutputStream( fileName );
            XMLWriter xmlWriter = new XMLWriter( out );

            xmlWriter.write( doc );

            xmlWriter.close();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            if( out != null )
            {
                try
                {
                    out.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static File convertOfficeToHtmlFile( File targetFile, File htmlFile )
    {
        if( !targetFile.exists() )
        {
            return null;
        }
        try
        {
            if( con == null )
            {
                con = new SocketOpenOfficeConnection( 8100 );
                con.connect();
            }
            DocumentConverter converter = new OpenOfficeDocumentConverter( con );

            converter.convert( targetFile, htmlFile );
        }
        catch ( ConnectException ec )
        {
            log.error( "无法获取当前服务器的openoffice连接,请检查系统变量!" );
            try
            {
                con.disconnect();
            }
            catch ( Exception localException1 )
            {
            }
            con = null;
            throw new FrameworkException( "无法获取当前服务器的openoffice连接,请检查系统变量!" );
        }
        catch ( Exception ex )
        {
            log.error( "转换Office文档失败,目标Office文件:" + targetFile.toString() );
            if( con != null )
            {
                try
                {
                    con.disconnect();
                }
                catch ( Exception localException2 )
                {
                }
            }
            con = null;
            throw new FrameworkException( "转换Office文档失败,目标Office文件:" + targetFile.toString() );
        }
        return htmlFile;
    }

    public static String clearWordFormat( String content )
    {
        content = content.replaceAll( "(<P)([^>]*)(>.*?)(<\\/P>)", "<p$3</p>" );

        content = content
            .replaceAll(
                "<[/]?(font|FONT|span|SPAN|xml|XML|del|DEL|ins|INS|meta|META|[ovwxpOVWXP]:\\w+)[^>]*?>",
                "" );

        content = content
            .replaceAll(
                "<([^>]*)(?:lang|LANG|class|CLASS|style|STYLE|size|SIZE|face|FACE|[ovwxpOVWXP]:\\w+)=(?:'[^']*'|\"\"[^\"\"]*\"\"|[^>]+)([^>]*)>",
                "<$1$2>" );

        int styleBegin = content.indexOf( "<STYLE" );
        int styleEnd = content.indexOf( "</STYLE>" ) + 8;
        String style = StringUtil.subString( content, styleBegin, styleEnd );
        content = content.replace( style, "" );
        return content;
    }

    public static String changeFileSizeToStr( long length )
    {
        long kbSize = Double.valueOf( MathUtil.div( length, 1024.0D, 0 ) ).longValue();

        String size = "";
        if( kbSize > 1024L )
        {
            size = MathUtil.div( length, 1048576.0D, 2 ) + " MB";
        }
        else
        {
            size = MathUtil.div( length, 1024.0D, 2 ) + " KB";
        }
        return size;
    }

    public static void convertMSDocToPdf( String msDocPath, String pdfPath )
    {
        File docFile = new File( msDocPath );
        if( !docFile.exists() )
        {
            log.error( "FileUtil: convertMSDocToPdf() 文件不存在：" + msDocPath );

            return;
        }
        File pdfFile = new File( pdfPath );
        try
        {
            if( con == null )
            {
                con = new SocketOpenOfficeConnection( 8100 );
                con.connect();
            }
            DocumentConverter converter = new OpenOfficeDocumentConverter( con );

            converter.convert( docFile, pdfFile );
        }
        catch ( ConnectException ec )
        {
            log.error( "无法获取当前服务器的openoffice连接,请检查JtopCMS系统变量!" );
            try
            {
                con.disconnect();
            }
            catch ( Exception localException1 )
            {
            }
            con = null;
            log.error( "无法获取当前服务器的openoffice连接,请检查JtopCMS系统变量!" );
        }
        catch ( Exception ex )
        {
            log.error( "转换Office文档为Pdf失败,目标Office文件:" + msDocPath );
            if( con != null )
            {
                try
                {
                    con.disconnect();
                }
                catch ( Exception localException2 )
                {
                }
            }
            con = null;
        }
    }

    public static boolean convertPdfToSwf( String pdfPath, String swfPath )
    {
        List commend = new ArrayList();

        String osName = System.getProperty( "os.name" ).toLowerCase();
        if( osName.indexOf( "win" ) != -1 )
        {
            commend.add( SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath()
                + PDFTOSWF_PATH );
        }
        else
        {
            commend.add( "pdf2swf" );
        }
        commend.add( pdfPath );
        commend.add( "-o" );
        commend.add( swfPath );
        commend.add( "-T" );
        commend.add( "9" );

        String[] strs = ( String[] ) commend.toArray( new String[0] );

        Process process = null;
        try
        {
            Runtime runtime = Runtime.getRuntime();

            process = runtime.exec( strs );

            System.out.print( loadStream( process.getInputStream() ) );
            System.err.print( loadStream( process.getErrorStream() ) );
            System.out.print( loadStream( process.getInputStream() ) );

            return true;
        }
        catch ( Exception e )
        {
            log.error( e );
            if( process != null )
            {
                try
                {
                    Thread.sleep( 1000L );
                }
                catch ( InterruptedException et )
                {
                    et.printStackTrace();
                }
                process.destroy();
            }
        }
        return false;
    }

    private static String loadStream( InputStream in ) throws IOException
    {
        int ptr = 0;
        BufferedReader reader = new BufferedReader( new InputStreamReader( in ) );
        StringBuilder buffer = new StringBuilder();
        while ( ( ptr = reader.read() ) != -1 )
        {
            buffer.append( ( char ) ptr );
        }
        return buffer.toString();
    }

    // ////////////////////////////////服务器网络传输文件//////////////////////////////////

    /**
     * 将文件转换成byte[]
     * 
     * @param filePath
     * @return
     */
    public static byte[] getBytes( String filePath )
    {
        byte[] buffer = null;
        try
        {
            File file = new File( filePath );
            FileInputStream fis = new FileInputStream( file );
            ByteArrayOutputStream bos = new ByteArrayOutputStream( 1024 );
            byte[] b = new byte[1024];
            int n;
            while ( ( n = fis.read( b ) ) != -1 )
            {
                bos.write( b, 0, n );
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * POST传送文件
     * 
     * @param url
     * @param fileInfo <relatePath fullPath>
     */
    public static void httpPostFile( String url, Map<String, String> fileInfo )
    {
        if( StringUtil.isStringNull( url ) || fileInfo == null )
        {
            return;
        }

        HttpClient httpclient = new DefaultHttpClient();

        httpclient.getParams().setIntParameter( CoreConnectionPNames.SO_TIMEOUT, 3000 );
        httpclient.getParams().setIntParameter( CoreConnectionPNames.CONNECTION_TIMEOUT, 3000 );

        try
        {

            HttpPost httppost = new HttpPost( url );

            String relatePath = "";

            String fullPath = "";

            Iterator iter = fileInfo.entrySet().iterator();

            Entry<String, String> entry = null;

            MultipartEntity reqEntity = new MultipartEntity();

            int index = 0;

            while ( iter.hasNext() )
            {
                entry = ( Entry<String, String> ) iter.next();

                relatePath = entry.getKey();

                fullPath = entry.getValue();

                String fileName = StringUtil.subString( relatePath,
                    relatePath.lastIndexOf( "." ) + 1, relatePath.length() );

                FileBody bin = new FileBody( new File( fullPath ) );

                reqEntity.addPart( "file_" + index, bin );

                httppost.setHeader( "file_name_" + index, SystemSafeCharUtil.encode( fileName ) );

                httppost
                    .setHeader( "file_relate_" + index, SystemSafeCharUtil.encode( relatePath ) );

                index++;
            }

            httppost.setEntity( reqEntity );

            HttpResponse response = httpclient.execute( httppost );

            int statusCode = response.getStatusLine().getStatusCode();

            if( statusCode == HttpStatus.SC_OK )
            {

                log.info( ".....目标服务器已响应....." );

                HttpEntity resEntity = response.getEntity();

                log.info( EntityUtils.toString( resEntity ) );

                log.info( resEntity.getContent() );

                EntityUtils.consume( resEntity );
            }

        }
        catch ( Exception e )
        {

            e.printStackTrace();
        }
        finally
        {
            try
            {
                httpclient.getConnectionManager().shutdown();

            }
            catch ( Exception ignore )
            {

            }
        }
    }

    /**
     * 接受POST文件
     * 
     * @param request
     * @param rootPath
     */
    public static void receiveData( HttpServletRequest request, String rootPath )
    {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload( factory );

        List<FileItem> items = new ArrayList();
        try
        {
            items = upload.parseRequest( request );

            Iterator<FileItem> it = items.iterator();

            int index = 0;

            while ( it.hasNext() )
            {
                FileItem fItem = ( FileItem ) it.next();

                String name = fItem.getName();

                if( name != null && !( "".equals( name ) ) )
                {
                    // name = name.substring( name.lastIndexOf( File.separator )
                    // + 1 );
                    String relate = SystemSafeCharUtil.decodeFromWeb( request
                        .getHeader( "file_relate_" + index ) );

                    String sepChar = "\\";

                    if( rootPath.indexOf( "/" ) != -1 )// Linux模式
                    {
                        relate = StringUtil.replaceString( relate, "\\", "/", false, false );

                        sepChar = "/";
                    }

                    String filePath = rootPath + relate;

                    log.info( "receiveData() file_path_" + index + " : " + filePath );

                    File testPath = new File( StringUtil.subString( filePath, 0, filePath
                        .lastIndexOf( sepChar ) ) );

                    if( !testPath.exists() )
                    {
                        testPath.mkdirs();
                    }

                    InputStream is = fItem.getInputStream();

                    FileOutputStream fos = new FileOutputStream( filePath );

                    writeFile( is, fos );

                    is.close();
                    fos.close();

                    index++;
                }

            }

            log.info( "[FileUtil]  receiveData 接受文件成功!" );

        }
        catch ( Exception e )
        {
            e.printStackTrace();

            log.error( "[FileUtil]  receiveData 出错!" + e );
        }

    }

    public static FileTypeEM getFileType( File file )
    {

        InputStream is = null;

        byte[] src = new byte[28];
        try
        {
            is = new FileInputStream( file );

            is.read( src, 0, 28 );

            StringBuilder stringBuilder = new StringBuilder( "" );
            if( src == null || src.length <= 0 )
            {
                return null;
            }
            for ( int i = 0; i < src.length; i++ )
            {
                int v = src[i] & 0xFF;
                String hv = Integer.toHexString( v ).toUpperCase();
                if( hv.length() < 2 )
                {
                    stringBuilder.append( 0 );
                }
                stringBuilder.append( hv );
            }
            FileTypeEM[] fileTypes = FileTypeEM.values();
            for ( FileTypeEM fileType : fileTypes )
            {
                if( stringBuilder.toString().startsWith( fileType.getValue() ) )
                {
                    return fileType;
                }
            }

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            if( is != null )
            {
                try
                {
                    is.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public enum FileTypeEM
    {

        /**
         * JPEG
         */
        JPEG( "FFD8FF", "jpg" ),

        /**
         * PNG
         */
        PNG( "89504E47", "png" ),

        /**
         * GIF
         */
        GIF( "47494638", "gif" ),

        /**
         * TIFF
         */
        TIFF( "49492A00" ),

        /**
         * Windows bitmap
         */
        BMP( "424D" ),

        /**
         * CAD
         */
        DWG( "41433130" ),

        /**
         * Adobe photoshop
         */
        PSD( "38425053" ),

        /**
         * Rich Text Format
         */
        RTF( "7B5C727466" ),

        /**
         * XML
         */
        XML( "3C3F786D6C" ),

        /**
         * HTML
         */
        HTML( "68746D6C3E" ),

        /**
         * Outlook Express
         */
        DBX( "CFAD12FEC5FD746F " ),

        /**
         * Outlook
         */
        PST( "2142444E" ),

        /**
         * doc;xls;dot;ppt;xla;ppa;pps;pot;msi;sdw;db
         */
        OLE2( "0xD0CF11E0A1B11AE1" ),

        /**
         * Microsoft Word/Excel
         */
        XLS_DOC( "D0CF11E0" ),

        /**
         * Microsoft Access
         */
        MDB( "5374616E64617264204A" ),

        /**
         * Word Perfect
         */
        WPB( "FF575043" ),

        /**
         * Postscript
         */
        EPS_PS( "252150532D41646F6265" ),

        /**
         * Adobe Acrobat
         */
        PDF( "255044462D312E" ),

        /**
         * Windows Password
         */
        PWL( "E3828596" ),

        /**
         * ZIP Archive
         */
        ZIP( "504B0304" ),

        /**
         * ARAR Archive
         */
        RAR( "52617221" ),

        /**
         * WAVE
         */
        WAV( "57415645" ),

        /**
         * AVI
         */
        AVI( "41564920" ),

        /**
         * Real Audio
         */
        RAM( "2E7261FD" ),

        /**
         * Real Media
         */
        RM( "2E524D46" ),

        /**
         * Quicktime
         */
        MOV( "6D6F6F76" ),

        /**
         * Windows Media
         */
        ASF( "3026B2758E66CF11" ),

        /**
         * MIDI
         */
        MID( "4D546864" );

        private String value = "";
        private String ext = "";

        FileTypeEM( String value )
        {
            this.value = value;
        }

        FileTypeEM( String value, String ext )
        {
            this( value );
            this.ext = ext;
        }

        public String getExt()
        {
            return ext;
        }

        public String getValue()
        {
            return value;
        }
    }
}
