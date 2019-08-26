package cn.com.mjsoft.cms.weixin.bean.item;

import java.sql.Timestamp;

public class WxNewsItemInfo
{

    private Long infoId = Long.valueOf( -1 );
    private Long rowFlag;
    private Integer rowOrder;
    private Long contentId;
    private String resType;
    private String resTag;
    private String title;
    private String url;
    private String img;
    private Integer showCover;
    private String summary;
    private Timestamp addTime;
    private Long modelId;
    private Long classId;
    private Long commendTypeId;
    private Integer orderFlag;
    private String typeFlag;
    private String commendFlag;
    private String commendMan;
    private String articleText;
    private Timestamp createTime;
    private String siteFlag;
    private Integer isTranSucc;
    private String mediaId;
    private String wxCode;
    private Long wxId;

    public Long getInfoId()
    {
        return this.infoId;
    }

    public void setInfoId( Long infoId )
    {
        this.infoId = infoId;
    }

    public Long getRowFlag()
    {
        return rowFlag;
    }

    public void setRowFlag( Long rowFlag )
    {
        this.rowFlag = rowFlag;
    }

    public Integer getRowOrder()
    {
        return this.rowOrder;
    }

    public void setRowOrder( Integer rowOrder )
    {
        this.rowOrder = rowOrder;
    }

    public Long getContentId()
    {
        return this.contentId;
    }

    public void setContentId( Long contentId )
    {
        this.contentId = contentId;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public String getUrl()
    {
        return this.url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public String getSummary()
    {
        return this.summary;
    }

    public void setSummary( String summary )
    {
        this.summary = summary;
    }

    public Timestamp getAddTime()
    {
        return addTime;
    }

    public void setAddTime( Timestamp addTime )
    {
        this.addTime = addTime;
    }

    public Long getModelId()
    {
        return this.modelId;
    }

    public void setModelId( Long modelId )
    {
        this.modelId = modelId;
    }

    public Long getClassId()
    {
        return this.classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Long getCommendTypeId()
    {
        return this.commendTypeId;
    }

    public void setCommendTypeId( Long commendTypeId )
    {
        this.commendTypeId = commendTypeId;
    }

    public Integer getOrderFlag()
    {
        return this.orderFlag;
    }

    public void setOrderFlag( Integer orderFlag )
    {
        this.orderFlag = orderFlag;
    }

    public String getTypeFlag()
    {
        return this.typeFlag;
    }

    public void setTypeFlag( String typeFlag )
    {
        this.typeFlag = typeFlag;
    }

    public String getCommendFlag()
    {
        return this.commendFlag;
    }

    public void setCommendFlag( String commendFlag )
    {
        this.commendFlag = commendFlag;
    }

    public String getCommendMan()
    {
        return commendMan;
    }

    public void setCommendMan( String commendMan )
    {
        this.commendMan = commendMan;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg( String img )
    {
        this.img = img;
    }

    public String getArticleText()
    {
        return articleText;
    }

    public void setArticleText( String articleText )
    {
        this.articleText = articleText;
    }

    public Long getWxId()
    {
        return wxId;
    }

    public void setWxId( Long wxId )
    {
        this.wxId = wxId;
    }

    public String getSiteFlag()
    {
        return siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime( Timestamp createTime )
    {
        this.createTime = createTime;
    }

    public String getWxCode()
    {
        return wxCode;
    }

    public void setWxCode( String wxCode )
    {
        this.wxCode = wxCode;
    }

    public String getMediaId()
    {
        return mediaId;
    }

    public void setMediaId( String mediaId )
    {
        this.mediaId = mediaId;
    }

    public Integer getIsTranSucc()
    {
        return isTranSucc;
    }

    public void setIsTranSucc( Integer isTranSucc )
    {
        this.isTranSucc = isTranSucc;
    }

    public String getResType()
    {
        return resType;
    }

    public void setResType( String resType )
    {
        this.resType = resType;
    }

    public String getResTag()
    {
        return resTag;
    }

    public void setResTag( String resTag )
    {
        this.resTag = resTag;
    }

    public Integer getShowCover()
    {
        return showCover;
    }

    public void setShowCover( Integer showCover )
    {
        this.showCover = showCover;
    }

}
