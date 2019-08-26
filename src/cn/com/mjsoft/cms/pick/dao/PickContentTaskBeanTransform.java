package cn.com.mjsoft.cms.pick.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.pick.bean.PickContentTaskBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class PickContentTaskBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        PickContentTaskBean bean = new PickContentTaskBean();

        bean.setPickTaskId( Long.valueOf( rs.getLong( "pickTaskId" ) ) );
        bean.setTaskName( rs.getString( "taskName" ) );
        bean.setRuleId( Long.valueOf( rs.getLong( "ruleId" ) ) );
        bean.setClassId( Long.valueOf( rs.getLong( "classId" ) ) );
        bean.setTaskDesc( rs.getString( "taskDesc" ) );
        bean.setPickThreadCount( Integer.valueOf( rs.getInt( "pickThreadCount" ) ) );
        bean.setPickInterval( Integer.valueOf( rs.getInt( "pickInterval" ) ) );
        bean.setPickMaxListPage( Integer.valueOf( rs.getInt( "pickMaxListPage" ) ) );
        bean.setPickMaxContent( Integer.valueOf( rs.getInt( "pickMaxContent" ) ) );
        bean.setCensorMode( Integer.valueOf( rs.getInt( "censorMode" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );
        bean.setExcuteDT( rs.getTimestamp( "excuteDT" ) );
        bean.setPeriodType( Integer.valueOf( rs.getInt( "periodType" ) ) );
        bean.setPeriod( Integer.valueOf( rs.getInt( "period" ) ) );
        bean.setSelfJobId( Long.valueOf( rs.getLong( "selfJobId" ) ) );
        bean.setDeleteHref( Integer.valueOf( rs.getInt( "deleteHref" ) ) );
        bean.setGuideImgPos( Integer.valueOf( rs.getInt( "guideImgPos" ) ) );
        bean.setDownOutImg( Integer.valueOf( rs.getInt( "downOutImg" ) ) );
        bean.setPickType( rs.getInt( "pickType" ) );
        bean.setRssUrl( rs.getString( "rssUrl" ) );
        bean.setRssRuleBeClass( rs.getString( "rssRuleBeClass" ) );
        bean.setDbRuleBeClass( rs.getString( "dbRuleBeClass" ) );

        return bean;
    }

}
