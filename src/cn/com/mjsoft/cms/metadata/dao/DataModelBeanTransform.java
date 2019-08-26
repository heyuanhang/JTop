package cn.com.mjsoft.cms.metadata.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class DataModelBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        DataModelBean bean = new DataModelBean();

        bean.setDataModelId( Long.valueOf( rs.getLong( "dataModelId" ) ) );
        bean.setModelName( rs.getString( "modelName" ) );
        bean.setModelSign( rs.getString( "modelSign" ) );
        bean.setModelResType( Integer.valueOf( rs.getInt( "modelResType" ) ) );
        bean.setModelType( Integer.valueOf( rs.getInt( "modelType" ) ) );
        bean.setRelateTableName( rs.getString( "relateTableName" ) );
        bean
            .setMainEditorFieldSign( rs.getString( "mainEditorFieldSign" ) == null
                ? "" : rs.getString( "mainEditorFieldSign" ) );
        bean.setValidateBehavior( rs.getString( "validateBehavior" ) );
        bean.setBeforeBehavior( rs.getString( "beforeBehavior" ) );
        bean.setAfterBehavior( rs.getString( "afterBehavior" ) );
        bean.setRemark( rs.getString( "remark" ) );
        bean.setUseState( Integer.valueOf( rs.getInt( "useState" ) ) );
        bean.setPrivateMode( Integer.valueOf( rs.getInt( "privateMode" ) ) );
        bean.setIco( rs.getString( "ico" ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );
        
        bean.setIsManageEdit( Integer.valueOf( rs.getInt( "isManageEdit" ) ) );
        bean.setIsMemberEdit( Integer.valueOf( rs.getInt( "isMemberEdit" ) ) );
        bean.setMustCensor( Integer.valueOf( rs.getInt( "mustCensor" ) ) );
        bean.setMustToken( Integer.valueOf( rs.getInt( "mustToken" ) ) );
        
        bean.setSimpleMode( rs.getInt( "simpleMode" ) );
        bean.setTitleMode( Integer.valueOf( rs.getInt( "titleMode" ) ) );
        bean.setKwMode( Integer.valueOf( rs.getInt( "kwMode" ) ) );
        bean.setTitleCol( rs.getString( "titleCol" ) );    
        return bean;
    }
}
