package cn.com.mjsoft.cms.content.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.cache.jsr14.NoToStringMap;
import cn.com.mjsoft.framework.util.StringUtil;

public class ContentCommendPushInfoBean
{
    private static ChannelService channelService = ChannelService.getInstance();

    private static ContentService contentService = ContentService.getInstance();

    private Long infoId = Long.valueOf( -1 );
    private Long rowFlag;
    private Integer rowOrder;
    private Long contentId;
    private String title;
    private String url;
    private String img;
    private String summary;
    private Timestamp addTime;
    private Long modelId;
    private Long classId;
    private Long commendTypeId;
    private Integer orderFlag;
    private String typeFlag;
    private String commendFlag;
    private String commendMan;
    private String siteFlag;

    // 业务字段
    private Boolean moreInfo = Boolean.FALSE;

    private List rowInfoList = new ArrayList();
    
    private Map ext = null;

    @SuppressWarnings( "unchecked" )
    public Map getExt()
    {
        if( ext == null )
        {
            ext = new NoToStringMap();

            ContentCommendTypeBean tb = channelService.retrieveSingleContentCommendTypeBeanByTypeId( this.commendTypeId );
            
            if(tb != null)
            {
                ext.putAll( contentService
                    .retrieveSingleUserDefineContentOnlyModelData(
                        tb.getModelId(), this.infoId, this.siteFlag ) );
            }

        }

        return ext;
    }

    public Long getInfoId()
    {
        return this.infoId;
    }

    public void setInfoId( Long infoId )
    {
        this.infoId = infoId;
    }

    public Long getRowFlag()
    {
        return rowFlag;
    }

    public void setRowFlag( Long rowFlag )
    {
        this.rowFlag = rowFlag;
    }

    public Integer getRowOrder()
    {
        return this.rowOrder;
    }

    public void setRowOrder( Integer rowOrder )
    {
        this.rowOrder = rowOrder;
    }

    public Long getContentId()
    {
        return this.contentId;
    }

