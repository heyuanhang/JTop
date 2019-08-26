package cn.com.mjsoft.cms.guestbook.bean;

import java.util.Date;

import cn.com.mjsoft.cms.common.Constant;

public class GuestbookMainInfoBean
{
    private Long gbId = Long.valueOf( -1 );
    private Long configId = Long.valueOf( -1 );
    private Integer isReply = Constant.COMMON.OFF;
    private Integer isCensor = Constant.COMMON.OFF;
    private Integer isOpen = Constant.COMMON.OFF;
    private String gbTitle;
    private String gbMan;
    private String gbText;
    private String gbEmail;
    private String replyMan;
    private String replyText;
    private Date replyDate;
    private String ip;
    private Long memberId;
    private Date addDate;
    private Long siteId;

    public Long getGbId()
    {
        return this.gbId;
    }

    public void setGbId( Long gbId )
    {
        this.gbId = gbId;
    }

    public Long getConfigId()
    {
        return this.configId;
    }

    public void setConfigId( Long configId )
    {
        this.configId = configId;
    }

    public Integer getIsCensor()
    {
        return isCensor;
    }

    public void setIsCensor( Integer isCensor )
    {
        this.isCensor = isCensor;
    }

    public Integer getIsOpen()
    {
        return isOpen;
    }

    public void setIsOpen( Integer isOpen )
    {
        this.isOpen = isOpen;
    }

    public Integer getIsReply()
    {
        return isReply;
    }

    public void setIsReply( Integer isReply )
    {
        this.isReply = isReply;
    }

    public String getGbTitle()
    {
        return gbTitle;
    }

    public void setGbTitle( String gbTitle )
    {
        this.gbTitle = gbTitle;
    }

    public String getGbMan()
    {
        return this.gbMan;
    }

    public void setGbMan( String gbMan )
    {
        this.gbMan = gbMan;
    }

    public String getGbText()
    {
        return this.gbText;
    }

    public void setGbText( String gbText )
    {
        this.gbText = gbText;
    }

    public String getGbEmail()
    {
        return this.gbEmail;
    }

    public void setGbEmail( String gbEmail )
    {
        this.gbEmail = gbEmail;
    }

    public Date getReplyDate()
    {
        return replyDate;
    }

    public void setReplyDate( Date replyDate )
    {
        this.replyDate = replyDate;
    }

    public String getReplyMan()
    {
        return replyMan;
    }

    public void setReplyMan( String replyMan )
    {
        this.replyMan = replyMan;
    }

    public String getReplyText()
    {
        return replyText;
    }

    public void setReplyText( String replyText )
    {
        this.replyText = replyText;
    }

    public String getIp()
    {
        return this.ip;
    }

    public void setIp( String ip )
    {
        this.ip = ip;
    }

    public Date getAddDate()
    {
        return this.addDate;
    }

    public void setAddDate( Date addDate )
    {
        this.addDate = addDate;
    }

    public Long getSiteId()
    {
        return siteId;
    }

    public void setSiteId( Long siteId )
    {
        this.siteId = siteId;
    }

    public Long getMemberId()
    {
        return memberId;
    }

    public void setMemberId( Long memberId )
    {
        this.memberId = memberId;
    }

}
