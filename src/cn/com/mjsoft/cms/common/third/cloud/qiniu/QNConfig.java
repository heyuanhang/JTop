package cn.com.mjsoft.cms.common.third.cloud.qiniu;

import com.qiniu.common.Zone;

public class QNConfig
{

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    private String location;

    public QNConfig( String accessKeyId, String accessKeySecret, String bucketName, String location )
    {

        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
        this.location = location;
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

    public Zone zone()
    {
        if( "0".equals( this.location ) )
        {
            return Zone.zone0();
        }
        else if( "1".equals( this.location ) )
        {
            return Zone.zone1();
        }
        else if( "2".equals( this.location ) )
        {
            return Zone.zone2();
        }
        else if( "n".equals( this.location ) )
        {
            return Zone.zoneNa0();
        }
        return null;
    }
}
