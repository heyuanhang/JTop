package cn.com.mjsoft.cms.guestbook.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.guestbook.bean.GuestbookConfigBean;
import cn.com.mjsoft.cms.guestbook.service.GuestbookService;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class SystemGuestbookTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -7352826949956396190L;

    private static GuestbookService gbService = GuestbookService.getInstance();

    private String gbId = "";

    private String configFlag = "";

    private String isReply = "";

    private String isCensor = "";

    private String isOpen = "";

    private String pageSize = "15";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        List result = Collections.EMPTY_LIST;

        if( !"".equals( gbId ) )
        {
            result = new ArrayList( 1 );

            result.add( gbService.retrieveSingleGuestbookInfoMapByGbId( Long.valueOf( StringUtil
                .getLongValue( gbId, -1 ) ) ) );
        }
        else
        {

            int page = StringUtil.getIntValue( ( String ) this.pageContext.getRequest()
                .getParameter( "pn" ), 1 );

            // 管理模式对于没有cfg的情况,取第一个留言板
            if( StringUtil.isStringNull( configFlag ) )
            {

                List accList = SecurityService.getInstance().retrieveAccIdList( null, -99999l,
                    -9999l, null, Integer.valueOf( 5 ) );

                if( !accList.isEmpty() )
                {
                    Long cfgId = ( Long ) ( ( Map ) accList.get( 0 ) ).get( "accId" );

                    GuestbookConfigBean cfgBean = gbService
                        .retrieveSingleGuestbookConfigBeanByConfigId( cfgId );

                    if( cfgBean != null )
                    {
                        configFlag = cfgBean.getCfgFlag();
                    }

                    if( StringUtil.isStringNull( configFlag ) )
                    {
                        Page pageInfo = new Page( StringUtil.getIntValue( pageSize, 12 ), 0, page );
                        this.pageContext
                            .setAttribute( "___system_dispose_page_object___", pageInfo );

                        pageContext.setAttribute( "allContent", result );
                    }
                }
            }

            Long count = gbService.retrieveGuestbookMainInfoCount( configFlag, isReply, isCensor,
                isOpen );

            Page pageInfo = new Page( StringUtil.getIntValue( pageSize, 12 ), count.intValue(),
                page );

            result = gbService.retrieveGuestbookMainInfoMapList( configFlag, isReply, isCensor,
                isOpen, Long.valueOf( pageInfo.getFirstResult() ), Integer.valueOf( StringUtil
                    .getIntValue( pageSize, 15 ) ) );

            this.pageContext.setAttribute( "___system_dispose_page_object___", pageInfo );

            pageContext.setAttribute( "allContent", result );

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

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "allContent" );
        return EVAL_PAGE;
    }

}
