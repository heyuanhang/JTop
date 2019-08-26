package cn.com.mjsoft.cms.member.bean;

import java.io.Serializable;

public class MemberBean implements Serializable
{
    private static final long serialVersionUID = -8850080423746304707L;
    private Long memberId;
    private String memberName;
    private String email;
    private String headPhoto;
    private Integer isTrueEmail;
    private String password;
    private Integer isTruePass;
    private String phoneNumber;
    private Integer isTruePhone;
    private String regDt;
    private String trueName;
    private Integer certType;
    private String certCode;
    private String certPhotoP;
    private String certPhotoR;
    private Integer isTrueMan;
    private String prevLoginIp;
    private String prevLoginArea;
    private String prevLoginDt;
    private String currLoginIp;
    private String currLoginDt;
    private Long siteId;
    private Integer memLevel;
    private Long score;
    private Integer useStatus;
    private Integer isFirst;
    private Long loginSuccessCount;

    public Long getMemberId()
    {
        return this.memberId;
    }

    public void setMemberId( Long memberId )
    {
        this.memberId = memberId;
    }

    public String getMemberName()
    {
        return this.memberName;
    }

    public void setMemberName( String memberName )
    {
        this.memberName = memberName;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public String getHeadPhoto()
    {
        return this.headPhoto;
    }

    public void setHeadPhoto( String headPhoto )
    {
        this.headPhoto = headPhoto;
    }

    public String getCertCode()
    {
        return certCode;
    }

    public void setCertCode( String certCode )
    {
        this.certCode = certCode;
    }

    public String getCertPhotoP()
    {
        return certPhotoP;
    }

    public void setCertPhotoP( String certPhotoP )
    {
        this.certPhotoP = certPhotoP;
    }

    public String getCertPhotoR()
    {
        return certPhotoR;
    }

    public void setCertPhotoR( String certPhotoR )
    {
        this.certPhotoR = certPhotoR;
    }

    public Integer getCertType()
    {
        return certType;
    }

    public void setCertType( Integer certType )
    {
        this.certType = certType;
    }

    public String getCurrLoginIp()
    {
        return currLoginIp;
    }

    public void setCurrLoginIp( String currLoginIp )
    {
        this.currLoginIp = currLoginIp;
    }

    public Integer getIsFirst()
    {
        return isFirst;
    }

    public void setIsFirst( Integer isFirst )
    {
        this.isFirst = isFirst;
    }

    public Integer getIsTrueEmail()
    {
        return isTrueEmail;
    }

    public void setIsTrueEmail( Integer isTrueEmail )
    {
        this.isTrueEmail = isTrueEmail;
    }

    public Integer getIsTrueMan()
    {
        return isTrueMan;
    }

    public void setIsTrueMan( Integer isTrueMan )
    {
        this.isTrueMan = isTrueMan;
    }

    public Integer getIsTruePass()
    {
        return isTruePass;
    }

    public void setIsTruePass( Integer isTruePass )
    {
        this.isTruePass = isTruePass;
    }

    public Integer getIsTruePhone()
    {
        return isTruePhone;
    }

    public void setIsTruePhone( Integer isTruePhone )
    {
        this.isTruePhone = isTruePhone;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber( String phoneNumber )
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPrevLoginArea()
    {
        return prevLoginArea;
    }

    public void setPrevLoginArea( String prevLoginArea )
    {
        this.prevLoginArea = prevLoginArea;
    }

    public void setPrevLoginIp( String prevLoginIp )
    {
        this.prevLoginIp = prevLoginIp;
    }

    public String getPrevLoginIp()
    {
        return prevLoginIp;
    }

    public String getCurrLoginDt()
    {
        return currLoginDt;
    }

    public void setCurrLoginDt( String currLoginDt )
    {
        this.currLoginDt = currLoginDt;
    }

    public String getPrevLoginDt()
    {
        return prevLoginDt;
    }

    public void setPrevLoginDt( String prevLoginDt )
    {
        this.prevLoginDt = prevLoginDt;
    }

    public String getRegDt()
    {
        return regDt;
    }

    public void setRegDt( String regDt )
    {
        this.regDt = regDt;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public String getTrueName()
    {
        return trueName;
    }

    public void setTrueName( String trueName )
    {
        this.trueName = trueName;
    }

    public Integer getMemLevel()
    {
        return memLevel;
    }

    public void setMemLevel( Integer memLevel )
    {
        this.memLevel = memLevel;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public Long getScore()
    {
        return score;
    }

    public void setScore( Long score )
    {
        this.score = score;
    }

    public Integer getUseStatus()
    {
        return useStatus;
    }

    public void setUseStatus( Integer useStatus )
    {
        this.useStatus = useStatus;
    }

    public Long getLoginSuccessCount()
    {
        return loginSuccessCount;
    }

    public void setLoginSuccessCount( Long loginSuccessCount )
    {
        this.loginSuccessCount = loginSuccessCount;
    }

}
