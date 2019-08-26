package cn.com.mjsoft.cms.schedule.controller;

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

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.schedule.dao.vo.ScheduleJobDetail;
import cn.com.mjsoft.cms.schedule.service.ScheduleService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/job" )
public class ManageScheduleJobController
{
    public static final String JOB_NAME = "PublishSiteHtmlContentJob";

    private static ScheduleService schService = ScheduleService.getInstance();

    @RequestMapping( value = "/addPSJob.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加发布任务", token = true )
    public ModelAndView addPSJob( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String hPubTarget = ( String ) params.get( "homePubTarget" );

        String cPubTarget = ( String ) params.get( "channelPubTarget" );

        String sPubTarget = ( String ) params.get( "specPubTarget" );

        ScheduleJobDetail sjd = ( ScheduleJobDetail ) ServletUtil.getValueObject( request,
            ScheduleJobDetail.class );

        sjd.setJobName( JOB_NAME );
        sjd.setSiteId( siteBean.getSiteId() );
        sjd.setSystemJob( Constant.COMMON.OFF );
        sjd.setUseState( Constant.COMMON.ON );
        sjd.setJobExecuteClass( ScheduleService.PUBLISH_HTML_CONTENT_NAME );

        schService.addPublichContentScheduleJobDetail( sjd, hPubTarget, cPubTarget, sPubTarget );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/deploy/CreatePublishJob.jsp", paramMap );

    }

    @RequestMapping( value = "/editPSJob.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑发布任务", token = true )
    public ModelAndView editPSJob( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String hPubTarget = ( String ) params.get( "homePubTarget" );

        String cPubTarget = ( String ) params.get( "channelPubTarget" );

        String sPubTarget = ( String ) params.get( "specPubTarget" );

        ScheduleJobDetail sjd = ( ScheduleJobDetail ) ServletUtil.getValueObject( request,
            ScheduleJobDetail.class );

        sjd.setJobName( JOB_NAME );
        sjd.setSiteId( siteBean.getSiteId() );
        sjd.setSystemJob( Constant.COMMON.OFF );
        sjd.setUseState( Constant.COMMON.ON );
        sjd.setJobExecuteClass( ScheduleService.PUBLISH_HTML_CONTENT_NAME );

        schService.editScheduleJobDetail( sjd, hPubTarget, cPubTarget, sPubTarget );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/deploy/EditPublishJob.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/shutdownJob.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "停止发布任务", token = true )
    public String shutdownJob( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        schService.shutdownPublishSiteScheduleJob( idList );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/startupJob.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "启动发布任务", token = true )
    public String startupJob( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        schService.startupPublishSiteScheduleJob( idList );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/deletePSJob.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除发布任务", token = true )
    public String deletePSJob( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        schService.deletePublishSiteScheduleJob( idList );

        return "success";

    }
}
