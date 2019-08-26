package cn.com.mjsoft.cms.content.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.bean.ContentCommendPushInfoBean;
import cn.com.mjsoft.cms.content.bean.ContentMainInfoBean;
import cn.com.mjsoft.cms.content.dao.vo.ContentAssistantPageInfo;
import cn.com.mjsoft.cms.content.dao.vo.ContentCommendPushInfo;
import cn.com.mjsoft.cms.content.dao.vo.ContentMainInfo;
import cn.com.mjsoft.cms.content.dao.vo.ContentStatus;
import cn.com.mjsoft.cms.content.dao.vo.PhotoGroupInfo;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.workflow.bean.WorkflowOperationBean;
import cn.com.mjsoft.cms.workflow.dao.WorkflowOperationAndExpandInfoBeanTransform;
import cn.com.mjsoft.cms.workflow.dao.WorkflowOperationBeanTransform;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.util.StringUtil;

public class ContentDao
{
    private Logger log = Logger.getLogger( getClass() );
    public static Cache countCache = new ClusterCacheAdapter( 5000, "contentDao.countCache" );
    public static Cache addTimeIdCache = new ClusterCacheAdapter( 500, "contentDao.addTimeIdCache" );

    // filter条件筛选中,数据变化较快的评论,点击类count单独缓存,以便不影响其他缓存
    public static Cache orderFilerCountCache = new ClusterCacheAdapter( 2500,
        "contentDao.orderFilerCountCache" );
    public PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public ContentDao()
    {
    }

    public ContentDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public void updateContentGuideImg( String targetImgFileName, Long contentId )
    {
        log.info( "[DAO] updateContentGuideImg() [参数] targetImgFileName:" + targetImgFileName
            + " ,contentId:" + contentId );
        String sql = "update content_main_info set homeImage=? where contentId=?";

        pe.update( sql, new Object[] { targetImgFileName, contentId } );
    }

    public void saveOrUpdateModelContent( String insertOrUpdateSql, Map paramMap )
    {
        pe.update( insertOrUpdateSql, paramMap );
    }

    public void saveOrUpdateModelContent( String insertOrUpdateSql, Object[] params )
    {
        try
        {
            pe.update( insertOrUpdateSql, params );
        }
        catch ( Exception e )
        {
            log.error( e );
        }
    }

    public UpdateState saveContentMainInfo( ContentMainInfo info )
    {
        return pe.save( info );
    }

    public void updateCotentTrashStatusNotUse( Long contentId, Integer status )
    {
        String sql = "update content_main_info set isTrash=? where contentId=?";

        pe.update( sql, new Object[] { status, contentId } );
    }

    public void transferCotentToTrash( Long contentId )
    {
        String sql = "insert into content_trash_main_info (contentId, modelId, classId, refCid, linkCid, title, simpleTitle, shortTitle, titleStyle, simpleTitleStyle, boost, author, creator, orgCode, summary, addTime, clickMonthCount, clickWeekCount, clickDayCount, clickCount, commMonthCount, commWeekCount, commDayCount, commCount, supportCount, againstCount, homeImage, classImage, channelImage, contentImage, systemHandleTime, especialTemplateUrl, staticPageUrl, produceType, censorState,  isPageContent, isSystemOrder, orderIdFlag, keywords, tagKey, pubDateSysDT, appearStartDateTime, appearEndDateTime, homeImgFlag, classImgFlag, channelImgFlag, contentImgFlag, commendFlag,  typeFlag, topFlag, siteId, allowCommend ,deleteTime)select contentId, modelId, classId, refCid, linkCid,   title, simpleTitle, shortTitle, titleStyle, simpleTitleStyle, boost, author, creator, orgCode, summary, addTime, clickMonthCount, clickWeekCount, clickDayCount, clickCount, commMonthCount, commWeekCount, commDayCount, commCount, supportCount, againstCount, homeImage, classImage, channelImage, contentImage, systemHandleTime, especialTemplateUrl, staticPageUrl, produceType, censorState,  isPageContent, isSystemOrder, orderIdFlag, keywords, tagKey, pubDateSysDT, appearStartDateTime, appearEndDateTime, homeImgFlag, classImgFlag, channelImgFlag, contentImgFlag, commendFlag, typeFlag, topFlag, siteId, allowCommend, now() as delateTime from content_main_info where contentId=?";

        pe.update( sql, new Object[] { contentId } );
    }

    public void transferTrashToCotent( Long contentId )
    {
        String sql = "insert into content_main_info (contentId, modelId, classId, refCid, linkCid, title, simpleTitle, shortTitle, titleStyle, simpleTitleStyle, boost, author, creator, orgCode, summary, addTime, clickMonthCount, clickWeekCount, clickDayCount, clickCount, commMonthCount, commWeekCount, commDayCount, commCount, supportCount, againstCount, homeImage, classImage, channelImage, contentImage, systemHandleTime, especialTemplateUrl, staticPageUrl, produceType, censorState,  isPageContent, isSystemOrder, orderIdFlag, keywords, tagKey, pubDateSysDT, appearStartDateTime, appearEndDateTime, homeImgFlag, classImgFlag, channelImgFlag, contentImgFlag, commendFlag,  typeFlag, topFlag, siteId, allowCommend )select contentId, modelId, classId, refCid, linkCid, title, simpleTitle, shortTitle, titleStyle, simpleTitleStyle,  boost,  author, creator, orgCode,summary, addTime, clickMonthCount, clickWeekCount, clickDayCount, clickCount, commMonthCount, commWeekCount, commDayCount, commCount, supportCount, againstCount, homeImage, classImage, channelImage, contentImage, systemHandleTime, especialTemplateUrl, staticPageUrl, produceType, censorState,  isPageContent, isSystemOrder, orderIdFlag, keywords, tagKey, pubDateSysDT, appearStartDateTime, appearEndDateTime, homeImgFlag, classImgFlag, channelImgFlag, contentImgFlag, commendFlag, typeFlag, topFlag, siteId, allowCommend from content_trash_main_info where contentId=?";

        pe.update( sql, new Object[] { contentId } );
    }

    public List queryTrashContentIdByClassIdAndModelId( Long classId, Long modelId, Long prevCId,
        Integer count )
    {
        String sql = "select contentId from content_trash_main_info where classId=? and modelId=? and contentId<? order by contentId desc limit ?";

        return pe.querySingleCloumn( sql, new Object[] { classId, modelId, prevCId, count } );
    }

    public List queryMainContentIdByClassIdAndModelId( Long classId, Long modelId, Long prevCId,
        Integer count )
    {
        String sql = "select contentId from content_main_info where classId=? and modelId=? and contentId<? order by contentId desc limit ?";

        return pe.querySingleCloumn( sql, new Object[] { classId, modelId, prevCId, count } );
    }

    public List queryMainContentByClassIdAndModelId( Long classId, Long modelId, Long prevCId,
        Integer count )
    {
        String sql = "select * from content_main_info where classId=? and modelId=? and contentId<? order by contentId desc limit ?";

        return pe.queryResultMap( sql, new Object[] { classId, modelId, prevCId, count } );
    }

    public UpdateState updateContentMainInfo( ContentMainInfo info )
    {
        String sql = "update content_main_info set classId=?, refCid=?, boost=?, addTime=?, title=?, simpleTitle=?, shortTitle=?, titleStyle=?, simpleTitleStyle=?, author=?, creator=?, summary=?, outLink=?, homeImage=?, classImage=?, channelImage=?, contentImage=?, systemHandleTime=?, pubDateSysDT=?, especialTemplateUrl=?, keywords=?, tagKey=?, appearStartDateTime=?, appearEndDateTime=?, homeImgFlag=?, classImgFlag=?, channelImgFlag=?, contentImgFlag=?, relateSurvey=?, commendFlag=?, typeFlag=?, topFlag=?, allowCommend=?, censorState=? where contentId=?";

        return pe.update( sql, info );
    }

    public UpdateState updateSystemContentOrderIdFlag( Double orderIdFlag, Long contentId )
    {
        String sql = "update content_main_info set orderIdFlag=? where contentId=?";
        return pe.update( sql, new Object[] { orderIdFlag, contentId } );
    }

    public UpdateState updateSystemPublishIdFlag( Long pubId, Long contentId )
    {
        String sql = "update content_main_info set pubDateSysDT=? where contentId=?";
        return pe.update( sql, new Object[] { pubId, contentId } );
    }

    public void updateSystemPublishIdFlagAndCensorStatusAndPubDate( Long pubId, Integer censor,
        Timestamp pubDT, Long contentId )
    {
        String sql = "update content_main_info set pubDateSysDT=?, censorState=?, appearStartDateTime=? where contentId=?";
        pe.update( sql, new Object[] { pubId, censor, pubDT, contentId } );
    }

    public Integer queryUserDefinedContentExist( DataModelBean model, Long id )
    {
        if( model == null )
        {
            return Integer.valueOf( -1 );
        }
        String sql = "select count(contentId) from " + model.getRelateTableName()
            + " where contentId=?";
        return ( Integer ) pe.querySingleObject( sql, new Object[] { id }, Integer.class );
    }

    public Integer queryUserDefineContentAllCount( Long targetClassId, DataModelBean modelBean )
    {
        String sql = "select count(*) from content_main_info where classId=? and modelId=?";

        String key = "queryUserDefineContentAllCount:" + targetClassId + modelBean.getDataModelId();

        Integer count = ( Integer ) countCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { targetClassId,
                modelBean.getDataModelId() }, Integer.class );
            countCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCountIds( String targetClassIds )
    {
        if( StringUtil.isStringNull( targetClassIds ) )
        {
            return Integer.valueOf( 0 );
        }
        String sql = "select count(*) from content_main_info where classId in (" + targetClassIds
            + ")";

        String key = "queryUserDefineContentAllCount:" + targetClassIds;

        Integer count = ( Integer ) countCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, Integer.class );
            countCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCount( Long targetClassId, DataModelBean modelBean,
        String orderFilter )
    {
        String sql = "select count(*) from content_main_info where classId=? and modelId=? "
            + orderFilter;

        String key = "queryUserDefineContentAllCount:" + targetClassId + modelBean.getDataModelId()
            + orderFilter;

        Integer count = ( Integer ) orderFilerCountCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { targetClassId,
                modelBean.getDataModelId() }, Integer.class );
            orderFilerCountCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCountIds( String targetClassIds, String orderFilter )
    {
        if( StringUtil.isStringNull( targetClassIds ) )
        {
            return Integer.valueOf( 0 );
        }
        String sql = "select count(*) from content_main_info where classId in (" + targetClassIds
            + ") " + orderFilter;

        String key = "queryUserDefineContentAllCount:" + targetClassIds + orderFilter;

        Integer count = ( Integer ) orderFilerCountCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, Integer.class );
            orderFilerCountCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCount( String typeBy, Long targetClassId,
        DataModelBean modelBean )
    {
        String sql = "select count(*) from content_main_info where typeFlag=? and classId=? and modelId=?";

        String key = "queryUserDefineContentAllCount:" + targetClassId + typeBy
            + modelBean.getDataModelId();

        Integer count = ( Integer ) countCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { typeBy, targetClassId,
                modelBean.getDataModelId() }, Integer.class );
            countCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCountIdsTb( String typeBy, String targetClassIds )
    {
        if( StringUtil.isStringNull( targetClassIds ) )
        {
            return Integer.valueOf( 0 );
        }
        String sql = "select count(*) from content_main_info where typeFlag=? and classId in ("
            + targetClassIds + ") ";

        String key = "queryUserDefineContentAllCount:" + targetClassIds + typeBy;

        Integer count = ( Integer ) countCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { typeBy }, Integer.class );
            countCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCount( String typeBy, Long targetClassId,
        DataModelBean modelBean, String orderFilter )
    {
        String sql = "select count(*) from content_main_info where typeFlag=? and classId=? and modelId=? "
            + orderFilter;

        String key = "queryUserDefineContentAllCount:" + targetClassId + typeBy
            + modelBean.getDataModelId() + orderFilter;

        Integer count = ( Integer ) orderFilerCountCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { typeBy, targetClassId,
                modelBean.getDataModelId() }, Integer.class );
            orderFilerCountCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCountIdsTb( String typeBy, String targetClassIds,
        String orderFilter )
    {
        if( StringUtil.isStringNull( targetClassIds ) )
        {
            return Integer.valueOf( 0 );
        }
        String sql = "select count(*) from content_main_info where typeFlag=? and classId in ("
            + targetClassIds + ") " + orderFilter;

        String key = "queryUserDefineContentAllCount:" + targetClassIds + typeBy + orderFilter;

        Integer count = ( Integer ) orderFilerCountCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { typeBy }, Integer.class );
            orderFilerCountCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCount( Long targetClassId, DataModelBean modelBean,
        Integer censorBy )
    {
        String sql = "select count(*) from content_main_info where classId=? and modelId=? and censorState=?";

        String key = "queryUserDefineContentAllCount:" + targetClassId + modelBean.getDataModelId()
            + censorBy;

        Integer count = ( Integer ) countCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { targetClassId,
                modelBean.getDataModelId(), censorBy }, Integer.class );
            countCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCountIdsCen( String targetClassIds, Integer censorBy )
    {
        if( StringUtil.isStringNull( targetClassIds ) )
        {
            return Integer.valueOf( 0 );
        }
        String sql = "select count(*) from content_main_info where classId in (" + targetClassIds
            + ") and censorState=?";

        String key = "queryUserDefineContentAllCount:" + targetClassIds + censorBy;

        Integer count = ( Integer ) countCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe
                .querySingleObject( sql, new Object[] { censorBy }, Integer.class );
            countCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCount( Long targetClassId, DataModelBean modelBean,
        Integer censorBy, String orderFilter )
    {
        String sql = "select count(*) from content_main_info where classId=? and modelId=? and censorState=? "
            + orderFilter;

        String key = "queryUserDefineContentAllCount:" + targetClassId + modelBean.getDataModelId()
            + censorBy + orderFilter;

        Integer count = ( Integer ) orderFilerCountCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { targetClassId,
                modelBean.getDataModelId(), censorBy }, Integer.class );
            orderFilerCountCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCountIds( String targetClassIds, Integer censorBy,
        String orderFilter )
    {
        if( StringUtil.isStringNull( targetClassIds ) )
        {
            return Integer.valueOf( 0 );
        }
        String sql = "select count(*) from content_main_info where classId in (" + targetClassIds
            + ") and censorState=? " + orderFilter;

        String key = "queryUserDefineContentAllCount:" + targetClassIds + censorBy + orderFilter;

        Integer count = ( Integer ) orderFilerCountCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe
                .querySingleObject( sql, new Object[] { censorBy }, Integer.class );
            orderFilerCountCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCount( String typeBy, Long targetClassId,
        DataModelBean modelBean, Integer censorBy )
    {
        String sql = "select count(*) from content_main_info where typeFlag=? and classId=? and modelId=? and censorState=?";

        String key = "queryUserDefineContentAllCount:" + targetClassId + modelBean.getDataModelId()
            + typeBy + censorBy;

        Integer count = ( Integer ) countCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { typeBy, targetClassId,
                modelBean.getDataModelId(), censorBy }, Integer.class );
            countCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCountIdsTbCen( String typeBy, String targetClassIds,
        Integer censorBy )
    {
        if( StringUtil.isStringNull( targetClassIds ) )
        {
            return Integer.valueOf( 0 );
        }
        String sql = "select count(*) from content_main_info where typeFlag=? and classId in ("
            + targetClassIds + ") and censorState=?";

        String key = "queryUserDefineContentAllCount:" + targetClassIds + typeBy + censorBy;

        Integer count = ( Integer ) countCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { typeBy, censorBy },
                Integer.class );
            countCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCount( String typeBy, Long targetClassId,
        DataModelBean modelBean, Integer censorBy, String orderFilter )
    {
        String sql = "select count(*) from content_main_info where typeFlag=? and classId=? and modelId=? and censorState=? "
            + orderFilter;

        String key = "queryUserDefineContentAllCount:" + targetClassId + modelBean.getDataModelId()
            + typeBy + censorBy + orderFilter;

        Integer count = ( Integer ) orderFilerCountCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { typeBy, targetClassId,
                modelBean.getDataModelId(), censorBy }, Integer.class );
            orderFilerCountCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCountIdsTb( String typeBy, String targetClassIds,
        Integer censorBy, String orderFilter )
    {
        if( StringUtil.isStringNull( targetClassIds ) )
        {
            return Integer.valueOf( 0 );
        }
        String sql = "select count(*) from content_main_info where typeFlag=? and classId in ("
            + targetClassIds + ") and censorState=? " + orderFilter;

        String key = "queryUserDefineContentAllCount:" + targetClassIds + typeBy + censorBy
            + orderFilter;

        Integer count = ( Integer ) orderFilerCountCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { typeBy, censorBy },
                Integer.class );
            orderFilerCountCache.putEntry( key, count );
        }
        return count;
    }

    public Long queryContentMainInfoModelIdByCid( Long id )
    {
        String sql = "select modelId from content_main_info where contentId=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { id }, Long.class );
    }

