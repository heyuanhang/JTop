package cn.com.mjsoft.cms.content.dao.vo;

import java.sql.Timestamp;

import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "content_commend_push_info", id = "infoId", idMode = EntitySqlBridge.DB_IDENTITY )
public class ContentCommendPushInfo
{

    private Long infoId = Long.valueOf( -1 );
    private Long rowFlag;
    private Integer rowOrder;
    private Long contentId;
    private String title;
    private String url;
    private String img;
    private String summary;
    private Timestamp addTime;
    private Long modelId;
    private Long classId;
    private Long commendTypeId;
    private Integer orderFlag;
    private String typeFlag;
    private String commendFlag;
    private String commendMan;
    private String siteFlag;

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

    public String getSiteFlag()
    {
        return siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }
}
