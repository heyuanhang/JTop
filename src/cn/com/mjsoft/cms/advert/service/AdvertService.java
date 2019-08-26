package cn.com.mjsoft.cms.advert.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.advert.bean.AdvertConfigBean;
import cn.com.mjsoft.cms.advert.bean.AdvertContentBean;
import cn.com.mjsoft.cms.advert.bean.AdvertPositionBean;
import cn.com.mjsoft.cms.advert.dao.AdvertDao;
import cn.com.mjsoft.cms.advert.dao.vo.AdvertConfig;
import cn.com.mjsoft.cms.advert.dao.vo.AdvertContent;
import cn.com.mjsoft.cms.advert.dao.vo.AdvertPosition;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.content.dao.ContentDao;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.MathUtil;
import cn.com.mjsoft.framework.util.StringUtil;

public class AdvertService
{
    private static Logger log = Logger.getLogger( AdvertService.class );

    private static final Cache posAdvertWeightMap = new ClusterCacheAdapter( 5000,
        "advertService.posAdvertWeightMap" );

    private static final Map cacheManager = new HashMap();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    static
    {
        cacheManager.put( "queryAdvertContentBeanListByPosFlagAndDate", new ClusterCacheAdapter(
            200, "advertService.queryAdvertContentBeanListByPosFlagAndDate" ) );
    }

    private static AdvertService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private AdvertDao advertDao;

    private ContentDao contentDao;

