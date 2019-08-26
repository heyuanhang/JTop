package cn.com.mjsoft.cms.content.dao;

import java.util.Map;

import cn.com.mjsoft.cms.stat.bean.StatContentVisitOrCommentDWMCount;
import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.framework.persistence.core.support.MapValueCallback;

public class ContentStatusMapValueCallback implements MapValueCallback
{

    @SuppressWarnings("unchecked")
    public void transformVlaue( Map result )
    {
        Long cid = ( Long ) result.get( "contentId" );

        StatContentVisitOrCommentDWMCount scv = ( StatContentVisitOrCommentDWMCount ) StatService.statCacheContentClickCountMap
            .get( cid );

        if( scv == null )
        {
            return;
        }

        long clickMonthCount = ( Long ) result.get( "clickMonthCount" );

        long clickWeekCount = ( Long ) result.get( "clickWeekCount" );

        long clickDayCount = ( Long ) result.get( "clickDayCount" );

        long clickCount = ( Long ) result.get( "clickCount" );

        result.put( "clickMonthCount", clickMonthCount + scv.getMonthCount() );
        result.put( "clickWeekCount", clickWeekCount + scv.getWeekCount() );
        result.put( "clickDayCount", clickDayCount + scv.getDayCount() );
        result.put( "clickCount", clickCount + scv.getNoLimitCount() );
    }

}
