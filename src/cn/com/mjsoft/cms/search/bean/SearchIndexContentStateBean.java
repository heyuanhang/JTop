package cn.com.mjsoft.cms.search.bean;

import java.util.Date;

public class SearchIndexContentStateBean
{
    private static final long serialVersionUID = -7084452638598706200L;

    private Long indexStateId = Long.valueOf( -1 );
    private Long siteId;
    private Long contentId;
    private Long classId;
    private Long modelId;
    private Float boost = 1.0F;
    private Integer censor;
    private Date indexDate;
    private Integer eventFlag;
    private Long clusterId;

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

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public Integer getCensor()
    {
        return censor;
    }

    public void setCensor( Integer censor )
    {
        this.censor = censor;
    }

    public Float getBoost()
    {
        return boost;
    }

    public void setBoost( Float boost )
    {
        this.boost = boost;
    }

    public Long getClusterId()
    {
        return clusterId;
    }

    public void setClusterId( Long clusterId )
    {
        this.clusterId = clusterId;
    }

}
