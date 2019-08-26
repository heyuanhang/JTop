package cn.com.mjsoft.cms.metadata.html;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemModelFiledTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 1289890997357473475L;

    private static Logger log = Logger.getLogger( SystemModelFiledTag.class );

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
        return "ModelFiled";
    }

    protected String returnRequestAndPageListAttName()
    {
        return "ModelFiledInfoList";
    }

    protected Object returnSingleObject()
    {
        Long id = Long.valueOf( StringUtil.getLongValue( this.getId(), -1 ) );

        Map currentInfo = ( Map ) this.pageContext.getAttribute( "Info" );

        return metaDataService.retrieveSingleDataModelFieldBeanById( id,
            currentInfo );
    }

    protected String returnValueRange()
    {
        return "pageRange";
    }
}
