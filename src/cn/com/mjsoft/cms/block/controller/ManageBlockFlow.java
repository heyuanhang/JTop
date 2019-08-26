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

import cn.com.mjsoft.cms.block.dao.vo.BlockInfo;
import cn.com.mjsoft.cms.block.service.BlockService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.Auth;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/block" )
public class ManageBlockFlow
{
    private static BlockService blockService = BlockService.getInstance();

    @RequestMapping( value = "/createBlock.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加区块", token = true )
    public Object createBlock( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        BlockInfo block = ( BlockInfo ) ServletUtil.getValueObject( request, BlockInfo.class );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth != null )
        {
            block.setCreator( auth.getApellation().toString() );
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        block.setSiteId( site.getSiteId() );

        // 区块的html路径在发布时不会改变
        block.setStaticUrl( block.getFlag() + Constant.CONTENT.HTML_PUB_HTML );

        blockService.addNewBlock( site, block );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/block/CreateBlock.jsp", paramMap );
    }

    @RequestMapping( value = "/editBlock.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑区块", token = true )
    public Object editBlock( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestDecodeInfo( request );

        BlockInfo block = ( BlockInfo ) ServletUtil.getValueObject( request, BlockInfo.class );

        Auth auth = SecuritySessionKeeper.getSecuritySession().getAuth();

        if( auth != null )
        {
            block.setCreator( auth.getApellation().toString() );
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        if( site != null )
        {
            block.setSiteId( site.getSiteId() );
        }

        blockService.editBlock( site, block );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        paramMap.put( "blockId", params.get( "blockId" ) );

        return ServletUtil.redirect( "/core/block/EditBlock.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/deleteBlock.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除区块", token = true )
    public Object deleteBlock( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        List ids = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        blockService.deleteBlockInfoById( ids );

        return "success";
    }

}
