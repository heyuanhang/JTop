package cn.com.mjsoft.cms.publish.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "publish_rule", id = "ruleId", idMode = EntitySqlBridge.DB_IDENTITY )
public class PublishRule
{
    private Long ruleId = Long.valueOf( -1 );
    private Long siteId = Long.valueOf( -1 );
    private String ruleName;
    private Integer type;
    private String savePathRule;
    private String savePathParam;
    private String fileNameRule;
    private String fileNameParam;
    private String suffixRule;

    private String pagePathParam;
    private String pagePathRule;
    private String pageFileNameRule;
    private String pageFileNameParam;

    private Integer needPage;

    public Long getRuleId()
    {
        return this.ruleId;
    }

    public void setRuleId( Long ruleId )
    {
        this.ruleId = ruleId;
    }

    public String getRuleName()
    {
        return this.ruleName;
    }

    public void setRuleName( String ruleName )
    {
        this.ruleName = ruleName;
    }

    public Integer getType()
    {
        return this.type;
    }

    public void setType( Integer type )
    {
        this.type = type;
    }

    public String getSavePathRule()
    {
        return this.savePathRule;
    }

    public void setSavePathRule( String savePathRule )
    {
        this.savePathRule = savePathRule;
    }

    public String getFileNameRule()
    {
        return this.fileNameRule;
    }

    public void setFileNameRule( String fileNameRule )
    {
        this.fileNameRule = fileNameRule;
    }

    public String getSuffixRule()
    {
        return this.suffixRule;
    }

    public void setSuffixRule( String suffixRule )
    {
        this.suffixRule = suffixRule;
    }

    public String getPageFileNameParam()
    {
        return pageFileNameParam;
    }

    public void setPageFileNameParam( String pageFileNameParam )
    {
        this.pageFileNameParam = pageFileNameParam;
    }

    public String getPageFileNameRule()
    {
        return pageFileNameRule;
    }

    public void setPageFileNameRule( String pageFileNameRule )
    {
        this.pageFileNameRule = pageFileNameRule;
    }

    public String getPagePathParam()
    {
        return pagePathParam;
    }

    public void setPagePathParam( String pagePathParam )
    {
        this.pagePathParam = pagePathParam;
    }

    public String getPagePathRule()
    {
        return pagePathRule;
    }

    public void setPagePathRule( String pagePathRule )
    {
        this.pagePathRule = pagePathRule;
    }

    public Integer getNeedPage()
    {
        return needPage;
    }

    public void setNeedPage( Integer needPage )
    {
        this.needPage = needPage;
    }

    public String getFileNameParam()
    {
        return fileNameParam;
    }

    public void setFileNameParam( String fileNameParam )
    {
        this.fileNameParam = fileNameParam;
    }

    public String getSavePathParam()
    {
        return savePathParam;
    }

    public void setSavePathParam( String savePathParam )
    {
        this.savePathParam = savePathParam;
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
