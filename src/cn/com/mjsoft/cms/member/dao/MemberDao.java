package cn.com.mjsoft.cms.member.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.content.dao.ContentValueResultCallBack;
import cn.com.mjsoft.cms.member.bean.MemberBean;
import cn.com.mjsoft.cms.member.bean.MemberMessageTemplateBean;
import cn.com.mjsoft.cms.member.bean.MemberRankBean;
import cn.com.mjsoft.cms.member.bean.MemberScoreActBean;
import cn.com.mjsoft.cms.member.dao.vo.Member;
import cn.com.mjsoft.cms.member.dao.vo.MemberMessage;
import cn.com.mjsoft.cms.member.dao.vo.MemberMessageTemplate;
import cn.com.mjsoft.cms.member.dao.vo.MemberRank;
import cn.com.mjsoft.cms.member.dao.vo.MemberScoreAct;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.framework.cache.Cache;

import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;

public class MemberDao
{
    private static Cache rankListCache = new ClusterCacheAdapter( 100, "memberDao.rankListCache" );

    private PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public MemberDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public void save( MemberMessageTemplate mt )
    {
        pe.save( mt );
    }

    public UpdateState save( Member mem )
    {
        return pe.save( mem );
    }

    public void updateMemberMessageTemplate( MemberMessageTemplate mt )
    {
        String sql = "update member_message_template set mtFlag=?, templateName=?, templateTitle=?, templateContent=? where mtId=?";
        pe.update( sql, mt );
    }

    public void save( MemberMessage msg )
    {
        pe.save( msg );
    }

    public UpdateState deleteMessage( Long memberId )
    {
        String sql = "delete from member_message where memberId=?";

        return pe.update( sql, new Object[] { memberId } );
    }

    public void deleteMessageById( Long msgId, Long memberId )
    {
        String sql = "delete from member_message where msgId=? and memberId=?";

        pe.update( sql, new Object[] { msgId, memberId } );
    }

    public void deleteCommentById( Long commentId, Long memberId )
    {
        String sql = "delete from comment_info where commentId=? and memberId=?";

        pe.update( sql, new Object[] { commentId, memberId } );
    }

    public MemberMessageTemplateBean querySingleMessageTemplate( String templateFlag, Long siteId )
    {
        String sql = "select * from member_message_template where mtFlag=? and siteId=?";
        return ( MemberMessageTemplateBean ) pe.querySingleRow( sql, new Object[] { templateFlag,
            siteId }, new MemberMessageTemplateBeanTransform() );
    }

    public void saveMessageTemplateParam( Map parmasMap )
    {
        String sql = "insert into member_message_template_param (paramName, paramFlag, creator, siteId) values (?, ?, ?, ?)";
        pe.insert( sql, parmasMap );

    }

    public void updateMessageTemplateParam( Map params )
    {
        String sql = "update member_message_template_param set paramName=?, paramFlag=? where tpId=?";
        pe.insert( sql, params );
    }

    public Long queryMessageTemplateCountBySiteId( Long siteId )
    {
        String sql = "select count(*) from member_message_template where siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId }, Long.class );
    }

    public List queryMessageTemplateBySiteId( Long siteId, Long start, Integer size )
    {
        String sql = "select * from member_message_template where siteId=? order by mtId desc limit ?,?";
        return pe.query( sql, new Object[] { siteId, start, size },
            new MemberMessageTemplateBeanTransform() );
    }

    public Long queryMemeberCountBySiteId( Long siteId )
    {
        String sql = "select count(*) from member where siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId }, Long.class );
    }

    public Long queryMemeberCountBySiteId( Long siteId, Integer status )
    {
        String sql = "select count(*) from member where siteId=? and useStatus=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, status }, Long.class );
    }

    public List queryMemeberBySiteId( Long siteId, Long start, Integer size )
    {
        String sql = "select * from member where siteId=? order by memberId desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { siteId, start, size } );
    }

