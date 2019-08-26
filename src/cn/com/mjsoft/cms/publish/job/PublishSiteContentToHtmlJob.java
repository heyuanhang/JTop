package cn.com.mjsoft.cms.publish.job;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.schedule.service.ScheduleService;
import cn.com.mjsoft.framework.cache.jsr14.ReadWriteLockHashMap;
import cn.com.mjsoft.framework.security.authorization.AuthorizationHandler;
import cn.com.mjsoft.framework.util.StringUtil;

public class PublishSiteContentToHtmlJob implements Job
{
    private static Logger log = Logger.getLogger( PublishSiteContentToHtmlJob.class );

    private static ScheduleService schService = ScheduleService.getInstance();

    private static Map excuteJob = new ReadWriteLockHashMap();

    @SuppressWarnings( "unchecked" )
    public void execute( JobExecutionContext jobContent ) throws JobExecutionException
    {
        if( excuteJob.containsKey( jobContent.getJobDetail().getKey() ) )
        {
            log.info( "[PublishSiteContentToHtmlJob] ...waiting..."
                + jobContent.getJobDetail().getKey() );
            return;
        }

        try
        {
            excuteJob.put( jobContent.getJobDetail().getKey(), Boolean.TRUE );

            log.info( "[PublishSiteContentToHtmlJob] ...execute start..."
                + jobContent.getJobDetail().getKey() );

            Map dataMap = jobContent.getJobDetail().getJobDataMap();

            Long jobId = ( Long ) dataMap.get( "jobId" );

            Map pubJob = schService.retrieveSinglePublishJobBean( jobId );

            String cmsPath = JtRuntime.cmsServer.getDomainFullPath();

            String uuidKey = "";

            String ht = ( String ) pubJob.get( "homePage" );

            String ct = ( String ) pubJob.get( "channel" );

            String st = ( String ) pubJob.get( "spec" );

            String url = null;

            // 以下逻辑独立分开,若每种静态化有改变,独立处理
            if( Constant.COMMON.ON.toString().equals( ht ) )
            {
                // 首先要向权限系统注册本次访问，下面的url需要带入本次生成的key

                uuidKey = StringUtil.getUUIDString();

                AuthorizationHandler.setInnerAccessFlag( uuidKey );

                url = cmsPath
                    + "/publish/generateContent.do?staticType=4&thread=true&job=true&innerAccessJtopSysFlag="
                    + uuidKey + "&siteId=" + pubJob.get( "siteId" );

                ServiceUtil.doGETMethodRequest( url );
            }

            if( Constant.COMMON.ON.toString().equals( ct ) )
            {
                // 首先要向权限系统注册本次访问，下面的url需要带入本次生成的key

                uuidKey = StringUtil.getUUIDString();

                AuthorizationHandler.setInnerAccessFlag( uuidKey );

                url = cmsPath
                    + "/publish/generateContent.do?staticType=1&thread=true&job=true&innerAccessJtopSysFlag="
                    + uuidKey + "&siteId=" + pubJob.get( "siteId" );

                ServiceUtil.doGETMethodRequest( url );
            }

            if( Constant.COMMON.ON.toString().equals( st ) )
            {
                // 首先要向权限系统注册本次访问，下面的url需要带入本次生成的key

                uuidKey = StringUtil.getUUIDString();

                AuthorizationHandler.setInnerAccessFlag( uuidKey );

                // 由于staticType中class和spec都为1,需传递区分参数
                url = cmsPath
                    + "/publish/generateContent.do?staticType=1&specClass=true&thread=true&job=true&innerAccessJtopSysFlag="
                    + uuidKey + "&siteId=" + pubJob.get( "siteId" );

                ServiceUtil.doGETMethodRequest( url );

                // 首先要向权限系统注册本次访问，下面的url需要带入本次生成的key

                uuidKey = StringUtil.getUUIDString();

                AuthorizationHandler.setInnerAccessFlag( uuidKey );

                // staticType为7的spec列表
                url = cmsPath
                    + "/publish/generateContent.do?staticType=7&specClass=true&thread=true&job=true&innerAccessJtopSysFlag="
                    + uuidKey + "&siteId=" + pubJob.get( "siteId" );

                ServiceUtil.doGETMethodRequest( url );
            }

            // 本地线程执行时间允许使用当前服务器时间记录
            schService.updateJobExecuteDT( jobId, new Timestamp( new Date().getTime() ) );

            log.info( "[PublishSiteContentToHtmlJob] ...execute over..."
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
