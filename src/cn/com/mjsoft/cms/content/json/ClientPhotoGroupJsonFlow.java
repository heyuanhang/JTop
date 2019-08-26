package cn.com.mjsoft.cms.content.json;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientPhotoGroupJsonFlow extends ApiFlowDisposBaseFlow
{
    private Logger log = Logger.getLogger( ClientPhotoGroupJsonFlow.class );

    private static ContentService contentService = ContentService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        HttpServletRequest request = this.getServletFlowContext().getRequest();

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        // 内容ID
        String idV = StringUtil.notNull( ( String ) params.get( "id" ) );

        Long id = Long.valueOf( StringUtil.getLongValue( idV, -1 ) );

        if( id.longValue() < 0 )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        String group = ( String ) params.get( "group" );// 对应模型字段标识

        if( StringUtil.isStringNull( group ) )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        String modelType = ( String ) params.get( "modelType" );

        String siteId = ( String ) params.get( "siteId" );// 指定站点ID,表单模式使用

        if( StringUtil.isStringNull( siteId ) )
        {
            siteId = "-1";
        }

        Long cid = id;

        if( cid != null && cid.longValue() > 0 )
        {
            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( StringUtil.getLongValue( siteId, -1 ) );

            if( site == null )
            {
                site = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( request );
            }

            if( site == null )
            {
                return ( JSON.toJSONString( "{empty:true}" ) );
            }

            List result = contentService
                .retrieveGroupPhotoInfoByContentId( cid, group, StringUtil
                    .getIntValue( modelType, 2 ), site, false );

            if( result.isEmpty() )
            {
                return ( JSON.toJSONString( "{empty:true}" ) );
            }

            return ( JSON.toJSONString( result ) );

        }

        return ( JSON.toJSONString( "{empty:true}" ) );

    }

}
