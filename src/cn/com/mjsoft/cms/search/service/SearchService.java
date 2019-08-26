package cn.com.mjsoft.cms.search.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.cluster.dao.ClusterDao;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.bean.ContentMainInfoBean;
import cn.com.mjsoft.cms.content.dao.ContentDao;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.publish.bean.PublishRuleBean;
import cn.com.mjsoft.cms.publish.bean.PublishStatusBean;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.search.bean.SearchIndexContentStateBean;
import cn.com.mjsoft.cms.search.dao.SearchDao;
import cn.com.mjsoft.cms.search.dao.vo.SearchIndexContentState;
import cn.com.mjsoft.cms.search.html.ClientSearchContentListTag;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.cache.jsr14.ReadWriteLockHashMap;
import cn.com.mjsoft.framework.config.SystemRuntimeConfig;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.LuceneUtil;
import cn.com.mjsoft.framework.util.MathUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;

public class SearchService
{
    private static final Logger log = Logger.getLogger( SearchService.class );

    private static final Integer disposeLimitCount = Integer.valueOf( 500 );

    /**
     * 所有搜索文件不参与集群活动
     */

    /**
     * 各索引搜索对象map
     */
    private Map searcherMap = new ReadWriteLockHashMap();

    /**
     * 各索引维护对象map
     */
    private Map writerMap = new ReadWriteLockHashMap();

    /**
     * 已过时读取对象map
     */
    private Map outdatedReaderMap = new HashMap();

    private static Double RAM_BUF = Double.valueOf( 32 );

    private static Integer MERGE_FACTOR = Integer.valueOf( 20 );

    private static Integer MAX_MERGE_DOCS = Integer.valueOf( 500 );

    public static DecimalFormat df2 = new DecimalFormat( "##0.000" );

    private static SearchService service = null;

    public PersistenceEngine mysqlEngine = new PersistenceEngine( new MySqlDataSource() );

    private static ChannelService channelService = ChannelService.getInstance();

    private static ContentService contentService = ContentService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    private SearchDao searchDao;

    private ContentDao contentDao;

    private ClusterDao clusterDao;

    private SearchService()
    {
        searchDao = new SearchDao( mysqlEngine );

        contentDao = new ContentDao( this.mysqlEngine );

        clusterDao = new ClusterDao( this.mysqlEngine );
    }

    private static synchronized void init()
    {
        if( null == service )
        {
            service = new SearchService();
        }
    }

    public static SearchService getInstance()
    {
        if( null == service )
        {
            init();
        }
        return service;
    }

    public void addIndexContentState( SearchIndexContentState vo )
    {

        List csList = clusterDao.queryAllClusterServer();

        Map cluServer = null;

        Integer isActive = null;

        Long serverId = null;

        for ( int i = 0; i < csList.size(); i++ )
        {
            cluServer = ( Map ) csList.get( i );

            serverId = ( Long ) cluServer.get( "serverId" );

            isActive = ( Integer ) cluServer.get( "isActive" );

            if( Constant.COMMON.OFF.equals( isActive ) )
            {
                continue;
            }

            vo.setClusterId( serverId );

            searchDao.saveIndexState( vo );

        }

    }

    public List retrieveIndexContentStateByFlag( Long cId, Long siteId, Long startId, Integer limit )
    {
        return searchDao.queryIndexContentStateByFlag( cId, siteId, startId, limit );
    }

    public void deleteIndexContentStateByLastIdAndClusterId( Long cId, Long siteId, Long startId,
        Long lastId )
    {
        searchDao.deleteIndexContentStateByLastId( cId, siteId, startId, lastId );
    }

    public void deleteIndexContentStateBySiteId( Long siteId )
    {
        searchDao.deleteIndexContentStateBySiteId( siteId );
    }

    public void disposeSearchQueryKeyCount( SiteGroupBean site )
    {
        Map keyMap = ( Map ) ClientSearchContentListTag.searchKeyMap.get( site.getSiteId() );

        if( keyMap == null )
        {
            return;
        }

        Map tempMap = new HashMap( keyMap );
        // 清空
        if( keyMap != null )
        {
            keyMap.clear();

            ClientSearchContentListTag.searchKeyMap.put( site.getSiteId(), keyMap );
        }

        boolean change = false;

        try
        {
            mysqlEngine.beginTransaction();

            Entry entry = null;

            String key = null;

            Integer count = null;

            Iterator iter = tempMap.entrySet().iterator();

            while ( iter.hasNext() )
            {
                entry = ( Entry ) iter.next();

                key = SystemSafeCharUtil.filterHTMLNotApos( ( String ) entry.getKey() );

                count = ( Integer ) entry.getValue();

                if( searchDao.querySearchKeyCount( key ).longValue() > 0 )
                {
                    searchDao.updateSearchKeyCount( key, count );
                }
                else
                {
                    searchDao.saveNewSearchKeyCount( key, count, site.getSiteId() );
                }

                change = true;

            }

            mysqlEngine.commit();
        }
        finally
        {
            mysqlEngine.endTransaction();
        }

        if( change )
        {
            SearchDao.clear();
        }
    }

