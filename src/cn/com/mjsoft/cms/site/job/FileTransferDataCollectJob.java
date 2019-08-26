package cn.com.mjsoft.cms.site.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

 
import cn.com.mjsoft.cms.site.service.FileMonitorListener;
import cn.com.mjsoft.framework.exception.FrameworkException;

public class FileTransferDataCollectJob implements Job
{

    public void execute( JobExecutionContext jobContent )
        throws JobExecutionException
    {
        FileMonitorListener fmListener = ( FileMonitorListener ) jobContent
            .getJobDetail().getJobDataMap().get( "fmListener" );

        if( fmListener != null )
        {
            fmListener.collectEventForPeriod();
        }
        else
        {
            throw new FrameworkException( "无法获取 FileMonitorListener ！ " );
        }

    }

}
