package cn.com.mjsoft.cms.member.dao.vo;

import java.util.Date;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "member_message", id = "msgId", idMode = EntitySqlBridge.DB_IDENTITY )
public class MemberMessage
{

    private Long msgId;
    private String msgTypeName;
    private String msgTitle;
    private String msgContent;
    private Date sendDt;
    private Long memberId;
    private Integer isSys;
    private Integer isRead;

    public Long getMsgId()
    {
        return this.msgId;
    }

    public void setMsgId( Long msgId )
    {
        this.msgId = msgId;
    }

    public String getMsgTypeName()
    {
        return this.msgTypeName;
    }

    public void setMsgTypeName( String msgTypeName )
    {
        this.msgTypeName = msgTypeName;
    }

    public String getMsgTitle()
    {
        return this.msgTitle;
    }

    public void setMsgTitle( String msgTitle )
    {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent()
    {
        return this.msgContent;
    }

    public void setMsgContent( String msgContent )
    {
        this.msgContent = msgContent;
    }

    public Date getSendDt()
    {
        return this.sendDt;
    }

    public void setSendDt( Date sendDt )
    {
        this.sendDt = sendDt;
    }

    public Long getMemberId()
    {
        return this.memberId;
    }

    public void setMemberId( Long memberId )
    {
        this.memberId = memberId;
    }

    public Integer getIsRead()
    {
        return isRead;
    }

    public void setIsRead( Integer isRead )
    {
        this.isRead = isRead;
    }

    public Integer getIsSys()
    {
        return isSys;
    }

    public void setIsSys( Integer isSys )
    {
        this.isSys = isSys;
    }

}
