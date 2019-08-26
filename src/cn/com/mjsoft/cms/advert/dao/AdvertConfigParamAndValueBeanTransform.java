package cn.com.mjsoft.cms.advert.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.advert.bean.AdvertConfigParamBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class AdvertConfigParamAndValueBeanTransform implements RowTransform
{
    private static final AdvertConfigParamBeanTransform beanTransform = new AdvertConfigParamBeanTransform();

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        AdvertConfigParamBean bean = ( AdvertConfigParamBean ) beanTransform
            .convertRow( rs, rowNum );

        bean.setValue( rs.getString( "paramValue" ) );

        return bean;
    }
}
