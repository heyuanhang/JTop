package cn.com.mjsoft.cms.metadata.bean;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import cn.com.mjsoft.cms.common.Constant;

/**
 * 合并的模型字段元数据信息，包含字段数据库级别定义以及html级别定义信息
 * 
 * @author mjsoft
 * 
 */
public class ModelFiledInfoBean
{

    // private Long htmlConfigId = Long.valueOf( -1 );
    // private Long htmlElementId = Long.valueOf( -1 );
    // private Long metaDataId = Long.valueOf( -1 );
    // private Long dataModelId = Long.valueOf( -1 );
    // private Integer isMustFill;
    // private Integer maxLength;
    // private String defaultValue;
    // private String choiceValue;

    // private String errorMessage;
    // private String htmlContent;
    // private String allowableFile;
    // private String showName;
    // private String relateFiledName;
    // private Integer perdureType;
    // private Long capacity;
    // private String filedSign;
    // private Integer dataType;
    // private String style;
    // private String cssClass;
    // private String javascript;
    // private String htmlDesc;
    // private String checkRegex;
    // private String defaultValidate;

    // private Integer needMark;
    // private Integer imageH;
    // private Integer imageW;
    // private Integer imageDisposeMode;
    // private Long linkModelId;
    // private Integer linkType;
    // private Long linkFieldId;
    // private Integer useSysUrl;
    // private Integer fullEditor;
    // private Integer mainEditor;
    // private Integer editorW;
    // private Integer editorH;
    // private Integer blankCount;
    // private Integer isListField = Integer.valueOf( 0 );
    // private Integer listShowSize = Integer.valueOf( 10 );

    // private Integer queryFlag;

    // private Integer orderFlag;

    // private Integer searchFlag;

    // private Integer pickFlag;

    // private Double orderId;

    private Map valueMap = new HashMap( 40 );

    /**
     * 根据指定的key获取模型字段元数据信息
     * 
     * @param key
     * @return
     */
    public Object retrieveModelFiledValueByKey( String key )
    {
        return valueMap.get( key );
    }

    /**
     * 设定指定的元数据值
     * 
     * @param key
     * @param value
     */
    public void putModelFiledValue( String key, Object value )
    {
        valueMap.put( key, value );
    }

    public String getAllowableFile()
    {
        // return allowableFile;
        return ( String ) valueMap.get( Constant.METADATA.MF_ALLOW_FILE );
    }

    public void setAllowableFile( String allowableFile )
    {
        // this.allowableFile = allowableFile;
        valueMap.put( Constant.METADATA.MF_ALLOW_FILE, allowableFile );
    }

    public String getChoiceValue()
    {
        // return choiceValue;
        return ( String ) valueMap.get( Constant.METADATA.MF_CHOICE_VALUE );
    }

    public String getHtmlDesc()
    {
        // return htmlDesc;
        return ( String ) valueMap.get( Constant.METADATA.MF_HTML_DESC );
    }

    public void setHtmlDesc( String htmlDesc )
    {
        // this.htmlDesc = htmlDesc;
        valueMap.put( Constant.METADATA.MF_HTML_DESC, htmlDesc );
    }

    public void setChoiceValue( String choiceValue )
    {
        // this.choiceValue = choiceValue;
        valueMap.put( Constant.METADATA.MF_CHOICE_VALUE, choiceValue );
    }

    public Long getDataModelId()
    {
        // return dataModelId;
        return ( Long ) valueMap.get( Constant.METADATA.MF_DM_ID );
    }

    public void setDataModelId( Long dataModelId )
    {
        // this.dataModelId = dataModelId;
        valueMap.put( Constant.METADATA.MF_DM_ID, dataModelId );
    }

    public String getDefaultValue()
    {
        // return defaultValue;
        return ( String ) valueMap.get( Constant.METADATA.MF_DEFAULT_VALUE );
    }

    public void setDefaultValue( String defaultValue )
    {
        // this.defaultValue = defaultValue;
        valueMap.put( Constant.METADATA.MF_DEFAULT_VALUE, defaultValue );
    }

    public String getErrorMessage()
    {
        // return errorMessage;
        return ( String ) valueMap.get( Constant.METADATA.MF_ERROR_MESSAGE );
    }

