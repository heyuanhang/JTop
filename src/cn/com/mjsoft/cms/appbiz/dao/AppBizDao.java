package cn.com.mjsoft.cms.appbiz.dao;

import cn.com.mjsoft.cms.appbiz.bean.SystemApiConfigBean;
import cn.com.mjsoft.cms.appbiz.dao.vo.SystemApiConfig;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;

import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppBizDao
{
    private static Map cacheManager = new HashMap();
    private PersistenceEngine pe;

    static
    {
        cacheManager.put( "querySingleBlockBean", new ClusterCacheAdapter(
            300, "appBizDao.querySingleBlockBean" )  );
    }

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public AppBizDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public void save( SystemApiConfig vo )
    {
        this.pe.save( vo );
    }

    public SystemApiConfigBean queryAppCfgBeanById( Long apiId )
    {
        String sql = "select * from system_api_config where apiId=?";

        return ( SystemApiConfigBean ) this.pe.querySingleBean( sql,
            new Object[] { apiId }, SystemApiConfigBean.class );
    }

    public SystemApiConfigBean queryAppCfgBeanByFlowPath( String flowPath )
    {
        String sql = "select * from system_api_config where flowPath=?";

        return ( SystemApiConfigBean ) this.pe.querySingleBean( sql,
            new Object[] { flowPath }, SystemApiConfigBean.class );
    }

    public void updateAppCfgById( SystemApiConfig vo )
    {
        String sql = "update system_api_config set apiName=?, flowPath=?, mustTok=?, mustEnc=?, mustSecTok=?, reqMethod=?, extBehaviorClass=? where apiId=?";

        this.pe.update( sql, vo );
    }

    public void updateAppCfgParamById( String apiParams, Long apiId )
    {
        String sql = "update system_api_config set apiParams=? where apiId=?";

        this.pe.update( sql, new Object[] { apiParams, apiId } );
    }

    public Long queryAppCfgCount()
    {
        String sql = "select count(*) from system_api_config";

        return ( Long ) this.pe.querySingleObject( sql, Long.class );
    }

    public List queryAllAppCfg( Long start, Integer size )
    {
        String sql = "select * from system_api_config order by apiId desc limit ?,?";

        return this.pe.queryBeanList( sql, new Object[] { start, size },
            SystemApiConfigBean.class );
    }

    public List queryAllAppCfg()
    {
        String sql = "select * from system_api_config";

        return this.pe.queryBeanList( sql, SystemApiConfigBean.class );
    }

    public List queryAppCfgBeanByName( String name )
    {
        String sql = "select * from system_api_config where apiName like '%"
            + name + "%'";

        return this.pe.queryBeanList( sql, SystemApiConfigBean.class );
    }

    public void deleteAppCfgById( Long apiId )
    {
        String sql = "delete from system_api_config where apiId=?";

        this.pe.update( sql, new Object[] { apiId } );
    }
}
