package cn.com.mjsoft.framework.util;

/**
 * 
 * @author mjsoft
 * 
 */
public class Chrysalides
{

    private Chrysalides()
    {

    }

    public static void Fake( Exception e )
    {
        e.toString();
    }

    public static Throwable butterfly( Throwable throwable, Throwable throwable1 )
    {
        try
        {
            throwable.getClass().getMethod( "initCause",
                new Class[] { java.lang.Throwable.class } ).invoke( throwable,
                new Object[] { throwable1 } );
        }
        catch ( Exception exception )
        {
        }
        return throwable;
    }

}
