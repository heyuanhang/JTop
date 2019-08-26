package cn.com.mjsoft.cms.weixin.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.com.mjsoft.cms.weixin.bean.WxAccount;
import cn.com.mjsoft.cms.weixin.bean.WxMsgBean;
import cn.com.mjsoft.cms.weixin.bean.WxSendAllInfoBean;
import cn.com.mjsoft.cms.weixin.bean.WxUserBean;
import cn.com.mjsoft.cms.weixin.bean.item.NewsItemGroupBean;
import cn.com.mjsoft.cms.weixin.bean.item.WxNewsItemInfo;
import cn.com.mjsoft.cms.weixin.bean.res.WxResourceBean;
import cn.com.mjsoft.cms.weixin.dao.vo.WxMenu;
import cn.com.mjsoft.cms.weixin.dao.vo.WxUser;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.util.StringUtil;

public class WeixinDao
{
    public PersistenceEngine pe;

    public void setPe( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public WeixinDao()
    {

    }

    public WeixinDao( PersistenceEngine pe )
    {
        this.pe = pe;
    }

    public UpdateState saveWxConfig( WxAccount wa )
    {
        String sql = "insert into wx_account (wxName, appId, appsSecret, apiToken, mainId, subWelInfoId, subWelResId, siteId) values (?,?,?,?,?,?,?,?)";

        return pe.insert( sql, wa );
    }

    public void updateWxConfig( WxAccount wa )
    {
        String sql = "update wx_account set wxName=?, appId=?, appsSecret=?, apiToken=?, mainId=?, subWelInfoId=?, subWelResId=? where acId=?";

        pe.update( sql, wa );
    }

    public WxAccount querySingleWxConfigBySiteId( Long siteId )
    {
        String sql = "select * from wx_account where siteId=?";

        return ( WxAccount ) pe.querySingleBean( sql, new Object[] { siteId }, WxAccount.class );
    }

    public WxAccount querySingleWxConfigByWxCode( String wxCode )
    {
        String sql = "select * from wx_account where mainId=?";

        return ( WxAccount ) pe.querySingleBean( sql, new Object[] { wxCode }, WxAccount.class );
    }

    public WxAccount querySingleWxConfig( Long acId )
    {
        String sql = "select * from wx_account where acId=?";

        return ( WxAccount ) pe.querySingleBean( sql, new Object[] { acId }, WxAccount.class );
    }

    public UpdateState saveWxMenu( WxMenu vo )
    {
        return pe.save( vo );
    }

    public void updateWxMenu( WxMenu vo )
    {
        String sql = "update wx_menu set mtId=?, resId=?, btName=?, btType=?, btKey=?, mediaId=?, btUrl=?, behaviorClass=? where wxCode=? and btId=?";

        pe.update( sql, vo );
    }

    public WxMenu querySingleWxMenu( String wxCode, Long btId )
    {
        String sql = "select * from wx_menu where wxCode=? and btId=?";

        return ( WxMenu ) pe.querySingleBean( sql, new Object[] { wxCode, btId }, WxMenu.class );
    }

    public List querySingleWxMenuByKey( String wxCode, String key )
    {
        String sql = "select * from wx_menu where wxCode=? and btKey=?";

        return pe.queryBeanList( sql, new Object[] { wxCode, key }, WxMenu.class );
    }

    public List queryWxMenuByParent( String wxCode, Long btId )
    {
        String sql = "select * from wx_menu where wxCode=? and parentId=? order by btOrder asc";

        return pe.queryBeanList( sql, new Object[] { wxCode, btId }, WxMenu.class );
    }

    public WxMenu queryWxMenuByOrderFlag( Long wxId, Integer order )
    {
        String sql = "select * from wx_menu where wxId=? and btOrder=? order by btOrder asc";

        return ( WxMenu ) pe.querySingleBean( sql, new Object[] { wxId, order }, WxMenu.class );
    }

    public List queryWxMenuByLayer( Long wxId, Integer layer )
    {
        String sql = "select * from wx_menu where wxId=? and btLayer=? order by btOrder asc";

        return pe.queryBeanList( sql, new Object[] { wxId, layer }, WxMenu.class );
    }

    public List queryAllWxMenu( String wxCode )
    {
        String sql = "select * from wx_menu where wxCode=? order by btOrder asc";

        return pe.queryBeanList( sql, new Object[] { wxCode }, WxMenu.class );
    }

    public void updateWxMenuOrder( String wxCode, Long btId, Integer order )
    {
        String sql = "update wx_menu set btOrder=? where wxCode=? and btId=?";

        pe.update( sql, new Object[] { order, wxCode, btId } );
    }

    public void deleteWxMenuBtId( String wxCode, Long btId )
    {
        String sql = "delete from wx_menu where wxCode=? and btId=?";

        pe.update( sql, new Object[] { wxCode, btId } );
    }

    public void deleteWxMenuBtParentId( String wxCode, Long parentId )
    {
        String sql = "delete from wx_menu where wxCode=? and parentId=?";

        pe.update( sql, new Object[] { wxCode, parentId } );
    }

    public Integer queryWxNewsItemMaxRowOrderByRowFlag( Long rowFlag, Long wxId )
    {
        String sql = "select max(rowOrder) from wx_news_info_item where rowFlag=? and wxId=?";

        return ( Integer ) pe
            .querySingleObject( sql, new Object[] { rowFlag, wxId }, Integer.class );
    }

    public List queryWxExtendByWxCode( String wxCode )
    {
        String sql = "select * from wx_event_be_extend where wxCode=? order by ebId desc";

        return pe.queryResultMap( sql, new Object[] { wxCode } );
    }

    public List queryWxExtendByWxCodeByMsg( String wxCode )
    {
        String sql = "select * from wx_event_be_extend where wxCode=? and isMsg=1 order by ebId desc";

        return pe.queryResultMap( sql, new Object[] { wxCode } );
    }

    public List queryWxExtendByWxCodeByInput( String wxCode )
    {
        String sql = "select * from wx_event_be_extend where wxCode=? and isInput=1 order by ebId desc";

        return pe.queryResultMap( sql, new Object[] { wxCode } );
    }

    public List queryWxExtendByWxCodeByMenu( String wxCode )
    {
        String sql = "select * from wx_event_be_extend where wxCode=? and isMenu=1 order by ebId desc";

        return pe.queryResultMap( sql, new Object[] { wxCode } );
    }

    public List queryWxExtendByWxCodeAndEventType( String wxCode, String eventType )
    {
        String sql = "select * from wx_event_be_extend where wxCode=? and eventType=? order by ebId desc";

        return pe.queryResultMap( sql, new Object[] { wxCode, eventType } );
    }

    public List queryWxExtendByWxCodeAndMsgType( String wxCode, String msgType )
    {
        String sql = "select * from wx_event_be_extend where wxCode=? and msgType=? order by ebId desc";

        return pe.queryResultMap( sql, new Object[] { wxCode, msgType } );
    }

    public Map querySingleWxExtendById( Long ebId )
    {
        String sql = "select * from wx_event_be_extend where ebId=?";

        return pe.querySingleResultMap( sql, new Object[] { ebId } );
    }

    public UpdateState saveNewWxExtend( Map params )
    {
        String sql = "insert into wx_event_be_extend (ebName, beClass, eventType, msgType, isMsg, isMenu, isInput, useStatus, wxCode) values (?,?,?,?,?,?,?,?,?)";

        return pe.insert( sql, params );
    }

    public void editWxExtend( Map params )
    {
        String sql = "update wx_event_be_extend set ebName=?, beClass=?, useStatus=? where ebId=?";

        pe.update( sql, params );
    }

    public void deleteWxExtendById( Long ebId )
    {
        String sql = "delete from wx_event_be_extend where ebId=?";

        pe.update( sql, new Object[] { ebId } );
    }

    public UpdateState saveNewsItem( WxNewsItemInfo wii )
    {
        String sql = "insert into wx_news_info_item (rowFlag, rowOrder, contentId, resType, resTag, title, url, img, showCover, summary, commendMan, articleText, addTime, modelId, classId, commendTypeId, orderFlag, typeFlag, commendFlag, isTranSucc, wxId, wxCode, siteFlag) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        return pe.insert( sql, wii );
    }

    public void updateNewsItemRowOrder( String commFlag, Long rowFlag, Integer startOrder,
        Long notUpdateInfoId )
    {
        String sql = "update wx_news_info_item set rowOrder=rowOrder+1 where commendFlag=? and rowFlag=? and rowOrder>=? and infoId!=?";

        pe.update( sql, new Object[] { commFlag, rowFlag, startOrder, notUpdateInfoId } );
    }

    public List queryAllNewsItemByWxId( String siteFlag, boolean needRev )
    {
        String sql = "select ci.* from wx_news_info_item ci inner join (select infoId as qid from wx_news_info_item cpi where cpi.siteFlag=? order by cpi.rowFlag asc, cpi.rowOrder desc) ids on ids.qid=ci.infoId order by ci.rowFlag asc, ci.rowOrder desc";

        Map prevBeanInfoMap = new TreeMap();

        pe
            .query( sql, new Object[] { siteFlag },
                new NewsItemGroupBeanTransform( prevBeanInfoMap ) );

        List val = new ArrayList( prevBeanInfoMap.values() );

        if( needRev )
        {
            Collections.reverse( val );
        }

        return val;
    }

    public NewsItemGroupBean querySingleNewsItemInfoByInfoId( Long infoId )
    {
        String sql = "select * from wx_news_info_item where infoId=?";

        return ( NewsItemGroupBean ) pe.querySingleBean( sql, new Object[] { infoId },
            NewsItemGroupBean.class );
    }

    public void updateNewsItemRowFlagByInfoId( Long rowFlag, Long infoId )
    {
        String sql = "update wx_news_info_item set rowFlag=? where infoId=?";
        pe.update( sql, new Object[] { rowFlag, infoId } );

    }

    public void updateNewsItemInfoByInfoId( WxNewsItemInfo news )
    {
        String sql = "update wx_news_info_item set contentId=?, resTag=?, title=?, url=?, img=?, showCover=?, summary=?, articleText=?, commendMan=?, modelId=?, classId=? where infoId=?";
        pe.update( sql, news );
    }

    public void updateNewsItemInfoMediaId( Long infoId, Integer isTranSucc, String mediaId )
    {
        String sql = "update wx_news_info_item set mediaId=?, isTranSucc=? where infoId=?";
        pe.update( sql, new Object[] { mediaId, isTranSucc, infoId } );
    }

    public void updateNewsItemInfoOrderInfoByInfoId( Long rowFlag, Integer rowOrder, Long infoId )
    {
        String sql = "update wx_news_info_item set rowFlag=?, rowOrder=? where infoId=?";
        pe.update( sql, new Object[] { rowFlag, rowOrder, infoId } );

    }

    public Integer queryAllNewsItemCountBySite( String siteFlag )
    {
        String sql = "select count(distinct rowFlag) from wx_news_info_item cpi where cpi.siteFlag=?";

        Integer count = ( Integer ) pe.querySingleObject( sql, new Object[] { siteFlag },
            Integer.class );

        return count;
    }

    public Integer queryAllNewsItemCountBySite( String resTag, String siteFlag )
    {
        String sql = "select count(distinct rowFlag) from wx_news_info_item cpi where cpi.resTag=? and cpi.siteFlag=?";

        Integer count = ( Integer ) pe.querySingleObject( sql, new Object[] { resTag, siteFlag },
            Integer.class );

        return count;
    }

    public List queryAllNewsItemBySite( String siteFlag, Long startPos, Integer size,
        boolean needRev )
    {
        String sql = "select ci.* from wx_news_info_item ci inner join (select distinct rowFlag as qid from wx_news_info_item cpi where cpi.siteFlag=? order by cpi.rowFlag asc, cpi.rowOrder desc limit ?,?) ids on ids.qid=ci.rowFlag where ci.siteFlag=? order by ci.rowFlag asc, ci.rowOrder desc";

        Map prevBeanInfoMap = new TreeMap();

        pe.query( sql, new Object[] { siteFlag, startPos, size, siteFlag },
            new NewsItemGroupBeanTransform( prevBeanInfoMap ) );

        List val = new ArrayList( prevBeanInfoMap.values() );

        if( needRev )
        {
            Collections.reverse( val );
        }

        return val;
    }

    public List queryAllNewsItemBySite( String resTag, String siteFlag, Long startPos,
        Integer size, boolean needRev )
    {
        String sql = "select ci.* from wx_news_info_item ci inner join (select distinct rowFlag as qid from wx_news_info_item cpi where cpi.resTag=? and cpi.siteFlag=? order by cpi.rowFlag asc, cpi.rowOrder desc limit ?,?) ids on ids.qid=ci.rowFlag where ci.siteFlag=? order by ci.rowFlag asc, ci.rowOrder desc";

        Map prevBeanInfoMap = new TreeMap();

        pe.query( sql, new Object[] { resTag, siteFlag, startPos, size, siteFlag },
            new NewsItemGroupBeanTransform( prevBeanInfoMap ) );

        List val = new ArrayList( prevBeanInfoMap.values() );

        if( needRev )
        {
            Collections.reverse( val );
        }

        return val;
    }

    public List queryNewsItemGroupByRowFlag( Long rowFlag, String siteFlag )
    {
        String sql = "select * from wx_news_info_item cpi where cpi.rowFlag=? and cpi.siteFlag=? order by cpi.rowOrder desc";

        return pe.query( sql, new Object[] { rowFlag, siteFlag }, new NewsItemGroupBeanTransform(
            false ) );
    }

    public List queryNewsItemGroupByRowFlagAndWxCode( Long rowFlag, String wxCode )
    {
        String sql = "select * from wx_news_info_item cpi where cpi.rowFlag=? and cpi.wxCode=? order by cpi.rowOrder desc";

        return pe.query( sql, new Object[] { rowFlag, wxCode }, new NewsItemGroupBeanTransform(
            false ) );
    }

    public List queryNewsItemByRowFlagAndExcludeId( String siteFlag, Long rowFlag,
        List excludeInfoId )
    {
        StringBuffer buf = new StringBuffer( "(" );

        long id = -1;
        boolean havaParam = false;

        for ( int i = 0; i < excludeInfoId.size(); i++ )
        {
            // check非法参数
            id = StringUtil.getLongValue( ( String ) excludeInfoId.get( i ), -1 );

            if( id < 0 )
            {
                continue;
            }

            havaParam = true;

            buf.append( ( String ) excludeInfoId.get( i ) );
            if( ( i + 1 ) != excludeInfoId.size() )
            {
                buf.append( "," );
            }
        }

        buf.append( ")" );

        if( !havaParam )
        {
            return null;
        }

        String sql = "select * from wx_news_info_item where rowFlag=? and siteFlag=? and infoId not in "
            + buf.toString() + " order by rowOrder asc";

        return pe.query( sql, new Object[] { rowFlag, siteFlag }, new NewsItemGroupBeanTransform(
            true ) );
    }

    public void deleteNewsItemByInfoId( Long infoId )
    {
        String sql = "delete from wx_news_info_item where infoId=?";
        pe.update( sql, new Object[] { infoId } );

    }

    public void saveWxMessage( Map params )
    {
        String sql = "insert into wx_message (inputKey, isInclude, isText, msgType, infoId, resId, wxCode) values (?, ?, ?, ?, ?, ?,?)";
        pe.update( sql, params );
    }

    public void editWxMessage( Map params )
    {
        String sql = "update wx_message set inputKey=?, isInclude=?, isText=?, msgType=?, infoId=?, resId=? where msgId=? and wxCode=?";
        pe.update( sql, params );
    }

    public void updateWxTextMsg( Long msgId, String wxCode, String text )
    {
        String sql = "update wx_message set textMsg=? where msgId=? and wxCode=?";

        pe.update( sql, new Object[] { text, msgId, wxCode } );
    }

    public void deleteWxMessage( Long msgId, String wxCode )
    {
        String sql = "delete from wx_message where msgId=? and wxCode=?";
        pe.update( sql, new Object[] { msgId, wxCode } );
    }

    public Map queryWxUnkMessageByMsgType( String msgType, String wxCode )
    {
        String sql = "select * from wx_message_unk where msgType=? and wxCode=?";

        return pe.querySingleResultMap( sql, new Object[] { msgType, wxCode } );
    }

    public List queryWxUnkMessage( String wxCode )
    {
        String sql = "select * from wx_message_unk where wxCode=?";

        return pe.queryResultMap( sql, new Object[] { wxCode } );
    }

    public void saveWxUnkMessage( Map params )
    {
        String sql = "insert into wx_message_unk (msgType, infoId, resId, wxCode) values (?, ?, ?, ?)";
        pe.update( sql, params );
    }

    public void updateWxUnkMessage( Map params )
    {
        String sql = "update wx_message_unk set infoId=?, resId=? where msgType=? and wxCode=?";

        pe.update( sql, params );
    }

    public void deleteWxUnkMessage( String msgType, String wxCode )
    {
        String sql = "delete from wx_message_unk where msgType=? and wxCode=?";

        pe.update( sql, new Object[] { msgType, wxCode } );
    }

    public Long queryWxMessageCount( String wxCode )
    {
        String sql = "select count(*) from wx_message where wxCode=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { wxCode }, Long.class );
    }

    public List queryWxMessage( String wxCode, Long sPos, Integer size )
    {
        String sql = "select * from wx_message where wxCode=? order by msgid desc limit ?,?";

        return pe.queryBeanList( sql, new Object[] { wxCode, sPos, size }, WxMsgBean.class );
    }

    public List queryWxMessageByIncludeMode( String wxCode, Integer isInclude )
    {
        String sql = "select * from wx_message where wxCode=? and isInclude=?";

        return pe.queryBeanList( sql, new Object[] { wxCode, isInclude }, WxMsgBean.class );
    }

    public WxMsgBean querySingleWxMessageByKey( String wxCode, String key )
    {
        String sql = "select * from wx_message where wxCode=? and inputKey=?";

        return ( WxMsgBean ) pe
            .querySingleBean( sql, new Object[] { wxCode, key }, WxMsgBean.class );
    }

    public WxMsgBean querySingleWxMessage( String wxCode, Long msgId )
    {
        String sql = "select * from wx_message where wxCode=? and msgId=?";

        return ( WxMsgBean ) pe.querySingleBean( sql, new Object[] { wxCode, msgId },
            WxMsgBean.class );
    }

    public void saveWxImageRes( Map params )
    {
        String sql = "insert into wx_resource (resTitle, imageRes, resType, resTag, isTranSucc, wxCode, siteId) values (?, ?, ?, ?, ?, ?, ?)";
        pe.update( sql, params );
    }

    public void updateWxImageRes( Map params )
    {
        String sql = "update wx_resource set resTitle=?, imageRes=?, resTag=? where wxCode=? and wrId=?";

        pe.update( sql, params );
    }

    public void saveWxVideoRes( Map params )
    {
        String sql = "insert into wx_resource (resName, resTitle, resDesc, videoRes,  resType, resTag, isTranSucc, wxCode, siteId) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        pe.update( sql, params );
    }

    public void updateWxVideoRes( Map params )
    {
        String sql = "update wx_resource set videoRes=?, resTitle=?, resDesc=?, resTag=? where wxCode=? and wrId=?";

        pe.update( sql, params );
    }

    public void saveWxVoiceRes( Map params )
    {
        String sql = "insert into wx_resource (resName, resTitle, resDesc, voiceRes, resType, resTag, isTranSucc, wxCode, siteId) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        pe.update( sql, params );
    }

    public void updateWxVoiceRes( Map params )
    {
        String sql = "update wx_resource set voiceRes=?, resTitle=?, resDesc=?, resTag=? where wxCode=? and wrId=?";

        pe.update( sql, params );
    }

    public void saveWxMusicRes( Map params )
    {
        String sql = "insert into wx_resource (resName, resTitle, musicThumb, musicUrl, hqMusicUrl, resDesc, musicRes, resType, resTag, isTranSucc, wxCode, siteId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        pe.update( sql, params );
    }

    public void updateWxMusicRes( Map params )
    {
        String sql = "update wx_resource set musicRes=?, resTitle=?, resDesc=?, musicUrl=?, musicThumb=?,  hqMusicUrl=?, resTag=? where wxCode=? and wrId=?";

        pe.update( sql, params );
    }

    public void saveWxTextRes( Map params )
    {
        String sql = "insert into wx_resource (resTitle, resContent, resType, resTag, isTranSucc, wxCode, siteId) values (?, ?, ?, ?, ?, ?, ?)";
        pe.update( sql, params );
    }

    public void updateWxTextRes( Map params )
    {
        String sql = "update wx_resource set resTitle=?, resContent=?, resTag=? where wxCode=? and wrId=?";

        pe.update( sql, params );
    }

    public void updateWxResTranInfo( Long wrId, String mediaId, String mediaImgUrl, Timestamp tranDT )
    {
        String sql = "update wx_resource set mediaId=?, mediaImgUrl=?, isTranSucc=1, tranDT=? where wrId=?";

        pe.update( sql, new Object[] { mediaId, mediaImgUrl, tranDT, wrId } );
    }

    public void updateWxThumbResTranInfo( Long wrId, String thumbMediaId, String thumbUrl,
        Timestamp tranDT )
    {
        String sql = "update wx_resource set thumbMediaId=?, thumbUrl=?, isTranSucc=1, tranDT=? where wrId=?";

        pe.update( sql, new Object[] { thumbMediaId, thumbUrl, tranDT, wrId } );
    }

    public WxResourceBean querySingleWxRes( String wxCode, Long wrId )
    {
        String sql = "select * from wx_resource where wxCode=? and wrId=?";

        return ( WxResourceBean ) pe.querySingleBean( sql, new Object[] { wxCode, wrId },
            WxResourceBean.class );
    }

    public Long queryAllWxImageResCount( String wxCode, String type )
    {
        String sql = "select count(*) from wx_resource where wxCode=? and resType=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { wxCode, type }, Long.class );
    }

    public List queryAllWxImageRes( String wxCode, String type, Long sPos, Integer size )
    {
        String sql = "select * from wx_resource where wxCode=? and resType=? order by wrId desc limit ?,?";

        return pe.queryBeanList( sql, new Object[] { wxCode, type, sPos, size },
            WxResourceBean.class );
    }

    public Long queryWxImageResByTagCount( String wxCode, String type, String resTag )
    {
        String sql = "select count(*) from wx_resource where wxCode=? and resType=? and resTag=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { wxCode, type, resTag },
            Long.class );
    }

