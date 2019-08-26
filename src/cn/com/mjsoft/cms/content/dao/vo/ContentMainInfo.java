package cn.com.mjsoft.cms.content.dao.vo;

import java.sql.Timestamp;
import java.util.Date;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;

/**
 * 所有内容的共有系统必需属性
 * 
 * @author mjsoft
 * 
 */

@Table( name = "content_main_info", id = "contentId", idMode = EntitySqlBridge.DB_IDENTITY )
public class ContentMainInfo
{

    private Long contentId = Long.valueOf( -1 );
    private Long modelId = Long.valueOf( -1 );
    private Long classId = Long.valueOf( -1 );
    private Long refCid = Long.valueOf( -1 );
    private Long linkCid = Long.valueOf( -1 );
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

    private Long moodT1Count = Long.valueOf( 0 );
    private Long moodT2Count = Long.valueOf( 0 );
    private Long moodT3Count = Long.valueOf( 0 );
    private Long moodT4Count = Long.valueOf( 0 );
    private Long moodT5Count = Long.valueOf( 0 );
    private Long moodT6Count = Long.valueOf( 0 );
    private Long moodT7Count = Long.valueOf( 0 );
    private Long moodT8Count = Long.valueOf( 0 );
    private Long moodT9Count = Long.valueOf( 0 );
    private Long moodT10Count = Long.valueOf( 0 );

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
    private String relateIds = "";
    private String relateSurvey = "";
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

    public String getOrgCode()
    {
        return orgCode;
    }

    public void setOrgCode( String orgCode )
    {
        this.orgCode = orgCode;
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

    public Integer getOtherFlag()
    {
        return otherFlag;
    }

    public void setOtherFlag( Integer otherFlag )
    {
        this.otherFlag = otherFlag;
    }

    public Integer getIsPageContent()
    {
        return isPageContent;
    }

    public void setIsPageContent( Integer isPageContent )
    {
        this.isPageContent = isPageContent;
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

    public Long getMoodT10Count()
    {
        return moodT10Count;
    }

    public void setMoodT10Count( Long moodT10Count )
    {
        this.moodT10Count = moodT10Count;
    }

    public Long getMoodT1Count()
    {
        return moodT1Count;
    }

    public void setMoodT1Count( Long moodT1Count )
    {
        this.moodT1Count = moodT1Count;
    }

    public Long getMoodT2Count()
    {
        return moodT2Count;
    }

    public void setMoodT2Count( Long moodT2Count )
    {
        this.moodT2Count = moodT2Count;
    }

    public Long getMoodT3Count()
    {
        return moodT3Count;
    }

    public void setMoodT3Count( Long moodT3Count )
    {
        this.moodT3Count = moodT3Count;
    }

    public Long getMoodT4Count()
    {
        return moodT4Count;
    }

    public void setMoodT4Count( Long moodT4Count )
    {
        this.moodT4Count = moodT4Count;
    }

    public Long getMoodT5Count()
    {
        return moodT5Count;
    }

    public void setMoodT5Count( Long moodT5Count )
    {
        this.moodT5Count = moodT5Count;
    }

    public Long getMoodT6Count()
    {
        return moodT6Count;
    }

    public void setMoodT6Count( Long moodT6Count )
    {
        this.moodT6Count = moodT6Count;
    }

    public Long getMoodT7Count()
    {
        return moodT7Count;
    }

    public void setMoodT7Count( Long moodT7Count )
    {
        this.moodT7Count = moodT7Count;
    }

    public Long getMoodT8Count()
    {
        return moodT8Count;
    }

    public void setMoodT8Count( Long moodT8Count )
    {
        this.moodT8Count = moodT8Count;
    }

    public Long getMoodT9Count()
    {
        return moodT9Count;
    }

    public void setMoodT9Count( Long moodT9Count )
    {
        this.moodT9Count = moodT9Count;
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
