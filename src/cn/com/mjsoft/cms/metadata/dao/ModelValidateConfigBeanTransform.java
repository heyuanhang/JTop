package cn.com.mjsoft.cms.metadata.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.metadata.bean.ModelValidateConfigBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class ModelValidateConfigBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        ModelValidateConfigBean bean = new ModelValidateConfigBean();

        bean.setErrorMessage( rs.getString( "errorMessage" ) );
        bean.setRegulation( rs.getString( "regulation" ) );
        bean.setValidateConfigId( Long
            .valueOf( rs.getLong( "validateConfigId" ) ) );
        bean.setValidateName( rs.getString( "validateName" ) );

        return bean;
    }
}
