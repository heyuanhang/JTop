package cn.com.mjsoft.cms.workflow.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.mjsoft.cms.workflow.bean.WorkflowBean;
import cn.com.mjsoft.cms.workflow.bean.WorkflowOperationBean;
import cn.com.mjsoft.cms.workflow.bean.WorkflowStepActionBean;
import cn.com.mjsoft.cms.workflow.bean.WorkflowStepInfoBean;
import cn.com.mjsoft.cms.workflow.dao.vo.Workflow;
import cn.com.mjsoft.cms.workflow.dao.vo.WorkflowActor;
import cn.com.mjsoft.cms.workflow.dao.vo.WorkflowOperation;
import cn.com.mjsoft.cms.workflow.dao.vo.WorkflowStepAction;
import cn.com.mjsoft.cms.workflow.dao.vo.WorkflowStepInfo;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;

public class WorkFlowDao
{
    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public WorkFlowDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public UpdateState saveWorkflowMainInfo( Workflow workflow )
    {
        return pe.save( workflow );
    }

    public void updateWorkflowMainInfo( Workflow workflow )
    {
        String sql = "update workflow set flowName=?, flowDesc=? where flowId=?";

        pe.update( sql, workflow );
    }

    public void saveWorkflowActor( WorkflowActor workflowActor )
    {
        pe.save( workflowActor );
    }

    public void deleteWorkflowActorByFlowIdAndStepId( Long flowId, Long stepId )
    {
        String sql = "delete from workflow_actor where flowId=? and censorStep=?";
        pe.update( sql, new Object[] { flowId, stepId } );
    }

    public List queryAllWorkflowBeanBySiteId( Long siteId )
    {
        String sql = "select * from workflow where siteId=?";

        return pe.query( sql, new Object[] { siteId }, new WorkflowBeanTransform() );
    }

    public List<Workflow> queryAllWorkflowBySiteId( Long siteId )
    {
        String sql = "select * from workflow where siteId=?";

        return pe.queryBeanList( sql, new Object[] { siteId }, Workflow.class );
    }

    public List queryAllWorkflowFlowIdBySiteId( Long siteId )
    {
        String sql = "select flowId from workflow where siteId=?";

        return pe.querySingleCloumn( sql, new Object[] { siteId }, Long.class );
    }

    public List queryWorkflowStepBeanListByFlowId( Long flowId )
    {
        String sql = "select * from workflow_step_info where flowId=? order by isStart desc, stepId asc";

        return pe.queryBeanList( sql, new Object[] { flowId }, WorkflowStepInfoBean.class );
    }

    public List<WorkflowStepInfo> queryWorkflowStepListByFlowId( Long flowId )
    {
        String sql = "select * from workflow_step_info where flowId=? order by isStart desc, stepId asc";

        return pe.queryBeanList( sql, new Object[] { flowId }, WorkflowStepInfo.class );
    }

    public WorkflowStepInfoBean querySingleWorkflowStepBeanByFlowIdAndStep( Long flowId, Long step )
    {
        String sql = "select * from workflow_step_info where flowId=? and stepId=?";

        return ( WorkflowStepInfoBean ) pe.querySingleRow( sql, new Object[] { flowId, step },
            new WorkflowStepInfoBeanTransform() );
    }

    public WorkflowStepInfoBean querySingleWorkflowStepBeanByFlowIdAndStep( Long step )
    {
        String sql = "select * from workflow_step_info where stepId=?";

        return ( WorkflowStepInfoBean ) pe.querySingleRow( sql, new Object[] { step },
            new WorkflowStepInfoBeanTransform() );
    }

    public WorkflowStepInfoBean querySingleWorkflowStepBeanByStepId( Long stepId )
    {
        String sql = "select * from workflow_step_info where stepId=?";

        return ( WorkflowStepInfoBean ) pe.querySingleRow( sql, new Object[] { stepId },
            new WorkflowStepInfoBeanTransform() );
    }

    public List queryWorkflowActorBeanList( Long flowId, Long stepId, Integer type )
    {
        String sql = "select * from workflow_actor where flowId=? and censorStep=? and type=? order by censorStep asc";

        return pe.query( sql, new Object[] { flowId, stepId, type },
            new WorkflowActorBeanTransform() );
    }

    public List<WorkflowActor> queryWorkflowActorList( Long flowId )
    {
        String sql = "select * from workflow_actor where flowId=? order by censorStep asc";

        return pe.queryBeanList( sql, new Object[] { flowId }, WorkflowActor.class );
    }

