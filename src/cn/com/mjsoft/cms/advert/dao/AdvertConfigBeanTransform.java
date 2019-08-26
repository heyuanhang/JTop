package cn.com.mjsoft.cms.advert.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.advert.bean.AdvertConfigBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class AdvertConfigBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        AdvertConfigBean bean = new AdvertConfigBean();

        bean.setConfigId( Long.valueOf( rs.getLong( "configId" ) ) );
        bean.setConfigName( rs.getString( "configName" ) );
        bean.setConfigDesc( rs.getString( "configDesc" ) );
        bean.setAdvertCode( rs.getString( "advertCode" ) );
        bean.setPosModelId( Long.valueOf( rs.getLong( "posModelId" ) ) );
        bean.setContentModelId( Long.valueOf( rs.getLong( "contentModelId" ) ) );
        bean.setUserState( Integer.valueOf( rs.getInt( "userState" ) ) );
        bean.setCreator( rs.getString( "creator" ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );
        
        return bean;
    }
}
