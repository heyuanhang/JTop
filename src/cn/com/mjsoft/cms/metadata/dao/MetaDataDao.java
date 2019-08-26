package cn.com.mjsoft.cms.metadata.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.dao.ContentValueResultCallBack;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.bean.ModelValidateConfigBean;
import cn.com.mjsoft.cms.metadata.bean.PathInjectAssistBean;
import cn.com.mjsoft.cms.metadata.dao.vo.DataModel;
import cn.com.mjsoft.cms.metadata.dao.vo.ModelFiledMetadata;
import cn.com.mjsoft.cms.metadata.dao.vo.ModelHtmlConfig;
import cn.com.mjsoft.cms.metadata.dao.vo.PathInjectAssist;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;

public class MetaDataDao
{
    private Logger log = Logger.getLogger( this.getClass() );

    public static Map cacheManager = new HashMap();

    static
    {
        cacheManager.put( "retrieveSingleDataModelBean", new ClusterCacheAdapter( 500,
            "metaDataDao.retrieveSingleDataModelBean" ) );

        cacheManager.put( "querySingleFiledNameForQueryData", new ClusterCacheAdapter( 3000,
            "metaDataDao.querySingleFiledNameForQueryData" ) );
        
        cacheManager.put( "querySingleDataModelFieldBeanById", new ClusterCacheAdapter( 4000,
            "metaDataDao.querySingleDataModelFieldBeanById" ) );

        cacheManager.put( "queryAllDataModelBeanListByModelTypeAndSiteId", new ClusterCacheAdapter(
            600, "metaDataDao. " ) );
    }

    public PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public MetaDataDao()
    {

    }

    public MetaDataDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public UpdateState saveDataModel( DataModel dataModel )
    {
        return pe.save( dataModel );
    }

    public UpdateState updateDataModelBaseInfo( DataModel dataModel )
    {
        String sql = "update model set modelName=?, validateBehavior=?, beforeBehavior=?, afterBehavior=?, titleMode=?, kwMode=?, simpleMode=?, titleCol=?, privateMode=?, ico=?, remark=?, isManageEdit=?, isMemberEdit=?, mustCensor=?, mustToken=? where dataModelId=?";

        return pe.update( sql, dataModel );
    }

    public List queryAllDataModelBeanListWithStateAndSiteId( Integer userState, Integer modelType,
        Long siteId )
    {
        String sql = "select * from (select * from model where useState=? and modelType=? and siteId=? and privateMode=1"
            + " union "
            + " select * from model where useState=? and modelType=? and privateMode=0) tmp order by dataModelId desc";

        return pe.query( sql, new Object[] { userState, modelType, siteId, userState, modelType },
            new DataModelBeanTransform() );
    }

    public List queryAllDataModelBeanListByModelTypeAndSiteId( Integer modelType, Long siteId )
    {
        String key = "queryAllDataModelBeanListByModelTypeAndSiteId:" + modelType + "|" + siteId;

        Cache cache = ( Cache ) cacheManager.get( "queryAllDataModelBeanListByModelTypeAndSiteId" );

        List result = ( List ) cache.getEntry( key );

        if( result == null )
        {
            String sql = "select * from (select * from model where modelType=? and siteId=? and privateMode=1"
                + " union "
                + " select * from model where modelType=? and privateMode=0) tmp order by dataModelId desc";

            result = pe.query( sql, new Object[] { modelType, siteId, modelType },
                new DataModelBeanTransform() );

            cache.putEntry( key, result );
        }

        return result;
    }

    public List queryAllDataModelBeanListByModelTypeAndSiteIdPrivateMode( Integer modelType,
        Long siteId, Integer pm )
    {
        String sql = "select * from model where modelType=? and siteId=? and privateMode=? order by dataModelId desc";

        return pe.query( sql, new Object[] { modelType, siteId, pm }, new DataModelBeanTransform() );
    }

    public List queryDataModelBeanListByModelTypeAndSiteId( Integer modelType, Long siteId )
    {

        String key = "queryDataModelBeanListByModelTypeAndSiteId:" + modelType + "|" + siteId;

        Cache cache = ( Cache ) cacheManager.get( "queryAllDataModelBeanListByModelTypeAndSiteId" );

        List result = ( List ) cache.getEntry( key );

        if( result == null )
        {
            String sql = "select * from model where modelType=? and siteId=? order by dataModelId desc";

            result = pe.query( sql, new Object[] { modelType, siteId },
                new DataModelBeanTransform() );

            cache.putEntry( key, result );
        }

        return result;
    }

