package cn.com.mjsoft.cms.stat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.stat.bean.StatVisitorHourAnalysisBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class StatVisitorHourAnalysisBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        StatVisitorHourAnalysisBean bean = new StatVisitorHourAnalysisBean();
        bean.setIpCount( Integer.valueOf( rs.getInt( "ipCount" ) ) );
        bean.setPvCount( Integer.valueOf( rs.getInt( "pvCount" ) ) );
        bean.setUvCount( Integer.valueOf( rs.getInt( "uvCount" ) ) );
        bean.setVisitDay( Integer.valueOf( rs.getInt( "visitDay" ) ) );
        bean.setVisitHour( Integer.valueOf( rs.getInt( "visitHour" ) ) );
        bean.setVisitMonth( Integer.valueOf( rs.getInt( "visitMonth" ) ) );
        bean.setVisitYear( Integer.valueOf( rs.getInt( "visitYear" ) ) );
        bean.setNewUv( Integer.valueOf( rs.getInt( "newUv" ) ) );
        bean.setOldUv( Integer.valueOf( rs.getInt( "oldUv" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );

        return bean;
    }
}