    public Integer queryUserDefineContentAllCountDateMode( Long targetClassId,
        DataModelBean modelBean, Long startDateCid, Long endDateCid )
    {
        String sql = "select count(*) from content_main_info where classId=? and modelId=? and contentId>=? and contentId<=?";

        String key = "queryUserDefineContentAllCount:" + targetClassId + modelBean.getDataModelId()
            + startDateCid + endDateCid;

        Integer count = ( Integer ) countCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { targetClassId,
                modelBean.getDataModelId(), startDateCid, endDateCid }, Integer.class );
            countCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCountDateMode( Long targetClassId,
        DataModelBean modelBean, Long startDateCid, Long endDateCid, String orderFilter )
    {
        String sql = "select count(*) from content_main_info where classId=? and modelId=? and contentId>=? and contentId<=? "
            + orderFilter;

        String key = "queryUserDefineContentAllCount:" + targetClassId + modelBean.getDataModelId()
            + startDateCid + endDateCid + orderFilter;

        Integer count = ( Integer ) orderFilerCountCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { targetClassId,
                modelBean.getDataModelId(), startDateCid, endDateCid }, Integer.class );
            orderFilerCountCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCountDateMode( Long targetClassId,
        DataModelBean modelBean, String typeBy, Long startDateCid, Long endDateCid )
    {
        String sql = "select count(*) from content_main_info where typeFlag=? and classId=? and modelId=? and contentId>=? and contentId<=?";

        String key = "queryUserDefineContentAllCount:" + targetClassId + modelBean.getDataModelId()
            + typeBy + startDateCid + endDateCid;

        Integer count = ( Integer ) countCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { typeBy, targetClassId,
                modelBean.getDataModelId(), startDateCid, endDateCid }, Integer.class );
            countCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCountDateMode( Long targetClassId,
        DataModelBean modelBean, String typeBy, Long startDateCid, Long endDateCid,
        String orderFilter )
    {
        String sql = "select count(*) from content_main_info where typeFlag=? and classId=? and modelId=? and contentId>=? and contentId<=? "
            + orderFilter;

        String key = "queryUserDefineContentAllCount:" + targetClassId + modelBean.getDataModelId()
            + typeBy + startDateCid + endDateCid + orderFilter;

        Integer count = ( Integer ) orderFilerCountCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { typeBy, targetClassId,
                modelBean.getDataModelId(), startDateCid, endDateCid }, Integer.class );
            orderFilerCountCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCountDateMode( Long targetClassId,
        DataModelBean modelBean, Long startDateCid, Long endDateCid, Integer censorBy )
    {
        String sql = "select count(*) from content_main_info where classId=? and modelId=? and censorState=? and contentId>=? and contentId<=?";

        String key = "queryUserDefineContentAllCount:" + targetClassId + modelBean.getDataModelId()
            + censorBy + startDateCid + endDateCid;

        Integer count = ( Integer ) countCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { targetClassId,
                modelBean.getDataModelId(), censorBy, startDateCid, endDateCid }, Integer.class );
            countCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCountDateMode( Long targetClassId,
        DataModelBean modelBean, Long startDateCid, Long endDateCid, Integer censorBy,
        String orderFilter )
    {
        String sql = "select count(*) from content_main_info where classId=? and modelId=? and censorState=? and contentId>=? and contentId<=? "
            + orderFilter;

        String key = "queryUserDefineContentAllCount:" + targetClassId + modelBean.getDataModelId()
            + censorBy + startDateCid + endDateCid + orderFilter;

        Integer count = ( Integer ) orderFilerCountCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { targetClassId,
                modelBean.getDataModelId(), censorBy, startDateCid, endDateCid }, Integer.class );
            orderFilerCountCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCountDateMode( Long targetClassId,
        DataModelBean modelBean, String typeBy, Long startDateCid, Long endDateCid, Integer censorBy )
    {
        String sql = "select count(*) from content_main_info where typeFlag=? and classId=? and modelId=? and censorState=? and contentId>=? and contentId<=?";

        String key = "queryUserDefineContentAllCount:" + targetClassId + modelBean.getDataModelId()
            + typeBy + censorBy + startDateCid + endDateCid;

        Integer count = ( Integer ) countCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { typeBy, targetClassId,
                modelBean.getDataModelId(), censorBy, startDateCid, endDateCid }, Integer.class );
            countCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryUserDefineContentAllCountDateMode( Long targetClassId,
        DataModelBean modelBean, String typeBy, Long startDateCid, Long endDateCid,
        Integer censorBy, String orderFilter )
    {
        String sql = "select count(*) from content_main_info where typeFlag=? and classId=? and modelId=? and censorState=? and contentId>=? and contentId<=? "
            + orderFilter;

        String key = "queryUserDefineContentAllCount:" + targetClassId + modelBean.getDataModelId()
            + typeBy + censorBy + startDateCid + endDateCid + orderFilter;

        Integer count = ( Integer ) orderFilerCountCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { typeBy, targetClassId,
                modelBean.getDataModelId(), censorBy, startDateCid, endDateCid }, Integer.class );
            orderFilerCountCache.putEntry( key, count );
        }
        return count;
    }

    public List queryLimitContentByModelAndFlagId( DataModelBean modelBean, String orderBy,
        long topFlag, double orderIdFlag, int limitSize )
    {
        String sqlDesc = "select tmp.*, cm.*, cc.className from (select ci.contentId from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where contentId<? and topFlag>-1 and orderIdFlag>-1 and modelId=? order by topFlag desc, orderIdFlag desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by topFlag desc, orderIdFlag desc ";

        String sqlAsc = "select tmp.*, cm.*, cc.className from (select ci.contentId from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where contentId>? and topFlag>-1 and orderIdFlag>-1 and modelId=? order by topFlag asc, orderIdFlag asc  limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by topFlag desc, orderIdFlag desc ";

        String sql = sqlDesc;
        if( "asc".equals( orderBy ) )
        {
            sql = sqlAsc;
        }
        List result = pe.queryResultMap( sql, new Object[] { Long.valueOf( topFlag ),
            Double.valueOf( orderIdFlag ), Integer.valueOf( limitSize ) },
            new ContentValueResultCallBack() );

        return result;
    }

    public List queryLimitModeContentTopMode( boolean showAll, DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, long classId, Long startDateCid,
        Long endDateCid, long pagePos, int pageSize, String orderFilter, String orderBy,
        String orderWay )
    {
        String sql = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? "
            + orderFilter
            + " "
            + "order by topFlag desc, "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by topFlag desc, cm."
            + orderBy + " " + orderWay;

        String sqlDateMode = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and contentId>=? and contentId<=? "
            + orderFilter
            + " "
            + "order by topFlag desc, "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by topFlag desc, cm."
            + orderBy + " " + orderWay;
        if( ( startDateCid != null ) && ( endDateCid != null ) )
        {
            return pe.queryResultMap( sqlDateMode, new Object[] { Long.valueOf( classId ),
                modelBean.getDataModelId(), startDateCid, endDateCid, Long.valueOf( pagePos ),
                Integer.valueOf( pageSize ) }, new ContentValueResultCallBack() );
        }
        return pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ),
            modelBean.getDataModelId(), Long.valueOf( pagePos ), Integer.valueOf( pageSize ) },
            new ContentValueResultCallBack() );
    }

    public List queryLimitModeContent( boolean showAll, DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, long classId, Long startDateCid,
        Long endDateCid, long pagePos, int pageSize, String orderFilter, String orderBy,
        String orderWay )
    {
        String sql = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? "
            + orderFilter
            + " order by "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " " + orderWay;

        String sqlDateMode = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and contentId>=? and contentId<=? "
            + orderFilter
            + " order by "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " " + orderWay;
        if( ( startDateCid != null ) && ( endDateCid != null ) )
        {
            return pe.queryResultMap( sqlDateMode, new Object[] { Long.valueOf( classId ),
                modelBean.getDataModelId(), startDateCid, endDateCid, Long.valueOf( pagePos ),
                Integer.valueOf( pageSize ) }, new ContentValueResultCallBack() );
        }
        return pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ),
            modelBean.getDataModelId(), Long.valueOf( pagePos ), Integer.valueOf( pageSize ) },
            new ContentValueResultCallBack() );
    }

    public List queryLimitModeContentTopMode( boolean showAll, DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, long classId, int censorBy,
        Long startDateCid, Long endDateCid, long pagePos, int pageSize, String orderFilter,
        String orderBy, String orderWay )
    {
        String sql = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and censorState=? "
            + orderFilter
            + " order by topFlag desc, "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by topFlag desc, cm."
            + orderBy + " " + orderWay;

        String sqlDateMode = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and censorState=? and contentId>=? and contentId<=? "
            + orderFilter
            + " order by topFlag desc, "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by topFlag desc, cm."
            + orderBy + " " + orderWay;
        if( ( startDateCid != null ) && ( endDateCid != null ) )
        {
            return pe.queryResultMap( sqlDateMode, new Object[] { Long.valueOf( classId ),
                modelBean.getDataModelId(), Integer.valueOf( censorBy ), startDateCid, endDateCid,
                Long.valueOf( pagePos ), Integer.valueOf( pageSize ) },
                new ContentValueResultCallBack() );
        }
        return pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ),
            modelBean.getDataModelId(), Integer.valueOf( censorBy ), Long.valueOf( pagePos ),
            Integer.valueOf( pageSize ) }, new ContentValueResultCallBack() );
    }

    public List queryLimitModeContent( boolean showAll, DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, long classId, int censorBy,
        Long startDateCid, Long endDateCid, long pagePos, int pageSize, String orderFilter,
        String orderBy, String orderWay )
    {
        String sql = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and censorState=? "
            + orderFilter
            + " order by "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " " + orderWay;

        String sqlDateMode = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and censorState=? and contentId>=? and contentId<=? "
            + orderFilter
            + " order by "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " " + orderWay;
        if( ( startDateCid != null ) && ( endDateCid != null ) )
        {
            return pe.queryResultMap( sqlDateMode, new Object[] { Long.valueOf( classId ),
                modelBean.getDataModelId(), Integer.valueOf( censorBy ), startDateCid, endDateCid,
                Long.valueOf( pagePos ), Integer.valueOf( pageSize ) },
                new ContentValueResultCallBack() );
        }
        return pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ),
            modelBean.getDataModelId(), Integer.valueOf( censorBy ), Long.valueOf( pagePos ),
            Integer.valueOf( pageSize ) }, new ContentValueResultCallBack() );
    }

    public List queryLimitModeContentTopMode( boolean showAll, DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, long classId, String typeFlag,
        Long startDateCid, Long endDateCid, long pagePos, int pageSize, String orderFilter,
        String orderBy, String orderWay )
    {
        String sql = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and typeFlag=? "
            + orderFilter
            + " order by topFlag desc, "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by topFlag desc, cm."
            + orderBy + " " + orderWay;

        String sqlDateMode = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and typeFlag=? and contentId>=? and contentId<=? "
            + orderFilter
            + " order by topFlag desc, "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by topFlag desc, cm."
            + orderBy + " " + orderWay;
        if( ( startDateCid != null ) && ( endDateCid != null ) )
        {
            return pe.queryResultMap( sqlDateMode, new Object[] { Long.valueOf( classId ),
                modelBean.getDataModelId(), typeFlag, startDateCid, endDateCid,
                Long.valueOf( pagePos ), Integer.valueOf( pageSize ) },
                new ContentValueResultCallBack() );
        }
        return pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ),
            modelBean.getDataModelId(), typeFlag, Long.valueOf( pagePos ),
            Integer.valueOf( pageSize ) }, new ContentValueResultCallBack() );
    }

    public List queryLimitModeContent( boolean showAll, DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, long classId, String typeFlag,
        Long startDateCid, Long endDateCid, long pagePos, int pageSize, String orderFilter,
        String orderBy, String orderWay )
    {
        String sql = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and typeFlag=? "
            + orderFilter
            + " order by "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " " + orderWay;

        String sqlDateMode = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and typeFlag=? and contentId>=? and contentId<=? "
            + orderFilter
            + " order by "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " " + orderWay;
        if( ( startDateCid != null ) && ( endDateCid != null ) )
        {
            return pe.queryResultMap( sqlDateMode, new Object[] { Long.valueOf( classId ),
                modelBean.getDataModelId(), typeFlag, startDateCid, endDateCid,
                Long.valueOf( pagePos ), Integer.valueOf( pageSize ) },
                new ContentValueResultCallBack() );
        }
        return pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ),
            modelBean.getDataModelId(), typeFlag, Long.valueOf( pagePos ),
            Integer.valueOf( pageSize ) }, new ContentValueResultCallBack() );
    }

    public List queryLimitModeContentTopMode( boolean showAll, DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, long classId, int censorBy,
        String typeFlag, Long startDateCid, Long endDateCid, long pagePos, int pageSize,
        String orderFilter, String orderBy, String orderWay )
    {
        String sql = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and censorState=? and typeFlag=? "
            + orderFilter
            + " order by topFlag desc, "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by topFlag desc, cm."
            + orderBy + " " + orderWay;

        String sqlDateMode = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and censorState=? and typeFlag=? and contentId>=? and contentId<=? "
            + orderFilter
            + " order by topFlag desc, "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by topFlag desc, cm."
            + orderBy + " " + orderWay;
        if( ( startDateCid != null ) && ( endDateCid != null ) )
        {
            return pe.queryResultMap( sqlDateMode, new Object[] { Long.valueOf( classId ),
                modelBean.getDataModelId(), Integer.valueOf( censorBy ), typeFlag, startDateCid,
                endDateCid, Long.valueOf( pagePos ), Integer.valueOf( pageSize ) },
                new ContentValueResultCallBack() );
        }
        return pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ),
            modelBean.getDataModelId(), Integer.valueOf( censorBy ), typeFlag,
            Long.valueOf( pagePos ), Integer.valueOf( pageSize ) },
            new ContentValueResultCallBack() );
    }

    public List queryLimitModeContent( boolean showAll, DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, long classId, int censorBy,
        String typeFlag, Long startDateCid, Long endDateCid, long pagePos, int pageSize,
        String orderFilter, String orderBy, String orderWay )
    {
        String sql = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and censorState=? and typeFlag=? "
            + orderFilter
            + " order by "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " " + orderWay;

        String sqlDateMode = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and censorState=? and typeFlag=? and contentId>=? and contentId<=? "
            + orderFilter
            + " order by "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " " + orderWay;
        if( ( startDateCid != null ) && ( endDateCid != null ) )
        {
            return pe.queryResultMap( sqlDateMode, new Object[] { Long.valueOf( classId ),
                modelBean.getDataModelId(), Integer.valueOf( censorBy ), typeFlag, startDateCid,
                endDateCid, Long.valueOf( pagePos ), Integer.valueOf( pageSize ) },
                new ContentValueResultCallBack() );
        }
        return pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ),
            modelBean.getDataModelId(), Integer.valueOf( censorBy ), typeFlag,
            Long.valueOf( pagePos ), Integer.valueOf( pageSize ) },
            new ContentValueResultCallBack() );
    }

    public List queryConetntMainInfoByContentIds( List cids, String orderBy, String orderWay )
    {
        StringBuffer buf = new StringBuffer( "(" );

        Long id = null;

        boolean havaParam = false;
        for ( int i = 0; i < cids.size(); i++ )
        {
            id = ( Long ) cids.get( i );

            havaParam = true;

            buf.append( id );
            if( i + 1 != cids.size() )
            {
                buf.append( "," );
            }
        }
        buf.append( ")" );
        if( !havaParam )
        {
            return new ArrayList();
        }
        String sql = "select * from content_main_info where contentId in " + buf.toString()
            + " order by " + orderBy + " " + orderWay;

        return pe.queryResultMap( sql, new ContentValueResultCallBack() );
    }

    public List queryLimitModeContentIdByClassIdsOnlyMainInfo( String classIds, String typeBy,
        int censorBy, long startPos, int pageSize, String orderFilter, String orderBy,
        String orderWay )
    {
        if( StringUtil.isStringNull( classIds ) )
        {
            return new ArrayList();
        }
        String sql = null;
        if( StringUtil.isStringNotNull( typeBy ) )
        {
            sql =

            "select ci.* from (select contentId as queryId from content_main_info where  classId in ("
                + classIds + ") and censorState=? and typeFlag=? " + orderFilter + " order by "
                + orderBy + " " + orderWay
                + " limit ?,?) ids left join content_main_info ci on ids.queryId=ci.contentId"
                + orderFilter + " order by ci." + orderBy + " " + orderWay;

            return pe.queryResultMap( sql, new Object[] { Integer.valueOf( censorBy ), typeBy,
                Long.valueOf( startPos ), Integer.valueOf( pageSize ) },
                new ContentValueResultCallBack() );
        }
        sql =

        "select ci.* from (select contentId as queryId from content_main_info where  classId in ("
            + classIds + ") and censorState=? " + orderFilter + " order by " + orderBy + " "
            + orderWay
            + " limit ?,?) ids left join content_main_info ci on ids.queryId=ci.contentId"
            + orderFilter + " order by ci." + orderBy + " " + orderWay;

        return pe.queryResultMap( sql, new Object[] { Integer.valueOf( censorBy ),
            Long.valueOf( startPos ), Integer.valueOf( pageSize ) },
            new ContentValueResultCallBack() );
    }

    public List queryLimitModeContentIdOnlyMainInfo( Long siteId, String typeBy, int censorBy,
        long startPos, int pageSize, String orderFilter, String orderBy, String orderWay )
    {
        String sql = null;
        if( StringUtil.isStringNotNull( typeBy ) )
        {
            sql =

            "select ci.* from (select contentId as queryId from content_main_info where  siteId=? and censorState=? and typeFlag=? "
                + orderFilter
                + " order by "
                + orderBy
                + " "
                + orderWay
                + " limit ?,?) ids , content_main_info ci where ids.queryId=ci.contentId"
                + orderFilter + " order by ci." + orderBy + " " + orderWay;

            return pe.queryResultMap( sql, new Object[] { siteId, Integer.valueOf( censorBy ),
                typeBy, Long.valueOf( startPos ), Integer.valueOf( pageSize ) },
                new ContentValueResultCallBack() );
        }
        sql =

        "select ci.* from (select contentId as queryId from content_main_info where  siteId=? and censorState=? "
            + orderFilter
            + " order by "
            + orderBy
            + " "
            + orderWay
            + " limit ?,?) ids , content_main_info ci where ids.queryId=ci.contentId"
            + orderFilter
            + " order by ci." + orderBy + " " + orderWay;

        return pe.queryResultMap( sql, new Object[] { siteId, Integer.valueOf( censorBy ),
            Long.valueOf( startPos ), Integer.valueOf( pageSize ) },
            new ContentValueResultCallBack() );
    }

    public List queryLimitModeContentByIdsOnlyMainInfo( String classIds, int censorBy,
        int pageSize, String orderFilter, String orderBy, String orderWay )
    {
        String[] groupIdArray = ( String[] ) StringUtil.changeStringToList( classIds, "," )
            .toArray( new String[0] );

        StringBuffer buf = new StringBuffer( "(" );

        long id = -1L;

        boolean havaParam = false;
        for ( int i = 0; i < groupIdArray.length; i++ )
        {
            id = StringUtil.getLongValue( new String( groupIdArray[i] ), -1L );
            if( id >= 0L )
            {
                havaParam = true;

                buf.append( new String( groupIdArray[i] ) );
                if( i + 1 != groupIdArray.length )
                {
                    buf.append( "," );
                }
            }
        }
        buf.append( ")" );
        if( !havaParam )
        {
            return new ArrayList();
        }
        String sql = "select tmp.*, cc.className from (select ci.* from content_main_info as ci inner join (select contentId as queryId from content_main_info where classId in "
            +

            buf.toString()
            + " and censorState=? "
            + orderFilter
            + " order by "
            + orderBy
            + " "
            + orderWay
            + " limit ?) ids on ci.contentId=ids.queryId ) tmp, contentclass cc where tmp.classId=cc.classId order by tmp."
            + orderBy + " " + orderWay;

        return pe.queryResultMap( sql, new Object[] { Integer.valueOf( censorBy ),
            Integer.valueOf( pageSize ) }, new ContentValueResultCallBack() );
    }

    public List queryLimitContentByModelAndFlagId( boolean showAll, DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, String orderBy, String orderWay,
        long classId, int topFlag, Object idFlag, int pageFlag, Long startDateCid, Long endDateCid,
        int limitSize )
    {
        String index = "";
        if( "contentId".equals( orderBy ) )
        {
            index = "FORCE INDEX (tcm)";
        }
        else if( "orderIdFlag".equals( orderBy ) )
        {
            index = "FORCE INDEX (tcmo)";
        }
        String sqlNextDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + index
            + " where topFlag=? and classId=? and modelId=? and "
            + orderBy
            + "<? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlNextAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + index
            + " where topFlag=? and classId=? and modelId=? and "
            + orderBy
            + ">? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        String sqlPrevDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + index
            + " where topFlag=? and classId=? and modelId=? and "
            + orderBy
            + ">? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlPrevAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + index
            + " where topFlag=? and classId=? and modelId=? and "
            + orderBy
            + "<? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        String sqlDateModeNextDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + " where topFlag=? and classId=? and modelId=? and contentId>=? and contentId<=? and "
            + orderBy
            + "<? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlDateModeNextAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + " where topFlag=? and classId=? and modelId=? and contentId>=? and contentId<=? and "
            + orderBy
            + ">? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        String sqlDateModePrevDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + " where topFlag=? and classId=? and modelId=? and contentId>=? and contentId<=? and "
            + orderBy
            + ">? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlDateModePrevAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + " where topFlag=? and classId=? and modelId=? and contentId>=? and contentId<=? and "
            + orderBy
            + "<? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        List result = null;
        String sql = null;
        if( ( startDateCid == null ) && ( endDateCid == null ) )
        {
            if( "asc".equals( orderWay ) )
            {
                if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
                {
                    sql = sqlPrevAsc;
                }
                else
                {
                    sql = sqlNextAsc;
                }
            }
            else if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
            {
                sql = sqlPrevDesc;
            }
            else
            {
                sql = sqlNextDesc;
            }
            result = pe.queryResultMap( sql, new Object[] { Integer.valueOf( topFlag ),
                Long.valueOf( classId ), modelBean.getDataModelId(), idFlag,
                Integer.valueOf( limitSize ) }, new ContentValueResultCallBack() );
        }
        else
        {
            if( "asc".equals( orderWay ) )
            {
                if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
                {
                    sql = sqlDateModePrevAsc;
                }
                else
                {
                    sql = sqlDateModeNextAsc;
                }
            }
            else if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
            {
                sql = sqlDateModePrevDesc;
            }
            else
            {
                sql = sqlDateModeNextDesc;
            }
            result = pe.queryResultMap( sql, new Object[] { Integer.valueOf( topFlag ),
                Long.valueOf( classId ), modelBean.getDataModelId(), startDateCid, endDateCid,
                idFlag, Integer.valueOf( limitSize ) }, new ContentValueResultCallBack() );
        }
        return result;
    }

    public List queryLimitContentByModelAndFlagId( boolean showAll, DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, String orderBy, String orderWay,
        long classId, int topFlag, Integer censorBy, Object idFlag, int pageFlag,
        Long startDateCid, Long endDateCid, int limitSize )
    {
        String index = "";
        if( "contentId".equals( orderBy ) )
        {
            index = "FORCE INDEX (tcmccid)";
        }
        else if( "orderIdFlag".equals( orderBy ) )
        {
            index = "FORCE INDEX (tcmcoid)";
        }
        String sqlNextDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + index
            + " where topFlag=? and classId=? and "
            + orderBy
            + "<? and modelId=? and censorState=? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlNextAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + index
            + " where topFlag=? and classId=? and "
            + orderBy
            + ">? and modelId=? and censorState=? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        String sqlPrevDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + index
            + " where topFlag=? and classId=? and "
            + orderBy
            + ">? and modelId=? and censorState=? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlPrevAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + index
            + " where topFlag=? and classId=? and "
            + orderBy
            + "<? and modelId=? and censorState=? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        String sqlDateModeNextDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + index
            + " where topFlag=? and classId=? and modelId=? and censorState=? and contentId>=? and contentId<=? and "
            + orderBy
            + "<? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlDateModeNextAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + index
            + " where topFlag=? and classId=? and modelId=? and censorState=? and contentId>=? and contentId<=? and "
            + orderBy
            + ">? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        String sqlDateModePrevDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + index
            + " where topFlag=? and classId=? and modelId=? and censorState=? and contentId>=? and contentId<=? and "
            + orderBy
            + ">? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlDateModePrevAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info "
            + " where topFlag=? and classId=? and modelId=? and censorState=? and contentId>=? and contentId<=? and "
            + orderBy
            + "<? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        List result = null;
        String sql = null;
        if( ( startDateCid == null ) && ( endDateCid == null ) )
        {
            if( "asc".equals( orderWay ) )
            {
                if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
                {
                    sql = sqlPrevAsc;
                }
                else
                {
                    sql = sqlNextAsc;
                }
            }
            else if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
            {
                sql = sqlPrevDesc;
            }
            else
            {
                sql = sqlNextDesc;
            }
            result = pe.queryResultMap( sql, new Object[] { Integer.valueOf( topFlag ),
                Long.valueOf( classId ), idFlag, modelBean.getDataModelId(), censorBy,
                Integer.valueOf( limitSize ) }, new ContentValueResultCallBack() );
        }
        else
        {
            if( "asc".equals( orderWay ) )
            {
                if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
                {
                    sql = sqlDateModePrevAsc;
                }
                else
                {
                    sql = sqlDateModeNextAsc;
                }
            }
            else if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
            {
                sql = sqlDateModePrevDesc;
            }
            else
            {
                sql = sqlDateModeNextDesc;
            }
            result = pe.queryResultMap( sql, new Object[] { Integer.valueOf( topFlag ),
                Long.valueOf( classId ), modelBean.getDataModelId(), censorBy, startDateCid,
                endDateCid, idFlag, Integer.valueOf( limitSize ) },
                new ContentValueResultCallBack() );
        }
        return result;
    }

    public List queryLimitContentByModelAndFlagId( boolean showAll, DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, String orderBy, String orderWay,
        long classId, String typeFlag, int topFlag, Object idFlag, int pageFlag, Long startDateCid,
        Long endDateCid, int limitSize )
    {
        String sqlNextDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info FORCE INDEX (tcm) where topFlag=? and classId=? and "
            + orderBy
            + "<? and modelId=? and typeFlag=? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlNextAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info FORCE INDEX (tcm) where topFlag=? and classId=? and "
            + orderBy
            + ">? and modelId=? and typeFlag=? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        String sqlPrevDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info FORCE INDEX (tcm) where topFlag=? and classId=? and "
            + orderBy
            + ">? and modelId=? and typeFlag=? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlPrevAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info FORCE INDEX (tcm) where topFlag=? and classId=? and "
            + orderBy
            + "<? and modelId=? and typeFlag=? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        String sqlDateModeNextDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and modelId=? and typeFlag=? and contentId>=? and contentId<=? and "
            + orderBy
            + "<? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlDateModeNextAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and modelId=? and typeFlag=? and contentId>=? and contentId<=? and "
            + orderBy
            + ">? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        String sqlDateModePrevDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and modelId=? and typeFlag=? and contentId>=? and contentId<=? and "
            + orderBy
            + ">? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlDateModePrevAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and modelId=? and typeFlag=? and contentId>=? and contentId<=? and "
            + orderBy
            + "<? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        List result = null;
        String sql = null;
        if( ( startDateCid == null ) && ( endDateCid == null ) )
        {
            if( "asc".equals( orderWay ) )
            {
                if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
                {
                    sql = sqlPrevAsc;
                }
                else
                {
                    sql = sqlNextAsc;
                }
            }
            else if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
            {
                sql = sqlPrevDesc;
            }
            else
            {
                sql = sqlNextDesc;
            }
            result = pe.queryResultMap( sql, new Object[] { Integer.valueOf( topFlag ),
                Long.valueOf( classId ), idFlag, modelBean.getDataModelId(), typeFlag,
                Integer.valueOf( limitSize ) }, new ContentValueResultCallBack() );
        }
        else
        {
            if( "asc".equals( orderWay ) )
            {
                if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
                {
                    sql = sqlDateModePrevAsc;
                }
                else
                {
                    sql = sqlDateModeNextAsc;
                }
            }
            else if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
            {
                sql = sqlDateModePrevDesc;
            }
            else
            {
                sql = sqlDateModeNextDesc;
            }
            result = pe.queryResultMap( sql, new Object[] { Integer.valueOf( topFlag ),
                Long.valueOf( classId ), modelBean.getDataModelId(), typeFlag, startDateCid,
                endDateCid, idFlag, Integer.valueOf( limitSize ) },
                new ContentValueResultCallBack() );
        }
        return result;
    }

    public List queryLimitContentByModelAndFlagId( boolean showAll, DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, String orderBy, String orderWay,
        long classId, String typeFlag, int topFlag, Integer censorBy, Object idFlag, int pageFlag,
        Long startDateCid, Long endDateCid, int limitSize )
    {
        String index = "";
        if( "contentId".equals( orderBy ) )
        {
            index = "FORCE INDEX (tcm)";
        }
        else if( "orderIdFlag".equals( orderBy ) )
        {
            index = "FORCE INDEX (tcmo)";
        }
        String sqlNextDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and "
            + orderBy
            + "<? and modelId=? and typeFlag=? and censorState=? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlNextAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and "
            + orderBy
            + ">? and modelId=? and typeFlag=? and censorState=? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        String sqlPrevDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and "
            + orderBy
            + ">? and modelId=? and typeFlag=? and censorState=? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlPrevAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and "
            + orderBy
            + "<? and modelId=? and typeFlag=? and censorState=? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        String sqlDateModeNextDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and modelId=? and typeFlag=? and censorState=? and contentId>=? and contentId<=? and "
            + orderBy
            + "<? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlDateModeNextAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and modelId=? and typeFlag=? and censorState=? and contentId>=? and contentId<=? and "
            + orderBy
            + ">? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        String sqlDateModePrevDesc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and modelId=? and typeFlag=? and censorState=? and contentId>=? and contentId<=? and "
            + orderBy
            + ">? order by "
            + orderBy
            + " asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " desc";

        String sqlDateModePrevAsc = "select tmp.*, cm.*, cc.className "
            + ( showAll ? "," + perMysqlCodebean.getSelectColumn() : "" )
            + " from (select "
            + ( showAll ? "ci.*" : perMysqlCodebean.getListSelectColumn() )
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and modelId=? and typeFlag=? and censorState=? and contentId>=? and contentId<=? and "
            + orderBy
            + "<? order by "
            + orderBy
            + " desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by cm."
            + orderBy + " asc";

        List result = null;
        String sql = null;
        if( ( startDateCid == null ) && ( endDateCid == null ) )
        {
            if( "asc".equals( orderWay ) )
            {
                if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
                {
                    sql = sqlPrevAsc;
                }
                else
                {
                    sql = sqlNextAsc;
                }
            }
            else if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
            {
                sql = sqlPrevDesc;
            }
            else
            {
                sql = sqlNextDesc;
            }
            result = pe.queryResultMap( sql, new Object[] { Integer.valueOf( topFlag ),
                Long.valueOf( classId ), idFlag, modelBean.getDataModelId(), typeFlag, censorBy,
                Integer.valueOf( limitSize ) }, new ContentValueResultCallBack() );
        }
        else
        {
            if( "asc".equals( orderWay ) )
            {
                if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
                {
                    sql = sqlDateModePrevAsc;
                }
                else
                {
                    sql = sqlDateModeNextAsc;
                }
            }
            else if( ( -1 == pageFlag ) || ( -2 == pageFlag ) )
            {
                sql = sqlDateModePrevDesc;
            }
            else
            {
                sql = sqlDateModeNextDesc;
            }
            result = pe.queryResultMap( sql, new Object[] { Integer.valueOf( topFlag ),
                Long.valueOf( classId ), modelBean.getDataModelId(), typeFlag, censorBy,
                startDateCid, endDateCid, idFlag, Integer.valueOf( limitSize ) },
                new ContentValueResultCallBack() );
        }
        return result;
    }

    public List queryLimitContentByModelAndFlagIdLimitQueryMode( DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, String orderBy, String orderWay,
        long classId, Object idFlag, int pageFlag, Timestamp startDate, Timestamp endDate,
        int limitSize, Page page )
    {
        String sqlDesc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? order by "
            + orderBy
            + " desc limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " desc";

        String sqlAsc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? order by "
            + orderBy
            + " asc limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " asc";

        String sqlDateModeDesc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and "
            + orderBy
            + "<? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? order by orderIdFlag desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " desc";

        String sqlDateModeAsc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and "
            + orderBy
            + ">? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? order by orderIdFlag asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " desc";

        List result = null;
        String sql = null;
        if( ( startDate == null ) && ( endDate == null ) )
        {
            if( "asc".equals( orderWay ) )
            {
                sql = sqlAsc;
            }
            else
            {
                sql = sqlDesc;
            }
            result = pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ),
                modelBean.getDataModelId(), Long.valueOf( page.getFirstResult() ),
                Integer.valueOf( limitSize ) }, new ContentValueResultCallBack() );
        }
        else
        {
            if( startDate == null )
            {
                startDate = Constant.CONTENT.MIN_DATE;
            }
            else if( endDate == null )
            {
                endDate = Constant.CONTENT.MAX_DATE;
            }
            sql = sqlDateModeDesc;
            if( "asc".equals( orderWay ) )
            {
                sql = sqlDateModeAsc;
            }
            result = pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ), startDate,
                endDate, modelBean.getDataModelId(), Long.valueOf( page.getFirstResult() ),
                Integer.valueOf( limitSize ) }, new ContentValueResultCallBack() );
        }
        return result;
    }

    public List queryLimitContentByModelAndFlagIdLimitQueryMode( DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, String orderBy, String orderWay,
        long classId, Integer censorBy, Object idFlag, int pageFlag, Timestamp startDate,
        Timestamp endDate, int limitSize, Page page )
    {
        String sqlDesc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and censorState=? order by "
            + orderBy
            + " desc limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " desc";

        String sqlAsc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and censorState=? order by "
            + orderBy
            + " asc limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " asc";

        String sqlDateModeDesc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and "
            + orderBy
            + "<? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? and censorState=? order by orderIdFlag desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " desc";

        String sqlDateModeAsc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and "
            + orderBy
            + ">? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? and censorState=? order by orderIdFlag asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " desc";

        List result = null;
        String sql = null;
        if( ( startDate == null ) && ( endDate == null ) )
        {
            if( "asc".equals( orderWay ) )
            {
                sql = sqlAsc;
            }
            else
            {
                sql = sqlDesc;
            }
            result = pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ),
                modelBean.getDataModelId(), censorBy, Long.valueOf( page.getFirstResult() ),
                Integer.valueOf( limitSize ) }, new ContentValueResultCallBack() );
        }
        else
        {
            if( startDate == null )
            {
                startDate = Constant.CONTENT.MIN_DATE;
            }
            else if( endDate == null )
            {
                endDate = Constant.CONTENT.MAX_DATE;
            }
            sql = sqlDateModeDesc;
            if( "asc".equals( orderWay ) )
            {
                sql = sqlDateModeAsc;
            }
            result = pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ), startDate,
                endDate, modelBean.getDataModelId(), censorBy,
                Long.valueOf( page.getFirstResult() ), Integer.valueOf( limitSize ) },
                new ContentValueResultCallBack() );
        }
        return result;
    }

    public List queryLimitContentByModelAndFlagIdLimitQueryMode( DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, String orderBy, String orderWay,
        long classId, String typeFlag, Object idFlag, int pageFlag, Timestamp startDate,
        Timestamp endDate, int limitSize, Page page )
    {
        String sqlDesc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and typeFlag=? order by "
            + orderBy
            + " desc limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " desc";

        String sqlAsc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and typeFlag=? order by "
            + orderBy
            + " asc limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " asc";

        String sqlDateModeDesc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and "
            + orderBy
            + "<? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? and typeFlag=? order by orderIdFlag desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " desc";

        String sqlDateModeAsc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and "
            + orderBy
            + ">? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? and typeFlag=? order by orderIdFlag asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " desc";

        List result = null;
        String sql = null;
        if( ( startDate == null ) && ( endDate == null ) )
        {
            if( "asc".equals( orderWay ) )
            {
                sql = sqlAsc;
            }
            else
            {
                sql = sqlDesc;
            }
            result = pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ),
                modelBean.getDataModelId(), typeFlag, Long.valueOf( page.getFirstResult() ),
                Integer.valueOf( limitSize ) }, new ContentValueResultCallBack() );
        }
        else
        {
            if( startDate == null )
            {
                startDate = Constant.CONTENT.MIN_DATE;
            }
            else if( endDate == null )
            {
                endDate = Constant.CONTENT.MAX_DATE;
            }
            sql = sqlDateModeDesc;
            if( "asc".equals( orderWay ) )
            {
                sql = sqlDateModeAsc;
            }
            result = pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ), startDate,
                endDate, modelBean.getDataModelId(), typeFlag,
                Long.valueOf( page.getFirstResult() ), Integer.valueOf( limitSize ) },
                new ContentValueResultCallBack() );
        }
        return result;
    }

    public List queryLimitContentByModelAndFlagIdLimitQueryMode( DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, String orderBy, String orderWay,
        long classId, String typeFlag, Integer censorBy, Object idFlag, int pageFlag,
        Timestamp startDate, Timestamp endDate, int limitSize, Page page )
    {
        String sqlDesc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and typeFlag=? and censorState=? order by "
            + orderBy
            + " desc limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " desc";

        String sqlAsc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and typeFlag=? and censorState=? order by "
            + orderBy
            + " asc limit ?,?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " asc";

        String sqlDateModeDesc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and "
            + orderBy
            + "<? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? and typeFlag=? and censorState=? order by orderIdFlag desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " desc";

        String sqlDateModeAsc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and "
            + orderBy
            + ">? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? and typeFlag=? and censorState=? order by orderIdFlag asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by "
            + orderBy + " desc";

        List result = null;
        String sql = null;
        if( ( startDate == null ) && ( endDate == null ) )
        {
            if( "asc".equals( orderWay ) )
            {
                sql = sqlAsc;
            }
            else
            {
                sql = sqlDesc;
            }
            result = pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ),
                modelBean.getDataModelId(), typeFlag, censorBy,
                Long.valueOf( page.getFirstResult() ), Integer.valueOf( limitSize ) },
                new ContentValueResultCallBack() );
        }
        else
        {
            if( startDate == null )
            {
                startDate = Constant.CONTENT.MIN_DATE;
            }
            else if( endDate == null )
            {
                endDate = Constant.CONTENT.MAX_DATE;
            }
            sql = sqlDateModeDesc;
            if( "asc".equals( orderWay ) )
            {
                sql = sqlDateModeAsc;
            }
            result = pe.queryResultMap( sql, new Object[] { Long.valueOf( classId ), startDate,
                endDate, modelBean.getDataModelId(), typeFlag, censorBy,
                Long.valueOf( page.getFirstResult() ), Integer.valueOf( limitSize ) },
                new ContentValueResultCallBack() );
        }
        return result;
    }

    public List queryLimitContentByModelAndFlagId( DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, String orderBy, String orderWay,
        long classId, String typeBy, long topFlag, double orderIdFlag, Timestamp startDate,
        Timestamp endDate, int limitSize )
    {
        String sqlDesc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and typeFlag=? and classId=? and orderIdFlag<? and modelId=? order by orderIdFlag desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by ? desc";

        String sqlAsc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and typeFlag=? and classId=? and orderIdFlag>? and modelId=? order by orderIdFlag asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by ? desc";

        String sqlDateModeDesc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and typeFlag=? classId=? and orderIdFlag<? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? order by orderIdFlag desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by ? desc";

        String sqlDateModeAsc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and typeFlag=? and classId=? and orderIdFlag>? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? order by orderIdFlag asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by ? desc";

        List result = null;
        String sql = null;
        if( ( startDate == null ) && ( endDate == null ) )
        {
            sql = sqlDesc;
            if( "asc".equals( orderWay ) )
            {
                sql = sqlAsc;
            }
            result = pe.queryResultMap( sql, new Object[] { Long.valueOf( topFlag ), typeBy,
                Long.valueOf( classId ), Double.valueOf( orderIdFlag ), modelBean.getDataModelId(),
                Integer.valueOf( limitSize ), orderBy }, new ContentValueResultCallBack() );
        }
        else
        {
            if( startDate == null )
            {
                startDate = Constant.CONTENT.MIN_DATE;
            }
            else if( endDate == null )
            {
                endDate = Constant.CONTENT.MAX_DATE;
            }
            sql = sqlDateModeDesc;
            if( "asc".equals( orderWay ) )
            {
                sql = sqlDateModeAsc;
            }
            result = pe.queryResultMap( sql, new Object[] { Long.valueOf( topFlag ), typeBy,
                Long.valueOf( classId ), Double.valueOf( orderIdFlag ), startDate, endDate,
                modelBean.getDataModelId(), Integer.valueOf( limitSize ), orderBy },
                new ContentValueResultCallBack() );
        }
        return result;
    }

    public List queryLimitContentByModelAndFlagId( DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, String orderBy, String orderWay,
        long classId, long topFlag, double orderIdFlag, Integer censorBy, Timestamp startDate,
        Timestamp endDate, int limitSize )
    {
        String sqlDesc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and orderIdFlag<? and modelId=? and censorState=? order by orderIdFlag desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by ? desc";

        String sqlAsc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and orderIdFlag>? and modelId=? and censorState=? order by orderIdFlag asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by ? desc";

        String sqlDateModeDesc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and orderIdFlag<? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? and censorState=? order by orderIdFlag desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by ? desc";

        String sqlDateModeAsc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and classId=? and orderIdFlag>? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? and censorState=? order by orderIdFlag asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by ? desc";

        List result = null;
        String sql = null;
        if( ( startDate == null ) && ( endDate == null ) )
        {
            sql = sqlDesc;
            if( "asc".equals( orderWay ) )
            {
                sql = sqlAsc;
            }
            result = pe
                .queryResultMap( sql, new Object[] { Long.valueOf( topFlag ),
                    Long.valueOf( classId ), Double.valueOf( orderIdFlag ),
                    modelBean.getDataModelId(), censorBy, Integer.valueOf( limitSize ), orderBy },
                    new ContentValueResultCallBack() );
        }
        else
        {
            if( startDate == null )
            {
                startDate = Constant.CONTENT.MIN_DATE;
            }
            else if( endDate == null )
            {
                endDate = Constant.CONTENT.MAX_DATE;
            }
            sql = sqlDateModeDesc;
            if( "asc".equals( orderWay ) )
            {
                sql = sqlDateModeAsc;
            }
            result = pe.queryResultMap( sql, new Object[] { Long.valueOf( topFlag ),
                Long.valueOf( classId ), Double.valueOf( orderIdFlag ), startDate, endDate,
                modelBean.getDataModelId(), censorBy, Integer.valueOf( limitSize ), orderBy },
                new ContentValueResultCallBack() );
        }
        return result;
    }

    public List queryLimitContentByModelAndFlagId( DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean perMysqlCodebean, String orderWay, long classId,
        String typeBy, long topFlag, double orderIdFlag, Integer censorBy, Timestamp startDate,
        Timestamp endDate, int limitSize )
    {
        String sqlDesc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and typeFlag=? and classId=? and orderIdFlag<? and modelId=? and censorState=? order by orderIdFlag desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by orderIdFlag desc";

        String sqlAsc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and typeFlag=? and classId=? and orderIdFlag>? and modelId=? and censorState=? order by orderIdFlag asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by orderIdFlag desc";

        String sqlDateModeDesc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and typeFlag=? and classId=? and orderIdFlag<? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? and censorState=? order by orderIdFlag desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by orderIdFlag desc";

        String sqlDateModeAsc = "select tmp.*, cm.*, cc.className from (select "
            + perMysqlCodebean.getListSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where topFlag=? and typeFlag=? and classId=? and orderIdFlag>? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? and censorState=? order by orderIdFlag asc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by orderIdFlag desc";

        List result = null;
        String sql = null;
        if( ( startDate == null ) && ( endDate == null ) )
        {
            sql = sqlDesc;
            if( "asc".equals( orderWay ) )
            {
                sql = sqlAsc;
            }
            result = pe.queryResultMap( sql, new Object[] { Long.valueOf( topFlag ), typeBy,
                Long.valueOf( classId ), Double.valueOf( orderIdFlag ), modelBean.getDataModelId(),
                censorBy, Integer.valueOf( limitSize ) }, new ContentValueResultCallBack() );
        }
        else
        {
            if( startDate == null )
            {
                startDate = Constant.CONTENT.MIN_DATE;
            }
            else if( endDate == null )
            {
                endDate = Constant.CONTENT.MAX_DATE;
            }
            sql = sqlDateModeDesc;
            if( "asc".equals( orderWay ) )
            {
                sql = sqlDateModeAsc;
            }
            result = pe.queryResultMap( sql, new Object[] { Long.valueOf( topFlag ), typeBy,
                Long.valueOf( classId ), Double.valueOf( orderIdFlag ), startDate, endDate,
                modelBean.getDataModelId(), censorBy, Integer.valueOf( limitSize ) },
                new ContentValueResultCallBack() );
        }
        return result;
    }

    public Integer[] queryTopContentPageInfo( Long classId, Long modelId, Long startDateCid,
        Long endDateCid, int pageSize )
    {
        String sql = "select count(*) from content_main_info where topFlag=1 and classId=? and modelId=?";

        String sqlDateMode = "select count(*) from content_main_info where topFlag=1 and classId=? and modelId=? and contentId>=? and contentId<=? ";

        String key = "queryTopContentPageInfo自定义" + classId + modelId + pageSize + startDateCid
            + endDateCid;

        Integer[] topPageInfo = ( Integer[] ) countCache.getEntry( key );
        if( topPageInfo == null )
        {
            Integer topCount = null;
            if( ( startDateCid == null ) && ( endDateCid == null ) )
            {
                topCount = ( Integer ) pe.querySingleObject( sql,
                    new Object[] { classId, modelId }, Integer.class );
            }
            else
            {
                topCount = ( Integer ) pe.querySingleObject( sqlDateMode, new Object[] { classId,
                    modelId, startDateCid, endDateCid }, Integer.class );
            }
            topPageInfo = disposeTopInfo( topCount, pageSize );

            countCache.putEntry( key, topPageInfo );
        }
        return topPageInfo;
    }

    public Integer[] queryTopContentPageInfo( String typeBy, Long classId, Long modelId,
        Long startDateCid, Long endDateCid, int pageSize )
    {
        String sql = "select count(*) from content_main_info where topFlag=1 and typeFlag=? and classId=? and modelId=?";

        String sqlDateMode = "select count(*) from content_main_info where topFlag=1 and typeFlag=? and classId=? and modelId=? and contentId>=? and contentId<=?";

        String key = "queryTopContentPageInfo自定义" + classId + modelId + typeBy + pageSize
            + startDateCid + endDateCid;

        Integer[] topPageInfo = ( Integer[] ) countCache.getEntry( key );
        if( topPageInfo == null )
        {
            Integer topCount = null;
            if( ( startDateCid == null ) && ( endDateCid == null ) )
            {
                topCount = ( Integer ) pe.querySingleObject( sql, new Object[] { typeBy, classId,
                    modelId }, Integer.class );
            }
            else
            {
                topCount = ( Integer ) pe.querySingleObject( sqlDateMode, new Object[] { typeBy,
                    classId, modelId, startDateCid, endDateCid }, Integer.class );
            }
            topPageInfo = disposeTopInfo( topCount, pageSize );

            countCache.putEntry( key, topPageInfo );
        }
        return topPageInfo;
    }

    public Integer[] queryTopContentPageInfo( Long classId, Long modelId, Integer censorBy,
        Long startDateCid, Long endDateCid, int pageSize )
    {
        String sql = "select count(*) from content_main_info where topFlag=1 and classId=? and modelId=? and censorState=?";

        String sqlDateMode = "select count(*) from content_main_info where topFlag=1 and classId=? and modelId=? and censorState=? and contentId>=? and contentId<=?";

        String key = "queryTopContentPageInfo自定义" + classId + modelId + censorBy + pageSize
            + startDateCid + endDateCid;

        Integer[] topPageInfo = ( Integer[] ) countCache.getEntry( key );
        if( topPageInfo == null )
        {
            Integer topCount = null;
            if( ( startDateCid == null ) && ( endDateCid == null ) )
            {
                topCount = ( Integer ) pe.querySingleObject( sql, new Object[] { classId, modelId,
                    censorBy }, Integer.class );
            }
            else
            {
                topCount = ( Integer ) pe.querySingleObject( sqlDateMode, new Object[] { classId,
                    modelId, censorBy, startDateCid, endDateCid }, Integer.class );
            }
            topPageInfo = disposeTopInfo( topCount, pageSize );

            countCache.putEntry( key, topPageInfo );
        }
        return topPageInfo;
    }

    public Integer[] queryTopContentPageInfo( String typeBy, Long classId, Long modelId,
        Integer censorBy, Long startDateCid, Long endDateCid, int pageSize )
    {
        String sql = "select count(*) from content_main_info where topFlag=1 and typeFlag=? and classId=? and modelId=? and censorState=?";

        String sqlDateMode = "select count(*) from content_main_info where topFlag=1 and typeFlag=? and classId=? and modelId=? and censorState=? and contentId>=? and contentId<=?";

        String key = "queryTopContentPageInfo自定义" + classId + modelId + typeBy + censorBy
            + pageSize + startDateCid + endDateCid;

        Integer[] topPageInfo = ( Integer[] ) countCache.getEntry( key );
        if( topPageInfo == null )
        {
            Integer topCount = null;
            if( ( startDateCid == null ) && ( endDateCid == null ) )
            {
                topCount = ( Integer ) pe.querySingleObject( sql, new Object[] { typeBy, classId,
                    modelId, censorBy }, Integer.class );
            }
            else
            {
                topCount = ( Integer ) pe.querySingleObject( sqlDateMode, new Object[] { typeBy,
                    classId, modelId, censorBy, startDateCid, endDateCid }, Integer.class );
            }
            topPageInfo = disposeTopInfo( topCount, pageSize );

            countCache.putEntry( key, topPageInfo );
        }
        return topPageInfo;
    }

    public Double queryLastTopContentOrderIdFlag( Long classId, Timestamp startDate,
        Timestamp endDate, Long modelId )
    {
        String sql = "select orderIdFlag from content_main_info where topFlag=1 and classId=? and modelId=? order by orderIdFlag asc limit 1";
        String sqlDateMode = "select orderIdFlag from content_main_info where topFlag=1 and classId=? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? order by orderIdFlag asc limit 1";

        Double result = null;
        if( ( startDate == null ) && ( endDate == null ) )
        {
            result = ( Double ) pe.querySingleObject( sql, new Object[] { classId, modelId },
                Double.class );
        }
        else
        {
            if( startDate == null )
            {
                startDate = Constant.CONTENT.MIN_DATE;
            }
            else if( endDate == null )
            {
                endDate = Constant.CONTENT.MAX_DATE;
            }
            result = ( Double ) pe.querySingleObject( sqlDateMode, new Object[] { classId,
                startDate, endDate, modelId }, Double.class );
        }
        return result;
    }

    public Double queryLastTopContentOrderIdFlag( String typeBy, Long classId, Timestamp startDate,
        Timestamp endDate, Long modelId )
    {
        String sql = "select orderIdFlag from content_main_info where topFlag=1 and typeFlag=? and classId=? and modelId=? order by orderIdFlag asc limit 1";
        String sqlDateMode = "select orderIdFlag from content_main_info where topFlag=1 and typeFlag=? and classId=? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? order by orderIdFlag asc limit 1";

        Double result = null;
        if( ( startDate == null ) && ( endDate == null ) )
        {
            result = ( Double ) pe.querySingleObject( sql,
                new Object[] { typeBy, classId, modelId }, Double.class );
        }
        else
        {
            if( startDate == null )
            {
                startDate = Constant.CONTENT.MIN_DATE;
            }
            else if( endDate == null )
            {
                endDate = Constant.CONTENT.MAX_DATE;
            }
            result = ( Double ) pe.querySingleObject( sqlDateMode, new Object[] { typeBy, classId,
                startDate, endDate, modelId }, Double.class );
        }
        return result;
    }

    public Double queryLastTopContentOrderIdFlag( Long classId, Timestamp startDate,
        Timestamp endDate, Long modelId, Integer censorBy )
    {
        String sql = "select orderIdFlag from content_main_info where topFlag=1 and classId=? and modelId=? and censorState=? order by orderIdFlag asc limit 1";
        String sqlDateMode = "select orderIdFlag from content_main_info where topFlag=1 and classId=? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? and censorState=? order by orderIdFlag asc limit 1";

        Double result = null;
        if( ( startDate == null ) && ( endDate == null ) )
        {
            result = ( Double ) pe.querySingleObject( sql, new Object[] { classId, modelId,
                censorBy }, Double.class );
        }
        else
        {
            if( startDate == null )
            {
                startDate = Constant.CONTENT.MIN_DATE;
            }
            else if( endDate == null )
            {
                endDate = Constant.CONTENT.MAX_DATE;
            }
            result = ( Double ) pe.querySingleObject( sqlDateMode, new Object[] { classId,
                startDate, endDate, modelId, censorBy }, Double.class );
        }
        return result;
    }

    public Double queryLastTopContentOrderIdFlag( String typeBy, Long classId, Timestamp startDate,
        Timestamp endDate, Long modelId, Integer censorBy )
    {
        String sql = "select orderIdFlag from content_main_info where topFlag=1 and typeFlag=? and classId=? and modelId=? and censorState=? order by orderIdFlag asc limit 1";
        String sqlDateMode = "select orderIdFlag from content_main_info where topFlag=1 and typeFlag=? and classId=? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? and censorState=? order by orderIdFlag asc limit 1";

        Double result = null;
        if( ( startDate == null ) && ( endDate == null ) )
        {
            result = ( Double ) pe.querySingleObject( sql, new Object[] { typeBy, classId, modelId,
                censorBy }, Double.class );
        }
        else
        {
            if( startDate == null )
            {
                startDate = Constant.CONTENT.MIN_DATE;
            }
            else if( endDate == null )
            {
                endDate = Constant.CONTENT.MAX_DATE;
            }
            result = ( Double ) pe.querySingleObject( sqlDateMode, new Object[] { typeBy, classId,
                startDate, endDate, modelId, censorBy }, Double.class );
        }
        return result;
    }

    public List queryLimitTopContentByModelAndFlagId( DataModelBean modelBean, String orderBy,
        long classId, long topFlag, double orderIdFlag, int limitSize )
    {
        String sqlDesc = "select tmp.*, cm.*, cc.className from (select * from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where contentId<? and topFlag>-1 and orderIdFlag>-1 and modelId=? order by topFlag desc, orderIdFlag desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by topFlag desc, orderIdFlag desc ";

        String sqlAsc = "select tmp.*, cm.*, cc.className from (select * from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where contentId>? and topFlag>-1 and orderIdFlag>-1 and modelId=? order by topFlag asc, orderIdFlag asc  limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by topFlag desc, orderIdFlag desc ";

        String sql = sqlDesc;
        if( "asc".equals( orderBy ) )
        {
            sql = sqlAsc;
        }
        List result = pe.queryResultMap( sql, new Object[] { Long.valueOf( topFlag ),
            Double.valueOf( orderIdFlag ), Integer.valueOf( limitSize ) },
            new ContentValueResultCallBack() );

        return result;
    }

    public List queryLimitPublishSuccessContentByModelAndFlagId( DataModelBean modelBean,
        Long classId, int limitSize )
    {
        String sql = "select tmp.*, cm.*, cc.className from (select * from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and censorState=2 order by contentId desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by contentId desc";

        List result = pe.queryResultMap( sql,
            new Object[] { classId, Integer.valueOf( limitSize ) },
            new ContentValueResultCallBack() );

        return result;
    }

    public List queryLimitPublishSuccessContentByModelAndFlagId( DataModelBean modelBean,
        Long classId, Long firstContentId, int limitSize )
    {
        String sql = "select tmp.*, cm.*, cc.className from (select * from "
            + modelBean.getRelateTableName()
            + " as ci inner join"
            + " (select contentId as queryId from content_main_info where classId=? and  contentId<? order by contentId desc limit ?) ids on ci.contentId=ids.queryId ) tmp, content_main_info cm, contentclass cc where cm.contentId=tmp.contentId and cm.classId=cc.classId order by contentId desc";

        List result = pe.queryResultMap( sql, new Object[] { classId, firstContentId,
            Integer.valueOf( limitSize ) }, new ContentValueResultCallBack() );

        return result;
    }

    public long queryFirstContentId( Long modelId )
    {
        String sql = "select contentId from content_main_info where modelId=? order by contentId desc limit 1 ";

        Long result = ( Long ) pe.querySingleObject( sql, new Object[] { modelId }, Long.class );
        if( result == null )
        {
            return -1L;
        }
        return result.longValue();
    }

    public long queryFirstContentId( Long classId, Long modelId )
    {
        String sql = "select contentId from content_main_info where classId=? and modelId=? order by contentId asc limit 1 ";

        Long result = ( Long ) pe.querySingleObject( sql, new Object[] { classId, modelId },
            Long.class );
        if( result == null )
        {
            return -1L;
        }
        return result.longValue();
    }

    public Map queryFirstCommendContentQueryFlag( Long modelId )
    {
        String sql = "select topFlag, orderIdFlag from content_main_info where commendId=1 and modelId=? order by orderIdFlag asc limit 1 ";

        Map result = pe.querySingleResultMap( sql, new Object[] { modelId } );

        return result;
    }

    public Map queryFirstCommendContentQueryFlag( Long classId, Timestamp startDate,
        Timestamp endDate, Long modelId )
    {
        String sql = "select topFlag, orderIdFlag from content_main_info where commendFlag=1 and classId=? and modelId=? order by orderIdFlag asc limit 1";

        String sqlDateMode = "select topFlag, orderIdFlag from content_main_info where commendFlag=1 and classId=? and appearStartDateTime>=? and appearEndDateTime<=? and modelId=? order by orderIdFlag asc limit 1";

        Map result = null;
        if( ( startDate == null ) && ( endDate == null ) )
        {
            result = pe.querySingleResultMap( sql, new Object[] { classId, modelId } );
        }
        else
        {
            if( startDate == null )
            {
                startDate = Constant.CONTENT.MIN_DATE;
            }
            else if( endDate == null )
            {
                endDate = Constant.CONTENT.MAX_DATE;
            }
            result = pe.querySingleResultMap( sqlDateMode, new Object[] { classId, startDate,
                endDate, modelId } );
        }
        return result;
    }

    public Map querySingleNextContentById( Double orderId, Long classId, Long modelId )
    {
        String sql = "select * from content_main_info where contentId=(select contentId from content_main_info where censorState=1 and classId=? and modelId=? and orderIdFlag<? order by orderIdFlag desc limit 1)";

        return pe.querySingleResultMap( sql, new Object[] { classId, modelId, orderId },
            new ContentValueResultCallBack() );
    }

    public Map querySinglePrevContentById( Double orderId, Long classId, Long modelId )
    {
        String sql = "select * from content_main_info where contentId=(select contentId from content_main_info where censorState=1 and classId=? and modelId=? and orderIdFlag>? order by orderIdFlag asc limit 1)";

        return pe.querySingleResultMap( sql, new Object[] { classId, modelId, orderId },
            new ContentValueResultCallBack() );
    }

    public String queryTextColumnVal( DataModelBean modelBean, ModelFiledInfoBean filedInfoBean,
        Long contentId )
    {
        String sql = "select jtopcms_def_" + filedInfoBean.getFieldSign() + " from "
            + modelBean.getRelateTableName() + " where contentId=?";

        return ( String ) pe.querySingleObject( sql, new Object[] { contentId }, String.class );
    }

    public Map querySingleUserDefineContent( ModelPersistenceMySqlCodeBean sqlCodeBean,
        String modelTableName, Long id, Integer pos )
    {
        String sql = "select  mi.*, cpi.pos, cpi.pageTitle, cpi.pageContent, cpi.pageStaticUrl, "
            +

            sqlCodeBean.getSelectColumn()
            + " from content_main_info mi left join "
            + modelTableName
            + " tmp on mi.contentId=tmp.contentId left join content_assistant_page_info cpi on mi.contentId=cpi.contentId and pos=? where mi.contentId=?";
        return pe.querySingleResultMap( sql, new Object[] { pos, id },
            new ContentValueResultCallBack() );
    }

    public Map querySingleUserDefineContent( ModelPersistenceMySqlCodeBean sqlCodeBean,
        String modelTableName, Long id )
    {
        String sql = "select mi.*, " + sqlCodeBean.getSelectColumn()
            + " from content_main_info mi left join " + modelTableName
            + " tmp on mi.contentId=tmp.contentId where mi.contentId=?";
        return pe.querySingleResultMap( sql, new Object[] { id }, new ContentValueResultCallBack() );
    }

    public Map querySingleTrashUserDefineContent( ModelPersistenceMySqlCodeBean sqlCodeBean,
        String modelTableName, Long id )
    {
        String sql = "select mi.*, " + sqlCodeBean.getSelectColumn()
            + " from content_trash_main_info mi left join " + modelTableName
            + " tmp on mi.contentId=tmp.contentId where mi.contentId=?";
        return pe.querySingleResultMap( sql, new Object[] { id }, new ContentValueResultCallBack() );
    }

    public Map querySingleUserDefineContentManageMode( ModelPersistenceMySqlCodeBean sqlCodeBean,
        String modelTableName, Long id )
    {
        String sql = "select mi.*, " + sqlCodeBean.getSelectColumn()
            + " from content_main_info mi left join " + modelTableName
            + " tmp on mi.contentId=tmp.contentId where mi.contentId=?";
        return pe.querySingleResultMap( sql, new Object[] { id },
            new ContentValueResultManageModeCallBack() );
    }

    public Map querySingleUserDefineContentOnlyModelData(
        ModelPersistenceMySqlCodeBean sqlCodeBean, String modelTableName, Long id, String siteFlag,
        Long modelId )
    {
        if( ( sqlCodeBean == null ) || ( StringUtil.isStringNull( sqlCodeBean.getSelectColumn() ) ) )
        {
            return Collections.EMPTY_MAP;
        }
        String sql = "select " + sqlCodeBean.getSelectColumn() + " from " + modelTableName
            + " tmp where tmp.contentId=?";

        return pe.querySingleResultMap( sql, new Object[] { id }, new ContentValueResultCallBack(
            null, siteFlag, modelId ) );
    }

    public Map querySingleUserDefineContentOnlyModelDataResultNotDisposeInfo(
        ModelPersistenceMySqlCodeBean sqlCodeBean, String modelTableName, Long id )
    {
        return pe.querySingleResultMap( sqlCodeBean.getSelectSql(), new Object[] { id } );
    }

    public UpdateState deleteContentMainInfo( Long contentId )
    {
        String sql = "delete from content_main_info where contentId=?";
        return pe.update( sql, new Object[] { contentId } );
    }

    public UpdateState deleteContentMainInfoBySiteId( Long siteId )
    {
        String sql = "delete from content_main_info where siteId=?";
        return pe.update( sql, new Object[] { siteId } );
    }

    public UpdateState deleteTrashContentMainInfo( Long contentId )
    {
        String sql = "delete from content_trash_main_info where contentId=?";
        return pe.update( sql, new Object[] { contentId } );
    }

    public UpdateState deleteUserDefineInfo( DataModelBean modelBean, Long contentId )
    {
        if( modelBean == null )
        {
            return new UpdateState();
        }
        String sql = "delete from " + modelBean.getRelateTableName() + " where contentId=?";
        return pe.update( sql, new Object[] { contentId } );
    }

    public void deleteUserDefineInfoByClassIdAndModelId( DataModelBean modelBean, Long classId )
    {
        String sql = "delete from "
            + modelBean.getRelateTableName()
            + " where contentId in (select contentId from content_main_info where classId=? and modelId=?)";
        pe.update( sql, new Object[] { classId, modelBean.getDataModelId() } );
    }

    public Map querySingleContentMainInfo( Long targetId )
    {
        String sql = "select ai.* from content_main_info ai where ai.contentId=?";

        return pe.querySingleResultMap( sql, new Object[] { targetId } );
    }

    public Map querySingleTrashContentMainInfo( Long targetId )
    {
        String sql = "select ai.* from content_trash_main_info ai where ai.contentId=?";

        return pe.querySingleResultMap( sql, new Object[] { targetId },
            new ContentValueResultCallBack( null, null, null ) );
    }

    public Long querySingleContentMainInfoRelateCreatorId( Long targetId )
    {
        String sql = "select userId from systemuser where userName=(select creator from content_main_info where contentId=?)";

        return ( Long ) pe.querySingleObject( sql, new Object[] { targetId }, Long.class );
    }

    public ContentMainInfoBean querySingleContentMainInfoBean( Long targetId )
    {
        String sql = "select ai.* from content_main_info ai where ai.contentId=?";

        return ( ContentMainInfoBean ) pe.querySingleRow( sql, new Object[] { targetId },
            new ContentMainInfoBeanTransform() );
    }

    public List querySingleContentMainInfoBeanByIds( List idList )
    {
        StringBuffer buf = new StringBuffer( "(" );

        long id = -1L;

        boolean havaParam = false;
        for ( int i = 0; i < idList.size(); i++ )
        {
            id = StringUtil.getLongValue( ( String ) idList.get( i ), -1L );
            if( id >= 0L )
            {
                havaParam = true;

                buf.append( ( String ) idList.get( i ) );
                if( i + 1 != idList.size() )
                {
                    buf.append( "," );
                }
            }
        }
        buf.append( ")" );
        if( !havaParam )
        {
            return null;
        }
        String sql = "select ai.* from content_main_info ai where ai.contentId in "
            + buf.toString();

        return pe.queryResultMap( sql, new ContentValueResultCallBack() );
    }

    public List querySingleTrashContentMainInfoBeanByIds( List idList )
    {
        StringBuffer buf = new StringBuffer( "(" );

        long id = -1L;

        boolean havaParam = false;
        for ( int i = 0; i < idList.size(); i++ )
        {
            id = StringUtil.getLongValue( ( String ) idList.get( i ), -1L );
            if( id >= 0L )
            {
                havaParam = true;

                buf.append( ( String ) idList.get( i ) );
                if( i + 1 != idList.size() )
                {
                    buf.append( "," );
                }
            }
        }
        buf.append( ")" );
        if( !havaParam )
        {
            return new ArrayList();
        }
        String sql = "select ai.* from content_trash_main_info ai where ai.contentId in "
            + buf.toString();

        return pe.queryResultMap( sql, new ContentValueResultCallBack() );
    }

    public Map queryBigestOrderArticleByNextId( Long nextId, Long classId, String directFlag )
    {
        String sql = "select * from content_main_info where classId=? and orderIdFlag<? order by orderIdFlag desc limit 1";
        String sqlGreat = "select * from content_main_info where classId=? and orderIdFlag>? order by orderIdFlag asc limit 1";
        if( "great".equals( directFlag ) )
        {
            return pe.querySingleResultMap( sqlGreat, new Object[] { classId, nextId } );
        }
        return null;
    }

    public List queryContentOrderInfo( Long targetOrderId, Long nextOrderId, Long classId,
        String directFlag )
    {
        String sqlGreat = "select contentId, orderIdFlag from content_main_info where classId=? and orderIdFlag>? and orderIdFlag <=? order by orderIdFlag asc";
        String sql = "select contentId, orderIdFlag from content_main_info where classId=? and orderIdFlag>=? and orderIdFlag<=? order by orderIdFlag desc";
        if( "great".equals( directFlag ) )
        {
            return pe.queryResultMap( sqlGreat,
                new Object[] { classId, nextOrderId, targetOrderId } );
        }
        return pe.queryResultMap( sql, new Object[] { classId, targetOrderId, nextOrderId } );
    }

    public void updateContentOrderId( Long contentId, Long tmpOrderId )
    {
        String sql = "update content_main_info set orderIdFlag=? where contentId=?";
        pe.update( sql, new Object[] { tmpOrderId, contentId } );
    }

    public Map queryBigestOrderContentByNextId( Double nextOrderFlag )
    {
        String sql = "select * from content_main_info where contentId=(select contentId from content_main_info where orderIdFlag>? limit 1)";
        return pe.querySingleResultMap( sql, new Object[] { nextOrderFlag } );
    }

    public List queryAllNotSysBigestOrderArticleByNextId( Double nextOrderFlag, Long floorValue )
    {
        String sql = "select ci.contentId, ci.orderIdFlag from content_main_info ci inner join (select contentId from content_main_info where orderIdFlag>? and floor(orderIdFlag)=? and isSystemOrder=0) queryIds on queryIds.contentId=ci.contentId order by orderIdFlag asc";
        return pe.queryResultMap( sql, new Object[] { nextOrderFlag, floorValue } );
    }

    public void updateContentOrderIdAndFlag( Long contentId, Double newOrderId, Integer sysOrderFlag )
    {
        String sql = "update content_main_info set orderIdFlag=?, isSystemOrder=? where contentId=?";
        pe.update( sql, new Object[] { newOrderId, sysOrderFlag, contentId } );
    }

    public void updateContentTopFlag( Long contentId, Integer topFlag )
    {
        String sql = "update content_main_info set topFlag=? where contentId=?";
        pe.update( sql, new Object[] { topFlag, contentId } );
    }

    public void deleteAllContentAndChildrenContentMainInfoByClassId( Long targetClassId,
        String wCond )
    {
        String sql = "delete from content_main_info where (" + wCond + ")";
        pe.update( sql );
    }

    public void updateContentStaticPageURL( String endStaticClassFilePath, Long contentId )
    {
        String sql = "update content_main_info set staticPageURL=? where contentId=?";
        pe.update( sql, new Object[] { endStaticClassFilePath, contentId } );
    }

    public void updatePageContentStaticURL( String endStaticClassFilePath, Long contentId,
        Integer posId )
    {
        String sql = "update content_assistant_page_info set pageStaticUrl=? where contentId=? and pos=?";
        pe.update( sql, new Object[] { endStaticClassFilePath, contentId, posId } );
    }

    public Long queryAllInWorkflowUserDefineContentCount( String sqlOrCond, Long loginUserId,
        Long loginUserOrgId )
    {
        String sql = "select count(*) from (select ci.title, ci.creator, ci.addTime, ci.modelId, ci.classId as c1, wsi.stepNodeName, wo.* from content_main_info ci, workflow_operation wo, workflow_step_info wsi, (select wo.contentId from workflow_operation wo,workflow_actor wa where wa.censorStep=wo.currentStep and wa.flowId=wo.flowId and wa.type=1 and wo.infoType=1 and ("
            + sqlOrCond
            + ")) ids where ids.contentId=ci.contentId and ids.contentId=wo.contentId and wo.flowId=wsi.flowId and wo.currentStep=wsi.stepId"
            + " union "
            + "select ci.title, ci.creator, ci.addTime,  ci.modelId, ci.classId as c2, wsi.stepNodeName, wo.* from content_main_info ci, workflow_operation wo, workflow_step_info wsi, (select wo.contentId from workflow_operation wo,workflow_actor wa where wa.censorStep=wo.currentStep and wa.flowId=wo.flowId and wa.type=2 and wo.infoType=1 and wa.auditManId=?) ids where ids.contentId=ci.contentId and ids.contentId=wo.contentId and wo.flowId=wsi.flowId and wo.currentStep=wsi.stepId"
            + " union "
            + "select ci.title, ci.creator, ci.addTime,  ci.modelId, ci.classId as c2, wsi.stepNodeName, wo.* from content_main_info ci, workflow_operation wo, workflow_step_info wsi, (select wo.contentId from workflow_operation wo,workflow_actor wa where wa.censorStep=wo.currentStep and wa.flowId=wo.flowId and wa.type=3 and wo.infoType=1 and wa.auditManId=?) ids where ids.contentId=ci.contentId and ids.contentId=wo.contentId and wo.flowId=wsi.flowId and wo.currentStep=wsi.stepId"
            + ") tmp";

        return ( Long ) pe.querySingleObject( sql, new Object[] { loginUserId, loginUserOrgId },
            Long.class );
    }

    public List<WorkflowOperationBean> queryAllInWorkflowUserDefineContent( String sqlOrCond, Long loginUserId,
        Long loginUserOrgId, Long start, Integer size )
    {
        String sql = "select * from (select ci.title, ci.creator, ci.addTime, ci.modelId, ci.classId as c1, wsi.stepNodeName, wo.* from content_main_info ci, workflow_operation wo, workflow_step_info wsi, (select wo.contentId from workflow_operation wo,workflow_actor wa where wa.censorStep=wo.currentStep and wa.flowId=wo.flowId and wa.type=1 and wo.infoType=1 and ("
            + sqlOrCond
            + ")) ids where ids.contentId=ci.contentId and ids.contentId=wo.contentId and wo.flowId=wsi.flowId and wo.currentStep=wsi.stepId"
            + " union "
            + "select ci.title, ci.creator, ci.addTime, ci.modelId, ci.classId, wsi.stepNodeName, wo.* from content_main_info ci, workflow_operation wo, workflow_step_info wsi, (select wo.contentId from workflow_operation wo,workflow_actor wa where wa.censorStep=wo.currentStep and wa.flowId=wo.flowId and wa.type=2 and wo.infoType=1 and wa.auditManId=?) ids where ids.contentId=ci.contentId and ids.contentId=wo.contentId and wo.flowId=wsi.flowId and wo.currentStep=wsi.stepId"
            + " union "
            + "select ci.title, ci.creator, ci.addTime, ci.modelId, ci.classId, wsi.stepNodeName, wo.* from content_main_info ci, workflow_operation wo, workflow_step_info wsi, (select wo.contentId from workflow_operation wo,workflow_actor wa where wa.censorStep=wo.currentStep and wa.flowId=wo.flowId and wa.type=3 and wo.infoType=1 and wa.auditManId=?) ids where ids.contentId=ci.contentId and ids.contentId=wo.contentId and wo.flowId=wsi.flowId and wo.currentStep=wsi.stepId)"
            +

            " tmp order by opId desc limit ?,?";

        return pe.query( sql, new Object[] { loginUserId, loginUserOrgId, start, size },
            new WorkflowOperationAndExpandInfoBeanTransform() );
    }

    public Integer queryContentCensorStatusById( Long contentId )
    {
        String sql = "select censorState from content_main_info where contentId=?";
        return ( Integer ) pe.querySingleObject( sql, new Object[] { contentId }, Integer.class );
    }

    public void updateContentMainInfoCensorStatus( Long contentId, Integer censorStatus )
    {
        String sql = "update content_main_info set censorState=? where contentId=?";
        pe.update( sql, new Object[] { censorStatus, contentId } );
    }

    public List queryInWorkflowUserDefinePersonalRejectContent( String userName )
    {
        String sql = "select ci.title, ci.creator, ci.addTime, ci.modelId, ci.classId, wo.* from workflow_operation wo left join content_main_info ci on wo.contentId=ci.contentId where ci.creator=? and wo.currentStep=0";
        return pe.query( sql, new Object[] { userName },
            new WorkflowOperationAndExpandInfoBeanTransform() );
    }

    public WorkflowOperationBean querySingleWorkflowContentProcessBean( Long contentId )
    {
        String sql = "select * from workflow_operation where contentId=?";

        return ( WorkflowOperationBean ) pe.querySingleRow( sql, new Object[] { contentId },
            new WorkflowOperationBeanTransform() );
    }

    public Map queryFirstCommendAndTopContentQueryFlagDateMode( Long targetClassId,
        Timestamp startDate, Timestamp endDate, Long modelId, Integer censorBy )
    {
        String sql = "select topFlag, orderIdFlag from content_main_info where topFlag=1 and commendFlag=1 and classId=? and modelId=? and appearStartDateTime>=? and appearEndDateTime<=? and censorState=? order by orderIdFlag asc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { targetClassId, modelId, startDate,
            endDate, censorBy } );
    }

    public Map queryFirstCommendContentQueryFlagDateMode( Long targetClassId, Timestamp startDate,
        Timestamp endDate, Long modelId, Integer censorBy )
    {
        String sql = "select topFlag, orderIdFlag from content_main_info where commendFlag=1 and classId=? and modelId=? and appearStartDateTime>=? and appearEndDateTime<=? and censorState=? order by orderIdFlag asc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { targetClassId, modelId, startDate,
            endDate, censorBy } );
    }

    public Map queryFirstCommendAndTopContentQueryFlag( Long targetClassId, Long modelId,
        Integer censorBy )
    {
        String sql = "select topFlag, orderIdFlag from content_main_info where topFlag=1 and commendFlag=1 and classId=? and modelId=? and censorState=? order by orderIdFlag asc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { targetClassId, modelId, censorBy } );
    }

    public Map queryFirstCommendAndTopContentQueryFlag( Long targetClassId, Long modelId )
    {
        String sql = "select topFlag, orderIdFlag from content_main_info where topFlag=1 and commendFlag=1 and classId=? and modelId=? order by orderIdFlag asc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { targetClassId, modelId } );
    }

    public Map queryFirstHotAndTopContentQueryFlag( Long targetClassId, Long modelId,
        Integer censorBy )
    {
        String sql = "select topFlag, orderIdFlag from content_main_info where topFlag=1 and hotFlag=1 and classId=? and modelId=? and censorState=? order by orderIdFlag asc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { targetClassId, modelId, censorBy } );
    }

    public Map queryFirstContentQueryFlagDateMode( Long targetClassId, Timestamp startDate,
        Timestamp endDate, Long modelId )
    {
        String sql = "select topFlag, orderIdFlag from content_main_info where classId=? and modelId=? and appearStartDateTime>=? and appearEndDateTime<=? order by orderIdFlag asc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { targetClassId, modelId, startDate,
            endDate } );
    }

    public Map queryFirstContentQueryFlagDateMode( String typeBy, Long targetClassId,
        Timestamp startDate, Timestamp endDate, Long modelId )
    {
        String sql = "select topFlag, orderIdFlag from content_main_info where typeFlag=? and classId=? and modelId=? and appearStartDateTime>=? and appearEndDateTime<=? order by orderIdFlag asc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { typeBy, targetClassId, modelId,
            startDate, endDate } );
    }

    public Map queryFirstContentQueryFlagDateMode( Long targetClassId, Timestamp startDate,
        Timestamp endDate, Long modelId, Integer censorBy )
    {
        String sql = "select topFlag, orderIdFlag from content_main_info where classId=? and modelId=? and appearStartDateTime>=? and appearEndDateTime<=? and censorState=? order by orderIdFlag asc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { targetClassId, modelId, startDate,
            endDate, censorBy } );
    }

    public Map queryFirstContentQueryFlagDateMode( Long targetClassId, Long modelId )
    {
        String sql = "select topFlag, orderIdFlag from content_main_info where classId=? and modelId=? order by orderIdFlag asc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { targetClassId, modelId } );
    }

    public Map queryFirstContentQueryFlagDateMode( String typeBy, Long targetClassId, Long modelId )
    {
        String sql = "select topFlag, orderIdFlag from content_main_info where typeFlag=? and classId=? and modelId=? order by orderIdFlag asc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { typeBy, targetClassId, modelId } );
    }

    public Map queryFirstContentQueryFlag( Long targetClassId, Long modelId, Integer censorBy )
    {
        String sql = "select topFlag, orderIdFlag from content_main_info where classId=? and modelId=? and censorState=? order by orderIdFlag asc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { targetClassId, modelId, censorBy } );
    }

    public Map queryFirstContentQueryFlag( String typeBy, Long targetClassId, Long modelId,
        Integer censorBy )
    {
        String sql = "select topFlag, orderIdFlag from content_main_info where typeFlag=? and classId=? and modelId=? and censorState=? order by orderIdFlag asc limit 1";

        return pe.querySingleResultMap( sql, new Object[] { typeBy, targetClassId, modelId,
            censorBy } );
    }

    public Long queryMinAddDateContentIdByDate( Long classId, Long modelId, Timestamp startDate,
        Timestamp endDate )
    {
        String sql = "select contentId from content_main_info where classId=? and modelId=? and addTime>=? and addTime<=? order by addTime asc limit 1";

        String key = "queryMinAddDateContentIdByDate:" + classId + "|" + modelId + startDate
            + endDate;

        Long minId = ( Long ) addTimeIdCache.getEntry( key );
        if( minId == null )
        {
            minId = ( Long ) pe.querySingleObject( sql, new Object[] { classId, modelId, startDate,
                endDate }, Long.class );

            addTimeIdCache.putEntry( key, minId );
        }
        return minId;
    }

    public Long queryMaxAddDateContentIdByDate( Long classId, Long modelId, Timestamp startDate,
        Timestamp endDate )
    {
        String sql = "select contentId from content_main_info where classId=? and modelId=? and addTime>=? and addTime<=? order by addTime desc limit 1";

        String key = "queryMaxAddDateContentIdByDate:" + classId + "|" + modelId + startDate
            + endDate;

        Long maxId = ( Long ) addTimeIdCache.getEntry( key );
        if( maxId == null )
        {
            maxId = ( Long ) pe.querySingleObject( sql, new Object[] { classId, modelId, startDate,
                endDate }, Long.class );

            addTimeIdCache.putEntry( key, maxId );
        }
        return maxId;
    }

    private Integer[] disposeTopInfo( Integer topCount, int pageSize )
    {
        Integer[] topPageInfo = new Integer[2];

        topPageInfo[0] = Integer.valueOf( topCount.intValue() / pageSize
            + ( topCount.intValue() % pageSize == 0 ? 0 : 1 ) );

        int lastPageCount = topCount.intValue() % pageSize;
        if( ( topPageInfo[0].intValue() != 0 ) && ( lastPageCount == 0 ) )
        {
            lastPageCount = pageSize;
        }
        topPageInfo[1] = Integer.valueOf( lastPageCount );

        return topPageInfo;
    }

    public List queryMediaSnapshotImageByContentId( Long contentId )
    {
        String sql = "select * from content_system_videoinfo_snapshot_img where contentId=? order by orderFlag asc";
        return pe.queryResultMap( sql, new Object[] { contentId } );
    }

    public void deleteMediaSnapshotImgByContentId( Long contentId )
    {
        String sql = "delete from content_system_videoinfo_snapshot_img where contentId=?";
        pe.update( sql, new Object[] { contentId } );
    }

    public List queryGroupPhotoInfoByContentId( Long contentId, Integer modelType,
        SiteGroupBean siteBean, boolean serverMode )
    {
        String sql = "select * from content_assistant_photo_group where contentId=? and modelType=? order by orderFlag asc";
        return pe.queryResultMap( sql, new Object[] { contentId, modelType },
            new PhotoGroupValueCallBack( siteBean, serverMode ) );
    }

    public List queryGroupPhotoInfoByContentId( Long contentId, String group, Integer modelType,
        SiteGroupBean siteBean, boolean serverMode )
    {
        String sql = "select * from content_assistant_photo_group where contentId=? and modelType=? and groupSign=? order by orderFlag asc";
        return pe.queryResultMap( sql, new Object[] { contentId, modelType, group },
            new PhotoGroupValueCallBack( siteBean, serverMode ) );
    }

    public List queryGroupPhotoInfoByContentIdModelDataMode( Long contentId, Integer modelType )
    {
        String sql = "select * from content_assistant_photo_group where contentId=? and modelType=? order by orderFlag asc";
        return pe.queryResultMap( sql, new Object[] { contentId, modelType } );
    }

    public List queryGroupPhotoInfoByContentIdAndGroupSignModelDataMode( Long contentId,
        String groupSign, Integer modelType )
    {
        String sql = "select * from content_assistant_photo_group where contentId=? and groupSign=? and modelType=? order by orderFlag asc";
        return pe.queryResultMap( sql, new Object[] { contentId, groupSign, modelType } );
    }

    public UpdateState saveSingleGroupPhoto( PhotoGroupInfo pgi )
    {
        return pe.save( pgi );
    }

    public void deletePhotoGroupInfo( Long contentId, Integer modelType )
    {
        String sql = "delete from content_assistant_photo_group where contentId=? and modelType=?";
        pe.update( sql, new Object[] { contentId, modelType } );
    }

    public void saveArticleContentPageInfo( ContentAssistantPageInfo capInfoBean )
    {
        pe.save( capInfoBean );
    }

    public List queryContentAssistantPageInfoBeanNotIncludeTextByContentId( Long contentId,
        Map info, ContentClassBean classBean, SiteGroupBean site )
    {
        String sql = "select contentId, pos, pageTitle, pageContent, pageStaticUrl, startPos, endPos from content_assistant_page_info cp inner join (select contentId as cid, pos as pid from content_assistant_page_info where contentId=? order by pos asc) ids on cp.contentId = ids.cid and cp.pos = ids.pid";

        return pe.query( sql, new Object[] { contentId }, new ContentAssistantPageInfoTransform(
            info, classBean, site ) );
    }

    public List queryContentAssistantPageInfoBeanByContentIdDataMode( Long contentId, Map info,
        ContentClassBean classBean )
    {
        String sql = "select * from content_assistant_page_info where contentId=?";

        return pe.queryResultMap( sql, new Object[] { contentId } );
    }

    public Map queryContentAssistantPageInfoBeanByContentIdDataMode( Long contentId, Integer pos )
    {
        String sql = "select * from content_assistant_page_info where contentId=? and pos=?";

        return pe.querySingleResultMap( sql, new Object[] { contentId, pos } );
    }

    public void deleteContentAssistantPageInfoByContentId( Long contentId )
    {
        String sql = "delete from content_assistant_page_info where contentId=?";

        pe.update( sql, new Object[] { contentId } );
    }

    public List queryNeedPublishContentByClassIDAndModelIdAndFlag( Long classId,
        DataModelBean modelBean, ModelPersistenceMySqlCodeBean sqlBean, Double orderIdFlag,
        Integer limitCount )
    {
        String tmpContentCol = "tmp.contentId as qid, ";
        if( StringUtil.isStringNull( tmpContentCol ) )
        {
            tmpContentCol = "tmp.contentId as qid";
        }
        String sql = "select res.*, cm.*, cc.className from (select "
            + tmpContentCol
            + sqlBean.getSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as tmp inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and censorState=1 and orderIdFlag<? order by orderIdFlag desc limit ?) ids on tmp.contentId=ids.queryId ) res, content_main_info cm, contentclass cc where cm.contentId=res.qId and cm.classId=cc.classId order by orderIdFlag desc";

        return pe.queryResultMap( sql, new Object[] { classId, modelBean.getDataModelId(),
            orderIdFlag, limitCount }, new ContentValueResultCallBack() );
    }

    public List queryNeedPublishContentByClassIDAndModelIdAndFlag( Long classId,
        DataModelBean modelBean, ModelPersistenceMySqlCodeBean sqlBean, Double orderIdFlag,
        Timestamp start, Timestamp end, Integer limitCount )
    {
        String tmpContentCol = "tmp.contentId as qId, ";
        if( StringUtil.isStringNull( tmpContentCol ) )
        {
            tmpContentCol = "tmp.contentId as qId";
        }
        String sql = "select res.*, cm.*, cc.className from (select "
            + tmpContentCol
            + sqlBean.getSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as tmp inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and censorState=1 and addTime>? and addTime<? and orderIdFlag<? order by orderIdFlag desc limit ?) ids on tmp.contentId=ids.queryId ) res, content_main_info cm, contentclass cc where cm.contentId=res.qId and cm.classId=cc.classId order by orderIdFlag desc";

        return pe.queryResultMap( sql, new Object[] { classId, modelBean.getDataModelId(), start,
            end, orderIdFlag, limitCount }, new ContentValueResultCallBack() );
    }

    public List queryNeedPublishContentAndPageContentByClassIDAndModelIdAndFlag( Long classId,
        DataModelBean modelBean, ModelPersistenceMySqlCodeBean sqlBean, Double orderIdFlag,
        Integer limitCount )
    {
        String tmpContentCol = "tmp.contentId, ";
        if( StringUtil.isStringNull( tmpContentCol ) )
        {
            tmpContentCol = "tmp.contentId ";
        }
        String sql = "select res.*, cm.*, cap.pos, cap.pageTitle, cap.pageContent, cap.pageStaticUrl, cc.className from ((select "
            + tmpContentCol
            + sqlBean.getSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as tmp inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and censorState=1 and orderIdFlag<? order by orderIdFlag desc limit ?) ids on tmp.contentId=ids.queryId ) res, content_main_info cm, contentclass cc)"
            + "left join content_assistant_page_info cap on res.contentId=cap.contentId and cap.pos=1 where cm.contentId=res.contentId and cm.classId=cc.classId order by orderIdFlag desc";

        return pe.queryResultMap( sql, new Object[] { classId, modelBean.getDataModelId(),
            orderIdFlag, limitCount }, new ContentValueResultCallBack() );
    }

    public List queryNeedPublishContentAndPageContentByClassIDAndModelIdAndFlag( Long classId,
        DataModelBean modelBean, ModelPersistenceMySqlCodeBean sqlBean, Double orderIdFlag,
        Timestamp start, Timestamp end, Integer limitCount )
    {
        String tmpContentCol = "tmp.contentId, ";
        if( StringUtil.isStringNull( tmpContentCol ) )
        {
            tmpContentCol = "tmp.contentId ";
        }
        String sql = "select res.*, cm.*, cap.pos, cap.pageTitle, cap.pageContent, cap.pageStaticUrl, cc.className from ((select "
            + tmpContentCol
            + sqlBean.getSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as tmp inner join"
            + " (select contentId as queryId from content_main_info where classId=? and modelId=? and censorState=1 and addTime>? and addTime<? and orderIdFlag<? order by orderIdFlag desc limit ?) ids on tmp.contentId=ids.queryId ) res, content_main_info cm, contentclass cc)"
            + "left join content_assistant_page_info cap on res.contentId=cap.contentId and cap.pos=1 where cm.contentId=res.contentId and cm.classId=cc.classId order by orderIdFlag desc";

        return pe.queryResultMap( sql, new Object[] { classId, modelBean.getDataModelId(), start,
            end, orderIdFlag, limitCount }, new ContentValueResultCallBack() );
    }

    public List queryWaitPublishContentIdByCurrentDate( Long siteId, Timestamp currTime )
    {
        String sql = "select contentId from content_main_info where siteId=? and censorState=2 and appearStartDateTime<=?";
        return pe.querySingleCloumn( sql, new Object[] { siteId, currTime }, Long.class );
    }

    public List queryWaitPublishArticlePageContentIdByCurrentDate( Long classId,
        DataModelBean modelBean, ModelPersistenceMySqlCodeBean sqlBean, Double orderIdFlag,
        Integer limitCount )
    {
        String tmpContentCol = "tmp.contentId, ";
        if( StringUtil.isStringNull( tmpContentCol ) )
        {
            tmpContentCol = "tmp.contentId ";
        }
        String sql = "select res.*, cm.*, cap.pos, cap.pageTitle, cap.pageContent, cap.pageStaticUrl, cc.className from ((select "
            + tmpContentCol
            + sqlBean.getSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as tmp inner join"
            + " (select contentId as queryId from content_wait_pub_temp where classId=? and orderIdFlag<? order by orderIdFlag desc limit ?) ids on tmp.contentId=ids.queryId ) res, content_main_info cm, contentclass cc)"
            + "left join content_assistant_page_info cap on res.contentId=cap.contentId and cap.pos=1 where cm.contentId=res.contentId and cm.classId=cc.classId order by orderIdFlag desc";

        return pe.queryResultMap( sql, new Object[] { classId, orderIdFlag, limitCount },
            new ContentValueResultCallBack() );
    }

    public List queryWaitPublishContentIdByCurrentDate( Long classId, DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean sqlBean, Double orderIdFlag, Integer limitCount )
    {
        String tmpContentCol = "tmp.contentId, ";
        if( StringUtil.isStringNull( tmpContentCol ) )
        {
            tmpContentCol = "tmp.contentId ";
        }
        String sql = "select res.*, cm.*, cc.className from (select "
            + tmpContentCol
            + sqlBean.getSelectColumn()
            + " from "
            + modelBean.getRelateTableName()
            + " as tmp inner join"
            + " (select contentId as queryId from content_main_info where classId=? and orderIdFlag<? order by orderIdFlag desc limit ?) ids on tmp.contentId=ids.queryId ) res, content_main_info cm, contentclass cc where cm.contentId=res.contentId and cm.classId=cc.classId order by orderIdFlag desc";

        return pe.queryResultMap( sql, new Object[] { classId, orderIdFlag, limitCount },
            new ContentValueResultCallBack() );
    }

    public List querySiteWaitPublishContentMainInfo( Long siteId, Timestamp currTime )
    {
        String sql = "select mc.* from content_main_info mc inner join (select ci.contentId from content_main_info ci where ci.siteId=? and ci.censorState=2 and ci.appearStartDateTime<=?)ids on mc.contentId = ids.contentId";

        return pe.queryResultMap( sql, new Object[] { siteId, currTime } );
    }

    public List querySiteWithdrawContentMainInfo( Long siteId, Timestamp currTime )
    {
        String sql = "select mc.* from content_main_info mc inner join (select ci.* from content_main_info ci where ci.siteId=? and ci.censorState=1 and ci.appearEndDateTime<=?)ids on mc.contentId = ids.contentId";

        return pe.queryResultMap( sql, new Object[] { siteId, currTime } );
    }

    public void saveWaitPublishIdTemp( Long contentId, Double orderIdFlag, Long classId )
    {
        String sql = "insert into content_wait_pub_temp (contentId, orderIdFlag, classId) values (?, ?, ?)";

        pe.update( sql, new Object[] { contentId, orderIdFlag, classId } );
    }

    public void deleteWaitPublishIdTemp()
    {
        String sql = "delete from content_wait_pub_temp";

        pe.update( sql );
    }

    public Long queryNeedPublishContentCountByClassIDAndModelIdAndFlag( Long classId,
        DataModelBean modelBean, Double orderIdFlag )
    {
        String sql = "select count(contentId) as pubCount from content_main_info where classId=? and modelId=? and censorState=1 and orderIdFlag<?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { classId,
            modelBean.getDataModelId(), orderIdFlag }, Long.class );
    }

    public Long queryNeedPublishContentCountByClassIDAndModelIdAndFlagAndAddDate( Long classId,
        DataModelBean modelBean, Double orderIdFlag, Timestamp startTime, Timestamp endTime )
    {
        String sql = "select count(contentId) as pubCount from content_main_info where classId=? and modelId=? and censorState=1 and addTime>? and addTime<? and orderIdFlag<?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { classId,
            modelBean.getDataModelId(), startTime, endTime, orderIdFlag }, Long.class );
    }

    public List queryAllContentPageByIds( String query, DataModelBean modelBean,
        ModelPersistenceMySqlCodeBean sqlBean )
    {
        String tmpContentCol = sqlBean.getSelectColumn();
        if( StringUtil.isStringNotNull( tmpContentCol ) )
        {
            tmpContentCol = ", " + tmpContentCol;
        }
        String sql = "select mi.*, cpi.pos, cpi.pageTitle, cpi.pageContent, cpi.pageStaticUrl "
            + tmpContentCol
            + " from content_assistant_page_info cpi left join content_main_info mi left join "
            + modelBean.getRelateTableName()
            + " tmp on mi.contentId=tmp.contentId on mi.contentId=cpi.contentId where mi.contentId in ("
            + query + ")";
        return pe.queryResultMap( sql, null, new ContentValueResultCallBack() );
    }

    public List queryAllCommendContentByCommendType( Long commendTypeId )
    {
        String sql = "select ci.* from content_main_info ci inner join (select contentId as qid from content_commend_push_info cpi where commendTypeId=?) ids on ids.qid=ci.contentId order by ci.orderIdFlag desc";

        return pe.queryResultMap( sql, new Object[] { commendTypeId } );
    }

    public List queryAllCommendContentByCommendTypeByFlag( String commendFlag, String siteFlag,
        boolean needRev )
    {
        String sql = "select ci.* from content_commend_push_info ci inner join (select infoId as qid from content_commend_push_info cpi where cpi.commendFlag=? and cpi.siteFlag=? order by cpi.rowFlag asc, cpi.rowOrder desc) ids on ids.qid=ci.infoId order by ci.rowFlag asc, ci.rowOrder desc";

        Map prevBeanInfoMap = new TreeMap();

        pe.query( sql, new Object[] { commendFlag, siteFlag },
            new ContentCommendPushInfoBeanTransform( prevBeanInfoMap ) );

        List val = new ArrayList( prevBeanInfoMap.values() );
        if( needRev )
        {
            Collections.reverse( val );
        }
        return val;
    }

    public List queryAllCommendContentByCommendTypeByFlag( String commendFlag, String siteFlag,
        Integer size, boolean needRev )
    {
        String sql = "select ci.* from content_commend_push_info ci inner join (select distinct rowFlag as qid from content_commend_push_info cpi where cpi.commendFlag=? and cpi.siteFlag=? order by cpi.rowFlag asc, cpi.rowOrder desc limit ?) ids on ids.qid=ci.rowFlag where ci.commendFlag=? and ci.siteFlag=? order by ci.rowFlag asc, ci.rowOrder desc";

        Map prevBeanInfoMap = new TreeMap();

        pe.query( sql, new Object[] { commendFlag, siteFlag, size, commendFlag, siteFlag },
            new ContentCommendPushInfoBeanTransform( prevBeanInfoMap ) );

        List val = new ArrayList( prevBeanInfoMap.values() );
        if( needRev )
        {
            Collections.reverse( val );
        }
        return val;
    }

    public List queryAllCommendContentByCommendTypeByTypeId( Long typeId, String siteFlag,
        Long startPos, Integer size, boolean needRev )
    {
        String sql = "select ci.* from content_commend_push_info ci inner join (select distinct rowFlag as qid from content_commend_push_info cpi where cpi.commendTypeId=? and cpi.siteFlag=? order by cpi.rowFlag asc, cpi.rowOrder desc limit ?,?) ids on ids.qid=ci.rowFlag where ci.commendTypeId=? and ci.siteFlag=? order by ci.rowFlag asc, ci.rowOrder desc";

        Map prevBeanInfoMap = new TreeMap();

        pe.query( sql, new Object[] { typeId, siteFlag, startPos, size, typeId, siteFlag },
            new ContentCommendPushInfoBeanTransform( prevBeanInfoMap ) );

        List val = new ArrayList( prevBeanInfoMap.values() );
        if( needRev )
        {
            Collections.reverse( val );
        }
        return val;
    }

    public List queryAllCommendContentByTypeId( Long typeId, String siteFlag )
    {
        String sql = "select cpi.* from content_commend_push_info cpi where cpi.commendTypeId=? and cpi.siteFlag=?";

        Map prevBeanInfoMap = new TreeMap();

        pe.query( sql, new Object[] { typeId, siteFlag }, new ContentCommendPushInfoBeanTransform(
            prevBeanInfoMap ) );

        List val = new ArrayList( prevBeanInfoMap.values() );

        return val;
    }

    public Integer queryAllCommendContentByCommendCountByFlag( String commendFlag )
    {
        String sql = "select count(distinct rowFlag) from content_commend_push_info cpi where cpi.commendFlag=?";

        String key = "queryAllCommendContentByCommendCountByFlag:" + commendFlag;

        Integer count = ( Integer ) countCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { commendFlag },
                Integer.class );

            countCache.putEntry( key, count );
        }
        return count;
    }

    public Integer queryAllCommendContentByCommendCountByTypeId( Long typeId )
    {
        String sql = "select count(distinct rowFlag) from content_commend_push_info cpi where cpi.commendTypeId=?";

        String key = "queryAllCommendContentByCommendCountByTypeId:" + typeId;

        Integer count = ( Integer ) countCache.getEntry( key );
        if( count == null )
        {
            count = ( Integer ) pe.querySingleObject( sql, new Object[] { typeId }, Integer.class );

            countCache.putEntry( key, count );
        }
        return count;
    }

    public ContentCommendPushInfoBean querySingleCommendPushInfoByInfoId( Long infoId )
    {
        String sql = "select * from content_commend_push_info where infoId=?";

        return ( ContentCommendPushInfoBean ) pe.querySingleRow( sql, new Object[] { infoId },
            new ContentCommendPushInfoBeanTransform( false ) );
    }

    public void deleteCommendRowInfoByCommFlag( String commFlag, List needDeleteRowFlow,
        String siteFlag )
    {
        StringBuffer buf = new StringBuffer( "(" );

        long id = -1L;
        boolean havaParam = false;
        for ( int i = 0; i < needDeleteRowFlow.size(); i++ )
        {
            id = ( ( Long ) needDeleteRowFlow.get( i ) ).longValue();
            if( id >= 0L )
            {
                havaParam = true;

                buf.append( ( Long ) needDeleteRowFlow.get( i ) );
                if( i + 1 != needDeleteRowFlow.size() )
                {
                    buf.append( "," );
                }
            }
        }
        buf.append( ")" );

        String sql = null;
        if( havaParam )
        {
            sql = "delete from content_commend_push_info where rowFlag in " + buf.toString()
                + " and commendFlag=? and siteFlag=?";
        }
        else
        {
            sql = "delete from content_commend_push_info where commendFlag=? and siteFlag=?";
        }
        pe.update( sql, new Object[] { commFlag, siteFlag } );
    }

    public List queryCommendRowInfoByCommFlag( String commFlag, String siteFlag )
    {
        String sql = "select * from content_commend_push_info where commendFlag=? and siteFlag=?";

        return pe.query( sql, new Object[] { commFlag, siteFlag },
            new ContentCommendPushInfoBeanTransform( false ) );
    }

    public List queryCommendRowInfoByCommFlagAndRowFlag( List rowFlagArrayList, String commFlag,
        String siteFlag )
    {
        StringBuffer buf = new StringBuffer( "(" );

        long id = -1L;
        boolean havaParam = false;
        for ( int i = 0; i < rowFlagArrayList.size(); i++ )
        {
            id = StringUtil.getLongValue( ( String ) rowFlagArrayList.get( i ), -1L );
            if( id >= 0L )
            {
                havaParam = true;

                buf.append( ( String ) rowFlagArrayList.get( i ) );
                if( i + 1 != rowFlagArrayList.size() )
                {
                    buf.append( "," );
                }
            }
        }
        buf.append( ")" );
        if( !havaParam )
        {
            return null;
        }
        String sql = "select * from content_commend_push_info where commendFlag=? and siteFlag=? and rowFlag in "
            + buf.toString();

        return pe.query( sql, new Object[] { commFlag, siteFlag },
            new ContentCommendPushInfoBeanTransform( false ) );
    }

    public void updateCommendRowInfoContentStatusByCommFlag( String commFlag,
        List needDeleteRowFlow, Integer status, String siteFlag )
    {
        StringBuffer buf = new StringBuffer( "(" );

        long id = -1L;
        boolean havaParam = false;
        for ( int i = 0; i < needDeleteRowFlow.size(); i++ )
        {
            id = ( ( Long ) needDeleteRowFlow.get( i ) ).longValue();
            if( id >= 0L )
            {
                havaParam = true;

                buf.append( ( Long ) needDeleteRowFlow.get( i ) );
                if( i + 1 != needDeleteRowFlow.size() )
                {
                    buf.append( "," );
                }
            }
        }
        buf.append( ")" );
        if( !havaParam )
        {
            return;
        }
        String sqlId = "select distinct contentId from content_commend_push_info where rowFlag in "
            + buf.toString()
            + " and commendFlag=? and contentId not in (select distinct contentId from content_commend_push_info where siteFlag =? and commendFlag !=?)";

        List ids = pe.querySingleCloumn( sqlId, new Object[] { commFlag, siteFlag, commFlag },
            Long.class );

        buf = new StringBuffer( "(" );

        id = -1L;

        havaParam = false;
        for ( int i = 0; i < ids.size(); i++ )
        {
            id = ( ( Long ) ids.get( i ) ).longValue();
            if( id >= 0L )
            {
                havaParam = true;

                buf.append( ( Long ) ids.get( i ) );
                if( i + 1 != ids.size() )
                {
                    buf.append( "," );
                }
            }
        }
        buf.append( ")" );
        if( !havaParam )
        {
            return;
        }
        String sql = "update content_main_info set commendFlag=? where contentId in "
            + buf.toString();

        pe.update( sql, new Object[] { status } );
    }

    public UpdateState saveCommendContent( ContentCommendPushInfo pushInfo )
    {
        return pe.save( pushInfo );
    }

    public Integer queryCommendContentMaxRowFlagByTypeId( Long commendTypeId )
    {
        String sql = " select max(rowFlag) from content_commend_push_info where commendTypeId=?";

        return ( Integer ) pe
            .querySingleObject( sql, new Object[] { commendTypeId }, Integer.class );
    }

    public Integer queryCommendContentMaxRowOrderByRowFlag( Long rowFlag, String commFlag )
    {
        String sql = "select max(rowOrder) from content_commend_push_info where rowFlag=? and commendFlag=?";

        return ( Integer ) pe.querySingleObject( sql, new Object[] { rowFlag, commFlag },
            Integer.class );
    }

    public void saveCommendPushTemp( Long contentId, Long commendTypeId, String commFlag )
    {
        String sql = "insert into content_commend_push_temp (contentId, commTypeId, commFlag) values (?,?,?) ";

        pe.update( sql, new Object[] { contentId, commendTypeId, commFlag } );
    }

    public Long queryCommendPushTempCount( Long contentId, Long commendTypeId )
    {
        String sql = "select count(*) from content_commend_push_temp where contentId=? and commTypeId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { contentId, commendTypeId },
            Long.class );
    }

    public Long queryCommendPushTempCount( Long commendTypeId )
    {
        String sql = "select count(*) from content_commend_push_temp cpt where cpt.commTypeId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { commendTypeId }, Long.class );
    }

    public List queryCommendPushTemp( Long commendTypeId )
    {
        String sql = "select cpt.*, ci.title, ci.classId from content_commend_push_temp cpt left join content_main_info ci on cpt.contentId=ci.contentId where cpt.commTypeId=?";

        return pe.queryResultMap( sql, new Object[] { commendTypeId } );
    }

    public void deleteCommendPushTemp( Long contentId, Long commendTypeId )
    {
        String sql = "delete from content_commend_push_temp where contentId=? and commTypeId=?";

        pe.update( sql, new Object[] { contentId, commendTypeId } );
    }

    public Integer queryCommendContentMaxRowFlagByCommFlag( String commFlag )
    {
        String sql = "select max(rowFlag) from content_commend_push_info where commendFlag=?";

        Integer max = ( Integer ) pe.querySingleObject( sql, new Object[] { commFlag },
            Integer.class );

        return max == null ? Integer.valueOf( 1 ) : max;
    }

    public Integer queryCommendContentRowCountByCommFlag( String commFlag )
    {
        String sql = "select count(distinct rowFlag) from content_commend_push_info where commendFlag=?";

        return ( Integer ) pe.querySingleObject( sql, new Object[] { commFlag }, Integer.class );
    }

    public Integer queryCensorStateByContentId( Long contentId )
    {
        String sql = "select censorState from content_main_info where contentId=?";
        return ( Integer ) pe.querySingleObject( sql, new Object[] { contentId }, Integer.class );
    }

    public void updateContentCensorState( Long contentId, Integer censorStatus )
    {
        String sql = "update content_main_info set censorState=? where contentId=?";

        pe.update( sql, new Object[] { censorStatus, contentId } );
    }

    public void updateContentPageModeInfo( Long contentId, Integer flag )
    {
        String sql = "update content_main_info set isPageContent=? where contentId=?";

        pe.update( sql, new Object[] { flag, contentId } );
    }

    public Map queryContentPublishInfo( Long contentId )
    {
        String sql = "select classId, censorState, especialTemplateUrl from content_main_info where contentId=?";
        return pe.querySingleResultMap( sql, new Object[] { contentId } );
    }

    public boolean checkContentTitle( String title, Long classId )
    {
        String sql = "select count(contentId) from content_main_info where title=? and classId=?";

        Long idCount = ( Long ) pe.querySingleObject( sql, new Object[] { title, classId },
            Long.class );

        return idCount.longValue() > 0L;
    }

    public void saveContentAssistantCopyInfo( Long contentId, Long copyClassId )
    {
        String sql = "insert into content_assistant_copy_info (contentId, refClassId) values (?, ?)";
        pe.update( sql, new Object[] { contentId, copyClassId } );
    }

    public void deleteContentAssistantCopyInfoByContentId( Long contentId )
    {
        String sql = "delete from content_assistant_copy_info where contentId=?";
        pe.update( sql, new Object[] { contentId } );
    }

    public void deleteContentAssistantCopyInfoByContentIdAndClassId( Long contentId, Long classId )
    {
        String sql = "delete from content_assistant_copy_info where contentId=? and refClassId=?";
        pe.update( sql, new Object[] { contentId, classId } );
    }

    public List queryContentAssistantCopyInfoByContentId( Long contentId )
    {
        String sql = "select * from content_assistant_copy_info where contentId=?";
        return pe.queryResultMap( sql, new Object[] { contentId } );
    }

    public List queryContentAssistantCopyResClassIdByContentId( Long contentId )
    {
        String sql = "select refClassId from content_assistant_copy_info where contentId=?";
        return pe.querySingleCloumn( sql, new Object[] { contentId }, Long.class );
    }

    public void updateContentMoveClassInfo( Long contentId, Long classId, Integer produceType,
        Integer allowCommend )
    {
        String sql = "update content_main_info set classId=?, produceType=?, allowCommend=?, typeFlag='', staticPageUrl='' where contentId=?";
        pe.update( sql, new Object[] { classId, produceType, allowCommend, contentId } );
    }

    public void deleteCommendTypeContentByTypeId( String commendFlag )
    {
        String sql = "delete from content_commend_push_info where commendFlag=?";

        pe.update( sql, new Object[] { commendFlag } );
    }

    public void updateCommendTypeContentCommFlagByTypeFlag( String commFlag, String oldCommFlag )
    {
        String sql = "update content_commend_push_info set commendFlag=? where commendFlag=?";
        pe.update( sql, new Object[] { commFlag, oldCommFlag } );
    }

    public void updateCommendPushInfoByInfoId( ContentCommendPushInfo commInfo )
    {
        String sql = "update content_commend_push_info set contentId=?, title=?, url=?, img=?, summary=?, modelId=?, classId=? where infoId=?";
        pe.update( sql, commInfo );
    }

    public void updateCommendPushContentOrderInfoByInfoId( Long rowFlag, Integer rowOrder,
        Long infoId )
    {
        String sql = "update content_commend_push_info set rowFlag=?, rowOrder=? where infoId=?";
        pe.update( sql, new Object[] { rowFlag, rowOrder, infoId } );
    }

    public void updateCommendPushContentRowFlagByInfoId( Long rowFlag, Long infoId )
    {
        String sql = "update content_commend_push_info set rowFlag=? where infoId=?";
        pe.update( sql, new Object[] { rowFlag, infoId } );
    }

    public void deleteCommendInfoByInfoId( Long infoId )
    {
        String sql = "delete from content_commend_push_info where infoId=?";
        pe.update( sql, new Object[] { infoId } );
    }

    public List queryCommendPushContentRowInfoByRowFlag( Long rowFlag, String commFlag,
        String siteFlag )
    {
        String sql = "select * from content_commend_push_info cpi where cpi.rowFlag=? and cpi.commendFlag=? and cpi.siteFlag=? order by cpi.rowOrder desc";

        return pe.query( sql, new Object[] { rowFlag, commFlag, siteFlag },
            new ContentCommendPushInfoBeanTransform( true ) );
    }

    public ContentCommendPushInfoBean querySingleCommendPushInfoByOrderInfo( String commendFlag,
        Long rowFlag, Integer rowOrder )
    {
        String sql = "select * from content_commend_push_info where commendFlag=? and rowFlag=? and rowOrder=?";

        return ( ContentCommendPushInfoBean ) pe.querySingleRow( sql, new Object[] { commendFlag,
            rowFlag, rowOrder }, new ContentCommendPushInfoBeanTransform( false ) );
    }

    public void updateCommendPushContentRowOrder( Long infoId, Integer rowOrder )
    {
        String sql = "update content_commend_push_info set rowOrder=? where infoId=?";

        pe.update( sql, new Object[] { rowOrder, infoId } );
    }

    public List queryCommendPushContentRowInfoByRowFlagAndExcludeId( String commFlag, Long rowFlag,
        List excludeInfoId )
    {
        StringBuffer buf = new StringBuffer( "(" );

        long id = -1L;
        boolean havaParam = false;
        for ( int i = 0; i < excludeInfoId.size(); i++ )
        {
            id = StringUtil.getLongValue( ( String ) excludeInfoId.get( i ), -1L );
            if( id >= 0L )
            {
                havaParam = true;

                buf.append( ( String ) excludeInfoId.get( i ) );
                if( i + 1 != excludeInfoId.size() )
                {
                    buf.append( "," );
                }
            }
        }
        buf.append( ")" );
        if( !havaParam )
        {
            return null;
        }
        String sql = "select * from content_commend_push_info where rowFlag=? and commendFlag=? and infoId not in "
            + buf.toString() + " order by rowOrder asc";

        return pe.query( sql, new Object[] { rowFlag, commFlag },
            new ContentCommendPushInfoBeanTransform( true ) );
    }

    public void updateCommendPushContentRowOrder( String commFlag, Long rowFlag,
        Integer startOrder, Long notUpdateInfoId )
    {
        String sql = "update content_commend_push_info set rowOrder=rowOrder+1 where commendFlag=? and rowFlag=? and rowOrder>=? and infoId!=?";

        pe.update( sql, new Object[] { commFlag, rowFlag, startOrder, notUpdateInfoId } );
    }

    public Long queryPublishOrderId()
    {
        String sql = "select pubIdTrace from content_assistant_publish_id";
        return ( Long ) pe.querySingleObject( sql );
    }

    public UpdateState updateAndAddValPublishOrderId()
    {
        String sql = "update content_assistant_publish_id set pubIdTrace=pubIdTrace+1";
        return pe.update( sql );
    }

    public void updateCommendFlagByContentId( Long contentId, Integer status )
    {
        String sql = "update content_main_info set commendFlag=? where contentId=?";
        pe.update( sql, new Object[] { status, contentId } );
    }

    public Map querySingleTrashContentByContentId( Long contentId )
    {
        String sql = "select classId, censorState, addTime, modelId, siteId from content_trash_main_info where contentId=?";
        return pe.querySingleResultMap( sql, new Object[] { contentId } );
    }

    public Integer queryTrashContentCountByClassId( Long classId, Long modelId )
    {
        String sql = "select count(*) from content_trash_main_info where classId=? and modelId=?";
        return ( Integer ) pe.querySingleObject( sql, new Object[] { classId, modelId },
            Integer.class );
    }

    public List queryTrashContentByClassId( Long classId, Long modelId, Long startPos, Integer size )
    {
        String sql = "select * from content_trash_main_info where classId=? and modelId=? order by contentId desc limit ?,?";
        return pe.queryResultMap( sql, new Object[] { classId, modelId, startPos, size } );
    }

    public List queryTrashContentByTitleKey( String key, Long classId )
    {
        String sql = "select * from content_trash_main_info where classId=? and title like '%"
            + key + "%' order by contentId desc";

        return pe.queryResultMap( sql, new Object[] { classId } );
    }

    public List queryAllTrashContentByclassId( Long classId )
    {
        String sql = "select contentId from content_trash_main_info where classId=?";
        return pe.querySingleCloumn( sql, new Object[] { classId }, Long.class );
    }

    public void saveSiteShareContent( Map paramMap )
    {
        String sql = "insert into site_share_content (contentId, fromSiteId, toSiteId, modelId, fromClassId, isPub) values (?,?,?,?,?,?)";

        pe.update( sql, paramMap );
    }

    public void saveSiteShareContentTrace( Long siteId, Long contentId )
    {
        String sql = "insert into site_share_content_trace (contentId, siteId) values (?,?)";

        pe.update( sql, new Object[] { contentId, siteId } );
    }

    public Long queryShareContentCountBySite( Long siteId, Long contentId )
    {
        String sql = "select count(*) from site_share_content where contentId=? and toSiteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { contentId, siteId } );
    }

    public Long queryShareTraceCountBySite( Long siteId, Long contentId )
    {
        String sql = "select count(*) from site_share_content_trace where contentId=? and siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { contentId, siteId } );
    }

    public List queryShareContentSiteName( Long contentId )
    {
        String sql = "select (select siteName from site_group where siteId=sst.siteId) as siteName from site_share_content_trace sst where sst.contentId=?";

        return pe.querySingleCloumn( sql, new Object[] { contentId }, String.class );
    }

    public void deleteShareContentInfo( Long siteId, Long contentId )
    {
        String sql = "delete from site_share_content where contentId=? and toSiteId=?";

        pe.update( sql, new Object[] { contentId, siteId } );
    }

    public void deleteShareContentTrace( Long siteId, Long contentId )
    {
        String sql = "delete from site_share_content_trace where contentId=? and siteId=?";

        pe.update( sql, new Object[] { contentId, siteId } );
    }

    public void deleteShareContentInfo( Long contentId )
    {
        String sql = "delete from site_share_content where contentId=?";

        pe.update( sql, new Object[] { contentId } );
    }

    public void deleteShareContentTrace( Long contentId )
    {
        String sql = "delete from site_share_content_trace where contentId=?";

        pe.update( sql, new Object[] { contentId } );
    }

    public Long queryDraftContentCountBySiteId( Long siteId, Long censorState, String creator )
    {
        String sql = "select count(*) from content_main_info where siteId=? and censorState=? and linkCid<0 and classId>0 and creator=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, censorState, creator },
            Long.class );
    }

    public Long queryDraftContentCountBySiteIdAndAuthor( Long siteId, Long censorState,
        String author, String creator )
    {
        String sql = "select count(*) from content_main_info where siteId=? and censorState=? and author=? and linkCid<0 and classId>0 and creator=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, censorState, author,
            creator }, Long.class );
    }

    public Long queryDraftContentCountBySiteId( Long siteId, Long classId, Long censorState,
        String creator )
    {
        String sql = "select count(*) from content_main_info where siteId=? and classId=? and censorState=? and linkCid<0 and classId>0 and creator=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, classId, censorState,
            creator }, Long.class );
    }

    public Long queryDraftContentCountBySiteIdAndAuthor( Long siteId, Long classId,
        Long censorState, String author, String creator )
    {
        String sql = "select count(*) from content_main_info where siteId=? and classId=? and censorState=? and author=? and linkCid<0 and classId>0 and creator=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, classId, censorState,
            author, creator }, Long.class );
    }

    public List queryDraftContentInfoBySiteId( Long siteId, String creator, Long start, Integer size )
    {
        String sql = "select * from content_main_info where siteId=? and censorState=-1 and creator=? order by contentId desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { siteId, creator, start, size },
            new ContentValueResultCallBack() );
    }

    public List queryDraftContentInfoBySiteId( Long siteId, Long censorState, String creator,
        Long start, Integer size )
    {
        String sql = "select * from content_main_info where siteId=? and censorState=? and linkCid<0 and classId>0 and creator=? order by contentId desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { siteId, censorState, creator, start, size },
            new ContentValueResultCallBack() );
    }

    public List queryDraftContentInfoBySiteIdAndAuthor( Long siteId, Long censorState,
        String author, String creator, Long start, Integer size )
    {
        String sql = "select * from content_main_info where siteId=? and censorState=? and author=? and linkCid<0 and classId>0 and creator=? order by contentId desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { siteId, censorState, author, creator, start,
            size }, new ContentValueResultCallBack() );
    }

    public List queryDraftContentInfoBySiteId( Long siteId, Long classId, Long censorState,
        String creator, Long start, Integer size )
    {
        String sql = "select * from content_main_info where siteId=? and classId=? and censorState=? and linkCid<0 and classId>0 and creator=? order by contentId desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { siteId, classId, censorState, creator, start,
            size }, new ContentValueResultCallBack() );
    }

    public List queryDraftContentInfoBySiteIdAndAuthor( Long siteId, Long classId,
        Long censorState, String author, String creator, Long start, Integer size )
    {
        String sql = "select * from content_main_info where siteId=? and classId=? and censorState=?  and author=? and linkCid<0 and classId>0 and creator=? order by contentId desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { siteId, classId, censorState, author,
            creator, start, size }, new ContentValueResultCallBack() );
    }

    public Long queryShareContentCountBySiteId( Long siteId )
    {
        String sql = "select count(*) from site_share_content where toSiteId=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId } );
    }

    public Long queryShareContentCountBySiteIdAndModelId( Long siteId, Long modelId )
    {
        String sql = "select count(*) from site_share_content where toSiteId=? and modelId=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, modelId } );
    }

    public List queryShareContentBySiteId( Long siteId, Long start, Integer size )
    {
        String sql = "select * from content_main_info ci inner join site_share_content ssc on ssc.toSiteId=? and ci.contentId=ssc.contentId order by ssc.sscId desc limit ?,?";
        return pe.queryResultMap( sql, new Object[] { siteId, start, size } );
    }

    public List queryShareContentBySiteIdAndModelId( Long siteId, Long modelId, Long start,
        Integer size )
    {
        String sql = "select * from content_main_info ci inner join site_share_content ssc on ssc.toSiteId=? and ssc.modelId=? and ci.contentId=ssc.contentId order by ssc.sscId desc limit ?,? ";
        return pe.queryResultMap( sql, new Object[] { siteId, modelId, start, size } );
    }

    public Long queryTagRaleteContentCountByTagId( Long tagId )
    {
        String sql = "select count(*) from content_main_info ci inner join tag_relate_content trc on ci.contentId=trc.contentId where trc.tagId=?";
        return ( Long ) pe.querySingleObject( sql, new Object[] { tagId }, Long.class );
    }

    public List queryTagRaleteContentByTagId( Long tagId, String order, Long start, Integer size )
    {
        String sql = "select * from content_main_info ci inner join (select trc.contentId as qid from tag_relate_content trc where trc.tagId=? order by trc.contentId "
            + order + " limit ?,?) ids on ci.contentId=ids.qId order by ci.contentId " + order;

        return pe.queryResultMap( sql, new Object[] { tagId, start, size },
            new ContentValueResultCallBack() );
    }

    public void updateContentRelateIdInfo( Long contentId, String relateStr )
    {
        String sql = "update content_main_info set relateIds=? where contentId=?";

        pe.update( sql, new Object[] { relateStr, contentId } );
    }

    public void updateContentRelateSurvey( Long contentId, String relateStr )
    {
        String sql = "update content_main_info set relateSurvey=? where contentId=?";

        pe.update( sql, new Object[] { relateStr, contentId } );
    }

    public Long queryCountForContentTitle( Long siteId, String title )
    {
        String sql = "select count(*) from content_main_info where siteId=? and title=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, title }, Long.class );
    }

    public Map querySingleContentSource( String source )
    {
        String sql = "select * from content_art_source where sourceName=?";

        return pe.querySingleResultMap( sql, new Object[] { source } );
    }

    public Map querySingleContentSource( Long sId )
    {
        String sql = "select * from content_art_source where sId=?";

        return pe.querySingleResultMap( sql, new Object[] { sId } );
    }

    public Long queryContentSourceCount()
    {
        String sql = "select count(*) from content_art_source";

        return ( Long ) pe.querySingleObject( sql );
    }

    public Long queryContentSourceCount( String fc )
    {
        String sql = "select count(*) from content_art_source where firstChar=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { fc } );
    }

    public List queryContentSource( Long start, Integer size )
    {
        String sql = "select * from content_art_source order by sId desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { start, size } );
    }

    public List queryContentSource( String fc, Long start, Integer size )
    {
        String sql = "select * from content_art_source where firstChar=? order by sId desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { fc, start, size } );
    }

    public void saveSource( String source, String fc )
    {
        String sql = "insert into content_art_source (sourceName, firstChar) values (?, ?)";

        pe.update( sql, new Object[] { source, fc } );
    }

    public void updateSource( String source, String fc, Long sId )
    {
        String sql = "update content_art_source set sourceName=?, firstChar=? where sId=?";

        pe.update( sql, new Object[] { source, fc, sId } );
    }

    public void deleteSource( Long sId )
    {
        String sql = "delete from content_art_source where sId=?";

        pe.update( sql, new Object[] { sId } );
    }

    public Map querySingleSensitiveWord( Long swId )
    {
        String sql = "select * from site_sensitive_word where swId=?";

        return pe.querySingleResultMap( sql, new Object[] { swId } );
    }

    public String querySingleSensitiveWordRep( String sw )
    {
        String sql = "select replaceStr from site_sensitive_word where sensitiveStr=?";

        return ( String ) pe.querySingleObject( sql, new Object[] { sw } );
    }

    public Long querySensitiveWordCount()
    {
        String sql = "select count(*) from site_sensitive_word";

        return ( Long ) pe.querySingleObject( sql, Long.class );
    }

    public List querySensitiveWord( Long start, Integer size )
    {
        String sql = "select * from site_sensitive_word order by swId desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { start, size } );
    }

    public List querySensitiveWord()
    {

        String sql = "select * from site_sensitive_word where useStatus=1";

        return pe.queryResultMap( sql );
    }

    public void saveSensitiveWord( String sensitive, String replace, Integer status )
    {
        String sql = "insert into site_sensitive_word (sensitiveStr, replaceStr, useStatus) values (?, ?, ?)";

        pe.update( sql, new Object[] { sensitive, replace, status } );
    }

    public void updateSensitiveWord( String sensitive, String replace, Long swId )
    {
        String sql = "update site_sensitive_word set sensitiveStr=?, replaceStr=? where swId=? ";

        pe.update( sql, new Object[] { sensitive, replace, swId } );
    }

    public void updateSensitiveWordUseStatus( Integer us, Long swId )
    {
        String sql = "update site_sensitive_word set useStatus=? where swId=? ";

        pe.update( sql, new Object[] { us, swId } );
    }

    public void deleteSensitiveWord( Long swId )
    {
        String sql = "delete from site_sensitive_word where swId=?";

        pe.update( sql, new Object[] { swId } );
    }

    public void saveContentStatus( ContentStatus cs )
    {
        pe.save( cs );
    }

    public void deleteContentStatus( Long contentId )
    {
        String sql = "delete from content_status where selfContentId=?";

        pe.update( sql, new Object[] { contentId } );
    }

    public Map querySingleContentStatus( Long contentId )
    {
        String sql = "select clickMonthCount, clickWeekCount, clickDayCount, clickCount, commMonthCount, commWeekCount, commDayCount, commCount, supportCount, againstCount, moodT1Count, moodT2Count, moodT3Count, moodT4Count, moodT5Count, moodT6Count, moodT7Count, moodT8Count, moodT9Count, moodT10Count from content_main_info where contentId=?";

        return pe.querySingleResultMap( sql, new Object[] { contentId } );
    }

    public void saveDlImg( Long contentId, Long resId )
    {
        String sql = "insert into content_aiticle_dl_img (contentId, resId) values (?,?)";

        pe.update( sql, new Object[] { contentId, resId } );
    }

    public List queryDlImgList( Long contentId )
    {
        String sql = "select resId from content_aiticle_dl_img where contentId=?";

        return pe.querySingleCloumn( sql, new Object[] { contentId } );
    }

    public void deleteDlImgList( Long contentId )
    {
        String sql = "delete from content_aiticle_dl_img where contentId=?";

        pe.update( sql, new Object[] { contentId } );
    }

    public void updateContentTagKeyIdStr( Long contentId, String tagIds )
    {
        String sql = "update content_main_info set tagKey=? where contentId=?";

        pe.update( sql, new Object[] { tagIds, contentId } );
    }

    public List queryContentLinkInfoTrace( Long contentId )
    {
        String sql = "select linkId from content_assistant_link_info where cId=?";

        return pe.querySingleCloumn( sql, new Object[] { contentId }, Long.class );
    }

    public void saveContentLinkInfoTrace( Long contentId, Long linkId )
    {
        String sql = "insert into content_assistant_link_info (cId, linkId) values (?, ?)";

        pe.insert( sql, new Object[] { contentId, linkId } );
    }

    public void saveContentOperInfo( Long contentId, Long opId, Long verId, String puserName,
        String actionId, String msgContent, Timestamp eventDT, String cMode, Integer infoType )
    {
        String sql = "insert into content_oper_info (contentId, opId, verContentId, puserName, actionId, msgContent, eventDT, cMode, infoType) values (?, ?, ?,?,?,?,?,?,?)";

        pe.update( sql, new Object[] { contentId, opId, verId, puserName, actionId, msgContent,
            eventDT, cMode, infoType } );
    }

    public void deleteContentOperInfo( Long contentId )
    {
        String sql = "delete from content_oper_info where contentId=?";

        pe.update( sql, new Object[] { contentId } );
    }

    public Set<String> queryContentOperInfoActMan( Long cid, Long opId )
    {
        String sql = "select puserName from content_oper_info where contentId=? and opId=?";

        return new HashSet<String>( pe.querySingleCloumn( sql, new Object[] { cid, opId },
            String.class ) );
    }

    public Long queryContentOperInfoInfoCount( Long cid )
    {
        String sql = "select count(*) from content_oper_info where contentId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { cid }, Long.class );
    }

    public Long queryLastContentOperInfoInfo( Long cid )
    {
        String sql = "select opId from content_oper_info where contentId=? and opId>0 order by eventDT desc limit 1";

        return ( Long ) pe.querySingleObject( sql, new Object[] { cid }, Long.class );
    }

    public List queryContentOperInfoInfoList( Long cid, Long start, Integer size )
    {
        String sql = "select * from content_oper_info ci, content_main_info cm where ci.contentId=? and ci.contentId=cm.contentId order by eventDT desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { cid, start, size } );
    }

    public List queryContentOperInfo( Long cid, Integer size )
    {
        String sql = "select * from content_oper_info ci, content_main_info cm where ci.contentId=? and ci.contentId=cm.contentId order by eventDT desc limit ?";

        return pe.queryResultMap( sql, new Object[] { cid, size } );
    }

    public List queryGbOperInfo( Long cid, Integer size )
    {
        String sql = "select * from content_oper_info ci, guestbook_main_info cm where ci.contentId=? and ci.contentId=cm.gbId order by eventDT desc limit ?";

        return pe.queryResultMap( sql, new Object[] { cid, size } );
    }

    public List queryContentOperInfoByAction( Long cid, String action, Integer size )
    {
        String sql = "select * from content_oper_info ci, content_main_info cm where ci.actionId=? and ci.contentId=? and ci.contentId=cm.contentId order by eventDT desc limit ?";

        return pe.queryResultMap( sql, new Object[] { action, cid, size } );
    }

    public List queryGbOperInfoByAction( Long cid, String action, Integer size )
    {
        String sql = "select * from content_oper_info ci, guestbook_main_info cm where ci.actionId=? and ci.contentId=? and ci.contentId=cm.gbId order by eventDT desc limit ?";

        return pe.queryResultMap( sql, new Object[] { action, cid, size } );
    }

    public List queryLinkContentMainInfo( Long cid )
    {
        String sql = "select contentId from content_main_info where linkCid=?";

        return pe.querySingleCloumn( sql, new Object[] { cid }, Long.class );
    }

    public List queryTrashLinkContentMainInfo( Long cid )
    {
        String sql = "select contentId from content_trash_main_info where linkCid=?";

        return pe.querySingleCloumn( sql, new Object[] { cid }, Long.class );
    }

    public List<Long> queryRelateContentIdByCId( Long cid, Integer icId )
    {
        String sql = "select infoId from content_relate_info where contentId=? and rClassId=?";

        return pe.querySingleCloumn( sql, new Object[] { cid, icId }, Long.class );
    }

    public void saveRelateContentId( Long cid, Integer classId, Long infoId, Integer order )
    {
        String sql = "insert into content_relate_info ( contentId, rClassId, infoId, orderFlag ) values (?, ?, ?, ?)";

        pe.update( sql, new Object[] { cid, classId, infoId, order } );
    }

    public void deleteRelateContentId( Long cid, Integer classId )
    {
        String sql = "delete from content_relate_info where contentId=? and rClassId=?";

        pe.update( sql, new Object[] { cid, classId } );
    }

    public void deleteRelateContentId( Long cid )
    {
        String sql = "delete from content_relate_info where contentId=?";

        pe.update( sql, new Object[] { cid } );
    }

    public void deleteRelateContentIdByClId( Long clId )
    {
        String sql = "delete from content_relate_info where rClassId=?";

        pe.update( sql, new Object[] { clId } );
    }

    public void deleteRelateContentIdByInfoId( Long infoId )
    {
        String sql = "delete from content_relate_info where infoId=?";

        pe.update( sql, new Object[] { infoId } );
    }

    public static void releaseAllCountCache()
    {
        countCache.clearAllEntry();
        orderFilerCountCache.clearAllEntry();
        addTimeIdCache.clearAllEntry();
    }

    public static void releaseOrderFilterCountCache()
    {
        orderFilerCountCache.clearAllEntry();
    }
}
