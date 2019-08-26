package cn.com.mjsoft.cms.search.json;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.search.service.SearchService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientSearchKeyJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger
        .getLogger( ClientSearchKeyJSonFlow.class );

    private static SearchService searchService = SearchService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        HttpServletRequest request = this.getServletFlowContext().getRequest();

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        String size = ( String ) params.get( "size" );

        String siteId = ( String ) params.get( "siteId" );// 指定站点ID,表单模式使用

        if( StringUtil.isStringNull( siteId ) )
        {
            siteId = "-1";
        }

        SiteGroupBean siteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( StringUtil.getLongValue( siteId, -1 ) );

        if( siteBean == null )
        {
            siteBean = SiteGroupService
                .getCurrentSiteInfoFromWebRequest( request );
        }

        if( siteBean == null )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        List result = searchService.retrieveSearchKeyCountInfoBySiteId(
            siteBean.getSiteId(), Integer.valueOf( StringUtil.getIntValue(
                size, 15 ) ) );

        if( result.isEmpty() )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        return ( JSON.toJSONString( result ) );

    }

}
