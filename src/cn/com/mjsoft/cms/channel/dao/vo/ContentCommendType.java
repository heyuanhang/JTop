package cn.com.mjsoft.cms.channel.dao.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "content_commend_type", id = "commendTypeId", idMode = EntitySqlBridge.DB_IDENTITY )
public class ContentCommendType
{

    private Long commendTypeId = Long.valueOf( -1 );

    private String commendName;

    private Long classId = Long.valueOf( -9999 );// 全站通用

    private Integer isSpec = Constant.COMMON.OFF;// 默认不属专题类型

    private String classLinerFlag = "";

    private Integer childClassMode = Constant.COMMON.ON;

    private String commFlag;

    private Long modelId;

    private String typeDesc;

    private Integer mustCensor;

    private String creator;

    private String siteFlag;

    private Integer imageWidth;

    private Integer imageHeight;

    private String listTemplateUrl;

    private String mobListTemplateUrl;

    private String padListTemplateUrl;

    private Integer listProduceType = Constant.COMMON.OFF;// 默认动态

    private Long listPublishRuleId;

    private String listStaticUrl;

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

    public Integer getIsSpec()
    {
        return isSpec;
    }

    public void setIsSpec( Integer isSpec )
    {
        this.isSpec = isSpec;
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

    public String getMobListTemplateUrl()
    {
        return mobListTemplateUrl;
    }

    public void setMobListTemplateUrl( String mobListTemplateUrl )
    {
        this.mobListTemplateUrl = mobListTemplateUrl;
    }

    public String getPadListTemplateUrl()
    {
        return padListTemplateUrl;
    }

    public void setPadListTemplateUrl( String padListTemplateUrl )
    {
        this.padListTemplateUrl = padListTemplateUrl;
    }

    public Long getModelId()
    {
        return modelId;
    }

    public void setModelId( Long modelId )
    {
        this.modelId = modelId;
    }

    public void decode()
    {
        try
        {
            this.commendName = URLDecoder.decode( this.commendName, "UTF-8" );
            this.listTemplateUrl = URLDecoder.decode( this.listTemplateUrl, "UTF-8" );
            this.mobListTemplateUrl = URLDecoder.decode( this.mobListTemplateUrl, "UTF-8" );
            this.padListTemplateUrl = URLDecoder.decode( this.padListTemplateUrl, "UTF-8" );
        }
        catch ( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }

    }
}
