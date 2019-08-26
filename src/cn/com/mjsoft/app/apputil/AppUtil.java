package cn.com.mjsoft.app.apputil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.X509TrustManager;

import com.alibaba.fastjson.JSON;

/**
 * APP模块核心工具类
 * @author mjsoft
 *
 */
@SuppressWarnings( "unchecked" )
public class AppUtil
{

    private static final String PARAM_FLAG = "?";

    private static final String PARAM_VALUE_FLAG = "=";

    private static final String PARAM_AND_FLAG = "&";

    /**
     * APP全局存储，需要存加密以及权限信息
     */
    private static Map APP_LOCAL = new HashMap();

    /**
     * API状态信息，是否要求Token ,是否加密传输，是否需要权限token
     */
    private static Map API_INFO = new HashMap();

    private static UUIDHexGenerator gen = new UUIDHexGenerator();

    public static void regAppCoreKey( String aesKey, String encodeAesKey )
    {
        APP_LOCAL.put( "AESKEY", aesKey );

        APP_LOCAL.put( "AESPKEY", encodeAesKey );
    }

    /**
     * 第一步APP启动注册：APP启动时向服务器获取公锁，并保存自己动态生成的AES，加密传输至CMS,完成注册。
     * 
     * @return
     * 
     */
    public static boolean initApp( String cmsBaseUrl )
    {
        // 获取CMS服务器RAS公锁 API
        String pkUrl = cmsBaseUrl + "/appbiz/appClientGetPK.do";

    
        String pk = doGETMethodRequest( pkUrl );

        // 随机生成aesKey，算法应为UUID类型即可，保证无重复
        String aesKey = getUUIDString();

        // 将刚才生成的UUID AES key通过获取的服务器公锁RSA加密
        String encodeAesKey = encryptPublic( pk, aesKey );
        
        // 向CMS服务器注册 API，传递当前运行时生成的唯一加密AES
        String regUrl = cmsBaseUrl + "/appbiz/appClientReg.do";

        // 向服务器注册：传递加密后的AES key给服务器完成注册
        String regOk = doPOSTMethodRequest( regUrl, "sys_app_pak="
            + encodeAesKey );
        
        //TODO 可以设定过期时间
        
        // 若服务器注册成功，在APP内部记录自己的AES key和加密过的AES key，以便后期使用
        if( "true".equals( regOk ) )
        {
            regAppCoreKey( aesKey, encodeAesKey );

            return true;
        }
        else
        {
            return false;
        }

    }

    public static String getAesKey()
    {
        return ( String ) APP_LOCAL.get( "AESKEY" );
    }

    public static String getPAesKey()
    {
        return ( String ) APP_LOCAL.get( "AESPKEY" );
    }

    public static String newToken( String serverUrl )
    {
        String tkUrl = serverUrl + "/appbiz/appGetToken.do?sys_app_pak="
            + getPAesKey();

        String tk = doGETMethodRequest( tkUrl );

        //aes key不在网络传递，所以不可被解
        String token = AppUtil.decodeAES( tk );

        return token;
    }

    public static AppStatus checkApiStatus( String serverUrl, String apiPath )
    {
        // 获取CMS服务器所有接口信息

        String getApiUrl = serverUrl + "/appbiz/getApiInfo.do";

        if( API_INFO.containsKey( apiPath ) )
        {
            return ( AppStatus ) API_INFO.get( apiPath );
        }

        String apiInfo = doGETMethodRequest( getApiUrl + "?sys_api_path="
            + apiPath );

        AppStatus as = JSON.parseObject( apiInfo, AppStatus.class );

        API_INFO.put( apiPath, as );

        return as;

    }

    public static String buildAPIUrl( String apiUrl, Map params )
    {
        String location = addParameters( params, apiUrl );

    
        return location;
    }

