package cn.com.mjsoft.cms.site.bean;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.framework.cache.jsr14.NoToStringMap;
import cn.com.mjsoft.framework.config.JTopServer;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.SiteNode;
import cn.com.mjsoft.framework.web.engine.InterceptFilter;

public class SiteGroupBean implements SiteNode
{
    private static ContentService contentService = ContentService.getInstance();

    private static ResourcesService resService = ResourcesService.getInstance();

    private Long siteId = Long.valueOf( -1 );

    private String siteFlag = "";

    private String siteRoot = "";

    private String siteName = "";

    private String siteUrl = "";

    private Integer orderFlag = 0;

    private String home301Url = "";

    private String mobSiteUrl = "";

    private String padSiteUrl = "";

    private Integer pcVm = 1;

    private Integer mobVm = 0;

    private Integer padVm = 0;

    private String homePageTemplate;

    private String mobHomePageTemplate;

    private String padHomePageTemplate;

    private String siteDesc;

    private Integer homePageProduceType;

    private String homePageStaticUrl;

    private String mobHomePageStaticUrl;

    private String padHomePageStaticUrl;

    private String imageRoot;

    private String mediaRoot;

    private String fileRoot;

    private String publishRoot;

    private String templateCharset = Constant.SITE_CHANNEL.DEF_PAGE_CODE;

    private String logoImage;
    private String copyright;
    private String icp;
    private Integer staticFileType;
    private String seoTitle;
    private String seoKeyword;
    private String seoDesc;
    private Integer shareMode;
    private Integer downOutImage;
    private Integer deleteOutLink;
    private Integer summaryLength;
    private Integer defClickCount;
    private String managerIP;
    private Integer siteCollType;
    private String outSiteCollUrl;
    private Integer sameTitle;
    private Integer genKw;
    private String sendMailHost;
    private String mail;
    private String mailUserName;
    private String mailUserPW;
    private Integer mailSSL;
    private String smsApiUrl;

    private String smsAccount;

    private String smsPW;

    private Integer smsSendOnceSec;// 默认一分钟一条

    private Integer smsMaxCount;// 默认最大50条

    private Integer smsIpDayCount;// 默认同一IP每天只能发10条
    private Integer managerLoginTime;
    private Integer searchFun;
    private Integer useFW;
    private String imageAllowType;
    private String mediaAllowType;
    private String fileAllowType;
    private Integer imageMaxC;
    private Integer mediaMaxC;
    private Integer fileMaxC;
    private Integer useImageMark;
    private Integer imageMarkType;
    private String imageMarkPos;
    private String imageMarkChar;
    private String imageMark;
    private Integer imageMarkDis;
    private Integer offSetX;
    private Integer offSetY;
    private Integer defEditorImageW;
    private Integer defEditorImageH;
    private Integer defEditorImageDM;
    private Integer defHomeImageW;
    private Integer defHomeImageH;
    private Integer defHomeImageDM;
    private Integer defClassImageW;
    private Integer defClassImageH;
    private Integer defClassImageDM;
    private Integer defListImageW;
    private Integer defListImageH;
    private Integer defListImageDM;
    private Integer defContentImageW;
    private Integer defContentImageH;
    private Integer defContentImageDM;
    private Integer useState;

    private Integer allowMemberReg;
    private String qqAppId;
    private String qqAppKey;
    private String qqBackUri;
    private String wbAppId;
    private String wbAppKey;
    private String wbBackUri;
    private String regMailText = "";
    private String resetPwText = "";
    private String mailRegBackUri;
    private String relateMemberUri;
    private String resetPwBackUri;
    private String memberLoginUri;
    private Integer memberLoginOnce = Constant.COMMON.OFF;

    private String wxAppId;

    private String wxAppKey;

    private String wxPrevUid;

    private String thirdLoginErrorUri;

    private String thirdLoginSuccessUri;

    private Long memberDefRoleId;

    private Integer memberDefLv = Integer.valueOf( -1 );

    private Long memberDefSc;

    private Long extDataModelId = Long.valueOf( -1 );

    private Long extMemberModelId = Long.valueOf( -1 );

    private Integer memberExpire = Integer.valueOf( 60 );

    private Integer mobJump = Integer.valueOf( 0 );

    private Long mobSiteId = Long.valueOf( -1 );

    // 业务字段
    private String siteIndexUrl = null;

    private String siteIndexUri = null;

    private String mobSiteIndexUri = null;

    private String padSiteIndexUri = null;

    private String sitePublishPrefixUrl = null;

    private String siteImagePrefixUrl = null;

    private String siteMediaPrefixUrl = null;

    private String siteFilePrefixUrl = null;

    private String siteImagePrefixUrlDMode = null;

    private String siteMediaPrefixUrlDMode = null;

    private String siteFilePrefixUrlDMode = null;

    private String siteTemplateUrl = null;

    private boolean notHost = false;

    private String hostMainUrl = null;

    // 当前生效资源服务器url
    private String imageServerUrl = null;

    private String mediaServerUrl = null;

    private String fileServerUrl = null;

    private Map ext = null;

    public Map getExt()
    {
        if( ext == null )
        {
            ext = new NoToStringMap();

            ext.putAll( contentService.retrieveSingleUserDefineContentOnlyModelData(
                this.extDataModelId, this.siteId, this.siteFlag ) );
        }

        return ext;
    }

    public void setExt( Map ext )
    {
        this.ext = ext;
    }

    public String getHomePageTemplate()
    {
        return homePageTemplate;
    }

    public void setHomePageTemplate( String homePageTemplate )
    {
        this.homePageTemplate = homePageTemplate;
    }

    public String getSiteDesc()
    {
        return siteDesc;
    }

