package cn.com.mjsoft.cms.content.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.mjsoft.cms.behavior.InitSiteGroupInfoBehavior;
import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.resources.bean.SiteResourceBean;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.FlowConstants;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/content" )
public class DownloadFileController
{
    private static Logger log = Logger.getLogger( DownloadFileController.class );

    private static ResourcesService resService = ResourcesService.getInstance();

    @RequestMapping( value = "/clientDf.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public String clientDf( HttpServletRequest request, HttpServletResponse response )
        throws UnsupportedEncodingException
    {
        Map params = ServletUtil.getRequestInfo( request );

        Long targetId = Long.valueOf( StringUtil.getLongValue( ( String ) params.get( "id" ), -1 ) );

        if( targetId.longValue() < 0 )
        {
            return FlowConstants.NULL_RESULT;
        }

        SiteResourceBean resBean = resService.retrieveSingleResourceBeanByResId( targetId );

        if( resBean == null )
        {
            log.info( "[SystemDownloadFileLinkFlow] 目标资源信息丢失,ID:" + targetId );
            return FlowConstants.NULL_RESULT;
        }

        SiteGroupBean site = ( SiteGroupBean ) InitSiteGroupInfoBehavior.siteGroupIdInfoCache
            .getEntry( resBean.getSiteId() );

        String baseRealPath = SystemConfiguration.getInstance().getSystemConfig()
            .getSystemRealPath();

        String rootPath = baseRealPath + site.getSiteRoot();
        File file = null;
        // if( Constant.RESOURCE.IMAGE_RES_TYPE.equals( resBean.getResType() ) )
        // {
        // file = new File( rootPath + File.separator + site.getImageRoot()
        // + File.separator + resBean.getResSource() );
        // }
        // else if( Constant.RESOURCE.VIDEO_RES_TYPE.equals(
        // resBean.getResType() )
        // || Constant.RESOURCE.MUSIC_RES_TYPE.equals( resBean.getResType() ) )
        // {
        // file = new File( rootPath + File.separator + site.getMediaRoot()
        // + File.separator + resBean.getResSource() );
        // }
        // else
        {
            file = new File( rootPath + File.separator + site.getFileRoot() + File.separator
                + resBean.getResSource() );
        }

        if( file == null || !file.exists() )
        {
            return FlowConstants.NULL_RESULT;
        }

        // 文件类型响应报文头
        response.reset();// 强制清除response内部信息
        response.setContentType( "application/octet-stream" );
        response.setHeader( "Content-Disposition", "attachment; filename="
            + new String( resBean.getResName().getBytes( JtRuntime.cmsServer.getEncoding() ),
                "iso-8859-1" ) + "." + resBean.getFileType() );
        response.addHeader( "Content-Length", "" + file.length() );

        // 获取输出流，将文件写出到客户端
        OutputStream os = null;
        FileInputStream fis = null;
        try
        {
            os = response.getOutputStream();
            // 将下载文件读入
            fis = new FileInputStream( file );
            byte[] b = new byte[1024];
            while ( fis.read( b ) != -1 )
            {
                os.write( b );
            }
            os.flush();
        }
        catch ( Exception e )
        {
        }
        finally
        {
            try
            {
                if( fis != null )
                {
                    fis.close();
                }

                if( os != null )
                {
                    os.close();
                }
            }
            catch ( Exception e )
            {
            }
        }

        // 设置成功标志

        request.setAttribute( "successFlag", Boolean.TRUE );

        return null;

    }

}
