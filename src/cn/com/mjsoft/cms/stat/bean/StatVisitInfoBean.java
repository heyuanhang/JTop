package cn.com.mjsoft.cms.stat.bean;

import java.util.Date;

public class StatVisitInfoBean
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
