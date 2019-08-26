package cn.com.mjsoft.cms.site.controller;

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
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.dao.vo.SitePublishGateway;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/site" )
public class ManagePublishGatewayController
{
    private static SiteGroupService siteService = SiteGroupService.getInstance();

    @RequestMapping( value = "/addPublishGateway.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加发布点", token = true )
    public ModelAndView addPublishGateway( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        // 默认period为1
        Integer transferPeriod = StringUtil.getIntValue( ( String ) params.get( "transferPeriod" ),
            1 );

        // 默认为分钟级
        Integer transferPeriodType = StringUtil.getIntValue( ( String ) params
            .get( "transferPeriodType" ), 2 );

        SitePublishGateway gateway = ( SitePublishGateway ) ServletUtil.getValueObject( request,
            SitePublishGateway.class );

        siteService.addSitePublishGateway( gateway, siteBean, transferPeriod, transferPeriodType );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/deploy/AddPublishGateway.jsp", returnParams );
    }

    @RequestMapping( value = "/editPublishGateway.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑发布点", token = true )
    public ModelAndView editPublishGateway( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        // 默认period为1
        Integer transferPeriod = StringUtil.getIntValue( ( String ) params.get( "transferPeriod" ),
            1 );

        // 默认为分钟级
        Integer transferPeriodType = StringUtil.getIntValue( ( String ) params
            .get( "transferPeriodType" ), 2 );

        SitePublishGateway gateway = ( SitePublishGateway ) ServletUtil.getValueObject( request,
            SitePublishGateway.class );

        siteService.editSitePublishGateway( gateway, siteBean, transferPeriod, transferPeriodType );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );
        returnParams.put( "id", gateway.getGatewayId() );

        return ServletUtil.redirect( "/core/deploy/EditPublishGateway.jsp", returnParams );
    }

    @ResponseBody
    @RequestMapping( value = "/deletePublishGateway.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除发布点", token = true )
    public String deletePublishGateway( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        siteService.deleteSitePublishGateway( idList );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/transferAllData.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "同步文件到服务器", token = true )
    public String transferAllData( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        Long gwId = StringUtil.getLongValue( ( String ) params.get( "gwId" ), -1 );

        siteService.transferAllDataByTypeAndGateway( siteBean, gwId );

        return "success";

    }

}
