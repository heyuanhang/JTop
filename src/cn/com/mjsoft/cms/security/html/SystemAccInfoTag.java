package cn.com.mjsoft.cms.security.html;

import java.util.List;

import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemAccInfoTag extends AbstractIteratorTag
{

    private static final long serialVersionUID = 8849522045649692765L;

    protected void initTag()
    {
        // TODO Auto-generated method stub

    }

    protected List returnObjectList()
    {
        return null;
    }

    protected String returnPutValueName()
    {
        return "Acc";
    }

    protected String returnRequestAndPageListAttName()
    {
        return "accInfoList";
    }

    protected Object returnSingleObject()
    {
        return null;
    }

    protected String returnValueRange()
    {
        return "pageRange";
    }

}
