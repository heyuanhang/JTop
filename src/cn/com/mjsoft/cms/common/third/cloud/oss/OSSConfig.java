package cn.com.mjsoft.cms.common.third.cloud.oss;

public class OSSConfig
{
    private String endpoint;
    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    private String location;

    public OSSConfig( String endpoint, String accessKeyId, String accessKeySecret,
        String bucketName, String location )
    {
        super();
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

    public String getLocation()
    {
        return location;
    }

    public void setLocation( String location )
    {
        this.location = location;
    }

}
