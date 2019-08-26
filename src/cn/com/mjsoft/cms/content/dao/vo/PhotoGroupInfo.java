package cn.com.mjsoft.cms.content.dao.vo;

import java.sql.Timestamp;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "content_assistant_photo_group", id = "", idMode = EntitySqlBridge.NO_KEY_ID )
public class PhotoGroupInfo
{

    private Long contentId = Long.valueOf( -1 );
    private String groupSign;
    private Integer modelType;
    private String photoName;
    private Integer isCover;
    private String url;
    private String photoDesc;
    private String outLinkUrl;
    private Integer width;
    private Integer height;
    private Timestamp photoAddTime;
    private Integer orderFlag;
    private Integer needMark = Integer.valueOf( 1 );

    public PhotoGroupInfo()
    {
    }

    public Long getContentId()
    {
        return this.contentId;
    }

    public void setContentId( Long contentId )
    {
        this.contentId = contentId;
    }

    public String getOutLinkUrl()
    {
        return outLinkUrl;
    }

    public void setOutLinkUrl( String outLinkUrl )
    {
        this.outLinkUrl = outLinkUrl;
    }

    public String getPhotoDesc()
    {
        return photoDesc;
    }

    public void setPhotoDesc( String photoDesc )
    {
        this.photoDesc = photoDesc;
    }

    public String getPhotoName()
    {
        return photoName;
    }

    public void setPhotoName( String photoName )
    {
        this.photoName = photoName;
    }

    public Integer getIsCover()
    {
        return isCover;
    }

    public void setIsCover( Integer isCover )
    {
        this.isCover = isCover;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public Timestamp getPhotoAddTime()
    {
        return photoAddTime;
    }

    public void setPhotoAddTime( Timestamp photoAddTime )
    {
        this.photoAddTime = photoAddTime;
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

    public Integer getOrderFlag()
    {
        return orderFlag;
    }

    public void setOrderFlag( Integer orderFlag )
    {
        this.orderFlag = orderFlag;
    }

    public String getGroupSign()
    {
        return groupSign;
    }

    public void setGroupSign( String groupSign )
    {
        this.groupSign = groupSign;
    }

    public Integer getNeedMark()
    {
        return needMark;
    }

    public void setNeedMark( Integer needMark )
    {
        this.needMark = needMark;
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
