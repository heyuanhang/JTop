package cn.com.mjsoft.cms.common.img;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RandomImageServlet extends HttpServlet
{
    /**
     * 
     */
    private static final long serialVersionUID = -6042627551785658763L;
    public final static String RANDOM_LOGIN_KEY = "RANDOM_LOGIN_KEY";

    public void init() throws ServletException
    {
        System.setProperty( "java.awt.headless", "true" );
    }

    public static String getRandomLoginKey( HttpServletRequest req )
    {
        return ( String ) req.getSession().getAttribute( RANDOM_LOGIN_KEY );
    }

    protected void doGet( HttpServletRequest req, HttpServletResponse res )
        throws ServletException, IOException
    {
        HttpSession ssn = req.getSession();
        // if( ssn != null )

        String randomString = RandomImageGenerator.random( 6 );// 生成种子
        ssn.setAttribute( RANDOM_LOGIN_KEY, randomString );// 将种子放到session里面
        res.setContentType( "image/jpeg" );// 设置图像生成格式

//        RandomImageGenerator.render( randomString, res.getOutputStream(), 130,
//            6 );// 输出到页面

    }
}