    public List queryWxImageResByTag( String wxCode, String type, String resTag, Long sPos,
        Integer size )
    {
        String sql = "select * from wx_resource where wxCode=? and resType=? and resTag=? order by wrId desc limit ?,?";

        return pe.queryBeanList( sql, new Object[] { wxCode, type, resTag, sPos, size },
            WxResourceBean.class );
    }

    public void deleteWxResRes( Long wrId, String wxCode )
    {
        String sql = "delete from wx_resource where wrId=? and wxCode=?";

        pe.update( sql, new Object[] { wrId, wxCode } );
    }

    public List queryResTagByType( String wxCode, String resType )
    {
        String sql = "select * from wx_resource_tag where wxCode=? and resType=? order by rtId desc";

        return pe.queryResultMap( sql, new Object[] { wxCode, resType } );
    }

    public void saveResTag( String tagName, String resType, String wxCode )
    {
        String sql = "insert into wx_resource_tag (resTagName, resType, wxCode) values (?, ?, ?)";

        pe.update( sql, new Object[] { tagName, resType, wxCode } );
    }

    public void updateResTag( Long rtId, String tagName, String wxCode )
    {
        String sql = "update wx_resource_tag set resTagName=? where wxCode=? and rtId=?";

        pe.update( sql, new Object[] { tagName, wxCode, rtId } );
    }

