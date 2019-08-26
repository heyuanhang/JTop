package cn.com.mjsoft.cms.message.dao;

import java.sql.Timestamp;
import java.util.List;

import cn.com.mjsoft.cms.message.bean.ManagerMessageBean;
import cn.com.mjsoft.cms.message.dao.vo.ManagerMessage;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;

public class MessageDao
{
    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public MessageDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public void save( ManagerMessage msg )
    {
        pe.save( msg );
    }

    public ManagerMessageBean querySingleMessageBean( String userId )
    {
        String sql = "select * from manager_message where msgId=?";

        return ( ManagerMessageBean ) pe.querySingleRow( sql,
            new Object[] { userId }, new ManagerMessageBeanTransform() );
    }

    public Long querySeMessageBeanCount( String userId )
    {
        String sql = "select count(*) from manager_message where sender=? and isSeDelete=0";

        return ( Long ) pe.querySingleObject( sql, new Object[] { userId },
            Long.class );
    }

    public Long querySeMessageBeanCountForNotRead( String userId )
    {
        String sql = "select count(*) from manager_message where sender=? and isRead=0 and isSeDelete=0";

        return ( Long ) pe.querySingleObject( sql, new Object[] { userId },
            Long.class );
    }

    public Long querySeMessageBeanCountForRead( String userId )
    {
        String sql = "select count(*) from manager_message where sender=? and isRead=1 and isReply=0 and isRead=0 and isSeDelete=0";

        return ( Long ) pe.querySingleObject( sql, new Object[] { userId },
            Long.class );
    }

    public Long querySeMessageBeanCountForReply( String userId )
    {
        String sql = "select count(*) from manager_message where sender=? and isReply=1 and isRead=0 and isSeDelete=0";

        return ( Long ) pe.querySingleObject( sql, new Object[] { userId },
            Long.class );
    }

    public List querySeMessageBeanList( String userId, Long start, Integer size )
    {
        String sql = "select * from manager_message where sender=? and isSeDelete=0 order by msgId desc limit ?,?";

        return pe.query( sql, new Object[] { userId, start, size },
            new ManagerMessageBeanTransform() );
    }

    public List querySeMessageBeanListForNotRead( String userId, Long start,
        Integer size )
    {
        String sql = "select * from manager_message where sender=? and isRead=0 and isSeDelete=0 order by msgId desc limit ?,?";

        return pe.query( sql, new Object[] { userId, start, size },
            new ManagerMessageBeanTransform() );
    }

    public List querySeMessageBeanListForRead( String userId, Long start,
        Integer size )
    {
        String sql = "select * from manager_message where sender=? and isRead=1 and isReply=0 and isSeDelete=0 order by msgId desc limit ?,?";

        return pe.query( sql, new Object[] { userId, start, size },
            new ManagerMessageBeanTransform() );
    }

    public List querySeMessageBeanListForReply( String userId, Long start,
        Integer size )
    {
        String sql = "select * from manager_message where sender=? and isReply=1 and isSeDelete=0 order by msgId desc limit ?,?";

        return pe.query( sql, new Object[] { userId, start, size },
            new ManagerMessageBeanTransform() );
    }

    public Long queryReMessageBeanCount( String userId )
    {
        String sql = "select count(*) from manager_message where userId=? and isReDelete=0";

        return ( Long ) pe.querySingleObject( sql, new Object[] { userId },
            Long.class );
    }

    public Long queryReMessageBeanCountForNotRead( String userId )
    {
        String sql = "select count(*) from manager_message where userId=? and isRead=0 and isReDelete=0";

        return ( Long ) pe.querySingleObject( sql, new Object[] { userId },
            Long.class );
    }

    public Long queryReMessageBeanCountForRead( String userId )
    {
        String sql = "select count(*) from manager_message where userId=? and isRead=1 and isReply=0 and isReDelete=0";

        return ( Long ) pe.querySingleObject( sql, new Object[] { userId },
            Long.class );
    }

    public Long queryReMessageBeanCountForReply( String userId )
    {
        String sql = "select count(*) from manager_message where userId=? and isReply=1 and isReDelete=0";

        return ( Long ) pe.querySingleObject( sql, new Object[] { userId },
            Long.class );
    }

    public List queryReMessageBeanList( String userId, Long start, Integer size )
    {
        String sql = "select * from manager_message where userId=? and isReDelete=0 order by msgId desc limit ?,?";

        return pe.query( sql, new Object[] { userId, start, size },
            new ManagerMessageBeanTransform() );
    }

    public List queryReMessageBeanListForNotRead( String userId, Long start,
        Integer size )
    {
        String sql = "select * from manager_message where userId=? and isRead=0 and isReDelete=0 order by msgId desc limit ?,?";

        return pe.query( sql, new Object[] { userId, start, size },
            new ManagerMessageBeanTransform() );
    }

    public Long queryReMessageCountForNotRead( Long userId )
    {
        String sql = "select count(*) from manager_message where userId=? and isRead=0 and isReDelete=0";

        return ( Long ) pe.querySingleObject( sql, new Object[] { userId },
            Long.class );
    }

    public List queryReMessageBeanListForRead( String userId, Long start,
        Integer size )
    {
        String sql = "select * from manager_message where userId=? and isRead=1 and isReply=0 and isReDelete=0 order by msgId desc limit ?,?";

        return pe.query( sql, new Object[] { userId, start, size },
            new ManagerMessageBeanTransform() );
    }

    public List queryReMessageBeanListForReply( String userId, Long start,
        Integer size )
    {
        String sql = "select * from manager_message where userId=? and isReply=1 and isReDelete=0 order by msgId desc limit ?,?";

        return pe.query( sql, new Object[] { userId, start, size },
            new ManagerMessageBeanTransform() );
    }

    public void updateReadMsgStatus( Long msgId, Integer status )
    {
        String sql = "update manager_message set isRead=? where msgId=?";

        pe.update( sql, new Object[] { status, msgId } );
    }

    public void updateReplyMsgStatus( Long msgId, Integer status )
    {
        String sql = "update manager_message set isReply=? where msgId=?";

        pe.update( sql, new Object[] { status, msgId } );
    }

    public void updateSeMsgDeleteStatus( Long msgId, Integer status )
    {
        String sql = "update manager_message set isSeDelete=? where msgId=?";

        pe.update( sql, new Object[] { status, msgId } );
    }

    public void updateReMsgDeleteStatus( Long msgId, Integer status )
    {
        String sql = "update manager_message set isReDelete=? where msgId=?";

        pe.update( sql, new Object[] { status, msgId } );
    }

    public void updateMsgReplyContent( Long msgId, String content,
        Timestamp replyDT )
    {
        String sql = "update manager_message set replyContent=?, replyDT=? where msgId=?";

        pe.update( sql, new Object[] { content, replyDT, msgId } );
    }

    public void updateSeMessageDeleteStatus( Long msgId, Integer status )
    {
        String sql = "update manager_message set isSeDelete=? where msgId=?";

        pe.update( sql, new Object[] { status, msgId } );
    }

    public void updateReMessageDeleteStatus( Long msgId, Integer status )
    {
        String sql = "update manager_message set isReDelete=? where msgId=?";

        pe.update( sql, new Object[] { status, msgId } );
    }

    public void deleteMessageAllDeleteStatus()
    {
        String sql = "delete from manager_message where isReDelete=1 and isSeDelete=1";

        pe.update( sql );
    }

    public void deleteSystemMessage( Long msgId )
    {
        String sql = "delete from manager_message where msgId=? and sender<1";

        pe.update( sql, new Object[] { msgId } );
    }
}