    public List queryAllDataModelIdListBySiteIdPrivateMode( Long siteId, Integer pm )
    {
        String sql = "select dataModelId from model where siteId=? and privateMode=?";

        return pe.querySingleCloumn( sql, new Object[] { siteId, pm }, Long.class );
    }

    public Double queryMaxDataModelOrderId( Long dataModelId )
    {
        String sql = "select max(orderId) from model_filed_metadata where dataModelId=?";

        return ( Double ) pe.querySingleObject( sql, new Object[] { dataModelId }, Double.class );
    }

    public void updateColumnDefVal( String relateTableName, String fieldName, String defVal )
    {
        String sql = "update " + relateTableName + " set " + fieldName + "=?";
        pe.update( sql, new Object[] { defVal } );
    }

    public void updateColumnDefValById( String relateTableName, String fieldName, String defVal,
        Long contentId )
    {
        String sql = "update " + relateTableName + " set " + fieldName + "=? where contentId=?";
        pe.update( sql, new Object[] { defVal, contentId } );
    }

    public UpdateState saveModelMetadata( ModelFiledMetadata filedMetadata )
    {
        // String sql = "insert into model_filed_metadata ()";
        return pe.save( filedMetadata );
    }

    public UpdateState saveModelFiledHtmlConfig( ModelHtmlConfig config )
    {
        // String sql = "insert into model_filed_metadata ()";
        return pe.save( config );
    }

    // public List queryModelFiledInfoBeanList( Long modelId )
    // {
    // String sql = "select mc.*, md.* from model_filed_metadata md left join
    // model_html_config mc on md.metaDataId=mc.metaDataId where
    // md.dataModelId=? order by md.orderId";
    //
    // return pe.query( sql, new Object[] { modelId },
    // new ModelFiledInfoBeanTransform() );
    // }

    public List queryUserDefinedModelFiledInfoBeanList( Long modelId )
    {
        String sql = "select mc.*, md.* from model_filed_metadata md left join model_html_config mc on md.metaDataId=mc.metaDataId where md.dataModelId=? order by md.orderId asc";

        return pe.query( sql, new Object[] { modelId }, new ModelFiledInfoBeanTransform() );
    }
    
    public List queryAllEditorModelFiledInfoBeanList( )
    {
        String sql = "select mc.*, md.* from model_filed_metadata md left join model_html_config mc on md.metaDataId=mc.metaDataId where mc.htmlElementId=3 order by md.orderId asc";

        return pe.query( sql , new ModelFiledInfoBeanTransform() );
    }
    
    public List queryEditorTextByField(DataModelBean model, ModelFiledInfoBean field, Long startPos, Integer limit )
    {       
        String sql = "select contentId, "+field.getRelateFiledName()+ " from "+model.getRelateTableName()+" where contentId<? order by contentId desc limit ?";
        
        return  pe.queryResultMap ( sql, new Object[]{startPos, limit});
    }
    
    public void updateEditorTextById(DataModelBean model, ModelFiledInfoBean field, String text, Long cId )
    {       
         
        String sql = "update "+model.getRelateTableName()+" set "+field.getRelateFiledName()+"=? where contentId=?";
        
         pe.update ( sql, new Object[]{text, cId});
    }

    public UpdateState deleteDataModelFiledMetadata( Long filedMetedataId )
    {
        return pe.delete( ModelFiledMetadata.class, filedMetedataId );
    }

    public UpdateState deleteHtmlConfigByItsFiledId( Long metaDataId )
    {
        String sql = "delete from model_html_config where metaDataId=?";
        return pe.update( sql, new Object[] { metaDataId } );

    }

