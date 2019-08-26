package cn.com.mjsoft.cms.metadata.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.cms.channel.controller.ListCommendTypeInfoTreeController;
import cn.com.mjsoft.cms.channel.controller.ListContentClassInfoTreeController;
import cn.com.mjsoft.cms.channel.dao.ChannelDao;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.content.dao.ContentDao;
import cn.com.mjsoft.cms.content.dao.vo.PhotoGroupInfo;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.guestbook.bean.GuestbookConfigBean;
import cn.com.mjsoft.cms.guestbook.service.GuestbookService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.MetadataValueSnapshotBean;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.bean.ModelValidateConfigBean;
import cn.com.mjsoft.cms.metadata.dao.MetaDataDao;
import cn.com.mjsoft.cms.metadata.dao.vo.DataModel;
import cn.com.mjsoft.cms.metadata.dao.vo.ModelFiledMetadata;
import cn.com.mjsoft.cms.metadata.dao.vo.ModelHtmlConfig;
import cn.com.mjsoft.cms.metadata.dao.vo.PathInjectAssist;
import cn.com.mjsoft.cms.organization.bean.SystemOrganizationBean;
import cn.com.mjsoft.cms.organization.dao.OrgDao;
import cn.com.mjsoft.cms.resources.bean.SiteResourceBean;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.search.dao.vo.SearchIndexContentState;
import cn.com.mjsoft.cms.search.service.SearchService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.support.ServiceState;

public class MetaDataService
{
    private static Logger log = Logger.getLogger( MetaDataService.class );

    public static Map cacheManager = new HashMap();
    static
    {
        cacheManager.put( "retrieveModelFiledInfoBeanList", new ClusterCacheAdapter( 600,
            "metaDataService.retrieveModelFiledInfoBeanList" ) );

        // 此缓存为系统级别,无需更新
        cacheManager.put( "retrieveSystemHtmlElementInfo", new ClusterCacheAdapter( 5,
            "metaDataService.retrieveSystemHtmlElementInfo" ) );

        cacheManager.put( "retrieveAllDataModelBeanList", new ClusterCacheAdapter( 5,
            "metaDataService.retrieveAllDataModelBeanList" ) );

        cacheManager.put( "retrieveSingleModelPerMysqlCodeBean", new ClusterCacheAdapter( 300,
            "metaDataService.retrieveSingleModelPerMysqlCodeBean" ) );
        cacheManager.put( "retrieveSystemModelFiledInfoBeanList", new ClusterCacheAdapter( 2,
            "metaDataService.retrieveSystemModelFiledInfoBeanList" ) );

        cacheManager.put( "retrieveAllModelValidateConfigBean", new ClusterCacheAdapter( 2,
            "metaDataService.retrieveAllModelValidateConfigBean" ) );

        cacheManager.put( "retrieveSingleModelValidateConfigBean", new ClusterCacheAdapter( 50,
            "metaDataService.retrieveSingleModelValidateConfigBean" ) );

        cacheManager.put( "retrieveContentPathInjectAssistInfo", new ClusterCacheAdapter( 60,
            "metaDataService.retrieveContentPathInjectAssistInfo" ) );

        cacheManager.put( "retrieveAllSearchFieldStrArrayMode", new ClusterCacheAdapter( 2,
            "metaDataService.retrieveAllSearchFieldStrArrayMode" ) );
    }

    public static Cache listFormDataCache = new ClusterCacheAdapter( 10000,
        "metaDataService.listFormDataCache" );

    public static Cache listCountCache = new ClusterCacheAdapter( 10000,
        "metaDataService.listCountCache" );

    public static Cache formDataCache = new ClusterCacheAdapter( 15000,
        "metaDataService.formDataCache" );

    private ResourcesService resService = ResourcesService.getInstance();

    private SearchService searchService = SearchService.getInstance();

    private static MetaDataService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    public MetaDataDao metaDataDao;

    private ContentDao contentDao = null;

    private OrgDao orgDao;

