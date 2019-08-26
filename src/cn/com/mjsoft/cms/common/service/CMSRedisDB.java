package cn.com.mjsoft.cms.common.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import cn.com.mjsoft.framework.util.StringUtil;

@SuppressWarnings( "unchecked" )
public class CMSRedisDB
{
    private static JedisPool pool = null;

    private static Map conf = new HashMap();

    /**
     * 构建redis连接池
     * 
     * @param ip
     * @param port
     * @return JedisPool
     */

    public static JedisPool initPool( String ip, String port, String pw, boolean clear )
    {
        if( pool == null )
        {
            conf.put( "pw", pw );

            int portVal = StringUtil.getIntValue( port, 6379 );

            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle( 10 );
            jedisPoolConfig.setMaxTotal( 100 );
            jedisPoolConfig.setMaxWaitMillis( 3000 );

            pool = new JedisPool( jedisPoolConfig, ip, portVal, 100000 );

        }

        if( clear )
        {
            System.err.println( "...... Redis DB CLEAR ......" );

            Jedis je = getJedisRes();

            je.flushAll();

            // 返还到连接池
            returnResource( pool, je );
        }

        return pool;
    }

    public static JedisPool initPool( String ip, boolean clear )
    {
        return initPool( ip, "", "", clear );
    }

    /**
     * 返还到连接池
     * 
     * @param pool
     * @param redis
     */
    public static void returnResource( JedisPool pool, Jedis redis )
    {
        if( redis != null )
        {
            pool.returnResource( redis );
        }
    }

    /**
     * 返还到连接池
     * 
     * @param pool
     * @param redis
     * @return
     */
    public static Jedis getJedisRes()
    {
        Jedis je = pool.getResource();

        String pw = ( String ) conf.get( "pw" );

        if( StringUtil.isStringNotNull( pw ) )
        {
            je.auth( pw );
        }

        return je;
    }

    /**
     * 存放map
     * 
     * @param key
     * @return
     */
    public static String setMap( String mapName, Map map )
    {
        String value = null;

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            value = jedis.hmset( mapName, map );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return value;
    }

