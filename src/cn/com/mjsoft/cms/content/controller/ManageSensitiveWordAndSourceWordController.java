package cn.com.mjsoft.cms.content.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelFiledInfoBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/content" )
public class ManageSensitiveWordAndSourceWordController
{
    private static Logger log = Logger.getLogger( ManageSensitiveWordAndSourceWordController.class );

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    private static ContentService contentService = ContentService.getInstance();

    private static ChannelService channelService = ChannelService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/importSW.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "批量导入敏感词汇", token = true )
    public String importSensitiveWord( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String sword = ( String ) params.get( "sword" );

        contentService.importSensitiveWord( sword );

        ContentService.SW_REP.clear();

        return "success";

    }

    @RequestMapping( value = "/addSw.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加敏感词汇", token = true )
    public Object addSw( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String sensitive = ( String ) params.get( "sensitiveStr" );

        String replace = ( String ) params.get( "replaceStr" );

        contentService.addNewSensitiveWord( sensitive, replace );

        ContentService.SW_REP.clear();

        /**
         * 更新词库
         */
        ClusterService.exeClusterMasterCMD( "cluster/reloadSW.do", Constant.COMMON.POST );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );
        returnParams.put( "id", params.get( "sId" ) );

        return ServletUtil.redirect( "/core/words/CreateSensitiveWord.jsp", returnParams );

    }

    @RequestMapping( value = "/editSw.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑敏感词汇", token = true )
    public Object editSw( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String sensitive = ( String ) params.get( "sensitiveStr" );

        String replace = ( String ) params.get( "replaceStr" );

        Long sId = StringUtil.getLongValue( ( String ) params.get( "swId" ), -1 );

        contentService.editSensitiveWord( sensitive, replace, sId );

        ContentService.SW_REP.clear();

        /**
         * 更新词库
         */
        ClusterService.exeClusterMasterCMD( "cluster/reloadSW.do", Constant.COMMON.POST );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );
        returnParams.put( "id", params.get( "sId" ) );

        return ServletUtil.redirect( "/core/words/EditSensitiveWord.jsp", returnParams );

    }

    @ResponseBody
    @RequestMapping( value = "/changeUs.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "改变敏感词汇状态", token = true )
    public String changeUs( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        String status = ( String ) params.get( "status" );

        Integer us = null;

        if( "on".equals( status ) )
        {
            us = Constant.COMMON.ON;
        }
        else
        {
            us = Constant.COMMON.OFF;
        }

        contentService.changeSensitiveWorduserStatus( idList, us );

        ContentService.SW_REP.clear();

        /**
         * 更新词库
         */
        ClusterService.exeClusterMasterCMD( "cluster/reloadSW.do", Constant.COMMON.POST );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/deleteSw.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除敏感词", token = true )
    public String deleteSw( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        contentService.deleteSensitiveWord( idList );

        ContentService.SW_REP.clear();

        /**
         * 更新词库
         */
        ClusterService.exeClusterMasterCMD( "cluster/reloadSW.do", Constant.COMMON.POST );

        return "success";

    }

    @RequestMapping( value = "/addCs.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加来源", token = true )
    public Object addCs( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String sourceName = ( String ) params.get( "sourceName" );

        contentService.addNewContentSource( sourceName );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        returnParams.put( "id", params.get( "sId" ) );

        return ServletUtil.redirect( "/core/words/CreateContentSource.jsp", returnParams );

    }

    @RequestMapping( value = "/editCs.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑来源", token = true )
    public Object editCs( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String sourceName = ( String ) params.get( "sourceName" );

        Long sId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "sId" ), -1 ) );

        contentService.editContentSource( sourceName, sId );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        returnParams.put( "id", params.get( "sId" ) );

        return ServletUtil.redirect( "/core/words/EditContentSource.jsp", returnParams );

    }

    @ResponseBody
    @RequestMapping( value = "/deleteCs.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除来源", token = true )
    public String deleteCs( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        contentService.deleteContentSource( idList );

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/disposeCs.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "从采集结果获取来源" )
    public String disposeCs( HttpServletRequest request, HttpServletResponse response )
    {
        contentService.checkAndDisposePickTraceSourceInfo();

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/swLight.do", method = { RequestMethod.POST } )
    public String modelDataHighlight( HttpServletRequest request, HttpServletResponse response )
    {

        Long classId = StringUtil.getLongValue( request.getParameter( "classId" ), -1 );

        ContentClassBean classBean = channelService.retrieveSingleClassBeanInfoByClassId( classId );

        if( classBean != null && classBean.getClassId().longValue() < 0 )
        {
            return null;
        }

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( classBean.getSiteFlag() );

        /**
         * 获取对应数据模型元数据
         */
        DataModelBean modelBean = metaDataService.retrieveSingleDataModelBeanById( classBean
            .getContentType() );

        List<ModelFiledInfoBean> filedBeanList = metaDataService
            .retrieveModelFiledInfoBeanList( classBean.getContentType() );

        ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( classBean.getContentType() );

        if( modelBean == null || filedBeanList.isEmpty() || sqlCodeBean == null )
        {
            return null;
        }

        // 编辑器类型字段
        Set editorFieldSet = ServiceUtil.checkEditorField( filedBeanList );

        Map params = ServletUtil.getRequestDecodeInfo( request, editorFieldSet );

        String code = contentService.disposeModelDataSensitiveWordHighlight( params, filedBeanList );

        return code;
    }
}
