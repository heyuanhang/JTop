package cn.com.mjsoft.cms.channel.html;

import java.util.List;

import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemClassTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -6514072152516302804L;

    private ChannelService channelService = ChannelService.getInstance();

    private String flag = "";

    protected String returnPutValueName()
    {
        return "Class";
    }

    protected String returnRequestAndPageListAttName()
    {
        return "classList";
    }

    protected String returnValueRange()
    {
        return "pageRange";
    }

    protected Object returnSingleObject()
    {
        Long targetId = Long.valueOf( StringUtil
            .getLongValue( this.getId(), -1 ) );

        if( targetId.longValue() > 0 )
        {
            return channelService
                .retrieveSingleClassBeanInfoByClassId( targetId );
        }
        else
        {
            return channelService.retrieveSingleClassBeanInfoByClassFlag( flag );
        }
    }

    protected List returnObjectList()
    {
        return null;
    }

    public void setFlag( String flag )
    {
        setToSingleMode();
        this.flag = flag;
    }

    protected void initTag()
    {
       

    }

}
