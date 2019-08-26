package cn.com.mjsoft.cms.site.bean;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SiteFileInfoBean
{
    private String fileName;

    private String entry; // 相对于完整path的路径

    private String lastModifyTime;

    private String creator;

    private long size;

    private String type;

    private boolean isDir;

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName( String fileName )
    {
        this.fileName = fileName;
    }

    public String getEntry()
    {
        return entry;
    }

    public void setEntry( String entry )
    {
        this.entry = entry;
    }

    public String getLastModifyTime()
    {
        return lastModifyTime;
    }

    public void setLastModifyTime( String lastModifyTime )
    {
        this.lastModifyTime = lastModifyTime;
    }

    public long getSize()
    {
        return size;
    }

    public void setSize( long size )
    {
        this.size = size;
    }

    public boolean isDir()
    {
        return isDir;
    }

    public void setDir( boolean isDir )
    {
        this.isDir = isDir;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder( this ).append( "entry", this.entry )
            .append( "type", this.type ).append( "lastModifyTime",
                this.lastModifyTime ).append( "creator", this.creator ).append(
                "fileName", this.fileName ).append( "dir", this.isDir() )
            .append( "size", this.size ).toString();
    }

}
