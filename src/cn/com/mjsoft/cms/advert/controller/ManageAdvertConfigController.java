package cn.com.mjsoft.cms.advert.controller;

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

import cn.com.mjsoft.cms.advert.dao.vo.AdvertConfig;
import cn.com.mjsoft.cms.advert.service.AdvertService;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/trevda" )
public class ManageAdvertConfigController
{
    private static AdvertService advertService = AdvertService.getInstance();

    @RequestMapping( value = "/createTrevdaConfig.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加广告配置", token = true )
    public Object createAdvertConfig( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Set htmlSet = new HashSet();

        htmlSet.add( "advertCode" );

        AdvertConfig config = ( AdvertConfig ) ServletUtil.getValueObjectDecode( request, htmlSet,
            AdvertConfig.class );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        config.setSiteId( site.getSiteId() );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth != null )
        {
            config.setCreator( auth.getApellation().toString() );
        }

        advertService.addNewAdvertConfig( config );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/trevda/CreateTrevdaConfig.jsp", paramMap );
    }

    @RequestMapping( value = "/editTrevdaConfig.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑广告配置", token = true )
    public Object editTrevdaConfig( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Set htmlSet = new HashSet();

        htmlSet.add( "advertCode" );

        AdvertConfig config = ( AdvertConfig ) ServletUtil.getValueObjectDecode( request, htmlSet,
            AdvertConfig.class );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        config.setSiteId( site.getSiteId() );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth != null )
        {
            config.setCreator( auth.getApellation().toString() );
        }

        advertService.editAdvertConfig( config );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/trevda/EditTrevdaConfig.jsp", paramMap );
    }

    @RequestMapping( value = "/editTrevdaTemp.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑广告模板", token = true )
    public Object editAdvertTemp( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Set htmlSet = new HashSet();

        htmlSet.add( "advertCode" );

        AdvertConfig config = ( AdvertConfig ) ServletUtil.getValueObjectDecode( request, htmlSet,
            AdvertConfig.class );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        config.setSiteId( site.getSiteId() );

        config.setAdvertCode( config.getAdvertCode() );

        advertService.editAdvertCodeTemplate( config );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/trevda/EditTrevdaCode.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/deleteTrevdaConfig.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除广告配置", token = true )
    public Object deleteAdvertConfig( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long configId = StringUtil.getLongValue( ( String ) params.get( "configId" ), -1 );

        boolean checkCfgUse = advertService.checkExistAdConfigAllCms( configId );

        if( checkCfgUse )
        {
            return "cfguse";
        }

        advertService.deleteAdvertConfigAllInfo( configId );

        return "success";

    }

}
