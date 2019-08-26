package cn.com.mjsoft.cms.questionnaire.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.questionnaire.bean.SurveyBaseInfoBean;
import cn.com.mjsoft.cms.questionnaire.bean.SurveyGroupBean;
import cn.com.mjsoft.cms.questionnaire.dao.vo.SurveyBaseInfo;
import cn.com.mjsoft.cms.questionnaire.dao.vo.SurveyGroup;
import cn.com.mjsoft.cms.questionnaire.dao.vo.SurveyOptionInfo;
import cn.com.mjsoft.cms.questionnaire.dao.vo.SurveyVoteInfo;
import cn.com.mjsoft.framework.cache.Cache;

import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;

@SuppressWarnings( "unchecked" )
public class SurveyDao
{
    private static Map cacheManager = new HashMap();

    static
    {
        cacheManager.put( "querySingleQuestBaseInfoBeanByQuestFlag", new ClusterCacheAdapter( 800,
            "surveyDao.querySingleQuestBaseInfoBeanByQuestFlag" ) );

        cacheManager.put( "querySurveyBaseInfoBeanListByGroupFlag", new ClusterCacheAdapter( 800,
            "surveyDao.querySurveyBaseInfoBeanListByGroupFlag" ) );

        cacheManager.put( "querySurveyOptionBeanListBySurveyId", new ClusterCacheAdapter( 3000,
            "surveyDao.querySurveyOptionBeanListBySurveyId" ) );

        cacheManager.put( "querySurveyVoteInfoCountBySurveyId", new ClusterCacheAdapter( 400,
            "surveyDao.querySurveyVoteInfoCountBySurveyId" ) );

        cacheManager.put( "querySurveyVoteInfoBySurveyId", new ClusterCacheAdapter( 600,
            "surveyDao.querySurveyVoteInfoBySurveyId" ) );

        cacheManager.put( "querySingleQuestBaseInfoBeanByQuestId", new ClusterCacheAdapter( 4000,
            "surveyDao.querySingleQuestBaseInfoBeanByQuestId" ) );

        cacheManager.put( "querySurveyGroupBean", new ClusterCacheAdapter( 6000,
            "surveyDao.querySurveyGroupBean" ) );

    }

    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public SurveyDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public UpdateState saveSurveyGroup( SurveyGroup quest )
    {
        return pe.save( quest );
    }

    public UpdateState saveSurveyBaseInfo( SurveyBaseInfo survey )
    {
        return pe.save( survey );
    }

    public UpdateState saveSurveyOptionInfo( SurveyOptionInfo option )
    {
        return pe.save( option );
    }

    public Long queryAllQuestBaseInfoBeanCount( Long siteId )
    {
        String sql = "select count(*) from survey_group where siteId=?";

        Cache cache = ( Cache ) cacheManager.get( "querySurveyGroupBean" );

        String key = "queryAllQuestBaseInfoBeanCount:" + siteId;

        Long result = ( Long ) cache.getEntry( key );

        if( result == null )
        {
            result = ( Long ) pe.querySingleObject( sql, new Object[] { siteId }, Long.class );

            cache.putEntry( key, result );
        }

        return result;
    }

    public List queryAllQuestBaseInfoBeanList( Long siteId, Long start, Integer size )
    {
        String sql = "select * from survey_group where siteId=? order by groupId desc limit ?,? ";

        Cache cache = ( Cache ) cacheManager.get( "querySurveyGroupBean" );

        String key = "queryAllQuestBaseInfoBeanList:" + siteId + "|" + start + "|" + size;

        List result = ( List ) cache.getEntry( key );

        if( result == null )
        {
            result = pe.query( sql, new Object[] { siteId, start, size },
                new SurveyGroupBeanTransform() );

            cache.putEntry( key, result );
        }

        return result;
    }