    public WorkflowBean querySingleWorkflowBean( Long flowId )
    {
        String sql = "select * from workflow where flowId=?";

        return ( WorkflowBean ) pe.querySingleRow( sql, new Object[] { flowId },
            new WorkflowBeanTransform() );
    }

    public void saveWorkflowOperation( WorkflowOperation wfOperBean )
    {
        pe.save( wfOperBean );
    }

    public void saveWorkflowOperInfo( Map infoMap )
    {
        String sql = "insert into workflow_oper_info (contentId, pUserName, actionId, fromStepId, toStepId, infoType, eventDT) values (?,?,?,?,?,?,?)";

        pe.update( sql, infoMap );
    }

    public Map queryWorkflowOperInfoInfo( Long cid, Integer infoType )
    {
        String sql = "select * from workflow_oper_info where contentId=? and infoType=? order by eventDT desc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { cid, infoType } );
    }

    public Long queryWorkflowOperInfoInfoCount( Long cid, Integer infoType )
    {
        String sql = "select count(*) from workflow_oper_info where contentId=? and infoType=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { cid, infoType }, Long.class );
    }

    public List queryWorkflowOperInfoInfoList( Long cid, Integer infoType, Long start, Integer size )
    {
        String sql = "select * from workflow_oper_info where contentId=? and infoType=? order by eventDT desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { cid, infoType, start, size } );
    }

    public void deleteWorkflowOperInfoByContentId( Long contentId, Integer infoType )
    {
        String sql = "delete from workflow_oper_info where contentId=? and infoType=?";

        pe.update( sql, new Object[] { contentId, infoType } );
    }

    public Integer queryFlowIdForOperation( Long flowId, Integer infoType )
    {
        String sql = "select count(*) from workflow_operation where flowId=? and infoType=?";

        return ( Integer ) pe.querySingleObject( sql, new Object[] { flowId, infoType },
            Integer.class );
    }

    public void deleteWorkflowOperationByFlowId( Long flowId )
    {
        String sql = "delete from workflow_operation where flowId=? ";

        pe.update( sql, new Object[] { flowId } );
    }

    public UpdateState saveWorkflowStepInfo( WorkflowStepInfo stepInfo )
    {
        return pe.save( stepInfo );
    }

    public UpdateState updateWorkflowStepInfo( WorkflowStepInfo stepInfo )
    {
        String sql = "update workflow_step_info set stepDesc=?, conjunctOrgFlag=?, conjunctRoleFlag=?, conjunctManFlag=?, avoidFlag=?, mustReq=?, stepNodeName=?, pubDefActId=?, deleteDefActId=?, orgMode=? where stepId=? and flowId=?";
        return pe.update( sql, stepInfo );
    }

    public UpdateState updateWorkflowStepCount( Long flowId, Integer count )
    {
        String sql = "update workflow set step=? where flowId=?";
        return pe.update( sql, new Object[] { count, flowId } );
    }

    public void deleteWorkflowStepInfoByFlowId( Long flowId )
    {
        String sql = "delete from workflow_step_info where flowId=?";
        pe.update( sql, new Object[] { flowId } );
    }

    public void deleteWorkflowActorByFlowId( Long flowId )
    {
        String sql = "delete from workflow_actor where flowId=?";
        pe.update( sql, new Object[] { flowId } );

    }

    public void deleteWorkflowByFlowId( Long flowId )
    {
        String sql = "delete from workflow where flowId=?";
        pe.update( sql, new Object[] { flowId } );

    }

    public void deleteWorkflowOperationByContentId( Long contentId, Integer infoType )
    {
        String sql = "delete from workflow_operation where contentId=? and infoType=?";
        pe.update( sql, new Object[] { contentId, infoType } );

    }

    public void deleteWorkflowOpTraceByContentId( Long contentId, Integer infoType )
    {
        String sql = "delete from workflow_op_trace where contentId=? and infoType=?";
        pe.update( sql, new Object[] { contentId, infoType } );

    }

    public void deleteWorkflowActionByFlowId( Long flowId )
    {
        String sql = "delete from workflow_step_action where flowId=?";
        pe.update( sql, new Object[] { flowId } );

    }

    public WorkflowOperationBean querySingleWorkflowOperation( Long contentId, Integer infoType )
    {
        String sql = "select * from workflow_operation where contentId=? and infoType=?";

        return ( WorkflowOperationBean ) pe.querySingleRow( sql,
            new Object[] { contentId, infoType }, new WorkflowOperationBeanTransform() );
    }

