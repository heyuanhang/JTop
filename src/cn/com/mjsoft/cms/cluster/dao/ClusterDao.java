package cn.com.mjsoft.cms.cluster.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;

public class ClusterDao
{
    public static Cache clusterServerCache = new ClusterCacheAdapter( 100,
        "clusterDao.clusterServerCache" );

    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public ClusterDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public Long saveClusterServer( String sName, String url, Integer port, Integer active )
    {
        String sql = "insert into system_cluster_server (serverName, clusterUrl, serverPort, isActive) values (?,?,?,?)";

        return pe.update( sql, new Object[] { sName, url, port, active } ).getKey();
    }

    public void updateClusterServer( Long serverId, String sName, String url, Integer port )
    {
        String sql = "update system_cluster_server set serverName=?, clusterUrl=?, serverPort=? where serverId=?";

        pe.update( sql, new Object[] { sName, url, port, serverId } );

    }

    public void updateClusterServerActiveFlag( Long serverId, Integer isActive )
    {
        String sql = "update system_cluster_server set isActive=? where serverId=?";

        pe.update( sql, new Object[] { isActive, serverId } );

    }

    /**
     * 获取节点服务器并追加主服务器
     * 
     * @return
     */
    public List queryAllClusterServer()
    {
        String sql = "select * from system_cluster_server";

        List result = ( List ) clusterServerCache.getEntry( "queryAllClusterServer" );

        if( result == null )
        {
            result = pe.queryResultMap( sql );

            Properties csp = SystemConfiguration.getInstance().getSystemConfig().getSysPro();

            // 追加主服务器

            Map serverNode = new HashMap();

            serverNode.put( "isActive", Constant.COMMON.ON );

            serverNode.put( "serverId", Long.valueOf( 0 ) );

            serverNode.put( "clusterUrl", csp.getProperty( "datetime_server_url" ) );

            result.add( serverNode );

            clusterServerCache.putEntry( "queryAllClusterServer", result );
        }

        return result;
    }

    public Map querySingleClusterServerById( Long serverId )
    {
        String sql = "select * from system_cluster_server where serverId=?";

        return pe.querySingleResultMap( sql, new Object[] { serverId } );
    }

    public Map querySingleClusterServerByHost( String host )
    {
        String sql = "select * from system_cluster_server where clusterUrl=?";

        return pe.querySingleResultMap( sql, new Object[] { host } );
    }

    public void deleteClusterServerById( Long serverId )
    {
        String sql = "delete from system_cluster_server where serverId=?";
        pe.update( sql, new Object[] { serverId } );
    }

    public static void clearClusterServer()
    {
        clusterServerCache.clearAllEntry();
    }
}
