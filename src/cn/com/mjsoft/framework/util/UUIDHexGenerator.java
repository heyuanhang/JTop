package cn.com.mjsoft.framework.util;

import java.net.InetAddress;
import java.util.Random;

/**
 * 产生UUID序列
 * 
 * @author from Hibernate
 * 
 */
public class UUIDHexGenerator
{

    private static final int IP;

    private static final Random random;

    static
    {
        int ipadd;
        try
        {
            ipadd = toInt( InetAddress.getLocalHost().getAddress() );
        }
        catch ( Exception e )
        {
            ipadd = 0;
        }
        IP = ipadd;
        random = new Random();
    }

    private static short counter = ( short ) 0;

    private static final int JVM = ( int ) ( System.currentTimeMillis() >>> 8 );

    private String sep = "";

    protected String format( int intval )
    {
        String formatted = Integer.toHexString( intval );
        StringBuffer buf = new StringBuffer( "00000000" );
        buf.replace( 8 - formatted.length(), 8, formatted );
        return buf.toString();
    }

    protected String format( short shortval )
    {
        String formatted = Integer.toHexString( shortval );
        StringBuffer buf = new StringBuffer( "0000" );
        buf.replace( 4 - formatted.length(), 4, formatted );
        return buf.toString();
    }

    public String generate()
    {
        return new StringBuffer( 40 ).append( format( getIP() ) ).append( sep )
            .append( format( getJVM() ) ).append( sep ).append(
                random.nextInt( 1000 ) ).append( format( getHiTime() ) )
            .append( sep ).append( format( getLoTime() ) ).append( sep )
            .append( format( getCount() ) ).toString();
    }

    public static int toInt( byte[] bytes )
    {
        int result = 0;
        for ( int i = 0; i < 4; i++ )
        {
            result = ( result << 8 ) - Byte.MIN_VALUE + ( int ) bytes[i];
        }
        return result;
    }

    protected int getJVM()
    {
        return JVM;
    }

    protected short getCount()
    {
        synchronized ( UUIDHexGenerator.class )
        {
            if( counter < 0 )
                counter = 0;
            return counter++;
        }
    }

    protected int getIP()
    {
        return IP;
    }

    protected short getHiTime()
    {
        return ( short ) ( System.currentTimeMillis() >>> 32 );
    }

    protected int getLoTime()
    {
        return ( int ) System.currentTimeMillis();
    }

    public static void main( String[] args ) throws Exception
    { }

}