    public void setContentId( Long contentId )
    {
        this.contentId = contentId;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public String getSummary()
    {
        return this.summary;
    }

    public void setSummary( String summary )
    {
        this.summary = summary;
    }

    public Timestamp getAddTime()
    {
        return addTime;
    }

    public void setAddTime( Timestamp addTime )
    {
        this.addTime = addTime;
    }

    public Long getModelId()
    {
        return this.modelId;
    }

    public void setModelId( Long modelId )
    {
        this.modelId = modelId;
    }

    public Long getClassId()
    {
        return this.classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Long getCommendTypeId()
    {
        return this.commendTypeId;
    }

    public void setCommendTypeId( Long commendTypeId )
    {
        this.commendTypeId = commendTypeId;
    }

    public Integer getOrderFlag()
    {
        return this.orderFlag;
    }

    public void setOrderFlag( Integer orderFlag )
    {
        this.orderFlag = orderFlag;
    }

    public String getTypeFlag()
    {
        return this.typeFlag;
    }

    public void setTypeFlag( String typeFlag )
    {
        this.typeFlag = typeFlag;
    }

    public String getCommendFlag()
    {
        return this.commendFlag;
    }

    public void setCommendFlag( String commendFlag )
    {
        this.commendFlag = commendFlag;
    }

    public String getCommendMan()
    {
        return commendMan;
    }

    public void setCommendMan( String commendMan )
    {
        this.commendMan = commendMan;
    }

    public void setImg( String img )
    {
        this.img = img;
    }

    public Boolean getMoreInfo()
    {
        return moreInfo;
    }

    public void setMoreInfo( Boolean moreInfo )
    {
        this.moreInfo = moreInfo;
    }

    public List getRowInfoList()
    {
        return rowInfoList;
    }

    public void setRowInfoList( List rowInfoList )
    {
        this.rowInfoList = rowInfoList;
    }

    public String getSiteFlag()
    {
        return siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    // 业务方法

    public String getUrlOrgVal()
    {
        return this.url;
    }

    public String getUrl()
    {
        boolean needSiteUrl = false;

        if( this.url != null && this.url.startsWith( "JTOPCMS-CONTENT-URL" ) )
        {
            // ContentMainInfoBean mainInfo = contentService
            // .retrieveSingleContentMainInfoBean( this.contentId );

            Map mainInfo = contentService.retrieveSingleUserDefineContent(
                this.contentId, Integer.valueOf( 1 ) );

            // url link
            ContentClassBean classBean = channelService
                .retrieveSingleClassBeanInfoByClassId( ( Long ) mainInfo
                    .get( "classId" ) );

            if( classBean != null && classBean.getClassId().longValue() > 0 )
            {
                SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                    .getEntry( classBean.getSiteFlag() );

                if( mainInfo.isEmpty() )
                {
                    return site.getSiteUrl();
                }

                // 每个内容必须对应有其所属的栏目

                // SystemRuntimeConfig config =
                // SystemConfiguration.getInstance()
                // .getSystemConfig();
                // String base = config.getSystemBasePath();
                // 外部地址

                if( StringUtil.isStringNotNull( ( String ) mainInfo
                    .get( "outLink" ) ) )
                {
                    return ( String ) mainInfo.get( "outLink" );
                }
                // HTML
                else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE
                    .equals( classBean.getContentProduceType() ) )
                {
                    String staticUrl = ( String ) mainInfo
                        .get( "staticPageUrl" );

                    if( StringUtil.isStringNotNull( staticUrl ) )
                    {
                        return site.getSiteUrl()
                        // + site.getPublishRoot()
                            + staticUrl;
                    }
                    else
                    {
                        // 若内容静态URL不存在,则输出动态URL
                        String contentTemplateUrl = ( String ) mainInfo
                            .get( "especialTemplateUrl" );

                        if( StringUtil.isStringNull( contentTemplateUrl ) )
                        {
                            // 如果当前的单个内容没有特殊模斑则将取栏目共用模版
                            contentTemplateUrl = classBean
                                .getContentTemplateUrl();
                            // if( StringUtil.isStringNull( contentTemplateUrl )
                            // )
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
                                    + StringUtil.replaceString(
                                        contentTemplateUrl, "{content-id}",
                                        mainInfo.get( "contentId" ).toString(),
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

                        // 获取栏目发布规则
                        // PublishRuleBean ruleBean = publishService
                        // .retrieveSinglePublishRuleBean( classBean
                        // .getContentPublishRuleId() );
                        //
                        // if( ruleBean == null )
                        // {
                        // System.err.println( "系统发布规则不存在!!!" );
                        // }
                        //
                        // String[] pathInfo =
                        // ruleBean.getFullContentClassPublishPath(
                        // site, classBean, result, null );
                        // result.put( "contentUrl", site.getSiteUrl()
                        // // + site.getPublishRoot()
                        // + pathInfo[1] );
                    }

                }//
                else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE
                    .equals( classBean.getContentProduceType() ) )
                {
                    String contentTemplateUrl = ( String ) mainInfo
                        .get( "especialTemplateUrl" );

                    if( StringUtil.isStringNull( contentTemplateUrl ) )
                    {
                        // 如果当前的单个内容没有特殊模斑则将取栏目共用模版
                        contentTemplateUrl = classBean.getContentTemplateUrl();
                        // if( StringUtil.isStringNull( contentTemplateUrl )
                        // )
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
                                + StringUtil.replaceString( contentTemplateUrl,
                                    "{content-id}", mainInfo.get( "contentId" )
                                        .toString(), false, false );

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
                }

            }
            else
            {
                needSiteUrl = true;
            }

        }

        if( StringUtil.isStringNull( this.url ) )
        {
            needSiteUrl = true;
        }

        if( needSiteUrl )
        {
            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                .getEntry( this.getSiteFlag() );

            return site.getSiteUrl();
        }

        return this.url;
    }

    public String getImg()
    {
        if( StringUtil.isStringNotNull( this.img ) )
        {
            SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                .getEntry( this.siteFlag );

            String reUrl = StringUtil.subString( this.img, this.img
                .indexOf( "reUrl=" ) + 6, this.img.indexOf( ";", this.img
                .indexOf( "reUrl=" ) + 6 ) );

            return site.getSiteImagePrefixUrl() + reUrl;
        }

        return "no_url";
    }

    public String getImgResId()
    {
        if( StringUtil.isStringNotNull( this.img ) )
        {
            return new String( this.img.substring(
                this.img.indexOf( "id=" ) + 3, this.img.indexOf( ";", this.img
                    .indexOf( "id=" ) + 3 ) ) );
        }

        return "";
    }

    public String getImgImageW()
    {
        if( StringUtil.isStringNotNull( this.img ) )
        {
            return new String( this.img.substring(
                this.img.indexOf( "iw=" ) + 3, this.img.indexOf( ";", this.img
                    .indexOf( "iw=" ) + 3 ) ) );
        }

        return "";
    }

    public String getImgImageH()
    {
        if( StringUtil.isStringNotNull( this.img ) )
        {
            return new String( this.img.substring(
                this.img.indexOf( "ih=" ) + 3, this.img.indexOf( ";", this.img
                    .indexOf( "ih=" ) + 3 ) ) );
        }

        return "";
    }

    public String getImgReUrl()
    {
        if( StringUtil.isStringNotNull( this.img ) )
        {
            return new String( this.img.substring(
                this.img.indexOf( "reUrl=" ) + 6, this.img.indexOf( ";",
                    this.img.indexOf( "reUrl=" ) + 6 ) ) );
        }

        return "";
    }

    public Map getContent()
    {
        return contentService.retrieveSingleUserDefineContent( this.contentId,
            Integer.valueOf( 1 ) );
    }
}
