package cn.com.mjsoft.cms.channel.html;

import java.util.List;

import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemContentTypeListTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -1405614749725592537L;

    private ChannelService channelService = ChannelService.getInstance();

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
            .getSecuritySession().getCurrentLoginSiteInfo();

        return channelService.retrieveAllContentTypeBean( site.getSiteId() );
    }

    protected String returnPutValueName()
    {
        return "ContentType";
    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected Object returnSingleObject()
    {
        Long idVar = Long.valueOf( StringUtil.getLongValue( id, -1 ) );

        return channelService.retrieveSingleContentTypeBean( idVar );
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
    }

}
