package cn.com.mjsoft.framework.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import cn.com.mjsoft.framework.config.SystemRuntimeConfig;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;

/**
 * 字符工具类
 * 
 * @author mjsoft
 * 
 */
public class StringUtil
{
    private static Logger log = Logger.getLogger( StringUtil.class );

    private static SystemRuntimeConfig config = SystemConfiguration.getInstance().getSystemConfig();

    private static UUIDHexGenerator gen = new UUIDHexGenerator();

    /**
     * String是否为空
     * 
     * @param input
     * @return
     */
    public static boolean isStringNull( String input )
    {
        if( null == input || input.trim().equals( "" ) )
        {
            return true;
        }
        return false;
    }

    public static boolean isStringNotNull( String input )
    {
        if( null == input || input.trim().equals( "" ) )
        {
            return false;
        }
        return true;
    }

    /**
     * 是否为比较运算符
     * 
     * @param input
     * @return
     */
    public static boolean isCompare( String input )
    {
        boolean flag = false;
        if( isStringNull( input ) )
        {
            return false;
        }
        String str = input.trim();
        if( str.equals( "<" ) || str.equals( ">" ) || str.equals( ">=" ) || str.equals( "<=" )
            || str.equals( "=" ) || str.equals( "==" ) )
        {
            flag = true;
        }
        return flag;
    }

    /**
     * 将String转换成int，转换失败用replace替代
     * 
     * @param intStr
     * @param replace
     * @return int
     */
    public static int getIntValue( String intStr, int replacement )
    {
        if( intStr == null )
        {
            return replacement;
        }

        int returnValue;
        try
        {
            returnValue = Integer.parseInt( intStr.trim() );
        }
        catch ( NumberFormatException e )
        {
            returnValue = replacement;// 转换失败的话用替代值
        }
        return returnValue;
    }

    /**
     * 将String转换成long，出现转换错误用replace替代
     * 
     * @param longStr
     * @param replace
     * @return long
     */
    public static long getLongValue( String longStr, long replacement )
    {
        if( longStr == null )
        {
            return replacement;
        }

        long returnValue;
        try
        {
            returnValue = Long.parseLong( longStr.trim() );
        }
        catch ( NumberFormatException e )
        {
            returnValue = replacement;// 转换失败的话用替代值
        }
        return returnValue;
    }

    /**
     * 将String转换成boolean，出现转换错误用replace替代
     * 
     * @param boolStr
     * @param replacement
     * @return
     */
    public static boolean getBooleanValue( String boolStr, boolean replacement )
    {
        if( boolStr == null )
        {
            return replacement;
        }

        boolean returnValue;
        try
        {
            returnValue = Boolean.parseBoolean( boolStr.trim() );
        }
        catch ( NumberFormatException e )
        {
            returnValue = replacement;// 转换失败的话用替代值
        }
        return returnValue;
    }

    /**
     * 将String转换成double，出现转换错误用replace替代
     * 
     * @param longStr
     * @param replace
     * @return double
     */
    public static double getDoubleValue( String doubleStr, double replacement )
    {
        if( doubleStr == null )
        {
            return replacement;
        }

        double returnValue;
        try
        {
            returnValue = Double.parseDouble( doubleStr.trim() );
        }
        catch ( NumberFormatException e )
        {
            returnValue = replacement;// 转换失败的话用替代值
        }
        return returnValue;
    }

    /**
     * 将String转换成float，出现转换错误用replace替代
     * 
     * @param longStr
     * @param replace
     * @return double
     */
    public static float getFloatValue( String fStr, float replacement )
    {
        if( fStr == null )
        {
            return replacement;
        }

        float returnValue;
        try
        {
            returnValue = Float.parseFloat( fStr.trim() );
        }
        catch ( NumberFormatException e )
        {
            returnValue = replacement;// 转换失败的话用替代值
        }
        return returnValue;
    }

    /**
     * 字符串不可为null或""
     * 
     * @param str
     * @param replace
     * @return String
     */
    public static String getNotNullString( String str, String replacement )
    {
        if( null == str || str.trim().equals( "" ) )
        {
            return replacement;
        }
        return str.trim();
    }

    /**
     * 空String返回""，防止null存在
     * 
     * @param str
     * @return String
     */
    public static String getNotNullString( String str )
    {
        if( null == str || str.trim().equals( "" ) )
        {
            return "";
        }
        return str.trim();
    }

