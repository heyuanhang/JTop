package cn.com.mjsoft.cms.comment.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.comment.dao.vo.CommentInfo;
import cn.com.mjsoft.cms.comment.service.CommentService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.member.bean.MemberBean;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/comment" )
public class CommentContentAndTakePartInController
{
    // 30分钟内不可重复顶
    private static final int COOKIE_MAX_AGE = 60 * 30;

    private static CommentService commentService = CommentService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/clientAddComment.do", method = { RequestMethod.POST } )
    public Object clientAddComment( HttpServletRequest request, HttpServletResponse response )
    {
        
        try
        {
            Thread.sleep( 500 );
        }
        catch ( InterruptedException e )
        {

            e.printStackTrace();
        }

        Map params = ServletUtil.getRequestInfo( request );

        Long replyCommentId = Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "replyId" ), -1 ) );

        Long commentId = Long.valueOf( StringUtil.getLongValue(
            ( String ) params.get( "commentId" ), -1 ) );

        if( commentId.longValue() > 0 )
        {
            // 评论digg
            String diggValue = ( String ) params.get( "digg" );

            Cookie cookie = ServletUtil.getCookie( request, "jtopcms_comment_digg" );

            String ids = null;
            boolean notExistId = true;

            if( cookie != null )
            {
                ids = cookie.getValue();

                if( ids.indexOf( commentId.toString() ) != -1 )
                {
                    notExistId = false;
                    return "-6";// 您已经顶踩过
                }
            }

            if( notExistId )
            {
                if( ids == null )
                {
                    ids = commentId.toString();
                }
                else
                {
                    ids = ids + "," + commentId;
                }

                ServletUtil.addCookie( response, "jtopcms_comment_digg", ids, COOKIE_MAX_AGE );

                commentService.updateCommentDiggInfo( commentId, diggValue );

                return "1";// 执行成功
            }

        }

        CommentInfo comment = ( CommentInfo ) ServletUtil.getValueObject( request,
            CommentInfo.class );

        // 解码
        comment.setUserName( SystemSafeCharUtil.decodeFromWeb( comment.getUserName() ) );

        comment.setCommentText( SystemSafeCharUtil.decodeFromWeb( comment.getCommentText() ) );

        SecuritySession session = SecuritySessionKeeper.getSecuritySession();

        if( session.getMember() != null )
        {
            MemberBean member = ( MemberBean ) session.getMember();

            comment.setUserName( member.getMemberName() );

            comment.setMemberId( member.getMemberId() );
        }
        else if( StringUtil.isStringNull( comment.getUserName() ) )
        {
            comment.setUserName( "匿名" );
        }

        if( comment.getUserName().length() >= 75 )
        {
            comment.setUserName( StringUtil.subString( comment.getUserName(), 0, 75 ) );
        }

        if( comment.getCommentText().length() >= 2900 )
        {
            comment.setCommentText( StringUtil.subString( comment.getCommentText(), 0, 2900 ) );
        }

        comment.setIp( request.getRemoteAddr() );

        comment.setCommentText( SystemSafeCharUtil.filterHTMLNotApos( comment.getCommentText() ) );

        SiteGroupBean site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );

        comment.setSiteId( site.getSiteId() );

        // 验证码
        HttpServletRequest req = request;

        HttpSession ssn = req.getSession();

        String checkCode = ( String ) ssn
            .getAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );

        String checkCodeTest = ( String ) params.get( "sysCheckCode" );

        String msg = commentService.addNewCommentAndInfo( comment, replyCommentId, checkCode,
            checkCodeTest );
        
        ContentService.releaseContentCache();

        String direct = ( String ) params.get( "direct" );

        if( "true".equals( direct ) )
        {
            String targetUrl = ( String ) params.get( "callbackUrl" );
            return ServletUtil.redirect( targetUrl );
        }

        if( "1".equals( msg ) )
        {
            // 设置成功标志

            request.setAttribute( "successFlag", Boolean.TRUE );
        }

        return msg;
    }
}
