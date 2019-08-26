package cn.com.mjsoft.cms.behavior;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.security.crypto.encrypt.Base64;
import cn.com.mjsoft.framework.security.crypto.encrypt.RSAEncryptor;
import cn.com.mjsoft.framework.security.crypto.util.EncodeOne;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.StringUtil;

public class InitRSABehavior implements Behavior
{
    private static Logger log = Logger.getLogger( InitRSABehavior.class );

    private static final String KEY_STORE_FILE_ROOT = "WEB-INF" + File.separator + "config"
        + File.separator + "security";

    private static final String PUBLIC_KEY_STORE_FILE = "WEB-INF" + File.separator + "config"
        + File.separator + "security" + File.separator + "publicKey.ks";

    private static final String PRIVATE_KEY_STORE_FILE = "WEB-INF" + File.separator + "config"
        + File.separator + "security" + File.separator + "privateKey.ks";

    public Object operation( Object target, Object[] param )
    {
        String basePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        RSAEncryptor.initKeystore( basePath + File.separator + KEY_STORE_FILE_ROOT );

        return null;
    }

    /**
     * 获取公锁
     * 
     * @return
     */
    public static String getPublicKey()
    {
        String basePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String puKs = ( String ) FileUtil.readTXTFileContent( new File( basePath + File.separator
            + PUBLIC_KEY_STORE_FILE ), "UTF-8" )[0];

        return puKs;
    }

    /**
     * 获取私锁
     * 
     * @return
     */
    public static String getPrivateKey()
    {
        String basePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String prKs = ( String ) FileUtil.readTXTFileContent( new File( basePath + File.separator
            + PRIVATE_KEY_STORE_FILE ), "UTF-8" )[0];

        return prKs;
    }

    public static String encryptPublic( String ks, String targetStr )
    {
        if( StringUtil.isStringNull( targetStr ) )
        {
            return "";
        }

        byte[] cipherData = null;
        String cipher = null;
        try
        {
            cipherData = RSAEncryptor.encrypt( RSAEncryptor.createPublicKey( ks ), targetStr
                .getBytes() );

            cipher = Base64.encode( cipherData );
        }
        catch ( Exception e )
        {
            log.error( "公锁加密失败：" + targetStr );
            // e.printStackTrace();
        }

        return cipher;

    }

    public static String encryptPublic( File ksFile, String targetStr )
    {
        return encryptPublic( ( String ) FileUtil.readTXTFileContent( ksFile, "UTF-8" )[0],
            targetStr );
    }

    public static String decryptPrivate( String ks, String targetCipher )
    {
        if( StringUtil.isStringNull( targetCipher ) )
        {
            return "";
        }

        byte[] res = null;
        try
        {
            res = RSAEncryptor.decrypt( RSAEncryptor.createPrivateKey( ks ), Base64
                .decode( targetCipher ) );
        }
        catch ( Exception e )
        {
            log.error( "私锁解密失败：" + targetCipher );
            // e.printStackTrace();
        }

        return res == null ? "" : new String( res );
    }

    public static String decryptPrivate( File ksFile, String targetStr )
    {
        return decryptPrivate( ( String ) FileUtil.readTXTFileContent( ksFile, "UTF-8" )[0],
            targetStr );
    }

    public static String encryptPrivate( String ks, String targetStr )
    {
        if( StringUtil.isStringNull( targetStr ) )
        {
            return "";
        }

        byte[] cipherData = null;
        String cipher = null;
        try
        {
            cipherData = RSAEncryptor.encrypt( RSAEncryptor.createPrivateKey( ks ), targetStr
                .getBytes() );

            cipher = Base64.encode( cipherData );
        }
        catch ( Exception e )
        {
            log.error( "私锁加密失败：" + targetStr );
            // e.printStackTrace();
        }

        return cipher;

    }

    public static String encryptPrivate( File ksFile, String targetStr )
    {
        return encryptPrivate( ( String ) FileUtil.readTXTFileContent( ksFile, "UTF-8" )[0],
            targetStr );
    }

    public static String decryptPublic( String ks, String targetCipher )
    {
        if( StringUtil.isStringNull( targetCipher ) )
        {
            return "";
        }

        byte[] res = null;
        try
        {
            res = RSAEncryptor.decrypt( RSAEncryptor.createPublicKey( ks ), Base64
                .decode( targetCipher ) );
        }
        catch ( Exception e )
        {
            log.error( "公锁解密失败：" + targetCipher );
            // e.printStackTrace();
        }

        return res == null ? "" : new String( res );
    }

    public static String decryptPublic( File ksFile, String targetStr )
    {
        return decryptPublic( ( String ) FileUtil.readTXTFileContent( ksFile, "UTF-8" )[0],
            targetStr );
    }

    public static String encodeB64AES( String pw, String sa )
    {
        byte[] encryptResult = null;

        try
        {
            encryptResult = EncodeOne.encryptAES( pw, sa );
        }
        catch ( Exception e )
        {

        }

        return encryptResult != null ? Base64.encode( encryptResult ) : pw;
    }

    public static String decodeB64AES( String pw, String sa )
    {
        byte[] decryptResult = null;

        String res = null;

        try
        {
            decryptResult = EncodeOne.decryptAES( Base64.decode( pw ), sa );
        }
        catch ( Exception e )
        {

        }

        try
        {
            res = decryptResult != null ? new String( decryptResult, "utf-8" ) : pw;
        }
        catch ( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }

        return res;
    }

}
