package cn.com.mjsoft.cms.metadata.html;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemModelFiledRowTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 1670108015380376917L;

    private static Logger log = Logger.getLogger( SystemModelFiledTag.class );

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        return null;
    }

    protected String returnPutValueName()
    {
        return "RowFiled";
    }

    protected String returnRequestAndPageListAttName()
    {
        return "ModelFiled";
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
