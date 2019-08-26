package cn.com.mjsoft.cms.stat.job;

import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.framework.cache.jsr14.ReadWriteLockHashMap;

/**
 * 执行收集Flow请求信息
 * 
 * @author mjsoft
 * 
 */
public class CollectFlowTraceJob implements Job
{
    private static Logger log = Logger.getLogger( CollectFlowTraceJob.class );

    private static Map excuteJob = new ReadWriteLockHashMap();

    private static StatService statService = StatService.getInstance();

    @SuppressWarnings( "unchecked" )
    public void execute( JobExecutionContext jobContent ) throws JobExecutionException
    {
        // if( excuteJob.containsKey( jobContent.getJobDetail().getKey() ) )
        // {
        // log.info( "[CollectVisitorInfoAndAnalyseJob] ...waiting..."
        // + jobContent.getJobDetail().getKey() );
        // return;
        // }

        /*
         * try {
         */
        excuteJob.put( jobContent.getJobDetail().getKey(), Boolean.TRUE );

        log.info( "[CollectFlowTraceJob] ...execute start..." + jobContent.getJobDetail().getKey() );

        statService.addSysFlowExcuteTrace();

        log.info( "[CollectFlowTraceJob] ...execute over..." + jobContent.getJobDetail().getKey() );
        /*
         * } finally { excuteJob.remove( jobContent.getJobDetail().getKey() ); }
         */

    }

}
