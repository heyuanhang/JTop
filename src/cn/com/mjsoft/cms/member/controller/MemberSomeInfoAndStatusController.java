package cn.com.mjsoft.cms.member.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.member.bean.MemberBean;
import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/member" )
public class MemberSomeInfoAndStatusController
{
    private static MemberService memberService = MemberService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/deleteMember.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除会员", token = true )
    public String deleteMember( HttpServletRequest request, HttpServletResponse response )
    {
        if( !SecuritySessionKeeper.getSecuritySession().isManager() )
        {
            return "-1";
        }

        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        memberService.deleteMember( idList );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteMemContent.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除会员内容", token = true )
    public String deleteMemContent( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );
        if( !SecuritySessionKeeper.getSecuritySession().isManager() )
        {
            return "-1";
        }

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        memberService.deleteContentForMember( idList );

        // 设置成功标志

        request.setAttribute( "successFlag", Boolean.TRUE );

        request.setAttribute( "memberId", idList );

        return "1";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteMemMsg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除会员消息", token = true )
    public String deleteMemMsg( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        memberService.deleteMessageForMember( idList );

        return "1";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteMemComm.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除会员评论", token = true )
    public String deleteMemComm( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        memberService.deleteCommentForMember( idList );

        // 设置成功标志

        request.setAttribute( "successFlag", Boolean.TRUE );

        request.setAttribute( "eventCount", Integer.valueOf( idList.size() ).toString() );

        return "1";
    }

    @ResponseBody
    @RequestMapping( value = "/changeUseStatus.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "改动会员状态", token = true )
    public String changeUseStatus( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        String mode = ( String ) params.get( "flag" );

        memberService.changeMemberStatus( idList, mode );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/checkCoreInfo.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "确认会员核心身份", token = true )
    public String checkCoreInfo( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long memberId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "memberId" ),
            -1 ) );

        String mode = ( String ) params.get( "mode" );

        Boolean checkVal = Boolean.valueOf( StringUtil.getBooleanValue( ( String ) params
            .get( "check" ), false ) );

        memberService.checkMemberCoreInfo( memberId, mode, checkVal );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/addMc.do", method = { RequestMethod.POST } )
    public String addMc( HttpServletRequest request, HttpServletResponse response )
        throws InterruptedException
    {
        Map params = ServletUtil.getRequestInfo( request );

        Thread.sleep( 200 );

        Object memObj = SecuritySessionKeeper.getSecuritySession().getMember();

        if( memObj == null )
        {
            return "-1";
        }

        long cId = StringUtil.getLongValue( ( String ) params.get( "id" ), -1 );

        if( cId < 0 )
        {
            return "-2";
        }

        MemberBean member = ( MemberBean ) memObj;

        String status = memberService.addMemberCollectContent( cId, member.getMemberId() );

        return status;

    }

    @ResponseBody
    @RequestMapping( value = "/deleteMc.do", method = { RequestMethod.POST } )
    public String deleteMc( HttpServletRequest request, HttpServletResponse response )
        throws InterruptedException
    {
        Map params = ServletUtil.getRequestInfo( request );

        Thread.sleep( 200 );

        Object memObj = SecuritySessionKeeper.getSecuritySession().getMember();

        if( memObj == null )
        {
            return "-1";
        }

        long cId = StringUtil.getLongValue( ( String ) params.get( "id" ), -1 );

        if( cId < 0 )
        {
            return "-2";
        }

        MemberBean member = ( MemberBean ) memObj;

        String status = memberService.deleteMemberCollectContent( cId, member.getMemberId() );

        return status;

    }

}
