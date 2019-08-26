package cn.com.mjsoft.cms.resources.dao.vo;

import java.sql.Timestamp;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "site_resource", id = "resId", idMode = EntitySqlBridge.DB_IDENTITY )
public class SiteResource
{

    private Long resId = Long.valueOf( -1 );

    private Long classId = Long.valueOf( -1 );

    private Long siteId = Long.valueOf( -1 );

    private String fileType;

    private Integer resType;

    private String resName;

    private String resSource;

    private Long resSize;

    private Integer width;

    private Integer height;

    private Integer duration;

    private String resolution;

    private Integer haveMark = Integer.valueOf( 0 );

    private String cover;

    private Timestamp modifyTime;

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public String getFileType()
    {
        return fileType;
    }

    public void setFileType( String fileType )
    {
        this.fileType = fileType;
    }

    public Integer getHeight()
    {
        return height;
    }

    public void setHeight( Integer height )
    {
        this.height = height;
    }

    public Timestamp getModifyTime()
    {
        return modifyTime;
    }

    public void setModifyTime( Timestamp modifyTime )
    {
        this.modifyTime = modifyTime;
    }

    public Long getResId()
    {
        return resId;
    }

    public void setResId( Long resId )
    {
        this.resId = resId;
    }

    public String getResName()
    {
        return resName;
    }

    public void setResName( String resName )
    {
        this.resName = resName;
    }

    public Long getResSize()
    {
        return resSize;
    }

    public void setResSize( Long resSize )
    {
        this.resSize = resSize;
    }

    public String getResSource()
    {
        return resSource;
    }

    public void setResSource( String resSource )
    {
        this.resSource = resSource;
    }

    public Integer getWidth()
    {
        return width;
    }

    public void setWidth( Integer width )
    {
        this.width = width;
    }

    public Integer getResType()
    {
        return resType;
    }

    public void setResType( Integer resType )
    {
        this.resType = resType;
    }

    public Integer getDuration()
    {
        return duration;
    }

    public void setDuration( Integer duration )
    {
        this.duration = duration;
    }

    public String getResolution()
    {
        return resolution;
    }

    public void setResolution( String resolution )
    {
        this.resolution = resolution;
    }

    public String getCover()
    {
        return cover;
    }

    public void setCover( String cover )
    {
        this.cover = cover;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public Integer getHaveMark()
    {
        return haveMark;
    }

    public void setHaveMark( Integer haveMark )
    {
        this.haveMark = haveMark;
    }
}
