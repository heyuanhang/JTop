package cn.com.mjsoft.cms.cluster.adapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cn.com.mjsoft.cms.common.service.CMSRedisDB;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 根据集群和单机部署环境部署切换Map存储源
 * 
 * @author MJ-Soft
 * 
 */

@SuppressWarnings( { "rawtypes", "unchecked" } )
public class ClusterMapAdapter
{

    private Map innerMap = new HashMap();

    private String redisMapName;

    private Class<? extends Object> keyClass;

    private Class<? extends Object> objClass;

    private String mode = "inner";

    public ClusterMapAdapter( String redisMapName, Class keyClass, Class objClass )
    {
        this.redisMapName = redisMapName;

        this.keyClass = keyClass;

        this.objClass = objClass;

        String cMode = SystemConfiguration.getInstance().getSystemConfig().getSysPro().getProperty(
            "cluster_mode" );

        mode = "true".equals( cMode ) ? "redis" : "inner";
    }

    public void put( Object key, Object val )
    {
        if( "inner".equals( mode ) )
        {
            innerMap.put( key, val );
        }
        else if( "redis".equals( mode ) )
        {

            CMSRedisDB.putMapVal( this.redisMapName, key != null ? key.toString() : "", JSONObject
                .toJSONString( val ) );
        }

    }

    public Object get( Object key )
    {
        if( "inner".equals( mode ) )
        {
            return innerMap.get( key );
        }
        else if( "redis".equals( mode ) )
        {
            return JSON.parseObject( CMSRedisDB.getMapVal( redisMapName, key != null ? key
                .toString() : "" ), objClass );
        }

        return null;

    }

    public boolean containsKey( Object key )
    {
        if( "inner".equals( mode ) )
        {
            return innerMap.containsKey( key );
        }
        else if( "redis".equals( mode ) )
        {
            return CMSRedisDB.existMapKey( redisMapName, key != null ? key.toString() : "" );
        }

        return false;

    }

    public void remove( Object key )
    {
        if( "inner".equals( mode ) )
        {
            innerMap.remove( key );
        }
        else if( "redis".equals( mode ) )
        {
            CMSRedisDB.delMapVal( redisMapName, key != null ? key.toString() : "" );
        }
    }

    public Map getMap()
    {
        if( "inner".equals( mode ) )
        {
            return innerMap;
        }
        else if( "redis".equals( mode ) )
        {
            Map<String, String> jsonMap = CMSRedisDB.getMap( redisMapName );

            Map objMap = new HashMap( jsonMap.size() );

            Iterator iter = jsonMap.entrySet().iterator();

            Entry<String, String> en = null;

            while ( iter.hasNext() )
            {
                en = ( Entry<String, String> ) iter.next();

                objMap.put( JSON.parseObject( en.getKey(), keyClass ), JSON.parseObject( en
                    .getValue(), objClass ) );
            }

            return objMap;
        }

        return null;
    }

    public long size()
    {
        if( "inner".equals( mode ) )
        {
            return innerMap.size();
        }
        else if( "redis".equals( mode ) )
        {
            return CMSRedisDB.mapSize( redisMapName );
        }

        return 0;
    }

    public void clear()
    {
        if( "inner".equals( mode ) )
        {
            innerMap.clear();
        }
        else if( "redis".equals( mode ) )
        {
            CMSRedisDB.delObj( redisMapName );
        }

    }

}