    public void deleteResTag( Long rtId, String wxCode )
    {
        String sql = "delete from wx_resource_tag where wxCode=? and rtId=?";

        pe.update( sql, new Object[] { wxCode, rtId } );
    }

    public void saveWxUser( WxUser wu )
    {
        pe.save( wu );
    }

    public void updateWxUserRemark( String opId, String re )
    {
        String sql = "update wx_user set userRemark=? where openId=?";

        pe.update( sql, new Object[] { re, opId } );
    }

    public List queryWxUserByWxCode( String wxCode )
    {
        String sql = "select * from wx_user where wxCode=?";

        return pe.queryBeanList( sql, new Object[] { wxCode }, WxUserBean.class );
    }

    public Long queryWxUserCountByWxCode( String wxCode )
    {
        String sql = "select count(*) from wx_user where wxCode=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { wxCode }, Long.class );
    }

    public List queryWxUserByWxCode( String wxCode, Long start, Integer size )
    {
        String sql = "select * from wx_user where wxCode=? limit ?,?";

        return pe.queryBeanList( sql, new Object[] { wxCode, start, size }, WxUserBean.class );
    }

    public Long queryWxUserCountByWxCodeAndGroup( String wxCode, String group )
    {
        String sql = "select count(*) from wx_user where wxCode=? and wuGroupid=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { wxCode, group }, Long.class );
    }

