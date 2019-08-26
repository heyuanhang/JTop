package cn.com.mjsoft.cms.common.third.cloud.cos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.model.DeleteObjectsRequest;
import com.qcloud.cos.model.DeleteObjectsResult;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.model.DeleteObjectsRequest.KeyVersion;
import com.qcloud.cos.model.DeleteObjectsResult.DeletedObject;
import com.qcloud.cos.region.Region;

public class COSUtil
{
    // 将本地文件上传到COS
    public static String simpleUploadFileFromLocal( COSConfig cfg, String file, String cosKeyPath )
    {
        if( cfg == null )
        {
            return null;
        }

        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials( cfg.getAccessKeyId(), cfg
            .getAccessKeySecret() );
        // 2 设置bucket的区域, COS地域的简称请参照
        // https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig( new Region( cfg.getLocation() ) );
        // 3 生成cos客户端
        COSClient cosclient = new COSClient( cred, clientConfig );
         
        // bucket名需包含appid
        String bucketName = cfg.getBucketName() + "-" + cfg.getAppId();

        File localFile = new File( file );

        if( !localFile.exists() )
        {
            return null;
        }
        
        String msg = "";

        PutObjectRequest putObjectRequest = new PutObjectRequest( bucketName, cosKeyPath, localFile );
        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia), 近线(nearline)
        putObjectRequest.setStorageClass( StorageClass.Standard_IA );
        try
        {
            PutObjectResult putObjectResult = cosclient.putObject( putObjectRequest );
            // putobjectResult会返回文件的etag
            String etag = putObjectResult.getETag();

            return etag;
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            
            msg = "Error Message: " + e.getMessage();
        }
        finally
        {
            // 关闭客户端
            cosclient.shutdown();

        }

        return msg;
    }

    // 删除单个文件(不带版本号, 即bucket未开启多版本)
    public static void delSingleFile( COSConfig cfg, String cosKeyPath )
    {
        if( cfg == null )
        {
            return;
        }

        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials( cfg.getAccessKeyId(), cfg
            .getAccessKeySecret() );
        // 2 设置bucket的区域, COS地域的简称请参照
        // https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig( new Region( cfg.getLocation() ) );
        // 3 生成cos客户端
        COSClient cosclient = new COSClient( cred, clientConfig );
        // bucket名需包含appid
        String bucketName = cfg.getBucketName() + "-" + cfg.getAppId();

        try
        {

            cosclient.deleteObject( bucketName, cosKeyPath );
        }
        catch ( Exception e )
        { // 如果是其他错误, 比如参数错误， 身份验证不过等会抛出CosServiceException
            e.printStackTrace();
        }
        finally
        {
            // 关闭客户端
            cosclient.shutdown();

        }

    }

    // 批量删除文件(不带版本号, 即bucket未开启多版本)
    public static List<DeletedObject> batchDelFile( COSConfig cfg, List<String> keys )
    {
        if( cfg == null )
        {
            return null;
        }

        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials( cfg.getAccessKeyId(), cfg
            .getAccessKeySecret() );
        // 2 设置bucket的区域, COS地域的简称请参照
        // https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig( new Region( cfg.getLocation() ) );
        // 3 生成cos客户端
        COSClient cosclient = new COSClient( cred, clientConfig );
        // bucket名需包含appid
        String bucketName = cfg.getBucketName() + "-" + cfg.getAppId();

        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest( bucketName );
        // 设置要删除的key列表, 最多一次删除1000个
        ArrayList<KeyVersion> keyList = new ArrayList<KeyVersion>();
        // 传入要删除的文件名

        for ( String key : keys )
        {
            keyList.add( new KeyVersion( key ) );
        }

        deleteObjectsRequest.setKeys( keyList );

        // 批量删除文件
        try
        {
            DeleteObjectsResult deleteObjectsResult = cosclient
                .deleteObjects( deleteObjectsRequest );
            List<DeletedObject> deleteObjectResultArray = deleteObjectsResult.getDeletedObjects();

            return deleteObjectResultArray;
        }
        catch ( MultiObjectDeleteException mde )
        { // 如果部分产出成功部分失败,
            // 返回MultiObjectDeleteException
            // List<DeletedObject> deleteObjects = mde.getDeletedObjects();
            // List<DeleteError> deleteErrors = mde.getErrors();
        }
        catch ( CosServiceException e )
        { // 如果是其他错误, 比如参数错误，
            // 身份验证不过等会抛出CosServiceException
            e.printStackTrace();
        }
        catch ( CosClientException e )
        { // 如果是客户端错误，比如连接不上COS
            e.printStackTrace();
        }
        finally
        {
            // 关闭客户端
            cosclient.shutdown();
        }
        return null;
    }

    // 批量删除带有版本号的文件(即bucket开启了多版本)
    public static List<DeletedObject> batchDelFileWithVersion( COSConfig cfg, List<String[]> keys )
    {
        if( cfg == null )
        {
            return null;
        }

        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials( cfg.getAccessKeyId(), cfg
            .getAccessKeySecret() );
        // 2 设置bucket的区域, COS地域的简称请参照
        // https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig( new Region( cfg.getLocation() ) );
        // 3 生成cos客户端
        COSClient cosclient = new COSClient( cred, clientConfig );
        // bucket名需包含appid
        String bucketName = cfg.getBucketName() + "-" + cfg.getAppId();

        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest( bucketName );
        // 设置要删除的key列表, 最多一次删除1000个
        ArrayList<KeyVersion> keyList = new ArrayList<KeyVersion>();
        // 传入要删除的文件名

        for ( String[] key : keys )
        {
            keyList.add( new KeyVersion( key[0], key[1] ) );
        }

        deleteObjectsRequest.setKeys( keyList );

        // 批量删除文件
        try
        {
            DeleteObjectsResult deleteObjectsResult = cosclient
                .deleteObjects( deleteObjectsRequest );
            List<DeletedObject> deleteObjectResultArray = deleteObjectsResult.getDeletedObjects();

            return deleteObjectResultArray;
        }
        catch ( MultiObjectDeleteException mde )
        { // 如果部分产出成功部分失败,
            // 返回MultiObjectDeleteException
            // List<DeletedObject> deleteObjects = mde.getDeletedObjects();
            // List<DeleteError> deleteErrors = mde.getErrors();
        }
        catch ( CosServiceException e )
        { // 如果是其他错误, 比如参数错误，
            // 身份验证不过等会抛出CosServiceException
            e.printStackTrace();
        }
        catch ( CosClientException e )
        { // 如果是客户端错误，比如连接不上COS
            e.printStackTrace();
        }
        finally
        {
            // 关闭客户端
            cosclient.shutdown();
        }

        return null;
    }
}
