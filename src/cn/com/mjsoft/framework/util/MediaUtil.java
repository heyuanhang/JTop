package cn.com.mjsoft.framework.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import cn.com.mjsoft.cms.content.bean.VideoMetadataBean;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;

/**
 * 多媒体处理工具类
 * 
 * @author mjsoft
 * 
 */
@SuppressWarnings( "unchecked" )
public class MediaUtil
{
    private static Logger log = Logger.getLogger( MediaUtil.class );

    public static final String FFMPEG_PATH = "core" + File.separator
        + "extools" + File.separator + "video"
        // + File.separator + "ffmpeg"
        + File.separator + "ffmpeg.exe"; // ffmpeg.exe的目录

    public static boolean processVideoImg( String videoPath,
        String imgOutputPath, int sec, String spec )
    {
        log.info( "[MediaUtil - processVideoImg()] " + "videoPath:" + videoPath
            + " ,imgOutputPath:" + imgOutputPath + " ,sec:" + sec + " ,spec:"
            + spec );

        List commend = new ArrayList();

        String osName = System.getProperty( "os.name" ).toLowerCase();
        if( osName.indexOf( "win" ) != -1 )
        {
            commend.add( SystemConfiguration.getInstance().getSystemConfig()
                .getSystemRealPath()
                + FFMPEG_PATH );
        }
        else
        {
            commend.add( "ffmpeg" );
        }
        commend.add( "-i" );
        commend.add( videoPath );
        commend.add( "-y" );
        commend.add( "-f" );
        commend.add( "image2" );
        commend.add( "-ss" );
        commend.add( Integer.valueOf( sec ).toString() );
        commend.add( "-t" );
        commend.add( "0.001" );
        commend.add( "-s" );
        commend.add( spec );
        commend.add( imgOutputPath );

        String[] strs = ( String[] ) commend.toArray( new String[] {} );

        Process process = null;
        try
        {
            Runtime runtime = Runtime.getRuntime();

            process = runtime.exec( strs );

            readInputStream( process, process.getErrorStream(), null, null );

            readInputStream( process, process.getInputStream(), null, null );

            return true;
        }
        catch ( Exception e )
        {
            log.error( e );

            if( process != null )
            {
                try
                {
                    Thread.sleep( 1000 );
                }
                catch ( InterruptedException et )
                {
                    et.printStackTrace();
                }

                process.destroy();
            }
            return false;
        }

    }

    /*
     * 
     */
    public static boolean divideUpVideoMedia( String fullPath,
        String saveFullPath, String startSec, String stopSec, String eventKey,
        Map statusMap )
    {
        File needDeleteFile = new File( saveFullPath );
        needDeleteFile.delete();

        List commend = new ArrayList();

        String osName = System.getProperty( "os.name" ).toLowerCase();
        if( osName.indexOf( "win" ) != -1 )
        {
            commend.add( SystemConfiguration.getInstance().getSystemConfig()
                .getSystemRealPath()
                + FFMPEG_PATH );
        }
        else
        {
            commend.add( "ffmpeg" );
        }

        commend.add( "-ss" );
        commend.add( startSec );

        commend.add( "-i" );
        commend.add( fullPath );

        if( StringUtil.isStringNotNull( stopSec ) )
        {
            commend.add( "-t" );
            commend.add( stopSec );
        }

        if( fullPath.toLowerCase().endsWith( ".flv" ) )
        {
            commend.add( "-vcodec" );
            commend.add( "copy" );

            commend.add( "-acodec" );
            commend.add( "copy" );
        }

        commend.add( saveFullPath );

        String[] strs = ( String[] ) commend.toArray( new String[] {} );

        Process process = null;
        try
        {
            Runtime runtime = Runtime.getRuntime();
            process = runtime.exec( strs );

            readInputStream( process, process.getErrorStream(), eventKey,
                statusMap );

            readInputStream( process, process.getInputStream(), eventKey,
                statusMap );

            return true;
        }
        catch ( Exception e )
        {
            log.error( e );
            if( process != null )
            {
                try
                {
                    Thread.sleep( 1000 );
                }
                catch ( InterruptedException et )
                {
                    et.printStackTrace();
                }

                process.destroy();
            }
            return false;
        }
    }

