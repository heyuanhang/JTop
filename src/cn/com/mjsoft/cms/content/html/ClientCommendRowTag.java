package cn.com.mjsoft.cms.content.html;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.bean.ContentCommendPushInfoBean;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientCommendRowTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 3119376902430811640L;

    private static ContentService contentService = ContentService.getInstance();

    private String commFlag = "";

    private String rowFlag = "";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        List infoList = null;
        if( !"".equals( commFlag ) )
        {
            SiteGroupBean site = ( SiteGroupBean ) pageContext.getRequest()
                .getAttribute( Constant.CONTENT.HTML_PUB_CURRENT_SITE );

            if( site == null )
            {
                site = ( SiteGroupBean ) this.pageContext.getRequest()
                    .getAttribute( "SiteObj" );

                if( site == null )
                {
                    site = SiteGroupService
                        .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                            .getRequest() );
                }
            }

            infoList = contentService.retrieveCommendContentRowInfoByRowFlag(
                Long.valueOf( StringUtil.getLongValue( rowFlag, -1 ) ),
                commFlag, site.getSiteFlag() );
        }
        else
        {
            ContentCommendPushInfoBean commInfoBean = ( ContentCommendPushInfoBean ) this.pageContext
                .getAttribute( "CommInfo" );

            if( commInfoBean == null )
            {

                return null;
            }

            infoList = commInfoBean.getRowInfoList();

        }

        return infoList;
    }

    protected String returnPutValueName()
    {
        return "RowInfo";
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

    public void setCommFlag( String commFlag )
    {
        this.commFlag = commFlag;
    }

    public void setRowFlag( String rowFlag )
    {
        this.rowFlag = rowFlag;
    }

}
