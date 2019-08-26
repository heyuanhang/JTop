package cn.com.mjsoft.cms.site.dao.vo;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "site_group", id = "siteId", idMode = EntitySqlBridge.DB_IDENTITY )
public class SiteGroup
{
    private Long siteId;
    private String siteFlag;
    private String siteRoot;
    private String siteName;
    private String siteUrl = "";
    private String home301Url = "";
    private String mobSiteUrl = "";

    private Integer orderFlag = 0;

    private String padSiteUrl = "";

    private Integer pcVm = 1;

    private Integer mobVm = 0;

    private Integer padVm = 0;

    private String homePageTemplate = "index.jsp";
    private String mobHomePageTemplate;
    private String padHomePageTemplate;

    private String siteDesc = "";
    private Integer homePageProduceType = Integer.valueOf( 1 );
    private String homePageStaticUrl;
    private String imageRoot = Constant.CONTENT.IMG_BASE;
    private String mediaRoot = Constant.CONTENT.MEDIA_BASE;
    private String fileRoot = Constant.CONTENT.FILE_BASE;
    private String publishRoot = Constant.CONTENT.HTML_BASE;

    private String templateCharset = Constant.SITE_CHANNEL.DEF_PAGE_CODE;

    private String logoImage;
    private String copyright;
    private String icp;
    private Integer staticFileType = Constant.COMMON.ON;
    private String seoTitle = "";
    private String seoKeyword = "";
    private String seoDesc = "";
    private Integer shareMode = Constant.COMMON.ON;
    private Integer downOutImage = Constant.COMMON.ON;
    private Integer deleteOutLink = Constant.COMMON.OFF;
    private Integer summaryLength = Integer.valueOf( 220 );
    private Integer defClickCount = Integer.valueOf( 100 );
    private String managerIP;
    private Integer siteCollType = Constant.COMMON.ON;
    private String outSiteCollUrl;
    private String sendMailHost;
    private Integer sameTitle = Constant.COMMON.OFF;
    private Integer genKw = Constant.COMMON.ON;
    private String mail;
    private String mailUserName;
    private String mailUserPW;

    private String smsApiUrl;

    private String smsAccount;

    private String smsPW;

    private Integer smsSendOnceSec = Integer.valueOf( 1 );// 默认一分钟一条

    private Integer smsMaxCount = Integer.valueOf( 50 );// 默认最大50条

    private Integer smsIpDayCount = Integer.valueOf( 10 );// 默认同一IP每天只能发10条

    private Integer mailSSL = Constant.COMMON.OFF;// 默认非SSL模式
    private Integer managerLoginTime = Integer.valueOf( 40 );
    private Integer searchFun = Constant.COMMON.ON;
    private Integer useFW = Constant.COMMON.ON;
    private String imageAllowType = Constant.RESOURCE.IMAGE_RULE_VAL;
    private String mediaAllowType = Constant.RESOURCE.VIDEO_RULE_VAL;
    private String fileAllowType = Constant.RESOURCE.FILE_RULE_VAL;

    private Integer imageMaxC = Integer.valueOf( 5 );
    private Integer mediaMaxC = Integer.valueOf( 100 );
    private Integer fileMaxC = Integer.valueOf( 60 );

    private Integer useImageMark = Constant.COMMON.ON;
    private Integer imageMarkType = Constant.COMMON.ON;
    private String imageMarkPos = "southeast";
    private String imageMarkChar;
    private String imageMark;
    private Integer imageMarkDis = Integer.valueOf( 100 );
    private Integer offSetX = Integer.valueOf( 0 );;
    private Integer offSetY = Integer.valueOf( 0 );;
    private Integer defEditorImageW;
    private Integer defEditorImageH;
    private Integer defEditorImageDM = Constant.COMMON.OFF;
    private Integer defHomeImageW;
    private Integer defHomeImageH;
    private Integer defHomeImageDM = Constant.COMMON.OFF;
    private Integer defClassImageW;
    private Integer defClassImageH;
    private Integer defClassImageDM = Constant.COMMON.OFF;
    private Integer defListImageW;
    private Integer defListImageH;
    private Integer defListImageDM = Constant.COMMON.OFF;
    private Integer defContentImageW;
    private Integer defContentImageH;
    private Integer defContentImageDM = Constant.COMMON.OFF;

    private Integer allowMemberReg = Constant.COMMON.ON;// 默认允许会员注册
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

    private Long memberDefRoleId = Long.valueOf( 1 );

    private Integer memberDefLv = Integer.valueOf( -1 );

    private Long memberDefSc = Long.valueOf( 0 );

    private Integer memberExpire = Integer.valueOf( 60 );

    private Integer useState = Constant.COMMON.ON;

    private Long extDataModelId = -1l;

    private Long extMemberModelId = -1l;

    private Integer mobJump = Constant.COMMON.OFF;// 默认非跳转站模式

    private Long mobSiteId = Long.valueOf( -1 );

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

    public String getSiteUrl()
    {
        return siteUrl;
    }

    public void setSiteUrl( String siteUrl )
    {
        this.siteUrl = siteUrl;
    }

    public String getHomePageStaticUrl()
    {
        return homePageStaticUrl;
    }

    public void setHomePageStaticUrl( String homePageStaticUrl )
    {
        this.homePageStaticUrl = homePageStaticUrl;
    }

    public Integer getHomePageProduceType()
    {
        return homePageProduceType;
    }

    public void setHomePageProduceType( Integer homePageProduceType )
    {
        this.homePageProduceType = homePageProduceType;
    }

    public String getPublishRoot()
    {
        return publishRoot;
    }

    public void setPublishRoot( String publishRoot )
    {
        this.publishRoot = publishRoot;
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

    public String getSiteRoot()
    {
        return siteRoot;
    }

    public void setSiteRoot( String siteRoot )
    {
        this.siteRoot = siteRoot;
    }

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

    public Integer getSearchFun()
    {
        return searchFun;
    }

    public void setSearchFun( Integer searchFun )
    {
        this.searchFun = searchFun;
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
        return imageMark;
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
        return logoImage;
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

    public Integer getUseImageMark()
    {
        return useImageMark;
    }

    public void setUseImageMark( Integer useImageMark )
    {
        this.useImageMark = useImageMark;
    }

    public Integer getUseState()
    {
        return useState;
    }

    public void setUseState( Integer useState )
    {
        this.useState = useState;
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

    public Integer getImageMarkDis()
    {
        return imageMarkDis;
    }

    public void setImageMarkDis( Integer imageMarkDis )
    {
        this.imageMarkDis = imageMarkDis;
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

    public Integer getMemberDefLv()
    {
        return memberDefLv;
    }

    public void setMemberDefLv( Integer memberDefLv )
    {
        this.memberDefLv = memberDefLv;
    }

    public Long getMemberDefRoleId()
    {
        return memberDefRoleId;
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

    public String getMemberLoginUri()
    {
        return memberLoginUri;
    }

    public void setMemberLoginUri( String memberLoginUri )
    {
        this.memberLoginUri = memberLoginUri;
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

    public Integer getMemberLoginOnce()
    {
        return memberLoginOnce;
    }

    public void setMemberLoginOnce( Integer memberLoginOnce )
    {
        this.memberLoginOnce = memberLoginOnce;
    }

}
