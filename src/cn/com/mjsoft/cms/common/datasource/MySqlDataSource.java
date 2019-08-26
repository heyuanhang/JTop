package cn.com.mjsoft.cms.common.datasource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.logicalcobwebs.proxool.ConnectionPool;
import org.logicalcobwebs.proxool.ConnectionPoolManager;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.framework.util.StringUtil;

public class MySqlDataSource implements DataSource
{
    public static final String CS_FILE_NAME = "config" + File.separator + "cs.properties";

    public static List rs = new ArrayList();

    private static final String MYSQL_ALIAS = "mysqll";

    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    static
    {

        registerPool();

    }

    public MySqlDataSource()
    {

    }

    public Connection getConnection() throws SQLException
    {
        ConnectionPool cp = null;
        try
        {
            if( !ConnectionPoolManager.getInstance().isPoolExistsForBtf( MYSQL_ALIAS ) )
            {
                registerPool();
            }

            cp = ConnectionPoolManager.getInstance().getConnectionPoolForBtf( MYSQL_ALIAS );
            Connection co = cp.getConnectionForBtf();

            return co;
        }
        catch ( ProxoolException e )
        {

            throw new SQLException( e.toString() );
        }
    }

    public Connection getConnection( String username, String password ) throws SQLException
    {
        throw new UnsupportedOperationException( "getConnection( String username, String password )" );
    }

    public PrintWriter getLogWriter() throws SQLException
    {
        throw new UnsupportedOperationException( "getLogWriter" );
    }

    public int getLoginTimeout() throws SQLException
    {
        throw new UnsupportedOperationException( "setLogWriter" );
    }

    public void setLogWriter( PrintWriter out ) throws SQLException
    {
        throw new UnsupportedOperationException( "setLogWriter" );

    }

    public void setLoginTimeout( int seconds ) throws SQLException
    {
        throw new UnsupportedOperationException( "setLogWriter" );

    }

    public boolean isWrapperFor( Class arg0 ) throws SQLException
    {

        return false;
    }

    public Object unwrap( Class arg0 ) throws SQLException
    {

        return null;
    }

    private static void registerPool()
    {
        String classesPath = null;
        try
        {
            classesPath = InitSiteGroupInfoBehavior.class.getClassLoader().getResource( "/" ).toURI().getPath();
        }
        catch ( URISyntaxException e1 )
        {
            e1.printStackTrace();
        }

        String osName = System.getProperty( "os.name" ).toLowerCase();

        if( osName.indexOf( "win" ) != -1 )
        {
            if( classesPath.startsWith( "/" ) )
            {
                classesPath = StringUtil.subString( classesPath, 1, classesPath.length() );
            }
        }

        classesPath = StringUtil.replaceString( classesPath, "/", File.separator, false, false );

        String rtFile = StringUtil.replaceString( classesPath, "classes", CS_FILE_NAME, false, false );

        Properties mysql = new Properties();
        try
        {
            // ins = Thread.currentThread().getContextClassLoader()
            // .getResourceAsStream( "mysql.properties" );

            mysql.load( new FileInputStream( new File( rtFile ) ) );

        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

        String driverUrl = "jdbc:mysql://" + ( String ) mysql.get( "db_ip" ) + ":" + ( String ) mysql.get( "db_port" )
            + "/" + ( String ) mysql.get( "db_name" )
            + "?autoReconnect=true&zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8";

        
        try
        {
            Class.forName( MYSQL_DRIVER );
        }
        catch ( ClassNotFoundException e )
        {
            e.printStackTrace();
        }
        Properties info = new Properties();
        info.setProperty( "proxool.maximum-connection-count", ( String ) mysql.get( "maximum-connection-count" ) );
        info.setProperty( "proxool.minimum-connection-count", ( String ) mysql.get( "minimum-connection-count" ) );
        info.setProperty( "proxool.simultaneous-build-throttle", ( String ) mysql.get( "simultaneous-build-throttle" ) );
        info.setProperty( "proxool.house-keeping-test-sql", "select CURRENT_DATE" );
        info.setProperty( "proxool.maximum-active-time", "9999000" );
        info.setProperty( "autoReconnect", "true" );
        info.setProperty( "proxool.house-keeping-sleep-time", "60000" );
        info.setProperty( "proxool.test-before-use", "true" );
        info.setProperty( "user", ( String ) mysql.get( "db_user" ) );
        info.setProperty( "password", ( String ) mysql.get( "db_pw" ) );
        String alias = "mysqll";
        String driverClass = "com.mysql.jdbc.Driver";

        String url = "proxool." + alias + ":" + driverClass + ":" + driverUrl;
        try
        {
            ProxoolFacade.registerConnectionPool( url, info );
        }
        catch ( ProxoolException e )
        {

            e.printStackTrace();
        }

    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException
    {
        // TODO Auto-generated method stub
        return null;
    }

   

}
