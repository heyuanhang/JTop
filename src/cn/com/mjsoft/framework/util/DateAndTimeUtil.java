package cn.com.mjsoft.framework.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;

/**
 * 系统日期工具
 * 
 * @author mjsoft
 */
public class DateAndTimeUtil
{
    public static final String DEAULT_FORMAT_MD = "MM-dd";

    public static final String DEAULT_FORMAT_YMD = "yyyy-MM-dd";

    public static final String DEAULT_FORMAT_HMS = "HH:mm:ss";

    public static final String DEAULT_FORMAT_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    public static final String DEAULT_FORMAT_NANO = "yyyy-MM-dd HH:mm:ss:SSS";

    /**
     * 得到当前集群系统毫秒时间
     * 
     * @param String DateFormat
     * @return String date
     */
    public static long clusterTimeMillis()
    {
        Properties csp = SystemConfiguration.getInstance().getSystemConfig().getSysPro();

        String cMode = csp.getProperty( "cluster_mode" );

        String sMode = csp.getProperty( "client_server" );

        if( "true".equals( cMode ) )
        {
            if( "false".equals( sMode ) )
            {
                return System.currentTimeMillis();
            }

            String url = csp.getProperty( "datetime_server_url" );

            String ctm = ServiceUtil.doGETInfo( url + "cluster_date" );

            // 若返回的非long,使用当前服务器时间
            return StringUtil.getLongValue( ctm, System.currentTimeMillis() );

        }

        return System.currentTimeMillis();
    }

    /**
     * 根据所给的日期时间(long数据)和格式返回日期时间字符
     * 
     * @param dateTime
     * @param format
     * @return
     */
    public static String getFormatDate( long dateTime, String format )
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis( dateTime );