    public List queryMemeberBySiteId( Long siteId, Integer status, Long start, Integer size )
    {
        String sql = "select * from member where siteId=? and useStatus=? order by memberId desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { siteId, status, start, size } );
    }

    public List queryMemeberByRoleId( Long roleId )
    {
        String sql = "select esu.*,null as roleName, null as roleId from member esu inner join (select userId from member_relate_role where roleId=?) ids on ids.userId=esu.memberId";

        return pe.queryResultMap( sql, new Object[] { roleId } );
    }

    public List queryMemeberByRoleId( Long roleId, Long start, Integer size )
    {
        String sql = "select esu.*,null as roleName, null as roleId from member esu inner join (select userId from member_relate_role where roleId=?) ids on ids.userId=esu.memberId order by esu.memberId desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { roleId, start, size } );
    }

    public Long queryMemeberCountByRoleId( Long roleId )
    {
        String sql = "select count(*) from member esu inner join (select userId from member_relate_role where roleId=?) ids on ids.userId=esu.memberId";

        return ( Long ) pe.querySingleObject( sql, new Object[] { roleId }, Long.class );
    }

    public Map querySingleMemberInfo( Long memberId )
    {
        String sql = "select * from member where memberId=?";
        return pe.querySingleResultMap( sql, new Object[] { memberId } );
    }

    public MemberBean querySingleMemberBeanById( Long memId )
    {
        String sql = "select * from member where memberId=?";

        return ( MemberBean ) pe.querySingleRow( sql, new Object[] { memId },
            new MemberBeanTransform() );
    }

    public MemberBean queryMemberBeanByName( String memName )
    {
        String sql = "select * from member where memberName=?";

        return ( MemberBean ) pe.querySingleRow( sql, new Object[] { memName },
            new MemberBeanTransform() );
    }

    public MemberBean queryMemberBeanByMail( String mail )
    {
        String sql = "select * from member where email=?";

        return ( MemberBean ) pe.querySingleRow( sql, new Object[] { mail },
            new MemberBeanTransform() );
    }

    public Map queryMemberInfoById( Long memId, DataModelBean model,
        ModelPersistenceMySqlCodeBean sqlCodeBean )
    {
        String noExtSql = "select * from member where memberId=?";

        if( model == null || sqlCodeBean == null)
        {
            return pe.querySingleResultMap( noExtSql, new Object[] { memId } );
        }
        else
        {
            String sql = "select mem.*, " + sqlCodeBean.getSelectColumn()
                + " from member mem left join " + model.getRelateTableName()
                + " tmp on tmp.contentId=mem.memberId where mem.memberId=?";

            return pe.querySingleResultMap( sql, new Object[] { memId } );
        }
    }

    public Map queryMemeberByName( Long siteId, String memberName )
    {
        String sql = "select * from member where siteId=? and memberName=?";

        return pe.querySingleResultMap( sql, new Object[] { siteId, memberName } );

    }

    public List queryMemeberLikeName( Long siteId, String nameStr )
    {
        String sql = "select * from member where siteId=? and memberName like ?";

        return pe.queryResultMap( sql, new Object[] { siteId, "%" + nameStr + "%" } );

    }

    public List queryMemeberByEmail( Long siteId, String email )
    {
        String sql = "select * from member where siteId=? and email=?";

        return pe.queryResultMap( sql, new Object[] { siteId, email } );

    }

    public List queryMemeberByTrueName( Long siteId, String trueName )
    {
        String sql = "select * from member where siteId=? and trueName=?";

        return pe.queryResultMap( sql, new Object[] { siteId, trueName } );
    }

    public Map queryMemeberByPhone( Long siteId, String phoneNumber )
    {
        String sql = "select * from member where siteId=? and phoneNumber=?";

        return pe.querySingleResultMap( sql, new Object[] { siteId, phoneNumber } );
    }

