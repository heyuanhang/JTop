package cn.com.mjsoft.cms.channel.bean;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.publish.bean.PublishRuleBean;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.util.StringUtil;

public class ContentCommendTypeBean
{
    private static PublishService publishService = PublishService.getInstance();

    private Long commendTypeId = Long.valueOf( -1 );

    private String commendName;

    private Long classId = Long.valueOf( -9999 );// 全站通用

    private Integer isSpec;

    private String classLinerFlag;

    private Integer childClassMode = Constant.COMMON.ON;

    private String commFlag;

    private Long modelId;

    private String typeDesc;

    private Integer mustCensor;

    private String creator;

    private String siteFlag;

    private Integer imageWidth;

    private Integer imageHeight;

    private String listTemplateUrl;

    private String mobListTemplateUrl;

    private String padListTemplateUrl;

    private Integer listProduceType;

    private Long listPublishRuleId;

    private String listStaticUrl;

    public String getCommendName()
    {
        return commendName;
    }

    public void setCommendName( String commendName )
    {
        this.commendName = commendName;
    }

    public Long getCommendTypeId()
    {
        return commendTypeId;
    }

    public void setCommendTypeId( Long commendTypeId )
    {
        this.commendTypeId = commendTypeId;
    }

    public String getCommFlag()
    {
        return commFlag;
    }

    public void setCommFlag( String commFlag )
    {
        this.commFlag = commFlag;
    }

    public String getTypeDesc()
    {
        return typeDesc;
    }

    public void setTypeDesc( String typeDesc )
    {
        this.typeDesc = typeDesc;
    }

    public Integer getChildClassMode()
    {
        return childClassMode;
    }

    public void setChildClassMode( Integer childClassMode )
    {
        this.childClassMode = childClassMode;
    }

    public Long getClassId()
    {
        return classId;
    }

    public void setClassId( Long classId )
    {
        this.classId = classId;
    }

    public Integer getIsSpec()
    {
        return isSpec;
    }

