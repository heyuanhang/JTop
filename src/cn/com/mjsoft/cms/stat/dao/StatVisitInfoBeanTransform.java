package cn.com.mjsoft.cms.stat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.stat.bean.StatVisitInfoBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class StatVisitInfoBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        StatVisitInfoBean bean = new StatVisitInfoBean();

        bean.setVisitorId( Long.valueOf( rs.getLong( "visitorId" ) ) );
        bean.setUvId( rs.getString( "uvId" ) );
        bean.setReffer( rs.getString( "reffer" ) );
        bean.setRefferHost( rs.getString( "refferHost" ) );
        bean.setRefferType( Integer.valueOf( rs.getInt( "refferType" ) ) );
        bean.setRefferKey( rs.getString( "refferKey" ) );
        bean.setVisitTimeIn( rs.getTimestamp( "visitTimeIn" ) );
        bean.setVisitTimeOut( rs.getTimestamp( "visitTimeOut" ) );
        bean.setIp( rs.getString( "ip" ) );
        bean.setHost( rs.getString( "host" ) );
        bean.setUrl( rs.getString( "url" ) );
        bean.setTitle( rs.getString( "title" ) );
        bean.setBrowser( rs.getString( "browser" ) );
        bean.setScreen( rs.getString( "screen" ) );
        bean.setSystem( rs.getString( "system" ) );
        bean.setLang( rs.getString( "lang" ) );
        bean.setSource( rs.getString( "source" ) );
        bean.setContentId( Long.valueOf( rs.getLong( "contentId" ) ) );
        bean.setClassId( Long.valueOf( rs.getLong( "classId" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );

        return bean;
    }

}
