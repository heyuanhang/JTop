package cn.com.mjsoft.cms.channel.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;

/**
 * 不再使用,由cms:Class替代
 * 
 * @author mjsoft
 * 
 */
public class ClientContentClassListTag extends TagSupport
{
    private static final long serialVersionUID = -2958787213977213728L;

    // private static final Integer CHANNEL_NOT_SHOW = Integer.valueOf( 0 );

    private ChannelService channelService = ChannelService.getInstance();

    private static Logger log = Logger
        .getLogger( ClientContentClassListTag.class );

    private String name;// 此名字的所有孩子将被取出

    private String isSpec = "false";

    private String classType = "";

    private String layer;// 取某层的所有栏目

    private String parentId;// 此ID的所有孩子将被取出,注意，如果同时有名字，优先使用ID

    private String idList;// xx,xx,xx形式的id list

    private String flags = "";// aa,bb,cc形式的flag list

    private String type;// 是否取所有子节点 all:所有子节点

    private String showMode;

    public int doStartTag() throws JspException
    {
        Integer channelShowFlag = Constant.SITE_CHANNEL.CHANNEL_SHOW;

        if( "false".equals( showMode ) )
        {
            channelShowFlag = Constant.SITE_CHANNEL.CHANNEL_ALL_SHOW;
        }

        String siteFlag = "";
        SiteGroupBean siteBean = null;

        // 指定的站点优先
        if( Boolean.TRUE.equals( pageContext.getRequest().getAttribute(
            Constant.CONTENT.HTML_PUB_ACTION_FLAG ) ) )
        {
            // 来自发布逻辑的访问,根据管理站点确定当前站点
            siteBean = ( SiteGroupBean ) pageContext.getRequest().getAttribute(
                Constant.CONTENT.HTML_PUB_CURRENT_SITE );
        }
        else
        {
            // 根据URL来判断站点
            siteBean = SiteGroupService
                .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                    .getRequest() );
        }

        if( siteBean != null )
        {
            siteFlag = siteBean.getSiteFlag();
        }

        // TODO 将通过SHOWMODE来控制是否显示栏幕
        List result = Collections.EMPTY_LIST;

        if( StringUtil.isStringNotNull( parentId ) )
        {
            if( !"".equals( classType ) )
            {
                result = channelService
                    .retrieveConetentClassByParentClassId(
                        Long.valueOf( StringUtil
                            .getLongValue( parentId, -99999 ) ), siteFlag, Long
                            .valueOf( StringUtil.getLongValue( classType, -1 ) ) );
            }
            else
            {
                result = channelService.fetchConetentClassByParentClassID(
                    StringUtil.getLongValue( parentId, -99999 ), true,
                    channelShowFlag, siteFlag, StringUtil.getBooleanValue(
                        isSpec, false ) );
            }
        }
        else if( StringUtil.isStringNotNull( name ) )
        {
            result = channelService.retrieveConetentClassByParentClassName(
                name.trim(), channelShowFlag );
        }
        else if( StringUtil.isStringNotNull( layer ) )
        {
            result = channelService.fetchConetentClassByLayer( StringUtil
                .getLongValue( layer, -99999 ) );
        }
        else
        {

            // if( "总站".equals( site ) )
            {
                if( StringUtil.isStringNotNull( flags ) )
                // && !StringUtil.hasInjectChars( flags ) )
                {
                    String[] flagArray = StringUtil.split( flags, "," );

                    result = channelService.retrieveClassBeanInfoBySomeFlags(
                        flagArray, "up" );
                }
                else if( StringUtil.isStringNotNull( idList ) )
                {
                    String[] ids = StringUtil.split( idList, "," );
                    Long targetId;
                    List list = new ArrayList();

                    for ( int i = 0; i < ids.length; i++ )
                    {
                        try
                        {
                            // TODO 组合时Long化,不需要再次循环
                            targetId = Long.valueOf( ids[i] );
                        }
                        catch ( Exception e )
                        {
                            throw new JspException(
                                "[ClassList标签] [idList参数] 必须为ID" );
                        }
                        list.add( targetId );
                    }

                    result = channelService.retrieveClassBeanInfoBySomeIds(
                        list, "up" );
                }
                else if( "all".equals( type ) )
                {
                    // 暂时取总站一级
                    result = channelService
                        .fetchAllIncludeConetentClassByClassID( siteFlag,
                            StringUtil.getBooleanValue( isSpec, false ),
                            Integer.valueOf( -1 ) );
                }
                else
                {
                    // 暂时取总站一级
                    result = channelService.fetchConetentClassByParentClassID(
                        -9999, true, channelShowFlag, siteFlag, StringUtil
                            .getBooleanValue( isSpec, false ) );
                }
            }
        }

        log.debug( "查询出的栏目列表:" + result );

        pageContext.setAttribute( "classList", result );

        return EVAL_BODY_INCLUDE;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId( String parentId )
    {
        this.parentId = parentId;
    }

    public String getLayer()
    {
        return layer;
    }

    public void setLayer( String layer )
    {
        this.layer = layer;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public String getShowMode()
    {
        return showMode;
    }

    public void setShowMode( String showMode )
    {
        this.showMode = showMode;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public String getIdList()
    {
        return idList;
    }

    public void setIdList( String idList )
    {
        this.idList = idList;
    }

    public void setIsSpec( String isSpec )
    {
        this.isSpec = isSpec;
    }

    public void setClassType( String classType )
    {
        this.classType = classType;
    }

    public int doEndTag() throws JspException
    {
        pageContext.removeAttribute( "classList" );
        return EVAL_PAGE;
    }

    public void release()
    {

    }

}
