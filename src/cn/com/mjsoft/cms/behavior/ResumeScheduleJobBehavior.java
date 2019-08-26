package cn.com.mjsoft.cms.behavior;

import cn.com.mjsoft.cms.schedule.service.ScheduleService;
import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.framework.behavior.Behavior;

public class ResumeScheduleJobBehavior implements Behavior
{
    private static ScheduleService scheduleService = ScheduleService.getInstance();

    private static StatService statService = StatService.getInstance();

    public Object operation( Object target, Object[] param )
    {
        sleep();

        // 主服务器模式
        if( !JtRuntime.cmsServer.getClientServer() )
        {
            /**
             * 当天日期更新
             */
            statService.updateCurrentDateForHelper();

            /**
             * 文件监视和传输
             */
            // scheduleService.resumeFileMonitorCollectEventDataJobAndTransferJob();
            /**
             * 访问统计
             */
            scheduleService.resumeCollectVisitorInfoAndAnalyseJob();

            /**
             * 投票统计
             */
            scheduleService.resumeCollectUserVoteInfoJob();

            /**
             * 内容发布处理
             */
            scheduleService.resumePublishAndDisposeContentJob();

            sleep();

            /**
             * 区块发布
             */
            scheduleService.resumePublishBlockJob();

            sleep();

            /**
             * 定时静态化
             */
            scheduleService.resumePublishContentHtmlJob();

            sleep();
            /**
             * 定时采集
             */
            scheduleService.resumePickWebHtmlJob();
        }

        /**
         * 索引创建,无论任何模式都需要执行
         */
        scheduleService.resumeCreateContentIndexJob();

        return null;
    }

    private void sleep()
    {
        // 停止2秒
        try
        {
            Thread.sleep( 2000 );
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace();
        }
    }

}
