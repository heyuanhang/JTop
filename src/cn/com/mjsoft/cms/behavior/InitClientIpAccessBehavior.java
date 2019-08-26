package cn.com.mjsoft.cms.behavior;

import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.web.engine.InterceptFilter;

public class InitClientIpAccessBehavior implements Behavior
{
    private static StatService statService = StatService.getInstance();

    public Object operation( Object target, Object[] param )
    {
        disposeBlackIpAccess();

        return null;
    }

    public static void disposeBlackIpAccess()
    {
        InterceptFilter.getSystemClientBlackIps().clear();
        InterceptFilter.getSystemClientBlackIps().addAll( statService.retrieveBlackClientIpStr() );

        ClusterService.exeClusterMasterCMD( "cluster/clearBlackIP.do", Constant.COMMON.POST );
    }

    public static void disposeBlackIpAccessCulster()
    {
        InterceptFilter.getSystemClientBlackIps().clear();
        InterceptFilter.getSystemClientBlackIps().addAll( statService.retrieveBlackClientIpStr() );

    }

}
