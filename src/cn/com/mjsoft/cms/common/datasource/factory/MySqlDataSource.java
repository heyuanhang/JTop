package cn.com.mjsoft.cms.common.datasource.factory;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.logicalcobwebs.proxool.ConnectionPool;
import org.logicalcobwebs.proxool.ConnectionPoolManager;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;

import cn.com.mjsoft.framework.util.StringUtil;

public class MySqlDataSource implements DataSource
{
    public static final String CS_FILE_NAME = "config" + File.separator
        + "cs.properties";

    public static List rs = new ArrayList();

    public Map cfg = new HashMap();

    private String MYSQL_ALIAS = "mysql" + StringUtil.getUUIDString();

    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    static
    {
        try
        {
            Class.forName( MYSQL_DRIVER );
        }
        catch ( ClassNotFoundException e )
        {
            e.printStackTrace();
        }
    }

    public MySqlDataSource()
    {

    }

    public Connection getConnection() throws SQLException
    {
        ConnectionPool cp = null;
        try
        {
            if( !ConnectionPoolManager.getInstance().isPoolExistsForBtf(
                MYSQL_ALIAS ) )
            {
                registerPool();
            }

            cp = ConnectionPoolManager.getInstance().getConnectionPoolForBtf(
                MYSQL_ALIAS );
            Connection co = cp.getConnectionForBtf();

            return co;
        }
        catch ( ProxoolException e )
        {

            throw new SQLException( e.toString() );
        }
    }

    public Connection getConnection( String username, String password )
        throws SQLException
    {
        throw new UnsupportedOperationException(
            "getConnection( String username, String password )" );
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

    public void setCfg( Map cfg )
    {
        this.cfg.putAll( cfg );
    }

    public void registerPool()
    {

        Map mysql = cfg;

        String driverUrl = "jdbc:mysql://"
            + ( String ) mysql.get( "ip" )
            + ":"
            + ( String ) mysql.get( "port" )
            + "/"
            + ( String ) mysql.get( "dbName" )
            + "?autoReconnect=true&zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=gbk";

        int mc = StringUtil.getIntValue( ( String ) mysql.get( "conn_count" ),
            40 );

        if( mc < 30 )
        {
            mc = 30;
        }

        Properties info = new Properties();
        info.setProperty( "proxool.maximum-connection-count", mc + "" );
        info.setProperty( "proxool.minimum-connection-count", "20" );
        info.setProperty( "proxool.simultaneous-build-throttle", "20" );
        info.setProperty( "proxool.house-keeping-test-sql",
            "select CURRENT_DATE" );
        info.setProperty( "proxool.maximum-active-time", "9999000" );
        info.setProperty( "autoReconnect", "true" );
        info.setProperty( "proxool.house-keeping-sleep-time", "60000" );
        info.setProperty( "proxool.test-before-use", "true" );
        info.setProperty( "user", ( String ) mysql.get( "user" ) );
        info.setProperty( "password", ( String ) mysql.get( "pw" ) );

        String driverClass = "com.mysql.jdbc.Driver";

        String url = "proxool." + MYSQL_ALIAS + ":" + driverClass + ":"
            + driverUrl;
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

        return null;
    }

}
