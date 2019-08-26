package cn.com.mjsoft.cms.cluster.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.mjsoft.cms.behavior.InitClientIpAccessBehavior;
import cn.com.mjsoft.cms.common.service.CMSRedisDB;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@Controller
@RequestMapping( "/cluster" )
public class BlackIPClearController
{
    private static Logger log = Logger.getLogger( BlackIPClearController.class );

    @RequestMapping( value = "/clearBlackIP.do", method = { RequestMethod.POST } )
    public void clearBlackIP( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String innerAccessJtopSysFlag = ( String ) params.get( "innerAccessJtopSysFlag" );

        if( CMSRedisDB.existMapKey( "AuthorizationHandler.innerAccessCheckMap",
            innerAccessJtopSysFlag ) )
        {

            InitClientIpAccessBehavior.disposeBlackIpAccessCulster();

        }

        // 无论是否正常的accFlag，都要执行remove
        CMSRedisDB.delMapVal( "AuthorizationHandler.innerAccessCheckMap", innerAccessJtopSysFlag );

    }
}
