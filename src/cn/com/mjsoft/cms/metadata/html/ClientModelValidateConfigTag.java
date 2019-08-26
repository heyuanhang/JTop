package cn.com.mjsoft.cms.metadata.html;

import java.util.List;

import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientModelValidateConfigTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 7946643453279989627L;

    private static MetaDataService metaDataService = MetaDataService
        .getInstance();

    private String fieldId = "-1";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        return metaDataService.retrieveAllModelValidateConfigBean();

    }

    protected String returnPutValueName()
    {
        return "Valid";
    }

    protected String returnRequestAndPageListAttName()
    {

        return null;
    }

    protected Object returnSingleObject()
    {

        Long configId = Long.valueOf( StringUtil.getLongValue( this.id, -1 ) );

        Long fId = Long.valueOf( StringUtil.getLongValue( fieldId, -1 ) );

        return metaDataService.retrieveSingleModelValidateConfigBean( configId,
            fId );
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
    }

    public void setFieldId( String fieldId )
    {
        this.fieldId = fieldId;
    }

}
