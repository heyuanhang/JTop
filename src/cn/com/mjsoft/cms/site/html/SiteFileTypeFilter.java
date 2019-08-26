package cn.com.mjsoft.cms.site.html;

import java.io.File;
import java.io.FileFilter;

public class SiteFileTypeFilter implements FileFilter
{

    public boolean accept( File file )
    {
        String fileName = file.getName();
        // 为文件且非隐藏文件
        // if( file.isFile() && !file.isHidden() )
        // {
        // // if( ( fileName.lastIndexOf( "jsp" ) + 3 ) == fileName.length()
        // // || ( fileName.lastIndexOf( "html" ) + 4 ) == fileName.length()
        // // || ( fileName.lastIndexOf( "htm" ) + 3 ) == fileName.length() )
        // {
        // // 只取模板文件
        // return true;
        // }
        //
        // }

        return false;
    }
}