    public SurveyGroupBean querySingleQuestBaseInfoBeanByQuestId( Long groupId )
    {
        String sql = "select * from survey_group where groupId=?";

        Cache cache = ( Cache ) cacheManager.get( "querySingleQuestBaseInfoBeanByQuestId" );

        SurveyGroupBean result = ( SurveyGroupBean ) cache.getEntry( groupId );

        if( result == null )
        {
            result = ( SurveyGroupBean ) pe.querySingleRow( sql, new Object[] { groupId },
                new SurveyGroupBeanTransform() );

            cache.putEntry( groupId, result );
        }

        return result;
    }

    public SurveyGroupBean querySingleQuestBaseInfoBeanByQuestFlag( String flagName )
    {
        String sql = "select * from survey_group where flagName=?";

        if( flagName == null )
        {
            return null;
        }

        Cache cache = ( Cache ) cacheManager.get( "querySingleQuestBaseInfoBeanByQuestFlag" );

        SurveyGroupBean bean = ( SurveyGroupBean ) cache.getEntry( flagName );

        if( bean == null )
        {
            bean = ( SurveyGroupBean ) pe.querySingleRow( sql, new Object[] { flagName },
                new SurveyGroupBeanTransform() );

            if( bean == null )
            {
                bean = new SurveyGroupBean();
            }

            cache.putEntry( flagName, bean );
        }

        return bean;
    }

    public List querySurveyBaseInfoBeanListByGroupId( Long groupId )
    {
        String sql = "select * from survey_base_info where groupId=? order by orderFlag asc";

        Cache cache = ( Cache ) cacheManager.get( "querySurveyBaseInfoBeanListByGroupFlag" );

        String key = "querySurveyBaseInfoBeanListByGroupId:" + groupId;

        List result = ( List ) cache.getEntry( key );

        if( result == null )
        {
            result = pe.query( sql, new Object[] { groupId }, new SurveyBaseInfoBeanTransform() );

            cache.putEntry( key, result );
        }

        return result;
    }

    public List querySurveyBaseInfoBeanListByGroupFlag( String groupFlag )
    {
        String sql = "select * from survey_base_info where groupFlag=? order by orderFlag asc";

        Cache cache = ( Cache ) cacheManager.get( "querySurveyBaseInfoBeanListByGroupFlag" );

        String key = "querySurveyBaseInfoBeanListByGroupFlag:" + groupFlag;

        List result = ( List ) cache.getEntry( key );

        if( result == null )
        {
            result = pe.query( sql, new Object[] { groupFlag }, new SurveyBaseInfoBeanTransform() );

            cache.putEntry( key, result );
        }

        return result;

    }

    public SurveyBaseInfoBean querySingleSurveyBaseInfoBeanBySurveyId( Long surveyId )
    {
        String sql = "select * from survey_base_info where surveyId=?";

        Cache cache = ( Cache ) cacheManager.get( "querySingleQuestBaseInfoBeanByQuestId" );

        String key = "querySingleSurveyBaseInfoBeanBySurveyId:" + surveyId;

        SurveyBaseInfoBean result = ( SurveyBaseInfoBean ) cache.getEntry( key );

        if( result == null )
        {
            result = ( SurveyBaseInfoBean ) pe.querySingleRow( sql, new Object[] { surveyId },
                new SurveyBaseInfoBeanTransform() );

            cache.putEntry( key, result );
        }

        return result;
    }

    public List querySurveyOptionBeanListBySurveyId( Long surveyId )
    {
        String sql = "select * from survey_option_info where surveyId=?";

        Cache cache = ( Cache ) cacheManager.get( "querySurveyOptionBeanListBySurveyId" );

        String key = "querySurveyOptionBeanListBySurveyId:" + surveyId;

        List result = ( List ) cache.getEntry( key );

        if( result == null )
        {
            result = pe.query( sql, new Object[] { surveyId }, new SurveyOptionInfoBeanTransform() );

            cache.putEntry( key, result );
        }

        return result;
    }

