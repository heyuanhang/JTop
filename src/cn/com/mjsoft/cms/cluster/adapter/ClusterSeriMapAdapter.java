package cn.com.mjsoft.cms.cluster.adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cn.com.mjsoft.cms.common.service.CMSRedisDB;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;

/**
 * 根据集群和单机部署环境部署切换Map存储源,序列化对象方式
 * 
 * @author MJ-Soft
 * 
 */

@SuppressWarnings( { "rawtypes", "unchecked" } )
public class ClusterSeriMapAdapter
{

    private Map innerMap = new HashMap();

    private String redisMapName;

    private String mode = "inner";

    public ClusterSeriMapAdapter( String redisMapName )
    {
        this.redisMapName = redisMapName;

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
            CMSRedisDB.putMapVal( this.redisMapName.getBytes(), key.toString().getBytes(), serialize( val ) );
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
            return CMSRedisDB.getMapVal( this.redisMapName.getBytes(), ( key != null ? key
                .toString() : "" ).getBytes() );
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
            return CMSRedisDB.existMapKey( redisMapName.getBytes(), ( key != null ? key.toString()
                : "" ).getBytes() );
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
            CMSRedisDB.delMapVal( redisMapName.getBytes(), ( key != null ? key.toString() : "" )
                .getBytes() );
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
            Map<byte[], byte[]> jsonMap = CMSRedisDB.getMap( redisMapName.getBytes() );

            Map objMap = new HashMap( jsonMap.size() );

            Iterator iter = jsonMap.entrySet().iterator();

            Entry<byte[], byte[]> en = null;

            while ( iter.hasNext() )
            {
                en = ( Entry<byte[], byte[]> ) iter.next();

                objMap.put( unserizlize( en.getKey() ), unserizlize( en.getValue() ) );
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
            return CMSRedisDB.mapSize( redisMapName.getBytes() );
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

    // 序列化
    public static byte[] serialize( Object obj )
    {
        ObjectOutputStream obi = null;
        ByteArrayOutputStream bai = null;
        try
        {
            bai = new ByteArrayOutputStream();
            obi = new ObjectOutputStream( bai );
            obi.writeObject( obj );
            byte[] byt = bai.toByteArray();
            return byt;
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        return null;
    }

    // 反序列化
    public static Object unserizlize( byte[] byt )
    {
        ObjectInputStream oii = null;
        ByteArrayInputStream bis = null;
        bis = new ByteArrayInputStream( byt );
        try
        {
            oii = new ObjectInputStream( bis );
            Object obj = oii.readObject();
            return obj;
        }
        catch ( Exception e )
        {

            e.printStackTrace();
        }

        return null;
    }

}
