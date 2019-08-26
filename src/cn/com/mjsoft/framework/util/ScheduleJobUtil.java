package cn.com.mjsoft.framework.util;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import cn.com.mjsoft.cms.common.Constant;

public class ScheduleJobUtil
{
    private static Logger log = Logger.getLogger( ScheduleJobUtil.class );

    private static final Properties config = new Properties();

    /**
     * 构建一个新的JobDetail
     * 
     * @param targetJobClass
     * @param key
     * @param desc
     * @return
     */
    @SuppressWarnings("unchecked")
    public static JobDetail buildNewJobDetail( Class targetJobClass,
        JobKey key, String desc )
    {
        return JobBuilder.newJob( targetJobClass ).withDescription( desc )
            .withIdentity( key ).build();
    }

    public static Trigger buildNewPeriodTrigger( TriggerKey key,
        Integer periodType, int period, boolean repeatForever )
    {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
            .simpleSchedule();

        if( Constant.JOB.PERROID_SEC.equals( periodType ) )
        {
            scheduleBuilder = scheduleBuilder.withIntervalInSeconds( period );
        }
        else if( Constant.JOB.PERROID_MIN.equals( periodType ) )
        {
            scheduleBuilder = scheduleBuilder.withIntervalInMinutes( period );

        }
        else if( Constant.JOB.PERROID_HOUR.equals( periodType ) )
        {
            scheduleBuilder = scheduleBuilder.withIntervalInHours( period );

        }
        else if( Constant.JOB.PERROID_DAY.equals( periodType ) )
        {
            scheduleBuilder = scheduleBuilder.withIntervalInHours( 24 * period );
        }
        else
        {
            return null;
        }

        if( repeatForever )
        {
            scheduleBuilder = scheduleBuilder.repeatForever();
        }

        return TriggerBuilder.newTrigger().withIdentity( key ).withSchedule(
            scheduleBuilder ).build();
    }

    public static Trigger buildNewCronTrigger( TriggerKey key, String cron )
    {
        return TriggerBuilder.newTrigger().withIdentity( key ).withSchedule(
            CronScheduleBuilder.cronSchedule( cron ) ).build();
    }

    public static Trigger buildNewAtOnceTrigger( TriggerKey key )
    {
        return TriggerBuilder.newTrigger().withIdentity( key ).withSchedule(
            SimpleScheduleBuilder.simpleSchedule() ).startNow().build();
    }

    /**
     * 注册一个新的任务
     * 
     * @param jobdetail
     * @param replace
     * @throws SchedulerException
     */
    public static void regJob( Scheduler scheduler, JobDetail jobDetail,
        boolean replace ) throws SchedulerException
    {
        scheduler.addJob( jobDetail, replace );
    }

    /**
     * 移除一个任务
     * 
     * @param jobKey
     * @return
     * @throws SchedulerException
     */
    public static boolean removeJob( Scheduler scheduler, JobKey jobKey )
        throws SchedulerException
    {
        return scheduler.deleteJob( jobKey );
    }

    /**
     * 向调度对象注册一个执行任务以及其相关信息
     * 
     * @param jobDetail
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public static Date regScheduleJob( Scheduler scheduler,
        JobDetail jobDetail, Trigger trigger ) throws SchedulerException
    {
        log.info( "[Schedule] 注册Job:" + jobDetail + ", 触发器:" + trigger );
        return scheduler.scheduleJob( jobDetail, trigger );
    }

    /**
     * 向调度对象注册任务相关的触发器.
     * 
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public static Date regScheduleJobTrigger( Scheduler scheduler,
        Trigger trigger ) throws SchedulerException
    {
        return scheduler.scheduleJob( trigger );
    }

    /**
     * 停止一个任务，根据提供key
     * 
     * @param triggerKey
     * @return
     * @throws SchedulerException
     */
    public static boolean stopScheduleJob( Scheduler scheduler,
        TriggerKey triggerKey ) throws SchedulerException
    {
        return scheduler.unscheduleJob( triggerKey );
    }

    /**
     * 重新执行指定的任务，并提供新的触发器信息。
     * 
     * @param triggerKey
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public static Date reScheduleJob( Scheduler scheduler,
        TriggerKey triggerKey, Trigger trigger ) throws SchedulerException
    {
        return scheduler.rescheduleJob( triggerKey, trigger );
    }

    /**
     * 根据任务的key触发任务
     * 
     * @param jobkey
     * @throws SchedulerException
     */
    public static void triggerJob( Scheduler scheduler, JobKey jobKey )
        throws SchedulerException
    {
        scheduler.triggerJob( jobKey );
    }

    /**
     * 根据任务的key触发任务,并提供任务运行的相关数据
     * 
     * @param jobKey
     * @param jobdataMap
     * @throws SchedulerException
     */
    public static void triggerJob( Scheduler scheduler, JobKey jobKey,
        JobDataMap jobDataInfo ) throws SchedulerException
    {
        scheduler.triggerJob( jobKey, jobDataInfo );
    }

    /**
     * 暂停一个触发器
     * 
     * @param triggerKey
     * @throws SchedulerException
     */
    public static void pauseTrigger( Scheduler scheduler, TriggerKey triggerKey )
        throws SchedulerException
    {
        scheduler.pauseTrigger( triggerKey );
    }

    /**
     * 恢复一个触发器
     * 
     * @param triggerKey
     * @throws SchedulerException
     */
    public static void resumeTrigger( Scheduler scheduler, TriggerKey triggerKey )
        throws SchedulerException
    {
        scheduler.resumeTrigger( triggerKey );
    }

    /**
     * 暂停一个任务
     * 
     * @param jobKey
     * @throws SchedulerException
     */
    public static void pauseJob( Scheduler scheduler, JobKey jobKey )
        throws SchedulerException
    {
        scheduler.pauseJob( jobKey );
    }

    /**
     * 重新开始任务
     * 
     * @param jobKey
     * @throws SchedulerException
     */
    public static void resumeJob( Scheduler scheduler, JobKey jobKey )
        throws SchedulerException
    {
        scheduler.resumeJob( jobKey );
    }

    /**
     * 暂停调度中所有任务
     * 
     * @throws SchedulerException
     */
    public static void pauseAll( Scheduler scheduler )
        throws SchedulerException
    {
        scheduler.pauseAll();
    }

    /**
     * 恢复调度中所有任务
     * 
     * @throws SchedulerException
     */
    public static void resumeAll( Scheduler scheduler )
        throws SchedulerException
    {
        scheduler.resumeAll();
    }

    /**
     * 启动调度对象
     * 
     * @throws SchedulerException
     */
    public static void start( Scheduler scheduler ) throws SchedulerException
    {
        log.info( "[Schedule] Schedule已启动!" );
        scheduler.start();
    }

    /**
     * 检查调度启动状态
     * 
     * @return
     * @throws SchedulerException
     */
    public static boolean isStarted( Scheduler scheduler )
        throws SchedulerException
    {
        return scheduler.isStarted();
    }

    /**
     * 关闭调度对象
     * 
     * @throws SchedulerException
     */
    public static void shutdown( Scheduler scheduler )
        throws SchedulerException
    {
        scheduler.shutdown();
    }

    public static void init( Properties params )
    {
        if( params != null )
        {
            // TODO
            // config.put( arg0, arg1 )
        }
    }

}
