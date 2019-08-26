package cn.com.mjsoft.cms.advert.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.advert.bean.AdvertConfigBean;
import cn.com.mjsoft.cms.advert.bean.AdvertContentBean;
import cn.com.mjsoft.cms.advert.bean.AdvertPositionBean;
import cn.com.mjsoft.cms.advert.dao.vo.AdvertConfig;
import cn.com.mjsoft.cms.advert.dao.vo.AdvertConfigParam;
import cn.com.mjsoft.cms.advert.dao.vo.AdvertContent;
import cn.com.mjsoft.cms.advert.dao.vo.AdvertPosition;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.content.dao.ContentValueResultCallBack;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;

public class AdvertDao
{
    private static Map cacheManager = new HashMap();
    private PersistenceEngine pe;

    static
    {
        cacheManager.put( "querySingleAdvertPositionBeanByPosFlag", new ClusterCacheAdapter( 500,
            "advertDao.querySingleAdvertPositionBeanByPosFlag" ) );

        cacheManager.put( "querySingleAdvertContentBeanByAdvertFlag", new ClusterCacheAdapter(
            2500, "advertDao.querySingleAdvertContentBeanByAdvertFlag" ) );
    }

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public AdvertDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public UpdateState saveAdvertConfig( AdvertConfig vo )
    {
        return pe.save( vo );
    }

    public UpdateState saveAdvertPosition( AdvertPosition vo )
    {
        return pe.save( vo );
    }

    public UpdateState saveAdvert( AdvertContent vo )
    {
        return pe.save( vo );
    }

    public void updateAdvertConfig( AdvertConfig config )
    {
        String sql = "update advert_config set configName=?, configDesc=?, contentModelId=?, posModelId=?, userState=? where configId=?";
        pe.update( sql, config );
    }

    public void updateAdvertCodeTemplate( String code, Long configId )
    {
        String sql = "update advert_config set advertCode=? where configId=?";
        pe.update( sql, new Object[] { code, configId } );
    }

    public void updateAdvertPosition( AdvertPosition pos )
    {
        String sql = "update advert_position set posName=?, posFlag=?, configId=?, width=?, height=?, posDesc=?, useState=?, creator=?, target=?, showMode=? where posId=?";
        pe.update( sql, new Object[] { pos.getPosName(), pos.getPosFlag(), pos.getConfigId(),
            pos.getWidth(), pos.getHeight(), pos.getPosDesc(), pos.getUseState(), pos.getCreator(),
            pos.getTarget(), pos.getShowMode(), pos.getPosId() } );
    }

    public void updateAdvertContent( AdvertContent advert )
    {
        String sql = "update advert_content set adName=?, adFlag=?, posId=?, advertCode=?, showStartDate=?, showEndDate=?, importance=?, keyword=?, creator=?, target=?, useState=? where advertId=?";
        pe.update( sql, new Object[] { advert.getAdName(), advert.getAdFlag(), advert.getPosId(),
            advert.getAdvertCode(), advert.getShowStartDate(), advert.getShowEndDate(),
            advert.getImportance(), advert.getKeyword(), advert.getCreator(), advert.getTarget(),
            advert.getUseState(), advert.getAdvertId() } );
    }

    public void deleteParamValue( Integer configType, Long configId, Long targetId )
    {
        String sql = "delete from advert_param_value where configType=? and configId=? and targetId=?";
        pe.update( sql, new Object[] { configType, configId, targetId } );
    }

    public void saveAdvertConfigParam( AdvertConfigParam vo )
    {
        pe.save( vo );
    }

    public List queryAllAdvertConfigBeanList()
    {
        String sql = "select * from advert_config";

        return pe.query( sql, new AdvertConfigBeanTransform() );
    }

    public List queryAllAdvertPosBeanList()
    {
        String sql = "select * from advert_position";

        return pe.query( sql, new AdvertPositionBeanTransform() );
    }

