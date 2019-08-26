package cn.com.mjsoft.cms.pick.controller;

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
import cn.com.mjsoft.cms.pick.dao.vo.PickContentRule;
import cn.com.mjsoft.cms.pick.service.PickService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/pick" )
public class ManagePickRuleController
{
    private static PickService pickService = PickService.getInstance();

    @RequestMapping( value = "/createPickRule.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加采集规则", token = true )
    public ModelAndView createPickRule( HttpServletRequest request, HttpServletResponse response )
    {
        Set htmlParamSet = new HashSet();

        ServletUtil.getRequestInfo( request, htmlParamSet );

        PickContentRule pickRule = ( PickContentRule ) ServletUtil.getValueObject( request,
            PickContentRule.class );

        pickService.addNewPickContentRule( pickRule );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/pick/CreatePickConfig.jsp", paramMap );
    }

    @RequestMapping( value = "/editPickRule.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑采集规则", token = true )
    public ModelAndView editPickRule( HttpServletRequest request, HttpServletResponse response )
    {
        Set htmlParamSet = new HashSet();

        Map param = ServletUtil.getRequestInfo( request, htmlParamSet );

        PickContentRule pickRule = ( PickContentRule ) ServletUtil.getValueObject( request,
            PickContentRule.class );

        Map paramMap = new HashMap();

        pickService.editPickContentRule( pickRule, param );

        paramMap.put( "cfgId", pickRule.getPickCfgId() );

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/pick/EditPickConfig.jsp", paramMap );
    }

    @RequestMapping( value = "/createModelExt.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加自定义模型采集规则", token = true )
    public ModelAndView createModelExt( HttpServletRequest request, HttpServletResponse response )
    {
        Set htmlParamSet = new HashSet();

        Map param = ServletUtil.getRequestInfo( request, htmlParamSet );

        Map paramMap = new HashMap();

        pickService.addModelExtPickRule( param );

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/pick/CreateExtRule.jsp", paramMap );
    }

    @RequestMapping( value = "/editModelExt.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑自定义模型采集规则", token = true )
    public ModelAndView editModelExt( HttpServletRequest request, HttpServletResponse response )
    {
        Set htmlParamSet = new HashSet();

        Map param = ServletUtil.getRequestInfo( request, htmlParamSet );

        Map paramMap = new HashMap();

        pickService.editModelExtPickRule( param );

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/pick/EditExtRule.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/deletePickRule.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除采集规则", token = true )
    public String deletePickRule( HttpServletRequest request, HttpServletResponse response )
    {

        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        pickService.deletePickContentRule( idList );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deletePickModelExt.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除采集扩展字段", token = true )
    public String deletePickModelExt( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long eprId = StringUtil.getLongValue( ( String ) params.get( "id" ), -1 );

        pickService.deleteModelExtPickRule( eprId );

        return "success";
    }

}
