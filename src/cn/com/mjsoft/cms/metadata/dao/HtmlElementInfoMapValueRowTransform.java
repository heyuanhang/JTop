package cn.com.mjsoft.cms.metadata.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cn.com.mjsoft.cms.metadata.bean.SystemModelHtmlElementBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class HtmlElementInfoMapValueRowTransform implements RowTransform
{
    private Map infoMap = new HashMap();

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SystemModelHtmlElementBean bean = new SystemModelHtmlElementBean();
        bean.setHtmlElementId( Long.valueOf( rs.getLong( "htmlElementId" ) ) );
        bean.setHtmlElementName( rs.getString( "htmlElementName" ) );
        bean.setHtmlInputTemplet( rs.getString( "htmlInputTemplet" ) );
        bean.setHtmlEditTemplet( rs.getString( "htmlEditTemplet" ) );
        bean.setLayoutParam( rs.getString( "layoutParam" ) );
        bean.setValueParam( rs.getString( "valueParam" ) );
        infoMap.put( Long.valueOf( rs.getLong( "htmlElementId" ) ), bean );

        return null;
    }

    public Map getInfoMap()
    {
        return infoMap;
    }
}