    /**
     * 是数字字符串吗？
     * 
     * @param str
     * @param boolean true：负值不合法 false:合法
     * @return
     */
    public static boolean isNum( String str, boolean minus )
    {
        boolean isnum = true;
        try
        {
            int value = Integer.parseInt( str.trim() );
            if( value < 0 && minus )// 负值是否合法
                return false;
        }
        catch ( NumberFormatException e )
        {
            return false;
        }
        return isnum;
    }

    public static String notNull( String taregt )
    {
        String end = taregt;
        if( taregt == null )
        {
            end = "";
        }
        return end;
    }

    /**
     * 
     * /** 按指定的规则替换选定的字符串
     * 
     * @param tragetString
     * @param replaceIt
     * @param replacement
     * @return
     */
    public static String replaceString( String tragetString, String replaceIt, String replacement )
    {
        return replaceString( tragetString, replaceIt, replacement, false, false );
    }

    /**
     * 按指定的规则替换选定的字符串
     * 
     * @param tragetString 目标字符
     * @param replaceIt 查询字符
     * @param replacement 替换的字符
     * @param toLowerCase 大小写匹配: true - 不区分大小写, flase - 严格匹配大小写
     * @param prefixMode 是否只替换第一次出现的前缀 : true - 只替换前缀, false - 替换所有字符
     * @return
     */
    public static String replaceString( String tragetString, String replaceIt, String replacement,
        boolean toLowerCase, boolean prefixMode )
    {
        if( tragetString == null || replaceIt == null || replacement == null )
        {
            return tragetString;
        }

        StringBuffer buf = new StringBuffer();
        int legthOfSource = tragetString.length();
        int legthOfReplaceIt = replaceIt.length();
        int postionStart = 0;
        int currentPos;// 每次找到的要替换的字符串的位置

        String tmpStr;
        String tmpReplace;
        if( toLowerCase )
        {
            tmpStr = tragetString.toLowerCase();
            tmpReplace = replaceIt.toLowerCase();
        }
        else
        {
            tmpStr = tragetString;
            tmpReplace = replaceIt;
        }

        while ( ( currentPos = ( tmpStr.indexOf( tmpReplace, postionStart ) ) ) >= 0 )
        {// 当能找到
            buf.append( new String( tragetString.substring( postionStart, currentPos ) ) );// 替换的是真实字符串
            buf.append( replacement );// 替换
            if( prefixMode )
            {
                buf.append( new String( tragetString.substring( tragetString.indexOf( replaceIt )
                    + replaceIt.length() ) ) );
                return buf.toString();
            }
            postionStart = currentPos + legthOfReplaceIt;// 位置移动
        }

        if( postionStart < legthOfSource )
        {// 如果source中已没有需要替换的字符串存在且没有到尾部，就要将剩下的并入结果
            buf.append( new String( tragetString.substring( postionStart ) ) );
        }

        return buf.toString();

    }

    /**
     * 替换所有的空格
     * 
     * @param source
     * @return String
     */
    public static String replaceAllSpace( String source )
    {
        return replaceString( source, " ", "", false, false );
    }

    /**
     * 将全角符号转换为半角符号。
     * 
     * @param input
     * @return
     */
    public static String replaceAlltoHalf( String input )
    {

        HashMap map = new HashMap();
        map.put( "，", "," );
        map.put( "。", "." );
        map.put( "〈", "<" );
        map.put( "〉", ">" );
        map.put( "‖", "|" );
        map.put( "《", "<" );
        map.put( "》", ">" );
        map.put( "〔", "[" );
        map.put( "〕", "]" );
        map.put( "﹖", "?" );
        map.put( "？", "?" );
        map.put( "“", "\"" );
        map.put( "”", "\"" );
        map.put( "：", ":" );
        map.put( "、", "," );
        map.put( "（", "(" );
        map.put( "）", ")" );
        map.put( "【", "[" );
        map.put( "】", "]" );
        map.put( "—", "-" );
        map.put( "～", "~" );
        map.put( "！", "!" );
        map.put( "‵", "'" );
        map.put( "①", "1" );
        map.put( "②", "2" );
        map.put( "③", "3" );
        map.put( "④", "4" );
        map.put( "⑤", "5" );
        map.put( "⑥", "6" );
        map.put( "⑦", "7" );
        map.put( "⑧", "8" );
        map.put( "⑨", "9" );

        int length = input.length();
        for ( int i = 0; i < length; i++ )
        {
            String charat = new String( input.substring( i, i + 1 ) );
            if( null != map.get( charat ) )
            {
                input = input.replace( charat, ( String ) map.get( charat ) );
            }
        }
        return input;
    }

