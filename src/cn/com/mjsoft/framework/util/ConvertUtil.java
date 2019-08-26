package cn.com.mjsoft.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ConvertUtil
{
    /**
     * is8859转换为GBK
     * 
     * @param input
     * @return
     */
    public static String toGBK( String input )
    {
        if( StringUtil.isStringNull( input ) )
        {
            return "";
        }
        String str = "";
        try
        {
            str = new String( input.getBytes( "ISO-8859-1" ), "GBK" );
        }
        catch ( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 处理下载中的中文(GBK)问题
     * 
     * @param input
     * @return
     */
    public static String toGBKForURL( String input )
    {
        String url = input;
        try
        {
            url = URLEncoder.encode( url, "GBK" );// 使用指定的编码机制将字符串转换为
            // application/x-www-form-urlencoded
            // 格式
        }
        catch ( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }
        url = StringUtil.replaceString( url, "+", " ", false, false );
        url = StringUtil.replaceString( url, "%2F", "/", false, false );
        return url;
    }
}
