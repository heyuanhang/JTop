package cn.com.mjsoft.cms.channel.controller;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
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

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.channel.bean.ArrayNoteBean;
import cn.com.mjsoft.cms.channel.bean.ContentClassBean;
import cn.com.mjsoft.cms.channel.bean.ContentCommendTypeBean;
import cn.com.mjsoft.cms.channel.bean.MoveNodeInfoBean;
import cn.com.mjsoft.cms.channel.dao.vo.ContentClass;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.ServiceUtil;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.content.service.ContentService;
import cn.com.mjsoft.cms.metadata.bean.DataModelBean;
import cn.com.mjsoft.cms.metadata.bean.ModelPersistenceMySqlCodeBean;
import cn.com.mjsoft.cms.metadata.service.MetaDataService;
import cn.com.mjsoft.cms.organization.controller.ManageOrganizationController;
import cn.com.mjsoft.cms.security.controller.ManageRoleController;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.Role;
import cn.com.mjsoft.framework.security.session.SecuritySession;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/channel" )
public class ManageContentClassController
{

    private static ChannelService channelService = ChannelService.getInstance();

    private static SiteGroupService siteService = SiteGroupService.getInstance();

    private static ContentService contentService = ContentService.getInstance();

    private static MetaDataService metaDataService = MetaDataService.getInstance();