    public List querySurveyOptionBeanListBySurveyIdNoCache( Long surveyId )
    {
        String sql = "select * from survey_option_info where surveyId=?";

        return pe.query( sql, new Object[] { surveyId }, new SurveyOptionInfoBeanTransform() );
    }

    public Integer queryMaxSurveyOrderFlag( Long groupId )
    {
        String sql = "select max(orderFlag) from survey_base_info where groupId=?";

        return ( Integer ) pe.querySingleObject( sql, new Object[] { groupId }, Integer.class );
    }

    public void updateVoteCount( Long optionId, Integer count )
    {
        String sql = "update survey_option_info set vote=vote+? where optionId=?";

        pe.update( sql, new Object[] { count, optionId } );
    }

    public void updateVotePercent( Long optionId, Integer per )
    {
        String sql = "update survey_option_info set votePer=? where optionId=?";

        pe.update( sql, new Object[] { per, optionId } );
    }

    public void saveIpVoteTrace( String ip, Long groupId, String currentTime )
    {
        String sql = "insert into survey_vote_ip_trace (ip, surveyGroupId, lastVoteDateTime) values (?,?,?)";
        pe.update( sql, new Object[] { ip, groupId, currentTime } );
    }

    public void updateIpVoteTrace( String ip, Long groupId, String currentTime )
    {
        String sql = "update survey_vote_ip_trace set lastVoteDateTime=? where ip=? and surveyGroupId=?";
        pe.update( sql, new Object[] { currentTime, ip, groupId, } );
    }

    public String queryIpVoteTraceDateTime( String ip, Long groupId )
    {
        String sql = "select lastVoteDateTime from survey_vote_ip_trace where ip=? and surveyGroupId=?";
        return ( String ) pe.querySingleObject( sql, new Object[] { ip, groupId }, String.class );
    }

    public SurveyBaseInfoBean queryFirstLessOrderFlagBySurveyId( Long surveyId, String groupFlag )
    {
        String sql = "select * from survey_base_info sbi1 where sbi1.orderFlag < (select sbi2.orderFlag from survey_base_info sbi2 where sbi2.surveyId=?) and sbi1.groupFlag=? order by sbi1.orderFlag desc limit 1";

        return ( SurveyBaseInfoBean ) pe.querySingleRow( sql, new Object[] { surveyId, groupFlag },
            new SurveyBaseInfoBeanTransform() );
    }

    public SurveyBaseInfoBean queryFirstBigOrderFlagBySurveyId( Long surveyId, String groupFlag )
    {
        String sql = "select * from survey_base_info sbi1 where sbi1.orderFlag > (select sbi2.orderFlag from survey_base_info sbi2 where sbi2.surveyId=?) and sbi1.groupFlag=? order by sbi1.orderFlag asc limit 1";

        return ( SurveyBaseInfoBean ) pe.querySingleRow( sql, new Object[] { surveyId, groupFlag },
            new SurveyBaseInfoBeanTransform() );
    }

    public void saveVoteInfo( SurveyVoteInfo vote )
    {
        pe.save( vote );
    }

    public void updateSurveyBaseInfo( SurveyBaseInfo survey )
    {
        String sql = "update survey_base_info set optionType=?, surveyTitle=?, haveText=?, addiTitle=? where surveyId=?";
        pe.update( sql, survey );
    }

    public void deletesSurveyOptionBySurveyId( Long surveyId )
    {
        String sql = "delete from survey_option_info where surveyId=?";

        pe.update( sql, new Object[] { surveyId } );
    }

    public void deletesSurveyBaseInfoBySurveyId( Long id )
    {
        String sql = "delete from survey_base_info where surveyId=?";

        pe.update( sql, new Object[] { id } );
    }

    public void deleteSurveyVoteInfoBySurveyId( Long id )
    {
        String sql = "delete from survey_vote_info where surveyId=?";

        pe.update( sql, new Object[] { id } );
    }

