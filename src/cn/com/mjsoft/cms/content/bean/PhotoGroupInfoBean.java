package cn.com.mjsoft.cms.content.bean;

public class PhotoGroupInfoBean
{
    private Long contentId;
    private String groupSign;
    private Integer modelType;
    private String name;
    private Integer isCover;
    private String photoUrl;
    private String photoDesc;

    public PhotoGroupInfoBean()
    {
    }

    public Long getContentId()
    {
        return contentId;
    }

    public void setContentId( Long contentId )
    {
        this.contentId = contentId;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getPhotoUrl()
    {
        return this.photoUrl;
    }

    public void setPhotoUrl( String photoUrl )
    {
        this.photoUrl = photoUrl;
    }

    public String getPhotoDesc()
    {
        return this.photoDesc;
    }

    public void setPhotoDesc( String photoDesc )
    {
        this.photoDesc = photoDesc;
    }

    public String getGroupSign()
    {
        return groupSign;
    }

    public void setGroupSign( String groupSign )
    {
        this.groupSign = groupSign;
    }

    public Integer getIsCover()
    {
        return isCover;
    }

    public void setIsCover( Integer isCover )
    {
        this.isCover = isCover;
    }

    public Integer getModelType()
    {
        return modelType;
    }

    public void setModelType( Integer modelType )
    {
        this.modelType = modelType;
    }

}
