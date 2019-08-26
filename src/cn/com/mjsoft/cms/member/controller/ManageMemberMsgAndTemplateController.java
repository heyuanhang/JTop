package cn.com.mjsoft.cms.member.controller;

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
import cn.com.mjsoft.cms.member.dao.vo.MemberMessageTemplate;
import cn.com.mjsoft.cms.member.service.MemberService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/member" )
public class ManageMemberMsgAndTemplateController
{
    private static MemberService memberService = MemberService.getInstance();

    @RequestMapping( value = "/createMT.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加消息模板", token = true )
    public ModelAndView createMT( HttpServletRequest request, HttpServletResponse response )
    {
        Set htmlParamSet = new HashSet();

        htmlParamSet.add( "templateContent" );

        MemberMessageTemplate mt = ( MemberMessageTemplate ) ServletUtil.getValueObject( request,
            htmlParamSet, MemberMessageTemplate.class );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        mt.setCreator( ( String ) auth.getApellation() );

        mt.setSiteId( site.getSiteId() );

        memberService.addNewMemberMessageTemplate( mt );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/member/CreateMessageTemplate.jsp", paramMap );

    }

    @RequestMapping( value = "/editMT.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑消息模板", token = true )
    public ModelAndView editMT( HttpServletRequest request, HttpServletResponse response )
    {
        Set htmlParamSet = new HashSet();

        htmlParamSet.add( "templateContent" );

        MemberMessageTemplate mt = ( MemberMessageTemplate ) ServletUtil.getValueObject( request,
            htmlParamSet, MemberMessageTemplate.class );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        mt.setCreator( ( String ) auth.getApellation() );

        mt.setSiteId( site.getSiteId() );

        memberService.editMemberMessageTemplate( mt );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/member/EditMessageTemplate.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteMsgTemp.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除消息模板", token = true )
    public String deleteMsgTemp( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        memberService.deleteMessageTemplate( idList );

        return "success";
    }

    @RequestMapping( value = "/createMTP.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加消息模板参数", token = true )
    public ModelAndView createMTP( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        params.put( "siteId", site.getSiteId() );
        params.put( "creator", auth.getApellation().toString() );

        memberService.addNewMessageTemplateParam( params );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/member/CreateMessageTemplateParam.jsp", paramMap );

    }

    @RequestMapping( value = "/editMTP.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑消息模板参数", token = true )
    public ModelAndView editMTP( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        memberService.editMessageTemplateParam( params );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/member/CreateMessageTemplateParam.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteMsgTempParam.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除消息模板参数", token = true )
    public String deleteMsgTempParam( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        memberService.deleteMessageTemplateParam( idList );

        return "success";
    }

    @RequestMapping( value = "/sendMsg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "发送站内消息", token = true )
    public ModelAndView sendMsg( HttpServletRequest request, HttpServletResponse response )
    {

        Set htmlParamSet = new HashSet();

        htmlParamSet.add( "templateContent" );

        Map params = ServletUtil.getRequestInfo( request, htmlParamSet );

        String title = ( String ) params.get( "templateTitle" );

        String content = ( String ) params.get( "templateContent" );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        memberService.sendMemberMessage( title, content, idList );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/member/SystemManagerSendMessage.jsp", paramMap );

    }

    @RequestMapping( value = "/sendMail.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "发送邮件", token = true )
    public ModelAndView sendMail( HttpServletRequest request, HttpServletResponse response )
    {

        Set htmlParamSet = new HashSet();

        htmlParamSet.add( "templateContent" );

        Map params = ServletUtil.getRequestInfo( request, htmlParamSet );

        String title = ( String ) params.get( "templateTitle" );

        String content = ( String ) params.get( "templateContent" );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        memberService.sendMemberMail( title, content, idList );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/member/SystemManagerSendMail.jsp", paramMap );

    }

}
