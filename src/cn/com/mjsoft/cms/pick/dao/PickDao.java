package cn.com.mjsoft.cms.pick.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.pick.bean.PickContentRuleBean;
import cn.com.mjsoft.cms.pick.bean.PickContentTaskBean;
import cn.com.mjsoft.cms.pick.dao.vo.PickContentRule;
import cn.com.mjsoft.cms.pick.dao.vo.PickContentTask;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;

public class PickDao
{
    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public PickDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public void save( PickContentRule pickRule )
    {
        pe.save( pickRule );
    }

    public UpdateState save( PickContentTask pickTask )
    {
        return pe.save( pickTask );
    }

    public PickContentTaskBean querySinglePickTaskBeanById( Long taskId, Long siteId )
    {
        String sql = "select * from pick_content_task where pickTaskId=? and siteId=?";
        return ( PickContentTaskBean ) pe.querySingleRow( sql, new Object[] { taskId, siteId },
            new PickContentTaskBeanTransform() );
    }

    public List queryPickTaskBeanBySiteId( Long siteId, Integer type )
    {
        String sql = "select * from pick_content_task where siteId=? and pickType=? order by pickTaskId desc";
        return pe.query( sql, new Object[] { siteId, type }, new PickContentTaskBeanTransform() );
    }

    public List queryAllPickTaskBeanBySiteId( Long siteId )
    {
        String sql = "select * from pick_content_task where siteId=?  order by pickTaskId desc";
        return pe.query( sql, new Object[] { siteId }, new PickContentTaskBeanTransform() );
    }

    public List queryPickTaskIdBySiteId( Long siteId, Integer type )
    {
        String sql = "select pickTaskId from pick_content_task where siteId=? and pickType=?";
        return pe.querySingleCloumn( sql, new Object[] { siteId, type }, Long.class );
    }
    
    public List queryPickTaskIdBySiteId( Long siteId )
    {
        String sql = "select pickTaskId from pick_content_task where siteId=?";
        return pe.querySingleCloumn( sql, new Object[] { siteId }, Long.class );
    }

    public List queryAllPickTaskIdBySiteId( Long siteId )
    {
        String sql = "select pickTaskId from pick_content_task where siteId=?";
        return pe.querySingleCloumn( sql, new Object[] { siteId }, Long.class );
    }

    public PickContentTaskBean querySinglePickTaskBeanById( Long taskId )
    {
        String sql = "select * from pick_content_task where pickTaskId=?";
        return ( PickContentTaskBean ) pe.querySingleRow( sql, new Object[] { taskId },
            new PickContentTaskBeanTransform() );
    }

    public PickContentRuleBean querySinglePickRuleBeanById( Long ruleId, Long siteId )
    {
        String sql = "select * from pick_content_rule where pickCfgId=? and siteId=?";
        return ( PickContentRuleBean ) pe.querySingleRow( sql, new Object[] { ruleId, siteId },
            new PickContentRuleBeanTransform() );
    }

    public PickContentRuleBean querySinglePickRuleBeanById( Long ruleId )
    {
        String sql = "select * from pick_content_rule where pickCfgId=?";
        return ( PickContentRuleBean ) pe.querySingleRow( sql, new Object[] { ruleId },
            new PickContentRuleBeanTransform() );
    }

    public void update( PickContentRule pickRule )
    {
        String sql = "update pick_content_rule set configName=?, extModelId=?, timeFormat=?, listHeadUrlRule=?, listUrlRule=?, contentUrlRule=?, contentPageUrlRule=?, prefixSiteUrl=?, configDesc=?, listStart=?, listEnd=?, titleStart=?, titleEnd=?, contentStart=?, contentEnd=?, summaryStart=?, summaryEnd=?, addDateStart=?, addDateEnd=?, sourceStart=?, sourceEnd=?, authorStart=?, authorEnd=?, keywordStart=?, keywordEnd=?, htmlMode=? where pickCfgId=?";
        pe.update( sql, pickRule );
    }

    public void deletePickRule( Long id )
    {
        String sql = "delete from pick_content_rule where pickCfgId=?";
        pe.update( sql, new Object[] { id } );
    }

    public void deletePickRuleBySiteId( Long siteId )
    {
        String sql = "delete from pick_content_rule where siteId=?";
        pe.update( sql, new Object[] { siteId } );
    }

    public void deletePickTask( Long id )
    {
        String sql = "delete from pick_content_task where pickTaskId=?";
        pe.update( sql, new Object[] { id } );
    }

    public void updateTaskJobId( Long taskId, Long jobId )
    {
        String sql = "update pick_content_task set selfJobId=? where pickTaskId=?";
        pe.update( sql, new Object[] { jobId, taskId } );
    }

    public Long queryTaskIdByJobId( Long jobId )
    {
        String sql = "select pickTaskId from pick_content_task where selfJobId=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { jobId }, Long.class );
    }

