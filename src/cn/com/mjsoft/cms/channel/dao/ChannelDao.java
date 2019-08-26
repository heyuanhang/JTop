package cn.com.mjsoft.cms.channel.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.bean.ContentClassNodeInfoBean;
import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.cms.channel.bean.ContentTypeBean;
import cn.com.mjsoft.cms.channel.bean.TagWordBean;
import cn.com.mjsoft.cms.channel.dao.vo.ContentClass;
import cn.com.mjsoft.cms.channel.dao.vo.ContentCommendType;
import cn.com.mjsoft.cms.channel.dao.vo.ContentType;
import cn.com.mjsoft.cms.channel.dao.vo.TagWord;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.publish.dao.PublishPageAssistantAllMapInfoTransform;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;

@SuppressWarnings( "unchecked" )
public class ChannelDao
{
    private static Logger log = Logger.getLogger( ChannelDao.class );

    private static Map cacheManager = new HashMap();
    private PersistenceEngine pe;

    static
    {
        cacheManager.put( "getClassByParentIdSQL", new ClusterCacheAdapter( 1200,
            "channelDao.getClassByParentIdSQL" ) );

        cacheManager.put( "getClassByParentIdAndShowStatusSQL", new ClusterCacheAdapter( 500,
            "channelDao.getClassByParentIdAndShowStatusSQL" ) );
        cacheManager.put( "getClassBySomeIdsSQL", new ClusterCacheAdapter( 500,
            "channelDao.getClassBySomeIdsSQL" ) );

        cacheManager
            .put( "allNodeObject", new ClusterCacheAdapter( 5, "channelDao.allNodeObject" ) );

        cacheManager.put( "querySingleTagBean", new ClusterCacheAdapter( 3000,
            "channelDao.querySingleTagBean" ) );

        cacheManager.put( "queryContentClassIdByPreLinear", new ClusterCacheAdapter( 1500,
            "channelDao.queryContentClassIdByPreLinear" ) );

        cacheManager.put( "queryContentClassBeanChannelPath", new ClusterCacheAdapter( 1500,
            "channelDao.queryContentClassBeanChannelPath" ) );
    }

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public ChannelDao()
    {
    }

    public ChannelDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public String queryLayerCacheByClassId( Long classId )
    {
        log.info( "[DAO方法] queryLayerCacheByClassId(), [参数] classId:" + classId );

        String classQuerySql = "";
        classQuerySql = ( String ) pe.querySingleObject(
            "select layerQuerySqlCache from syscacheclasslayer where classId=?",
            new Object[] { classId }, String.class );

        return classQuerySql;
    }

    public UpdateState saveContentClass( ContentClass contentclass )
    {
        log.info( "[DAO方法] saveContentClass(), [参数] contentclass:" + contentclass );
        return pe.save( contentclass );
    }

    public void updateContentClassPath( Long classId, String path )
    {
        log.info( "[DAO方法] updateContentClassPath(), [参数] classId:" + classId + " ,path:" + path );

        String sql = "update contentclass set channelPath=? where classId=?";

        pe.update( sql, new Object[] { path, classId } );
    }

    public void saveContentClassLayerCache( Long classId, String layerClassIdCacheSql )
    {
        log.info( "[DAO方法] saveContentClassLayerCache() [参数] classId:" + classId
            + " ,layerClassIdCacheSql:" + layerClassIdCacheSql );

        String sql = "insert into syscacheclasslayer (classId,layerQuerySqlCache) values (?,?)";
        pe.update( sql, new Object[] { classId, layerClassIdCacheSql } );
    }

    public void updateContentClassById( ContentClass contentClass )
    {
        log.info( "[DAO方法] updateContentClassById() [参数] contentclass:" + contentClass );

        String sql = "update contentclass set classFlag=?, linkFromClass=?, linkToClass=?, className=?, classType=?, orgMode=?, classDesc=?, contentType=?, workflowId=?, outLink=?, logoImage=?, banner=?, classHomeTemplateUrl=?, classTemplateUrl=?, contentTemplateUrl=?, "
            + " mobClassHomeTemplateUrl=?, mobClassTemplateUrl=?, mobContentTemplateUrl=?,"
            + " padClassHomeTemplateUrl=?, padClassTemplateUrl=?, padContentTemplateUrl=?,"
            + " listPageLimit=?, classHomeProduceType=?, classProduceType=?, contentProduceType=?, showStatus=?, syncPubClass=?, useStatus=?, relateRangeType=?, contentPublishRuleId=?, classHomePublishRuleId=?, classPublishRuleId=?, openComment=?, mustCommentCensor=?, notMemberComment=?, commentCaptcha=?, filterCommentSensitive=?, commentHtml=?, sensitiveMode=?, seoTitle=?, seoKeyword=?, seoDesc=?, searchStatus=?, memberAddContent=?, extDataModelId=?, editorImageH=?, editorImageW=?, homeImageW=?, homeImageH=?, classImageW=?, classImageH=?, listImageW=?, listImageH=?, contentImageW=?, contentImageH=?, contentImageDM=?, listImageDM=?, classImageDM=?, homeImageDM=?, editorImageDM=?, editorImageMark=?, whiteIp=?, submitWhiteIp=?, submitDCBehavior=?, relateClassIds=? where classId=?";

        pe.update( sql, contentClass );
    }

    public void updateSpecialClassById( ContentClass contentClass )
    {
        log.info( "[DAO方法] updateSpecialClassById() [参数] contentclass:" + contentClass );

        String sql = "update contentclass set classFlag=?, className=? where classId=?";

        pe.update( sql, contentClass );
    }

    public void updateContentClassListProduceType( Long classId, Integer produceType )
    {
        String sql = "update contentclass set classProduceType=? where classId=?";

        pe.update( sql, new Object[] { produceType, classId } );
    }

    public void updateContentClassLayerCache( Long classId, String newClassIdCacheInfo )
    {
        log.info( "[DAO方法] updateContentClassLayerCache() [参数] classId:" + classId
            + " ,newClassIdCacheInfo:" + newClassIdCacheInfo );
        String sql = "update syscacheclasslayer set layerQuerySqlCache=concat(layerQuerySqlCache,?) where classId=?";
        pe.update( sql, new Object[] { newClassIdCacheInfo, classId } );
    }

    public void setContentClassLayerCache( Long classId, String classIdLayerCache )
    {
        log.info( "[DAO方法] setContentClassLayerCache() [参数] classId:" + classId
            + " ,classIdLayerCache:" + classIdLayerCache );
        String sql = "update syscacheclasslayer set layerQuerySqlCache=? where classId=?";
        pe.update( sql, new Object[] { classIdLayerCache, classId } );
    }

    public ContentClass getContentClassVoForClassID( Long classId )
    {
        log.info( "[DAO方法] getContentClassVoForClassID() [参数] classId:" + classId );

        String sql = "select * from contentclass where classId=?";

        ContentClass contentClass = ( ContentClass ) pe.querySingleRow( sql,
            new Object[] { classId }, new ContentClassVoTransform() );
        return contentClass;
    }

    public ContentClassBean querySingleClassBeanInfoByClassId( Long classId )
    {
        log.info( "[DAO方法] querySingleClassBeanInfoByClassId() [参数] classId:" + classId );
        String sql = "select * from contentclass where classId=?";

        return ( ContentClassBean ) pe.querySingleRow( sql, new Object[] { classId },
            new ContentClassBeanTransform() );
    }

    public ContentClassBean querySingleClassBeanInfoByClassFlag( String classFlag )
    {
        log.info( "[DAO方法] querySingleClassBeanInfoByClassFlag() [参数] classFalg:" + classFlag );
        String sql = "select * from contentclass where classFlag=?";

        return ( ContentClassBean ) pe.querySingleRow( sql, new Object[] { classFlag },
            new ContentClassBeanTransform() );
    }

    public List getChildrenContentClassVoForParentID( long parentClassId, Integer showFlag )
    {
        log.info( "[DAO方法] getChildrenContentClassVoForParentID() [参数] classId:" + parentClassId );

        String sql = "select * from contentclass where parent=? and showStatus=?";

        List result = pe.query( sql, new Object[] { Long.valueOf( parentClassId ), showFlag },
            new ContentClassVoTransform() );
        return result;
    }