    public List queryWxUserByWxCodeAndGroup( String wxCode, String group, Long start, Integer size )
    {
        String sql = "select * from wx_user where wxCode=? and wuGroupid=? and subStatus=1 limit ?,?";

        return pe
            .queryBeanList( sql, new Object[] { wxCode, group, start, size }, WxUserBean.class );
    }

    public List queryWxUserByWxCodeAndNickName( String wxCode, String nickName )
    {
        String sql = "select * from wx_user where wxCode=? and wuNickname like '%" + nickName
            + "%'";

        return pe.queryBeanList( sql, new Object[] { wxCode }, WxUserBean.class );
    }

    public List queryWxUserByWxCodeAndRemark( String wxCode, String remark )
    {
        String sql = "select * from wx_user where wxCode=? and userRemark like '%" + remark + "%'";

        return pe.queryBeanList( sql, new Object[] { wxCode }, WxUserBean.class );
    }

    public WxUser querySingleWxUserByWxCodeAndOpenId( String wxCode, String opid )
    {
        String sql = "select * from wx_user where wxCode=? and openId=?";

        return ( WxUser ) pe.querySingleBean( sql, new Object[] { wxCode, opid }, WxUser.class );
    }

    public List queryWxUserByWxCodeAndGId( String wxCode, String gid )
    {
        String sql = "select * from wx_user where wxCode=? and wuGroupId=?";

        return pe.queryBeanList( sql, new Object[] { wxCode, gid }, WxUser.class );
    }

