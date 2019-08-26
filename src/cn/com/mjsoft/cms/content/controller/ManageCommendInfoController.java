package cn.com.mjsoft.cms.content.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.content.bean.ContentCommendPushInfoBean;
import cn.com.mjsoft.cms.content.dao.vo.ContentCommendPushInfo;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/content" )
public class ManageCommendInfoController
{

    private static ContentService contentService = ContentService.getInstance();

    private static ChannelService channelService = ChannelService.getInstance();

    @RequestMapping( value = { "/addSingleCommendInfo.do", "/addSingleSpecInfo.do" }, method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加区块内容", token = true )
    public ModelAndView addSingleCommendInfo( HttpServletRequest request,
        HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        ContentCommendPushInfo commInfo = ( ContentCommendPushInfo ) ServletUtil.getValueObject(
            request, ContentCommendPushInfo.class );

        String flag = ( String ) params.get( "flag" );

        if( request.getServletPath().endsWith( "addSingleSpecInfo.do" ) )
        {
            // 检查是否spec,非spec不允许调用此cmd

            Long typeId = commInfo.getCommendTypeId();

            if( typeId == null )
            {
                typeId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "typeId" ),
                    -1 ) );
            }

            ContentCommendTypeBean ctBean = channelService
                .retrieveSingleContentCommendTypeBeanByTypeId( typeId );

