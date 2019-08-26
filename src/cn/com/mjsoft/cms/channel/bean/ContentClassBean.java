package cn.com.mjsoft.cms.channel.bean;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.publish.bean.PublishRuleBean;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.cache.jsr14.NoToStringMap;
import cn.com.mjsoft.framework.util.StringUtil;

/**
 * 业务bean
 * 
 * @author mjsoft
 * 
 */
public class ContentClassBean
{
    private static Logger log = Logger.getLogger( ContentClassBean.class );

    private static final String D_CLASS_TYPE_STRING = "<font color='blue'>栏目动态</font>";

    private static final String S_CLASS_TYPE_STRING = "<font color='red'>栏目静态</font>";

    private static final String D_CONTENT_TYPE_STRING = "<font color='blue'>内容动态</font>";

    private static final String S_CONTENT_TYPE_STRING = "<font color='red'>内容静态</font>";

    private static PublishService publishService = PublishService.getInstance();

    private static ContentService contentService = ContentService.getInstance();

    private static ChannelService channelService = ChannelService.getInstance();

    // 业务字段
    private boolean isShow = false;

    // 数据字段
    private Long classId = Long.valueOf( -1 );
    private String siteFlag;
    private String classFlag;
    private String className;
    private Integer classType;
    private Long linkFromClass;
    private Long linkToClass;
    private String relateClassIds;
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
    private String submitWhiteIp;
    
    private String submitDCBehavior;

    private ContentClassBean firstChild = null;

    private Map ext = null;

    @SuppressWarnings( "unchecked" )
    public Map getExt()
    {
        if( ext == null )
        {
            ext = new NoToStringMap();

            ext.putAll( contentService.retrieveSingleUserDefineContentOnlyModelData(
                this.extDataModelId, this.classId, this.siteFlag ) );
        }

        return ext;
    }

    public void setExt( Map ext )
    {
        this.ext = ext;
    }

    public boolean isShow()
    {
        return isShow;
    }

    public void setShow( boolean isShow )
    {
        this.isShow = isShow;
    }

