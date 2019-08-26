package cn.com.mjsoft.cms.comment.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.comment.service.CommentService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/comment" )
public class ConveyMoodAndDiggForContentController
{
    // 强制30分钟内不可重复顶
    private static final int COOKIE_MAX_AGE = 60 * 30;

    private static CommentService commentService = CommentService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/mood.do", method = { RequestMethod.POST } )
    public Object mood( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long contentId = StringUtil.getLongValue( ( String ) params.get( "contentId" ), -1 );

        String message = "1";// 成功

        String moodFlag = ( String ) params.get( "mood" );
 

        Cookie cookie = ServletUtil.getCookie( request, "jtopcms_mood" );

        String ids = null;
        boolean notExistId = true;

        if( cookie != null )
        {
            ids = cookie.getValue();

            if( ids.indexOf( contentId.toString() ) != -1 )
            {
                notExistId = false;
                message = "-1";// 您已经表达过心情
            }
        }

        if( notExistId )
        {
            if( ids == null )
            {
                ids = contentId.toString();
            }
            else
            {
                ids = ids + "," + contentId;
            }

            ServletUtil.addCookie( response, "jtopcms_mood", ids, COOKIE_MAX_AGE );

            commentService.addNewMoodInfo( contentId, Integer.valueOf( StringUtil.getIntValue(
                moodFlag, -1 ) ) );
        }

        return message;
    }

    @ResponseBody
    @RequestMapping( value = "/digg.do", method = { RequestMethod.POST } )
    public Object digg( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long contentId = StringUtil.getLongValue( ( String ) params.get( "contentId" ), -1 );

        String message = "1";// 成功

        String diggValue = ( String ) params.get( "digg" );

        Cookie cookie = ServletUtil.getCookie( request, "jtopcms_digg" );

        String ids = null;
        boolean notExistId = true;

        if( cookie != null )
        {
            ids = cookie.getValue();

            if( ids.indexOf( contentId.toString() ) != -1 )
            {
                notExistId = false;
                message = "-1";// 您已经顶踩过这篇内容
            }
        }

        if( notExistId )
        {
            if( ids == null )
            {
                ids = contentId.toString();
            }
            else
            {
                ids = ids + "#" + contentId;
            }

            ServletUtil.addCookie( response, "jtopcms_digg", ids, COOKIE_MAX_AGE );

            commentService.addNewDiggInfo( contentId, diggValue );
        }

        return message;

    }
}
