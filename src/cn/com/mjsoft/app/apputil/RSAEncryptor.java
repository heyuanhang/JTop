package cn.com.mjsoft.app.apputil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

 

public class RSAEncryptor
{
    private static final String PUKN = "publicKey.ks";

    private static final String PRKN = "privateKey.ks";

    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
        '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * 生成密钥文件
     * 
     * @param filePath
     */
    public static void initKeystore( String filePath )
    {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try
        {
            keyPairGen = KeyPairGenerator.getInstance( "RSA" );
        }
        catch ( NoSuchAlgorithmException e )
        {

            e.printStackTrace();
        }
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize( 1024, new SecureRandom() );
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = ( RSAPrivateKey ) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = ( RSAPublicKey ) keyPair.getPublic();
        try
        {
            File testr = new File( filePath );

            if( !testr.exists() )
            {
                testr.mkdirs();
            }

            File testfpu = new File( filePath + File.separator + PUKN );
            File testfpr = new File( filePath + File.separator + PRKN );

            testfpu.delete();
            testfpr.delete();

            // 得到公钥字符串
            String publicKeyString = Base64.encode( publicKey.getEncoded() );
            // 得到私钥字符串
            String privateKeyString = Base64.encode( privateKey.getEncoded() );
            // 将密钥对写入到文件
            FileWriter pubfw = new FileWriter( filePath + File.separator + PUKN );
            FileWriter prifw = new FileWriter( filePath + File.separator + PRKN );
            BufferedWriter pubbw = new BufferedWriter( pubfw );
            BufferedWriter pribw = new BufferedWriter( prifw );
            pubbw.write( publicKeyString );
            pribw.write( privateKeyString );
            pubbw.flush();
            pubbw.close();
            pubfw.close();
            pribw.flush();
            pribw.close();
            prifw.close();
        }
        catch ( Exception e )
        {
            throw new RuntimeException( "密钥文件初始化异常", e );
        }
    }

    /**
     * 创建公锁
     * 
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    public static RSAPublicKey createPublicKey( String publicKeyStr )
        throws Exception
    {
        try
        {
            byte[] buffer = Base64.decode( publicKeyStr );
            KeyFactory keyFactory = KeyFactory.getInstance( "RSA" );
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec( buffer );
            return ( RSAPublicKey ) keyFactory.generatePublic( keySpec );
        }
        catch ( Exception e )
        {
            throw new RuntimeException( "公锁构建异常", e );
        }

    }

    /**
     * 创建私锁
     * 
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey createPrivateKey( String privateKeyStr )
        throws Exception
    {
        try
        {
            byte[] buffer = Base64.decode( privateKeyStr );
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec( buffer );
            KeyFactory keyFactory = KeyFactory.getInstance( "RSA" );
            return ( RSAPrivateKey ) keyFactory.generatePrivate( keySpec );
        }
        catch ( Exception e )
        {
            throw new RuntimeException( "私锁构建异常", e );
        }
    }

    /**
     * 公钥加密
     * 
     * @param publicKey
     * @param targetStr
     * @return
     * @throws Exception
     */
    public static byte[] encrypt( RSAPublicKey publicKey, byte[] targetStr )
        throws Exception
    {
        if( publicKey == null )
        {
            throw new RuntimeException( "公钥缺失" );
        }

        Cipher cipher = null;
        try
        {
            // 使用默认RSA
            cipher = Cipher.getInstance( "RSA" );
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init( Cipher.ENCRYPT_MODE, publicKey );
            byte[] output = cipher.doFinal( targetStr );
            return output;
        }
        catch ( Exception e )
        {
            throw new RuntimeException( "公钥加密异常", e );
        }
    }

    /**
     * 私钥加密过程
     * 
     * @param privateKey 私钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static byte[] encrypt( RSAPrivateKey privateKey, byte[] plainTextData )
        throws Exception
    {
        if( privateKey == null )
        {
            throw new RuntimeException( "私钥缺失" );
        }

        Cipher cipher = null;
        try
        {
            // 使用默认RSA
            cipher = Cipher.getInstance( "RSA" );
            cipher.init( Cipher.ENCRYPT_MODE, privateKey );
            byte[] output = cipher.doFinal( plainTextData );
            return output;
        }
        catch ( Exception e )
        {
            throw new RuntimeException( "私钥加密异常", e );
        }
    }

    /**
     * 私钥解密
     * 
     * @param privateKey
     * @param cipherData
     * @return
     * @throws Exception
     */
    public static byte[] decrypt( RSAPrivateKey privateKey, byte[] cipherData )
        throws Exception
    {
        if( privateKey == null )
        {
            throw new RuntimeException( "私钥缺失" );
        }

        Cipher cipher = null;
        try
        {
            // 使用默认RSA
            cipher = Cipher.getInstance( "RSA" );
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init( Cipher.DECRYPT_MODE, privateKey );
            byte[] output = cipher.doFinal( cipherData );
            return output;
        }
        catch ( Exception e )
        {
            throw new RuntimeException( "私钥解密异常", e );
        }
    }

