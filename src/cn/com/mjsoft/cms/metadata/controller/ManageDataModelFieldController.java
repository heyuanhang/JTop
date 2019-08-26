package cn.com.mjsoft.cms.metadata.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.dao.vo.ModelFiledMetadata;
import cn.com.mjsoft.cms.metadata.dao.vo.ModelHtmlConfig;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.support.ServiceState;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/metadata" )
public class ManageDataModelFieldController
{
    private static MetaDataService metaDataService = MetaDataService.getInstance();

    @RequestMapping( value = "/addModelFiled.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加模型字段", token = true )
    public ModelAndView addModelFiled( HttpServletRequest request, HttpServletResponse response )
    {

        Set htmlFieldSet = new HashSet();
        htmlFieldSet.add( "htmlContent" );

        Map params = ServletUtil.getRequestInfo( request, htmlFieldSet );

        ModelHtmlConfig config = ( ModelHtmlConfig ) ServletUtil.getValueObject( request,
            ModelHtmlConfig.class );

        ModelFiledMetadata metadata = ( ModelFiledMetadata ) ServletUtil.getValueObject( request,
            ModelFiledMetadata.class );

        String[] defaultValidateChecked = StringUtil.getCheckBoxValue( params
            .get( "defaultValidate" ) );

        StringBuffer buf = new StringBuffer();

        for ( int i = 0; i < defaultValidateChecked.length; i++ )
        {
            buf.append( defaultValidateChecked[i] );
            if( i + 1 != defaultValidateChecked.length )
            {
                buf.append( "," );
            }
        }

        // 关联
        if( config.getLinkModelId() != null && config.getLinkModelId().longValue() > 0 )
        {
            config.setDataType( Constant.METADATA.LONG_TYPE );
        }

        config.setDefaultValidate( buf.toString() );

        Long dataModelId = StringUtil.getLongValue( ( String ) params.get( "dataModelId" ), -1 );

        ServiceState state = null;

        state = metaDataService.createOrEditDataModelFiledMetadataAndHtmlConfig( config, metadata,
            false );

        // 更新索引,大数据量时,需要花费大量时间,需友好提示
        metaDataService.updateMetadataTableIndex( dataModelId );

        // 如果是新增加的字段,要根据默认值进行更新

        String defVal = config.getDefaultValue();

        if( StringUtil.isStringNotNull( defVal ) )
        {
            String fieldName = Constant.METADATA.PREFIX_COLUMN_NAME + metadata.getFiledSign();

            DataModelBean model = metaDataService.retrieveSingleDataModelBeanById( dataModelId );

            metaDataService.updateFieldMetadataDefVal( model, fieldName, defVal );
        }

        Map paramMap = new HashMap();

        if( state != null )
        {
            paramMap.put( "errorMsg", state.getMessage() );

            if( !ServiceState.SUCCESS.equals( state.getPerformStatus() ) )
            {
                paramMap.put( "error", Boolean.TRUE );
            }
        }

        paramMap.put( "modelId", dataModelId );
        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/metadata/AddModelFiled.jsp", paramMap );

    }