    /**
     * 生成模型对应的表系统必需基础数据模型MYSQL sql。
     * 
     * @param dataModel
     * @return
     */
    public UpdateState createModelRelatingMysqlTable( DataModel dataModel )
    {
        String cs = SystemConfiguration.getInstance().getSystemConfig().getSysPro().getProperty(
        "db_cs" );
        
        StringBuffer sqlBuf = new StringBuffer( "create table IF NOT EXISTS " );

        sqlBuf.append( dataModel.getRelateTableName() + " (" );
        sqlBuf.append( Constant.METADATA.CONTENT_ID_NAME + " bigint(20) NOT NULL,"
        // AUTO_INCREMENT
            // + "classId bigint(20) NOT NULL,"
            // + "title varchar(255) NOT NULL,creator varchar(50) DEFAULT
            // NULL,clickCount bigint(20),"
            // + "systemHandleTime varchar(25) NOT NULL,staticPageUrl
            // varchar(200) DEFAULT NULL,produceType smallint(11) DEFAULT
            // NULL,censorState smallint(11) DEFAULT NULL,orderIdFlag bigint(20)
            // NOT NULL,tagKey varchar(260) DEFAULT NULL,"
            // + "appearStartDateTime timestamp NOT NULL DEFAULT '0000-00-00
            // 00:00:00',appearEndDateTime timestamp NOT NULL DEFAULT
            // '0000-00-00 00:00:00',"
            + "PRIMARY KEY (" );
        sqlBuf.append( Constant.METADATA.CONTENT_ID_NAME );
        sqlBuf.append( ")) ENGINE=InnoDB DEFAULT CHARSET="+cs );

        return pe.update( sqlBuf.toString() );

    }

    public UpdateState createOrModifyModelRelatingMysqlColumn( ModelFiledMetadata metadata,
        String targetModelTable, boolean editMode )
    {
        String dbType = null;

        // TODO 决定数据库列类型,此方法有必要考虑根据各数据库一同提升到业务逻辑范围
        if( Constant.METADATA.MYSQL_VARCHAR.equals( metadata.getPerdureType() ) )
        {
            dbType = "varchar(" + metadata.getCapacity() + ")";
        }
        else if( Constant.METADATA.MYSQL_TEXT.equals( metadata.getPerdureType() ) )
        {
            dbType = "text";
        }
        else if( Constant.METADATA.MYSQL_LONGTEXT.equals( metadata.getPerdureType() ) )
        {
            dbType = "longtext";
        }
        else if( Constant.METADATA.MYSQL_INT.equals( metadata.getPerdureType() ) )
        {
            dbType = "int";
        }
        else if( Constant.METADATA.MYSQL_BIGINT.equals( metadata.getPerdureType() ) )
        {
            dbType = "bigint";
        }
        else if( Constant.METADATA.MYSQL_DOUBLE.equals( metadata.getPerdureType() ) )
        {
            dbType = "double";
        }
        else if( Constant.METADATA.MYSQL_DATETIME.equals( metadata.getPerdureType() ) )
        {
            dbType = "datetime DEFAULT '0000-00-00 00:00:00'";
        }
        else if( Constant.METADATA.MYSQL_DATE.equals( metadata.getPerdureType() ) )
        {
            dbType = "date DEFAULT '0000-00-00'";
        }
        else if( Constant.METADATA.MYSQL_TIME.equals( metadata.getPerdureType() ) )
        {
            dbType = "time DEFAULT '00:00:00'";
        }

        StringBuffer sqlBuf = new StringBuffer( "alter table " );

        sqlBuf.append( targetModelTable );

        if( editMode )
        {
            sqlBuf.append( " modify " );
        }
        else
        {
            sqlBuf.append( " add column " );
        }

        sqlBuf.append( metadata.getRelateFiledName() );

        sqlBuf.append( " " + dbType );

        UpdateState us = null;
        try
        {
            us = pe.update( sqlBuf.toString() );
        }
        catch ( Exception e )
        {
            String errorMsgTest = "Row size too large. The maximum row size for the used table type, not counting BLOBs, is 65535. You have to change some columns to TEXT or BLOBs";

            String msg = e.getMessage();
            if( msg.indexOf( errorMsgTest ) != -1
                && Constant.METADATA.MYSQL_VARCHAR.equals( metadata.getPerdureType() ) )
            {
                sqlBuf = new StringBuffer( "alter table " );

                sqlBuf.append( targetModelTable );

                if( editMode )
                {
                    sqlBuf.append( " modify " );
                }
                else
                {
                    sqlBuf.append( " add column " );
                }

                sqlBuf.append( metadata.getRelateFiledName() );

                sqlBuf.append( " " + "text" );

                us = pe.update( sqlBuf.toString() );
            }
        }

        return us;

    }

