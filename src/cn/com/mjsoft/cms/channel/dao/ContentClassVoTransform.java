package cn.com.mjsoft.cms.channel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.com.mjsoft.cms.channel.dao.vo.ContentClass;
import cn.com.mjsoft.framework.persistence.core.RowTransform;

public class ContentClassVoTransform implements RowTransform
{

    public Object convertRow( ResultSet rs, int rowNum ) throws SQLException
    {
        ContentClass vo = new ContentClass();
        vo.setLinearOrderFlag( rs.getString( "linearOrderFlag" ) );
        vo.setSiteFlag( rs.getString( "siteFlag" ) );
        vo.setClassDesc( rs.getString( "classDesc" ) );
        vo.setClassName( rs.getString( "className" ) );
        vo.setClassFlag( rs.getString( "classFlag" ) );
        vo.setChannelPath( rs.getString( "channelPath" ) );
        vo.setIsSpecial( Integer.valueOf( rs.getInt( "isSpecial" ) ) );
        vo.setClassType( Integer.valueOf( rs.getInt( "classType" ) ) );
        vo.setIsLeaf( Integer.valueOf( rs.getInt( "isLeaf" ) ) );
        vo.setLayer( Integer.valueOf( rs.getInt( "layer" ) ) );
        vo.setClassId( Long.valueOf( rs.getLong( "classId" ) ) );
        vo.setLinkFromClass( Long.valueOf( rs.getLong( "linkFromClass" ) ) );
        vo.setRelateClassIds( rs.getString( "relateClassIds" ) );
        vo.setParent( Long.valueOf( rs.getLong( "parent" ) ) );
        vo.setIsLastChild( Integer.valueOf( rs.getInt( "isLastChild" ) ) );
        vo.setClassHomeTemplateUrl( rs.getString( "classHomeTemplateUrl" ) );
        vo.setClassTemplateUrl( rs.getString( "classTemplateUrl" ) );
        vo.setMobClassHomeTemplateUrl( rs.getString( "mobClassHomeTemplateUrl" ) );
        vo.setMobClassTemplateUrl( rs.getString( "mobClassTemplateUrl" ) );
        vo.setMobContentTemplateUrl( rs.getString( "mobContentTemplateUrl" ) );
        vo.setPadClassHomeTemplateUrl( rs.getString( "padClassHomeTemplateUrl" ) );
        vo.setPadClassTemplateUrl( rs.getString( "padClassTemplateUrl" ) );
        vo.setPadContentTemplateUrl( rs.getString( "padContentTemplateUrl" ) );
        vo.setContentType( Long.valueOf( rs.getLong( "contentType" ) ) );
        vo.setContentTemplateUrl( rs.getString( "contentTemplateUrl" ) );
        vo.setLogoImage( rs.getString( "logoImage" ) );
        vo.setClassProduceType( Integer
            .valueOf( rs.getInt( "classProduceType" ) ) );
        vo.setContentProduceType( Integer.valueOf( rs
            .getInt( "ContentProduceType" ) ) );
        vo.setStaticHomePageUrl( rs.getString( "staticHomePageUrl" ) );
        vo.setStaticPageUrl( rs.getString( "staticPageUrl" ) );
        vo.setNeedCensor( Integer.valueOf( rs.getInt( "needCensor" ) ) );
        vo.setUseStatus( Integer.valueOf( rs.getInt( "useStatus" ) ) );
        vo.setImmediatelyStaticAction( Integer.valueOf( rs
            .getInt( "immediatelyStaticAction" ) ) );
        vo.setShowStatus( Integer.valueOf( rs.getInt( "showStatus" ) ) );
        vo.setOutLink( rs.getString( "outLink" ) );
        vo.setWorkflowId( Long.valueOf( rs.getLong( "workflowId" ) ) );

        vo.setContentPublishRuleId( Long.valueOf( rs
            .getLong( "contentPublishRuleId" ) ) );
        vo.setClassPublishRuleId( Long.valueOf( rs
            .getLong( "classPublishRuleId" ) ) );
        vo.setEndPagePos( Integer.valueOf( rs.getInt( "endPagePos" ) ) );
        vo.setEndStaticPageUrl( rs.getString( "endStaticPageUrl" ) );

        vo.setOpenComment( Integer.valueOf( rs.getInt( "openComment" ) ) );
        vo.setMustCommentCensor( Integer.valueOf( rs
            .getInt( "mustCommentCensor" ) ) );
        vo.setNotMemberComment( Integer
            .valueOf( rs.getInt( "notMemberComment" ) ) );
        vo.setSensitiveMode( Integer.valueOf( rs.getInt( "sensitiveMode" ) ) );
        vo.setListPageLimit( rs.getString( "listPageLimit" ) );
        vo
            .setRelateRangeType( Integer
                .valueOf( rs.getInt( "relateRangeType" ) ) );
        vo.setSyncPubClass( Integer.valueOf( rs.getInt( "syncPubClass" ) ) );
        vo.setBanner( rs.getString( "banner" ) );
        vo.setAddYear( Integer.valueOf( rs.getInt( "addYear" ) ) );
        vo.setAddMonth( Integer.valueOf( rs.getInt( "addMonth" ) ) );
        vo.setContentImageDM( Integer.valueOf( rs.getInt( "contentImageDM" ) ) );
        vo.setListImageDM( Integer.valueOf( rs.getInt( "listImageDM" ) ) );
        vo.setClassImageDM( Integer.valueOf( rs.getInt( "classImageDM" ) ) );
        vo.setHomeImageDM( Integer.valueOf( rs.getInt( "homeImageDM" ) ) );
        vo.setEditorImageDM( Integer.valueOf( rs.getInt( "editorImageDM" ) ) );
        vo.setSingleContentId( Long.valueOf( rs.getLong( "singleContentId" ) ) );
        vo.setIsRecommend( Integer.valueOf( rs.getInt( "isRecommend" ) ) );
        vo.setOrgMode( Integer.valueOf( rs.getInt( "orgMode" ) ) );


        return vo;
    }
}
