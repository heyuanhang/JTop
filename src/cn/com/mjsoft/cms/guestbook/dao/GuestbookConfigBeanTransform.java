package cn.com.mjsoft.cms.guestbook.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.guestbook.bean.GuestbookConfigBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class GuestbookConfigBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        GuestbookConfigBean bean = new GuestbookConfigBean();

        bean.setConfigId( Long.valueOf( rs.getLong( "configId" ) ) );
        bean.setCfgName( rs.getString( "cfgName" ) );
        bean.setCfgDesc( rs.getString( "cfgDesc" ) );
        bean.setMustHaveTitle( Integer.valueOf( rs.getInt( "mustHaveTitle" ) ) );
        bean.setMustLogin( Integer.valueOf( rs.getInt( "mustLogin" ) ) );
        bean.setMustCensor( Integer.valueOf( rs.getInt( "mustCensor" ) ) );
        bean
            .setNeedVerifyCode( Integer.valueOf( rs.getInt( "needVerifyCode" ) ) );
        bean.setCfgFlag( rs.getString( "cfgFlag" ) );
        bean.setUseState( Integer.valueOf( rs.getInt( "useState" ) ) );
        bean.setInfoModelId( Long.valueOf( rs.getLong( "infoModelId" ) ) );
        bean.setWorkflowId( Long.valueOf( rs.getLong( "workflowId" ) ) );
        bean.setSiteId(  Long.valueOf( rs.getLong( "siteId" ) ) );

        return bean;
    }
}
