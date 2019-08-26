package cn.com.mjsoft.cms.stat.bean;


public class ContentTempTraceBean
{
    private Long contentId;

    private Integer imgCount = 0;

    private Integer videoCount = 0;

    public Long getContentId()
    {
        return contentId;
    }

    public void setContentId( Long contentId )
    {
        this.contentId = contentId;
    }

    public Integer getImgCount()
    {
        return imgCount;
    }

    public void setImgCount( Integer imgCount )
    {
        this.imgCount = imgCount;
    }

    public Integer getVideoCount()
    {
        return videoCount;
    }

    public void setVideoCount( Integer videoCount )
    {
        this.videoCount = videoCount;
    }
    
}