    public static String getRandomUUIDString()
    {
        UUID uu = UUID.randomUUID();
        String str = uu.toString();

        return replaceString( str, "-", "", false, false );
    }

    public static String getUUIDString()
    {
        return gen.generate();
    }

    public static String[] getCheckBoxValue( Object checkParam )
    {
        if( checkParam instanceof String[] )
        {
            return ( String[] ) checkParam;
        }
        else if( checkParam instanceof String )
        {
            return new String[] { ( String ) checkParam };
        }
        else
        {
            return new String[] {};
        }
    }

    /**
     * 将Array中的所有值转换为按照分隔符区分的String
     * 
     * @param checkParam
     * @param split
     * @return
     */
    public static String getStringFromStringOrArray( Object checkParam, String split,
        boolean utf8Mode )
    {
        if( checkParam instanceof String )
        {
            if( utf8Mode )
            {
                try
                {
                    return URLDecoder.decode( ( String ) checkParam, "UTF-8" );
                }
                catch ( UnsupportedEncodingException e )
                {
                    e.printStackTrace();
                }
            }
            else
            {
                return ( String ) checkParam;
            }
        }
        else if( checkParam instanceof String[] )
        {
            StringBuffer buf = new StringBuffer();
            String[] tmp = ( String[] ) checkParam;
            for ( int i = 0; i < tmp.length; i++ )
            {
                buf.append( tmp[i] );

                if( i + 1 != tmp.length )
                {
                    buf.append( split );
                }
            }
            return buf.toString();
        }

        return "";
    }

    /**
     * 返回给定字符串中出现的某字符串次数
     * 
     * @param targetStr
     * @param repeat
     * @return
     */
    public static int getRepeatCharLength( String targetStr, String repeat )
    {
        int index = 0;

        int length = 0;

        int indexVal = targetStr.indexOf( repeat, index );
        while ( indexVal != -1 )
        {
            length++;

            indexVal = targetStr.indexOf( repeat, indexVal + 1 );
        }

        return length;
    }

    /**
     * 将URL中传递的中文参数转化为系统默认编码
     * 
     * @param target
     * @return
     */
    public static String formatURLChineseParam( String target )
    {
        // String xxx = config.getSystemConfig().getEncoding();

        log.info( "[formatURLChineseParam] target:" + "'" );

        try
        {
            return new String( target.getBytes( "ISO-8859-1" ), config.getEncoding() ).trim();
        }
        catch ( UnsupportedEncodingException e )
        {
            e.printStackTrace();
            log.error( "参数中包含不支持的字符" );
        }

        return target;
    }

    /**
     * 将URL中传递的中文参数转化为指定的编码
     * 
     * @param target
     * @param enc
     * @return
     */
    public static String formatURLChineseParam( String target, String enc )
    {
        try
        {
            return new String( target.getBytes( "ISO-8859-1" ), enc );
        }
        catch ( UnsupportedEncodingException e )
        {
            e.printStackTrace();
            log.error( "参数中包含不支持的字符" );
        }

        return target;
    }

