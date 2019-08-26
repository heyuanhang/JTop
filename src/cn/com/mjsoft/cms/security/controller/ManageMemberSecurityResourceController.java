package cn.com.mjsoft.cms.security.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.security.dao.vo.MemberSecurityResource;
import cn.com.mjsoft.cms.security.service.SecurityService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.exception.FrameworkException;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/security" )
public class ManageMemberSecurityResourceController
{

    private static SecurityService securityService = SecurityService.getInstance();

    @RequestMapping( value = "/addMemberSecurityResource.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加会员权限资源", token = true )
    public ModelAndView addMemberSecurityResource( HttpServletRequest request,
        HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        MemberSecurityResource securityResourceVO = ServletUtil.getValueObject( request,
            MemberSecurityResource.class );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        securityResourceVO.setSiteId( site.getSiteId() );

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

        securityService.addMemberSecurityResource( securityResourceVO );

        Map returnParams = new HashMap();

        returnParams.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/member/AddMemberSecurityResource.jsp", returnParams );
    }

    @RequestMapping( value = "/sortMemberSecRes.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "会员权限资源排序", token = true )
    public ModelAndView sortMemberSecRes( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long currentResId = Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "currentResId" ), -1 ) );

        Long targetResId = Long.valueOf( StringUtil.getLongValue( ( String ) params
            .get( "targetResId" ), -1 ) );

        String linear = ( String ) params.get( "parent" );

        securityService.swapMemberSecurityResNote( currentResId, targetResId );

        Map paramMap = new HashMap();
        paramMap.put( "parentResLinear", linear );
        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/member/ShowSortMemberSecRes.jsp", paramMap );
    }

    @RequestMapping( value = "/editMemberSecurityResource.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑会员权限资源", token = true )
    public ModelAndView editMemberSecurityResource( HttpServletRequest request,
        HttpServletResponse response )
    {
        MemberSecurityResource resource = ( MemberSecurityResource ) ServletUtil.getValueObject(
            request, MemberSecurityResource.class );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth != null )
        {
            resource.setCreator( ( String ) auth.getApellation() );
        }

        securityService.editMemberSecurityResource( resource );

        Map paramMap = new HashMap();
        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/member/EditMemberSecurityResource.jsp", paramMap );
    }

    @RequestMapping( value = "/deleteMemberSecurityResource.do", method = { RequestMethod.POST, RequestMethod.GET } )
    @ActionInfo( traceName = "删除会员权限资源", token = true )
    public ModelAndView deleteMemberSecurityResource( HttpServletRequest request,
        HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String idparam = ( String ) params.get( "id" );

        Long id = Long.valueOf( StringUtil.getLongValue( idparam, -1 ) );

        if( id.longValue() < 0 )
        {
            throw new FrameworkException( "错误的ID信息" );
        }

        securityService.deleteMemberSecurityResourceByResId( id );

        return ServletUtil.redirect( "/core/member/ManageMemberResource.jsp" );

    }

}
