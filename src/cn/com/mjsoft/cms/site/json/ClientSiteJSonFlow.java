package cn.com.mjsoft.cms.site.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.appbiz.flow.ApiFlowDisposBaseFlow;
import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.bean.SiteGroupJSONBean;
import cn.com.mjsoft.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

@SuppressWarnings( "unchecked" )
public class ClientSiteJSonFlow extends ApiFlowDisposBaseFlow
{
    private static Logger log = Logger.getLogger( ClientSiteJSonFlow.class );

    // @Flow( mod="japi", cmd="getSite" ,name="获取站点信息API")
    public String executeBiz() throws Exception
    {

        Map params = this.getFlowContext().getHttpRequestSnapshot();

        String siteId = ( String ) params.get( "siteId" );

        if( "all".equals( siteId ) )
        {
            List result = InitSiteGroupInfoBehavior.siteGroupListCache;

            if( result.isEmpty() )
            {
                return ( JSON.toJSONString( "{empty:true}" ) );
            }

            return ( JSON.toJSONString( transformJSONData( result ) ) );
        }

        Long sid = Long.valueOf( StringUtil.getLongValue( siteId, -1 ) );

        if( sid.longValue() < 0 )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        SiteGroupBean siteGroupBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( Long.valueOf( StringUtil.getLongValue( siteId, -1 ) ) );

        if( siteGroupBean == null )
        {
            return ( JSON.toJSONString( "{empty:true}" ) );
        }

        return ( JSON.toJSONString( transformJSONData( siteGroupBean ) ) );

    }

    private List transformJSONData( List cbList )
    {
        if( cbList == null || cbList.isEmpty() )
        {
            return Collections.EMPTY_LIST;
        }

        SiteGroupBean siteBean = null;

        List jbList = new ArrayList( cbList.size() );

        for ( int i = 0; i < cbList.size(); i++ )
        {
            siteBean = ( SiteGroupBean ) cbList.get( i );

            jbList.add( transformJSONData( siteBean ) );
        }

        return jbList;
    }

