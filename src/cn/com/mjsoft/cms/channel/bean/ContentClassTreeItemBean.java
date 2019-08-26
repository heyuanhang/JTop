package cn.com.mjsoft.cms.channel.bean;

import java.io.Serializable;

import cn.com.mjsoft.framework.util.StringUtil;

/**
 * 用于栏目树节点
 * 
 * @author mjsoft
 * 
 */
public class ContentClassTreeItemBean implements Serializable
{
    private static final long serialVersionUID = -9039994595771257888L;

    private Long parent;

    private String name;

    private String linear;

    private Integer layer;

    private Integer isLeaf;

    private Integer classType;

    private Integer isRecommend;

    private Integer isSpecial;

    private Long siteId;

    private Long classId;

    private Long firstClassId;// 全站第一个ID,默认

    private Long modelId;

    private String targetListPage;

    private Long contentModelId;

    private Long contentId;

    private String iconFile;// 注意:现此字段已经不使用

    private String ico;

    private Boolean haveRole;

    public ContentClassTreeItemBean( Integer isSpecial, Long parent,
        String name, String linear, Integer layer, Integer isLeaf,
        Long classId, Long modelId, String targetListPage, Long contentModelId,
        Long contentId, String iconFile, String ico )
    {
        this.isSpecial = isSpecial;
        this.parent = parent;
        this.name = name;
        this.linear = linear;
        this.layer = layer;
        this.isLeaf = isLeaf;
        this.classId = classId;
        this.modelId = modelId;
        this.targetListPage = targetListPage;
        this.contentModelId = contentModelId;
        this.contentId = contentId;
        this.iconFile = iconFile;
        
        if(StringUtil.isStringNull( ico ))
        {
            ico = "document.png";
        }
        this.ico = ico;
    }

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Integer getIsLeaf()
    {
        return isLeaf;
    }

    public void setIsLeaf( Integer isLeaf )
    {
        this.isLeaf = isLeaf;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Long getParent()
    {
        return parent;
    }

    public void setParent( Long parent )
    {
        this.parent = parent;
    }

    public String getTargetListPage()
    {
        return targetListPage;
    }

    public void setTargetListPage( String targetListPage )
    {
        this.targetListPage = targetListPage;
    }

    public Long getContentModelId()
    {
        return contentModelId;
    }

    public void setContentModelId( Long contentModelId )
    {
        this.contentModelId = contentModelId;
    }

    public String getIconFile()
    {
        return iconFile;
    }

    public void setIconFile( String iconFile )
    {
        this.iconFile = iconFile;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public Integer getLayer()
    {
        return layer;
    }

    public void setLayer( Integer layer )
    {
        this.layer = layer;
    }

    public Long getModelId()
    {
        return modelId;
    }

    public void setModelId( Long modelId )
    {
        this.modelId = modelId;
    }

    public Integer getClassType()
    {
        return classType;
    }

    public void setClassType( Integer classType )
    {
        this.classType = classType;
    }

    public Integer getIsSpecial()
    {
        return isSpecial;
    }

    public void setIsSpecial( Integer isSpecial )
    {
        this.isSpecial = isSpecial;
    }

    public Long getFirstClassId()
    {
        return firstClassId;
    }

    public void setFirstClassId( Long firstClassId )
    {
        this.firstClassId = firstClassId;
    }

    public String getLinear()
    {
        return linear;
    }

    public void setLinear( String linear )
    {
        this.linear = linear;
    }

    public Long getContentId()
    {
        return contentId;
    }

    public void setContentId( Long contentId )
    {
        this.contentId = contentId;
    }

    public Integer getIsRecommend()
    {
        return isRecommend;
    }

    public void setIsRecommend( Integer isRecommend )
    {
        this.isRecommend = isRecommend;
    }

    public Boolean getHaveRole()
    {
        return haveRole;
    }

    public void setHaveRole( Boolean haveRole )
    {
        this.haveRole = haveRole;
    }

    public String getIco()
    {
        return ico;
    }

    public void setIco( String ico )
    {
        this.ico = ico;
    }

}
