package cn.com.mjsoft.cms.content.bean;

import java.sql.Timestamp;
import java.util.Date;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;

public class ContentMainInfoBean
{
    private Long contentId = Long.valueOf( -1 );
    private Long modelId = Long.valueOf( -1 );
    private Long classId = Long.valueOf( -1 );
    private Long refCid;
    private Long linkCid;
    private String title;
    private String simpleTitle;
    private String shortTitle;
    private String titleStyle;
    private String simpleTitleStyle;
    private String author;
    private String creator;
    private String orgCode;
    private Float boost = 1.0F;
    private String summary;
    private Timestamp addTime = new Timestamp( DateAndTimeUtil.clusterTimeMillis() );// 当前时间
    private Long clickMonthCount = Long.valueOf( 0 );
    private Long clickWeekCount = Long.valueOf( 0 );
    private Long clickDayCount = Long.valueOf( 0 );
    private Long clickCount = Long.valueOf( 0 );
    private Long commMonthCount = Long.valueOf( 0 );
    private Long commWeekCount = Long.valueOf( 0 );
    private Long commDayCount = Long.valueOf( 0 );
    private Long commCount = Long.valueOf( 0 );
    private Long supportCount = Long.valueOf( 0 );
    private Long againstCount = Long.valueOf( 0 );
    private String outLink;
    private String homeImage;
    private String classImage;
    private String channelImage;
    private String contentImage;
    private String systemHandleTime;
    private String especialTemplateUrl;
    private String staticPageUrl;
    private Integer produceType;
    private Integer censorState;
    private Integer isPageContent = Constant.COMMON.OFF;// 默认不是文章类型,系统将不考虑内容分页
    private Integer isSystemOrder;
    private Double orderIdFlag;
    private String keywords;
    private String tagKey;
    private Long pubDateSysDT;
    private Date appearStartDateTime;
    private Date appearEndDateTime;
    private Integer homeImgFlag = Integer.valueOf( 0 );
    private Integer classImgFlag = Integer.valueOf( 0 );
    private Integer channelImgFlag = Integer.valueOf( 0 );
    private Integer contentImgFlag = Integer.valueOf( 0 );
    private String relateIds;
    private String relateSurvey;
    private Integer commendFlag;
    private String typeFlag;
    private Integer topFlag = Constant.COMMON.OFF;// 默认非置顶 ;
    private Integer otherFlag = Constant.COMMON.OFF;
    private Long siteId;
    private Integer allowCommend;

    public Timestamp getAddTime()
    {
        return addTime;
    }

    public void setAddTime( Timestamp addTime )
    {
        this.addTime = addTime;
    }

    public Long getAgainstCount()
    {
        return againstCount;
    }

    public void setAgainstCount( Long againstCount )
    {
        this.againstCount = againstCount;
    }

    public Integer getAllowCommend()
    {
        return allowCommend;
    }

    public void setAllowCommend( Integer allowCommend )
    {
        this.allowCommend = allowCommend;
    }

    public Date getAppearEndDateTime()
    {
        return appearEndDateTime;
    }

    public void setAppearEndDateTime( Date appearEndDateTime )
    {
        this.appearEndDateTime = appearEndDateTime;
    }

    public Date getAppearStartDateTime()
    {
        return appearStartDateTime;
    }

