package cn.com.mjsoft.cms.metadata.bean;

import java.io.Serializable;

public class DataModelBean implements Serializable
{
    private static final long serialVersionUID = 1045819506303359036L;

    private Long dataModelId = Long.valueOf( -1 );
    private String modelName;
    private String modelSign;
    private String relateTableName;
    private Integer modelResType;
    private Integer modelType;
    private String mainEditorFieldSign;
    private String validateBehavior;
    private String beforeBehavior;// 内容增加操作前参数值自定义处理接口
    private String afterBehavior;// 内容增加操作前参数值自定义处理接口
    private String remark;
    private Integer titleMode;
    private String titleCol;
    private Integer kwMode;
    private Integer simpleMode;
    private String ico;
    private Integer privateMode;
    private Long siteId;
    private Integer isManageEdit;
    private Integer mustCensor;
    private Integer isMemberEdit;
    private Integer mustToken;
    private Integer useState;

    public DataModelBean()
    {
    }

    public DataModelBean( Long dataModelId, String modelName, String modelSign,
        String relateTableName, Integer modelType, String remark, Integer useState )
    {
        this.dataModelId = dataModelId;
        this.modelName = modelName;
        this.modelSign = modelSign;
        this.relateTableName = relateTableName;
        this.modelType = modelType;
        this.remark = remark;
        this.useState = useState;
    }

    public Long getDataModelId()
    {
        return this.dataModelId;
    }

    public void setDataModelId( Long dataModelId )
    {
        this.dataModelId = dataModelId;
    }

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

    /**
     * 扩展属性
     */
    public String getUseStateStr()
    {

        if( useState.intValue() == 10 )
        {
            return "正常";
        }

        if( useState.intValue() == 20 )
        {
            return "<font color='red'>停用</font>";
        }

        return "";
    }

    public String getModelTypeStr()
    {

        if( modelType.intValue() == 10 )
        {
            return "系统模型";
        }

        if( modelType.intValue() == 20 )
        {
            return "自定义模型";
        }

        return "";
    }

}
