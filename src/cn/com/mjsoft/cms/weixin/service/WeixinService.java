package cn.com.mjsoft.cms.weixin.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.cluster.adapter.ClusterMapAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.dao.ContentDao;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.dao.MetaDataDao;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.resources.bean.SiteResourceBean;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.weixin.bean.WxAccount;
import cn.com.mjsoft.cms.weixin.bean.WxExtendBean;
import cn.com.mjsoft.cms.weixin.bean.WxMsgBean;
import cn.com.mjsoft.cms.weixin.bean.WxSendAllInfoBean;
import cn.com.mjsoft.cms.weixin.bean.item.NewsItemGroupBean;
import cn.com.mjsoft.cms.weixin.bean.item.WxNewsItemInfo;
import cn.com.mjsoft.cms.weixin.bean.menu.ButtonBean;
import cn.com.mjsoft.cms.weixin.bean.menu.HavaChildButtonBean;
import cn.com.mjsoft.cms.weixin.bean.menu.WxMenuBean;
import cn.com.mjsoft.cms.weixin.bean.res.ArticleRes;
import cn.com.mjsoft.cms.weixin.bean.res.WxNewsResBean;
import cn.com.mjsoft.cms.weixin.bean.res.WxResourceBean;
import cn.com.mjsoft.cms.weixin.dao.WeixinDao;
import cn.com.mjsoft.cms.weixin.dao.vo.WxMenu;
import cn.com.mjsoft.cms.weixin.dao.vo.WxUser;
import cn.com.mjsoft.cms.weixin.util.MessageUtil;
import cn.com.mjsoft.cms.weixin.util.message.resp.Article;
import cn.com.mjsoft.cms.weixin.util.message.resp.Image;
import cn.com.mjsoft.cms.weixin.util.message.resp.ImageMessage;
import cn.com.mjsoft.cms.weixin.util.message.resp.Music;
import cn.com.mjsoft.cms.weixin.util.message.resp.MusicMessage;
import cn.com.mjsoft.cms.weixin.util.message.resp.NewsMessage;
import cn.com.mjsoft.cms.weixin.util.message.resp.TextMessage;
import cn.com.mjsoft.cms.weixin.util.message.resp.Video;
import cn.com.mjsoft.cms.weixin.util.message.resp.VideoMessage;
import cn.com.mjsoft.cms.weixin.util.message.resp.Voice;
import cn.com.mjsoft.cms.weixin.util.message.resp.VoiceMessage;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.cache.Cache;

import cn.com.mjsoft.framework.config.SystemRuntimeConfig;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.ObjectUtility;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings( "unchecked" )
public class WeixinService
{
    private static Logger log = Logger.getLogger( WeixinService.class );

    public static ClusterMapAdapter API_TOKEN = new ClusterMapAdapter( "weixinService.API_TOKEN",
        String.class, String.class );

    private static ClusterMapAdapter tokenCache = new ClusterMapAdapter(
        "weixinService.tokenCache", Long.class, String.class );

    private static Cache waCache = new ClusterCacheAdapter( 100, "weixinService.waCache" );

    private static Cache menuCache = new ClusterCacheAdapter( 500, "weixinService.menuCache" );

    private static Cache wxResCache = new ClusterCacheAdapter( 5000, "weixinService.wxResCache" );

    private static Cache wxMsgCache = new ClusterCacheAdapter( 2000, "weixinService.wxMsgCache" );

    private static WeixinService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    private static ResourcesService resService = ResourcesService.getInstance();

    private WeixinDao wxDao;

    private ContentDao contentDao;

    private MetaDataDao metaDataDao;

