package cn.com.mjsoft.framework.util;

import java.util.ArrayList;

/**
 * 对表单进行抽象数据类型的验证，以List的形式返回错误链，当存在错误时command不应该再执行下去
 *  *
 * @author mjsoft
 */
public class Validate
{
    private ArrayList errorList = new ArrayList();

    /**
     * @param errorList
     */
    public Validate()
    {
        this.errorList.clear();
    }

    /**
     * 为null或者""
     * 
     * @param input
     * @return
     */
    private boolean isNullOrEmpty( String input )
    {
        return StringUtil.isStringNull( input );
    }

    /**
     * 验证是否为empty或null,并加入错误信息
     * 
     * @param input
     * @param errMsg
     * @return
     */
    public String validateIsNullOrEmpty( String input, String errMsg )
    {
        if( isNullOrEmpty( input ) )
        {
            if( null == errMsg || errMsg.trim().equals( "" ) )
                errorList.add( "[默认信息：输入为空字符或字符为null!]" );
            else
                errorList.add( errMsg );
        }
        return input;
    }

    /**
     * 验证是否为数字,并加入错误信息
     * 
     * @param input
     * @param errMsg
     * @return
     */
    public String validateIsNumber( String input, String errMsg, boolean minus )
    {
        if( !StringUtil.isNum( input, minus ) )
        {
            if( null == errMsg || errMsg.trim().equals( "" ) )
                errorList.add( "[默认信息：输入不是一个数字!]" );
            else
                errorList.add( errMsg );
        }
        return input;
    }

    /**
     * 整个验证过程产生了错误吗？
     * 
     * @return
     */
    public boolean hasError()
    {
        if( errorList.size() > 0 )
            return true;
        return false;
    }

    /**
     * 添加一个信息
     * 
     * @param msg
     * @return
     */
    public boolean addAError( String msg )
    {
        return errorList.add( msg );
    }

    /**
     * 返回错误信息列表
     * 
     * @return
     */
    public ArrayList getErrors()
    {
        return this.errorList;
    }

    /**
     * 清空错误列表
     */
    public void clear()
    {
        this.errorList.clear();
    }

    public static void main( String args[] )
    { }

}
