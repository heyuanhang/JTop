package cn.com.mjsoft.cms.block.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.block.service.BlockService;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/block" )
public class ManageBlockTypeFlow
{
    private static BlockService blockService = BlockService.getInstance();

    @RequestMapping( value = "/createBlockType.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加区块分类", token = true )
    public Object createBlockType( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        String btName = ( String ) params.get( "blockTypeName" );

        String tmUrl = ( String ) params.get( "templateUrl" );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        blockService.addNewBlockType( btName, tmUrl, site.getSiteFlag(), ( String ) auth
            .getApellation() );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/block/CreateBlockType.jsp", paramMap );
    }

    @RequestMapping( value = "/editBlockType.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑区块分类", token = true )
    public Object editBlockType( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        Long blockTypeId = StringUtil.getLongValue( ( String ) params.get( "blockTypeId" ), -1 );

        String btName = ( String ) params.get( "blockTypeName" );

        String tmUrl = ( String ) params.get( "templateUrl" );

        blockService.editBlockType( btName, tmUrl, blockTypeId );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/block/EditBlockType.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/deleteBlockType.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除区块分类", token = true )
    public Object deleteBlockType( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        List ids = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        blockService.deleteBlockTypeById( ids );

        return "success";
    }

}
