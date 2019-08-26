package cn.com.mjsoft.cms.content.json;

import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientNextOrPrevJsonFlow extends ApiFlowDisposBaseFlow
{
    private Logger log = Logger.getLogger( ClientContentJsonFlow.class );

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

        String flag = ( String ) params.get( "flag" );

        if( StringUtil.isStringNull( flag ) )
        {
            flag = "n";
        }

        Map info = contentService.retrieveSingleUserDefineContent( id, Integer
            .valueOf( 1 ) );

        if( info == null || info.isEmpty() )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        Double orderIdFlag = ( Double ) info.get( "orderIdFlag" );

        Long classId = ( Long ) info.get( "classId" );

        Long modelId = ( Long ) info.get( "modelId" );

        Map ncInfo = contentService.retrieveSingleNextOrPrevContentById(
            orderIdFlag, classId, modelId, flag );

        if( ncInfo == null || ncInfo.isEmpty() )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        return ( JSON.toJSONString( ncInfo ) );
    }

}
