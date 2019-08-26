package cn.com.mjsoft.cms.metadata.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "model_filed_metadata", id = "metaDataId", idMode = EntitySqlBridge.DB_IDENTITY )
public class ModelFiledMetadata 
{
    private Long metaDataId = Long.valueOf( -1 );
    // 所属模型
    private Long dataModelId = Long.valueOf( -1 );

    private String showName;
    private String relateFiledName;
    private Integer perdureType;
    private Long capacity;
    private String filedSign;

    private Integer queryFlag;

    private Integer orderFlag;

    private Integer searchFlag;

    private Integer pickFlag;

    private Double orderId;

    // 字段

    public Long getMetaDataId()
    {
        return this.metaDataId;
    }

    public void setMetaDataId( Long metaDataId )
    {
        this.metaDataId = metaDataId;
    }

    public Long getDataModelId()
    {
        return this.dataModelId;
    }

    public void setDataModelId( Long dataModelId )
    {
        this.dataModelId = dataModelId;
    }

    public String getShowName()
    {
        return this.showName;
    }

    public void setShowName( String showName )
    {
        this.showName = showName;
    }

    public String getRelateFiledName()
    {
        return this.relateFiledName;
    }

    public void setRelateFiledName( String relateFiledName )
    {
        this.relateFiledName = relateFiledName;
    }

    public Integer getOrderFlag()
    {
        return orderFlag;
    }

    public void setOrderFlag( Integer orderFlag )
    {
        this.orderFlag = orderFlag;
    }

    public Integer getQueryFlag()
    {
        return queryFlag;
    }

    public void setQueryFlag( Integer queryFlag )
    {
        this.queryFlag = queryFlag;
    }

    public Integer getPerdureType()
    {
        return perdureType;
    }

    public void setPerdureType( Integer perdureType )
    {
        this.perdureType = perdureType;
    }

    public String getFiledSign()
    {
        return this.filedSign;
    }

    public void setFiledSign( String filedSign )
    {
        this.filedSign = filedSign;
    }

    public Double getOrderId()
    {
        return orderId;
    }

    public void setOrderId( Double orderId )
    {
        this.orderId = orderId;
    }

    public Long getCapacity()
    {
        return capacity;
    }

    public void setCapacity( Long capacity )
    {
        this.capacity = capacity;
    }

    public Integer getSearchFlag()
    {
        return searchFlag;
    }

    public void setSearchFlag( Integer searchFlag )
    {
        this.searchFlag = searchFlag;
    }

    public Integer getPickFlag()
    {
        return pickFlag;
    }

    public void setPickFlag( Integer pickFlag )
    {
        this.pickFlag = pickFlag;
    }
}
