package cn.com.mjsoft.cms.publish.bean;

import com.alibaba.fastjson.JSON;

import cn.com.mjsoft.cms.common.JSONString;

public class PublishStatusBean implements JSONString
{
    private Long pubCount = Long.valueOf( 0 );

    private Long current = Long.valueOf( 0 );

    private Long tranChannelCount = Long.valueOf( 0 );

    private Long tranChannelCurrent = Long.valueOf( 0 );

    private Long tranListCount = Long.valueOf( 0 );

    private Long tranListCurrent = Long.valueOf( 0 );

    private Long tranContentCount = Long.valueOf( 0 );

    private Long tranContentCurrent = Long.valueOf( 0 );

    private Long tranHomeCount = Long.valueOf( 0 );

    private Long tranHomeCurrent = Long.valueOf( 0 );

    private String currPubObj = "";

    private Long channelAllCount = Long.valueOf( 0 );

    private Long listAllCount = Long.valueOf( 0 );

    private Long contentAllCount = Long.valueOf( 0 );

    private Integer homeOperStatus = Integer.valueOf( 0 );

    private Integer channelOperStatus = Integer.valueOf( 0 );

    private Integer listOperStatus = Integer.valueOf( 0 );

    private Integer contentOperStatus = Integer.valueOf( 0 );

    private Integer homeTranStatus = Integer.valueOf( 0 );

    private Integer channelTranStatus = Integer.valueOf( 0 );

    private Integer listTranStatus = Integer.valueOf( 0 );

    private Integer contentTranStatus = Integer.valueOf( 0 );

    public Long getCurrent()
    {
        return current;
    }

    public void setCurrent( Long current )
    {
        this.current = current;
    }

    public Long getPubCount()
    {
        return pubCount;
    }

    public void setPubCount( Long pubCount )
    {
        this.pubCount = pubCount;
    }

    public Integer getChannelOperStatus()
    {
        return channelOperStatus;
    }

    public void setChannelOperStatus( Integer channelOperStatus )
    {
        this.channelOperStatus = channelOperStatus;
    }

    public Integer getContentOperStatus()
    {
        return contentOperStatus;
    }

    public void setContentOperStatus( Integer contentOperStatus )
    {
        this.contentOperStatus = contentOperStatus;
    }

    public String getCurrPubObj()
    {
        return currPubObj;
    }

    public void setCurrPubObj( String currPubObj )
    {
        this.currPubObj = currPubObj;
    }

    public Integer getHomeOperStatus()
    {
        return homeOperStatus;
    }

    public void setHomeOperStatus( Integer homeOperStatus )
    {
        this.homeOperStatus = homeOperStatus;
    }

    public Integer getListOperStatus()
    {
        return listOperStatus;
    }

    public void setListOperStatus( Integer listOperStatus )
    {
        this.listOperStatus = listOperStatus;
    }

    public Long getContentAllCount()
    {
        return contentAllCount;
    }

    public void setContentAllCount( Long contentAllCount )
    {
        this.contentAllCount = contentAllCount;
    }

    public Long getListAllCount()
    {
        return listAllCount;
    }

    public void setListAllCount( Long listAllCount )
    {
        this.listAllCount = listAllCount;
    }

    public Long getChannelAllCount()
    {
        return channelAllCount;
    }

    public void setChannelAllCount( Long channelAllCount )
    {
        this.channelAllCount = channelAllCount;
    }

    public Integer getChannelTranStatus()
    {
        return channelTranStatus;
    }

    public void setChannelTranStatus( Integer channelTranStatus )
    {
        this.channelTranStatus = channelTranStatus;
    }

    public Integer getContentTranStatus()
    {
        return contentTranStatus;
    }

    public void setContentTranStatus( Integer contentTranStatus )
    {
        this.contentTranStatus = contentTranStatus;
    }

    public Integer getHomeTranStatus()
    {
        return homeTranStatus;
    }

    public void setHomeTranStatus( Integer homeTranStatus )
    {
        this.homeTranStatus = homeTranStatus;
    }

    public Integer getListTranStatus()
    {
        return listTranStatus;
    }

    public void setListTranStatus( Integer listTranStatus )
    {
        this.listTranStatus = listTranStatus;
    }

    public Long getTranChannelCount()
    {
        return tranChannelCount;
    }

    public void setTranChannelCount( Long tranChannelCount )
    {
        this.tranChannelCount = tranChannelCount;
    }

    public Long getTranChannelCurrent()
    {
        return tranChannelCurrent;
    }

    public void setTranChannelCurrent( Long tranChannelCurrent )
    {
        this.tranChannelCurrent = tranChannelCurrent;
    }

    public Long getTranContentCount()
    {
        return tranContentCount;
    }

    public void setTranContentCount( Long tranContentCount )
    {
        this.tranContentCount = tranContentCount;
    }

    public Long getTranContentCurrent()
    {
        return tranContentCurrent;
    }

    public void setTranContentCurrent( Long tranContentCurrent )
    {
        this.tranContentCurrent = tranContentCurrent;
    }

    public Long getTranHomeCount()
    {
        return tranHomeCount;
    }

    public void setTranHomeCount( Long tranHomeCount )
    {
        this.tranHomeCount = tranHomeCount;
    }

    public Long getTranHomeCurrent()
    {
        return tranHomeCurrent;
    }

    public void setTranHomeCurrent( Long tranHomeCurrent )
    {
        this.tranHomeCurrent = tranHomeCurrent;
    }

    public Long getTranListCount()
    {
        return tranListCount;
    }

    public void setTranListCount( Long tranListCount )
    {
        this.tranListCount = tranListCount;
    }

    public Long getTranListCurrent()
    {
        return tranListCurrent;
    }

    public void setTranListCurrent( Long tranListCurrent )
    {
        this.tranListCurrent = tranListCurrent;
    }

    public String toJSON()
    {
        
        return JSON.toJSONString( this );

    }
}
