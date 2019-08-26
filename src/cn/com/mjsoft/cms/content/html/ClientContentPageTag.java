package cn.com.mjsoft.cms.content.html;

import java.util.List;

import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientContentPageTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 1732248065909079564L;

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        return null;
    }

    protected String returnPutValueName()
    {
        return "CPage";
    }

    protected String returnRequestAndPageListAttName()
    {
        return "contentPageInfoList";
    }

    protected Object returnSingleObject()
    {
        return null;
    }

    protected String returnValueRange()
    {
        return TagConstants.PAGE_RANFE;
    }

}
