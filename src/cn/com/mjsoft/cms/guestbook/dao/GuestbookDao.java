package cn.com.mjsoft.cms.guestbook.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.guestbook.bean.GuestbookConfigBean;
import cn.com.mjsoft.cms.guestbook.bean.GuestbookMainInfoBean;
import cn.com.mjsoft.cms.guestbook.dao.vo.GuestbookConfig;
import cn.com.mjsoft.cms.guestbook.dao.vo.GuestbookMainInfo;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.workflow.dao.WorkflowOperationAndExpandInfoBeanTransform;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.util.StringUtil;

public class GuestbookDao
{
    private static Cache cfgCache = new ClusterCacheAdapter( 200, "guestbookDao.cfgCache" );

    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public GuestbookDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public List queryAllGuestbookConfigBeanList( Long siteId )
    {
        String sql = "select * from guestbook_config where siteId=? order by configId desc";
        return pe.query( sql, new Object[] { siteId }, new GuestbookConfigBeanTransform() );
    }

    public List queryAllGuestbookConfigIdList( Long siteId )
    {
        String sql = "select configId from guestbook_config where siteId=?";

        return pe.querySingleCloumn( sql, new Object[] { siteId }, Long.class );
    }

    public GuestbookConfigBean querySingleGuestbookConfigBeanByConfigId( Long configId )
    {
        String sql = "select * from guestbook_config where configId=?";

        GuestbookConfigBean cfgBean = ( GuestbookConfigBean ) pe.querySingleRow( sql,
            new Object[] { configId }, new GuestbookConfigBeanTransform() );

        return cfgBean;
    }

    public String queryExtTableNameForGuestbookByGbId( Long gbId )
    {
        String sql = "select relateTableName from model where dataModelId=(select infoModelId from guestbook_config where configId=(select configId from guestbook_main_info where gbId=?))";

        return ( String ) pe.querySingleObject( sql, new Object[] { gbId }, String.class );
    }

    public GuestbookConfigBean querySingleGuestbookConfigBeanByConfigFlag( String flag )
    {
        String sql = "select * from guestbook_config where cfgFlag=?";

        String key = "querySingleGuestbookConfigBeanByConfigFlag:" + flag;

        GuestbookConfigBean cfgBean = ( GuestbookConfigBean ) cfgCache.getEntry( key );

        if( cfgBean == null )
        {
            cfgBean = ( GuestbookConfigBean ) pe.querySingleRow( sql, new Object[] { flag },
                new GuestbookConfigBeanTransform() );

            cfgCache.putEntry( key, cfgBean );
        }

        return cfgBean;
    }

    public void save( GuestbookConfig gbCfg )
    {
        pe.save( gbCfg );
    }

    public UpdateState save( GuestbookMainInfo gbInfo )
    {
        return pe.save( gbInfo );
    }

    public void update( GuestbookConfig gbCfg )
    {
        String sql = "update guestbook_config set cfgName=?, cfgDesc=?, mustHaveTitle=?, mustLogin=?, mustCensor=?, needVerifyCode=?, cfgFlag=?, useState=?, infoModelId=?, workflowId=? where configId=?";
        pe.update( sql, gbCfg );
    }

    // public Integer queryGuestbookMainInfoCount()
    // {
    // String sql = "select count(*) from guestbook_main_info";
    //
    // return ( Integer ) pe.querySingleObject( sql );
    // }

    public Long queryGuestbookMainInfoCount( Long cfgId )
    {
        String sql = "select count(*) from guestbook_main_info where configId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { cfgId } );
    }

    // public Integer queryGuestbookMainInfoCountIsReply( Integer isReply )
    // {
    // String sql = "select count(*) from guestbook_main_info where isReply=?";
    //
    // return ( Integer ) pe.querySingleObject( sql, new Object[] { isReply } );
    // }

    public Long queryGuestbookMainInfoCountIsReply( Integer isReply, Long cfgId )
    {
        String sql = "select count(*) from guestbook_main_info where isReply=? and configId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { isReply, cfgId } );
    }

    // public Integer queryGuestbookMainInfoCountIsOpen( Integer isOpen )
    // {
    // String sql = "select count(*) from guestbook_main_info where isOpen=?";
    //
    // return ( Integer ) pe.querySingleObject( sql, new Object[] { isOpen } );
    // }

