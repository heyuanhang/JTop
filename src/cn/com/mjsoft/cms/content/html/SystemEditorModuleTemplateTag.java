package cn.com.mjsoft.cms.content.html;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemEditorModuleTemplateTag extends TagSupport
{
    private static final long serialVersionUID = 3326889500558105513L;

    private static ChannelService channelService = ChannelService.getInstance();

    private String type = "";

    public int doStartTag() throws JspException
    {
        String htmlCode = "";

        SecuritySession securitySession = SecuritySessionKeeper
            .getSecuritySession();

        SiteGroupBean siteBean = ( SiteGroupBean ) securitySession
            .getCurrentLoginSiteInfo();

        Map emInfo = null;

        if( "video".equals( type ) )
        {
            emInfo = channelService.retrieveSingleEditorModuleInfoByType(
                Integer.valueOf( 1 ), siteBean.getSiteId() );

            htmlCode = SystemSafeCharUtil
                .encodeJSNotIncludeLtAndGt( ( String ) emInfo.get( "code" ) );
        }
        else if( "image".equals( type ) )
        {
            emInfo = channelService.retrieveSingleEditorModuleInfoByType(
                Integer.valueOf( 2 ), siteBean.getSiteId() );

            htmlCode = SystemSafeCharUtil
                .encodeJSNotIncludeLtAndGt( ( String ) emInfo.get( "code" ) );
        }
        else if( "file".equals( type ) )
        {
            emInfo = channelService.retrieveSingleEditorModuleInfoByType(
                Integer.valueOf( 3 ), siteBean.getSiteId() );

            htmlCode = SystemSafeCharUtil
                .encodeJSNotIncludeLtAndGt( ( String ) emInfo.get( "code" ) );
        }

        this.pageContext.setAttribute( "EditorTemp", htmlCode );

        return EVAL_BODY_INCLUDE;
    }

    public void setType( String type )
    {
        this.type = type;
    }
}