    public List getChildrenContentClassBeanForParentIDAndShowStatus( long parentClassId,
        Integer showFlag, String siteFlag, Integer isSpec )
    {
        log.info( "[DAO方法] getChildrenContentClassBeanForParentID() [参数] parentClassId:"
            + parentClassId );

        String sql = "select * from contentclass where parent=? and showStatus=? and siteFlag=? and isSpecial=? order by linearOrderFlag";

        String key = Long.valueOf( parentClassId ) + "|" + showFlag + "|" + siteFlag + "|" + isSpec;

        List result = ( List ) ( ( Cache ) cacheManager.get( "getClassByParentIdAndShowStatusSQL" ) )
            .getEntry( key );
        if( result == null )
        {
            log.info( "[Cache] 放入缓存 : getClassByParentIdAndShowStatusSQL,[parentClassId]:"
                + parentClassId + ", [showFlag]:" + showFlag + ", [isSpec]:" + isSpec );

            result = pe.query( sql, new Object[] { Long.valueOf( parentClassId ), showFlag,
                siteFlag, isSpec }, new ContentClassBeanTransform() );

            ( ( Cache ) cacheManager.get( "getClassByParentIdAndShowStatusSQL" ) ).putEntry( key,
                result );
        }
        else
        {
            log.info( "[Cache] 命中缓存 : getClassByParentIdAndShowStatusSQL,[parentClassId]:"
                + parentClassId + ", [showFlag]:" + showFlag + ", [isSpec]:" + isSpec );
        }
        return result;
    }

    public void updateSinglePageModeClassContentId( Long classId, Long contentId )
    {
        String sql = "update contentclass set singleContentId=? where classId=?";

        pe.update( sql, new Object[] { contentId, classId } );
    }

    public List queryChildrenContentClassBeanForParentID( long parentClassId, String siteFlag,
        Integer isSpec )
    {
        log.info( "[DAO方法] queryChildrenContentClassBeanForParentID() [参数] parentClassId:"
            + parentClassId );

        String sql = "select * from contentclass where parent=? and isSpecial=? and siteFlag=? order by linearOrderFlag";

        String key = parentClassId + "|" + isSpec + "|" + siteFlag;

        List result = ( List ) ( ( Cache ) cacheManager.get( "getClassByParentIdSQL" ) )
            .getEntry( key );
        if( result == null )
        {
            log.info( "[Cache] 放入缓存 : getClassByParentIdSQL,[parentClassId]:" + parentClassId
                + ", [isSpec:]" + isSpec );

            result = pe.query( sql,
                new Object[] { Long.valueOf( parentClassId ), isSpec, siteFlag },
                new ContentClassBeanTransform() );

            ( ( Cache ) cacheManager.get( "getClassByParentIdSQL" ) ).putEntry( key, result );
        }
        else
        {
            log.info( "[Cache] 命中缓存 : getClassByParentIdSQL,[parentClassId]:" + parentClassId
                + ", [isSpec:]" + isSpec );
        }
        return result;
    }

    public Long queryChildrenContentClassCountForParentId( Long parentClassId, String siteFlag,
        Long classType )
    {
        log.info( "[DAO方法] queryChildrenContentClassCountForParentID() [参数] parentClassId:"
            + parentClassId + ", classType:" + classType );

        String sql = "select count(*) from contentclass where parent=? and siteFlag=? and classType=?";

        Long result = ( Long ) pe.querySingleObject( sql, new Object[] { parentClassId, siteFlag,
            classType }, Long.class );

        return result;
    }

    public List queryChildrenContentClassBeanForParentId( Long parentClassId, String siteFlag,
        Long classType, Long pos, Integer size )
    {
        log.info( "[DAO方法] queryChildrenContentClassBeanForParentID() [参数] parentClassId:"
            + parentClassId + ", classType:" + classType );

        String sql = "select * from contentclass where parent=? and siteFlag=? and classType=? order by linearOrderFlag limit ?,?";

        List result = pe.query( sql,
            new Object[] { parentClassId, siteFlag, classType, pos, size },
            new ContentClassBeanTransform() );

        return result;
    }

    public List queryChildrenContentClassBeanForParentId( Long parentClassId, String siteFlag,
        Long classType )
    {
        log.info( "[DAO方法] queryChildrenContentClassBeanForParentID() [参数] parentClassId:"
            + parentClassId + ", classType:" + classType );

        String sql = "select * from contentclass where parent=? and siteFlag=? and classType=? order by linearOrderFlag";

        List result = pe.query( sql, new Object[] { parentClassId, siteFlag, classType },
            new ContentClassBeanTransform() );

        return result;
    }

    public String queryChildrenContentClassIdNotSpecForParentId( Long parentClassId, String siteFlag )
    {
        String sql = "select classId from contentclass where parent=? and siteFlag=? and isSpecial=0 order by linearOrderFlag";

        Cache cache = ( Cache ) cacheManager.get( "getClassByParentIdSQL" );

        String key = "queryChildrenContentClassIdNotSpecForParentId" + parentClassId + "|"
            + siteFlag;

        String result = ( String ) cache.getEntry( key );
        if( result == null )
        {
            List clist = pe.querySingleCloumn( sql, new Object[] { parentClassId, siteFlag },
                Long.class );

            String classIds = StringUtil.changeStringArrayToStr( ( Long[] ) clist
                .toArray( new Long[0] ), "," );

            cache.putEntry( key, classIds );
        }
        return result;
    }

    public List queryChildrenContentClassBeanNotSpecForParentId( Long parentClassId, Long modelId,
        String siteFlag, String order )
    {
        log.info( "[DAO方法] queryChildrenContentClassBeanNotSpecForParentId() [参数] parentClassId:"
            + parentClassId );

        String sql = "select * from contentclass where parent=? and siteFlag=? and contentType=? and isSpecial=0 and showStatus=1 order by linearOrderFlag "
            + order;

        List result = pe.query( sql, new Object[] { parentClassId, siteFlag, modelId },
            new ContentClassBeanTransform() );

        return result;
    }

    public List queryChildrenContentClassBeanSpecModeForParentId( Long parentClassId, Long modelId,
        String siteFlag, String order )
    {
        log.info( "[DAO方法] queryChildrenContentClassBeanSpecModeForParentId() [参数] parentClassId:"
            + parentClassId );

        String sql = "select * from contentclass where parent=? and siteFlag=? and contentType=? and isSpecial=1 and showStatus=1 order by linearOrderFlag "
            + order;

        List result = pe.query( sql, new Object[] { parentClassId, siteFlag, modelId },
            new ContentClassBeanTransform() );

        return result;
    }

    public List queryChildrenContentClassBeanSpecCommModeForParentId( Long parentClassId,
        Long modelId, String siteFlag, String order )
    {
        log
            .info( "[DAO方法] queryChildrenContentClassBeanSpecCommModeForParentId() [参数] parentClassId:"
                + parentClassId );

        String sql = "select * from contentclass where parent=? and siteFlag=? and contentType=? and isSpecial=1 and isRecommend=1 and showStatus=1 order by linearOrderFlag "
            + order;

        List result = pe.query( sql, new Object[] { parentClassId, siteFlag, modelId },
            new ContentClassBeanTransform() );

        return result;
    }

    public List queryChildrenContentClassBeanNotSpecForParentId( Long parentClassId,
        String siteFlag, String order )
    {
        log.info( "[DAO方法] queryChildrenContentClassBeanNotSpecForParentId() [参数] parentClassId:"
            + parentClassId );

        String sql = "select * from contentclass where parent=? and siteFlag=? and isSpecial=0 and showStatus=1 order by linearOrderFlag "
            + order;

        List result = pe.query( sql, new Object[] { parentClassId, siteFlag },
            new ContentClassBeanTransform() );

        return result;
    }

    public List queryAllContentClassBeanNotSpecForSite( String siteFlag, String order )
    {
        String sql = "select * from contentclass where siteFlag=? and isSpecial=0 order by linearOrderFlag "
            + order;

        List result = pe.query( sql, new Object[] { siteFlag }, new ContentClassBeanTransform() );

        return result;
    }

    public List queryAllChildrenContentClassBeanNotSpecForParentLinear( String linear, String order )
    {
        String sql = "select * from contentclass where linearOrderFlag like '" + linear
            + "%' and isSpecial=0 order by linearOrderFlag " + order;

        List result = pe.query( sql, new ContentClassBeanTransform() );

        return result;
    }

