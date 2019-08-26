package cn.com.mjsoft.cms.advert.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.com.mjsoft.cms.advert.service.AdvertService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemAdvertPosTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -8149550584344471576L;

    private static AdvertService advertService = AdvertService.getInstance();

    private String posId = "";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        List result = Collections.EMPTY_LIST;

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
            .getSecuritySession().getCurrentLoginSiteInfo();

        if( "all".equals( posId ) )
        {
            result = advertService.retrieveAllAdvertPosInfoList( site
                .getSiteId() );
        }
        else
        {
            Long posIdVar = Long.valueOf( StringUtil.getLongValue( posId, -1 ) );

            if( posIdVar.longValue() > 0 )
            {
                result = new ArrayList( 1 );
                result.add( advertService
                    .retrieveSingleAdvertPositionMapByPosId( posIdVar ) );
            }
        }

        return result;

    }

    protected String returnPutValueName()
    {
        return "Pos";
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

    public void setPosId( String posId )
    {
        this.posId = posId;
    }

}
