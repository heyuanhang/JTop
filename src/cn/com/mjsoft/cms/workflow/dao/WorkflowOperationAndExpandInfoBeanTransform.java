package cn.com.mjsoft.cms.workflow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.workflow.bean.WorkflowOperationBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class WorkflowOperationAndExpandInfoBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        WorkflowOperationBean bean = new WorkflowOperationBean();

        bean.setOpId( Long.valueOf( rs.getLong( "opId" ) ) );
        bean.setFlowId( Long.valueOf( rs.getLong( "flowId" ) ) );
        bean.setContentId( Long.valueOf( rs.getLong( "contentId" ) ) );

        bean.setClassId( Long.valueOf( rs.getLong( "classId" ) ) );
        bean.setPossessUserId( Long.valueOf( rs.getLong( "possessUserId" ) ) );
        bean.setCurrentStep( Long.valueOf( rs.getLong( "currentStep" ) ) );
        bean.setOperStatus( Integer.valueOf( rs.getInt( "operStatus" ) ) );
        bean.setPossessStatus( Integer.valueOf( rs.getInt( "possessStatus" ) ) );
        bean.setStepNodeName( rs.getString( "stepNodeName" ) );

        bean.setCurrentAuditUser( rs.getString( "currentAuditUser" ) );

        bean.setInfoType( Integer.valueOf( rs.getInt( "infoType" ) ) );
        bean.setFlowTarget( rs.getString( "flowTarget" ) );
        bean.setStartCensor( Integer.valueOf( rs.getInt( "startCensor" ) ) );

        if( bean.getInfoType() == 1 )// 内容
        {
            bean.setModelId( rs.getLong( "modelId" ) );
            bean.setAddTime( rs.getTimestamp( "addTime" ) );
            bean.setCreator( rs.getString( "creator" ) );
            bean.setTitle( rs.getString( "title" ) );
        }
        else if( bean.getInfoType() == 2 )// 留言
        {
            bean.setAddTime( rs.getTimestamp( "addDate" ) );

            bean.setTitle( rs.getString( "gbTitle" ) );
        }

        return bean;
    }
}
