package cn.com.mjsoft.cms.content.json;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.appbiz.bean.ApiReq;
import cn.com.mjsoft.cms.appbiz.controller.ApiFlowDisposBaseController;
import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/api" )
public class ContentClickAndCommStatusJsonController extends ApiFlowDisposBaseController
{
    private Logger log = Logger.getLogger( ContentClickAndCommStatusJsonController.class );

    private static ContentService contentService = ContentService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/getContentStatus.do", method = { RequestMethod.POST,
        RequestMethod.GET } )
    public Object executeBiz( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        ApiReq ar = apiReqCheck( request ,response );

        if( ar.getCode() != null )
        {
            return ar.getCode();
        }

        Map params = ar.getParam();

        // 内容ID
        String idV = StringUtil.notNull( ( String ) params.get( "id" ) );

        Long id = Long.valueOf( StringUtil.getLongValue( idV, -1 ) );

        if( id.longValue() < 0 )
        {
            return ( "{empty:true}" );
        }

        Map csMap = contentService.retrieveSingleContentStatus( id );

        return apiResult( ar, csMap );
    }
}
