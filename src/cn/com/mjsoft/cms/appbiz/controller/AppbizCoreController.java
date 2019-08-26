package cn.com.mjsoft.cms.appbiz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.appbiz.bean.SystemApiConfigBean;
import cn.com.mjsoft.cms.appbiz.service.AppbizService;
import cn.com.mjsoft.cms.behavior.InitRSABehavior;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/appbiz" )
public class AppbizCoreController
{
    private static AppbizService abService = AppbizService.getInstance();

    private static Map<String, String> PAK = new HashMap<String, String>();

    @ResponseBody
    @RequestMapping( value = "/appClientGetCMSTime.do", method = { RequestMethod.POST,
        RequestMethod.GET } )
    public Object appClientGetCMSTime( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        return ServletUtil.responseJSON( response, DateAndTimeUtil.clusterTimeMillis() + "" );
    }

    @ResponseBody
    @RequestMapping( value = "/appClientGetPK.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public Object appClientGetPK( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        if( PAK.isEmpty() )
        {
            PAK.put( "pr_key", InitRSABehavior.getPublicKey() );
        }

        return PAK;
    }

    @ResponseBody
    @RequestMapping( value = "/appClientReg.do", method = { RequestMethod.POST } )
    public Object appClientReg( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String sysAESKeyEnc = ( String ) params.get( "sys_app_pak" );

        boolean regOk = abService.appClientKeyReg( sysAESKeyEnc );

        return ServletUtil.responseJSON( response, regOk + "" );
    }

    @ResponseBody
    @RequestMapping( value = "/appGetToken.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public Object appGetToken( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String sysAESKeyEnc = ( String ) params.get( "sys_app_pak" );

        Map<String, String> json = new HashMap<String, String>();

        json.put( "app_token", abService.genAppFlowToken( sysAESKeyEnc ) );

        return json;
    }

    @ResponseBody
    @RequestMapping( value = "/appTokenError.do", method = { RequestMethod.POST } )
    public Object appTokenError( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map<String, String> json = new HashMap<String, String>();

        json.put( "error", "flow-token-error" );

        return json;
    }

    @ResponseBody
    @RequestMapping( value = "/getApiInfo.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public Object getApiInfo( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        String apiPath = ( String ) params.get( "sys_api_path" );

        SystemApiConfigBean sac = abService.retrieveSingleAppCfgBeanByPath( apiPath );

        Map resMap = new HashMap();

        resMap.put( "mustTok", sac.getMustTok() );
        resMap.put( "mustEnc", sac.getMustEnc() );
        resMap.put( "mustSecTok", sac.getMustSecTok() );
        resMap.put( "postMode", Integer.valueOf( "post".equals( sac.getReqMethod() ) ? 1 : 0 ) );

        return resMap;
    }

    @ResponseBody
    @RequestMapping( value = "/getPushInfo.do", method = { RequestMethod.GET } )
    public Object pushInfoToApp( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String pushClassIdFalg = "_sys_push_";

        int pustLimit = 20;

        ContentClassBean pclass = ChannelService.getInstance()
            .retrieveSingleClassBeanInfoByClassFlag( pushClassIdFalg );

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( pclass.getSiteFlag() );

        DataModelBean model = MetaDataService.getInstance().retrieveSingleDataModelBeanById(
            pclass.getContentType() );

        List<Map> pustList = ContentService.getInstance().retrieveLimitModeContent( true, model,
            pclass.getClassId(), Constant.WORKFLOW.CENSOR_STATUS_SUCCESS, "", null, null, 0,
            pustLimit, "", "contentId", "desc" );

        List<Map> jsonList = new ArrayList<Map>();

        Map<String, String> jm = null;

        for ( Map pc : pustList )
        {
            jm = new LinkedHashMap<String, String>();

            String img = "";

            if( pc.get( "jt_push_imgCmsSysReUrl" ) != null )
            {
                String reimg = pc.get( "jt_push_imgCmsSysReUrl" ).toString();

                String surl = site.getSiteUrl();

                if( surl.endsWith( "/" + site.getSiteRoot() + "/" ) )
                {
                    img = surl + site.getImageRoot() + "/" + reimg;
                }
                else
                {
                    img = surl + site.getSiteRoot() + "/" + site.getImageRoot() + "/" + reimg;
                }
            }

            jm.put( "id", pc.get( "contentId" ).toString() );
            jm.put( "title", pc.get( "jt_push_title" ).toString() );
            jm.put( "img", img );
            jm.put( "link", pc.get( "jt_push_link" ).toString() );

            jsonList.add( jm );
        }

        return jsonList;

    }

    @ResponseBody
    @RequestMapping( value = "/getVer.do", method = { RequestMethod.GET } )
    public Object getAppVersion( HttpServletRequest request, HttpServletResponse response )
    {

        String siteFalg = "xym";

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( siteFalg );

        Map<String, String> jm = new LinkedHashMap<String, String>();

        jm = new LinkedHashMap<String, String>();

        jm.put( "app_ver", site.getExt().get( "jt_android_ver" ).toString() );
        jm.put( "ver_desc", site.getExt().get( "jt_ver_desc" ).toString() );
        jm.put( "android_durl", site.getExt().get( "jt_android_durl" ).toString() );
        jm.put( "ios_durl", site.getExt().get( "jt_ios_durl" ).toString() );

        return jm;

    }

}
