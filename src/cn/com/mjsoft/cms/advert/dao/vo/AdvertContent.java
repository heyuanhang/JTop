package cn.com.mjsoft.cms.advert.dao.vo;

import java.util.Date;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "advert_content", id = "advertId", idMode = EntitySqlBridge.DB_IDENTITY )
public class AdvertContent
{

    private Long advertId;
    private Long posId;
    private String adName;
    private String adFlag;
    private String advertCode = "";
    private Date showStartDate;
    private Date showEndDate;
    private Integer percentVal = Integer.valueOf( 0 );
    private Integer importance = Integer.valueOf( 0 );
    private String keyword;
    private String creator;
    private Integer target;
    private Integer useState;
    private Long siteId;

    public Long getAdvertId()
    {
        return this.advertId;
    }

    public void setAdvertId( Long advertId )
    {
        this.advertId = advertId;
    }

    public Long getPosId()
    {
        return this.posId;
    }

    public void setPosId( Long posId )
    {
        this.posId = posId;
    }

    public String getAdvertCode()
    {
        return advertCode;
    }

    public void setAdvertCode( String advertCode )
    {
        this.advertCode = advertCode;
    }

    public Date getShowStartDate()
    {
        return this.showStartDate;
    }

    public void setShowStartDate( Date showStartDate )
    {
        this.showStartDate = showStartDate;
    }

    public Date getShowEndDate()
    {
        return this.showEndDate;
    }

    public void setShowEndDate( Date showEndDate )
    {
        this.showEndDate = showEndDate;
    }

    public Integer getImportance()
    {
        return this.importance;
    }

    public void setImportance( Integer importance )
    {
        this.importance = importance;
    }

    public String getKeyword()
    {
        return this.keyword;
    }

    public void setKeyword( String keyword )
    {
        this.keyword = keyword;
    }

    public String getAdFlag()
    {
        return adFlag;
    }

    public void setAdFlag( String adFlag )
    {
        this.adFlag = adFlag;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public String getAdName()
    {
        return adName;
    }

    public void setAdName( String adName )
    {
        this.adName = adName;
    }

    public Integer getUseState()
    {
        return useState;
    }

    public void setUseState( Integer useState )
    {
        this.useState = useState;
    }

    public Integer getTarget()
    {
        return target;
    }

    public void setTarget( Integer target )
    {
        this.target = target;
    }

    public Integer getPercentVal()
    {
        return percentVal;
    }

    public void setPercentVal( Integer percentVal )
    {
        this.percentVal = percentVal;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

}
