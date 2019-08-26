package cn.com.mjsoft.cms.content.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientRelateContentJsonFlow extends ApiFlowDisposBaseFlow
{
    private Logger log = Logger.getLogger( ClientRelateContentJsonFlow.class );

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

        String type = ( String ) params.get( "type" );

        if( StringUtil.isStringNull( type ) )
        {
            type = "";
        }

        String size = ( String ) params.get( "size" );

        if( StringUtil.isStringNull( size ) )
        {
            size = "8";
        }

        Map info = contentService.retrieveSingleUserDefineContent( id, Integer
            .valueOf( 1 ) );

        if( info.isEmpty() )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        List strList = new ArrayList();

        String rids = "";

        if( "c".equals( type ) )
        {
            rids = ( String ) info.get( "relateIds" );
        }
        else if( "s".equals( type ) )
        {
            rids = ( String ) info.get( "relateSurvey" );
        }

        int sizeVar = StringUtil.getIntValue( size, 8 );

        if( StringUtil.isStringNotNull( rids ) )
        {
            String[] temp = StringUtil.split( rids, "\\*" );

            if( temp != null )
            {
                for ( int i = 0; i < temp.length; i++ )
                {
                    if( i == sizeVar )
                    {
                        // 达到size
                        break;
                    }

                    strList.add( temp[i] );
                }
            }
        }

        if( strList.isEmpty() )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        List result = new ArrayList();

        long contentId = -1;

        for ( int i = 0; i < strList.size(); i++ )
        {
            contentId = StringUtil.getLongValue( ( String ) strList.get( i ),
                -1 );

            if( contentId < 0 )
            {
                continue;
            }

            result.add( contentService.retrieveSingleUserDefineContent(
                contentId, Integer.valueOf( 1 ) ) );
        }

        return ( JSON.toJSONString( result ) );
    }

}