    public WorkflowOperationBean querySingleWorkflowOperationBean( Long contentId, Integer infoType )
    {
        String sql = "select * from workflow_operation where contentId=? and infoType=?";

        return ( WorkflowOperationBean ) pe.querySingleBean( sql, new Object[] { contentId,
            infoType }, WorkflowOperationBean.class );
    }

    public WorkflowOperationBean querySingleWorkflowOperationBeanByOpId( Long opId )
    {
        String sql = "select * from workflow_operation where opId=?";

        return ( WorkflowOperationBean ) pe.querySingleBean( sql, new Object[] { opId },
            WorkflowOperationBean.class );
    }

    public void updateWorkflowOperationPossessInfo( Long contentId, Integer infoType, Long userId,
        String userName, int possessStatus, int operStatus )
    {
        String sql = "update workflow_operation set possessUserId=?, currentAuditUser=?, possessStatus=?, operStatus=? where contentId=? and infoType=?";
        pe.update( sql, new Object[] { userId, userName, Integer.valueOf( possessStatus ),
            Integer.valueOf( operStatus ), contentId, infoType } );
    }

    public void updateWorkflowOperationPossessInfo( Long contentId, Integer infoType, Long userId,
        String userName, Integer possessStatus, Integer operStatus, Long currentStep )
    {

        String name = "";
        try
        {
            name = URLDecoder.decode( name, "utf-8" );
        }
        catch ( UnsupportedEncodingException e )
        {

            e.printStackTrace();
        }

        String sql = "update workflow_operation set possessUserId=?, currentAuditUser=?, possessStatus=?, operStatus=?, currentStep=? where contentId=? and infoType=?";
        pe.update( sql, new Object[] { userId, userName, possessStatus, operStatus, currentStep,
            contentId, infoType } );
    }

    public void updateWorkflowOperationCurrentAuditUser( Long contentId, Integer infoType,
        String userName )
    {
        String sql = "update workflow_operation set currentAuditUser=?, possessStatus=? where contentId=? and infoType=?";
        pe.update( sql, new Object[] { userName, Integer.valueOf( 0 ), contentId, infoType } );
    }

    public Integer queryWorkflowOperationInAuditCountByUserIdWithNoPossess( String roleSqlhp,
        Long contentId, Integer infoType )
    {
        String sql = "select count(*) from workflow_operation wo join workflow_actor wa on wo.flowId=wa.flowId and "
            + roleSqlhp + " where wo.contentId=? and wo.infoType=? and possessStatus=0";
        return ( Integer ) pe.querySingleObject( sql, new Object[] { contentId, infoType },
            Integer.class );
    }

    public Integer queryWorkflowOperationCountByContentId( Long contentId, Integer infoType )
    {
        String sql = "select count(*) from workflow_operation where contentId=? and infoType=?";
        return ( Integer ) pe.querySingleObject( sql, new Object[] { contentId, infoType },
            Integer.class );
    }

    public WorkflowOperationBean queryWorkflowOperationBeanByContentId( Long contentId,
        Integer infoType )
    {
        String sql = "select * from workflow_operation where contentId=? and infoType=?";
        return ( WorkflowOperationBean ) pe.querySingleRow( sql,
            new Object[] { contentId, infoType }, new WorkflowOperationBeanTransform() );
    }

    public WorkflowStepInfoBean querySingleWorkflowStepInfoBean( Long contentId, Integer infoType )
    {
        String sql = "select wi.* from workflow_step_info wi left join workflow_operation wo on wi.flowId=wo.flowId and wi.stepId=wo.currentStep where wo.contentId=? and wo.infoType=?";
        return ( WorkflowStepInfoBean ) pe.querySingleRow( sql,
            new Object[] { contentId, infoType }, new WorkflowStepInfoBeanTransform() );
    }

    public WorkflowOperationBean querySingleWorkflowOperationBeanByContentIdAndUserId(
        Long contentId, Integer infoType )
    {
        String sql = "select * from workflow_operation where contentId=? and infoType=? and  (operStatus=0 or operStatus=1)";
        return ( WorkflowOperationBean ) pe.querySingleRow( sql,
            new Object[] { contentId, infoType }, new WorkflowOperationBeanTransform() );
    }

    public List queryWorkflowActionBeanByFlowId( Long flowId )
    {
        String sql = "select * from workflow_step_action where flowId=? order by fromStepId asc";

        return pe.query( sql, new Object[] { flowId }, new WorkflowActionBeanTransform() );
    }

