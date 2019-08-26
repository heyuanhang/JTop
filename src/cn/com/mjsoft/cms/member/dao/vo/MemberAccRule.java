package cn.com.mjsoft.cms.member.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "member_acc_rule", id = "accRuleId", idMode = EntitySqlBridge.DB_IDENTITY )
public class MemberAccRule
{

    private Long accRuleId;
    private String accName;
    private String ruleDesc;
    private Long minScore;
    private Long minLever;
    private String roleIds;
    private Integer eft;
    private Integer typeId;
    private Long siteId;

    public Long getAccRuleId()
    {
        return this.accRuleId;
    }

    public void setAccRuleId( Long accRuleId )
    {
        this.accRuleId = accRuleId;
    }

    public Long getMinScore()
    {
        return this.minScore;
    }

    public void setMinScore( Long minScore )
    {
        this.minScore = minScore;
    }

    public Long getMinLever()
    {
        return this.minLever;
    }

    public void setMinLever( Long minLever )
    {
        this.minLever = minLever;
    }

    public String getRoleIds()
    {
        return this.roleIds;
    }

    public void setRoleIds( String roleIds )
    {
        this.roleIds = roleIds;
    }

    public String getAccName()
    {
        return accName;
    }

    public void setAccName( String accName )
    {
        this.accName = accName;
    }

    public Integer getEft()
    {
        return eft;
    }

    public void setEft( Integer eft )
    {
        this.eft = eft;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public String getRuleDesc()
    {
        return ruleDesc;
    }

    public void setRuleDesc( String ruleDesc )
    {
        this.ruleDesc = ruleDesc;
    }

    public Integer getTypeId()
    {
        return typeId;
    }

    public void setTypeId( Integer typeId )
    {
        this.typeId = typeId;
    }

}
