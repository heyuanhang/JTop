package cn.com.mjsoft.cms.common.datasource.factory;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.logicalcobwebs.proxool.ConnectionPool;
import org.logicalcobwebs.proxool.ConnectionPoolManager;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;

import cn.com.mjsoft.framework.util.StringUtil;

public class OracleDataSource implements DataSource
{
    public static final String CS_FILE_NAME = "config" + File.separator + "cs.properties";

    public Map cfg = new HashMap();

    private String ORACLE_ALIAS = "oracle" + StringUtil.getUUIDString();

    private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

    static
    {
        try
        {
            Class.forName( ORACLE_DRIVER );
        }
        catch ( ClassNotFoundException e )
        {
            e.printStackTrace();
        }
    }

    public OracleDataSource()
    {

    }

    public Connection getConnection() throws SQLException
    {

        ConnectionPool cp = null;
        try
        {
            if( !ConnectionPoolManager.getInstance().isPoolExistsForBtf( ORACLE_ALIAS ) )
            {
                registerPool();
            }

            cp = ConnectionPoolManager.getInstance().getConnectionPoolForBtf( ORACLE_ALIAS );

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

        Map oraclePro = cfg;

        String driverUrl = "jdbc:oracle:thin:@" + ( String ) oraclePro.get( "ip" ) + ":"
            + ( String ) oraclePro.get( "port" ) + ":" + ( String ) oraclePro.get( "dbName" );

        int mc = StringUtil.getIntValue( ( String ) oraclePro.get( "conn_count" ), 40 );

        if( mc < 30 )
        {
            mc = 30;
        }

        Properties info = new Properties();
        info.setProperty( "proxool.maximum-connection-count", mc + "" );
        info.setProperty( "proxool.minimum-connection-count", "20" );
        info.setProperty( "proxool.simultaneous-build-throttle", "20" );
        info.setProperty( "proxool.house-keeping-test-sql", "select CURRENT_DATE from dual" );
        info.setProperty( "proxool.maximum-active-time", "9999000" );
        info.setProperty( "autoReconnect", "true" );
        info.setProperty( "proxool.house-keeping-sleep-time", "60000" );
        info.setProperty( "proxool.test-before-use", "true" );
        info.setProperty( "user", ( String ) oraclePro.get( "user" ) );
        info.setProperty( "password", ( String ) oraclePro.get( "pw" ) );

        String url = "proxool." + ORACLE_ALIAS + ":" + ORACLE_DRIVER + ":" + driverUrl;
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
