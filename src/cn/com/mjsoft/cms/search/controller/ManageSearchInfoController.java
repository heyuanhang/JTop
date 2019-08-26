package cn.com.mjsoft.cms.search.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.common.Constant;

import cn.com.mjsoft.cms.common.controller.OperationDisposeObserverController;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.publish.bean.PublishStatusBean;
import cn.com.mjsoft.cms.search.service.SearchService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/search" )
public class ManageSearchInfoController
{
    private static final int KEY_SIZE = 5;// 排名靠前几位的词汇

    private static SearchService searchService = SearchService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/rebuildIndex.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "重建索引", token = true )
    public String rebuildIndex( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String mode = ( String ) params.get( "mode" );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        // 本次索引唯一识别代码(UUID)
        String eventKey = ( String ) params.get( "key" );

        // 根据登陆管理员来获取站点
        SecuritySession securitySession = SecuritySessionKeeper.getSecuritySession();

        SiteGroupBean siteBean = ( SiteGroupBean ) securitySession.getCurrentLoginSiteInfo();

        PublishStatusBean status = new PublishStatusBean();

        OperationDisposeObserverController.statusMap.put( eventKey, status );

        if( "form".equals( mode ) )
        {
            searchService.rebuildFormSearchIndex( idList, siteBean, status );
        }
        else
        {
            searchService.rebuildSearchIndex( idList, siteBean, status );
        }

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteSearchIndex.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除索引", token = true )
    public String deleteSearchIndex( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        boolean siteMode = StringUtil.getBooleanValue( ( String ) params.get( "siteMode" ), false );

        String mode = ( String ) params.get( "mode" );

        if( siteMode )
        {
            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            searchService.deleteSearchIndexMetadataBySite( site );

            ClusterService.exeClusterMasterCMD( "cluster/clearSiteIndex.do", "siteId="
                + site.getSiteId(), Constant.COMMON.POST );
        }
        else
        {
            List idList = StringUtil.changeStringToList( ( String ) params.get( "idInfo" ), "," );

            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            if( "form".equals( mode ) )
            {
                searchService.deleteSearchIndexMetadataByModelId( idList, site );
            }
            else
            {
                searchService.deleteSearchIndexMetadataByClassId( idList, site );
            }

            ClusterService.exeClusterMasterCMD( "cluster/clearIndex.do", "mode=" + mode
                + "&idInfo=" + params.get( "idInfo" ) + "&siteId=" + site.getSiteId(),
                Constant.COMMON.POST );
        }

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteSk.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除搜索词", token = true )
    public String deleteSk( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        searchService.deleteSearchKeyInfo( idList );

        return "success";
    }

    @RequestMapping( value = "/link.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public ModelAndView link( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long contentId = Long
            .valueOf( StringUtil.getLongValue( ( String ) params.get( "id" ), -1 ) );

        String contentUrl = searchService.retrieveSearchContentUrlLinkByContentId( contentId );

        if(StringUtil.isStringNull( contentUrl ))
        {
            return null;
        }
        
        return ServletUtil.redirect( contentUrl );
    }

    @ResponseBody
    @RequestMapping( value = "/distillKeyword.do", method = { RequestMethod.POST } )
    public String distillKeyword( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String targetStr = ( String ) params.get( "jtopcms_sys_keyword_content" );

        targetStr = SystemSafeCharUtil.decodeFromWeb( targetStr );

        List keyInfoList = null;

        keyInfoList = searchService.disposeTextKeyword( targetStr );

        String key = null;

        StringBuffer buf = new StringBuffer();
        for ( int i = 0; i < keyInfoList.size(); i++ )
        {
            key = ( String ) keyInfoList.get( i );

            if( i < KEY_SIZE )
            {
                buf.append( key + " " );
            }

        }

        return buf.toString();
    }

}
