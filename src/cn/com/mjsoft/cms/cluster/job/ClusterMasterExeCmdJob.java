package cn.com.mjsoft.cms.cluster.job;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.questionnaire.job.CollectUserVoteJob;
import cn.com.mjsoft.framework.security.authorization.AuthorizationHandler;
import cn.com.mjsoft.framework.util.StringUtil;

public class ClusterMasterExeCmdJob implements Job
{
    private static Logger log = Logger.getLogger( CollectUserVoteJob.class );

    private static Map excuteJob = new ConcurrentHashMap();

    private static ClusterService clusterService = ClusterService.getInstance();

    @SuppressWarnings( "unchecked" )
    public void execute( JobExecutionContext jobContent ) throws JobExecutionException
    {
        if( excuteJob.containsKey( jobContent.getJobDetail().getKey() ) )
        {
            log.info( "[ClusterExeCmdJob] ...waiting..." + jobContent.getJobDetail().getKey() );
            return;
        }

        try
        {
            excuteJob.put( jobContent.getJobDetail().getKey(), Boolean.TRUE );

            log
                .info( "[ClusterExeCmdJob] ...execute start..."
                    + jobContent.getJobDetail().getKey() );

            Map params = jobContent.getJobDetail().getJobDataMap();

            String cmdInfo = ( String ) params.get( "cmdInfo" );

            String param = ( String ) params.get( "params" );

            String method = ( String ) params.get( "method" );

            if( param == null )
            {
                param = "";
            }
            else
            {
                param = ServiceUtil.encodeValFormUrlParam( param );
            }

            if( method == null )
            {
                method = "GET";
            }

            List csList = clusterService.retrieveClusterNodeAndServerInfo();

            Map cluServer = null;

            String serClientServerIp = null;

            String url = null;

            Integer isActive = null;

            log.info( "ClusterMasterExeCmd : current_serverId: "
                + JtRuntime.cmsServer.getServerId() );

            for ( int i = 0; i < csList.size(); i++ )
            {
                cluServer = ( Map ) csList.get( i );

                serClientServerIp = ( String ) cluServer.get( "clusterUrl" );

                isActive = ( Integer ) cluServer.get( "isActive" );

                // 非运行状态节点或当前服务所在节点不执行
                if( Constant.COMMON.ON.equals( isActive )
                    && !JtRuntime.cmsServer.getServerId().equals(
                        ( Long ) cluServer.get( "serverId" ) ) )
                {
                    log.info( "ClusterMasterExeCmd : exe_serverId: " + cluServer.get( "serverId" )
                        + ": " + serClientServerIp + cmdInfo );
                    // 首先要向权限系统注册本次访问，下面的url需要带入本次生成的key

                    String uuidKey = StringUtil.getUUIDString();

                    AuthorizationHandler.setInnerAccessFlag( uuidKey );

                    url = serClientServerIp + cmdInfo + "?" + "innerAccessJtopSysFlag=" + uuidKey
                        + "&" + param;

                    boolean errorFlag = false;

                    if( Constant.COMMON.GET.equals( method ) )
                    {
                        errorFlag = ServiceUtil.GETMethodRequest( url );
                    }
                    else if( Constant.COMMON.POST.equals( method ) )
                    {
                        errorFlag = ServiceUtil.POSTMethodRequest( url );
                    }

                    // 清除已无法连接的服务节点
                    if( errorFlag && cluServer.get( "serverId" ) != null )
                    {
                        clusterService.changeClusterServerActiveFlag( ( Long ) cluServer
                            .get( "serverId" ), Constant.COMMON.OFF );
                    }

                    AuthorizationHandler.romoveInnerAccessFlag( uuidKey );
                }

            }

            log.info( "[ClusterExeCmdJob] ...execute over..." + jobContent.getJobDetail().getKey() );
        }
        finally
        {
            excuteJob.remove( jobContent.getJobDetail().getKey() );
        }

    }
}