    public List queryAllAdvertPosInfoList( Long siteId )
    {
        String sql = "select * from advert_position where siteId=? order by posId desc";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public List queryAllAdvertPosIdList( Long siteId )
    {
        String sql = "select posId from advert_position where siteId=?";

        return pe.querySingleCloumn( sql, new Object[] { siteId }, Long.class );
    }

    public List queryAllAdvertPosInfoList()
    {
        String sql = "select * from advert_position order by posId desc";

        return pe.queryResultMap( sql );
    }

    public List queryAllAdvertContentBeanList()
    {
        String sql = "select * from advert_content";

        return pe.query( sql, new AdvertContentBeanTransform() );
    }

    public List queryAllAdvertContentInfoList( Long siteId )
    {
        String sql = "select * from advert_content where siteId=? order by advertId desc";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public List queryAllAdvertContentInfoList()
    {
        String sql = "select * from advert_content order by advertId desc";

        return pe.queryResultMap( sql );
    }

    public List queryAdvertContentBeanListByPosId( Long posId, Long siteId )
    {
        String sql = "select * from advert_content where posId=? and siteId=? order by posId desc";

        return pe.query( sql, new Object[] { posId, siteId }, new AdvertContentBeanTransform() );
    }

    public List queryAdvertContentBeanListByPosId( Long posId )
    {
        String sql = "select * from advert_content where posId=? order by posId desc";

        return pe.query( sql, new Object[] { posId }, new AdvertContentBeanTransform() );
    }

    public List queryAdvertContentInfoListByPosId( Long posId )
    {
        String sql = "select * from advert_content where posId=?";

        return pe.queryResultMap( sql, new Object[] { posId } );
    }

    public List queryAdvertContentBeanListByPosFlagAndDate( String posFlag, Timestamp date )
    {
        String sql = "select * from advert_content where posId=(select posId from advert_position where posFlag=?) and showStartDate<? and showEndDate>? and useState=1";

        return pe.query( sql, new Object[] { posFlag, date, date },
            new AdvertContentBeanTransform() );
    }

    public AdvertConfigBean querySingleAdvertConfigBeanByConfigId( Long configId )
    {
        String sql = "select * from advert_config where configId=?";
        return ( AdvertConfigBean ) pe.querySingleRow( sql, new Object[] { configId },
            new AdvertConfigBeanTransform() );
    }

    public AdvertPositionBean querySingleAdvertPositionBeanByPosId( Long posId )
    {
        String sql = "select * from advert_position where posId=?";
        return ( AdvertPositionBean ) pe.querySingleRow( sql, new Object[] { posId },
            new AdvertPositionBeanTransform() );
    }

    public Map querySingleAdvertPositionInfoByPosId( Long posId )
    {
        String sql = "select * from advert_position where posId=?";
        return pe.querySingleResultMap( sql, new Object[] { posId } );
    }

    public Map querySingleAdvertPositionInfoByPosId( Long posId, DataModelBean model,
        ModelPersistenceMySqlCodeBean sqlCodeBean, Long siteId )
    {
        if(sqlCodeBean == null || model == null)
        {
            return null;
        }
        
        String sql = "select gmi.*, " + sqlCodeBean.getSelectColumn()
            + " from advert_position gmi left join " + model.getRelateTableName()
            + " tmp on tmp.contentId=gmi.posId  where gmi.posId=? ";

        return pe.querySingleResultMap( sql, new Object[] { posId },
            new ContentValueResultCallBack( siteId, null, model.getDataModelId() ) );
    }

    public Map querySingleAdvertContentInfoByAdvertId( Long advertId, DataModelBean model,
        ModelPersistenceMySqlCodeBean sqlCodeBean, Long siteId )
    {
        if(sqlCodeBean == null || model == null)
        {
            return null;
        }
        
        String sql = "select gmi.*, " + sqlCodeBean.getSelectColumn()
            + " from advert_content gmi left join " + model.getRelateTableName()
            + " tmp on tmp.contentId=gmi.advertId where gmi.advertId=? ";

        return pe.querySingleResultMap( sql, new Object[] { advertId },
            new ContentValueResultCallBack( siteId, null, model.getDataModelId() ) );
    }

    public Long querySingleAdvertPositionCountByCfgId( Long cfgId )
    {
        String sql = "select count(*) from advert_position where configId=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { cfgId }, Long.class );
    }

    public AdvertPositionBean querySingleAdvertPositionBeanByPosFlag( String posFlag )
    {
        String sql = "select * from advert_position where posFlag=?";

        Cache cachePos = ( Cache ) cacheManager.get( "querySingleAdvertPositionBeanByPosFlag" );

        String keyPos = posFlag;

        AdvertPositionBean posBean = ( AdvertPositionBean ) cachePos.getEntry( keyPos );
        if( posBean == null )
        {
            posBean = ( AdvertPositionBean ) pe.querySingleRow( sql, new Object[] { posFlag },
                new AdvertPositionBeanTransform() );
            if( posBean == null )
            {
                posBean = new AdvertPositionBean();
            }
            cachePos.putEntry( keyPos, posBean );
        }
        return posBean;
    }

    public AdvertContentBean querySingleAdvertContentBeanByAdvertId( Long advretId )
    {
        String sql = "select * from advert_content where advertId=?";
        return ( AdvertContentBean ) pe.querySingleRow( sql, new Object[] { advretId },
            new AdvertContentBeanTransform() );
    }

    public Map querySingleAdvertContentInfoByAdvertId( Long advretId )
    {
        String sql = "select * from advert_content where advertId=?";
        return pe.querySingleResultMap( sql, new Object[] { advretId } );
    }

    public AdvertContentBean querySingleAdvertContentBeanByAdvertFlag( String flag )
    {
        String sql = "select * from advert_content where adFlag=? and useState=1";

        Cache cache = ( Cache ) cacheManager.get( "querySingleAdvertContentBeanByAdvertFlag" );

        String key = "querySingleAdvertContentBeanByAdvertFlag:" + flag;

        AdvertContentBean result = ( AdvertContentBean ) cache.getEntry( key );
        if( result == null )
        {
            result = ( AdvertContentBean ) pe.querySingleRow( sql, new Object[] { flag },
                new AdvertContentBeanTransform() );
            if( result == null )
            {
                result = new AdvertContentBean();
            }
            cache.putEntry( key, result );
        }
        return result;
    }

    public void deleteAdvertConfigByConfigId( Long configId )
    {
        String sql = "delete from advert_config where configId=?";

        pe.update( sql, new Object[] { configId } );
    }

    public void deleteAdvertConfigParamByConfigId( Long configId )
    {
        String sql = "delete from advert_config_param where configId=?";

        pe.update( sql, new Object[] { configId } );
    }

    public void deleteAdvertConfigParamValueByConfigId( Long configId )
    {
        String sql = "delete from advert_param_value where configId=?";

        pe.update( sql, new Object[] { configId } );
    }

    public void deleteAdvertConfigParamValueByTargetId( Long targetId )
    {
        String sql = "delete from advert_param_value where targetId=?";

        pe.update( sql, new Object[] { targetId } );
    }

    public void deleteAdvertContentByPosId( Long posId )
    {
        String sql = "delete from advert_content where posId=?";

        pe.update( sql, new Object[] { posId } );
    }

    public void deleteAdvertContentById( Long adId )
    {
        String sql = "delete from advert_content where advertId=?";

        pe.update( sql, new Object[] { adId } );
    }

    public List queryAdvertConfigParamBeanList( Long configId, Integer type )
    {
        String sql = "select * from advert_config_param where configId=? and paramType=?";

        return pe
            .query( sql, new Object[] { configId, type }, new AdvertConfigParamBeanTransform() );
    }

    public List queryAdvertConfigParamBeanListByPosId( Long posId, Integer type )
    {
        String sql = "select acp.* from advert_config_param acp where acp.configId=(select ap.configId from advert_position ap where ap.posId=?) and acp.paramType=?";

        return pe.query( sql, new Object[] { posId, type }, new AdvertConfigParamBeanTransform() );
    }

    public List queryAdvertConfigParamAndValueBeanList( Long configId, Long targetId, Integer type )
    {
        String sql = "select * from advert_config_param acp left join advert_param_value apv on acp.paramType=apv.configType and acp.configId=apv.configId and acp.paramId=apv.paramId and apv.targetId=? where acp.configId=? and acp.paramType=?";

        return pe.query( sql, new Object[] { targetId, configId, type },
            new AdvertConfigParamAndValueBeanTransform() );
    }

    public List queryAdvertConfigParamAndValueBeanList( Long configId, Long posId, Long adId )
    {
        String sql = "select * from advert_config_param acp left join advert_param_value apv on acp.paramType=apv.configType and acp.configId=apv.configId and acp.paramId=apv.paramId and apv.targetId=? where acp.configId=? and acp.paramType=1 union select * from advert_config_param acp left join advert_param_value apv on acp.paramType=apv.configType and acp.configId=apv.configId and acp.paramId=apv.paramId and apv.targetId=? where acp.configId=? and acp.paramType=2";

        return pe.query( sql, new Object[] { posId, configId, adId, configId },
            new AdvertConfigParamAndValueBeanTransform() );
    }

    public List queryAdvertConfigParamAndValueBeanListByPosId( Long configId, Long targetId,
        Integer type )
    {
        String sql = "select * from advert_config_param acp left join advert_param_value apv on acp.paramType=apv.configType and acp.configId=apv.configId and acp.paramId=apv.paramId and apv.targetId=? where acp.configId=(select ap.configId from advert_position ap where ap.posId=?) and acp.paramType=?";

        return pe.query( sql, new Object[] { targetId, configId, type },
            new AdvertConfigParamAndValueBeanTransform() );
    }

    public void savePosExtendParam( Long paramId, Integer configType, Long configId, Long targetId,
        String value )
    {
        String sql = "insert into advert_param_value(paramId, configType, configId, targetId, paramValue) values (?,?,?,?,?)";
        pe.update( sql, new Object[] { paramId, configType, configId, targetId, value } );
    }

    public void deleteAdvertPositionByPosId( Long posId )
    {
        String sql = "delete from advert_position where posId=?";

        pe.update( sql, new Object[] { posId } );
    }

    public void deleteAdvertPositionByConfigId( Long configId )
    {
        String sql = "delete from advert_position where configId=?";

        pe.update( sql, new Object[] { configId } );
    }

    public AdvertConfigBean querySingleAdvertConfigBeanByPosId( Long posId )
    {
        String sql = "select * from advert_config where configId=(select ap.configId from advert_position ap where ap.posId=?)";

        return ( AdvertConfigBean ) pe.querySingleRow( sql, new Object[] { posId },
            new AdvertConfigBeanTransform() );
    }

    public AdvertConfigBean querySingleAdvertConfigBeanByAdId( Long adId )
    {
        String sql = "select * from advert_config where configId=(select ap.configId from advert_position ap where ap.posId = (select posId from advert_content where advertId=?))";

        return ( AdvertConfigBean ) pe.querySingleRow( sql, new Object[] { adId },
            new AdvertConfigBeanTransform() );
    }

    public void updateAdvertCode( Long advertId, String code )
    {
        String sql = "update advert_content set advertCode=? where advertId=?";

        pe.update( sql, new Object[] { code, advertId } );
    }

    public void updateAdvertPercent( Long advertId, Integer percent )
    {
        String sql = "update advert_content set percentVal=? where advertId=?";

        pe.update( sql, new Object[] { percent, advertId } );
    }

    public void updateAdvertStatus( Long advertId, Integer status )
    {
        String sql = "update advert_content set useState=? where advertId=?";

        pe.update( sql, new Object[] { status, advertId } );
    }

    public static void clearCache()
    {
        Cache cache = ( Cache ) cacheManager.get( "querySingleAdvertPositionBeanByPosFlag" );

        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "querySingleAdvertContentBeanByAdvertFlag" );

        cache.clearAllEntry();

         
    }

    
}
