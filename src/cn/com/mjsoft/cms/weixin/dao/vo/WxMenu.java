package cn.com.mjsoft.cms.weixin.dao.vo;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.persistence.core.annotation.Table;
import cn.com.mjsoft.framework.persistence.core.support.EntitySqlBridge;

@Table( name = "wx_menu", id = "btId", idMode = EntitySqlBridge.DB_IDENTITY )
public class WxMenu
{

    private Long btId;
    private Long parentId;
    private Long mtId;
    private Long resId;
    private Integer btLayer;
    private Integer btOrder;
    private String btName;
    private String btType;
    private String btKey;
    private String mediaId;
    private String behaviorClass;
    private String siteFlag;
    private String btUrl;
    private String wxCode;
    private Long wxId;

    public Long getBtId()
    {
        return this.btId;
    }

    public void setBtId( Long btId )
    {
        this.btId = btId;
    }

    public Long getParentId()
    {
        return this.parentId;
    }

    public void setParentId( Long parentId )
    {
        this.parentId = parentId;
    }

    public Long getMtId()
    {
        return this.mtId;
    }

    public void setMtId( Long mtId )
    {
        this.mtId = mtId;
    }

    public Integer getBtLayer()
    {
        return this.btLayer;
    }

    public void setBtLayer( Integer btLayer )
    {
        this.btLayer = btLayer;
    }

    public Integer getBtOrder()
    {
        return this.btOrder;
    }

    public void setBtOrder( Integer btOrder )
    {
        this.btOrder = btOrder;
    }

    public String getBtName()
    {
        return this.btName;
    }

    public void setBtName( String btName )
    {
        this.btName = btName;
    }

    public String getBtType()
    {
        return this.btType;
    }

    public void setBtType( String btType )
    {
        this.btType = btType;
    }

    public String getBtKey()
    {
        return this.btKey;
    }

    public void setBtKey( String btKey )
    {
        this.btKey = btKey;
    }

    public String getMediaId()
    {
        return this.mediaId;
    }

    public void setMediaId( String mediaId )
    {
        this.mediaId = mediaId;
    }

    public String getBtUrl()
    {
        return this.btUrl;
    }

    public void setBtUrl( String btUrl )
    {
        this.btUrl = btUrl;
    }

    // 业务方法

    public String getWxCode()
    {
        return wxCode;
    }

    public void setWxCode( String wxCode )
    {
        this.wxCode = wxCode;
    }

    public String getSiteFlag()
    {
        return siteFlag;
    }

    public void setSiteFlag( String siteFlag )
    {
        this.siteFlag = siteFlag;
    }

    public Long getWxId()
    {
        return wxId;
    }

    public void setWxId( Long wxId )
    {
        this.wxId = wxId;
    }

    public Long getResId()
    {
        return resId;
    }

    public void setResId( Long resId )
    {
        this.resId = resId;
    }

    public String getBehaviorClass()
    {
        return behaviorClass;
    }

    public void setBehaviorClass( String behaviorClass )
    {
        this.behaviorClass = behaviorClass;
    }

    public String getUiName()
    {
        StringBuilder buf = new StringBuilder();
        for ( int i = 0; i < ( btLayer.intValue() - 1 ); i++ )
        {
            buf.append( Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR + Constant.SITE_CHANNEL.HTML_BLANK_CHAR
                + Constant.SITE_CHANNEL.HTML_BLANK_CHAR + Constant.SITE_CHANNEL.HTML_BLANK_CHAR );
        }

        String layerUIClassName = null;
        if( this.btLayer == 2 )
        {
            layerUIClassName = buf + "<img id='img" + this.btId
                + "' src='../../core/style/default/images/control_small.png'/>" + this.btName;
        }
        else
        {

            layerUIClassName = buf + "<img id='img" + this.btId
                + "' src='../../core/style/default/images/t_small.png'/>" + this.btName;
        }

        return layerUIClassName;
    }

}
