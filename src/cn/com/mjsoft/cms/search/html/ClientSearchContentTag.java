package cn.com.mjsoft.cms.search.html;

import java.util.List;

import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientSearchContentTag extends AbstractIteratorTag
{
    /**
     * 
     */
    private static final long serialVersionUID = 8844563817552366577L;

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        return null;
    }

    protected String returnPutValueName()
    {
        return "Hit";
    }

    protected String returnRequestAndPageListAttName()
    {

        return "searchResultList";
    }

    protected Object returnSingleObject()
    {
        // TODO Auto-generated method stub
        return null;
    }

    protected String returnValueRange()
    {
        return TagConstants.PAGE_RANFE;
    }
}
