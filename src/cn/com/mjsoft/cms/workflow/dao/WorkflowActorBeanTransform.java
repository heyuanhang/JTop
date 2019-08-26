package cn.com.mjsoft.cms.workflow.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.workflow.bean.WorkflowActorBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class WorkflowActorBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        WorkflowActorBean bean = new WorkflowActorBean();

        bean.setFlowId( Long.valueOf( rs.getLong( "flowId" ) ) );
        bean.setCensorStep( Long.valueOf( rs.getLong( "censorStep" ) ) );
        bean.setAuditManId( Long.valueOf( rs.getLong( "auditManId" ) ) );
        bean.setType( Integer.valueOf( rs.getInt( "type" ) ) );

        return bean;
    }

}
