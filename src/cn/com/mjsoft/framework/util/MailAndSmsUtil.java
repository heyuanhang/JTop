package cn.com.mjsoft.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.HtmlEmail;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class MailAndSmsUtil
{
    private static Logger log = Logger.getLogger( MailAndSmsUtil.class );

    public static boolean sendEmail( String host, String port, boolean sslMode,
        String charset, String[] sendTo, String fromMail, String fromName,
        String sendMail, String pw, String subject, String msg )
    {
        // 不要使用SimpleEmail,会出现乱码问题
        HtmlEmail email = new HtmlEmail();
        try
        {
            // 这里是SMTP发送服务器的名字：，163的如下：
            email.setSSLOnConnect( sslMode );
            email.setHostName( host );

            if( StringUtil.isStringNotNull( port ) )
            {
                if( sslMode )
                {
                    email.setSslSmtpPort( port );
                }
                else
                {
                    email.setSmtpPort( StringUtil.getIntValue( port, 21 ) );
                }
            }

            email.setCharset( charset );
            // 收件人的邮箱

            email.addTo( sendTo );
            // 发送人的邮箱
            email.setFrom( fromMail, fromName );
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
            email.setAuthentication( sendMail, pw );
            email.setSubject( subject );
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
            email.setMsg( msg );
            // 发送
            email.send();

        }
        catch ( Exception e )
        {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    public static String sendSMS( String url, String account, String pw,
        String mobile, String content )
    {
        if( StringUtil.isStringNull( url ) )
        {
            return "-999";
        }

        String result = "-999";

        HttpClient httpclient = new DefaultHttpClient();

        log.info( "[sendSMS] url:" + url );

        // 创建httppost

        HttpPost httppost = new HttpPost( url );

        httppost.setHeader( "Connection", "close" );

        httppost.setHeader( "ContentType",
            "application/x-www-form-urlencoded;charset=UTF-8" );

        // 创建参数队列

        List formparams = new ArrayList();

        formparams.add( new BasicNameValuePair( "account", account ) );

        formparams.add( new BasicNameValuePair( "password", pw ) );

        formparams.add( new BasicNameValuePair( "mobile", mobile ) );

        formparams.add( new BasicNameValuePair( "content", content ) );

        UrlEncodedFormEntity uefEntity;

        try
        {
            uefEntity = new UrlEncodedFormEntity( formparams, "UTF-8" );

            httppost.setEntity( uefEntity );

            HttpResponse response;

            response = httpclient.execute( httppost );

            HttpEntity entity = response.getEntity();

            if( entity != null )
            {

                result = EntityUtils.toString( entity, "UTF-8" );

                System.out.println( "[sendSMS]>>>>>>>>>>>>>>>>>>>>>>>>>> 返回信息:"
                    + result );

                // Document doc = DocumentHelper.parseText( result );
                // Element root = doc.getRootElement();
                //
                // String code = root.elementText( "result" );
                // String msg = root.elementText( "message" );
                // String smsid = root.elementText( "smsid" );
                //
                // log.info( "[sendSMS] code:" + code );
                // log.info( "[sendSMS] msg:" + msg );
                // log.info( "[sendSMS] id:" + smsid );
            }

        }
        catch ( Exception e )
        {
            result = "-999";
            log.error( e );
            e.printStackTrace();
        }
        finally
        {
            httpclient.getConnectionManager().shutdown();
        }

        return result;
    }

    public static String SMS( String postData, String postUrl )
    {
        try
        {
            // 发送POST请求
            URL url = new URL( postUrl );
            HttpURLConnection conn = ( HttpURLConnection ) url.openConnection();
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type",
                "application/x-www-form-urlencoded" );
            conn.setRequestProperty( "Connection", "Keep-Alive" );
            conn.setUseCaches( false );
            conn.setDoOutput( true );

            conn.setRequestProperty( "Content-Length", "" + postData.length() );
            OutputStreamWriter out = new OutputStreamWriter( conn
                .getOutputStream(), "UTF-8" );
            out.write( postData );
            out.flush();
            out.close();

            // 获取响应状态
            if( conn.getResponseCode() != HttpURLConnection.HTTP_OK )
            {
                System.out.println( "connect failed!" );
                return "";
            }
            // 获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader( new InputStreamReader( conn
                .getInputStream(), "utf-8" ) );
            while ( ( line = in.readLine() ) != null )
            {
                result += line + "\n";
            }
            in.close();
            return result;
        }
        catch ( IOException e )
        {
            e.printStackTrace( System.out );
        }
        return "";
    }

    public static void main( String[] arg ) throws Exception
    { }
}