    /**
     * 将支持的视频转换为指定格式,若失不支持当前视频,则返回状态0,成功为状态1,转换异常为状态-1
     * 
     * @param exeFullPath 视频转换核心处理程序路径
     * @param needDisposeVideoFullPath
     * @param outputFlvFileFullPath
     * @return
     */
    public static int convertVideo( String needDisposeVideoFullPath,
        String outputFlvFileFullPath, int q, boolean h264, String videoSpec,
        String eventKey, Map statusMap )
    {
        log.info( "[MediaUtil - convertVideoToFLV()] "
            + "needDisposeVideoFullPath:" + needDisposeVideoFullPath
            + " ,outputFlvFileFullPath:" + outputFlvFileFullPath
            + " ,videoSpec:" + videoSpec );

        // 首先删除已存在文件
        new File( outputFlvFileFullPath ).delete();

        /**
         * html5 mp4规格
         */
        // 视频编码：AVC（H264）
        // 音频编码：AAC
        // 文件格式：MP4
        // ffmpeg.exe -y -i d:/jt.avi yadif -acodec aac -ar 44100 -ab
        // 128k -ac 2 -vcodec libx264 -vrpe medium -crf 20
        // -async 512 -strict -2 d:/test.mp4
        // centos下需要-vpre medium
        int status = -1;

        List commend = new ArrayList();

        String osName = System.getProperty( "os.name" ).toLowerCase();
        if( osName.indexOf( "win" ) != -1 )
        {
            commend.add( SystemConfiguration.getInstance().getSystemConfig()
                .getSystemRealPath()
                + FFMPEG_PATH );
        }
        else
        {
            commend.add( "ffmpeg" );
        }
        // String s =
        // "D:\\JAVA_SERVER\\apache-tomcat-7.0.61\\webapps\\jtopcms\\core\\extools\\video\\ffmpeg.exe";
        // commend.add( "D:/JAVA_DEV/ffmpeg/ffmpeg.exe" );

        commend.add( "-i" );
        commend.add( needDisposeVideoFullPath );

        if( outputFlvFileFullPath.endsWith( ".mp4" ) )
        {

            if( h264 )
            {
                commend.add( "-acodec" );
                commend.add( "aac" );

                commend.add( "-ar" );
                commend.add( "44100" );

                commend.add( "-ab" );
                commend.add( "128k" );

                commend.add( "-ac" );
                commend.add( "2" );

                commend.add( "-vcodec" );
                commend.add( "h264" );

                commend.add( "-strict" );
                commend.add( "-2" );
            }
            else
            {
                commend.add( "-acodec" );
                commend.add( "mp3" );
            }

        }
        else
        {
            commend.add( "-y" );
            commend.add( "-ab" );
            commend.add( "56" );
            commend.add( "-ar" );
            commend.add( "22050" );
            commend.add( "-b" );
            commend.add( "500" );

            commend.add( "-r" );
            commend.add( "25" );
        }

        commend.add( "-qscale" );// 设置动态码率
        commend.add( q + "" );// 测试发现如果想得到高品质视频此值越小越好

        if( StringUtil.isStringNotNull( videoSpec ) )
        {
            commend.add( "-s" );
            commend.add( videoSpec );
        }

        commend.add( outputFlvFileFullPath );

        String[] strs = ( String[] ) commend.toArray( new String[] {} );

        Process process = null;
        try
        {
            Runtime runtime = Runtime.getRuntime();

            process = runtime.exec( strs );

            readInputStream( process, process.getErrorStream(), eventKey,
                statusMap );
            readInputStream( process, process.getInputStream(), eventKey,
                statusMap );

            // 注意注意!!!!需要在这个方法中处理非正常状态(异常发生)下的Process线程,若时间过长或者出现了异常
            // 一定要关闭ffmpeg线程!!!!若不关闭,CPU将死锁!!!!!

            // System.out.println( process.waitFor() );
            // System.out.println( process.exitValue() );

            status = 1;
        }
        catch ( Exception e )
        {
            log.error( e );
            status = -1;
            if( process != null )
            {
                try
                {
                    Thread.sleep( 1000 );
                }
                catch ( InterruptedException et )
                {
                    et.printStackTrace();
                }

                process.destroy();
            }
        }

        return status;
    }

