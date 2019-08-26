package cn.com.mjsoft.framework.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;
import org.im4java.core.IMOperation;
import org.im4java.process.StandardStream;

import cn.com.mjsoft.framework.config.impl.SystemConfiguration;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.swetake.util.Qrcode;

public class ImageUtil
{
    private static Logger log = Logger.getLogger( ImageUtil.class );

    public static final String GM_PATH = "core" + File.separator + "extools"
        + File.separator + "image" + File.separator + "GraphicsMagick"; // GraphicsMagick主目录

    public ByteArrayOutputStream ResizeImg( InputStream inputStream,
        int maxWidth, int maxHeight ) throws IOException
    {
        BufferedImage bufferimage = ImageIO.read( inputStream );
        int curWidth = bufferimage.getWidth();
        int curHeight = bufferimage.getHeight();

        // double desiredRatio =
        // Math.min((double)(maxWidth)/(double)curWidth,(double)maxHeight/(double)curHeight);
        // curWidth = (int) (curWidth*desiredRatio);
        // curHeight = (int)(curHeight*desiredRatio);
        double ratio = 0;
        Image itemp = bufferimage.getScaledInstance( maxWidth, maxHeight,
            BufferedImage.SCALE_SMOOTH );
        // 计算比例
        if( ( bufferimage.getHeight() > maxHeight )
            || ( bufferimage.getWidth() > maxWidth ) )
        {
            if( bufferimage.getHeight() > bufferimage.getWidth() )
            {
                ratio = ( new Integer( maxHeight ) ).doubleValue()
                    / bufferimage.getHeight();
            }
            else
            {
                ratio = ( new Integer( maxWidth ) ).doubleValue()
                    / bufferimage.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp( AffineTransform
                .getScaleInstance( ratio, ratio ), null );
            itemp = op.filter( bufferimage, null );
        }

        BufferedImage bufftmp = new BufferedImage( curWidth, curHeight,
            BufferedImage.TYPE_INT_RGB );
        Graphics2D g2D = bufftmp.createGraphics();

        g2D.setColor( Color.white );
        g2D.fillRect( 0, 0, curWidth, curHeight );
        // g2D.drawImage(bufferimage, null, 0, 0);

        if( maxWidth == itemp.getWidth( null ) )
            g2D.drawImage( itemp, 0,
                ( maxHeight - itemp.getHeight( null ) ) / 2, itemp
                    .getWidth( null ), itemp.getHeight( null ), Color.white,
                null );
        else
            g2D.drawImage( itemp, ( maxWidth - itemp.getWidth( null ) ) / 2, 0,
                itemp.getWidth( null ), itemp.getHeight( null ), Color.white,
                null );
        g2D.dispose();
        // itemp = bufftmp;

        ByteArrayOutputStream op = new ByteArrayOutputStream();

        JPEGImageEncoder imageEncoder = JPEGCodec.createJPEGEncoder( op );

        imageEncoder.encode( ( BufferedImage ) itemp );

        return op;
    }

    /**
     * 获取指定图片的子区域,并输出到指定路径
     * 
     * @param targetImgFile 目标图片文件
     * @param outPutPicFullName 输出子图片完整路径
     * @param x x轴数据
     * @param y y轴数据
     * @param w 宽度
     * @param h 高度
     * @throws IOException
     */
    public static void subPicture( File targetImgFile,
        String outPutPicFullName, int x, int y, int w, int h )
        throws IOException
    {
        doSubImg( ImageIO.read( targetImgFile ), outPutPicFullName, x, y, w, h );

    }

    private static void doSubImg( BufferedImage image,
        String outPutPicFullName, int x, int y, int w, int h )
        throws IOException
    {
        BufferedImage subImage = image.getSubimage( x, y, w, h );

        BufferedImage tmpImg = new BufferedImage( w, h,
            BufferedImage.TYPE_INT_RGB );

        tmpImg.getGraphics().drawImage(
            subImage.getScaledInstance( w, h, BufferedImage.SCALE_SMOOTH ), 0,
            0, null );

        File littleFile = new File( outPutPicFullName );
        ImageIO.write( tmpImg, "JPEG", littleFile );

    }

    public static void zoomPicture( File imgFile, String outPutPicName,
        double max ) throws IOException
    {
        ImageIO.setUseCache( true );

        ImageIO.setCacheDirectory( new File( SystemConfiguration.getInstance()
            .getSystemConfig().getSystemRealPath()
            + "sys_temp" ) );

        doResizeImg( ImageIO.read( imgFile ), outPutPicName, max );

    }

    public static void zoomPicture( File imgFile, String outPutPicName,
        int width, int height ) throws IOException
    {
        ImageIO.setUseCache( true );

        ImageIO.setCacheDirectory( new File( SystemConfiguration.getInstance()
            .getSystemConfig().getSystemRealPath()
            + "sys_temp" ) );

        doResizeImg( ImageIO.read( imgFile ), outPutPicName, width, height );

    }

    public static void zoomPicture( InputStream imgStream,
        String outPutPicName, double max ) throws IOException
    {
        doResizeImg( ImageIO.read( imgStream ), outPutPicName, max );

    }

    private static void doResizeImg( BufferedImage Bi,
        String outPutPicFullName, double max ) throws IOException
    {

        if( Bi == null )
        {
            return;
        }

        double maxLimit = max;
        double ratio = 1.0;

        if( ( Bi.getHeight() > maxLimit ) || ( Bi.getWidth() > maxLimit ) )
        {
            // if( Bi.getHeight() > Bi.getWidth() )
            // ratio = maxLimit / Bi.getHeight();
            // else
            // ratio = maxLimit / Bi.getWidth();
            // 强制高度为ratio参考值
            ratio = maxLimit / Bi.getWidth();
        }
        int widthdist = ( int ) Math.floor( Bi.getWidth() * ratio ), heightdist = ( int ) Math
            .floor( Bi.getHeight() * ratio );

        BufferedImage tag = new BufferedImage( widthdist, heightdist,
            BufferedImage.TYPE_INT_RGB );

        Graphics g = tag.getGraphics();
        Image image = Bi.getScaledInstance( widthdist, heightdist,
            BufferedImage.SCALE_SMOOTH );
        g.drawImage( image, 0, 0, null );
        image.flush();

        File littleFile = new File( outPutPicFullName );
        ImageIO.write( tag, "JPEG", littleFile );
        g.dispose();
    }

    private static void doResizeImg( BufferedImage Bi,
        String outPutPicFullName, int width, int height ) throws IOException
    {

        if( Bi == null )
        {
            return;
        }

        // double maxLimit = max;
        // double ratio = 1.0;
        //
        // if( ( Bi.getHeight() > maxLimit ) || ( Bi.getWidth() > maxLimit ) )
        // {
        // // if( Bi.getHeight() > Bi.getWidth() )
        // // ratio = maxLimit / Bi.getHeight();
        // // else
        // // ratio = maxLimit / Bi.getWidth();
        // // 强制高度为ratio参考值
        // ratio = maxLimit / Bi.getWidth();
        // }
        int widthdist = width, heightdist = height;

        BufferedImage tag = new BufferedImage( widthdist, heightdist,
            BufferedImage.TYPE_INT_RGB );

        Graphics g = tag.getGraphics();
        Image image = Bi.getScaledInstance( widthdist, heightdist,
            BufferedImage.SCALE_SMOOTH );
        g.drawImage( image, 0, 0, null );
        image.flush();

        File littleFile = new File( outPutPicFullName );
        ImageIO.write( tag, "JPEG", littleFile );
        g.dispose();
    }

    public static void reduceImg( String imgsrc, String imgdist, int widthdist,
        int heightdist )
    {
        try
        {
            File srcfile = new File( imgsrc );
            if( !srcfile.exists() )
            {
                return;
            }
            Image src = javax.imageio.ImageIO.read( srcfile );

            BufferedImage tag = new BufferedImage( ( int ) widthdist,
                ( int ) heightdist, BufferedImage.TYPE_INT_RGB );

            tag.getGraphics().drawImage(
                src.getScaledInstance( widthdist, heightdist,
                    Image.SCALE_SMOOTH ), 0, 0, null );
            // / tag.getGraphics().drawImage(src.getScaledInstance(widthdist,
            // heightdist, Image.SCALE_AREA_AVERAGING), 0, 0, null);

            FileOutputStream out = new FileOutputStream( imgdist );
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder( out );
            encoder.encode( tag );
            out.close();

        }
        catch ( IOException ex )
        {
            ex.printStackTrace();
        }
    }

    /**
     * 从指定的图片web地址下载图片,若下载成功，返回本地路径，否则返回空.
     * 
     * @param targetImgUrl 图片URL
     * @param systemSaveImgpath 存储路径
     * @return
     */
    public static File downloadImageByUrl( String targetImgUrl,
        String systemSaveImgpath )
    {
        File endFile = null;

        try
        {

            URL targetUrl = new URL( targetImgUrl );

            // URLConnection ucon = targetUrl.openConnection();

            String webImgFile = targetUrl.getFile();

            String targetFileNameEnd = null;

            if( StringUtil.isStringNotNull( webImgFile ) )
            {
                targetFileNameEnd = StringUtil.subString( webImgFile,
                    webImgFile.lastIndexOf( "." ), webImgFile.length() );
            }

            if( StringUtil.isStringNull( targetFileNameEnd ) )
            {
                return null;
            }

            // HttpClient client = new DefaultHttpClient();
            //
            // HttpGet httpget = new HttpGet( targetImgUrl );
            //
            // httpget.addHeader( "Content-type",
            // "application/x-www-form-urlencoded" );
            //
            // HttpResponse response = client.execute( httpget );
            // HttpEntity entity = response.getEntity();
            //
            // System.out.println( "----------------------------------------" );
            // System.out.println( entity.getContentLength() );
            // System.out.println( "----------------------------------------" );

            endFile = new File( systemSaveImgpath + StringUtil.getUUIDString()
                + targetFileNameEnd );

            File dir = new File( systemSaveImgpath );

            if( !dir.exists() )
            {
                dir.mkdirs();
            }

            // log.info( "最终图片路径：" + endFile.toString() );
            InputStream ins = null;
            BufferedInputStream ios = null;
            FileOutputStream fo = null;
            BufferedOutputStream bos = null;
            try
            {
                ins = targetUrl.openStream();

                ios = new BufferedInputStream( ins );

                fo = new FileOutputStream( endFile );

                bos = new BufferedOutputStream( fo );

                FileUtil.writeFile( ios, bos );
            }
            finally
            {
                log.info( "尝试下载图片:" + targetImgUrl );

                if( ins != null )
                {
                    ins.close();
                }
                if( ios != null )
                {
                    ios.close();
                }

                if( fo != null )
                {
                    fo.close();
                }

                if( bos != null )
                {
                    bos.close();
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

        return endFile;
    }

    /**
     * 返回指定图片的长和宽.[0]:width, [1]:height, [3] modifyTime
     * 
     * @param path
     * @return
     */
    public static Object[] getImageHeightAndWidth( String path )
    {
        File file = new File( path );
        FileInputStream fis = null;

        Object[] result = new Object[] { Integer.valueOf( 0 ),
            Integer.valueOf( 0 ), Long.valueOf( 0 ) };
        try
        {
            fis = new FileInputStream( file );
            ImageIO.setUseCache( true );

            File testDir = new File( SystemConfiguration.getInstance()
                .getSystemConfig().getSystemRealPath()
                + "sys_temp" );

            if( !testDir.exists() )
            {
                testDir.mkdirs();
            }

            ImageIO.setCacheDirectory( new File( SystemConfiguration
                .getInstance().getSystemConfig().getSystemRealPath()
                + "sys_temp" ) );

            log.info( ">>>>>>>>>cache dir>>>>>>>>>:"
                + SystemConfiguration.getInstance().getSystemConfig()
                    .getSystemRealPath() + "sys_temp" );

            BufferedImage bufferedImg = ImageIO.read( fis );
            result[0] = Integer.valueOf( bufferedImg.getWidth() );
            result[1] = Integer.valueOf( bufferedImg.getHeight() );
            result[2] = Long.valueOf( file.lastModified() );
            bufferedImg.flush();
        }
        catch ( Exception e )
        {
            result = new Object[] { Integer.valueOf( 0 ), Integer.valueOf( 0 ),
                Long.valueOf( 0 ) };
            // log.error( e );
            e.printStackTrace();
        }
        finally
        {
            if( fis != null )
            {
                try
                {
                    fis.close();
                }
                catch ( IOException e )
                {

                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * 
     * @param souchFilePath：源图片路径
     * @param targetFilePath：生成后的目标图片路径
     * @param markContent:要加的文字
     * @param markContentColor:文字颜色
     * @param qualNum:质量数字
     * @param fontType:字体类型
     * @param fontSize:字体大小
     * @return
     */
    public static void createCharMark( String souchFilePath,
        String targetFilePath, String markContent, Color markContentColor,
        float qualNum, String fontType, int fontSize, int fixWidth,
        int fixHeight )
    {

        ImageIcon imgIcon = new ImageIcon( souchFilePath );
        Image theImg = imgIcon.getImage();
        // Image可以获得 输入图片的信息
        int width = theImg.getWidth( null );
        int height = theImg.getHeight( null );

        // 800 800 为画出图片的大小
        BufferedImage bimage = new BufferedImage( width, height,
            BufferedImage.TYPE_INT_RGB );
        // 2d 画笔
        Graphics2D g = bimage.createGraphics();
        g.setColor( markContentColor );
        g.setBackground( Color.white );

        // 画出图片-----------------------------------
        g.drawImage( theImg, 0, 0, null );
        // 画出图片-----------------------------------

        // --------对要显示的文字进行处理--------------
        AttributedString ats = new AttributedString( markContent );
        Font f = new Font( fontType, Font.BOLD, fontSize );
        ats.addAttribute( TextAttribute.FONT, f, 0, markContent.length() );
        AttributedCharacterIterator iter = ats.getIterator();
        // ----------------------

        g.drawString( iter, width - fixWidth, height - fixHeight );
        // 添加水印的文字和设置水印文字出现的内容 ----位置
        g.dispose();// 画笔结束

        FileOutputStream out = null;
        try
        {
            // 输出 文件 到指定的路径
            out = new FileOutputStream( targetFilePath );
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder( out );

            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam( bimage );

            param.setQuality( qualNum, true );
            encoder.encode( bimage, param );

            out.close();
        }
        catch ( Exception e )
        {
            log.error( e );
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

    /** **************************************以下为高质量图象处理逻辑*************************************** */

    /**
     * * 根据坐标裁剪图片
     * 
     * @param srcPath 要裁剪图片的路径
     * @param newPath 裁剪图片后的路径
     * @param x 起始横坐标
     * @param y 起始挫坐标
     * @param x1 结束横坐标
     * @param y1 结束挫坐标
     */
    public static void cutImage( String srcPath, String newPath, int x, int y,
        int x1, int y1 )
    {
        int width = x1 - x;
        int height = y1 - y;
        IMOperation op = new IMOperation();

        op.addImage();
        /**
         * width：裁剪的宽度 height：裁剪的高度 x：裁剪的横坐标 y：裁剪的挫坐标
         */
        op.crop( Integer.valueOf( width ), Integer.valueOf( height ), Integer
            .valueOf( x ), Integer.valueOf( y ) );

        if( width > 1000 || height > 1000 )
        {
            op.quality( Double.valueOf( 96 ) );
        }
        else
        {
            op.quality( Double.valueOf( 100 ) );
        }

        op.addImage();

        // 使用GM
        ConvertCmd cmd = new ConvertCmd( true );
        cmd.setErrorConsumer( StandardStream.STDERR );

        String osName = System.getProperty( "os.name" ).toLowerCase();
        if( osName.indexOf( "win" ) != -1 )
        {
            cmd.setSearchPath( SystemConfiguration.getInstance()
                .getSystemConfig().getSystemRealPath()
                + GM_PATH );
        }

        try
        {
            cmd.run( op, new String[] { srcPath, newPath } );
        }
        catch ( Exception e )
        {

            e.printStackTrace();
        }

    }

    /**
     * 
     * 根据尺寸缩放图片
     * 
     * @param width 缩放后的图片宽度 ,若为-1,则按照高度缩放
     * @param height 缩放后的图片高度,若为-1,则按照宽度缩放
     * @param srcPath 源图片路径
     * @param newPath 缩放后图片的路径
     */
    public static void resizeImage( int width, int height, String srcPath,
        String newPath, int quality )
    {
        IMOperation op = new IMOperation();
        op.addImage();

        if( height == -1 )
        {
            op.resize( Integer.valueOf( width ), null );
        }
        else if( width == -1 )
        {
            op.resize( null, Integer.valueOf( height ) );
        }
        else
        {
            op
                .resize( Integer.valueOf( width ), Integer.valueOf( height ),
                    ">" );
        }

        op.quality( Double.valueOf( quality ) );
        op.addImage();

        // 使用GM
        ConvertCmd cmd = new ConvertCmd( true );
        cmd.setErrorConsumer( StandardStream.STDERR );

        String osName = System.getProperty( "os.name" ).toLowerCase();
        if( osName.indexOf( "win" ) != -1 )
        {
            cmd.setSearchPath( SystemConfiguration.getInstance()
                .getSystemConfig().getSystemRealPath()
                + GM_PATH );
            // cmd.setSearchPath( "D:\\Program Files\\GraphicsMagick-1.3.18-Q8"
            // );
        }

        try
        {
            cmd.run( op, new String[] { srcPath, newPath } );
        }
        catch ( Exception e )
        {
            //e.printStackTrace();
        }

    }

    /**
     * 给图片加文字水印
     * 
     * @param srcPath 源图片路径
     */
    public static void addImageTextMark( String srcPath, String newPath,
        String markStr, String fontName, String color, int fontSize, int x,
        int y )
    {
        GMOperation go = new GMOperation();

        go.font( fontName ).gravity( "southeast" ).pointsize(
            Integer.valueOf( fontSize ) ).fill( color ).draw(
            "text " + x + "," + y + " " + "'" + markStr + "'" );

        go.quality( Double.valueOf( 100 ) );
        go.addImage();
        go.addImage();

        // 使用GM

        ConvertCmd cmd = new ConvertCmd( true );
        cmd.setErrorConsumer( StandardStream.STDERR );

        String osName = System.getProperty( "os.name" ).toLowerCase();
        if( osName.indexOf( "win" ) != -1 )
        {
            cmd.setSearchPath( SystemConfiguration.getInstance()
                .getSystemConfig().getSystemRealPath()
                + GM_PATH );
        }

        try
        {
            cmd.run( go, new String[] { srcPath, newPath } );
        }
        catch ( Exception e )
        {

            e.printStackTrace();
        }

    }

    /**
     * 给图片加图片水印
     * 
     * @param srcPath 源图片路径
     */
    public static boolean addImageMark( String srcPath, String newPath,
        String markPath, String where, int x, int y, int offX, int offY, int dis )

    {
        File testFile = new File( markPath );

        if( !testFile.exists() )
        {
            return false;
        }

        IMOperation go = new IMOperation();
        go.gravity( where ).geometry( Integer.valueOf( x ),
            Integer.valueOf( y ), Integer.valueOf( offX ),
            Integer.valueOf( offY ) ).dissolve( Integer.valueOf( dis ) );

        go.quality( Double.valueOf( 1000 ) );
        go.addImage();
        go.addImage();
        go.addImage();

        // 使用GM

        CompositeCmd cmd = new CompositeCmd( true );
        cmd.setErrorConsumer( StandardStream.STDERR );

        String osName = System.getProperty( "os.name" ).toLowerCase();

        if( osName.indexOf( "win" ) != -1 )
        {
            cmd.setSearchPath( SystemConfiguration.getInstance()
                .getSystemConfig().getSystemRealPath()
                + GM_PATH );
        }

        // cmd.setSearchPath( "D:\\Program Files\\GraphicsMagick-1.3.18-Q8" );
        boolean isOK = false;

        try
        {
            cmd.run( go, new String[] { markPath, srcPath, newPath } );
            isOK = true;
        }
        catch ( Exception e )
        {
            isOK = false;
            e.printStackTrace();
        }

        return isOK;
    }

    /**
     * 生成二维码(QRCode)图片
     * 
     * @param content 二维码图片的内容
     * @param imgPath 生成二维码图片完整的路径
     * @param ccbpath 二维码图片中间的logo路径
     * @param size存储数据量 1~40
     */
    public static int createQRCode( String content, String imgPath,
        String ccbPath, int size )
    {
        try
        {
            Qrcode qrcodeHandler = new Qrcode();
            qrcodeHandler.setQrcodeErrorCorrect( 'M' );
            qrcodeHandler.setQrcodeEncodeMode( 'B' );
            qrcodeHandler.setQrcodeVersion( size );

            int imgSize = 67 + 12 * ( size - 1 );

            byte[] contentBytes = content.getBytes( "gb2312" );
            BufferedImage bufImg = new BufferedImage( imgSize, imgSize,
                BufferedImage.TYPE_INT_RGB );
            Graphics2D gs = bufImg.createGraphics();

            gs.setBackground( Color.WHITE );
            gs.clearRect( 0, 0, imgSize, imgSize );

            // 设定图像颜色 > BLACK
            gs.setColor( Color.BLACK );
            // 设置偏移量 不设置可能导致解析出错
            int pixoff = 2;
            // 输出内容 > 二维码
            if( contentBytes.length > 0 && contentBytes.length < 120 )
            {
                boolean[][] codeOut = qrcodeHandler.calQrcode( contentBytes );
                for ( int i = 0; i < codeOut.length; i++ )
                {
                    for ( int j = 0; j < codeOut.length; j++ )
                    {
                        if( codeOut[j][i] )
                        {
                            gs.fillRect( j * 3 + pixoff, i * 3 + pixoff, 3, 3 );
                        }
                    }
                }
            }
            else
            {
                System.err.println( "QRCode content bytes length = "
                    + contentBytes.length + " not in [ 0,120 ]. " );
                return -1;
            }

            if( StringUtil.isStringNotNull( ccbPath ) )
            {
                Image img = ImageIO.read( new File( ccbPath ) );
                // 实例化一个Image对象。
                gs.drawImage( img, 55, 55, null );
                gs.dispose();
                bufImg.flush();

                // 实例化一个Image对象。
                gs.drawImage( img, 55, 55, null );
                gs.dispose();
                bufImg.flush();
            }

            // 生成二维码QRCode图片
            File imgFile = new File( imgPath );
            ImageIO.write( bufImg, "png", imgFile );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return -100;
        }
        return 0;
    }

    public static void main( String[] args ) throws Exception
    { }
}
