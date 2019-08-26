package cn.com.mjsoft.cms.cluster.adapter;

import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.common.service.CMSRedisDB;

import cn.com.mjsoft.framework.cache.impl.LRUCache;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.util.StringUtil;

/**
 * 根据集群和单机部署环境处理Cache业务
 * 
 * @author MJ-Soft
 * 
 */
@SuppressWarnings( { "rawtypes", "unchecked" } )
public class ClusterCacheAdapter extends LRUCache
{
    private String entryName;

    private String mode = "inner";

    public ClusterCacheAdapter( int lruc, String entryName )
    {
        super( lruc );

        this.entryName = entryName;

        String cMode = SystemConfiguration.getInstance().getSystemConfig().getSysPro().getProperty(
            "cluster_mode" );

        mode = "true".equals( cMode ) ? "redis" : "inner";
    }

    public Object getEntry( Object key )
    {
        checkClusterCacheClear();

        return super.getEntry( key );
    }

    public Object getEntry( Object key, boolean showLog )
    {
        checkClusterCacheClear();

        return super.getEntry( key, showLog );
    }

    public Object putEntry( Object key, Object value )
    {
        return super.putEntry( key, value );
    }

    public boolean containsEntryKey( Object key )
    {
        checkClusterCacheClear();

        return super.containsEntryKey( key );
    }

    public Object removeEntry( Object key )
    {
        return super.removeEntry( key );
    }

    public int cacheCurrentSize()
    {
        checkClusterCacheClear();

        return super.cacheCurrentSize();
    }

    public void clearAllEntry()
    {
        if( "inner".equals( mode ) )
        {
            super.clearAllEntry();
        }
        else if( "redis".equals( mode ) )
        {

            super.clearAllEntry();

            String cluIdInfo = CMSRedisDB.getMapVal( "__cluster_node_info__", "__all_cluster_id__" );

            CMSRedisDB.putMapVal( "__cluster_cache_trace__", entryName, cluIdInfo );

        }

    }

    private void checkClusterCacheClear()
    {
        if( "redis".equals( mode ) )
        {
            Long sId = JtRuntime.cmsServer.getServerId();

            String cst = CMSRedisDB.getMapVal( "__cluster_cache_trace__", entryName );

            if( cst != null && cst.indexOf( sId + ":" ) != -1 )
            {
                super.clearAllEntry();

                CMSRedisDB.putMapVal( "__cluster_cache_trace__", entryName, StringUtil
                    .replaceString( cst, sId + ":", "", false, false ) );
            }
        }

    }

}