    public String getSiteFlag()
    {
        return siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

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

    public String getRelateClassIds()
    {
        return relateClassIds;
    }

    public void setRelateClassIds( String relateClassIds )
    {
        this.relateClassIds = relateClassIds;
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
        if( StringUtil.isStringNull( this.outLink ) )
        {
            return "http://";
        }

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

    public Integer getRelateRangeType()
    {
        return relateRangeType;
    }

    public void setRelateRangeType( Integer relateRangeType )
    {
        this.relateRangeType = relateRangeType;
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

    public Integer getEditorImageMark()
    {
        return editorImageMark;
    }

    public void setEditorImageMark( Integer editorImageMark )
    {
        this.editorImageMark = editorImageMark;
    }

    public String getStaticHomePageUrl()
    {
        return staticHomePageUrl;
    }

    public void setStaticHomePageUrl( String staticHomePageUrl )
    {
        this.staticHomePageUrl = staticHomePageUrl;
    }

    public String getBanner()
    {
        if( StringUtil.isStringNotNull( this.banner ) )
        {
            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                .getEntry( this.siteFlag );

            String reUrl = StringUtil.subString( this.banner, this.banner.indexOf( "reUrl=" ) + 6,
                this.banner.indexOf( ";", this.banner.indexOf( "reUrl=" ) + 6 ) );

            return site.getSiteImagePrefixUrl() + reUrl;
        }

        return "no_url";
    }

    public void setBanner( String banner )
    {
        this.banner = banner;
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

    public String getLogoImage()
    {
        if( StringUtil.isStringNotNull( this.logoImage ) )
        {
            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                .getEntry( this.siteFlag );

            String reUrl = StringUtil.subString( this.logoImage,
                this.logoImage.indexOf( "reUrl=" ) + 6, this.logoImage.indexOf( ";", this.logoImage
                    .indexOf( "reUrl=" ) + 6 ) );

            return site.getSiteImagePrefixUrl() + reUrl;
        }

        return "no_url";
    }

    public String getLogoImageReUrl()
    {
        if( StringUtil.isStringNotNull( this.logoImage ) )
        {

            String reUrl = new String( this.logoImage.substring(
                this.logoImage.indexOf( "reUrl=" ) + 6, this.logoImage.indexOf( ";", this.logoImage
                    .indexOf( "reUrl=" ) + 6 ) ) );

            return reUrl;
        }

        return "no_url";
    }

    public void setLogoImage( String logoImage )
    {
        this.logoImage = logoImage;
    }

    public Timestamp getSystemHandleTime()
    {
        return systemHandleTime;
    }

    public void setSystemHandleTime( Timestamp systemHandleTime )
    {
        this.systemHandleTime = systemHandleTime;
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
    
    public String getSubmitWhiteIp()
    {
        return submitWhiteIp;
    }

    public void setSubmitWhiteIp( String submitWhiteIp )
    {
        this.submitWhiteIp = submitWhiteIp;
    }

    /**
     * ********************** 扩展数据 **********************
     */
    public String getClassTreePath()
    {
        if( this.linkFromClass == null || this.linkFromClass < 1 )
        {
            return "";
        }

        ContentClassBean classBean = channelService
            .retrieveSingleClassBeanInfoByClassId( this.linkFromClass );
        
        if(classBean == null || classBean.getClassId() < 0 )
        {
            return "";
        }

        String path = classBean.getChannelPath();

        String[] ids = StringUtil.split( path, "," );

        StringBuilder buf = new StringBuilder();

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( classBean.getSiteFlag() );

        buf.append( site.getSiteName() + " > " );

        String flag = "";
        for ( String cid : ids )
        {

            buf.append( flag );
            classBean = channelService.retrieveSingleClassBeanInfoByClassId( StringUtil
                .getLongValue( cid, -1 ) );

            buf.append( classBean.getClassName() );
            flag = " > ";

        }

        return buf.toString();
    }
    
    public String getToClassTreePath()
    {
        if( this.linkToClass == null || this.linkToClass < 1 )
        {
            return "";
        }

        ContentClassBean classBean = channelService
            .retrieveSingleClassBeanInfoByClassId( this.linkToClass );
        
        if(classBean == null || classBean.getClassId() < 0 )
        {
            return "";
        }

        String path = classBean.getChannelPath();

        String[] ids = StringUtil.split( path, "," );

        StringBuilder buf = new StringBuilder();

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( classBean.getSiteFlag() );

        buf.append( site.getSiteName() + " > " );

        String flag = "";
        for ( String cid : ids )
        {

            buf.append( flag );
            classBean = channelService.retrieveSingleClassBeanInfoByClassId( StringUtil
                .getLongValue( cid, -1 ) );

            buf.append( classBean.getClassName() );
            flag = " > ";

        }

        return buf.toString();
    }

    public boolean getHaveChannel()
    {
        if( StringUtil.isStringNotNull( classHomeTemplateUrl ) )
        {
            return true;
        }

        return false;
    }

    public boolean getHaveClass()
    {
        if( StringUtil.isStringNotNull( classTemplateUrl ) )
        {
            return true;
        }

        return false;
    }

    public boolean gethaveChannel()
    {
        if( StringUtil.isStringNotNull( classHomeTemplateUrl ) )
        {
            return true;
        }

        return false;
    }
    
    

    public String getSubmitDCBehavior()
    {
        return submitDCBehavior;
    }

    public void setSubmitDCBehavior( String submitDCBehavior )
    {
        this.submitDCBehavior = submitDCBehavior;
    }

    public String getChannelUrl()
    {

        // SystemRuntimeConfig config = SystemConfiguration.getInstance()
        // .getSystemConfig();
        // String base = config.getSystemBasePath();

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( this.siteFlag );

        // 外部链接
        if( Constant.SITE_CHANNEL.CLASS_TYPE_OUT_LINK.equals( this.classType ) )
        {
            return this.outLink;
        }
        // 单页类型
        else if( Constant.SITE_CHANNEL.CLASS_TYPE_SINGLE_PAGE.equals( this.classType ) )
        {
            Map mainInfo = contentService.retrieveSingleUserDefineContent( this.singleContentId,
                Integer.valueOf( 1 ) );

            // HTML
            if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.classProduceType ) )
            {
                String staticUrl = ( String ) mainInfo.get( "staticPageUrl" );

                if( StringUtil.isStringNotNull( staticUrl ) )
                {
                    return site.getSiteUrl()
                    // + site.getPublishRoot()
                        + staticUrl;
                }
                else
                {
                    // 目前处理为,不存在,输出当前规则URL
                    // // 若内容静态URL不存在,则输出动态URL
                    // return disposeUrlLink( mainInfo, site, this );

                    // 获取内容发布规则
                    PublishRuleBean ruleBean = publishService
                        .retrieveSinglePublishRuleBean( contentPublishRuleId );

                    if( ruleBean == null )
                    {
                        return "NO_RULE";
                    }

                    String[] pathInfo = ruleBean.getFullContentClassPagePublishPath( site, this,
                        null, null, Integer.valueOf( 1 ) );

                    return site.getSiteUrl() + pathInfo[1];

                }

            }//
            else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( this.classProduceType ) )
            {
                return disposeUrlLink( mainInfo, site, this );
            }

        }
        // HTML
        else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.classHomeProduceType ) )
        {
            if( site.isNotHost() )
            {
                return site.getSiteUrl() + this.staticHomePageUrl;
            }
            else
            {

                if( StringUtil.isStringNotNull( this.staticHomePageUrl ) )
                {
                    return site.getSiteUrl()
                    // + site.getSiteRoot()+ Constant.CONTENT.URL_SEP
                        // + site.getPublishRoot()
                        + this.staticHomePageUrl;
                }
                else
                {
                    // 获取栏目发布规则
                    PublishRuleBean ruleBean = publishService
                        .retrieveSinglePublishRuleBean( classHomePublishRuleId );

                    if( ruleBean == null )
                    {
                        return "NO_RULE";
                    }

                    String[] pathInfo = ruleBean.getFullContentClassPagePublishPath( site, this,
                        null, null, Integer.valueOf( 1 ) );

                    return site.getSiteUrl() + pathInfo[1];

                    // return site.getSiteUrl()
                    // // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                    // // + Constant.CONTENT.URL_SEP
                    // + StringUtil
                    // .replaceString( this.classTemplateUrl,
                    // "{class-id}", this.classId.toString(), false,
                    // false );
                }

            }

            // 获取栏目发布规则
            // PublishRuleBean ruleBean = publishService
            // .retrieveSinglePublishRuleBean( this.classPublishRuleId );
            //
            // String[] pathInfo = ruleBean.getFullContentClassPagePublishPath(
            // site, this, null, null, Integer.valueOf( 1 ) );
            //
            // return site.getSiteUrl() +pathInfo[1];
        }
        // 动态
        else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( this.classHomeProduceType ) )
        {

            // if( site.isNotHost() )
            {
                return site.getSiteUrl()
                // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                    // + Constant.CONTENT.URL_SEP
                    + StringUtil.replaceString( this.classHomeTemplateUrl, "{class-id}",
                        this.classId.toString(), false, false );
            }
            // else
            // {
            // return site.getSiteUrl()
            // + site.getSiteRoot()
            // + Constant.CONTENT.URL_SEP
            // + Constant.CONTENT.TEMPLATE_BASE
            // + Constant.CONTENT.URL_SEP
            // + StringUtil.replaceString( this.classTemplateUrl,
            // "{class-id}", this.classId.toString(), false, false );
            // }

        }

