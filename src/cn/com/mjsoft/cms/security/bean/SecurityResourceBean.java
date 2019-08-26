package cn.com.mjsoft.cms.security.bean;

import cn.com.mjsoft.cms.common.Constant;

public class SecurityResourceBean implements java.io.Serializable
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
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR );
            // + Constant.SITE_CHANNEL.HTML_BLANK_CHAR );
        }

        if( this.isLeaf.intValue() == 1 )
        {
            buf.append( "<img id='img" + this.linearOrderFlag ).append(
                "' src='../../core/style/default/images/control_small.png'/>" + this.resourceName );
        }
        else
        {
            buf.append( "<img id='img" + this.linearOrderFlag
                + "' src='../../core/style/default/images/t_small.png'/>" + this.resourceName );
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
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
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

    public int hashCode()
    {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ( ( creator == null ) ? 0 : creator.hashCode() );
        result = PRIME * result + ( ( dataProtectType == null ) ? 0 : dataProtectType.hashCode() );
        result = PRIME * result + ( ( dataTypeSecId == null ) ? 0 : dataTypeSecId.hashCode() );
        result = PRIME * result + ( ( icon == null ) ? 0 : icon.hashCode() );
        result = PRIME * result + ( ( isLastChild == null ) ? 0 : isLastChild.hashCode() );
        result = PRIME * result + ( ( isLeaf == null ) ? 0 : isLeaf.hashCode() );
        result = PRIME * result + ( ( layer == null ) ? 0 : layer.hashCode() );
        result = PRIME * result + ( ( linearOrderFlag == null ) ? 0 : linearOrderFlag.hashCode() );
        result = PRIME * result + ( ( parentResId == null ) ? 0 : parentResId.hashCode() );
        result = PRIME * result + ( ( parentResName == null ) ? 0 : parentResName.hashCode() );
        result = PRIME * result + ( ( resourceDesc == null ) ? 0 : resourceDesc.hashCode() );
        result = PRIME * result + ( ( resourceName == null ) ? 0 : resourceName.hashCode() );
        result = PRIME * result + ( ( resourceType == null ) ? 0 : resourceType.hashCode() );
        result = PRIME * result + ( ( secResId == null ) ? 0 : secResId.hashCode() );
        result = PRIME * result + ( ( sysFlag == null ) ? 0 : sysFlag.hashCode() );
        result = PRIME * result + ( ( target == null ) ? 0 : target.hashCode() );
        return result;
    }

    public boolean equals( Object obj )
    {
        if( this == obj )
            return true;
        if( obj == null )
            return false;
        if( getClass() != obj.getClass() )
            return false;
        final SecurityResourceBean other = ( SecurityResourceBean ) obj;
        if( creator == null )
        {
            if( other.creator != null )
                return false;
        }
        else if( !creator.equals( other.creator ) )
            return false;
        if( dataProtectType == null )
        {
            if( other.dataProtectType != null )
                return false;
        }
        else if( !dataProtectType.equals( other.dataProtectType ) )
            return false;
        if( dataTypeSecId == null )
        {
            if( other.dataTypeSecId != null )
                return false;
        }
        else if( !dataTypeSecId.equals( other.dataTypeSecId ) )
            return false;
        if( icon == null )
        {
            if( other.icon != null )
                return false;
        }
        else if( !icon.equals( other.icon ) )
            return false;
        if( isLastChild == null )
        {
            if( other.isLastChild != null )
                return false;
        }
        else if( !isLastChild.equals( other.isLastChild ) )
            return false;
        if( isLeaf == null )
        {
            if( other.isLeaf != null )
                return false;
        }
        else if( !isLeaf.equals( other.isLeaf ) )
            return false;
        if( layer == null )
        {
            if( other.layer != null )
                return false;
        }
        else if( !layer.equals( other.layer ) )
            return false;
        if( linearOrderFlag == null )
        {
            if( other.linearOrderFlag != null )
                return false;
        }
        else if( !linearOrderFlag.equals( other.linearOrderFlag ) )
            return false;
        if( parentResId == null )
        {
            if( other.parentResId != null )
                return false;
        }
        else if( !parentResId.equals( other.parentResId ) )
            return false;
        if( parentResName == null )
        {
            if( other.parentResName != null )
                return false;
        }
        else if( !parentResName.equals( other.parentResName ) )
            return false;
        if( resourceDesc == null )
        {
            if( other.resourceDesc != null )
                return false;
        }
        else if( !resourceDesc.equals( other.resourceDesc ) )
            return false;
        if( resourceName == null )
        {
            if( other.resourceName != null )
                return false;
        }
        else if( !resourceName.equals( other.resourceName ) )
            return false;
        if( resourceType == null )
        {
            if( other.resourceType != null )
                return false;
        }
        else if( !resourceType.equals( other.resourceType ) )
            return false;
        if( secResId == null )
        {
            if( other.secResId != null )
                return false;
        }
        else if( !secResId.equals( other.secResId ) )
            return false;
        if( sysFlag == null )
        {
            if( other.sysFlag != null )
                return false;
        }
        else if( !sysFlag.equals( other.sysFlag ) )
            return false;
        if( target == null )
        {
            if( other.target != null )
                return false;
        }
        else if( !target.equals( other.target ) )
            return false;
        return true;
    }

}
