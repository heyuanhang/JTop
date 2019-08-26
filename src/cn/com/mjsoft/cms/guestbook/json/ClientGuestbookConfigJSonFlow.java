package cn.com.mjsoft.cms.guestbook.json;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.guestbook.service.GuestbookService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientGuestbookConfigJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger.getLogger( ClientGuestbookConfigJSonFlow.class );

    private static GuestbookService gbService = GuestbookService.getInstance();

    @Override
    protected String executeBiz() throws Exception
    {
        HttpServletRequest request = this.getServletFlowContext().getRequest();

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        String configId = ( String ) params.get( "configId" );

        List result = Collections.EMPTY_LIST;

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
            return responseAjaxTextMessage( JSON.toJSONString( "{empty:true}" ) );
        }

        if( "all".equals( configId ) )
        {
            result = gbService.retrieveAllGuestbookConfigBeanListBySite( site.getSiteId() );

            return responseAjaxTextMessage( JSON.toJSONString( result ) );
        }
        else
        {

            Long configIdVar = Long.valueOf( StringUtil.getLongValue( configId, -1 ) );

            if( configIdVar.longValue() > 0 )
            {
                return responseAjaxTextMessage( JSON.toJSONString( gbService
                    .retrieveSingleGuestbookConfigBeanByConfigId( configIdVar ) ) );
            }
        }

        return responseAjaxTextMessage( JSON.toJSONString( "{empty:true}" ) );
    }
}