    private SiteGroupJSONBean transformJSONData( SiteGroupBean siteBean )
    {

        SiteGroupJSONBean jsonBean = new SiteGroupJSONBean();

        if( siteBean == null )
        {
            return null;
        }

        jsonBean.setAllowMemberReg( siteBean.getAllowMemberReg() );
        jsonBean.setCopyright( siteBean.getCopyright() );
        jsonBean.setDefClassImageDM( siteBean.getDefClassImageDM() );
        jsonBean.setDefClassImageH( siteBean.getDefClassImageH() );
        jsonBean.setDefClassImageW( siteBean.getDefClassImageW() );
        jsonBean.setDefClickCount( siteBean.getDefClickCount() );
        jsonBean.setDefContentImageDM( siteBean.getDefContentImageDM() );
        jsonBean.setDefContentImageH( siteBean.getDefContentImageH() );
        jsonBean.setDefContentImageW( siteBean.getDefContentImageW() );
        jsonBean.setDefEditorImageDM( siteBean.getDefEditorImageDM() );
        jsonBean.setDefEditorImageH( siteBean.getDefEditorImageH() );
        jsonBean.setDefEditorImageW( siteBean.getDefEditorImageW() );
        jsonBean.setDefHomeImageDM( siteBean.getDefHomeImageDM() );
        jsonBean.setDefHomeImageH( siteBean.getDefHomeImageH() );
        jsonBean.setDefHomeImageW( siteBean.getDefHomeImageW() );
        jsonBean.setDefListImageDM( siteBean.getDefListImageDM() );
        jsonBean.setDefListImageH( siteBean.getDefListImageH() );
        jsonBean.setDefListImageW( siteBean.getDefListImageW() );
        jsonBean.setDeleteOutLink( siteBean.getDeleteOutLink() );
        jsonBean.setDownOutImage( siteBean.getDownOutImage() );
        jsonBean.setExt( siteBean.getExt() );
        jsonBean.setExtDataModelId( siteBean.getExtDataModelId() );
        jsonBean.setExtMemberModelId( siteBean.getExtMemberModelId() );
        jsonBean.setFileAllowType( siteBean.getFileAllowType() );
        jsonBean.setFileMaxC( siteBean.getFileMaxC() );
        jsonBean.setFileRoot( siteBean.getFileRoot() );

        jsonBean.setGenKw( siteBean.getGenKw() );
        jsonBean.setHomePageProduceType( siteBean.getHomePageProduceType() );
        jsonBean.setHomePageStaticUrl( siteBean.getHomePageStaticUrl() );
        jsonBean.setHomePageTemplate( siteBean.getHomePageTemplate() );
        jsonBean.setHostMainUrl( siteBean.getHostMainUrl() );
        jsonBean.setIcp( siteBean.getIcp() );
        jsonBean.setImageAllowType( siteBean.getImageAllowType() );
        jsonBean.setImageMark( siteBean.getImageMark() );
        jsonBean.setImageMarkChar( siteBean.getImageMarkChar() );
        jsonBean.setImageMarkDis( siteBean.getImageMarkDis() );
        jsonBean.setImageMarkPos( siteBean.getImageMarkPos() );
        jsonBean.setImageMarkType( siteBean.getImageMarkType() );
        jsonBean.setImageMaxC( siteBean.getImageMaxC() );
        jsonBean.setImageRoot( siteBean.getImageRoot() );

        jsonBean.setLogoImage( siteBean.getLogoImage() );
        jsonBean.setMail( siteBean.getMail() );
        jsonBean.setMailRegBackUri( siteBean.getMailRegBackUri() );
        jsonBean.setMailSSL( siteBean.getMailSSL() );
        jsonBean.setMailUserName( siteBean.getMailUserName() );
        jsonBean.setMailUserPW( siteBean.getMailUserPW() );
        jsonBean.setManagerIP( siteBean.getManagerIP() );
        jsonBean.setManagerLoginTime( siteBean.getManagerLoginTime() );
        jsonBean.setMediaAllowType( siteBean.getMediaAllowType() );
        jsonBean.setMediaMaxC( siteBean.getMediaMaxC() );
        jsonBean.setMediaRoot( siteBean.getMediaRoot() );

        jsonBean.setMemberDefLv( siteBean.getMemberDefLv() );
        jsonBean.setMemberDefRoleId( siteBean.getMemberDefRoleId() );
        jsonBean.setMemberDefSc( siteBean.getMemberDefSc() );
        jsonBean.setMemberExpire( siteBean.getMemberExpire() );
        jsonBean.setMemberLoginUri( siteBean.getMemberLoginUri() );
        jsonBean.setMobJump( siteBean.getMobJump() );
        jsonBean.setMobSiteId( siteBean.getMobSiteId() );
        jsonBean.setNotHost( siteBean.isNotHost() );
        jsonBean.setOffSetX( siteBean.getOffSetX() );
        jsonBean.setOffSetY( siteBean.getOffSetY() );
        jsonBean.setOutSiteCollUrl( siteBean.getOutSiteCollUrl() );
        jsonBean.setPublishRoot( siteBean.getPublishRoot() );
        jsonBean.setQqAppId( siteBean.getQqAppId() );
        jsonBean.setQqAppKey( siteBean.getQqAppKey() );
        jsonBean.setQqBackUri( siteBean.getQqBackUri() );
        jsonBean.setRegMailText( siteBean.getRegMailText() );
        jsonBean.setRelateMemberUri( siteBean.getRelateMemberUri() );
        jsonBean.setResetPwBackUri( siteBean.getResetPwBackUri() );
        jsonBean.setResetPwText( siteBean.getResetPwText() );
        jsonBean.setSameTitle( siteBean.getSameTitle() );
        jsonBean.setSearchFun( siteBean.getSearchFun() );
        jsonBean.setSendMailHost( siteBean.getSendMailHost() );
        jsonBean.setSeoDesc( siteBean.getSeoDesc() );
        jsonBean.setSeoKeyword( siteBean.getSeoKeyword() );
        jsonBean.setSeoTitle( siteBean.getSeoTitle() );
        jsonBean.setShareMode( siteBean.getShareMode() );
        jsonBean.setSiteCollType( siteBean.getSiteCollType() );
        jsonBean.setSiteDesc( siteBean.getSiteDesc() );
        jsonBean.setSiteFilePrefixUrl( siteBean.getSiteFilePrefixUrl() );
        jsonBean.setSiteFlag( siteBean.getSiteFlag() );
        jsonBean.setSiteId( siteBean.getSiteId() );
        jsonBean.setSiteImagePrefixUrl( siteBean.getSiteImagePrefixUrl() );
        jsonBean.setSiteIndexUri( siteBean.getSiteIndexUri() );
        jsonBean.setSiteIndexUrl( siteBean.getSiteIndexUrl() );
        jsonBean.setSiteMediaPrefixUrl( siteBean.getSiteMediaPrefixUrl() );
        jsonBean.setSiteName( siteBean.getSiteName() );
        jsonBean.setSitePublishPrefixUrl( siteBean.getSitePublishPrefixUrl() );
        jsonBean.setSiteRoot( siteBean.getSiteRoot() );
        jsonBean.setSiteTemplateUrl( siteBean.getSiteTemplateUrl() );
        jsonBean.setSiteUrl( siteBean.getSiteUrl() );
        jsonBean.setSmsAccount( siteBean.getSmsAccount() );
        jsonBean.setSmsApiUrl( siteBean.getSmsApiUrl() );
        jsonBean.setSmsIpDayCount( siteBean.getSmsIpDayCount() );
        jsonBean.setSmsMaxCount( siteBean.getSmsMaxCount() );
        jsonBean.setSmsPW( siteBean.getSmsPW() );
        jsonBean.setSmsSendOnceSec( siteBean.getSmsSendOnceSec() );
        jsonBean.setStaticFileType( siteBean.getStaticFileType() );
        jsonBean.setSummaryLength( siteBean.getSummaryLength() );
        jsonBean.setTemplateCharset( siteBean.getTemplateCharset() );
        jsonBean.setThirdLoginErrorUri( siteBean.getThirdLoginErrorUri() );
        jsonBean.setThirdLoginSuccessUri( siteBean.getThirdLoginSuccessUri() );
        jsonBean.setUseFW( siteBean.getUseFW() );
        jsonBean.setUseImageMark( siteBean.getUseImageMark() );
        jsonBean.setUseState( siteBean.getUseState() );
        jsonBean.setWbAppId( siteBean.getWbAppId() );
        jsonBean.setWbAppKey( siteBean.getWbAppKey() );
        jsonBean.setWbBackUri( siteBean.getWbBackUri() );
        jsonBean.setWxAppId( siteBean.getWxAppId() );
        jsonBean.setWxAppKey( siteBean.getWxAppKey() );
        jsonBean.setWxPrevUid( siteBean.getWxPrevUid() );

        return jsonBean;

    }

}
