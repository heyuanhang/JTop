package cn.com.mjsoft.cms.organization.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.organization.dao.vo.SystemOrganization;
import cn.com.mjsoft.cms.organization.service.OrgService;
import cn.com.mjsoft.cms.security.bean.SecurityResourceBean;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.stat.service.StatService;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.SecuritrConstant;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/organization" )
public class ManageOrganizationController
{
    private static Logger log = Logger.getLogger( ManageOrganizationController.class );

    private static OrgService orgService = OrgService.getInstance();

    private static SecurityService securityService = SecurityService.getInstance();

    @RequestMapping( value = "/createOrg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加部门机构", token = true )
    public ModelAndView createOrg( HttpServletRequest request, HttpServletResponse response )
    {
        SystemOrganization org = ( SystemOrganization ) ServletUtil.getValueObject( request,
            SystemOrganization.class );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth != null )
        {
            org.setCreator( auth.getApellation().toString() );
        }

        orgService.addNewOrganization( org );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/organization/CreateOrganization.jsp", paramMap );

    }

    @RequestMapping( value = "/editOrg.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑部门机构", token = true )
    public ModelAndView editOrg( HttpServletRequest request, HttpServletResponse response )
    {
        SystemOrganization org = ( SystemOrganization ) ServletUtil.getValueObject( request,
            SystemOrganization.class );

        orgService.editOrganization( org );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/organization/EditOrganization.jsp", paramMap );

    }

    @ResponseBody
    @RequestMapping( value = "/setOrgBoss.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "设置部门管理人", token = true )
    public String setOrgBoss( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String uid = ( String ) params.get( "uid" );

        String orgId = ( String ) params.get( "orgId" );

        orgService.setRelateOrgBoss( Long.valueOf( StringUtil.getLongValue( uid, -1 ) ), Long
            .valueOf( StringUtil.getLongValue( orgId, -1 ) ) );

        return "success";
    }

    @RequestMapping( value = "/deleteOrg.do", method = { RequestMethod.POST, RequestMethod.GET } )
    @ActionInfo( traceName = "删除部门", token = true )
    public ModelAndView deleteOrg( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long orgId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "orgId" ), -1 ) );

        orgService.deleteOrganizationAllInfo( orgId );

        StatService.getInstance().deleteOrgTrace( orgId );

        return ServletUtil.redirect( "/core/organization/ManageOrganization.jsp" );

    }

    @RequestMapping( value = "/createOrgAuthRange.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "创建部门权限范围", token = true )
    public ModelAndView createOrgAuthRange( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long orgId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "orgId" ), -1 ) );

        // 当前管理的站点
        Long targetSiteId = Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "targetSiteId" ), -1 ) );

        Integer tab = Integer.valueOf( StringUtil.getIntValue( ( String ) params.get( "tab" ), 1 ) );

        // 站点管理
        String[] checkOrgSiteManagerArray = StringUtil.getCheckBoxValue( params
            .get( "site-manager" ) );

        // 粗粒度菜单权限
        String[] checkResourceArray = StringUtil.getCheckBoxValue( params.get( "checkResource" ) );

        // 内容维护细粒度保护资源
        List protectContentAccTypeSecRes = securityService
            .retrieverSecurityResourceBeanByParentSysFlag(
                Constant.SECURITY.SYS_SEC_CONTENT_ACC_FLAG, SecuritrConstant.SPEC_PROTECT_TYPE,
                Constant.SECURITY.DATA_SEC_CONTENT );

        if( protectContentAccTypeSecRes.isEmpty() )
        {
            throw new FrameworkException( "系统权限数据丢失!" );
        }

        List allProtectContentAccTypeSecRes = securityService
            .retrieverAccGroupSecurityResourceBean();

        if( allProtectContentAccTypeSecRes.isEmpty() )
        {
            throw new FrameworkException( "系统权限数据丢失!" );
        }

        // 默认细粒度类型
        // List dataSecTypeIdList = new ArrayList();

        // dataSecTypeIdList.add( Constant.SECURITY.DATA_SEC_CLASS );
        // dataSecTypeIdList.add( Constant.SECURITY.DATA_SEC_CONTENT );
        // dataSecTypeIdList.add( Constant.SECURITY.DATA_SEC_SPEC );
        // dataSecTypeIdList.add( Constant.SECURITY.DATA_SEC_COMMEND );
        // dataSecTypeIdList.add( Constant.SECURITY.DATA_SEC_LEAVE_MSG );

        List dataSecTypeIdList = securityService.retrieveAllSecTypeId();

        Map dataSecTypeMap = new HashMap();

        Map accMap = null;

        // 解析所有类型的acc数据,并归类
        for ( int i = 0; i < dataSecTypeIdList.size(); i++ )
        {

            accMap = new HashMap();

            dataSecTypeMap.put( dataSecTypeIdList.get( i ), accMap );

            disposeAccTypeSecResBySecType( allProtectContentAccTypeSecRes, params, accMap,
                ( Long ) dataSecTypeIdList.get( i ) );

        }

        Map checkContentAccProtectClassIdResMap = new HashMap();

        // 解析数据,关键获取 name="acc-flag(group的访问标志)"
        // value="${Class.linearOrderFlag}-${Class.classId}"
        disposeAccTypeSecRes( protectContentAccTypeSecRes, params,
            checkContentAccProtectClassIdResMap );

        // 栏目维护细粒度保护资源
        List protectClassAccTypeSecRes = securityService
            .retrieverSecurityResourceBeanByParentSysFlag(
                Constant.SECURITY.SYS_SEC_CLASS_ACC_FLAG, SecuritrConstant.SPEC_PROTECT_TYPE,
                Constant.SECURITY.DATA_SEC_CLASS );

        if( protectClassAccTypeSecRes.isEmpty() )
        {
            throw new FrameworkException( "系统权限数据丢失!" );
        }

        Map checkClassAccProtectClassIdResMap = new HashMap();

        // 解析数据,关键获取 name="acc-flag(group的访问标志)"
        // value="${Class.linearOrderFlag}-${Class.classId}"
        disposeAccTypeSecRes( protectClassAccTypeSecRes, params, checkClassAccProtectClassIdResMap );

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( siteBean == null )
        {
            log.error( "当前管理员所管理站点信息丢失! login manager:"
                + SecuritySessionKeeper.getSecuritySession().getAuth() );
            throw new FrameworkException( "当前管理员所管理站点信息丢失!" );
        }

        orgService.maintainOrganizationProtectResRangeForRole( orgId, targetSiteId,
            checkOrgSiteManagerArray, checkResourceArray, dataSecTypeMap );

        List allManagerSite = orgService.retrieveSiteBeanByOrgId( orgId, null );

        SiteGroupBean site = null;
        boolean havaSite = false;
        for ( int i = 0; i < allManagerSite.size(); i++ )
        {
            site = ( SiteGroupBean ) allManagerSite.get( i );
            if( targetSiteId.equals( site.getSiteId() ) )
            {
                havaSite = true;
                break;
            }
        }

        if( !havaSite && !allManagerSite.isEmpty() )
        {
            targetSiteId = ( ( SiteGroupBean ) allManagerSite.get( 0 ) ).getSiteId();
        }

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );
        paramMap.put( "orgId", orgId );
        paramMap.put( "siteId", targetSiteId );
        paramMap.put( "tab", tab );

        return ServletUtil.redirect( "/core/organization/OrgAuthRange.jsp", paramMap );

    }

    @RequestMapping( value = "/directMaintainOrgAuthRange.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public ModelAndView directMaintainOrgAuthRange( HttpServletRequest request,
        HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long orgId = StringUtil.getLongValue( ( String ) params.get( "orgId" ), -1 );

        // 当前管理的站点
        Long targetSiteId = StringUtil.getLongValue( ( String ) params.get( "targetSiteId" ), -1 );

        Integer tab = StringUtil.getIntValue( ( String ) params.get( "tab" ), 1 );

        // 内容维护细粒度保护资源
        List protectContentAccTypeSecRes = securityService
            .retrieverSecurityResourceBeanByParentSysFlag(
                Constant.SECURITY.SYS_SEC_CONTENT_ACC_FLAG, SecuritrConstant.SPEC_PROTECT_TYPE,
                Constant.SECURITY.DATA_SEC_CONTENT );

        if( protectContentAccTypeSecRes.isEmpty() )
        {
            throw new FrameworkException( "系统权限数据丢失!" );
        }

        List allProtectContentAccTypeSecRes = securityService
            .retrieverAccGroupSecurityResourceBean();

        if( allProtectContentAccTypeSecRes.isEmpty() )
        {
            throw new FrameworkException( "系统权限数据丢失!" );
        }

        // 默认细粒度类型
        // List dataSecTypeIdList = new ArrayList();

        // dataSecTypeIdList.add( Constant.SECURITY.DATA_SEC_CLASS );
        // dataSecTypeIdList.add( Constant.SECURITY.DATA_SEC_CONTENT );
        // dataSecTypeIdList.add( Constant.SECURITY.DATA_SEC_SPEC );
        // dataSecTypeIdList.add( Constant.SECURITY.DATA_SEC_COMMEND );
        // dataSecTypeIdList.add( Constant.SECURITY.DATA_SEC_LEAVE_MSG );

        List dataSecTypeIdList = securityService.retrieveAllSecTypeId();

        Map dataSecTypeMap = new HashMap();

        Map accMap = null;

        // 解析所有类型的acc数据,并归类
        for ( int i = 0; i < dataSecTypeIdList.size(); i++ )
        {

            accMap = new HashMap();

            dataSecTypeMap.put( dataSecTypeIdList.get( i ), accMap );

            disposeAccTypeSecResBySecType( allProtectContentAccTypeSecRes, params, accMap,
                ( Long ) dataSecTypeIdList.get( i ) );

        }

        Map checkContentAccProtectClassIdResMap = new HashMap();

        // 解析数据,关键获取 name="acc-flag(group的访问标志)"
        // value="${Class.linearOrderFlag}-${Class.classId}"
        disposeAccTypeSecRes( protectContentAccTypeSecRes, params,
            checkContentAccProtectClassIdResMap );

        // 栏目维护细粒度保护资源
        List protectClassAccTypeSecRes = securityService
            .retrieverSecurityResourceBeanByParentSysFlag(
                Constant.SECURITY.SYS_SEC_CLASS_ACC_FLAG, SecuritrConstant.SPEC_PROTECT_TYPE,
                Constant.SECURITY.DATA_SEC_CLASS );

        if( protectClassAccTypeSecRes.isEmpty() )
        {
            throw new FrameworkException( "系统权限数据丢失!" );
        }

        Map checkClassAccProtectClassIdResMap = new HashMap();

        // 解析数据,关键获取 name="acc-flag(group的访问标志)"
        // value="${Class.linearOrderFlag}-${Class.classId}"
        disposeAccTypeSecRes( protectClassAccTypeSecRes, params, checkClassAccProtectClassIdResMap );

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( siteBean == null )
        {
            log.error( "当前管理员所管理站点信息丢失! login manager:"
                + SecuritySessionKeeper.getSecuritySession().getAuth() );
            throw new FrameworkException( "当前管理员所管理站点信息丢失!" );
        }

        // 非正常登录必抱错
        SecuritySessionKeeper.getSecuritySession().getAuth().getIdentity();

        List allManagerSite = orgService.retrieveSiteBeanByOrgId( orgId, null );

        SiteGroupBean site = null;
        boolean havaSite = false;
        for ( int i = 0; i < allManagerSite.size(); i++ )
        {
            site = ( SiteGroupBean ) allManagerSite.get( i );
            if( targetSiteId.equals( site.getSiteId() ) )
            {
                havaSite = true;
                break;
            }
        }

        if( !havaSite && !allManagerSite.isEmpty() )
        {
            targetSiteId = ( ( SiteGroupBean ) allManagerSite.get( 0 ) ).getSiteId();
        }

        Map paramMap = new HashMap();

          paramMap.put( "orgId", orgId );
        paramMap.put( "siteId", targetSiteId );
        paramMap.put( "tab", tab );

        return ServletUtil.redirect( "/core/organization/OrgAuthRange.jsp", paramMap );

    }

    /**
     * 填充accProtectClassIdMap,key:受保护细粒度资源 vlaue:所选的classId
     * 
     * @param protectContentAccTypeSecRes
     * @param requestParams
     * @param accProtectClassIdMap : key-ResBean, value-classId []
     */
    private static void disposeAccTypeSecRes( List protectContentAccTypeSecRes, Map requestParams,
        Map accProtectClassIdMap )
    {
        SecurityResourceBean bean = null;

        String[] checkedClassNodeIdAccSec = null;

        for ( int i = 0; i < protectContentAccTypeSecRes.size(); i++ )
        {
            bean = ( SecurityResourceBean ) protectContentAccTypeSecRes.get( i );

            // 选择了某sysFlag类型细粒度组合的目标栏目ID
            checkedClassNodeIdAccSec = getCheckAccClassIdInfo( StringUtil
                .getCheckBoxValue( requestParams.get( bean.getSysFlag() ) ) );

            accProtectClassIdMap.put( bean, checkedClassNodeIdAccSec );
        }
    }

    /**
     * 填充accProtectClassIdMap,key:受保护细粒度资源 vlaue:所选的classId
     * 
     * @param protectContentAccTypeSecRes
     * @param requestParams
     * @param accProtectClassIdMap : key-ResBean, value-classId []
     */
    private static void disposeAccTypeSecResBySecType( List protectContentAccTypeSecRes,
        Map requestParams, Map accProtectClassIdMap, Long dataProTypeId )
    {
        SecurityResourceBean bean = null;

        String[] checkedClassNodeIdAccSec = null;

        for ( int i = 0; i < protectContentAccTypeSecRes.size(); i++ )
        {
            bean = ( SecurityResourceBean ) protectContentAccTypeSecRes.get( i );

            // 选择了某sysFlag类型细粒度组合的目标栏目ID
            checkedClassNodeIdAccSec = getCheckAccClassIdInfo( StringUtil
                .getCheckBoxValue( requestParams.get( bean.getSysFlag() ) ) );

            if( dataProTypeId.equals( bean.getDataSecTypeId() ) )
            {
                accProtectClassIdMap.put( bean, checkedClassNodeIdAccSec );
            }
        }
    }

    private static String[] getCheckAccClassIdInfo( String[] checkValue )
    {
        String[] result = new String[checkValue.length];

        for ( int i = 0; i < checkValue.length; i++ )
        {
            result[i] = StringUtil.split( checkValue[i], "-" )[1];
        }

        return result;
    }

    public static void disposeNewAccResAuthRange( Map params )
    {

        Long orgId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "orgId" ), -1 ) );

        // 当前管理的站点
        Long targetSiteId = Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "targetSiteId" ), -1 ) );

        // 站点管理
        String[] checkOrgSiteManagerArray = StringUtil.getCheckBoxValue( params
            .get( "site-manager" ) );

        // 粗粒度菜单权限
        String[] checkResourceArray = StringUtil.getCheckBoxValue( params.get( "checkResource" ) );

        // 内容维护细粒度保护资源
        List protectContentAccTypeSecRes = securityService
            .retrieverSecurityResourceBeanByParentSysFlag(
                Constant.SECURITY.SYS_SEC_CONTENT_ACC_FLAG, SecuritrConstant.SPEC_PROTECT_TYPE,
                Constant.SECURITY.DATA_SEC_CONTENT );

        if( protectContentAccTypeSecRes.isEmpty() )
        {
            throw new FrameworkException( "系统权限数据丢失!" );
        }

        List allProtectContentAccTypeSecRes = securityService
            .retrieverAccGroupSecurityResourceBean();

        if( allProtectContentAccTypeSecRes.isEmpty() )
        {
            throw new FrameworkException( "系统权限数据丢失!" );
        }

        // 默认细粒度类型
        // List dataSecTypeIdList = new ArrayList();

        // dataSecTypeIdList.add( Constant.SECURITY.DATA_SEC_CLASS );
        // dataSecTypeIdList.add( Constant.SECURITY.DATA_SEC_CONTENT );
        // dataSecTypeIdList.add( Constant.SECURITY.DATA_SEC_SPEC );
        // dataSecTypeIdList.add( Constant.SECURITY.DATA_SEC_COMMEND );
        // dataSecTypeIdList.add( Constant.SECURITY.DATA_SEC_LEAVE_MSG );

        List dataSecTypeIdList = securityService.retrieveAllSecTypeId();

        Map dataSecTypeMap = new HashMap();

        Map accMap = null;

        // 解析所有类型的acc数据,并归类
        for ( int i = 0; i < dataSecTypeIdList.size(); i++ )
        {

            accMap = new HashMap();

            dataSecTypeMap.put( dataSecTypeIdList.get( i ), accMap );

            disposeAccTypeSecResBySecType( allProtectContentAccTypeSecRes, params, accMap,
                ( Long ) dataSecTypeIdList.get( i ) );

            Iterator it = accMap.entrySet().iterator();

            while ( it.hasNext() )
            {
                Entry e = ( Entry ) it.next();

                String[] s = ( String[] ) e.getValue();

                for ( int ix = 0; ix < s.length; ix++ )
                {
                  //  System.err.print( "打印 acc val:" + s[ix] + "  " );
                }
                
            }
        }

        Map checkContentAccProtectClassIdResMap = new HashMap();

        // 解析数据,关键获取 name="acc-flag(group的访问标志)"
        // value="${Class.linearOrderFlag}-${Class.classId}"
        disposeAccTypeSecRes( protectContentAccTypeSecRes, params,
            checkContentAccProtectClassIdResMap );

        // 栏目维护细粒度保护资源
        List protectClassAccTypeSecRes = securityService
            .retrieverSecurityResourceBeanByParentSysFlag(
                Constant.SECURITY.SYS_SEC_CLASS_ACC_FLAG, SecuritrConstant.SPEC_PROTECT_TYPE,
                Constant.SECURITY.DATA_SEC_CLASS );

        if( protectClassAccTypeSecRes.isEmpty() )
        {
            throw new FrameworkException( "系统权限数据丢失!" );
        }

        Map checkClassAccProtectClassIdResMap = new HashMap();

        // 解析数据,关键获取 name="acc-flag(group的访问标志)"
        // value="${Class.linearOrderFlag}-${Class.classId}"
        disposeAccTypeSecRes( protectClassAccTypeSecRes, params, checkClassAccProtectClassIdResMap );

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( siteBean == null )
        {
            log.error( "当前管理员所管理站点信息丢失! login manager:"
                + SecuritySessionKeeper.getSecuritySession().getAuth() );
            throw new FrameworkException( "当前管理员所管理站点信息丢失!" );
        }

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();
        // String creator = null;
        if( auth != null )
        {
            // org.setCreator( auth.getApellation().toString() );
        }

        orgService.maintainOrganizationProtectResRangeForRoleForNewClassMode( orgId, targetSiteId,
            checkOrgSiteManagerArray, checkResourceArray, dataSecTypeMap );
        // checkContentAccProtectClassIdResMap );

        List allManagerSite = orgService.retrieveSiteBeanByOrgId( orgId, null );

        SiteGroupBean site = null;
        boolean havaSite = false;
        for ( int i = 0; i < allManagerSite.size(); i++ )
        {
            site = ( SiteGroupBean ) allManagerSite.get( i );
            if( targetSiteId.equals( site.getSiteId() ) )
            {
                havaSite = true;
                break;
            }
        }

        if( !havaSite && !allManagerSite.isEmpty() )
        {
            targetSiteId = ( ( SiteGroupBean ) allManagerSite.get( 0 ) ).getSiteId();
        }

    }
}
