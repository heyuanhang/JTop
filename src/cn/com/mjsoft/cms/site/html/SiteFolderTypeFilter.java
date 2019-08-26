package cn.com.mjsoft.cms.site.html;

import java.io.File;
import java.io.FileFilter;

public class SiteFolderTypeFilter implements FileFilter
{

    public boolean accept( File file )
    {
        // 为目录且非隐藏文件
        if( file.isDirectory() && !file.isHidden() )
        {
            return true;
        }
        
        return false;
    }

}
