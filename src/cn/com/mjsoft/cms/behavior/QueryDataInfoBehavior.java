package cn.com.mjsoft.cms.behavior;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.com.mjsoft.cms.cluster.adapter.ClusterMapAdapter;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;

public class QueryDataInfoBehavior implements Behavior
{
    // private static final String SYSTEM_QUERY_SQL =
    // "/WEB-INF/classes/query.properties";

    private static final String SYSTEM_QUERY_XSQL = "WEB-INF" + File.separator + "config"
        + File.separator + "query.xml";

    // private static final String SYSTEM_SERVICE =
    // "/WEB-INF/classes/service.properties";

    private static boolean debugMode = false;

    private static ClusterMapAdapter QUERY_SQL = new ClusterMapAdapter(
        "queryDataInfoBehavior.QUERY_SQL", String.class, String.class );

    // private static Properties QUERY_SERVICE = new Properties();;

    public Object operation( Object target, Object[] param )
    {
        // ServletContext context = ( ServletContext ) param[0];

        // String filePath = context.getRealPath( SYSTEM_QUERY_SQL );

        // String serviceFilePath = context.getRealPath( SYSTEM_SERVICE );

        // String queryXmlFilePath = context.getRealPath( SYSTEM_QUERY_XSQL );

        // if( null != filePath )
        // {
        // InputStream in = null;
        // try
        // {
        // QUERY_SQL.clear();
        // in = new FileInputStream( filePath );
        // QUERY_SQL.load( in );
        // }
        // catch ( Exception e )
        // {
        // e.printStackTrace();
        // }
        // finally
        // {
        // if( null != in )
        // {
        // try
        // {
        // in.close();
        // }
        // catch ( IOException e )
        // {
        // e.printStackTrace();
        // }
        // }
        // }
        // }

        // if( null != serviceFilePath )
        // {
        // InputStream in = null;
        // try
        // {
        // in = new FileInputStream( serviceFilePath );
        // QUERY_SERVICE.load( in );
        // }
        // catch ( Exception e )
        // {
        // e.printStackTrace();
        // }
        // finally
        // {
        // if( null != in )
        // {
        // try
        // {
        // in.close();
        // }
        // catch ( IOException e )
        // {
        // e.printStackTrace();
        // }
        // }
        // }
        //
        // }

        // xml
        SAXReader reader = new SAXReader();

        String basePath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String filePath = basePath + SYSTEM_QUERY_XSQL;

        try
        {
            Document doc = reader.read( new FileInputStream( filePath ) );

            Element root = doc.getRootElement();

            List moduleNodes = root.elements();

            for ( Iterator it = moduleNodes.iterator(); it.hasNext(); )
            {
                Element queryNode = ( Element ) it.next();

                if( "query".equals( queryNode.getName() ) )
                {
                    String key = queryNode.attributeValue( "key" );

                    QUERY_SQL.put( key.trim().toLowerCase(), queryNode.getTextTrim() );

                }

            }
        }
        catch ( FileNotFoundException e )
        {

            e.printStackTrace();
        }
        catch ( DocumentException e )
        {

            e.printStackTrace();
        }

        return null;
    }

    public static ClusterMapAdapter getSystemQueryDataSQl()
    {
        if( debugMode )
        {
            SAXReader reader = new SAXReader();

            String basePath = SystemConfiguration.getInstance().getSystemConfig()
                .getSystemRealPath();

            String filePath = basePath + SYSTEM_QUERY_XSQL;

            try
            {
                Document doc = reader.read( new FileInputStream( filePath ) );

                Element root = doc.getRootElement();

                List moduleNodes = root.elements();

                for ( Iterator it = moduleNodes.iterator(); it.hasNext(); )
                {
                    Element queryNode = ( Element ) it.next();

                    if( "query".equals( queryNode.getName() ) )
                    {
                        String key = queryNode.attributeValue( "key" );

                        QUERY_SQL.put( key.trim().toLowerCase(), queryNode.getTextTrim() );
                    }

                }
            }
            catch ( FileNotFoundException e )
            {

                e.printStackTrace();
            }
            catch ( DocumentException e )
            {

                e.printStackTrace();
            }

            return QUERY_SQL;

        }

        return QUERY_SQL;
    }

    // public static Properties getSystemQueryDataService()
    // {
    // return QUERY_SERVICE;
    // }
}
