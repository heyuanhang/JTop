package cn.com.mjsoft.cms.channel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.channel.bean.TagWordBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class TagWordBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        TagWordBean bean = new TagWordBean();

        bean.setTagId( Long.valueOf( rs.getLong( "tagId" ) ) );
        bean.setTagName( rs.getString( "tagName" ) );
        bean.setTagFlag( rs.getString( "tagFlag" ) );
        bean.setTagTypeId( Long.valueOf( rs.getLong( "tagTypeId" ) ) );
        bean.setFirstChar( rs.getString( "firstChar" ) );
        bean.setClickCount( Long.valueOf( rs.getLong( "clickCount" ) ) );
        bean.setContentCount( Long.valueOf( rs.getLong( "contentCount" ) ) );
        bean.setCreator( rs.getString( "creator" ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );

        return bean;

    }

}
