package cn.com.mjsoft.cms.workflow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.workflow.bean.WorkflowOperationBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class WorkflowOperationBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        WorkflowOperationBean bean = new WorkflowOperationBean();

        bean.setOpId( Long.valueOf( rs.getLong( "opId" ) ) );
        bean.setFlowId( Long.valueOf( rs.getLong( "flowId" ) ) );
        bean.setContentId( Long.valueOf( rs.getLong( "contentId" ) ) );
        bean.setInfoType( Integer.valueOf( rs.getInt( "infoType" ) ) );
        bean.setFlowTarget( rs.getString( "flowTarget" ) );

        bean.setPossessUserId( Long.valueOf( rs.getLong( "possessUserId" ) ) );
        bean.setCurrentStep( Long.valueOf( rs.getLong( "currentStep" ) ) );
        bean.setOperStatus( Integer.valueOf( rs.getInt( "operStatus" ) ) );
        bean.setPossessStatus( Integer.valueOf( rs.getInt( "possessStatus" ) ) );
        bean.setCurrentAuditUser( rs.getString( "currentAuditUser" ) );
        bean.setStartCensor( Integer.valueOf( rs.getInt( "startCensor" ) ) );

        return bean;
    }
}
