package cn.com.mjsoft.cms.channel.html;

import java.util.List;

import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemCommendClassTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 7859114261317793865L;

    private ChannelService channelService = ChannelService.getInstance();

    private String typeId = "";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        List result = null;

        if( !"".equals( typeId ) )
        {
            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
                .getSecuritySession().getCurrentLoginSiteInfo();

            result = channelService.retrieveContentCommendContentClassBean(
                site.getSiteFlag(), Long.valueOf( StringUtil.getLongValue(
                    typeId, -1 ) ) );
        }

        return result;
    }

    protected String returnPutValueName()
    {
        return "CommClass";
    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected Object returnSingleObject()
    {
        return null;
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
    }

    public void setTypeId( String typeId )
    {
        this.typeId = typeId;
    }

}
