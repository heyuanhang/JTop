package cn.com.mjsoft.cms.advert.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "advert_config_param", id = "paramId", idMode = EntitySqlBridge.DB_IDENTITY )
public class AdvertConfigParam
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

}
