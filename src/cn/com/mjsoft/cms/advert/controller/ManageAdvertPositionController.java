package cn.com.mjsoft.cms.advert.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.mjsoft.cms.advert.bean.AdvertConfigBean;
import cn.com.mjsoft.cms.advert.bean.AdvertContentBean;
import cn.com.mjsoft.cms.advert.dao.vo.AdvertPosition;
import cn.com.mjsoft.cms.advert.service.AdvertService;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/trevda" )
public class ManageAdvertPositionController
{
    private static AdvertService advertService = AdvertService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    @RequestMapping( value = "/createTrevdaPos.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加广告位", token = true )
    public Object createAdvertPos( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        Map params = ServletUtil.getRequestInfo( request );

        AdvertPosition position = ( AdvertPosition ) ServletUtil.getValueObject( request,
            AdvertPosition.class );

        Map paramMap = new HashMap();

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        position.setSiteId( site.getSiteId() );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth != null )
        {
            position.setCreator( auth.getApellation().toString() );
        }

        // 扩展模型信息

        AdvertConfigBean acb = advertService.retrieveSingleAdvertConfigBeanByConfigId( position
            .getConfigId() );

        if( acb == null )
        {
            return null;
        }

        // 广告版位参数
        List posFiledBeanList = metaDataService
            .retrieveModelFiledInfoBeanList( acb.getPosModelId() );

        ModelPersistenceMySqlCodeBean posSqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( acb.getPosModelId() );

        DataModelBean posModel = metaDataService.retrieveSingleDataModelBeanById( acb
            .getPosModelId() );

        advertService.addNewAdvertPositionAndExtendParamValue( position, posModel,
            posFiledBeanList, posSqlCodeBean, params );

        paramMap.put( "fromFlow", Boolean.TRUE );
        paramMap.put( "posId", params.get( "posId" ) );

        return ServletUtil.redirect( "/core/trevda/CreateTrevdaPosition.jsp", paramMap );
    }

    @RequestMapping( value = "/editTrevdaPos.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑广告位", token = true )
    public Object editAdvertPos( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        Map params = ServletUtil.getRequestInfo( request );

        AdvertPosition position = ( AdvertPosition ) ServletUtil.getValueObject( request,
            AdvertPosition.class );

        Map paramMap = new HashMap();

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        position.setSiteId( site.getSiteId() );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth != null )
        {
            position.setCreator( auth.getApellation().toString() );
        }

        // 扩展模型信息

        AdvertConfigBean acb = advertService.retrieveSingleAdvertConfigBeanByConfigId( position
            .getConfigId() );

        if( acb == null )
        {
            return "success";
        }

        // 广告版位参数
        List posFiledBeanList = metaDataService
            .retrieveModelFiledInfoBeanList( acb.getPosModelId() );

        ModelPersistenceMySqlCodeBean posSqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( acb.getPosModelId() );

        DataModelBean posModel = metaDataService.retrieveSingleDataModelBeanById( acb
            .getPosModelId() );

        // 广告内容参数
        List contentFiledBeanList = metaDataService.retrieveModelFiledInfoBeanList( acb
            .getContentModelId() );

        ModelPersistenceMySqlCodeBean contentSqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( acb.getContentModelId() );

        DataModelBean contentModel = metaDataService.retrieveSingleDataModelBeanById( acb
            .getContentModelId() );

        advertService.editAdvertPositionAndExtendParamValue( position, posModel, posFiledBeanList,
            posSqlCodeBean, contentModel, contentFiledBeanList, contentSqlCodeBean, params );

        paramMap.put( "fromFlow", Boolean.TRUE );
        paramMap.put( "posId", params.get( "posId" ) );

        return ServletUtil.redirect( "/core/trevda/EditTrevdaPosition.jsp", paramMap );
    }

    @RequestMapping( value = "/deleteTrevdaPos.do", method = { RequestMethod.POST, RequestMethod.GET } )
    @ActionInfo( traceName = "删除广告位", token = true )
    public Object deleteAdvertPos( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long posId = StringUtil.getLongValue( ( String ) params.get( "posId" ), -1 );

        List adList = advertService.retrieveAdvertContentBeanListByPosId( posId );

        AdvertContentBean bean = null;

        StringBuffer buf = new StringBuffer();

        for ( int i = 0; i < adList.size(); i++ )
        {
            bean = ( AdvertContentBean ) adList.get( i );

            buf.append( bean.getAdvertId() + "," );
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        // 先删除所属广告
        advertService.deleteAdvertContent( StringUtil.changeStringToList( buf.toString(), "," ),
            site );

        advertService.deleteAdvertPositionAndParamValue( posId, site );

        return ServletUtil.redirect( "/core/trevda/ManageTrevdaPosition.jsp" );

    }

}
