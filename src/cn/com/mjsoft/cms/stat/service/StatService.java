package cn.com.mjsoft.cms.stat.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import cn.com.mjsoft.cms.behavior.InitClientIpAccessBehavior;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.cluster.adapter.ClusterCacheAdapter;
import cn.com.mjsoft.cms.cluster.adapter.ClusterListAdapter;
import cn.com.mjsoft.cms.cluster.adapter.ClusterMapAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.controller.ManageModelContentController;
import cn.com.mjsoft.cms.content.dao.ContentDao;

import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.message.service.MessageService;
import cn.com.mjsoft.cms.organization.bean.SystemOrganizationBean;
import cn.com.mjsoft.cms.organization.service.OrgService;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.schedule.service.ScheduleService;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.dao.SiteGroupDao;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.cms.stat.bean.ContentTempTraceBean;
import cn.com.mjsoft.cms.stat.bean.FlowTraceBean;
import cn.com.mjsoft.cms.stat.bean.SiteVisitDisposeBean;
import cn.com.mjsoft.cms.stat.bean.StatClassContentTraceBean;
import cn.com.mjsoft.cms.stat.bean.StatContentVisitOrCommentDWMCount;
import cn.com.mjsoft.cms.stat.bean.StatManagerContentTraceBean;
import cn.com.mjsoft.cms.stat.bean.StatOrgContentTraceBean;
import cn.com.mjsoft.cms.stat.bean.StatVisitInfoBean;
import cn.com.mjsoft.cms.stat.bean.StatVisitorHourAnalysisBean;
import cn.com.mjsoft.cms.stat.dao.StatDao;
import cn.com.mjsoft.cms.stat.dao.vo.StatClassContentTrace;
import cn.com.mjsoft.cms.stat.dao.vo.StatManagerContentTrace;
import cn.com.mjsoft.cms.stat.dao.vo.StatOrgContentTrace;
import cn.com.mjsoft.cms.stat.dao.vo.StatVisitInfo;
import cn.com.mjsoft.cms.stat.dao.vo.StatVisitorDayAnalysis;
import cn.com.mjsoft.cms.stat.dao.vo.StatVisitorHourAnalysis;
import cn.com.mjsoft.framework.cache.Cache;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.headstream.IUser;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.IPSeeker;
import cn.com.mjsoft.framework.util.MathUtil;
import cn.com.mjsoft.framework.util.RegexUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;

@SuppressWarnings( { "rawtypes", "unchecked" } )
public class StatService
{
    private static Logger log = Logger.getLogger( StatService.class );

    private static final String[] PROVS = new String[] { "北京", "天津", "上海", "重庆", "河北", "河南", "云南",
        "辽宁", "黑龙江", "湖南", "安徽", "山东", "新疆", "江苏", "浙江", "江西", "湖北", "广西", "甘肃", "山西", "内蒙古", "陕西",
        "吉林", "福建", "贵州", "广东", "青海", "西藏", "四川", "宁夏", "海南", "台湾", "香港", "澳门" };

    public static Cache cache = new ClusterCacheAdapter( 3000, "statService.cache" );

    public static List<FlowTraceBean> traceTemp = new ArrayList();

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private static ScheduleService scheduleService = ScheduleService.getInstance();

    private static final String REG_IP = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";

    private static final int MAX_DELAY = 7;// 测试模式使用10以内小数字，正式部署建议1000左右

    public static final ClusterListAdapter statDelayList = new ClusterListAdapter(
        "statService.statDelayList", StatVisitInfo.class );

    public static final ClusterListAdapter statCommentDelayList_NOTUSE = new ClusterListAdapter(
        "statService.statCommentDelayList", null );

    public static final ClusterMapAdapter statCacheContentClickCountMap = new ClusterMapAdapter(
        "statService.statCacheContentClickCountMap", Long.class,
        StatContentVisitOrCommentDWMCount.class );

    public static final Map statCacheContentCommentCountMap_NOTUSE = new HashMap();

    private SiteGroupService siteService = SiteGroupService.getInstance();

    private MessageService messageService = MessageService.getInstance();

    private SecurityService securityService = SecurityService.getInstance();

    private static StatService service = null;

    private StatDao statDao;

    private SiteGroupDao siteGroupDao;

    // private static Object key = new Object();

