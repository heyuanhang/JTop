package cn.com.mjsoft.cms.content.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.resources.bean.SiteResourceBean;
import cn.com.mjsoft.cms.resources.dao.vo.SiteResource;
import cn.com.mjsoft.cms.resources.service.ResourcesService;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.cms.site.service.SiteGroupService;
import cn.com.mjsoft.framework.config.impl.SystemConfiguration;
import cn.com.mjsoft.framework.persistence.core.support.UpdateState;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.DateAndTimeUtil;
import cn.com.mjsoft.framework.util.FileUtil;
import cn.com.mjsoft.framework.util.StringUtil;
import cn.com.mjsoft.framework.web.wing.ServletUtil;

@SuppressWarnings( "unchecked" )
@Controller
@RequestMapping( "/content" )
public class ConvertOfficeDocmentController
{

    private static ResourcesService resService = ResourcesService.getInstance();

    private static SiteGroupService siteService = SiteGroupService.getInstance();

    @ResponseBody
    @RequestMapping( value = "/convertOffice.do", method = { RequestMethod.POST, RequestMethod.GET } )
    public String convertOffice( HttpServletRequest request, HttpServletResponse response )
    {

        Long resId = ( Long ) request.getAttribute( "resId" );

        if( resId.longValue() < 0 )
        {
            return "error:资源丢失";
        }

        SiteResourceBean resource = resService.retrieveSingleResourceBeanByResId( resId );

        if( resource == null )
        {
            return "error:资源丢失";
        }

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        File outputHtmlFile = null;

        // 物理和网络路径
        String tempFilePath = ServletUtil.getSiteFilePath( request.getServletContext() )
            + site.getSiteRoot() + File.separator + Constant.CONTENT.TEMP_FILE_BASE
            + File.separator + resource.getResId();

        File tempFile = new File( tempFilePath );
        tempFile.mkdirs();

        String siteFileUploadPath = ServletUtil.getSiteFilePath( request.getServletContext() )
            + site.getSiteRoot() + File.separator + site.getFileRoot();

        String imageUploadWebPath = ServletUtil.getCmsServerBasePath( request )
            + site.getSiteRoot() + Constant.CONTENT.URL_SEP + Constant.CONTENT.TEMP_FILE_BASE
            + Constant.CONTENT.URL_SEP + resource.getResId();

        File targetFile = new File( siteFileUploadPath + File.separator
            + StringUtil.replaceString( resource.getResSource(), "/", File.separator, false, false ) );

        File htmlFile = new File( tempFilePath + File.separator + resource.getResId() + ".html" );
        try
        {
            outputHtmlFile = FileUtil.convertOfficeToHtmlFile( targetFile, htmlFile );
        }

        catch ( Exception e )
        {
            return "jtopcms-error:无法使用当前服务器的Office中间件,请检查系统相关部分配置!";
        }

        String end = null;
        if( outputHtmlFile.exists() )
        {

            String endStr = ( String ) FileUtil.readTXTFileContent( outputHtmlFile, FileUtil
                .getTXTFileCode( outputHtmlFile ) )[0];

            // TODO 清除Word格式 有BUG,现无需使用
            end = FileUtil.clearWordFormat( endStr );
            end = endStr;

            // 替换图片目录
            end = replaceImageSrcToFullPath( end, new File( tempFilePath ), imageUploadWebPath );

            return end;
        }

        return "error:转换文件出现错误";
    }

    /**
     * 
     * @param target 目标文本
     * @param targerTempDir 当前缓存目录
     * @param sysWebPath 系统web访问主路径
     * @return
     */
    private String replaceImageSrcToFullPath( String target, File targerTempDir, String sysWebPath )
    {
        // 当前时间日期
        String day = DateAndTimeUtil.getCunrrentDayAndTime( DateAndTimeUtil.DEAULT_FORMAT_YMD );

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        String rootPath = SystemConfiguration.getInstance().getSystemConfig().getSystemRealPath();

        String imageRoot = rootPath + File.separator + site.getSiteFlag() + File.separator
            + "upload" + File.separator + day;

        File tf = new File( imageRoot );
        if( !tf.exists() )
        {
            tf.mkdirs();
        }

        String result = target;
        if( targerTempDir.isDirectory() )
        {
            File[] imageFile = targerTempDir.listFiles( new ImageFilenameFilter() );

            for ( int i = 0; i < imageFile.length; i++ )
            {

                String originalFilename = imageFile[i].getName();

                FileUtil.copyFile( imageFile[i].getPath(), imageRoot + File.separator
                    + originalFilename );

                SiteResource resInfo = new SiteResource();

                resInfo.setSiteId( site.getSiteId() );

                resInfo.setResName( StringUtil.subString( originalFilename, 0, originalFilename
                    .lastIndexOf( "." ) ) );

                String fileType = StringUtil.subString( originalFilename,
                    originalFilename.lastIndexOf( "." ) + 1, originalFilename.length() )
                    .toLowerCase();
                resInfo.setFileType( fileType );

                resInfo.setResType( Constant.RESOURCE.IMAGE_RES_TYPE );

                resInfo.setModifyTime( new Timestamp( DateAndTimeUtil.clusterTimeMillis() ) );
                resInfo.setResSize( Long.valueOf( imageFile[i].length() ) );
                resInfo.setResSource( day + Constant.CONTENT.URL_SEP + imageFile[i].getName() );

                UpdateState dbState = resService.addSiteResourceAndUploadTrace( resInfo );

                if( dbState != null && dbState.getKey() > 0 )
                {
                    resService.updateSiteResourceTraceUseStatus( Long.valueOf( dbState.getKey() ),
                        Constant.COMMON.ON );
                }

                // 进行数据分发
                List resList = new ArrayList();
                resList.add( resInfo );
                siteService.transferUpdateDataToServer( resList );

                result = StringUtil.replaceString( result, imageFile[i].getName(), site
                    .getSiteImagePrefixUrl()
                    + day + Constant.CONTENT.URL_SEP + imageFile[i].getName(), false, false );
            }
        }

        return result;
    }

    private class ImageFilenameFilter implements FilenameFilter
    {
        public boolean accept( File dir, String name )
        {
            if( name.toLowerCase().lastIndexOf( ".png" ) + 4 == name.length()
                || name.toLowerCase().lastIndexOf( ".jpg" ) + 4 == name.length()
                || name.toLowerCase().lastIndexOf( ".jpeg" ) + 5 == name.length()
                || name.toLowerCase().lastIndexOf( ".gif" ) + 4 == name.length()
                || name.toLowerCase().lastIndexOf( ".bmp" ) + 4 == name.length() )
            {
                return true;
            }
            return false;
        }
    }

}
