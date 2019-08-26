package cn.com.mjsoft.cms.resources.service;

import java.io.File;
import java.io.FileFilter;

public class FolderTypeFilter implements FileFilter
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
