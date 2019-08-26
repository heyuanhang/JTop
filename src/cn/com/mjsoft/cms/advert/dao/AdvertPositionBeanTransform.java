package cn.com.mjsoft.cms.advert.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.advert.bean.AdvertPositionBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class AdvertPositionBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        AdvertPositionBean bean = new AdvertPositionBean();

        bean.setConfigId( Long.valueOf( rs.getLong( "configId" ) ) );
        bean.setConfigName( rs.getString( "configName" ) );
        bean.setCreator( rs.getString( "creator" ) );
        bean.setPosDesc( rs.getString( "posDesc" ) );
        bean.setPosFlag( rs.getString( "posFlag" ) );
        bean.setPosId( Long.valueOf( rs.getLong( "posId" ) ) );
        bean.setPosName( rs.getString( "posName" ) );
        bean.setShowMode( Integer.valueOf( rs.getInt( "showMode" ) ) );
        bean.setUseState( Integer.valueOf( rs.getInt( "useState" ) ) );
        bean.setTarget( rs.getString( "target" ) );
        bean.setWidth( Integer.valueOf( rs.getInt( "width" ) ) );
        bean.setHeight( Integer.valueOf( rs.getInt( "height" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );

        return bean;
    }

}
