package cn.com.mjsoft.cms.metadata.dao.vo;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "model", id = "dataModelId", idMode = EntitySqlBridge.DB_IDENTITY )
public class DataModel
{

    private Long dataModelId = Long.valueOf( -1 );
    private String modelName;
    private String modelSign;
    private String relateTableName;
    private Integer modelResType = Integer.valueOf( 0 );
    private Integer modelType;
    private String mainEditorFieldSign;
    private String validateBehavior;
    private String beforeBehavior;// 内容增加操作前参数值自定义处理接口
    private String afterBehavior;// 内容增加操作前参数值自定义处理接口
    private Integer privateMode = Constant.COMMON.OFF;// 默认不是私有模型
    private String ico = "document.png";
    private Long siteId;
    private Integer titleMode;
    private String titleCol;
    private Integer kwMode;
    private String remark;
    private Integer simpleMode = Constant.COMMON.OFF;;
    private Integer isManageEdit = Constant.COMMON.ON;
    private Integer mustCensor = Constant.COMMON.ON;
    private Integer isMemberEdit = Constant.COMMON.ON;
    private Integer mustToken = Constant.COMMON.ON;
    private Integer useState = Constant.COMMON.ON;// 默认使用模式

    public String getModelName()
    {
        return this.modelName;
    }

    public void setModelName( String modelName )
    {
        this.modelName = modelName;
    }

    public String getModelSign()
    {
        return this.modelSign;
    }

    public void setModelSign( String modelSign )
    {
        this.modelSign = modelSign;
    }

    public String getRelateTableName()
    {
        return this.relateTableName;
    }

    public void setRelateTableName( String relateTableName )
    {
        this.relateTableName = relateTableName;
    }

    public String getRemark()
    {
        return this.remark;
    }

    public void setRemark( String remark )
    {
        this.remark = remark;
    }

    public Integer getModelType()
    {
        return modelType;
    }

    public void setModelType( Integer modelType )
    {
        this.modelType = modelType;
    }

    public Integer getUseState()
    {
        return useState;
    }

    public void setUseState( Integer useState )
    {
        this.useState = useState;
    }

    public Long getDataModelId()
    {
        return dataModelId;
    }

    public void setDataModelId( Long dataModelId )
    {
        this.dataModelId = dataModelId;
    }

    public Integer getModelResType()
    {
        return modelResType;
    }

    public void setModelResType( Integer modelResType )
    {
        this.modelResType = modelResType;
    }

    public String getMainEditorFieldSign()
    {
        return mainEditorFieldSign;
    }

    public void setMainEditorFieldSign( String mainEditorFieldSign )
    {
        this.mainEditorFieldSign = mainEditorFieldSign;
    }

    public String getAfterBehavior()
    {
        return afterBehavior;
    }

    public void setAfterBehavior( String afterBehavior )
    {
        this.afterBehavior = afterBehavior;
    }

    public String getBeforeBehavior()
    {
        return beforeBehavior;
    }

    public void setBeforeBehavior( String beforeBehavior )
    {
        this.beforeBehavior = beforeBehavior;
    }

    public String getValidateBehavior()
    {
        return validateBehavior;
    }

    public void setValidateBehavior( String validateBehavior )
    {
        this.validateBehavior = validateBehavior;
    }

    public Integer getPrivateMode()
    {
        return privateMode;
    }

    public void setPrivateMode( Integer privateMode )
    {
        this.privateMode = privateMode;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public String getIco()
    {
        return ico;
    }

    public void setIco( String ico )
    {
        this.ico = ico;
    }

    public Integer getIsManageEdit()
    {
        return isManageEdit;
    }

    public void setIsManageEdit( Integer isManageEdit )
    {
        this.isManageEdit = isManageEdit;
    }

    public Integer getMustCensor()
    {
        return mustCensor;
    }

    public void setMustCensor( Integer mustCensor )
    {
        this.mustCensor = mustCensor;
    }

    public Integer getIsMemberEdit()
    {
        return isMemberEdit;
    }

    public void setIsMemberEdit( Integer isMemberEdit )
    {
        this.isMemberEdit = isMemberEdit;
    }

    public Integer getMustToken()
    {
        return mustToken;
    }

    public void setMustToken( Integer mustToken )
    {
        this.mustToken = mustToken;
    }

    public Integer getTitleMode()
    {
        return titleMode;
    }

    public void setTitleMode( Integer titleMode )
    {
        this.titleMode = titleMode;
    }

    public String getTitleCol()
    {
        return titleCol;
    }

    public void setTitleCol( String titleCol )
    {
        this.titleCol = titleCol;
    }

    public Integer getKwMode()
    {
        return kwMode;
    }

    public void setKwMode( Integer kwMode )
    {
        this.kwMode = kwMode;
    }

    public Integer getSimpleMode()
    {
        return simpleMode;
    }

    public void setSimpleMode( Integer simpleMode )
    {
        this.simpleMode = simpleMode;
    }
}
