package cn.com.mjsoft.cms.channel.json;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientChannelPathJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger
        .getLogger( ClientChannelPathJSonFlow.class );

    private ChannelService channelService = ChannelService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        Map params = this.getFlowContext().getHttpRequestSnapshot();

        String classId = ( String ) params.get( "id" );

        long id = StringUtil.getLongValue( classId, -1 );

        if( id < 0 )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        ContentClassBean classBean = channelService
            .retrieveSingleClassBeanInfoByClassId( Long.valueOf( id ) );

        if( classBean == null || classBean.getClassId().longValue() < 0 )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        List resultList = channelService
            .retrieveContentClassBeanByCurrentPath( classBean.getChannelPath() );

        return ( JSON.toJSONString( ClientClassJSonFlow
            .transformJSONData( resultList ) ) );

    }

}
