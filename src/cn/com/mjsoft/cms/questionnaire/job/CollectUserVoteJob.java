package cn.com.mjsoft.cms.questionnaire.job;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.mjsoft.cms.questionnaire.service.SurveyService;

public class CollectUserVoteJob implements Job
{
    private static Logger log = Logger.getLogger( CollectUserVoteJob.class );

    private static Map excuteJob = new ConcurrentHashMap();

    private static SurveyService surveyService = SurveyService.getInstance();

    @SuppressWarnings("unchecked")
    public void execute( JobExecutionContext jobContent )
        throws JobExecutionException
    {
        if( excuteJob.containsKey( jobContent.getJobDetail().getKey() ) )
        {
            log.info( "[CollectUserVoteJob] ...waiting..."
                + jobContent.getJobDetail().getKey() );
            return;
        }

        try
        {
            excuteJob.put( jobContent.getJobDetail().getKey(), Boolean.TRUE );

            log.info( "[CollectUserVoteJob] ...execute start..."
                + jobContent.getJobDetail().getKey() );

            surveyService.transferUserVoteInfoCacheToPe();

            log.info( "[CollectUserVoteJob] ...execute over..."
                + jobContent.getJobDetail().getKey() );
        }
        finally
        {
            excuteJob.remove( jobContent.getJobDetail().getKey() );
        }

    }
}
