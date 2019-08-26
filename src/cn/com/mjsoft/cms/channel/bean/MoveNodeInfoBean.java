package cn.com.mjsoft.cms.channel.bean;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MoveNodeInfoBean
{

    private Long classId = Long.valueOf( "-1" );

    private Long currentParent = Long.valueOf( "-1" );

    private Long selectParent = Long.valueOf( "-1" );

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Long getCurrentParent()
    {
        return currentParent;
    }

    public void setCurrentParent( Long currentParent )
    {
        this.currentParent = currentParent;
    }

    public Long getSelectParent()
    {
        return selectParent;
    }

    public void setSelectParent( Long selectParent )
    {
        this.selectParent = selectParent;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder( this ).append( "classId", this.classId )
            .append( "currentParent", this.currentParent ).append(
                "selectParent", this.selectParent ).toString();
    }

}
