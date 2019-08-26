package cn.com.mjsoft.cms.block.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.block.bean.BlockInfoBean;
import cn.com.mjsoft.cms.block.bean.BlockTypeBean;
import cn.com.mjsoft.cms.block.dao.BlockDao;
import cn.com.mjsoft.cms.block.dao.vo.BlockInfo;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.schedule.dao.ScheduleDao;
import cn.com.mjsoft.cms.schedule.service.ScheduleService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class BlockService
{
    private static Logger log = Logger.getLogger( BlockService.class );

    private static BlockService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine(
        new MySqlDataSource() );

    private ScheduleService scheduleService = ScheduleService.getInstance();

    private BlockDao blockDao;

    private ScheduleDao scheduleDao;

    private BlockService()
    {
        blockDao = new BlockDao( mysqlEngine );

        scheduleDao = new ScheduleDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new BlockService();
        }
    }

    public static BlockService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public List retrieveAllBlockBeanListByCurrSite()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
            .getSecuritySession().getCurrentLoginSiteInfo();

        return blockDao.queryAllBlockInfoBeanBySiteId( site.getSiteId() );
    }

    public List retrieveBlockBeanListByTypeId( Long typeId )
    {
        return blockDao.queryBlockInfoBeanByTypeId( typeId );
    }

    public BlockInfoBean retrieveSingleBlockBean( Long blockId )
    {
        return blockDao.querySingleBlockBean( blockId );
    }

    public BlockInfoBean retrieveSingleBlockBean( String flag )
    {
        return blockDao.querySingleBlockBean( flag );
    }

    public List retrieveBlockBeanBySomeFlag( List flagList )
    {
        String blockFlag = null;

        BlockInfoBean blockBean = null;

        List resList = new ArrayList();

        for ( int i = 0; i < flagList.size(); i++ )
        {
            blockFlag = ( String ) flagList.get( i );

            blockBean = blockDao.querySingleBlockBean( blockFlag );

            if( blockBean != null )
            {
                resList.add( blockBean );
            }
        }

        return resList;
    }

    public void addNewBlockType( String btName, String url, String siteFlag,
        String creator )
    {
        blockDao.saveBlockType( btName, url, siteFlag, creator );
    }

    public void editBlockType( String btName, String url, Long btId )
    {
        blockDao.updateBlockType( btName, url, btId );
    }

    public void addNewBlock( SiteGroupBean site, BlockInfo block )
    {
        try
        {
            mysqlEngine.beginTransaction();

            UpdateState updateState = blockDao.save( block );

            if( updateState.haveKey() )
            {
                block.setBlockId( Long.valueOf( updateState.getKey() ) );

                if( block.getPeriod().intValue() > 0 )
                {
                    scheduleService.addNewPublishBlockJob( site, block );
                }
            }
            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            BlockDao.clearCache();
        }
    }

    public void editBlock( SiteGroupBean site, BlockInfo block )
    {
        try
        {
            mysqlEngine.beginTransaction();

            blockDao.updateBlockInfo( block );

            // 移除并删除job
            scheduleService.stopPublishBlockScheduleJob( block.getBlockId() );

            if( block.getJobId() != null && block.getJobId().longValue() > 0 )
            {
                if( block.getPeriod().intValue() == 0 )
                {
                    scheduleDao.deleteScheduleJobDetailByJobId( block
                        .getJobId() );
                    blockDao.updateBlockJobId( block.getBlockId(), null );
                }
                else
                {
                    // 根据改变增加新的job
                    scheduleService.updatePublishBlockJob( site, block );
                }
            }
            else
            {
                if( block.getPeriod().intValue() != 0 )
                {
                    scheduleService.addNewPublishBlockJob( site, block );
                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            BlockDao.clearCache();
        }
    }

    public void deleteBlockInfoById( List idList )
    {
        try
        {
            mysqlEngine.beginTransaction();

            Long blockId = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                blockId = Long.valueOf( StringUtil.getLongValue(
                    ( String ) idList.get( i ), -1 ) );

                if( blockId.longValue() < 1 )
                {
                    continue;
                }

                BlockInfoBean blockBean = blockDao
                    .querySingleBlockBean( blockId );

                blockDao.deleteBlockInfoById( blockId );

                scheduleDao.deleteScheduleJobDetailByJobId( blockBean
                    .getJobId() );

                scheduleService.stopPublishBlockScheduleJob( blockBean
                    .getBlockId() );

            }

            mysqlEngine.commit();

        }
        finally
        {
            mysqlEngine.endTransaction();

            BlockDao.clearCache();
        }

    }

    public void deleteBlockTypeById( List idList )
    {
        try
        {
            mysqlEngine.beginTransaction();

            Long typeId = null;

            List blockBeanList = null;

            BlockInfoBean blockBean = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                if( idList.get( i ) instanceof String )
                {
                    typeId = Long.valueOf( StringUtil.getLongValue(
                        ( String ) idList.get( i ), -1 ) );
                }
                else
                {
                    typeId = ( Long ) idList.get( i );
                }

                if( typeId.longValue() < 1 )
                {
                    continue;
                }

                blockBeanList = blockDao.queryBlockInfoBeanByTypeId( typeId );

                for ( int j = 0; j < blockBeanList.size(); j++ )
                {
                    blockBean = ( BlockInfoBean ) blockBeanList.get( j );

                    blockDao.deleteBlockInfoById( blockBean.getBlockId() );

                    scheduleDao.deleteScheduleJobDetailByJobId( blockBean
                        .getJobId() );

                    scheduleService.stopPublishBlockScheduleJob( blockBean
                        .getBlockId() );
                }

                blockDao.deleteBlockTypeById( typeId );

            }

            mysqlEngine.commit();

        }
        finally
        {
            mysqlEngine.endTransaction();

            BlockDao.clearCache();
        }

    }

    public void updateBlockPublishDateTrace( Long blockId, Timestamp pubTime )
    {
        // 注意:此方法不更新缓存,在改变block配置时更新
        try
        {
            mysqlEngine.beginTransaction();

            Map testExist = blockDao.queryBlockPublishDate( blockId );

            if( testExist.isEmpty() )
            {
                blockDao.saveBlockPublishDate( blockId, pubTime );
            }
            else
            {
                blockDao.updateBlockPublishDate( blockId, pubTime );
            }

            mysqlEngine.commit();

        }
        finally
        {
            mysqlEngine.endTransaction();

        }
    }

    public void updateBlockPublishStaticUrl( Long blockId, String staticUrl )
    {
        blockDao.updateBlockPublishStaticUrl( blockId, staticUrl );

        BlockDao.clearCache();
    }

    public List retrieveAllBlockTypeBeanListByCurrSite()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
            .getSecuritySession().getCurrentLoginSiteInfo();

        return blockDao.queryAllBlockTypeBeanList( site.getSiteFlag() );
    }

    public BlockTypeBean retrieveSingleBlockTypeBean( Long btId )
    {
        return blockDao.querySingleBlockTypeBeanById( btId );
    }

    public Object getBlockPubDateInfoTag( String blockId )
    {
        Long bid = Long.valueOf( StringUtil.getLongValue( blockId, -1 ) );

        return blockDao.queryBlockPublishDate( bid );
    }

    

}
