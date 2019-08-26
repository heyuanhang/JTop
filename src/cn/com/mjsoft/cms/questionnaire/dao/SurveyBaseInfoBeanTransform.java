package cn.com.mjsoft.cms.questionnaire.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.questionnaire.bean.SurveyBaseInfoBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SurveyBaseInfoBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SurveyBaseInfoBean bean = new SurveyBaseInfoBean();

        bean.setSurveyId( Long.valueOf( rs.getLong( "surveyId" ) ) );
        bean.setGroupId( Long.valueOf( rs.getLong( "groupId" ) ) );
        bean.setGroupFlag( rs.getString( "groupFlag" ) );
        bean.setOptionType( Integer.valueOf( rs.getInt( "optionType" ) ) );
        bean.setSurveyTitle( rs.getString( "surveyTitle" ) );
        bean.setHaveText( Integer.valueOf( rs.getInt( "haveText" ) ) );
        bean.setAddiTitle( rs.getString( "addiTitle" ) );
        bean.setSiteFlag( rs.getString( "siteFlag" ) );
        bean.setOrderFlag( Integer.valueOf( rs.getInt( "orderFlag" ) ) );

        return bean;
    }
}