    public List<WorkflowStepAction> queryWorkflowActionByFlowId( Long flowId )
    {
        String sql = "select * from workflow_step_action where flowId=? order by fromStepId asc";

        return pe.queryBeanList( sql, new Object[] { flowId }, WorkflowStepAction.class );
    }

    public List queryWorkflowStartActionBeanByFlowId( Long flowId )
    {
        String sql = "select * from workflow_step_action where flowId=? and fromStepId=(select stepId from workflow_step_info where flowId=? and isStart=1) order by fromStepId asc";

        return pe.query( sql, new Object[] { flowId, flowId }, new WorkflowActionBeanTransform() );
    }

    public List queryWorkflowStepBeanListByClassId( Long contentId, Integer infoType )
    {
        String sql = "select * from workflow_step_action wsa, (select wo.flowId, wo.currentStep from workflow_operation wo where wo.contentId=? and wo.infoType=?) tmp where tmp.flowId=wsa.flowId and tmp.currentStep=wsa.fromStepId order by wsa.fromStepId asc";

        return pe.query( sql, new Object[] { contentId, infoType },
            new WorkflowActionBeanTransform() );
    }

    public List queryWorkflowActionBeanByFlowIdAndStartStepId( Long flowId, Long fromStepId )
    {
        String sql = "select * from workflow_step_action where flowId=? and fromStepId=? order by actionId asc";

        return pe.query( sql, new Object[] { flowId, fromStepId },
            new WorkflowActionBeanTransform() );
    }

    public WorkflowStepActionBean querySingleWorkflowStepActionBean( Long actId )
    {
        String sql = "select * from workflow_step_action where actionId=?";

        return ( WorkflowStepActionBean ) pe.querySingleRow( sql, new Object[] { actId },
            new WorkflowActionBeanTransform() );
    }

    public void saveWorkflowAction( WorkflowStepAction vo )
    {
        pe.save( vo );
    }

    public void updateWorkflowAction( WorkflowStepAction vo )
    {
        pe.update( vo );
    }

    public Integer queryActionCountByUserId( Long userId, Long contentId, Integer infoType )
    {
        String sql = "select count(*) from (select ur.userId,wa.auditManId from user_relate_role ur, workflow_actor wa,(select wo.flowId, wo.currentStep from workflow_operation wo where wo.contentId=? and wo.infoType=?) tmp where ur.userId=? and wa.flowId=tmp.flowId and wa.censorStep=tmp.currentStep and ur.roleId=wa.auditManId and wa.type=1"
            + " union "
            + "select su.userId,wa.auditManId from systemuser su, workflow_actor wa,(select wo.flowId, wo.currentStep from workflow_operation wo where wo.contentId=? and wo.infoType=?) tmp where su.userId=? and wa.flowId=tmp.flowId and wa.censorStep=tmp.currentStep and su.userId=wa.auditManId and wa.type=2"
            + " union "
            + "select su.userId,wa.auditManId from systemuser su, workflow_actor wa,(select wo.flowId, wo.currentStep from workflow_operation wo where wo.contentId=? and wo.infoType=?) tmp where su.userId=? and wa.flowId=tmp.flowId and wa.censorStep=tmp.currentStep and (select orgId from system_organization where linearOrderFlag=su.relateOrgCode)=wa.auditManId and wa.type=3"
            + ") res";

        return ( Integer ) pe.querySingleObject( sql, new Object[] { contentId, infoType, userId,
            contentId, infoType, userId, contentId, infoType, userId }, Integer.class );
    }
    
    public List queryExistInStepCountByUserId( Long userId, Long flowId )
    {
        String sql = "select * from (select ur.userId,wa.auditManId, wa.censorStep from user_relate_role ur, workflow_actor wa where ur.userId=? and wa.flowId=? and ur.roleId=wa.auditManId and wa.type=1"
            + " union "
            + "select su.userId,wa.auditManId, wa.censorStep from systemuser su, workflow_actor wa where su.userId=? and wa.flowId=? and su.userId=wa.auditManId and wa.type=2"
            + " union "
            + "select su.userId,wa.auditManId, wa.censorStep from systemuser su, workflow_actor wa where su.userId=? and wa.flowId=? and (select orgId from system_organization where linearOrderFlag=su.relateOrgCode)=wa.auditManId and wa.type=3"
            + ") res";

        return  pe.queryResultMap( sql, new Object[] { userId,
            flowId,  userId, flowId, userId, flowId }  );
    }

