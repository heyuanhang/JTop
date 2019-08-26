package cn.com.mjsoft.cms.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.service.CommonSystemService;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.IPSeeker;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/common" )
public class EditCmsRuntimeConfigController
{
    public static final String CS_FILE_NAME = "/WEB-INF/config/cs.properties";

    private static CommonSystemService csService = CommonSystemService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/editRTcfg.do", method = { RequestMethod.POST } )
    public Object editRTcfg( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        if( !SecuritySessionKeeper.getSecuritySession().isManager() )
        {
            return null;
        }

        String rtFilePath = ServletUtil.getSiteFilePath( request.getServletContext() )
            + CS_FILE_NAME;

        ServiceUtil.decodeAndDisposeMapParam( params );

        // 加入当前IP,防止当前所有管理员都无法登陆
        String ip = IPSeeker.getIp( request );

        String mip = ( String ) params.get( "managerIp" );

        if( StringUtil.isStringNotNull( mip ) && mip.indexOf( ip ) == -1 )
        {
            params.put( "managerIp", mip + "," + ip );
        }

        csService.editCmsRuntimeConfig( params, rtFilePath );

        return "success";
    }

}