    public Long queryGuestbookMainInfoCountIsOpen( Integer isOpen, Long cfgId )
    {
        String sql = "select count(*) from guestbook_main_info where isOpen=? and configId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { isOpen, cfgId } );
    }

    // public Integer queryGuestbookMainInfoCountIsOpenAndIsReply( Integer
    // isOpen,
    // Integer isReply )
    // {
    // String sql = "select count(*) from guestbook_main_info where isOpen=? and
    // isReply=?";
    //
    // return ( Integer ) pe.querySingleObject( sql, new Object[] { isOpen,
    // isReply } );
    // }

    public Long queryGuestbookMainInfoCountIsOpenAndIsReply( Integer isOpen, Integer isReply,
        Long cfgId )
    {
        String sql = "select count(*) from guestbook_main_info where isOpen=? and isReply=? and configId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { isOpen, isReply, cfgId } );
    }

    // public Integer queryGuestbookMainInfoCountIsCensor( Integer isCensor )
    // {
    // String sql = "select count(*) from guestbook_main_info where isCensor=?";
    //
    // return ( Integer ) pe
    // .querySingleObject( sql, new Object[] { isCensor } );
    // }

    public Long queryGuestbookMainInfoCountIsCensor( Integer isCensor, Long cfgId )
    {
        String sql = "select count(*) from guestbook_main_info where isCensor=? and configId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { isCensor, cfgId } );
    }

    // public Integer queryGuestbookMainInfoCountIsCensorAndIsReply(
    // Integer isCensor, Integer isReply )
    // {
    // String sql = "select count(*) from guestbook_main_info where isCensor=?
    // and isReply=?";
    //
    // return ( Integer ) pe.querySingleObject( sql, new Object[] { isCensor,
    // isReply } );
    // }

    public Long queryGuestbookMainInfoCountIsCensorAndIsReply( Integer isCensor, Integer isReply,
        Long cfgId )
    {
        String sql = "select count(*) from guestbook_main_info where isCensor=? and isReply=? and configId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { isCensor, isReply, cfgId } );
    }

    // public Integer queryGuestbookMainInfoCountIsOpenAndIsCensor(
    // Integer isCensor, Integer isOpen )
    // {
    // String sql = "select count(*) from guestbook_main_info where isCensor=?
    // and isOpen=?";
    //
    // return ( Integer ) pe.querySingleObject( sql, new Object[] { isCensor,
    // isOpen } );
    // }

    public Long queryGuestbookMainInfoCountIsOpenAndIsCensor( Integer isCensor, Integer isOpen,
        Long cfgId )
    {
        String sql = "select count(*) from guestbook_main_info where isCensor=? and isOpen=? and configId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { isCensor, isOpen, cfgId } );
    }

    // public Integer queryGuestbookMainInfoCountIsOpenAndIsCensorAndIsReply(
    // Integer isCensor, Integer isOpen, Integer isReply )
    // {
    // String sql = "select count(*) from guestbook_main_info where isCensor=?
    // and isOpen=? and isReply=?";
    //
    // return ( Integer ) pe.querySingleObject( sql, new Object[] { isCensor,
    // isOpen, isReply } );
    // }

    public Long queryGuestbookMainInfoCountIsOpenAndIsCensorAndIsReply( Integer isCensor,
        Integer isOpen, Integer isReply, Long cfgId )
    {
        String sql = "select count(*) from guestbook_main_info where isCensor=? and isOpen=? and isReply=? and configId=?";

        return ( Long ) pe.querySingleObject( sql,
            new Object[] { isCensor, isOpen, isReply, cfgId } );
    }

    public List queryGuestbookMainInfoMapList( Long configId, DataModelBean model,
        ModelPersistenceMySqlCodeBean sqlCodeBean, Integer pageSize, Long limit )
    {
        String noExtInfoSql = "select gmi.* from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId order by gmi.gbId desc";

        if( model == null || sqlCodeBean == null || StringUtil.isStringNull( sqlCodeBean.getSelectColumn() ))
        {
            return pe.queryResultMap( noExtInfoSql, new Object[] { configId, limit, pageSize } );
        }
        else
        {
            if( sqlCodeBean == null )
            {
                return new ArrayList();
            }

            String sql = "select gmi.*, "
                + sqlCodeBean.getSelectColumn()
                + " from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId left join "
                + model.getRelateTableName()
                + " tmp on tmp.contentId=gmi.gbId order by gmi.gbId desc";

            return pe.queryResultMap( sql, new Object[] { configId, limit, pageSize } );
        }

    }

