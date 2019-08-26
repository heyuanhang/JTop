package cn.com.mjsoft.cms.common.controller;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.img.RandomImageGenerator;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.FlowConstants;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/common" )
public class RandomImageController
{

    @ResponseBody
    @RequestMapping( value = "/authImg.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public Object authImg( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        int count = StringUtil.getIntValue( ( String ) request.getParameter( "count" ), 6 );

        int line = StringUtil.getIntValue( ( String ) request.getParameter( "line" ), 6 );

        int point = StringUtil.getIntValue( ( String ) request.getParameter( "point" ), 260 );

        int width = StringUtil.getIntValue( ( String ) request.getParameter( "width" ), 130 );

        int height = StringUtil.getIntValue( ( String ) request.getParameter( "height" ), 26 );

        int jump = StringUtil.getIntValue( ( String ) request.getParameter( "jump" ), 5 );

        if( count > 20 || count < 1 || line > 20 || line < 1 || point > 1000 || point < 1
            || width > 500 || width < 1 || height > 100 || height < 1 || jump > 50 || jump < 1 )
        {
            return FlowConstants.NULL_RESULT;
        }

        HttpSession ssn = request.getSession();

        String randomString = RandomImageGenerator.random( count );// 生成种子
        ssn.setAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY, randomString );// 将种子放到session里面
        response.setContentType( "image/jpeg" );// 设置图像生成格式

        OutputStream out = response.getOutputStream();

        RandomImageGenerator.render( randomString, out, width, height, line, point, jump, count );// 输出到页面

        out.close();

        return FlowConstants.NULL_RESULT;
    }

    public static String getRandomLoginKey( HttpServletRequest req )
    {
        return ( String ) req.getSession().getAttribute(
            Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );
    }

}
