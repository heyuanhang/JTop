package cn.com.mjsoft.cms.guestbook.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.guestbook.service.GuestbookService;
import cn.com.mjsoft.cms.publish.service.PublishService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientGuestbookTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -4018013040934201780L;

    private static GuestbookService gbService = GuestbookService.getInstance();

    private static PublishService publishService = PublishService.getInstance();

    private String gbId = "";

    private String configFlag = "";

    private String isReply = "";

    private String isCensor = "";

    private String isOpen = "";

    private String page = "false";

    private String pageSize = "15";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        SiteGroupBean site = ( SiteGroupBean ) pageContext.getRequest().getAttribute(
            Constant.CONTENT.HTML_PUB_CURRENT_SITE );

        if( site == null )
        {
            site = ( SiteGroupBean ) this.pageContext.getRequest().getAttribute( "SiteObj" );

            if( site == null )
            {
                site = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                        .getRequest() );
            }
        }

        List result = Collections.EMPTY_LIST;

        if( !"".equals( gbId ) )
        {
            result = new ArrayList( 1 );

            result.add( gbService.retrieveSingleGuestbookInfoMapByGbId( Long.valueOf( StringUtil
                .getLongValue( gbId, -1 ) ) ) );
        }
        else
        {
            int pn = StringUtil.getIntValue( this.pageContext.getRequest().getParameter( "pn" ), 1 );

            if( !"true".equals( page ) )
            {
                pn = 1;
            }

            Long count = gbService.retrieveGuestbookMainInfoCount( configFlag, isReply, isCensor,
                isOpen );

            Page pageInfo = new Page( StringUtil.getIntValue( pageSize, 15 ), count.intValue(), pn );

            result = gbService.retrieveGuestbookMainInfoMapList( configFlag, isReply, isCensor,
                isOpen, Long.valueOf( pageInfo.getFirstResult() ), Integer.valueOf( StringUtil
                    .getIntValue( pageSize, 15 ) ) );

            this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );

            pageContext.setAttribute( "allContent", result );

            //String queryCod = "configFlag=" + configFlag + "&isReply=" + isReply + "&isCensor="
                //+ isCensor + "&isOpen=" + isOpen + "&page=" + page + "&pageSize=" + pageSize;

            ContentClassBean classBean = ( ContentClassBean ) this.pageContext
                .getAttribute( "Class" );

            if( classBean != null )
            {
                // 静态分页
                publishService.htmlTagPage( this.pageContext, null, classBean.getClassId(),
                    classBean, classBean.getClassId(), pageInfo, page, "" );
            }

        }

        return result;
    }

    protected String returnPutValueName()
    {
        return "GbInfo";
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

    public void setConfigFlag( String configFlag )
    {
        this.configFlag = configFlag;
    }

    public void setIsCensor( String isCensor )
    {
        this.isCensor = isCensor;
    }

    public void setIsOpen( String isOpen )
    {
        this.isOpen = isOpen;
    }

    public void setIsReply( String isReply )
    {
        this.isReply = isReply;
    }

    public void setPageSize( String pageSize )
    {
        this.pageSize = pageSize;
    }

    public void setGbId( String gbId )
    {
        this.gbId = gbId;
    }

    public void setPage( String page )
    {
        this.page = page;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "allContent" );
        return EVAL_PAGE;
    }
}