    /**
     * 将参数添加到URL
     * 
     * @param parameterMap
     * @param location
     * @return
     * 
     */
    @SuppressWarnings( "unused" )
    private static String addParameters( Map parameterMap, String location )
    {
        if( parameterMap != null )
        {
            if( !parameterMap.isEmpty() )
            {
                StringBuffer buf = new StringBuffer( location );

                if( isStringNotNull( location ) )
                {
                    buf.append( PARAM_FLAG );
                }

                Iterator entrys = parameterMap.entrySet().iterator();

                while ( entrys.hasNext() )
                {
                    Entry entry = ( ( Entry ) entrys.next() );
                    String value = "";
                    if( entry.getValue() != null )
                    {
                        value = entry.getValue().toString();
                    }

                    buf.append( entry.getKey().toString() );
                    buf.append( PARAM_VALUE_FLAG );

                    if( value != null )
                    {
                        buf.append( value );
                    }
                    else
                    {
                        buf.append( "" );
                    }

                    buf.append( PARAM_AND_FLAG );
                }

                return buf.deleteCharAt( buf.length() - 1 ).toString();
            }
        }
        return location;
    }

    public static String encryptPublic( String ks, String targetStr )
    {
        if( isStringNull( targetStr ) )
        {
            return "";
        }

        byte[] cipherData = null;
        String cipher = null;
        try
        {
            cipherData = RSAEncryptor.encrypt( RSAEncryptor
                .createPublicKey( ks ), targetStr.getBytes() );

            cipher = Base64.encode( cipherData );
        }
        catch ( Exception e )
        {
            // log.error( "公锁加密失败：" + targetStr );
            e.printStackTrace();
        }

        return cipher;

    }

    public static String decryptPrivate( String ks, String targetCipher )
    {
        if( isStringNull( targetCipher ) )
        {
            return "";
        }

        byte[] res = null;
        try
        {
            res = RSAEncryptor.decrypt( RSAEncryptor.createPrivateKey( ks ),
                Base64.decode( targetCipher ) );
        }
        catch ( Exception e )
        {
            // log.error( "私锁解密失败：" + targetCipher );
            e.printStackTrace();
        }

        return res == null ? "" : new String( res );
    }

    public static String encryptPrivate( String ks, String targetStr )
    {
        if( isStringNull( targetStr ) )
        {
            return "";
        }

        byte[] cipherData = null;
        String cipher = null;
        try
        {
            cipherData = RSAEncryptor.encrypt( RSAEncryptor
                .createPrivateKey( ks ), targetStr.getBytes() );

            cipher = Base64.encode( cipherData );
        }
        catch ( Exception e )
        {
            // log.error( "私锁加密失败：" + targetStr );
            e.printStackTrace();
        }

        return cipher;

    }

    public static String encryptPrivate( File ksFile, String targetStr )
    {
        return encryptPrivate(
            ( String ) readTXTFileContent( ksFile, "UTF-8" )[0], targetStr );
    }

    public static String decryptPublic( String ks, String targetCipher )
    {
        if( isStringNull( targetCipher ) )
        {
            return "";
        }

        byte[] res = null;
        try
        {
            res = RSAEncryptor.decrypt( RSAEncryptor.createPublicKey( ks ),
                Base64.decode( targetCipher ) );
        }
        catch ( Exception e )
        {
            // log.error( "公锁解密失败：" + targetCipher );
            e.printStackTrace();
        }

        return res == null ? "" : new String( res );
    }

    public static String decryptPublic( File ksFile, String targetStr )
    {
        return decryptPublic(
            ( String ) readTXTFileContent( ksFile, "UTF-8" )[0], targetStr );
    }

    public static String encodeAES( String pw )
    {
        return encodeAES( pw, ( String ) APP_LOCAL.get( "AESKEY" ) );
    }

    public static String encodeAES( String pw, String sa )
    {
        if( sa == null )
        {
            return pw;
        }

        byte[] encryptResult = null;

        try
        {
            encryptResult = EncodeOne.encryptAES( pw, sa );
        }
        catch ( Exception e )
        {
            return pw;
        }

        return encryptResult != null ? EncodeOne.encode16( encryptResult )
            .toLowerCase() : pw;
    }

    public static String decodeAES( String tk )
    {
        return decodeAES( tk, ( String ) APP_LOCAL.get( "AESKEY" ) );
    }