    private StatService()
    {
        statDao = new StatDao( mysqlEngine );

        siteGroupDao = new SiteGroupDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new StatService();
        }
    }

    public static StatService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public void addNewVisitorStatInfo( StatVisitInfo visitInfo )
    {
        if( visitInfo == null )
        {
            return;
        }

        Long vistContentId = visitInfo.getContentId();
        StatContentVisitOrCommentDWMCount clickDWMCount = null;

        if( vistContentId.longValue() > 0 )
        {
            clickDWMCount = ( StatContentVisitOrCommentDWMCount ) statCacheContentClickCountMap
                .get( vistContentId );

            if( clickDWMCount == null )
            {
                statCacheContentClickCountMap.put( vistContentId,
                    new StatContentVisitOrCommentDWMCount() );
            }
            else
            {
                clickDWMCount.addOneClick();

                // 若为集群模式，对象值变化需要同步到Redis
                statCacheContentClickCountMap.put( vistContentId, clickDWMCount );
            }
        }

        statDelayList.add( visitInfo );

        if( statDelayList.size() >= MAX_DELAY )
        {
            scheduleService.startCollectVisitorInfoAndAnalyseJob();
        }
    }

    public void updateContentCommentCountInfo_NOTUSE( Long contentId )
    {
        if( contentId == null )
        {
            return;
        }

        StatContentVisitOrCommentDWMCount clickDWMCount = null;

        if( contentId.longValue() > 0 )
        {
            clickDWMCount = ( StatContentVisitOrCommentDWMCount ) statCacheContentCommentCountMap_NOTUSE
                .get( contentId );

            if( clickDWMCount == null )
            {
                statCacheContentClickCountMap.put( contentId,
                    new StatContentVisitOrCommentDWMCount() );
            }
            else
            {
                clickDWMCount.addOneClick();
            }
        }

        // statCommentDelayList.add( visitInfo );

        if( statCommentDelayList_NOTUSE.size() >= MAX_DELAY )
        {
            scheduleService.startCollectVisitorInfoAndAnalyseJob();
        }
    }

    public void transferVisitorStatInfoCacheToPe()
    {
        List tempInfoList = null;
        synchronized ( statDelayList )
        {

            tempInfoList = new ArrayList( statDelayList.getList() );

            statDelayList.clear();

            log.info( "[StatService] transferVisitorStatInfoCacheToPe : DelayList-isEmpty:"
                + statDelayList.size() );
        }

        log.info( "[StatService] transferVisitorStatInfoCacheToPe : DelayList " + tempInfoList );

        // 分析当前所出小时的情况
        StatVisitInfo vi = null;
        Set ipSet = new HashSet();
        Set uvSet = new HashSet();

        // int cookieSec = 60 - currentTime.get( Calendar.SECOND );

        for ( int i = tempInfoList.size() - 1; i >= 0; i-- )
        {
            vi = ( ( StatVisitInfo ) tempInfoList.get( i ) );
            ipSet.add( vi.getIp() );
            uvSet.add( vi.getUvId() );

        }

        // 点击数处理
        Map tempClickCountMap = null;
        synchronized ( statCacheContentClickCountMap )
        {

            tempClickCountMap = new HashMap( statCacheContentClickCountMap.getMap() );

            statCacheContentClickCountMap.clear();

            log.info( "[StatService] transferVisitorStatInfoCacheToPe : ClickCountCache-isEmpty:"
                + statCacheContentClickCountMap.size() );
        }

        log.info( "[StatService] transferVisitorStatInfoCacheToPe : ClickCountCache "
            + tempClickCountMap );

        // 分析当前所处小时的数据
        // Calendar currentTime = Calendar.getInstance();

        // int year = currentTime.get( Calendar.YEAR );

        // int month = currentTime.get( Calendar.MONTH );

        // int day = currentTime.get( Calendar.DAY_OF_MONTH );

        // int hour = currentTime.get( Calendar.HOUR_OF_DAY );

        boolean clickEvent = false;

        try
        {
            mysqlEngine.beginTransaction();

            mysqlEngine.startBatch();

            // 访问者细节信息
            StatVisitInfo visitInfo = null;
            for ( int j = 0; j < tempInfoList.size(); j++ )
            {
                visitInfo = ( StatVisitInfo ) tempInfoList.get( j );

                visitInfo
                    .setRefferKey( SystemSafeCharUtil.decodeFromWeb( visitInfo.getRefferKey() ) );
             
                statDao.save( visitInfo );
                ipSet.add( vi.getIp() );
                uvSet.add( vi.getUvId() );
            }

            // 点击内容事件
            if( !tempClickCountMap.isEmpty() )
            {
                clickEvent = true;
            }

            Iterator entryIter = tempClickCountMap.entrySet().iterator();

            Entry entry = null;
            StatContentVisitOrCommentDWMCount clickDWMCount = null;

            while ( entryIter.hasNext() )
            {
                entry = ( Entry ) entryIter.next();

                clickDWMCount = ( StatContentVisitOrCommentDWMCount ) entry.getValue();

                statDao.updateContentAllClickCount( clickDWMCount, ( Long ) entry.getKey() );
            }

            mysqlEngine.executeBatch();

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            // 快速缓存模式更新
            if( clickEvent )
            {
                ContentDao.releaseOrderFilterCountCache();
                ContentService.releaseFastListContentCache();
            }
        }

    }

    /**
     * 分析当前日期当前小时的PV UV IP(合并站点资源等过期资源清除)
     * 
     */
    @SuppressWarnings( "unchecked" )
    public void analyseVisitorStatInfoAndClearSiteResCurrnetHour( boolean prevHour )
    {
        Calendar currentTime = Calendar.getInstance();

        log.info( "analyseVisitorStatInfo at time :"
            + DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS ) );

        Integer hour = Integer.valueOf( currentTime.get( Calendar.HOUR_OF_DAY ) );

        Integer year = null;

        Integer month = null;

        Integer day = null;

        boolean DAY_OVER = false;
        boolean WEEK_OVER = false;
        boolean MONTH_OVER = false;

        log.info( "analyseVisitorStatInfo prevHour :" + prevHour );

        if( prevHour )
        {
            if( hour.intValue() == 0 )
            {
                DAY_OVER = true;
                // 获取上一天的日期
                Calendar prevTime = Calendar.getInstance();
                prevTime.setTime( ( Date ) statDao.queryStatDateHelper().get( "currentDay" ) );
                hour = Integer.valueOf( 23 );
                day = Integer.valueOf( prevTime.get( Calendar.DAY_OF_MONTH ) );
                month = Integer.valueOf( prevTime.get( Calendar.MONTH ) + 1 );
                year = Integer.valueOf( prevTime.get( Calendar.YEAR ) );

                // 零点后为星期一,表示一周结束
                if( Calendar.MONDAY == currentTime.get( Calendar.DAY_OF_WEEK ) )
                {
                    WEEK_OVER = true;
                }

                // 零点后为一号,表示一个月结束
                if( 1 == currentTime.get( Calendar.DAY_OF_MONTH ) )
                {
                    MONTH_OVER = true;
                }
            }
            else
            {
                // 当天的日期
                hour = Integer.valueOf( hour.intValue() - 1 );
                day = Integer.valueOf( currentTime.get( Calendar.DAY_OF_MONTH ) );
                year = Integer.valueOf( currentTime.get( Calendar.YEAR ) );
                month = Integer.valueOf( currentTime.get( Calendar.MONTH ) + 1 );
            }
        }

        log.info( "analyseVisitorStatInfo  DAY_OVER first " + DAY_OVER );

        try
        {
            mysqlEngine.beginTransaction();

            List ipCountSiteList = statDao.queryVisitInfoIPCountByHour( year, month, day, hour );

            List uvCountSiteList = statDao.queryVisitInfoUVCountByHour( year, month, day, hour );

            List pvCountSiteList = statDao.queryVisitInfoPVCountByHour( year, month, day, hour );

            Map siteHourViMap = new HashMap();

            Long siteId = null;

            Map vCmap = null;

            Long count = null;

            SiteVisitDisposeBean svdBean = null;

            // 站点小时ip分析
            for ( int ipc = 0; ipc < ipCountSiteList.size(); ipc++ )
            {
                vCmap = ( Map ) ipCountSiteList.get( ipc );

                siteId = ( Long ) vCmap.get( "siteId" );

                count = ( Long ) vCmap.get( "ipCount" );

                svdBean = ( SiteVisitDisposeBean ) siteHourViMap.get( siteId );

                if( svdBean == null )
                {
                    svdBean = new SiteVisitDisposeBean();

                    svdBean.setSiteId( siteId );

                    siteHourViMap.put( siteId, svdBean );
                }

                svdBean.setIpCount( Integer.valueOf( count.intValue() ) );
            }

            // 站点小时uv分析
            for ( int ipc = 0; ipc < uvCountSiteList.size(); ipc++ )
            {
                vCmap = ( Map ) uvCountSiteList.get( ipc );

                siteId = ( Long ) vCmap.get( "siteId" );

                count = ( Long ) vCmap.get( "uvCount" );

                svdBean = ( SiteVisitDisposeBean ) siteHourViMap.get( siteId );

                if( svdBean == null )
                {
                    svdBean = new SiteVisitDisposeBean();

                    svdBean.setSiteId( siteId );

                    siteHourViMap.put( siteId, svdBean );
                }

                svdBean.setUvCount( Integer.valueOf( count.intValue() ) );
            }

            // 站点小时pv分析
            for ( int ipc = 0; ipc < pvCountSiteList.size(); ipc++ )
            {
                vCmap = ( Map ) pvCountSiteList.get( ipc );

                siteId = ( Long ) vCmap.get( "siteId" );

                count = ( Long ) vCmap.get( "pvCount" );

                svdBean = ( SiteVisitDisposeBean ) siteHourViMap.get( siteId );

                if( svdBean == null )
                {
                    svdBean = new SiteVisitDisposeBean();

                    svdBean.setSiteId( siteId );

                    siteHourViMap.put( siteId, svdBean );
                }

                svdBean.setPvCount( Integer.valueOf( count.intValue() ) );
            }

            // 小时分析记录

            List allSiteIdList = siteGroupDao.queryAllSiteBean();

            SiteGroupBean site = null;

            StatVisitorHourAnalysis hourAnalysis = null;

            for ( int si = 0; si < allSiteIdList.size(); si++ )
            {
                site = ( SiteGroupBean ) allSiteIdList.get( si );

                siteId = site.getSiteId();

                // 小时回头客分析

                // List hourUvList = statDao.queryUvidInfoByHour( siteId, year,
                // month, day, hour );

                // String uvid = null;
                //
                // Long uvCount = null;

                // for ( int huv = 0; huv < hourUvList.size(); huv++ )
                // {
                // uvid = ( String ) hourUvList.get( huv );
                //
                // uvCount = statDao.queryUvidTarceExist( uvid );
                //
                // if( uvCount.longValue() < 1 )
                // {
                // statDao.saveUvidTarce( uvid );
                //
                // newUv++;
                // }
                // else
                // {
                // oldUv++;
                // }
                // }

                // 小时访问PV细节分析
                List hourVInfoList = statDao.queryVisitInfoByHour( siteId, year, month, day, hour );

                StatVisitInfoBean sviBean = null;

                String ip = null;

                String area = null;

                Long existCount = null;

                String host = null;

                Long contentId = null;

                Long classId = null;

                String url = null;

                String os = null;

                String br = null;

                String la = null;

                String reso = null;

                for ( int hvi = 0; hvi < hourVInfoList.size(); hvi++ )
                {
                    sviBean = ( StatVisitInfoBean ) hourVInfoList.get( hvi );
                    // url点击
                    url = sviBean.getUrl();

                    if( StringUtil.isStringNotNull( url ) )
                    {
                        existCount = statDao.queryUrlConutBySiteId( siteId, url );

                        if( existCount.longValue() < 1 )
                        {
                            statDao.saveUrl( siteId, url, Long.valueOf( 1 ) );
                        }
                        else
                        {
                            statDao.updateUrlOnePv( siteId, url );
                        }
                    }

                    // 站群
                    host = sviBean.getHost();

                    if( StringUtil.isStringNotNull( host ) )
                    {
                        existCount = statDao.queryCommonConutBySiteId( siteId, host,
                            "stat_visitor_host_analysis", "host" );

                        if( existCount.longValue() < 1 )
                        {
                            statDao.saveCommonInfo( siteId, host, Long.valueOf( 1 ),
                                "stat_visitor_host_analysis", "host" );
                        }
                        else
                        {
                            statDao.updateCommonOnePv( siteId, host, "stat_visitor_host_analysis",
                                "host" );
                        }
                    }

                    // 区域
                    ip = sviBean.getIp();

                    area = IPSeeker.getInstance().getCountry( ip );

                    if( StringUtil.isStringNotNull( area ) )
                    {
                        existCount = statDao.queryAreaConutBySiteId( siteId, area );

                        if( existCount.longValue() < 1 )
                        {
                            statDao.saveArea( siteId, area, Long.valueOf( 1 ) );
                        }
                        else
                        {
                            statDao.updateAreaOnePv( siteId, area );
                        }
                    }

                    // 栏目
                    classId = sviBean.getClassId();

                    if( classId.longValue() > 0 )
                    {
                        existCount = statDao.queryCommonConutBySiteId( siteId, classId.toString(),
                            "stat_visitor_class_analysis", "classId" );

                        if( existCount.longValue() < 1 )
                        {
                            statDao.saveCommonInfo( siteId, classId.toString(), Long.valueOf( 1 ),
                                "stat_visitor_class_analysis", "classId" );
                        }
                        else
                        {
                            statDao.updateCommonOnePv( siteId, classId.toString(),
                                "stat_visitor_class_analysis", "classId" );
                        }
                    }

                    // 内容
                    contentId = sviBean.getContentId();

                    if( contentId.longValue() > 0 )
                    {
                        existCount = statDao.queryCommonConutBySiteId( siteId,
                            contentId.toString(), "stat_visitor_content_analysis", "contentId" );

                        if( existCount.longValue() < 1 )
                        {
                            statDao.saveCommonInfo( siteId, contentId.toString(),
                                Long.valueOf( 1 ), "stat_visitor_content_analysis", "contentId" );
                        }
                        else
                        {
                            statDao.updateCommonOnePv( siteId, contentId.toString(),
                                "stat_visitor_content_analysis", "contentId" );
                        }
                    }

                    // 操作系统

                    os = sviBean.getSystem();

                    if( StringUtil.isStringNotNull( os ) )
                    {
                        existCount = statDao.queryCommonConutBySiteId( siteId, os,
                            "stat_visitor_os_analysis", "osName" );

                        if( existCount.longValue() < 1 )
                        {
                            statDao.saveCommonInfo( siteId, os, Long.valueOf( 1 ),
                                "stat_visitor_os_analysis", "osName" );
                        }
                        else
                        {
                            statDao.updateCommonOnePv( siteId, os, "stat_visitor_os_analysis",
                                "osName" );
                        }
                    }

                    // 浏览器

                    br = sviBean.getBrowser();

                    if( StringUtil.isStringNotNull( br ) )
                    {

                        existCount = statDao.queryCommonConutBySiteId( siteId, br,
                            "stat_visitor_br_analysis", "brName" );

                        if( existCount.longValue() < 1 )
                        {
                            statDao.saveCommonInfo( siteId, br, Long.valueOf( 1 ),
                                "stat_visitor_br_analysis", "brName" );
                        }
                        else
                        {
                            statDao.updateCommonOnePv( siteId, br, "stat_visitor_br_analysis",
                                "brName" );
                        }
                    }

                    // 分辨率

                    reso = sviBean.getScreen();

                    if( StringUtil.isStringNotNull( reso ) )
                    {
                        existCount = statDao.queryCommonConutBySiteId( siteId, reso,
                            "stat_visitor_resol_analysis", "resVal" );

                        if( existCount.longValue() < 1 )
                        {
                            statDao.saveCommonInfo( siteId, reso, Long.valueOf( 1 ),
                                "stat_visitor_resol_analysis", "resVal" );
                        }
                        else
                        {
                            statDao.updateCommonOnePv( siteId, reso, "stat_visitor_resol_analysis",
                                "resVal" );
                        }
                    }

                    // 语言

                    la = sviBean.getLang();

                    if( StringUtil.isStringNotNull( la ) )
                    {
                        existCount = statDao.queryCommonConutBySiteId( siteId, la,
                            "stat_visitor_la_analysis", "laName" );

                        if( existCount.longValue() < 1 )
                        {
                            statDao.saveCommonInfo( siteId, la, Long.valueOf( 1 ),
                                "stat_visitor_la_analysis", "laName" );
                        }
                        else
                        {
                            statDao.updateCommonOnePv( siteId, la, "stat_visitor_la_analysis",
                                "laName" );
                        }
                    }

                }

                // 来源分类 TODO 1.3版本实现

                // 记录小时数据
                svdBean = ( SiteVisitDisposeBean ) siteHourViMap.get( siteId );

                int newUv = statDao.queryNewUvidCountByHour( siteId, year, month, day, hour )
                    .intValue();

                int oldUv = statDao.queryOldUvidCountByHour( siteId, year, month, day, hour )
                    .intValue();

                log.info( "analyseVisitorStatInfo svdBean :" + svdBean );

                if( svdBean == null )
                {
                    // 站点无访问信息
                    hourAnalysis = new StatVisitorHourAnalysis();

                    hourAnalysis.setIpCount( Integer.valueOf( 0 ) );
                    hourAnalysis.setUvCount( Integer.valueOf( 0 ) );
                    hourAnalysis.setPvCount( Integer.valueOf( 0 ) );

                    hourAnalysis.setVisitYear( year );
                    hourAnalysis.setVisitMonth( month );
                    hourAnalysis.setVisitDay( day );
                    hourAnalysis.setVisitHour( hour );

                    hourAnalysis.setNewUv( Integer.valueOf( newUv ) );
                    hourAnalysis.setOldUv( Integer.valueOf( oldUv ) );

                    hourAnalysis.setSiteId( siteId );

                    log.info( "analyseVisitorStatInfo new hourAnalysis :" + hourAnalysis );

                    statDao.save( hourAnalysis );
                }
                else
                {
                    // 站点有信息
                    StatVisitorHourAnalysisBean currentHourAnalysisBean = statDao
                        .queryVisitorHourAnalysisBeanByDate( siteId, year, month, day, hour );

                    if( currentHourAnalysisBean == null )
                    {
                        hourAnalysis = new StatVisitorHourAnalysis();

                        hourAnalysis.setIpCount( svdBean.getIpCount() );
                        hourAnalysis.setUvCount( svdBean.getUvCount() );
                        hourAnalysis.setPvCount( svdBean.getPvCount() );

                        hourAnalysis.setVisitYear( year );
                        hourAnalysis.setVisitMonth( month );
                        hourAnalysis.setVisitDay( day );
                        hourAnalysis.setVisitHour( hour );

                        hourAnalysis.setNewUv( Integer.valueOf( newUv ) );
                        hourAnalysis.setOldUv( Integer.valueOf( oldUv ) );

                        hourAnalysis.setSiteId( siteId );

                        log.info( "analyseVisitorStatInfo new hourAnalysis2 :" + hourAnalysis );

                        statDao.save( hourAnalysis );
                    }
                    else
                    {
                        hourAnalysis.setIpCount( svdBean.getIpCount() );
                        hourAnalysis.setUvCount( svdBean.getUvCount() );
                        hourAnalysis.setPvCount( svdBean.getPvCount() );

                        log.info( "analyseVisitorStatInfo update hourAnalysis :" + hourAnalysis );

                        statDao.updateVisitorHourAnalysisBeanByDate( currentHourAnalysisBean );
                    }
                }

            }

            // 当天 实时(每过一小时后按天记录的数据变化) 站点总体pv uv ip
            StatVisitorDayAnalysis dayAnalysis = null;

            Map vciMap = null;

            Integer dayNewUv = null;

            Integer dayOldUv = null;

            for ( int si = 0; si < allSiteIdList.size(); si++ )
            {
                site = ( SiteGroupBean ) allSiteIdList.get( si );

                siteId = site.getSiteId();

                dayAnalysis = new StatVisitorDayAnalysis();

                // 当前所有小时uv回头客分析数据
                dayNewUv = statDao.queryNewUvidCountByDay( siteId, year, month, day ).intValue();

                dayOldUv = statDao.queryOldUvidCountByDay( siteId, year, month, day ).intValue();

                dayAnalysis.setNewUv( dayNewUv );

                dayAnalysis.setOldUv( dayOldUv );

                vciMap = statDao.queryDayVisitCountInfo( siteId, year, month, day );

                dayAnalysis.setSiteId( siteId );

                dayAnalysis.setVisitYear( year );
                dayAnalysis.setVisitMonth( month );
                dayAnalysis.setVisitDay( day );

                dayAnalysis.setIpCount( changeSqlSumResToInt( vciMap.get( "ivc" ) ) );
                dayAnalysis.setUvCount( changeSqlSumResToInt( vciMap.get( "uvc" ) ) );
                dayAnalysis.setPvCount( changeSqlSumResToInt( vciMap.get( "pvc" ) ) );

                dayAnalysis.setVdt( DateAndTimeUtil.getTimestamp( year + "-" + month + "-" + day,
                    DateAndTimeUtil.DEAULT_FORMAT_YMD ) );

                if( statDao.queryDayVisitBeanByDayAndSiteId( siteId, year, month, day ) == null )
                {
                    statDao.save( dayAnalysis );
                }
                else
                {
                    statDao.updateDayVisitBeanByDayAndSiteId( changeSqlSumResToInt( vciMap
                        .get( "uvc" ) ), changeSqlSumResToInt( vciMap.get( "ivc" ) ),
                        changeSqlSumResToInt( vciMap.get( "pvc" ) ), dayNewUv, dayOldUv, site
                            .getSiteId(), year, month, day );
                }

            }

            log.info( "analyseVisitorStatInfo  goto DAY_OVER " );

            // 若是零点,则需要删除分析过的前n天的所有数据
            if( DAY_OVER )
            {
                log.info( "analyseVisitorStatInfo  DAY_OVER: " + DAY_OVER );

                // 综合当天实时站点总体入口出口
                List uvidList = statDao.queryUvidInfoByDay( siteId, year, month, day );

                String uvid = null;

                for ( int uv = 0; uv < uvidList.size(); uv++ )
                {
                    uvid = ( String ) uvidList.get( uv );

                    saveOrUpdateUvidVisInfo( statDao.queryUvidInfoFirstVisitGateUrlByDay( siteId,
                        uvid, year, month, day ), year, month, day, true );

                    saveOrUpdateUvidVisInfo( statDao.queryUvidInfoLastVisitExitUrlByDay( siteId,
                        uvid, year, month, day ), year, month, day, false );
                }

                // 每日统计一次各站点来源信息
                String refferHost = null;

                Long reffHostUvCount = null;

                Long existCount = null;

                log.info( "analyseVisitorStatInfo  allSiteIdList " + allSiteIdList );

                for ( int si = 0; si < allSiteIdList.size(); si++ )
                {
                    site = ( SiteGroupBean ) allSiteIdList.get( si );

                    siteId = site.getSiteId();

                    // 添加今日的新uv
                    statDao.saveNewUvidByDay( siteId, year, month, day );

                    // 来源站

                    List refferHostList = statDao
                        .queryAllRefferHostByDay( siteId, year, month, day );

                    for ( int rf = 0; rf < refferHostList.size(); rf++ )
                    {
                        refferHost = ( String ) refferHostList.get( rf );

                        if( StringUtil.isStringNotNull( refferHost ) )
                        {
                            reffHostUvCount = statDao.queryRefferHostUvidCountByDay( siteId, year,
                                month, day, refferHost );

                            existCount = statDao.queryCommonConutBySiteId( siteId, refferHost,
                                "stat_visitor_src_s_analysis", "siteUrl" );

                            if( existCount.longValue() < 1 )
                            {
                                statDao.saveSiteUrlUvCount( siteId, reffHostUvCount, refferHost );
                            }
                            else
                            {
                                statDao.updateSiteUrlUvCount( siteId, reffHostUvCount, refferHost );
                            }
                        }
                    }

                    // 搜索引擎
                    refferHostList = statDao.queryAllSearchHostByDay( siteId, year, month, day );

                    for ( int rf = 0; rf < refferHostList.size(); rf++ )
                    {
                        refferHost = ( String ) refferHostList.get( rf );

                        if( StringUtil.isStringNotNull( refferHost ) )
                        {
                            reffHostUvCount = statDao.queryRefferHostUvidCountByDay( siteId, year,
                                month, day, refferHost );

                            existCount = statDao.queryCommonConutBySiteId( siteId, refferHost,
                                "stat_visitor_search_analysis", "searchName" );

                            if( existCount.longValue() < 1 )
                            {
                                statDao.saveSearchUvCount( siteId, reffHostUvCount, refferHost );
                            }
                            else
                            {
                                statDao.updateSearchUvCount( siteId, reffHostUvCount, refferHost );
                            }
                        }
                    }

                    // 搜索词
                    refferHostList = statDao.queryAllSearchKeyByDay( siteId, year, month, day );

                    for ( int rf = 0; rf < refferHostList.size(); rf++ )
                    {
                        refferHost = ( String ) refferHostList.get( rf );

                        if( StringUtil.isStringNotNull( refferHost ) )
                        {
                            reffHostUvCount = statDao.queryRefferKeyUvidCountByDay( siteId, year,
                                month, day, refferHost );

                            existCount = statDao.queryCommonConutBySiteId( siteId, refferHost,
                                "stat_visitor_seh_key_analysis", "keyVal" );

                            if( existCount.longValue() < 1 )
                            {
                                statDao.saveSearchKeyUvCount( siteId, reffHostUvCount, refferHost );
                            }
                            else
                            {
                                statDao
                                    .updateSearchKeyUvCount( siteId, reffHostUvCount, refferHost );
                            }
                        }
                    }
                }

                // 删除访问细节
                Calendar currTime = Calendar.getInstance();

                currTime.add( Calendar.DAY_OF_MONTH, -3 );// 删除三天前的访问信息

                log.info( "analyseVisitorStatInfo  delete other " + currTime );

                statDao.deleteVisitorInfoByBeforeDate( currTime.getTime() );

                // 更新目前日期记录
                statDao.deleteStatDateHelperAllDate();

                statDao.saveNewDateForStatDateHelper( null, new Date( DateAndTimeUtil
                    .clusterTimeMillis() ) );

                // 更新今天日点击数为0
                statDao.updateAllContentDayClickCountToZero();

                // 更新今天日评论数为0
                statDao.updateAllContentDayCommCountToZero();

                log.info( "analyseVisitorStatInfo  exe over " + currTime );

                if( WEEK_OVER )
                {
                    // 若新的一周,更新今天周点击数为0
                    statDao.updateAllContentWeekClickCountToZero();

                    // 若新的一周,更新今天周评论数为0
                    statDao.updateAllContentWeekCommCountToZero();
                }

                if( MONTH_OVER )
                {
                    // 若新的一月,更新今天月点击数为0
                    statDao.updateAllContentMonthClickCountToZero();

                    // 若新的一月,更新今天月评论数为0
                    statDao.updateAllContentMonthCommCountToZero();

                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        // 更新缓存
        ContentDao.releaseOrderFilterCountCache();
        ContentService.releaseFastListContentCache();

        /**
         * 早上4点执行删除记录的已删站点以及栏目删除记录并删除三个月前操作记录
         */

        if( hour == 4 )
        {
            siteService.deleteCMSSiteAllInfo();

            deleteSysFlowExcuteTrace( -90 );
        }

        /**
         * 每天3 13点更新一次工作量缓存 ，3点重新统计工作量
         */
        if( hour == 3 )
        {
            initSiteContentStatTrace();
        }

        if( hour == 3 || hour == 13 )
        {
            cache.clearAllEntry();
        }

        // 自动备份
        ResourcesService.getInstance().zipAllSiteResource( false );

    }

    public void saveOrUpdateUvidVisInfo( Map vidInfo, Integer y, Integer m, Integer d, boolean gate )
    {
        String url = ( String ) vidInfo.get( "url" );

        Long siteId = ( Long ) vidInfo.get( "siteId" );

        Long count = null;

        if( gate )
        {
            count = statDao.queryUvidInfoFirstVisitGateExist( siteId, y, m, d, url );

            if( count.longValue() < 1 )
            {
                statDao.saveUvidVisitGate( siteId, y, m, d, url, Integer.valueOf( 1 ) );
            }
            else
            {
                statDao.updateUvidVisitGate( siteId, y, m, d, url );
            }
        }
        else
        {
            count = statDao.queryUvidInfoExitVisitGateExist( siteId, y, m, d, url );

            if( count.longValue() < 1 )
            {
                statDao.saveUvidVisitExit( siteId, y, m, d, url, Integer.valueOf( 1 ) );
            }
            else
            {
                statDao.updateUvidVisitExit( siteId, y, m, d, url );
            }
        }
    }

    public void updateCurrentDateForHelper()
    {
        try
        {
            mysqlEngine.beginTransaction();

            statDao.deleteStatDateHelperAllDate();
            statDao.saveNewDateForStatDateHelper( null, new Date( DateAndTimeUtil
                .clusterTimeMillis() ) );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }
    }

    public void addSysFlowExcuteTrace()
    {

        List<FlowTraceBean> tempInfoList = null;

        synchronized ( traceTemp )
        {

            tempInfoList = new ArrayList( traceTemp );

            traceTemp.clear();

        }

        try
        {
            mysqlEngine.beginTransaction();

            mysqlEngine.startBatch();

            for ( FlowTraceBean ftb : tempInfoList )
            {
                statDao.saveFlowExcuteTrace( ftb.getFlowActUserName(), ftb.getFlowCom(), ftb
                    .getFlowName(), ftb.getExeDt(), ftb.getIp(), ftb.getParamInfo(), ftb
                    .getSiteId() );
            }

            mysqlEngine.executeBatch();

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void deleteSysFlowExcuteTrace( int beforeDayCount )
    {
        String beforeDay = DateAndTimeUtil.getTheDayByNum( DateAndTimeUtil
            .getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD ), -10 );

        statDao.deleteFlowExcuteTraceByDay( DateAndTimeUtil.getTimestamp( beforeDay,
            DateAndTimeUtil.DEAULT_FORMAT_YMD ) );
    }

    public List retrieveBlackClientIpInfo()
    {
        return statDao.queryBlackClientIpInfo();
    }

    public List retrieveBlackClientIpStr()
    {
        return statDao.queryBlackClientIpStr();
    }

    public void addBlackIpTrace( String ip, Date startDT, Integer effectHour, Integer isDeForever,
        String targetUrl, String dangerStr, String queryStr )
    {
        try
        {
            mysqlEngine.beginTransaction();

            Long ipCount = statDao.queryBlackClientIpCount( ip );

            if( ipCount.longValue() < 1 )
            {
                boolean notCollAcc = ( targetUrl.indexOf( "/stat/collStat." ) == -1 ) ? true
                    : false;

                if( notCollAcc )
                {
                    statDao.saveBlackIp( ip, startDT, DateAndTimeUtil.getTheHourByNum( startDT,
                        effectHour.intValue() ), effectHour, isDeForever );
                }

                if( notCollAcc )
                {
                    statDao.saveBlackIpAccess( ip, targetUrl, SystemSafeCharUtil
                        .filterHTML( dangerStr ), SystemSafeCharUtil.filterHTML( queryStr ),
                        startDT );
                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            InitClientIpAccessBehavior.disposeBlackIpAccess();
        }

        // 发送系统消息,提醒所有机构管理员,有攻击!

        messageService.sendManagerMessage( Long.valueOf( -9999 ), "系统事件消息", "拦截到非法访问!", "于 "
            + DateAndTimeUtil.formatTimestamp(
                new Timestamp( DateAndTimeUtil.clusterTimeMillis() ),
                DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS )
            + " 发现疑似非法访问,请相关人员进入 [安全与日志] 查看记录并处理,此消息由系统发送,请勿回复。", securityService
            .retrieveAllSystemOrgBossUserBean() );

    }

    public void addNewBlackIp( List ipsList, Integer effectHour, Integer forever )
    {
        if( ipsList == null )
        {
            return;
        }

        String ip = null;

        Date currDate = new Date( DateAndTimeUtil.clusterTimeMillis() );

        try
        {
            mysqlEngine.beginTransaction();

            for ( int i = 0; i < ipsList.size(); i++ )
            {
                ip = ( String ) ipsList.get( i );

                if( StringUtil.isStringNull( ip ) )
                {
                    continue;
                }

                ip = ip.trim();

                if( !RegexUtil.match( ip, REG_IP ) )
                {
                    continue;
                }

                statDao.deleteBlackClientIp( ip );

                if( Constant.COMMON.ON.equals( forever ) )
                {
                    statDao.saveBlackIp( ip, new Timestamp( currDate.getTime() ), DateAndTimeUtil
                        .getTheHourByNum( currDate, effectHour.intValue() ), Integer.valueOf( 0 ),
                        Constant.COMMON.ON );
                }
                else
                {
                    statDao.saveBlackIp( ip, new Timestamp( currDate.getTime() ), DateAndTimeUtil
                        .getTheHourByNum( currDate, effectHour.intValue() ), effectHour,
                        Constant.COMMON.OFF );
                }

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            InitClientIpAccessBehavior.disposeBlackIpAccess();
        }

    }

    public void deleteBlackIp( List ipsList )
    {
        if( ipsList == null )
        {
            return;
        }

        String ip = null;

        try
        {
            mysqlEngine.beginTransaction();

            for ( int i = 0; i < ipsList.size(); i++ )
            {
                ip = ( String ) ipsList.get( i );

                statDao.deleteBlackClientIp( ip );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            InitClientIpAccessBehavior.disposeBlackIpAccess();
        }
    }

    public void disposeLiftBanBlackClientIpInfo( Timestamp currTime )
    {
        try
        {
            mysqlEngine.beginTransaction();

            List ciList = statDao.queryLiftBanBlackClientIpInfo( currTime );

            Map ciInfo = null;

            for ( int ci = 0; ci < ciList.size(); ci++ )
            {
                ciInfo = ( Map ) ciList.get( ci );

                statDao.deleteBlackClientIp( ( String ) ciInfo.get( "blackIp" ) );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            InitClientIpAccessBehavior.disposeBlackIpAccess();
        }

    }

    public Object getSiteClientIpDangerAccessTraceTag( String trId, String eventDate, String pn,
        String size )
    {
        Timestamp eventDateTs = DateAndTimeUtil.getTimestamp( eventDate,
            DateAndTimeUtil.DEAULT_FORMAT_YMD );

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        Long count = null;

        List result = null;

        if( StringUtil.isStringNotNull( trId ) )
        {
            Map accInfo = statDao.querySingleClientIpDangerAccessTrace( Long.valueOf( StringUtil
                .getLongValue( trId, -1 ) ) );

            accInfo.put( "dangerStr", SystemSafeCharUtil.resumeHTML( ( String ) accInfo
                .get( "dangerStr" ) ) );

            return accInfo;
        }
        else if( eventDateTs != null )
        {
            count = statDao.queryClientIpDangerAccessTraceCount( eventDateTs );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = statDao.queryBlackIpAccessInfoList( eventDateTs, Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageSize ) );
        }
        else
        {
            count = statDao.queryClientIpDangerAccessTraceCount();

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = statDao.queryBlackIpAccessInfoList( Long.valueOf( pageInfo.getFirstResult() ),
                Integer.valueOf( pageSize ) );
        }

        return new Object[] { result, pageInfo };
    }

    public Object getSiteClientBlackIpInfoTag( String targetIp, String pn, String size )
    {
        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        Long count = null;

        List result = null;

        if( StringUtil.isStringNotNull( targetIp ) )
        {
            Map resMap = statDao.querySingleBlackClientIpInfo( targetIp );

            if( resMap.isEmpty() )
            {
                return new ArrayList();
            }
            else
            {
                return resMap;
            }
        }
        else
        {
            count = statDao.queryClientIpInfoCount();

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = statDao.queryBlackIpInfoList( Long.valueOf( pageInfo.getFirstResult() ),
                Integer.valueOf( pageSize ) );
        }

        return new Object[] { result, pageInfo };
    }

    public void addManagerLoginTrace( String userName, String ip, Integer ls )
    {
        statDao.saveManagerLoginTrace( ip, userName, new Timestamp( DateAndTimeUtil
            .clusterTimeMillis() ), ls );
    }

    public Object getManagerLoginTraceTag( String userName, String pn, String size )
    {
        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        Long count = null;

        List result = null;

        userName = SystemSafeCharUtil.decodeDangerChar( userName );

        if( StringUtil.isStringNotNull( userName ) )
        {
            count = statDao.queryManagerLoginTraceCount( userName.trim() );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = statDao.queryManagerLoginTrace( userName.trim(), Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageSize ) );
        }
        else
        {
            count = statDao.queryManagerLoginTraceCount();

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = statDao.queryManagerLoginTrace( Long.valueOf( pageInfo.getFirstResult() ),
                Integer.valueOf( pageSize ) );
        }

        return new Object[] { result, pageInfo };
    }

    public Object getFlowExtTraceTag( String userName, String pn, String size )
    {
        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        Long count = null;

        List result = null;

        userName = SystemSafeCharUtil.decodeDangerChar( userName );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( StringUtil.isStringNotNull( userName ) )
        {
            count = statDao.queryFlowExcuteTraceCount( userName.trim(), site.getSiteId() );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = statDao.queryFlowExcuteTrace( userName.trim(), site.getSiteId(), Long
                .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );
        }
        else
        {
            count = statDao.queryFlowExcuteTraceCount( site.getSiteId() );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = statDao.queryFlowExcuteTrace( site.getSiteId(), Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageSize ) );
        }

        return new Object[] { result, pageInfo };
    }

    public Object getSiteDayVisitInfoTag( String sd, String ed, String day, String reverse )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Timestamp sdt = DateAndTimeUtil.getTimestamp( sd, DateAndTimeUtil.DEAULT_FORMAT_YMD );

        Timestamp edt = DateAndTimeUtil.getTimestamp( ed, DateAndTimeUtil.DEAULT_FORMAT_YMD );

        List dvBeanList = null;

        if( sdt != null && edt != null )
        {

            dvBeanList = statDao.queryDayVisitBeanListByDayAndSiteId( site.getSiteId(), sdt, edt );

        }
        else
        {

            Calendar currCal = Calendar.getInstance();

            currCal.set( Calendar.HOUR_OF_DAY, 23 );
            currCal.set( Calendar.SECOND, 59 );
            currCal.set( Calendar.MINUTE, 59 );

            Date endDate = currCal.getTime();

            int dayVal = 12;

            if( StringUtil.isStringNotNull( day ) )
            {
                dayVal = StringUtil.getIntValue( day, 12 );
            }

            currCal.add( Calendar.DAY_OF_MONTH, -dayVal );

            currCal.set( Calendar.HOUR_OF_DAY, 0 );
            currCal.set( Calendar.SECOND, 0 );
            currCal.set( Calendar.MINUTE, 0 );

            Date startDate = currCal.getTime();

            dvBeanList = statDao.queryDayVisitBeanListByDayAndSiteId( site.getSiteId(), startDate,
                endDate );
        }

        if( "true".equals( reverse ) )
        {
            Collections.reverse( dvBeanList );
        }

        return dvBeanList;
    }

    public Object getSiteDayBackManVisitInfoTag( String sd, String ed, String day, String reverse )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Timestamp sdt = DateAndTimeUtil.getTimestamp( sd, DateAndTimeUtil.DEAULT_FORMAT_YMD );

        Timestamp edt = DateAndTimeUtil.getTimestamp( ed, DateAndTimeUtil.DEAULT_FORMAT_YMD );

        List uvList = null;

        if( sdt != null && edt != null )
        {

            uvList = statDao.queryDayVisitBeanListByDayAndSiteId( site.getSiteId(), sdt, edt );

        }
        else
        {

            Calendar currCal = Calendar.getInstance();

            currCal.set( Calendar.HOUR_OF_DAY, 23 );
            currCal.set( Calendar.SECOND, 59 );
            currCal.set( Calendar.MINUTE, 59 );

            Date endDate = currCal.getTime();

            int dayVal = 12;

            if( StringUtil.isStringNotNull( day ) )
            {
                dayVal = StringUtil.getIntValue( day, 12 );
            }

            currCal.add( Calendar.DAY_OF_MONTH, -dayVal );

            currCal.set( Calendar.HOUR_OF_DAY, 0 );
            currCal.set( Calendar.SECOND, 0 );
            currCal.set( Calendar.MINUTE, 0 );

            Date startDate = currCal.getTime();

            uvList = statDao.queryDayVisitBeanListByDayAndSiteId( site.getSiteId(), startDate,
                endDate );

        }

        if( "true".equals( reverse ) )
        {
            Collections.reverse( uvList );
        }

        return uvList;
    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteAllVisitInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Map res = new HashMap();

        Calendar currCal = Calendar.getInstance();

        Integer day = Integer.valueOf( currCal.get( Calendar.DAY_OF_MONTH ) );
        Integer month = Integer.valueOf( currCal.get( Calendar.MONTH ) + 1 );
        Integer year = Integer.valueOf( currCal.get( Calendar.YEAR ) );

        currCal.set( Calendar.HOUR_OF_DAY, 23 );
        currCal.set( Calendar.SECOND, 59 );
        currCal.set( Calendar.MINUTE, 59 );

        Date ed = currCal.getTime();

        // 总统计
        Map allDay = statDao.querySiteAllPvUvIpCount( site.getSiteId() );

        Long newUv = MathUtil.changeSUMResToLong( allDay.get( "newUv" ) );

        Long oldUv = MathUtil.changeSUMResToLong( allDay.get( "oldUv" ) );

        double ov = ( newUv.longValue() + oldUv.longValue() ) != 0 ? MathUtil.mul( MathUtil.div(
            StringUtil.getDoubleValue( oldUv.toString(), 0 ), StringUtil.getDoubleValue( ""
                + ( newUv.longValue() + oldUv.longValue() ), 0 ), 4 ), 100 ) : 0.0;

        allDay.put( "ov", ov + "%" );

        currCal.add( Calendar.DAY_OF_MONTH, -7 );

        currCal.set( Calendar.HOUR_OF_DAY, 0 );
        currCal.set( Calendar.SECOND, 0 );
        currCal.set( Calendar.MINUTE, 0 );

        Date sd = currCal.getTime();

        Map currWeek = statDao.querySiteAllPvUvIpWeek7DayCount( site.getSiteId(), sd, ed );

        newUv = MathUtil.changeSUMResToLong( currWeek.get( "newUv" ) );

        oldUv = MathUtil.changeSUMResToLong( currWeek.get( "oldUv" ) );

        ov = ( newUv.longValue() + oldUv.longValue() ) != 0 ? MathUtil.mul( MathUtil.div(
            StringUtil.getDoubleValue( oldUv.toString(), 0 ), StringUtil.getDoubleValue( ""
                + ( newUv.longValue() + oldUv.longValue() ), 0 ), 4 ), 100 ) : 0.0;

        currWeek.put( "ov", ov + "%" );

        Map currDay = statDao.querySiteAllPvUvIpCurrDayCount( site.getSiteId(), year, month, day );

        newUv = MathUtil.changeSUMResToLong( currDay.get( "newUv" ) );

        oldUv = MathUtil.changeSUMResToLong( currDay.get( "oldUv" ) );

        ov = ( newUv.longValue() + oldUv.longValue() ) != 0 ? MathUtil.mul( MathUtil.div(
            StringUtil.getDoubleValue( oldUv.toString(), 0 ), StringUtil.getDoubleValue( ""
                + ( newUv.longValue() + oldUv.longValue() ), 0 ), 4 ), 100 ) : 0.0;

        currDay.put( "ov", ov + "%" );

        Map currMonth = statDao.querySiteAllPvUvIpMonthCount( site.getSiteId(), year, month );

        newUv = MathUtil.changeSUMResToLong( currMonth.get( "newUv" ) );

        oldUv = MathUtil.changeSUMResToLong( currMonth.get( "oldUv" ) );

        ov = ( newUv.longValue() + oldUv.longValue() ) != 0 ? MathUtil.mul( MathUtil.div(
            StringUtil.getDoubleValue( oldUv.toString(), 0 ), StringUtil.getDoubleValue( ""
                + ( newUv.longValue() + oldUv.longValue() ), 0 ), 4 ), 100 ) : 0.0;

        currMonth.put( "ov", ov + "%" );

        // per
        Long dayCount = statDao.querySiteAllDayCount( site.getSiteId() );

        Map per = new HashMap();

        if( dayCount.longValue() != 0 )
        {
            Long allPv = MathUtil.changeSUMResToLong( allDay.get( "pv" ) );

            Long allUv = MathUtil.changeSUMResToLong( allDay.get( "uv" ) );

            Long allIp = MathUtil.changeSUMResToLong( allDay.get( "ip" ) );

            per.put( "pvp", Double.valueOf(
                StringUtil.getDoubleValue( MathUtil.div( allPv.doubleValue(), dayCount
                    .doubleValue(), 0 )
                    + "", 0 ) ).longValue() );

            per.put( "uvp", Double.valueOf(
                StringUtil.getDoubleValue( MathUtil.div( allUv.doubleValue(), dayCount
                    .doubleValue(), 0 )
                    + "", 0 ) ).longValue() );

            per.put( "ipp", Double.valueOf(
                StringUtil.getDoubleValue( MathUtil.div( allIp.doubleValue(), dayCount
                    .doubleValue(), 0 )
                    + "", 0 ) ).longValue() );

        }

        res.put( "per", per );

        // max

        Map mpvMap = statDao.querySiteMaxPvCount( site.getSiteId() );

        res.put( "mpv", mpvMap );

        Map muvMap = statDao.querySiteMaxUvCount( site.getSiteId() );

        res.put( "muv", muvMap );

        Map mipMap = statDao.querySiteMaxIpCount( site.getSiteId() );

        res.put( "mip", mipMap );

        res.put( "all", allDay );
        res.put( "week", currWeek );
        res.put( "month", currMonth );
        res.put( "day", currDay );

        return res;
    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteAllVisitInfoTag( SiteGroupBean site )
    {
        Map res = new HashMap();

        if( site == null )
        {
            return res;
        }

        Calendar currCal = Calendar.getInstance();

        Integer day = Integer.valueOf( currCal.get( Calendar.DAY_OF_MONTH ) );
        Integer month = Integer.valueOf( currCal.get( Calendar.MONTH ) + 1 );
        Integer year = Integer.valueOf( currCal.get( Calendar.YEAR ) );

        currCal.set( Calendar.HOUR_OF_DAY, 23 );
        currCal.set( Calendar.SECOND, 59 );
        currCal.set( Calendar.MINUTE, 59 );

        Date ed = currCal.getTime();

        // 总统计
        Map allDay = statDao.querySiteAllPvUvIpCount( site.getSiteId() );

        Long newUv = MathUtil.changeSUMResToLong( allDay.get( "newUv" ) );

        Long oldUv = MathUtil.changeSUMResToLong( allDay.get( "oldUv" ) );

        double ov = ( newUv.longValue() + oldUv.longValue() ) != 0 ? MathUtil.mul( MathUtil.div(
            StringUtil.getDoubleValue( oldUv.toString(), 0 ), StringUtil.getDoubleValue( ""
                + ( newUv.longValue() + oldUv.longValue() ), 0 ), 4 ), 100 ) : 0.0;

        allDay.put( "ov", ov + "%" );

        currCal.add( Calendar.DAY_OF_MONTH, -7 );

        currCal.set( Calendar.HOUR_OF_DAY, 0 );
        currCal.set( Calendar.SECOND, 0 );
        currCal.set( Calendar.MINUTE, 0 );

        Date sd = currCal.getTime();

        Map currWeek = statDao.querySiteAllPvUvIpWeek7DayCount( site.getSiteId(), sd, ed );

        newUv = MathUtil.changeSUMResToLong( currWeek.get( "newUv" ) );

        oldUv = MathUtil.changeSUMResToLong( currWeek.get( "oldUv" ) );

        ov = ( newUv.longValue() + oldUv.longValue() ) != 0 ? MathUtil.mul( MathUtil.div(
            StringUtil.getDoubleValue( oldUv.toString(), 0 ), StringUtil.getDoubleValue( ""
                + ( newUv.longValue() + oldUv.longValue() ), 0 ), 4 ), 100 ) : 0.0;

        currWeek.put( "ov", ov + "%" );

        Map currDay = statDao.querySiteAllPvUvIpCurrDayCount( site.getSiteId(), year, month, day );

        newUv = MathUtil.changeSUMResToLong( currDay.get( "newUv" ) );

        oldUv = MathUtil.changeSUMResToLong( currDay.get( "oldUv" ) );

        ov = ( newUv.longValue() + oldUv.longValue() ) != 0 ? MathUtil.mul( MathUtil.div(
            StringUtil.getDoubleValue( oldUv.toString(), 0 ), StringUtil.getDoubleValue( ""
                + ( newUv.longValue() + oldUv.longValue() ), 0 ), 4 ), 100 ) : 0.0;

        currDay.put( "ov", ov + "%" );

        Map currMonth = statDao.querySiteAllPvUvIpMonthCount( site.getSiteId(), year, month );

        newUv = MathUtil.changeSUMResToLong( currMonth.get( "newUv" ) );

        oldUv = MathUtil.changeSUMResToLong( currMonth.get( "oldUv" ) );

        ov = ( newUv.longValue() + oldUv.longValue() ) != 0 ? MathUtil.mul( MathUtil.div(
            StringUtil.getDoubleValue( oldUv.toString(), 0 ), StringUtil.getDoubleValue( ""
                + ( newUv.longValue() + oldUv.longValue() ), 0 ), 4 ), 100 ) : 0.0;

        currMonth.put( "ov", ov + "%" );

        // per
        Long dayCount = statDao.querySiteAllDayCount( site.getSiteId() );

        Map per = new HashMap();

        if( dayCount.longValue() != 0 )
        {
            Long allPv = MathUtil.changeSUMResToLong( allDay.get( "pv" ) );

            Long allUv = MathUtil.changeSUMResToLong( allDay.get( "uv" ) );

            Long allIp = MathUtil.changeSUMResToLong( allDay.get( "ip" ) );

            per.put( "pvp", Double.valueOf(
                StringUtil.getDoubleValue( MathUtil.div( allPv.doubleValue(), dayCount
                    .doubleValue(), 0 )
                    + "", 0 ) ).longValue() );

            per.put( "uvp", Double.valueOf(
                StringUtil.getDoubleValue( MathUtil.div( allUv.doubleValue(), dayCount
                    .doubleValue(), 0 )
                    + "", 0 ) ).longValue() );

            per.put( "ipp", Double.valueOf(
                StringUtil.getDoubleValue( MathUtil.div( allIp.doubleValue(), dayCount
                    .doubleValue(), 0 )
                    + "", 0 ) ).longValue() );

        }

        res.put( "per", per );

        // max

        Map mpvMap = statDao.querySiteMaxPvCount( site.getSiteId() );

        res.put( "mpv", mpvMap );

        Map muvMap = statDao.querySiteMaxUvCount( site.getSiteId() );

        res.put( "muv", muvMap );

        Map mipMap = statDao.querySiteMaxIpCount( site.getSiteId() );

        res.put( "mip", mipMap );

        res.put( "all", allDay );
        res.put( "week", currWeek );
        res.put( "month", currMonth );
        res.put( "day", currDay );

        return res;
    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteHostVisitInfoTag()
    {
        Long allPvCount = statDao.queryAllSiteHostPvCount();

        List allHost = statDao.querySiteAllHost();

        String host = null;

        Long count = null;

        List res = new ArrayList();

        Map hostPv = null;

        for ( int i = 0; i < allHost.size(); i++ )
        {
            hostPv = new HashMap();

            host = ( String ) allHost.get( i );

            hostPv.put( "host", host );

            count = statDao.querySiteHostPvCount( host );

            hostPv.put( "pv", count );

            double ov = ( allPvCount.longValue() ) != 0 ? MathUtil.mul( MathUtil.div( StringUtil
                .getDoubleValue( count.toString(), 0 ), StringUtil.getDoubleValue( ""
                + ( allPvCount.longValue() ), 0 ), 4 ), 100 ) : 0.0;

            hostPv.put( "per", ov );
            Collections.reverse( res );
            res.add( hostPv );

        }

        return res;

    }

    public Object getSiteVisitInfoTag( String pn, String size )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        Long count = statDao.querySiteVisitorCount( site.getSiteId() );

        pageInfo = new Page( pageSize, count.intValue(), pageNum );

        List result = statDao.querySiteVisitor( site.getSiteId(), Long.valueOf( pageInfo
            .getFirstResult() ), Integer.valueOf( pageSize ) );

        return new Object[] { result, pageInfo };

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitHourInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        List res = new ArrayList();

        Map valMap = null;

        Map uvMap = null;

        long newUv = 0;

        long oldUv = 0;

        uvMap = statDao.querySiteVistorHourSumUvBySite( site.getSiteId() );

        newUv = MathUtil.changeSUMResToLong( uvMap.get( "nuv" ) ).longValue();

        oldUv = MathUtil.changeSUMResToLong( uvMap.get( "ouv" ) ).longValue();

        long allUv = newUv + oldUv;

        for ( int i = 0; i < 24; i++ )
        {
            valMap = statDao.querySiteVistorSumAllByHour( site.getSiteId(), Integer.valueOf( i ) );

            uvMap = statDao.querySiteVistorSumUvByHour( site.getSiteId(), Integer.valueOf( i ) );

            double ov = ( allUv != 0 ) ? MathUtil.mul( MathUtil.div( StringUtil.getDoubleValue(
                MathUtil.changeSUMResToLong( uvMap.get( "ouv" ) ).toString(), 0 ), StringUtil
                .getDoubleValue( "" + allUv, 0 ), 4 ), 100 ) : 0.0;

            valMap.put( "uvPer", ov );

            if( valMap.get( "pvc" ) == null )
            {
                valMap.put( "pvc", 0 );
            }

            if( valMap.get( "uvc" ) == null )
            {
                valMap.put( "uvc", 0 );
            }

            if( valMap.get( "ivc" ) == null )
            {
                valMap.put( "ivc", 0 );
            }

            res.add( valMap );
        }

        return res;

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitAeraInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long allPvCount = statDao.querySiteVistorAeraSumPvBySite( site.getSiteId() );

        List aeraPvList = statDao.querySiteVistorAeraAllPvBySite( site.getSiteId() );

        Map valMap = null;

        Long pv = null;

        for ( int i = 0; i < aeraPvList.size(); i++ )
        {
            valMap = ( Map ) aeraPvList.get( i );

            pv = ( Long ) valMap.get( "pvCount" );

            double ov = ( allPvCount.longValue() ) != 0 ? MathUtil.mul( MathUtil.div( StringUtil
                .getDoubleValue( pv.toString(), 0 ), StringUtil.getDoubleValue( allPvCount
                .toString(), 0 ), 4 ), 100 ) : 0.0;

            valMap.put( "pvPer", ov );

        }

        return aeraPvList;

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitProvAeraInfoTag()
    {

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        List aeraPvList = new ArrayList();

        Map valMap = null;

        for ( String prov : PROVS )
        {
            Long allPvCount = statDao
                .querySiteVistorAeraSumPvBySiteAndArea( site.getSiteId(), prov );

            valMap = new HashMap( 2 );

            valMap.put( "area", prov );

            valMap.put( "pv", allPvCount );

            aeraPvList.add( valMap );

        }

        return aeraPvList;

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitReffHostUvAllInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long allUvCount = statDao.querySiteVistorReffHostSumUvBySite( site.getSiteId() );

        List aeraUvList = statDao.querySiteVistorReffHostAllUvBySite( site.getSiteId() );

        Map valMap = null;

        Long pv = null;

        for ( int i = 0; i < aeraUvList.size(); i++ )
        {
            valMap = ( Map ) aeraUvList.get( i );

            pv = ( Long ) valMap.get( "uvCount" );

            double ov = ( allUvCount.longValue() ) != 0 ? MathUtil.mul( MathUtil.div( StringUtil
                .getDoubleValue( pv.toString(), 0 ), StringUtil.getDoubleValue( allUvCount
                .toString(), 0 ), 4 ), 100 ) : 0.0;

            valMap.put( "uvPer", ov );

        }

        return aeraUvList;

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitReffHostUvInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long allUvCount = statDao.querySiteVistorReffHostSumUvBySite( site.getSiteId() );

        List aeraUvList = statDao.querySiteVistorReffHostAllUvBySite( site.getSiteId() );

        Map valMap = null;

        Long pv = null;

        List ovList = new ArrayList();

        Map tempOvMap = new HashMap();

        List endAeraUvList = new ArrayList();

        for ( int i = 0; i < aeraUvList.size(); i++ )
        {
            valMap = ( Map ) aeraUvList.get( i );

            pv = ( Long ) valMap.get( "uvCount" );

            double ov = ( allUvCount.longValue() ) != 0 ? MathUtil.mul( MathUtil.div( StringUtil
                .getDoubleValue( pv.toString(), 0 ), StringUtil.getDoubleValue( allUvCount
                .toString(), 0 ), 4 ), 100 ) : 0.0;

            ovList.add( Double.valueOf( ov ) );

            valMap.put( "uvPer", ov );

            tempOvMap.put( Double.valueOf( ov ), valMap );
        }

        Collections.sort( ovList );

        Collections.reverse( ovList );

        double othePer = 0;

        for ( int i = 0; i < ovList.size(); i++ )
        {
            if( i + 1 <= 5 )
            {
                endAeraUvList.add( tempOvMap.get( ovList.get( i ) ) );
            }
            else
            {
                othePer += ( ( Double ) ovList.get( i ) ).doubleValue();
            }
        }

        Map other = new HashMap();

        other.put( "siteUrl", "其他" );

        other.put( "siteId", site.getSiteId() );
        other.put( "uvCount", "" );

        other.put( "uvPer", Double.valueOf( othePer ) );

        endAeraUvList.add( other );

        return endAeraUvList;

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitSearchHostUvInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long allUvCount = statDao.querySiteVistorSearchHostSumUvBySite( site.getSiteId() );

        List aeraUvList = statDao.querySiteVistorSearchHostAllUvBySite( site.getSiteId() );

        Map valMap = null;

        Long pv = null;

        String su = null;

        List ovList = new ArrayList();

        Map tempOvMap = new HashMap();

        List endAeraUvList = new ArrayList();

        for ( int i = 0; i < aeraUvList.size(); i++ )
        {
            valMap = ( Map ) aeraUvList.get( i );

            pv = ( Long ) valMap.get( "uvCount" );

            su = ( String ) valMap.get( "searchName" );

            if( su != null && su.indexOf( "baidu.com" ) != -1 )
            {
                valMap.put( "searchName", su + " (百度)" );
            }
            else if( su != null && su.indexOf( "so.com" ) != -1 )
            {
                valMap.put( "searchName", su + " (360搜索)" );
            }
            else if( su != null && su.indexOf( "sogou.com" ) != -1 )
            {
                valMap.put( "searchName", su + " (搜狗)" );
            }
            else if( su != null && su.indexOf( "soso.com" ) != -1 )
            {
                valMap.put( "searchName", su + " (搜搜)" );
            }

            double ov = ( allUvCount.longValue() ) != 0 ? MathUtil.mul( MathUtil.div( StringUtil
                .getDoubleValue( pv.toString(), 0 ), StringUtil.getDoubleValue( allUvCount
                .toString(), 0 ), 4 ), 100 ) : 0.0;

            ovList.add( Double.valueOf( ov ) );

            valMap.put( "uvPer", ov );

            tempOvMap.put( Double.valueOf( ov ), valMap );

        }

        Collections.sort( ovList );

        Collections.reverse( ovList );

        double othePer = 0;

        for ( int i = 0; i < ovList.size(); i++ )
        {
            if( i + 1 <= 5 )
            {
                endAeraUvList.add( tempOvMap.get( ovList.get( i ) ) );
            }
            else
            {
                othePer += ( ( Double ) ovList.get( i ) ).doubleValue();
            }
        }

        Map other = new HashMap();

        other.put( "searchName", "其他" );

        other.put( "siteId", site.getSiteId() );
        other.put( "uvCount", "" );

        other.put( "uvPer", Double.valueOf( othePer ) );

        endAeraUvList.add( other );

        return endAeraUvList;

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitSearchHostUvAllInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long allUvCount = statDao.querySiteVistorSearchHostSumUvBySite( site.getSiteId() );

        List aeraUvList = statDao.querySiteVistorSearchHostAllUvBySite( site.getSiteId() );

        Map valMap = null;

        Long pv = null;

        String su = null;

        for ( int i = 0; i < aeraUvList.size(); i++ )
        {
            valMap = ( Map ) aeraUvList.get( i );

            pv = ( Long ) valMap.get( "uvCount" );

            su = ( String ) valMap.get( "searchName" );

            if( su != null && su.indexOf( "baidu.com" ) != -1 )
            {
                valMap.put( "searchName", su + " (百度)" );
            }
            else if( su != null && su.indexOf( "so.com" ) != -1 )
            {
                valMap.put( "searchName", su + " (360搜索)" );
            }
            else if( su != null && su.indexOf( "sogou.com" ) != -1 )
            {
                valMap.put( "searchName", su + " (搜狗)" );
            }
            else if( su != null && su.indexOf( "soso.com" ) != -1 )
            {
                valMap.put( "searchName", su + " (搜搜)" );
            }

            double ov = ( allUvCount.longValue() ) != 0 ? MathUtil.mul( MathUtil.div( StringUtil
                .getDoubleValue( pv.toString(), 0 ), StringUtil.getDoubleValue( allUvCount
                .toString(), 0 ), 4 ), 100 ) : 0.0;

            valMap.put( "uvPer", ov );

        }

        return aeraUvList;

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitSearchKeyUvInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long allUvCount = statDao.querySiteVistorSearchKeySumUvBySite( site.getSiteId() );

        List aeraUvList = statDao.querySiteVistorSearchKeyAllUvBySite( site.getSiteId() );

        Map valMap = null;

        Long pv = null;

        for ( int i = 0; i < aeraUvList.size(); i++ )
        {
            valMap = ( Map ) aeraUvList.get( i );

            pv = ( Long ) valMap.get( "uvCount" );

            double ov = ( allUvCount.longValue() ) != 0 ? MathUtil.mul( MathUtil.div( StringUtil
                .getDoubleValue( pv.toString(), 0 ), StringUtil.getDoubleValue( allUvCount
                .toString(), 0 ), 4 ), 100 ) : 0.0;

            valMap.put( "uvPer", ov );

        }

        return aeraUvList;

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitGateInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long allCountCount = statDao.querySiteVistorGateSumPvBySite( site.getSiteId() );

        List gateUrlCountList = statDao.querySiteVistorGateAllPvBySite( site.getSiteId() );

        Map valMap = null;

        Integer pv = null;

        for ( int i = 0; i < gateUrlCountList.size(); i++ )
        {
            valMap = ( Map ) gateUrlCountList.get( i );

            pv = Integer.valueOf( MathUtil.changeSUMResToLong( valMap.get( "count" ) ).intValue() );

            double ov = ( allCountCount.longValue() ) != 0 ? MathUtil.mul( MathUtil.div( StringUtil
                .getDoubleValue( pv.toString(), 0 ), StringUtil.getDoubleValue( allCountCount
                .toString(), 0 ), 4 ), 100 ) : 0.0;

            valMap.put( "pvPer", ov );
        }

        return gateUrlCountList;

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitExitInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long allCountCount = statDao.querySiteVistorExitSumPvBySite( site.getSiteId() );

        List gateUrlCountList = statDao.querySiteVistorExitAllPvBySite( site.getSiteId() );

        Map valMap = null;

        Integer pv = null;

        for ( int i = 0; i < gateUrlCountList.size(); i++ )
        {
            valMap = ( Map ) gateUrlCountList.get( i );

            pv = ( Integer ) valMap.get( "count" );

            double ov = ( allCountCount.longValue() ) != 0 ? MathUtil.mul( MathUtil.div( StringUtil
                .getDoubleValue( pv.toString(), 0 ), StringUtil.getDoubleValue( allCountCount
                .toString(), 0 ), 4 ), 100 ) : 0.0;

            valMap.put( "pvPer", ov );
        }

        return gateUrlCountList;

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitUrlPvInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long allCountCount = statDao.querySiteVistorUrlPvSumPvBySite( site.getSiteId() );

        List gateUrlCountList = statDao.querySiteVistorUrlAllPvBySite( site.getSiteId() );

        Map valMap = null;

        Long pv = null;

        for ( int i = 0; i < gateUrlCountList.size(); i++ )
        {
            valMap = ( Map ) gateUrlCountList.get( i );

            pv = ( Long ) valMap.get( "clickCount" );

            double ov = ( allCountCount.longValue() ) != 0 ? MathUtil.mul( MathUtil.div( StringUtil
                .getDoubleValue( pv.toString(), 0 ), StringUtil.getDoubleValue( allCountCount
                .toString(), 0 ), 4 ), 100 ) : 0.0;

            valMap.put( "pvPer", ov );
        }

        return gateUrlCountList;

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitClassPvInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long allCountCount = statDao.querySiteVistorClassPvSumPvBySite( site.getSiteId() );

        List gateUrlCountList = statDao.querySiteVistorClassAllPvBySite( site.getSiteId() );

        Map valMap = null;

        Long pv = null;

        for ( int i = 0; i < gateUrlCountList.size(); i++ )
        {
            valMap = ( Map ) gateUrlCountList.get( i );

            pv = ( Long ) valMap.get( "pvCount" );

            double ov = ( allCountCount.longValue() ) != 0 ? MathUtil.mul( MathUtil.div( StringUtil
                .getDoubleValue( pv.toString(), 0 ), StringUtil.getDoubleValue( allCountCount
                .toString(), 0 ), 4 ), 100 ) : 0.0;

            valMap.put( "pvPer", ov );
        }

        return gateUrlCountList;

    }

    public Object getSiteVisitContentPvInfoTag( String pn, String size )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 20 );

        Page pageInfo = null;

        Long count = statDao.querySiteVisitorContentCount( site.getSiteId() );

        pageInfo = new Page( pageSize, count.intValue(), pageNum );

        List result = statDao.querySiteVisitorContentPvInfo( site.getSiteId(), Long
            .valueOf( pageInfo.getFirstResult() ), Integer.valueOf( pageSize ) );

        return new Object[] { result, pageInfo };

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitOsPvInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long allUvCount = statDao.querySiteVistorOsSumPvBySite( site.getSiteId() );

        List aeraUvList = statDao.querySiteVistorOsAllUvBySite( site.getSiteId() );

        Map valMap = null;

        Long pv = null;

        for ( int i = 0; i < aeraUvList.size(); i++ )
        {
            valMap = ( Map ) aeraUvList.get( i );

            pv = ( Long ) valMap.get( "pvCount" );

            double ov = ( allUvCount.longValue() ) != 0 ? MathUtil.mul( MathUtil.div( StringUtil
                .getDoubleValue( pv.toString(), 0 ), StringUtil.getDoubleValue( allUvCount
                .toString(), 0 ), 4 ), 100 ) : 0.0;

            valMap.put( "pvPer", ov );

        }

        return aeraUvList;

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitBrPvInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long allUvCount = statDao.querySiteVistorBrSumPvBySite( site.getSiteId() );

        List aeraUvList = statDao.querySiteVistorBrAllUvBySite( site.getSiteId() );

        Map valMap = null;

        Long pv = null;

        for ( int i = 0; i < aeraUvList.size(); i++ )
        {
            valMap = ( Map ) aeraUvList.get( i );

            pv = ( Long ) valMap.get( "pvCount" );

            double ov = ( allUvCount.longValue() ) != 0 ? MathUtil.mul( MathUtil.div( StringUtil
                .getDoubleValue( pv.toString(), 0 ), StringUtil.getDoubleValue( allUvCount
                .toString(), 0 ), 4 ), 100 ) : 0.0;

            valMap.put( "pvPer", ov );

        }

        return aeraUvList;

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitLaPvInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long allUvCount = statDao.querySiteVistorLaSumPvBySite( site.getSiteId() );

        List aeraUvList = statDao.querySiteVistorLaAllUvBySite( site.getSiteId() );

        Map valMap = null;

        Long pv = null;

        for ( int i = 0; i < aeraUvList.size(); i++ )
        {
            valMap = ( Map ) aeraUvList.get( i );

            pv = ( Long ) valMap.get( "pvCount" );

            double ov = ( allUvCount.longValue() ) != 0 ? MathUtil.mul( MathUtil.div( StringUtil
                .getDoubleValue( pv.toString(), 0 ), StringUtil.getDoubleValue( allUvCount
                .toString(), 0 ), 4 ), 100 ) : 0.0;

            valMap.put( "pvPer", ov );

        }

        return aeraUvList;

    }

    @SuppressWarnings( "unchecked" )
    public Object getSiteVisitScPvInfoTag()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long allUvCount = statDao.querySiteVistorScSumPvBySite( site.getSiteId() );

        List aeraUvList = statDao.querySiteVistorScAllUvBySite( site.getSiteId() );

        Map valMap = null;

        Long pv = null;

        for ( int i = 0; i < aeraUvList.size(); i++ )
        {
            valMap = ( Map ) aeraUvList.get( i );

            pv = ( Long ) valMap.get( "pvCount" );

            double ov = ( allUvCount.longValue() ) != 0 ? MathUtil.mul( MathUtil.div( StringUtil
                .getDoubleValue( pv.toString(), 0 ), StringUtil.getDoubleValue( allUvCount
                .toString(), 0 ), 4 ), 100 ) : 0.0;

            valMap.put( "pvPer", ov );

        }

        return aeraUvList;

    }

    public void disposeReferType( StatVisitInfo visitInfo )
    {
        int type = -1;

        if( visitInfo == null )
        {
            return;
        }

        String reffer = visitInfo.getReffer();

        if( StringUtil.isStringNull( reffer ) )
        {
            type = 0; // 没有来源,直接输入作为入口
        }
        else if( reffer.indexOf( "fromsource=" ) > -1 )
        {
            type = 1; // 推广链接
        }
        else if( reffer.indexOf( "baidu.com" ) > -1 )
        {
            type = 2; // 百度
        }
        else if( reffer.indexOf( "haosou.com" ) > -1 )
        {
            type = 3; // 360
        }
        else if( reffer.indexOf( "so.com" ) > -1 )
        {
            type = 3; // 360
        }
        // else if( reffer.indexOf( "google.com" ) > -1 )
        // {
        // type = 3; // Google
        // }
        else if( reffer.indexOf( "sogou.com" ) > -1 )
        {
            type = 4; // 搜狗
        }
        else if( reffer.indexOf( "soso.com" ) > -1 )
        {
            type = 5; // 搜搜
        }
        else
        {
            type = 6; // 外站或本站连接进入
        }

        visitInfo.setRefferType( Integer.valueOf( type ) );

    }

    /**
     * 栏目统计到月单位，以月为单位计算
     */

    public void initSiteContentStatTrace()
    {
        // 删除相关信息
        statDao.deleteAllPubTracePub();
        statDao.deleteAllStatClassContentTrace();
        statDao.deleteAllStatClassUpdateTrace();
        statDao.deleteAllStatManagerCoententTrace();
        statDao.deleteAllStatOrgCoententTrace();

        Calendar currentTime = Calendar.getInstance();

        int year = currentTime.get( Calendar.YEAR );

        int month = currentTime.get( Calendar.MONTH ) + 1;

        int day = currentTime.get( Calendar.DAY_OF_MONTH );

        List allSite = InitSiteGroupInfoBehavior.siteGroupListCache;

        SiteGroupBean site = null;

        ContentClassBean ccBean = null;

        List classList = null;

        Set currentClass = new HashSet();

        for ( int i = 0; i < allSite.size(); i++ )
        {
            site = ( SiteGroupBean ) allSite.get( i );

            classList = ChannelService.getInstance().retrieveAllClassBeanInfoBySiteFlag(
                site.getSiteFlag() );

            for ( int j = 0; j < classList.size(); j++ )
            {
                ccBean = ( ContentClassBean ) classList.get( j );

                currentClass.add( ccBean.getClassId() );

                Map classUT = statDao.querySingleClassUpdateTrace( ccBean.getClassId() );

                Map params = new HashMap();

                params.put( "classId", ccBean.getClassId() );
                params.put( "updateYear", year );
                params.put( "updateMon", month );
                params.put( "updateDay", day );
                params.put( "updateDate", currentTime.getTime() );

                if( classUT.isEmpty() )
                {
                    params.put( "siteId", site.getSiteId() );

                    statDao.saveClassUpdateTrace( params );
                }
            }
        }

        // 删除已废除栏目记录

        List exist = statDao.queryAllClassUpdateTrace();

        Map tr = null;

        Long classId = null;

        for ( int i = 0; i < exist.size(); i++ )
        {
            tr = ( Map ) exist.get( i );

            classId = ( Long ) tr.get( "classId" );

            if( currentClass.contains( classId ) )
            {
                continue;
            }
            else
            {
                statDao.deleteClassUpdateTrace( classId );
            }
        }

        // 初始化内容
        List acl = statDao.queryTraceAllClass();

        Map classMap = null;

        for ( int i = 0; i < acl.size(); i++ )
        {
            classMap = ( Map ) acl.get( i );

            initAllSystemAndUserDefineContentTrace( ( Long ) classMap.get( "classId" ) );
        }

    }

    public void deleteClassTrace( Long classId )
    {
        statDao.deleteClassUpdateTrace( classId );

        statDao.deleteClassContentTrace( classId );

    }

    public void deleteOrgTrace( Long orgId )
    {
        statDao.deleteOrgUpdateTrace( orgId );

    }

    public void deleteUserTrace( Long userId )
    {
        statDao.deleteManagerUpdateTrace( userId );

    }

    public void addClassTrace( Long classId, Long siteId )
    {
        Calendar currentTime = Calendar.getInstance();

        int year = currentTime.get( Calendar.YEAR );

        int month = currentTime.get( Calendar.MONTH ) + 1;

        int day = currentTime.get( Calendar.DAY_OF_MONTH );

        Map params = new HashMap();

        params.put( "classId", classId );
        params.put( "updateYear", year );
        params.put( "updateMon", month );
        params.put( "updateDay", day );
        params.put( "updateDate", currentTime.getTime() );

        statDao.saveClassUpdateTrace( params );

        StatClassContentTrace vo = new StatClassContentTrace();

        vo.setSiteId( siteId );
        vo.setClassId( classId );
        vo.setAddCount( 0l );
        vo.setPubCount( 0l );
        vo.setImgCount( 0l );
        vo.setVideoCount( 0l );
        vo.setUpYear( year );
        vo.setUpMon( month );
        vo.setUpDT( new Timestamp( currentTime.getTimeInMillis() ) );

        statDao.save( vo );

        cache.clearAllEntry();
    }

    public void initAllSystemAndUserDefineContentTrace( Long classId )
    {
        // 不开启事务,以下调用带事务删除

        Long prevCid = Long.valueOf( Constant.CONTENT.MAX_ID_FLAG );

        Long modelId = null;

        ContentClassBean classBean = ChannelService.getInstance()
            .retrieveSingleClassBeanInfoByClassId( classId );

        if( classId != null )
        {
            modelId = classBean.getContentType();
        }

        List needRecoverContentList = statDao.queryTraceContentIdByClassIdAndModelId( classId,
            modelId, prevCid, 1000 );
        Map li = null;

        if( !needRecoverContentList.isEmpty() )
        {
            li = ( Map ) needRecoverContentList.get( 0 );
        }

        Map info = null;

        while ( !needRecoverContentList.isEmpty() )
        {
            prevCid = ( Long ) ( ( Map ) needRecoverContentList
                .get( needRecoverContentList.size() - 1 ) ).get( "contentId" );

            for ( int i = 0; i < needRecoverContentList.size(); i++ )
            {
                info = ( Map ) needRecoverContentList.get( i );

                Integer[] resCount = new Integer[] { 0, 0 };

                int addCount = 1;

                int pubCount = 1;

                resCount = ContentService.getInstance().checkContentImgAndVideoCount(
                    ( Long ) info.get( "contentId" ) );

                if( !Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( ( Integer ) info
                    .get( "censorState" ) )
                    && !Constant.WORKFLOW.CENSOR_STATUS_WAIT_PUBLISH.equals( ( Integer ) info
                        .get( "censorState" ) ) )
                {
                    addCount = 1;

                    pubCount = 0;

                    ContentTempTraceBean ctt = new ContentTempTraceBean();

                    ctt.setContentId( ( Long ) info.get( "contentId" ) );
                    ctt.setImgCount( resCount[0] );
                    ctt.setVideoCount( resCount[1] );

                    ManageModelContentController.ivTemp.put( ( Long ) info.get( "contentId" ), ctt );

                    resCount = new Integer[] { 0, 0 };

                }

               collAndAnalysisContentStat( true,
                    ( Date ) info.get( "addTime" ), ( Long ) info.get( "contentId" ),
                    ( String ) info.get( "creator" ), ( Integer ) info.get( "censorState" ),
                    ( Long ) info.get( "siteId" ), ( Long ) info.get( "classId" ), addCount,
                    pubCount, resCount[0], resCount[1] );

            }

            needRecoverContentList = statDao.queryTraceContentIdByClassIdAndModelId( classId,
                modelId, prevCid, 1000 );
        }

        if( li != null )
        {
            Map params = new HashMap();

            Calendar currentTime = Calendar.getInstance();

            currentTime.setTime( ( Date ) li.get( "addTime" ) );

            int year = currentTime.get( Calendar.YEAR );

            int month = currentTime.get( Calendar.MONTH ) + 1;

            int day = currentTime.get( Calendar.DAY_OF_MONTH );

            params.put( "classId", classId );
            params.put( "updateYear", year );
            params.put( "updateMon", month );
            params.put( "updateDay", day );
            params.put( "updateDate", currentTime.getTime() );

            statDao.updateClassUpdateTrace( params );
        }

    }

    public void deleteContentResInfo( Long contentId )
    {
        statDao.deletePubTracePub( contentId );
    }

    public void collAndAnalysisContentStat( boolean init, Date cud, Long contentId, String creator,
        Integer censor, Long siteId, Long classId, Integer addCount, Integer pubCount,
        Integer imageCount, Integer videoCount )
    {
        if( init )
        {
            statDao.savePubTracePub( contentId, imageCount, videoCount );

            if( !Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( censor )
                && !Constant.WORKFLOW.CENSOR_STATUS_WAIT_PUBLISH.equals( censor ) )
            {
                statDao.updatePubTracePub( contentId, Constant.COMMON.OFF );
            }
        }
        else
        {

            Map pt = statDao.queryPubTrace( contentId );

            if( addCount.intValue() == -1 && pubCount.intValue() == -1 )
            {
                if( !pt.isEmpty() )
                {
                    statDao.updatePubTracePub( contentId, Constant.COMMON.ON );

                }
            }
            // 稿件第一次进入或审核中
            else if( addCount.intValue() == 1 && pubCount.intValue() == 0 )
            {
                if( pt.isEmpty() )
                {
                    imageCount = 0;

                    videoCount = 0;

                    statDao.savePubTracePub( contentId, 0, 0 );

                    statDao.updatePubTracePub( contentId, Constant.COMMON.OFF );

                }
                else
                {
                    addCount = 0;
                }

            }
            // // 工作流中退稿件
            // else if( addCount.intValue() == 1 && pubCount.intValue() == -1 )
            // {
            // addCount = 0;
            //                
            // //已经发布过的稿件退稿
            // if( !pt.isEmpty()
            // && Constant.COMMON.ON.equals( pt.get( "isPub" ) ) )
            // {
            // Integer ic = ( Integer ) pt.get( "imgCount" );
            //
            // Integer iv = ( Integer ) pt.get( "videoCount" );
            //
            // imageCount = -ic;
            //
            // videoCount = -iv;
            //
            // }
            //                
            //
            // }
            // 进入重编辑，或工作流结束,或直接发布的情况
            else if( addCount.intValue() == 0 && pubCount.intValue() == 1 )
            {
                if( !pt.isEmpty() && Constant.COMMON.ON.equals( pt.get( "isPub" ) ) )
                {
                    pubCount = 0;// 重进入不需要改动发布值
                }
                else
                {
                    if( pt.isEmpty() )
                    {
                        addCount = 1;

                        statDao.savePubTracePub( contentId, imageCount, videoCount );
                    }
                    else
                    {
                        statDao.updatePubTracePub( contentId, Constant.COMMON.ON );
                    }
                }

                // 记录资源
                Integer[] resCount = ContentService.getInstance().checkContentImgAndVideoCount(
                    contentId );

                statDao.updatePubTrace( contentId, resCount[0], resCount[1] );

            }
            // 删除到回收站
            else if( addCount.intValue() == -9999 )
            {
                addCount = -1;

                if( Constant.COMMON.OFF.equals( pt.get( "isPub" ) ) )
                {
                    imageCount = 0;

                    videoCount = 0;
                }
                else
                {
                    Integer ic = ( Integer ) pt.get( "imgCount" );

                    Integer iv = ( Integer ) pt.get( "videoCount" );

                    imageCount = -ic;

                    videoCount = -iv;
                }

            }
            // 回收站恢复
            else if( addCount.intValue() == 9999 )
            {
                addCount = 1;

                if( Constant.COMMON.OFF.equals( pt.get( "isPub" ) ) )
                {
                    imageCount = 0;

                    videoCount = 0;
                }
                else
                {
                    Integer ic = ( Integer ) pt.get( "imgCount" );

                    Integer iv = ( Integer ) pt.get( "videoCount" );

                    imageCount = ic;

                    videoCount = iv;
                }
            }
        }

        Calendar currentTime = Calendar.getInstance();

        if( cud != null )
        {
            currentTime.setTime( cud );
        }

        int year = currentTime.get( Calendar.YEAR );

        int month = currentTime.get( Calendar.MONTH ) + 1;

        int day = currentTime.get( Calendar.DAY_OF_MONTH );

        /**
         * 站点和栏目更新记录
         */
        // class core trace
        StatClassContentTraceBean scctb = statDao.querySingleStatClassContentTraceBean( classId,
            year, month, day );

        if( scctb == null )
        {
            StatClassContentTrace vo = new StatClassContentTrace();

            vo.setSiteId( siteId );
            vo.setClassId( classId );
            vo.setAddCount( addCount.longValue() );
            vo.setPubCount( pubCount.longValue() );
            vo.setImgCount( imageCount.longValue() );
            vo.setVideoCount( videoCount.longValue() );
            vo.setUpYear( year );
            vo.setUpMon( month );
            vo.setUpDay( day );
            vo.setUpDT( new Timestamp( currentTime.getTimeInMillis() ) );

            statDao.save( vo );
        }
        else
        {
            statDao.updateStatClassContentTraceBean( classId, year, month, day, addCount, pubCount,
                imageCount, videoCount );
        }

        // user core trace

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        IUser user = securityService.obtainUser( creator );

        if( user != null )
        {
            Long userId = ( Long ) user.getIdentity();

            StatManagerContentTraceBean smctb = statDao.querySingleStatManagerContentTraceBean(
                userId, year, month, day );

            if( smctb == null )
            {
                StatManagerContentTrace vo = new StatManagerContentTrace();

                vo.setUserId( userId );
                vo.setAddCount( addCount.longValue() );
                vo.setPubCount( pubCount.longValue() );
                vo.setImgCount( imageCount.longValue() );
                vo.setVideoCount( videoCount.longValue() );
                vo.setUpYear( year );
                vo.setUpMon( month );
                vo.setUpDay( day );
                vo.setUpDT( new Timestamp( currentTime.getTimeInMillis() ) );

                statDao.save( vo );
            }
            else
            {
                statDao.updateStatManagerContentTraceBean( userId, year, month, day, addCount,
                    pubCount, imageCount, videoCount );
            }

        }

        // org core trace

        if( user != null )
        {
            Long orgId = ( Long ) user.getOrgIdentity();

            StatOrgContentTraceBean soctb = statDao.querySingleStatOrgContentTraceBean( orgId,
                year, month, day );

            if( soctb == null )
            {
                StatOrgContentTrace vo = new StatOrgContentTrace();

                vo.setOrgId( orgId );
                vo.setAddCount( addCount.longValue() );
                vo.setPubCount( pubCount.longValue() );
                vo.setImgCount( imageCount.longValue() );
                vo.setVideoCount( videoCount.longValue() );
                vo.setUpYear( year );
                vo.setUpMon( month );
                vo.setUpDay( day );
                vo.setUpDT( new Timestamp( currentTime.getTimeInMillis() ) );

                statDao.save( vo );
            }
            else
            {
                statDao.updateStatOrgContentTraceBean( orgId, year, month, day, addCount, pubCount,
                    imageCount, videoCount );
            }

        }

        // class trace
        Map classUT = statDao.querySingleClassUpdateTrace( classId );

        Map params = new HashMap();

        params.put( "classId", classId );
        params.put( "updateYear", year );
        params.put( "updateMon", month );
        params.put( "updateDay", day );
        params.put( "updateDate", currentTime.getTime() );

        if( classUT.isEmpty() )
        {
            params.put( "siteId", siteId );

            statDao.saveClassUpdateTrace( params );
        }
        else
        {
            statDao.updateClassUpdateTrace( params );
        }

    }

    /**
     * TAG方法
     */
    public Object getSiteContentMonTraceTag( String ids, String cf, String sd, String ed )
    {

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( "".equals( ids ) && !"001".equals( auth.getOrgCode() ) )
        {
            Long orgIdVar = ( Long ) auth.getOrgIdentity();

            Long childOrgIdVar = null;

            List siteList = OrgService.getInstance().retrieveSiteBeanByOrgId( orgIdVar,
                childOrgIdVar );

            if( !siteList.isEmpty() )
            {
                SiteGroupBean site = ( SiteGroupBean ) siteList.get( 0 );

                ids = site.getSiteId().toString();
            }

        }

        String key = "getSiteContentMonTraceTag:" + ids + cf + sd + ed;

        List result = ( List ) cache.getEntry( key );

        if( result != null )
        {
            return result;
        }

        // 判断是否合法格式的日期
        Timestamp sdt = DateAndTimeUtil.getTimestamp( sd, "yyyy-MM-dd" );

        Timestamp edt = DateAndTimeUtil.getTimestamp( ed, "yyyy-MM-dd" );

        result = new ArrayList();

        if( sdt != null && edt != null )
        {
            String minDate = sd;

            String maxDate = ed;

            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );

            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();

            try
            {
                min.setTime( sdf.parse( minDate ) );
                min.set( min.get( Calendar.YEAR ), min.get( Calendar.MONTH ), 1 );

                max.setTime( sdf.parse( maxDate ) );
                max.set( max.get( Calendar.YEAR ), max.get( Calendar.MONTH ), 2 );
            }
            catch ( ParseException e )
            {

                e.printStackTrace();
            }

            Calendar curr = min;

            Map info = null;

            while ( curr.before( max ) )
            {
                info = statDao.queryStatClassContentTraceBySE( curr.get( Calendar.YEAR ), ( curr
                    .get( Calendar.MONTH ) + 1 ) );

                if( info.get( "upYear" ) == null )
                {
                    info.put( "upYear", curr.get( Calendar.YEAR ) );
                    info.put( "upMon", ( curr.get( Calendar.MONTH ) + 1 ) );
                    info.put( "addAllCount", "0" );
                    info.put( "pubAllCount", "0" );
                    info.put( "imgAllCount", "0" );
                    info.put( "videoAllCount", "0" );
                }

                result.add( info );

                curr.add( Calendar.MONTH, 1 );
            }

        }
        else
        {

            if( "mon".equals( cf ) )
            {

                String[] showdt = new String[12];

                Calendar cal = Calendar.getInstance();
                cal.set( Calendar.MONTH, cal.get( Calendar.MONTH ) + 1 );

                Map info = null;
                for ( int i = 0; i < 12; i++ )
                {
                    cal.set( Calendar.MONTH, cal.get( Calendar.MONTH ) - 1 ); // 减一月

                    if( StringUtil.isStringNotNull( ids ) )
                    {
                        info = statDao.queryStatClassContentTraceBySE( cal.get( Calendar.YEAR ),
                            ( cal.get( Calendar.MONTH ) + 1 ), StringUtil.getLongValue( ids, -1 ) );
                    }
                    else
                    {
                        info = statDao.queryStatClassContentTraceBySE( cal.get( Calendar.YEAR ),
                            ( cal.get( Calendar.MONTH ) + 1 ) );
                    }

                    if( info.get( "upYear" ) == null )
                    {
                        info.put( "upYear", cal.get( Calendar.YEAR ) );
                        info.put( "upMon", ( cal.get( Calendar.MONTH ) + 1 ) );
                        info.put( "addAllCount", "0" );
                        info.put( "pubAllCount", "0" );
                        info.put( "imgAllCount", "0" );
                        info.put( "videoAllCount", "0" );

                    }

                    result.add( info );

                    showdt[11 - i] = cal.get( Calendar.YEAR ) + "-"
                        + ( cal.get( Calendar.MONTH ) + 1 );
                }
            }
            else if( "year".equals( cf ) )
            {

                String[] showdt = new String[12];

                Calendar cal = Calendar.getInstance();
                cal.set( Calendar.YEAR, cal.get( Calendar.YEAR ) + 1 );

                Map info = null;
                for ( int i = 0; i < 10; i++ )
                {
                    cal.set( Calendar.YEAR, cal.get( Calendar.YEAR ) - 1 ); // 减一年

                    if( StringUtil.isStringNotNull( ids ) )
                    {
                        info = statDao.queryStatClassContentTraceBySE( cal.get( Calendar.YEAR ),
                            StringUtil.getLongValue( ids, -1 ) );
                    }
                    else
                    {
                        info = statDao.queryStatClassContentTraceBySE( cal.get( Calendar.YEAR ) );
                    }

                    if( info.get( "upYear" ) == null )
                    {
                        info.put( "upYear", cal.get( Calendar.YEAR ) );
                        info.put( "upMon", ( cal.get( Calendar.MONTH ) + 1 ) );
                        info.put( "upDay", ( cal.get( Calendar.DAY_OF_MONTH ) + 1 ) );
                        info.put( "addAllCount", "0" );
                        info.put( "pubAllCount", "0" );
                        info.put( "imgAllCount", "0" );
                        info.put( "videoAllCount", "0" );

                    }

                    result.add( info );

                    showdt[11 - i] = cal.get( Calendar.YEAR ) + "";
                }
            }
            else
            {

                String[] showdt = new String[31];

                Calendar cal = Calendar.getInstance();
                cal.set( Calendar.DAY_OF_MONTH, cal.get( Calendar.DAY_OF_MONTH ) + 1 );

                Map info = null;
                for ( int i = 0; i < 30; i++ )
                {
                    cal.set( Calendar.DAY_OF_MONTH, cal.get( Calendar.DAY_OF_MONTH ) - 1 ); // 减一日

                    if( StringUtil.isStringNotNull( ids ) )
                    {
                        info = statDao.queryStatClassContentTraceBySE( cal.get( Calendar.YEAR ),
                            ( cal.get( Calendar.MONTH ) + 1 ), cal.get( Calendar.DAY_OF_MONTH ),
                            StringUtil.getLongValue( ids, -1 ) );
                    }
                    else
                    {
                        info = statDao.queryStatClassContentTraceBySE( cal.get( Calendar.YEAR ),
                            ( cal.get( Calendar.MONTH ) + 1 ), cal.get( Calendar.DAY_OF_MONTH ) );
                    }

                    if( info.get( "upYear" ) == null )
                    {
                        info.put( "upYear", cal.get( Calendar.YEAR ) );
                        info.put( "upMon", ( cal.get( Calendar.MONTH ) + 1 ) );
                        info.put( "upDay", ( cal.get( Calendar.DAY_OF_MONTH ) ) );
                        info.put( "addAllCount", "0" );
                        info.put( "pubAllCount", "0" );
                        info.put( "imgAllCount", "0" );
                        info.put( "videoAllCount", "0" );

                    }

                    result.add( info );

                    showdt[30 - i] = cal.get( Calendar.YEAR )
                        + "-"
                        + ( cal.get( Calendar.MONTH ) + 1 + "-" + ( cal.get( Calendar.DAY_OF_MONTH ) ) );
                }
            }

            Collections.reverse( result );
        }

        cache.putEntry( key, result );

        return result;
    }

    public Map querySiteContentIntegratedTraceForTag()
    {
        String key = "querySiteContentIntegratedTraceForTag:";

        Map info = ( Map ) cache.getEntry( key );

        if( info != null )
        {
            return info;
        }

        info = new HashMap();

        Long siteCount = statDao.querySiteCount();

        Long classCount = statDao.queryContentClassCount();

        Long orgCount = statDao.queryOrgCount();

        Long userCount = statDao.queryManagerCount();

        Long allAddCount = statDao.queryAddContentCount();

        Long allPubCount = statDao.queryPubContentCount();

        Long allImgCount = statDao.queryImageInfoCount();

        Long allVideoCount = statDao.queryVideoInfoCount();

        Long allFilerCount = statDao.queryFilterInfoCount();

        Long allBlackCount = statDao.queryBlackInfoCount();

        info.put( "siteCount", siteCount );

        info.put( "classCount", classCount );

        info.put( "orgCount", orgCount );

        info.put( "userCount", userCount );

        info.put( "allAddCount", allAddCount );

        info.put( "allPubCount", allPubCount );

        info.put( "allImgCount", allImgCount );

        info.put( "allVideoCount", allVideoCount );

        info.put( "allFilterCount", allFilerCount );

        info.put( "allBlackCount", allBlackCount );

        cache.putEntry( key, info );

        return info;
    }

    public List querySiteContentTraceForTag( String ids, String st, String et )
    {

        // 判断是否合法格式的日期
        Timestamp startT = DateAndTimeUtil.getTimestamp( st, "yyyy-MM-dd" );

        Timestamp endT = DateAndTimeUtil.getTimestamp( et, "yyyy-MM-dd" );

        if( startT == null || endT == null )
        {
            // 站点内容细节统计
            return disposeSiteContentTrace( ids, null, null );

        }
        else
        {
            Calendar cal = Calendar.getInstance();

            cal.setTime( endT );

            // cal.add( Calendar.MONTH, 1 );
            cal.add( Calendar.DAY_OF_MONTH, 1 );

            return disposeSiteContentTrace( ids, startT, new Timestamp( cal.getTime().getTime() ) );
        }

    }

    public List queryOrgContentTraceForTag( String ids, String st, String et )
    {

        // 判断是否合法格式的日期
        Timestamp startT = DateAndTimeUtil.getTimestamp( st, "yyyy-MM-dd" );

        Timestamp endT = DateAndTimeUtil.getTimestamp( et, "yyyy-MM-dd" );

        if( startT == null || endT == null )
        {
            // 站点内容细节统计
            return disposeOrgContentTrace( ids, null, null );

        }
        else
        {
            Calendar cal = Calendar.getInstance();

            cal.setTime( endT );

            // cal.add( Calendar.MONTH, 1 );
            cal.add( Calendar.DAY_OF_MONTH, 1 );

            return disposeOrgContentTrace( ids, startT, new Timestamp( cal.getTime().getTime() ) );
        }

    }

    public List queryManagerContentTraceForTag( String ids, String st, String et )
    {
        // 判断是否合法格式的日期
        Timestamp startT = DateAndTimeUtil.getTimestamp( st, "yyyy-MM-dd" );

        Timestamp endT = DateAndTimeUtil.getTimestamp( et, "yyyy-MM-dd" );

        if( startT == null || endT == null )
        {
            // 站点内容细节统计
            return disposeManagerContentTrace( ids, null, null );

        }
        else
        {
            Calendar cal = Calendar.getInstance();

            cal.setTime( endT );

            // cal.add( Calendar.MONTH, 1 );
            cal.add( Calendar.DAY_OF_MONTH, 1 );

            return disposeManagerContentTrace( ids, startT, new Timestamp( cal.getTime().getTime() ) );
        }

    }

    public List queryStatSiteContentWeekAndMonAndYearEndClassForTag( String siteId, String flag )
    {

        String key = "queryStatSiteContentWeekAndMonAndYearEndClassForTag:" + siteId + "|" + flag;

        List result = ( List ) cache.getEntry( key );

        if( result != null )
        {
            return result;
        }

        SiteGroupBean site = null;

        String siteFlag = "";

        if( !"all".equals( siteId ) )
        {
            if( StringUtil.isStringNull( siteId ) )
            {
                Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

                Long orgIdVar = ( Long ) auth.getOrgIdentity();

                Long childOrgIdVar = null;

                List siteList = OrgService.getInstance().retrieveSiteBeanByOrgId( orgIdVar,
                    childOrgIdVar );

                if( !siteList.isEmpty() )
                {
                    site = ( SiteGroupBean ) siteList.get( 0 );

                    siteFlag = site.getSiteFlag();
                }
            }
            else
            {
                site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                    .getEntry( StringUtil.getLongValue( siteId, -1 ) );

                siteFlag = site.getSiteFlag();
            }
        }

        if( StringUtil.isStringNull( flag ) )
        {
            flag = "week";
        }

        Calendar curr = Calendar.getInstance();

        curr.add( Calendar.DAY_OF_MONTH, -7 );

        Date weekEnd = curr.getTime();

        curr = Calendar.getInstance();

        curr.add( Calendar.DAY_OF_MONTH, -90 );

        Date threeMonEnd = curr.getTime();

        curr = Calendar.getInstance();

        curr.add( Calendar.DAY_OF_MONTH, -180 );

        Date halfYearEnd = curr.getTime();

        curr = Calendar.getInstance();

        curr.add( Calendar.DAY_OF_MONTH, -360 );

        Date yearEnd = curr.getTime();

        if( StringUtil.isStringNotNull( siteFlag ) )
        {
            if( "week".equals( flag ) )
            {
                result = statDao.queryStatSiteContentWeekAndMonAndYearEndCountBySiteId( siteFlag,
                    weekEnd );
            }
            else if( "threeMon".equals( flag ) )
            {
                result = statDao.queryStatSiteContentWeekAndMonAndYearEndCountBySiteId( siteFlag,
                    threeMonEnd );
            }
            else if( "halfYear".equals( flag ) )
            {
                result = statDao.queryStatSiteContentWeekAndMonAndYearEndCountBySiteId( siteFlag,
                    halfYearEnd );
            }
            else if( "year".equals( flag ) )
            {
                result = statDao.queryStatSiteContentWeekAndMonAndYearEndCountBySiteId( siteFlag,
                    yearEnd );
            }
            else if( "zero".equals( flag ) )
            {
                result = statDao.queryStatClassContentAllZeroTraceBySiteAndClass( siteFlag );
            }

        }
        else
        {
            if( "week".equals( flag ) )
            {
                result = statDao.queryStatSiteContentWeekAndMonAndYearEndCountBySiteId( weekEnd );
            }
            else if( "threeMon".equals( flag ) )
            {
                result = statDao
                    .queryStatSiteContentWeekAndMonAndYearEndCountBySiteId( threeMonEnd );
            }
            else if( "halfYear".equals( flag ) )
            {
                result = statDao
                    .queryStatSiteContentWeekAndMonAndYearEndCountBySiteId( halfYearEnd );
            }
            else if( "year".equals( flag ) )
            {
                result = statDao.queryStatSiteContentWeekAndMonAndYearEndCountBySiteId( yearEnd );
            }
            else if( "zero".equals( flag ) )
            {
                result = statDao.queryStatClassContentAllZeroTraceBySiteAndClass();
            }
        }

        cache.putEntry( key, result );

        return result;
    }

    public List queryStatSiteContentZeroClassForTag( String siteFlag, String flag )
    {
        String key = "queryStatSiteContentZeroClassForTag:" + siteFlag + "|" + flag;

        List result = ( List ) cache.getEntry( key );

        if( result != null )
        {
            return result;
        }

        if( StringUtil.isStringNotNull( siteFlag ) )
        {
            result = statDao.queryStatClassContentAllZeroTraceBySiteAndClass( siteFlag );
        }
        else
        {
            result = statDao.queryStatClassContentAllZeroTraceBySiteAndClass();
        }

        cache.putEntry( key, result );

        return result;
    }

    public List queryClassContentTraceForTag( String siteFlag, String st, String et )
    {
        String key = "queryClassContentTraceForTag:" + siteFlag + "|" + st + "|" + et;

        List result = ( List ) cache.getEntry( key );

        if( result != null )
        {
            return result;
        }

        // 判断是否合法格式的日期
        Timestamp startT = DateAndTimeUtil.getTimestamp( st, "yyyy-MM-dd" );

        Timestamp endT = DateAndTimeUtil.getTimestamp( et, "yyyy-MM-dd" );

        SiteGroupBean site = null;

        if( StringUtil.isStringNull( siteFlag ) )
        {
            Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

            Long orgIdVar = ( Long ) auth.getOrgIdentity();

            Long childOrgIdVar = null;

            List siteList = OrgService.getInstance().retrieveSiteBeanByOrgId( orgIdVar,
                childOrgIdVar );

            if( !siteList.isEmpty() )
            {
                site = ( SiteGroupBean ) siteList.get( 0 );

                siteFlag = site.getSiteFlag();
            }
        }
        else
        {
            site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                .getEntry( siteFlag );
        }

        // 栏目内容细节统计

        if( site == null )
        {
            return result;
        }

        String defOrder = "addAllCount";

        String defOrderFlag = "desc";

        if( startT == null || endT == null )
        {
            result = statDao.queryStatClassContentTraceCollByClassId( siteFlag, defOrder,
                defOrderFlag );

            Map allColl = statDao.queryStatAllClassContentTraceColl( site.getSiteId() );

            allColl.put( "classId", "" );
            allColl.put( "className", "合计" );

            result.add( allColl );
        }
        else
        {
            Calendar cal = Calendar.getInstance();

            cal.setTime( endT );

            // cal.add( Calendar.MONTH, 1 );

            cal.add( Calendar.DAY_OF_MONTH, 1 );

            result = statDao.queryStatClassContentTraceCollByClassId( startT, new Timestamp( cal
                .getTime().getTime() ), siteFlag, defOrder, defOrderFlag );

            Map allColl = statDao.queryStatAllClassContentTraceColl( startT, new Timestamp( cal
                .getTime().getTime() ), site.getSiteId() );

            allColl.put( "classId", "" );
            allColl.put( "className", "合计" );

            result.add( allColl );
        }

        cache.putEntry( key, result );

        return result;
    }

    public Map getContentResTrace( Long contentId )
    {
        Map pt = statDao.queryPubTrace( contentId );

        return pt;
    }

    public String report( String flag, String st, String et, String ids, String eFlag )
    {
        if( "site".equals( flag ) )
        {
            return reportSite( ids, st, et );
        }
        else if( "org".equals( flag ) )
        {
            return reportOrg( ids, st, et );
        }
        else if( "user".equals( flag ) )
        {
            return reportManager( ids, st, et );
        }
        else if( "class".equals( flag ) )
        {
            return reportClass( ids, st, et );
        }
        else if( "empty".equals( flag ) )
        {
            return reportEmpty( ids, eFlag );
        }

        return "";
    }

    private String reportSite( String ids, String st, String et )
    {
        Map taHead = new LinkedHashMap();

        taHead.put( "序号", "rank" );
        taHead.put( "网站名称", "siteName" );
        taHead.put( "录入数", "addAllCount" );
        taHead.put( "发布数", "pubAllCount" );
        taHead.put( "图片信息", "imgAllCount" );
        taHead.put( "视频信息", "videoAllCount" );

        taHead.put( "无数据栏目", "zeroCount" );
        taHead.put( "一周内无更新", "weekEndCount" );
        taHead.put( "三个月无更新", "threeMonEndCount" );
        taHead.put( "半年无更新", "halfYearEndCount" );
        taHead.put( "一年以上无更新", "yearEndCount" );

        String fileFlagName = "站点综合";

        SiteGroupBean site = ( ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( StringUtil.getLongValue( ids, -1 ) ) );

        if( site != null )
        {

            fileFlagName = fileFlagName + "(" + site.getSiteName() + ")";

        }

        List bodyResult = querySiteContentTraceForTag( ids, st, et );

        if( StringUtil.isStringNotNull( st ) && StringUtil.isStringNotNull( et ) )
        {
            fileFlagName = fileFlagName + "(" + st + "至" + et + ")";
        }

        return genExcel( taHead, bodyResult, fileFlagName );

    }

    private String reportOrg( String ids, String st, String et )
    {
        Map taHead = new LinkedHashMap();

        taHead.put( "序号", "rank" );
        taHead.put( "部门名称", "orgTree" );
        taHead.put( "录入数", "addAllCount" );
        taHead.put( "发布数", "pubAllCount" );
        taHead.put( "图片信息", "imgAllCount" );
        taHead.put( "视频信息", "videoAllCount" );

        List bodyResult = queryOrgContentTraceForTag( ids, st, et );

        String fileFlagName = "部门综合";

        SystemOrganizationBean org = OrgService.getInstance().retrieveSingleOrganizationBeanById(
            StringUtil.getLongValue( ids, -1 ) );

        if( org != null )
        {
            fileFlagName = fileFlagName + "(" + org.getOrgName() + ")";
        }

        if( StringUtil.isStringNotNull( st ) && StringUtil.isStringNotNull( et ) )
        {
            fileFlagName = fileFlagName + "(" + st + "至" + et + ")";
        }

        return genExcel( taHead, bodyResult, fileFlagName );

    }

    private String reportManager( String ids, String st, String et )
    {
        Map taHead = new LinkedHashMap();

        taHead.put( "序号", "rank" );
        taHead.put( "用户名", "userTrueName" );
        taHead.put( "所属部门", "orgTree" );
        taHead.put( "录入数", "addAllCount" );
        taHead.put( "发布数", "pubAllCount" );
        taHead.put( "图片信息", "imgAllCount" );
        taHead.put( "视频信息", "videoAllCount" );

        List bodyResult = queryManagerContentTraceForTag( ids, st, et );

        String fileFlagName = "人员综合";

        SystemOrganizationBean org = OrgService.getInstance()
            .retrieveSingleOrganizationBeanByOrgIdLinearFlag( ids );

        if( org != null )
        {
            fileFlagName = fileFlagName + "(" + org.getOrgName() + ")";
        }

        if( StringUtil.isStringNotNull( st ) && StringUtil.isStringNotNull( et ) )
        {
            fileFlagName = fileFlagName + "(" + st + "至" + et + ")";
        }

        return genExcel( taHead, bodyResult, fileFlagName );

    }

    private String reportClass( String siteFlag, String st, String et )
    {
        Map taHead = new LinkedHashMap();

        taHead.put( "序号", "rank" );
        taHead.put( "栏目ID", "classId" );
        taHead.put( "栏目名称", "classTree" );

        taHead.put( "录入数", "addAllCount" );
        taHead.put( "发布数", "pubAllCount" );
        taHead.put( "图片信息", "imgAllCount" );
        taHead.put( "视频信息", "videoAllCount" );

        List bodyResult = queryClassContentTraceForTag( siteFlag, st, et );

        String fileFlagName = "站内栏目综合";

        SiteGroupBean site = null;

        if( StringUtil.isStringNull( siteFlag ) )
        {
            List siteList = InitSiteGroupInfoBehavior.siteGroupListCache;

            if( !siteList.isEmpty() )
            {
                site = ( SiteGroupBean ) siteList.get( 0 );

                siteFlag = site.getSiteFlag();
            }
        }
        else
        {
            site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                .getEntry( siteFlag );
        }

        if( site != null )
        {
            fileFlagName = fileFlagName + "(" + site.getSiteName() + ")";
        }

        if( StringUtil.isStringNotNull( st ) && StringUtil.isStringNotNull( et ) )
        {
            fileFlagName = fileFlagName + "(" + st + "至" + et + ")";
        }

        return genExcel( taHead, bodyResult, fileFlagName );

    }

    private String reportEmpty( String siteId, String eFlag )
    {
        Map taHead = new LinkedHashMap();

        taHead.put( "序号", "rank" );
        taHead.put( "栏目ID", "classId" );
        taHead.put( "栏目名称", "classTree" );

        taHead.put( "最后更新日期", "ud" );
        taHead.put( "已停止更新天数", "dayCount" );

        List bodyResult = queryStatSiteContentWeekAndMonAndYearEndClassForTag( siteId, eFlag );

        String fileFlagName = "栏目维护统计";

        String flagName = "";

        if( "week".equals( eFlag ) || StringUtil.isStringNull( eFlag ) )
        {
            flagName = "一周无更新";
        }
        else if( "threeMon".equals( eFlag ) )
        {
            flagName = "三个月无更新";
        }
        else if( "halfYear".equals( eFlag ) )
        {
            flagName = "半年无更新";
        }
        else if( "year".equals( eFlag ) )
        {
            flagName = "一年以上无更新";
        }
        else if( "zero".equals( eFlag ) )
        {
            flagName = "无数据栏目";
        }

        fileFlagName = fileFlagName + "(" + flagName + "-";

        SiteGroupBean site = null;

        if( StringUtil.isStringNull( siteId ) )
        {
            List siteList = InitSiteGroupInfoBehavior.siteGroupListCache;

            if( !siteList.isEmpty() )
            {
                site = ( SiteGroupBean ) siteList.get( 0 );
            }
        }
        else
        {
            site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( StringUtil.getLongValue( siteId, -1 ) );
        }

        if( site != null )
        {
            fileFlagName = fileFlagName + site.getSiteName() + ")";
        }

        return genExcel( taHead, bodyResult, fileFlagName );

    }

    private String genExcel( Map taHead, List bodyResult, String flagName )
    {
        Workbook wb = new SXSSFWorkbook(); // 创建一个文档

        Sheet sh = wb.createSheet();

        List colNameList = createTableHead( taHead, wb, sh );

        createTableBody( colNameList, bodyResult, wb, sh );

        String base = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        // 当前时间日期
        String day = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        String fileBase = base + "sys_temp";

        File test = new File( fileBase );

        if( !test.exists() )
        {
            test.mkdirs();
        }

        String fullName = fileBase + File.separator + flagName + "_" + day + ".xlsx";

        FileOutputStream fileOut = null;

        try
        {
            fileOut = new FileOutputStream( fullName );
            wb.write( fileOut );
        }
        catch ( Exception e )
        {

            e.printStackTrace();

            try
            {

                fileOut.close();
            }
            catch ( IOException e1 )
            {

            }

        }

        return fullName;
    }

    @SuppressWarnings( "deprecation" )
    private List createTableHead( Map head, Workbook wb, Sheet sh )
    {

        int cellIndex = 0;
        Row title = sh.createRow( 0 ); // 创建一行

        Iterator iter = null;

        Entry entry = null;

        String key = null;

        String val = null;

        List colNameList = new ArrayList();

        iter = head.entrySet().iterator();

        Set bigc = new HashSet();

        while ( iter.hasNext() )
        {

            entry = ( Entry ) iter.next();

            key = ( String ) entry.getKey();

            val = ( String ) entry.getValue();

            if( key.indexOf( "部门" ) != -1 || key.indexOf( "栏目名" ) != -1 )
            {
                bigc.add( cellIndex );
            }

            colNameList.add( val );

            title.createCell( cellIndex++ ).setCellValue( key ); // 创建单元格

        }

        CellStyle style = wb.createCellStyle(); // 单元格样式
        style.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND ); // 设置背景颜色模式
        style.setFillForegroundColor( HSSFColor.GREY_25_PERCENT.index );
        // 设置背景颜色
        style.setAlignment( HSSFCellStyle.ALIGN_CENTER );
        Font font = wb.createFont();
        font.setFontName( "黑体" );
        font.setFontHeightInPoints( ( short ) 13 );// 设置字体大小
        style.setFont( font ); // 设置字体
        for ( int i = 0; i < cellIndex; i++ )
        {

            title.getCell( i ).setCellStyle( style );

            if( i == 0 )
            {
                sh.setColumnWidth( i, 2000 ); // 设置列宽
            }
            else if( bigc.contains( i ) )
            {
                sh.setColumnWidth( i, 16000 ); // 设置列宽
            }
            else
            {
                sh.setColumnWidth( i, 4000 ); // 设置列宽
            }
        }

        return colNameList;
    }

    private void createTableBody( List colNameList, List body, Workbook wb, Sheet sh )
    {
        Map valMap = null;

        String val = null;

        Double numVal = null;

        String col = null;

        for ( int i = 0; i < body.size(); i++ )
        {
            valMap = ( Map ) body.get( i );

            Row row = sh.createRow( i + 1 );

            int cellIndex = 0;

            for ( int j = 0; j < colNameList.size(); j++ )
            {
                col = ( String ) colNameList.get( j );

                val = valMap.get( col ) != null ? valMap.get( col ).toString() : "0";

                if( "rank".equals( col ) )
                {
                    val = ( i + 1 ) + "";
                }

                double test = StringUtil.getDoubleValue( val, -1 );

                if( test != -1 )
                {
                    row.createCell( cellIndex++ ).setCellValue( test );
                }
                else
                {
                    row.createCell( cellIndex++ ).setCellValue( val );
                }
            }

        }
    }

    private List disposeSiteContentTrace( String ids, Date startT, Date endT )
    {

        String key = "disposeSiteContentTrace:" + ids + "|" + startT + "|" + endT;

        List result = ( List ) cache.getEntry( key );

        if( result != null )
        {
            return result;
        }

        result = new ArrayList();

        StringBuilder buf = new StringBuilder();

        StringBuilder flagBuf = new StringBuilder();

        List siteList = null;

        if( StringUtil.isStringNull( ids ) )
        {
            Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

            Long orgIdVar = ( Long ) auth.getOrgIdentity();

            Long childOrgIdVar = null;

            siteList = OrgService.getInstance().retrieveSiteBeanByOrgId( orgIdVar, childOrgIdVar );

            SiteGroupBean site = null;

            for ( int i = 0; i < siteList.size(); i++ )
            {
                site = ( SiteGroupBean ) siteList.get( i );

                if( i + 1 != siteList.size() )
                {
                    buf.append( site.getSiteId() + "," );
                    flagBuf.append( "'" + site.getSiteFlag() + "'," );
                }
                else
                {
                    buf.append( site.getSiteId().toString() );
                    flagBuf.append( "'" + site.getSiteFlag() + "'" );
                }
            }
        }
        else
        {
            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( StringUtil.getLongValue( ids, -1 ) );

            buf.append( site.getSiteId().toString() );

            flagBuf.append( site.getSiteFlag() );
        }

        // List idList = StringUtil.changeStringToList( ids, "," );
        //
        // StringBuilder buf = new StringBuilder();
        //
        // for ( int i = 0; i < idList.size(); i++ )
        // {
        // if( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) > 0 )
        // {
        // if( i + 1 != idList.size() )
        // {
        // buf.append( idList.get( i ) + "," );
        // }
        // else
        // {
        // buf.append( idList.get( i ) );
        // }
        // }
        // }

        /**
         * 分析站点内容更新明细
         */

        Calendar curr = Calendar.getInstance();

        curr.add( Calendar.DAY_OF_MONTH, -7 );

        Date weekEnd = curr.getTime();

        curr = Calendar.getInstance();

        curr.add( Calendar.DAY_OF_MONTH, -90 );

        Date threeMonEnd = curr.getTime();

        curr = Calendar.getInstance();

        curr.add( Calendar.DAY_OF_MONTH, -180 );

        Date halfYearEnd = curr.getTime();

        curr = Calendar.getInstance();

        curr.add( Calendar.DAY_OF_MONTH, -360 );

        Date yearEnd = curr.getTime();

        String defOrder = "addAllCount";

        String defOrderFlag = "desc";

        if( startT == null || endT == null )
        {
            result = statDao.queryStatClassContentTraceCollBySiteId( flagBuf.toString(), buf
                .toString(), defOrder, defOrderFlag, weekEnd, threeMonEnd, halfYearEnd, yearEnd );

            if( StringUtil.isStringNull( ids ) )
            {

                Map allColl = statDao
                    .queryStatAllClassContentTraceColl( flagBuf.toString(), buf.toString(),
                        defOrder, defOrderFlag, weekEnd, threeMonEnd, halfYearEnd, yearEnd );

                allColl.put( "siteId", "" );
                allColl.put( "siteName", "合计" );

                result.add( allColl );
            }
        }
        else
        {
            result = statDao.queryStatClassContentTraceCollBySiteId( flagBuf.toString(), buf
                .toString(), startT, endT, defOrder, defOrderFlag, weekEnd, threeMonEnd,
                halfYearEnd, yearEnd );

            if( StringUtil.isStringNull( ids ) )
            {
                Map allColl = statDao.queryStatAllClassContentTraceColl( flagBuf.toString(), buf
                    .toString(), startT, endT, defOrder, defOrderFlag, weekEnd, threeMonEnd,
                    halfYearEnd, yearEnd );

                allColl.put( "siteId", "" );
                allColl.put( "siteName", "合计" );

                result.add( allColl );
            }
        }

        cache.putEntry( key, result );

        return result;
    }

    private List disposeOrgContentTrace( String ids, Date startT, Date endT )
    {
        String key = "disposeOrgContentTrace:" + ids + "|" + startT + "|" + endT;

        List result = ( List ) cache.getEntry( key );

        if( result != null )
        {
            return result;
        }

        result = new ArrayList();

        /**
         * 分析组织机构内容更新明细
         */

        List corg = OrgService.getInstance().retrieveOrganizationBeanByOrgIdLinearFlag(
            StringUtil.getLongValue( ids, -1 ) );

        if( corg == null )
        {
            Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

            corg = OrgService.getInstance().retrieveOrganizationBeanByOrgIdLinearFlag(
                StringUtil.getLongValue( auth.getOrgIdentity().toString(), -1 ) );
        }

        SystemOrganizationBean org = null;

        StringBuilder buf = new StringBuilder();

        for ( int i = 0; i < corg.size(); i++ )
        {
            org = ( SystemOrganizationBean ) corg.get( i );

            if( i + 1 != corg.size() )
            {
                buf.append( org.getOrgId() + "," );
            }
            else
            {
                buf.append( org.getOrgId() );
            }

        }

        // List idList = StringUtil.changeStringToList( ids, "," );
        //
        // StringBuilder buf = new StringBuilder();
        //
        // for ( int i = 0; i < idList.size(); i++ )
        // {
        // if( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) > 0 )
        // {
        // if( i + 1 != idList.size() )
        // {
        // buf.append( idList.get( i ) + "," );
        // }
        // else
        // {
        // buf.append( idList.get( i ) );
        // }
        // }
        // }

        String defOrder = "addAllCount";

        String defOrderFlag = "desc";

        if( startT == null || endT == null )
        {
            result = statDao.queryStatOrgContentTraceCollBySiteId( buf.toString(), defOrder,
                defOrderFlag );

            Map allColl = statDao.queryStatAllOrgContentTraceColl( buf.toString(), defOrder,
                defOrderFlag );

            allColl.put( "orgId", Long.valueOf( -9999 ) );
            allColl.put( "orgName", "合计" );

            result.add( allColl );
        }
        else
        {
            result = statDao.queryStatOrgContentTraceCollBySiteId( buf.toString(), startT, endT,
                defOrder, defOrderFlag );

            Map allColl = statDao.queryStatAllOrgContentTraceColl( buf.toString(), startT, endT,
                defOrder, defOrderFlag );

            allColl.put( "orgId", Long.valueOf( -9999 ) );
            allColl.put( "orgName", "合计" );

            result.add( allColl );
        }

        cache.putEntry( key, result );

        return result;
    }

    private List disposeManagerContentTrace( String ids, Date startT, Date endT )
    {
        String key = "disposeManagerContentTrace:" + ids + "|" + startT + "|" + endT;

        List result = ( List ) cache.getEntry( key );

        if( result != null )
        {
            return result;
        }

        /**
         * 分析管理员内容更新明细
         */

        List corg = OrgService.getInstance().retrieveOrganizationBeanByOrgIdLinearFlag(
            StringUtil.getLongValue( ids, -1 ) );

        if( corg == null )
        {
            Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

            corg = OrgService.getInstance().retrieveOrganizationBeanByOrgIdLinearFlag(
                StringUtil.getLongValue( auth.getOrgIdentity().toString(), -1 ) );
        }

        SystemOrganizationBean org = null;

        StringBuilder buf = new StringBuilder();

        for ( int i = 0; i < corg.size(); i++ )
        {
            org = ( SystemOrganizationBean ) corg.get( i );

            if( i + 1 != corg.size() )
            {
                buf.append( org.getLinearOrderFlag() + "," );
            }
            else
            {
                buf.append( org.getLinearOrderFlag() );
            }

        }

        String defOrder = "addAllCount";

        String defOrderFlag = "desc";

        if( startT == null || endT == null )
        {
            result = statDao.queryStatManagerContentTraceCollBySiteId( buf.toString(), defOrder,
                defOrderFlag );

            Map allColl = statDao.queryStatAllManagerContentTraceColl( buf.toString(), defOrder,
                defOrderFlag );

            allColl.put( "userId", Long.valueOf( -9999 ) );
            allColl.put( "orgName", "所有部门" );
            allColl.put( "userName", "合计" );

            result.add( allColl );
        }
        else
        {
            result = statDao.queryStatManagerContentTraceCollBySiteId( buf.toString(), startT,
                endT, defOrder, defOrderFlag );

            Map allColl = statDao.queryStatAllManagerContentTraceColl( buf.toString(), startT,
                endT, defOrder, defOrderFlag );

            allColl.put( "userId", Long.valueOf( -9999 ) );
            allColl.put( "orgName", "所有部门" );
            allColl.put( "userName", "合计" );

            result.add( allColl );
        }

         cache.putEntry( key, result );

        return result;
    }

    private Integer changeSqlSumResToInt( Object obj )
    {
        if( obj instanceof BigDecimal )
        {
            return Integer.valueOf( ( ( BigDecimal ) obj ).intValue() );
        }
        else if( obj instanceof Double )
        {
            return Integer.valueOf( ( ( Double ) obj ).intValue() );
        }

        return Integer.parseInt( obj.toString() );
    }

}
