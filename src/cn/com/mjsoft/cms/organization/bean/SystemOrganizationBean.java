package cn.com.mjsoft.cms.organization.bean;

import cn.com.mjsoft.cms.common.Constant;

public class SystemOrganizationBean
{
    private Long orgId;
    private Long parentId;
    private String orgFlag;
    private String orgName;
    private String orgDesc;
    private Integer isLeaf;
    private Integer layer;
    private String linearOrderFlag;
    private String directorIds;
    private Long orgBossId;
    private String creator;

    public Long getOrgId()
    {
        return this.orgId;
    }

    public void setOrgId( Long orgId )
    {
        this.orgId = orgId;
    }

    public Long getParentId()
    {
        return this.parentId;
    }

    public void setParentId( Long parentId )
    {
        this.parentId = parentId;
    }

    public String getOrgFlag()
    {
        return this.orgFlag;
    }

    public void setOrgFlag( String orgFlag )
    {
        this.orgFlag = orgFlag;
    }

    public String getOrgName()
    {
        return this.orgName;
    }

    public void setOrgName( String orgName )
    {
        this.orgName = orgName;
    }

    public String getOrgDesc()
    {
        return this.orgDesc;
    }

    public void setOrgDesc( String orgDesc )
    {
        this.orgDesc = orgDesc;
    }

    public Integer getLayer()
    {
        return this.layer;
    }

    public void setLayer( Integer layer )
    {
        this.layer = layer;
    }

    public String getLinearOrderFlag()
    {
        return this.linearOrderFlag;
    }

    public void setLinearOrderFlag( String linearOrderFlag )
    {
        this.linearOrderFlag = linearOrderFlag;
    }

    public String getDirectorIds()
    {
        return this.directorIds;
    }

    public void setDirectorIds( String directorIds )
    {
        this.directorIds = directorIds;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public Integer getIsLeaf()
    {
        return isLeaf;
    }

    public void setIsLeaf( Integer isLeaf )
    {
        this.isLeaf = isLeaf;
    }

    public Long getOrgBossId()
    {
        return orgBossId;
    }

    public void setOrgBossId( Long orgBossId )
    {
        this.orgBossId = orgBossId;
    }

    // 业务方法

    public String getUiName()
    {
        StringBuilder buf = new StringBuilder();
        for ( int i = 0; i < ( layer.intValue() - 1 ); i++ )
        {
            buf.append( Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR );
        }

        String layerUIClassName = null;
        if( this.isLeaf.intValue() == 1 )
        {
            layerUIClassName = buf + "<img id='img" + this.linearOrderFlag
                + "' src='../../core/style/default/images/control_small.png'/>"
                + this.orgName;
        }
        else
        {

            layerUIClassName = buf + "<img id='img" + this.linearOrderFlag
                + "' src='../../core/style/default/images/t_small.png'/>"
                + this.orgName;
        }

        return layerUIClassName;
    }

    public String getUiLayerName()
    {
        StringBuilder buf = new StringBuilder();
        for ( int i = 0; i < ( layer.intValue() - 1 ); i++ )
        {
            buf.append( Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR );
        }

        String layerUIClassName = null;
        if( this.isLeaf.intValue() == 1 && this.layer.intValue() > 1 )
        {
            layerUIClassName = buf + this.orgName;
        }
        else
        {

            layerUIClassName = buf + this.orgName;
        }

        return layerUIClassName;
    }

}