    public UpdateState createModelRelatingMysqlColumnForDateOrderHelper( String targetModelTable,
        String filedName )
    {
        StringBuffer sqlBuf = new StringBuffer( "alter table " );

        sqlBuf.append( targetModelTable );

        sqlBuf.append( " add column " );

        sqlBuf.append( filedName );

        sqlBuf.append( " bigint" );

        return pe.update( sqlBuf.toString() );

    }

    public String queryTargetModelTableNameByModelId( Long dataModelId )
    {
        String sql = "select relateTableName from model where dataModelId=?";

        return ( String ) pe.querySingleObject( sql, new Object[] { dataModelId }, String.class );

    }

    public boolean queryHaveSameFiledSign( ModelFiledMetadata metadata )
    {
        String sql = "select count(*) from model_filed_metadata where filedSign=? and dataModelId=?";

        Integer count = ( Integer ) pe.querySingleObject( sql, new Object[] {
            metadata.getFiledSign(), metadata.getDataModelId() }, Integer.class );

        if( count.intValue() > 0 )
        {
            return true;
        }

        return false;

    }

    public void dropMysqlFiledbyId( String modelTableName, String modelFiledName )
    {
        StringBuffer buf = new StringBuffer( "alter table " );
        buf.append( modelTableName );
        buf.append( " drop column " );
        buf.append( modelFiledName );

        try
        {
            pe.update( buf.toString() );
        }
        catch ( Exception e )
        {
            // e.printStackTrace();
        }

    }

    public String querySingleModelTableName( Long filedMetedataId )
    {
        String sql = "select relateTableName from model mo,model_filed_metadata mf where mo.dataModelId=mf.dataModelId and mf.metaDataId=?";
        return ( String ) pe
            .querySingleObject( sql, new Object[] { filedMetedataId }, String.class );
    }

    public String querySingleFiledName( Long filedMetedataId )
    {
        String sql = "select relateFiledName from model_filed_metadata where metaDataId=?";
        return ( String ) pe
            .querySingleObject( sql, new Object[] { filedMetedataId }, String.class );
    }

    public String querySingleFiledNameForQueryData( Long filedMetedataId )
    {
        String sql = "select relateFiledName from model_filed_metadata where metaDataId=?";

        Cache cache = ( Cache ) cacheManager.get( "querySingleFiledNameForQueryData" );

        String filedName = ( String ) cache.getEntry( filedMetedataId );

        if( filedName == null )
        {
            filedName = ( String ) pe.querySingleObject( sql, new Object[] { filedMetedataId },
                String.class );

            cache.putEntry( filedMetedataId, filedName );
        }

        return filedName;
    }

    public String querySingleFiledSign( Long filedMetedataId )
    {
        String sql = "select filedSign from model_filed_metadata where metaDataId=?";
        return ( String ) pe
            .querySingleObject( sql, new Object[] { filedMetedataId }, String.class );
    }

    public Integer querySingleFiledDataType( Long filedMetedataId )
    {
        String sql = "select perdureType from model_filed_metadata where metaDataId=?";
        return ( Integer ) pe.querySingleObject( sql, new Object[] { filedMetedataId },
            Integer.class );
    }

    public Integer querySingleFiledOrderFlag( Long filedMetedataId )
    {
        String sql = "select orderFlag from model_filed_metadata where metaDataId=?";
        return ( Integer ) pe.querySingleObject( sql, new Object[] { filedMetedataId },
            Integer.class );
    }

    public Integer querySinglFiledCharLength( Long filedMetedataId )
    {
        String sql = "select maxLength from model_html_config where metaDataId=?";
        return ( Integer ) pe.querySingleObject( sql, new Object[] { filedMetedataId },
            Integer.class );
    }

    public Map querySystemHtmlElementInfo()
    {
        String sql = "select * from model_html_element";

        HtmlElementInfoMapValueRowTransform infoTransform = new HtmlElementInfoMapValueRowTransform();
        pe.query( sql, infoTransform );

        return infoTransform.getInfoMap();
    }

