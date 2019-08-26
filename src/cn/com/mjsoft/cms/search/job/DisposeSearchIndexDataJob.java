package cn.com.mjsoft.cms.search.job;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.search.bean.SearchIndexContentStateBean;
import cn.com.mjsoft.cms.search.service.SearchService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.cache.jsr14.ReadWriteLockHashMap;
import cn.com.mjsoft.framework.util.LuceneUtil;

public class DisposeSearchIndexDataJob implements Job
{
    private static Logger log = Logger.getLogger( DisposeSearchIndexDataJob.class );

    private static final int DISPOSE_DEGREE = 30;

    private static final Integer DISPOSE_LIMIT = Integer.valueOf( 500 );

    private static SearchService searchService = SearchService.getInstance();

    private static Map excuteJob = new ReadWriteLockHashMap();

    public void execute( JobExecutionContext jobContent ) throws JobExecutionException
    {
        if( excuteJob.containsKey( jobContent.getJobDetail().getKey() ) )
        {
            log.info( "[DisposeSearchIndexDataJob] ...waiting..."
                + jobContent.getJobDetail().getKey() );
            return;
        }

        try
        {
            excuteJob.put( jobContent.getJobDetail().getKey(), Boolean.TRUE );

            log.info( "[DisposeSearchIndexDataJob] ...execute start..."
                + jobContent.getJobDetail().getKey() );

            // 要根据Site的不同,来进行索引的更新
            SiteGroupBean site = ( SiteGroupBean ) jobContent.getMergedJobDataMap().get( "site" );

            if( site == null )
            {
                log.info( "[DisposeSearchIndexDataJob] 重要信息丢失,任务停止执行" );
                return;
            }

            SiteGroupBean testSite = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( site.getSiteId() );

            if( testSite == null )
            {
                // 2017:删除已不存在站点的索引更新记录
                searchService.deleteIndexContentStateBySiteId( site.getSiteId() );

                log.info( "[DisposeSearchIndexDataJob] 站点已不存在,任务停止执行" );
                
                return;
            }

            String rootIndexPath = SearchService.getIndexRootFullPathAndCheckDir( site );

            String indexKey = SearchService.buildKey( rootIndexPath, site.getSiteId() );

            // clean当前搜索所相关的过失数据
            searchService.cleanOutdatedReaderMap( indexKey );

            // 处理索引文件,需集群同步
            searchService.disposeSearchIndex( rootIndexPath, DISPOSE_DEGREE, DISPOSE_LIMIT, site,
                indexKey );

            /**
             * 搜索词参数
             */
            searchService.disposeSearchQueryKeyCount( site );

            log.info( "[DisposeSearchIndexDataJob] ...execute over..."
                + jobContent.getJobDetail().getKey() );
        }
        finally
        {
            excuteJob.remove( jobContent.getJobDetail().getKey() );
        }

    }
}
