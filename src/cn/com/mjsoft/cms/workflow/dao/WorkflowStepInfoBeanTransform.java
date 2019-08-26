package cn.com.mjsoft.cms.workflow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.workflow.bean.WorkflowStepInfoBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class WorkflowStepInfoBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        WorkflowStepInfoBean bean = new WorkflowStepInfoBean();

        bean.setFlowId( Long.valueOf( rs.getLong( "flowId" ) ) );
        bean.setStepNodeName( rs.getString( "stepNodeName" ) );
        bean.setStepId( Long.valueOf( rs.getLong( "stepId" ) ) );
        bean.setConjunctOrgFlag( Integer.valueOf( rs.getInt( "conjunctOrgFlag" ) ) );
        bean.setConjunctRoleFlag( Integer.valueOf( rs.getInt( "conjunctRoleFlag" ) ) );
        bean.setConjunctManFlag( Integer.valueOf( rs.getInt( "conjunctManFlag" ) ) );
        bean.setAvoidFlag( Integer.valueOf( rs.getInt( "avoidFlag" ) ) );
        // bean.setMustReq( Integer.valueOf( rs.getInt( "mustReq" ) ) );
        bean.setStepDesc( rs.getString( "stepDesc" ) );
        bean.setIsStart( Integer.valueOf( rs.getInt( "isStart" ) ) );
        // bean.setPubDefActId( Long.valueOf( rs.getLong( "pubDefActId" ) ) );
        // bean.setDeleteDefActId( Long.valueOf( rs.getLong( "deleteDefActId" )
        // ) );
        bean.setOrgMode( Integer.valueOf( rs.getInt( "orgMode" ) ) );

        return bean;
    }

}
