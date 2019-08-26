package cn.com.mjsoft.cms.member.controller;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/member" )
public class ThirdLoginRequestController
{
    @ResponseBody
    @RequestMapping( value = "/thirdLoginReq.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public ModelAndView thirdLoginReq( HttpServletRequest request, HttpServletResponse response )
    {

        response.setContentType( "text/html;charset=utf-8" );
        try
        {
            String uuid = StringUtil.getUUIDString();

            request.getSession().setAttribute( "__sys_third_login_uid__", uuid );

            Map params = ServletUtil.getRequestInfo( request );

            String flag = ( String ) params.get( "flag" );

            SiteGroupBean site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );

            if( "qq".equals( flag ) )
            {

                String clientId = site.getQqAppId();

                String redirect_uri = URLEncoder.encode( site.getSiteUrl()
                    + "member/qqLoginAfter.do", "UTF-8" );

                String url = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id="
                    + clientId + "&redirect_uri=" + redirect_uri + "&state=" + uuid;

                response.sendRedirect( url );
            }
            else if( "weibo".equals( flag ) )
            {
                String clientId = site.getWbAppId();

                String redirect_uri = URLEncoder.encode( site.getSiteUrl()
                    + "member/weiboLoginAfter.do", "UTF-8" );

                String url = "https://api.weibo.com/oauth2/authorize?response_type=code&client_id="
                    + clientId + "&redirect_uri=" + redirect_uri + "&state=" + uuid;

                response.sendRedirect( url );
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return null;
    }

}
