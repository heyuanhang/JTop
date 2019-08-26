package cn.com.mjsoft.cms.cluster.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.cms.common.service.CMSRedisDB;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 根据集群和单机部署环境部署切换List存储源
 * 
 * @author MJ-Soft
 * 
 */
@SuppressWarnings( { "rawtypes", "unchecked" } )
public class ClusterListAdapter
{
    private List<Object> innerList = new ArrayList<Object>();

    private String redisListName;

    private Class objClass;

    private String mode = "inner";

    public ClusterListAdapter( String redisListName, Class objClass )
    {
        this.redisListName = redisListName;

        this.objClass = objClass;

        String cMode = SystemConfiguration.getInstance().getSystemConfig().getSysPro().getProperty(
            "cluster_mode" );

        mode = "true".equals( cMode ) ? "redis" : "inner";
    }

    public void add( Object val )
    {
        if( "inner".equals( mode ) )
        {
            innerList.add( val );
        }
        else if( "redis".equals( mode ) )
        {
            CMSRedisDB.addListVal( redisListName, JSONObject.toJSONString( val ) );
        }

    }

    public Object get( int index )
    {
        if( "inner".equals( mode ) )
        {
            return innerList.get( index );
        }
        else if( "redis".equals( mode ) )
        {
            return JSON.parseObject( CMSRedisDB.getListVal( redisListName, index ), objClass );
        }

        return null;

    }

    public List getList()
    {
        if( "inner".equals( mode ) )
        {
            return innerList;
        }
        else if( "redis".equals( mode ) )
        {
            List<String> jsonList = CMSRedisDB.getList( redisListName );

            List objList = new ArrayList( jsonList.size() );

            for ( String json : jsonList )
            {
                objList.add( JSON.parseObject( json, objClass ) );
            }

            return objList;
        }

        return null;
    }

    public long size()
    {
        if( "inner".equals( mode ) )
        {
            return innerList.size();
        }
        else if( "redis".equals( mode ) )
        {
            return CMSRedisDB.listSize( redisListName );
        }

        return 0;

    }

    public void clear()
    {
        if( "inner".equals( mode ) )
        {
            innerList.clear();
        }
        else if( "redis".equals( mode ) )
        {
            CMSRedisDB.emptyList( redisListName );
        }

    }

}
