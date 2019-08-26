package cn.com.mjsoft.cms.templet.bean;

import java.io.File;

import org.apache.commons.lang.builder.ToStringBuilder;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.util.StringUtil;

public class TempletFileBean
{

    private Long templetId = Long.valueOf( -1 );

    private String siteName;

    private String templetDisplayName;

    private String templetPath; // 并非存到数据库的path,不包括文件名

    private String templetFullPath = ""; // 完整path

    private String templetType;// 1.栏目展示模板 2.内容展示模板

    private String templetDesc;

    private String templetFileName;

    public Long getTempletId()
    {
        return templetId;
    }

    public void setTempletId( Long templetId )
    {
        this.templetId = templetId;
    }

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

    public String getTempletDisplayName()
    {
        return templetDisplayName;
    }

    public void setTempletDisplayName( String templetDisplayName )
    {
        this.templetDisplayName = templetDisplayName;
    }

    public String getTempletPath()
    {
        return templetPath;
    }

    public void setTempletPath( String templetPath )
    {
        this.templetPath = templetPath;
    }

    public String getTempletType()
    {
        return templetType;
    }

    public void setTempletType( String templetType )
    {
        this.templetType = templetType;
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

    /** ************************ 扩展数据 *********************** */

    /**
     * 取得当前模板的URL数据
     */
    public String getTempletLink()
    {

        return Constant.TEMPLET.SYSTEM_SITE_ROOT_FLAG
            + "/"
            + this.siteName
            + "/"
            + StringUtil.replaceString( this.templetFullPath, File.separator, "/",
                false, false );
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder( this ).append( "templetFullPath",
            this.templetFullPath ).append( "templetFileName",
            this.templetFileName ).append( "templetType", this.templetType )
            .append( "templetPath", this.templetPath ).append(
                "templetDisplayName", this.templetDisplayName ).append(
                "templetDesc", this.templetDesc ).append( "siteName",
                this.siteName ).append( "templetId", this.templetId )
            .toString();
    }

}