    public void setSiteDesc( String siteDesc )
    {
        this.siteDesc = siteDesc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.mjsoft.cms.site.bean.SiteNode#getSiteId()
     */
    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public String getSiteName()
    {
        return siteName;
    }

    public void setSiteName( String siteName )
    {
        this.siteName = siteName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.mjsoft.cms.site.bean.SiteNode#getSiteUrl()
     */
    public String getSiteUrl()
    {
        return siteUrl;
    }

    public void setSiteUrl( String siteUrl )
    {
        this.siteUrl = siteUrl;
    }

    public Integer getSearchFun()
    {
        return searchFun;
    }

    public void setSearchFun( Integer searchFun )
    {
        this.searchFun = searchFun;
    }

    public Integer getHomePageProduceType()
    {
        return homePageProduceType;
    }

    public void setHomePageProduceType( Integer homePageProduceType )
    {
        this.homePageProduceType = homePageProduceType;
    }

    public String getHomePageStaticUrl()
    {
        return homePageStaticUrl;
    }

    public void setHomePageStaticUrl( String homePageStaticUrl )
    {
        this.homePageStaticUrl = homePageStaticUrl;
    }

    public String getFileRoot()
    {
        return fileRoot;
    }

    public void setFileRoot( String fileRoot )
    {
        this.fileRoot = fileRoot;
    }

    public String getImageRoot()
    {
        return imageRoot;
    }

    public void setImageRoot( String imageRoot )
    {
        this.imageRoot = imageRoot;
    }

    public String getMediaRoot()
    {
        return mediaRoot;
    }

    public void setMediaRoot( String mediaRoot )
    {
        this.mediaRoot = mediaRoot;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.mjsoft.cms.site.bean.SiteNode#getSiteRoot()
     */
    public String getSiteRoot()
    {
        return siteRoot;
    }

    public void setSiteRoot( String siteRoot )
    {
        this.siteRoot = siteRoot;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.mjsoft.cms.site.bean.SiteNode#getPublishRoot()
     */
    public String getPublishRoot()
    {
        return publishRoot;
    }

    public void setPublishRoot( String publishRoot )
    {
        this.publishRoot = publishRoot;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.mjsoft.cms.site.bean.SiteNode#getSiteFlag()
     */
    public String getSiteFlag()
    {
        return siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    public String getTemplateCharset()
    {
        return templateCharset;
    }

    public void setTemplateCharset( String templateCharset )
    {
        this.templateCharset = templateCharset;
    }

    public String getCopyright()
    {
        return copyright;
    }

    public void setCopyright( String copyright )
    {
        this.copyright = copyright;
    }

    public Integer getDefClassImageH()
    {
        return defClassImageH;
    }

    public void setDefClassImageH( Integer defClassImageH )
    {
        this.defClassImageH = defClassImageH;
    }

    public Integer getDefClassImageW()
    {
        return defClassImageW;
    }

    public void setDefClassImageW( Integer defClassImageW )
    {
        this.defClassImageW = defClassImageW;
    }

    public Integer getDefClickCount()
    {
        return defClickCount;
    }

    public void setDefClickCount( Integer defClickCount )
    {
        this.defClickCount = defClickCount;
    }

    public Integer getDefContentImageH()
    {
        return defContentImageH;
    }

    public void setDefContentImageH( Integer defContentImageH )
    {
        this.defContentImageH = defContentImageH;
    }

    public Integer getDefContentImageW()
    {
        return defContentImageW;
    }

    public void setDefContentImageW( Integer defContentImageW )
    {
        this.defContentImageW = defContentImageW;
    }

    public Integer getDefEditorImageH()
    {
        return defEditorImageH;
    }

    public void setDefEditorImageH( Integer defEditorImageH )
    {
        this.defEditorImageH = defEditorImageH;
    }

    public Integer getDefEditorImageW()
    {
        return defEditorImageW;
    }

    public void setDefEditorImageW( Integer defEditorImageW )
    {
        this.defEditorImageW = defEditorImageW;
    }

    public Integer getDefHomeImageH()
    {
        return defHomeImageH;
    }

    public void setDefHomeImageH( Integer defHomeImageH )
    {
        this.defHomeImageH = defHomeImageH;
    }

    public Integer getDefHomeImageW()
    {
        return defHomeImageW;
    }

    public void setDefHomeImageW( Integer defHomeImageW )
    {
        this.defHomeImageW = defHomeImageW;
    }

    public Integer getDefListImageH()
    {
        return defListImageH;
    }

    public void setDefListImageH( Integer defListImageH )
    {
        this.defListImageH = defListImageH;
    }

    public Integer getDefListImageW()
    {
        return defListImageW;
    }

    public void setDefListImageW( Integer defListImageW )
    {
        this.defListImageW = defListImageW;
    }

    public Integer getDeleteOutLink()
    {
        return deleteOutLink;
    }

    public void setDeleteOutLink( Integer deleteOutLink )
    {
        this.deleteOutLink = deleteOutLink;
    }

    public Integer getDownOutImage()
    {
        return downOutImage;
    }

    public void setDownOutImage( Integer downOutImage )
    {
        this.downOutImage = downOutImage;
    }

    public String getFileAllowType()
    {
        return fileAllowType;
    }

    public void setFileAllowType( String fileAllowType )
    {
        this.fileAllowType = fileAllowType;
    }

    public String getIcp()
    {
        return icp;
    }

    public void setIcp( String icp )
    {
        this.icp = icp;
    }

    public String getImageAllowType()
    {
        return imageAllowType;
    }

    public void setImageAllowType( String imageAllowType )
    {
        this.imageAllowType = imageAllowType;
    }

    public String getImageMark()
    {
        if( StringUtil.isStringNotNull( this.imageMark ) )
        {
            String reUrl = StringUtil.subString( this.imageMark,
                this.imageMark.indexOf( "reUrl=" ) + 6, this.imageMark.indexOf( ";", this.imageMark
                    .indexOf( "reUrl=" ) + 6 ) );

            return getSiteImagePrefixUrl() + reUrl;
        }

        return "";
    }

    public String getImageMarkResId()
    {
        if( StringUtil.isStringNotNull( this.imageMark ) )
        {
            return new String( this.imageMark.substring( this.imageMark.indexOf( "id=" ) + 3,
                this.imageMark.indexOf( ";", this.imageMark.indexOf( "id=" ) + 3 ) ) );
        }

        return "";
    }

    public String getImageMarkImageW()
    {
        if( StringUtil.isStringNotNull( this.imageMark ) )
        {
            return new String( this.imageMark.substring( this.imageMark.indexOf( "iw=" ) + 3,
                this.imageMark.indexOf( ";", this.imageMark.indexOf( "iw=" ) + 3 ) ) );
        }

        return "";
    }

    public String getImageMarkImageH()
    {
        if( StringUtil.isStringNotNull( this.imageMark ) )
        {
            return new String( this.imageMark.substring( this.imageMark.indexOf( "ih=" ) + 3,
                this.imageMark.indexOf( ";", this.imageMark.indexOf( "ih=" ) + 3 ) ) );
        }

        return "";
    }

    public String getImageMarkReUrl()
    {
        if( StringUtil.isStringNotNull( this.imageMark ) )
        {
            return new String( this.imageMark.substring( this.imageMark.indexOf( "reUrl=" ) + 6,
                this.imageMark.indexOf( ";", this.imageMark.indexOf( "reUrl=" ) + 6 ) ) );
        }

        return "";
    }

    public void setImageMark( String imageMark )
    {
        this.imageMark = imageMark;
    }

    public String getImageMarkChar()
    {
        return imageMarkChar;
    }

    public void setImageMarkChar( String imageMarkChar )
    {
        this.imageMarkChar = imageMarkChar;
    }

    public String getImageMarkPos()
    {
        return imageMarkPos;
    }

    public void setImageMarkPos( String imageMarkPos )
    {
        this.imageMarkPos = imageMarkPos;
    }

    public Integer getImageMarkType()
    {
        return imageMarkType;
    }

    public void setImageMarkType( Integer imageMarkType )
    {
        this.imageMarkType = imageMarkType;
    }

    public String getLogoImage()
    {
        if( StringUtil.isStringNotNull( this.logoImage ) )
        {
            String reUrl = StringUtil.subString( this.logoImage,
                this.logoImage.indexOf( "reUrl=" ) + 6, this.logoImage.indexOf( ";", this.logoImage
                    .indexOf( "reUrl=" ) + 6 ) );

            return getSiteImagePrefixUrl() + reUrl;
        }

        return "no_url";
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

    public String getLogoImageImageH()
    {
        if( StringUtil.isStringNotNull( this.logoImage ) )
        {
            return new String( this.logoImage.substring( this.logoImage.indexOf( "ih=" ) + 3,
                this.logoImage.indexOf( ";", this.logoImage.indexOf( "ih=" ) + 3 ) ) );
        }

        return "";
    }

    public String getLogoImageReUrl()
    {
        if( StringUtil.isStringNotNull( this.logoImage ) )
        {
            return new String( this.logoImage.substring( this.logoImage.indexOf( "reUrl=" ) + 6,
                this.logoImage.indexOf( ";", this.logoImage.indexOf( "reUrl=" ) + 6 ) ) );
        }

        return "";
    }

    public void setLogoImage( String logoImage )
    {
        this.logoImage = logoImage;
    }

    public String getMail()
    {
        return mail;
    }

    public void setMail( String mail )
    {
        this.mail = mail;
    }

    public Integer getMailSSL()
    {
        return mailSSL;
    }

    public void setMailSSL( Integer mailSSL )
    {
        this.mailSSL = mailSSL;
    }

    public String getMailUserName()
    {
        return mailUserName;
    }

    public void setMailUserName( String mailUserName )
    {
        this.mailUserName = mailUserName;
    }

    public String getMailUserPW()
    {
        return mailUserPW;
    }

    public void setMailUserPW( String mailUserPW )
    {
        this.mailUserPW = mailUserPW;
    }

    public String getManagerIP()
    {
        return managerIP;
    }

    public void setManagerIP( String managerIP )
    {
        this.managerIP = managerIP;
    }

    public Integer getManagerLoginTime()
    {
        return managerLoginTime;
    }

    public void setManagerLoginTime( Integer managerLoginTime )
    {
        this.managerLoginTime = managerLoginTime;
    }

    public String getMediaAllowType()
    {
        return mediaAllowType;
    }

    public void setMediaAllowType( String mediaAllowType )
    {
        this.mediaAllowType = mediaAllowType;
    }

    public String getOutSiteCollUrl()
    {
        return outSiteCollUrl;
    }

    public void setOutSiteCollUrl( String outSiteCollUrl )
    {
        this.outSiteCollUrl = outSiteCollUrl;
    }

    public String getSendMailHost()
    {
        return sendMailHost;
    }

    public void setSendMailHost( String sendMailHost )
    {
        this.sendMailHost = sendMailHost;
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

    public Integer getShareMode()
    {
        return shareMode;
    }

    public void setShareMode( Integer shareMode )
    {
        this.shareMode = shareMode;
    }

    public Integer getSiteCollType()
    {
        return siteCollType;
    }

    public void setSiteCollType( Integer siteCollType )
    {
        this.siteCollType = siteCollType;
    }

    public Integer getStaticFileType()
    {
        return staticFileType;
    }

    public void setStaticFileType( Integer staticFileType )
    {
        this.staticFileType = staticFileType;
    }

    public Integer getSummaryLength()
    {
        return summaryLength;
    }

    public void setSummaryLength( Integer summaryLength )
    {
        this.summaryLength = summaryLength;
    }

    public Integer getDefClassImageDM()
    {
        return defClassImageDM;
    }

    public void setDefClassImageDM( Integer defClassImageDM )
    {
        this.defClassImageDM = defClassImageDM;
    }

    public Integer getDefContentImageDM()
    {
        return defContentImageDM;
    }

    public void setDefContentImageDM( Integer defContentImageDM )
    {
        this.defContentImageDM = defContentImageDM;
    }

    public Integer getDefEditorImageDM()
    {
        return defEditorImageDM;
    }

    public void setDefEditorImageDM( Integer defEditorImageDM )
    {
        this.defEditorImageDM = defEditorImageDM;
    }

    public Integer getDefHomeImageDM()
    {
        return defHomeImageDM;
    }

    public void setDefHomeImageDM( Integer defHomeImageDM )
    {
        this.defHomeImageDM = defHomeImageDM;
    }

    public Integer getDefListImageDM()
    {
        return defListImageDM;
    }

    public void setDefListImageDM( Integer defListImageDM )
    {
        this.defListImageDM = defListImageDM;
    }

    public Integer getUseFW()
    {
        return useFW;
    }

    public void setUseFW( Integer useFW )
    {
        this.useFW = useFW;
    }

    public Integer getUseImageMark()
    {
        return useImageMark;
    }

    public void setUseImageMark( Integer useImageMark )
    {
        this.useImageMark = useImageMark;
    }

    public Integer getImageMarkDis()
    {
        return imageMarkDis;
    }

    public void setImageMarkDis( Integer imageMarkDis )
    {
        this.imageMarkDis = imageMarkDis;
    }

    public Integer getUseState()
    {
        return useState;
    }

    public void setUseState( Integer useState )
    {
        this.useState = useState;
    }

    public void setSiteFilePrefixUrl( String siteFilePrefixUrl )
    {
        this.siteFilePrefixUrl = siteFilePrefixUrl;
    }

    public void setSiteImagePrefixUrl( String siteImagePrefixUrl )
    {
        this.siteImagePrefixUrl = siteImagePrefixUrl;
    }

    public void setSiteIndexUri( String siteIndexUri )
    {
        this.siteIndexUri = siteIndexUri;
    }

    public void setSiteIndexUrl( String siteIndexUrl )
    {
        this.siteIndexUrl = siteIndexUrl;
    }

    public void setSiteMediaPrefixUrl( String siteMediaPrefixUrl )
    {
        this.siteMediaPrefixUrl = siteMediaPrefixUrl;
    }

    public void setSitePublishPrefixUrl( String sitePublishPrefixUrl )
    {
        this.sitePublishPrefixUrl = sitePublishPrefixUrl;
    }

    public void setSiteTemplateUrl( String siteTemplateUrl )
    {
        this.siteTemplateUrl = siteTemplateUrl;
    }

    public String getSmsAccount()
    {
        return smsAccount;
    }

    public void setSmsAccount( String smsAccount )
    {
        this.smsAccount = smsAccount;
    }

    public String getSmsApiUrl()
    {
        return smsApiUrl;
    }

    public void setSmsApiUrl( String smsApiUrl )
    {
        this.smsApiUrl = smsApiUrl;
    }

    public Integer getSmsIpDayCount()
    {
        return smsIpDayCount;
    }

    public void setSmsIpDayCount( Integer smsIpDayCount )
    {
        this.smsIpDayCount = smsIpDayCount;
    }

    public Integer getSmsMaxCount()
    {
        return smsMaxCount;
    }

    public void setSmsMaxCount( Integer smsMaxCount )
    {
        this.smsMaxCount = smsMaxCount;
    }

    public String getSmsPW()
    {
        return smsPW;
    }

    public void setSmsPW( String smsPW )
    {
        this.smsPW = smsPW;
    }

    public Integer getSmsSendOnceSec()
    {
        return smsSendOnceSec;
    }

    public void setSmsSendOnceSec( Integer smsSendOnceSec )
    {
        this.smsSendOnceSec = smsSendOnceSec;
    }

    public Long getExtDataModelId()
    {
        return extDataModelId;
    }

    public void setExtDataModelId( Long extDataModelId )
    {
        this.extDataModelId = extDataModelId;
    }

    public Integer getGenKw()
    {
        return genKw;
    }

    public void setGenKw( Integer genKw )
    {
        this.genKw = genKw;
    }

    public Long getExtMemberModelId()
    {
        return extMemberModelId;
    }

    public void setExtMemberModelId( Long extMemberModelId )
    {
        this.extMemberModelId = extMemberModelId;
    }

    public Integer getAllowMemberReg()
    {
        return allowMemberReg;
    }

    public void setAllowMemberReg( Integer allowMemberReg )
    {
        this.allowMemberReg = allowMemberReg;
    }

    public String getMailRegBackUri()
    {
        return mailRegBackUri;
    }

    public void setMailRegBackUri( String mailRegBackUri )
    {
        this.mailRegBackUri = mailRegBackUri;
    }

    public String getQqAppId()
    {
        return qqAppId;
    }

    public void setQqAppId( String qqAppId )
    {
        this.qqAppId = qqAppId;
    }

    public String getQqAppKey()
    {
        return qqAppKey;
    }

    public void setQqAppKey( String qqAppKey )
    {
        this.qqAppKey = qqAppKey;
    }

    public String getQqBackUri()
    {
        return qqBackUri;
    }

    public void setQqBackUri( String qqBackUri )
    {
        this.qqBackUri = qqBackUri;
    }

    public String getResetPwBackUri()
    {
        return resetPwBackUri;
    }

    public void setResetPwBackUri( String resetPwBackUri )
    {
        this.resetPwBackUri = resetPwBackUri;
    }

    public String getWbAppId()
    {
        return wbAppId;
    }

    public void setWbAppId( String wbAppId )
    {
        this.wbAppId = wbAppId;
    }

    public String getWbAppKey()
    {
        return wbAppKey;
    }

    public void setWbAppKey( String wbAppKey )
    {
        this.wbAppKey = wbAppKey;
    }

    public String getWbBackUri()
    {
        return wbBackUri;
    }

    public void setWbBackUri( String wbBackUri )
    {
        this.wbBackUri = wbBackUri;
    }

    public String getRegMailText()
    {
        return regMailText;
    }

    public void setRegMailText( String regMailText )
    {
        this.regMailText = regMailText;
    }

    public String getResetPwText()
    {
        return resetPwText;
    }

    public void setResetPwText( String resetPwText )
    {
        this.resetPwText = resetPwText;
    }

    public Long getMemberDefRoleId()
    {
        return memberDefRoleId;
    }

    public Integer getMemberDefLv()
    {
        return memberDefLv;
    }

    public void setMemberDefLv( Integer memberDefLv )
    {
        this.memberDefLv = memberDefLv;
    }

    public void setMemberDefRoleId( Long memberDefRoleId )
    {
        this.memberDefRoleId = memberDefRoleId;
    }

    public Long getMemberDefSc()
    {
        return memberDefSc;
    }

    public void setMemberDefSc( Long memberDefSc )
    {
        this.memberDefSc = memberDefSc;
    }

    public String getRelateMemberUri()
    {
        return relateMemberUri;
    }

    public void setRelateMemberUri( String relateMemberUri )
    {
        this.relateMemberUri = relateMemberUri;
    }

    public String getThirdLoginErrorUri()
    {
        return thirdLoginErrorUri;
    }

    public void setThirdLoginErrorUri( String thirdLoginErrorUri )
    {
        this.thirdLoginErrorUri = thirdLoginErrorUri;
    }

    public String getThirdLoginSuccessUri()
    {
        return thirdLoginSuccessUri;
    }

    public void setThirdLoginSuccessUri( String thirdLoginSuccessUri )
    {
        this.thirdLoginSuccessUri = thirdLoginSuccessUri;
    }

    public Integer getMemberExpire()
    {
        return memberExpire;
    }

    public void setMemberExpire( Integer memberExpire )
    {
        this.memberExpire = memberExpire;
    }

    public Integer getMobJump()
    {
        return mobJump;
    }

    public void setMobJump( Integer mobJump )
    {
        this.mobJump = mobJump;
    }

    public Long getMobSiteId()
    {
        return mobSiteId;
    }

    public void setMobSiteId( Long mobSiteId )
    {
        this.mobSiteId = mobSiteId;
    }

    public Integer getMemberLoginOnce()
    {
        return memberLoginOnce;
    }

    public void setMemberLoginOnce( Integer memberLoginOnce )
    {
        this.memberLoginOnce = memberLoginOnce;
    }

    // 业务方法

    public String getSiteFilePrefixUrlDMode()
    {
        return siteFilePrefixUrlDMode;
    }

    public void setSiteFilePrefixUrlDMode( String siteFilePrefixUrlDMode )
    {
        this.siteFilePrefixUrlDMode = siteFilePrefixUrlDMode;
    }

    public String getSiteMediaPrefixUrlDMode()
    {
        return siteMediaPrefixUrlDMode;
    }

    public void setSiteMediaPrefixUrlDMode( String siteMediaPrefixUrlDMode )
    {
        this.siteMediaPrefixUrlDMode = siteMediaPrefixUrlDMode;
    }

    public void setSiteImagePrefixUrlDMode( String siteImagePrefixUrlDMode )
    {
        this.siteImagePrefixUrlDMode = siteImagePrefixUrlDMode;
    }

    public String getMobHomePageStaticUrl()
    {
        return mobHomePageStaticUrl;
    }

    public void setMobHomePageStaticUrl( String mobHomePageStaticUrl )
    {
        this.mobHomePageStaticUrl = mobHomePageStaticUrl;
    }

    public String getPadHomePageStaticUrl()
    {
        return padHomePageStaticUrl;
    }

    public void setPadHomePageStaticUrl( String padHomePageStaticUrl )
    {
        this.padHomePageStaticUrl = padHomePageStaticUrl;
    }

    public Boolean isMob( String url )
    {
        return url.startsWith( mobSiteUrl ) ? true : false;
    }

    public Boolean isPad( String url )
    {
        return url.startsWith( padSiteUrl ) ? true : false;
    }

    public Boolean isPc( String url )
    {
        return url.startsWith( siteUrl ) ? true : false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.mjsoft.cms.site.bean.SiteNode#getSiteIndexUrl()
     */
    public String getSiteIndexUrl()
    {
        if( this.siteIndexUrl == null )
        {
            // 子站是主域名+二级子目录

            if( this.siteUrl.lastIndexOf( "/" + this.siteRoot ) != -1 )
            {
                notHost = true;
            }

            if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.homePageProduceType ) )
            {
                if( notHost )
                {
                    this.siteIndexUrl = this.siteUrl + this.publishRoot + Constant.CONTENT.URL_SEP
                        + this.homePageStaticUrl;
                }
                else
                {
                    this.siteIndexUrl = this.siteUrl + this.siteRoot + Constant.CONTENT.URL_SEP
                        + this.publishRoot + Constant.CONTENT.URL_SEP + this.homePageStaticUrl;
                }
            }
            else
            {
                if( notHost )
                {
                    // TODO 站点名暂用
                    this.siteIndexUrl = this.siteUrl + Constant.CONTENT.TEMPLATE_BASE
                        + Constant.CONTENT.URL_SEP + this.homePageTemplate;
                }
                else
                {
                    // TODO 站点名暂用
                    this.siteIndexUrl = this.siteUrl + this.siteRoot + Constant.CONTENT.URL_SEP
                        + Constant.CONTENT.TEMPLATE_BASE + Constant.CONTENT.URL_SEP
                        + this.homePageTemplate;
                }
            }

        }

        return this.siteIndexUrl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.mjsoft.cms.site.bean.SiteNode#getSiteIndexUri()
     */
    public String getSiteIndexUri()
    {
        if( this.siteIndexUri == null )
        {
            // 子站是主域名+二级子目录

            if( this.siteUrl.lastIndexOf( "/" + this.siteRoot + "/" ) != -1 )
            {
                notHost = true;
            }

            if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.homePageProduceType ) )
            {
                if( notHost )
                {
                    this.siteIndexUri = this.publishRoot + Constant.CONTENT.URL_SEP
                        + this.homePageStaticUrl;
                }
                else
                {
                    this.siteIndexUri = this.siteRoot + Constant.CONTENT.URL_SEP + this.publishRoot
                        + Constant.CONTENT.URL_SEP + this.homePageStaticUrl;
                }
            }
            else
            {
                if( notHost )
                {
                    // TODO 站点名暂用
                    this.siteIndexUri = Constant.CONTENT.TEMPLATE_BASE + Constant.CONTENT.URL_SEP
                        + this.homePageTemplate;
                }
                else
                {
                    // TODO 站点名暂用
                    this.siteIndexUri = this.siteRoot + Constant.CONTENT.URL_SEP
                        + Constant.CONTENT.TEMPLATE_BASE + Constant.CONTENT.URL_SEP
                        + this.homePageTemplate;
                }
            }

        }

        return this.siteIndexUri;

    }

    public String getMobSiteIndexUri()
    {
        if( this.mobSiteIndexUri == null )
        {
            // 子站是主域名+二级子目录

            if( this.mobSiteUrl.lastIndexOf( "/" + this.siteRoot + "/" ) != -1 )
            {
                notHost = true;
            }

            if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.homePageProduceType ) )
            {
                // if( notHost )
                // {
                // this.mobSiteIndexUri = Constant.CONTENT.URL_SEP +
                // this.siteRoot
                // + Constant.CONTENT.URL_SEP + this.publishRoot
                // + Constant.CONTENT.URL_SEP + this.mobHomePageStaticUrl;
                // }
                // else
                {
                    this.mobSiteIndexUri = Constant.CONTENT.URL_SEP + this.siteRoot
                        + Constant.CONTENT.URL_SEP + this.publishRoot + Constant.CONTENT.URL_SEP
                        + this.mobHomePageStaticUrl;
                }
            }
            else
            {
                if( notHost )
                {
                    // 移动站首页相对跟目录
                    this.mobSiteIndexUri = Constant.CONTENT.URL_SEP + this.siteRoot
                        + Constant.CONTENT.URL_SEP + Constant.CONTENT.TEMPLATE_BASE
                        + Constant.CONTENT.URL_SEP + this.mobHomePageTemplate;
                }
                else
                {
                    // 移动站首页相对跟目录
                    this.mobSiteIndexUri = Constant.CONTENT.URL_SEP + this.siteRoot
                        + Constant.CONTENT.URL_SEP + Constant.CONTENT.TEMPLATE_BASE
                        + Constant.CONTENT.URL_SEP + this.mobHomePageTemplate;
                }
            }

        }

        return this.mobSiteIndexUri;

    }

    public String getPadSiteIndexUri()
    {
        if( this.padSiteIndexUri == null )
        {
            // 子站是主域名+二级子目录

            if( this.padSiteUrl.lastIndexOf( "/" + this.siteRoot + "/" ) != -1 )
            {
                notHost = true;
            }

            if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( this.homePageProduceType ) )
            {
                // if( notHost )
                // {
                // this.padSiteIndexUri = Constant.CONTENT.URL_SEP +
                // this.siteRoot
                // + Constant.CONTENT.URL_SEP + this.publishRoot
                // + Constant.CONTENT.URL_SEP + this.padHomePageStaticUrl;
                // }
                // else
                {
                    this.padSiteIndexUri = Constant.CONTENT.URL_SEP + this.siteRoot
                        + Constant.CONTENT.URL_SEP + this.publishRoot + Constant.CONTENT.URL_SEP
                        + this.padHomePageStaticUrl;
                }
            }
            else
            {
                if( notHost )
                {
                    // TODO 站点名暂用
                    this.padSiteIndexUri = Constant.CONTENT.TEMPLATE_BASE
                        + Constant.CONTENT.URL_SEP + this.padHomePageTemplate;
                }
                else
                {
                    // TODO 站点名暂用
                    this.padSiteIndexUri = this.siteRoot + Constant.CONTENT.URL_SEP
                        + Constant.CONTENT.TEMPLATE_BASE + Constant.CONTENT.URL_SEP
                        + this.padHomePageTemplate;
                }
            }

        }

        return this.padSiteIndexUri;

    }

    public String getHostMainUrl()
    {
        // 子站是主域名+二级子目录
        if( hostMainUrl == null )
        {
            if( this.siteUrl.lastIndexOf( "/" + this.siteRoot + "/" ) != -1 )
            {
                notHost = true;
            }

            if( notHost )
            {
                hostMainUrl = StringUtil.subString( siteUrl, 0, siteUrl.lastIndexOf( this.siteRoot
                    + "/" ) );
            }
            else
            {
                hostMainUrl = siteUrl;
            }
        }

        return hostMainUrl;
    }

    public String getSitePublishPrefixUrl()
    {
        if( this.sitePublishPrefixUrl == null )
        {

            // 子站是主域名+二级子目录

            if( this.siteUrl.lastIndexOf( "/" + this.siteRoot + "/" ) != -1 )
            {
                notHost = true;
            }

            if( notHost )
            {
                this.sitePublishPrefixUrl = this.siteUrl + this.publishRoot;
                // + Constant.CONTENT.URL_SEP;
            }
            else
            {
                this.sitePublishPrefixUrl = this.siteUrl + this.siteRoot + Constant.CONTENT.URL_SEP
                    + this.publishRoot;
                // + Constant.CONTENT.URL_SEP;
            }
        }

        return this.sitePublishPrefixUrl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.mjsoft.cms.site.bean.SiteNode#getSiteImagePrefixUrl()
     */
    public String getSiteImagePrefixUrl()
    {
        if( this.siteImagePrefixUrl == null )
        {
            Properties sysPro = SystemConfiguration.getInstance().getSystemConfig().getSysPro();

            String cloEndPoint = resService.getCloudEndPoint( this.siteId,
                Constant.RESOURCE.IMAGE_RES_TYPE );

            if( "true".equals( sysPro.getProperty( "res_relate_path" ) ) )
            {
                JTopServer server = ( JTopServer ) InterceptFilter.serverCache.get( "SERVER" );

                String context = StringUtil.isStringNotNull( server.getContext() )
                    ? Constant.CONTENT.URL_SEP + server.getContext() : "";

                this.siteImagePrefixUrl = context + Constant.CONTENT.URL_SEP + this.siteRoot
                    + Constant.CONTENT.URL_SEP + this.imageRoot + Constant.CONTENT.URL_SEP;
            }
            else if( !cloEndPoint.startsWith( "no_" ) )
            {
                this.siteImagePrefixUrl = cloEndPoint + this.siteRoot + Constant.CONTENT.URL_SEP
                    + this.imageRoot + Constant.CONTENT.URL_SEP;
            }
            else
            {
                // 子站是主域名+二级子目录

                if( this.siteUrl.lastIndexOf( "/" + this.siteRoot + "/" ) != -1 )
                {
                    notHost = true;
                }

                if( notHost )
                {
                    if( imageServerUrl == null )
                    {
                        this.siteImagePrefixUrl = this.siteUrl + this.imageRoot
                            + Constant.CONTENT.URL_SEP;
                    }
                    else
                    {
                        this.siteImagePrefixUrl = imageServerUrl + this.siteRoot
                            + Constant.CONTENT.URL_SEP + this.imageRoot + Constant.CONTENT.URL_SEP;
                    }
                }
                else
                {

                    if( imageServerUrl == null )
                    {
                        this.siteImagePrefixUrl = this.siteUrl + this.siteRoot
                            + Constant.CONTENT.URL_SEP + this.imageRoot + Constant.CONTENT.URL_SEP;
                        // this.siteImagePrefixUrl = this.siteUrl +
                        // this.imageRoot
                        // + Constant.CONTENT.URL_SEP;
                    }
                    else
                    {
                        this.siteImagePrefixUrl = imageServerUrl + this.siteRoot
                            + Constant.CONTENT.URL_SEP + this.imageRoot + Constant.CONTENT.URL_SEP;
                    }

                }
            }

        }

        return this.siteImagePrefixUrl;
    }

    public String getSiteImagePrefixUrlDMode()
    {
        if( this.siteImagePrefixUrlDMode == null )
        {

            String cloEndPoint = resService.getCloudEndPoint( this.siteId,
                Constant.RESOURCE.IMAGE_RES_TYPE );

            if( !cloEndPoint.startsWith( "no_" ) )
            {
                this.siteImagePrefixUrlDMode = cloEndPoint + this.siteRoot
                    + Constant.CONTENT.URL_SEP + this.imageRoot + Constant.CONTENT.URL_SEP;
            }
            else
            {
                // 子站是主域名+二级子目录

                if( this.siteUrl.lastIndexOf( "/" + this.siteRoot + "/" ) != -1 )
                {
                    notHost = true;
                }

                if( notHost )
                {
                    if( imageServerUrl == null )
                    {
                        this.siteImagePrefixUrlDMode = this.siteUrl + this.imageRoot
                            + Constant.CONTENT.URL_SEP;
                    }
                    else
                    {
                        this.siteImagePrefixUrlDMode = imageServerUrl + this.siteRoot
                            + Constant.CONTENT.URL_SEP + this.imageRoot + Constant.CONTENT.URL_SEP;
                    }
                }
                else
                {

                    if( imageServerUrl == null )
                    {
                        this.siteImagePrefixUrlDMode = this.siteUrl + this.siteRoot
                            + Constant.CONTENT.URL_SEP + this.imageRoot + Constant.CONTENT.URL_SEP;
                        // this.siteImagePrefixUrl = this.siteUrl +
                        // this.imageRoot
                        // + Constant.CONTENT.URL_SEP;
                    }
                    else
                    {
                        this.siteImagePrefixUrlDMode = imageServerUrl + this.siteRoot
                            + Constant.CONTENT.URL_SEP + this.imageRoot + Constant.CONTENT.URL_SEP;
                    }

                }
            }

        }

        return this.siteImagePrefixUrlDMode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.mjsoft.cms.site.bean.SiteNode#getSiteMediaPrefixUrl()
     */
    public String getSiteMediaPrefixUrl()
    {
        if( this.siteMediaPrefixUrl == null )
        {
            Properties sysPro = SystemConfiguration.getInstance().getSystemConfig().getSysPro();

            String cloEndPoint = resService.getCloudEndPoint( this.siteId,
                Constant.RESOURCE.VIDEO_RES_TYPE );

            if( "true".equals( sysPro.getProperty( "res_relate_path" ) ) )
            {
                JTopServer server = ( JTopServer ) InterceptFilter.serverCache.get( "SERVER" );

                String context = StringUtil.isStringNotNull( server.getContext() )
                    ? Constant.CONTENT.URL_SEP + server.getContext() : "";

                this.siteMediaPrefixUrl = context + Constant.CONTENT.URL_SEP + this.siteRoot
                    + Constant.CONTENT.URL_SEP + this.mediaRoot + Constant.CONTENT.URL_SEP;
            }
            else if( !cloEndPoint.startsWith( "no_" ) )
            {
                this.siteMediaPrefixUrl = cloEndPoint + this.siteRoot + Constant.CONTENT.URL_SEP
                    + this.mediaRoot + Constant.CONTENT.URL_SEP;
                ;
            }
            else
            {
                // 子站是主域名+二级子目录

                if( this.siteUrl.lastIndexOf( "/" + this.siteRoot + "/" ) != -1 )
                {
                    notHost = true;
                }

                if( notHost )
                {
                    if( this.mediaServerUrl == null )
                    {
                        this.siteMediaPrefixUrl = this.siteUrl + this.mediaRoot
                            + Constant.CONTENT.URL_SEP;
                    }
                    else
                    {
                        this.siteMediaPrefixUrl = this.mediaServerUrl + this.siteRoot
                            + Constant.CONTENT.URL_SEP + this.mediaRoot + Constant.CONTENT.URL_SEP;
                    }
                }
                else
                {
                    if( this.mediaServerUrl == null )
                    {
                        this.siteMediaPrefixUrl = this.siteUrl + this.siteRoot
                            + Constant.CONTENT.URL_SEP + this.mediaRoot + Constant.CONTENT.URL_SEP;
                        // this.siteMediaPrefixUrl = this.siteUrl +
                        // this.mediaRoot
                        // + Constant.CONTENT.URL_SEP;
                    }
                    else
                    {
                        this.siteMediaPrefixUrl = this.mediaServerUrl + this.siteRoot
                            + Constant.CONTENT.URL_SEP + this.mediaRoot + Constant.CONTENT.URL_SEP;
                    }

                }
            }
        }

        return this.siteMediaPrefixUrl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.mjsoft.cms.site.bean.SiteNode#getSiteFilePrefixUrl()
     */
    public String getSiteFilePrefixUrl()
    {
        if( this.siteFilePrefixUrl == null )
        {
            Properties sysPro = SystemConfiguration.getInstance().getSystemConfig().getSysPro();

            String cloEndPoint = resService.getCloudEndPoint( this.siteId,
                Constant.RESOURCE.ANY_RES_TYPE );

            if( "true".equals( sysPro.getProperty( "res_relate_path" ) ) )
            {
                JTopServer server = ( JTopServer ) InterceptFilter.serverCache.get( "SERVER" );

                String context = StringUtil.isStringNotNull( server.getContext() )
                    ? Constant.CONTENT.URL_SEP + server.getContext() : "";

                this.siteFilePrefixUrl = context + Constant.CONTENT.URL_SEP + this.siteRoot
                    + Constant.CONTENT.URL_SEP + this.fileRoot + Constant.CONTENT.URL_SEP;
            }
            else if( !cloEndPoint.startsWith( "no_" ) )
            {
                this.siteFilePrefixUrl = cloEndPoint + this.siteRoot + Constant.CONTENT.URL_SEP
                    + this.fileRoot + Constant.CONTENT.URL_SEP;
            }
            else
            {
                // 子站是主域名+二级子目录

                if( this.siteUrl.lastIndexOf( "/" + this.siteRoot + "/" ) != -1 )
                {
                    notHost = true;
                }

                if( notHost )
                {
                    if( this.fileServerUrl == null )
                    {
                        this.siteFilePrefixUrl = this.siteUrl + this.fileRoot
                            + Constant.CONTENT.URL_SEP;
                    }
                    else
                    {
                        this.siteFilePrefixUrl = this.fileServerUrl + this.siteRoot
                            + Constant.CONTENT.URL_SEP + this.fileRoot + Constant.CONTENT.URL_SEP;
                    }
                }
                else
                {
                    if( this.fileServerUrl == null )
                    {
                        this.siteFilePrefixUrl = this.siteUrl + this.siteRoot
                            + Constant.CONTENT.URL_SEP + this.fileRoot + Constant.CONTENT.URL_SEP;
                    }
                    else
                    {
                        this.siteFilePrefixUrl = this.fileServerUrl + this.siteRoot
                            + Constant.CONTENT.URL_SEP + this.fileRoot + Constant.CONTENT.URL_SEP;
                    }
                }
            }
        }

        return this.siteFilePrefixUrl;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.com.mjsoft.cms.site.bean.SiteNode#getSiteTemplateUrl()
     */
    public String getSiteTemplateUrl()
    {
        if( this.siteTemplateUrl == null )
        {
            // 子站是主域名+二级子目录

            if( this.siteUrl.lastIndexOf( "/" + this.siteRoot + "/" ) != -1 )
            {
                notHost = true;
            }

            if( notHost )
            {
                this.siteTemplateUrl = this.siteUrl + Constant.CONTENT.TEMPLATE_BASE
                    + Constant.CONTENT.URL_SEP;
            }
            else
            {
                this.siteTemplateUrl = this.siteUrl + this.getSiteRoot() + Constant.CONTENT.URL_SEP
                    + Constant.CONTENT.TEMPLATE_BASE + Constant.CONTENT.URL_SEP;
            }
        }

        return this.siteTemplateUrl;
    }

    public boolean isNotHost()
    {
        return notHost;
    }

    public void setNotHost( boolean notHost )
    {
        this.notHost = notHost;
    }

    public void setFileServerUrl( String fileServerUrl )
    {
        this.fileServerUrl = fileServerUrl;
    }

    public void setImageServerUrl( String imageServerUrl )
    {
        this.imageServerUrl = imageServerUrl;
    }

    public void setMediaServerUrl( String mediaServerUrl )
    {
        this.mediaServerUrl = mediaServerUrl;
    }

    public Integer getOffSetX()
    {
        return offSetX;
    }

    public void setOffSetX( Integer offSetX )
    {
        this.offSetX = offSetX;
    }

    public Integer getOffSetY()
    {
        return offSetY;
    }

    public void setOffSetY( Integer offSetY )
    {
        this.offSetY = offSetY;
    }

    public Integer getSameTitle()
    {
        return sameTitle;
    }

    public void setSameTitle( Integer sameTitle )
    {
        this.sameTitle = sameTitle;
    }

    public Integer getFileMaxC()
    {
        return fileMaxC;
    }

    public void setFileMaxC( Integer fileMaxC )
    {
        this.fileMaxC = fileMaxC;
    }

    public Integer getImageMaxC()
    {
        return imageMaxC;
    }

    public void setImageMaxC( Integer imageMaxC )
    {
        this.imageMaxC = imageMaxC;
    }

    public Integer getMediaMaxC()
    {
        return mediaMaxC;
    }

    public void setMediaMaxC( Integer mediaMaxC )
    {
        this.mediaMaxC = mediaMaxC;
    }

    public String getWxAppId()
    {
        return wxAppId;
    }

    public void setWxAppId( String wxAppId )
    {
        this.wxAppId = wxAppId;
    }

    public String getWxAppKey()
    {
        return wxAppKey;
    }

    public void setWxAppKey( String wxAppKey )
    {
        this.wxAppKey = wxAppKey;
    }

    public String getWxPrevUid()
    {
        return wxPrevUid;
    }

    public void setWxPrevUid( String wxPrevUid )
    {
        this.wxPrevUid = wxPrevUid;
    }

    public String getMemberLoginUri()
    {
        return memberLoginUri;
    }

    public void setMemberLoginUri( String memberLoginUri )
    {
        this.memberLoginUri = memberLoginUri;
    }

    public String getImageATVal()
    {
        if( StringUtil.isStringNull( this.imageAllowType ) )
        {
            return "no_any_type";
        }

        List ts = StringUtil.changeStringToList( this.imageAllowType, "," );

        StringBuffer buf = new StringBuffer();

        for ( int i = 0; i < ts.size(); i++ )
        {
            if( StringUtil.isStringNull( ( String ) ts.get( i ) ) )
            {
                continue;
            }

            if( i + 1 != ts.size() )
            {
                buf.append( "*." + ts.get( i ) + ";" );
            }
            else
            {
                buf.append( "*." + ts.get( i ) );
            }
        }

        return buf.toString();
    }

    public String getMediaATVal()
    {
        if( StringUtil.isStringNull( this.mediaAllowType ) )
        {
            return "no_any_type";
        }

        List ts = StringUtil.changeStringToList( this.mediaAllowType, "," );

        StringBuffer buf = new StringBuffer();

        for ( int i = 0; i < ts.size(); i++ )
        {
            if( StringUtil.isStringNull( ( String ) ts.get( i ) ) )
            {
                continue;
            }

            if( i + 1 != ts.size() )
            {
                buf.append( "*." + ts.get( i ) + ";" );
            }
            else
            {
                buf.append( "*." + ts.get( i ) );
            }
        }

        return buf.toString();
    }

    public String getFileATVal()
    {
        if( StringUtil.isStringNull( this.fileAllowType ) )
        {
            return "no_any_type";
        }

        List ts = StringUtil.changeStringToList( this.fileAllowType, "," );

        StringBuffer buf = new StringBuffer();

        for ( int i = 0; i < ts.size(); i++ )
        {
            if( StringUtil.isStringNull( ( String ) ts.get( i ) ) )
            {
                continue;
            }

            if( i + 1 != ts.size() )
            {
                buf.append( "*." + ts.get( i ) + ";" );
            }
            else
            {
                buf.append( "*." + ts.get( i ) );
            }
        }

        return buf.toString();
    }

    public String getHome301Url()
    {
        return home301Url;
    }

    public void setHome301Url( String home301Url )
    {
        this.home301Url = home301Url;
    }

    public String getMobSiteUrl()
    {
        return mobSiteUrl;
    }

    public void setMobSiteUrl( String mobSiteUrl )
    {
        this.mobSiteUrl = mobSiteUrl;
    }

    public Integer getMobVm()
    {
        return mobVm;
    }

    public void setMobVm( Integer mobVm )
    {
        this.mobVm = mobVm;
    }

    public String getPadSiteUrl()
    {
        return padSiteUrl;
    }

    public void setPadSiteUrl( String padSiteUrl )
    {
        this.padSiteUrl = padSiteUrl;
    }

    public Integer getPadVm()
    {
        return padVm;
    }

    public void setPadVm( Integer padVm )
    {
        this.padVm = padVm;
    }

    public Integer getPcVm()
    {
        return pcVm;
    }

    public void setPcVm( Integer pcVm )
    {
        this.pcVm = pcVm;
    }

    public String getMobHomePageTemplate()
    {
        return mobHomePageTemplate;
    }

    public void setMobHomePageTemplate( String mobHomePageTemplate )
    {
        this.mobHomePageTemplate = mobHomePageTemplate;
    }

    public String getPadHomePageTemplate()
    {
        return padHomePageTemplate;
    }

    public void setPadHomePageTemplate( String padHomePageTemplate )
    {
        this.padHomePageTemplate = padHomePageTemplate;
    }

    public Integer getOrderFlag()
    {
        return orderFlag;
    }

    public void setOrderFlag( Integer orderFlag )
    {
        this.orderFlag = orderFlag;
    }

    public void resetUrlCache()
    {
        this.siteIndexUri = null;
        this.siteIndexUrl = null;
        this.sitePublishPrefixUrl = null;
        this.siteImagePrefixUrl = null;
        this.siteMediaPrefixUrl = null;
        this.siteFilePrefixUrl = null;
    }

}
