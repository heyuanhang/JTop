package cn.com.mjsoft.cms.stat.behavior;

import java.util.Date;

import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.cluster.adapter.ClusterMapAdapter;
import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;

public class DangerAcceccTraceBehavior implements Behavior
{
    private static ClusterMapAdapter ipMap = new ClusterMapAdapter(
        "dangerAcceccTraceBehavior.ipMap", String.class, Integer.class );

    public Object operation( Object target, Object[] param )
    {

        String ip = ( String ) param[0];

        String url = ( String ) param[1];

        String dangerStr = ( String ) param[2];

        String queryStr = ( String ) param[3];

        Integer accCount = null;

        if( ipMap.containsKey( ip ) )
        {
            // 当前服务器启动已经存在非法记录
            accCount = ( Integer ) ipMap.get( ip );

            if( accCount.intValue() + 1 >= JtRuntime.cmsServer.getDangerAccessCount().intValue() )// 达到临界值
            {
                // 直接屏蔽IP

                // 1.入库(同时会更新黑名单IP)

                if( JtRuntime.cmsServer.getActDefense() )
                {
                    StatService.getInstance().addBlackIpTrace( ip,
                        new Date( DateAndTimeUtil.clusterTimeMillis() ), Integer.valueOf( 1 ),
                        Integer.valueOf( 0 ), url, dangerStr, queryStr );
                }

                // 3.
                ipMap.remove( ip );
            }
            else
            {
                ipMap.put( ip, Integer.valueOf( accCount.intValue() + 1 ) );
            }
        }
        else
        {
            ipMap.put( ip, Integer.valueOf( 1 ) );
        }

        return null;
    }
}
