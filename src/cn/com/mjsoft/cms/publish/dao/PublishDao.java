package cn.com.mjsoft.cms.publish.dao;

import java.util.List;

import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.publish.bean.PublishRuleBean;
import cn.com.mjsoft.cms.publish.dao.vo.PublishRule;
import cn.com.mjsoft.framework.cache.Cache;

import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;

public class PublishDao
{
    private static Cache singleCache = new ClusterCacheAdapter( 80, "publishDao.singleCache" );

    private static Cache typeCache = new ClusterCacheAdapter( 5, "publishDao.typeCache" );

    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public PublishDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public PublishRuleBean querySinglePublishRuleBeanByRuleId( Long ruleId )
    {
        String sql = "select * from publish_rule where ruleId=?";

        if( ruleId == null || ruleId.longValue() < 1 )
        {
            return null;
        }

        String key = "querySinglePublishRuleBeanByRuleId:" + ruleId;

        PublishRuleBean result = ( PublishRuleBean ) singleCache.getEntry( key );

        if( result != null && result.getRuleId().longValue() < 0 )
        {
            return null;
        }

        if( result == null )
        {
            result = ( PublishRuleBean ) pe.querySingleRow( sql, new Object[] { ruleId },
                new PublishRuleBeanTransform() );

            if( result == null )
            {
                // null bean
                result = new PublishRuleBean();
            }

            singleCache.putEntry( key, result );
        }

        return result;
    }

    public List queryPublishRuleBeanByType( Integer type, Long siteId )
    {
        String sql = "select * from publish_rule where type=? and siteId=?";

        String key = "queryPublishRuleBeanByType:" + type + "|" + siteId;

        List result = ( List ) typeCache.getEntry( key );

        if( result == null )
        {
            result = pe.query( sql, new Object[] { type, siteId }, new PublishRuleBeanTransform() );
            typeCache.putEntry( key, result );
        }

        return result;
    }

    public List queryPublishRuleBeanByType( Integer type )
    {
        String sql = "select * from publish_rule where type=?";

        String key = "queryPublishRuleBeanByType:" + type;

        List result = ( List ) typeCache.getEntry( key );

        if( result == null )
        {
            result = pe.query( sql, new Object[] { type }, new PublishRuleBeanTransform() );
            typeCache.putEntry( key, result );
        }

        return result;
    }

    public List queryAllPublishRuleBean( Long siteId )
    {
        String sql = "select * from publish_rule where siteId=?";
        return pe.query( sql, new Object[] { siteId }, new PublishRuleBeanTransform() );
    }

    public List queryAllPublishRuleBean()
    {
        String sql = "select * from publish_rule";
        return pe.query( sql, new PublishRuleBeanTransform() );
    }

    public void updatePublishRule( PublishRule rule )
    {
        String sql = "update publish_rule set ruleName=?, type=?, savePathRule=?, savePathParam=?, fileNameRule=?, fileNameParam=?, suffixRule=?, pagePathParam=?, pagePathRule=?, pageFileNameRule=?, pageFileNameParam=? where ruleId=?";

        pe.update( sql, rule );

    }

    public UpdateState save( PublishRule rule )
    {
        return pe.save( rule );
    }

    public void deletePublishRuleById( Long ruleId )
    {
        String sql = "delete from publish_rule where ruleId=?";

        pe.update( sql, new Object[] { ruleId } );
    }

    public void deletePublishRuleBySiteId( Long siteId )
    {
        String sql = "delete from publish_rule where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void updateClassTemplateUrlPublishRuleIdForAllClass( Long newRId, Long ruleId )
    {
        String sql = "update contentclass set classPublishRuleId=? where classPublishRuleId=?";

        pe.update( sql, new Object[] { newRId, ruleId } );

    }

    public void updateClassHomeTemplateUrlPublishRuleIdForAllClass( Long newRId, Long ruleId )
    {
        String sql = "update contentclass set classHomePublishRuleId=? where classHomePublishRuleId=?";

        pe.update( sql, new Object[] { newRId, ruleId } );

    }

    public void updateContentTemplateUrlPublishRuleIdForAllClass( Long newRId, Long ruleId )
    {
        String sql = "update contentclass set contentPublishRuleId=? where contentPublishRuleId=?";

        pe.update( sql, new Object[] { newRId, ruleId } );

    }

    public static void clearCache()
    {
        singleCache.clearAllEntry();
        typeCache.clearAllEntry();
    }

 
}