    public List queryMemeberByIsTrueName( Long siteId, String isTM )
    {
        String sql = "select * from member where siteId=? and isTrueMan=?";

        return pe.queryResultMap( sql, new Object[] { siteId, isTM } );
    }

    public Long queryActInfoCommentCount( Long memId, Long siteId )
    {
        String sql = "select count(*) from comment_info where memberId=? and siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { memId, siteId }, Long.class );
    }

    public Long queryActInfoMessageCount( Long memId )
    {
        String sql = "select count(*) from member_message where memberId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { memId }, Long.class );
    }

    public Long queryActInfoGuestbookCount( Long memId, Long siteId )
    {
        String sql = "select count(*) from guestbook_main_info where memberId=? and siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { memId, siteId }, Long.class );
    }

    public Long queryActInfoPushContentCount( String memName, Long siteId )
    {
        String sql = "select count(*) from content_main_info where creator=? and otherFlag=1 and siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { memName, siteId }, Long.class );
    }

    public void updateMemberBasic( Map params )
    {
        String sql = "update member set email=?, trueName=?, certCode=? where memberId=?";

        pe.update( sql, params );
    }

    public UpdateState updateMemberPW( Long memberId, String newPw )
    {
        String sql = "update member set password=? where memberId=?";

        return pe.update( sql, new Object[] { newPw, memberId } );

    }

    public UpdateState updateMemberHeadPhoto( String headPhoto, Long memberId )
    {
        String sql = "update member set headPhoto=? where memberId=?";

        return pe.update( sql, new Object[] { headPhoto, memberId } );
    }

    public UpdateState updateMemberCertPhoto( String certPhotoP, String certPhotoR, Long memberId )
    {
        String sql = "update member set certPhotoP=?, certPhotoR=? where memberId=?";

        return pe.update( sql, new Object[] { certPhotoP, certPhotoR, memberId } );
    }

    public void updateMemberCertStatus( Long memberId, Integer status )
    {
        String sql = "update member set isTrueMan=? where memberId=?";

        pe.update( sql, new Object[] { status, memberId } );
    }

    public void updateMemberBankStatus( Long memberId, Integer status )
    {
        String sql = "update member set isTruePayCard=? where memberId=?";

        pe.update( sql, new Object[] { status, memberId } );
    }

    public Long queryMemCollContent( Long cId, Long memId )
    {
        String sql = "select count(*) from member_coll_content where cId=? and memId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { cId, memId }, Long.class );
    }

    public Long queryMemCollContentCount( Long memId )
    {
        String sql = "select count(*) from member_coll_content where memId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { memId }, Long.class );
    }

    public Long queryMemCollContentCount( Long memId, Long modelId )
    {
        String sql = "select count(*) from member_coll_content where memId=? and mId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { memId, modelId }, Long.class );
    }

    public List queryMemCollContent( Long memId, Long start, Integer size )
    {
        String sql = "select cId from member_coll_content where memId=? order by cId desc limit ?,?";

        return pe.querySingleCloumn( sql, new Object[] { memId, start, size }, Long.class );
    }

    public List queryMemCollContent( Long memId, Long modelId, Long start, Integer size )
    {
        String sql = "select cId from member_coll_content where memId=? and mId=? order by cId desc limit ?,?";

        return pe.querySingleCloumn( sql, new Object[] { memId, modelId, start, size }, Long.class );
    }

    public void saveMemCollContent( Long cId, Long mId, Long memId )
    {
        String sql = "insert into member_coll_content (cId, mId, memId) values (?, ?, ?)";

        pe.insert( sql, new Object[] { cId, mId, memId } );
    }

    public void deleteMemCollContent( Long cId, Long memId )
    {
        String sql = "delete from member_coll_content where cId=? and memId=?";

        pe.update( sql, new Object[] { cId, memId } );
    }

