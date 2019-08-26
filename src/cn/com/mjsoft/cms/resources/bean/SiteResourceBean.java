package cn.com.mjsoft.cms.resources.bean;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SiteResourceBean implements Serializable
{
    private static final long serialVersionUID = 1543557418687797204L;

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

    private String cover;

    private Integer haveMark;

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

    // 业务方法

    public String getSizeStr()
    {
        return FileUtil.changeFileSizeToStr( this.resSize.longValue() );
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

    public String getUrl()
    {
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( siteId );

        if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( this.resType ) )
        {
            return site.getSiteImagePrefixUrl() + resSource;
        }

        if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( this.resType )
            || Constant.RESOURCE.MUSIC_RES_TYPE.equals( this.resType ) )
        {
            return site.getSiteMediaPrefixUrl() + resSource;
        }

        if( Constant.RESOURCE.DOC_RES_TYPE.equals( this.resType )
            || Constant.RESOURCE.ANY_RES_TYPE.equals( this.resType ) )
        {
            return site.getSiteFilePrefixUrl() + resSource;
        }

        return "";
    }

    /**
     * 获取资源完整物理路径
     * 
     * @return
     */
    public String getFullFilePath()
    {
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( siteId );

        String rootPath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String fileResSource = StringUtil.replaceString( resSource, "/", File.separator, false,
            false );

        if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( this.resType ) )
        {
            return rootPath + site.getSiteRoot() + File.separator + Constant.CONTENT.IMG_BASE
                + File.separator + fileResSource;
        }

        if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( this.resType )
            || Constant.RESOURCE.MUSIC_RES_TYPE.equals( this.resType ) )
        {
            return rootPath + site.getSiteRoot() + File.separator + Constant.CONTENT.MEDIA_BASE
                + File.separator + fileResSource;
        }

        if( Constant.RESOURCE.DOC_RES_TYPE.equals( this.resType )
            || Constant.RESOURCE.ANY_RES_TYPE.equals( this.resType ) )
        {
            return rootPath + site.getSiteRoot() + File.separator + Constant.CONTENT.FILE_BASE
                + File.separator + fileResSource;
        }

        return "";
    }

    /**
     * 获取资源完整物理路径（根据资源类型返回多种路径信息）
     * 
     * @return
     */
    public String[] getFullResFilePath()
    {
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( siteId );

        String rootPath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String fileResSource = StringUtil.replaceString( resSource, "/", File.separator, false,
            false );

        if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( this.resType ) )
        {
            String resizefileResSource = StringUtil.replaceString( resSource, "/", File.separator
                + "imgResize", false, false );

            return new String[] {
                rootPath + site.getSiteRoot() + File.separator + Constant.CONTENT.IMG_BASE
                    + File.separator + fileResSource,
                rootPath + site.getSiteRoot() + File.separator + Constant.CONTENT.IMG_BASE
                    + File.separator + resizefileResSource };
        }

        if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( this.resType )
            || Constant.RESOURCE.MUSIC_RES_TYPE.equals( this.resType ) )
        {
            return new String[] { rootPath + site.getSiteRoot() + File.separator
                + Constant.CONTENT.MEDIA_BASE + File.separator + fileResSource };
        }

        if( Constant.RESOURCE.DOC_RES_TYPE.equals( this.resType )
            || Constant.RESOURCE.ANY_RES_TYPE.equals( this.resType ) )
        {
            return new String[] { rootPath + site.getSiteRoot() + File.separator
                + Constant.CONTENT.FILE_BASE + File.separator + fileResSource };
        }

        return new String[] {};
    }

    public String getResizeImgUrl()
    {
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( siteId );

        if( site != null && Constant.RESOURCE.IMAGE_RES_TYPE.equals( this.resType ) )
        {
            return site.getSiteImagePrefixUrl()
                + StringUtil.replaceString( resSource, "/", "/imgResize", false, false );
        }

        return "";
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder( this ).append( "resId", this.resId ).append( "haveMark",
            this.haveMark ).append( "resType", this.resType )
            .append( "resolution", this.resolution ).append( "cover", this.cover ).append(
                "siteId", this.siteId ).append( "resName", this.resName ).append( "source",
                this.resSource ).append( "duration", this.duration ).append( "fileType",
                this.fileType ).append( "classId", this.classId ).append( "height", this.height )
            .append( "url", this.getUrl() ).append( "resizeImgUrl", this.getResizeImgUrl() )
            .append( "sizeStr", this.getSizeStr() ).append( "width", this.width ).append(
                "modifyTime", this.modifyTime ).append( "size", this.resSize ).toString();
    }

}
