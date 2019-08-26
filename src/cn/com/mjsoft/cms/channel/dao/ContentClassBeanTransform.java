package cn.com.mjsoft.cms.channel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.framework.persistence.core.RowTransform;
import cn.com.mjsoft.framework.util.StringUtil;

public class ContentClassBeanTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        ContentClassBean bean = new ContentClassBean();
        bean.setClassId( Long.valueOf( rs.getLong( "classId" ) ) );
        bean.setLinkFromClass( Long.valueOf( rs.getLong( "linkFromClass" ) ) );
        bean.setLinkToClass( Long.valueOf( rs.getLong( "linkToClass" ) ) );
        bean.setRelateClassIds( rs.getString( "relateClassIds" ) );
        bean.setSiteFlag( rs.getString( "siteFlag" ) );
        bean.setClassFlag( rs.getString( "classFlag" ) );
        bean.setClassName( rs.getString( "className" ) );
        bean.setLogoImage( rs.getString( "logoImage" ) );
        bean.setClassType( Integer.valueOf( rs.getInt( "classType" ) ) );
        bean.setParent( Long.valueOf( rs.getLong( "parent" ) ) );
        bean.setLayer( Integer.valueOf( rs.getInt( "layer" ) ) );
        bean.setIsLeaf( Integer.valueOf( rs.getInt( "isLeaf" ) ) );
        bean.setClassDesc( rs.getString( "classDesc" ) );
        bean.setLinearOrderFlag( rs.getString( "linearOrderFlag" ) );
        bean.setIsSpecial( Integer.valueOf( rs.getInt( "isSpecial" ) ) );
        bean.setIsLastChild( Integer.valueOf( rs.getInt( "isLastChild" ) ) );
        bean.setContentType( Long.valueOf( rs.getLong( "contentType" ) ) );
        bean.setOrgMode( Integer.valueOf( rs.getInt( "orgMode" ) ) );
        
        
        bean.setMobClassHomeTemplateUrl( rs.getString( "mobClassHomeTemplateUrl" ) );
        bean.setMobClassTemplateUrl( rs.getString( "mobClassTemplateUrl" ) );
        bean.setMobContentTemplateUrl( rs.getString( "mobContentTemplateUrl" ) );
        bean.setPadClassHomeTemplateUrl( rs.getString( "padClassHomeTemplateUrl" ) );
        bean.setPadClassTemplateUrl( rs.getString( "padClassTemplateUrl" ) );
        bean.setPadContentTemplateUrl( rs.getString( "padContentTemplateUrl" ) );

        String templateUrl = rs.getString( "classHomeTemplateUrl" );

        if( StringUtil.isStringNotNull( templateUrl )
            && templateUrl.startsWith( "/" ) )
        {
            templateUrl = StringUtil.subString( templateUrl, 1, templateUrl
                .length() );
        }

        bean.setClassHomeTemplateUrl( templateUrl );

        templateUrl = rs.getString( "classTemplateUrl" );

        if( StringUtil.isStringNotNull( templateUrl )
            && templateUrl.startsWith( "/" ) )
        {
            templateUrl = StringUtil.subString( templateUrl, 1, templateUrl
                .length() );
        }

        bean.setClassTemplateUrl( templateUrl );

        templateUrl = rs.getString( "contentTemplateUrl" );

        if( StringUtil.isStringNotNull( templateUrl )
            && templateUrl.startsWith( "/" ) )
        {
            templateUrl = StringUtil.subString( templateUrl, 1, templateUrl
                .length() );
        }

        bean.setContentTemplateUrl( templateUrl );
        
 
        templateUrl = rs.getString( "mobClassHomeTemplateUrl" );

        if( StringUtil.isStringNotNull( templateUrl )
            && templateUrl.startsWith( "/" ) )
        {
            templateUrl = StringUtil.subString( templateUrl, 1, templateUrl
                .length() );
        }

        bean.setMobClassHomeTemplateUrl( templateUrl );

        templateUrl = rs.getString( "mobClassTemplateUrl" );

        if( StringUtil.isStringNotNull( templateUrl )
            && templateUrl.startsWith( "/" ) )
        {
            templateUrl = StringUtil.subString( templateUrl, 1, templateUrl
                .length() );
        }

        bean.setMobClassTemplateUrl( templateUrl );

        templateUrl = rs.getString( "mobContentTemplateUrl" );

        if( StringUtil.isStringNotNull( templateUrl )
            && templateUrl.startsWith( "/" ) )
        {
            templateUrl = StringUtil.subString( templateUrl, 1, templateUrl
                .length() );
        }

        bean.setMobContentTemplateUrl( templateUrl );
        
 
        
        templateUrl = rs.getString( "padClassHomeTemplateUrl" );

        if( StringUtil.isStringNotNull( templateUrl )
            && templateUrl.startsWith( "/" ) )
        {
            templateUrl = StringUtil.subString( templateUrl, 1, templateUrl
                .length() );
        }

        bean.setPadClassHomeTemplateUrl( templateUrl );

        templateUrl = rs.getString( "padClassTemplateUrl" );

        if( StringUtil.isStringNotNull( templateUrl )
            && templateUrl.startsWith( "/" ) )
        {
            templateUrl = StringUtil.subString( templateUrl, 1, templateUrl
                .length() );
        }

        bean.setPadClassTemplateUrl( templateUrl );

        templateUrl = rs.getString( "padContentTemplateUrl" );

        if( StringUtil.isStringNotNull( templateUrl )
            && templateUrl.startsWith( "/" ) )
        {
            templateUrl = StringUtil.subString( templateUrl, 1, templateUrl
                .length() );
        }

        bean.setPadContentTemplateUrl( templateUrl );
 

        bean.setChannelPath( rs.getString( "channelPath" ) );
        bean.setEndStaticPageUrl( rs.getString( "endStaticPageUrl" ) );
        bean.setEndPagePos( Integer.valueOf( rs.getInt( "endPagePos" ) ) );
        bean.setStaticHomePageUrl( rs.getString( "staticHomePageUrl" ) );
        bean.setStaticPageUrl( rs.getString( "staticPageUrl" ) );
        bean.setClassHomeProduceType( Integer.valueOf( rs
            .getInt( "classHomeProduceType" ) ) );
        bean.setClassProduceType( Integer.valueOf( rs
            .getInt( "classProduceType" ) ) );
        bean.setContentProduceType( Integer.valueOf( rs
            .getInt( "contentProduceType" ) ) );
        bean.setImmediatelyStaticAction( Integer.valueOf( rs
            .getInt( "immediatelyStaticAction" ) ) );
        bean.setNeedCensor( Integer.valueOf( rs.getInt( "needCensor" ) ) );
        bean.setShowStatus( Integer.valueOf( rs.getInt( "showStatus" ) ) );
        bean.setWorkflowId( Long.valueOf( rs.getLong( "workflowId" ) ) );
        bean.setUseStatus( Integer.valueOf( rs.getInt( "useStatus" ) ) );
        bean.setContentPublishRuleId( Long.valueOf( rs
            .getLong( "contentPublishRuleId" ) ) );
        bean.setClassHomePublishRuleId( Long.valueOf( rs
            .getLong( "classHomePublishRuleId" ) ) );
        bean.setClassPublishRuleId( Long.valueOf( rs
            .getLong( "classPublishRuleId" ) ) );
        bean.setOutLink( rs.getString( "outLink" ) );
        bean.setOpenComment( Integer.valueOf( rs.getInt( "openComment" ) ) );
        bean.setMustCommentCensor( Integer.valueOf( rs
            .getInt( "mustCommentCensor" ) ) );
        bean.setNotMemberComment( Integer.valueOf( rs
            .getInt( "notMemberComment" ) ) );
        bean
            .setCommentCaptcha( Integer.valueOf( rs.getInt( "commentCaptcha" ) ) );
        bean.setFilterCommentSensitive( Integer.valueOf( rs
            .getInt( "filterCommentSensitive" ) ) );
        bean.setCommentHtml( Integer.valueOf( rs.getInt( "commentHtml" ) ) );
        bean.setSensitiveMode( Integer.valueOf( rs.getInt( "sensitiveMode" ) ) );
        bean.setSeoTitle( rs.getString( "seoTitle" ) );
        bean.setSeoKeyword( rs.getString( "seoKeyword" ) );
        bean.setSeoDesc( rs.getString( "seoDesc" ) );
        bean.setSearchStatus( Integer.valueOf( rs.getInt( "searchStatus" ) ) );
        bean.setMemberAddContent( Integer.valueOf( rs
            .getInt( "memberAddContent" ) ) );
        bean.setExtDataModelId( Long.valueOf( rs.getLong( "extDataModelId" ) ) );
        bean.setEditorImageMark( Integer
            .valueOf( rs.getInt( "editorImageMark" ) ) );
        bean.setEditorImageH( Integer.valueOf( rs.getInt( "editorImageH" ) ) );
        bean.setEditorImageW( Integer.valueOf( rs.getInt( "editorImageW" ) ) );
        bean.setHomeImageW( Integer.valueOf( rs.getInt( "homeImageW" ) ) );
        bean.setHomeImageH( Integer.valueOf( rs.getInt( "homeImageH" ) ) );
        bean.setClassImageW( Integer.valueOf( rs.getInt( "classImageW" ) ) );
        bean.setClassImageH( Integer.valueOf( rs.getInt( "classImageH" ) ) );
        bean.setListImageW( Integer.valueOf( rs.getInt( "listImageW" ) ) );
        bean.setListImageH( Integer.valueOf( rs.getInt( "listImageH" ) ) );
        bean.setContentImageW( Integer.valueOf( rs.getInt( "contentImageW" ) ) );
        bean.setContentImageH( Integer.valueOf( rs.getInt( "contentImageH" ) ) );
        bean
            .setContentImageDM( Integer.valueOf( rs.getInt( "contentImageDM" ) ) );
        bean.setListImageDM( Integer.valueOf( rs.getInt( "listImageDM" ) ) );
        bean.setClassImageDM( Integer.valueOf( rs.getInt( "classImageDM" ) ) );
        bean.setHomeImageDM( Integer.valueOf( rs.getInt( "homeImageDM" ) ) );
        bean.setEditorImageDM( Integer.valueOf( rs.getInt( "editorImageDM" ) ) );
        bean.setListPageLimit( rs.getString( "listPageLimit" ) );
        bean.setRelateRangeType( Integer
            .valueOf( rs.getInt( "relateRangeType" ) ) );
        bean.setSyncPubClass( Integer.valueOf( rs.getInt( "syncPubClass" ) ) );
        bean.setBanner( rs.getString( "banner" ) );
        bean.setAddYear( Integer.valueOf( rs.getInt( "addYear" ) ) );
        bean.setAddMonth( Integer.valueOf( rs.getInt( "addMonth" ) ) );
        bean
            .setSingleContentId( Long.valueOf( rs.getLong( "singleContentId" ) ) );
        bean.setIsRecommend( Integer.valueOf( rs.getInt( "isRecommend" ) ) );
        bean.setSystemHandleTime( rs.getTimestamp( "systemHandleTime" ) );
        bean.setWhiteIp( rs.getString( "whiteIp" ) );
        bean.setSubmitWhiteIp( rs.getString( "submitWhiteIp" ) );
        bean.setSubmitDCBehavior( rs.getString( "submitDCBehavior" ) );
        
        return bean;
    }
}
