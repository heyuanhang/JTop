package cn.com.mjsoft.cms.stat.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.cluster.adapter.ClusterMapAdapter;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.cms.stat.service.StatService;

import com.alibaba.fastjson.JSON;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/stat" )
public class ClientQuerySiteVisStatController
{
    private static StatService statService = StatService.getInstance();

    public static final ClusterMapAdapter SITE_VIS_STAT = new ClusterMapAdapter( "SITE_VIS_STAT",
        Long.class, String.class );

    @ResponseBody
    @RequestMapping( value = "/visInfo.do", method = { RequestMethod.POST } )
    public String visInfo( HttpServletRequest request, HttpServletResponse response )
    {
        // 来自发布逻辑的访问,根据管理站点确定当前站点
        SiteGroupBean siteBean = ( SiteGroupBean ) request
            .getAttribute( Constant.CONTENT.HTML_PUB_CURRENT_SITE );

        if( siteBean == null )
        {
            siteBean = ( SiteGroupBean ) request.getAttribute( "SiteObj" );

            if( siteBean == null )
            {
                // 根据URL来判断站点
                siteBean = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) request );
            }
        }

        String visStat = ( String ) SITE_VIS_STAT.get( siteBean.getSiteId() );

        if( visStat == null )
        {
            visStat = JSON.toJSONString( ( Map ) statService.getSiteAllVisitInfoTag( siteBean ) );

            SITE_VIS_STAT.put( siteBean.getSiteId(), visStat );
        }

        return visStat;
    }

}
