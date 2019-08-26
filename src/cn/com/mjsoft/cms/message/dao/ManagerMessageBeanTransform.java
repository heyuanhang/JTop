package cn.com.mjsoft.cms.message.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.message.bean.ManagerMessageBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class ManagerMessageBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        ManagerMessageBean bean = new ManagerMessageBean();

        bean.setMsgId( Long.valueOf( rs.getLong( "msgId" ) ) );
        bean.setMsgTypeName( rs.getString( "msgTypeName" ) );
        bean.setMsgTitle( rs.getString( "msgTitle" ) );
        bean.setMsgContent( rs.getString( "msgContent" ) );
        bean.setReplyContent( rs.getString( "replyContent" ) );
        bean.setSender( Long.valueOf( rs.getLong( "sender" ) ) );
        bean.setSendDT( rs.getTimestamp( "sendDT" ) );
        bean.setReplyDT( rs.getTimestamp( "replyDT" ) );
        bean.setUserId( Long.valueOf( rs.getLong( "userId" ) ) );
        bean.setIsRead( Integer.valueOf( rs.getInt( "isRead" ) ) );
        bean.setIsReply( Integer.valueOf( rs.getInt( "isReply" ) ) );
        bean.setIsReDelete( Integer.valueOf( rs.getInt( "isReDelete" ) ) );
        bean.setIsSeDelete( Integer.valueOf( rs.getInt( "isSeDelete" ) ) );

        return bean;
    }
}
