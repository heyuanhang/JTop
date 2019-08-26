package cn.com.mjsoft.cms.resources.bean;

import java.sql.Timestamp;

public class SiteResourceTraceBean
{
    private Long resId = Long.valueOf( -1 );

    private Timestamp uploadDate;

    private Integer isUse;

    public Integer getIsUse()
    {
        return isUse;
    }

    public void setIsUse( Integer isUse )
    {
        this.isUse = isUse;
    }

    public Long getResId()
    {
        return resId;
    }

    public void setResId( Long resId )
    {
        this.resId = resId;
    }

    public Timestamp getUploadDate()
    {
        return uploadDate;
    }

    public void setUploadDate( Timestamp uploadDate )
    {
        this.uploadDate = uploadDate;
    }

}
