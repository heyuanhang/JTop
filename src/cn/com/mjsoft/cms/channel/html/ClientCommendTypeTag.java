package cn.com.mjsoft.cms.channel.html;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.TagConstants;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientCommendTypeTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = 674136668781184273L;

    private static ChannelService channelService = ChannelService.getInstance();

    private String typeId = "";

    private String classId = "";

    private String showAll = "false";

    private String isSpec = "false";

    protected void initTag()
    {

    }

    protected List returnObjectList()
    {
        if( !"".equals( typeId ) )
        {
            ContentCommendTypeBean commTypeBean = channelService
                .retrieveSingleContentCommendTypeBeanByTypeId( Long
                    .valueOf( StringUtil.getLongValue( typeId, -1 ) ) );

            List result = new ArrayList( 1 );

            result.add( commTypeBean );

            return result;
        }
        else
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

            return channelService.retrieveContentCommendTypeBean( site
                .getSiteFlag(), Long.valueOf( StringUtil.getLongValue( classId,
                -1 ) ), StringUtil.getBooleanValue( showAll, false ),
                StringUtil.getBooleanValue( isSpec, false ), false );
        }
    }

    protected String returnPutValueName()
    {
        return "CommendType";
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

    public void setTypeId( String typeId )
    {
        this.typeId = typeId;
    }

    public void setClassId( String classId )
    {
        this.classId = classId;
    }

    public void setShowAll( String showAll )
    {
        this.showAll = showAll;
    }

    public void setIsSpec( String isSpec )
    {
        this.isSpec = isSpec;
    }
}
