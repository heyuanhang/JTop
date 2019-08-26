package cn.com.mjsoft.cms.channel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.channel.dao.vo.ContentType;
import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/channel" )
public class ManageContentTypeController
{
    private static ChannelService channelService = ChannelService.getInstance();

    @RequestMapping( value = "/addCt.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加内容类型", token = true )
    public Object addCt( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
      
        ContentType ct = ( ContentType ) ServletUtil.getValueObject( request, ContentType.class );

        channelService.createContentType( ct );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/channel/CreateContentType.jsp", paramMap );
    }

    @RequestMapping( value = "/editCt.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑内容类型", token = true )
    public Object editCt( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
       
        ContentType ct = ( ContentType ) ServletUtil.getValueObject( request, ContentType.class );

        channelService.editContentType( ct );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/channel/CreateContentType.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/deleteCt.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除内容分类", token = true )
    public Object deleteCt( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        channelService.deleteContentType( idList );

        return "success";
    }

}