    public void updateWxUserNewGroup( String wxCode, String wuGroupId, String openId )
    {
        String sql = "update wx_user set wuGroupId=? where wxCode=? and openId=?";

        pe.update( sql, new Object[] { wuGroupId, wxCode, openId } );
    }

    public void updateWxUserSubStatus( String wxCode, String openId, Integer status )
    {
        String sql = "update wx_user set subStatus=? where wxCode=? and openId=?";

        pe.update( sql, new Object[] { status, wxCode, openId } );
    }

    public void deleteWxUserByOpenId( String wxCode, String opid )
    {
        String sql = "delete from wx_user where wxCode=? and openId=?";

        pe.update( sql, new Object[] { wxCode, opid } );
    }

    public void deleteWxUserByWxCode( String wxCode )
    {
        String sql = "delete from wx_user where wxCode=?";

        pe.update( sql, new Object[] { wxCode } );
    }

    public void deleteWxUserByWxCodeAndStatus( String wxCode, Integer status )
    {
        String sql = "delete from wx_user where wxCode=? and subStatus=?";

        pe.update( sql, new Object[] { wxCode, status } );
    }

    public void updateWxUserStatusByWxCode( String wxCode, Integer status )
    {
        String sql = "update wx_user set subStatus=? where wxCode=?";

        pe.update( sql, new Object[] { status, wxCode } );
    }

