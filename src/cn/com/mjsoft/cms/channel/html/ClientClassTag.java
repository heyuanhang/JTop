package cn.com.mjsoft.cms.channel.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.handler.view.DefaultResultHandler;
import cn.com.mjsoft.framework.web.html.common.AbstractIteratorTag;

public class ClientClassTag extends AbstractIteratorTag
{
    private static final long serialVersionUID = -6514072152516302804L;

    private static Logger log = Logger.getLogger( ClientClassTag.class );

    private static DefaultResultHandler resultHandler = new DefaultResultHandler();

    private ChannelService channelService = ChannelService.getInstance();

    private String flagMode = "false";

    private String test = "false";// 测试是否栏目存在，若不存在直接跳404页

    private String idList = "";// xx,xx,xx形式的id list,支持id和flag

    private String modelId = "-1";

    private String order = "up";

    private String specMode = "false";

    private String specComm = "false";

    private String objName = "Class";// 嵌套使用需要改变名称

    protected String returnPutValueName()
    {
        return objName;
    }

    protected String returnRequestAndPageListAttName()
    {
        return null;
    }

    protected String returnValueRange()
    {
        return "selfRange";
    }

    protected Object returnSingleObject()
    {
        ContentClassBean classBean = null;

        if( "false".equals( flagMode ) )
        {
            Long targetId = Long.valueOf( StringUtil.getLongValue( this.getId(), -1 ) );

            if( targetId < 0 )
            {
                classBean = channelService.retrieveSingleClassBeanInfoByClassFlag( id );
            }
            else
            {
                classBean = channelService.retrieveSingleClassBeanInfoByClassId( targetId );
            }
        }
        else if( "true".equals( flagMode ) )
        {
            classBean = channelService.retrieveSingleClassBeanInfoByClassFlag( id );
        }

        if( "true".equals( test ) && classBean.getClassId().longValue() < 0 )
        {
            HttpServletRequest request = ( HttpServletRequest ) pageContext.getRequest();

            HttpServletResponse response = ( HttpServletResponse ) pageContext.getResponse();

            String requestPath = request.getServletPath();

            int pathDepth = StringUtil.getRepeatCharLength( requestPath, Constant.CONTENT.URL_SEP );

            StringBuffer buf = new StringBuffer();

            for ( int i = 0; i < ( pathDepth - 1 ); i++ )
            {
                buf.append( "../" );
            }

            // 应该可以自定义404页
            buf.append( "common/404/404.jsp" );

            response.setStatus( HttpServletResponse.SC_NOT_FOUND );

            resultHandler
                .resolveCustomDirectResult( buf.toString(), request, response, false, null );

            this.setSkipPage();

        }

        return classBean;
    }

