package cn.com.mjsoft.cms.pick.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "pick_content_rule", id = "pickCfgId", idMode = EntitySqlBridge.DB_IDENTITY )
public class PickContentRule
{

    private Long pickCfgId;
    private String configName;
    private Long extModelId;
    private String listHeadUrlRule;
    private String listUrlRule;
    private String contentUrlRule;
    private String contentPageUrlRule;
    private String prefixSiteUrl;
    private String configDesc;
    private String titleStart;
    private String titleEnd;
    private String contentStart;
    private String contentEnd;
    private String summaryStart;
    private String summaryEnd;
    private String addDateStart;
    private String addDateEnd;
    private String sourceStart;
    private String sourceEnd;
    private String authorStart;
    private String authorEnd;
    private String keywordStart;
    private String keywordEnd;
    private Integer htmlMode;
    private Long siteId;

    private String listStart;
    private String listEnd;

    private String timeFormat;

    public Long getPickCfgId()
    {
        return this.pickCfgId;
    }

    public void setPickCfgId( Long pickCfgId )
    {
        this.pickCfgId = pickCfgId;
    }

    public String getConfigName()
    {
        return this.configName;
    }

    public void setConfigName( String configName )
    {
        this.configName = configName;
    }

    public String getListUrlRule()
    {
        return this.listUrlRule;
    }

    public void setListUrlRule( String listUrlRule )
    {
        this.listUrlRule = listUrlRule;
    }

    public String getContentUrlRule()
    {
        return this.contentUrlRule;
    }

    public void setContentUrlRule( String contentUrlRule )
    {
        this.contentUrlRule = contentUrlRule;
    }

    public String getContentPageUrlRule()
    {
        return this.contentPageUrlRule;
    }

    public void setContentPageUrlRule( String contentPageUrlRule )
    {
        this.contentPageUrlRule = contentPageUrlRule;
    }

    public String getPrefixSiteUrl()
    {
        return this.prefixSiteUrl;
    }

    public void setPrefixSiteUrl( String prefixSiteUrl )
    {
        this.prefixSiteUrl = prefixSiteUrl;
    }

    public String getConfigDesc()
    {
        return this.configDesc;
    }

    public void setConfigDesc( String configDesc )
    {
        this.configDesc = configDesc;
    }

    public String getTitleStart()
    {
        return this.titleStart;
    }

    public void setTitleStart( String titleStart )
    {
        this.titleStart = titleStart;
    }

    public String getTitleEnd()
    {
        return this.titleEnd;
    }

    public void setTitleEnd( String titleEnd )
    {
        this.titleEnd = titleEnd;
    }

    public String getContentStart()
    {
        return this.contentStart;
    }

    public void setContentStart( String contentStart )
    {
        this.contentStart = contentStart;
    }

    public String getContentEnd()
    {
        return this.contentEnd;
    }

    public void setContentEnd( String contentEnd )
    {
        this.contentEnd = contentEnd;
    }

    public String getSummaryStart()
    {
        return this.summaryStart;
    }

    public void setSummaryStart( String summaryStart )
    {
        this.summaryStart = summaryStart;
    }

    public String getSummaryEnd()
    {
        return this.summaryEnd;
    }

    public void setSummaryEnd( String summaryEnd )
    {
        this.summaryEnd = summaryEnd;
    }

    public String getAddDateStart()
    {
        return this.addDateStart;
    }

    public void setAddDateStart( String addDateStart )
    {
        this.addDateStart = addDateStart;
    }

    public String getAddDateEnd()
    {
        return this.addDateEnd;
    }

    public void setAddDateEnd( String addDateEnd )
    {
        this.addDateEnd = addDateEnd;
    }

    public String getAuthorStart()
    {
        return this.authorStart;
    }

    public void setAuthorStart( String authorStart )
    {
        this.authorStart = authorStart;
    }

    public String getAuthorEnd()
    {
        return this.authorEnd;
    }

    public void setAuthorEnd( String authorEnd )
    {
        this.authorEnd = authorEnd;
    }

    public String getKeywordStart()
    {
        return this.keywordStart;
    }

    public void setKeywordStart( String keywordStart )
    {
        this.keywordStart = keywordStart;
    }

    public String getKeywordEnd()
    {
        return this.keywordEnd;
    }

    public void setKeywordEnd( String keywordEnd )
    {
        this.keywordEnd = keywordEnd;
    }

    public Long getSiteId()
    {
        return this.siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public String getSourceEnd()
    {
        return sourceEnd;
    }

    public void setSourceEnd( String sourceEnd )
    {
        this.sourceEnd = sourceEnd;
    }

    public String getSourceStart()
    {
        return sourceStart;
    }

    public void setSourceStart( String sourceStart )
    {
        this.sourceStart = sourceStart;
    }

    public Integer getHtmlMode()
    {
        return htmlMode;
    }

    public void setHtmlMode( Integer htmlMode )
    {
        this.htmlMode = htmlMode;
    }

    public String getListHeadUrlRule()
    {
        return listHeadUrlRule;
    }

    public void setListHeadUrlRule( String listHeadUrlRule )
    {
        this.listHeadUrlRule = listHeadUrlRule;
    }

    public Long getExtModelId()
    {
        return extModelId;
    }

    public void setExtModelId( Long extModelId )
    {
        this.extModelId = extModelId;
    }

    public String getListEnd()
    {
        return listEnd;
    }

    public void setListEnd( String listEnd )
    {
        this.listEnd = listEnd;
    }

    public String getListStart()
    {
        return listStart;
    }

    public void setListStart( String listStart )
    {
        this.listStart = listStart;
    }

    public String getTimeFormat()
    {
        return timeFormat;
    }

    public void setTimeFormat( String timeFormat )
    {
        this.timeFormat = timeFormat;
    }

}
