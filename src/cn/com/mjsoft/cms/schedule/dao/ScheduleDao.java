package cn.com.mjsoft.cms.schedule.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.schedule.bean.ScheduleJobDetailBean;
import cn.com.mjsoft.cms.schedule.dao.vo.ScheduleJobDetail;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;

public class ScheduleDao
{
    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public ScheduleDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public UpdateState saveScheduleJobDetail( ScheduleJobDetail job )
    {
        return pe.save( job );
    }

    public void updateScheduleJobDetailAllJobInfo( ScheduleJobDetail job )
    {
        String sql = "update schedule_job_detail set triggerType=?, dayExeTime=?, periodSegment=?, periodVar=?, cronExpression=?, jobStartDate=?, jobEndDate=?, jobDesc=? where jobId=?";
        pe.update( sql, job );
    }

    public void updateScheduleJobDetail( Long jobId, Integer periodType,
        Integer period )
    {
        String sql = "update schedule_job_detail set periodSegment=?, periodVar=? where jobId=?";
        pe.update( sql, new Object[] { periodType, period, jobId } );
    }

    public ScheduleJobDetailBean querySingleScheduleJobDetailBean( Long jobId )
    {
        String sql = "select * from schedule_job_detail where jobid=?";

        return ( ScheduleJobDetailBean ) pe.querySingleRow( sql,
            new Object[] { jobId }, new ScheduleJobDetailBeanTransform() );
    }

    public void updateJobExecuteDate( Long jobId, Timestamp dt )
    {
        String sql = "update schedule_job_detail set lastExcuteTime=? where jobid=?";

        pe.update( sql, new Object[] { dt, jobId } );

    }

    public void updateScheduleJobUseStatus( Long jobId, Integer useStatus )
    {
        String sql = "update schedule_job_detail set useState=? where jobid=? and systemJob=0";

        pe.update( sql, new Object[] { useStatus, jobId } );
    }

    public Map querySinglePublishScheduleJobDetailBean( Long jobId )
    {
        String sql = "select * from schedule_job_detail sjb left join schedule_publish_job_target st on sjb.jobId=st.selfJobId where sjb.jobId=?";

        return pe.querySingleResultMap( sql, new Object[] { jobId } );
    }

    public List queryScheduleJobDetailBeanBySiteIdAndName( String jobName,
        Long siteId )
    {
        String sql = "select * from schedule_job_detail where jobName=? and siteId=? order by jobId desc";

        return pe.query( sql, new Object[] { jobName, siteId },
            new ScheduleJobDetailBeanTransform() );
    }

    public List querySchedulePublishJobDetailInfoBySiteIdAndName(
        String jobName, Long siteId )
    {
        String sql = "select * from schedule_job_detail sjb left join schedule_publish_job_target st on sjb.jobId=st.selfJobId where sjb.jobName=? and sjb.siteId=? order by sjb.jobId desc";

        return pe.queryResultMap( sql, new Object[] { jobName, siteId } );
    }

    public List queryScheduleJobDetailBeanByName( String jobName )
    {
        String sql = "select * from schedule_job_detail where jobName=? and systemJob=0 and useState=1";

        return pe.query( sql, new Object[] { jobName },
            new ScheduleJobDetailBeanTransform() );
    }

    public ScheduleJobDetailBean querySingleSystemScheduleJobDetailBean(
        String jobClassName )
    {
        String sql = "select * from schedule_job_detail where jobExecuteClass=? and systemJob=1";
        return ( ScheduleJobDetailBean ) pe
            .querySingleRow( sql, new Object[] { jobClassName },
                new ScheduleJobDetailBeanTransform() );
    }

    public void deleteScheduleJobDetailByJobId( Long jobId )
    {
        String sql = "delete from schedule_job_detail where jobId=? and systemJob=0";

        pe.update( sql, new Object[] { jobId } );
    }

    public void updateSchedulePublishJobTarget( Long jobId, String ht,
        String ct, String st )
    {
        String sql = "update schedule_publish_job_target set homePage=?, channel=?, spec=? where selfJobId=?";

        pe.update( sql, new Object[] { ht, ct, st, jobId } );
    }

    public void deleteSchedulePublishJobTargetByJobId( Long jobId )
    {
        String sql = "delete from schedule_publish_job_target where selfJobId=?";

        pe.update( sql, new Object[] { jobId } );
    }

    public void saveSchedulePublishJobTarget( Long jobId, String ht, String ct,
        String st )
    {
        String sql = "insert into schedule_publish_job_target (selfJobId, homePage, channel, spec) values (?,?,?,?)";

        pe.update( sql, new Object[] { jobId, ht, ct, st } );
    }

}
