package cn.com.mjsoft.cms.guestbook.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.guestbook.dao.vo.GuestbookConfig;
import cn.com.mjsoft.cms.guestbook.service.GuestbookService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/guestbook" )
public class ManageGuestbookConfigController
{
    private static GuestbookService gbService = GuestbookService.getInstance();

    @RequestMapping( value = "/createGbConfig.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加留言板", token = true )
    public ModelAndView createGbConfig( HttpServletRequest request, HttpServletResponse response )
    {
        GuestbookConfig gbCfg = ( GuestbookConfig ) ServletUtil.getValueObject( request,
            GuestbookConfig.class );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        gbCfg.setSiteId( site.getSiteId() );

        gbService.addNewGuestbookConfig( gbCfg );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/guestbook/CreateGuestbookConfig.jsp", paramMap );
    }

    @RequestMapping( value = "/editGbConfig.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑留言板", token = true )
    public ModelAndView editGbConfig( HttpServletRequest request, HttpServletResponse response )
    {
        GuestbookConfig gbCfg = ( GuestbookConfig ) ServletUtil.getValueObject( request,
            GuestbookConfig.class );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        gbCfg.setSiteId( site.getSiteId() );

        gbService.editGuestbookConfig( gbCfg );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/guestbook/EditGuestbookConfig.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/deleteGbConfig.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除留言配置", token = true )
    public String deleteGbConfig( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "ids" );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        gbService.deleteGuestbookConfigAllInfoByIds( StringUtil.changeStringToList( ids, "," ),
            site );

        return "success";

    }

}
