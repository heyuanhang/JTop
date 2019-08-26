package cn.com.mjsoft.cms.comment.dao;

import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.comment.bean.CommentInfoBean;
import cn.com.mjsoft.cms.comment.bean.ContentMoodBean;
import cn.com.mjsoft.cms.comment.dao.vo.CommentInfo;
import cn.com.mjsoft.cms.comment.dao.vo.ContentMood;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;

public class CommentDao
{
    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public CommentDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public UpdateState saveComment( CommentInfo comment )
    {
        return pe.save( comment );
    }

    public ContentMoodBean querySingleContentMoodBeanById( Long contentId )
    {
        String sql = "select * from content_mood where contentId=?";
        return ( ContentMoodBean ) pe.querySingleRow( sql,
            new Object[] { contentId }, new ContentMoodBeanTransform() );
    }

    public Map querySingleContentStatus( Long contentId )
    {
        String sql = "select clickMonthCount, clickWeekCount, clickDayCount, clickCount, commMonthCount, commWeekCount, commDayCount, commCount, supportCount, againstCount from content_main_info where contentId=?";

        return pe.querySingleResultMap( sql, new Object[] { contentId } );
    }

    public void updateContentDiggSupportInfo( Long count, Long contentId )
    {
        String sql = "update content_main_info set supportCount=supportCount+? where contentId=?";
        pe.update( sql, new Object[] { count, contentId } );
    }

    public void updateCommentDiggSupportInfo( Long count, Long commentId )
    {
        String sql = "update comment_info set supportCount=supportCount+? where commentId=?";
        pe.update( sql, new Object[] { count, commentId } );
    }

    public void updateContentDiggAgainstInfo( Long count, Long contentId )
    {
        String sql = "update content_main_info set againstCount=againstCount+? where contentId=?";
        pe.update( sql, new Object[] { count, contentId } );
    }

    public void updateCommentDiggAgainstInfo( Long count, Long commentId )
    {
        String sql = "update comment_info set againstCount=supportCount+? where commentId=?";
        pe.update( sql, new Object[] { count, commentId } );
    }

    public void updateContentMoodInfo( Integer moodFlag, Long count,
        Long contentId )
    {
        String sql = "update content_main_info set moodT" + moodFlag
            + "Count=moodT" + moodFlag + "Count+? where contentId=?";
        pe.update( sql, new Object[] { count, contentId } );
    }

    public void saveContentMood( ContentMood vo )
    {
        pe.save( vo );
    }
    
    public List queryCommentBeanListByTypeId( Long contentId, Integer typeId, Long start,
        Integer size )
    {
        String sql = "select * from comment_info ci,(select commentId as id from comment_info where contentId=? and typeId=? order by commentId desc limit ?,?) cids where cids.id=ci.commentId order by ci.commentId desc";
        return pe.query( sql, new Object[] { contentId, typeId, start, size },
            new ContentCommentBeanTransform() );
    }
    
    public List queryCommentBeanListByTypeId( Long contentId, Integer typeId, Long start,
        Integer size, Integer status )
    {
        String sql = "select * from comment_info ci,(select commentId as id from comment_info where contentId=? and censorState=? and typeId=? order by commentId desc limit ?,?) cids where cids.id=ci.commentId order by ci.commentId desc";
        return pe.query( sql, new Object[] { contentId, status, typeId, start, size },
            new ContentCommentBeanTransform() );
    }
    

    public List queryCommentBeanListByContentId( Long contentId, Long start,
        Integer size )
    {
        String sql = "select * from comment_info ci,(select commentId as id from comment_info where contentId=? order by commentId desc limit ?,?) cids where cids.id=ci.commentId order by ci.commentId desc";
        return pe.query( sql, new Object[] { contentId, start, size },
            new ContentCommentBeanTransform() );
    }

    public List queryCommentBeanListByContentId( Long contentId, Long start,
        Integer size, Integer status )
    {
        String sql = "select * from comment_info ci,(select commentId as id from comment_info where contentId=? and censorState=? order by commentId desc limit ?,?) cids where cids.id=ci.commentId order by ci.commentId desc";
        return pe.query( sql, new Object[] { contentId, status, start, size },
            new ContentCommentBeanTransform() );
    }

    public CommentInfoBean querySingleCommentBeanByCommentId( Long commentId )
    {
        String sql = "select * from comment_info where commentId=?";
        return ( CommentInfoBean ) pe.querySingleRow( sql,
            new Object[] { commentId }, new ContentCommentBeanTransform() );
    }

    public void updateCommentBeanCensorState( Long commentId,
        Integer censorState )
    {
        String sql = "update comment_info set censorState=? where commentId=?";
        pe.update( sql, new Object[] { censorState, commentId } );
    }

    public void updateCommentBeanCensorStateByIdFlag( String inFlag,
        Integer censorState )
    {
        String sql = "update comment_info set censorState=? where commentId in ("
            + inFlag + ")";
        pe.update( sql, new Object[] { censorState } );
    }

    public List queryCommentBeanListByClassId( Long classId, Long start,
        Integer size )
    {
        String sql = "select * from comment_info ci,(select commentId as id from comment_info where classId=? order by commentId desc limit ?,?) cids where cids.id=ci.commentId order by ci.commentId desc";

        return pe.query( sql, new Object[] { classId, start, size },
            new ContentCommentBeanTransform() );
    }

