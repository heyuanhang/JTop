package cn.com.mjsoft.framework.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogByteSizeMergePolicy;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.exception.FrameworkException;

/**
 * 沿用Lucene3.6API
 * 
 */
public class LuceneUtil
{
    private static Logger log = Logger.getLogger( LuceneUtil.class );

    public static Analyzer analyzer = new IKAnalyzer();

    /**
     * 创建一个新的IndexSearcher.
     * 
     * @param reader
     * @return
     */
    public static IndexSearcher createSearcher( IndexReader reader )
    {
        if( reader == null )
        {
            return null;
        }

        return new IndexSearcher( reader );
    }

    /**
     * 创建一个新的IndexReader.
     * 
     * @param indexFullPath
     * @return
     */
    public static IndexReader createReader( String indexFullPath )
    {
        IndexReader reader = null;

        File rootDir = new File( indexFullPath );

        if( rootDir.exists() && rootDir.isDirectory() )
        {
            try
            {
                return IndexReader.open( FSDirectory.open( new File(indexFullPath ) ) );
            }
            catch ( Exception e )
            {
                log.error( "[createReader] 当前站点无任何索引元文件." );
            }
        }
        else
        {
            log.error( "[LuceneUtil] createReader: 指定的索引目录不存在!" );
        }

        return reader;
    }

    /**
     * 创建一个新的IndexWriter.
     * 
     * @param indexFullPath
     * @return
     */
    public static IndexWriter createWriter( String indexFullPath,
        double bufRAMSize, int mergeFactor, int maxMergeDocs )
    {
        IndexWriter writer = null;

        File rootDir = new File( indexFullPath );

        if( rootDir.exists() && rootDir.isDirectory() )
        {
            rootDir.mkdirs();
        }

        try
        {
            Directory dir = FSDirectory.open( new File( indexFullPath ) );

            writer = createWriter( dir, bufRAMSize, mergeFactor, maxMergeDocs );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            log.error( e );
        }

        return writer;
    }

    /**
     * 创建一个新的IndexWriter.
     * 
     * @param indexFullPath
     * @return
     */
    public static IndexWriter createWriter( Directory dir, double bufRAMSize,
        int mergeFactor, int maxMergeDocs )
    {
        IndexWriter writer = null;

        try
        {
            IndexWriterConfig iwc = new IndexWriterConfig( Version.LUCENE_36,
                analyzer );
            iwc.setOpenMode( OpenMode.CREATE_OR_APPEND );
            iwc.setRAMBufferSizeMB( bufRAMSize );

            LogMergePolicy po = new LogByteSizeMergePolicy();
            po.setMergeFactor( mergeFactor );
            po.setMaxMergeDocs( maxMergeDocs );
            po.setUseCompoundFile( true );

            iwc.setMergePolicy( po );

            writer = new IndexWriter( dir, iwc );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            log.error( e );
        }

        return writer;
    }

    /**
     * 重新打开关联的索引,若index发生改变,返回一个新的searcher,否则返回null,仍然使用以前的searcher.
     * 
     * @param oldReader
     * @param oldSearch
     * @param currentWriter
     * @return Object[] [0]:新的Search [1]:过时的search,若为null则无变化
     */
    public static Object[] reopenReaderAndSearcher( IndexSearcher oldSearch )
    {
        return reopenReaderAndSearcher( oldSearch, null );
    }

    /**
     * 重新打开关联的索引,若index发生改变,返回一个新的searcher,否则返回null,仍然使用以前的searcher, <br>
     * 若提供了当前的writer,则当前writer中没有提交的数据也会被包含
     * 
     * @param oldReader
     * @param oldSearch
     * @param currentWriter
     * @return Object[] [0]:新的Search [1]:过时的search,若为null则无变化
     */
    public static Object[] reopenReaderAndSearcher( IndexSearcher oldSearch,
        IndexWriter currentWriter )
    {
        if( oldSearch == null )
        {
            throw new FrameworkException( "[LuceneUtil] : oldSearch:"
                + oldSearch );
        }

        IndexReader oldReader = oldSearch.getIndexReader();

        if( oldReader == null )
        {
            throw new FrameworkException( "[LuceneUtil] : oldReader:"
                + oldSearch );
        }

        try
        {
            IndexReader newReader = null;

            if( currentWriter != null )
            {
                newReader = IndexReader.openIfChanged( oldReader,
                    currentWriter, true );
            }
            else
            {
                newReader = IndexReader.openIfChanged( oldReader );
            }

            if( newReader != null )
            {
                // 返回新的 searcher
                return new Object[] { new IndexSearcher( newReader ), oldSearch };
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            log.error( e );
        }

        return new Object[] { oldSearch, null };
    }

    // public static void addIndexDocument( IndexWriter writer, List fieldList )
    // {
    //
    // if( fieldList == null || writer == null )
    // {
    // log.error( "[LuceneUtil] addIndexDocument 参数为空" );
    // return;
    // }
    //
    // Document document = new Document();
    //
    // Field field = null;
    // for ( int i = 0; i < fieldList.size(); i++ )
    // {
    // field = ( Field ) fieldList.get( i );
    // String s = field.name();
    // document.add( field );
    // }
    //
    // //addIndexDocument( writer, document );
    // }

    public static void addOrUpdateIndexDocument( IndexWriter writer,
        Document document, String id )
    {
        if( document == null || writer == null )
        {
            log.error( "[LuceneUtil] addOrUpdateIndexDocument 参数为空" );
            return;
        }

        try
        {
            writer.updateDocument( new Term( "contentId", id ), document );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            log.error( e );
        }
    }

    public static void deleteIndexDocument( IndexWriter writer, Term idTerm )
    {

        if( writer == null )
        {
            log.error( "[LuceneUtil] deleteIndexDocument 参数为空" );
            return;
        }

        try
        {
            writer.deleteDocuments( idTerm );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            log.error( e );
        }
    }

    public static void close( IndexWriter writer )
    {
        if( writer != null )
        {
            try
            {
                writer.forceMerge( 3 );
                writer.close();
            }
            catch ( Exception e )
            {
                e.printStackTrace();
                log.error( e );
            }
        }
    }

    public static void commit( IndexWriter writer )
    {
        try
        {
            if( writer != null )
            {
                writer.forceMerge( 3 );
                writer.close();
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            log.error( e );
        }

    }

    /**
     * 分析并切分目标文字
     * 
     * @param target 目标文字
     * @param split 结果分割符
     * @param notLimit 是否最小化切分
     * @return
     */
    public static List wordAnalysis( String target, String split,
        boolean notLimit )
    {
        if( StringUtil.isStringNull( target ) )
        {
            return new ArrayList();
        }

        StringBuffer charBuffer = new StringBuffer();
        List result = new ArrayList();

        InputStream input = null;
        Reader reader = null;

        try
        {
            input = new ByteArrayInputStream( target.getBytes() );
            reader = new InputStreamReader( input );

            IKSegmentation iks = new IKSegmentation( reader, notLimit );
            Lexeme lexeme;
            while ( ( lexeme = iks.next() ) != null )
            {
                result.add( lexeme.getLexemeText() );
                charBuffer.append( lexeme.getLexemeText() ).append( split );
            }

            if( charBuffer.length() > 1 )
            {
                charBuffer.delete( charBuffer.length() - split.length(),
                    charBuffer.length() );
            }
        }
        catch ( IOException e )
        {
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

            if( input != null )
            {
                try
                {
                    input.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }

        }

        log.debug( "[wordAnalysis] " + charBuffer.toString() );
        return result;
    }

}
