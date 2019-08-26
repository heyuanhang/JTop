package cn.com.mjsoft.cms.advert.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.advert.bean.AdvertConfigParamBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class AdvertConfigParamBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        AdvertConfigParamBean bean = new AdvertConfigParamBean();

        bean.setParamId( Long.valueOf( rs.getLong( "paramId" ) ) );
        bean.setConfigId( Long.valueOf( rs.getLong( "configId" ) ) );
        bean.setParamFlag( rs.getString( "paramFlag" ) );
        bean.setParamType( Integer.valueOf( rs.getInt( "paramType" ) ) );
        bean.setParamName( rs.getString( "paramName" ) );
        bean.setHtmlType( Integer.valueOf( rs.getInt( "htmlType" ) ) );
        bean.setChoiceValue( rs.getString( "choiceValue" ) );
        bean.setDefaultValue( rs.getString( "defaultValue" ) );
        bean.setMustFill( Integer.valueOf( rs.getInt( "mustFill" ) ) );
        bean.setAllowFileType( rs.getString( "allowFileType" ) );
        bean.setFileSize( Integer.valueOf( rs.getInt( "fileSize" ) ) );
     
        return bean;

    }

}