            if( ctBean == null || Constant.COMMON.OFF.equals( ctBean.getIsSpec() ) )
            {
                return null;
            }

        }

        Long rowFlag = Long.valueOf( StringUtil.getLongValue(
            ( String ) params.get( "infoColumn" ), 1 ) );

        Integer infoRow = Integer.valueOf( StringUtil.getIntValue( ( String ) params
            .get( "infoRow" ), 1 ) );

        boolean inCol = StringUtil.getBooleanValue( ( String ) params.get( "inCol" ), false );

        // 设定推荐人
        Auth sysAuth = SecuritySessionKeeper.getSecuritySession().getAuth();

        commInfo.setCommendMan( ( String ) sysAuth.getApellation() );

        // 设定站点
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        commInfo.setSiteFlag( site.getSiteFlag() );

        contentService.addSingleCommendInfo( params, commInfo, rowFlag, infoRow, inCol, site
            .getSiteFlag(), flag );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );
        returnParams.put( "classId", commInfo.getClassId() );
        returnParams.put( "typeId", ( String ) params.get( "typeId" ) );

        if( "true".equals( params.get( "spec" ) ) )
        {
            return ServletUtil.redirect( "/core/content/special/AddSpecialInfo.jsp", returnParams );
        }

        return ServletUtil.redirect( "/core/content/AddCommendInfo.jsp", returnParams );

    }

    @RequestMapping( value = { "/editSingleCommendInfo.do", "/editSingleSpecInfo.do" }, method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑区块内容", token = true )
    public ModelAndView editSingleCommendInfo( HttpServletRequest request,
        HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        ContentCommendPushInfo commInfo = ( ContentCommendPushInfo ) ServletUtil.getValueObject(
            request, ContentCommendPushInfo.class );

        if( request.getServletPath().endsWith( "editSingleSpecInfo.do" ) )
        {
            // 检查是否spec,非spec不允许调用此cmd

            Long typeId = commInfo.getCommendTypeId();

            if( typeId == null )
            {
                typeId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "typeId" ),
                    -1 ) );
            }

            ContentCommendTypeBean ctBean = channelService
                .retrieveSingleContentCommendTypeBeanByTypeId( typeId );

            if( ctBean == null || Constant.COMMON.OFF.equals( ctBean.getIsSpec() ) )
            {
                return null;
            }

        }

        contentService.editSingleCommendInfo( params, commInfo );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );
        returnParams.put( "classId", commInfo.getClassId() );
        returnParams.put( "typeId", ( String ) params.get( "typeId" ) );

        if( "true".equals( params.get( "spec" ) ) )
        {
            return ServletUtil.redirect( "/core/content/special/EditSpecialInfo.jsp", returnParams );
        }

        return ServletUtil.redirect( "/core/content/EditCommendInfo.jsp", returnParams );

    }

    @ResponseBody
    @RequestMapping( value = { "/addMuptiCommendInfo.do", "/addMuptiSpecInfo.do" }, method = { RequestMethod.POST } )
    @ActionInfo( traceName = "批量添加区块内容", token = true )
    public Object addMuptiCommendInfo( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        ContentCommendPushInfo commInfo = ( ContentCommendPushInfo ) ServletUtil.getValueObject(
            request, ContentCommendPushInfo.class );

        String flag = ( String ) params.get( "flag" );

        if( request.getServletPath().endsWith( "addMuptiSpecInfo.do" ) )
        {
            // 检查是否spec,非spec不允许调用此cmd

            Long typeId = commInfo.getCommendTypeId();

            if( typeId == null )
            {
                typeId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "typeId" ),
                    -1 ) );
            }

            ContentCommendTypeBean ctBean = channelService
                .retrieveSingleContentCommendTypeBeanByTypeId( typeId );

            if( ctBean == null || Constant.COMMON.OFF.equals( ctBean.getIsSpec() ) )
            {
                return null;
            }

        }

        String commFlag = ( String ) params.get( "commFlag" );

        List contentIdList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        String action = ( String ) params.get( "action" );

        // 推荐人
        Auth sysAuth = SecuritySessionKeeper.getSecuritySession().getAuth();

        // 站点
        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        contentService.addMutiCommendInfoForContent( commFlag, contentIdList, ( String ) sysAuth
            .getApellation(), site.getSiteFlag(), flag, action );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/commendContent.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "推荐区块内容", token = true )
    public Object commendContent( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List contentIdArrayList = StringUtil.changeStringToList( ( String ) params
            .get( "contentIds" ), "," );

        List commTypeIdArrayList = StringUtil.changeStringToList( ( String ) params.get( "ids" ),
            "," );

        boolean isSpec = StringUtil.getBooleanValue( ( String ) params.get( "isSpec" ), false );

        contentService.commendContentInfo( contentIdArrayList, commTypeIdArrayList, isSpec );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = { "/deleteCommRow.do", "/deleteSpecRow.do" }, method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除区块内容", token = true )
    public Object deleteCommRow( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );
        String commFlag = ( String ) params.get( "commFlag" );

        if( request.getServletPath().endsWith( "deleteSpecRow.do" ) )
        {
            // 检查是否spec,非spec不允许调用此cmd

            Long typeId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "typeId" ),
                -1 ) );

            ContentCommendTypeBean ctBean = channelService
                .retrieveSingleContentCommendTypeBeanByTypeId( typeId );

            if( ctBean == null || Constant.COMMON.OFF.equals( ctBean.getIsSpec() ) )
            {
                return null;
            }

        }

        int deleteMode = StringUtil.getIntValue( ( String ) params.get( "mode" ), -1 );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( deleteMode == 1 )// 行模式
        {
            String flag = ( String ) params.get( "all" );

            if( "true".equals( flag ) )
            {
                contentService.deleteCommendContentColumnInfo( commFlag, null, site.getSiteFlag() );
            }
            else
            {
                List rowFlagArrayList = StringUtil.changeStringToList( ( String ) params
                    .get( "rowFlag" ), "," );

                contentService.deleteCommendContentColumnInfo( commFlag, rowFlagArrayList, site
                    .getSiteFlag() );
            }

        }
        else if( deleteMode == 2 )// 行中删除某列
        {
            List infoIdArrayList = StringUtil.changeStringToList( ( String ) params.get( "ids" ),
                "," );

            Long rowFlag = Long.valueOf( StringUtil.getLongValue(
                ( String ) params.get( "rowFlag" ), -1 ) );

            contentService.deleteCommendContentRowInfo( commFlag, rowFlag, infoIdArrayList, site
                .getSiteFlag() );

        }

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = { "/sortCommendInfo.do", "/sortSpecInfo.do" }, method = { RequestMethod.POST } )
    @ActionInfo( traceName = "排序区块内容", token = true )
    public Object sortCommendInfo( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        if( request.getServletPath().endsWith( "sortSpecInfo" ) )
        {
            // 检查是否spec,非spec不允许调用此cmd

            Long typeId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "typeId" ),
                -1 ) );

            ContentCommendTypeBean ctBean = channelService
                .retrieveSingleContentCommendTypeBeanByTypeId( typeId );

            if( ctBean == null || Constant.COMMON.OFF.equals( ctBean.getIsSpec() ) )
            {
                return "success";
            }

        }

        Long infoId = Long
            .valueOf( StringUtil.getLongValue( ( String ) params.get( "infoId" ), -1 ) );

        String sortFlag = ( String ) params.get( "flag" );

        String type = ( String ) params.get( "type" );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( "row".equals( type ) )
        {
            if( !Constant.CONTENT.SORT_LEFT.equals( sortFlag )
                && !Constant.CONTENT.SORT_RIGHT.equals( sortFlag ) )
            {
                return "error";
            }

            contentService.sortCommendContentRowInfo( infoId, sortFlag );
        }
        else if( "column".equals( type ) )
        {
            String commFlag = ( String ) params.get( "commFlag" );

            Long rowFlag = Long.valueOf( StringUtil.getLongValue(
                ( String ) params.get( "rowFlag" ), 0 ) );

            String direct = ( String ) params.get( "direct" );

            Integer count = Integer.valueOf( StringUtil.getIntValue( ( String ) params
                .get( "count" ), 0 ) );

            contentService.sortCommendContentColumnInfo( direct, count, commFlag, rowFlag, site
                .getSiteFlag() );
        }

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/getCommRowBeanJson.do", method = { RequestMethod.POST,
        RequestMethod.GET } )
    public Object getCommRowBeanJson( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long rowFlag = StringUtil.getLongValue( ( String ) params.get( "rowFlag" ), -1 );

        String commFlag = ( String ) params.get( "commFlag" );

        List jsonList = new ArrayList();

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        List commendBeanRowList = contentService.retrieveCommendContentRowInfoByRowFlag( rowFlag,
            commFlag, site.getSiteFlag() );

        ContentCommendPushInfoBean bean = null;

        for ( int i = 0; i < commendBeanRowList.size(); i++ )
        {
            bean = ( ContentCommendPushInfoBean ) commendBeanRowList.get( i );

            bean.setUrl( "" );
            bean.setSummary( "" );

            jsonList.add( bean );
        }

        return jsonList;

    }

}