    public void saveMemHisContent( Long cId, Long mId, Long memId )
    {
        String sql = "insert into member_history_content (cId, mId,memId,  addDate) values (?, ?, ?, ?)";

        pe.insert( sql, new Object[] { cId, mId, memId, new Date() } );
    }

    public List queryMemHisContent( Long memId, Long start, Integer size )
    {
        String sql = "select cId from member_history_content where memId=? order by addDate desc limit ?,?";

        return pe.querySingleCloumn( sql, new Object[] { memId, start, size }, Long.class );
    }

    public List queryMemHisContent( Long memId, Long modelId, Long start, Integer size )
    {
        String sql = "select cId from member_history_content where memId=? and mId=? order by addDate desc limit ?,?";

        return pe.querySingleCloumn( sql, new Object[] { memId, modelId, start, size }, Long.class );
    }

    public Long queryMemHisContentCount( Long memId )
    {
        String sql = "select count(*) from member_history_content where memId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { memId }, Long.class );
    }

    public Long queryMemHisContentCount( Long memId, Long modelId )
    {
        String sql = "select count(*) from member_history_content where memId=? and mId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { memId, modelId }, Long.class );
    }

    public Long queryMemHisContent( Long cId, Long memId )
    {
        String sql = "select count(*) from member_history_content where cId=? and memId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { cId, memId }, Long.class );
    }

    public void updateMemberUseStatus( Long memberId, Integer status )
    {
        String sql = "update member set useStatus=? where memberId=?";

        pe.update( sql, new Object[] { status, memberId } );
    }

    public void updateMemberLevel( Long memberId, Integer level )
    {
        String sql = "update member set memLevel=? where memberId=?";

        pe.update( sql, new Object[] { level, memberId } );
    }

    public Map querySingleMemberInfoByName( String name )
    {
        String sql = "select * from member where memberName=?";

        return pe.querySingleResultMap( sql, new Object[] { name } );
    }

    public Map querySingleMemberInfoByMail( String email )
    {
        String sql = "select * from member where email=?";
        return pe.querySingleResultMap( sql, new Object[] { email } );
    }

    public Map querySingleMemberInfoByPhone( String mobiCode )
    {
        String sql = "select * from member where phoneNumber=?";
        return pe.querySingleResultMap( sql, new Object[] { mobiCode } );
    }

    public void updateMailCheckStatus( Long memberId, Integer status )
    {
        String sql = "update member set isTrueEmail=? where memberId=?";
        pe.update( sql, new Object[] { status, memberId, } );
    }

    public void updateMemberLoginTrace( String crIp, Timestamp crloginDT, String prIp, String area,
        Timestamp prloginDT, Long memberId )
    {
        String sql = "update member set isFirst ='0', prevLoginIp=?, prevLoginDT=?, currLoginIp=?, currLoginDT=?, prevLoginArea=? where memberId=?";

        pe.update( sql, new Object[] { prIp, prloginDT, crIp, crloginDT, area, memberId } );
    }

    public Map queryThirdLoginInfo( String loginType, String openID )
    {
        String sql = "select * from member_third_login where loginType=? and openUid=?";

        return pe.querySingleResultMap( sql, new Object[] { loginType, openID } );
    }

    public void saveThirdLoginInfo( Long memberId, String loginType, String openID )
    {
        String sql = "insert into member_third_login (selfMemberId, loginType, openUid) values (?,?,?)";

        pe.update( sql, new Object[] { memberId, loginType, openID } );
    }

    public void saveMemberLostEmailTrace( String key, Date dt )
    {
        String sql = "insert into member_lost_email_trace (keyStr, eventDt) values (?, ?)";

        pe.update( sql, new Object[] { key, dt } );

    }

    public Long queryMemberLostEmailTraceCount( String key )
    {
        String sql = "select count(*) from member_lost_email_trace where keyStr=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { key }, Long.class );
    }

    public void deleteMemberLostEmailTrace( String key )
    {
        String sql = "delete from member_lost_email_trace where keyStr=?";

        pe.update( sql, new Object[] { key } );
    }

