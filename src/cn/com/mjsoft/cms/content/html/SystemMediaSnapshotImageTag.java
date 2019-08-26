package cn.com.mjsoft.cms.content.html;

import java.util.List;

import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemMediaSnapshotImageTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -2229377490718502128L;

    private static ContentService contentService = ContentService.getInstance();

    private String contentId;

    protected void initTag()
    {
        // TODO Auto-generated method stub

    }

    protected List returnObjectList()
    {
        return contentService.retrieveMediaSnapshotImageByContentId( Long
            .valueOf( StringUtil.getLongValue( contentId, -1 ) ) );
    }

    protected String returnPutValueName()
    {
        return "SPSSIMG";
    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected Object returnSingleObject()
    {
        // TODO Auto-generated method stub
        return null;
    }

    protected String returnValueRange()
    {
        return "selfRange";
    }

    public void setContentId( String contentId )
    {
        this.contentId = contentId;
    }
}