    public List queryChildrenContentClassBeanSpecModeForParentId( Long parentClassId,
        String siteFlag, String order )
    {
        log.info( "[DAO方法] queryChildrenContentClassBeanSpecModeForParentId() [参数] parentClassId:"
            + parentClassId );

        String sql = "select * from contentclass where parent=? and siteFlag=? and isSpecial=1 and showStatus=1 order by linearOrderFlag "
            + order;

        List result = pe.query( sql, new Object[] { parentClassId, siteFlag },
            new ContentClassBeanTransform() );

        return result;
    }

    public List queryChildrenContentClassBeanSpecCommModeForParentId( Long parentClassId,
        String siteFlag, String order )
    {
        log
            .info( "[DAO方法] queryChildrenContentClassBeanSpecCommModeForParentId() [参数] parentClassId:"
                + parentClassId );

        String sql = "select * from contentclass where parent=? and siteFlag=? and isSpecial=1 and isRecommend=1 and showStatus=1 order by linearOrderFlag "
            + order;

        List result = pe.query( sql, new Object[] { parentClassId, siteFlag },
            new ContentClassBeanTransform() );

        return result;
    }

    public List getChildrenContentClassForParentClassName( String name, Integer showFlag )
    {
        log.info( "[DAO方法] getChildrenContentClassForParentClassName() [参数] name:" + name );
        String sql = " select * from contentclass where parent=(select classId from contentclass where className=?) and showStatus=? order by linearOrderFlag asc";

        List result = pe.query( sql, new Object[] { name, showFlag },
            new ContentClassBeanTransform() );

        return result;
    }

    public Long queryUpBrotherNodeByClassIdAndTheirParentId( Long classId, Long parentId )
    {
        log.info( "[DAO方法] queryUpBrotherNodeByClassIdAndTheirParentId() [参数] classId:" + classId
            + " ,parentId:" + parentId );

        String sql = "select classId from contentclass where linearOrderFlag <(select linearOrderFlag from contentclass where classId=?) and linearOrderFlag>(select linearOrderFlag from contentclass where classId=?)  and  layer=(select layer from contentclass where classId=?) order by linearOrderFlag desc limit 1";

        String sqlForTopLayerNode = "select classId from contentclass where linearOrderFlag <(select linearOrderFlag from contentclass where classId=?) and layer=(select layer from contentclass where classId=?) order by linearOrderFlag desc limit 1";

        Long result = null;
        if( parentId.longValue() == -9999L )
        {
            result = ( Long ) pe.querySingleObject( sqlForTopLayerNode, new Object[] { classId,
                classId }, Long.class );
        }
        else
        {
            result = ( Long ) pe.querySingleObject( sql,
                new Object[] { classId, parentId, classId }, Long.class );
        }
        return result;
    }

    public List getAllContentClassBeanOrderByLinear( String order )
    {
        log.info( "[DAO方法] getAllContentClassBeanOrderByLinear() [参数] order:" + order );

        Cache cache = ( Cache ) cacheManager.get( "allNodeObject" );
        List resultList = ( List ) cache.getEntry( "NodeObject" + order );
        if( resultList != null )
        {
            return resultList;
        }
        String sql = "select * from contentclass where isSpecial=0 order by linearOrderFlag "
            + order;

        resultList = pe.query( sql, new ContentClassBeanTransform() );
        cache.putEntry( "NodeObject" + order, resultList );

        return resultList;
    }

    public List getAllContentClassBeanOrderByLinear( Long contentId, String order )
    {
        String sql = "select * from contentclass where contentType=? and isSpecial=0 is order by linearOrderFlag "
            + order;

        List resultList = pe.query( sql, new Object[] { contentId },
            new ContentClassBeanTransform() );

        return resultList;
    }

    public List queryAllRootClassBeanInfoBySiteFlag( String siteFlag )
    {
        String sql = "select * from contentclass where siteFlag=? and parent=-9999 and classType<4 order by linearOrderFlag asc";
        return pe.query( sql, new Object[] { siteFlag }, new ContentClassBeanTransform() );
    }

    public List queryAllClassBeanInfoBySiteFlag( String siteFlag )
    {
        String sql = "select * from contentclass where siteFlag=? and classType<4 order by linearOrderFlag asc";
        return pe.query( sql, new Object[] { siteFlag }, new ContentClassBeanTransform() );
    }

    public List queryAllSpecClassBeanInfoBySiteFlag( String siteFlag )
    {
        String sql = "select * from contentclass where siteFlag=? and classType>=4 order by linearOrderFlag asc";
        return pe.query( sql, new Object[] { siteFlag }, new ContentClassBeanTransform() );
    }

    public List queryAllClassBeanInfoBySiteFlagAndContentPubStatus( String siteFlag,
        Integer contentPubStatus )
    {
        String sql = "select * from contentclass where siteFlag=? and classType<4 and contentProduceType=? order by linearOrderFlag asc";
        return pe.query( sql, new Object[] { siteFlag, contentPubStatus },
            new ContentClassBeanTransform() );
    }

    public List queryAllClassBeanInfoBySiteFlagAndChannelPubStatus( String siteFlag,
        Integer pubStatus )
    {
        String sql = "select * from contentclass where siteFlag=? and classType<4 and (classHomeProduceType=? or classProduceType=?) order by linearOrderFlag asc";
        return pe.query( sql, new Object[] { siteFlag, pubStatus, pubStatus },
            new ContentClassBeanTransform() );
    }

    public List queryAllClassBeanInfoBySiteFlagAndSpecClassPubStatus( String siteFlag,
        Integer pubStatus )
    {
        String sql = "select * from contentclass where siteFlag=? and classType>=4 and (classHomeProduceType=? or classProduceType=?) order by linearOrderFlag asc";
        return pe.query( sql, new Object[] { siteFlag, pubStatus, pubStatus },
            new ContentClassBeanTransform() );
    }

    public List getAllContentClassVoOrderByLinear( String order )
    {
        log.info( "[DAO方法] getAllContentClassVoOrderByLinear() [参数] order:" + order );

        String sql = "select * from contentclass cc inner join (select classId from contentclass order by linearOrderFlag "
            + order + ") as ids on cc.classId = ids.classId";

        List resultList = pe.query( sql, new ContentClassVoTransform() );
        return resultList;
    }

    public List getAllContentClassOrderByLinearAndType( String order, int contentType )
    {
        log.info( "[DAO方法] getAllContentClassOrderByLinearAndType() [参数] order:" + order
            + " ,contentType:" + contentType );

        String sql = "select sql_no_cache * from contentclass cc inner join (select classId from contentclass where contentType=? order by linearOrderFlag "
            + order + ") as ids on cc.classId = ids.classId";

        List resultList = pe.query( sql, new Object[] { Integer.valueOf( contentType ) },
            new ContentClassVoTransform() );
        return resultList;
    }

    public ContentClass queryLastChildClassForParentID( Long parentClassId )
    {
        log.info( "[DAO方法] queryLastChildClassForParentID() [参数] parentClassId:" + parentClassId );

        String sql = "select * from contentclass where parent=? order by linearOrderFlag desc limit 1";

        ContentClass contentClass = ( ContentClass ) pe.querySingleRow( sql,
            new Object[] { parentClassId }, new ContentClassVoTransform() );

        return contentClass;
    }

    public ContentClassNodeInfoBean queryLastChildClassNodeInfoForParent( Long parentClassId )
    {
        log.info( "[DAO方法] queryLastChildClassNodeInfoForParent() [参数] parentClassId:"
            + parentClassId );

        String sql = "select linearOrderFlag, isLastChild, isLeaf, parent, layer, classId, isSpecial from contentclass where parent=? order by linearOrderFlag desc limit 1";

        ContentClassNodeInfoBean bean = ( ContentClassNodeInfoBean ) pe.querySingleRow( sql,
            new Object[] { parentClassId }, new ContentClassNodeInfoBeanTransform() );

        return bean;
    }

