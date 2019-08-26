package cn.com.mjsoft.cms.metadata.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.content.dao.ContentDao;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.dao.vo.DataModel;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/metadata" )
public class ManageDataModelController
{
    private static MetaDataService metaDataService = MetaDataService.getInstance();

    @RequestMapping( value = "/addDataModel.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加数据模型", token = true )
    public ModelAndView addDataModel( HttpServletRequest request, HttpServletResponse response )
    {

        DataModel dataModel = ( DataModel ) ServletUtil.getValueObject( request, DataModel.class );

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        dataModel.setSiteId( siteBean.getSiteId() );

        metaDataService.createDataModel( dataModel );

        /**
         * 更新内容数据缓存
         */
        ContentDao.releaseAllCountCache();
        ContentService.releaseContentCache();

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        paramMap.put( "modelId", dataModel.getDataModelId() );

        return ServletUtil.redirect( "/core/metadata/CreateDataModel.jsp", paramMap );

    }

    @RequestMapping( value = "/editDataModel.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑数据模型", token = true )
    public ModelAndView editDataModel( HttpServletRequest request, HttpServletResponse response )
    {

        DataModel dataModel = ( DataModel ) ServletUtil.getValueObject( request, DataModel.class );

        if( StringUtil.isStringNull( dataModel.getIco() ) )
        {
            dataModel.setIco( "document.png" );
        }

        metaDataService.editDataModel( dataModel );

        /**
         * 更新内容数据缓存
         */
        ContentDao.releaseAllCountCache();
        ContentService.releaseContentCache();

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        paramMap.put( "modelId", dataModel.getDataModelId() );

        return ServletUtil.redirect( "/core/metadata/EditDataModel.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteSystemDataModel.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除数据模型", token = true )
    public String deleteSystemDataModel( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        List<String> ids = StringUtil.changeStringToList( ( String ) params.get( "modelId" ), "," );

        Long modelId = null;

        for ( String id : ids )
        {
            modelId = Long.valueOf( StringUtil.getLongValue( id, -1 ) );

            metaDataService.deleteDataModelAllInfo( modelId, site.getSiteId() );
        }

        return "success";
    }

}
