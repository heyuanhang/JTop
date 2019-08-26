package cn.com.mjsoft.cms.content.html;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModelException;

public class ClientPhotoGroupTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -8996977273346124281L;

    private static ContentService contentService = ContentService.getInstance();

    private static ChannelService channelService = ChannelService.getInstance();

    String contentId = "";

    String group = "";// 对应模型字段标识

    String modelType = "";

    String serverMode = "";

    protected void initTag()
    {

    }

    @SuppressWarnings( "unchecked" )
    protected List returnObjectList()
    {
        Long cid = null;

        if( StringUtil.isStringNotNull( contentId ) )
        {
            cid = Long.valueOf( StringUtil.getLongValue( contentId, -1 ) );
        }

        if( cid != null && cid.longValue() > 0
            && StringUtil.isStringNotNull( modelType ) )
        {
            SiteGroupBean siteBean = ( SiteGroupBean ) pageContext.getRequest()
                .getAttribute( Constant.CONTENT.HTML_PUB_CURRENT_SITE );

            if( siteBean == null )
            {
                siteBean = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                        .getRequest() );
            }
            return contentService
                .retrieveGroupPhotoInfoByContentId( cid, group, StringUtil
                    .getIntValue( modelType, -1 ), siteBean, false );
        }

           
        Map info = null;
        
        // freemarker
        Object obj = pageContext
            .getAttribute( "Info" );

        if( obj instanceof SimpleHash )
        {
            try
            {
                info = ( ( SimpleHash ) obj ).toMap();
            }
            catch ( TemplateModelException e )
            {
                info = new HashMap();
                e.printStackTrace();
            }
        }
        else
        {
            info = ( Map ) pageContext.getAttribute( "Info" );
        }

        Integer modelType = Constant.METADATA.MODEL_TYPE_CONTENT;

        if( info == null )
        {
            info = new HashMap( 2 );

            ContentClassBean classBean = ( ContentClassBean ) pageContext
                .getAttribute( "Class" );

            if( classBean != null )
            {
                modelType = Constant.METADATA.MODEL_TYPE_CHANNEL;

                info.put( "classId", classBean.getClassId() );
            }
            else
            {
                SiteGroupBean site = ( SiteGroupBean ) pageContext
                    .getAttribute( "Site" );

                if( site != null )
                {
                    modelType = Constant.METADATA.MODEL_TYPE_SITE;

                    info.put( "siteId", site.getSiteId() );
                }
            }
        }

        if( info == null )
        {               
            // freemarker
            obj = pageContext
                .getAttribute( "Site" );

            if( obj instanceof SimpleHash )
            {
                try
                {
                    info = ( ( SimpleHash ) obj ).toMap();
                }
                catch ( TemplateModelException e )
                {
                    info = new HashMap();
                    e.printStackTrace();
                }
            }
            else
            {
                info = ( Map ) pageContext.getAttribute( "Site" );
            }
        }

        cid = null;

        if( StringUtil.isStringNotNull( contentId ) )
        {
            cid = Long.valueOf( StringUtil.getLongValue( contentId, -1 ) );

        }
        else if( info != null )
        {
            cid = ( Long ) info.get( "contentId" );
        }

        if( cid != null && cid.longValue() != -1 )
        {
            SiteGroupBean siteBean = null;

            boolean serMode = StringUtil.getBooleanValue( serverMode, false );

            if( serMode )
            {
                if( info.get( "classId" ) != null )
                {
                    ContentClassBean classBean = channelService
                        .retrieveSingleClassBeanInfoByClassId( ( Long ) info
                            .get( "classId" ) );

                    siteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
                        .getEntry( classBean.getSiteFlag() );
                }
                else
                {
                    siteBean = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
                        .getEntry( ( Long ) info.get( "siteId" ) );
                }
            }
            else
            {
                siteBean = ( SiteGroupBean ) pageContext.getRequest()
                    .getAttribute( Constant.CONTENT.HTML_PUB_CURRENT_SITE );

                if( siteBean == null )
                {
                    siteBean = SiteGroupService
                        .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                            .getRequest() );
                }
            }

            return contentService.retrieveGroupPhotoInfoByContentId( cid,
                group, modelType, siteBean, serMode );
        }

        // if( StringUtil.isStringNotNull( photoId ) )
        // {
        // String[] idsTemp = photoId.split( "," );
        //
        // return contentService.retrieveGroupPhotoInfoByGroupIdsNotUse( idsTemp
        // );
        // }

        return null;
    }

    protected String returnPutValueName()
    {
        return "Photo";
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
        return "selfRange";
    }

    public void setContentId( String contentId )
    {
        this.contentId = contentId;
    }

    public void setServerMode( String serverMode )
    {
        this.serverMode = serverMode;
    }

    public void setGroup( String group )
    {
        this.group = group;
    }

    public void setModelType( String modelType )
    {
        this.modelType = modelType;
    }
}
