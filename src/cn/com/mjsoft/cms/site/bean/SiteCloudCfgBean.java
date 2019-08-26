package cn.com.mjsoft.cms.site.bean;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.third.cloud.cos.COSConfig;
import cn.com.mjsoft.cms.common.third.cloud.oss.OSSConfig;
import cn.com.mjsoft.cms.common.third.cloud.qiniu.QNConfig;

public class SiteCloudCfgBean
{
    private Long cloId;
    private String appId;
    private String accessKey;
    private String accessSecret;
    private String endPoint;
    private String accessUrl;
    private String bucketName;
    private String location;
    private String cloudType;
    private Long siteId;

    public Long getCloId()
    {
        return this.cloId;
    }

    public void setCloId( Long cloId )
    {
        this.cloId = cloId;
    }

    public String getAppId()
    {
        return this.appId;
    }

    public void setAppId( String appId )
    {
        this.appId = appId;
    }

    public String getAccessKey()
    {
        return this.accessKey;
    }

    public void setAccessKey( String accessKey )
    {
        this.accessKey = accessKey;
    }

    public String getAccessSecret()
    {
        return this.accessSecret;
    }

    public void setAccessSecret( String accessSecret )
    {
        this.accessSecret = accessSecret;
    }

    public String getEndPoint()
    {
        return this.endPoint;
    }

    public void setEndPoint( String endPoint )
    {
        this.endPoint = endPoint;
    }

    public String getBucketName()
    {
        return this.bucketName;
    }

    public void setBucketName( String bucketName )
    {
        this.bucketName = bucketName;
    }

    public String getLocation()
    {
        return this.location;
    }

    public void setLocation( String location )
    {
        this.location = location;
    }

    public String getCloudType()
    {
        return this.cloudType;
    }

    public void setCloudType( String cloudType )
    {
        this.cloudType = cloudType;
    }

    public String getAccessUrl()
    {
        return accessUrl;
    }

    public void setAccessUrl( String accessUrl )
    {
        this.accessUrl = accessUrl;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    // 业务方法
    public COSConfig toCOSCfg()
    {
        COSConfig cfg = new COSConfig( this.appId, this.endPoint, this.accessKey,
            this.accessSecret, this.bucketName, this.location );

        return cfg;
    }

    public OSSConfig toOSSCfg()
    {
        OSSConfig cfg = new OSSConfig( this.endPoint, this.accessKey, this.accessSecret,
            this.bucketName, this.location );

        return cfg;
    }

    public QNConfig toQNCfg()
    {
        QNConfig cfg = new QNConfig( this.accessKey, this.accessSecret, this.bucketName,
            this.location );

        return cfg;
    }
    
    public String getTypeStr()
    {
        if( Constant.RESOURCE.CLOUD_COS.equals( this.cloudType ) )
        {
             return "腾讯云";
        }
        else if( Constant.RESOURCE.CLOUD_OSS.equals( this.cloudType ) )
        {
            return "阿里云";
        }
        else if( Constant.RESOURCE.CLOUD_QN.equals( this.cloudType ) )
        {
            return "七牛云";
        }
        
        return "";
    }

}