    public DataModelBean querySingleDataModelBeanById( Long modelId )
    {
        String sql = "select * from model where dataModelId=?";

        Cache cache = ( Cache ) cacheManager.get( "retrieveSingleDataModelBean" );

        DataModelBean bean = ( DataModelBean ) cache.getEntry( modelId );

        if( bean == null )
        {
            bean = ( DataModelBean ) pe.querySingleRow( sql, new Object[] { modelId },
                new DataModelBeanTransform() );
            cache.putEntry( modelId, bean );
        }

        return bean;
    }

    public DataModelBean querySingleDataModelBeanByName( String modelName )
    {
        String sql = "select * from model where modelName=?";

        if( StringUtil.isStringNull( modelName ) )
        {
            return null;
        }

        Cache cache = ( Cache ) cacheManager.get( "retrieveSingleDataModelBean" );

        DataModelBean bean = ( DataModelBean ) cache.getEntry( modelName );

        if( bean == null )
        {
            bean = ( DataModelBean ) pe.querySingleRow( sql, new Object[] { modelName },
                new DataModelBeanTransform() );
            cache.putEntry( modelName, bean );
        }

        return bean;
    }

    public void updateMetedataOrder( Long id, Double order )
    {
        String sql = "update model_filed_metadata set orderId=? where metaDataId=?";

        pe.update( sql, new Object[] { order, id } );
    }

    public void updateMetedataBlankCount( Long id, Integer count )
    {
        String sql = "update model_html_config set blankCount=? where metaDataId=?";

        pe.update( sql, new Object[] { count, id } );
    }

    public List queryModelFieldValue( DataModelBean modelBean, String fieldName )
    {
        String sql = "select contentId, " + fieldName + " as val from "
            + modelBean.getRelateTableName();

        return pe.queryResultMap( sql );
    }

    public Integer queryHaveMetedataEntitySql( Long dataModelId )
    {
        String sql = "select count(*) from model_persistence_code where dataModelId=?";

        return ( Integer ) pe.querySingleObject( sql, new Object[] { dataModelId }, Integer.class );
    }

    public void saveMetedataEntitySql( EntitySqlBridge sqlBridge, Long dataModelId )
    {
        String sql = "insert into model_persistence_code (dataModelId, insertSql, updateSql, deleteSql, selectSql, selectColumn, listSelectColumn) values (? ,?, ?, ?, ?, ?, ?)";
        pe.update( sql, new Object[] { dataModelId, sqlBridge.getInsertSql(),
            sqlBridge.getUpdateSql(), sqlBridge.getDeleteSql(), sqlBridge.getSelectSql(),
            sqlBridge.getSelectColumn(), sqlBridge.getListSelectColumn() } );
    }

    public void updateMetedataEntitySql( EntitySqlBridge sqlBridge, Long dataModelId )
    {
        String sql = "update model_persistence_code set insertSql=?, updateSql=?, deleteSql=?, selectSql=?, selectColumn=? where dataModelId=?";
        pe.update( sql, new Object[] { sqlBridge.getInsertSql(), sqlBridge.getUpdateSql(),
            sqlBridge.getDeleteSql(), sqlBridge.getSelectSql(), sqlBridge.getSelectColumn(),
            dataModelId } );
    }

    /**
     * 取所有系统级别字段
     * 
     * @return
     */
    public List querySystemModelFiledInfoBeanList()
    {
        String sql = "select mc.*, md.* from model_filed_metadata md left join model_html_config mc on md.metaDataId=mc.metaDataId where md.dataModelId=0 order by md.orderId asc";

        return pe.query( sql, new ModelFiledInfoBeanTransform() );
    }

    public ModelPersistenceMySqlCodeBean querySingleModelPerMysqlCodeBean( Long dataModelId )
    {
        String sql = "select * from model_persistence_code where dataModelId=?";

        return ( ModelPersistenceMySqlCodeBean ) pe.querySingleRow( sql,
            new Object[] { dataModelId }, new ModelPersistenceMySqlCodeBeanTransform() );

    }

    public void deleteExtendTable( String relateTableName )
    {
        String sql = "drop table " + relateTableName;

        try
        {
            pe.update( sql );
        }
        catch ( Exception e )
        {
            // 无需打印记录错误
            // e.printStackTrace();
        }
    }

    public void deleteAllMetadataFiledByModelId( Long dataModelId )
    {
        String sql = "delete from model_filed_metadata where dataModelId=?";

        pe.update( sql, new Object[] { dataModelId } );
    }

