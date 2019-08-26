package cn.com.mjsoft.framework.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.site.service.FileMonitorListener;

 

import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyException;
import net.contentobjects.jnotify.JNotifyListener;

public class FileMonitorUtil
{
    private static Logger log = Logger.getLogger( FileMonitorUtil.class );

    public static final int ALL_EVENT_MASK = JNotify.FILE_CREATED
        | JNotify.FILE_DELETED | JNotify.FILE_MODIFIED | JNotify.FILE_RENAMED;

    private static final Map watchReg = new HashMap();

    /**
     * 注册新的文件监视
     * 
     * @param mask
     * @param watchSubtree
     * @param eventListener
     * @param fullRootPath
     * @return
     */
    public static int regNewFileMonitor( int mask, boolean watchSubtree,
        JNotifyListener eventListener, String fullRootPath )
    {
        int watchId = -9999;
        try
        {
            watchId = JNotify.addWatch( fullRootPath, mask, watchSubtree,
                eventListener );
        }
        catch ( Exception e )
        {
            try
            {
                JNotify.removeWatch( watchId );
            }
            catch ( JNotifyException je )
            {
                je.printStackTrace();
            }
            log.error( "注册系统文件监视失败!" );
            log.error( e );
            e.printStackTrace();
        }

      
        watchReg.put( Integer.valueOf( watchId ), Integer.valueOf( watchId ) );

        return watchId;

    }

    public static void main( String[] args ) throws InterruptedException
    { }

    public static void removeNewFileMonitor( int watchId )
    {
        try
        {
            JNotify.removeWatch( watchId );
        }
        catch ( JNotifyException e )
        {
            e.printStackTrace();
        }
        watchReg.remove( Integer.valueOf( watchId ) );
    }
}
