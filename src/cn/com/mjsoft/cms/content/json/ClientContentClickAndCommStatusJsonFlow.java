package cn.com.mjsoft.cms.content.json;

import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientContentClickAndCommStatusJsonFlow extends
    ApiFlowDisposBaseFlow
{
    private Logger log = Logger
        .getLogger( ClientContentClickAndCommStatusJsonFlow.class );

    private static ContentService contentService = ContentService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        Map params = this.getFlowContext().getHttpRequestSnapshot();

        // 内容ID
        String idV = StringUtil.notNull( ( String ) params.get( "id" ) );

        Long id = Long.valueOf( StringUtil.getLongValue( idV, -1 ) );

        if( id.longValue() < 0 )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        Map csMap = contentService.retrieveSingleContentStatus( id );

        return ( JSON.toJSONString( csMap ) );
    }
}
