package cn.com.mjsoft.cms.content.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.content.bean.CropAndZoomImageBean;
import cn.com.mjsoft.cms.resources.bean.SiteResourceBean;
import cn.com.mjsoft.cms.resources.dao.vo.SiteResource;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.ImageUtil;
import cn.com.mjsoft.framework.util.MathUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/content" )
public class DisposeImageController
{

    private static ResourcesService resService = ResourcesService.getInstance();

    private static SiteGroupService siteService = SiteGroupService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/disposeImage.do", method = { RequestMethod.POST } )
    public String disposeImage( HttpServletRequest request, HttpServletResponse response )
        throws Exception
    {
        Map params = ServletUtil.getRequestInfo( request );

        CropAndZoomImageBean czBean = ( CropAndZoomImageBean ) ServletUtil.getValueObject( request,
            CropAndZoomImageBean.class );

        Long orgResId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "orgResId" ),
            -1 ) );

        boolean resizeFlag = StringUtil.getBooleanValue( ( String ) params.get( "resize" ), false );

        boolean effectFlag = StringUtil.getBooleanValue( ( String ) params.get( "effect" ), false );

        boolean ratio = StringUtil.getBooleanValue( ( String ) params.get( "ratio" ), false );

        boolean gm = StringUtil.getBooleanValue( ( String ) params.get( "gm" ), false );

        int order = StringUtil.getIntValue( ( String ) params.get( "order" ), 0 );

        int mw = StringUtil.getIntValue( ( String ) params.get( "mw" ), 0 );

        int mh = StringUtil.getIntValue( ( String ) params.get( "mh" ), 0 );

        int fmw = StringUtil.getIntValue( ( String ) params.get( "fmw" ), 0 );

        int fmh = StringUtil.getIntValue( ( String ) params.get( "fmh" ), 0 );

        String fieldSign = ( String ) params.get( "fieldSign" );

        SiteGroupBean siteBean = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( czBean.getResId() );

        // 物理主路径
        String sitePath = ServletUtil.getSiteFilePath( request.getServletContext() );

        // 当前时间日期
        String day = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        long currImageSize = 0;

        String targetFilePath = sitePath + siteBean.getSiteRoot() + File.separator
            + siteBean.getImageRoot() + File.separator
            + StringUtil.replaceString( resBean.getResSource(), "/", File.separator, false, false );

        String targetTempFileBase = sitePath + File.separator + siteBean.getSiteRoot()
            + File.separator + siteBean.getImageRoot() + File.separator + day;

        File targetFile = new File( targetFilePath );

        if( !targetFile.exists() )
        {
            return "no file";
        }

        currImageSize = targetFile.length();

        targetFile = new File( targetTempFileBase );

        if( !targetFile.exists() )
        {
            targetFile.mkdirs();
        }

        SiteResourceBean orgResBean = resService.retrieveSingleResourceBeanByResId( orgResId );

        if( effectFlag )
        {
            if( orgResId.equals( czBean.getResId() ) )
            {
                return "fieldSign=" + fieldSign + "&resId=" + czBean.getResId() + "&orgResId="
                    + orgResId + "&ratio=" + Boolean.valueOf( ratio ) + "&gm="
                    + Boolean.valueOf( gm ) + "&order=" + Integer.valueOf( order ) + "&mw="
                    + Integer.valueOf( mw ) + "&mh=" + Integer.valueOf( mh ) + "&fmw="
                    + Integer.valueOf( fmw ) + "&fmh=" + Integer.valueOf( fmh );
            }

            // 主图
            FileUtil.copyFile( targetFilePath, sitePath
                + siteBean.getSiteRoot()
                + File.separator
                + siteBean.getImageRoot()
                + File.separator
                + StringUtil.replaceString( orgResBean.getResSource(), "/", File.separator, false,
                    false ) );

            // resize图

            String resizePath = sitePath + File.separator + siteBean.getSiteRoot() + File.separator
                + siteBean.getImageRoot() + File.separator + Constant.CONTENT.TEMP_IMAGE_BASE
                + File.separator + "imgResize" + orgResId + "." + resBean.getFileType();

            FileUtil.copyFile( resizePath, sitePath
                + siteBean.getSiteRoot()
                + File.separator
                + siteBean.getImageRoot()
                + File.separator
                + StringUtil.replaceString( StringUtil.replaceString( orgResBean.getResSource(),
                    "/", "/imgResize", false, false ), "/", File.separator, false, false ) );

            resService.updateImageResourceWHL( orgResId, resBean.getWidth(), resBean.getHeight(),
                Long.valueOf( currImageSize ) );

            return "fieldSign=" + fieldSign + "&resId=" + orgResId + "&orgResId=" + orgResId
                + "&ratio=" + Boolean.valueOf( ratio ) + "&gm=" + Boolean.valueOf( gm ) + "&order="
                + Integer.valueOf( order ) + "&mw=" + Integer.valueOf( mw ) + "&mh="
                + Integer.valueOf( mh ) + "&fmw=" + Integer.valueOf( fmw ) + "&fmh="
                + Integer.valueOf( fmh );
        }

        int sx = ( int ) Math.floor( MathUtil.round( czBean.getSelectorX().doubleValue(), 0 ) );

        int ix = ( int ) MathUtil.round( czBean.getImageX().doubleValue(), 0 );

        int startX = sx - ix;

        int sy = ( int ) Math.floor( MathUtil.round( czBean.getSelectorY().doubleValue(), 0 ) );

        int iy = ( int ) Math.floor( MathUtil.round( czBean.getImageY().doubleValue(), 0 ) );

        int startY = sy - iy;

        int startW = ( int ) Math.floor( MathUtil.round( czBean.getSelectorW().doubleValue(), 0 ) );
        int startH = ( int ) Math.floor( MathUtil.round( czBean.getSelectorH().doubleValue(), 0 ) );

        if( startX < 0 )
        {
            startW = startW + startX;
            startX = 0;
        }

        if( startY < 0 )
        {
            startH = startH + startY;
            startY = 0;
        }

        String newName = DateAndTimeUtil.clusterTimeMillis() + StringUtil.getUUIDString() + "."
            + resBean.getFileType();

        String zoomImagePath = targetTempFileBase + File.separator + newName;

        String resizeImagePath = targetTempFileBase + File.separator + "imgResize" + newName;

        // 若宽高没有变化,则不缩放,直接拷贝,保证质量

        int iw = ( int ) Math.floor( MathUtil.round( czBean.getImageW().doubleValue(), 0 ) );

        int ih = ( int ) Math.floor( MathUtil.round( czBean.getImageH().doubleValue(), 0 ) );

        if( iw < resBean.getWidth().intValue() || ih < resBean.getHeight().intValue() )
        {
            ImageUtil.resizeImage( iw, ih, targetFilePath, zoomImagePath,
                Constant.RESOURCE.IMAGE_RESIZE_Q_MAX );
        }
        else
        {
            FileUtil.copyFile( targetFilePath, zoomImagePath );
        }

        Integer endW = null;
        Integer endH = null;

        String endSource = null;

        String endFullPath = null;

        String endResize = null;
        // 若只是缩放图片,则不需要裁剪
        if( !resizeFlag )
        {
            // 重新生成名称
            newName = DateAndTimeUtil.clusterTimeMillis() + StringUtil.getUUIDString() + "."
                + resBean.getFileType();

            String newImagePath = targetTempFileBase + File.separator + newName;

            ImageUtil.cutImage( zoomImagePath, newImagePath, startX, startY, startX + startW,
                startY + startH );

            endW = Integer.valueOf( startW );
            endH = Integer.valueOf( startH );
            endSource = day + "/" + newName;
            endFullPath = newImagePath;

            endResize = targetTempFileBase + File.separator + "imgResize" + newName;

        }
        else
        {
            // 缩放图片

            endW = Integer.valueOf( iw );
            endH = Integer.valueOf( ih );
            endSource = day + "/" + newName;
            endFullPath = zoomImagePath;

            endResize = targetTempFileBase + File.separator + "imgResize" + newName;
        }

        SiteResource res = new SiteResource();

        res.setClassId( resBean.getClassId() );
        res.setFileType( resBean.getFileType() );
        res.setWidth( endW );
        res.setHeight( endH );
        res.setModifyTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );
        res.setResName( resBean.getResName() );
        res.setResType( resBean.getResType() );
        res.setSiteId( resBean.getSiteId() );
        res.setResSource( endSource );

        UpdateState us = resService.addSiteResourceAndUploadTrace( res );

        if( resizeFlag && Constant.COMMON.ON.equals( orgResBean.getHaveMark() ) )
        {
            // 缩放模式下暂时将水印标志生效,因为已经打过水印
            if( us.haveKey() )
            {
                resService.setImageMarkStatus( endSource, Constant.COMMON.ON );
            }
        }

        // 缩略图

        if( res.getWidth().intValue() >= res.getHeight().intValue() )
        {
            ImageUtil.resizeImage( 140, -1, endFullPath, endResize,
                Constant.RESOURCE.IMAGE_RESIZE_Q_MID );
        }
        else
        {
            ImageUtil.resizeImage( -1, 140, endFullPath, endResize,
                Constant.RESOURCE.IMAGE_RESIZE_Q_MID );
        }

        List resList = new ArrayList( 2 );

        resList.add( res );

        // 进行数据分发
        siteService.transferUpdateDataToServer( resList );

        // 裁剪图片后需要删除原图
        if( !resizeFlag )
        {
            FileUtil.delFile( zoomImagePath );
            FileUtil.delFile( resizeImagePath );
        }

        return "fieldSign=" + fieldSign + "&resId=" + Long.valueOf( us.getKey() ) + "&orgResId="
            + orgResId + "&ratio=" + Boolean.valueOf( ratio ) + "&gm=" + Boolean.valueOf( gm )
            + "&order=" + Integer.valueOf( order ) + "&mw=" + Integer.valueOf( mw ) + "&mh="
            + Integer.valueOf( mh ) + "&fmw=" + Integer.valueOf( fmw ) + "&fmh="
            + Integer.valueOf( fmh );
    }
}
