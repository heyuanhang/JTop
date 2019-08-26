package cn.com.mjsoft.cms.common.html;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemPreviewTag extends TagSupport
{
    private static final long serialVersionUID = 2092630218602010944L;

    private static ContentService contentService = ContentService.getInstance();

    private static ChannelService channelService = ChannelService.getInstance();

    private String mode = "";

    private String id = "";

    public int doStartTag() throws JspException
    {
        SecuritySession securitySession = SecuritySessionKeeper.getSecuritySession();

        // 必须是系统管理员才可使用
        if( securitySession.isManager() )
        {
            if( "content".equals( mode ) )
            {
                Map info = ( Map ) this.pageContext.getAttribute( "Info" );

                if( info == null )
                {

                    Long idVar = Long.valueOf( StringUtil.getLongValue( id, -1 ) );

                    Long modelId = contentService.retrieveContentMainInfoModelIdByCid( idVar );

                    // 编辑模式
                    info = contentService.retrieveSingleUserDefineContent( modelId, idVar );

                }

                if( info != null && !info.isEmpty() )
                {
                    // 外部地址
                    String endUrl = null;
                    if( StringUtil.isStringNotNull( ( String ) info.get( "outLink" ) ) )
                    {
                        endUrl = ( String ) info.get( "outLink" );
                    }
                    else
                    {
                        ContentClassBean classBean = channelService
                            .retrieveSingleClassBeanInfoByClassId( ( Long ) info.get( "classId" ) );

                        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                            .getEntry( classBean.getSiteFlag() );

                        String contentTemplateUrl = ( String ) info.get( "especialTemplateUrl" );

                        if( StringUtil.isStringNull( contentTemplateUrl ) )
                        {
                            // 如果当前的单个内容没有特殊模斑则将取栏目共用模版
                            contentTemplateUrl = classBean.getContentTemplateUrl();
                        }

                        endUrl = site.getSiteUrl()
                            + StringUtil.replaceString( contentTemplateUrl, "{content-id}",
                                ( ( Long ) info.get( "contentId" ) ).toString(), false, false );
                    }

                    if( endUrl.endsWith( ".jsp" ) || endUrl.endsWith( ".thtml" ) )
                    {
                        endUrl = endUrl + "?___sys_cms_preview___="
                            + ParamUtilTag.encodePW( "true" + System.currentTimeMillis(), "A" );
                    }
                    else
                    {
                        endUrl = endUrl + "&___sys_cms_preview___="
                            + ParamUtilTag.encodePW( "true" + System.currentTimeMillis(), "A" );
                    }

                    try
                    {
                        this.pageContext.getOut().write( endUrl );
                    }
                    catch ( IOException e )
                    {
                        e.printStackTrace();
                    }

                }
            }
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        return super.doEndTag();
    }

    public void setMode( String mode )
    {
        this.mode = mode;
    }

    public void setId( String id )
    {
        this.id = id;
    }

}
