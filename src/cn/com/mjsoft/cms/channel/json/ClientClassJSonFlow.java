package cn.com.mjsoft.cms.channel.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.bean.ContentClassJSONBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

public class ClientClassJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger.getLogger( ClientClassJSonFlow.class );

    private ChannelService channelService = ChannelService.getInstance();

    @SuppressWarnings( "unchecked" )
    public String executeBiz() throws Exception
    {
        HttpServletRequest request = this.getServletFlowContext().getRequest();

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        String flagMode = ( String ) params.get( "flagMode" );

        if( StringUtil.isStringNull( flagMode ) )
        {
            flagMode = "false";
        }

        String id = ( String ) params.get( "id" );

        String idList = ( String ) params.get( "idList" );// xx,xx,xx形式的id
        // list,支持id和flag
        if( StringUtil.isStringNull( idList ) )
        {
            idList = "";
        }

        String modelId = ( String ) params.get( "modelId" );

        if( StringUtil.isStringNull( modelId ) )
        {
            modelId = "-1";
        }

        String order = ( String ) params.get( "order" );

        if( StringUtil.isStringNull( order ) )
        {
            order = "up";
        }

        String specMode = ( String ) params.get( "specMode" );

        if( StringUtil.isStringNull( specMode ) )
        {
            specMode = "false";
        }

        String specComm = ( String ) params.get( "specComm" );

        if( StringUtil.isStringNull( specComm ) )
        {
            specComm = "false";
        }

        String objName = ( String ) params.get( "objName" );// 嵌套使用需要改变名称

        if( StringUtil.isStringNull( objName ) )
        {
            objName = "Class";
        }

        if( StringUtil.isStringNotNull( id ) )
        {
            ContentClassBean classBean = null;

            if( "false".equals( flagMode ) )
            {
                Long targetId = Long.valueOf( StringUtil.getLongValue( id, -1 ) );

                if( targetId < 0 )
                {
                    classBean = channelService.retrieveSingleClassBeanInfoByClassFlag( id );
                }
                else
                {
                    classBean = channelService.retrieveSingleClassBeanInfoByClassId( targetId );
                }
            }
            else if( "true".equals( flagMode ) )
            {
                classBean = channelService.retrieveSingleClassBeanInfoByClassFlag( id );
            }

            return ( JSON.toJSONString( transformJSONData( classBean ) ) );
        }
        else
        {
            // 来自发布逻辑的访问,根据管理站点确定当前站点
            SiteGroupBean siteBean = ( SiteGroupBean ) request
                .getAttribute( Constant.CONTENT.HTML_PUB_CURRENT_SITE );

            if( siteBean == null )
            {
                siteBean = ( SiteGroupBean ) request.getAttribute( "SiteObj" );

                if( siteBean == null )
                {
                    // 根据URL来判断站点
                    siteBean = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );
                }
            }

            List result = Collections.EMPTY_LIST;

            List classBeanList = null;

            Long paramClassId = null;

            if( idList.indexOf( "," ) != -1 )
            {
                // 传入的多个ID
                if( "true".equals( flagMode ) )
                {
                    String[] flagArray = StringUtil.split( idList, "," );

                    classBeanList = channelService.retrieveClassBeanInfoBySomeFlags( flagArray,
                        "down" );
                }
                else
                {
                    classBeanList = channelService.retrieveClassBeanInfoBySomeIds( StringUtil
                        .changeStringToList( idList, "," ), "down" );
                }
            }
            else if( idList.startsWith( "parent:" ) )
            {
                // 直接父栏目
                if( "true".equals( flagMode ) )
                {
                    paramClassId = channelService.retrieveSingleClassBeanInfoByClassFlag(
                        StringUtil.replaceString( idList, "parent:", "", false, false ) )
                        .getParent();
                }
                else
                {
                    paramClassId = channelService.retrieveSingleClassBeanInfoByClassId(
                        Long.valueOf( StringUtil.getLongValue( StringUtil.replaceString( idList,
                            "parent:", "", false, false ), -1 ) ) ).getParent();
                }

                classBeanList = new ArrayList( 2 );

                classBeanList.add( channelService
                    .retrieveSingleClassBeanInfoByClassId( paramClassId ) );

            }
            else if( idList.startsWith( "child:" ) )
            {
                // 直接孩子

                if( idList.equals( "child:root" ) )
                {
                    // 根栏目
                    paramClassId = Long.valueOf( -9999 );
                }
                else
                {
                    if( "true".equals( flagMode ) )
                    {
                        paramClassId = channelService.retrieveSingleClassBeanInfoByClassFlag(
                            StringUtil.replaceString( idList, "child:", "", false, false ) )
                            .getClassId();
                    }
                    else
                    {
                        paramClassId = Long.valueOf( StringUtil.getLongValue( StringUtil
                            .replaceString( idList, "child:", "", false, false ), -1 ) );
                    }
                }

                if( "true".equals( specMode ) )
                {
                    classBeanList = channelService
                        .retrieveConetentClassBeanSpecModeByParentClassId( paramClassId, Long
                            .valueOf( StringUtil.getLongValue( modelId, -1 ) ), specComm, siteBean
                            .getSiteFlag(), order );
                }
                else
                {
                    classBeanList = channelService.retrieveConetentClassBeanNotSpecByParentClassId(
                        paramClassId, Long.valueOf( StringUtil.getLongValue( modelId, -1 ) ),
                        siteBean.getSiteFlag(), order );
                }

            }
            else if( idList.startsWith( "all:child:" ) )
            {
                // 所有孩子
                String linear = null;

                if( idList.equals( "all:child:root" ) )
                {
                    // 根栏目
                    linear = "root";
                }
                else
                {
                    if( "true".equals( flagMode ) )
                    {
                        linear = channelService.retrieveSingleClassBeanInfoByClassFlag(
                            StringUtil.replaceString( idList, "all:child:", "", false, false ) )
                            .getLinearOrderFlag();
                    }
                    else
                    {
                        linear = channelService.retrieveSingleClassBeanInfoByClassId(
                            Long.valueOf( StringUtil.getLongValue( StringUtil.replaceString(
                                idList, "all:child:", "", false, false ), -1 ) ) )
                            .getLinearOrderFlag();

                    }
                }

                // 不支持专题模式
                if( !"true".equals( specMode ) && StringUtil.isStringNotNull( linear ) )
                {
                    classBeanList = channelService
                        .retrieveAllChildConetentClassBeanNotSpecByParentLinear( linear, siteBean
                            .getSiteFlag(), order );
                }

            }
            else if( idList.startsWith( "bro:" ) )
            {
                if( "true".equals( flagMode ) )
                {
                    paramClassId = channelService.retrieveSingleClassBeanInfoByClassFlag(
                        StringUtil.replaceString( idList, "bro:", "", false, false ) ).getClassId();
                }
                else
                {
                    paramClassId = Long.valueOf( StringUtil.getLongValue( StringUtil.replaceString(
                        idList, "bro:", "", false, false ), -1 ) );
                }

                ContentClassBean currentClassBean = channelService
                    .retrieveSingleClassBeanInfoByClassId( paramClassId );

                if( "true".equals( specMode ) )
                {
                    classBeanList = channelService
                        .retrieveConetentClassBeanSpecModeByParentClassId( currentClassBean
                            .getParent(), Long.valueOf( StringUtil.getLongValue( modelId, -1 ) ),
                            specComm, siteBean.getSiteFlag(), order );
                }
                else
                {
                    classBeanList = channelService.retrieveConetentClassBeanNotSpecByParentClassId(
                        currentClassBean.getParent(), Long.valueOf( StringUtil.getLongValue(
                            modelId, -1 ) ), siteBean.getSiteFlag(), order );
                }

            }

            result = classBeanList;

            log.debug( "JSON API 查询出的栏目列表:" + result );

            if( result == null || result.isEmpty() )
            {
                return ( JSON.toJSONString( "{empty:true}" ) );
            }

            return ( JSON.toJSONString( transformJSONData( result ) ) );

        }

    }

    public static List transformJSONData( List cbList )
    {
        if( cbList == null || cbList.isEmpty() )
        {
            return Collections.EMPTY_LIST;
        }

        ContentClassBean classBean = null;

        List jbList = new ArrayList( cbList.size() );

        for ( int i = 0; i < cbList.size(); i++ )
        {
            classBean = ( ContentClassBean ) cbList.get( i );

            jbList.add( transformJSONData( classBean ) );
        }

        return jbList;
    }

    public static ContentClassJSONBean transformJSONData( ContentClassBean classBean )
    {

        ContentClassJSONBean jsonBean = new ContentClassJSONBean();

        if( classBean == null )
        {
            return null;
        }

        jsonBean.setAddMonth( classBean.getAddMonth() );

        jsonBean.setAddYear( classBean.getAddYear() );

        jsonBean.setBanner( classBean.getBanner() );

        jsonBean.setChannelPath( classBean.getChannelPath() );

        jsonBean.setChannelUrl( classBean.getChannelUrl() );

        jsonBean.setClassDesc( classBean.getClassDesc() );

        jsonBean.setClassFlag( classBean.getClassFlag() );

        jsonBean.setOrgMode( classBean.getOrgMode() );

        jsonBean.setClassHomeProduceType( classBean.getClassHomeProduceType() );

        jsonBean.setClassHomePublishRuleId( classBean.getClassHomePublishRuleId() );

        jsonBean.setClassHomeTemplateUrl( classBean.getClassHomeTemplateUrl() );

        jsonBean.setClassId( classBean.getClassId() );

        jsonBean.setClassImageDM( classBean.getClassImageDM() );

        jsonBean.setClassImageH( classBean.getClassImageH() );

        jsonBean.setClassImageW( classBean.getClassImageW() );

        jsonBean.setClassName( classBean.getClassName() );

        jsonBean.setClassProduceType( classBean.getClassProduceType() );

        jsonBean.setClassPublishRuleId( classBean.getClassPublishRuleId() );

        jsonBean.setClassTemplateUrl( classBean.getClassTemplateUrl() );

        jsonBean.setClassType( classBean.getClassType() );

        jsonBean.setClassUrl( classBean.getClassUrl() );

        jsonBean.setCommentCaptcha( classBean.getCommentCaptcha() );

        jsonBean.setCommentHtml( classBean.getCommentHtml() );

        jsonBean.setContentImageDM( classBean.getContentImageDM() );

        jsonBean.setContentImageH( classBean.getContentImageH() );

        jsonBean.setContentImageW( classBean.getContentImageW() );

        jsonBean.setContentProduceType( classBean.getContentProduceType() );

        jsonBean.setContentPublishRuleId( classBean.getContentPublishRuleId() );

        jsonBean.setContentTemplateUrl( classBean.getContentTemplateUrl() );

        jsonBean.setContentType( classBean.getContentType() );

        jsonBean.setEditorImageDM( classBean.getEditorImageDM() );

        jsonBean.setEditorImageH( classBean.getEditorImageH() );

        jsonBean.setEditorImageMark( classBean.getEditorImageMark() );

        jsonBean.setEditorImageW( classBean.getEditorImageW() );

        jsonBean.setEndPagePos( classBean.getEndPagePos() );

        jsonBean.setEndStaticPageUrl( classBean.getEndStaticPageUrl() );

        jsonBean.setExt( classBean.getExt() );

        jsonBean.setExtDataModelId( classBean.getExtDataModelId() );

        jsonBean.setFilterCommentSensitive( classBean.getFilterCommentSensitive() );

        jsonBean.setFirstChild( classBean.getFirstChild().getClassId() );

        jsonBean.setHaveChannel( classBean.getHaveChannel() );

        jsonBean.setHaveClass( classBean.getHaveClass() );

        jsonBean.setHomeImageDM( classBean.getHomeImageDM() );

        jsonBean.setHomeImageH( classBean.getHomeImageH() );

        jsonBean.setHomeImageW( classBean.getHomeImageW() );

        jsonBean.setImmediatelyStaticAction( classBean.getImmediatelyStaticAction() );

        jsonBean.setIsLastChild( classBean.getIsLastChild() );

        jsonBean.setIsLeaf( classBean.getIsLeaf() );

        jsonBean.setIsRecommend( classBean.getIsRecommend() );

        jsonBean.setShow( classBean.isShow() );

        jsonBean.setIsSpecial( classBean.getIsSpecial() );

        jsonBean.setLayer( classBean.getLayer() );

        jsonBean.setLinearOrderFlag( classBean.getLinearOrderFlag() );

        jsonBean.setListImageDM( classBean.getListImageDM() );

        jsonBean.setListImageH( classBean.getListImageH() );

        jsonBean.setListImageW( classBean.getListImageW() );

        jsonBean.setListPageLimit( classBean.getListPageLimit() );

        jsonBean.setLogoImage( classBean.getLogoImage() );

        jsonBean.setMemberAddContent( classBean.getMemberAddContent() );

        jsonBean.setMustCommentCensor( classBean.getMustCommentCensor() );

        jsonBean.setNeedCensor( classBean.getNeedCensor() );

        jsonBean.setNotMemberComment( classBean.getNotMemberComment() );

        jsonBean.setOpenComment( classBean.getOpenComment() );

        jsonBean.setOutLink( classBean.getOutLink() );

        jsonBean.setParent( classBean.getParent() );

        jsonBean.setRelateRangeType( classBean.getRelateRangeType() );

        jsonBean.setSearchStatus( classBean.getSearchStatus() );

        jsonBean.setSensitiveMode( classBean.getSensitiveMode() );

        jsonBean.setSeoDesc( classBean.getSeoDesc() );

        jsonBean.setSeoKeyword( classBean.getSeoKeyword() );

        jsonBean.setSeoTitle( classBean.getSeoTitle() );

        jsonBean.setShowStatus( classBean.getShowStatus() );

        jsonBean.setSingleContentId( classBean.getSingleContentId() );

        jsonBean.setSiteFlag( classBean.getSiteFlag() );

        jsonBean.setStaticHomePageUrl( classBean.getStaticHomePageUrl() );

        jsonBean.setStaticPageUrl( classBean.getStaticPageUrl() );

        jsonBean.setSyncPubClass( classBean.getSyncPubClass() );

        jsonBean.setSystemHandleTime( classBean.getSystemHandleTime() );

        jsonBean.setUseStatus( classBean.getUseStatus() );

        jsonBean.setWhiteIp( classBean.getWhiteIp() );

        jsonBean.setWorkflowId( classBean.getWorkflowId() );

        return jsonBean;

    }
}
