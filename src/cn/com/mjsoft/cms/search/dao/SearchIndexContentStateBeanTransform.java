package cn.com.mjsoft.cms.search.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.search.bean.SearchIndexContentStateBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SearchIndexContentStateBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SearchIndexContentStateBean bean = new SearchIndexContentStateBean();

        bean.setClassId( Long.valueOf( rs.getLong( "classId" ) ) );
        bean.setClusterId( Long.valueOf( rs.getLong( "clusterId" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );
        bean.setContentId( Long.valueOf( rs.getLong( "contentId" ) ) );
        bean.setCensor( Integer.valueOf( rs.getInt( "censor" ) ) );
        bean.setBoost( rs.getFloat( "boost" ) );
        bean.setIndexDate( rs.getTimestamp( "indexDate" ) );
        bean.setEventFlag( Integer.valueOf( rs.getInt( "eventFlag" ) ) );
        bean.setIndexStateId( Long.valueOf( rs.getLong( "indexStateId" ) ) );
        bean.setModelId( Long.valueOf( rs.getLong( "modelId" ) ) );

        return bean;
    }

}
