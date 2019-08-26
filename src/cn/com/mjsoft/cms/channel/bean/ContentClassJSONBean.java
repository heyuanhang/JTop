package cn.com.mjsoft.cms.channel.bean;

import java.sql.Timestamp;
import java.util.Map;

/**
 * JSON接口业务bean
 * 
 * @author mjsoft
 * 
 */
public class ContentClassJSONBean
{

    // 业务字段
    private boolean isShow = false;

    // 数据字段
    private Long classId = Long.valueOf( -1 );
    private Long linkFromClass;
    private String relateClassIds;
    private String siteFlag;
    private String classFlag;
    private String className;
    private Integer classType;
    private Long parent = Long.valueOf( -1 );
    private Integer layer;
    private Integer isLeaf;
    private String classDesc;
    private String linearOrderFlag = "";
    private Integer isRecommend;
    private Integer isSpecial;
    private Integer isLastChild;

    private Long contentType;// 内容模型ID
    private Integer orgMode;
    private Long singleContentId;
    private String classHomeTemplateUrl;
    private String classTemplateUrl;
    private String contentTemplateUrl;

    private String mobClassHomeTemplateUrl;
    private String mobClassTemplateUrl;
    private String mobContentTemplateUrl;

    private String padClassHomeTemplateUrl;
    private String padClassTemplateUrl;
    private String padContentTemplateUrl;

    private String logoImage;
    private String banner;
    private String channelPath;
    private String listPageLimit;
    private String endStaticPageUrl;
    private Integer endPagePos;
    private String staticHomePageUrl;
    private String staticPageUrl;
    private Integer classHomeProduceType;
    private Integer classProduceType;
    private Integer contentProduceType;
    private Integer syncPubClass;
    private Integer immediatelyStaticAction;
    private Integer needCensor;
    private Integer showStatus;
    private Long workflowId;
    private Integer useStatus;
    private Integer relateRangeType;
    private Long contentPublishRuleId;
    private Long classHomePublishRuleId;
    private Long classPublishRuleId;
    private String outLink;
    private Integer openComment;
    private Integer mustCommentCensor;
    private Integer notMemberComment;
    private Integer commentCaptcha;
    private Integer filterCommentSensitive;
    private Integer commentHtml;
    private Integer sensitiveMode;
    private String seoTitle;
    private String seoKeyword;
    private String seoDesc;
    private Integer searchStatus;
    private Integer memberAddContent;
    private Long extDataModelId;
    private Integer editorImageMark;
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

    // 扩展业务

    private boolean haveChannel;

    private boolean haveClass;

    private String channelUrl;

    private String classUrl;

    private Long firstChild = null;

    private Map ext = null;

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

    public String getBanner()
    {
        return banner;
    }

    public void setBanner( String banner )
    {
        this.banner = banner;
    }

    public String getChannelPath()
    {
        return channelPath;
    }

    public void setChannelPath( String channelPath )
    {
        this.channelPath = channelPath;
    }

    public String getChannelUrl()
    {
        return channelUrl;
    }

    public void setChannelUrl( String channelUrl )
    {
        this.channelUrl = channelUrl;
    }

    public String getClassDesc()
    {
        return classDesc;
    }

    public void setClassDesc( String classDesc )
    {
        this.classDesc = classDesc;
    }

    public String getClassFlag()
    {
        return classFlag;
    }