    public void deleteAllMetadataHtmlConfigByModelId( Long dataModelId )
    {
        String sql = "delete from model_html_config where metaDataId in (select metaDataId from model_filed_metadata where metaDataId=?)";

        pe.update( sql, new Object[] { dataModelId } );

    }

    public void deleteModelPersistenceCodeByModelId( Long dataModelId )
    {
        String sql = "delete from model_persistence_code where dataModelId=?";

        pe.update( sql, new Object[] { dataModelId } );

    }

    public void deleteModelByModelId( Long dataModelId )
    {
        String sql = "delete from model where dataModelId=?";

        pe.update( sql, new Object[] { dataModelId } );
    }

    public void updateModelResType( Long modelId, Integer modelResType )
    {
        String sql = "update model set modelResType=? where dataModelId=?";

        pe.update( sql, new Object[] { modelResType, modelId } );
    }

    public void updateModelMainEditorFieldSign( Long modelId, String filedSign )
    {
        String sql = "update model set mainEditorFieldSign=? where dataModelId=?";

        pe.update( sql, new Object[] { filedSign, modelId } );
    }

    public ModelFiledInfoBean querySingleDataModelFieldBeanById( Long id )
    {
        String sql = "select mc.*, md.* from model_filed_metadata md left join model_html_config mc on md.metaDataId=mc.metaDataId where md.metadataId=?";

        Cache cache = ( Cache ) cacheManager.get( "querySingleDataModelFieldBeanById" );
        
        String key = "querySingleDataModelFieldBeanById:"+id;
        
        ModelFiledInfoBean result = (ModelFiledInfoBean) cache.getEntry(key);
        
        if(result == null)
        {
            result = ( ModelFiledInfoBean ) pe.querySingleRow( sql, new Object[] { id },
                    new ModelFiledInfoBeanTransform() );
            
            cache.putEntry(key, result);
            
        }

        return result;
    }
    
    public ModelFiledInfoBean querySingleDataModelFieldBeanBySign( String sign )
    {
        String sql = "select mc.*, md.* from model_filed_metadata md left join model_html_config mc on md.metaDataId=mc.metaDataId where md.filedSign=?";

        return ( ModelFiledInfoBean ) pe.querySingleRow( sql, new Object[] { sign },
            new ModelFiledInfoBeanTransform() );
    }

    public UpdateState updateModelMetadata( ModelFiledMetadata metadata )
    {
        return pe.update( metadata );
    }

    public void updateModelFiledHtmlConfig( ModelHtmlConfig config )
    {
        String sql = "update model_html_config set htmlElementId=?, isMustFill=?, maxLength=?, defaultValue=?, choiceValue=?, errorMessage=?, htmlContent=?, allowableFile=?, dataType=?, style=?, cssClass=?, javascript=?, htmlDesc=?, checkRegex=?, defaultValidate=?, needMark=?, imageH=?, imageW=?, imageDisposeMode=?, linkType=?, linkModelId=?, linkFieldId=?, useSysUrl=?, fullEditor=?, mainEditor=?, editorW=?, editorH=?, blankCount=?, isListField=?, listShowSize=? where metaDataId=?";
        pe.update( sql, config );
    }

    public List queryAllModelValidateConfigBean()
    {
        String sql = "select * from model_validate_config";

        return pe.query( sql, new ModelValidateConfigBeanTransform() );
    }

    public ModelValidateConfigBean querySingleModelValidateConfigBean( Long configId )
    {
        String sql = "select * from model_validate_config where validateConfigId=?";

        return ( ModelValidateConfigBean ) pe.querySingleRow( sql, new Object[] { configId },
            new ModelValidateConfigBeanTransform() );
    }

    public List queryContentPathInjectAssistInfo( Long modelId )
    {
        String sql = "select * from model_res_path_inject_assist where modelId=?";

        return pe.query( sql, new Object[] { modelId }, new PathInjectAssistBeanTransform() );
    }

    public PathInjectAssistBean querySingleContentPathInjectAssistInfo( Long metaDataId )
    {
        String sql = "select * from model_res_path_inject_assist where metaDataId=?";

        return ( PathInjectAssistBean ) pe.querySingleRow( sql, new Object[] { metaDataId },
            new PathInjectAssistBeanTransform() );
    }

    public void deletePathInjectAssistByMdId( Long metaDataId )
    {
        String sql = "delete from model_res_path_inject_assist where metaDataId=?";

        pe.update( sql, new Object[] { metaDataId } );
    }

