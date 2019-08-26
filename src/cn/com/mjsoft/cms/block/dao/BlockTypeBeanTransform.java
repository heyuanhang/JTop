package cn.com.mjsoft.cms.block.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.block.bean.BlockTypeBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class BlockTypeBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        BlockTypeBean bean = new BlockTypeBean();

        bean.setBlockTypeId( Long.valueOf( rs.getLong( "blockTypeId" ) ) );
        bean.setBlockTypeName( rs.getString( "blockTypeName" ) );
        bean.setSiteFlag( rs.getString( "siteFlag" ) );
        bean.setTemplateUrl( rs.getString( "templateUrl" ) );

        return bean;
    }
}
