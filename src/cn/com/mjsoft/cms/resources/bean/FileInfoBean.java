package cn.com.mjsoft.cms.resources.bean;

import java.util.HashMap;
import java.util.Map;

import cn.com.mjsoft.cms.behavior.JtRuntime;
import cn.com.mjsoft.cms.common.Constant;
import cn.com.mjsoft.cms.site.bean.SiteGroupBean;
import cn.com.mjsoft.framework.security.session.SecuritySessionKeeper;
import cn.com.mjsoft.framework.util.StringUtil;

@SuppressWarnings( "unchecked" )
public class FileInfoBean
{
    private static final Map ICON = new HashMap();

    static
    {
        ICON.put( "gif", "image_gif.gif" );
        ICON.put( "jpg", "image_jpg.gif" );
        ICON.put( "jpeg", "image_jpg.gif" );
        ICON.put( "png", "image_png.gif" );
        ICON.put( "bmp", "image_bmp.gif" );
        ICON.put( "jsp", "code_jsp.png" );
        ICON.put( "html", "code_html.gif" );
        ICON.put( "thtml", "page_white_freehand.png" );
        ICON.put( "ftl", "page_white_freehand.png" );
        ICON.put( "shtml", "code_html.gif" );
        ICON.put( "htm", "code_html.gif" );
        ICON.put( "css", "code_css.gif" );
        ICON.put( "txt", "code_txt.gif" );
        ICON.put( "js", "code_js.gif" );
        ICON.put( "pdf", "text_pdf.png" );

        ICON.put( "jar", "zip.png" );
        ICON.put( "zip", "zip.png" );
        ICON.put( "rar", "zip.png" );
        ICON.put( "tar", "zip.png" );

        ICON.put( "swf", "media_flash.png" );
        ICON.put( "flv", "media_flash.png" );

        ICON.put( "mp4", "media_default.png" );
        ICON.put( "rmvb", "media_default.png" );
        ICON.put( "rm", "media_default.png" );
        ICON.put( "avi", "media_default.png" );
        ICON.put( "mkv", "media_default.png" );
        ICON.put( "wmv", "media_default.png" );
        ICON.put( "3gp", "media_default.png" );
        ICON.put( "mpg", "media_default.png" );
        ICON.put( "mpeg", "media_default.png" );

        ICON.put( "mp3", "music_default.png" );
        ICON.put( "wav", "music_default.png" );
        ICON.put( "wma", "music_default.png" );

        ICON.put( "xls", "office_excel.png" );
        ICON.put( "xlsx", "office_excel.png" );
        ICON.put( "doc", "office_word.png" );
        ICON.put( "docx", "office_word.png" );
        ICON.put( "ppt", "office_pp.png" );
        ICON.put( "pptx", "office_pp.png" );
        ICON.put( "vsd", "office_default.png" );
        ICON.put( "vsd", "office_default.png" );

    }

    private String fileName;

    private String entry; // 相对于完整path的路径

    private String lastModifyTime;

    private String creator;

    private Long size;

    private String type;

    private Boolean isDir;

    private Boolean haveDir;

    public String getCreator()
    {
        return creator;
    }

    public void setCreator( String creator )
    {
        this.creator = creator;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName( String fileName )
    {
        this.fileName = fileName;
    }

    public String getEntry()
    {
        return entry;
    }

    public void setEntry( String entry )
    {
        this.entry = entry;
    }

    public String getLastModifyTime()
    {
        return lastModifyTime;
    }

    public void setLastModifyTime( String lastModifyTime )
    {
        this.lastModifyTime = lastModifyTime;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public Boolean getHaveDir()
    {
        return haveDir;
    }

    public void setHaveDir( Boolean haveDir )
    {
        this.haveDir = haveDir;
    }

    public Boolean getIsDir()
    {
        return isDir;
    }

    public void setIsDir( Boolean isDir )
    {
        this.isDir = isDir;
    }

    public Long getSize()
    {
        return size;
    }

    public void setSize( Long size )
    {
        this.size = size;
    }

    // 业务方法

    public String getIcon()
    {
        if( this.isDir.booleanValue() )
        {
            return "folder.gif";
        }
        else
        {
            String icon = ( String ) ICON.get( this.type );
            if( icon != null )
            {
                return icon;
            }
            else
            {
                return "any_file.png";
            }
        }
    }

    public String getFullPath()
    {
        String path = "";

        String fp = JtRuntime.cmsServer.getDomainFullPath();

        SiteGroupBean site = ( SiteGroupBean ) SecuritySessionKeeper.getSecuritySession()
            .getCurrentLoginSiteInfo();

        path = StringUtil.replaceString( this.entry, "*", Constant.CONTENT.URL_SEP, false, false );

        // if( !path.startsWith( "/template/" ) )
        // {
        // if( path.startsWith( "/upload/" ) )
        // {
        // path = StringUtil
        // .replaceString( path, "/upload/", "", false, false );
        // }
        //  
        //             
        // }
        // else
        // {
        // return site.getHostMainUrl() + path;
        // }

        // String cmsBase = InitSiteGroupInfoBehavior.currentCmsServerInfoBean
        // .getDomainFullPath();

        // return site.getSiteImagePrefixUrl() + path;

        return fp + site.getSiteRoot() + path;
    }
}
