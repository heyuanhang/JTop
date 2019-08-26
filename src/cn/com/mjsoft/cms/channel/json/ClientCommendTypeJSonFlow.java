package cn.com.mjsoft.cms.channel.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeJSONBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientCommendTypeJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger
        .getLogger( ClientCommendTypeJSonFlow.class );

    private ChannelService channelService = ChannelService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        HttpServletRequest request = this.getServletFlowContext().getRequest();

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        String typeId = ( String ) params.get( "typeId" );

        if( StringUtil.isStringNull( typeId ) )
        {
            typeId = "";
        }

        String classId = ( String ) params.get( "classId" );

        if( StringUtil.isStringNull( classId ) )
        {
            classId = "";
        }

        String showAll = ( String ) params.get( "showAll" );

        if( StringUtil.isStringNull( showAll ) )
        {
            showAll = "false";
        }

        String isSpec = ( String ) params.get( "isSpec" );

        if( StringUtil.isStringNull( isSpec ) )
        {
            isSpec = "false";
        }

        String siteId = ( String ) params.get( "siteId" );

        if( StringUtil.isStringNull( siteId ) )
        {
            siteId = "-1";
        }

        if( !"".equals( typeId ) )
        {
            ContentCommendTypeBean commTypeBean = channelService
                .retrieveSingleContentCommendTypeBeanByTypeId( Long
                    .valueOf( StringUtil.getLongValue( typeId, -1 ) ) );

            return ( JSON.toJSONString( transformJSONData( commTypeBean ) ) );

        }
        else
        {

            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                .getEntry( StringUtil.getLongValue( siteId, -1 ) );

            if( site == null )
            {
                site = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( request );
            }

            if( site == null )
            {
                return ( JSON.toJSONString( "{empty:true}" ) );
            }

            List resultList = channelService.retrieveContentCommendTypeBean(
                site.getSiteFlag(), Long.valueOf( StringUtil.getLongValue(
                    classId, -1 ) ), StringUtil
                    .getBooleanValue( showAll, false ), StringUtil
                    .getBooleanValue( isSpec, false ), false );
            if( resultList.isEmpty() )
            {
                return ( JSON.toJSONString( "{empty:true}" ) );
            }

            // 确定站点是否传递错误
            String cSiteId = ( String ) ( ( ContentCommendTypeBean ) resultList
                .get( 0 ) ).getSiteFlag();

            if( !site.getSiteFlag().equals( cSiteId ) )
            {
                return ( JSON.toJSONString( "{empty:true}" ) );
            }

            return ( JSON.toJSONString( transformJSONData( resultList ) ) );

        }

    }

    public static List transformJSONData( List cbList )
    {
        if( cbList == null || cbList.isEmpty() )
        {
            return Collections.EMPTY_LIST;
        }

        ContentCommendTypeBean ctBean = null;

        List jbList = new ArrayList( cbList.size() );

        for ( int i = 0; i < cbList.size(); i++ )
        {
            ctBean = ( ContentCommendTypeBean ) cbList.get( i );

            jbList.add( transformJSONData( ctBean ) );
        }

        return jbList;
    }

    public static ContentCommendTypeJSONBean transformJSONData(
        ContentCommendTypeBean ctBean )
    {

        ContentCommendTypeJSONBean jsonBean = new ContentCommendTypeJSONBean();

        if( ctBean == null )
        {
            return null;
        }

        jsonBean.setCommendName( ctBean.getCommendName() );
        jsonBean.setCommendTypeId( ctBean.getCommendTypeId() );
        jsonBean.setCommFlag( ctBean.getCommFlag() );
        jsonBean.setTypeDesc( ctBean.getTypeDesc() );
        jsonBean.setChildClassMode( ctBean.getChildClassMode() );
        jsonBean.setClassId( ctBean.getClassId() );
        jsonBean.setIsSpec( ctBean.getIsSpec() );
        jsonBean.setCreator( ctBean.getCreator() );
        jsonBean.setClassLinerFlag( ctBean.getClassLinerFlag() );
        jsonBean.setMustCensor( ctBean.getMustCensor() );
        jsonBean.setSiteFlag( ctBean.getSiteFlag() );
        jsonBean.setImageHeight( ctBean.getImageHeight() );
        jsonBean.setImageWidth( ctBean.getImageWidth() );
        jsonBean.setListProduceType( ctBean.getListProduceType() );
        jsonBean.setListPublishRuleId( ctBean.getListPublishRuleId() );
        jsonBean.setListStaticUrl( ctBean.getListStaticUrl() );
        jsonBean.setListTemplateUrl( ctBean.getListTemplateUrl() );
        jsonBean.setUrl( ctBean.getUrl() );

        return jsonBean;

    }

}
