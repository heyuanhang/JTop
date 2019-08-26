package cn.com.mjsoft.cms.common.third.cloud.cos;

public class COSConfig
{
    private String appId;
    private String endpoint;
    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    private String location;

    public COSConfig( String appId, String endpoint, String accessKeyId, String accessKeySecret,
        String bucketName, String location )
    {

        this.appId = appId;
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
        this.location = location;
    }

    public String getEndpoint()
    {
        return endpoint;
    }

    public void setEndpoint( String endpoint )
    {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId()
    {
        return accessKeyId;
    }

    public void setAccessKeyId( String accessKeyId )
    {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret()
    {
        return accessKeySecret;
    }

    public void setAccessKeySecret( String accessKeySecret )
    {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName()
    {
        return bucketName;
    }

    public void setBucketName( String bucketName )
    {
        this.bucketName = bucketName;
    }

    public String getAppId()
    {
        return appId;
    }

    public void setAppId( String appId )
    {
        this.appId = appId;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation( String location )
    {
        this.location = location;
    }

}
