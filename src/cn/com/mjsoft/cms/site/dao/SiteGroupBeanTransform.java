package cn.com.mjsoft.cms.site.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class SiteGroupBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        SiteGroupBean bean = new SiteGroupBean();

        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );
        bean.setSiteFlag( rs.getString( "siteFlag" ) );
        bean.setSiteRoot( rs.getString( "siteRoot" ) );
        bean.setSiteName( rs.getString( "siteName" ) );
        bean.setSiteUrl( rs.getString( "siteUrl" ) );
        
        bean.setOrderFlag( Integer.valueOf( rs.getInt( "orderFlag" ) ) );
        
        bean.setHome301Url( rs.getString( "home301Url" ) );
        
        bean.setMobSiteUrl( rs.getString( "mobSiteUrl" ) );
        bean.setPadSiteUrl( rs.getString( "padSiteUrl" ) );
        bean.setPcVm( Integer.valueOf( rs.getInt( "pcVm" ) ) );
        bean.setMobVm( Integer.valueOf( rs.getInt( "mobVm" ) ) );
        bean.setPadVm( Integer.valueOf( rs.getInt( "padVm" ) ) );
        
        bean.setSiteDesc( rs.getString( "siteDesc" ) );
        bean.setHomePageTemplate( rs.getString( "homePageTemplate" ) );
        bean.setMobHomePageTemplate( rs.getString( "mobHomePageTemplate" ) );
        bean.setPadHomePageTemplate( rs.getString( "padHomePageTemplate" ) );
        bean.setHomePageStaticUrl( rs.getString( "homePageStaticUrl" ) );
        bean.setMobHomePageStaticUrl( rs.getString( "mobHomePageStaticUrl" ) );
        bean.setPadHomePageStaticUrl( rs.getString( "padHomePageStaticUrl" ) );
        bean.setHomePageProduceType( Integer.valueOf( rs
            .getInt( "homePageProduceType" ) ) );
        bean.setFileRoot( rs.getString( "fileRoot" ) );
        bean.setImageRoot( rs.getString( "imageRoot" ) );
        bean.setMediaRoot( rs.getString( "mediaRoot" ) );
        bean.setPublishRoot( rs.getString( "publishRoot" ) );
        bean.setTemplateCharset( rs.getString( "templateCharset" ) );

        bean.setSiteId( Long.valueOf( rs.getLong( "siteId" ) ) );
        bean.setSiteFlag( rs.getString( "siteFlag" ) );
        bean.setSiteRoot( rs.getString( "siteRoot" ) );
        bean.setSiteName( rs.getString( "siteName" ) );
        bean.setSiteUrl( rs.getString( "siteUrl" ) );
        bean.setHomePageTemplate( rs.getString( "homePageTemplate" ) );
        bean.setSiteDesc( rs.getString( "siteDesc" ) );
        bean.setHomePageProduceType( Integer.valueOf( rs
            .getInt( "homePageProduceType" ) ) );
        bean.setHomePageStaticUrl( rs.getString( "homePageStaticUrl" ) );
        bean.setImageRoot( rs.getString( "imageRoot" ) );
        bean.setMediaRoot( rs.getString( "mediaRoot" ) );
        bean.setFileRoot( rs.getString( "fileRoot" ) );
        bean.setPublishRoot( rs.getString( "publishRoot" ) );
        bean.setTemplateCharset( rs.getString( "templateCharset" ) );
        bean.setLogoImage( rs.getString( "logoImage" ) );
        bean.setCopyright( rs.getString( "copyright" ) );
        bean.setIcp( rs.getString( "icp" ) );
        bean
            .setStaticFileType( Integer.valueOf( rs.getInt( "staticFileType" ) ) );
        bean.setSeoTitle( rs.getString( "seoTitle" ) );
        bean.setSeoKeyword( rs.getString( "seoKeyword" ) );
        bean.setSeoDesc( rs.getString( "seoDesc" ) );
        bean.setShareMode( Integer.valueOf( rs.getInt( "shareMode" ) ) );
        bean.setDownOutImage( Integer.valueOf( rs.getInt( "downOutImage" ) ) );
        bean.setDeleteOutLink( Integer.valueOf( rs.getInt( "deleteOutLink" ) ) );
        bean.setSummaryLength( Integer.valueOf( rs.getInt( "summaryLength" ) ) );
        bean.setSameTitle( Integer.valueOf( rs.getInt( "sameTitle" ) ) );
        bean.setGenKw( Integer.valueOf( rs.getInt( "genKw" ) ) );
        bean.setDefClickCount( Integer.valueOf( rs.getInt( "defClickCount" ) ) );
        bean.setManagerIP( rs.getString( "managerIP" ) );
        bean.setSiteCollType( Integer.valueOf( rs.getInt( "siteCollType" ) ) );
        bean.setOutSiteCollUrl( rs.getString( "outSiteCollUrl" ) );
        bean.setSendMailHost( rs.getString( "sendMailHost" ) );
        bean.setMail( rs.getString( "mail" ) );
        bean.setMailUserName( rs.getString( "mailUserName" ) );
        bean.setMailUserPW( rs.getString( "mailUserPW" ) );
        bean.setMailSSL( Integer.valueOf( rs.getInt( "mailSSL" ) ) );
        bean.setSmsApiUrl( rs.getString( "smsApiUrl" ) );
        bean.setSmsAccount( rs.getString( "smsAccount" ) );
        bean.setSmsPW( rs.getString( "smsPW" ) );
        bean
            .setSmsSendOnceSec( Integer.valueOf( rs.getInt( "smsSendOnceSec" ) ) );
        bean.setSmsMaxCount( Integer.valueOf( rs.getInt( "smsMaxCount" ) ) );
        bean.setSmsIpDayCount( Integer.valueOf( rs.getInt( "smsIpDayCount" ) ) );
        bean.setManagerLoginTime( Integer.valueOf( rs
            .getInt( "managerLoginTime" ) ) );
        bean.setSearchFun( Integer.valueOf( rs.getInt( "searchFun" ) ) );
        bean.setUseFW( Integer.valueOf( rs.getInt( "useFW" ) ) );
        bean.setImageAllowType( rs.getString( "imageAllowType" ) );
        bean.setMediaAllowType( rs.getString( "mediaAllowType" ) );
        bean.setFileAllowType( rs.getString( "fileAllowType" ) );
        bean.setImageMaxC( Integer.valueOf( rs.getInt( "imageMaxC" ) ) );
        bean.setMediaMaxC( Integer.valueOf( rs.getInt( "mediaMaxC" ) ) );
        bean.setFileMaxC( Integer.valueOf( rs.getInt( "fileMaxC" ) ) );
        bean.setUseImageMark( Integer.valueOf( rs.getInt( "useImageMark" ) ) );
        bean.setImageMarkType( Integer.valueOf( rs.getInt( "imageMarkType" ) ) );
        bean.setImageMarkDis( Integer.valueOf( rs.getInt( "imageMarkDis" ) ) );
        bean.setImageMarkPos( rs.getString( "imageMarkPos" ) );
        bean.setImageMarkChar( rs.getString( "imageMarkChar" ) );
        bean.setImageMark( rs.getString( "imageMark" ) );
        bean.setOffSetX( Integer.valueOf( rs.getInt( "offSetX" ) ) );
        bean.setOffSetY( Integer.valueOf( rs.getInt( "offSetY" ) ) );
        bean.setDefEditorImageW( Integer
            .valueOf( rs.getInt( "defEditorImageW" ) ) );
        bean.setDefEditorImageH( Integer
            .valueOf( rs.getInt( "defEditorImageH" ) ) );
        bean.setDefEditorImageDM( Integer.valueOf( rs
            .getInt( "defEditorImageDM" ) ) );
        bean.setDefHomeImageW( Integer.valueOf( rs.getInt( "defHomeImageW" ) ) );
        bean.setDefHomeImageH( Integer.valueOf( rs.getInt( "defHomeImageH" ) ) );
        bean
            .setDefHomeImageDM( Integer.valueOf( rs.getInt( "defHomeImageDM" ) ) );
        bean
            .setDefClassImageW( Integer.valueOf( rs.getInt( "defClassImageW" ) ) );
        bean
            .setDefClassImageH( Integer.valueOf( rs.getInt( "defClassImageH" ) ) );
        bean.setDefClassImageDM( Integer
            .valueOf( rs.getInt( "defClassImageDM" ) ) );
        bean.setDefListImageW( Integer.valueOf( rs.getInt( "defListImageW" ) ) );
        bean.setDefListImageH( Integer.valueOf( rs.getInt( "defListImageH" ) ) );
        bean
            .setDefListImageDM( Integer.valueOf( rs.getInt( "defListImageDM" ) ) );
        bean.setDefContentImageW( Integer.valueOf( rs
            .getInt( "defContentImageW" ) ) );
        bean.setDefContentImageH( Integer.valueOf( rs
            .getInt( "defContentImageH" ) ) );
        bean.setDefContentImageDM( Integer.valueOf( rs
            .getInt( "defContentImageDM" ) ) );
        bean.setUseState( Integer.valueOf( rs.getInt( "useState" ) ) );
        bean.setExtDataModelId( Long.valueOf( rs.getLong( "extDataModelId" ) ) );

        bean
            .setAllowMemberReg( Integer.valueOf( rs.getInt( "allowMemberReg" ) ) );

        bean.setWxAppId( rs.getString( "wxAppId" ) );
        bean.setWxAppKey( rs.getString( "wxAppKey" ) );

        bean.setQqAppId( rs.getString( "qqAppId" ) );
        bean.setQqAppKey( rs.getString( "qqAppKey" ) );
        bean.setQqBackUri( rs.getString( "qqBackUri" ) );
        bean.setWbAppId( rs.getString( "wbAppId" ) );
        bean.setWbAppKey( rs.getString( "wbAppKey" ) );
        bean.setWxPrevUid( rs.getString( "wxPrevUid" ) );
        bean.setWbBackUri( rs.getString( "wbBackUri" ) );
        bean.setRegMailText( rs.getString( "regMailText" ) );
        bean.setResetPwText( rs.getString( "resetPwText" ) );
        bean.setMailRegBackUri( rs.getString( "mailRegBackUri" ) );
        bean.setRelateMemberUri( rs.getString( "relateMemberUri" ) );
        bean.setThirdLoginErrorUri( rs.getString( "thirdLoginErrorUri" ) );
        bean.setThirdLoginSuccessUri( rs.getString( "thirdLoginSuccessUri" ) );
        bean.setResetPwBackUri( rs.getString( "resetPwBackUri" ) );
        bean
            .setMemberDefRoleId( Long.valueOf( rs.getLong( "memberDefRoleId" ) ) );
        bean.setMemberDefLv( Integer.valueOf( rs.getInt( "memberDefLv" ) ) );
        bean.setMemberDefSc( Long.valueOf( rs.getLong( "memberDefSc" ) ) );
        bean.setExtMemberModelId( Long
            .valueOf( rs.getLong( "extMemberModelId" ) ) );
        bean.setMemberExpire( Integer.valueOf( rs.getInt( "memberExpire" ) ) );
        bean.setMobJump( Integer.valueOf( rs.getInt( "mobJump" ) ) );
        bean.setMobSiteId( Long.valueOf( rs.getLong( "mobSiteId" ) ) );
        bean.setMemberLoginUri( rs.getString( "memberLoginUri" ) );
        bean.setMemberLoginOnce( Integer.valueOf( rs.getInt( "memberLoginOnce" ) ) );

        // 业务字段notHost

        // 子站是主域名+二级子目录

        if( bean.getSiteUrl().lastIndexOf( "/" + bean.getSiteRoot() + "/" ) != -1 )
        {
            bean.setNotHost( true );
        }

        return bean;
    }
}
