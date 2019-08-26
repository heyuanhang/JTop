package cn.com.mjsoft.cms.metadata.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "model_html_config", id = "htmlConfigId", idMode = EntitySqlBridge.DB_IDENTITY )
public class ModelHtmlConfig
{

    private Long htmlConfigId = Long.valueOf( -1 );
    // 所属字段
    private Long metaDataId = Long.valueOf( -1 );
    private Long htmlElementId = Long.valueOf( -1 );
    private Integer isMustFill;
    // 最大值同时绝对HTML元素长度以及数据库字段长度
    private Integer maxLength;
    private String defaultValue;
    private String choiceValue;
    private String errorMessage;
    private String htmlContent;
    private String allowableFile;
    private Integer dataType;
    private String style;// CSS文本
    private String cssClass;// class类型
    private String javascript;// js代码
    private String htmlDesc;
    private String checkRegex;// 自定义验证js
    private String defaultValidate;// 默认check类型
    private Integer needMark;
    private Integer imageH;
    private Integer imageW;
    private Integer imageDisposeMode;
    private Long linkModelId;
    private Integer linkType;
    private Long linkFieldId;
    private Integer useSysUrl;
    private Integer fullEditor;
    private Integer mainEditor;
    private Integer editorW;
    private Integer editorH;
    private Integer blankCount = Integer.valueOf( 0 );

    private Integer isListField = Integer.valueOf( 0 );
    private Integer listShowSize = Integer.valueOf( 10 );

    public Long getHtmlConfigId()
    {
        return this.htmlConfigId;
    }

    public void setHtmlConfigId( Long htmlConfigId )
    {
        this.htmlConfigId = htmlConfigId;
    }

    public Long getHtmlElementId()
    {
        return this.htmlElementId;
    }

    public void setHtmlElementId( Long htmlElementId )
    {
        this.htmlElementId = htmlElementId;
    }

    public Integer getMaxLength()
    {
        return this.maxLength;
    }

    public void setMaxLength( Integer maxLength )
    {
        this.maxLength = maxLength;
    }

    public String getCheckRegex()
    {
        return checkRegex;
    }

    public void setCheckRegex( String checkRegex )
    {
        this.checkRegex = checkRegex;
    }

    public String getDefaultValidate()
    {
        return defaultValidate;
    }

    public void setDefaultValidate( String defaultValidate )
    {
        this.defaultValidate = defaultValidate;
    }

    public String getHtmlDesc()
    {
        return htmlDesc;
    }

    public void setHtmlDesc( String htmlDesc )
    {
        this.htmlDesc = htmlDesc;
    }

    public String getDefaultValue()
    {
        return this.defaultValue;
    }

    public void setDefaultValue( String defaultValue )
    {
        this.defaultValue = defaultValue;
    }

    public String getChoiceValue()
    {
        return this.choiceValue;
    }

    public void setChoiceValue( String choiceValue )
    {
        this.choiceValue = choiceValue;
    }

    public String getErrorMessage()
    {
        return this.errorMessage;
    }

    public void setErrorMessage( String errorMessage )
    {
        this.errorMessage = errorMessage;
    }

    public String getHtmlContent()
    {
        return this.htmlContent;
    }

    public void setHtmlContent( String htmlContent )
    {
        this.htmlContent = htmlContent;
    }

    // public String getWidth()
    // {
    // return this.width;
    // }
    //
    // public void setWidth( String width )
    // {
    // this.width = width;
    // }
    //
    // public String getHeight()
    // {
    // return this.height;
    // }
    //
    // public void setHeight( String height )
    // {
    // this.height = height;
    // }

    public String getAllowableFile()
    {
        return this.allowableFile;
    }

    public void setAllowableFile( String allowableFile )
    {
        this.allowableFile = allowableFile;
    }

    public Integer getIsMustFill()
    {
        return isMustFill;
    }

    public void setIsMustFill( Integer isMustFill )
    {
        this.isMustFill = isMustFill;
    }

    public Long getMetaDataId()
    {
        return metaDataId;
    }

    public void setMetaDataId( Long metaDataId )
    {
        this.metaDataId = metaDataId;
    }

    public String getJavascript()
    {
        return javascript;
    }

    public void setJavascript( String javascript )
    {
        this.javascript = javascript;
    }

    public String getStyle()
    {
        return style;
    }

    public void setStyle( String style )
    {
        this.style = style;
    }

