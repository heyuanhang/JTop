package cn.com.mjsoft.cms.resources.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.resources.bean.SiteResourceTraceBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SiteResourceTraceBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SiteResourceTraceBean bean = new SiteResourceTraceBean();

        bean.setResId( Long.valueOf( rs.getLong( "resId" ) ) );
        bean.setUploadDate( rs.getTimestamp( "uploadDate" ) );
        bean.setIsUse( Integer.valueOf( rs.getInt( "isUse" ) ) );

        return bean;
    }
}
