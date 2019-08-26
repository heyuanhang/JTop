package cn.com.mjsoft.cms.workflow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.workflow.bean.WorkflowStepActionBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class WorkflowActionBeanTransform implements RowTransform
{
    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        WorkflowStepActionBean bean = new WorkflowStepActionBean();

        bean.setFlowId( Long.valueOf( rs.getLong( "flowId" ) ) );
        bean.setPassActionName( rs.getString( "passActionName" ) );
        bean.setFromStepId( Long.valueOf( rs.getLong( "fromStepId" ) ) );
        bean.setToStepId( Long.valueOf( rs.getLong( "toStepId" ) ) );
        bean.setActionId( Long.valueOf( rs.getLong( "actionId" ) ) );
        bean.setConjunctOrgFlag( Integer.valueOf( rs.getInt( "conjunctOrgFlag" ) ) );
        bean.setConjunctRoleFlag( Integer.valueOf( rs.getInt( "conjunctRoleFlag" ) ) );
        bean.setConjunctManFlag( Integer.valueOf( rs.getInt( "conjunctManFlag" ) ) );
        bean.setOrgBossMode( Integer.valueOf( rs.getInt( "orgBossMode" ) ) );
        bean.setActDesc( rs.getString( "actDesc" ) );
        bean.setNeedRequest( Integer.valueOf( rs.getInt( "needRequest" ) ) );
        bean.setDirectMode( Integer.valueOf( rs.getInt( "directMode" ) ) );

        return bean;
    }
}
