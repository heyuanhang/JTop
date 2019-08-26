package cn.com.mjsoft.cms.comment.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.comment.service.CommentService;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/comment" )
public class CensorCommentTextAndOtherInfoController
{
    private static CommentService commentService = CommentService.getInstance();

    @RequestMapping( value = "/censorComment.do", method = { RequestMethod.POST, RequestMethod.GET } )
    @ActionInfo( traceName = "审核评论", token = true )
    public ModelAndView censorComment( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Map paramMap = new HashMap();

        String[] idArray = StringUtil.split( ( ( String ) params.get( "commentId" ) ), "," );

        Long[] ids = new Long[idArray.length];

        paramMap.put( "nextPage", ( String ) params.get( "nextPage" ) );

        long id;
        for ( int i = 0; i < idArray.length; i++ )
        {
            // 强制转换Long,任何非法数据将变为-1
            id = StringUtil.getLongValue( idArray[i], -1 );

            ids[i] = Long.valueOf( id );

        }

        Integer censorState = Integer.valueOf( StringUtil.getIntValue( ( String ) params
            .get( "censorState" ), -1 ) );

        if( censorState.longValue() < 0 )
        {
            return null;
        }

        commentService.censorComment( ids, censorState );

        paramMap.put( "fromFlow", Boolean.TRUE );
        paramMap.put( "censor", "true" );
        paramMap.put( "pn", params.get( "pn" ) );
        paramMap.put( "contentId", params.get( "contentId" ) );

        if( "true".equals( ( String ) params.get( "single" ) ) )
        {
            return ServletUtil.redirect( "/core/comment/ManageSingleContentComment.jsp", paramMap );
        }

        return ServletUtil.redirect( "/core/comment/ManageContentComment.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteComment.do", method = { RequestMethod.POST, RequestMethod.GET  } )
    @ActionInfo( traceName = "删除评论", token = true )
    public ModelAndView deleteComment( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String[] idArray = StringUtil.split( ( ( String ) params.get( "commentId" ) ), "," );

        Long[] ids = new Long[idArray.length];

        long id;
        for ( int i = 0; i < idArray.length; i++ )
        {
            // 强制转换Long,任何非法数据将变为-1
            id = StringUtil.getLongValue( idArray[i], -1 );

            ids[i] = Long.valueOf( id );

        }

        List memberCollList = new ArrayList();

        commentService.deleteCommentAllInfoByIds( ids, memberCollList );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );
        returnParams.put( "pn", params.get( "pn" ) );
        returnParams.put( "contentId", params.get( "contentId" ) );

        // 设置成功标志

        request.setAttribute( "successFlag", Boolean.TRUE );

        request.setAttribute( "memberId", memberCollList );

        if( "true".equals( ( String ) params.get( "single" ) ) )
        {
            return ServletUtil.redirect( "/core/comment/ManageSingleContentComment.jsp",
                returnParams );
        }

        return ServletUtil.redirect( "/core/comment/ManageContentComment.jsp", returnParams );

    }

}
