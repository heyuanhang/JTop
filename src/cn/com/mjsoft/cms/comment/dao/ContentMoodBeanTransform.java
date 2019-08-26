package cn.com.mjsoft.cms.comment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.comment.bean.ContentMoodBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class ContentMoodBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        ContentMoodBean bean = new ContentMoodBean();

        bean.setAgainstCount( Long.valueOf( rs.getLong( "againstCount" ) ) );
        bean.setSupportCount( Long.valueOf( rs.getLong( "supportCount" ) ) );
        bean.setContentId( Long.valueOf( rs.getLong( "contentId" ) ) );
        bean.setMoodT10count( Long.valueOf( rs.getLong( "moodT10count" ) ) );
        bean.setMoodT9count( Long.valueOf( rs.getLong( "moodT9count" ) ) );
        bean.setMoodT8count( Long.valueOf( rs.getLong( "moodT8count" ) ) );
        bean.setMoodT6count( Long.valueOf( rs.getLong( "moodT6count" ) ) );
        bean.setMoodT5count( Long.valueOf( rs.getLong( "moodT5count" ) ) );
        bean.setMoodT4count( Long.valueOf( rs.getLong( "moodT4count" ) ) );
        bean.setMoodT3count( Long.valueOf( rs.getLong( "moodT3count" ) ) );
        bean.setMoodT2count( Long.valueOf( rs.getLong( "moodT2count" ) ) );
        bean.setMoodT1count( Long.valueOf( rs.getLong( "moodT1count" ) ) );

        return bean;
    }

}
