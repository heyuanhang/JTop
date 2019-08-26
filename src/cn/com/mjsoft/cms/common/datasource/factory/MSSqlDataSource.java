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

@SuppressWarnings( "unchecked" )
public class MSSqlDataSource implements DataSource
{
    public static final String CS_FILE_NAME = "config" + File.separator + "cs.properties";

    public static List rs = new ArrayList();

    public Map cfg = new HashMap();

    private String SQLSERVER_ALIAS = "sqlserver" + StringUtil.getUUIDString();

    private static final String SQLSERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    static
    {
        try
        {
            Class.forName( SQLSERVER_DRIVER );
        }
        catch ( ClassNotFoundException e )
        {
            e.printStackTrace();
        }
    }

    public MSSqlDataSource()
    {

    }

    public Connection getConnection() throws SQLException
    {
        ConnectionPool cp = null;
        try
        {
            if( !ConnectionPoolManager.getInstance().isPoolExistsForBtf( SQLSERVER_ALIAS ) )
            {
                registerPool();
            }

            cp = ConnectionPoolManager.getInstance().getConnectionPoolForBtf( SQLSERVER_ALIAS );
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

    public void setCfg( Map cfg )
    {
        this.cfg.putAll( cfg );
    }

    public void registerPool()
    {

        Map sqlCfg = cfg;

        String driverUrl = "jdbc:sqlserver://" + ( String ) sqlCfg.get( "ip" ) + ":"
            + ( String ) sqlCfg.get( "port" ) + ";DatabaseName=" + ( String ) sqlCfg.get( "dbName" );

        int mc = StringUtil.getIntValue( ( String ) sqlCfg.get( "conn_count" ), 40 );

        if( mc < 30 )
        {
            mc = 30;
        }

        Properties info = new Properties();
        info.setProperty( "proxool.maximum-connection-count", mc + "" );
        info.setProperty( "proxool.minimum-connection-count", "20" );
        info.setProperty( "proxool.simultaneous-build-throttle", "20" );
        info.setProperty( "proxool.house-keeping-test-sql", "select getdate()" );
        info.setProperty( "proxool.maximum-active-time", "9999000" );
        info.setProperty( "autoReconnect", "true" );
        info.setProperty( "proxool.house-keeping-sleep-time", "60000" );
        info.setProperty( "proxool.test-before-use", "true" );
        info.setProperty( "user", ( String ) sqlCfg.get( "user" ) );
        info.setProperty( "password", ( String ) sqlCfg.get( "pw" ) );

        String url = "proxool." + SQLSERVER_ALIAS + ":" + SQLSERVER_DRIVER + ":" + driverUrl;
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

    public boolean isWrapperFor( Class<?> iface ) throws SQLException
    {

        return false;
    }

    public <T> T unwrap( Class<T> iface ) throws SQLException
    {

        return null;
    }

}