    public static VideoMetadataBean getMediaMetadata( String mediaFilePath )
    {
        log.info( "[MediaUtil - getMediaMetadata()] " + "mediaFilePath:"
            + mediaFilePath );

        File testFile = new File( mediaFilePath );

        if( !testFile.exists() )
        {
            // 不存在的视频文件不处理
            return null;
        }

        String info = null;
        VideoMetadataBean vmBean = new VideoMetadataBean();

        List commend = new ArrayList();

        String osName = System.getProperty( "os.name" ).toLowerCase();
        if( osName.indexOf( "win" ) != -1 )
        {
            commend.add( SystemConfiguration.getInstance().getSystemConfig()
                .getSystemRealPath()
                + FFMPEG_PATH );
        }
        else
        {
            commend.add( "ffmpeg" );
        }

        commend.add( "-i" );
        commend.add( mediaFilePath );

        try
        {
            // ProcessBuilder builder = new ProcessBuilder( commend );
            // builder.command( commend );
            // builder.redirectErrorStream( true );
            // Process p = builder.start();
            //
            // // 1. start
            // BufferedReader buf = null; // 保存ffmpeg的输出结果流
            // String line = null;
            // // read the standard output
            //
            // buf = new BufferedReader(
            // new InputStreamReader( p.getInputStream() ) );
            //
            // StringBuffer sb = new StringBuffer();
            // while ( ( line = buf.readLine() ) != null )
            // {
            // System.out.println( line );
            // sb.append( line );
            // continue;
            // }
            // int ret = p.waitFor();// 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
            // // 1. end
            // return sb.toString();

            String[] strs = ( String[] ) commend.toArray( new String[] {} );

            Runtime runtime = Runtime.getRuntime();
            Process pro = runtime.exec( strs );

            // readInputStream( pro.getInputStream(), null, null );
            info = readInputStream( pro, pro.getErrorStream(), null, null );

            PatternCompiler compiler = new Perl5Compiler();

            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            String regexVideo = "Video: (.*?), (.*?), (.*?)[,\\s]";
            // String regexAudio = "Audio: (\\w*), (\\d*) Hz";

            Pattern patternDuration = compiler.compile( regexDuration,
                Perl5Compiler.CASE_INSENSITIVE_MASK );
            PatternMatcher matcherDuration = new Perl5Matcher();

            // System.out.println( result+" : "+ patternDuration);

            if( matcherDuration.contains( info, patternDuration ) )
            {
                MatchResult re = matcherDuration.getMatch();

                // System.out.println( "提取出播放时间 ===" + re.group( 1 ) );
                // System.out.println( "开始时间 =====" + re.group( 2 ) );
                // System.out.println( "bitrate 码率 单位 kb==" + re.group( 3 ) );

                String allTime = re.group( 1 );

                vmBean.setDuration( allTime );
                vmBean.setDurationHour( Integer.valueOf( StringUtil
                    .getIntValue( StringUtil.subString( allTime, 0, 2 ), 0 ) ) );
                vmBean.setDurationMinute( Integer.valueOf( StringUtil
                    .getIntValue( StringUtil.subString( allTime, 3, 5 ), 0 ) ) );
                vmBean.setDurationSec( Integer.valueOf( StringUtil.getIntValue(
                    StringUtil.subString( allTime, 6, 8 ), 0 ) ) );

                vmBean.setBitrate( re.group( 3 ) );

            }

            Pattern patternVideo = compiler.compile( regexVideo,
                Perl5Compiler.CASE_INSENSITIVE_MASK );
            PatternMatcher matcherVideo = new Perl5Matcher();

            if( matcherVideo.contains( info, patternVideo ) )
            {
                MatchResult re = matcherVideo.getMatch();

                System.out.println( "编码格式 ===" + re.group( 1 ) );
                // System.out.println( "视频格式 ===" + re.group( 2 ) );
                // System.out.println( " 分辨率 == =" + re.group( 3 ) );

                vmBean.setCodeFormat( re.group( 2 ) );
                vmBean.setResolution( re.group( 3 ) );

            }

            // Pattern patternAudio = compiler.compile( regexAudio,
            // Perl5Compiler.CASE_INSENSITIVE_MASK );
            // PatternMatcher matcherAudio = new Perl5Matcher();
            //
            // if( matcherAudio.contains( info, patternAudio ) )
            // {
            // MatchResult re = matcherAudio.getMatch();
            // System.out.println( "音频编码 ===" + re.group( 1 ) );
            // System.out.println( "音频采样频率 ===" + re.group( 2 ) );
            // }

        }
        catch ( Exception e )
        {
            log.equals( e );
            e.printStackTrace();
        }

        log.info( "Media Info : " + vmBean );

        return vmBean;
    }

