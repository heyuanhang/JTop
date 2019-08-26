package cn.com.mjsoft.cms.weixin.util.message.resp;

/**
 * 视频model
 * 
 * @author liufeng
 * @date 2013-09-11
 */
public class Video
{
    // 媒体文件id
    private String MediaId;
    // 缩略图的媒体id
    private String ThumbMediaId;

    private String Title;

    private String Description;

    public String getMediaId()
    {
        return MediaId;
    }

    public void setMediaId( String mediaId )
    {
        MediaId = mediaId;
    }

    public String getThumbMediaId()
    {
        return ThumbMediaId;
    }

    public void setThumbMediaId( String thumbMediaId )
    {
        ThumbMediaId = thumbMediaId;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setDescription( String description )
    {
        Description = description;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle( String title )
    {
        Title = title;
    }

}