    public List queryGuestbookMainInfoMapIsReplyList( Long configId, Integer isReply,
        DataModelBean model, ModelPersistenceMySqlCodeBean sqlCodeBean, Integer pageSize, Long limit )
    {
        String noExtInfoSql = "select gmi.* from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? and isReply=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId order by gmi.gbId desc";

        if( model == null || sqlCodeBean == null || StringUtil.isStringNull( sqlCodeBean.getSelectColumn() ))
        {
            return pe.queryResultMap( noExtInfoSql, new Object[] { configId, isReply, limit,
                pageSize } );
        }
        else
        {
            String sql = "select gmi.*, "
                + sqlCodeBean.getSelectColumn()
                + " from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? and isReply=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId left join "
                + model.getRelateTableName()
                + " tmp on tmp.contentId=gmi.gbId order by gmi.gbId desc";
            return pe.queryResultMap( sql, new Object[] { configId, isReply, limit, pageSize } );
        }

    }

    public List queryGuestbookMainInfoMapIsReplyAndIsOpenList( Long configId, Integer isReply,
        Integer isOpen, DataModelBean model, ModelPersistenceMySqlCodeBean sqlCodeBean,
        Integer pageSize, Long limit )
    {

        String noExtInfoSql = "select gmi.* from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? and isReply=? and isOpen=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId order by gmi.gbId desc";

        if( model == null || sqlCodeBean == null || StringUtil.isStringNull( sqlCodeBean.getSelectColumn() ))
        {
            return pe.queryResultMap( noExtInfoSql, new Object[] { configId, isReply, isOpen,
                limit, pageSize } );
        }
        else
        {
            String sql = "select gmi.*, "
                + sqlCodeBean.getSelectColumn()
                + " from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? and isReply=? and isOpen=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId left join "
                + model.getRelateTableName()
                + " tmp on tmp.contentId=gmi.gbId order by gmi.gbId desc";

            return pe.queryResultMap( sql, new Object[] { configId, isReply, isOpen, limit,
                pageSize } );

        }

    }

    public List queryGuestbookMainInfoMapIsReplyAndIsCensorList( Long configId, Integer isCensor,
        Integer isReply, DataModelBean model, ModelPersistenceMySqlCodeBean sqlCodeBean,
        Integer pageSize, Long limit )
    {
        String noExtInfoSql = "select gmi.* from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? and isReply=? and isCensor=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId order by gmi.gbId desc";

        if( model == null || sqlCodeBean == null || StringUtil.isStringNull( sqlCodeBean.getSelectColumn() ))
        {
            return pe.queryResultMap( noExtInfoSql, new Object[] { configId, isReply, isCensor,
                limit, pageSize } );
        }
        else
        {
            String sql = "select gmi.*, "
                + sqlCodeBean.getSelectColumn()
                + " from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? and isReply=? and isCensor=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId left join "
                + model.getRelateTableName()
                + " tmp on tmp.contentId=gmi.gbId order by gmi.gbId desc";

            return pe.queryResultMap( sql, new Object[] { configId, isReply, isCensor, limit,
                pageSize } );
        }

    }

    public List queryGuestbookMainInfoMapIsOpenAndIsCensorAndIsReplyList( Long configId,
        Integer isOpen, Integer isReply, Integer isCensor, DataModelBean model,
        ModelPersistenceMySqlCodeBean sqlCodeBean, Integer pageSize, Long limit )
    {

        String noExtInfoSql = "select gmi.* from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? and isOpen=? and isReply=? and isCensor=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId order by gmi.gbId desc";

        if( model == null || sqlCodeBean == null || StringUtil.isStringNull( sqlCodeBean.getSelectColumn() ))
        {
            return pe.queryResultMap( noExtInfoSql, new Object[] { configId, isOpen, isReply,
                isCensor, limit, pageSize } );
        }
        else
        {
            String sql = "select gmi.*, "
                + sqlCodeBean.getSelectColumn()
                + " from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? and isOpen=? and isReply=? and isCensor=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId left join "
                + model.getRelateTableName()
                + " tmp on tmp.contentId=gmi.gbId order by gmi.gbId desc";

            return pe.queryResultMap( sql, new Object[] { configId, isOpen, isReply, isCensor,
                limit, pageSize } );
        }

    }