    public void saveWxUserGroup( String wxCode, String wuGroupId, String wuGroupName,
        String wuUserCount )
    {
        String sql = "insert into wx_user_group (wuGroupId, wuGroupName, wuUserCount, wxCode) values (?,?,?,?)";

        pe.update( sql, new Object[] { wuGroupId, wuGroupName, wuUserCount, wxCode } );
    }

    public void updateWxUserGroup( String wxCode, String wuGroupId, String newName )
    {
        String sql = "update wx_user_group set wuGroupName=? where wxCode=? and wuGroupId=?";

        pe.update( sql, new Object[] { newName, wxCode, wuGroupId } );
    }

    public Map querySingleWxUserGroupByWxCodeAndGId( String wxCode, String gid )
    {
        String sql = "select * from wx_user_group where wxCode=? and wuGroupId=?";

        return pe.querySingleResultMap( sql, new Object[] { wxCode, gid } );
    }

    public List queryWxUserGroupByWxCode( String wxCode )
    {
        String sql = "select * from wx_user_group where wxCode=?";

        return pe.queryResultMap( sql, new Object[] { wxCode } );
    }

    public void deleteWxUserGroupByWxCode( String wxCode )
    {
        String sql = "delete from wx_user_group where wxCode=?";

        pe.update( sql, new Object[] { wxCode } );
    }

