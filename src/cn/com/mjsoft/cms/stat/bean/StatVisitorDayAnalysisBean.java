package cn.com.mjsoft.cms.stat.bean;

import java.sql.Timestamp;

import cn.com.mjsoft.framework.util.MathUtil;
import cn.com.mjsoft.framework.util.StringUtil;

public class StatVisitorDayAnalysisBean
{
    private Integer uvCount;
    private Integer ipCount;
    private Integer pvCount;
    private Integer visitYear;
    private Integer visitMonth;
    private Integer visitDay;
    private Integer newUv;
    private Integer oldUv;
    private Timestamp vdt;
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

    public Timestamp getVdt()
    {
        return vdt;
    }

    public void setVdt( Timestamp vdt )
    {
        this.vdt = vdt;
    }

    //
    public String getOldUvPer()
    {
        double ov = ( newUv.longValue() + oldUv.longValue() ) != 0 ? MathUtil
            .mul( MathUtil.div(
                StringUtil.getDoubleValue( oldUv.toString(), 0 ), StringUtil
                    .getDoubleValue( ""
                        + ( newUv.longValue() + oldUv.longValue() ), 0 ), 4 ),
                100 ) : 0.0d;

        return ov + "";
    }

}
