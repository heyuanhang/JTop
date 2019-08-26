package cn.com.mjsoft.cms.content.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.ObjectUtility;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/content" )
public class ManageDefFormDataController
{

    private static ContentService contentService = ContentService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/editFormData.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑表单内容", token = true )
    public String editFormData( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        // 由于存在一些字段允许html,所以不能在这里先使用getHttpRequestSnapshot(),故使用普通方式获取modelId
        Long modelId = Long.valueOf( StringUtil.getLongValue( ( String ) request
            .getParameter( "modelId" ), -1 ) );

        /**
         * 获取对应数据模型元数据
         */
        DataModelBean modelBean = metaDataService.retrieveSingleDataModelBeanById( modelId );
        List filedBeanList = metaDataService.retrieveModelFiledInfoBeanList( modelId );

        ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( modelId );

        if( modelBean == null || filedBeanList.isEmpty() || sqlCodeBean == null )
        {
            return "-1";// 表单模型元数据信息不全
        }

        if( Constant.COMMON.OFF.equals( modelBean.getIsManageEdit() ) )
        {
            return "-2";// 不允许管理员改动表单数据
        }

        Map params = ServletUtil.getRequestDecodeInfo( request, ServiceUtil
            .checkEditorField( filedBeanList ) );

        String vbClassName = modelBean.getValidateBehavior();

        // 进行自定义验证行为
        String errorJSON = null;
        if( StringUtil.isStringNotNull( vbClassName ) )
        {
            Class classObj = ObjectUtility.getClassInstance( vbClassName );

            if( classObj != null )
            {
                Object valiedateBehavior = classObj.newInstance();

                if( valiedateBehavior instanceof Behavior )
                {
                    errorJSON = ( String ) ( ( Behavior ) valiedateBehavior ).operation( params,
                        new Object[] { modelBean, filedBeanList, sqlCodeBean } );
                }
            }
        }

        if( StringUtil.isStringNotNull( errorJSON ) )
        {
            return errorJSON;
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        /**
         * 文本特殊处理
         */
        Set editorFieldSet = ServiceUtil.checkEditorField( filedBeanList );

        Iterator editorFieldIter = editorFieldSet.iterator();

        String fieldName = null;

        String text = null;

        while ( editorFieldIter.hasNext() )
        {
            fieldName = ( String ) editorFieldIter.next();

            text = ( String ) params.get( fieldName );

            // html白名单
            text = ServiceUtil.cleanEditorHtmlByWhiteRule( text );

            // 站外链接
            if( Constant.COMMON.ON.equals( site.getDeleteOutLink() ) )
            {
                text = contentService.deleteContentTextOutHref( text, site );
            }

            params.put( fieldName, text );
        }

        // 进行前置参数处理行为

        vbClassName = modelBean.getBeforeBehavior();

        if( StringUtil.isStringNotNull( vbClassName ) )
        {
            Class classObj = ObjectUtility.getClassInstance( vbClassName );

            if( classObj != null )
            {
                Object valiedateBehavior = classObj.newInstance();

                if( valiedateBehavior instanceof Behavior )
                {
                    ( ( Behavior ) valiedateBehavior ).operation( params, new Object[] { modelBean,
                        filedBeanList, sqlCodeBean } );
                }
            }
        }

        contentService.addOrEditDefineFormData( site, params, true );

        // 进行后置参数处理行为

        vbClassName = modelBean.getAfterBehavior();

        if( StringUtil.isStringNotNull( vbClassName ) )
        {

            Class classObj = ObjectUtility.getClassInstance( vbClassName );

            if( classObj != null )
            {
                Object valiedateBehavior = classObj.newInstance();

                if( valiedateBehavior instanceof Behavior )
                {
                    ( ( Behavior ) valiedateBehavior ).operation( params, new Object[] { modelBean,
                        filedBeanList, sqlCodeBean } );
                }
            }
        }

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteFormData.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除表单内容", token = true )
    public String deleteFormData( HttpServletRequest request, HttpServletResponse response )

    {
        Map params = ServletUtil.getRequestInfo( request );

        Long modelId = StringUtil.getLongValue( ( String ) params.get( "modelId" ), -1 );

        String mode = ( String ) params.get( "mode" );

        String ids = ( String ) params.get( "ids" );

        if( !SecuritySessionKeeper.getSecuritySession().isManager() )
        {
            return "fail";
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( "all".equals( mode ) )
        {
            contentService.deleteAllDefFormContent( site, modelId );

        }
        else
        {
            contentService.deleteDefFormContent( site, StringUtil.changeStringToList( ids, "," ),
                modelId );

        }

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/censorFormData.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "审核表单内容", token = true )
    public String censorFormData( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String censor = ( String ) params.get( "censorState" );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        Long modelId = StringUtil.getLongValue( ( String ) params.get( "modelId" ), -1 );

        if( "true".equals( censor ) )
        {
            metaDataService.changeFormDataStatus( idList, modelId, Integer.valueOf( 1 ) );

        }
        else if( "false".equals( censor ) )
        {
            metaDataService.changeFormDataStatus( idList, modelId, Integer.valueOf( 0 ) );
        }

        return "success";

    }

    @RequestMapping( value = "/report.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public void report( HttpServletRequest request, HttpServletResponse response )
        throws UnsupportedEncodingException
    {
        Map params = ServletUtil.getRequestInfo( request );

        String query = SystemSafeCharUtil.decodeFromWeb( ( String ) params.get( "sq" ) );

        List idsList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        Long modelId = StringUtil.getLongValue( ( String ) params.get( "modelId" ), -1 );

        String fileFullPath = contentService.genFromDataReport( idsList, modelId, query );

        File file = new File( fileFullPath );

        // 文件类型响应报文头
        response.reset();// 强制清除response内部信息
        response.setContentType( "application/octet-stream" );
        response.setHeader( "Content-Disposition", "attachment; filename="
            + new String( file.getName().getBytes( JtRuntime.cmsServer.getEncoding() ),
                "iso-8859-1" ) );
        response.addHeader( "Content-Length", "" + file.length() );

        // 获取输出流，将文件写出到客户端
        OutputStream os = null;
        FileInputStream fis = null;
        try
        {
            os = response.getOutputStream();
            // 将下载文件读入
            fis = new FileInputStream( file );
            byte[] b = new byte[1024];
            while ( fis.read( b ) != -1 )
            {
                os.write( b );
            }
            os.flush();
        }
        catch ( Exception e )
        {
        }
        finally
        {
            try
            {
                if( fis != null )
                {
                    fis.close();
                }

                if( os != null )
                {
                    os.close();
                }
            }
            catch ( Exception e )
            {
            }
        }

    }
}
