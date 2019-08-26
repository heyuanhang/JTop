package cn.com.mjsoft.cms.questionnaire.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.questionnaire.bean.SurveyGroupBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SurveyGroupBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SurveyGroupBean bean = new SurveyGroupBean();

        bean.setGroupId( Long.valueOf( rs.getLong( "groupId" ) ) );
        bean.setClassId( Long.valueOf( rs.getLong( "classId" ) ) );
        bean.setQuestName( rs.getString( "questName" ) );
        bean.setQuestDesc( rs.getString( "questDesc" ) );
        bean.setFlagName( rs.getString( "flagName" ) );
        bean.setRestriction( Integer.valueOf( rs.getInt( "restriction" ) ) );
        bean.setRestInterval( Integer.valueOf( rs.getInt( "restInterval" ) ) );
        bean.setStartDate( rs.getDate( "startDate" ) );
        bean.setEndDate( rs.getDate( "endDate" ) );
        bean.setNeedCaptcha( Integer.valueOf( rs.getInt( "needCaptcha" ) ) );
        bean.setUseState( Integer.valueOf( rs.getInt( "useState" ) ) );
        bean.setHandlers( rs.getString( "handlers" ) );

        return bean;

    }

}
