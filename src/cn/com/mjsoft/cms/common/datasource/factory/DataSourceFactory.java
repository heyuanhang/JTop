package cn.com.mjsoft.cms.common.datasource.factory;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.com.mjsoft.framework.util.InitClassPathHelper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

public class DataSourceFactory
{
    private static Logger log = Logger.getLogger( DataSourceFactory.class );

    public static final String DS_FILE_NAME = "config" + File.separator
        + "data_source.xml";

    private static final Map DS = new HashMap();

    static
    {
        readCfgFile();
    }

    private static void readCfgFile()
    {

        String rootPath = ServletUtil
            .getClassPath( InitClassPathHelper.class );

        String rtFile = StringUtil.replaceString( rootPath, "classes",
            DS_FILE_NAME, false, false );

        SAXReader reader = new SAXReader();

        try
        {

            Document doc = reader.read( new FileInputStream( rtFile ) );

            Element root = doc.getRootElement();

            List moduleNodes = root.elements();

            for ( Iterator it = moduleNodes.iterator(); it.hasNext(); )
            {
                Element dsNode = ( Element ) it.next();

                String name = dsNode.attributeValue( "name" );

                String type = dsNode.attributeValue( "type" );

                Map val = new HashMap();

                String ip = dsNode.element( "ip" ).getText();

                val.put( "ip", ip );

                String port = dsNode.element( "port" ).getText();

                val.put( "port", port );

                String dbName = dsNode.element( "dbName" ).getText();

                val.put( "dbName", dbName );

                String user = dsNode.element( "user" ).getText();

                val.put( "user", user );

                String pw = dsNode.element( "pw" ).getText();

                val.put( "pw", pw );

                String conn_count = dsNode.element( "conn_count" ).getText();

                val.put( "conn_count", conn_count );

                if( "mysql".equals( type ) )
                {
                    MySqlDataSource mds = new MySqlDataSource();

                    mds.setCfg( val );

                    mds.registerPool();

                    DS.put( name, mds );

                    log
                        .info( "****************************************************" );
                    log.info( "DataSourceFactory: 新增Mysql数据源：" + name );
                    log
                        .info( "****************************************************" );
                }
                else if( "sqlserver".equals( type ) )
                {
                    MSSqlDataSource mds = new MSSqlDataSource();

                    mds.setCfg( val );

                    mds.registerPool();

                    DS.put( name, mds );

                    log
                        .info( "****************************************************" );
                    log.info( "DataSourceFactory: 新增SqlServer数据源：" + name );
                    log
                        .info( "****************************************************" );

                }
                else if( "oracle".equals( type ) )
                {
                    OracleDataSource mds = new OracleDataSource();

                    mds.setCfg( val );

                    mds.registerPool();

                    DS.put( name, mds );

                    log
                        .info( "****************************************************" );
                    log.info( "DataSourceFactory: 新增Oracle数据源：" + name );
                    log
                        .info( "****************************************************" );

                }

            }
        }
        catch ( Exception e )
        {

            e.printStackTrace();
        }

    }

    public static DataSource getDataSource( String name )
    {
        return ( DataSource ) DS.get( name );
    }
}
