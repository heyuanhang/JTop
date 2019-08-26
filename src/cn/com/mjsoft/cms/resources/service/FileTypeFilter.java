package cn.com.mjsoft.cms.resources.service;

import java.io.File;
import java.io.FileFilter;

public class FileTypeFilter implements FileFilter
{

    public boolean accept( File file )
    {

        // 为文件且非隐藏文件
        if( file.isFile() && !file.isHidden() )
        {
            return true;
        }

        return false;
    }
}
