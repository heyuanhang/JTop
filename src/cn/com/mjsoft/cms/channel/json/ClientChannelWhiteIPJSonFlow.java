package cn.com.mjsoft.cms.channel.json;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.framework.util.IPSeeker;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientChannelWhiteIPJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger
        .getLogger( ClientChannelWhiteIPJSonFlow.class );

    private ChannelService channelService = ChannelService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        HttpServletRequest request = this.getServletFlowContext().getRequest();

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        String classId = ( String ) params.get( "classId" );

        long id = StringUtil.getLongValue( classId, -1 );

        if( id < 0 )
        {
            return responseAjaxTextMessage( JSON.toJSONString( "{ipOk:0}" ) );
        }

        Long cid = Long.valueOf( StringUtil.getLongValue( classId, -1 ) );

        ContentClassBean classBean = channelService
            .retrieveSingleClassBeanInfoByClassId( cid );

        // 检查是否存在白名单
        String whiteIp = classBean.getWhiteIp();

        if( StringUtil.isStringNotNull( whiteIp ) )
        {
            String loginIp = IPSeeker.getIp( request );

            if( !ServiceUtil.checkWhiteIP( whiteIp, loginIp ) )
            {
                return responseAjaxTextMessage( JSON.toJSONString( "{ipOk:0}" ) );
            }
        }

        return responseAjaxTextMessage( JSON.toJSONString( "{ipOk:1}" ) );

    }

}