    public void setClassFlag( String classFlag )
    {
        this.classFlag = classFlag;
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

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Integer getClassImageDM()
    {
        return classImageDM;
    }

    public void setClassImageDM( Integer classImageDM )
    {
        this.classImageDM = classImageDM;
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

    public String getClassName()
    {
        return className;
    }

    public void setClassName( String className )
    {
        this.className = className;
    }

    public Integer getClassProduceType()
    {
        return classProduceType;
    }

    public void setClassProduceType( Integer classProduceType )
    {
        this.classProduceType = classProduceType;
    }

    public Long getClassPublishRuleId()
    {
        return classPublishRuleId;
    }

    public void setClassPublishRuleId( Long classPublishRuleId )
    {
        this.classPublishRuleId = classPublishRuleId;
    }

    public String getClassTemplateUrl()
    {
        return classTemplateUrl;
    }

    public void setClassTemplateUrl( String classTemplateUrl )
    {
        this.classTemplateUrl = classTemplateUrl;
    }

    public Integer getClassType()
    {
        return classType;
    }

    public void setClassType( Integer classType )
    {
        this.classType = classType;
    }

    public String getClassUrl()
    {
        return classUrl;
    }

    public void setClassUrl( String classUrl )
    {
        this.classUrl = classUrl;
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

    public Integer getContentImageDM()
    {
        return contentImageDM;
    }

    public void setContentImageDM( Integer contentImageDM )
    {
        this.contentImageDM = contentImageDM;
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

    public Integer getContentProduceType()
    {
        return contentProduceType;
    }

    public void setContentProduceType( Integer contentProduceType )
    {
        this.contentProduceType = contentProduceType;
    }

    public Long getContentPublishRuleId()
    {
        return contentPublishRuleId;
    }

    public void setContentPublishRuleId( Long contentPublishRuleId )
    {
        this.contentPublishRuleId = contentPublishRuleId;
    }

    public String getContentTemplateUrl()
    {
        return contentTemplateUrl;
    }

    public void setContentTemplateUrl( String contentTemplateUrl )
    {
        this.contentTemplateUrl = contentTemplateUrl;
    }

    public Long getContentType()
    {
        return contentType;
    }

    public void setContentType( Long contentType )
    {
        this.contentType = contentType;
    }

    public Integer getEditorImageDM()
    {
        return editorImageDM;
    }

    public void setEditorImageDM( Integer editorImageDM )
    {
        this.editorImageDM = editorImageDM;
    }

    public Integer getEditorImageH()
    {
        return editorImageH;
    }

    public void setEditorImageH( Integer editorImageH )
    {
        this.editorImageH = editorImageH;
    }

    public Integer getEditorImageMark()
    {
        return editorImageMark;
    }

    public void setEditorImageMark( Integer editorImageMark )
    {
        this.editorImageMark = editorImageMark;
    }

    public Integer getEditorImageW()
    {
        return editorImageW;
    }

    public void setEditorImageW( Integer editorImageW )
    {
        this.editorImageW = editorImageW;
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

    public Map getExt()
    {
        return ext;
    }

    public void setExt( Map ext )
    {
        this.ext = ext;
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

    public Long getFirstChild()
    {
        return firstChild;
    }

    public void setFirstChild( Long firstChild )
    {
        this.firstChild = firstChild;
    }

    public boolean isHaveChannel()
    {
        return haveChannel;
    }

    public void setHaveChannel( boolean haveChannel )
    {
        this.haveChannel = haveChannel;
    }

    public boolean isHaveClass()
    {
        return haveClass;
    }

    public void setHaveClass( boolean haveClass )
    {
        this.haveClass = haveClass;
    }

    public Integer getHomeImageDM()
    {
        return homeImageDM;
    }

    public void setHomeImageDM( Integer homeImageDM )
    {
        this.homeImageDM = homeImageDM;
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

    public Integer getIsRecommend()
    {
        return isRecommend;
    }

    public void setIsRecommend( Integer isRecommend )
    {
        this.isRecommend = isRecommend;
    }

    public boolean isShow()
    {
        return isShow;
    }

    public void setShow( boolean isShow )
    {
        this.isShow = isShow;
    }

    public Integer getIsSpecial()
    {
        return isSpecial;
    }

    public void setIsSpecial( Integer isSpecial )
    {
        this.isSpecial = isSpecial;
    }

    public Integer getLayer()
    {
        return layer;
    }

    public void setLayer( Integer layer )
    {
        this.layer = layer;
    }

    public String getLinearOrderFlag()
    {
        return linearOrderFlag;
    }

    public void setLinearOrderFlag( String linearOrderFlag )
    {
        this.linearOrderFlag = linearOrderFlag;
    }

    public Integer getListImageDM()
    {
        return listImageDM;
    }

    public void setListImageDM( Integer listImageDM )
    {
        this.listImageDM = listImageDM;
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

    public String getListPageLimit()
    {
        return listPageLimit;
    }

    public void setListPageLimit( String listPageLimit )
    {
        this.listPageLimit = listPageLimit;
    }

    public String getLogoImage()
    {
        return logoImage;
    }

    public void setLogoImage( String logoImage )
    {
        this.logoImage = logoImage;
    }

    public Integer getMemberAddContent()
    {
        return memberAddContent;
    }

    public void setMemberAddContent( Integer memberAddContent )
    {
        this.memberAddContent = memberAddContent;
    }

    public Integer getMustCommentCensor()
    {
        return mustCommentCensor;
    }

    public void setMustCommentCensor( Integer mustCommentCensor )
    {
        this.mustCommentCensor = mustCommentCensor;
    }

    public Integer getNeedCensor()
    {
        return needCensor;
    }

    public void setNeedCensor( Integer needCensor )
    {
        this.needCensor = needCensor;
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

    public String getOutLink()
    {
        return outLink;
    }

    public void setOutLink( String outLink )
    {
        this.outLink = outLink;
    }

    public Long getParent()
    {
        return parent;
    }

    public void setParent( Long parent )
    {
        this.parent = parent;
    }

    public Integer getRelateRangeType()
    {
        return relateRangeType;
    }

    public void setRelateRangeType( Integer relateRangeType )
    {
        this.relateRangeType = relateRangeType;
    }

    public Integer getSearchStatus()
    {
        return searchStatus;
    }

    public void setSearchStatus( Integer searchStatus )
    {
        this.searchStatus = searchStatus;
    }

    public Integer getSensitiveMode()
    {
        return sensitiveMode;
    }

    public void setSensitiveMode( Integer sensitiveMode )
    {
        this.sensitiveMode = sensitiveMode;
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

    public Integer getShowStatus()
    {
        return showStatus;
    }

    public void setShowStatus( Integer showStatus )
    {
        this.showStatus = showStatus;
    }

    public Long getSingleContentId()
    {
        return singleContentId;
    }

    public void setSingleContentId( Long singleContentId )
    {
        this.singleContentId = singleContentId;
    }

    public String getSiteFlag()
    {
        return siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    public String getStaticHomePageUrl()
    {
        return staticHomePageUrl;
    }

    public void setStaticHomePageUrl( String staticHomePageUrl )
    {
        this.staticHomePageUrl = staticHomePageUrl;
    }

    public String getStaticPageUrl()
    {
        return staticPageUrl;
    }

    public void setStaticPageUrl( String staticPageUrl )
    {
        this.staticPageUrl = staticPageUrl;
    }

    public Integer getSyncPubClass()
    {
        return syncPubClass;
    }

    public void setSyncPubClass( Integer syncPubClass )
    {
        this.syncPubClass = syncPubClass;
    }

    public Timestamp getSystemHandleTime()
    {
        return systemHandleTime;
    }

    public void setSystemHandleTime( Timestamp systemHandleTime )
    {
        this.systemHandleTime = systemHandleTime;
    }

    public Integer getUseStatus()
    {
        return useStatus;
    }

    public void setUseStatus( Integer useStatus )
    {
        this.useStatus = useStatus;
    }

    public String getWhiteIp()
    {
        return whiteIp;
    }

    public void setWhiteIp( String whiteIp )
    {
        this.whiteIp = whiteIp;
    }

    public Long getWorkflowId()
    {
        return workflowId;
    }

    public void setWorkflowId( Long workflowId )
    {
        this.workflowId = workflowId;
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

}
