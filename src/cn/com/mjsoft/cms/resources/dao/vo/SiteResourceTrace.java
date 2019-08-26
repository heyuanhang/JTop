package cn.com.mjsoft.cms.resources.dao.vo;

import java.sql.Timestamp;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "site_resource_upload_trace", id = "resId", idMode = EntitySqlBridge.ASSIGNED )
public class SiteResourceTrace
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
