package cn.com.mjsoft.cms.publish.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.publish.bean.PublishPageAssistantBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class PublishPageAssistantTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        PublishPageAssistantBean bean = new PublishPageAssistantBean();

        bean.setClassId( Long.valueOf( rs.getLong( "classId" ) ) );
        bean.setClassTemplateUrl( rs.getString( "classTemplateUrl" ) );
        bean.setQueryKey( rs.getString( "queryKey" ) );
        bean.setLastPageStaticUrl( rs.getString( "lastPageStaticUrl" ) );
        bean.setLastPn( Integer.valueOf( rs.getInt( "lastPn" ) ) );

        return bean;
    }

}