    public void deletePathInjectAssistByModelId( Long modelId )
    {
        String sql = "delete from model_res_path_inject_assist where modelId=?";

        pe.update( sql, new Object[] { modelId } );
    }

    public void deleteClassExtModelIdByModelId( Long modelId )
    {
        String sql = "update contentclass set extDataModelId=-1 where extDataModelId=?";

        pe.update( sql, new Object[] { modelId } );
    }

    public void deletePathInjectAssistByFieldName( Long modelId, String fieldName )
    {
        String sql = "delete from model_res_path_inject_assist where modelId=? and fieldName=?";

        pe.update( sql, new Object[] { modelId, fieldName } );
    }

    public void saveNewPathInjectAssistInfo( PathInjectAssist vo )
    {
        pe.save( vo );
    }

    public void updatePathInjectAssistInfo( String fieldName, Long metaDataId )
    {
        String sql = "update model_res_path_inject_assist set fieldName=? where metaDataId=?";
        pe.update( sql, new Object[] { fieldName, metaDataId } );
    }

    public Long querySystemTableCount( String countQuery, Object[] vars )
    {
        if(StringUtil.isStringNull( countQuery ))
        {
            return 1l;
        }
        
        return ( Long ) pe.querySingleObject( countQuery, vars );
    }

    public List querySystemTableByQueryFlag( String query, Object[] vars )
    {
        return pe.queryResultMap( query, vars );
    }

    public List queryMutiQueryContentByQueryFlagAndPageInfo( String query, Object[] vars, Long siteId, Long modelId )
    {
        if(StringUtil.isStringNull( query ))
        {
            return new ArrayList();
        }
        
        return pe.queryResultMap( query, vars, new ContentValueResultCallBack( siteId, "", modelId, true) );
    }

    public List queryIndexMetadataForTable( String table )
    {
        // if( StringUtil.hasSQLDChars( table ) )
        // {
        // throw new FrameworkException( "包含非法字符,本次操作强制中止执行" );
        // }

        String sql = "show index from " + table;
        return pe.queryResultMap( sql );
    }

    public void deleteIndexForTable( String table, String indexName )
    {
        if( SystemSafeCharUtil.hasSQLDChars( table ) )
        {
            throw new FrameworkException( "包含非法字符,本次操作强制中止执行" );
        }

        if( SystemSafeCharUtil.hasSQLDChars( indexName ) )
        {
            throw new FrameworkException( "包含非法字符,本次操作强制中止执行" );
        }
        // PRIMARY
        String sql = "ALTER TABLE " + table + " DROP INDEX " + indexName;
        try
        {
            pe.update( sql );
        }
        catch ( Exception e )
        {
            // 无需打印记录错误,因需要删除时,若无索引不会影响删除结果
            // e.printStackTrace();
        }
    }

    public void createNewIndexForTable( String table, String indexName, String fieldNameStr )
    {
        if( SystemSafeCharUtil.hasSQLDChars( table ) )
        {
            throw new FrameworkException( "包含非法字符,本次操作强制中止执行" );
        }

        if( SystemSafeCharUtil.hasSQLDChars( indexName ) )
        {
            throw new FrameworkException( "包含非法字符,本次操作强制中止执行" );
        }

        String sql = "create index " + indexName + " on " + table + " (" + fieldNameStr + ")";

        try
        {
            pe.update( sql );
        }
        catch ( Exception e )
        {
            // e.printStackTrace();
        }
    }

    public List queryAllSearchFieldSign()
    {
        String sql = "select filedSign from model_filed_metadata where searchFlag=1";

        return pe.querySingleCloumn( sql, String.class );
    }

    public List queryModelLinkFieldInfo( Long modelId )
    {
        String sql = "select * from model_filed_metadata mfm, model_html_config mhc where mfm.dataModelId=? and mfm.metaDataId=mhc.metaDataId and mhc.htmlElementId in (1,7,8,9)";

        return pe.queryResultMap( sql, new Object[] { modelId } );
    }

    public List queryDefFormShowListField( Long modelId )
    {
        String sql = "select * from model_filed_metadata mfm, model_html_config mhc where mfm.dataModelId=? and mfm.metaDataId=mhc.metaDataId and mhc.isListField=1";

        return pe.queryResultMap( sql, new Object[] { modelId } );
    }

