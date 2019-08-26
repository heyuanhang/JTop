package cn.com.mjsoft.cms.channel.html;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.IPSeeker;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.handler.view.DefaultResultHandler;

public class ChannelWhiteIpViewTag extends TagSupport
{
    private static final long serialVersionUID = 1L;

    private static DefaultResultHandler resultHandler = new DefaultResultHandler();

    private ChannelService channelService = ChannelService.getInstance();

    private String classId = "";

    private String jump = "";

    private boolean mustSkip = false;

    public int doStartTag() throws JspException
    {

        // 发布时跳过
        HttpServletRequest request = ( HttpServletRequest ) pageContext
            .getRequest();

        if( !Boolean.TRUE.equals( request
            .getAttribute( Constant.CONTENT.HTML_PUB_ACTION_FLAG ) ) )
        {

            if( !"".equals( classId ) )
            {

                Long cid = Long
                    .valueOf( StringUtil.getLongValue( classId, -1 ) );

                ContentClassBean classBean = channelService
                    .retrieveSingleClassBeanInfoByClassId( cid );

                // 检查是否存在白名单
                String whiteIp = classBean.getWhiteIp();

                if( StringUtil.isStringNotNull( whiteIp ) )
                {
                    String loginIp = IPSeeker.getIp( request );

                    if( !ServiceUtil.checkWhiteIP( whiteIp, loginIp ) )
                    {
                        mustSkip = true;

                        jumpAccFailPage();
                    }
                }

            }

        }

        return EVAL_BODY_INCLUDE;

    }

    private void jumpAccFailPage()
    {
        HttpServletRequest request = ( HttpServletRequest ) pageContext
            .getRequest();

        HttpServletResponse response = ( HttpServletResponse ) pageContext
            .getResponse();

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper
            .getSecuritySession().getCurrentLoginSiteInfo();

        if( site == null || site.getSiteId().longValue() < 0 )
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

        resultHandler.resolveCustomDirectResult( site.getSiteUrl() + jump,
            request, response, true, null );
    }

    public int doEndTag() throws JspException
    {
        if( mustSkip )
        {
            mustSkip  =false;   
            
            classId = "";
            
            jump = "";
           
            return SKIP_PAGE;
        }
        else
        {
            mustSkip  =false;   
            
            classId = "";
            
            jump = "";  
            
            return EVAL_PAGE;
        }
    }

    public void setClassId( String classId )
    {
        this.classId = classId;
    }

    public void setJump( String jump )
    {
        this.jump = jump;
    }

}
