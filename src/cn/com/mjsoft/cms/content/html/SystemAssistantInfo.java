package cn.com.mjsoft.cms.content.html;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemAssistantInfo extends TagSupport
{
    private static final long serialVersionUID = 5137925712508902535L;

    private static ContentService contentService = ContentService.getInstance();

    private static ChannelService channelService = ChannelService.getInstance();

    String contentId = "";

    String mode = "copy";

    public int doStartTag() throws JspException
    {
        Long cid = Long.valueOf( StringUtil.getLongValue( contentId, -1 ) );

        Map infoMap = new HashMap();

        if( cid != null && cid.longValue() != -1 )
        {
            if( "copy".equals( mode ) )
            {
                List reult = contentService
                    .retrieveContentAssistantCopyInfoByContentId( cid );

                Map info = null;

                StringBuffer buf = new StringBuffer();

                for ( int i = 0; i < reult.size(); i++ )
                {
                    info = ( Map ) reult.get( i );

                    ContentClassBean classBean = channelService
                        .retrieveSingleClassBeanInfoByClassId( ( Long ) info
                            .get( "refClassId" ) );

                    if( i + 1 == reult.size() )
                    {
                        buf.append( classBean.getClassName() );
                    }
                    else
                    {
                        buf.append( classBean.getClassName() + ", " );
                    }
                }

                infoMap.put( "copyClassCount", Integer.valueOf( reult.size() ) );

                infoMap.put( "copyClassStr", buf.toString() );
            }
            else if( "share".equals( mode ) )
            {
                List result = contentService.retrieveShareContentSiteName( cid );

                StringBuffer buf = new StringBuffer();

                for ( int i = 0; i < result.size(); i++ )
                {
                    if( i + 1 == result.size() )
                    {
                        buf.append( result.get( i ) );
                    }
                    else
                    {
                        buf.append( result.get( i ) + ", " );
                    }
                }

                infoMap
                    .put( "shareSiteCount", Integer.valueOf( result.size() ) );

                infoMap.put( "shareSiteStr", buf.toString() );
            }
        }

        this.pageContext.setAttribute( "AssiInfo", infoMap );

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        this.pageContext.removeAttribute( "AssiInfo" );
        return super.doEndTag();
    }

    public void setContentId( String contentId )
    {
        this.contentId = contentId;
    }

    public void setMode( String mode )
    {
        this.mode = mode;
    }
}
