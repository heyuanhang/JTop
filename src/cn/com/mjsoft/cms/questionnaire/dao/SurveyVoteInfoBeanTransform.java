package cn.com.mjsoft.cms.questionnaire.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.questionnaire.bean.SurveyVoteInfoBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SurveyVoteInfoBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SurveyVoteInfoBean bean = new SurveyVoteInfoBean();

        bean.setOptId( Long.valueOf( rs.getLong( "optId" ) ) );
        bean.setSurveyId( Long.valueOf( rs.getLong( "surveyId" ) ) );
        bean.setVoteText( rs.getString( "voteText" ) );
        bean.setVoteMan( rs.getString( "voteMan" ) );
        bean.setIp( rs.getString( "ip" ) );
        bean.setVoteDate( rs.getDate( "voteDate" ) );

        return bean;
    }

}
