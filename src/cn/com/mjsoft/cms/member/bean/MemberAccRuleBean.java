package cn.com.mjsoft.cms.member.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.com.mjsoft.framework.util.StringUtil;

public class MemberAccRuleBean implements Serializable
{
 
    private static final long serialVersionUID = 8022016124827515698L;
    private Long accRuleId = Long.valueOf( -1 );
    private String accName;
    private String ruleDesc;
    private Long minScore = Long.valueOf( -1 );
    private Long minLever = Long.valueOf( 0 );
    private String roleIds;
    private Integer eft = Integer.valueOf( 1 );
    private Integer typeId;
    private Long siteId;

    // 业务字段
    private Set roleIdSet = null;

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

    public Set getRoleIdSet()
    {
        if( roleIdSet == null )
        {
            List ids = StringUtil.changeStringToList( roleIds, "," );

            roleIdSet = new HashSet();

            long rid = -1;

            for ( int i = 0; i < ids.size(); i++ )
            {
                rid = StringUtil.getLongValue( ( String ) ids.get( i ), -1 );

                if( rid > 0 )
                {
                    roleIdSet.add( Long.valueOf( rid ) );
                }
            }
        }

        return roleIdSet;
    }
}