    public void deleteWxUserGroupByWxCode( String wxCode, String wuGroupId )
    {
        String sql = "delete from wx_user_group where wxCode=? and wuGroupId=?";

        pe.update( sql, new Object[] { wxCode, wuGroupId } );
    }

    public void saveWxSendAllInfo( WxSendAllInfoBean sai )
    {
        String sql = "insert into wx_send_all_info (msgType, msgId, exeMan, exeTime, sendDT, sendTarget, censor, wxCode, wxId) values (?,?,?,?,?,?,?,?,?)";

        pe.update( sql, sai );
    }

    public WxSendAllInfoBean querySingleWxSendAllInfoCount( Long saId )
    {
        String sql = "select * from wx_send_all_info where saId=?";

        return ( WxSendAllInfoBean ) pe.querySingleBean( sql, new Object[] { saId },
            WxSendAllInfoBean.class );
    }

    public Long queryWxSendAllInfoCount( String wxCode )
    {
        String sql = "select count(*) from wx_send_all_info where wxCode=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { wxCode }, Long.class );
    }

    public List queryWxSendAllInfo( String wxCode, Long start, Integer size )
    {
        String sql = "select * from wx_send_all_info where wxCode=? order by said desc limit ?,?";

        return pe
            .queryBeanList( sql, new Object[] { wxCode, start, size }, WxSendAllInfoBean.class );
    }

    public Long queryWxSendAllInfoCount( String wxCode, Integer censor )
    {
        String sql = "select count(*) from wx_send_all_info where wxCode=? and censor=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { wxCode, censor }, Long.class );
    }

    public List queryWxSendAllInfo( String wxCode, Integer censor, Long start, Integer size )
    {
        String sql = "select * from wx_send_all_info where wxCode=? and censor=? order by said desc limit ?,?";

        return pe.queryBeanList( sql, new Object[] { wxCode, censor, start, size },
            WxSendAllInfoBean.class );
    }

    public Long queryWxSendAllInfoCount( String wxCode, String manager )
    {
        String sql = "select count(*) from wx_send_all_info where wxCode=? and exeMan=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { wxCode, manager }, Long.class );
    }

    public List queryWxSendAllInfo( String wxCode, String manager, Long start, Integer size )
    {
        String sql = "select * from wx_send_all_info where wxCode=? and exeMan=? order by said desc limit ?,?";

        return pe.queryBeanList( sql, new Object[] { wxCode, manager, start, size },
            WxSendAllInfoBean.class );
    }