    public List queryCommentBeanListByClassId( Long classId, Long start,
        Integer size, Integer status )
    {
        String sql = "select * from comment_info ci,(select commentId as id from comment_info where classId=? and censorState=? order by commentId desc limit ?,?) cids where cids.id=ci.commentId order by ci.commentId desc";

        return pe.query( sql, new Object[] { classId, status, start, size },
            new ContentCommentBeanTransform() );
    }

    public CommentInfoBean querySingeleCommentBean( Long commId )
    {
        String sql = "select * from comment_info where commentId=?";

        return ( CommentInfoBean ) pe.querySingleRow( sql,
            new Object[] { commId }, new ContentCommentBeanTransform() );
    }

    public List queryCommentBeanListByParentId( Long replyId )
    {
        String sql = "select * from comment_info ci,(select commentId as id from comment_info where replyId=?) cids where cids.id=ci.commentId order by ci.commentId asc";
        return pe.query( sql, new Object[] { replyId },
            new ContentCommentBeanTransform() );
    }

    public List queryAllCommentBeanListBySiteId( Long siteId, Long startPos,
        Integer limit )
    {
        // String sql = "select * from comment_info ci where ci.commentId in
        // (select commId.commentId from (select commentId from comment_info
        // order by commentId desc limit ?,? ) commId) order by ci.commentId
        // desc";

        String sql = "select * from comment_info ci,(select commentId as id from comment_info where siteId=? order by commentId desc limit ?,? ) cids where cids.id=ci.commentId order by ci.commentId desc";

        return pe.query( sql, new Object[] { siteId, startPos, limit },
            new ContentCommentBeanTransform() );
    }

    public List queryAllCommentBeanListBySiteId( Long siteId, Long startPos,
        Integer limit, Integer status )
    {
        // String sql = "select * from comment_info ci where ci.commentId in
        // (select commId.commentId from (select commentId from comment_info
        // order by commentId desc limit ?,? ) commId) order by ci.commentId
        // desc";

        String sql = "select * from comment_info ci,(select commentId as id from comment_info where siteId=? and censorState=? order by commentId desc limit ?,? ) cids where cids.id=ci.commentId order by ci.commentId desc";

        return pe.query( sql, new Object[] { siteId, status, startPos, limit },
            new ContentCommentBeanTransform() );
    }

    public List queryAllCommentBeanListBySiteIdAndUserName( Long siteId,
        String userName, Long startPos, Integer limit )
    {
        String sql = "select * from comment_info ci,(select commentId as id from comment_info where siteId=? and userName=? order by commentId desc limit ?,? ) cids where cids.id=ci.commentId order by ci.commentId desc";

        return pe.query( sql,
            new Object[] { siteId, userName, startPos, limit },
            new ContentCommentBeanTransform() );
    }

    public List queryCommentBeanListByIdInFlag( String inFlag )
    {
        String sql = "select * from comment_info ci,(select commentId as id from comment_info where commentId in ("
            + inFlag
            + ")) cids where cids.id=ci.commentId order by ci.commentId asc";
        return pe.query( sql, new ContentCommentBeanTransform() );
    }

    public void deleteCommentAllInfoByIdFlag( String inFlag )
    {
        String sql = "delete from comment_info where commentId in (" + inFlag
            + ")";

        pe.update( sql );
    }

    public void deleteCommentAllInfoBySiteId( Long siteId )
    {
        String sql = "delete from comment_info where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void saveCommentReplayText( Long commentId, String replayText )
    {
        String sql = "insert into comment_replay_text (commentId, replayText) values (?,?)";
        pe.update( sql, new Object[] { commentId, replayText } );
    }

    public void deleteCommentReplayText( Long commentId )
    {
        String sql = "delete from comment_replay_text where commentId=?";
        pe.update( sql, new Object[] { commentId } );
    }
    
    public Long queryCommentCountByTypeId( Long contentId, Integer typeId )
    {
        String sql = "select count(commentId) from comment_info where contentId=? and typeId=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { contentId, typeId },
            Long.class );
    }
    
    public Long queryCommentCountByTypeId( Long contentId, Integer typeId, Integer status )
    {
        String sql = "select count(commentId) from comment_info where contentId=? and typeId=? and censorState=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { contentId, typeId, status  },
            Long.class );
    }

    public Long queryCommentCountByClassId( Long classId )
    {
        String sql = "select count(commentId) from comment_info where classId=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { classId },
            Long.class );
    }

    public Long queryCommentCountByClassId( Long classId, Integer status )
    {
        String sql = "select count(commentId) from comment_info where classId=? and censorState=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { classId,
            status }, Long.class );
    }

    public Long queryCommentCountByContentId( Long contentId )
    {
        String sql = "select count(commentId) from comment_info where contentId=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { contentId },
            Long.class );
    }

    public Long queryCommentCountByContentId( Long contentId, Integer status )
    {
        String sql = "select count(commentId) from comment_info where contentId=? and censorState=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { contentId,
            status }, Long.class );
    }

    public Long queryCommentCount( Long siteId )
    {
        String sql = "select count(commentId) from comment_info where siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId },
            Long.class );
    }

    public Long queryCommentCount( Long siteId, Integer status )
    {
        String sql = "select count(commentId) from comment_info where siteId=? and censorState=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId,
            status }, Long.class );
    }

    public Long queryCommentCountByUserName( Long siteId, String userName )
    {
        String sql = "select count(commentId) from comment_info where siteId=? and userName=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId,
            userName }, Long.class );
    }

}
