package cn.com.mjsoft.cms.resources.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.resources.bean.SiteResourceBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SiteResourceBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SiteResourceBean bean = new SiteResourceBean();

        bean.setResId( Long.valueOf( rs.getLong( "resId" ) ) );
        bean.setClassId( Long.valueOf( rs.getLong( "classId" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );
        bean.setResType( Integer.valueOf( rs.getInt( "resType" ) ) );
        bean.setFileType( rs.getString( "fileType" ) );
        bean.setResName( rs.getString( "resName" ) );
        bean.setResSource( rs.getString( "resSource" ) );
        bean.setResSize( Long.valueOf( rs.getLong( "resSize" ) ) );
        bean.setWidth( Integer.valueOf( rs.getInt( "width" ) ) );
        bean.setHeight( Integer.valueOf( rs.getInt( "height" ) ) );
        bean.setDuration( Integer.valueOf( rs.getInt( "duration" ) ) );
        bean.setResolution( rs.getString( "resolution" ) );
        bean.setHaveMark( Integer.valueOf( rs.getInt( "haveMark" ) ) );
        bean.setCover( rs.getString( "cover" ) );

        return bean;
    }
}
