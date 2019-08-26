package cn.com.mjsoft.cms.content.html;

import java.util.ArrayList;
import java.util.List;

import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemCommendContentTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 1360699805696089655L;

    private static ContentService contentService = ContentService.getInstance();

    private String typeId = "-1";

    private String flag = "";

    private String infoId = "-1";

    private String size = "10";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        List commendContent = null;

        if( !"-1".equals( infoId ) )
        {
            commendContent = new ArrayList( 1 );
            commendContent.add( contentService
                .retrieveSingleCommendPushInfoByInfoId( Long
                    .valueOf( StringUtil.getLongValue( infoId, -1 ) ) ) );
        }
        else
        {
            int nextPage = StringUtil.getIntValue( this.pageContext
                .getRequest().getParameter( "pn" ), 1 );

            int pageSize = StringUtil.getIntValue( size, 10 );

            Page pageInfo = null;

            Integer count = null;

            if( !"-1".equals( typeId ) )
            {
                count = contentService
                    .retrieveAllCommendContentByCommendCountByTypeId( Long
                        .valueOf( StringUtil.getLongValue( typeId, -1 ) ) );
            }
            else
            {
                count = contentService
                    .retrieveAllCommendContentByCommendCountByFlag( flag );
            }

            pageInfo = new Page( pageSize, count.intValue(), nextPage );

            this.pageContext.setAttribute( "___system_dispose_page_object___",
                pageInfo );

            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
                .getSecuritySession().getCurrentLoginSiteInfo();

            if( !"-1".equals( typeId ) )
            {
                commendContent = contentService
                    .retrieveAllCommendContentByCommendByTypeId( Long
                        .valueOf( StringUtil.getLongValue( typeId, -1 ) ), site
                        .getSiteFlag(), Long
                        .valueOf( pageInfo.getFirstResult() ), Integer
                        .valueOf( pageInfo.getPageSize() ) );
            }
            else
            {
                commendContent = contentService
                    .retrieveAllCommendContentByCommendByFlag( flag, site
                        .getSiteFlag() );
            }
        }

        return commendContent;
    }

    protected String returnPutValueName()
    {
        return "CommInfo";
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

    public void setFlag( String flag )
    {
        this.flag = flag;
    }

    public void setInfoId( String infoId )
    {
        this.infoId = infoId;
    }

    public void setSize( String size )
    {
        this.size = size;
    }

    public void setTypeId( String typeId )
    {
        this.typeId = typeId;
    }

}
