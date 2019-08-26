package cn.com.mjsoft.cms.comment.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.comment.bean.CommentInfoBean;
import cn.com.mjsoft.cms.comment.bean.ContentMoodBean;
import cn.com.mjsoft.cms.comment.dao.CommentDao;
import cn.com.mjsoft.cms.comment.dao.vo.CommentInfo;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.content.dao.ContentDao;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.stat.dao.StatDao;
import cn.com.mjsoft.framework.cache.Cache;

import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;

public class CommentService
{
    private static Logger log = Logger.getLogger( CommentService.class );

    public static final Integer ALL_MODE = Integer.valueOf( 1 );

    public static final Integer CLASS_MODE = Integer.valueOf( 2 );

    public static final Integer CONTENT_MODE = Integer.valueOf( 3 );

    public static Cache fastCommentCountCache = new ClusterCacheAdapter( 2000,
        "commentService.fastCommentCountCache" );

    public static Cache fastCommentListCache = new ClusterCacheAdapter( 3500,
        "commentService.fastCommentListCache" );

    private static CommentService service = null;

    private ChannelService channelService = ChannelService.getInstance();

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private CommentDao commentDao;

    private ContentDao contentDao;

    private StatDao statDao;

    private CommentService()
    {
        commentDao = new CommentDao( mysqlEngine );

        contentDao = new ContentDao( mysqlEngine );

        statDao = new StatDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new CommentService();
        }
    }

    public static CommentService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public String addNewCommentAndInfo( CommentInfo comment, Long replyCommentId, String checkCode,
        String testCheckCode )
    {

        if( comment == null )
        {
            log.info( "[addNewCommentAndInfo] 传入的评论对象为空" );
            return "-2";// 传入的评论对象为空
        }

        if( comment.getCommentText().length() < 10 )
        {
            log.info( "[addNewCommentAndInfo] 评论文本小于最小限度值:" + 10 );
            return "-3";// 评论文本小于最小限度值
        }

        try
        {
            mysqlEngine.beginTransaction();

            ContentClassBean classBean = channelService
                .retrieveSingleClassBeanInfoByClassId( comment.getClassId() );

            if( Constant.COMMON.OFF.equals( classBean.getOpenComment() ) )
            {
                // 栏目关闭了评论
                return "-4";
            }

            // 强制输入验证码
           
            
            if( Constant.COMMON.ON.equals( classBean.getCommentCaptcha() ) )
            {
                if( StringUtil.isStringNull( checkCode )
                    || !checkCode.equalsIgnoreCase( testCheckCode ) )
                {
                    // 验证码错误
                    //DCWY
                    return "-1";
                }
            }

            CommentInfoBean replyBean = commentDao
                .querySingleCommentBeanByCommentId( replyCommentId );

            if( replyBean != null )
            {
                comment.setReplyTrace( replyBean.getCommentId() + "," + replyBean.getReplyTrace() );
                comment.setReplyId( replyCommentId );
            }

            comment.setCommDT( new Date( DateAndTimeUtil.clusterTimeMillis() ) );
            // 审核
            if( Constant.COMMON.ON.equals( classBean.getMustCommentCensor() ) )
            {
                comment.setCensorState( Constant.WORKFLOW.CENSOR_STATUS_IN_FLOW );
            }
            else
            {
                comment.setCensorState( Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );
            }

            // 是否强制评论必须会员
            if( Constant.COMMON.OFF.equals( classBean.getNotMemberComment() ) )
            {
                SecuritySession session = SecuritySessionKeeper.getSecuritySession();

                Object member = session.getMember();

                if( member == null )
                {
                    return "-5";
                }
            }

            // html白名单
            comment.setCommentText( ServiceUtil
                .cleanBasicHtmlByWhiteRule( comment.getCommentText() ) );

            // 根据回复对象确定是否有ids
            // comment.setReplyIds( replyIds )
            // 敏感词
            comment.setCommentText( ContentService.getInstance().replcaeContentTextSensitive(
                comment.getCommentText() ) );

            UpdateState us = commentDao.saveComment( comment );

            if( us.haveKey() )
            {
                statDao.updateContentAllCommentAddOneCount( comment.getContentId() );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

        }

        // 成功后立即更新缓存
        ContentDao.releaseOrderFilterCountCache();

        ContentService.releaseFastListContentCache();

        CommentService.clearCommentCache();

        return "1";

    }

    public void addNewDiggInfo( Long contentId, String diggValue )
    {
        try
        {
            mysqlEngine.beginTransaction();

            // ContentMoodBean moodBean = commentDao
            // .querySingleContentMoodBeanById( contentId );
            //
            // Long supportCount = null;
            // Long againstCount = null;
            //
            // if( moodBean == null )
            // {
            // ContentMood mood = new ContentMood();
            //
            // mood.setContentId( contentId );
            //
            // commentDao.saveContentMood( mood );
            //
            // supportCount = Long.valueOf( 1 );
            // againstCount = Long.valueOf( 1 );
            // }
            // else
            // {
            // supportCount = Long.valueOf( moodBean.getSupportCount()
            // .longValue() + 1 );
            // againstCount = Long.valueOf( moodBean.getAgainstCount()
            // .longValue() + 1 );
            // }

            if( Constant.CONTENT.DIGG_SUPPORT.equals( diggValue ) )
            {
                commentDao.updateContentDiggSupportInfo( Long.valueOf( 1 ), contentId );
            }
            else if( Constant.CONTENT.DIGG_AGAINST.equals( diggValue ) )
            {
                commentDao.updateContentDiggAgainstInfo( Long.valueOf( 1 ), contentId );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            ContentDao.releaseOrderFilterCountCache();
            ContentService.releaseFastListContentCache();
        }

    }

    public void updateCommentDiggInfo( Long commentId, String diggValue )
    {
        try
        {
            mysqlEngine.beginTransaction();

            if( Constant.CONTENT.DIGG_SUPPORT.equals( diggValue ) )
            {
                commentDao.updateCommentDiggSupportInfo( Long.valueOf( 1 ), commentId );
            }
            else if( Constant.CONTENT.DIGG_AGAINST.equals( diggValue ) )
            {
                commentDao.updateCommentDiggAgainstInfo( Long.valueOf( 1 ), commentId );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        ContentDao.releaseOrderFilterCountCache();
        ContentService.releaseFastListContentCache();

        CommentService.clearCommentCache();
    }

    public void addNewMoodInfo( Long contentId, Integer moodFlag )
    {
        if( moodFlag.intValue() < 1 || moodFlag.intValue() > 10 )
        {
            return;
        }

        try
        {
            mysqlEngine.beginTransaction();

            // ContentMoodBean moodBean = commentDao
            // .querySingleContentMoodBeanById( contentId );
            //
            // Long moodCount = null;
            //
            // if( moodBean == null )
            // {
            // ContentMood mood = new ContentMood();
            //
            // mood.setContentId( contentId );
            //
            // commentDao.saveContentMood( mood );
            //
            // moodCount = Long.valueOf( 1 );
            // }
            // else
            // {
            // moodCount = Long.valueOf( chooseCurrentMoodCount( moodFlag,
            // moodBean ).longValue() + 1 );
            // }

            commentDao.updateContentMoodInfo( moodFlag, Long.valueOf( 1 ), contentId );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            ContentDao.releaseOrderFilterCountCache();
            ContentService.releaseFastListContentCache();
        }

    }

    public Long retrieveCommentCount( Long siteId, Integer mode, Long id, Integer status )
    {
        String key = "retrieveCommentCount:" + siteId + "|" + mode + "|" + id + "|" + status;

        Long count = ( Long ) fastCommentCountCache.getEntry( key );

        if( count == null )
        {

            if( status.intValue() < 0 )
            {
                if( ALL_MODE.equals( mode ) )
                {
                    count = commentDao.queryCommentCount( siteId );
                }
                else if( CLASS_MODE.equals( mode ) )
                {
                    count = commentDao.queryCommentCountByClassId( id );
                }
                else if( CONTENT_MODE.equals( mode ) )
                {
                    count = commentDao.queryCommentCountByContentId( id );
                }
            }
            else
            {
                if( ALL_MODE.equals( mode ) )
                {
                    count = commentDao.queryCommentCount( siteId, status );
                }
                else if( CLASS_MODE.equals( mode ) )
                {
                    count = commentDao.queryCommentCountByClassId( id, status );
                }
                else if( CONTENT_MODE.equals( mode ) )
                {
                    count = commentDao.queryCommentCountByContentId( id, status );
                }
            }

            fastCommentCountCache.putEntry( key, count );
        }

        return count;
    }

    public Long retrieveCommentCountTypeMode( Long siteId, Integer typeId, Long id, Integer status )
    {
        String key = "retrieveCommentCountTypeMode:" + siteId + "|" + typeId + "|" + id + "|"
            + status;

        Long count = ( Long ) fastCommentCountCache.getEntry( key );

        if( count == null )
        {

            if( status.intValue() < 0 )
            {
                count = commentDao.queryCommentCountByTypeId( id, typeId );
            }
            else
            {
                count = commentDao.queryCommentCountByTypeId( id, typeId, status );
            }

            fastCommentCountCache.putEntry( key, count );
        }

        return count;
    }

    public Long retrieveCommentCountByUserName( Long siteId, String userName )
    {
        return commentDao.queryCommentCountByUserName( siteId, userName );
    }

    public List retrieveCommentBeanListByContentId( Long contentId, Long start, Integer size,
        Integer status )
    {
        String key = "retrieveCommentBeanListByContentId:" + contentId + "|" + start + "|" + size
            + "|" + status;

        List result = ( List ) fastCommentListCache.getEntry( key );

        if( result == null )
        {

            if( status.intValue() < 0 )
            {
                result = commentDao.queryCommentBeanListByContentId( contentId, start, size );
            }
            else
            {
                result = commentDao
                    .queryCommentBeanListByContentId( contentId, start, size, status );
            }

            fastCommentListCache.putEntry( key, result );
        }

        return result;
    }

    public List retrieveCommentBeanListByTypeId( Long contentId, Integer typeId, Long start,
        Integer size, Integer status )
    {
        String key = "retrieveCommentBeanListByContentId:" + contentId + "|" + typeId + "|" + start
            + "|" + size + "|" + status;

        List result = ( List ) fastCommentListCache.getEntry( key );

        if( result == null )
        {

            if( status.intValue() < 0 )
            {
                result = commentDao.queryCommentBeanListByTypeId( contentId, typeId, start, size );
            }
            else
            {
                result = commentDao.queryCommentBeanListByTypeId( contentId, typeId, start, size,
                    status );
            }

            fastCommentListCache.putEntry( key, result );
        }

        return result;
    }

    public List retrieveCommentBeanListByClassId( Long classId, Long start, Integer size,
        Integer status )
    {
        if( status.intValue() < 0 )
        {
            return commentDao.queryCommentBeanListByClassId( classId, start, size );
        }
        else
        {
            return commentDao.queryCommentBeanListByClassId( classId, start, size, status );
        }
    }

    public CommentInfoBean retrieveSingeleCommentBean( Long commId )
    {
        return commentDao.querySingeleCommentBean( commId );
    }

    public List retrieveAllCommentBeanList( Long siteId, Long startPos, Integer limit,
        Integer status )
    {
        if( status.intValue() < 0 )
        {
            return commentDao.queryAllCommentBeanListBySiteId( siteId, startPos, limit );
        }
        else
        {
            return commentDao.queryAllCommentBeanListBySiteId( siteId, startPos, limit, status );
        }
    }

    public List retrieveAllCommentBeanListByUserName( Long siteId, String userName, Long startPos,
        Integer limit )
    {
        return commentDao.queryAllCommentBeanListBySiteIdAndUserName( siteId, userName, startPos,
            limit );
    }

    public List retrieveCommentBeanListByCommentIds( String ids )
    {

        if( StringUtil.isStringNull( ids ) )
        {
            return Collections.EMPTY_LIST;
        }

        String[] idsArray = StringUtil.split( ids, "," );

        if( idsArray == null || idsArray.length == 0 )
        {
            return Collections.EMPTY_LIST;
        }

        StringBuffer buf = new StringBuffer( "" );

        for ( int i = 0; i < idsArray.length; i++ )
        {
            if( ( i + 1 ) == idsArray.length )
            {
                buf.append( idsArray[i] );
            }
            else
            {
                buf.append( idsArray[i] + ", " );
            }
        }

        return commentDao.queryCommentBeanListByIdInFlag( buf.toString() );
    }

    public List retrieveCommentBeanListByParentId( Long replyId )
    {
        List result = null;
        try
        {
            mysqlEngine.beginTransaction();
            result = commentDao.queryCommentBeanListByParentId( replyId );
            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        return result;
    }

    public void deleteCommentAllInfoByIds( Long[] idArray, List memberCollList )
    {
        if( idArray == null )
        {
            return;
        }

        StringBuffer buf = new StringBuffer();
        for ( int i = 0; i < idArray.length; i++ )
        {
            buf.append( idArray[i] );

            if( ( i + 1 ) != idArray.length )
            {
                buf.append( ", " );
            }
        }

        try
        {
            mysqlEngine.beginTransaction();

            CommentInfoBean ciBean = null;

            for ( int i = 0; i < idArray.length; i++ )
            {
                ciBean = commentDao.querySingeleCommentBean( idArray[i] );

                if( ciBean.getMemberId().longValue() > 0 )
                {
                    memberCollList.add( ciBean.getMemberId() );
                }

                statDao.updateContentAllCommentMinusOneCount( ciBean.getContentId() );
            }

            commentDao.deleteCommentAllInfoByIdFlag( buf.toString() );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        // 成功后立即更新缓存
        ContentDao.releaseOrderFilterCountCache();

        ContentService.releaseFastListContentCache();

        CommentService.clearCommentCache();

    }

    public void censorComment( Long[] commentIdArray, Integer nextState )
    {
        try
        {
            mysqlEngine.beginTransaction();

            if( ( !Constant.WORKFLOW.CENSOR_STATUS_DRAFT.equals( nextState ) )
                && ( !Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( nextState ) )
                && ( !Constant.WORKFLOW.CENSOR_STATUS_RETURN.equals( nextState ) ) )
            {
                // 非正常审核状态
                return;
            }

            if( commentIdArray.length == 1 )
            {

                commentDao.updateCommentBeanCensorState( commentIdArray[0], nextState );

            }
            else
            {
                StringBuffer buf = new StringBuffer();
                for ( int i = 0; i < commentIdArray.length; i++ )
                {

                    buf.append( commentIdArray[i] );

                    if( ( i + 1 ) != commentIdArray.length )
                    {
                        buf.append( ", " );
                    }
                }

                commentDao.updateCommentBeanCensorStateByIdFlag( buf.toString(), nextState );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            CommentService.clearCommentCache();
        }
    }

    public static void clearCommentCache()
    {
        fastCommentCountCache.clearAllEntry();
        fastCommentListCache.clearAllEntry();
    }

    private Long chooseCurrentMoodCount( Integer moodFlag, ContentMoodBean moodBean )
    {
        if( moodFlag.intValue() == 1 )
        {
            return moodBean.getMoodT1count();
        }
        else if( moodFlag.intValue() == 2 )
        {
            return moodBean.getMoodT2count();
        }
        else if( moodFlag.intValue() == 3 )
        {
            return moodBean.getMoodT3count();
        }
        else if( moodFlag.intValue() == 4 )
        {
            return moodBean.getMoodT4count();
        }
        else if( moodFlag.intValue() == 5 )
        {
            return moodBean.getMoodT5count();
        }
        else if( moodFlag.intValue() == 6 )
        {
            return moodBean.getMoodT6count();
        }
        else if( moodFlag.intValue() == 7 )
        {
            return moodBean.getMoodT7count();
        }
        else if( moodFlag.intValue() == 8 )
        {
            return moodBean.getMoodT8count();
        }
        else if( moodFlag.intValue() == 9 )
        {
            return moodBean.getMoodT9count();
        }
        else if( moodFlag.intValue() == 10 )
        {
            return moodBean.getMoodT10count();
        }

        return Long.valueOf( -1 );
    }

}
