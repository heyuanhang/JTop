package cn.com.mjsoft.cms.pick.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.pick.bean.PickContentTaskBean;
import cn.com.mjsoft.cms.pick.service.PickService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/pick" )
public class PickUpWebHtmlContentController
{

    private static PickService pickService = PickService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/pickWeb.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "进行采集", token = false )
    public Object deletePickTrace( HttpServletRequest request, HttpServletResponse response )
    {

        Map params = ServletUtil.getRequestDecodeInfo( request );

        boolean testMode = StringUtil.getBooleanValue( ( String ) params.get( "testMode" ), false );

        boolean singleMode = StringUtil.getBooleanValue( ( String ) params.get( "singleMode" ),
            false );

        boolean innerMode = StringUtil
            .getBooleanValue( ( String ) params.get( "innerMode" ), false );

        String mode = ( String ) params.get( "mode" );

        if( testMode )
        {
            Map testValMap = pickService.pickUpWebContentForTestConfig( Long.valueOf( StringUtil
                .getLongValue( ( String ) params.get( "ruleId" ), -1 ) ) );

            if( testValMap.isEmpty() )
            {
                testValMap.put( "empty", "true" );
            }

            return StringUtil.changeMapToJSON( testValMap );
        }
        else if( singleMode )
        {
            Long classId = Long.valueOf( StringUtil.getLongValue(
                ( String ) params.get( "classId" ), -1 ) );

            boolean isSucc = false;

            String[] targetUrls = StringUtil.split( ( String ) params.get( "targetUrl" ), "\r\n" );

            for ( String targetUrl : targetUrls )
            {

                if( !targetUrl.startsWith( "http" ) )
                {
                    innerMode = true;
                }

                if( innerMode )
                {
                    isSucc = pickService.pickUpSingleInnerContent( targetUrl, classId,
                        Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "ruleId" ),
                            -1 ) ), mode );
                }
                else
                {
                    isSucc = pickService.pickUpSingleWebContent( targetUrl, classId,
                        Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "ruleId" ),
                            -1 ) ), mode );
                }
            }

            if( isSucc )
            {
                return "success";
            }
            else
            {
                return "fail";
            }

        }
        else
        {
            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            Long taskId = StringUtil.getLongValue( ( String ) params.get( "taskId" ), -1 );

            PickContentTaskBean pt = pickService.retrieveSinglePickTaskBeanById( taskId );

            // 本次唯一识别代码(UUID)
            String pubEventKey = ( String ) params.get( "key" );

            if( pt.getPickType() == 1 )
            {
                pickService.pickUpWebContentByConfig( site.getSiteId(), taskId, pubEventKey, mode );
            }
            else if( pt.getPickType() == 2 )
            {
                pickService.pickUpSingleJSONOroutdOrDBInfo( pt, pubEventKey );
            }

            return ServletUtil.redirect( "/core/pick/PickUpContentFromWeb.jsp" );
        }

    }

}
