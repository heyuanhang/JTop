package cn.com.mjsoft.cms.block.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.block.bean.BlockInfoBean;
import cn.com.mjsoft.cms.block.bean.BlockTypeBean;
import cn.com.mjsoft.cms.block.dao.vo.BlockInfo;
import cn.com.mjsoft.cms.block.dao.vo.BlockType;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;

public class BlockDao
{
    private static Map cacheManager = new HashMap();

    static
    {
        cacheManager.put( "querySingleBlockBean", new ClusterCacheAdapter( 300,
            "blockDao.querySingleBlockBean" ) );
    }

    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public BlockDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public void saveBlockType( String btName, String url, String siteFlag, String creator )
    {
        String sql = "insert into block_type (blockTypeName,templateUrl, siteFlag, creator) values (?,?,?,?)";

        pe.update( sql, new Object[] { btName, url, siteFlag, creator } );

    }

    public void updateBlockType( String btName, String url, Long btId )
    {
        String sql = "update block_type set blockTypeName=?, templateUrl=? where blockTypeId=?";

        pe.update( sql, new Object[] { btName, url, btId } );

    }

    public List queryAllBlockInfoBeanBySiteId( Long siteId )
    {
        String sql = "select * from block_info where siteId=? order by parentId, blockId";

        return pe.query( sql, new Object[] { siteId }, new BlockInfoBeanTransform() );
    }

    public List queryAllBlockInfoBean()
    {
        String sql = "select * from block_info order by parentId, blockId";

        return pe.query( sql, new BlockInfoBeanTransform() );
    }

    public BlockInfoBean querySingleBlockBean( String flag )
    {
        String sql = "select * from block_info where flag=?";

        Cache cache = ( Cache ) cacheManager.get( "querySingleBlockBean" );

        BlockInfoBean bean = ( BlockInfoBean ) cache.getEntry( flag );

        if( bean == null )
        {
            bean = ( BlockInfoBean ) pe.querySingleRow( sql, new Object[] { flag },
                new BlockInfoBeanTransform() );

            if( bean != null )
            {
                cache.putEntry( flag, bean );
            }
        }

        return bean;
    }

    public BlockInfoBean querySingleBlockBean( Long blockId )
    {
        String sql = "select * from block_info where blockId=?";

        return ( BlockInfoBean ) pe.querySingleRow( sql, new Object[] { blockId },
            new BlockInfoBeanTransform() );
    }

    public UpdateState save( BlockInfo block )
    {
        return pe.save( block );
    }

    public void deleteBlockInfoById( Long blockId )
    {
        String sql = "delete from block_info where blockId=?";
        pe.update( sql, new Object[] { blockId } );
    }

    public void updateBlockPublishDate( Long blockId, Timestamp pubTime )
    {
        String sql = "update block_pub_dt_trace set lastPubDT=? where selfBlockId=?";

        pe.update( sql, new Object[] { pubTime, blockId } );
    }

    public void saveBlockPublishDate( Long blockId, Timestamp pubTime )
    {
        String sql = "insert into block_pub_dt_trace (selfBlockId, lastPubDT) values (?, ?)";

        pe.update( sql, new Object[] { blockId, pubTime } );
    }

    public Map queryBlockPublishDate( Long blockId )
    {
        String sql = "select * from block_pub_dt_trace where selfBlockId=?";

        return pe.querySingleResultMap( sql, new Object[] { blockId } );
    }

    public void updateBlockPublishStaticUrl( Long blockId, String staticUrl )
    {
        String sql = "update block_info set staticUrl=? where blockId=?";

        pe.update( sql, new Object[] { staticUrl, blockId } );
    }

    public List queryAllBlockTypeBeanList( String siteFlag )
    {
        String sql = "select * from block_type where siteFlag=? order by blockTypeId desc";

        return pe.query( sql, new Object[] { siteFlag }, new BlockTypeBeanTransform() );
    }

    public List queryAllBlockTypeIdList( String siteFlag )
    {
        String sql = "select blockTypeId from block_type where siteFlag=?";

        return pe.querySingleCloumn( sql, new Object[] { siteFlag }, Long.class );
    }

    public void saveBlockType( BlockType vo )
    {
        pe.save( vo );
    }

    public void deleteBlockTypeById( Long typeId )
    {
        String sql = "delete from block_type where blockTypeId=?";

        pe.update( sql, new Object[] { typeId } );
    }

    public BlockTypeBean querySingleBlockTypeBeanById( Long typeId )
    {
        String sql = "select * from block_type where blockTypeId=?";

        return ( BlockTypeBean ) pe.querySingleRow( sql, new Object[] { typeId },
            new BlockTypeBeanTransform() );
    }

    public void updateBlockJobId( Long blockId, Long jobId )
    {
        String sql = "update block_info set jobId=? where blockId=?";

        pe.update( sql, new Object[] { jobId, blockId } );
    }

    public void updateBlockInfo( BlockInfo block )
    {
        String sql = "update block_info set parentId=?, blockName=?, type=?, templateUrl=?, creator=?, blockDesc=?, periodType=?, period=? where blockId=?";

        pe.update( sql, new Object[] { block.getParentId(), block.getBlockName(), block.getType(),
            block.getTemplateUrl(), block.getCreator(), block.getBlockDesc(),
            block.getPeriodType(), block.getPeriod(), block.getBlockId() } );
    }

    public List queryBlockInfoBeanByTypeId( Long typeId )
    {
        String sql = "select * from block_info where parentId=?";

        return pe.query( sql, new Object[] { typeId }, new BlockInfoBeanTransform() );

    }

    public static void clearCache()
    {
        Cache cache = ( Cache ) cacheManager.get( "querySingleBlockBean" );
        cache.clearAllEntry();

       
    }

    

}
