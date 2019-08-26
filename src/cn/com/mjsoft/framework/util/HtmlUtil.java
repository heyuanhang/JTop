package cn.com.mjsoft.framework.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

/**
 * HTML类型文本处理
 * 
 * @author mj-soft
 * 
 */
public class HtmlUtil
{
    private static Logger log = Logger.getLogger( HtmlUtil.class );

    private static Pattern p;

    private static String M_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31";

    private static String IE_AGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)";

    private static String CHROME_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1";

    /**
     * @param args
     * @throws IOException
     */
    public static void main( String[] args ) throws IOException
    {
    }

    public static Document parseConnect( String url )
    {
        Document doc = null;

        try
        {
            doc = Jsoup.parse( new URL( url ).openStream(), "UTF-8", url );
        }
        catch ( Exception e )
        {

        }

        return doc;
    }

    public static Document parseConnect( String url, String code )
    {
        Document doc = null;

        try
        {
            doc = Jsoup.parse( new URL( url ).openStream(), code, url );
        }
        catch ( Exception e )
        {

            e.printStackTrace();
        }

        return doc;
    }

    /**
     * 和指定URL建立连接,返回Html根Document
     * 
     * @param url
     * @return
     */
    public static Document connectUrlnoCode( String url )
    {
        return connectUrl( url, 5000, null );
    }

    /**
     * 和指定URL建立连接,返回Html根Document
     * 
     * @param url
     * @return
     */
    public static Document connectUrl( String url, String code )
    {
        return connectUrl( url, 5000, code );
    }

    /**
     * 和指定URL建立连接,返回Html根Document
     * 
     * @param url
     * @param timeout
     * @return
     */
    public static Document connectUrl( String url, int timeout, String code )
    {
        Document d = null;

        if( StringUtil.isStringNull( url ) )
        {
            return d;
        }

        try
        {
            if( StringUtil.isStringNotNull( code ) )
            {
                d = parseConnect( url, code );
            }
            else
            {

                d = parseConnect( url );

                if( d == null )
                {
                    d = Jsoup.connect( url ).userAgent( IE_AGENT ).timeout( timeout ).get();
                }

                code = "utf-8";

                Elements eles = d.select( "meta[http-equiv=Content-Type]" );
                Iterator<Element> itor = eles.iterator();
                while ( itor.hasNext() )
                {
                    code = matchCharset( itor.next().toString() );
                    break;
                }

                if( code.equals( "gb2312" ) || code.equals( "gbk" ) )
                {
                    d = Jsoup.parse( new URL( url ).openStream(), "GBK", url );
                }
            }
        }
        catch ( Exception e )
        {
            // e.printStackTrace();
            log.error( "当前URL无法建立连接,URL:" + url );
            log.error( e );
            try
            {
                d = Jsoup.connect( url ).userAgent( CHROME_AGENT ).timeout( timeout ).get();
            }
            catch ( Exception e1 )
            {
                log.error( "再次连接仍然失败,URL:" + url );
                log.error( e1 );
            }

        }

        return d;
    }

    public static String matchCharset( String content )
    {
        String chs = "gb2312";
        p = Pattern.compile( "(?<=charset=)(.+)(?=\")" );
        Matcher m = p.matcher( content );
        if( m.find() )
            return m.group();
        return chs;
    }

    /**
     * 取得指定Node下所有按规则获取的所有子Node.
     * 
     * @param coList 收集到的Node
     * @param rootNode 根入口Node
     * @param textNodeMode 是否获取文本Node
     * @param needElementTagName 所需Node类型列表
     * @param clearElementTagName 过滤节点类型列表
     */
    public static void getAllNeedNodes( List coList, Node rootNode, boolean textNodeMode,
        Set needElementTagName, Set clearElementTagName )
    {
        // log.info( "[HtmlUtil] coList:" + coList
        // //+ " ,rootNode:" + rootNode
        // + " ,textNodeMode:" + textNodeMode + " ,needElementTagName:"
        // + needElementTagName + " ,clearElementTagName:"
        // + clearElementTagName );

        if( coList == null || rootNode == null )
        {
            return;
        }

        Iterator nodeIter = rootNode.childNodes().iterator();
        Node targetTestNode = null;

        while ( nodeIter.hasNext() )
        {
            targetTestNode = ( Node ) nodeIter.next();

            if( clearElementTagName != null
                && clearElementTagName.contains( targetTestNode.nodeName().toLowerCase() ) )
            {
                continue;
            }

            if( ( needElementTagName != null && needElementTagName.contains( targetTestNode
                .nodeName().toLowerCase() ) )
                || ( textNodeMode && targetTestNode instanceof TextNode ) )
            {
                // 不在过滤Node类型中且在所需Node类型中,或者当前需要文本类型节点
                coList.add( targetTestNode );

            }

            getAllNeedNodes( coList, targetTestNode, textNodeMode, needElementTagName,
                clearElementTagName );
        }
    }

    public boolean isTitle( Element e )
    {
        String nodeName = e.nodeName();

        String className = e.attr( "class" );

        if( nodeName.toLowerCase().equals( "<h1>" ) || nodeName.toLowerCase().equals( "<h3>" )
            || nodeName.toLowerCase().equals( "<b>" ) || nodeName.toLowerCase().equals( "<strong>" ) )
        {
            if( className.indexOf( "title" ) != -1 || className.indexOf( "head" ) != -1 )
            {

            }
        }

        return true;
    }

    /**
     * 从指定的图片web地址下载图片,若下载成功，返回本地路径，否则返回空.
     * 
     * @param targetImgUrl 图片URL
     * @param systemSaveImgpath 存储路径
     * @return
     */
    public static File downloadImageByUrl( String targetImgUrl, String systemSaveImgpath )
    {
        return ( File ) downloadFileByUrl( targetImgUrl, systemSaveImgpath, false )[0];
    }

    /**
     * 从指定的图片web地址下载文件,若下载成功，返回本地路径，否则返回空.
     * 
     * @param targetImgUrl 图片URL
     * @param systemSaveImgpath 存储路径
     * @return
     */
    public static Object[] downloadFileByUrl( String targetImgUrl, String systemSaveImgpath,
        boolean oldNameMode )
    {
        File endFile = null;

        String name = "";

        String type = "";

        try
        {
            String targetFileNameEnd = null;

            if( oldNameMode )
            {
                if( StringUtil.isStringNotNull( targetImgUrl ) )
                {
                    if( targetImgUrl.endsWith( ".html" ) )
                    {
                        return new Object[] { endFile, name, type };
                    }

                    targetFileNameEnd = StringUtil.subString( targetImgUrl, targetImgUrl
                        .lastIndexOf( "/" ), targetImgUrl.length() );
                }

                if( StringUtil.isStringNull( targetFileNameEnd ) )
                {
                    return new Object[] { endFile, name, type };
                }
            }
            else
            {
                if( StringUtil.isStringNotNull( targetImgUrl ) )
                {
                    targetFileNameEnd = StringUtil.subString( targetImgUrl, targetImgUrl
                        .lastIndexOf( "." ), targetImgUrl.length() );
                }

                if( StringUtil.isStringNull( targetFileNameEnd ) )
                {
                    return new Object[] { endFile, name, type };
                }

                String fileType = StringUtil.subString( targetFileNameEnd,
                    targetFileNameEnd.lastIndexOf( "." ) + 1, targetFileNameEnd.length() )
                    .toLowerCase();

                if( fileType.length() > 4 )
                {
                    targetFileNameEnd = StringUtil.subString( targetFileNameEnd, 0,
                        targetFileNameEnd.lastIndexOf( "." ) + 5 ).toLowerCase();
                }

                targetFileNameEnd = StringUtil.getUUIDString() + targetFileNameEnd;
            }

            if( targetFileNameEnd == null || targetFileNameEnd.length() < 3 )
            {
                targetFileNameEnd = StringUtil.getUUIDString();
            }

            endFile = new File( systemSaveImgpath + targetFileNameEnd );

            File dir = new File( systemSaveImgpath );

            if( !dir.exists() )
            {
                dir.mkdirs();
            }

            // log.info( "最终图片路径：" + endFile.toString() );

            FileOutputStream fo = null;

            try
            {

                Response resultImageResponse = Jsoup.connect( targetImgUrl ).userAgent( IE_AGENT )
                    .maxBodySize( 100000000 ).ignoreContentType( true ).execute();

                fo = new FileOutputStream( endFile );

                fo.write( resultImageResponse.bodyAsBytes() );

                Map ss = resultImageResponse.headers();

                String cd = ( String ) ss.get( "Content-Disposition" );

                if( cd != null && cd.indexOf( "filename=" ) != -1 )
                {

                    cd = new String( cd.getBytes( "iso_8859_1" ), "gbk" );

                    name = cd.substring( cd.indexOf( "filename=" ) + 10, cd.length() - 1 ).trim();

                    type = name.substring( name.indexOf( "." ) + 1, name.length() );

                    // if( targetFileNameEnd.indexOf( "." ) == -1 )
                    {
                        File file = new File( systemSaveImgpath + targetFileNameEnd );
                        System.out.println( file.getPath() );

                        FileUtil.copyFile( systemSaveImgpath + targetFileNameEnd, systemSaveImgpath
                            + targetFileNameEnd + "." + type );
                        // file.renameTo( newFile );

                        endFile = new File( systemSaveImgpath + targetFileNameEnd + "." + type );
                    }

                }

            }
            finally
            {
                log.info( "尝试下载文件:" + targetImgUrl );

                if( fo != null )
                {
                    fo.close();
                }

            }

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        if( endFile == null )
        {
            return null;
        }

        return new Object[] { endFile, name, type };
    }

}
