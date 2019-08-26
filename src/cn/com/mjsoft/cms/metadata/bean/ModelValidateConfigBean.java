package cn.com.mjsoft.cms.metadata.bean;

import cn.com.mjsoft.framework.util.StringUtil;

public class ModelValidateConfigBean
{
    private Long validateConfigId = Long.valueOf( -1 );

    private String validateName = "";

    private String regulation = "";

    private String errorMessage = "";

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage( String errorMessage )
    {
        this.errorMessage = errorMessage;
    }

    public String getRegulation()
    {
        return StringUtil
            .replaceString( regulation, "\\", "\\\\", false, false );
    }

    public void setRegulation( String regulation )
    {
        this.regulation = regulation;
    }

    public Long getValidateConfigId()
    {
        return validateConfigId;
    }

    public void setValidateConfigId( Long validateConfigId )
    {
        this.validateConfigId = validateConfigId;
    }

    public String getValidateName()
    {
        return validateName;
    }

    public void setValidateName( String validateName )
    {
        this.validateName = validateName;
    }

    public static void main( String[] args )
    { }

}
