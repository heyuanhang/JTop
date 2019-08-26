package cn.com.mjsoft.cms.member.bean;

public class MemberScoreActBean
{
    private Long saId;
    private String actName;
    private Integer actFlag;
    private Integer actScore;
    private String targetCmd;
    private String actClass;
    private String actDesc;
    private Long siteId;

    public Long getSaId()
    {
        return this.saId;
    }

    public void setSaId( Long saId )
    {
        this.saId = saId;
    }

    public String getActName()
    {
        return this.actName;
    }

    public void setActName( String actName )
    {
        this.actName = actName;
    }

    public Integer getActScore()
    {
        return this.actScore;
    }

    public void setActScore( Integer actScore )
    {
        this.actScore = actScore;
    }

    public String getTargetCmd()
    {
        return this.targetCmd;
    }

    public void setTargetCmd( String targetCmd )
    {
        this.targetCmd = targetCmd;
    }

    public String getActClass()
    {
        return this.actClass;
    }

    public void setActClass( String actClass )
    {
        this.actClass = actClass;
    }

    public String getActDesc()
    {
        return this.actDesc;
    }

    public void setActDesc( String actDesc )
    {
        this.actDesc = actDesc;
    }

    public Integer getActFlag()
    {
        return actFlag;
    }

    public void setActFlag( Integer actFlag )
    {
        this.actFlag = actFlag;
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
