package cn.com.mjsoft.cms.channel.bean;

import java.util.ArrayList;
import java.util.List;

public class ArrayNoteBean
{
    String arrayMode;

    Long parent;

    Long classId;

    Long currentClassId;

    Long targetClassId;

    public String getArrayMode()
    {
        return arrayMode;
    }

    public void setArrayMode( String arrayMode )
    {
        this.arrayMode = arrayMode;
    }

    public Long getCurrentClassId()
    {
        return currentClassId;
    }

    public void setCurrentClassId( Long currentClassId )
    {
        this.currentClassId = currentClassId;
    }

    public Long getTargetClassId()
    {
        return targetClassId;
    }

    public void setTargetClassId( Long targetClassId )
    {
        this.targetClassId = targetClassId;
    }

    public Long getParent()
    {
        return parent;
    }

    public void setParent( Long parent )
    {
        this.parent = parent;
    }

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public List changeToIdList()
    {
        List classIdList = new ArrayList( 2 );
        classIdList.add( currentClassId );
        classIdList.add( targetClassId );
        return classIdList;
    }

}