    /**
     * 分析文本,将切分的词汇按照出现频率由大到小排序
     * 
     * @param text
     * @return
     */
    public List disposeTextKeyword( String text )
    {
        if( StringUtil.isStringNull( text ) )
        {
            return new ArrayList();
        }

        List keywordList = LuceneUtil.wordAnalysis( text, " ", true );

        String tempKey = null;
        Map keyInfoMap = new TreeMap();
        Integer count = null;

        for ( int i = 0; i < keywordList.size(); i++ )
        {
            tempKey = ( String ) keywordList.get( i );

            if( tempKey.length() < 2
                || StringUtil.getDoubleValue( tempKey, -9999999.999999 ) != -9999999.999999 )

            {
                continue;
            }

            count = ( Integer ) keyInfoMap.get( tempKey );

            if( count == null )
            {
                keyInfoMap.put( tempKey, Integer.valueOf( 1 ) );
            }
            else
            {
                keyInfoMap.put( tempKey, Integer.valueOf( count.intValue() + 1 ) );
            }

        }

        ArrayList sortList = new ArrayList( keyInfoMap.entrySet() );
        Collections.sort( sortList, new Comparator()
        {
            public int compare( Object arg0, Object arg1 )
            {
                Integer count0 = ( Integer ) ( ( Entry ) arg0 ).getValue();
                Integer count1 = ( Integer ) ( ( Entry ) arg1 ).getValue();
                return count1.intValue() - count0.intValue();
            }

        } );

        // 重新整理文本顺序,只有超过1个的重复词汇才能按照出现次数排列,出现一个的词汇必须按照文本流解吸顺序排列
        List resultKeywordList = new ArrayList();

        Entry entry = null;

        // 将重复出现的词汇放在前面
        for ( int i = 0; i < sortList.size(); i++ )
        {
            entry = ( Entry ) sortList.get( i );

            if( ( ( Integer ) entry.getValue() ).intValue() > 1 )
            {
                resultKeywordList.add( entry.getKey() );
            }
        }

        // 在后面加入出现一次的词汇
        String onlyOneKey = null;
        Integer onlyOneCount = null;
        for ( int i = 0; i < keywordList.size(); i++ )
        {
            onlyOneKey = ( String ) keywordList.get( i );

            onlyOneCount = ( Integer ) keyInfoMap.get( onlyOneKey );
            // 需要判断null,因为有些词汇不需要被去掉
            if( onlyOneCount == null || onlyOneCount.intValue() > 1 )
            {
                continue;
            }

            resultKeywordList.add( onlyOneKey );
        }

        return resultKeywordList;
    }

    public void regIndexSearcher( String key, String rootPath )
    {

    }

    /**
     * 获取对应index目录的writer,若不存在,则建立新的writer
     * 
     * @param key
     * @param rootFullPath
     * @param siteId
     * @return
     */
    public IndexWriter getIndexWriter( String key, String rootFullPath )
    {
        /**
         * 保证一个索引(唯一路径)只有一个writer,目前情况下无多线程访问一个writer的情况,所以无需要最高级同步
         */
        // if( writerMap.containsKey( key ) )
        // {
        // return ( IndexWriter ) writerMap.get( key );
        // }
        IndexWriter writer = LuceneUtil.createWriter( rootFullPath, RAM_BUF.doubleValue(),
            MERGE_FACTOR.intValue(), MAX_MERGE_DOCS.intValue() );

        // writerMap.put( key, writer );
        return writer;
    }

    /**
     * 获取对应index目录的writer,若不存在,则建立新的writer
     * 
     * @param key
     * @param rootFullPath
     * @param siteId
     * @return
     */
    public IndexWriter getIndexWriter( String key, Directory dir )
    {
        /**
         * 保证一个索引(唯一路径)只有一个writer,目前情况下无多线程访问一个writer的情况,所以无需要最高级同步
         */

        IndexWriter writer = LuceneUtil.createWriter( dir, RAM_BUF.doubleValue(), MERGE_FACTOR
            .intValue(), MAX_MERGE_DOCS.intValue() );

        return writer;
    }

    /**
     * 提交并合并
     * 
     * @param writer
     */
    public void commit( IndexWriter writer )
    {
        LuceneUtil.commit( writer );
    }

    /**
     * 获取对应index目录的search,若不存在或索引有更新,则建立新的search
     * 
     * @param key
     * @param rootFullPath
     * @param siteId
     * @return
     */
    public IndexSearcher getIndexSearcher( String key, String rootFullPath )
    {
        IndexSearcher searcher = null;

        /**
         * 多线程访问环境下进行最高级同步,保证一个索引(唯一路径)只有一个searcher,
         */
        synchronized ( searcherMap )
        {
            searcher = ( IndexSearcher ) searcherMap.get( key );

            if( searcher == null )
            {
                searcher = LuceneUtil.createSearcher( LuceneUtil.createReader( rootFullPath ) );
                searcherMap.put( key, searcher );
            }

            return searcher;
        }
    }