    /**
     * 公钥解密过程
     * 
     * @param publicKey 公钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static byte[] decrypt( RSAPublicKey publicKey, byte[] cipherData )
        throws Exception
    {
        if( publicKey == null )
        {
            throw new RuntimeException( "公钥缺失" );
        }

        Cipher cipher = null;
        try
        {
            // 使用默认RSA
            cipher = Cipher.getInstance( "RSA" );
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init( Cipher.DECRYPT_MODE, publicKey );
            byte[] output = cipher.doFinal( cipherData );
            return output;
        }
        catch ( Exception e )
        {
            throw new RuntimeException( "公钥解密异常", e );
        }
    }

    /** *********************** 签名业务 ********************** */
    /**
     * RSA签名
     * 
     * @param content 待签名数据
     * @param privateKey 商户私钥
     * @param encode 字符集编码
     * @return 签名值
     */
    public static String sign( String content, String privateKey, String encode )
    {
        try
        {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec( Base64
                .decode( privateKey ) );

            KeyFactory keyf = KeyFactory.getInstance( "RSA" );
            PrivateKey priKey = keyf.generatePrivate( priPKCS8 );

            java.security.Signature signature = java.security.Signature
                .getInstance( SIGN_ALGORITHMS );

            signature.initSign( priKey );
            signature.update( content.getBytes( encode ) );

            byte[] signed = signature.sign();

            return Base64.encode( signed );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String sign( String content, String privateKey )
    {
        try
        {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec( Base64
                .decode( privateKey ) );
            KeyFactory keyf = KeyFactory.getInstance( "RSA" );
            PrivateKey priKey = keyf.generatePrivate( priPKCS8 );
            java.security.Signature signature = java.security.Signature
                .getInstance( SIGN_ALGORITHMS );
            signature.initSign( priKey );
            signature.update( content.getBytes() );
            byte[] signed = signature.sign();
            return Base64.encode( signed );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA验签名检查
     * 
     * @param content 待签名数据
     * @param sign 签名值
     * @param publicKey 分配给开发商公钥
     * @param encode 字符集编码
     * @return 布尔值
     */
    public static boolean doCheck( String content, String sign,
        String publicKey, String encode )
    {
        try
        {
            KeyFactory keyFactory = KeyFactory.getInstance( "RSA" );
            byte[] encodedKey = Base64.decode( publicKey );
            PublicKey pubKey = keyFactory
                .generatePublic( new X509EncodedKeySpec( encodedKey ) );

            java.security.Signature signature = java.security.Signature
                .getInstance( SIGN_ALGORITHMS );

            signature.initVerify( pubKey );
            signature.update( content.getBytes( encode ) );

            boolean bverify = signature.verify( Base64.decode( sign ) );
            return bverify;

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean doCheck( String content, String sign, String publicKey )
    {
        try
        {
            KeyFactory keyFactory = KeyFactory.getInstance( "RSA" );
            byte[] encodedKey = Base64.decode( publicKey );
            PublicKey pubKey = keyFactory
                .generatePublic( new X509EncodedKeySpec( encodedKey ) );

            java.security.Signature signature = java.security.Signature
                .getInstance( SIGN_ALGORITHMS );

            signature.initVerify( pubKey );
            signature.update( content.getBytes() );

            boolean bverify = signature.verify( Base64.decode( sign ) );
            return bverify;

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 字节数据转十六进制字符串
     * 
     * @param data 输入数据
     * @return 十六进制内容
     */
    public static String byteArrayToString( byte[] data )
    {
        StringBuilder stringBuilder = new StringBuilder();
        for ( int i = 0; i < data.length; i++ )
        {
            // 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
            stringBuilder.append( HEX_CHAR[( data[i] & 0xf0 ) >>> 4] );
            // 取出字节的低四位 作为索引得到相应的十六进制标识符
            stringBuilder.append( HEX_CHAR[( data[i] & 0x0f )] );
            if( i < data.length - 1 )
            {
                stringBuilder.append( ' ' );
            }
        }
        return stringBuilder.toString();
    }
}