    private WeixinService()
    {
        wxDao = new WeixinDao( mysqlEngine );

        contentDao = new ContentDao( mysqlEngine );

        metaDataDao = new MetaDataDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new WeixinService();
        }
    }

    public static WeixinService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public String configWeixin( WxAccount wa )
    {
        Long acId = wa.getAcId();

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( acId > 0 )
        {
            WxAccount testwao = wxDao.querySingleWxConfigByWxCode( wa.getMainId() );

            if( testwao != null && !testwao.getAppId().equals( wa.getAppId() ) )
            {
                return "-1"; // 已存在原始ID
            }

            WxAccount wao = wxDao.querySingleWxConfig( acId );

            // 更新配制
            wxDao.updateWxConfig( wa );

            if( wao != null )
            {
                // 更新wxCode
                wxDao.updateWxCode( wao.getMainId(), wa.getMainId() );
            }

            clearWaAllInfoCache();
        }
        else
        {

            wa.setSiteId( site.getSiteId() );

            // 无配制,需要增加新的
            wxDao.saveWxConfig( wa );
        }

        clearWaCache();

        initSitGroupWXApiToken();

        return "success";
    }

    public void initSitGroupWXApiToken()
    {
        List<SiteGroupBean> sg = InitSiteGroupInfoBehavior.siteGroupListCache;

        API_TOKEN.clear();

        for ( SiteGroupBean site : sg )
        {
            WxAccount wa = wxDao.querySingleWxConfigBySiteId( site.getSiteId() );

            if( wa != null )
            {
                API_TOKEN.put( site.getSiteUrl(), wa.getApiToken() );
            }

        }
    }

    public WxAccount retrieveSingleWxConfigBySiteId( Long siteId )
    {

        String key = "retrieveSingleWxConfigBySiteId" + siteId;

        WxAccount res = ( WxAccount ) waCache.getEntry( key );

        if( res == null )
        {
            res = wxDao.querySingleWxConfigBySiteId( siteId );

            if( res == null )
            {
                WxAccount wa = new WxAccount();
                wa.setMainId( "" );
                res = wa;
            }

            waCache.putEntry( key, res );
        }

        return res;

    }

    public WxAccount retrieveSingleWxConfigByWxCode( String wxCode )
    {
        String key = "retrieveSingleWxConfigByWxCode" + wxCode;

        WxAccount res = ( WxAccount ) waCache.getEntry( key );

        if( res == null )
        {
            res = wxDao.querySingleWxConfigByWxCode( wxCode );

            waCache.putEntry( key, res );
        }

        return res;

    }

    public Object getWeixinConfigForTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        return wxDao.querySingleWxConfigBySiteId( site.getSiteId() );
    }

    public List retrieveSingleWxMenuByKey( String wxCode, String key )
    {
        String ckey = "" + wxCode + "|" + key;

        List result = ( List ) menuCache.getEntry( ckey );

        if( result == null )
        {
            result = wxDao.querySingleWxMenuByKey( wxCode, key );

            menuCache.putEntry( ckey, result );
        }

        return result;
    }

    public String createNewWxMenu( WxMenu vo, String wxCode )
    {

        try
        {
            mysqlEngine.beginTransaction();

            vo.setWxCode( wxCode );

            List childMenu = null;

            vo.setBtOrder( Integer.valueOf( 0 ) );

            if( vo.getParentId().longValue() == -9999 )
            {
                vo.setBtLayer( Integer.valueOf( 1 ) );

                childMenu = wxDao.queryWxMenuByParent( wxCode, Long.valueOf( -9999 ) );

                if( childMenu.size() == 3 )
                {
                    return "-2";
                }

            }
            else
            {
                vo.setBtLayer( Integer.valueOf( 2 ) );

                childMenu = wxDao.queryWxMenuByParent( wxCode, vo.getParentId() );

                WxMenu parent = wxDao.querySingleWxMenu( wxCode, vo.getParentId() );

                if( parent == null )
                {
                    return "-1";
                }

                if( childMenu.size() == 5 )
                {
                    return "-3";
                }

            }

            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            vo.setSiteFlag( site.getSiteFlag() );

            wxDao.saveWxMenu( vo );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearMenuCache();
        }

        return "1";
    }

    public String editNewWxMenu( WxMenu vo, String wxCode )
    {

        try
        {
            mysqlEngine.beginTransaction();

            vo.setWxCode( wxCode );

            wxDao.updateWxMenu( vo );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearMenuCache();

        }

        return "1";
    }

    public String sortNewWxMenu( Map params, String wxCode )
    {

        try
        {
            mysqlEngine.beginTransaction();

            int order = -1;

            List wxList = wxDao.queryAllWxMenu( wxCode );

            WxMenu menu = null;

            for ( int i = 0; i < wxList.size(); i++ )
            {
                menu = ( WxMenu ) wxList.get( i );

                order = StringUtil.getIntValue( ( String ) params.get( "orderFlag-"
                    + menu.getBtId() ), -1 );

                if( order > 0 )
                {

                    wxDao.updateWxMenuOrder( wxCode, menu.getBtId(), order );

                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearMenuCache();

        }

        return "1";

    }

    public void deleteNewWxMenu( String wxCode, Long btId )
    {

        try
        {
            mysqlEngine.beginTransaction();

            wxDao.deleteWxMenuBtId( wxCode, btId );

            wxDao.deleteWxMenuBtParentId( wxCode, btId );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearMenuCache();
        }

    }

    public List retrieveWxExtendByWxCodeAndEventType( String wxCode, String eventType )
    {
        String ckey = "retrieveWxExtendByWxCodeAndEventType:" + wxCode + "|" + eventType;

        List res = ( List ) menuCache.getEntry( ckey );

        if( res == null )
        {
            res = wxDao.queryWxExtendByWxCodeAndEventType( wxCode, eventType );

            menuCache.putEntry( ckey, res );
        }

        return res;

    }

    public String checkWxExtendBehavior( String eventType, String wxCode )
    {
        List allExtend = wxDao.queryWxExtendByWxCode( wxCode );

        boolean exist = false;

        if( Constant.WX.EVENT_TYPE_MSG_SUBSCRIBE.equals( eventType )
            || Constant.WX.EVENT_TYPE_MSG_UNSUBSCRIBE.equals( eventType )
            || Constant.WX.EVENT_TYPE_MSG_LOCATION.equals( eventType )
            || Constant.WX.EVENT_TYPE_MSG_SCAN.equals( eventType ) )
        {
            Map ex = null;

            for ( int i = 0; i < allExtend.size(); i++ )
            {
                ex = ( Map ) allExtend.get( i );

                if( eventType.equals( ( String ) ex.get( "eventType" ) ) )
                {
                    exist = true;

                    break;
                }

            }
        }

        if( exist )
        {
            return "-2";
        }

        return "1";
    }

    public String createWxExtendBehavior( Map params )
    {
        String eventType = ( String ) params.get( "eventType" );

        if( "-1".equals( eventType ) )
        {
            return "-1";
        }

        try
        {
            mysqlEngine.beginTransaction();

            params.put( "isMsg", Constant.COMMON.OFF );
            params.put( "isInput", Constant.COMMON.OFF );
            params.put( "isMenu", Constant.COMMON.ON );
            params.put( "useStatus", Constant.COMMON.ON );

            List allExtend = wxDao.queryWxExtendByWxCode( ( String ) params.get( "wxCode" ) );

            boolean exist = false;

            if( Constant.WX.EVENT_TYPE_MSG_SUBSCRIBE.equals( eventType )
                || Constant.WX.EVENT_TYPE_MSG_UNSUBSCRIBE.equals( eventType )
                || Constant.WX.EVENT_TYPE_MSG_LOCATION.equals( eventType )
                || Constant.WX.EVENT_TYPE_MSG_SCAN.equals( eventType )

                ||

                Constant.WX.REQ_MESSAGE_TYPE_TEXT.equals( eventType )
                || Constant.WX.REQ_MESSAGE_TYPE_IMAGE.equals( eventType )
                || Constant.WX.REQ_MESSAGE_TYPE_LINK.equals( eventType )
                || Constant.WX.REQ_MESSAGE_TYPE_LOCATION.equals( eventType )
                || Constant.WX.REQ_MESSAGE_TYPE_VIDEO.equals( eventType )
                || Constant.WX.REQ_MESSAGE_TYPE_SHORT_VIDEO.equals( eventType )
                || Constant.WX.REQ_MESSAGE_TYPE_VOICE.equals( eventType ) )
            {

                if( Constant.WX.EVENT_TYPE_MSG_SUBSCRIBE.equals( eventType )
                    || Constant.WX.EVENT_TYPE_MSG_UNSUBSCRIBE.equals( eventType )
                    || Constant.WX.EVENT_TYPE_MSG_LOCATION.equals( eventType )
                    || Constant.WX.EVENT_TYPE_MSG_SCAN.equals( eventType ) )
                {
                    params.put( "isMsg", Constant.COMMON.ON );
                }
                else
                {
                    params.put( "isInput", Constant.COMMON.ON );
                }

                params.put( "isMenu", Constant.COMMON.OFF );

                Map ex = null;

                for ( int i = 0; i < allExtend.size(); i++ )
                {
                    ex = ( Map ) allExtend.get( i );

                    if( eventType.equals( ( String ) ex.get( "eventType" ) ) )
                    {
                        exist = true;

                        break;
                    }
                }
            }

            if( exist )
            {
                return "-2";
            }

            wxDao.saveNewWxExtend( params );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

        }

        return "1";
    }

    public void editWxExtendBehavior( Map params )
    {

        try
        {
            mysqlEngine.beginTransaction();

            wxDao.editWxExtend( params );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

        }

    }

    public void deleteWxExtendBehavior( Long ebid )
    {

        try
        {
            mysqlEngine.beginTransaction();

            wxDao.deleteWxExtendById( ebid );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

        }

    }

    public Object getWxExtendForTag( String ebId, String eventType, String msgType )
    {

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        Long ebIdVar = StringUtil.getLongValue( ebId, -1 );

        if( ebIdVar.longValue() > 0 )
        {
            return wxDao.querySingleWxExtendById( ebIdVar );
        }

        if( "menu".equals( msgType ) )
        {
            return wxDao.queryWxExtendByWxCodeByMenu( wxCode );
        }
        else if( "msg".equals( msgType ) )
        {
            return wxDao.queryWxExtendByWxCodeByMsg( wxCode );
        }
        else if( "input".equals( msgType ) )
        {
            return wxDao.queryWxExtendByWxCodeByInput( wxCode );
        }

        if( StringUtil.isStringNotNull( eventType ) )
        {
            return wxDao.queryWxExtendByWxCodeAndEventType( wxCode, eventType );
        }

        return wxDao.queryWxExtendByWxCode( wxCode );
    }

    public Object getWxMenuForTag( String wxCode, String pId, String btId )
    {
        // 当前只取站点配置的wx号

        Long parentId = StringUtil.getLongValue( pId, -1 );

        Long buttonId = StringUtil.getLongValue( btId, -1 );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

        wxCode = wa.getMainId();

        // Integer btLayer = Integer.valueOf( StringUtil.getIntValue( layer, -1
        // ) );

        if( buttonId.longValue() > 0 )
        {
            return wxDao.querySingleWxMenu( wxCode, buttonId );
        }
        else if( parentId.longValue() > 0 || parentId.longValue() == -9999 )
        {
            return wxDao.queryWxMenuByParent( wxCode, parentId );
        }

        return wxDao.queryAllWxMenu( wxCode );
    }

    public WxMsgBean retrieveSingleWxMessageByKey( String wxCode, String key )
    {
        String ckey = "retrieveSingleWxMessageByKey:" + wxCode + "|" + key;

        WxMsgBean res = ( WxMsgBean ) wxMsgCache.getEntry( ckey );

        if( res == null )
        {
            res = wxDao.querySingleWxMessageByKey( wxCode, key );

            wxMsgCache.putEntry( ckey, res );
        }

        return res;

    }

    public List retrieveWxMessageByIncludeMode( String wxCode, Integer status )
    {
        String ckey = "retrieveWxMessageByIncludeMode:" + wxCode + "|" + status;

        List res = ( List ) wxMsgCache.getEntry( ckey );

        if( res == null )
        {
            res = wxDao.queryWxMessageByIncludeMode( wxCode, status );

            wxMsgCache.putEntry( ckey, res );
        }

        return res;

    }

    public Map retrieveWxUnkMessageByMsgType( String msgType, String wxCode )
    {
        String ckey = "retrieveWxUnkMessageByMsgType:" + wxCode + "|" + msgType;

        Map res = ( Map ) wxMsgCache.getEntry( ckey );

        if( res == null )
        {
            res = wxDao.queryWxUnkMessageByMsgType( msgType, wxCode );

            wxMsgCache.putEntry( ckey, res );
        }

        return res;

    }

    public void createWxMessage( Map params )
    {
        String msgTitle = ( String ) params.get( "msgTitle" );

        String type = "";

        if( Constant.COMMON.ON.toString().equals( ( String ) params.get( "isText" ) ) )
        {
            type = "内部文本";

            params.put( "infoId", -1 );
            params.put( "resId", -1 );

        }
        else if( msgTitle.startsWith( "[图文" ) )
        {
            type = "图文";

            params.put( "resId", -1 );
        }
        else if( msgTitle.startsWith( "[文本" ) )
        {
            type = "文本";

            params.put( "infoId", -1 );
        }
        else if( msgTitle.startsWith( "[图片" ) )
        {
            type = "图片";

            params.put( "infoId", -1 );
        }
        else if( msgTitle.startsWith( "[视频" ) )
        {
            type = "视频";

            params.put( "infoId", -1 );
        }
        else if( msgTitle.startsWith( "[语音" ) )
        {
            type = "语音";

            params.put( "infoId", -1 );
        }
        else if( msgTitle.startsWith( "[音乐" ) )
        {
            type = "音乐";

            params.put( "infoId", -1 );
        }

        params.put( "msgType", type );

        wxDao.saveWxMessage( params );

        clearWxMsgCache();
    }

    public void editWxMessage( Map params )
    {
        String msgTitle = ( String ) params.get( "msgTitle" );

        String type = "";

        if( Constant.COMMON.ON.toString().equals( ( String ) params.get( "isText" ) ) )
        {
            type = "内部文本";

            params.put( "infoId", -1 );
            params.put( "resId", -1 );

        }
        else if( msgTitle.startsWith( "[图文" ) )
        {
            type = "图文";

            params.put( "resId", -1 );
        }
        else if( msgTitle.startsWith( "[文本" ) )
        {
            type = "文本";

            params.put( "infoId", -1 );
        }
        else if( msgTitle.startsWith( "[图片" ) )
        {
            type = "图片";

            params.put( "infoId", -1 );
        }
        else if( msgTitle.startsWith( "[视频" ) )
        {
            type = "视频";

            params.put( "infoId", -1 );
        }
        else if( msgTitle.startsWith( "[语音" ) )
        {
            type = "语音";

            params.put( "infoId", -1 );
        }
        else if( msgTitle.startsWith( "[音乐" ) )
        {
            type = "音乐";

            params.put( "infoId", -1 );
        }

        params.put( "msgType", type );

        wxDao.editWxMessage( params );

        clearWxMsgCache();
    }

    public void editWxTextMessage( Long msgId, String wxCode, String text )
    {
        wxDao.updateWxTextMsg( msgId, wxCode, text );

        clearWxMsgCache();
    }

    public void deleteWxMessage( List idList, String wxCode )
    {

        try
        {
            mysqlEngine.beginTransaction();

            long id = -1;

            for ( int i = 0; i < idList.size(); i++ )
            {
                id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );

                if( id < 0 )
                {
                    continue;
                }

                wxDao.deleteWxMessage( id, wxCode );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearWxMsgCache();
        }
    }

    public Object getWxMessageForTag( String msgIdVar, String key, String pn, String size )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        Long msgId = Long.valueOf( StringUtil.getLongValue( msgIdVar, -1 ) );

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        Long count = null;

        List result = null;

        if( msgId.longValue() > 0 )
        {
            return wxDao.querySingleWxMessage( wxCode, msgId );
        }
        else if( StringUtil.isStringNotNull( key ) )
        {
            result = new ArrayList();

            WxMsgBean mb = wxDao.querySingleWxMessageByKey( wxCode, SystemSafeCharUtil
                .decodeFromWeb( key.trim() ) );

            if( mb != null )
            {
                result.add( mb );
            }

            return result;
        }
        else
        {
            count = wxDao.queryWxMessageCount( wxCode );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = wxDao.queryWxMessage( wxCode, Long.valueOf( pageInfo.getFirstResult() ),
                Integer.valueOf( pageSize ) );

            return new Object[] { result, pageInfo };
        }

    }

    public Object getWxUnkMessageForTag( String msgType )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        if( StringUtil.isStringNotNull( msgType ) )
        {

            Map unkm = wxDao.queryWxUnkMessageByMsgType( msgType, wxCode );

            return unkm;
        }
        else
        {
            List unkmList = wxDao.queryWxUnkMessage( wxCode );

            return unkmList;
        }

    }

    public boolean createWxUnkMessage( Map params )
    {
        try
        {
            mysqlEngine.beginTransaction();

            String msgType = ( String ) params.get( "msgType" );

            String wxCode = ( String ) params.get( "wxCode" );

            Map test = wxDao.queryWxUnkMessageByMsgType( msgType, wxCode );

            if( !test.isEmpty() )
            {
                return false;
            }

            wxDao.saveWxUnkMessage( params );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearWxMsgCache();

        }

        return true;
    }

    public void editWxUnkMessage( Map params )
    {
        wxDao.updateWxUnkMessage( params );

        clearWxMsgCache();
    }

    public void deleteWxUnkMessage( String msgType, String wxCode )
    {
        wxDao.deleteWxUnkMessage( msgType, wxCode );

        clearWxMsgCache();
    }

    public List retrieveContentInfoByIds( List cidArrayList )
    {
        List result = new ArrayList();

        long contentId = -1;

        for ( int i = 0; i < cidArrayList.size(); i++ )
        {
            contentId = StringUtil.getLongValue( ( String ) cidArrayList.get( i ), -1 );

            if( contentId < 0 )
            {
                continue;
            }

            DataModelBean modelBean = metaDataDao.querySingleDataModelBeanById( contentDao
                .queryContentMainInfoModelIdByCid( contentId ) );

            if( modelBean == null )
            {
                continue;
            }

            ModelPersistenceMySqlCodeBean posSqlCodeBean = metaDataService
                .retrieveSingleModelPerMysqlCodeBean( modelBean.getDataModelId() );

            if( posSqlCodeBean == null )
            {
                continue;
            }

            Map info = contentDao.querySingleUserDefineContent( posSqlCodeBean, modelBean
                .getRelateTableName(), contentId );

            String efn = modelBean.getMainEditorFieldSign();

            String text = ( String ) info.get( efn );

            info.put( "__sys_editor_text__", text );

            result.add( info );
        }

        return result;
    }

    public void addSingleWxNewsInfo( WxNewsItemInfo news, Long rowFlag, Integer rowIndex,
        boolean inCol )
    {
        if( news == null || rowFlag == null || rowFlag == null || rowIndex == null )
        {
            return;
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        Long wxId = Long.valueOf( 1 );

        news.setWxCode( wxCode );

        news.setResType( Constant.WX.RESP_MESSAGE_TYPE_NEWS );

        news.setTypeFlag( Constant.WX.RESP_MESSAGE_TYPE_NEWS );

        try
        {
            mysqlEngine.beginTransaction();

            // 编辑器内容处理
            disposeEditor( news );

            if( inCol )// 在某一行中增加
            {
                // 获取增加新内容之前行最大order
                Integer maxOrder = wxDao.queryWxNewsItemMaxRowOrderByRowFlag( rowFlag, wxId );

                if( maxOrder == null )
                {
                    // 表示加入了新的最后一行
                    maxOrder = Integer.valueOf( 0 );
                }

                // if( rowIndex.intValue() == -1 )
                {
                    rowIndex = Integer.valueOf( maxOrder.intValue() + 1 );
                }

                Integer pos = Integer.valueOf( ( maxOrder.intValue() + 2 ) - rowIndex.intValue() );

                // 增加新的info
                news.setRowFlag( rowFlag );
                news.setRowOrder( pos );
                news.setAddTime( DateAndTimeUtil.getTodayTimestampDayAndTime() );

                // 图片信息处理
                news.setImg( ServiceUtil.disposeSingleImageInfo( Long.valueOf( StringUtil
                    .getLongValue( news.getImg(), -1 ) ) ) );

                news.setCommendFlag( "wxnews" );
                news.setCommendTypeId( Long.valueOf( 1 ) );

                news.setCreateTime( DateAndTimeUtil.getTodayTimestampDayAndTime() );
                news.setIsTranSucc( Constant.COMMON.OFF );

                UpdateState us = wxDao.saveNewsItem( news );

                if( us.haveKey() && maxOrder.intValue() > 0 )
                {
                    wxDao.updateNewsItemRowOrder( news.getCommendFlag(), rowFlag, pos, Long
                        .valueOf( us.getKey() ) );
                }
            }
            else
            {

                List rowBeanAllList = wxDao.queryAllNewsItemByWxId( site.getSiteFlag(), false );

                // 增加新的行,新的info
                news.setRowFlag( Long.valueOf( 1 ) );
                news.setRowOrder( Integer.valueOf( 1 ) );
                news.setAddTime( DateAndTimeUtil.getTodayTimestampDayAndTime() );

                // 图片信息处理
                news.setImg( ServiceUtil.disposeSingleImageInfo( Long.valueOf( StringUtil
                    .getLongValue( news.getImg(), -1 ) ) ) );

                news.setCommendFlag( "wxnews" );
                news.setCommendTypeId( Long.valueOf( 1 ) );
                news.setIsTranSucc( Constant.COMMON.OFF );

                UpdateState us = wxDao.saveNewsItem( news );

                if( us.haveKey() )
                {
                    NewsItemGroupBean rowInfoBean = null;
                    List rowBeanInnerList = null;
                    int num = 0;

                    for ( int i = 0; i < rowBeanAllList.size(); i++ )
                    {
                        num = i + 1;

                        rowInfoBean = ( NewsItemGroupBean ) rowBeanAllList.get( i );

                        if( rowInfoBean.getRowFlag().intValue() < rowFlag.intValue() )// 小于新行的无需更新,大于等于新行的需要重新编号
                        {
                            continue;
                        }

                        wxDao.updateNewsItemRowFlagByInfoId( Long.valueOf( num + 1 ), rowInfoBean
                            .getInfoId() );

                        rowBeanInnerList = rowInfoBean.getRowInfoList();
                        for ( int j = 0; j < rowBeanInnerList.size(); j++ )
                        {
                            rowInfoBean = ( NewsItemGroupBean ) rowBeanInnerList.get( j );
                            wxDao.updateNewsItemRowFlagByInfoId( Long.valueOf( num + 1 ),
                                rowInfoBean.getInfoId() );
                        }
                    }
                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearWxResCache();
        }
    }

    public void editSingleNewsItemInfo( WxNewsItemInfo news )
    {
        try
        {
            mysqlEngine.beginTransaction();

            // 图片信息处理
            news.setImg( ServiceUtil.disposeSingleImageInfo( Long.valueOf( StringUtil.getLongValue(
                news.getImg(), -1 ) ) ) );

            // 编辑器内容处理
            disposeEditor( news );

            wxDao.updateNewsItemInfoByInfoId( news );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearWxResCache();
        }

    }

    private String disposeEditor( WxNewsItemInfo news )
    {
        /**
         * 处理所有编辑器的资源文件，更新为已使用
         */

        String text = news.getArticleText();

        if( StringUtil.isStringNotNull( text ) )
        {
            Document doc = Jsoup.parse( text );

            Iterator eles = doc.getAllElements().iterator();

            Element ele = null;

            String id = null;

            Set currentIdSet = new HashSet();

            String oldText = null;

            SiteResourceBean imgResBean = null;

            while ( eles.hasNext() )
            {
                ele = ( Element ) eles.next();

                id = ele.id();

                if( id.startsWith( "jtopcms_content_image_" ) )
                {
                    id = StringUtil.replaceString( id, "jtopcms_content_image_", "", false, false );

                    // 水印添加
                    imgResBean = resService.retrieveSingleResourceBeanByResId( Long
                        .valueOf( StringUtil.getLongValue( id, -1 ) ) );

                    // 站点开启水印且图片信息存在才会添加

                    if( imgResBean != null )
                    {
                        String reUrl = imgResBean.getResSource();

                        // 已经加过水印的不需要再增加,此处代码和其他地方不一样，不需要再次读取resbean
                        if( !Constant.COMMON.ON.equals( imgResBean.getHaveMark() ) )
                        {
                            if( ServiceUtil.disposeImageMark(
                                ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                    .getEntry( imgResBean.getSiteId() ), reUrl, imgResBean
                                    .getWidth(), imgResBean.getHeight() ) )
                            {
                                // 成功加水印则更新
                                resService.setImageMarkStatus( reUrl, Constant.COMMON.ON );
                            }
                        }
                    }
                }
                else if( id.startsWith( "jtopcms_content_media_" ) )
                {
                    id = StringUtil.replaceString( id, "jtopcms_content_media_", "", false, false );
                }
                else if( id.startsWith( "jtopcms_content_file_" ) )
                {
                    id = StringUtil.replaceString( id, "jtopcms_content_file_", "", false, false );
                }

                resService.updateSiteResourceTraceUseStatus( Long.valueOf( StringUtil.getLongValue(
                    id, -1 ) ), Constant.COMMON.ON );

                currentIdSet.add( id );

            }

            // 处理原内容资源

            NewsItemGroupBean on = wxDao.querySingleNewsItemInfoByInfoId( news.getInfoId() );

            // 存在原始记录
            if( on != null )
            {
                oldText = on.getArticleText();

                if( StringUtil.isStringNotNull( oldText ) )
                {

                    doc = Jsoup.parse( oldText );

                    eles = doc.getAllElements().iterator();

                    ele = null;

                    id = null;

                    while ( eles.hasNext() )
                    {
                        ele = ( Element ) eles.next();

                        id = ele.id();

                        if( id.startsWith( "jtopcms_content_image_" ) )
                        {
                            id = StringUtil.replaceString( id, "jtopcms_content_image_", "", false,
                                false );
                        }
                        else if( id.startsWith( "jtopcms_content_media_" ) )
                        {
                            id = StringUtil.replaceString( id, "jtopcms_content_media_", "", false,
                                false );
                        }
                        else if( id.startsWith( "jtopcms_content_file_" ) )
                        {
                            id = StringUtil.replaceString( id, "jtopcms_content_file_", "", false,
                                false );
                        }

                        if( !currentIdSet.contains( id ) )
                        {
                            resService.updateSiteResourceTraceUseStatus( Long.valueOf( StringUtil
                                .getLongValue( id, -1 ) ), Constant.COMMON.OFF );
                        }

                    }

                }

            }

        }
        else
        {
            Document doc = Jsoup.parse( text );

            Iterator eles = doc.getAllElements().iterator();

            Element ele = null;

            String id = null;

            Set currentIdSet = new HashSet();

            String oldText = null;

            // 处理原内容资源

            NewsItemGroupBean on = wxDao.querySingleNewsItemInfoByInfoId( news.getInfoId() );

            // 存在原始记录
            if( on != null )
            {
                oldText = on.getArticleText();

                if( StringUtil.isStringNotNull( oldText ) )
                {

                    doc = Jsoup.parse( text );

                    eles = doc.getAllElements().iterator();

                    ele = null;

                    id = null;

                    while ( eles.hasNext() )
                    {
                        ele = ( Element ) eles.next();

                        id = ele.id();

                        if( id.startsWith( "jtopcms_content_image_" ) )
                        {
                            id = StringUtil.replaceString( id, "jtopcms_content_image_", "", false,
                                false );
                        }
                        else if( id.startsWith( "jtopcms_content_media_" ) )
                        {
                            id = StringUtil.replaceString( id, "jtopcms_content_media_", "", false,
                                false );
                        }
                        else if( id.startsWith( "jtopcms_content_file_" ) )
                        {
                            id = StringUtil.replaceString( id, "jtopcms_content_file_", "", false,
                                false );
                        }

                        if( !currentIdSet.contains( id ) )
                        {
                            resService.updateSiteResourceTraceUseStatus( Long.valueOf( StringUtil
                                .getLongValue( id, -1 ) ), Constant.COMMON.OFF );
                        }

                    }

                }
            }
        }

        return text;

    }

    public void deleteNewsItemInfo( Long rowFlag, List deleteInfoIdArrayList, String siteFlag )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        try
        {
            mysqlEngine.beginTransaction();

            List rowInfoExcludeDeleteIdList = wxDao.queryNewsItemByRowFlagAndExcludeId( siteFlag,
                rowFlag, deleteInfoIdArrayList );

            // 删除info
            Long infoId = null;

            NewsItemGroupBean bean = null;

            for ( int i = 0; i < deleteInfoIdArrayList.size(); i++ )
            {
                infoId = Long.valueOf( StringUtil.getLongValue( ( String ) deleteInfoIdArrayList
                    .get( i ), -1 ) );

                if( infoId.longValue() > 0 )
                {
                    bean = wxDao.querySingleNewsItemInfoByInfoId( infoId );

                    ServiceUtil.deleteSiteResTraceMode( Long.valueOf( StringUtil.getLongValue( bean
                        .getImgResId(), -1 ) ) );

                    wxDao.deleteNewsItemByInfoId( infoId );

                    deleteWeixinServerRes( wxCode, bean.getMediaId() );
                }

            }

            // 更新删除后的order
            NewsItemGroupBean infoBean = null;
            int num = 0;
            for ( int i = 0; i < rowInfoExcludeDeleteIdList.size(); i++ )
            {
                num = i + 1;

                infoBean = ( NewsItemGroupBean ) rowInfoExcludeDeleteIdList.get( i );

                wxDao.updateNewsItemInfoOrderInfoByInfoId( infoBean.getRowFlag(), Integer
                    .valueOf( num ), infoBean.getInfoId() );
            }

            // 若整个行数据被删除,需要调整行排序
            if( rowInfoExcludeDeleteIdList.size() == 0 )
            {
                List allRowInfo = wxDao.queryAllNewsItemByWxId( siteFlag, false );

                List rowList = null;
                num = 0;
                for ( int i = 0; i < allRowInfo.size(); i++ )
                {

                    infoBean = ( NewsItemGroupBean ) allRowInfo.get( i );

                    if( infoBean.getRowFlag().longValue() != rowFlag.longValue() )
                    {
                        num++;

                        wxDao.updateNewsItemInfoOrderInfoByInfoId( Long.valueOf( num ), infoBean
                            .getRowOrder(), infoBean.getInfoId() );

                        rowList = infoBean.getRowInfoList();

                        for ( int j = 0; j < rowList.size(); j++ )
                        {
                            infoBean = ( NewsItemGroupBean ) rowList.get( j );

                            wxDao.updateNewsItemInfoOrderInfoByInfoId( Long.valueOf( num ),
                                infoBean.getRowOrder(), infoBean.getInfoId() );
                        }
                    }
                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearWxResCache();
        }
    }

    public void deleteAllNewsItemInfo( List rowFlagList )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        try
        {
            mysqlEngine.beginTransaction();

            Long rowFlag = null;

            for ( int i = 0; i < rowFlagList.size(); i++ )
            {
                rowFlag = StringUtil.getLongValue( ( String ) rowFlagList.get( i ), -1 );

                List allNews = wxDao.queryNewsItemGroupByRowFlagAndWxCode( rowFlag, wxCode );

                NewsItemGroupBean bean = null;

                for ( int j = 0; j < allNews.size(); j++ )
                {

                    bean = ( NewsItemGroupBean ) allNews.get( j );

                    ServiceUtil.deleteSiteResTraceMode( Long.valueOf( StringUtil.getLongValue( bean
                        .getImgResId(), -1 ) ) );

                    wxDao.deleteNewsItemByInfoId( bean.getInfoId() );

                    deleteWeixinServerRes( wxCode, bean.getMediaId() );

                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearWxResCache();
        }
    }

    public List retrieveNewsItemGroup( Long rowFlag, String wxCode )
    {
        String key = "retrieveNewsItemGroup:" + rowFlag + "|" + wxCode;

        List res = ( List ) wxResCache.getEntry( key );

        if( res == null )
        {
            res = wxDao.queryNewsItemGroupByRowFlagAndWxCode( rowFlag, wxCode );

            wxResCache.putEntry( key, res );
        }
        return res;
    }

    public NewsItemGroupBean retrieveSingleNewsItemGroup( Long infoId )
    {
        String key = "retrieveSingleNewsItemGroup:" + infoId;

        NewsItemGroupBean res = ( NewsItemGroupBean ) wxResCache.getEntry( key );

        if( res == null )
        {
            res = wxDao.querySingleNewsItemInfoByInfoId( infoId );

            wxResCache.putEntry( key, res );
        }

        return res;
    }

    public Object getNewsItemForTag( String resTag, String siteFlag, String pageSize,
        String pageNumber, String newsId )
    {
        Long nId = StringUtil.getLongValue( newsId, -1 );

        if( nId.longValue() > 0 )
        {
            return wxDao.querySingleNewsItemInfoByInfoId( nId );
        }
        else
        {
            if( StringUtil.isStringNull( siteFlag ) )
            {
                return null;
            }

            resTag = SystemSafeCharUtil.decodeFromWeb( resTag );

            int pn = StringUtil.getIntValue( pageNumber, 1 );

            int size = StringUtil.getIntValue( pageSize, 15 );

            Page pageInfo = null;

            Integer count = null;

            List result = null;

            if( StringUtil.isStringNull( resTag ) )
            {
                count = wxDao.queryAllNewsItemCountBySite( siteFlag );

                pageInfo = new Page( size, count, pn );

                result = wxDao.queryAllNewsItemBySite( siteFlag, Long.valueOf( pageInfo
                    .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ), false );
            }
            else
            {
                count = wxDao.queryAllNewsItemCountBySite( resTag, siteFlag );

                pageInfo = new Page( size, count, pn );

                result = wxDao.queryAllNewsItemBySite( resTag, siteFlag, Long.valueOf( pageInfo
                    .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ), false );
            }

            return new Object[] { result, pageInfo };
        }

    }

    public Object getNewsItemGroupForTag( String siteFlag, String rowFlag )
    {

        Long rf = StringUtil.getLongValue( rowFlag, -1 );

        return wxDao.queryNewsItemGroupByRowFlag( rf, siteFlag );

    }

    public WxResourceBean retrieveSingleWxRes( String wxCode, Long wrId )
    {
        String key = "retrieveSingleWxRes:" + wrId + "|" + wxCode;

        WxResourceBean res = ( WxResourceBean ) wxResCache.getEntry( key );

        // if( res == null )
        {
            res = wxDao.querySingleWxRes( wxCode, wrId );

            wxResCache.putEntry( key, res );
        }

        return res;
    }

    public void addImageRes( Map params )
    {
        Long imResId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "imageRes" ),
            -1 ) );

        try
        {
            mysqlEngine.beginTransaction();

            params.put( "imageRes", ServiceUtil.disposeSingleImageInfo( imResId ) );
            params.put( "resType", Constant.WX.RESP_MESSAGE_TYPE_IMAGE );
            params.put( "isTranSucc", Constant.COMMON.OFF );

            SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( imResId );

            if( resBean != null )
            {
                params.put( "resTitle", resBean.getResName() );
            }

            wxDao.saveWxImageRes( params );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearWxResCache();
        }
    }

    public void editImageRes( Map params )
    {
        Long imResId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "imageRes" ),
            -1 ) );

        try
        {
            mysqlEngine.beginTransaction();

            ServiceUtil.disposeOldImageInfo( imResId, "imageRes", params );

            params.put( "imageRes", ServiceUtil.disposeSingleImageInfo( imResId ) );

            params.put( "isTranSucc", Constant.COMMON.OFF );

            SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( imResId );

            if( resBean != null )
            {
                params.put( "resTitle", resBean.getResName() );
            }

            wxDao.updateWxImageRes( params );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearWxResCache();
        }

    }

    public void deleteImageRes( List idList, String wxCode )
    {

        try
        {
            mysqlEngine.beginTransaction();

            long id = -1;

            WxResourceBean res = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );

                if( id < 0 )
                {
                    continue;
                }

                res = wxDao.querySingleWxRes( wxCode, id );

                resService.updateSiteResourceTraceUseStatus( ServiceUtil.getResId( res
                    .getImageRes() ), Constant.COMMON.OFF );

                wxDao.deleteWxResRes( id, wxCode );

                deleteWeixinServerRes( wxCode, res.getMediaId() );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearWxResCache();
        }
    }

    public void addVideoRes( Map params )
    {

        String videoRes = ServiceUtil.disposeSingleVideoInfo( params, "wx_sp_file" );

        params.put( "videoRes", videoRes );

        params.put( "resType", Constant.WX.RESP_MESSAGE_TYPE_VIDEO );
        params.put( "isTranSucc", Constant.COMMON.OFF );

        wxDao.saveWxVideoRes( params );

        clearWxResCache();
    }

    public void editVideoRes( Map params )
    {
        String videoRes = ServiceUtil.disposeSingleVideoInfo( params, "wx_sp_file" );

        params.put( "videoRes", videoRes );

        params.put( "isTranSucc", Constant.COMMON.OFF );

        wxDao.updateWxVideoRes( params );

        clearWxResCache();
    }

    public void addVoiceRes( Map params )
    {

        String videoRes = ServiceUtil.disposeSingleVideoInfo( params, "wx_sp_file" );

        params.put( "voiceRes", videoRes );

        params.put( "resType", Constant.WX.RESP_MESSAGE_TYPE_VOICE );
        params.put( "isTranSucc", Constant.COMMON.OFF );

        wxDao.saveWxVoiceRes( params );

        clearWxResCache();
    }

    public void editVoiceRes( Map params )
    {
        String videoRes = ServiceUtil.disposeSingleVideoInfo( params, "wx_sp_file" );

        params.put( "voiceRes", videoRes );

        params.put( "isTranSucc", Constant.COMMON.OFF );

        wxDao.updateWxVoiceRes( params );

        clearWxResCache();
    }

    public void addMusicRes( Map params )
    {

        String musicRes = ServiceUtil.disposeSingleVideoInfo( params, "wx_sp_file" );

        SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( ServiceUtil
            .getResId( musicRes ) );

        if( resBean != null )
        {
            params.put( "musicUrl", resBean.getUrl() );
            params.put( "hqMusicUrl", resBean.getUrl() );
        }

        params.put( "musicRes", musicRes );

        Long imResId = Long.valueOf( StringUtil.getLongValue(
            ( String ) params.get( "musicThumb" ), -1 ) );

        params.put( "musicThumb", ServiceUtil.disposeSingleImageInfo( imResId ) );

        params.put( "resType", Constant.WX.RESP_MESSAGE_TYPE_MUSIC );
        params.put( "isTranSucc", Constant.COMMON.OFF );

        wxDao.saveWxMusicRes( params );

        clearWxResCache();
    }

    public void editMusicRes( Map params )
    {
        String musicRes = ServiceUtil.disposeSingleVideoInfo( params, "wx_sp_file" );

        SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( ServiceUtil
            .getResId( musicRes ) );

        if( resBean != null )
        {
            params.put( "musicUrl", resBean.getUrl() );
            params.put( "hqMusicUrl", resBean.getUrl() );
        }

        params.put( "musicRes", musicRes );

        Long imResId = Long.valueOf( StringUtil.getLongValue(
            ( String ) params.get( "musicThumb" ), -1 ) );

        params.put( "musicThumb", ServiceUtil.disposeSingleImageInfo( imResId ) );

        params.put( "isTranSucc", Constant.COMMON.OFF );

        wxDao.updateWxMusicRes( params );

        clearWxResCache();
    }

    public void addTextRes( Map params )
    {

        params.put( "resType", Constant.WX.RESP_MESSAGE_TYPE_TEXT );

        wxDao.saveWxTextRes( params );

        clearWxResCache();
    }

    public void editTextRes( Map params )
    {

        wxDao.updateWxTextRes( params );

        clearWxResCache();
    }

    public void deleteVideoAndVoiceAndMusicAndTextRes( List idList, String wxCode )
    {
        try
        {
            mysqlEngine.beginTransaction();

            long id = -1;

            WxResourceBean res = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );

                if( id < 0 )
                {
                    continue;
                }

                res = wxDao.querySingleWxRes( wxCode, id );

                SiteResourceBean resBean = null;

                if( Constant.WX.RESP_MESSAGE_TYPE_VIDEO.equals( res.getResType() ) )
                {
                    resBean = resService.retrieveSingleResourceBeanByResId( ServiceUtil
                        .getResId( res.getVideoRes() ) );
                }
                else if( Constant.WX.RESP_MESSAGE_TYPE_VOICE.equals( res.getResType() ) )
                {
                    resBean = resService.retrieveSingleResourceBeanByResId( ServiceUtil
                        .getResId( res.getVoiceRes() ) );
                }
                else if( Constant.WX.RESP_MESSAGE_TYPE_MUSIC.equals( res.getResType() ) )
                {
                    resBean = resService.retrieveSingleResourceBeanByResId( ServiceUtil
                        .getResId( res.getMusicRes() ) );
                }

                if( resBean != null )
                {
                    // 更新文件使用状态
                    resService.updateSiteResourceTraceUseStatus( resBean.getResId(),
                        Constant.COMMON.OFF );

                    String cover = StringUtil.isStringNull( resBean.getCover() ) ? "" : resBean
                        .getCover();

                    if( StringUtil.isStringNotNull( resBean.getCover() ) )
                    {
                        SiteResourceBean coverResBean = resService
                            .retrieveSingleResourceBeanBySource( cover );

                        if( coverResBean != null )
                        {
                            resService.updateSiteResourceTraceUseStatus( coverResBean.getResId(),
                                Constant.COMMON.OFF );
                        }
                    }
                }

                wxDao.deleteWxResRes( id, wxCode );

                deleteWeixinServerRes( wxCode, res.getMediaId() );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            clearWxResCache();
        }

    }

    public Object getSysResForTag( String resId )
    {
        SiteResourceBean srBean = resService.retrieveSingleResourceBeanByResId( Long
            .valueOf( StringUtil.getLongValue( resId, -1 ) ) );

        return srBean;
    }

    public Object getWxResForTag( String wrId, String type, String resTag, String pn, String size )
    {
        Long wId = StringUtil.getLongValue( wrId, -1 );

        resTag = SystemSafeCharUtil.decodeFromWeb( resTag );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        if( wId.longValue() > 0 )
        {
            return wxDao.querySingleWxRes( wxCode, wId );
        }
        else
        {
            int pageNum = StringUtil.getIntValue( pn, 1 );

            int pageSize = StringUtil.getIntValue( size, 10 );

            Page pageInfo = null;

            Long count = null;

            List result = null;

            if( StringUtil.isStringNull( resTag ) )
            {
                count = wxDao.queryAllWxImageResCount( wxCode, type );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = wxDao.queryAllWxImageRes( wxCode, type, Long.valueOf( pageInfo
                    .getFirstResult() ), Integer.valueOf( pageSize ) );

                return new Object[] { result, pageInfo };
            }
            else
            {
                count = wxDao.queryWxImageResByTagCount( wxCode, type, resTag );

                pageInfo = new Page( pageSize, count.intValue(), pageNum );

                result = wxDao.queryWxImageResByTag( wxCode, type, resTag, Long.valueOf( pageInfo
                    .getFirstResult() ), Integer.valueOf( pageSize ) );

                return new Object[] { result, pageInfo };
            }
        }

    }

    public void addResTag( String tagName, String wxCode, String resType )
    {
        wxDao.saveResTag( tagName, resType, wxCode );
    }

    public void editResTag( Long rtId, String tagName, String wxCode )
    {
        wxDao.updateResTag( rtId, tagName, wxCode );
    }

    public void deleteResTag( Long rtId, String wxCode )
    {
        wxDao.deleteResTag( rtId, wxCode );
    }

    public Object getResTagForTag( String resType )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        return wxDao.queryResTagByType( wxCode, resType );

    }

    public void addSendAllInfo( WxSendAllInfoBean wsa )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

        wsa.setExeMan( ( String ) auth.getApellation() );

        wsa.setExeTime( new Date( DateAndTimeUtil.clusterTimeMillis() ) );

        wsa.setIsSend( Constant.COMMON.OFF );

        wsa.setWxCode( wa.getMainId() );

        wsa.setCensor( Integer.valueOf( -1 ) );

        wxDao.saveWxSendAllInfo( wsa );
    }

    public String censorSendAllInfo( Long saId, Integer censor )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

        WxSendAllInfoBean wsa = wxDao.querySingleWxSendAllInfo( saId );

        if( Constant.COMMON.ON.equals( wsa.getIsSend() ) )
        {
            return "sendover";
        }

        wxDao.updateWxSendAllInfoCensor( saId, censor, wa.getMainId() );

        /**
         * 群发业务
         */

        if( Constant.COMMON.ON.equals( censor ) )
        {
            Date sendDT = wsa.getSendDT();

            Date now = new Date( DateAndTimeUtil.clusterTimeMillis() );

            int sec = DateAndTimeUtil.getSecInterval( sendDT, now );

            if( sec <= 0 )
            {
                String flag = sendWeixinContent( wa.getMainId(), null, "false", "", null, wsa );

                if( flag.startsWith( "error" ) || flag.startsWith( "{\"errcode\":" ) )
                {
                    return flag;
                }
            }
        }

        return "success";
    }

    public void sendAllToWeinxinServer()
    {
        List<SiteGroupBean> siteList = InitSiteGroupInfoBehavior.siteGroupListCache;

        for ( SiteGroupBean site : siteList )
        {
            WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

            if( wa == null )
            {
                continue;
            }

            List needSend = wxDao.queryWxNeedSendAllInfoByCensor( wa.getMainId(), new Date(
                DateAndTimeUtil.clusterTimeMillis() ) );

            WxSendAllInfoBean wab = null;

            for ( int i = 0; i < needSend.size(); i++ )
            {
                wab = ( WxSendAllInfoBean ) needSend.get( i );

                sendWeixinContent( wa.getMainId(), wab.getMsgId(), "false", "", wab
                    .getMsgTypeFlag(), wab );
            }
        }
    }

    public Object getSendAllInfoForTag( String saId, String censor, String exeMan,
        String pageNumber, String pageSize )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        Long saIdVal = StringUtil.getLongValue( saId, -1 );

        if( saIdVal.longValue() > 0 )
        {
            return wxDao.querySingleWxSendAllInfoCount( saIdVal );
        }

        Integer censorVal = StringUtil.getIntValue( censor, -9999 );

        int pn = StringUtil.getIntValue( pageNumber, 1 );

        int size = StringUtil.getIntValue( pageSize, 15 );

        Page pageInfo = null;

        Long count = null;

        List result = null;

        if( StringUtil.isStringNull( censor ) && StringUtil.isStringNull( exeMan ) )
        {
            count = wxDao.queryWxSendAllInfoCount( wxCode );

            pageInfo = new Page( size, count.intValue(), pn );

            result = wxDao.queryWxSendAllInfo( wxCode, Long.valueOf( pageInfo.getFirstResult() ),
                Integer.valueOf( pageInfo.getPageSize() ) );
        }
        // else if( "9999".equals( censor ) && StringUtil.isStringNotNull(
        // exeMan ) )
        // {
        // count = wxDao.queryWxSendAllInfoByIsSendCount( wxCode, exeMan );
        //
        // pageInfo = new Page( size, count.intValue(), pn );
        //
        // result = wxDao.queryWxSendAllInfoByIsSend( wxCode, exeMan, Long
        // .valueOf( pageInfo.getFirstResult() ), Integer
        // .valueOf( pageInfo.getPageSize() ) );
        // }
        // else if( "9999".equals( censor ) && StringUtil.isStringNull( exeMan )
        // )
        // {
        // count = wxDao.queryWxSendAllInfoByIsSendCount( wxCode );
        //
        // pageInfo = new Page( size, count.intValue(), pn );
        //
        // result = wxDao.queryWxSendAllInfoByIsSend( wxCode, Long
        // .valueOf( pageInfo.getFirstResult() ), Integer
        // .valueOf( pageInfo.getPageSize() ) );
        // }
        else if( StringUtil.isStringNotNull( censor ) && StringUtil.isStringNull( exeMan ) )
        {
            count = wxDao.queryWxSendAllInfoCount( wxCode, censorVal );

            pageInfo = new Page( size, count.intValue(), pn );

            result = wxDao.queryWxSendAllInfo( wxCode, censorVal, Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );
        }
        else if( StringUtil.isStringNull( censor ) && StringUtil.isStringNotNull( exeMan ) )
        {
            count = wxDao.queryWxSendAllInfoCount( wxCode, exeMan );

            pageInfo = new Page( size, count.intValue(), pn );

            result = wxDao.queryWxSendAllInfo( wxCode, exeMan, Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );
        }
        else if( "9999".equals( censor ) && StringUtil.isStringNotNull( exeMan ) )
        {
            count = wxDao.queryWxSendAllInfoByIsSendCount( wxCode, exeMan );

            pageInfo = new Page( size, count.intValue(), pn );

            result = wxDao.queryWxSendAllInfo( wxCode, exeMan, Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );
        }
        else if( "9999".equals( censor ) && StringUtil.isStringNull( exeMan ) )
        {
            count = wxDao.queryWxSendAllInfoByIsSendCount( wxCode );

            pageInfo = new Page( size, count.intValue(), pn );

            result = wxDao.queryWxSendAllInfo( wxCode, Long.valueOf( pageInfo.getFirstResult() ),
                Integer.valueOf( pageInfo.getPageSize() ) );
        }
        else if( StringUtil.isStringNotNull( censor ) && StringUtil.isStringNotNull( exeMan ) )
        {
            count = wxDao.queryWxSendAllInfoCount( wxCode, exeMan, censorVal );

            pageInfo = new Page( size, count.intValue(), pn );

            result = wxDao.queryWxSendAllInfo( wxCode, exeMan, censorVal, Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageInfo.getPageSize() ) );
        }

        return new Object[] { result, pageInfo };
    }

    /**
     * **********************以下为weixin接口业务***********************
     */

    public String getAccessToken( String clientId, String clientKey, Long siteId )
    {
        if( siteId == null )
        {
            return null;
        }

        String ex_a_t = ( String ) tokenCache.get( siteId );

        if( StringUtil.isStringNull( ex_a_t ) )
        {
            JSONObject jn = null;

            String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + clientId + "&secret=" + clientKey;

            ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

            String backStr = ServiceUtil.readStream( ServiceUtil.doGETMethodRequest( tokenUrl ),
                "UTF-8" );

            log.info( "token info:" + backStr );

            if( StringUtil.isStringNull( backStr ) )
            {
                log.error( "获取token失败 :" + backStr );
                
                return null;
            }

            jn = JSON.parseObject( backStr );

            String access_token = jn.getString( "access_token" );

            if( StringUtil.isStringNull( access_token ) )
            {
                log.error( "获取token失败 :" + backStr );
                
                return null;
            }

            tokenCache.put( siteId, access_token );

            ex_a_t = access_token;
        }

        return ex_a_t;
    }

    public String transferWeixinMenu( String wxCode )
    {

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = null;

        if( site.getSiteId() < 0 )
        {
            wa = retrieveSingleWxConfigByWxCode( wxCode );

        }
        else
        {
            wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );
        }

        String clientId = wa.getAppId();

        String clientKey = wa.getAppsSecret();

        String ex_a_t = getAccessToken( clientId, clientKey, site.getSiteId() );

        // 组合menu

        ButtonBean[] threeMain = new ButtonBean[3];

        ButtonBean[] fiveChild = new ButtonBean[3];

        WxMenuBean menu = new WxMenuBean( threeMain );

        List rootMenuList = wxDao.queryWxMenuByParent( wxCode, Long.valueOf( -9999 ) );

        List childMenuList = null;

        HavaChildButtonBean mainBtn = null;

        ButtonBean childBtn = null;

        WxMenu vo = null;

        for ( int i = 0; i < rootMenuList.size(); i++ )
        {

            vo = ( WxMenu ) rootMenuList.get( i );

            mainBtn = new HavaChildButtonBean( vo.getBtName() );

            mainBtn.setKey( vo.getBtKey() );
            mainBtn.setMedia_id( vo.getMediaId() );
            mainBtn.setType( vo.getBtType() );
            mainBtn.setUrl( vo.getBtUrl() );

            threeMain[i] = mainBtn;

            childMenuList = wxDao.queryWxMenuByParent( wxCode, ( ( WxMenu ) rootMenuList.get( i ) )
                .getBtId() );

            if( !childMenuList.isEmpty() )
            {
                fiveChild = new ButtonBean[childMenuList.size()];

                for ( int j = 0; j < childMenuList.size(); j++ )
                {
                    vo = ( WxMenu ) childMenuList.get( j );

                    childBtn = new ButtonBean( vo.getBtName(), vo.getBtType() );
                    childBtn.setKey( vo.getBtKey() );
                    childBtn.setMedia_id( vo.getMediaId() );
                    childBtn.setUrl( vo.getBtUrl() );

                    fiveChild[j] = childBtn;
                }

                mainBtn.setSub_button( fiveChild );
            }

        }

        String upnewsUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + ex_a_t;

        ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

        String backstr = ServiceUtil.doPOSTMethodRequestForJson( upnewsUrl, JSON.toJSON( menu )
            .toString() );

        JSONObject jn = JSON.parseObject( backstr );

        String errorCode = jn.getString( "errcode" );

        if( errorCode != null && !"0".equals( errorCode ) )
        {
            return backstr;
        }

        String resCode = jn.toString();

        return resCode == null ? "error:sys_err" : resCode;
    }

    public String transferFromWeixinAllUser( String wxCode )
    {

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = null;

        if( site.getSiteId() < 0 )
        {
            wa = retrieveSingleWxConfigByWxCode( wxCode );

        }
        else
        {
            wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );
        }

        String clientId = wa.getAppId();

        String clientKey = wa.getAppsSecret();

        String ex_a_t = getAccessToken( clientId, clientKey, site.getSiteId() );

        // 获取用户

        String getUserUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + ex_a_t
            + "&next_openid=";

        ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

        String backstr = ServiceUtil.readStream( ServiceUtil.doGETMethodRequest( getUserUrl ),
            "UTF-8" );

        JSONObject jn = JSON.parseObject( backstr );

        String errorCode = jn.getString( "errcode" );

        if( errorCode != null && !"0".equals( errorCode ) )
        {
            return backstr;
        }

        JSONObject joData = jn.getJSONObject( "data" );

        if( joData.isEmpty() )
        {
            return "{\"errcode\":empty data}";
        }

        JSONArray jaOId = joData.getJSONArray( "openid" );

        String[] openIds = jaOId.toArray( new String[] {} );

        String openId = null;

        // 更新所有用户为未关注
        wxDao.updateWxUserStatusByWxCode( wxCode, Constant.COMMON.OFF );

        for ( int i = 0; i < openIds.length; i++ )
        {
            openId = openIds[i];

            transferFromWeixinSingleUser( wxCode, openId );
        }

        String resCode = jn.toString();

        return resCode == null ? "error:sys_err" : "success";
    }

    public void deleteWxUserBySubStatus( String wxCode, Integer status )
    {
        wxDao.deleteWxUserByWxCodeAndStatus( wxCode, status );
    }

    public void transferFromWeixinSingleUser( String wxCode, String openId )
    {

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = null;

        if( site.getSiteId() < 0 )
        {
            wa = retrieveSingleWxConfigByWxCode( wxCode );

        }
        else
        {
            wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );
        }

        String clientId = wa.getAppId();

        String clientKey = wa.getAppsSecret();

        String ex_a_t = getAccessToken( clientId, clientKey, site.getSiteId() );

        // 获取用户

        String getUserUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + ex_a_t
            + "&openid=" + openId + "&lang=zh_CN";

        ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

        String backstr = ServiceUtil.readStream( ServiceUtil.doGETMethodRequest( getUserUrl ),
            "UTF-8" );

        JSONObject jn = JSON.parseObject( backstr );

        String errorCode = jn.getString( "errcode" );

        if( errorCode != null && !"0".equals( errorCode ) )
        {
            return;
        }

        WxUser wu = new WxUser();

        wu.setOpenId( openId );
        wu.setWxCode( wxCode );
        wu.setWuCity( jn.getString( "city" ) );
        wu.setWuCountry( jn.getString( "country" ) );
        wu.setWuHeadimgurl( jn.getString( "headimgurl" ) );
        wu.setWuLanguage( jn.getString( "language" ) );
        wu.setWuNickname( SystemSafeCharUtil.encode(jn.getString( "nickname" )) );
        wu.setWuProvince( jn.getString( "province" ) );
        wu.setWuRemark( jn.getString( "remark" ) );
        wu.setWuSex( jn.getString( "sex" ) );

        Long time = Long.parseLong( jn.getString( "subscribe_time" ) );
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        String subtime = sdf.format( new Date( time * 1000L ) );

        wu.setWuSubscribe_time( subtime );
        wu.setWuTagid_list( jn.getString( "tagid_list" ) );
        wu.setWuUnionid( jn.getString( "unionid" ) );
        wu.setWuGroupid( jn.getString( "groupid" ) );
        wu.setSubStatus( Constant.COMMON.ON );

        WxUser testExist = wxDao.querySingleWxUserByWxCodeAndOpenId( wxCode, openId );

        if( testExist != null )
        {
            wu.setUserRemark( testExist.getUserRemark() );

            wxDao.deleteWxUserByOpenId( wxCode, openId );
        }

        wxDao.saveWxUser( wu );

    }

    public void addWxUserRemark( String openId, String remark )
    {
        wxDao.updateWxUserRemark( openId, remark );
    }

    public Object getWxUserForTag( String openId, String groupId, String ncKey, String target,
        String pn, String size )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        if( StringUtil.isStringNotNull( openId ) )
        {
            return wxDao.querySingleWxUserByWxCodeAndOpenId( wxCode, openId );
        }
        else if( StringUtil.isStringNotNull( ncKey ) )
        {
            ncKey = SystemSafeCharUtil.decodeFromWeb( ncKey );

            if( "nc".equals( target ) )
            {

                return wxDao.queryWxUserByWxCodeAndNickName( wxCode, ncKey );
            }
            else if( "bz".equals( target ) )
            {

                return wxDao.queryWxUserByWxCodeAndRemark( wxCode, ncKey );
            }
        }
        else if( StringUtil.isStringNull( groupId ) )
        {
            int pageNum = StringUtil.getIntValue( pn, 1 );

            int pageSize = StringUtil.getIntValue( size, 15 );

            Page pageInfo = null;

            Long count = null;

            List result = null;

            count = wxDao.queryWxUserCountByWxCode( wxCode );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = wxDao.queryWxUserByWxCode( wxCode, Long.valueOf( pageInfo.getFirstResult() ),
                Integer.valueOf( pageSize ) );

            return new Object[] { result, pageInfo };

        }
        else if( StringUtil.isStringNotNull( groupId ) )
        {
            int pageNum = StringUtil.getIntValue( pn, 1 );

            int pageSize = StringUtil.getIntValue( size, 15 );

            Page pageInfo = null;

            Long count = null;

            List result = null;

            count = wxDao.queryWxUserCountByWxCodeAndGroup( wxCode, groupId );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = wxDao.queryWxUserByWxCodeAndGroup( wxCode, groupId, Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageSize ) );

            return new Object[] { result, pageInfo };
        }

        return Collections.EMPTY_LIST;

    }

    /**
     * 删除已上传微信服务器上的资源
     * 
     * @param wxCode
     * @param name
     * @return
     */
    public String deleteWeixinServerRes( String wxCode, String mediaId )
    {
        if( StringUtil.isStringNull( mediaId ) )
        {
            return "null";
        }

        String targetUrl = "https://api.weixin.qq.com/cgi-bin/material/del_material";

        String param = "{\"media_id\":" + mediaId + "}";

        String backStr = requestWeixinServer( wxCode, targetUrl, "POST", param );

        // JSONObject jn = JSON.parseObject( backStr );

        // String errorCode = jn.getString( "errcode" );

        // if( errorCode != null && !"0".equals( errorCode ) )
        // {
        // return backStr;
        // }

        return backStr;

    }

    public String transferFromWeixinAllUserGroup( String wxCode )
    {

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = null;

        if( site.getSiteId() < 0 )
        {
            wa = retrieveSingleWxConfigByWxCode( wxCode );
        }
        else
        {
            wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );
        }

        String clientId = wa.getAppId();

        String clientKey = wa.getAppsSecret();

        String ex_a_t = getAccessToken( clientId, clientKey, site.getSiteId() );

        // 获取用户

        String getUserUrl = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=" + ex_a_t;

        ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

        String backstr = ServiceUtil.readStream( ServiceUtil.doGETMethodRequest( getUserUrl ),
            "UTF-8" );

        JSONObject jn = JSON.parseObject( backstr );

        String errorCode = jn.getString( "errcode" );

        if( errorCode != null && !"0".equals( errorCode ) )
        {
            return backstr;
        }

        JSONArray jog = jn.getJSONArray( "groups" );

        if( jog.isEmpty() )
        {
            return "{\"errcode\":empty data}";
        }

        wxDao.deleteWxUserGroupByWxCode( wxCode );

        Object[] ogs = jog.toArray();

        JSONObject group = null;

        String id = null;

        String name = null;

        String count = null;

        for ( int i = 0; i < ogs.length; i++ )
        {
            group = ( JSONObject ) ogs[i];

            id = group.getString( "id" );

            name = group.getString( "name" );

            count = group.getString( "count" );

            wxDao.saveWxUserGroup( wxCode, id, name, count );
        }

        String resCode = jn.toString();

        return resCode == null ? "error:sys_err" : "success";
    }

    /**
     * 创建新的用户组并存到本地
     * 
     * @param wxCode
     * @param name
     * @return
     */
    public String transferToWeixinNewUserGroup( String wxCode, String name )
    {
        String updateUGN = "https://api.weixin.qq.com/cgi-bin/groups/create";

        String param = "{\"group\":{\"name\":\"" + name + "\"}}";

        String backStr = requestWeixinServer( wxCode, updateUGN, "POST", param );

        JSONObject jn = JSON.parseObject( backStr );

        String errorCode = jn.getString( "errcode" );

        if( errorCode != null && !"0".equals( errorCode ) )
        {
            return backStr;
        }

        JSONObject jog = jn.getJSONObject( "group" );

        String gid = jog.getString( "id" );

        String gname = jog.getString( "name" );

        wxDao.saveWxUserGroup( wxCode, gid, gname, "0" );

        return "success";

    }

    /**
     * 编辑用户组名称并存到本地
     * 
     * @param wxCode
     * @param name
     * @return
     */
    public String transferToWeixinEditUserGroupName( String wxCode, String id, String name )
    {
        String updateUGN = "https://api.weixin.qq.com/cgi-bin/groups/update";

        String param = "{\"group\":{\"id\":" + id + ",\"name\":\"" + name + "\"}}";

        String backStr = requestWeixinServer( wxCode, updateUGN, "POST", param );

        JSONObject jn = JSON.parseObject( backStr );

        String errorCode = jn.getString( "errcode" );

        if( errorCode != null && !"0".equals( errorCode ) )
        {
            return backStr;
        }

        wxDao.updateWxUserGroup( wxCode, id, name );

        return "success";

    }

    /**
     * 改变用户分组并存到本地
     * 
     * @param wxCode
     * @param name
     * @return
     */
    public String transferToWeixinChangeUserGroup( String wxCode, List openIds, String toGid )
    {
        String updateUGN = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate";

        String oids = "";

        if( openIds != null )
        {
            for ( int i = 0; i < openIds.size(); i++ )
            {
                if( i + 1 != openIds.size() )
                {
                    oids += "\"" + openIds.get( i ) + "\",";
                }
                else
                {
                    oids += "\"" + openIds.get( i ) + "\"";
                }
            }
        }

        String param = "{\"openid_list\":[" + oids + "],\"to_groupid\":" + toGid + "}";

        String backStr = requestWeixinServer( wxCode, updateUGN, "POST", param );

        JSONObject jn = JSON.parseObject( backStr );

        String errorCode = jn.getString( "errcode" );

        if( errorCode != null && !"0".equals( errorCode ) )
        {
            return backStr;
        }

        if( openIds != null )
        {
            for ( int i = 0; i < openIds.size(); i++ )
            {

                wxDao.updateWxUserNewGroup( wxCode, toGid, ( String ) openIds.get( i ) );

            }
        }

        return "success";

    }

    /**
     * 删除用户分组并存到本地
     * 
     * @param wxCode
     * @param name
     * @return
     */
    public String transferToWeixinDeleteUserGroup( String wxCode, String gid )
    {
        String targetUrl = "https://api.weixin.qq.com/cgi-bin/groups/delete";

        String param = "{\"group\":{\"id\":" + gid + "}}";

        String backStr = requestWeixinServer( wxCode, targetUrl, "POST", param );

        JSONObject jn = JSON.parseObject( backStr );

        String errorCode = jn.getString( "errcode" );

        if( errorCode != null && !"0".equals( errorCode ) )
        {
            return backStr;
        }

        wxDao.deleteWxUserGroupByWxCode( wxCode, gid );

        return "success";

    }

    public Object getWxUserGroupForTag( String gId )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        if( StringUtil.isStringNotNull( gId ) )
        {
            return wxDao.querySingleWxUserGroupByWxCodeAndGId( wxCode, gId );
        }
        else
        {
            return wxDao.queryWxUserGroupByWxCode( wxCode );
        }

    }

    public String sendWeixinContent( String wxCode, Long resId, String preview, String previewId,
        String type, WxSendAllInfoBean wab )
    {

        if( StringUtil.isStringNull( type ) && resId == null && wab == null )
        {
            return "error:param_null";
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String mediaId = "";

        if( StringUtil.isStringNull( type ) )
        {
            type = wab.getMsgTypeFlag();
        }

        if( resId == null )
        {
            resId = wab.getMsgId();
        }

        if( "mpnews".equals( type ) )
        {
            NewsItemGroupBean news = null;

            List resl = wxDao.queryNewsItemGroupByRowFlagAndWxCode( resId, wxCode );

            if( resl.isEmpty() )
            {
                return "error:res_null:素材已丢失";
            }

            news = ( NewsItemGroupBean ) resl.get( 0 );

            if( news == null )
            {
                return "error:res_null";
            }

            mediaId = news.getMediaId();
        }
        else if( "image".equals( type ) || "voice".equals( type ) || "music".equals( type )
            || "mpvideo".equals( type ) || "text".equals( type ) )
        {
            WxResourceBean res = wxDao.querySingleWxRes( wxCode, resId );

            if( res == null )
            {
                return "error:res_null";
            }

            mediaId = res.getMediaId();

            if( "text".equals( type ) )
            {
                mediaId = res.getResContent();
            }

        }

        if( StringUtil.isStringNull( mediaId ) )
        {
            return "error:not_upload";
        }

        WxAccount wa = null;

        if( site.getSiteId() < 0 )
        {
            wa = retrieveSingleWxConfigByWxCode( wxCode );
        }
        else
        {
            wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );
        }

        String clientId = wa.getAppId();

        String clientKey = wa.getAppsSecret();

        String accesToken = getAccessToken( clientId, clientKey, site.getSiteId() );

        String pushBackstr = null;

        String contentFlag = "media_id";

        if( "text".equals( type ) )
        {
            contentFlag = "content";
        }

        if( "true".equals( preview ) )
        {
            // 以下为预览
            String previewUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token="
                + accesToken;

            if( StringUtil.isStringNull( previewId ) )
            {
                previewId = ( String ) SecuritySessionKeeper.getSecuritySession()
                    .getWorkContextMap().get( "weixinName" );
            }

            String spMsg = "{" + "\"touser\":\"" + previewId + "\"," + "\"" + type + "\":{"
                + "\"" + contentFlag + "\":\"" + mediaId + "\"" + "}," + "\"msgtype\":\"" + type
                + "\"" + "}";

            ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

            pushBackstr = ServiceUtil.doPOSTMethodRequestForJson( previewUrl, spMsg );
        }
        else
        {

            String toGroupUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="
                + accesToken;

            String all = "false";

            if( "-9999".equals( wab.getSendTarget().toString() ) )
            {
                all = "true";
            }

            String toGroup = "{" + "\"filter\":{" + " \"is_to_all\":" + all + ",\"group_id\": \""
                + wab.getSendTarget() + "\"" + "}," + "\"" + type + "\":{" + "\"" + contentFlag
                + "\":\"" + mediaId + "\"" + "}," + "\"msgtype\":\"" + type + "\" " + "}";

            ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

            pushBackstr = "";

            pushBackstr = ServiceUtil.doPOSTMethodRequestForJson( toGroupUrl, toGroup );

            wxDao.updateWxSendAllInfoReturnMsg( wab.getSaId(), pushBackstr );

            wxDao.updateWxSendAllInfoCensor( wab.getSaId(), Integer.valueOf( 9999 ), wxCode );
        }

        return pushBackstr;
    }

    /**
     * 上传资源
     * 
     * @param accessToken
     * @param type
     * @param filePath
     * @return
     */
    public static String uploadMedia( String accessToken, String type, String filePath,
        boolean imageMode )
    {

        String result = null;

        File file = new File( filePath );
        if( !file.exists() || !file.isFile() )
        {
            return null;
        }

        /**
         * 第一部分
         */
        String uploadMediaUrl = null;
        // 拼装请求地址
        if( imageMode )
        {
            uploadMediaUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token="
                + accessToken;
        }

        else
        {
            uploadMediaUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="
                + accessToken + "&type=" + type;
        }

        URL urlObj;
        HttpURLConnection con = null;
        try
        {
            urlObj = new URL( uploadMediaUrl );

            // 连接
            con = ( HttpURLConnection ) urlObj.openConnection();

            /**
             * 设置关键值
             */
            con.setRequestMethod( "POST" ); // 以Post方式提交表单，默认get方式
            con.setDoInput( true );
            con.setDoOutput( true );
            con.setUseCaches( false ); // post方式不能使用缓存

            // 设置请求头信息
            con.setRequestProperty( "Connection", "Keep-Alive" );
            con.setRequestProperty( "Charset", "UTF-8" );

            // 设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            con.setRequestProperty( "Content-Type", "multipart/form-data; boundary=" + BOUNDARY );

            // 请求正文信息

            // 第一部分：
            StringBuilder sb = new StringBuilder();
            sb.append( "--" ); // 必须多两道线
            sb.append( BOUNDARY );
            sb.append( "\r\n" );
            sb.append( "Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName()
                + "\"\r\n" );
            sb.append( "Content-Type:application/octet-stream\r\n\r\n" );

            byte[] head = sb.toString().getBytes( "utf-8" );

            // 获得输出流
            OutputStream out = new DataOutputStream( con.getOutputStream() );
            // 输出表头
            out.write( head );

            // 文件正文部分
            // 把文件已流文件的方式 推入到url中
            DataInputStream in = new DataInputStream( new FileInputStream( file ) );
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ( ( bytes = in.read( bufferOut ) ) != -1 )
            {
                out.write( bufferOut, 0, bytes );
            }
            in.close();

            // 结尾部分
            byte[] foot = ( "\r\n--" + BOUNDARY + "--\r\n" ).getBytes( "utf-8" );// 定义最后数据分隔线

            out.write( foot );

            out.flush();
            out.close();
        }
        catch ( Exception e1 )
        {
            e1.printStackTrace();
        }

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try
        {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader( new InputStreamReader( con.getInputStream() ) );
            String line = null;
            while ( ( line = reader.readLine() ) != null )
            {
                buffer.append( line );
            }
            if( result == null )
            {
                result = buffer.toString();
            }
        }
        catch ( IOException e )
        {
            System.out.println( "发送POST请求出现异常！" + e );
            e.printStackTrace();

        }
        finally
        {
            if( reader != null )
            {
                try
                {
                    reader.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }

        }

        JSONObject jn = JSON.parseObject( result );

        if( imageMode )
        {
            String media_url = jn.getString( "url" );

            return media_url;
        }

        String media_id = jn.getString( "media_id" );

        return media_id;
    }

    public String transferWxNews( List idList, String wxCode )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = null;

        if( site.getSiteId() < 0 )
        {
            wa = retrieveSingleWxConfigByWxCode( wxCode );
        }
        else
        {
            wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );
        }

        String clientId = wa.getAppId();

        String clientKey = wa.getAppsSecret();

        StringBuilder errBuf = new StringBuilder();

        String accesToken = getAccessToken( clientId, clientKey, site.getSiteId() );

        String baseRealPath = SystemConfiguration.getInstance().getSystemConfig()
            .getSystemRealPath();

        Long infoId = null;

        NewsItemGroupBean bean = null;

        SiteResourceBean resBean = null;

        for ( int i = 0; i < idList.size(); i++ )
        {
            infoId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

            if( infoId.longValue() > 0 )
            {
                // 处理封面
                bean = wxDao.querySingleNewsItemInfoByInfoId( infoId );

                resBean = resService.retrieveSingleResourceBeanByResId( StringUtil.getLongValue(
                    bean.getImgResId(), -1 ) );

                String imgMediaId = "";

                if( resBean != null )
                {
                    String rootPath = baseRealPath + site.getSiteRoot();

                    String fullPath = rootPath + File.separator + site.getImageRoot()
                        + File.separator + resBean.getResSource();

                    File file = new File( fullPath );

                    if( file.exists() )
                    {
                        String[] res = uploadForeverMedia( accesToken, file, "image", "", "" );

                        if( res.length == 2 )
                        {
                            imgMediaId = res[0];
                        }
                        else
                        {
                            errBuf.append( "资源ID:" + infoId );
                            errBuf.append( "<br/>" );
                            errBuf.append( res[0] );
                            errBuf.append( "<br/>" );
                            errBuf.append( "<br/>" );

                            return StringUtil.isStringNull( errBuf.toString() ) ? "success"
                                : errBuf.toString();
                        }

                    }

                }

                // 组合资源
                WxNewsResBean wxRes = getAndDisposeWxNewsRes( accesToken, infoId, "", "" );

                String tranFlag = transferWeixinNewsRes( accesToken, wxRes, infoId );

                if( !"success".equals( tranFlag ) )
                {
                    errBuf.append( "资源ID:" + infoId );
                    errBuf.append( "<br/>" );
                    errBuf.append( tranFlag );
                    errBuf.append( "<br/>" );
                    errBuf.append( "<br/>" );
                }

            }

        }

        clearWxResCache();

        return StringUtil.isStringNull( errBuf.toString() ) ? "success" : errBuf.toString();
    }

    public String transferWeixinNewsRes( String accesToken, WxNewsResBean res, Long infoId )
    {
        String upnewsUrl = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token="
            + accesToken;

        ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

        String msg = JSON.toJSON( res ).toString();

        String backstr = ServiceUtil.doPOSTMethodRequestForJson( upnewsUrl, msg );

        JSONObject jn = JSON.parseObject( backstr );

        String errorCode = jn.getString( "errcode" );

        log.info( "transferWeixinNewsRes:" + jn.toJSONString() );

        if( errorCode != null && !"0".equals( errorCode ) )
        {
            return jn.toString();
        }
        else
        {
            log.info( "updateNewsItemInfoMediaId:" + infoId + "-" + jn.getString( "media_id" ) );
            wxDao
                .updateNewsItemInfoMediaId( infoId, Constant.COMMON.ON, jn.getString( "media_id" ) );

            return "success";

        }
    }

    public String transferWxResource( List idList, String resType, String wxCode )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = null;

        if( site.getSiteId() < 0 )
        {
            wa = retrieveSingleWxConfigByWxCode( wxCode );
        }
        else
        {
            wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );
        }

        String clientId = wa.getAppId();

        String clientKey = wa.getAppsSecret();

        String ex_a_t = getAccessToken( clientId, clientKey, site.getSiteId() );

        WxResourceBean wb = null;

        String baseRealPath = SystemConfiguration.getInstance().getSystemConfig()
            .getSystemRealPath();

        long id = -1;

        StringBuilder errBuf = new StringBuilder();

        for ( int i = 0; i < idList.size(); i++ )
        {
            id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );

            if( id < 0 )
            {
                continue;
            }

            wb = wxDao.querySingleWxRes( wxCode, id );

            if( wb != null )
            {

                Long targetId = Long.valueOf( -1 );

                if( Constant.WX.RESP_MESSAGE_TYPE_IMAGE.equals( resType ) )
                {
                    targetId = Long.valueOf( ServiceUtil.getResId( wb.getImageRes() ) );
                }
                else if( Constant.WX.RESP_MESSAGE_TYPE_VIDEO.equals( resType ) )
                {
                    targetId = Long.valueOf( ServiceUtil.getResId( wb.getVideoRes() ) );
                }
                else if( Constant.WX.RESP_MESSAGE_TYPE_VOICE.equals( resType ) )
                {
                    targetId = Long.valueOf( ServiceUtil.getResId( wb.getVoiceRes() ) );
                }
                else if( Constant.WX.SPEC_RES_THUMB.equals( resType ) )
                {
                    targetId = Long.valueOf( ServiceUtil.getResId( wb.getMusicThumb() ) );
                }

                if( targetId.longValue() < 0 )
                {
                    continue;
                }

                // 删除原始media信息
                String mid = wb.getMediaId();

                if( StringUtil.isStringNotNull( mid ) )
                {
                    deleteWeixinServerRes( wxCode, mid );
                }

                SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( targetId );

                String rootPath = baseRealPath + site.getSiteRoot();

                String resRoot = null;

                if( Constant.WX.RESP_MESSAGE_TYPE_IMAGE.equals( resType )
                    || Constant.WX.SPEC_RES_THUMB.equals( resType ) )
                {
                    resRoot = site.getImageRoot();
                }
                else if( Constant.WX.RESP_MESSAGE_TYPE_VIDEO.equals( resType )
                    || Constant.WX.RESP_MESSAGE_TYPE_VOICE.equals( resType ) )
                {
                    resRoot = site.getMediaRoot();
                }

                File file = new File( rootPath + File.separator + resRoot + File.separator
                    + resBean.getResSource() );

                if( !file.exists() )
                {
                    errBuf.append( "资源ID:" + id + ", 错误:资源丢失" );
                    errBuf.append( "<br/>" );
                }
                else
                {

                    String title = "";

                    String desc = "";

                    if( Constant.WX.RESP_MESSAGE_TYPE_VIDEO.equals( resType ) )
                    {
                        title = wb.getResTitle();

                        desc = wb.getResDesc();
                    }

                    String[] res = uploadForeverMedia( ex_a_t, file, resType, title, desc );

                    if( res.length == 2 )
                    {
                        if( Constant.WX.SPEC_RES_THUMB.equals( resType ) )
                        {
                            wxDao.updateWxThumbResTranInfo( id, res[0], res[1], new Timestamp(
                                DateAndTimeUtil.clusterTimeMillis() ) );
                        }
                        else
                        {
                            wxDao.updateWxResTranInfo( id, res[0], res[1], new Timestamp(
                                DateAndTimeUtil.clusterTimeMillis() ) );
                        }
                    }
                    else
                    {
                        errBuf.append( "资源ID:" + id + ", 错误:" + res[0] );
                        errBuf.append( "<br/>" );
                        errBuf.append( "<br/>" );

                        if( res[0] != null && res[0].indexOf( ":40001" ) != -1 )
                        {
                            WeixinService.clearTokenCache();
                        }

                    }
                }
            }

        }

        clearWxResCache();

        return StringUtil.isStringNull( errBuf.toString() ) ? "success" : errBuf.toString();

    }

    public static String[] uploadForeverMedia( String accessToken, File file, String type,
        String title, String introduction )
    {
        try
        {

            // 这块是用来处理如果上传的类型是video的类型的
            JSONObject videoD = new JSONObject();
            videoD.put( "title", title );
            videoD.put( "introduction", introduction );

            // 拼装请求地址
            String uploadMediaUrl = "http://api.weixin.qq.com/cgi-bin/material/add_material?access_token=##ACCESS_TOKEN##";
            uploadMediaUrl = uploadMediaUrl.replace( "##ACCESS_TOKEN##", accessToken );

            URL url = new URL( uploadMediaUrl );
            String result = null;
            long filelength = file.length();
            String fileName = file.getName();
            String suffix = fileName.substring( fileName.lastIndexOf( "." ), fileName.length() );
            // String type = "image/jpg"; // 我这里写死
            /**
             * 你们需要在这里根据文件后缀suffix将type的值设置成对应的mime类型的值
             */
            HttpURLConnection con = ( HttpURLConnection ) url.openConnection();
            con.setRequestMethod( "POST" ); // 以Post方式提交表单，默认get方式
            con.setDoInput( true );
            con.setDoOutput( true );
            con.setUseCaches( false ); // post方式不能使用缓存
            // 设置请求头信息
            con.setRequestProperty( "Connection", "Keep-Alive" );
            con.setRequestProperty( "Charset", "UTF-8" );

            String BOUNDARY = "----------" + System.currentTimeMillis();
            con.setRequestProperty( "Content-Type", "multipart/form-data; boundary=" + BOUNDARY );
            // 请求正文信息
            // 第一部分：

            StringBuilder sb = new StringBuilder();

            // 这块是post提交type的值也就是文件对应的mime类型值
            sb.append( "--" ); // 必须多两道线
            // 这里说明下，这两个横杠是http协议要求的，用来分隔提交的参数用的，不懂的可以看看http
            // 协议头
            sb.append( BOUNDARY );
            sb.append( "\r\n" );

            /** 以下使用通用文件传输* */
            // sb.append( "Content-Disposition: form-data;name=\"type\"
            // \r\n\r\n" ); // 这里是参数名，参数名和值之间要用两次
            // sb.append( type + "\r\n" ); // 参数的值
            sb.append( "Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName()
                + "\"\r\n" );
            sb.append( "Content-Type:application/octet-stream\r\n\r\n" );

            if( Constant.WX.RESP_MESSAGE_TYPE_VIDEO.equals( type ) )
            {
                // 这块是上传video是必须的参数，你们可以在这里根据文件类型做if/else 判断
                sb.append( "--" ); // 必须多两道线
                sb.append( BOUNDARY );
                sb.append( "\r\n" );
                sb.append( "Content-Disposition: form-data;name=\"description\" \r\n\r\n" );
                sb.append( videoD.toString() + "\r\n" );
            }

            /**
             * 这里重点说明下，上面两个参数完全可以卸载url地址后面 就想我们平时url地址传参一样，
             * http://api.weixin.qq.com/cgi-bin/material/add_material?access_token=##ACCESS_TOKEN##&type=""&description={}
             * 这样，如果写成这样，上面的 那两个参数的代码就不用写了，不过media参数能否这样提交我没有试，感兴趣的可以试试
             */

            sb.append( "--" ); // 必须多两道线
            sb.append( BOUNDARY );
            sb.append( "\r\n" );
            // 这里是media参数相关的信息，这里是否能分开下我没有试，感兴趣的可以试试
            sb.append( "Content-Disposition: form-data;name=\"media\";filename=\"" + fileName
                + "\";filelength=\"" + filelength + "\" \r\n" );
            sb.append( "Content-Type:application/octet-stream\r\n\r\n" );
           
            byte[] head = sb.toString().getBytes( "utf-8" );
            // 获得输出流
            OutputStream out = new DataOutputStream( con.getOutputStream() );
            // 输出表头
            out.write( head );
            // 文件正文部分
            // 把文件已流文件的方式 推入到url中
            DataInputStream in = new DataInputStream( new FileInputStream( file ) );
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ( ( bytes = in.read( bufferOut ) ) != -1 )
            {
                out.write( bufferOut, 0, bytes );
            }
            in.close();
            // 结尾部分，这里结尾表示整体的参数的结尾，结尾要用"--"作为结束，这些都是http协议的规定
            byte[] foot = ( "\r\n--" + BOUNDARY + "--\r\n" ).getBytes( "utf-8" );// 定义最后数据分隔线
            out.write( foot );
            out.flush();
            out.close();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = null;
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader( new InputStreamReader( con.getInputStream() ) );
            String line = null;
            while ( ( line = reader.readLine() ) != null )
            {
                buffer.append( line );
            }
            if( result == null )
            {
                result = buffer.toString();
            }
            // 使用JSON-lib解析返回结果
          
            JSONObject jn = JSON.parseObject( result );
           
            String media_id = jn.getString( "media_id" );

            String media_url = jn.getString( "url" );

            if( StringUtil.isStringNull( media_id ) )
            {
                return new String[] { jn.toString() };
            }

            return new String[] { media_id, media_url };

        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {

        }

        return new String[] { "" };
    }

    /**
     * 核心方法:处理微信发来的请求
     * 
     * @param request
     * @return xml
     */
    public void processClientRequest( PrintWriter out, HttpServletRequest request )
    {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容

        try
        {
            // 调用parseXml方法解析请求消息
            Map requestMap = MessageUtil.parseXml( request );
            // 发送方帐号
            String fromUserName = ( String ) requestMap.get( "FromUserName" );
            // 开发者微信号
            String toUserName = ( String ) requestMap.get( "ToUserName" );
            // 消息类型
            String msgType = ( String ) requestMap.get( "MsgType" );

            log.info( "[[[>>>>>>><<<<<<<]]]:" + requestMap );

            // 扩展行为所需信息
            WxExtendBean extendBean = new WxExtendBean( requestMap, out, ( String ) requestMap
                .get( "Event" ), msgType );

            // 回复默认文本消息
            // TextMessage textMessage = new TextMessage();
            // textMessage.setToUserName( fromUserName );
            // textMessage.setFromUserName( toUserName );
            // textMessage.setCreateTime( new Date().getTime() );
            // textMessage.setMsgType( Constant.WX.RESP_MESSAGE_TYPE_TEXT );

            // 事件推送类型总扩展处理 包含两种事件 1.菜单操作产生的各种事件 2.用户操作触发的某些事件
            if( msgType.equals( Constant.WX.REQ_MESSAGE_TYPE_EVENT ) )
            {
                // 事件类型
                String eventType = ( String ) requestMap.get( "Event" );

                String key = ( String ) requestMap.get( "EventKey" );

                // 自定义菜单 各种事件
                if( eventType.equals( Constant.WX.EVENT_TYPE_CLICK )
                    || eventType.equals( Constant.WX.EVENT_TYPE_VIEW )
                    || eventType.equals( Constant.WX.EVENT_TYPE_SP )
                    || eventType.equals( Constant.WX.EVENT_TYPE_SW )
                    || eventType.equals( Constant.WX.EVENT_TYPE_PS )
                    || eventType.equals( Constant.WX.EVENT_TYPE_PA )
                    || eventType.equals( Constant.WX.EVENT_TYPE_PIC )
                    || eventType.equals( Constant.WX.EVENT_TYPE_LOCS ) )
                {
                    respXml = "感谢您的点击！";

                    List wxMenuList = retrieveSingleWxMenuByKey( toUserName, key );

                    WxMenu menu = null;

                    // 响应消息,执行点击事件扩展接口
                    if( !wxMenuList.isEmpty() )
                    {
                        menu = ( WxMenu ) wxMenuList.get( 0 );

                        extendBean.setMenu( menu );

                        WxMsgBean msgBean = new WxMsgBean();

                        msgBean.setInfoId( menu.getMtId() );
                        msgBean.setResId( menu.getResId() );

                        respXml = repWxMessage( msgBean, fromUserName, toUserName );

                        // 由于只能相应一次信息,所以要判断是否有可相应,不然会影响自定义处理逻辑

                        if( StringUtil.isStringNotNull( respXml ) )
                        {
                            out.print( respXml );
                        }

                        String vbClassName = menu.getBehaviorClass();

                        if( StringUtil.isStringNotNull( vbClassName ) )
                        {

                            Class classObj = ObjectUtility.getClassInstance( vbClassName );

                            if( classObj != null )
                            {
                                Object valiedateBehavior = classObj.newInstance();

                                if( valiedateBehavior instanceof Behavior )
                                {
                                    ( ( Behavior ) valiedateBehavior ).operation( null,
                                        new Object[] { extendBean } );
                                }
                            }
                        }
                    }

                }

                else
                {
                    // 非点击事件
                    // 关注 取消关注 扫描带参数二维码 上报地理位置 事件
                    // 某些类型仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户
                    // 这些交互事件由扩展事件配置控制,但不包含菜单点击事件,所有非菜单事件自动触发执行配置扩展类

                    if( Constant.WX.EVENT_TYPE_MSG_SUBSCRIBE.equals( eventType ) )
                    {
                        // 关注事件欢迎信息,需要单独配置

                        WxAccount wa = retrieveSingleWxConfigByWxCode( toUserName );

                        if( wa.getSubWelInfoId().longValue() > 0
                            || wa.getSubWelResId().longValue() > 0 )
                        {

                            WxMsgBean msgBean = new WxMsgBean();

                            msgBean.setInfoId( wa.getSubWelInfoId() );

                            msgBean.setResId( wa.getSubWelResId() );

                            respXml = repWxMessage( msgBean, fromUserName, toUserName );

                            if( StringUtil.isStringNotNull( respXml ) )
                            {
                                out.print( respXml );
                            }
                        }

                        transferFromWeixinSingleUser( toUserName, fromUserName );
                    }

                    /**
                     * 关注 或 取消 关注处理用户信息
                     */
                    if( Constant.WX.EVENT_TYPE_MSG_UNSUBSCRIBE.equals( eventType ) )
                    {
                        wxDao.updateWxUserSubStatus( toUserName, fromUserName, Constant.COMMON.OFF );
                    }

                    excuteWxExtend( extendBean );
                }

            }
            // 接受用户发送消息 文本 图片 语音 视频 地理位置 链接
            else
            {
                // 文本类型关键字回复 独立处理
                if( msgType.equals( Constant.WX.REQ_MESSAGE_TYPE_TEXT ) )
                {
                    // 先精确后模糊
                    String content = ( String ) requestMap.get( "Content" );

                    if( StringUtil.isStringNotNull( content ) )
                    {
                        content = content.toLowerCase();

                        WxMsgBean msgBean = retrieveSingleWxMessageByKey( toUserName, content );

                        if( msgBean != null )
                        {

                            if( Constant.COMMON.ON.equals( msgBean.getIsText() ) )
                            {

                                TextMessage textMessage = new TextMessage();

                                textMessage.setToUserName( fromUserName );
                                textMessage.setFromUserName( toUserName );
                                textMessage.setCreateTime( DateAndTimeUtil.clusterTimeMillis() );
                                textMessage.setMsgType( Constant.WX.RESP_MESSAGE_TYPE_TEXT );
                                textMessage.setContent( msgBean.getTextMsg() );

                                respXml = MessageUtil.messageToXml( textMessage );

                            }
                            else
                            {
                                respXml = repWxMessage( msgBean, fromUserName, toUserName );
                            }

                            if( StringUtil.isStringNotNull( respXml ) )
                            {
                                out.print( respXml );
                            }
                        }
                        else
                        {
                            List wxMsgBeanList = retrieveWxMessageByIncludeMode( toUserName,
                                Constant.COMMON.ON );

                            for ( int i = 0; i < wxMsgBeanList.size(); i++ )
                            {
                                msgBean = ( WxMsgBean ) wxMsgBeanList.get( i );

                                if( content.indexOf( msgBean.getInputKey() ) != -1 )
                                {

                                    if( Constant.COMMON.ON.equals( msgBean.getIsText() ) )
                                    {

                                        TextMessage textMessage = new TextMessage();

                                        textMessage.setToUserName( fromUserName );
                                        textMessage.setFromUserName( toUserName );
                                        textMessage.setCreateTime( DateAndTimeUtil
                                            .clusterTimeMillis() );
                                        textMessage.setMsgType( Constant.WX.RESP_MESSAGE_TYPE_TEXT );
                                        textMessage.setContent( msgBean.getTextMsg() );

                                        respXml = MessageUtil.messageToXml( textMessage );
                                    }
                                    else
                                    {
                                        respXml = repWxMessage( msgBean, fromUserName, toUserName );
                                    }

                                    if( StringUtil.isStringNotNull( respXml ) )
                                    {
                                        out.print( respXml );
                                    }

                                    break;
                                }
                            }
                        }
                    }

                }

                // 扩展接口
                excuteWxExtend( extendBean );

                // 默认回复，若以上业务没有执行 out.print( respXml )则使用默认回复有效

                String repXml = defaultReply( msgType, toUserName, fromUserName, toUserName );

                if( StringUtil.isStringNotNull( repXml ) )
                {
                    out.print( repXml );
                }

            }

            // 设置文本消息的内容
            // textMessage.setContent( respContent );
            // 将文本消息对象转换成xml
            // respXml = MessageUtil.messageToXml( textMessage );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        // out.print( respXml );
    }

    /**
     * 请求微信服务
     * 
     * @param url 目标API URL
     * @param reqMethod 请求方法（GET POST）
     * @param param 参数
     * @return
     */
    public String requestWeixinServer( String wxCode, String url, String reqMethod, String param )
    {

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = null;

        if( site.getSiteId() < 0 )
        {
            wa = retrieveSingleWxConfigByWxCode( wxCode );
        }
        else
        {
            wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );
        }

        String clientId = wa.getAppId();

        String clientKey = wa.getAppsSecret();

        String ex_a_t = getAccessToken( clientId, clientKey, site.getSiteId() );

        String targetUrl = url + "?access_token=" + ex_a_t;

        ServiceUtil.trustAllHttpsCertificates();// 设置信任所有的http证书

        String backstr = null;

        if( "GET".equals( reqMethod ) )
        {
            backstr = ServiceUtil.readStream( ServiceUtil.doGETMethodRequest( targetUrl + param ),
                "UTF-8" );
        }
        else if( "POST".equals( reqMethod ) )
        {
            backstr = ServiceUtil.doPOSTMethodRequestForJson( targetUrl, param );
        }

        return backstr;

    }

    private String defaultReply( String msgType, String wxCode, String fromUserName,
        String toUserName )
    {
        Map rep = null;

        rep = retrieveWxUnkMessageByMsgType( msgType, wxCode );

        WxMsgBean msgBean = new WxMsgBean();

        msgBean.setInfoId( ( Long ) rep.get( "infoId" ) );

        msgBean.setResId( ( Long ) rep.get( "resId" ) );

        return repWxMessage( msgBean, fromUserName, toUserName );

    }

    /**
     * 根据配置的接口执行扩展代码
     * 
     * @param extendBean
     */
    private void excuteWxExtend( WxExtendBean extendBean )
    {
        Map requestMap = extendBean.getRequestMap();

        // 获取非点击事件扩展行为

        List ebClassList = null;

        if( StringUtil.isStringNull( extendBean.getEvent() ) )
        {
            ebClassList = retrieveWxExtendByWxCodeAndEventType( ( String ) requestMap
                .get( "ToUserName" ), extendBean.getMsgType() );
        }
        else
        {
            ebClassList = retrieveWxExtendByWxCodeAndEventType( ( String ) requestMap
                .get( "ToUserName" ), extendBean.getEvent() );
        }

        if( !ebClassList.isEmpty() )
        {
            Map eb = ( Map ) ebClassList.get( 0 );

            if( Constant.COMMON.ON.equals( ( Integer ) eb.get( "useStatus" ) ) )
            {
                String vbClassName = ( String ) eb.get( "beClass" );

                if( StringUtil.isStringNotNull( vbClassName ) )
                {
                    Class classObj = ObjectUtility.getClassInstance( vbClassName );

                    if( classObj != null )
                    {
                        Object valiedateBehavior = null;
                        try
                        {
                            valiedateBehavior = classObj.newInstance();
                        }
                        catch ( Exception e )
                        {
                            e.printStackTrace();
                        }

                        if( valiedateBehavior != null && valiedateBehavior instanceof Behavior )
                        {
                            ( ( Behavior ) valiedateBehavior ).operation( null,
                                new Object[] { extendBean } );
                        }
                    }
                }
            }

        }
    }

    private String repWxMessage( WxMsgBean msgBean, String fromUserName, String toUserName )
    {
        if( msgBean == null || msgBean.getInfoId() == null )
        {
            return "";
        }

        if( msgBean.getInfoId().longValue() > 0 )
        {
            NewsItemGroupBean itemInfo = retrieveSingleNewsItemGroup( msgBean.getInfoId() );

            if( itemInfo != null
                && Constant.WX.RESP_MESSAGE_TYPE_NEWS.equals( itemInfo.getTypeFlag() ) )
            {

                List group = retrieveNewsItemGroup( itemInfo.getRowFlag(), itemInfo.getWxCode() );

                NewsItemGroupBean item = null;

                List articleList = new ArrayList();

                Article ai = null;

                for ( int i = 0; i < group.size(); i++ )
                {
                    item = ( NewsItemGroupBean ) group.get( i );

                    ai = new Article();

                    ai.setDescription( item.getSummary() );
                    ai.setPicUrl( item.getImg() );
                    ai.setTitle( item.getTitle() );
                    ai.setUrl( item.getUrl() );

                    articleList.add( ai );
                }

                NewsMessage nm = new NewsMessage();
                nm.setArticleCount( articleList.size() );
                nm.setArticles( articleList );
                nm.setToUserName( fromUserName );
                nm.setFromUserName( toUserName );
                nm.setCreateTime( itemInfo.getAddTime().getTime() );
                nm.setMsgType( Constant.WX.RESP_MESSAGE_TYPE_NEWS );

                return MessageUtil.messageToXml( nm );
            }
        }
        else if( msgBean.getResId().longValue() > 0 )
        {

            WxResourceBean wxResBean = retrieveSingleWxRes( toUserName, msgBean.getResId() );

            if( wxResBean == null )
            {
                return "";
            }

            if( Constant.WX.RESP_MESSAGE_TYPE_IMAGE.equals( wxResBean.getResType() ) )
            {
                Image image = new Image();
                image.setMediaId( wxResBean.getMediaId() );

                ImageMessage im = new ImageMessage();

                im.setToUserName( fromUserName );
                im.setFromUserName( toUserName );
                im.setCreateTime( DateAndTimeUtil.clusterTimeMillis() );
                im.setMsgType( Constant.WX.RESP_MESSAGE_TYPE_IMAGE );
                im.setImage( image );

                return MessageUtil.messageToXml( im );

            }
            else if( Constant.WX.RESP_MESSAGE_TYPE_TEXT.equals( wxResBean.getResType() ) )
            {

                TextMessage tm = new TextMessage();

                tm.setToUserName( fromUserName );
                tm.setFromUserName( toUserName );
                tm.setCreateTime( DateAndTimeUtil.clusterTimeMillis() );
                tm.setMsgType( Constant.WX.RESP_MESSAGE_TYPE_TEXT );
                tm.setContent( wxResBean.getResContent() );

                return MessageUtil.messageToXml( tm );
            }
            else if( Constant.WX.RESP_MESSAGE_TYPE_VIDEO.equals( wxResBean.getResType() ) )
            {

                Video video = new Video();

                video.setMediaId( wxResBean.getMediaId() );
                video.setTitle( wxResBean.getResTitle() );
                video.setDescription( wxResBean.getResDesc() );

                VideoMessage vm = new VideoMessage();
                vm.setToUserName( fromUserName );
                vm.setFromUserName( toUserName );
                vm.setCreateTime( DateAndTimeUtil.clusterTimeMillis() );
                vm.setMsgType( Constant.WX.RESP_MESSAGE_TYPE_VIDEO );
                vm.setVideo( video );

                return MessageUtil.messageToXml( vm );
            }
            else if( Constant.WX.RESP_MESSAGE_TYPE_VOICE.equals( wxResBean.getResType() ) )
            {
                Voice voice = new Voice();

                voice.setMediaId( wxResBean.getMediaId() );

                VoiceMessage vm = new VoiceMessage();
                vm.setToUserName( fromUserName );
                vm.setFromUserName( toUserName );
                vm.setCreateTime( DateAndTimeUtil.clusterTimeMillis() );
                vm.setMsgType( Constant.WX.RESP_MESSAGE_TYPE_VOICE );
                vm.setVoice( voice );

                return MessageUtil.messageToXml( vm );
            }
            else if( Constant.WX.RESP_MESSAGE_TYPE_MUSIC.equals( wxResBean.getResType() ) )
            {
                Music music = new Music();

                music.setDescription( wxResBean.getResDesc() );
                music.setHQMusicUrl( wxResBean.getHqMusicUrl() );
                music.setMusicUrl( wxResBean.getMusicUrl() );
                music.setThumbMediaId( wxResBean.getThumbMediaId() );
                music.setTitle( wxResBean.getResTitle() );

                MusicMessage vm = new MusicMessage();
                vm.setToUserName( fromUserName );
                vm.setFromUserName( toUserName );
                vm.setCreateTime( DateAndTimeUtil.clusterTimeMillis() );
                vm.setMsgType( Constant.WX.RESP_MESSAGE_TYPE_MUSIC );
                vm.setMusic( music );

                return MessageUtil.messageToXml( vm );
            }

        }
        return "";
    }

    private WxNewsResBean getAndDisposeWxNewsRes( String accesToken, Long infoId,
        String fromUserName, String toUserName )
    {
        NewsItemGroupBean itemInfo = service.retrieveSingleNewsItemGroup( infoId );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String baseRealPath = SystemConfiguration.getInstance().getSystemConfig()
            .getSystemRealPath();

        if( itemInfo != null && Constant.WX.RESP_MESSAGE_TYPE_NEWS.equals( itemInfo.getTypeFlag() ) )
        {
            List group = service
                .retrieveNewsItemGroup( itemInfo.getRowFlag(), itemInfo.getWxCode() );

            NewsItemGroupBean item = null;

            List articleList = new ArrayList();

            ArticleRes ai = null;

            SiteResourceBean resBean = null;

            String imgMediaId = "";

            for ( int i = 0; i < group.size(); i++ )
            {
                item = ( NewsItemGroupBean ) group.get( i );

                // 处理封面

                resBean = resService.retrieveSingleResourceBeanByResId( StringUtil.getLongValue(
                    item.getImgResId(), -1 ) );

                if( resBean != null )
                {
                    String rootPath = baseRealPath + site.getSiteRoot();

                    String fullPath = rootPath + File.separator + site.getImageRoot()
                        + File.separator + resBean.getResSource();

                    File file = new File( fullPath );

                    if( file.exists() )
                    {
                        String[] res = uploadForeverMedia( accesToken, file, "image", "", "" );

                        if( res.length == 2 )
                        {
                            imgMediaId = res[0];
                        }

                    }

                }

                ai = new ArticleRes();

                ai.setAuthor( item.getCommendMan() );
                ai.setContent( uploadNewsContentImage( accesToken, item.getArticleText() ) );
                ai.setContent_source_url( item.getUrl() );
                ai.setDigest( item.getSummary() );
                ai.setShow_cover_pic( item.getShowCover() == 1 ? item.getShowCover().toString()
                    : "0" );
                ai.setThumb_media_id( imgMediaId );

                ai.setTitle( item.getTitle() );

                articleList.add( ai );
            }

            WxNewsResBean newsRes = new WxNewsResBean();

            newsRes.setArticles( articleList );

            return newsRes;
        }

        return null;
    }

    /**
     * 将图文中的图片上传到微信服务并替换原
     * 
     * @param prefixUrl
     * @param text
     * @param site
     * @param classId
     * @param dfList
     * @return
     */

    public String uploadNewsContentImage( String accesToken, String text )
    {
        if( StringUtil.isStringNull( text ) )
        {
            return text;
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String prefixUrl = site.getSiteImagePrefixUrl();

        SystemRuntimeConfig config = SystemConfiguration.getInstance().getSystemConfig();

        String systemBase = config.getSystemRealPath();

        Map imgHandlerMap = new HashMap();

        String targetContent = text;

        int startFlag = targetContent.indexOf( "<img", 0 );

        while ( startFlag != -1 )
        {
            int end = targetContent.indexOf( ">", startFlag ) + 1;

            String targetImg = StringUtil.subString( targetContent, startFlag, end );

            int srcStart = targetImg.indexOf( "\"", targetImg.indexOf( " src" ) );

            int endPos = targetImg.indexOf( "\" ", srcStart );

            if( endPos <= srcStart )
            {
                startFlag = targetContent.indexOf( "<img", end );
                continue;
            }

            String targetSrc = StringUtil.subString( targetImg, srcStart + 1, endPos );

            if( StringUtil.isStringNull( prefixUrl )
                || !targetSrc.toLowerCase().startsWith( prefixUrl ) )
            {
                startFlag = targetContent.indexOf( "<img", end );
                continue;
            }

            String filePath = StringUtil.replaceString( targetSrc.toLowerCase(), prefixUrl, "",
                false, true );

            File imgFile = new File( systemBase + site.getSiteFlag() + File.separator
                + Constant.CONTENT.IMG_BASE + File.separator + filePath );

            if( imgFile.exists() )
            {
                String wxImgUrl = uploadMedia( accesToken, "image", imgFile.getPath(), true );

                imgHandlerMap.put( targetSrc, wxImgUrl );
            }

            startFlag = targetContent.indexOf( "<img", end );
        }

        Iterator it = imgHandlerMap.entrySet().iterator();
        // 替换内容所有出现的已经下载成功的图片

        while ( it.hasNext() )
        {
            Entry entry = ( Entry ) it.next();

            targetContent = StringUtil.replaceString( targetContent, ( String ) entry.getKey(),
                ( String ) entry.getValue(), false, false );
        }

        return targetContent;

    }

    public String getSiteWxCode()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        WxAccount wa = retrieveSingleWxConfigBySiteId( site.getSiteId() );

        String wxCode = wa.getMainId();

        return wxCode;
    }

    public static void clearTokenCache()
    {
        tokenCache.clear();

    }

    public static void clearTokenCacheClusterMode()
    {
        // tokenCache.clearAllEntry();
    }

    public static void clearMenuCache()
    {
        menuCache.clearAllEntry();

    }

    public static void clearMenuCacheClusterMode()
    {
        menuCache.clearAllEntry();
    }

    public static void clearWxResCache()
    {
        wxResCache.clearAllEntry();

    }

    public static void clearWxResCacheClusterMode()
    {
        wxResCache.clearAllEntry();
    }

    public static void clearWxMsgCache()
    {
        wxMsgCache.clearAllEntry();

    }

    public static void clearWxMsgCacheClusterMode()
    {
        wxMsgCache.clearAllEntry();
    }

    public static void clearWaCache()
    {
        waCache.clearAllEntry();

    }

    public static void clearWaCacheClusterMode()
    {
        waCache.clearAllEntry();
    }

    public static void clearWaAllInfoCache()
    {
        menuCache.clearAllEntry();
        wxResCache.clearAllEntry();
        wxMsgCache.clearAllEntry();

    }

    public static void clearWaAllInfoCacheClusterMode()
    {
        menuCache.clearAllEntry();
        wxResCache.clearAllEntry();
        wxMsgCache.clearAllEntry();
    }

    
    
    

}
