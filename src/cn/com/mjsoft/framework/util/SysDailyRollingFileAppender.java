package cn.com.mjsoft.framework.util;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;

 
/**
 * 将日志保存方式由本地路径改为发布目录为根路径
 * 
 * * @author mjsoft
 */
public class SysDailyRollingFileAppender extends DailyRollingFileAppender
{
    Logger log = Logger.getLogger( SysDailyRollingFileAppender.class );

    public synchronized void setFile( String fileName, boolean append,
        boolean bufferedIO, int bufferSize ) throws IOException
    {
        // String root = SysConfigForInit.getInstance().getLog4jFileSavePath();
        // String dirSeparator = File.separator;
        // if (!root.substring(root.length() - 1).equals(dirSeparator)) {// 加"\"
        // root = root + dirSeparator;
        // }
        // String path = root + "SysCore" + dirSeparator + "logs" + dirSeparator
        // + "Sys_ML_log.html";
        //
        // // 由log4j配置文件决定日志文件
        // if (null != fileName && fileName.trim().equals("")) {
        // if (fileName.toLowerCase().equals("HIGH")) {
        // path = root + "SysCore" + dirSeparator + "logs" + dirSeparator
        // + "Sys_HL_log.html";
        // }
        // }
        // super.setFile(path, append, bufferedIO, bufferSize);
    }
}
