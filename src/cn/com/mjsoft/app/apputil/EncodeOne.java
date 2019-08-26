package cn.com.mjsoft.app.apputil;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import cn.com.mjsoft.framework.util.StringUtil;

/**
 * 字符串 DESede(3DES) ,AES 加密
 */
public class EncodeOne
{
    private static final String Algorithm = "DESede"; // 定义 加密算法,可用
    // DES,DESede,Blowfish
    private static final String hexString = "0123456789ABCDEF";

    /**
     * 
     * @param keybyte 加密密钥，长度为24字节
     * @param src 字节数组(根据给定的字节数组构造一个密钥。 )
     * @return
     */
    public static byte[] encryptMode( byte[] keybyte, byte[] src )
    {
        try
        {
            // 根据给定的字节数组和算法构造一个密钥
            SecretKey deskey = new SecretKeySpec( keybyte, Algorithm );
            // 加密
            Cipher c1 = Cipher.getInstance( Algorithm );
            c1.init( Cipher.ENCRYPT_MODE, deskey );
            return c1.doFinal( src );
        }
        catch ( java.security.NoSuchAlgorithmException e1 )
        {
            e1.printStackTrace();
        }
        catch ( javax.crypto.NoSuchPaddingException e2 )
        {
            e2.printStackTrace();
        }
        catch ( java.lang.Exception e3 )
        {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * @param keybyte 密钥
     * @param src 需要解密的数据
     * @return
     */
    public static byte[] decryptMode( byte[] keybyte, byte[] src )
    {
        try
        {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec( keybyte, Algorithm );
            // 解密
            Cipher c1 = Cipher.getInstance( Algorithm );
            c1.init( Cipher.DECRYPT_MODE, deskey );
            return c1.doFinal( src );
        }
        catch ( java.security.NoSuchAlgorithmException e1 )
        {
            e1.printStackTrace();
        }
        catch ( javax.crypto.NoSuchPaddingException e2 )
        {
            e2.printStackTrace();
        }
        catch ( java.lang.Exception e3 )
        {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转为16进制
     * 
     * @param str
     * @return
     */
    public static String encode16( byte[] bytes )
    {
        // 根据默认编码获取字节数组
        // byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder( bytes.length * 2 );

        // 将字节数组中每个字节拆解成2位16进制整数
        for ( int i = 0; i < bytes.length; i++ )
        {
            sb.append( hexString.charAt( ( bytes[i] & 0xf0 ) >> 4 ) );
            sb.append( hexString.charAt( ( bytes[i] & 0x0f ) >> 0 ) );
        }
        return sb.toString();
    }

    /**
     * 
     * @param bytes
     * @return 将16进制数字解码成字符串,适用于所有字符（包括中文）
     */
    public static byte[] decode16( String bytes )
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream( bytes.length() / 2 );
        // 将每2位16进制整数组装成一个字节
        for ( int i = 0; i < bytes.length(); i += 2 )
            baos.write( ( hexString.indexOf( bytes.charAt( i ) ) << 4 | hexString.indexOf( bytes
                .charAt( i + 1 ) ) ) );
        return baos.toByteArray();
    }

    /**
     * 加密
     * 
     * @param content 需要加密的内容
     * @param salt 加密salt
     * @return
     */
    public static byte[] encryptAES( String content, String salt )
    {
        try
        {
            SecureRandom random = SecureRandom.getInstance( "SHA1PRNG" );
            random.setSeed( salt.getBytes() );

            KeyGenerator kgen = KeyGenerator.getInstance( "AES" );
            kgen.init( 128, random );
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec( enCodeFormat, "AES" );
            Cipher cipher = Cipher.getInstance( "AES" );// 创建密码器
            byte[] byteContent = content.getBytes( "utf-8" );
            cipher.init( Cipher.ENCRYPT_MODE, key );// 初始化
            byte[] result = cipher.doFinal( byteContent );
            return result; // 加密
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解密
     * 
     * @param content 待解密内容
     * @param salt 解密salt
     * @return
     */
    public static byte[] decryptAES( byte[] content, String salt )
    {
        try
        {
            SecureRandom random = SecureRandom.getInstance( "SHA1PRNG" );
            random.setSeed( salt.getBytes() );

            KeyGenerator kgen = KeyGenerator.getInstance( "AES" );
            kgen.init( 128, random );
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec( enCodeFormat, "AES" );
            Cipher cipher = Cipher.getInstance( "AES" );// 创建密码器
            cipher.init( Cipher.DECRYPT_MODE, key );// 初始化
            byte[] result = cipher.doFinal( content );
            return result; // 加密
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 加密
     * 
     * @param content 需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static byte[] encrypt2( String content, String password )
    {
        try
        {
            SecretKeySpec key = new SecretKeySpec( password.getBytes(), "AES" );
            Cipher cipher = Cipher.getInstance( "AES/ECB/NoPadding" );
            byte[] byteContent = content.getBytes( "utf-8" );
            cipher.init( Cipher.ENCRYPT_MODE, key );// 初始化
            byte[] result = cipher.doFinal( byteContent );
            return result; // 加密
        }
        catch ( NoSuchAlgorithmException e )
        {
            e.printStackTrace();
        }
        catch ( NoSuchPaddingException e )
        {
            e.printStackTrace();
        }
        catch ( InvalidKeyException e )
        {
            e.printStackTrace();
        }
        catch ( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }
        catch ( IllegalBlockSizeException e )
        {
            e.printStackTrace();
        }
        catch ( BadPaddingException e )
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将16进制转换为二进制
     * 
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte( String hexStr )
    {
        if( hexStr.length() < 1 )
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for ( int i = 0; i < hexStr.length() / 2; i++ )
        {
            int high = Integer.parseInt( AppUtil.subString( hexStr, i * 2, i * 2 + 1 ), 16 );
            int low = Integer.parseInt( AppUtil.subString( hexStr, i * 2 + 1, i * 2 + 2 ), 16 );
            result[i] = ( byte ) ( high * 16 + low );
        }
        return result;
    }

    /**
     * base 64 encode
     * 
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode( byte[] bytes )
    {
        return Base64.encodeBase64String( bytes );
    }

    /**
     * base 64 decode
     * 
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode( String base64Code ) throws Exception
    {
        return StringUtil.isStringNull( base64Code ) ? null : new BASE64Decoder()
            .decodeBuffer( base64Code );
    }

    
}