    public WorkflowStepActionBean queryWorkflowStepActionBeanByFlowIdAndStep( Long flowId,
        Long fromStepId, Long toStepId )
    {
        String sql = "select * from workflow_step_action where flowId=? and fromStepId=? and toStepId=?";

        return ( WorkflowStepActionBean ) pe.querySingleRow( sql, new Object[] { flowId,
            fromStepId, toStepId }, new WorkflowActionBeanTransform() );
    }

    public Set queryActionOpTraceUserId( Long actionId, Long contentId, Integer infoType )
    {
        String sql = "select userId from workflow_op_trace where actionId=? and contentId=? and infoType=?";

        return new HashSet( pe.querySingleCloumn( sql,
            new Object[] { actionId, contentId, infoType }, Long.class ) );
    }

    public Set queryActorIdForStepOrg( Long flowId, Long stepId )
    {
        String sql = "select userId from systemuser where relateOrgCode in (select linearOrderFlag from system_organization where orgId in (select auditManId from workflow_actor where type=3 and flowId=? and censorStep=?))"
            + " union "
            + "select userId from user_relate_role where roleId in (select auditManId from workflow_actor where type=1 and flowId=? and censorStep=?)"
            + " union "
            + "select auditManId as userId from workflow_actor where type=2 and flowId=? and censorStep=?";

        return new HashSet( pe.querySingleCloumn( sql, new Object[] { flowId, stepId, flowId,
            stepId, flowId, stepId }, Long.class ) );
    }

    public Set queryActorOrgBossId( Long flowId, Long stepId )
    {
        String sql = "select orgBossId from system_organization where orgId in (select auditManId from workflow_actor where type=3 and flowId=? and censorStep=?)";

        return new HashSet( pe.querySingleCloumn( sql, new Object[] { flowId, stepId }, Long.class ) );
    }

    public void saveActorIdForStepOrg( Long contentId, Long actionId, Long loginUid,
        Integer infoType )
    {
        String sql = "insert into workflow_op_trace (contentId, actionId, userId, infoType ) values (?,?, ?, ?)";

        pe.insert( sql, new Object[] { contentId, actionId, loginUid, infoType } );
    }

    public Long queryActorIdCountForStepOrg( Long contentId, Long actionId, Long loginUid,
        Integer infoType )
    {
        String sql = "select count(*) from workflow_op_trace where contentId=? and actionId=? and userId=? and infoType=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { contentId, actionId, loginUid,
            infoType }, Long.class );
    }

    public void deleteActorCensorTraceInfo( Long contentId, Long actionId, Integer infoType )
    {
        String sql = "delete from workflow_op_trace where contentId=? and actionId=? and infoType=?";

        pe.update( sql, new Object[] { contentId, actionId, infoType } );
    }

    public void deleteWorkflowMsg( Long contentId )
    {
        String sql = "delete from manager_message where msgTitle like '%ID : " + contentId
            + "%' and sender=-9999";

        // TODO SY需求需要阻止删除系统消息记录
        // pe.update( sql );
    }

    public Long queryStartStepTrueIdByFlowId( Long flowId )
    {
        String sql = "select stepId from workflow_step_info where isStart=1 and flowId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { flowId }, Long.class );
    }

    public void deleteWorkflowStepNotStartStep( Long stepId )
    {
        String sql = "delete from workflow_step_info where stepId=? and isStart=0";

        pe.update( sql, new Object[] { stepId } );
    }

    public void deleteWorkflowStepAction( Long actionId )
    {
        String sql = "delete from workflow_step_action where actionId=?";

        pe.update( sql, new Object[] { actionId } );
    }

    public void updateWorkflowUpdateDT( Long dt, Long flowId )
    {
        String sql = "update workflow set updateDT=? where flowId=?";

        pe.update( sql, new Object[] { dt, flowId } );

    }

    public void updateWorkflowOperationStepAndWkUpdateDtInfo( Long contentId, Integer infoType,
        Long startStepId, Long updateDT )
    {
        String sql = "update workflow_operation set wkUpdateDT=?, currentStep=?, possessUserId=null, currentAuditUser=null, possessStatus=0 where contentId=? and infoType=?";

        pe.update( sql, new Object[] { updateDT, startStepId, contentId, infoType } );
    }

    public Long queryContentFormStep( Long contentId, Long toStepId )
    {
        String sql = "select actionId from workflow_oper_info where contentId=? and toStepId=? order by eventDT desc limit 1";

        return ( Long ) pe
            .querySingleObject( sql, new Object[] { contentId, toStepId }, Long.class );
    }

}
