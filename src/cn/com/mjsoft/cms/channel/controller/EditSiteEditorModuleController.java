package cn.com.mjsoft.cms.channel.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/channel" )
public class EditSiteEditorModuleController
{
    private static ChannelService channelService = ChannelService.getInstance();

    @RequestMapping( value = "/editEditorCode.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑编辑器代码", token = true )
    public Object editEditorCode( HttpServletRequest request, HttpServletResponse response )
    {
        Set htmlSet = new HashSet();

        htmlSet.add( "emCode" );

        Map params = ServletUtil.getRequestInfo( request, htmlSet );

        String code = ( String ) params.get( "emCode" );

        String emDesc = ( String ) params.get( "emDesc" );

        Long emId = StringUtil.getLongValue( ( String ) params.get( "emId" ), -1 );

        channelService.editSiteEditorModule( emId, code, emDesc );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/tool/EditEditorModuleCode.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/reEditorCode.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "重编辑编辑器代码", token = true )
    public Object reEditorCode( HttpServletRequest request, HttpServletResponse response )
    {
        Set htmlSet = new HashSet();

        htmlSet.add( "emCode" );

        Map params = ServletUtil.getRequestInfo( request, htmlSet );

        SecuritySession securitySession = SecuritySessionKeeper.getSecuritySession();

        SiteGroupBean siteBean = ( SiteGroupBean ) securitySession.getCurrentLoginSiteInfo();

        channelService
            .resumeSiteEditorModule( siteBean.getSiteId(), ( String ) params.get( "type" ) );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/getEditorCode.do", method = { RequestMethod.POST } )
    public Object getEditorCode( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String code = channelService.loadEditorModuleCode( params );

        return code;
    }

}