        return new SimpleDateFormat( format ).format( calendar.getTime() );
    }

    /**
     * 得到当前系统日期
     * 
     * @param String DateFormat
     * @return String date
     */
    public static String getCunrrentDayAndTime( String dateFormat )
    {
        return new SimpleDateFormat( dateFormat ).format( new Date( clusterTimeMillis() ) );
    }

    // ///////////////////////数据库格式时间的相关操作//////////////////////////

    /**
     * 判断时间是否就是今天
     * 
     * @param Timestamp ts
     */
    public static boolean isToday( Timestamp ts )
    {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        if( sdf.format( ts ).equals( sdf.format( new Date( clusterTimeMillis() ) ) ) )
        {
            return true;
        }
        return false;
    }

    /**
     * 将今天转换为年月日的Timestamp(yyyy-MM-dd)
     * 
     * @return Timestamp today
     */
    public static Timestamp getTodayTimestamp()
    {
        String now = DateAndTimeUtil.getCunrrentDayAndTime( "yyyy-MM-dd" );

        Timestamp ts = null;
        try
        {
            ts = new Timestamp( new SimpleDateFormat( DEAULT_FORMAT_YMD ).parse( now ).getTime() );
        }
        catch ( ParseException e )
        {

        }
        return ts;
    }

    /**
     * 将今天转换为年月日的Timestamp(yyyy-MM-dd HH:mm:ss)
     * 
     * @return Timestamp today
     */
    public static Timestamp getTodayTimestampDayAndTime()
    {
        String now = DateAndTimeUtil.getCunrrentDayAndTime( DEAULT_FORMAT_YMD_HMS );

        Timestamp ts = null;
        try
        {
            ts = new Timestamp( new SimpleDateFormat( DEAULT_FORMAT_YMD_HMS ).parse( now )
                .getTime() );
        }
        catch ( ParseException e )
        {

        }
        return ts;
    }

    /**
     * 格式化Timestamp为String
     * 
     * @param time
     * @param format
     * @return String
     */
    public static String formatTimestamp( Timestamp time, String format )
    {
        if( null == time )
        {
            return null;
        }

        SimpleDateFormat fmt = new SimpleDateFormat( format );

        return fmt.format( time );

    }

    /**
     * 获取两个时间中间的天数
     * 
     * @param after
     * @param before
     * @param sdf
     * @return
     */
    public static int getDayInterval( String after, String before, String sdf )
    {
        long dayCount;
        SimpleDateFormat fmt = new SimpleDateFormat( sdf );
        try
        {
            Date afterDate = fmt.parse( after );
            Date oldDate = fmt.parse( before );

            dayCount = ( afterDate.getTime() - oldDate.getTime() ) / ( 24 * 60 * 60 * 1000 );

        }
        catch ( ParseException e )
        {
            dayCount = 0;

        }
        return ( int ) dayCount;
    }

    /**
     * 获取两个时间中间的小时数
     * 
     * @param after
     * @param before
     * @param sdf
     * @return
     */
    public static int getHourInterval( String after, String before, String sdf )
    {
        long hourCount;

        SimpleDateFormat fmt = new SimpleDateFormat( sdf );
        try
        {
            Date afterDate = fmt.parse( after );
            Date oldDate = fmt.parse( before );

            hourCount = ( afterDate.getTime() - oldDate.getTime() ) / ( 60 * 60 * 1000 );

        }
        catch ( ParseException e )
        {
            hourCount = 0;

        }
        return ( int ) hourCount;
    }

    /**
     * 获取两个时间中间的分钟
     * 
     * @param after
     * @param before
     * @param sdf
     * @return
     */
    public static int getSecInterval( String after, String before, String sdf )
    {
        long secCount;

        SimpleDateFormat fmt = new SimpleDateFormat( sdf );
        try
        {
            Date afterDate = fmt.parse( after );
            Date oldDate = fmt.parse( before );

            secCount = ( afterDate.getTime() - oldDate.getTime() ) / ( 60 * 1000 );

        }
        catch ( ParseException e )
        {
            secCount = 0;

        }
        return ( int ) secCount;
    }

    /**
     * 获取两个时间中间的分钟
     * 
     * @param after
     * @param before
     * @param sdf
     * @return
     */
    public static int getSecInterval( Date afterDate, Date oldDate )
    {
        long secCount = 0;

        if( afterDate == null || oldDate == null )
        {
            return 0;
        }

        secCount = ( afterDate.getTime() - oldDate.getTime() ) / ( 60 * 1000 );

        return ( int ) secCount;
    }

    /**
     * 将String 型日期按format转换为Timestamp,如input不符合格式则为当前时间
     * 
     * @param input
     * @param format
     * @return
     */
    public static Timestamp getNotNullTimestamp( String input, String format )
    {
        Timestamp res;

        if( StringUtil.isStringNull( input ) )
        {
            res = new Timestamp( clusterTimeMillis() );
        }
        else
        {
            try
            {
                res = new Timestamp( new SimpleDateFormat( format ).parse( input ).getTime() );
            }
            catch ( Exception e )
            {
                res = new Timestamp( clusterTimeMillis() );
            }
        }
        return res;
    }

    /**
     * 将String 型日期按format转换为Timestamp,如input不符合格式则返回NULL
     * 
     * @param input
     * @param format
     * @return
     */
    public static Timestamp getTimestamp( String input, String format )
    {
        Timestamp res;

        SimpleDateFormat sdf = new SimpleDateFormat( format );

        if( StringUtil.isStringNull( input ) )
        {
            res = null;
        }
        else
        {
            try
            {
                res = new Timestamp( sdf.parse( input ).getTime() );
            }
            catch ( Exception e )
            {
               // e.printStackTrace();
                res = null;
            }
        }
        return res;
    }

    // public Date afterOrBeforeDay( Date targetDay, int count, boolean before )
    // {
    // Calendar cal = Calendar.getInstance();
    //
    // cal.setTime( targetDay );
    //        
    // cal.
    //
    // }

    /**
     * 当前天的第-num天或第num天
     * 
     * @param time
     * @param days
     * @return
     */
    public static String getTheDayByNum( String time, int days )
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd" );
        try
        {
            cal.setTime( new SimpleDateFormat( "yyyy-MM-dd" ).parse( time ) );
            cal.add( Calendar.DAY_OF_MONTH, days );
            String mDateTime = formatter.format( cal.getTime() );
            return StringUtil.subString( mDateTime, 0, 10 );
        }
        catch ( ParseException e )
        {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 当前天的第-num小时或第num小时
     * 
     * @param time
     * @param days
     * @return
     */
    public static Date getTheHourByNum( Date date, int hours )
    {
        Calendar cal = Calendar.getInstance();

        cal.setTime( date );

        cal.add( Calendar.HOUR, hours );

        return cal.getTime();
    }

    /**
     * 当前天的第-num小时或第num小时
     * 
     * @param time
     * @param days
     * @return
     */
    public static String getTheHourByNum( Date date, int hours, String format )
    {
        Calendar cal = Calendar.getInstance();

        cal.setTime( date );

        cal.add( Calendar.HOUR, hours );

        SimpleDateFormat fmt = new SimpleDateFormat( format );

        return fmt.format( cal.getTime() );

    }

    // 获得当前日期与本周一相差的天数
    public static int getMondayPlus()
    {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get( Calendar.DAY_OF_WEEK );
        if( dayOfWeek == 1 )
        {
            return -6;
        }
        else
        {
            return 2 - dayOfWeek;
        }
    }

    // 获得当前周- 周一的日期
    public static Calendar getCurrentMonday()
    {
        int mondayPlus = getMondayPlus();
        Calendar currentDate = Calendar.getInstance();
        currentDate.add( Calendar.DATE, mondayPlus );

        return currentDate;

    }

    // 获得当前周- 周日 的日期
    public static String getPreviousSunday()
    {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add( GregorianCalendar.DATE, mondayPlus + 6 );
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format( monday );
        return preMonday;
    }

    public static long startClock()
    {
        return clusterTimeMillis();
    }

    public static long currentClock( long prevTime )
    {
        return clusterTimeMillis() - prevTime;
    }

}
