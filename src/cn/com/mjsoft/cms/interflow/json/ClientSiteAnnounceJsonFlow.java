package cn.com.mjsoft.cms.interflow.json;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.interflow.service.InterflowService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientSiteAnnounceJsonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger
        .getLogger( ClientSiteAnnounceJsonFlow.class );

    private static InterflowService inService = InterflowService.getInstance();

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

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( StringUtil.getLongValue( siteId, -1 ) );

        if( site == null )
        {
            site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );
        }

        if( site == null )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        // 注意：只精确到日期的非精确时间要求，无需集群时间同步
        Calendar cal = Calendar.getInstance();

        cal.set( Calendar.HOUR_OF_DAY, 0 );
        cal.set( Calendar.SECOND, 0 );
        cal.set( Calendar.MINUTE, 0 );

        Date cd = cal.getTime();

        int pageSize = StringUtil.getIntValue( size, 20 );

        List result = inService.retrieveSiteAnnounceBeanListByCurrDate( site
            .getSiteId(), cd, Long.valueOf( 0 ), Integer.valueOf( pageSize ) );

        if( result.isEmpty() )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        return ( JSON.toJSONString( result ) );

    }
}