    public List queryDefFormQueryField( Long modelId )
    {
        String sql = "select * from model_filed_metadata mfm, model_html_config mhc where mfm.dataModelId=? and mfm.metaDataId=mhc.metaDataId and mfm.queryFlag=1";

        return pe.queryResultMap( sql, new Object[] { modelId } );
    }

    public List queryDefFormSearchField( Long modelId )
    {
        String sql = "select * from model_filed_metadata mfm, model_html_config mhc where mfm.dataModelId=? and mfm.metaDataId=mhc.metaDataId and mfm.searchFlag=1";

        return pe.queryResultMap( sql, new Object[] { modelId } );
    }

    public Long queryDefFormDataCount( DataModelBean model )
    {
        String sql = "select count(*) from " + model.getRelateTableName();

        return ( Long ) pe.querySingleObject( sql, Long.class );
    }

    public List queryDefFormDataByIdTrace( DataModelBean model, Long prevId, Integer limitCount )
    {
        String sql = "select contentId from " + model.getRelateTableName()
            + " where contentId<? order by contentId desc limit ?";

        return pe.queryResultMap( sql, new Object[] { prevId, limitCount } );
    }

    public Map querySingleFormDataById( Long siteId, DataModelBean model,
        ModelPersistenceMySqlCodeBean sqlCodeBean, Long cId )
    {

        String sql = "select tmp.contentId, " + sqlCodeBean.getSelectColumn() + " from "
            + model.getRelateTableName() + " tmp where tmp.contentId=?";

        return pe.querySingleResultMap( sql, new Object[] { cId }, new ContentValueResultCallBack(
            siteId, null, model.getDataModelId() ) );
    }

    public Map querySinagleFormDataMainById( Long cId )
    {
        String sql = "select * from model_def_from_data_id where defFormDataId=?";

        return pe.querySingleResultMap( sql, new Object[] { cId } );
    }

    public void deleteFormDataMainById( Long cId )
    {

        String sql = "delete from model_def_from_data_id where defFormDataId=?";

        pe.update( sql, new Object[] { cId } );
    }

    public List queryFormDataByIds( DataModelBean model, ModelPersistenceMySqlCodeBean sqlCodeBean,
        List idList )
    {
        StringBuffer buf = new StringBuffer( "(" );

        long id = -1;

        boolean havaParam = false;

        for ( int i = 0; i < idList.size(); i++ )
        {
            // check非法参数
            id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );

            if( id < 0 )
            {
                continue;
            }

            havaParam = true;

            buf.append( ( String ) idList.get( i ) );
            if( ( i + 1 ) != idList.size() )
            {
                buf.append( "," );
            }
        }

        buf.append( ")" );

        if( !havaParam )
        {
            return null;
        }

        String sql = "select tmp.contentId, " + sqlCodeBean.getSelectColumn() + " from "
            + model.getRelateTableName() + " tmp where tmp.contentId in " + buf.toString();
        ;

        return pe.queryResultMap( sql );
    }

    public UpdateState saveDefFormMainInfo( Long modelId, Long siteId, Integer censorState )
    {

        String sql = "insert into model_def_from_data_id (modelId, siteId, censorState) values (?, ?, ?)";

        return pe.insert( sql, new Object[] { modelId, siteId, censorState } );
    }

    public void updateDefFormMainInfoCensorStatus( Long cid, Integer censorState )
    {

        String sql = "update model_def_from_data_id set censorState=? where defFormDataId=?";

        pe.update( sql, new Object[] { censorState, cid } );
    }

    public List queryPickModelField( Long modelId )
    {
        String sql = "select * from model_filed_metadata mfm where mfm.dataModelId=? and mfm.pickFlag=1";

        return pe.queryResultMap( sql, new Object[] { modelId } );
    }

    public static void clearCache()
    {
        Cache cache = ( Cache ) cacheManager.get( "retrieveSingleDataModelBean" );
        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "querySingleFiledNameForQueryData" );
        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "queryAllDataModelBeanListByModelTypeAndSiteId" );
        cache.clearAllEntry();
        
        cache = ( Cache ) cacheManager.get( "querySingleDataModelFieldBeanById" );
        cache.clearAllEntry();
    }

    

}
