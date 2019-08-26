package cn.com.mjsoft.cms.message.dao.vo;

import java.util.Date;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "manager_message", id = "msgId", idMode = EntitySqlBridge.DB_IDENTITY )
public class ManagerMessage
{
    private Long msgId;
    private String msgTypeName;
    private String msgTitle;
    private String msgContent = "";
    private String replyContent;
    private Long sender;
    private Date sendDT;
    private Date replyDT;
    private Long userId;
    private Integer isRead = Constant.COMMON.OFF;
    private Integer isReply = Constant.COMMON.OFF;
    private Integer isReDelete = Constant.COMMON.OFF;
    private Integer isSeDelete = Constant.COMMON.OFF;

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

    public Date getReplyDT()
    {
        return replyDT;
    }

    public void setReplyDT( Date replyDT )
    {
        this.replyDT = replyDT;
    }

    public Date getSendDT()
    {
        return sendDT;
    }

    public void setSendDT( Date sendDT )
    {
        this.sendDT = sendDT;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId( Long userId )
    {
        this.userId = userId;
    }

    public Integer getIsRead()
    {
        return isRead;
    }

    public void setIsRead( Integer isRead )
    {
        this.isRead = isRead;
    }

    public Long getSender()
    {
        return sender;
    }

    public void setSender( Long sender )
    {
        this.sender = sender;
    }

    public Integer getIsReDelete()
    {
        return isReDelete;
    }

    public void setIsReDelete( Integer isReDelete )
    {
        this.isReDelete = isReDelete;
    }

    public Integer getIsReply()
    {
        return isReply;
    }

    public void setIsReply( Integer isReply )
    {
        this.isReply = isReply;
    }

    public Integer getIsSeDelete()
    {
        return isSeDelete;
    }

    public void setIsSeDelete( Integer isSeDelete )
    {
        this.isSeDelete = isSeDelete;
    }

    public String getReplyContent()
    {
        return replyContent;
    }

    public void setReplyContent( String replyContent )
    {
        this.replyContent = replyContent;
    }
}
