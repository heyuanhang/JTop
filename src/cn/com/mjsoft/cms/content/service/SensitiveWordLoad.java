package cn.com.mjsoft.cms.content.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.mjsoft.cms.common.datasource.MySqlDataSource;
import cn.com.mjsoft.cms.content.dao.ContentDao;
import cn.com.mjsoft.framework.persistence.core.PersistenceEngine;
import cn.com.mjsoft.framework.util.StringUtil;

/**
 * @Description: 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 * 
 * @Author : 第三方
 * 
 */
@SuppressWarnings( { "rawtypes", "unchecked" } )
public class SensitiveWordLoad
{
    private ContentDao contentDao = new ContentDao( new PersistenceEngine( new MySqlDataSource() ) );

    private String ENCODING = "GBK"; // 字符编码

    public HashMap sensitiveWordMap;

    public SensitiveWordLoad()
    {
        super();
    }

    public Map initKeyWord()
    {
        try
        {
            // 读取敏感词库
            Set<String> keyWordSet = readSensitiveWordFile();
            // 将敏感词库加入到HashMap中
            addSensitiveWordToHashMap( keyWordSet );
             
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        return sensitiveWordMap;
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
     * 中 = { isEnd = 0 国 = {<br>
     * isEnd = 1 人 = {isEnd = 0 民 = {isEnd = 1} } 男 = { isEnd = 0 人 = { isEnd =
     * 1 } } } } 五 = { isEnd = 0 星 = { isEnd = 0 红 = { isEnd = 0 旗 = { isEnd = 1 } } } }
     * 
     * 
     */

    private void addSensitiveWordToHashMap( Set<String> keyWordSet )
    {
        sensitiveWordMap = new HashMap( keyWordSet.size() ); // 初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        // 迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while ( iterator.hasNext() )
        {
            key = iterator.next(); // 关键字
            nowMap = sensitiveWordMap;
            for ( int i = 0; i < key.length(); i++ )
            {
                char keyChar = key.charAt( i ); // 转换成char型
                Object wordMap = nowMap.get( keyChar ); // 获取

                if( wordMap != null )
                { // 如果存在该key，直接赋值
                    nowMap = ( Map ) wordMap;
                }
                else
                { // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String, String>();
                    newWorMap.put( "isEnd", "0" ); // 不是最后一个
                    nowMap.put( keyChar, newWorMap );
                    nowMap = newWorMap;
                }

                if( i == key.length() - 1 )
                {
                    nowMap.put( "isEnd", "1" ); // 最后一个
                }
            }
        }
    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     * 
     * 
     */
    @SuppressWarnings( "resource" )
    private Set<String> readSensitiveWordFile() throws Exception
    {
        Set<String> set = new HashSet<String>();
//
//        File file = new File( "D:\\ss.txt" ); // 读取文件
//        InputStreamReader read = new InputStreamReader( new FileInputStream( file ), ENCODING );
//        try
//        {
//            if( file.isFile() && file.exists() )
//            { // 文件流是否存在
//                
//                BufferedReader bufferedReader = new BufferedReader( read );
//                String txt = null;
//                while ( ( txt = bufferedReader.readLine() ) != null )
//                { // 读取文件，将文件内容放入到set中
//                    set.add( txt );
//                }
//            }
//            else
//            { // 不存在抛出异常信息
//                throw new Exception( "FileError : 敏感词库文件不存在" );
//            }
//        }
//        catch ( Exception e )
//        {
//            throw e;
//        }
//        finally
//        {
//            read.close(); // 关闭文件流
//        }

        /**
         * 从数据库读取
         */

        List swList = contentDao.querySensitiveWord();

        String sw = null;

        Map swInfo = null;

        for ( int i = 0; i < swList.size(); i++ )
        {
            swInfo = ( Map ) swList.get( i );

            sw = ( String ) swInfo.get( "sensitiveStr" );

            if( StringUtil.isStringNotNull( sw ) )
            {
                String[] words = StringUtil.split( sw, "," );

                String word = null;

                for ( int j = 0; j < words.length; j++ )
                {
                    word = words[j];

                    set.add( word );
                }
            }
        }

        return set;
    }
}