    public void cleanOutdatedReaderMap( String indexKey )
    {
        IndexSearcher oldSearcher = ( IndexSearcher ) outdatedReaderMap.get( indexKey );

        if( oldSearcher == null )
        {
            log.info( "[SearchService] cleanOutdatedReaderMap ： no old data!" );
            return;
        }

        IndexReader oldReader = oldSearcher.getIndexReader();

        try
        {

            if( oldReader != null )
            {
                oldReader.close();
                log.info( "[SearchService] cleanOutdatedReaderMap : close oldReader" );
            }

            if( oldSearcher != null )
            {

                oldSearcher.close();
                log.info( "[SearchService] cleanOutdatedReaderMap : close oldSearcher" );
            }

            outdatedReaderMap.remove( indexKey );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

        // ClusterService.exeClusterMasterCMD(
        // "cluster/cleanOutReader.do?indexKey=" + indexKey,
        // Constant.COMMON.POST );
    }

    public void cleanOutdatedReaderMapCluster( String indexKey )
    {
        IndexSearcher oldSearcher = ( IndexSearcher ) outdatedReaderMap.get( indexKey );

        if( oldSearcher == null )
        {
            log.info( "[SearchService] cleanOutdatedReaderMap ： no old data!" );
            return;
        }

        IndexReader oldReader = oldSearcher.getIndexReader();

        try
        {

            if( oldReader != null )
            {
                oldReader.close();
                log.info( "[SearchService] cleanOutdatedReaderMap : close oldReader" );
            }

            if( oldSearcher != null )
            {

                oldSearcher.close();
                log.info( "[SearchService] cleanOutdatedReaderMap : close oldSearcher" );
            }

            outdatedReaderMap.remove( indexKey );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

    }

    public void disposeSearchIndex( String rootIndexPath, int DISPOSE_DEGREE,
        Integer DISPOSE_LIMIT, SiteGroupBean site, String indexKey )
    {

        Directory dir = null;
        try
        {
            dir = FSDirectory.open( new File( rootIndexPath ) );

            if( dir != null && !IndexWriter.isLocked( dir ) )
            {
                // 获取目标content数据并进行索引

                Long prevStartId = null;

                Long prevLastId = null;

                Long startId = Long.valueOf( 0 );

                int count = 0;

                while ( count < DISPOSE_DEGREE )
                {
                    List targetNeedIndexData = retrieveIndexContentStateByFlag( JtRuntime.cmsServer
                        .getServerId(), site.getSiteId(), startId, DISPOSE_LIMIT );

                    if( targetNeedIndexData.isEmpty() )
                    {
                        // 没有数据立即停止
                        break;
                    }

                    prevStartId = ( ( SearchIndexContentStateBean ) targetNeedIndexData.get( 0 ) )
                        .getIndexStateId();

                    prevLastId = ( ( SearchIndexContentStateBean ) targetNeedIndexData
                        .get( targetNeedIndexData.size() - 1 ) ).getIndexStateId();

                    startId = prevLastId;

                    // 处理内容索引到index,并进行分析
                    transferContentStateListAndAddToIndex( getIndexWriter( indexKey, dir ),
                        targetNeedIndexData );

                    // 统一删除已处理过的数据，按照集群节点
                    deleteIndexContentStateByLastIdAndClusterId( JtRuntime.cmsServer.getServerId(),
                        site.getSiteId(), prevStartId, prevLastId );

                    count++;
                }

                // 立即重新构造加入新索引
                IndexSearcher searcher = ( IndexSearcher ) getSearchMap().get( indexKey );

                if( searcher != null )
                {
                    Object[] changeState = LuceneUtil.reopenReaderAndSearcher( searcher );

                    searcher = ( IndexSearcher ) changeState[0];

                    getSearchMap().put( indexKey, searcher );

                    if( changeState[1] != null )
                    {
                        // 若过时数据位置不为null,说明已发生改变,需要放入容器等待下次job清除
                        getSearchOutdatedReaderMap().put( indexKey,
                            ( IndexSearcher ) changeState[1] );
                    }
                }
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    public void deleteSearchIndexMetadataByClassId( List classIdList, SiteGroupBean site )
    {
        if( classIdList == null )
        {
            return;
        }

        String rootIndexPath = getIndexRootFullPathAndCheckDir( site );

        String indexKey = buildKey( rootIndexPath, site.getSiteId() );

        IndexWriter writer = null;
        try
        {
            writer = getIndexWriter( indexKey, rootIndexPath );

            long classId = -1;

            for ( int i = 0; i < classIdList.size(); i++ )
            {
                classId = StringUtil.getLongValue( ( String ) classIdList.get( i ), -1 );

                if( classId > 0 )
                {
                    LuceneUtil.deleteIndexDocument( writer, new Term( "classId", Long.valueOf(
                        classId ).toString() ) );
                }
            }
        }
        finally
        {
            LuceneUtil.close( writer );
        }
    }

    public void deleteSearchIndexMetadataByModelId( List modelIdList, SiteGroupBean site )
    {
        if( modelIdList == null )
        {
            return;
        }

        String rootIndexPath = getIndexRootFullPathAndCheckDir( site );

        String indexKey = buildKey( rootIndexPath, site.getSiteId() );

        IndexWriter writer = null;
        try
        {
            writer = getIndexWriter( indexKey, rootIndexPath );

            long modelId = -1;

            for ( int i = 0; i < modelIdList.size(); i++ )
            {
                modelId = StringUtil.getLongValue( ( String ) modelIdList.get( i ), -1 );

                if( modelId > 0 )
                {
                    LuceneUtil.deleteIndexDocument( writer, new Term( "modelId", Long.valueOf(
                        modelId ).toString() ) );
                }
            }
        }
        finally
        {
            LuceneUtil.close( writer );
        }
    }

    public void deleteSearchIndexMetadataBySite( SiteGroupBean site )
    {
        if( site == null )
        {
            return;
        }

        String rootIndexPath = getIndexRootFullPathAndCheckDir( site );

        String indexKey = buildKey( rootIndexPath, site.getSiteId() );

        IndexWriter writer = null;
        try
        {
            writer = getIndexWriter( indexKey, rootIndexPath );

            LuceneUtil.deleteIndexDocument( writer,
                new Term( "siteId", site.getSiteId().toString() ) );
        }
        finally
        {
            LuceneUtil.close( writer );
        }

    }

    /**
     * 
     * @param writer
     * @param targetNeedIndexData
     */
    public void transferContentStateListAndAddToIndex( IndexWriter writer, List targetNeedIndexData )
    {
        if( targetNeedIndexData == null )
        {
            return;
        }

        try
        {
            // 添加index索引
            SearchIndexContentStateBean sctb = null;
            for ( int i = 0; i < targetNeedIndexData.size(); i++ )
            {
                sctb = ( SearchIndexContentStateBean ) targetNeedIndexData.get( i );

                if( Constant.JOB.SEARCH_INDEX_ADD.equals( sctb.getEventFlag() )
                    || Constant.JOB.SEARCH_INDEX_EDIT.equals( sctb.getEventFlag() ) )
                {
                    // 添加或更新索引
                    LuceneUtil.addOrUpdateIndexDocument( writer,
                        transferBeanToIndexDocument( sctb ), sctb.getContentId().toString() );
                }
                else if( Constant.JOB.SEARCH_INDEX_DEL.equals( sctb.getEventFlag() ) )
                {
                    // 删除指定contentId的索引
                    LuceneUtil.deleteIndexDocument( writer, new Term( "contentId", sctb
                        .getContentId().toString() ) );
                }
            }
        }
        finally
        {
            LuceneUtil.close( writer );
        }
    }

    /**
     * 搜索
     * 
     * @param searcher
     * @param keyword
     * @param fieldArray
     * @param colseClassId
     * @param pageStartDocId
     * @param limitSize
     * @return
     */
    public static Object[] search( IndexSearcher searcher, String keyword, String[] fieldArray,
        String[] classIdArray, Long[] colseClassId, Long closeContentId, String[] modelIdArray,
        String[] dateArray, Integer pageStartDocId, int limitSize, boolean manageMode )
    {
        if( StringUtil.isStringNull( keyword ) )
        {
            return null;
        }

        int totalHits = 0;
        TopDocs result = null;
        BooleanQuery queryAll = new BooleanQuery();
        long searchTime = 0;
        double ms = 0;

        try
        {
            long l1 = System.nanoTime();

            List splitKey = LuceneUtil.wordAnalysis( keyword, " ", true );

            String key = null;

            for ( int i = 0; i < splitKey.size(); i++ )
            {
                key = ( String ) splitKey.get( i );
                BooleanQuery booleanQuery = new BooleanQuery();
                for ( int j = 0; j < fieldArray.length; j++ )
                {
                    booleanQuery.add( new FuzzyQuery( new Term( fieldArray[j], key ) ),
                        BooleanClause.Occur.SHOULD );
                }

                queryAll.add( booleanQuery, BooleanClause.Occur.MUST );
            }

            if( !manageMode )
            {
                // 审核状态限制,CMS系统强制只有审核通过的内容才可被搜索
                queryAll.add( new TermQuery( new Term( "censor",
                    Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.toString() ) ),
                    BooleanClause.Occur.MUST );
            }

            // 模型限制,只搜索指定模型的内容

            if( modelIdArray != null )
            {
                for ( int i = 0; i < modelIdArray.length; i++ )
                {
                    queryAll.add(
                        new TermQuery( new Term( "modelId", modelIdArray[i].toString() ) ),
                        BooleanClause.Occur.SHOULD );
                }
            }

            // 指定搜索栏目限制
            if( classIdArray != null )
            {
                for ( int i = 0; i < classIdArray.length; i++ )
                {                 
                    queryAll.add(
                        new TermQuery( new Term( "classId", classIdArray[i].toString() ) ),
                        BooleanClause.Occur.SHOULD );
                }
            }

            // 关闭搜索栏目限制
            if( colseClassId != null )
            {

                for ( int i = 0; i < colseClassId.length; i++ )
                {
                    // 禁止查询条件
                    queryAll.add(
                        new TermQuery( new Term( "classId", colseClassId[i].toString() ) ),
                        BooleanClause.Occur.MUST_NOT );
                }
            }

            // 回避搜索内容ID限制
            if( closeContentId != null )
            {
                if( closeContentId.longValue() > 0 )
                {
                    // 禁止查询条件
                    queryAll.add(
                        new TermQuery( new Term( "contentId", closeContentId.toString() ) ),
                        BooleanClause.Occur.MUST_NOT );
                }
            }

            Sort sort = new Sort();

            boolean sortMode = false;

            // 时间查询
            if( dateArray.length > 0 && StringUtil.isStringNotNull( dateArray[0] )
                && StringUtil.isStringNotNull( dateArray[1] ) )
            {
                queryAll.add(
                    new TermRangeQuery( "addDate", dateArray[0], dateArray[1], true, true ),
                    BooleanClause.Occur.MUST );
            }

            // 时间排序

            if( dateArray.length > 0 && StringUtil.isStringNotNull( dateArray[2] ) )
            {
                sortMode = true;

                SortField dateSort = new SortField( "addDate", SortField.STRING, "ad-down"
                    .equals( dateArray[2] ) ? true : false );

                sort.setSort( new SortField[] { dateSort } );

            }

            log.info( "[query]" + queryAll.toString() );
            //
            // Sort sort = new Sort();
            // SortField sortField = new SortField( "id", SortField.INT, true );
            // sort.setSort( sortField );

            // Filter filter = new CachingWrapperFilter( new QueryWrapperFilter(
            // queryAll ) );

            // TopDocs allHitDoc = null;
            // if( startFlagDoc != null )
            // {
            // allHitDoc = searcher.searchAfter( startFlagDoc, queryAll,
            // limitSize ); // 执行搜索，获取查询结果集对象
            // }
            // else
            // {
            // allHitDoc = searcher.search( queryAll, limitSize );
            // }
            TopDocs allHitDocs = null;

            if( pageStartDocId.intValue() < 1 )
            {
                if( sortMode )
                {
                    result = searcher.search( queryAll, limitSize, sort );
                }
                else
                {
                    result = searcher.search( queryAll, limitSize );
                }

                totalHits = result.totalHits;
            }
            else
            {
                if( sortMode )
                {
                    allHitDocs = searcher.search( queryAll, pageStartDocId.intValue() + 1, sort );
                }
                else
                {
                    allHitDocs = searcher.search( queryAll, pageStartDocId.intValue() + 1 );
                }

                totalHits = allHitDocs.totalHits; // 获取命中数

                if( totalHits > 1 )
                {
                    result = searcher.searchAfter( allHitDocs.scoreDocs[pageStartDocId.intValue()],
                        queryAll, limitSize );
                }
            }
            searchTime = ( System.nanoTime() - l1 );

            ms = MathUtil.div( Double.valueOf( searchTime ).doubleValue(), Double.valueOf( 1000000 )
                .doubleValue(), 0 );

            log.info( "查询时间: " + ms + " 毫秒" );

            log.info( "命中数：" + totalHits );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            log.error( e );
        }

        return new Object[] { result, queryAll, df2.format( ms / 1000 ),
            Integer.valueOf( totalHits ) };
    }

    public Map getSearchMap()
    {
        return searcherMap;
    }

    public Map getSearchOutdatedReaderMap()
    {
        return outdatedReaderMap;
    }

    public static String buildKey( String rootPath, Long siteId )
    {
        return "siteId:" + siteId + "rootPath:" + rootPath;
    }

    public static String getIndexRootFullPathAndCheckDir( SiteGroupBean site )
    {
        // 根目录
        SystemRuntimeConfig config = SystemConfiguration.getInstance().getSystemConfig();

        String rootIndexPath = config.getSystemRealPath() + File.separator + site.getSiteRoot()
            + File.separator + Constant.CONTENT.INDEX_BASE;

        File rootDir = new File( rootIndexPath );

        if( !rootDir.exists() )
        {
            rootDir.mkdirs();
        }

        return rootIndexPath;
    }

    public String retrieveSearchContentUrlLinkByContentId( Long contentId )
    {
        ContentMainInfoBean mainInfo = contentService.retrieveSingleContentMainInfoBean( contentId );

        if( mainInfo == null )
        {
            return null;
        }

        ContentClassBean classBean = channelService.retrieveSingleClassBeanInfoByClassId( mainInfo
            .getClassId() );

        if( classBean == null || classBean.getClassId().longValue() < 0 )
        {
            return null;
        }

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( classBean.getSiteFlag() );

        if( site == null )
        {
            return null;
        }

        // HTML
        if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( classBean.getContentProduceType() ) )
        {
            String staticUrl = mainInfo.getStaticPageUrl();

            if( StringUtil.isStringNotNull( staticUrl ) )
            {
                return site.getSiteUrl()
                // + site.getPublishRoot()
                    + staticUrl;
            }
            else
            {
                // 若内容静态URL不存在,则输出动态URL
                // return disposeUrlLink( mainInfo, site, classBean );

                // 获取内容发布规则

                PublishRuleBean ruleBean = publishService.retrieveSinglePublishRuleBean( classBean
                    .getContentPublishRuleId() );

                if( ruleBean == null )
                {
                    return site.getSiteUrl()
                    // + site.getPublishRoot()
                        + "NO_RULE";
                }
                else
                {
                    Map info = new HashMap( 2 );
                    info.put( Constant.METADATA.CONTENT_ID_NAME, contentId );

                    String[] pathInfo = ruleBean.getFullContentClassPublishPath( site, classBean,
                        info, null );
                    return site.getSiteUrl()
                    // + site.getPublishRoot()
                        + pathInfo[1];
                }
            }
        }
        else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( classBean
            .getContentProduceType() ) )
        {
            return disposeUrlLink( mainInfo, site, classBean );
        }

        return null;
    }

    private String disposeUrlLink( ContentMainInfoBean mainInfo, SiteGroupBean site,
        ContentClassBean classBean )
    {
        String contentTemplateUrl = mainInfo.getEspecialTemplateUrl();

        if( StringUtil.isStringNull( contentTemplateUrl ) )
        {
            // 如果当前的单个内容没有特殊模斑则将取栏目共用模版
            contentTemplateUrl = classBean.getContentTemplateUrl();
        }

        // if( site.isNotHost() )
        {
            return site.getSiteUrl()
            // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                // + Constant.CONTENT.URL_SEP
                + StringUtil.replaceString( contentTemplateUrl, "{content-id}", mainInfo
                    .getContentId().toString(), false, false );
        }
    }

    /**
     * 将系统内容模型数值Bean转换为Lucene索引,目前的做法:对于任何模型只分析指定的内容信息
     * 目前分别为标题,文本内容,其中,文本内容针对摘要和主编辑器
     * 
     * @param bean
     * @return
     */
    public static Document transferBeanToIndexDocument( SearchIndexContentStateBean bean )
    {
        if( bean == null )
        {
            return null;
        }

        /**
         * 获取内容数据
         */
        Map info = new HashMap();

        if( bean.getClassId().longValue() != -999999999 )
        {
            info = contentService.retrieveSingleUserDefineContent( bean.getModelId(), bean
                .getContentId() );
        }
        else
        {
            info = metaDataService.retrieveSingleFormDataById( bean.getSiteId(), bean.getModelId(),
                bean.getContentId() );
        }

        if( info.isEmpty() )
        {
            log.info( "[SearchService] transferBeanToIndexDocument() 内容信息丢失，contentId:"
                + bean.getContentId() );
            return null;
        }

        Document document = new Document();

        Field field = null;

        // 以下ID项目,不分析

        field = new Field( "contentId", bean.getContentId().toString(), Field.Store.YES,
            Field.Index.NOT_ANALYZED_NO_NORMS );

        document.add( field );

        field = new Field( "censor", bean.getCensor().toString(), Field.Store.YES,
            Field.Index.NOT_ANALYZED_NO_NORMS );

        document.add( field );

        field = new Field( "classId", bean.getClassId().toString(), Field.Store.YES,
            Field.Index.NOT_ANALYZED_NO_NORMS );

        document.add( field );

        field = new Field( "modelId", bean.getModelId().toString(), Field.Store.YES,
            Field.Index.NOT_ANALYZED_NO_NORMS );

        document.add( field );

        field = new Field( "siteId", bean.getSiteId().toString(), Field.Store.YES,
            Field.Index.NOT_ANALYZED_NO_NORMS );

        document.add( field );

        field = new Field( "addDate", bean.getIndexDate().toString(), Field.Store.YES,
            Field.Index.NOT_ANALYZED );

        document.add( field );

        /**
         * 以下内容项目,分析语句
         */
        if( bean.getClassId().longValue() != -999999999 )
        {
            // 标题为默认

            field = new Field( "title", info.get( "title" ) == null ? "" : info.get( "title" )
                .toString(), Field.Store.YES, Field.Index.ANALYZED,
                Field.TermVector.WITH_POSITIONS_OFFSETS );

            document.add( field );

            // 关键字为默认

            field = new Field( "keywords", info.get( "keywords" ) == null ? "" : info.get(
                "keywords" ).toString(), Field.Store.YES, Field.Index.ANALYZED,
                Field.TermVector.WITH_POSITIONS_OFFSETS );

            document.add( field );
        }

        // 以下为模型字段中支持搜索的字段
        List fieldSignList = metaDataService.retrieveModelFiledInfoBeanList( bean.getModelId() );

        if( fieldSignList.isEmpty() )
        {
            log.info( "[SearchService] transferBeanToIndexDocument() 模型字段信息丢失，modelId:"
                + bean.getModelId() );
            return null;
        }

        ModelFiledInfoBean fieldBean = null;

        String endText = "";
        for ( int i = 0; i < fieldSignList.size(); i++ )
        {
            fieldBean = ( ModelFiledInfoBean ) fieldSignList.get( i );

            if( Constant.COMMON.ON.equals( fieldBean.getSearchFlag() ) )
            {
                // 需要解析出纯文本，不干扰分词活动
                if( StringUtil.isStringNotNull( ( String ) info.get( fieldBean.getFieldSign() ) ) )
                {
                    endText = Jsoup.parse( info.get( fieldBean.getFieldSign() ).toString() ).text();
                }

                field = new Field( fieldBean.getFieldSign(), endText, Field.Store.YES,
                    Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS );

                document.add( field );
            }
        }

        if( bean.getBoost() != null )
        {
            document.setBoost( bean.getBoost() );
        }

        return document;
    }

    public Object[] searchContentByKey( SiteGroupBean siteBean, String[] sfa, Long modelId,
        Long classId, Long[] closeClassIds, Long closeCid, String[] dateRange, String queryKey,
        int pageSize, int page, boolean titleMode, boolean manageMode )
    {
        int limitVar = pageSize;

        if( page < 1 )
        {
            page = 1;
        }

        String rootFullIndexPath = SearchService.getIndexRootFullPathAndCheckDir( siteBean );

        String indexKey = SearchService.buildKey( rootFullIndexPath, siteBean.getSiteId() );

        IndexSearcher searcher = getIndexSearcher( indexKey, rootFullIndexPath );

        if( searcher == null )
        {
            return new Object[] { new ArrayList(), new Page( 1, 0, 1 ) };
        }

        String[] searchFieldArray = null;

        // 查询所有可搜索字段
        if( titleMode )
        {
            searchFieldArray = new String[] { "title", "keywords" };
        }
        else if( sfa != null )
        {
            searchFieldArray = sfa;
        }
        else
        {
            // 全字段搜索
            searchFieldArray = metaDataService.retrieveAllSiteSearchFieldStrArrayMode();
        }

        // 分页数据
        int currentPagEndPos = page * limitVar - 1;

        int pageStartDocId = currentPagEndPos - limitVar;

        Object[] searchResult = SearchService.search( searcher, queryKey, searchFieldArray,
            ( classId == null || classId.longValue() < 0 ) ? null : new String[] { classId
                .toString() }, closeClassIds, closeCid,
            ( modelId != null && modelId.longValue() != -1 ) ? new String[] { modelId.toString() }
                : null, dateRange, Integer.valueOf( pageStartDocId ), limitVar, manageMode );

        List idList = new ArrayList();

        Page pageInfo = null;

        if( searchResult != null )
        {
            pageInfo = new Page( limitVar, ( ( Integer ) searchResult[3] ).intValue(), page );

            TopDocs allHotDoc = ( TopDocs ) searchResult[0];

            try
            {
                ScoreDoc sdoc = null;
                if( allHotDoc != null )
                {
                    ScoreDoc[] scoreDocArray = allHotDoc.scoreDocs;
                    for ( int j = 0; j < scoreDocArray.length; j++ )
                    {
                        Document hitDoc;

                        sdoc = scoreDocArray[j];

                        hitDoc = searcher.doc( sdoc.doc );

                        String contentId = hitDoc.get( "contentId" );

                        idList.add( contentId );

                    }
                }
            }
            catch ( Exception e )
            {
                log.error( e );

            }
        }

        if( pageInfo == null )
        {
            pageInfo = new Page( 1, 0, 1 );
        }

        return new Object[] { idList, pageInfo };
    }

    public void rebuildSearchIndex( List classIdList, SiteGroupBean siteBean,
        PublishStatusBean status )
    {
        if( classIdList == null )
        {
            return;
        }
        Long classId = null;

        int count = 0;
        for ( int i = 0; i < classIdList.size(); i++ )
        {
            classId = Long.valueOf( ( String ) classIdList.get( i ) );

            ContentClassBean classBean = channelService
                .retrieveSingleClassBeanInfoByClassId( classId );
            if( ( classBean != null ) && ( classBean.getClassId().longValue() >= 0L ) )
            {
                count = count
                    + retrieveAllContentCountByClassIDAndModelIdAndFlag( classId, Double
                        .valueOf( 1.0E+018D ), disposeLimitCount, null, null );
            }
        }
        status.setPubCount( Long.valueOf( count ) );
        try
        {
            this.mysqlEngine.beginTransaction();

            this.mysqlEngine.startBatch();
            for ( int ix = 0; ix < classIdList.size(); ix++ )
            {
                classId = Long.valueOf( ( String ) classIdList.get( ix ) );

                ContentClassBean classBean = channelService
                    .retrieveSingleClassBeanInfoByClassId( classId );
                if( ( classBean != null ) && ( classBean.getClassId().longValue() >= 0L ) )
                {
                    List contentList = this.contentDao.queryMainContentByClassIdAndModelId(
                        classId, classBean.getContentType(), Long.valueOf( 999999999999999999L ),
                        disposeLimitCount );

                    Long prevContentOrderId = null;
                    while ( contentList.size() > 0 )
                    {
                        Map info = null;
                        for ( int i = 0; i < contentList.size(); i++ )
                        {
                            info = ( Map ) contentList.get( i );

                            SearchIndexContentState searchIndexState = new SearchIndexContentState();

                            searchIndexState.setClassId( classBean.getClassId() );
                            searchIndexState.setContentId( ( Long ) info.get( "contentId" ) );

                            searchIndexState.setCensor( ( Integer ) info.get( "censorState" ) );
                            searchIndexState.setBoost( ( Float ) info.get( "boost" ) );
                            searchIndexState.setIndexDate( ( Date ) info.get( "addTime" ) );

                            searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_EDIT );

                            searchIndexState.setModelId( ( Long ) info.get( "modelId" ) );
                            searchIndexState.setSiteId( siteBean.getSiteId() );

                            addIndexContentState( searchIndexState );

                            status
                                .setCurrent( Long.valueOf( status.getCurrent().longValue() + 1L ) );
                        }
                        Map cMap = ( Map ) contentList.get( contentList.size() - 1 );
                        if( contentList.size() == disposeLimitCount.intValue() )
                        {
                            prevContentOrderId = ( Long ) cMap.get( "contentId" );
                        }
                        else
                        {
                            prevContentOrderId = Long.valueOf( 0L );
                        }
                        contentList = this.contentDao.queryMainContentByClassIdAndModelId( classId,
                            classBean.getContentType(), prevContentOrderId, disposeLimitCount );
                    }
                }
            }
            this.mysqlEngine.executeBatch();

            this.mysqlEngine.commit();
        }
        finally
        {
            this.mysqlEngine.endTransaction();
        }
    }

    public void rebuildFormSearchIndex( List modelIdList, SiteGroupBean siteBean,
        PublishStatusBean status )
    {
        if( modelIdList == null )
        {
            return;
        }
        Long modelId = null;

        long count = 0L;
        for ( int i = 0; i < modelIdList.size(); i++ )
        {
            modelId = Long.valueOf( ( String ) modelIdList.get( i ) );

            DataModelBean modelBean = metaDataService.retrieveSingleDataModelBeanById( modelId );
            if( modelBean != null )
            {
                count += retrieveAllFormContentCount( modelId ).longValue();
            }
        }
        status.setPubCount( Long.valueOf( count ) );
        try
        {
            this.mysqlEngine.beginTransaction();

            this.mysqlEngine.startBatch();
            for ( int ix = 0; ix < modelIdList.size(); ix++ )
            {
                modelId = Long.valueOf( ( String ) modelIdList.get( ix ) );

                DataModelBean modelBean = metaDataService.retrieveSingleDataModelBeanById( modelId );
                if( modelBean != null )
                {
                    List contentList = metaDataService.retrieveFormDataByIdTrace( modelId, Long
                        .valueOf( 999999999999999999L ), disposeLimitCount );

                    Long prevContentOrderId = null;
                    while ( contentList.size() > 0 )
                    {
                        Map info = null;
                        for ( int i = 0; i < contentList.size(); i++ )
                        {
                            info = ( Map ) contentList.get( i );

                            SearchIndexContentState searchIndexState = new SearchIndexContentState();

                            searchIndexState.setClassId( Long.valueOf( -999999999L ) );
                            searchIndexState.setContentId( ( Long ) info.get( "contentId" ) );

                            searchIndexState.setCensor( Constant.WORKFLOW.CENSOR_STATUS_SUCCESS );

                            searchIndexState.setIndexDate( new Date( DateAndTimeUtil
                                .clusterTimeMillis() ) );

                            searchIndexState.setEventFlag( Constant.JOB.SEARCH_INDEX_EDIT );

                            searchIndexState.setModelId( modelBean.getDataModelId() );
                            searchIndexState.setSiteId( siteBean.getSiteId() );

                            addIndexContentState( searchIndexState );

                            status
                                .setCurrent( Long.valueOf( status.getCurrent().longValue() + 1L ) );
                        }
                        Map cMap = ( Map ) contentList.get( contentList.size() - 1 );
                        if( contentList.size() == disposeLimitCount.intValue() )
                        {
                            prevContentOrderId = ( Long ) cMap.get( "contentId" );
                        }
                        else
                        {
                            prevContentOrderId = Long.valueOf( 0L );
                        }
                        contentList = metaDataService.retrieveFormDataByIdTrace( modelId,
                            prevContentOrderId, disposeLimitCount );
                    }
                }
            }
            this.mysqlEngine.executeBatch();

            this.mysqlEngine.commit();
        }
        finally
        {
            this.mysqlEngine.endTransaction();
        }
    }

    private int retrieveAllContentCountByClassIDAndModelIdAndFlag( Long classId,
        Double orderIdFlag, Integer limitCount, Timestamp startAddDate, Timestamp endAddDate )
    {

        ContentClassBean classBean = channelService.retrieveSingleClassBeanInfoByClassId( classId );

        List contentList = contentService.retrieveNeedPublishContentByClassIDAndModelIdAndFlag(
            classId, classBean.getContentType(), Double
                .valueOf( Constant.CONTENT.MAX_ORDER_ID_FLAG ), disposeLimitCount, null, null );

        Double prevContentOrderId = null;

        int count = contentList.size();

        while ( contentList.size() > 0 )
        {
            Map cMap = ( Map ) contentList.get( contentList.size() - 1 );

            if( contentList.size() == disposeLimitCount.intValue() )
            {
                prevContentOrderId = ( Double ) cMap.get( "orderIdFlag" );
            }
            else
            {
                // 不再获取
                prevContentOrderId = Double.valueOf( 0 );
            }

            contentList = contentService.retrieveNeedPublishContentByClassIDAndModelIdAndFlag(
                classId, classBean.getContentType(), prevContentOrderId, disposeLimitCount, null,
                null );

            count += contentList.size();
        }

        return count;
    }

    private Long retrieveAllFormContentCount( Long modelId )
    {
        return metaDataService.retrieveAllFormDataCountForModelId( modelId );
    }

    public void deleteSearchKeyInfo( List idList )
    {
        Long id = null;

        for ( int i = 0; i < idList.size(); i++ )
        {
            id = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

            if( id.longValue() < 1 )
            {
                continue;
            }

            searchDao.deleteSearchKey( id );
        }

        SearchDao.clear();
    }

    public List retrieveSearchKeyCountInfoBySiteId( Long siteId, Integer size )
    {
        return searchDao.querySearchKeyBySiteId( siteId, Long.valueOf( 0 ), size );
    }

    public Object getSearchKeyInfoTag( String pn, String size )
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        List result = null;

        int pageNum = StringUtil.getIntValue( pn, 1 );

        int pageSize = StringUtil.getIntValue( size, 15 );

        Page pageInfo = null;

        Long count = null;

        count = searchDao.querySearchKeyCountBySiteId( site.getSiteId() );

        pageInfo = new Page( pageSize, count.intValue(), pageNum );

        result = searchDao.querySearchKeyBySiteId( site.getSiteId(), Long.valueOf( pageInfo
            .getFirstResult() ), Integer.valueOf( pageSize ) );

        return new Object[] { result, pageInfo };
    }

    public static void main( String[] args ) throws IOException
    {

    }

}
