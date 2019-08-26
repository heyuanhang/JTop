package cn.com.mjsoft.cms.resources.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.common.spring.annotation.ActionInfo;
import cn.com.mjsoft.cms.resources.bean.SiteResourceBean;
import cn.com.mjsoft.cms.resources.dao.vo.SiteResource;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.util.SystemSafeCharUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/resources" )
public class ManageFileResourceController
{
    // 后台管理使用，无需集群化
    public static Map statusMap = new HashMap();

    private static ResourcesService resService = ResourcesService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/covrertVideo.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "处理视频", token = true )
    public String covrertVideo( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        statusMap.clear();

        Long resId = StringUtil.getLongValue( ( String ) params.get( "resId" ), -1 );

        Integer qual = StringUtil.getIntValue( ( String ) params.get( "qual" ), -1 );

        String fileType = ( String ) params.get( "ft" );

        String act = ( String ) params.get( "act" );

        String st = ( String ) params.get( "st" );

        String et = ( String ) params.get( "et" );

        if( "cut".equals( act ) )
        {
            resService.divideMediaResource( resId, st, et, "div_video", statusMap );

            statusMap.put( "div_video", "exit" );
        }
        else
        {
            resService.covertMediaResource( resId, fileType, qual, "covert_video", statusMap );

            statusMap.put( "covert_video", "exit" );
        }

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/copyResource.do", method = { RequestMethod.POST } )
    public Object copyResource( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long resId = StringUtil.getLongValue( ( String ) params.get( "resId" ), -1 );

        Long classId = StringUtil.getLongValue( ( String ) params.get( "classId" ), -1 );

        if( resId.longValue() < 0 )
        {
            return "error:抱歉!所选资源文件已失效";
        }

        SiteResource res = resService.copyNewResourceAndFile( resId, classId );

        if( res == null )
        {
            return "error:抱歉!所选资源文件已失效";
        }

        // 20166:[CMS SHOP 全系改进] 对于视频，仍然要复制已经存在的图片
        if( Constant.RESOURCE.VIDEO_RES_TYPE.equals( res.getResType() ) )
        {
            SiteResourceBean oldRes = resService.retrieveSingleResourceBeanByResId( resId );

            if( oldRes != null && StringUtil.isStringNotNull( oldRes.getCover() ) )
            {
                SiteResourceBean cvOldRes = resService.retrieveSingleResourceBeanBySource( oldRes
                    .getCover() );

                SiteResource newCvRes = resService.copyNewResourceAndFile( cvOldRes.getResId(),
                    classId );

                resService.updateSingleVideoResourceCoverByResId( newCvRes.getResSource(), res
                    .getResId() );
            }
        }

        List beanList = new ArrayList();
        beanList.add( res );

        return StringUtil.changeJSON( beanList, "obj_" );

    }

    @ResponseBody
    @RequestMapping( value = "/checkCovrertVideo.do", method = { RequestMethod.POST } )
    public String checkCovrertVideo( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String st = ( String ) statusMap.get( ( String ) params.get( "flag" ) );

        if( "exit".equals( st ) )
        {
            st = "-1";
        }

        return ( st != null ? st : "0" );
    }

    @ResponseBody
    @RequestMapping( value = "/changeVideoName.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "改变视频名称", token = true )
    public String changeVideoName( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String name = SystemSafeCharUtil.decodeFromWeb( ( String ) params.get( "name" ) );

        Long resId = StringUtil.getLongValue( ( String ) params.get( "resId" ), -1 );

        resService.changeMediaResName( resId, name );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/setVideoClass.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "设置视频分类", token = true )
    public String setVideoClass( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long classId = StringUtil.getLongValue( ( String ) params.get( "classId" ), -1 );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        resService.setMediaResClass( idList, classId );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/snapVideoImage.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "视频截图", token = true )
    public String snapVideoImage( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        String st = ( String ) params.get( "st" );

        Long resId = StringUtil.getLongValue( ( String ) params.get( "resId" ), -1 );

        resService.snapMediaResourceCover( resId, st );

        return "success";
    }

    @ResponseBody
    @RequestMapping( value = "/deleteCovrertVideo.do", method = { RequestMethod.POST } )
    @ActionInfo( traceName = "删除截取视频", token = true )
    public String deleteCovrertVideo( HttpServletRequest request, HttpServletResponse response )
    {
        Map params = ServletUtil.getRequestInfo( request );

        List idList = StringUtil.changeStringToList( ( String ) params.get( "ids" ), "," );

        resService.deleteCovertMediaResource( idList );

        return "success";
    }

}