    public MemberRankBean querySingleMemberRankInfo( Long id )
    {
        String sql = "select * from member_rank where rankId=?";

        return ( MemberRankBean ) pe.querySingleBean( sql, new Object[] { id },
            MemberRankBean.class );
    }

    public List queryMemberRankInfo( Long siteId )
    {
        String sql = "select * from member_rank where siteId=? order by rankLevel asc";

        List result = ( List ) rankListCache.getEntry( siteId );

        if( result == null )
        {
            result = pe.queryResultMap( sql, new Object[] { siteId } );

            rankListCache.putEntry( siteId, result );
        }

        return result;
    }

    public List queryMemberRankInfoByLevel( Integer level )
    {
        String sql = "select * from member_rank where rankLevel=? order by rankLevel asc";

        return pe.queryResultMap( sql, new Object[] { level } );
    }

    public void saveMemberRank( MemberRank rank )
    {
        pe.save( rank );
    }

    public void deleteMemberRank( Long id )
    {
        String sql = "delete from member_rank where rankId=?";

        pe.update( sql, new Object[] { id } );
    }

    public void deleteMemberRankBySiteId( Long siteId )
    {
        String sql = "delete from member_rank where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void updateMemberRank( MemberRank rank )
    {
        String sql = "update member_rank set rankName=?, rankLevel=?, minScore=?, maxScore=?, rankDesc=? where rankId=?";

        pe.update( sql, rank );

    }

    public MemberScoreActBean querySingleMemberScoreActInfo( Long id )
    {
        String sql = "select * from member_score_act where saId=?";

        return ( MemberScoreActBean ) pe.querySingleBean( sql, new Object[] { id },
            MemberScoreActBean.class );
    }

    public MemberScoreActBean querySingleMemberScoreActInfoByCommand( String cmd, Long siteId )
    {
        String sql = "select * from member_score_act where targetCmd=? and siteId=?";

        return ( MemberScoreActBean ) pe.querySingleBean( sql, new Object[] { cmd, siteId },
            MemberScoreActBean.class );
    }

    public List queryMemberScoreActInfo( Long siteId )
    {
        String sql = "select * from member_score_act where siteId=? order by said asc";

        return pe.queryResultMap( sql, new Object[] { siteId } );
    }

    public void saveMemberScoreAct( MemberScoreAct sa )
    {
        pe.save( sa );
    }

    public void deleteMemberScoreAct( Long id )
    {
        String sql = "delete from member_score_act where saId=?";

        pe.update( sql, new Object[] { id } );
    }

    public void deleteMemberScoreActBySiteId( Long siteId )
    {
        String sql = "delete from member_score_act where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void updateMemberScoreAct( MemberScoreAct sa )
    {
        String sql = "update member_score_act set actName=?, actFlag=?, actScore=?, targetCmd=?, actClass=?, actDesc=? where saId=?";

        pe.update( sql, sa );
    }

    public void updateMemberScoreAddVal( Long memberId, Integer actScore )
    {
        String sql = "update member set score=score+? where memberId=?";

        pe.update( sql, new Object[] { actScore, memberId } );

    }

    public void updateMemberScoreReduceVal( Long memberId, Integer actScore )
    {
        String sql = "update member set score=score-? where memberId=?";

        pe.update( sql, new Object[] { actScore, memberId } );

    }

    public void updateMemberLoginSuccessCount( Long memberId )
    {
        String sql = "update member set loginSuccessCount=loginSuccessCount+1 where memberId=?";

        pe.update( sql, new Object[] { memberId } );
    }

    public Map querySingleMemberThirdRegInfo( Long memberId )
    {
        String sql = "select * from member_third_login where selfMemberId=?";

        return pe.querySingleResultMap( sql, new Object[] { memberId } );
    }

    public void deleteMemberThirdRegInfo( Long memberId )
    {
        String sql = "delete from member_third_login where selfMemberId=?";

        pe.update( sql, new Object[] { memberId } );
    }

    public void saveMemberLoginTrace( Long siteId, String ip, String userName, Timestamp eventDT,
        Integer ls )
    {
        String sql = "insert into member_login_trace (siteId, ip, userName, eventDT, loginSuccess) values (?,?,?,?,?)";

        pe.update( sql, new Object[] { siteId, ip, userName, eventDT, ls } );
    }

    public Long queryMemberLoginTraceCount( String userName )
    {
        String sql = "select count(*) from member_login_trace where userName=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { userName }, Long.class );
    }

    public List queryMemberLoginTrace( String userName, Long start, Integer size )
    {
        String sql = "select * from member_login_trace where userName=? order by eventDT desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { userName, start, size } );
    }

    public Long queryMemberLoginTraceCount( Long siteId )
    {
        String sql = "select count(*) from member_login_trace where siteId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId }, Long.class );
    }

    public List queryMemberLoginTrace( Long siteId, Long start, Integer size )
    {
        String sql = "select * from member_login_trace where siteId=? order by eventDT desc limit ?,?";

        return pe.queryResultMap( sql, new Object[] { siteId, start, size } );
    }

    /**
     * 以下前台会员Tag使用
     */
    public Long queryMemeberContentCountBySiteId( Long siteId, String memName )
    {
        String sql = "select count(*) from content_main_info where siteId=? and creator=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, memName }, Long.class );
    }

    public List queryMemeberContentMainInfoBySiteId( Long siteId, String memName, Long start,
        Integer size )
    {
        String sql = "select * from content_main_info where siteId=? and creator=? order by contentId desc limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { siteId, memName, start, size } );
    }

    public Long queryMemeberContentCountBySiteIdAndState( Long siteId, String memName, Integer state )
    {
        String sql = "select count(*) from content_main_info where siteId=? and creator=? and censorState=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, memName, state },
            Long.class );
    }

    public List queryMemeberContentMainInfoBySiteIdAndState( Long siteId, String memName,
        Integer state, Long start, Integer size )
    {
        String sql = "select * from content_main_info where siteId=? and creator=? and censorState=? order by contentId desc limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { siteId, memName, state, start, size } );
    }

    public Map queryMemeberSIngleContentMainInfoById( Long cid )
    {
        String sql = "select * from content_main_info where contentId=?";

        return pe
            .querySingleResultMap( sql, new Object[] { cid }, new ContentValueResultCallBack() );
    }

    public Long queryMemeberCommentCountBySiteId( Long siteId, Long memberId )
    {
        String sql = "select count(*) from comment_info where siteId=? and memberId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, memberId }, Long.class );
    }

    public List queryMemeberCommentBySiteId( Long siteId, Long memberId, Long start, Integer size )
    {
        String sql = "select * from comment_info where siteId=? and memberId=? order by commentId desc limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { siteId, memberId, start, size } );
    }

    public Long queryMemeberCommentCountBySiteIdAndCensor( Long siteId, Long memberId,
        Integer censor )
    {
        String sql = "select count(*) from comment_info where siteId=? and memberId=? and censorState=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, memberId, censor },
            Long.class );
    }

