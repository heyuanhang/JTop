package cn.com.mjsoft.cms.cluster.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.common.service.CMSRedisDB;
import cn.com.mjsoft.cms.publish.bean.PublishStatusBean;
import cn.com.mjsoft.cms.search.service.SearchService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@Controller
@RequestMapping( "/cluster" )
public class SearchIndexController
{
    private static Logger log = Logger.getLogger( BlackIPClearController.class );

    private static SearchService searchService = SearchService.getInstance();

    @RequestMapping( value = "/clearSiteIndex.do", method = { RequestMethod.POST } )
    public void clearSiteIndex( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String innerAccessJtopSysFlag = ( String ) params.get( "innerAccessJtopSysFlag" );

        if( CMSRedisDB.existMapKey( "AuthorizationHandler.innerAccessCheckMap",
            innerAccessJtopSysFlag ) )
        {

            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( StringUtil.getLongValue( ( String ) params.get( "siteId" ), -1 ) );

            searchService.deleteSearchIndexMetadataBySite( site );

        }

        // 无论是否正常的accFlag，都要执行remove
        CMSRedisDB.delMapVal( "AuthorizationHandler.innerAccessCheckMap", innerAccessJtopSysFlag );

    }

    @RequestMapping( value = "/clearIndex.do", method = { RequestMethod.POST } )
    public void clearIndex( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String innerAccessJtopSysFlag = ( String ) params.get( "innerAccessJtopSysFlag" );

        if( CMSRedisDB.existMapKey( "AuthorizationHandler.innerAccessCheckMap",
            innerAccessJtopSysFlag ) )
        {
            String mode = ( String ) params.get( "mode" );

            List idList = StringUtil.changeStringToList( ( String ) params.get( "idInfo" ), "," );

            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( StringUtil.getLongValue( ( String ) params.get( "siteId" ), -1 ) );

            if( "form".equals( mode ) )
            {
                searchService.deleteSearchIndexMetadataByModelId( idList, site );
            }
            else
            {
                searchService.deleteSearchIndexMetadataByClassId( idList, site );
            }

        }

        // 无论是否正常的accFlag，都要执行remove
        CMSRedisDB.delMapVal( "AuthorizationHandler.innerAccessCheckMap", innerAccessJtopSysFlag );

    }

    
}
