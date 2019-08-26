package cn.com.mjsoft.cms.message.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.message.service.MessageService;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/message" )
public class SendOrReceiveManagerMessageController
{
    private static MessageService messageService = MessageService.getInstance();

    @RequestMapping( value = "/sendMsg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "发送消息", token = true )
    public ModelAndView sendMsg( HttpServletRequest request, HttpServletResponse response )
    {

        Set htmlParamSet = new HashSet();

        Map params = ServletUtil.getRequestInfo( request, htmlParamSet );

        String title = ( String ) params.get( "msgTitle" );

        String content = ( String ) params.get( "msgContent" );

        List userIdList = StringUtil.changeStringToList( ( String ) params.get( "userIds" ), "," );

        Long currentSenderId = ( Long ) SecuritySessionKeeper.getSecuritySession().getAuth()
            .getIdentity();

        messageService.sendManagerMessage( currentSenderId, "管理员消息", title, content, userIdList );

        Map paramMap = new HashMap();
        paramMap.put( "fromFlow", Boolean.TRUE );

        if( "true".equals( params.get( "dialog" ) ) )
        {
            ServletUtil.redirect( "/core/message/CreateAndSendMessageDialog.jsp", paramMap );
        }

        return ServletUtil.redirect( "/core/message/CreateAndSendMessage.jsp", paramMap );

    }

    @RequestMapping( value = "/replyMsg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "回复消息", token = true )
    public ModelAndView replyMsg( HttpServletRequest request, HttpServletResponse response )
    {
        Set htmlParamSet = new HashSet();

        Map params = ServletUtil.getRequestInfo( request, htmlParamSet );

        String replyContent = ( String ) params.get( "replyContent" );

        Long msgId = StringUtil.getLongValue( ( String ) params.get( "msgId" ), -1 );

        messageService.replyMessage( msgId, replyContent );

        Map paramMap = new HashMap();
        paramMap.put( "fromFlow", Boolean.TRUE );

        if( "true".equals( params.get( "dialog" ) ) )
        {
            ServletUtil.redirect( "/core/message/ReadAndSendMessageDialog.jsp", paramMap );
        }

        return ServletUtil.redirect( "/core/message/ReadAndSendMessage.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/readMsg.do", method = { RequestMethod.POST } )
    public String readMsg( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long msgId = StringUtil.getLongValue( ( String ) params.get( "msgId" ), -1 );

        messageService.readMessage( msgId );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteMsg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除消息", token = true )
    public String deleteMsg( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List msgIdList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        String msgFlag = ( String ) params.get( "msgFlag" );

        messageService.deleteMessage( msgIdList, msgFlag );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/getNotReadMsgCount.do", method = { RequestMethod.POST } )
    public String getNotReadMsgCount( HttpServletRequest request, HttpServletResponse response )
    {
        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        Long userId = ( auth == null ) ? Long.valueOf( -1 ) : ( Long ) auth.getIdentity();

        return messageService.retrieveMessageNotReadCount( userId ).toString();
    }
}
