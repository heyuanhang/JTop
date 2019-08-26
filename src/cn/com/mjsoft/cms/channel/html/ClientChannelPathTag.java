package cn.com.mjsoft.cms.channel.html;

import java.util.Collections;
import java.util.List;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientChannelPathTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 5405501120009681453L;

    private ChannelService channelService = ChannelService.getInstance();

    private String classId = "";

    private String contentId = "";

    protected String returnPutValueName()
    {
        return "PathClass";
    }

    protected String returnValueRange()
    {
        return "selfRange";
    }

    protected Object returnSingleObject()
    {

        return null;
    }

    protected List returnObjectList()
    {
        long id = StringUtil.getLongValue( classId, -1 );

        if( id > 0 )
        {
            ContentClassBean classBean = channelService
                .retrieveSingleClassBeanInfoByClassId( Long.valueOf( id ) );

            if( classBean != null && classBean.getClassId().longValue() > 0 )
            {
                List resultList = channelService
                    .retrieveContentClassBeanByCurrentPath( classBean
                        .getChannelPath() );

                return resultList;
            }
        }
        else
        {
            id = StringUtil.getLongValue( contentId, -1 );
        }

        return Collections.EMPTY_LIST;

    }

    protected void initTag()
    {

    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    public void setClassId( String classId )
    {
        this.classId = classId;
    }

    public void setContentId( String contentId )
    {
        this.contentId = contentId;
    }
}
