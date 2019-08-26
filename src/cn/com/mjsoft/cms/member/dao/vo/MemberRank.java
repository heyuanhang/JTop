package cn.com.mjsoft.cms.member.dao.vo;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "member_rank", id = "rankId", idMode = EntitySqlBridge.DB_IDENTITY )
public class MemberRank
{
    private Long rankId;
    private String rankName;
    private Integer rankLevel = Integer.valueOf( 0 );
    private Long minScore = Long.valueOf( 0 );
    private Long maxScore = Long.valueOf( 0 );
    private String rankDesc;
    private Long siteId;

    public Long getRankId()
    {
        return this.rankId;
    }

    public void setRankId( Long rankId )
    {
        this.rankId = rankId;
    }

    public String getRankName()
    {
        return this.rankName;
    }

    public void setRankName( String rankName )
    {
        this.rankName = rankName;
    }

    public Integer getRankLevel()
    {
        return rankLevel;
    }

    public void setRankLevel( Integer rankLevel )
    {
        this.rankLevel = rankLevel;
    }

    public Long getMinScore()
    {
        return this.minScore;
    }

    public void setMinScore( Long minScore )
    {
        this.minScore = minScore;
    }

    public Long getMaxScore()
    {
        return this.maxScore;
    }

    public void setMaxScore( Long maxScore )
    {
        this.maxScore = maxScore;
    }

    public String getRankDesc()
    {
        return this.rankDesc;
    }

    public void setRankDesc( String rankDesc )
    {
        this.rankDesc = rankDesc;
    }

    public Long getSiteId()
    {
        return this.siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

}
