package cn.com.mjsoft.cms.schedule.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.schedule.bean.ScheduleJobDetailBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class ScheduleJobDetailBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        ScheduleJobDetailBean bean = new ScheduleJobDetailBean();

        bean.setCronExpression( rs.getString( "cronExpression" ) );
        bean.setJobDesc( rs.getString( "jobDesc" ) );
        bean.setJobEndDate( rs.getTimestamp( "jobEndDate" ) );
        bean.setJobExecuteClass( rs.getString( "jobExecuteClass" ) );
        bean.setJobId( Long.valueOf( rs.getLong( "jobId" ) ) );
        bean.setJobName( rs.getString( "jobName" ) );
        bean.setJobStartDate( rs.getTimestamp( "jobStartDate" ) );
        bean.setPeriodVar( Integer.valueOf( rs.getInt( "periodVar" ) ) );
        bean.setPeriodSegment( Integer.valueOf( rs.getInt( "periodSegment" ) ) );
        bean.setDayExeTime( rs.getString( "dayExeTime" ) );
        bean.setTriggerType( Integer.valueOf( rs.getInt( "triggerType" ) ) );
        bean.setUseState( Integer.valueOf( rs.getInt( "useState" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );

        return bean;
    }

}
