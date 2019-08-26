package cn.com.mjsoft.cms.weixin.bean;

import java.util.Date;

public class WxSendAllInfoBean
{

    private Long saId;

    private String msgType;

    private Long msgId;

    private String exeMan;

    private Date exeTime;

    private Date sendDT;

    private Integer isSend;

    private Integer sendTarget;

    private Integer censor;

    private String returnMsg;

    private String wxCode;

    private Long wxId;

    public Long getSaId()
    {
        return this.saId;
    }

    public void setSaId( Long saId )
    {
        this.saId = saId;
    }

    public String getMsgType()
    {
        return this.msgType;
    }

    public void setMsgType( String msgType )
    {
        this.msgType = msgType;
    }

    public Long getMsgId()
    {
        return msgId;
    }

    public void setMsgId( Long msgId )
    {
        this.msgId = msgId;
    }

    public String getExeMan()
    {
        return this.exeMan;
    }

    public void setExeMan( String exeMan )
    {
        this.exeMan = exeMan;
    }

    public Date getExeTime()
    {
        return this.exeTime;
    }

    public void setExeTime( Date exeTime )
    {
        this.exeTime = exeTime;
    }

    public Date getSendDT()
    {
        return sendDT;
    }

    public void setSendDT( Date sendDT )
    {
        this.sendDT = sendDT;
    }

    public Integer getCensor()
    {
        return this.censor;
    }

    public void setCensor( Integer censor )
    {
        this.censor = censor;
    }

    public Integer getSendTarget()
    {
        return sendTarget;
    }

    public void setSendTarget( Integer sendTarget )
    {
        this.sendTarget = sendTarget;
    }

    public Integer getIsSend()
    {
        return isSend;
    }

    public void setIsSend( Integer isSend )
    {
        this.isSend = isSend;
    }

    public String getReturnMsg()
    {
        return returnMsg;
    }

    public void setReturnMsg( String returnMsg )
    {
        this.returnMsg = returnMsg;
    }

    public String getWxCode()
    {
        return this.wxCode;
    }

    public void setWxCode( String wxCode )
    {
        this.wxCode = wxCode;
    }

    public Long getWxId()
    {
        return this.wxId;
    }

    public void setWxId( Long wxId )
    {
        this.wxId = wxId;
    }

    public String getMsgTypeFlag()
    {
        if( "图文".equals( this.msgType ) )
        {
            return "mpnews";
        }
        else if( "图片".equals( this.msgType ) )
        {
            return "image";
        }
        else if( "视频".equals( this.msgType ) )
        {
            return "mpvideo";
        }
        else if( "录音".equals( this.msgType ) )
        {
            return "voice";
        }
        else if( "文本".equals( this.msgType ) )
        {
            return "text";
        }

        return "";
    }

}
