package cn.com.mjsoft.cms.security.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.security.dao.vo.SecurityResource;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/security" )
public class ManageSecurityResourceController
{

    private static SecurityService securityService = SecurityService.getInstance();

    @RequestMapping( value = "/addSecurityResource.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加会员权限资源", token = true )
    public ModelAndView addSecurityResource( HttpServletRequest request,
        HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        SecurityResource securityResourceVO = ServletUtil.getValueObject( request,
            SecurityResource.class );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth != null )
        {
            securityResourceVO.setCreator( ( String ) auth.getApellation() );
        }

        if( Constant.SECURITY.DATA_P_TYPE_SIMPLE.equals( securityResourceVO.getDataProtectType() ) )
        {
            securityResourceVO.setDataSecTypeId( Long.valueOf( 0 ) );
        }
        else if( Constant.SECURITY.DATA_P_TYPE_ACC.equals( securityResourceVO.getDataProtectType() ) )
        {
            Long dataSecTypeId = Long.valueOf( StringUtil.getLongValue( ( String ) params
                .get( "dataSecTypeId" ), -1 ) );

            boolean isOk = securityService.checkDataSecTypeId( dataSecTypeId );

            if( !isOk )
            {
                throw new FrameworkException( "系统错误:传入非法的细粒度授权类型ID,dataSecTypeId:" + dataSecTypeId );
            }

            securityResourceVO.setDataSecTypeId( dataSecTypeId );
        }

        securityService.addSecurityResource( securityResourceVO );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/security/AddSecurityResource.jsp", returnParams );
    }

    @RequestMapping( value = "/sortSecRes.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "权限资源排序", token = true )
    public ModelAndView sortSecRes( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long currentResId = Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "currentResId" ), -1 ) );

        Long targetResId = Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "targetResId" ), -1 ) );

        String linear = ( String ) params.get( "parent" );

        securityService.swapSecurityResNote( currentResId, targetResId );

        Map paramMap = new HashMap();
        paramMap.put( "parentResLinear", linear );
        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/security/ShowSortSecRes.jsp", paramMap );
    }

    @RequestMapping( value = "/editSecurityResource.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑权限资源", token = true )
    public ModelAndView editSecurityResource( HttpServletRequest request,
        HttpServletResponse response )
    {
        SecurityResource resource = ( SecurityResource ) ServletUtil.getValueObject( request,
            SecurityResource.class );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth != null )
        {
            resource.setCreator( ( String ) auth.getApellation() );
        }

        securityService.editSecurityResource( resource );

        Map paramMap = new HashMap();
        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/security/EditSecurityResource.jsp", paramMap );
    }

    @RequestMapping( value = "/deleteSecurityResource.do", method = { RequestMethod.POST, RequestMethod.GET } )
    @ActionInfo( traceName = "删除权限资源", token = true )
    public ModelAndView deleteSecurityResource( HttpServletRequest request,
        HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String idparam = ( String ) params.get( "id" );

        Long id = Long.valueOf( StringUtil.getLongValue( idparam, -1 ) );

        if( id.longValue() < 0 )
        {
            throw new FrameworkException( "错误的ID信息" );
        }

        securityService.deleteSecurityResourceByResId( id );

        return ServletUtil.redirect( "/core/security/ManageResource.jsp" );
    }

    @RequestMapping( value = "/addSecType.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加权限类型", token = true )
    public ModelAndView addSecType( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        securityService.addSecType( params );

        Map paramMap = new HashMap();
        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/security/CreateSecType.jsp", paramMap );
    }

    @RequestMapping( value = "/editSecType.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑权限类型", token = true )
    public ModelAndView editSecType( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        securityService.editSecType( params );

        Map paramMap = new HashMap();
        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/security/EditSecType.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/deleteSecType.do", method = { RequestMethod.POST, RequestMethod.GET } )
    @ActionInfo( traceName = "删除权限资源类型" )
    public String deleteSecType( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long idVar = StringUtil.getLongValue( ( String ) params.get( "id" ), -1 );

        securityService.deleteSecType( idVar );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/checkSecType.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "检查权限资源类型" )
    public String checkSecType( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long parentId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "parentId" ),
            -1 ) );

        Integer cruuentResType = Integer.valueOf( StringUtil.getIntValue( ( String ) params
            .get( "resType" ), -1 ) );

        boolean isMember = StringUtil.getBooleanValue( ( String ) params.get( "isMember" ), false );

        return securityService.checkSecurityResType( parentId, cruuentResType, isMember );

    }

}