    /** 对进程的输出流进行监测 返回的是完成进度百分比 */
    private static String readInputStream( Process p, InputStream is,
        String eventKey, Map statusMap ) throws Exception
    {
        int comp = 0;
        int prevState = comp;
        // 将进程的输出流封装成缓冲读者对象
        BufferedReader br = new BufferedReader( new InputStreamReader( is ) );
        StringBuffer lines = new StringBuffer();// 构造一个可变字符串
        long totalTime = 0;

        // 对缓冲读者对象进行每行循环
        for ( String line = br.readLine(); line != null; line = br.readLine() )
        {
            System.out.println( line );
            lines.append( line );// 将每行信息字符串添加到可变字符串中
            int positionDuration = line.indexOf( "Duration:" );// 在当前行中找到第一个"Duration:"的位置
            int positionTime = line.indexOf( "time=" );
            if( positionDuration > 0 )
            {// 如果当前行中有"Duration:"

                String dur = line.replace( "Duration:", "" );// 将当前行中"Duration:"替换为""
                dur = StringUtil.subString( dur.trim(), 0, 8 );// 将替换后的字符串去掉首尾空格后截取前8个字符
                int h = Integer.parseInt( StringUtil.subString( dur, 0, 2 ) );// 封装成小时
                int m = Integer.parseInt( StringUtil.subString( dur, 3, 5 ) );// 封装成分钟

                int s = Integer.parseInt( StringUtil.subString( dur, 6, 8 ) );// 封装成秒
                totalTime = h * 3600 + m * 60 + s;// 得到总共的时间秒数

            }
            if( positionTime > 0 )
            {// 如果所用时间字符串存在
                // 截取包含time=的当前所用时间字符串
                String time = StringUtil.subString( line, positionTime, line
                    .indexOf( "bitrate" ) - 1 );
                time = StringUtil.subString( time, time.indexOf( "=" ) + 1,
                    time.indexOf( "." ) );// 截取当前所用时间字符串

                int h = Integer.parseInt( StringUtil.subString( time, 0, 2 ) );// 封装成小时
                int m = Integer.parseInt( StringUtil.subString( time, 3, 5 ) );// 封装成分钟

                int s = Integer.parseInt( StringUtil.subString( time, 6, 8 ) );// 封装成秒
                long timeVal = h * 3600 + m * 60 + s;// 得到总共的时间秒数

                if( timeVal > 0 )
                {
                    float t = ( float ) Long.valueOf( timeVal )
                        / ( float ) totalTime;// 计算所用时间与总共需要时间的比例

                    comp = ( int ) Math.ceil( t * 100 );// 计算完成进度百分比
                }

            }

            if( comp != prevState )
            {
                System.out.println( "完成：" + comp + "%" );

                if( statusMap != null )
                {
                    statusMap
                        .put( eventKey, Integer.valueOf( comp ).toString() );
                }
            }
            prevState = comp;
        }
        br.close();// 关闭进程的输出流
        return lines.toString();
    }

    public static void main( String[] args )
    { }

}
