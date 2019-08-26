package cn.com.mjsoft.cms.guestbook.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.guestbook.bean.GuestbookMainInfoBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class GuestbookMainInfoBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {

        GuestbookMainInfoBean bean = new GuestbookMainInfoBean();

        bean.setGbId( Long.valueOf( rs.getLong( "gbId" ) ) );
        bean.setConfigId( Long.valueOf( rs.getLong( "configId" ) ) );
        bean.setIsReply( Integer.valueOf( rs.getInt( "isReply" ) ) );
        bean.setIsCensor( Integer.valueOf( rs.getInt( "isCensor" ) ) );
        bean.setIsOpen( Integer.valueOf( rs.getInt( "isOpen" ) ) );
        bean.setGbTitle( rs.getString( "gbTitle" ) );
        bean.setGbMan( rs.getString( "gbMan" ) );
        bean.setGbText( rs.getString( "gbText" ) );
        bean.setGbEmail( rs.getString( "gbEmail" ) );
        bean.setReplyMan( rs.getString( "replyMan" ) );
        bean.setReplyText( rs.getString( "replyText" ) );
        bean.setReplyDate( rs.getDate( "replyDate" ) );
        bean.setIp( rs.getString( "ip" ) );
        bean.setAddDate( rs.getDate( "addDate" ) );
        bean.setMemberId( Long.valueOf( rs.getLong( "memberId" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );

        return bean;

    }

}
