package cn.com.mjsoft.cms.content.interceptor;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.mjsoft.cms.cluster.service.ClusterService;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

public class DeleteTempFileWhenUploadErrorInterceptor implements HandlerInterceptor
{
    private static Logger log = Logger.getLogger( DeleteTempFileWhenUploadErrorInterceptor.class );

    public void postHandle( HttpServletRequest request, HttpServletResponse response,
        Object handler, ModelAndView modelAndView ) throws Exception
    {
        Boolean flowSuccessFlag = ( Boolean ) request.getAttribute( "successFlag" );

        log.info( "[Interceptor] - DeleteTempWhenUploadErrorInterceptor - flowSuccessFlag:"
            + flowSuccessFlag );

        if( !Boolean.TRUE.equals( flowSuccessFlag ) )
        {
            String sitePath = ServletUtil.getSiteFilePath( request.getServletContext() );

            String targetFileBasePath = "";

            String fileType = ( String ) request.getAttribute( "fileType" );

            if( "photoGroup".equals( fileType ) )
            {
                String ids = ( String ) request.getAttribute( "ids" );

                if( ids == null )
                {
                    ids = "";
                }

                // contentService.deletePhotoGroupContent( StringUtil.split(ids,
                // "," ) );
            }
            else
            {
                String fileName = "";

                fileName = ( String ) request.getAttribute( "fileName" );

                if( "img".equals( fileType ) )
                {
                    String isChannal = ( String ) request.getAttribute( "isChannal" );

                    if( "true".equals( isChannal ) )
                    {
                        targetFileBasePath = Constant.CONTENT.IMG_BASE + File.separator;
                    }
                    else
                    {
                        targetFileBasePath = Constant.CONTENT.IMG_BASE + File.separator
                            + Constant.CONTENT.CHANNEL_BASE + File.separator;
                    }
                }
                else if( "video".equals( fileType ) )
                {
                    targetFileBasePath = Constant.CONTENT.MEDIA_BASE + File.separator;
                }

                log.info( "[Action] DeleteTempFileFlow 目标文件:" + targetFileBasePath + fileName );

                if( !( "".equals( targetFileBasePath.trim() ) || "".equals( fileName.trim() ) ) )
                {
                    if( FileUtil.delFile( sitePath + targetFileBasePath + fileName ) )
                    {
                        log.info( "[Action] DeleteTempFileFlow 成功删除临时文件:" + targetFileBasePath
                            + fileName );

                        ClusterService.exeClusterMasterCMD( "cluster/deleteFile.do", "fp="
                            + StringUtil.replaceString( targetFileBasePath + fileName,
                                File.separator, "/", false, false ), Constant.COMMON.POST );
                    }
                }
            }

        }

    }

    public boolean preHandle( HttpServletRequest request, HttpServletResponse response,
        Object handler ) throws Exception
    {
        return true;
    }

    public void afterCompletion( HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex ) throws Exception
    {

    }

}
