package cn.com.mjsoft.cms.advert.html;

import java.util.Collections;
import java.util.List;

import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

/**
 * 此类已废弃，不再使用
 * 
 * @author mjsoft
 * 
 */

public class SystemAdvertConfigParamTagNOTUSE extends AbstractIteratorTag
{

    private static final long serialVersionUID = -7955184494880943876L;

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {

        List result = Collections.EMPTY_LIST;
        //
        // if( "false".equals( isEdit ) )
        // {
        // Long configIdVar = Long.valueOf( StringUtil.getLongValue( configId,
        // -1 ) );
        // // input
        // result = advertService.retrieveAdvertConfigParamBeanList(
        // configIdVar, Integer
        // .valueOf( StringUtil.getIntValue( type, -1 ) ), Boolean
        // .valueOf( isEdit ) );
        //
        // }
        // else
        // {
        //
        // }
        // if( "all".equals( configId ) )
        // {
        // result = advertService.retrieveAllAdvertConfigBeanList();
        // }
        // else
        // {
        // Long configIdVar = Long.valueOf( StringUtil.getLongValue( configId,
        // -1 ) );
        //
        // if( configIdVar.longValue() > 0 )
        // {
        // result = new ArrayList( 1 );
        // result.add( advertService
        // .retrieveSingleAdvertConfigBeanByConfigId( configIdVar ) );
        // }
        // }

        return result;

    }

    protected String returnPutValueName()
    {
        return "Config";
    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected Object returnSingleObject()
    {
        return null;
    }

    protected String returnValueRange()
    {
        return TagConstants.SELF_RANFE;
    }

}
