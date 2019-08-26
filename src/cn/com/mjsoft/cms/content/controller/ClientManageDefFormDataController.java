package cn.com.mjsoft.cms.content.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.behavior.Behavior;
import cn.com.mjsoft.framework.util.ObjectUtility;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/content" )
public class ClientManageDefFormDataController
{

    private static ContentService contentService = ContentService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    @ResponseBody
    @RequestMapping( value = { "/clientAddFormData.do", "/clientEditFormData.do" }, method = { RequestMethod.POST } )
    public String clientAddOrEditFormData( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        // 由于存在一些字段允许html,所以不能在这里先使用getHttpRequestSnapshot(),故使用普通方式获取modelId
        Long modelId = Long.valueOf( StringUtil
            .getLongValue( request.getParameter( "modelId" ), -1 ) );

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

        boolean editMode = false;

        if( request.getServletPath().endsWith( "clientEditFormData.do" ) )
        {
            editMode = true;
        }

        if( Constant.COMMON.OFF.equals( modelBean.getIsMemberEdit() ) )
        {
            if( editMode )
            {
                return "-2";// 不允许外部用户改动已改动表单数据
            }
        }

        // 验证码

        HttpSession ssn = request.getSession();

        String checkCode = ( String ) ssn
            .getAttribute( Constant.SITE_CHANNEL.RANDOM_INPUT_RAND_CODE_KEY );

        String checkCodeTest = ( String ) request.getParameter( "sysCheckCode" );

        if( modelBean.getMustToken().intValue() == 1 )
        {
            if( StringUtil.isStringNull( checkCode ) || !checkCode.equalsIgnoreCase( checkCodeTest ) )
            {
                // 验证码错误
                return "-3";
            }
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

        SiteGroupBean site = SiteGroupService.getCurrentSiteInfoFromWebRequest( request );

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

        int status = contentService.addOrEditDefineFormData( site, params, editMode );

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

        return String.valueOf( status );
    }
}