    @ResponseBody
    @RequestMapping( value = { "/addSpecClass.do", "/addSpecSubject.do" }, method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加专题分类", token = true )
    public Object addSpecClass( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        

        ContentClass contentClass = ( ContentClass ) ServletUtil.getValueObject( request,
            ContentClass.class );
        contentClass.setLayer( Integer.valueOf( "1" ) );// 默认第一层
        contentClass.setIsLeaf( Constant.SITE_CHANNEL.TRUE_FLAG );
        contentClass.setSystemHandleTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

        SecuritySession securitySession = SecuritySessionKeeper.getSecuritySession();

        SiteGroupBean siteBean = ( SiteGroupBean ) securitySession.getCurrentLoginSiteInfo();

        boolean isSpecNode = Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL.equals( contentClass
            .getClassType() )
            || Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUBJECT
                .equals( contentClass.getClassType() )
            || Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUB_NODE.equals( contentClass
                .getClassType() );

        // 检查专题类型添加是否合法
        boolean isParentSpecial = channelService.checkParentIsSpecial( contentClass.getParent() );

        if( isParentSpecial )
        {
            if( !isSpecNode )
            {
                // 后台验证沉默模式,不提示
                return null;
            }
        }
        else if( !Constant.SITE_CHANNEL.SITE_NODE_FLAG.equals( contentClass.getParent() ) )
        {
            // 父栏目是一般栏目，也不可添加专题
            if( isSpecNode )
            {
                // 后台验证沉默模式,不提示
                return null;
            }
        }

        if( isSpecNode )// 专题模式,ajax方式提交,故转中文
        {
            contentClass.setClassName( SystemSafeCharUtil.decodeFromWeb( contentClass
                .getClassName() ) );
        }

        // 设定站点标志
        contentClass.setSiteFlag( siteBean.getSiteRoot() );

        // 设定专题标志
        if( isSpecNode )
        {
            contentClass.setIsSpecial( Constant.COMMON.ON );
        }

        // 默认过滤敏感词
        contentClass.setSensitiveMode( Constant.COMMON.ON );

        // 根据所属站点配置配置栏目初始值
        contentClass.setEditorImageW( siteBean.getDefEditorImageW() );
        contentClass.setEditorImageH( siteBean.getDefEditorImageH() );
        contentClass.setEditorImageDM( siteBean.getDefEditorImageDM() );

        contentClass.setContentImageW( siteBean.getDefContentImageW() );
        contentClass.setContentImageH( siteBean.getDefContentImageH() );
        contentClass.setContentImageDM( siteBean.getDefContentImageDM() );

        contentClass.setListImageW( siteBean.getDefListImageW() );
        contentClass.setListImageH( siteBean.getDefListImageH() );
        contentClass.setListImageDM( siteBean.getDefListImageDM() );

        contentClass.setClassImageW( siteBean.getDefClassImageW() );
        contentClass.setClassImageH( siteBean.getDefClassImageH() );
        contentClass.setClassImageDM( siteBean.getDefClassImageDM() );

        contentClass.setHomeImageW( siteBean.getDefHomeImageW() );
        contentClass.setHomeImageH( siteBean.getDefHomeImageH() );
        contentClass.setHomeImageDM( siteBean.getDefHomeImageDM() );

        // 默认模板
        Map defTemplate = siteService.retrieveSingleModelTemplate( siteBean.getSiteId(),
            contentClass.getContentType() );

        contentClass.setClassTemplateUrl( ( String ) defTemplate.get( "listTemplateUrl" ) );
        contentClass.setContentTemplateUrl( ( String ) defTemplate.get( "contentTemplateUrl" ) );

        channelService.addContentClass( contentClass, siteBean, isSpecNode );

        // 增加所在组织机构权限范围

        Map param = new HashMap();

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        Long orgId = ( Long ) auth.getOrgIdentity();

        param.put( "orgId", orgId.toString() );
        param.put( "targetSiteId", siteBean.getSiteId().toString() );
        param.put( "maintain-spec-accredit", contentClass.getLinearOrderFlag() + "-"
            + contentClass.getClassId() );

        ManageOrganizationController.disposeNewAccResAuthRange( param );

        // 不需要对比orgId下的所有role，只需关注当前管理员所存在的第一个role
        Role role = auth.getUserRole()[0];

        if( role != null )
        {
            param.put( "roleId", role.getRoleID().toString() );

            ManageRoleController.disposeNewAccResRole( param );
        }

        if( isSpecNode )
        {
            return "success";
        }

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );
        paramMap.put( "classId", contentClass.getClassId() );

        if( request.getServletPath().endsWith( "addSpecSubject.do" ) )
        {
            return ServletUtil.redirect( "/core/channel/AddSpecialSubject.jsp", paramMap );
        }

        return ServletUtil.redirect( "/core/channel/AddSpecialClass.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = { "/createChannel.do", "/createChannelSiteMode.do" }, method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加栏目分类", token = true )
    public Object createChannel( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        Map params = ServletUtil.getRequestInfo( request );

       

        if( request.getServletPath().endsWith( "createChannelSiteMode.do" ) )
        {
            // 检查站点模式添加,父classId必须为-9999

            if( StringUtil.getLongValue( ( String ) params.get( "classId" ), -1 ) != -9999 )
            {
                // 后台验证沉默模式,不提示
                return null;
            }
        }

        Long targetCfgCid = Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "targetCfgClassId" ), -1 ) );

        String classNameInfo = ( String ) params.get( "classNameInfo" );

        List classNameList = StringUtil.changeStringToList( classNameInfo, "\n" );

        String name = null;

        String flag = null;

        String[] temp = null;

        for ( int i = 0; i < classNameList.size(); i++ )
        {
            temp = StringUtil.split( ( String ) classNameList.get( i ), "@" );

            if( temp.length < 2 )
            {
                continue;
            }

            name = temp[0];

            flag = temp[1];

            if( StringUtil.isStringNull( name ) || StringUtil.isStringNull( flag ) )
            {
                continue;
            }

            if( channelService.haveSameFlag( flag ) )
            {
                continue;
            }

            ContentClass contentClass = ( ContentClass ) ServletUtil.getValueObject( request,
                ContentClass.class );

            contentClass.setClassName( name.trim() );
            contentClass.setClassFlag( flag.trim() );

            contentClass.setLayer( Integer.valueOf( "1" ) );
            contentClass.setIsLeaf( Constant.SITE_CHANNEL.TRUE_FLAG );
            contentClass.setSystemHandleTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );

            // 强制使用父栏目classId
            contentClass.setParent( Long.valueOf( StringUtil.getLongValue( ( String ) params
                .get( "classId" ), -1 ) ) );

            SecuritySession securitySession = SecuritySessionKeeper.getSecuritySession();

            SiteGroupBean siteBean = ( SiteGroupBean ) securitySession.getCurrentLoginSiteInfo();

            boolean isSpecNode = Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL.equals( contentClass
                .getClassType() )
                || Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUBJECT.equals( contentClass
                    .getClassType() )
                || Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUB_NODE.equals( contentClass
                    .getClassType() );

            // 检查专题类型添加是否合法
            boolean isParentSpecial = channelService
                .checkParentIsSpecial( contentClass.getParent() );

            if( isParentSpecial )
            {
                if( !isSpecNode )
                {
                    // 后台验证沉默模式,不提示
                    return null;
                }
            }
            else if( !Constant.SITE_CHANNEL.SITE_NODE_FLAG.equals( contentClass.getParent() ) )
            {
                // 父栏目是一般栏目，也不可添加专题
                if( isSpecNode )
                {
                    // 后台验证沉默模式,不提示
                    return null;
                }
            }

            if( isSpecNode )// 专题模式,ajax方式提交,故转中文
            {
                contentClass
                    .setClassName( URLDecoder.decode( contentClass.getClassName(), "UTF-8" ) );
            }

            // 设定站点标志
            contentClass.setSiteFlag( siteBean.getSiteRoot() );

            // 设定专题标志
            if( isSpecNode )
            {
                contentClass.setIsSpecial( Constant.COMMON.ON );
            }

            // 默认过滤敏感词
            contentClass.setSensitiveMode( Constant.COMMON.ON );

            // 根据所属站点配置配置栏目初始值
            contentClass.setEditorImageW( siteBean.getDefEditorImageW() );
            contentClass.setEditorImageH( siteBean.getDefEditorImageH() );
            contentClass.setEditorImageDM( siteBean.getDefEditorImageDM() );

            contentClass.setContentImageW( siteBean.getDefContentImageW() );
            contentClass.setContentImageH( siteBean.getDefContentImageH() );
            contentClass.setContentImageDM( siteBean.getDefContentImageDM() );

            contentClass.setListImageW( siteBean.getDefListImageW() );
            contentClass.setListImageH( siteBean.getDefListImageH() );
            contentClass.setListImageDM( siteBean.getDefListImageDM() );

            contentClass.setClassImageW( siteBean.getDefClassImageW() );
            contentClass.setClassImageH( siteBean.getDefClassImageH() );
            contentClass.setClassImageDM( siteBean.getDefClassImageDM() );

            contentClass.setHomeImageW( siteBean.getDefHomeImageW() );
            contentClass.setHomeImageH( siteBean.getDefHomeImageH() );
            contentClass.setHomeImageDM( siteBean.getDefHomeImageDM() );

            // 默认模板
            Map defTemplate = siteService.retrieveSingleModelTemplate( siteBean.getSiteId(),
                contentClass.getContentType() );

            contentClass.setClassTemplateUrl( ( String ) defTemplate.get( "listTemplateUrl" ) );
            contentClass.setContentTemplateUrl( ( String ) defTemplate.get( "contentTemplateUrl" ) );

            // 如果选择了目标同步配置栏目,则应用核心配置
            if( targetCfgCid.longValue() > 0 )
            {
                ContentClassBean tCfgClassBean = channelService
                    .retrieveSingleClassBeanInfoByClassId( targetCfgCid );

                contentClass.setClassType( tCfgClassBean.getClassType() );
                contentClass.setContentType( tCfgClassBean.getContentType() );
                contentClass.setWorkflowId( tCfgClassBean.getWorkflowId() );
                contentClass.setClassHomeTemplateUrl( tCfgClassBean.getClassHomeTemplateUrl() );
                contentClass.setClassTemplateUrl( tCfgClassBean.getClassTemplateUrl() );
                contentClass.setContentTemplateUrl( tCfgClassBean.getContentTemplateUrl() );
                contentClass.setClassHomeProduceType( tCfgClassBean.getClassHomeProduceType() );
                contentClass.setClassProduceType( tCfgClassBean.getClassProduceType() );
                contentClass.setContentProduceType( tCfgClassBean.getContentProduceType() );
                contentClass.setClassHomePublishRuleId( tCfgClassBean.getClassHomePublishRuleId() );
                contentClass.setClassPublishRuleId( tCfgClassBean.getClassPublishRuleId() );
                contentClass.setContentPublishRuleId( tCfgClassBean.getContentPublishRuleId() );
                contentClass.setListPageLimit( tCfgClassBean.getListPageLimit() );
                contentClass.setSyncPubClass( tCfgClassBean.getSyncPubClass() );
                contentClass.setExtDataModelId( tCfgClassBean.getExtDataModelId() );
            }

            channelService.addContentClass( contentClass, siteBean, isSpecNode );

            // 增加所在组织机构权限范围

            Map param = new HashMap();

            Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

            Long orgId = ( Long ) auth.getOrgIdentity();

            param.put( "orgId", orgId.toString() );
            param.put( "targetSiteId", siteBean.getSiteId().toString() );
            param.put( "manage-accredit-class", contentClass.getLinearOrderFlag() + "-"
                + contentClass.getClassId() );
            param.put( "maintain-content-accredit", contentClass.getLinearOrderFlag() + "-"
                + contentClass.getClassId() );

            ManageOrganizationController.disposeNewAccResAuthRange( param );

            // 不需要对比orgId下的所有role，只需关注当前管理员所存在的第一个role
            Role role = auth.getUserRole()[0];

            if( role != null )
            {
                param.put( "roleId", role.getRoleID().toString() );

                ManageRoleController.disposeNewAccResRole( param );
            }

            if( isSpecNode )
            {
                return "success";
            }
            else
            {
                // 增加内容统计
                StatService.getInstance().addClassTrace( contentClass.getClassId(),
                    siteBean.getSiteId() );
            }

        }

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );
        paramMap.put( "classId", params.get( "parent" ) );

        return ServletUtil.redirect( "/core/channel/AddContentClass.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = { "/editContentClass.do", "/editSpecClass.do", "/editSpecSubject.do" }, method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑栏目分类", token = true )
    public Object editContentClass( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        

        // 由于存在一些字段允许html,所以不能在这里先使用getHttpRequestSnapshot(),故使用普通方式获取classId
        Long extModelId = Long.valueOf( StringUtil.getLongValue( ( String ) request
            .getParameter( "extDataModelId" ), -1 ) );

        List filedBeanList = metaDataService.retrieveModelFiledInfoBeanList( extModelId );

        Set htmlFieldSet = ServiceUtil.checkEditorField( filedBeanList );
        htmlFieldSet.add( "classTemplateUrl" );
        htmlFieldSet.add( "contentTemplateUrl" );
        htmlFieldSet.add( "classTemplateUrl" );

        Map params = ServletUtil.getRequestDecodeInfo( request, htmlFieldSet );

        ContentClass contentClass = ( ContentClass ) ServletUtil.getValueObjectDecode( request,
            ContentClass.class );

        if( contentClass.getClassId() == null || contentClass.getClassId().longValue() < 1 )
        {
            throw new FrameworkException( "没有选择所要操作的栏目" );
        }

        // 检查spec cmd

        if( request.getServletPath().endsWith( "editSpecClass.do" ) )// esc
        // cmd为初级权限
        {
            ContentClassBean spec = channelService
                .retrieveSingleClassBeanInfoByClassId( contentClass.getClassId() );

            // spec模式只有spec允许被操作
            if( !( Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL.equals( spec.getClassType() )
                || Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUBJECT.equals( spec.getClassType() ) || Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUB_NODE
                .equals( spec.getClassType() ) ) )
            {
                return "success";
            }

        }

        if( request.getServletPath().endsWith( "editSpecSubject.do" ) ) // ess
        // cmd
        // 为数据权限
        {
            // 替换classId
            contentClass.setClassId( Long.valueOf( StringUtil.getLongValue( ( String ) params
                .get( "selfId" ), -1 ) ) );
        }

        boolean isSpec = false;

        if( Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL.equals( contentClass.getClassType() )
            || Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUBJECT
                .equals( contentClass.getClassType() )
            || Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUB_NODE.equals( contentClass
                .getClassType() ) )
        {
            isSpec = true;
        }

        if( isSpec )// 专题模式,ajax方式提交,故转中文
        {
            contentClass.setClassName( SystemSafeCharUtil.decodeFromWeb( contentClass.getClassName() ) );
        }
        else
        {
            // 栏目模式需要确定是否模型变动
            //
            long oldModelId = StringUtil.getLongValue( ( String ) params.get( "sys_old_model_id" ),
                -1 );

            if( oldModelId != -1 && contentClass.getContentType().longValue() != oldModelId )
            {
                contentService.deleteAllSystemAndUserDefineContentToTrash(
                    ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                        .getCurrentLoginSiteInfo(), oldModelId, contentClass.getClassId(),
                    new ArrayList() );
            }

            // 强制去掉没有模板的静态发布信息
            if( StringUtil.isStringNull( contentClass.getClassHomeTemplateUrl() ) )
            {
                contentClass.setClassHomePublishRuleId( Long.valueOf( -1 ) );
                contentClass.setClassHomeProduceType( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE );
            }

            if( StringUtil.isStringNull( contentClass.getClassTemplateUrl() ) )
            {
                contentClass.setClassPublishRuleId( Long.valueOf( -1 ) );
                contentClass.setClassProduceType( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE );
            }

            if( StringUtil.isStringNull( contentClass.getContentTemplateUrl() ) )
            {
                contentClass.setContentPublishRuleId( Long.valueOf( -1 ) );
                contentClass.setContentProduceType( Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE );
            }

        }

        ModelPersistenceMySqlCodeBean sqlCodeBean = metaDataService
            .retrieveSingleModelPerMysqlCodeBean( extModelId );

        DataModelBean model = metaDataService.retrieveSingleDataModelBeanById( contentClass
            .getExtDataModelId() );

        channelService.editContentClassByClassId( contentClass, model, filedBeanList, sqlCodeBean,
            params );

        if( isSpec )
        {
            // 专题模式下,需要查找推荐子分类是否存在静态发布
            SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
                .getCurrentLoginSiteInfo();

            // 获取专题下所有栏目列表数
            List commTypeList = channelService.retrieveContentCommendTypeBean( site.getSiteFlag(),
                contentClass.getClassId(), false, true, false );

            ContentCommendTypeBean commTypeBean = null;

            boolean havaStatic = false;
            for ( int icl = 0; icl < commTypeList.size(); icl++ )
            {
                commTypeBean = ( ContentCommendTypeBean ) commTypeList.get( icl );

                if( Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE.equals( commTypeBean
                    .getListProduceType() ) )
                {
                    havaStatic = true;
                    break;
                }
            }

            if( havaStatic )
            {
                channelService.updateContentClassListProduceType( contentClass.getClassId(),
                    Constant.SITE_CHANNEL.PAGE_PRODUCE_H_TYPE );
            }
            else
            {
                channelService.updateContentClassListProduceType( contentClass.getClassId(),
                    Constant.SITE_CHANNEL.PAGE_PRODUCE_D_TYPE );
            }

            return "success";
        }

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = { "/sortContentClass.do", "/sortContentClassSiteMode.do",
        "/sortSpecSubject.do" }, method = { RequestMethod.POST } )
    @ActionInfo( traceName = "栏目分类排序", token = true )
    public Object sortContentClass( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        

        ArrayNoteBean nodeBean = ( ArrayNoteBean ) ServletUtil.getValueObject( request,
            ArrayNoteBean.class );

        if( request.getServletPath().endsWith( "createChannelSiteMode.do" ) )
        {
            // 检查站点模式添加,父classId必须为-9999

            if( nodeBean.getClassId().longValue() != -9999 )
            {
                // 后台验证沉默模式,不提示
                return "success";
            }
        }

        nodeBean.setParent( nodeBean.getClassId() );

        boolean sameParent = channelService.checkContentClassBelongToSameParent( nodeBean
            .changeToIdList() );

        if( !sameParent )
        {

            throw new FrameworkException( "所操作的栏目不属于同一父栏目" );
        }

        channelService.swapContentClassNote( nodeBean.getCurrentClassId(), nodeBean
            .getTargetClassId() );

        Map paramMap = new HashMap();
        paramMap.put( "classId", nodeBean.getParent().toString() );
        paramMap.put( "fromFlow", Boolean.TRUE );

        if( request.getServletPath().endsWith( "sortSpecSubject.do" ) )
        {
            return ServletUtil.redirect( "/core/channel/ShowSortSpecSubject.jsp", paramMap );

        }

        return ServletUtil.redirect( "/core/channel/ShowSortChannel.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/copyChannelConfig.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "复制栏目分类配置", token = true )
    public Object copyChannelConfig( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        

        Map params = ServletUtil.getRequestInfo( request );

        Long classId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "classId" ),
            -1 ) );

        Integer range = Integer.valueOf( StringUtil.getIntValue( ( String ) params.get( "range" ),
            -1 ) );

        String[] copyTemplate = StringUtil.getCheckBoxValue( params.get( "template" ) );

        String[] copyPublish = StringUtil.getCheckBoxValue( params.get( "publish" ) );

        String[] copyStaticRule = StringUtil.getCheckBoxValue( params.get( "staticRule" ) );

        String[] copyImageRule = StringUtil.getCheckBoxValue( params.get( "imageRule" ) );

        String[] copyOther = StringUtil.getCheckBoxValue( params.get( "other" ) );

        channelService.disposeCopyChannelConfig( classId, range, copyTemplate, copyPublish,
            copyStaticRule, copyImageRule, copyOther );

        Map paramMap = new HashMap();
        paramMap.put( "fromFlow", Boolean.TRUE );
        paramMap.put( "classId", classId );

        return ServletUtil.redirect( "/core/channel/CopyChannelConfig.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/moveContentClass", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "移动栏目分类", token = true )
    public Object moveContentClass( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        

        MoveNodeInfoBean moveNodeInfoBean = ( MoveNodeInfoBean ) ServletUtil.getValueObject(
            request, MoveNodeInfoBean.class );

        if( moveNodeInfoBean.getCurrentParent().equals( moveNodeInfoBean.getSelectParent() ) )
        {
            return "-1";

        }

        if( moveNodeInfoBean.getClassId().equals( moveNodeInfoBean.getSelectParent() ) )
        {
            return "0";

        }

        int serviceFlag = channelService.moveContentClassNodeToNewlayer( moveNodeInfoBean );

        if( serviceFlag == -2 )
        {
            return "-2";
        }

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/moveSpecSubject", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "移动专题分类", token = true )
    public Object moveSpecSubject( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        

        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "classId" );

        Long currentParent = Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "currentParent" ), -1 ) );
        Long selectParent = Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "selectParent" ), -1 ) );

        List idList = StringUtil.changeStringToList( ids, "," );

        Long classId = null;
        for ( int i = 0; i < idList.size(); i++ )
        {

            classId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

            if( classId.longValue() < 0 )
            {
                continue;
            }

            MoveNodeInfoBean moveNodeInfoBean = new MoveNodeInfoBean();

            moveNodeInfoBean.setClassId( classId );
            moveNodeInfoBean.setCurrentParent( currentParent );
            moveNodeInfoBean.setSelectParent( selectParent );

            if( moveNodeInfoBean.getCurrentParent().equals( moveNodeInfoBean.getSelectParent() ) )
            {
                return "-1";

            }

            if( moveNodeInfoBean.getClassId().equals( moveNodeInfoBean.getSelectParent() ) )
            {
                return "0";

            }

            channelService.moveContentClassNodeToNewlayer( moveNodeInfoBean );

        }

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/deleteSpecSubject", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除专题分类", token = true )
    public Object deleteSpecSubject( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

         

        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "specIds" );

        List idList = StringUtil.changeStringToList( ids, "," );

        Long classId = null;

        for ( int i = 0; i < idList.size(); i++ )
        {

            classId = Long.valueOf( StringUtil.getLongValue( ( String ) idList.get( i ), -1 ) );

            if( classId.longValue() < 0 )
            {
                continue;
            }

            ContentClassBean contentClassBean = new ContentClassBean();

            contentClassBean.setClassId( classId );

            contentClassBean = channelService
                .deleteContentClassAndChildrenAllInfomation( contentClassBean );

            List typeIdList = channelService
                .retrieveSpecContentType( contentClassBean.getClassId() );

            channelService.deleteCommendTypeAllInfo( typeIdList );

        }

        return "success";

    }

    @ResponseBody
    @RequestMapping( value = "/setSpecRecomStatus", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "推荐专题分类", token = true )
    public Object setSpecRecomStatus( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        String ids = ( String ) params.get( "classId" );

        Integer status = Integer.valueOf( StringUtil.getIntValue(
            ( String ) params.get( "status" ), -1 ) );

        channelService.updateSpecSubjectRecommendStatus( StringUtil.changeStringToList( ids, "," ),
            status );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = { "/deleteContentClass.do", "/deleteSpecClass.do" }, method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除栏目分类", token = true )
    public Object deleteContentClass( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
       

        Map params = ServletUtil.getRequestInfo( request );

        ContentClassBean contentClassBean = ( ContentClassBean ) ServletUtil.getValueObject(
            request, ContentClassBean.class );

        if( contentClassBean.getClassId() == null || contentClassBean.getClassId().longValue() < 1 )
        {
            throw new FrameworkException( "没有选择所要操作的栏目" );
        }

        // 检查spec cmd

        if( request.getServletPath().endsWith( "deleteSpecClass.do" ) )
        {
            ContentClassBean spec = channelService
                .retrieveSingleClassBeanInfoByClassId( contentClassBean.getClassId() );

            // spec模式只有spec允许被操作
            if( !( Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL.equals( spec.getClassType() )
                || Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUBJECT.equals( spec.getClassType() ) || Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUB_NODE
                .equals( spec.getClassType() ) ) )
            {
                return "success";
            }

        }

        contentClassBean = channelService
            .deleteContentClassAndChildrenAllInfomation( contentClassBean );

        // 以下为专题删除
        if( Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL.equals( contentClassBean.getClassType() )
            || Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUBJECT.equals( contentClassBean
                .getClassType() )
            || Constant.SITE_CHANNEL.CLASS_TYPE_SPECIAL_SUB_NODE.equals( contentClassBean
                .getClassType() ) )
        {
            return "success";
        }
        else
        {
            StatService.getInstance().deleteClassTrace( contentClassBean.getClassId() );
        }

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", "true" );
        paramMap.put( "flag", Integer.valueOf( 2 ) );

        paramMap.put( "classId", contentClassBean.getParent() );

        if( "true".equals( ( String ) params.get( "childMode" ) ) )
        {
            return ServletUtil.redirect( "/core/channel/ShowDeleteChannel.jsp", paramMap );
        }

        return ServletUtil.redirect( "/core/channel/redirectFrame.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/getClassTreeInfo.do", method = { RequestMethod.POST, RequestMethod.GET} )
    public Object getClassTreeInfo( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {

        Map params = ServletUtil.getRequestInfo( request );

        String id = ( String ) params.get( "id" );

        if( StringUtil.isStringNull( id ) )
        {
            return "";
        }

        ContentClassBean classBean = channelService
            .retrieveSingleClassBeanInfoByClassId( StringUtil.getLongValue( id, -1 ) );

        String path = classBean.getChannelPath();

        String[] ids = StringUtil.split( path, "," );

        StringBuilder buf = new StringBuilder();

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupFlagInfoCache
            .getEntry( classBean.getSiteFlag() );

        buf.append( site.getSiteName() + " > " );

        String flag = "";
        for ( String cid : ids )
        {
            buf.append( flag );
            classBean = channelService.retrieveSingleClassBeanInfoByClassId( StringUtil
                .getLongValue( cid, -1 ) );

            buf.append( classBean.getClassName() );
            flag = " > ";

        }

        return buf.toString();

    }

}
