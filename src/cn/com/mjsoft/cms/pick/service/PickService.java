package cn.com.mjsoft.cms.pick.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.block.dao.BlockDao;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.dao.ChannelDao;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.controller.OperationDisposeObserverController;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.dao.ContentDao;
import cn.com.mjsoft.cms.content.dao.vo.ContentMainInfo;
import cn.com.mjsoft.cms.content.dao.vo.ContentStatus;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.dao.MetaDataDao;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.pick.bean.PickContentRuleBean;
import cn.com.mjsoft.cms.pick.bean.PickContentTaskBean;
import cn.com.mjsoft.cms.pick.dao.PickDao;
import cn.com.mjsoft.cms.pick.dao.vo.PickContentRule;
import cn.com.mjsoft.cms.pick.dao.vo.PickContentTask;
import cn.com.mjsoft.cms.publish.bean.PublishStatusBean;
import cn.com.mjsoft.cms.resources.dao.vo.SiteResource;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.schedule.dao.ScheduleDao;
import cn.com.mjsoft.cms.schedule.service.ScheduleService;
import cn.com.mjsoft.cms.search.dao.vo.SearchIndexContentState;
import cn.com.mjsoft.cms.search.service.SearchService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.cms.workflow.service.WorkflowService;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.config.SystemRuntimeConfig;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.authorization.AuthorizationHandler;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.HtmlUtil;
import cn.com.mjsoft.framework.util.ImageUtil;
import cn.com.mjsoft.framework.util.ObjectUtility;
import cn.com.mjsoft.framework.util.RegexUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;

@SuppressWarnings( "unchecked" )
public class PickService
{
    private static Logger log = Logger.getLogger( PickService.class );

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private ContentService contentService = ContentService.getInstance();

    private MetaDataService metaDataService = MetaDataService.getInstance();

    private ScheduleService scheduleService = ScheduleService.getInstance();

    private SearchService searchService = SearchService.getInstance();

    private static PickService service = null;

    private PickDao pickDao = null;

    private ChannelDao channelDao = null;
    private ContentDao contentDao = null;
    public MetaDataDao metaDataDao;
    private ScheduleDao scheduleDao = null;

    private PickService()
    {
        pickDao = new PickDao( mysqlEngine );

        channelDao = new ChannelDao( mysqlEngine );
        metaDataDao = new MetaDataDao( mysqlEngine );
        contentDao = new ContentDao( mysqlEngine );
        scheduleDao = new ScheduleDao( mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new PickService();
        }
    }