    private MetaDataService()
    {
        metaDataDao = new MetaDataDao( mysqlEngine );

        contentDao = new ContentDao( mysqlEngine );

        orgDao = new OrgDao( mysqlEngine );

    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new MetaDataService();
        }
    }

    public static MetaDataService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public void createDataModel( DataModel dataModel )
    {
        if( dataModel == null )
        {
            log.info( "createDataModel() : dataModel为null" );
            return;
        }

        // 数据库元数据生成
        dataModel.setRelateTableName( Constant.METADATA.PREFIX_TABLE_NAME
            + dataModel.getModelSign() );

        try
        {
            mysqlEngine.beginTransaction();

            UpdateState updateState = metaDataDao.saveDataModel( dataModel );

            if( updateState.getRow() < 0 )
            {
                throw new FrameworkException( "增加内容模型失败" );
            }

            updateState = metaDataDao.createModelRelatingMysqlTable( dataModel );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            resetCache();
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
        }
    }

    public void editDataModel( DataModel dataModel )
    {

        if( dataModel == null )
        {
            log.info( "editDataModel() : dataModel为null" );
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            metaDataDao.updateDataModelBaseInfo( dataModel );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            resetCache();
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
        }
    }

    public void updateFieldMetadataDefVal( DataModelBean model, String fieldName, String defVal )
    {
        metaDataDao.updateColumnDefVal( model.getRelateTableName(), fieldName, defVal );
    }

    public void updateFieldMetadataDefValAndId( DataModelBean model, String fieldName,
        String defVal, Long contentId )
    {
        metaDataDao.updateColumnDefValById( model.getRelateTableName(), fieldName, defVal,
            contentId );
    }

    /**
     * 获取所有模型信息
     * 
     * @param currentInfo
     * @param useState
     * @return
     */
    public List retrieveAllDataModelBeanList( Integer useState, Integer modelType, Long siteId,
        String showMode )
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveAllDataModelBeanList" );

        String key = "retrieveAllDataModelBeanList:" + useState + ":" + modelType + "|" + siteId
            + "|" + showMode;

        List result = ( List ) cache.getEntry( key );

        if( result == null )
        {
            if( Constant.METADATA.MD_IS_ALL_STATE.equals( useState ) )// 任意
            {
                if( Constant.METADATA.P_MODE.equals( showMode ) )// 私有
                {
                    result = metaDataDao.queryAllDataModelBeanListByModelTypeAndSiteIdPrivateMode(
                        modelType, siteId, Constant.COMMON.ON );
                }
                else if( Constant.METADATA.S_MODE.equals( showMode ) )// 共用
                {
                    result = metaDataDao.queryAllDataModelBeanListByModelTypeAndSiteIdPrivateMode(
                        modelType, siteId, Constant.COMMON.OFF );
                }
                else
                {
                    result = metaDataDao.queryAllDataModelBeanListByModelTypeAndSiteId( modelType,
                        siteId );
                }
            }
            else
            {
                // useState永远为ON,此逻辑将不会到达
                result = metaDataDao.queryAllDataModelBeanListWithStateAndSiteId( useState,
                    modelType, siteId );
            }

            cache.putEntry( key, result );
        }

        return result;

    }

    public ServiceState createOrEditDataModelFiledMetadataAndHtmlConfig( ModelHtmlConfig config,
        ModelFiledMetadata metadata, boolean editMode )
    {
        if( config == null || config.getHtmlElementId().longValue() < 0 )
        {
            return null;
        }

        if( metadata == null )
        {
            return null;
        }

        prefixDisposeModelFiledMetadata( config, metadata );

        // 对已经存在的模型表进行结构操作,当结构表形成,且不对原数据产生破坏的情况下,才可表示更新成功
        try
        {
            mysqlEngine.beginTransaction();

            // 持久层元数据处理
            metadata.setRelateFiledName( Constant.METADATA.PREFIX_COLUMN_NAME
                + metadata.getFiledSign() );

            // check 字段名称重复
            boolean haveSameFiled = metaDataDao.queryHaveSameFiledSign( metadata );

            if( haveSameFiled )
            {
                if( !editMode )
                {
                    log.info( "字段名称重复,metadata:" + metadata );
                    return new ServiceState( ServiceState.ERROR, "已存在名为" + metadata.getFiledSign()
                        + "的字段标识!" );
                }
            }

            DataModelBean model = metaDataDao.querySingleDataModelBeanById( metadata
                .getDataModelId() );

            if( model == null )
            {
                log.info( "模型主数据无法获取,modelId:" + metadata.getDataModelId() );
                return new ServiceState( ServiceState.ERROR, "模型主数据无法获取,modelId:"
                    + metadata.getDataModelId() );
            }

            String modelTableName = null;
            String modelFiledName = null;
            String modelFiledSign = null;
            Integer modelFiledCharLength = null;

            // 暂为MYSQL的类型比较
            Integer modelFiledDataType = null;
            Double maxModelOrderId = null;
            Long newKey = null;

            if( editMode )
            {
                // 注意:必须在改动结构表之前获取原字段标识!
                modelTableName = metaDataDao.querySingleModelTableName( metadata.getMetaDataId() );
                modelFiledName = metaDataDao.querySingleFiledName( metadata.getMetaDataId() );
                modelFiledSign = metaDataDao.querySingleFiledSign( metadata.getMetaDataId() );
                modelFiledDataType = metaDataDao
                    .querySingleFiledDataType( metadata.getMetaDataId() );
                modelFiledCharLength = metaDataDao.querySinglFiledCharLength( metadata
                    .getMetaDataId() );

                metaDataDao.updateModelMetadata( metadata );

                metaDataDao.updateModelFiledHtmlConfig( config );

                // 是否变由普通字段为资源类型

                // 若编辑主编辑器改动为非主编辑器,则更新为普通模型资源类型
                if( ( config.getMainEditor() == null || Constant.COMMON.OFF.equals( config
                    .getMainEditor() ) )
                    && model.getMainEditorFieldSign().equals( metadata.getFiledSign() )
                    && Constant.METADATA.MODEL_RES_ARTICLE.equals( model.getModelResType() ) )
                {
                    metaDataDao.updateModelResType( metadata.getDataModelId(),
                        Constant.METADATA.MODEL_RES_NO );

                    metaDataDao.updateModelMainEditorFieldSign( metadata.getDataModelId(), null );
                }

                // 若主字段名发生变化,改动模型对应信息
                if( Constant.COMMON.ON.equals( config.getMainEditor() ) )
                {
                    metaDataDao.updateModelResType( metadata.getDataModelId(),
                        Constant.METADATA.MODEL_RES_ARTICLE );

                    metaDataDao.updateModelMainEditorFieldSign( metadata.getDataModelId(), metadata
                        .getFiledSign() );
                }
            }
            else
            {
                // 取最大的orderId,注意只需要Integer部分
                maxModelOrderId = metaDataDao.queryMaxDataModelOrderId( metadata.getDataModelId() );

                // 设定新的orderId
                if( maxModelOrderId == null )
                {
                    // 当前模型无字段
                    metadata.setOrderId( Double.valueOf( 1 ) );
                }
                else
                {
                    metadata.setOrderId( Double.valueOf( maxModelOrderId.intValue() + 1 ) );
                }

                UpdateState updateState = metaDataDao.saveModelMetadata( metadata );

                if( updateState.haveKey() )
                {
                    // 若模型存在主编辑器类型字段，模型资源将变为文章类型,主编辑器意味着支持内容分页
                    if( Constant.COMMON.ON.equals( config.getMainEditor() ) )
                    {
                        // 若存在主编辑器字段,意味着已有主字段
                        if( StringUtil.isStringNotNull( model.getMainEditorFieldSign() ) )
                        {
                            config.setMainEditor( Constant.COMMON.OFF );
                        }
                        else
                        {
                            metaDataDao.updateModelResType( metadata.getDataModelId(),
                                Constant.METADATA.MODEL_RES_ARTICLE );

                            metaDataDao.updateModelMainEditorFieldSign( metadata.getDataModelId(),
                                metadata.getFiledSign() );
                        }
                    }

                    newKey = Long.valueOf( updateState.getKey() );
                    config.setMetaDataId( newKey );
                    metaDataDao.saveModelFiledHtmlConfig( config );
                }
                else
                {
                    log.error( "数据库增加元数据失败!" );
                    return new ServiceState( ServiceState.ERROR, "无法增加模型元数据" );
                }

            }

            // 附加数据表字段处理

            String targetModelTableName = metaDataDao.queryTargetModelTableNameByModelId( metadata
                .getDataModelId() );

            if( StringUtil.isStringNull( targetModelTableName ) )
            {
                log.info( "目标数据表名为空,metadata:" + metadata );
                return new ServiceState( ServiceState.ERROR, "模型附加表丢失!" );
            }

            /**
             * 生成对应SQL
             */
            List targetFiledList = metaDataDao.queryUserDefinedModelFiledInfoBeanList( metadata
                .getDataModelId() );

            List filedNameList = new ArrayList();

            // String idName = targetModelTableName.substring(
            // targetModelTableName.lastIndexOf( "_" ) + 1,
            // targetModelTableName.length() )
            // + "Id";
            String idName = Constant.METADATA.CONTENT_ID_NAME;

            Set innerColumnSet = new HashSet();
            ModelFiledInfoBean mfInfoBean;
            for ( int i = 0; i < targetFiledList.size(); i++ )
            {
                mfInfoBean = ( ModelFiledInfoBean ) targetFiledList.get( i );
                filedNameList.add( mfInfoBean.getRelateFiledName() );

                if( Constant.METADATA.INNER_DATA == mfInfoBean.getHtmlElementId().intValue() )
                {
                    innerColumnSet.add( mfInfoBean.getRelateFiledName() );
                }

                // 时间排序字段要新加辅助字段
                if( Constant.METADATA.MYSQL_DATETIME.equals( mfInfoBean.getPerdureType() )
                    && Constant.COMMON.ON.equals( mfInfoBean.getOrderFlag() ) )
                {
                    filedNameList.add( Constant.METADATA.PREFIX_DT_ORDER_COLUMN_NAME
                        + mfInfoBean.getFieldSign() );
                }
            }

            // 最后加入ID filed
            filedNameList.add( idName );

            EntitySqlBridge sqlBridge = new EntitySqlBridge( targetModelTableName, idName,
                Constant.METADATA.PREFIX_QUERY_CACHE_NAME, Constant.METADATA.PREFIX_COLUMN_NAME,
                filedNameList, innerColumnSet, EntitySqlBridge.ASSIGNED );

            if( metaDataDao.queryHaveMetedataEntitySql( metadata.getDataModelId() ).intValue() == 0 )
            {
                metaDataDao.saveMetedataEntitySql( sqlBridge, metadata.getDataModelId() );
            }
            else
            {
                metaDataDao.updateMetedataEntitySql( sqlBridge, metadata.getDataModelId() );
            }

            /**
             * 若为需要特殊处理的上传和时间类型,则需要资源类型注册
             */
            // 先删除,如果存在的话
            metaDataDao.deletePathInjectAssistByMdId( metadata.getMetaDataId() );

            // 若为特殊类型,添加到辅助表
            if( editMode )
            {
                newKey = metadata.getMetaDataId();
            }

            PathInjectAssist piAssist = null;
            if( Constant.METADATA.UPLOAD_IMG == config.getHtmlElementId().intValue() )
            {
                piAssist = new PathInjectAssist( metadata.getDataModelId(),
                    metadata.getFiledSign(), Constant.SITE_CHANNEL.RES_IMAGE, newKey );
            }
            if( Constant.METADATA.UPLOAD_IMG_GROUP == config.getHtmlElementId().intValue() )
            {
                piAssist = new PathInjectAssist( metadata.getDataModelId(),
                    metadata.getFiledSign(), Constant.SITE_CHANNEL.RES_IMG_GROUP, newKey );
            }
            else if( Constant.METADATA.UPLOAD_FILE == config.getHtmlElementId().intValue() )
            {
                piAssist = new PathInjectAssist( metadata.getDataModelId(),
                    metadata.getFiledSign(), Constant.SITE_CHANNEL.RES_FILE, newKey );
            }
            else if( Constant.METADATA.UPLOAD_MEDIA == config.getHtmlElementId().intValue() )
            {
                piAssist = new PathInjectAssist( metadata.getDataModelId(),
                    metadata.getFiledSign(), Constant.SITE_CHANNEL.RES_MEDIA, newKey );
            }
            else if( Constant.METADATA.DATE == config.getHtmlElementId().intValue()
                || Constant.METADATA.TIME == config.getHtmlElementId().intValue()
                || Constant.METADATA.DATETIME == config.getHtmlElementId().intValue() )
            {
                piAssist = new PathInjectAssist( metadata.getDataModelId(),
                    metadata.getFiledSign(), Constant.SITE_CHANNEL.RES_DATETIME, newKey );
            }

            metaDataDao.saveNewPathInjectAssistInfo( piAssist );

            /**
             * 以下ALTER表将导致前面的更新完整提前提交,故放在最后
             */
            if( ( !haveSameFiled || !modelFiledDataType.equals( metadata.getPerdureType() ) )
                && editMode )// 若字段标志改变
            {
                if( Constant.METADATA.MYSQL_TEXT.equals( modelFiledDataType )
                    || Constant.METADATA.MYSQL_LONGTEXT.equals( modelFiledDataType ) )
                {
                    // 若当前的字段类型也为大文本类型,则不需要drop
                    if( Constant.METADATA.MYSQL_TEXT.equals( metadata.getPerdureType() )
                        || Constant.METADATA.MYSQL_LONGTEXT.equals( metadata.getPerdureType() ) )
                    {
                        metaDataDao.createOrModifyModelRelatingMysqlColumn( metadata,
                            targetModelTableName, true );
                    }
                }
                else
                {
                    // 先drop
                    metaDataDao.dropMysqlFiledbyId( modelTableName, modelFiledName );
                    // 重新创建
                    metaDataDao.createOrModifyModelRelatingMysqlColumn( metadata,
                        targetModelTableName, false );
                }
            }

            if( editMode )
            {
                // 在编辑模式下,要尝试drop掉存在的时间排序辅助字段
                try
                {
                    metaDataDao.dropMysqlFiledbyId( modelTableName,
                        Constant.METADATA.PREFIX_DT_ORDER_COLUMN_NAME + modelFiledSign );
                }
                catch ( Exception e )
                {

                }

                // 可变字符类型长度改变
                if( Constant.METADATA.MYSQL_VARCHAR.equals( modelFiledDataType )
                    && config.getMaxLength().intValue() != modelFiledCharLength )
                {
                    metaDataDao.createOrModifyModelRelatingMysqlColumn( metadata,
                        targetModelTableName, true );
                }
            }
            else
            {
                // 直接创建
                metaDataDao.createOrModifyModelRelatingMysqlColumn( metadata, targetModelTableName,
                    false );

            }

            // 时间类型若是支持排序类型,需要增加排序字段
            if( Constant.METADATA.MYSQL_DATETIME.equals( metadata.getPerdureType() )
                && Constant.COMMON.ON.equals( metadata.getOrderFlag() ) )
            {
                metaDataDao.createModelRelatingMysqlColumnForDateOrderHelper( targetModelTableName,
                    Constant.METADATA.PREFIX_DT_ORDER_COLUMN_NAME + metadata.getFiledSign() );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            resetCache();
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
        }

        return new ServiceState( ServiceState.SUCCESS, "创建字段成功!" );

    }

    public void updateMetadataTableIndex( Long modelId )
    {
        DataModelBean model = metaDataDao.querySingleDataModelBeanById( modelId );

        if( model == null )
        {
            log.info( "[updateMetadataTableIndex] 模型主数据无法获取,更新索引动作停止,modelId:" + modelId );
        }

        // 改动查询排序字段相关的索引
        List indexInfoList = metaDataDao.queryIndexMetadataForTable( model.getRelateTableName() );

        Map indexInfo = null;
        String indexName = null;

        for ( int i = 0; i < indexInfoList.size(); i++ )
        {
            indexInfo = ( Map ) indexInfoList.get( i );
            indexName = ( String ) indexInfo.get( "Key_name" );

            if( "PRIMARY".equalsIgnoreCase( indexName ) )
            {
                continue;
            }

            metaDataDao.deleteIndexForTable( model.getRelateTableName(), indexName );

        }

        // 重新建立索引
        List modelFiledInfoBeanList = metaDataDao.queryUserDefinedModelFiledInfoBeanList( modelId );

        ModelFiledInfoBean modelFieldBean = null;

        List orderFlagFieldList = new ArrayList();
        List queryFlagFieldList = new ArrayList();
        Set dateTypeOrderFieldSignSet = new HashSet();

        for ( int i = 0; i < modelFiledInfoBeanList.size(); i++ )
        {
            modelFieldBean = ( ModelFiledInfoBean ) modelFiledInfoBeanList.get( i );

            // 排序字段
            if( Constant.COMMON.ON.equals( modelFieldBean.getOrderFlag() ) )
            {
                orderFlagFieldList.add( modelFieldBean.getFieldSign() );

                // 记录时间类型排序
                if( Constant.METADATA.MYSQL_DATETIME.equals( modelFieldBean.getPerdureType() )
                    && Constant.COMMON.ON.equals( modelFieldBean.getOrderFlag() ) )
                {
                    dateTypeOrderFieldSignSet.add( modelFieldBean.getFieldSign() );
                }
            }

            // 查询字段
            if( Constant.COMMON.ON.equals( modelFieldBean.getQueryFlag() ) )
            {
                queryFlagFieldList.add( modelFieldBean.getFieldSign() );
            }
        }

        StringBuffer buf = null;

        // 每个order一个新的索引
        String queryField = null;
        String orderField = null;
        for ( int i = 0; i < orderFlagFieldList.size(); i++ )
        {
            orderField = ( String ) orderFlagFieldList.get( i );
            buf = new StringBuffer();

            if( SystemSafeCharUtil.hasSQLDChars( orderField ) )
            {
                throw new FrameworkException( "包含非法字符,本次操作强制中止执行" );
            }
            // 注意order的主字段要放在前面
            buf.append( Constant.METADATA.PREFIX_COLUMN_NAME + orderField );

            for ( int j = 0; j < queryFlagFieldList.size(); j++ )
            {
                if( j == 0 )
                {
                    buf.append( "," );
                }

                queryField = ( String ) queryFlagFieldList.get( j );
                // 同时排序和查询的字段只需要排序字段
                if( queryField.equals( orderField ) )
                {
                    continue;
                }

                if( SystemSafeCharUtil.hasSQLDChars( queryField ) )
                {
                    throw new FrameworkException( "包含非法字符,本次操作强制中止执行" );
                }

                if( j + 1 != queryFlagFieldList.size() )
                {
                    buf.append( Constant.METADATA.PREFIX_COLUMN_NAME + queryField + "," );
                }
                else
                {
                    buf.append( Constant.METADATA.PREFIX_COLUMN_NAME + queryField );
                }
            }

            // 2015-2:去掉可能的最后一个逗号(bug)
            String colNames = buf.toString().trim();

            if( colNames.lastIndexOf( "," ) != -1 )
            {
                colNames = StringUtil.subString( colNames, 0, colNames.lastIndexOf( "," ) );
            }

            // 去buf最后的,
            String bufStr = buf.toString();

            if( bufStr.endsWith( "," ) )
            {
                bufStr = StringUtil.subString( bufStr, 0, bufStr.length() - 1 );
            }

            metaDataDao.createNewIndexForTable( model.getRelateTableName(), model.getModelSign()
                + orderField, bufStr );
        }

        // 默认排序查询,以contentId为排序标准
        queryField = null;
        buf = new StringBuffer();

        for ( int i = 0; i < queryFlagFieldList.size(); i++ )
        {
            queryField = ( String ) queryFlagFieldList.get( i );

            buf.append( Constant.METADATA.PREFIX_COLUMN_NAME + queryField + "," );
        }

        buf.append( "contentId" );

        // 2015:主键contentId不需要建立索引

        if( !Constant.METADATA.CONTENT_ID_NAME.equals( buf.toString() ) )
        {
            metaDataDao.createNewIndexForTable( model.getRelateTableName(), model.getModelSign(),
                buf.toString() );

            log.info( "[updateMetadataTableIndex] 更新模型对应表查询索引成功,modelId:" + modelId );
        }
    }

    /**
     * 获取指定模型的元数据以及当前实时值,按照行列排序。
     * 
     * @param currentInfo
     * @param modelId
     * @return ValueSnapshotBean
     */
    public Object[] retrieveModelFiledAndValueInfoBeanList( Map currentInfo, Long modelId )
    {
        List result = retrieveModelFiledInfoBeanList( modelId );

        // 转换成ValueSnapshot
        ModelFiledInfoBean mfBean = null;

        MetadataValueSnapshotBean bean = null;

        // 确定排列顺序
        Map rowMap = new TreeMap();
        List colList = null;
        Integer orderIdRow = null;

        List allFieldInfoBeanList = new ArrayList();

        for ( int i = 0; i < result.size(); i++ )
        {
            mfBean = ( ModelFiledInfoBean ) result.get( i );

            bean = new MetadataValueSnapshotBean( mfBean, currentInfo );

            orderIdRow = Integer.valueOf( bean.getOrderId().intValue() );

            colList = ( List ) rowMap.get( orderIdRow );

            // 所有的模型字段都会加入
            allFieldInfoBeanList.add( bean );

            if( Constant.METADATA.INNER_DATA == mfBean.getHtmlElementId().intValue() )
            {
                continue;
            }

            if( colList == null )
            {
                colList = new ArrayList();
                colList.add( bean );

                rowMap.put( orderIdRow, colList );
            }
            else
            {
                colList.add( bean );
            }
        }

        return new Object[] { new ArrayList( rowMap.values() ), allFieldInfoBeanList };
    }

    /**
     * 获取指定模型的元数据以及当前实时值。
     * 
     * @param currentInfo
     * @param modelId
     * @return List<ModelFiledInfoBean>
     */
    public List<ModelFiledInfoBean> retrieveModelFiledInfoBeanList( Long modelId )
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveModelFiledInfoBeanList" );

        List result = ( List ) cache.getEntry( modelId );

        if( result == null )
        {
            result = metaDataDao.queryUserDefinedModelFiledInfoBeanList( modelId );
            cache.putEntry( modelId, result );
        }

        return result;
    }

    /**
     * 获取指定模型的元数据以及当前实时值。
     * 
     * @param currentInfo
     * @param modelId
     * @return Map<String, ModelFiledInfoBean>
     */
    public Map<String, ModelFiledInfoBean> retrieveModelFiledInfoBeanMap( Long modelId )
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveModelFiledInfoBeanList" );

        Map<String, ModelFiledInfoBean> result = ( Map<String, ModelFiledInfoBean> ) cache
            .getEntry( "map:" + modelId );

        if( result == null )
        {

            result = new LinkedHashMap<String, ModelFiledInfoBean>();

            List<ModelFiledInfoBean> fl = metaDataDao
                .queryUserDefinedModelFiledInfoBeanList( modelId );

            for ( ModelFiledInfoBean f : fl )
            {
                result.put( f.getFieldSign(), f );
            }

            cache.putEntry( "map:" + modelId, result );
        }

        return result;
    }

    /**
     * 获取指定模型的元数据。
     * 
     * @param currentInfo
     * @param modelId
     * @return ValueSnapshotBean
     */
    public List retrieveSystemModelFiledInfoBeanList()
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveSystemModelFiledInfoBeanList" );

        List result = ( List ) cache.getEntry( "retrieveSystemModelFiledInfoBeanList" );

        if( result == null )
        {
            result = metaDataDao.querySystemModelFiledInfoBeanList();
            cache.putEntry( "retrieveSystemModelFiledInfoBeanList", result );
        }
        return result;
    }

    public List retrieveAllDataModelBeanListByModelTypeAndSiteId( Integer contentType, Long siteId )
    {
        return metaDataDao.queryAllDataModelBeanListByModelTypeAndSiteId( contentType, siteId );
    }

    public List retrieveAllDataModelBeanListByModelTypeAndModeAndSiteId( Integer contentType,
        String siteMode, Long siteId )
    {
        if( "false".equals( siteMode ) )
        {
            return metaDataDao.queryAllDataModelBeanListByModelTypeAndSiteId( contentType, siteId );
        }
        else
        {
            return metaDataDao.queryDataModelBeanListByModelTypeAndSiteId( contentType, siteId );
        }
    }

    /**
     * 删除模型所有信息,包括存在的实际数据.
     * 
     * @param modelId
     */
    public void deleteDataModelAllInfo( Long modelId, Long siteId )
    {
        try
        {
            mysqlEngine.beginTransaction();

            DataModelBean dataModelBean = metaDataDao.querySingleDataModelBeanById( modelId );

            if( dataModelBean != null )
            {
                if( !siteId.equals( dataModelBean.getSiteId() ) )
                {
                    // 进一步判断是否最高管理员
                    SecuritySession session = SecuritySessionKeeper.getSecuritySession();

                    Auth auth = session.getAuth();

                    boolean isBigBoss = false;

                    if( session != null && session.isManager() )
                    {
                        SystemOrganizationBean rootOrgBean = orgDao
                            .querySingleSystemOrganizationBeanByLinear( "001" );

                        if( auth != null
                            && rootOrgBean.getOrgBossId().equals( ( Long ) auth.getIdentity() ) )
                        {
                            isBigBoss = true;
                        }
                    }

                    if( !isBigBoss )
                    {
                        return;
                    }

                }
            }

            // 默认系统系统模型不可删除
            // 2013注：现在已无系统模型
            /*
             * long dmId = dataModelBean.getDataModelId().longValue(); if( dmId <
             * 5 ) { return; }
             */

            // 删除所有字段元数据相关的html配置参数
            metaDataDao.deleteAllMetadataHtmlConfigByModelId( dataModelBean.getDataModelId() );

            // 删除所有字段元数据
            metaDataDao.deleteAllMetadataFiledByModelId( dataModelBean.getDataModelId() );

            // 删除持久层sql数据
            metaDataDao.deleteModelPersistenceCodeByModelId( dataModelBean.getDataModelId() );

            // 删除model主数据
            metaDataDao.deleteModelByModelId( dataModelBean.getDataModelId() );

            // 删除栏目扩展资源信息
            metaDataDao.deleteClassExtModelIdByModelId( modelId );

            // 删除资源辅助信息
            metaDataDao.deletePathInjectAssistByModelId( modelId );

            // 删除扩展信息表
            String targetTableName = dataModelBean.getRelateTableName();

            if( targetTableName.indexOf( Constant.METADATA.PREFIX_TABLE_NAME ) == 0 )
            {
                // 重要:确保删除的是扩展表,而不是其他系统表
                metaDataDao.deleteExtendTable( dataModelBean.getRelateTableName() );
            }
            else
            {
                log.error( "系统不接受删除非扩展表,当前目标表名:" + targetTableName );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            resetCache();
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            ChannelService.clearContentClassCache();
        }
    }

    public boolean deleteDataModelFiledMetadataAndHtmlConfig( Long filedMetedataId, Long modelId )
    {
        if( filedMetedataId == null || filedMetedataId.longValue() < 0 || modelId == null )
        {
            return false;
        }

        DataModelBean dataModelBean = metaDataDao.querySingleDataModelBeanById( modelId );

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( dataModelBean != null )
        {
            if( !siteBean.getSiteId().equals( dataModelBean.getSiteId() ) )
            {
                // 进一步判断是否最高管理员
                SecuritySession session = SecuritySessionKeeper.getSecuritySession();

                Auth auth = session.getAuth();

                boolean isBigBoss = false;

                if( session != null && session.isManager() )
                {
                    SystemOrganizationBean rootOrgBean = orgDao
                        .querySingleSystemOrganizationBeanByLinear( "001" );

                    if( auth != null
                        && rootOrgBean.getOrgBossId().equals( ( Long ) auth.getIdentity() ) )
                    {
                        isBigBoss = true;
                    }
                }

                if( !isBigBoss )
                {
                    return false;
                }

            }
        }

        try
        {
            mysqlEngine.beginTransaction();

            String modelTableName = metaDataDao.querySingleModelTableName( filedMetedataId );
            String modelFiledName = metaDataDao.querySingleFiledName( filedMetedataId );
            String modelFiledSign = metaDataDao.querySingleFiledSign( filedMetedataId );
            Integer modelFiledDataType = metaDataDao.querySingleFiledDataType( filedMetedataId );
            Integer modelFiledOrderFlag = metaDataDao.querySingleFiledOrderFlag( filedMetedataId );

            if( StringUtil.isStringNull( modelTableName )
                || StringUtil.isStringNull( modelFiledName ) )
            {
                return false;
            }

            // 文章资源处理
            DataModelBean model = metaDataDao.querySingleDataModelBeanById( modelId );

            if( modelFiledSign.equals( model.getMainEditorFieldSign() ) )
            {
                metaDataDao.updateModelMainEditorFieldSign( modelId, null );
                metaDataDao.updateModelResType( modelId, Constant.METADATA.MODEL_RES_NO );
            }

            metaDataDao.deleteHtmlConfigByItsFiledId( filedMetedataId );
            metaDataDao.deleteDataModelFiledMetadata( filedMetedataId );
            metaDataDao.deletePathInjectAssistByFieldName( modelId, modelFiledSign );

            // 附加数据表字段处理

            String targetModelTableName = metaDataDao.queryTargetModelTableNameByModelId( modelId );

            if( StringUtil.isStringNull( targetModelTableName ) )
            {
                log.info( "目标数据表名为空,metadata:" + filedMetedataId );
                throw new FrameworkException( "模型附加表丢失!" );
                // return new ServiceState( ServiceState.ERROR, "模型附加表丢失!" );
            }

            /**
             * 生成对应SQL
             */
            List targetFiledList = metaDataDao.queryUserDefinedModelFiledInfoBeanList( modelId );

            List filedNameList = new ArrayList();
            // 首先加入ID filed

            // String idName = targetModelTableName.substring(
            // targetModelTableName.lastIndexOf( "_" ) + 1,
            // targetModelTableName.length() )
            // + "Id";

            String idName = Constant.METADATA.CONTENT_ID_NAME;

            Set innerColumnSet = new HashSet();
            ModelFiledInfoBean mfInfoBean;
            for ( int i = 0; i < targetFiledList.size(); i++ )
            {
                mfInfoBean = ( ModelFiledInfoBean ) targetFiledList.get( i );
                filedNameList.add( mfInfoBean.getRelateFiledName() );

                // 内部业务字段登记
                if( Constant.METADATA.INNER_DATA == mfInfoBean.getHtmlElementId().intValue() )
                {
                    innerColumnSet.add( mfInfoBean.getRelateFiledName() );
                }

                // 时间排序字段要新加辅助字段
                if( Constant.METADATA.MYSQL_DATETIME.equals( mfInfoBean.getPerdureType() )
                    && Constant.COMMON.ON.equals( mfInfoBean.getOrderFlag() ) )
                {
                    filedNameList.add( Constant.METADATA.PREFIX_DT_ORDER_COLUMN_NAME
                        + mfInfoBean.getFieldSign() );
                }
            }

            // 在所有字段后加入ID
            filedNameList.add( idName );

            EntitySqlBridge sqlBridge = new EntitySqlBridge( targetModelTableName, idName,
                Constant.METADATA.PREFIX_QUERY_CACHE_NAME, Constant.METADATA.PREFIX_COLUMN_NAME,
                filedNameList, innerColumnSet, EntitySqlBridge.ASSIGNED );

            if( metaDataDao.queryHaveMetedataEntitySql( modelId ).intValue() == 0 )
            {
                metaDataDao.saveMetedataEntitySql( sqlBridge, modelId );
            }
            else
            {
                metaDataDao.updateMetedataEntitySql( sqlBridge, modelId );
            }

            metaDataDao.dropMysqlFiledbyId( modelTableName, modelFiledName );

            if( Constant.METADATA.MYSQL_DATETIME.equals( modelFiledDataType )
                && Constant.COMMON.ON.equals( modelFiledOrderFlag ) )
            {
                // 先drop
                metaDataDao.dropMysqlFiledbyId( modelTableName,
                    Constant.METADATA.PREFIX_DT_ORDER_COLUMN_NAME + modelFiledSign );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            resetCache();
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
        }

        return true;
    }

    // TODO 目前只是mysql版本
    private void prefixDisposeModelFiledMetadata( ModelHtmlConfig config,
        ModelFiledMetadata metadata )
    {
        boolean noTypeMode = false;
        // 决定数据库字段类型需要根据用户输入的信息
        switch ( config.getHtmlElementId().intValue() )
        {
            case Constant.METADATA.TEXT:
                // config.textMode();

                break;

            case Constant.METADATA.TEXTAREA:
                // config.textAreaMode();

                break;

            case Constant.METADATA.EDITER:
                config.setDataType( Constant.METADATA.LONGTEXT_TYPE );
                break;

            case Constant.METADATA.SELECT:
                if( Constant.COMMON.ON.equals( config.getLinkType() ) )
                {
                    config.setDataType( Constant.METADATA.LONG_TYPE );
                }
                break;

            case Constant.METADATA.RADIO:
                if( Constant.COMMON.ON.equals( config.getLinkType() ) )
                {
                    config.setDataType( Constant.METADATA.LONG_TYPE );
                }
                break;

            case Constant.METADATA.CHECK_BOX:
                if( Constant.COMMON.ON.equals( config.getLinkType() ) )
                {
                    config.setDataType( Constant.METADATA.LONG_TYPE );
                }
                break;

            case Constant.METADATA.DATE:
                // config.dateTimeMode();
                metadata.setPerdureType( Constant.METADATA.MYSQL_DATE );
                config.setDataType( Constant.METADATA.DATE_TYPE );
                noTypeMode = true;
                break;

            case Constant.METADATA.TIME:
                // config.dateTimeMode();
                metadata.setPerdureType( Constant.METADATA.MYSQL_TIME );
                config.setDataType( Constant.METADATA.TIME_TYPE );
                noTypeMode = true;
                break;

            case Constant.METADATA.DATETIME:
                // config.dateTimeMode();
                metadata.setPerdureType( Constant.METADATA.MYSQL_DATETIME );
                config.setDataType( Constant.METADATA.DATETIME_TYPE );
                noTypeMode = true;
                break;

            case Constant.METADATA.UPLOAD_FILE:
                // config.fileUploadMode();
                metadata.setPerdureType( Constant.METADATA.MYSQL_VARCHAR );
                config.setDataType( Constant.METADATA.TEXT_TYPE );
                metadata.setCapacity( Long.valueOf( 200 ) );
                config.setMaxLength( Integer.valueOf( 200 ) );
                noTypeMode = true;
                break;

            case Constant.METADATA.UPLOAD_IMG:
                // config.fileUploadMode();
                metadata.setPerdureType( Constant.METADATA.MYSQL_VARCHAR );
                config.setDataType( Constant.METADATA.TEXT_TYPE );
                metadata.setCapacity( Long.valueOf( 200 ) );
                config.setMaxLength( Integer.valueOf( 200 ) );
                noTypeMode = true;
                break;

            case Constant.METADATA.UPLOAD_IMG_GROUP:
                metadata.setPerdureType( Constant.METADATA.MYSQL_VARCHAR );
                config.setDataType( Constant.METADATA.TEXT_TYPE );
                metadata.setCapacity( Long.valueOf( 200 ) );
                config.setMaxLength( Integer.valueOf( 200 ) );
                noTypeMode = true;
                break;

            case Constant.METADATA.UPLOAD_MEDIA:
                // config.fileUploadMode();
                metadata.setPerdureType( Constant.METADATA.MYSQL_VARCHAR );
                config.setDataType( Constant.METADATA.TEXT_TYPE );
                metadata.setCapacity( Long.valueOf( 200 ) );
                config.setMaxLength( Integer.valueOf( 200 ) );
                noTypeMode = true;
                break;

            case Constant.METADATA.CUSTOM_HTML_CONTENT:
                // config.htmlContentMode();
                metadata.setPerdureType( Constant.METADATA.MYSQL_LONGTEXT );
                noTypeMode = true;
                break;

            default:
        }

        if( !noTypeMode )
        {
            Integer dataType = config.getDataType();

            if( Constant.METADATA.TEXT_TYPE.equals( dataType ) )
            {
                metadata.setPerdureType( Constant.METADATA.MYSQL_VARCHAR );

                // 若超过3W文字强制为3W
                Long length = Long.valueOf( config.getMaxLength().longValue() );
                if( length.longValue() > 30000 )
                {
                    length = Long.valueOf( 30010 );
                }

                metadata.setCapacity( length );
                config.setMaxLength( Integer.valueOf( length.intValue() ) );

            }
            else if( Constant.METADATA.LONGTEXT_TYPE.equals( dataType ) )
            {
                if( config.getMaxLength().intValue() >= 60000 )
                {
                    metadata.setPerdureType( Constant.METADATA.MYSQL_LONGTEXT );
                }
                else
                {
                    metadata.setPerdureType( Constant.METADATA.MYSQL_TEXT );
                }
            }
            else if( Constant.METADATA.INT_TYPE.equals( dataType ) )
            {
                metadata.setCapacity( Long.valueOf( config.getMaxLength().longValue() ) );
                metadata.setPerdureType( Constant.METADATA.MYSQL_INT );
            }
            else if( Constant.METADATA.LONG_TYPE.equals( dataType ) )
            {
                metadata.setCapacity( Long.valueOf( config.getMaxLength().longValue() ) );
                metadata.setPerdureType( Constant.METADATA.MYSQL_BIGINT );
            }
            else if( Constant.METADATA.DOUBLE_TYPE.equals( dataType ) )
            {
                metadata.setCapacity( Long.valueOf( config.getMaxLength().longValue() ) );
                metadata.setPerdureType( Constant.METADATA.MYSQL_DOUBLE );
            }
            else if( Constant.METADATA.DATETIME_TYPE.equals( dataType ) )
            {
                metadata.setPerdureType( Constant.METADATA.MYSQL_DATETIME );
                config.setDataType( Constant.METADATA.DATETIME_TYPE );
            }
            else if( Constant.METADATA.DATE_TYPE.equals( dataType ) )
            {
                metadata.setPerdureType( Constant.METADATA.MYSQL_DATE );
                config.setDataType( Constant.METADATA.DATE_TYPE );
            }
            else if( Constant.METADATA.TIME_TYPE.equals( dataType ) )
            {
                metadata.setPerdureType( Constant.METADATA.MYSQL_TIME );
                config.setDataType( Constant.METADATA.TIME_TYPE );
            }

        }
    }

    /**
     * 获取系统当前所有模型对应的Html元素
     * 
     * @return
     */
    public Map retrieveSystemHtmlElementInfo()
    {

        Cache cache = ( Cache ) cacheManager.get( "retrieveSystemHtmlElementInfo" );

        Map result = ( Map ) cache.getEntry( "retrieveSystemHtmlElementInfo" );

        if( result == null )
        {
            result = metaDataDao.querySystemHtmlElementInfo();
            cache.putEntry( "retrieveSystemHtmlElementInfo", result );
        }

        return result;

    }

    public DataModelBean retrieveSingleDataModelBeanById( Long modelId )
    {
        return metaDataDao.querySingleDataModelBeanById( modelId );
    }

    public MetadataValueSnapshotBean retrieveSingleDataModelFieldBeanById( Long id, Map info )
    {
        ModelFiledInfoBean fieldBean = metaDataDao.querySingleDataModelFieldBeanById( id );

        return new MetadataValueSnapshotBean( fieldBean, info );
    }

    public ModelFiledInfoBean retrieveSingleDataModelFieldBeanBySign( String fieldSign )
    {
        ModelFiledInfoBean fieldBean = metaDataDao.querySingleDataModelFieldBeanBySign( fieldSign );

        return fieldBean;
    }

    public DataModelBean retrieveSingleDataModelBeanByName( String modelName )
    {
        return metaDataDao.querySingleDataModelBeanByName( modelName );
    }

    public void updateModelFileldOrderAndBlankCount( Long modelId, Map orderInfoMap,
        Map blankInfoMap )
    {
        try
        {
            mysqlEngine.beginTransaction();

            DataModelBean model = metaDataDao.querySingleDataModelBeanById( modelId );

            // 排列
            Iterator iter = orderInfoMap.entrySet().iterator();

            Entry entry = null;

            Long id = null;
            Double order = null;

            mysqlEngine.startBatch();

            while ( iter.hasNext() )
            {
                entry = ( Entry ) iter.next();

                id = ( Long ) entry.getKey();
                order = ( Double ) entry.getValue();

                metaDataDao.updateMetedataOrder( id, order );
            }

            // 空白间隔数
            iter = blankInfoMap.entrySet().iterator();

            Integer count = null;

            while ( iter.hasNext() )
            {
                entry = ( Entry ) iter.next();

                id = ( Long ) entry.getKey();

                count = ( Integer ) entry.getValue();

                metaDataDao.updateMetedataBlankCount( id, count );
            }

            mysqlEngine.executeBatch();

            /**
             * 生成对应SQL
             */
            List targetFiledList = metaDataDao.queryUserDefinedModelFiledInfoBeanList( modelId );

            List filedNameList = new ArrayList();

            String idName = Constant.METADATA.CONTENT_ID_NAME;

            Set innerColumnSet = new HashSet();
            ModelFiledInfoBean mfInfoBean;
            for ( int i = 0; i < targetFiledList.size(); i++ )
            {
                mfInfoBean = ( ModelFiledInfoBean ) targetFiledList.get( i );
                filedNameList.add( mfInfoBean.getRelateFiledName() );

                // 内部业务字段登记
                if( Constant.METADATA.INNER_DATA == mfInfoBean.getHtmlElementId().intValue() )
                {
                    innerColumnSet.add( mfInfoBean.getRelateFiledName() );
                }

                // 时间排序字段要新加辅助字段
                if( Constant.METADATA.MYSQL_DATETIME.equals( mfInfoBean.getPerdureType() )
                    && Constant.COMMON.ON.equals( mfInfoBean.getOrderFlag() ) )
                {
                    filedNameList.add( Constant.METADATA.PREFIX_DT_ORDER_COLUMN_NAME
                        + mfInfoBean.getFieldSign() );
                }
            }

            // 最后加入ID filed
            filedNameList.add( idName );

            EntitySqlBridge sqlBridge = new EntitySqlBridge( model.getRelateTableName(), idName,
                Constant.METADATA.PREFIX_QUERY_CACHE_NAME, Constant.METADATA.PREFIX_COLUMN_NAME,
                filedNameList, innerColumnSet, EntitySqlBridge.ASSIGNED );

            metaDataDao.updateMetedataEntitySql( sqlBridge, modelId );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
            resetCache();
            ListContentClassInfoTreeController.resizeSiteContentClassCache();
            ListCommendTypeInfoTreeController.resizeSiteCommendTypeCache();
            ChannelDao.clearAllCache();
            ChannelService.clearContentClassCache();
        }

    }

    public ModelPersistenceMySqlCodeBean retrieveSingleModelPerMysqlCodeBean( Long modelId )
    {

        if( modelId == null || modelId.longValue() == -1 )
        {
            return null;
        }

        Cache cache = ( Cache ) cacheManager.get( "retrieveSingleModelPerMysqlCodeBean" );

        ModelPersistenceMySqlCodeBean bean = ( ModelPersistenceMySqlCodeBean ) cache
            .getEntry( "retrieveSingleModelPerMysqlCodeBean:" + modelId );

        if( bean == null )
        {
            bean = metaDataDao.querySingleModelPerMysqlCodeBean( modelId );
            cache.putEntry( "retrieveSingleModelPerMysqlCodeBean:" + modelId, bean );
        }

        return bean;
    }

    public List retrieveAllModelValidateConfigBean()
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveAllModelValidateConfigBean" );

        String key = "retrieveAllModelValidateConfigBean";

        List result = ( List ) cache.getEntry( key );

        if( result == null )
        {
            result = metaDataDao.queryAllModelValidateConfigBean();
            cache.putEntry( key, result );
        }

        return result;

    }

    public ModelValidateConfigBean retrieveSingleModelValidateConfigBean( Long configId,
        Long fieldId )
    {

        Cache cache = ( Cache ) cacheManager.get( "retrieveSingleModelValidateConfigBean" );

        String key = "retrieveSingleModelValidateConfigBean:" + configId + "|" + fieldId;

        ModelValidateConfigBean result = ( ModelValidateConfigBean ) cache.getEntry( key );

        if( result == null )
        {
            if( configId != null && configId.longValue() > 0 )
            {
                result = metaDataDao.querySingleModelValidateConfigBean( configId );
            }
            else
            {
                ModelFiledInfoBean mfBean = metaDataDao.querySingleDataModelFieldBeanById( fieldId );

                if( mfBean != null && StringUtil.isStringNotNull( mfBean.getCheckRegex() ) )
                {
                    ModelValidateConfigBean bean = new ModelValidateConfigBean();

                    bean.setErrorMessage( mfBean.getErrorMessage() );
                    bean.setRegulation( mfBean.getCheckRegex() );

                    bean.setValidateName( mfBean.getShowName() + ":自定义" );

                    result = bean;
                }
            }

            if( result == null )
            {
                result = new ModelValidateConfigBean();
            }

            cache.putEntry( key, result );
        }

        return result;

    }

    public List retrieveContentPathInjectAssistInfo( Long modelId )
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveContentPathInjectAssistInfo" );

        String key = "retrieveContentPathInjectAssistInfo:" + modelId;

        List assistInfoList = ( List ) cache.getEntry( key );

        if( assistInfoList == null )
        {
            assistInfoList = metaDataDao.queryContentPathInjectAssistInfo( modelId );

            cache.putEntry( key, assistInfoList );
        }

        // Map assistInfoMap = new HashMap( assistInfoList.size() );
        // PathInjectAssistBean assistBean = null; for ( int i = 0; i <
        // assistInfoList.size(); i++ ) { assistBean = ( PathInjectAssistBean )
        // assistInfoList.get( i ); assistInfoMap.put(
        // assistBean.getFieldName(), assistBean .getResType() ); }

        return assistInfoList;
    }

    public Long retrieveSystemTableByQueryFlagAndPageInfoCount( String countQuery, Object[] var,
        boolean formMode )
    {
        // 判断sql非法字符
        // if( StringUtil.hasSQLDChars( countQuery ) )
        // {
        // throw new FrameworkException( "包含非法字符,本次操作强制中止执行" );
        // }

        StringBuffer key = new StringBuffer( "retrieveSystemTableByQueryFlagAndPageInfoCount:"
            + countQuery + "|" + formMode + "|" );

        if( var != null )
        {
            for ( int i = 0; i < var.length; i++ )
            {
                key.append( var[i] + "|" );
            }
        }

        Cache cache = null;

        if( formMode )
        {
            cache = listCountCache;
        }
        else
        {
            cache = ContentDao.countCache;
        }

        Long count = ( Long ) cache.getEntry( key.toString() );

        if( count == null )
        {
            count = metaDataDao.querySystemTableCount( countQuery, var );

            cache.putEntry( key.toString(), count );
        }

        return count;
    }

    public List retrieveSystemTableByQueryFlagAndPageInfo( String query, Object[] var )
    {
        // TODO 判断sql,不允许出现系统关键表名称
        return metaDataDao.querySystemTableByQueryFlag( query, var );
    }

    public List retrieveMutiQueryContentByQueryFlagAndPageInfo( String query, Object[] var,
        boolean formMode, Long siteId, Long modelId )
    {
        StringBuffer key = new StringBuffer( "retrieveMutiQueryContentByQueryFlagAndPageInfo:"
            + query + "|" + formMode + "|" + siteId + "|" + modelId + "|" );

        if( var != null )
        {
            for ( int i = 0; i < var.length; i++ )
            {
                key.append( var[i] + "|" );
            }
        }

        Cache cache = null;

        if( formMode )
        {
            cache = listFormDataCache;
        }
        else
        {
            cache = ContentService.listContentCache;
        }

        List result = ( List ) cache.getEntry( key.toString() );

        if( result == null )
        {
            result = metaDataDao.queryMutiQueryContentByQueryFlagAndPageInfo( query, var, siteId,
                modelId );

            cache.putEntry( key.toString(), result );
        }

        return result;
    }

    public List retrieveFieldValueListByMetadata( Long dataModelId, Long fieldMetadataId )
    {
        DataModelBean modelBean = metaDataDao.querySingleDataModelBeanById( dataModelId );

        String fieldName = metaDataDao.querySingleFiledNameForQueryData( fieldMetadataId );

        return metaDataDao.queryModelFieldValue( modelBean, fieldName );
    }

    /**
     * 返回查询排序条件对应的sql
     * 
     * @param modelId
     * @param queryFlagStr 查询排序条件,如:0=<a1(对应整数字段)<=9999,1971-1-1=<b1(对应日期字段)<=2018-18-18
     * @return
     */
    public String retrieveModelMutiQuery( String[] cIdType, SiteGroupBean site, Long modelId,
        String queryFlagStr, String orderFlagStr, String orderWayFlag, List sqlParamList,
        boolean countMode, Map modelIdMap, String siteIdStr )
    {

        DataModelBean modelBean = null;

        String tableName = "";

        String idName = "";

        String cidName = "";

        boolean defFormMode = false;

        boolean singleDTMode = false;

        String mode = "";

        Long cId = Long.valueOf( -1 );

        if( cIdType != null && cIdType.length == 2 )
        {
            mode = cIdType[0];

            if( "-9999".equals( cIdType[1] ) )
            {
                singleDTMode = true;

                mode = StringUtil.replaceString( mode, ":", "" );
            }
            else
            {
                cId = StringUtil.getLongValue( cIdType[1], -1 );
            }
        }

        String mainQuery = "";

        if( "content".equals( mode ) )
        {
            mainQuery = " and cmx.censorState=1 ";

            tableName = "content_main_info";

            idName = "contentId";

            cidName = "classId";

            queryFlagStr = StringUtil
                .replaceString( queryFlagStr, "{contentId}", "{cmx.contentId}" );

            if( singleDTMode )
            {
                modelBean = retrieveSingleDataModelBeanById( modelId );

                if( "-1".equals( siteIdStr ) )
                {
                    mainQuery = " 1=1 ";
                }
                else
                {
                    mainQuery = " cmx.siteId=" + siteIdStr;
                }

                cidName = "";
            }
            else
            {
                ContentClassBean classBean = ChannelService.getInstance()
                    .retrieveSingleClassBeanInfoByClassId( cId );

                if( classBean.getClassId().longValue() > 0 )
                {
                    modelBean = retrieveSingleDataModelBeanById( classBean.getContentType() );
                }
            }
        }
        else if( "commend".equals( mode ) )
        {
            tableName = "content_commend_push_info";

            idName = "infoId";

            cidName = "commendTypeId";

            if( singleDTMode )
            {
                modelBean = retrieveSingleDataModelBeanById( modelId );

                if( "-1".equals( siteIdStr ) )
                {
                    mainQuery = " 1=1 ";
                }
                else
                {
                    mainQuery = " cmx.siteFlag="
                        + ( ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                            .getEntry( StringUtil.getLongValue( siteIdStr, -1 ) ) ).getSiteFlag();
                }
            }
            else
            {
                ContentCommendTypeBean typeBean = ChannelService.getInstance()
                    .retrieveSingleContentCommendTypeBeanByTypeId( cId );

                if( typeBean != null )
                {
                    modelBean = retrieveSingleDataModelBeanById( typeBean.getModelId() );
                }
            }
        }
        else if( "gb".equals( mode ) )
        {
            tableName = "guestbook_main_info";

            idName = "gbId";

            cidName = "configId";

            if( singleDTMode )
            {
                modelBean = retrieveSingleDataModelBeanById( modelId );

                if( "-1".equals( siteIdStr ) )
                {
                    mainQuery = " 1=1 ";
                }
                else
                {
                    mainQuery = " cmx.siteId=" + siteIdStr;
                }
            }
            else
            {
                GuestbookConfigBean cfgBean = GuestbookService.getInstance()
                    .retrieveSingleGuestbookConfigBeanByConfigId( cId );

                if( cfgBean != null )
                {
                    modelBean = retrieveSingleDataModelBeanById( cfgBean.getInfoModelId() );
                }
            }
        }
        else if( "member".equals( mode ) )
        {
            if( site == null )
            {
                return "";
            }

            if( "-1".equals( siteIdStr ) )
            {
                mainQuery = " 1=1 ";
            }
            else
            {
                mainQuery = "  cmx.siteId=" + site.getSiteId();
            }

            singleDTMode = true;

            tableName = "member";

            idName = "memberId";

            modelBean = retrieveSingleDataModelBeanById( site.getExtMemberModelId() );

        }
        else if( "form".equals( mode ) )
        {
            defFormMode = true;

            modelBean = retrieveSingleDataModelBeanById( modelId );

            if( site == null )
            {
                return "";
            }
        }

        if( modelBean == null )
        {
            return "";
        }

        modelIdMap.put( "modelId", modelBean.getDataModelId() );

        ModelPersistenceMySqlCodeBean peSqlBean = retrieveSingleModelPerMysqlCodeBean( modelBean
            .getDataModelId() );

        if( peSqlBean == null || StringUtil.isStringNull( peSqlBean.getSelectColumn() ) )
        {
            return null;
        }

        // 所有字段
        List<ModelFiledInfoBean> tmpModelFiledInfoBeanList = new ArrayList<ModelFiledInfoBean>();

        tmpModelFiledInfoBeanList.addAll( retrieveModelFiledInfoBeanList( modelBean
            .getDataModelId() ) );

        Map<String, ModelFiledInfoBean> tmpModelFiledInfoBeanMap = retrieveModelFiledInfoBeanMap( modelBean
            .getDataModelId() );

        String[] queryOrderArray = StringUtil.split( queryFlagStr, "," );
        Map queryBeanMap = new HashMap();
        List<ModelFiledInfoBean> mainQueryBeanList = new ArrayList<ModelFiledInfoBean>();
        String fieldName = null;
        List subList = null;

        String queryInfo = null;
        String testQuery = null;

        ModelFiledInfoBean sameBean = null;

        for ( int i = 0; i < queryOrderArray.length; i++ )
        {
            subList = StringUtil.subStringToList( queryOrderArray[i], "{", "}" );

            if( !subList.isEmpty() )
            {
                fieldName = ( String ) subList.get( 0 );

                queryInfo = queryOrderArray[i];

                testQuery = StringUtil.replaceString( queryInfo, "{" + fieldName + "}", "", false,
                    false );
                testQuery = StringUtil.replaceString( testQuery, "=[", "", false, false );
                testQuery = StringUtil.replaceString( testQuery, "[=", "", false, false );
                testQuery = StringUtil.replaceString( testQuery, "=]", "", false, false );
                testQuery = StringUtil.replaceString( testQuery, "]=", "", false, false );
                testQuery = StringUtil.replaceString( testQuery, "]", "", false, false );
                testQuery = StringUtil.replaceString( testQuery, "[", "", false, false );
                testQuery = StringUtil.replaceString( testQuery, "*=", "", false, false );
                testQuery = StringUtil.replaceString( testQuery, "!=", "", false, false );

                testQuery = StringUtil.replaceString( testQuery, "=", "", false, false );

                // 错误的查询条件查不会被使用
                if( StringUtil.isStringNotNull( testQuery ) )
                {

                    if( !queryBeanMap.containsKey( fieldName ) )
                    {
                        queryBeanMap.put( fieldName, queryOrderArray[i] );
                    }
                    else
                    {
                        // 同名参数
                        String nfn = fieldName + ":" + System.currentTimeMillis();

                        sameBean = tmpModelFiledInfoBeanMap.get( fieldName );

                        sameBean.setFieldSign( nfn );

                        tmpModelFiledInfoBeanList.add( sameBean );

                        queryBeanMap.put( nfn, queryOrderArray[i] );
                    }

                    if( !fieldName.startsWith( "jt_" ) )
                    {
                        ModelFiledInfoBean mfb = new ModelFiledInfoBean();

                        mfb.setFieldSign( fieldName );
                        mfb.setQueryFlag( Constant.COMMON.ON );
                        mfb.setOrderFlag( Constant.COMMON.OFF );

                        mainQueryBeanList.add( mfb );

                    }

                }
            }
        }

        // 追加主表查询
        tmpModelFiledInfoBeanList.addAll( mainQueryBeanList );

        StringBuffer querySqlBuf = new StringBuffer();

        String[] queryFlagArray = null;

        ModelFiledInfoBean bean = null;

        String sqlQuery = null;

        String sqlVal = null;

        String orderField = Constant.METADATA.CONTENT_ID_NAME;

        if( StringUtil.isStringNotNull( orderFlagStr ) && !orderFlagStr.startsWith( "jt_" ) )
        {
            // 若order指定了值,则默认认为是系统字段,若后面的扩展字段有匹配,则转换为扩展字段
            orderField = orderFlagStr;

        }

        int flag = 0;

        boolean haveQuery = false;

        boolean haveLeft = true;

        String tfn = null;

        for ( int i = 0; i < tmpModelFiledInfoBeanList.size(); i++ )
        {
            haveLeft = true;

            bean = ( ModelFiledInfoBean ) tmpModelFiledInfoBeanList.get( i );

            tfn = bean.getFieldSign();

            if( tfn.indexOf( ":" ) != -1 )
            {
                tfn = tfn.substring( 0, tfn.indexOf( ":" ) );
            }

            // 若当前字段是排序字段
            if( Constant.COMMON.ON.equals( bean.getOrderFlag() ) )
            {
                if( bean.getFieldSign().equals( orderFlagStr ) )
                {
                    orderField = Constant.METADATA.PREFIX_COLUMN_NAME + bean.getFieldSign();
                }

            }

            // 若当前字段是查询字段
            if( Constant.COMMON.ON.equals( bean.getQueryFlag() )
                && queryBeanMap.containsKey( bean.getFieldSign() ) )
            {
                if( flag > 0 )
                {
                    querySqlBuf.append( " and " );
                }

                String prefix = Constant.METADATA.PREFIX_COLUMN_NAME;

                if( !bean.getFieldSign().startsWith( "jt_" ) )
                {
                    prefix = "";
                }

                // 若当前字段存在在查询条件里
                if( queryBeanMap.containsKey( bean.getFieldSign() ) )
                {
                    haveQuery = true;

                    queryInfo = ( String ) queryBeanMap.get( bean.getFieldSign() );

                    if( queryInfo == null )
                    {
                        queryFlagArray = new String[] {};
                    }
                    else
                    {
                        queryFlagArray = StringUtil.split( ( String ) queryBeanMap.get( bean
                            .getFieldSign() ), "\\{" + tfn + "\\}" );
                    }

                    for ( int x = 0; x < queryFlagArray.length; x++ )
                    {
                        sqlQuery = queryFlagArray[x];

                        if( "".equals( sqlQuery ) )
                        {
                            haveLeft = false;
                            continue;
                        }

                        if( x > 0 && haveLeft )
                        {
                            querySqlBuf.append( " and " );
                        }

                        // 处理查询条件

                        if( sqlQuery.indexOf( "=[" ) != -1 || sqlQuery.indexOf( "[=" ) != -1 )
                        {
                            if( x == 0 )
                            {
                                querySqlBuf.append( prefix + tfn + ">=?" );
                            }
                            else
                            {
                                querySqlBuf.append( prefix + tfn + "<=?" );
                            }

                            sqlVal = StringUtil.replaceString( sqlQuery, "[=", "", false, false );
                            sqlVal = StringUtil.replaceString( sqlVal, "=[", "", false, false );
                        }
                        else if( sqlQuery.indexOf( "]=" ) != -1 || sqlQuery.indexOf( "=]" ) != -1 )
                        {
                            if( x == 0 )
                            {
                                querySqlBuf.append( prefix + tfn + "<=?" );
                            }
                            else
                            {
                                querySqlBuf.append( prefix + tfn + ">=?" );
                            }

                            sqlVal = StringUtil.replaceString( sqlQuery, "]=", "", false, false );
                            sqlVal = StringUtil.replaceString( sqlVal, "=]", "", false, false );
                        }
                        else if( sqlQuery.indexOf( "*=" ) != -1 )
                        {

                            querySqlBuf.append( prefix + tfn + " like ?" );

                            sqlVal = "%"
                                + StringUtil.replaceString( sqlQuery, "*=", "", false, false )
                                + "%";

                        }
                        else if( sqlQuery.indexOf( "!=" ) != -1 )
                        {

                            querySqlBuf.append( prefix + tfn + "!=?" );

                            sqlVal = StringUtil.replaceString( sqlQuery, "!=", "", false, false );

                        }
                        else if( sqlQuery.indexOf( "=" ) != -1 )
                        {
                            querySqlBuf.append( prefix + tfn + "=?" );

                            sqlVal = StringUtil.replaceString( sqlQuery, "=", "", false, false );
                        }

                        else if( sqlQuery.indexOf( "[" ) != -1 )
                        {
                            if( x == 0 )
                            {
                                querySqlBuf.append( prefix + tfn + ">?" );
                            }
                            else
                            {
                                querySqlBuf.append( prefix + tfn + "<?" );
                            }

                            sqlVal = StringUtil.replaceString( sqlQuery, "[", "", false, false );

                        }
                        else if( sqlQuery.indexOf( "]" ) != -1 )
                        {
                            if( x == 0 )
                            {
                                querySqlBuf.append( prefix + tfn + "<?" );
                            }
                            else
                            {
                                querySqlBuf.append( prefix + tfn + ">?" );
                            }

                            sqlVal = StringUtil.replaceString( sqlQuery, "]", "", false, false );
                        }

                        // 条件参数完毕
                        sqlParamList.add( changeSQLParamToObject( bean, sqlVal ) );
                    }

                }
                else
                {
                    // 不在查询条件中的情况,直接使用默认最小值筛选
                    if( Constant.METADATA.INT_TYPE.equals( bean.getDataType() ) )
                    {
                        querySqlBuf.append( prefix + tfn + ">" + Integer.MIN_VALUE );
                    }
                    else if( Constant.METADATA.LONG_TYPE.equals( bean.getDataType() ) )
                    {
                        querySqlBuf.append( prefix + tfn + ">" + Long.MIN_VALUE );
                    }
                    else if( Constant.METADATA.DOUBLE_TYPE.equals( bean.getDataType() ) )
                    {
                        querySqlBuf.append( prefix + tfn + ">-999999999999999999.999999" );
                    }
                    else if( Constant.METADATA.DATETIME_TYPE.equals( bean.getDataType() )
                        || Constant.METADATA.DATE_TYPE.equals( bean.getDataType() )
                        || Constant.METADATA.TIME_TYPE.equals( bean.getDataType() ) )
                    {
                        querySqlBuf.append( prefix + tfn + ">'1000-01-01 00:00:00'" );
                    }
                }

                flag++;
            }

        }

        String orderWay = Constant.CONTENT.DOWN_ORDER_WAY_VAR;

        if( Constant.CONTENT.UP_ORDER_WAY.equals( orderWayFlag ) )
        {
            orderWay = Constant.CONTENT.UP_ORDER_WAY_VAR;
        }

        // 无查询
        if( StringUtil.isStringNull( queryFlagStr ) || !haveQuery )
        {
            if( defFormMode )
            {
                if( countMode )
                {
                    return "select count(contentId) from " + modelBean.getRelateTableName()
                        + ", model_def_from_data_id where contentId=defFormDataId and siteId="
                        + site.getSiteId();
                }
                else
                {
                    return "select " + peSqlBean.getSelectColumn() + ", tmp.contentId from "
                        + modelBean.getRelateTableName() + " tmp, (select contentId from "
                        + modelBean.getRelateTableName()
                        + ", model_def_from_data_id where contentId=defFormDataId and siteId="
                        + site.getSiteId() + " order by " + orderField + " " + orderWay
                        + " limit ?,?) ids where tmp.contentId=ids.contentId order by "
                        + orderField + " " + orderWay;
                }
            }
            else if( singleDTMode )
            {
                if( countMode )
                {
                    return "select count(ci.contentId) from " + modelBean.getRelateTableName()
                        + " ci inner join " + tableName + " cmx on ci.contentId = cmx." + idName
                        + " where  " + mainQuery;
                }
                else
                {
                    return "select cm.*, " + peSqlBean.getSelectColumn() + " from " + tableName
                        + " cm, " + modelBean.getRelateTableName() + " tmp, (select cmx." + idName
                        + " from " + modelBean.getRelateTableName() + " ci inner join " + tableName
                        + " cmx on cmx." + idName + "=ci.contentId and " + mainQuery + " order by "
                        + orderField + " " + orderWay + " limit ?,?) ids where cm." + idName
                        + "=ids." + idName + " and tmp.contentId=ids." + idName + " order by "
                        + orderField + " " + orderWay;
                }
            }
            else
            {
                if( countMode )
                {
                    return "select count(ci.contentId) from " + modelBean.getRelateTableName()
                        + " ci inner join " + tableName + " cmx on ci.contentId = cmx." + idName
                        + " where cmx." + cidName + "=" + cId + " " + mainQuery;
                }
                else
                {
                    return "select cm.*, " + peSqlBean.getSelectColumn() + " from " + tableName
                        + " cm, " + modelBean.getRelateTableName() + " tmp, (select cmx." + idName
                        + " from " + modelBean.getRelateTableName() + " ci inner join " + tableName
                        + " cmx on cmx." + idName + "=ci.contentId and cmx." + cidName + "=" + cId
                        + " " + mainQuery + " order by " + orderField + " " + orderWay
                        + " limit ?,?) ids where cm." + idName + "=ids." + idName
                        + " and tmp.contentId=ids." + idName + " order by " + orderField + " "
                        + orderWay;
                }
            }
        }

        String query = querySqlBuf.toString();

        // 有查询
        if( StringUtil.isStringNull( query ) )
        {
            if( defFormMode )
            {
                if( countMode )
                {
                    return "select count(contentId) from " + modelBean.getRelateTableName()
                        + ", model_def_from_data_id where contentId=defFormDataId and siteId="
                        + site.getSiteId();
                }
                else
                {
                    return "select " + peSqlBean.getSelectColumn() + ", tmp.contentId from "
                        + modelBean.getRelateTableName() + " tmp, (select contentId from "
                        + modelBean.getRelateTableName()
                        + ", model_def_from_data_id where contentId=defFormDataId and siteId="
                        + site.getSiteId() + " order by " + orderField + " " + orderWay
                        + " limit ?,?) ids where tmp.contentId=ids.contentId order by "
                        + orderField + " " + orderWay;

                }
            }
            else if( singleDTMode )
            {
                if( countMode )
                {
                    return "select count(ci.contentId) from " + modelBean.getRelateTableName()
                        + " ci inner join " + tableName + " cmx on ci.contentId = cmx." + idName
                        + "  where  " + mainQuery;

                }
                else
                {
                    return "select cm.*, " + peSqlBean.getSelectColumn() + " from " + tableName
                        + " cm, " + modelBean.getRelateTableName() + " tmp, (select cmx." + idName
                        + " from " + modelBean.getRelateTableName() + " ci inner join " + tableName
                        + " cmx on cmx." + idName + "=ci.contentId and  " + mainQuery
                        + " order by " + orderField + " " + orderWay + " limit ?,?) ids where cm."
                        + idName + "=ids." + idName + " and tmp.contentId=ids." + idName
                        + " order by " + orderField + " " + orderWay;

                }
            }
            else
            {
                if( countMode )
                {
                    return "select count(ci.contentId) from " + modelBean.getRelateTableName()
                        + " ci inner join " + tableName + " cmx on ci.contentId = cmx." + idName
                        + "  where cmx." + cidName + "=" + cId + " " + mainQuery;

                }
                else
                {
                    return "select cm.*, " + peSqlBean.getSelectColumn() + " from " + tableName
                        + " cm, " + modelBean.getRelateTableName() + " tmp, (select cmx." + idName
                        + " from " + modelBean.getRelateTableName() + " ci inner join " + tableName
                        + " cmx on cmx." + idName + "=ci.contentId and cmx." + cidName + "=" + cId
                        + " " + mainQuery + " order by " + orderField + " " + orderWay
                        + " limit ?,?) ids where cm." + idName + "=ids." + idName
                        + " and tmp.contentId=ids." + idName + " order by " + orderField + " "
                        + orderWay;

                }
            }

        }
        else
        {
            if( defFormMode )
            {
                if( countMode )
                {
                    return "select count(contentId) from " + modelBean.getRelateTableName()
                        + ", model_def_from_data_id where " + querySqlBuf.toString()
                        + " and contentId=defFormDataId and siteId=" + site.getSiteId();
                }
                else
                {
                    return "select " + peSqlBean.getSelectColumn() + ", tmp.contentId from "
                        + modelBean.getRelateTableName() + " tmp, (select contentId from "
                        + modelBean.getRelateTableName() + ", model_def_from_data_id where "
                        + querySqlBuf.toString() + " and contentId=defFormDataId and siteId="
                        + site.getSiteId() + " order by " + orderField + " " + orderWay
                        + " limit ?,?) ids where tmp.contentId=ids.contentId order by "
                        + orderField + " " + orderWay;
                }
            }
            else if( singleDTMode )
            {

                if( countMode )
                {
                    return "select count(ci.contentId) from " + modelBean.getRelateTableName()
                        + " ci inner join " + tableName + " cmx on ci.contentId = cmx." + idName
                        + " where " + querySqlBuf.toString() + " and " + mainQuery;
                }
                else
                {
                    return "select cm.*, " + peSqlBean.getSelectColumn() + " from " + tableName
                        + " cm, " + modelBean.getRelateTableName() + " tmp, (select cmx." + idName
                        + " from " + modelBean.getRelateTableName() + " ci inner join " + tableName
                        + " cmx on cmx." + idName + "=ci.contentId " + " where "
                        + querySqlBuf.toString() + " and " + mainQuery + " order by " + orderField
                        + " " + orderWay + " limit ?,?) ids where cm." + idName + "=ids." + idName
                        + " and tmp.contentId=ids." + idName + " order by " + orderField + " "
                        + orderWay;
                }

            }
            else
            {
                if( countMode )
                {
                    return "select count(ci.contentId) from " + modelBean.getRelateTableName()
                        + " ci inner join " + tableName + " cmx on ci.contentId = cmx." + idName
                        + " and cmx." + cidName + "=" + cId + " where " + querySqlBuf.toString()
                        + " " + mainQuery;
                }
                else
                {
                    return "select cm.*, " + peSqlBean.getSelectColumn() + " from " + tableName
                        + " cm, " + modelBean.getRelateTableName() + " tmp, (select cmx." + idName
                        + " from " + modelBean.getRelateTableName() + " ci inner join " + tableName
                        + " cmx on cmx." + idName + "=ci.contentId and cmx." + cidName + "=" + cId
                        + " where " + querySqlBuf.toString() + " " + mainQuery + " order by "
                        + orderField + " " + orderWay + " limit ?,?) ids where cm." + idName
                        + "=ids." + idName + " and tmp.contentId=ids." + idName + " order by "
                        + orderField + " " + orderWay;
                }
            }
        }
    }

    public String[] retrieveAllSiteSearchFieldStrArrayMode()
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveAllSearchFieldStrArrayMode" );

        String key = "retrieveAllSearchFieldStrArrayMode";

        String[] result = ( String[] ) cache.getEntry( key );

        if( result == null )
        {
            List fieldSignList = metaDataDao.queryAllSearchFieldSign();

            fieldSignList.add( "title" );// title是默认且必须有的系统搜索字段
            fieldSignList.add( "keywords" );// keywords是默认且必须有的系统搜索字段

            result = ( String[] ) fieldSignList.toArray( new String[] {} );

            cache.putEntry( key, result );
        }

        return result;
    }

    /**
     * 增加或更新模型数据(通用方法)
     * 
     * @param params
     * @param modelId
     * @param infoId
     * @param modelType
     */
    public void addOrEditDefModelInfo( Map params, Long modelId, Long infoId, Integer modelType )
    {
        ModelPersistenceMySqlCodeBean sqlCodeBean = retrieveSingleModelPerMysqlCodeBean( modelId );

        DataModelBean model = retrieveSingleDataModelBeanById( modelId );

        List filedBeanList = retrieveModelFiledInfoBeanList( modelId );

        // 获取自定义模型数据
        // 确认当前是否有扩展模型
        if( modelId.longValue() > 0 && model != null && filedBeanList != null
            && sqlCodeBean != null )
        {

            Integer count = contentDao.queryUserDefinedContentExist( model, infoId );

            boolean editMode = false;

            Map currentObj = null;

            if( count.intValue() == 1 )
            {
                // 已有数据,更新模式
                editMode = true;

                currentObj = contentDao.querySingleUserDefineContent( sqlCodeBean, model
                    .getRelateTableName(), infoId );
            }

            ModelFiledInfoBean bean = null;

            List userDefineParamList = new ArrayList();

            String reUrl = null;

            Object val = null;

            List needUploadImageGroupInfoList = new ArrayList();

            for ( int j = 0; j < filedBeanList.size(); j++ )
            {
                bean = ( ModelFiledInfoBean ) filedBeanList.get( j );
                // 需要引入filed元数据来对不同类型字段进行对应处理

                val = ServiceUtil.disposeDataModelFiledFromWeb( bean, params,
                    needUploadImageGroupInfoList, false );

                if( editMode && !params.containsKey( bean.getFieldSign() ) )
                {
                    val = currentObj.get( bean.getFieldSign() );
                }

                userDefineParamList.add( val );

                // 单图水印处理
                if( Constant.METADATA.UPLOAD_IMG == bean.getHtmlElementId().intValue()
                    && Constant.COMMON.ON.equals( bean.getNeedMark() ) )
                {
                    // 水印处理
                    reUrl = ServiceUtil.getImageReUrl( ( String ) val );

                    // 已经加过水印的不需要再增加
                    if( !Constant.COMMON.ON.equals( resService.getImageMarkStatus( reUrl ) ) )
                    {
                        if( ServiceUtil.disposeImageMark( ( SiteGroupBean ) SecuritySessionKeeper
                            .getSecuritySession().getCurrentLoginSiteInfo(), reUrl, Integer
                            .valueOf( ServiceUtil.getImageW( ( String ) val ) ), Integer
                            .valueOf( ServiceUtil.getImageH( ( String ) val ) ) ) )
                        {
                            // 成功加水印则更新
                            resService.setImageMarkStatus( reUrl, Constant.COMMON.ON );
                        }
                    }
                }
            }

            // 添加ID到最后位置
            userDefineParamList.add( infoId );

            if( editMode )
            {
                // 已有数据,更新模式
                contentDao.saveOrUpdateModelContent( sqlCodeBean.getUpdateSql(),
                    userDefineParamList.toArray() );
            }
            else
            {
                contentDao.saveOrUpdateModelContent( sqlCodeBean.getInsertSql(),
                    userDefineParamList.toArray() );
            }

            /**
             * 所有图集组件出现的图片入库
             */
            // 获取原图集记录
            List oldGroupPhotoList = contentDao.queryGroupPhotoInfoByContentId( infoId, modelType,
                ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                    .getCurrentLoginSiteInfo(), true );

            // 删除所有原相关信息
            contentDao.deletePhotoGroupInfo( infoId, modelType );

            // 增加本次改动中出现的所有的图片信息
            PhotoGroupInfo pgi = null;
            Set urlInfoSet = new HashSet();

            for ( int i = 0; i < needUploadImageGroupInfoList.size(); i++ )
            {
                pgi = ( PhotoGroupInfo ) needUploadImageGroupInfoList.get( i );

                urlInfoSet.add( pgi.getUrl() );

                pgi.setContentId( infoId );

                // 模型类型
                pgi.setModelType( modelType );

                contentDao.saveSingleGroupPhoto( pgi );

                // 水印处理
                if( Constant.COMMON.ON.equals( pgi.getNeedMark() ) )
                {
                    reUrl = ServiceUtil.getImageReUrl( pgi.getUrl() );

                    // 已经加过水印的不需要再增加
                    if( !Constant.COMMON.ON.equals( resService.getImageMarkStatus( reUrl ) ) )
                    {
                        if( ServiceUtil.disposeImageMark( ( SiteGroupBean ) SecuritySessionKeeper
                            .getSecuritySession().getCurrentLoginSiteInfo(), reUrl, Integer
                            .valueOf( ServiceUtil.getImageW( pgi.getUrl() ) ), Integer
                            .valueOf( ServiceUtil.getImageH( pgi.getUrl() ) ) ) )
                        {
                            // 成功加水印则更新
                            resService.setImageMarkStatus( reUrl, Constant.COMMON.ON );
                        }
                    }
                }
            }

            Map pgiInfo = null;
            for ( int i = 0; i < oldGroupPhotoList.size(); i++ )
            {
                pgiInfo = ( Map ) oldGroupPhotoList.get( i );

                if( !urlInfoSet.contains( pgiInfo.get( "cmsSysUrl" ) ) )
                {
                    resService.updateSiteResourceTraceUseStatus( Long.valueOf( ( String ) pgiInfo
                        .get( "resId" ) ), Constant.COMMON.OFF );
                }

            }

        }
    }

    /**
     * 删除模型数据和资源(通用)
     * 
     * @param contentId
     * @param modelId
     * @param siteFlag
     * @param modelType
     */
    public void deleteAndClearDefModelInfo( Long contentId, Long modelId, String siteFlag,
        Integer modelType )
    {
        List modeFieldList = null;

        DataModelBean modelBean = null;

        ModelPersistenceMySqlCodeBean sqlCodeBean = null;

        if( modelId != null )
        {
            modelBean = retrieveSingleDataModelBeanById( modelId );
        }

        if( modelBean == null )
        {
            return;
        }

        sqlCodeBean = retrieveSingleModelPerMysqlCodeBean( modelId );

        modeFieldList = retrieveModelFiledInfoBeanList( modelId );

        modelBean = retrieveSingleDataModelBeanById( modelId );

        Map mainAndDefInfo = contentDao.querySingleUserDefineContentOnlyModelData( sqlCodeBean,
            modelBean.getRelateTableName(), contentId, siteFlag, modelId );

        /**
         * 删除资源文件以及信息 以下为资源类型字段
         */
        ModelFiledInfoBean bean = null;

        if( modeFieldList != null )
        {
            for ( int j = 0; j < modeFieldList.size(); j++ )
            {
                bean = ( ModelFiledInfoBean ) modeFieldList.get( j );

                if( Constant.METADATA.UPLOAD_IMG == bean.getHtmlElementId().intValue() )
                {
                    SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( Long
                        .valueOf( StringUtil.getLongValue( ( String ) mainAndDefInfo.get( bean
                            .getFieldSign()
                            + "ResId" ), -1 ) ) );

                    if( resBean != null )
                    {
                        // 更新文件使用状态
                        resService.updateSiteResourceTraceUseStatus( resBean.getResId(),
                            Constant.COMMON.OFF );
                    }
                }
                else if( Constant.METADATA.UPLOAD_MEDIA == bean.getHtmlElementId().intValue() )
                {
                    SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( Long
                        .valueOf( StringUtil.getLongValue( ( String ) mainAndDefInfo.get( bean
                            .getFieldSign()
                            + "ResId" ), -1 ) ) );

                    if( resBean != null )
                    {
                        // 更新文件使用状态
                        resService.updateSiteResourceTraceUseStatus( resBean.getResId(),
                            Constant.COMMON.OFF );

                        String cover = StringUtil.isStringNull( resBean.getCover() ) ? "" : resBean
                            .getCover();

                        if( StringUtil.isStringNotNull( resBean.getCover() ) )
                        {
                            SiteResourceBean coverResBean = resService
                                .retrieveSingleResourceBeanBySource( cover );

                            if( coverResBean != null )
                            {
                                resService.updateSiteResourceTraceUseStatus( coverResBean
                                    .getResId(), Constant.COMMON.OFF );
                            }
                        }
                    }
                }
                else if( Constant.METADATA.UPLOAD_FILE == bean.getHtmlElementId().intValue() )
                {
                    SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( Long
                        .valueOf( StringUtil.getLongValue( ( String ) mainAndDefInfo.get( bean
                            .getFieldSign() ), -1 ) ) );

                    if( resBean != null )
                    {
                        // 更新文件使用状态
                        resService.updateSiteResourceTraceUseStatus( resBean.getResId(),
                            Constant.COMMON.OFF );
                    }
                }
                else if( Constant.METADATA.UPLOAD_IMG_GROUP == bean.getHtmlElementId().intValue() )
                {
                    List imageGroupList = contentDao.queryGroupPhotoInfoByContentId( contentId,
                        modelType, null, true );

                    Map imageInfo = null;

                    for ( int k = 0; k < imageGroupList.size(); k++ )
                    {
                        imageInfo = ( Map ) imageGroupList.get( k );

                        SiteResourceBean resBean = resService
                            .retrieveSingleResourceBeanByResId( Long.valueOf( StringUtil
                                .getLongValue( ( String ) imageInfo.get( "resId" ), -1 ) ) );

                        if( resBean != null )
                        {
                            // 更新文件使用状态
                            resService.updateSiteResourceTraceUseStatus( resBean.getResId(),
                                Constant.COMMON.OFF );
                        }

                    }

                }
                else if( Constant.METADATA.EDITER == bean.getHtmlElementId().intValue() )
                {
                    ServiceUtil.disposeTextHaveSiteResId( null, ( String ) mainAndDefInfo.get( bean
                        .getFieldSign() ), new HashSet(), contentId, true );
                }
            }
        }

        // 删除图集信息,并改动资源使用状态,如果有的话
        contentDao.deletePhotoGroupInfo( contentId, modelType );

        if( modelBean != null )
        {
            contentDao.deleteUserDefineInfo( modelBean, contentId );
        }

    }

    public void deleteAndClearDefModelInfo( Long contentId, Long modelId, String siteFlag )
    {
        DataModelBean modelBean = retrieveSingleDataModelBeanById( modelId );

        if( modelBean != null )
        {

            List modeFieldList = retrieveModelFiledInfoBeanList( modelId );

            ModelPersistenceMySqlCodeBean sqlCodeBean = retrieveSingleModelPerMysqlCodeBean( modelId );

            Map mainAndDefInfo = contentDao.querySingleUserDefineContentOnlyModelData( sqlCodeBean,
                modelBean.getRelateTableName(), contentId, siteFlag, modelId );

            ModelFiledInfoBean bean = null;

            for ( int j = 0; j < modeFieldList.size(); j++ )
            {
                bean = ( ModelFiledInfoBean ) modeFieldList.get( j );

                if( Constant.METADATA.UPLOAD_IMG == bean.getHtmlElementId().intValue() )
                {
                    SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( Long
                        .valueOf( StringUtil.getLongValue( ( String ) mainAndDefInfo.get( bean
                            .getFieldSign()
                            + "ResId" ), -1 ) ) );

                    if( resBean != null )
                    {
                        // 更新文件使用状态
                        resService.updateSiteResourceTraceUseStatus( resBean.getResId(),
                            Constant.COMMON.OFF );
                    }
                }
                else if( Constant.METADATA.UPLOAD_MEDIA == bean.getHtmlElementId().intValue() )
                {
                    SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( Long
                        .valueOf( StringUtil.getLongValue( ( String ) mainAndDefInfo.get( bean
                            .getFieldSign()
                            + "ResId" ), -1 ) ) );

                    if( resBean != null )
                    {
                        // 更新文件使用状态
                        resService.updateSiteResourceTraceUseStatus( resBean.getResId(),
                            Constant.COMMON.OFF );

                        String cover = StringUtil.isStringNull( resBean.getCover() ) ? "" : resBean
                            .getCover();

                        if( StringUtil.isStringNotNull( resBean.getCover() ) )
                        {
                            SiteResourceBean coverResBean = resService
                                .retrieveSingleResourceBeanBySource( cover );

                            if( coverResBean != null )
                            {
                                resService.updateSiteResourceTraceUseStatus( coverResBean
                                    .getResId(), Constant.COMMON.OFF );
                            }
                        }
                    }
                }
                else if( Constant.METADATA.UPLOAD_FILE == bean.getHtmlElementId().intValue() )
                {
                    SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( Long
                        .valueOf( StringUtil.getLongValue( ( String ) mainAndDefInfo.get( bean
                            .getFieldSign() ), -1 ) ) );

                    if( resBean != null )
                    {
                        // 更新文件使用状态
                        resService.updateSiteResourceTraceUseStatus( resBean.getResId(),
                            Constant.COMMON.OFF );
                    }
                }
                else if( Constant.METADATA.UPLOAD_IMG_GROUP == bean.getHtmlElementId().intValue() )
                {
                    List imageGroupList = contentDao.queryGroupPhotoInfoByContentId( contentId,
                        Constant.METADATA.MODEL_TYPE_CONTENT, null, true );

                    Map imageInfo = null;

                    for ( int k = 0; k < imageGroupList.size(); k++ )
                    {
                        imageInfo = ( Map ) imageGroupList.get( k );

                        SiteResourceBean resBean = resService
                            .retrieveSingleResourceBeanByResId( Long.valueOf( ( String ) imageInfo
                                .get( "resId" ) ) );

                        if( resBean != null )
                        {
                            // 更新文件使用状态
                            resService.updateSiteResourceTraceUseStatus( resBean.getResId(),
                                Constant.COMMON.OFF );
                        }

                    }

                }
                else if( Constant.METADATA.EDITER == bean.getHtmlElementId().intValue() )
                {
                    ServiceUtil.disposeTextHaveSiteResId( null, ( String ) mainAndDefInfo.get( bean
                        .getFieldSign() ), new HashSet(), contentId, true );
                }
            }

            // 删除defInfo
            contentDao.deleteUserDefineInfo( modelBean, contentId );

        }
    }

    public List retrieveAllModelLinkFieldInfoByModelId( Long modelId )
    {
        return metaDataDao.queryModelLinkFieldInfo( modelId );
    }

    public Long retrieveAllFormDataCountForModelId( Long modelId )
    {
        DataModelBean model = metaDataDao.querySingleDataModelBeanById( modelId );

        if( model == null )
        {
            return Long.valueOf( 0 );
        }

        return metaDataDao.queryDefFormDataCount( model );

    }

    public List retrieveFormDataByIdTrace( Long modelId, Long prevId, Integer limitCount )
    {
        DataModelBean model = metaDataDao.querySingleDataModelBeanById( modelId );

        if( model == null )
        {
            return new ArrayList();
        }

        return metaDataDao.queryDefFormDataByIdTrace( model, prevId, limitCount );
    }

    public Map retrieveSingleFormDataById( Long siteId, Long modelId, Long contentId )
    {
        DataModelBean model = metaDataDao.querySingleDataModelBeanById( modelId );

        if( model == null )
        {
            return new HashMap();
        }

        return metaDataDao.querySingleFormDataById( siteId, model,
            retrieveSingleModelPerMysqlCodeBean( model.getDataModelId() ), contentId );
    }

    public Map retrieveSingleFormDataById( Long contentId )
    {
        Cache cache = formDataCache;

        String key = "retrieveSingleFormDataById:" + contentId;

        Map result = ( Map ) cache.getEntry( key );

        if( result == null )
        {
            Map main = metaDataDao.querySinagleFormDataMainById( contentId );

            DataModelBean model = metaDataDao.querySingleDataModelBeanById( ( Long ) main
                .get( "modelId" ) );

            if( model == null )
            {
                result = new HashMap();
            }
            else
            {
                result = metaDataDao.querySingleFormDataById( ( Long ) main.get( "siteId" ), model,
                    retrieveSingleModelPerMysqlCodeBean( model.getDataModelId() ), contentId );

                result.putAll( main );
            }

            cache.putEntry( key, result );
        }

        return result;
    }

    public List retrieveFormDataByIds( Long modelId, List idList )
    {
        DataModelBean model = metaDataDao.querySingleDataModelBeanById( modelId );

        if( model == null )
        {
            return null;
        }

        return metaDataDao.queryFormDataByIds( model, retrieveSingleModelPerMysqlCodeBean( model
            .getDataModelId() ), idList );
    }

    public void changeFormDataStatus( List idList, Long modelId, Integer censorState )
    {
        if( idList == null )
        {
            return;
        }

        DataModelBean modelBean = retrieveSingleDataModelBeanById( modelId );

        if( modelBean == null )
        {
            return;// 表单模型不存在
        }

        try
        {
            mysqlEngine.beginTransaction();

            Long contentId = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                if( idList.get( i ) instanceof Long )
                {
                    contentId = ( Long ) idList.get( i );
                }
                else
                {
                    contentId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ),
                        -1 ) );
                }

                if( contentId.longValue() < 0 )
                {
                    continue;
                }

                metaDataDao.updateDefFormMainInfoCensorStatus( contentId, censorState );

                SearchIndexContentState searchIndexState = new SearchIndexContentState();

                searchIndexState.setClassId( Long.valueOf( -999999999 ) );
                searchIndexState.setContentId( contentId );

                searchIndexState.setCensor( censorState );

                searchIndexState.setIndexDate( new Date( DateAndTimeUtil.clusterTimeMillis() ) );

                searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_EDIT );

                searchIndexState.setModelId( modelBean.getDataModelId() );

                searchIndexState.setSiteId( ( ( SiteGroupBean ) SecuritySessionKeeper
                    .getSecuritySession().getCurrentLoginSiteInfo() ).getSiteId() );

                searchService.addIndexContentState( searchIndexState );
            }
            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            resetFormDataCache();
        }

    }

    public List getDefFormShowFieldTag( String modelId )
    {
        Long mid = Long.valueOf( StringUtil.getLongValue( modelId, -1 ) );

        return metaDataDao.queryDefFormShowListField( mid );
    }

    public List getDefFormQueryFieldTag( String modelId )
    {
        Long mid = Long.valueOf( StringUtil.getLongValue( modelId, -1 ) );

        return metaDataDao.queryDefFormQueryField( mid );
    }

    public List getDefFormSearchFieldTag( String modelId )
    {
        Long mid = Long.valueOf( StringUtil.getLongValue( modelId, -1 ) );

        return metaDataDao.queryDefFormSearchField( mid );
    }

    public Object getModelFieldInfoValTag( String modelId, String contentId, String fName )
    {
        if( fName == null )
        {
            fName = "";
        }

        Long mid = Long.valueOf( StringUtil.getLongValue( modelId, -1 ) );

        Long cid = Long.valueOf( StringUtil.getLongValue( contentId, -1 ) );

        Map info = ContentService.getInstance()
            .retrieveSingleUserDefineContentManageMode( mid, cid );

        return info.get( fName );
    }

    public Object getModelPickFieldTag( String modelId )
    {
        Long mid = Long.valueOf( StringUtil.getLongValue( modelId, -1 ) );

        return metaDataDao.queryPickModelField( mid );

    }

    private Object changeSQLParamToObject( ModelFiledInfoBean bean, String sqlVal )
    {
        if( Constant.METADATA.INT_TYPE.equals( bean.getDataType() ) )
        {
            return Integer.valueOf( StringUtil.getIntValue( sqlVal, -888888888 ) );
        }
        else if( Constant.METADATA.LONG_TYPE.equals( bean.getDataType() ) )
        {
            return Long.valueOf( StringUtil.getLongValue( sqlVal, -8888888888888888888L ) );
        }
        else if( Constant.METADATA.DOUBLE_TYPE.equals( bean.getDataType() ) )
        {
            return Double.valueOf( StringUtil.getDoubleValue( sqlVal, -88888888888888888.8888888 ) );
        }
        else if( Constant.METADATA.DATETIME_TYPE.equals( bean.getDataType() ) )
        {
            return DateAndTimeUtil.getTimestamp( sqlVal, DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );
        }
        else if( Constant.METADATA.DATE_TYPE.equals( bean.getDataType() ) )
        {
            return DateAndTimeUtil.getTimestamp( sqlVal, DateAndTimeUtil.DEAULT_FORMAT_YMD );
        }
        else if( Constant.METADATA.TIME_TYPE.equals( bean.getDataType() ) )
        {
            return DateAndTimeUtil.getTimestamp( sqlVal, DateAndTimeUtil.DEAULT_FORMAT_HMS );
        }

        return sqlVal;
    }

    public static void resetCache()
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveModelFiledInfoBeanList" );
        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "retrieveAllDataModelBeanList" );
        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "retrieveSingleModelPerMysqlCodeBean" );
        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "retrieveSystemModelFiledInfoBeanList" );
        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "retrieveAllModelValidateConfigBean" );
        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "retrieveSingleModelValidateConfigBean" );
        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "retrieveContentPathInjectAssistInfo" );
        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "retrieveAllSearchFieldStrArrayMode" );
        cache.clearAllEntry();

        // DAO层cache同时失效
        MetaDataDao.clearCache();
    }

    public static void resetFormDataCache()
    {
        listFormDataCache.clearAllEntry();
        listCountCache.clearAllEntry();
        formDataCache.clearAllEntry();
    }

}
