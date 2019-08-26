package cn.com.mjsoft.cms.stat.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.framework.persistence.core.support.MapValueCallback;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;

public class SiteEmptyClassInfoResultCallBack implements MapValueCallback
{

    private static ChannelService channelService = ChannelService.getInstance();

    @SuppressWarnings( "unchecked" )
    public void transformVlaue( Map result )
    {
        long dayCount = 0;

        Date afterDate = DateAndTimeUtil.getTodayTimestamp();
        Date oldDate = ( Date ) result.get( "ud" );

        dayCount = ( afterDate.getTime() - oldDate.getTime() )
            / ( 24 * 60 * 60 * 1000 );

        result.put( "dayCount", dayCount + "" );

        Long classId = ( Long ) result.get( "classId" );

        ContentClassBean classBean = channelService
            .retrieveSingleClassBeanInfoByClassId( classId );

        StringBuilder buf = new StringBuilder();

        List resultList = null;

        if( classBean != null && classBean.getClassId().longValue() > 0 )
        {
            resultList = channelService
                .retrieveContentClassBeanByCurrentPath( classBean
                    .getChannelPath() );

            for ( int i = 0; i < resultList.size(); i++ )
            {
                classBean = ( ContentClassBean ) resultList.get( i );

                if( i + 1 != resultList.size() )
                {
                    buf.append( classBean.getClassName() + " > " );
                }
                else
                {
                    buf.append( classBean.getClassName() );
                }
            }
        }

        result.put( "classTree", buf.toString() );
    }

}
