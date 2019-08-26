package cn.com.mjsoft.cms.content.bean;

public class UploadImageJsonBean
{
    private Long resId = Long.valueOf( -1 );

    private String resizeImageUrl;

    private String imageUrl;

    private String oldImageUrl;

    private String relatePath;

    private String imageName;

    private String genName;

    private String pefixDir;

    private Integer height;

    private Integer width;

    public String getRelatePath()
    {
        return relatePath;
    }

    public void setRelatePath( String relatePath )
    {
        this.relatePath = relatePath;
    }

    public String getResizeImageUrl()
    {
        return resizeImageUrl;
    }

    public void setResizeImageUrl( String resizeImageUrl )
    {
        this.resizeImageUrl = resizeImageUrl;
    }

    public String getImageName()
    {
        return imageName;
    }

    public void setImageName( String imageName )
    {
        this.imageName = imageName;
    }

    public String getGenName()
    {
        return genName;
    }

    public void setGenName( String genName )
    {
        this.genName = genName;
    }

    public String getPefixDir()
    {
        return pefixDir;
    }

    public void setPefixDir( String pefixDir )
    {
        this.pefixDir = pefixDir;
    }

    public Integer getHeight()
    {
        return height;
    }

    public void setHeight( Integer height )
    {
        this.height = height;
    }

    public Integer getWidth()
    {
        return width;
    }

    public void setWidth( Integer width )
    {
        this.width = width;
    }

    public String getOldImageUrl()
    {
        return oldImageUrl;
    }

    public void setOldImageUrl( String oldImageUrl )
    {
        this.oldImageUrl = oldImageUrl;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl( String imageUrl )
    {
        this.imageUrl = imageUrl;
    }

    public Long getResId()
    {
        return resId;
    }

    public void setResId( Long resId )
    {
        this.resId = resId;
    }

}
