package cn.com.mjsoft.cms.templet.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.templet.bean.TempletFileBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class TempletFileBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        TempletFileBean bean = new TempletFileBean();
        bean.setTempletFullPath( rs.getString( "templetFullPath" ) );
        bean.setTempletDisplayName( rs.getString( "templetDisplayName" ) );
        bean.setSiteName( rs.getString( "siteName" ) );
        // bean.set( rs.getString( "templetDisplayName" ) );

        return bean;
    }

}
