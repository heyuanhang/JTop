package cn.com.mjsoft.cms.metadata.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.DataModelJSONBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientModelJsonFlow extends ApiFlowDisposBaseFlow
{
    private Logger log = Logger.getLogger( ClientModelJsonFlow.class );

    private static MetaDataService metaDataService = MetaDataService
        .getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        HttpServletRequest request = this.getServletFlowContext().getRequest();

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        // 内容ID
        Integer modelType = StringUtil.getIntValue( ( String ) params
            .get( "modelType" ), Constant.METADATA.MODEL_TYPE_CONTENT
            .intValue() );

        String siteId = ( String ) params.get( "siteId" );// 指定站点ID,表单模式使用

        if( StringUtil.isStringNull( siteId ) )
        {
            siteId = "-1";
        }

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( StringUtil.getLongValue( siteId, -1 ) );

        if( site == null )
        {
            site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );
        }

        if( site == null )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        List result = metaDataService
            .retrieveAllDataModelBeanListByModelTypeAndModeAndSiteId(
                modelType, "false", site.getSiteId() );

        if( result.isEmpty() )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        return ( JSON.toJSONString( transformJSONData( result ) ) );
    }

    public static List transformJSONData( List cbList )
    {
        if( cbList == null || cbList.isEmpty() )
        {
            return Collections.EMPTY_LIST;
        }

        DataModelBean mBean = null;

        List jbList = new ArrayList( cbList.size() );

        for ( int i = 0; i < cbList.size(); i++ )
        {
            mBean = ( DataModelBean ) cbList.get( i );

            jbList.add( transformJSONData( mBean ) );
        }

        return jbList;
    }

    public static DataModelJSONBean transformJSONData( DataModelBean mBean )
    {

        DataModelJSONBean jsonBean = new DataModelJSONBean();

        if( mBean == null )
        {
            return null;
        }

        jsonBean.setDataModelId( mBean.getDataModelId() );
        jsonBean.setModelName( mBean.getModelName() );
        jsonBean.setModelSign( mBean.getModelSign() );
        jsonBean.setRelateTableName( mBean.getRelateTableName() );
        jsonBean.setRemark( mBean.getRemark() );
        jsonBean.setModelType( mBean.getModelType() );
        jsonBean.setUseState( mBean.getUseState() );
        jsonBean.setModelResType( mBean.getModelResType() );
        jsonBean.setMainEditorFieldSign( mBean.getMainEditorFieldSign() );
        jsonBean.setAfterBehavior( mBean.getAfterBehavior() );
        jsonBean.setBeforeBehavior( mBean.getBeforeBehavior() );
        jsonBean.setValidateBehavior( mBean.getValidateBehavior() );
        jsonBean.setPrivateMode( mBean.getPrivateMode() );
        jsonBean.setSiteId( mBean.getSiteId() );
        jsonBean.setIco( mBean.getIco() );
        jsonBean.setIsManageEdit( mBean.getIsManageEdit() );
        jsonBean.setMustCensor( mBean.getMustCensor() );
        jsonBean.setIsMemberEdit( mBean.getIsMemberEdit() );
        jsonBean.setMustToken( mBean.getMustToken() );

        return jsonBean;

    }
}