    public Long queryWxSendAllInfoCount( String wxCode, String manager, Integer censor )
    {
        String sql = "select count(*) from wx_send_all_info where wxCode=? and exeMan=? and censor=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { wxCode, manager, censor },
            Long.class );
    }

    public List queryWxSendAllInfoByIsSend( String wxCode, Long start, Integer size )
    {
        String sql = "select * from wx_send_all_info where wxCode=? and isSend=1 order by said desc limit ?,?";

        return pe
            .queryBeanList( sql, new Object[] { wxCode, start, size }, WxSendAllInfoBean.class );
    }

    public Long queryWxSendAllInfoByIsSendCount( String wxCode )
    {
        String sql = "select count(*) from wx_send_all_info where wxCode=? and isSend=1";

        return ( Long ) pe.querySingleObject( sql, new Object[] { wxCode }, Long.class );
    }

    public List queryWxSendAllInfoByIsSend( String wxCode, String exeman, Long start, Integer size )
    {
        String sql = "select * from wx_send_all_info where wxCode=? and isSend=1 and exeMan=? order by said desc limit ?,?";

        return pe.queryBeanList( sql, new Object[] { wxCode, exeman, start, size },
            WxSendAllInfoBean.class );
    }

    public Long queryWxSendAllInfoByIsSendCount( String wxCode, String exeman )
    {
        String sql = "select count(*) from wx_send_all_info where wxCode=? and isSend=1 and exeMan=?";

        return ( Long ) pe.querySingleObject( sql, new Object[] { wxCode, exeman }, Long.class );
    }

    public WxSendAllInfoBean querySingleWxSendAllInfo( Long saId )
    {
        String sql = "select * from wx_send_all_info where saId=?";

        return ( WxSendAllInfoBean ) pe.querySingleBean( sql, new Object[] { saId },
            WxSendAllInfoBean.class );
    }

    public List queryWxSendAllInfo( String wxCode, String manager, Integer censor, Long start,
        Integer size )
    {
        String sql = "select * from wx_send_all_info where wxCode=? and exeMan=? and censor=? order by said desc limit ?,?";

        return pe.queryBeanList( sql, new Object[] { wxCode, manager, censor, start, size },
            WxSendAllInfoBean.class );
    }

    public List queryWxNeedSendAllInfoByCensor( String wxCode, Date now )
    {
        String sql = "select * from wx_send_all_info where wxCode=? and censor=1 and sendDT<=? order by said asc";

        return pe.queryBeanList( sql, new Object[] { wxCode, now }, WxSendAllInfoBean.class );
    }

    public void updateWxSendAllInfoCensor( Long saiId, Integer censor, String wxCode )
    {
        String sql = "update wx_send_all_info set censor=? where wxCode=? and saId=?";

        pe.update( sql, new Object[] { censor, wxCode, saiId } );
    }

    public void updateWxSendAllInfoReturnMsg( Long saiId, String rmsg )
    {
        String sql = "update wx_send_all_info set returnMsg=?, isSend=1 where saId=?";

        pe.update( sql, new Object[] { rmsg, saiId } );
    }

    public void updateWxCode( String oldWC, String newWC )
    {
        String sql1 = "update wx_event_be_extend set wxCode=? where wxCode=?";

        pe.update( sql1, new Object[] { newWC, oldWC } );

        String sql2 = "update wx_menu set wxCode=? where wxCode=?";

        pe.update( sql2, new Object[] { newWC, oldWC } );

        String sql3 = "update wx_message set wxCode=? where wxCode=?";

        pe.update( sql3, new Object[] { newWC, oldWC } );

        String sql4 = "update wx_message_unk set wxCode=? where wxCode=?";

        pe.update( sql4, new Object[] { newWC, oldWC } );

        String sql5 = "update wx_news_info_item set wxCode=? where wxCode=?";

        pe.update( sql5, new Object[] { newWC, oldWC } );

        String sql6 = "update wx_resource set wxCode=? where wxCode=?";

        pe.update( sql6, new Object[] { newWC, oldWC } );

        String sql7 = "update wx_resource_tag set wxCode=? where wxCode=?";

        pe.update( sql7, new Object[] { newWC, oldWC } );

        String sql8 = "update wx_send_all_info set wxCode=? where wxCode=?";

        pe.update( sql8, new Object[] { newWC, oldWC } );

    }

    public void deleteWXAllbyWxCode( String wc )
    {
        String sql1 = "delete from wx_event_be_extend where wxCode=?";

        pe.update( sql1, new Object[] { wc } );

        String sql2 = "delete from wx_menu where wxCode=?";

        pe.update( sql2, new Object[] { wc } );

        String sql3 = "delete from wx_message where wxCode=?";

        pe.update( sql3, new Object[] { wc } );

        String sql4 = "delete from wx_message_unk where wxCode=?";

        pe.update( sql4, new Object[] { wc } );

        String sql5 = "delete from wx_news_info_item where wxCode=?";

        pe.update( sql5, new Object[] { wc } );

        String sql6 = "delete from wx_resource where wxCode=?";

        pe.update( sql6, new Object[] { wc } );

        String sql7 = "delete from wx_resource_tag where wxCode=?";

        pe.update( sql7, new Object[] { wc } );

        String sql8 = "delete from wx_send_all_info where wxCode=?";

        pe.update( sql8, new Object[] { wc } );

        String sql9 = "delete from wx_account where wxCode=?";

        pe.update( sql9, new Object[] { wc } );

    }

}
