package cn.com.mjsoft.cms.security.bean;

import cn.com.mjsoft.cms.common.Constant;

public class MemberSecurityResourceBean implements java.io.Serializable
{
    private static final long serialVersionUID = 6849894607043921831L;

    private Long secResId = Long.valueOf( -1 );

    private Long parentResId = Long.valueOf( -1 );

    private Long dataTypeSecId = Long.valueOf( -1 );

    private String resourceName;

    private String icon;

    private String resourceDesc;

    private Integer resourceType;

    private String sysFlag;

    private Integer useState;

    private Integer dataProtectType;

    private Long dataSecTypeId;

    private String creator;

    private String target;

    private String linearOrderFlag;

    private Integer layer;

    private Integer isLastChild;

    private Integer isLeaf;

    private Long siteId;

    // 扩展字段
    private String parentResName;

    // Constructors

    // Property accessors

    public Long getSecResId()
    {
        return this.secResId;
    }

    public void setSecResId( Long secResId )
    {
        this.secResId = secResId;
    }

    public String getResourceName()
    {
        return this.resourceName;
    }

    public void setResourceName( String resourceName )
    {
        this.resourceName = resourceName;
    }

    public Integer getResourceType()
    {
        return resourceType;
    }

    public void setResourceType( Integer resourceType )
    {
        this.resourceType = resourceType;
    }

    public Integer getIsLeaf()
    {
        return isLeaf;
    }

    public void setIsLeaf( Integer isLeaf )
    {
        this.isLeaf = isLeaf;
    }

    public String getCreator()
    {
        return this.creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public String getTarget()
    {
        return this.target;
    }

    public void setTarget( String target )
    {
        this.target = target;
    }

    public Long getParentResId()
    {
        return this.parentResId;
    }

    public void setParentResId( Long parentResId )
    {
        this.parentResId = parentResId;
    }

    public String getResourceDesc()
    {
        return resourceDesc;
    }

    public void setResourceDesc( String resourceDesc )
    {
        this.resourceDesc = resourceDesc;
    }

    public String getLinearOrderFlag()
    {
        return linearOrderFlag;
    }

    public void setLinearOrderFlag( String linearOrderFlag )
    {
        this.linearOrderFlag = linearOrderFlag;
    }

    public Long getDataSecTypeId()
    {
        return dataSecTypeId;
    }

    public void setDataSecTypeId( Long dataSecTypeId )
    {
        this.dataSecTypeId = dataSecTypeId;
    }

    // 以下为业务方法
    public Integer getDataProtectType()
    {
        return dataProtectType;
    }

    public void setDataProtectType( Integer dataProtectType )
    {
        this.dataProtectType = dataProtectType;
    }

    public Integer getIsLastChild()
    {
        return isLastChild;
    }

    public void setIsLastChild( Integer isLastChild )
    {
        this.isLastChild = isLastChild;
    }

    public Integer getLayer()
    {
        return layer;
    }

    public void setLayer( Integer layer )
    {
        this.layer = layer;
    }

    public Integer getUseState()
    {
        return useState;
    }

    public void setUseState( Integer useState )
    {
        this.useState = useState;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon( String icon )
    {
        this.icon = icon;
    }

    public String getResourceNameInfo()
    {
        // int size = this.layer.intValue();
        //
        // StringBuffer buf = new StringBuffer();
        //
        // for ( int i = 0; i < size; i++ )
        // {
        // buf.append( "&nbsp&nbsp" );
        // }
        //
        // return buf.toString() + this.resourceName;

        StringBuffer buf = new StringBuffer();
        for ( int i = 0; i < ( layer.intValue() - 1 ); i++ )
        {
            buf.append( Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR );
            // + Constant.SITE_CHANNEL.HTML_BLANK_CHAR );
        }

        if( this.isLeaf.intValue() == 1 )
        {
            buf.append( "<img id='img" + this.linearOrderFlag ).append(
                "' src='../../core/style/default/images/control_small.png'/>"
                    + this.resourceName );
        }
        else
        {
            buf.append( "<img id='img" + this.linearOrderFlag
                + "' src='../../core/style/default/images/t_small.png'/>"
                + this.resourceName );
        }

        return buf.toString();
    }

    public String getResourceNameBlank()
    {
        // int size = this.layer.intValue();
        //
        // StringBuffer buf = new StringBuffer();
        //
        // for ( int i = 0; i < size; i++ )
        // {
        // buf.append( "&nbsp&nbsp" );
        // }
        //
        // return buf.toString() + this.resourceName;

        StringBuffer buf = new StringBuffer();
        for ( int i = 0; i < ( layer.intValue() - 1 ); i++ )
        {
            buf.append( Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR );
            // + Constant.SITE_CHANNEL.HTML_BLANK_CHAR );
        }

        buf.append( this.resourceName );

        return buf.toString();
    }

    public String getResourceTypeInfo()
    {

        if( this.resourceType != null )
        {
            if( this.resourceType.shortValue() == 1 )
            {
                return "入口";
            }

            else if( this.resourceType.shortValue() == 2 )
            {
                return "模块";
            }

            else if( this.resourceType.shortValue() == 3 )
            {
                return "菜单";
            }

            else if( this.resourceType.shortValue() == 4 )
            {
                return "组合";
            }

            else if( this.resourceType.shortValue() == 5 )
            {
                return "链接";
            }
        }

        return "";

    }

    public String getUseStateInfo()
    {
        if( this.useState != null )
        {
            if( this.useState.shortValue() == 2 )
            {
                return "<font color='red'>停用</font>";
            }

            if( this.useState.shortValue() == 1 )
            {
                return "启用";
            }
        }

        return "";

    }

    public String getProtectTypeInfo()
    {

        if( this.dataProtectType != null )
        {
            if( this.dataProtectType.shortValue() == 1 )
            {
                return "功能";
            }

            if( this.dataProtectType.shortValue() == 2 )
            {
                return "数据";
            }
        }

        return "";

    }

    public String getParentResName()
    {
        return parentResName == null ? "无（为顶级资源）" : parentResName;
    }

    public void setParentResName( String parentResName )
    {
        this.parentResName = parentResName;
    }

    public String getSysFlag()
    {
        return sysFlag;
    }

    public void setSysFlag( String sysFlag )
    {
        this.sysFlag = sysFlag;
    }

    public Long getDataTypeSecId()
    {
        return dataTypeSecId;
    }

    public void setDataTypeSecId( Long dataTypeSecId )
    {
        this.dataTypeSecId = dataTypeSecId;
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