    public Long querySurveyVoteInfoCountBySurveyId( Long id )
    {
        String sql = "select count(*) from survey_vote_info where surveyId=?";

        String key = "querySurveyVoteInfoCountBySurveyId:" + id;

        Cache cache = ( Cache ) cacheManager.get( "querySurveyVoteInfoCountBySurveyId" );

        Long result = ( Long ) cache.getEntry( key );

        if( result == null )
        {
            result = ( Long ) pe.querySingleObject( sql, new Object[] { id } );

            cache.putEntry( key, result );
        }

        return result;
    }

    public List querySurveyVoteInfoBySurveyId( Long id, Long start, Integer size )
    {
        String sql = "select * from survey_vote_info where surveyId=? order by voteDate desc limit ?,?";

        String key = "querySurveyVoteInfoBySurveyId:" + id + "|" + start + "|" + size;

        Cache cache = ( Cache ) cacheManager.get( "querySurveyVoteInfoBySurveyId" );

        List result = ( List ) cache.getEntry( key );

        if( result == null )
        {
            result = pe.queryResultMap( sql, new Object[] { id, start, size } );

            cache.putEntry( key, result );
        }

        return result;
    }

    public void deleteSurveyGroupByGroupId( Long id )
    {
        String sql = "delete from survey_group where groupId=?";

        pe.update( sql, new Object[] { id } );
    }

    public void updateSurveyOrderFlag( Long surveyId, Integer currentFlag )
    {
        String sql = "update survey_base_info set orderFlag=? where surveyId=?";
        pe.update( sql, new Object[] { currentFlag, surveyId } );
    }

    public Long querySurveyGroupBeanCountByClassId( String classId, Long siteId )
    {
        String sql = "select count(*) from survey_group where classId=? and siteId=?";

        Cache cache = ( Cache ) cacheManager.get( "querySurveyGroupBean" );

        String key = "querySurveyGroupBeanCountByClassId:" + classId + "|" + siteId;

        Long result = ( Long ) cache.getEntry( key );

        if( result == null )
        {
            result = ( Long ) pe.querySingleObject( sql, new Object[] { classId, siteId },
                Long.class );

            cache.putEntry( key, result );
        }

        return result;
    }

    public List querySurveyGroupBeanListByClassId( Long siteId, String classId, Long start,
        Integer size )
    {
        String sql = "select * from survey_group where siteId=? and classId=? order by groupId desc limit ?,?";

        Cache cache = ( Cache ) cacheManager.get( "querySurveyGroupBean" );

        String key = "querySurveyGroupBeanListByClassId:" + classId + "|" + siteId + "|" + start
            + "|" + size;

        List result = ( List ) cache.getEntry( key );

        if( result == null )
        {
            result = pe.query( sql, new Object[] { siteId, classId, start, size },
                new SurveyGroupBeanTransform() );

            cache.putEntry( key, result );
        }

        return result;
    }

    public List querySurveyGroupIdListBySiteId( Long siteId )
    {
        String sql = "select groupId from survey_group where siteId=? ";

        return pe.querySingleCloumn( sql, new Object[] { siteId }, Long.class );
    }

    public void updateSurveyGroup( SurveyGroup quest )
    {
        String sql = "update survey_group set classId=?, questName=?, flagName=?, questDesc=?, restriction=?, restInterval=?, startDate=?, endDate=?, needCaptcha=?, useState=? where groupId=?";
        pe.update( sql, quest );
    }

    public static void clearCache()
    {
        Cache cache = ( Cache ) cacheManager.get( "querySingleQuestBaseInfoBeanByQuestFlag" );

        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "querySurveyBaseInfoBeanListByGroupFlag" );

        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "querySurveyOptionBeanListBySurveyId" );

        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "querySurveyVoteInfoCountBySurveyId" );

        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "querySurveyVoteInfoBySurveyId" );

        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "querySingleQuestBaseInfoBeanByQuestId" );

        cache.clearAllEntry();

        cache = ( Cache ) cacheManager.get( "querySurveyGroupBean" );

        cache.clearAllEntry();
     }

     

}
