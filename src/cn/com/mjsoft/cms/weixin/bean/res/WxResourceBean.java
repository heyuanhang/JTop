package cn.com.mjsoft.cms.weixin.bean.res;

import java.sql.Timestamp;

import cn.com.mjsoft.cms.common.Constant;

public class WxResourceBean
{
    private Long wrId = Long.valueOf( -1 );

    private String resName;

    private String resTitle;

    private String resDesc;

    private String imageRes;

    private String videoRes;

    private String voiceRes;

    private String musicRes;

    private String resType;

    private String resTag = "";

    private Integer isTranSucc;

    private Timestamp tranDt;

    private String resContent;

    private String musicUrl;

    private String hqMusicUrl;

    private String musicThumb;

    private String thumbMediaId;

    private String thumbUrl;

    private String mediaId;

    private String mediaImgUrl;

    private String wxCode;

    private Long siteId = Long.valueOf( -1 );

    public Long getWrId()
    {
        return this.wrId;
    }

    public void setWrId( Long wrId )
    {
        this.wrId = wrId;
    }

    public String getImageRes()
    {
        return this.imageRes;
    }

    public void setImageRes( String imageRes )
    {
        this.imageRes = imageRes;
    }

    public String getVideoRes()
    {
        return this.videoRes;
    }

    public void setVideoRes( String videoRes )
    {
        this.videoRes = videoRes;
    }

    public String getVoiceRes()
    {
        return this.voiceRes;
    }

    public void setVoiceRes( String voiceRes )
    {
        this.voiceRes = voiceRes;
    }

    public String getMusicRes()
    {
        return this.musicRes;
    }

    public void setMusicRes( String musicRes )
    {
        this.musicRes = musicRes;
    }

    public String getResType()
    {
        return resType;
    }

    public void setResType( String resType )
    {
        this.resType = resType;
    }

    public String getResTag()
    {
        return this.resTag;
    }

    public void setResTag( String resTag )
    {
        this.resTag = resTag;
    }

    public Integer getIsTranSucc()
    {
        return this.isTranSucc;
    }

    public void setIsTranSucc( Integer isTranSucc )
    {
        this.isTranSucc = isTranSucc;
    }

    public Timestamp getTranDt()
    {
        return tranDt;
    }

    public void setTranDt( Timestamp tranDt )
    {
        this.tranDt = tranDt;
    }

    public String getMediaId()
    {
        return this.mediaId;
    }

    public void setMediaId( String mediaId )
    {
        this.mediaId = mediaId;
    }

    public String getMediaImgUrl()
    {
        return mediaImgUrl;
    }

    public void setMediaImgUrl( String mediaImgUrl )
    {
        this.mediaImgUrl = mediaImgUrl;
    }

    public String getWxCode()
    {
        return this.wxCode;
    }

    public void setWxCode( String wxCode )
    {
        this.wxCode = wxCode;
    }

    public Long getSiteId()
    {
        return this.siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public String getResName()
    {
        return resName;
    }

    public void setResName( String resName )
    {
        this.resName = resName;
    }

    public String getHqMusicUrl()
    {
        return hqMusicUrl;
    }

    public void setHqMusicUrl( String hqMusicUrl )
    {
        this.hqMusicUrl = hqMusicUrl;
    }

    public String getMusicUrl()
    {
        return musicUrl;
    }

    public void setMusicUrl( String musicUrl )
    {
        this.musicUrl = musicUrl;
    }

    public String getResDesc()
    {
        return resDesc;
    }

    public void setResDesc( String resDesc )
    {
        this.resDesc = resDesc;
    }

    public String getResTitle()
    {
        return resTitle;
    }

    public void setResTitle( String resTitle )
    {
        this.resTitle = resTitle;
    }

    public String getResContent()
    {
        return resContent;
    }

    public void setResContent( String resContent )
    {
        this.resContent = resContent;
    }

    public String getThumbMediaId()
    {
        return thumbMediaId;
    }

    public void setThumbMediaId( String thumbMediaId )
    {
        this.thumbMediaId = thumbMediaId;
    }

    public String getMusicThumb()
    {
        return musicThumb;
    }

    public void setMusicThumb( String musicThumb )
    {
        this.musicThumb = musicThumb;
    }

    public String getThumbUrl()
    {
        return thumbUrl;
    }

    public void setThumbUrl( String thumbUrl )
    {
        this.thumbUrl = thumbUrl;
    }

    public String getResTypeStr()
    {
        String resStr = "";

        if( Constant.WX.RESP_MESSAGE_TYPE_IMAGE.equals( resType ) )
        {
            resStr = "图片";
        }
        else if( Constant.WX.RESP_MESSAGE_TYPE_TEXT.equals( resType ) )
        {
            resStr = "文本";
        }
        if( Constant.WX.RESP_MESSAGE_TYPE_VIDEO.equals( resType ) )
        {
            resStr = "视频";
        }
        if( Constant.WX.RESP_MESSAGE_TYPE_VOICE.equals( resType ) )
        {
            resStr = "语音";
        }
        if( Constant.WX.RESP_MESSAGE_TYPE_MUSIC.equals( resType ) )
        {
            resStr = "音乐";
        }
        if( Constant.WX.RESP_MESSAGE_TYPE_NEWS.equals( resType ) )
        {
            resStr = "图文";
        }

        return resStr;
    }
}
