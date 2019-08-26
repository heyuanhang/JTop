package cn.com.mjsoft.cms.metadata.bean;

public class PathInjectAssistBean
{
    private Long modelId;

    private String fieldName;

    private Integer resType;

    private Long metaDataId;

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName( String fieldName )
    {
        this.fieldName = fieldName;
    }

    public Long getModelId()
    {
        return modelId;
    }

    public void setModelId( Long modelId )
    {
        this.modelId = modelId;
    }

    public Integer getResType()
    {
        return resType;
    }

    public void setResType( Integer resType )
    {
        this.resType = resType;
    }

    public Long getMetaDataId()
    {
        return metaDataId;
    }

    public void setMetaDataId( Long metaDataId )
    {
        this.metaDataId = metaDataId;
    }
}
