package cn.com.mjsoft.cms.content.html;

import java.util.List;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemTrashContentTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 156178878236194167L;

    private static ChannelService channelService = ChannelService.getInstance();

    private static ContentService contentService = ContentService.getInstance();

    private String classId = "";

    private String key = "";

    private String pn = "";

    private String size = "15";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        List infoList = null;

        if( !SecuritySessionKeeper.getSecuritySession().isManager() )
        {
            return infoList;
        }

        Page pageInfo = null;

        Long targetClassId = Long.valueOf( StringUtil
            .getLongValue( classId, -1 ) );

        ContentClassBean classBean = channelService
            .retrieveSingleClassBeanInfoByClassId( Long.valueOf( targetClassId ) );

        if( StringUtil.isStringNotNull( key ) )
        {
            String keyVal = SystemSafeCharUtil.decodeFromWeb( key );

            infoList = contentService.retrieveTrashContentByTitleKey( keyVal,
                targetClassId );

            pageInfo = new Page( 11, infoList.size(), 1 );

            this.pageContext.setAttribute( "___system_dispose_page_object___",
                pageInfo );
        }
        else
        {
            int nextPage = StringUtil.getIntValue( pn, 1 );

            int pageSize = StringUtil.getIntValue( size, 15 );

            Integer count = contentService.retrieveTrashContentCountByClassId(
                targetClassId, classBean.getContentType() );

            pageInfo = new Page( pageSize, count.intValue(), nextPage );

            infoList = contentService.retrieveTrashContentByClassId(
                targetClassId, classBean.getContentType(), Long
                    .valueOf( pageInfo.getFirstResult() ), Integer
                    .valueOf( pageSize ) );

            this.pageContext.setAttribute( "___system_dispose_page_object___",
                pageInfo );

        }

        return infoList;
    }

    protected String returnPutValueName()
    {
        return "Trash";
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

    public void setClassId( String classId )
    {
        this.classId = classId;
    }

    public void setPn( String pn )
    {
        this.pn = pn;
    }

    public void setSize( String size )
    {
        this.size = size;
    }

    public void setKey( String key )
    {
        this.key = key;
    }
}
