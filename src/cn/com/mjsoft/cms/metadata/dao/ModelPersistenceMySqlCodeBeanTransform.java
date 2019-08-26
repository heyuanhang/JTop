package cn.com.mjsoft.cms.metadata.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class ModelPersistenceMySqlCodeBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {

        ModelPersistenceMySqlCodeBean bean = new ModelPersistenceMySqlCodeBean();

        bean.setDataModelId( Long.valueOf( rs.getLong( "dataModelId" ) ) );
        bean.setDeleteSql( rs.getString( "deleteSql" ) );
        bean.setInsertSql( rs.getString( "insertSql" ) );
        bean.setSelectSql( rs.getString( "selectSql" ) );
        bean.setUpdateSql( rs.getString( "updateSql" ) );
        bean.setSelectColumn( rs.getString( "selectColumn" ) );
        bean.setListSelectColumn( rs.getString( "listSelectColumn" ) );
        return bean;
    }
}
