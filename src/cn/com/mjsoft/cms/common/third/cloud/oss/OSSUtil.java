package cn.com.mjsoft.cms.common.third.cloud.oss;

import java.io.File;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;

public class OSSUtil
{
    public static String uploadFile( OSSConfig cfg, String filePath, String key )
    {
        File file = new File(filePath);
        
        if( !file.exists() )
        {
            return "";
        }

        String msg = "";
        
        OSSClient ossClient = null;
        try
        {
            ossClient = new OSSClient( cfg.getEndpoint(), cfg.getAccessKeyId(), cfg
                .getAccessKeySecret() );

            return ossClient.putObject( new PutObjectRequest( cfg.getBucketName(), key, file ) )
                .getETag();

        }
        catch ( OSSException oe )
        {
            System.out.println( "Caught an OSSException, which means your request made it to OSS, "
                + "but was rejected with an error response for some reason." );
            System.out.println( "Error Message: " + oe.getErrorCode() );
            System.out.println( "Error Code:       " + oe.getErrorCode() );
            System.out.println( "Request ID:      " + oe.getRequestId() );
            System.out.println( "Host ID:           " + oe.getHostId() );
        }
        catch ( ClientException ce )
        {
            System.out.println( "Caught an ClientException, which means the client encountered "
                + "a serious internal problem while trying to communicate with OSS, "
                + "such as not being able to access the network." );
            System.out.println( "Error Message: " + ce.getMessage() );
            
            msg = "Error Message: " + ce.getMessage();
        }
        finally
        {
            /*
             * Do not forget to shut down the client finally to release all
             * allocated resources.
             */
            ossClient.shutdown();
        }
        return msg;

    }

    public static void deleteFile( OSSConfig cfg, String key )
    {

        OSSClient ossClient = null;
        try
        {
            ossClient = new OSSClient( cfg.getEndpoint(), cfg.getAccessKeyId(), cfg
                .getAccessKeySecret() );

            ossClient.deleteObject( cfg.getBucketName(), key );

        }
        catch ( OSSException oe )
        {
            System.out.println( "Caught an OSSException, which means your request made it to OSS, "
                + "but was rejected with an error response for some reason." );
            System.out.println( "Error Message: " + oe.getErrorCode() );
            System.out.println( "Error Code:       " + oe.getErrorCode() );
            System.out.println( "Request ID:      " + oe.getRequestId() );
            System.out.println( "Host ID:           " + oe.getHostId() );
        }
        catch ( ClientException ce )
        {
            System.out.println( "Caught an ClientException, which means the client encountered "
                + "a serious internal problem while trying to communicate with OSS, "
                + "such as not being able to access the network." );
            System.out.println( "Error Message: " + ce.getMessage() );
        }
        finally
        {
            /*
             * Do not forget to shut down the client finally to release all
             * allocated resources.
             */
            ossClient.shutdown();
        }

    }

    public static void copy( OSSConfig cfg, String fromBucket, String toBucket, String key,
        String newKey )
    {

        OSSClient ossClient = null;
        try
        {
            ossClient = new OSSClient( cfg.getEndpoint(), cfg.getAccessKeyId(), cfg
                .getAccessKeySecret() );

            if( newKey == null )
            {
                newKey = key;
            }

            ossClient.copyObject( fromBucket, key, toBucket, newKey );

        }
        catch ( OSSException oe )
        {
            System.out.println( "Caught an OSSException, which means your request made it to OSS, "
                + "but was rejected with an error response for some reason." );
            System.out.println( "Error Message: " + oe.getErrorCode() );
            System.out.println( "Error Code:       " + oe.getErrorCode() );
            System.out.println( "Request ID:      " + oe.getRequestId() );
            System.out.println( "Host ID:           " + oe.getHostId() );
        }
        catch ( ClientException ce )
        {
            System.out.println( "Caught an ClientException, which means the client encountered "
                + "a serious internal problem while trying to communicate with OSS, "
                + "such as not being able to access the network." );
            System.out.println( "Error Message: " + ce.getMessage() );
        }
        finally
        {
            /*
             * Do not forget to shut down the client finally to release all
             * allocated resources.
             */
            ossClient.shutdown();
        }
    }

    
}
