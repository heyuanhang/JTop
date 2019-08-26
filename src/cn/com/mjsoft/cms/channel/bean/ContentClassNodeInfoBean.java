package cn.com.mjsoft.cms.channel.bean;


/**
 * 节点信息bean,此bean只包含数据结构意义上的节点信息
 * 
 * @author mjsoft
 * 
 */
public class ContentClassNodeInfoBean
{
    private Long classId = Long.valueOf( -1 );

    private Long parent = Long.valueOf( -1 );

    private Integer layer;

    private Integer isLeaf;

    private Integer isSpecial;

    private String linearOrderFlag;

    private Integer isLastChild;

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Integer getLayer()
    {
        return layer;
    }

    public void setLayer( Integer layer )
    {
        this.layer = layer;
    }

    public String getLinearOrderFlag()
    {
        return linearOrderFlag;
    }

    public void setLinearOrderFlag( String linearOrderFlag )
    {
        this.linearOrderFlag = linearOrderFlag;
    }

    public boolean isLastChild()
    {
        return ( isLastChild.intValue() == 1 ) ? true : false;
    }

    public boolean isLeaf()
    {
        return ( isLeaf.intValue() == 1 ) ? true : false;
    }

    public Long getParent()
    {
        return parent;
    }

    public void setParent( Long parent )
    {
        this.parent = parent;
    }

    public Integer getIsLastChild()
    {
        return isLastChild;
    }

    public void setIsLastChild( Integer isLastChild )
    {
        this.isLastChild = isLastChild;
    }

    public Integer getIsLeaf()
    {
        return isLeaf;
    }

    public void setIsLeaf( Integer isLeaf )
    {
        this.isLeaf = isLeaf;
    }

    public Integer getIsSpecial()
    {
        return isSpecial;
    }

    public void setIsSpecial( Integer isSpecial )
    {
        this.isSpecial = isSpecial;
    }

}
