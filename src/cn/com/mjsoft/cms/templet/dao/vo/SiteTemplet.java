package cn.com.mjsoft.cms.templet.dao.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "sitetemplet", id = "templetId", idMode = EntitySqlBridge.DB_IDENTITY )
public class SiteTemplet
{

    private Long templetId;

    private String templetFileName;// ID

    private String siteName;

    private String templetDisplayName;

    private String templetFullPath; // 完整path

    private String relatedTempletFilePath;

    private Integer type;// 1.栏目展示模板 2.内容展示模板

    private String templetDesc;

    public String getSiteName()
    {
        return siteName;
    }

    public void setSiteName( String siteName )
    {
        this.siteName = siteName;
    }

    public String getTempletDesc()
    {
        return templetDesc;
    }

    public void setTempletDesc( String templetDesc )
    {
        this.templetDesc = templetDesc;
    }

    public String getTempletFileName()
    {
        return templetFileName;
    }

    public void setTempletFileName( String templetFileName )
    {
        this.templetFileName = templetFileName;
    }

    public String getTempletFullPath()
    {
        return templetFullPath;
    }

    public void setTempletFullPath( String templetFullPath )
    {
        this.templetFullPath = templetFullPath;
    }

    public String getTempletDisplayName()
    {
        return templetDisplayName;
    }

    public void setTempletDisplayName( String templetDisplayName )
    {
        this.templetDisplayName = templetDisplayName;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType( Integer type )
    {
        this.type = type;
    }

    public String getRelatedTempletFilePath()
    {
        return relatedTempletFilePath;
    }

    public void setRelatedTempletFilePath( String relatedTempletFilePath )
    {
        this.relatedTempletFilePath = relatedTempletFilePath;
    }

    public Long getTempletId()
    {
        return templetId;
    }

    public void setTempletId( Long templetId )
    {
        this.templetId = templetId;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder( this ).append( "templetFullPath", this.templetFullPath )
            .append( "templetFileName", this.templetFileName ).append( "relatedTempletFilePath",
                this.relatedTempletFilePath ).append( "type", this.type ).append(
                "templetDisplayName", this.templetDisplayName ).append( "templetDesc",
                this.templetDesc ).append( "siteName", this.siteName ).append( "templetId",
                this.templetId ).toString();
    }

}