    public void setErrorMessage( String errorMessage )
    {
        // this.errorMessage = errorMessage;
        valueMap.put( Constant.METADATA.MF_ERROR_MESSAGE, errorMessage );
    }

    public String getFieldSign()
    {
        // return filedSign;
        return ( String ) valueMap.get( Constant.METADATA.MF_FILED_SIGN );
    }

    public void setFieldSign( String filedSign )
    {
        // this.filedSign = filedSign;
        valueMap.put( Constant.METADATA.MF_FILED_SIGN, filedSign );
    }

    // public String getHeight()
    // {
    // // return height;
    // return ( String ) valueMap.get( Constant.METADATA.MF_HEIGHT );
    // }

    public Long getHtmlConfigId()
    {
        // return htmlConfigId;
        return ( Long ) valueMap.get( Constant.METADATA.MF_HC_ID );
    }

    public void setHtmlConfigId( Long htmlConfigId )
    {
        // this.htmlConfigId = htmlConfigId;
        valueMap.put( Constant.METADATA.MF_HC_ID, htmlConfigId );
    }

    public String getHtmlContent()
    {
        // return htmlContent;
        return ( String ) valueMap.get( Constant.METADATA.MF_HTML_CONTENT );
    }

    public void setHtmlContent( String htmlContent )
    {
        // this.htmlContent = htmlContent;
        valueMap.put( Constant.METADATA.MF_HTML_CONTENT, htmlContent );
    }

    public Long getHtmlElementId()
    {
        // return htmlElementId;
        return ( Long ) valueMap.get( Constant.METADATA.MF_HE_ID );
    }

    public void setHtmlElementId( Long htmlElementId )
    {
        // this.htmlElementId = htmlElementId;
        valueMap.put( Constant.METADATA.MF_HTML_CONTENT, htmlElementId );
    }

    public Integer getIsMustFill()
    {
        // return isMustFill;
        return ( Integer ) valueMap.get( Constant.METADATA.MF_IS_MUST_FILL );
    }

    public void setIsMustFill( Integer isMustFill )
    {
        // this.isMustFill = isMustFill;
        valueMap.put( Constant.METADATA.MF_IS_MUST_FILL, isMustFill );
    }

    public Integer getMaxLength()
    {
        // return maxLength;
        return ( Integer ) valueMap.get( Constant.METADATA.MF_MAX_LENGTH );
    }

    public void setMaxLength( Integer maxLength )
    {
        // this.maxLength = maxLength;
        valueMap.put( Constant.METADATA.MF_MAX_LENGTH, maxLength );
    }

    public Long getMetaDataId()
    {
        // return metaDataId;
        return ( Long ) valueMap.get( Constant.METADATA.MF_MD_ID );
    }

    public void setMetaDataId( Long metaDataId )
    {
        // this.metaDataId = metaDataId;
        valueMap.put( Constant.METADATA.MF_MD_ID, metaDataId );
    }

    public Integer getPerdureType()
    {
        // return perdureType;
        return ( Integer ) valueMap.get( Constant.METADATA.MF_PERDURE_TYPE );
    }

    public void setPerdureType( Integer perdureType )
    {
        // this.perdureType = perdureType;
        valueMap.put( Constant.METADATA.MF_PERDURE_TYPE, perdureType );
    }

    public String getRelateFiledName()
    {
        // return relateFiledName;
        return ( String ) valueMap.get( Constant.METADATA.MF_RELATE_FILED_NAME );
    }

    public void setRelateFiledName( String relateFiledName )
    {
        // this.relateFiledName = relateFiledName;
        valueMap.put( Constant.METADATA.MF_RELATE_FILED_NAME, relateFiledName );
    }

    public String getShowName()
    {
        // return showName;
        return ( String ) valueMap.get( Constant.METADATA.MF_SHOW_NAME );
    }

    public void setShowName( String showName )
    {
        // this.showName = showName;
        valueMap.put( Constant.METADATA.MF_RELATE_FILED_NAME, showName );
    }

    // public String getWidth()
    // {
    // // return width;
    // return ( String ) valueMap.get( Constant.METADATA.MF_WIDTH );
    // }
    //
    // public void setWidth( String width )
    // {
    // // this.width = width;
    // valueMap.put( Constant.METADATA.MF_WIDTH, width );
    // }