    public static String decodeAES( String pw, String sa )
    {
        if( sa == null )
        {
            return pw;
        }

        byte[] decryptResult = null;

        String end = "";

        try
        {
            decryptResult = EncodeOne.decryptAES( EncodeOne
                .parseHexStr2Byte( pw ), sa );

            end = decryptResult != null ? new String( decryptResult, "utf-8" )
                : pw;
        }
        catch ( Exception e )
        {
            end = pw;
        }

        return end;
    }

    public static String doGETMethodRequest( String url )
    {
        return readStream( doGETMethodRequestStream( url ), "UTF-8" );
    }

    public static InputStream doGETMethodRequestStream( String url )
    {
        InputStream stream = null;

        if( isStringNotNull( url ) )
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
                // log.error( e );
                e.printStackTrace();
            }
        }

        return stream;
    }

    public static String doPOSTMethodRequest( String url )
    {
        return readStream( doPOSTMethodRequestStream( url ), "UTF-8" );
    }

    public static InputStream doPOSTMethodRequestStream( String url )
    {
        InputStream stream = null;

        if( isStringNotNull( url ) )
        {
            try
            {
                URL targetUrl = new URL( url );

                URLConnection URLconnection = targetUrl.openConnection();
                HttpURLConnection httpConnection = ( HttpURLConnection ) URLconnection;

                httpConnection.setRequestProperty( "contentType", "UTF-8" );
                httpConnection.setConnectTimeout( 5 * 1000 );
                httpConnection.setRequestMethod( "POST" );
                httpConnection.setDoOutput( true );

                // httpConnection.connect();
                stream = httpConnection.getInputStream();

            }
            catch ( Exception e )
            {
                // log.error( e );
                e.printStackTrace();
            }
        }

        return stream;
    }

    public static String doPOSTMethodRequestForJson( String strURL, String json )
    {

        try
        {
            URL url = new URL( strURL );
            HttpURLConnection connection = ( HttpURLConnection ) url
                .openConnection();
            connection.setDoOutput( true );
            connection.setDoInput( true );
            connection.setUseCaches( false );
            connection.setInstanceFollowRedirects( true );
            connection.setRequestMethod( "POST" );
            connection.setRequestProperty( "Accept", "application/json" );
            connection.setRequestProperty( "Content-Type", "application/json" );
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter( connection
                .getOutputStream(), "UTF-8" ); // utf-8编码
            out.append( json );
            out.flush();
            out.close();
            // 读取响应

            InputStream is = connection.getInputStream();
            return readStream( is, "UTF-8" );

        }
        catch ( IOException e )
        {
            // log.error( e );
            e.printStackTrace();
        }

        return "";
    }

    public static String doPOSTMethodRequest( String strURL, Map params )
    {
        return doPOSTMethodRequest( strURL, addParameters( params, "" ) );
    }

    public static String doPOSTMethodRequest( String strURL, String str )
    {
        System.out.println( str );
        try
        {
            URL url = new URL( strURL );
            HttpURLConnection connection = ( HttpURLConnection ) url
                .openConnection();
            connection.setDoOutput( true );
            connection.setDoInput( true );
            connection.setUseCaches( false );
            connection.setInstanceFollowRedirects( true );
            connection.setRequestMethod( "POST" ); // 设置请求方式

            connection.setRequestProperty( "Content-Type",
                "application/x-www-form-urlencoded" ); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter( connection
                .getOutputStream(), "UTF-8" ); // utf-8编码
            out.write( str );
            out.flush();
            out.close();

            // 读取响应

            InputStream is = connection.getInputStream();

            return readStream( is, "UTF-8" );

        }
        catch ( IOException e )
        {
            // log.error( e );
            e.printStackTrace();
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

        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory( sc
            .getSocketFactory() );
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
            bufReader = new BufferedReader( new InputStreamReader( stream,
                charSet ) );

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

    /**
     * String是否为空
     * 
     * @param input
     * @return
     */
    public static boolean isStringNull( String input )
    {
        if( null == input || input.trim().equals( "" ) )
        {
            return true;
        }
        return false;
    }

    public static boolean isStringNotNull( String input )
    {
        if( null == input || input.trim().equals( "" ) )
        {
            return false;
        }
        return true;
    }

    public static String getUUIDString()
    {
        return gen.generate();
    }

    /**
     * 按照指定的字符集读取文件的内容
     * 
     * @param file
     * @param charset
     * @return Object[]: obj[0]-content,obj[1]-lineCount
     */
    public static Object[] readTXTFileContent( File file, String charset )
    {
        return readTXTFileContent( file, charset, null );
    }

    /**
     * 按照指定的字符集读取文件的内容,对于每行字符的处理由Behavior行为决定.当Behavior<br>
     * 动作返回TRUE时,读取动作将中断,返回空字符
     * 
     * @param file
     * @param charset
     * @return Object[]: obj[0]-content,obj[1]-lineCount
     */
    public static Object[] readTXTFileContent( File file, String charset,
        Object[] bParam )
    {
        Object[] value = new Object[] { "", Integer.valueOf( "0" ) };

        BufferedReader reader = null;
        InputStreamReader streamReader = null;

        boolean behaviorMode = false;

        int lineCount = 0;

        try
        {
            streamReader = new InputStreamReader( new FileInputStream( file ),
                charset );
            reader = new BufferedReader( streamReader );

            String line = reader.readLine();
            StringBuffer buf = new StringBuffer();

            String tempStr = "";
            while ( line != null )
            {

                lineCount++;

                // tempStr = new String( ( line + "\n" ).getBytes(), charset );
                tempStr = line + "\n";

                buf.append( tempStr );
                line = reader.readLine();
            }

            value[0] = buf.toString();
            value[1] = Integer.valueOf( lineCount );

            return value;

        }
        catch ( Exception e )
        {
            // log.error( "读取字符出现错误,指定文件为:" + file.getPath() );
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

        return value;
    }

    /**
     * 按指定的规则替换选定的字符串
     * 
     * @param tragetString 目标字符
     * @param replaceIt 查询字符
     * @param replacement 替换的字符
     * @param toLowerCase 大小写匹配: true - 不区分大小写, flase - 严格匹配大小写
     * @param prefixMode 是否只替换第一次出现的前缀 : true - 只替换前缀, false - 替换所有字符
     * @return
     */
    public static String replaceString( String tragetString, String replaceIt,
        String replacement, boolean toLowerCase, boolean prefixMode )
    {
        if( tragetString == null || replaceIt == null || replacement == null )
        {
            return tragetString;
        }

        StringBuffer buf = new StringBuffer();
        int legthOfSource = tragetString.length();
        int legthOfReplaceIt = replaceIt.length();
        int postionStart = 0;
        int currentPos;// 每次找到的要替换的字符串的位置

        String tmpStr;
        String tmpReplace;
        if( toLowerCase )
        {
            tmpStr = tragetString.toLowerCase();
            tmpReplace = replaceIt.toLowerCase();
        }
        else
        {
            tmpStr = tragetString;
            tmpReplace = replaceIt;
        }

        while ( ( currentPos = ( tmpStr.indexOf( tmpReplace, postionStart ) ) ) >= 0 )
        {// 当能找到
            buf.append( new String( tragetString.substring( postionStart,
                currentPos ) ) );// 替换的是真实字符串
            buf.append( replacement );// 替换
            if( prefixMode )
            {
                buf.append( new String( tragetString.substring( tragetString
                    .indexOf( replaceIt )
                    + replaceIt.length() ) ) );
                return buf.toString();
            }
            postionStart = currentPos + legthOfReplaceIt;// 位置移动
        }

        if( postionStart < legthOfSource )
        {// 如果source中已没有需要替换的字符串存在且没有到尾部，就要将剩下的并入结果
            buf.append( new String( tragetString.substring( postionStart ) ) );
        }

        return buf.toString();

    }

    /**
     * 此方法防止JDK同名方法内存泄露
     * 
     * @param target
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public static String subString( String target, int beginIndex, int endIndex )
    {
        return new String( target.substring( beginIndex, endIndex ) );
    }

    /**
     * 此方法防止JDK(1.4,1.5,1.6)同名方法内存泄露
     * 
     * @param target
     * @param beginIndex
     * @param endIndex
     * @return 新的安全String
     */
    public static String subString( String target, int beginIndex )
    {
        return new String( target.substring( beginIndex ) );
    }

}
