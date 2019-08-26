package cn.com.mjsoft.cms.comment.dao.vo;

import java.util.Date;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "comment_info", id = "commentId", idMode = EntitySqlBridge.DB_IDENTITY )
public class CommentInfo
{

    private Long commentId = Long.valueOf( -1 );
    private Long contentId = Long.valueOf( -1 );
    private Long classId = Long.valueOf( -1 );
    private Long memberId = Long.valueOf( -1 );
    private Long modelId = Long.valueOf( -1 );
    private Long typeId = Long.valueOf( -1 );
    private Long replyId = Long.valueOf( -1 );;
    private String replyTrace = "";
    private String userName;
    private String commentText;
    private Date commDT;
    private Integer score;
    private Long supportCount = Long.valueOf( 0 );
    private Long againstCount = Long.valueOf( 0 );
    private Integer moodFlag;
    private String ip;
    private Integer censorState;
    private Long siteId;

    public Long getCommentId()
    {
        return this.commentId;
    }

    public void setCommentId( Long commentId )
    {
        this.commentId = commentId;
    }

    public Long getContentId()
    {
        return this.contentId;
    }

    public void setContentId( Long contentId )
    {
        this.contentId = contentId;
    }

    public Long getMemberId()
    {
        return this.memberId;
    }

    public void setMemberId( Long memberId )
    {
        this.memberId = memberId;
    }

    public Long getModelId()
    {
        return this.modelId;
    }

    public void setModelId( Long modelId )
    {
        this.modelId = modelId;
    }

    public Long getTypeId()
    {
        return this.typeId;
    }

    public void setTypeId( Long typeId )
    {
        this.typeId = typeId;
    }

    public Long getReplyId()
    {
        return replyId;
    }

    public void setReplyId( Long replyId )
    {
        this.replyId = replyId;
    }

    public String getReplyTrace()
    {
        return replyTrace;
    }

    public void setReplyTrace( String replyTrace )
    {
        this.replyTrace = replyTrace;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public String getCommentText()
    {
        return this.commentText;
    }

    public void setCommentText( String commentText )
    {
        this.commentText = commentText;
    }

    public Date getCommDT()
    {
        return commDT;
    }

    public void setCommDT( Date commDT )
    {
        this.commDT = commDT;
    }

    public Integer getScore()
    {
        return this.score;
    }

    public void setScore( Integer score )
    {
        this.score = score;
    }

    public Long getSupportCount()
    {
        return this.supportCount;
    }

    public void setSupportCount( Long supportCount )
    {
        this.supportCount = supportCount;
    }

    public Long getAgainstCount()
    {
        return this.againstCount;
    }

    public void setAgainstCount( Long againstCount )
    {
        this.againstCount = againstCount;
    }

    public String getIp()
    {
        return this.ip;
    }

    public void setIp( String ip )
    {
        this.ip = ip;
    }

    public Integer getCensorState()
    {
        return censorState;
    }

    public void setCensorState( Integer censorState )
    {
        this.censorState = censorState;
    }

    public Integer getMoodFlag()
    {
        return moodFlag;
    }

    public void setMoodFlag( Integer moodFlag )
    {
        this.moodFlag = moodFlag;
    }

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }
}
