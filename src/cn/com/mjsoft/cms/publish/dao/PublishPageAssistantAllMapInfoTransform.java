package cn.com.mjsoft.cms.publish.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class PublishPageAssistantAllMapInfoTransform implements RowTransform
{
    private PublishPageAssistantTransform ppat = new PublishPageAssistantTransform();

    private Map infoMap = new HashMap();

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        long classId = rs.getLong( "classId" );

        String qk = rs.getString( "queryKey" );

        String target = classId+"";

        if( qk.startsWith( classId + ":mob" ) )
        {
            target = "mob" + classId;
        }
        else if( qk.startsWith( classId + ":pad" ) )
        {
            target = "pad" + classId;
        }

        infoMap.put( target, ppat.convertRow(
            rs, rowNum ) );

        return null;
    }

    public Map getInfoMap()
    {
        return infoMap;
    }

}