    public static PickService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    @SuppressWarnings( "unchecked" )
    public Map pickUpWebContentForTestConfig( Long ruleId )
    {
        PickContentRuleBean ruleBean = pickDao.querySinglePickRuleBeanById( ruleId );

        Map pickValueMap = new HashMap();

        if( ruleBean == null )
        {
            return pickValueMap;
        }

        String contentEntryUrl = SystemSafeCharUtil.resumeHTML( ruleBean.getContentUrlRule() );

        String[] contentEntryUrlArray = StringUtil.split( contentEntryUrl, "\r\n" );

        // 处理入口entry列表页

        List allHrefList = new ArrayList();
        Set needElementTagNameSet = new HashSet();

        needElementTagNameSet.add( "a" );

        /**
         * 进入列表页url访问,获取所有符合规则的超级链接访问
         */
        String trueListUrl = null;

        // 尝试1页 1~n -> 01~n
        String listUrlRule = SystemSafeCharUtil.resumeHTML( ruleBean.getListUrlRule() );

        int start = 1;

        int end = 100;

        String pageInfo = null;

        int l0 = listUrlRule.indexOf( "{" );
        int l02 = listUrlRule.indexOf( "}", l0 );

        if( l0 != -1 && l02 != -1 )
        {
            pageInfo = StringUtil.subString( listUrlRule, l0 + 1, l02 );
        }

        String[] nums = null;

        if( StringUtil.isStringNotNull( pageInfo ) )
        {
            nums = StringUtil.split( pageInfo, "-" );

            if( nums.length == 2 )
            {
                start = StringUtil.getIntValue( nums[0], 0 );

                end = StringUtil.getIntValue( nums[1], 100 );
            }

        }

        Document doc = null;

        List allContentPickUrlList = new ArrayList();

        List contenturlRegexList = new ArrayList();

        for ( int j = 0; j < contentEntryUrlArray.length; j++ )
        {
            // 获取内容页URl匹配正则
            contenturlRegexList.add( disposeUrlReg( contentEntryUrlArray[j] ) );
        }

        Set cHrefSet = new HashSet();

        if( start <= end )
        {
            // 列表首页优先
            trueListUrl = SystemSafeCharUtil.resumeHTML( ruleBean.getListHeadUrlRule() );

            if( StringUtil.isStringNotNull( trueListUrl ) )
            {
                log.info( "pickUpWebContentByConfig() 列表首页入口URL:" + trueListUrl );

                doc = HtmlUtil.connectUrl( trueListUrl , ruleBean.getConfigDesc());

                HtmlUtil.getAllNeedNodes( allHrefList, doc == null ? null : doc.body(), false,
                    needElementTagNameSet, null );

                // 初步分析
                String pHtml = ( doc == null ) ? "" : doc.body().outerHtml();

                for ( int j = 0; j < contenturlRegexList.size(); j++ )
                {
                    // 获取内容页URl匹配正则

                    cHrefSet.addAll( RegexUtil.matchRes( pHtml, ( String ) contenturlRegexList
                        .get( j ) ) );
                }

            }

            for ( int i = start; i <= end; i++ )
            {
                // 对列表页所有href进行分析
                trueListUrl = StringUtil.replaceString( SystemSafeCharUtil.resumeHTML( ruleBean
                    .getListUrlRule() ), "{" + pageInfo + "}", Integer.valueOf( i ).toString(),
                    false, false );
                log.info( "pickUpWebContentByConfig() 列表入口URL:" + trueListUrl );

                doc = HtmlUtil.connectUrl( trueListUrl , ruleBean.getConfigDesc());

                if( doc == null && i < 10 )
                {
                    // 加0测试个位
                    trueListUrl = StringUtil.replaceString( SystemSafeCharUtil.resumeHTML( ruleBean
                        .getListUrlRule() ), "{" + pageInfo + "}", "0"
                        + Integer.valueOf( i + 1 ).toString(), false, false );

                    log.info( "pickUpWebContentByConfig() 列表入口URL:" + trueListUrl );

                    doc = HtmlUtil.connectUrl( trueListUrl , ruleBean.getConfigDesc());

                }

                HtmlUtil.getAllNeedNodes( allHrefList, doc == null ? null : doc.body(), false,
                    needElementTagNameSet, null );

                // 初步分析
                String pHtml = ( doc == null ) ? "" : doc.body().outerHtml();

                for ( int j = 0; j < contenturlRegexList.size(); j++ )
                {
                    // 获取内容页URl匹配正则

                    cHrefSet.addAll( RegexUtil.matchRes( pHtml, ( String ) contenturlRegexList
                        .get( j ) ) );
                }

                if( doc != null )
                {
                    break;
                }
            }
        }
        else
        {

            // 列表首页优先
            trueListUrl = SystemSafeCharUtil.resumeHTML( ruleBean.getListHeadUrlRule() );

            if( StringUtil.isStringNotNull( trueListUrl ) )
            {

                log.info( "pickUpWebContentByConfig() 列表首页入口URL:" + trueListUrl );

                doc = HtmlUtil.connectUrl( trueListUrl , ruleBean.getConfigDesc());

                HtmlUtil.getAllNeedNodes( allHrefList, doc == null ? null : doc.body(), false,
                    needElementTagNameSet, null );

                // 初步分析
                String pHtml = ( doc == null ) ? "" : doc.body().outerHtml();

                for ( int j = 0; j < contenturlRegexList.size(); j++ )
                {
                    // 获取内容页URl匹配正则

                    cHrefSet.addAll( RegexUtil.matchRes( pHtml, ( String ) contenturlRegexList
                        .get( j ) ) );
                }
            }

            for ( int i = start; i >= end; i-- )
            {
                // 对列表页所有href进行分析
                trueListUrl = StringUtil.replaceString( SystemSafeCharUtil.resumeHTML( ruleBean
                    .getListUrlRule() ), "{" + pageInfo + "}", Integer.valueOf( i ).toString(),
                    false, false );
                log.info( "pickUpWebContentByConfig() 列表入口URL:" + trueListUrl );

                doc = HtmlUtil.connectUrl( trueListUrl, ruleBean.getConfigDesc() );

                if( doc == null && i < 10 )
                {
                    // 加0测试个位
                    trueListUrl = StringUtil.replaceString( SystemSafeCharUtil.resumeHTML( ruleBean
                        .getListUrlRule() ), "{" + pageInfo + "}", "0"
                        + Integer.valueOf( i + 1 ).toString(), false, false );

                    log.info( "pickUpWebContentByConfig() 列表入口URL:" + trueListUrl );

                    doc = HtmlUtil.connectUrl( trueListUrl , ruleBean.getConfigDesc());

                }

                HtmlUtil.getAllNeedNodes( allHrefList, doc == null ? null : doc.body(), false,
                    needElementTagNameSet, null );

                // 初步分析
                String pHtml = ( doc == null ) ? "" : doc.body().outerHtml();

                for ( int j = 0; j < contenturlRegexList.size(); j++ )
                {
                    // 获取内容页URl匹配正则

                    cHrefSet.addAll( RegexUtil.matchRes( pHtml, ( String ) contenturlRegexList
                        .get( j ) ) );
                }

                if( doc != null )
                {
                    break;
                }
            }
        }

        Node hrefNode = null;

        for ( int k = 0; k < allHrefList.size(); k++ )
        {
            hrefNode = ( Node ) allHrefList.get( k );

            if( cHrefSet.contains( hrefNode.attr( Constant.PICK.HTML_HREF_NODE ) ) )
            {
                continue;
            }
            // log.info( "当前<a>:" + hrefNode.toString() );

            for ( int x = 0; x < contenturlRegexList.size(); x++ )
            {
                log.info( "TARGET URL : " + hrefNode.attr( Constant.PICK.HTML_HREF_NODE ) );

                if( RegexUtil.match( hrefNode.attr( Constant.PICK.HTML_HREF_NODE ),
                    ( String ) contenturlRegexList.get( x ) ) )
                {
                    allContentPickUrlList.add( hrefNode.attr( Constant.PICK.HTML_HREF_NODE ) );
                }
            }
        }

        allContentPickUrlList.addAll( cHrefSet );

        String contentPickUrl;

        String title = "";

        String keywords = "";

        String author = "";

        String source = "";

        String summary = "";

        String content = "";

        for ( int z = allContentPickUrlList.size() - 1; z >= 0; z-- )
        {
            Random r = new Random();
            
            contentPickUrl = ( String ) allContentPickUrlList.get( r.nextInt( z ) );

            if( contentPickUrl.startsWith( "../" ) )
            {
                contentPickUrl = StringUtil.replaceString( contentPickUrl, "../", "" );
            }
            
            if( contentPickUrl.startsWith( "./" ) )
            {
                contentPickUrl = StringUtil.replaceString( contentPickUrl, "./", "" );
            }

            if( StringUtil.isStringNotNull( ruleBean.getPrefixSiteUrl() ) && contentPickUrl != null
                && !contentPickUrl.toLowerCase().startsWith( "http:" ) )
            {
                contentPickUrl = SystemSafeCharUtil.resumeHTML( disposePrefixUrl( ruleBean
                    .getPrefixSiteUrl(), "c" ) )
                    + contentPickUrl;
            }

            contentPickUrl = StringUtil.replaceString( contentPickUrl, "\\", "/" );

            doc = HtmlUtil.connectUrl( contentPickUrl , ruleBean.getConfigDesc());

            if( doc != null )
            {
                String docHtml = doc.outerHtml();
                 
                System.out
                    .println( "---------------------------------------------------------------------------------------------------------" );
                System.err.println( "title:" + doc.title() );
                System.err
                    .println( "---------------------------------------------------------------------------------------------------------" );

                String ts = SystemSafeCharUtil.resumeHTML( ruleBean.getTitleStart() );

                String te = SystemSafeCharUtil.resumeHTML( ruleBean.getTitleEnd() );

                String ks = SystemSafeCharUtil.resumeHTML( ruleBean.getKeywordStart() );

                String ke = SystemSafeCharUtil.resumeHTML( ruleBean.getKeywordEnd() );

                String fs = SystemSafeCharUtil.resumeHTML( ruleBean.getSourceStart() );

                String fe = SystemSafeCharUtil.resumeHTML( ruleBean.getSourceEnd() );

                String as = SystemSafeCharUtil.resumeHTML( ruleBean.getAuthorStart() );

                String ae = SystemSafeCharUtil.resumeHTML( ruleBean.getAuthorEnd() );

                String cs = SystemSafeCharUtil.resumeHTML( ruleBean.getContentStart() );

                String ce = SystemSafeCharUtil.resumeHTML( ruleBean.getContentEnd() );

                String ss = SystemSafeCharUtil.resumeHTML( ruleBean.getSummaryStart() );

                String se = SystemSafeCharUtil.resumeHTML( ruleBean.getSummaryEnd() );

                /**
                 * title
                 */
                if( StringUtil.isStringNotNull( ts ) )
                {
                    if( ts.startsWith( "id=\"" ) && StringUtil.isStringNull( te ) )
                    {
                        Element idE = doc.body().getElementById(
                            ts.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                        if( idE != null )
                        {
                            title = idE.text();
                        }
                    }
                    else
                    {
                        int fir0 = docHtml.indexOf( ts );
                        int fir02 = docHtml.indexOf( te, fir0 );
                        if( fir0 != -1 && fir02 != -1 && fir0 != fir02 )
                        {
                            title = StringUtil.subString( docHtml, fir0 + ts.length(), fir02 );

                            title = Jsoup.parse( title != null ? title : "" ).text();
                        }
                    }
                }

                pickValueMap.put( "title", title );

                /**
                 * summary
                 */
                if( StringUtil.isStringNotNull( ss ) )
                {
                    if( ss.startsWith( "id=\"" ) && StringUtil.isStringNull( se ) )
                    {
                        Element idE = doc.body().getElementById(
                            ss.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                        if( idE != null )
                        {
                            summary = idE.text();
                        }
                    }
                    else
                    {
                        int fir1 = docHtml.indexOf( ss );
                        int fir12 = docHtml.indexOf( se, fir1 );
                        if( fir1 != -1 && fir12 != -1 && fir1 != fir12 )
                        {
                            summary = StringUtil.subString( docHtml, fir1 + ss.length(), fir12 );

                            summary = Jsoup.parse( summary != null ? summary : "" ).text();
                        }
                    }
                }

                pickValueMap.put( "summary", summary );

                /**
                 * keywords
                 */
                if( StringUtil.isStringNotNull( ks ) )
                {
                    if( ks.startsWith( "id=\"" ) && StringUtil.isStringNull( ke ) )
                    {
                        Element idE = doc.body().getElementById(
                            ks.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                        if( idE != null )
                        {
                            keywords = idE.text();
                        }
                    }
                    else
                    {
                        int fir3 = docHtml.indexOf( ks );
                        int fir32 = docHtml.indexOf( ke, fir3 );
                        if( fir3 != -1 && fir32 != -1 && fir3 != fir32 )
                        {
                            keywords = StringUtil.subString( docHtml, fir3 + ks.length(), fir32 );

                            keywords = Jsoup.parse( keywords != null ? keywords : "" ).text();
                        }
                    }
                }

                pickValueMap.put( "keywords", keywords );

                /**
                 * author
                 */
                if( StringUtil.isStringNotNull( as ) )
                {
                    if( as.startsWith( "id=\"" ) && StringUtil.isStringNull( ae ) )
                    {
                        Element idE = doc.body().getElementById(
                            as.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                        if( idE != null )
                        {
                            author = idE.text();
                        }
                    }
                    else
                    {
                        int fir4 = docHtml.indexOf( as );
                        int fir42 = docHtml.indexOf( ae, fir4 );
                        if( fir4 != -1 && fir42 != -1 && fir4 != fir42 )
                        {
                            author = StringUtil.subString( docHtml, fir4 + as.length(), fir42 );

                            author = Jsoup.parse( author != null ? author : "" ).text();
                        }
                    }
                }

                pickValueMap.put( "author", author );

                /**
                 * source
                 */
                if( StringUtil.isStringNotNull( fs ) )
                {
                    if( fs.startsWith( "id=\"" ) && StringUtil.isStringNull( fe ) )
                    {
                        Element idE = doc.body().getElementById(
                            fs.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                        if( idE != null )
                        {
                            source = idE.text();
                        }
                    }
                    else
                    {
                        int fir5 = docHtml.indexOf( fs );
                        int fir52 = docHtml.indexOf( fe, fir5 );
                        if( fir5 != -1 && fir52 != -1 && fir5 != fir52 )
                        {
                            source = StringUtil.subString( docHtml, fir5 + fs.length(), fir52 );

                            source = Jsoup.parse( source != null ? source : "" ).text();
                        }
                    }
                }

                pickValueMap.put( "source", source );

                /**
                 * content
                 */
                if( StringUtil.isStringNotNull( cs ) )
                {
                    if( cs.startsWith( "id=\"" ) && StringUtil.isStringNull( ce ) )
                    {
                        Element idE = doc.body().getElementById(
                            cs.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                        if( idE != null )
                        {
                            content = idE.text();
                        }
                    }
                    else
                    {
                        int fir = docHtml.indexOf( cs );

                        if( fir != -1 )
                        {

                            int fir2 = docHtml.indexOf( ce, fir );

                            if( fir2 != -1 )
                            {
                                content = StringUtil.subString( docHtml, fir + cs.length(), fir2 );
                                content = Jsoup.parse( content != null ? content : "" ).text();
                            }
                        }
                    }
                }

                pickValueMap.put( "content", content );

                // 只需要测试一次
                return pickValueMap;
            }
        }

        return pickValueMap;

    }

    @SuppressWarnings( "unchecked" )
    public void pickUpWebContentByConfig( Long siteId, Long taskId, String pubEventKey, String mode )
    {
        PublishStatusBean status = new PublishStatusBean();

        if( pubEventKey != null )
        {
            OperationDisposeObserverController.statusMap.put( pubEventKey, status );
        }

        if( taskId == null )
        {
            return;
        }

        List allContentPickUrlList = new ArrayList();

        PickContentRuleBean ruleBean = null;

        Map pickUrlStatus = new TreeMap();

        Map pickUrlTag = new HashMap();

        Map pickUrlAuthor = new HashMap();

        Map pickTitle = new HashMap();

        StringBuffer saveCidBuf = new StringBuffer();

        ContentClassBean classBean = null;

        try
        {
            PickContentTaskBean taskBean = pickDao.querySinglePickTaskBeanById( taskId );

            if( taskBean == null )
            {
                return;
            }

            classBean = channelDao.querySingleClassBeanInfoByClassId( taskBean.getClassId() );

            if( classBean == null )
            {
                return;
            }

            DataModelBean model = metaDataDao.querySingleDataModelBeanById( classBean
                .getContentType() );

            if( model == null )
            {
                return;
            }

            ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataDao
                .querySingleModelPerMysqlCodeBean( model.getDataModelId() );

            if( sqlCodeBean == null )
            {
                return;
            }

            ruleBean = pickDao.querySinglePickRuleBeanById( taskBean.getRuleId() );

            if( ruleBean == null )
            {
                return;
            }

            Map ext = pickDao.querySinglePickModelExt( ruleBean.getExtModelId() );

            // 更新执行时间
            pickDao.updateExcuteDate( taskId, new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

            // String startEntryUrl = pickConfig.getStartEntryUrl();

            // String[] startEntryArray = StringUtil.split( startEntryUrl,
            // "\r\n" );

            String contentEntryUrl = SystemSafeCharUtil.resumeHTML( ruleBean.getContentUrlRule() );

            String[] contentEntryUrlArray = StringUtil.split( contentEntryUrl, "\r\n" );

            // 处理入口entry列表页

            List allHrefList = new ArrayList();
            Set needElementTagNameSet = new HashSet();

            needElementTagNameSet.add( "a" );

            /**
             * 进入列表页url访问,获取所有符合规则的超级链接访问
             */
            String trueListUrl = null;

            // 尝试1页 1~n -> 01~n
            String listUrlRule = SystemSafeCharUtil.resumeHTML( ruleBean.getListUrlRule() );

            int start = 1;

            int end = 100;

            String pageInfo = null;

            int l0 = listUrlRule.indexOf( "{" );
            int l02 = listUrlRule.indexOf( "}", l0 );

            if( l0 != -1 && l02 != -1 )
            {
                pageInfo = StringUtil.subString( listUrlRule, l0 + 1, l02 );
            }

            String[] nums = null;

            if( StringUtil.isStringNotNull( pageInfo ) )
            {
                nums = StringUtil.split( pageInfo, "-" );

                if( nums.length == 2 )
                {
                    start = StringUtil.getIntValue( nums[0], 0 );

                    end = StringUtil.getIntValue( nums[1], 100 );
                }
            }

            Document doc = null;

            int maxPageCount = taskBean.getPickMaxListPage().intValue();

            int pageCount = 0;

            List contenturlRegexList = new ArrayList();

            for ( int j = 0; j < contentEntryUrlArray.length; j++ )
            {
                // 获取内容页URl匹配正则
                contenturlRegexList.add( disposeUrlReg( contentEntryUrlArray[j] ) );
            }

            Set cHrefSet = new HashSet();

            if( start <= end )
            {
                // 列表首页优先
                trueListUrl = SystemSafeCharUtil.resumeHTML( ruleBean.getListHeadUrlRule() );

                if( StringUtil.isStringNotNull( trueListUrl ) )
                {

                    log.info( "pickUpWebContentByConfig() 列表首页入口URL:" + trueListUrl );

                    doc = HtmlUtil.connectUrl( trueListUrl , ruleBean.getConfigDesc());

                    HtmlUtil.getAllNeedNodes( allHrefList, doc == null ? null : doc.body(), false,
                        needElementTagNameSet, null );

                    // 初步分析
                    String pHtml = ( doc == null ) ? "" : doc.body().outerHtml();

                    if( StringUtil.isStringNotNull( ruleBean.getListStart() )
                        && StringUtil.isStringNotNull( ruleBean.getListEnd() ) )
                    {
                        int s = pHtml.indexOf( SystemSafeCharUtil.resumeHTML( ruleBean
                            .getListStart() ) );

                        int e = pHtml.indexOf( SystemSafeCharUtil
                            .resumeHTML( ruleBean.getListEnd() ), s );

                        if( s > 0 && e > 0 )
                        {
                            pHtml = StringUtil.subString( pHtml, s, e );
                        }
                    }

                    for ( int j = 0; j < contenturlRegexList.size(); j++ )
                    {
                        // 获取内容页URl匹配正则

                        cHrefSet.addAll( RegexUtil.matchRes( pHtml, ( String ) contenturlRegexList
                            .get( j ) ) );
                    }

                    pageCount++;
                }

                for ( int i = start; i <= end; i++ )
                {
                    // 对列表页所有href进行分析
                    trueListUrl = StringUtil.replaceString( SystemSafeCharUtil.resumeHTML( ruleBean
                        .getListUrlRule() ), "{" + pageInfo + "}", Integer.valueOf( i ).toString(),
                        false, false );
                    log.info( "pickUpWebContentByConfig() 列表入口URL:" + trueListUrl );

                    doc = HtmlUtil.connectUrl( trueListUrl, ruleBean.getConfigDesc() );

                    if( doc == null && i < 10 )
                    {

                        // 加0测试个位
                        trueListUrl = StringUtil.replaceString( SystemSafeCharUtil
                            .resumeHTML( ruleBean.getListUrlRule() ), "{" + pageInfo + "}", "0"
                            + Integer.valueOf( i + 1 ).toString(), false, false );

                        log.info( "pickUpWebContentByConfig() 列表入口URL:" + trueListUrl );

                        doc = HtmlUtil.connectUrl( trueListUrl, ruleBean.getConfigDesc() );

                    }

                    HtmlUtil.getAllNeedNodes( allHrefList, doc == null ? null : doc.body(), false,
                        needElementTagNameSet, null );

                    // 初步分析
                    String pHtml = ( doc == null ) ? "" : doc.body().outerHtml();

                    if( StringUtil.isStringNotNull( ruleBean.getListStart() )
                        && StringUtil.isStringNotNull( ruleBean.getListEnd() ) )
                    {
                        int s = pHtml.indexOf( SystemSafeCharUtil.resumeHTML( ruleBean
                            .getListStart() ) );

                        int e = pHtml.indexOf( SystemSafeCharUtil
                            .resumeHTML( ruleBean.getListEnd() ), s );

                        if( s > 0 && e > 0 )
                        {
                            pHtml = StringUtil.subString( pHtml, s, e );
                        }
                    }

                    for ( int j = 0; j < contenturlRegexList.size(); j++ )
                    {
                        // 获取内容页URl匹配正则

                        cHrefSet.addAll( RegexUtil.matchRes( pHtml, ( String ) contenturlRegexList
                            .get( j ) ) );
                    }

                    pageCount++;

                    if( pageCount >= maxPageCount )
                    {
                        break;
                    }

                }
            }
            else
            {

                // 列表首页优先
                trueListUrl = SystemSafeCharUtil.resumeHTML( ruleBean.getListHeadUrlRule() );

                if( StringUtil.isStringNotNull( trueListUrl ) )
                {

                    log.info( "pickUpWebContentByConfig() 列表首页入口URL:" + trueListUrl );

                    doc = HtmlUtil.connectUrl( trueListUrl, ruleBean.getConfigDesc() );

                    HtmlUtil.getAllNeedNodes( allHrefList, doc == null ? null : doc.body(), false,
                        needElementTagNameSet, null );

                    // 初步分析
                    String pHtml = ( doc == null ) ? "" : doc.body().outerHtml();

                    for ( int j = 0; j < contenturlRegexList.size(); j++ )
                    {
                        // 获取内容页URl匹配正则

                        cHrefSet.addAll( RegexUtil.matchRes( pHtml, ( String ) contenturlRegexList
                            .get( j ) ) );
                    }

                    pageCount++;
                }

                for ( int i = start; i >= end; i-- )
                {
                    // 对列表页所有href进行分析
                    trueListUrl = StringUtil.replaceString( SystemSafeCharUtil.resumeHTML( ruleBean
                        .getListUrlRule() ), "{" + pageInfo + "}", Integer.valueOf( i ).toString(),
                        false, false );

                    log.info( "pickUpWebContentByConfig() 列表入口URL:" + trueListUrl );

                    doc = HtmlUtil.connectUrl( trueListUrl , ruleBean.getConfigDesc());

                    if( doc == null && i < 10 )
                    {
                        // 加0测试个位
                        trueListUrl = StringUtil.replaceString( SystemSafeCharUtil
                            .resumeHTML( ruleBean.getListUrlRule() ), "{" + pageInfo + "}", "0"
                            + Integer.valueOf( i + 1 ).toString(), false, false );

                        log.info( "pickUpWebContentByConfig() 列表入口URL:" + trueListUrl );

                        doc = HtmlUtil.connectUrl( trueListUrl, ruleBean.getConfigDesc() );

                    }

                    HtmlUtil.getAllNeedNodes( allHrefList, doc == null ? null : doc.body(), false,
                        needElementTagNameSet, null );

                    // 初步分析
                    String pHtml = ( doc == null ) ? "" : doc.body().outerHtml();

                    for ( int j = 0; j < contenturlRegexList.size(); j++ )
                    {
                        // 获取内容页URl匹配正则

                        cHrefSet.addAll( RegexUtil.matchRes( pHtml, ( String ) contenturlRegexList
                            .get( j ) ) );
                    }

                    pageCount++;

                    if( pageCount >= maxPageCount )
                    {
                        break;
                    }

                }
            }

            Node hrefNode = null;

            Set urlSet = new TreeSet();

            for ( int k = allHrefList.size() - 1; k >= 0; k-- )
            {
                hrefNode = ( Node ) allHrefList.get( k );

                log.info( "当前<a>:" + hrefNode.toString() );

                if( cHrefSet.contains( hrefNode.attr( Constant.PICK.HTML_HREF_NODE ) ) )
                {
                    continue;
                }

                for ( int x = 0; x < contenturlRegexList.size(); x++ )
                {
                    if( RegexUtil.match( hrefNode.attr( Constant.PICK.HTML_HREF_NODE ),
                        ( String ) contenturlRegexList.get( x ) ) )
                    {
                        if( hrefNode.attr( "href" ).indexOf( "#" ) == -1 )
                        {
                            log.info( "匹配 <a>:" + hrefNode.attr( "href" ) );
                            urlSet.add( hrefNode.attr( Constant.PICK.HTML_HREF_NODE ) );
                        }
                    }
                }

            }

            allContentPickUrlList.addAll( urlSet );

            allContentPickUrlList.addAll( cHrefSet );

            int maxCount = taskBean.getPickMaxContent().intValue()
                * taskBean.getPickMaxListPage().intValue();

            status.setPubCount( Long.valueOf( maxCount ) );

            String contentPickUrl;

            String ts = SystemSafeCharUtil.resumeHTML( ruleBean.getTitleStart() );

            String te = SystemSafeCharUtil.resumeHTML( ruleBean.getTitleEnd() );

            String ks = SystemSafeCharUtil.resumeHTML( ruleBean.getKeywordStart() );

            String ke = SystemSafeCharUtil.resumeHTML( ruleBean.getKeywordEnd() );

            String fs = SystemSafeCharUtil.resumeHTML( ruleBean.getSourceStart() );

            String fe = SystemSafeCharUtil.resumeHTML( ruleBean.getSourceEnd() );

            String as = SystemSafeCharUtil.resumeHTML( ruleBean.getAuthorStart() );

            String ae = SystemSafeCharUtil.resumeHTML( ruleBean.getAuthorEnd() );

            String cs = SystemSafeCharUtil.resumeHTML( ruleBean.getContentStart() );

            String ce = SystemSafeCharUtil.resumeHTML( ruleBean.getContentEnd() );

            String ss = SystemSafeCharUtil.resumeHTML( ruleBean.getSummaryStart() );

            String se = SystemSafeCharUtil.resumeHTML( ruleBean.getSummaryEnd() );

            String ads = SystemSafeCharUtil.resumeHTML( ruleBean.getAddDateStart() );

            String ade = SystemSafeCharUtil.resumeHTML( ruleBean.getAddDateEnd() );

            int count = 1;

            Long titleCount = null;

            List needClearHtmlList = null;

            List scriptHtmlList = null;

            // for ( int z = 0; z <allContentPickUrlList.size(); z++ )
            for ( int z = allContentPickUrlList.size() - 1; z >= 0; z-- )
            {
                // if( pubEventKey != null )
                // {
                // if( OperationDisposeObserverFlow.statusMap
                // .get( pubEventKey ) == null )
                // {
                // // 是否状态已丢失,一般认为Client已经关闭了此操作
                // return;
                // }
                // }

                if( count > maxCount )
                {
                    // 最大内容值限制
                    break;
                }

                String title = "";

                String tags = "";

                String author = "";

                String addDate = "";

                String source = "";

                String summary = "";

                String content = "";

                status.setCurrent( Long.valueOf( count ) );

                contentPickUrl = ( String ) allContentPickUrlList.get( z );

                if( contentPickUrl.startsWith( "../" ) )
                {
                    contentPickUrl = StringUtil.replaceString( contentPickUrl, "../", "" );
                }
                
                if( contentPickUrl.startsWith( "./" ) )
                {
                    contentPickUrl = StringUtil.replaceString( contentPickUrl, "./", "" );
                }

                if( StringUtil.isStringNotNull( ruleBean.getPrefixSiteUrl() )
                    && contentPickUrl != null && !contentPickUrl.toLowerCase().startsWith( "http:" ) )
                {
                    contentPickUrl = SystemSafeCharUtil.resumeHTML( disposePrefixUrl( ruleBean
                        .getPrefixSiteUrl(), "c" ) )
                        + contentPickUrl;
                }

                contentPickUrl = StringUtil.replaceString( contentPickUrl, "\\", "/" );

                doc = HtmlUtil.connectUrl( contentPickUrl, ruleBean.getConfigDesc() );

                if( doc != null )
                {

                    // doc = cleaner.clean( doc );

                    String docHtml = doc.outerHtml();

                    // 去掉所有js 和 iframe
                    needClearHtmlList = StringUtil
                        .subStringToList( docHtml, "<iframe", "</iframe>" );

                    for ( int ih = 0; ih < needClearHtmlList.size(); ih++ )
                    {

                        docHtml = StringUtil.replaceString( docHtml, "<"
                            + ( String ) needClearHtmlList.get( ih ), "", false, false );
                    }

                    scriptHtmlList = StringUtil.subStringToList( docHtml, "<script", "</script>" );

                    for ( int ih = 0; ih < scriptHtmlList.size(); ih++ )
                    {
                        docHtml = StringUtil.replaceString( docHtml, "<"
                            + ( String ) scriptHtmlList.get( ih ), "", false, false );
                    }

                    System.out.println( "" );
                    System.out
                        .println( "---------------------------------------------------------------------------------------------------------" );
                    System.out.println( "title:" + doc.title() );
                    System.out
                        .println( "---------------------------------------------------------------------------------------------------------" );

                    Map pickValueMap = new HashMap();
                    /**
                     * title
                     */
                    if( StringUtil.isStringNotNull( ts ) )
                    {
                        if( ts.startsWith( "id=\"" ) && StringUtil.isStringNull( te ) )
                        {
                            Element idE = doc.body().getElementById(
                                ts.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                title = idE.text();
                            }
                        }
                        else
                        {
                            int fir0 = docHtml.indexOf( ts );
                            int fir02 = docHtml.indexOf( te, fir0 );
                            if( fir0 != -1 )
                            {
                                title = StringUtil.subString( docHtml, fir0 + ts.length(), fir02 );

                                // if( Constant.COMMON.OFF.equals( ruleBean
                                // .getHtmlMode() ) )
                                {
                                    title = Jsoup.parse( title != null ? title : "" ).text();
                                }
                            }
                        }
                    }

                    pickValueMap.put( "title", title );

                    if( StringUtil.isStringNotNull( title ) )
                    {
                        /**
                         * 此处需要判断是否有重复标题,不需要在main-info里查询,而直接在trace里查
                         */
                        titleCount = pickDao.queryTraceTitleCount( siteId, title );

                        if( titleCount.longValue() > 0 )
                        {
                            count++;

                            continue;
                        }

                        pickTitle.put( contentPickUrl, title );
                    }
                    else
                    {
                        pickTitle.put( contentPickUrl, contentPickUrl );
                    }

                    // 采集生效
                    pickUrlStatus.put( contentPickUrl, Constant.COMMON.ON );

                    /**
                     * summary
                     */
                    if( StringUtil.isStringNotNull( ss ) )
                    {
                        if( ss.startsWith( "id=\"" ) && StringUtil.isStringNull( se ) )
                        {
                            Element idE = doc.body().getElementById(
                                ss.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                summary = idE.text();
                            }
                        }
                        else
                        {
                            int fir1 = docHtml.indexOf( ss );
                            int fir12 = docHtml.indexOf( se, fir1 );
                            if( fir1 != -1 )
                            {
                                summary = StringUtil.subString( docHtml, fir1 + ss.length(), fir12 );

                                summary = Jsoup.parse( summary != null ? summary : "" ).text();

                            }
                        }
                    }

                    pickValueMap.put( "summary", summary );

                    /**
                     * tags
                     */
                    if( StringUtil.isStringNotNull( ks ) )
                    {
                        if( ks.startsWith( "id=\"" ) && StringUtil.isStringNull( ke ) )
                        {
                            Element idE = doc.body().getElementById(
                                ks.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                tags = idE.text();
                            }
                        }
                        else
                        {
                            int fir3 = docHtml.indexOf( ks );
                            int fir32 = docHtml.indexOf( ke, fir3 );
                            if( fir3 != -1 && fir32 != -1 )
                            {
                                tags = StringUtil.subString( docHtml, fir3 + ks.length(), fir32 );

                                tags = Jsoup.parse( tags != null ? tags : "" ).text();
                            }
                        }
                    }

                    pickUrlTag.put( contentPickUrl, tags );

                    /**
                     * author
                     */
                    if( StringUtil.isStringNotNull( as ) )
                    {
                        if( as.startsWith( "id=\"" ) && StringUtil.isStringNull( ae ) )
                        {
                            Element idE = doc.body().getElementById(
                                as.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                author = idE.text();
                            }
                        }
                        else
                        {
                            int fir4 = docHtml.indexOf( as );
                            int fir42 = docHtml.indexOf( ae, fir4 );
                            if( fir4 != -1 && fir42 != -1 )
                            {
                                author = StringUtil.subString( docHtml, fir4 + as.length(), fir42 );

                                author = Jsoup.parse( author != null ? author : "" ).text();
                            }
                        }
                    }

                    pickValueMap.put( "author", author );

                    /**
                     * addDate
                     */
                    if( StringUtil.isStringNotNull( ads ) )
                    {
                        if( ads.startsWith( "id=\"" ) && StringUtil.isStringNull( ade ) )
                        {
                            Element idE = doc.body().getElementById(
                                ads.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                addDate = idE.text();
                            }
                        }
                        else
                        {
                            int fir4 = docHtml.indexOf( ads );
                            int fir42 = docHtml.indexOf( ade, fir4 );
                            if( fir4 != -1 && fir42 != -1 && fir4 != fir42 )
                            {
                                addDate = StringUtil
                                    .subString( docHtml, fir4 + ads.length(), fir42 );

                                addDate = Jsoup.parse( addDate != null ? addDate : "" ).text();
                            }
                        }
                    }

                    pickValueMap.put( "addDate", addDate );

                    /**
                     * source
                     */
                    if( StringUtil.isStringNotNull( fs ) )
                    {
                        if( fs.startsWith( "id=\"" ) && StringUtil.isStringNull( fe ) )
                        {
                            Element idE = doc.body().getElementById(
                                fs.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                source = idE.text();
                            }
                        }
                        else
                        {
                            int fir5 = docHtml.indexOf( fs );
                            int fir52 = docHtml.indexOf( fe, fir5 );
                            if( fir5 != -1 && fir52 != -1 )
                            {
                                source = StringUtil.subString( docHtml, fir5 + fs.length(), fir52 );

                                source = Jsoup.parse( source != null ? source : "" ).text();
                            }
                        }
                    }

                    pickValueMap.put( "source", source );

                    pickUrlAuthor.put( contentPickUrl, source );

                    /**
                     * content
                     */
                    if( StringUtil.isStringNotNull( cs ) )
                    {
                        if( cs.startsWith( "id=\"" ) && StringUtil.isStringNull( ce ) )
                        {
                            Element idE = doc.body().getElementById(
                                cs.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                content = idE.outerHtml();

                                if( Constant.COMMON.OFF.equals( ruleBean.getHtmlMode() ) )
                                {
                                    content = Jsoup.clean( content != null ? content : "",
                                        basicWithImages() );
                                }
                            }
                        }
                        else
                        {
                            int fir = docHtml.indexOf( cs );

                            if( fir != -1 )
                            {
                                int fir2 = docHtml.indexOf( ce, fir );

                                if( fir2 != -1 )
                                {
                                    content = StringUtil.subString( docHtml, fir + cs.length(),
                                        fir2 );

                                    if( Constant.COMMON.OFF.equals( ruleBean.getHtmlMode() ) )
                                    {
                                        // content = Jsoup.parse(
                                        // content != null ? content : "" )
                                        // .text();

                                        content = Jsoup.clean( content != null ? content : "",
                                            basicWithImages() );
                                    }
                                }
                            }
                        }
                    }

                    // 处理summary,有些采集summary不符合要求,需要截取
                    SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                        .getEntry( siteId );

                    int summaryLength = site.getSummaryLength().intValue();

                    String ctext = Jsoup.parse( content != null ? content : "" ).text();

                    if( summary.length() < summaryLength && ctext.length() >= summaryLength )
                    {
                        summary = StringUtil.subString( ctext, 0, summaryLength );

                        pickValueMap.put( "summary", summary );
                    }

                    // 下载外站图片

                    List dfList = new ArrayList();

                    if( Constant.COMMON.ON.equals( site.getDownOutImage() ) )
                    {
                        content = contentService.downloadImageFormWeb( contentPickUrl,
                            disposePrefixUrl( ruleBean.getPrefixSiteUrl(), "p" ), content,
                            ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                .getEntry( siteId ), classBean.getClassId(), dfList );

                        content = contentService.downloadFileFormWeb( contentPickUrl,
                            disposePrefixUrl( ruleBean.getPrefixSiteUrl(), "p" ), content,
                            ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                .getEntry( siteId ), classBean.getClassId(), dfList );
                    }

                    content = praseHtmlTagOpenAndClose( content );

                    // 站外链接
                    if( Constant.COMMON.ON.equals( site.getDeleteOutLink() ) )
                    {
                        content = contentService.deleteContentTextOutHref( content, site );
                    }

                    pickValueMap.put( model.getMainEditorFieldSign(), content );

                    pickValueMap.put( "jtopcms_sys_siteId", site.getSiteId() );

                    /**
                     * 扩展字段采集
                     */
                    List<Map> extRuleList = pickDao.queryPickModelExtByRuleId( ruleBean
                        .getExtModelId() );

                    for ( Map er : extRuleList )
                    {
                        String efs = SystemSafeCharUtil
                            .resumeHTML( ( String ) er.get( "colStart" ) );

                        String efe = SystemSafeCharUtil.resumeHTML( ( String ) er.get( "colEnd" ) );

                        String extData = "";

                        if( StringUtil.isStringNotNull( efs ) )
                        {
                            if( efs.startsWith( "id=\"" ) && StringUtil.isStringNull( efe ) )
                            {
                                Element idE = doc.body().getElementById(
                                    efs.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                                if( idE != null )
                                {
                                    extData = idE.outerHtml();

                                    if( Constant.COMMON.OFF.equals( ruleBean.getHtmlMode() ) )
                                    {
                                        extData = Jsoup.clean( extData != null ? extData : "",
                                            basicWithImages() );
                                    }
                                }
                            }
                            else
                            {
                                int fir = docHtml.indexOf( efs );

                                if( fir != -1 )
                                {
                                    int fir2 = docHtml.indexOf( efe, fir + efs.length() );

                                    if( fir2 != -1 )
                                    {
                                        extData = StringUtil.subString( docHtml,
                                            fir + efs.length(), fir2 );

                                        if( Constant.COMMON.OFF.equals( ruleBean.getHtmlMode() ) )
                                        {
                                            // content = Jsoup.parse(
                                            // content != null ? content : "" )
                                            // .text();

                                            extData = Jsoup.clean( extData != null ? extData : "",
                                                Whitelist.simpleText() );
                                        }
                                    }
                                }
                            }

                        }

                        pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );

                        String field = ( String ) er.get( "fieldSign" );

                        ModelFiledInfoBean fb = metaDataDao
                            .querySingleDataModelFieldBeanBySign( field );

                        if( fb.getHtmlElementId().longValue() != 10
                            && fb.getHtmlElementId().longValue() != 11
                            && fb.getHtmlElementId().longValue() != 12 )
                        {
                            extData = Jsoup
                                .clean( extData != null ? extData : "", Whitelist.none() );

                            pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );
                        }

                        if( fb.getHtmlElementId().longValue() == 10 )
                        {
                            extData = downloadFile( ruleBean.getListHeadUrlRule(),
                                disposePrefixUrl( ruleBean.getPrefixSiteUrl(), "p" ), extData,
                                ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                    .getEntry( site.getSiteId() ), classBean.getClassId(), dfList );

                            pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );

                        }
                        else if( fb.getHtmlElementId().longValue() == 11 )
                        {
                            extData = downloadImg( ruleBean.getListHeadUrlRule(), disposePrefixUrl(
                                ruleBean.getPrefixSiteUrl(), "p" ), extData,
                                ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                    .getEntry( site.getSiteId() ), classBean.getClassId(), dfList );

                            pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );

                        }

                        if( fb.getHtmlElementId().longValue() == 7
                            || fb.getHtmlElementId().longValue() == 8
                            || fb.getHtmlElementId().longValue() == 9 )
                        {
                            if( StringUtil.isStringNotNull( fb.getChoiceValue() ) )
                            {
                                Timestamp nts = DateAndTimeUtil.getNotNullTimestamp( extData, fb
                                    .getChoiceValue() );

                                pickValueMap.put( ( String ) er.get( "fieldSign" ), nts );

                            }
                        }

                    }

                    pickValueMap.put( "jtopcms_sys_siteId", site.getSiteId() );

                    if( "form".equals( mode ) )
                    {
                        pickValueMap.put( "modelId", ext.get( "modelId" ).toString() );

                        contentService.addOrEditDefineFormData( site, pickValueMap, false );
                    }
                    else
                    {

                        ContentMainInfo mainInfo = getSystemModelValueFromPickWebParam( ruleBean,
                            classBean, pickValueMap );

                        /**
                         * 处理自动关键字
                         */

                        List keyInfoList = null;

                        keyInfoList = searchService.disposeTextKeyword( title );

                        String key = null;

                        StringBuffer buf = new StringBuffer();

                        for ( int i = 0; i < keyInfoList.size(); i++ )
                        {
                            key = ( String ) keyInfoList.get( i );

                            if( i < ContentService.KEY_SIZE )
                            {
                                buf.append( key + " " );
                            }
                        }

                        mainInfo.setKeywords( buf.toString() );

                        // 获取自定义模型数据
                        List filedBeanList = metaDataService
                            .retrieveModelFiledInfoBeanList( classBean.getContentType() );

                        ModelFiledInfoBean bean = null;

                        List needUploadImageGroupInfoList = new ArrayList();

                        List userDefineParamList = new ArrayList();

                        Object val = null;

                        for ( int j = 0; j < filedBeanList.size(); j++ )
                        {
                            bean = ( ModelFiledInfoBean ) filedBeanList.get( j );

                            // 需要引入filed元数据来对不同类型字段进行对应处理
                            val = ServiceUtil.disposeDataModelFiledFromWeb( bean, pickValueMap,
                                needUploadImageGroupInfoList, false );

                            // 采集内容暂无图片水印问题

                            userDefineParamList.add( val );

                            // 自定义时间排序类型处理,需要独立新加值
                            if( Constant.METADATA.MYSQL_DATETIME.equals( bean.getPerdureType() )
                                && Constant.COMMON.ON.equals( bean.getOrderFlag() ) )
                            {
                                userDefineParamList.add( Long.valueOf( ( ( Timestamp ) val )
                                    .getTime() ) );
                            }
                        }

                        // 是否作为引导图
                        if( !dfList.isEmpty() )
                        {
                            // 当前时间日期
                            String day = DateAndTimeUtil
                                .getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

                            String imgResInfo = ServiceUtil.disposeSingleImageInfo( ( Long ) dfList
                                .get( 0 ) );

                            String newResInfo = ServiceUtil.copyImageRes( imgResInfo, site, day,
                                classBean.getClassId() );

                            if( taskBean.getGuideImgPos().intValue() == 1 )
                            {
                                mainInfo.setHomeImage( newResInfo );
                                mainInfo.setHomeImgFlag( Constant.COMMON.ON );
                            }
                            else if( taskBean.getGuideImgPos().intValue() == 2 )
                            {
                                mainInfo.setChannelImage( newResInfo );
                                mainInfo.setChannelImgFlag( Constant.COMMON.ON );
                            }
                            else if( taskBean.getGuideImgPos().intValue() == 3 )
                            {
                                mainInfo.setClassImage( newResInfo );
                                mainInfo.setClassImgFlag( Constant.COMMON.ON );
                            }
                            else if( taskBean.getGuideImgPos().intValue() == 4 )
                            {
                                mainInfo.setContentImage( newResInfo );
                                mainInfo.setContentImgFlag( Constant.COMMON.ON );
                            }

                        }

                        // 是否直接发布或需要筛选
                        if( Constant.COMMON.ON.equals( taskBean.getCensorMode() ) )
                        {
                            mainInfo.setCensorState( Constant.WORKFLOW.CENSOR_STATUS_PICK_WAIT );
                        }

                        UpdateState updateState = contentDao.saveContentMainInfo( mainInfo );

                        if( updateState.haveKey() )
                        {
                            Long contentId = Long.valueOf( updateState.getKey() );

                            // 记录下载的图片
                            Long resId = null;
                            for ( int i = 0; i < dfList.size(); i++ )
                            {
                                resId = ( Long ) dfList.get( i );

                                contentService.addDownloadImgRes( contentId, resId );
                            }

                            // 增加内容快速更新状态表

                            // 删除防止已经存在
                            contentDao.deleteContentStatus( contentId );

                            ContentStatus cons = new ContentStatus();

                            cons.setSelfContentId( contentId );

                            contentDao.saveContentStatus( cons );

                            saveCidBuf.append( contentId ).append( "," );

                            mainInfo.setContentId( contentId );

                            // 将ID放在最后一个位置
                            userDefineParamList.add( contentId );

                            contentDao.saveOrUpdateModelContent( sqlCodeBean.getInsertSql(),
                                userDefineParamList.toArray() );

                            // 设定最新的排序ID
                            contentDao.updateSystemContentOrderIdFlag( Double.valueOf( updateState
                                .getKey() ), contentId );

                            SearchIndexContentState searchIndexState = new SearchIndexContentState();

                            searchIndexState.setClassId( classBean.getClassId() );
                            searchIndexState.setContentId( mainInfo.getContentId() );

                            searchIndexState.setCensor( ( taskBean.getCensorMode().intValue() == 1 )
                                ? Constant.WORKFLOW.CENSOR_STATUS_PICK_WAIT
                                : Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );

                            searchIndexState.setIndexDate( mainInfo.getAddTime() );
                            searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_ADD );

                            searchIndexState.setModelId( classBean.getContentType() );
                            searchIndexState.setSiteId( siteId );

                            searchService.addIndexContentState( searchIndexState );

                        }

                    }

                }
                else
                {
                    System.err.println( "失败：" + contentPickUrl );

                    pickUrlStatus.put( contentPickUrl, Constant.COMMON.OFF );
                }
                count++;

            }
        }
        finally
        {
            ContentDao.releaseAllCountCache();

            ContentService.releaseContentCache();

        }

        status.setHomeOperStatus( Integer.valueOf( 1 ) );

        // 独立记录采集信息
        String contentPickUrl = null;

        Integer pickStatus = null;

        Iterator iter = pickUrlStatus.entrySet().iterator();

        Entry entry = null;

        while ( iter.hasNext() )
        {
            entry = ( Entry ) iter.next();

            pickStatus = ( Integer ) entry.getValue();

            contentPickUrl = ( String ) entry.getKey();

            pickDao.savePickTrace( ( String ) pickTitle.get( contentPickUrl ), classBean
                .getClassId(), contentPickUrl, ruleBean.getPickCfgId(), new Timestamp(
                DateAndTimeUtil.clusterTimeMillis() ), ( String ) pickUrlTag.get( contentPickUrl ),
                ( String ) pickUrlAuthor.get( contentPickUrl ), pickStatus, ruleBean.getSiteId() );
        }

        /**
         * 发布静态
         */
        String ids = saveCidBuf.toString();

        if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean.getContentProduceType() )
            && StringUtil.isStringNotNull( ids ) )
        {

            String cmsPath = JtRuntime.cmsServer.getDomainFullPath();

            // 首先要向权限系统注册本次访问，下面的url需要带入本次生成的key

            String uuidKey = StringUtil.getUUIDString();

            AuthorizationHandler.setInnerAccessFlag( uuidKey );

            String url = cmsPath
                + "/publish/generateContent.do?staticType=2&thread=true&job=true&innerAccessJtopSysFlag="
                + uuidKey + "&siteId=" + siteId + "&someContentId=" + ids;

            try
            {
                ServiceUtil.doGETMethodRequest( url );
            }
            finally
            {
                // AuthorizationHandler.romoveInnerAccessFlag( uuidKey );
            }
        }

    }

    @SuppressWarnings( "unchecked" )
    public boolean pickUpSingleWebContent( String targetUrl, Long classId, Long ruleId, String mode )
    {
        if( StringUtil.isStringNull( targetUrl ) )
        {
            return false;
        }

        List allContentPickUrlList = new ArrayList();

        PickContentRuleBean ruleBean = null;

        Map pickUrlStatus = new TreeMap();

        Map pickUrlTag = new HashMap();

        Map pickUrlAuthor = new HashMap();

        Map pickTitle = new HashMap();

        StringBuffer saveCidBuf = new StringBuffer();

        ContentClassBean classBean = null;

        SiteGroupBean site = null;

        try
        {

            DataModelBean model = null;

            ruleBean = pickDao.querySinglePickRuleBeanById( ruleId );

            if( ruleBean == null )
            {
                return false;
            }

            Map ext = pickDao.querySinglePickModelExt( ruleBean.getExtModelId() );

            if( !"form".equals( mode ) )
            {
                classBean = channelDao.querySingleClassBeanInfoByClassId( classId );

                if( classBean == null )
                {
                    return false;
                }

                site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                    .getEntry( classBean.getSiteFlag() );

                model = metaDataDao.querySingleDataModelBeanById( classBean.getContentType() );
            }
            else
            {
                classBean = new ContentClassBean();

                site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                    .getEntry( ruleBean.getSiteId() );

                model = metaDataDao.querySingleDataModelBeanById( ( Long ) ext.get( "modelId" ) );
            }

            if( site == null )
            {
                return false;
            }

            if( model == null )
            {
                return false;
            }

            ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataDao
                .querySingleModelPerMysqlCodeBean( model.getDataModelId() );

            if( sqlCodeBean == null )
            {
                return false;
            }

            Document doc = null;

            allContentPickUrlList.add( targetUrl );

            String contentPickUrl;

            String ts = SystemSafeCharUtil.resumeHTML( ruleBean.getTitleStart() );

            String te = SystemSafeCharUtil.resumeHTML( ruleBean.getTitleEnd() );

            String ks = SystemSafeCharUtil.resumeHTML( ruleBean.getKeywordStart() );

            String ke = SystemSafeCharUtil.resumeHTML( ruleBean.getKeywordEnd() );

            String fs = SystemSafeCharUtil.resumeHTML( ruleBean.getSourceStart() );

            String fe = SystemSafeCharUtil.resumeHTML( ruleBean.getSourceEnd() );

            String as = SystemSafeCharUtil.resumeHTML( ruleBean.getAuthorStart() );

            String ae = SystemSafeCharUtil.resumeHTML( ruleBean.getAuthorEnd() );

            String cs = SystemSafeCharUtil.resumeHTML( ruleBean.getContentStart() );

            String ce = SystemSafeCharUtil.resumeHTML( ruleBean.getContentEnd() );

            String ss = SystemSafeCharUtil.resumeHTML( ruleBean.getSummaryStart() );

            String se = SystemSafeCharUtil.resumeHTML( ruleBean.getSummaryEnd() );

            String ads = SystemSafeCharUtil.resumeHTML( ruleBean.getAddDateStart() );

            String ade = SystemSafeCharUtil.resumeHTML( ruleBean.getAddDateEnd() );

            int count = 1;

            Long titleCount = null;

            List needClearHtmlList = null;

            List scriptHtmlList = null;

            // for ( int z = 0; z <allContentPickUrlList.size(); z++ )
            for ( int z = allContentPickUrlList.size() - 1; z >= 0; z-- )
            {
                String title = "";

                String tags = "";

                String author = "";

                String source = "";

                String summary = "";

                String content = "";

                String addDate = "";

                contentPickUrl = ( String ) allContentPickUrlList.get( z );

                if( contentPickUrl.startsWith( "../" ) )
                {
                    contentPickUrl = StringUtil.replaceString( contentPickUrl, "../", "" );
                }
                
                if( contentPickUrl.startsWith( "./" ) )
                {
                    contentPickUrl = StringUtil.replaceString( contentPickUrl, "./", "" );
                }

                if( StringUtil.isStringNotNull( ruleBean.getPrefixSiteUrl() )
                    && contentPickUrl != null && !contentPickUrl.toLowerCase().startsWith( "http:" ) )
                {
                    contentPickUrl = SystemSafeCharUtil.resumeHTML( disposePrefixUrl( ruleBean
                        .getPrefixSiteUrl(), "c" ) )
                        + contentPickUrl;
                }

                contentPickUrl = StringUtil.replaceString( contentPickUrl, "\\", "/" );

                doc = HtmlUtil.connectUrl( contentPickUrl , ruleBean.getConfigDesc());

                if( doc != null )
                {

                    // doc = cleaner.clean( doc );

                    String docHtml = doc.outerHtml();

                    // 去掉所有js 和 iframe
                    needClearHtmlList = StringUtil
                        .subStringToList( docHtml, "<iframe", "</iframe>" );

                    for ( int ih = 0; ih < needClearHtmlList.size(); ih++ )
                    {

                        docHtml = StringUtil.replaceString( docHtml, "<"
                            + ( String ) needClearHtmlList.get( ih ), "", false, false );
                    }

                    scriptHtmlList = StringUtil.subStringToList( docHtml, "<script", "</script>" );

                    for ( int ih = 0; ih < scriptHtmlList.size(); ih++ )
                    {
                        docHtml = StringUtil.replaceString( docHtml, "<"
                            + ( String ) scriptHtmlList.get( ih ), "", false, false );
                    }

                    System.out.println( "" );
                    System.out
                        .println( "---------------------------------------------------------------------------------------------------------" );
                    System.out.println( "title:" + doc.title() );
                    System.out
                        .println( "---------------------------------------------------------------------------------------------------------" );

                    Map pickValueMap = new HashMap();
                    /**
                     * title
                     */
                    if( StringUtil.isStringNotNull( ts ) )
                    {
                        if( ts.startsWith( "id=\"" ) && StringUtil.isStringNull( te ) )
                        {
                            Element idE = doc.body().getElementById(
                                ts.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                title = idE.text();
                            }
                        }
                        else
                        {
                            int fir0 = docHtml.indexOf( ts );
                            int fir02 = docHtml.indexOf( te, fir0 );
                            if( fir0 != -1 )
                            {
                                title = StringUtil.subString( docHtml, fir0 + ts.length(), fir02 );

                                // if( Constant.COMMON.OFF.equals( ruleBean
                                // .getHtmlMode() ) )
                                {
                                    title = Jsoup.parse( title != null ? title : "" ).text();
                                }
                            }
                        }
                    }

                    pickValueMap.put( "title", title );

                    if( StringUtil.isStringNotNull( title ) )
                    {
                        /**
                         * 此处需要判断是否有重复标题,不需要在main-info里查询,而直接在trace里查
                         */
                        // titleCount = pickDao.queryTraceTitleCount(
                        // site.getSiteId(),
                        // title );
                        //
                        // if( titleCount.longValue() > 0 )
                        // {
                        // count++;
                        //
                        // continue;
                        // }
                        pickTitle.put( contentPickUrl, title );
                    }
                    else
                    {
                        pickTitle.put( contentPickUrl, contentPickUrl );
                    }

                    // 采集生效
                    pickUrlStatus.put( contentPickUrl, Constant.COMMON.ON );

                    /**
                     * summary
                     */
                    if( StringUtil.isStringNotNull( ss ) )
                    {
                        if( ss.startsWith( "id=\"" ) && StringUtil.isStringNull( se ) )
                        {
                            Element idE = doc.body().getElementById(
                                ss.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                summary = idE.text();
                            }
                        }
                        else
                        {
                            int fir1 = docHtml.indexOf( ss );
                            int fir12 = docHtml.indexOf( se, fir1 );
                            if( fir1 != -1 )
                            {
                                summary = StringUtil.subString( docHtml, fir1 + ss.length(), fir12 );

                                summary = Jsoup.parse( summary != null ? summary : "" ).text();

                            }
                        }
                    }

                    pickValueMap.put( "summary", summary );

                    /**
                     * tags
                     */
                    if( StringUtil.isStringNotNull( ks ) )
                    {
                        if( ks.startsWith( "id=\"" ) && StringUtil.isStringNull( ke ) )
                        {
                            Element idE = doc.body().getElementById(
                                ks.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                tags = idE.text();
                            }
                        }
                        else
                        {
                            int fir3 = docHtml.indexOf( ks );
                            int fir32 = docHtml.indexOf( ke, fir3 );
                            if( fir3 != -1 && fir32 != -1 )
                            {
                                tags = StringUtil.subString( docHtml, fir3 + ks.length(), fir32 );

                                tags = Jsoup.parse( tags != null ? tags : "" ).text();
                            }
                        }
                    }

                    pickUrlTag.put( contentPickUrl, tags );

                    /**
                     * author
                     */
                    if( StringUtil.isStringNotNull( as ) )
                    {
                        if( as.startsWith( "id=\"" ) && StringUtil.isStringNull( ae ) )
                        {
                            Element idE = doc.body().getElementById(
                                as.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                author = idE.text();
                            }
                        }
                        else
                        {
                            int fir4 = docHtml.indexOf( as );
                            int fir42 = docHtml.indexOf( ae, fir4 );
                            if( fir4 != -1 && fir42 != -1 )
                            {
                                author = StringUtil.subString( docHtml, fir4 + as.length(), fir42 );

                                author = Jsoup.parse( author != null ? author : "" ).text();
                            }
                        }
                    }

                    pickValueMap.put( "author", author );

                    /**
                     * addDate
                     */
                    if( StringUtil.isStringNotNull( ads ) )
                    {
                        if( ads.startsWith( "id=\"" ) && StringUtil.isStringNull( ade ) )
                        {
                            Element idE = doc.body().getElementById(
                                ads.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                addDate = idE.text();
                            }
                        }
                        else
                        {
                            int fir4 = docHtml.indexOf( ads );
                            int fir42 = docHtml.indexOf( ade, fir4 );
                            if( fir4 != -1 && fir42 != -1 && fir4 != fir42 )
                            {
                                addDate = StringUtil
                                    .subString( docHtml, fir4 + ads.length(), fir42 );

                                addDate = Jsoup.parse( addDate != null ? addDate : "" ).text();
                            }
                        }
                    }

                    pickValueMap.put( "addDate", addDate );

                    /**
                     * source
                     */
                    if( StringUtil.isStringNotNull( fs ) )
                    {
                        if( fs.startsWith( "id=\"" ) && StringUtil.isStringNull( fe ) )
                        {
                            Element idE = doc.body().getElementById(
                                fs.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                source = idE.text();
                            }
                        }
                        else
                        {
                            int fir5 = docHtml.indexOf( fs );
                            int fir52 = docHtml.indexOf( fe, fir5 );
                            if( fir5 != -1 && fir52 != -1 )
                            {
                                source = StringUtil.subString( docHtml, fir5 + fs.length(), fir52 );

                                source = Jsoup.parse( source != null ? source : "" ).text();
                            }
                        }
                    }

                    pickValueMap.put( "source", source );

                    pickUrlAuthor.put( contentPickUrl, source );

                    /**
                     * content
                     */
                    if( StringUtil.isStringNotNull( cs ) )
                    {
                        if( cs.startsWith( "id=\"" ) && StringUtil.isStringNull( ce ) )
                        {
                            Element idE = doc.body().getElementById(
                                cs.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                content = idE.outerHtml();

                                if( Constant.COMMON.OFF.equals( ruleBean.getHtmlMode() ) )
                                {

                                    content = Jsoup.clean( content != null ? content : "",
                                        basicWithImages() );

                                }
                            }
                        }
                        else
                        {
                            int fir = docHtml.indexOf( cs );

                            if( fir != -1 )
                            {
                                int fir2 = docHtml.indexOf( ce, fir );

                                if( fir2 != -1 )
                                {
                                    content = StringUtil.subString( docHtml, fir + cs.length(),
                                        fir2 );

                                    if( Constant.COMMON.OFF.equals( ruleBean.getHtmlMode() ) )
                                    {

                                        content = Jsoup.clean( content != null ? content : "",
                                            basicWithImages() );

                                    }
                                }
                            }
                        }
                    }

                    // 处理summary,有些采集summary不符合要求,需要截取

                    int summaryLength = site.getSummaryLength().intValue();

                    String ctext = Jsoup.parse( content != null ? content : "" ).text();

                    if( summary.length() < summaryLength && ctext.length() >= summaryLength )
                    {
                        summary = StringUtil.subString( ctext, 0, summaryLength );

                        pickValueMap.put( "summary", summary );
                    }

                    // 下载外站图片

                    List dfList = new ArrayList();

                    if( Constant.COMMON.ON.equals( site.getDownOutImage() ) )
                    {
                        content = contentService.downloadImageFormWeb( contentPickUrl,
                            disposePrefixUrl( ruleBean.getPrefixSiteUrl(), "p" ), content,
                            ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                .getEntry( site.getSiteId() ), classBean.getClassId(), dfList );

                        content = contentService.downloadFileFormWeb( contentPickUrl,
                            disposePrefixUrl( ruleBean.getPrefixSiteUrl(), "p" ), content,
                            ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                .getEntry( site.getSiteId() ), classBean.getClassId(), dfList );

                    }

                    content = praseHtmlTagOpenAndClose( content );

                    // 站外链接
                    if( Constant.COMMON.ON.equals( site.getDeleteOutLink() ) )
                    {
                        content = contentService.deleteContentTextOutHref( content, site );
                    }

                    pickValueMap.put( model.getMainEditorFieldSign(), content );

                    pickValueMap.put( "jtopcms_sys_siteId", site.getSiteId() );

                    /**
                     * 扩展字段采集
                     */
                    List<Map> extRuleList = pickDao.queryPickModelExtByRuleId( ruleBean
                        .getExtModelId() );

                    for ( Map er : extRuleList )
                    {
                        String efs = SystemSafeCharUtil
                            .resumeHTML( ( String ) er.get( "colStart" ) );

                        String efe = SystemSafeCharUtil.resumeHTML( ( String ) er.get( "colEnd" ) );

                        String extData = "";

                        if( StringUtil.isStringNotNull( efs ) )
                        {
                            if( efs.startsWith( "id=\"" ) && StringUtil.isStringNull( efe ) )
                            {
                                Element idE = doc.body().getElementById(
                                    efs.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                                if( idE != null )
                                {
                                    extData = idE.outerHtml();

                                    if( Constant.COMMON.OFF.equals( ruleBean.getHtmlMode() ) )
                                    {
                                        extData = Jsoup.clean( extData != null ? extData : "",
                                            basicWithImages() );
                                    }
                                }
                            }
                            else
                            {
                                int fir = docHtml.indexOf( efs );

                                if( fir != -1 )
                                {
                                    int fir2 = docHtml.indexOf( efe, fir + efs.length() );

                                    if( fir2 != -1 )
                                    {
                                        extData = StringUtil.subString( docHtml,
                                            fir + efs.length(), fir2 );

                                        if( Constant.COMMON.OFF.equals( ruleBean.getHtmlMode() ) )
                                        {
                                            // content = Jsoup.parse(
                                            // content != null ? content : "" )
                                            // .text();

                                            extData = Jsoup.clean( extData != null ? extData : "",
                                                Whitelist.simpleText() );
                                        }
                                    }
                                }
                            }

                        }
                        pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );

                        String field = ( String ) er.get( "fieldSign" );

                        ModelFiledInfoBean fb = metaDataDao
                            .querySingleDataModelFieldBeanBySign( field );

                        if( fb.getHtmlElementId().longValue() != 10
                            && fb.getHtmlElementId().longValue() != 11
                            && fb.getHtmlElementId().longValue() != 12 )
                        {
                            extData = Jsoup
                                .clean( extData != null ? extData : "", Whitelist.none() );

                            pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );
                        }

                        if( fb.getHtmlElementId().longValue() == 10 )
                        {
                            extData = downloadFile( ruleBean.getListHeadUrlRule(),
                                disposePrefixUrl( ruleBean.getPrefixSiteUrl(), "p" ), extData,
                                ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                    .getEntry( site.getSiteId() ), classBean.getClassId(), dfList );

                            pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );

                        }
                        else if( fb.getHtmlElementId().longValue() == 11 )
                        {
                            extData = downloadImg( ruleBean.getListHeadUrlRule(), disposePrefixUrl(
                                ruleBean.getPrefixSiteUrl(), "p" ), extData,
                                ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                    .getEntry( site.getSiteId() ), classBean.getClassId(), dfList );

                            pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );

                        }

                        if( fb.getHtmlElementId().longValue() == 7
                            || fb.getHtmlElementId().longValue() == 8
                            || fb.getHtmlElementId().longValue() == 9 )
                        {
                            if( StringUtil.isStringNotNull( fb.getChoiceValue() ) )
                            {
                                Timestamp nts = DateAndTimeUtil.getNotNullTimestamp( extData, fb
                                    .getChoiceValue() );

                                pickValueMap.put( ( String ) er.get( "fieldSign" ), nts );

                            }
                        }

                    }

                    pickValueMap.put( "jtopcms_sys_siteId", site.getSiteId() );

                    if( "form".equals( mode ) )
                    {
                        pickValueMap.put( "modelId", ext.get( "modelId" ).toString() );

                        contentService.addOrEditDefineFormData( site, pickValueMap, false );
                    }
                    else
                    {

                        ContentMainInfo mainInfo = getSystemModelValueFromPickWebParam( ruleBean,
                            classBean, pickValueMap );

                        /**
                         * 处理自动关键字
                         */

                        List keyInfoList = null;

                        keyInfoList = searchService.disposeTextKeyword( title );

                        String key = null;

                        StringBuffer buf = new StringBuffer();

                        for ( int i = 0; i < keyInfoList.size(); i++ )
                        {
                            key = ( String ) keyInfoList.get( i );

                            if( i < ContentService.KEY_SIZE )
                            {
                                buf.append( key + " " );
                            }
                        }

                        mainInfo.setKeywords( buf.toString() );

                        // 获取自定义模型数据
                        List filedBeanList = metaDataService
                            .retrieveModelFiledInfoBeanList( classBean.getContentType() );

                        ModelFiledInfoBean bean = null;

                        List needUploadImageGroupInfoList = new ArrayList();

                        List userDefineParamList = new ArrayList();

                        Object val = null;

                        for ( int j = 0; j < filedBeanList.size(); j++ )
                        {
                            bean = ( ModelFiledInfoBean ) filedBeanList.get( j );

                            // 需要引入filed元数据来对不同类型字段进行对应处理
                            val = ServiceUtil.disposeDataModelFiledFromWeb( bean, pickValueMap,
                                needUploadImageGroupInfoList, false );

                            // 采集内容暂无图片水印问题

                            userDefineParamList.add( val );

                            // 自定义时间排序类型处理,需要独立新加值
                            if( Constant.METADATA.MYSQL_DATETIME.equals( bean.getPerdureType() )
                                && Constant.COMMON.ON.equals( bean.getOrderFlag() ) )
                            {
                                userDefineParamList.add( Long.valueOf( ( ( Timestamp ) val )
                                    .getTime() ) );
                            }
                        }

                        // 是否直接发布或需要筛选
                        // if( Constant.COMMON.ON
                        // .equals( taskBean.getCensorMode() ) )
                        // {
                        // mainInfo.setCensorState(
                        // Constant.WORKFLOW.CENSOR_STATUS_PICK_WAIT );
                        // }

                        UpdateState updateState = contentDao.saveContentMainInfo( mainInfo );

                        if( updateState.haveKey() )
                        {
                            Long contentId = Long.valueOf( updateState.getKey() );

                            // 记录下载的图片
                            Long resId = null;
                            for ( int i = 0; i < dfList.size(); i++ )
                            {
                                resId = ( Long ) dfList.get( i );

                                contentService.addDownloadImgRes( contentId, resId );
                            }

                            // 增加内容快速更新状态表

                            // 删除防止已经存在
                            contentDao.deleteContentStatus( contentId );

                            ContentStatus cons = new ContentStatus();

                            cons.setSelfContentId( contentId );

                            contentDao.saveContentStatus( cons );

                            saveCidBuf.append( contentId ).append( "," );

                            mainInfo.setContentId( contentId );

                            // 将ID放在最后一个位置
                            userDefineParamList.add( contentId );

                            contentDao.saveOrUpdateModelContent( sqlCodeBean.getInsertSql(),
                                userDefineParamList.toArray() );

                            // 设定最新的排序ID
                            contentDao.updateSystemContentOrderIdFlag( Double.valueOf( updateState
                                .getKey() ), contentId );

                            SearchIndexContentState searchIndexState = new SearchIndexContentState();

                            searchIndexState.setClassId( classBean.getClassId() );
                            searchIndexState.setContentId( mainInfo.getContentId() );

                            searchIndexState.setCensor( Constant.WORKFLOW.CENSOR_STATUS_PICK_WAIT );

                            searchIndexState.setIndexDate( mainInfo.getAddTime() );
                            searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_ADD );

                            searchIndexState.setModelId( classBean.getContentType() );
                            searchIndexState.setSiteId( site.getSiteId() );

                            searchService.addIndexContentState( searchIndexState );

                        }

                    }

                }
                else
                {
                    System.err.println( "失败：" + contentPickUrl );

                    pickUrlStatus.put( contentPickUrl, Constant.COMMON.OFF );
                }
                count++;

            }
        }
        finally
        {
            ContentDao.releaseAllCountCache();

            ContentService.releaseContentCache();

        }

        // 独立记录采集信息
        String contentPickUrl = null;

        Integer pickStatus = null;

        Iterator iter = pickUrlStatus.entrySet().iterator();

        Entry entry = null;

        while ( iter.hasNext() )
        {
            entry = ( Entry ) iter.next();

            pickStatus = ( Integer ) entry.getValue();

            contentPickUrl = ( String ) entry.getKey();

            pickDao.savePickTrace( ( String ) pickTitle.get( contentPickUrl ), classBean
                .getClassId(), contentPickUrl, ruleBean.getPickCfgId(), new Timestamp(
                DateAndTimeUtil.clusterTimeMillis() ), ( String ) pickUrlTag.get( contentPickUrl ),
                ( String ) pickUrlAuthor.get( contentPickUrl ), pickStatus, ruleBean.getSiteId() );
        }

        /**
         * 发布静态
         */
        String ids = saveCidBuf.toString();

        if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean.getContentProduceType() )
            && StringUtil.isStringNotNull( ids ) )
        {

            String cmsPath = JtRuntime.cmsServer.getDomainFullPath();

            // 首先要向权限系统注册本次访问，下面的url需要带入本次生成的key

            String uuidKey = StringUtil.getUUIDString();

            AuthorizationHandler.setInnerAccessFlag( uuidKey );

            String url = cmsPath
                + "/publish/generateContent.do?staticType=2&thread=true&job=true&innerAccessJtopSysFlag="
                + uuidKey + "&siteId=" + site.getSiteId() + "&someContentId=" + ids;

            try
            {
                ServiceUtil.doGETMethodRequest( url );
            }
            finally
            {
                // AuthorizationHandler.romoveInnerAccessFlag( uuidKey );
            }
        }

        return true;

    }

    /**
     * 处理outd 单一内容
     * 
     * @param targetUrl
     * @param classId
     * @param ruleId
     * @param mode
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @SuppressWarnings( "unchecked" )
    public boolean pickUpSingleJSONOroutdOrDBInfo( PickContentTaskBean pt, String pubEventKey )
    {
        PublishStatusBean status = new PublishStatusBean();

        if( pubEventKey != null )
        {
            OperationDisposeObserverController.statusMap.put( pubEventKey, status );
        }

        List<Map> outdItemList = null;

        String vbClassName = pt.getDbRuleBeClass();

        // 进行outd采集

        if( StringUtil.isStringNotNull( vbClassName ) )
        {
            try
            {
                Class classObj = ObjectUtility.getClassInstance( vbClassName );

                if( classObj != null )
                {
                    Object valiedateBehavior = classObj.newInstance();

                    if( valiedateBehavior instanceof Behavior )
                    {
                        outdItemList = ( List<Map> ) ( ( Behavior ) valiedateBehavior ).operation(
                            pt, new Object[] {} );
                    }
                }

            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
        }

        if( outdItemList == null || outdItemList.isEmpty() )
        {
            return false;
        }

        status.setPubCount( Long.valueOf( outdItemList.size() ) );

        StringBuffer saveCidBuf = new StringBuffer();

        ContentClassBean classBean = null;

        SiteGroupBean site = null;

        Map pickUrlStatus = new TreeMap();

        Map pickUrlAuthor = new HashMap();

        Map pickTitle = new HashMap();

        try
        {

            DataModelBean model = null;

            classBean = channelDao.querySingleClassBeanInfoByClassId( pt.getClassId() );

            if( classBean == null )
            {
                return false;
            }

            site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                .getEntry( classBean.getSiteFlag() );

            model = metaDataDao.querySingleDataModelBeanById( classBean.getContentType() );

            if( site == null )
            {
                return false;
            }

            if( model == null )
            {
                return false;
            }

            ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataDao
                .querySingleModelPerMysqlCodeBean( model.getDataModelId() );

            if( sqlCodeBean == null )
            {
                return false;
            }

            String contentPickUrl;

            int count = 1;

            Long titleCount = null;

            for ( Map outdItem : outdItemList )
            {
                status.setCurrent( Long.valueOf( count ) );

                // outd生效
                contentPickUrl = ( String ) outdItem.get( "link" );

                String title = "";

                String author = "";

                String summary = "";

                String content = "";

                Map pickValueMap = new HashMap();
                /**
                 * title
                 */
                title = outdItem.get( "title" ).toString();

                title = Jsoup.parse( title != null ? title : "" ).text();

                pickValueMap.put( "title", title );

                pickTitle.put( contentPickUrl, title );

                if( StringUtil.isStringNotNull( title ) )
                {
                    /**
                     * 此处需要判断是否有重复标题,不需要在main-info里查询,而直接在trace里查
                     */
                    titleCount = pickDao.queryTraceTitleCount( site.getSiteId(), title );

                    if( titleCount.longValue() > 0 )
                    {
                        count++;

                        continue;
                    }

                    pickTitle.put( contentPickUrl, title );
                }
                else
                {
                    pickTitle.put( contentPickUrl, contentPickUrl );
                }

                // 采集生效
                pickUrlStatus.put( contentPickUrl, Constant.COMMON.ON );

                /**
                 * author source
                 */
                author = outdItem.get( "author" ).toString();

                author = Jsoup.parse( author != null ? author : "" ).text();

                pickValueMap.put( "author", "admin" );// 系统线程采集强制admin

                pickValueMap.put( "source", author );

                pickUrlAuthor.put( "contentPickUrl", author );

                /**
                 * addDate
                 */

                pickValueMap.put( "addDate", outdItem.get( "pubdate" ) );

                /**
                 * content
                 */
                content = outdItem.get( "description" ).toString();

                content = ( content != null ? content : "" );

                // 处理summary,有些采集summary不符合要求,需要截取

                int summaryLength = site.getSummaryLength().intValue();

                String ctext = Jsoup.parse( content != null ? content : "" ).text();

                if( summary.length() < summaryLength && ctext.length() >= summaryLength )
                {
                    summary = StringUtil.subString( ctext, 0, summaryLength );

                    pickValueMap.put( "summary", summary );
                }

                // 下载外站图片

                List dfList = new ArrayList();

                if( Constant.COMMON.ON.equals( site.getDownOutImage() ) )
                {
                    // 已禁止！因RRS源文章内图片存在超大图片，可能出现jvm内存耗尽
                    // content = contentService.downloadImageFormWeb(
                    // contentPickUrl, null, content,
                    // ( SiteGroupBean )
                    // InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                    // .getEntry( site.getSiteId() ), classBean.getClassId(),
                    // dfList );
                    //
                    // content = contentService.downloadFileFormWeb(
                    // contentPickUrl, null, content,
                    // ( SiteGroupBean )
                    // InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                    // .getEntry( site.getSiteId() ), classBean.getClassId(),
                    // dfList );

                }

                content = praseHtmlTagOpenAndClose( content );

                // 站外链接
                if( Constant.COMMON.ON.equals( site.getDeleteOutLink() ) )
                {
                    content = contentService.deleteContentTextOutHref( content, site );
                }

                pickValueMap.put( model.getMainEditorFieldSign(), content );

                pickValueMap.put( "jtopcms_sys_siteId", site.getSiteId() );

                /**
                 * 扩展字段采集
                 */
                List<Map> extRuleList = pickDao.queryPickModelExtByRuleId( classBean
                    .getContentType() );

                for ( Map er : extRuleList )
                {

                    String extData = "";

                    extData = outdItem.get( ( String ) er.get( "fieldSign" ) ).toString();

                    pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );

                    String field = ( String ) er.get( "fieldSign" );

                    ModelFiledInfoBean fb = metaDataDao.querySingleDataModelFieldBeanBySign( field );

                    if( fb.getHtmlElementId().longValue() != 10
                        && fb.getHtmlElementId().longValue() != 11
                        && fb.getHtmlElementId().longValue() != 12 )
                    {
                        extData = Jsoup.clean( extData != null ? extData : "", Whitelist.none() );

                        pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );
                    }

                    if( fb.getHtmlElementId().longValue() == 10 )
                    {
                        extData = downloadFile( null, null, extData,
                            ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                .getEntry( site.getSiteId() ), classBean.getClassId(), dfList );

                        pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );

                    }
                    else if( fb.getHtmlElementId().longValue() == 11 )
                    {
                        extData = downloadImg( null, null, extData,
                            ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                .getEntry( site.getSiteId() ), classBean.getClassId(), dfList );

                        pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );

                    }

                    if( fb.getHtmlElementId().longValue() == 7
                        || fb.getHtmlElementId().longValue() == 8
                        || fb.getHtmlElementId().longValue() == 9 )
                    {
                        if( StringUtil.isStringNotNull( fb.getChoiceValue() ) )
                        {
                            Timestamp nts = DateAndTimeUtil.getNotNullTimestamp( extData, fb
                                .getChoiceValue() );

                            pickValueMap.put( ( String ) er.get( "fieldSign" ), nts );

                        }
                    }

                }

                pickValueMap.put( "jtopcms_sys_siteId", site.getSiteId() );

                ContentMainInfo mainInfo = getSystemModelValueFromPickWebParam( null, classBean,
                    pickValueMap );

                /**
                 * 处理自动关键字
                 */

                List keyInfoList = null;

                keyInfoList = searchService.disposeTextKeyword( title );

                String key = null;

                StringBuffer buf = new StringBuffer();

                for ( int i = 0; i < keyInfoList.size(); i++ )
                {
                    key = ( String ) keyInfoList.get( i );

                    if( i < ContentService.KEY_SIZE )
                    {
                        buf.append( key + " " );
                    }
                }

                mainInfo.setKeywords( buf.toString() );

                // 获取自定义模型数据
                List filedBeanList = metaDataService.retrieveModelFiledInfoBeanList( classBean
                    .getContentType() );

                ModelFiledInfoBean bean = null;

                List needUploadImageGroupInfoList = new ArrayList();

                List userDefineParamList = new ArrayList();

                Object val = null;

                for ( int j = 0; j < filedBeanList.size(); j++ )
                {
                    bean = ( ModelFiledInfoBean ) filedBeanList.get( j );

                    // 需要引入filed元数据来对不同类型字段进行对应处理
                    val = ServiceUtil.disposeDataModelFiledFromWeb( bean, pickValueMap,
                        needUploadImageGroupInfoList, false );

                    // 采集内容暂无图片水印问题

                    userDefineParamList.add( val );

                    // 自定义时间排序类型处理,需要独立新加值
                    if( Constant.METADATA.MYSQL_DATETIME.equals( bean.getPerdureType() )
                        && Constant.COMMON.ON.equals( bean.getOrderFlag() ) )
                    {
                        userDefineParamList.add( Long.valueOf( ( ( Timestamp ) val ).getTime() ) );
                    }
                }

                // 是否直接发布或需要筛选
                if( Constant.COMMON.ON.equals( pt.getCensorMode() ) )
                {
                    mainInfo.setCensorState( Constant.WORKFLOW.CENSOR_STATUS_PICK_WAIT );
                }

                UpdateState updateState = contentDao.saveContentMainInfo( mainInfo );

                if( updateState.haveKey() )
                {
                    Long contentId = Long.valueOf( updateState.getKey() );

                    // 记录下载的图片
                    Long resId = null;
                    for ( int i = 0; i < dfList.size(); i++ )
                    {
                        resId = ( Long ) dfList.get( i );

                        contentService.addDownloadImgRes( contentId, resId );
                    }

                    // 增加内容快速更新状态表

                    // 删除防止已经存在
                    contentDao.deleteContentStatus( contentId );

                    ContentStatus cons = new ContentStatus();

                    cons.setSelfContentId( contentId );

                    contentDao.saveContentStatus( cons );

                    saveCidBuf.append( contentId ).append( "," );

                    mainInfo.setContentId( contentId );

                    // 将ID放在最后一个位置
                    userDefineParamList.add( contentId );

                    contentDao.saveOrUpdateModelContent( sqlCodeBean.getInsertSql(),
                        userDefineParamList.toArray() );

                    // 设定最新的排序ID
                    contentDao.updateSystemContentOrderIdFlag( Double
                        .valueOf( updateState.getKey() ), contentId );

                    SearchIndexContentState searchIndexState = new SearchIndexContentState();

                    searchIndexState.setClassId( classBean.getClassId() );
                    searchIndexState.setContentId( mainInfo.getContentId() );

                    searchIndexState.setCensor( Constant.WORKFLOW.CENSOR_STATUS_PICK_WAIT );

                    searchIndexState.setIndexDate( mainInfo.getAddTime() );
                    searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_ADD );

                    searchIndexState.setModelId( classBean.getContentType() );
                    searchIndexState.setSiteId( site.getSiteId() );

                    searchService.addIndexContentState( searchIndexState );

                }

                count++;

            }
        }
        finally
        {
            ContentDao.releaseAllCountCache();

            ContentService.releaseContentCache();

        }

        status.setHomeOperStatus( Integer.valueOf( 1 ) );

        // 独立记录采集信息
        String contentPickUrl = null;

        Integer pickStatus = null;

        Iterator iter = pickUrlStatus.entrySet().iterator();

        Entry entry = null;

        while ( iter.hasNext() )
        {
            entry = ( Entry ) iter.next();

            pickStatus = ( Integer ) entry.getValue();

            contentPickUrl = ( String ) entry.getKey();

            pickDao.savePickTrace( ( String ) pickTitle.get( contentPickUrl ), classBean
                .getClassId(), contentPickUrl, null, new Timestamp( DateAndTimeUtil
                .clusterTimeMillis() ), null, ( String ) pickUrlAuthor.get( contentPickUrl ),
                pickStatus, site.getSiteId() );
        }

        /**
         * 发布静态
         */
        String ids = saveCidBuf.toString();

        if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean.getContentProduceType() )
            && StringUtil.isStringNotNull( ids ) )
        {

            String cmsPath = JtRuntime.cmsServer.getDomainFullPath();

            // 首先要向权限系统注册本次访问，下面的url需要带入本次生成的key

            String uuidKey = StringUtil.getUUIDString();

            AuthorizationHandler.setInnerAccessFlag( uuidKey );

            String url = cmsPath
                + "/publish/generateContent.do?staticType=2&thread=true&job=true&innerAccessJtopSysFlag="
                + uuidKey + "&siteId=" + site.getSiteId() + "&someContentId=" + ids;

            try
            {
                ServiceUtil.doGETMethodRequest( url );
            }
            finally
            {
                // AuthorizationHandler.romoveInnerAccessFlag( uuidKey );
            }
        }

        return true;

    }

    @SuppressWarnings( "unchecked" )
    public boolean pickUpSingleInnerContent( String targetUrl, Long classId, Long ruleId,
        String mode )
    {
        if( StringUtil.isStringNull( targetUrl ) )
        {
            return false;
        }

        File root = new File( targetUrl );

        File[] fss = root.listFiles();

        List allContentPickUrlList = new ArrayList();

        PickContentRuleBean ruleBean = null;

        Map pickUrlStatus = new TreeMap();

        Map pickUrlTag = new HashMap();

        Map pickUrlAuthor = new HashMap();

        Map pickTitle = new HashMap();

        StringBuffer saveCidBuf = new StringBuffer();

        ContentClassBean classBean = null;

        SiteGroupBean site = null;

        try
        {
            DataModelBean model = null;

            ruleBean = pickDao.querySinglePickRuleBeanById( ruleId );

            if( ruleBean == null )
            {
                return false;
            }

            Map ext = pickDao.querySinglePickModelExt( ruleBean.getExtModelId() );

            if( !"form".equals( mode ) )
            {
                classBean = channelDao.querySingleClassBeanInfoByClassId( classId );

                if( classBean == null )
                {
                    return false;
                }

                site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                    .getEntry( classBean.getSiteFlag() );

                model = metaDataDao.querySingleDataModelBeanById( classBean.getContentType() );
            }
            else
            {
                classBean = new ContentClassBean();

                site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                    .getEntry( ruleBean.getSiteId() );

                model = metaDataDao.querySingleDataModelBeanById( ( Long ) ext.get( "modelId" ) );
            }

            if( site == null )
            {
                return false;
            }

            if( model == null )
            {
                return false;
            }

            ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataDao
                .querySingleModelPerMysqlCodeBean( model.getDataModelId() );

            if( sqlCodeBean == null )
            {
                return false;
            }

            Document doc = null;

            allContentPickUrlList.add( targetUrl );

            String contentPickUrl;

            String ts = SystemSafeCharUtil.resumeHTML( ruleBean.getTitleStart() );

            String te = SystemSafeCharUtil.resumeHTML( ruleBean.getTitleEnd() );

            String ks = SystemSafeCharUtil.resumeHTML( ruleBean.getKeywordStart() );

            String ke = SystemSafeCharUtil.resumeHTML( ruleBean.getKeywordEnd() );

            String fs = SystemSafeCharUtil.resumeHTML( ruleBean.getSourceStart() );

            String fe = SystemSafeCharUtil.resumeHTML( ruleBean.getSourceEnd() );

            String as = SystemSafeCharUtil.resumeHTML( ruleBean.getAuthorStart() );

            String ae = SystemSafeCharUtil.resumeHTML( ruleBean.getAuthorEnd() );

            String cs = SystemSafeCharUtil.resumeHTML( ruleBean.getContentStart() );

            String ce = SystemSafeCharUtil.resumeHTML( ruleBean.getContentEnd() );

            String ss = SystemSafeCharUtil.resumeHTML( ruleBean.getSummaryStart() );

            String se = SystemSafeCharUtil.resumeHTML( ruleBean.getSummaryEnd() );

            String ads = SystemSafeCharUtil.resumeHTML( ruleBean.getAddDateStart() );

            String ade = SystemSafeCharUtil.resumeHTML( ruleBean.getAddDateEnd() );

            int count = 1;

            Long titleCount = null;

            List needClearHtmlList = null;

            List scriptHtmlList = null;

            // for ( int z = 0; z <allContentPickUrlList.size(); z++ )
            for ( File inf : fss )
            {
                String title = "";

                String tags = "";

                String author = "";

                String source = "";

                String summary = "";

                String content = "";

                String addDate = "";

                try
                {
                    doc = Jsoup.parse( inf, "UTF-8", ruleBean.getPrefixSiteUrl() );
                }
                catch ( IOException e )
                {

                    e.printStackTrace();
                }

                if( doc != null )
                {

                    // doc = cleaner.clean( doc );

                    String docHtml = doc.outerHtml();

                    // 去掉所有js 和 iframe
                    needClearHtmlList = StringUtil
                        .subStringToList( docHtml, "<iframe", "</iframe>" );

                    for ( int ih = 0; ih < needClearHtmlList.size(); ih++ )
                    {

                        docHtml = StringUtil.replaceString( docHtml, "<"
                            + ( String ) needClearHtmlList.get( ih ), "", false, false );
                    }

                    scriptHtmlList = StringUtil.subStringToList( docHtml, "<script", "</script>" );

                    for ( int ih = 0; ih < scriptHtmlList.size(); ih++ )
                    {
                        docHtml = StringUtil.replaceString( docHtml, "<"
                            + ( String ) scriptHtmlList.get( ih ), "", false, false );
                    }

                    System.out.println( "" );
                    System.out
                        .println( "---------------------------------------------------------------------------------------------------------" );
                    System.out.println( "title:" + doc.title() );
                    System.out
                        .println( "---------------------------------------------------------------------------------------------------------" );

                    Map pickValueMap = new HashMap();
                    /**
                     * title
                     */
                    if( StringUtil.isStringNotNull( ts ) )
                    {
                        if( ts.startsWith( "id=\"" ) && StringUtil.isStringNull( te ) )
                        {
                            Element idE = doc.body().getElementById(
                                ts.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                title = idE.text();
                            }
                        }
                        else
                        {
                            int fir0 = docHtml.indexOf( ts );
                            int fir02 = docHtml.indexOf( te, fir0 );
                            if( fir0 != -1 )
                            {
                                title = StringUtil.subString( docHtml, fir0 + ts.length(), fir02 );

                                // if( Constant.COMMON.OFF.equals( ruleBean
                                // .getHtmlMode() ) )
                                {
                                    title = Jsoup.parse( title != null ? title : "" ).text();
                                }
                            }
                        }
                    }

                    pickValueMap.put( "title", title );

                    if( StringUtil.isStringNotNull( title ) )
                    {
                        /**
                         * 此处需要判断是否有重复标题,不需要在main-info里查询,而直接在trace里查
                         */
                        // titleCount = pickDao.queryTraceTitleCount(
                        // site.getSiteId(),
                        // title );
                        //
                        // if( titleCount.longValue() > 0 )
                        // {
                        // count++;
                        //
                        // continue;
                        // }
                        pickTitle.put( inf.getPath(), title );
                    }
                    else
                    {
                        pickTitle.put( inf.getPath(), inf.getPath() );
                    }

                    // 采集生效
                    pickUrlStatus.put( inf.getPath(), Constant.COMMON.ON );

                    /**
                     * summary
                     */
                    if( StringUtil.isStringNotNull( ss ) )
                    {
                        if( ss.startsWith( "id=\"" ) && StringUtil.isStringNull( se ) )
                        {
                            Element idE = doc.body().getElementById(
                                ss.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                summary = idE.text();
                            }
                        }
                        else
                        {
                            int fir1 = docHtml.indexOf( ss );
                            int fir12 = docHtml.indexOf( se, fir1 );
                            if( fir1 != -1 )
                            {
                                summary = StringUtil.subString( docHtml, fir1 + ss.length(), fir12 );

                                summary = Jsoup.parse( summary != null ? summary : "" ).text();

                            }
                        }
                    }

                    pickValueMap.put( "summary", summary );

                    /**
                     * tags
                     */
                    if( StringUtil.isStringNotNull( ks ) )
                    {
                        if( ks.startsWith( "id=\"" ) && StringUtil.isStringNull( ke ) )
                        {
                            Element idE = doc.body().getElementById(
                                ks.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                tags = idE.text();
                            }
                        }
                        else
                        {
                            int fir3 = docHtml.indexOf( ks );
                            int fir32 = docHtml.indexOf( ke, fir3 );
                            if( fir3 != -1 && fir32 != -1 )
                            {
                                tags = StringUtil.subString( docHtml, fir3 + ks.length(), fir32 );

                                tags = Jsoup.parse( tags != null ? tags : "" ).text();
                            }
                        }
                    }

                    pickUrlTag.put( inf.getPath(), tags );

                    /**
                     * author
                     */
                    if( StringUtil.isStringNotNull( as ) )
                    {
                        if( as.startsWith( "id=\"" ) && StringUtil.isStringNull( ae ) )
                        {
                            Element idE = doc.body().getElementById(
                                as.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                author = idE.text();
                            }
                        }
                        else
                        {
                            int fir4 = docHtml.indexOf( as );
                            int fir42 = docHtml.indexOf( ae, fir4 );
                            if( fir4 != -1 && fir42 != -1 )
                            {
                                author = StringUtil.subString( docHtml, fir4 + as.length(), fir42 );

                                author = Jsoup.parse( author != null ? author : "" ).text();
                            }
                        }
                    }

                    pickValueMap.put( "author", author );

                    /**
                     * addDate
                     */
                    if( StringUtil.isStringNotNull( ads ) )
                    {
                        if( ads.startsWith( "id=\"" ) && StringUtil.isStringNull( ade ) )
                        {
                            Element idE = doc.body().getElementById(
                                ads.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                addDate = idE.text();
                            }
                        }
                        else
                        {
                            int fir4 = docHtml.indexOf( ads );
                            int fir42 = docHtml.indexOf( ade, fir4 );
                            if( fir4 != -1 && fir42 != -1 && fir4 != fir42 )
                            {
                                addDate = StringUtil
                                    .subString( docHtml, fir4 + ads.length(), fir42 );

                                addDate = Jsoup.parse( addDate != null ? addDate : "" ).text();
                            }
                        }
                    }

                    pickValueMap.put( "addDate", addDate );

                    /**
                     * source
                     */
                    if( StringUtil.isStringNotNull( fs ) )
                    {
                        if( fs.startsWith( "id=\"" ) && StringUtil.isStringNull( fe ) )
                        {
                            Element idE = doc.body().getElementById(
                                fs.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                source = idE.text();
                            }
                        }
                        else
                        {
                            int fir5 = docHtml.indexOf( fs );
                            int fir52 = docHtml.indexOf( fe, fir5 );
                            if( fir5 != -1 && fir52 != -1 )
                            {
                                source = StringUtil.subString( docHtml, fir5 + fs.length(), fir52 );

                                source = Jsoup.parse( source != null ? source : "" ).text();
                            }
                        }
                    }

                    pickValueMap.put( "source", source );

                    pickUrlAuthor.put( inf.getPath(), source );

                    /**
                     * content
                     */
                    if( StringUtil.isStringNotNull( cs ) )
                    {
                        if( cs.startsWith( "id=\"" ) && StringUtil.isStringNull( ce ) )
                        {
                            Element idE = doc.body().getElementById(
                                cs.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                            if( idE != null )
                            {
                                content = idE.outerHtml();

                                if( Constant.COMMON.OFF.equals( ruleBean.getHtmlMode() ) )
                                {

                                    content = Jsoup.clean( content != null ? content : "",
                                        basicWithImages() );

                                }
                            }
                        }
                        else
                        {
                            int fir = docHtml.indexOf( cs );

                            if( fir != -1 )
                            {
                                int fir2 = docHtml.indexOf( ce, fir );

                                if( fir2 != -1 )
                                {
                                    content = StringUtil.subString( docHtml, fir + cs.length(),
                                        fir2 );

                                    if( Constant.COMMON.OFF.equals( ruleBean.getHtmlMode() ) )
                                    {

                                        content = Jsoup.clean( content != null ? content : "",
                                            basicWithImages() );

                                    }
                                }
                            }
                        }
                    }

                    // 处理summary,有些采集summary不符合要求,需要截取

                    int summaryLength = site.getSummaryLength().intValue();

                    String ctext = Jsoup.parse( content != null ? content : "" ).text();

                    if( summary.length() < summaryLength && ctext.length() >= summaryLength )
                    {
                        summary = StringUtil.subString( ctext, 0, summaryLength );

                        pickValueMap.put( "summary", summary );
                    }

                    // 下载外站图片

                    List dfList = new ArrayList();

                    if( Constant.COMMON.ON.equals( site.getDownOutImage() ) )
                    {
                        content = contentService.downloadImageFormWeb( ruleBean
                            .getListHeadUrlRule(), disposePrefixUrl( ruleBean.getPrefixSiteUrl(),
                            "p" ), content,
                            ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                .getEntry( site.getSiteId() ), classBean.getClassId(), dfList );

                        content = contentService.downloadFileFormWeb(
                            ruleBean.getListHeadUrlRule(), disposePrefixUrl( ruleBean
                                .getPrefixSiteUrl(), "p" ), content,
                            ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                .getEntry( site.getSiteId() ), classBean.getClassId(), dfList );

                    }

                    content = praseHtmlTagOpenAndClose( content );

                    // 站外链接
                    if( Constant.COMMON.ON.equals( site.getDeleteOutLink() ) )
                    {
                        content = contentService.deleteContentTextOutHref( content, site );
                    }

                    pickValueMap.put( model.getMainEditorFieldSign(), content );

                    pickValueMap.put( "jtopcms_sys_siteId", site.getSiteId() );

                    /**
                     * 扩展字段采集
                     */
                    List<Map> extRuleList = pickDao.queryPickModelExtByRuleId( ruleBean
                        .getExtModelId() );

                    for ( Map er : extRuleList )
                    {
                        String efs = SystemSafeCharUtil
                            .resumeHTML( ( String ) er.get( "colStart" ) );

                        String efe = SystemSafeCharUtil.resumeHTML( ( String ) er.get( "colEnd" ) );

                        String extData = "";

                        if( StringUtil.isStringNotNull( efs ) )
                        {
                            if( efs.startsWith( "id=\"" ) && StringUtil.isStringNull( efe ) )
                            {
                                Element idE = doc.body().getElementById(
                                    efs.replaceAll( "id=", "" ).replaceAll( "\"", "" ) );

                                if( idE != null )
                                {
                                    extData = idE.outerHtml();

                                    if( Constant.COMMON.OFF.equals( ruleBean.getHtmlMode() ) )
                                    {
                                        extData = Jsoup.clean( extData != null ? extData : "",
                                            basicWithImages() );
                                    }
                                }
                            }
                            else
                            {
                                int fir = docHtml.indexOf( efs );

                                if( fir != -1 )
                                {
                                    int fir2 = docHtml.indexOf( efe, fir + efs.length() );

                                    if( fir2 != -1 )
                                    {
                                        extData = StringUtil.subString( docHtml,
                                            fir + efs.length(), fir2 );

                                        if( Constant.COMMON.OFF.equals( ruleBean.getHtmlMode() ) )
                                        {
                                            // content = Jsoup.parse(
                                            // content != null ? content : "" )
                                            // .text();

                                            extData = Jsoup.clean( extData != null ? extData : "",
                                                Whitelist.simpleText() );
                                        }
                                    }
                                }
                            }

                        }

                        pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );

                        String field = ( String ) er.get( "fieldSign" );

                        ModelFiledInfoBean fb = metaDataDao
                            .querySingleDataModelFieldBeanBySign( field );

                        if( fb.getHtmlElementId().longValue() != 10
                            && fb.getHtmlElementId().longValue() != 11
                            && fb.getHtmlElementId().longValue() != 12 )
                        {
                            extData = Jsoup
                                .clean( extData != null ? extData : "", Whitelist.none() );

                            pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );
                        }

                        if( fb.getHtmlElementId().longValue() == 10 )
                        {
                            extData = downloadFile( ruleBean.getListHeadUrlRule(),
                                disposePrefixUrl( ruleBean.getPrefixSiteUrl(), "p" ), extData,
                                ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                    .getEntry( site.getSiteId() ), classBean.getClassId(), dfList );

                            pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );

                        }
                        else if( fb.getHtmlElementId().longValue() == 11 )
                        {
                            extData = downloadImg( ruleBean.getListHeadUrlRule(), disposePrefixUrl(
                                ruleBean.getPrefixSiteUrl(), "p" ), extData,
                                ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                                    .getEntry( site.getSiteId() ), classBean.getClassId(), dfList );

                            pickValueMap.put( ( String ) er.get( "fieldSign" ), extData );

                        }

                        if( fb.getHtmlElementId().longValue() == 7
                            || fb.getHtmlElementId().longValue() == 8
                            || fb.getHtmlElementId().longValue() == 9 )
                        {
                            if( StringUtil.isStringNotNull( fb.getChoiceValue() ) )
                            {
                                Timestamp nts = DateAndTimeUtil.getNotNullTimestamp( extData, fb
                                    .getChoiceValue() );

                                pickValueMap.put( ( String ) er.get( "fieldSign" ), nts );

                            }
                        }

                    }

                    pickValueMap.put( "jtopcms_sys_siteId", site.getSiteId() );

                    if( "form".equals( mode ) )
                    {
                        pickValueMap.put( "modelId", ext.get( "modelId" ).toString() );

                        contentService.addOrEditDefineFormData( site, pickValueMap, false );
                    }
                    else
                    {

                        ContentMainInfo mainInfo = getSystemModelValueFromPickWebParam( ruleBean,
                            classBean, pickValueMap );

                        /**
                         * 处理自动关键字
                         */

                        List keyInfoList = null;

                        keyInfoList = searchService.disposeTextKeyword( title );

                        String key = null;

                        StringBuffer buf = new StringBuffer();

                        for ( int i = 0; i < keyInfoList.size(); i++ )
                        {
                            key = ( String ) keyInfoList.get( i );

                            if( i < ContentService.KEY_SIZE )
                            {
                                buf.append( key + " " );
                            }
                        }

                        mainInfo.setKeywords( buf.toString() );

                        // 获取自定义模型数据
                        List filedBeanList = metaDataService
                            .retrieveModelFiledInfoBeanList( classBean.getContentType() );

                        ModelFiledInfoBean bean = null;

                        List needUploadImageGroupInfoList = new ArrayList();

                        List userDefineParamList = new ArrayList();

                        Object val = null;

                        for ( int j = 0; j < filedBeanList.size(); j++ )
                        {
                            bean = ( ModelFiledInfoBean ) filedBeanList.get( j );

                            // 需要引入filed元数据来对不同类型字段进行对应处理
                            val = ServiceUtil.disposeDataModelFiledFromWeb( bean, pickValueMap,
                                needUploadImageGroupInfoList, false );

                            // 采集内容暂无图片水印问题

                            userDefineParamList.add( val );

                            // 自定义时间排序类型处理,需要独立新加值
                            if( Constant.METADATA.MYSQL_DATETIME.equals( bean.getPerdureType() )
                                && Constant.COMMON.ON.equals( bean.getOrderFlag() ) )
                            {
                                userDefineParamList.add( Long.valueOf( ( ( Timestamp ) val )
                                    .getTime() ) );
                            }
                        }

                        // 是否直接发布或需要筛选
                        // if( Constant.COMMON.ON
                        // .equals( taskBean.getCensorMode() ) )
                        // {
                        // mainInfo.setCensorState(
                        // Constant.WORKFLOW.CENSOR_STATUS_PICK_WAIT );
                        // }

                        UpdateState updateState = contentDao.saveContentMainInfo( mainInfo );

                        if( updateState.haveKey() )
                        {
                            Long contentId = Long.valueOf( updateState.getKey() );

                            // 记录下载的图片
                            Long resId = null;
                            for ( int i = 0; i < dfList.size(); i++ )
                            {
                                resId = ( Long ) dfList.get( i );

                                contentService.addDownloadImgRes( contentId, resId );
                            }

                            // 增加内容快速更新状态表

                            // 删除防止已经存在
                            contentDao.deleteContentStatus( contentId );

                            ContentStatus cons = new ContentStatus();

                            cons.setSelfContentId( contentId );

                            contentDao.saveContentStatus( cons );

                            saveCidBuf.append( contentId ).append( "," );

                            mainInfo.setContentId( contentId );

                            // 将ID放在最后一个位置
                            userDefineParamList.add( contentId );

                            contentDao.saveOrUpdateModelContent( sqlCodeBean.getInsertSql(),
                                userDefineParamList.toArray() );

                            // 设定最新的排序ID
                            contentDao.updateSystemContentOrderIdFlag( Double.valueOf( updateState
                                .getKey() ), contentId );

                            SearchIndexContentState searchIndexState = new SearchIndexContentState();

                            searchIndexState.setClassId( classBean.getClassId() );
                            searchIndexState.setContentId( mainInfo.getContentId() );

                            searchIndexState.setCensor( Constant.WORKFLOW.CENSOR_STATUS_PICK_WAIT );

                            searchIndexState.setIndexDate( mainInfo.getAddTime() );
                            searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_ADD );

                            searchIndexState.setModelId( classBean.getContentType() );
                            searchIndexState.setSiteId( site.getSiteId() );

                            searchService.addIndexContentState( searchIndexState );

                        }
                    }

                }
                else
                {
                    System.err.println( "失败：" + inf.getPath() );

                    pickUrlStatus.put( inf.getPath(), Constant.COMMON.OFF );
                }
                count++;

            }
        }
        finally
        {
            ContentDao.releaseAllCountCache();

            ContentService.releaseContentCache();

        }

        // 独立记录采集信息
        String contentPickUrl = null;

        Integer pickStatus = null;

        Iterator iter = pickUrlStatus.entrySet().iterator();

        Entry entry = null;

        while ( iter.hasNext() )
        {
            entry = ( Entry ) iter.next();

            pickStatus = ( Integer ) entry.getValue();

            contentPickUrl = ( String ) entry.getKey();

            pickDao.savePickTrace( ( String ) pickTitle.get( contentPickUrl ), classBean
                .getClassId(), contentPickUrl, ruleBean.getPickCfgId(), new Timestamp(
                DateAndTimeUtil.clusterTimeMillis() ), ( String ) pickUrlTag.get( contentPickUrl ),
                ( String ) pickUrlAuthor.get( contentPickUrl ), pickStatus, ruleBean.getSiteId() );
        }

        /**
         * 发布静态
         */
        String ids = saveCidBuf.toString();

        if( !"form".equals( mode )
            && Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean.getContentProduceType() )
            && StringUtil.isStringNotNull( ids ) )
        {

            String cmsPath = JtRuntime.cmsServer.getDomainFullPath();

            // 首先要向权限系统注册本次访问，下面的url需要带入本次生成的key

            String uuidKey = StringUtil.getUUIDString();

            AuthorizationHandler.setInnerAccessFlag( uuidKey );

            String url = cmsPath
                + "/publish/generateContent.do?staticType=2&thread=true&job=true&innerAccessJtopSysFlag="
                + uuidKey + "&siteId=" + site.getSiteId() + "&someContentId=" + ids;

            try
            {
                ServiceUtil.doGETMethodRequest( url );
            }
            finally
            {
                // AuthorizationHandler.romoveInnerAccessFlag( uuidKey );
            }
        }

        return true;

    }

    public String praseHtmlTagOpenAndClose( String content )
    {
        if( content == null )
        {
            return null;
        }

        content = Jsoup.parseBodyFragment( content ).outerHtml();

        content = StringUtil.replaceString( content, "<html>", "", false, false );

        content = StringUtil.replaceString( content, "</html>", "", false, false );

        content = StringUtil.replaceString( content, "<head>", "", false, false );

        content = StringUtil.replaceString( content, "</head>", "", false, false );

        content = StringUtil.replaceString( content, "<body>", "", false, false );

        content = StringUtil.replaceString( content, "</body>", "", false, false );

        return content;
    }

    public Object getPickTaskInfoTag( String type )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( StringUtil.isStringNull( type ) )
        {
            return pickDao.queryAllPickTaskBeanBySiteId( site.getSiteId() );
        }

        return pickDao.queryPickTaskBeanBySiteId( site.getSiteId(), StringUtil
            .getIntValue( type, 0 ) );
    }

    public PickContentTaskBean retrieveSinglePickTaskBeanById( Long taskId )
    {
        return pickDao.querySinglePickTaskBeanById( taskId );
    }

    public void addNewPickContentRule( PickContentRule pickRule )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        pickRule.setSiteId( site.getSiteId() );

        pickDao.save( pickRule );
    }

    public void addModelExtPickRule( Map params )
    {

        Long modelId = StringUtil.getLongValue( ( String ) params.get( "modelId" ), -1 );

        try
        {
            mysqlEngine.beginTransaction();

            // 扩展字段匹配规则

            UpdateState us = pickDao.savePickModelExt( modelId, ( String ) params.get( "eprName" ),
                ( String ) params.get( "eprDesc" ) );

            List<Map> pfList = metaDataDao.queryPickModelField( modelId );

            for ( Map field : pfList )
            {
                String fieldName = ( String ) field.get( "filedSign" );

                String colStart = ( String ) params.get( fieldName + "Start" );

                String colEnd = ( String ) params.get( fieldName + "End" );

                pickDao.savePickModelExtField( us.getKey(), fieldName, colStart, colEnd );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void editModelExtPickRule( Map params )
    {

        Long eprId = StringUtil.getLongValue( ( String ) params.get( "eprId" ), -1 );

        try
        {
            mysqlEngine.beginTransaction();

            pickDao.updatePickModelExt( params );

            pickDao.deletePickModelExtField( eprId );

            Map epr = pickDao.querySinglePickModelExt( eprId );

            // 扩展字段匹配规则

            List<Map> pfList = metaDataDao.queryPickModelField( ( Long ) epr.get( "modelId" ) );

            for ( Map field : pfList )
            {
                String fieldName = ( String ) field.get( "filedSign" );

                String colStart = ( String ) params.get( fieldName + "Start" );

                String colEnd = ( String ) params.get( fieldName + "End" );

                pickDao.savePickModelExtField( eprId, fieldName, colStart, colEnd );

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void deleteModelExtPickRule( Long eprId )
    {

        try
        {
            mysqlEngine.beginTransaction();

            pickDao.deletePickModelExt( eprId );

            pickDao.deletePickModelExtField( eprId );

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void addNewPickContentTask( PickContentTask pickTask )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        try
        {
            mysqlEngine.beginTransaction();

            pickTask.setSiteId( site.getSiteId() );

            UpdateState us = pickDao.save( pickTask );

            if( us.haveKey() && pickTask.getPeriod().intValue() > 0 )
            {
                scheduleService.addPickWebContentScheduleJobDetail( Long.valueOf( us.getKey() ),
                    site.getSiteId(), pickTask.getPeriodType(), pickTask.getPeriod() );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void editPickContentRule( PickContentRule pickRule, Map params )
    {

        try
        {
            mysqlEngine.beginTransaction();
            // 默认字段匹配规则
            PickContentRuleBean oldCfg = pickDao.querySinglePickRuleBeanById( pickRule
                .getPickCfgId() );

            pickDao.update( pickRule );

            if( oldCfg.getExtModelId() != null && oldCfg.getExtModelId() > 0 )
            {
                pickDao.deletePickModelExtField( pickRule.getExtModelId() );

                Map epr = pickDao.querySinglePickModelExt( pickRule.getExtModelId() );

                // 扩展字段匹配规则

                List<Map> pfList = metaDataDao.queryPickModelField( ( Long ) epr.get( "modelId" ) );

                for ( Map field : pfList )
                {
                    String fieldName = ( String ) field.get( "filedSign" );

                    String colStart = ( String ) params.get( fieldName + "Start" );

                    String colEnd = ( String ) params.get( fieldName + "End" );

                    pickDao.savePickModelExtField( pickRule.getExtModelId(), fieldName, colStart,
                        colEnd );

                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void deletePickContentRule( List idList )
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

                pickDao.deletePickRule( Long.valueOf( id ) );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void deleteAllPickWebTrace( Long siteId )
    {
        pickDao.deleteAllPickWebTrace( siteId );
    }

    public void deletePickWebTrace( List idList )
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

                pickDao.deletePickWebTraceById( Long.valueOf( id ) );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public Object getPickWebContentTrace( String ruleId, String pn, String size )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long rid = StringUtil.getLongValue( ruleId, -1 );

        List result = null;

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 12 );

        Page pageInfo = null;

        Long count = null;

        if( rid.longValue() < 1 )
        {
            count = pickDao.queryPickWebTraceCountBySiteId( site.getSiteId() );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = pickDao.queryPickWebTrace( site.getSiteId(), Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageSize ) );
        }
        else
        {
            count = pickDao.queryPickWebTraceCountBySiteId( site.getSiteId(), rid );

            pageInfo = new Page( pageSize, count.intValue(), pageNum );

            result = pickDao.queryPickWebTrace( site.getSiteId(), rid, Long.valueOf( pageInfo
                .getFirstResult() ), Integer.valueOf( pageSize ) );
        }

        return new Object[] { result, pageInfo };
    }

    public void deletePickContentTask( List idList )
    {
        try
        {
            mysqlEngine.beginTransaction();

            long id = -1;

            PickContentTaskBean taskBean = null;

            for ( int i = 0; i < idList.size(); i++ )
            {
                if( idList.get( i ) instanceof String )
                {
                    id = StringUtil.getLongValue( ( String ) idList.get( i ), -1 );
                }
                else
                {
                    id = ( Long ) idList.get( i );
                }

                if( id < 0 )
                {
                    continue;
                }

                taskBean = pickDao.querySinglePickTaskBeanById( Long.valueOf( id ) );

                pickDao.deletePickTask( Long.valueOf( id ) );

                scheduleDao.deleteScheduleJobDetailByJobId( taskBean.getSelfJobId() );

                scheduleService.stopPickWebScheduleJob( taskBean.getSelfJobId() );
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

    }

    public void editPickContentTask( PickContentTask pickTask )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        try
        {
            mysqlEngine.beginTransaction();

            pickDao.update( pickTask );

            PickContentTaskBean taskBean = pickDao.querySinglePickTaskBeanById( pickTask
                .getPickTaskId() );

            // 移除并删除job
            scheduleService.stopPickWebScheduleJob( taskBean.getSelfJobId() );

            if( taskBean.getSelfJobId() != null && taskBean.getSelfJobId().longValue() > 0 )
            {
                if( taskBean.getPeriod().intValue() == 0 )
                {
                    scheduleDao.deleteScheduleJobDetailByJobId( taskBean.getSelfJobId() );
                    pickDao.updateTaskJobId( pickTask.getPickTaskId(), null );
                }
                else
                {
                    // 根据改变增加新的job
                    scheduleService.updatePickWebJob( taskBean );
                }
            }
            else
            {
                if( taskBean.getPeriod().intValue() != 0 )
                {
                    scheduleService.addPickWebContentScheduleJobDetail( taskBean.getPickTaskId(),
                        site.getSiteId(), taskBean.getPeriodType(), taskBean.getPeriod() );
                }
            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();

            BlockDao.clearCache();
        }
    }

    private String disposePrefixUrl( String urlInfo, String type )
    {
        String[] urlInfoUrlArray = StringUtil.split( urlInfo, "\r\n" );

        if( urlInfoUrlArray.length == 1 && urlInfo.startsWith( "http://" ) )
        {
            return urlInfo;
        }

        String end = "";

        if( "c".equals( type ) )
        {
            for ( int i = 0; i < urlInfoUrlArray.length; i++ )
            {
                if( urlInfoUrlArray[i].startsWith( "c=" ) )
                {
                    end = StringUtil.subString( urlInfoUrlArray[i], 2 );
                }

            }

        }
        else if( "p".equals( type ) )
        {
            for ( int i = 0; i < urlInfoUrlArray.length; i++ )
            {
                if( urlInfoUrlArray[i].startsWith( "p=" ) )
                {
                    end = StringUtil.subString( urlInfoUrlArray[i], 2 );
                }

            }
        }

        return end;
    }

    private String disposeUrlReg( String config )
    {
        config = StringUtil.replaceString( config, Constant.PICK.REG_W_FLAG, Constant.PICK.REG_W,
            false, false );

        config = StringUtil.replaceString( config, Constant.PICK.REG_D_FLAG, Constant.PICK.REG_D,
            false, false );

        config = StringUtil.replaceString( config, Constant.PICK.REG_C_FLAG, Constant.PICK.REG_C,
            false, false );

        config = StringUtil.replaceString( config, Constant.PICK.REG_NOT_D_FLAG,
            Constant.PICK.REG_NOT_D, false, false );

        config = StringUtil.replaceString( config, Constant.PICK.REG_ANY_FLAG,
            Constant.PICK.REG_ANY, false, false );

        String endRegexStr = null;
        // 去掉reg范围分割符号
        endRegexStr = StringUtil.replaceString( config, Constant.PICK.START_REG, "", false, false );

        endRegexStr = StringUtil.replaceString( endRegexStr, Constant.PICK.END_REG, "", false,
            false );

        // 替换reg次数分割符号
        endRegexStr = StringUtil.replaceString( endRegexStr, Constant.PICK.START_GREEDY,
            Constant.PICK.START_REG, false, false );

        endRegexStr = StringUtil.replaceString( endRegexStr, Constant.PICK.END_GREEDY,
            Constant.PICK.END_REG, false, false );

        // return StringUtil.replaceString( endRegexStr, "?", "\\?", false,
        // false );

        return SystemSafeCharUtil.escapeExprSpecialWord( endRegexStr );

    }

    /**
     * 采集获取内容系统定义参数,普通edit模式不更新系统参数
     * 
     * @param editMode
     * @param modelId
     * @param classBean
     * @param params
     * @return
     */
    private ContentMainInfo getSystemModelValueFromPickWebParam( PickContentRuleBean ruleBean,
        ContentClassBean classBean, Map params )
    {
        boolean editMode = false;

        Timestamp adt = null;

        if( params.get( "addDate" ) instanceof String )
        {
            String addDate = ( String ) params.get( "addDate" );

            String af = DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS;

            //af = "yyyy-MM-dd HH:mm";

            if( StringUtil.isStringNotNull( addDate ) && addDate.indexOf( ":" ) == -1 )
            {
                af = DateAndTimeUtil.DEAULT_FORMAT_YMD;
            }

            if( ruleBean != null && StringUtil.isStringNotNull( ruleBean.getTimeFormat() ) )
            {
                af = ruleBean.getTimeFormat();
            }

            adt = DateAndTimeUtil.getNotNullTimestamp( addDate, af );
        }
        else
        {
            adt = ( Timestamp ) params.get( "addDate" );
        }

        ContentMainInfo mainInfo = new ContentMainInfo();

        mainInfo.setAddTime( adt );

        // classId
        mainInfo.setClassId( classBean.getClassId() );

        // refCid

        // title
        mainInfo.setTitle( ( String ) params.get( "title" ) );

        // simpleTitle

        // shortTitle

        // titleStyle

        // simpleTitleStyle

        // summary
        mainInfo.setSummary( ( String ) params.get( "summary" ) );

        // 创建或修改者,为系统用户或会员或填入的名称,在采集中对应author
        String creator = ( String ) params.get( "author" );
        if( StringUtil.isStringNull( creator ) )
        {
            Auth sysAuth = SecuritySessionKeeper.getSecuritySession().getAuth();
            if( sysAuth == null )
            {
                mainInfo.setCreator( "匿名用户" );
            }
            else
            {
                mainInfo.setCreator( ( String ) sysAuth.getApellation() );
            }
        }
        else
        {
            mainInfo.setCreator( creator );
        }

        // 作者,版权所有人,对应source
        mainInfo.setAuthor( ( String ) params.get( "source" ) );

        // systemHandleTime 只要对数据更改,都必须改变这个值
        mainInfo.setSystemHandleTime( DateAndTimeUtil.getNotNullTimestamp( null,
            DateAndTimeUtil.DEAULT_FORMAT_NANO ).toString() );

        if( !editMode )
        {
            // produceType 根据栏目设定
            mainInfo.setProduceType( classBean.getContentProduceType() );

            // orderIdFlag
            mainInfo.setOrderIdFlag( Double.valueOf( 1 ) );

            // isSystemOrder
            mainInfo.setIsSystemOrder( Integer.valueOf( 1 ) );
        }

        // tagKey

        // keywords
        mainInfo.setKeywords( ( String ) params.get( "keywords" ) );

        // appearStartDateTime
        // 当为空设定当前时间,表示开始发布时间为立即发布,注意：为空时取时间，永远为服务器最新的时间为发布时间。若某一秒已被其他操作获取
        // 需要保持唯一性，即获取不同的新的一秒时间，要严格保持严格性
        String appearStartDateTime = "1000-01-01 00:00:00";
        Timestamp appearStartDateTS = null;
        if( StringUtil.isStringNull( appearStartDateTime ) )
        {
            if( !editMode )
            {
                // 增加模式下 若为空,则立即发布,并立即设置置时间记录序列ID,静态化则需要静态更新URL
                appearStartDateTS = DateAndTimeUtil.getTodayTimestampDayAndTime();

                mainInfo.setPubDateSysDT( contentService.getNextPublishOrderTrace() );
            }
            // 编辑模式下,不可能值为空,所以不会出现在空发布时间值处理逻辑中
        }
        else
        {
            // 填入时间则为将来发步,获取填入的时间并检查,若比当前时间小，则为当前时间,且立即发布,若为合法将来发布时间,则只记录时间,不设置时间记录序列ID,等待发布任务改动序列
            appearStartDateTS = DateAndTimeUtil.getNotNullTimestamp( appearStartDateTime,
                DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );

            // 添加时,若所给时间小于等于现在时间,立即发布,并设定发布排序ID
            // 编辑时,若小于等于现在时间,时间为原来时间,不做改动
            Timestamp currentTime = DateAndTimeUtil.getTodayTimestampDayAndTime();
            if( appearStartDateTS.compareTo( currentTime ) < 1 )
            {
                if( !editMode )
                {
                    // 增加时,立即设置为现在时间,并设置发布排序ID,静态化则需要静态更新URL

                    Calendar cal = Calendar.getInstance();

                    cal.set( Calendar.MINUTE, cal.get( Calendar.MINUTE ) + 5 );

                    appearStartDateTS = new Timestamp( cal.getTimeInMillis() );

                    // mainInfo.setPubDateSysDT(
                    // contentService.getNextPublishOrderTrace() );
                }
                else
                {
                    Integer prevCensorState = Integer.valueOf( ( String ) params
                        .get( "censorState" ) );
                    // 下线后的状态要处理
                    if( Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( prevCensorState ) )
                    {
                        // 若已经发布,设定为原时间,原排序ID,静态化则需要静态更新URL
                        appearStartDateTS = DateAndTimeUtil.getNotNullTimestamp( ( String ) params
                            .get( "cmsSysOldPublishDateTime" ),
                            DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );

                        mainInfo.setPubDateSysDT( Long.valueOf( ( String ) params
                            .get( "cmsSysOldPublishDT" ) ) );
                    }
                    else if( Constant.WORKFLOW.CENSOR_STATUS_WAIT_PUBLISH.equals( prevCensorState ) )
                    {
                        // 若为等待发布状态的将来时间变为现在时间,则需要立即发布,并更新发布排序ID,静态化则需要静态更新URL
                        appearStartDateTS = currentTime;

                        mainInfo.setPubDateSysDT( contentService.getNextPublishOrderTrace() );
                    }
                }
            }
            else
            {
                // 大于现在时间需要清除发布排序ID
                mainInfo.setPubDateSysDT( null );
            }
            // 大于现在时间的待发布状态无论是增加还是更新操作直接设定发布时间,并清除排序ID,等待发布任务改动发布排序ID
        }

        mainInfo.setAppearStartDateTime( appearStartDateTS );

        // appearEndDateTime
        // 当为空设定最大的时间,表示结束发布时间为永远
        String appearEndDateTime = "9999-12-31 23:59:59";
        Timestamp appearEndDateTS = null;
        if( StringUtil.isStringNull( appearEndDateTime ) )
        {
            appearEndDateTS = Constant.CONTENT.MAX_DATE;
        }
        else
        {
            appearEndDateTS = DateAndTimeUtil.getNotNullTimestamp( appearEndDateTime,
                DateAndTimeUtil.DEAULT_FORMAT_YMD_HMS );
        }

        mainInfo.setAppearEndDateTime( appearEndDateTS );

        // censorState
        mainInfo.setCensorState( WorkflowService.pendingCensorStateByStartAndEndPublishDate(
            appearStartDateTS, appearEndDateTS, Constant.WORKFLOW.CENSOR_STATUS_SUCCESS ) );

        // topFlag
        mainInfo.setTopFlag( Integer.valueOf( 0 ) );

        // typeFlag

        // allowCommend
        mainInfo.setAllowCommend( Integer.valueOf( 1 ) );

        // especialTemplateUrl

        // homeImage and flag

        // channelImage and flag

        // classImage and flag

        // contentImage and flag

        // modelID
        mainInfo.setModelId( classBean.getContentType() );

        if( editMode )
        {
            mainInfo.setContentId( Long.valueOf( StringUtil.getLongValue( ( String ) params
                .get( "contentId" ), -1 ) ) );
        }
        else
        {
            // siteId

            mainInfo.setSiteId( ( Long ) params.get( "jtopcms_sys_siteId" ) );
        }

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( classBean.getSiteFlag() );

        // 点击数 和 支持数
        if( !editMode && site.getDefClickCount().intValue() > 0 )
        {
            mainInfo.setClickCount( Long.valueOf( new Random().nextInt( site.getDefClickCount()
                .intValue() ) + 1 ) );

            mainInfo.setSupportCount( Long.valueOf( new Random().nextInt( site.getDefClickCount()
                .intValue() ) + 1 ) );
        }

        return mainInfo;

    }

    public Object getPickModelExt( String eprId )
    {
        long eprIdVar = Long.valueOf( StringUtil.getLongValue( eprId, -1 ) );

        if( eprIdVar > 0 )
        {
            return pickDao.querySinglePickModelExt( eprIdVar );
        }
        else if( eprIdVar == -9999 )
        {
            return pickDao.queryPickModelExt();
        }

        return null;
    }

    public Object getPickModelExtField( String eprId, String fieldSign )
    {
        long eprIdVar = StringUtil.getLongValue( eprId, -1 );

        return pickDao.queryPickModelExtField( eprIdVar, fieldSign );
    }

    public static Whitelist basicWithImages()
    {
        return Whitelist.none().addTags( "img" ).addAttributes( "img", "align", "alt", "height",
            "src", "title", "width" );
    }

    /**
     * 下载附件到本地
     * 
     * @param text
     */
    @SuppressWarnings( "unchecked" )
    public String downloadFile( String fullUrl, String prefixUrl, String text, SiteGroupBean site,
        Long classId, List dfList )
    {
        if( StringUtil.isStringNull( text ) )
        {
            return null;
        }

        if( site == null )
        {
            return null;
        }

        List resList = new ArrayList();

        SystemRuntimeConfig config = SystemConfiguration.getInstance().getSystemConfig();

        String webBase = site.getSiteFilePrefixUrl();

        // String systemBase = config.getSystemRealPath();

        String uploadImageBasePath = config.getSystemRealPath() + site.getSiteRoot()
            + File.separator + site.getFileRoot();

        String date = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        Map imgHandlerMap = new HashMap();

        Map newImgHandlerMap = new HashMap();

        String targetContent = text;

        /*
         * Document doc = Jsoup.parse( targetContent );
         * 
         * Elements es = doc.getElementsByTag( "img" );
         * 
         * Iterator iter = es.iterator();
         * 
         * Element el = null;
         * 
         * while ( iter.hasNext() ) { el = ( Element ) iter.next(); }
         */

        int startFlag = targetContent.indexOf( "<a", 0 );

        Document doc = Jsoup.parse( text );

        Elements elements = doc.select( "a[href]" );

        for ( Element element : elements )
        {
            int end = targetContent.indexOf( ">", startFlag ) + 1;

            String targetSrc = element.attr( "href" );

            // 如果文件已经是本地的，则不需要做下载处理，后期若出现在文件服务器中的地址也不需要处理

            if( targetSrc == null || targetSrc.indexOf( webBase ) != -1 )
            {

                continue;
            }

            String downTrueUrl = targetSrc;

            if( StringUtil.isStringNotNull( prefixUrl ) && targetSrc != null
                && !targetSrc.toLowerCase().startsWith( "http:" ) )
            {
                // if( prefixUrl.endsWith( "/" ) && targetSrc.startsWith( "/" )
                // )
                // {
                // downTrueUrl = StringUtil.subString( prefixUrl, 0,
                // prefixUrl.length() - 1 )
                // + targetSrc;
                // }
                // else
                // {
                // downTrueUrl = prefixUrl + targetSrc;
                // }

                URI base;
                try
                {
                    base = new URI( fullUrl );

                    URI abs = base.resolve( targetSrc );

                    System.out.println( abs.toURL().toString() );

                    downTrueUrl = abs.toURL().toString();
                }
                catch ( Exception e )
                {
                    downTrueUrl = prefixUrl + targetSrc;

                    e.printStackTrace();
                }
            }

            Object[] df = HtmlUtil.downloadFileByUrl( downTrueUrl, uploadImageBasePath
                + File.separator + date + File.separator, false );

            File endFileFile = ( File ) df[0];

            if( endFileFile != null && endFileFile.exists() )
            {

                String successDownloadImgName = endFileFile.getName();

                if( StringUtil.isStringNotNull( successDownloadImgName ) )
                {

                    imgHandlerMap.put( SystemSafeCharUtil.filterHTML( targetSrc ),
                        successDownloadImgName );

                    // 加入res
                    SiteResource resInfo = new SiteResource();

                    resInfo.setClassId( classId );
                    resInfo.setSiteId( site.getSiteId() );

                    if( StringUtil.isStringNotNull( ( String ) df[1] ) )
                    {
                        resInfo.setResName( ( String ) df[1] );
                    }
                    else
                    {
                        resInfo.setResName( StringUtil.subString( successDownloadImgName, 0,
                            successDownloadImgName.lastIndexOf( "." ) ) );
                    }

                    String fileType = StringUtil.subString( successDownloadImgName,
                        successDownloadImgName.lastIndexOf( "." ) + 1,
                        successDownloadImgName.length() ).toLowerCase();

                    fileType = StringUtil.isStringNull( fileType ) ? ( String ) df[2] : fileType;

                    resInfo.setFileType( fileType );

                    resInfo.setResName( StringUtil.replaceString( resInfo.getResName(), "."
                        + fileType, "" ) );

                    resInfo.setResType( Constant.RESOURCE.ANY_RES_TYPE );

                    resInfo.setModifyTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );
                    resInfo.setResSize( Long.valueOf( endFileFile.length() ) );
                    resInfo.setResSource( StringUtil.replaceString( endFileFile.getPath().replace(
                        uploadImageBasePath, "" ), File.separator, "/" ) );

                    UpdateState us = ResourcesService.getInstance()
                        .addSiteResourceAndUploadTraceSuccessStatus( resInfo );

                    if( dfList != null )
                    {
                        dfList.add( Long.valueOf( us.getKey() ) );

                        newImgHandlerMap.put( successDownloadImgName, Long.valueOf( us.getKey() ) );

                    }

                    resList.add( resInfo );

                    targetContent = Long.valueOf( us.getKey() ).toString();

                    break;

                }

            }
            else
            {
                log.warn( "无法下载文件，目标地址：" + targetSrc );
            }

            startFlag = targetContent.indexOf( "<a", end );
        }

        // 进行数据分发
        SiteGroupService.getInstance().transferUpdateDataToServer( resList );

        return targetContent;

    }

    /**
     * 下载图片到本地
     * 
     * @param text
     */
    @SuppressWarnings( "unchecked" )
    public String downloadImg( String fullUrl, String prefixUrl, String text, SiteGroupBean site,
        Long classId, List dfList )
    {
        if( StringUtil.isStringNull( text ) )
        {
            return null;
        }

        if( site == null )
        {
            return null;
        }

        List resList = new ArrayList();

        SystemRuntimeConfig config = SystemConfiguration.getInstance().getSystemConfig();

        String webBase = site.getSiteImagePrefixUrl();

        // String systemBase = config.getSystemRealPath();

        String uploadImageBasePath = config.getSystemRealPath() + site.getSiteRoot()
            + File.separator + site.getImageRoot();

        String date = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        Map imgHandlerMap = new HashMap();

        Map newImgHandlerMap = new HashMap();

        String targetContent = text;

        /*
         * Document doc = Jsoup.parse( targetContent );
         * 
         * Elements es = doc.getElementsByTag( "img" );
         * 
         * Iterator iter = es.iterator();
         * 
         * Element el = null;
         * 
         * while ( iter.hasNext() ) { el = ( Element ) iter.next(); }
         */

        int startFlag = targetContent.indexOf( "<img", 0 );

        Document doc = Jsoup.parse( text );

        Elements elements = doc.select( "img[src]" );

        for ( Element element : elements )
        {
            int end = targetContent.indexOf( ">", startFlag ) + 1;

            String targetSrc = element.attr( "src" );

            // 如果文件已经是本地的，则不需要做下载处理，后期若出现在文件服务器中的地址也不需要处理

            if( targetSrc == null || targetSrc.indexOf( webBase ) != -1 )
            {

                continue;
            }

            String downTrueUrl = targetSrc;

            if( StringUtil.isStringNotNull( prefixUrl ) && targetSrc != null
                && !targetSrc.toLowerCase().startsWith( "http:" ) )
            {
                // if( prefixUrl.endsWith( "/" ) && targetSrc.startsWith( "/" )
                // )
                // {
                // downTrueUrl = StringUtil.subString( prefixUrl, 0,
                // prefixUrl.length() - 1 )
                // + targetSrc;
                // }
                // else
                // {
                // downTrueUrl = prefixUrl + targetSrc;
                // }

                URI base;
                try
                {
                    base = new URI( fullUrl );

                    URI abs = base.resolve( targetSrc );

                    System.out.println( abs.toURL().toString() );

                    downTrueUrl = abs.toURL().toString();
                }
                catch ( Exception e )
                {
                    downTrueUrl = prefixUrl + targetSrc;

                    e.printStackTrace();
                }
            }

            Object[] df = HtmlUtil.downloadFileByUrl( downTrueUrl, uploadImageBasePath
                + File.separator + date + File.separator, false );

            File endFileFile = ( File ) df[0];

            if( endFileFile != null && endFileFile.exists() )
            {

                String successDownloadImgName = endFileFile.getName();

                if( StringUtil.isStringNotNull( successDownloadImgName ) )
                {

                    imgHandlerMap.put( SystemSafeCharUtil.filterHTML( targetSrc ),
                        successDownloadImgName );

                    // 加入res
                    SiteResource resInfo = new SiteResource();

                    resInfo.setClassId( classId );
                    resInfo.setSiteId( site.getSiteId() );

                    if( StringUtil.isStringNotNull( ( String ) df[1] ) )
                    {
                        resInfo.setResName( ( String ) df[1] );
                    }
                    else
                    {
                        resInfo.setResName( StringUtil.subString( endFileFile.getPath(),
                            endFileFile.getPath().lastIndexOf( File.separator ) + 1, endFileFile
                                .getPath().lastIndexOf( "." ) ) );
                    }

                    String fileType = StringUtil.subString( successDownloadImgName,
                        successDownloadImgName.lastIndexOf( "." ) + 1,
                        successDownloadImgName.length() ).toLowerCase();

                    fileType = StringUtil.isStringNull( fileType ) ? ( String ) df[2] : fileType;

                    resInfo.setFileType( fileType );

                    resInfo.setResName( StringUtil.replaceString( resInfo.getResName(), "."
                        + fileType, "" ) );

                    resInfo.setResType( Constant.RESOURCE.IMAGE_RES_TYPE );

                    // 获取图片原始的宽高
                    Object[] imgOffset = ImageUtil.getImageHeightAndWidth( endFileFile.getPath() );

                    Integer width = ( ( Integer ) imgOffset[0] );
                    Integer height = ( ( Integer ) imgOffset[1] );

                    // 缩略图
                    String resize = uploadImageBasePath + File.separator + date + File.separator
                        + "imgResize" + endFileFile.getName();

                    int maxResize = 140;

                    if( width.intValue() >= height.intValue() )
                    {
                        ImageUtil.resizeImage( maxResize, -1, endFileFile.getPath(), resize,
                            Constant.RESOURCE.IMAGE_RESIZE_Q_MID );
                    }
                    else
                    {
                        ImageUtil.resizeImage( -1, maxResize, endFileFile.getPath(), resize,
                            Constant.RESOURCE.IMAGE_RESIZE_Q_MID );
                    }

                    resInfo.setHeight( height );
                    resInfo.setWidth( width );

                    resInfo.setModifyTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );
                    resInfo.setResSize( Long.valueOf( endFileFile.length() ) );
                    resInfo.setResSource( date + Constant.CONTENT.URL_SEP + endFileFile.getName() );

                    resInfo.setResType( Constant.RESOURCE.IMAGE_RES_TYPE );

                    UpdateState us = ResourcesService.getInstance()
                        .addSiteResourceAndUploadTraceSuccessStatus( resInfo );

                    if( dfList != null )
                    {
                        dfList.add( Long.valueOf( us.getKey() ) );

                        newImgHandlerMap.put( successDownloadImgName, Long.valueOf( us.getKey() ) );

                    }

                    resList.add( resInfo );

                    targetContent = Long.valueOf( us.getKey() ).toString();

                    break;

                }

            }
            else
            {
                log.warn( "无法下载文件，目标地址：" + targetSrc );
            }

            startFlag = targetContent.indexOf( "<a", end );
        }

        // 进行数据分发
        SiteGroupService.getInstance().transferUpdateDataToServer( resList );

        return targetContent;

    }

}
