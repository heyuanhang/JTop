package cn.com.mjsoft.cms.block.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.block.bean.BlockInfoBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class BlockInfoBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        BlockInfoBean bean = new BlockInfoBean();

        bean.setBlockId( Long.valueOf( rs.getLong( "blockId" ) ) );
        bean.setBlockName( rs.getString( "blockName" ) );
        bean.setFlag( rs.getString( "flag" ) );
        bean.setTemplateUrl( rs.getString( "templateUrl" ) );
        bean.setType( Integer.valueOf( rs.getInt( "type" ) ) );
        bean.setCreator( rs.getString( "creator" ) );
        bean.setBlockDesc( rs.getString( "blockDesc" ) );
        bean.setStaticUrl( rs.getString( "staticUrl" ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );
        bean.setJobId( Long.valueOf( rs.getLong( "jobId" ) ) );
        bean.setParentId( Long.valueOf( rs.getLong( "parentId" ) ) );
        bean.setPeriod( Integer.valueOf( rs.getInt( "period" ) ) );
        bean.setPeriodType( Integer.valueOf( rs.getInt( "periodType" ) ) );
        bean.setLastPubTime( rs.getTimestamp( "lastPubTime" ) );

        return bean;
    }

}
