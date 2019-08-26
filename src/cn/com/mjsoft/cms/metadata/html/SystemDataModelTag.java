package cn.com.mjsoft.cms.metadata.html;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemDataModelTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -2278652240211642750L;

    private static Logger log = Logger.getLogger( SystemDataModelTag.class );

    private static MetaDataService metaDataService = MetaDataService
        .getInstance();

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {

        return null;
    }

    protected String returnPutValueName()
    {
        return "DataModel";
    }

    protected String returnRequestAndPageListAttName()
    {
        return "dataModelList";
    }

    protected Object returnSingleObject()
    {
        Long modelId = Long.valueOf( StringUtil.getLongValue( this.id, -1 ) );
        return metaDataService.retrieveSingleDataModelBeanById( modelId );
    }

    protected String returnValueRange()
    {
        return "pageRange";
    }

}
