package cn.com.mjsoft.cms.metadata.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "model_res_path_inject_assist", id = "", idMode = EntitySqlBridge.NO_KEY_ID )
public class PathInjectAssist
{
    private Long modelId;

    private String fieldName;

    private Integer resType;

    private Long metaDataId;

    public PathInjectAssist()
    {

    }

    public PathInjectAssist( Long modelId, String fieldName, Integer resType, Long metaDataId )
    {
        super();
        this.modelId = modelId;
        this.fieldName = fieldName;
        this.resType = resType;
        this.metaDataId = metaDataId;
    }

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
