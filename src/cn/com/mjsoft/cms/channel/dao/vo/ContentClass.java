package cn.com.mjsoft.cms.channel.dao.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;

@Table( name = "contentclass", id = "classId", idMode = EntitySqlBridge.DB_IDENTITY )
public class ContentClass
{

    private Long classId = Long.valueOf( -1 );
    private Long linkFromClass;
    private Long linkToClass;
    private String relateClassIds;
    private String siteFlag;
    private String classFlag;
    private String className;
    private Integer classType;
    private Long parent;
    private Integer layer;
    private Integer isLeaf;
    private String classDesc;
    private Integer isRecommend = Constant.COMMON.OFF;
    private Integer isSpecial = Constant.COMMON.OFF;// 默认为栏目
    private String linearOrderFlag;
    private Integer isLastChild;
    private Long contentType = Long.valueOf( -1 );// 默认无内容模型
    private Long singleContentId;
    private String classHomeTemplateUrl;
    private String classTemplateUrl;
    private String contentTemplateUrl;
    private Integer orgMode = Constant.COMMON.OFF;

    private String mobClassHomeTemplateUrl;
    private String mobClassTemplateUrl;
    private String mobContentTemplateUrl;

    private String padClassHomeTemplateUrl;
    private String padClassTemplateUrl;
    private String padContentTemplateUrl;

    private String channelPath = "";
    private String listPageLimit = Constant.SITE_CHANNEL.LIMIT_PAGE_PUB_SIZE;
    private String endStaticPageUrl;
    private Integer endPagePos;
    private String staticHomePageUrl;
    private String staticPageUrl;
    private String logoImage;
    private String banner;
    private Integer classHomeProduceType = Constant.COMMON.ON;
    private Integer classProduceType = Constant.COMMON.ON;
    private Integer contentProduceType = Constant.COMMON.ON;
    private Integer syncPubClass = Constant.COMMON.ON;// 默认内容变化同步发布栏目
    private Integer immediatelyStaticAction = Constant.COMMON.ON;// 现默认审核通过状态且时间合法则立即发布
    private Integer needCensor;
    private Integer showStatus = Constant.COMMON.ON;// 默认显示栏目
    private Long workflowId = Long.valueOf( -1 );
    private Integer useStatus = Constant.COMMON.ON;// 默认栏目正常
    private Integer relateRangeType = Constant.COMMON.ON;// 默认开启筛选
    private Long contentPublishRuleId;
    private Long classHomePublishRuleId;
    private Long classPublishRuleId;
    private String outLink;
    private Integer openComment = Constant.COMMON.ON;
    private Integer mustCommentCensor = Constant.COMMON.OFF;// 默认不需审核
    private Integer notMemberComment = Constant.COMMON.ON;
    private Integer commentCaptcha = Constant.COMMON.ON;
    private Integer filterCommentSensitive = Constant.COMMON.ON;// 默认必须过滤敏感词
    private Integer commentHtml = Constant.COMMON.OFF;// 默认不允许HTML
    private Integer sensitiveMode = Constant.COMMON.ON;
    private String seoTitle;
    private String seoKeyword;
    private String seoDesc;
    private Integer searchStatus = Constant.COMMON.ON;
    private Integer memberAddContent;
    private Long extDataModelId;
    private Integer editorImageMark = Constant.COMMON.ON;// 默认编辑器图片有水印
    private Integer editorImageH;
    private Integer editorImageW;
    private Integer homeImageW;
    private Integer homeImageH;
    private Integer classImageW;
    private Integer classImageH;
    private Integer listImageW;
    private Integer listImageH;
    private Integer contentImageW;
    private Integer contentImageH;
    private Integer contentImageDM;
    private Integer listImageDM;
    private Integer classImageDM;
    private Integer homeImageDM;
    private Integer editorImageDM;
    private Integer addYear;
    private Integer addMonth;
    private Timestamp systemHandleTime;
    private String whiteIp;
    private String submitWhiteIp;
    private String submitDCBehavior;

