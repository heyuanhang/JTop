package cn.com.mjsoft.cms.advert.bean;

import cn.com.mjsoft.cms.common.Constant;

public class AdvertConfigParamBean
{
    private Long paramId;
    private Long configId;
    private String paramFlag;
    private Integer paramType;
    private String paramName;
    private Integer htmlType;
    private String choiceValue;
    private String defaultValue;
    private Integer mustFill;
    private String allowFileType;
    private Integer fileSize;

    // param表业务字段
    private String value;

    public Long getConfigId()
    {
        return this.configId;
    }

    public void setConfigId( Long configId )
    {
        this.configId = configId;
    }

    public String getParamFlag()
    {
        return this.paramFlag;
    }

    public void setParamFlag( String paramFlag )
    {
        this.paramFlag = paramFlag;
    }

    public String getParamName()
    {
        return this.paramName;
    }

    public void setParamName( String paramName )
    {
        this.paramName = paramName;
    }

    public String getChoiceValue()
    {
        return this.choiceValue;
    }

    public void setChoiceValue( String choiceValue )
    {
        this.choiceValue = choiceValue;
    }

    public String getDefaultValue()
    {
        return this.defaultValue;
    }

    public void setDefaultValue( String defaultValue )
    {
        this.defaultValue = defaultValue;
    }

    public Integer getHtmlType()
    {
        return htmlType;
    }

    public void setHtmlType( Integer htmlType )
    {
        this.htmlType = htmlType;
    }

    public Integer getMustFill()
    {
        return mustFill;
    }

    public void setMustFill( Integer mustFill )
    {
        this.mustFill = mustFill;
    }

    public Integer getParamType()
    {
        return paramType;
    }

    public void setParamType( Integer paramType )
    {
        this.paramType = paramType;
    }

    public String getAllowFileType()
    {
        return this.allowFileType;
    }

    public void setAllowFileType( String allowFileType )
    {
        this.allowFileType = allowFileType;
    }

    public Integer getFileSize()
    {
        return this.fileSize;
    }

    public void setFileSize( Integer fileSize )
    {
        this.fileSize = fileSize;
    }

    public Long getParamId()
    {
        return paramId;
    }

    public void setParamId( Long paramId )
    {
        this.paramId = paramId;
    }

    // 业务方法
    public String getInputHtmlCode()
    {
        String result = "";

        if( Constant.METADATA.TEXT == this.paramType.intValue() )
        {
            // input类型
            result = "<input type=\"text\" size=\"60\" id=\"" + this.paramFlag
                + "\" name=\"" + this.paramFlag
                + "\" class=\"form-input\"></input>";
        }
        // else if{
        //            
        // }

        return result;
    }

    public String getEditHtmlCode()
    {
        String result = "";

        return result;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

}
