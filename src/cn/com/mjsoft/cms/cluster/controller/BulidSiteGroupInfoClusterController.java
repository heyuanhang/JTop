package cn.com.mjsoft.cms.cluster.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.common.service.CMSRedisDB;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@Controller
@RequestMapping( "/cluster" )
public class BulidSiteGroupInfoClusterController
{
    @ResponseBody
    @RequestMapping( value = "/cluBSGI.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public String cluBSGI( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String testMode = ( String ) params.get( "sys_test_mode" );

        if( "true".equals( testMode ) )
        {
            return "ok";
        }

        String innerAccessJtopSysFlag = ( String ) params.get( "innerAccessJtopSysFlag" );

        if( CMSRedisDB.existMapKey( "AuthorizationHandler.innerAccessCheckMap",
            innerAccessJtopSysFlag ) )
        {

            InitSiteGroupInfoBehavior.bulidSiteGroupInfoClusterMode();

        }

        // 无论是否正常的accFlag，都要执行remove
        CMSRedisDB.delMapVal( "AuthorizationHandler.innerAccessCheckMap", innerAccessJtopSysFlag );

        return null;

    }

}
