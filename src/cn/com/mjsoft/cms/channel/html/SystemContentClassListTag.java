package cn.com.mjsoft.cms.channel.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.cms.common.page.Page;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

public class SystemContentClassListTag extends TagSupport
{
    private static final long serialVersionUID = -2848819202539743076L;

    private ChannelService channelService = ChannelService.getInstance();

    private static Logger log = Logger
        .getLogger( ClientContentClassListTag.class );

    private String key = "";

    private String name;// 此名字的所有孩子将被取出

    private String isSpec = "false";

    private String classType = "";

    private String layer;// 取某层的所有栏目

    private String parentId;// 此ID的所有孩子将被取出,注意，如果同时有名字，优先使用ID

    private String idList;// xx,xx,xx形式的id list

    private String flags = "";// aa,bb,cc形式的flag list

    // TODO 目前通过url判断当前访问站,此参数已多余
    private String site = "";// 所属站点，

    private String type;// 是否取所有子节点 all:所有子节点

    private String firstClassMode = "false";

    private String showMode;

    private String size = "5000";

    public int doStartTag() throws JspException
    {
        // TODO 需要获取当前站点地址
        // ServletUtility.regSiteHost( ( HttpServletRequest ) this.pageContext
        // .getRequest() );

        Integer channelShowFlag = Constant.SITE_CHANNEL.CHANNEL_SHOW;

        if( "false".equals( showMode ) )
        {
            channelShowFlag = Constant.SITE_CHANNEL.CHANNEL_ALL_SHOW;
        }

        String siteFlag = "";
        SiteGroupBean siteBean = null;
        // 指定的站点优先
        if( "" != this.site )
        {
            siteFlag = this.site;
        }

        else
        {
            // 根据登陆管理员来获取站点
            SecuritySession securitySession = SecuritySessionKeeper
                .getSecuritySession();

            siteBean = ( SiteGroupBean ) securitySession
                .getCurrentLoginSiteInfo();
        }

        if( siteBean != null )
        {
            siteFlag = siteBean.getSiteFlag();
        }

        //   将通过SHOWMODE来控制是否显示栏目
        List result = Collections.EMPTY_LIST;

        if( StringUtil.isStringNotNull( key ) )
        {
            String keyVal = SystemSafeCharUtil.decodeFromWeb( key );

            result = channelService.retrieveConetentClassByClassNameSearchKey(
                keyVal, Long.valueOf( StringUtil
                    .getLongValue( parentId, -99999 ) ) );

        }
        else if( StringUtil.isStringNotNull( parentId ) )
        {
            if( !"".equals( classType ) )
            {
                int nextPage = StringUtil.getIntValue( this.pageContext
                    .getRequest().getParameter( "pn" ), 1 );

                int pageSize = StringUtil.getIntValue( size, 9000 );

                Long count = channelService
                    .retrieveConetentClassCountByParentClassId(
                        Long.valueOf( StringUtil
                            .getLongValue( parentId, -99999 ) ), siteFlag, Long
                            .valueOf( StringUtil.getLongValue( classType, -1 ) ) );

                Page pageInfo = new Page( pageSize, count.intValue(), nextPage );

                this.pageContext.setAttribute(
                    "___system_dispose_page_object___", pageInfo );

                result = channelService
                    .retrieveConetentClassByParentClassId(
                        Long.valueOf( StringUtil
                            .getLongValue( parentId, -99999 ) ),
                        siteFlag,
                        Long.valueOf( StringUtil.getLongValue( classType, -1 ) ),
                        Long.valueOf( pageInfo.getFirstResult() ), Integer
                            .valueOf( pageSize ) );
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
        else if( "true".equals( firstClassMode ) )
        {
            // 取站点第一个ClassId
            List allClassBeanList = channelService
                .retrieveAllClassBeanInfoBySiteFlag( siteBean.getSiteFlag() );

            ContentClassBean firstClassBean = ( ContentClassBean ) allClassBeanList
                .get( 0 );

            result = new ArrayList( 1 );
            result.add( firstClassBean );
        }
        else
        {

            // TODO 无站群
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
                    int nextPage = StringUtil.getIntValue( this.pageContext
                        .getRequest().getParameter( "pn" ), 1 );

                    int pageSize = StringUtil.getIntValue( size, 9000 );

                    Long count = channelService
                        .fetchAllIncludeConetentClassCountByClassID( siteFlag,
                            StringUtil.getBooleanValue( isSpec, false ),
                            Integer.valueOf( StringUtil.getIntValue( classType,
                                -1 ) ) );

                    Page pageInfo = new Page( pageSize, count.intValue(),
                        nextPage );

                    this.pageContext.setAttribute(
                        "___system_dispose_page_object___", pageInfo );

                    result = channelService
                        .fetchAllIncludeConetentClassByClassID( siteFlag,
                            StringUtil.getBooleanValue( isSpec, false ),
                            Integer.valueOf( StringUtil.getIntValue( classType,
                                -1 ) ), Long
                                .valueOf( pageInfo.getFirstResult() ), Integer
                                .valueOf( pageSize ) );
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

    public void setSite( String site )
    {
        this.site = site;
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

    public void setFirstClassMode( String firstClassMode )
    {
        this.firstClassMode = firstClassMode;
    }

    public void setKey( String key )
    {
        this.key = key;
    }

    public void setSize( String size )
    {
        this.size = size;
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