    public Long getClassId()
    {
        return this.classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public String getClassName()
    {
        return this.className;
    }

    public void setClassName( String className )
    {
        this.className = className;
    }

    public String getClassFlag()
    {
        return classFlag;
    }

    public void setClassFlag( String classFlag )
    {
        this.classFlag = classFlag;
    }

    public Long getParent()
    {
        return this.parent;
    }

    public void setParent( Long parent )
    {
        this.parent = parent;
    }

    public Integer getLayer()
    {
        return this.layer;
    }

    public void setLayer( Integer layer )
    {
        this.layer = layer;
    }

    public String getClassDesc()
    {
        return this.classDesc;
    }

    public void setClassDesc( String classDesc )
    {
        this.classDesc = classDesc;
    }

    public String getLinearOrderFlag()
    {
        return this.linearOrderFlag;
    }

    public void setLinearOrderFlag( String linearOrderFlag )
    {
        this.linearOrderFlag = linearOrderFlag;
    }

    public String getClassTemplateUrl()
    {
        return this.classTemplateUrl;
    }

    public void setClassTemplateUrl( String classTemplateUrl )
    {
        this.classTemplateUrl = classTemplateUrl;
    }

    public String getContentTemplateUrl()
    {
        return this.contentTemplateUrl;
    }

    public void setContentTemplateUrl( String contentTemplateUrl )
    {
        this.contentTemplateUrl = contentTemplateUrl;
    }

    public String getChannelPath()
    {
        return this.channelPath;
    }

    public void setChannelPath( String channelPath )
    {
        this.channelPath = channelPath;
    }

    public String getStaticPageUrl()
    {
        return this.staticPageUrl;
    }

    public void setStaticPageUrl( String staticPageUrl )
    {
        this.staticPageUrl = staticPageUrl;
    }

    public Integer getClassProduceType()
    {
        return classProduceType;
    }

    public void setClassProduceType( Integer classProduceType )
    {
        this.classProduceType = classProduceType;
    }

    public Integer getContentProduceType()
    {
        return contentProduceType;
    }

    public void setContentProduceType( Integer contentProduceType )
    {
        this.contentProduceType = contentProduceType;
    }

    public Long getContentType()
    {
        return contentType;
    }

    public void setContentType( Long contentType )
    {
        this.contentType = contentType;
    }

    public Integer getImmediatelyStaticAction()
    {
        return immediatelyStaticAction;
    }

    public void setImmediatelyStaticAction( Integer immediatelyStaticAction )
    {
        this.immediatelyStaticAction = immediatelyStaticAction;
    }

    public Integer getIsLastChild()
    {
        return isLastChild;
    }

    public void setIsLastChild( Integer isLastChild )
    {
        this.isLastChild = isLastChild;
    }

    public Integer getIsLeaf()
    {
        return isLeaf;
    }

    public void setIsLeaf( Integer isLeaf )
    {
        this.isLeaf = isLeaf;
    }

    public Integer getNeedCensor()
    {
        return needCensor;
    }

    public void setNeedCensor( Integer needCensor )
    {
        this.needCensor = needCensor;
    }

    public Integer getShowStatus()
    {
        return showStatus;
    }

    public void setShowStatus( Integer showStatus )
    {
        this.showStatus = showStatus;
    }

    public Integer getUseStatus()
    {
        return useStatus;
    }

    public void setUseStatus( Integer useStatus )
    {
        this.useStatus = useStatus;
    }

    public Integer getClassType()
    {
        return classType;
    }

    public void setClassType( Integer classType )
    {
        this.classType = classType;
    }

    public String getOutLink()
    {
        return outLink;
    }

    public void setOutLink( String outLink )
    {
        this.outLink = outLink;
    }

    public Long getWorkflowId()
    {
        return workflowId;
    }

    public void setWorkflowId( Long workflowId )
    {
        this.workflowId = workflowId;
    }

    public Long getClassPublishRuleId()
    {
        return classPublishRuleId;
    }

    public void setClassPublishRuleId( Long classPublishRuleId )
    {
        this.classPublishRuleId = classPublishRuleId;
    }

    public Long getContentPublishRuleId()
    {
        return contentPublishRuleId;
    }

    public void setContentPublishRuleId( Long contentPublishRuleId )
    {
        this.contentPublishRuleId = contentPublishRuleId;
    }

    public Integer getRelateRangeType()
    {
        return relateRangeType;
    }

    public void setRelateRangeType( Integer relateRangeType )
    {
        this.relateRangeType = relateRangeType;
    }

    public Integer getEndPagePos()
    {
        return endPagePos;
    }

    public void setEndPagePos( Integer endPagePos )
    {
        this.endPagePos = endPagePos;
    }

    public String getEndStaticPageUrl()
    {
        return endStaticPageUrl;
    }

    public void setEndStaticPageUrl( String endStaticPageUrl )
    {
        this.endStaticPageUrl = endStaticPageUrl;
    }

    public Integer getMustCommentCensor()
    {
        return mustCommentCensor;
    }

    public void setMustCommentCensor( Integer mustCommentCensor )
    {
        this.mustCommentCensor = mustCommentCensor;
    }

    public Integer getNotMemberComment()
    {
        return notMemberComment;
    }

    public void setNotMemberComment( Integer notMemberComment )
    {
        this.notMemberComment = notMemberComment;
    }

    public Integer getOpenComment()
    {
        return openComment;
    }

    public void setOpenComment( Integer openComment )
    {
        this.openComment = openComment;
    }

    public String getSiteFlag()
    {
        return siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    public Integer getSensitiveMode()
    {
        return sensitiveMode;
    }

    public void setSensitiveMode( Integer sensitiveMode )
    {
        this.sensitiveMode = sensitiveMode;
    }

    public Integer getClassImageH()
    {
        return classImageH;
    }

    public void setClassImageH( Integer classImageH )
    {
        this.classImageH = classImageH;
    }

    public Integer getClassImageW()
    {
        return classImageW;
    }

    public void setClassImageW( Integer classImageW )
    {
        this.classImageW = classImageW;
    }

    public Integer getClassHomeProduceType()
    {
        return classHomeProduceType;
    }

    public void setClassHomeProduceType( Integer classHomeProduceType )
    {
        this.classHomeProduceType = classHomeProduceType;
    }

    public Long getClassHomePublishRuleId()
    {
        return classHomePublishRuleId;
    }

    public void setClassHomePublishRuleId( Long classHomePublishRuleId )
    {
        this.classHomePublishRuleId = classHomePublishRuleId;
    }

    public String getClassHomeTemplateUrl()
    {
        return classHomeTemplateUrl;
    }

    public void setClassHomeTemplateUrl( String classHomeTemplateUrl )
    {
        this.classHomeTemplateUrl = classHomeTemplateUrl;
    }

    public Integer getCommentCaptcha()
    {
        return commentCaptcha;
    }

    public void setCommentCaptcha( Integer commentCaptcha )
    {
        this.commentCaptcha = commentCaptcha;
    }

    public Integer getCommentHtml()
    {
        return commentHtml;
    }

    public void setCommentHtml( Integer commentHtml )
    {
        this.commentHtml = commentHtml;
    }

    public Integer getContentImageH()
    {
        return contentImageH;
    }

    public void setContentImageH( Integer contentImageH )
    {
        this.contentImageH = contentImageH;
    }

    public Integer getContentImageW()
    {
        return contentImageW;
    }

    public void setContentImageW( Integer contentImageW )
    {
        this.contentImageW = contentImageW;
    }

    public Integer getEditorImageH()
    {
        return editorImageH;
    }

    public void setEditorImageH( Integer editorImageH )
    {
        this.editorImageH = editorImageH;
    }

    public Integer getEditorImageW()
    {
        return editorImageW;
    }

    public void setEditorImageW( Integer editorImageW )
    {
        this.editorImageW = editorImageW;
    }

    public Long getExtDataModelId()
    {
        return extDataModelId;
    }

    public void setExtDataModelId( Long extDataModelId )
    {
        this.extDataModelId = extDataModelId;
    }

    public Integer getFilterCommentSensitive()
    {
        return filterCommentSensitive;
    }

    public void setFilterCommentSensitive( Integer filterCommentSensitive )
    {
        this.filterCommentSensitive = filterCommentSensitive;
    }

    public Integer getHomeImageH()
    {
        return homeImageH;
    }

    public void setHomeImageH( Integer homeImageH )
    {
        this.homeImageH = homeImageH;
    }

    public Integer getHomeImageW()
    {
        return homeImageW;
    }

    public void setHomeImageW( Integer homeImageW )
    {
        this.homeImageW = homeImageW;
    }

    public Integer getListImageH()
    {
        return listImageH;
    }

    public void setListImageH( Integer listImageH )
    {
        this.listImageH = listImageH;
    }

    public Integer getListImageW()
    {
        return listImageW;
    }

    public void setListImageW( Integer listImageW )
    {
        this.listImageW = listImageW;
    }

    public Integer getMemberAddContent()
    {
        return memberAddContent;
    }

    public void setMemberAddContent( Integer memberAddContent )
    {
        this.memberAddContent = memberAddContent;
    }

    public Integer getSearchStatus()
    {
        return searchStatus;
    }

    public void setSearchStatus( Integer searchStatus )
    {
        this.searchStatus = searchStatus;
    }

    public String getSeoDesc()
    {
        return seoDesc;
    }

    public void setSeoDesc( String seoDesc )
    {
        this.seoDesc = seoDesc;
    }

    public String getSeoKeyword()
    {
        return seoKeyword;
    }

    public void setSeoKeyword( String seoKeyword )
    {
        this.seoKeyword = seoKeyword;
    }

    public String getSeoTitle()
    {
        return seoTitle;
    }

    public void setSeoTitle( String seoTitle )
    {
        this.seoTitle = seoTitle;
    }

    public String getListPageLimit()
    {
        return listPageLimit;
    }

    public void setListPageLimit( String listPageLimit )
    {
        this.listPageLimit = listPageLimit;
    }

    public Integer getSyncPubClass()
    {
        return syncPubClass;
    }

    public void setSyncPubClass( Integer syncPubClass )
    {
        this.syncPubClass = syncPubClass;
    }

    public String getLogoImage()
    {
        return logoImage;
    }

    public void setLogoImage( String logoImage )
    {
        this.logoImage = logoImage;
    }

    public Integer getAddMonth()
    {
        return addMonth;
    }

    public void setAddMonth( Integer addMonth )
    {
        this.addMonth = addMonth;
    }

    public Integer getAddYear()
    {
        return addYear;
    }

    public void setAddYear( Integer addYear )
    {
        this.addYear = addYear;
    }

    public Integer getIsSpecial()
    {
        return isSpecial;
    }

    public void setIsSpecial( Integer isSpecial )
    {
        this.isSpecial = isSpecial;
    }

    public String getBanner()
    {
        return banner;
    }

    public void setBanner( String banner )
    {
        this.banner = banner;
    }

    public Integer getEditorImageMark()
    {
        return editorImageMark;
    }

    public void setEditorImageMark( Integer editorImageMark )
    {
        this.editorImageMark = editorImageMark;
    }

    public Timestamp getSystemHandleTime()
    {
        return systemHandleTime;
    }

    public void setSystemHandleTime( Timestamp systemHandleTime )
    {
        this.systemHandleTime = systemHandleTime;
    }

    public void decodeForSpecialSubject()
    {
        try
        {
            this.className = SystemSafeCharUtil.decodeFromWeb( this.className );
            this.classHomeTemplateUrl = URLDecoder.decode( this.classHomeTemplateUrl, "UTF-8" );
            this.seoTitle = URLDecoder.decode( this.seoTitle, "UTF-8" );
            this.seoDesc = URLDecoder.decode( this.seoDesc, "UTF-8" );
            this.seoKeyword = URLDecoder.decode( this.seoKeyword, "UTF-8" );
        }
        catch ( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }
    }

    public Integer getClassImageDM()
    {
        return classImageDM;
    }

    public void setClassImageDM( Integer classImageDM )
    {
        this.classImageDM = classImageDM;
    }

    public Integer getContentImageDM()
    {
        return contentImageDM;
    }

    public void setContentImageDM( Integer contentImageDM )
    {
        this.contentImageDM = contentImageDM;
    }

    public Integer getEditorImageDM()
    {
        return editorImageDM;
    }

    public void setEditorImageDM( Integer editorImageDM )
    {
        this.editorImageDM = editorImageDM;
    }

    public Integer getHomeImageDM()
    {
        return homeImageDM;
    }

    public void setHomeImageDM( Integer homeImageDM )
    {
        this.homeImageDM = homeImageDM;
    }

    public Integer getListImageDM()
    {
        return listImageDM;
    }

    public void setListImageDM( Integer listImageDM )
    {
        this.listImageDM = listImageDM;
    }

    public Long getSingleContentId()
    {
        return singleContentId;
    }

    public void setSingleContentId( Long singleContentId )
    {
        this.singleContentId = singleContentId;
    }

    public Integer getIsRecommend()
    {
        return isRecommend;
    }

    public void setIsRecommend( Integer isRecommend )
    {
        this.isRecommend = isRecommend;
    }

    public String getStaticHomePageUrl()
    {
        return staticHomePageUrl;
    }

    public void setStaticHomePageUrl( String staticHomePageUrl )
    {
        this.staticHomePageUrl = staticHomePageUrl;
    }

    public String getWhiteIp()
    {
        return whiteIp;
    }

    public void setWhiteIp( String whiteIp )
    {
        this.whiteIp = whiteIp;
    }

    public String getMobClassHomeTemplateUrl()
    {
        return mobClassHomeTemplateUrl;
    }

    public void setMobClassHomeTemplateUrl( String mobClassHomeTemplateUrl )
    {
        this.mobClassHomeTemplateUrl = mobClassHomeTemplateUrl;
    }

    public String getMobClassTemplateUrl()
    {
        return mobClassTemplateUrl;
    }

    public void setMobClassTemplateUrl( String mobClassTemplateUrl )
    {
        this.mobClassTemplateUrl = mobClassTemplateUrl;
    }

    public String getMobContentTemplateUrl()
    {
        return mobContentTemplateUrl;
    }

    public void setMobContentTemplateUrl( String mobContentTemplateUrl )
    {
        this.mobContentTemplateUrl = mobContentTemplateUrl;
    }

    public String getPadClassHomeTemplateUrl()
    {
        return padClassHomeTemplateUrl;
    }

    public void setPadClassHomeTemplateUrl( String padClassHomeTemplateUrl )
    {
        this.padClassHomeTemplateUrl = padClassHomeTemplateUrl;
    }

    public String getPadClassTemplateUrl()
    {
        return padClassTemplateUrl;
    }

    public void setPadClassTemplateUrl( String padClassTemplateUrl )
    {
        this.padClassTemplateUrl = padClassTemplateUrl;
    }

    public String getPadContentTemplateUrl()
    {
        return padContentTemplateUrl;
    }

    public void setPadContentTemplateUrl( String padContentTemplateUrl )
    {
        this.padContentTemplateUrl = padContentTemplateUrl;
    }

    public Long getLinkFromClass()
    {
        return linkFromClass;
    }

    public void setLinkFromClass( Long linkFromClass )
    {
        this.linkFromClass = linkFromClass;
    }

    
    public Long getLinkToClass()
    {
        return linkToClass;
    }

    public void setLinkToClass( Long linkToClass )
    {
        this.linkToClass = linkToClass;
    }

    public Integer getOrgMode()
    {
        return orgMode;
    }

    public void setOrgMode( Integer orgMode )
    {
        this.orgMode = orgMode;
    }

    public String getRelateClassIds()
    {
        return relateClassIds;
    }

    public void setRelateClassIds( String relateClassIds )
    {
        this.relateClassIds = relateClassIds;
    }

    public String getSubmitWhiteIp()
    {
        return submitWhiteIp;
    }

    public void setSubmitWhiteIp( String submitWhiteIp )
    {
        this.submitWhiteIp = submitWhiteIp;
    }

    public String getSubmitDCBehavior()
    {
        return submitDCBehavior;
    }

    public void setSubmitDCBehavior( String submitDCBehavior )
    {
        this.submitDCBehavior = submitDCBehavior;
    }

}