    /**
     * 生成一个简体中文汉字字符
     * 
     * @return
     */
    public static String genChineseChar()
    {
        String str = null;

        int hightPos, lowPos; // 定义高低位
        Random random0 = new Random();
        Random random = new Random( random0.nextInt( 1000000 ) );
        hightPos = ( 176 + Math.abs( random.nextInt( 39 ) ) );

        lowPos = ( 161 + Math.abs( random.nextInt( 93 ) ) );

        byte[] b = new byte[2];

        b[0] = ( new Integer( hightPos ).byteValue() );

        b[1] = ( new Integer( lowPos ).byteValue() );

        try
        {
            str = new String( b, "gbk" );
        }
        catch ( UnsupportedEncodingException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return str;
    }

    /**
     * 将Map中的元素按照value值的比较规则进行排序
     * 
     * @param map
     * @param reverse
     * @return
     */
    public static Map sortByValue( Map map, final boolean reverse )
    {
        List list = new LinkedList( map.entrySet() );
        Collections.sort( list, new Comparator()
        {
            public int compare( Object o1, Object o2 )
            {
                if( reverse )
                {
                    return -( ( Comparable ) ( ( Map.Entry ) o1 ).getValue() )
                        .compareTo( ( ( Map.Entry ) o2 ).getValue() );
                }
                return ( ( Comparable ) ( ( Map.Entry ) o1 ).getValue() )
                    .compareTo( ( ( Map.Entry ) o2 ).getValue() );
            }
        } );

        Map result = new LinkedHashMap();
        for ( Iterator it = list.iterator(); it.hasNext(); )
        {
            Map.Entry entry = ( Map.Entry ) it.next();
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }

    /**
     * 返回一个List,这个List包含了所有匹配start和end之间的子字符串.
     * 
     * @param target
     * @param start
     * @param end
     * @return
     */
    @SuppressWarnings( "unchecked" )
    public static List subStringToList( String target, String start, String end )
    {
        if( target == null || start == null || end == null )
        {
            return null;
        }

        List result = new ArrayList();

        int prevPos = 0;

        int startPos = 0;

        int endPos = 0;

        while ( true )
        {
            startPos = target.indexOf( start, prevPos );

            endPos = target.indexOf( end, startPos );

            if( startPos == -1 || endPos == -1 )
            {
                break;
            }

            result.add( new String( target.substring( startPos + 1, endPos ) ) );

            prevPos = endPos;
        }

        return result;
    }

    /**
     * 此方法防止JDK同名方法内存泄露
     * 
     * @param target
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public static String subString( String target, int beginIndex, int endIndex )
    {
        return new String( target.substring( beginIndex, endIndex ) );
    }

    /**
     * 此方法防止JDK(1.4,1.5,1.6)同名方法内存泄露
     * 
     * @param target
     * @param beginIndex
     * @param endIndex
     * @return 新的安全String
     */
    public static String subString( String target, int beginIndex )
    {
        return new String( target.substring( beginIndex ) );
    }

    /**
     * 此方法防止JDK(1.4,1.5,1.6)同名方法内存泄露
     * 
     * @param target
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public static String[] split( String target, String splitChar )
    {
        String[] tmp = target.split( splitChar );
        String[] reArray = new String[tmp.length];

        for ( int i = 0; i < tmp.length; i++ )
        {
            reArray[i] = new String( tmp[i] );
        }

        return reArray;
    }

    public static List getSubString( String oriStr, int subStrLen )
    {
        ArrayList list = new ArrayList();
        byte[] subStrBytes = oriStr.getBytes();
        int subStrIndex = 0;
        int index = 0;
        while ( subStrIndex < subStrBytes.length )
        {
            int len = subStrLen;
            if( subStrIndex + len > subStrBytes.length )
            {
                len = subStrBytes.length - subStrIndex;
            }
            String subStr = new String( subStrBytes, subStrIndex, len );
            if( subStr.charAt( subStr.length() - 1 ) != oriStr.charAt( index + subStr.length() - 1 ) )
            {
                subStr = subStr.substring( 0, subStr.length() - 1 );
                len = subStr.getBytes().length;
            }
            if( len <= 0 )
            {
                return null;
            }
            subStrIndex += len;
            index += subStr.length();
            list.add( subStr );
        }
        // String[] subStrs = ( String[] ) list.toArray( new String[list.size()]
        // );
        return list;
    }

    /**
     * 将包含分隔符的String转换为List
     * 
     * @param str
     * @param splitFlag
     * @return
     */
    public static List<String> changeStringToList( String str, String splitFlag )
    {
        List strList = new ArrayList();

        if( isStringNotNull( str ) )
        {
            String[] temp = split( str, splitFlag );

            if( temp != null )
            {
                for ( int i = 0; i < temp.length; i++ )
                {
                    strList.add( temp[i] );
                }
            }
        }

        return strList;
    }

    /**
     * 将String[]转换为分隔符区分的字符串
     * 
     * @param str
     * @param splitFlag
     * @return
     */
    public static String changeStringArrayToStr( Object[] strArray, String splitFlag )
    {
        StringBuffer buf = new StringBuffer();

        if( strArray != null )
        {

            for ( int i = 0; i < strArray.length; i++ )
            {
                if( i + 1 != strArray.length )
                {
                    buf.append( strArray[i].toString() + splitFlag );
                }
                else
                {
                    buf.append( strArray[i].toString() );
                }
            }

        }

        return buf.toString();
    }

    /**
     * 将strArray中的str组合为aa flag bb flag ....的形式
     * 
     * @param flag
     * @return
     */
    public static String join( String[] strArray, String flag )
    {
        if( strArray == null )
        {
            return "";
        }

        String str = null;
        StringBuffer buf = new StringBuffer();
        for ( int i = 0; i < strArray.length; i++ )
        {
            str = strArray[i];

            if( isStringNotNull( str ) )
            {
                if( i + 1 != strArray.length )
                {
                    buf.append( str ).append( flag );
                }
                else
                {
                    buf.append( str );
                }
            }
        }

        return buf.toString();
    }

    /**
     * 将List中的Bean或Map(k-v对应字段名称和值)转换为JSon格式
     * 
     * @param beanList
     * @return
     */
    public static Map changeJSON( List beanList, String pefix )
    {
        if( beanList == null )
        {
            return null;
        }

        Map jsonMap = new TreeMap();

        Object jsonBean = null;

        for ( int i = 0; i < beanList.size(); i++ )
        {
            jsonBean = beanList.get( i );
            jsonMap.put( pefix + Integer.valueOf( i ), jsonBean );
        }

         

        return jsonMap;

    }

    /**
     * 将Map中的k-v或Bean转换为JSon格式
     * 
     * @param beanList
     * @return
     */
    public static String changeMapToJSON( Object valMap )
    {
        if( valMap == null )
        {
            return null;
        }

        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try
        {
            mapper.writeValue( writer, valMap );
            json = writer.toString();
        }
        catch ( JsonGenerationException e )
        {
            e.printStackTrace();
        }
        catch ( JsonMappingException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                writer.close();
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }

        return json;

    }

    public static Object changeStringToObject( String target, String type )
    {
        if( "I".equals( type ) )
        {
            return Integer.valueOf( target );
        }
        else if( "L".equals( type ) )
        {
            return Long.valueOf( target );
        }
        else if( "F".equals( type ) )
        {
            return Float.valueOf( target );
        }
        else if( "DB".equals( type ) )
        {
            return Double.valueOf( target );
        }
        else if( "T".equals( type ) )
        {
            return Timestamp.valueOf( target );
        }
        else
        {
            return target;
        }
    }

    static final int GB_SP_DIFF = 160;
    // 存放国标一级汉字不同读音的起始区位码
    static final int[] secPosValueList = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787,
        3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5600 };
    // 存放国标一级汉字不同读音的起始区位码对应读音
    static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x', 'y', 'z' };

    // 获取一个字符串的拼音码
    public static String getFirstLetter( String oriStr )
    {
        String str = oriStr.toLowerCase();
        StringBuffer buffer = new StringBuffer();
        char ch;
        char[] temp;
        for ( int i = 0; i < str.length(); i++ )
        { // 依次处理str中每个字符
            ch = str.charAt( i );
            temp = new char[] { ch };
            byte[] uniCode = new String( temp ).getBytes();
            if( uniCode[0] < 128 && uniCode[0] > 0 )
            { // 非汉字
                buffer.append( temp );
            }
            else
            {
                buffer.append( convert( uniCode ) );
            }
        }
        return buffer.toString();
    }

    // 获取一个汉字的拼音码
    public static Character getFirstPY( char ch )
    {

        byte[] uniCode = null;
        try
        {
            uniCode = String.valueOf( ch ).getBytes( "GBK" );
        }
        catch ( UnsupportedEncodingException e )
        {
            e.printStackTrace();
            return null;
        }

        if( ( ( ( byte ) uniCode[0] ) & 0xff ) < 128 && ( ( ( byte ) uniCode[0] ) & 0xff ) > 0 )
        { // 非汉字
            return new Character( ch );
        }
        else
        {
            return convert( uniCode );
        }
    }

    public static Character convert( byte[] bytes )
    {
        char result = '-';
        int secPosValue = 0;
        int i;
        for ( i = 0; i < bytes.length; i++ )
        {
            bytes[i] -= GB_SP_DIFF;
        }
        secPosValue = bytes[0] * 100 + bytes[1];
        for ( i = 0; i < 23; i++ )
        {
            if( secPosValue >= secPosValueList[i] && secPosValue < secPosValueList[i + 1] )
            {
                result = firstLetter[i];
                break;
            }
        }
        return new Character( result );
    }

    /**
     * 对指定配置文件设定值
     * 
     * @param filePath
     * @param parameterKey
     * @param parameterValue
     */
    public static void setProperty( String filePath, String parameterKey, String parameterValue )
    {
        Properties prop = new Properties();

        InputStream in = null;
        FileOutputStream outPut = null;

        try
        {
            in = new FileInputStream( filePath );

            prop.load( in );
            outPut = new FileOutputStream( filePath );
            prop.setProperty( parameterKey, parameterValue );
            prop.store( outPut, filePath );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if( in != null )
                {
                    in.close();
                }

                if( outPut != null )
                {
                    outPut.close();
                }
            }
            catch ( IOException e )
            {

                e.printStackTrace();
            }
        }
    }

