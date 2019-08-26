package cn.com.mjsoft.cms.pick.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.pick.bean.PickContentRuleBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class PickContentRuleBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        PickContentRuleBean bean = new PickContentRuleBean();

        bean.setPickCfgId( Long.valueOf( rs.getLong( "pickCfgId" ) ) );
        bean.setConfigName( rs.getString( "configName" ) );
        bean.setExtModelId( Long.valueOf( rs.getLong( "extModelId" ) ) );
        bean.setListHeadUrlRule( rs.getString( "listHeadUrlRule" ) );
        bean.setListUrlRule( rs.getString( "listUrlRule" ) );
        bean.setContentUrlRule( rs.getString( "contentUrlRule" ) );
        bean.setContentPageUrlRule( rs.getString( "contentPageUrlRule" ) );
        bean.setPrefixSiteUrl( rs.getString( "prefixSiteUrl" ) );
        bean.setConfigDesc( rs.getString( "configDesc" ) );
        bean.setTitleStart( rs.getString( "titleStart" ) );
        bean.setTitleEnd( rs.getString( "titleEnd" ) );
        bean.setContentStart( rs.getString( "contentStart" ) );
        bean.setContentEnd( rs.getString( "contentEnd" ) );
        bean.setSummaryStart( rs.getString( "summaryStart" ) );
        bean.setSummaryEnd( rs.getString( "summaryEnd" ) );
        bean.setAddDateStart( rs.getString( "addDateStart" ) );
        bean.setAddDateEnd( rs.getString( "addDateEnd" ) );
        bean.setAuthorStart( rs.getString( "authorStart" ) );
        bean.setSourceStart( rs.getString( "sourceStart" ) );
        bean.setSourceEnd( rs.getString( "sourceEnd" ) );
        bean.setAuthorEnd( rs.getString( "authorEnd" ) );
        bean.setKeywordStart( rs.getString( "keywordStart" ) );
        bean.setKeywordEnd( rs.getString( "keywordEnd" ) );
        bean.setHtmlMode( Integer.valueOf( rs.getInt( "htmlMode" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );

        bean.setListStart( rs.getString( "listStart" ) );

        bean.setListEnd( rs.getString( "listEnd" ) );

        bean.setTimeFormat( rs.getString( "timeFormat" ) );

        return bean;
    }

}
