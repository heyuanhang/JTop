package cn.com.mjsoft.cms.schedule.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ScheduleShutdownListener implements ServletContextListener
{

    public void contextDestroyed( ServletContextEvent sc )
    {
        System.err.println( "[ScheduleShutdownListener] 系统将销毁所有Schedule" );

        try
        {

            ScheduleService.getScheduler().shutdown();

            Thread.sleep( 5000 );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        System.err.println( "[ScheduleShutdownListener] 销毁所有Schedule结束" );
    }

    public void contextInitialized( ServletContextEvent arg0 )
    {
    }

}