    private AdvertService()
    {
        advertDao = new AdvertDao( mysqlEngine );
        contentDao = new ContentDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new AdvertService();
        }
    }

    public static AdvertService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public void addNewAdvertConfig( AdvertConfig config )
    {
        if( config == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            UpdateState updateState = advertDao.saveAdvertConfig( config );

            if( !updateState.haveKey() )
            {
                log.info( "[AdvertService] addNewAdvertConfig : 增加config失败, config:" + config );
                return;
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            clearCache();
        }

    }

    public void editAdvertCodeTemplate( AdvertConfig config )
    {
        if( config == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            advertDao.updateAdvertCodeTemplate( config.getAdvertCode(), config.getConfigId() );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            clearCache();
        }
    }

    public void editAdvertConfig( AdvertConfig config )
    {
        if( config == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            advertDao.updateAdvertConfig( config );

            // if( updateState.haveKey() )
            // {
            // Long configId = Long.valueOf( updateState.getKey() );
            //
            // AdvertConfigParam configParam = null;
            // for ( int i = 0; i < configParamList.size(); i++ )
            // {
            // configParam = ( AdvertConfigParam ) configParamList.get( i );
            // configParam.setConfigId( configId );
            // advertDao.saveAdvertConfigParam( configParam );
            // }
            // }
            // else
            // {
            // log
            // .info( "[AdvertService] addNewAdvertConfig : 增加config失败, config:"
            // + config );
            // return;
            // }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            clearCache();
        }

    }

    public AdvertConfigBean retrieveSingleAdvertConfigBeanByConfigId( Long configId )
    {
        return advertDao.querySingleAdvertConfigBeanByConfigId( configId );
    }

    public AdvertPositionBean retrieveSingleAdvertPositionBeanByPosId( Long posId )
    {
        return advertDao.querySingleAdvertPositionBeanByPosId( posId );
    }

    public Map retrieveSingleAdvertPositionMapByPosId( Long posId )
    {
        Map result = null;

        try
        {
            mysqlEngine.beginTransaction();

            AdvertConfigBean acb = advertDao.querySingleAdvertConfigBeanByPosId( posId );

            if( acb == null )
            {
                return null;
            }

            if( acb.getPosModelId() == null || acb.getPosModelId().longValue() < 1 )
            {
                result = advertDao.querySingleAdvertPositionInfoByPosId( posId );
            }
            else
            {
                DataModelBean model = metaDataService.retrieveSingleDataModelBeanById( acb
                    .getPosModelId() );

                ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
                    .retrieveSingleModelPerMysqlCodeBean( acb.getPosModelId() );

                result = advertDao.querySingleAdvertPositionInfoByPosId( posId, model, sqlCodeBean,
                    acb.getSiteId() );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        return result;
    }

    public AdvertContentBean retrieveSingleAdvertContentBeanByAdvertId( Long advertId )
    {
        return advertDao.querySingleAdvertContentBeanByAdvertId( advertId );
    }

    public Map retrieveSingleAdvertContentInfoByAdvertId( Long advertId )
    {
        Map result = null;

        try
        {
            mysqlEngine.beginTransaction();

            Map tempMap = advertDao.querySingleAdvertContentInfoByAdvertId( advertId );

            AdvertConfigBean acb = advertDao.querySingleAdvertConfigBeanByPosId( ( Long ) tempMap
                .get( "posId" ) );

            if( acb == null )
            {
                return null;
            }

            if( acb.getContentModelId() == null || acb.getContentModelId().longValue() < 1 )
            {
                result = tempMap;
            }
            else
            {
                DataModelBean model = metaDataService.retrieveSingleDataModelBeanById( acb
                    .getContentModelId() );

                ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
                    .retrieveSingleModelPerMysqlCodeBean( acb.getContentModelId() );

                result = advertDao.querySingleAdvertContentInfoByAdvertId( advertId, model,
                    sqlCodeBean, acb.getSiteId() );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        return result;

    }

    public AdvertContentBean retrieveSingleAdvertContentBeanByAdvertFlag( String flag )
    {
        return advertDao.querySingleAdvertContentBeanByAdvertFlag( flag );
    }

    public List retrieveAdvertConfigParamBeanList( Long configId, Long targetId, Integer type,
        Boolean isEdit )
    {
        List result = null;
        if( isEdit.booleanValue() )
        {
            if( Constant.ADVERT.ADVERT_CONFIG_CON_TYPE.equals( type ) )
            {
                // 如果是广告内容参数,所以传入的实际是PosId,需要取其配置ID
                result = advertDao.queryAdvertConfigParamAndValueBeanListByPosId( configId,
                    targetId, type );
            }
            else
            {
                result = advertDao
                    .queryAdvertConfigParamAndValueBeanList( configId, targetId, type );
            }

        }
        else
        {
            if( Constant.ADVERT.ADVERT_CONFIG_CON_TYPE.equals( type ) )
            {
                // 如果是广告内容参数,所以传入的实际是PosId,需要取其配置ID
                result = advertDao.queryAdvertConfigParamBeanListByPosId( configId, type );
            }
            else
            {
                result = advertDao.queryAdvertConfigParamBeanList( configId, type );
            }
        }
        return result;
    }

    public List retrieveAllAdvertConfigBeanList()
    {
        return advertDao.queryAllAdvertConfigBeanList();
    }

    public List retrieveAllAdvertPosBeanList()
    {
        return advertDao.queryAllAdvertPosBeanList();
    }

    public List retrieveAllAdvertPosInfoList( Long siteId )
    {
        return advertDao.queryAllAdvertPosInfoList( siteId );
    }

    public List retrieveAllAdvertContentBeanList()
    {
        return advertDao.queryAllAdvertContentBeanList();
    }

    public List retrieveAllAdvertContentInfoList( Long siteId )
    {
        return advertDao.queryAllAdvertContentInfoList( siteId );
    }

    public List retrieveAdvertContentBeanListByPosId( Long posId )
    {
        return advertDao.queryAdvertContentBeanListByPosId( posId );
    }

    public List retrieveAdvertContentInfoListByPosId( Long posId, Long siteId )
    {
        return advertDao.queryAdvertContentBeanListByPosId( posId, siteId );
    }

    public AdvertConfigBean retrieveSingleAdvertConnfigBeanByPosId( Long posId )
    {
        return advertDao.querySingleAdvertConfigBeanByPosId( posId );
    }

    public void deleteAdvertConfigAllInfo( Long configId )
    {

        try
        {
            mysqlEngine.beginTransaction();

            advertDao.deleteAdvertConfigByConfigId( configId );
            advertDao.deleteAdvertPositionByConfigId( configId );
            advertDao.deleteAdvertConfigParamByConfigId( configId );
            advertDao.deleteAdvertConfigParamValueByConfigId( configId );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearCache();
        }
    }

    public void addNewAdvertPositionAndExtendParamValue( AdvertPosition position,
        DataModelBean model, List filedBeanList, ModelPersistenceMySqlCodeBean sqlCodeBean,
        Map requestParams )
    {
        if( position == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            UpdateState updateState = advertDao.saveAdvertPosition( position );

            if( updateState.haveKey() )
            {
                // 扩展数据
                if( model != null && filedBeanList != null && sqlCodeBean != null )
                {
                    ModelFiledInfoBean bean = null;

                    List userDefineParamList = new ArrayList();
                    for ( int j = 0; j < filedBeanList.size(); j++ )
                    {
                        bean = ( ModelFiledInfoBean ) filedBeanList.get( j );
                        // 需要引入filed元数据来对不同类型字段进行对应处理

                        userDefineParamList.add( ServiceUtil.disposeDataModelFiledFromWeb( bean,
                            requestParams, null, true ) );
                    }

                    // 添加ID到最后位置

                    userDefineParamList.add( Long.valueOf( updateState.getKey() ) );

                    contentDao.saveOrUpdateModelContent( sqlCodeBean.getInsertSql(),
                        userDefineParamList.toArray() );

                }
            }
            else
            {
                log
                    .info( "[AdvertService] addNewAdvertPositionAndExtendParamValue : 增加Position失败, Position:"
                        + position );
                return;
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            clearCache();
        }
    }

    public void addNewAdvertContentAndExtendParamValue( AdvertContent advert,
        DataModelBean contentModel, List contentFiledBeanList,
        ModelPersistenceMySqlCodeBean contentSqlCodeBean, DataModelBean posModel,
        List posFiledBeanList, ModelPersistenceMySqlCodeBean posSqlCodeBean, Map requestParams )
    {
        if( advert == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            UpdateState updateState = advertDao.saveAdvert( advert );

            if( updateState.haveKey() )
            {
                AdvertPositionBean posBean = advertDao.querySingleAdvertPositionBeanByPosId( advert
                    .getPosId() );

                if( posBean == null )
                {
                    log
                        .error( "[AdvertService] addNewAdvertContentAndExtendParamValue : 广告所属版位信息丢失 posId:"
                            + advert.getPosId() );
                    return;
                }

                // 扩展数据
                if( contentModel != null && contentFiledBeanList != null
                    && contentSqlCodeBean != null )
                {
                    ModelFiledInfoBean bean = null;

                    List userDefineParamList = new ArrayList();
                    for ( int j = 0; j < contentFiledBeanList.size(); j++ )
                    {
                        bean = ( ModelFiledInfoBean ) contentFiledBeanList.get( j );
                        // 需要引入filed元数据来对不同类型字段进行对应处理

                        // 广告图片无需水印
                        userDefineParamList.add( ServiceUtil.disposeDataModelFiledFromWeb( bean,
                            requestParams, null, true ) );
                    }

                    // 添加ID到最后位置

                    userDefineParamList.add( Long.valueOf( updateState.getKey() ) );

                    contentDao.saveOrUpdateModelContent( contentSqlCodeBean.getInsertSql(),
                        userDefineParamList.toArray() );
                }

                // 生成最终广告代码

                AdvertConfigBean configBean = advertDao
                    .querySingleAdvertConfigBeanByConfigId( posBean.getConfigId() );

                if( configBean == null )
                {
                    log
                        .error( "[AdvertService] addNewAdvertContentAndExtendParamValue : 广告所属配置信息丢失 configId:"
                            + posBean.getConfigId() );
                    return;
                }

                AdvertContentBean adBean = advertDao.querySingleAdvertContentBeanByAdvertId( Long
                    .valueOf( updateState.getKey() ) );

                String templateCode = configBean.getAdvertCode();

                // 更新广告code
                Map posParamInfo = null;
                if( posModel != null && posFiledBeanList != null && posSqlCodeBean != null )
                {
                    posParamInfo = advertDao.querySingleAdvertPositionInfoByPosId( posBean
                        .getPosId(), posModel, posSqlCodeBean, posBean.getSiteId() );
                }

                Map contentParamInfo = null;

                if( contentModel != null && contentFiledBeanList != null
                    && contentSqlCodeBean != null )
                {
                    contentParamInfo = advertDao.querySingleAdvertContentInfoByAdvertId( advert
                        .getAdvertId(), contentModel, contentSqlCodeBean, posBean.getSiteId() );
                }

                // 更新广告code
                advertDao.updateAdvertCode( adBean.getAdvertId(), disposeAdvertTemplateCode(
                    templateCode, posBean, adBean, posParamInfo, contentParamInfo,
                    posFiledBeanList, contentFiledBeanList ) );

                // 更新所有权重百分比
                disposeAdvertWeight( advert );
            }
            else
            {
                log
                    .info( "[AdvertService] addNewAdvertContentAndExtendParamValue : 增加advert失败, advert:"
                        + advert );
                return;
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            clearCache();
        }
    }

    public void editAdvertContentAndExtendParamValue( AdvertContent advert,
        DataModelBean contentModel, List contentFiledBeanList,
        ModelPersistenceMySqlCodeBean contentSqlCodeBean, DataModelBean posModel,
        List posFiledBeanList, ModelPersistenceMySqlCodeBean posSqlCodeBean, Map requestParams )
    {
        if( advert == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            advertDao.updateAdvertContent( advert );

            AdvertPositionBean pos = advertDao.querySingleAdvertPositionBeanByPosId( advert
                .getPosId() );

            if( pos == null )
            {
                log
                    .error( "[AdvertService] editAdvertContentAndExtendParamValue : 广告所属版位信息丢失 posId:"
                        + advert.getPosId() );
                return;
            }

            // 扩展数据
            if( contentModel != null && contentFiledBeanList != null && contentSqlCodeBean != null )
            {
                ModelFiledInfoBean bean = null;

                List userDefineParamList = new ArrayList();

                for ( int j = 0; j < contentFiledBeanList.size(); j++ )
                {
                    bean = ( ModelFiledInfoBean ) contentFiledBeanList.get( j );
                    // 需要引入filed元数据来对不同类型字段进行对应处理

                    userDefineParamList.add( ServiceUtil.disposeDataModelFiledFromWeb( bean,
                        requestParams, null, true ) );
                }

                Map testInfo = contentDao
                    .querySingleUserDefineContentOnlyModelDataResultNotDisposeInfo(
                        contentSqlCodeBean, contentModel.getRelateTableName(), advert.getAdvertId() );

                // 添加ID到最后位置

                userDefineParamList.add( advert.getAdvertId() );

                if( testInfo.isEmpty() )
                {
                    contentDao.saveOrUpdateModelContent( contentSqlCodeBean.getInsertSql(),
                        userDefineParamList.toArray() );
                }
                else
                {
                    contentDao.saveOrUpdateModelContent( contentSqlCodeBean.getUpdateSql(),
                        userDefineParamList.toArray() );
                }
            }

            // 生成广告代码

            AdvertConfigBean configBean = advertDao.querySingleAdvertConfigBeanByConfigId( pos
                .getConfigId() );

            if( configBean == null )
            {
                log
                    .error( "[AdvertService] addNewAdvertContentAndExtendParamValue : 广告所属配置信息丢失 configId:"
                        + pos.getConfigId() );
                return;
            }

            AdvertContentBean adBean = advertDao.querySingleAdvertContentBeanByAdvertId( advert
                .getAdvertId() );

            String templateCode = configBean.getAdvertCode();

            Map posParamInfo = null;

            if( posModel != null && posFiledBeanList != null && posSqlCodeBean != null )
            {
                posParamInfo = advertDao.querySingleAdvertPositionInfoByPosId( pos.getPosId(),
                    posModel, posSqlCodeBean, pos.getSiteId() );
            }

            Map contentParamInfo = null;

            if( contentModel != null && contentFiledBeanList != null && contentSqlCodeBean != null )
            {
                contentParamInfo = advertDao.querySingleAdvertContentInfoByAdvertId( advert
                    .getAdvertId(), contentModel, contentSqlCodeBean, pos.getSiteId() );
            }

            // 更新广告code
            advertDao.updateAdvertCode( adBean.getAdvertId(), disposeAdvertTemplateCode(
                templateCode, pos, adBean, posParamInfo, contentParamInfo, posFiledBeanList,
                contentFiledBeanList ) );

            // 更新所有百分比

            disposeAdvertWeight( advert );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            clearCache();
        }

    }

    public void changeAdvertStatus( List idList, Integer status )
    {
        try
        {
            mysqlEngine.beginTransaction();

            long id = -1;

            for ( int i = 0; i < idList.size(); i++ )
            {
                id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );

                if( id < 0 )
                {
                    continue;
                }

                advertDao.updateAdvertStatus( Long.valueOf( id ), status );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearCache();
        }

    }

    public void editAdvertPositionAndExtendParamValue( AdvertPosition position,
        DataModelBean posModel, List posFiledBeanList,
        ModelPersistenceMySqlCodeBean posSqlCodeBean, DataModelBean contentModel,
        List contentFiledBeanList, ModelPersistenceMySqlCodeBean contentSqlCodeBean,
        Map requestParams )
    {
        if( position == null )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            advertDao.updateAdvertPosition( position );

            // 扩展数据
            if( posModel != null && posFiledBeanList != null && posSqlCodeBean != null )
            {
                ModelFiledInfoBean bean = null;

                List userDefineParamList = new ArrayList();
                for ( int j = 0; j < posFiledBeanList.size(); j++ )
                {
                    bean = ( ModelFiledInfoBean ) posFiledBeanList.get( j );
                    // 需要引入filed元数据来对不同类型字段进行对应处理

                    userDefineParamList.add( ServiceUtil.disposeDataModelFiledFromWeb( bean,
                        requestParams, null, true ) );
                }

                Map testInfo = contentDao
                    .querySingleUserDefineContentOnlyModelDataResultNotDisposeInfo( posSqlCodeBean,
                        posModel.getRelateTableName(), position.getPosId() );

                // 添加ID到最后位置

                userDefineParamList.add( position.getPosId() );

                if( testInfo.isEmpty() )
                {
                    contentDao.saveOrUpdateModelContent( posSqlCodeBean.getInsertSql(),
                        userDefineParamList.toArray() );
                }
                else
                {
                    contentDao.saveOrUpdateModelContent( posSqlCodeBean.getUpdateSql(),
                        userDefineParamList.toArray() );
                }

            }

            // 更新所有的当前位置的广告

            // 生成广告代码

            AdvertConfigBean configBean = advertDao.querySingleAdvertConfigBeanByConfigId( position
                .getConfigId() );

            if( configBean == null )
            {
                log
                    .error( "[AdvertService] editAdvertPositionAndExtendParamValue : 广告所属配置信息丢失 configId:"
                        + position.getConfigId() );
                return;
            }

            String templateCode = configBean.getAdvertCode();

            AdvertPositionBean posBean = advertDao.querySingleAdvertPositionBeanByPosId( position
                .getPosId() );

            if( posBean == null )
            {
                log
                    .error( "[AdvertService] editAdvertPositionAndExtendParamValue : 广告所属版位信息丢失 posId:"
                        + position.getPosId() );
                return;
            }

            List advertContentBeanList = advertDao.queryAdvertContentBeanListByPosId( position
                .getPosId() );

            Map posParamInfo = null;

            if( posModel != null )
            {
                posParamInfo = advertDao.querySingleAdvertPositionInfoByPosId( position.getPosId(),
                    posModel, posSqlCodeBean, position.getSiteId() );
            }

            Map contentParamInfo = null;

            mysqlEngine.startBatch();

            AdvertContentBean adBean = null;

            // 更新此版位下所有广告code
            for ( int i = 0; i < advertContentBeanList.size(); i++ )
            {
                adBean = ( AdvertContentBean ) advertContentBeanList.get( i );

                contentParamInfo = advertDao.querySingleAdvertContentInfoByAdvertId( adBean
                    .getAdvertId(), contentModel, contentSqlCodeBean, position.getSiteId() );

                advertDao.updateAdvertCode( adBean.getAdvertId(), disposeAdvertTemplateCode(
                    templateCode, posBean, adBean, posParamInfo, contentParamInfo,
                    posFiledBeanList, contentFiledBeanList ) );
            }

            mysqlEngine.executeBatch();

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            clearCache();
        }

    }

    public void deleteAdvertPositionAndParamValue( Long posId, SiteGroupBean site )
    {

        try
        {
            mysqlEngine.beginTransaction();

            AdvertConfigBean acb = advertDao.querySingleAdvertConfigBeanByPosId( posId );

            if( acb != null && acb.getPosModelId().longValue() > 0 )
            {
                DataModelBean adModel = metaDataService.retrieveSingleDataModelBeanById( acb
                    .getPosModelId() );

                if( adModel != null )
                {
                    /**
                     * 删除扩展数据
                     */
                    metaDataService.deleteAndClearDefModelInfo( posId, acb.getPosModelId(), site
                        .getSiteFlag() );

                }
            }

            // 删除版位信息
            advertDao.deleteAdvertPositionByPosId( posId );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearCache();
        }

    }

    public AdvertContentBean retrieveSingleAdvertContentBeanByPosIdOrderByImportance( String posFlag )
    {
        AdvertContentBean adbean = null;

        Timestamp cuurentDate = new Timestamp( DateAndTimeUtil.clusterTimeMillis() );

        Cache cachePosDate = ( Cache ) cacheManager
            .get( "queryAdvertContentBeanListByPosFlagAndDate" );

        String keyPosDate = "queryAdvertContentBeanListByPosFlagAndDate:"
            + posFlag
            + DateAndTimeUtil.getFormatDate( cuurentDate.getTime(),
                DateAndTimeUtil.DEAULT_FORMAT_YMD );

        try
        {
            mysqlEngine.beginTransaction();

            List advertContentBeanList = ( List ) cachePosDate.getEntry( keyPosDate );

            if( advertContentBeanList == null )
            {
                advertContentBeanList = advertDao.queryAdvertContentBeanListByPosFlagAndDate(
                    posFlag, cuurentDate );

                cachePosDate.putEntry( keyPosDate, advertContentBeanList );
            }

            AdvertPositionBean posBean = advertDao.querySingleAdvertPositionBeanByPosFlag( posFlag );

            if( posBean.getPosId().longValue() < 0 )
            {
                log
                    .info( "[AdvertService] editAdvertPositionAndExtendParamValue : 广告所属版位信息丢失 posFlag:"
                        + posFlag );
                return null;
            }

            if( Constant.ADVERT.ADVERT_SHOW_RANDOM.equals( posBean.getShowMode() ) )
            {
                if( advertContentBeanList.size() > 0 )
                {
                    adbean = ( AdvertContentBean ) advertContentBeanList.get( RandomUtils
                        .nextInt( advertContentBeanList.size() ) );
                }

            }
            else if( Constant.ADVERT.ADVERT_SHOW_WEIGHT.equals( posBean.getShowMode() ) )
            {
                List weightList = ( List ) posAdvertWeightMap.getEntry( posFlag );

                if( weightList != null )
                {
                    int rand = RandomUtils.nextInt( weightList.size() );// 随机分布

                    adbean = ( AdvertContentBean ) weightList.get( rand );
                }
                else
                {
                    // 当广告无效时,所有缓存会效果,在这次访问时重新建立pos的广告权重映射表

                    AdvertContentBean tempAdBean = null;

                    // 建立按照权重排序

                    weightList = new ArrayList();
                    int percent = 0;
                    for ( int i = 0; i < advertContentBeanList.size(); i++ )
                    {
                        tempAdBean = ( AdvertContentBean ) advertContentBeanList.get( i );

                        percent = tempAdBean.getPercentVal().intValue();
                        for ( int j = 0; j < percent; j++ )
                        {
                            weightList.add( tempAdBean );
                        }
                    }

                    posAdvertWeightMap.putEntry( posFlag, weightList );

                    int rand = RandomUtils.nextInt( weightList.size() );// 随机分布

                    adbean = ( AdvertContentBean ) weightList.get( rand );
                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }
        return adbean;
    }

    private String disposeAdvertTemplateCode( String template, AdvertPositionBean pos,
        AdvertContentBean ad, Map advertPosParam, Map advertContentParam, List posFieldList,
        List contentFieldList )
    {
        // 处理所有bean值以及extend值 ${pos.} ${ad.}
        String endAdvertCode = template;

        // pos bean参数
        endAdvertCode = StringUtil.replaceString( template, "${pos.width}", pos.getWidth()
            .toString(), false, false );

        endAdvertCode = StringUtil.replaceString( endAdvertCode, "${pos.height}", pos.getHeight()
            .toString(), false, false );

        endAdvertCode = StringUtil.replaceString( endAdvertCode, "${pos.flag}", pos.getPosFlag(),
            false, false );

        // ad bean参数
        endAdvertCode = StringUtil.replaceString( endAdvertCode, "${ad.flag}", ad.getAdFlag(),
            false, false );

        endAdvertCode = StringUtil.replaceString( endAdvertCode, "${ad.name}", ad.getAdName(),
            false, false );

        // target
        Integer targetFlag = ad.getTarget();
        String target = "_self";
        if( Constant.ADVERT.TARGET_BLANK.equals( targetFlag ) )
        {
            target = "_blank";
        }

        endAdvertCode = StringUtil.replaceString( endAdvertCode, "${target}", target, false, false );

        // base
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( pos.getSiteId() );

        endAdvertCode = StringUtil.replaceString( endAdvertCode, "${basePath}", site
            .getSiteTemplateUrl(), false, false );

        ModelFiledInfoBean bean = null;

        Object adcp = null;

        // extend参数 pos

        if( advertPosParam != null )
        {
            for ( int i = 0; i < posFieldList.size(); i++ )
            {
                bean = ( ModelFiledInfoBean ) posFieldList.get( i );
                String param = null;

                param = "${pos." + bean.getFieldSign() + "}";

                adcp = advertPosParam.get( bean.getFieldSign() );

                if( adcp == null )
                {
                    adcp = "";
                }

                endAdvertCode = StringUtil.replaceString( endAdvertCode, param, adcp.toString(),
                    false, false );
            }
        }

        // extend参数 content
        if( advertContentParam != null )
        {

            for ( int i = 0; i < contentFieldList.size(); i++ )
            {
                bean = ( ModelFiledInfoBean ) contentFieldList.get( i );
                String param = null;

                param = "${ad." + bean.getFieldSign() + "}";

                adcp = advertContentParam.get( bean.getFieldSign() );

                if( adcp == null )
                {
                    adcp = "";
                }

                endAdvertCode = StringUtil.replaceString( endAdvertCode, param, adcp.toString(),
                    false, false );
            }
        }

        return endAdvertCode;
    }

    // TODO 注意,当广告下线时(人工或线程),重新计算权重
    private void disposeAdvertWeight( AdvertContent advert )
    {
        List allPosAdvertList = advertDao.queryAdvertContentBeanListByPosId( advert.getPosId() );

        double imporSum = 1.0;

        AdvertContentBean adBean = null;
        // 求和
        for ( int i = 0; i < allPosAdvertList.size(); i++ )
        {
            imporSum += ( ( AdvertContentBean ) allPosAdvertList.get( i ) ).getImportance()
                .doubleValue();
        }

        Integer percent = null;
        for ( int x = 0; x < allPosAdvertList.size(); x++ )
        {
            adBean = ( AdvertContentBean ) allPosAdvertList.get( x );

            percent = Integer.valueOf( Double.valueOf(
                MathUtil.div( adBean.getImportance().doubleValue(), imporSum, 2 ) * 100 )
                .intValue() );
            advertDao.updateAdvertPercent( adBean.getAdvertId(), percent );
        }
    }

    public void deleteAdvertContent( List idList, SiteGroupBean site )
    {

        try
        {
            mysqlEngine.beginTransaction();

            AdvertConfigBean acb = null;

            // 删除所有扩展模型信息

            DataModelBean adModel = null;

            long id = -1;

            for ( int i = 0; i < idList.size(); i++ )
            {
                id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );

                if( id < 0 )
                {
                    continue;
                }

                acb = advertDao.querySingleAdvertConfigBeanByAdId( id );

                if( acb != null )
                {

                    adModel = metaDataService.retrieveSingleDataModelBeanById( acb
                        .getContentModelId() );

                    if( adModel != null )
                    {

                        /**
                         * 删除扩展数据
                         */
                        metaDataService.deleteAndClearDefModelInfo( id, acb.getContentModelId(),
                            site.getSiteFlag() );

                    }
                }

                // 删除广告
                advertDao.deleteAdvertContentById( Long.valueOf( id ) );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearCache();
        }

    }

    public boolean checkExistAdConfigAllCms( Long configId )
    {
        Long count = advertDao.querySingleAdvertPositionCountByCfgId( configId );

        if( count > 0 )
        {
            return true;
        }

        return false;
    }

    public static void clearCache()
    {
        Cache cache = ( Cache ) cacheManager.get( "queryAdvertContentBeanListByPosFlagAndDate" );

        cache.clearAllEntry();

        AdvertDao.clearCache();

        posAdvertWeightMap.clearAllEntry();

        
    }

    
}
