package cn.com.mjsoft.cms.channel.json;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.appbiz.bean.ApiReq;
import cn.com.mjsoft.cms.appbiz.controller.ApiFlowDisposBaseController;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/api" )
public class ClientChannelPathJSonController extends ApiFlowDisposBaseController
{
    private ChannelService channelService = ChannelService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/getChannelPath.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public Object executeBiz( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        // API请求前置处理
        ApiReq ar = apiReqCheck( request, response );

        if( ar.getCode() != null )
        {
            return ar.repCode();
        }

        Map params = ar.getParam();

        String classId = ( String ) params.get( "id" );

        long id = StringUtil.getLongValue( classId, -1 );

        if( id < 0 )
        {
            return ServletUtil.responseJSON( response, "{empty:true}" );
        }

        ContentClassBean classBean = channelService.retrieveSingleClassBeanInfoByClassId( Long
            .valueOf( id ) );

        if( classBean == null || classBean.getClassId().longValue() < 0 )
        {
            return ServletUtil.responseJSON( response, "{empty:true}" );
        }

        List resultList = channelService.retrieveContentClassBeanByCurrentPath( classBean
            .getChannelPath() );

      

        // API请求结果处理
        return apiResult( ar, ClientClassJSonFlow.transformJSONData( resultList ) );

    }

}
