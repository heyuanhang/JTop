package cn.com.mjsoft.cms.pick.controller;

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

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.pick.dao.vo.PickContentTask;
import cn.com.mjsoft.cms.pick.service.PickService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/pick" )
public class ManagePickTaskController
{
    public static final String JOB_NAME = "PickWebContent";
    
    private static PickService pickService = PickService.getInstance();

    @RequestMapping( value = "/createPickTask.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加采集任务", token = true )
    public ModelAndView createPickTask( HttpServletRequest request, HttpServletResponse response )
    {
        PickContentTask pickTask = ( PickContentTask ) ServletUtil.getValueObject( request,
            PickContentTask.class );

        pickService.addNewPickContentTask( pickTask );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/pick/CreatePickJob.jsp", paramMap );
    }

    @RequestMapping( value = "/editPickTask.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑采集任务", token = true )
    public ModelAndView editPickTask( HttpServletRequest request, HttpServletResponse response )
    {
        PickContentTask pickTask = ( PickContentTask ) ServletUtil.getValueObject( request,
            PickContentTask.class );

        pickService.editPickContentTask( pickTask );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/pick/CreatePickJob.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/deletePickTask.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除采集任务", token = true )
    public String deletePickTask( HttpServletRequest request, HttpServletResponse response )
    {

        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        pickService.deletePickContentTask( idList );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deletePickTrace.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除采集结果记录", token = true )
    public String deletePickTrace( HttpServletRequest request, HttpServletResponse response )
    {

        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        pickService.deletePickWebTrace( idList );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteAllPickTrace.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除全部采集结果记录", token = true )
    public String deleteAllPickTrace( HttpServletRequest request, HttpServletResponse response )
    {

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        pickService.deleteAllPickWebTrace( site.getSiteId() );

        return "success";
    }
}
