package cn.com.mjsoft.cms.stat.bean;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SiteVisitDisposeBean
{
    private Long siteId = Long.valueOf( -1 );

    private Integer uvCount = Integer.valueOf( 0 );

    private Integer ipCount = Integer.valueOf( 0 );

    private Integer pvCount = Integer.valueOf( 0 );

    public Integer getIpCount()
    {
        return ipCount;
    }

    public void setIpCount( Integer ipCount )
    {
        this.ipCount = ipCount;
    }

    public Integer getPvCount()
    {
        return pvCount;
    }

    public void setPvCount( Integer pvCount )
    {
        this.pvCount = pvCount;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public Integer getUvCount()
    {
        return uvCount;
    }

    public void setUvCount( Integer uvCount )
    {
        this.uvCount = uvCount;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder( this ).append( "ipCount", this.ipCount )
            .append( "siteId", this.siteId ).append( "uvCount", this.uvCount )
            .append( "pvCount", this.pvCount ).toString();
    }

}
