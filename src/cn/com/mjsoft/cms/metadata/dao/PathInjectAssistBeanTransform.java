package cn.com.mjsoft.cms.metadata.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.metadata.bean.PathInjectAssistBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class PathInjectAssistBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        PathInjectAssistBean bean = new PathInjectAssistBean();

        bean.setModelId( Long.valueOf( rs.getLong( "modelId" ) ) );
        bean.setFieldName( rs.getString( "fieldName" ) );
        bean.setResType( Integer.valueOf( rs.getInt( "resType" ) ) );
        bean.setMetaDataId( Long.valueOf( rs.getLong( "metaDataId" ) ) );

        return bean;

    }

}