    public void update( PickContentTask pickTask )
    {
        String sql = "update pick_content_task set taskName=?, ruleId=?, pickType=?, rssUrl=?,  dbRuleBeClass=?, classId=?, taskDesc=?, pickThreadCount=?, pickInterval=?, pickMaxListPage=?, pickMaxContent=?, censorMode=?, period=?, periodType=?, downOutImg=?, guideImgPos=?, deleteHref=? where pickTaskId=?";
        pe.update( sql, pickTask );

    }

    public void updateExcuteDate( Long taskId, Timestamp exd )
    {
        String sql = "update pick_content_task set excuteDT=? where pickTaskId=?";
        pe.update( sql, new Object[] { exd, taskId } );

    }

    public void savePickTrace( String title, Long classId, String url, Long ruleId,
        Timestamp eventDT, String tagStr, String author, Integer isSucc, Long siteId )
    {
        String sql = "insert into pick_web_trace (title, targetUrl, classId, selfRuleId, eventDT, tags, author, pickSucc, siteId) values (?, ?, ?, ? ,?, ?, ?, ?, ?)";
        pe.update( sql, new Object[] { title, url, classId, ruleId, eventDT, tagStr, author,
            isSucc, siteId } );
    }

    public Long queryPickWebTraceCountBySiteId( Long siteId )
    {
        String sql = "select count(*) from pick_web_trace where siteId=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId }, Long.class );
    }

    public Long queryPickWebTraceCountBySiteId( Long siteId, Long rid )
    {
        String sql = "select count(*) from pick_web_trace where siteId=? and selfRuleId=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, rid }, Long.class );
    }

    public List queryAllPickWebTraceSource()
    {
        String sql = "select author from pick_web_trace";

        return pe.querySingleCloumn( sql, String.class );
    }

    public List queryPickWebTrace( Long siteId, Long startId, Integer size )
    {
        String sql = "select * from pick_web_trace where siteId=? order by eventDT desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { siteId, startId, size } );
    }

    public List queryPickWebTrace( Long siteId, Long rid, Long startId, Integer size )
    {
        String sql = "select * from pick_web_trace where siteId=? and selfRuleId=? order by eventDT desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { siteId, rid, startId, size } );
    }

    public void deletePickWebTraceById( Long id )
    {
        String sql = "delete from pick_web_trace where tid=?";
        pe.update( sql, new Object[] { id } );
    }

    public void deleteAllPickWebTrace( Long siteId )
    {
        String sql = "delete from pick_web_trace where siteId=?";
        pe.update( sql, new Object[] { siteId } );
    }

    public Long queryTraceTitleCount( Long siteId, String title )
    {
        String sql = "select count(*) from pick_web_trace where siteId=? and title=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, title }, Long.class );
    }

    public List queryPickModelExtField( Long eprId, String fieldSign )
    {
        String sql = "select * from pick_model_ext_field where eprId=? and fieldSign=?";

        return pe.queryResultMap( sql, new Object[] { eprId, fieldSign } );
    }

    public List queryPickModelExt()
    {
        String sql = "select * from pick_model_ext order by eprId desc";

        return pe.queryResultMap( sql );
    }

    public List queryPickModelExtByRuleId( Long eprId )
    {
        String sql = "select * from pick_model_ext_field where eprId=?";

        return pe.queryResultMap( sql, new Object[] { eprId } );
    }

    public Map querySinglePickModelExt( Long eprId )
    {
        String sql = "select * from pick_model_ext where eprId=?";

        return pe.querySingleResultMap( sql, new Object[] { eprId } );
    }

    public UpdateState savePickModelExt( Long modelId, String eprName, String eprDesc )
    {
        String sql = "insert into pick_model_ext (modelId, eprName, eprDesc) values (?, ?, ?)";

        return pe.insert( sql, new Object[] { modelId, eprName, eprDesc } );
    }

    public void updatePickModelExt( Map params )
    {
        String sql = "update pick_model_ext set  eprName=?, eprDesc=? where eprId=? ";

        pe.update( sql, params );
    }

    public void savePickModelExtField( Long eprlId, String field, String colStart, String colEnd )
    {
        String sql = "insert into pick_model_ext_field (eprId, fieldSign, colStart, colEnd) values (?, ?, ?, ?)";

        pe.update( sql, new Object[] { eprlId, field, colStart, colEnd } );
    }

    public void deletePickModelExt( Long eprId )
    {
        String sql = "delete from pick_model_ext where eprId=?";

        pe.update( sql, new Object[] { eprId } );
    }

    public void deletePickModelExtField( Long eprlId )
    {
        String sql = "delete from pick_model_ext_field where eprId=?";

        pe.update( sql, new Object[] { eprlId } );
    }

}
