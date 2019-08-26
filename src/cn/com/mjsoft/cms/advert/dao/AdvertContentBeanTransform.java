package cn.com.mjsoft.cms.advert.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.advert.bean.AdvertContentBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;

public class AdvertContentBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        AdvertContentBean bean = new AdvertContentBean();

        bean.setAdvertCode( rs.getString( "advertCode" ) );
        bean.setAdvertId( Long.valueOf( rs.getLong( "advertId" ) ) );
        bean.setImportance( Integer.valueOf( rs.getInt( "importance" ) ) );
        bean.setPercentVal( Integer.valueOf( rs.getInt( "percentVal" ) ) );
        bean.setKeyword( rs.getString( "keyword" ) );
        bean.setPosId( Long.valueOf( rs.getLong( "posId" ) ) );
        bean.setAdName( rs.getString( "adName" ) );
        bean.setAdFlag( rs.getString( "adFlag" ) );

        String endDate = DateAndTimeUtil.getFormatDate( rs.getTimestamp(
            "showEndDate" ).getTime(), DateAndTimeUtil.DEAULT_FORMAT_YMD );

        if( "9999-12-31".equals( endDate ) )
        {
            endDate = "";
        }

        bean.setShowEndDate( endDate );
        bean.setShowStartDate( DateAndTimeUtil.getFormatDate( rs.getTimestamp(
            "showStartDate" ).getTime(), DateAndTimeUtil.DEAULT_FORMAT_YMD ) );
        bean.setTarget( Integer.valueOf( rs.getInt( "target" ) ) );
        bean.setUseState( Integer.valueOf( rs.getInt( "useState" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );

        return bean;
    }

}