    @SuppressWarnings( "unchecked" )
    protected List returnObjectList()
    {
        // 来自发布逻辑的访问,根据管理站点确定当前站点
        SiteGroupBean siteBean = ( SiteGroupBean ) pageContext.getRequest().getAttribute(
            Constant.CONTENT.HTML_PUB_CURRENT_SITE );

        if( siteBean == null )
        {
            siteBean = ( SiteGroupBean ) this.pageContext.getRequest().getAttribute( "SiteObj" );

            if( siteBean == null )
            {
                // 根据URL来判断站点
                siteBean = SiteGroupService
                    .getCurrentSiteInfoFromWebRequest( ( HttpServletRequest ) this.pageContext
                        .getRequest() );
            }
        }

        List result = Collections.EMPTY_LIST;

        List classBeanList = null;

        Long paramClassId = null;

        if( idList.indexOf( "," ) != -1 )
        {
            // 传入的多个ID
            if( "true".equals( flagMode ) )
            {
                String[] flagArray = StringUtil.split( idList, "," );

                classBeanList = channelService.retrieveClassBeanInfoBySomeFlags( flagArray, "down" );
            }
            else
            {
                classBeanList = channelService.retrieveClassBeanInfoBySomeIds( StringUtil
                    .changeStringToList( idList, "," ), "down" );
            }
        }
        else if( idList.startsWith( "parent:" ) )
        {
            // 直接父栏目
            if( "true".equals( flagMode ) )
            {
                paramClassId = channelService.retrieveSingleClassBeanInfoByClassFlag(
                    StringUtil.replaceString( idList, "parent:", "", false, false ) ).getParent();
            }
            else
            {
                paramClassId = channelService.retrieveSingleClassBeanInfoByClassId(
                    Long.valueOf( StringUtil.getLongValue( StringUtil.replaceString( idList,
                        "parent:", "", false, false ), -1 ) ) ).getParent();
            }

            classBeanList = new ArrayList( 2 );

            classBeanList.add( channelService.retrieveSingleClassBeanInfoByClassId( paramClassId ) );

        }
        else if( idList.startsWith( "child:" ) )
        {
            // 直接孩子

            if( idList.equals( "child:root" ) )
            {
                // 根栏目
                paramClassId = Long.valueOf( -9999 );
            }
            else
            {
                if( "true".equals( flagMode ) )
                {
                    paramClassId = channelService.retrieveSingleClassBeanInfoByClassFlag(
                        StringUtil.replaceString( idList, "child:", "", false, false ) )
                        .getClassId();
                }
                else
                {
                    paramClassId = Long.valueOf( StringUtil.getLongValue( StringUtil.replaceString(
                        idList, "child:", "", false, false ), -1 ) );
                }
            }

            if( "true".equals( specMode ) )
            {
                classBeanList = channelService.retrieveConetentClassBeanSpecModeByParentClassId(
                    paramClassId, Long.valueOf( StringUtil.getLongValue( modelId, -1 ) ), specComm,
                    siteBean.getSiteFlag(), order );
            }
            else
            {
                classBeanList = channelService.retrieveConetentClassBeanNotSpecByParentClassId(
                    paramClassId, Long.valueOf( StringUtil.getLongValue( modelId, -1 ) ), siteBean
                        .getSiteFlag(), order );
            }

        }
        else if( idList.startsWith( "all:child:" ) )
        {
            // 所有孩子
            String linear = null;

            if( idList.equals( "all:child:root" ) )
            {
                // 根栏目
                linear = "root";
            }
            else
            {
                if( "true".equals( flagMode ) )
                {
                    linear = channelService.retrieveSingleClassBeanInfoByClassFlag(
                        StringUtil.replaceString( idList, "all:child:", "", false, false ) )
                        .getLinearOrderFlag();
                }
                else
                {
                    linear = channelService.retrieveSingleClassBeanInfoByClassId(
                        Long.valueOf( StringUtil.getLongValue( StringUtil.replaceString( idList,
                            "all:child:", "", false, false ), -1 ) ) ).getLinearOrderFlag();

                }
            }

            // 不支持专题模式
            if( !"true".equals( specMode ) && StringUtil.isStringNotNull( linear ) )
            {
                classBeanList = channelService
                    .retrieveAllChildConetentClassBeanNotSpecByParentLinear( linear, siteBean
                        .getSiteFlag(), order );
            }

        }
        else if( idList.startsWith( "bro:" ) )
        {
            if( "true".equals( flagMode ) )
            {
                paramClassId = channelService.retrieveSingleClassBeanInfoByClassFlag(
                    StringUtil.replaceString( idList, "bro:", "", false, false ) ).getClassId();
            }
            else
            {
                paramClassId = Long.valueOf( StringUtil.getLongValue( StringUtil.replaceString(
                    idList, "bro:", "", false, false ), -1 ) );
            }

            ContentClassBean currentClassBean = channelService
                .retrieveSingleClassBeanInfoByClassId( paramClassId );

            if( "true".equals( specMode ) )
            {
                classBeanList = channelService.retrieveConetentClassBeanSpecModeByParentClassId(
                    currentClassBean.getParent(), Long.valueOf( StringUtil.getLongValue( modelId,
                        -1 ) ), specComm, siteBean.getSiteFlag(), order );
            }
            else
            {
                classBeanList = channelService.retrieveConetentClassBeanNotSpecByParentClassId(
                    currentClassBean.getParent(), Long.valueOf( StringUtil.getLongValue( modelId,
                        -1 ) ), siteBean.getSiteFlag(), order );
            }

        }

        result = classBeanList;

        log.debug( "查询出的栏目列表:" + result );

        return result;
    }

    public void setFlag( String flag )
    {
        setToSingleMode();
    }

    protected void initTag()
    {

    }

    public void setFlagMode( String flagMode )
    {
        this.flagMode = flagMode;
    }

    public void setIdList( String idList )
    {
        this.idList = idList;
    }

    public void setModelId( String modelId )
    {
        this.modelId = modelId;
    }

    public void setOrder( String order )
    {
        this.order = order;
    }

    public void setSpecMode( String specMode )
    {
        this.specMode = specMode;
    }

    public void setSpecComm( String specComm )
    {
        this.specComm = specComm;
    }

    public void setObjName( String objName )
    {
        this.objName = objName;
    }

    public void setTest( String test )
    {
        this.test = test;
    }

}
