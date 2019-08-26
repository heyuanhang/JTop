package cn.com.mjsoft.cms.content.json;

import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.html.ParamUtilTag;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientContentJsonFlow extends ApiFlowDisposBaseFlow
{
    private Logger log = Logger.getLogger( ClientContentJsonFlow.class );

    private static ContentService contentService = ContentService.getInstance();

    private static MetaDataService metaDataService = MetaDataService
        .getInstance();

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

        // 是否表单模式
        String formMode = StringUtil.notNull( ( String ) params
            .get( "formMode" ) );

        // 从URL获取内容分页参数,如果模型是文章资源类型,会特别处理
        int posVal = StringUtil.getIntValue( ( String ) params.get( "pn" ), 1 );

        // 根据模型ID获取对应contentId的全部信息
        Map info = null;

        if( "true".equals( formMode ) )
        {
            info = metaDataService.retrieveSingleFormDataById( id );
        }
        else
        {
            info = contentService.retrieveSingleUserDefineContent( id, Integer
                .valueOf( posVal ) );
        }

        // 不允许看到没有审核通过的数据
        if( !Constant.WORKFLOW.CENSOR_STATUS_SUCCESS.equals( ( Integer ) info
            .get( "censorState" ) ) )
        {
            // view mode

            String pcode = ( String ) params.get( "___sys_cms_preview___" );

            if( StringUtil.isStringNotNull( pcode ) )
            {
                String flag = "";

                try
                {
                    flag = ParamUtilTag.decodePW( pcode, "A" );

                }
                catch ( Exception e )
                {
                    flag = "";
                }

                if( flag.startsWith( "true" ) )
                {
                    return ( JSON.toJSONString( info ) );
                }
            }

            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        return ( JSON.toJSONString( info ) );
    }

}
