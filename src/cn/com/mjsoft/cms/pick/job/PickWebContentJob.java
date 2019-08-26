package cn.com.mjsoft.cms.pick.job;

import java.sql.Timestamp;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.mjsoft.cms.pick.service.PickService;
import cn.com.mjsoft.cms.schedule.service.ScheduleService;
import cn.com.mjsoft.framework.cache.jsr14.ReadWriteLockHashMap;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;

public class PickWebContentJob implements Job
{
    private static Logger log = Logger.getLogger( PickWebContentJob.class );

    private static ScheduleService schService = ScheduleService.getInstance();

    private static PickService pickService = PickService.getInstance();

    private static Map excuteJob = new ReadWriteLockHashMap();

    @SuppressWarnings( "unchecked" )
    public void execute( JobExecutionContext jobContent ) throws JobExecutionException
    {
        if( excuteJob.containsKey( jobContent.getJobDetail().getKey() ) )
        {
            log.info( "[PickWebContentJob] ...waiting..." + jobContent.getJobDetail().getKey() );
            return;
        }

        try
        {
            excuteJob.put( jobContent.getJobDetail().getKey(), Boolean.TRUE );

            log.info( "[PickWebContentJob] ...execute start..."
                + jobContent.getJobDetail().getKey() );

            Map dataMap = jobContent.getJobDetail().getJobDataMap();

            Long jobId = ( Long ) dataMap.get( "jobId" );

            Long taskId = ( Long ) dataMap.get( "taskId" );

            Long siteId = ( Long ) dataMap.get( "siteId" );

            pickService.pickUpWebContentByConfig( siteId, taskId, null, null );

            schService.updateJobExecuteDT( jobId, new Timestamp( DateAndTimeUtil
                .clusterTimeMillis() ) );

            log
                .info( "[PickWebContentJob] ...execute over..."
                    + jobContent.getJobDetail().getKey() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            excuteJob.remove( jobContent.getJobDetail().getKey() );
        }
    }
}