    /**
     * 放置对象
     * 
     * @param object
     * @param key
     * @return
     */
    public static String setObj( String key, Object object )
    {
        if( object == null )
        {
            return "";
        }

        Jedis jedis = null;
        try
        {
            jedis = getJedisRes();

            return jedis.set( key.getBytes(), serialize( object ) );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return "";
    }

    /**
     * 获取对象
     * 
     * @param key
     * @return
     */
    public static Object getObj( String key )
    {
        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();

            byte[] value = jedis.get( key.getBytes() );

            if( value == null )
            {
                return null;
            }

            return unserialize( value );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return null;

    }

    /**
     * 删除对象
     * 
     * @param key
     * @return
     */
    public static boolean delObj( String key )
    {
        Jedis jedis = null;
        try
        {
            jedis = getJedisRes();

            return jedis.del( key.getBytes() ) > 0;
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return false;
    }

    /**
     * 删除对象,以key为前缀
     * 
     * @param key
     * @return
     */
    public static boolean delValByPKey( String key )
    {
        Jedis jedis = null;
        try
        {
            jedis = getJedisRes();

            Set sss = jedis.keys( ( key + "*" ).getBytes() );

            Iterator itor = sss.iterator();

            byte[] b = null;
            while ( itor.hasNext() )
            {
                b = ( byte[] ) itor.next();

                jedis.del( b );
            }

            return jedis.del( "*" + key.getBytes() ) > 0;
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return false;
    }

    /**
     * 设置map值
     * 
     * @param key
     * @return
     */
    public static void setVal( String key, String val )
    {

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            jedis.set( key, val );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

    }

    /**
     * 取得map值
     * 
     * @param key
     * @return
     */
    public static String getVal( String key )
    {
        String value = null;

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            value = jedis.get( key );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return value;
    }

    /**
     * 删除map值
     * 
     * @param key
     * @return
     */
    public static void delVal( String key )
    {

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            jedis.del( key );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

    }

    /**
     * 取得map值
     * 
     * @param key
     * @return
     */
    public static String getMapVal( String mapName, String key )
    {
        String value = null;

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            value = jedis.hget( mapName, key );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return value;
    }

    /**
     * 取得map值
     * 
     * @param key
     * @return
     */
    public static Object getMapVal( byte[] mapName, byte[] key )
    {
        Object value = null;

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            value = unserialize( jedis.hget( mapName, key ) );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return value;
    }

    /**
     * 取得map值
     * 
     * @param key
     * @return
     */
    public static Map<String, String> getMap( String mapName )
    {

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            return jedis.hgetAll( mapName );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return null;
    }

    /**
     * 取得map值
     * 
     * @param key
     * @return
     */
    public static Map<byte[], byte[]> getMap( byte[] mapName )
    {

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            return jedis.hgetAll( mapName );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return null;
    }

    /**
     * 设置map值
     * 
     * @param key
     * @return
     */
    public static void putMapVal( String mapName, String key, String val )
    {

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            jedis.hset( mapName, key, val );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

    }

    /**
     * 设置map值
     * 
     * @param key
     * @return
     */
    public static void putMapVal( byte[] mapName, byte[] key, byte[] val )
    {

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            jedis.hset( mapName, key, val );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

    }

    /**
     * 删除map值
     * 
     * @param key
     * @return
     */
    public static void delMapVal( String mapName, String key )
    {

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            jedis.hdel( mapName, key );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

    }

    /**
     * 删除map值
     * 
     * @param key
     * @return
     */
    public static void delMapVal( byte[] mapName, byte[] key )
    {

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            jedis.hdel( mapName, key );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

    }

    /**
     * map数据值数量
     * 
     * @param key
     * @return
     */
    public static Long mapSize( String mapName )
    {

        Jedis jedis = null;

        Long len = Long.valueOf( 0 );

        try
        {

            jedis = getJedisRes();
            len = jedis.hlen( mapName );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return len;
    }

    /**
     * map数据值数量
     * 
     * @param key
     * @return
     */
    public static Long mapSize( byte[] mapName )
    {

        Jedis jedis = null;

        Long len = Long.valueOf( 0 );

        try
        {

            jedis = getJedisRes();
            len = jedis.hlen( mapName );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return len;
    }

    /**
     * map中是否存在key
     * 
     * @param key
     * @return
     */
    public static boolean existMapKey( String mapName, String key )
    {

        if( key == null )
        {
            return false;
        }

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            return jedis.hexists( mapName, key );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return false;
    }

    /**
     * map中是否存在key
     * 
     * @param key
     * @return
     */
    public static boolean existMapKey( byte[] mapName, byte[] key )
    {

        if( key == null )
        {
            return false;
        }

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            return jedis.hexists( mapName, key );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return false;
    }

    /**
     * 设置list值
     * 
     * @param key
     * @return
     */
    public static void addListVal( String listName, String val )
    {

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            jedis.rpush( listName, val );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

    }

    /**
     * 返回list
     * 
     * @param key
     * @return
     */
    public static List<String> getList( String listName )
    {

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            return jedis.lrange( listName, 0, -1 );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return null;

    }

    /**
     * 返回list指定位置的值
     * 
     * @param key
     * @return
     */
    public static String getListVal( String listName, long index )
    {

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            return jedis.lindex( listName, index );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return null;

    }

    /**
     * 返回list数量
     * 
     * @param key
     * @return
     */
    public static Long listSize( String listName )
    {
        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            return jedis.llen( listName );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return 0l;

    }

    /**
     * 清空list
     * 
     * @param key
     * @return
     */
    public static void emptyList( String listName )
    {
        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            jedis.ltrim( listName, 1, 0 );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

    }

    /**
     * 是否存在某对象
     * 
     * @param key
     * @return
     */
    public static boolean exist( String name )
    {
        boolean value = false;

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            value = jedis.exists( name );
        }
        catch ( Exception e )
        {
            // 释放redis对象

            e.printStackTrace();
            pool.returnBrokenResource( jedis );
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return value;
    }

    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
    public static String get( String key )
    {
        String value = null;

        Jedis jedis = null;
        try
        {

            jedis = getJedisRes();
            value = jedis.get( key );
        }
        catch ( Exception e )
        {
            // 释放redis对象
            pool.returnBrokenResource( jedis );
            e.printStackTrace();
        }
        finally
        {
            // 返还到连接池
            returnResource( pool, jedis );
        }

        return value;
    }

    public static byte[] serialize( Object object )
    {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try
        {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream( baos );
            oos.writeObject( object );
            byte[] bytes = baos.toByteArray();
            return bytes;
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                baos.close();
                oos.close();
            }
            catch ( Exception e )
            {

            }

        }
        return null;
    }

    public static Object unserialize( byte[] bytes )
    {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try
        {
            // 反序列化
            bais = new ByteArrayInputStream( bytes );
            ois = new ObjectInputStream( bais );
            return ois.readObject();
        }
        catch ( Exception e )
        {

            e.printStackTrace();
        }
        finally
        {
            try
            {

                if( bais != null )
                    bais.close();
                if( ois != null )
                    ois.close();
            }
            catch ( Exception e )
            {

            }

        }
        return null;
    }

    public static void main( String args[] )
    { }
}
