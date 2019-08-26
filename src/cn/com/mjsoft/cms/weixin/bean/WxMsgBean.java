package cn.com.mjsoft.cms.weixin.bean;

public class WxMsgBean
{
    private Long msgId;

    private String inputKey;

    private Integer isInclude;

    private Integer isText;

    private String msgType;

    private Long infoId;

    private Long resId;

    private String textMsg;

    private String wxCode;

    public Long getInfoId()
    {
        return infoId;
    }

    public void setInfoId( Long infoId )
    {
        this.infoId = infoId;
    }

    public String getInputKey()
    {
        return inputKey;
    }

    public void setInputKey( String inputKey )
    {
        this.inputKey = inputKey;
    }

    public Integer getIsInclude()
    {
        return isInclude;
    }

    public void setIsInclude( Integer isInclude )
    {
        this.isInclude = isInclude;
    }

    public Long getMsgId()
    {
        return msgId;
    }

    public void setMsgId( Long msgId )
    {
        this.msgId = msgId;
    }

    public String getMsgType()
    {
        return msgType;
    }

    public void setMsgType( String msgType )
    {
        this.msgType = msgType;
    }

    public String getWxCode()
    {
        return wxCode;
    }

    public void setWxCode( String wxCode )
    {
        this.wxCode = wxCode;
    }

    public Integer getIsText()
    {
        return isText;
    }

    public void setIsText( Integer isText )
    {
        this.isText = isText;
    }

    public String getTextMsg()
    {
        return textMsg;
    }

    public void setTextMsg( String textMsg )
    {
        this.textMsg = textMsg;
    }

    public Long getResId()
    {
        return resId;
    }

    public void setResId( Long resId )
    {
        this.resId = resId;
    }
}