    public Integer getDataType()
    {
        return dataType;
    }

    public void setDataType( Integer dataType )
    {
        this.dataType = dataType;
    }

    public String getCssClass()
    {
        return cssClass;
    }

    public void setCssClass( String cssClass )
    {
        this.cssClass = cssClass;
    }

    public Integer getFullEditor()
    {
        return fullEditor;
    }

    public void setFullEditor( Integer fullEditor )
    {
        this.fullEditor = fullEditor;
    }

    public Integer getImageDisposeMode()
    {
        return imageDisposeMode;
    }

    public void setImageDisposeMode( Integer imageDisposeMode )
    {
        this.imageDisposeMode = imageDisposeMode;
    }

    public Integer getImageH()
    {
        return imageH;
    }

    public void setImageH( Integer imageH )
    {
        this.imageH = imageH;
    }

    public Integer getImageW()
    {
        return imageW;
    }

    public void setImageW( Integer imageW )
    {
        this.imageW = imageW;
    }

    public Long getLinkFieldId()
    {
        return linkFieldId;
    }

    public void setLinkFieldId( Long linkFieldId )
    {
        this.linkFieldId = linkFieldId;
    }

    public Integer getLinkType()
    {
        return linkType;
    }

    public void setLinkType( Integer linkType )
    {
        this.linkType = linkType;
    }

    public Integer getMainEditor()
    {
        return mainEditor;
    }

    public void setMainEditor( Integer mainEditor )
    {
        this.mainEditor = mainEditor;
    }

    public Integer getNeedMark()
    {
        return needMark;
    }

    public void setNeedMark( Integer needMark )
    {
        this.needMark = needMark;
    }

    public Integer getUseSysUrl()
    {
        return useSysUrl;
    }

    public void setUseSysUrl( Integer useSysUrl )
    {
        this.useSysUrl = useSysUrl;
    }

    public Integer getEditorH()
    {
        return editorH;
    }

    public void setEditorH( Integer editorH )
    {
        this.editorH = editorH;
    }

    public Integer getEditorW()
    {
        return editorW;
    }

    public void setEditorW( Integer editorW )
    {
        this.editorW = editorW;
    }

    public Long getLinkModelId()
    {
        return linkModelId;
    }

    public void setLinkModelId( Long linkModelId )
    {
        this.linkModelId = linkModelId;
    }

    public Integer getBlankCount()
    {
        return blankCount;
    }

    public void setBlankCount( Integer blankCount )
    {
        this.blankCount = blankCount;
    }

    public Integer getIsListField()
    {
        return isListField;
    }

    public void setIsListField( Integer isListField )
    {
        this.isListField = isListField;
    }

    public Integer getListShowSize()
    {
        return listShowSize;
    }

    public void setListShowSize( Integer listShowSize )
    {
        this.listShowSize = listShowSize;
    }

    public void textMode()
    {
        this.htmlContent = "";
        this.choiceValue = "";
        this.allowableFile = "";
    }

    public void textAreaMode()
    {
        this.htmlContent = "";
        this.choiceValue = "";
        this.allowableFile = "";
    }

    public void selectMode()
    {
        // this.maxLength = null;

        this.htmlContent = "";
        this.defaultValidate = null;
        this.errorMessage = null;
        this.allowableFile = "";
    }

    public void radioMode()
    {
        // this.maxLength = null;

        this.htmlContent = "";
        this.isMustFill = null;
        this.defaultValidate = null;
        this.errorMessage = null;
        this.allowableFile = "";
    }

    public void checkBoxMode()
    {
        // this.maxLength = null;

        this.htmlContent = "";
        this.defaultValidate = null;
        this.errorMessage = null;
        this.allowableFile = "";
    }

    public void dateTimeMode()
    {
        // this.maxLength = null;

        this.htmlContent = "";
        this.defaultValue = null;
        this.defaultValidate = null;
        this.errorMessage = null;
        this.allowableFile = "";
    }

    public void fileUploadMode()
    {
        // this.maxLength = null;

        this.htmlContent = "";
        this.defaultValue = null;
        this.defaultValidate = null;
        this.errorMessage = null;
    }

    public void htmlContentMode()
    {
        // this.maxLength = null;
        this.dataType = null;
        this.defaultValue = null;
        this.isMustFill = null;
        this.defaultValidate = null;
        this.errorMessage = null;
        this.allowableFile = "";
    }

}
