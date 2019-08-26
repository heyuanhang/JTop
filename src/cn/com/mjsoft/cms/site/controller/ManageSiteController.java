package cn.com.mjsoft.cms.site.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.dao.SiteGroupDao;
import cn.com.mjsoft.cms.site.dao.vo.SiteGroup;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.cms.weixin.service.WeixinService;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/site" )
public class ManageSiteController
{
    private static SiteGroupService siteService = SiteGroupService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    @RequestMapping( value = "/addSite.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加站群节点", token = true )
    public ModelAndView addSite( HttpServletRequest request, HttpServletResponse response )
    {
        SiteGroup siteGroup = ( SiteGroup ) ServletUtil.getValueObjectDecode( request,
            SiteGroup.class );

        siteService.createNewSiteNode( siteGroup );

        // 当从无站点到增加第一站点的情况，需要更新当前主管理的登录站点，
        List siteBeanList = siteService.retrieveAllSiteBean();

        if( siteBeanList.size() == 1 )
        {
            SiteGroupBean site = ( SiteGroupBean ) siteBeanList.get( 0 );

            SecuritySessionKeeper.getSecuritySession().setCurrentLoginSiteInfo( site.getSiteId() );

        }

        updateCache();

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/channel/CreateSiteNode.jsp", returnParams );
    }

    @RequestMapping( value = "/editSiteBaseInfo.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑站群节点", token = true )
    public ModelAndView editSiteBaseInfo( HttpServletRequest request, HttpServletResponse response )
    {
        SiteGroup siteGroup = ( SiteGroup ) ServletUtil.getValueObjectDecode( request,
            SiteGroup.class );

        siteService.updateSiteNodeBaseInfo( siteGroup );

        updateCache();

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/channel/EditSiteNode.jsp", returnParams );
    }

    @ResponseBody
    @RequestMapping( value = "/sortSite.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "站群节点排序", token = true )
    public String sortSite( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        siteService.sortSiteNode( params );

        updateCache();

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/editSite.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑站点配置", token = true )
    public String editSite( HttpServletRequest request, HttpServletResponse response )
    {
        // 由于存在一些字段允许html,所以不能在这里先使用getHttpRequestSnapshot(),故使用普通方式获取classId
        Long extModelId = Long.valueOf( StringUtil.getLongValue( ( String ) request
            .getParameter( "extDataModelId" ), -1 ) );

        List filedBeanList = metaDataService.retrieveModelFiledInfoBeanList( extModelId );

        // 允许html的编辑器字段
        Set htmlFieldSet = ServiceUtil.checkEditorField( filedBeanList );

        htmlFieldSet.add( "siteUrl" );
        htmlFieldSet.add( "homePageTemplate" );

        htmlFieldSet.add( "regMailText" );
        htmlFieldSet.add( "resetPwText" );

        Map params = ServletUtil.getRequestDecodeInfo( request, htmlFieldSet );

        SiteGroup siteGroup = ( SiteGroup ) ServletUtil.getValueObjectDecode( request,
            SiteGroup.class );

        // 处理图片数据

        Long liResId = Long.valueOf( StringUtil.getLongValue( siteGroup.getLogoImage(), -1 ) );

        ServiceUtil.disposeOldImageInfo( liResId, "logoImage", params );

        siteGroup.setLogoImage( ServiceUtil.disposeSingleImageInfo( liResId ) );

        Long imResId = Long.valueOf( StringUtil.getLongValue( siteGroup.getImageMark(), -1 ) );

        ServiceUtil.disposeOldImageInfo( imResId, "imageMark", params );

        siteGroup.setImageMark( ServiceUtil.disposeSingleImageInfo( imResId ) );

        // 扩展模型信息
        ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( extModelId );

        DataModelBean model = metaDataService.retrieveSingleDataModelBeanById( siteGroup
            .getExtDataModelId() );

        siteService.updateSiteInfoBySiteId( siteGroup, model, filedBeanList, sqlCodeBean, params );

        // 模型默认模板
        siteService.disposeDefaultModelTemplate( siteGroup.getSiteId(), params );

        WeixinService.getInstance().initSitGroupWXApiToken();

        updateCache();

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteSiteGroup.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除站群节点", token = true )
    public String deleteSiteGroup( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        siteService.deleteSiteGroupNode( idList );

        // 需要更新当前主管理的登录站点，其他已经登录管理员不用关注
        List siteBeanList = siteService.retrieveAllSiteBean();

        if( !siteBeanList.isEmpty() )
        {
            SiteGroupBean site = ( SiteGroupBean ) siteBeanList.get( 0 );

            SecuritySessionKeeper.getSecuritySession().setCurrentLoginSiteInfo( site.getSiteId() );

        }
        else
        {
            if( !JtRuntime.cmsServer.getClusterMode() )
            {
                SecuritySessionKeeper.getSecuritySession().setCurrentLoginSiteInfo(
                    Long.valueOf( -1 ) );
            }

        }

        updateCache();

        return "success";
    }

    /**
     * 更新站群相关缓存信息
     * 
     */
    private void updateCache()
    {

        WeixinService.getInstance().initSitGroupWXApiToken();

        SiteGroupDao.clearPSCache();
    }
}
