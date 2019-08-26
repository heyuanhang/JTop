package cn.com.mjsoft.cms.publish.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.publish.bean.PublishRuleBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class PublishRuleBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        PublishRuleBean bean = new PublishRuleBean();

        bean.setFileNameParam( rs.getString( "fileNameParam" ) );
        bean.setFileNameRule( rs.getString( "fileNameRule" ) );
        bean.setRuleId( Long.valueOf( rs.getLong( "ruleId" ) ) );
        bean.setRuleName( rs.getString( "ruleName" ) );
        bean.setSavePathParam( rs.getString( "savePathParam" ) );
        bean.setSavePathRule( rs.getString( "savePathRule" ) );
        bean.setSuffixRule( rs.getString( "suffixRule" ) );
        bean.setType( Integer.valueOf( rs.getInt( "type" ) ) );
        bean.setPageFileNameParam( rs.getString( "pageFileNameParam" ) );
        bean.setPageFileNameRule( rs.getString( "pageFileNameRule" ) );
        bean.setPagePathParam( rs.getString( "pagePathParam" ) );
        bean.setPagePathRule( rs.getString( "pagePathRule" ) );
        bean.setNeedPage( Integer.valueOf( rs.getInt( "needPage" ) ) );
        bean.setPageSize( Integer.valueOf( rs.getInt( "pageSize" ) ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );

        return bean;
    }
}
