package cn.com.mjsoft.cms.message.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.message.dao.MessageDao;
import cn.com.mjsoft.cms.message.dao.vo.ManagerMessage;
import cn.com.mjsoft.cms.security.bean.SystemUserBean;
import cn.com.mjsoft.cms.security.dao.SecurityDao;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;

public class MessageService
{
    private static Logger log = Logger.getLogger( MessageService.class );

    private static MessageService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private MessageDao messageDao;

    private SecurityDao securityDao;

    private MessageService()
    {
        messageDao = new MessageDao( mysqlEngine );

        securityDao = new SecurityDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new MessageService();
        }
    }

    public static MessageService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }
    
    public int sendManagerMessage( Long senderId, String msgType, String title, String content,
        List idList )
    {
        return sendManagerMessage(  senderId,  msgType,  title,   content, idList , null);
    }

    public int sendManagerMessage( Long senderId, String msgType, String title, String content,
        List idList, String deOrgCode)
    {
        if( idList == null )
        {
            return 0;
        }

        List userIdList = idList;

        try
        {
            mysqlEngine.beginTransaction();

            // 每个用户发送消息
            Long userId = null;

            for ( int i = 0; i < userIdList.size(); i++ )
            {
                if( userIdList.get( i ) instanceof SystemUserBean )
                {
                    // 获取登陆ID
                    userId = ( ( SystemUserBean ) userIdList.get( i ) ).getUserId();
                }
                else
                {
                    // 获取登陆ID
                    if( userIdList.get( i ) == null )
                    {
                        continue;
                    }

                    userId = Long.valueOf( ( userIdList.get( i ) ).toString() );

                    // 检查用户是否存在
                    SystemUserBean user = securityDao.querySingleSystemUserBeanById( userId );

                    if( user == null )
                    {
                        continue;
                    }
                }
                
                SystemUserBean su = securityDao.querySingleSystemUserBeanById( userId );
                
                if( StringUtil.isStringNotNull( deOrgCode ) 
                       && deOrgCode.equals( su.getRelateOrgCode() ) )
                {
                    continue;
                }

                ManagerMessage msg = new ManagerMessage();

                msg.setSendDT( new Date( DateAndTimeUtil.clusterTimeMillis() ) );
                msg.setMsgTitle( title );
                msg.setMsgContent( content );
                msg.setMsgTypeName( msgType );
                msg.setUserId( userId );
                msg.setSender( senderId );

                messageDao.save( msg );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        return 1;
    }

    public int sendManagerMessageNoTran( Long senderId, String msgType, String title,
        String content, List idList )
    {
        if( idList == null )
        {
            return 0;
        }

        List userIdList = idList;

        // 每个用户发送消息
        Long userId = null;

        for ( int i = 0; i < userIdList.size(); i++ )
        {
            if( userIdList.get( i ) instanceof SystemUserBean )
            {
                // 获取登陆ID
                userId = ( ( SystemUserBean ) userIdList.get( i ) ).getUserId();
            }
            else
            {
                // 获取登陆ID
                userId = Long.valueOf( ( userIdList.get( i ) ).toString() );

                // 检查用户是否存在
                SystemUserBean user = securityDao.querySingleSystemUserBeanById( userId );

                if( user == null )
                {
                    continue;
                }
            }

            ManagerMessage msg = new ManagerMessage();

            msg.setSendDT( new Date( DateAndTimeUtil.clusterTimeMillis() ) );
            msg.setMsgTitle( title );
            msg.setMsgContent( content );
            msg.setMsgTypeName( msgType );
            msg.setUserId( userId );
            msg.setSender( senderId );

            messageDao.save( msg );
        }

        return 1;
    }

    public void readMessage( Long msgId )
    {
        messageDao.updateReadMsgStatus( msgId, Constant.COMMON.ON );
    }

    public void replyMessage( Long msgId, String content )
    {
        try
        {
            mysqlEngine.beginTransaction();

            messageDao.updateMsgReplyContent( msgId, content, new Timestamp( DateAndTimeUtil
                .clusterTimeMillis() ) );

            messageDao.updateReplyMsgStatus( msgId, Constant.COMMON.ON );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }
    }

    public void deleteMessage( List msgIdList, String msgFlag )
    {
        try
        {
            mysqlEngine.beginTransaction();

            Long msgid = null;

            for ( int i = 0; i < msgIdList.size(); i++ )
            {
                msgid = Long.valueOf( ( msgIdList.get( i ) ).toString() );

                if( msgid.longValue() < 1 )
                {
                    continue;
                }

                if( "se".equals( msgFlag ) )
                {
                    messageDao.updateSeMessageDeleteStatus( msgid, Constant.COMMON.ON );
                }
                else if( "re".equals( msgFlag ) )
                {
                    messageDao.updateReMessageDeleteStatus( msgid, Constant.COMMON.ON );
                }

                // 若为系统消息立即删除
                messageDao.deleteSystemMessage( msgid );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        // 删除所有s r双方都删除的短信
        messageDao.deleteMessageAllDeleteStatus();

    }

    public Long retrieveMessageNotReadCount( Long reUserId )
    {
        Long count = messageDao.queryReMessageCountForNotRead( reUserId );

        return count;
    }

    /**
     * QueryTag使用
     * 
     * @param userId
     * @param msgFlag
     * @param msgId
     * @return
     */
    public Object getManagerMessageTag( String userId, String msgFlag, String reStatus,
        String seStatus, String msgId, String pn, String size )
    {
        if( !"".equals( msgId ) )
        {
            return messageDao.querySingleMessageBean( msgId );
        }

        List result = null;

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        Long count = null;

        if( "se".equals( msgFlag ) )
        {

            if( "1".equals( seStatus ) )// 未读
            {
                count = messageDao.querySeMessageBeanCountForNotRead( userId );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = messageDao.querySeMessageBeanListForNotRead( userId, Long
                    .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
            }
            else if( "2".equals( seStatus ) )// 已读
            {
                count = messageDao.querySeMessageBeanCountForRead( userId );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = messageDao.querySeMessageBeanListForRead( userId, Long.valueOf( pageInfo
                    .getFirstResult() ), Integer.valueOf( pageSize ) );
            }
            else if( "3".equals( seStatus ) )// 已回
            {
                count = messageDao.querySeMessageBeanCountForReply( userId );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = messageDao.querySeMessageBeanListForReply( userId, Long.valueOf( pageInfo
                    .getFirstResult() ), Integer.valueOf( pageSize ) );
            }
            else
            {
                count = messageDao.querySeMessageBeanCount( userId );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = messageDao.querySeMessageBeanList( userId, Long.valueOf( pageInfo
                    .getFirstResult() ), Integer.valueOf( pageSize ) );
            }
        }
        else if( "re".equals( msgFlag ) )
        {
            if( "1".equals( reStatus ) )// 未读
            {
                count = messageDao.queryReMessageBeanCountForNotRead( userId );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = messageDao.queryReMessageBeanListForNotRead( userId, Long
                    .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
            }
            else if( "2".equals( reStatus ) )// 已读
            {
                count = messageDao.queryReMessageBeanCountForRead( userId );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = messageDao.queryReMessageBeanListForRead( userId, Long.valueOf( pageInfo
                    .getFirstResult() ), Integer.valueOf( pageSize ) );
            }
            else if( "3".equals( reStatus ) )// 已回
            {
                count = messageDao.queryReMessageBeanCountForReply( userId );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = messageDao.queryReMessageBeanListForReply( userId, Long.valueOf( pageInfo
                    .getFirstResult() ), Integer.valueOf( pageSize ) );
            }
            else
            {
                count = messageDao.queryReMessageBeanCount( userId );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = messageDao.queryReMessageBeanList( userId, Long.valueOf( pageInfo
                    .getFirstResult() ), Integer.valueOf( pageSize ) );
            }

        }

        return new Object[] { result, pageInfo };
    }

}
