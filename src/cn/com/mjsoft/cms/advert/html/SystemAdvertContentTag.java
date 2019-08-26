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

public class SystemAdvertContentTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -8513644059209629696L;

    private static AdvertService advertService = AdvertService.getInstance();

    private String posId = "-1";

    private String advertId = "-1";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {

        List result = Collections.EMPTY_LIST;

        Long advertIdVar = Long
            .valueOf( StringUtil.getLongValue( advertId, -1 ) );

        if( advertIdVar.longValue() > 0 )
        {
            result = new ArrayList( 1 );
            result.add( advertService
                .retrieveSingleAdvertContentInfoByAdvertId( advertIdVar ) );
        }
        else
        {
            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
                .getSecuritySession().getCurrentLoginSiteInfo();

            Long posIdVar = Long.valueOf( StringUtil.getLongValue( posId, -1 ) );

            if( posIdVar.longValue() > 0 )
            {
                // 取某版位的所有广告
                result = advertService.retrieveAdvertContentInfoListByPosId(
                    posIdVar, site.getSiteId() );
            }
            else
            {
                result = advertService.retrieveAllAdvertContentInfoList( site
                    .getSiteId() );
            }
        }

        return result;

    }

    protected String returnPutValueName()
    {
        return "Advert";
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

    public void setAdvertId( String advertId )
    {
        this.advertId = advertId;
    }

}
