package cn.com.mjsoft.cms.search.dao;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.search.dao.vo.SearchIndexContentState;
import cn.com.mjsoft.framework.cache.Cache;

import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;

public class SearchDao
{
    private static Logger log = Logger.getLogger( SearchDao.class );

    private PersistenceEngine pe;

    private static Cache serKeyCountCache = new ClusterCacheAdapter( 300,
        "searchDao.serKeyCountCache" );

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public SearchDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public UpdateState saveIndexState( SearchIndexContentState vo )
    {
        return pe.save( vo );
    }

    public List queryIndexContentStateByFlag( Long cId, Long siteId, Long startId, Integer limit )
    {
        String sql = "select * from search_index_content_state where siteId=? and clusterId=? and indexStateId>? order by indexStateId asc limit ?";

        return pe.query( sql, new Object[] { siteId, cId, startId, limit },
            new SearchIndexContentStateBeanTransform() );
    }

    public void deleteIndexContentStateByLastId( Long clusterId, Long siteId, Long startId,
        Long lastId )
    {
        String sql = "delete from search_index_content_state where siteId=? and clusterId=? and indexStateId>=? and indexStateId<=?";

        pe.update( sql, new Object[] { siteId, clusterId, startId, lastId } );
    }

    public void deleteIndexContentStateBySiteId( Long siteId )
    {
        String sql = "delete from search_index_content_state where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public List querySearchConfigRelateSiteId()
    {
        String sql = "select siteId from search_config";

        return pe.queryResultMap( sql );
    }

    public Long querySearchKeyCount( String key )
    {
        String sql = "select count(*) from search_key_count where queryKey=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { key }, Long.class );
    }

    public Long querySearchKeyCountBySiteId( Long siteId )
    {
        String sql = "select count(*) from search_key_count where siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId }, Long.class );
    }

    public List querySearchKeyBySiteId( Long siteId, Long start, Integer size )
    {
        String sql = "select * from search_key_count where siteId=? order by count desc limit ?,?";

        String key = "querySearchKeyBySiteId:" + siteId + "|" + start + "|" + size;

        List result = ( List ) serKeyCountCache.getEntry( key );

        if( result == null )
        {
            result = pe.queryResultMap( sql, new Object[] { siteId, start, size } );

            serKeyCountCache.putEntry( key, result );
        }

        return result;
    }

    public void updateSearchKeyCount( String key, Integer count )
    {
        String sql = "update search_key_count set count=count+? where queryKey=?";

        pe.update( sql, new Object[] { count, key } );
    }

    public void saveNewSearchKeyCount( String key, Integer count, Long siteId )
    {
        String sql = "insert into search_key_count (queryKey, count, siteId) values (?, ?, ?)";

        pe.update( sql, new Object[] { key, count, siteId } );
    }

    public void deleteSearchKey( Long skId )
    {
        String sql = "delete from search_key_count where skId=?";

        pe.update( sql, new Object[] { skId } );
    }

    public static void clear()
    {
        serKeyCountCache.clearAllEntry();
    }

 
}