    @RequestMapping( value = "/editModelFiled.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑模型字段", token = true )
    public ModelAndView editModelFiled( HttpServletRequest request, HttpServletResponse response )
    {

        Set htmlFieldSet = new HashSet();
        htmlFieldSet.add( "htmlContent" );

        Map params = ServletUtil.getRequestInfo( request, htmlFieldSet );

        ModelHtmlConfig config = ( ModelHtmlConfig ) ServletUtil.getValueObject( request,
            ModelHtmlConfig.class );

        ModelFiledMetadata metadata = ( ModelFiledMetadata ) ServletUtil.getValueObject( request,
            ModelFiledMetadata.class );

        String[] defaultValidateChecked = StringUtil.getCheckBoxValue( params
            .get( "defaultValidate" ) );

        StringBuffer buf = new StringBuffer();

        for ( int i = 0; i < defaultValidateChecked.length; i++ )
        {
            buf.append( defaultValidateChecked[i] );
            if( i + 1 != defaultValidateChecked.length )
            {
                buf.append( "," );
            }
        }

        // 关联
        if( config.getLinkModelId() != null && config.getLinkModelId().longValue() > 0 )
        {
            config.setDataType( Constant.METADATA.LONG_TYPE );
        }

        config.setDefaultValidate( buf.toString() );

        Long dataModelId = StringUtil.getLongValue( ( String ) params.get( "dataModelId" ), -1 );

        ServiceState state = null;

        Long fieldId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "fieldId" ),
            -1 ) );

        metadata.setMetaDataId( fieldId );
        config.setMetaDataId( fieldId );

        state = metaDataService.createOrEditDataModelFiledMetadataAndHtmlConfig( config, metadata,
            true );

        // 更新索引,大数据量时,需要花费大量时间,需友好提示
        metaDataService.updateMetadataTableIndex( dataModelId );

        Map paramMap = new HashMap();

        if( state != null )
        {
            paramMap.put( "errorMsg", state.getMessage() );

            if( !ServiceState.SUCCESS.equals( state.getPerformStatus() ) )
            {
                paramMap.put( "error", Boolean.TRUE );
            }
        }

        paramMap.put( "modelId", dataModelId );
        paramMap.put( "fieldId", fieldId );
        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/metadata/EditModelFiled.jsp", paramMap );

    }

    @RequestMapping( value = "/deleteModelFiled.do", method = { RequestMethod.POST, RequestMethod.GET  } )
    @ActionInfo( traceName = "删除数据字段", token = true )
    public ModelAndView deleteModelFiled( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long filedMetedataId = StringUtil.getLongValue( ( String ) params.get( "filedMetedataId" ),
            -1 );

        Long modelId = StringUtil.getLongValue( ( String ) params.get( "modelId" ), -1 );

        metaDataService.deleteDataModelFiledMetadataAndHtmlConfig( filedMetedataId, modelId );

        // 更新索引,大数据量时,需要花费大量时间,需友好提示
        metaDataService.updateMetadataTableIndex( modelId );

        Map paramMap = new HashMap();

        paramMap.put( "modelId", modelId );
        paramMap.put( "modelType", ( String ) params.get( "modelType" ) );

        return ServletUtil.redirect( "/core/metadata/ManageModelMetadata.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/changeFieldOrder.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "模型字段排序", token = true )
    public String changeFieldOrder( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String newOrder = ( String ) params.get( "newOrderInfo" );
        String[] newOrderInfo = null;

        Map orderInfoMap = new HashMap();

        if( StringUtil.isStringNotNull( newOrder ) )
        {
            String[] info = null;

            newOrderInfo = StringUtil.split( newOrder, "\\*" );

            for ( String or : newOrderInfo )
            {
                info = StringUtil.split( or, "-" );

                if( StringUtil.getDoubleValue( info[1], -1 ) < 0 )
                {
                    // 过滤不合法的order
                    continue;
                }

                orderInfoMap.put( Long.valueOf( info[0] ), Double.valueOf( StringUtil
                    .getDoubleValue( info[1], -1 ) ) );
            }
        }

        String blankCount = ( String ) params.get( "blankCountInfo" );

        String[] blankCountInfo = null;

        Map bcInfoMap = new HashMap();

        if( StringUtil.isStringNotNull( blankCount ) )
        {
            String[] info = null;

            blankCountInfo = StringUtil.split( blankCount, "\\*" );

            for ( String bc : blankCountInfo )
            {
                info = StringUtil.split( bc, "-" );

                if( info.length < 2 || StringUtil.getIntValue( info[1], -1 ) < 0 )
                {
                    // 过滤不合法的order
                    continue;
                }

                bcInfoMap.put( Long.valueOf( info[0] ), Integer.valueOf( StringUtil.getIntValue(
                    info[1], -1 ) ) );
            }
        }

        Long modelId = StringUtil.getLongValue( ( String ) params.get( "modelId" ), -1 );

        metaDataService.updateModelFileldOrderAndBlankCount( modelId, orderInfoMap, bcInfoMap );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/getLinkFieldJson.do", method = { RequestMethod.POST } )
    public Object getLinkFieldJson( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long modelId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "modelId" ),
            -1 ) );

        List fieldList = metaDataService.retrieveAllModelLinkFieldInfoByModelId( modelId );

        return StringUtil.changeJSON( fieldList, "obj_" );
 
    }

}
