package cn.com.mjsoft.cms.channel.bean;

import cn.com.mjsoft.cms.common.Constant;

public class ContentCommendTypeJSONBean
{

    private Long commendTypeId = Long.valueOf( -1 );

    private String commendName;

    private Long classId = Long.valueOf( -9999 );// 全站通用

    private Integer isSpec;

    private String classLinerFlag;

    private Integer childClassMode = Constant.COMMON.ON;

    private String commFlag;

    private String typeDesc;

    private Integer mustCensor;

    private String creator;

    private String siteFlag;

    private Integer imageWidth;

    private Integer imageHeight;

    private String listTemplateUrl;

    private Integer listProduceType;

    private Long listPublishRuleId;

    private String listStaticUrl;

    // 业务属性
    private String url;

    public String getCommendName()
    {
        return commendName;
    }

    public void setCommendName( String commendName )
    {
        this.commendName = commendName;
    }

    public Long getCommendTypeId()
    {
        return commendTypeId;
    }

    public void setCommendTypeId( Long commendTypeId )
    {
        this.commendTypeId = commendTypeId;
    }

    public String getCommFlag()
    {
        return commFlag;
    }

    public void setCommFlag( String commFlag )
    {
        this.commFlag = commFlag;
    }

    public String getTypeDesc()
    {
        return typeDesc;
    }

    public void setTypeDesc( String typeDesc )
    {
        this.typeDesc = typeDesc;
    }

    public Integer getChildClassMode()
    {
        return childClassMode;
    }

    public void setChildClassMode( Integer childClassMode )
    {
        this.childClassMode = childClassMode;
    }

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Integer getIsSpec()
    {
        return isSpec;
    }

    public void setIsSpec( Integer isSpec )
    {
        this.isSpec = isSpec;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public String getClassLinerFlag()
    {
        return classLinerFlag;
    }

    public void setClassLinerFlag( String classLinerFlag )
    {
        this.classLinerFlag = classLinerFlag;
    }

    public Integer getMustCensor()
    {
        return mustCensor;
    }

    public void setMustCensor( Integer mustCensor )
    {
        this.mustCensor = mustCensor;
    }

    public String getSiteFlag()
    {
        return siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    public Integer getImageHeight()
    {
        return imageHeight;
    }

    public void setImageHeight( Integer imageHeight )
    {
        this.imageHeight = imageHeight;
    }

    public Integer getImageWidth()
    {
        return imageWidth;
    }

    public void setImageWidth( Integer imageWidth )
    {
        this.imageWidth = imageWidth;
    }

    public Integer getListProduceType()
    {
        return listProduceType;
    }

    public void setListProduceType( Integer listProduceType )
    {
        this.listProduceType = listProduceType;
    }

    public Long getListPublishRuleId()
    {
        return listPublishRuleId;
    }

    public void setListPublishRuleId( Long listPublishRuleId )
    {
        this.listPublishRuleId = listPublishRuleId;
    }

    public String getListStaticUrl()
    {
        return listStaticUrl;
    }

    public void setListStaticUrl( String listStaticUrl )
    {
        this.listStaticUrl = listStaticUrl;
    }

    public String getListTemplateUrl()
    {
        return listTemplateUrl;
    }

    public void setListTemplateUrl( String listTemplateUrl )
    {
        this.listTemplateUrl = listTemplateUrl;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

}
