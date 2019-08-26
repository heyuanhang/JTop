package cn.com.mjsoft.cms.content.dao;

import java.sql.Timestamp;
import java.util.Map;

import cn.com.mjsoft.framework.persistence.core.support.MapValueCallback;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;

public class ContentDayTimeValueCallBack implements MapValueCallback
{

    public void transformVlaue( Map result )
    {
        Timestamp tsStart = ( Timestamp ) result.get( "appearStartDateTime" );
        Timestamp tsEnd = ( Timestamp ) result.get( "appearEndDateTime" );

        if( tsStart != null )
        {
            result.put( "appearStartDateTime", DateAndTimeUtil.getFormatDate(
                tsStart.getTime(), DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );
        }

        if( tsEnd != null )
        {
            result.put( "appearEndDateTime", DateAndTimeUtil.getFormatDate(
                tsEnd.getTime(), DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );
        }
    }

}
