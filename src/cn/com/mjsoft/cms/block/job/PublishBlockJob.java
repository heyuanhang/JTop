package cn.com.mjsoft.cms.block.job;

import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.framework.cache.jsr14.ReadWriteLockHashMap;
import cn.com.mjsoft.framework.security.authorization.AuthorizationHandler;
import cn.com.mjsoft.framework.util.StringUtil;

public class PublishBlockJob implements Job
{
    private static Logger log = Logger.getLogger( PublishBlockJob.class );

    private static Map excuteJob = new ReadWriteLockHashMap();

    @SuppressWarnings( "unchecked" )
    public void execute( JobExecutionContext jobContent ) throws JobExecutionException
    {
        if( excuteJob.containsKey( jobContent.getJobDetail().getKey() ) )
        {
            log.info( "[PublishBlockJob] ...waiting..." + jobContent.getJobDetail().getKey() );
            return;
        }

        String uuidKey = StringUtil.getUUIDString();

        try
        {
            excuteJob.put( jobContent.getJobDetail().getKey(), Boolean.TRUE );

            log.info( "[PublishBlockJob] ...execute start..." + jobContent.getJobDetail().getKey() );

            Map dataMap = jobContent.getJobDetail().getJobDataMap();

            String blockFlag = ( String ) dataMap.get( "blockFlag" );

            Long siteId = ( Long ) dataMap.get( "siteId" );

            String cmsPath = JtRuntime.cmsServer.getDomainFullPath();

            // 首先要向权限系统注册本次访问，下面的url需要带入本次生成的key
            AuthorizationHandler.setInnerAccessFlag( uuidKey );

            String url = cmsPath + "/publish/generateContent.do?staticType=5&siteId=" + siteId
                + "&job=true&blockFlag=" + blockFlag + "&innerAccessJtopSysFlag=" + uuidKey;

            ServiceUtil.doGETMethodRequest( url );

            log.info( "[PublishBlockJob] ...execute over..." + jobContent.getJobDetail().getKey() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            excuteJob.remove( jobContent.getJobDetail().getKey() );
            AuthorizationHandler.romoveInnerAccessFlag( uuidKey );
        }

    }
}