    public List queryAllIncludeConetentClassBean( String siteFlag, String order, Integer isSpec )
    {
        log.info( "[DAO方法] queryAllIncludeConetentClassBean() [参数] order:" + order );
        if( ( !"asc".equals( order ) ) && ( !"desc".equals( order ) ) )
        {
            return new ArrayList( 1 );
        }
        String sql = "select * from contentclass where siteFlag=? and isSpecial=? order by linearOrderFlag "
            + order;

        List contentClassList = pe.query( sql, new Object[] { siteFlag, isSpec },
            new ContentClassBeanTransform() );

        return contentClassList;
    }

    public Long queryAllIncludeConetentClassCount( String siteFlag, Integer isSpec,
        Integer classType )
    {
        String sql = "select count(*) from contentclass where siteFlag=? and isSpecial=? and classType=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteFlag, isSpec, classType },
            Long.class );
    }

    public List queryAllIncludeConetentClassBean( String siteFlag, String order, Integer isSpec,
        Integer classType, Long pos, Integer size )
    {
        log.info( "[DAO方法] queryAllIncludeConetentClassBean() [参数] order:" + order );
        if( ( !"asc".equals( order ) ) && ( !"desc".equals( order ) ) )
        {
            return new ArrayList( 1 );
        }
        String sql = "select * from contentclass where siteFlag=? and isSpecial=? and classType=? order by linearOrderFlag "
            + order + " limit ?,?";

        List contentClassList = pe.query( sql, new Object[] { siteFlag, isSpec, classType, pos,
            size }, new ContentClassBeanTransform() );

        return contentClassList;
    }

    public List queryAllIncludeConetentClassBean( String siteFlag, String order, Integer isSpec,
        Integer classType )
    {
        log.info( "[DAO方法] queryAllIncludeConetentClassBean() [参数] order:" + order );
        if( ( !"asc".equals( order ) ) && ( !"desc".equals( order ) ) )
        {
            return new ArrayList( 1 );
        }
        String sql = "select * from contentclass where siteFlag=? and isSpecial=? and classType=? order by linearOrderFlag "
            + order;

        List contentClassList = pe.query( sql, new Object[] { siteFlag, isSpec, classType },
            new ContentClassBeanTransform() );

        return contentClassList;
    }

    public void updateContentClassLeafNodeState( Long classId, Boolean leafState )
    {
        log.info( "[DAO方法] updateContentClassLeafNodeState() [参数] classId:" + classId
            + " ,leafState" + leafState );

        String sql = "update contentclass set isLeaf=? where classId=?";
        pe.update( sql, new Object[] { leafState, classId } );
    }

    public boolean queryContentClassLeafNodeState( Long classId )
    {
        log.info( "[DAO方法] queryContentClassLeafNodeState() [参数] classId:" + classId );

        String sql = "select isLeaf from contentclass where classId=?";
        return ( ( Boolean ) pe.querySingleObject( sql, new Object[] { classId }, Boolean.class ) )
            .booleanValue();
    }

    public List getParentClassIdAndCurrentLayerInfoByChild( Long childId )
    {
        log.info( "[DAO方法] <getParentClassIdAndCurrentLayerInfoByChild() [参数] childId:" + childId );

        String sql = "select parent,layerQuerySqlCache from contentclass cc,syscacheclasslayer $scl where cc.classId=? and $scl.classId=cc.classId";

        return pe.queryResultMap( sql, new Object[] { childId } );
    }

    public void updateChannelClassStaticPageURL( String endStaticClassFilePath, Long classId )
    {
        log.info( "[DAO方法] updateChannelClassStaticPageURL() [参数] endStaticClassFilePath:"
            + endStaticClassFilePath + ", classId:" + classId );

        String sql = "update contentclass set staticPageURL=? where classId=?";
        pe.update( sql, new Object[] { endStaticClassFilePath, classId } );
    }

    public void updateChannelClassHomeStaticPageURL( String endStaticClassFilePath, Long classId )
    {
        log.info( "[DAO方法] updateChannelClassHomeStaticPageURL() [参数] endStaticClassFilePath:"
            + endStaticClassFilePath + ", classId:" + classId );

        String sql = "update contentclass set staticHomePageURL=? where classId=?";
        pe.update( sql, new Object[] { endStaticClassFilePath, classId } );
    }

    public void deleteContentClassAndChildrenClassByTargetId( Long targetClassId, String wCond )
    {
        log.info( "[DAO方法] deleteContentClassAndChildrenClassByTargetId() [参数] targetClassId:"
            + targetClassId + " ,wCond" + wCond );
        String sql = "delete from contentclass where (" + wCond + ")";

        pe.update( sql );
    }

    public int queryBrotherNodeCountBySelf( Long targetClassId )
    {
        log.info( "[DAO方法] queryBrotherNodeCountBySelf() [参数] targetClassId:" + targetClassId );

        String sql = "select count(*)-1 from contentclass where parent = (select parent from contentclass where classId=?)";
        return ( ( Integer ) pe.querySingleObject( sql, new Object[] { targetClassId },
            Integer.class ) ).intValue();
    }

    public void deleteLayerCacheInfoByLayerCache( String layerCache )
    {
        log.info( "[DAO方法] deleteLayerCacheInfoByLayerCache() [参数] layerCache:" + layerCache );

        String sql = "delete from syscacheclasslayer where (" + layerCache + ")";

        pe.update( sql );
    }

    public ContentClassNodeInfoBean queryContentClassNodeInfoByClassId( Long classId )
    {
        log.info( "[DAO方法] queryContentClassNodeInfoByClassId() [参数] classId:" + classId );

        String sql = "select linearOrderFlag, isLastChild, isLeaf, isSpecial, parent, layer, classId from contentclass where classId=?";

        return ( ContentClassNodeInfoBean ) pe.querySingleRow( sql, new Object[] { classId },
            new ContentClassNodeInfoBeanTransform() );
    }

    public Long queryParentIdByClassId( Long classId )
    {
        log.info( "[DAO方法] queryParentIdByClassId() [参数] classId:" + classId );

        String sql = "select parent from contentclass where classId=?";

        Long result = ( Long ) pe.querySingleObject( sql, new Object[] { classId }, Long.class );
        return result;
    }

    public List queryContentNodeInfoBeanByPreLinear( String preLinear )
    {
        log.info( "[DAO方法] queryContentNodeInfoBeanByPreLinear() [参数] preLinear:" + preLinear );

        String sql = "select linearOrderFlag, isLastChild, isLeaf, parent, layer, classId, isSpecial from contentclass where linearOrderFlag like'"
            + preLinear + "%'";

        List result = pe.query( sql, new ContentClassNodeInfoBeanTransform() );
        return result;
    }

    public List queryContentClassBeanByPreLinear( String preLinear )
    {
        log.info( "[DAO方法] queryContentClassBeanByPreLinear() [参数] preLinear:" + preLinear );

        String sql = "select * from contentclass where linearOrderFlag like'" + preLinear
            + "%' order by linearOrderFlag asc";

        List result = pe.query( sql, new ContentClassBeanTransform() );
        return result;
    }

    public List querySinglePageContentClassBean( String siteFlag )
    {
        String sql = "select * from contentclass where classType=3 and siteFlag=?";

        List result = pe.query( sql, new Object[] { siteFlag }, new ContentClassBeanTransform() );
        return result;
    }

    public String queryContentClassIdByPreLinear( String preLinear, String siteFlag )
    {
        log.info( "[DAO方法] queryContentClassIdByPreLinear() [参数] preLinear:" + preLinear );

        String key = "queryContentClassIdByPreLinear:" + preLinear + "|" + siteFlag;

        Cache cache = ( Cache ) cacheManager.get( "queryContentClassIdByPreLinear" );

        String result = ( String ) cache.getEntry( key );
        if( result == null )
        {
            String allSql = "select classId from contentclass where siteFlag=? and isSpecial=0 order by linearOrderFlag asc";

            String sql = "select classId from contentclass where linearOrderFlag like'" + preLinear
                + "%' order by linearOrderFlag asc";
            if( "000".equals( preLinear ) )
            {
                List cid = pe.querySingleCloumn( allSql, new Object[] { siteFlag }, Long.class );

                result = StringUtil.replaceString( cid.toString(), "[", "", false, false );

                result = StringUtil.replaceString( result, "]", "", false, false );
            }
            else
            {
                List cid = pe.querySingleCloumn( sql, Long.class );

                result = StringUtil.replaceString( cid.toString(), "[", "", false, false );

                result = StringUtil.replaceString( result, "]", "", false, false );
            }
            cache.putEntry( key, result );
        }
        return result;
    }

    public List queryContentClassBeanByModelId( String siteFlag, Long modelId )
    {
        log.info( "[DAO方法] queryContentClassBeanByModelId() [参数] modelId:" + modelId
            + ", siteFlag:" + siteFlag );

        String sql = "select * from contentclass where siteFlag=? and contentType=?";

        List result = pe.query( sql, new Object[] { siteFlag, modelId },
            new ContentClassBeanTransform() );
        return result;
    }

    public void updateContentClasslinearOrderFlagByClassId( Long classId, String linearFlag )
    {
        log.info( "[DAO方法] updateContentClasslinearOrderFlagByClassId() [参数] classId:" + classId
            + " ,linearFlag:" + linearFlag );

        String sql = "update contentclass set linearOrderFlag=? where classId=?";
        pe.update( sql, new Object[] { linearFlag, classId } );
    }

    public void updateContentClassLastChildFlag( Long classId, Boolean lastChildFlag )
    {
        log.info( "[DAO方法] updateContentClassLastChildFlag() [参数] classId:" + classId
            + " ,lastChildFlag:" + lastChildFlag );

        String sql = "update contentclass set isLastChild=? where classId=?";
        pe.update( sql, new Object[] { lastChildFlag, classId } );
    }

    public void test()
    {
        String sql = "update contentclass  set  linearOrderFlag=(select replace(linearOrderFlag,'001','888'')) as a  ";
        pe.update( sql );
        pe.startBatch();

        pe.executeBatch();
    }

    public void setContentClassParent( Long classId, Long selectParent )
    {
        log.info( "[DAO方法] setContentClassParent() [参数] classId:" + classId + " ,selectParent+:"
            + selectParent );
        String sql = "update contentclass set parent=? where classId=?";
        pe.update( sql, new Object[] { selectParent, classId } );
    }

    public boolean queryLastChildFlag( Long classId )
    {
        log.info( "[DAO方法] queryLastChildFlag() [参数] classId:" + classId );

        String sql = "select isLastChild from contentclass where classId=?";

        return ( ( Boolean ) pe.querySingleObject( sql, new Object[] { classId }, Boolean.class ) )
            .booleanValue();
    }

    public void updateContentClassNodeInfoByClassId( Long classId, Integer layer,
        String newLayerLinear )
    {
        log.info( "[DAO方法] updateContentClassNodeInfoByClassId() [参数] classId:" + classId
            + " ,layer:" + layer + " ,newLayerLinear:" + newLayerLinear );
        String sql = "update contentclass set layer=?, linearOrderFlag=? where classId=?";

        pe.update( sql, new Object[] { layer, newLayerLinear, classId } );
    }

    public void updateGuideImg( String targetDeleteImg, Long classId )
    {
        log.info( "[DAO方法] updateGuideImg() [参数] targetDeleteImg:" + targetDeleteImg + " ,classId:"
            + classId );
        String sql = "update contentclass set classPageGuideImg=? where classId=?";

        pe.update( sql, new Object[] { targetDeleteImg, classId } );
    }

    public List queryClassBeanBySomeIds( List idList, String orderFlag )
    {
        if( ( idList == null ) || ( idList.isEmpty() ) )
        {
            return new ArrayList();
        }
        Long targetId = null;

        StringBuffer buf = new StringBuffer( "(" );
        for ( int i = 0; i < idList.size(); i++ )
        {
            if( ( idList.get( i ) instanceof String ) )
            {
                targetId = Long
                    .valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1L ) );
            }
            else
            {
                targetId = ( Long ) idList.get( i );
            }
            buf.append( "classId=" ).append( targetId );
            if( i + 1 != idList.size() )
            {
                buf.append( " or " );
            }
        }
        buf.append( ")" );

        String order = "";
        if( "down".equals( orderFlag ) )
        {
            order = "desc";
        }
        String endSql = "select * from contentclass where " + buf + " order by linearOrderFlag "
            + order;

        Cache cache = ( Cache ) cacheManager.get( "getClassBySomeIdsSQL" );

        String key = "ByIds:" + buf + ":" + order;

        List result = ( List ) cache.getEntry( key );
        if( result == null )
        {
            result = pe.query( endSql, new ContentClassBeanTransform() );
            cache.putEntry( key, result );
        }
        return result;
    }

    public List queryClassBeanInfoByContentPublishState( String siteFlag,
        Integer contentPublishState )
    {
        String sql = "select * from contentclass where siteFlag=? and contentProduceType=?";

        return pe.query( sql, new Object[] { siteFlag, contentPublishState },
            new ContentClassBeanTransform() );
    }

    public List queryClassBeanBySomeFlags( String[] flagArray, String orderFlag )
    {
        StringBuffer buf = new StringBuffer( "(" );
        for ( int i = 0; i < flagArray.length; i++ )
        {
            if( SystemSafeCharUtil.hasSQLDChars( flagArray[i] ) )
            {
                throw new FrameworkException( "[非法参数:] idList:" + buf );
            }
            buf.append( "'" + flagArray[i] + "'" );
            if( i + 1 != flagArray.length )
            {
                buf.append( "," );
            }
        }
        buf.append( ")" );

        String order = "";
        if( "down".equals( orderFlag ) )
        {
            order = " desc";
        }
        String endSql = "select * from contentclass where classFlag in " + buf
            + " order by linearOrderFlag " + order;

        Cache cache = ( Cache ) cacheManager.get( "getClassBySomeIdsSQL" );

        String key = "ByFlags:" + buf + ":" + order;

        List result = ( List ) cache.getEntry( key );
        if( result == null )
        {
            result = pe.query( endSql, new ContentClassBeanTransform() );

            cache.putEntry( key, result );
        }
        return result;
    }

    public ContentClassBean querySingleClassBeanInfoByclassName( String className )
    {
        String sql = "select * from contentclass where className=?";

        return ( ContentClassBean ) pe.querySingleRow( sql, new Object[] { className },
            new ContentClassBeanTransform() );
    }

    public void updateWorkflowInfoToDefaultStatus( Long flowId )
    {
        String sql = "update contentclass set workflowId=0 where workflowId=?";

        pe.update( sql, new Object[] { flowId } );
    }

    public void updateChannelClassEndStaticPageInfo( Long classId, Integer disposePageCurrentCount,
        String endPageUrl )
    {
        String sql = "update contentclass set endPagePos=?, endStaticPageUrl=? where classId=?";
        pe.update( sql, new Object[] { disposePageCurrentCount, endPageUrl, classId } );
    }

    public Map queryClassPublishPageAssistant()
    {
        String sql = "select * from publish_page_assistant";

        PublishPageAssistantAllMapInfoTransform tran = new PublishPageAssistantAllMapInfoTransform();

        pe.query( sql, tran );

        return tran.getInfoMap();
    }

    public void insertClassPublishPageAssistant( Long classId, Integer lastPagePos,
        String classTemplateUrl, String endPageUrl, Long ruleId )
    {
        String sql = "insert into publish_page_assistant (classId, classTemplateUrl, lastPn, queryKey, lastPageStaticUrl, ruleId ) values (?,?,?,?,?,?)";
        pe.update( sql, new Object[] { classId, classTemplateUrl, lastPagePos,
            classId + ":" + classTemplateUrl, endPageUrl, ruleId } );
    }

    public void deleteClassPublishPageAssistant( Long classId, String classTemplateUrl )
    {
        String sql = "delete from publish_page_assistant where classId=? and classTemplateUrl=?";
        pe.update( sql, new Object[] { classId, classTemplateUrl } );
    }

    public void deleteClassPublishPageAssistant( Long classId )
    {
        String sql = "delete from publish_page_assistant where classId=?";
        pe.update( sql, new Object[] { classId } );
    }

    public List queryContentClassBeanChannelPath( String channelPath )
    {
        String key = "queryContentClassBeanChannelPath:" + channelPath;

        Cache cache = ( Cache ) cacheManager.get( "queryContentClassBeanChannelPath" );

        List result = ( List ) cache.getEntry( key );
        if( result == null )
        {
            String sql = "select * from contentclass where classId in (" + channelPath
                + ") order by linearOrderFlag asc";

            result = pe.query( sql, new ContentClassBeanTransform() );

            cache.putEntry( key, result );
        }
        return result;
    }

    public List queryAllContentCommendTypeBean( String siteFlag )
    {
        String sql = "select * from content_commend_type where siteFlag=? and isSpec=0";

        return pe.query( sql, new Object[] { siteFlag }, new ContentCommendTypeBeanTransform() );
    }

    public List queryAllSpecInfoTypeBean( String siteFlag )
    {
        String sql = "select * from content_commend_type where siteFlag=? and isSpec=1";

        return pe.query( sql, new Object[] { siteFlag }, new ContentCommendTypeBeanTransform() );
    }

    public List queryContentCommendTypeBeanBySiteFlag( String siteFlag )
    {
        String sql = "select * from content_commend_type where siteFlag=?";

        return pe.query( sql, new Object[] { siteFlag }, new ContentCommendTypeBeanTransform() );
    }

    public List queryContentCommendTypeBean( String siteFlag, Long classId )
    {
        String sql = "select * from content_commend_type where siteFlag=? and classId=? and isSpec=0";

        return pe.query( sql, new Object[] { siteFlag, classId },
            new ContentCommendTypeBeanTransform() );
    }

    public List queryContentSpecInfoTypeBean( String siteFlag, Long classId )
    {
        String sql = "select * from content_commend_type where siteFlag=? and classId=? and isSpec=1";

        return pe.query( sql, new Object[] { siteFlag, classId },
            new ContentCommendTypeBeanTransform() );
    }

    public List queryContentCommendTypeBeanByClassIds( String siteFlag, List classIdArray,
        Long mainClassId )
    {
        StringBuffer buf = new StringBuffer( "(" );

        long classId = -1L;
        for ( int i = 0; i < classIdArray.size(); i++ )
        {
            classId = StringUtil.getLongValue( ( String ) classIdArray.get( i ), -1L );
            if( classId >= 0L )
            {
                if( i + 1 != classIdArray.size() )
                {
                    buf.append( classId + "," );
                }
                else
                {
                    buf.append( classId );
                }
            }
        }
        buf.append( ")" );

        String sql = "select * from content_commend_type where siteFlag=? and childClassMode=1 and (classId=-9999 or classId in "
            + buf.toString()
            + ")"
            + " union select * from content_commend_type where siteFlag=? and classId = ?";

        return pe.query( sql, new Object[] { siteFlag, siteFlag, mainClassId },
            new ContentCommendTypeBeanTransform() );
    }

    public List queryContentCommendTypeRelateClassIdByClassId( String siteFlag, List classIdArray,
        Long mainClassId )
    {
        StringBuffer buf = new StringBuffer( "(" );

        long classId = -1L;
        for ( int i = 0; i < classIdArray.size(); i++ )
        {
            classId = StringUtil.getLongValue( ( String ) classIdArray.get( i ), -1L );
            if( classId >= 0L )
            {
                if( i + 1 != classIdArray.size() )
                {
                    buf.append( classId + "," );
                }
                else
                {
                    buf.append( classId );
                }
            }
        }
        buf.append( ")" );

        String sql = "select classId from content_commend_type where siteFlag=? and childClassMode=1 and classId in "
            + buf.toString()
            + " union select classId from content_commend_type where siteFlag=? and classId = ?";

        return pe.query( sql, new Object[] { siteFlag, siteFlag, mainClassId },
            new ContentCommendTypeBeanTransform() );
    }

    public List queryAllContentTypeBean( Long siteId )
    {
        String sql = "select * from content_type where siteId=? order by typeId asc";

        return pe.query( sql, new Object[] { siteId }, new ContentTypeBeanTransform() );
    }

    public ContentTypeBean querySingleContentTypeBean( Long typeId )
    {
        String sql = "select * from content_type where typeId=?";

        return ( ContentTypeBean ) pe.querySingleBean( sql, new Object[] { typeId },
            ContentTypeBean.class );
    }

    public ContentTypeBean querySingleContentTypeBean( String typeFlag )
    {
        String sql = "select * from content_type where typeFlag=?";

        return ( ContentTypeBean ) pe.querySingleBean( sql, new Object[] { typeFlag },
            ContentTypeBean.class );
    }

    public Long saveCommendTypeInfo( ContentCommendType commendType )
    {
        UpdateState us = pe.save( commendType );
        if( us.haveKey() )
        {
            return Long.valueOf( us.getKey() );
        }
        return Long.valueOf( -1L );
    }

    public void updateCommendTypeInfo( ContentCommendType commendType )
    {
        String sql = "update content_commend_type set classId=?, commendName=?, commFlag=?, modelId=?, typeDesc=?, childClassMode=?, mustCensor=?, imageWidth=?, imageHeight=?, listTemplateUrl=?, mobListTemplateUrl=?, padListTemplateUrl=?, listProduceType=?, listPublishRuleId=?, listStaticUrl=? where commendTypeId=?";
        pe.update( sql, commendType );
    }

    public ContentCommendTypeBean querySingleContentCommendTypeBeanByTypeId( Long typeId )
    {
        String sql = "select * from content_commend_type where commendTypeId=?";

        return ( ContentCommendTypeBean ) pe.querySingleRow( sql, new Object[] { typeId },
            new ContentCommendTypeBeanTransform() );
    }

    public ContentCommendTypeBean querySingleContentCommendTypeBeanByCommFlag( String commFlag )
    {
        String sql = "select * from content_commend_type where commFlag=?";

        return ( ContentCommendTypeBean ) pe.querySingleRow( sql, new Object[] { commFlag },
            new ContentCommendTypeBeanTransform() );
    }

    public void deleteCommendType( Long typeId )
    {
        String sql = "delete from content_commend_type where commendTypeId=?";

        pe.update( sql, new Object[] { typeId } );
    }

    public Integer queryChildCountByClassIdAndClassType( Long parent, Integer classType )
    {
        String sql = "select count(*) from contentclass where parent=? and classType=?";

        return ( Integer ) pe.querySingleObject( sql, new Object[] { parent, classType },
            Integer.class );
    }

    public void updateChannelTemplate( Long classId, String classHomeTemplateUrl )
    {
        String sql = "update contentclass set classHomeTemplateUrl=? where classId=?";
        pe.update( sql, new Object[] { classHomeTemplateUrl, classId } );
    }

    public void updateClassTemplateUrl( Long classId, String classTemplateUrl )
    {
        String sql = "update contentclass set classTemplateUrl=? where classId=?";
        pe.update( sql, new Object[] { classTemplateUrl, classId } );
    }

    public void updateContentTemplateUrl( Long classId, String contentTemplateUrl )
    {
        String sql = "update contentclass set contentTemplateUrl=? where classId=?";
        pe.update( sql, new Object[] { contentTemplateUrl, classId } );
    }

    public void updatePublishChannelState( Long classId, Integer state )
    {
        String sql = "update contentclass set classHomeProduceType=? where classId=?";
        pe.update( sql, new Object[] { state, classId } );
    }

    public void updatePublishClassState( Long classId, Integer state )
    {
        String sql = "update contentclass set classProduceType=? where classId=?";
        pe.update( sql, new Object[] { state, classId } );
    }

    public void updatePublishContentState( Long classId, Integer state )
    {
        String sql = "update contentclass set contentProduceType=? where classId=?";
        pe.update( sql, new Object[] { state, classId } );
    }

    public void updateStaticChannelRule( Long classId, Long ruleId )
    {
        String sql = "update contentclass set classHomePublishRuleId=? where classId=?";
        pe.update( sql, new Object[] { ruleId, classId } );
    }

    public void updateStaticClassRule( Long classId, Long ruleId )
    {
        String sql = "update contentclass set classPublishRuleId=? where classId=?";
        pe.update( sql, new Object[] { ruleId, classId } );
    }

    public void updateStaticContentRule( Long classId, Long ruleId )
    {
        String sql = "update contentclass set contentPublishRuleId=? where classId=?";
        pe.update( sql, new Object[] { ruleId, classId } );
    }

    public void updateEditorGuideImageRule( Long classId, Integer w, Integer h, Integer dm )
    {
        String sql = "update contentclass set editorImageW=?, editorImageH=?, editorImageDM=? where classId=?";
        pe.update( sql, new Object[] { w, h, dm, classId } );
    }

    public void updateEditorImageMark( Long classId, Integer editorImageMark )
    {
        String sql = "update contentclass set editorImageMark=? where classId=?";
        pe.update( sql, new Object[] { editorImageMark, classId } );
    }

    public void updateHomeGuideImageRule( Long classId, Integer w, Integer h, Integer dm )
    {
        String sql = "update contentclass set homeImageW=?, homeImageH=?, homeImageDM=? where classId=?";
        pe.update( sql, new Object[] { w, h, dm, classId } );
    }

    public void updateChannelGuideImageRule( Long classId, Integer w, Integer h, Integer dm )
    {
        String sql = "update contentclass set classImageW=?, classImageH=?, classImageDM=? where classId=?";
        pe.update( sql, new Object[] { w, h, dm, classId } );
    }

    public void updateListGuideImageRule( Long classId, Integer w, Integer h, Integer dm )
    {
        String sql = "update contentclass set listImageW=?, listImageH=?, listImageDM=? where classId=?";
        pe.update( sql, new Object[] { w, h, dm, classId } );
    }

    public void updateContentGuideImageRule( Long classId, Integer w, Integer h, Integer dm )
    {
        String sql = "update contentclass set contentImageW=?, contentImageH=?, contentImageDM=? where classId=?";
        pe.update( sql, new Object[] { w, h, dm, classId } );
    }

    public void updateContentShowStatus( Long classId, Integer showStatus )
    {
        String sql = "update contentclass set showStatus=? where classId=?";
        pe.update( sql, new Object[] { showStatus, classId } );
    }

    public void updateContentListPageLimit( Long classId, String listPageLimit )
    {
        String sql = "update contentclass set listPageLimit=? where classId=?";
        pe.update( sql, new Object[] { listPageLimit, classId } );
    }

    public void updateContentSyncPubClass( Long classId, Integer syncPubClass )
    {
        String sql = "update contentclass set syncPubClass=? where classId=?";
        pe.update( sql, new Object[] { syncPubClass, classId } );
    }

    public void updateContentSearchStatus( Long classId, Integer searchStatus )
    {
        String sql = "update contentclass set searchStatus=? where classId=?";
        pe.update( sql, new Object[] { searchStatus, classId } );
    }

    public List querySiteNotUseSearchFunClassId( String siteFlag )
    {
        String sql = "select classId from contentclass where siteFlag=? and searchStatus=0";
        return pe.querySingleCloumn( sql, new Object[] { siteFlag }, Long.class );
    }

    public List querySiteNotUseRelateFunClassId( String siteFlag )
    {
        String sql = "select classId from contentclass where siteFlag=? and relateRangeType=0";
        return pe.querySingleCloumn( sql, new Object[] { siteFlag }, Long.class );
    }

    public void updateContentRelateRangeType( Long classId, Integer relateRangeType )
    {
        String sql = "update contentclass set relateRangeType=? where classId=?";
        pe.update( sql, new Object[] { relateRangeType, classId } );
    }

    public void updateMemberAddContent( Long classId, Integer memberAddContent )
    {
        String sql = "update contentclass set memberAddContent=? where classId=?";
        pe.update( sql, new Object[] { memberAddContent, classId } );
    }

    public List querySpecContentTypeBuClassId( Long classId )
    {
        String sql = "select commendTypeId from content_commend_type where classId=?";
        return pe.querySingleCloumn( sql, new Object[] { classId }, Long.class );
    }

    public void updateSpecSubjectRecommendStatus( Long classId, Integer status )
    {
        String sql = "update contentclass set isRecommend=? where classId=?";
        pe.update( sql, new Object[] { status, classId } );
    }

    public List queryConetentClassByClassNameSearchKey( String keyVal, Long parentId )
    {
        String sql = "select * from contentclass where parent=? and className like '%" + keyVal
            + "%' order by linearOrderFlag asc";

        return pe.query( sql, new Object[] { parentId }, new ContentClassBeanTransform() );
    }

    public List queryRootClassBeanBySite( String siteFlag )
    {
        String sql = "select classId from contentclass where siteFlag=? and parent=-9999 and isSpecial=0 order by linearOrderFlag asc";

        return pe.querySingleCloumn( sql, new Object[] { siteFlag }, Long.class );
    }

    public void saveTagType( String typeName, String typeFlag, Long siteId )
    {
        String sql = "insert into tag_type (tagTypeName, flag, siteId) values (?,?,?)";

        pe.update( sql, new Object[] { typeName, typeFlag, siteId } );
    }

    public void updateTagType( Long typeId, String typeName, String typeFlag )
    {
        String sql = "update tag_type set tagTypeName=? where tagTypeId=?";

        pe.update( sql, new Object[] { typeName, typeId } );
    }

    public void deleteTagTypeInfoByIdAndSiteId( Long typeId, Long siteId )
    {
        String sql = "delete from tag_type where tagTypeId=? and siteId=?";

        pe.update( sql, new Object[] { typeId, siteId } );
    }

    public void deleteTagTypeInfoBySiteId( Long siteId )
    {
        String sql = "delete from tag_type where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public List queryTagTypeList( Long siteId )
    {
        String sql = "select * from tag_type where siteId=?";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public Map querySingleTagType( Long typeId )
    {
        String sql = "select * from tag_type where tagTypeId=?";

        return pe.querySingleResultMap( sql, new Object[] { typeId } );
    }

    public Map querySingleTagType( String flag )
    {
        String sql = "select * from tag_type where flag=?";

        return pe.querySingleResultMap( sql, new Object[] { flag } );
    }

    public void saveTagWord( TagWord tw )
    {
        pe.save( tw );
    }

    public UpdateState deleteTagWordInfoByIdAndSiteId( Long tagId, Long siteId )
    {
        String sql = "delete from tag_word where tagId=? and siteId=?";

        return pe.update( sql, new Object[] { tagId, siteId } );
    }

    public UpdateState deleteTagWordInfoBySiteId( Long siteId )
    {
        String sql = "delete from tag_word where siteId=?";

        return pe.update( sql, new Object[] { siteId } );
    }

    public void deleteTagRelateContentByTagId( Long tagId )
    {
        String sql = "delete from tag_relate_content where tagId=?";

        pe.update( sql, new Object[] { tagId } );
    }

    public void deleteTagRelateContentBySiteId( Long siteId )
    {
        String sql = "delete from tag_relate_content where tagId in (select tagId from tag_word where siteId=?)";

        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteTagRelateContentByContentId( Long contentId )
    {
        String sql = "delete from tag_relate_content where contentId=?";

        pe.update( sql, new Object[] { contentId } );
    }

    public Long queryTagWordRelateContentCount( Long tagId, Long contentId )
    {
        String sql = "select count(*) from tag_relate_content where tagId=? and contentId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { tagId, contentId }, Long.class );
    }

    public void saveTagWordRelateContent( Long tagId, Long contentId )
    {
        String sql = "insert into tag_relate_content (tagId, contentId) values (?,?)";

        pe.update( sql, new Object[] { tagId, contentId } );
    }

    public void updateTagWordRelateContentCount( Long tagId )
    {
        String sql = "update tag_word set contentCount=(select count(contentId) from tag_relate_content where tagId=?) where tagId=?";

        pe.update( sql, new Object[] { tagId, tagId } );
    }

    public void updateTagWord( TagWord tw )
    {
        String sql = "update tag_word set tagName=?, tagTypeId=?, firstChar=?, clickCount=? where tagId=?";
        pe.update( sql, tw );
    }

    public TagWordBean querySingleTagBeanByTagId( Long tagId )
    {
        String sql = "select * from tag_word where tagId=?";

        Cache cache = ( Cache ) cacheManager.get( "querySingleTagBean" );

        String key = "querySingleTagBeanByTagId:" + tagId;

        TagWordBean result = ( TagWordBean ) cache.getEntry( key );
        if( result == null )
        {
            result = ( TagWordBean ) pe.querySingleRow( sql, new Object[] { tagId },
                new TagWordBeanTransform() );

            cache.putEntry( key, result );
        }
        return result;
    }

    public TagWordBean querySingleTagBeanByTagName( String name )
    {
        String sql = "select * from tag_word where tagName=?";

        Cache cache = ( Cache ) cacheManager.get( "querySingleTagBean" );

        String key = "querySingleTagBeanByTagName:" + name;

        TagWordBean result = ( TagWordBean ) cache.getEntry( key );
        if( result == null )
        {
            result = ( TagWordBean ) pe.querySingleRow( sql, new Object[] { name },
                new TagWordBeanTransform() );

            cache.putEntry( key, result );
        }
        return result;
    }

    public Long queryTagWordCountBySiteId( Long siteId, String fc )
    {
        String sql = "select count(*) from tag_word where siteId=? and firstChar=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, fc } );
    }

    public Long queryTagWordCountBySiteId( Long siteId )
    {
        String sql = "select count(*) from tag_word where siteId=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId } );
    }

    public List queryTagWordBeanBySiteId( Long siteId, String fc, Long start, Integer size )
    {
        String sql = "select * from tag_word where siteId=? and firstChar=? order by tagId desc limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { siteId, fc, start, size } );
    }

    public List queryTagWordBeanBySiteId( Long siteId, String fc, Long start, Integer size,
        String of, String op )
    {
        String sql = "select * from tag_word where siteId=? and firstChar=? order by " + of + " "
            + op + " limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { siteId, fc, start, size } );
    }

    public List queryTagWordBeanBySiteId( Long siteId, Long start, Integer size )
    {
        String sql = "select * from tag_word where siteId=? order by tagId desc limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { siteId, start, size } );
    }

    public List queryTagWordBeanBySiteId( Long siteId, Long start, Integer size, String of,
        String op )
    {
        String sql = "select * from tag_word where siteId=? order by " + of + " " + op
            + " limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { siteId, start, size } );
    }

    public Long queryTagWordCountBySiteId( Long siteId, String fc, Long typeId )
    {
        String sql = "select count(*) from tag_word where siteId=? and firstChar=? and tagTypeId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, fc, typeId } );
    }

    public List queryTagWordBeanBySiteId( Long siteId, String fc, Long typeId, Long start,
        Integer size )
    {
        String sql = "select * from tag_word where siteId=? and firstChar=? and tagTypeId=? order by tagId desc limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { siteId, fc, typeId, start, size } );
    }

    public List queryTagWordBeanBySiteId( Long siteId, String fc, Long typeId, Long start,
        Integer size, String of, String op )
    {
        String sql = "select * from tag_word where siteId=? and firstChar=? and tagTypeId=? order by "
            + of + " " + op + " limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { siteId, fc, typeId, start, size } );
    }

    public Long queryTagWordCountBySiteId( Long siteId, Long typeId )
    {
        String sql = "select count(*) from tag_word where siteId=? and tagTypeId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, typeId } );
    }

    public List queryTagWordBeanBySiteId( Long siteId, Long typeId, Long start, Integer size )
    {
        String sql = "select * from tag_word where siteId=? and tagTypeId=? order by tagId desc limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { siteId, typeId, start, size } );
    }

    public List queryTagWordBeanBySiteId( Long siteId, Long typeId, Long start, Integer size,
        String of, String op )
    {
        String sql = "select * from tag_word where siteId=? and tagTypeId=? order by " + of + " "
            + op + " limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { siteId, typeId, start, size } );
    }

    public TagWordBean querySingleTagWordBeanByTagIdNoCache( Long tagId, Long siteId )
    {
        String sql = "select * from tag_word where tagId=? and siteId=?";

        return ( TagWordBean ) pe.querySingleRow( sql, new Object[] { tagId, siteId },
            new TagWordBeanTransform() );
    }

    public List queryEditorModuleInfoListBySiteId( Long siteId )
    {
        String sql = "select * from tool_editor_module_code where siteId=?";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public Map querySingleEditorModuleInfoByType( Integer type, Long siteId )
    {
        String sql = "select * from tool_editor_module_code where emType=? and siteId=?";

        return pe.querySingleResultMap( sql, new Object[] { type, siteId } );
    }

    public void updateSiteEditorModule( Long emId, String code, String emDesc )
    {
        String sql = "update tool_editor_module_code set code=?, emDesc=? where emId=?";

        pe.update( sql, new Object[] { code, emDesc, emId } );
    }

    public void updateSiteEditorModuleByTypeAndSite( Long siteId, Integer type, String code,
        String emDesc )
    {
        String sql = "update tool_editor_module_code set code=?, emDesc=? where siteId=? and emType=?";

        pe.update( sql, new Object[] { code, emDesc, siteId, type } );
    }

    public void deleteSiteEditorModuleBySite( Long siteId )
    {
        String sql = "delete from tool_editor_module_code where siteId=? ";

        pe.update( sql, new Object[] { siteId } );
    }

    public void saveEditorInfo( Long siteId, String name, Integer type, String code, String desc )
    {
        String sql = "insert into tool_editor_module_code (code, emName, emDesc, emType, useState, siteId) values (?,?,?,?,?,?)";

        pe.update( sql, new Object[] { code, name, desc, type, Constant.COMMON.ON, siteId } );
    }

    public List queryImageratioBySiteId( Long siteId )
    {
        String sql = "select * from tool_image_ratio where siteId=?";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public List querySingleImageratioBySiteId( Long irId )
    {
        String sql = "select * from tool_image_ratio where irId=?";

        return pe.queryResultMap( sql, new Object[] { irId } );
    }

    public void saveImageratio( String ratioName, Integer w, Integer h, Long siteId )
    {
        String sql = "insert into tool_image_ratio (ratioName, ratioWidth, ratioHeight, siteId) values (?,?,?,?)";

        pe.update( sql, new Object[] { ratioName, w, h, siteId } );
    }

    public void updateImageratio( Long irId, String ratioName, Integer w, Integer h )
    {
        String sql = "update tool_image_ratio set ratioName=?, ratioWidth=?, ratioHeight=? where irId=?";

        pe.update( sql, new Object[] { ratioName, w, h, irId } );
    }

    public void deleteImageratio( Long irId )
    {
        String sql = "delete from tool_image_ratio where irId=?";

        pe.update( sql, new Object[] { irId } );
    }

    public void deleteImageratioBySiteId( Long siteId )
    {
        String sql = "delete from tool_image_ratio where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void saveContentType( ContentType ct )
    {
        pe.save( ct );
    }

    public void updateContentType( ContentType ct )
    {
        String sql = "update content_type set typeName=? where typeId=?";

        pe.update( sql, ct );
    }

    public void deleteContentType( Long typeId )
    {
        String sql = "delete from content_type where typeId=?";

        pe.update( sql, new Object[] { typeId } );
    }

    public void updateClassContentModelId( Long classId, Long modelId )
    {
        String sql = "update contentclass set contentType=? where classId=?";

        pe.update( sql, new Object[] { modelId, classId } );
    }

    public void updateClassWorkflowId( Long classId, Long wkId )
    {
        String sql = "update contentclass set workflowId=? where classId=?";

        pe.update( sql, new Object[] { wkId, classId } );
    }

    public void updateClassDefModelId( Long classId, Long modelId )
    {
        String sql = "update contentclass set extDataModelId=? where classId=?";

        pe.update( sql, new Object[] { modelId, classId } );
    }

    public List queryClassListByLink( Long linkClassId, Long modelId )
    {
        String sql = "select classId from contentclass where linkFromClass=? and contentType=?";

        List result = pe.querySingleCloumn( sql, new Object[] { linkClassId, modelId },
            String.class );

        return result;
    }

    public static void clearAllCache()
    {
        log.info( "[释放缓存:] ChannelDao" );

        ( ( Cache ) cacheManager.get( "getClassByParentIdSQL" ) ).clearAllEntry();
        ( ( Cache ) cacheManager.get( "getClassByParentIdAndShowStatusSQL" ) ).clearAllEntry();
        ( ( Cache ) cacheManager.get( "getClassBySomeIdsSQL" ) ).clearAllEntry();

        ( ( Cache ) cacheManager.get( "allNodeObject" ) ).clearAllEntry();

        ( ( Cache ) cacheManager.get( "queryContentClassIdByPreLinear" ) ).clearAllEntry();

        ( ( Cache ) cacheManager.get( "queryContentClassBeanChannelPath" ) ).clearAllEntry();

    }

    public static void clearTagCache()
    {
        ( ( Cache ) cacheManager.get( "querySingleTagBean" ) ).clearAllEntry();

    }

}
