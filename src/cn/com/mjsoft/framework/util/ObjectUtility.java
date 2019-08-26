package cn.com.mjsoft.framework.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.workflow.dao.vo.Workflow;
import cn.com.mjsoft.framework.persistence.core.annotation.Table;

/**
 * 框架用来创建java对象的工厂类
 * 
 * @author mjsoft
 * 
 */
public class ObjectUtility
{
    protected final static Logger log = Logger.getLogger( ObjectUtility.class );

    /**
     * 根据一个指定Class名称返回一个对象
     * 
     * @param className 指定Class名称
     * @return 一个对象
     * @throws Exception
     * 
     */
    public static Object buildObject( String className )
    {
        Class clazz;
        try
        {
            clazz = getClassInstance( className );
            return buildObject( clazz );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据一个指定Class返回一个对象
     * 
     * @param clazz 指定Class
     * @return 一个对象
     * @throws Exception
     * 
     */
    public static Object buildObject( Class clazz )
    {
        try
        {
            return clazz.newInstance();
        }
        catch ( InstantiationException e )
        {
            e.printStackTrace();
        }
        catch ( IllegalAccessException e )
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据一个指定Class名称和构造函数参数返回一个对象
     * 
     * @param className Class名称
     * @param params 构造函数参数
     * @return 一个对象
     * @throws Exception
     * 
     */
    public static Object buildObject( String className, Object[] params ) throws Exception
    {
        Class clazz = getClassInstance( className );
        return buildObject( clazz, params );
    }

    /**
     * 根据一个指定Class和构造函数参数返回一个对象
     * 
     * @param clazz Class
     * @param params 构造函数参数
     * @return 一个对象
     * @throws Exception
     * 
     */
    public static Object buildObject( Class clazz, Object[] params ) throws Exception
    {
        Object object = null;
        Constructor[] constructors = clazz.getConstructors();
        for ( int i = 0; i < constructors.length; i++ )
        {
            try
            {
                object = constructors[i].newInstance( params );
            }
            catch ( Exception e )
            {
                // 继续循环,以找到匹配的构造器
            }
        }
        if( null == object )// 构造对象失败,指定的Class无法匹配构造函数
            throw new InstantiationException();
        return object;
    }

    /**
     * 根据一个指定名称返回当前ClassLoader下的Class对象
     * 
     * @param className 指定类名
     * @return Class对象
     * @throws ClassNotFoundException
     * 
     */
    public static Class getClassInstance( String className )
    {
        return loadClass( className, ObjectUtility.class );
    }

    /**
     * 根据一个指定名称或一个ClassLoader下的Class来加载Class对象
     * 
     * @param className 指定类名
     * @param callingClass 指定Class对象
     * @return Class对象
     * @throws ClassNotFoundException
     * 
     */
    public static Class loadClass( String className, Class callingClass )
    {
        try
        {
            // 原代码在多线程环境下于产生了死锁,故替换掉
            // return Thread.currentThread().getContextClassLoader().loadClass(
            // className );
            return Class.forName( className );
        }
        catch ( ClassNotFoundException e )
        {
            try
            {
                return ObjectUtility.class.getClassLoader().loadClass( className );
            }
            catch ( ClassNotFoundException exc )
            {
                exc.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 在指定对象上取得某private类型字段的值。
     * 
     * @param field 要操作的字段
     * @param targetPojo 所属目标对象
     * @return
     */
    public static Object getPrivateFieldValue( Field field, Object targetPojo )
    {
        Object result = null;

        if( field == null )
        {
            log.error( "无法获取对应的Field!" );
            return null;
        }

        try
        {
            field.setAccessible( true );
            result = field.get( targetPojo );
        }
        catch ( IllegalArgumentException e )
        {
            log.error( e );
            e.printStackTrace();
        }
        catch ( IllegalAccessException e )
        {
            log.error( e );
            e.printStackTrace();
        }
        catch ( Exception e )
        {
            log.error( e );
            e.printStackTrace();
        }

        return result;
    }

    public static void setPrivateFieldValue( Field f, Object targetPojo, Object value )
    {
        if( f == null )
        {
            throw new NullPointerException();
        }

        f.setAccessible( true );

        try
        {
            f.set( targetPojo, value );
        }
        catch ( final IllegalArgumentException e )
        {
            log.error( e );
            e.printStackTrace();
        }
        catch ( final IllegalAccessException e )
        {
            log.error( e );
            e.printStackTrace();
        }

    }

    public static Object getPrivateFieldValue( String fieldName, Object target )
    {
        Class clazz = target.getClass();
        Field f = null;
        try
        {
            f = clazz.getDeclaredField( fieldName.trim() );
        }
        catch ( SecurityException e )
        {
            e.printStackTrace();
        }
        catch ( NoSuchFieldException e )
        {
            e.printStackTrace();
        }

        return getPrivateFieldValue( f, target );
    }

    /**
     * 通过包名获取包内所有类
     * 
     * @param pkg
     * @return
     */
    public static List<String> getAllClassByPackageName( Package pkg )
    {
        String packageName = pkg.getName();
        // 获取当前包下以及子包下所以的类
        List<String> returnClassList = getClasses( packageName );
        return returnClassList;
    }

    /**
     * 取得某一类所在包的所有类名 不含迭代
     */
    public static String[] getPackageAllClassName( String classLocation, String packageName )
    {
        // 将packageName分解
        String[] packagePathSplit = packageName.split( "[.]" );
        String realClassLocation = classLocation;
        int packageLength = packagePathSplit.length;
        for ( int i = 0; i < packageLength; i++ )
        {
            realClassLocation = realClassLocation + File.separator + packagePathSplit[i];
        }
        File packeageDir = new File( realClassLocation );
        if( packeageDir.isDirectory() )
        {
            String[] allClassName = packeageDir.list();
            return allClassName;
        }
        return null;
    }

    /**
     * 从包package中获取所有的Class
     * 
     * @param pack
     * @return
     */
    private static List<String> getClasses( String packageName )
    {

        // 第一个class类的集合
        List<String> classes = new ArrayList<String>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageDirName = packageName.replace( '.', '/' );
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try
        {
            dirs = Thread.currentThread().getContextClassLoader().getResources( packageDirName );
            // 循环迭代下去
            while ( dirs.hasMoreElements() )
            {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if( "file".equals( protocol ) )
                {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode( url.getFile(), "UTF-8" );
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile( packageName, filePath, recursive, classes );
                }
                else if( "jar".equals( protocol ) )
                {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    JarFile jar;
                    try
                    {
                        // 获取jar
                        jar = ( ( JarURLConnection ) url.openConnection() ).getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while ( entries.hasMoreElements() )
                        {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if( name.charAt( 0 ) == '/' )
                            {
                                // 获取后面的字符串
                                name = name.substring( 1 );
                            }
                            // 如果前半部分和定义的包名相同
                            if( name.startsWith( packageDirName ) )
                            {
                                int idx = name.lastIndexOf( '/' );
                                // 如果以"/"结尾 是一个包
                                if( idx != -1 )
                                {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring( 0, idx ).replace( '/', '.' );
                                }
                                // 如果可以迭代下去 并且是一个包
                                if( ( idx != -1 ) || recursive )
                                {
                                    // 如果是一个.class文件 而且不是目录
                                    if( name.endsWith( ".class" ) && !entry.isDirectory() )
                                    {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(
                                            packageName.length() + 1, name.length() - 6 );
                                        classes.add( packageName + '.' + className );

                                    }
                                }
                            }
                        }
                    }
                    catch ( IOException e )
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     * 
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    private static void findAndAddClassesInPackageByFile( String packageName, String packagePath,
        final boolean recursive, List<String> classes )
    {
        // 获取此包的目录 建立一个File
        File dir = new File( packagePath );
        // 如果不存在或者 也不是目录就直接返回
        if( !dir.exists() || !dir.isDirectory() )
        {
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles( new FileFilter()
        {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept( File file )
            {
                return ( recursive && file.isDirectory() )
                    || ( file.getName().endsWith( ".class" ) );
            }
        } );
        // 循环所有文件
        for ( File file : dirfiles )
        {
            // 如果是目录 则继续扫描
            if( file.isDirectory() )
            {
                findAndAddClassesInPackageByFile( packageName + "." + file.getName(), file
                    .getAbsolutePath(), recursive, classes );
            }
            else
            {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring( 0, file.getName().length() - 6 );

                String classFn = packageName + '.' + className;

                if( classFn.startsWith( "." ) )
                {
                    classFn = StringUtil.subString( classFn, 1, classFn.length() );

                }

                classes.add( classFn );

            }
        }
    }

    public static void main( String args[] )
    { }

}
