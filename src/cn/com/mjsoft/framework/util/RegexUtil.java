package cn.com.mjsoft.framework.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.exception.FrameworkException;

/**
 * 正则表达式工具
 * 
 * @author mjsoft
 * 
 */
public class RegexUtil
{
    private static Logger log = Logger.getLogger( RegexUtil.class );

    private static PatternCompiler compiler = new Perl5Compiler();

    private static Perl5Matcher matcher = new Perl5Matcher();

    /**
     * 对target进行正则匹配
     * 
     * @param target
     * @param reg
     * @return
     */
    public static boolean match( String target, String reg )
    {
        log.info( "[RegexUtil] match() reg:" + reg );

        target = StringUtil.replaceString( target, "\\", "\\\\", false, false );

        Pattern pattern = null;

        try
        {
            pattern = compiler.compile( reg,
                Perl5Compiler.CASE_INSENSITIVE_MASK );

            log
                .info( "[RegexUtil] match:"
                    + matcher.contains( target, pattern ) );

            return matcher.contains( target, pattern );
        }
        catch ( MalformedPatternException e )
        {
            e.printStackTrace();

            log.error( e );
            throw new FrameworkException( "正则表达式错误! reg:" + reg );
        }
    }

    /**
     * 对target进行正则匹配,返回结果
     * 
     * @param target
     * @param reg
     * @return
     */
    public static List matchRes( String target, String reg )
    {
        log.info( "[RegexUtil] matchRes() reg:" + reg );

        target = StringUtil.replaceString( target, "\\", "\\\\", false, false );

        Pattern pattern = null;

        PatternMatcherInput input = new PatternMatcherInput( target );

        try
        {
            pattern = compiler.compile( reg,
                Perl5Compiler.CASE_INSENSITIVE_MASK );

            log.info( "[RegexUtil] matchRes:"
                + matcher.contains( target, pattern ) );

            List res = new ArrayList();
            while ( matcher.contains( input, pattern ) )
            {
                String re = matcher.getMatch().toString();

                re = StringUtil.replaceString( re, "\\\\", "\\", false, false );

                re = StringUtil.replaceString( re, "//", "/", false, false );
                
                
                re = StringUtil.replaceString( re, "http:/" ,"http://" , false, false );
                
                re = StringUtil.replaceString( re, "https:/" ,"https://" , false, false );
                

                res.add( re );
            }

            return res;
        }
        catch ( MalformedPatternException e )
        {
            e.printStackTrace();

            log.error( e );
            throw new FrameworkException( "正则表达式错误! reg:" + reg );
        }
    }

    public static void main( String args[] )
    {
    }

    private static String disposeUrlReg( String config )
    {
        config = StringUtil.replaceString( config, Constant.PICK.REG_W_FLAG,
            Constant.PICK.REG_W, false, false );

        config = StringUtil.replaceString( config, Constant.PICK.REG_D_FLAG,
            Constant.PICK.REG_D, false, false );

        config = StringUtil.replaceString( config, Constant.PICK.REG_C_FLAG,
            Constant.PICK.REG_C, false, false );

        config = StringUtil
            .replaceString( config, Constant.PICK.REG_NOT_D_FLAG,
                Constant.PICK.REG_NOT_D, false, false );

        config = StringUtil.replaceString( config, Constant.PICK.REG_ANY_FLAG,
            Constant.PICK.REG_ANY, false, false );

        String endRegexStr = null;
        // 去掉reg范围分割符号
        endRegexStr = StringUtil.replaceString( config,
            Constant.PICK.START_REG, "", false, false );

        endRegexStr = StringUtil.replaceString( endRegexStr,
            Constant.PICK.END_REG, "", false, false );

        // 替换reg次数分割符号
        endRegexStr = StringUtil.replaceString( endRegexStr,
            Constant.PICK.START_GREEDY, Constant.PICK.START_REG, false, false );

        endRegexStr = StringUtil.replaceString( endRegexStr,
            Constant.PICK.END_GREEDY, Constant.PICK.END_REG, false, false );

        return endRegexStr;

    }
}