    public List queryGuestbookMainInfoMapIsOpenList( Long configId, Integer isOpen,
        DataModelBean model, ModelPersistenceMySqlCodeBean sqlCodeBean, Integer pageSize, Long limit )
    {

        String noExtInfoSql = "select gmi.* from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? and isOpen=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId order by gmi.gbId desc";

        if( model == null || sqlCodeBean == null || StringUtil.isStringNull( sqlCodeBean.getSelectColumn() ))
        {
            return pe.queryResultMap( noExtInfoSql, new Object[] { configId, isOpen, limit,
                pageSize } );
        }
        else
        {
            String sql = "select gmi.*, "
                + sqlCodeBean.getSelectColumn()
                + " from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? and isOpen=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId left join "
                + model.getRelateTableName()
                + " tmp on tmp.contentId=gmi.gbId order by gmi.gbId desc";

            return pe.queryResultMap( sql, new Object[] { configId, isOpen, limit, pageSize } );
        }

    }

    public List queryGuestbookMainInfoMapIsCensorList( Long configId, Integer isCensor,
        DataModelBean model, ModelPersistenceMySqlCodeBean sqlCodeBean, Integer pageSize, Long limit )
    {

        String noExtInfoSql = "select gmi.* from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? and isCensor=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId order by gmi.gbId desc";

        if( model == null || sqlCodeBean == null || StringUtil.isStringNull( sqlCodeBean.getSelectColumn() ))
        {
            return pe.queryResultMap( noExtInfoSql, new Object[] { configId, isCensor, limit,
                pageSize } );
        }
        else
        {
            String sql = "select gmi.*, "
                + sqlCodeBean.getSelectColumn()
                + " from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? and isCensor=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId left join "
                + model.getRelateTableName()
                + " tmp on tmp.contentId=gmi.gbId order by gmi.gbId desc";

            return pe.queryResultMap( sql, new Object[] { configId, isCensor, limit, pageSize } );
        }
    }

    public List queryGuestbookMainInfoMapIsOpenAndIsCensorList( Long configId, Integer isOpen,
        Integer isCensor, DataModelBean model, ModelPersistenceMySqlCodeBean sqlCodeBean,
        Integer pageSize, Long limit )
    {
        String noExtInfoSql = "select gmi.* from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? and isOpen=? and isCensor=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId order by gmi.gbId desc";

        if( model == null || sqlCodeBean == null || StringUtil.isStringNull( sqlCodeBean.getSelectColumn() ))
        {
            return pe.queryResultMap( noExtInfoSql, new Object[] { configId, isOpen, isCensor,
                limit, pageSize } );
        }
        else
        {
            String sql = "select gmi.*, "
                + sqlCodeBean.getSelectColumn()
                + " from guestbook_main_info gmi inner join (select gbId from guestbook_main_info where configId=? and isOpen=? and isCensor=? order by gbId desc limit ?,?) ids on gmi.gbId=ids.gbId left join "
                + model.getRelateTableName()
                + " tmp on tmp.contentId=gmi.gbId order by gmi.gbId desc";

            return pe.queryResultMap( sql, new Object[] { configId, isOpen, isCensor, limit,
                pageSize } );
        }

    }

    public Map querySingleGuestbookInfoMapByGbId( Long gbId, DataModelBean model,
        ModelPersistenceMySqlCodeBean sqlCodeBean )
    {
        String noExtInfoSql = "select gmi.* from guestbook_main_info gmi where gmi.gbId=?";

        if( model == null || sqlCodeBean == null || StringUtil.isStringNull( sqlCodeBean.getSelectColumn() ))
        {
            return pe.querySingleResultMap( noExtInfoSql, new Object[] { gbId } );
        }
        else
        {
            String sql = "select gmi.*, " + sqlCodeBean.getSelectColumn()
                + " from guestbook_main_info gmi left join " + model.getRelateTableName()
                + " tmp on tmp.contentId=gmi.gbId where gmi.gbId=?";

            return pe.querySingleResultMap( sql, new Object[] { gbId } );
        }
    }