    public void setIsSpec( Integer isSpec )
    {
        this.isSpec = isSpec;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public String getClassLinerFlag()
    {
        return classLinerFlag;
    }

    public void setClassLinerFlag( String classLinerFlag )
    {
        this.classLinerFlag = classLinerFlag;
    }

    public Integer getMustCensor()
    {
        return mustCensor;
    }

    public void setMustCensor( Integer mustCensor )
    {
        this.mustCensor = mustCensor;
    }

    public String getSiteFlag()
    {
        return siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    public Integer getImageHeight()
    {
        return imageHeight;
    }

    public void setImageHeight( Integer imageHeight )
    {
        this.imageHeight = imageHeight;
    }

    public Integer getImageWidth()
    {
        return imageWidth;
    }

    public void setImageWidth( Integer imageWidth )
    {
        this.imageWidth = imageWidth;
    }

    public Integer getListProduceType()
    {
        return listProduceType;
    }

    public void setListProduceType( Integer listProduceType )
    {
        this.listProduceType = listProduceType;
    }

    public Long getListPublishRuleId()
    {
        return listPublishRuleId;
    }

    public void setListPublishRuleId( Long listPublishRuleId )
    {
        this.listPublishRuleId = listPublishRuleId;
    }

    public String getListStaticUrl()
    {
        return listStaticUrl;
    }

    public void setListStaticUrl( String listStaticUrl )
    {
        this.listStaticUrl = listStaticUrl;
    }

    public String getListTemplateUrl()
    {
        return listTemplateUrl;
    }

    public void setListTemplateUrl( String listTemplateUrl )
    {
        this.listTemplateUrl = listTemplateUrl;
    }

    // 业务方法

    public Long getModelId()
    {
        return modelId;
    }

    public void setModelId( Long modelId )
    {
        this.modelId = modelId;
    }

    public String getMobListTemplateUrl()
    {
        return mobListTemplateUrl;
    }

    public void setMobListTemplateUrl( String mobListTemplateUrl )
    {
        this.mobListTemplateUrl = mobListTemplateUrl;
    }

    public String getPadListTemplateUrl()
    {
        return padListTemplateUrl;
    }

    public void setPadListTemplateUrl( String padListTemplateUrl )
    {
        this.padListTemplateUrl = padListTemplateUrl;
    }

    public String getUrl()
    {
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( this.siteFlag );

        // HTML
        if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.listProduceType ) )
        {
            if( site.isNotHost() )
            {
                return site.getSiteUrl() + this.listStaticUrl;
            }
            else
            {
                if( StringUtil.isStringNotNull( this.listStaticUrl ) )
                {
                    return site.getSiteUrl()
                    // + site.getSiteRoot()+ Constant.CONTENT.URL_SEP
                        // + site.getPublishRoot()
                        + this.listStaticUrl;
                }
                else
                {
                    // 获取栏目发布规则
                    PublishRuleBean ruleBean = publishService
                        .retrieveSinglePublishRuleBean( this.listPublishRuleId );

                    if( ruleBean == null )
                    {
                        return "NO_RULE";
                    }

                    String[] pathInfo = ruleBean.getFullCommendInfoPublishPath( site, this, null,
                        null, Integer.valueOf( 1 ) );

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
        else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( this.listProduceType ) )
        {

            // if( site.isNotHost() )
            {
                if( site != null )
                {
                    return site.getSiteUrl()
                    // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                        // + Constant.CONTENT.URL_SEP
                        + StringUtil.replaceString( StringUtil.replaceString( this.listTemplateUrl,
                            "{type-id}", this.commendTypeId.toString(), false, false ),
                            "{class-id}", this.classId.toString(), false, false );
                }
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

    public String getMobUrl()
    {
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( this.siteFlag );

        // HTML
        if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.listProduceType ) )
        {
            // if( site.isNotHost() )
            // {
            // return site.getSiteUrl() + this.listStaticUrl;
            // }
            // else
            {
                // if( StringUtil.isStringNotNull( this.listStaticUrl ) )
                // {
                // return site.getSiteUrl()
                // // + site.getSiteRoot()+ Constant.CONTENT.URL_SEP
                // // + site.getPublishRoot()
                // + this.listStaticUrl;
                // }
                // else
                {
                    // 获取栏目发布规则
                    PublishRuleBean ruleBean = publishService
                        .retrieveSinglePublishRuleBean( this.listPublishRuleId );

                    if( ruleBean == null )
                    {
                        return "NO_RULE";
                    }

                    String[] pathInfo = ruleBean.getFullCommendInfoPublishPath( site, this, null,
                        null, Integer.valueOf( 1 ) );

                    return site.getSiteUrl() + pathInfo[3];

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
        else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( this.listProduceType ) )
        {

            // if( site.isNotHost() )
            {
                if( site != null )
                {
                    return site.getSiteUrl()
                    // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                        // + Constant.CONTENT.URL_SEP
                        + StringUtil.replaceString( StringUtil.replaceString(
                            this.mobListTemplateUrl, "{type-id}", this.commendTypeId.toString(),
                            false, false ), "{class-id}", this.classId.toString(), false, false );
                }
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

    public String getPadUrl()
    {
        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( this.siteFlag );

        // HTML
        if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.listProduceType ) )
        {
            // if( site.isNotHost() )
            // {
            // return site.getSiteUrl() + this.listStaticUrl;
            // }
            // else
            {
                // if( StringUtil.isStringNotNull( this.listStaticUrl ) )
                // {
                // return site.getSiteUrl()
                // // + site.getSiteRoot()+ Constant.CONTENT.URL_SEP
                // // + site.getPublishRoot()
                // + this.listStaticUrl;
                // }
                // else
                {
                    // 获取栏目发布规则
                    PublishRuleBean ruleBean = publishService
                        .retrieveSinglePublishRuleBean( this.listPublishRuleId );

                    if( ruleBean == null )
                    {
                        return "NO_RULE";
                    }

                    String[] pathInfo = ruleBean.getFullCommendInfoPublishPath( site, this, null,
                        null, Integer.valueOf( 1 ) );

                    return site.getSiteUrl() + pathInfo[5];

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
        else if( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE.equals( this.listProduceType ) )
        {

            // if( site.isNotHost() )
            {
                if( site != null )
                {
                    return site.getSiteUrl()
                    // + Constant.CONTENT.TEMPLATE_BASE 隐藏template目录
                        // + Constant.CONTENT.URL_SEP
                        + StringUtil.replaceString( StringUtil.replaceString(
                            this.padListTemplateUrl, "{type-id}", this.commendTypeId.toString(),
                            false, false ), "{class-id}", this.classId.toString(), false, false );
                }
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

}
