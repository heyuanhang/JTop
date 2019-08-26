package cn.com.mjsoft.cms.stat.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "stat_visitor_hour_analysis", id = "", idMode = EntitySqlBridge.NO_KEY_ID )
public class StatVisitorHourAnalysis
{
    private Integer uvCount;
    private Integer ipCount;
    private Integer pvCount;
    private Integer visitYear;
    private Integer visitMonth;
    private Integer visitDay;
    private Integer visitHour;

    private Integer newUv;

    private Integer oldUv;

    private Long siteId;

    public Integer getUvCount()
    {
        return this.uvCount;
    }

    public void setUvCount( Integer uvCount )
    {
        this.uvCount = uvCount;
    }

    public Integer getIpCount()
    {
        return this.ipCount;
    }

    public void setIpCount( Integer ipCount )
    {
        this.ipCount = ipCount;
    }

    public Integer getPvCount()
    {
        return this.pvCount;
    }

    public void setPvCount( Integer pvCount )
    {
        this.pvCount = pvCount;
    }

    public Integer getVisitYear()
    {
        return this.visitYear;
    }

    public void setVisitYear( Integer visitYear )
    {
        this.visitYear = visitYear;
    }

    public Integer getVisitMonth()
    {
        return this.visitMonth;
    }

    public void setVisitMonth( Integer visitMonth )
    {
        this.visitMonth = visitMonth;
    }

    public Integer getVisitDay()
    {
        return this.visitDay;
    }

    public void setVisitDay( Integer visitDay )
    {
        this.visitDay = visitDay;
    }

    public Integer getVisitHour()
    {
        return this.visitHour;
    }

    public void setVisitHour( Integer visitHour )
    {
        this.visitHour = visitHour;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public Integer getNewUv()
    {
        return newUv;
    }

    public void setNewUv( Integer newUv )
    {
        this.newUv = newUv;
    }

    public Integer getOldUv()
    {
        return oldUv;
    }

    public void setOldUv( Integer oldUv )
    {
        this.oldUv = oldUv;
    }
}