    public void updateGuestbookReplyInfo( Long gbId, String replyText, String replyMan,
        Date replyDate, Integer isOpen )
    {
        String sql = "update guestbook_main_info set replyText=?, replyMan=?, replyDate=?, isOpen=? where gbId=?";
        pe.update( sql, new Object[] { replyText, replyMan, replyDate, isOpen, gbId } );
    }

    public void updateGuestbookCensorStatus( Long gbId, Integer flag )
    {
        String sql = "update guestbook_main_info set isCensor=? where gbId=?";
        pe.update( sql, new Object[] { flag, gbId } );
    }
    
    public void updateGuestbookReplyStatus( Long gbId, Integer flag )
    {
        String sql = "update guestbook_main_info set isReply=? where gbId=?";
        pe.update( sql, new Object[] { flag, gbId } );
    }

    public void updateGuestbookOpenStatus( Long gbId, Integer flag )
    {
        String sql = "update guestbook_main_info set isOpen=? where gbId=?";
        pe.update( sql, new Object[] { flag, gbId } );
    }

    public GuestbookMainInfoBean querySingleGuestbookMainInfoByGbid( Long gbId )
    {
        String sql = "select * from guestbook_main_info where gbId=?";

        return ( GuestbookMainInfoBean ) pe.querySingleRow( sql, new Object[] { gbId },
            new GuestbookMainInfoBeanTransform() );
    }

    public Long queryGuestbookConfigIdByGbid( Long gbId )
    {
        String sql = "select configId from guestbook_main_info where gbId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { gbId }, Long.class );
    }

    public void deleteGuestbookInfoById( Long gbId )
    {
        String sql = "delete from guestbook_main_info where gbId=?";
        pe.update( sql, new Object[] { gbId } );
    }

    public void deleteGuestbookInfoExtInfoByGbId( String extTableName, Long gbId )
    {
        String sql = "delete from " + extTableName + " where contentId=?";
        pe.update( sql, new Object[] { gbId } );
    }

    public void deleteGuestbookConfigById( Long configId )
    {
        String sql = "delete from guestbook_config where configId=?";
        pe.update( sql, new Object[] { configId } );

    }

    public List queryAllGuestbookIdList( Long configId )
    {
        String sql = "select gbId from guestbook_main_info where configId=?";
        return pe.querySingleCloumn( sql, new Object[] { configId }, Long.class );
    }
    
    public List queryAllInWorkflowGuestbookContent( String sqlOrCond, Long loginUserId,
        Long loginUserOrgId, Long start, Integer size )
    {
        String sql = "select * from (select ci.*, wsi.stepNodeName, wo.* from guestbook_main_info ci, workflow_operation wo, workflow_step_info wsi, (select wo.contentId from workflow_operation wo,workflow_actor wa where wa.censorStep=wo.currentStep and wa.flowId=wo.flowId and wa.type=1 and ("
            + sqlOrCond
            + ")) ids where ids.contentId=ci.gbId and ids.contentId=wo.contentId and wo.flowId=wsi.flowId and wo.currentStep=wsi.stepId"
            + " union "
            + "select ci.*, wsi.stepNodeName, wo.* from guestbook_main_info ci, workflow_operation wo, workflow_step_info wsi, (select wo.contentId from workflow_operation wo,workflow_actor wa where wa.censorStep=wo.currentStep and wa.flowId=wo.flowId and wa.type=2 and wa.auditManId=?) ids where ids.contentId=ci.gbId and ids.contentId=wo.contentId and wo.flowId=wsi.flowId and wo.currentStep=wsi.stepId"
            + " union "
            + "select ci.*, wsi.stepNodeName, wo.* from guestbook_main_info ci, workflow_operation wo, workflow_step_info wsi, (select wo.contentId from workflow_operation wo,workflow_actor wa where wa.censorStep=wo.currentStep and wa.flowId=wo.flowId and wa.type=3 and wa.auditManId=?) ids where ids.contentId=ci.gbId and ids.contentId=wo.contentId and wo.flowId=wsi.flowId and wo.currentStep=wsi.stepId)"
            +

            " tmp order by opId desc limit ?,?";

        return pe.query( sql, new Object[] { loginUserId, loginUserOrgId, start, size },
            new WorkflowOperationAndExpandInfoBeanTransform() );
    }

    public static void clearConfigBeanCache()
    {
        cfgCache.clearAllEntry();

    }

}
