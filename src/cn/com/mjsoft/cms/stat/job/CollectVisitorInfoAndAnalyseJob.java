package cn.com.mjsoft.cms.stat.job;

import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.framework.cache.jsr14.ReadWriteLockHashMap;

/**
 * 每小时执行分析,临界值只存值
 * 
 * @author mjsoft
 * 
 */
public class CollectVisitorInfoAndAnalyseJob implements Job
{
    private static Logger log = Logger
        .getLogger( CollectVisitorInfoAndAnalyseJob.class );

    private static Map excuteJob = new ReadWriteLockHashMap();

    private static StatService statService = StatService.getInstance();

    @SuppressWarnings( "unchecked" )
    public void execute( JobExecutionContext jobContent )
        throws JobExecutionException
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

        log.info( "[CollectVisitorInfoAndAnalyseJob] ...execute start..."
            + jobContent.getJobDetail().getKey() );

        Boolean prevHour = ( Boolean ) jobContent.getJobDetail()
            .getJobDataMap().get( "prevHour" );

        Boolean analyse = ( Boolean ) jobContent.getJobDetail().getJobDataMap()
            .get( "analyse" );

        statService.transferVisitorStatInfoCacheToPe();
        
        if( analyse.booleanValue() )
        {
            statService
                .analyseVisitorStatInfoAndClearSiteResCurrnetHour( prevHour
                    .booleanValue() );
        }

       

        log.info( "[CollectVisitorInfoAndAnalyseJob] ...execute over..."
            + jobContent.getJobDetail().getKey() );
        /*
         * } finally { excuteJob.remove( jobContent.getJobDetail().getKey() ); }
         */

    }

    public static void main( String[] args )
    { }
}
