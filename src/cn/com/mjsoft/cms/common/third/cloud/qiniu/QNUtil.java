package cn.com.mjsoft.cms.common.third.cloud.qiniu;

import java.io.File;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

public class QNUtil
{
    public static String uploadFile( QNConfig cfg, String filePath, String key )
    {
        File file = new File( filePath );

        if( !file.exists() )
        {
            return "";
        }

        Configuration cfgObj = new Configuration( cfg.zone() );

        UploadManager uploadManager = new UploadManager( cfgObj );

        Auth auth = Auth.create( cfg.getAccessKeyId(), cfg.getAccessKeySecret() );
        String upToken = auth.uploadToken( cfg.getBucketName() );

        String msg = "";
        
        try
        {
            Response response = uploadManager.put( filePath, key, upToken );
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson( response.bodyString(), DefaultPutRet.class );
            System.out.println( putRet.key );
            System.out.println( putRet.hash );

            return putRet.toString();

        }
        catch ( QiniuException ex )
        {
            Response r = ex.response;
            System.err.println( r.toString() );
            try
            {
                System.err.println( r.bodyString() );
                
                msg =  "Error Message: " + r.bodyString();
            }
            catch ( QiniuException ex2 )
            {

            }
            
          
        }
        
        return msg;

    }

    public static void deleteFile( QNConfig cfg, String key )
    {

        Configuration cfgObj = new Configuration( cfg.zone() );

        Auth auth = Auth.create( cfg.getAccessKeyId(), cfg.getAccessKeySecret() );

        try
        {

            BucketManager bucketManager = new BucketManager( auth, cfgObj );

            bucketManager.delete( cfg.getBucketName(), key );

        }
        catch ( QiniuException ex )
        {           
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

}