    public void setAppearStartDateTime( Date appearStartDateTime )
    {
        this.appearStartDateTime = appearStartDateTime;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor( String author )
    {
        this.author = author;
    }

    public Integer getCensorState()
    {
        return censorState;
    }

    public void setCensorState( Integer censorState )
    {
        this.censorState = censorState;
    }

    public Integer getChannelImgFlag()
    {
        return channelImgFlag;
    }

    public void setChannelImgFlag( Integer channelImgFlag )
    {
        this.channelImgFlag = channelImgFlag;
    }

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Integer getClassImgFlag()
    {
        return classImgFlag;
    }

    public void setClassImgFlag( Integer classImgFlag )
    {
        this.classImgFlag = classImgFlag;
    }

    public Long getClickCount()
    {
        return clickCount;
    }

    public void setClickCount( Long clickCount )
    {
        this.clickCount = clickCount;
    }

    public Long getClickDayCount()
    {
        return clickDayCount;
    }

    public void setClickDayCount( Long clickDayCount )
    {
        this.clickDayCount = clickDayCount;
    }

    public Long getClickMonthCount()
    {
        return clickMonthCount;
    }

    public void setClickMonthCount( Long clickMonthCount )
    {
        this.clickMonthCount = clickMonthCount;
    }

    public Long getClickWeekCount()
    {
        return clickWeekCount;
    }

    public void setClickWeekCount( Long clickWeekCount )
    {
        this.clickWeekCount = clickWeekCount;
    }

    public Long getCommCount()
    {
        return commCount;
    }

    public void setCommCount( Long commCount )
    {
        this.commCount = commCount;
    }

    public Long getCommDayCount()
    {
        return commDayCount;
    }

    public void setCommDayCount( Long commDayCount )
    {
        this.commDayCount = commDayCount;
    }

    public Integer getCommendFlag()
    {
        return commendFlag;
    }

    public void setCommendFlag( Integer commendFlag )
    {
        this.commendFlag = commendFlag;
    }

    public Long getCommMonthCount()
    {
        return commMonthCount;
    }

    public void setCommMonthCount( Long commMonthCount )
    {
        this.commMonthCount = commMonthCount;
    }

    public Long getCommWeekCount()
    {
        return commWeekCount;
    }

    public void setCommWeekCount( Long commWeekCount )
    {
        this.commWeekCount = commWeekCount;
    }

    public Long getContentId()
    {
        return contentId;
    }

    public void setContentId( Long contentId )
    {
        this.contentId = contentId;
    }

    public Integer getContentImgFlag()
    {
        return contentImgFlag;
    }

    public void setContentImgFlag( Integer contentImgFlag )
    {
        this.contentImgFlag = contentImgFlag;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public String getEspecialTemplateUrl()
    {
        return especialTemplateUrl;
    }

    public void setEspecialTemplateUrl( String especialTemplateUrl )
    {
        this.especialTemplateUrl = especialTemplateUrl;
    }

    public Integer getHomeImgFlag()
    {
        return homeImgFlag;
    }

    public void setHomeImgFlag( Integer homeImgFlag )
    {
        this.homeImgFlag = homeImgFlag;
    }

    public Integer getIsSystemOrder()
    {
        return isSystemOrder;
    }

    public void setIsSystemOrder( Integer isSystemOrder )
    {
        this.isSystemOrder = isSystemOrder;
    }

    public String getKeywords()
    {
        return keywords;
    }

    public void setKeywords( String keywords )
    {
        this.keywords = keywords;
    }

    public Long getModelId()
    {
        return modelId;
    }

    public void setModelId( Long modelId )
    {
        this.modelId = modelId;
    }

    public Double getOrderIdFlag()
    {
        return orderIdFlag;
    }

    public void setOrderIdFlag( Double orderIdFlag )
    {
        this.orderIdFlag = orderIdFlag;
    }

    public Integer getProduceType()
    {
        return produceType;
    }

    public void setProduceType( Integer produceType )
    {
        this.produceType = produceType;
    }

    public String getStaticPageUrl()
    {
        return staticPageUrl;
    }

    public void setStaticPageUrl( String staticPageUrl )
    {
        this.staticPageUrl = staticPageUrl;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary( String summary )
    {
        this.summary = summary;
    }

    public Long getSupportCount()
    {
        return supportCount;
    }

    public void setSupportCount( Long supportCount )
    {
        this.supportCount = supportCount;
    }

    public String getSystemHandleTime()
    {
        return systemHandleTime;
    }

    public void setSystemHandleTime( String systemHandleTime )
    {
        this.systemHandleTime = systemHandleTime;
    }

    public String getTagKey()
    {
        return tagKey;
    }

    public void setTagKey( String tagKey )
    {
        this.tagKey = tagKey;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public Integer getTopFlag()
    {
        return topFlag;
    }

    public void setTopFlag( Integer topFlag )
    {
        this.topFlag = topFlag;
    }

    public String getTypeFlag()
    {
        return typeFlag;
    }

    public void setTypeFlag( String typeFlag )
    {
        this.typeFlag = typeFlag;
    }

    public String getShortTitle()
    {
        return shortTitle;
    }

    public void setShortTitle( String shortTitle )
    {
        this.shortTitle = shortTitle;
    }

    public String getSimpleTitle()
    {
        return simpleTitle;
    }

    public void setSimpleTitle( String simpleTitle )
    {
        this.simpleTitle = simpleTitle;
    }

    public String getSimpleTitleStyle()
    {
        return simpleTitleStyle;
    }

    public void setSimpleTitleStyle( String simpleTitleStyle )
    {
        this.simpleTitleStyle = simpleTitleStyle;
    }

    public String getTitleStyle()
    {
        return titleStyle;
    }

    public void setTitleStyle( String titleStyle )
    {
        this.titleStyle = titleStyle;
    }

    public String getChannelImage()
    {
        return channelImage;
    }

    public void setChannelImage( String channelImage )
    {
        this.channelImage = channelImage;
    }

    public String getClassImage()
    {
        return classImage;
    }

    public void setClassImage( String classImage )
    {
        this.classImage = classImage;
    }

    public String getContentImage()
    {
        return contentImage;
    }

    public void setContentImage( String contentImage )
    {
        this.contentImage = contentImage;
    }

    public String getHomeImage()
    {
        return homeImage;
    }

    public void setHomeImage( String homeImage )
    {
        this.homeImage = homeImage;
    }

    public Integer getIsPageContent()
    {
        return isPageContent;
    }

    public void setIsPageContent( Integer isPageContent )
    {
        this.isPageContent = isPageContent;
    }

    public Integer getOtherFlag()
    {
        return otherFlag;
    }

    public void setOtherFlag( Integer otherFlag )
    {
        this.otherFlag = otherFlag;
    }

    public Long getRefCid()
    {
        return refCid;
    }

    public void setRefCid( Long refCid )
    {
        this.refCid = refCid;
    }

    public Long getLinkCid()
    {
        return linkCid;
    }

    public void setLinkCid( Long linkCid )
    {
        this.linkCid = linkCid;
    }

    public String getRelateIds()
    {
        return relateIds;
    }

    public void setRelateIds( String relateIds )
    {
        this.relateIds = relateIds;
    }

    public String getRelateSurvey()
    {
        return relateSurvey;
    }

    public void setRelateSurvey( String relateSurvey )
    {
        this.relateSurvey = relateSurvey;
    }

    public Long getPubDateSysDT()
    {
        return pubDateSysDT;
    }

    public void setPubDateSysDT( Long pubDateSysDT )
    {
        this.pubDateSysDT = pubDateSysDT;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public String getOutLink()
    {
        return outLink;
    }

    public void setOutLink( String outLink )
    {
        this.outLink = outLink;
    }

    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode( String orgCode )
    {
        this.orgCode = orgCode;
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