    public List queryMemeberCommentBySiteIdAndCensor( Long siteId, Long memberId, Integer censor,
        Long start, Integer size )
    {
        String sql = "select * from comment_info where siteId=? and memberId=? and censorState=? order by commentId desc limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { siteId, memberId, censor, start, size } );
    }

    public Long queryMemeberGbCountBySiteId( Long siteId, Long memberId )
    {
        String sql = "select count(*) from guestbook_main_info where siteId=? and memberId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, memberId }, Long.class );
    }

    public List queryMemeberGbBySiteId( Long siteId, Long memberId, Long start, Integer size )
    {
        String sql = "select * from guestbook_main_info where siteId=? and memberId=? order by gbId desc limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { siteId, memberId, start, size } );
    }

    public Long queryMemeberGbCountBySiteIdAndCensor( Long siteId, Long memberId, Integer censor )
    {
        String sql = "select count(*) from guestbook_main_info where siteId=? and memberId=? and isReply=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { siteId, memberId, censor },
            Long.class );
    }

    public List queryMemeberGbBySiteIdAndCensor( Long siteId, Long memberId, Integer censor,
        Long start, Integer size )
    {
        String sql = "select * from guestbook_main_info where siteId=? and memberId=? and isReply=? order by gbId desc limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { siteId, memberId, censor, start, size } );
    }

    public Long queryMemeberMsgCount( Long memberId )
    {
        String sql = "select count(*) from member_message where memberId=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { memberId }, Long.class );
    }

    public List queryMemeberMsg( Long memberId, Long start, Integer size )
    {
        String sql = "select * from member_message where memberId=? order by msgId desc limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { memberId, start, size } );
    }

    public Long queryMemeberMsgCount( Long memberId, Integer isR )
    {
        String sql = "select count(*) from member_message where memberId=? and isRead=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { memberId, isR }, Long.class );
    }

    public List queryMemeberMsg( Long memberId, Integer isR, Long start, Integer size )
    {
        String sql = "select * from member_message where memberId=? and isRead=? order by msgId desc limit ?,? ";

        return pe.queryResultMap( sql, new Object[] { memberId, isR, start, size } );
    }

    public Map querySingleMemeberMsg( Long msgId )
    {
        String sql = "select * from member_message where msgId=?";

        return pe.querySingleResultMap( sql, new Object[] { msgId } );
    }

    public void updateMemeberMsgReadFlag( Long msgId )
    {
        String sql = "update member_message set isRead=1 where msgId=?";

        pe.update( sql, new Object[] { msgId } );
    }

    public void updateMemberEMail( Long memberId, String regMail )
    {
        String sql = "update member set email=? where memberId=?";

        pe.update( sql, new Object[] { regMail, memberId } );

    }

    public void deleteMemberById( Long memberId )
    {
        String sql = "delete from member where memberId=?";

        pe.update( sql, new Object[] { memberId } );
    }

    public void deleteMemberRoleBySiteId( Long siteId )
    {
        String sql = "delete from member_role where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteMemberRoleRelateSecBySiteId( Long siteId )
    {
        String sql = "delete from member_role_relate_resource where roleId in (select roleId from member_role where siteId=?)";

        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteMemberSecBySiteId( Long siteId )
    {
        String sql = "delete from member_securityresource where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteMessageTemplateParamBySiteId( Long siteId )
    {
        String sql = "delete from member_message_template_param where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteMessageTemplateBySiteId( Long siteId )
    {
        String sql = "delete from member_message_template where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteMemberLoginTraceBySiteId( Long siteId )
    {
        String sql = "delete from member_login_trace where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteMemberClassSubmitAccBySiteId( Long siteId )
    {
        String sql = "delete from member_class_submit_acc where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteMemberClassAccBySiteId( Long siteId )
    {
        String sql = "delete from member_class_acc where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteMemberAccClassRelateRoleBySiteId( Long siteId )
    {
        String sql = "delete from member_acc_class_relate_role where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteMemberAccRuleBySiteId( Long siteId )
    {
        String sql = "delete from member_acc_rule where siteId=?";

        pe.update( sql, new Object[] { siteId } );
    }

    public void deleteMessageTemplate( Long mtId )
    {
        String sql = "delete from member_message_template where mtId=?";

        pe.update( sql, new Object[] { mtId } );

    }

    public void deleteMessageTemplateParam( Long mtId )
    {
        String sql = "delete from member_message_template_param where tpId=?";

        pe.update( sql, new Object[] { mtId } );

    }

    public static void clearRankCache()
    {
        rankListCache.clearAllEntry();
    }

}
