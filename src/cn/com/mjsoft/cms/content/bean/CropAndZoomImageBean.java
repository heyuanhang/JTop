package cn.com.mjsoft.cms.content.bean;

public class CropAndZoomImageBean
{
    private Long resId = Long.valueOf( -1 );
    private String imageSource;
    private Integer imageRotate;
    private Double viewPortW;
    private Double viewPortH;
    private Double selectorX;
    private Double selectorY;
    private Double selectorW;
    private Double selectorH;
    private Double imageX;
    private Double imageY;
    private Double imageW;
    private Double imageH;

    public Double getImageH()
    {
        return imageH;
    }

    public void setImageH( Double imageH )
    {
        this.imageH = imageH;
    }

    public Integer getImageRotate()
    {
        return imageRotate;
    }

    public void setImageRotate( Integer imageRotate )
    {
        this.imageRotate = imageRotate;
    }

    public String getImageSource()
    {
        return imageSource;
    }

    public void setImageSource( String imageSource )
    {
        this.imageSource = imageSource;
    }

    public Double getImageW()
    {
        return imageW;
    }

    public void setImageW( Double imageW )
    {
        this.imageW = imageW;
    }

    public Double getImageX()
    {
        return imageX;
    }

    public void setImageX( Double imageX )
    {
        this.imageX = imageX;
    }

    public Double getImageY()
    {
        return imageY;
    }

    public void setImageY( Double imageY )
    {
        this.imageY = imageY;
    }

    public Double getSelectorH()
    {
        return selectorH;
    }

    public void setSelectorH( Double selectorH )
    {
        this.selectorH = selectorH;
    }

    public Double getSelectorW()
    {
        return selectorW;
    }

    public void setSelectorW( Double selectorW )
    {
        this.selectorW = selectorW;
    }

    public Double getSelectorX()
    {
        return selectorX;
    }

    public void setSelectorX( Double selectorX )
    {
        this.selectorX = selectorX;
    }

    public Double getSelectorY()
    {
        return selectorY;
    }

    public void setSelectorY( Double selectorY )
    {
        this.selectorY = selectorY;
    }

    public Double getViewPortH()
    {
        return viewPortH;
    }

    public void setViewPortH( Double viewPortH )
    {
        this.viewPortH = viewPortH;
    }

    public Double getViewPortW()
    {
        return viewPortW;
    }

    public void setViewPortW( Double viewPortW )
    {
        this.viewPortW = viewPortW;
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
