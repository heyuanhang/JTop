package cn.com.mjsoft.cms.metadata.html;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientModelFiledTag extends AbstractIteratorTag
{

    private static final long serialVersionUID = 1L;

    private static MetaDataService metaDataService = MetaDataService
        .getInstance();

    private String modelId = "-1";
    
    private String fieldSign = "";
    
    protected void initTag()
    {
        if(StringUtil.isStringNotNull( fieldSign ))
        {
            this.setToSingleMode();
        }
    }

    protected List returnObjectList()
    {
        if( "-1".equals( modelId ) )
        {
            return new ArrayList();
        }

        List result = metaDataService.retrieveModelFiledInfoBeanList( Long
            .valueOf( StringUtil.getLongValue( modelId, -1 ) ) );

        return result;

    }

    protected String returnPutValueName()
    {
        return "Field";
    }

    protected String returnRequestAndPageListAttName()
    {

        return null;
    }

    protected Object returnSingleObject()
    {     
        return metaDataService.retrieveSingleDataModelFieldBeanBySign( fieldSign  );
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
    }

    public void setModelId( String modelId )
    {
        this.modelId = modelId;
    }

    public void setFieldSign( String fieldSign )
    {
        this.fieldSign = fieldSign;
    }

}
