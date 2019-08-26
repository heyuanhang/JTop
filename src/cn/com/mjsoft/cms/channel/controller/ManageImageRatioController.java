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

import cn.com.mjsoft.cms.channel.service.ChannelService;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/channel" )
public class ManageImageRatioController
{
    private static ChannelService channelService = ChannelService.getInstance();

    @RequestMapping( value = "/createIR.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "添加图片规格", token = true )
    public Object createIR( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        String ratioName = ( String ) params.get( "ratioName" );

        Integer rw = Integer.valueOf( StringUtil.getIntValue(
            ( String ) params.get( "ratioWidth" ), 100 ) );

        Integer rh = Integer.valueOf( StringUtil.getIntValue(
            ( String ) params.get( "ratioHeight" ), 100 ) );

        channelService.addNewImageratio( ratioName, rw, rh );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/tool/CreateImageRatio.jsp", paramMap );

    }

    @RequestMapping( value = "/editIR.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "编辑图片规格", token = true )
    public Object editIR( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        String ratioName = ( String ) params.get( "ratioName" );

        Integer rw = Integer.valueOf( StringUtil.getIntValue(
            ( String ) params.get( "ratioWidth" ), 100 ) );

        Integer rh = Integer.valueOf( StringUtil.getIntValue(
            ( String ) params.get( "ratioHeight" ), 100 ) );

        Long irId = StringUtil.getLongValue( ( String ) params.get( "irId" ), -1 );

        channelService.editImageratio( irId, ratioName, rw, rh );

        Map paramMap = new HashMap();

        paramMap.put( "fromFlow", Boolean.TRUE );

        return ServletUtil.redirect( "/core/tool/EditImageRatio.jsp", paramMap );
    }

    @ResponseBody
    @RequestMapping( value = "/deleteIR.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除图片规格", token = true )
    public Object deleteIR( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        channelService.deleteImageratio( idList );

        return "success";
    }

}
