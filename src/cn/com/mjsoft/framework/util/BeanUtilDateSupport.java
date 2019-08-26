package cn.com.mjsoft.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;
import org.apache.log4j.Logger;

/**
 * 对beanUtil的util.date,sql.date自动转换进行扩展
 * 
 * @author mjsoft
 * 
 */
public class BeanUtilDateSupport implements Converter
{
    private Logger log = Logger.getLogger( this.getClass() );

    public Object convert( Class arg0, Object targetObject )
    {
        String dateStr = ( String ) targetObject;
        if( StringUtil.isStringNull( dateStr ) )
        {
            return null;
        }
        try
        {
            SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            return df.parse( dateStr.trim() );
        }
        catch ( Exception e )
        {
            log.debug( "尝试使用 yyyy-MM-dd 格式转换" );
            try
            {
                SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );
                return df.parse( dateStr.trim() );
            }
            catch ( ParseException ex )
            {
                log.debug( "无法转换 " + dateStr + " 为时间类型" );
                return null;
            }
        }

    }

}
