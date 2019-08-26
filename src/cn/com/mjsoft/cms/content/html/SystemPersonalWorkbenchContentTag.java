package cn.com.mjsoft.cms.content.html;

import java.util.List;

import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemPersonalWorkbenchContentTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -2229377490718502128L;

    private static ContentService contentService = ContentService.getInstance();

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        return null;
    }

    protected String returnPutValueName()
    {
        return "SPInfo";
    }

    protected String returnRequestAndPageListAttName()
    {
        return "SPInfoList";
    }

    protected Object returnSingleObject()
    {
        Long cid = Long.valueOf( StringUtil.getLongValue( this.id, -1 ) );

        if( cid.longValue() > 0 )
        {

            return contentService
                .retrieveSingleWorkflowContentProcessInfo( cid );
        }
        
        return null;
    }

    protected String returnValueRange()
    {
        return "pageRange";
    }

}
