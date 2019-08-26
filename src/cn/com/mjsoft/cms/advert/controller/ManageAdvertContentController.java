package cn.com.mjsoft.cms.advert.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.advert.bean.AdvertConfigBean;
import cn.com.mjsoft.cms.advert.bean.AdvertContentBean;
import cn.com.mjsoft.cms.advert.dao.vo.AdvertContent;
import cn.com.mjsoft.cms.advert.service.AdvertService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/trevda" )
public class ManageAdvertContentController
{
    private static AdvertService advertService = AdvertService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/changeDaStatus.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "改动广告状态", token = true )
    public String changeAdStatus( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String flag = ( String ) params.get( "flag" );

        Integer status = Constant.COMMON.ON;

        if( "off".equals( flag ) )
        {
            status = Constant.COMMON.OFF;
        }

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        advertService.changeAdvertStatus( idList, status );

        return "success";
    }

    @RequestMapping( value = "/createTrevda.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "创建广告", token = true )
    public Object createAdvert( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        AdvertContent advert = ( AdvertContent ) ServletUtil.getValueObject( request,
            AdvertContent.class );

        if( advert.getShowStartDate() == null )
        {
            advert.setShowStartDate( DateAndTimeUtil.getTodayTimestamp() );
        }

        if( advert.getShowEndDate() == null )
        {
            advert.setShowEndDate( Constant.CONTENT.MAX_DATE );
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        advert.setSiteId( site.getSiteId() );

        Map paramMap = new HashMap();

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth != null )
        {
            advert.setCreator( auth.getApellation().toString() );
        }

        // 扩展模型信息
        AdvertConfigBean acb = advertService.retrieveSingleAdvertConnfigBeanByPosId( advert
            .getPosId() );

        if( acb == null )
        {
            return null;
        }

        // 广告内容参数
        List contentFiledBeanList = metaDataService.retrieveModelFiledInfoBeanList( acb
            .getContentModelId() );

        ModelPersistenceMySqlCodeBean contentSqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( acb.getContentModelId() );

        DataModelBean contentModel = metaDataService.retrieveSingleDataModelBeanById( acb
            .getContentModelId() );

        // 广告版位参数
        List posFiledBeanList = metaDataService
            .retrieveModelFiledInfoBeanList( acb.getPosModelId() );

        ModelPersistenceMySqlCodeBean posSqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( acb.getPosModelId() );

        DataModelBean posModel = metaDataService.retrieveSingleDataModelBeanById( acb
            .getPosModelId() );

        advertService.addNewAdvertContentAndExtendParamValue( advert, contentModel,
            contentFiledBeanList, contentSqlCodeBean, posModel, posFiledBeanList, posSqlCodeBean,
            params );

        paramMap.put( "fromFlow", Boolean.TRUE );

        paramMap.put( "advertId", params.get( "advertId" ) );

        return ServletUtil.redirect( "/core/trevda/CreateTrevdaContent.jsp", paramMap );
    }

    @RequestMapping( value = "/editTrevda.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑广告", token = true )
    public Object editAdvert( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        AdvertContent advert = ( AdvertContent ) ServletUtil.getValueObject( request,
            AdvertContent.class );

        if( advert.getShowStartDate() == null )
        {
            advert.setShowStartDate( DateAndTimeUtil.getTodayTimestamp() );
        }

        if( advert.getShowEndDate() == null )
        {
            advert.setShowEndDate( Constant.CONTENT.MAX_DATE );
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        advert.setSiteId( site.getSiteId() );

        Map paramMap = new HashMap();

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth != null )
        {
            advert.setCreator( auth.getApellation().toString() );
        }

        // 扩展模型信息
        AdvertConfigBean acb = advertService.retrieveSingleAdvertConnfigBeanByPosId( advert
            .getPosId() );

        if( acb == null )
        {
            return null;
        }

        // 广告内容参数
        List contentFiledBeanList = metaDataService.retrieveModelFiledInfoBeanList( acb
            .getContentModelId() );

        ModelPersistenceMySqlCodeBean contentSqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( acb.getContentModelId() );

        DataModelBean contentModel = metaDataService.retrieveSingleDataModelBeanById( acb
            .getContentModelId() );

        // 广告版位参数
        List posFiledBeanList = metaDataService
            .retrieveModelFiledInfoBeanList( acb.getPosModelId() );

        ModelPersistenceMySqlCodeBean posSqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( acb.getPosModelId() );

        DataModelBean posModel = metaDataService.retrieveSingleDataModelBeanById( acb
            .getPosModelId() );

        advertService.editAdvertContentAndExtendParamValue( advert, contentModel,
            contentFiledBeanList, contentSqlCodeBean, posModel, posFiledBeanList, posSqlCodeBean,
            params );

        paramMap.put( "fromFlow", Boolean.TRUE );

        paramMap.put( "advertId", params.get( "advertId" ) );

        return ServletUtil.redirect( "/core/trevda/EditTrevdaContent.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/showTrevda.do", method = { RequestMethod.POST } )
    public Object showAdvert( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        String posFlag = ( String ) params.get( "posFlag" );

        AdvertContentBean adBean = null;

        adBean = advertService.retrieveSingleAdvertContentBeanByPosIdOrderByImportance( posFlag );

        String advertCode = Constant.COMMON.EMPTY_STRING;

        if( adBean != null )
        {
            advertCode = adBean.getAdvertCode();
        }

        return advertCode;

    }

    @ResponseBody
    @RequestMapping( value = "/deleteTrevda.do", method = { RequestMethod.POST } )
    public Object deleteAdvert( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "ids" );

        List idList = StringUtil.changeStringToList( ids, "," );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        advertService.deleteAdvertContent( idList, site );

        return "success";

    }

}
