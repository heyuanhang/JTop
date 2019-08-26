package cn.com.mjsoft.cms.stat.dao.vo;

// default package

import java.util.Date;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "stat_visit_info", id = "visitorId", idMode = EntitySqlBridge.DB_IDENTITY )
public class StatVisitInfo
{
    private Long visitorId;
    private String uvId;
    private String reffer;
    private String refferHost;
    private Integer refferType;
    private String refferKey;
    private Date visitTimeIn;
    private Date visitTimeOut;
    private String ip;
    private String host;
    private String url;
    private String title;
    private String browser;
    private String screen;
    private String system;
    private String lang;
    private String source;
    private Integer visitYear;
    private Integer visitMonth;
    private Integer visitDay;
    private Integer visitHour;
    private Long contentId;
    private Long classId;
    private Long siteId;

    public Long getVisitorId()
    {
        return this.visitorId;
    }

    public void setVisitorId( Long visitorId )
    {
        this.visitorId = visitorId;
    }

    public String getUvId()
    {
        return this.uvId;
    }

    public void setUvId( String uvId )
    {
        this.uvId = uvId;
    }

    public String getReffer()
    {
        return this.reffer;
    }

    public void setReffer( String reffer )
    {
        this.reffer = reffer;
    }

    public Integer getRefferType()
    {
        return refferType;
    }

    public void setRefferType( Integer refferType )
    {
        this.refferType = refferType;
    }

    public String getRefferKey()
    {
        return this.refferKey;
    }

    public void setRefferKey( String refferKey )
    {
        this.refferKey = refferKey;
    }

    public Date getVisitTimeIn()
    {
        return this.visitTimeIn;
    }

    public void setVisitTimeIn( Date visitTimeIn )
    {
        this.visitTimeIn = visitTimeIn;
    }

    public Date getVisitTimeOut()
    {
        return this.visitTimeOut;
    }

    public void setVisitTimeOut( Date visitTimeOut )
    {
        this.visitTimeOut = visitTimeOut;
    }

    public String getIp()
    {
        return this.ip;
    }

    public void setIp( String ip )
    {
        this.ip = ip;
    }

    public String getUrl()
    {
        return this.url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public String getBrowser()
    {
        return this.browser;
    }

    public void setBrowser( String browser )
    {
        this.browser = browser;
    }

    public String getScreen()
    {
        return screen;
    }

    public void setScreen( String screen )
    {
        this.screen = screen;
    }

    public String getSystem()
    {
        return this.system;
    }

    public void setSystem( String system )
    {
        this.system = system;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource( String source )
    {
        this.source = source;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost( String host )
    {
        this.host = host;
    }

    public Integer getVisitDay()
    {
        return visitDay;
    }

    public void setVisitDay( Integer visitDay )
    {
        this.visitDay = visitDay;
    }

    public Integer getVisitHour()
    {
        return visitHour;
    }

    public void setVisitHour( Integer visitHour )
    {
        this.visitHour = visitHour;
    }

    public Integer getVisitMonth()
    {
        return visitMonth;
    }

    public void setVisitMonth( Integer visitMonth )
    {
        this.visitMonth = visitMonth;
    }

    public Integer getVisitYear()
    {
        return visitYear;
    }

    public void setVisitYear( Integer visitYear )
    {
        this.visitYear = visitYear;
    }

    public Long getContentId()
    {
        return contentId;
    }

    public void setContentId( Long contentId )
    {
        this.contentId = contentId;
    }

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public String getLang()
    {
        return lang;
    }

    public void setLang( String lang )
    {
        this.lang = lang;
    }

    public String getRefferHost()
    {
        return refferHost;
    }

    public void setRefferHost( String refferHost )
    {
        this.refferHost = refferHost;
    }

}
