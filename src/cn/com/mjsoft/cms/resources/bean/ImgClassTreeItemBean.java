package cn.com.mjsoft.cms.resources.bean;

import java.io.Serializable;

public class ImgClassTreeItemBean implements Serializable
{
    private static final long serialVersionUID = -9039994595771257888L;

    private Long parent;

    private String name;

    private Integer isLeaf;

    private Long classId;

    public ImgClassTreeItemBean()
    {

    }

    public ImgClassTreeItemBean( Long parent, String name, Integer isLeaf,
        Long classId )
    {

        this.parent = parent;
        this.name = name;
        this.isLeaf = isLeaf;
        this.classId = classId;

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

}
