package cn.com.mjsoft.cms.member.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.member.dao.vo.MemberRank;
import cn.com.mjsoft.cms.member.dao.vo.MemberScoreAct;
import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/member" )
public class ManageMemberRankAndScoreController
{
    private static MemberService memberService = MemberService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/addRank.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加会员等级", token = true )
    public String addRank( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        // 整数积分和等级处理
        params.put( "rankLevel", Integer.valueOf(
            StringUtil.getIntValue( ( String ) params.get( "rankLevel" ), 999 ) ).toString() );

        params.put( "minScore", Integer.valueOf(
            StringUtil.getIntValue( ( String ) params.get( "minScore" ), 99999 ) ).toString() );

        params.put( "maxScore", Integer.valueOf(
            StringUtil.getIntValue( ( String ) params.get( "maxScore" ), 999999 ) ).toString() );

        MemberRank rank = ( MemberRank ) ServletUtil.getValueObjectDecode( request,
            MemberRank.class );

        memberService.addMemberRank( rank );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/editRank.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑会员等级", token = true )
    public String editRank( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        // 整数积分和等级处理
        params.put( "rankLevel", Integer.valueOf(
            StringUtil.getIntValue( ( String ) params.get( "rankLevel" ), 999 ) ).toString() );

        params.put( "minScore", Integer.valueOf(
            StringUtil.getIntValue( ( String ) params.get( "minScore" ), 99999 ) ).toString() );

        params.put( "maxScore", Integer.valueOf(
            StringUtil.getIntValue( ( String ) params.get( "maxScore" ), 999999 ) ).toString() );

        // AJAX 提交转码
        ServiceUtil.decodeMapParam( params );

        MemberRank rank = ( MemberRank ) ServletUtil.getValueObjectDecode( request,
            MemberRank.class );

        memberService.editMemberRank( rank );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteRank.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除会员等级", token = true )
    public String deleteRank( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        memberService.deleteMemberRank( idList );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/addScoreAct.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加积分规则", token = true )
    public String addScoreAct( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        // 整数积分和等级处理

        params.put( "actScore", Integer.valueOf(
            StringUtil.getIntValue( ( String ) params.get( "actScore" ), 1 ) ).toString() );

        // AJAX 提交转码
        ServiceUtil.decodeMapParam( params );

        MemberScoreAct sa = ( MemberScoreAct ) ServletUtil.getValueObjectDecode( request,
            MemberScoreAct.class );

        memberService.addMemberScoreAct( sa );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/editScoreAct.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑积分规则", token = true )
    public String editScoreAct( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        // 整数积分和等级处理

        params.put( "actScore", Integer.valueOf(
            StringUtil.getIntValue( ( String ) params.get( "actScore" ), 1 ) ).toString() );

        // AJAX 提交转码
        ServiceUtil.decodeMapParam( params );

        MemberScoreAct sa = ( MemberScoreAct ) ServletUtil.getValueObjectDecode( request,
            MemberScoreAct.class );

        memberService.editMemberScoreAct( sa );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteScoreAct.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除积分规则", token = true )
    public String deleteScoreAct( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        memberService.deleteMemberScoreAct( idList );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/changeMemberScore.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "改变会员积分", token = true )
    public String changeMemberScore( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Integer score = StringUtil.getIntValue( ( String ) params.get( "score" ), 0 );

        String mode = ( String ) params.get( "flag" );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        memberService.changeMemberScore( mode, score, idList );

        return "success";
    }
}