    public static void readTheLastLineText()
    {
        // 使用RandomAccessFile , 从后找最后一行数据
        RandomAccessFile raf;
        try
        {
            raf = new RandomAccessFile( "F:/cms_core.log", "r" );
            long len = raf.length();
            String lastLine = "";
            long pos = 0;
            if( len != 0L )
            {
                pos = len - 1;
                while ( pos > 0 && pos > 11390854 )
                {
                    pos--;
                    raf.seek( pos );
                    if( raf.readByte() == '\n' )
                    {
                        lastLine = raf.readLine();
                        System.out.println( "pos:" + pos + "   "
                            + new String( lastLine.getBytes( "iso8859_1" ), "GBK" ) );
                        // break;
                    }
                }
            }
            raf.close();
            System.out.println( "pos:" + pos + "   "
                + new String( lastLine.getBytes( "iso8859_1" ), "GBK" ) );
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static String haveChinaSheng( String target )
    {
        if( isStringNull( target ) )
        {
            return null;
        }

        if( target.indexOf( "安徽" ) != -1 )
        {
            return "安徽";
        }
        else if( target.indexOf( "北京" ) != -1 )
        {
            return "北京";
        }
        else if( target.indexOf( "福建" ) != -1 )
        {
            return "福建";
        }
        else if( target.indexOf( "甘肃" ) != -1 )
        {
            return "甘肃";
        }
        else if( target.indexOf( "广东" ) != -1 )
        {
            return "广东";
        }
        else if( target.indexOf( "广西" ) != -1 )
        {
            return "广西";
        }
        else if( target.indexOf( "贵州" ) != -1 )
        {
            return "贵州";
        }
        else if( target.indexOf( "海南" ) != -1 )
        {
            return "海南";
        }
        else if( target.indexOf( "河北" ) != -1 )
        {
            return "河北";
        }
        else if( target.indexOf( "河南" ) != -1 )
        {
            return "河南";
        }
        else if( target.indexOf( "黑龙江" ) != -1 )
        {
            return "黑龙江";
        }
        else if( target.indexOf( "湖北" ) != -1 )
        {
            return "湖北";
        }
        else if( target.indexOf( "福建" ) != -1 )
        {
            return "福建";
        }
        else if( target.indexOf( "湖南" ) != -1 )
        {
            return "湖南";
        }
        else if( target.indexOf( "吉林" ) != -1 )
        {
            return "吉林";
        }
        else if( target.indexOf( "江苏" ) != -1 )
        {
            return "江苏";
        }
        else if( target.indexOf( "江西" ) != -1 )
        {
            return "江西";
        }
        else if( target.indexOf( "辽宁" ) != -1 )
        {
            return "辽宁";
        }
        else if( target.indexOf( "内蒙古" ) != -1 )
        {
            return "内蒙古";
        }
        else if( target.indexOf( "宁夏" ) != -1 )
        {
            return "宁夏";
        }
        else if( target.indexOf( "青海" ) != -1 )
        {
            return "青海";
        }
        else if( target.indexOf( "山东" ) != -1 )
        {
            return "山东";
        }
        else if( target.indexOf( "山西" ) != -1 )
        {
            return "山西";
        }
        else if( target.indexOf( "陕西" ) != -1 )
        {
            return "陕西";
        }
        else if( target.indexOf( "上海" ) != -1 )
        {
            return "上海";
        }
        else if( target.indexOf( "四川" ) != -1 )
        {
            return "四川";
        }
        else if( target.indexOf( "天津" ) != -1 )
        {
            return "天津";
        }
        else if( target.indexOf( "西藏" ) != -1 )
        {
            return "西藏";
        }
        else if( target.indexOf( "新疆" ) != -1 )
        {
            return "新疆";
        }
        else if( target.indexOf( "云南" ) != -1 )
        {
            return "云南";
        }
        else if( target.indexOf( "浙江" ) != -1 )
        {
            return "浙江";
        }
        else if( target.indexOf( "重庆" ) != -1 )
        {
            return "重庆";
        }

        return null;

    }

    public static void main( String[] args )
    { }

}
