package cn.com.mjsoft.cms.metadata.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class ModelFiledInfoBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        ModelFiledInfoBean bean = new ModelFiledInfoBean();

        // bean.setAllowableFile( rs.getString( "allowableFile" ) );
        // bean.setChoiceValue( rs.getString( "choiceValue" ) );
        // bean.setDataModelId( Long.valueOf( rs.getLong( "dataModelId" ) ) );
        // bean.setDefaultValue( rs.getString( "defaultValue" ) );
        // bean.setErrorMessage( rs.getString( "errorMessage" ) );
        // bean.setFiledSign( rs.getString( "filedSign" ) );
        // bean.setHeight( rs.getString( "height" ) );
        // bean.setHtmlConfigId( Long.valueOf( rs.getLong( "htmlConfigId" ) ) );
        // bean.setHtmlContent( rs.getString( "htmlContent" ) );
        // bean.setHtmlElementId( Long.valueOf( rs.getLong( "htmlElementId" ) )
        // );
        // bean.setIsMustFill( Integer.valueOf( rs.getInt( "isMustFill" ) ) );
        // bean.setMaxLength( Integer.valueOf( rs.getInt( "maxLength" ) ) );
        // bean.setMetaDataId( Long.valueOf( rs.getLong( "metaDataId" ) ) );
        // bean.setPerdureType( rs.getString( "perdureType" ) );
        // bean.setRelateFiledName( rs.getString( "relateFiledName" ) );
        // bean.setShowName( rs.getString( "showName" ) );
        // bean.setValidateType( Integer.valueOf( rs.getInt( "validateType" ) )
        // );
        // bean.setWidth( rs.getString( "width" ) );

        bean.putModelFiledValue( Constant.METADATA.MF_ALLOW_FILE, rs
            .getString( Constant.METADATA.MF_ALLOW_FILE ) );

        bean.putModelFiledValue( Constant.METADATA.MF_CHOICE_VALUE, rs
            .getString( Constant.METADATA.MF_CHOICE_VALUE ) );

        bean.putModelFiledValue( Constant.METADATA.MF_DM_ID, Long.valueOf( rs
            .getLong( Constant.METADATA.MF_DM_ID ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_DEFAULT_VALUE, rs
            .getString( Constant.METADATA.MF_DEFAULT_VALUE ) );

        bean.putModelFiledValue( Constant.METADATA.MF_ERROR_MESSAGE, rs
            .getString( Constant.METADATA.MF_ERROR_MESSAGE ) );

        bean.putModelFiledValue( Constant.METADATA.MF_FILED_SIGN, rs
            .getString( Constant.METADATA.MF_FILED_SIGN ) );

        bean.putModelFiledValue( Constant.METADATA.MF_HC_ID, Long.valueOf( rs
            .getLong( Constant.METADATA.MF_HC_ID ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_HTML_CONTENT, rs
            .getString( Constant.METADATA.MF_HTML_CONTENT ) );

        bean.putModelFiledValue( Constant.METADATA.MF_HE_ID, Long.valueOf( rs
            .getLong( Constant.METADATA.MF_HE_ID ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_IS_MUST_FILL, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_IS_MUST_FILL ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_MAX_LENGTH, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_MAX_LENGTH ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_MD_ID, Long.valueOf( rs
            .getLong( Constant.METADATA.MF_MD_ID ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_PERDURE_TYPE, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_PERDURE_TYPE ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_RELATE_FILED_NAME, rs
            .getString( Constant.METADATA.MF_RELATE_FILED_NAME ) );

        bean.putModelFiledValue( Constant.METADATA.MF_SHOW_NAME, rs
            .getString( Constant.METADATA.MF_SHOW_NAME ) );

        bean.putModelFiledValue( Constant.METADATA.MF_CHECK_REGEX, rs
            .getString( Constant.METADATA.MF_CHECK_REGEX ) );

        bean.putModelFiledValue( Constant.METADATA.MF_CSS_CLASS, rs
            .getString( Constant.METADATA.MF_CSS_CLASS ) );

        bean.putModelFiledValue( Constant.METADATA.MF_CSS_CLASS, rs
            .getString( Constant.METADATA.MF_CSS_CLASS ) );

        bean.putModelFiledValue( Constant.METADATA.MF_DEFAULT_CHECK, rs
            .getString( Constant.METADATA.MF_DEFAULT_CHECK ) );

        bean.putModelFiledValue( Constant.METADATA.MF_DATA_TYPE, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_DATA_TYPE ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_HTML_DESC, rs
            .getString( Constant.METADATA.MF_HTML_DESC ) );

        bean.putModelFiledValue( Constant.METADATA.MF_STYLE, rs
            .getString( Constant.METADATA.MF_STYLE ) );

        bean.putModelFiledValue( Constant.METADATA.MF_JS, rs
            .getString( Constant.METADATA.MF_JS ) );

        bean.putModelFiledValue( Constant.METADATA.MF_FULL_EDITOR, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_FULL_EDITOR ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_IMAGE_DISPOSE_MODE,
            Integer.valueOf( rs
                .getInt( Constant.METADATA.MF_IMAGE_DISPOSE_MODE ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_IMAGE_H, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_IMAGE_H ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_IMAGE_W, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_IMAGE_W ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_LINK_MODEL_ID, Long
            .valueOf( rs.getLong( Constant.METADATA.MF_LINK_MODEL_ID ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_LINK_FIELD_ID, Long
            .valueOf( rs.getLong( Constant.METADATA.MF_LINK_FIELD_ID ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_LINK_TYPE, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_LINK_TYPE ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_MAIN_EDITOR, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_MAIN_EDITOR ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_NEED_MARK, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_NEED_MARK ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_USE_SYS_URL, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_USE_SYS_URL ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_EDITOR_W, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_EDITOR_W ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_EDITOR_H, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_EDITOR_H ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_QUERY_FLAG, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_QUERY_FLAG ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_ORDER_FLAG, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_ORDER_FLAG ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_SEARCH_FLAG, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_SEARCH_FLAG ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_PICK_FLAG, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_PICK_FLAG ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_ORDER_ID, Double
            .valueOf( rs.getDouble( Constant.METADATA.MF_ORDER_ID ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_BLANK_COUNT, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_BLANK_COUNT ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_IS_LIST_FIELD, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_IS_LIST_FIELD ) ) );

        bean.putModelFiledValue( Constant.METADATA.MF_LIST_SHOW_SIZE, Integer
            .valueOf( rs.getInt( Constant.METADATA.MF_LIST_SHOW_SIZE ) ) );

        return bean;
    }
}
