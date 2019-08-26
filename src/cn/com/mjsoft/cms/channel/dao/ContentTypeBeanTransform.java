package cn.com.mjsoft.cms.channel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.channel.bean.ContentTypeBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class ContentTypeBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        ContentTypeBean bean = new ContentTypeBean();

        bean.setGroupId( Integer.valueOf( rs.getInt( "groupId" ) ) );
        bean.setTypeFlag( rs.getString( "typeFlag" ) );
        bean.setTypeId( Long.valueOf( rs.getLong( "typeId" ) ) );
        bean.setTypeName( rs.getString( "typeName" ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );

        return bean;
    }

}