        return "";
    }

    public String getMobChannelUrl()
    {

        // SystemRuntimeConfig config = SystemConfiguration.getInstance()
        // .getSystemConfig();
        // String base = config.getSystemBasePath();

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( this.siteFlag );

        // 外部链接
        if( Constant.SITE_CHANNEL.CLASS_TYPE_OUT_LINK.equals( this.classType ) )
        {
            return this.outLink;
        }
        // 单页类型
        else if( Constant.SITE_CHANNEL.CLASS_TYPE_SINGLE_PAGE.equals( this.classType ) )
        {
            Map mainInfo = contentService.retrieveSingleUserDefineContent( this.singleContentId,
                Integer.valueOf( 1 ) );

            // HTML
            if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.classProduceType ) )
            {
                String staticUrl = ( String ) mainInfo.get( "staticPageUrl" );

                if( StringUtil.isStringNotNull( staticUrl ) )
                {
                    return site.getSiteUrl()
                    // + site.getPublishRoot()
                        + staticUrl;
                }
                else
                {
                    // 目前处理为,不存在,输出当前规则URL
                    // // 若内容静态URL不存在,则输出动态URL
                    // return disposeUrlLink( mainInfo, site, this );

                    // 获取内容发布规则
                    PublishRuleBean ruleBean = publishService
                        .retrieveSinglePublishRuleBean( contentPublishRuleId );

                    if( ruleBean == null )
                    {
                        return "NO_RULE";
                    }

                    String[] pathInfo = ruleBean.getFullContentClassPagePublishPath( site, this,
                        null, null, Integer.valueOf( 1 ) );

                    return site.getSiteUrl() + pathInfo[1];

                }

            }//
            else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( this.classProduceType ) )
            {
                return disposeUrlLink( mainInfo, site, this );
            }

        }
        // HTML
        else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.classHomeProduceType ) )
        {
            if( site.isNotHost() )
            {
                return site.getSiteUrl() + this.staticHomePageUrl;
            }
            else
            {

                if( StringUtil.isStringNotNull( this.staticHomePageUrl ) )
                {
                    return site.getSiteUrl()
                    // + site.getSiteRoot()+ Constant.CONTENT.URL_SEP
                        // + site.getPublishRoot()
                        + this.staticHomePageUrl;
                }
                else
                {
                    // 获取栏目发布规则
                    PublishRuleBean ruleBean = publishService
                        .retrieveSinglePublishRuleBean( classHomePublishRuleId );

                    if( ruleBean == null )
                    {
                        return "NO_RULE";
                    }

                    String[] pathInfo = ruleBean.getFullContentClassPagePublishPath( site, this,
                        null, null, Integer.valueOf( 1 ) );

                    return site.getSiteUrl() + pathInfo[1];

                    // return site.getSiteUrl()
                    // // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                    // // + Constant.CONTENT.URL_SEP
                    // + StringUtil
                    // .replaceString( this.classTemplateUrl,
                    // "{class-id}", this.classId.toString(), false,
                    // false );
                }

            }

            // 获取栏目发布规则
            // PublishRuleBean ruleBean = publishService
            // .retrieveSinglePublishRuleBean( this.classPublishRuleId );
            //
            // String[] pathInfo = ruleBean.getFullContentClassPagePublishPath(
            // site, this, null, null, Integer.valueOf( 1 ) );
            //
            // return site.getSiteUrl() +pathInfo[1];
        }
        // 动态
        else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( this.classHomeProduceType ) )
        {

            // if( site.isNotHost() )
            {
                String main = site.getMobSiteUrl();

                if( main.endsWith( "/mob/" ) )
                {
                    main = StringUtil.subString( main, 0, main.length() - 4 );
                }

                return main
                // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                    // + Constant.CONTENT.URL_SEP
                    + StringUtil.replaceString( this.mobClassHomeTemplateUrl, "{class-id}",
                        this.classId.toString(), false, false );
            }
            // else
            // {
            // return site.getSiteUrl()
            // + site.getSiteRoot()
            // + Constant.CONTENT.URL_SEP
            // + Constant.CONTENT.TEMPLATE_BASE
            // + Constant.CONTENT.URL_SEP
            // + StringUtil.replaceString( this.classTemplateUrl,
            // "{class-id}", this.classId.toString(), false, false );
            // }

        }

        return "";
    }

    public String getClassUrl()
    {
        // SystemRuntimeConfig config = SystemConfiguration.getInstance()
        // .getSystemConfig();
        // String base = config.getSystemBasePath();

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( this.siteFlag );

        // 外部链接
        if( Constant.SITE_CHANNEL.CLASS_TYPE_OUT_LINK.equals( this.classType ) )
        {
            return this.outLink;
        }
        // 单页类型
        else if( Constant.SITE_CHANNEL.CLASS_TYPE_SINGLE_PAGE.equals( this.classType ) )
        {
            Map mainInfo = contentService.retrieveSingleUserDefineContent( this.singleContentId,
                Integer.valueOf( 1 ) );

            // HTML
            if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.classProduceType ) )
            {
                String staticUrl = ( String ) mainInfo.get( "staticPageUrl" );

                if( StringUtil.isStringNotNull( staticUrl ) )
                {
                    return site.getSiteUrl()
                    // + site.getPublishRoot()
                        + staticUrl;
                }
                else
                {
                    // 目前处理为,不存在,输出当前规则URL
                    // // 若内容静态URL不存在,则输出动态URL
                    // return disposeUrlLink( mainInfo, site, this );

                    // 获取内容发布规则
                    PublishRuleBean ruleBean = publishService
                        .retrieveSinglePublishRuleBean( contentPublishRuleId );

                    if( ruleBean == null )
                    {
                        return "NO_RULE";
                    }

                    String[] pathInfo = ruleBean.getFullContentClassPagePublishPath( site, this,
                        null, null, Integer.valueOf( 1 ) );

                    return site.getSiteUrl() + pathInfo[1];

                }

            }//
            else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( this.classProduceType ) )
            {
                return disposeUrlLink( mainInfo, site, this );
            }

        }
        // HTML
        else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.classProduceType ) )
        {
            if( site.isNotHost() )
            {
                return site.getSiteUrl() + this.staticPageUrl;
            }
            else
            {

                if( StringUtil.isStringNotNull( this.staticPageUrl ) )
                {
                    return site.getSiteUrl()
                    // + site.getSiteRoot()+ Constant.CONTENT.URL_SEP
                        // + site.getPublishRoot()
                        + this.staticPageUrl;
                }
                else
                {
                    // 获取栏目发布规则
                    PublishRuleBean ruleBean = publishService
                        .retrieveSinglePublishRuleBean( classPublishRuleId );

                    if( ruleBean == null )
                    {
                        return "NO_RULE";
                    }

                    String[] pathInfo = ruleBean.getFullContentClassPagePublishPath( site, this,
                        null, null, Integer.valueOf( 1 ) );

                    return site.getSiteUrl() + pathInfo[1];

                    // return site.getSiteUrl()
                    // // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                    // // + Constant.CONTENT.URL_SEP
                    // + StringUtil
                    // .replaceString( this.classTemplateUrl,
                    // "{class-id}", this.classId.toString(), false,
                    // false );
                }

            }

            // 获取栏目发布规则
            // PublishRuleBean ruleBean = publishService
            // .retrieveSinglePublishRuleBean( this.classPublishRuleId );
            //
            // String[] pathInfo = ruleBean.getFullContentClassPagePublishPath(
            // site, this, null, null, Integer.valueOf( 1 ) );
            //
            // return site.getSiteUrl() +pathInfo[1];
        }
        // 动态
        else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( this.classProduceType ) )
        {

            // if( site.isNotHost() )
            {
                return site.getSiteUrl()
                // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                    // + Constant.CONTENT.URL_SEP
                    + StringUtil.replaceString( this.classTemplateUrl, "{class-id}", this.classId
                        .toString(), false, false );
            }
            // else
            // {
            // return site.getSiteUrl()
            // + site.getSiteRoot()
            // + Constant.CONTENT.URL_SEP
            // + Constant.CONTENT.TEMPLATE_BASE
            // + Constant.CONTENT.URL_SEP
            // + StringUtil.replaceString( this.classTemplateUrl,
            // "{class-id}", this.classId.toString(), false, false );
            // }

        }

        return "";

    }

    public String getMobClassUrl()
    {
        // SystemRuntimeConfig config = SystemConfiguration.getInstance()
        // .getSystemConfig();
        // String base = config.getSystemBasePath();

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( this.siteFlag );

        // 外部链接
        if( Constant.SITE_CHANNEL.CLASS_TYPE_OUT_LINK.equals( this.classType ) )
        {
            return this.outLink;
        }
        // 单页类型
        else if( Constant.SITE_CHANNEL.CLASS_TYPE_SINGLE_PAGE.equals( this.classType ) )
        {
            Map mainInfo = contentService.retrieveSingleUserDefineContent( this.singleContentId,
                Integer.valueOf( 1 ) );

            // HTML
            if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.classProduceType ) )
            {
                String staticUrl = ( String ) mainInfo.get( "mobStaticPageUrl" );

                if( StringUtil.isStringNotNull( staticUrl ) )
                {
                    return site.getSiteUrl()
                    // + site.getPublishRoot()
                        + staticUrl;
                }
                else
                {
                    // 目前处理为,不存在,输出当前规则URL
                    // // 若内容静态URL不存在,则输出动态URL
                    // return disposeUrlLink( mainInfo, site, this );

                    // 获取内容发布规则
                    PublishRuleBean ruleBean = publishService
                        .retrieveSinglePublishRuleBean( contentPublishRuleId );

                    if( ruleBean == null )
                    {
                        return "NO_RULE";
                    }

                    String[] pathInfo = ruleBean.getFullContentClassPagePublishPath( site, this,
                        null, null, Integer.valueOf( 1 ) );

                    return site.getSiteUrl() + pathInfo[1];

                }

            }//
            else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( this.classProduceType ) )
            {
                return disposeUrlLink( mainInfo, site, this );
            }

        }
        // HTML
        else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.classProduceType ) )
        {
            // 获取栏目发布规则
            PublishRuleBean ruleBean = publishService
                .retrieveSinglePublishRuleBean( classPublishRuleId );

            if( ruleBean == null )
            {
                return "NO_RULE";
            }

            String[] pathInfo = ruleBean.getFullContentClassPagePublishPath( site, this, null,
                null, Integer.valueOf( 1 ) );

            String main = site.getMobSiteUrl();

            if( main.endsWith( "/mob/" ) )
            {
                main = StringUtil.subString( main, 0, main.length() - 4 );
            }

            return main + pathInfo[3];
        }
        // 动态
        else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( this.classProduceType ) )
        {

            // if( site.isNotHost() )
            {
                String main = site.getMobSiteUrl();

                if( main.endsWith( "/mob/" ) )
                {
                    main = StringUtil.subString( main, 0, main.length() - 4 );
                }

                return main
                // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                    // + Constant.CONTENT.URL_SEP
                    + StringUtil.replaceString( this.mobClassTemplateUrl, "{class-id}",
                        this.classId.toString(), false, false );
            }
            // else
            // {
            // return site.getSiteUrl()
            // + site.getSiteRoot()
            // + Constant.CONTENT.URL_SEP
            // + Constant.CONTENT.TEMPLATE_BASE
            // + Constant.CONTENT.URL_SEP
            // + StringUtil.replaceString( this.classTemplateUrl,
            // "{class-id}", this.classId.toString(), false, false );
            // }

        }

        return "";

    }

    public String getPadClassUrl()
    {
        // SystemRuntimeConfig config = SystemConfiguration.getInstance()
        // .getSystemConfig();
        // String base = config.getSystemBasePath();

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( this.siteFlag );

        // 外部链接
        if( Constant.SITE_CHANNEL.CLASS_TYPE_OUT_LINK.equals( this.classType ) )
        {
            return this.outLink;
        }
        // 单页类型
        else if( Constant.SITE_CHANNEL.CLASS_TYPE_SINGLE_PAGE.equals( this.classType ) )
        {
            Map mainInfo = contentService.retrieveSingleUserDefineContent( this.singleContentId,
                Integer.valueOf( 1 ) );

            // HTML
            if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.classProduceType ) )
            {
                String staticUrl = ( String ) mainInfo.get( "mobStaticPageUrl" );

                if( StringUtil.isStringNotNull( staticUrl ) )
                {
                    return site.getSiteUrl()
                    // + site.getPublishRoot()
                        + staticUrl;
                }
                else
                {
                    // 目前处理为,不存在,输出当前规则URL
                    // // 若内容静态URL不存在,则输出动态URL
                    // return disposeUrlLink( mainInfo, site, this );

                    // 获取内容发布规则
                    PublishRuleBean ruleBean = publishService
                        .retrieveSinglePublishRuleBean( contentPublishRuleId );

                    if( ruleBean == null )
                    {
                        return "NO_RULE";
                    }

                    String[] pathInfo = ruleBean.getFullContentClassPagePublishPath( site, this,
                        null, null, Integer.valueOf( 1 ) );

                    return site.getSiteUrl() + pathInfo[1];

                }

            }//
            else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( this.classProduceType ) )
            {
                return disposeUrlLink( mainInfo, site, this );
            }

        }
        // HTML
        else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.classProduceType ) )
        {
            if( site.isNotHost() )
            {
                return site.getSiteUrl() + this.staticPageUrl;
            }
            else
            {

                if( StringUtil.isStringNotNull( this.staticPageUrl ) )
                {
                    return site.getSiteUrl()
                    // + site.getSiteRoot()+ Constant.CONTENT.URL_SEP
                        // + site.getPublishRoot()
                        + this.staticPageUrl;
                }
                else
                {
                    // 获取栏目发布规则
                    PublishRuleBean ruleBean = publishService
                        .retrieveSinglePublishRuleBean( classPublishRuleId );

                    if( ruleBean == null )
                    {
                        return "NO_RULE";
                    }

                    String[] pathInfo = ruleBean.getFullContentClassPagePublishPath( site, this,
                        null, null, Integer.valueOf( 1 ) );

                    return site.getSiteUrl() + pathInfo[1];

                    // return site.getSiteUrl()
                    // // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                    // // + Constant.CONTENT.URL_SEP
                    // + StringUtil
                    // .replaceString( this.classTemplateUrl,
                    // "{class-id}", this.classId.toString(), false,
                    // false );
                }

            }

            // 获取栏目发布规则
            // PublishRuleBean ruleBean = publishService
            // .retrieveSinglePublishRuleBean( this.classPublishRuleId );
            //
            // String[] pathInfo = ruleBean.getFullContentClassPagePublishPath(
            // site, this, null, null, Integer.valueOf( 1 ) );
            //
            // return site.getSiteUrl() +pathInfo[1];
        }
        // 动态
        else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( this.classProduceType ) )
        {

            // if( site.isNotHost() )
            {
                return site.getPadSiteUrl()
                // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                    // + Constant.CONTENT.URL_SEP
                    + StringUtil.replaceString( this.padClassTemplateUrl, "{class-id}",
                        this.classId.toString(), false, false );
            }
            // else
            // {
            // return site.getSiteUrl()
            // + site.getSiteRoot()
            // + Constant.CONTENT.URL_SEP
            // + Constant.CONTENT.TEMPLATE_BASE
            // + Constant.CONTENT.URL_SEP
            // + StringUtil.replaceString( this.classTemplateUrl,
            // "{class-id}", this.classId.toString(), false, false );
            // }

        }

        return "";

    }

    public String getLogoImageResId()
    {
        if( StringUtil.isStringNotNull( this.logoImage ) )
        {
            return new String( this.logoImage.substring( this.logoImage.indexOf( "id=" ) + 3,
                this.logoImage.indexOf( ";", this.logoImage.indexOf( "id=" ) + 3 ) ) );
        }

        return "";
    }

    public String getLogoImageImageW()
    {
        if( StringUtil.isStringNotNull( this.logoImage ) )
        {
            return new String( this.logoImage.substring( this.logoImage.indexOf( "iw=" ) + 3,
                this.logoImage.indexOf( ";", this.logoImage.indexOf( "iw=" ) + 3 ) ) );
        }

        return "";
    }

    public ContentClassBean getFirstChild()
    {
        if( firstChild == null )
        {
            List resList = channelService.retrieveAllChildConetentClassBeanNotSpecByParentLinear(
                this.linearOrderFlag, this.siteFlag, "up" );

            if( !resList.isEmpty() && resList.size() > 1 )
            {
                firstChild = ( ContentClassBean ) resList.get( 1 );
            }
            else
            {
                firstChild = new ContentClassBean();
            }

        }

        return firstChild;
    }

    public String getLogoImageImageH()
    {
        if( StringUtil.isStringNotNull( this.logoImage ) )
        {
            return new String( this.logoImage.substring( this.logoImage.indexOf( "ih=" ) + 3,
                this.logoImage.indexOf( ";", this.logoImage.indexOf( "ih=" ) + 3 ) ) );
        }

        return "";
    }

    public String getBannerResId()
    {
        if( StringUtil.isStringNotNull( this.banner ) )
        {
            return new String( this.banner.substring( this.banner.indexOf( "id=" ) + 3, this.banner
                .indexOf( ";", this.banner.indexOf( "id=" ) + 3 ) ) );
        }

        return "";
    }

    public String getBannerImageW()
    {
        if( StringUtil.isStringNotNull( this.banner ) )
        {
            return new String( this.banner.substring( this.banner.indexOf( "iw=" ) + 3, this.banner
                .indexOf( ";", this.banner.indexOf( "iw=" ) + 3 ) ) );
        }

        return "";
    }

    public String getBannerImageH()
    {
        if( StringUtil.isStringNotNull( this.banner ) )
        {
            return new String( this.banner.substring( this.banner.indexOf( "ih=" ) + 3, this.banner
                .indexOf( ";", this.banner.indexOf( "ih=" ) + 3 ) ) );
        }

        return "";
    }

    public String getBannerReUrl()
    {
        if( StringUtil.isStringNotNull( this.banner ) )
        {
            return new String( this.banner.substring( this.banner.indexOf( "reUrl=" ) + 6,
                this.banner.indexOf( ";", this.banner.indexOf( "reUrl=" ) + 6 ) ) );
        }

        return "";
    }

    public boolean getImmedStaticFlag()
    {
        int tmpVar = this.immediatelyStaticAction.intValue();
        if( tmpVar == 1 )
        {
            return true;
        }
        else if( tmpVar == 2 )
        {
            return false;
        }

        return false;
    }

    // public String getClassLinkNotUse()
    // {
    // SystemRuntimeConfig config = SystemConfiguration.getInstance()
    // .getSystemConfig();
    // String base = config.getSystemBasePathNotUse();
    //
    // String siteBase = base + SITE_BASE;
    //
    // String endClassLink = "";
    // if( this.getClassProduceType().intValue() == 3 )
    // {
    // endClassLink = base + this.getStaticPageUrl();
    // }
    // else if( this.getClassProduceType().intValue() == 1 )
    // {
    // endClassLink = siteBase + this.getClassTemplateUrl();
    // }
    //
    // log.info( "最终栏目链接:"
    //
    // + endClassLink );
    //
    // return endClassLink;
    // }

    public String getLayerUIClassName()
    {
        StringBuilder buf = new StringBuilder();
        for ( int i = 0; i < ( layer.intValue() - 1 ); i++ )
        {
            buf.append( Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR + Constant.SITE_CHANNEL.HTML_BLANK_CHAR );
        }

        String layerUIClassName = null;
        if( this.isLeaf.intValue() == 1 && this.layer.intValue() > 1 )
        {
            layerUIClassName = buf + "<img id='img" + this.linearOrderFlag
                + "' src='../../core/style/default/images/control_small.png'/>" + className;
        }
        else
        {

            layerUIClassName = buf + "<img id='img" + this.linearOrderFlag
                + "' src='../../core/style/default/images/t_small.png'/>" + className;
        }

        return layerUIClassName;
    }

    public String getLayerUIBlankClassName()
    {
        StringBuilder buf = new StringBuilder();
        for ( int i = 0; i < ( layer.intValue() - 1 ); i++ )
        {
            buf.append( Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR );
        }

        buf.append( className );

        return buf.toString();
    }

    public String getClassProduceString()
    {

        if( this.classProduceType.intValue() == 3 )
        {
            return "<img alt=\"栏目列表静态\" src=\"../../core/style/icon/tick.png\"/>";
        }
        else
        {
            return "<img alt=\"栏目列表静态\" src=\"../../core/style/icon/del.gif\"/>";
        }

    }

    public String getClassHomeProduceString()
    {

        if( this.classHomeProduceType.intValue() == 3 )
        {
            return "<img alt=\"栏目首页静态\" src=\"../../core/style/icon/tick.png\"/>";
        }
        else
        {
            return "<img alt=\"栏目首页静态\" src=\"../../core/style/icon/del.gif\"/>";
        }
    }

    public String getContentProduceString()
    {

        if( this.contentProduceType.intValue() == 3 )
        {
            return "<img alt=\"内容页静态\" src=\"../../core/style/icon/tick.png\"/>";
        }
        else
        {
            return "<img alt=\"栏目列表静态\" src=\"../../core/style/icon/del.gif\"/>";
        }
    }

    public String getProduceTypeString()
    {

        if( ( this.classProduceType.intValue() == 1 || this.classProduceType.intValue() == 2 )
            && ( this.contentProduceType.intValue() == 1 || this.contentProduceType.intValue() == 2 ) )
        {
            return "全动态";
        }

        if( this.classProduceType.intValue() == 3 && this.contentProduceType.intValue() == 3 )
        {
            return "全静态";
        }

        if( this.classProduceType.intValue() == 3
            && ( this.contentProduceType.intValue() == 1 || this.contentProduceType.intValue() == 2 ) )
        {
            return "栏目静态<br>内容动态";
        }

        if( this.contentProduceType.intValue() == 3
            && ( this.classProduceType.intValue() == 1 || this.classProduceType.intValue() == 2 ) )
        {
            return "栏目动态<br>内容静态";
        }

        // TODO
        String produceTypeString = "";
        switch ( this.classProduceType.intValue() )
        {
            case 1:
                produceTypeString = D_CLASS_TYPE_STRING;
                break;
            case 2:
                produceTypeString = D_CLASS_TYPE_STRING;
                break;
            case 3:
                produceTypeString = S_CLASS_TYPE_STRING;
                break;
            default:
                produceTypeString = "未知";
        }

        switch ( this.contentProduceType.intValue() )
        {
            // JSP
            case 1:
                produceTypeString += D_CONTENT_TYPE_STRING;
                break;
            case 2:
                produceTypeString += D_CONTENT_TYPE_STRING;
                break;
            case 3:
                produceTypeString += S_CONTENT_TYPE_STRING;
                break;
            default:
                produceTypeString += "未知";
        }

        return produceTypeString;
    }

    public boolean isNeedStaticAction()
    {
        if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.classHomeProduceType )
            || Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.classProduceType )
            || Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.contentProduceType ) )
        {
            return true;
        }

        return false;
    }

    public String getContentTypeName()
    {
        String classContentType = "";
        switch ( this.contentType.intValue() )
        {
            case 1:
                classContentType = "文章";
                break;
            case 2:
                classContentType = "图集";
                break;
            case 3:
                classContentType = "视频";
                break;
            case 4:
                classContentType = "下载";
                break;
            case 0:
                classContentType = "组合";
                break;
            default:
                classContentType = "扩展模型";
        }
        return classContentType;
    }

    // public String getStaticCheckBox()
    // {
    // String checkBox = " <input type='checkbox' id='${Class.classId}'
    // name='${Class.classId}' value='${Class.classId}'
    // onclick='regCheckBox(this);' />";
    // }

    private String disposeUrlLink( Map mainInfo, SiteGroupBean site, ContentClassBean classBean )
    {
        String contentTemplateUrl = ( String ) mainInfo.get( "especialTemplateUrl" );

        if( StringUtil.isStringNull( contentTemplateUrl ) )
        {
            // 如果当前的单个内容没有特殊模斑则将取栏目共用模版
            contentTemplateUrl = classBean.getContentTemplateUrl();
            // if( StringUtil.isStringNull( contentTemplateUrl ) )
            // {
            // log.error( "[ContentLinkTag] 内容模型名称丢失,Info:"
            // + contentInfo );
            // return EVAL_BODY_INCLUDE;
            // }
        }

        if( site != null )
        {
            // if( site.isNotHost() )
            {
                return site.getSiteUrl()
                // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                    // + Constant.CONTENT.URL_SEP
                    + StringUtil.replaceString( StringUtil.replaceString( contentTemplateUrl,
                        "{content-id}", mainInfo.get( "contentId" ) != null ? mainInfo.get(
                            "contentId" ).toString() : "", false, false ), "{class-id}", mainInfo
                        .get( "classId" ) != null ? mainInfo.get( "classId" ).toString() : "",
                        false, false );
            }
            // else
            // {
            // result.put( "contentUrl", site.getSiteUrl()
            // // + site.getSiteRoot()
            // // + Constant.CONTENT.URL_SEP
            // + Constant.CONTENT.TEMPLATE_BASE
            // + Constant.CONTENT.URL_SEP
            // + StringUtil.replaceString( contentTemplateUrl,
            // "{content-id}", ( ( Long ) result
            // .get( "contentId" ) ).toString(), false,
            // false ) );
            // }

        }

        return "";

    }

}
