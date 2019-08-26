package cn.com.mjsoft.cms.search.dao.vo;

import java.util.Date;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "search_index_content_state", id = "indexStateId", idMode = EntitySqlBridge.DB_IDENTITY )
public class SearchIndexContentState
{
    private Long indexStateId = Long.valueOf( -1 );
    private Long siteId;
    private Long contentId;
    private Long classId;
    private Float boost = 1.0F;
    private Long modelId;
    private Integer censor;
    private Date indexDate;
    private Integer eventFlag;
    private Long clusterId;

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public Long getIndexStateId()
    {
        return indexStateId;
    }

    public void setIndexStateId( Long indexStateId )
    {
        this.indexStateId = indexStateId;
    }

    public Long getContentId()
    {
        return this.contentId;
    }

    public void setContentId( Long contentId )
    {
        this.contentId = contentId;
    }

    public Long getClassId()
    {
        return this.classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Long getModelId()
    {
        return this.modelId;
    }

    public void setModelId( Long modelId )
    {
        this.modelId = modelId;
    }

    public Date getIndexDate()
    {
        return indexDate;
    }

    public void setIndexDate( Date indexDate )
    {
        this.indexDate = indexDate;
    }

    public Integer getEventFlag()
    {
        return this.eventFlag;
    }

    public void setEventFlag( Integer eventFlag )
    {
        this.eventFlag = eventFlag;
    }

    public Integer getCensor()
    {
        return censor;
    }

    public void setCensor( Integer censor )
    {
        this.censor = censor;
    }

    public Long getClusterId()
    {
        return clusterId;
    }

    public void setClusterId( Long clusterId )
    {
        this.clusterId = clusterId;
    }

    public Float getBoost()
    {
        return boost;
    }

    public void setBoost( Float boost )
    {
        this.boost = boost;
    }

}
