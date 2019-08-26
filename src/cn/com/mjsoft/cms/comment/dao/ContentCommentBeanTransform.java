package cn.com.mjsoft.cms.comment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.comment.bean.CommentInfoBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class ContentCommentBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        CommentInfoBean bean = new CommentInfoBean();

        bean.setCommentId( Long.valueOf( rs.getLong( "commentId" ) ) );
        bean.setContentId( Long.valueOf( rs.getLong( "contentId" ) ) );
        bean.setClassId( Long.valueOf( rs.getLong( "classId" ) ) );
        bean.setMemberId( Long.valueOf( rs.getLong( "memberId" ) ) );
        bean.setModelId( Long.valueOf( rs.getLong( "modelId" ) ) );
        bean.setTypeId( Long.valueOf( rs.getLong( "typeId" ) ) );
        bean.setReplyId( Long.valueOf( rs.getLong( "replyId" ) ) );
        bean.setReplyTrace( rs.getString( "replyTrace" ) );
        bean.setUserName( rs.getString( "userName" ) );
        bean.setCommentText( rs.getString( "commentText" ) );
        bean.setCommDT( rs.getTimestamp( "commDT" ) );
        bean.setScore( Integer.valueOf( rs.getInt( "score" ) ) );
        bean.setSupportCount( Long.valueOf( rs.getLong( "supportCount" ) ) );
        bean.setAgainstCount( Long.valueOf( rs.getLong( "againstCount" ) ) );
        bean.setMoodFlag( Integer.valueOf( rs.getInt( "moodFlag" ) ) );
        bean.setIp( rs.getString( "ip" ) );
        bean.setCensorState( Integer.valueOf( rs.getInt( "censorState" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );

        return bean;

    }
}