    public String getCheckRegex()
    {
        // return checkRegex;
        return ( String ) valueMap.get( Constant.METADATA.MF_CHECK_REGEX );
    }

    public void setCheckRegex( String checkRegex )
    {
        // this.checkRegex = checkRegex;
        valueMap.put( Constant.METADATA.MF_CHECK_REGEX, checkRegex );
    }

    public String getDefaultValidate()
    {
        // return defaultCheck;
        return ( String ) valueMap.get( Constant.METADATA.MF_DEFAULT_CHECK );
    }

    public void setDefaultValidate( String defaultCheck )
    {
        // this.defaultCheck = defaultCheck;
        valueMap.put( Constant.METADATA.MF_DEFAULT_CHECK, defaultCheck );
    }

    public Long getCapacity()
    {
        return ( Long ) valueMap.get( Constant.METADATA.MF_CAPACITY );
    }

    public void setCapacity( Long capacity )
    {
        valueMap.put( Constant.METADATA.MF_CAPACITY, capacity );
    }

    public Integer getDataType()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_DATA_TYPE );
    }

    public void setDataType( Integer dataType )
    {
        valueMap.put( Constant.METADATA.MF_DATA_TYPE, dataType );
    }

    public String getJavascript()
    {
        return ( String ) valueMap.get( Constant.METADATA.MF_JS );
    }

    public void setJavascript( String javascript )
    {
        valueMap.put( Constant.METADATA.MF_JS, javascript );
    }

    public String getStyle()
    {
        return ( String ) valueMap.get( Constant.METADATA.MF_STYLE );
    }

    public void setStyle( String style )
    {
        valueMap.put( Constant.METADATA.MF_STYLE, style );
    }

    public String getCssClass()
    {
        return ( String ) valueMap.get( Constant.METADATA.MF_CSS_CLASS );
    }

    public void setCssClass( String cssClass )
    {
        valueMap.put( Constant.METADATA.MF_CSS_CLASS, cssClass );
    }

    public Integer getFullEditor()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_FULL_EDITOR );
    }

    public void setFullEditor( Integer fullEditor )
    {
        valueMap.put( Constant.METADATA.MF_FULL_EDITOR, fullEditor );
    }

    public Integer getImageDisposeMode()
    {
        return ( Integer ) valueMap
            .get( Constant.METADATA.MF_IMAGE_DISPOSE_MODE );
    }

    public void setImageDisposeMode( Integer imageDisposeMode )
    {
        valueMap
            .put( Constant.METADATA.MF_IMAGE_DISPOSE_MODE, imageDisposeMode );
    }

    public Integer getImageH()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_IMAGE_H );
    }

    public void setImageH( Integer imageH )
    {
        valueMap.put( Constant.METADATA.MF_IMAGE_H, imageH );
    }

    public Integer getImageW()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_IMAGE_W );
    }

    public void setImageW( Integer imageW )
    {
        valueMap.put( Constant.METADATA.MF_IMAGE_W, imageW );
    }

    public Long getLinkFieldId()
    {
        return ( Long ) valueMap.get( Constant.METADATA.MF_LINK_FIELD_ID );
    }

    public void setLinkFieldId( Long linkFieldId )
    {
        valueMap.put( Constant.METADATA.MF_LINK_FIELD_ID, linkFieldId );
    }

    public Integer getLinkType()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_LINK_TYPE );
    }

    public void setLinkType( Integer linkType )
    {
        valueMap.put( Constant.METADATA.MF_LINK_TYPE, linkType );
    }

    public Integer getMainEditor()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_MAIN_EDITOR );
    }

    public void setMainEditor( Integer mainEditor )
    {
        valueMap.put( Constant.METADATA.MF_MAIN_EDITOR, mainEditor );
    }

    public Integer getNeedMark()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_NEED_MARK );
    }

    public void setNeedMark( Integer needMark )
    {
        valueMap.put( Constant.METADATA.MF_NEED_MARK, needMark );
    }

    public Integer getUseSysUrl()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_USE_SYS_URL );
    }

    public void setUseSysUrl( Integer useSysUrl )
    {
        valueMap.put( Constant.METADATA.MF_USE_SYS_URL, useSysUrl );
    }

    public Integer getEditorH()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_EDITOR_H );
    }

    public void setEditorH( Integer editorH )
    {
        valueMap.put( Constant.METADATA.MF_EDITOR_H, editorH );
    }

    public Integer getEditorW()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_EDITOR_W );
    }

    public void setEditorW( Integer editorW )
    {
        valueMap.put( Constant.METADATA.MF_EDITOR_W, editorW );
    }

    public Integer getOrderFlag()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_ORDER_FLAG );
    }

    public void setOrderFlag( Integer orderFlag )
    {
        valueMap.put( Constant.METADATA.MF_ORDER_FLAG, orderFlag );
    }

    public Integer getQueryFlag()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_QUERY_FLAG );
    }

    public void setQueryFlag( Integer queryFlag )
    {
        valueMap.put( Constant.METADATA.MF_QUERY_FLAG, queryFlag );
    }

    public Integer getSearchFlag()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_SEARCH_FLAG );
    }

    public void setSearchFlag( Integer searchFlag )
    {
        valueMap.put( Constant.METADATA.MF_SEARCH_FLAG, searchFlag );
    }

    public Integer getPickFlag()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_PICK_FLAG );
    }

    public void setPickFlag( Integer pickFlag )
    {
        valueMap.put( Constant.METADATA.MF_PICK_FLAG, pickFlag );
    }

    public Double getOrderId()
    {
        return ( Double ) valueMap.get( Constant.METADATA.MF_ORDER_ID );
    }

    public void setOrderId( Double orderId )
    {
        valueMap.put( Constant.METADATA.MF_ORDER_ID, orderId );
    }

    public Integer getBlankCount()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_BLANK_COUNT );
    }

    public void setBlankCount( Integer blankCount )
    {
        valueMap.put( Constant.METADATA.MF_BLANK_COUNT, blankCount );
    }

    public Integer getIsListField()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_IS_LIST_FIELD );
    }

    public void setIsListField( Integer isListField )
    {
        valueMap.put( Constant.METADATA.MF_IS_LIST_FIELD, isListField );
    }

    public Integer getListShowSize()
    {
        return ( Integer ) valueMap.get( Constant.METADATA.MF_LIST_SHOW_SIZE );
    }

    public void setListShowSize( Integer listShowSize )
    {
        valueMap.put( Constant.METADATA.MF_LIST_SHOW_SIZE, listShowSize );
    }

    public Long getLinkModelId()
    {
        return ( Long ) valueMap.get( Constant.METADATA.MF_LINK_MODEL_ID );
    }

    public void setLinkModelId( Long linkModelId )
    {
        valueMap.put( Constant.METADATA.MF_LINK_MODEL_ID, linkModelId );
    }

    // 业务扩展字段
    public String getOrderIdVal()
    {
        Double doubleVal = ( Double ) valueMap
            .get( Constant.METADATA.MF_ORDER_ID );
        String str = doubleVal.toString();

        if( str.lastIndexOf( ".0" ) != -1 )
        {
            DecimalFormat df = new DecimalFormat( "#" );
            return df.format( doubleVal.doubleValue() );
        }

        return doubleVal.toString();
    }

    public String getMustFillStr()
    {
        if( this.getIsMustFill() == null )
        {
            return "否";
        }

        switch ( this.getIsMustFill().intValue() )
        {
            case 1:
                return "是";
            case 0:
                return "否";
            default:
                return "否";
        }

    }

    public String getHtmlModeStr()
    {
        switch ( this.getHtmlElementId().intValue() )
        {
            case 1:
                return "文本输入框";
            case 2:
                return "多行文本框";
            case 3:
                return "文本编辑器";
            case 4:
                return "下拉选择框";
            case 5:
                return "单选框";
            case 6:
                return "复选框";
            case 7:
                return "日期";
            case 8:
                return "时间";
            case 9:
                return "日期时间";
            case 10:
                return "文件上传";
            case 11:
                return "图片上传";
            case 12:
                return "媒体上传";
            case 13:
                return "自定义HTML";
            case 14:
                return "图片集";
            case 15:
                return "内部字段";

            default:
                return "";
        }
    }

    public Map getValueMap()
    {
        return valueMap;
    }

    public void setValueMap( Map valueMap )
    {
        this.valueMap = valueMap;
    }

}
