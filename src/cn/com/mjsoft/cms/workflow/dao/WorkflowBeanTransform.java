package cn.com.mjsoft.cms.workflow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.workflow.bean.WorkflowBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class WorkflowBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        WorkflowBean bean = new WorkflowBean();

        bean.setFlowId( Long.valueOf( rs.getLong( "flowId" ) ) );
        bean.setFlowName( rs.getString( "flowName" ) );
        bean.setFlowDesc( rs.getString( "flowDesc" ) );
        bean.setStep( Integer.valueOf( rs.getInt( "step" ) ) );
        bean.setCreator( rs.getString( "creator" ) );
        bean.setBypassFlag( Integer.valueOf( rs.getInt( "bypassFlag" ) ) );
        bean.setConjunctFlag( Integer.valueOf( rs.getInt( "conjunctFlag" ) ) );
        bean.setSystemHandleTime( rs.getTimestamp( "systemHandleTime" ) );
        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );
        bean.setUpdateDT( Long.valueOf( rs.getLong( "updateDT" ) ) );

        return bean;
    }

}
