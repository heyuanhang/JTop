package cn.com.mjsoft.cms.advert.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.com.mjsoft.cms.advert.service.AdvertService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemAdvertConfigTag extends AbstractIteratorTag
{
    private static AdvertService advertService = AdvertService.getInstance();

    private static final long serialVersionUID = -7955184494880943876L;

    private String configId;

    private String posId;

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {

        List result = Collections.EMPTY_LIST;
        if( "all".equals( configId ) )
        {
            result = advertService.retrieveAllAdvertConfigBeanList();
        }
        else
        {
            Long configIdVar = Long.valueOf( StringUtil.getLongValue( configId,
                -1 ) );

            if( configIdVar.longValue() > 0 )
            {
                result = new ArrayList( 1 );
                result.add( advertService
                    .retrieveSingleAdvertConfigBeanByConfigId( configIdVar ) );
            }
            else
            {
                Long posdVar = Long.valueOf( StringUtil
                    .getLongValue( posId, -1 ) );

                result = new ArrayList( 1 );
                result.add( advertService
                    .retrieveSingleAdvertConnfigBeanByPosId( posdVar ) );
            }
        }

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

    public void setConfigId( String configId )
    {
        this.configId = configId;
    }

    public void setPosId( String posId )
    {
        this.posId = posId;
    }

}
