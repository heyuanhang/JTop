package cn.com.mjsoft.cms.questionnaire.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.questionnaire.bean.SurveyOptionInfoBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SurveyOptionInfoBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SurveyOptionInfoBean bean = new SurveyOptionInfoBean();

        bean.setOptionId( Long.valueOf( rs.getLong( "optionId" ) ) );
        bean.setSurveyId( Long.valueOf( rs.getLong( "surveyId" ) ) );
        bean.setOptionText( rs.getString( "optionText" ) );
        bean.setOptionImage( rs.getString( "optionImage" ) );
        bean.setVote( Integer.valueOf( rs.getInt( "vote" ) ) );
        bean.setVotePer( Integer.valueOf( rs.getInt( "votePer" ) ) );
        bean.setTarget( rs.getString( "target" ) );
        bean.setInputText( rs.getString( "inputText" ) );
        bean
            .setInputTextCount( Integer.valueOf( rs.getInt( "inputTextCount" ) ) );

        bean.setSiteFlag( rs.getString( "siteFlag" ) );

        return bean;

    }
}
